package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.LogDetail;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class FileDownServlet  extends HttpServlet {
	private LogManagePageModel lm = new LogManagePageModel();
	private String date = "";
	private String path = "";
	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest paramHttpServletRequest,
			HttpServletResponse paramHttpServletResponse)
			throws ServletException, IOException {
		doPost(paramHttpServletRequest, paramHttpServletResponse);
	}

	public void doPost(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletException, IOException {
		String path = this.getServletContext().getRealPath("/")+"pages\\sysmanager\\ZWHZYQ_001_004\\log\\"+URLDecoder.decode(URLDecoder.decode(request.getParameter("path"),"utf8"),"utf8");
		File file = new File(path);
        String filename = file.getName();
        //String foldername = this.getServletContext().getRealPath("/")+"pages\\sysmanager\\ZWHZYQ_001_004\\log";
        /*如果文件存在*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			//response.setContentType("application/octet-stream;charset=GBK");
			response.setContentType("application/x-msdownload;charset=GBK");  
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*如果文件长度大于0*/
            if (fileLength != 0) {
                /*创建输入流*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*创建输出流*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
               //UploadHelpFileServlet.delFolder(foldername);
                inStream.close();
                servletOS.flush();
                servletOS.close();
                
            }
        }else{
        	ServletOutputStream servletOS = response.getOutputStream();
        	servletOS.print(new String(filename.getBytes("GBK"), "ISO8859-1"));
        	servletOS.flush();
            servletOS.close();
        }
		
		
		
		
	}
	
	
	public void rebackup(String username, String type, String object, String info, String start, String end, String path){
		this.path = path;
		File file = new File(this.path);
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File f : files){
				if(f.isFile()){
					System.gc();
					boolean b = f.delete();
				}
			}
		}
		
		FileExpWindowPageModel.isfinish=1;
		HBSession sess = HBUtil.getHBSession();
		StringBuffer str = new StringBuffer();
		str.append("From LogMain t where 1=1") ;
		if(username!=null&&!username.equals("")){
			str.append(" and t.userlog like '%"+username+"%'");
		}
		if(type!=null&&!type.equals("")){
			str.append(" and t.eventtype like '%"+type+"%'");
		}
		if(object!=null&&!object.equals("")){
			str.append(" and t.objectname like '%"+object+"%'");
		}
		if(info!=null&&!info.equals("")){
			str.append(" and t.eventobject like '%"+info+"%'");
		}
		if(start!=null&&!start.equals("")){//str_to_date
			if(DBType.ORACLE == DBUtil.getDBType()){
				str.append(" AND t.systemoperatedate >= to_date('"+start+"','YYYY-MM-DD')");
			}else{
				str.append(" AND t.systemoperatedate >= str_to_date('"+start+"','YYYY-MM-DD')");
			}

			
		}
		if(end!=null&&!end.equals("")){
			if(DBType.ORACLE == DBUtil.getDBType()){
				str.append(" AND t.systemoperatedate < (to_date('"+end+"','YYYY-MM-DD')+1)");
			}else{
				str.append(" AND t.systemoperatedate < (str_to_date('"+end+"','YYYY-MM-DD')+1)");
			}
			
		}
		
		//日志导出按时间倒序
		str.append("order by t.systemoperatedate");
		
		
		String hql = str.toString();
		List<LogMain> list = sess.createQuery(hql).list();
		try {
			BuildXMLDoc(list,sess);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			FileExpWindowPageModel.isfinish=0;
			e.printStackTrace();
		}
	}
	public void BuildXMLDoc(List<LogMain> list,HBSession sess) throws IOException, JDOMException {     
        // 创建根节点 并设置它的属性 ;     
        Element root = new Element("log");     
        // 将根节点添加到文档中；     
        Document Doc = new Document(root);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileExpWindowPageModel.total=list.size();
        
          if(list.size()>0){
        	  for (int i = 0; i <list.size(); i++) {
        		  FileExpWindowPageModel.cur=i;
		           // 创建节点 book; 
        		   LogMain lm = (LogMain)list.get(i);
        		   if(lm.getEventobject()==null){
        			   lm.setEventobject("");
        		   }
        		   if(lm.getEventtype()==null){
        			   lm.setEventtype("");;
        		   }
        		   if(lm.getObjectid()==null){
        			   lm.setObjectid("");
        		   }
        		   if(lm.getObjectname()==null){
        			   lm.setObjectname("");
        		   }
        		   if(lm.getOperatecomments()==null){
        			   lm.setOperatecomments("");
        		   }
		           Element elements = new Element("mainInform");
		           elements.setAttribute("SYSTEM_LOG_ID", lm.getSystemlogid());
		           elements.setAttribute("USERLOG", lm.getUserlog());
		           elements.setAttribute("SYSTEM_OPERATE_DATE", sdf.format(lm.getSystemoperatedate()));
		           elements.setAttribute("EVENTTYPE", lm.getEventtype());
		           elements.setAttribute("EVENTOBJECT", lm.getEventobject());
		           elements.setAttribute("OBJECTID", lm.getObjectid());
		           elements.setAttribute("OBJECTNAME", lm.getObjectname());
		           elements.setAttribute("OPERATE_COMMENTS", lm.getOperatecomments());
		            String hql = "From LogDetail t where t.systemlogid='"+lm.getSystemlogid()+"'";
		            List list1 = getDetail(list,i,lm,500,sess);//sess.createQuery(hql).list();//
		            if(list1!=null&&list1.size()>0){
		            	for(int j=0;j<list1.size();j++){
		            		LogDetail ld = (LogDetail)list1.get(j);
		            		if(ld.getDataname()==null){
			        			   ld.setDataname("");
			        		   }
			        		   if(ld.getOldvalue()==null){
			        			   ld.setOldvalue("");
			        		   }
			        		   if(ld.getNewvalue()==null){
			        			   ld.setNewvalue("");;
			        		   }
		            		Element element = new Element("item");
		            		String sd = LogDetail.escapeAttributeEntities(ld.getOldvalue());
		            		String sd1 = LogDetail.escapeAttributeEntities(ld.getNewvalue());
		            		element.setAttribute("DATANAME", ld.getDataname());
		            		element.setAttribute("OLDVALUE", sd);
		            		element.setAttribute("NEWVALUE", sd1);
		            		element.setAttribute("CHANGEDATETIME",sdf.format(ld.getChangedatetime()));
		            		elements.addContent(element);
		            	}
		            }
		           root.addContent(elements);    
		       }  
          }
          
        // 输出 books.xml 文件；    
        // 使xml文件 缩进效果  
        Format format = Format.getPrettyFormat();  
        XMLOutputter XMLOut = new XMLOutputter(format);
        //String path = this.getServletContext().getRealPath("/");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd-HH：mm：ss");
        date = sdf1.format(new Date());
        File file = new File(path);
        if(!file.isDirectory()){
        	file.mkdirs();
        }
        XMLOut.output(Doc, new FileOutputStream(path+"日志备份文件"+date+".log")); 
        FileExpWindowPageModel.isfinish=0;
        FileExpWindowPageModel.total=0;
        FileExpWindowPageModel.cur=0;
	} 

	static Map<String, List<LogDetail>> LogMain = null;
	private List getDetail(List<LogMain> list, int i, LogMain lm2, int limit, HBSession sess) {
		if(i%limit==0){
			LogMain = null;
		}
		if(LogMain!=null){
			return LogMain.get(lm2.getSystemlogid());
		}
		LogMain = new HashMap<String, List<LogDetail>>();
			List<LogMain> logMainList = new ArrayList<LogMain>();
			StringBuffer sb = new StringBuffer("'");
			for(int n = i;n<(list.size()>(limit+i)?i+limit:list.size());n++){
				LogMain lgm = list.get(n);
				sb.append(lgm.getSystemlogid()+"','");
			}
			sb.delete(sb.length()-2, sb.length());
			String hql = "From LogDetail t where t.systemlogid in("+sb.toString()+")";
            List<LogDetail> logDetaillist = sess.createQuery(hql).list();
            if(logDetaillist!=null&&logDetaillist.size()>0){
            	for(int j=0;j<logDetaillist.size();j++){
            		LogDetail logDetail = logDetaillist.get(j);
            		if(LogMain.get(logDetail.getSystemlogid())==null){
            			List<LogDetail> sublogMainList = new ArrayList<LogDetail>();
            			LogMain.put(logDetail.getSystemlogid(), sublogMainList);
            		}
            		LogMain.get(logDetail.getSystemlogid()).add(logDetail);
            	}
            }
		
		return LogMain.get(lm2.getSystemlogid());
	}

	/**
	 * 下载文件(单个文件下载)方法
	 */
	public String downFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String path = this.getServletContext().getRealPath("/")+"pages\\sysmanager\\ZWHZYQ_001_004\\log\\日志备份文件"+date+".log";
		CommonQueryBS.systemOut(path);
        /*读取文件*/
        File file = new File(path);
        String filename = file.getName();
        //String foldername = this.getServletContext().getRealPath("/")+"pages\\sysmanager\\ZWHZYQ_001_004\\log";
        /*如果文件存在*/
        if (file.exists()) {
            response.reset();
            response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(filename.getBytes("GBK"), "ISO8859-1"));
           
            int fileLength = (int) file.length();
            response.setContentLength(fileLength);
            /*如果文件长度大于0*/
            if (fileLength != 0) {
                /*创建输入流*/
                InputStream inStream = new FileInputStream(file);
                byte[] buf = new byte[4096];
                /*创建输出流*/
                ServletOutputStream servletOS = response.getOutputStream();
                int readLength;
                while (((readLength = inStream.read(buf)) != -1)) {
                    servletOS.write(buf, 0, readLength);
                }
               //UploadHelpFileServlet.delFolder(foldername);
                inStream.close();
                servletOS.flush();
                servletOS.close();
                
            }
        }
       return path;
       
	}
	
}
