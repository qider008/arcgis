package com.kelan.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFilesImpl;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * @author 赵奎东 E-mail:285713138@qq.com
 * @version 创建时间：2017年7月31日 下午7:28:01 类说明
 */
public class Html2PdfUtils {
	/**
	 ** 静态css文件
	 */
	public static String cssFile;
	/**
	 * * 图片路径
	 */
	public static String imagePath;

	/**
	 * * html转PDF * * @param htmlFle * @param pdfFile
	 */
	public static void parse2Pdf(String htmlFle, String pdfFile) {
		try {
			parse2Pdf(new FileInputStream(htmlFle), new FileOutputStream(pdfFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void parse2Pdf(InputStream htmlFle, OutputStream pdfFile) {
		try {
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, pdfFile);
			// step 3
			document.open();
			// step 4
			MyXMLParser.getInstance(document, writer).parse(tidyHtml(htmlFle), Charset.forName("UTF-8"));
			// step 5
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * * html转PDF * * @param htmlContext html 字符串 * @param pdfFile
	 */
	public static void parseHtml2Pdf(String htmlContext, OutputStream pdfFile) {
		try {
			// step 1
			Document document = new Document();
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document, pdfFile);
			// step 3
			document.open();

			MyXMLParser.getInstance(document, writer).parse(tidyHtml(htmlContext), Charset.forName("UTF-8"));
			// step 5
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * * html转PDF * * @param htmlContext html 字符串 * @param pdfFile
	 */
	public static void parseHtml2Pdf(String htmlContext, File pdfFile) {
		try {
			parseHtml2Pdf(htmlContext, new FileOutputStream(pdfFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * * 闭合HTML标签 * * @param htmlStream * @return
	 */
	private static InputStream tidyHtml(InputStream htmlStream) {
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(htmlStream, "UTF-8", "");
			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
			doc.outputSettings().prettyPrint(true);
			doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
			Elements head=doc.select("head");
	        if(head.select("meta").isEmpty()) {
	        	head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
	        }	        
			return new ByteArrayInputStream(doc.html().replaceAll("<([a-z]+?)(?:\\s+?[^>]*?)?>\\s*?<\\/\\1>", "").getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * * 闭合HTML标签 * * @param htmlContext * @return
	 */
	private static InputStream tidyHtml(String htmlContext) {
		try {
			org.jsoup.nodes.Document doc = Jsoup.parse(htmlContext, "");
			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
			doc.outputSettings().prettyPrint(true);
			doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
			Elements head=doc.select("head");
	        if(head.select("meta").isEmpty()) {
	        	head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
	        }	        
			return new ByteArrayInputStream(doc.html().getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	protected static class MyXMLParser {
		public static XMLParser getInstance(Document doc, PdfWriter pdfWriter) throws Exception {

			// 固定css
			CssFilesImpl cssFiles = new CssFilesImpl();
			if (StringUtils.isNotBlank(cssFile)) {
				cssFiles.add(XMLWorkerHelper.getCSS(new FileInputStream(new File(cssFile))));
			} else {
				cssFiles.add(XMLWorkerHelper.getInstance().getDefaultCSS());
			}
			StyleAttrCSSResolver cssResolver = new StyleAttrCSSResolver(cssFiles);

			// 宋体支持

			HtmlPipelineContext hpc = new HtmlPipelineContext(new CssAppliersImpl(new SongFontsProvider()));

			// 图片加载
			if (StringUtils.isNotBlank(imagePath)) {
				hpc.setImageProvider(new ImageProvider(imagePath));
			}
			hpc.setAcceptUnknown(true).autoBookmark(true).setTagFactory(Tags.getHtmlTagProcessorFactory());
			HtmlPipeline htmlPipeline = new HtmlPipeline(hpc, new PdfWriterPipeline(doc, pdfWriter));
			Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, htmlPipeline);
			return new XMLParser(true, new XMLWorker(pipeline, true));
		}
	}

	/**
	 * * 找不到的字体一律改为宋体
	 */

	protected static class SongFontsProvider extends XMLWorkerFontProvider {
		public SongFontsProvider() {
			super(null, null);
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

	protected static class ImageProvider extends AbstractImageProvider {

		private String imageRootPath;
		public ImageProvider() {
			
		}

		public ImageProvider(String imageRootPath) {
			System.out.println(imageRootPath);
			this.imageRootPath = imageRootPath;
		}

		public String getImageRootPath() {
			return imageRootPath;
		}
	}
}
