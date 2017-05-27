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

<form:form action="${RequestURI}" modelAttribute="welcomeOfferForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="welcomeOffer.data" />
		</legend>
		
		<acme:textbox code="welcomeOffer.title" path="title" mandatory="true" />
		
		<br/>
	
		<acme:datepicker code="welcomeOffer.openPeriod" path="openPeriod"
			mandatory="true" />
		<br />
		
		<acme:datepicker code="welcomeOffer.endPeriod" path="endPeriod"
			mandatory="true" />
		<br />
		
		<acme:textbox code="welcomeOffer.amount" path="amount" mandatory="true" />
		
		<br/>
		
		<acme:textbox code="welcomeOffer.extractionAmount" path="extractionAmount" mandatory="true" />
		
		<br/>

	</fieldset>
	<br />
	
		<acme:submit code="welcomeOffer.save" name="save" />
		<acme:cancel code="welcomeOffer.cancel" url="" />
		
	<br/>
	
<jstl:if test="${errorMessage != null }">
	<spring:message code="${errorMessage}" var="error" />
	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
</jstl:if>

</form:form>