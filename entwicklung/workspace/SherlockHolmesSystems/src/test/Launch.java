package test;

import MODEL.Database;
import SERVICE.Config;
import SERVICE.Myadmin;


public class Launch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args){
		
		//Database a = Myadmin.getInstance();
		Config a = Config.getInstance();
		System.out.println(a.random(16));
	}

}
