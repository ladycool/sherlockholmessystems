package SERVICE;

import java.io.File;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import CONTROLLER.Controller;
import MODEL._Config;
import MODEL.enums.Viewertype;


public class Config extends _Config {
	
	/*
	 * PRIVATE
	 */
	private static Config _config = new Config("de");
	private Config(){
		super();//do nothing
	}
	
	private Config(String lang){
		super();
		this.lang = lang;
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
		
		try {
			if(result.getString("username").isEmpty()){
				
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
				byte[] tocrypt = Controller.shscipher.getkey(this.symInstance);
				tocrypt2 = new String(tocrypt) + this.savesym; //Zu dem bereits vorhandenen Schlüssel wird ein Stück text hinzugefügt um die Zuordnug beim
																			//Entschlüsseln zu ermöglichen.
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				HashMap<String, byte[]>tocrypt1 = Controller.shscipher.getkey();
				//Verschlüsselung und Speicherung des public Key
				tocrypt2 = new String(tocrypt1.get("pubk"))+this.savepubk;//Analog zum savesym
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				//Verschlüsselung und Speicherung des private Key
				tocrypt2 = new String(tocrypt1.get("prik"))+this.saveprik; // Analog zum savesym
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				HashMap<String,Object> userattr = new HashMap<String,Object>();
				userattr.put(this.username,attributes.get(this.username));
				userattr.put(this.userid,userId);
				userattr.put(this.keys,Controller.shscipher);
				user = User.getInstance(userattr);
			}else{
				Controller.shsgui.triggernotice("FEHLERMELDUNG");
			}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
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
	
	//////////////////////////////TICKETS-START/////////////////////////////////
	
	@Override
	public HashMap<Integer,String[]> gettickets(){
		HashMap<Integer,String[]> toreturn = new HashMap<Integer,String[]>();
		try {
			String my_username = (String) Controller.shsuser.getattr("username");
			my_username = Controller.shscipher.crypt(my_username,this.asymInstance,this.encryptmode);
			
			ResultSet result = Controller.shsdb.select("tickets","id,sent_by,filename","sent_to LIKE '"+my_username+"'");
			int i=0;
			String ticketId,sent_by,filename;
			while(result.next()){
				ticketId = result.getString("id");
				
				sent_by = result.getString("sent_by");
				sent_by = Controller.shscipher.crypt(sent_by,this.asymInstance,this.decryptmode);
				
				filename = result.getString("filename");
				filename = Controller.shscipher.crypt(filename,this.asymInstance,this.decryptmode);
				
				toreturn.put(i,new String[]{ticketId,sent_by,filename});
				i++;
			}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		return toreturn;
	}
	
	@Override
	/**
	 * @author Shazem(Patrick)
	 */
	public HashMap<String, String> readticket(String ticketId){
		HashMap<String, String> toreturn = new HashMap<String, String>();
		try{
			ResultSet result = Controller.shsdb.select("tickets","fileId,`key`","id="+ticketId);
			String pseudokey = result.getString("key");
			pseudokey = Controller.shscipher.crypt(pseudokey,this.asymInstance,this.decryptmode);
			
			String fileId = result.getString("fileId");
			fileId = Controller.shscipher.crypt(fileId,this.asymInstance,this.decryptmode);
			
			toreturn.put("fileId",fileId);
			toreturn.put("pseudokey",pseudokey);
		}catch(SQLException e){
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	@Override
	/**
	 * @author Shazem(Patrick)
	 */
	public void createticket(int fileId,String[] userList){
		try {
			ResultSet result;
			String 
			sent_by = (String) Controller.shsuser.getattr("username"),
			sent_to, his_publickey, secretkey, filename, values,
			fields = "sent_by,sent_to,fileId,filename,`key`";
			String[] temp;
			
			for (String user : userList){
				result = Controller.shsdb.select("public_keys", "username,`key`","userId LIKE '"+user+"'");//QUERY
				his_publickey = result.getString("key");
				
				sent_to = result.getString("username");
				sent_to = Controller.shscipher.crypt(sent_to,his_publickey,this.asymInstance,this.encryptmode);
				
				sent_by = Controller.shscipher.crypt(sent_by,his_publickey,this.asymInstance,this.encryptmode);
				
				result = Controller.shsdb.select("files","`key`,path","id = "+fileId);//QUERY			
				secretkey = Controller.shscipher.crypt(result.getString("key"),this.symInstance, this.decryptmode);
				secretkey = Controller.shscipher.crypt(secretkey,his_publickey,this.asymInstance,this.encryptmode);
				
				temp = Controller.shscipher.crypt(result.getString("path"),this.symInstance,this.decryptmode).split("/"); 
				filename = Controller.shscipher.crypt(temp[temp.length-1],his_publickey,this.asymInstance,this.encryptmode);
				
				
				values = "'"+sent_by+"','"+sent_to+"','"+fileId+"','"+filename+"','"+secretkey+"'";
				Controller.shsdb.insert("tickets",fields, values,Controller.shsdb.text(45));			
			}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}
	
	//////////////////////////////TICKETS-ENDE/////////////////////////////////
}
