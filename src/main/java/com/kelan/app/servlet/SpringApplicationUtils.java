package com.kelan.app.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月27日 下午5:03:00
* 类说明
*/
@Component
@Lazy(false)
public class SpringApplicationUtils implements ApplicationContextAware,DisposableBean{
	
	private static Logger logger = LoggerFactory.getLogger(SpringApplicationUtils.class);
	private static ApplicationContext applicationContext=null;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringApplicationUtils.applicationContext = applicationContext; 
	}
	public static ApplicationContext getApplicationContext() {  
	    return applicationContext;  
	}  
	
	public static Object getBean(String name) throws BeansException {  
        return applicationContext.getBean(name);  
    }  
	
	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}
	public static void clearHolder() {
		if (logger.isDebugEnabled()){
			logger.debug("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		}
		applicationContext = null;
	}
	
	@Override
	public void destroy() throws Exception {
		SpringApplicationUtils.clearHolder();
	}

}
