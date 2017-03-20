package content;

import java.util.HashSet;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import dao.SqliteMasterDao;


public class MWTreeModel extends DefaultTreeModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected EventListenerList listenerList = new EventListenerList();
	
	private DefaultMutableTreeNode root;
	
	public MWTreeModel(TreeNode root) {
		// 允许添加子节点
		super(root, true);
		this.root = (DefaultMutableTreeNode) root;
		addTreeModelListener(new MWTreeModelListener());
	}
	
	public Object getRoot() {
		return root;
	}
	
	/**
	 * 向  root 添加节点
	 * @param data
	 */
	public void insertIntoRoot(Data d) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(d, true);
		
		DefaultMutableTreeNode masterTable 	= new DefaultMutableTreeNode("Master_Table", true);
		DefaultMutableTreeNode sqlite_master = new DefaultMutableTreeNode("sqlite_master", true);

		// 添加 sqlite_master 表中的列
		for(String sm: SqliteMasterDao.COLUMNS) {
			DefaultMutableTreeNode smnode = new DefaultMutableTreeNode(sm, false); 
			sqlite_master.add(smnode);
		}
		
		// 添加 tables
		DefaultMutableTreeNode tables   = new DefaultMutableTreeNode("Tables", true);
		HashSet<SqliteMasterDao> smAllSet = (HashSet<SqliteMasterDao>) d.getMasterSet();
		addSetToNode(smAllSet, "table", tables);
			
		// 添加 Views
		DefaultMutableTreeNode views 	= new DefaultMutableTreeNode("Views", true);
		addSetToNode(smAllSet, "view", views);
		DefaultMutableTreeNode indexes 	= new DefaultMutableTreeNode("Indexes", true);
		addSetToNode(smAllSet, "index", indexes);
		DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Triggers", true);
		addSetToNode(smAllSet, "trigger", triggers);
		
		masterTable.add(sqlite_master);
		addNodeToPar(masterTable, node);
		addNodeToPar(tables, node);
		addNodeToPar(views, node);
		addNodeToPar(indexes, node);
		addNodeToPar(triggers, node);
		
		insertNodeInto(node, root, getChildCount(root));
	}
	
	/**
	 * 向数据库节点添加 3 级节点
	 * 同时更新 3 级节点的名字
	 * @param node  三级节点
	 * @param to 	数据库节点
	 */
	private void addNodeToPar(DefaultMutableTreeNode node, DefaultMutableTreeNode par) {
		par.add(node);
		node.setUserObject(node.getUserObject() + "(" + node.getChildCount() + ")");
	}
	
	private void addSetToNode(HashSet<SqliteMasterDao> set, String type, DefaultMutableTreeNode node) {
		HashSet<SqliteMasterDao> smset = SqliteMasterDao.getSetByType(set, type);
		for(SqliteMasterDao sm: smset) {
			DefaultMutableTreeNode smnode = new DefaultMutableTreeNode(sm.getName(), false);
			node.add(smnode);
		}
	}
	
	
	public void removeFromRoot(Data data) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(data.getName(), true);
		removeNodeFromParent(node);
	}
	
	public class MWTreeModelListener implements TreeModelListener{


		public void treeNodesChanged(TreeModelEvent e) {
			System.out.println("treeNodesChanged" + e.getSource());
			reload( (TreeNode) e.getTreePath() );
		}

		public void treeNodesInserted(TreeModelEvent e) {
//			System.out.println("[Test] Tree Nodes Inserted");
			reload( (TreeNode) e.getTreePath().getLastPathComponent() );
		}

		public void treeNodesRemoved(TreeModelEvent e) {
			reload( (TreeNode) e.getTreePath() );
		}

		public void treeStructureChanged(TreeModelEvent e) {
//			reload( (TreeNode) e.getTreePath() );
		}

	}


}
