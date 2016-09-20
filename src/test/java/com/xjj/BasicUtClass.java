package com.xjj;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicUtClass {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected final ObjectMapper objectMapper = new ObjectMapper();

}
