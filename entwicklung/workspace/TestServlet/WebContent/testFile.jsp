<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Simple JSP File</title>
</head>
<body>
	<form action="http://localhost:8080/TestServlet/testFile.jsp" method="POST">
		<input type="text" name="thisisatest" size="100">
		<input type="submit" value="test test">
	</form>
	<%=request.getParameter("thisisatest") %>
	<br><br><br><br>
	<%!
	public int add(int a,int b){
		return a+b;
	}
	%>
	<h3>lalala</h3><br/>
	<%
	//ServletContext context = request.getServletContext();
	int i = 1;
	int j = i+4;
	out.println("Value of j "+j);
	int k = add(345,-33);
	%>
	he he he is also <%=i+4 %>
	<br/>
	<%=k %>
	
	<% 
	String username = request.getParameter("username"); 
	session.setAttribute("username", username);
	application.setAttribute("username", username);
	session.getAttribute("username");	
	application.getAttribute("username");//this is the context object used in servelts
	
	//only available in jsp: Pagecontent object
	pageContext.setAttribute("pcUsername", username);
	pageContext.findAttribute("username"); //it will look in all these objects
	//and scope
	%>
</body>
</html>