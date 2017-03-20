package dao;

import java.util.ArrayList;

import content.GDTableColumn;

public interface TablePatternInterface {
	/**
	 * 返回记录数
	 * @return
	 */
	public int getRecordsCount();
	
	/**
	 * 返回列数
	 * @return
	 */
	public int getColumnCount();
	
	public String getColumnName(int columnIndex);
	
	public GDTableColumn getTc();
	
	public ArrayList<RecordDao> getRecords();
	
	public Object getValueAt(int rowIndex, int columnIndex);
	
	public Class<?> getColumnClass(int columnIndex);
	
	public void setRecordsValueAt(Object value, int rowIndex, int columnIndex);
}
