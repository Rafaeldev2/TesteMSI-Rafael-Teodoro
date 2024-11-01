package com.cadastro.cadastropf.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import static jakarta.persistence.DiscriminatorType.STRING;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Set;


@Table(name = "pessoa")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DISC", discriminatorType=STRING, length=20)
public class Pessoa implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long idPessoa;
    
    @Column(nullable = false)
    private String nome;
    
    @OneToMany(mappedBy="pessoa", fetch=FetchType.LAZY, orphanRemoval = true,cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Endereco> endereco;
    
    @Column(nullable = false, unique=true)
    private String email;
    
    @Column(nullable = false)
    private String passwd;

    public Long getIdPessoa() {
        return idPessoa;
    }

    public String getNome() {
        return nome;
    }

    public Set<Endereco> getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(Set<Endereco> endereco) {
        this.endereco = endereco;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void addEndereco(Endereco endereco) {
        endereco.setPessoa(this);
        this.endereco.add(endereco);
    }
}
