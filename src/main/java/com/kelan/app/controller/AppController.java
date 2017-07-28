package com.kelan.app.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kelan.app.servlet.CaptchaValidateFilter;
import com.kelan.common.utils.ViewExcel;
import com.kelan.common.utils.ViewPDF;


/** 程序入口 */
@Controller
@RequestMapping("/app")
public class AppController {
	
	@Autowired
	CaptchaValidateFilter captchaValidateFilter;
	
	@RequestMapping(value="/checkcode")
	public void getCode(HttpServletRequest req, HttpServletResponse resp)throws IOException {
		int fSize=24;
		int codeCount = 4;//定义图片上显示验证码的个数
		int fWidth=fSize+1;
		int width = fWidth*codeCount+6;//定义图片的width
		int height = fSize * 2+1;//定义图片的height

		int codeY = height-16;
		char[] codeSequence = {'1','2','3','4','5','6','7','8','9',
	            'a','b','c','d','e','f','g','h','i','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z',
	            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
//		Graphics2D gd = buffImg.createGraphics();
		//Graphics2D gd = (Graphics2D) buffImg.getGraphics();
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Georgia", Font.BOLD, fSize);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.LIGHT_GRAY);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生20条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < codeCount*6; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);			
			gd.drawRect(x, y, 1, 1);
		}
		codeSequence=Arrays.copyOfRange(codeSequence, 0,9);
		int length=codeSequence.length;
		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuffer randomCode = new StringBuffer();
		int red = 0, green = 0, blue = 0;

		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(length)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, i*fSize+5, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		session.setAttribute(captchaValidateFilter.getCaptchaParam(), randomCode.toString());

		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}
	
	 @RequestMapping(value = "/excel")
	 public ModelAndView viewExcel() {
	        Map<String, Object> model = new HashMap<>();
	        return new ModelAndView(new ViewExcel(), model);
	 }
	 @RequestMapping(value="/pdf")  
	 public ModelAndView viewPDF(){  
		 Map<String, Object> model = new HashMap<>();       
		 return new ModelAndView(new ViewPDF(), model);  
	 }  
	 
	 @RequestMapping("/test")
	 public String test(HttpServletRequest request) {
		 return "test";
	 }
	 
	 @RequestMapping("/viewer")
	 public String view(HttpServletRequest request,Model model) {
		 String url=request.getParameter("url");
		 model.addAttribute("url", url);
		 return "viewer";
	 }
	 

    @RequestMapping("/download")
    public void downloadFile(@RequestParam("fileName") String fileName,HttpServletRequest request, HttpServletResponse response) {
        if (fileName != null) {
            String realPath = request.getServletContext().getRealPath("/");
            File parent = new File(realPath);
            String filePath=parent.getParent()+"/attachment";
            
            File file = new File(filePath, fileName);
            if (file.exists()) {
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/octet-stream");
                try {
					response.setHeader("Content-Disposition","attachment; filename=" + new String(fileName.getBytes(), "iso8859-1"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
