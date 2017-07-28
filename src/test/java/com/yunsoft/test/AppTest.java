package com.yunsoft.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelan.app.jdbc.dao.JdbcDao;
import com.kelan.app.service.PersonService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class AppTest {
	@Inject
	PersonService personService;
	@Autowired
	JdbcDao  jdbcDao;
	@Test
	public void test() throws JsonProcessingException {
		ObjectMapper mapper=new ObjectMapper();
		
/*		List<Tuple> list=personService.getPersonAndCity();
		for(Tuple tp:list) {
			System.out.println(tp.get(QPerson.person).getUsername()+"=="+tp.get(QCity.city).getName());
		}*/
		
		System.out.println(mapper.writeValueAsString(jdbcDao.getAllUser()));
	}
}