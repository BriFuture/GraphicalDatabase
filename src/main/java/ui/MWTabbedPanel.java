package ui;

import java.awt.Color;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class MWTabbedPanel {
	private MainWindow mw;
	private JTabbedPane tabbedPane;
	
	public MWTabbedPanel(MainWindow mw) {
		this.mw = mw;
		init();
	}
	
	private void init() {
		tabbedPane = new JTabbedPane();
		JPanel panel1 = makePanel("表结构", Color.red);
		JPanel panel2 = makePanel("数据信息", Color.black);
		JPanel panel3 = makePanel("执行SQL", Color.BLUE);
		JPanel panel4 = makePanel("数据库设置", Color.GREEN);
		
		addTab(tabbedPane, "表结构", null, panel1);
		addTab(tabbedPane, "数据信息", null, panel2);
		addTab(tabbedPane, "执行SQL", null, panel3);
		addTab(tabbedPane, "数据库设置", null, panel4);
	}
	
	private JPanel makePanel(String title, Color c) {
		JPanel p = new JPanel();
		p.setName(title);
		p.setBackground(c);
		return p;
	}
	
	private void addTab(JTabbedPane tabbed, String title, Icon icon, JPanel panel) {
		tabbedPane.addTab(title, icon, panel, title);
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	
	

}
