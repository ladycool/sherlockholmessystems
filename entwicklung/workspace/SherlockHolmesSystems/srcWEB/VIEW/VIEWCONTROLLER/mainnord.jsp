	<!-- protected HashMap<String, ArrayList<String>> internalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	
	<table>
		<tr>
			<td class="headerB">
			<%=
				Controller.shsgui.createInput("button","deletebut",Controller.shsdb.text(60),"")+"   "+
				Controller.shsgui.createInput("button","freebut",Controller.shsdb.text(61),"")
			%>
			<form action="" METHOD="POST">
				<%=
				Controller.shsgui.createInput("hidden","uploadfile","uploadfile")+
				Controller.shsgui.createInput("file","file","")+
				Controller.shsgui.createInput("submit","freebut",Controller.shsdb.text(59),"")+"   "
				%>
			</form>	
			</td>
		</tr>
	</table>
	
	<table>
		<%if(!Controller.shsconfig.getinternalviewdata().isEmpty()){
			for(String fileId : Controller.shsconfig.getinternalviewdata().keySet()){%>
				<tr>
					<td><%=Controller.shsgui.createInput("checkbox","checkbox_"+fileId,"value_"+fileId) %></td>
					<td><%=Controller.shsgui.createA("simplepopup(event,{width:500,height:200},folderpopup)",Controller.shsconfig.getexternalviewdata().get(fileId)) %></td>
				</tr>
			<%}
		}%>	
	</table>
