package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DB {
	static final String DATABASE = "jdbc:sqlite:";
	private String dbPath = "";
	private Connection conn = null;
	private Statement state = null;
//	private 

	protected DB() {
		
	}
	
	protected DB(String dbPath) {
		setDbPath(dbPath);
	}
	
	protected DB(String dbPath, String db) {
		setDbPath(dbPath, db);
	}
	
	/**
	 * @return the dbPath
	 */
	public String getDbPath() {
		return dbPath;
	}

	/**
	 * @param dbPath 数据库文件的绝对路径
	 */
	public void setDbPath(String dbPath) {
		this.dbPath = DATABASE + dbPath;
	}
	
	/**
	 * 
	 * @param dbPath  数据库文件的绝对路径
	 * @param db	    具体数据库类型
	 */
	public void setDbPath(String dbPath, String db) {
		this.dbPath = "jdbc:" + db +":" + dbPath;
	}
	
	/**
	 * 
	 * @return 返回数据库连接
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * 
	 * @return  返回 state
	 */
	public Statement getStatement() {
		return state;
	}

	/**
	 * 连接数据库
	 */
	public void connect() {
		try {
			conn = DriverManager.getConnection(dbPath);
			state = conn.createStatement();
		} catch (SQLException e) {
			
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * 断开数据库连接
	 */
	public void disconnect() {
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			System.err.println(e);
		}
	}

}
