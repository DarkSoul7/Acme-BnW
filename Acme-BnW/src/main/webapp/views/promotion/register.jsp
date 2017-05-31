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

<form:form action="${requestURI}" modelAttribute="promotionForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="promotion.data" />
		</legend>
		
		<acme:textbox code="promotion.title" path="title" mandatory="true" />
		<br />
		
		<acme:textbox code="promotion.description" path="description" mandatory="true" />
		<br />
		
		<acme:textbox code="promotion.fee" path="fee" mandatory="true" />
		<br />

		<acme:datepicker code="promotion.startMoment" path="startMoment"
			mandatory="true" />
		<br />
		
		<acme:datepicker code="promotion.endMoment" path="endMoment"
			mandatory="true" />
		<br />
		
		<jstl:if test="${promotionForm.id == 0}">
			<acme:select items="${markets}" itemLabel="type" code="market.title" path="idMarket" mandatory="true"/>
		</jstl:if>

	</fieldset>
	<br />

	<jstl:if test="${promotionForm.id != 0}">
		<acme:submit code="promotion.save" name="save" />
		<acme:cancel code="promotion.back" url="" />
	</jstl:if>
	<jstl:if test="${promotionForm.id == 0}">
		<acme:submit code="promotion.create" name="save" />
		<acme:cancel code="promotion.cancel" url="" />
	</jstl:if>

</form:form>
