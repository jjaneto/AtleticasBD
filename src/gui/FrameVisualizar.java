package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    private JTextField jtfNascimento;
    private JTextField jtfEmail;
    private JTextField jtfTelefone;
    private JTextField jtfCurso;

    private JTextField arrTextField[];

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
        btFechar.requestFocus();
        
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
                if (!btSalvar.isEnabled()) {
                    btSalvar.setEnabled(true);
                    setEditableTextFields(true);
                    jtfMatUniversidade.requestFocus();
                }
            }
        });

        btFechar = new JButton("Fechar", new ImageIcon("./img/cancel.png"));
        btFechar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (btSalvar.isEnabled()) {
                    int clicked = JOptionPane.showConfirmDialog(getContentPane(),
                            "Você tem certeza que deseja sair? Quaisquer "
                            + "Alterações não salvas serão perdidas!");

                    if (clicked == JOptionPane.OK_OPTION) {
                        dispose();
                    }

                } else {
                    dispose();
                }
            }
        });

        panelBotoes = new JPanel(new GridLayout(1, 3, 1, 1));
        panelBotoes.add(btSalvar);
        panelBotoes.add(btEditar);
        panelBotoes.add(btFechar);
        this.add(panelBotoes, BorderLayout.SOUTH);
    }

    private void constroiEAdcionaPainelDados() {
        panelDados = new JPanel(new MigLayout("fillx"));

        JLabel lbMatAtl = new JLabel("Matrícula da Atlética: ");
        jtfMatAtl = new JTextField(mbr.getMatricula_atletica());
        panelDados.add(lbMatAtl, "split 2");
        panelDados.add(jtfMatAtl, "growx, wrap");

        JLabel lbMatUni = new JLabel("Matrícula da Universidade: ");
        jtfMatUniversidade = new JTextField(mbr.getMatricula_universidade());
        panelDados.add(lbMatUni, "split 2");
        panelDados.add(jtfMatUniversidade, "growx, wrap");

        JLabel lbNome = new JLabel("Nome: ");
        jtfNome = new JTextField(mbr.getNome());
        panelDados.add(lbNome, "split 2");
        panelDados.add(jtfNome, "growx, wrap");

        JLabel lbCPF = new JLabel("CPF: ");
        jtfCPF = new JTextField(mbr.getCPF());
        panelDados.add(lbCPF, "split 2");
        panelDados.add(jtfCPF, "growx, wrap");

        JLabel lbRG = new JLabel("RG: ");
        jtfRG = new JTextField(mbr.getRG());
        panelDados.add(lbRG, "split 2");
        panelDados.add(jtfRG, "growx, wrap");

        JLabel lbOcupacao = new JLabel("Ocupação: ");
        jtfOcupacao = new JTextField(mbr.getOcupacao());
        panelDados.add(lbOcupacao, "split 2");
        panelDados.add(jtfOcupacao, "growx, wrap");

        JLabel lbStatus = new JLabel("Status: ");
        jtfStatus = new JTextField(mbr.getStatus());
        panelDados.add(lbStatus, "split 2");
        panelDados.add(jtfStatus, "growx");
        

        arrTextField = new JTextField[]{
            jtfMatAtl,
            jtfMatUniversidade,
            jtfNome,
            jtfCPF,
            jtfRG,
            jtfOcupacao,
            jtfStatus};
        
        setEditableTextFields(false);
        
        this.add(panelDados, BorderLayout.NORTH);

    }

    private void setEditableTextFields(boolean answer) {
        for(JTextField x : arrTextField)
            x.setEditable(answer);
    }

}
