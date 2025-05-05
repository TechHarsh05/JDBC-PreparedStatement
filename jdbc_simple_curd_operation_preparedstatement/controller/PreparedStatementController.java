package jdbc_simple_curd_operation_preparedstatement.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.mysql.cj.jdbc.Driver;

public class PreparedStatementController {

//	1. Create Connection
	public Connection getConnection() {
		Connection conn = null;

		try {

//		2. Register Driver
			Driver driver = new Driver();
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "root");

			conn = driver.connect("jdbc:mysql://localhost:3306/jdbc-e4", props);

			if (conn != null)
				System.out.println("Connection Established...");
			return conn;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void InsertStudent() {

		PreparedStatement ps = null;
		Connection conn = getConnection();
		Scanner sc = new Scanner(System.in);

		try {
			
			String sql = "Insert into student (id, name, email, dob)values(?,?,?,?)";

			System.out.println("Enter Id :");
			int id = sc.nextInt();
			sc.nextLine();

			System.out.println("Enter Name :");
			String name = sc.nextLine();

			System.out.println("Enter Email :");
			String email = sc.nextLine();

			System.out.println("Enter dob : yyyy-mm-dd");
			String dob = sc.nextLine();

			Date DOB = Date.valueOf(dob);

//			3. Create Statement
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setString(3, email);
			ps.setDate(4, DOB);

//			4. Execute Statement
			int result = ps.executeUpdate();

			if (result == 1) {
				System.out.println("Data Inserted");
			} else {
				System.out.println("Something went wrong");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

//			5. Close Connection
			try {
				sc.close();
				if (ps != null)
					ps.close();

				if (conn != null)
					conn.close();
					System.out.println("connection closed...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void ShowStudent() {

		Connection conn = getConnection();
		PreparedStatement ps = null;	
		
		try {
			String sql = "Select * From student";
			ps = conn.prepareStatement(sql);
			ResultSet result = ps.executeQuery();
			
			if(result!=null)
				while (result.next()) {
					int id = result.getInt(1);
					String name = result.getString(2);
					String email = result.getString(3);
					Date dob = result.getDate(4);
					
					System.out.println("id :"+id+" name :"+name+" email :"+email+" dob :"+dob);
				}

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (ps != null)
					ps.close();

				if (conn != null)
					conn.close();

				System.out.println("connection closed...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void UpdateStudentNameById() {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		Scanner sc = new Scanner(System.in);
		
		try {
		
			String sql = "Update student Set name = ? where id = ?";
			
			ps = conn.prepareStatement(sql);
			
			System.out.println("Enter Name To Update :");
			String name = sc.nextLine();
			System.out.println("Enter Id :");
			int id = sc.nextInt();
			
			ps.setString(1, name);
			ps.setInt(2, id);
			
			int res = ps.executeUpdate();
			
			if(res==1)System.out.println("Updated");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				sc.close();
				if (ps != null)
					ps.close();

				if (conn != null)
					conn.close();
					System.out.println("connection closed...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
	}

	public void DeleteStudent() {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		Scanner sc = new Scanner(System.in);
		
		try {
		
			String sql = "Delete from student where id = ?";
			
			ps = conn.prepareStatement(sql);
			
			System.out.println("Enter Id To Delete :");
			int id = sc.nextInt();
			
			ps.setInt(1, id);
			
			int res = ps.executeUpdate();
			
			if(res==1)System.out.println("Deleted");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				sc.close();
				if (ps != null)
					ps.close();

				if (conn != null)
					conn.close();
					System.out.println("connection closed...");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {

		PreparedStatementController psc = new PreparedStatementController();
		
//		UNCOMMENT METHODS TO CALL
		psc.ShowStudent();
//		psc.InsertStudent();
//		psc.UpdateStudentNameById();
//		psc.DeleteStudent();
//		psc.ShowStudent();

	}
}
