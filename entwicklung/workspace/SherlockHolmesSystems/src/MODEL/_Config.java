package MODEL;

import java.io.File;

import MODEL.enums.*;
import SERVICE.*;

public class _Config {
	/*
	 * PRIVATE
	 */
	protected _Config(){}//do nothing
	
	
	/*
	 * PROTECTED
	 */
	protected static int
	randomlength = 10	
	;
	protected static Shscipher 
	symchipher = new Symmcipher(),
	asymchipher = new Asymmcipher()
	;
	
	
	
	/*
	 * PUBLIC
	 */
	//paths
	private static final String 
	jsp = ".jsp",
	sep = "/"
	;
	
	public static final String	
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
	
	
	//Tag names
	public static final String
	mainId = "controllboard",
	signupId = "signup",
	signupBackId="signupback",
	signinId = "signin",
	headerId = mainId+"_header",
	mainnordId = mainId+"_nord",
	mainwestId = mainId+"_west",
	mainsouthId = mainId+"_south",
	consoleId = "progress"
	;
	
	
	//Database
	public static final String
	driver="jdbc:mysql:",
	port="3306",
	url="//https://jonathan.sv.hs-mannheim.de/phpMyAdmin/",//@Engin: Den richtigen Link solltest du finden
	db="kirkelstillsystems",
	url_db=driver+url +":"+port+"//"+db,
	username="kirkelstill",
	password="kirkelstill",
	
	usertb="user"
	;

	
	//Singeltons und Objekte
	public static final Database shsdb = new Myadmin();
	public Direction shsDir = null;
	public static Logintype shslogin = null;
	public static final GUI shsgui = new HTML();
	//public static User shsuser;
	
	
	
	//ENUM
	public static final Direction
	verti = Direction.verti,
	horiz = Direction.horiz
	;
}
