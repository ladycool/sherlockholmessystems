<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@ page import="CONTROLLER.Controller" %>

<!-- @Autor: Patrick -->
<% /*
	String username = request.getParameter("username"); 
	session.setAttribute("username", username);
	application.setAttribute("username", username);
	session.getAttribute("username");	
	application.getAttribute("username");//this is the context object used in servelts
	
	//only available in jsp: Pagecontent object
	pageContext.setAttribute("pcUsername", username);
	pageContext.findAttribute("username"); //it will look in all these objects
	//and scope
	*/
	%>
<%
	session.setAttribute(Controller.shsconfig.notice,"");
	session.setAttribute(Controller.shsconfig.usernameId,request.getParameter(Controller.shsconfig.usernameId));
	
%>
	
<%	
	if(!(request.getParameter(Controller.shsconfig.usernameId).isEmpty() || 
			request.getParameter(Controller.shsconfig.passwordId).isEmpty())){
		
		HashMap<String,String>attributes = new HashMap<String,String>();
		Map<String,String[]>temp = request.getParameterMap();
		String type = temp.get(Controller.shsconfig.signactionId)[0];
		
		for(String key : temp.keySet()){
			if(!key.equals(Controller.shsconfig.signactionId) && !temp.get(key)[0].isEmpty()){
			attributes.put(key, temp.get(key)[0]);
			}
		}
		
		Controller.shsuser = Controller.shsconfig.loginSHS(type,attributes);	
		if(Controller.shsuser != null){
			if(type.equals(Controller.shsconfig.signactionA)){//sign
				//show a gimp loading animation
				Controller.shsconfig.loadUserView();			
			}
		}else{
			if(type.equals(Controller.shsconfig.signactionA)){
				session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(51));
			}else if(type.equals(Controller.shsconfig.signactionB)){
				session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(52));
			}		
			response.sendRedirect(absPath);//Alles ist in Ordnung
		}
	}else{
		session.setAttribute(Controller.shsconfig.notice,Controller.shsdb.text(14));
		response.sendRedirect(absPath);
	}
	
%>


