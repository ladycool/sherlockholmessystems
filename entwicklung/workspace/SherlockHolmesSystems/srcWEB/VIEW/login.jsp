 <%@ page import="CONTROLLER.Controller" %>
 
 <%
	String loginPath = request.getContextPath();
 %>
 
<center>
	<div class= loginDiv id="<%=Controller.shsconfig.signinId%>">
		<form action="" method="POST"><!-- Zusatzpfad zum Testen: /VIEW/controllboard.jsp -->
			<%@ include file="/VIEW/VIEWCONTROLLER/signin.jsp" %>
		</form>
		<%if(!((String)session.getAttribute(Controller.shsconfig.notice)).isEmpty()){%>
			<table><tr>
			<td class="notice" id="<%=Controller.shsconfig.noticeId%>"><%=session.getAttribute(Controller.shsconfig.notice) %></td>
			</tr></table>
		<%}%>
	</div>
</center>