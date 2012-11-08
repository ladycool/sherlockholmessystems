package MODEL;

import java.io.File;
import java.util.HashMap;

import MODEL.enums.*;
import SERVICE.*;

public abstract class _Config {
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
	signupBackId="signupback",
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
	
	hiddensignId = "signaction" //SEHR WICHTIG
	;
	
	
	//Database
	public final String
	driver="jdbc:mysql:",
	port="3306",
	url="https://jonathan.sv.hs-mannheim.de/phpMyAdmin/",//@Engin: Den richtigen Link solltest du finden
	db="kirkelstillsystems",
	url_db=driver+url +":"+port+"//"+db,
	username="kirkelstill",
	password="kirkelstill",
	
	usertb="user"
	;	
	
	
	//ENUM
	public final Ciphertype
	asymmetric = Ciphertype.asymmetric,
	symmetric = Ciphertype.symmetric
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
	
	
	//METHODS
	/**
	 * @doc NOT DONE YET
	 * @param type
	 * @param attributes
	 * @return
	 */
	public abstract User login(String action,HashMap<String, Object>attributes);
	
}
