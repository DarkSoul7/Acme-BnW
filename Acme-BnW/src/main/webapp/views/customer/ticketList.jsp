
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


<display:table name="tickets" id="row" requestURI="${RequestURI}"
	pagesize="5">

	<spring:message code="ticket.amount" var="amount" />
	<display:column property="amount" title="${amount}" />
	
	<spring:message code="ticket.convertedMoney" var="convertedMoney" />
	<display:column property="convertedMoney" title="${convertedMoney}" />
	
	<spring:message code="ticket.currency" var="currency" />
	<display:column property="currency" title="${currency}" />
	
	<spring:message code="ticket.moment" var="moment" />
	<display:column title="${moment}">
			<jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${row.moment}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${row.moment}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>

	<display:column>
		
		<acme:confirm code="ticket.generatePdf" url="customer/generateInvoice.do?ticketId=${row.id}" msg="message.deleteConfirm"/>
	</display:column>

</display:table>

<script>


function printFunction() {
    window.print();
}
</script>

