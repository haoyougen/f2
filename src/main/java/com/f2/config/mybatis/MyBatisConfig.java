package com.f2.config.mybatis;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.pagehelper.PageHelper;

@Configuration
@AutoConfigureAfter({ DBConfig.class })
@EnableTransactionManagement
public class MyBatisConfig implements EnvironmentAware {
	private static Logger log = LoggerFactory.getLogger(MyBatisConfig.class);

	private RelaxedPropertyResolver propertyResolver;

	@Resource(name = "dataSource")
	private DataSource dataSource;

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "mybatis.");
	}

	@Bean(name = "sqlSessionFactory")
	@ConditionalOnMissingBean
	public SqlSessionFactory sqlSessionFactoryBean() {
		SqlSessionFactory result = null;
		SqlSessionFactoryBean factorybean = new SqlSessionFactoryBean();
		try {
			factorybean.setDataSource(dataSource);

			// factorybean.setConfigLocation(new
			// DefaultResourceLoader().getResource(configLocation));
			// factorybean.setDataSource(roundRobinDataSouceProxy());
			String aliaspackage = propertyResolver.getProperty("typeAliasesPackage");
			String mapperlocation = propertyResolver.getProperty("mapperLocations");
			//String configlocation = propertyResolver.getProperty("configLocation");

			log.info("aliaspackage:" + aliaspackage);
			log.info("mapperlocation:" + mapperlocation);
			//log.info("configlocation:" + configlocation);

			factorybean.setTypeAliasesPackage(aliaspackage);
			factorybean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperlocation));
		//	factorybean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configlocation));

			// 设置分页
			PageHelper pageHelper = new PageHelper();
			Properties properties = new Properties();
			properties.setProperty("reasonable", "true");
			properties.setProperty("supportMethodsArguments", "true");
			properties.setProperty("returnPageInfo", "check");
			properties.setProperty("params", "count=countSql");
			pageHelper.setProperties(properties);

			// 添加插件
			factorybean.setPlugins(new Interceptor[] { pageHelper });

			// 添加XML目录
			// ResourcePatternResolver resolver = new
			// PathMatchingResourcePatternResolver();
			// factorybean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			  result = factorybean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Bean(name = "sqlSessionTemplate")
	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate() {
		SqlSessionFactory factory =sqlSessionFactoryBean();
		System.out.println("factory:"+factory);
		return new SqlSessionTemplate(factory,ExecutorType.SIMPLE);
	}

	// @Bean
	// public RoundRobinRWRoutingDataSourceProxy roundRobinDataSouceProxy(){
	// RoundRobinRWRoutingDataSourceProxy proxy = new
	// RoundRobinRWRoutingDataSourceProxy();
	// proxy.setWriteDataSource(writeDataSource);
	// proxy.setReadDataSoures(readDataSources);
	// proxy.setReadKey("READ");
	// proxy.setWriteKey("WRITE");
	//
	// return proxy;
	// }
 

}
