package com.cadastro.cadastropf.service;

import com.cadastro.cadastropf.entity.PessoaFisica;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ValidaPessoa {
    
    @Autowired
    private Mensagem mensagem;

    private Long idPessoa;
    
    public Mensagem validarPessoas(Object pessoa){
        
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();
        String pessoaJson = gson.toJson(pessoa);
        JsonObject pessoaJS = (JsonObject) JsonParser.parseString(pessoaJson);
        if(pessoaJS.has("idPessoa")){
            idPessoa = pessoaJS.get("idPessoa").getAsLong();
        } else if(pessoaJS.has("cpf")){
            String CPF = pessoaJS.get("cpf").getAsString();
            if(CPF.length() != 11 || ! CPF.chars().allMatch(Character::isDigit)){
                mensagem.setFuncao("Valida CPF");
                mensagem.setDescrição("CPF Inválido, Tamanho/Letras incorreto.");
                mensagem.setObject(null);
                return mensagem;
            }
            if( !pessoaJS.has("nome") ){
                mensagem.setFuncao("Valida Nome da Pessoa");
                mensagem.setDescrição("Nome da Pessoa não informado.");
                mensagem.setObject(null);
                return mensagem;
            }
            PessoaFisica pf = gson.fromJson(pessoaJS, PessoaFisica.class);
            pf.setIdPessoa(idPessoa);
            mensagem.setFuncao("Valida Pessoa Física");
            mensagem.setDescrição("Pessoa Física corretamente informada.");
            mensagem.setObject(pf);
            return mensagem;
        }
        mensagem.setFuncao("Validação de Pessoa Física");
        mensagem.setDescrição("Pessoa informado sem CPF ou nome");
        mensagem.setObject(null);
        return mensagem;
        
    }
    
}
