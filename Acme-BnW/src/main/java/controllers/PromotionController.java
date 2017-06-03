package controllers;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.PromotionService;
import domain.Promotion;
import forms.PromotionForm;

@RequestMapping(value = "/promotion")
@Controller
public class PromotionController extends AbstractController {

	//Related services

	@Autowired
	private PromotionService	promotionService;


	//Constructor

	public PromotionController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) String errorMessage, @RequestParam(required = false) String successMessage) {
		ModelAndView result;
		Collection<Promotion> promotions;
		Date currentMoment;

		if (LoginService.isAuthenticated() && Authority.MANAGER.equals(LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority())) {
			promotions = this.promotionService.findAll();
		} else {
			promotions = this.promotionService.findAllNotCancelled();
		}
		currentMoment = new Date();

		result = new ModelAndView("promotion/list");
		result.addObject("promotions", promotions);
		result.addObject("currentMoment", currentMoment);
		result.addObject("requestURI", "promotion/list.do");
		result.addObject("errorMessage", errorMessage);
		result.addObject("successMessage", successMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam int marketId) {
		ModelAndView result = new ModelAndView();

		PromotionForm promotionForm = this.promotionService.create();
		promotionForm.setIdMarket(marketId);
		result = this.createModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int promotionId) {
		ModelAndView result = new ModelAndView();

		Promotion promotion = this.promotionService.findOne(promotionId);
		PromotionForm promotionForm = this.promotionService.toFormObject(promotion);
		result = this.editModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PromotionForm promotionForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();
		Boolean invalidDates = false;
		Boolean incorrectMarket = false;

		promotion = this.promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(promotionForm);
		} else {
			try {
				Date now = new Date();
				if (promotion.getStartMoment().before(now) || promotion.getEndMoment().before(promotion.getStartMoment())) {
					invalidDates = true;
					throw new IllegalArgumentException();
				}

				if (promotion.getMarket().getMatch().getEndMoment().before(now)) {
					incorrectMarket = true;
					throw new IllegalArgumentException();
				}
				this.promotionService.save(promotion);
				result = new ModelAndView("redirect:/promotion/list.do");
				result.addObject("successMessage", "promotion.register.success");
			} catch (Throwable oops) {
				if (invalidDates == true) {
					result = this.createModelAndView(promotionForm, "promotion.dates.error");
				} else if (incorrectMarket == true) {
					result = this.createModelAndView(promotionForm, "promotion.market.error");
				} else {
					result = this.createModelAndView(promotionForm, "promotion.register.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid PromotionForm promotionForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();
		Boolean invalidDates = false;
		Boolean incorrectMarket = false;

		promotion = this.promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.editModelAndView(promotionForm);
		} else {
			try {
				Date now = new Date();
				if (promotion.getStartMoment().before(now) || promotion.getEndMoment().before(promotion.getStartMoment())) {
					invalidDates = true;
					throw new IllegalArgumentException();
				}

				if (promotion.getMarket().getMatch().getEndMoment().before(now)) {
					incorrectMarket = true;
					throw new IllegalArgumentException();
				}
				this.promotionService.save(promotion);
				result = new ModelAndView("redirect:/promotion/list.do");
				result.addObject("successMessage", "promotion.edit.success");
			} catch (Throwable oops) {
				if (invalidDates == true) {
					result = this.createModelAndView(promotionForm, "promotion.dates.error");
				} else if (incorrectMarket == true) {
					result = this.createModelAndView(promotionForm, "promotion.market.error");
				} else {
					result = this.createModelAndView(promotionForm, "promotion.edit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int promotionId) {
		ModelAndView result = new ModelAndView();

		Promotion promotion = this.promotionService.findOne(promotionId);
		try {
			this.promotionService.cancel(promotion);
			result = new ModelAndView("redirect:/promotion/list.do");
			result.addObject("successMessage", "promotion.cancel.success");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/promotion/list.do");
			result.addObject("errorMessage", "promotion.cancel.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(PromotionForm promotionForm) {
		return this.createModelAndView(promotionForm, null);
	}

	protected ModelAndView createModelAndView(PromotionForm promotionForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("promotion/register");
		result.addObject("promotionForm", promotionForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "promotion/register.do");

		return result;
	}

	protected ModelAndView editModelAndView(PromotionForm promotionForm) {
		return this.editModelAndView(promotionForm, null);
	}

	protected ModelAndView editModelAndView(PromotionForm promotionForm, String errorMessage) {
		ModelAndView result;

		result = new ModelAndView("promotion/edit");
		result.addObject("promotionForm", promotionForm);
		result.addObject("errorMessage", errorMessage);
		result.addObject("RequestURI", "promotion/edit.do");

		return result;
	}

}
