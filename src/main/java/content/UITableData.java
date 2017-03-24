package content;

import java.util.ArrayList;

public class UITableData implements TablePatternInterface {
	protected ArrayList<String> columnList;
	protected ArrayList<RecordDao> recordList;
	protected ArrayList<Class<?>> classTypeList;
	protected GDTableColumn tc;

	public UITableData() {
		columnList = new ArrayList<String>();
		recordList = new ArrayList<RecordDao>();
		classTypeList = new ArrayList<Class<?>>();
		tc = new GDTableColumn();
	}
	
	/**
	 * 添加新的一列，默认添加到末尾
	 */
	@Override
	public void addColumn(String columnName, Class<?> classType) {
		tc.addColumn(columnName, classType);
		columnList.add(columnName);
		classTypeList.add(classType);
	}
	
	/**
	 * 移除一列
	 * @param columnIndex
	 */
	@Override
	public void removeColumn(int columnIndex) {
		tc.removeColumn(columnIndex);
		columnList.remove(columnIndex);
		classTypeList.remove(columnIndex);
	}

	@Override
	public int getRecordsCount() {
		return recordList.size();
	}

	@Override
	public int getColumnCount() {
		System.out.println("UITableData: " + columnList.size());
		return columnList.size();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnList.get(columnIndex);
	}

	@Override
	public GDTableColumn getTc() {
		return tc;
	}

	@Override
	public ArrayList<RecordDao> getRecords() {
		return recordList;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return recordList.get(rowIndex).getValue(columnIndex);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if( classTypeList.size() > columnIndex )
			return classTypeList.get(columnIndex);
		
		return Object.class;
	}

	@Override
	public void setRecordsValueAt(Object value, int rowIndex, int columnIndex) {
		recordList.get(rowIndex).setValueAtColumn(columnIndex, value);
	}

	@Override
	public void removeRecord(int rowIndex) {
		recordList.remove(rowIndex);
	}

	@Override
	public void addRecord(RecordDao record) {
		recordList.add(record);
	}

	@Override
	public void addRecord(int i, RecordDao record) {
		recordList.add(i, record);
	}
	
}
