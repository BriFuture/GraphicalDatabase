package ui;

import javax.swing.JTable;

public class StructureTable extends JTable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};
	static Object[][] data = {
		    {"Kathy", "Smith",
		     "Snowboarding", new Integer(5), new Boolean(false)},
		    {"John", "Doe",
		     "Rowing", new Integer(3), new Boolean(true)},
		    {"Sue", "Black",
		     "Knitting", new Integer(2), new Boolean(false)},
		    {"Jane", "White",
		     "Speed reading", new Integer(20), new Boolean(true)},
		    {"Joe", "Brown",
		     "Pool", new Integer(10), new Boolean(false)}
		};
	public StructureTable() {
		this(data, columnNames);
	}
	public StructureTable(Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
	}
	
	

}
