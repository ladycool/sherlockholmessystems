package SERVICE;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import CONTROLLER.Controller;
import MODEL.*;


/**
 * 
 * @author Shazem (Patrick)
 *
 */
public class Myadmin implements Database {
	private Connection connect;
	// predefinend variables
	//private Statement statement = null;
	//private PreparedStatement preparedStatement = null;
	private static Myadmin db = null;

	
	//SINGELTON-START
	private Myadmin() {
		this.connect = dbconnect();
	}

	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static Myadmin getInstance(){
		//SETTER-START
		if(db == null){
			db = new Myadmin();
		}
		//SETTER-END
		return db;
	}
	//SINGELTON-END
	
	
	// private Methods
	/**
	 * Establishing an new connection to either the local or remote database ->
	 * Outsourcing of code to prevent redundancy
	 * 
	 * @return
	 */
	private Connection dbconnect() {
		Connection toreturn = null;
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.jdbc.Driver");// "oracle.jdbc.driver.OracleDriver"
			System.out.println("MySQL JDBC driver loaded ok.");

			// Setup the connection with the DB
			// "jdbc:mysql://https://jonathan.sv.hs-mannheim.de/phpMyAdmin/:3306/database","username","password"
			
				toreturn = DriverManager.getConnection(Controller.shsconfig.url_db,Controller.shsconfig.dbusername,Controller.shsconfig.dbpassword);
				System.out.println("The biding has been proceeded");
		} catch (SQLException | ClassNotFoundException e) {
				//2012.11.10
				//Ein Gateway muss hier implementiert werden.
				Controller.shsgui.triggernotice(e);
			}
		
		return toreturn;
	}


	@Override
	public void insert(String table, String fields, String values, String info){
		try {
			String query = "INSERT INTO "+ table;
			if (!fields.equals("")) {
				query += " (" + fields + ") ";
			}
			query += "VALUES(" + values + ")";
			
			connect.createStatement().executeQuery(query);
			
			if(info.isEmpty()){info =this.text(344,"");}
			Controller.shsgui.triggernotice(info);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}

	@Override
	public void insert(String table,String[] fields,String[] values,String info){
		String implodedfields="",implodedvalues="",comma="";
		for (int i = 0; i < values.length; i++) {
			implodedfields = comma+fields[i];
			implodedfields = comma+values[i];
			comma=",";
		}
		insert(table,implodedfields,implodedvalues,info);
	}
	
	@Override
	public void insert(String table,HashMap<String,String>attributes,String info){
		String[] fields = (String[]) attributes.keySet().toArray();
		String[] values = (String[]) attributes.values().toArray();
		insert(table, fields, values, info);
	}
	
	
	
	@Override
	public void update(String table, String[] fields, String[] values,String condition,String info){
		try {
			String query = "UPDATE "+ table + " SET ";
			String comma="";
			for (int i = 0; i < fields.length; i++) {
				query += comma+fields[i]+"='"+values[i]+"'";
				comma = ",";
			}
			if(!condition.isEmpty()){
				query += " WHERE ("+condition+")";
			}
			connect.createStatement().executeQuery(query);
			
			if(info.equals("")){info = this.text(655,"");}
			Controller.shsgui.triggernotice(info);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(info);
		}
			
	}

	@Override
	public void update(String table, HashMap<String,String>attributes,String condition, String info){
		String[] fields = (String[]) attributes.keySet().toArray();
		String[] values = (String[]) attributes.values().toArray();
		update(table, fields, values, condition, info);
	}

	
	
	@Override
	public void delete(String table,String condition, String info){
		try {
			String query = "DELETE FROM "+table;
			if(!condition.isEmpty()){
				query += " WHERE("+condition+")";
			}
			connect.createStatement().executeQuery(query);
			
			if(info.equals("")){info = this.text(4444,"de");}
			Controller.shsgui.triggernotice(info);
			
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}

	
	
	@Override
	public ResultSet select(String table, String fields, String condition,String others) {
		ResultSet result = null;
		try {
			String query = "SELECT "+fields+" FROM "+table;
			
			if(!condition.isEmpty()){
				query += " WHERE("+condition+")";
			}

			if (!others.isEmpty()) {
				query += " "+others;
			}

			result = connect.createStatement().executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	

	@Override
	public String text(int id,String lang){
		String toreturn="";
		try {
			if(lang.isEmpty()){lang = "de";}
			
			String field = "text_"+lang;
			ResultSet result = this.select("text", field,"id="+id,"");
			toreturn = result.getString(field);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	
	@Override
	public String text(int id){
		return text(""+id); 
	}
	
	@Override
	public String text(String message){
		return message;
	}
	
	
	public void close() {
		try {
			if(connect != null){connect.close();}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}
}
