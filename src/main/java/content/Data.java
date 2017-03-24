package content;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dao.SqliteMasterDao;
import database.DB;
import database.DBManager;

/**
 * 用于保存数据库文件，提供执行 SQL 的接口
 * @author future
 *
 */
public class Data {
	/* 数据库文件 */
	private File dbFile;
	/* 数据库绝对路径 */
	private String path;
	/* 数据库管理器 */
	private DBManager mgr;
	private DB db;
	private Statement state;
	
	//
	private HashSet<SqliteMasterDao> smset;
	
	
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
	 * 返回数据库文件名（不包含路径）
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
		return getName();
	}
	
	public Object excuteCustomSql(String sql) {
		try {
			ResultSet rs = state.executeQuery(sql);
			rs.wasNull();
		} catch ( SQLException e) {
			e.printStackTrace();
			// 
			System.out.println(e.getMessage());
		} finally {
			closeState();
		}
		return null;
	}

	/**
	 * 返回数据库的所有 table
	 * @return 
	 */
	
	public Set<SqliteMasterDao> getMasterSet() {
		if( smset == null ) {
			smset = new HashSet<SqliteMasterDao>();
			try {
				ResultSet rs = state.executeQuery("select * from sqlite_master");
				while( rs.next() ) {
					smset.add(new SqliteMasterDao(rs.getString(SqliteMasterDao.C_TYPE), 
											rs.getString(SqliteMasterDao.C_NAME), 
											rs.getString(SqliteMasterDao.C_TBL_NAME), 
											rs.getInt(SqliteMasterDao.C_ROOTPAGE), 
											rs.getString(SqliteMasterDao.C_SQL)
											)
							);
				}
			} catch ( SQLException e) {
				e.printStackTrace();
			} finally {
				closeState();
			}
		}
		return smset;
	}
	
	public Set<SqliteMasterDao> getMasterSet(boolean restate) {
		if(restate) {
			smset = null;
		}
		return getMasterSet();
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
