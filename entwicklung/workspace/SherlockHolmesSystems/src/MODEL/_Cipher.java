package MODEL;

import java.util.HashMap;


/**
 * @author Shazem (Patrick)
 */
public abstract class _Cipher {
	
	private int keysize = 0;
	protected String asymInstance = "", symInstance = "", master="master";
	
	protected _Cipher(int keysize,String symInstance, String asymInstance){
		_init(keysize, symInstance, asymInstance);
	}

	protected _Cipher(HashMap<String,Object> settings){
		_init((int)settings.get("keysize"),(String)settings.get("symInstance"),(String)settings.get("asymInstance"));
	}
	
	private void _init(int keysize,String symInstance, String asymInstance){
		if(this.keysize == 0 && this.symInstance.isEmpty() && this.asymInstance.isEmpty()){
			this.keysize = keysize;
			this.symInstance = symInstance;
			this.asymInstance = asymInstance;
		}
	}
	
	
	protected int getkeysize(){
		return this.keysize;
	}
	
	
	private String getkeyInstance(String name){
		String toreturn = "";
		if(name.equals("asym")){
			toreturn = this.asymInstance;//Bsp: RSA
		}else if(name.equals("sym") || name.equals("master")){
			toreturn = this.symInstance; // Bsp: AES
		}
		return toreturn;
	}

	
	//Abstract methods
	/**Gibt einen der sym. Schlüssel in seiner Byte-form zurück 
	 * @param type = master | simple symmetric
	 * @return byte[]
	 */
	public abstract byte[] getkey(String type);
	
	/**
	 * Gibt das asym. Schlüsselpaar in deren Byte-form zurück
	 * @return HashMap<String, byte[]>
	 */
	public abstract HashMap<String, byte[]> getkey();
	
	/**
	 * Erzeugt einen sym. Schlüssel
	 * @return Der Rückgabetyp ist von der implementierten Methode abhängig.
	 */
	protected abstract Object createsymmetricKey();
	
	/**
	 * Erzeugt einen sym. Schlüssel 
	 * @param pseudokey: String der Länge 16Bytes, der als Schlüsselwurzel diennen soll. 
	 * @return Der Rückgabetyp ist von der implementierten Methode abhängig.
	 */
	protected abstract Object createsymmetricKey(String pseudokey);
	
	/**
	 * Selbsterklärend
	 * @return Siehe oben
	 */
	protected abstract Object createAsymmetricKey();
	
	/**
	 * Diese Methode wird sowohl zur Entschlüsselung als auch zur Verschlüsselung verwendet
	 * @param tocrypt: Text der chiffriert werden muss
	 * @param instance: asymmetrisch || symmetrisch(master || secret)
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract String crypt(String tocrypt,String instance,int cipherMODE);
	
	/**
	 * Diese Methode wird sowohl zur Entschlüsselung als auch zur Verschlüsselung verwendet
	 * @param tocrypt: Text der chiffriert werden muss
	 * @param key: Schlüssel zum crypten
	 * @param instance: asymmetrisch || symmetrisch(master || secret)
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract String crypt(String tocrypt,String key,String instance,int cipherMODE);
	/**
	 * Diese Methode wird beim Upload von Datei vewendet. Die dient lediglig der Verschlüsselung
	 * @param filepath
	 * @param pseudokey: String der Länge 16Bytes, der als Schlüsselwurzel diennen soll.
	 * @return String: verschlüsselte Form des Dateiinhaltes
	 */
	public abstract String encryptfile(String filepath,String pseudokey);
	
	/**
	 * Diese Methode liest Dateien aus der Tabelle "files".
	 * @param pseudokey
	 * @param fileId: Id der Datei.
	 * @return String.
	 */
	public abstract String readfile(String pseudokey,String fileId);
	


	
}
