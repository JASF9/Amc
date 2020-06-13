package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import helpers.Hash;

public class Auth {

	public HttpSession authenticate(String username, String password, HttpServletRequest request) {
		
		if(isSessionActive(request)) {
			System.out.println("There's already an active session.");
			return request.getSession(false);
		}
		else {
			String epass = "";
			
			Hash hash = new Hash();
			if(!password.equals("")){
			epass = hash.encrypt(password);
			}
			
			User user = new User();
			 
			if(user.logIn(username, epass)) {
				System.out.println("Successfull log.");
				return request.getSession();
			}
			else {
				System.out.println("Could not log.");
				return null;
			}
		}
	}
	
	public Boolean getAdminStatus(String username) {
		User user = new User();
		return user.getAdminStatus(username);
		
	}
	
	public Boolean isSessionActive(HttpServletRequest request) {
		if(request.getSession(false)!=null) {
			System.out.println("Valid session.");
			return true;
		}
		else {
			System.out.println("Inactive session.");
			return false;
		}
	}
}
