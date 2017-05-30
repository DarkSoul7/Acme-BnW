<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<fieldset>
	<legend>
		<b><spring:message code="dashboard.stadisticsQuantityForBet" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsQuantityForBet" id="row1"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.statistics" var="statistics" />
		<display:column title="${statistics}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.min" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:when test="${i == 1}">
					<spring:message code="dashboard.max" var="title" />
					<jstl:set var="i" value="2"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.avg" var="title" />
					<jstl:set var="i" value="3"></jstl:set>
				</jstl:otherwise>
			</jstl:choose>
			<fmt:formatNumber var="value" value="${row1}" maxFractionDigits="2"></fmt:formatNumber>
			<b><jstl:out value="${title}:"></jstl:out></b>
			<jstl:out value="${value} ${years}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br />


<fieldset>
	<legend>
		<b><spring:message code="dashboard.stadisticsBetsWonPerClients" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsBetsWonPerClients" id="row2"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.statistics" var="statistics" />
		<display:column title="${statistics}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.min" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:when test="${i == 1}">
					<spring:message code="dashboard.max" var="title" />
					<jstl:set var="i" value="2"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.avg" var="title" />
					<jstl:set var="i" value="3"></jstl:set>
				</jstl:otherwise>
			</jstl:choose>
			<fmt:formatNumber var="value" value="${row2}" maxFractionDigits="2"></fmt:formatNumber>
			<b><jstl:out value="${title}:"></jstl:out></b>
			<jstl:out value="${value} ${years}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.stadisticsBetsLostPerClients" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsBetsLostPerClients" id="row3"
		requestURI="administrator/dashboardC" class="displaytag">
		<spring:message code="dashboard.statistics" var="statistics" />
		<display:column title="${statistics}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.min" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:when test="${i == 1}">
					<spring:message code="dashboard.max" var="title" />
					<jstl:set var="i" value="2"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.avg" var="title" />
					<jstl:set var="i" value="3"></jstl:set>
				</jstl:otherwise>
			</jstl:choose>

			<fmt:formatNumber var="value" value="${row3}" maxFractionDigits="2"></fmt:formatNumber>
			<b><jstl:out value="${title}:"></jstl:out></b>
			<jstl:out value="${value} ${years}"></jstl:out>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.categoryMoreBets" /></b>
	</legend>

	<display:table name="categoryMoreBets" uid="categoryMoreBets"
		requestURI="administrator/dashboardC" pagesize="5">
		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.description" var="description" />
		<display:column property="description" title="${description}" />
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.categoryLessBets" /></b>
	</legend>

	<display:table name="categoryLessBets" uid="categoryLessBets"
		requestURI="administrator/dashboardC" pagesize="5">
		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.description" var="description" />
		<display:column property="description" title="${description}" />
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.sportMoreBets" /></b>
	</legend>

	<display:table name="sportMoreBets" uid="sportMoreBets"
		requestURI="administrator/dashboardC" pagesize="5" id="row4">
		<spring:message code="dashboard.name" var="name" />
		<display:column title="${name}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<jstl:out value="${row4.getName()}"></jstl:out>
				</jstl:when>
				<jstl:otherwise>
					<jstl:out value="${row4.getSpanishName()}"></jstl:out>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.sportLessBets" /></b>
	</legend>

	<display:table name="sportLessBets" uid="sportLessBets"
		requestURI="administrator/dashboardC" pagesize="5" id="row5">
		<spring:message code="dashboard.name" var="name" />
		<display:column title="${name}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<jstl:out value="${row5.getName()}"></jstl:out>
				</jstl:when>
				<jstl:otherwise>
					<jstl:out value="${row5.getSpanishName()}"></jstl:out>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.betsByLocalTeam" /></b>
	</legend>

	<display:table name="${teamStatisticsBets[0]}" uid="betsByLocalTeam"
		requestURI="administrator/dashboardC.do" class="displaytag" id="row6"
		pagesize="5">

		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.shield" var="shield" />
		<display:column title="${shield}">
			<img src="${row6.shield}" alt="shield" width="70" height="70">
		</display:column>
	</display:table>
</fieldset>

<br />

<fieldset>
	<legend>
		<b><spring:message code="dashboard.betsByVisitorTeam" /></b>
	</legend>
	
	<display:table name="${teamStatisticsBets[1]}" uid="betsByVisitorTeam"
		requestURI="administrator/dashboardC.do" class="displaytag" id="row7"
		pagesize="5">

		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.shield" var="shield" />
		<display:column title="${shield}">
			<img src="${row7.shield}" alt="shield" width="70" height="70">
		</display:column>
	</display:table>
</fieldset>

<acme:cancel url="" code="dasboard.back" />