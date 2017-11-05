package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import net.miginfocom.swing.MigLayout;
import utils.BancoControle;
import utils.Membro;
import utils.FrameInterativo;
import utils.TabelaMembros;

/**
 *
 * @author jjaneto
 */
public final class FrameVisualizar extends JFrame implements FrameInterativo {

    /**
     * Variáveis do Membro passado como parâmetro no construtor.
     */
    private final Membro mbr;
    private final int rowStatus;
    private final int columnStatus;
    private final TabelaMembros model;

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
    private JLabel lbTelefone;
    private JLabel lbCurso;

    private MaskFormatter maskCPF;
    private MaskFormatter maskTelefone;
    private MaskFormatter maskNascimento;

    /**
     * Array para auxiliar troca de editable.
     */
    private JTextField arrTextField[];

    public FrameVisualizar(TabelaMembros model,
            Membro mbr, int rowStatus,
            int columnStatus) throws HeadlessException {
        super("Tela do membro " + mbr.getMatricula_atletica());
        this.mbr = mbr;
        this.rowStatus = rowStatus;
        this.columnStatus = columnStatus;
        this.model = model;

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
        jtfMatAtl.setEditable(false);

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
        jtfStatus = new JTextField(mbr.getStatus().toString());

        lbMembroDesde = new JLabel("Membro desde: ");
        jtfMembroDesde = new JTextField(mbr.getMembro_desde_formatado());
        jtfMembroDesde.setEditable(false);

        try {
            maskCPF = new MaskFormatter("###########");
            maskTelefone = new MaskFormatter("(##)#####-####");
            maskNascimento = new MaskFormatter("##/##/####");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        lbNascimento = new JLabel("Nascimento: ");
        jtfNascimento = new JFormattedTextField(maskNascimento);
        jtfNascimento.setText(mbr.getDataNascimentoFormatado());
        lbTelefone = new JLabel("Telefone: ");
        jtfTelefone = new JFormattedTextField(maskTelefone);
        jtfTelefone.setText(mbr.getTelefone());
        lbCurso = new JLabel("Curso: ");
        jtfCurso = new JTextField(mbr.getCurso());
        lbEmail = new JLabel("Email: ");
        jtfEmail = new JTextField(mbr.getEmail());

        panelDados = new JPanel(new MigLayout("debug, fillx"));
        panelDados.add(lbMatAtl, "split 4");
        panelDados.add(jtfMatAtl, "growx");
        panelDados.add(lbMembroDesde);
        panelDados.add(jtfMembroDesde, "growx, wrap");

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
        panelDados.add(jtfStatus, "growx, wrap");
        panelDados.add(lbEmail, "split 2");
        panelDados.add(jtfEmail, "growx, wrap");
        panelDados.add(lbCurso, "split 2");
        panelDados.add(jtfCurso, "growx, wrap");
        panelDados.add(lbNascimento, "split 2");
        panelDados.add(jtfNascimento, "growx, wrap");
        panelDados.add(lbTelefone, "split 2");
        panelDados.add(jtfTelefone, "growx");

        arrTextField = new JTextField[]{
            jtfMatUniversidade,
            jtfNome,
            jtfCPF,
            jtfRG,
            jtfOcupacao,
            jtfStatus,
            jtfEmail,
            jtfTelefone,
            jtfCurso,
            jtfNascimento};

        setEditableTextFields(false);

        this.add(panelDados, BorderLayout.NORTH);
    }

    @Override
    public void atribuiListeners() {

        btSalvar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (btSalvar.isEnabled()) {
                    mbr.setCPF(jtfCPF.getText());
                    mbr.setMatricula_atletica(jtfMatAtl.getText());
                    mbr.setMatricula_universidade(jtfMatUniversidade.getText());
                    mbr.setNome(jtfNome.getText());
                    mbr.setRG(jtfRG.getText());
                    mbr.setOcupacao(jtfOcupacao.getText());
                    if(jtfStatus.getText().equals("Pago")){
                        mbr.setStatus(Membro.STATUS.PAGO);
                    }else if(jtfStatus.getText().equals("Devendo")){
                        mbr.setStatus(Membro.STATUS.DEVENDO);
                    }else{
                        mbr.setStatus(Membro.STATUS.A_VENCER);
                    }
                    mbr.setCurso(jtfCurso.getText());
                    mbr.setTelefone(jtfTelefone.getText());
                    mbr.setEmail(jtfEmail.getText());
                    if(BancoControle.atualizaMembroVariasColunas(mbr)){
                        model.trocaMembro(rowStatus, mbr);
                        JOptionPane.showMessageDialog(getContentPane(),
                                "O membro foi atualizado com sucesso.",
                                "Êxito", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }else{
                        JOptionPane.showMessageDialog(getContentPane(),
                                "Erro ao atualizar o membro.",
                                "Erro!", JOptionPane.ERROR_MESSAGE);
                    }
                }
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
