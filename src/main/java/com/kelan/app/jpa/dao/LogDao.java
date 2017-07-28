package com.kelan.app.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kelan.app.entity.Log;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午3:19:11
* 类说明
*/
@Repository
public interface LogDao extends JpaRepository<Log, String>, JpaSpecificationExecutor<Log>, PagingAndSortingRepository<Log, String>,
		QueryDslPredicateExecutor<Log> {

}
