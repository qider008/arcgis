package com.kelan.app.jpa.querydsl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.kelan.app.entity.User;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午5:44:24
* 类说明
*/
@Repository
public interface UserQuerydslDao extends JpaRepository<User, Long>, QueryDslPredicateExecutor<User> {
	
}
