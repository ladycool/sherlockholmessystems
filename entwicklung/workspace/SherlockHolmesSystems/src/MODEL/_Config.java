package MODEL;

import java.util.ArrayList;
import java.util.HashMap;

import MODEL.SERVICE.__Config;
import SERVICE.User;

public abstract class _Config extends __Config{
	/**
	 * @author Shazem (Patrick)
	 */
	//METHODS NORMAL
	protected _Config(){super();}//do nothing
	
	
	protected byte[] random(int length){
		byte[] toreturn = new byte[length];
		for (int i = 0; i < length; i++) {
			byte randomsign = (byte) ('a'+Math.random()*128);
			toreturn[i] = randomsign;
		}
		return toreturn;
	}
	
	protected int randomnr(int margin){
		return (int) (Math.random()*margin);
	}
	
	/**
	 * Falls der searchtxt im fulltext vorhanden ist, so sollte er rausgeschnitten werden, ansonsten bleibt fulltxt ungeändert
	 * @author Shazem
	 * @param fulltxt
	 * @param searchtxt
	 * @return String
	 */
	protected String remove(String fulltxt,String searchtxt){
		if(fulltxt.contains(searchtxt)){
			fulltxt = fulltxt.replace(searchtxt,"");
		}
		return fulltxt;
	}
	
	protected String remove(String fulltxt,byte[]searchstream){
		String searchtxt = searchstream.toString();
		return remove(fulltxt, searchtxt);
	}
	
	protected byte[] remove(byte[] fullstream,byte[]searchstream){
		String fulltxt = fullstream.toString();
		String searchtxt = searchstream.toString();
		return remove(fulltxt, searchtxt).getBytes();
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
	
	protected String insert(String fulltxt,byte[]inserttxt){
		String temp = new String(inserttxt);
		return insert(fulltxt,temp);
	}
	
	protected String insert(byte[]fullstream,String inserttxt){
		String fulltxt = new String(fullstream);
		return insert(fulltxt, inserttxt);
	}
	
	protected byte[] insert(byte[] fullstream, byte[] insertstream){
		String fulltxt = new String(fullstream);
		String inserttxt = new String(insertstream);
		return insert(fulltxt, inserttxt).getBytes();
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
	
	public void setloadingisdone(){
		this.loadingisdone = true;
	}
	
	protected boolean loadingstatus(){//System.nanoTime();
		long curtime, lapsedtime = 0,trialnr=0;
		while(!this.loadingisdone && trialnr < 40){
			curtime = System.currentTimeMillis();
			while(lapsedtime <= 500){//jede halbe Sekunde
				lapsedtime = System.currentTimeMillis() - curtime;
			}
			trialnr++;
		}
		return this.loadingisdone;
	}
	
	public HashMap<String, ArrayList<String>> getinternalviewdata(){
		return this.internalviewdata;
	}
	
	public HashMap<String, ArrayList<String>> getexternalviewdata(){
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
	
	public abstract void uploadfile(String localpath,String newpath);
	
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
	public abstract HashMap<String, ArrayList<String>> gettickets();
	
	/**
	 * Geschiet während dem Versuch sich eine Fremddatei anzusehen
	 * @param fileId
	 * @return {@link HashMap} HashMap<String,String>:"fileId"->fileId, "pseudokey"->Schlüssel zur Entschlüsselung der Datei
	 */
	public abstract HashMap<String, Object> readticket(String fileId);
	
	public abstract void createticket(int fileId,String[] userlist);

	public abstract void deleteticket(int fileId,String[]userlist,String[] ticketIdlist);
	
	/**
	 * Diese Methode löscht sowohl Ordner, als auch Dateien und Tickets
	 * und aktualisiert das jeweilige Viewobjekt
	 * @param status //owner || reader
	 * @param datatype //folder || (intern)file || ticket
	 * @param data //folder -> path || (intern) file -> fileId || externe file/ticket -> ticketId
	 */
	public abstract void delete(String status,String datatype,String data);
	
	/**
	 * selbsterklärend
	 * @param foldername
	 */
	public abstract String createfolder(String foldername);
}
