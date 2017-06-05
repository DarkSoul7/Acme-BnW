
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
		<jstl:choose>
			<jstl:when test="${pageContext.response.locale.language=='en'}">
				<fmt:formatDate value="${row.creationMoment}" pattern="MM/dd/yyyy" />
			</jstl:when>
			<jstl:otherwise>
				<fmt:formatDate value="${row.creationMoment}" pattern="dd/MM/yyyy" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
	
	<spring:message code="topic.customer" var="customerUserName" />
	<display:column property="customer.userAccount.username" title="${customerUserName}" />
	
	<spring:message code="topic.starsNumber" var="starsNumber" />
	<display:column property="starsNumber" title="${starsNumber}" sortable="true"/>
	
	<security:authorize access="hasRole('CUSTOMER')">
		<spring:message code="topic.edit" var="edit" />
		<display:column title="${edit}" >
			<jstl:if test="${row.customer.id == customer.id}">
				<acme:cancel code="topic.edit" url="topic/edit.do?topicId=${row.id}" />
			</jstl:if>
		</display:column>
		<display:column >
				<acme:cancel code="topic.punctuations" url="topic/punctuation/list.do?topicId=${row.id}" />
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMINISTRATOR')">
		<display:column >
				<acme:confirm msg="topic.deleteConfirm" code="topic.delete" url="topic/delete.do?topicId=${row.id}" />
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('CUSTOMER')">
	<acme:cancel code="topic.create" url="topic/create.do" class_="btn btn-primary" />
</security:authorize>
<acme:cancel url="/" code="topic.back" />