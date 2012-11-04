package MODEL;

public interface Database {
	//public boolean connect ();
		
	/**
	 * @deprecated Solbald die Verbindung zur Datenbank erzeugt wird, wird diese Methode gelöscht werden. 
	 * @param tx
	 * @return
	 */
	public String text(String tx);
	
	//CRUD-Befehle
	/**
	 * @doc INSERT-Befehl in mysql == CREATE
	 * @param values
	 * @param fields
	 * @param table
	 */
	public void insert(String table,String fields,String values);
	
	/**
	 * @doc INSERT-Befehl in mysql == CREATE
	 * 		Erweiterng der insert-Methode
	 * @param values
	 * @param fields
	 * @param table
	 * @param info: Bei einer erfolgreichen Durchführung des Befehls sollte eine Meldung angezeigt werden
	 */
	public void insert(String table,String fields,String values,String info);
	
	/**
	 * @doc SELECT-Befehl in mysql == READ
	 * @param fields
	 * @param table
	 * @param condition
	 * @return String[]
	 */
	public String[] select(String table,String fields,String condition);
	
	/**
	 * @doc SELECT-Befehl in mysql == READ
	 * 		Reserviert für Befehle die höchstens eine Zeile als Antwort haben.
	 * @param fields
	 * @param table
	 * @param condition
	 * @return String (leerer String wenn die Suche erfolgreich gewesen ist)
	 */
	public String select1(String table,String fields,String condition);
	
	/**
	 * @doc UPDATE-Befehl in mysql == UPDATE
	 * @param table
	 * @param fields
	 * @param values
	 * @param condition
	 */
	public void update(String table,String fields,String values,String condition);
	
	/**
	 * @doc UPDATE-Befehl in mysql == UPDATE
	 * 		Erweiterng der update-Methode
	 * @param table
	 * @param fields
	 * @param values
	 * @param condition
	 * @param info: Bei einer erfolgreichen Durchführung des Befehls sollte eine Meldung angezeigt werden
	 */
	public void update(String table,String fields,String values,String condition,String info);
	
	/**
	 * @doc TRUNCATE-Befehl in mysql == DELETE (DROP könnte unter Umstände später dazu kommen)
	 * @param table
	 * @param fields
	 * @param values
	 * @param condition
	 * @param info
	 */
	public void delete(String table,String fields,String values,String condition,String info);
	
	//CRUD-Befehle---END
	
	/**
	 * @doc Mit der Hilfe der Hilfe einer bekomme ich einen Text
	 * 		Basiert auf dem select-Befehl: SELECT name_ FROM text WHERE id = id
	 * @param id: Id der gesuchten Zeile in der Table 'Text'
	 * @return String
	 */
	public String text(int id);
	
	
}
