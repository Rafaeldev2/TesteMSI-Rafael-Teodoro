package com.cadastro.cadastropf.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name="idPessoa")
public class PessoaFisica extends Pessoa{
    
    @Column(length = 11, nullable = false, unique=true)
    private String cpf;
    
    @Column(nullable = true)
    private Long telefone;
    
    @Column(nullable = true)
    private String DataNasc;

    public String getCpf() {
        return cpf;
    }

    public String getDataNasc() {
        return DataNasc;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNasc(String DataNasc) {
        this.DataNasc = DataNasc;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }
}