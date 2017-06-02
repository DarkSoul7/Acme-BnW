/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.MarketService;
import services.PromotionService;
import services.WelcomeOfferService;
import domain.Actor;
import domain.Market;
import domain.Promotion;
import domain.WelcomeOffer;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Related services -------------------------------------------------------

	@Autowired
	private ActorService		actorService;

	@Autowired
	private WelcomeOfferService	welcomeOfferService;

	@Autowired
	private MarketService		marketService;

	@Autowired
	private PromotionService	promotionService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------		

	@RequestMapping(value = "/index")
	public ModelAndView index() {
		ModelAndView result;
		String fullName;
		Actor principal;
		WelcomeOffer active;
		Collection<Market> notedMarkets = null;
		Collection<Promotion> favouritePromotions = null;

		if (LoginService.isAuthenticated()) {
			principal = this.actorService.findByPrincipal();
			fullName = principal.getName() + " " + principal.getSurname();

			final Actor actor = this.actorService.findByPrincipal();
			if (actor.getUserAccount().getAuthorities().iterator().next().getAuthority().equals("CUSTOMER")) {
				notedMarkets = this.marketService.getMarkedMarket();
				favouritePromotions = this.promotionService.getAllPromotionsCustomizedByCustomer();
			}

		} else
			fullName = null;

		active = this.welcomeOfferService.getActive();

		result = new ModelAndView("welcome/index");
		result.addObject("fullName", fullName);
		result.addObject("activeOffer", active);
		result.addObject("notedMarkets", notedMarkets);
		result.addObject("favouritePromotions", favouritePromotions);
		result.addObject("currentMoment", new Date());
		result.addObject("RequestURI", "welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {

		ModelAndView result;

		result = new ModelAndView("welcome/cookies");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/conditions")
	public ModelAndView conditions() {
		ModelAndView result;

		result = new ModelAndView("welcome/conditions");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}

	@RequestMapping(value = "/eraseMe")
	public ModelAndView eraseMe() {
		ModelAndView result;

		result = new ModelAndView("welcome/eraseMe");
		result.addObject("backURI", "/welcome/index.do");

		return result;
	}
}
