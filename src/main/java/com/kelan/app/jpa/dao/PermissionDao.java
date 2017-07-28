package com.kelan.app.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kelan.app.entity.Permission;

public interface PermissionDao extends JpaRepository<Permission, Long> {

}
