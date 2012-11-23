package SERVICE;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;

import CONTROLLER.Controller;
import MODEL.SERVICE._Config;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * 
 * @author Shazem (Patrick)
 *
 */
public class Config extends _Config {
	
	/*
	 * PRIVATE
	 */
	private static Config _config = new Config("deu");
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
	
	////////////////////LOGIN-START/////////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loginSHS(String type,HashMap<String,String>attributes){
		User _user = null;
		if(type.equals(this.signactionA)){//sign in
			_user =  signin(attributes);
		}else if(type.equals(this.signactionB)){//sign up
			_user = signup(attributes);
		}
		return _user;
	}
	
	/**
	 * @author Shazem (Patrick): ERLEDIGT
	 * @param attributes
	 * @return
	 */
	private User signup(HashMap<String, String>attributes){
		HashMap<String, String> toinsert = new HashMap<String, String>();
		String username = attributes.get(this.username);
		attributes.remove(this.username);
		username = username.replace("'","");
		String _username = super.wrap(username);
		
		ResultSet result = null;
		User user = null;
		
		try {
			try {
				result = Controller.shsdb.select(this.usertb,this.username,"username LIKE "+_username);
			} catch (Exception e) {
				//In java erzeugt ein leeres SELECT-Ergebnis eine Exception (So dumm ist java)
			}
			
			//result.next(); wird hier bei result.first() ersetzt
			if(!result.first()){
				
				//Tabelle: user
				toinsert.put(this.username,username);
				
				String password = attributes.get(this.password);				
				attributes.remove(this.password);
				
				String userId="";
				while(true){//Die Userid ist ebenfalls eindeutig
					userId = new String(super.random(username.length()+password.length()));//Step 1
					userId = userId.replace("?", "");
					try {
						result = Controller.shsdb.select(this.keytb,"userId","userId LIKE '"+userId+"%'");
					} catch (Exception e) {}
					if(!result.first()){
						break;
					}
				}
							
				password = super.insert(password,userId);//pseudo-password -- Step2				
				toinsert.put(this.password,Base64.encode(password.getBytes()));
				
				
				String pseudouserId = super.insert(userId,username);// Step3		
				toinsert.put(this.dbuserId, Base64.encode(pseudouserId.getBytes()));//pseudouserId to save
				
				toinsert.putAll(attributes);
				for (String elt : toinsert.keySet()) {
					toinsert.put(elt, super.wrap(toinsert.get(elt)));
				}
				
				
				
				toinsert.put("created_date", "NOW()");
				toinsert.put("last_mod_date",this.stamp);
				Controller.shsdb.insert(this.usertb, toinsert);
				
				
				
				//keys
				Controller.shscipher = Shscipher.getInstance(this.symkeysize,this.asymkeysize,this.symInstance,this.asymInstance);
				byte[] towrite;
				FileOutputStream fos;
				String tosave;
				File file;
				file = new File(this.keypath+username);
				file.mkdirs();
				
				buildkeyspath(username);//legt die Pfade für secret-,public- und private key fest
				
				
				towrite = Controller.shscipher.getkey();
				file = new File(this.secretkeypath);
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(towrite);				
				fos.close();
				
				HashMap<String, byte[]>towritepair = Controller.shscipher.getkeys();				
				towrite = towritepair.get(this.prik);
				file = new File(this.privatekeypath);
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(towrite);
				fos.close();				
				towrite = towritepair.get(this.pubk);
				file = new File(this.publickeypath);
				file.createNewFile();
				fos = new FileOutputStream(file);
				fos.write(towrite);
				fos.close();
				
				
				//NICHT Verschlüsselung und Speicherung des public Key in die Datenbank
				tosave = super.wrap(Base64.encode(towrite));
				Controller.shsdb.insert(this.pubkeytb,_username+","+tosave+",NULL");//DATABASE:public_keys
				
				
				//Finally neu USER
				HashMap<String,Object> userattr = new HashMap<String,Object>();
				
				userattr.put(this.username,username);
				userattr.put(this.userId,userId);
				userattr.put(this.keys,Controller.shscipher);
				userattr.put(this.userlang,attributes.get(this.languageId));
				user = User.getInstance(userattr);
				
			}else{
				String totrigger = Controller.shsdb.text(12).replace("%%",username);
				Controller.shsgui.triggernotice(totrigger);
			}
		} catch (SQLException | IOException e) {System.out.println(e.getMessage());
			Controller.shsgui.triggernotice(e);
		}
		
		return user;
	}
	
	/**
	 * @author Shazem (Patrick)
	 * @param attributes
	 * @return
	 */
	private User signin(HashMap<String,String>attributes){		
		User user = null;		
		try {
			String username = attributes.get(this.username);
			username = username.replace("'","");
			ResultSet result = null;
			
			try{
			result = Controller.shsdb.select(this.usertb, "*", "username LIKE "+(String)super.wrap(username));
			}catch(Exception e){}
			
			if(!result.next()){//Exit mit User == null
				String text = Controller.shsdb.text(8).replace("%%",username);
				Controller.shsgui.triggernotice(text);
			}else{
				//Tabelle user
				String fullname = result.getString(this.fullnameId);
				
				String pseudouserId = result.getString("userId");
				pseudouserId = new String(Base64.decode(pseudouserId));				
				String userId = this.remove(pseudouserId, username);
				
				String pseudopassword = result.getString("password");
				pseudopassword = new String(Base64.decode(pseudopassword));
				String password = this.remove(pseudopassword, userId);
				
				if(password.equals(attributes.get(this.password))){
					//Hier wird es festgestellt ob das Passwort das Richtige ist:
					//PASSWORD + USERID + USERNAME = MASTERKEY
					
					//keys
					HashMap<Integer, byte[]> keys = new HashMap<Integer, byte[]>();
					File keyfile;
					FileInputStream fis;
					byte[] encodedkey;
					
					buildkeyspath(userId);
					
					String _keys[] = new String[]{this.secretkeypath.replace("%%", userId),
												this.publickeypath.replace("%%", userId),
												this.privatekeypath.replace("%%", userId)};
					
					for (int i = 0; i < _keys.length; i++) {
						keyfile = new File(_keys[i]);
						fis = new FileInputStream(keyfile);
						encodedkey = new byte[(int) keyfile.length()];
						fis.read(encodedkey);
						fis.close();
						keys.put(i, encodedkey);
					}
					
					HashMap<String,Object> settings = new HashMap<String,Object>();
					settings.put("symkeysize",this.symkeysize);
					settings.put("asymkeysize",this.asymkeysize);
					settings.put("symInstance", this.symInstance);
					settings.put("asymInstance", this.asymInstance);
					
					Controller.shscipher = Shscipher.getInstance(settings, keys);
					//Die neue Instanz vom Shscipher wurde angelegt
					
					HashMap<String, Object> userattributes = new HashMap<String, Object>();
					userattributes.put(this.username, username);
					userattributes.put(this.userId, userId);
					userattributes.put(this.keys, Controller.shscipher);
					user = User.getInstance(userattributes);

				}else{//Exit mit User == null
					Controller.shsgui.triggernotice(Controller.shsdb.text(9));
				}
			}
		} catch (SQLException | Base64DecodingException | IOException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		return user;	
	}
	
    ////////////////////LOGIN-END/////////////////////////////
	
	///////////////////LOADUSERVIEW-START/////////////////////////
	/**
	 * {@inheritDoc}
	 */
	public void loadUserView(){
		//der user sollte aus der Session kommen
		//public wegen einem möglichen Reload-button
		Controller.shsthread = new Shsthread();
		Controller.shsthread.setName(this.threadinternal);
		Controller.shsthread.start();
		Controller.shsthread = new Shsthread();
		Controller.shsthread.setName(this.threadexternal);
		Controller.shsthread.start();		
		this.loadingstatus();
	}
	
	@Override
	public void loadinternalview(){
		HashMap<String,String> toreturn = new HashMap<String,String>();

		try {
			String _pseudouserId = (String) Controller.shsuser.getattr(this.userId) + (String) Controller.shsuser.getattr(this.username);
			byte[] pseudouserId = _pseudouserId.getBytes();
			pseudouserId = Controller.shscipher.crypt(pseudouserId,this.symInstance,this.encryptmode);
			_pseudouserId = super.wrap(Base64.encode(pseudouserId));
			ResultSet result=null,result1;
			try{
				result = Controller.shsdb.select(this.filestb,"id,`key`,pathdef","pseudouserId LIKE "+_pseudouserId);
			}catch(Exception e){}
			
			String path,sep,fileId,filename="";
			byte[] pseudopath,pseudokey;
			String[] temp;
			
			while(result.next()){
				path = ""; sep="";
				pseudokey = Base64.decode(result.getString("key"));
				pseudokey = Controller.shscipher.crypt(pseudokey,this.symInstance,this.decryptmode);
				pseudopath = Base64.decode(result.getString("pathdef"));
				pseudopath = Controller.shscipher.crypt(pseudopath, pseudokey, this.symInstance,this.decryptmode);
				
				temp = new String(pseudopath).split(this.sep);			
				for (String t : temp) {
					result1 = Controller.shsdb.select(this.pathtb,"pathname","def LIKE "+wrap(t));
					result1.next();
					path += sep+result1.getString("pathname");
					
					filename = result1.getString("pathname");
					sep = this.sep;
				}
				path = this.remove(path, this.sep+filename);
				
				fileId = result.getString("id");
				
				toreturn.put(fileId,filename);				
			}
			Controller.shsdb.text(33);
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		this.internalviewdata = toreturn;	
		this.intloadingisdone = true;
	}
	
	public void loadexternalview(){
		this.externalviewdata = gettickets();
		this.extloadingisdone = true;
	}
	
	///////////////////LOADUSERVIEW-END/////////////////////////
	
	
	////////////////////LOAD-FILE-START////////////////////////
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void uploadfile(File file){//JFileChooser
		try {
			HashMap<String,byte[]> _toinsert = new HashMap<String,byte[]>();
			HashMap<String,String> toinsert = new HashMap<String,String>();
			String 
			filepath = file.getPath(),_temp[] = filepath.split(this.sep),
			filename = _temp[_temp.length-1];
			
			//pseudo user
			String _pseudouserId = (String)Controller.shsuser.getattr(this.userId) + (String)Controller.shsuser.getattr(this.username);
			//Ich habe mich an der Stelle gegen Base64 um für den Fall dass die userId geknackt wird die Zuordnung nicht direkt zu erkennen
			//pseudouserId = Base64.encode(pseudouserId.getBytes());
			byte[] pseudouserId = Controller.shscipher.crypt(_pseudouserId.getBytes(), this.symInstance, this.encryptmode);
			_toinsert.put("pseudouserId", pseudouserId);		
			
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			byte[] pseudokey = super.random(this.symkeysize/8);
			byte[] content = Controller.shscipher.encryptfile(in, pseudokey);
			
			pseudokey = Controller.shscipher.crypt(pseudokey, this.symInstance, this.encryptmode);
			
			_toinsert.put("`key`",pseudokey);
			_toinsert.put("content",content);

			
			byte[] pathdef = filepath.getBytes();
			pathdef = Controller.shscipher.crypt(pathdef, this.symInstance, this.encryptmode);
			
			_toinsert.put("pathdef", pathdef);
			
			for (String key : _toinsert.keySet()) {
				toinsert.put(key, super.wrap(Base64.encode(_toinsert.get(key))));
			}
			toinsert.put("date",this.stamp);
			
			Controller.shsdb.insert(this.filestb, toinsert, Controller.shsdb.text(44));//INSERT

			Controller.shsgui.triggernotice(Controller.shsdb.text(32));
			
			//Eine Vereinfachung von this.loadinternalview();
			int fileId = Controller.shsdb.max(this.filestb);//Controller.shsdb.select(this.filestb, "MAX(id) AS id");
			/* --> createfolder
			if(this.internalviewdata.get(newpath) == null){
				this.internalviewdata.put(newpath, new ArrayList<String>());
			}
			*/
			
			this.internalviewdata.put(""+fileId,filename);			
			
		} catch (FileNotFoundException e) {
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
	public HashMap<String,String> previewfile(String id,String status){
		HashMap<String,String> file = new HashMap<String,String>();
		if(status.equals(this.owner)){
			file = this.preloadInternalfile(id);
		}else if(status.equals(this.reader)){
			file = this.preloadExternalfile(id);
		}
		
		return file;//filepath, content
	}
	
	/**
	 * Diese Methode bereitet die Datei vor der Anzeige oder dem Herunterladen vor, für den Besitzer der Datei
	 * @param fileid
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	private HashMap<String,String> preloadInternalfile(String fileId){
		HashMap<String, String> toreturn = new HashMap<String, String>();		
		try {
			ResultSet result = Controller.shsdb.select(this.filestb,"`key`,content,pathdef","id="+fileId);
			result.next();
			byte[]
			_content = Base64.decode(result.getString("content")),
			_pathdef = Base64.decode(result.getString("pathdef")),
			
			pseudokey = Controller.shscipher.crypt(Base64.decode(result.getString("key")), this.symInstance, this.decryptmode);
			_pathdef = Controller.shscipher.crypt(_pathdef, this.symInstance, this.decryptmode);
			
			//Entschlüsselung des Inhaltes
			_content = Controller.shscipher.crypt(_content,pseudokey,this.symInstance,this.decryptmode);		

			toreturn.put("filepath",new String(_pathdef));
			toreturn.put("content",new String(_content));
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	/**
	 * Diese Methode bereitet die Datei vor der Anzeige vor, für den nicht-Besitzer der Datei
	 * @param fileid
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	private HashMap<String,String> preloadExternalfile(String ticketId){
		HashMap<String, String> toreturn = new HashMap<String, String>();
		HashMap<String, Object> temp = this.readticket(ticketId);//fileid + key + path
		
		try {
			byte[] pseudokey = (byte[])temp.get("pseudokey");
			String fileId = (String)temp.get("fileId");
			ResultSet result = Controller.shsdb.select(this.filestb,"content","id="+fileId);
			result.next();
			
			//Entschlüsselung des Inhaltes
			byte[]
			_content = Base64.decode(result.getString("content"));
			_content = Controller.shscipher.crypt(_content,pseudokey,this.symInstance,this.decryptmode);
			
			toreturn.put("filepath", (String)temp.get("filepath"));
			toreturn.put("content", new String(_content));
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	
	/*
	@Override
	public String createfolder(String foldername){
		ResultSet result = null;//System.out.println("---->"+(String)Controller.shsuser.getattr(this.username));
		String 
		username = (String)Controller.shsuser.getattr(this.username),
		_foldername = "'%"+foldername+"%'",			
		_username = "'%"+username+"%'",
		toreturn="";	
		try {
			result = Controller.shsdb.select(this.pathtb,"def,users,id","pathname LIKE "+_foldername+" AND users NOT LIKE "+_username);
		} catch (Exception e) {}
		try {
			if(result.first()){
				String id = result.getString("id");
				String users = result.getString("users");
				users += "+"+(String)Controller.shsuser.getattr("username");
				users = (String)super.wrap(users);
				Controller.shsdb.update(this.pathtb,new String[]{"users","`date`"},new String[]{users,this.stamp},"id="+id);
			}else{
				try {
					result = Controller.shsdb.select(this.pathtb,"def,users,id","pathname LIKE "+_foldername+" AND users LIKE "+_username);
				} catch (Exception e) {}
				if(result.first()){
					Controller.shsgui.triggernotice(41);//Der Ordner existiert bereits
				}else{
					toreturn = new String(this.random(10));
					String values = super.wrap(toreturn)+","+super.wrap(foldername)+","+super.wrap(username)+","+this.stamp;
					Controller.shsdb.insert(this.pathtb, values);
				}
			}
			
			this.internalviewdata.put(foldername, new ArrayList<String>());
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		
		return toreturn;
	}
	*/
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete(String status,String datatype,String data){
		if(status.equals(this.owner)){
			deletedata(datatype,data);
		}else if(status.equals(this.reader)){
			hidedata(datatype,data);//ticketId
		}
	}
	
	private void deletedata(String datatype,String data){
			/*
			if(datatype.equals(this.foldertype)){
				String todelete = this.internalviewdata.get(data);//data = id
				String filedata[],ticketstodel[],fileId = "",ticketsalad;
				byte[]pseudokey,tickets;
				ResultSet result;
				
				for (String val : todelete) {
					filedata = val.split(this.sep+this.sep);
					fileId = filedata[0];			
				
					result = Controller.shsdb.select(this.filestb,"key,k_ticketsId","id ="+fileId);
					result.first();
					
					pseudokey = result.getBytes("key");
					pseudokey = Controller.shscipher.crypt(pseudokey,this.symInstance,this.decryptmode);
					
					if((tickets = result.getBytes("k_ticketsId")) != null){
						tickets = Controller.shscipher.crypt(tickets,pseudokey,this.symInstance,this.decryptmode);
						ticketsalad = new String(tickets);
						ticketstodel = ticketsalad.split(this.dbsep);
						for (String id : ticketstodel) {
							Controller.shsdb.delete(this.tickettb,"id="+id,"");
						}
					}
					
					Controller.shsdb.delete(this.filestb,"id="+fileId,"");
				}
				
				//Vereinfachte reload-Methode
				this.internalviewdata.remove(data);
				
			}else*/
			if(datatype.equals(this.filetype)){
				
			}else if(datatype.equals(this.tickettype)){
				//getrennt implementiert: deletetickets
			}			
	}
	
	////////////////////LOAD-FILE-END////////////////////////
	
	//////////////////////////////TICKETS-START/////////////////////////////////
	private void hidedata(String dataId,String datatype){
		if(datatype.equals(this.foldertype)){
			
		}else if(datatype.equals(this.filetype)){
			
		}
	}
	
	/**
	 * {@inheritDoc} //////////////////////////////HIER BIN ICH
	 */
	@Override
	public HashMap<String, String> gettickets(){
		HashMap<String,String> toreturn = new HashMap<String,String>();
		try {
			String _my_username = (String) Controller.shsuser.getattr("username");
			byte[] my_username = Controller.shscipher.crypt(_my_username.getBytes(),this.asymInstance,this.encryptmode);
			_my_username = super.wrap(Base64.encode(my_username));
			ResultSet result = Controller.shsdb.select("tickets","id,sent_by,filename","sent_to LIKE "+_my_username);
			String ticketId,_sent_by;
			byte[] sent_by,filename;
			while(result.next()){
				ticketId = result.getString("id");
				
				sent_by = result.getBytes("sent_by");
				sent_by = Controller.shscipher.crypt(sent_by,this.asymInstance,this.decryptmode);
				
				filename = result.getBytes("filename");
				filename = Controller.shscipher.crypt(filename,this.asymInstance,this.decryptmode);
				
				_sent_by = new String(sent_by);

				toreturn.put(ticketId,_sent_by+this.sep+this.sep+filename);	
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
	public HashMap<String, Object> readticket(String ticketId){
		HashMap<String, Object> toreturn = new HashMap<String, Object>();
		try{
			String my_username = (String) Controller.shsuser.getattr("username");
			byte[] _my_username = my_username.getBytes();
			byte[] publickey = Controller.shscipher.getkeys().get(this.pubk);
			_my_username = Controller.shscipher.crypt(_my_username, publickey, this.asymInstance, this.encryptmode);
			my_username = super.wrap(Base64.encode(_my_username));
			
			ResultSet result = Controller.shsdb.select("tickets","sent_by,fileId,filename,`key`","id="+ticketId+" AND sent_to LIKE "+my_username);
			result.next();
			
			String _sent_by = result.getString("sent_by");
			byte[] sent_by = Controller.shscipher.crypt(Base64.decode(_sent_by), this.asymInstance, this.decryptmode);
			
			String _filename = result.getString("filename");
			byte[] filename = Controller.shscipher.crypt(Base64.decode(_filename), this.asymInstance, this.decryptmode);
			
			String path = new String(sent_by)+this.sep+new String(filename);
			
			String _pseudokey = result.getString("key");
			byte[] pseudokey = Controller.shscipher.crypt(Base64.decode(_pseudokey),this.asymInstance,this.decryptmode);
			
			String _fileId = result.getString("fileId");
			byte[] fileId = Controller.shscipher.crypt(Base64.decode(_fileId),this.asymInstance,this.decryptmode);
			
			toreturn.put("fileId",new String(fileId));
			toreturn.put("pseudokey",pseudokey);
			toreturn.put("filepath", path);
			
		}catch(SQLException | Base64DecodingException e){
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void createticket(int fileId,String[] userlist){
		try {
			ResultSet result = null;
			String
			ticketsId="",readers="",_temp[];
			byte[] 
			sent_by = ((String) Controller.shsuser.getattr(this.username)).getBytes(),
			sent_to, his_publickey, pseudokey=null, filename, temp, _ticketsId,_readers,_fileId;
			boolean newticket=false;
			
			HashMap<String,String> currentreaderinfo = this.getcurrentreader(fileId);
			ticketsId = currentreaderinfo.get("ticketsId");
			readers = currentreaderinfo.get("readers");	
			
			for (String user : userlist){
				if(!readers.contains(user)){//überprüft ob der user bereits eine Erlaubnis hat
					newticket = true;
					
					result = Controller.shsdb.select(this.pubkeytb, "username,`key`","username LIKE '"+user+"'");//QUERY username kommt aus public_key_tb
					his_publickey = Base64.decode(result.getString("key"));
					
					sent_to = result.getString("username").getBytes();
					sent_to = Controller.shscipher.crypt(sent_to,his_publickey,this.asymInstance,this.encryptmode);
					
					sent_by = Controller.shscipher.crypt(sent_by,his_publickey,this.asymInstance,this.encryptmode);
					
					result = Controller.shsdb.select(this.filestb,"`key`,pathdef","id = "+fileId);//QUERY			
					pseudokey = Controller.shscipher.crypt(Base64.decode(result.getString("key")),this.symInstance, this.decryptmode);
					pseudokey = Controller.shscipher.crypt(pseudokey,his_publickey,this.asymInstance,this.encryptmode);
					
					temp = Controller.shscipher.crypt(Base64.decode(result.getString("pathdef")),this.symInstance,this.decryptmode); 
					_temp = new String(temp).split(this.sep);
					filename = Controller.shscipher.crypt(_temp[_temp.length-1].getBytes(),his_publickey,this.asymInstance,this.encryptmode);
					
					_fileId = Controller.shscipher.crypt((""+fileId).getBytes(),his_publickey,this.asymInstance,this.encryptmode);
					
					
					HashMap<String,byte[]> _toinsert = new HashMap<String, byte[]>();
					_toinsert.put("sent_by",sent_by);
					_toinsert.put("sent_to",sent_to);
					_toinsert.put("fileId",_fileId);
					_toinsert.put("filename",filename);
					_toinsert.put("`key`",pseudokey);
					HashMap<String,String> toinsert = new HashMap<String,String>();
					
					for (String key : _toinsert.keySet()) {
						toinsert.put(key,super.wrap(Base64.encode(_toinsert.get(key))));
					}
					
					Controller.shsdb.insert(this.tickettb, toinsert,Controller.shsdb.text(30));	
					int maxid = Controller.shsdb.max(this.tickettb);
					
					//Vorbereitung aufs Update der Spalten readers und k_ticketsId vor
					if(readers.isEmpty() && ticketsId.isEmpty()){
						readers = user;
						ticketsId = ""+maxid;
					}else{
						readers += Controller.shsconfig.dbsep+user;
						ticketsId += Controller.shsconfig.dbsep+maxid;
					}
				}
			}
			
			//Updae der Spalten readers und k_ticketsId
			if(newticket){
				_readers = Controller.shscipher.crypt(readers.getBytes(),this.symInstance,this.encryptmode);
				_ticketsId = Controller.shscipher.crypt(ticketsId.getBytes(),this.symInstance,this.encryptmode);
				
				HashMap<String,String> toupdate = new HashMap<String,String>();
				toupdate.put("k_ticketsId",super.wrap(Base64.encode(_ticketsId)));
				toupdate.put("readers",super.wrap(Base64.encode(_readers)));
				
				Controller.shsdb.update(this.filestb,toupdate,"id="+fileId);
			}
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteticket(int fileId,String[]userlist,String[] ticketIdlist){	
		try {
			HashMap<String, String> currentreaderinfo = this.getcurrentreader(fileId);
			String 
			ticketsId = currentreaderinfo.get("ticketsId"),
			readers = currentreaderinfo.get("readers")
			;	
			byte[] _readers,_ticketsId;
			
			for (int i=0;i < ticketIdlist.length;i++) {
				Controller.shsdb.delete(this.tickettb,"id="+ticketIdlist[i],Controller.shsdb.text(17)); 
				ticketsId = ticketsId.replace(ticketIdlist[i],"");
				readers = readers.replace(userlist[i],"");
			}
			
			//Vorbereitung aufs Update
			ticketsId = ticketsId.replace(this.dbsep+this.dbsep,this.dbsep);//++ -> +
			readers = readers.replace(this.dbsep+this.dbsep,this.dbsep);			
			if(ticketsId.startsWith(this.dbsep)){
				ticketsId = ticketsId.substring(1);
				readers = readers.substring(1);
			}
			if(ticketsId.endsWith(this.dbsep)){
				ticketsId = ticketsId.substring(0,ticketsId.length()-1);
				readers = readers.substring(0,ticketsId.length()-1);
			}
			
			
			//Update
			_readers = Controller.shscipher.crypt(readers.getBytes(),this.symInstance,this.encryptmode);
			_ticketsId = Controller.shscipher.crypt(ticketsId.getBytes(),this.symInstance,this.encryptmode);
			
			HashMap<String,String> toupdate = new HashMap<String,String>();
			toupdate.put("k_ticketsId",super.wrap(Base64.encode(_ticketsId)));
			toupdate.put("readers",super.wrap(Base64.encode(_readers)));
			
			Controller.shsdb.update(this.filestb,toupdate,"id="+fileId);
			
		} catch (SQLException | Base64DecodingException e) {
			Controller.shsgui.triggernotice(e);
		}
		
	}
	
	
	private HashMap<String,String> getcurrentreader(Object fileId) throws SQLException, Base64DecodingException{
		HashMap<String,String> toreturn = new HashMap<String,String>();
		ResultSet result = null;
		byte[] _ticketsId=null,_readers=null;
		String ticketsId="",readers="";
		
		try {
			result = Controller.shsdb.select(this.filestb,"k_ticketsId,readers","id="+fileId);
		} catch (Exception e) {}
		if(result.next()){
			
			_ticketsId = Base64.decode(result.getString("k_ticketsId"));
			_readers = Base64.decode(result.getString("readers"));
			
			_ticketsId = Controller.shscipher.crypt(_ticketsId,this.symInstance,this.decryptmode); 
			_readers = Controller.shscipher.crypt(_readers,this.symInstance,this.decryptmode); 
			
			ticketsId = new String(_ticketsId);
			readers = new String(_readers);
			//holt alle user die bereits eine Erlaubnis haben.
		}
		toreturn.put("ticketsId",ticketsId);
		toreturn.put("readers",readers);
		//toreturn.put("pseudokey",pseudokey);
		return toreturn;
	}
	//////////////////////////////TICKETS-ENDE/////////////////////////////////



}
