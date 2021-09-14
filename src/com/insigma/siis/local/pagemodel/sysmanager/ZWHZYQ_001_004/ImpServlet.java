package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.util.MD5;
import com.insigma.siis.local.business.entity.LogDetail;
import com.insigma.siis.local.business.entity.LogMain;


public class ImpServlet extends HttpServlet {
	//private String date = "";
	public void destroy() {
		super.destroy();
	}
	
	public void doGet(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		response.setContentType("text/html;charset=GBK");
		request.setCharacterEncoding("GBK");
		PrintWriter out = response.getWriter();
		String classPath = getClass().getClassLoader().getResource("/")
				.getPath();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath = "";
		String filename = "";
		String houzhui = "";
		if ("\\".equals(File.separator)) {
			rootPath = classPath.substring(1, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("/", "\\");
		}
		// linux下
		if ("/".equals(File.separator)) {
			rootPath = classPath.substring(0, classPath
					.indexOf("WEB-INF/classes"));
			rootPath = rootPath.replace("\\", "/");
		}
		String upload_file = rootPath + "pages\\sysmanager\\ZWHZYQ_001_004\\implog";
		try {
			File file = new File(upload_file);
			// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		Map map = new HashMap();
		boolean flag = false;
		List<FileItem> list = null;
		try {
			list = uploader.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		String filePath = "";
		for (FileItem item : list) {
			if (!item.isFormField()) {
				// 将文件保存到指定目录
				String path = item.getName();// 文件名称
				try {
					filename = path.substring(path.lastIndexOf("\\") + 1);
					//System.out.println(upload_file);
					//houzhui = filename.substring(filename.lastIndexOf(".") + 1,filename.length());
					//String md5Name = MD5.MD5(filename);
					filePath = upload_file+"\\"+filename;
					item.write(new File(filePath));
					item.getOutputStream().close();
					fileImp(filePath);
					out.print("导入成功，请手动点击查询按钮刷新日志。");
				} catch (Exception e) {
					response.sendRedirect("pages/sysmanager/ZWHZYQ_001_004/FileImpWindow.jsp");
					return;
				}
			}
		}
		
		
	}
	public void fileImp(String filePath) {
		File f = new File(filePath);
		if(f.exists()){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<LogMain> logmain = new ArrayList<LogMain>();
				List<LogDetail> detail = new ArrayList<LogDetail>();
				HashMap map= new HashMap(); 
				List list = new ArrayList();
				SAXReader reader = new SAXReader(); 
				Document doc = reader.read(f);
				Element root = doc.getRootElement();
				for (Iterator i = root.elementIterator("mainInform"); i.hasNext();) {
					LogMain lm = new LogMain();
					Element mainInform = (Element) i.next();
					lm.setSystemoperatedate(sdf.parse(mainInform.attributeValue("SYSTEM_OPERATE_DATE")));
					lm.setUserlog(mainInform.attributeValue("USERLOG"));
					lm.setEventobject(mainInform.attributeValue("EVENTOBJECT"));
					lm.setEventtype(mainInform.attributeValue("EVENTTYPE"));
					lm.setObjectid(mainInform.attributeValue("OBJECTID"));
					lm.setObjectname(mainInform.attributeValue("OBJECTNAME"));
					lm.setOperatecomments(mainInform.attributeValue("OPERATE_COMMENTS"));
					logmain.add(lm);
					int a = logmain.size()-1;
					list.add(a);
					for (Iterator j = mainInform.elementIterator("item"); j.hasNext();){
						LogDetail ld = new LogDetail();
						Element item = (Element) j.next();
						ld.setDataname(item.attributeValue("DATANAME"));
						ld.setOldvalue(item.attributeValue("OLDVALUE"));
						ld.setNewvalue(item.attributeValue("NEWVALUE"));
						ld.setChangedatetime(sdf.parse(item.attributeValue("CHANGEDATETIME")));
						ld.setSystemlogid(""+a);
						detail.add(ld);
					}
					
					
				}
				HBSession sess = HBUtil.getHBSession();
				Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
				for(int i=0;i<logmain.size();i++){
					sess.save(logmain.get(i));
					//ts.commit();
					for(int j=0;j<list.size();j++){
						int a = (Integer) list.get(j);
						if(i==a){
							map.put(""+a, logmain.get(i).getSystemlogid());
						}
					}
				}
				ts.commit();
				ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
				for(int i=0;i<detail.size();i++){
						detail.get(i).setSystemlogid((String)map.get(detail.get(i).getSystemlogid()));
					sess.save(detail.get(i));
					//ts.commit();
				}
				ts.commit();
				f.delete();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
}
