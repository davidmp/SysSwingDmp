
package pe.com.syscenterlife.jtablecomp;

import java.awt.Component;

import javax.swing.AbstractCellEditor;


import javax.swing.JTable;
import javax.swing.table.TableCellEditor;


public class ButtonsEditor extends AbstractCellEditor implements TableCellEditor {

    protected final ButtonsPanel panel =new ButtonsPanel();
    protected final JTable table;
    
    public ButtonsEditor(JTable table) {
        super();
        this.table = table;
        for (int i = 0; i < ButtonsPanel.metaDataButtons.length; i++) {
            panel.buttons.get(i);
        }
    }

    @Override
    public Component getTableCellEditorComponent(JTable tbl, Object value, boolean isSelected, int row, int column) {        
        return panel;
    }

    @Override
    public ButtonsPanel getCellEditorValue() {                
        return panel;
    }
}


