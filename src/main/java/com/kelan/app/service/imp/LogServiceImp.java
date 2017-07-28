package com.kelan.app.service.imp;

import javax.inject.Inject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.kelan.app.entity.Log;
import com.kelan.app.jpa.dao.LogDao;
import com.kelan.app.service.LogService;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午3:20:56
* 类说明
*/
@Service("logService")
public class LogServiceImp implements LogService {
	@Inject
	private LogDao logDao;
	
	@Override
	public Log saveLog(Log log) {
		return logDao.save(log);
	}

	@Override
	public Page<Log> getPage(Pageable pageable, Specification<Log> spec) {
		return logDao.findAll(spec, pageable);
	}
}
