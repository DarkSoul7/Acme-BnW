
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


<display:table name="welcomeOffer" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="welcomeOffer.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="welcomeOffer.amount" var="amount" />
	<display:column property="amount" title="${amount}" />
	
	<spring:message code="welcomeOffer.extractionAmount" var="extractionAmount" />
	<display:column property="extractionAmount" title="${extractionAmount}" />
	
</display:table>

<jstl:if test="${optionSelected == true }">
<br/>
	<spring:message code="welcomeOffer.extractionAmountTrue" var="extractionAmountTrue" />
	<jstl:out value="${extractionAmountTrue }"></jstl:out>
</jstl:if>

<jstl:if test="${optionSelected == false }">
<br/>
	<spring:message code="welcomeOffer.extractionAmountFalse" var="extractionAmountFalse" />
	<jstl:out value="${extractionAmountFalse }"></jstl:out>
</jstl:if>

<jstl:if test="${optionSelected == null }">
	<br/>
	<acme:cancel code="welcomeOffer.acceptButton" url="welcomeOffer/welcomeOfferOption.do?option=accept" />
	&nbsp;&nbsp;&nbsp;&nbsp;
	<acme:cancel code="welcomeOffer.declineButton" url="welcomeOffer/welcomeOfferOption.do?option=decline" />
</jstl:if>