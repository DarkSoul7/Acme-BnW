
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<display:table name="customers" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="customer.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="customer.surname" var="surname" />
	<display:column property="surname" title="${surname}" />
	
	<spring:message code="customer.email" var="email" />
	<display:column property="email" title="${email}" />
	
	<spring:message code="customer.phone" var="phone" />
	<display:column property="phone" title="${phone}" />
	
	<spring:message code="customer.username" var="username" />
	<display:column property="userAccount.username" title="${username}" />
	
	<display:column>
		<jstl:if test="${row.userAccount.enabled == true}">
			<acme:confirm code="customer.ban" url="customer/banManagement.do?customerId=${row.id}" msg="customer.ban.confirm" />
		</jstl:if>
	</display:column>
	<display:column >
		<jstl:if test="${row.userAccount.enabled == false}">
			<acme:confirm code="customer.unban" url="customer/banManagement.do?customerId=${row.id}" msg="customer.unban.confirm" />
		</jstl:if>
	</display:column>

</display:table>

<br/>
<acme:cancel code="welcomeOffer.create" url="welcomeOffer/create.do" />

<br/>

<jstl:if test="${errorMessage != null }">
	<spring:message code="${errorMessage}" var="error" />
	<font size="4" color="red"><jstl:out value="${error}"></jstl:out></font>
</jstl:if>