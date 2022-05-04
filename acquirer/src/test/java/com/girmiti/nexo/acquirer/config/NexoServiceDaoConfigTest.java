package com.girmiti.nexo.acquirer.config;

import java.security.GeneralSecurityException;

import javax.persistence.EntityManagerFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;

import com.girmiti.nexo.util.AESEncConfig;
import com.girmiti.nexo.util.AcquirerProperties;
import com.zaxxer.hikari.HikariDataSource;

@RunWith(MockitoJUnitRunner.Silent.class)
public class NexoServiceDaoConfigTest {
	
	@InjectMocks
	NexoServiceDaoConfig nexoServiceDaoConfig;
	
	@Mock
	AESEncConfig aESEncConfig;
	
	@Mock
	HikariDataSource datasources ;
	
	@Mock
	HibernateJpaVendorAdapter hibernateJpaVendorAdapter;
	
	@Mock
	LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean;
	
	@Mock
	EntityManagerFactory emf;
	
	@Test
	public void testDataSourceException() throws GeneralSecurityException {
		ReflectionTestUtils.setField(nexoServiceDaoConfig,"driverClass","Ghhs6k5hd3gNhGgJY7MS4xRbp82omY8Ma61DUEROUDi52zR2w7qFDkEC5TZj3jvc");
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"jdbcUrl","CaclBeQEKAEmE5N55Zpo1J73bcrVlUaDgP5ODcX2j89DNGuyMg2fVq//zX0i/gFkwzfxcWRG4AShntkqQQa55d6Q1szuDDVy+i4w2ziJftRTIN1x6qv691b3/MyaMUPDfxE7JiBZ74w4KehPZyaFQg==");
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"user","XmhM8NzWmiZZVjN3wbkD0onfjohcpl3lJ5rdopmyq0o3+m9CwuciV4cv0ccBthJq");
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"password","3WUA8oD80vNmDH3hHYhXgBmbUFw/ORWxR+hW00oYkTh1pKYHvllITlGhjfQwhrZ3");
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"maxPoolSize",5);
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"minPoolSize",1);
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"maxLifetimeMs",180000);
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"leakDetectionThreshold",1);
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"poolName","CHATAKNEXOHikariCP");
		 ReflectionTestUtils.setField(nexoServiceDaoConfig,"idleConnectionTestPeriod",60);
		HikariDataSource response = nexoServiceDaoConfig.dataSource();
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testJpaVendorAdapter() throws GeneralSecurityException {
		HibernateJpaVendorAdapter response = nexoServiceDaoConfig.jpaVendorAdapter();
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testEntityManagerFactory() throws GeneralSecurityException {
		LocalContainerEntityManagerFactoryBean response = nexoServiceDaoConfig.entityManagerFactory();
		Assert.assertNotNull(response);
	}
	
	@Test
	public void testTransactionManager() throws GeneralSecurityException {
		BaseUrlConfig baseUrlConfig=new BaseUrlConfig();
		baseUrlConfig.setAcquirerService("acq");
		baseUrlConfig.setZuul("zull");
		baseUrlConfig.getAcquirerService();
		baseUrlConfig.getZuul();
		PlatformTransactionManager response = nexoServiceDaoConfig.transactionManager(emf);
		Assert.assertNotNull(response);
	}

}
