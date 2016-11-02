package com.xjj.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.xjj.dao.PersonDAO;
import com.xjj.entity.Person;

@Service
public class PersonService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PersonDAO personDao;
	

	/**
	 * 根据id获取Person对象，使用缓存
	 * @param id
	 * @return Person对象
	 */
	@Cacheable(value="getPersonById", sync=true)
	public Person getPersonById(int id){
		logger.debug("getting data from database, personId={}", id);
		return personDao.getPersonById(id);
	}
	
}
