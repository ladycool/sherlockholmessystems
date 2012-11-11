package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


import CONTROLLER.Controller;
import SERVICE.Config;
import SERVICE.Myadmin;
import SERVICE.Shscipher;

public class Launch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		String a = "0123456789";
		
		System.out.println(a.getBytes("UTF8"));
		
		byte[] b = a.getBytes("UTF8");
		String c = new String(b,"UTF8");
		System.out.println(c);
		
		/*
		Myadmin db = Myadmin.getInstance();
		System.out.println(db == null);
		*/
		/*
		Shscipher d = Shscipher.getInstance(512, Controller.shsconfig.symInstance, Controller.shsconfig.asymInstance);
		d.dataPUSH("C:/Users/Shazem/Desktop/domains.txt", Controller.shsconfig.asymmetric, Controller.shsconfig.encryptmode);
		
		
		String f="";
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:/Users/Shazem/Desktop/domains.txt")),"UTF8"));
		String linebreak="";
		String str ="";
		while((str = in.readLine()) != null){
		    f += linebreak+str;
		    linebreak="\n";
		    System.out.println(str);
		}
		in.close();
		System.out.println(f);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:/Users/Shazem/Desktop/lalala.txt"),"UTF8"));
		out.write(f);
		out.close();
		*/
		
		String[]wer = null;
		String we = "MAMAlerkqwertlerklololerkpapa";
		wer = we.split("lerk");
		for (int i = 0; i < wer.length; i++) {
			System.out.println(wer[i]);
		}
		
	}

}
