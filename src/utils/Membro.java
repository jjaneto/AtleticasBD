package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jjaneto
 */
public class Membro {

    private String matricula_atletica;
    private String matricula_universidade;
    private String nome;
    private String CPF;
    private String RG;
    private String ocupacao;
    private String status;
    //private String curso;
    private LocalDate membro_desde;

    public Membro() {
    }

    public Membro(ResultSet st) {
        try {
            this.matricula_atletica = st.getString("matricula_atletica");
            this.matricula_universidade = st.getString("matricula_universidade");
            this.nome = st.getString("nome");
            this.CPF = st.getString("CPF");
            this.RG = st.getString("RG");
            this.ocupacao = st.getString("ocupacao");
            this.status = st.getString("status");
            this.membro_desde = LocalDate.parse(st.getString("membro_desde"),
                                    DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (SQLException e) {
            System.err.println("Erro ao setar atributo do membro durante sua inicializacao!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            Logs.printLogEdicao(e, this);
        }
    }

    public Membro(String matricula_atletica, String matricula_universidade,
            String nome, String CPF, String RG,
            String ocupacao, String status, LocalDate membro_desde) {
        this.matricula_atletica = matricula_atletica;
        this.matricula_universidade = matricula_universidade;
        this.nome = nome;
        this.CPF = CPF;
        this.RG = RG;
        this.ocupacao = ocupacao;
        this.status = status;
        this.membro_desde = membro_desde;
    }

    public String getMatricula_atletica() {
        return matricula_atletica;
    }

    public void setMatricula_atletica(String matricula_atletica) {
        this.matricula_atletica = matricula_atletica;
    }

    public String getMatricula_universidade() {
        return matricula_universidade;
    }

    public void setMatricula_universidade(String matricula_universidade) {
        this.matricula_universidade = matricula_universidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String RG) {
        this.RG = RG;
    }

    public String getOcupacao() {
        return ocupacao;
    }

    public void setOcupacao(String ocupacao) {
        this.ocupacao = ocupacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getMembro_desde() {
        return membro_desde;
    }
    
    public String getMembro_desde_formatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dtf.format(membro_desde);
    }

    public void setMembro_desde(LocalDate membro_desde) {
        this.membro_desde = membro_desde;
    }

    public String toSQL() {
        String ret = "(";
        ret += "'" + getMatricula_atletica() + "', ";
        ret += "'" + getMatricula_universidade() + "', ";
        ret += "'" + getNome() + "', ";
        ret += "'" + getCPF() + "', ";
        ret += "'" + getRG() + "', ";
        ret += "'" + getMembro_desde_formatado()+ "', ";
        ret += "'" + getOcupacao() + "', ";
        ret += "'" + getStatus() + "')";
        return ret;
    }
   
    @Override
    public String toString(){
        return getMatricula_atletica() + " | " + getNome();
    }

}
