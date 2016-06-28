package com.xjj.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.xjj.annotation.MyDbRepository;
import com.xjj.annotation.TestRepository;

/**
 * 配置MyBatis Mapper Scanner
 * @author XuJijun
 *
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

	/**
	 * - 设置SqlSessionFactory；
	 * - 设置dao所在的package路径；
	 * - 关联注解在dao类上的Annotation名字；
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer1() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
		mapperScannerConfigurer.setBasePackage("com.xjj.dao");
		mapperScannerConfigurer.setAnnotationClass(TestRepository.class);
		return mapperScannerConfigurer;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer2() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory2");
		mapperScannerConfigurer.setBasePackage("com.xjj.dao");
		mapperScannerConfigurer.setAnnotationClass(MyDbRepository.class);
		return mapperScannerConfigurer;
	}
	
}
