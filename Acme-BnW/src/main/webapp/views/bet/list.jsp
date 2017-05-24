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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="bets" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="bet.quantity" var="quantity" />
	<display:column property="quantity" title="${quantity}" />
	
	<spring:message code="bet.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	

	<spring:message code="bet.creationMoment" var="creationMoment" />
	<display:column title="${creationMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.creationMoment}" pattern="MM/dd/yyyy" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.creationMoment}" pattern="dd/MM/yyyy" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="bet.type" var="type" />
	<display:column title="${type}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.type.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.type.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="bet.status" var="status" />
	<display:column title="${status}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.status.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.status.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column>
		<acme:cancel url="bet/join.do?betId=${row.id}"
			code="bet.join" />
	</display:column>
</display:table>
