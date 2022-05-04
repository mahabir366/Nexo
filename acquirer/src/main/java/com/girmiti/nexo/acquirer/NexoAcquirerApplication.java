package com.girmiti.nexo.acquirer;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.girmiti.nexo.server.corelauncher.ClientHandler;
import com.girmiti.nexo.util.AcquirerProperties;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
public class NexoAcquirerApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(NexoAcquirerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(NexoAcquirerApplication.class, args);
	}
	
	@Bean
	public AcquirerProperties propertyConfigurers() {
		AcquirerProperties ppc = new AcquirerProperties();
		Resource[] resources = new ClassPathResource[] { new ClassPathResource("/bootstrap.properties"),
				new ClassPathResource("/application.properties") };
		ppc.setLocations(resources);
		ppc.setIgnoreUnresolvablePlaceholders(true);
		return ppc;
	}
	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		return objectMapper;
	}
	
	@Bean
	public ClientHandler getClientHandler(){
		return new ClientHandler(8081);
	}
	
	@Bean
	public CustomScopeConfigurer servletCustomScopeConfigurer(
			org.springframework.cloud.context.scope.refresh.RefreshScope refreshScope) {
		CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
		customScopeConfigurer.addScope("refresh", refreshScope);
		return customScopeConfigurer;
	}
	

}