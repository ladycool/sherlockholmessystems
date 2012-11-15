<%@ page import="CONTROLLER.Controller" %>

<table><tr>
	<td  class="left">
		Peter Lustig
	</td>
	<td class="right">
		<%=Controller.shsgui.createInput("buttonMain", "", "LOGOUT") %>
		<%=Controller.shsgui.createInput("buttonMain", "", "Hilfe") %>
		<%=Controller.shsgui.createInput("buttonMain","","Einstellung",
				"popup(event,'"+"',{width:100,height:100},{name:'john',location:'mannheim'})")%>
	</td>
</tr></table>