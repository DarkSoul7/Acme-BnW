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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<jstl:choose>
	<jstl:when test="${pageContext.response.locale.language=='en'}">
		<jstl:set var="itemLabel" value="name" />
	</jstl:when>
	<jstl:otherwise>
		<jstl:set var="itemLabel" value="spanishName" />
	</jstl:otherwise>
</jstl:choose>

<form:form action="${requestURI}" modelAttribute="marketForm">
	<form:hidden path="id" />

	<fieldset>
		<legend>
			<spring:message code="market.data" />
		</legend>
		
		<jstl:choose>
			<jstl:when test="${marketForm.id == 0}">
				<acme:select2 items="${marketTypes}" itemLabel="${itemLabel}" code="market.title" path="type" mandatory="true"/>
				<acme:select3 items="${matches}" code="market.match" mandatory="true" path="idMatch" selectedItem="${marketForm.idMatch}"/>
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="type"/>
				<form:hidden path="idMatch"/>
				
				<h4 style="padding-left:1cm;"><jstl:choose>
					<jstl:when test="${pageContext.response.locale.language=='en'}">
						<jstl:out value="${match.localTeam.name} - ${match.visitorTeam.name }  (${marketForm.type.getName()})" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:out value="${match.localTeam.name} - ${match.visitorTeam.name }  (${marketForm.type.getSpanishName()})" />
					</jstl:otherwise>
				</jstl:choose></h4>
			</jstl:otherwise>
		</jstl:choose>

		<spring:message code="market.fee.help" var="feeHelp" />
		<acme:textbox code="market.fee" path="fee" mandatory="true" placeholder="${feeHelp}" />
		
	</fieldset>

	<jstl:if test="${marketForm.id != 0}">
		<acme:submit code="market.save" name="save" />
	</jstl:if>
	<jstl:if test="${marketForm.id == 0}">
		<acme:submit code="market.create" name="save" />
	</jstl:if>
	<acme:cancel code="market.cancel" url="/market/list.do" />
</form:form>
