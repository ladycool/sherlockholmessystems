package SERVICE;

import java.io.File;
import java.util.HashMap;
import java.sql.ResultSet;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;
import MODEL._Config;
import MODEL.enums.Ciphertype;


public class Config extends _Config {
	private int randomuseridlength = 10;
	
	
	/*
	 * PRIVATE
	 */
	private static Config _config = new Config();
	private Config(){
		super();//do nothing
	}
	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static Config getInstance(){
		return Config._config;
	}
	
	
	/*
	 * FINAL
	 */
	
	
	/*
	 * METHODS
	 */
	/**
	 * @doc Die Methode holt sich je nach gesuchtem Dateientyp, Dateien aus einem Verzeichnis die anschliessend
	 * 		hochgeladen werden.
	 * @param folder: Verzeichinis in dem die Daei sich befinden
	 * @param suffix: Die Endung des gesuchten Dateientyp. z.B.: .java
	 * @param seqfirst: beim Uploaden -> seqfirst + Datei
	 * @param seqlast: beim Uploaden -> seqfirst + Datei + seqlast
	 * @param seperator: beim Uploaden -> seqfirst + Datei + seqlast + seperator + seqfirst + Datei + seqlast
	 * @return	seqfirst + Datei + seqlast + seperator + seqfirst + Datei + seqlast + ...
	 */
	public String libraryUploader(String _folder,String suffix,String seqfirst,String seqlast,String seperator){
		File folder = new File(_folder);
		String toreturn="";
		
		for (final File fileEntry : folder.listFiles()) {
			if(fileEntry.isFile() && fileEntry.getName().endsWith(suffix)){
				toreturn += seqfirst+fileEntry+seqlast+seperator;
			}
		}
		return toreturn;
	}
	
	
	@Override
	public void loginSHS(String type,HashMap<String,String>attributes){
		if(type.equals(this.signactionA)){//sign in
			Controller.shsuser =  signin(attributes);
		}else if(type.equals(this.signactionB)){//sign up
			Controller.shsuser = signup(attributes);
		}
	}
	
	private User signup(HashMap<String, String>attributes){
		String username = attributes.get("username");		
		ResultSet result = Controller.shsdb.select(this.usertb,"username", "username LIKE '"+username+"'","");
		User user = null;
		
		if(result != null){
			
			//Tabelle: user
			String userId = this.random(this.randomuseridlength);
			String pseudouserId = userId;
			String password = attributes.get(this.passwordId);
			
			int insertpos = this.randomnr(password.length()-1);
			password = password.substring(0, insertpos) + userId + password.substring(insertpos);//new password
			password = Base64.encode(password.getBytes());//password to save
			attributes.put(this.passwordId, password);
			
			insertpos = this.randomnr(userId.length()-1);
			userId = userId.substring(0,insertpos) + username + userId.substring(insertpos);//new userId
			userId = Base64.encode(userId.getBytes());//userId to save
			attributes.put("userid", userId);
			
			Controller.shsdb.insert(this.usertb, attributes,"");
			
			
			//Tabelle: key
			Controller.shscipher = Shscipher.getInstance(this.keysize,this.symInstance,this.asymInstance);
			String cryptedkey="";
			//attributes = new HashMap<String, String>();
			attributes.put("userid", pseudouserId);
			
			//
			//Verschlüsselung und Speicherung des geheimen symmetrischen Schlüssels
			byte[] tocrypt = Controller.shscipher.getkey(Controller.shsconfig.symmetric);
			cryptedkey = Controller.shscipher.crypt(new String(tocrypt),Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			cryptedkey += Controller.shsconfig.savesym;
			Controller.shsdb.insert("userid", "key", cryptedkey,"");
			
			HashMap<String, byte[]>tocrypt1 = Controller.shscipher.getkey();
			//Verschlüsselung und Speicherung des public Key
			cryptedkey = Controller.shscipher.crypt(new String(tocrypt1.get("pubk")),Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			cryptedkey += Controller.shsconfig.savepubk;
			Controller.shsdb.insert("userid", "key", cryptedkey,"");
			//Verschlüsselung und Speicherung des private Key
			cryptedkey = Controller.shscipher.crypt(new String(tocrypt1.get("prik")),Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			cryptedkey += Controller.shsconfig.saveprik;
			Controller.shsdb.insert("userid", "key", cryptedkey,"");
			
			
			//CREATE USER: HIER BIN HIER _________________________________________
		}
		
		return user;
	}
	
	private User signin(HashMap<String,String>attributes){
		return null;	
	}
	
	
	@Override
	public void uploaddata(String type,String path,String toggleId){
		if(type.equals(this.uploadtypeA)){
			internaldataupload(path,toggleId);
		}else if(type.equals(this.uploadtypeB)){
			externaldataupload(path,toggleId);
		}
	}
	
	
	private void internaldataupload(String path,String toggleId){
		
	}
	
	private void externaldataupload(String path,String toggleId){
		
	}
	
	@Override
	protected void savecreatedKeys(){
		//kein Gebrauch für den Moment
	}
	
	@Override
	public HashMap<String, Object>getKeys(){
		return null;
	}
	
	@Override
	protected void getticket(){
		
	}
	
	@Override
	public void createticket(String path,String name){
		
	}
	
	
	
	@Override
	public void shsinit(){
		//the redirect call will be integrated here
		//the threads will be create here as well
	}
	

}
