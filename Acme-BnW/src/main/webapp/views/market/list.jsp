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

<display:table name="markets" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="market.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="market.fee" var="fee" />
	<display:column property="fee" title="${fee}" />

	<security:authorize access="hasRole('MANAGER')">
	
		<display:column>
			<acme:cancel url="market/edit.do?marketId=${row.id}"
				code="market.edit" />
		</display:column>
		
		<display:column>
			<acme:cancel url="market/delete.do?marketId=${row.id}"
				code="market.delete" />
		</display:column>
	

	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<acme:cancel url="market/register.do"
				code="market.create" />
</security:authorize>
