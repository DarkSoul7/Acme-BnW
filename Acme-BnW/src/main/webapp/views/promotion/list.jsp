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

<display:table name="promotions" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="promotion.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="promotion.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="promotion.fee" var="fee" />
	<display:column property="fee" title="${fee}" />

	<spring:message code="promotion.startMoment" var="startMoment" />
	<display:column title="${startMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.startMoment}" pattern="MM/dd/yyyy" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.startMoment}" pattern="dd/MM/yyyy" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="promotion.endMoment" var="endMoment" />
	<display:column title="${endMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.endMoment}" pattern="MM/dd/yyyy" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.endMoment}" pattern="dd/MM/yyyy" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<display:column>
		<acme:cancel url="promotion/join.do?promotionId=${row.id}"
			code="promotion.join" />
	</display:column>
</display:table>
