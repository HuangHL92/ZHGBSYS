package com.insigma.siis.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
//import com.aspose.words.*; //引入aspose-words-15.8.0-jdk16.jar包

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class TestAspose2Pdf {
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void doc2pdf(String DocAddress,String PdfAddress) {

		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		try {
			long old = System.currentTimeMillis();
			// File file = new
			// File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/generic/web/file/pdf1.pdf");
			// //新建一个空白pdf文档
			File file = new File(PdfAddress); // 新建一个空白pdf文档
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(DocAddress); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// 相互转换
			long now = System.currentTimeMillis();
			os.close();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void doc2pdf(InputStream inputStream,String PdfAddress) {

		if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
			return;
		}
		try {
			long old = System.currentTimeMillis();
			// File file = new
			// File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/generic/web/file/pdf1.pdf");
			// //新建一个空白pdf文档
			File file = new File(PdfAddress); // 新建一个空白pdf文档
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(inputStream); // Address是将要被转化的word文档
			doc.save(os, SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// 相互转换
			long now = System.currentTimeMillis();
			os.close();
			System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒"); // 转化用时
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		doc2pdf("D:\\321.docx","D:\\321.pdf");
	}

}
