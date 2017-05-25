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


<jstl:choose>
	<jstl:when test="${pageContext.response.locale.language=='en'}">
		<acme:cancel url="tournament/listBySport.do?sport=${Sport.FOOTBALL.getName()}"
			code="tournament.category" />
		<br />
		<acme:cancel url="category/listBySport.do?sport=${Sport.BASKETBALL.getName()}"
			code="tournament.category" />
	</jstl:when>
	<jstl:otherwise>
		<acme:cancel url="tournament/listBySport.do?sport=${Sport.FOOTBALL.getSpanishName()}"
			code="tournament.category" />
		<br />
		<acme:cancel url="category/listBySport.do?sport=${Sport.BASKETBALL.getSpanishName()}"
			code="tournament.category" />
	</jstl:otherwise>
</jstl:choose>
