package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DBC;

public class Comment {
	
	private DBC dbc;
	
	public Comment() {
		dbc = new DBC();
	}
	
	public Boolean createComment(String content, String username, String id_Product) {
		if(content.isEmpty()) {
			System.out.println("No body.");
			return false;
		}
		else {
			dbc.addParameter(content);
			dbc.addParameter(username);
			dbc.addParameter(id_Product);
			dbc.prepare(dbc.getSentence("insertC"));
			try {
				dbc.exQuery();
				return true;
			}catch(SQLException e) {
				System.out.println("Unexpected DB error. Comment not posted.");
				return false;
			}
		}
	}
	
	public Boolean updateComment (String content, String id_Comment) {
		if(content.isEmpty()) {
			System.out.println("No body.");
			return false;
		}
		else {
			dbc.addParameter(content);
			dbc.addParameter(id_Comment);
			dbc.prepare(dbc.getSentence("updateC"));
			try {
				dbc.exQuery();
				return true;
			}catch(SQLException e) {
				System.out.println("Unexpected DB error. Comment not updated.");
				return false;
			}
		}
	}
	
	public Boolean deleteComment(String id_Comment) {
		dbc.addParameter(id_Comment);
		dbc.prepare(dbc.getSentence("deleteC"));
		try {
			dbc.exQuery();
			return true;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Comment not deleted.");
			return false;
		}
	}
	
	public ResultSet searchCommentP(String id_Product) {
		dbc.addParameter(id_Product);
		dbc.prepare(dbc.getSentence("searchCP"));
		try {
			ResultSet rs = dbc.exQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Comments not loaded.");
			return null;
		}
	}
}
