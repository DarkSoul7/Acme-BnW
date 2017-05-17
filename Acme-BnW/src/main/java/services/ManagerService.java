package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Customer;
import domain.Manager;
import domain.Market;
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
}
