package com.kelan.app.service;

import java.util.List;

import com.querydsl.core.Tuple;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月20日 下午7:02:44
* 类说明
*/
public interface PersonService {
	List<Tuple> getPersonAndCity();
}
