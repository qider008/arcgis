package com.kelan.app.service.imp;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.kelan.app.entity.Operation;
import com.kelan.app.entity.Permission;
import com.kelan.app.entity.Resource;
import com.kelan.app.entity.Role;
import com.kelan.app.jpa.dao.OperationDao;
import com.kelan.app.jpa.dao.PermissionDao;
import com.kelan.app.jpa.dao.ResourceDao;
import com.kelan.app.jpa.dao.RoleDao;
import com.kelan.app.service.RoleService;

@Service
public class RoleServiceImp implements RoleService {
    @Inject
    private RoleDao roleDao;

    @Inject
    private ResourceDao resourceDao;

    @Inject
    private OperationDao operationDao;

    @Inject
    private PermissionDao permissionDao;

    @Override
    public boolean authorise(Long roleId,Long resourceId,List<Long> operationIds){
        Role r = roleDao.findOne(roleId);
        if (r == null) {
            return false;
        }
        Resource resource = resourceDao.findOne(resourceId);
        List<Operation> operations = operationDao.findAll(operationIds);

        Set<Permission> hasPerms = r.getPermissions();

        Permission permission = null;
        for (Permission p : hasPerms) {
            if (p.getResource().equals(resource)) {
                permission = p;
                break;
            }

        }

        if (permission == null) {
            permission = new Permission();
            permission.setResource(resource);
            permission.getOperations().addAll(operations);
            // permission = permissionDao.save(permission);
            r.getPermissions().add(permission);
            roleDao.save(r);
        } else {
            permission.getOperations().addAll(operations);
            permissionDao.save(permission);
        }
        return true;
    }

}
