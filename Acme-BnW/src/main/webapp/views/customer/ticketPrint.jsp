
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

<style type="text/css">
   body {
   margin: 0px 0px 0px 0px;
   padding: 0px 0px 0px 0px;
   }
   table {
   width: 100%;
   border­collapse: collapse;
   }
   td {
   vertical­align: top;
   }
   .print_page_container, .section {
   width: 100%;
   font­family: Arial, Helvetica, sans­serif;
   text­align: left;
   }
   .section {
   width: 100%;
   margin­bottom: 36px;
   padding: 0 0;
   border­collapse: collapse;
   border­spacing: 0; /*page­break­inside: avoid;*/
   }
   .leftbox, .rightbox {
   width: 48%;
   background­color: #F3F3F3;
   }
   .middlebox {
   width: 4%;
   }
   .customer_section, .contact_section {
   width: 100%;
   border: 0px solid #CCCCCC;
   border­collapse: collapse;
   border­spacing: 0;
   }
   .print_heading, .ticket_heading, .mycompany_heading {
   text­align: left;
   font­weight: bold;
   color: #000000;
   border­collapse: collapse;
   border­spacing: 0;
   border­bottom: 1px solid #CCCCCC;
   }
   .customer_body {
   padding: 3px 3px;
   }
   .amountdue_section {
   text­align: right;
   border: 2px solid #CCCCCC;
   border­collapse: collapse;
   border­spacing: 0;
   }
   .ticket_section, .item_section, .asset_section, .appointment_section {
   width: 100%;
   text­align: left;
   border: 0px solid #CCCCCC;
   border­collapse: collapse;
   border­spacing: 0;
   }
   .customer_label {
   display: none;
   text­align: left;
   font­weight: bold;
   color: #000000;
   background­color: #FFFFFF;
   vertical­align: top;
   white­space: nowrap;
   }
   .ticket_label, .ticket_label2, .wolabel, .amountdue_label, .asset_label {
   text­align: left;
   vertical­align: top;
   font­weight: bold;
   color: #000000;
   background­color: #FFFFFF;
   white­space: nowrap;
   border: 1px solid #CCCCCC;
   border­collapse: collapse;
   border­spacing: 0;
   padding: 2px 2px;
   }
   .ticket_text, .ticket_text2, .wotext, .wotext_number, .amountdue_text, .asset_text {
   text­align: left;
   vertical­align: top;
   color: #000000;
   background­color: #FFFFFF;
   border: 1px solid #CCCCCC;
   border­collapse: collapse;
   border­spacing: 0;
   padding: 2px 2px;
   }
   .ticket_label, .ticket_label2 {
   width: 10%;
   }
   .ticket_text, .ticket_text2 {
   width: 40%;
   }
   .customer_label {
   width: 10%;
   margin­left: 10px;
   }
   .customer_text {
   width: 40%;
   }
   .asset_label {
   width: 20%;
   }
   .asset_text {
   width: 80%;
   }
   .wotext_number {
   text­align: right;
   white­space: nowrap;
   }
   .wotext, .wotext_number {
   padding­top: 3px;
   padding­bottom: 3px;
   padding­left: 3px;
   padding­right: 3px;
   }
   .my_company_info {
   text­align: left;
   color: #000000;
   background­color: #FFFFFF;
   }
   .customer_section, .customer_label, .customer_text, .contact_section, .wolabel {
   text­align: left;
   }
   .amountdue_label {
   text­align: right;
   }
   .ticket_info {
   font­family: &amp;
   quot;
   Arial Rounded MT Bold&amp;
   quot;
   ;
   color: #DDDDDD;
   text­align: center;
   }
   .largeHeader {
   }
   .print$10028.00label {
   white­space: nowrap;
   }
   .print$10028.00text {
   text­align: right;
   }
   .signature {
   width: 216px;
   page­break­inside: avoid;
   }
   .my_company_logo {
   }
   .dates_and_terms {
   border­collapse: collapse;
   width: 360px;
   font­weight: normal;
   margin­left: auto;
   margin­right: 1px;
   }
   .dates_and_terms td {
   padding: 5px 10px;
   }
   .dates_and_terms_label {
   text­align: left;
   white­space: nowrap;
   }
   .dates_and_terms_text {
   text­align: right;
   white­space: nowrap;
   }
   .balance_due {
   font­weight: bold;
   background­color: #EEE;
   border: 1px solid #CCC;
   }
   .invoice_row .wolabel {
   background­color: #EEE;
   }
   .item_section td {
   padding: 0px 10px;
   font­size: 0.9em;
   }
   .print_subtotal_text {
   }
   #itemlist table{
   table-layout: auto;
   }
   #itemlist td{
   border:0;
   padding:2px;
   }
   #itemlist tr:nth-child(odd){
   background-color: #FFF;
   }
   #itemlist tr:nth-child(even){
   background-color: ;
   }
   #itemlist tr:first-child td{
   border-bottom:1px solid black;
   }
   .item_tax {display: none;}
</style>
<div class="print_page_container">
   <table class="section">
      <tbody>
         <tr>
         	<spring:message code="ticketInvoice.title" var="title" />
            <td style="text-align: center;"><jstl:out value="${title}"/></td>
         </tr>
      </tbody>
   </table>
   <table></table>
<!--    <table class="section" style="font-size: 12px; margin: 0px;"> -->
<!--       <tbody> -->
<!--          <tr> -->
<!--             <td style="padding: 10px 3px; border-bottom: 4px solid black;"><strong style="color: #0a689e; font-size: 22px; font-family: times new roman;">INVOICE _InvoiceNumber_</strong></td> -->
<!--             <td style="text-align: right; padding: 10px 3px; border-bottom: 4px solid black;"> -->
<!--                _MyCompanyName_<br /> -->
<!--                License #8364 -->
<!--             </td> -->
<!--          </tr> -->
<!--       </tbody> -->
<!--    </table> -->
   <table class="section" style="float: right; font-size: 12px; margin: 0px;">
      <tbody>
         <tr>
         <spring:message code="ticketInvoice.extractionAmount" var="extractionAmount" />
         <spring:message code="ticketInvoice.moment" var="moment" />
         <spring:message code="ticketInvoice.amount" var="amount" />
            <td style="width: 70%; padding: 5px 2px; border-bottom: 1px solid black;"><strong><jstl:out value="${moment}"/></strong> <jstl:choose>
				<jstl:when test="${pageContext.response.locale.language=='en'}">
					<fmt:formatDate value="${ticket.moment}" pattern="MM/dd/yyyy" />
				</jstl:when>
				<jstl:otherwise>
					<fmt:formatDate value="${ticket.moment}" pattern="dd/MM/yyyy" />
				</jstl:otherwise>
			</jstl:choose>
			<br/>
			<jstl:out value="${amount}"/>  <jstl:out value="${ticket.amount }"/>
			</td>
            <td rowspan="2" style="width: 70%; text-align: center; padding: 5px 2px; border-bottom: 2px solid black;">
               <strong style="font-size: 13px; color: #0a689e;"><jstl:out value="${extractionAmount}"/>  <jstl:out value="${ticket.convertedMoney}"/><jstl:out value="${ticket.currency}"/></strong>
            </td>
         </tr>
      </tbody>
   </table>
   <table class="section" style="float: right; font-size: 12px; margin: 0px;">
    	<spring:message code="ticketCustomer.name" var="name" /> 
    	<spring:message code="ticketCustomer.data" var="customerData" />
      <tbody>
         <tr>
            <td style="width: 50%; padding: 20px 5px 20px; border-bottom: 3px solid black;"><strong><span style="text-decoration: underline;"><jstl:out value="${name}"/></span><br />
               </strong><jstl:out value="${customer.name}"/>
            </td>
            <td style="text-align: right; width: 50%; padding: 20px 5px 20px; border-bottom: 3px solid black;">
               <strong><span style="text-decoration: underline;"><jstl:out value="${customerData}"/></span><br />
               </strong><jstl:out value="${customer.surname}"/><br />
               <jstl:out value="${customer.phone}"/><br />
               <jstl:out value="${customer.email}"/>
            </td>
         </tr>
      </tbody>
   </table>
   <spring:message code="ticketInvoice.notes" var="notes" />
   <spring:message code="ticketInvoice.signature" var="signature" />
    <spring:message code="ticketInvoice.BnWTeam" var="BnWTeam" />
   <table class="section" style="float: right; font-size: 12px; margin: 0px;">
      <tbody>
         <tr>
            <td style="vertical-align: top; width: 100%; padding: 5px 2px 5px; border-bottom: 2px solid black;">
               <span style="font-size: 12px; font-family: times new roman; color: #0a689e;"><jstl:out value="${notes}"/></span><br /><br /><br /><br /><br />
               
            </td>
         </tr>
      </tbody>
   </table>
   <!-- Signature -->
   <table cellspacing="0" cellpadding="0" border="0" class="section">
      <tbody>
         <tr>
            <td>
               <p style="text-align: center;"><img src="images/logo-bnw.jpg"  style="width:204px;height:128px;"/>  </p>
               <p></p>
            </td>
            <td style="width: 50%;">
               <table cellspacing="6" cellpadding="2" border="0" style="width: 100%;">
                  <tbody>
                     <tr>
                        <td colspan="2">&nbsp;</td>
                     </tr>
                     <tr>
                        <td style="width: 75%; border-bottom: 1px solid #000000;">
                           <span style="font-size: 12px;"><br />
                           <img src="images/firma.png"  style="width:180px;height:100px;"/>
                           </span>
                        </td>
                     </tr>
                     <tr>
                        <td style="margin-bottom: 20px;">
                           <span style="font-size: 12px;"><jstl:out value="${signature }"/>
                           </span>
                        </td>
<!--                         <td style="margin-bottom: 20px;"><span style="font-size: 12px;"> -->
<!--                            Date -->
<!--                            </span> -->
<!--                         </td> -->
                     </tr>
                  </tbody>
               </table>
            </td>
         </tr>
      </tbody>
   </table>
   <table width="99%" class="section" style="float: right; font-size: 12px; margin: 0px;">
      <tbody>
         <tr>
            <td style="vertical-align: top; width: 50%; padding: 10px 2px 5px; border-bottom: 4px solid black; text-align: center; border-top: 1px solid black;">
               <jstl:out value="${BnWTeam}"/>
            </td>
         </tr>
      </tbody>
   </table>
</div>

<spring:message code="ticket.generatePdf" var="generatePdf" />
<button class="btn btn-success" type="button" onclick="printFunction()"><jstl:out value="${generatePdf }"/></button>
<acme:cancel code="customer.back" url="/customer/ticketList.do"/>

<script>

function printFunction() {
    window.print();
}
</script>

