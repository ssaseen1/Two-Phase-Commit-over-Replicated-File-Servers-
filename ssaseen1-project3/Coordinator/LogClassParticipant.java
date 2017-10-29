import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

public class LogClassParticipant {
	
	public static void create() 
	{
		try 
		{

			Class.forName("org.sqlite.JDBC");

		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      Connection connection = null;
	      try
	      {

			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			Statement stmt = connection.createStatement();
			String sql = "create table if not exists Log"+
					 	 "(transaction_id int primary key,"+
					 	 "filename text not null,"+
					 	 "operation text not null,"+
					 	 "content text,"+
					 	 "message text not null)";
			stmt.executeUpdate(sql);
			stmt.close();
			connection.close();

		}
	      catch (SQLException e) 
	      {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	public static void insert(RFile rFile, TwoPhaseCommitProtocol tpc, String operation) {
		
		try 
		{

			Class.forName("org.sqlite.JDBC");
		} 
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      Connection connection = null;
	      try
	      {

			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			PreparedStatement ps = connection.prepareStatement("insert into Log values (?,?,?,?,?)");
			ps.setInt(1, tpc.getTransactionId());
			ps.setString(2, rFile.getMeta().getFilename());
			ps.setString(3, operation);
			ps.setString(4, rFile.getContent());
			ps.setString(5, tpc.getMessage());
			ps.executeUpdate();
			ps.close();

			connection.close();
			
		} 
	      catch (SQLException e)
	      {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void update(TwoPhaseCommitProtocol tpc) 
	{
		try {

			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			PreparedStatement ps = connection.prepareStatement("update Log set message=? where transaction_id=?");
			ps.setString(1, tpc.getMessage());
			ps.setInt(2, tpc.getTransactionId());
			ps.executeUpdate();
			ps.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String currentStatus(RFile rFile)
	{
		String status = null;
		try 
		{			

			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			Statement stmt = connection.createStatement();
			String sql = "select * from Log where filename ='"+rFile.getMeta().getFilename()+"' and message in ('commit','abort','vote_request')";
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.isClosed()) {
				while(rs.next()) {
					status = rs.getString("message");
				}
			}
			rs.close();
			stmt.close();
			connection.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return status;
	}
	
	public static List<LogObject> incompleteStatus() 
	{

		
		List<LogObject> list = new ArrayList<>();
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			Statement stmt = connection.createStatement();
			String sql = "select * from Log where message in ('commit','abort')";
			ResultSet rs = stmt.executeQuery(sql);
			if(!rs.isClosed()) {
				while(rs.next()) {
					LogObject lp = new LogObject();
					lp.setTransactionId(rs.getInt("transaction_id"));
					lp.setFilename(rs.getString("filename"));
					lp.setOperation(rs.getString("operation"));
					lp.setMessage(rs.getString("message"));
					lp.setContent(rs.getString("content"));
					list.add(lp);
				}
			}
			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	public static String getContent(int transaction_id) {

		String content = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			Statement stmt = connection.createStatement();
			String sql = "select * from Log where transaction_id = "+transaction_id;
			ResultSet rs = stmt.executeQuery(sql);
			content = rs.getString("content");
			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return content;
	}
	
	public static String lastAction(int transaction_id) 
	{

		String action = null;
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			Statement stmt = connection.createStatement();
			String sql = "select * from Log where transaction_id = "+transaction_id;
			ResultSet rs = stmt.executeQuery(sql);
			action = rs.getString("message");
			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return action;
	}
}
