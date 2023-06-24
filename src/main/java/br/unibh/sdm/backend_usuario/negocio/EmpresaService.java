package br.unibh.sdm.backend_usuario.negocio;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.unibh.sdm.backend_usuario.entidades.Empresa;
import br.unibh.sdm.backend_usuario.persistencia.EmpresaRepository;

/**
 * Classe contendo a lógica de negócio para Criptomoeda
 * @author jhcru
 *
 */
@Service
public class EmpresaService {

    private static final Logger logger= LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private final EmpresaRepository empresaRepo;

    public EmpresaService(EmpresaRepository empresaRepository){
        this.empresaRepo=empresaRepository;
    }
    
    public List<Empresa> getEmpresas(){
        if(logger.isInfoEnabled()){
            logger.info("Buscando todos os objetos");
        }
        Iterable<Empresa> lista = this.empresaRepo.findAll();
        if (lista == null) {
        	return new ArrayList<Empresa>();
        }
        return IteratorUtils.toList(lista.iterator());
    }

    public Empresa getEmpresaByCodigo(String codigo){
        if(logger.isInfoEnabled()){
            logger.info("Buscando Empresa com o codigo {}",codigo);
        }
        Optional<Empresa> retorno = this.empresaRepo.findById(codigo);
        if(!retorno.isPresent()){
            throw new RuntimeException("Empresa com o codigo "+codigo+" nao encontrada");
        }
        return retorno.get();
    }
    
    public Empresa getEmpresaByNome(String nome){
        if(logger.isInfoEnabled()){
            logger.info("Buscando Empresa com o nome {}",nome);
        }
        List<Empresa> lista = this.empresaRepo.findByNome(nome);
        if(lista == null || lista.isEmpty()){
            throw new RuntimeException("Empresa com o nome "+nome+" nao encontrada");
        }
        return lista.get(0);
    }

    public Empresa saveEmpresa(Empresa empresa){
        if(logger.isInfoEnabled()){
            logger.info("Salvando Empresa com os detalhes {}",empresa.toString());
        }
        return this.empresaRepo.save(empresa);
    }
    
    public void deleteEmpresa(String codigo){
        if(logger.isInfoEnabled()){
            logger.info("Excluindo Empresa com id {}",codigo);
        }
        this.empresaRepo.deleteById(codigo);
    }

    public boolean isEmpresaExists(Empresa empresa){
    	Optional<Empresa> retorno = this.empresaRepo.findById(empresa.getId());
        return retorno.isPresent() ? true:  false;
    }

    public boolean isEmpresaExists(String codigo){
    	Optional<Empresa> retorno = this.empresaRepo.findById(codigo);
        return retorno.isPresent() ? true:  false;
    }
}