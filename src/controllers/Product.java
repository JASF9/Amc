package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DBC;

public class Product {
	private DBC dbc;
	
	public Product() {
		dbc= new DBC();
	}
	
	public Boolean createProduct(String username, String name, String comment, int stock , String category, float price, String image) {
		
		if(username.isEmpty()||name.isEmpty()||comment.isEmpty()||stock==0||category.isEmpty()||price==0) {
			System.out.println("Empty field. Product not summited.");
			return false;
		}
		else {
			try {
				dbc.addParameter(username);
				dbc.prepare(dbc.getSentence("searchU"));
				ResultSet rs = dbc.exQuery();
				if(rs.next()) {
					dbc.addParameter(username);
					dbc.addParameter(name);
					dbc.addParameter(comment);
					dbc.addParameter(stock);
					dbc.addParameter(category);
					dbc.addParameter(price);
					dbc.addParameter(image);
					dbc.prepare(dbc.getSentence("insertP"));
					try {
						dbc.exQuery();
						return true;
					}catch(SQLException t) {
						t.printStackTrace();
						return false;
					}
				}else {
					System.out.println("No seller found.");
					return false;
				}
			}catch(SQLException|NullPointerException e) {
				System.out.println("Seller not found.");
				return false;
			}
		}		
	}
	
	public Boolean updateProduct(String name, String comment, int stock , String category, float price, String id) {
		if(name.isEmpty()||comment.isEmpty()||stock==0||category.isEmpty()||price==0) {
			System.out.println("Empty field. Product info not uploaded.");
			return false;
		}
		else {
			dbc.addParameter(name);
			dbc.addParameter(comment);
			dbc.addParameter(stock);
			dbc.addParameter(category);
			dbc.addParameter(price);
			dbc.addParameter(id);
			dbc.prepare(dbc.getSentence("updateP"));
			try {
				dbc.exQuery();
				return true;				
			}catch(SQLException t) {
				t.printStackTrace();
				return false;				
			}
		}
	}
	
	public Boolean deleteProduct(String id) {
		dbc.addParameter(id);
		dbc.prepare(dbc.getSentence("deleteP"));
		try {
			dbc.exQuery();
			return true;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Product not deleted.");
			return false;
		}
	}
	
	public ResultSet searchProductU(String username) {
		dbc.addParameter(username);
		dbc.prepare(dbc.getSentence("searchUP"));
		try {
			ResultSet rs = dbc.exQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Products not found.");
			return null;
		}
	}
	
	public ResultSet searchProductID(String id) {
		dbc.addParameter(id);
		dbc.prepare(dbc.getSentence("searchUID"));
		try {
			ResultSet rs = dbc.exQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Product not found.");
			return null;
		}
	}
	
	public void addImg(String url, String id) {
		dbc.addParameter(url);
		dbc.addParameter(id);
		dbc.prepare(dbc.getSentence("addImg"));
		try {
			dbc.exQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
