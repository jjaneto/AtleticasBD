package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import net.miginfocom.layout.CC;
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
    private JTextField jtfEmail;
    private JTextField jtfCurso;
    private JTextField jtfNextVenc;
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
    private JLabel lbPlano;
    private JLabel lbModalidade;
    private JLabel lbNextVenc;

    private MaskFormatter maskCPF;
    private MaskFormatter maskTelefone;
    private MaskFormatter maskNascimento;
        
    private JRadioButton rbAnual;
    private JRadioButton rbSemestral;
    /**
     * Botões.
     */
    private JButton btOk;
    private JButton btCancelar;
    private JButton btDoubt;
    

    /**
     * Tabela principal.
     */
    private TabelaMembros model;

    /**
     * A próxima matrícula (última + 1).
     */
    private final int novaMatricula;

    public FrameAdicionar(TabelaMembros model) throws HeadlessException {
        super("Adicionar Membro");
        setLayout(new BorderLayout());
        this.model = model;
        String vlMaxMat = BancoControle.getMaxMatriculaAtletica();
        novaMatricula = Integer.valueOf(vlMaxMat == null ? "0" : vlMaxMat);

        instanciaEAdicionaVariaveis();
        atribuiListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(410, 440));
//        pack();
        setResizable(false);
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
            maskCPF = new MaskFormatter("###########");
            maskTelefone = new MaskFormatter("(##)#####-####");
            maskNascimento = new MaskFormatter("##/##/####");
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
        lbMatAtletica = new JLabel("Matrícula da Atlética: ");
        jtfMatAtletica = new JTextField();
        jtfMatAtletica.setText(String.valueOf(novaMatricula + 1));
        jtfMatAtletica.setEditable(false);
        jtfMatAtletica.setToolTipText("Matrícula escolhida automaticamente. "
                + "Para editar manualmente, consulte o administrador.");
        
        rbAnual = new JRadioButton("Anual");
        rbSemestral = new JRadioButton("Semestral");
        
        lbPlano = new JLabel("Plano de Associação");
        lbNextVenc = new JLabel("Próximo Vencimento");
        lbModalidade = new JLabel("Modalidade: ");
        jtfNextVenc = new JTextField(LocalDate.now().
                format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        jtfNextVenc.setEditable(false);
        jtfNextVenc.setForeground(Color.LIGHT_GRAY);
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
        panelPrincipal = new JPanel(new MigLayout("debug, fillx"));
        JLabel lbAtletica = new JLabel("Dados Atlética");
        panelPrincipal.add(lbAtletica, new CC().alignX("center").spanX());
        panelPrincipal.add(lbMatAtletica, "split 2");
        panelPrincipal.add(jtfMatAtletica, "growx, wrap");
        panelPrincipal.add(lbOcupacao, "split 2");
        panelPrincipal.add(jtfOcupacao, "growx, wrap");
        JLabel lbPessoa = new JLabel("Dados Pessoais");
        panelPrincipal.add(lbPessoa, new CC().alignX("center").spanX());
        panelPrincipal.add(lbMatUniversidade, "split 2");
        panelPrincipal.add(jtfMatUniversidade, "growx, wrap");
        panelPrincipal.add(lbNome, "split 2");
        panelPrincipal.add(jtfNome, "growx, wrap");
        panelPrincipal.add(lbCPF, "split 4");
        panelPrincipal.add(jtfCPF, "growx");
        panelPrincipal.add(lbRG);
        panelPrincipal.add(jtfRG, "growx, wrap");
        panelPrincipal.add(lbEmail, "split 2");
        panelPrincipal.add(jtfEmail, "growx, wrap");
        panelPrincipal.add(lbCurso, "split 2");
        panelPrincipal.add(jtfCurso, "growx, wrap");
        panelPrincipal.add(lbNascimento, "split 4");
        panelPrincipal.add(jtfNascimento, "growx");
        panelPrincipal.add(lbTelefone);
        panelPrincipal.add(jtfTelefone, "growx, wrap");        
        panelPrincipal.add(lbPlano, new CC().alignX("center").spanX());
        panelPrincipal.add(lbModalidade, "split 3");
        ButtonGroup groupBt = new ButtonGroup();
        groupBt.add(rbAnual);
        groupBt.add(rbSemestral);
        panelPrincipal.add(rbAnual);
        panelPrincipal.add(rbSemestral, "wrap");        
        panelPrincipal.add(lbNextVenc, "split 2");
        panelPrincipal.add(jtfNextVenc, "growx, wrap");
        this.add(panelPrincipal, BorderLayout.CENTER);
    }

    @Override
    public void atribuiListeners() {
        btOk.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!rbAnual.isSelected() && !rbSemestral.isSelected()) {
                    JOptionPane.showMessageDialog(getContentPane(),
                            "Você precisa selecionar uma modalidade!",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean todosDigitados = getTexts();
                    int ans = 0;
                    if (!todosDigitados) {
                        ans = JOptionPane.showConfirmDialog(getContentPane(), 
                                "Há campos vazios. Deseja cadastrar membro mesmo assim?", 
                                "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    }
                    if (ans == JOptionPane.YES_OPTION || todosDigitados) {
                        try{
                            LocalDate now = LocalDate.now();
                            Membro mbr = new Membro();
                            mbr.setMatricula_atletica(jtfMatAtletica.getText());
                            mbr.setMatricula_universidade(jtfMatUniversidade.getText());
                            mbr.setNome(jtfNome.getText());
                            mbr.setCPF(jtfCPF.getText());
                            mbr.setRG(jtfRG.getText());
                            mbr.setMembro_desde(LocalDate.now());
                            mbr.setOcupacao(jtfOcupacao.getText());
                            mbr.setDataNascimento(LocalDate.parse(jtfNascimento.getText(),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            mbr.setMembro_desde(now);
                            mbr.setUltimaAssociacao(now);
                            mbr.setCurso(jtfCurso.getText());
                            mbr.setTelefone(jtfTelefone.getText());
                            mbr.setEmail(jtfEmail.getText());
                            mbr.setStatus(Membro.STATUS.A_RECEBER_CARTEIRA);
                            if (rbAnual.isSelected()) {
                                mbr.setModalidade(Membro.MODALIDADE.ANUAL);
                            } else {
                                mbr.setModalidade(Membro.MODALIDADE.SEMESTRAL);
                            }
                            mbr.setVencimento(now);
                            mbr.setRecebimentoCarteira(now);
                            mbr.setRecebeu_carteira(false);
                            if (BancoControle.adicionaMembro(mbr)) {
                                JOptionPane.showMessageDialog(getContentPane(),
                                        "Adição concluída com sucesso!",
                                        "Êxito", JOptionPane.INFORMATION_MESSAGE);
                                model.addMembro(mbr);
                                dispose();
                            }
                        }catch(DateTimeException dte){
                            JOptionPane.showMessageDialog(getContentPane(), 
                                    "Erro na data! Você digitou corretamente?", 
                                    "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        btCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
                
        rbAnual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rbAnual.isSelected() && rbAnual.isEnabled()){
                        jtfNextVenc.setText(LocalDate
                                .now()
                                .plusYears(1)
                                .format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
                }
            }
        });
        
        rbSemestral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rbSemestral.isSelected() && rbSemestral.isEnabled()){
                    jtfNextVenc.setText(LocalDate
                            .now()
                            .plusMonths(6)
                            .format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
                }
            }
        });
    }

    public boolean getTexts(){
        if(jtfMatAtletica.getText().isEmpty()){
            return false;
        }else if(jtfMatUniversidade.getText().isEmpty()){
            return false;
        }else if(jtfNome.getText().isEmpty()){
            return false;
        }else if(jtfRG.getText().isEmpty()){
            return false;
        }else if(jtfOcupacao.getText().isEmpty()){
            return false;
        }else if(jtfEmail.getText().isEmpty()){
            return false;
        }else if(jtfCurso.getText().isEmpty()){
            return false;
        }else if(jtfNextVenc.getText().isEmpty()){
            return false;
        }else if(jtfNascimento.getText().equals("  /  /    ")){
            return false;
        }else if(jtfCPF.getText().equals("         ")){
            return false;
        }else if(jtfTelefone.getText().equals("(  )     -    ")){
            return false;
        }
        return true;
    }
}
