package SERVICE;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

import MODEL._Config;
import MODEL.enums.Logintype;


public class Config extends _Config {
	
	/*
	 * PRIVATE
	 */
	private static Config _config = new Config();
	private Config(){
		super();//do nothing
	}
	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static Config getInstance(){
		return Config._config;
	}
	
	
	/*
	 * FINAL
	 */
	
	
	/*
	 * METHODS
	 */
	/**
	 * @doc Die Methode holt sich je nach gesuchtem Dateientyp, Dateien aus einem Verzeichnis die anschliessend
	 * 		hochgeladen werden.
	 * @param folder: Verzeichinis in dem die Daei sich befinden
	 * @param suffix: Die Endung des gesuchten Dateientyp. z.B.: .java
	 * @param seqfirst: beim Uploaden -> seqfirst + Datei
	 * @param seqlast: beim Uploaden -> seqfirst + Datei + seqlast
	 * @param seperator: beim Uploaden -> seqfirst + Datei + seqlast + seperator + seqfirst + Datei + seqlast
	 * @return	seqfirst + Datei + seqlast + seperator + seqfirst + Datei + seqlast + ...
	 */
	public String libraryUploader(String _folder,String suffix,String seqfirst,String seqlast,String seperator){
		File folder = new File(_folder);
		String toreturn="";
		
		for (final File fileEntry : folder.listFiles()) {
			if(fileEntry.isFile() && fileEntry.getName().endsWith(suffix)){
				toreturn += seqfirst+fileEntry+seqlast+seperator;
			}
		}
		return toreturn;
	}
	
@Override
	public User login(String action,HashMap<String, Object>attributes){
		User shsuser=null;
		if(action.isEmpty()){//sign in
			
		}else{//sign up
					
		}
		return shsuser;
	}

	
	
	public String random(int n){
		String toreturn="";
		for (int i = 0; i < n; i++) {
			char randomsign = (char) ('a'+Math.random()*128);
			toreturn += randomsign;
		}
		return toreturn;
	}
}
