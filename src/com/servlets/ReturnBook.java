package com.servlets;


import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.beans.IssueBookBean;
import com.dao.BookDao;
import com.dao.StudentDao;
import java.util.Calendar;

@WebServlet("/ReturnBook")
public class ReturnBook extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.println("<head>");
		out.println("<title>Return Book Form</title>");
		out.println("<link rel='stylesheet' href='bootstrap.min.css'/>");
		out.println("</head>");
		out.println("<body>");
		request.getRequestDispatcher("navlibrarian.html").include(request, response);
		
		out.println("<div class='container'>");
		String isbn=request.getParameter("isbn");
		String sid=request.getParameter("usn");
		
		boolean i=BookDao.checkIsbnissue(isbn);
		if(i == false) {
			out.println("<h3>Invalid ISBN.</h3><p>Please check again.</p>");
		}
		else {
			i=StudentDao.checkUsnissue(sid);
			if(i == false) {
				out.println("<h3>Invalid Student USN.</h3><p>Please check again.</p>");
			}
			else {
				List<Date> j=BookDao.returnBook(isbn,sid);
				if(j != null){
					Date doi = j.get(0);
					Date dor = j.get(1);
					System.out.println(doi);
					System.out.println(dor);
					
					String doiStr = doi.toString();
					String dorStr = dor.toString();
					char doiDay1 = doiStr.charAt(8);
					char doiDay2 = doiStr.charAt(9);
					char dorDay1 = dorStr.charAt(8);
					char dorDay2 = dorStr.charAt(9);
					System.out.println(doiDay1);
					System.out.println(doiDay2);
					System.out.println(dorDay1);
					System.out.println(dorDay2);
					
					String doiDay1str = Character.toString(doiDay1);
					String doiDay2str = Character.toString(doiDay2);
					String doiDay = doiDay1str + doiDay2str;
					System.out.println(doiDay);
					
					String dorDay1str = Character.toString(dorDay1);
					String dorDay2str = Character.toString(dorDay2);
					String dorDay = dorDay1str + dorDay2str;
					System.out.println(dorDay);
					
					int doiday = Integer.parseInt(doiDay);
					int dorday = Integer.parseInt(dorDay);
					System.out.println(doiday);
					System.out.println(dorday);
					int difference = dorday - doiday;
					
					out.println("<h3>Book returned successfully</h3>");
					int fineAmt = 1;
					int limit = 7;
					System.out.println(difference);
					if(difference > limit)
					{
						int fine = (difference - limit) * fineAmt;
						System.out.println(fine);
						out.println("<p>Fine to be paid: " + "Rs." + Integer.toString(fine) + "</p>");
						out.println("<p>Number of days delayed: " + Integer.toString(difference - limit) + "</p>");
					}
				}else{
					out.println("<h3>Sorry, unable to return book.</h3><p>Please try again.</p>");
				}
			}
		}
		
		out.println("</div>");
		
		
		request.getRequestDispatcher("footer.html").include(request, response);
		out.close();
	}

}
