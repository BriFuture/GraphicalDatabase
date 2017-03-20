package ui;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class UITable extends JTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public TableColumn getColumn(Object identifier) {
		// TODO Auto-generated method stub
		return super.getColumn(identifier);
	}

	@Override
	public Object getValueAt(int row, int column) {
		return getModel().getValueAt(row, column);
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return super.getColumnCount();
	}

	@Override
	public String getColumnName(int column) {
		// TODO Auto-generated method stub
		return super.getColumnName(column);
	}

	@Override
	public Class<?> getColumnClass(int column) {
		// TODO Auto-generated method stub
//		return super.getColumnClass(column);
		return getModel().getColumnClass(column);
	}

	@Override
	public void addColumn(TableColumn aColumn) {
		// TODO Auto-generated method stub
		super.addColumn(aColumn);
	}

	@Override
	public TableModel getModel() {
		// TODO Auto-generated method stub
		return super.getModel();
	}

	@Override
	public TableColumnModel getColumnModel() {
		// TODO Auto-generated method stub
		return super.getColumnModel();
	}

}
