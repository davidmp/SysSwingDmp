package pe.com.syscenterlife.tablepag;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectTableModel<T> extends AbstractTableModel {
    private List<T> objectRows = new ArrayList<>();

    public List<T> getObjectRows() {
        return objectRows;
    }

    public void setObjectRows(List<T> objectRows) {
        this.objectRows = objectRows;
    }

    @Override
    public int getRowCount() {
        return objectRows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        return getValueAt(t, columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (objectRows.isEmpty()) {
            return Object.class;
        }
        Object valueAt = getValueAt(0, columnIndex);
        return valueAt != null ? valueAt.getClass() : Object.class;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        T t=objectRows.get(row);
        return isCellEditable(t, column);
    }
    
    public abstract Object getValueAt(T t, int columnIndex);
    
    @Override
    public void setValueAt(Object value, int row, int col) {
       
    }    
    
    public abstract boolean isCellEditable(T t,int column);

    @Override
    public abstract String getColumnName(int column);
}