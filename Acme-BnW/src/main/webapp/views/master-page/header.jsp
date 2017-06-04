<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page import="security.Authority"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!-- Scriptlets -->
<%@ page import="services.CustomerService, security.LoginService" %>
<%
	if(LoginService.isAuthenticated() && Authority.CUSTOMER.equals(LoginService.getPrincipal().getAuthorities().iterator().next().getAuthority())) {
		Double balance = CustomerService.findStaticByPrincipal().getBalance();
		request.setAttribute("balance", balance);
	}
%>

<!-- Variables -->
<fmt:formatNumber maxFractionDigits="2" minFractionDigits="2" value="${balance}" var="balance"></fmt:formatNumber>

<!-- Scripts -->
<script type="text/javascript" src="scripts/moment-with-locales.js"></script>
<script type="text/javascript" src="scripts/transition.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap-collapse-2.0.4.js"></script>
<script type="text/javascript" src="scripts/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="scripts/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="scripts/bootstrap-datepicker.es.min.js"></script>
<script type="text/javascript" src="scripts/bootbox.min.js"></script>
<script type="text/javascript" src="scripts/acme.js"></script>

<!-- Stylesheets -->
<link rel="stylesheet" type="text/css" href="styles/common.css">
<link rel="stylesheet" type="text/css" href="styles/bootstrap-datetimepicker.css">
<link rel="stylesheet" type="text/css" href="styles/datepicker-standalone.css">

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/"><img style="max-height: 150%;" src="images/logo-bnw.jpg" alt="Acme-BnW Co., Inc." /></a>
		</div>
		<ul class="nav navbar-nav" style="margin:0; padding:0">
			<security:authorize access="hasRole('ADMINISTRATOR')">
				<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><spring:message	code="master.page.administrator.dashboard" /><span class="caret"></span></a>
					<ul class="dropdown-menu inverse-dropdown">
						<li><a href="administrator/dashboardC.do"><spring:message code="master.page.administrator.dashboradC" /></a></li>
						<li><a href="administrator/dashboardB.do"><spring:message code="master.page.administrator.dashboradB" /></a></li>
						<li><a href="administrator/dashboardA.do"><spring:message code="master.page.administrator.dashboradA" /></a></li>
					</ul>
				</li>
				<li><a href="topic/listAll.do"><spring:message code="master.page.list.topic" /></a></li>
				<li><a href="balanceSearchEngine/list.do"><spring:message code="master.page.balanceSearchEngine" /></a></li>
				<li><a href="customer/list.do"><spring:message code="master.page.list.customer" /></a></li>
				<li><a href="actionManager/register.do"><spring:message code="master.page.manager.register" /></a></li>
			</security:authorize>
			<security:authorize access="permitAll">
			<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><spring:message	code="master.page.lists" /><span class="caret"></span></a>
				<ul class="dropdown-menu inverse-dropdown">
					<li><a href="tournament/list.do"><spring:message code="master.page.tournament.list" /></a></li>
					<li><a href="category/list.do"><spring:message code="master.page.category.list" /></a></li>
					<li><a href="fixture/list.do"><spring:message code="master.page.fixture.list" /></a></li>
					<li><a href="match/list.do"><spring:message code="master.page.match.list" /></a></li>
					<li><a href="team/list.do"><spring:message code="master.page.team.list" /></a></li>
				</ul>
			</li>
			<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><spring:message	code="master.page.sports" /><span class="caret"></span></a>
				<ul class="dropdown-menu inverse-dropdown">
					<li><a href="tournament/listOfFootball.do"><spring:message code="master.page.football" /></a></li>
					<li><a href="tournament/listOfBasketball.do"><spring:message code="master.page.basketball" /></a></li>
				</ul>
			</li>
			</security:authorize>
			<security:authorize access="hasRole('CUSTOMER')">
				<li><a href="topic/listAll.do"><spring:message code="master.page.list.topic" /></a></li>
				<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><spring:message	code="master.page.offers" /><span class="caret"></span></a>
					<ul class="dropdown-menu inverse-dropdown">
						<li><a href="welcomeOffer/showWelcomeOfferOption.do"><spring:message code="master.page.welcomeOffer" /></a></li>
						<li><a href="promotion/list.do"><spring:message code="master.page.promotion.list" /></a></li>
						
					</ul>
				</li>
			</security:authorize>
			
			<security:authorize access="hasRole('MANAGER')">
				<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><spring:message	code="master.page.managers" /><span class="caret"></span></a>
					<ul class="dropdown-menu inverse-dropdown">
						<li><a href="market/list.do"><spring:message code="master.page.market.list" /></a></li>
						<li><a href="match/list.do"><spring:message code="master.page.match.list" /></a></li>
						<li><a href="team/list.do"><spring:message code="master.page.team.list" /></a></li>
						<li><a href="tournament/list.do"><spring:message code="master.page.tournament.list" /></a></li>
						<li><a href="category/list.do"><spring:message code="master.page.category.list" /></a></li>
						<li><a href="fixture/list.do"><spring:message code="master.page.fixture.list" /></a></li>
						<li><a href="welcomeOffer/list.do"><spring:message code="master.page.welcomeOffer.list" /></a></li>
						<li><a href="promotion/list.do"><spring:message code="master.page.promotion.list" /></a></li>
					</ul>
				</li>
			</security:authorize>
		</ul>
		
		<div>
			<div class="nav navbar-nav navbar-right" style="padding-top:8px; padding-right:6px; padding-left:6px;">
				<a href="?language=es"><img src="images/es.svg" style="height: 15px;" /></a>
				<span style="border-top: 6px solid #333; height: 1px;display: block;"></span>
				<a href="?language=en"><img src="images/us.svg" style="height: 15px;" /></a>
			</div>
			<security:authorize access="isAnonymous()">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="customer/register.do"><span class="glyphicon glyphicon-user"></span> <spring:message code="master.page.customer.register" /></a></li>
					<li><a href="security/login.do"><span class="glyphicon glyphicon-log-in"></span> <spring:message code="master.page.login" /></a></li>
				</ul>
			</security:authorize>
			<security:authorize access="isAuthenticated()">
				<div class="nav navbar-nav navbar-right">
					<ul class="nav navbar-nav">
						<li class="dropdown"><a class="handCursor dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> <spring:message code="master.page.profile" /> (<security:authentication property="principal.username" />)<span class="caret"></span></a>
							<ul class="dropdown-menu inverse-dropdown">
								<security:authorize access="hasRole('CUSTOMER')">
									<li><a href="bet/history.do"><span class="glyphicon glyphicon-calendar"></span> <spring:message code="master.page.betHistory" /> </a></li>
									<li><a href="bet/pendingBets.do"><span class="glyphicon glyphicon-time"></span> <spring:message code="master.page.pendingBets" /> </a></li>
									<li><a href="bet/showSelection.do"><span class="glyphicon glyphicon-list-alt"></span> <spring:message code="master.page.showSelection" /> </a></li>
									<li role="presentation" class="divider"></li>
									<li><a href="customer/addBalance.do"><span class="glyphicon glyphicon-euro"></span> <spring:message code="master.page.customer.addBalance" /></a></li>
									<li><a href="customer/extractBalance.do"><span class="glyphicon glyphicon-euro"></span> <spring:message code="master.page.customer.extractBalance" /></a></li>
									<li><a href="customer/ticketList.do"> <span class="glyphicon glyphicon-barcode"></span></span><spring:message code="master.page.customer.tickets" /></a></li>
									<li role="presentation" class="divider"></li>
									<li><a href="customer/edit.do"><span class="glyphicon glyphicon-edit"></span> <spring:message code="master.page.edit.user" /></a></li>
								</security:authorize>
								<security:authorize access="hasRole('MANAGER')">
									<li><a href="actionManager/edit.do"><span class="glyphicon glyphicon-edit"></span> <spring:message code="master.page.edit.user" /></a></li>
								</security:authorize>
								<li><a href="j_spring_security_logout"><span class="glyphicon glyphicon-log-out"></span> <spring:message code="master.page.logout" /> </a></li>
							</ul>
						</li>
						
						<security:authorize access="hasRole('CUSTOMER')">
							<li class="dropdown"><a class="dropdown-toggle">${balance}&euro;</a>
						</security:authorize>
					</ul>
				</div>
			</security:authorize>
		</div>
	</div>
</div>

<br/>
<br/>
<br/>
