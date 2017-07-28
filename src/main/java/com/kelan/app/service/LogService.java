package com.kelan.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.kelan.app.entity.Log;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午3:20:24
* 类说明
*/
public interface LogService {

	public Log saveLog(Log log);
	
	public Page<?> getPage(Pageable pageable,Specification<Log> spec);
}
