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

<form:form action="${requestURI}" modelAttribute="teamForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="team.data" />
		</legend>

		<acme:textbox code="team.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="team.shield" path="shield" mandatory="true" />
	</fieldset>
	<br />

	<jstl:if test="${teamForm.id != 0}">
		<acme:submit code="team.save" name="save" />
		<acme:cancel code="team.back" url="" />
	</jstl:if>
	<jstl:if test="${teamForm.id == 0}">
		<acme:submit code="team.create" name="save" />
		<acme:cancel code="team.cancel" url="" />
	</jstl:if>

</form:form>
