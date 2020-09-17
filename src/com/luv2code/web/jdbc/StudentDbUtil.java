package com.luv2code.web.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;


public class StudentDbUtil {
	private DataSource datasource;
	
	public StudentDbUtil(DataSource theDataSource) {
		datasource = theDataSource;
	}
	
	
	public List<Student> getStudents() throws Exception {
		
		List<Student> students = new ArrayList<Student>();
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			
			// get a connection
			myConn = datasource.getConnection();
			// create sql statement
			String sql = "select * from student order by last_name";
			myStmt = myConn.createStatement();
			//execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while(myRs.next()) {
				
				//retrieve data from set row
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				// create new student objects
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				
				
				// add it to the list of students
				students.add(tempStudent);
			}
		
			// close JDBC objects
			return students;
		}
		finally {
			close(myConn, myStmt, myRs);
		}
		
		
		
	}


	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if(myRs != null) {
				myRs.close();
			}
			if(myStmt != null) {
				myStmt.close();
			}
			if(myConn != null) {
				myConn.close();
			}
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	

}
