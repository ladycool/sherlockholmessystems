	<!-- protected HashMap<String, ArrayList<String>> internalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	<%
		String[] filedata;//fileid,filename
	%>
	
	<table><tr>
		<%for(String path : Controller.shsconfig.getinternalviewdata().keySet()){%>
			<td><div>
				<ul><li class="folder"><%=path%></li>
					<ul><%for(String file : Controller.shsconfig.getexternalviewdata().get(path)){
							filedata = file.split(Controller.shsconfig.sep+Controller.shsconfig.sep);
						%>
						<li class="files"><%=Controller.shsgui.createA("",filedata[1])%></li>
						<!-- fileid = filedata[0] onclick -->
					<%}%></ul>
				</ul>
			</div></td>
		<%}%>	
	</tr></table>