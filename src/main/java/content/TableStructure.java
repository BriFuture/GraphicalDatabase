package content;

import dao.SqliteMasterDao;

public class TableStructure extends UITableData{
	public static String COLUMN_ID = "Column ID";
	public static String COLUMN_NAME = "Name";
	public static String COLUMN_TYPE = "Type";
	public static String COLUMN_NOTNULL = "Not Null";
	public static String COLUMN_DEFAULT = "Default Value";
	public static String COLUMN_PK = "Primary Key";
	public static String COLUMN_FK = "Foreign Key";
	
	public static String TYPE_INT = "integer";
	public static String TYPE_TEXT = "text";
	
	
	
	public TableStructure() {
		super();
		
		addColumn(COLUMN_ID, String.class);
		addColumn(COLUMN_NAME, String.class);
		addColumn(COLUMN_TYPE, String.class);
		addColumn(COLUMN_NOTNULL, Boolean.class);
		addColumn(COLUMN_DEFAULT, String.class);
		addColumn(COLUMN_PK, Integer.class);
		addColumn(COLUMN_FK, Boolean.class);
	}
	
	public void addColumn(String str, Class<?> type) {
		classTypeList.add(type);
		tc.addColumn(str, type);
	}
	
	/**
	 * 添加一行记录 
	*/
	public void addRecord(int id, String name, String type, boolean notnull, String defaultvalue, int pk, boolean fk) {
		RecordDao rd = new RecordDao();
		rd.addValue(id);
		rd.addValue(name);
		rd.addValue(type);
		rd.addValue(notnull);
		rd.addValue(defaultvalue);
		rd.addValue(pk);
		rd.addValue(fk);
		recordList.add(rd);
	}
	/**
	 * @return the tc
	 */
	public GDTableColumn getTc() {
		return tc;
	}

	/**
	 * 
	 */
	public void setDefaultRecords() {
		recordList.clear();
		addRecord(0, SqliteMasterDao.C_TYPE, 	TYPE_TEXT, false, "null", 0, false);
		addRecord(1, SqliteMasterDao.C_NAME, 	TYPE_TEXT, false, "null", 0, false);
		addRecord(2, SqliteMasterDao.C_TBL_NAME, TYPE_TEXT, false, "null", 0, false);
		addRecord(3, SqliteMasterDao.C_ROOTPAGE, TYPE_INT,  false, "null", 0, false);
		addRecord(4, SqliteMasterDao.C_SQL, 		TYPE_TEXT, false, "null", 0, false);
	}
	
}
