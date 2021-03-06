package com.dao;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.beans.CategoryBean;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class CategoryDao {

	public static int save(CategoryBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("insert into category(name, description) values(?,?)");
			ps.setString(1,bean.getName());
			ps.setString(2,bean.getDescription());
			status=ps.executeUpdate();
			con.close();
			
		}
		catch (MySQLIntegrityConstraintViolationException e) {
			status = 2;
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		System.out.println(status);
		
		return status;
	}
	public static int update(CategoryBean bean){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("update category set name=?, description=? where cid=?");
			ps.setString(1,bean.getName());
			ps.setString(2,bean.getDescription());
			ps.setInt(3,bean.getCid());
			status=ps.executeUpdate();
			con.close();
			
		}
		catch(MySQLIntegrityConstraintViolationException e)
		{status = 2;}
		catch (Exception e) {
			System.out.println(e);
		}
		
		return status;
	}
	public static List<CategoryBean> view(){
		List<CategoryBean> list=new ArrayList<CategoryBean>();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from category");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				CategoryBean bean=new CategoryBean();
				bean.setCid(rs.getInt("cid"));
				bean.setName(rs.getString("name"));
				bean.setDescription(rs.getString("description"));
				list.add(bean);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return list;
	}
	public static CategoryBean viewById(String cid){
		CategoryBean bean = new CategoryBean();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select * from category where cid=?");
			ps.setString(1,cid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				bean.setCid(rs.getInt(1));
				bean.setName(rs.getString(2));
				bean.setDescription(rs.getString(3));
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return bean;
	}
	public static int delete(String cid){
		int status=0;
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("delete from category where cid=?");
			ps.setString(1,cid);
			status=ps.executeUpdate();
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return status;
	}
	public static String getNamebyId(Integer Cid)
	{
		String name = new String();
		try{
			Connection con=DB.getCon();
			PreparedStatement ps=con.prepareStatement("select name from category where cid=?");
			ps.setInt(1,Cid);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				name = rs.getString(1);
			}
			con.close();
			
		}catch(Exception e){System.out.println(e);}
		
		return name;
	}
}