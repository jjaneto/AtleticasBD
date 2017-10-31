package utils;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author jjaneto
 */
public class RendererMembros extends DefaultTableCellRenderer implements TableCellRenderer{

    public RendererMembros() {
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        setBackground(null);
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(String.valueOf(value));
        if(row % 2 == 0){
            this.setBackground(Color.WHITE);
        }else{
            this.setBackground(Color.LIGHT_GRAY);
        }
        return this;
    }
    
}
