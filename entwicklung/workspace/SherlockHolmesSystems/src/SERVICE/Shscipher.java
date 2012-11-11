package SERVICE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import CONTROLLER.Controller;
import MODEL._Cipher;
import MODEL.enums.Ciphertype;

/**
 * @author Shazem (Patrick) && Cedrick
 */
public class Shscipher extends _Cipher { //http://openbook.galileocomputing.de/java7/1507_22_006.html
										//http://herbertwu.wordpress.com/2010/03/06/a-simple-string-cipher-in-java-6/
										//http://herbertwu.wordpress.com/2010/03/06/a-simple-string-cipher-in-java-6/
	//How to create a pdf file in java (Nice to have) --> http://www.vogella.com/articles/JavaPDF/article.html
	
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
		if(_cipher == null){
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
			cipher = Cipher.getInstance(this.getInstance(type));//AES || RSA
			
			if(type.equals(Controller.shsconfig.asymmetric)){
				if(cipherMODE == Cipher.ENCRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPublic());
				}else if(cipherMODE == Cipher.DECRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPrivate());
				}
			}else if(type.equals(Controller.shsconfig.symmetric)){
	            cipher.init(cipherMODE, this.shssymkey);				
			}else if(type.equals(Controller.shsconfig.master)){			
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
	protected SecretKey createsymmetricKey(String pseudokey) {
		SecretKey key = null;
		try {
			if(pseudokey.isEmpty()){
				KeyGenerator keygen = KeyGenerator.getInstance(this.getInstance(Controller.shsconfig.symmetric));//AES
				SecureRandom random = new SecureRandom();
		        keygen.init(random);
		        key = keygen.generateKey();
			}else{
				key = new SecretKeySpec(pseudokey.getBytes(),this.getInstance(Controller.shsconfig.symmetric));//AES
			}
		} catch (GeneralSecurityException e) {
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
        
		return key;
	}
	
	@Override
	protected SecretKey createsymmetricKey() {
		return createsymmetricKey("");
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
			
			byte[] crypted = cipher.doFinal(tocrypt.getBytes("UTF8"));
			toreturn = new String(crypted,"UTF8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		
		return toreturn;
	}
	
	@Override
	public String encryptfile(String filepath,String pseudokey){
		String toreturn = "";
		try {
			//Lesen der Datei
			BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath)),"UTF8"));
			String tocrypt = "",str="";
			while((str = file.readLine()) != null){
			    tocrypt += str+Controller.shsconfig.txtlinebreak;//Puffern <shsbr/>
			    //System.out.println(str);
			}
			tocrypt += Controller.shsuser.getattr("metadata");
			file.close();

			
			//Verschlüsselung des Inhaltes
			SecretKey key = new SecretKeySpec(pseudokey.getBytes(),this.getInstance(Controller.shsconfig.symmetric));
			Cipher cipher = Cipher.getInstance(this.getInstance(Controller.shsconfig.symmetric));
			cipher.init(Controller.shsconfig.encryptmode, key);
			byte[] crypted = cipher.doFinal(tocrypt.getBytes("UTF8"));
			toreturn = new String(crypted,"UTF8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt	
			
		} catch (GeneralSecurityException | IOException e) {
			Controller.shsgui.triggernotice(e);
		}	
		
		return toreturn;
	}
	
	@Override
	protected String[] readfile(String pseudokey, int fileId){
		String[] toreturn = null;
		try {
			//Datei aus der Db holen
			ResultSet result = Controller.shsdb.select("files","content","id="+fileId,"");
			String content = result.getString("content");
			
			//Entschlüsselung des Inhaltes
			SecretKey key = new SecretKeySpec(pseudokey.getBytes(),this.getInstance(Controller.shsconfig.symmetric));
			Cipher cipher = Cipher.getInstance(this.getInstance(Controller.shsconfig.symmetric));
			cipher.init(Controller.shsconfig.decryptmode, key);
			byte[] crypted = cipher.doFinal(content.getBytes("UTF8"));
			content = new String(crypted,"UTF8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt	
			
			//Entpuffern: Entfernung von <shsbr/>
			toreturn = content.split(Controller.shsconfig.txtlinebreak);
			
		} catch (Exception e) {
			Controller.shsgui.triggernotice(e);
		}
			
		return toreturn;
	}
	
	
	

	
	

}
