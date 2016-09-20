package com.xjj;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.xjj.config.MyProps;
import com.xjj.dao.PersonDAO;
import com.xjj.entity.Person;

public class MySpringBootApplicationTests extends BasicUtClass{
	@Autowired
	private PersonDAO personDAO;
	@Autowired
	private MyProps myProps; 
	
	@Test
	public void common() throws JsonProcessingException{
		//JSONObject o = new JSONObject();
		//objectMapper.readValue(src, valueType)
		Map<String, Object> m = new HashMap<>();
		m.put("k", "<我是中文>");
		String s = objectMapper.writeValueAsString(m);
		System.out.println(s);
	}
	
	@Test
	public void propsTest() throws JsonProcessingException {
		System.out.println("simpleProp: " + myProps.getSimpleProp());
		System.out.println("arrayProps: " + objectMapper.writeValueAsString(myProps.getArrayProps()));
		System.out.println("listProp1: " + objectMapper.writeValueAsString(myProps.getListProp1()));
		System.out.println("listProp2: " + objectMapper.writeValueAsString(myProps.getListProp2()));
		System.out.println("mapProps: " + objectMapper.writeValueAsString(myProps.getMapProps()));
	}
	
	@Test 
	public void logTest(){
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
	
/*	@Autowired
	VelocityEngine velocityEngine;
	
	@Test
	public void velocityTest(){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("time", XDateUtils.nowToString());
		model.put("message", "这是测试的内容。。。");
		model.put("toUserName", "张三");
		model.put("fromUserName", "老许");
		System.out.println(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "welcome.vm", "UTF-8", model));
	}*/

}
