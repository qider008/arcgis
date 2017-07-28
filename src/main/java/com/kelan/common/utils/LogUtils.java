package com.kelan.common.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.kelan.app.entity.Log;
import com.kelan.app.entity.User;
import com.kelan.app.service.LogService;
import com.kelan.app.servlet.SpringApplicationUtils;
import com.kelan.common.constants.Constants;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午4:56:26
* 类说明
*/
public class LogUtils {
	
	private static LogService logService=SpringApplicationUtils.getBean(LogService.class);
	
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {		
		User user=(User)request.getAttribute(Constants.CURRENT_USER);		
		if (user != null && user.getId() != null){
			Log log = new Log();
			if(StringUtils.isBlank(title)) {
				log.setTitle(user.getUsername());
			}else {
				log.setTitle(title);
			}
			log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
			log.setRemoteAddr(StringLocalUtils.getRemoteAddr(request));
			log.setUserAgent(request.getHeader("user-agent"));
			log.setRequestUri(request.getRequestURI());
			log.setParams(request.getParameterMap());
			log.setMethod(request.getMethod());
			log.setCreateBy(user.getId()+"");
			log.setCreateDate(new Date());
			// 异步保存日志
			new SaveLogThread(log, handler, ex).start();
		}		
	}
	
	public static class SaveLogThread extends Thread{		
		private Log log;
		@SuppressWarnings("unused")
		private Object handler;
		private Exception ex;
		
		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}
		
		@Override
		public void run() {
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringLocalUtils.isBlank(log.getTitle()) && StringLocalUtils.isBlank(log.getException())){
				return;
			}
			// 保存日志信息
			logService.saveLog(log);
		}
	}	
}
