package content;

import java.util.ArrayList;

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
	
	/**
	 * 添加列
	 * @param columnName
	 * @param classType
	 */
	public void addColumn(String columnName, Class<?> classType);

	/**
	 * 移除列
	 * @param columnIndex
	 */
	public void removeColumn(int columnIndex);

	/**
	 * 返回列名
	 * @param columnIndex
	 * @return
	 */
	public String getColumnName(int columnIndex);
	
	public GDTableColumn getTc();
	
	/**
	 * 返回所有记录
	 * @return
	 */
	public ArrayList<RecordDao> getRecords();
	
	public Object getValueAt(int rowIndex, int columnIndex);
	
	public Class<?> getColumnClass(int columnIndex);
	
	public void setRecordsValueAt(Object value, int rowIndex, int columnIndex);
	
	public void removeRecord(int rowIndex);
	
	public void addRecord(RecordDao record);
	
	public void addRecord(int i, RecordDao record);
	
}
