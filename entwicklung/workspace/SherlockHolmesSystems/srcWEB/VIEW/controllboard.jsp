<%@ page import="CONTROLLER.Controller" %>

<!-- @Autor: Patrick -->
<%
	String 
	headerheight = "50px",
	mainwestwidth = "200px"
	;
	
%>


<div id="<%=Controller.shsconfig.mainId%>" class="maxwidth">
	<table>
		<tr><td colspan="2" class="headerA"><!-- HEADER -->
			<div id="<%=Controller.shsconfig.headerId%>">
				<%@ include file="VIEWCONTROLLER/header.jsp" %>
			</div>
		</td></tr><!-- HEADER END-->
		<tr><!-- MAIN -->
			<td class="headerB" id="<%=Controller.shsconfig.mainwesttdId%>"><!-- Header für main WEST -->
				My buddies :)
			</td><!-- Später DATENBANK AUFRUF -->
			<td class="headerB" id="<%=Controller.shsconfig.mainnordtdId%>"><!-- Header für main NORD -->
				<table>
					<tr>
						<td>My folders</td><!-- Später DATENBANK AUFRUF -->
						<td class="right"><%=Controller.shsgui.createImg(Controller.shsconfig.mainnordarrowId, Controller.shsconfig.mainnordId, 
															Controller.shsconfig.arrowup, Controller.shsconfig.arrowdown) %></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td rowspan="3" class="body">
				<div id="<%=Controller.shsconfig.mainwestId%>">
					<%@ include file="VIEWCONTROLLER/mainwest.jsp" %>
				</div>				
			</td>
			<td class="body">
				<div id="<%=Controller.shsconfig.mainnordId%>">
					<%@ include file="VIEWCONTROLLER/mainnord.jsp" %>
				</div>
			</td>
		</tr>
		<tr>
			<!-- <td></td> -->
			<td class="headerB"><!-- Header für main SOUTH -->
				<table>
					<tr>
						<td>My buddie's folders</td><!-- Später DATENBANK AUFRUF -->
						<td class="right">
							<%=Controller.shsgui.createImg(Controller.shsconfig.mainsoutharrowId, Controller.shsconfig.mainsouthId, 
												Controller.shsconfig.arrowdown, Controller.shsconfig.arrowup) %>
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
			<td class="headerB" colspan="2"><!-- Header für Console -->
				<table>
					<tr>
						<td>Console</td><!-- Später DATENBANK AUFRUF -->
						<td class="right">
							<%=Controller.shsgui.createImg(Controller.shsconfig.consolearrowId, Controller.shsconfig.consoleId, 
															Controller.shsconfig.arrowdown, Controller.shsconfig.arrowup) %>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td colspan="2" id="<%=Controller.shsconfig.consoletdId%>" class="console">
			<div id="<%=Controller.shsconfig.consoleId%>" title="<%=Controller.shsdb.text(23)%>">
				<table>
					<tr><td>
						<%!String werfen(int j){
							String t ="";
							for(int i=0;i<j;i++){
								t +="Test\n";
							}
							return t;
						}%>
						<%=werfen(10) %>
					</td></tr>
				</table>
			</div>
		</td></tr><!-- CONSOLE END -->
	</table>
	<script>documentready('<%=Controller.shsconfig.bodyId%>','<%=Controller.shsconfig.mainwestId%>');</script>
</div>