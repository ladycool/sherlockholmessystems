<!-- @Eshan,Engin,Stella -->

<%@ page import="SERVICE.Config" %>
<%
	final String
	firstnameId = "",
	middlenameId = "",
	lastnameId = "",
	titleNa = "",
	streetId = "",
	zipcodeId = "",
	cityId = "",
	phonenrId = ""
	;

	final int
	length = 50
	;
%>


<table id='<%=Config.signupId%>' style='dislay:none;'>
	<tr><td class="loginlabel"><%=Config.shsdb.text(455) %></td><td class="logininput"><%=Config.shsgui.defaultTXTInput(firstnameId) %></td></tr>
	<tr><td class="loginlabel"><%=Config.shsdb.text(456) %></td><td class="logininput"><%=Config.shsgui.defaultTXTInput(middlenameId) %></td></tr>
</table>