package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utils.Membro;

/**
 *
 * @author jjaneto
 */
public class FrameVisualizar extends JFrame {

    private Membro mbr;
    private int rowStatus;
    private int columnStatus;

    private JButton btSalvar;
    private JButton btFechar;
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

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Tela do membro " + mbr.getMatricula_atletica());
        constroiEAdicionaPainelBotoes();
        constroiEAdcionaPainelDados();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);        
    }

    private void constroiEAdicionaPainelBotoes() {
        btSalvar = new JButton("Salvar alterações", new ImageIcon("./img/save.png"));
        btSalvar.setEnabled(false);
        btSalvar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            
            }
        });
        
        btEditar = new JButton("Editar membro", new ImageIcon("./img/edit.png"));
        btEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!btSalvar.isEnabled()){
                    btSalvar.setEnabled(true);
                }
            }
        });
        
        btFechar = new JButton("Fechar", new ImageIcon("./img/cancel.png"));
        btFechar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(btSalvar.isEnabled()){
                    int clicked = JOptionPane.showConfirmDialog(getContentPane(),
                            "Você tem certeza que deseja sair? Quaisquer "
                                    + "Alterações não salvas serão perdidas!");
                    
                    if(clicked == JOptionPane.OK_OPTION)
                        dispose();
                    
                }else dispose();
            }
        });
        
        panelBotoes = new JPanel(new GridLayout(1, 3, 1, 1));
        panelBotoes.add(btSalvar);
        panelBotoes.add(btEditar);
        panelBotoes.add(btFechar);
        this.add(panelBotoes, BorderLayout.SOUTH);
    }

    private void constroiEAdcionaPainelDados() {
        panelDados = new JPanel(new MigLayout());
    }

}
