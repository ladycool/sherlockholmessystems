<!-- @Eshan,Engin,Stella -->

<%@ page import="CONTROLLER.Controller" %>
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


<table id='<%=Controller.shsconfig.signupId%>' style='dislay:none;'>
	<tr><td class="loginlabel"><%=Controller.shsdb.text(455) %></td><td class="logininput"><%=Controller.shsgui.defaultTXTInput(firstnameId) %></td></tr>
	<tr><td class="loginlabel"><%=Controller.shsdb.text(456) %></td><td class="logininput"><%=Controller.shsgui.defaultTXTInput(middlenameId) %></td></tr>
</table>