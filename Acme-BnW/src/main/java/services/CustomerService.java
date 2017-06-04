
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Bet;
import domain.ConvertionCurrency;
import domain.CreditCard;
import domain.Currency;
import domain.Customer;
import domain.Message;
import domain.Punctuation;
import domain.Team;
import domain.Ticket;
import domain.Topic;
import forms.BalanceForm;
import forms.CustomerForm;

@Service
@Transactional
public class CustomerService {

	// Managed repository

	@Autowired
	private CustomerRepository			customerRepository;

	private static CustomerRepository	staticCustomerRepository;

	// Supported services

	@Autowired
	private WelcomeOfferService			welcomeOfferService;

	@Autowired
	private AdministratorService		administratorService;

	@Autowired
	private TeamService					teamService;

	@Autowired
	private TicketService				ticketService;

	@Autowired
	private Validator					validator;

	@Autowired
	private ConvertionCurrencyService	convertionCurrencyService;


	//Constructor

	public CustomerService() {
		super();
	}

	@PostConstruct
	public void initStaticRepository() {
		CustomerService.staticCustomerRepository = this.customerRepository;
	}

	//Simple CRUD methods

	public CustomerForm createForm() {
		return new CustomerForm();
	}

	public Customer create() {
		Customer result;
		UserAccount userAccount;
		Authority authority;

		authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);

		userAccount = new UserAccount();
		userAccount.addAuthority(authority);
		userAccount.setEnabled(true);

		result = new Customer();
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int customerId) {
		return this.customerRepository.findOne(customerId);

	}

	public void save(final Customer customer) {
		Assert.notNull(customer);

		this.customerRepository.save(customer);
	}

	public void delete(final Customer customer) {
		this.customerRepository.delete(customer);
	}

	// Other business methods

	public Customer findByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any customer with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public static Customer findStaticByPrincipal() {
		Customer result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = CustomerService.staticCustomerRepository.findByUserAccountId(userAccount.getId());
		Assert.notNull(result, "Any customer with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Customer result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.customerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Customer findByUserName(final String username) {
		Assert.notNull(username);
		Customer result;

		result = this.customerRepository.findByUserName(username);

		return result;
	}

	public Customer reconstruct(final CustomerForm customerForm, final BindingResult binding) throws CheckDigitException {
		Assert.notNull(customerForm);
		Customer result = this.create();

		//New customer (register)
		if (customerForm.getId() == 0) {

			if (!customerForm.getPassword().isEmpty() && customerForm.getPassword().equals(customerForm.getRepeatPassword())) {
				result.getUserAccount().setPassword(this.hashPassword(customerForm.getPassword()));
			} else {
				result.getUserAccount().setPassword("");

			}
			result.getUserAccount().setUsername(customerForm.getUsername());
			final Collection<Bet> bets = new ArrayList<Bet>();
			result.setBets(bets);
			final Collection<Message> messages = new ArrayList<Message>();
			result.setMessages(messages);
			final Collection<Punctuation> punctuations = new ArrayList<>();
			result.setPunctuations(punctuations);
			final Collection<Team> teams = new ArrayList<>();
			result.setFavouriteTeams(teams);
			final Collection<Ticket> tickets = new ArrayList<>();
			result.setTickets(tickets);
			final Collection<Topic> topics = new ArrayList<>();
			result.setTopics(topics);

			//Register default parameters for new Customer
			result.setFinishedOffer(false);
			result.setWelcomeOffer(this.welcomeOfferService.getActive());
			result.setBalance(0.);
			result.setIsDisabled(false);
			result.setActiveWO(true);
			result.setBanNum(0);

			//Editing customer
		} else {
			result = this.findOne(customerForm.getId());
			final Customer customer = this.findByPrincipal();

			Assert.isTrue(customer.equals(result));

		}

		result.setBirthDate(customerForm.getBirthDate());
		result.setCoordinates(customerForm.getCoordinates());
		result.setCreditCard(customerForm.getCreditCard());
		result.setEmail(customerForm.getEmail());
		result.setName(customerForm.getName());
		result.setNid(customerForm.getNid());
		result.setPhone(customerForm.getPhone());
		result.setSurname(customerForm.getSurname());

		// Check customer is over age
		final int customerYears = this.getCustomerAge(result);
		if (customerYears < 18)
			result.setOverAge(false);
		else
			result.setOverAge(true);

		//Check overAge

		if (result.getOverAge() == false) {
			FieldError fieldError;
			final String[] codes = {
				"customer.birthDate.error"
			};
			fieldError = new FieldError("customerForm", "overAge", result.getOverAge(), false, codes, null, "");
			binding.addError(fieldError);

			this.validator.validate(customerForm.isOverAge(), binding);
		}

		// Check creditCard if any
		if (this.analyzeCreditCard(customerForm.getCreditCard())) {
			if (this.checkCreditCard(result.getCreditCard()) == false) {
				FieldError fieldError;
				final String[] codes = {
					"customer.creditCard.error"
				};
				fieldError = new FieldError("customerForm", "creditCard", result.getCreditCard(), false, codes, null, "");
				binding.addError(fieldError);
			}
			//			result.setValidCreditCard(this.checkCreditCard(result.getCreditCard()));
			this.validator.validate(customerForm.getCreditCard().getCvv(), binding);
		}

		if (binding != null && customerForm.getId() == 0) {

			if (result.getUserAccount().getPassword() == "" || result.getUserAccount().getPassword() == null) {
				FieldError fieldError;
				final String[] codes = {
					"customer.passwords.error"
				};
				fieldError = new FieldError("customerForm", "userAccount.password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			}
		}

		return result;
	}

	public CustomerForm toFormObject(final Customer customer) {
		Assert.notNull(customer);
		final CustomerForm result = new CustomerForm();

		result.setId(customer.getId());
		result.setName(customer.getName());
		result.setSurname(customer.getSurname());
		result.setEmail(customer.getEmail());
		result.setPhone(customer.getPhone());
		result.setNid(customer.getNid());
		result.setCoordinates(customer.getCoordinates());
		result.setBirthDate(customer.getBirthDate());
		result.setCreditCard(customer.getCreditCard());

		return result;
	}

	private boolean analyzeCreditCard(final CreditCard creditCard) {
		boolean result = false;

		if (creditCard != null)
			if (creditCard.getBrandName() != null || !creditCard.getHolderName().isEmpty() || creditCard.getCvv() != null || creditCard.getExpirationMonth() != null
				|| creditCard.getExpirationYear() != null || !creditCard.getNumber().isEmpty())
				result = true;
		return result;
	}

	public int getCustomerAge(final Customer customer) {
		Assert.notNull(customer);

		final LocalDate customerBirthDate = new LocalDate(customer.getBirthDate());
		final LocalDate now = new LocalDate();
		final Years customerYears = Years.yearsBetween(customerBirthDate, now);

		return customerYears.getYears();
	}

	private String hashPassword(final String password) {
		String result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		result = encoder.encodePassword(password, null);

		return result;
	}

	public boolean checkCreditCard(final CreditCard creditCard) throws CheckDigitException {
		Assert.notNull(creditCard);
		boolean result = false;

		final boolean luhn = LuhnCheckDigit.LUHN_CHECK_DIGIT.isValid(creditCard.getNumber());
		final LocalDate localDate = new LocalDate();
		final LocalDate expirationDate = new LocalDate(creditCard.getExpirationYear(), creditCard.getExpirationMonth(), 1);

		final int days = Days.daysBetween(localDate, expirationDate).getDays();

		if (luhn && (days > 1) && !creditCard.getHolderName().isEmpty() && creditCard.getBrandName() != null && !creditCard.getHolderName().isEmpty() && creditCard.getExpirationMonth() != null
			&& creditCard.getExpirationYear() != null && !creditCard.getNumber().isEmpty()) {
			result = true;
		}

		return result;
	}
	public void addBalance(final BalanceForm balanceForm) {
		final Customer principal = this.findByPrincipal();
		final Date currentMoment = new Date();

		//Conversión de moneda
		final ConvertionCurrency convertionCurrency = this.convertionCurrencyService.findOne(balanceForm.getCurrency());
		final Double convertedAmount = balanceForm.getBalance() * convertionCurrency.getConvertionAmount();
		final Double roundAmount = Math.round(convertedAmount * 100.0) / 100.0;
		principal.setBalance(principal.getBalance() + roundAmount);

		if (!principal.getFinishedOffer() && principal.getActiveWO()) {
			if (principal.getWelcomeOffer() != null && principal.getWelcomeOffer().getExtractionAmount() <= roundAmount && principal.getWelcomeOffer().getOpenPeriod().compareTo(currentMoment) <= 0
				&& principal.getWelcomeOffer().getEndPeriod().compareTo(currentMoment) >= 0) {
				principal.setBalance(principal.getBalance() + principal.getWelcomeOffer().getAmount());
			}
			principal.setFinishedOffer(true);
		}

		this.save(principal);
	}

	public void extractBalance(final BalanceForm balanceForm, final String language) {
		final Customer principal = this.findByPrincipal();
		Assert.isTrue(balanceForm.getBalance() >= 10.0);
		Assert.isTrue(principal.getBalance() - balanceForm.getBalance() >= 0);

		//Create ticket and save ticket and customer
		this.getextractVirtualCredit(balanceForm.getBalance(), language, balanceForm.getCurrency());

	}

	public void managementBan(final int customerId) {
		final Customer customer = this.customerRepository.findOne(customerId);
		Assert.notNull(customer);
		this.administratorService.findByPrincipal();

		if (customer.getUserAccount().isEnabled()) {
			customer.getUserAccount().setEnabled(false);
			customer.setBanNum(customer.getBanNum() + 1);
		} else {
			customer.getUserAccount().setEnabled(true);
		}
		this.customerRepository.save(customer);

	}

	public void autoExclusion() {
		final Customer customer = this.findByPrincipal();
		Assert.isTrue(!customer.getIsDisabled());

		customer.setIsDisabled(true);
		customer.getUserAccount().setEnabled(false);
		this.customerRepository.save(customer);
	}

	public void activeOffer(final Double charge) {
		final Customer customer = this.findByPrincipal();
		Assert.isTrue(charge >= customer.getWelcomeOffer().getExtractionAmount());
		Assert.isTrue(customer.getFinishedOffer() == false);
		customer.setBalance(customer.getBalance() + customer.getWelcomeOffer().getAmount());
		customer.setFinishedOffer(true);
		this.save(customer);

	}

	public void cancelOffer() {
		final Customer customer = this.findByPrincipal();
		Assert.isTrue(!customer.getFinishedOffer());
		customer.setFinishedOffer(true);
		customer.setActiveWO(false);
		this.save(customer);
	}

	public void addTeamFavourite(final int teamId) {
		final Team team = this.teamService.findOne(teamId);
		final Customer customer = this.findByPrincipal();
		Assert.notNull(team);
		Assert.isTrue(!team.getCustomers().contains(customer));

		team.getCustomers().add(customer);

		this.teamService.addTeamFavourite(team);
	}

	public void deleteTeamFavourite(final int teamId) {
		final Team team = this.teamService.findOne(teamId);
		final Customer customer = this.findByPrincipal();
		Assert.notNull(team);
		Assert.isTrue(team.getCustomers().contains(customer));

		team.getCustomers().remove(customer);

		this.teamService.addTeamFavourite(team);
	}

	public Collection<Team> getFavouriteTeams() {
		final Customer customer = this.findByPrincipal();
		return this.customerRepository.getFavouriteTeams(customer.getId());
	}

	public void getVirtualCredit(final double amount) {
		final Customer customer = this.findByPrincipal();
		customer.setBalance(customer.getBalance() + amount);

		this.save(customer);
	}

	public void getextractVirtualCredit(final double amount, final String language, final Currency currency) {
		final Customer customer = this.findByPrincipal();
		Ticket ticket;

		if (language.equals("en")) {
			ticket = this.ticketService.getDefaultEnglishExtractionTicket(amount, currency);
		} else {
			ticket = this.ticketService.getDefaultSpanishExtractionTicket(amount, currency);
		}

		customer.setBalance(customer.getBalance() - amount);

		this.save(customer);
		this.ticketService.save(ticket);
	}

	public void flush() {
		this.customerRepository.flush();
	}

	//Dashboard

	//B.1

	public Integer getAutoExclusionNumber() {
		return this.customerRepository.getAutoExclusionNumber();
	}

	//B.2

	public Integer getBanNumber() {
		return this.customerRepository.getBanNumber();
	}

	//A2
	public Collection<Customer> customerWithMoreMessages() {
		return this.customerRepository.customerWithMoreMessages();
	}

	//A4

	public Collection<Customer> getCustomersWhoJoinedMorePromotion() {
		Collection<Customer> result;

		result = this.customerRepository.getCustomersWhoJoinedMorePromotion();

		return result;
	}
}
