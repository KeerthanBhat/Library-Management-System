package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types.*;

import com.beans.BookBean;
import com.beans.IssueBookBean;
import com.beans.LibrarianBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class BookDao {

	public static int save(BookBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("insert into book values(?,?,?,?,?,?,?)");
			ps.setString(1,bean.getIsbn());
			ps.setString(2,bean.getTitle());
			ps.setString(3,bean.getEdition());
			ps.setInt(4,bean.getQuantity());
			ps.setInt(6,bean.getPid());
			ps.setInt(7,bean.getCid());
			ps.setInt(5,0);
			status=ps.executeUpdate();
			con.close();
			
		}catch(MySQLIntegrityConstraintViolationException e)
		{System.out.println(e); status = 2;}
		
		catch(Exception e) {}
		
		return status;
	}
	public static List<BookBean> view(){
		List<BookBean> list=new ArrayList<BookBean>();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from book");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				String isbn = rs.getString("isbn");
				String name = rs.getString("title");
				String edition = rs.getString("edition");
				int quantity = rs.getInt("quantity");
				int issued = rs.getInt("issued");
				int pid = rs.getInt("pid");
				int cid = rs.getInt("cid");
				BookBean bean=new BookBean(isbn, name, edition, quantity, issued, pid, cid);
				list.add(bean);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return list;
	}
	public static int delete(String isbn){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("delete from book where isbn=?");
			ps.setString(1,isbn);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	public static int getIssued(String isbn){
		int issued=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from book where isbn=?");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				issued=rs.getInt("issued");
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return issued;
	}
	public static boolean checkIssue(String isbn){
		boolean status=false;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from book where isbn=? and quantity>issued");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				status=true;
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	public static int issueBook(IssueBookBean bean){
		String isbn=bean.getIsbn();
		boolean checkstatus=checkIssue(isbn);
		System.out.println("Check status: "+checkstatus);
		if(checkstatus){
			int status=0;
			try{
				Connection con=DB.getCon();
				PreparedStatement ps=con.prepareStatement("insert into issuebook (isbn,sid,lid,doi,dor) values(?,?,?,?,?)");
				ps.setString(1,bean.getIsbn());
				ps.setString(2,bean.getSid());
				ps.setString(3,bean.getLid());
				java.sql.Date currentDate=new java.sql.Date(System.currentTimeMillis());
				ps.setDate(4,currentDate);
				ps.setNull(5,java.sql.Types.DATE);
				
				status=ps.executeUpdate();
				if(status>0){
					PreparedStatement ps2=con.prepareStatement("update book set issued=? where isbn=?");
					ps2.setInt(1,getIssued(isbn)+1);
					ps2.setString(2,isbn);
					status=ps2.executeUpdate();
				}
				con.close();
				
			}catch(Exception e){System.out.println(e);}
			
			return status;
		}else{
			return 0;
		}
	}
	public static int returnBook(String isbn,String sid){
		int status=0;
			try{
				Connection con=DB.getCon();
				PreparedStatement ps=con.prepareStatement("update issuebook set dor=? where isbn=? and sid=?");
				java.sql.Date currentDate=new java.sql.Date(System.currentTimeMillis());
				ps.setDate(1,currentDate);
				ps.setString(2,isbn);
				ps.setString(3,sid);
				
				status=ps.executeUpdate();
				if(status>0){
					PreparedStatement ps2=con.prepareStatement("update book set issued=? where isbn=?");
					ps2.setInt(1,getIssued(isbn)-1);
					ps2.setString(2,isbn);
					status=ps2.executeUpdate();
				}
				con.close();
				
			}catch(Exception e){System.out.println(e);}
			
			return status;
	}
	public static List<IssueBookBean> viewIssuedBooks(){
		List<IssueBookBean> list=new ArrayList<IssueBookBean>();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from issuebook order by doi desc");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				IssueBookBean bean = new IssueBookBean();
				bean.setIsbn(rs.getString("isbn"));
				bean.setSid(rs.getString("sid"));
				bean.setLid(rs.getString("lid"));
				bean.setDoi(rs.getDate("doi"));
				bean.setDor(rs.getDate("dor"));
				list.add(bean);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return list;
	}
	public static List<IssueBookBean> viewIssuedBooksforStudent(String sid){
		List<IssueBookBean> list=new ArrayList<IssueBookBean>();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from issuebook where sid=? order by doi desc");
			ps.setString(1,sid);
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				IssueBookBean bean = new IssueBookBean();
				bean.setIsbn(rs.getString("isbn"));
				bean.setSid(rs.getString("sid"));
				bean.setLid(rs.getString("lid"));
				bean.setDoi(rs.getDate("doi"));
				bean.setDor(rs.getDate("dor"));
				list.add(bean);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return list;
	}
	public static int update(BookBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("update book set title=?,edition=?,quantity=?,pid=?,cid=? where isbn=?");
			ps.setString(1,bean.getTitle());
			ps.setString(2,bean.getEdition());
			ps.setInt(3, bean.getQuantity());
			ps.setInt(4,bean.getPid());
			ps.setInt(5,bean.getCid());
			ps.setString(6,bean.getIsbn());
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	public static BookBean viewById(String isbn){
		BookBean bean=new BookBean();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from book where isbn=?");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean.setIsbn(rs.getString(1));
				bean.setTitle(rs.getString(2));
				bean.setEdition(rs.getString(3));
				bean.setQuantity(rs.getInt(4));
				bean.setIssued(rs.getInt(5));
				bean.setPid(rs.getInt(6));
				bean.setCid(rs.getInt(7));
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return bean;
	}
	public static String getTitlebyId(String isbn) {
		BookBean bean=new BookBean();
		String title = new String();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select title from book where isbn=?");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				title = rs.getString(1);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return title;
	}
	public static boolean checkIsbn(String isbn) {
		boolean i=false;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from book where isbn=?");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				i = true;
			}
			else {
				i = false;
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return i;
	}
	public static boolean checkIsbnissue(String isbn) {
		boolean i=false;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from issuebook where isbn=?");
			ps.setString(1,isbn);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				i = true;
			}
			else {
				i = false;
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return i;
	}
}