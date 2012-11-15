package CONTROLLER;

import MODEL.Database;
import MODEL.GUI;
import MODEL._Cipher;
import MODEL._Config;
import SERVICE.Config;
import SERVICE.HTML;
import SERVICE.Myadmin;
import SERVICE.Shscipher;
import SERVICE.User;

public class Controller{	
	
	private Controller() {}
	
	public static _Config shsconfig = Config.getInstance();
	public static GUI shsgui = HTML.getInstance();
	public static Database shsdb = Myadmin.getInstance();
	public static _Cipher shscipher;
	public static User shsuser;
}
