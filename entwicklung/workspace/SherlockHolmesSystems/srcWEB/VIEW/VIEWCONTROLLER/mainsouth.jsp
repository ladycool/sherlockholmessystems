	<!-- protected HashMap<String, ArrayList<String>> externalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	<%
		String[] filedata;//ticketid,filename
	%>
	
	<table><tr>
		<%for(String sentby : Controller.shsconfig.getexternalviewdata().keySet()){%>
			<td><div>
				<ul><li class="folder"><%=sentby%></li>
					<ul><%for(String file : Controller.shsconfig.getexternalviewdata().get(sentby)){
							filedata = file.split(Controller.shsconfig.sep+Controller.shsconfig.sep);
						%>
						<li class="files"><%=Controller.shsgui.createA("",filedata[1])%></li>
						<!-- ticketid = filedata[0] onclick -->
					<%}%></ul>
				</ul>
			</div></td>
		<%}%>	
	</tr></table>