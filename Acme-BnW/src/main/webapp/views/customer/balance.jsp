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
	<jstl:set var="itemLabel" value="englishName"></jstl:set>
</jstl:if>

<jstl:if test="${pageContext.response.locale.language=='es'}">
	<jstl:set var="itemLabel" value="spanishName"></jstl:set>
</jstl:if>

<spring:message code="customer.balanceNow" /> : <jstl:out value="${balanceNow}"></jstl:out>
<br>
<br>
<br>

<form:form action="${requestURI}" modelAttribute="balanceForm">

		<acme:textbox code="customer.balance" path="balance" mandatory="true" />
		<br />
		
		<acme:select2 items="${currencies}" itemLabel="${itemLabel}"
			code="customer.currency" path="currency" mandatory="true" />
		<br />
			
		<acme:submit code="customer.save" name="save" />
		<acme:cancel code="customer.back" url="" />

</form:form>

<jstl:if test="${message != null }">
<br/>
<br/>
	<spring:message code="${message}" var="error" />
	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
</jstl:if>
