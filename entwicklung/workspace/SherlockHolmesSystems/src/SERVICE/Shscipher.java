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
			 * NICHT L�SCHEN (Patrick)
			 */
			KeyFactory keyfactory = KeyFactory.getInstance(this.asymInstance);
			EncodedKeySpec asymkSpec;
			////load public key
			asymkSpec = new X509EncodedKeySpec(publickey.getBytes());
			PublicKey pubK = keyfactory.generatePublic(asymkSpec);
			
			////load private key
			asymkSpec = new PKCS8EncodedKeySpec(privatekey.getBytes());
			PrivateKey priK = keyfactory.generatePrivate(asymkSpec);
			
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
		EncodedKeySpec asymkSpec = null;
		HashMap<String, byte[]> toreturn = new HashMap<String,byte[]>();
		
		asymkSpec = new X509EncodedKeySpec(this.shsasymkeypair.getPrivate().getEncoded());
		toreturn.put(Controller.shsconfig.prik, asymkSpec.getEncoded());
		
		asymkSpec = new PKCS8EncodedKeySpec(this.shsasymkeypair.getPublic().getEncoded());
		toreturn.put(Controller.shsconfig.pubk, asymkSpec.getEncoded());
		
		return toreturn;
	}

	/**
	 * Erzeugt ein Objekt vom Typ Chipher, das zum chiffrieren ben�tigt wird.
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
				//SecretKeySpec skeySpec = new SecretKeySpec(getCryptoKeyByteArray(length=16));
				key = new SecretKeySpec(pseudokey.getBytes(),this.symInstance);//AES pseudo.length() = 16
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
		Cipher cipher = this.getCipher(instance, cipherMODE);
		return this.crypt(cipher,tocrypt);
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
			
			toreturn = this.crypt(cipher,tocrypt);
			
		} catch (GeneralSecurityException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	
	@SuppressWarnings("deprecation")
	private String crypt(Cipher cipher,String tocrypt){
		String toreturn="";
		int blocksize = cipher.getBlockSize();
		byte[] tocryptbytes = new byte[blocksize];
		byte[] crypted = new byte[cipher.getOutputSize(blocksize)];System.out.println("Start" + blocksize+"---"+crypted.length);
		int 
		maxlength = tocrypt.getBytes().length,
		start = -blocksize,
		end = start+blocksize; //=0
		try{	
			while(true){
				start += blocksize;
				end += blocksize;		
			
				if(end < maxlength){
					tocryptbytes = new byte[blocksize];
					tocrypt.getBytes(start,end,tocryptbytes,0);
					crypted = cipher.update(tocryptbytes);
					toreturn += new String(crypted);
				}else{
					//end = maxlength;
					tocryptbytes = new byte[maxlength - start];
					tocrypt.getBytes(start,maxlength,tocryptbytes,0);
					crypted = cipher.doFinal(tocryptbytes);
					toreturn += new String(crypted);
					break;
				}			
			}
		} catch (GeneralSecurityException e) {
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

			
			//Verschl�sselung des Inhaltes
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
			ResultSet result=null;
			try{
				//Datei aus der Db holen
				result = Controller.shsdb.select("files","content","id="+fileId,"");
			} catch (Exception e) {
				//In java erzeugt ein leeres SELECT-Ergebnis eine Exception (So dumm ist java)
			}
			
			if(result.first()){
				content = result.getString("content");
				
				//Entschl�sselung des Inhaltes
				content = this.crypt(content,pseudokey,this.symInstance,Cipher.DECRYPT_MODE);
							
				//Entpuffern: Entfernung von <shsbr/>
				//toreturn = content.split(Controller.shsconfig.txtlinebreak);
				//Jede weitere Methode wird sich ums Entpuffern k�mmern
			}else{
				Controller.shsgui.triggernotice(Controller.shsdb.text(41));
			}
		} catch (Exception e) {
			Controller.shsgui.triggernotice(Controller.shsdb.text(41));
			Controller.shsgui.triggernotice(e);
		}
			
		return content;
	}
	
	

	
	

}
