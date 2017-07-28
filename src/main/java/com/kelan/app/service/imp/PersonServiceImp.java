package com.kelan.app.service.imp;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import com.kelan.app.entity.QCity;
import com.kelan.app.entity.QPerson;
import com.kelan.app.entity.QPersonOfCity;
import com.kelan.app.jpa.querydsl.dao.PersonQuerydslDao;
import com.kelan.app.service.PersonService;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午7:05:30
* 类说明
*/
@Service
public class PersonServiceImp implements PersonService {
	
	@Inject
	PersonQuerydslDao query;
	
	@Inject  
    @PersistenceContext  
    private EntityManager entityManager; 
	private JPAQueryFactory queryFactory;  
 
    @PostConstruct  
    public void init() {  
        queryFactory = new JPAQueryFactory(entityManager);  
    }  

	@Override
	public List<Tuple> getPersonAndCity() {
		Predicate predicate=null;
		JPAQuery<Tuple> jpaQuery = queryFactory.select(QPerson.person,QCity.city)
				.from(QPersonOfCity.personOfCity)
                .rightJoin(QPerson.person)
                .on(QPerson.person.id.longValue().eq(QPersonOfCity.personOfCity.pid))
                .rightJoin(QCity.city)
                .on(QPersonOfCity.personOfCity.cid.eq(QCity.city.id));  
		jpaQuery.where(predicate);  
		//拿到结果  
		return jpaQuery.fetch();  
	}

}
