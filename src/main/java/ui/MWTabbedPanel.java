package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import content.Data;
import dao.Index;
import dao.SqliteMaster;
import dao.Table;
import dao.Trigger;

public class MWTabbedPanel {
	private MainWindow mw;
	private JTabbedPane tabbedPane;
	
	private BtnListener btnListener;
	
	private JPanel panel1;
	private String p1Tab;
	private JLabel recordsLabel;
	private JLabel indexLabel;
	private JLabel triggerLabel;
	private JButton p1ExportBtn;
	private JButton p1DropBtn;
	private JButton p1EmptyBtn;
	private JButton p1RenameBtn;
	private JButton p1ReindexBtn;
	private JButton p1CopyBtn;
	
	private TitledBorder opBorder;
	
	private JPanel panel2;
	private String p2Tab;
	
	private JPanel panel3;
	private String p3Tab;
	
	private JPanel panel4;
	private String p4Tab;
	
	private JPanel panel5;
	private String p5Tab;
	
	
	private Data data;
	
	public MWTabbedPanel(MainWindow mw) {
		this.mw = mw;
		init();
	}
	
	private void init() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setMinimumSize(new Dimension(600, 600));
		p1Tab = "表结构";
		p2Tab = "数据信息";
		p3Tab = "执行SQL";
		p4Tab = "数据库设置";
		p5Tab = "Graphical Database";
		
		btnListener = new BtnListener();
		
		panel1 = makePanel(1, null);
		panel2 = makePanel(2, null);
		panel3 = makePanel(3, null);
		panel4 = makePanel(4, null);
		panel5 = makePanel(5, null);
		
	}
	
	private JPanel makePanel(int n, Icon icon) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		switch(n) {
		case 1:
			setPanel1(p);
			break;
		case 2:
			setPanel2(p);
			break;
		case 3:
			setPanel3(p);
			break;
		case 4:
			setPanel4(p);
			break;
		case 5:
			setPanel5(p);
			break;
		}
		tabbedPane.addTab(p.getName(), icon, p, p.getName());
		return p;
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public void add(Data d) {
		data = d;
		updatePanels(d);
	}
	
	private void updatePanels(Data d) {
		HashSet<SqliteMaster> smset = (HashSet<SqliteMaster>) d.getMasterSet();
		HashSet<SqliteMaster> tableset = SqliteMaster.getSetByType(smset, Table.TYPE);
		HashSet<SqliteMaster> indexset = SqliteMaster.getSetByType(smset, Index.TYPE);
		HashSet<SqliteMaster> triggerset = SqliteMaster.getSetByType(smset, Trigger.TYPE);
		opBorder.setTitle("Operation of " + d.getName());
		recordsLabel.setText("Number of records:  " + tableset.size());
		indexLabel.setText("Number of indexes:  " + indexset.size());
		triggerLabel.setText("Number of triggers:  " + triggerset.size());
	}
	
	private void setPanel1(JPanel p) {
		p.setName(p1Tab);
		
		recordsLabel = new JLabel("Number of records:  0");
		indexLabel 	 = new JLabel("Number of indexes:  0");
		triggerLabel = new JLabel("Number of triggers:  0");
		
		p1ExportBtn = new JButton("Export");
		p1DropBtn 	= new JButton("Drop");
		p1EmptyBtn 	= new JButton("Empty");
		p1RenameBtn = new JButton("Rename");
		p1ReindexBtn= new JButton("Reindex");
		p1CopyBtn  	= new JButton("Copy");
		
		p1ExportBtn.addActionListener(btnListener);
		p1DropBtn.addActionListener(btnListener);
		p1EmptyBtn.addActionListener(btnListener);
		p1RenameBtn.addActionListener(btnListener);
		p1ReindexBtn.addActionListener(btnListener);
		p1CopyBtn.addActionListener(btnListener);
		
		opBorder = BorderFactory.createTitledBorder("Operation of tablename");

		Box mbh1 = Box.createHorizontalBox();
		Box mbh2 = Box.createHorizontalBox();
		Box mbh3 = Box.createHorizontalBox();
		
		mbh1.add(Box.createHorizontalStrut(5));
		mbh1.add(p1DropBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p1EmptyBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p1RenameBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p1ReindexBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p1CopyBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p1ExportBtn);
		mbh1.add(Box.createHorizontalGlue());
		

		Box mbv1 = Box.createVerticalBox();
		mbv1.add(Box.createVerticalStrut(10));
		mbv1.add(mbh1);
		mbv1.add(Box.createVerticalStrut(10));
		mbv1.setBorder(opBorder);
		
		mbh2.add(Box.createHorizontalStrut(5));
		mbh2.add(recordsLabel);
		mbh2.add(Box.createHorizontalStrut(35));
		mbh2.add(indexLabel);
		mbh2.add(Box.createHorizontalStrut(35));
		mbh2.add(triggerLabel);
		mbh2.add(Box.createHorizontalStrut(35));
		mbh2.add(Box.createHorizontalGlue());
		
		Box mbv2 = Box.createVerticalBox();
		mbv2.add(Box.createVerticalStrut(10));
		mbv2.add(mbh2);
		mbv2.add(Box.createVerticalStrut(10));
		
		mbv2.setBorder(BorderFactory.createTitledBorder("more about this table"));
		
		mbh3.add(new ScrollPane());
		
		p.add(mbv1, BorderLayout.NORTH);
		p.add(mbh3, BorderLayout.CENTER);
		p.add(mbv2, BorderLayout.SOUTH);
		
	}
	
	private void setPanel2(JPanel p) {
		p.setName(p2Tab);
	}
	
	private void setPanel3(JPanel p) {
		p.setName(p3Tab);
	}
	
	private void setPanel4(JPanel p) {
		p.setName(p4Tab);
	}
	
	private void setPanel5(JPanel p) {
		p.setName(p5Tab);
	}
	
	private class BtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("[Test] Btn clicked");
		}
		
	}
	

}
