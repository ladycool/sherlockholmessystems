<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="MODEL.Config" %>
<center>
	<div>
		<%@ include file="VIEWCONTROLLER/signin.jsp" %>
	</div>
	<div id=<%=Config.signupId %>><!-- Das kommt erst später -->
		<%@ include file="VIEWCONTROLLER/signup.jsp" %>
	</div>
</center>			
		