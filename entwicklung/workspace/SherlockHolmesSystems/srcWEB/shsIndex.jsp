<%@ page import="MODEL.Config" %>

<%!
	//init
	public void jspInit(){
		String defaultUser = getServletConfig().getInitParameter("defaultUser");
		//servlet.config -> from the config
		ServletContext context = getServletContext();//context of the converted jsp class
		//servlet.context -> is scope-Object available accros the application
		context.setAttribute("defaultUser", defaultUser);
	}

	//destroy
	public void jspdestroy(){
		
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link href="css/class.css" type="text/css" rel="stylesheet">
		<link href="css/tags.css" type="text/css" rel="stylesheet">
		<script src="jquery/jquery.js" type="text/javascript"></script>
		<title>Sherlock Holmes Systems</title>
	</head>
	<body>
		<div style="width:100%;">
			<%if(session.getAttribute("user") == null){ %>
				<%@ include file="VIEW/login.jsp" %>
			<%}else{%>
				<%@ include file="VIEW/controllboard.jsp" %>	
			<%}%>
		</div>
	</body>
</html>
