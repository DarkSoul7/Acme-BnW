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
			<spring:message code="welcome.virtualCredit" var="virtualCredit" /> <!-- win: win -->
			<spring:message code="welcome.deposit" var="deposit" /> <!-- deposit: if you deposit -->
			<jstl:out value="${win} ${activeOffer.amount} (${virtualCredit }) ${deposit} ${activeOffer.extractionAmount} (${virtualCredit })"/>
		</div>
		
	</div>
</jstl:if>
	
<security:authorize access="hasRole('CUSTOMER')"> 
	<br/>
	<br/>
	<spring:message code="welcome.notedMarkets" var="markedMarkets" />
	<h3><jstl:out value="markedMarkets"/></h3>
	<br/>
	<display:table name="notedMarkets" id="row" requestURI="${RequestURI}"
	pagesize="5">

		<spring:message code="market.title" var="title" />
		<display:column property="title" title="${title}"/>
		
		<spring:message code="market.fee" var="fee" />
		<display:column property="fee" title="${fee}"/>
		
		<spring:message code="match.startMoment" var="startMoment" />
		<display:column title="${startMoment}">
			<fmt:formatDate value="${row.match.startMoment}" pattern="MM/dd/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="match.endMoment" var="endMoment" />
		<display:column title="${endMoment}">
			<fmt:formatDate value="${row.match.endMoment}" pattern="MM/dd/yyyy HH:mm" />
		</display:column>
		
		<spring:message code="match.localTeam" var="localTeam" />
		<display:column property="match.localTeam" title="${localTeam}"/>
		
		<spring:message code="match.visitorTeam" var="visitorTeam" />
		<display:column property="match.visitorTeam" title="${visitorTeam}"/>
	
	</display:table>
	
</security:authorize>
