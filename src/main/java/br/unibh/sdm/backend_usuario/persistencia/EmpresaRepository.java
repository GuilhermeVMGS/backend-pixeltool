package br.unibh.sdm.backend_usuario.persistencia;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import br.unibh.sdm.backend_usuario.entidades.Empresa;

import java.util.List;


@EnableScan
public interface EmpresaRepository extends CrudRepository<Empresa, String>{
    
    List<Empresa> findByNome (String nome);
}