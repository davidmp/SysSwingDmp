
package pe.com.syscenterlife.jtablecomp;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonsRenderer implements TableCellRenderer {
    private final ButtonsPanel panel = new ButtonsPanel() {
      @Override public void updateUI() {
        super.updateUI();
        setName("Table.cellRenderer");
      }
    };


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return panel;
    }
}