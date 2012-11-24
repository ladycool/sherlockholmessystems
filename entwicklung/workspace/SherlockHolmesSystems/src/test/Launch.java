package test;

import java.io.File;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.crypto.Cipher;

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
		
		String username = "luis11",password="luis";
		HashMap<String, String> attribute = new HashMap<String, String>();
		int i = 1;
		
		if(i == 0){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			attribute.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attribute);
		//Controller.shsconfig.createfolder("test1");
		}else if(i == 1){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			Controller.shsuser = Controller.shsconfig.loginSHS("signin",attribute);
		
		}else{
			System.out.println('~'-'"');
			
		}
		
		/*
		System.out.println(Controller.shsuser == null);
		System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
		System.out.println(Controller.shsuser.getattr(Controller.shsconfig.userId));

		File file = new File("C:/Users/x3ro/Desktop/hallo.txt");
		
		Controller.shsconfig.uploadfile(file);
		
		String fileId = "15";
		String[] userlist = {"patrick3","patrick4"};
		
		Controller.shsconfig.createticket(fileId, userlist);
		*/
		Controller.shsconfig.delete(Controller.shsconfig.reader, "", "1");
		
	
	}

}