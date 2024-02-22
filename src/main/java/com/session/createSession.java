package com.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class createSession
 */
@WebServlet("/createSession")
public class createSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 Connection con=null;
     PreparedStatement stmt;
    
	public void init() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/mydb","root","root");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try {
			stmt=con.prepareStatement("insert into user values(?,?,?,?)");
			String name=req.getParameter("uname");
			String email=req.getParameter("email");
			int bal=Integer.parseInt(req.getParameter("balance"));
			String pass=req.getParameter("password");
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setInt(3, bal);
			stmt.setString(4, pass);
			int rs=stmt.executeUpdate();
			PrintWriter out=res.getWriter();
			RequestDispatcher rd1 = req.getRequestDispatcher("WelcomServlet");
			RequestDispatcher rd2 = req.getRequestDispatcher("createSession");
			
			//session creation starts
			HttpSession session = req.getSession();//Creating session
			session.setAttribute("name", name); // set value in session
			if(rs>0) {
				rd1.forward(req, res);	
			}else {
				rd2.include(req, res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	
	}

}
