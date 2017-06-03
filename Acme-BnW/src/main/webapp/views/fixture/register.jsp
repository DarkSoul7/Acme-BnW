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

<form:form action="${requestURI}" modelAttribute="fixtureForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="fixture.data" />
		</legend>

		<acme:textbox code="fixture.title" path="title" mandatory="true" />
		<br />
		
		<acme:datetimepicker code="fixture.startMoment" path="startMoment"
			mandatory="true" />
		<br />
		
		<acme:datetimepicker code="fixture.endMoment" path="endMoment"
			mandatory="true" />
		<br />

		<jstl:if test="${fixtureForm.id == 0}">
			<acme:select items="${categories}" itemLabel="name" code="fixture.category.name" path="idCategory" mandatory="true"/>
		</jstl:if>
		

	</fieldset>
	<br />

	<jstl:if test="${fixtureForm.id != 0}">
		<acme:submit code="fixture.save" name="save" />
		<acme:cancel code="fixture.back" url="" />
	</jstl:if>
	<jstl:if test="${fixtureForm.id == 0}">
		<acme:submit code="fixture.create" name="save" />
		<acme:cancel code="fixture.cancel" url="" />
	</jstl:if>

</form:form>
