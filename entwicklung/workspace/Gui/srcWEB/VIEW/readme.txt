Hier kommen alle .jsp-Dateien


	//how to get context path request.getContextPath()
	//how to get uri request.getRequestURI()
	//how to get the current url
	String currentURL = request.getRequestURL().toString();
	String newURL = "";
	
	/*
	if(session.getAttribute("user") == null){ 
		newURL = currentURL.replaceAll("shsIndex.jsp$", Config.login);
	}else{
		newURL = currentURL.replaceAll("shsIndex.jsp$", Config.cb);
	}
	response.sendRedirect(newURL);
	*/