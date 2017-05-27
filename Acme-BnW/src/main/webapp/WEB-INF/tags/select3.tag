<%--
 * select.tag
 *
 * Copyright (C) 2016 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@ tag language="java" body-content="empty" %>

<%-- Taglibs --%>

<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 

<%@ attribute name="code" required="true" %>
<%@ attribute name="path" required="true" %>
<%@ attribute name="items" required="true" type="java.util.Collection" %>


<%@ attribute name="id" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="mandatory" required="false" %>
<%@ attribute name="selectedItem" required="false" %>
<%@ attribute name="selectError" required="false" %>

<jstl:if test="${id == null}">
	<jstl:set var="id" value="${UUID.randomUUID().toString()}" />
</jstl:if>

<jstl:if test="${onchange == null}">
	<jstl:set var="onchange" value="javascript: return true;" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<jstl:if test="${selectedItem == null}">
	<jstl:set var="selectedItem" value="-1" />
</jstl:if>

<%-- Definition --%>
<div class="form-group  ${selectError? 'has-error':''}" style="padding-left:1cm">
	<label for="${path}">
		<spring:message code="${code}" />:
		<jstl:if test="${mandatory == true}">
			<a class="error">(*)</a>
		</jstl:if>
	</label>	
	<form:select path="${path}" id="${id}" onchange="${onchange}" class="form-control" >
		<option value="0" label="----" />
		<jstl:forEach var="item" items="${items}">
			<jstl:if test="${item.id != selectedItem}">
				<option value="${item.id}" label="${item.localTeam.name} - ${item.visitorTeam.name}"/>
			</jstl:if>
			<jstl:if test="${item.id == selectedItem}">
				<option value="${item.id}" label="${item.localTeam.name} - ${item.visitorTeam.name}" selected="selected" />
			</jstl:if>
		</jstl:forEach>
	</form:select>
	<form:errors path="${path}" cssClass="error" />
</div>