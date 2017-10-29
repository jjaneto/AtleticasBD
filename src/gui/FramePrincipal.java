package gui;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author jjaneto
 */
public class FramePrincipal extends JFrame{
    
    private JPanel panelSuperior;
    
    private JButton btAdicionar;
    private JButton btExcluir;
    private JButton btEditar;

    public FramePrincipal() {
        
        btAdicionar = new JButton("Adicionar");
        btExcluir = new JButton("Excluir");
        btEditar = new JButton("Editar");
        
        panelSuperior = new JPanel(new BorderLayout());
        
        panelSuperior.add(btAdicionar, BorderLayout.EAST);
        panelSuperior.add(btExcluir, BorderLayout.CENTER);
        panelSuperior.add(btEditar, BorderLayout.WEST);
        
        add(panelSuperior);
        
            
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    
}
