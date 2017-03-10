package content;

import java.util.HashSet;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;


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
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(d.getName(), true);
		
		DefaultMutableTreeNode masterTable 	= new DefaultMutableTreeNode("Master_Table", true);
		DefaultMutableTreeNode sqlite_master = new DefaultMutableTreeNode("sqlite_master", true);

		// 添加 sqlite_master 表中的列
		for(String sm: SqliteMaster.COLUMNS) {
			DefaultMutableTreeNode smnode = new DefaultMutableTreeNode(sm, false); 
			sqlite_master.add(smnode);
		}
		
		HashSet<SqliteMaster> smset = (HashSet<SqliteMaster>) d.getTables();
		
		d.closeState();

		DefaultMutableTreeNode tables   = new DefaultMutableTreeNode("Tables(" + smset.size() + ")", true);
		for(SqliteMaster sm: smset) {
			DefaultMutableTreeNode smnode = new DefaultMutableTreeNode(sm.getName(), false);
			tables.add(smnode);
		}
			
		DefaultMutableTreeNode views 	= new DefaultMutableTreeNode("Vables", true);
		DefaultMutableTreeNode indexes 	= new DefaultMutableTreeNode("Indexes", true);
		DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Triggers", true);
		
		node.add(masterTable);
		masterTable.add(sqlite_master);
		node.add(tables);
		node.add(views);
		node.add(indexes);
		node.add(triggers);
		
		insertNodeInto(node, root, getChildCount(root));
	}
	
	
	public void removeFromRoot(Data data) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(data.getName(), true);
		removeNodeFromParent(node);
	}
	
	public class MWTreeModelListener implements TreeModelListener{


		public void treeNodesChanged(TreeModelEvent e) {
			System.out.println("treeNodesChanged" + e.getSource());
			
		}

		public void treeNodesInserted(TreeModelEvent e) {
//			System.out.println("[Test] Tree Nodes Inserted");
			reload( (TreeNode) e.getTreePath().getLastPathComponent() );
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}

	}


}
