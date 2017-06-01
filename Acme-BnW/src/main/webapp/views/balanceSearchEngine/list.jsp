<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form:form action="${formRequestURI}" modelAttribute="balanceSearchEngineForm">
	
	<fieldset>
		<legend><spring:message code="balanceSearchEngine.params"/></legend>
		
		
		<acme:textbox code="balanceSearchEngine.name" path="name" />
		<br/>
		
		<acme:textbox code="balanceSearchEngine.surname" path="surname" />
		<br/>
		
		<acme:textbox code="balanceSearchEngine.nid" path="nid" />
		<br/>
		
	</fieldset>
	
	<br/>
	
	<acme:submit code="balanceSearchEngine.search" name="save"/>
</form:form>

<br/>

<h2><spring:message code="balanceSearchEngine.results" /></h2>

<display:table name="customers" id="row" requestURI="${tableRequestURI}">
	
	<spring:message code="customer.name" var="name" />
	<display:column property="name" title="${name}"/>
	
	<spring:message code="customer.surname" var="surname" />
	<display:column property="surname" title="${surname}"/>
	
	<spring:message code="customer.nid" var="nid" />
	<display:column property="nid" title="${nid}"/>
	
	<spring:message code="customer.phone" var="phone" />
	<display:column property="phone" title="${phone}"/>

	<spring:message code="customer.email" var="email" />
	<display:column property="email" title="${email}"/>
	
	<spring:message code="customer.earnings" var="earnings" />
	<display:column property="earnings" title="${earnings}"/>
	
	<spring:message code="customer.losses" var="losses" />
	<display:column property="losses" title="${losses}"/>

</display:table>