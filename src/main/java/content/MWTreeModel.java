package content;

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
	DefaultMutableTreeNode masterTable = new DefaultMutableTreeNode("Master_Table", true);
	DefaultMutableTreeNode tables = new DefaultMutableTreeNode("Tables", true);
	DefaultMutableTreeNode views = new DefaultMutableTreeNode("Vables", true);
	DefaultMutableTreeNode indexes = new DefaultMutableTreeNode("Indexes", true);
	DefaultMutableTreeNode triggers = new DefaultMutableTreeNode("Triggers", true);
	
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
	 * 向 root 添加节点
	 * @param data
	 */
	public void insertIntoRoot(Data data) {
//		data.get
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(data.getName(), true);
		node.add(masterTable);
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
			System.out.println(e.getSource());
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
