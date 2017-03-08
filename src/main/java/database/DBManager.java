package database;

import java.util.HashMap;

/**
 * DB.java
 * @author future
 * 提供数据库连接
 */
public class DBManager {
	
	private static DBManager manager;
	private HashMap<String, DB> map;
	
	private DBManager() {
		map = new HashMap<String, DB>();
	}
	
	public static DBManager getManager() {
		if(manager == null ) {
			manager = new DBManager();
		}
		return manager;
	}
	
	public DB newDBInstance(String file) {
		DB db = null;
		if(map.containsKey(file)) {
			db = map.get(file);
		} else {
			db = new DB(file);
			// 连接数据库
			db.connect();
			map.put(file, db);
		}
		return db;
	}
	
	public DB getDatabase(String file) {
		return map.get(file);
	}
	
	/**
	 * 断开数据库连接
	 * @param file
	 */
	public void disconnect(String file) {
		getDatabase(file).disconnect();
		map.remove(file);
	}
	
	/**
	 * 断开所有连接
	 */
	public void disconnectAll() {
		for(DB db : map.values()) {
			db.disconnect();
			System.out.println("[Info] database \'" + db.getDbPath() + "\' has disconnected!");
		}
	}

}
