package dao;

import java.util.ArrayList;

public class TableDao {
	public static final String TYPE = "table";
	
	private ArrayList<String> columnList;
	/* sqlite several pks */
	private ArrayList<String> pkList;
	private ArrayList<String> fkList;
	
	private String createSQL;
	
	
	public TableDao() {
		super();
		columnList = new ArrayList<String>();
		pkList = new ArrayList<String>();
		fkList = new ArrayList<String>();
	}

	private String tablename;

	/**
	 * @return the tablename
	 */
	public String getTablename() {
		return tablename;
	}

	/**
	 * @param tablename the tablename to set
	 */
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	/**
	 * @return the columnList
	 */
	public ArrayList<String> getColumnList() {
		return columnList;
	}

	/**
	 * @param columnList the columnList to set
	 */
	public void setColumnList(ArrayList<String> columnList) {
		this.columnList = columnList;
	}

	/**
	 * 主键列表（SQLITE 支持多主键）
	 * @return the pkList
	 */
	public ArrayList<String> getPkList() {
		return pkList;
	}

	/**
	 * @param pkList the pkList to set
	 */
	public void setPkList(ArrayList<String> pkList) {
		this.pkList = pkList;
	}

	/**
	 * 外键列表
	 * @return the fkList
	 */
	public ArrayList<String> getFkList() {
		return fkList;
	}

	/**
	 * @param fkList the fkList to set
	 */
	public void setFkList(ArrayList<String> fkList) {
		this.fkList = fkList;
	}

	/**
	 * @return the createSQL
	 */
	public String getCreateSQL() {
		return createSQL;
	}

	/**
	 * 设置建该表的 SQL 语句
	 * @param createSQL the createSQL to set
	 */
	public void setCreateSQL(String createSQL) {
		this.createSQL = createSQL;
	}
	
	
}
