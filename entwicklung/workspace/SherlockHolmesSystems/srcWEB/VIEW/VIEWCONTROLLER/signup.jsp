<!-- Das Skript ist vollständig: Patrick -->

<%@ page import="CONTROLLER.Controller" %>


<table id='<%=Controller.shsconfig.signupId%>' style='dislay:none;'>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text("Name") %></td>
		<td class="logininput"><%=Controller.shsgui.defaultTXTInput(Controller.shsconfig.fullnameId) %></td>
	</tr>
	<tr>
		<td class="loginlabel"><%=Controller.shsdb.text(56)%></td>
		<td><select name="<%=Controller.shsconfig.languageId%>" class="logininput" title="">
		  <option value="deu" selected="selected"><%=Controller.shsdb.text(53)%></option>
		  <option value="eng"><%=Controller.shsdb.text(54)%></option>
		  <option value="fra"><%=Controller.shsdb.text(55)%></option>
		</select></td>
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
