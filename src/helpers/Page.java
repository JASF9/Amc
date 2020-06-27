package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import controllers.Product;

public class Page {
	
	public String sProducts(ResultSet rs) {
		String html = "";
		try {
			
			if(rs.next()) {
				do {
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
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"/></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET' /><input type='hidden' id='ind' name='ind' value='redirect' /><input type='hidden' id='idP' name='idP' value="+id+" /><input type='submit' value='See Product Page'/></form><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE' /><input type='hidden' id='idP' name='idP' value="+id+" /><input type='submit' value='Delete Product'/></form></div></div></div>";
					}
					else {
						html = html + "<div class='row'><div class='product'><div class='column side'><h1>No Image Available</h1></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET'/><input type='hidden' id='ind' name='ind' value='redirect'/><input type='hidden' id='idP' name='idP' value="+id+" /><input type='submit' value='See Product Page'/></form><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Delete Product'/></form></div></div></div>";
					}
				} while(rs.next());
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
			
			if(rs.next()) {
				do {
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
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<a href ='updateP.html'>Update Info</a><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Delete Product'/></form></div></div></div>";
					}
					else {
						html = html + "<div class='row'><div class='product'><div class='column side'><h1>No Image Available</h1></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						//html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='ind' name='ind' value='yes'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Update info'></form></div></div></div>";
						html = html + "<a href ='updateP.html'>Update Info</a><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Delete Product'/></form></div></div></div>";
					}

				}while(rs.next());
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
		html = html + "<p>Comments</p><br/>";
		try {
			if(rs.next()) {
				do {
					//String idP = rs.getString("id_Product");
					String username = rs.getString("username");
					String content = rs.getString("content");
					String idC = rs.getString("id_Comment");
					html = html + "<div class='comment'><p>"+username+" says:</p><br/><p>"+content+"</p><br/>";
					if(request.getParameter("username").equals(username)) { 
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Comentary'><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='idC' name='idC' value="+idC+"/><input type='textarea' name='newcom' rows='4' cols='25'></textarea><br/><input type='submit' value='Upload Comment'/></form>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Comentary'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idC' name='idC' value="+idC+"/><input type='submit' value='Delete'/></form>";
					}
					html = html + "</div>";

				} while(rs.next());
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
	
	public String uProducts(ResultSet rs) {
		String html = "";
		try {
			
			if(rs.next()) {
				do {
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
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"/></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET'/><input type='hidden' id='ind' name='ind' value='redirect'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='See Product Page'/></form><br/>";
					}
					else {
						html = html + "<div class='row'><div class='product'><div class='column side'><h1>No Image Available</h1></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='GET'/><input type='hidden' id='ind' name='ind' value='redirect'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='See Product Page'/></form><br/>";
						
					}
				} while(rs.next());
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
	
	public String indUProduct(ResultSet rs) {
		String html = "";
		try {
			if(rs.next()) {
				do {
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
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						html = html + "<form method='post' action='http://localhost:8080/Amachon2/Cart'><input type='hidden' id='rm' name='rm' value='POST'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='price' name='price' value="+price+"/>";
						html = html + "<input type='range' id='stock' name='stock' max="+stock+"/><input type='submit' value='Add to Cart'/></form></div></div></div>";
					
					}
					else {
						html = html + "<div class='row'><div class='product'><div class='column side'><h1>No Image Available</h1></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description"+comment+"</p><br/><p>Likes:"+likes+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>In stock:"+stock+"</p><br/>";
						//html = html + "<form method='get' action='http://localhost:8080/Amachon2/Inventory'><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='ind' name='ind' value='yes'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='submit' value='Update info'></form></div></div></div>";
						html = html + "<form method='post' action='http://localhost:8080/Amachon2/Cart'><input type='hidden' id='rm' name='rm' value='POST'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='price' name='price' value="+price+"/>";
						html = html + "<input type='range' id='stock' name='stock' max="+stock+"/><input type='submit' value='Add to Cart'/></form></div></div></div>";
					}

				}while(rs.next());
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
	
	public String showCart(ResultSet rs) {
		String html = "";
		try {
			if(rs.next()) {
				do {
					String username = rs.getString("username");
					String id = rs.getString("id_Product");
					int units = rs.getInt("units");
					float price = rs.getFloat("price");
					
					Product p = new Product();
					String name ="";
					String comment ="";
					String category="";
					String image="";
					ResultSet prs = p.searchProductID(id);
					if(prs.next()) {
						name = prs.getString("name");
						comment = prs.getString("comment");
						category = prs.getString("category");
						image = prs.getString("image");	
					}
					//PUT DELETE ALL BUTTON ON VIEW
					if(!image.isEmpty()) {
						
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"/></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>Units ordered:"+units+"</p><br/>";
						//delete and change units
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Cart'><input type='range' id='units' name='units' min='1' max="+units+"><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='username' name='username' value="+username+"/><input type='submit' value='Change units in order'/></form><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Cart'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='username' name='username' value="+username+"/><input type='submit' value='Delete Order'/></form></div></div></div>";
						
					}
					else {
						
						html = html + "<div class='row'><div class='product'><div class='column side'><img src="+image+"/></div>";
						html = html + "<div class='column middle'><h1>"+name+"</h1><p>Category:"+category+"</p><br/><p>Description:"+comment+"</p><br/></div>";
						html = html + "<div class='column side'><h1>"+price+"</h1><p>Units ordered:"+units+"</p><br/>";
						//delete and change units
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Cart'><input type='range' id='units' name='units' min='1' max="+units+"><input type='hidden' id='rm' name='rm' value='PUT'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='username' name='username' value="+username+"/><input type='submit' value='Change units in order'/></form><br/>";
						html = html + "<form method='get' action='http://localhost:8080/Amachon2/Cart'><input type='hidden' id='rm' name='rm' value='DELETE'/><input type='hidden' id='idP' name='idP' value="+id+"/><input type='hidden' id='username' name='username' value="+username+"/><input type='submit' value='Delete Order'/></form></div></div></div>";
						
					}
				} while(rs.next());
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
