
package services;

import java.util.Collection;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;

import domain.Manager;
import forms.ManagerForm;
import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ManagerService {

	// Managed repository

	@Autowired
	private ManagerRepository managerRepository;


	// Supported services

	public ManagerService() {
		super();
	}

	public Manager create() {
		return new Manager();
	}

	public Collection<Manager> findAll() {
		return this.managerRepository.findAll();
	}

	public Manager findOne(final int managerId) {
		return this.managerRepository.findOne(managerId);

	}

	public void save(final Manager manager) {
		this.managerRepository.save(manager);
	}

	public void delete(final Manager manager) {
		this.managerRepository.delete(manager);
	}


	@Autowired
	private Validator validator;


	public Manager reconstruct(final ManagerForm managerForm, final BindingResult binding) throws CheckDigitException {
		Assert.notNull(managerForm);
		Manager result = this.create();

		if (managerForm.getId() == 0) {

			if (managerForm.getPassword().equals(managerForm.getRepeatPassword()))
				result.getUserAccount().setPassword(this.hashPassword(managerForm.getPassword()));
			else
				result.getUserAccount().setPassword("");

			result.getUserAccount().setUsername(managerForm.getUserName());
			//Editing customer
		} else {
			result = this.findOne(managerForm.getId());
			final Manager manager = this.findByPrincipal();
			Assert.isTrue(manager.equals(result));

		}

		result.setEmail(managerForm.getEmail());
		result.setName(managerForm.getName());
		result.setNid(managerForm.getNid());
		result.setPhone(managerForm.getPhone());
		result.setSurname(managerForm.getSurname());
		result.setCoordinates(managerForm.getCoordinates());

		if (binding != null && managerForm.getId() == 0) {
			this.validator.validate(result, binding);

			if (result.getUserAccount().getPassword() == "" || result.getUserAccount().getPassword() == null) {
				FieldError fieldError;
				final String[] codes = {
					"customer.passwords.error"
				};
				fieldError = new FieldError("managerForm", "userAccount.password", result.getUserAccount().getPassword(), false, codes, null, "");
				binding.addError(fieldError);
			}
		} else
			this.validator.validate(result, binding);

		return result;
	}

	// Other business methods

	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();

		result = this.findByUserAccount(userAccount);
		Assert.notNull(result, "Any manager with userAccountId=" + userAccount.getId() + "has be found");

		return result;
	}

	public Manager findByUserAccount(final UserAccount userAccount) {
		Manager result;
		int userAccountId;

		userAccountId = userAccount.getId();
		result = this.managerRepository.findByUserAccountId(userAccountId);

		return result;
	}

	public Manager findByUserName(final String username) {
		Assert.notNull(username);
		Manager result;

		result = this.managerRepository.findByUserName(username);

		return result;
	}

	private String hashPassword(final String password) {
		String result;
		Md5PasswordEncoder encoder;

		encoder = new Md5PasswordEncoder();
		result = encoder.encodePassword(password, null);

		return result;
	}
}
