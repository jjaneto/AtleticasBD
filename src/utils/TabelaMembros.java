package utils;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jjaneto
 */
public class TabelaMembros extends AbstractTableModel{
    
    public String colunas[] = {"Mat. Atlética",
                               "Nome",
                               "Contato",
                               "Curso",
                               "Status"};
    
    ArrayList<Membro> arrRows;

    public TabelaMembros() {
        arrRows = new ArrayList<>();
    }   

    public TabelaMembros(ArrayList<Membro> arrRows) {
        this.arrRows = arrRows;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
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
    public String getColumnName(int columnIndex){
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Membro mbr = arrRows.get(rowIndex);
        
        switch(columnIndex){
            case 0:
                return mbr.getMatricula_atletica();
            case 1:
                return mbr.getNome();
            case 2:
                return mbr.getTelefone(); //MUDAR DEPOIS
            case 3:
                return mbr.getCurso();
            case 4:
                return mbr.getStatus();
        }
        
        return null;
    }
    
    public void addMembro(Membro mbr){
        this.arrRows.add(mbr);
        fireTableDataChanged();
    }
    
}
