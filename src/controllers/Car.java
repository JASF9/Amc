package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DBC;

public class Car {
	
private DBC dbc;
	
	public Car() {
		dbc = new DBC();
	}
	
	public Boolean createCart(int units, float price, String id_Product, String username) {
		if(id_Product.isEmpty()||username.isEmpty()) {
			System.out.println("Error.");
			return false;
		}
		else {
			Product p = new Product();
			try {
				p.updateStock(units, id_Product);
			} catch (SQLException e1) {
				System.out.println("Stock not updated.");;
			}
			dbc.addParameter(units);
			dbc.addParameter(price);
			dbc.addParameter(id_Product);
			dbc.addParameter(username);
			dbc.prepare(dbc.getSentence("insertCart"));
			try {
				dbc.exQuery();
				return true;
			}catch(SQLException e) {
				System.out.println("Unexpected DB error. Product not added to cart.");
				return false;
			}
		}
	}
	
	public Boolean updateCart(int units , String id_Product, String username) {
		if(id_Product.isEmpty()||username.isEmpty()||units==0) {
			System.out.println("Cart error.");
			return false;
		}
		else {
			dbc.addParameter(units);
			dbc.addParameter(id_Product);
			dbc.addParameter(username);
			dbc.prepare(dbc.getSentence("updateCart"));
			try {
				dbc.exQuery();
				return true;
			}catch(SQLException e) {
				System.out.println("Unexpected DB error. Comment not updated.");
				return false;
			}
		}
	}
	
	public Boolean deleteCart(String id_Product, String username) {
		dbc.addParameter(id_Product);
		dbc.addParameter(username);
		dbc.prepare(dbc.getSentence("deleteCart"));
		try {
			dbc.exQuery();
			return true;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Product not deleted from cart.");
			return false;
		}
	}
	
	public Boolean deleteWholeCart( String username) {
		dbc.addParameter(username);
		dbc.prepare(dbc.getSentence("deleteWholeCart"));
		try {
			dbc.exQuery();
			return true;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Cart not emptied.");
			return false;
		}
	}
	
	public ResultSet getCart(String username) {
		dbc.addParameter(username);
		dbc.prepare(dbc.getSentence("getCart"));
		try {
			ResultSet rs = dbc.exQuery();
			return rs;
		}catch(SQLException e) {
			System.out.println("Unexpected DB error. Cart not loaded.");
			return null;
		}
	}
}
