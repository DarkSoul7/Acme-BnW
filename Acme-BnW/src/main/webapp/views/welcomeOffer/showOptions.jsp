
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="welcomeOffers" id="row" requestURI="${RequestURI}" pagesize="5">

	<spring:message code="welcomeOffer.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="welcomeOffer.amount" var="amount" />
	<display:column property="amount" title="${amount}" />
	
	<spring:message code="welcomeOffer.extractionAmount" var="extractionAmount" />
	<display:column property="extractionAmount" title="${extractionAmount}" />
	
	<spring:message code="welcomeOffer.active" var="active" />
	<display:column title="${active}">
		<jstl:if test="${activeWelcomeOffer.id == row.id && activeWO == true}">
			<span class="glyphicon glyphicon-ok-circle"></span>
		</jstl:if>
		<jstl:if test="${activeWelcomeOffer.id == row.id && activeWO == false}">
			<span class="glyphicon glyphicon-remove-circle"></span>
		</jstl:if>
	</display:column>
	
</display:table>

<jstl:if test="${activeWO == true}">
	<spring:message code="welcomeOffer.extractionAmountTrue" var="extractionAmountTrue" />
	<jstl:out value="${extractionAmountTrue}"></jstl:out>
	
	<acme:cancel code="welcomeOffer.declineButton" url="welcomeOffer/cancelWelcomeOffer.do" />
</jstl:if>

<jstl:if test="${activeWO == false}">
	<spring:message code="welcomeOffer.extractionAmountFalse" var="extractionAmountFalse" />
	<jstl:out value="${extractionAmountFalse }"></jstl:out>
</jstl:if>