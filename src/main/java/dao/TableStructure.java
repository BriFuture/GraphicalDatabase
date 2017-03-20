package dao;

import java.util.ArrayList;
import java.util.HashMap;

import content.GDTableColumn;

public class TableStructure implements TablePatternInterface{
	public static String COLUMN_ID = "Column ID";
	public static String COLUMN_NAME = "Name";
	public static String COLUMN_TYPE = "Type";
	public static String COLUMN_NOTNULL = "Not Null";
	public static String COLUMN_DEFAULT = "Default Value";
	public static String COLUMN_PK = "Primary Key";
	public static String COLUMN_FK = "Foreign Key";
	
	public static String TYPE_INT = "integer";
	public static String TYPE_TEXT = "text";
	
	private GDTableColumn tc;
	
	private ArrayList<RecordDao> recordsList;
	private HashMap<String, Class<?>> classType;
	
	
	public TableStructure() {
		tc = new GDTableColumn();
		recordsList = new ArrayList<RecordDao>();
		classType 	= new HashMap<String, Class<?>>();
		
		addColumn(COLUMN_ID, String.class);
		addColumn(COLUMN_NAME, String.class);
		addColumn(COLUMN_TYPE, String.class);
		addColumn(COLUMN_NOTNULL, Boolean.class);
		addColumn(COLUMN_DEFAULT, String.class);
		addColumn(COLUMN_PK, Integer.class);
		addColumn(COLUMN_FK, Boolean.class);
	}
	
	public void addColumn(String str, Class<?> type) {
		classType.put(str, type);
		tc.addColumn(str, type);
	}
	
	/**
	 * 添加一行记录 
	*/
	public void addRecord(int id, String name, String type, boolean notnull, String defaultvalue, int pk, boolean fk) {
		RecordDao rd = new RecordDao(tc);
		rd.addRecordValue(COLUMN_ID, id);
		rd.addRecordValue(COLUMN_NAME, name);
		rd.addRecordValue(COLUMN_TYPE, type);
		rd.addRecordValue(COLUMN_NOTNULL, notnull);
		rd.addRecordValue(COLUMN_DEFAULT, defaultvalue);
		rd.addRecordValue(COLUMN_PK, pk);
		rd.addRecordValue(COLUMN_FK, fk);
		recordsList.add(rd);
	}
	
	/**
	 * 返回记录数
	 * @return
	 */
	public int getRecordsCount() {
		return recordsList.size();
	}
	
	/**
	 * 返回列数
	 * @return
	 */
	public int getColumnCount() {
		return tc.getColumnCount();
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
		recordsList.clear();
		addRecord(0, SqliteMasterDao.C_TYPE, 	TYPE_TEXT, false, "null", 0, false);
		addRecord(1, SqliteMasterDao.C_NAME, 	TYPE_TEXT, false, "null", 0, false);
		addRecord(2, SqliteMasterDao.C_TBL_NAME, TYPE_TEXT, false, "null", 0, false);
		addRecord(3, SqliteMasterDao.C_ROOTPAGE, TYPE_INT,  false, "null", 0, false);
		addRecord(4, SqliteMasterDao.C_SQL, 		TYPE_TEXT, false, "null", 0, false);
	}
	
	public ArrayList<RecordDao> getRecords() {
		return recordsList;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		return recordsList.get(rowIndex).getValue(columnIndex);
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		if(classType.size() > 0) {
			return classType.get(tc.getColumnName(columnIndex));
		}
		return Object.class;
	}
	
	public void setRecordsValueAt(Object value, int rowIndex, int columnIndex) {
		recordsList.get(rowIndex).setRecordValue(getColumnName(columnIndex), value);;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return null;
	}
	
}
