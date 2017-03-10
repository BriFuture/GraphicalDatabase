package ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import content.Data;
import content.MWTreeModel;

/**
 * 负责主页面的树状图
 * @author future
 *
 */
public class MWTree {
	private MainWindow mw;
	private JTree tree;
	private MouseListener mlistener;
	private MWTreeModel treeModel;
	
	// 默认的根节点，添加数据库文件后隐藏
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Databases");
	
	
	public MWTree(MainWindow mw) {
		this.mw = mw;
		initTree();
	}
	
	private void initTree() {
		treeModel = new MWTreeModel(rootNode);
		tree = new JTree(treeModel);
		
		mlistener = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				int selRow = tree.getRowForLocation(e.getX(), e.getY());
				TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
				
				if(selRow != -1) {
					if ( selPath.getLastPathComponent().equals(rootNode) && e.getClickCount() == 2 ) {
						mw.connectDatabase();
					} 
				}
//				System.out.println("Tree selected!" +  selPath.getLastPathComponent().equals(rootNode));
			}
			
		};
		
		tree.addMouseListener(mlistener);
	}

	/**
	 * @return the tree
	 */
	public JTree getTree() {
		return tree;
	}
	
	public void insertRoot(Data d) {
		treeModel.insertIntoRoot(d);
	}
}
