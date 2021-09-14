package com.insigma.siis.local.pagemodel.weboffice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

public class ViewOffice extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String webof= GlobalNames.sysConfig.get("PHOTO_PATH");
		 webof=webof.replace("HZBPHOTOS", "WebOffice");
		 String name = request.getParameter("name");
		 File fileF = new File(webof+name);
		 if(fileF.exists()){
			 long nLen = fileF.length();
				int nSize = (int) nLen;
				byte[] data = new byte[nSize];
				FileInputStream inStream = new FileInputStream(fileF); 
				inStream.read(data);
				inStream.close();
				OutputStream out = response.getOutputStream();
				out.write(data);
				out.flush();
				out.close();
			}
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		
	}

}
