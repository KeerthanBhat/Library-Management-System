package com.servlets;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.AuthorBean;
import com.beans.BookBean;
import com.beans.WrittenBean;
import com.dao.AuthorDao;
import com.dao.BookDao;
import com.dao.WrittenDao;
import com.dao.BookDao;
import com.beans.BookBean;

@WebServlet("/AddBookAuthor")
public class AddBookAuthor extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Add Books for Author Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		request.getRequestDispatcher("navlibrarian.html").include(request, response);
		
		out.println("<div class='container'>");

		String isbn=request.getParameter("isbn");
		Integer aid=Integer.parseInt(request.getParameter("aid"));
		
		WrittenBean bean=new WrittenBean(isbn,aid);
		int i=WrittenDao.save(bean);
		System.out.println(i);
		
		if(i==1){
			out.println("<h3>Book for Author saved successfully</h3>");
		}
		else if(i==2) {
			out.println("<h3>Book for Author already exists</h3>");
		}
		
		List<BookBean> listBook=BookDao.view();
		List<AuthorBean> listAuthor=AuthorDao.view();
		
		request.getRequestDispatcher("addbookauthorform.html").include(request, response);
		out.println("<div class=\"form-group\">"
				+ "<label for=\"book1\">Book</label>");
				
				out.println("<select required name=\"isbn\">");
				for(BookBean bean1:listBook){
					out.println("<option value=\""+bean1.getIsbn()+"\">"+bean1.getTitle()+"</option>");
				}
				out.println("</select>");
				out.println("</div>");
				
		out.println("<div class=\"form-group\">"
				+ "<label for=\"author1\">Author</label>");
				
				out.println("<select required name=\"aid\">");
				for(AuthorBean bean2:listAuthor){
					out.println("<option value=\""+bean2.getAid()+"\">"+bean2.getFname()+" "+bean2.getLname()+"</option>");
				}
				out.println("</select>");
				out.println("</div>");
				
				out.println("<button type=\"submit\" class=\"btn btn-primary\">Save</button>"
				+ "</form>");
		
		out.println("</div>");
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}
}