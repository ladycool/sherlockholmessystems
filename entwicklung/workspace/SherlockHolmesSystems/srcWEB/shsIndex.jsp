<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="SERVICE.Config" %>

<%
	String
	absPath = request.getContextPath(),
	bodyId = "shsbody"
	;
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
		
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">		
		<link href="<%=absPath+Config.classes %>" type="text/css" rel="stylesheet">
		<link href="<%=absPath+Config.tags %>" type="text/css" rel="stylesheet">
		<script src="<%=absPath+Config.jquery %>" type="text/javascript"></script>	
		<script src="<%=absPath+Config.jsmeth %>" type="text/javascript"></script>	
		
		<title>Sherlock Holmes Systems</title>
	</head>
	<body class="maxwidth" id="<%=bodyId%>" name="<%=bodyId%>">
		<%if(session.getAttribute("user") != null){%>
			<%@ include file="VIEW/login.jsp" %>
		<%}else{%>
			<%@ include file="VIEW/controllboard.jsp" %>	
		<%}%>
	</body>
</html>
