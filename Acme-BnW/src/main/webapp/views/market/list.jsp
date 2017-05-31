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

<script type="text/javascript">
	setInterval(
		function updateFees(matchId) {
			var url = "market/updateFees.do";
			
			if(matchId != null) {
				url += '?matchId=' + matchId;
			}
			
			$.ajax({
				type: 'get',
				url: url,
				success: function(response) {
					$.each(response, function(index, element) {
						var feeSpan = document.getElementById('fee' + element.marketId);
						if(feeSpan != null) {
							feeSpan.innerHTML = element.fee;
						}
					});
				},
				error: function(e) {
				}
			});
	}, 10000);
</script>

<jstl:if test="${match != null}">
	<h3 onload="javascript: updateFees('${match.id}');"><jstl:out value="${match.localTeam.name} - ${match.visitorTeam.name}" /></h3>
</jstl:if>

<display:table name="markets" id="row" requestURI="${requestURI}" pagesize="5">

	<spring:message code="market.match" var="match" />
	<display:column title="${match}">
		<jstl:out value="${row.match.localTeam.name} - ${row.match.visitorTeam.name}"></jstl:out>
	</display:column>
	
	<spring:message code="market.title" var="title" />
	<display:column title="${title}">
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<jstl:out value="${row.type.getName()}"></jstl:out>
			</jstl:when>
			<jstl:otherwise>
				<jstl:out value="${row.type.getSpanishName()}"></jstl:out>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="market.fee" var="fee" />
	<display:column title="${fee}">
		<span id="fee${row.id}"><jstl:out value="${row.fee}" /></span>
	</display:column>

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<acme:cancel url="market/edit.do?marketId=${row.id}"
				code="market.edit" />
		</display:column>
		
		<display:column>
			<acme:confirm url="market/delete.do?marketId=${row.id}" code="market.delete" msg="market.removeSelection.confirm" />
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<spring:message code="market.bet.quantity" var="betQuantity" />
		<display:column title="${betQuantity}">
			<input type="text" id="input${row.id}" class="form-control" />
		</display:column>
		
		<spring:message code="market.bet.submit" var="betSubmit" />
		<display:column>
			<button type="button" class="btn btn-success" onclick="javascript: submitSimpleBet('${row.id}', '${row.match.id}');" >${betSubmit}</button>
		</display:column>
		
		<spring:message code="market.bet.addSelection" var="betAddSelection" />
		<display:column>
			<acme:cancel url="/bet/addSelection.do?marketId=${row.id}" code="market.bet.addSelection" class_="btn btn-default" />
		</display:column>
	</security:authorize>
</display:table>

<div>
	<security:authorize access="hasRole('MANAGER')">
		<acme:cancel url="market/register.do" code="market.create" class_="btn btn-primary" />
	</security:authorize>
	
	<acme:cancel code="market.back" url="/match/list.do" />
</div>