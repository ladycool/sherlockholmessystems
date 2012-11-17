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
		/*Dieser Test ist gultig und erfolgreich
		 * 
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put(Controller.shsconfig.username, "test3");
		attributes.put(Controller.shsconfig.password, "test3");
		attributes.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attributes);
		System.out.println(Controller.shsuser == null);
		 */

		
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put(Controller.shsconfig.username, "test3");
		attributes.put(Controller.shsconfig.password, "test3");
		Controller.shsuser = Controller.shsconfig.loginSHS("signin",attributes);
		System.out.println(Controller.shsuser == null);
		
	}

}
