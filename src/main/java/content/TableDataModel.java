package content;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class TableDataModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private TablePatternInterface tpi;
	
	public TableDataModel() {
	}

	public TableDataModel(TablePatternInterface tpi) {
		this.tpi = tpi;
	}
	

	/**
	 * @param tpi the tpi to set
	 */
	public void setTablePattern(TablePatternInterface tpi) {
		this.tpi = tpi;
//		fireTableStructureChanged();
//		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		if( tpi == null ) {
			return 0;
		}
		return tpi.getRecordsCount();
	}

	@Override
	public int getColumnCount() {
		if(tpi == null) {
			return 0;
		}
//		System.out.println("===data model column count: " + tpi.getColumnCount());
		return tpi.getColumnCount();
	}

	@Override
	public String getColumnName(int columnIndex) {
		return tpi.getColumnName(columnIndex);
	}


	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return tpi.getColumnClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if( tpi == null ) {
			return null;
		}
//		System.out.println("value at data model: " + rowIndex + " === " + columnIndex + " === " + tpi.getValueAt(rowIndex, columnIndex));
		return tpi.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		
	}
	
	public void addRow(Object[] rowData) {
//		data[] = rowData;
	}
	

}
