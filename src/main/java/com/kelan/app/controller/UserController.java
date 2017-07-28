package com.kelan.app.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kelan.app.annotations.CurrentUser;
import com.kelan.app.entity.Permission;
import com.kelan.app.entity.Resource;
import com.kelan.app.entity.Role;
import com.kelan.app.entity.User;
import com.kelan.app.jpa.dao.RoleDao;
import com.kelan.app.jpa.dao.UserDao;
import com.kelan.app.service.UserService;
import com.kelan.app.servlet.CaptchaValidateFilter;

@Controller
@RequestMapping("/api/user")
public class UserController {
	
    @Inject
    private UserService userService;

    @Inject
    private RoleDao roleDao;

    @Inject
    private UserDao userDao;
    @Autowired
    CaptchaValidateFilter captchaValidateFilter;
    
    @RequiresPermissions("user:view")
    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view() {
        return "user/user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable Long id) {
        userDao.delete(id);
        return "OK";
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestParam String username, @RequestParam String password, @RequestParam String email,
            @RequestParam(value = "roles", required = false) List<Long> roleIds) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        List<Role> roles = roleDao.findAll(roleIds);

        user.setRoles(new HashSet<>(roles));
        userService.save(user);
        return "OK";
    }

    @RequiresPermissions({ "user:edit", "role:edit" })
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestParam(value = "userId", required = true) Long userId,
            @RequestParam(value = "roles", required = true) List<Long> roleIds) {
        User user = userDao.findOne(userId);
        List<Role> roles = roleDao.findAll(roleIds);

        Set<Role> preRoles = user.getRoles();

        for (Role r : roles) {
            if (preRoles.contains(r))
                continue;
            user.getRoles().add(r);
        }

        try {
            userDao.save(user);
        } catch (Exception e) {
            return "update fail!";
        }

        return "OK";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    // @ResponseBody
    public ResponseEntity<?> getAll() {

        List<User> list = userDao.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest req, RedirectAttributes ra) {
        String exceptionClassName = (String) req.getAttribute("shiroLoginFailure");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        }else if (captchaValidateFilter.getCaptchaErrorCode().equals(exceptionClassName)) {
        	error = "验证码不正确";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        if (error != null) {
            ra.addFlashAttribute("error", error);
            return "redirect:/api/user/unauthorized";
        }
        System.out.println("home");
        return "redirect:/api/user/index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage() {
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(User user) {
        try {
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
        return "OK";
    }

     @RequestMapping(value = "/logout")
     public String logout() {
    	 Subject subject = SecurityUtils.getSubject();
    	 System.out.println("logout");
         subject.logout();
         return "redirect:/api/user/login";
     }

     @RequestMapping(value = "/getUser")
     @ResponseBody
     public ResponseEntity<?> getUser() {
    	 Subject subject = SecurityUtils.getSubject();
         User user=userService.findByUsername(subject.getPrincipal().toString());
         return ResponseEntity.ok(user);
     }
     
     @RequestMapping(value = "/index")
     public ModelAndView index(@CurrentUser User user) {
         Set<Role> roles = user.getRoles();

         Set<Permission> permissions = new HashSet<>();
         for (Role r : roles) {
             permissions.addAll(r.getPermissions());
         }
         Set<Resource> resources = new HashSet<>();
         for (Permission p : permissions) {
             resources.add(p.getResource());
         }

         ModelAndView mv = new ModelAndView();
         mv.addObject("menus", resources);
         mv.setViewName("home");

         return mv;
     }

     @RequestMapping(value = "/unauthorized")
     public String unauthorized() {
         return "unauthorized";
     }
}
