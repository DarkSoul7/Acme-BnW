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

<display:table name="teams" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="team.name" var="name" />
	<display:column property="name" title="${name}" />
	
	<spring:message code="team.shield" var="shield" />
	<display:column title="${shield}">
		<img src="${row.shield}" alt="shield" width="70" height="70">
	</display:column>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<jstl:if test="${row.favourite == false}">
				<acme:cancel url="team/addFavourite.do?teamId=${row.id}" code="team.addTeam"/>
			</jstl:if>
			<jstl:if test="${row.favourite == true}">
				<acme:cancel url="team/deleteFavourite.do?teamId=${row.id}" code="team.deleteTeam"/>
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
	
		<display:column>
			<acme:cancel url="team/edit.do?teamId=${row.id}"
				code="team.edit" />
		</display:column>
		
		<display:column>
			<acme:cancel url="team/delete.do?teamId=${row.id}"
				code="team.delete" />
		</display:column>
	

	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<acme:cancel url="team/register.do"
				code="team.create" />
</security:authorize>