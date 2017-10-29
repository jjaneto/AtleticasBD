package utils;

import java.util.Date;

/**
 *
 * @author jjaneto
 */
public class Membro {
    
    private String matricula_atletica;
    private String matricula_universidade;
    private String nome;
    private String CPF;
    private String ocupacao;
    private String status;
    private Date membro_desde;

    public Membro() {
    }

    public Membro(String matricula_atletica, String matricula_universidade, 
            String nome, String CPF, String ocupacao, 
            String status, Date membro_desde) {
        this.matricula_atletica = matricula_atletica;
        this.matricula_universidade = matricula_universidade;
        this.nome = nome;
        this.CPF = CPF;
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

    public Date getMembro_desde() {
        return membro_desde;
    }

    public void setMembro_desde(Date membro_desde) {
        this.membro_desde = membro_desde;
    }
    
}
