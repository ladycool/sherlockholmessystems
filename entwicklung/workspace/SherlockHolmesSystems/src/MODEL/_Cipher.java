package MODEL;

import java.security.KeyPair;
import java.util.HashMap;

import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;

import CONTROLLER.Controller;
import MODEL.enums.Ciphertype;


/**
 * @author Shazem (Patrick)
 */
public abstract class _Cipher {
	
	private int keysize = 0;
	private String asymInstance = "", symInstance = "";
	
	protected _Cipher(int keysize,String symInstance, String asymInstance){
		_init(keysize, symInstance, asymInstance);
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
	
	protected String getInstance(Ciphertype type){
		String toreturn = "";
		if(type.equals(Controller.shsconfig.asymmetric)){
			toreturn = this.asymInstance;//Bsp: RSA
		}else if(type.equals(Controller.shsconfig.symmetric)){
			toreturn = this.symInstance; // Bsp: AES
		}
		return toreturn;
	}
	
	
	
	//Abstract methods
	/**Gibt einen der sym. Schlüssel in seiner Byte-form zurück 
	 * @param type = master | simple symmetric
	 * @return byte[]
	 */
	public abstract byte[] getkey(Ciphertype type);
	
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
	 * @param type: asymmetrisch || symmetrisch
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract String crypt(String tocrypt,Ciphertype type,int cipherMODE);
	
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
	 * @return String[i] --> Zeile in der Datei.
	 */
	protected abstract String[] readfile(String pseudokey, int fileId);
	


	
}
