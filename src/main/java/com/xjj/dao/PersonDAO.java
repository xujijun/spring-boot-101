package com.xjj.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.xjj.entity.Person;

@Mapper
public interface PersonDAO {
	
	@Select("SELECT id, first_name AS firstName, last_name AS lastName, birth_date AS birthDate, sex, phone_no AS phoneNo"
			+ " FROM test.t_person WHERE id=#{0};")
	public Person getPersonById(int id);
	
	public int insertPerson(Person person);
	
	public int updatePersonById(Person person);

	public int updatePersonByPhoneNo(Person person);
}
