package com.insigma.siis.local.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.License;
import com.aspose.cells.PdfSaveOptions;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.SaveFormat;

public class AsposeExcelToPdf
{
	private static InputStream license;

	/**
	 * 
	 * @return
	 */
	public static boolean getLicense()
	{
		boolean result = false;
		try
		{
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			license = new FileInputStream(loader.getResource("license.xml").getPath());// 鍑瘉鏂囦欢
			License aposeLic = new License();
			aposeLic.setLicense(license);
			result = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}

	public static void pdfToPdf(String filePaths) throws Exception
	{
		String fileName= filePaths.substring(0,filePaths.lastIndexOf("."))+".pdf";
		File file = new File(fileName);
		if (!file.exists()) {
			if (!getLicense()) { // 验证License 若不验证则转化出的文档会有水印产生
				return;
			}	
			//OutputStream out = res.getOutputStream();
			FileOutputStream out1=new FileOutputStream(file);
			//res.setContentType("application/pdf");
			/*res.setHeader("Content-Disposition", "filename="
                    .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			//Document doc = new Document(filePaths);// 读取文档
			Workbook doc = new Workbook(filePaths);
			//doc.setFileFormat(FileFormatType.XLSX);
			
			
			
			
			PdfSaveOptions option = new PdfSaveOptions();
			option.setOnePagePerSheet(false);
			doc.save(os, option);// 转成pdf
			//out.write(os.toByteArray());
			out1.write(os.toByteArray());
			
			//关闭流
			if (out1 != null) {
				out1.close();
			}
			if (os != null) {
				os.close();
			}
		} 
		
	}
}
