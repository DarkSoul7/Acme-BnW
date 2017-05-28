<%--
 * textbox.tag
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

<script src="scripts/moment-with-locales.js"></script>
<script src="scripts/bootstrap-collapse-2.0.4.js"></script>
<script src="scripts/transition.min.js"></script>
<script src="scripts/bootstrap-datetimepicker.js"></script>

<link rel="stylesheet" type="text/css" href="styles/bootstrap-datetimepicker.css">

<%-- Attributes --%> 
 
<%@ attribute name="path" required="true" %>
<%@ attribute name="code" required="true" %>

<%@ attribute name="readonly" required="false" %>
<%@ attribute name="mandatory" required="false" %>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false" />
</jstl:if>

<jstl:if test="${mandatory == null}">
	<jstl:set var="mandatory" value="false" />
</jstl:if>

<%-- Definition --%>

<spring:bind path="${path}">
	<div class="form-group ${status.error? 'has-error':''}"
		style="padding-left: 1cm">
		<form:label path="${path}">
			<spring:message code="${code}" />:
			<jstl:if test="${mandatory == true}">
				<a class="error">(*)</a>
			</jstl:if>
		</form:label>
		<div class="container">
			<div class="row">
				<div class='col-sm-6'>
					<div class="form-group">
						<div class='input-group date' id="${path}_datetimepicker">
							<form:input path="${path}" readonly="${readonly}" class="form-control" />	
							<form:errors path="${path}" cssClass="error" /> 
							<span class="input-group-addon"> 
								<span class="glyphicon glyphicon-calendar"></span>
							</span>
						</div>
					</div>
				</div>
				<script type="text/javascript">
            $(function () {
            	$.fn.datepicker.defaults.language = '${pageContext.response.locale.language}';
                $('.${path}_datetimepicker').datetimepicker({
                    locale: '${cookie.language.value}'
                });
            });
        </script>
			</div>
		</div>
	</div>
</spring:bind>
