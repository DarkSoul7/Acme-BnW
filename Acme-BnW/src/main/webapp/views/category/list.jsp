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

<display:table name="categories" id="row" requestURI="${requestURI}"
	pagesize="5">

	<spring:message code="category.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="category.description" var="description" />
	<display:column property="description" title="${description}" />

	<display:column>
		<acme:cancel url="fixture/listByCategory.do?categoryId=${row.id}"
			code="category.fixture" />
	</display:column>
	
	<security:authorize access="hasRole('MANAGER')">
	
		<display:column>
			<acme:cancel url="category/edit.do?categoryId=${row.id}"
				code="category.edit" />
		</display:column>
		
		<display:column>
			<jstl:if test="${empty row.fixtures}">
				<acme:cancel url="category/delete.do?categoryId=${row.id}"
					code="category.delete" />
			</jstl:if>
		</display:column>
	

	</security:authorize>
</display:table>


<security:authorize access="hasRole('MANAGER')">
	<acme:cancel url="category/register.do"
				code="category.create" />
</security:authorize>