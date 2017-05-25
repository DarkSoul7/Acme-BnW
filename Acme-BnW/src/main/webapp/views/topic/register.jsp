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

<form:form action="${requestURI}" modelAttribute="topicForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="topic.data" />
		</legend>

		<acme:textbox code="topic.title" path="title" mandatory="true" />
		<br />

		<acme:textbox code="topic.description" path="description"
			mandatory="true" />
		<br />

		<acme:datepicker code="topic.creationMoment" path="creationMoment"
			mandatory="true" />
		<br />
		
	</fieldset>
	<br />

	<jstl:if test="${topicForm.id != 0}">
		<acme:submit code="topic.save" name="save" />
		<acme:cancel code="topic.back" url="" />
	</jstl:if>
	<jstl:if test="${topicForm.id == 0}">
		<acme:submit code="topic.create" name="save" />
		<acme:cancel code="topic.cancel" url="" />
	</jstl:if>

</form:form>
