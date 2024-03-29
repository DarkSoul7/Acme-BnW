<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:if test="${fullName == null}">
	<jstl:set var="fullName"><spring:message code="welcome.anonymousUser" /></jstl:set>
</jstl:if>

<p><spring:message code="welcome.greeting.prefix" /> ${fullName}<spring:message code="welcome.greeting.suffix" /></p>

<p><spring:message code="welcome.welcome" /></p>

<br/>

<jstl:if test="${activeOffer != null}">
	<div style="border-style: solid; border-color: red; display: inline-block; height: auto;">
		<spring:message code="welcome.welcomeOffer" var="welcomeOffer" />
		<h3 style="font-weight:bold; font-style:italic;">
			<jstl:out value="${welcomeOffer}" /></h3>
		<div style="display: inline-block; height: auto; background-color: black; color:white">
			<spring:message code="welcomeOffer.title" var="title2" />
			<jstl:out value="${activeOffer.title}"/>
			<br/>
			<spring:message code="welcome.valid" var="valid" /> <!-- valid : valid from -->
			<spring:message code="welcome.to" var="to" /> <!-- to: to-->
			
			<jstl:out value="${valid} "/>
			<fmt:formatDate value="${activeOffer.openPeriod}" pattern="MM/dd/yyyy" />
			<jstl:out value="${to} "/>
			<fmt:formatDate value="${activeOffer.endPeriod}" pattern="MM/dd/yyyy" />
			<br/>
			<spring:message code="welcome.win" var="win" /> <!-- win: win -->
			<spring:message code="welcome.deposit" var="deposit" /> <!-- deposit: if you deposit -->
			<jstl:out value="${win} ${activeOffer.amount}" />&euro; <jstl:out value="${deposit} ${activeOffer.extractionAmount}"/>&euro;
		</div>
		
	</div>
</jstl:if>
	<br/>
	<br/>
<spring:message code="welcome.conersionTable" var="conersionTable" />
	<h3><jstl:out value="${conersionTable}"/></h3>
<display:table name="convertionCurrencies" uid="convertionCurrencies" requestURI="${RequestURI}" pagesize="5">

		<spring:message code="currency.currencyType" var="currencyType" />
		<display:column property="currency.constant" title="${currencyType}"/>
		
		<spring:message code="currency.convertionAmount" var="convertionAmount" />
		<display:column property="convertionAmount" title="${convertionAmount}"/>
		
</display:table>
	
<security:authorize access="hasRole('CUSTOMER')"> 
	<br/>
	<spring:message code="welcome.notedMarkets" var="markedMarkets" />
	<h3><jstl:out value="${markedMarkets}"/></h3>
	
	<jstl:if test="${fn:length(notedMarkets) > 0}">
		<display:table name="notedMarkets" uid="notedMarkets" requestURI="${RequestURI}" pagesize="5">
	
			<spring:message code="match.localTeam" var="localTeam" />
			<display:column property="match.localTeam.name" title="${localTeam}"/>
			
			<spring:message code="match.visitorTeam" var="visitorTeam" />
			<display:column property="match.visitorTeam.name" title="${visitorTeam}"/>
			
			<spring:message code="welcome.title" var="title" />
			<display:column title="${title}">
				<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<jstl:out value="${notedMarkets.type.getName()}"></jstl:out>
				</jstl:when>
				<jstl:otherwise>
					<jstl:out value="${notedMarkets.type.getSpanishName()}"></jstl:out>
				</jstl:otherwise>
			</jstl:choose>
			</display:column>
			
			<jstl:set var="validPromotion" value="${notedMarkets.promotion != null && !notedMarkets.promotion.cancel && notedMarkets.promotion.startMoment < currentMoment && notedMarkets.promotion.endMoment > currentMoment}" />
			<spring:message code="market.fee" var="fee" />
			<display:column style="${validPromotion? 'text-decoration:line-through;':''}" property="fee" title="${fee}"/>
			
			<spring:message code="promotion.fee" var="promotionFee" />
			<display:column title="${promotionFee}">
				<jstl:if test="${validPromotion}">
					<jstl:out value="${notedMarkets.promotion.fee}" />
				</jstl:if>
			</display:column>
			
			<spring:message code="match.startMoment" var="startMoment" />
			<display:column title="${startMoment}">
				<fmt:formatDate value="${notedMarkets.match.startMoment}" pattern="MM/dd/yyyy HH:mm" />
			</display:column>
			
			<spring:message code="match.endMoment" var="endMoment" />
			<display:column title="${endMoment}">
				<fmt:formatDate value="${notedMarkets.match.endMoment}" pattern="MM/dd/yyyy HH:mm" />
			</display:column>
			
			<display:column>
				<acme:cancel code="welcome.goToMarket" url="market/listByMatch.do?matchId=${notedMarkets.match.id}" />
			</display:column>
		
		</display:table>
		
		<br/>
	</jstl:if>
	
	<spring:message code="welcome.favouriteTeamPromotions" var="favouriteTeamPromotions" />
	<h3><jstl:out value="${favouriteTeamPromotions }"/></h3>
	
	<display:table name="favouritePromotions" uid="favouritePromotions" requestURI="${RequestURI}" pagesize="5">
	
		<spring:message code="promotion.title" var="title" />
		<display:column property="title" title="${title}" />
		
		<spring:message code="promotion.description" var="description" />
		<display:column property="description" title="${description}" />
		
		<spring:message code="match.localTeam" var="localTeam" />
		<display:column property="market.match.localTeam.name" title="${localTeam}"/>
		
		<spring:message code="match.visitorTeam" var="visitorTeam" />
		<display:column property="market.match.visitorTeam.name" title="${visitorTeam}"/>
		
		<spring:message code="welcome.title" var="wTitle" />
		<display:column title="${wTitle}">
			<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${favouritePromotions.market.type.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${favouritePromotions.market.type.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
		</display:column>
		
		<spring:message code="promotion.market.fee" var="marketFee" />
		<display:column style="text-decoration:line-through;" property="market.fee" title="${marketFee}" />
		
		<spring:message code="promotion.fee" var="fee" />
		<display:column property="fee" title="${fee}" />
	
		<spring:message code="promotion.startMoment" var="startMoment" />
		<display:column title="${startMoment}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${favouritePromotions.startMoment}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${favouritePromotions.startMoment}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="promotion.endMoment" var="endMoment" />
		<display:column title="${endMoment}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${favouritePromotions.endMoment}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${favouritePromotions.endMoment}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
			
		<display:column>
			<acme:cancel code="welcome.goToMarket" url="market/list.do?marketId=${favouritePromotions.market.id}" />
		</display:column>
	
	</display:table>
	
	
</security:authorize>
