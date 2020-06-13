package servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import controllers.Auth;
import controllers.Product;
import helpers.Page;

/**
 * Servlet implementation class Inventory
 */
@WebServlet("/Inventory")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15, // 15 MB
        location            = "C:/tmp"
)
public class Inventory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Inventory() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	if(request.getMethod().equals("POST")) 
    		doPost(request,response);
    	if(request.getMethod().equals("GET")) { 
    		if(request.getParameter("rm").equals("GET"))
    			doGet(request,response);
    		if(request.getParameter("rm").equals("PUT"))
    			doPut(request,response);
    		if(request.getParameter("rm").equals("DELETE")) 
    			doDelete(request,response);
   		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("username");
			Boolean admin = (Boolean) session.getAttribute("admin");
			Product p = new Product();
			String category = request.getParameter("category");
			if(admin) {
				if(request.getParameter("ind").equals("no")) {
					ResultSet rs = p.searchProductU(username);
					Page page = new Page();
					String s = page.sProducts(rs);
					response.setContentType("text/html");
					PrintWriter writer= response.getWriter();
					writer.println(s);
				}
				else {
					session.setAttribute("idP", request.getParameter("idP"));
					ResultSet rs = p.searchProductID(request.getParameter("idP"));
					Page page = new Page();
					String s = page.indSProduct(rs);
					PrintWriter writer = response.getWriter();
					writer.println(s);
				}
			}
			else {
				//list for normal users
			}
		}
		else {
			response.sendRedirect("public/views/userResponses/expired.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			
			HttpSession session = request.getSession(false);
			String username = (String) session.getAttribute("username");
			String name = request.getParameter("name");
			String comment = request.getParameter("comment");	
			int stock = Integer.parseInt(request.getParameter("stock"));
			String category = request.getParameter("category");
			float price = Float.parseFloat(request.getParameter("price"));
			
			Part file = request.getPart("image");
			String filename = file.getSubmittedFileName();
			String path = "public/assets/images/"+username;
			String mime = file.getHeader("content-type");
			String[] s = mime.split("/");
			mime=s[1];
			File upload = new File(path);
			upload.mkdirs();
			String image = "";
		    OutputStream out = null;
		    InputStream in = null;

			try {
		        out = new FileOutputStream(new File(path + File.separator+ filename+"."+mime));
		        in = file.getInputStream();
		        image = path + File.separator+ filename+"."+mime;
			    int read = 0;
			    byte[] bytes = new byte[1024];

			    while ((read = in.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
			        
			}catch (FileNotFoundException fne) {
			        System.out.println("u tried.");
			} finally {
		        if (out != null) {
		            out.close();
		        }
		        if (in != null) {
			        in.close();
		        }
		        
		    }
			
			Product p = new Product();
			if(p.createProduct(username, name, comment, stock, category, price,image)) {
				response.sendRedirect("public/views/loggedSHome.html");
			}
			else {
				response.sendRedirect("public/views/userResponses/pro/failure.html");
			}
			
		}
		else {
			response.sendRedirect("public/views/userResponses/expired.html");
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			HttpSession session = request.getSession(false);
			String username =  (String) session.getAttribute("username");
			String id_Product = (String) session.getAttribute("idP");
			String name = request.getParameter("name");
			String comment = request.getParameter("comment");	
			int stock = Integer.parseInt(request.getParameter("stock"));
			String category = request.getParameter("category");
			float price = Float.parseFloat(request.getParameter("price"));
			
			Part file = request.getPart("image");
			String filename = file.getSubmittedFileName();
			String path = "public/assets/images/"+username;
			String mime = file.getHeader("content-type");
			String[] s = mime.split("/");
			mime=s[1];
			File upload = new File(path);
			upload.mkdirs();
			String image = "";
		    OutputStream out = null;
		    InputStream in = null;

			try {
		        out = new FileOutputStream(new File(path + File.separator+ filename+"."+mime));
		        in = file.getInputStream();
		        image = path + File.separator+ filename+"."+mime;
			    int read = 0;
			    byte[] bytes = new byte[1024];

			    while ((read = in.read(bytes)) != -1) {
		            out.write(bytes, 0, read);
		        }
			        
			}catch (FileNotFoundException fne) {
			        System.out.println("u tried.");
			} finally {
		        if (out != null) {
		            out.close();
		        }
		        if (in != null) {
			        in.close();
		        }
		    }
			
			Product p = new Product();
			if(p.updateProduct(name, comment, stock, category, price,id_Product)) {
				p.addImg(image, id_Product);
				response.sendRedirect("public/views/userPs.html");
			}
			else {
				response.sendRedirect("public/views/userResponses/pro/failure.html");
			}
		}
		else {
			response.sendRedirect("public/views/userResponses/expired.html");
		}
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			String id = request.getParameter("idP");
			Product p = new Product();
			if(p.deleteProduct(id)) {
				response.sendRedirect("public/views/userPs.html");
			}
			else {
				response.sendRedirect("public/views/loggedSHome.html");
			}
		}
		else {
			response.sendRedirect("public/views/userResponses/expired.html");
		}
	}

}
