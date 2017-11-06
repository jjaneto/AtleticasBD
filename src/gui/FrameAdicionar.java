package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
    
    private JComboBox comboStatus;
    
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
        pack();
//        setResizable(false);
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
        comboStatus = new JComboBox(Membro.STATUS.values());
        comboStatus.setSelectedItem(Membro.STATUS.DEVENDO);
        lbMatAtletica = new JLabel("Matrícula da Atlética: ");
        jtfMatAtletica = new JTextField();
        jtfMatAtletica.setText(String.valueOf(novaMatricula + 1));
        jtfMatAtletica.setEditable(false);
        jtfMatAtletica.setToolTipText("Matrícula escolhida automaticamente. "
                + "Para editar manualmente, consulte o administrador.");
        
        lbPlano = new JLabel("Plano:");
        rbAnual = new JRadioButton("Anual");
        rbAnual.setEnabled(false);
        rbSemestral = new JRadioButton("Semestral");
        rbSemestral.setEnabled(false);
        
        lbPlano = new JLabel("Plano de Associação");
        btDoubt = new JButton("?");
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
        JLabel lbPessoa = new JLabel("Dados Pessoa");
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
        panelPrincipal.add(lbStatus, "split 3");
        panelPrincipal.add(comboStatus, "growx");       
        panelPrincipal.add(btDoubt, "growx, wrap");       
        panelPrincipal.add(lbModalidade, "split 3");
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
                Membro mbr = new Membro();
                mbr.setMatricula_atletica(jtfMatAtletica.getText());
                mbr.setMatricula_universidade(jtfMatUniversidade.getText());
                mbr.setNome(jtfNome.getText());
                mbr.setCPF(jtfCPF.getText());
                mbr.setRG(jtfRG.getText());
                mbr.setMembro_desde(LocalDate.now());
                mbr.setOcupacao(jtfOcupacao.getText());
                if(comboStatus.getSelectedItem() == Membro.STATUS.PAGO){
                    mbr.setStatus(Membro.STATUS.PAGO);
                    mbr.setVencimento(LocalDate.now().plusMonths(6));
                }else if(comboStatus.getSelectedItem() == Membro.STATUS.DEVENDO){
                    mbr.setStatus(Membro.STATUS.DEVENDO);
                    mbr.setVencimento(LocalDate.now());
                }else{
                    mbr.setStatus(Membro.STATUS.A_VENCER);
                    mbr.setVencimento(LocalDate.now().plusMonths(1));
                }
                mbr.setEmail(jtfEmail.getText());
                mbr.setDataNascimento(LocalDate.parse(jtfNascimento.getText(), 
                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                mbr.setMembro_desde(LocalDate.now());
                mbr.setCurso(jtfCurso.getText());
                mbr.setTelefone(jtfTelefone.getText());
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
        
        rbAnual.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(rbSemestral.isSelected()){
                    rbSemestral.setSelected(false);
                    jtfNextVenc.setText(LocalDate
                            .now()
                            .plusYears(1)
                            .format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
                }
            }
        });
        
        rbSemestral.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(rbAnual.isSelected()){
                    rbAnual.setSelected(false);
                    jtfNextVenc.setText(LocalDate
                            .now()
                            .plusMonths(6)
                            .format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
                }
            }
        });
        
        comboStatus.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(comboStatus.getSelectedItem() == Membro.STATUS.A_VENCER){
                        LocalDate aMonth = LocalDate.now().plusMonths(1);
                        jtfNextVenc.setText(aMonth.format(DateTimeFormatter
                                                .ofPattern("dd/MM/YYYY")));
                    }else if(comboStatus.getSelectedItem() == Membro.STATUS.PAGO){
                        rbAnual.setEnabled(true);
                        rbAnual.setSelected(true);
                        rbSemestral.setEnabled(true);
                        LocalDate aYear = LocalDate.now().plusYears(1);
                        jtfNextVenc.setText(aYear.format(DateTimeFormatter
                                                .ofPattern("dd/MM/YYYY")));
                    }else if(comboStatus.getSelectedItem() == Membro.STATUS.DEVENDO){
                        rbAnual.setSelected(false);
                        rbAnual.setEnabled(false);
                        rbSemestral.setSelected(false);
                        rbSemestral.setEnabled(false);
                        jtfNextVenc.setText(LocalDate.now().format(DateTimeFormatter
                                            .ofPattern("dd/MM/YYYY")));
                    }
                }
            }
        });
    }

}
