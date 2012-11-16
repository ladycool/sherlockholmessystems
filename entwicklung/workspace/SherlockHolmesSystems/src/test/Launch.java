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
		
		Myadmin a  = Myadmin.getInstance();
		String result = a.text(55);
		//result.next();
		System.out.println(result);
		/*
		
		HashMap<String, String> attributes = new HashMap<String, String>();
		attributes.put(Controller.shsconfig.username, "test");
		attributes.put(Controller.shsconfig.password, "test");
		attributes.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attributes);
		*/
	}

}
