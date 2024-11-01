package com.cadastro.cadastropf.service;

import com.cadastro.cadastropf.entity.Pessoa;
import com.cadastro.cadastropf.entity.PessoaFisica;
import com.cadastro.cadastropf.repository.PessoaFisicaRepository;
import com.cadastro.cadastropf.repository.PessoaRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class PessoaService {
    
    @Autowired
    private PessoaRepository pessoaRepository;
    
    @Autowired
    private PessoaFisicaRepository pessoaFisicaRepository;
    
    @Autowired
    private Mensagem mensagem;
    
    @Autowired
    private ValidaPessoa validaPessoa;
    
    @Value("${cadastropf.pagina.reg}")
    private int regPaginas;
    
    
    public Object incluirPessoa(Object pessoa){
        
        mensagem = validaPessoa.validarPessoas(pessoa);
        if(mensagem.getObject() == null){
            return mensagem;
        }
        Object obj = mensagem.getObject();        
        switch (obj) {
            case PessoaFisica pf -> {
                mensagem.setFuncao("Incluir nova Pessoa PF");
                if(pessoaFisicaRepository.findByCpf(pf.getCpf()) != null){
                    mensagem.setDescrição("Pessoa com CPF já Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Incluir nova Pessoa PF");
                    mensagem.setObject(pessoaFisicaRepository.save(pf).getIdPessoa());
                }
            }
            default -> {
                mensagem.setFuncao("Incluir PF.");
                mensagem.setDescrição("Objeto Pessoa PF não definido.");
                mensagem.setObject(null);
            }            
        }
        return mensagem;
    }
    
    public Object consultarPessoa(Long IdPessoa) {
        
        if(IdPessoa == null) { return null; }
        Optional<Pessoa> pes = pessoaRepository.findById(IdPessoa);
        mensagem.setFuncao("Consulta Pessoa por ID");
        if(pes.isPresent()){
            mensagem.setDescrição("Consulta Pessoa PF com ID: " + IdPessoa);
            mensagem.setObject(pes.get());
        } else {
            mensagem.setDescrição("Pessoa não cadastrada no sistema. ID: " + IdPessoa);
            mensagem.setObject(null);
        }
        return mensagem;
    }
    
    
    public Object consultarPessoaCpf(String cpf) {
        
        if(cpf == null) { return null; }
        PessoaFisica pf = (PessoaFisica) pessoaFisicaRepository.findByCpf(cpf);
        mensagem.setFuncao("Consulta Pessoa Física");
        if( pf != null ){
            mensagem.setDescrição("Consulta de Pessoa PF por CPF: " + cpf);
            mensagem.setObject(pf);
        } else {
            mensagem.setDescrição("Pessoa não cadastrada no sistema com CPF: " + cpf);
            mensagem.setObject(null);
        }
        return mensagem;
    }
    
    
    public Object listarPessoasOrdenadosPF(String ordem, Integer pagina){            
   
            pagina = (pagina -1)* regPaginas;
            mensagem.setFuncao("Consulta Pessoas PF ordenados");
            switch(ordem){
                case("cpf") ->{
                    mensagem.setDescrição("Consulta Pessoas PF ordenados por " + ordem);
                    mensagem.setObject(pessoaFisicaRepository.findAllOrderByCpf(pagina,regPaginas));
                }
                case("nome") ->{
                    mensagem.setDescrição("Consulta Pessoas PF ordenados por " + ordem);
                    mensagem.setObject(pessoaFisicaRepository.findAllOrderByNome(pagina,regPaginas));
                }
                default ->{
                    mensagem.setDescrição("Consulta incorreta. Parâmetros aceitos: cpf ou nome");
                    mensagem.setObject(null);
                }
            }
            return mensagem;
    }
    
    
    public Object excluirPessoa(Long IdPessoa){
        
        mensagem.setFuncao("Excluir Pessoa PF cadastrado.");
        try {
            pessoaRepository.deleteById(IdPessoa);
            mensagem.setDescrição("Pessoa excluída com sucesso, ID: " + IdPessoa);
            mensagem.setObject(true);
        } catch (Exception ex) {
            mensagem.setDescrição("Erro ao excluir Pessoa com ID: " + IdPessoa);
            mensagem.setObject(false);
        }
        return mensagem;
    }
    
    public Object atualizarPessoa(Object pessoa){
        
        mensagem = validaPessoa.validarPessoas(pessoa);
        if(mensagem.getObject() == null){
            return mensagem;
        }
        Object obj = mensagem.getObject();
        if( obj instanceof PessoaFisica pf ){       
                
                PessoaFisica pfatual = pessoaFisicaRepository.getReferenceById(pf.getIdPessoa());
                mensagem.setFuncao("Atualizar Pessoa PF cadastrado.");
                
                if( pfatual.getIdPessoa() == null){
                    mensagem.setDescrição("Pessoa PF não Cadastrado!");
                    mensagem.setObject(null);
                } else {
                    mensagem.setDescrição("Atualizar Pessoa PF com ID: " + pf.getIdPessoa());
                    pfatual.setTelefone(pf.getTelefone());
                    pfatual.setCpf(pf.getCpf());
                    pfatual.setEmail(pf.getEmail());
                    pfatual.setDataNasc(pf.getDataNasc());
                    pfatual.setNome(pf.getNome());
                    mensagem.setObject(pessoaFisicaRepository.save(pfatual).getIdPessoa());
                }
        } else {
                mensagem.setFuncao("Atualizar Pessoa PF cadastrado.");
                mensagem.setDescrição("Objeto Pessoa PF não definido.");
                mensagem.setObject(null);
            }
        return mensagem;        
        }
}
