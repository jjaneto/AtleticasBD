package utils;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jjaneto
 */
public class TabelaMembros extends AbstractTableModel {

    public String colunas[] = {"Mat. Atlética",
        "Nome",
        "Contato",
        "Curso",
        "Status"};

    private boolean isAux = false;

    ArrayList<Membro> arrRows;
    ArrayList<Membro> arrAux;

    public TabelaMembros() {
        arrRows = new ArrayList<>();
        this.arrAux = new ArrayList<>();
    }

    public TabelaMembros(ArrayList<Membro> arrRows, ArrayList<Membro> arrAux) {
        this.arrRows = arrRows;
        this.arrAux = arrAux;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getRowCount() {
        if (isAux) {
            return arrAux.size();
        } else {
            return arrRows.size();
        }

//        return arrRows.size();
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Membro mbr;
        if (isAux) {
            mbr = arrAux.get(rowIndex);
        } else {
            mbr = arrRows.get(rowIndex);
        }
//        Membro mbr = arrRows.get(rowIndex);

        switch (columnIndex) {
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

    public void addMembro(Membro mbr) {
        this.arrRows.add(mbr);
        fireTableRowsInserted(arrRows.size() - 1, arrRows.size());
    }

    public void removeMembro(Membro mbr) {
        this.arrRows.remove(mbr);
        fireTableDataChanged();
    }

    public void trocaArrayParaAuxiliar() {
        if (!isAux) {
            isAux = true;
            fireTableDataChanged();
        }
    }
    
    public void trocaAuxiliarParaArray(){
        if(isAux){
            isAux = false;
            fireTableDataChanged();
        }
    }
    
    public void addMembroArrAuxiliar(Membro mbr){
        this.arrAux.add(mbr);
        fireTableRowsInserted(arrAux.size() - 1, arrAux.size());
    }
    
    public void limpaArrayPrincipal(){
        this.arrRows.clear();
    }
    
    public void limpaArrayAux(){
        this.arrAux.clear();
    }

}
