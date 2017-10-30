package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import utils.BancoControle;
import utils.Membro;

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

    public FramePrincipal() {
        setLayout(new BorderLayout());
        constroiEAdicionaPanelSuperior();
        constroiEAdicionaPanelInferior();

        setTitle("Banco de dados da Atletica das Engenharias - UFS");
        setMinimumSize(new Dimension(300, 300));
//        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void constroiEAdicionaPanelSuperior() {
        btAdicionar = new JButton("Adicionar");
        btAdicionar.setIcon(new ImageIcon("./img/add.png"));
        btAdicionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    new FrameAdicionar();
                }
            }
        });
        
        btExcluir = new JButton("Excluir");
        btExcluir.setIcon(new ImageIcon("./img/trash.png"));
        btExcluir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    
                }
            }
        });
        
        btEditar = new JButton("Editar");
        btEditar.setIcon(new ImageIcon("./img/edit.png"));
        btEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });

        panelSuperior = new JPanel(new GridLayout(1, 3, 1, 1));

        panelSuperior.add(btAdicionar);//, BorderLayout.EAST);
        panelSuperior.add(btExcluir);//, BorderLayout.CENTER);
        panelSuperior.add(btEditar);//, BorderLayout.WEST);

        this.add(panelSuperior, BorderLayout.NORTH);
    }
    
    public void constroiEAdicionaPanelInferior(){
        panelInferior = new JPanel(new BorderLayout());
        panelInferior.add(new JLabel("TABELA DOS MEMBROS VAI AQUI"));
        this.add(panelInferior, BorderLayout.CENTER);
    }

}