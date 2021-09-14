package com.insigma.siis.local.pagemodel.customquery;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.fr.third.javax.xml.stream.xerces.util.SynchronizedSymbolTable;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.lbs.cp.sysmanager.entity.Sysudp;

/**
 * @author zoul
 *
 */
public class UploadQueryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=GBK");
		req.setCharacterEncoding("GBK");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setHeaderEncoding("GBK");
		String upload_file = AppConfig.HZB_PATH + "\\Customtemp\\impdata";
		PrintWriter out = null;
		String filename=null;
		String filePath = null;
		try {
			File file = new File(upload_file);
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			List<FileItem> fileItems = uploader.parseRequest(req);
			java.util.Iterator<FileItem> iter = fileItems.iterator();
			while (iter.hasNext()) {
				FileItem fi = iter.next();
				if (fi.isFormField()) {
					
				} else {
					// 将文件保存到指定目录
					String path = fi.getName();// 文件名称
					try {
						filename = path.substring(path.lastIndexOf("\\") + 1);
						//String md5Name = MD5.MD5(filename);
						String houzhui = filename.substring(filename.lastIndexOf(".") + 1,
								filename.length());
						//String md5Name = MD5.MD5(filename);
						filePath = upload_file +"\\"+ "自定义查询方案"+System.currentTimeMillis() + "." + houzhui;
						fi.write(new File(filePath));
						fi.getOutputStream().close();
						fi = null;
						} catch (Exception e) {
						e.printStackTrace();
						try {
							throw new AppException("上传失败");
						} catch (AppException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			//1
			SAXReader reader = new SAXReader();
			//2
			Document doc = reader.read(new File(filePath));
			//3
			Element root = doc.getRootElement();
			List<Element> elelist = root.elements();
			/*Element queryid = Customquery.addElement("QUERYID");
	        queryid.addText(customquery.getQueryid());
			Element querysql = Customquery.addElement("QUERYSQL");
			querysql.addText(customquery.getQuerysql());
			Element querydescription = Customquery.addElement("QUERYDESCRIPTION");
			querydescription.addText(customquery.getQuerydescription());
			Element loginname = Customquery.addElement("LOGINNAME");
			loginname.addText(customquery.getLoginname());
			Element createtime = Customquery.addElement("CREATETIME");
			createtime.addText(customquery.getCreatetime().toString());
			Element queryName = Customquery.addElement("QUERYNAME");
			queryName.addText(customquery.getQueryname());
			Element gridstring = Customquery.addElement("GRIDSTRING");
			gridstring.addText(customquery.getGridstring());
			Element sharename = Customquery.addElement("SHARENAME");
			if(customquery.getSharename()==null) {
				sharename.addText("-1");
			}else {
				sharename.addText(customquery.getSharename());
			}*/
			HBSession sess = HBUtil.getHBSession();
			Customquery customquery = new Customquery();
			for(Element e : elelist){
				Element queryidElement = e.element("QUERYID");
				String queryid = queryidElement.getText();
				Customquery customqueryentry = (Customquery)sess.get(Customquery.class, queryid);
				if(customqueryentry!=null) {
					 out = res.getWriter();
					 out.print("ss");
					return;
				}
				customquery.setQueryid(queryid);
				System.out.println(queryid);
				
				Element querysqlElement = e.element("QUERYSQL");
				String querysql = querysqlElement.getText();
				StringBuilder sb = new StringBuilder(querysql);
				sb.insert(6, " ");
				customquery.setQuerysql(sb.toString());
				System.out.println(sb.toString());
				
				Element querydescriptionElement = e.element("QUERYDESCRIPTION");
				String querydescription = querydescriptionElement.getText();
				customquery.setQuerydescription(querydescription);
				System.out.println(querydescription);
				
				Element loginnameElement = e.element("LOGINNAME");
				String loginname = loginnameElement.getText();
				loginname = SysUtil.getCacheCurrentUser().getLoginname();
				customquery.setLoginname(loginname);
				System.out.println(loginname);
				
				
				Element createtimeElement = e.element("CREATETIME");
				String createtime = createtimeElement.getText();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				customquery.setCreatetime(formatter.parse(createtime));;
				System.out.println(createtime);
				
				
				Element queryNameElement = e.element("QUERYNAME");
				String queryName = queryNameElement.getText();
				customquery.setQueryname(queryName);
				System.out.println(queryName);
				
				
				Element gridstringElement = e.element("GRIDSTRING");
				String gridstring = gridstringElement.getText();
				customquery.setGridstring(gridstring);
				System.out.println(gridstring);
				
				Element sharenameElement = e.element("SHARENAME");
				String sharename = sharenameElement.getText();
				if(sharename.equals("-1")) {
					sharename = null;
				}
				customquery.setSharename(sharename);
				System.out.println(sharename);
				
				sess.save(customquery);
				sess.flush();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		
		String demo = "sds";
		StringBuilder sb = new StringBuilder(demo);
		sb.insert(1, " ");
		System.out.println(sb.toString());
	}
	
}