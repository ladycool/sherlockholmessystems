package test;

import javax.crypto.Cipher;

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
				Controller.shsconfig.symInstance, 
				Controller.shsconfig.asymInstance);
		
		String b = a.crypt(wer, "AES", Cipher.ENCRYPT_MODE);
		
		System.out.println(b);
	}

}
