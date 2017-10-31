package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import utils.BancoControle;
import utils.Membro;
import utils.TabelaMembros;

/**
 *
 * @author jjaneto
 */
public class FramePrincipal extends JFrame {

    private JPanel panelSuperior;
    private JPanel panelInferior;

    private JButton btAdicionar;
    private JButton btExcluir;
    private JButton btEditar;

    public ArrayList<Membro> arrMembros;

    TabelaMembros model;

    public FramePrincipal() {
        setLayout(new BorderLayout());
        constroiEAdicionaPanelSuperior();
        constroiEAdicionaPanelInferior();

        setTitle("Banco de dados da Atletica das Engenharias - UFS");
        setMinimumSize(new Dimension(600, 300));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void constroiEAdicionaPanelSuperior() {
        btAdicionar = new JButton("Adicionar Membro");
        btAdicionar.setIcon(new ImageIcon("./img/add.png"));
        btAdicionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    new FrameAdicionar(model);
                }
            }
        });

        btExcluir = new JButton("Excluir Membro");
        btExcluir.setIcon(new ImageIcon("./img/trash.png"));
        btExcluir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    String matricula = JOptionPane.showInputDialog(getContentPane(), 
                            "Só é possível excluir um membro\ndigitando a "
                                    + "matrícula da atlética do membro", 
                            "Excluir membro", JOptionPane.WARNING_MESSAGE);
                    boolean exists = false;
                    for(Membro mbr : arrMembros){
                        if(mbr.getMatricula_atletica().equals(matricula)){
                            exists = true; 
                            break;
                        }
                    }
                    if(exists){
                        int optionClicked = JOptionPane.showConfirmDialog(getContentPane(),
                                "Você tem certeza de que quer excluir esse membro?"
                                        + "\nEssa ação não pode ser desfeita!", 
                                "Confirmar exclusão", JOptionPane.YES_NO_OPTION);
                        if(optionClicked == JOptionPane.YES_OPTION){
                            
                        }
                    }else{
                        JOptionPane.showMessageDialog(getContentPane(), 
                                "Essa matrícula não existe!", 
                                "Ops!", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btEditar = new JButton("Editar Membro");
        btEditar.setIcon(new ImageIcon("./img/edit.png"));
        btEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    JOptionPane.showMessageDialog(getContentPane(), 
                            "Para editar um membro você deve clicar sob ele na tabela!\n"
                                    + "Em seguida, pressione o botão \"Editar membro\" "
                                    + "para habilitar a edição dos campos.", 
                            "Editar um membro", 
                            JOptionPane.INFORMATION_MESSAGE,
                            new ImageIcon("./img/information.png"));
                }
            }
        });

        panelSuperior = new JPanel(new MigLayout("fillx"));
        panelSuperior.add(btAdicionar, "growx");
        panelSuperior.add(btExcluir, "growx");
        panelSuperior.add(btEditar, "growx, wrap");
        
        String campos[] = {
            "Mat. Atletica", 
            "Mat. Universidade", 
            "Nome",
            "CPF",
            "RG",
            "Ocupação",
            "Telefone",
            "Email",
            "Data de Nascimento"
        };
        
        JComboBox combo = new JComboBox(campos);
        
        JTextField textPesquisa = new JTextField();
        
        JButton btPesquisa = new JButton(new ImageIcon("./img/find.png"));
        btPesquisa.setToolTipText("Buscar campo digitado!");
        
        panelSuperior.add(combo, "spanx, split 3");
        panelSuperior.add(textPesquisa, "spanx, growx");
        panelSuperior.add(btPesquisa);
        panelSuperior.setPreferredSize(panelSuperior.getPreferredSize());

        this.add(panelSuperior, BorderLayout.NORTH);
    }

    public void constroiEAdicionaPanelInferior() {
        panelInferior = new JPanel(new BorderLayout());
        //panelInferior.add(new JLabel("TABELA DOS MEMBROS VAI AQUI"));

        arrMembros = BancoControle.carregaTabela();

        model = new TabelaMembros(arrMembros);

        JTable tabela = new JTable(model);
        
        tabela.setGridColor(Color.gray);
        JScrollPane scroll = new JScrollPane(tabela);

        panelInferior.add(scroll);

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowClicked = tabela.rowAtPoint(e.getPoint());
                int columnClicked = tabela.columnAtPoint(e.getPoint());
                if (rowClicked >= 0 && columnClicked >= 0) {
                    new FrameVisualizar((Membro) arrMembros.get(rowClicked),
                            rowClicked, columnClicked);
                }
            }
        });

        this.add(panelInferior, BorderLayout.CENTER);
    }

}
