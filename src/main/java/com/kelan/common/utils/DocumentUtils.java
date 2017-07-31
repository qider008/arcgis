package com.kelan.common.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年7月28日 下午1:26:53
* 类说明
*/
public class DocumentUtils {
	
	public static void writeFile(String content, String path) {  
        FileOutputStream fos = null;   
        BufferedWriter bw = null;  
        org.jsoup.nodes.Document doc = Jsoup.parse(content);  
		OutputSettings setting=new OutputSettings();
		setting.syntax(OutputSettings.Syntax.xml);
		doc.outputSettings(setting);
        content=doc.html();  
        try {  
            File file = new File(path);  
            fos = new FileOutputStream(file);  
            bw = new BufferedWriter(new OutputStreamWriter(fos,"UTF-8"));  
            bw.write(content);  
        } catch (FileNotFoundException fnfe) {  
            fnfe.printStackTrace();  
        } catch (IOException ioe) {  
            ioe.printStackTrace();  
        } finally {  
            try {  
                if (bw != null) {
                	bw.close();
                }                      
                if (fos != null) {
                	fos.close();  
                }                   
            } catch (IOException ie) {  
            	ie.printStackTrace();
            }  
        }  
    }  
	
	
	//word 转 html 
    public static void convert2Html(String fileName, String outPutFile){    	
    	FileInputStream fis=null;
    	HWPFDocument wordHWPFDocument = null;
    	XWPFDocument wordXSPFDocument = null;
    	OutputStream out =null;
    	File file=new File(fileName);
    	File outFile=new File(outPutFile);
    	String folderPath=outFile.getParent();
		try {
			fis = new FileInputStream(file);						
			if(fileName.indexOf(".docx")!=-1 && FileTypeUtils.getFileType(file).equals("docx")) {
				wordXSPFDocument = new XWPFDocument(fis);	
				// 2) 解析 XHTML配置 (这里设置IURIResolver来设置图片存放的目录) 
				File imgFolder = new File(folderPath+File.separator+"images");
				XHTMLOptions options = XHTMLOptions.create();
				options.setExtractor(new FileImageExtractor(imgFolder));
				//html中图片的路径 相对路径 
				options.URIResolver(new BasicURIResolver("images"));
				options.setIgnoreStylesIfUnused(false); 
				options.setFragment(true); 
				 
				// 3) 将 XWPFDocument转换成XHTML
				//生成html文件上级文件夹
				File folder = new File(folderPath);
				if(!folder.exists()){ 
				  folder.mkdirs(); 
				}
				out = new FileOutputStream(outPutFile); 
				XHTMLConverter.getInstance().convert(wordXSPFDocument, out, options);		
			}else {
				wordHWPFDocument=new HWPFDocument(fis);
				WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		        wordToHtmlConverter.setPicturesManager( new PicturesManager(){
		            public String savePicture( byte[] content,PictureType pictureType, String suggestedName,float widthInches, float heightInches ){
		                return "images"+File.separator+suggestedName;
		            }
		        });
		     
		        wordToHtmlConverter.processDocument(wordHWPFDocument);
		        //save pictures
		        List<Picture> pics=wordHWPFDocument.getPicturesTable().getAllPictures();
		        if(pics!=null){
		            for(int i=0;i<pics.size();i++){
		                Picture pic = (Picture)pics.get(i);
		                try {
		                    pic.writeImageContent(new FileOutputStream(folderPath+File.separator+"images"+File.separator+ pic.suggestFullFileName()));
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
		        org.w3c.dom.Document htmlDocument = wordToHtmlConverter.getDocument();
		        out = new ByteArrayOutputStream();
		        DOMSource domSource = new DOMSource(htmlDocument);
		        StreamResult streamResult = new StreamResult(out);

		        TransformerFactory tf = TransformerFactory.newInstance();
		        Transformer serializer = tf.newTransformer();
		        serializer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		        serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		        serializer.setOutputProperty(OutputKeys.METHOD, "HTML");
		        serializer.transform(domSource, streamResult);
		        writeFile(new String(((ByteArrayOutputStream) out).toByteArray()), outPutFile);
			}	        
		} catch (TransformerException | IOException | ParserConfigurationException | FactoryConfigurationError e1) {
			e1.printStackTrace();
		} finally {
			org.jsoup.nodes.Document docu = null;
			try {
				docu = Jsoup.parse(new File(outPutFile), "UTF-8");
				OutputSettings setting=new OutputSettings();
				setting.syntax(OutputSettings.Syntax.xml);
				docu.outputSettings(setting);
				
		        Elements head=docu.select("head");
		        if(head.select("meta").isEmpty()) {
		        	head.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>");
		        }	
		        Elements eles = docu.getAllElements();
		        for(Element ele : eles){
		        	 ele.removeAttr("style");
		        	 ele.removeAttr("class");
		        }
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	        try(FileOutputStream o=new FileOutputStream(outPutFile)){
	        	String html=docu.html().replaceAll("<([a-z]+?)(?:\\s+?[^>]*?)?>\\s*?<\\/\\1>", "");
	        	o.write(html.getBytes());
	        } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(wordHWPFDocument!=null) {
				try {
					wordHWPFDocument.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(wordXSPFDocument!=null) {
				try {
					wordXSPFDocument.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}  
    }
}
