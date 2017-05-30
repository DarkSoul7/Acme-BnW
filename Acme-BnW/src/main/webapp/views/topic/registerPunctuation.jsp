<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${RequestURI}" modelAttribute="punctuation">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="customer" />
	<form:hidden path="topic" />
	
	<fieldset>
		<legend>
			<spring:message code="topic.punctuation.data" />
		</legend>
		<acme:textbox code="topic.punctuation.stars" path="stars" mandatory="true" />

	</fieldset>
	<br />

		<acme:submit code="topic.save" name="save" />
		<acme:cancel code="topic.back" url="" />

</form:form>
