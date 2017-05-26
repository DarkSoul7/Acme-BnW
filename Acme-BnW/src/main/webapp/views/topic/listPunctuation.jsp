
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


<display:table name="punctuations" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="topic.punctuation.stars" var="stars" />
	<display:column property="stars" title="${stars}"/>
	
	<spring:message code="topic.customer" var="customer" />
	<display:column property="customer.userAccount.username" title="${customer}" />
	
	<display:column>
		<jstl:if test="${customerId == row.customer.id}">
			<acme:cancel code="topic.punctuation.delete" url="topic/punctuation/delete.do?punctuationId=${row.id}" />
		</jstl:if>
	</display:column>

</display:table>

<br/>
	<jstl:if test="${givenPunctuation == false }">
		<acme:cancel code="topic.punctuation.create" url="topic/punctuation/create.do?topicId=${topicId}" />
	</jstl:if>
<br/>

<jstl:if test="${errorMessage != null }">
	<spring:message code="${errorMessage}" var="error" />
	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
</jstl:if>
