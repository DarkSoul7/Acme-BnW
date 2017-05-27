
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

import domain.Market;
import domain.Promotion;
import forms.PromotionForm;
import services.MarketService;
import services.PromotionService;

@RequestMapping(value = "/promotion")
@Controller
public class PromotionController extends AbstractController {

	//Related services

	@Autowired
	private PromotionService	promotionService;

	@Autowired
	private MarketService		marketService;


	//Constructor

	public PromotionController() {
		super();
	}

	//Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Promotion> promotions;

		promotions = promotionService.findAll();

		result = new ModelAndView("promotion/list");
		result.addObject("promotions", promotions);
		result.addObject("requestURI", "promotion/list.do");

		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		ModelAndView result = new ModelAndView();

		PromotionForm promotionForm = promotionService.create();
		result = this.createModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int promotionId) {
		ModelAndView result = new ModelAndView();

		Promotion promotion = promotionService.findOne(promotionId);
		PromotionForm promotionForm = promotionService.toFormObject(promotion);
		result = this.createEditModelAndView(promotionForm);
		return result;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PromotionForm promotionForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();

		promotion = promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.createModelAndView(promotionForm);
		} else {
			try {
				promotionService.save(promotion);
				result = new ModelAndView("redirect:/promotion/list.do");
			} catch (Throwable oops) {
				result = this.createModelAndView(promotionForm, "promotion.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid PromotionForm promotionForm, BindingResult binding) {
		ModelAndView result = new ModelAndView();
		Promotion promotion = new Promotion();

		promotion = promotionService.reconstruct(promotionForm, binding);
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(promotionForm);
		} else {
			try {
				promotionService.save(promotion);
				result = new ModelAndView("redirect:/promotion/list.do");
			} catch (Throwable oops) {
				result = this.createEditModelAndView(promotionForm, "promotion.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int promotionId) {
		ModelAndView result = new ModelAndView();

		Promotion promotion = promotionService.findOne(promotionId);
		try {
			promotionService.delete(promotion);
			result = new ModelAndView("redirect:/promotion/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/promotion/list.do");
			result.addObject("errorMessage", "promotion.delete.error");
		}

		return result;
	}

	//Ancillary methods

	protected ModelAndView createModelAndView(final PromotionForm promotionForm) {
		return this.createModelAndView(promotionForm, null);
	}

	protected ModelAndView createModelAndView(final PromotionForm promotionForm, final String message) {
		ModelAndView result;

		Collection<Market> markets = marketService.findAll();
		result = new ModelAndView("promotion/register");
		result.addObject("promotionForm", promotionForm);
		result.addObject("message", message);
		result.addObject("markets", markets);
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
		result.addObject("message", message);
		result.addObject("RequestURI", "promotion/edit.do");

		return result;
	}

}
