package SERVICE;

import java.util.ArrayList;
import java.util.HashMap;



public class User{
	private HashMap<String, Object> attributes;
	private ArrayList<String> restrictedSETAttr;
	private ArrayList<String> restrictedGETAttr;
	private static User singelton;
	
	
	private User(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @doc Diese Methode sorgt daf�r, dass der Benutzer zu jeder Zeit eindeutig ist. 
	 * @param attributes
	 * @return ein Singelton
	 */
	public static User getInstance(HashMap<String, Object> attributes) {
		//SETTER-START
		if(User.singelton == null){
			User.singelton = new User(attributes);
		}
		//SETTER-END
		return User.singelton;
	}
	
	/**
	 * @doc selbsterkl�rend
	 * @param key
	 * @param value
	 */
	public void setattr(String key,Object value){
		if(!this.restrictedSETAttr.contains(key)){
			this.attributes.put(key, value);
		}
	}
	
	/**
	 * @doc selbsterkl�rend
	 * @param key
	 * @return
	 */
	public Object getattr(String key){
		if(!this.restrictedGETAttr.contains(key)){
			return this.attributes.get(key);
		}
		return null;//Fehlermeldung
	}
}
