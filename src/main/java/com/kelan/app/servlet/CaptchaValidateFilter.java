package com.kelan.app.servlet;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Component;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月21日 下午4:13:18
* 类说明
*/
@Component
public class CaptchaValidateFilter extends AccessControlFilter {

	private boolean captchaEbabled = true;//是否开启验证码支持
    private String captchaParam = "code";//前台提交的验证码参数名
    private String captchaErrorCode = "errorCode";
    private String failureKeyAttribute = "shiroLoginFailure"; //验证失败后存储到的属性名
    
    public void setFailureKeyAttribute(String failureKeyAttribute) {
        this.failureKeyAttribute = failureKeyAttribute;
    }
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        //1、设置验证码是否开启属性，页面可以根据该属性来决定是否显示验证码
        request.setAttribute("captchaEbabled", captchaEbabled);
    
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpSession session=httpServletRequest.getSession();
        //2、判断验证码是否禁用 或不是表单提交（允许访问）
        if (captchaEbabled == false || !"post".equalsIgnoreCase(httpServletRequest.getMethod())) {
            return true;
        }
        //3、此时是表单提交，验证验证码是否正确
        return  httpServletRequest.getParameter(captchaParam).equals(session.getAttribute(captchaParam).toString());
    }
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //如果验证码失败了，存储失败key属性
        request.setAttribute(failureKeyAttribute, captchaErrorCode);
        return true;
    }

	public void setCaptchaEbabled(boolean captchaEbabled) {
		this.captchaEbabled = captchaEbabled;
	}

	public void setCaptchaParam(String captchaParam) {
		this.captchaParam = captchaParam;
	}
	
	public void setCaptchaErrorCode(String captchaErrorCode) {
		this.captchaErrorCode = captchaErrorCode;
	}
	public String getCaptchaParam() {
		return captchaParam;
	}
	public String getCaptchaErrorCode() {
		return captchaErrorCode;
	}
}
