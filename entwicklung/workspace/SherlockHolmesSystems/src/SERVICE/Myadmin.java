package SERVICE;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		Connection toreturn = null;//http://p2p.wrox.com/jsp-basics/65998-illegal-operation-empty-result-set.html
		try {
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.jdbc.Driver");// "oracle.jdbc.driver.OracleDriver"
			// Setup the connection with the DB
			// "jdbc:mysql://https://jonathan.sv.hs-mannheim.de/phpMyAdmin/:3306/database","username","password"
			
				toreturn = DriverManager.getConnection(Controller.shsconfig.url_db,Controller.shsconfig.dbusername,Controller.shsconfig.dbpassword);
				//System.out.println("The biding has been proceeded1");
		} catch (SQLException | ClassNotFoundException e) {
			Controller.shsgui.triggernotice(e);
			System.out.println(e.getMessage());
			/*
			try{				
				toreturn = DriverManager.getConnection("jdbc:mysql://localhost:1433/kirkelstillsystems","root","deburnatshazem");
				System.out.println("The biding has been proceeded2");
			}catch(SQLException e2){
				System.out.println(e2.getMessage());
				Controller.shsgui.triggernotice(e2);
			}		
			*/
//			try{				
//				toreturn = DriverManager.getConnection("jdbc:mysql://localhost:3306/kirkelstillsystems","root","m13");
//				System.out.println("The biding has been proceeded2");
//			}catch(SQLException e2){
//				System.out.println(e2.getMessage());
//			Controller.shsgui.triggernotice(e2);
//			}		
			
		}
		
		return toreturn;
	}

	@Override
	public void insert(String table,String values){
		this.insert(table,values,"");
	}
	
	@Override
	public void insert(String table,String values,String info){
		values = "NULL,"+values;
		this.insert(table,"",values,info);
	}
	
	@Override
	public void insert(String table, String fields, String values, String info){
		try {
			String query = "INSERT INTO "+ table+" ";
			if (!fields.equals("")) {
				query += "(" + fields + ") ";
			}
			query += "VALUES ("+values+")";
			//System.out.println(query);
			connect.createStatement().executeUpdate(query);
			
			if(info.isEmpty()){info=this.text(32);}
			Controller.shsgui.triggernotice(info);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
			e.printStackTrace();
		}
	}
	
	@Override
	public void insert(String table,String[] fields,String[] values,String info){
		String implodedfields="",comma="";
		String implodedvalues="";
		for (int i = 0; i < values.length; i++) {
			implodedfields += comma+fields[i];
			implodedvalues += comma+values[i];
			comma=",";
		}
		insert(table,implodedfields,implodedvalues,info);
	}
	
	@Override
	public void insert(String table,String[] values,String info){
		String implodedvalues="";
		String comma="";
		for (int i = 0; i < values.length; i++) {
			implodedvalues += comma+values[i];
			comma=",";
		}
		insert(table,implodedvalues,info);
	}
	
	@Override
	public void insert(String table,HashMap<String,String>attributes,String info){
		String[] fields = attributes.keySet().toArray(new String[attributes.keySet().size()]);
		String[] values = attributes.values().toArray(new String[attributes.values().size()]);
		insert(table, fields, values, info);
	}
	
	@Override
	public void insert(String table,HashMap<String,String>attributes){
		String[] fields = attributes.keySet().toArray(new String[attributes.keySet().size()]);
		String[] values = attributes.values().toArray(new String[attributes.values().size()]);
		insert(table, fields, values,"");
	}
	
	@Override
	public void insert(String table,ArrayList<String>attributes,String info){
		String[] values = (String[]) attributes.toArray();
		insert(table,values,info);
	}
	
	@Override
	public void insert(String table,ArrayList<String>attributes){
		String[] values = (String[]) attributes.toArray();
		insert(table,values,"");
	}

	
	@Override
	public void update(String table, String[] fields, String[] values,String condition){
		update(table, fields, values, condition,"");
	}

	
	@Override
	public void update(String table, String[] fields, String[] values,String condition,String info){
		try {
			String query = "UPDATE "+ table + " SET ";
			String comma="";
			for (int i = 0; i < fields.length; i++) {
				query += comma+fields[i]+"="+values[i];
				comma = ",";
			}
			if(!condition.isEmpty()){
				query += " WHERE ("+condition+")";
			}
			connect.createStatement().executeUpdate(query);
			
			if(info.equals("")){info = this.text(33);}
			Controller.shsgui.triggernotice(info);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(info);
		}
			
	}
	
	@Override
	public void update(String table, HashMap<String,String>attributes,String condition){
		String[] fields = attributes.keySet().toArray(new String[attributes.keySet().size()]);
		String[] values = attributes.values().toArray(new String[attributes.values().size()]);
		update(table, fields, values, condition,"");
	}
	
	@Override
	public void update(String table, HashMap<String,String>attributes,String condition, String info){
		String[] fields = attributes.keySet().toArray(new String[attributes.keySet().size()]);
		String[] values = attributes.values().toArray(new String[attributes.values().size()]);
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
			
			if(info.equals("")){info = this.text(34);}
			Controller.shsgui.triggernotice(info);
			
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}

	@Override
	public void delete(String table,String condition){
		this.delete(table, condition,"");
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
			//System.out.println(query);
			result = connect.createStatement().executeQuery(query);
			
		} catch (SQLException e) {
			e.getMessage();
			Controller.shsgui.triggernotice(e);
		}

		return result;

	}

	
	@Override
	public ResultSet select(String table, String fields, String condition) {
		return select(table, fields, condition,"");
	}

	@Override
	public ResultSet select(String table, String fields){
		return select(table,fields,"","");
	}
	
	///////////////Zusätzlich-Start/////////////////////////
	@Override
	public String text(int id){
		String toreturn="";
		try {
			String field = Controller.shsconfig.getlang();
			if(Controller.shsuser != null){
				if(Controller.shsuser.getattr(Controller.shsconfig.userlang) != null){
					field = (String) Controller.shsuser.getattr(Controller.shsconfig.userlang);
				}
			}

			ResultSet result = this.select("text", field,"id="+id,"");
			result.next();
			toreturn = result.getString(field);
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	@Override
	public String text(String message){
		return message;
	}
	
	@Override
	public int max(String table){
		int toreturn=0;
		try {
			ResultSet result=null;
			try{
			result = this.select(table,"MAX(id) AS id");
			}catch(Exception e){}
			if(result.first()){
				toreturn = result.getInt("id");
			}
		} catch (Exception e) {
			Controller.shsgui.triggernotice(e);
		}
		return toreturn;
	}
	
	@Override
	public void close() {
		try {
			if(connect != null){connect.close();}
		} catch (SQLException e) {
			Controller.shsgui.triggernotice(e);
		}
	}
	
///////////////Zusätzlich-End/////////////////////////
}
