import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class LogClassCoordinator {
	
	public static void create() {
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
			String sql = "create table if not exists Log"+
					 	 "(transaction_id int primary key,"+
					 	 "filename text not null,"+
					 	 "operation text not null,"+
					 	 "content text,"+
					 	 "message text not null)";
			stmt.executeUpdate(sql);
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void insert(RFile rFile, TwoPhaseCommitProtocol tpc, String operation) {
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      Connection connection = null;
	      try {
			connection = DriverManager.getConnection("jdbc:sqlite:log.db");
			PreparedStatement ps = connection.prepareStatement("insert into Log values (?,?,?,?,?)");
			ps.setInt(1, tpc.getTransactionId());
			ps.setString(2, rFile.getMeta().getFilename());
			ps.setString(3, operation);
			ps.setString(4, null);
			ps.setString(5, tpc.getMessage());
			ps.executeUpdate();
			ps.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<LogObject> select() {
		
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
			String sql = "select * from Log where message = 'vote_request'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				LogObject lcp = new LogObject();
				lcp.setTransactionId(rs.getInt("transaction_id"));
				lcp.setFilename(rs.getString("filename"));
				lcp.setOperation(rs.getString("operation"));
				lcp.setContent(rs.getString("content"));
				lcp.setMessage(rs.getString("message"));
				list.add(lcp);
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
	
	public static void update(TwoPhaseCommitProtocol tpc) {
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
	
	public static AtomicInteger maxTransaction_id() {
		AtomicInteger t_id = null;
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
			String sql = "select * from Log where transaction_id = (select max(transaction_id) from Log)";
			ResultSet rs = stmt.executeQuery(sql);
			t_id = new AtomicInteger(rs.getInt("transaction_id"));
			rs.close();
			stmt.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return new AtomicInteger(0);
		}
		return t_id;
	}
	
	public static String lastAction(TwoPhaseCommitProtocol tpc) {
		String last_action = null;
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
			String sql = "select * from Log where transaction_id="+tpc.getTransactionId();
			ResultSet rs = stmt.executeQuery(sql);
			last_action = rs.getString("message");
			rs.close();
			stmt.close();
			connection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return last_action;
	}
}