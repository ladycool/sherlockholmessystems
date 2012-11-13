package MODEL;

import java.io.File;
import java.util.HashMap;

import javax.crypto.Cipher;

import MODEL.enums.*;
import SERVICE.*;

public abstract class _Config {
	//About jsp - servlets
	//http://ww2.cis.temple.edu/cis308/Lectures/Unit%202/Servlets_ImplicitObjects/jsp_objects.htm	
	
	
	/*
	 * PRIVATE
	 */
	protected _Config(){}//do nothing
	
	
	/*
	 * PROTECTED
	 */
	protected int
	randomlength = 10	
	;	
	
	
	/*
	 * PUBLIC
	 */
	//paths
	private final String 
	jsp = ".jsp",
	sep = "/"
	;
	
	public final String	
	_view = "VIEW",
	view = sep+_view,
	
	js = view+sep+"javascript",
	css = view+sep+"css",
	viewcontrol = view+sep+"VIEWCONTROLLER",
	login = view+sep+"login"+jsp,
	controllboard = view+sep+"controllboard"+jsp,
	
	tags = css+sep+"tags.css",
	classes = css+sep+"classes.css",
	jquery = js+sep+"jquery.js",
	jsmeth = js+sep+"methods.js",
	header = viewcontrol+sep+"header"+jsp,
	mainnord = viewcontrol+sep+"marginnord"+jsp,
	mainsouth = viewcontrol+sep+"marginsouth"+jsp,
	mainwest = viewcontrol+sep+"marginwest"+jsp,
	progress = viewcontrol+sep+"progress"+jsp,
	signup	= viewcontrol+sep+"signup"+jsp,
	signin	= viewcontrol+sep+"signin"+jsp,
	
	png=".png",
	images = _view+sep+"images"+sep,
	arrowleft=images+"arrowLEFT"+png,
	arrowright=images+"arrowRIGHT"+png,
	arrowup=images+"arrowUP"+png,
	arrowdown=images+"arrowDOWN"+png,
	print=images+"print"+png
	;
	
	
	//Tag attributes
	public final String
	bodyId = "shsbody",
	mainId = "controllboard",
	signupId = "signup",
	signinId = "signin",
	headerId = mainId+"_header",
	mainnordId = mainId+"_nord",
	mainwestId = mainId+"_west",
	mainsouthId = mainId+"_south",
	consoleId = "progress",
	mainnordarrowId = mainnordId+"_arrow",
	mainwestarrowId = mainwestId+"_arrow",
	mainsoutharrowId = mainsouthId+"_arrow",
	consolearrowId = consoleId+"_arrow",
	mainwesttdId = mainwestId+"_td",
	mainnordtdId = mainnordId+"_td",
	consoletdId = consoleId+"_td",
	passwordId = "password",
	usernameId = "username",
	
	programName = "Holmes Secure",
	
	signactionId = "signaction", //SEHR WICHTIG
	signactionA = "signin",
	signactionB = "signup",
	uploadtypeA = "todb",
	uploadtypeB = "fromdb",
	uploadtypeC = "external",
	savesym = "savesym",
	savepubk = "savepubk",
	saveprik = "saveprik",
	
	
	popupId = "popup",
	title = "Sherlock Holmes Systems"
	;
	
	protected String
	lang = ""
	;
	public String getlang(){
		return lang;
	}
	
	
	//Database
	public final String
	driver="jdbc:mysql:",//com.mysql.jdbc.Driver
	port="3306",//3306
	url="//jonathan.sv.hs-mannheim.de/phpMyAdmin/",//141.19.141.151
	db="kirkelstillsystems",
	url_db= driver+url+db,//driver+url +":"+port+"/"+db,
	dbusername="kirkelstill",
	dbpassword="kirkelstill",
	
	usertb="user",
	dbuserId="userid",
	userid = dbuserId,
	keys="keys",
	keytb="key",
	filestb ="files",
	username="username",
	password="",
	txtlinebreak="<shsbr/>",
	stamp = "CURRENT_TIMESTAMP";
	;	
	
	
	//Cipher
	public int 
	keysize = 512,
	encryptmode = Cipher.ENCRYPT_MODE,
	decryptmode = Cipher.ENCRYPT_MODE
	;
	public String
	symInstance = "AES",
	asymInstance = "RSA",
	masterInstance = "master"
	;
	
	//ENUM
	public final Direction
	verti = Direction.verti,
	horiz = Direction.horiz,
	up = Direction.up,
	down = Direction.down,
	left = Direction.left,
	right = Direction.right
	;
	public final Logintype
	signinlogintype = Logintype.signin,
	signuplogintype = Logintype.signup
	;
	
	public final Viewertype
	owner = Viewertype.owner,
	reader = Viewertype.reader
	;
	
	
	
	/**
	 * @author Shazem (Patrick)
	 */
	//METHODS NORMAL
	protected String random(int length){
		String toreturn="";
		for (int i = 0; i < length; i++) {
			char randomsign = (char) ('a'+Math.random()*128);
			toreturn += randomsign;
		}
		return toreturn;
	}
	
	protected int randomnr(int margin){
		return (int) (Math.random()*margin);
	}
	
	/**
	 * Falls der searchtxt im fulltext vorhanden ist, so sollte er rausgeschnitten werden, ansonsten bleibt fulltxt ungeändert
	 * @author Shazem
	 * @param fulltxt
	 * @param searchtxt
	 * @return String
	 */
	protected String remove(String fulltxt,String searchtxt){
		if(fulltxt.contains(searchtxt)){
			fulltxt = fulltxt.replace(searchtxt,"");
		}
		return searchtxt;
	}
	
	/**
	 * newtxt = fulltxt(teil1) + inserttxt + fulltxt(teil2)
	 * @author Shazem
	 * @param fulltxt
	 * @param searchtxt
	 * @return String
	 */
	protected String insert(String fulltxt,String inserttxt){
		int insertpos = this.randomnr(fulltxt.length()-1);
		fulltxt = fulltxt.substring(0, insertpos) + inserttxt + fulltxt.substring(insertpos);//new fulltxt
		return fulltxt;
	}
	
	protected String wrap(String towrap, String with){
		return with + towrap + with;
	}
	
	protected String wrap(String towrap){
		return wrap(towrap,"'");
	}
	//METHODS ABSTRACT
	/**
	 * @doc In progress
	 * @param action: [signin || signup]
	 * @param attributes: benutzerspezifische Eigenschaften
	 * @return User || null
	 */
	protected abstract User loginSHS(String type,HashMap<String,String>attributes);
	
	protected abstract void loadUserView(User user); 
	
	/*
	 * loginSHS
	 * -->session stuff
	 * loadUserView
	 */
	
	protected abstract void uploadfile(String path,String toggleId);
	
	protected abstract void downloadfile(String path,int fileId);
	
	protected abstract void viewfile(String id,Viewertype status);
	
	/**
	 * Passiert beim Laden der Hauptseite.
	 * @return {@link HashMap} HashMap<Integer, String[]>: Integer = Zähler, String[]={0->ticketId,1->sent_by,2->filename}
	 */
	protected abstract HashMap<Integer, String[]> gettickets();
	
	/**
	 * Geschiet während dem Versuch sich eine Fremddatei anzusehen
	 * @param fileId
	 * @return {@link HashMap} HashMap<String,String>:"fileId"->fileId, "pseudokey"->Schlüssel zur Entschlüsselung der Datei
	 */
	protected abstract HashMap<String, String> readticket(String fileId);
	
	protected abstract void createticket(int fileId,String[] userList);


	
}
