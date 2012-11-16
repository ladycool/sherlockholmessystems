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
		String key = "dsk üüdclsädäööldcö";
		Shscipher a = Shscipher.getInstance(Controller.shsconfig.keysize, 
				Controller.shsconfig.symInstance, 
				Controller.shsconfig.asymInstance);
		
		//a.crypt(key, instance, Cipher.ENCRYPT_MODE);
	}

}
