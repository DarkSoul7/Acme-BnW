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

<script type="text/javascript">
	function submitBet(marketId, marketTitle) {
		var quantity = document.getElementById('input' + marketTitle).value;
		var url = '/bet/simpleBet.do?marketId=' + marketId + '&quantity=' + quantity;
		
		relativeRedir(url);
	}
	
	function addSelection(marketId) {
		alert('TODO');
	}
</script>

<display:table name="markets" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="market.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="market.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<security:authorize access="hasRole('CUSTOMER')">
		<spring:message code="market.bet.quantity" var="betQuantity" />
		<display:column title="${betQuantity}">
			<input type="text" id="input${row.title}" class="form-control" />
		</display:column>
		
		<spring:message code="market.bet.submit" var="betSubmit" />
		<display:column>
			<button type="button" class="btn btn-success" onclick="javascript: submitBet('${row.id}', '${row.title}');" >${betSubmit}</button>
		</display:column>
		
		<spring:message code="market.bet.addSelection" var="betAddSelection" />
		<display:column>
			<button type="button" class="btn btn-default" onclick="javascript: addSelection('${row.id}');" >${betAddSelection}</button>
		</display:column>
	</security:authorize>
</display:table>

<acme:cancel code="market.bet.showSelection" url="/bet/showSelection.do" class_="btn btn-primary" />
<acme:cancel code="market.back" url="/match/list.do" />