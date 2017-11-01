package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utils.Membro;
import utils.FrameInterativo;

/**
 *
 * @author jjaneto
 */
public final class FrameVisualizar extends JFrame implements FrameInterativo {

    /**
     * Variáveis do Membro passado como parâmetro no construtor.
     */
    private Membro mbr;
    private int rowStatus;
    private int columnStatus;

    /**
     * Panels que compõem esse JFrame.
     */
    private JPanel panelBotoes;
    private JPanel panelDados;

    /**
     * Botões usados nesse JFrame.
     */
    private JButton btSalvar;
    private JButton btFechar;
    private JButton btEditar;

    /**
     * Todos os campos de digitação, e seus respectivos labels.
     */
    private JTextField jtfMatAtl;
    private JTextField jtfMatUniversidade;
    private JTextField jtfNome;
    private JTextField jtfCPF;
    private JTextField jtfRG;
    private JTextField jtfOcupacao;
    private JTextField jtfStatus;    
    private JTextField jtfEmail;    
    private JTextField jtfCurso;
    private JTextField jtfMembroDesde;
    private JTextField jtfNascimento;
    private JTextField jtfTelefone;

    private JLabel lbMatAtl;
    private JLabel lbMatUni;
    private JLabel lbNome;
    private JLabel lbCPF;
    private JLabel lbRG;
    private JLabel lbOcupacao;
    private JLabel lbStatus;
    private JLabel lbMembroDesde;
    private JLabel lbNascimento;
    private JLabel lbEmail;
    private JLabel lbelefone;
    private JLabel lbCurso;

    /**
     * Array para auxiliar troca de editable.
     */
    private JTextField arrTextField[];

    public FrameVisualizar(Membro mbr, int rowStatus, int columnStatus) throws HeadlessException {
        super("Tela do membro " + mbr.getMatricula_atletica());
        this.mbr = mbr;
        this.rowStatus = rowStatus;
        this.columnStatus = columnStatus;

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        instanciaEAdicionaVariaveis();
        atribuiListeners();
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        btFechar.requestFocus();
    }

    private void setEditableTextFields(boolean answer) {
        for (JTextField x : arrTextField) {
            x.setEditable(answer);
        }
    }

    @Override
    public void instanciaEAdicionaVariaveis() {
        /**
         * Panel Inferior.
         */
        btSalvar = new JButton("Salvar alterações", new ImageIcon("./img/save.png"));
        btSalvar.setEnabled(false);
        btEditar = new JButton("Editar membro", new ImageIcon("./img/edit.png"));
        btFechar = new JButton("Fechar", new ImageIcon("./img/cancel.png"));

        panelBotoes = new JPanel(new GridLayout(1, 3, 1, 1));
        panelBotoes.add(btSalvar);
        panelBotoes.add(btEditar);
        panelBotoes.add(btFechar);
        this.add(panelBotoes, BorderLayout.SOUTH);
        
        lbMatAtl = new JLabel("Matrícula da Atlética: ");
        jtfMatAtl = new JTextField(mbr.getMatricula_atletica());
        
        lbMatUni = new JLabel("Matrícula da Universidade: ");
        jtfMatUniversidade = new JTextField(mbr.getMatricula_universidade());
        
        lbNome = new JLabel("Nome: ");
        jtfNome = new JTextField(mbr.getNome());
        
        lbCPF = new JLabel("CPF: ");
        jtfCPF = new JTextField(mbr.getCPF());
        
        lbRG = new JLabel("RG: ");
        jtfRG = new JTextField(mbr.getRG());
        
        lbOcupacao = new JLabel("Ocupação: ");
        jtfOcupacao = new JTextField(mbr.getOcupacao());
        
        lbStatus = new JLabel("Status: ");
        jtfStatus = new JTextField(mbr.getStatus());
        
        panelDados = new JPanel(new MigLayout("fillx"));
        panelDados.add(lbMatAtl, "split 2");
        panelDados.add(jtfMatAtl, "growx, wrap");
        panelDados.add(lbMatUni, "split 2");
        panelDados.add(jtfMatUniversidade, "growx, wrap");
        panelDados.add(lbNome, "split 2");
        panelDados.add(jtfNome, "growx, wrap");
        panelDados.add(lbCPF, "split 2");
        panelDados.add(jtfCPF, "growx, wrap");
        panelDados.add(lbRG, "split 2");
        panelDados.add(jtfRG, "growx, wrap");
        panelDados.add(lbOcupacao, "split 2");
        panelDados.add(jtfOcupacao, "growx, wrap");
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

    @Override
    public void atribuiListeners() {

        btSalvar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

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
    }

}
