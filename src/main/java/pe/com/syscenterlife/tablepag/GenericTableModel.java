
package pe.com.syscenterlife.tablepag;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public abstract class GenericTableModel<T> extends AbstractTableModel {
      
    private List<T> objectRows = new ArrayList<>();
    
    public GenericTableModel(List<T> data){
        this.objectRows=data;
    }
    
    @Override
    public int getRowCount() {
        return objectRows.size();
    }
    public List<T> getObjectRows() {
        return objectRows;
    }     
    public void setObjectRows(List<T> objectRows) {
        this.objectRows = objectRows;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        return getValueAt(t, columnIndex);        
    }
    
    public abstract Object getValueAt(T t, int columnIndex);
    
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
    @Override
    public void setValueAt(Object value, int row, int col) {
    } 

    public abstract boolean isCellEditable(T t,int column);

    @Override
    public abstract String getColumnName(int column);
    
    
    public Object getRow(int row) { // usado para paintForm
        this.fireTableRowsUpdated(row, row);
        return objectRows.get(row);
    }    
    
    public void addRow(T d) { // con db no se usa
        objectRows.add(d);
        //this.fireTableDataChanged();
        this.fireTableRowsInserted(objectRows.size(), objectRows.size());
    }    
    
    public void removeRow(int linha) { // con db no se usa
        this.objectRows.remove(linha);
        this.fireTableRowsDeleted(linha, linha);
    }    
}
