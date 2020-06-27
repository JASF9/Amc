package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Auth;
import controllers.Car;
import helpers.Page;

/**
 * Servlet implementation class Cart
 */
@WebServlet("/Cart")
@MultipartConfig(
        fileSizeThreshold   = 1024 * 1024 * 1,  // 1 MB
        maxFileSize         = 1024 * 1024 * 10, // 10 MB
        maxRequestSize      = 1024 * 1024 * 15, // 15 MB
        location            = "C:/tmp"
)
public class Cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Cart() {
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
			
			Car c = new Car();
			ResultSet rs = c.getCart(username);
			Page page = new Page();
			String s = page.showCart(rs);
			PrintWriter writer= response.getWriter();
			writer.write(s);
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
			int units = Integer.parseInt(request.getParameter("stock"));
			float price = Float.parseFloat(request.getParameter("price"));
			String id = request.getParameter("idP");
			String username = (String) session.getAttribute("username");
			
			Car c = new Car();
			if(c.createCart(units,price, username, id)) {
				response.sendRedirect(request.getRequestURI());
			}
			else {
				response.sendRedirect("public/views/userResponses/dberror.html");
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
			int units = Integer.parseInt(request.getParameter("units"));
			String idP = request.getParameter("idP");
			String username = request.getParameter("username");
			Car c = new Car();
			if(c.updateCart(units, idP, username)) {
				response.sendRedirect(request.getRequestURI());
			}
			else {
				response.sendRedirect("public/views/userResponses/dberror.html");
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
			String idP = request.getParameter("idP");
			String username = request.getParameter("username");
			Car c = new Car();
			if(c.deleteCart(idP, username)) {
				response.sendRedirect(request.getRequestURI());
			}
			else {
				response.sendRedirect("public/views/userResponses/dberror.html");
			}
		}
		else {
			response.sendRedirect("public/views/userResponses/expired.html");
		}
	}

}
