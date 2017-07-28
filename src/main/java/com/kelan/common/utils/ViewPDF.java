package com.kelan.common.utils;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;
/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月12日 下午4:28:23
* 类说明
*/
public class ViewPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response){
        String fileName = "用户信息.pdf"; // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        try {
        	  response.setCharacterEncoding("UTF-8");
              response.setContentType("application/octet-stream");
              response.setHeader("Content-Disposition","attachment; filename=" + new String(fileName.getBytes(), "iso8859-1"));
              
              List<?> list = (List<?>) model.get("list");
              
              // bf = BaseFont.createFont("Font/simsun.ttc,1", //注意这里有一个,1 BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
              String webRoot=request.getServletContext().getRealPath("WEB-INF");
              BaseFont bfChinese = BaseFont.createFont(webRoot+"\\font\\simsun.ttc,1", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

              Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);

              for (Object obj:list) {
            	  String value=obj.toString();
                  document.add(new Paragraph(value, FontChinese));
              }
        }catch(DocumentException | IOException e) {
        	e.printStackTrace();
        }finally {
        	 if(null!=document) {
             	document.close();
             }
        }
    }
}