package com.kelan.app.jpa.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelan.app.entity.Resource;

public interface ResourceDao extends JpaRepository<Resource, Long> {
    List<Resource> findByIdentity(String identity);
}
