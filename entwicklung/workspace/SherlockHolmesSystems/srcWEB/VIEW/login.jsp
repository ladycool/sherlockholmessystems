 <%@ page import="CONTROLLER.Controller" %>
 
 <%
	String loginPath = request.getContextPath();
 %>
 
<center>
	<div class= loginDiv id="<%=Controller.shsconfig.signinId%>">
		<form action="" method="POST"><!-- Zusatzpfad zum Testen: /VIEW/controllboard.jsp -->
			<%@ include file="/VIEW/VIEWCONTROLLER/signin.jsp" %>
		</form>
		<%if(session.getAttribute(Controller.shsconfig.notice) != null){%>
			<table><tr>
			<td class="notice" id="<%=Controller.shsconfig.consoleId%>"><%=session.getAttribute(Controller.shsconfig.notice) %></td>
			</tr></table>
		<%}%>
	</div>
</center>