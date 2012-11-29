package SERVICE;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import CONTROLLER.Controller;
import MODEL._Cipher;
import MODEL.enums.Ciphertype;


public class Shscipher extends _Cipher { //http://openbook.galileocomputing.de/java7/1507_22_006.html
	private SecretKey masterkey;
	private SecretKey shssymkey;
	private KeyPair shsasymkeypair;
	
	/*
	 * PRIVATE
	 */
	private static Shscipher _cipher = null;
	private Shscipher(int keysize, String symInstance, String asymInstance) {
		super(keysize, symInstance, asymInstance);
		this.masterkey = this.createsymmetricKey();
		this.shssymkey = this.createsymmetricKey();
		this.shsasymkeypair = this.createAsymmetricKey();
	}
	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static Shscipher getInstance(int keysize, String symInstance, String asymInstance){
		//SETTER-START
		if(_cipher != null){
			_cipher = new Shscipher(keysize, symInstance, asymInstance);
		}
		//SETTER-END
		return _cipher;
	}
	
	public byte[] getkey(Ciphertype type){
		byte[] toreturn = null;
		if(type.equals(Ciphertype.master)){
			toreturn = this.masterkey.getEncoded();
		}else if(type.equals(Ciphertype.symmetric)){
			toreturn = this.shssymkey.getEncoded();	
		}			
		return toreturn;
	}
	
	public HashMap<String, byte[]> getkey() {
		HashMap<String, byte[]> toreturn = new HashMap<String,byte[]>();
		toreturn.put("prik", this.shsasymkeypair.getPrivate().getEncoded());
		toreturn.put("pubk", this.shsasymkeypair.getPublic().getEncoded());
		
		return toreturn;
	}

	/**
	 * Erzeugt ein Objekt vom Typ Chipher, das zum chiffrieren benötigt wird.
	 * @param type: asymmetric || symmetric || master
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 * @return
	 */
	private Cipher getCipher(Ciphertype type,int cipherMODE){
		Cipher cipher = null;
		try{
			if(type.equals(Controller.shsconfig.asymmetric)){			
				cipher = Cipher.getInstance(this.getInstance(type));
				cipher.init(cipherMODE, this.shsasymkeypair.getPublic());
				//byte[] wrappedKey = cipher.wrap(key);				        
		        
			}else if(type.equals(Controller.shsconfig.symmetric)){
				cipher = Cipher.getInstance(this.getInstance(type));
	            cipher.init(cipherMODE, this.shssymkey);
				
			}else if(type.equals(Controller.shsconfig.master)){			
				cipher = Cipher.getInstance(this.getInstance(type));
				cipher.init(cipherMODE, this.masterkey);					        
			}
		}catch (GeneralSecurityException e){
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		return cipher;
	}
	
	
	@SuppressWarnings("unused")
	@Override
	protected SecretKey createsymmetricKey() {
		SecretKey key = null;
		try {
			KeyGenerator keygen;
			keygen = KeyGenerator.getInstance(this.getInstance(Controller.shsconfig.symmetric));//AES
			SecureRandom random = new SecureRandom();
	        keygen.init(random);
	        key = keygen.generateKey();
	        
		} catch (GeneralSecurityException e) {
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
        
		return key;
	}

	@SuppressWarnings("unused")
	@Override
	protected KeyPair createAsymmetricKey() {
		KeyPair keypair = null;
		try {
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance(this.getInstance(Controller.shsconfig.asymmetric));//RSA
            SecureRandom random = new SecureRandom();
            pairgen.initialize(this.getkeysize(), random);
            keypair = pairgen.generateKeyPair();
			
		} catch (GeneralSecurityException e){
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}

		return keypair;
	}

	@Override
	public String crypt(String tocrypt,Ciphertype type,int cipherMODE) {
		//cipherMODE = Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
		String toreturn="";
		try {
			Cipher cipher = getCipher(type, cipherMODE);
			
			byte[] crypted = cipher.doFinal(tocrypt.getBytes("UTF-8"));
			toreturn = new String(crypted,"UTF-8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		
		return toreturn;
	}
	
	@Override
	public void dataPUSH(String filepath,Ciphertype type,int cipherMODE) {
		// NOT DONE YET. 2 points are missing
		try {
			Cipher cipher = getCipher(type, cipherMODE);
			
			CipherInputStream cipherdata = new CipherInputStream(new DataInputStream(new FileInputStream(filepath)),cipher);
			
			int data = cipherdata.read();
			while(data != -1) {
			  //NOT DONE YET. In DB speichern(data)
			  data = cipherdata.read();
			}
			cipherdata.close();		
			String tosend=""; //NOT DONE YET. I will be coming from the database
			Controller.shsgui.triggernotice(tosend);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		
	}

	@Override
	public void dataPULL(String dbfilename,String localfilepath,Ciphertype type,int cipherMODE) {//NOT DONE
		try {
			Cipher cipher = getCipher(type, cipherMODE);
			
			String tocrypt = "";//TO DO --> Datenbankaufruf			
			String plaintext = crypt(tocrypt, type, cipherMODE);
			
			DataOutputStream out = new DataOutputStream(new FileOutputStream(localfilepath));
			out.writeBytes(plaintext);
		    
		} catch (IOException e) {
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		
	}


}
