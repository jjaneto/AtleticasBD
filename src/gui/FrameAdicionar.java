package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javafx.stage.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;
import utils.BancoControle;
import utils.Membro;
import utils.TabelaMembros;

/**
 *
 * @author jjaneto
 */
public class FrameAdicionar extends JFrame {

    private JPanel panelPrincipal;
    private JPanel panelBotoes;
    
    private JTextField jtfMatAtletica;
    private JTextField jtfMatUniversidade;
    private JTextField jtfNome;
    private JTextField jtfCPF;
    private JTextField jtfRG;
    private JTextField jtfOcupacao;
    private JTextField jtfStatus;
    
    private JButton btOk;
    private JButton btCancelar;
    
    private TabelaMembros model;

    public FrameAdicionar(TabelaMembros model) throws HeadlessException {
        setLayout(new BorderLayout());
        this.model = model;
        constroiEAdicionaPanelBotoes();
        constroiEAdicionaPanelPrincipal();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //pack();
        setMinimumSize(new Dimension(400, 280));
        setLocationRelativeTo(null);
        setTitle("Adicionar Membro");
        setVisible(true);
    }

    public void constroiEAdicionaPanelBotoes() {
        panelBotoes = new JPanel(new GridLayout(1, 2, 1, 1));
        this.add(panelBotoes, BorderLayout.SOUTH);

        btOk = new JButton("Adicionar");
        btOk.setIcon(new ImageIcon("./img/ok.png"));
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
                if(BancoControle.adicionaMembro(mbr)){
                    JOptionPane.showMessageDialog(getContentPane(), 
                            "Adição concluída com sucesso!", 
                            "Êxito", JOptionPane.INFORMATION_MESSAGE);
                    model.addMembro(mbr);
                    dispose();
                }
            }
        });

        btCancelar = new JButton("Cancelar");
        btCancelar.setIcon(new ImageIcon("./img/cancel.png"));
        btCancelar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });

        panelBotoes.add(btCancelar);
        panelBotoes.add(btOk);
    }

    public void constroiEAdicionaPanelPrincipal() {
        panelPrincipal = new JPanel(new MigLayout("fillx"));
        this.add(panelPrincipal, BorderLayout.CENTER);
        
        System.out.println(BancoControle.getMaxMatriculaAtletica());
        
        int novaMatricula = Integer.valueOf(BancoControle.getMaxMatriculaAtletica());
        
        JLabel lbMatAtletica = new JLabel("Matrícula da Atlética: ");
        jtfMatAtletica = new JTextField();
        jtfMatAtletica.setText(String.valueOf(novaMatricula + 1));
        jtfMatAtletica.setEditable(false);
        jtfMatAtletica.setToolTipText("Matrícula escolhida automaticamente. "
                + "Para editar manualmente, consulte o administrador.");
        panelPrincipal.add(lbMatAtletica, "split 2");
        panelPrincipal.add(jtfMatAtletica, "growx, wrap");
        
        JLabel lbMatUniversidade = new JLabel("Matrícula da Universidade: ");
        jtfMatUniversidade = new JTextField();
        panelPrincipal.add(lbMatUniversidade, "split 2");
        panelPrincipal.add(jtfMatUniversidade, "growx, wrap");
        
        JLabel lbNome = new JLabel("Nome: ");
        jtfNome = new JTextField();
        panelPrincipal.add(lbNome, "split 2");
        panelPrincipal.add(jtfNome, "growx, wrap");
        
        JLabel lbCPF = new JLabel("CPF: ");
        jtfCPF = new JTextField();
        panelPrincipal.add(lbCPF, "split 2");
        panelPrincipal.add(jtfCPF, "growx, wrap");
        
        JLabel lbRG = new JLabel("RG: ");
        jtfRG = new JTextField();
        panelPrincipal.add(lbRG, "split 2");
        panelPrincipal.add(jtfRG, "growx, wrap");
        
        JLabel lbOcupacao = new JLabel("Ocupação: ");
        jtfOcupacao = new JTextField();
        panelPrincipal.add(lbOcupacao, "split 2");
        panelPrincipal.add(jtfOcupacao, "growx, wrap");
        
        JLabel lbStatus = new JLabel("Status: ");
        jtfStatus = new JTextField();
        panelPrincipal.add(lbStatus, "split 2");
        panelPrincipal.add(jtfStatus, "growx");
     
    }

}
