package com.picCut.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Blob;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.hsqldb.lib.HashMap;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;
import com.picCut.jspsmart.upload.SmartUpload;

public class DownloadUserHeadImage extends HttpServlet {
	private String sPath = "/rmb/images/head_pic.png";
	private ServletConfig config = null;
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String path = "";
			String a0000 = request.getParameter("a0000");
			if("".equals(a0000)||a0000==null){
				path = request.getParameter("path");
				if(!"".equals(path)&&path!=null){
					path = URLDecoder.decode(path,"UTF8");
					File fileF = new File(path);
					if(fileF.isFile()){
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
					}else{
						SmartUpload mySmartUpload = new SmartUpload();
						mySmartUpload.initialize(this.config, request, response);
						File file = new File(mySmartUpload.getPhysicalPath(sPath, 1));
						FileInputStream in = new FileInputStream(file); 
						byte[] b = new byte[(int)file.length()];
						in.read(b);
						in.close();
						OutputStream out = response.getOutputStream();
						out.write(b);
						out.flush();
						out.close();
					}
				}	
			}
			a0000 = URLDecoder.decode(a0000,"UTF8");
			String xt = request.getParameter("xt");
			HBSession sess = HBUtil.getHBSession();
			if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
				List<Object[]> list = sess.createSQLQuery("select PHOTOPATH,Photoname from v_js_a57 where a0000='"+a0000+"' and v_xt='"+xt+"'").list();
				if(list.size() > 0){
					//Blob img = a57.getPhotodata();
					//InputStream inStream = img.getBinaryStream();
					String photourl =(list.get(0)[0] +"").toUpperCase();
					File fileF = new File(PhotosUtil.PHOTO_PATH+"qcjs/"+xt+"/"+photourl+list.get(0)[1] );
					System.out.println(PhotosUtil.PHOTO_PATH+"qcjs/"+xt+"/"+photourl+list.get(0)[1] );
					if(fileF.isFile()){
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
					}else{
						SmartUpload mySmartUpload = new SmartUpload();
						mySmartUpload.initialize(this.config, request, response);
						File file = new File(mySmartUpload.getPhysicalPath(sPath, 1));
						FileInputStream in = new FileInputStream(file); 
						byte[] b = new byte[(int)file.length()];
						in.read(b);
						in.close();
						OutputStream out = response.getOutputStream();
						out.write(b);
						out.flush();
						out.close();
					}
				}else{
					SmartUpload mySmartUpload = new SmartUpload();
					mySmartUpload.initialize(this.config, request, response);
					File file = new File(mySmartUpload.getPhysicalPath(sPath, 1));
					FileInputStream in = new FileInputStream(file); 
					byte[] b = new byte[(int)file.length()];
					in.read(b);
					in.close();
					OutputStream out = response.getOutputStream();
					out.write(b);
					out.flush();
					out.close();
				}
			} else {
//				A57 a57 = (A57)sess.get(A57.class, a0000);
				A57 a57=new A57();
				String centerPhoto = request.getParameter("centerPhoto");
				if(centerPhoto!=null) {
					List<java.util.HashMap<String, Object>> list = 	com.utils.CommonQueryBS.getListBySQL("select \"PHOTOPATH\",\"PHOTONAME\" from "+Zsrm.getZjkTableName("a57")+" where a0000='"+a0000+"'");
					//List<A57> list = sess.createSQLQuery("select \"PHOTONAME\" from "+Zsrm.getZjkTableName("a57")+" where a0000='"+a0000+"'").addEntity(A57.class).list();
					if(list.size()>0) {
					 a57.setPhotoname(list.get(0).get("photoname").toString());
					 a57.setPhotopath(list.get(0).get("photopath").toString().toUpperCase());
					}else {
						a57 = (A57)sess.get(A57.class, a0000); 
					}
				}else {
					a57 = (A57)sess.get(A57.class, a0000);
				}
				if(a57!=null){
					//Blob img = a57.getPhotodata();
					//InputStream inStream = img.getBinaryStream();
					String photourl = a57.getPhotopath().toUpperCase();
					File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
					if(fileF.isFile()){
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
					}else{
						SmartUpload mySmartUpload = new SmartUpload();
						mySmartUpload.initialize(this.config, request, response);
						File file = new File(mySmartUpload.getPhysicalPath(sPath, 1));
						FileInputStream in = new FileInputStream(file); 
						byte[] b = new byte[(int)file.length()];
						in.read(b);
						in.close();
						OutputStream out = response.getOutputStream();
						out.write(b);
						out.flush();
						out.close();
					}
				}else{
					SmartUpload mySmartUpload = new SmartUpload();
					mySmartUpload.initialize(this.config, request, response);
					File file = new File(mySmartUpload.getPhysicalPath(sPath, 1));
					FileInputStream in = new FileInputStream(file); 
					byte[] b = new byte[(int)file.length()];
					in.read(b);
					in.close();
					OutputStream out = response.getOutputStream();
					out.write(b);
					out.flush();
					out.close();
				}
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
	
}