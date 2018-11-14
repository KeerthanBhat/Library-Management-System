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

import com.beans.CategoryBean;
import com.beans.CourseRecommendBean;
import com.dao.CategoryDao;
import com.dao.CourseDao;

@WebServlet("/BookRecommendationCourse")
public class BookRecommendationCourse extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>View Recommendations</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("studentusn") != null)
		{
		
			request.getRequestDispatcher("navstudent.html").include(request, response);
			out.println("<div class='container'>");
			
			List<CourseRecommendBean> list = CourseDao.retrieve(request.getParameter("cid"));
			
			out.println("<table class='table table-bordered table-striped'>");
			out.println("<tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Course</th><th>Stock</th></tr>");
			for(CourseRecommendBean bean:list){
				out.println("<tr><td>"+bean.getIsbn()+"</td><td>"+bean.getTitle()+"</td><td>"+bean.getEdition()+"</td><td>"+bean.getCourse()+"</td><td>"+bean.getStock()+"</td></tr>");
			}
			out.println("</table>");
			
			out.println("</div>");
		}
		
		else
		{
			new com.authfunctions.StudentLogin(request,response,out);
		}
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>View Recommendations</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		
		HttpSession session = request.getSession();
		
		if (session.getAttribute("studentusn") != null)
		{
		
			request.getRequestDispatcher("navstudent.html").include(request, response);
			out.println("<div class='container'>");
			
			List<CourseRecommendBean> list = new ArrayList<CourseRecommendBean>();
			
			
			out.println("<table class='table table-bordered table-striped'>");
			out.println("<tr><th>ISBN</th><th>Title</th><th>Edition</th><th>Course</th><th>Stock</th></tr>");
			for(CourseRecommendBean bean:list){
				out.println("<tr><td>"+bean.getIsbn()+"</td><td>"+bean.getTitle()+"</td><td>"+bean.getEdition()+"</td><td>"+bean.getCourse()+"</td><td>"+bean.getStock()+"</td></tr>");
			}
			out.println("</table>");
			
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
