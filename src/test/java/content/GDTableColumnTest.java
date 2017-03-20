package content;

import static org.junit.Assert.*;

import javax.swing.table.TableColumn;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.TableStructure;

public class GDTableColumnTest {
	GDTableColumn gdtc;
	TableStructure ts;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
//		gdtc = new GDTableColumn();
		ts = new TableStructure();
		ts.setDefaultRecords();
	}

	@Test
	public void testHeaderValue() {
//		fail("Not yet implemented");
		assertEquals(ts.getTc().getColumn(0).getHeaderValue(), "Column ID");
		assertEquals(ts.getTc().getColumn(0).getIdentifier(), "Column ID");
		assertTrue( ts.getTc().getColumn(0).getWidth() == 75 );
	}
	
	@Test
	public void getColumnCountTest() {
		assertTrue( ts.getTc().getColumnCount() == 7 );
		for(TableColumn tc: ts.getTc().getColumnList()) {
			System.out.println(tc.getHeaderValue());
		}
		assertTrue( ts.getTc().getColumnIndex("Name") == 1 );
	}
	
	@Test
	public void getColumnIndexAtXTest() {
		assertTrue( ts.getTc().getColumnIndexAtX(140) == 1 );
		assertTrue( ts.getTc().getTotalColumnWidth() != 0 );
	}

}
