package com.insigma.siis.local.pagemodel.cbdHandler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.hibernate.Transaction;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.AttachmentInfo;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilder;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;

/**
 * 附件下载Servlet
 * @author lixy
 *
 */
public class CBDFiledownServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String method = request.getParameter("method");
		if("backByGP".equals(method)){
			try {
				backByGP(request,response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ;
		}
		String fileName = "";
		File outFile = null;
		request.setCharacterEncoding("utf-8");
		if(!"getZipFile".equals(method)){
			HBSession sess = HBUtil.getHBSession();
			ExportDataBuilder edb=new ExportDataBuilder();
			String tempType = request.getParameter("excelType");//模板类型
			fileName=request.getParameter("fileName"); //文件路径
			String cbd_id =(String)request.getParameter("cbd_id");//呈报单记录主键
			String cbd_name=(String)request.getParameter("cbd_name");//呈报单名称
			Template tmp=edb.getTemplate(tempType, request.getSession().getServletContext(),"");//获取导出模板
			String flag = request.getParameter("flag");
			Map<String, Object> dataMap=null;
			//request.setAttribute("cbd_name", "cbd_name");
			//当为上报呈报单时，根据本机呈报单查询上报呈报单的编号
			if("11".equals(tempType)){
				List<CBDInfo> list = sess.createQuery("from CBDInfo where objectno = '"+cbd_id.split("@")[0]+"'").list();
				if(list.size()>0){
					cbd_id = list.get(0).getCbd_id();
				}
			}
			if(cbd_id==null||"".equals(cbd_id)){
				request.setAttribute("cbd_id", "");
				dataMap=edb.getTempData( tempType,request);
			}else{
				request.setAttribute("cbd_id", cbd_id);
				dataMap=edb.getTempData( tempType,request);
			}
			if("g".equals(flag)){
				dataMap.put("cbd_name", cbd_name);
			}
			
			// 以流的形式下载文件。
			outFile = new File(fileName);
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
	        
		}else if("getZipFile".equals(method)){//接收zip文件
			
			String filePath = "";
			String newXmlPath = "";
			String cbd_id = request.getParameter("cbd_id");
			String cbd_name = request.getParameter("cbd_name");
			String flag = request.getParameter("flag");//获取操作类型标志
			String bj_cbdid = request.getParameter("bj_cbdid");
			String personname = request.getParameter("personname");
			CommonQueryBS.systemOut(personname);
			response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			if("u".equals(flag)){//当flag值为u时，执行上报呈报单页面的导出数据包的操作
				//整理呈报单下面的附件，压缩成zip压缩包
				ReportCBDFilePageModel rc = new ReportCBDFilePageModel();
				String zipPath =rc.collectAttachment(cbd_id, cbd_name);
				//判断附件情况，如果附件不足，不可以上报呈报单
				if("noCBDAtta".equals(zipPath)){
					out.println("<script>parent.parent.odin.alert('还没有上传呈报单附件，不能下载呈报单数据包！');</script>");
					return;
				}else if("noPersonAtta".equals(zipPath.split("@")[0])){
					out.println("<script>parent.parent.odin.alert('"+zipPath.split("@")[1]+"还没有上传任何附件，不能下载呈报单数据包！');</script>");
					return ;
				}
				
				
				//组装xml文件
				String zipPath1 = zipPath.split("@")[2];
				Map<String, String> map = new HashMap<String, String>();
				HBSession sess = HBUtil.getHBSession();
				try {
					//获取登录用户的信息
					UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
					List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
					B01 b01 = null;
					if (b01s != null && b01s.size() > 0) {
						b01 = b01s.get(0);
					} else {
						out.println("<script>parent.parent.odin.alert('系统出错，请联系管理员！');</script>");
						return ;
					}
					//获取页面中联系人信息
					String linkpsn = request.getParameter("linkpsn");
					//获取页面中联系人电话信息
					String linktel = request.getParameter("linktel");
					//获取页面中备注信息
					String remark = request.getParameter("remark");
					//定义时间变量
					java.sql.Timestamp now = DateUtil.getTimestamp();
					String time = DateUtil.timeToString(now);
					String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
					//创建ftp上报信息xml对象
					ZwhzPackDefine info = new ZwhzPackDefine();
					
					info.setId(cbd_id);
					info.setB0101(b01.getB0101());
					info.setB0111(b01.getB0111());
					info.setB0114(b01.getB0114());
					info.setB0194(b01.getB0194());

					info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),"yyyyMMdd"));
					info.setLinkpsn(linkpsn);
					info.setLinktel(linktel);
					info.setRemark(remark);
					info.setStype("11");
					info.setStypename("呈报单上报");
					info.setTime(time);
					info.setTranstype("up");
					info.setErrortype("无");
					info.setErrorinfo("无");
					info.setCbdStatus("0");//设置呈报单状态  0：未接收
					info.setPersonname(personname);
					
					List<SFileDefine> sfile = new ArrayList<SFileDefine>();
					//定义xml文件名
					String packageFile = "Pack_" +zipPath.split("@")[3] + ".xml";
						
					SFileDefine sf = new SFileDefine();

					sf.setTime(time);

					//要上报的文件
					String zipfile =zipPath.split("@")[0];
						
					File file0 = new File(zipfile);
					sf.setName(file0.getName());
					sf.setSize(getFileSize(file0));
					sfile.add(sf);
					//记录文件信息
					info.setDatainfo("呈报单附件1个，文件夹"+ zipPath.split("@")[1] + "个。");
					
					info.setSfile(sfile);
					
					CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
					//创建xml文件
					FileUtil.createFile(zipPath1 + "/" + packageFile,
							JXUtil.Object2Xml(info, true), false, "UTF-8");
					
					String oldZip = zipPath.split("@")[2];
					String newZip = zipPath.split("@")[2]+"/"+cbd_name+"_"+time1+".zip";
					//将文件夹压缩（zip压缩包）
					CommonQueryBS.systemOut("=============开始"+oldZip);
					SevenZipUtil.zip7z(oldZip, newZip, null);
					CommonQueryBS.systemOut(oldZip);
					CommonQueryBS.systemOut(newZip);
					CommonQueryBS.systemOut(zipPath.split("@")[0]);
					//删除原压缩包和xml文件
					
					if(!zipPath.split("@")[0].equals(newZip)){
						File file_oldzip = new File(zipPath.split("@")[0]);
	 					file_oldzip.delete();
					}
					File file_xml = new File(zipPath1 + "/" + packageFile);
					file_xml.delete();
					fileName = newZip;
					//记录呈报单步骤
					String uuid = UUID.randomUUID().toString();
					//获取时间
					SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
					String CBD_STATUS_TIME = sdf.format(new Date());
					Cbdstatus cbdstatus = new Cbdstatus();
					cbdstatus.setStatusid(uuid);
					cbdstatus.setCbdstatusstep("5");
					cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
					cbdstatus.setCbdid(bj_cbdid);
					sess.beginTransaction();
					sess.save(cbdstatus);
					sess.getTransaction().commit();
					out.println("<script language='javascript'>parent.radow.doEvent('reload');</script>");
				} catch (Exception e) {
					e.printStackTrace();
					sess.getTransaction().rollback();

				}
				
			}else if("g".equals(flag)){//当flag值为g时，表示的是接收页面的接收下载操作
				
				//获取xml文件路径
				filePath = new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
				//由xml文件的路径获取zip文件的路径
				String zipPath = filePath.replace("Pack_", "").replace("xml", "zip");
				//获取根路径
				CBDTools ct = new CBDTools();
				String path = ct.getPath();
				//创建xml和zip文件的file对象
				File xml_file = new File(filePath);
				File zip_file = new File(zipPath);
				//创建xml和zip的目标对象
				newXmlPath = path+"GetZip/"+xml_file.getName();
				//检查是否存在GetZip文件夹，如果不存在则创建
				File zip_path = new File(path+"GetZip");
				if(!zip_path.exists() && !zip_path.isDirectory()){
					zip_path.mkdir();
				}
				File targetxml_file = new File(newXmlPath);
				boolean a = targetxml_file.equals(xml_file);
//				System.out.println(targetxml_file.getPath());
//				System.out.println(xml_file.getPath());
//				System.out.println(a);
				if(!a){
					
					File targetzip_file = new File(path+"GetZip/"+zip_file.getName());
					// 复制xml和zip文件
					ReportCBDFilePageModel.copyFile(xml_file, targetxml_file);
					ReportCBDFilePageModel.copyFile(zip_file, targetzip_file);
					//删除原文件
					xml_file.delete();
					zip_file.delete();
				}
				//由于xml文件和zip文件名称中间相同，将xml文件名称修改为zip文件的名称
				fileName = path+"GetZip/"+zip_file.getName();
				
			}else if("gppf".equals(flag)){//光盘批复  add by lizs 20160905
				//创建数据库对象
				HBSession sess = HBUtil.getHBSession();
				//创建呈报单对象
				CBDInfo cbdinfo = null;
				//创建附件对象
				AttachmentInfo atta = null;
				List<AttachmentInfo> atta_list = new ArrayList<AttachmentInfo>();
				//查询呈报单信息和附件信息
				List list= sess.createQuery("from CBDInfo where cbd_id = '"+cbd_id+"'").list();
				if(list.size()>0){
					cbdinfo = (CBDInfo) list.get(0);
				}
				if(cbdinfo != null){
					
					List list_atta = sess.createQuery("from AttachmentInfo where objectid = '"+cbdinfo.getCbd_id()+"'").list();
					if(list_atta.size()>0 ){
						atta = (AttachmentInfo) list_atta.get(0);
						atta_list.add(atta);
					}
				}
				
				//定义变量：存放批复文件包存放路径
				String pfFilePath = "";
				//创建工具类
				CBDTools ct = new CBDTools();
				//获取系统根目录
				String rootpath = ct.getPath();
				pfFilePath = rootpath +"CBDUpload/CBDZip/";
				
				//组装批复文件存放文件夹
				String fileDir = cbd_name+personname.substring(personname.lastIndexOf("_"), personname.lastIndexOf(".")).concat("的批复反馈");
				File file = new File(pfFilePath+fileDir);
				//如果意见文件不存在，创建该文件
				if(!file.exists() && !file.isDirectory() ){
					file.mkdirs();
				}
				for(int i = 0;i<atta_list.size();i++){
					//附件原路径
					String filepath = rootpath + atta_list.get(0).getFilepath();
					File atta_file = new File(filepath);
					String atta_fileName = atta_file.getName();
					File targetFile = new File(pfFilePath+fileDir+"/"+atta_fileName);
					ReportCBDFilePageModel.copyFile(atta_file, targetFile);
					
				}
				
				//定义压缩文件名
				String zipFile = pfFilePath+fileDir+".zip";
				//将文件夹压缩（zip压缩包）
				SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile, null);
				
				//组装xml文件
				//获取登录用户的信息
				UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
				List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
				B01 b01 = null;
				if (b01s != null && b01s.size() > 0) {
					b01 = b01s.get(0);
				}
				//定义时间变量
				java.sql.Timestamp now = DateUtil.getTimestamp();
				String time = DateUtil.timeToString(now);
				String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
				//创建ftp上报信息xml对象
				ZwhzPackDefine info = new ZwhzPackDefine();
				info.setId(cbd_id);
				info.setB0101(b01.getB0101());
				info.setB0111(b01.getB0111());
				info.setB0114(b01.getB0114());
				info.setB0194(b01.getB0194());
				//获取页面中联系人信息
				String linkpsn = request.getParameter("linkpsn");
				//获取页面中联系人电话信息
				String linktel = request.getParameter("linktel");
				//获取页面中备注信息
				String remark = request.getParameter("remark");
				info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
						"yyyyMMdd"));
				info.setLinkpsn(linkpsn);
				info.setLinktel(linktel);
				info.setRemark(remark);
				info.setStype("11");
				info.setStypename("呈报单上报");
				info.setTime(time);
				info.setTranstype("down");
				info.setErrortype("无");
				info.setErrorinfo("无");
				info.setCbdStatus("3");//设置呈报单状态  0：未接收
				
				List<SFileDefine> sfile = new ArrayList<SFileDefine>();
				//定义xml文件名
				String packageFile = "Pack_" +fileDir + ".xml";
				
				SFileDefine sf = new SFileDefine();
				sf.setTime(time);
				File zipFile1 = new File(zipFile);
				sf.setName(zipFile1.getName());
				sf.setSize(ct.getFileSize(zipFile1));
				sfile.add(sf);
				
				//记录文件信息
				info.setDatainfo("本级呈报单扫描件文件"+atta_list.size()+"个。");
				
				info.setSfile(sfile);
				
				//创建xml文件
				try {
					FileUtil.createFile(pfFilePath + "/" + packageFile,
							JXUtil.Object2Xml(info, true), false, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//删除原文件
				for(int i = 0;i<atta_list.size();i++){
					//附件原路径
					String filepath = rootpath + atta_list.get(0).getFilepath();
					File atta_file = new File(filepath);
					String atta_fileName = atta_file.getName();
					File targetFile = new File(pfFilePath+fileDir+"/"+atta_fileName);
					targetFile.delete();
				}
				//删除文件
				File xmlfile = new File(pfFilePath + "/" + packageFile);
				File zipfile = new File(zipFile);
				File txmlfile = new File(pfFilePath + "/" +fileDir+"/"+ packageFile);
				File tzipfile = new File(pfFilePath+fileDir+"/"+fileDir+".zip");
				ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
				ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
				String zipFile2 = pfFilePath+fileDir+"下载数据包"+".zip";
				SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile2, null);
				txmlfile.delete();
				tzipfile.delete();
				//删除文件夹
				file.delete();
				
				
				ct.changeStatus(cbd_id+"@"+personname+"@3");
				
				String uuid = UUID.randomUUID().toString();
				//获取时间
				SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
				String CBD_STATUS_TIME = sdf.format(new Date());
				Cbdstatus cbdstatus = new Cbdstatus();
				cbdstatus.setStatusid(uuid);
				cbdstatus.setCbdstatusstep("3");
				cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
				cbdstatus.setCbdid(cbd_id);
				Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
				sess.save(cbdstatus);
				ts.commit();
				fileName = zipFile2;
			}
			//创建源文件
			outFile = new File(fileName);
			String fileName1 = outFile.getName();
//			response.reset();
//			response.setCharacterEncoding("UTF-8");
//			fileName1 = java.net.URLEncoder.encode(fileName1, "UTF-8");
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ fileName1);
//			response.setContentLength((int) outFile.length());
//			response.setContentType("application/zip");// 定义输出类型
//			try {
//				FileInputStream fis = new FileInputStream(outFile);
//				BufferedInputStream buff = new BufferedInputStream(fis);
//				byte[] b = new byte[1024];// 相当于我们的缓存
//				long k = 0;// 该值用于计算当前实际下载了多少字节
//				OutputStream myout = response.getOutputStream();// 从response对象中得到输出流,准备下载
//				// 开始循环下载
//				while (k < outFile.length()) {
//					int j = buff.read(b, 0, 1024);
//					k += j;
//					myout.write(b, 0, j);
//				}
//				myout.flush();
//				myout.close();
//			} catch (Exception e) {
//				System.out.println(e);
//			}
			out.println("<script language='javascript'> var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(fileName.replace("\\", "/"),"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}</script>");
			if("g".equals(flag)){
				//接收成功后，修改xml的状态 1:已接收
				CBDTools ct = new CBDTools();
				ct.changeStatus(cbd_id+"@"+newXmlPath+"@1");
				out.println("<script language='javascript'> parent.radow.doEvent('btnsx.onclick');</script>");
			}else{
				out.println("<script language='javascript'> parent.parent.setNextStep('5');</script>");
			}
		}
	}
	
	/**
	 * 获取文件的size
	 * @param f
	 * @return
	 */
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
	
	/**
	 * 通过光盘进行打回操作
	 * @throws Exception 
	 * 
	 */
	public void backByGP(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		HBSession sess = HBUtil.getHBSession();
		//获取页面参数
		String cbd_id = request.getParameter("cbd_id");
		String filePath=new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
		String cbd_text=new String(request.getParameter("cbd_text").getBytes("iso-8859-1"),"gbk");
		String cbd_name=new String(request.getParameter("cbd_name").getBytes("iso-8859-1"),"gbk");
		String linkpsn=new String(request.getParameter("linkpsn").getBytes("iso-8859-1"),"gbk");
		String linktel=new String(request.getParameter("linktel").getBytes("iso-8859-1"),"gbk");
		String remark=new String(request.getParameter("remark").getBytes("iso-8859-1"),"gbk");
		
		//定义变量：存放批复文件包存放路径
		String pfFilePath = "";
		//创建工具类
		CBDTools ct = new CBDTools();
		//获取系统根目录
		String rootpath = ct.getPath();
		pfFilePath = rootpath +"CBDUpload/CBDZip/";
		
		//组装打回意见文件夹
		String fileDir = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("的反馈");
		String fileName = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("的打回意见.txt");
		File file = new File(pfFilePath+fileDir);
		File file1 = new File(pfFilePath+fileDir+"/"+fileName);
		//如果意见文件不存在，创建该文件
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		//将页面中的意见内容写到文件中
		OutputStreamWriter pw = null;//定义一个流
		pw = new OutputStreamWriter(new FileOutputStream(file1),"GBK");//确认流的输出文件和编码格式
		pw.write(cbd_text);//将要写入文件的内容，可以多次write
		pw.close();//关闭流
		
		//定义压缩文件名
		String zipFile = pfFilePath+fileDir+".zip";
		//将文件夹压缩（zip压缩包）
		SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile, null);
		
		//组装xml文件
		
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
		B01 b01 = null;
		if (b01s != null && b01s.size() > 0) {
			b01 = b01s.get(0);
		}
		//定义时间变量
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		//创建ftp上报信息xml对象
		ZwhzPackDefine info = new ZwhzPackDefine();
		info.setId(cbd_id);
		info.setB0101(b01.getB0101());
		info.setB0111(b01.getB0111());
		info.setB0114(b01.getB0114());
		info.setB0194(b01.getB0194());

		info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
				"yyyyMMdd"));
		info.setLinkpsn(linkpsn);
		info.setLinktel(linktel);
		info.setRemark(remark);
		info.setStype("11");
		info.setStypename("呈报单上报");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("无");
		info.setErrorinfo("无");
		info.setCbdStatus("2");//设置呈报单状态  0：未接收
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//定义xml文件名
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//记录文件信息
		info.setDatainfo("呈报单打回意见文件1个。");
		
		info.setSfile(sfile);
		
		//创建xml文件
		FileUtil.createFile(pfFilePath + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//删除打回意见文件
		file1.delete();
		//删除文件
		File xmlfile = new File(pfFilePath + "/" + packageFile);
		File zipfile = new File(zipFile);
		File txmlfile = new File(pfFilePath + "/" +fileDir+"/"+ packageFile);
		File tzipfile = new File(pfFilePath+fileDir+"/"+fileDir+".zip");
		ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
		ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
		String zipFile2 = pfFilePath+fileDir+"下载数据包"+".zip";
		SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile2, null);
		txmlfile.delete();
		tzipfile.delete();
		//删除打回意见文件夹
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@2");
		
		String uuid = UUID.randomUUID().toString();
		//获取时间
		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String CBD_STATUS_TIME = sdf.format(new Date());
		Cbdstatus cbdstatus = new Cbdstatus();
		cbdstatus.setStatusid(uuid);
		cbdstatus.setCbdstatusstep("2");
		cbdstatus.setCbdstatustime(CBD_STATUS_TIME);
		cbdstatus.setCbdid(cbd_id);
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		sess.save(cbdstatus);
		ts.commit();
		out.println("<script language='javascript'> var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(zipFile2.replace("\\", "/"),"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}</script>");
		out.println("<script language='javascript'> parent.parent.location.reload();</script>");
		out.println("<script language='javascript'> parent.parent.parent.setNextStep('5');</script>");
	};
}
