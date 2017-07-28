package com.kelan.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月17日 下午2:01:44
* 类说明
*/
public class FreeMarkerUtil {
	/**
	 * 
	 * 生成HTML静态页面的公公方法
	 * @param fmc 
	 * @param templateName 模板的名称
	 * @param request
	 * @param map 生成模板需要的数据
	 * @param filePath 相对于web容器的路径
	 * @param fileName 要生成的文件的名称，带扩展名
	 * @author HuifengWang
	 * 
	 */
	public static void createHtml(FreeMarkerConfig fmc, String templateName,HttpServletRequest request, Map<?, ?> map, String filePath,String fileName) {
		Writer out = null;
		try {
			Template template = fmc.getConfiguration().getTemplate(templateName);
			String separator=File.separator;
			if(filePath.endsWith("/") || filePath.endsWith("\\")) {
				separator="";
			}
			
			String htmlPath = request.getSession().getServletContext().getRealPath(filePath)+separator+ fileName;
			File htmlFile = new File(htmlPath);
			if (!htmlFile.getParentFile().exists()) {
				htmlFile.getParentFile().mkdirs();
			}
			if (!htmlFile.exists()) {
				htmlFile.createNewFile();
			}
			out = new OutputStreamWriter(new FileOutputStream(htmlPath),"UTF-8");
			template.process(map, out);
			out.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				out = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @param request
	 * @param filePath  文件存放的路径
	 * @param fileName 文件的名称，需要扩展名
	 * @author HuifengWang
	 * @return
	 */
	public static Map<String,Object> htmlFileHasExist(HttpServletRequest request,String filePath,String fileName) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		String separator=File.separator;
		if(filePath.endsWith("/") || filePath.endsWith("\\")) {
			separator=File.separator;
		}
		
		String htmlPath = request.getSession().getServletContext().getRealPath(filePath)+separator + fileName;
		File htmlFile = new File(htmlPath);
		if(htmlFile.exists()){
			map.put("exist", true);
		}else{
			map.put("exist",false);
		}
		return map ;
	}
	
	public static String getRequestHTML(HttpServletRequest request) {
        // web应用名称,部署在ROOT目录时为空
        String contextPath = request.getContextPath();
        // web应用/目录/文件.do
        String requestURI = request.getRequestURI();
        // basePath里面已经有了web应用名称，所以直接把它replace掉，以免重复
        requestURI = requestURI.replaceFirst(contextPath, "");
        // 将.do改为.html,稍后将请求转发到此html文件
        requestURI = requestURI.substring(0, requestURI.indexOf(".")) + ".html"; 
        return requestURI;
    }
}
