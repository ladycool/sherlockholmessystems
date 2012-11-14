package test;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import MODEL.Database;
import SERVICE.Config;
import SERVICE.Myadmin;


public class Launch {

	/**
	 * @param args
	 * @throws Base64DecodingException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Base64DecodingException{
		
		String password = Base64.encode("asbvc".getBytes());
		System.out.println(password);
		
		String a = new String(Base64.decode(password));
		System.out.println(a);
	}

}
