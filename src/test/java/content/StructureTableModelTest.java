package content;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StructureTableModelTest {
	TableDataModel stm;
	TableStructure ts;
	@Before
	public void setUp() throws Exception {
		ts = new TableStructure();
		ts.setDefaultRecords();
		stm = new TableDataModel();
		stm.setTablePattern(ts);
	}

	@Test
	public void test() {
		assertSame("StructureTableModel Table Column ", TableStructure.COLUMN_ID, stm.getColumnName(0));
		System.out.println(stm.getColumnName(0));
	}

}
