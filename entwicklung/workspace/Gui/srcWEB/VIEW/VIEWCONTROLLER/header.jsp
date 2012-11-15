<%@ page import="CONTROLLER.Controller" %>

<table><tr>
	<td  class="left">
		Lisa Hoffmann
	</td>
	<td class="right">
		<%=Controller.shsgui.createInput("button","","Einstellung",
				"popup(event,'"+"',{width:100,height:100},{name:'john',location:'mannheim'})")%>
		<%=Controller.shsgui.space(2, Controller.shsconfig.horiz) %>
		<%=Controller.shsgui.createInput("button", "", "LOGOUT") %>
	</td>
</tr></table>