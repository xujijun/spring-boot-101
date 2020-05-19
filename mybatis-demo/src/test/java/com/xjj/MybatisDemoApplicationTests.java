package com.xjj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjj.dao.PersonDao;
import com.xjj.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MybatisDemoApplicationTests {
    final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PersonDao personDao;


    @Test
    void contextLoads() {
        log.warn("test");
    }

    @Test
    void dbTest1() throws JsonProcessingException {
        Person person = new Person();
        person.setSex('F');
        person.setLastName("Zhao");
        person.setFirstName("Si");

        personDao.insertPerson(person);

        Person person2 = personDao.getPersonById(2);
		log.info("person no 2 is: {}", objectMapper.writeValueAsString(person2));
    }

}
