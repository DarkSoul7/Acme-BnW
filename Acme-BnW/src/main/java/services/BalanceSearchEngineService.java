
package services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forms.CustomerForm;

@Service
@Transactional
public class BalanceSearchEngineService {

	//Supported services

	@Autowired
	private AdministratorService	administratorService;


	//Constructor
	public BalanceSearchEngineService() {
		super();
	}

	//Other business methods

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
}
