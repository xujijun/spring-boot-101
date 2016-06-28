package com.xjj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj.dao.PersonDAO;
import com.xjj.entity.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MySpringBootApplication.class)
public class MySpringBootApplicationTests {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	PersonDAO personDAO;
	
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
}
