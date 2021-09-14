package com.insigma.siis.local.pagemodel.weboffice;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.fr.stable.core.UUID;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.WebOffice;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.picCut.jspsmart.upload.File;
import com.picCut.jspsmart.upload.SmartUpload;
import com.picCut.jspsmart.upload.SmartUploadException;

public class SendtoServer extends HttpServlet {
	private ServletConfig config = null;	
	private String Path1(String path){
		java.io.File file=new java.io.File(path);
		return file.getAbsolutePath(); 
	}

	/**
	 * 保存数据到FTP文件  param FileName:文件名   RealPath：路径
	 */	
	protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		HBSession sess = HBUtil.getHBSession();
		SmartUpload mySmartUpload = new SmartUpload();
		String id = request.getParameter("id");
		//String uuid = request.getParameter("uuid");
		if(id.startsWith("null")||"".equals(id)||id==null){
			id = UUID.randomUUID().toString();
		}
		/*HttpSession session = request.getSession();
		session.setAttribute("filen", id);*/
		//else{
		//	id=id.toLowerCase().replaceAll("\\.doc", "").replaceAll("\\.docx", "").replaceAll("\\.xls", "").replaceAll("\\.xlsx", "");
		//}
	  String Path = AppConfig.HZB_PATH+"\\WebOffice"; 
	  java.io.File file = new java.io.File(Path);
	  if(!file.exists()){//没有文件自动创建文件路径
		  java.io.File file1 = new java.io.File(Path);
		  file1.mkdirs();
	  }
	  String  sql="select filename from weboffice where id='"+id+"'";
	  String filename = (String) sess.createSQLQuery(sql).uniqueResult();
	  //String FileName="";
	   
		// 初始化上传组件
		mySmartUpload.initialize(this.config, request, response);	
		PrintWriter out = response.getWriter();
		//Path = request.getSession().getServletContext().getRealPath(Path) ; 
		//Path=hzb+"\\"+Path;
		//Path=Path1(Path);
		//System.out.println("===========路径"+Path);
		try {
		// 获取传到表单记录		
		mySmartUpload.upload();
		String type=request.getParameter("type");
		String filen=request.getParameter("filename");
		String selty=request.getParameter("sel");
		String name=request.getParameter("nametype");
		String nametype=URLDecoder.decode(name, "utf-8");
		
		//String id=request.getParameter("id");
		File myFile = mySmartUpload.getFiles().getFile(0);
		String ext = myFile.getFileExt().toLowerCase();
		if(filename==null||filename==""){
			id = (id + "." + ext);
		}else{
			id=id.toLowerCase().replaceAll("\\.doc", "").replaceAll("\\.docx", "").replaceAll("\\.xls", "").replaceAll("\\.xlsx", "");
			id = (id + "." + ext);
		}
		if(!myFile.isMissing()){
			if(StringUtils.isEmpty(filename)){
				String FileName = mySmartUpload.getRequest().getParameter("FileName");
				myFile.saveAs(Path + "\\" + id, mySmartUpload.SAVE_PHYSICAL);
				if(("3".equals(type)&&"doc".equals(ext))||("1".equals(type)&&"xls".equals(ext))||("2".equals(type)&&"xls".equals(ext))||("4".equals(type)&&"xls".equals(ext))){
					SavaData(FileName,Path + "\\" + id,type,id,nametype,selty);
					out.write(id);//返回控件HttpPost()方法值
				}else{
					out.write(id);//返回控件HttpPost()方法值
				}
				
				
			  }else{
				 String FileName=filename;
				/* java.io.File file2 = new java.io.File(Path+"\\"+id);
				 if(file2.exists()){
					 file2.delete();
					}*/
				// if("1".equals(type)||"2".equals(type)||"4".equals(type)||"".equals(filen)||filen==null){
					 myFile.saveAs(Path + "\\" + id, mySmartUpload.SAVE_PHYSICAL);
				 /*}else{
					 myFile.saveAs(Path + "\\" + id+"1", mySmartUpload.SAVE_PHYSICAL);//word进程被占用重新临时命名，最后保存还用打开的名字 
				 }*/
				
					SavaData(FileName,Path + "\\" + id,type,id,nametype,selty);
					out.write(id);//返回控件HttpPost()方法值
			  }
			
		}
		} catch (SmartUploadException e) {
			System.out.println("++++++++++++"+e.toString());
			out.write("failed");//返回控件HttpPost()方法值
		}
		out.flush();
	}
	
	
	/**
	 * 保存数据到数据库  param FileName:文件名   RealPath：路径
	 * @param type 
	 */
	public void SavaData(String FileName,String RealPath,String type,String id,String nametype,String selty) {
		HBSession sess = HBUtil.getHBSession();
		WebOffice webOffice = new WebOffice();
		webOffice.setId(id);
		webOffice.setFilename(FileName);
		webOffice.setCreattime(new Date());
		webOffice.setUpdatetime(new Date());
		webOffice.setRealpath(RealPath);
		webOffice.setType(type);
		webOffice.setSelty(selty);
		webOffice.setNametype(nametype);
		sess.saveOrUpdate(webOffice);
		/*String uid = UUID.randomUUID().toString();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		String  sql1=" select officeid from COMPETENCE_USERWEBOFFICE where officeid='"+id+"'";
		String officeid = (String) sess.createSQLQuery(sql1).uniqueResult();
		if(officeid==""||officeid==null){
			String sql="INSERT INTO COMPETENCE_USERWEBOFFICE (id, userid,officeid,officetype,type) VALUES ('"+uid+"', '"+cueUserid+"','"+id+"','"+type+"','1')";
			sess.createSQLQuery(sql).executeUpdate();
		}*/
		
		sess.flush(); 
		
	}
	
	
	/**
	 *  接收weboffice保存文件
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
}
