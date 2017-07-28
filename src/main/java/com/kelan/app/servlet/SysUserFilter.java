package com.kelan.app.servlet;

import javax.inject.Inject;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.PathMatchingFilter;

import com.kelan.app.entity.User;
import com.kelan.app.service.UserService;
import com.kelan.common.constants.Constants;

public class SysUserFilter extends PathMatchingFilter {

    @Inject
    private UserService userService;
    
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)throws Exception {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUsername(username);
        request.setAttribute(Constants.CURRENT_USER, user);
        return true;
    }
}