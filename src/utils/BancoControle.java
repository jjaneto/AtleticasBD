package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author jjaneto
 */
public class BancoControle {
    
    private Properties props;
    private Connection conn;
    private String url;

    public BancoControle(String user, String password, Boolean ssl) {
        url = "jdbc:postgresql://localhost:5432/atleticasbd";
        try{
            props = new Properties();
            props.setProperty("user", user);
            props.setProperty("password", password);
//            props.setProperty("ssl", ssl.toString());
            Class.forName("org.postgresql.Driver");            
            conn = DriverManager.getConnection(url, props);
        }catch(ClassNotFoundException e){
            System.err.println("Erro ao carregar a classe do driver do banco!");
            System.out.println("Salvando o log do erro e encerrando o programa...");
            Logs.printLogErro(e);
            System.exit(0);
        }catch(SQLException ex){
            System.err.println("Erro ao carregar o driver do banco!");
            System.out.println("Salvando o log do erro e encerrando o programa...");
            Logs.printLogErro(ex);
            System.exit(0);
        }
    }  
    
    public boolean adicionaMembro(Membro membro){
        String cmd = "INSERT INTO membro VALUES " + membro.toSQL() + ";";
        try(Statement st = conn.createStatement()){            
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao adicionar membro na tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            return false;
        }
        return true;
    }
    
    public boolean removeMembro(Membro membro){
        String cmd = "DELETE FROM membro WHERE matricula_atletica = " 
                + membro.getMatricula_atletica() + ";";
        
        try(Statement st = conn.createStatement()){
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao excluir o membro da tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
            return false;            
        }
        return true;
    }
    
    public ArrayList<Membro> procuraMembro(String coluna, String txt){
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
            return null;
        }
        return queryResult;
    }
    
    public ArrayList<Membro> carregaTabela(){
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
            Logs.printLogErro(e);
            return null;
        }
        return queryResult;
    }
    
    public boolean atualizaMembro(String coluna, String novo_valor, String valor_antigo){
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
}
