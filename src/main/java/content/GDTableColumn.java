package content;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GDTableColumn implements TableColumnModel,
                        PropertyChangeListener, ListSelectionListener, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//
// Instance Variables
//
    /** Array of TableColumn objects in this model */
    protected ArrayList<TableColumn> tableColumns;

    /** Model for keeping track of column selections */
    protected ListSelectionModel selectionModel;

    /** Width margin between each column */
    protected int columnMargin;

    /** List of TableColumnModelListener */
    protected EventListenerList listenerList = new EventListenerList();

    /** Change event (only one needed) */
    transient protected ChangeEvent changeEvent = null;

    /** Column selection allowed in this column model */
    protected boolean columnSelectionAllowed;

    /** A local cache of the combined width of all columns */
    protected int totalColumnWidth;
    

//
// Constructors
//
    /**
     * Creates a default table column model.
     */
    public GDTableColumn() {

        // Initialize local ivars to default
        tableColumns = new ArrayList<TableColumn>();
        setSelectionModel(createSelectionModel());
        setColumnMargin(2);
        invalidateWidthCache();
        setColumnSelectionAllowed(false);
    }

//
// Modifying the model
//
    public void addColumn(TableColumn aColumn) {
        if (aColumn == null) {
            throw new IllegalArgumentException("Object is null");
        }

        tableColumns.add(aColumn);
        aColumn.addPropertyChangeListener(this);
        invalidateWidthCache();

        // Post columnAdded event notification
        fireColumnAdded(new TableColumnModelEvent(this, 0,
                                                  getColumnCount() - 1));
    }
    
    public void addColumn(String columnName, Class<?> classType) {
    	TableColumn tc = new TableColumn();
    	tc.setHeaderValue(columnName);
    	addColumn(tc);
    }

    public void removeColumn(TableColumn column) {
        int columnIndex = tableColumns.indexOf(column);

        if (columnIndex != -1) {
            // Adjust for the selection
            if (selectionModel != null) {
                selectionModel.removeIndexInterval(columnIndex,columnIndex);
            }

            column.removePropertyChangeListener(this);
            tableColumns.remove(columnIndex);
            invalidateWidthCache();

            // Post columnAdded event notification.  (JTable and JTableHeader
            // listens so they can adjust size and redraw)
            fireColumnRemoved(new TableColumnModelEvent(this,
                                           columnIndex, 0));
        }
    }

    public void moveColumn(int columnIndex, int newIndex) {
        if ((columnIndex < 0) || (columnIndex >= getColumnCount()) ||
            (newIndex < 0) || (newIndex >= getColumnCount()))
            throw new IllegalArgumentException("moveColumn() - Index out of range");

        TableColumn aColumn;

        if (columnIndex == newIndex) {
            fireColumnMoved(new TableColumnModelEvent(this, columnIndex, newIndex));
            return;
        }
        aColumn = tableColumns.get(columnIndex);

        tableColumns.remove(columnIndex);
        boolean selected = selectionModel.isSelectedIndex(columnIndex);
        selectionModel.removeIndexInterval(columnIndex,columnIndex);

        tableColumns.add(newIndex, aColumn);
        selectionModel.insertIndexInterval(newIndex, 1, true);
        if (selected) {
            selectionModel.addSelectionInterval(newIndex, newIndex);
        }
        else {
            selectionModel.removeSelectionInterval(newIndex, newIndex);
        }

        fireColumnMoved(new TableColumnModelEvent(this, columnIndex,
                                                               newIndex));
    }

    public void setColumnMargin(int newMargin) {
        if (newMargin != columnMargin) {
            columnMargin = newMargin;
            // Post columnMarginChanged event notification.
            fireColumnMarginChanged();
        }
    }

//
// Querying the model
//
    @Override
    public int getColumnCount() {
        return tableColumns.size();
    }

	@Override
	public Enumeration<TableColumn> getColumns() {
		return Collections.enumeration(tableColumns);
	}
	
    public ArrayList<TableColumn> getColumnList() {
    	return tableColumns;
    }

    @Override
    public int getColumnIndex(Object identifier) {
        if (identifier == null) {
            throw new IllegalArgumentException("Identifier is null");
        }

//        Enumeration enumeration = getColumns();
        ArrayList<TableColumn> columns = tableColumns;
        int index = 0;

        for(TableColumn tc: columns) {
//            // Compare them this way in case the column's identifier is null.
        	if( identifier.equals(tc.getIdentifier()) ) {
        		return index;
        	}
        	index++;
        }
        throw new IllegalArgumentException("Identifier not found");
    }

    @Override
    public TableColumn getColumn(int columnIndex) {
        return tableColumns.get(columnIndex);
    }
    

    @Override
    public int getColumnMargin() {
        return columnMargin;
    }

    @Override
    public int getColumnIndexAtX(int x) {
        if (x < 0) {
            return -1;
        }
        int cc = getColumnCount();
        for(int column = 0; column < cc; column++) {
            x = x - getColumn(column).getWidth();
            if (x < 0) {
                return column;
            }
        }
        return -1;
    }

    /**
     * Returns the total combined width of all columns.
     * @return the <code>totalColumnWidth</code> property
     */
    public int getTotalColumnWidth() {
        if (totalColumnWidth == -1) {
            recalcWidthCache();
        }
        return totalColumnWidth;
    }

//
// Selection model
//

    /**
     *  Sets the selection model for this <code>TableColumnModel</code>
     *  to <code>newModel</code>
     *  and registers for listener notifications from the new selection
     *  model.  If <code>newModel</code> is <code>null</code>,
     *  an exception is thrown.
     *
     * @param   newModel        the new selection model
     * @exception IllegalArgumentException      if <code>newModel</code>
     *                                          is <code>null</code>
     * @see     #getSelectionModel
     */
    public void setSelectionModel(ListSelectionModel newModel) {
        if (newModel == null) {
            throw new IllegalArgumentException("Cannot set a null SelectionModel");
        }

        ListSelectionModel oldModel = selectionModel;

        if (newModel != oldModel) {
            if (oldModel != null) {
                oldModel.removeListSelectionListener(this);
            }

            selectionModel= newModel;
            newModel.addListSelectionListener(this);
        }
    }

    /**
     * Returns the <code>ListSelectionModel</code> that is used to
     * maintain column selection state.
     *
     * @return  the object that provides column selection state.  Or
     *          <code>null</code> if row selection is not allowed.
     * @see     #setSelectionModel
     */
    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    // implements javax.swing.table.TableColumnModel
    /**
     * Sets whether column selection is allowed.  The default is false.
     * @param  flag true if column selection will be allowed, false otherwise
     */
    public void setColumnSelectionAllowed(boolean flag) {
        columnSelectionAllowed = flag;
    }

    // implements javax.swing.table.TableColumnModel
    /**
     * Returns true if column selection is allowed, otherwise false.
     * The default is false.
     * @return the <code>columnSelectionAllowed</code> property
     */
    public boolean getColumnSelectionAllowed() {
        return columnSelectionAllowed;
    }

    // implements javax.swing.table.TableColumnModel
    /**
     * Returns an array of selected columns.  If <code>selectionModel</code>
     * is <code>null</code>, returns an empty array.
     * @return an array of selected columns or an empty array if nothing
     *                  is selected or the <code>selectionModel</code> is
     *                  <code>null</code>
     */
    public int[] getSelectedColumns() {
        if (selectionModel != null) {
            int iMin = selectionModel.getMinSelectionIndex();
            int iMax = selectionModel.getMaxSelectionIndex();

            if ((iMin == -1) || (iMax == -1)) {
                return new int[0];
            }

            int[] rvTmp = new int[1+ (iMax - iMin)];
            int n = 0;
            for(int i = iMin; i <= iMax; i++) {
                if (selectionModel.isSelectedIndex(i)) {
                    rvTmp[n++] = i;
                }
            }
            int[] rv = new int[n];
            System.arraycopy(rvTmp, 0, rv, 0, n);
            return rv;
        }
        return  new int[0];
    }

    // implements javax.swing.table.TableColumnModel
    /**
     * Returns the number of columns selected.
     * @return the number of columns selected
     */
    public int getSelectedColumnCount() {
        if (selectionModel != null) {
            int iMin = selectionModel.getMinSelectionIndex();
            int iMax = selectionModel.getMaxSelectionIndex();
            int count = 0;

            for(int i = iMin; i <= iMax; i++) {
                if (selectionModel.isSelectedIndex(i)) {
                    count++;
                }
            }
            return count;
        }
        return 0;
    }

//
// Listener Support Methods
//

    // implements javax.swing.table.TableColumnModel
    /**
     * Adds a listener for table column model events.
     * @param x  a <code>TableColumnModelListener</code> object
     */
    public void addColumnModelListener(TableColumnModelListener x) {
        listenerList.add(TableColumnModelListener.class, x);
    }

    // implements javax.swing.table.TableColumnModel
    /**
     * Removes a listener for table column model events.
     * @param x  a <code>TableColumnModelListener</code> object
     */
    public void removeColumnModelListener(TableColumnModelListener x) {
        listenerList.remove(TableColumnModelListener.class, x);
    }

    public TableColumnModelListener[] getColumnModelListeners() {
        return listenerList.getListeners(TableColumnModelListener.class);
    }

//
//   Event firing methods
//

    protected void fireColumnAdded(TableColumnModelEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableColumnModelListener.class) {
                // Lazily create the event:
                // if (e == null)
                //  e = new ChangeEvent(this);
                ((TableColumnModelListener)listeners[i+1]).
                    columnAdded(e);
            }
        }
    }

    protected void fireColumnRemoved(TableColumnModelEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableColumnModelListener.class) {
                // Lazily create the event:
                // if (e == null)
                //  e = new ChangeEvent(this);
                ((TableColumnModelListener)listeners[i+1]).
                    columnRemoved(e);
            }
        }
    }

    protected void fireColumnMoved(TableColumnModelEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableColumnModelListener.class) {
                // Lazily create the event:
                // if (e == null)
                //  e = new ChangeEvent(this);
                ((TableColumnModelListener)listeners[i+1]).
                    columnMoved(e);
            }
        }
    }

    protected void fireColumnSelectionChanged(ListSelectionEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableColumnModelListener.class) {
                // Lazily create the event:
                // if (e == null)
                //  e = new ChangeEvent(this);
                ((TableColumnModelListener)listeners[i+1]).
                    columnSelectionChanged(e);
            }
        }
    }

    protected void fireColumnMarginChanged() {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TableColumnModelListener.class) {
                // Lazily create the event:
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((TableColumnModelListener)listeners[i+1]).
                    columnMarginChanged(changeEvent);
            }
        }
    }

    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {
        return listenerList.getListeners(listenerType);
    }

//
// Implementing the PropertyChangeListener interface
//

    // PENDING(alan)
    // implements java.beans.PropertyChangeListener
    /**
     * Property Change Listener change method.  Used to track changes
     * to the column width or preferred column width.
     *
     * @param  evt  <code>PropertyChangeEvent</code>
     */
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();

        if (name == "width" || name == "preferredWidth") {
            invalidateWidthCache();
            // This is a misnomer, we're using this method
            // simply to cause a relayout.
            fireColumnMarginChanged();
        }

    }

    public void valueChanged(ListSelectionEvent e) {
        fireColumnSelectionChanged(e);
    }

//
// Protected Methods
//

    /**
     * Creates a new default list selection model.
     */
    protected ListSelectionModel createSelectionModel() {
        return new DefaultListSelectionModel();
    }

    protected void recalcWidthCache() {
    	totalColumnWidth = 0;
    	for(TableColumn tc : tableColumns) {
    		totalColumnWidth += tc.getWidth();
    	}
    }

    private void invalidateWidthCache() {
        totalColumnWidth = -1;
    }

	public String getColumnName(int columnIndex) {
		return tableColumns.get(columnIndex).getHeaderValue().toString();
	}



}
