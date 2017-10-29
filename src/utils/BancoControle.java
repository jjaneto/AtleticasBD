package utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author jjaneto
 */
public class BancoControle {
    
    Properties props;
    Connection conn;

    public BancoControle(String user, String password, boolean ssl) {
        try{
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.err.println("Erro ao carregar o driver do banco!");
            System.out.println("Salvando o log do erro e encerrando o programa...");
            Logs.printLogErro(e);
            System.exit(0);
        }
    }  
    
    public boolean adicionaMembro(Membro membro){
        String cmd = "INSERT INTO membro VALUES " + membro.toSQL() + ";";
        try{
            Statement st = conn.createStatement();
            st.executeUpdate(cmd);
        }catch(SQLException e){
            System.err.println("Erro ao adicionar membro na tabela!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogErro(e);
        }
        return true;
    }
    
    public boolean removeMembro(Membro membro){
        return true;
    }
    
    public Membro procuraMembro(String coluna, String txt){
        
        return null;
    }
}
