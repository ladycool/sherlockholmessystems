package SERVICE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import CONTROLLER.Controller;
import MODEL._Cipher;

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
		//settings
		super(keysize, symInstance, asymInstance);
		
		//create
		this.masterkey = this.createsymmetricKey();
		this.shssymkey = this.createsymmetricKey();
		this.shsasymkeypair = this.createAsymmetricKey();
	}
	
	private Shscipher(HashMap<String,Object> settings,HashMap<String,String> keys){
		//settings
		super(settings);
		
		//load
		String 
		masterkey = (String)keys.get("masterkey"),
		secretkey = (String)keys.get("secretkey"),
		privatekey = (String)keys.get("privatekey"),
		publickey = (String)keys.get("publickey");
		
		////load masterkey
		this.masterkey = new SecretKeySpec(masterkey.getBytes(),this.symInstance);
		////load secretkey
		this.shssymkey = new SecretKeySpec(secretkey.getBytes(),this.symInstance);
		
		
		try {
			/*
			 * Alternative1: KeyRep-Klasse
			 * Alternative2: EncodedKeySpec-Klasse-->
			 * 					http://stackoverflow.com/questions/5263156/rsa-keypair-generation-and-storing-to-keystore 
			 * NICHT LÖSCHEN (Patrick)
			 */
			KeyFactory keyfactory = KeyFactory.getInstance(this.asymInstance);
			EncodedKeySpec pkSpec;
			////load public key
			pkSpec = new X509EncodedKeySpec(publickey.getBytes());
			PublicKey pubK = keyfactory.generatePublic(pkSpec);
			
			////load private key
			pkSpec = new PKCS8EncodedKeySpec(privatekey.getBytes());
			PrivateKey priK = keyfactory.generatePrivate(pkSpec);
			
			this.shsasymkeypair = new KeyPair(pubK, priK);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		
	}
	/**
	 * Erzeugt einen Singelton
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
	
	/**
	 * Erzeugt einen Singelton
	 * @return
	 */
	public static Shscipher getInstance(HashMap<String,Object> settings, HashMap<String,String> keys){
		//SETTER-START
		if(_cipher == null){
			_cipher = new Shscipher(settings, keys);
		}
		//SETTER-END
		return _cipher;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getkey(String instance){
		byte[] toreturn = null;
		if(instance.equals(this.master)){
			toreturn = this.masterkey.getEncoded();
		}else if(instance.equals(this.symInstance)){
			toreturn = this.shssymkey.getEncoded();	
		}			
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, byte[]> getkey() {
		X509EncodedKeySpec x509ks = null;
		HashMap<String, byte[]> toreturn = new HashMap<String,byte[]>();
		x509ks = new X509EncodedKeySpec(this.shsasymkeypair.getPrivate().getEncoded());
		toreturn.put("prik", x509ks.getEncoded());
		x509ks = new X509EncodedKeySpec(this.shsasymkeypair.getPublic().getEncoded());
		toreturn.put("prik", x509ks.getEncoded());
		
		return toreturn;
	}

	/**
	 * Erzeugt ein Objekt vom Typ Chipher, das zum chiffrieren benötigt wird.
	 * @param type: asymmetric || symmetric || master
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 * @return
	 */
	private Cipher getCipher(String instance,int cipherMODE){
		Cipher cipher = null;
		try{
			if(instance.equals(this.master)){
				cipher = Cipher.getInstance(this.symInstance);//AES
			}else{
				cipher = Cipher.getInstance(instance);//AES || RSA
			}
			
			if(instance.equals(this.asymInstance)){
				if(cipherMODE == Cipher.ENCRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPublic());
				}else if(cipherMODE == Cipher.DECRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPrivate());
				}
			}else if(instance.equals(this.symInstance)){
	            cipher.init(cipherMODE, this.shssymkey);				
			}else if(instance.equals(this.master)){			
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
				KeyGenerator keygen = KeyGenerator.getInstance(this.symInstance);//AES
				SecureRandom random = new SecureRandom();
		        keygen.init(random);
		        key = keygen.generateKey();
			}else{
				key = new SecretKeySpec(pseudokey.getBytes(),this.symInstance);//AES
			}
		} catch (GeneralSecurityException e) {
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
        
		return key;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected SecretKey createsymmetricKey() {
		return createsymmetricKey("");
	}
	
	/**
	 * TO DO registerKeys()
	 */
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unused")
	@Override
	protected KeyPair createAsymmetricKey() {
		KeyPair keypair = null;
		try {
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance(this.asymInstance);//RSA
            SecureRandom random = new SecureRandom();
            pairgen.initialize(this.getkeysize(), random);
            keypair = pairgen.generateKeyPair();
			
		} catch (GeneralSecurityException e){
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}

		return keypair;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String crypt(String tocrypt,String instance,int cipherMODE) {
		//cipherMODE = Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
		String toreturn="";
		try {
			Cipher cipher = getCipher(instance, cipherMODE);
			
			byte[] crypted = cipher.doFinal(tocrypt.getBytes("UTF8"));
			toreturn = new String(crypted,"UTF8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt
			
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String crypt(String tocrypt,String key,String instance,int cipherMODE){
		String toreturn="";
		try {
			Key _key = new SecretKeySpec(key.getBytes(),instance);
			Cipher cipher = Cipher.getInstance(instance);
			cipher.init(cipherMODE, _key);			
			byte[] crypted = cipher.doFinal(tocrypt.getBytes("UTF8"));
			toreturn = new String(crypted,"UTF8");//UTF-8 -> So werden ebenfalls die Sonderzeichen des europaischen Raums mitberücksichtigt	
		} catch (GeneralSecurityException | UnsupportedEncodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
			//tocrypt += Controller.shsuser.getattr("metadata");
			file.close();

			
			//Verschlüsselung des Inhaltes
			toreturn = this.crypt(tocrypt,pseudokey,this.symInstance,Cipher.ENCRYPT_MODE);
			
		} catch (IOException e) {
			Controller.shsgui.triggernotice(e);
		}	
		
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String readfile(String pseudokey,String fileId){
		String content = "";
		try {
			//Datei aus der Db holen
			ResultSet result = Controller.shsdb.select("files","content","id="+fileId,"");
			content = result.getString("content");
			
			//Entschlüsselung des Inhaltes
			content = this.crypt(content,pseudokey,this.symInstance,Cipher.DECRYPT_MODE);
						
			//Entpuffern: Entfernung von <shsbr/>
			//toreturn = content.split(Controller.shsconfig.txtlinebreak);
			//Jede weitere Methode wird sich ums Entpuffern kümmern
			
		} catch (Exception e) {
			Controller.shsgui.triggernotice(e);
		}
			
		return content;
	}
	
	

	
	

}
