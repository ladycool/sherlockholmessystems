package MODEL.SERVICE;

import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import MODEL.__Config;
import SERVICE.User;

public abstract class _Config extends __Config{
	/**
	 * @author Shazem (Patrick)
	 */
	//METHODS NORMAL
	protected _Config(){super();}//do nothing
	
	
	protected byte[] random(int length){
		byte[] 
		toreturn = new byte[length],
		temp = new byte[]{'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b',
		                'c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
		                '1','2','3','4','5','6','7','8','9','0','+','-','_','!','"','@','%','&','(',')','=','?','*','[',']','^','|','<','>',
		                ',','.',';',':'};
		for (int i = 0; i < length; i++) { 
			toreturn[i] =temp[(int)(Math.random()*(temp.length-1))];
		}
		return toreturn;
	}
	
	protected int randomnr(int margin){
		return (int) (Math.random()*margin);
	}
	
	protected void buildkeyspath(String userId){
		this.secretkeypath = this.keypath+userId+this.sep+"secret.ppk";
		this.publickeypath = this.keypath+userId+this.sep+"public.ppk";
		this.privatekeypath = this.keypath+userId+this.sep+"private.ppk";
	}
	
	
	/**
	 * Falls der searchtxt im fulltext vorhanden ist, so sollte er rausgeschnitten werden, ansonsten bleibt fulltxt ungeändert
	 * @author Shazem
	 * @param fulltxt
	 * @param searchtxt
	 * @return String
	 */
	protected String remove(String fulltxt,String searchtxt){
		//System.out.println(fulltxt +"---"+searchtxt);
		if(fulltxt.contains(searchtxt)){
			fulltxt = fulltxt.replace(searchtxt,"");
		}
		//System.out.println(fulltxt);
		return fulltxt;
	}
	
	/**
	 * newtxt = fulltxt(teil1) + inserttxt + fulltxt(teil2)
	 * @author Shazem
	 * @param fulltxt
	 * @param searchtxt
	 * @return String
	 */
	protected String insert(String fulltxt,String inserttxt){
		int insertpos = this.randomnr(fulltxt.length()-1);
		fulltxt = fulltxt.substring(0, insertpos) + inserttxt + fulltxt.substring(insertpos);//new fulltxt
		return fulltxt;
	}
	
	
	protected String wrap(String towrap, String with){
		return with + towrap + with;
	}
	
	protected String wrap(String towrap){
		//towrap = towrap.replace("'","\\'"); //ohne die Kappselung würde jeder String, der ein Hochkomma beinhaltet, eine Fehlermeldung beim sql-insert verursachen.
		return wrap(towrap,"'");
	}
	
	protected String unwrap(String towrap){
		return towrap.replace("\\'", "'"); //sieh wrap();
	}
	
	
	protected boolean loadingstatus(){//System.nanoTime();
		boolean toreturn = false;
		long curtime, lapsedtime = 0,trialnr=0;
		while((!this.intloadingisdone || !this.extloadingisdone) && trialnr < 40){
			curtime = System.currentTimeMillis();
			while(lapsedtime <= 500){//jede halbe Sekunde
				lapsedtime = System.currentTimeMillis() - curtime;
			}
			trialnr++;
		}
		if(this.intloadingisdone && this.extloadingisdone){
			toreturn = true;
		}
		return toreturn;
	}
	
	
	public void setintloadingstatus(){
		if(this.intloadingisdone){
			this.intloadingisdone = false;
		}else{
			this.intloadingisdone = true;
		}
	}
	
	public void setextloadingstatus(){
		if(this.extloadingisdone){
			this.extloadingisdone = false;
		}else{
			this.extloadingisdone = true;
		}
	}
	
	public HashMap<String,String> getinternalviewdata(){
		return this.internalviewdata;
	}
	
	public HashMap<String,String> getexternalviewdata(){
		return this.externalviewdata;
	}
	
	
	//METHODS ABSTRACT
	/**
	 * @doc In progress
	 * @param action: [signin || signup]
	 * @param attributes: benutzerspezifische Eigenschaften
	 * @return User || null
	 */
	public abstract User loginSHS(String type,HashMap<String,String>attributes);
	
	public abstract void loadUserView(); 
	
	/*
	 * loginSHS
	 * -->session stuff
	 * loadUserView
	 */
	
	public abstract void uploadfile(File file);
	
	public abstract void uploadfile(String path);
	
	/**
	 * Selbsterklärend
	 */
	public abstract void loadinternalview();
	
	/**
	 * Selbsterklärend
	 */
	public abstract void loadexternalview();
	
	
	public abstract HashMap<String,String> downloadfile(String fileId);
	
	/**
	 * @param id: --> fileId beim internen Dateien, -->ticketId bei externen Datei
	 * @param status: owner | reader
	 * @return {@link HashMap} HashMap<String,String> = (filepath->"",content->"")
	 */
	public abstract HashMap<String,String> previewfile(String id,String status);
	
	/**
	 * Diese Methode holt alle aktive Tickets die für den benutzer beabsichtigt sind
	 * Diese wird bsp. beim Laden der Hauptseite aufgerufen 
	 * @return {@link HashMap} HashMap<Integer, String[]>: Integer = Zähler, String[]={0->ticketId,1->sent_by,2->filename}
	 */
	public abstract HashMap<String, String> gettickets();
	
	/**
	 * Geschiet während dem Versuch sich eine Fremddatei anzusehen
	 * @param fileId
	 * @return {@link HashMap} HashMap<String,String>:"fileId"->fileId, "pseudokey"->Schlüssel zur Entschlüsselung der Datei
	 */
	public abstract HashMap<String, Object> readticket(String fileId);
	
	public abstract void createticket(String fileId,String[] userlist);

	protected abstract void deleteticket(String fileId,String[]userlist,String[] ticketIdlist);
	
	public abstract void logoutSHS();
	/**
	 * Diese Methode löscht sowohl Ordner, als auch Dateien und Tickets
	 * und aktualisiert das jeweilige Viewobjekt
	 * @param status //owner || reader
	 * @param datatype //(intern)file || (intern)ticket || (extern)hide
	 * @param metadata intern -> (fileId(s) || ticketIds) || extern --> ticketId(s)
	 */
	public abstract void delete(String status,String datatype, HashMap<String,ArrayList<String>> metadata);
	
	
	//TOMCAT-GUI
	public abstract void uploadfile(DataInputStream in,String filename);
}
