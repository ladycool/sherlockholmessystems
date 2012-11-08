package SERVICE;

import java.sql.Connection;
import java.sql.DriverManager;

import CONTROLLER.Controller;
import MODEL.Database;


public class Myadmin implements Database {
	
	
	private Connection connect;
	
	/*
	 * PRIVATE
	 */
	private static Myadmin _db = new Myadmin();
	private Myadmin(){
		this.connect = dbconnect();
	}
	
	/**
	 * @doc Erzeugt einen Singelton
	 * @return
	 */
	public static Myadmin getInstance(){
		return Myadmin._db;
	}
	
	
	
	
	

	//Ovderride	
	public String text(int id) {
		return String.format("%d", id);
	}

	public String text(String tx) {
		return tx;
	}

	
	
	//private Methods
	/**
	 * Establishing an new connection to either the local or remote database
	 * -> Outsourcing of code to prevent redundancy
	 * @return
	 */
	private Connection dbconnect() {
		Connection toreturn = null;
		try{
			// Load the MySQL JDBC driver
			Class.forName("com.mysql.jdbc.Driver") ;//"oracle.jdbc.driver.OracleDriver"
		    //System.out.println("MySQL JDBC driver loaded ok.");
			
		    // Setup the connection with the DB
		    //"jdbc:mysql://https://jonathan.sv.hs-mannheim.de/phpMyAdmin/:3306/database","username","password"
		    toreturn = DriverManager.getConnection(Controller.shsconfig.url_db,Controller.shsconfig.username,Controller.shsconfig.password);	
		    
		}catch (Exception e){
			//gui.triggernotice(e);
		}
		return toreturn;
	}


	@Override
	public void insert(String table, String fields, String values) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void insert(String table, String fields, String values, String info) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String[] select(String table, String fields, String condition) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(String table, String fields, String values,
			String condition) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void update(String table, String fields, String values,
			String condition, String info) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(String table, String fields, String values,
			String condition, String info) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String select1(String table, String fields, String condition) {
		// TODO Auto-generated method stub
		return null;
	}
}
