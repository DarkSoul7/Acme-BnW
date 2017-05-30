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

<acme:confirm code="customer.disableProfile" url="customer/autoExclusion.do" msg="customer.disableProfile.confirm" />

<br/>

<form:form action="${requestURI}" modelAttribute="customerForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="customer.personalDetails" />
		</legend>

		<acme:textbox code="customer.name" path="name" mandatory="true" />
		<br />

		<acme:textbox code="customer.surname" path="surname" mandatory="true" />
		<br />

		<acme:textbox code="customer.phone" path="phone" mandatory="true" />
		<br />

		<acme:textbox code="customer.email" path="email" mandatory="true" />
		<br />

		<acme:textbox code="customer.nid" path="nid" mandatory="true" />
		<br />

		<acme:datepicker code="customer.birthDate" path="birthDate"
			mandatory="true" />
		<acme:error path="overAge" />
		<br />

	</fieldset>
	<br />
	<fieldset>
		<legend>
			<spring:message code="customer.coordinates" />
		</legend>

		<acme:textbox code="coordinates.country" path="coordinates.country"
			mandatory="true" />
		<br />

		<acme:textbox code="coordinates.state" path="coordinates.state" />
		<br />

		<acme:textbox code="coordinates.province" path="coordinates.province" />
		<br />

		<acme:textbox code="coordinates.city" path="coordinates.city"
			mandatory="true" />
		<br />

	</fieldset>

	<br />

	<fieldset>
		<legend>
			<spring:message code="customer.creditCard" />
		</legend>

		<acme:textbox code="creditCard.holderName"
			path="creditCard.holderName" />
		<br />

		<acme:select2 items="${brands}" itemLabel="${itemLabel}"
			code="creditCard.brandName" path="creditCard.brandName" />
		<br />

		<acme:textbox code="creditCard.number" path="creditCard.number" />
		<br />

		<acme:textbox code="creditCard.expirationMonth"
			path="creditCard.expirationMonth" />
		<br />

		<acme:textbox code="creditCard.expirationYear"
			path="creditCard.expirationYear" />
		<br />

		<acme:textbox code="creditCard.cvv" path="creditCard.cvv" />
		<br />
	</fieldset>

	<security:authorize access="isAnonymous()">
		<br />
		<fieldset>
			<legend>
				<spring:message code="customer.userAccountDetails" />
			</legend>
			<br />
			<acme:textbox code="customer.username" path="username"
				mandatory="true" />
			<br />
			<acme:password code="customer.password" path="password"
				mandatory="true" />
			<br />
			<acme:password code="customer.repeatPassword" path="repeatPassword"
				mandatory="true" />
			<br>
			<acme:checkbox code="customer.acceptCondition" path="acceptCondition"
				mandatory="true" />
		</fieldset>
	</security:authorize>

	<br />
	<br />

	<jstl:if test="${customerForm.id != 0}">
		<acme:submit code="customer.save" name="save" />
		<acme:cancel code="customer.back" url="" />
	</jstl:if>
	<jstl:if test="${customerForm.id == 0}">
		<acme:submit code="customer.create" name="save" />
		<acme:cancel code="customer.cancel" url="" />
	</jstl:if>

</form:form>
