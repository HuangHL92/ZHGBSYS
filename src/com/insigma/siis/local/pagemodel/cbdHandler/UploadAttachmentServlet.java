package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class UploadAttachmentServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	} 
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String filename = request.getParameter("excelFile");
		CommonQueryBS.systemOut("--UploadAttachmentServlet--"+filename);
	} 
}
