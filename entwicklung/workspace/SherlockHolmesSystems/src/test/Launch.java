package test;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		ResultSet result = a.select("text", "deu", "id=3");
		result.next();
		System.out.println(result.getString("deu"));
	}

}
