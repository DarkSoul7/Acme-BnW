
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

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

import domain.Actor;
import domain.Administrator;
import domain.Bet;
import domain.CreditCard;
import domain.Customer;
import domain.Message;
import domain.Punctuation;
import domain.Team;
import domain.Ticket;
import domain.Topic;
import forms.BalanceForm;
import forms.CustomerForm;
import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CustomerService {

	// Managed repository

	@Autowired
	private CustomerRepository		customerRepository;

	// Supported services

	@Autowired
	private WelcomeOfferService		welcomeOfferService;

	@Autowired
	private AdministratorService	administratorService;
	
	@Autowired
	private ActorService actorService;


	//Constructor

	public CustomerService() {
		super();
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


	@Autowired
	private Validator	validator;


	public Customer reconstruct(final CustomerForm customerForm, final BindingResult binding) throws CheckDigitException {
		Assert.notNull(customerForm);
		Customer result = this.create();

		//New customer (register)
		if (customerForm.getId() == 0) {

			if (customerForm.getPassword().equals(customerForm.getRepeatPassword()))
				result.getUserAccount().setPassword(this.hashPassword(customerForm.getPassword()));
			else
				result.getUserAccount().setPassword("");

			result.getUserAccount().setUsername(customerForm.getUserName());
			final Collection<Bet> bets = new ArrayList<Bet>();
			result.setBets(bets);
			final Collection<Message> messages = new ArrayList<Message>();
			result.setMessages(messages);
			final Collection<Punctuation> punctuations = new ArrayList<>();
			result.setPunctuations(punctuations);
			final Collection<Team> teams = new ArrayList<>();
			result.setTeams(teams);
			final Collection<Ticket> tickets = new ArrayList<>();
			result.setTickets(tickets);
			final Collection<Topic> topics = new ArrayList<>();
			result.setTopics(topics);

			//Register default parameters for new Customer
			result.setFinishedOffer(false);
			result.setWelcomeOffer(this.welcomeOfferService.getActive());
			result.setBalance(0.);

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

		// Check creditCard if any
		if (this.analyzeCreditCard(customerForm.getCreditCard())) {
			if (!this.checkCreditCard(result.getCreditCard())) {
				FieldError fieldError;
				final String[] codes = {
					"customer.creditCard.error"
				};
				fieldError = new FieldError("customerForm", "creditCard", result.getCreditCard(), false, codes, null, "");
				binding.addError(fieldError);
			}
			//			result.setValidCreditCard(this.checkCreditCard(result.getCreditCard()));
			this.validator.validate(customerForm.getCreditCard(), binding);
		}

		if (binding != null && customerForm.getId() == 0) {
			this.validator.validate(result, binding);

			if (result.getUserAccount().getPassword() == "" || result.getUserAccount().getPassword() == null) {
				FieldError fieldError;
				final String[] codes = {
					"customer.passwords.error"
				};
				fieldError = new FieldError("customerForm", "userAccount.password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			}
		} else
			this.validator.validate(result, binding);

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

		if (luhn && (days > 1))
			result = true;

		return result;
	}

	public void addBalance(final BalanceForm balanceForm) {
		final Customer principal = this.findByPrincipal();
		principal.setBalance(principal.getBalance() + balanceForm.getBalance());

		this.save(principal);
	}

	public void extractBalance(final BalanceForm balanceForm) {
		final Customer principal = this.findByPrincipal();
		Assert.isTrue(balanceForm.getBalance() > 10.0);
		principal.setBalance(principal.getBalance() - balanceForm.getBalance());
		Assert.isTrue(principal.getBalance() >= 0);

		this.save(principal);
	}

	public Collection<CustomerForm> getGlobalBalance(final String name, final String surname, final String nid) {
		this.administratorService.findByPrincipal();
		Collection<CustomerForm> result;
		TypedQuery<CustomerForm> query;
		EntityManagerFactory entityManagerFactory;
		EntityManager entityManager;
		String queryString;
		Map<String, Object> parameters;

		entityManagerFactory = Persistence.createEntityManagerFactory("Acme-BnW");
		entityManager = entityManagerFactory.createEntityManager();
		queryString = "select new forms.CustomerForm(c, (select (sum(b.quantity*b.fee)) as result1 from Bet b where b.status = 1 and b.customer.id = c.id), (select sum(b2.quantity) as result2 from Bet b2 where b2.status = 2 and b2.customer.id = c.id))  from Customer c";
		parameters = new HashMap<String, Object>();

		if (name != null || surname != null || nid != null) {
			queryString += " where ";
			int cont = 0;
			if (name != null) {
				queryString += "c.name like :name";
				parameters.put("name", "%" + name + "%");
				cont++;
			}
			if (surname != null) {
				if (cont > 0)
					queryString += " and c.surname like :surname";
				else
					queryString += "c.surname like :surname";
				parameters.put("surname", "%" + surname + "%");
				cont++;
			}
			if (nid != null) {
				if (cont > 0)
					queryString += " and c.nid like :nid";
				else
					queryString += "c.nid like :nid";
				parameters.put("nid", "%" + nid + "%");
			}
		}

		query = entityManager.createQuery(queryString, CustomerForm.class);

		for (final Entry<String, Object> e : parameters.entrySet())
			query.setParameter(e.getKey(), e.getValue());

		result = query.getResultList();

		return result;
	}
	
	public void disable(int customerId){
		Actor actor = actorService.findByPrincipal();
		if(actor.getUserAccount().getAuthorities().contains(Authority.ADMIN) || actor.getUserAccount().getAuthorities().contains(Authority.CUSTOMER)){
			Customer customer = this.findOne(customerId);
			this.save(customer);
		}else{
			Assert.isTrue(false);
		}
	}
	
	public void activeCustomer(int customerId){
		Administrator admin = administratorService.findByPrincipal();
		Assert.notNull(admin);
		Customer customer = this.findOne(customerId);
		customer.getUserAccount().setEnabled(true);
		this.save(customer);
	}
	
	public void activeOffer(Double charge, int customerId){
		Customer customer = this.findByPrincipal();
		Assert.isTrue(charge >= customer.getWelcomeOffer().getExtractionAmount());
		customer.setBalance(customer.getBalance()+customer.getWelcomeOffer().getAmount());
		customer.setFinishedOffer(true);
		this.save(customer);
	
		
	}
	
	public void cancelOffer(int customerId){
		Customer customer = this.findByPrincipal();
		Assert.isTrue(!customer.getFinishedOffer());
		customer.setWelcomeOffer(null);
		this.save(customer);
	}
}
