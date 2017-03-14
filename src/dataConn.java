import java.sql.*;


public class dataConn {
	public String dbusername;
	public String dbemail;
	public String dbpassword;

	public boolean checkUser(String user, String passwd){
		Connection con = null;
		Statement stmt = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/harsha","root","harsha");
			 stmt = con.createStatement();
			 String sql;
		     
			 sql = "SELECT * FROM PROFILE WHERE EMAIL=" + "'" + user + "'" + " AND PASSWORD=" + "'" + passwd + "'";
			 
			 System.out.println(sql);
		     ResultSet rs = stmt.executeQuery(sql);
		     String email = null,password = null;
		     while (rs.next()) {
	                email = rs.getString("email");
	                password = rs.getString("password");
	                this.dbusername = rs.getString("username");
	            }
		     rs.close();
		     stmt.close();
		     con.close();
		     if(user.equals(email) && passwd.equals(password))
		    	 return true;
		     else
		    	 return false;
		     
		     
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean insertRow(String username, String password, String email){
		Connection con = null;
		Statement stmt = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/harsha","root","harsha");
			 stmt = con.createStatement();
			 String sql;
		     sql = "INSERT INTO PROFILE VALUES(" + 
		    		 "'" + username + "'," +
		    		 "'" + password + "'," +
		    		 "'" + email + "'," +
		    		 "CURRENT_TIMESTAMP)";
		     
		     System.out.println(sql);

		     stmt.executeUpdate(sql);
		     stmt.close();
		     con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		  }
		return true;
	}
	public boolean checkemail(String email) {
		Connection con = null;
		Statement stmt = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/harsha","root","harsha");
			 stmt = con.createStatement();
			 String sql;
		     
			 sql = "SELECT * FROM PROFILE WHERE EMAIL=" + "'" + email + "'";
			 
			 System.out.println(sql);
		     
			 ResultSet rs = stmt.executeQuery(sql);
		     String emailid = null;
		     
		     while (rs.next()) {
	                emailid = rs.getString("email");
	                this.dbusername = rs.getString("username");
	                this.dbpassword = rs.getString("password");
	            }
		     rs.close();
		     stmt.close();
		     con.close();
		     if(email.equals(emailid))
		    	 return true;
		     else
		    	 return false;    
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean updateEmail(String email, String password) 
	{
		Connection con = null;
		Statement stmt = null;
		try {
			 Class.forName("com.mysql.jdbc.Driver");
			 con = DriverManager.getConnection("jdbc:mysql://localhost:3306/harsha","root","harsha");
			 stmt = con.createStatement();
			 String sql;
		     
			 sql = "UPDATE PROFILE SET PASSWORD=" + "'" + password + "', CREATEDON=NOW() WHERE EMAIL=" +
			 "'"+email+"'";

			 System.out.println(sql);
			 stmt.executeUpdate(sql);
		     stmt.close();
		     con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	return true;
	}
	/*
	public static void main(String args[]) {
		dataConn d = new dataConn();
		d.insertRow();
		System.out.println(d.checkUser("admin@tst.com","admin"));
	} 
	*/
}
