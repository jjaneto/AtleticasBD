package utils;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jjaneto
 */
public class TabelaMembros extends AbstractTableModel{
    
    public String colunas[] = {"Matricula Atlética",
                               "Nome",
                               "Curso",
                               "Status"};
    
    ArrayList<Membro> arrRows;

    public TabelaMembros() {
        arrRows = new ArrayList<>();
    }   

    @Override
    public int getRowCount() {
        return arrRows.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        return null;
    }
    
}
