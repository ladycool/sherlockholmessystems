<%@ page import="CONTROLLER.Controller" %>
<%@ page import="java.util.HashMap" %>


<%String absPath = request.getContextPath();%>
<link href="<%=absPath+Controller.shsconfig.classes %>" type="text/css" rel="stylesheet">
<link href="<%=absPath+Controller.shsconfig.tags %>" type="text/css" rel="stylesheet">
<script src="<%=absPath+Controller.shsconfig.jquery %>" type="text/javascript"></script>	
<script src="<%=absPath+Controller.shsconfig.jsmeth %>" type="text/javascript"></script>

<!-- Die Datenbankanbindung sollte ebenfalls hier erfolgen, Ausgabebefehl: out.system()-->


<%
String  
status = request.getParameter(Controller.shsconfig.ajstatus);//owner || reader
;

switch(request.getParameter("case")){
	case"setting": //--> NICE TO HAVE
		//language & layout & passwort & username &...
		break;
	case"help":
		
		break;
	case"userinfo"://NICE TO HAVE
		break;
	case"signout"://Siehe shsindex.jsp
		break;
	case"viewfile"://muss		 		
		String
		id = request.getParameter(Controller.shsconfig.ajId);//ticketid oder fileid
		
		HashMap<String,String> filedata = Controller.shsconfig.previewfile(id,status);
		
		//output ergebnis
		break;
	case"clickonfile"://muss
		//no break
	case"clickonfolder"://muss
		break;
	case"upload"://muss
		String
		localpath = request.getParameter(Controller.shsconfig.ajlocalpath),
		newpath = request.getParameter(Controller.shsconfig.ajnewpath);
		
		Controller.shsconfig.uploadfile(localpath, newpath);
		//--> reload javascript
	
		break;
	case"share"://muss
		break;
	case"unshare"://muss
		break;
	case"rename"://file & folder --> NICE TO HAVE
		break;
	case"delete"://file & folder
		String
		datatype = request.getParameter(Controller.shsconfig.ajdatatyp),//folder || (intern)file || ticket
		data = request.getParameter(Controller.shsconfig.ajdata)//folder -> path || (intern) file -> fileId || externe file/ticket -> ticketId
		;
		
		Controller.shsconfig.delete(status,data,datatype);
		break;
	case"openfolder"://nice to have
		break;
	case"closefolder"://nice to have--> toggle
		break;
	case"createfolder"://muss
		//neues Verzeichnis
		break;
	case"refresh"://muss
		break;
	default:

}%>

	
