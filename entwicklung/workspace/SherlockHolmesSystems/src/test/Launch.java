package test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;
import MODEL.Database;
import SERVICE.Config;
import SERVICE.Myadmin;


public class Launch {

	/**
	 * @param args
	 * @throws Base64DecodingException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Base64DecodingException, SQLException{
		//Dieser Test ist gultig und erfolgreich
		
		String username = "patjnijbohkick",password="patrick";
		HashMap<String, String> attributes = new HashMap<String, String>();
		int i = 1;
		
		if(i == 0){
		
			attributes.put(Controller.shsconfig.username, username);
			attributes.put(Controller.shsconfig.password, password);
			attributes.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attributes);
		}else if(i == 1){
		
			attributes.put(Controller.shsconfig.username, username);
			attributes.put(Controller.shsconfig.password, password);
			Controller.shsuser = Controller.shsconfig.loginSHS("signin",attributes);
		
		}else{
			System.out.println('~'-'"');
			
		}
		
		System.out.println(Controller.shsuser == null);
		
		String localpath = "C:/Users/Sandra/Desktop/Benutzer.txt";
		String newpath = "Benutzer";
		
		Controller.shsconfig.uploadfile(localpath, newpath);
		

		
		
	}

}
