package MODEL;

import java.io.DataInputStream;
import java.util.HashMap;


/**
 * @author Shazem (Patrick)
 */
public abstract class _Cipher {
	
	private int symkeysize = 0, asymkeysize = 0;
	protected String asymInstance = "", symInstance = "", master="master";
	
	protected _Cipher(int symkeysize,int asymkeysize,String symInstance, String asymInstance){
		_init(symkeysize,asymkeysize, symInstance, asymInstance);
	}

	protected _Cipher(HashMap<String,Object> settings){
		_init((int)settings.get("symkeysize"),(int)settings.get("asymkeysize"),(String)settings.get("symInstance"),(String)settings.get("asymInstance"));
	}
	
	private void _init(int symkeysize,int asymkeysize, String symInstance, String asymInstance){
		if(this.symkeysize == 0 && this.asymkeysize == 0 && this.symInstance.isEmpty() && this.asymInstance.isEmpty()){
			this.symkeysize = symkeysize;
			this.asymkeysize = asymkeysize;
			this.symInstance = symInstance;
			this.asymInstance = asymInstance;
		}
	}
	
	
	protected int getasymkeysize(){
		return this.asymkeysize;
	}
	
	/*
	private String getkeyInstance(String name){
		String toreturn = "";
		if(name.equals("asym")){
			toreturn = this.asymInstance;//Bsp: RSA
		}else if(name.equals("sym")){
			toreturn = this.symInstance; // Bsp: AES
		}
		return toreturn;
	}
	*/
	
	//Abstract methods
	/**Gibt einen der sym. Schl�ssel in seiner Byte-form zur�ck 
	 * @param type = master | simple symmetric
	 * @return byte[]
	 */
	public abstract byte[] getkey();
	
	/**
	 * Gibt das asym. Schl�sselpaar in deren Byte-form zur�ck
	 * @return HashMap<String, byte[]>
	 */
	public abstract HashMap<String, byte[]> getkeys();
	
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
	 * @param instance: asymmetrisch || symmetrisch(master || secret)
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract byte[] crypt(byte[] tocrypt,String instance,int cipherMODE);
	
	/**
	 * Diese Methode wird sowohl zur Entschl�sselung als auch zur Verschl�sselung verwendet
	 * @param tocrypt: Text der chiffriert werden muss
	 * @param key: Schl�ssel zum crypten
	 * @param instance: asymmetrisch || symmetrisch(master || secret)
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract byte[] crypt(byte[] tocrypt,byte[] key,String instance,int cipherMODE);
	/**
	 * Diese Methode wird beim Upload von Datei vewendet. Die dient lediglig der Verschl�sselung
	 * @param filepath
	 * @param pseudokey: String der L�nge 16Bytes, der als Schl�sselwurzel diennen soll.
	 * @return String: verschl�sselte Form des Dateiinhaltes
	 */
	public abstract byte[] encryptfile(String filepath,byte[] pseudokey);
	
	/**
	 * Diese Methode wird beim Upload von Datei vewendet. Die dient lediglig der Verschl�sselung
	 * @param file
	 * @param pseudokey: String der L�nge 16Bytes, der als Schl�sselwurzel diennen soll.
	 * @return String: verschl�sselte Form des Dateiinhaltes
	 */
	public abstract byte[] encryptfile(DataInputStream file,byte[] pseudokey);
	
	

	
}
