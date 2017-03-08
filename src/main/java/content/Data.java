package content;

import java.io.File;
import java.lang.Thread.State;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import database.DB;
import database.DBManager;

public class Data {
	/* 数据库绝对路径 */
	private File dbFile;
	private String path;
	private DBManager mgr;
	private DB db;
	
	
	public Data(File f) {
		dbFile = f;
		path = f.getAbsolutePath();
		mgr = DBManager.getManager();
		db = mgr.newDBInstance(dbFile.getAbsolutePath());
	}

	/**
	 * @return 数据库绝对路径
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * 返回数据库文件名
	 * @return
	 */
	public String getName() {
		return dbFile.getName();
	}
	
	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Data) {
			Data d = (Data) obj;
			return path.equals(d.toString());
		}
		return path.equals(obj);
	}
	
	@Override
	public String toString() {
		return path.toString();
	}

	/**
	 * 返回数据库的所有 table
	 */
	public void getTables() {
		try {
			Statement state = db.getStatement();
			ResultSet rs = state.executeQuery("select * from sqlite_master where type=\"table\"");
			while(rs.next()) {
				System.out.println("Type: " + rs.getString("type") + "  name: " + rs.getString("name") );
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
