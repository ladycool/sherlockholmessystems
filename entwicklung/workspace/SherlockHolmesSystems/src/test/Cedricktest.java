package test;

import java.io.File;
import java.util.HashMap;

import CONTROLLER.Controller;

public class Cedricktest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Dieser Test ist gultig und erfolgreich
		
		String username = "cedrick",password="shs";
		/*
		String username = "cedrick1",password="shs";
		String username = "cedrick2",password="shs";
		String username = "cedrick3",password="shs";
		*/
		HashMap<String, String> attribute = new HashMap<String, String>();
		int i = 1;
		
		if(i == 0){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			attribute.put(Controller.shsconfig.language, "deu");		
			Controller.shsuser = Controller.shsconfig.loginSHS("signup",attribute);
		
		}else if(i == 1){
		
			attribute.put(Controller.shsconfig.username, username);
			attribute.put(Controller.shsconfig.password, password);
			Controller.shsuser = Controller.shsconfig.loginSHS("signin",attribute);
		
		}else{
			System.out.println('~'-'"');			
		}
		
		System.out.println(Controller.shsuser == null);
		System.out.println(Controller.shsuser.getattr(Controller.shsconfig.username));
		
		/*
		String fileId = "1";
		
		String[]userlist = new String[]{"cedrick1"};
		Controller.shsconfig.createticket(fileId, userlist);
		*/
		
		File file = new File("C:/Users/Shazem/Desktop/sss.txt");
		Controller.shsconfig.uploadfile(file);
		
		//VOILA
		//Avec ce code tu peux sauvegarder des fichiers dans la banque de données à volonté
		//Concernant les tickets, il faudrait tout d'abords ecrire n'importe quoi dans la table et voir si ca resort. 
		//Je suis encore sur createticket.

	}

}
