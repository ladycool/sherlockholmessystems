package test;

import java.io.File;

import SERVICE.Config;
import SERVICE.Myadmin;

public class Launch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File a = Config.jsfile;
		System.out.println(a.getPath());
		
	}

}
