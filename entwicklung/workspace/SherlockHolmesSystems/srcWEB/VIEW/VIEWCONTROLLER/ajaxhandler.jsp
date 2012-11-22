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
status = request.getParameter(Controller.shsconfig.ajstatus),//owner || reader
id = request.getParameter(Controller.shsconfig.ajId),//ticketid oder fileid
userlist[] = request.getParameterValues(Controller.shsconfig.ajuserlist)
;
int 
fileId = Integer.parseInt(id),
casenr = Integer.parseInt(request.getParameter("case"));

switch(casenr){
	case 1: //"setting"--> NICE TO HAVE
		//language & layout & passwort & username &...
		break;
	case 2://"help"
		
		break;
	case 3://"userinfo" -->NICE TO HAVE
		break;
	case 4://"signout" --> Siehe shsindex.jsp
		break;
	case 5://"viewfile" --> muss		 				
		HashMap<String,String> filedata = Controller.shsconfig.previewfile(id,status);
		
		//output ergebnis
		break;
	case 6://"clickonfile" --> muss
		//no break
	case 7://"clickonfolder" --> muss
		break;
	case 8://"upload" --> muss
		String
		localpath = request.getParameter(Controller.shsconfig.ajlocalpath),
		newpath = request.getParameter(Controller.shsconfig.ajnewpath);
		
		Controller.shsconfig.uploadfile(localpath, newpath);
		//--> reload javascript
	
		break;
	case 9://"share" --> muss
		Controller.shsconfig.createticket(fileId, userlist);
		break;
	case 10://"unshare" --> muss
		String
		ticketIdlist[] = request.getParameterValues(Controller.shsconfig.ajuserlist)
		;
		Controller.shsconfig.deleteticket(fileId, userlist, ticketIdlist);
		break;
	case 11://"rename" file & folder --> NICE TO HAVE
		break;
	case 12://"delete" --> file & folder
		String
		datatype = request.getParameter(Controller.shsconfig.ajdatatyp),//folder || (intern)file || ticket
		data = request.getParameter(Controller.shsconfig.ajdata)//folder -> path || (intern) file -> fileId || externe file/ticket -> ticketId
		;
		
		Controller.shsconfig.delete(status,data,datatype);
		break;
	case 13://"openfolder" --> nice to have
		break;
	case 14://"closefolder" --> nice to have--> toggle
		break;
	case 15://"createfolder" --> muss
		String foldername = request.getParameter(Controller.shsconfig.ajnewpath);
		Controller.shsconfig.createfolder(foldername);
		
		//--> reload mainnorth
		break;
	case 16://"refresh" --> muss
		break;
	case 17://"showlist" --> 
		//first to created ticket --> getallusers
		//second to delete ticket --> getcurrentreaders
		break;
	default:

}%>

	
