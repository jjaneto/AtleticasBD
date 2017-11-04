package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author jjaneto
 */
public class Membro implements Comparable{
    
    public enum STATUS{
        PAGO("Pago"), 
        DEVENDO("Devendo"), 
        A_VENCER("A vencer");
        
        private final String value;
        
        private STATUS(String value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return this.value;
        }
    }
    
    public enum FIELD{
        
        MATRICULA_ATL("Mat. Atlética"), 
        MAT_UNI("Mat. Universidade"),
        NOME("Nome"),
        RG("RG"),
        CPF("CPF"),
        OCUPACAO("Ocupação"),
        STATUS("Status"),
        CURSO("Curso"),
        TELEFONE("Telefone"),
        EMAIL("Email"),
        NASCIMENTO("Nascimento"),
        MEMBRO_DESDE("Membro Desde");
        
        private final String value;
        
        private FIELD(String value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return this.value;
        }
    }

    private String matricula_atletica;
    private String matricula_universidade;
    private String nome;
    private String CPF;
    private String RG;
    private String ocupacao;
    private String curso;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private LocalDate membro_desde;
    private LocalDate vencimento;
    private STATUS status;

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
            this.email = st.getString("email");
            this.telefone = st.getString("telefone");
            this.curso = st.getString("curso");
            this.membro_desde = LocalDate.parse(st.getString("membro_desde"),   
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.vencimento = LocalDate.parse(st.getString("vencimento"),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            this.dataNascimento = LocalDate.parse(st.getString("nascimento"),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if(LocalDate.now().isAfter(vencimento)){
                this.status = STATUS.DEVENDO;
            }else if(ChronoUnit.DAYS.between(LocalDate.now(), vencimento) <= (long) 30){
                this.status = STATUS.A_VENCER;
            }else{
                this.status = STATUS.PAGO;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao setar atributo do membro durante sua inicializacao!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            e.printStackTrace();
            Logs.printLogEdicao(e, this);
        }
    }

    public Membro(String matricula_atletica, String matricula_universidade,
            String nome, String CPF, String RG,
            String ocupacao, STATUS status, LocalDate membro_desde) {
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

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public LocalDate getMembro_desde() {
        return membro_desde;
    }
    
    public String getMembro_desde_formatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(membro_desde);
    }

    public void setMembro_desde(LocalDate membro_desde) {
        this.membro_desde = membro_desde;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }
    
    public String getDataNascimentoFormatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(dataNascimento);
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public LocalDate getVencimento() {
        return vencimento;
    }
    
    public String getVencimentoFormatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(vencimento);
    }

    public void setVencimento(LocalDate vencimento) {
        this.vencimento = vencimento;
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
        ret += "'" + getVencimentoFormatado() + "', ";
        ret += "'" + getCurso() + "', ";
        ret += "'" + getDataNascimentoFormatado() + "', ";
        ret += "'" + getTelefone() + "', ";
        ret += "'" + getEmail() + "')";
        return ret;
    }
    
    public String toSQLUpdate(){
        String ret = "";
        ret += "nome = '" + getNome() + "', ";
        ret += "matricula_universidade = '" + getMatricula_universidade() + "', ";
        ret += "rg = '" + getRG() + "', ";
        ret += "membro_desde = '" + getMembro_desde_formatado() + "', ";
        ret += "ocupacao = '" + getOcupacao() + "', ";
        ret += "status = '" + getStatus() + "'";        
        return ret;
    }
   
    @Override
    public String toString(){
        return getMatricula_atletica() + " | " + getNome();
    }
    
    @Override
    public int compareTo(Object o) {
        Membro dest = (Membro) o;
        int matDest = Integer.valueOf(dest.getMatricula_atletica());
        int myMat = Integer.valueOf(this.getMatricula_atletica());
        return myMat - matDest;
    }

}
