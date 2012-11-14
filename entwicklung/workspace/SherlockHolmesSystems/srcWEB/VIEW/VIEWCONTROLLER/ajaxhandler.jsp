<%@ page import="CONTROLLER.Controller" %>

<!-- Die Datenbankanbindung sollte ebenfalls hier erfolgen, Ausgabebefehl: out.system()-->


<%switch(request.getParameter("case")){
case"setting": //--> NICE TO HAVE
	//language & layout & passwort & username &...
	break;
case"help":
	break;
case"userinfo":
	break;
case"signout"://Noch nicht sicher
	break;
case"file":
	//no break
case"folder":
	break;
case"upload":
	break;
case"share":
	break;
case"unshare":
	break;
case"rename"://file & folder --> NICE TO HAVE
	break;
case"delete"://file & folder
	break;
case"openfolder":
	break;
case"closefolder":
	break;
case"createfolder":
	break;
case"showexternalfolder":
	break;
default:

}%>

	
