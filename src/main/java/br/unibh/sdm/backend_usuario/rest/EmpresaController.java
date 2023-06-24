package br.unibh.sdm.backend_usuario.rest;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.unibh.sdm.backend_usuario.entidades.Empresa;
import br.unibh.sdm.backend_usuario.negocio.EmpresaService;

/**
 * Classe contendo as definições de serviços REST/JSON para Criptomoeda
 * @author jhcru
 *
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "empresa")
public class EmpresaController {
   
    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService){
        this.empresaService=empresaService;
    }

    @GetMapping
    public List<Empresa> getEmpresas(){
        return empresaService.getEmpresas();
    }
    
    @GetMapping(value="{id}")
    public Empresa getEmpresaById(@PathVariable String id) throws Exception{
        if(!ObjectUtils.isEmpty(id)){
           return empresaService.getEmpresaByCodigo(id);
        }
        throw new Exception("Empresa com codigo "+id+" nao encontrada");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Empresa createEmpresa(@RequestBody @NotNull Empresa empresa) throws Exception {
         return empresaService.saveEmpresa(empresa);
    }
    
    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Empresa updateCEmpresa(@PathVariable String id, 
    		@RequestBody @NotNull Empresa empresa) throws Exception {
    	if (!id.equals(empresa.getId())) {
    		throw new Exception("Empresa "+id+" nao está correto");
    	}
    	if (!empresaService.isEmpresaExists(empresa)) {
    		throw new Exception("Empresa com codigo "+id+" não existe");
    	}
        return empresaService.saveEmpresa(empresa);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "{id}")
    public boolean updateCEmpresa(@PathVariable String id) throws Exception {
    	if (!empresaService.isEmpresaExists(id)) {
    		throw new Exception("Empresa com codigo "+id+" não existe");
    	} 
    	empresaService.deleteEmpresa(id);
        return true;
    }
    
}