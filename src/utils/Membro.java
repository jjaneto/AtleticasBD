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
        A_RECEBER_CARTEIRA("Sem carteirinha"),
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
    
    public enum MODALIDADE{
        ANUAL("Anual"),
        SEMESTRAL("Semestral"),
        NAO_DEFINIDO("Não definida");        

        private final String value;
        
        private MODALIDADE(String value) {
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
    private LocalDate ultimaAssociacao;
    private LocalDate recebimentoCarteira;
    private boolean recebeu_carteira;
    private STATUS status;
    private MODALIDADE modalidade;

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
            this.ultimaAssociacao = LocalDate.parse(st.getString("ultima_associacao"),
                                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if((recebeu_carteira = st.getBoolean("recebeu_carteira"))){
                if(LocalDate.now().isAfter(vencimento)){
                    this.status = STATUS.DEVENDO;
                }else if(ChronoUnit.DAYS.between(LocalDate.now(), vencimento) <= (long) 30){
                    this.status = STATUS.A_VENCER;
                }else{
                    this.status = STATUS.PAGO;
                }
            }else{
                this.status = STATUS.A_RECEBER_CARTEIRA;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao setar atributo do membro durante sua inicializacao!");
            System.err.println("Salvando o log do erro no arquivo de erros...");
            e.printStackTrace();
            Logs.printLogEdicao(e, this);
        }
    }
    
    public Membro(Membro mbr){
        this.CPF = mbr.getCPF();
        this.RG = mbr.getRG();
        this.curso = mbr.getCurso();
        this.dataNascimento = mbr.getDataNascimento();
        this.email = mbr.getEmail();
        this.matricula_atletica = mbr.getMatricula_atletica();
        this.matricula_universidade = mbr.getMatricula_universidade();
        this.membro_desde = mbr.getMembro_desde();
        this.modalidade = mbr.getModalidade();
        this.nome = mbr.getNome();
        this.ocupacao = mbr.getOcupacao();
        this.recebeu_carteira = mbr.isRecebeu_carteira();
        this.recebimentoCarteira = mbr.getRecebimentoCarteira();
        this.status = mbr.getStatus();
        this.telefone = mbr.getTelefone();
        this.ultimaAssociacao = mbr.getUltimaAssociacao();
        this.vencimento = mbr.getVencimento();
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

    public MODALIDADE getModalidade() {
        return modalidade;
    }

    public void setModalidade(MODALIDADE modalidade) {
        this.modalidade = modalidade;
    }

    public LocalDate getUltimaAssociacao() {
        return ultimaAssociacao;
    }
    
    public String getUltimaAssociacao_formatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(ultimaAssociacao);
    }

    public void setUltimaAssociacao(LocalDate ultimaAssociacao) {
        this.ultimaAssociacao = ultimaAssociacao;
    }

    public LocalDate getRecebimentoCarteira() {
        return recebimentoCarteira;
    }
    
    public String getRecebimentoCarteira_formatado(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(recebimentoCarteira);
    }

    public void setRecebimentoCarteira(LocalDate recebimentoCarteira) {
        this.recebimentoCarteira = recebimentoCarteira;
    }

    public boolean isRecebeu_carteira() {
        return recebeu_carteira;
    }

    public void setRecebeu_carteira(boolean recebeu_carteira) {
        this.recebeu_carteira = recebeu_carteira;
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
        ret += "'" + getEmail() + "', ";
        ret += "'" + getModalidade() + "', ";
        ret += "'" + getUltimaAssociacao_formatado() + "', ";
        ret += "'" + getRecebimentoCarteira_formatado() + "', ";
        ret += "'" + isRecebeu_carteira() + "')";
        return ret;
    }
    
    public String toSQLUpdate(){
        String ret = "";
        ret += "nome = '" + getNome() + "', ";
        ret += "matricula_universidade = '" + getMatricula_universidade() + "', ";
        ret += "rg = '" + getRG() + "', ";
        ret += "membro_desde = '" + getMembro_desde_formatado() + "', ";
        ret += "ocupacao = '" + getOcupacao() + "', ";
//        ret += "status = '" + getStatus() + "', ";
        ret += "cpf = '" + getCPF() + "', ";
        ret += "vencimento = '" + getVencimentoFormatado() + "', ";
        ret += "curso = '" + getCurso() + "', ";
        ret += "nascimento = '" + getDataNascimentoFormatado() + "', ";
        ret += "telefone = '" + getTelefone() + "', ";
        ret += "email = '" + getEmail() + "', ";
        ret += "modalidade = '" + getModalidade() + "', ";
        ret += "ultima_associacao = '" + getUltimaAssociacao_formatado() + "', ";
        ret += "confirmacao_carteira = '" + getRecebimentoCarteira_formatado() + "', ";
        ret += "recebeu_carteira = '" + isRecebeu_carteira() + "'";
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
