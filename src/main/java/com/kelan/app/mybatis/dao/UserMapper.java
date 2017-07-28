package com.kelan.app.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.kelan.app.entity.User;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午3:28:34
* 类说明
*/
@Repository
public interface UserMapper {
	int insert(User user);
	List<User> getAllUser();
}
