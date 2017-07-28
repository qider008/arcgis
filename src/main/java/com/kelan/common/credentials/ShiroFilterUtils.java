package com.kelan.common.credentials;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月24日 下午9:43:46
* 类说明
*/
public class ShiroFilterUtils {
	
	public static boolean isAjax(HttpServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
	}
	
	public static void out(HttpServletResponse response, Map<String, Object> result){
		ObjectMapper mapper=new ObjectMapper();
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(mapper.writeValueAsString(result));
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
