	<!-- protected HashMap<String, ArrayList<String>> externalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	
	<table>
		<tr>
			<td class="headerB">
			<%=
				Controller.shsgui.createInput("button","deletebut",Controller.shsdb.text(60),"")
			%>
			</td>
		</tr>
	</table>
	
	
	<table>
		<%
		if(!Controller.shsconfig.getexternalviewdata().isEmpty()){
			for(String ticketId : Controller.shsconfig.getexternalviewdata().keySet()){%>
				<tr>
					<td><%=Controller.shsgui.createInput("checkbox","checkbox_"+ticketId,"value_"+ticketId) %></td>
					<td><%=Controller.shsgui.createA("",Controller.shsconfig.getexternalviewdata().get(ticketId)) %></td>
				</tr>
			<%}
		}%>	
	</table>
	