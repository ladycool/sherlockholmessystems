package service;

import java.util.HashMap;

public class LoginService {
	HashMap<String, String> users = new HashMap<>();
	
	public LoginService(){
		users.put("a", "john Doe");
		users.put("s", "john Dorett");
		users.put("d", "janette Doe");
	}
	
	public boolean authenticate(String userId, String password){
		password = password.trim();
		if(password.isEmpty())
			return false;
		else
			return true;
	}
	
	public String getUsername(String userId){
		return this.users.get(userId);
	}
	
}
