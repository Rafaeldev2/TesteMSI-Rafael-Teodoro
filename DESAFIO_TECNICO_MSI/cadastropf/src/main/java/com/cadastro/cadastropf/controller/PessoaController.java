package com.cadastro.cadastropf.controller;

import com.cadastro.cadastropf.service.Mensagem;
import com.cadastro.cadastropf.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class PessoaController {
 
    @Autowired
    private PessoaService pessoaService;
    
    @Autowired
    private Mensagem mensagem;
    
   
    @Operation(summary = "Consulta Pessoa PF por ID",description = "Retorna Pessoa PF por ID)")
    @CrossOrigin(origins = "*")
    @GetMapping("/pessoa/{id}")
    public ResponseEntity<Object> consultarPessoa(@PathVariable(value = "id") long id)
    {
        mensagem = (Mensagem) pessoaService.consultarPessoa(id);
        if( mensagem.getObject() instanceof Object ) {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        } else {            
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }
    
    @Operation(summary = "Consulta Pessoa PF por CPF",description = "Retorna Pessoa PF por CPF)")
    @CrossOrigin(origins = "*")
    @GetMapping("/pessoapf/{cpf}")
    public ResponseEntity<Object> consultarPessoaCpf(@PathVariable(value = "cpf") String cpf)
    {
        mensagem = (Mensagem) pessoaService.consultarPessoaCpf(cpf);
        if( mensagem.getObject() instanceof Object ) {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        } else {            
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }

    
    @Operation(summary = "Lista de Pessoas Físicas classificados por nome ou cpf e paginado",
               description = "Retorna Lista de Pessoas Físicas classificados por nome ou cpf por Páginas")
    @CrossOrigin(origins = "*")
    @GetMapping("/pessoapf/lista/{ordem}/{pagina}")
    public ResponseEntity<Object> consultaPessoaOrdenadosPF(@PathVariable(value = "ordem") String ordem,
                                                              @PathVariable(value = "pagina") Integer pagina) {
        if(pagina == null) { pagina = 1; }
        mensagem = (Mensagem) pessoaService.listarPessoasOrdenadosPF(ordem, pagina);
        if( mensagem.getObject() instanceof List list ){ 
            return new ResponseEntity<>(list,HttpStatus.OK); 
        } else {
            mensagem.setFuncao("Listar Pessoas Físicas ordenados");
            mensagem.setDescrição("Listar Pessoas Físicas ordenados por ("+ ordem + ") não encontrados!");
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem , HttpStatus.NOT_FOUND);            
        }
    }
    
    @Operation(summary = "Cadastra nova pessoa", description = "Cadastra nova pessoa")
    @CrossOrigin(origins = "*")
    @PostMapping("/pessoa")
    public ResponseEntity<Object> novaPessoa(@Valid @RequestBody Object obj) {
    	
        mensagem = (Mensagem) pessoaService.incluirPessoa(obj);
        if( !(mensagem.getObject() instanceof Long) ){
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        }
    }
    
    @Operation(summary = "Atualiza Pessoa PF já cadastrado, por ID",
               description = "Atualiza Pessoa PF já cadastrado, por ID")
    @CrossOrigin(origins = "*")
    @PutMapping("/pessoa")
    public ResponseEntity<Object> atualizarPessoa( @Valid @RequestBody Object atualizaPessoa )
    {
        mensagem = (Mensagem) pessoaService.atualizarPessoa(atualizaPessoa);
        if( (mensagem.getObject() instanceof Long) ){
            return new ResponseEntity<>(mensagem.getObject(), HttpStatus.OK);
        }
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
    }
    
    @Operation(summary = "Exclui Pessoa já cadastrado, por ID",
               description = "Exclui Pessoa já cadastrado, por ID")
    @CrossOrigin(origins = "*")
    @DeleteMapping("/pessoa/{id}")
    public ResponseEntity<Object> excluirCliente(@PathVariable(value = "id") long id){
        
        mensagem = (Mensagem) pessoaService.excluirPessoa(id);
        if ( mensagem.getObject().equals(true) ){
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } else {
            mensagem.setObject(null);
            return new ResponseEntity<>(mensagem, HttpStatus.NOT_FOUND);
        }
    }
}
