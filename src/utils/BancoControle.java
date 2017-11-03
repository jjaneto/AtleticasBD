package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author jjaneto
 */
public class BancoControle {
    
    private static Properties props;
    private static Connection conn;
    private static String url;

    public BancoControle(String user, String password, Boolean ssl) {
        url = "jdbc:postgresql://localhost:5432/atleticasbd";
        try{
            props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
            
            Class.forName("org.postgresql.Driver");            
            conn = DriverManager.getConnection(url, props);
        }catch(ClassNotFoundException e){
            System.err.println("Erro ao carregar a classe do driver do banco!");
            System.out.println("Salvando o log do erro e encerrando o programa...");
            e.printStackTrace();
            Logs.printLogErro(e);
            System.exit(0);
        }catch(SQLException ex){
            System.err.println("Erro ao carregar o driver do banco!");
            System.out.println("Salvando o log do erro e encerrando o programa...");
            ex.printStackTrace();
            Logs.printLogErro(ex);
            System.exit(0);
        }
    }  
    
    public static boolean adicionaMembro(Membro membro){
        String cmd = "INSERT INTO membro VALUES " + membro.toSQL() + ";";
        try(Statement st = conn.createStatement()){            
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao adicionar membro na tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            JOptionPane.showMessageDialog(null, 
                            "Erro ao adicionar membro ao banco de dados!\n"
                                    + trataMensagem(e), 
                            "Erro.", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static boolean removeMembro(Membro membro){
        String cmd = "DELETE FROM membro WHERE matricula_atletica = '" 
                + membro.getMatricula_atletica() + "';";
        System.out.println(cmd);
        try(Statement st = conn.createStatement()){
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao excluir o membro da tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            e.printStackTrace();
            return false;            
        }
        return true;
    }
    
    public static ArrayList<Membro> procuraMembro(String coluna, String txt){
        String cmd = "SELECT * FROM membro WHERE " + coluna + " = " + txt;
        ArrayList<Membro> queryResult = null;
        
        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(cmd)   ){
            
            while(rs.next())
                queryResult.add(new Membro(rs));
            
        }catch(SQLException e){
            System.err.println("Erro ao procurar o membro na tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            e.printStackTrace();
            return null;
        }
        return queryResult;
    }
    
    public static ArrayList<Membro> carregaTabela(){
        String cmd = "SELECT * FROM membro";
        ArrayList<Membro> queryResult = null;
        
        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(cmd)   ){
            queryResult = new ArrayList<>();
            
            while(rs.next()){
                queryResult.add(new Membro(rs));
            }
            
        }catch(SQLException e){
            System.err.println("Erro ao carregar a tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            e.printStackTrace();
            Logs.printLogErro(e);
            return null;
        }
        return queryResult;
    }
    
    public static boolean atualizaMembroUnicaColuna(String coluna, String novo_valor, String valor_antigo){
        String cmd = "UPDATE TABLE membro SET " + coluna + " = " + novo_valor +
                     " WHERE " + coluna + " = " + valor_antigo;
        
        try(Statement st = conn.createStatement()){
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao atualizar o membro da tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            return false;
        }
        return true;
    }
    
    public static boolean atualizaMembroVariasColunas(Membro mbr){
        String cmd = "UPDATE membro SET " + mbr.toSQLUpdate() 
                + " WHERE matricula_atletica = '" + mbr.getMatricula_atletica() + "';";
        System.out.println(cmd);
        
        try(Statement st = conn.createStatement()){
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao atualizar o membro da tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, 
                            "Erro ao adicionar membro ao banco de dados!\n"
                                    + trataMensagem(e), 
                            "Erro.", JOptionPane.ERROR_MESSAGE);
            Logs.printLogErro(e);
            return false;
        }
        return true;
    }
    
    public static String getMaxMatriculaAtletica(){
        String cmd = "SELECT max(matricula_atletica) FROM membro;";
        try(Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(cmd)  ){
            
            while(rs.next())
                return rs.getString("max");
            
        }catch(SQLException e){
            System.err.println("Erro ao pegar a ultima matricula!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            e.printStackTrace();
            Logs.printLogErro(e);            
        }
        return null;
    }
    
    public static String trataMensagem(SQLException e){
        String ret = "";
        System.out.println("Mensagem do erro: " + e.getMessage());
        if(e.getMessage().contains("duplicate key value violates unique constraint")){
            return "Alguns itens aqui digitados já constam no banco.\n"
                    + "Verique se todos os dados foram digitados corretamente."
                    + "Nenhuma alteração no banco de dados foi feita.";
        }else if(e.getMessage().contains("violates check constraint")){
            return "Alguns itens aqui digitados não foram digitados corretamente.\n"
                    + "Verifique se todos foram fornecidos e/ou digitados corretamente.\n"
                    + "Nenhuma alteração no banco de dados foi feita.";
        }        
        return ret;
    }
}
