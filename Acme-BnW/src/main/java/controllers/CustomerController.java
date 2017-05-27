
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Brand;
import domain.Customer;
import forms.BalanceForm;
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
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(required = false) final String successMessage) {
		ModelAndView result;
		Customer customer;
		CustomerForm customerForm;

		customer = this.customerService.findByPrincipal();
		customerForm = this.customerService.toFormObject(customer);
		customerForm.setAcceptCondition(true);

		result = this.editModelAndView(customerForm);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final CustomerForm customerForm, final BindingResult binding) throws CheckDigitException {
		ModelAndView result = new ModelAndView();
		final Customer customer;

		try {
			customer = this.customerService.reconstruct(customerForm, binding);

			if (binding.hasErrors()) {
				result = this.editModelAndView(customerForm, "customer.creditCard.error");
			} else {
				try {
					this.customerService.save(customer);
					result = new ModelAndView("redirect:/customer/edit.do");
					result.addObject("successMessage", "customer.edit.success");
				} catch (final Throwable oops) {
					result = this.editModelAndView(customerForm, "customer.commit.error");
				}
			}

		} catch (final Throwable oops) {
			result = this.editModelAndView(customerForm, "customer.commit.error");
		}

		return result;
	}
	
	@RequestMapping(value = "/addBalance", method = RequestMethod.GET)
	public ModelAndView addBalance() {

		ModelAndView result;

		BalanceForm balanceForm = new BalanceForm();
		result = this.addBalanceModelAndView(balanceForm);

		return result;
	}

	@RequestMapping(value = "extractBalance", method = RequestMethod.GET)
	public ModelAndView extractBalance() {

		ModelAndView result;

		BalanceForm balanceForm = new BalanceForm();
		result = this.extractBalanceModelAndView(balanceForm);

		return result;
	}

	@RequestMapping(value = "/extractBalance", method = RequestMethod.POST, params = "save")
	public ModelAndView saveExt(@Valid BalanceForm balanceForm, BindingResult binding) {

		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			result = extractBalanceModelAndView(balanceForm);
		} else {
			try {
				customerService.extractBalance(balanceForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = extractBalanceModelAndView(balanceForm, "balance.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/addBalance", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdd(@Valid BalanceForm balanceForm, BindingResult binding) {

		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			result = this.addBalanceModelAndView(balanceForm);
		} else {
			try {
				customerService.addBalance(balanceForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {
				result = addBalanceModelAndView(balanceForm, "balance.commit.error");
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
	
	protected ModelAndView editModelAndView(final CustomerForm customerForm) {
		final ModelAndView result = this.editModelAndView(customerForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final CustomerForm customerForm, final String message) {
		ModelAndView result;
		result = new ModelAndView("customer/edit");
		result.addObject("customerForm", customerForm);
		result.addObject("message", message);
		result.addObject("requestURI", "customer/edit.do");

		return result;
	}

	protected ModelAndView addBalanceModelAndView(final BalanceForm balanceForm) {
		return this.addBalanceModelAndView(balanceForm, null);
	}

	protected ModelAndView addBalanceModelAndView(final BalanceForm balanceForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/addBalance");
		result.addObject("balanceForm", balanceForm);
		result.addObject("message", message);
		result.addObject("balanceNow", customerService.findByPrincipal().getBalance());

		return result;
	}

	protected ModelAndView extractBalanceModelAndView(final BalanceForm balanceForm) {
		return this.extractBalanceModelAndView(balanceForm, null);
	}

	protected ModelAndView extractBalanceModelAndView(final BalanceForm balanceForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("customer/extractBalance");
		result.addObject("balanceForm", balanceForm);
		result.addObject("message", message);
		result.addObject("balanceNow", customerService.findByPrincipal().getBalance());

		return result;
	}
}
