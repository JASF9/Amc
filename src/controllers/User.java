package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import helpers.DBC;

public class User {
	private DBC dbc;
	private String uname, upass;
	
	public User() {
		dbc = new DBC();
	}
	
	public Boolean createUser(String username, String id, String name,String admin, String password, String telf, String email, String direction) {
		if(username.isEmpty()||id.isEmpty()||name.isEmpty()||admin.isEmpty()||password.isEmpty()||telf.isEmpty()||email.isEmpty()||direction.isEmpty()) {
			System.out.println("Empty field. Register denied.");
			return false;
		}
		else {
			try {
				dbc.addParameter(username);
				dbc.prepare(dbc.getSentence("searchU"));
				ResultSet rs = dbc.exQuery();
				try {
					if(rs.next()) {
						System.out.println("There's already an user with this username.");
						return false;
					}
					else {
						dbc.addParameter(username);
						dbc.addParameter(id);
						dbc.addParameter(name);
						dbc.addParameter(admin);
						dbc.addParameter(password);
						dbc.addParameter(telf);
						dbc.addParameter(email);
						dbc.addParameter(direction);
						dbc.prepare(dbc.getSentence("insertU"));
						try {
							dbc.exQuery();
							return true;
						}catch(SQLException t) {
							t.printStackTrace();
							return false;
						}
					}
				}catch(NullPointerException e) {
					dbc.addParameter(username);
					dbc.addParameter(id);
					dbc.addParameter(name);
					dbc.addParameter(admin);
					dbc.addParameter(password);
					dbc.addParameter(telf);
					dbc.addParameter(email);
					dbc.addParameter(direction);
					dbc.prepare(dbc.getSentence("insertU"));
					try {
						dbc.exQuery();
						return true;
					}catch(SQLException t) {
						t.printStackTrace();
						return false;
					}
				}
			}catch(SQLException e) {
				return false;
			}
		}
	}
	
	public Boolean logIn(String username, String password) {
		if(username.equals("")||password.equals("")) {
			System.out.println("Empty field. Login denied.");
			return false;
		}
		else {
			dbc.addParameter(username);
			dbc.prepare(dbc.getSentence("searchU"));
			try {
				ResultSet rs = dbc.exQuery();
				if(rs.next()) {
					this.uname = rs.getString("username");
					this.upass = rs.getString("password");
					if(uname.equals(username)&&upass.equals(password)) {
						System.out.println("Successfuly logged in.");
						return true;
					}
					else {
						System.out.println("Incorrect username or password.");
						return false;
					}	
				}
				else {
					System.out.println("User not found.");
					return false;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public Boolean getAdminStatus(String username) {
		dbc.addParameter(username);
		dbc.prepare(dbc.getSentence("searchU"));
		try {
			ResultSet rs = dbc.exQuery();
			if(rs.next()) {
				return rs.getBoolean("admin");
			}
			else {
				System.out.println("It shouldn't reach here. Ever.");
				return null;
			}
		}catch(SQLException e) {
			System.out.println("Neither here.");
			return null;
		}
	}
	
	public int updateUser(String id, String name, String password,String telf, String email, String direction, HttpServletRequest request) {
		if(id.isEmpty()||name.isEmpty()||password.isEmpty()||telf.isEmpty()||email.isEmpty()||direction.isEmpty()) {
			System.out.println("Empty field. Update denied.");
			return 2;
		}
		else {
			Auth a = new Auth();
			if(a.isSessionActive(request)) {
				dbc.addParameter(id);
				dbc.addParameter(name);
				dbc.addParameter(password);
				dbc.addParameter(telf);
				dbc.addParameter(email);
				dbc.addParameter(direction);
				String unm = (String) request.getSession(false).getAttribute("username");
				dbc.addParameter(unm);
				dbc.prepare(dbc.getSentence("updateU"));
				try {
					dbc.exQuery();
					return 1;
				}catch(SQLException t) {
					t.printStackTrace();
					return 3;
				}
			}
			else {
				return 4;
			}
			
		}
	}
	
	public Boolean deleteU(HttpServletRequest request) {
		String unm = (String) request.getSession(false).getAttribute("username");
		dbc.addParameter(unm);
		dbc.prepare(dbc.getSentence("deleteU"));
		try {
			dbc.exQuery();
			return true;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. User not deleted.");
			return false;
		}
	}
}
