package com.xjj.dao;

import com.xjj.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PersonDao {
	
	@Select("SELECT id, first_name AS firstName, last_name AS lastName, birth_date AS birthDate, sex, phone_no AS phoneNo"
			+ " FROM test.t_person WHERE id=#{0};")
	Person getPersonById(int id);
	
	int insertPerson(Person person);
	
	int updatePersonById(Person person);

	int updatePersonByPhoneNo(Person person);

	List<Person> selectUnionPerson(@Param("ids") List<Integer> ids);
}
