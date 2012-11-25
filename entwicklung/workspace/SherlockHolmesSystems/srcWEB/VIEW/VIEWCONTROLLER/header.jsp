<%@ page import="CONTROLLER.Controller" %>
<%
	
	String name = (String)Controller.shsuser.getattr("username");

%>
<table><tr>
	<td  class="left">
		<%=Controller.shsgui.createA("",name) %>
	</td>
	<td>
		<%=Controller.shsgui.createInput("button","","Einstellung",
				"popup(event,{width:100,height:100},{action:'setting'})")%>
		<%=Controller.shsgui.space(2, Controller.shsconfig.horiz) %>
		<%=Controller.shsgui.createInput("button", "", "Hilfe") %>
		<%=Controller.shsgui.createInput("button", "", "LOGOUT") %>
		<!-- Der Button Tag in css führt dazu das die Buttons in umgekehrten Reihenfolge angezeigt werden: Was nicht gut ist -->
	</td>
</tr></table>