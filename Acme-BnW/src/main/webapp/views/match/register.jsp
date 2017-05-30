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

<form:form action="${requestURI}" modelAttribute="matchForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="match.data" />
		</legend>

		<acme:datetimepicker code="match.startMoment" path="startMoment"
			mandatory="true" />
		<br />
		
		<acme:datetimepicker code="match.endMoment" path="endMoment"
			mandatory="true" />
		<br />
		
		<jstl:if test="${matchForm.id == 0}">
			<acme:select items="${fixtures}" itemLabel="title" code="match.fixture" path="idFixture" mandatory="true"/>
		</jstl:if>
		
		<jstl:if test="${matchForm.id == 0}">
			<acme:select items="${teams}" itemLabel="name" code="match.visitor" path="idTeamVisitor" mandatory="true"/>
		</jstl:if>
		
		<jstl:if test="${matchForm.id == 0}">
			<acme:select items="${teams}" itemLabel="name" code="match.local" path="idTeamLocal" mandatory="true"/>
		</jstl:if>

	</fieldset>
	<br />

	<jstl:if test="${matchForm.id != 0}">
		<acme:submit code="match.save" name="save" />
		<acme:cancel code="match.back" url="" />
	</jstl:if>
	<jstl:if test="${matchForm.id == 0}">
		<acme:submit code="match.create" name="save" />
		<acme:cancel code="match.cancel" url="" />
	</jstl:if>

</form:form>
