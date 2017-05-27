
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.WelcomeOfferService;
import domain.Customer;

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
}
