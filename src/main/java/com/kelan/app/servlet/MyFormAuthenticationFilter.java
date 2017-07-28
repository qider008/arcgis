package com.kelan.app.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月21日 下午4:11:55
* 类说明
*/
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {  
        if(request.getAttribute(getFailureKeyAttribute()) != null) {  
            return true;  
        }  
        return super.onAccessDenied(request, response, mappedValue);  
    }  
}
