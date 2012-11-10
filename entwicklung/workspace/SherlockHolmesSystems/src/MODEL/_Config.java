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

	arrowleft=_view+sep+"images"+sep+"arrowLEFT.png",
	arrowright=_view+sep+"images"+sep+"arrowRIGHT.png",
	arrowup=_view+sep+"images"+sep+"arrowUP.png",
	arrowdown=_view+sep+"images"+sep+"arrowDOWN.png"
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
	
	signactionId = "signaction", //SEHR WICHTIG
	signactionA = "signin",
	signactionB = "signup",
	uploadtypeA = "internal",
	uploadtypeB = "external",
	savesym = "savesym",
	savepubk = "savepubk",
	saveprik = "saveprik",
	
	
	popupId = "popup",
	title = "Sherlock Holmes Systems"
	;
	
	
	//Database
	public final String
	driver="jdbc:mysql:",
	port="443",//3306
	url="https://jonathan.sv.hs-mannheim.de/phpMyAdmin/",//@Engin: Den richtigen Link solltest du finden
	db="kirkelstillsystems",
	url_db=driver+url +":"+port+"//"+db,
	username="kirkelstill",
	password="kirkelstill",
	
	usertb="user",
	dbuserId="userid",
	userid = dbuserId,
	keytb="key"
	;	
	
	
	//Cipher
	public int 
	keysize = 512,
	encryptmode = Cipher.ENCRYPT_MODE,
	decryptmode = Cipher.ENCRYPT_MODE
	;
	public String
	symInstance = "AES",
	asymInstance = "RSA"
	;
	
	//ENUM
	public final Ciphertype
	asymmetric = Ciphertype.asymmetric,
	symmetric = Ciphertype.symmetric,
	master = Ciphertype.master
	;
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
	
	
	//METHODS NORMAL
	public String random(int length){
		String toreturn="";
		for (int i = 0; i < length; i++) {
			char randomsign = (char) ('a'+Math.random()*128);
			toreturn += randomsign;
		}
		return toreturn;
	}
	
	public int randomnr(int margin){
		return (int) (Math.random()*margin);
	}
	
	
	//METHODS ABSTRACT
	/**
	 * @doc In progress
	 * @param action: [signin || signup]
	 * @param attributes: benutzerspezifische Eigenschaften
	 * @return User || null
	 */
	public abstract void loginSHS(String type,HashMap<String,String>attributes);
	
	public abstract void uploaddata(String type,Object path,String toggleId);
	
	protected abstract void savecreatedKeys();
	
	public abstract HashMap<String, Object>getKeys();
	
	protected abstract void getticket();
	
	public abstract void createticket(String path,String name);
	
	public abstract void shsinit();
	
}
