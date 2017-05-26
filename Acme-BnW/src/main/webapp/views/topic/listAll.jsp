
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

<display:table name="topics" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="topic.title" var="title" />
	<display:column title="${title}">
	
		<a href="message/list.do?topicId=${row.id}"><jstl:out value="${row.title }"/></a>
	</display:column>

	<spring:message code="topic.creationMoment" var="creationMoment" />
	<display:column title="${creationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="MM/dd/yyyy HH:mm" />
	</display:column>
	
	<spring:message code="topic.customer" var="customer" />
	<display:column property="customer.userAccount.username" title="${customer}" />
	
	<spring:message code="topic.edit" var="edit" />
	<display:column title="${edit}" >
		<jstl:if test="${row.customer.id == customerId}">
			<acme:cancel code="topic.edit" url="topic/edit.do?topicId=${row.id}" />
		</jstl:if>
	</display:column>

</display:table>

<br/>
<acme:cancel code="topic.create" url="topic/create.do" />

<br/>

<jstl:if test="${errorMessage != null }">
	<spring:message code="${errorMessage}" var="error" />
	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
</jstl:if>
