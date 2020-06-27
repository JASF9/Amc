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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;

import controllers.Auth;
import controllers.Comment;
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
    	
    	if(request.getMethod().equals("POST")) {
    		if(request.getParameter("rm").equals("GET"))
    			doGet(request,response);	
    		else
    			doPost(request,response);
    	}
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
			if(admin) {
				if(request.getParameter("ind").equals("no")) {
					ResultSet rs = p.searchProductU(username);
					Page page = new Page();
					String s = page.sProducts(rs);
					response.setContentType("text/html");
					PrintWriter writer= response.getWriter();
					writer.write(s);
				}
				else {
					if(request.getParameter("ind").equals("redirect")) {
						if(a.isSessionActive(request)) {
							System.out.println("reached redirect");
							session.setAttribute("idP", request.getParameter("idP"));
							response.sendRedirect("public/views/product.html");
							
						}
						else {
							response.sendRedirect("public/views/userResponses/expired.html");
						}
					}
					else {
						String idP = (String) session.getAttribute("idP");
						ResultSet rs = p.searchProductID(idP);
						Page page = new Page();
						String s = page.indSProduct(rs);
						Comment c = new Comment();
						ResultSet rsc = c.searchCommentP(idP);
						String sc = page.allCom(rsc, request);
						s = s+sc;
						PrintWriter writer = response.getWriter();
						writer.write(s);
					}
				}
			}
			else {
				
				if(request.getParameter("ind").equals("no")) {
					if(request.getParameter("ready").equals("yes")){
						String ss = (String) session.getAttribute("category");
						ResultSet rs = p.searchProductCat(ss);
						Page page = new Page();
						String s = page.uProducts(rs);
						PrintWriter writer = response.getWriter();
						writer.write(s);
					}
					else {
						String category = request.getParameter("category");
						session.setAttribute("category", category);
						response.sendRedirect("public/views/userProduct.html");
					}
				}
				else {
					if(request.getParameter("ind").equals("redirect")) {
						if(a.isSessionActive(request)) {
							session.setAttribute("idP", request.getParameter("idP"));
							response.sendRedirect("public/views/product.html");
							
						}
						else {
							response.sendRedirect("public/views/userResponses/expired.html");
						}
					}
					else {
						String idP = (String) session.getAttribute("idP");
						ResultSet rs = p.searchProductID(idP);
						Page page = new Page();
						String s = page.indUProduct(rs);
						PrintWriter writer = response.getWriter();
						writer.write(s);
					}
				}
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
			
			String path = "C:\\Users\\Jose Sanchez\\eclipse-workspace\\Amachon2\\WebContent\\public\\assets\\images\\"+username+"\\";
			String mime = file.getHeader("content-type");
			String[] s = mime.split("/");
			mime=s[1];
			File upload = new File(path);
			upload.mkdirs();
			String image = "";
		    OutputStream out = null;
		    InputStream in = null;

			try {
				String npath = path+name+"."+mime;
				File nFile = new File(npath);
				out = new FileOutputStream(nFile);
		        in = file.getInputStream();
		        image = npath;
		        IOUtils.copyLarge(in, out);
			        
			}catch (FileNotFoundException fne) {
			        System.out.println("File not found.");
			} finally {
		        if (out != null) {
		        	out.flush();
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
			String username = "";
			String id_Product = ""; 
			String name = "";
			String comment = "";
			String category = "";
			String image = "";
			int stock = 0;
			float price = 0;
			Part file = null;
			try {
			username =  (String) session.getAttribute("username");
			id_Product = (String) session.getAttribute("idP");
			name = request.getParameter("name");
			comment = request.getParameter("comment");	
			stock = Integer.parseInt(request.getParameter("stock"));
			category = request.getParameter("category");
			price = Float.parseFloat(request.getParameter("price"));
			}catch(NumberFormatException|NullPointerException e) {
				
			}
			String content = request.getContentType();
			try {
			if(content.equals("multipart/form-data")||content.equals("multipart/mixed stream")) {
			file = request.getPart("image");
			
			String path = "C:\\Users\\Jose Sanchez\\eclipse-workspace\\Amachon2\\WebContent\\public\\assets\\images\\"+username+"\\";
			String mime = file.getHeader("content-type");
			String[] s = mime.split("/");
			mime=s[1];
			File upload = new File(path);
			upload.mkdirs();
			
		    OutputStream out = null;
		    InputStream in = null;

			try {
				String npath = path+name+"."+mime;
				File nFile = new File(npath);
				out = new FileOutputStream(nFile);
		        in = file.getInputStream();
		        image = npath;
		        IOUtils.copyLarge(in, out);
			        
			}catch (FileNotFoundException fne) {
			        System.out.println("File not found.");
			} finally {
		        if (out != null) {
		        	out.flush();
		            out.close();
		        }
		        if (in != null) {
			        in.close();
		        }
		        
		    }
			}
			}catch(NullPointerException t) {
				
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
