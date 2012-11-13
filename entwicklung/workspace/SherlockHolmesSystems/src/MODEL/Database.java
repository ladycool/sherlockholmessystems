package MODEL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public interface Database {
	// public boolean connect ();

	// CRUD-Befehle
	/**
	 * @doc INSERT-Befehl in mysql == CREATE Erweiterng der insert-Methode
	 * @param values
	 * @param fields
	 * @param table
	 * @param info
	 *            : Bei einer erfolgreichen Durchführung des Befehls sollte eine
	 *            Meldung angezeigt werden
	 * @throws Exception
	 */
	public void insert(String table, String fields, String values, String info);
	
	public void insert(String table,String[] fields,String[] values,String info);
	
	public void insert(String table,HashMap<String, String>attributes,String info);
	
	
	
	/**
	 * @doc UPDATE-Befehl in mysql == UPDATE Erweiterng der update-Methode
	 * @param table
	 * @param fields
	 * @param values
	 * @param condition
	 * @param info: Bei einer erfolgreichen Durchführung des Befehls sollte eine Meldung angezeigt werden
	 */
	public void update(String table, String[] fields, String[] values,String condition,String info);
	
	public void update(String table, HashMap<String,String>attributes,String condition, String info);
	
	
	
	/**
	 * @doc TRUNCATE-Befehl in mysql == DELETE (DROP könnte unter Umstände
	 *      später dazu kommen)
	 * @param table
	 * @param condition
	 * @param info
	 */
	public void delete(String table,String condition, String info);
	
	
	/**
	 * 
	 * @param table
	 * @param fields
	 * @param condition
	 * @param others
	 * @return
	 */
	public ResultSet select(String table, String fields, String condition,String others);
	
	/**
	 * @see ResultSet select(String table, String fields, String condition,String others);
	 * @param table
	 * @param fields
	 * @param condition
	 * @return
	 */
	public ResultSet select(String table, String fields, String condition);
	// CRUD-Befehle---END

	// Zusätzliches---START	
	/**
	 * Mit der Hilfe der Hilfe einer bekomme ich einen Text Basiert auf dem
	 *      select-Befehl: SELECT name_ FROM text WHERE id = id
	 * @param id: Id der gesuchten Zeile in der Table 'Text'
	 * @return
	 */
	public String text(int id);
	
	/**
	 * @deprecated
	 * @param message
	 * @return
	 */
	public String text(String message);
	// Zusätzliches---END
}
