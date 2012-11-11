 <%@ page import="CONTROLLER.Controller" %>
 
 <%
	String loginPath = request.getContextPath();
 %>
 
<center>
	<div class= loginDiv id="<%=Controller.shsconfig.signinId%>">
		<form action="" method="POST"><!-- Zusatzpfad zum Testen: /VIEW/controllboard.jsp -->
			<%@ include file="/VIEW/VIEWCONTROLLER/signin.jsp" %>
		</form>
	</div>
</center>