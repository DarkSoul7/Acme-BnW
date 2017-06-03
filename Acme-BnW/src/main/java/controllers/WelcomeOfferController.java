package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	public ModelAndView welcomeOfferOptions(@RequestParam(required = false) String errorMessage) {
		ModelAndView result;
		Collection<WelcomeOffer> welcomeOffers;
		Customer principal;

		welcomeOffers = this.welcomeOfferService.findAll();
		principal = this.customerService.findByPrincipal();

		result = new ModelAndView("welcomeOffer/showWelcomeOfferOption");
		result.addObject("welcomeOffers", welcomeOffers);
		result.addObject("activeWelcomeOffer", principal.getWelcomeOffer());
		result.addObject("activeWO", principal.getActiveWO());
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "welcomeOffer/showWelcomeOfferOption.do");

		return result;
	}

	// Decline welcome offer
	@RequestMapping(value = "/cancelWelcomeOffer", method = RequestMethod.GET)
	public ModelAndView acceptDeclineWO() {
		ModelAndView result;

		try {
			this.customerService.cancelOffer();

			result = new ModelAndView("redirect:/welcomeOffer/showWelcomeOfferOption.do");

		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcomeOffer/showWelcomeOfferOption.do");
			result.addObject("errorMessage", "welcomeOffer.option.error");
		}
		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;

		Collection<WelcomeOffer> welcomeOffers = this.welcomeOfferService.findAll();

		result = new ModelAndView("welcomeOffer/list");
		result.addObject("RequestURI", "welcomeOffer/list.do");
		result.addObject("welcomeOffers", welcomeOffers);
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		WelcomeOfferForm welcomeOfferForm = this.welcomeOfferService.create();
		result = this.createModelAndView(welcomeOfferForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int welcomeOfferId) {
		ModelAndView result = new ModelAndView();

		WelcomeOffer welcomeOffer = this.welcomeOfferService.findOne(welcomeOfferId);
		WelcomeOfferForm welcomeOfferForm = this.welcomeOfferService.toFormObject(welcomeOffer);
		result = this.editModelAndView(welcomeOfferForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WelcomeOfferForm welcomeOfferForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		WelcomeOffer welcomeOffer = new WelcomeOffer();

		try {
			welcomeOffer = this.welcomeOfferService.reconstruct(welcomeOfferForm, binding);
			if (binding.hasErrors()) {
				result = this.createModelAndView(welcomeOfferForm);
			} else {
				try {
					this.welcomeOfferService.save(welcomeOffer);
					result = new ModelAndView("redirect:/welcomeOffer/list.do");
					result.addObject("successMessage", "welcomeOffer.save.success");
				} catch (Throwable oops) {
					result = this.createModelAndView(welcomeOfferForm, "welcomeOffer.save.error");
				}
			}
		} catch (Throwable e) {
			result = this.createModelAndView(welcomeOfferForm, "welcomeOffer.register.error");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid WelcomeOfferForm welcomeOfferForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		WelcomeOffer welcomeOffer = new WelcomeOffer();

		try {
			welcomeOffer = this.welcomeOfferService.reconstruct(welcomeOfferForm, binding);
			if (binding.hasErrors()) {
				result = this.editModelAndView(welcomeOfferForm);
			} else {
				try {
					this.welcomeOfferService.save(welcomeOffer);
					result = new ModelAndView("redirect:/welcomeOffer/list.do");
					result.addObject("successMessage", "welcomeOffer.edit.success");
				} catch (Throwable oops) {
					result = this.editModelAndView(welcomeOfferForm, "welcomeOffer.edit.error");
				}
			}
		} catch (Throwable e) {
			result = this.editModelAndView(welcomeOfferForm, "welcomeOffer.edit.error");
		}
		return result;
	}

	//Delete

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int welcomeOfferId) {
		ModelAndView result;

		try {
			WelcomeOffer welcomeOffer = this.welcomeOfferService.findOne(welcomeOfferId);

			this.welcomeOfferService.delete(welcomeOffer);
			result = new ModelAndView("redirect:/welcomeOffer/list.do");
			result.addObject("successMessage", "welcomeOffer.delete.success");

		} catch (Throwable e) {
			result = new ModelAndView("redirect:/welcomeOffer/list.do");
			result.addObject("errorMessage", "welcomeOffer.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(WelcomeOfferForm welcomeOfferForm) {
		return this.createModelAndView(welcomeOfferForm, null);
	}

	protected ModelAndView createModelAndView(WelcomeOfferForm welcomeOfferForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("welcomeOffer/create");
		result.addObject("welcomeOfferForm", welcomeOfferForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "welcomeOffer/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(WelcomeOfferForm welcomeOfferForm) {
		return this.editModelAndView(welcomeOfferForm, null);
	}

	protected ModelAndView editModelAndView(WelcomeOfferForm welcomeOfferForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("welcomeOffer/edit");
		result.addObject("welcomeOfferForm", welcomeOfferForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "welcomeOffer/edit.do");

		return result;
	}

}
