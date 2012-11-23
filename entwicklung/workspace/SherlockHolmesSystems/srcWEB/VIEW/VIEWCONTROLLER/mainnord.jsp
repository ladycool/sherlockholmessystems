	<!-- protected HashMap<String, ArrayList<String>> internalviewdata -->
	<%@ page import="CONTROLLER.Controller" %>
	<%
		String[] filedata;//fileid,filename
		
		String folderpopup=
		"<fieldset>"+
			"<legend>"+""+"</legend>"+
			Controller.shsgui.createInput("file","",50)+
		"</fieldset>";
	%>
	<script type="text/javascript">
				var folderpopup=
					"<fieldset>"+
						"<legend>Fileupload</legend>"+
						"<input type='file' name='file' id='file' size='40px'/>"+
						"<input type='button' name='upload' value='Hochladen'"+
							"onclick='requestToJavahandler(8,{id:\"file\"})'/>"+
					"</fieldset>";
				</script>
	<table><tr>
		<%for(String path : Controller.shsconfig.getinternalviewdata().keySet()){%>
			<td><div>
				<script type="text/javascript">
				<%if(!path.equals(Controller.shsconfig.defaultordner)){%>					
					folderpopup +=
						"<fieldset>"+
						"<legend>Umbenennen</legend>"+
						"<input type='text' name='renametxt' size='50px'/>"+
						"<input type='button' name='renamebut' value='Umbenennen'/>"+
					"</fieldset>"+
					"<fieldset>"+
						"<legend>Sonstiges</legend>"+
						"<input type='button' value='delete' name='deletebut'/>"+
					"</fieldset>";						
				<%}%>
				folderpopup += "<br/>CLOSETAG";
				</script>
				<ul><li class="folder" onclick="simplepopup(event,{width:500,height:200},folderpopup)"><%=path%></li>
					<ul><%for(String file : Controller.shsconfig.getinternalviewdata().get(path)){
							filedata = file.split(Controller.shsconfig.sep+Controller.shsconfig.sep);
						%>
						<li class="files"><%=Controller.shsgui.createA("",filedata[1])%></li>
						<!-- fileid = filedata[0] onclick -->
					<%}%></ul>
				</ul>
			</div></td>
		<%}%>	
	</tr></table>