package MODEL;

import java.util.HashMap;

import javax.crypto.Cipher;

import MODEL.enums.Direction;
import MODEL.enums.Logintype;

public class __Config{
	//About jsp - servlets
	//http://ww2.cis.temple.edu/cis308/Lectures/Unit%202/Servlets_ImplicitObjects/jsp_objects.htm	
	
	
	/*
	 * PRIVATE
	 */
	protected __Config(){}//do nothing
	
	
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
	sep = "/",
	_sep = "\\";
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
	shsuser = "shsuser",
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
	
	usertb="user_test",
	pubkeytb="public_keys_test",
	filestb ="files_test",
	tickettb = "tickets_test",	
	keytb="`keys`",
			
	dbuserId="userid",
	userId = dbuserId,
	keys="keys",
	pathtb = "path_definition",	
	username="username",
	password="password",
	language="language",
	txtlinebreak="<shsbr/>",
	stamp = "CURRENT_TIMESTAMP",
	defaultordner="home", 
	dbsep = "&"
	;	
	
	//AJAXHANDLER
	public final String
	ajId = "id",
	ajstatus = "status",
	ajlocalpath = "localpath",
	ajnewpath = "newpath",
	ajdatatyp = "datatyp",
	ajdata = "data",
	ajuserlist = "ajuserlist"
	;
	
	
	
	//OTHERS
	public final String
	userlang = "language",
	pubk = "pubk",
	prik = "prik",
	owner = "owner",
	reader = "reader",
	foldertype = "folder",
	filetype = "file",
	tickettype = "ticket",
	ticketIdlist = "ticketIdlist",
	readerlist = "userlist",
	fileId = "fileId"
	;
	public final String[]retrictedsigns = {"'","/","\\"};
	
	
	
	//Cipher
	public int 
	symkeysize = 128,//256 -> pseudokey = new String().length() = 16;
	asymkeysize = 512,
	encryptmode = Cipher.ENCRYPT_MODE,
	decryptmode = Cipher.DECRYPT_MODE
	;
	public String
	symInstance = "AES",
	asymInstance = "RSA",
	masterInstance = "master",
	threadsep = "<<>>",
	keypath = "keys"+sep,
	secretkeypath = keypath+"%%"+sep+"secret.ppk",
	publickeypath = keypath+"%%"+sep+"public.ppk",
	privatekeypath = keypath+"%%"+sep+"private.ppk",
	savesym = "####",
	saveprik = "++++",
	savemaster = "===="
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
	
	
	//THREADS and THREADS-RELATED + Session and Session-related
	public final String
	threadinternal = "maininternal",
	threadexternal = "mainexternal",
	notice = "notice",
	inputdata = "inputdata"
	;
	protected HashMap<String,String> externalviewdata = new HashMap<String,String>();
	protected HashMap<String,String> internalviewdata = new HashMap<String,String>();
	protected boolean intloadingisdone = false;
	protected boolean extloadingisdone = false;
	
	
	
	//Object-ATTRIBUTES

}
