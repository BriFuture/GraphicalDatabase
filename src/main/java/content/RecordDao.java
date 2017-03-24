package content;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 一条记录
 * @author future
 *
 */
public class RecordDao {
	private ArrayList<Object> row;
	
	public RecordDao() {
		row = new ArrayList<Object>();
	}
	
	public void addValue(Object value) {
		this.row.add(value);
	}
	
	public void addValueAtColumn(int columnIndex, Object value) {
		this.row.add(columnIndex, value);
	}
	
	public void setValueAtColumn(int columnIndex, Object value) {
		this.row.set(columnIndex, value);
	}
	
	/**
	 * 返回该记录在 columnIndex 列的值
	 * @param columnIndex
	 * @return
	 */
	public Object getValue(int columnIndex) {
		return row.get(columnIndex);
	}

}
