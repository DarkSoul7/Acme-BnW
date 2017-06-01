
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
	public ModelAndView list(@RequestParam(required = false) final String errorMessage) {
		ModelAndView result;
		Collection<Promotion> promotions;

		promotions = this.promotionService.findAll();

		result = new ModelAndView("promotion/list");
		result.addObject("promotions", promotions);
		result.addObject("requestURI", "promotion/list.do");
		result.addObject("errorMessage", errorMessage);

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int marketId) {
		ModelAndView result = new ModelAndView();

		final PromotionForm promotionForm = this.promotionService.create();
		promotionForm.setIdMarket(marketId);
		result = this.createModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int promotionId) {
		ModelAndView result = new ModelAndView();

		final Promotion promotion = this.promotionService.findOne(promotionId);
		final PromotionForm promotionForm = this.promotionService.toFormObject(promotion);
		result = this.createEditModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PromotionForm promotionForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();
		Boolean invalidDates = false;
		Boolean incorrectMarket = false;

		promotion = this.promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(promotionForm);
		} else {
			try {
				final Date now = new Date();
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
			} catch (final Throwable oops) {
				if (invalidDates == true) {
					result = this.createModelAndView(promotionForm, "promotion.dates.error");
				} else if (incorrectMarket == true) {
					result = this.createModelAndView(promotionForm, "promotion.market.error");
				} else {
					result = this.createModelAndView(promotionForm, "promotion.commit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final PromotionForm promotionForm, final BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();
		Boolean invalidDates = false;
		Boolean incorrectMarket = false;

		promotion = this.promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(promotionForm);
		} else {
			try {
				final Date now = new Date();
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
			} catch (final Throwable oops) {
				if (invalidDates == true) {
					result = this.createModelAndView(promotionForm, "promotion.dates.error");
				} else if (incorrectMarket == true) {
					result = this.createModelAndView(promotionForm, "promotion.market.error");
				} else {
					result = this.createModelAndView(promotionForm, "promotion.commit.error");
				}
			}
		}

		return result;
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int promotionId) {
		ModelAndView result = new ModelAndView();

		final Promotion promotion = this.promotionService.findOne(promotionId);
		try {
			this.promotionService.cancel(promotion);
			result = new ModelAndView("redirect:/promotion/list.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/promotion/list.do");
			result.addObject("errorMessage", "promotion.cancel.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final PromotionForm promotionForm) {
		return this.createModelAndView(promotionForm, null);
	}

	protected ModelAndView createModelAndView(final PromotionForm promotionForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("promotion/register");
		result.addObject("promotionForm", promotionForm);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "promotion/register.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final PromotionForm promotionForm) {
		return this.createEditModelAndView(promotionForm, null);
	}

	protected ModelAndView createEditModelAndView(final PromotionForm promotionForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("promotion/edit");
		result.addObject("promotionForm", promotionForm);
		result.addObject("errorMessage", message);
		result.addObject("RequestURI", "promotion/edit.do");

		return result;
	}

}
