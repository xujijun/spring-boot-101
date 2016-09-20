package com.xjj;

import java.util.Arrays;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Web 页面测试
 * @author Xu
 *
 */
//@WebIntegrationTest("server.port:0") //使用随机的port，也可以用这个参数：(randomPort = true)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebTests extends BasicUtClass{
	@LocalServerPort //spring boot 1.4 才支持
	private int port;
	//@Value("${local.server.port}")
	//private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	//private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void webTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:"+port+"/web/hi", HttpMethod.GET,	requestEntity, String.class);
		System.out.println("port is: " + port);
		System.out.println("headers: " + responseEntity.getHeaders());
		System.out.println(responseEntity.getBody());
	}
}
