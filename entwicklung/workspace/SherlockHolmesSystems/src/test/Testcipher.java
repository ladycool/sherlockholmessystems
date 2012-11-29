package test;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.*;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import SERVICE.Shscipher;

import CONTROLLER.Controller;

public class Testcipher {

	/**
	 * @param args
	 * @throws SQLException 
	 * @throws Base64DecodingException 
	 */
	public static void main(String[] args) throws SQLException, Base64DecodingException {
		// TODO Auto-generated method stub
		String _a = "this is a test";
		byte[] a = "this is a test".getBytes();
		String b = "'"+Base64.encode(a)+"'";
		
		
		//String values = "'test_base',"+b+",2,455";
		//Controller.shsdb.insert("testdb","",values,"");
		ResultSet res = Controller.shsdb.select("testdb","*","`key`=455");
		res.first();
		
		//byte[] byt = res.getBytes("password");
		String string = res.getString("password");
		byte[] byt = Base64.decode(string);
		
		System.out.println(new String(byt)+"---"+a+"--------"+string);
		Controller.shsdb.close();
		
	}

}
