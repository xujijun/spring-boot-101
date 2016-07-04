package com.xjj;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj.dao.PersonDAO;
import com.xjj.entity.Person;
import com.xjj.util.XDateUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MySpringBootApplication.class)
@WebIntegrationTest("server.port:0") //使用随机的port，也可以用这个参数：(randomPort = true)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MySpringBootApplicationTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	//@LocalServerPort
	//private int port;
	@Value("${local.server.port}")
    int port;
	
	//@Autowired
	//private TestRestTemplate testRestTemplate;
	
	@Autowired
	PersonDAO personDAO;

	RestTemplate restTemplate = new TestRestTemplate();
	
	@Test
	public void contextLoads() {
		logger.trace("I am trace log.");
		logger.debug("I am debug log.");
		logger.warn("I am warn log.");
		logger.error("I am error log.");
	}

	@Test
	public void dbTest() throws JsonProcessingException{
		Person person2 = personDAO.getPersonById(2);
		logger.info("person no 2 is: {}", objectMapper.writeValueAsString(person2));
		
		person2.setFirstName("八");
		personDAO.updatePersonById(person2);
		person2 = personDAO.getPersonById(2);
		logger.info("person no 2 after update is: {}", objectMapper.writeValueAsString(person2));
	}
	
	@Autowired
	VelocityEngine velocityEngine;
	
	@Test
	public void velocityTest(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", XDateUtils.nowToString());
		model.put("message", "这是测试的内容。。。");
		model.put("toUserName", "张三");
		model.put("fromUserName", "老许");
		System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", model));
	}
	
	@Test
	public void webTest(){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.TEXT_HTML));
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				"http://localhost:"+port+"/web/hi", HttpMethod.GET,	requestEntity, String.class);
		System.out.println("port is: " + port);
		System.out.println(responseEntity.getHeaders());
		System.out.println(responseEntity.getBody());
	}
}
