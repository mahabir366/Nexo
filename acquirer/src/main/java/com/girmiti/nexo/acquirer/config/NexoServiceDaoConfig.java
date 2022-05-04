package com.girmiti.nexo.acquirer.config;

import java.security.GeneralSecurityException;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.girmiti.nexo.util.AESEncConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableWebMvc
@ComponentScan("com.girmiti.nexo.acquirer.dao")
@EnableJpaRepositories("com.girmiti.nexo.acquirer.dao.repository")
public class NexoServiceDaoConfig {

	@Value("${cw.db.driverClass}")
	private String driverClass;

	@Value("${cw.db.jdbcUrl}")
	private String jdbcUrl;

	@Value("${cw.db.user}")
	private String user;
	
	@Value("${cw.db.password}")
	private String password;
	
	@Value("${cw.db.initialPoolSize}")
	private Integer initialPoolSize;

	@Value("${cw.db.maxPoolSize}")
	private Integer maxPoolSize;

	@Value("${cw.db.minPoolSize}")
	private Integer minPoolSize;

	@Value("${cw.db.acquireIncrement}")
	private Integer acquireIncrement;

	@Value("${cw.db.acquireRetryAttempts}")
	private Integer acquireRetryAttempts;

	@Value("${cw.db.maxStatements}")
	private Integer maxStatements;

	@Value("${cw.db.checkoutTimeout}")
	private Integer checkoutTimeout;

	@Value("${cw.db.idleConnectionTestPeriod}")
	private Integer idleConnectionTestPeriod;

	@Value("${cw.db.maxLifetime}")
	private Integer maxLifetimeMs;

	@Value("${cw.db.leakDetectionThreshold}")
	private Integer leakDetectionThreshold;

	@Value("${cw.db.poolName}")
	private String poolName;

	@Bean
	public HikariDataSource dataSource() throws GeneralSecurityException {
		HikariDataSource datasource = new HikariDataSource();
		try {
			datasource.setDriverClassName(AESEncConfig.decrypt(driverClass));
			datasource.setJdbcUrl(AESEncConfig.decrypt(jdbcUrl));
			datasource.setUsername(AESEncConfig.decrypt(user));
			datasource.setPassword(AESEncConfig.decrypt(password));
			datasource.setMaximumPoolSize(maxPoolSize);
			datasource.setMinimumIdle(minPoolSize);
			datasource.setMaxLifetime(maxLifetimeMs);
			datasource.setLeakDetectionThreshold(leakDetectionThreshold);
			datasource.setPoolName(poolName);
			datasource.setIdleTimeout(idleConnectionTestPeriod);
			
		} catch (Exception e) {
			datasource.close();
		}
		return datasource;
	}

	@Bean
	public HibernateJpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.POSTGRESQL);
		vendorAdapter.setShowSql(Boolean.TRUE);
		vendorAdapter.setGenerateDdl(Boolean.FALSE);
		return vendorAdapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws GeneralSecurityException {
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource());
		entityManager.setJpaVendorAdapter(jpaVendorAdapter());
		entityManager.setPackagesToScan("com.girmiti.nexo.acquirer.dao.model");
		return entityManager;

	}
	
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf)
			throws GeneralSecurityException {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}
}
