<!-- Das Skript ist vollständig: Patrick -->

<%@ page import="CONTROLLER.Controller" %>


<table id='<%=Controller.shsconfig.signupId%>' style='dislay:none;'>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Name") %></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(Controller.shsconfig.fullnameId) %></td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Anschrift") %></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(Controller.shsconfig.addressId) %></td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Tel Nr.") %></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(Controller.shsconfig.phonenrId) %></td>
	</tr>
</table>
