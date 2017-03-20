package dao;

import java.util.HashMap;

import content.GDTableColumn;

/**
 * 一条记录
 * @author future
 *
 */
public class RecordDao {
	private GDTableColumn tc;
	
	private HashMap<String, Object> record = new HashMap<String, Object>();
	private HashMap<String, Object> recordClass = new HashMap<String, Object>();
	
	public RecordDao(GDTableColumn tc) {
		this.tc = tc;
	}
	
	public void addRecordValue(String column, Object value) {
		this.record.put(column, value);
	}
	
	public void setRecordValue(String column, Object value) {
		this.record.put(column, value);
	}
	
	/**
	 * 获取该记录所属的列对象
	 * @return the tc
	 */
	public GDTableColumn getTc() {
		return tc;
	}
	
	/**
	 * 返回该记录在 columnIndex 列的值
	 * @param columnIndex
	 * @return
	 */
	public Object getValue(int columnIndex) {
		return record.get(tc.getColumn(columnIndex).getHeaderValue());
	}
	
	/**
	 * 返回该记录在 columnIndex 列的数据类型
	 * @param columnIndex
	 * @return
	 */
//	public Class<?> getClass(int columnIndex) {
//		return tc.getColumnClass(columnIndex);
//	}
	

}
