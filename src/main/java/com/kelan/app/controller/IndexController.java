package com.kelan.app.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kelan.app.entity.User;
import com.kelan.app.service.UserService;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月17日 上午10:49:44
* 类说明
*/
@Controller
@RequestMapping("/index")
public class IndexController {
	/*@Autowired
	private FreeMarkerConfig freeMarkerConfig;//获取FreemarkerConfig的实例
*/	
	@Value("#{params['html']}")
	String filePath;
	
	@Inject
	UserService userService;
	
	@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model) {		
		List<User> list=userService.findAllUsers();
		model.addAttribute("index", "主页");
		model.addAttribute("list", list);
		return "index";
	}
	
	/*@RequestMapping("/index")
	public String index(HttpServletRequest request,Model model) {
		String path=request.getSession().getServletContext().getContextPath();
		return "redirect:/app/viewer.do?url="+path+"/index/viewPDF.do";
	}
	@RequestMapping("/test")
	public void test(HttpServletRequest request,HttpServletResponse response,Model model) {
		String requestHTML = FreeMarkerUtil.getRequestHTML(request);
		Boolean flag =(Boolean)FreeMarkerUtil.htmlFileHasExist(request,filePath, requestHTML).get("exist");
		if(!flag){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("index", "index");
			model.addAllAttributes(map);
	        FreeMarkerUtil.createHtml(freeMarkerConfig, "free.ftl", request,map, filePath, requestHTML);//根据模板生成静态页面
		}
		
		PdfUtil.exportPdf("abc.pdf", filePath+requestHTML, request, response);
		
		try {
			request.getRequestDispatcher(filePath+requestHTML).forward(request, response);  //跳转到html页面
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@RequestMapping("/viewPDF")
	public void viewPDF(HttpServletRequest request,HttpServletResponse response,Model model) {
		String requestHTML = FreeMarkerUtil.getRequestHTML(request);
		Boolean flag =(Boolean)FreeMarkerUtil.htmlFileHasExist(request,filePath, requestHTML).get("exist");
		if(!flag){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("index", "index");
			model.addAllAttributes(map);
	        FreeMarkerUtil.createHtml(freeMarkerConfig, "free.ftl", request,map, filePath, requestHTML);//根据模板生成静态页面
		}
		
		PdfUtil.exportPdf("abc.pdf", filePath+requestHTML, request, response);
	}*/
}
