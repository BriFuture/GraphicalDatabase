package ui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.JPanel;

import dao.SqliteMasterDao;

public class GraphicalPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SqliteMasterDao> tableList;
	private boolean adding = true;
	private int ox;
	private int oy;
	private int px;
	private int py;
	
	private static int BOX_WIDTH = 200;
	private static int BOX_HEIGHT = 100;

	public GraphicalPanel() {
		setLayout(new BorderLayout());
		tableList = new ArrayList<SqliteMasterDao>();
	}

	public void setTableSet(HashSet<SqliteMasterDao> tableset) {
		tableList.clear();
		adding = true;
		tableList.addAll(tableset);
		adding = false;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(g == null) {
			return;
		}
		ox = getWidth() / 2;
		oy = getHeight() / 2;
		px = (getWidth() - BOX_WIDTH) / 2;
		py = (getHeight() - BOX_HEIGHT) / 2;
		g.drawRect(px, py, BOX_WIDTH, BOX_HEIGHT);
		if( !adding ) {
			g.drawBytes(tableList.get(0).getName().getBytes(), 0, tableList.get(0).getName().length(), ox, oy);
		}
	}
	
}
