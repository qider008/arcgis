package com.kelan.app.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kelan.app.entity.Operation;
@Repository
public interface OperationDao extends JpaRepository<Operation, Long> {

}
