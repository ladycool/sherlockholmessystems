package MODEL;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.Cipher;

import CONTROLLER.Controller;
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
	public final String 
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
	fullnameId = "fullname",
	addressId = "address",
	phonenrId = "phonenr",
	languageId = "language",
	
	programName = "Holmes Secure",
	
	signactionId = "signaction", //SEHR WICHTIG
	signactionA = "signin",
	signactionB = "signup",
	uploadtypeA = "todb",
	uploadtypeB = "fromdb",
	uploadtypeC = "external",
	savesym = "savesym",
	saveprik = "saveprik",
	savemaster = "savemaster",
	
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
	url="jonathan.sv.hs-mannheim.de", // "//jonathan.sv.hs-mannheim.de/phpMyAdmin/",
	host="localhost",
	db="kirkelstillsystems",
	url_db= driver+sep+sep+url+":"+port+sep+db,//driver+url +":"+port+"/"+db,
	dbusername="kirkelstill",
	dbpassword="kirkelstill",
	
	usertb="user",
	dbuserId="userid",
	userId = dbuserId,
	keys="keys",
	keytb="`keys`",
	pubkeytb="public_keys",
	filestb ="files",
	pathtb = "path_definition",
	username="username",
	password="password",
	language="language",
	txtlinebreak="<shsbr/>",
	stamp = "CURRENT_TIMESTAMP";
	;	
	
	//OTHERS
	public String
	userlang = "language",
	pubk = "pubk",
	prik = "prik"
	;
	
	
	//Cipher
	public int 
	keysize = 512,
	encryptmode = Cipher.ENCRYPT_MODE,
	decryptmode = Cipher.ENCRYPT_MODE
	;
	public String
	symInstance = "AES",
	asymInstance = "RSA",//"RSA/ECB/PKCS1Padding"
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
	
	
	//THREADS and THREADS-RELATED + Session and Session-related
	public final String
	threadinternal = "maininternal",
	threadexternal = "mainexternal",
	notice = "notice",
	inputdata = "inputdata"
	;
	protected HashMap<String, ArrayList<String>> externalviewdata = new HashMap<String, ArrayList<String>>();
	protected HashMap<String, ArrayList<String>> internalviewdata = new HashMap<String, ArrayList<String>>();
	protected boolean loadingisdone = false;
	
	
	
	
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
		return fulltxt;
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
		towrap = towrap.replace("'","\\'"); //ohne die Kappselung würde jeder String, der ein Hochkomma beinhaltet, eine Fehlermeldung beim sql-insert verursachen.
		return wrap(towrap,"'");
	}
	
	protected String unwrap(String towrap){
		return towrap.replace("\\'", "'"); //sieh wrap();
	}
	
	public void setloadingisdone(){
		this.loadingisdone = true;
	}
	
	protected boolean loadingstatus(){//System.nanoTime();
		long curtime, lapsedtime = 0,trialnr=0;
		while(!this.loadingisdone && trialnr < 40){
			curtime = System.currentTimeMillis();
			while(lapsedtime <= 500){//jede halbe Sekunde
				lapsedtime = System.currentTimeMillis() - curtime;
			}
			trialnr++;
		}
		return this.loadingisdone;
	}
	
	public HashMap<String, ArrayList<String>> getinternalviewdata(){
		return this.internalviewdata;
	}
	
	public HashMap<String, ArrayList<String>> getexternalviewdata(){
		return this.externalviewdata;
	}
	//METHODS ABSTRACT
	/**
	 * @doc In progress
	 * @param action: [signin || signup]
	 * @param attributes: benutzerspezifische Eigenschaften
	 * @return User || null
	 */
	public abstract User loginSHS(String type,HashMap<String,String>attributes);
	
	public abstract void loadUserView(); 
	
	/*
	 * loginSHS
	 * -->session stuff
	 * loadUserView
	 */
	
	public abstract void uploadfile(String path,String toggleId);
	
	/**
	 * Selbsterklärend
	 */
	public abstract void loadinternalview();
	
	/**
	 * Selbsterklärend
	 */
	public abstract void loadexternalview();
	
	
	public abstract HashMap<String,String> downloadfile(String fileId);
	
	/**
	 * @param id: --> fileId beim internen Dateien, -->ticketId bei externen Datei
	 * @param status: owner | reader
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	protected abstract HashMap<String,String> previewfile(String id,Viewertype status);
	
	/**
	 * Passiert beim Laden der Hauptseite.
	 * @return {@link HashMap} HashMap<Integer, String[]>: Integer = Zähler, String[]={0->ticketId,1->sent_by,2->filename}
	 */
	public abstract HashMap<String, ArrayList<String>> gettickets();
	
	/**
	 * Geschiet während dem Versuch sich eine Fremddatei anzusehen
	 * @param fileId
	 * @return {@link HashMap} HashMap<String,String>:"fileId"->fileId, "pseudokey"->Schlüssel zur Entschlüsselung der Datei
	 */
	public abstract HashMap<String, String> readticket(String fileId);
	
	public abstract void createticket(int fileId,String[] userList);


	
}
