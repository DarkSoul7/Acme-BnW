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
		
		<spring:message code="match.localTeam" var="localTeam" />
		<display:column property="market.match.localTeam.name" title="${localTeam}"/>
		
		<spring:message code="match.visitorTeam" var="visitorTeam" />
		<display:column property="market.match.visitorTeam.name" title="${visitorTeam}"/>
		
		<spring:message code="welcome.title" var="wTitle" />
		<display:column title="${wTitle}">
			<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.market.type.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.market.type.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
		</display:column>
		
		<spring:message code="promotion.market.fee" var="marketFee" />
		<display:column style="text-decoration:line-through;" property="market.fee" title="${marketFee}" />
		
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
			
	<security:authorize access="hasRole('MANAGER')">
	
	<spring:message code="promotion.cancelled" var="cancel" />
	<display:column title="${cancel}">
			<jstl:if test="${row.cancel}">
				<span class="glyphicon glyphicon-ok-circle"></span>
			</jstl:if>
			<jstl:if test="${!row.cancel}">
				<span class="glyphicon glyphicon-remove-circle"></span>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')"> 
		<display:column>
			<jstl:set var="validPromotion" value="${row.startMoment < currentMoment && row.endMoment > currentMoment}" />
			<jstl:choose>
				<jstl:when test="${validPromotion}">
					<acme:cancel code="welcome.goToMarket" url="market/list.do?marketId=${row.market.id}" />			
				</jstl:when>
				<jstl:otherwise>
					<i><spring:message code="promotion.comingSoon" /></i>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>´
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<acme:cancel url="promotion/edit.do?promotionId=${row.id}" code="promotion.edit" />
		</display:column>
		
		<display:column>
			<jstl:if test="${row.cancel == false }">
				<acme:confirm url="promotion/cancel.do?promotionId=${row.id}" code="promotion.cancel" msg="promotion.cancel.confirm" />
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>

<acme:cancel url="/" code="promotion.back" />