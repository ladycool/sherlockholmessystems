 <%@ page import="CONTROLLER.Controller" %>
 
 <%
	//absPath = request.getContextPath();
 %>
 
<center>
	<div id="<%=Controller.shsconfig.signinId%>" class="maxwidth">
		<form action="<%=request.getContextPath()%>/VIEW/controllboard.jsp" method="post">
			<%@ include file="/VIEW/VIEWCONTROLLER/signin.jsp" %>
		</form>
	</div>
</center>