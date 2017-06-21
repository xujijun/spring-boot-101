package com.xjj;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.core.lookup.MainMapLookup;
import org.apache.logging.log4j.core.lookup.MapLookup;
import org.apache.logging.log4j.message.MapMessage;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.xjj.config.MyProps;
import com.xjj.dao.PersonDAO;
import com.xjj.entity.Person;
import com.xjj.service.PersonService;

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
		//MainMapLookup.setMainArguments("projectName", "test-project-name");
		logger.trace("I am trace log.");
		logger.debug("I am debug log.");
		logger.warn("I am warn log.");
		logger.error("I am error log.");
		logger.info("I am info log.");
	}

	@Test
	public void dbTest() throws JsonProcessingException{
		Person person2 = personDAO.getPersonById(2);
		logger.info("person no 2 is: {}", objectMapper.writeValueAsString(person2));
		
		person2.setFirstName("八");
		personDAO.updatePersonById(person2);
		person2 = personDAO.getPersonById(2);
		logger.info("person no 2 after update is: {}", objectMapper.writeValueAsString(person2));
		assertThat(person2.getFirstName(), equalTo("八"));

	}

	@Autowired
	MongoTemplate mongoTemplate;
	
	@Test
	public void mongoSaveGetTest() throws JsonProcessingException {
		String dbName = mongoTemplate.getDb().getName();
		logger.info("db name: {}", dbName);
		assertThat(dbName, is("test"));
		
		for(int i=3;i<=25;i++){
			Person p = personDAO.getPersonById(i);
			if(p!=null){
				mongoTemplate.save(p);
			}
		}
		
		Criteria c = new Criteria();
		c.and("id").is(3);
		Person gotP = mongoTemplate.findOne(Query.query(c), Person.class);
		logger.debug("p={}", objectMapper.writeValueAsString(gotP));
		assertThat(gotP.getFirstName(), equalTo("七"));
		
		Set<String> collectionNames = mongoTemplate.getDb().getCollectionNames();
		logger.info("colection names: {}", collectionNames);
		assertThat(collectionNames, hasItem("person"));
	}
	
	@Test
	public void mongoAggregationTest() throws JsonProcessingException{
		Criteria c = new Criteria();
		c.and("sex").is("F");
		
		Aggregation aggr = Aggregation.newAggregation(
				Aggregation.match(c),
	            Aggregation.group("lastName").count().as("count")
	    );
		AggregationResults<BasicDBObject> aggrResult = mongoTemplate.aggregate(aggr, "person", BasicDBObject.class);
		if(!aggrResult.getMappedResults().isEmpty()){
			for(BasicDBObject obj : aggrResult.getMappedResults()){
				logger.info("count by first name: {}", objectMapper.writeValueAsString(obj));
			}
		}	
	}

	@Autowired
	RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void redisTest() {
		String key = "redisTestKey";
		String value = "I am test value";
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		//数据插入测试：
		opsForValue.set(key, value);
		String valueFromRedis = opsForValue.get(key);
		logger.info("redis value after set: {}", valueFromRedis);
		assertThat(valueFromRedis, is(value));
		
		//数据删除测试：
		redisTemplate.delete(key);
		valueFromRedis = opsForValue.get(key);
		logger.info("redis value after delete: {}", valueFromRedis);
		assertThat(valueFromRedis, equalTo(null));
	}
	
	@Autowired
	PersonService personService;
	
	@Test
	public void localCacheTest() throws JsonProcessingException, InterruptedException{
		System.out.println("第一次："); //从数据库中获取
		Person p = personService.getPersonById(2);
		logger.info("1st time: {}", objectMapper.writeValueAsString(p));
		
		System.out.println("第二次："); //从缓存中获取
		p = personService.getPersonById(2);
		logger.info("2nd time: {}", objectMapper.writeValueAsString(p));
		
		Thread.sleep(5000);
		
		System.out.println("第三次："); //5秒钟超时后，从数据库中获取
		p = personService.getPersonById(2);
		logger.info("3rd time: {}", objectMapper.writeValueAsString(p));

		System.out.println("第四次："); //从缓存中获取
		p = personService.getPersonById(2);
		logger.info("4th time: {}", objectMapper.writeValueAsString(p));

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
