package com.kelan.app.service.imp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import org.springframework.stereotype.Service;

import com.kelan.app.entity.Operation;
import com.kelan.app.entity.Permission;
import com.kelan.app.entity.QUser;
import com.kelan.app.entity.Resource;
import com.kelan.app.entity.Role;
import com.kelan.app.entity.User;
import com.kelan.app.jdbc.dao.JdbcDao;
import com.kelan.app.jpa.dao.UserDao;
import com.kelan.app.jpa.querydsl.dao.UserQuerydslDao;
import com.kelan.app.mybatis.dao.UserMapper;
import com.kelan.app.service.UserService;
import com.kelan.app.servlet.PasswordHelper;
import com.querydsl.core.types.Predicate;

@Service
public class UserServiceImp implements UserService {

    @Inject
    private UserDao userDao;
    @Inject
    UserQuerydslDao userQuerydslDao;
    @Inject
	JdbcDao jdbcDao;
    @Inject
    UserMapper userMapperDao;
    @Inject
    PasswordHelper passwordHelper;

    @Override
    public User save(User user) {
        passwordHelper.encryptPassword(user);
        return userDao.save(user);
    }

    @Override
    public Set<String> getStringRoles(String username) {

        User user = userDao.findByUsername(username);

        Set<Role> roles = user.getRoles();

        Set<String> stringRoles = new HashSet<>();
        for (Role r : roles) {
            stringRoles.add(r.getRole());
        }

        return stringRoles;
    }

    @Override
    public Set<String> getStringPermissions(String username) {

        User user = userDao.findByUsername(username);

        Set<String> stringPermissions = new HashSet<>();

        for (Role r : user.getRoles()) {
            for (Permission p : r.getPermissions()) {
                Resource res = p.getResource();

                for (Operation o : p.getOperations()) {
                    stringPermissions.add(res.getIdentity() + ":" + o.getOperation());
                }
            }
        }
        return stringPermissions;
    }

	@Override
	public List<User> findAllUsers() {
		/*QUser user=QUser.user;
		List<User> users=(List<User>) userQuerydslDao.findAll(user.username.eq("admin"));

		for(User u:users) {
			System.out.println(u.toString());
		}
		
		List<User> listUsers=jdbcDao.getAllUser();
		for(User u:listUsers) {
			System.out.println(u.toString());
		}
		
		List<User> lists=userMapperDao.getAllUser();
		for(User u:lists) {
			System.out.println(u.toString());
		}
		*/
		return userDao.findAll();
	}

	@Override
	public User findUserByName(String name) {
		QUser quser=QUser.user;
		Predicate p1=quser.username.eq(name);
		return userDao.findOne(p1);
	}

	@Override
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}
}
