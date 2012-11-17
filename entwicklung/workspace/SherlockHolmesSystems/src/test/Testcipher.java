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
		String wer= "Ha";
		System.out.println("Text = "+wer);
		byte[] _wer = wer.getBytes();
		Shscipher a = Shscipher.getInstance(Controller.shsconfig.keysize, 
				"AES", 
				Controller.shsconfig.asymInstance);
		
		byte[] b = a.crypt(_wer, "RSA", Cipher.ENCRYPT_MODE);
		
		System.out.println("b_byte= "+b);
		System.out.println("b_string= "+new String(b));
		
		byte[] c = a.crypt(b, "RSA", Cipher.DECRYPT_MODE);
		
		System.out.println("c_byte= "+c);
		System.out.println("c:string= "+new String(c));
	}

}
