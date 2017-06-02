
package controllers;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.validator.routines.checkdigit.CheckDigitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Brand;
import domain.Customer;
import forms.BalanceForm;
import forms.CustomerForm;

@RequestMapping(value = "/customer")
@Controller
public class CustomerController extends AbstractController {

	//Related services

	@Autowired
	private CustomerService	customerService;


	//Constructor

	public CustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		final Collection<Customer> customers = this.customerService.findAll();

		result = new ModelAndView("customer/list");
		result.addObject("customers", customers);
		result.addObject("RequestURI", "customer/list.do");
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {

		ModelAndView result;

		final CustomerForm customerForm = this.customerService.createForm();
		result = this.createModelAndView(customerForm);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CustomerForm customerForm, final BindingResult binding) throws CheckDigitException {

		ModelAndView result = new ModelAndView();

		Customer customer;

		try {
			customer = this.customerService.reconstruct(customerForm, binding);

		} catch (final Throwable e) {
			result = this.createModelAndView(customerForm, "customer.creditCard.error");
		}

		if (binding.hasErrors()) {
			result = this.createModelAndView(customerForm, "customer.creditCard.error");
		} else {
			try {
				customer = this.customerService.reconstruct(customerForm, binding);
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/security/login.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(customerForm, "customer.commit.error");
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

		final BalanceForm balanceForm = new BalanceForm();
		result = this.addBalanceModelAndView(balanceForm);

		return result;
	}

	@RequestMapping(value = "extractBalance", method = RequestMethod.GET)
	public ModelAndView extractBalance() {

		ModelAndView result;

		final BalanceForm balanceForm = new BalanceForm();
		result = this.extractBalanceModelAndView(balanceForm);

		return result;
	}

	@RequestMapping(value = "/extractBalance", method = RequestMethod.POST, params = "save")
	public ModelAndView saveExt(@Valid final BalanceForm balanceForm, final BindingResult binding) {

		ModelAndView result = new ModelAndView();
		Locale locale;

		if (binding.hasErrors()) {
			result = this.extractBalanceModelAndView(balanceForm);
		} else {
			try {
				locale = LocaleContextHolder.getLocale();
				this.customerService.extractBalance(balanceForm, locale.getLanguage());
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.extractBalanceModelAndView(balanceForm, "balance.extract.error");
			}
		}

		return result;
	}
	@RequestMapping(value = "/addBalance", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdd(@Valid final BalanceForm balanceForm, final BindingResult binding) {

		ModelAndView result = new ModelAndView();

		if (binding.hasErrors()) {
			result = this.addBalanceModelAndView(balanceForm);
		} else {
			try {
				this.customerService.addBalance(balanceForm);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.addBalanceModelAndView(balanceForm, "balance.add.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/banManagement", method = RequestMethod.GET)
	public ModelAndView banManagement(@RequestParam final int customerId) {
		ModelAndView result;

		try {

			this.customerService.managementBan(customerId);

			result = new ModelAndView("redirect:/customer/list.do");
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/customer/list.do");
			result.addObject("errorMessage", "customer.ban.error");
		}

		return result;
	}

	@RequestMapping(value = "/autoExclusion", method = RequestMethod.GET)
	public ModelAndView autoExclusion() {
		ModelAndView result;

		try {
			this.customerService.autoExclusion();
			result = new ModelAndView("redirect:/j_spring_security_logout");
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
			result.addObject("errorMessage", "customer.ban.error");
		}

		return result;
	}

	@RequestMapping(value = "/ticketList", method = RequestMethod.GET)
	public ModelAndView ticketList() {
		ModelAndView result;
		final Customer customer = this.customerService.findByPrincipal();

		result = new ModelAndView("customer/ticketList");
		result.addObject("tickets", customer.getTickets());
		result.addObject("RequestURI", "customer/ticketList.do");

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final CustomerForm customerForm) {
		return this.createModelAndView(customerForm, null);
	}

	protected ModelAndView createModelAndView(final CustomerForm customerForm, final String message) {
		ModelAndView result;

		final List<Brand> brands = Arrays.asList(Brand.values());
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
		result.addObject("balanceNow", this.customerService.findByPrincipal().getBalance());

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
		result.addObject("balanceNow", this.customerService.findByPrincipal().getBalance());

		return result;
	}
}
