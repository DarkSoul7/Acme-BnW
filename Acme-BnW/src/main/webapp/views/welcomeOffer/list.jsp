
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="welcomeOffers" id="row" requestURI="${RequestURI}" pagesize="5">

	<spring:message code="welcomeOffer.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="welcomeOffer.openPeriod" var="openPeriod" />
	<display:column title="${openPeriod}">
		<fmt:formatDate value="${row.openPeriod}" pattern="MM/dd/yyyy HH:mm" />
	</display:column>
	
	<spring:message code="welcomeOffer.endPeriod" var="endPeriod" />
	<display:column title="${endPeriod}">
		<fmt:formatDate value="${row.endPeriod}" pattern="MM/dd/yyyy HH:mm" />
	</display:column>
	
	<spring:message code="welcomeOffer.amount" var="amount" />
	<display:column property="amount" title="${amount}" />
	
	<spring:message code="welcomeOffer.extractionAmount" var="extractionAmount" />
	<display:column property="extractionAmount" title="${extractionAmount}" />
	
	<display:column>
		<jstl:if test="${fn:length(row.customers) == 0}">
			<acme:cancel code="welcomeOffer.edit" url="welcomeOffer/edit.do?welcomeOfferId=${row.id}" />
		</jstl:if>
	</display:column>
	<display:column >
		<jstl:if test="${fn:length(row.customers) == 0}">
			<acme:confirm code="welcomeOffer.delete" url="welcomeOffer/delete.do?welcomeOfferId=${row.id}" msg="welcomeOffer.delete.confirm" />
		</jstl:if>
	</display:column>

</display:table>

<br/>
<acme:cancel code="welcomeOffer.create" url="welcomeOffer/register.do" class_="btn btn-primary" />
<acme:cancel code="welcomeOffer.back" url="/" />