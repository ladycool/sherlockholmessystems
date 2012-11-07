package test;

import java.io.File;
import java.io.UnsupportedEncodingException;

import SERVICE.Config;
import SERVICE.Myadmin;

public class Launch {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a = "0123456789";
		
		System.out.println(a.getBytes("UTF-8"));
		
		byte[] b = a.getBytes("UTF-8");
		String c = new String(b,"UTF-8");
		System.out.println(c);
		
	}

}
