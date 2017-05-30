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

<jstl:if test="${pageContext.response.locale.language=='en'}">
	<jstl:set var="itemLabel" value="name"></jstl:set>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language=='es'}">
	<jstl:set var="itemLabel" value="spanishName"></jstl:set>
</jstl:if>

<form:form action="${requestURI}" modelAttribute="tournamentForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="tournament.data" />
		</legend>

		<acme:textbox code="tournament.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="tournament.description" path="description"
			mandatory="true" />
		<br />

		<acme:datepicker code="tournament.startMoment" path="startMoment"
			mandatory="true" />
		<br />
		
		<acme:datepicker code="tournament.endMoment" path="endMoment"
			mandatory="true" />
		<br />
		
			<acme:select2 items="${sports}" itemLabel="${itemLabel}" code="tournament.sport" path="sport" mandatory="true"/>
	<br/>
	</fieldset>
	<br />

	<jstl:if test="${tournamentForm.id != 0}">
		<acme:submit code="tournament.save" name="save" />
		<acme:cancel code="tournament.back" url="" />
	</jstl:if>
	<jstl:if test="${tournamentForm.id == 0}">
		<acme:submit code="tournament.create" name="save" />
		<acme:cancel code="tournament.cancel" url="" />
	</jstl:if>

</form:form>
