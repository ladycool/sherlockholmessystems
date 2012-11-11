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
	/**Gibt einen der sym. Schl�ssel in seiner Byte-form zur�ck 
	 * @param type = master | simple symmetric
	 * @return byte[]
	 */
	public abstract byte[] getkey(Ciphertype type);
	
	/**
	 * Gibt das asym. Schl�sselpaar in deren Byte-form zur�ck
	 * @return HashMap<String, byte[]>
	 */
	public abstract HashMap<String, byte[]> getkey();
	
	/**
	 * Erzeugt einen sym. Schl�ssel
	 * @return Der R�ckgabetyp ist von der implementierten Methode abh�ngig.
	 */
	protected abstract Object createsymmetricKey();
	
	/**
	 * Erzeugt einen sym. Schl�ssel 
	 * @param pseudokey: String der L�nge 16Bytes, der als Schl�sselwurzel diennen soll. 
	 * @return Der R�ckgabetyp ist von der implementierten Methode abh�ngig.
	 */
	protected abstract Object createsymmetricKey(String pseudokey);
	
	/**
	 * Selbsterkl�rend
	 * @return Siehe oben
	 */
	protected abstract Object createAsymmetricKey();
	
	/**
	 * Diese Methode wird sowohl zur Entschl�sselung als auch zur Verschl�sselung verwendet
	 * @param tocrypt: Text der chiffriert werden muss
	 * @param type: asymmetrisch || symmetrisch
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract String crypt(String tocrypt,Ciphertype type,int cipherMODE);
	
	/**
	 * Diese Methode wird beim Upload von Datei vewendet. Die dient lediglig der Verschl�sselung
	 * @param filepath
	 * @param pseudokey: String der L�nge 16Bytes, der als Schl�sselwurzel diennen soll.
	 * @return String: verschl�sselte Form des Dateiinhaltes
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
