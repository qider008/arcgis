package com.kelan.app.servlet;

import java.io.IOException;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月26日 下午6:08:00
* 类说明
*/
public class FreeMarkerConfig extends FreeMarkerConfigurer {
	public void afterPropertiesSet() throws IOException, TemplateException {  
        super.afterPropertiesSet();
        Configuration cfg = this.getConfiguration();
        cfg.setSharedVariable("shiro", new ShiroTags());//shiro标签
        cfg.setNumberFormat("#");//防止页面输出数字,变成2,000
	}
}
