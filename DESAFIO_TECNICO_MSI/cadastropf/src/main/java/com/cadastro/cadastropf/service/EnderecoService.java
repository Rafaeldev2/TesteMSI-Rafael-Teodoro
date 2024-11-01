package com.cadastro.cadastropf.service;

import com.cadastro.cadastropf.entity.Endereco;
import com.cadastro.cadastropf.entity.Pessoa;
import com.cadastro.cadastropf.repository.EnderecoRepository;
import com.cadastro.cadastropf.repository.PessoaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {
    
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Value("${url.viacep}")
    private String urlViacep;
    
    
    public List<Endereco> listarEnderecos(){
        
        return enderecoRepository.findAll();
    }
    
    public Optional<Endereco> consultaEndereco(Long ID){
        
        return enderecoRepository.findById(ID);        
    }
    
    public Long incluirEndereco(Endereco end, Long pessoaId){
        
        Pessoa pes = pessoaRepository.getReferenceById(pessoaId);
        end.setPessoa(pes);
        Long endID = enderecoRepository.save(end).getID();
        if(endID !=null){
            Set<Endereco> enderecos = pes.getEndereco();
            if(enderecos == null){
                enderecos = (Set<Endereco>) new ArrayList<Endereco>();                
            }
            enderecos.add(end);
            pes.setEndereco(enderecos);
            pessoaRepository.save(pes);
        }
        return endID;
    }
    
    public Boolean atualizarEndereco(Endereco end){     
                
        if(enderecoRepository.existsById(end.getID())){
           Endereco atualend = enderecoRepository.getReferenceById(end.getID());
           atualend.setLogradouro(end.getLogradouro());
           atualend.setNumero(end.getNumero());
           atualend.setBairro(end.getBairro());
           atualend.setComplemento(end.getComplemento());
           atualend.setCidade(end.getCidade());
           atualend.setEstado(end.getEstado());
           enderecoRepository.save(atualend);
           return true;
        } else {
            return false;            
        }       
    }
    
    public Boolean excluirEndereco(Long ID){
        
        if(enderecoRepository.existsById(ID)){
            enderecoRepository.deleteById(ID);
            return true;
        }
        return false;
    }
    
    public String consultaApiCEP(String cep){
        
        try{
            RestTemplate restTemplate = new RestTemplate();
            String urlconsulta = urlViacep + cep + "/json/";
            ResponseEntity<String> response = restTemplate.getForEntity(urlconsulta,String.class);
            return response.getBody();
        } catch (HttpClientErrorException ex){
            return "\"erro\":\"Erro ao chamar API ViaCEP: " + ex.getLocalizedMessage() + "\"";
        }        
    }
}
