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


<div style="background-color: lightblue; height: auto; display: inline-block;">
		<h3><jstl:out value="${topic.title }"></jstl:out></h3>
		<br/>
		<jstl:out value="${topic.description }"></jstl:out>
	
</div>
<br/>
	
<display:table name="messages" id="row" requestURI="${RequestURI}"
	pagesize="5" sort="list" defaultorder="descending">
	
	<display:column>
		<div>
			<div style="background-color: #ffcc66;">
				<jstl:out value="${row.title}"/>
			</div>
			<br/>
			<div >
				<jstl:out value="${row.description}"/>
			</div>
		</div>
	</display:column>
	<spring:message code="message.order" var="order" />
	<display:column property="order" title="${order}" sortable="true" headerClass="sortable"/>
	<spring:message code="message.creationMoment" var="creationMoment" />
	<display:column title="${creationMoment}">
		<fmt:formatDate value="${row.creationMoment}" pattern="MM/dd/yyyy HH:mm" />
	</display:column>
	
	<display:column title="${creationMoment}">
		<jstl:if test="${row.customer.id == customerId}">
			<acme:confirm code="message.edit" url="message/edit.do?messageId=${row.id}&topicId=${topic.id}" msg="message.editConfirm"/>
		</jstl:if>
	</display:column>

</display:table>

<br/>
<acme:cancel code="message.create" url="message/create.do?topicId=${topic.id}" />