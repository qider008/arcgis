package com.kelan.app.jdbc.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午5:46:12
* 类说明
*/
@Repository
public class UserJdbcDaoImpl implements UserJdbcDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public List<Map<String, Object>> getAllUser() {
		List<Map<String,Object>> list=new ArrayList<>();
		String sql="select p.username,c.name from person_city pc rightjoin sys_person p on pc.pid=p.id rightjoin sys_city c on pc.cid=c.id";
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql); 
		
		for(Map<String,Object> res:rows) {
			Map<String,Object> result=new HashMap<>();
			result.put("username", res.get("username").toString());
			result.put("name", res.get("name").toString());
			list.add(result);
		}		
		return list;
	}
}
