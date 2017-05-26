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

<jstl:if test="${successMessage != null}">
	<div class="alert alert-success">
		<spring:message code="${successMessage}" />
	</div>
</jstl:if>

<form:form action="${requestURI}" modelAttribute="categoryForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="category.data" />
		</legend>

		<acme:textbox code="category.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="category.description" path="description"
			mandatory="true" />
		<br />
		<jstl:if test="${categoryForm.id == 0}">
			<acme:select items="${tournaments}" itemLabel="name" code="tournament.name" path="idTournament" mandatory="true"/>
		</jstl:if>
		

	</fieldset>
	<br />

	<jstl:if test="${categoryForm.id != 0}">
		<acme:submit code="category.save" name="save" />
		<acme:cancel code="category.back" url="" />
	</jstl:if>
	<jstl:if test="${categoryForm.id == 0}">
		<acme:submit code="category.create" name="save" />
		<acme:cancel code="category.cancel" url="" />
	</jstl:if>

</form:form>
