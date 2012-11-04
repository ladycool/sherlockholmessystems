<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<p>Login Successful</p>
	<jsp:useBean id="username" class="java.lang.String" scope="request">
	<!-- If the object is not available the bean creates a new bean with the scope=scope and the value=id-->
	<!-- While creating it for the 1st time whatever code in this tag will be execute after creating the object-->
		<jsp:setProperty property="username" name="user" value="NewUser"></jsp:setProperty>
	</jsp:useBean>
	
	<p>Hello <jsp:getProperty property="here the get of the attribute" name="username"></jsp:getProperty> </p>
	<!-- <p>Hello <%=(String)session.getAttribute("username") %></p> -->
	
	
</body>
</html>