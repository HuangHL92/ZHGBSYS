package com.picCut.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.picCut.jspsmart.upload.File;
import com.picCut.jspsmart.upload.SmartUpload;

import sun.misc.BASE64Decoder;

public class UpLoadUserHeadImage extends HttpServlet {
	private ServletConfig config = null;
	private String FileName = null;
	private String sPath = "picCut/UploadPhoto";

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String photodata = request.getParameter("photodata");
		
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			if(photodata!=null&&!"".equals(photodata)){
				photodata = photodata.replace("data:image/jpeg;base64,", "");//data:image/jpeg;base64,
				//PhotosUtil.getPhotoStream(photourl)
				String userid = SysManagerUtils.getUserId();
				this.FileName = new String(userid.getBytes("iso-8859-1"));
				this.FileName = this.FileName + ".jpg";
				BASE64Decoder decoder = new BASE64Decoder();
				String url = request.getSession().getServletContext().getRealPath(this.sPath + "/" + this.FileName);
				
				
				java.io.File outFile = new java.io.File(url);
				OutputStream ro = new FileOutputStream(outFile);
				byte[] b = decoder.decodeBuffer(photodata);
				/*for (int i = 0; i < b.length; ++i) {
					if (b[i] < 0) {
						b[i] += 256;
					}
				}*/
				ro.write(b);
				ro.flush();
				ro.close();
				
				response.sendRedirect(request.getContextPath()+"/picCut/uploadimage.jsp?ImgIdPostfix="+request.getParameter("ImgIdPostfix")+"&Picurl="
						+ this.FileName + "&step=2&r="+Math.random());
				return;
			}
			
			
			SmartUpload mySmartUpload = new SmartUpload();
			mySmartUpload.initialize(this.config, request, response);
			//mySmartUpload.setMaxFileSize(102400L);//100KB
			mySmartUpload.setMaxFileSize(2097152L);//文件限制 2M
			mySmartUpload.setAllowedFilesList("jpg");
			mySmartUpload.upload();
			File myFile = mySmartUpload.getFiles().getFile(0);
			if (!myFile.isMissing()) {
				//Date currTime = new Date();
				//SimpleDateFormat formatter2 = new SimpleDateFormat(
				//		"yyyyMMddhhmmssS", Locale.US);
				String userid = SysManagerUtils.getUserId();
				this.FileName = new String(userid.getBytes("iso-8859-1"));
				String ext = myFile.getFileExt().toLowerCase();
				this.FileName = (this.FileName + "." + ext);
				myFile.saveAs(this.sPath + "/" + this.FileName, 1);
			}
			/*response.sendRedirect(request.getContextPath()+"/picCut/uploadimage.jsp?ImgIdPostfix="+request.getParameter("ImgIdPostfix")+"&Picurl="
					+ this.FileName + "&step=2&r="+Math.random());*/
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write("rparent.showdialog2('"+this.FileName+"');");
			pw.write("window.close();");
			pw.flush();
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			//pw.write("<script type=\"text/javascript\">");
			//pw.write("parent.$h.alert('系统提示','"+(e.getMessage()==null?"请登录!":e.getMessage())+"',null,250);");
			
			
			//pw.write("alert("+e.getMessage()+")");
			
			pw.write("alert('"+e.getMessage()+"');");
			/*pw.write("window.location.href='"+request.getContextPath()+"/picCut/uploadimage.jsp?Picurl="
					+ this.FileName + "&step=2&r="+Math.random()+"'");*/
			/*pw.write("window.location.href='"+request.getContextPath()+"/picCut/uploadimage.jsp?Picurl="
					+ null + "&step=2&r="+Math.random()+"'");*/
			
			pw.write("window.close()");
			//pw.write("</script>");
		}
	}


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HBSession session = HBUtil.getHBSession();
		String a0000 = req.getParameter("a0000");
		String deletePhoto = req.getParameter("deletePhoto");
		if(a0000!=null && !"".equals(a0000) && "true".equals(deletePhoto)){
			A57 a57 = (A57) session.get(A57.class, a0000);
			if(a57!=null && !"".equals(a57)){
				session.delete(a57);
				session.flush();
			}
		}
	}



	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
}