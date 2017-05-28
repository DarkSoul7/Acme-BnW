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

<display:table name="multipleBet.childrenBets" id="row" requestURI="${requestURI}" pagesize="5">
	
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
		<jstl:out value="${row.market.title}"></jstl:out>
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
</display:table>

<div>
	<b><spring:message code="bet.quantity" />: </b><jstl:out value="${multipleBet.quantity}" />&euro;
	<br/>
	<b><spring:message code="bet.totalFee" />: </b><jstl:out value="${multipleBet.fee}" />
	<br/>
	<jstl:choose>
		<jstl:when test="${pageContext.response.locale.language=='en'}">
			<b><spring:message code="bet.status" />: </b><jstl:out value="${multipleBet.status.getName()}" />
		</jstl:when>
		<jstl:otherwise>
			<b><spring:message code="bet.status" />: </b><jstl:out value="${multipleBet.status.getSpanishName()}" />
		</jstl:otherwise>
		</jstl:choose>
	<br/>
	<b><spring:message code="bet.status" />: </b>
	<jstl:choose>
		<jstl:when test="${multipleBet.status.getName() == 'PENDING'}">
			<b><jstl:out value="-"></jstl:out></b>
		</jstl:when>
		<jstl:otherwise>
			<jstl:choose>
				<jstl:when test="${multipleBet.status.getName() == 'SUCCESSFUL'}">
					<span class="positiveBalance">+<fmt:formatNumber maxFractionDigits="2" value="${multipleBet.quantity * multipleBet.fee}" />&euro;</span>
				</jstl:when>
				<jstl:otherwise>
					<span class="negativeBalance"><jstl:out value="0" />&euro;</span>
				</jstl:otherwise>
			</jstl:choose>
		</jstl:otherwise>
	</jstl:choose>
</div>

<br/>

<div>
	<acme:cancel url="/bet/history.do" code="bet.back"/>
</div>