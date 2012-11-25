<%@page import="java.io.DataInputStream"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="CONTROLLER.Controller" %>

<!-- @Autor: Patrick -->
<% /*
	String username = request.getParameter("username"); 
	session.setAttribute("username", username);
	application.setAttribute("username", username);
	session.getAttribute("username");	
	application.getAttribute("username");//this is the context object used in servelts
	
	//only available in jsp: Pagecontent object
	pageContext.setAttribute("pcUsername", username);
	pageContext.findAttribute("username"); //it will look in all these objects
	//and scope
	*/
	%>
<%
	session.setAttribute(Controller.shsconfig.notice,"");
	session.setAttribute(Controller.shsconfig.usernameId,request.getParameter(Controller.shsconfig.usernameId));	
%>
	
<%	
	String type = "";

	if(!(request.getParameter(Controller.shsconfig.usernameId) == null || request.getParameter(Controller.shsconfig.passwordId) == null)){
		if(!(request.getParameter(Controller.shsconfig.usernameId).isEmpty() || request.getParameter(Controller.shsconfig.passwordId).isEmpty())){
			
			HashMap<String,String>attributes = new HashMap<String,String>();
			Map<String,String[]>temp = request.getParameterMap();
			type = temp.get(Controller.shsconfig.signactionId)[0];
			
			for(String key : temp.keySet()){
				if(!key.equals(Controller.shsconfig.signactionId) && !temp.get(key)[0].isEmpty()){
				attributes.put(key, temp.get(key)[0]);
				}
			}
			//Controller.shsconfig.keypath = request.getRealPath("/keys");				
			//EINLOGGEN_________________________________________________
			
			if(session.getAttribute(Controller.shsconfig.shsuser) == null){
				Controller.shsuser = Controller.shsconfig.loginSHS(type,attributes);
				
				if(Controller.shsuser != null){
					session.setAttribute(Controller.shsconfig.shsuser, Controller.shsuser);
					//show a gimp loading animation
					Controller.shsconfig.loadUserView();
				}
			}

		}else{
			if(type.equals(Controller.shsconfig.signactionA)){
				session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(51));
			}else if(type.equals(Controller.shsconfig.signactionB)){
				session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(52));
			}		
			response.sendRedirect(absPath);//Alles ist in Ordnung
		}
	}else if(session.getAttribute(Controller.shsconfig.shsuser) != null){
				if(request.getParameter("uploadfile") != null){	
					if(request.getParameter("uploadfile").equals("uploadfile")){
						String contentType = request.getContentType();
						 out.println("<br>Content type is :: " +contentType);  
						 if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)){ 
							DataInputStream input = new DataInputStream(request.getInputStream());
							Controller.shsconfig.uploadfile(input,request.getParameter("file"));	
						 }
						}
					}					
	}else{
		session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(14));
		response.sendRedirect(absPath);
	}
%>



<div class = "main" id="<%=Controller.shsconfig.mainId%>" class="maxwidth">
	<table>
		<tr><td colspan="2" class="headerA"><!-- HEADER -->
			<div id="<%=Controller.shsconfig.headerId%>">
				<%@ include file="VIEWCONTROLLER/header.jsp" %>
			</div>
		</td></tr><!-- HEADER END-->
		<tr><!-- MAIN -->
			<td class="headerB" id="<%=Controller.shsconfig.mainwesttdId%>"><!-- Header f�r main WEST -->
				<%=Controller.shsdb.text(48) %>
			</td>
			<td class="headerB" id="<%=Controller.shsconfig.mainnordtdId%>"><!-- Header f�r main NORD -->
				<table>
					<tr>
						<td><%=Controller.shsdb.text(37) %></td>
						
						<td class="right"><%=Controller.shsgui.createImg(Controller.shsconfig.mainnordarrowId, Controller.shsconfig.mainnordId, 
															Controller.shsconfig.arrowup, Controller.shsconfig.arrowdown,"verti") %></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td rowspan="3" class="body"><!-- MAINWEST -->
				<div id="<%=Controller.shsconfig.mainwestId%>">
					<%@ include file="VIEWCONTROLLER/mainwest.jsp" %>
				</div>				
			</td>
			<td class="body"><!-- MAINNORD -->
				<div id="<%=Controller.shsconfig.mainnordId%>">
					<%@ include file="VIEWCONTROLLER/mainnord.jsp" %>
				</div>
			</td>
		</tr>
		<tr>
			<!-- <td></td> -->
			<td class="headerB"><!-- Header f�r main SOUTH -->
				<table>
					<tr>
						<td><%=Controller.shsdb.text(49) %></td>
						<td class="right">
							<%=Controller.shsgui.createImg(Controller.shsconfig.mainsoutharrowId, Controller.shsconfig.mainsouthId, 
												Controller.shsconfig.arrowup, Controller.shsconfig.arrowdown,"verti") %>
						</td>
					</tr>
				</table>			
			</td>
		</tr>
		<tr>
			<!-- <td></td> -->
			<td class="body">
				<div id="<%=Controller.shsconfig.mainsouthId%>" class="body">
					<%@ include file="VIEWCONTROLLER/mainsouth.jsp" %>
				</div>
			</td>
		</tr><!-- MAIN END -->
		<tr><!-- CONSOLE -->
			<td class="headerB" colspan="2"><!-- Header f�r Console -->
				<table>
					<tr>
						<td><%=Controller.shsdb.text(50) %></td><!-- Sp�ter DATENBANK AUFRUF -->
						<td class="right">
							<%=Controller.shsgui.createImg(Controller.shsconfig.consolearrowId, Controller.shsconfig.consoleId, 
															Controller.shsconfig.arrowup, Controller.shsconfig.arrowdown,"verti") %>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="2" id="<%=Controller.shsconfig.consoletdId%>" class="console">
			<div id="<%=Controller.shsconfig.consoleId%>" title="<%=Controller.shsdb.text(23)%>">
				<table>
					<tr><td>
					</td></tr>
				</table>
			</div>
		</td></tr><!-- CONSOLE END -->
	</table>
	<!-- <script>documentready('<%=Controller.shsconfig.bodyId%>','<%=Controller.shsconfig.mainwestId%>');</script>-->
</div>

<div id="<%=Controller.shsconfig.popupId %>" class="popup">
<!-- Replace CLOSE by close-Tag -->
</div>

