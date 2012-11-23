<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="CONTROLLER.Controller" %>

<%
	String
	absPath = request.getContextPath()
	;
	//Controller.shsconfig.keypath = absPath + Controller.shsconfig.keypath;
%>

<%!
	//init
	/*
	public void jspInit(){
		String defaultUser = getServletConfig().getInitParameter("defaultUser");
		//servlet.config -> from the config
		ServletContext context = getServletContext();//context of the converted jsp class
		//servlet.context -> is scope-Object available accros the application
		context.setAttribute("defaultUser", defaultUser);
	}
	*/
	public void jspInit(){
		
	}
	


	//destroy
	public void jspdestroy(){
		Controller.shsdb.close();
		
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">		
		<link href="<%=absPath+Controller.shsconfig.classes %>" type="text/css" rel="stylesheet">
		<link href="<%=absPath+Controller.shsconfig.tags %>" type="text/css" rel="stylesheet">
		<script src="<%=absPath+Controller.shsconfig.jquery %>" type="text/javascript"></script>	
		<script src="<%=absPath+Controller.shsconfig.jsmeth %>" type="text/javascript"></script>	
		
		<title><%=Controller.shsconfig.title%></title>
	</head>
	
	<body>
		<%if(session.getAttribute(Controller.shsconfig.shsuser) == null && 
			request.getParameter(Controller.shsconfig.signactionId) == null){%>
			<%@ include file="VIEW/login.jsp" %>
		<%}else{%>
			<%@ include file="VIEW/controllboard.jsp" %>	
		<%}%>
	</body>
</html>
