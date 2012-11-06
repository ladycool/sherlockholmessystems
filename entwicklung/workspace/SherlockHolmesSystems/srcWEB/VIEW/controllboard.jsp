<%@ page import="SERVICE.Config" %>

<!-- @Autor: Patrick -->
<%
	
%>


<div id="<%=Config.mainId%>" class="maxwidth">
	<table class="maxwidth">
		<tr><td colspan="2">
			<div id="<%=Config.headerId%>">
				<%@ include file="VIEWCONTROLLER/header.jsp" %>
			</div>
		</td></tr>
		<tr>
			<td class="right" Style="max-width=30%;"><!-- Header für main WEST -->
				<%=Config.shsgui.createImg(Config.mainwestId,Config.arrowleft,Config.arrowright) %>
			</td>
			<td class="right"><!-- Header für main NORD -->
				<%=Config.shsgui.createImg(Config.mainnordId,Config.arrowup,Config.arrowdown) %>
			</td>
		</tr>
		<tr>
			<td rowspan="3">
				<div id="<%=Config.mainwestId%>">
					<%@ include file="VIEWCONTROLLER/mainwest.jsp" %>
				</div>				
			</td>
			<td>
				<div id="<%=Config.mainnordId%>">
					<%@ include file="VIEWCONTROLLER/mainnord.jsp" %>
				</div>
				<hr>
			</td>
		</tr>
		<tr>
			<!-- <td></td> -->
			<td class="right"><!-- Header für main SOUTH -->
				<%=Config.shsgui.createImg(Config.mainsouthId,Config.arrowdown,Config.arrowup) %>
			</td>
		</tr>
		<tr>
			<!-- <td></td> -->
			<td>
				<div id="<%=Config.mainsouthId%>">
					<%@ include file="VIEWCONTROLLER/mainsouth.jsp" %>
				</div>
			</td>
		</tr>
		<tr>
			<td class="right" colspan="2"><!-- Header für Console -->
				<%=Config.shsgui.createImg(Config.consoleId,Config.arrowdown,Config.arrowup) %>
			</td>
		</tr>
		<tr><td colspan="2">
			<div id="<%=Config.consoleId%>" title="<%=Config.shsdb.text(23)%>" style="max-height:250px;">
				<%!String werfen(int j){
					String t ="";
					for(int i=0;i<j;i++){
						t +="Test\n";
					}
					return t;
				}%>
				<%=werfen(10000) %>
			</div>
		</td></tr>
	</table>
</div>