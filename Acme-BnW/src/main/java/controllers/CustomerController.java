
package controllers;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Brand;
import domain.Customer;
import forms.CustomerForm;
import services.CustomerService;

@RequestMapping(value = "/customer")
@Controller
public class CustomerController extends AbstractController {

	//Related services

	@Autowired
	private CustomerService customerService;


	//Constructor

	public CustomerController() {
		super();
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		ModelAndView result;

		CustomerForm customerForm = customerService.createForm();
		result = this.createModelAndView(customerForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid CustomerForm customerForm, BindingResult binding) throws CheckDigitException {

		ModelAndView result = new ModelAndView();

		Customer customer;

		customer = customerService.reconstruct(customerForm, binding);
		if (binding.hasErrors()) {
			result = createModelAndView(customerForm);
		} else {
			try {
				customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = createModelAndView(customerForm, "customer.commit.error");
			}
		}

		return result;
	}
	//Ancillary methods

	protected ModelAndView createModelAndView(final CustomerForm customerForm) {
		return this.createModelAndView(customerForm, null);
	}

	protected ModelAndView createModelAndView(final CustomerForm customerForm, final String message) {
		ModelAndView result;

		List<Brand> brands = Arrays.asList(Brand.values());
		result = new ModelAndView("customer/register");
		result.addObject("customerForm", customerForm);
		result.addObject("message", message);
		result.addObject("brands", brands);

		return result;
	}

}
