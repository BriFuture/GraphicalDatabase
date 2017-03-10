package content;

public class SqliteMaster {
	public static final String C_TYPE 	= "type";
	public static final String C_NAME 	= "name";
	public static final String C_TBL_NAME = "tbl_name";
	public static final String C_ROOTPAGE = "rootpage";
	public static final String C_SQL 		= "sql";
	
	public static final String[] COLUMNS = {C_TYPE, C_NAME, C_TBL_NAME, C_ROOTPAGE, C_SQL};
	
	private String type;
	private String name;
	private String tbl_name;
	private int rootpage;
	private String sql;
	
	public SqliteMaster(String type, String name, String tbl_name, int rootpage, String sql) {
		this.type = type;
		this.name = name;
		this.tbl_name = tbl_name;
		this.rootpage = rootpage;
		this.sql = sql;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the tbl_name
	 */
	public String getTbl_name() {
		return tbl_name;
	}

	/**
	 * @return the rootpage
	 */
	public int getRootpage() {
		return rootpage;
	}

	/**
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param tbl_name the tbl_name to set
	 */
	public void setTbl_name(String tbl_name) {
		this.tbl_name = tbl_name;
	}

	/**
	 * @param rootpage the rootpage to set
	 */
	public void setRootpage(int rootpage) {
		this.rootpage = rootpage;
	}

	/**
	 * @param sql the sql to set
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	

}
