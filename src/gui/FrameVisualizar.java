package gui;

import java.awt.HeadlessException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utils.Membro;

/**
 *
 * @author jjaneto
 */
public class FrameVisualizar extends JFrame{
    
    private Membro mbr;
    private int rowStatus;
    private int columnStatus;

    private JButton btSalvar;
    private JButton btCancelar;
    private JButton btEditar;
   
    private JTextField jtfMatAtl;
    private JTextField jtfMatUniversidade;
    private JTextField jtfNome;
    private JTextField jtfCPF;
    private JTextField jtfRG;
    private JTextField jtfOcupacao;
    private JTextField jtfStatus;
    private JTextField jtfMembroDesde;
    
    private JPanel panelBotoes;
    private JPanel panelDados;
    
        public FrameVisualizar(Membro mbr, int rowStatus, int columnStatus) throws HeadlessException {
        this.mbr = mbr;
        this.rowStatus = rowStatus;
        this.columnStatus = columnStatus;
    }
    
    
}
