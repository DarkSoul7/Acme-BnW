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

<display:table name="matches" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="match.fixture" var="fixture" />
	<display:column title="${fixture}">
		<jstl:out value="${row.fixture.title}"></jstl:out>
	</display:column>
	
	<spring:message code="match.local" var="local" />
	<display:column title="${local}">
		<jstl:out value="${row.localTeam.name}"></jstl:out>
	</display:column>

	<spring:message code="match.visitor" var="visitor" />
	<display:column title="${visitor}">
		<jstl:out value="${row.visitorTeam.name}"></jstl:out>
	</display:column>
	
	<spring:message code="match.localResult" var="localResult" />
	<display:column property="localResult" title="${localResult}" />
	
	<spring:message code="match.visitorResult" var="visitorResult" />
	<display:column property="visitorResult" title="${visitorResult}" />
	
	<spring:message code="match.startMoment" var="startMoment" />
	<display:column title="${startMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.startMoment}" pattern="MM/dd/yyyy HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.startMoment}" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	
	<spring:message code="match.endMoment" var="endMoment" />
	<display:column title="${endMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.endMoment}" pattern="MM/dd/yyyy HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.endMoment}" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<display:column>
		<jstl:if test="${row.endMoment > currentMoment}">
			<acme:cancel url="market/listByMatch.do?matchId=${row.id}" code="match.market" />
		</jstl:if>
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${row.endMoment > currentMoment && row.startMoment < currentMoment}">
				<acme:cancel url="match/editResult.do?matchId=${row.id}" code="match.editResult" />
			</jstl:if>
		</display:column>
	
		<display:column>
			<jstl:if test="${row.endMoment > currentMoment}">
				<acme:cancel url="match/edit.do?matchId=${row.id}" code="match.edit" />
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${row.endMoment > currentMoment}">
				<acme:confirm url="match/delete.do?matchId=${row.id}" code="match.delete" msg="match.delete.confirm"/>
			</jstl:if>
		</display:column>
	
		<display:column>
			<jstl:if test="${row.endMoment < currentMoment && row.solvedBets == false}">
				<acme:confirm url="match/solveBets.do?matchId=${row.id}" code="match.solveBets" msg="match.solveBets.confirm"/>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<acme:cancel url="match/register.do" code="match.create" class_="btn btn-primary" />
</security:authorize>

<acme:cancel code="match.back" url="/fixture/list.do" />