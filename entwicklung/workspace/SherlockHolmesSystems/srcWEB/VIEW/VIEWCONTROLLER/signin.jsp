<%@ page import="MODEL.*" %>
<%@ page import="SERVICE.*" %>
<% 
	GUI gui = new HTML();
	Database db = (Myadmin)session.getAttribute("db");	
%>

<table>
	<tr>
		<td><%=db.text("Username") %></td>
		<td><%=gui.createInput("text","username","",50) %></td>
	</tr>
	<tr>
		<td><%=db.text("Password") %></td>
		<td><%=gui.createInput("password","username","",50) %></td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:right;">
			<a onclick="alert('hiho')"><%=db.text("Not a member yet") %></a><!-- Not a memeber yet -->		
			<%=gui.space(5, Direction.horiz)+gui.createInput("submit","username",db.text(0)) %>
		</td>
	</tr>
</table>