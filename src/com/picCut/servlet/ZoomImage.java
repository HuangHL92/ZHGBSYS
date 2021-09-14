package com.picCut.servlet;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.picCut.jspsmart.upload.SmartUpload;
import com.picCut.teetaa.util.ImageHepler;

public class ZoomImage extends HttpServlet {
	private ServletConfig config = null;
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int imageWidth = Integer.parseInt(request.getParameter("txt_width"));
		int imageHeight = Integer.parseInt(request.getParameter("txt_height"));
		int cutTop = Integer.parseInt(request.getParameter("txt_top"));
		int cutLeft = Integer.parseInt(request.getParameter("txt_left"));
		int dropWidth = Integer.parseInt(request.getParameter("txt_DropWidth"));
		int dropHeight = Integer.parseInt(request
				.getParameter("txt_DropHeight"));
		
		String a0000 = request.getParameter("a0000");
		
		//图片截图超出范围 调整
		/*if(cutTop>(imageHeight-dropHeight)){
			cutTop = imageHeight-dropHeight;
		}
		if(cutLeft>(imageWidth-dropWidth)){
			cutLeft = imageWidth-dropWidth;
		}
		
		if(cutTop<0){
			cutTop = 0;
		}
		if(cutLeft<0){
			cutLeft = 0;
		}*/
		
		
		float imageZoom = Float.parseFloat(request.getParameter("txt_Zoom"));
		SmartUpload mySmartUpload = new SmartUpload();
		mySmartUpload.initialize(this.config, request, response);
		String picture = request.getParameter("picture");
		Rectangle rec = new Rectangle(cutLeft, cutTop, dropWidth, dropHeight);
		File file = new File(mySmartUpload.getPhysicalPath("/picCut/User/UserHeadImage/"+ picture, 1) 
				);
		try {
			saveSubImage(new File(mySmartUpload.getPhysicalPath("/picCut/UploadPhoto/"+ picture, 1)) ,
					file, imageWidth, imageHeight, rec,a0000);
			
			
			
			/*response.sendRedirect(request.getContextPath()+"/picCut/uploadimage.jsp?Picurl="
				+ picture + "&step=3&a0000="+URLEncoder.encode(URLEncoder.encode(a0000,"UTF-8"),"UTF-8"));*/
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("Step3();");
			pw.write("window.close();");
			pw.flush();
			return;
		} catch (Exception e) {
			
			
			/*e.printStackTrace();
			response.setCharacterEncoding("GBK");
			PrintWriter pw = response.getWriter();
			pw.write("<script type=\"text/javascript\">");
			pw.write("parent.$h.alert('系统提示','"+e.getMessage()+"',null,250);");
			pw.write("window.location.href='"+request.getContextPath()+"/picCut/uploadimage.jsp?step=2&r="+Math.random()+"'");
			pw.write("</script>");*/
			
			e.printStackTrace();
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			//pw.write("<script type=\"text/javascript\">");
			pw.write("alert('照片格式有问题，请使用图片编辑器转为BMP，再转为jpg上传');");
			//pw.write("window.location.href='"+request.getContextPath()+"/picCut/uploadimage.jsp?step=2&r="+Math.random()+"'");
			
			pw.write("window.close()");
			
			//pw.write("</script>");
			
		}
	}

	private static void saveSubImage(File srcImageFile, File descDir,
			int width, int height, Rectangle rect ,String a0000) throws IOException {
		ImageHepler.cut(srcImageFile, descDir, width, height, rect,a0000);
	}
	
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
}