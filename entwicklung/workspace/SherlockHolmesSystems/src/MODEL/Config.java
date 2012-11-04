package MODEL;

import MODEL.enums.*;
import SERVICE.*;

public class Config {
	/*
	 * PROTECTED
	 */
	protected Config(){
		
	}
	
	
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
	private static String jsp = ".jsp";
	
	public static final String 
	_view = "VIEW",
	view = "./"+_view,
	model = "./MODEL",
	controller = "./CONTROLLER",	
	viewcontrol = "VIEWCONTROLLER",
	
	signup	= viewcontrol+"/signup"+jsp,
	signin	= viewcontrol+"/signin"+jsp,
	fview = viewcontrol+"/filesview"+jsp,
	header = viewcontrol+"/header"+jsp,
	mleft = viewcontrol+"/marginleft"+jsp,
	progress = viewcontrol+"/progress"+jsp,
	css = "",
	jquery = ""
	;
	
	public static final String
	init = "shsIndex.jsp",
	login = _view+"/login"+jsp,
	cb = _view+"/controllboard"+jsp
	;
	
	
	//Tag names
	public static final String
	mainId = "controllboard",
	signupId = "signup",
	signinId = "signin",
	headerId = mainId+"_header",
	mainnordId = mainId+"_nord",
	mainwestId = mainId+"_west",
	mainsouthId = mainId+"_south",
	progressId = "progress"
	;
	
	
	//Database
	public static final String
	driver="jdbc:mysql:",
	port="3306",
	url="//https://jonathan.sv.hs-mannheim.de/phpMyAdmin/",
	db="kirkelstillsystems",
	url_db=driver+url +":"+port+"//"+db,
	username="kirkelstill",
	password="kirkelstill",
	
	usertb="user"
	;

	
	//Singeltons und Objekte
	public static final Database mydb = new Myadmin();
	public static Direction dir = null;
	public static Logintype mylogin = null;
	public static final GUI mygui = new HTML();
	//public static User shsuser;
	
}
