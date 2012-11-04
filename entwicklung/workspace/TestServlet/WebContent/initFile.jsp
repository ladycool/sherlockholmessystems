<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<%!//init
public void jspInit(){
	String defaultUser = getServletConfig().getInitParameter("defaultUser");
	//servlet.config -> from the config
	ServletContext context = getServletContext();//context of the converted jsp class
	//servlet.context -> is scope-Object available accros the application
	context.setAttribute("defaultUser", defaultUser);
}
%>
<body>
This is the default User from the servlet config 
	<%=getServletConfig().getInitParameter("defaultUser")
	%>
	<br/>
This is the value in the servlet context 
	<%=getServletContext().getAttribute("defaultUser")
	%>

</body>
</html>