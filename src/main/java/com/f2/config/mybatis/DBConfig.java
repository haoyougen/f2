package com.f2.config.mybatis;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@EnableTransactionManagement
public class DBConfig implements EnvironmentAware {
	private RelaxedPropertyResolver propertyResolver;
	private static Logger log = LoggerFactory.getLogger(DBConfig.class);

	@Override
	public void setEnvironment(Environment env) {
		this.propertyResolver = new RelaxedPropertyResolver(env, "jdbc.");
	}

	@Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
	@Primary
	public DataSource dataSource() {
		log.debug("---------------Configruing  DataSource---------------");
		DruidDataSource datasource = new DruidDataSource();
		String url = propertyResolver.getProperty("url");
		String driver = propertyResolver.getProperty("driverClassName");
		String username = propertyResolver.getProperty("username");
		String password = propertyResolver.getProperty("password");

		log.info("--------------------url:" + url);
		log.info("--------------------driver:" + driver);
		log.info("--------------------username:" + username);

		datasource.setUrl(url);
		datasource.setDriverClassName(driver);
		datasource.setUsername(username);
		datasource.setPassword(password);

		return datasource;
	}
}
