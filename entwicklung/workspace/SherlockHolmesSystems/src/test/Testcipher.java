package test;

import javax.crypto.*;

import SERVICE.Shscipher;

import CONTROLLER.Controller;

public class Testcipher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String wer= "dsk üüdclsädäööldcö";
		Shscipher a = Shscipher.getInstance(Controller.shsconfig.keysize, 
				"AES/CBC/PKCS5Padding", 
				Controller.shsconfig.asymInstance);
		
		String b = a.crypt(wer, "AES/CBC/PKCS5Padding", Cipher.ENCRYPT_MODE);
		
		System.out.println(b);
		
		String c = a.crypt(b, "AES/CBC/PKCS5Padding", Cipher.DECRYPT_MODE);
		
		//System.out.println(c);
	}

}
