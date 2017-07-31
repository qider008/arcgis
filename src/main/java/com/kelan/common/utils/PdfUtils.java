package com.kelan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月17日 下午5:25:31
* 类说明
*/
public class PdfUtils {
    private static Logger logger = LoggerFactory.getLogger(PdfUtils.class);
    /**
     * PDF生成路径
     */
    public static final String PDF_DOWNLOAD_PATH = "/WEB-INF/pdf/";
    
    public static class MyFontsProvider extends XMLWorkerFontProvider {
    	HttpServletRequest request;
        public MyFontsProvider(){
        	
        }
        
        public MyFontsProvider(HttpServletRequest request) {
        	this.request=request;
        }

        @Override
        public Font getFont(final String fontname, String encoding, float size, final int style) {
            String fntname = fontname;
            if (fntname == null) {
                fntname = "宋体";
            }
            if (size == 0) {
                size = 4;
            }
            //String webRoot=request.getServletContext().getRealPath("WEB-INF");
            try {
				//BaseFont bf = BaseFont.createFont(webRoot+"\\font\\simsun.ttc,1", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
				BaseFont bf = BaseFont.createFont("E:\\Software\\workspaces\\kelan\\src\\main\\webapp\\WEB-INF\\font\\simsun.ttc,1", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
				return new Font(bf, size, style);
			} catch (DocumentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            return super.getFont(fntname, encoding, size, style);
        }
    }
    /**
     * 导出PDF文件
     * 
     * @param content
     * @param response
     */
    public static void exportPdf(String fileName, String filePath,HttpServletRequest request, HttpServletResponse response) {

        FileOutputStream fos = null;
        FileInputStream in = null;
        OutputStream out = null;
        Document document = new Document();
        File newPath = null;
        try {
            if (StringUtils.isBlank(fileName)) {
                fileName = LocalDateTime.now().toString().replaceAll(" ", "").replaceAll(":", "").replaceAll("-", "")+ "文件名.pdf";
            }
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
 
            String dicPath = request.getSession().getServletContext().getRealPath("/");
            String srcPath = dicPath + PDF_DOWNLOAD_PATH + fileName;

            newPath = new File(dicPath + PDF_DOWNLOAD_PATH);
            newPath.mkdirs();
            // 删除临时文件
            boolean success = fileDelete(newPath);

            if (success) {
                newPath.mkdirs();
                File file = new File(srcPath);
                fos = new FileOutputStream(file);
                PdfWriter writer = PdfWriter.getInstance(document, fos);
                document.open();   
                String filename=request.getSession().getServletContext().getRealPath(filePath);

                org.jsoup.nodes.Document docu=Jsoup.parse(new File(filename), "UTF-8");
    			OutputSettings setting=new OutputSettings();
    			setting.syntax(OutputSettings.Syntax.xml);
    			docu.outputSettings(setting);
                
                try(FileOutputStream o=new FileOutputStream(new File("D:/test.html"))){
                	o.write(docu.html().getBytes());
                }
                
                InputStream htmlInput = new FileInputStream(new File(filename));
                // 使用我们的字体提供器，并将其设置为unicode字体样式
                MyFontsProvider fontProvider = new MyFontsProvider(request);
                fontProvider.addFontSubstitute("lowagie", "garamond");
                fontProvider.setUseUnicode(true);
                CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
                HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
                htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

                XMLWorkerHelper.getInstance().parseXHtml(writer, document, htmlInput, null, Charset.forName("UTF-8"),fontProvider);
                
                document.close();
                writer.close();
                // 设置文件ContentType类型，这样设置，会自动判断下载文件类型
                response.setContentType("multipart/form-data");
                // 设置响应头，控制浏览器下载该文件
                response.setHeader("content-disposition", "attachment;filename=" + fileName);
                // 读取要下载的文件，保存到文件输入流
                in = new FileInputStream(srcPath);
                // 创建输出流
                out = response.getOutputStream();
                // 创建缓冲区
                byte buffer[] = new byte[1024];
                int len = 0;
                // 循环将输入流中的内容读取到缓冲区当中
                while ((len = in.read(buffer)) > 0) {
                    // 输出缓冲区的内容到浏览器，实现文件下载
                    out.write(buffer, 0, len);
                }
            }
        } catch (DocumentException e) {
            logger.error("Export PDF error :" + e.getMessage());
            throw new RuntimeException("Export PDF error : ", e);
        } catch (IOException e) {
            logger.error("Export PDF error :" + e.getMessage());
            throw new RuntimeException("Export PDF error : ", e);
        } catch (Exception e) {
            logger.error("Export PDF error :" + e.getMessage());
            throw new RuntimeException("Export PDF error : ", e);
        } finally {
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
            if (newPath != null) {
                //fileDelete(newPath);
            }
        }
    }
    public static void exportPdf(String inFile,String outFile){
    	
		File file=new File(inFile);
	
		org.jsoup.nodes.Document docu = null;
		try {
			docu = Jsoup.parse(new File(inFile), "UTF-8");
			OutputSettings setting=new OutputSettings();
			setting.syntax(OutputSettings.Syntax.xml);
			docu.outputSettings(setting);
			
	        Elements head=docu.select("head");
	        if(head.select("meta").isEmpty()) {
	        	head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
	        }	        
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        try(FileOutputStream o=new FileOutputStream(file)){
        	o.write(docu.html().replaceAll("<([a-z]+?)(?:\\s+?[^>]*?)?>\\s*?<\\/\\1>", "").getBytes());
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	Document document = new Document();
        // step 2
        PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(outFile));
			// step 3
	        document.open();
	        // step 4
	        MyFontsProvider fontProvider = new MyFontsProvider();
	        fontProvider.addFontSubstitute("lowagie", "garamond");
	        fontProvider.setUseUnicode(true);
	        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
	        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
	        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
	        XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(file), null, Charset.forName("UTF-8"),fontProvider);
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			document.close();
			if(null!=writer) {
				writer.close();
			}
			
		}
    }
    

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    private static boolean fileDelete(File file) {
        if (file.isDirectory()) {
            String[] children = file.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                boolean success = fileDelete(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return file.delete();
    }
}
