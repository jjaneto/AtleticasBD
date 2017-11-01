package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import net.miginfocom.swing.MigLayout;
import utils.BancoControle;
import utils.HintJTextField;
import utils.Membro;
import utils.RendererMembros;
import utils.TabelaMembros;
import utils.FrameInterativo;

/**
 *
 * @author jjaneto
 */
public final class FramePrincipal extends JFrame implements FrameInterativo{

    /**
     * Panels desse JFrame.
     */
    private JPanel panelSuperior;
    private JPanel panelInferior;

    /**
     * Componentes do Panel Superior.
     */
    private JButton btAdicionar;
    private JButton btExcluir;
    private JButton btEditar;
    private JButton btReturnTable;
    private JButton btPesquisa;    
    private HintJTextField textPesquisa;
    private JComboBox combo;
    
    /**
     * Componentes do Panel Inferior.
     */
    private TabelaMembros model;
    private JTable tabela;
    private JScrollPane scroll;

    /**
     * Arrays que compõem as tabelas.
     */
    public ArrayList<Membro> arrMembros;
    public ArrayList<Membro> arrAuxiliar;    

    
    public FramePrincipal() {
        super("Banco de dados da Atletica das Engenharias - UFS");
        setLayout(new BorderLayout());
        constroiEAdicionaPanelSuperior();
        constroiEAdicionaPanelInferior();

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
                                    + "matrícula da atlética do mesmo", 
                            "Excluir membro", JOptionPane.WARNING_MESSAGE);
                    
                    Membro selected = null;
                    for(Membro mbr : arrMembros){
                        if(mbr.getMatricula_atletica().equals(matricula)){
                            selected = mbr;
                            break;
                        }
                    }
                    if(selected != null){
                        int optionClicked = JOptionPane.showConfirmDialog(getContentPane(),
                                "Você tem certeza de que quer excluir esse membro?"
                                        + "\nEssa ação não pode ser desfeita!", 
                                "Confirmar exclusão", JOptionPane.YES_NO_OPTION,
                                JOptionPane.WARNING_MESSAGE);
                        if(optionClicked == JOptionPane.YES_OPTION){
                            BancoControle.removeMembro(selected);
                            model.removeMembro(selected);
                        }
                    }else if(matricula != null && selected == null){
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
        
        btReturnTable = new JButton();
        btReturnTable.setIcon(new ImageIcon("./img/return.png"));
        btReturnTable.setEnabled(false);
        btReturnTable.setToolTipText(
                "Ativado apenas se a tabela abaixo for a dos resultados de pesquisa."
                + " Ao clicá-lo, a tabela original será restaurada.");
        
        btReturnTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                model.trocaAuxiliarParaArray();
                model.limpaArrayAux();
                btReturnTable.setEnabled(false);
            }
        });

        panelSuperior = new JPanel(new MigLayout("fillx"));
        panelSuperior.add(btAdicionar, "growx, split 4");
        panelSuperior.add(btExcluir, "growx");
        panelSuperior.add(btEditar, "growx");
        panelSuperior.add(btReturnTable, "wrap");
        
               
        combo = new JComboBox(Membro.FIELD.values());
        
        combo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    if(combo.getSelectedItem().equals(Membro.FIELD.MEMBRO_DESDE)){
                        JOptionPane.showMessageDialog(getContentPane(), 
                                "Para essa opção, o resultado será todos os membros"
                                        + " cadastrados a partir da data digitada.",
                                "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        
        textPesquisa = new HintJTextField("Selecione a opção ao lado e digite a pesquisa aqui.");
        btPesquisa = new JButton(new ImageIcon("./img/find.png"));
        btPesquisa.setToolTipText("Buscar campo digitado!");
        btPesquisa.setEnabled(false);
        btPesquisa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(btPesquisa.isEnabled()){                    
                    searchAndGet(textPesquisa.getText(), (Membro.FIELD)combo.getSelectedItem());
                    model.trocaArrayParaAuxiliar();
                    btReturnTable.setEnabled(true);
                }
            }
        });        
        
        textPesquisa.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(textPesquisa.getText().isEmpty()){
                    btPesquisa.setEnabled(false);
                }else btPesquisa.setEnabled(true);
            }
        });
        
        panelSuperior.add(combo, "spanx, split 3");
        panelSuperior.add(textPesquisa, "spanx, growx");
        panelSuperior.add(btPesquisa);
        panelSuperior.setPreferredSize(panelSuperior.getPreferredSize());

        this.add(panelSuperior, BorderLayout.NORTH);
    }

    public void constroiEAdicionaPanelInferior() {
        panelInferior = new JPanel(new BorderLayout());

        arrMembros = BancoControle.carregaTabela();
        arrAuxiliar = new ArrayList<>();

        model = new TabelaMembros(arrMembros, arrAuxiliar);

        tabela = new JTable(model);
        tabela.setDefaultRenderer(Object.class, new RendererMembros());
        
        tabela.setGridColor(Color.gray);
        scroll = new JScrollPane(tabela);

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

    public void searchAndGet(String what, Membro.FIELD field){
        model.limpaArrayAux();
        switch(field){
            case MATRICULA_ATL:
                for(Membro mbr : arrMembros){
                    if(mbr.getMatricula_atletica()
                            .substring(0, Math.min(what.length(), mbr.getMatricula_atletica().length() - 1))
                            .equals(what)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case MAT_UNI:
                for(Membro mbr : arrMembros){
                    String total = mbr.getMatricula_universidade();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case NOME:
                for(Membro mbr : arrMembros){
                    String total = mbr.getNome();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case RG:
                for(Membro mbr : arrMembros){
                    String total = mbr.getRG();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case CPF:
                for(Membro mbr : arrMembros){
                    String total = mbr.getCPF();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case OCUPACAO:
                for(Membro mbr : arrMembros){
                    String total = mbr.getOcupacao();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case STATUS:
                
                break;
            case CURSO:
                for(Membro mbr : arrMembros){
                    String total = mbr.getCurso();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case TELEFONE:
                for(Membro mbr : arrMembros){
                    String total = mbr.getTelefone();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case EMAIL: 
                for(Membro mbr : arrMembros){
                    String total = mbr.getEmail();
                    String sub = total.substring(0, Math.min(what.length(), total.length() - 1));
                    if(what.equals(sub)){
                        model.addMembroArrAuxiliar(mbr);
                    }
                }
                break;
            case NASCIMENTO:
                for(Membro mbr : arrMembros){
                    LocalDate dateMembro = mbr.getDataNascimento();
                    LocalDate diaPesquisado = LocalDate.parse(what, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    if(dateMembro.isEqual(diaPesquisado)){
                        arrMembros.add(mbr);
                    }
                }
                break;
            case MEMBRO_DESDE:
                for(Membro mbr : arrMembros){
                    LocalDate dateMembro = mbr.getMembro_desde();
                    LocalDate diaPesquisado = LocalDate.parse(what, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                    if(dateMembro.isAfter(diaPesquisado) || dateMembro.isEqual(diaPesquisado)){
                        arrMembros.add(mbr);
                    }
                }
                break;
        }
    }

    @Override
    public void instanciaEAdicionaVariaveis() {
    
    }

    @Override
    public void atribuiListeners() {
    
    }
}
