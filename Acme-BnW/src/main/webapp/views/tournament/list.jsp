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

<display:table name="tournaments" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="tournament.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="tournament.description" var="description" />
	<display:column property="description" title="${description}" />

	<spring:message code="tournament.startMoment" var="startMoment" />
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

	<spring:message code="tournament.endMoment" var="endMoment" />
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

	<spring:message code="tournament.sport" var="sport" />
	<display:column title="${sport}">
		<jstl:if test="${pageContext.response.locale.language=='en'}">
			<jstl:out value="${row.sport.name}"></jstl:out>
		</jstl:if>
		<jstl:if test="${pageContext.response.locale.language=='es'}">
			<jstl:out value="${row.sport.spanishName}"></jstl:out>
		</jstl:if>
	</display:column>

	<display:column>
		<acme:cancel url="category/listByTournament.do?tournamentId=${row.id}"
			code="tournament.category" />
	</display:column>
	<security:authorize access="hasRole('MANAGER')">
	
		<display:column>
			<acme:cancel url="tournament/edit.do?tournamentId=${row.id}"
				code="tournament.edit" />
		</display:column>
		
		<display:column>
			<jstl:if test="${empty row.categories}">
				<acme:cancel url="tournament/delete.do?tournamentId=${row.id}"
					code="tournament.delete" />
			</jstl:if>
		</display:column>
	

	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<acme:cancel url="tournament/register.do"
				code="tournament.create" />
</security:authorize>

<%-- <jstl:if test="${errorMessage != null }"> --%>
<!-- <br/> -->
<!-- <br/> -->
<%-- 	<spring:message code="${errorMessage}" var="error" /> --%>
<%-- 	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font> --%>
<%-- </jstl:if> --%>