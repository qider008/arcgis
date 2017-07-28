package com.kelan.app.jdbc.dao;

import java.util.List;
import java.util.Map;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午5:45:08
* 类说明
*/
public interface UserJdbcDao {

	List<Map<String,Object>> getAllUser();
}
