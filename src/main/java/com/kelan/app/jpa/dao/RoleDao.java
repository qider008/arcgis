package com.kelan.app.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelan.app.entity.Role;

public interface RoleDao extends JpaRepository<Role, Long> {

}
