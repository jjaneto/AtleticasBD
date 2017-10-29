package gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
        constroiEAdicionaPanelSuperior();

        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void constroiEAdicionaPanelSuperior() {
        btAdicionar = new JButton("Adicionar");
        btAdicionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
//                    JOptionPane.showConfirmDialog(getContentPane(), "Haha!");
                }
            }
        });
        
        btExcluir = new JButton("Excluir");
        btExcluir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });
        
        btEditar = new JButton("Editar");
        btEditar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {

                }
            }
        });

        panelSuperior = new JPanel(new BorderLayout());

        panelSuperior.add(btAdicionar, BorderLayout.EAST);
        panelSuperior.add(btExcluir, BorderLayout.CENTER);
        panelSuperior.add(btEditar, BorderLayout.WEST);

        this.add(panelSuperior);
    }

}
