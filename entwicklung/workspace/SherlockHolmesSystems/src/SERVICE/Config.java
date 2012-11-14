package SERVICE;

import java.io.File;
import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
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
	/**
	 * {@inheritDoc}
	 */
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
		HashMap<String, String> toinsert = new HashMap<String, String>();
		String username = attributes.get(this.username);		
		ResultSet result = Controller.shsdb.select(this.usertb,"username", "username LIKE '"+username+"'","");
		User user = null;
		
		try {
			if(result.getString(this.username).isEmpty()){
				
				//Tabelle: user
				String password = attributes.get(this.password);
				String userId = this.random(username.length()+password.length());
				
				//userId eindeutig festlegen
				//////////////////////TO DO ---> passwortcipher(String username, String passwort = pseudopasswort, pseudouserid)
				password = this.insert(password, userId);//pseudo-password
				/*
				 * @Luis:
				 * username,password
				 * pseudouserid = Base64.decode(pseudouserid)
				 * userid = remove(pseudouserid,username)
				 * password = Base64.decode(password)
				 */
				password = Base64.encode(password.getBytes());//password to save
				attributes.put(this.password, password);
				
				String pseudouserId = this.insert(userId,username);
				pseudouserId = Base64.encode(pseudouserId.getBytes());//pseudouserId to save
				attributes.put(this.dbuserId, pseudouserId);
				
				toinsert.putAll(attributes);
				for (String elt : toinsert.keySet()) {
					elt = wrap(elt);
				}
				toinsert.put("created_date", "NOW()");
				toinsert.put("last_mod_date",this.stamp);
				Controller.shsdb.insert(this.usertb, toinsert,"");
				
				
				//Tabelle: keys
				Controller.shscipher = Shscipher.getInstance(this.keysize,this.symInstance,this.asymInstance);
				String cryptedkey="",tocrypt2 = "";
				
				//Verschl�sselung und Speicherung des geheimen symmetrischen Schl�ssels
				byte[] tocrypt = Controller.shscipher.getkey(this.symInstance);
				tocrypt2 = new String(tocrypt) + this.savesym; //Zu dem bereits vorhandenen Schl�ssel wird ein St�ck text hinzugef�gt um die Zuordnug beim
																			//Entschl�sseln zu erm�glichen.
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				HashMap<String, byte[]>tocrypt1 = Controller.shscipher.getkey();
				//Verschl�sselung und Speicherung des public Key
				tocrypt2 = new String(tocrypt1.get("pubk"))+this.savepubk;//Analog zum savesym
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				//Verschl�sselung und Speicherung des private Key
				tocrypt2 = new String(tocrypt1.get("prik"))+this.saveprik; // Analog zum savesym
				cryptedkey = Controller.shscipher.crypt(tocrypt2,this.masterInstance,this.encryptmode);
				Controller.shsdb.insert(this.keytb,"",userId+","+cryptedkey,"");//DATABASE
				
				HashMap<String,Object> userattr = new HashMap<String,Object>();
				userattr.put(this.username,attributes.get(this.username));
				userattr.put(this.userId,userId);
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
		User user = null;		
		try {
			String username = attributes.get(this.username);
			ResultSet result = Controller.shsdb.select(this.usertb, "*", "username LIKE '"+username+"'");
			if(result.getString("username").isEmpty()){//Exit mit User == null
				String text = Controller.shsdb.text(12).replace("%%",username);
				Controller.shsgui.triggernotice(text);
			}else{
				//Tabelle user
				String pseudouserId = result.getString("userId");
				pseudouserId = new String(Base64.decode(pseudouserId));				
				String userId = this.remove(pseudouserId, username);
				
				String pseudopassword = result.getString("password");
				pseudopassword = new String(Base64.decode(pseudopassword));
				String password = this.remove(pseudopassword, userId);
				
				if(password.equals(attributes.get(this.password))){
					//erfolgreiche Authentifikation
					//Hier kannst du weiter arbeiten Luis 2012.11.14
					/*
					 * Benutzt die Methode signup als St�ztmethode
					 * Dein Chiper-objekt wird folgendes sein: Shscipher getInstance(String masterkey,String shssymkey,String privateky,String publickey)
					 * 
					 */
				}else{//Exit mit User == null
					Controller.shsgui.triggernotice(Controller.shsdb.text(14));
				}
			}
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		return user;	
	}
	
    ////////////////////LOGIN-END/////////////////////////////
	
	///////////////////LOADUSERVIEW-START/////////////////////////
	/**
	 * {@inheritDoc}
	 */
	public void loadUserView(User user){
		//der user sollte aus der Session kommen
		//public wegen einem m�glichen Reload-button
		
		if(user != null){
			Thread threadinternal = new Shsthread();
			threadinternal.setName(this.threadinternal);
			Thread threadexternal = new Shsthread();
			threadexternal.setName(this.threadinternal);
			
			threadinternal.start();
			threadexternal.start();
			
		}else{
			//redirect with triggernotice
		}
		
		//Threads will be created here.
	}
	
	@Override
	public void loadexternalview(){
		HashMap<Integer, String[]> toreturn = new HashMap<Integer, String[]>();
		try {
			String userId = (String) Controller.shsuser.getattr("userId");
			String pseudouserId = Controller.shscipher.crypt(userId,this.symInstance,this.decryptmode);
			ResultSet result = Controller.shsdb.select(this.filestb,"id,key,pathdef","pseudouserId LIKE "+wrap(pseudouserId)),
			result1;
			String pseudokey,content,pseudopath,path,_sep,fileId;
			String[] temp;
			int i = 0;
			
			while(result.next()){
				path = ""; _sep="";
				pseudokey = result.getString("key");
				pseudopath = result.getString("pathdef");
				pseudopath = Controller.shscipher.crypt(pseudopath, pseudouserId, this.symInstance,this.decryptmode);
				temp = pseudopath.split(this.sep);
								
				for (String t : temp) {
					result1 = Controller.shsdb.select(this.pathtb,"pathname","def LIKE "+wrap(t));
					path += _sep+result1.getString("pathname");
					_sep = this.sep;
				}
				
				fileId = result.getString("id");
				toreturn.put(i, new String[]{fileId,path});
				i++;				
			}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		this.externalviewdata = toreturn;		
	}
	
	public void loadinternalview(){
		this.internalviewdata = gettickets();
	}
	
	///////////////////LOADUSERVIEW-END/////////////////////////
	
	
	////////////////////LOAD-FILE-START////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uploadfile(String localpath,String newpath){
		try {
			HashMap<String,String> toinsert = new HashMap<String, String>();
			//pseudo user
			String pseudouserId = this.insert((String)Controller.shsuser.getattr("userId"),username);
			//Ich habe mich an der Stelle gegen Base64 um f�r den Fall dass die userId geknackt wird die Zuordnung nicht direkt zu erkennen
			//pseudouserId = Base64.encode(pseudouserId.getBytes());
			pseudouserId = wrap(Controller.shscipher.crypt(pseudouserId, this.symInstance, this.encryptmode));
			toinsert.put("pseudouserId", pseudouserId);
			
			String[]temp = localpath.split("/");
			String pseudokey = this.random(this.keysize);
			String content = wrap(Controller.shscipher.encryptfile(localpath, pseudokey));			
			pseudokey = wrap(Controller.shscipher.crypt(pseudokey, this.symInstance, this.encryptmode));
			
			toinsert.put("key", pseudokey);
			toinsert.put("content", content);
			
			ResultSet result = Controller.shsdb.select("path_definition", "def", "pathname LIKE '"+newpath+"'");			
			String pathdef = result.getString("def")+"/"+temp[temp.length-1];
			pathdef = wrap(Controller.shscipher.crypt(pathdef, this.symInstance, this.encryptmode));
			
			toinsert.put("pathdef", pathdef);
			toinsert.put("date",this.stamp);
			
			Controller.shsdb.insert(this.filestb, toinsert, Controller.shsdb.text(44));
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String,String> downloadfile(String fileId){
		return preloadInternalfile(fileId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public HashMap<String,String> previewfile(String id,Viewertype status){
		HashMap<String,String> file = new HashMap<String,String>();
		if(status.equals(this.owner)){
			file = this.preloadInternalfile(id);
		}else if(status.equals(this.reader)){
			file = this.preloadExternalfile(id);
		}
		
		return file;
	}
	
	/**
	 * Diese Methode bereitet die Datei vor der Anzeige oder dem Herunterladen vor, f�r den Besitzer der Datei
	 * @param fileid
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	private HashMap<String,String> preloadInternalfile(String fileId){
		HashMap<String, String> toreturn = new HashMap<String, String>();		
		try {
			ResultSet result = Controller.shsdb.select(this.filestb,"key,content,pathdef","id="+fileId);
			String pseudokey = result.getString("key");
			String content = result.getString("content");
			String pathdef = result.getString("pathdef");
			pseudokey = Controller.shscipher.crypt(pseudokey, this.symInstance, this.decryptmode);
			content = Controller.shscipher.crypt(content, pseudokey, this.symInstance, this.decryptmode);
			pathdef = Controller.shscipher.crypt(pathdef, this.symInstance, this.decryptmode);
			
			String[] temp = pathdef.split("/");
			pathdef="";
			String sep="";
			for (String t : temp) {
				result = Controller.shsdb.select(this.pathtb,"pathname","def LIKE '"+t+"'");
				pathdef +=sep+result.getString("pathname");
				sep="/";
			}

			toreturn.put("filepath",pathdef);
			toreturn.put("content",content);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toreturn;
	}
	
	/**
	 * Diese Methode bereitet die Datei vor der Anzeige vor, f�r den nicht-Besitzer der Datei
	 * @param fileid
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	private HashMap<String,String> preloadExternalfile(String ticketId){
		HashMap<String, String> toreturn = new HashMap<String, String>();
		HashMap<String, String> temp = this.readticket(ticketId);//fileid + key + path
		toreturn.put("filepath", temp.get("filepath"));
		
		String pseudokey = temp.get("pseudokey");
		String fileId = temp.get("fileId");
		String content = Controller.shscipher.readfile(pseudokey, fileId);
		content = content.replace(this.txtlinebreak, "<br/>");
		toreturn.put("content", content);
		
		return toreturn;
	}
	
	////////////////////LOAD-FILE-END////////////////////////
	
	//////////////////////////////TICKETS-START/////////////////////////////////
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 * * @author Shazem(Patrick)
	 */
	@Override
	public HashMap<String, String> readticket(String ticketId){
		HashMap<String, String> toreturn = new HashMap<String, String>();
		try{
			ResultSet result = Controller.shsdb.select("tickets","sent_by,fileId,filename,`key`","id="+ticketId);
			
			String sent_by = result.getString("sent_by");
			sent_by = Controller.shscipher.crypt(sent_by, this.asymInstance, this.decryptmode);
			String filename = result.getString("filename");
			filename = Controller.shscipher.crypt(filename, this.asymInstance, this.decryptmode);
			String path = sent_by+"/"+filename;
			
			String pseudokey = result.getString("key");
			pseudokey = Controller.shscipher.crypt(pseudokey,this.asymInstance,this.decryptmode);
			
			String fileId = result.getString("fileId");
			fileId = Controller.shscipher.crypt(fileId,this.asymInstance,this.decryptmode);
			
			toreturn.put("fileId",fileId);
			toreturn.put("pseudokey",pseudokey);
			toreturn.put("filepath", path);
		}catch(SQLException e){
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 * @author Shazem(Patrick)
	 */
	@Override
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
