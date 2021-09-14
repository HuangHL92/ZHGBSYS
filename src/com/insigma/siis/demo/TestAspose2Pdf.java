package com.insigma.siis.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
//import com.aspose.words.*; //����aspose-words-15.8.0-jdk16.jar��

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

public class TestAspose2Pdf {
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader()
					.getResourceAsStream("Aspose.Words.lic"); // license.xmlӦ����..\WebRoot\WEB-INF\classes·����
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

		if (!getLicense()) { // ��֤License ������֤��ת������pdf�ĵ�����ˮӡ����
			return;
		}
		try {
			long old = System.currentTimeMillis();
			// File file = new
			// File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/generic/web/file/pdf1.pdf");
			// //�½�һ���հ�pdf�ĵ�
			File file = new File(PdfAddress); // �½�һ���հ�pdf�ĵ�
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(DocAddress); // Address�ǽ�Ҫ��ת����word�ĵ�
			doc.save(os, SaveFormat.PDF);// ȫ��֧��DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// �໥ת��
			long now = System.currentTimeMillis();
			os.close();
			System.out.println("����ʱ��" + ((now - old) / 1000.0) + "��"); // ת����ʱ
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void doc2pdf(InputStream inputStream,String PdfAddress) {

		if (!getLicense()) { // ��֤License ������֤��ת������pdf�ĵ�����ˮӡ����
			return;
		}
		try {
			long old = System.currentTimeMillis();
			// File file = new
			// File("C:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/webapps/generic/web/file/pdf1.pdf");
			// //�½�һ���հ�pdf�ĵ�
			File file = new File(PdfAddress); // �½�һ���հ�pdf�ĵ�
			FileOutputStream os = new FileOutputStream(file);
			Document doc = new Document(inputStream); // Address�ǽ�Ҫ��ת����word�ĵ�
			doc.save(os, SaveFormat.PDF);// ȫ��֧��DOC, DOCX, OOXML, RTF HTML,
											// OpenDocument, PDF, EPUB, XPS, SWF
											// �໥ת��
			long now = System.currentTimeMillis();
			os.close();
			System.out.println("����ʱ��" + ((now - old) / 1000.0) + "��"); // ת����ʱ
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args){
		doc2pdf("D:\\321.docx","D:\\321.pdf");
	}

}
