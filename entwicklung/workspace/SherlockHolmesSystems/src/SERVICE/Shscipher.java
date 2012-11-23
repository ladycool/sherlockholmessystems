package SERVICE;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
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
	
	private SecretKey shssymkey = null;
	private KeyPair shsasymkeypair = null;
	
	/*
	 * PRIVATE
	 */
	private static Shscipher _cipher = null;
	private Shscipher(int symkeysize,int asymkeysize,String symInstance, String asymInstance) {
		//settings
		super(symkeysize,asymkeysize,symInstance,asymInstance);
		
		//create
		this.shssymkey = this.createsymmetricKey();
		this.shsasymkeypair = this.createAsymmetricKey();
	}
	
	private Shscipher(HashMap<String,Object> settings,HashMap<Integer, byte[]> keys){
		//settings
		super(settings);
		
		//load		
		try {
			this.shssymkey = new SecretKeySpec(keys.get(0),this.symInstance);//sym
		
			KeyFactory keyfactory;
			keyfactory = KeyFactory.getInstance(this.asymInstance);
			
			EncodedKeySpec keyspec = new X509EncodedKeySpec(keys.get(1));
			PublicKey pubk = keyfactory.generatePublic(keyspec);
			
			keyspec = new PKCS8EncodedKeySpec(keys.get(2));
			PrivateKey prik = keyfactory.generatePrivate(keyspec);
			
			this.shsasymkeypair = new KeyPair(pubk, prik);//asym
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			Controller.shsgui.triggernotice(e);
		}
	}
	

	/**
	 * Erzeugt einen Singelton
	 * @return
	 */
	public static Shscipher getInstance(int symkeysize,int asymkeysize, String symInstance, String asymInstance){
		//SETTER-START
		if(_cipher == null){
			_cipher = new Shscipher(symkeysize,asymkeysize, symInstance, asymInstance);
		}
		//SETTER-END
		return _cipher;
	}
	
	/**
	 * Erzeugt einen Singelton
	 * @return
	 */
	public static Shscipher getInstance(HashMap<String,Object> settings,HashMap<Integer, byte[]> keys){
		//SETTER-START
		if(_cipher == null){
			_cipher = new Shscipher(settings,keys);
		}
		//SETTER-END
		return _cipher;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] getkey(){			
		return this.shssymkey.getEncoded();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String, byte[]> getkeys() {
		EncodedKeySpec asymkSpec = null;
		HashMap<String, byte[]> toreturn = new HashMap<String,byte[]>();
		
		asymkSpec = new X509EncodedKeySpec(this.shsasymkeypair.getPublic().getEncoded());
		toreturn.put(Controller.shsconfig.pubk, asymkSpec.getEncoded());
		
		asymkSpec = new PKCS8EncodedKeySpec(this.shsasymkeypair.getPrivate().getEncoded());
		toreturn.put(Controller.shsconfig.prik, asymkSpec.getEncoded());
		
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
			cipher = Cipher.getInstance(instance);//AES || RSA
			if(instance.equals(this.asymInstance)){
				if(cipherMODE == Cipher.ENCRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPublic());
				}else if(cipherMODE == Cipher.DECRYPT_MODE){
					cipher.init(cipherMODE, this.shsasymkeypair.getPrivate());
				}
			}else if(instance.equals(this.symInstance)){
	            cipher.init(cipherMODE, this.shssymkey);				
			}
		}catch (GeneralSecurityException e){
			//e.printStackTrace();
			Controller.shsgui.triggernotice(e);
		}
		return cipher;
	}
	
	
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
				//SecretKeyFactory factory = SecretKeyFactory.getInstance(this.symInstance);
				
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
	@Override
	protected KeyPair createAsymmetricKey() {
		KeyPair keypair = null;
		try {
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance(this.asymInstance);//RSA
            SecureRandom random = new SecureRandom();
            pairgen.initialize(this.getasymkeysize(), random);
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
	public byte[] crypt(byte[] tocrypt,String instance,int cipherMODE) {
		//cipherMODE = Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE		
		Cipher cipher = this.getCipher(instance, cipherMODE);
		return this.crypt(cipher,tocrypt);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] crypt(byte[] tocrypt,byte[] key,String instance,int cipherMODE){
		byte[] toreturn = null;
		try {
			Key _key = new SecretKeySpec(key,instance);
			Cipher cipher = Cipher.getInstance(instance);
			cipher.init(cipherMODE, _key);			
			
			toreturn = this.crypt(cipher,tocrypt);
			
		} catch (GeneralSecurityException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	
	private byte[] crypt(Cipher cipher,byte[] tocrypt){
		//byte[] toreturn;
		ArrayList<byte[]> _toreturn =  new ArrayList<byte[]>();
		
		int blocksize = cipher.getBlockSize();
		byte[] tocryptbytes;
		//byte[] crypted = new byte[cipher.getOutputSize(blocksize)];
		//System.out.println("Start" + blocksize+"---"+crypted.length);
		int 
		maxlength = tocrypt.length,
		start = -blocksize,
		end = start+blocksize; //=0
		try{	
			while(true){
				start += blocksize;
				end += blocksize;		
			
				if(end < maxlength){
					tocryptbytes = new byte[blocksize];
					for (int i=start,j=0; i < end; i++,j++) {
						tocryptbytes[j] = tocrypt[i];
					}
					_toreturn.add(cipher.update(tocryptbytes));
				}else{
					//end = maxlength;
					tocryptbytes = new byte[maxlength - start];
					for (int i=start,j=0; i < maxlength; i++,j++) {
						tocryptbytes[j] = tocrypt[i];
					}
					_toreturn.add(cipher.doFinal(tocryptbytes));
					break;
				}			
			}
		} catch (GeneralSecurityException e) {
			Controller.shsgui.triggernotice(e);
		}
		return this.arraylisttobytearray(_toreturn);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] encryptfile(String filepath,byte[] pseudokey){
		byte[] toreturn = null,tocrypt;
		ArrayList<byte[]>temp = new ArrayList<byte[]>();
		
		try {
			//Lesen der Datei
			byte[] cbuf = new byte[1024];
			DataInputStream file = new DataInputStream(new FileInputStream(new File(filepath)));
			while(file.read(cbuf) != -1){
				temp.add(cbuf);
			}
			file.close();
			
			//Convertierung von Arraylist<byte[]> to byte[]
			tocrypt = this.arraylisttobytearray(temp);
			
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
	public byte[] encryptfile(DataInputStream file,byte[] pseudokey){
		byte[] toreturn = null,tocrypt;
		ArrayList<byte[]>temp = new ArrayList<byte[]>();
		
		try {
			//Lesen der Datei
			byte[] cbuf = new byte[1024];
			//DataInputStream file = new DataInputStream(new FileInputStream(new File(filepath)));
			while(file.read(cbuf) != -1){
				temp.add(cbuf);
			}
			file.close();
			
			//Convertierung von Arraylist<byte[]> to byte[]
			tocrypt = this.arraylisttobytearray(temp);
			
			//Verschlüsselung des Inhaltes
			toreturn = this.crypt(tocrypt,pseudokey,this.symInstance,Cipher.ENCRYPT_MODE);
			
		} catch (IOException e) {
			Controller.shsgui.triggernotice(e);
		}	
		
		return toreturn;
	}

	
	
	private byte[] arraylisttobytearray(ArrayList<byte[]> toconvert){
		byte[] toreturn = null;
		int newlength=0,continueindex=0;
		byte[] temp;
		for (byte[] b : toconvert) {
			newlength += b.length;
			temp = toreturn;
			toreturn = new byte[newlength];
			if(temp != null){				
				for (int i = 0; i < temp.length; i++) {
					toreturn[i] = temp[i];
				}
				continueindex = temp.length;
			}
			for (int i = 0; i < b.length; i++) {
				toreturn[continueindex+i] = b[i];
			}
		}
		return toreturn;
	}
	
	

}
