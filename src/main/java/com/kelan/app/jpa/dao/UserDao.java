package com.kelan.app.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.kelan.app.entity.User;
@Repository
public interface UserDao extends JpaRepository<User, Long>,QueryDslPredicateExecutor<User>,PagingAndSortingRepository<User, Long>,JpaSpecificationExecutor<User> {

    User findByUsername(String username);
}
