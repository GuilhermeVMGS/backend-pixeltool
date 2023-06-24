package br.unibh.sdm.backend_usuarios.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

import br.unibh.sdm.backend_usuario.entidades.Empresa;
import br.unibh.sdm.backend_usuario.persistencia.EmpresaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PropertyPlaceholderAutoConfiguration.class, EmpresaTests.DynamoDBConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class EmpresaTests {
    
    private static Logger LOGGER = LoggerFactory.getLogger(EmpresaTests.class);
    private SimpleDateFormat df = new SimpleDateFormat("dd/mm/yyyy");
	    
    @Configuration
	@EnableDynamoDBRepositories(basePackageClasses = { EmpresaRepository.class })
	public static class DynamoDBConfig {

		@Value("${amazon.aws.accesskey}")
		private String amazonAWSAccessKey;

		@Value("${amazon.aws.secretkey}")
		private String amazonAWSSecretKey;

		public AWSCredentialsProvider amazonAWSCredentialsProvider() {
			return new AWSStaticCredentialsProvider(amazonAWSCredentials());
		}
		@Bean
		public AWSCredentials amazonAWSCredentials() {
			return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
		}

		@Bean
		public AmazonDynamoDB amazonDynamoDB() {
			return AmazonDynamoDBClientBuilder.standard().withCredentials(amazonAWSCredentialsProvider())
					.withRegion(Regions.US_EAST_1).build();
		}
	}
    
	@Autowired
	private EmpresaRepository repository;

@Test
	public void teste1Criacao() throws ParseException {
		LOGGER.info("Criando objetos...");
		Empresa e1 = new Empresa("PixelTools", "pixeltools@gmail.com", "31992694183", "19172113000100");
		repository.save(e1);

		LOGGER.info("Pesquisado todos");
		Iterable<Empresa> lista = repository.findAll();
		assertNotNull(lista.iterator());
		for (Empresa empresa : lista) {
			LOGGER.info(empresa.toString());
		}
		LOGGER.info("Pesquisado um objeto");
		List<Empresa> result = repository.findByNome("PixelTools");
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getCnpj(), "19172113000100");
		LOGGER.info("Encontrado: {}", result.get(0));
	}


		@Test
	public void teste2Exclusao() throws ParseException {
		Iterable<Empresa> lista = repository.findAll();
		for (Empresa empresa : lista) {
			LOGGER.info("Excluindo empresa cujo CNPJ e: "+empresa.getCnpj());
			repository.delete(empresa);
		}
		lista = repository.findAll();
		assertEquals(lista.iterator().hasNext(), false);
		LOGGER.info("Exclusão feita com sucesso");
	}
}