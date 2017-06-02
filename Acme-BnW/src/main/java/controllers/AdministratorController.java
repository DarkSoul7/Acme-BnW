/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BetService;
import services.CategoryService;
import services.CustomerService;
import services.MessageService;
import services.TeamService;
import services.TopicService;
import services.TournamentService;
import domain.Category;
import domain.Customer;
import domain.Sport;
import domain.Team;

@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Related services

	// @Autowired
	// private AdministratorService administratorService;

	@Autowired
	private BetService			betService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private TeamService			teamService;

	@Autowired
	private TournamentService	tournamentService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TopicService		topicService;

	@Autowired
	private MessageService		messageService;


	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Dashboard C

	@RequestMapping(value = "/dashboardC", method = RequestMethod.GET)
	public ModelAndView dashboardC() {
		final ModelAndView result;

		final Double maxQuantityForBet = betService.maxQuantityForBet();
		final Double minQuantityForBet = betService.minQuantityForBet();
		final Double avgQuantityForBet = betService.avgQuantityForBet();
		final Object[] stadisticsQuantityForBet = {minQuantityForBet, maxQuantityForBet, avgQuantityForBet};

		final Long maxBetsWonPerClients = betService.maxBetsWonPerClients();
		final Long minBetsWonPerClients = betService.minBetsWonPerClients();
		final Double avgWonBetsPerClients = betService.avgWonBetsPerClients();
		final Object[] stadisticsBetsWonPerClients = {minBetsWonPerClients, maxBetsWonPerClients,
				avgWonBetsPerClients};

		final Long maxBetsLostPerClients = betService.maxBetsLostPerClients();
		final Long minBetsLostPerClients = betService.minBetsLostPerClients();
		final Double avgLostBetsPerClients = betService.avgLostBetsPerClients();
		final Object[] stadisticsBetsLostPerClients = {minBetsLostPerClients, maxBetsLostPerClients,
				avgLostBetsPerClients};

		Collection<Category> categoryMoreBets = categoryService.categoryMoreBets();
		Collection<Category> categoryLessBets = categoryService.categoryLessBets();

		Collection<Sport> sportMoreBets = tournamentService.SportMoreBets();
		Collection<Sport> sportLessBets = tournamentService.SportMoreBets();

		List<List<Team>> teamStatisticsBets = teamService.teamStatisticsBets();

		result = new ModelAndView("administrator/dashboardC");
		result.addObject("stadisticsQuantityForBet", stadisticsQuantityForBet);
		result.addObject("stadisticsBetsWonPerClients", stadisticsBetsWonPerClients);
		result.addObject("stadisticsBetsLostPerClients", stadisticsBetsLostPerClients);
		result.addObject("categoryMoreBets", categoryMoreBets);
		result.addObject("categoryLessBets", categoryLessBets);
		result.addObject("sportMoreBets", sportMoreBets);
		result.addObject("sportLessBets", sportLessBets);
		result.addObject("teamStatisticsBets", teamStatisticsBets);
		result.addObject("requestURI", "administrator/dashboardC.do");

		return result;

	}

	// Dashboard B
	@RequestMapping(value = "/dashboardB", method = RequestMethod.GET)
	public ModelAndView dashboardB() {
		final ModelAndView result;

		Integer autoExclusionNumber = customerService.getAutoExclusionNumber();
		Integer banNumber = customerService.getBanNumber();
		final Object[] autoExclusionAndBanNumber = {autoExclusionNumber, banNumber};

		final Integer maxTopicsByCustomer = topicService.getMaxTopicsByCustomer();
		final Integer minTopicsByCustomer = topicService.getMinTopicsByCustomer();
		final Double topicAvgByCustomers = topicService.getTopicAvgByCustomers();
		final Object[] stadisticsTopicsByCustomer = {minTopicsByCustomer, maxTopicsByCustomer, topicAvgByCustomers};

		final Integer maxMessagesByCustomer = topicService.getMaxMessagesByCustomer();
		final Integer minMessagesByCustomer = topicService.getMinMessagesByCustomer();
		final Double messageAvgByCustomers = topicService.getMessageAvgByCustomers();
		final Object[] stadisticsMessagesByCustomer = {minMessagesByCustomer, maxMessagesByCustomer,
				messageAvgByCustomers};

		result = new ModelAndView("administrator/dashboardB");
		result.addObject("autoExclusionAndBanNumber", autoExclusionAndBanNumber);
		result.addObject("stadisticsTopicsByCustomer", stadisticsTopicsByCustomer);
		result.addObject("stadisticsMessagesByCustomer", stadisticsMessagesByCustomer);
		result.addObject("requestURI", "administrator/dashboardB.do");

		return result;
	}

	// Dashboard A

	@RequestMapping(value = "/dashboardA", method = RequestMethod.GET)
	public ModelAndView dashboardA() {
		final ModelAndView result;

		Double avgFavouritTeamPerCustomer = teamService.avgFavouritTeamPerCustomer();
		Double avgMessagesPerTopic = messageService.avgMessagesPerTopic();
		Collection<Customer> customerWithMoreMessages = customerService.customerWithMoreMessages();
		Collection<Customer> customersWhoJoinedMorePromotion = customerService.getCustomersWhoJoinedMorePromotion();

		result = new ModelAndView("administrator/dashboardA");
		result.addObject("avgFavouritTeamPerCustomer", avgFavouritTeamPerCustomer);
		result.addObject("avgMessagesPerTopic", avgMessagesPerTopic);
		result.addObject("customerWithMoreMessages", customerWithMoreMessages);
		result.addObject("customersWhoJoinedMorePromotion", customersWhoJoinedMorePromotion);
		result.addObject("requestURI", "administrator/dashboardA.do");

		return result;

	}
}
