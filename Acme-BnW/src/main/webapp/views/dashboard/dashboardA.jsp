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
		<b><spring:message code="dashboard.avgFavouritTeamPerCustomer" /></b>
	</legend>
	<spring:message code="dashboard.avg" var="avg" />
	<b><jstl:out value="${avg}:"></jstl:out></b>
	<jstl:out value="${avgFavouritTeamPerCustomer}"></jstl:out>
</fieldset>

<fieldset>
	<legend>
		<b><spring:message code="dashboard.avgMessagesPerTopic" /></b>
	</legend>

	<spring:message code="dashboard.avg" var="avg" />
	<b><jstl:out value="${avg}:"></jstl:out></b>
	<jstl:out value="${avgMessagesPerTopic}"></jstl:out>
</fieldset>

<fieldset>
	<legend>
		<b><spring:message code="dashboard.customerWithMoreMessages" /></b>
	</legend>

	<display:table name="customerWithMoreMessages" uid="customerWithMoreMessages"
		requestURI="${requestURI}" pagesize="5">

		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.surname" var="surname" />
		<display:column property="surname" title="${surname}" />

		<spring:message code="dashboard.email" var="email" />
		<display:column property="email" title="${email}" />

		<spring:message code="dashboard.phone" var="phone" />
		<display:column property="phone" title="${phone}" />

		<spring:message code="dashboard.nid" var="nid" />
		<display:column property="nid" title="${nid}" />

		<spring:message code="dashboard.birthDate" var="birthDate" />
		<display:column title="${birthDate}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${customerWithMoreMessages.birthDate}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${customerWithMoreMessages.birthDate}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
</fieldset>

<fieldset>
	<legend>
		<b><spring:message code="dashboard.customersWhoJoinedMorePromotion" /></b>
	</legend>
	<display:table name="customersWhoJoinedMorePromotion" uid="customersWhoJoinedMorePromotion"
		requestURI="${requestURI}" pagesize="5">

		<spring:message code="dashboard.name" var="name" />
		<display:column property="name" title="${name}" />

		<spring:message code="dashboard.surname" var="surname" />
		<display:column property="surname" title="${surname}" />

		<spring:message code="dashboard.email" var="email" />
		<display:column property="email" title="${email}" />

		<spring:message code="dashboard.phone" var="phone" />
		<display:column property="phone" title="${phone}" />

		<spring:message code="dashboard.nid" var="nid" />
		<display:column property="nid" title="${nid}" />

		<spring:message code="dashboard.birthDate" var="birthDate" />
		<display:column title="${birthDate}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${customersWhoJoinedMorePromotion.birthDate}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${customersWhoJoinedMorePromotion.birthDate}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</display:table>
</fieldset>