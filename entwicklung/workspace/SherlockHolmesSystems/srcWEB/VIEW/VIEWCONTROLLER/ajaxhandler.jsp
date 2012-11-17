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
case"file"://muss
	String 
	type = request.getParameter("type");
	id = request.getParameter("id");
	
	hashMap<> filedata = Controller.shsconfig.previewfile();
	;

	
	//if type = intern --> id = fileid
			//direkt in file-tbl
			/*
			secretkey ents(pseuso)
			*/
			
	//else if type = extern --> id = ticket
		// in ticket-tbl --> fileid
		// in file-tbl
		
		
	//ergebnis = Controller.shscipher.crypt(tocrypt,pseudokey, "aes", decript)	
			
	//out.print(ergebnis)
	
	//no break
case"folder"://muss
	break;
case"upload"://muss
	
	break;
case"share"://muss
	break;
case"unshare":
	break;
case"rename"://file & folder --> NICE TO HAVE
	break;
case"delete"://file & folder
	break;
case"openfolder"://nice to have
	break;
case"closefolder"://nice to have
	break;
case"createfolder"://muss
	//neu Verzeichnis
	break;
case"showexternalfolder"://muss
	break;
default:

}%>

	
