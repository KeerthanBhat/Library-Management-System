package com.servlets;

import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.AuthorBean;
import com.dao.AuthorDao;

@WebServlet("/AddAuthor")
public class AddAuthor extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Add Author Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		request.getRequestDispatcher("navlibrarian.html").include(request, response);
		
		out.println("<div class='container'>");

		String fname=request.getParameter("fname");
		String lname=request.getParameter("lname");
		String phone=request.getParameter("phone");
		String address=request.getParameter("address");
		
		AuthorBean bean=new AuthorBean(fname,lname,phone,address);
		int i=AuthorDao.save(bean);
		System.out.println(i);
		
		if(i==1){
			out.println("<h3>Author saved successfully</h3>");
		}
		else if(i==0) {
			out.println("<h3>Author already exists</h3>");
		}
		request.getRequestDispatcher("addauthorform.html").include(request, response);
		out.println("</div>");
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}