package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import net.miginfocom.swing.MigLayout;
import utils.BancoControle;
import utils.Membro;
import utils.TabelaMembros;
import utils.FrameInterativo;

/**
 *
 * @author jjaneto
 */
public final class FrameAdicionar extends JFrame implements FrameInterativo {

    /**
     * Panels que compõem este JFrame.
     */
    private JPanel panelPrincipal;
    private JPanel panelBotoes;

    /**
     * Todos os campos de digitação com seus respectivos labels.
     */
    private JTextField jtfMatAtletica;
    private JTextField jtfMatUniversidade;
    private JTextField jtfNome;
    private JTextField jtfRG;
    private JTextField jtfOcupacao;
    private JTextField jtfStatus;
    private JTextField jtfEmail;
    private JTextField jtfCurso;
    private JFormattedTextField jtfNascimento;
    private JFormattedTextField jtfCPF;
    private JFormattedTextField jtfTelefone;

    private JLabel lbMatAtletica;
    private JLabel lbMatUniversidade;
    private JLabel lbNome;
    private JLabel lbCPF;
    private JLabel lbRG;
    private JLabel lbOcupacao;
    private JLabel lbStatus;
    private JLabel lbEmail;
    private JLabel lbTelefone;
    private JLabel lbCurso;
    private JLabel lbNascimento;

    private MaskFormatter maskCPF;
    private MaskFormatter maskTelefone;
    private MaskFormatter maskNascimento;
    /**
     * Botões.
     */
    private JButton btOk;
    private JButton btCancelar;

    /**
     * Tabela principal.
     */
    private TabelaMembros model;

    /**
     * A próxima matrícula (última + 1).
     */
    private int novaMatricula;

    public FrameAdicionar(TabelaMembros model) throws HeadlessException {
        super("Adicionar Membro");
        setLayout(new BorderLayout());
        this.model = model;
        novaMatricula = Integer.valueOf(BancoControle.getMaxMatriculaAtletica());

        instanciaEAdicionaVariaveis();
        atribuiListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 280));
        setLocationRelativeTo(null);
        setVisible(true);
        btCancelar.requestFocus();
    }

    @Override
    public void instanciaEAdicionaVariaveis() {

        /**
         * Panel dos botoes.
         */
        btOk = new JButton("Adicionar");
        btOk.setIcon(new ImageIcon("./img/ok.png"));
        btCancelar = new JButton("Cancelar");
        btCancelar.setIcon(new ImageIcon("./img/cancel.png"));

        try {
            maskCPF = new MaskFormatter("###.###.###-##");
            maskTelefone = new MaskFormatter("(##)#####-####");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        
        lbNascimento = new JLabel("Nascimento: ");
        jtfNascimento = new JFormattedTextField(maskNascimento);
        lbTelefone = new JLabel("Telefone: ");
        jtfTelefone = new JFormattedTextField(maskTelefone);
        lbCurso = new JLabel("Curso: ");
        jtfCurso = new JTextField();
        lbEmail = new JLabel("Email: ");
        jtfEmail = new JTextField();        
        lbMatUniversidade = new JLabel("Matrícula da Universidade: ");
        jtfMatUniversidade = new JTextField();
        lbNome = new JLabel("Nome: ");
        jtfNome = new JTextField();
        lbCPF = new JLabel("CPF: ");
        jtfCPF = new JFormattedTextField(maskCPF);
        lbRG = new JLabel("RG: ");
        jtfRG = new JTextField();
        lbOcupacao = new JLabel("Ocupação: ");
        jtfOcupacao = new JTextField();
        lbStatus = new JLabel("Status: ");
        jtfStatus = new JTextField();
        lbMatAtletica = new JLabel("Matrícula da Atlética: ");
        jtfMatAtletica = new JTextField();
        jtfMatAtletica.setText(String.valueOf(novaMatricula + 1));
        jtfMatAtletica.setEditable(false);
        jtfMatAtletica.setToolTipText("Matrícula escolhida automaticamente. "
                + "Para editar manualmente, consulte o administrador.");

        /**
         * Panel dos botões.
         */
        panelBotoes = new JPanel(new GridLayout(1, 2, 1, 1));
        panelBotoes.add(btCancelar);
        panelBotoes.add(btOk);
        this.add(panelBotoes, BorderLayout.SOUTH);

        /**
         * Panel dos dados do membro.
         */
        panelPrincipal = new JPanel(new MigLayout("fillx"));
        panelPrincipal.add(lbMatAtletica, "split 2");
        panelPrincipal.add(jtfMatAtletica, "growx, wrap");
        panelPrincipal.add(lbMatUniversidade, "split 2");
        panelPrincipal.add(jtfMatUniversidade, "growx, wrap");
        panelPrincipal.add(lbNome, "split 2");
        panelPrincipal.add(jtfNome, "growx, wrap");
        panelPrincipal.add(lbCPF, "split 2");
        panelPrincipal.add(jtfCPF, "growx, wrap");
        panelPrincipal.add(lbRG, "split 2");
        panelPrincipal.add(jtfRG, "growx, wrap");
        panelPrincipal.add(lbOcupacao, "split 2");
        panelPrincipal.add(jtfOcupacao, "growx, wrap");
        panelPrincipal.add(lbStatus, "split 2");
        panelPrincipal.add(jtfStatus, "growx");
        this.add(panelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public void atribuiListeners() {
        btOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Membro mbr = new Membro();
                mbr.setMatricula_atletica(jtfMatAtletica.getText());
                mbr.setMatricula_universidade(jtfMatUniversidade.getText());
                mbr.setNome(jtfNome.getText());
                mbr.setCPF(jtfCPF.getText());
                mbr.setRG(jtfRG.getText());
                mbr.setMembro_desde(LocalDate.now());
                mbr.setOcupacao(jtfOcupacao.getText());
                mbr.setStatus(jtfStatus.getText());
                if (BancoControle.adicionaMembro(mbr)) {
                    JOptionPane.showMessageDialog(getContentPane(),
                            "Adição concluída com sucesso!",
                            "Êxito", JOptionPane.INFORMATION_MESSAGE);
                    model.addMembro(mbr);
                    dispose();
                }
            }
        });

        btCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

}
