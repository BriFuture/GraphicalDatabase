package content;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import database.DB;
import database.DBManager;

public class Data {
	/* 数据库绝对路径 */
	private File dbFile;
	private String path;
	private DBManager mgr;
	private DB db;
	private Statement state;
	
	
	public Data(File f) {
		dbFile = f;
		path = f.getAbsolutePath();
		mgr = DBManager.getManager();
		db = mgr.newDBInstance(dbFile.getAbsolutePath());
		state = db.getStatement();
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
	 * @return 
	 */
	
	public Set<SqliteMaster> getTables() {
		HashSet<SqliteMaster> set = new HashSet<SqliteMaster>();
		try {
			ResultSet rs = state.executeQuery("select * from sqlite_master where type=\"table\"");
			while( rs.next() ) {
				set.add(new SqliteMaster(rs.getString(SqliteMaster.C_TYPE), 
										rs.getString(SqliteMaster.C_NAME), 
										rs.getString(SqliteMaster.C_TBL_NAME), 
										rs.getInt(SqliteMaster.C_ROOTPAGE), 
										rs.getString(SqliteMaster.C_SQL)
										)
						);
			}
		} catch ( SQLException e) {
			e.printStackTrace();
			
		}
		return set;
		
	}
	
	/**
	 *  主动调用，关闭 state 
	*/
	public void closeState() {
		if( state != null) {
			try {
				state.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
