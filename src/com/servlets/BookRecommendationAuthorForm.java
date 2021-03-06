package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.beans.AuthorBean;
import com.beans.AuthorBean;
import com.dao.AuthorDao;
import com.dao.AuthorDao;

@WebServlet("/BookRecommendationAuthorForm")
public class BookRecommendationAuthorForm extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Book Recommendation Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("<link rel='stylesheet' href='dropdownlist.css'/>");
		
		out.println("</head>");
		out.println("<body>");

		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("studentusn") != null)
		{
		
			request.getRequestDispatcher("navstudent.html").include(request, response);
			out.println("<div class='container'>");
			
			List<AuthorBean> cList = new ArrayList<AuthorBean>();
			
			cList = AuthorDao.view();
			
			out.print("<h3>Enter Author</h3>");
			out.print("<form action='BookRecommendationAuthor' method='post' style='width:300px'>");
			out.print("<div class='form-group'>");
			out.print("<select class='form-control' name='aid'>");
			
			out.print("<option disabled selected value=''></option>");
			
			for(AuthorBean bean: cList)
			{
				out.print("<option value='" + bean.getAid() + "'>" + bean.getFname() + " " + bean.getLname() + "</option>");
			}
			
			out.print("</select>");
			out.print("</div><br />");
			out.print("<button class='btn btn-primary' type='submit'>View Books</button>");
			
			out.print("</form>");
			
			out.println("</div>");
			
		}
		
		else
		{
			new com.authfunctions.StudentLogin(request,response,out);	
		}
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
		
	}

}
