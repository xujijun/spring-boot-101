package com.xjj.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.github.pagehelper.PageHelper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * DataSource、SqlSessionFactory和Transaction Manager 配置
 * @author XuJijun
 *
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer{
	private final static Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

	//数据库连接相关的参数：
	private String driverClassName = "com.mysql.jdbc.Driver";
	@Value("${mysql.ipPort}") private String jdbcIpPort; //从配置文件中获取
	private String jdbcUrl = "jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=UTF-8";
	private String userName = "root";
	private String password = "123456";
	
	//连接池相关的参数：
	//等待从连接池中获得连接的最大时长（毫秒），超过这个时长还没可用的连接则发生SQLException， 缺省:30秒
	private long connectionTimeout = 30000;
	//一个连接idle状态的最大时长（毫秒），超时则被释放（retired），缺省:10分钟
	private long idleTimeout = 600000;
	//一个连接的生命时长（毫秒），超时而且没被使用则被释放（retired），缺省:30分钟，建议设置比数据库超时时长少30秒以上，
	//参考MySQL wait_timeout参数（show variables like '%timeout%';）
	private long maxLifetime = 1765000;
	//连接池中允许的最大连接数。缺省值：10；推荐的公式：((core_count * 2) + effective_spindle_count)
	private int maximumPoolSize = 15;
	
	/**
	 * 配置dataSource，使用Hikari连接池 
	 */
	@Bean(destroyMethod = "close")
	@Primary
	public DataSource dataSource1(){
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(String.format(jdbcUrl, jdbcIpPort, "test"));
		config.setUsername(userName);
		config.setPassword(password);
		config.setConnectionTimeout(connectionTimeout); 
		config.setIdleTimeout(idleTimeout);
		config.setMaxLifetime(maxLifetime);
		config.setMaximumPoolSize(maximumPoolSize);
		
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}

	@Bean(destroyMethod = "close")  
	public DataSource dataSource2(){
		HikariConfig config = new HikariConfig();
		config.setDriverClassName(driverClassName);
		config.setJdbcUrl(String.format(jdbcUrl, jdbcIpPort, "my_db"));
		config.setUsername(userName);
		config.setPassword(password);
		config.setConnectionTimeout(connectionTimeout);
		config.setIdleTimeout(idleTimeout);
		config.setMaxLifetime(maxLifetime);
		config.setMaximumPoolSize(maximumPoolSize);
		
		HikariDataSource ds = new HikariDataSource(config);
		return ds;
	}

	/**
	 * 配置SqlSessionFactory：
	 * - 创建SqlSessionFactoryBean，并指定一个dataSource；
	 * - 设置这个分页插件：https://github.com/pagehelper/Mybatis-PageHelper；
	 * - 指定mapper文件的路径；
	 */
	@Bean
    public SqlSessionFactory sqlSessionFactory1() {
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource1());
		
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageHelper});
        
        try {
        	//指定mapper xml目录
        	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        	bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	@Bean
    public SqlSessionFactory sqlSessionFactory2() {
		
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource2());
		
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        bean.setPlugins(new Interceptor[]{pageHelper});
        
        try {
        	//指定mapper xml目录
        	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        	bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
			return bean.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * Transaction 相关配置
	 * 因为有两个数据源，所有使用ChainedTransactionManager把两个DataSourceTransactionManager包括在一起。
	 */
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		DataSourceTransactionManager dtm1 = new DataSourceTransactionManager(dataSource1());
		DataSourceTransactionManager dtm2 = new DataSourceTransactionManager(dataSource2());

		ChainedTransactionManager ctm = new ChainedTransactionManager(dtm1, dtm2);
		return ctm;
	}

}
