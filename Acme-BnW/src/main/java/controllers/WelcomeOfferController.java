
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.WelcomeOfferService;
import domain.Customer;
import domain.WelcomeOffer;
import forms.WelcomeOfferForm;

@Controller
@RequestMapping(value = "/welcomeOffer")
public class WelcomeOfferController extends AbstractController {

	//Related services

	@Autowired
	private WelcomeOfferService	welcomeOfferService;

	@Autowired
	private CustomerService		customerService;


	//Constructor
	public WelcomeOfferController() {
		super();
	}

	// Show welcome offer options
	@RequestMapping(value = "/showWelcomeOfferOption", method = RequestMethod.GET)
	public ModelAndView welcomeOfferOptions(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		final Customer customer = this.customerService.findByPrincipal();
		result = new ModelAndView("welcomeOffer/showWelcomeOfferOption");
		result.addObject("errorMessage", errorMessage);
		result.addObject("welcomeOffer", customer.getWelcomeOffer());
		result.addObject("optionSelected", customer.getActiveWO());
		result.addObject("RequestURI", "welcomeOffer/showWelcomeOfferOption.do");

		return result;
	}

	// Accept/Decline welcome offer
	@RequestMapping(value = "/welcomeOfferOption", method = RequestMethod.GET)
	public ModelAndView acceptDeclineWO(@RequestParam final String option) {
		ModelAndView result;

		try {
			final Customer customer = this.customerService.findByPrincipal();
			Assert.isTrue(customer.getActiveWO() == null);

			if (option.equals("accept")) {
				customer.setActiveWO(true);
				this.customerService.save(customer);

			} else if (option.equals("decline")) {
				customer.setActiveWO(false);
				this.customerService.save(customer);

			} else {
				throw new IllegalArgumentException("Invalid option");
			}

			result = new ModelAndView("redirect:/welcomeOffer/showWelcomeOfferOption.do");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcomeOffer/showWelcomeOfferOption.do");
			result.addObject("errorMessage", "welcomeOffer.option.error");
		}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;

		final Collection<WelcomeOffer> welcomeOffers = this.welcomeOfferService.findAll();

		result = new ModelAndView("welcomeOffer/list");
		result.addObject("RequestURI", "welcomeOffer/list.do");
		result.addObject("welcomeOffers", welcomeOffers);
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	//Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final WelcomeOfferForm welcomeOfferForm = this.welcomeOfferService.create();

		result = this.createEditModelAndView(welcomeOfferForm);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int welcomeOfferId) {
		ModelAndView result;

		try {
			final WelcomeOffer welcomeOffer = this.welcomeOfferService.findOne(welcomeOfferId);
			final WelcomeOfferForm welcomeOfferForm = this.welcomeOfferService.toFormObject(welcomeOffer);

			result = this.createEditModelAndView(welcomeOfferForm);

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcomeOffer/list.do");
			result.addObject("errorMessage", "welcomeOffer.edit.error");
		}

		return result;
	}

	//Save

	@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final WelcomeOfferForm welcomeOfferForm, final BindingResult binding) {
		ModelAndView result;
		WelcomeOffer welcomeOffer = null;

		try {
			welcomeOffer = this.welcomeOfferService.reconstruct(welcomeOfferForm, binding);

			if (binding.hasErrors()) {
				result = this.createEditModelAndView(welcomeOfferForm);
			} else {
				try {
					if (welcomeOffer == null) {
						welcomeOffer = this.welcomeOfferService.reconstruct(welcomeOfferForm, binding);
					}

					this.welcomeOfferService.save(welcomeOffer);
					result = new ModelAndView("redirect:/welcomeOffer/list.do");

				} catch (final Throwable oops) {
					result = this.createEditModelAndView(welcomeOfferForm, "welcomeOffer.save.error");
				}
			}

		} catch (final Throwable e) {
			result = this.createEditModelAndView(welcomeOfferForm, "welcomeOffer.save.error");
		}

		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int welcomeOfferId) {
		ModelAndView result;

		try {
			final WelcomeOffer welcomeOffer = this.welcomeOfferService.findOne(welcomeOfferId);

			this.welcomeOfferService.delete(welcomeOffer);
			result = new ModelAndView("redirect:/welcomeOffer/list.do");

		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcomeOffer/list.do");
			result.addObject("errorMessage", "welcomeOffer.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createEditModelAndView(final WelcomeOfferForm welcomeOfferForm) {
		return this.createEditModelAndView(welcomeOfferForm, null);
	}

	protected ModelAndView createEditModelAndView(final WelcomeOfferForm welcomeOfferForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("welcomeOffer/create");
		result.addObject("welcomeOfferForm", welcomeOfferForm);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "welcomeOffer/save.do");

		return result;
	}
}
