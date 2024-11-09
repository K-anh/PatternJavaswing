package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectDB {
	public static Connection con = null;
	
	private static ConnectDB instance = new ConnectDB();
	public static ConnectDB getInstance() {
		return instance;
	}
	public void connect() {
		String url="jdbc:sqlserver://localhost:1433;databasename=DB_PTUD_Final";
		String user = "sa";
		String password = "12345";
		try { 
			con =DriverManager.getConnection(url,user,password);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection() {
		return con;
	}
	public void disconnect()
	{
		if(con!= null)
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
	}
	public static ResultSet sqlQuery(String qry) {
		// TODO Auto-generated method stub
		return null;
	}
	public static void Open() {
		// TODO Auto-generated method stub
		
	}
}
