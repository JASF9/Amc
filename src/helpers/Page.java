package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class Page {
	
	public String sProducts(ResultSet rs) {
		String html = "";
		try {
			ResultSet copy = rs;
			if(copy.next()) {
				while(rs.next()) {
					String id = rs.getString("id_Product");
					String name = rs.getString("name");
					String comment = rs.getString("comment");
					String category = rs.getString("category");
					int stock = rs.getInt("stock");
					float price = rs.getFloat("price");
					int likes = rs.getInt("likes");
					String image = rs.getString("image");
					if(!image.isEmpty()) {
						//<a href='updateP.html'>Update Product Info</a>
						html.concat("<div class='product'><div class='row'><div class='column side'><img src='"+image+"'/></div>");
						html.concat("<div class='column middle'><h1>'"+name+"'</h1><p>Category:'"+category+"'</p><br/><p>Description'"+comment+"'</p><br/><p>Likes:'"+likes+"'</p><br/></div>");
						html.concat("<div class='column side'><h1>'"+price+"'</h1><p>In stock:'"+stock+"'</p><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET'/><input type='hidden' id='ind' name='ind' value='yes'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='See Product Page'/></form><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='Delete Product'/></form></div></div></div>");
					}
					else {
						html.concat("<div class='product'><div class='row'><div class='column side'><h1>No Image Available</h1></div>");
						html.concat("<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>");
						html.concat("<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET'/><input type='hidden' id='ind' name='ind' value='yes'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='See Product Page'/></form><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='Delete Product'/></form></div></div></div>");
					}
				}
				return html;
			}
			else {
				return "No results";
			}
		}
		catch(NullPointerException|SQLException e) {
			return "No results";
		}
	}
	
	public String indSProduct(ResultSet rs) {
		String html = "";
		try {
			ResultSet copy = rs;
			if(copy.next()) {
				while(rs.next()) {
					String id = rs.getString("id_Product");
					String name = rs.getString("name");
					String comment = rs.getString("comment");
					String category = rs.getString("category");
					int stock = rs.getInt("stock");
					float price = rs.getFloat("price");
					int likes = rs.getInt("likes");
					String image = rs.getString("image");
					if(!image.isEmpty()) {
						//<a href='updateP.html'>Update Product Info</a>
						html.concat("<div class='product'><div class='row'><div class='column side'><img src='"+image+"'></div>");
						html.concat("<div class='column middle'><h1>'"+name+"'</h1><p>Category:'"+category+"'</p><br/><p>Description'"+comment+"'</p><br/><p>Likes:'"+likes+"'</p><br/></div>");
						html.concat("<div class='column side'><h1>'"+price+"'</h1><p>In stock:'"+stock+"'</p><br/>");
						html.concat("<a href ='updateP.html'>Update Info</a><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='Delete Product'/></form></div></div></div>");
					}
					else {
						html.concat("<div class='product'><div class='row'><div class='column side'><h1>No Image Available</h1></div>");
						html.concat("<div class='column middle'><h1>'"+name+"'</h1><p>Category:'"+category+"'</p><br/><p>Description'"+comment+"'</p><br/><p>Likes:'"+likes+"'</p><br/></div>");
						html.concat("<div class='column side'><h1>'"+price+"'</h1><p>In stock:'"+stock+"'</p><br/>");
						//html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='ind' name='ind' value='yes'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Update info'></form></div></div></div>");
						html.concat("<a href ='updateP.html'>Update Info</a><br/>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value='"+id+"'/><input type='submit' value='Delete Product'/></form></div></div></div>");
					}

				}
				return html;
			}
			else {
				return "No results";
			}
		}
		catch(NullPointerException|SQLException e) {
			return "No results";
		}
	}
	
	public String allCom(ResultSet rs,HttpServletRequest request) {
		String html = "";
		html.concat("<p>Comments</p><br/>");
		try {
			ResultSet copy = rs;
			if(copy.next()) {
				while(rs.next()) {
					String idP = rs.getString("id_Product");
					String username = rs.getString("username");
					String content = rs.getString("content");
					String idC = rs.getString("id_Comment");
					html.concat("<div class='comment'><p>"+username+" says:</p><br/><p>"+content+"</p><br/>");
					if(request.getParameter("username").equals(username)) { 
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Comentary'><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='idC' name='idC' value='"+idC+"'/><input type='textarea' name='newcom' rows='4' cols='25'></textarea><br/><input type='submit' value='Upload Comment'/></form>");
						html.concat("<form method='get' action='http://localhost:8080/Amachon2/Comentary'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idC' name='idC' value='"+idC+"'/><input type='submit' value='Delete'/></form>");
					}
					html.concat("</div>");

				}
				return html;
			}
			else {
				return "No results";
			}
		}
		catch(NullPointerException|SQLException e) {
			return "No results";
		}
	}
}
