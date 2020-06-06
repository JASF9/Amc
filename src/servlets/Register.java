package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Auth;
import controllers.User;
import helpers.Hash;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	// TODO Auto-generated method stub
    	if(request.getMethod().equals("POST")) {
    		doPost(request,response);
    	}
    	if(request.getMethod().equals("GET")) {
    		if(request.getParameter("rm").equals("GET"))
    			doGet(request,response);
    		else {
    			if(request.getParameter("rm").equals("PUT"))
    				doPut(request,response);
    			else {
    				if(request.getParameter("rm").equals("DELETE")) {
    					doDelete(request,response);
    				}
    			}
    		}
    	}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Auth a = new Auth();
		HttpSession session = a.authenticate(username, password, request);
		if(session!=null) {
			session.setAttribute("username", username);
			session.setAttribute("admin", a.getAdminStatus(username));
			session.setMaxInactiveInterval(120);
			response.sendRedirect("public/views/loggedHome.html");
			
		}
		else {
			response.sendRedirect("public/views/userResponses/log/failure.html");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String admin = request.getParameter("admin");	
		String password = request.getParameter("password");
		String telf = request.getParameter("telf");
		String email = request.getParameter("email");
		String direction = request.getParameter("direction");
		
		Hash hash = new Hash();
		String epass = hash.encrypt(password);
		
		if (admin==null) {
			admin = "false";
		}
		
		User u = new User();
		if(u.createUser(username, id, name, admin, epass, telf,email,direction)) {
			Auth a = new Auth();
			HttpSession session = a.authenticate(username, password, request);
			if(session!=null) {
				session.setAttribute("username", username);
				session.setAttribute("admin", a.getAdminStatus(username));
				session.setMaxInactiveInterval(120);
				response.sendRedirect("public/views/loggedHome.html");
				
			}
			else {
				response.sendRedirect("public/views/userResponses/log/failure.html");
			}
		}
		else {
			response.sendRedirect("public/views/userResponses/reg/failure.html");
		}
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");	
		String password = request.getParameter("password");
		String telf = request.getParameter("telf");
		String email = request.getParameter("email");
		String direction = request.getParameter("direction");
		
		Hash hash = new Hash();
		String epass = hash.encrypt(password);
		
		User u = new User();
		int result = u.updateUser(id, name, epass, telf, email, direction, request);
		if(result==1) {
			response.sendRedirect("public/views/loggedHome.html");
		}
		else {
			if(result==2) {
				response.sendRedirect("public/views/userResponses/emptyUP.html");
			}
			else {
				if(result==4) {
					response.sendRedirect("public/views/userResponses/expired.html");
				}
				else {
					response.sendRedirect("public/views/userResponses/dberror.html");
				}
			}
		}
	}
	
	/**
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			User user = new User();
			if(user.deleteU(request)) {
				request.getSession(false).invalidate();
				response.sendRedirect("http://localhost:8080/Amachon2");
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
