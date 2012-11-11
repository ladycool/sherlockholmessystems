package SERVICE;

import java.io.File;
import java.util.HashMap;
import java.sql.ResultSet;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;
import MODEL._Config;
import MODEL.enums.Viewertype;


public class Config extends _Config {
	
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
	
	
	////////////////////LOGIN-START/////////////////////////////
	@Override
	public User loginSHS(String type,HashMap<String,String>attributes){
		if(type.equals(this.signactionA)){//sign in
			//Mapping (Luis!!!)
			Controller.shsuser =  signin(attributes);
		}else if(type.equals(this.signactionB)){//sign up
			Controller.shsuser = signup(attributes);
		}
		return Controller.shsuser;
	}
	
	/**
	 * @author Shazem (Patrick): ERLEDIGT, NOCH NICHT GETESTET
	 * 					An alle Entwickler, das solltet ihr als Beispiel benutzen.
	 * @param attributes
	 * @return
	 */
	private User signup(HashMap<String, String>attributes){
		String username = attributes.get("username");		
		ResultSet result = Controller.shsdb.select(this.usertb,"username", "username LIKE '"+username+"'","");
		User user = null;
		
		if(result != null){
			
			//Tabelle: user
			String password = attributes.get(this.passwordId);
			String userId = this.random(username.length()+password.length());
						
			password = this.insert(password, userId);//pseudo-password
			password = Base64.encode(password.getBytes());//password to save
			attributes.put(this.passwordId, password);
			
			String pseudouserId = this.insert(username, userId);
			pseudouserId = Base64.encode(pseudouserId.getBytes());//pseudouserId to save
			attributes.put(this.dbuserId, pseudouserId);
			
			Controller.shsdb.insert(this.usertb, attributes,"");
			
			
			//Tabelle: key
			Controller.shscipher = Shscipher.getInstance(this.keysize,this.symInstance,this.asymInstance);
			String cryptedkey="",tocrypt2 = "";
			
			//Verschlüsselung und Speicherung des geheimen symmetrischen Schlüssels
			byte[] tocrypt = Controller.shscipher.getkey(Controller.shsconfig.symmetric);
			tocrypt2 = new String(tocrypt) + Controller.shsconfig.savesym; //Zu dem bereits vorhandenen Schlüssel wird ein Stück text hinzugefügt um die Zuordnug beim
																		//Entschlüsseln zu ermöglichen.
			cryptedkey = Controller.shscipher.crypt(tocrypt2,Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
			
			HashMap<String, byte[]>tocrypt1 = Controller.shscipher.getkey();
			//Verschlüsselung und Speicherung des public Key
			tocrypt2 = new String(tocrypt1.get("pubk"))+Controller.shsconfig.savepubk;//Analog zum savesym
			cryptedkey = Controller.shscipher.crypt(tocrypt2,Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
			
			//Verschlüsselung und Speicherung des private Key
			tocrypt2 = new String(tocrypt1.get("prik"))+Controller.shsconfig.saveprik; // Analog zum savesym
			cryptedkey = Controller.shscipher.crypt(tocrypt2,Controller.shsconfig.master,Controller.shsconfig.encryptmode);
			Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
			
			HashMap<String,Object> userattr = new HashMap<String,Object>();
			userattr.put(this.username,attributes.get(this.username));
			userattr.put(this.userid,userId);
			userattr.put(this.keys,Controller.shscipher);
			user = User.getInstance(userattr);
		}
		
		return user;
	}
	
	/**
	 * @author Luis
	 * @param attributes
	 * @return
	 */
	private User signin(HashMap<String,String>attributes){
		return null;	
	}
	
    ////////////////////LOGIN-END/////////////////////////////
	
	///////////////////LOADUSERVIEW-START/////////////////////////
	public void loadUserView(User user){
		//der user sollte aus der Session kommen
		//public wegen einem möglichen Reload-button
		
		if(user != null){
			
		}else{
			//redirect with triggernotice
		}
		
		//Threads will be created here.
	}
	
	
	
	///////////////////LOADUSERVIEW-END/////////////////////////
	
	
	////////////////////LOAD-FILE-START////////////////////////
	@Override
	public void uploadfile(String path,String toggleId){
		/*
		 * Anhand des Pfads sollten die Dateien hochgeholt werden und mithilfe von Javascript dan richtig angezeigt werden
		 */
		File file = new File(path);
		
	}
	
    private void pseudokey(){
		
	}
	
	@Override
	public void downloadfile(String path,int fileId){
				
	}
	
	@Override
	protected void viewfile(int fileId,Viewertype status){
		if(status.equals(Controller.shsconfig.owner)){
			this.viewInternalfile(fileId);
		}else if(status.equals(Controller.shsconfig.reader)){
			this.viewExternalfile(fileId);
		}
	}
	
	private void viewInternalfile(int fileId){
		
	}
	
	private void viewExternalfile(int fileId){
		
	}
	
	////////////////////LOAD-FILE-END////////////////////////
	
	
	
	@Override
	protected void getticket(){
		
	}
	
	@Override
	public void createticket(String path,String name){
		
	}
	
	
}
