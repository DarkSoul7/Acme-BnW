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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="bets" id="row" requestURI="${requestURI}" pagesize="5">
	
	<spring:message code="bet.quantity" var="quantity" />
	<display:column title="${quantity}">
		<jstl:out value="${row.quantity}" />&euro;
	</display:column>
	
	<spring:message code="bet.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="bet.match" var="match" />
	<display:column title="${match}">
		<jstl:if test="${row.type != 'MULTIPLE'}">
			<jstl:out value="${row.market.match.localTeam.name} - ${row.market.match.visitorTeam.name}"></jstl:out>
		</jstl:if>
	</display:column>
	
	<spring:message code="bet.market" var="market" />
	<display:column title="${market}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.market.type.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.market.type.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>

	<spring:message code="bet.creationMoment" var="creationMoment" />
	<display:column title="${creationMoment}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.creationMoment}" pattern="MM/dd/yyyy HH:mm" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.creationMoment}" pattern="dd/MM/yyyy HH:mm" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="bet.type" var="type" />
	<display:column title="${type}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.type.getName()}" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.type.getSpanishName()}" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="bet.status" var="status" />
	<display:column title="${status}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.status.getName()}" />
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.status.getSpanishName()}" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="bet.balance" var="balance" />
	<display:column title="${balance}">
		<jstl:choose>
			<jstl:when test="${row.status.getConstant() == 'PENDING'}">
				<b><jstl:out value="-"></jstl:out></b>
			</jstl:when>
			<jstl:otherwise>
				<jstl:choose>
					<jstl:when test="${row.status.getConstant() == 'SUCCESSFUL'}">
						<span class="positiveBalance">+<fmt:formatNumber maxFractionDigits="2" value="${row.quantity * row.fee}" />&euro;</span>
					</jstl:when>
					<jstl:otherwise>
						<span class="negativeBalance"><jstl:out value="0" />&euro;</span>
					</jstl:otherwise>
				</jstl:choose>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<display:column>
		<jstl:if test="${row.type == 'MULTIPLE'}">
			<acme:cancel url="bet/multipleBetDetails.do?betId=${row.id}" code="bet.multipleBetDetails"/>
		</jstl:if>
	</display:column>
</display:table>

<div>
	<acme:cancel url="/" code="bet.back"/>
</div>