package com.picCut.servlet;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aspose.words.Document;
import com.aspose.words.HtmlSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.local.epsoft.config.AppConfig;

@SuppressWarnings("serial")
public class SorlQueryServlet extends HttpServlet{
	

	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException{
		try {
			//String path = new String(request.getParameter("path").getBytes("ISO-8859-1"),"GBK");
			String filepath = request.getParameter("path");
			if(filepath!=null&&!"".equals(filepath)){
				filepath=URLDecoder.decode(filepath,"utf8");
				filepath=filepath.replace("@@","/");
				if(filepath.startsWith("PublishUpload")){
					filepath=AppConfig.HZB_PATH+"/"+filepath;
				}
				getDocOutStream(response,filepath);					
				return;
			}
			//getDocOutStream(response,path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
private void getDocOutStream(HttpServletResponse res, String filePaths) throws Exception {
		String localName = filePaths.substring(filePaths.lastIndexOf(".")+1);
		String fileName= filePaths.substring(0,filePaths.lastIndexOf("."))+".html";
		File file = new File(fileName);
		OutputStream out = res.getOutputStream();
		//res.setContentType("application/pdf");
		if ("doc".equals(localName)||"docx".equals(localName)) {
			res.setContentType("text/html;charset=UTF-8");
			if (!getLicense()) { // 验证License 若不验证则转化出的文档会有水印产生
				return;
			}
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			HtmlSaveOptions options = new HtmlSaveOptions(SaveFormat.HTML);
			options.setImageSavingCallback(new HandleImageSaving());
			Document doc = new Document(filePaths);// 读取文档
			//doc.save(os, SaveFormat.PDF);// 转成pdf
			doc.save(os, options);
			out.write(os.toByteArray());
			if (os != null) {
				os.close();
			}
		} else if("pdf".equals(localName)){
			res.setContentType("application/pdf");
			InputStream in = new FileInputStream(filePaths);
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			if (in != null) {
				in.close();
			}
		}
		//关闭流
		if (out != null) {
			out.close();
		}
	}


/**
 * 去除水印
 * @return
 */
	public static boolean getLicense() {
		boolean result = false;
		try {
			InputStream is = TestAspose2Pdf.class.getClassLoader().getResourceAsStream("Aspose.Words.lic");// Aspose.Words.lic应放在..src 下面
			License aposeLic = new License();
			aposeLic.setLicense(is);
			result = true;
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void main(String[] args) {
		SorlQueryServlet us = new SorlQueryServlet();
	System.out.println(us.getPersonCode("陈洁32020319720827151X.jpg"));
}
private String getPersonCode(String phName) {
	Pattern p = Pattern.compile("\\d{18}|\\d{17}(X|x)|\\d{15}"); 
	  Matcher m = p.matcher(phName);
	  //phName = phName.replaceAll("^\\d{15}|\\d{18}|\\d{17}(X|x)", "");
	  if(m.find()){
		  phName=m.group();
	  }
	return phName;
}

		public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException {
			doGet(request, response);
		}
	

}
