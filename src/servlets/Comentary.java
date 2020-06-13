package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Auth;
import controllers.Comment;
import helpers.Page;

/**
 * Servlet implementation class Comentary
 */
@WebServlet("/Comentary")
public class Comentary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comentary() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Auth a = new Auth();
		if(a.isSessionActive(request)) {
			String id = request.getParameter("idP");
			Comment c = new Comment();
			ResultSet rs = c.searchCommentP(id);
			Page page = new Page();
			String s = page.allCom(rs, request);
			PrintWriter writer= response.getWriter();
			writer.println(s);
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
			String content = request.getParameter("content");
			String username = request.getParameter("username");
			String id = request.getParameter("idP");
			Comment c = new Comment();
			if(c.createComment(content, username, id)) {
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
			String content = request.getParameter("newcom");
			String idC = request.getParameter("idC");
			Comment c = new Comment();
			if(c.updateComment(content, idC)) {
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
			String idC = request.getParameter("idC");
			Comment c = new Comment();
			if(c.deleteComment(idC)) {
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
