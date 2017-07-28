package com.yunsoft.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
* @author 赵奎东 E-mail:285713138@qq.com
* @version 创建时间：2017年3月23日 上午11:44:58
* 类说明
*/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SpringTest {

	@Test
	public void test(){
		String filePath = "D:/test/";
		File root = new File(filePath);
		File[] files = root.listFiles();
		for(File file:files){
			if (!file.isDirectory()) {
				InputStream is=null;
				try {
					is = new FileInputStream(file);
					System.out.println(file.getName());
					if(file.getName().indexOf("xls")!=-1) {
						HSSFWorkbook hssfWorkbook = null;
						try {
							hssfWorkbook = new HSSFWorkbook(is);
						} catch (IOException e) {
							e.printStackTrace();
						}
				        
				        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			            if (null!= hssfSheet) {
			            	for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				                if (hssfRow != null) {
				                	System.err.println(hssfRow.getCell(1));
								}
				            }
			            }
					}else if(file.getName().indexOf("xlsx")!=-1){
						XSSFWorkbook hssfWorkbook = null;
						try {
							hssfWorkbook = new XSSFWorkbook(is);
						} catch (IOException e) {
							e.printStackTrace();
						}
				        
				        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
			            if (null!= hssfSheet) {
			            	for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				                XSSFRow hssfRow = hssfSheet.getRow(rowNum);
				                if (hssfRow != null) {
				                	System.err.println(hssfRow.getCell(1));
								}
				            }
			            }
					}
				} catch (FileNotFoundException e) {					
					e.printStackTrace();
				}finally{
					if(null!=is){
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	@SuppressWarnings({ "deprecation", "static-access", "unused" })
	private String getValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
}
