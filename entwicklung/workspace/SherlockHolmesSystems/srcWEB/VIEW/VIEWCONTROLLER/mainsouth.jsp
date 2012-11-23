	<!-- protected HashMap<String, ArrayList<String>> externalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	<%
		//obwohl der Checker mekert ist alles richtig, das Objekt wird nämlich bereits voher erzeugt.
		filedata = new String[]{};//ticketid,filename
	%>
	
	<table><tr>
		<%
		if(!Controller.shsconfig.getexternalviewdata().isEmpty()){
			for(String sentby : Controller.shsconfig.getexternalviewdata().keySet()){%>
				<td><div>
					<ul><li class="folder" onclick="simplepopup(event,{width:100,height:100},'')"><%=sentby%></li>
						<ul><%for(String file : Controller.shsconfig.getexternalviewdata().get(sentby)){
								filedata = file.split(Controller.shsconfig.sep+Controller.shsconfig.sep);
							%>
							<li class="files"><%=Controller.shsgui.createA("",filedata[1])%></li>
							<!-- ticketid = filedata[0] onclick -->
						<%}%></ul>
					</ul>
				</div></td>
			<%}
		}%>	
	</tr></table>