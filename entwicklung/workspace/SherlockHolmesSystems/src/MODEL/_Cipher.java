package MODEL;

import java.security.KeyPair;
import java.util.HashMap;

import MODEL.enums.Ciphertype;

public abstract class _Cipher {
	
	private int keysize = 0;
	private String asymInstance = "", symInstance = "";
	
	protected _Cipher(int keysize,String symInstance, String asymInstance){
		_init(keysize, symInstance, asymInstance);
	}
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
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
		if(type.equals(Ciphertype.asymmetric)){
			toreturn = this.asymInstance;
		}else if(type.equals(Ciphertype.symmetric)){
			toreturn = this.asymInstance;
		}
		return toreturn;
	}
	
	//Abstract methods
	public abstract byte[] getkey(Ciphertype type);
	
	public abstract HashMap<String, byte[]> getkey();
	
	protected abstract Object createsymmetricKey();
	
	protected abstract Object createAsymmetricKey();
	
	/**
	 * Diese Methode wird sowohl zur Entschlüsselung als auch zur Verschlüsselung verwendet
	 * @param tocrypt: Text der chiffriert werden muss
	 * @param type: asymmetrisch || symmetrisch
	 * @param cipherMODE: Cipher.ENCRYPT_MODE || Cipher.DECRYPT_MODE
	 */
	public abstract String crypt(String tocrypt,Ciphertype type,int cipherMODE);
	
	public abstract void dataPUSH(String filepath,Ciphertype type,int cipherMODE);
	
	public abstract void dataPULL(String filename,String localfilepath,Ciphertype type,int cipherMODE);
	
}
