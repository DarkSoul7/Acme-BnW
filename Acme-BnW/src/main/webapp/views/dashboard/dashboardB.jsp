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
		<b><spring:message
				code="dashboard.autoExclusionNumberAndBanNumber" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="autoExclusionAndBanNumber" id="row1"
		requestURI="administrator/dashboardB.do" class="displaytag">
		<spring:message code="dashboard.autoExclusionNumberAndBanNumber"
			var="autoExclusionNumberAndBanNumber" />
		<display:column title="${autoExclusionNumberAndBanNumber}">
			<jstl:choose>
				<jstl:when test="${i == 0}">
					<spring:message code="dashboard.autoExclusionNumber" var="title" />
					<jstl:set var="i" value="1"></jstl:set>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="dashboard.banNumber" var="title" />
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
		<b><spring:message code="dashboard.stadisticsTopicsByCustomer" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsTopicsByCustomer" id="row1"
		requestURI="administrator/dashboardB.do" class="displaytag">
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
		<b><spring:message code="dashboard.stadisticsMessagesByCustomer" /></b>
	</legend>

	<jstl:set var="i" value="0"></jstl:set>
	<display:table name="stadisticsMessagesByCustomer" id="row2"
		requestURI="administrator/dashboardC.do" class="displaytag">
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