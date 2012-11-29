package test;

import java.io.File;
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
	
	
	static HashMap<String,String> getcurrentreader(String fileId) throws SQLException, Base64DecodingException{
		HashMap<String,String> toreturn = new HashMap<String,String>();
		byte[] _ticketsId=null,_readers=null;
		String ticketsId="",readers="";		
		ResultSet result = Controller.shsdb.select("files_test","k_ticketsId,readers","id="+fileId+" AND k_ticketsId IS NOT NULL AND readers IS NOT NULL");
			
		if(result.first()){// && result.getString("k_ticketsId") != null && result.getString("readers") != null
		
				_ticketsId = Base64.decode(result.getString("k_ticketsId"));
				_readers = Base64.decode(result.getString("readers"));
				
				_ticketsId = Controller.shscipher.crypt(_ticketsId,"AES",Cipher.DECRYPT_MODE); 
				_readers = Controller.shscipher.crypt(_readers,"AES",Cipher.DECRYPT_MODE); 
				
				ticketsId = new String(_ticketsId);
				readers = new String(_readers);
				//holt alle user die bereits eine Erlaubnis haben.
			
		}
		toreturn.put("ticketIdlist",ticketsId);
		toreturn.put("readerlist",readers);
		return toreturn;
	}
	
	/**
	 * @param args
	 * @throws Base64DecodingException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Base64DecodingException, SQLException{
		//Dieser Test ist gultig und erfolgreich
		
		String username = "patrick",password="shs";//id=2
		HashMap<String, String> attribute = new HashMap<String, String>();
		HashMap<String,String> temp = new HashMap<String,String>();
		int i = 2;
		
		if(i == 0){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			attribute.put(Controller.shsconfig.language, "eng");
		
		Controller.shsuser = Controller.shsconfig.loginSHS("signup",attribute);
		System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
		//Controller.shsconfig.createfolder("test1");
		}else if(i == 1){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			Controller.shsuser = Controller.shsconfig.loginSHS("signin",attribute);
			System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
			
			/*			
			File file = new File("C:/Users/Shazem/Desktop/sss.txt");
			Controller.shsconfig.uploadfile(file);
			*/
						
			String id = "2";
			/*
			String[]userlist = new String[]{"patrick2"};
			Controller.shsconfig.createticket(id, userlist);
			*/
			/*
			temp = Controller.shsconfig.previewfile(id, Controller.shsconfig.reader);
			System.out.println(temp.get("filepath"));
			System.out.println(temp.get("content"));
			*/
			/*
			Controller.shsconfig.loadinternalview();
			*/
			getcurrentreader(id);
			
			
		}else{
			String temp1[] = "8&2&3&4".split("&");
			System.out.println(temp1[3]);
			
		}
			
	}

}
