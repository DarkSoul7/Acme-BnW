<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${RequestURI}" modelAttribute="messageForm">
	<form:hidden path="topicId"/>
	<form:hidden path="id"/>
	
	
	<acme:textbox code="message.title" path="title" mandatory="true" />

	<br/>
	
	<acme:textarea code="message.description" path="description" mandatory="true" />
	

	<acme:submit code="message.save" name="save"/>
	
	<acme:cancel code="message.cancel" url="/message/list.do?topicId=${topicId}"/>
		
</form:form>
