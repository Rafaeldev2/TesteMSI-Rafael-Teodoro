package com.cadastro.cadastropf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name="idPessoa")
public class PessoaFisica extends Pessoa{
    
    @Column(length = 11, nullable = false, unique=true)
    private String cpf;
    
    @Column(nullable = true)
    private Long telefone;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(nullable = true)
    private LocalDate dataNasc;

    public String getCpf() {
        return cpf;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
    
    public void setDataNasc(String dataNasc) {
        this.dataNasc = LocalDate.parse(dataNasc);
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }
}