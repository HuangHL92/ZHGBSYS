package com.insigma.siis.local.pagemodel.exportexcel;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute.Space;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hsqldb.lib.StringUtil;

import sun.misc.BASE64Decoder;

import com.aspose.words.CompositeNode;
import com.aspose.words.Document;
import com.aspose.words.DocumentBuilder;
import com.aspose.words.FieldMergingArgs;
import com.aspose.words.IFieldMergingCallback;
import com.aspose.words.ImageFieldMergingArgs;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.aspose.words.ShapeType;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.demo.MapMailMergeDataSource;
import com.insigma.siis.demo.TestAspose2Pdf;
import com.insigma.siis.demo.entity.BbUtils;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.AddPersonPageModel;
import com.insigma.siis.local.pagemodel.search.PreSubmitPageModel;
import com.picCut.teetaa.util.ImageHepler;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 附件下载Servlet
 * @author lixy
 *
 */
public class FiledownServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String lx = request.getParameter("bc"); 
		if(lx != null && lx.equals("yzp") ){//用来判断是吴皓的代码还是王勰及，杨国庆的代码
			
			//------------------------以下是王勰 杨国庆 关于表册自定义的代码到else以前---
//			ExportDataBuilder edb = new ExportDataBuilder();
			ExportDataBuilderBcZdy edb = new ExportDataBuilderBcZdy();
			ExportPhotoExcel epe = new ExportPhotoExcel();
			request.setCharacterEncoding("GBK");
			String tempType = request.getParameter("excelType");//模板类型
			request.getSession().setAttribute("tpid", tempType);
			
			//名册展现方式
			
			//-------------------------------------------------------------------------------------------------------
			 String viewType1=request.getParameter("viewType")==null?request.getParameter("viewType2"):request.getParameter("viewType");
			 if("21".equals(viewType1)||"22".equals(viewType1)||"23".equals(viewType1)){
				 request.getSession().setAttribute("yviewType", viewType1);//展示方式
				 request.getSession().setAttribute("ytempType", tempType);//自定义表册
				 String checkList = request.getParameter("List");//人员id
				 
				 request.getSession().setAttribute("yList", checkList);//人员id
				 ServletOutputStream out =response.getOutputStream();
			     out.println("<script language='javascript'>parent.parent.showtemplate();</script>");
			 }else{
				 String viewType=request.getParameter("viewType")==null?request.getParameter("viewType3"):request.getParameter("viewType");
				 request.getSession().setAttribute("tempType", tempType);
				//文件路径？要导出的文件名称
				String fileName=request.getParameter("fileName"); 
				String downLoad=(String)request.getParameter("downLoad");
				String a0000=(String)request.getParameter("a0000");
				String a0101=(String)request.getParameter("a0101");
				
				HBSession sess = HBUtil.getHBSession();
				
				Template tmp = null;
				Map<String, Object> dataMap=null;
					if(a0000==null||"".equals(a0000)){
						request.setAttribute("a0000", "");
						dataMap=edb.getTempData( tempType,request,response);
						
						//-------------------------------------------------------
						
						request.getSession().setAttribute("ytempType", tempType);
						
						//-------------------------------------------------------
					}
				ServletOutputStream out =response.getOutputStream();
	        	
		} 
		
		}else{
			 //----------------------------以下是吳皓代碼 关于 登记表的方法结束-----------------------
			ExportDataBuilder edb = new ExportDataBuilder();
			ExportPhotoExcel epe = new ExportPhotoExcel();
			request.setCharacterEncoding("GBK");
			String tempType = request.getParameter("excelType");//模板类型
			//名册展现方式
			String viewType=request.getParameter("viewType")==null?request.getParameter("viewType2"):request.getParameter("viewType");
			//文件路径？要导出的文件名称
			String fileName=request.getParameter("fileName"); 
			String downLoad=(String)request.getParameter("downLoad");
			String a0000=(String)request.getParameter("a0000");
			String a0101=(String)request.getParameter("a0101");
			HBSession sess = HBUtil.getHBSession();
			
			Template tmp = null;
			if(!tempType.equals("6")&&!tempType.equals("8")){
				tmp=edb.getTemplate(tempType, request.getSession().getServletContext(),viewType);//获取导出模板
			}
			
			Map<String, Object> dataMap=null;
			if(("1".equals(tempType)||"2".equals(tempType))&&!"".equals(downLoad)&&downLoad!=null&&!"null".equals(downLoad)){//下载登记表时
				String[] a=a0000.split(",");
				if(a.length>0){
					String[] b=a0101.split(",");
	 				//System.out.println(fileName);
	 				for(int i=0;i<a.length;i++){
	 					List list = sess.createSQLQuery("select a0000 from Rydjb where a0000 = '"+a[i]+"'").list();
	 					//dataMap=edb.getDjbMap(a[i]);
	 					if(list.size()<=0){//判断有没有登记表数据
							 ServletOutputStream out =response.getOutputStream();
				             out.println("<script language='javascript'>alert('"+new String("没有生成过登记表不能下载，请先生成！".getBytes("gb2312"), "ISO8859-1" )+"');</script>");
				             return;
						}
	 					
						A01 a01= (A01)sess.get(A01.class, a[i]);
						A01 a01log = new A01();
						try {
							new LogUtil().createLog("52", "A01", a01.getA0000(), a01.getA0101(), "导出登记表", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IntrospectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	 				}
					downloadfile(a,b,tmp,edb,request,response,fileName);
					return;
				}else{
					dataMap=edb.getDjbMap(a0000);
					String a1701 = (String)dataMap.get("a1701");
					if(a1701 != null){
						a1701 = a1701.replaceAll("\r\n|\r|\n", "</w:t><w:br/><w:t>");
						dataMap.put("a1701", a1701);
					}
					
					
				}
			}else if("7".equals(tempType)){
				downloadfile2(a0000.split(","),null,tmp,edb,request,response,fileName);
				return;
			}else if("47".equals(tempType)){	//干部任免审批表
				String[] a = null;
				String[] b = null;
				a = a0000.split(",");
				b = a0101.split(",");
				downloadfile3(a,b,request,response,fileName);
				//downloadfile3(a,b,tmp,edb,request,response,fileName);
				return;
			}else{
				if(a0000==null||"".equals(a0000)){
					request.setAttribute("a0000", "");
					dataMap=edb.getTempData( tempType,request);
					if(tempType.equals("6")||tempType.equals("8")){
						Workbook workbook = null;
					    workbook = epe.export(dataMap,tempType);
						downloadExcel(workbook, response, fileName);
						return;
					}
					
				}else{
					String[] a=a0000.split(",");
					for(int i=0;i<a.length;i++){
						request.setAttribute("a0000", a[i]);
						request.setAttribute("a00", a0000);
						if(!tempType.equals("100")){
						A01 a01= (A01)sess.get(A01.class, a[i]);
						A01 a01log = new A01();
						try {
							new LogUtil().createLog("51", "A01", a01.getA0000(), a01.getA0101(), "生成登记表", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IntrospectionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AppException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						}
					    dataMap=edb.getTempData( tempType,request);
					}
				}
			}
			
			if(dataMap==null||dataMap.size()<=0){//判断有没有登记表数据
				 ServletOutputStream out =response.getOutputStream();
	             out.println("<script language='javascript'>alert('"+new String( "没有生成过登记表不能下载，请先生成！".getBytes("gb2312"), "ISO8859-1" )+"');</script>");
	             return;
			}
			
			if(("1".equals(tempType)||"2".equals(tempType))&&!"true".equals(downLoad)){
				  ServletOutputStream out =response.getOutputStream();
	              out.println("<script language='javascript'>alert('"+new String( "登记表生成成功！".getBytes("gb2312"), "ISO8859-1" )+"');</script>");
	              out.println("<script language='javascript'>parent.parent.expExcelTemp();</script>");
	              return;
			}
	        // 以流的形式下载文件。
			File outFile = new File(fileName);
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "utf-8"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				tmp.process(dataMap, out);
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        // 以流的形式下载文件。
	        InputStream fis = new BufferedInputStream(new FileInputStream(outFile));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        //String word = new String(buffer,"latin1");
	        //buffer = word.replaceAll("_rn_", "_rn_").getBytes("latin1");
	        fis.close();
	        // 清空response
	        response.reset();
	        // 设置response的Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
	        response.addHeader("Content-Length", "" + outFile.length());
	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	       
			
		}
	}
	
	public void downloadfile(String[] a,String[] b,Template tmp,ExportDataBuilder edb,HttpServletRequest request, HttpServletResponse response,String fileName){
		Map<String, Object> dataMap=null;
		String expFile = ExpRar.expFile();
		Date date = new Date();
		if(a.length>0){
			for(int i=0;i<a.length;i++){				
				
				request.setAttribute("a0000", a[i]);
				dataMap=edb.getDjbMap(a[i]);
				String a1701 = (String)dataMap.get("a1701");
				if(a1701 != null){
					//简历格式化
					StringBuffer originaljl = new StringBuffer("");
					String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
					a1701 = jianli.replaceAll("\r\n|\r|\n", "</w:t><w:br/><w:t>");
					dataMap.put("a1701", a1701);
				}
				CommonQueryBS.systemOut(expFile+b[i]+".doc");
				File outFile = new File(expFile+i+"_"+b[i]+".doc");
				Writer out = null;
				try {
					out = new BufferedWriter(new OutputStreamWriter(
							new FileOutputStream(outFile), "utf-8"));
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					tmp.process(dataMap, out);
					out.close();
				} catch (TemplateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String downloadfileName =expFile.substring(0,expFile.length()-1)+".zip" ;
			SevenZipUtil.zip7z(expFile, downloadfileName, null);
			// 以流的形式下载文件。
			File outFile = new File(downloadfileName);

	        InputStream fis;
			try {
				fis = new BufferedInputStream(new FileInputStream(downloadfileName));
				byte[] buffer = new byte[fis.available()];
		        fis.read(buffer);
		        fis.close();
		        // 清空response
		        response.reset();
		        // 设置response的Header
		        if(fileName.contains(".")){
			        fileName=fileName.substring(0,fileName.indexOf("."))+".zip";
		        }else{
		        	fileName=fileName+".zip";
		        }
		        response.addHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
		        response.addHeader("Content-Length", "" + outFile.length());
		        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		        response.setContentType("application/octet-stream");
		        toClient.write(buffer);
		        toClient.flush();
		        toClient.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		}
	}
	
	
	public void downloadfile2(String[] a, String[] b, Template tmp, ExportDataBuilder edb, HttpServletRequest request,
			HttpServletResponse response, String fileName) throws IOException {
		Map<String, Object> dataMap=null;
		Map<String, Object> tmpData=null;
		String expFile = ExpRar.expFile();
		String seq = "";
		if(a.length>0){
			 List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
			for(int i=0;i<a.length;i++){
				request.setAttribute("a0000", a[i]);
				dataMap=edb.getbaList(request,a[i]);
				seq=Integer.toString(i+1);
				dataMap.put("seq", seq);
			   
			    list.add(dataMap);
			}
			tmpData=new Hashtable<String, Object>();
		    tmpData.put("sb", list);
		    
		    tmpData.put("date", edb.getDate());
		}
			//String downloadfileName =expFile.substring(0,expFile.length()-1)+".zip" ;
			//SevenZipUtil.zip7z(expFile, downloadfileName, null);
	        // 以流的形式下载文件。
			File outFile = new File(fileName);
			Writer out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(outFile), "utf-8"));
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			try {
				tmp.process(tmpData, out);
				
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        // 以流的形式下载文件。
	        InputStream fis = new BufferedInputStream(new FileInputStream(outFile));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        //String word = new String(buffer,"latin1");
	        //buffer = word.replaceAll("_rn_", "_rn_").getBytes("latin1");
	        fis.close();
	        // 清空response
	        response.reset();
	        // 设置response的Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String( "公务员登记备案汇总表.doc".getBytes("gb2312"), "ISO8859-1" ));
	        response.addHeader("Content-Length", "" + outFile.length());
	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	        
		}
	
	public String changeCharset(String str, String newCharset)
			throws UnsupportedEncodingException {
		if (str != null) {
			//用默认字符编码解码字符串。
			byte[] bs = str.getBytes();
			//用新的字符编码生成字符串
			return new String(bs, newCharset);
		}
		return null;
	}
	 
	 protected void downloadExcel(Workbook workbook, HttpServletResponse response, String filename) throws IOException {
	        OutputStream out = response.getOutputStream();
	    response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
	    response.setContentType("application/msexcel;charset=UTF-8");
	    workbook.write(out);
	    out.close();        
	}
	 
	 public static boolean getLicense() {
			boolean result = false;
			try {
				InputStream is = TestAspose2Pdf.class.getClassLoader()
						.getResourceAsStream("Aspose.Words.lic"); // license.xml应放在..\WebRoot\WEB-INF\classes路径下
				License aposeLic = new License();
				aposeLic.setLicense(is);
				result = true;
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		} 
	 
	 public void downloadfile3(String[] a,String[] b,HttpServletRequest request, HttpServletResponse response,String fileName){
		 if (!getLicense()) { // 验证License 若不验证则生成的word文档会有水印产生
				return;
			}
		 //获取模板路径  
		 String classPath = FiledownServlet.class.getClassLoader().getResource("/").getPath();
		 try {
				classPath = URLDecoder.decode(classPath, "GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String rootPath  = ""; 
			
			//windows下 
			if("\\".equals(File.separator)){   
				rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("/", "\\"); 
			} 
			//linux下 
			if("/".equals(File.separator)){   
				rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
				rootPath = rootPath.replace("\\", "/"); 
			}
		 rootPath = rootPath + "pages/exportexcel/";
	     String doctpl = "";
		 Map<String, Object> dataMap=null;
		 String expFile = ExpRar.expFile();
		 if(a != null && a.length > 0){
			 for(int i=0;i<a.length;i++){
				 String a0000 = a[i].replace("'", "");
				//创建Document对象，读取Word模板  
			     try {
			    	dataMap = getMap(a0000);
			    	String QRZXLXX = (String)dataMap.get("qrzxlxx");
					String QRZXWXX = (String)dataMap.get("qrzxwxx");
					String ZZXLXX = (String)dataMap.get("zzxlxx");
					String ZZXWXX = (String)dataMap.get("zzxwxx");
					if(QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
						//上一，下二
						doctpl = rootPath+"gbrmspb_4.doc";
					}else if(!QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
						//上二，下一
						doctpl = rootPath+"gbrmspb_3.doc";
					}else if(!QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
						//上二，下二
						doctpl = rootPath+"gbrmspb_2.doc";
					}else if(QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
						//上一，下一
						doctpl = rootPath+"gbrmspb_1.doc";
					}
					Document doc = new Document(doctpl);
					//增加处理简历程序
			        doc.getMailMerge().setFieldMergingCallback(new HandleMergeFieldFromBlob());
			        //向模板填充数据源  
			        //doc.getMailMerge().executeWithRegions(new MapMailMergeDataSource(getMapList2(a0000), "Employees"));
			        StringBuffer mapkey = new StringBuffer();
		        	StringBuffer mapvalue = new StringBuffer();
			        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
			        	String key = entry.getKey();
			        	Object value = entry.getValue();
			        	if(key.equals("image")){
			        		 A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
						     if (a57 != null) {
						    	 byte[] image = PhotosUtil.getPhotoData(a57);
						    	 if (image != null) {
						    		 value = PhotosUtil.PHOTO_PATH + a57.getPhotopath()+ a57.getPhotoname();
						    	 }else{
						    		 value = "";
						    	 }
							}else{
								value = "";
							}
			        	}
			        	mapkey = mapkey.append(key+",");
			        	mapvalue = mapvalue.append(value+",");
			        }
			        //文本域
			        String[] Flds = mapkey.toString().split(",");
			        //值
			        String[] Vals = mapvalue.toString().split(",");
			        
			        //填充单个数据  
			        doc.getMailMerge().execute(Flds, Vals);
			        File outFile = new File(expFile+(i+1)+"_"+b[i]+".doc");
			        //写入到Word文档中  
			        ByteArrayOutputStream os = new ByteArrayOutputStream(); 
			        //保存到输出流中  
			        doc.save(os, SaveFormat.DOC);  
			        OutputStream out = new FileOutputStream(outFile);  
			        out.write(os.toByteArray());  
			        out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 String downloadfileName =expFile.substring(0,expFile.length()-1)+".zip" ;
			 SevenZipUtil.zip7z(expFile, downloadfileName, null);
			 // 以流的形式下载文件。
			 File outFile = new File(downloadfileName);
		     InputStream fis;
			 try {
				 fis = new BufferedInputStream(new FileInputStream(downloadfileName));
				 byte[] buffer = new byte[fis.available()];
			     fis.read(buffer);
			     fis.close();
			     // 清空response
			     response.reset();
			     // 设置response的Header
			     if(fileName.contains(".")){
				     fileName=fileName.substring(0,fileName.indexOf("."))+".zip";
			     }else{
			         fileName=fileName+".zip";
			     }
			     response.addHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
			     response.addHeader("Content-Length", "" + outFile.length());
			     OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			     response.setContentType("application/octet-stream");
			     toClient.write(buffer);
			     toClient.flush();
			     toClient.close();
			 } catch (FileNotFoundException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 } catch (IOException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
			 }
		 }
	 }
	 
	 public static Map<String, Object> getMap(String a0000,String tpid) throws Exception{  
	        //List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	        ExportDataBuilder edb = new ExportDataBuilder();
	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        if(tpid.equals("eebdefc2-4d67-4452-a973-5f7939530a11")){
	        	dataMap = edb.getGbrmspbMap(a0000);
	        }else if(tpid.equals("B73E508A87A44EF889430ABA451AC85C")){
	        	dataMap = edb.getGbrmspbMap(a0000);
	        }else if(tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
	        	dataMap = edb.getGwydjbMap(a0000);
	        }else if(tpid.equals("a43d8c50-400d-42fe-9e0d-5665ed0b0508")){
	        	dataMap = edb.getNdkhdjb(a0000);
	        }else if(tpid.equals("04f59673-9c3a-4d9c-b016-a5b789d636e2")){
	        	dataMap = edb.getGwyjlspb(a0000);
	        }else if(tpid.equals("3de527c0-ea23-42c4-a66f")){
	        	//dataMap = edb.getGwylyspb(a0000);
	        	dataMap = edb.getGwylyb(a0000);
	        }else if(tpid.equals("9e7e1226-6fa1-46a1-8270")){
	        	dataMap = edb.getGwydrspb(a0000);
	        }else if(tpid.equals("28bc4d39-dccd-4f07-8aa9")){
	        	dataMap = edb.getGwyzjtgb(a0000);
	        }
			/** 姓名 */
			String a0101 = (String) dataMap.get("a0101");
			/** 民族 */
			String a0117 = (String) dataMap.get("a0117");
			/** 籍贯 */
			String a0111a = (String) dataMap.get("a0111a");
			/** 健康 */
			String a0128 = (String) dataMap.get("a0128");
			/** 专业技术职务 */
			String a0196 = (String) dataMap.get("a0196");
			/** 特长 */
			String a0187a = (String) dataMap.get("a0187a");
			/** 身份证号码 */
			String a0184 = (String) dataMap.get("a0184");
			//格式化时间参数
			String a0107 = (String)dataMap.get("a0107");
			String a0134 = (String)dataMap.get("a0134");
			String a1701 = (String)dataMap.get("a1701");
			String a2949 = (String)dataMap.get("a2949");
			String a0288 = (String) dataMap.get("a0288");
			String a0192t = (String) dataMap.get("a0192t");
			//格式化学历学位
			String qrzxl = (String)dataMap.get("qrzxl");
			String qrzxw = (String)dataMap.get("qrzxw");
			String zzxl = (String)dataMap.get("zzxl");
			String zzxw = (String)dataMap.get("zzxw");
			if(!StringUtil.isEmpty(qrzxl)&&!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxl+"\r\n"+qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxl)){
				String qrzxlxw = qrzxl;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else{
				dataMap.put("qrzxlxw", "");
			}
			if(!StringUtil.isEmpty(zzxl)&&!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxl+"\r\n"+zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxl)){
				String zzxlxw = zzxl;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else{
				dataMap.put("zzxlxw", "");
			}
			//格式化学校及院系
			String QRZXLXX = (String) dataMap.get("qrzxlxx");
			String QRZXWXX = (String) dataMap.get("qrzxwxx");
			String ZZXLXX = (String) dataMap.get("zzxlxx");
			String ZZXWXX = (String) dataMap.get("zzxwxx");
			if(!StringUtil.isEmpty(QRZXLXX)&&!StringUtil.isEmpty(QRZXWXX)&&!QRZXLXX.equals(QRZXWXX)){
				String qrzxlxx = QRZXLXX+"\r\n"+QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXLXX)){
				String qrzxlxx = QRZXLXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXWXX)){
				String qrzxlxx = QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else{
				dataMap.put("qrzxlxx", "");
			}
			if(!StringUtil.isEmpty(ZZXLXX)&&!StringUtil.isEmpty(ZZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				String zzxlxx = ZZXLXX+"\r\n"+ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXLXX)){
				String zzxlxx = ZZXLXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXWXX)){
				String zzxlxx = ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else{
				dataMap.put("zzxlxx", "");
			}
			if(a1701 != null){
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
				a1701 = jianli;
				dataMap.put("a1701", a1701);
			}
			if(a0107 != null && !"".equals(a0107)){
				a0107 = a0107.substring(0,4)+"."+a0107.substring(4,6);
				dataMap.put("a0107", a0107);
			}
			if(a0134 != null && !"".equals(a0134)){
				a0134 = a0134.substring(0,4)+"."+a0134.substring(4,6);
				dataMap.put("a0134", a0134);
			}
			if(a2949 != null && !"".equals(a2949)){
				a2949 = a2949.substring(0,4)+"."+a2949.substring(4,6);
				dataMap.put("a2949", a2949);
			}
			if(a0288 != null && !"".equals(a0288)){
				a0288 = a0288.substring(0,4)+"."+a0288.substring(4,6);
				dataMap.put("a0288", a0288);
			}
			if(a0192t != null && !"".equals(a0192t)){
				a0192t = a0192t.substring(0,4)+"."+a0192t.substring(4,6);
				dataMap.put("a0192t", a0192t);
			}
			//姓名2个字加空格
/*			if(a0101 != null){
				if(a0101.length() == 2){
					StringBuilder sb = new StringBuilder();
				    int length = a0101.length();
				    for (int i1 = 0; i1 < length; i1++) {
				        if (length - i1 <= 2) {      //防止ArrayIndexOutOfBoundsException
				            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				            sb.append(a0101.substring(i1 + 1));
				            break;
				        }
				        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				    }
				    dataMap.put("a0101", sb.toString());
				}
			}*/
			
			dataMap.put("a0101" , Space(a0101) );
			dataMap.put("a0117" , a0117);
			dataMap.put("a0111a", Space(a0111a));
			dataMap.put("a0128" , a0128);
			dataMap.put("a0196" , a0196);
			dataMap.put("a0187a", Space(a0187a));
			//System.out.println(dataMap);
			//现职务层次格式化
			
			String a0221 = (String)dataMap.get("a0221");
			if(a0221 !=null){
				if(a0221.length()==5){
					a0221 = a0221.substring(0,3)+"\r\n"+a0221.substring(3);
					dataMap.put("a0221", a0221);
					dataMap.put("a0148", a0221);
				}
			}
			String a0192d = (String) dataMap.get("a0192d");
			if(a0192d !=null){
				if(a0192d.length()>5){
					a0192d = a0192d.substring(0, 3)+"\r\n"+a0192d.substring(3);
					dataMap.put("a0192d", a0192d);
				}
			}
			//dataList.add(dataMap);
	        return dataMap;
		}
	
	 public static Map<String, Object> getMap(String a0000,String tpid, String js0122) throws Exception{  
	        //List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	        ExportDataBuilder edb = new ExportDataBuilder();
	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        if(tpid.equals("eebdefc2-4d67-4452-a973-5f7939530a11")){
	        	dataMap = edb.getGbrmspbMap(a0000, js0122);
	        }else if(tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
	        	dataMap = edb.getGwydjbMap(a0000);
	        }else if(tpid.equals("a43d8c50-400d-42fe-9e0d-5665ed0b0508")){
	        	dataMap = edb.getNdkhdjb(a0000);
	        }else if(tpid.equals("04f59673-9c3a-4d9c-b016-a5b789d636e2")){
	        	dataMap = edb.getGwyjlspb(a0000);
	        }else if(tpid.equals("3de527c0-ea23-42c4-a66f")){
	        	//dataMap = edb.getGwylyspb(a0000);
	        	dataMap = edb.getGwylyb(a0000);
	        }else if(tpid.equals("9e7e1226-6fa1-46a1-8270")){
	        	dataMap = edb.getGwydrspb(a0000);
	        }else if(tpid.equals("28bc4d39-dccd-4f07-8aa9")){
	        	dataMap = edb.getGwyzjtgb(a0000);
	        }
			/** 姓名 */
			String a0101 = (String) dataMap.get("a0101");
			/** 民族 */
			String a0117 = (String) dataMap.get("a0117");
			/** 籍贯 */
			String a0111a = (String) dataMap.get("a0111a");
			/** 健康 */
			String a0128 = (String) dataMap.get("a0128");
			/** 专业技术职务 */
			String a0196 = (String) dataMap.get("a0196");
			/** 特长 */
			String a0187a = (String) dataMap.get("a0187a");
			/** 身份证号码 */
			String a0184 = (String) dataMap.get("a0184");
			//格式化时间参数
			String a0107 = (String)dataMap.get("a0107");
			String a0134 = (String)dataMap.get("a0134");
			String a1701 = (String)dataMap.get("a1701");
			String a2949 = (String)dataMap.get("a2949");
			String a0288 = (String) dataMap.get("a0288");
			String a0192t = (String) dataMap.get("a0192t");
			//格式化学历学位
			String qrzxl = (String)dataMap.get("qrzxl");
			String qrzxw = (String)dataMap.get("qrzxw");
			String zzxl = (String)dataMap.get("zzxl");
			String zzxw = (String)dataMap.get("zzxw");
			if(!StringUtil.isEmpty(qrzxl)&&!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxl+"\r\n"+qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxl)){
				String qrzxlxw = qrzxl;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else{
				dataMap.put("qrzxlxw", "");
			}
			if(!StringUtil.isEmpty(zzxl)&&!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxl+"\r\n"+zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxl)){
				String zzxlxw = zzxl;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else{
				dataMap.put("zzxlxw", "");
			}
			//格式化学校及院系
			String QRZXLXX = (String) dataMap.get("qrzxlxx");
			String QRZXWXX = (String) dataMap.get("qrzxwxx");
			String ZZXLXX = (String) dataMap.get("zzxlxx");
			String ZZXWXX = (String) dataMap.get("zzxwxx");
			if(!StringUtil.isEmpty(QRZXLXX)&&!StringUtil.isEmpty(QRZXWXX)&&!QRZXLXX.equals(QRZXWXX)){
				String qrzxlxx = QRZXLXX+"\r\n"+QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXLXX)){
				String qrzxlxx = QRZXLXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXWXX)){
				String qrzxlxx = QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else{
				dataMap.put("qrzxlxx", "");
			}
			if(!StringUtil.isEmpty(ZZXLXX)&&!StringUtil.isEmpty(ZZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				String zzxlxx = ZZXLXX+"\r\n"+ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXLXX)){
				String zzxlxx = ZZXLXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXWXX)){
				String zzxlxx = ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else{
				dataMap.put("zzxlxx", "");
			}
			if(a1701 != null){
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
				a1701 = jianli;
				dataMap.put("a1701", a1701);
			}
			if(a0107 != null && !"".equals(a0107)){
				a0107 = a0107.substring(0,4)+"."+a0107.substring(4,6);
				dataMap.put("a0107", a0107);
			}
			if(a0134 != null && !"".equals(a0134)){
				a0134 = a0134.substring(0,4)+"."+a0134.substring(4,6);
				dataMap.put("a0134", a0134);
			}
			if(a2949 != null && !"".equals(a2949)){
				a2949 = a2949.substring(0,4)+"."+a2949.substring(4,6);
				dataMap.put("a2949", a2949);
			}
			if(a0288 != null && !"".equals(a0288)){
				a0288 = a0288.substring(0,4)+"."+a0288.substring(4,6);
				dataMap.put("a0288", a0288);
			}
			if(a0192t != null && !"".equals(a0192t)){
				a0192t = a0192t.substring(0,4)+"."+a0192t.substring(4,6);
				dataMap.put("a0192t", a0192t);
			}
			//姓名2个字加空格
/*			if(a0101 != null){
				if(a0101.length() == 2){
					StringBuilder sb = new StringBuilder();
				    int length = a0101.length();
				    for (int i1 = 0; i1 < length; i1++) {
				        if (length - i1 <= 2) {      //防止ArrayIndexOutOfBoundsException
				            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				            sb.append(a0101.substring(i1 + 1));
				            break;
				        }
				        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				    }
				    dataMap.put("a0101", sb.toString());
				}
			}*/
			
			dataMap.put("a0101" , Space(a0101) );
			dataMap.put("a0117" , Space(a0117) );
			dataMap.put("a0111a", Space(a0111a));
			dataMap.put("a0128" , Space(a0128) );
			dataMap.put("a0196" , Space(a0196) );
			dataMap.put("a0187a", Space(a0187a));
			//System.out.println(dataMap);
			//现职务层次格式化
			
			String a0221 = (String)dataMap.get("a0221");
			if(a0221 !=null){
				if(a0221.length()==5){
					a0221 = a0221.substring(0,3)+"\r\n"+a0221.substring(3);
					dataMap.put("a0221", a0221);
					dataMap.put("a0148", a0221);
				}
			}
			String a0192d = (String) dataMap.get("a0192d");
			if(a0192d !=null){
				if(a0192d.length()>5){
					a0192d = a0192d.substring(0, 3)+"\r\n"+a0192d.substring(3);
					dataMap.put("a0192d", a0192d);
				}
			}
			//dataList.add(dataMap);
	        return dataMap;
		}
	/**
	 * 加空格处理
	 */
	public static String Space(String Value) {
		String text_Value = "";
		if (Value != null) {
			if (Value.length() == 2) {
				StringBuffer sb = new StringBuffer();
				int length = Value.length();
				for (int i1 = 0; i1 < length; i1++) {
					if (length - i1 <= 2) { // 防止ArrayIndexOutOfBoundsException
						sb.append(Value.substring(i1, i1 + 1)).append("  ");
						sb.append(Value.substring(i1 + 1));
						break;
					}
					sb.append(Value.substring(i1, i1 + 1)).append("  ");
				}
				text_Value = sb.toString();
			} else {
				text_Value = Value;	
			}
		}
		return text_Value;
	}
	 
	 public static Map<String, Object> getMap(String a0000) throws Exception{  
	        //List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	        ExportDataBuilder edb = new ExportDataBuilder();
	        Map<String, Object> dataMap = edb.getGbrmspbMap(a0000);
	        String a0101 = (String)dataMap.get("a0101");
			
	      //格式化时间参数
			String a0107 = (String)dataMap.get("a0107");
			String a0134 = (String)dataMap.get("a0134");
			String a1701 = (String)dataMap.get("a1701");
			//格式化学历学位
			String qrzxl = (String)dataMap.get("qrzxl");
			String qrzxw = (String)dataMap.get("qrzxw");
			String zzxl = (String)dataMap.get("zzxl");
			String zzxw = (String)dataMap.get("zzxw");
			if(!StringUtil.isEmpty(qrzxl)&&!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxl+"\r\n"+qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxl)){
				String qrzxlxw = qrzxl;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else{
				dataMap.put("qrzxlxw", "");
			}
			if(!StringUtil.isEmpty(zzxl)&&!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxl+"\r\n"+zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxl)){
				String zzxlxw = zzxl;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else{
				dataMap.put("zzxlxw", "");
			}
			//格式化学校及院系
			String QRZXLXX = (String) dataMap.get("qrzxlxx");
			String QRZXWXX = (String) dataMap.get("qrzxwxx");
			String ZZXLXX = (String) dataMap.get("zzxlxx");
			String ZZXWXX = (String) dataMap.get("zzxwxx");
			if(!StringUtil.isEmpty(QRZXLXX)&&!StringUtil.isEmpty(QRZXWXX)&&!QRZXLXX.equals(QRZXWXX)){
				String qrzxlxx = QRZXLXX+"\r\n"+QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXLXX)){
				String qrzxlxx = QRZXLXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXWXX)){
				String qrzxlxx = QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else{
				dataMap.put("qrzxlxx", "");
			}
			if(!StringUtil.isEmpty(ZZXLXX)&&!StringUtil.isEmpty(ZZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				String zzxlxx = ZZXLXX+"\r\n"+ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXLXX)){
				String zzxlxx = ZZXLXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXWXX)){
				String zzxlxx = ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else{
				dataMap.put("zzxlxx", "");
			}
			if(a1701 != null){
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
				a1701 = jianli;
				dataMap.put("a1701", a1701);
			}
			if(a0107 != null && !"".equals(a0107)){
				a0107 = a0107.substring(0,4)+"."+a0107.substring(4,6);
				dataMap.put("a0107", a0107);
			}
			if(a0134 != null && !"".equals(a0134)){
				a0134 = a0134.substring(0,4)+"."+a0134.substring(4,6);
				dataMap.put("a0134", a0134);
			}
			//姓名2个字加空格
			if(a0101 != null){
				if(a0101.length() == 2){
					StringBuilder sb = new StringBuilder();
				    int length = a0101.length();
				    for (int i1 = 0; i1 < length; i1++) {
				        if (length - i1 <= 2) {      //防止ArrayIndexOutOfBoundsException
				            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				            sb.append(a0101.substring(i1 + 1));
				            break;
				        }
				        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				    }
				    dataMap.put("a0101", sb.toString());
				}
			}
			//dataList.add(dataMap);
	        return dataMap;
		}
	 
	 private static class HandleMergeFieldFromBlob implements IFieldMergingCallback {
		    public void fieldMerging(FieldMergingArgs args) throws Exception {
		    	 if (args.getDocumentFieldName().equals("a1701")){
		             // 使用DocumentBuilder处理简历
		             DocumentBuilder builder = new DocumentBuilder(args.getDocument());
		             builder.moveToMergeField(args.getFieldName());
		             BbUtils.formatArray(builder, args, true);
		         }else if(args.getDocumentFieldName().equals("image")){
		        	 String image = args.getFieldValue().toString();
		        	 if(!StringUtil.isEmpty(image)){
		        		 DocumentBuilder builder = new DocumentBuilder(args.getDocument());
			             builder.moveToMergeField(args.getFieldName());
			             builder.insertImage(args.getFieldValue().toString(),105,145); 
		        	 }
		         }
		    }

		    public void imageFieldMerging(ImageFieldMergingArgs e) throws Exception {
//		    	if (e.getDocumentFieldName().equals("image")){
//		    		DocumentBuilder builder = new DocumentBuilder(e.getDocument());
//	                builder.moveToMergeField(e.getFieldName());
//	                //向此单元格中插入图片
//	                builder.insertImage(e.getFieldValue().toString());
//	                /*Shape shape = new Shape(doc, ShapeType.IMAGE);
//	                String url = "";
//	                shape.getImageData().setImage(new BASE64Decoder().decodeBuffer(e.getFieldValue().toString()));
//	                CompositeNode node = shape.getParentNode();
//	                builder.insertNode(shape);*/
//
//		    	}
		    }
		}
	
	 
	 public void downloadfile3(String[] a,String[] b,Template tmp,ExportDataBuilder edb,HttpServletRequest request, HttpServletResponse response,String fileName){
			Map<String, Object> dataMap=null;
			String tempType = request.getParameter("excelType");
			if(tempType.equals("47")){
				String expFile = ExpRar.expFile();
				Date date = new Date();
				if(a != null && a.length > 0){
					for(int i=0;i<a.length;i++){
						String a0000 = a[i].replace("'", "");
						request.setAttribute("a0000", a0000);
						dataMap=edb.getGbrmspbMap(a0000);
						String a0101 = (String)dataMap.get("a0101");
						String QRZXLXX = (String)dataMap.get("qrzxlxx");
						String QRZXWXX = (String)dataMap.get("qrzxwxx");
						String ZZXLXX = (String)dataMap.get("zzxlxx");
						String ZZXWXX = (String)dataMap.get("zzxwxx");
						//格式化时间参数
						String a0107 = (String)dataMap.get("a0107");
						String a0134 = (String)dataMap.get("a0134");
						String a1701 = (String)dataMap.get("a1701");
						if(a1701 != null){
							//简历格式化
							StringBuffer originaljl = new StringBuffer("");
							String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
							a1701 = jianli.replaceAll("\r\n|\r|\n", "</w:t><w:br/><w:t>");
							//a1701 = jianli;
							dataMap.put("a1701", a1701);
						}
						if(a0107 != null && !"".equals(a0107)){
							a0107 = a0107.substring(0,4)+"."+a0107.substring(4,6);
							dataMap.put("a0107", a0107);
						}
						if(a0134 != null && !"".equals(a0134)){
							a0134 = a0134.substring(0,4)+"."+a0134.substring(4,6);
							dataMap.put("a0134", a0134);
						}
						//姓名2个字加空格
						if(a0101 != null){
							if(a0101.length() == 2){
								StringBuilder sb = new StringBuilder();
							    int length = a0101.length();
							    for (int i1 = 0; i1 < length; i1++) {
							        if (length - i1 <= 2) {      //防止ArrayIndexOutOfBoundsException
							            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
							            sb.append(a0101.substring(i1 + 1));
							            break;
							        }
							        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
							    }
							    dataMap.put("a0101", sb.toString());
							}
						}
						if(QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
							try {
								//上一，下二
								tmp=edb.getTemplate("45", request.getSession().getServletContext(),"1");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//获取导出模板
						}else if(!QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
							try {
								//上二，下一
								tmp=edb.getTemplate("46", request.getSession().getServletContext(),"1");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//获取导出模板
						}else if(!QRZXLXX.equals(QRZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
							try {
								//上二，下二
								tmp=edb.getTemplate("47", request.getSession().getServletContext(),"1");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//获取导出模板
						}else if(QRZXLXX.equals(QRZXWXX)&&ZZXLXX.equals(ZZXWXX)){
							try {
								//上一，下一
								tmp=edb.getTemplate("44", request.getSession().getServletContext(),"1");
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}//获取导出模板
						}
						//CommonQueryBS.systemOut(expFile+b[i]+".doc");
						File outFile = new File(expFile+(i+1)+"_"+b[i]+".doc");
						Writer out = null;
						try {
							out = new BufferedWriter(new OutputStreamWriter(
									new FileOutputStream(outFile), "utf-8"));
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						try {
							tmp.process(dataMap, out);
							out.close();
						} catch (TemplateException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					String downloadfileName =expFile.substring(0,expFile.length()-1)+".zip" ;
					SevenZipUtil.zip7z(expFile, downloadfileName, null);
					// 以流的形式下载文件。
					File outFile = new File(downloadfileName);
			        InputStream fis;
					try {
						fis = new BufferedInputStream(new FileInputStream(downloadfileName));
						byte[] buffer = new byte[fis.available()];
				        fis.read(buffer);
				        fis.close();
				        // 清空response
				        response.reset();
				        // 设置response的Header
				        if(fileName.contains(".")){
					        fileName=fileName.substring(0,fileName.indexOf("."))+".zip";
				        }else{
				        	fileName=fileName+".zip";
				        }
				        response.addHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
				        response.addHeader("Content-Length", "" + outFile.length());
				        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				        response.setContentType("application/octet-stream");
				        toClient.write(buffer);
				        toClient.flush();
				        toClient.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	 
	 /**
	  * 自定义导出
	  * 
	  * */
	 public static Map<String, Object> getMapw(String a0000,String name,List list,String queryCondition,String rad) throws Exception{  
	        //List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
	        ExportDataBuilder edb = new ExportDataBuilder();
	        Map<String, Object> dataMap = new HashMap<String, Object>();
	        if(!"".equals(name)){
	        	dataMap = edb.getGbrmspbMapw(a0000,list, queryCondition,rad);
	        }
	        String a0101 = (String)dataMap.get("a0101");
			
			//格式化时间参数
			String a01_a0107 = (String)dataMap.get("a01_a0107");
			String a01_a0144 = (String)dataMap.get("a01_a0144");
			String a01_a0134 = (String)dataMap.get("a01_a0134");
		
			String a0134 = (String)dataMap.get("a0134");
			String a1701 = (String)dataMap.get("a1701");
			//格式化学历学位
			String qrzxl = (String)dataMap.get("qrzxl");
			String qrzxw = (String)dataMap.get("qrzxw");
			String zzxl = (String)dataMap.get("zzxl");
			String zzxw = (String)dataMap.get("zzxw");
			if(!StringUtil.isEmpty(qrzxl)&&!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxl+"\r\n"+qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxl)){
				String qrzxlxw = qrzxl;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else if(!StringUtil.isEmpty(qrzxw)){
				String qrzxlxw = qrzxw;
				dataMap.put("qrzxlxw", qrzxlxw);
			}else{
				dataMap.put("qrzxlxw", "");
			}
			if(!StringUtil.isEmpty(zzxl)&&!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxl+"\r\n"+zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxl)){
				String zzxlxw = zzxl;
				dataMap.put("zzxlxw", zzxlxw);
			}else if(!StringUtil.isEmpty(zzxw)){
				String zzxlxw = zzxw;
				dataMap.put("zzxlxw", zzxlxw);
			}else{
				dataMap.put("zzxlxw", "");
			}
			//格式化学校及院系
			String QRZXLXX = (String) dataMap.get("qrzxlxx");
			String QRZXWXX = (String) dataMap.get("qrzxwxx");
			String ZZXLXX = (String) dataMap.get("zzxlxx");
			String ZZXWXX = (String) dataMap.get("zzxwxx");
			if(!StringUtil.isEmpty(QRZXLXX)&&!StringUtil.isEmpty(QRZXWXX)&&!QRZXLXX.equals(QRZXWXX)){
				String qrzxlxx = QRZXLXX+"\r\n"+QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXLXX)){
				String qrzxlxx = QRZXLXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else if(!StringUtil.isEmpty(QRZXWXX)){
				String qrzxlxx = QRZXWXX;
				dataMap.put("qrzxlxx", qrzxlxx);
			}else{
				dataMap.put("qrzxlxx", "");
			}
			if(!StringUtil.isEmpty(ZZXLXX)&&!StringUtil.isEmpty(ZZXWXX)&&!ZZXLXX.equals(ZZXWXX)){
				String zzxlxx = ZZXLXX+"\r\n"+ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXLXX)){
				String zzxlxx = ZZXLXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else if(!StringUtil.isEmpty(ZZXWXX)){
				String zzxlxx = ZZXWXX;
				dataMap.put("zzxlxx", zzxlxx);
			}else{
				dataMap.put("zzxlxx", "");
			}
			if(a1701 != null){
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
				a1701 = jianli;
				dataMap.put("a1701", a1701);
			}
			if(a01_a0107 != null && !"".equals(a01_a0107)){//格式化出生日期
				a01_a0107 = a01_a0107.substring(0,4)+"."+a01_a0107.substring(4,6);
				dataMap.put("a01_a0107", a01_a0107);
			}
			if(a01_a0144 != null && !"".equals(a01_a0144)){//格式化入党时间
				a01_a0144 = a01_a0144.substring(0,4)+"."+a01_a0144.substring(4,6);
				dataMap.put("a01_a0144", a01_a0144);
			}
			if(a01_a0134 != null && !"".equals(a01_a0134)){
				a01_a0134 = a01_a0134.substring(0,4)+"."+a01_a0134.substring(4,6);
				dataMap.put("a01_a0134", a01_a0134);
			}
			//姓名2个字加空格
			if(a0101 != null){
				if(a0101.length() == 2){
					StringBuilder sb = new StringBuilder();
				    int length = a0101.length();
				    for (int i1 = 0; i1 < length; i1++) {
				        if (length - i1 <= 2) {      //防止ArrayIndexOutOfBoundsException
				            sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				            sb.append(a0101.substring(i1 + 1));
				            break;
				        }
				        sb.append(a0101.substring(i1, i1 + 1)).append("  ");
				    }
				    dataMap.put("a0101", sb.toString());
				}
			}
			//dataList.add(dataMap);
	        return dataMap;
		}
	 
}
