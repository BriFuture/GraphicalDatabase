package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import content.Data;
import content.RecordDao;
import content.TableDataModel;
import content.TableStructure;
import content.UITableData;
import dao.IndexDao;
import dao.SqliteMasterDao;
import dao.TableDao;
import dao.TriggerDao;

public class MWTabbedPanel {
	private MainWindow mw;
	private JTabbedPane tabbedPane;
	
	private BtnListener btnListener;
	
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
	private JTable p1table;
	private TableDataModel p1model;
	
	private String p2Tab;
	private TitledBorder opBorder;
	private JLabel p2label;
	private JTextField p2text;
	private JButton p2searchBtn;
	private JButton p2showBtn;
	private JButton p2addBtn;
	private JButton p2duplicateBtn;
	private JButton p2editBtn;
	private JButton p2deleteBtn;
	private JTable p2table;
	private TableDataModel p2model;
	
	
	private String p3Tab;
	
	private String p4Tab;
	
	private String p5Tab;
	private GraphicalPanel p5;
	private Graphics p5graphic;
	
	private Data data;
	private HashSet<SqliteMasterDao> tableset;
	
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
		
		makePanel(1, null);
		makePanel(2, null);
		makePanel(3, null);
		makePanel(4, null);
		p5 = new GraphicalPanel();
		p5.setName(p5Tab);
		tabbedPane.addTab(p5.getName(), null, p5, p5.getName());
		
		tabbedPane.setSelectedIndex(4);
	}
	
	private void makePanel(int n, Icon icon) {
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
//		case 5:
//			setPanel5(p);
//			break;
		}
		tabbedPane.addTab(p.getName(), icon, p, p.getName());
	}

	/**
	 * @return the tabbedPane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public void setCurrentData(Data d) {
		data = d;
		updatePanels(data);
	}
	
	private void updatePanels(Data d) {
		/* 获取相关数据  */
		HashSet<SqliteMasterDao> smset = (HashSet<SqliteMasterDao>) d.getMasterSet();
		tableset = SqliteMasterDao.getSetByType(smset, TableDao.TYPE);
		HashSet<SqliteMasterDao> indexset = SqliteMasterDao.getSetByType(smset, IndexDao.TYPE);
		HashSet<SqliteMasterDao> triggerset = SqliteMasterDao.getSetByType(smset, TriggerDao.TYPE);
		opBorder.setTitle("Operation of " + d.getName());
		recordsLabel.setText("Number of tables:  " + tableset.size());
		indexLabel.setText("Number of indexes:  " + indexset.size());
		triggerLabel.setText("Number of triggers:  " + triggerset.size());
		
		TableStructure ts = new TableStructure();
		ts.setDefaultRecords();
		p1table.setColumnModel(ts.getTc());
		p1model.setTablePattern(ts);
		
		/* p2 */
		UITableData tableData = new UITableData();
		tableData.addColumn("Row ID", String.class);
		for(int i = 0; i < SqliteMasterDao.COLUMNS.length; i++) {
			tableData.addColumn(SqliteMasterDao.COLUMNS[i], String.class);
		}
		int i = 1;
		// 在表格中添加记录
		for(SqliteMasterDao smd : smset) {
			RecordDao record = new RecordDao();
			record.addValue(i);
			record.addValue(smd.getName());
			record.addValue(smd.getType());
			record.addValue(smd.getTbl_name());
			record.addValue(smd.getRootpage());
			record.addValue(smd.getSql());
			tableData.addRecord(record);
			i++;
		}
		p2table.setColumnModel(tableData.getTc());
		p2model.setTablePattern(tableData);
		System.out.println("[Test] p2table: " + p2table.getRowCount() + " rows, " + p2table.getColumnCount() + " columns");
//		for(int j = 0; j < p2table.getRowCount(); j++) {
//			for(int i = 0; i < p2table.getColumnCount(); i++) {
//				System.out.print(" =" + p2table.getValueAt(j, i) + "=" + i + "=  ");
//			}
//			System.out.println();
//		}
//		System.out.println("=============");
//		System.out.println("===p2model " + p2table.getModel().getColumnCount() + " table " + p2table.getColumnCount() );
		
		/* p5 */
		p5.setTableSet(tableset);
		p5.repaint();
	}
	
	/**
	 * 设置 P1 面板, 显示表结构或显示建表语句
	 * @param p
	 */
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
		
		p1table = new UITable();
		p1model = new TableDataModel();
		p1table.setModel(p1model);
//		p1table.setColumnModel(columnModel);
		
		JScrollPane scrollPane = new JScrollPane(p1table);
		p1table.setFillsViewportHeight(true);
		mbh3.add(scrollPane);
		
		Box mbv3 = Box.createVerticalBox();
		mbv3.add(mbh3);
		
		p.add(mbv1, BorderLayout.NORTH);
		p.add(mbv3, BorderLayout.CENTER);
		p.add(mbv2, BorderLayout.SOUTH);
		
	}
	
	/**
	 * 显示表中记录
	 * @param p
	 */
	private void setPanel2(JPanel p) {
		p.setName(p2Tab);
		
		Box mbh1 = Box.createHorizontalBox();
		Box mbh2 = Box.createHorizontalBox();
		Box mbh3 = Box.createHorizontalBox();
		
		p2label = new JLabel("MASTER");
		p2text = new JTextField();
		p2text.setMaximumSize(new Dimension(120, 25));
		p2text.setPreferredSize(new Dimension(70, 25));
		p2text.setMinimumSize(new Dimension(40, 25));
		p2searchBtn = new JButton("Search");
		p2showBtn = new JButton("Show All");
		p2addBtn = new JButton("Add");
		p2duplicateBtn = new JButton("Duplicate");
		p2editBtn = new JButton("Edit");
		p2deleteBtn = new JButton("Delete");
		
		mbh1.add(Box.createHorizontalStrut(5));
		mbh1.add(p2label);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2text);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2searchBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2showBtn);
		mbh1.add(Box.createHorizontalStrut(45));
		mbh1.add(Box.createHorizontalGlue());
		mbh1.add(Box.createHorizontalStrut(45));
		mbh1.add(p2addBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2duplicateBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2editBtn);
		mbh1.add(Box.createHorizontalStrut(15));
		mbh1.add(p2deleteBtn);
		mbh1.add(Box.createHorizontalStrut(10));
		
		p2table = new UITable();
		p2model = new TableDataModel();
		p2table.setModel(p2model);
		
		JScrollPane scrollPane = new JScrollPane(p2table);
		p2table.setFillsViewportHeight(true);
		mbh3.add(scrollPane);
		
		Box mbv1 = Box.createVerticalBox();
		mbv1.add(Box.createVerticalStrut(10));
		mbv1.add(mbh1);
		mbv1.add(Box.createVerticalStrut(10));

		Box mbv2 = Box.createVerticalBox();
		mbv2.add(Box.createVerticalStrut(10));
		mbv2.add(mbh2);
		mbv2.add(Box.createVerticalStrut(10));
		
		Box mbv3 = Box.createVerticalBox();
		mbv3.add(mbh3);
		
		p.add(mbv1, BorderLayout.NORTH);
		p.add(mbv3, BorderLayout.CENTER);
		p.add(mbv2, BorderLayout.SOUTH);
	}
	
	/**
	 * 显示数据库设置
	 * @param p
	 */
	private void setPanel3(JPanel p) {
		p.setName(p3Tab);
	}
	
	private void setPanel4(JPanel p) {
		p.setName(p4Tab);
	}
	
	/**
	 * 以表为单位，进行绘图
	 * @param p
	 */
//	private void setPanel5(JPanel p) {
//		p.setName(p5Tab);
//		
//		p.addComponentListener(new ComponentAdapter() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				graphicDatabase();
//			}
//		});
//	}
	
	private class BtnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
//			System.out.println("[Test] Btn clicked");
//			if(e.getSource().equals(p1ExportBtn)) {
//				openDialog();
//			}
			openDialog( (JButton) e.getSource() );
		}
		
	}
	
	

	/***
	 * 清空所有面板的内容
	 */
	public void clearContent() {
//		p1model
	}
	
	public void openDialog(JButton b) {
		JDialog dialog = new JDialog(mw, b.getActionCommand());
		dialog.setSize(200, 200);
		BaseWindow.setComponentCenter(dialog, mw);
		dialog.add(new JLabel( b.getActionCommand() ));
//		mw.setFocusableWindowState(false);
//		int result = dialog.show
//		if(dialog.get)
	}
	

}
