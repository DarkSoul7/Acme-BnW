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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<display:table name="bets" id="row" requestURI="${requestURI}" pagesize="5">

	<display:column>
		<input type="checkbox" id="check${row.id}">
	</display:column>

	<spring:message code="bet.match" var="match" />
	<display:column title="${match}">
		<jstl:out value="${row.market.match.localTeam.name} - ${row.market.match.visitorTeam.name}"></jstl:out>
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
	
	<spring:message code="bet.market.fee" var="fee" />
	<display:column property="market.fee" title="${fee}" />

	<spring:message code="bet.quantity" var="betQuantity" />
	<display:column title="${betQuantity}">
		<input type="text" id="input${row.id}" class="form-control" />
	</display:column>
	
	<spring:message code="bet.submit" var="submit" />
	<display:column>
		<button type="button" class="btn btn-success" onclick="javascript: submitSelectedSimpleBet('${row.market.id}', '${row.market.match.id}', '${row.id}');" >${submit}</button>
	</display:column>
	
	<spring:message code="bet.removeSelection" var="removeSelection" />
		<display:column>
			<acme:confirm url="bet/removeSelection.do?matchId=${row.market.match.id}&betId=${row.id}" code="bet.removeSelection" msg="bet.removeSelection.confirm" />
		</display:column>
</display:table>

<jstl:if test="${fn:length(bets) > 1}">
	<div id="multipleBetDiv">
		<jstl:set var="totalFee" value="1" />
		<jstl:forEach items="${bets}" var="bet">
			<jstl:set var="totalFee" value="${totalFee * bet.market.fee}" />
		</jstl:forEach>
		<fmt:formatNumber var="totalFee" value="${totalFee}" maxFractionDigits="2" />
		
		<table style="border:none;">
			<tr>
				<td>
					<b><spring:message code="bet.multiple" /><jstl:out value=" (@${totalFee})" /></b>
				</td>
				<td>
					<input type="text" id="inputMultiple" class="form-control" />
				</td>
				<td>
					<button type="button" class="btn btn-success" onclick="javascript: submitMultipleBets();" >${submit}</button>
				</td>
			</tr>
		</table>
	</div>
</jstl:if>

<br/>

<div>
	<acme:cancel code="bet.back" url="/" />
</div>