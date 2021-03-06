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
 * ????????Servlet
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
			String tempType = request.getParameter("excelType");//????????
			fileName=request.getParameter("fileName"); //????????
			String cbd_id =(String)request.getParameter("cbd_id");//??????????????
			String cbd_name=(String)request.getParameter("cbd_name");//??????????
			Template tmp=edb.getTemplate(tempType, request.getSession().getServletContext(),"");//????????????
			String flag = request.getParameter("flag");
			Map<String, Object> dataMap=null;
			//request.setAttribute("cbd_name", "cbd_name");
			//????????????????????????????????????????????????????
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
			
			// ????????????????????
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
			 // ????????????????????
	        InputStream fis = new BufferedInputStream(new FileInputStream(outFile));
	        byte[] buffer = new byte[fis.available()];
	        fis.read(buffer);
	        //String word = new String(buffer,"latin1");
	        //buffer = word.replaceAll("_rn_", "_rn_").getBytes("latin1");
	        fis.close();
	        // ????response
	        response.reset();
	        // ????response??Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
	        response.addHeader("Content-Length", "" + outFile.length());
	        OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
	        toClient.close();
	        
		}else if("getZipFile".equals(method)){//????zip????
			
			String filePath = "";
			String newXmlPath = "";
			String cbd_id = request.getParameter("cbd_id");
			String cbd_name = request.getParameter("cbd_name");
			String flag = request.getParameter("flag");//????????????????
			String bj_cbdid = request.getParameter("bj_cbdid");
			String personname = request.getParameter("personname");
			CommonQueryBS.systemOut(personname);
			response.setContentType("text/html;charset=GBK");
			PrintWriter out = response.getWriter();
			if("u".equals(flag)){//??flag????u????????????????????????????????????????
				//????????????????????????????zip??????
				ReportCBDFilePageModel rc = new ReportCBDFilePageModel();
				String zipPath =rc.collectAttachment(cbd_id, cbd_name);
				//????????????????????????????????????????????
				if("noCBDAtta".equals(zipPath)){
					out.println("<script>parent.parent.odin.alert('????????????????????????????????????????????');</script>");
					return;
				}else if("noPersonAtta".equals(zipPath.split("@")[0])){
					out.println("<script>parent.parent.odin.alert('"+zipPath.split("@")[1]+"??????????????????????????????????????????');</script>");
					return ;
				}
				
				
				//????xml????
				String zipPath1 = zipPath.split("@")[2];
				Map<String, String> map = new HashMap<String, String>();
				HBSession sess = HBUtil.getHBSession();
				try {
					//??????????????????
					UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
					List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
					B01 b01 = null;
					if (b01s != null && b01s.size() > 0) {
						b01 = b01s.get(0);
					} else {
						out.println("<script>parent.parent.odin.alert('????????????????????????');</script>");
						return ;
					}
					//????????????????????
					String linkpsn = request.getParameter("linkpsn");
					//????????????????????????
					String linktel = request.getParameter("linktel");
					//??????????????????
					String remark = request.getParameter("remark");
					//????????????
					java.sql.Timestamp now = DateUtil.getTimestamp();
					String time = DateUtil.timeToString(now);
					String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
					//????ftp????????xml????
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
					info.setStypename("??????????");
					info.setTime(time);
					info.setTranstype("up");
					info.setErrortype("??");
					info.setErrorinfo("??");
					info.setCbdStatus("0");//??????????????  0????????
					info.setPersonname(personname);
					
					List<SFileDefine> sfile = new ArrayList<SFileDefine>();
					//????xml??????
					String packageFile = "Pack_" +zipPath.split("@")[3] + ".xml";
						
					SFileDefine sf = new SFileDefine();

					sf.setTime(time);

					//????????????
					String zipfile =zipPath.split("@")[0];
						
					File file0 = new File(zipfile);
					sf.setName(file0.getName());
					sf.setSize(getFileSize(file0));
					sfile.add(sf);
					//????????????
					info.setDatainfo("??????????1??????????"+ zipPath.split("@")[1] + "????");
					
					info.setSfile(sfile);
					
					CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
					//????xml????
					FileUtil.createFile(zipPath1 + "/" + packageFile,
							JXUtil.Object2Xml(info, true), false, "UTF-8");
					
					String oldZip = zipPath.split("@")[2];
					String newZip = zipPath.split("@")[2]+"/"+cbd_name+"_"+time1+".zip";
					//??????????????zip????????
					CommonQueryBS.systemOut("=============????"+oldZip);
					SevenZipUtil.zip7z(oldZip, newZip, null);
					CommonQueryBS.systemOut(oldZip);
					CommonQueryBS.systemOut(newZip);
					CommonQueryBS.systemOut(zipPath.split("@")[0]);
					//??????????????xml????
					
					if(!zipPath.split("@")[0].equals(newZip)){
						File file_oldzip = new File(zipPath.split("@")[0]);
	 					file_oldzip.delete();
					}
					File file_xml = new File(zipPath1 + "/" + packageFile);
					file_xml.delete();
					fileName = newZip;
					//??????????????
					String uuid = UUID.randomUUID().toString();
					//????????
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
				
			}else if("g".equals(flag)){//??flag????g??????????????????????????????????
				
				//????xml????????
				filePath = new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
				//??xml??????????????zip??????????
				String zipPath = filePath.replace("Pack_", "").replace("xml", "zip");
				//??????????
				CBDTools ct = new CBDTools();
				String path = ct.getPath();
				//????xml??zip??????file????
				File xml_file = new File(filePath);
				File zip_file = new File(zipPath);
				//????xml??zip??????????
				newXmlPath = path+"GetZip/"+xml_file.getName();
				//????????????GetZip????????????????????????
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
					// ????xml??zip????
					ReportCBDFilePageModel.copyFile(xml_file, targetxml_file);
					ReportCBDFilePageModel.copyFile(zip_file, targetzip_file);
					//??????????
					xml_file.delete();
					zip_file.delete();
				}
				//????xml??????zip????????????????????xml??????????????zip??????????
				fileName = path+"GetZip/"+zip_file.getName();
				
			}else if("gppf".equals(flag)){//????????  add by lizs 20160905
				//??????????????
				HBSession sess = HBUtil.getHBSession();
				//??????????????
				CBDInfo cbdinfo = null;
				//????????????
				AttachmentInfo atta = null;
				List<AttachmentInfo> atta_list = new ArrayList<AttachmentInfo>();
				//????????????????????????
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
				
				//????????????????????????????????
				String pfFilePath = "";
				//??????????
				CBDTools ct = new CBDTools();
				//??????????????
				String rootpath = ct.getPath();
				pfFilePath = rootpath +"CBDUpload/CBDZip/";
				
				//??????????????????????
				String fileDir = cbd_name+personname.substring(personname.lastIndexOf("_"), personname.lastIndexOf(".")).concat("??????????");
				File file = new File(pfFilePath+fileDir);
				//??????????????????????????????
				if(!file.exists() && !file.isDirectory() ){
					file.mkdirs();
				}
				for(int i = 0;i<atta_list.size();i++){
					//??????????
					String filepath = rootpath + atta_list.get(0).getFilepath();
					File atta_file = new File(filepath);
					String atta_fileName = atta_file.getName();
					File targetFile = new File(pfFilePath+fileDir+"/"+atta_fileName);
					ReportCBDFilePageModel.copyFile(atta_file, targetFile);
					
				}
				
				//??????????????
				String zipFile = pfFilePath+fileDir+".zip";
				//??????????????zip????????
				SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile, null);
				
				//????xml????
				//??????????????????
				UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
				List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
				B01 b01 = null;
				if (b01s != null && b01s.size() > 0) {
					b01 = b01s.get(0);
				}
				//????????????
				java.sql.Timestamp now = DateUtil.getTimestamp();
				String time = DateUtil.timeToString(now);
				String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
				//????ftp????????xml????
				ZwhzPackDefine info = new ZwhzPackDefine();
				info.setId(cbd_id);
				info.setB0101(b01.getB0101());
				info.setB0111(b01.getB0111());
				info.setB0114(b01.getB0114());
				info.setB0194(b01.getB0194());
				//????????????????????
				String linkpsn = request.getParameter("linkpsn");
				//????????????????????????
				String linktel = request.getParameter("linktel");
				//??????????????????
				String remark = request.getParameter("remark");
				info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
						"yyyyMMdd"));
				info.setLinkpsn(linkpsn);
				info.setLinktel(linktel);
				info.setRemark(remark);
				info.setStype("11");
				info.setStypename("??????????");
				info.setTime(time);
				info.setTranstype("down");
				info.setErrortype("??");
				info.setErrorinfo("??");
				info.setCbdStatus("3");//??????????????  0????????
				
				List<SFileDefine> sfile = new ArrayList<SFileDefine>();
				//????xml??????
				String packageFile = "Pack_" +fileDir + ".xml";
				
				SFileDefine sf = new SFileDefine();
				sf.setTime(time);
				File zipFile1 = new File(zipFile);
				sf.setName(zipFile1.getName());
				sf.setSize(ct.getFileSize(zipFile1));
				sfile.add(sf);
				
				//????????????
				info.setDatainfo("????????????????????"+atta_list.size()+"????");
				
				info.setSfile(sfile);
				
				//????xml????
				try {
					FileUtil.createFile(pfFilePath + "/" + packageFile,
							JXUtil.Object2Xml(info, true), false, "UTF-8");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//??????????
				for(int i = 0;i<atta_list.size();i++){
					//??????????
					String filepath = rootpath + atta_list.get(0).getFilepath();
					File atta_file = new File(filepath);
					String atta_fileName = atta_file.getName();
					File targetFile = new File(pfFilePath+fileDir+"/"+atta_fileName);
					targetFile.delete();
				}
				//????????
				File xmlfile = new File(pfFilePath + "/" + packageFile);
				File zipfile = new File(zipFile);
				File txmlfile = new File(pfFilePath + "/" +fileDir+"/"+ packageFile);
				File tzipfile = new File(pfFilePath+fileDir+"/"+fileDir+".zip");
				ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
				ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
				String zipFile2 = pfFilePath+fileDir+"??????????"+".zip";
				SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile2, null);
				txmlfile.delete();
				tzipfile.delete();
				//??????????
				file.delete();
				
				
				ct.changeStatus(cbd_id+"@"+personname+"@3");
				
				String uuid = UUID.randomUUID().toString();
				//????????
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
			//??????????
			outFile = new File(fileName);
			String fileName1 = outFile.getName();
//			response.reset();
//			response.setCharacterEncoding("UTF-8");
//			fileName1 = java.net.URLEncoder.encode(fileName1, "UTF-8");
//			response.setHeader("Content-Disposition", "attachment; filename="
//					+ fileName1);
//			response.setContentLength((int) outFile.length());
//			response.setContentType("application/zip");// ????????????
//			try {
//				FileInputStream fis = new FileInputStream(outFile);
//				BufferedInputStream buff = new BufferedInputStream(fis);
//				byte[] b = new byte[1024];// ????????????????
//				long k = 0;// ??????????????????????????????????
//				OutputStream myout = response.getOutputStream();// ??response????????????????,????????
//				// ????????????
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
				//????????????????xml?????? 1:??????
				CBDTools ct = new CBDTools();
				ct.changeStatus(cbd_id+"@"+newXmlPath+"@1");
				out.println("<script language='javascript'> parent.radow.doEvent('btnsx.onclick');</script>");
			}else{
				out.println("<script language='javascript'> parent.parent.setNextStep('5');</script>");
			}
		}
	}
	
	/**
	 * ??????????size
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
	 * ????????????????????
	 * @throws Exception 
	 * 
	 */
	public void backByGP(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("utf-8");
		HBSession sess = HBUtil.getHBSession();
		//????????????
		String cbd_id = request.getParameter("cbd_id");
		String filePath=new String(request.getParameter("filePath").getBytes("iso-8859-1"),"gbk");
		String cbd_text=new String(request.getParameter("cbd_text").getBytes("iso-8859-1"),"gbk");
		String cbd_name=new String(request.getParameter("cbd_name").getBytes("iso-8859-1"),"gbk");
		String linkpsn=new String(request.getParameter("linkpsn").getBytes("iso-8859-1"),"gbk");
		String linktel=new String(request.getParameter("linktel").getBytes("iso-8859-1"),"gbk");
		String remark=new String(request.getParameter("remark").getBytes("iso-8859-1"),"gbk");
		
		//????????????????????????????????
		String pfFilePath = "";
		//??????????
		CBDTools ct = new CBDTools();
		//??????????????
		String rootpath = ct.getPath();
		pfFilePath = rootpath +"CBDUpload/CBDZip/";
		
		//??????????????????
		String fileDir = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("??????");
		String fileName = cbd_name+filePath.substring(filePath.lastIndexOf("_"), filePath.lastIndexOf(".")).concat("??????????.txt");
		File file = new File(pfFilePath+fileDir);
		File file1 = new File(pfFilePath+fileDir+"/"+fileName);
		//??????????????????????????????
		if(!file.exists() && !file.isDirectory() ){
			file.mkdirs();
		}
		//????????????????????????????
		OutputStreamWriter pw = null;//??????????
		pw = new OutputStreamWriter(new FileOutputStream(file1),"GBK");//??????????????????????????
		pw.write(cbd_text);//????????????????????????????write
		pw.close();//??????
		
		//??????????????
		String zipFile = pfFilePath+fileDir+".zip";
		//??????????????zip????????
		SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile, null);
		
		//????xml????
		
		//??????????????????
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		List<B01> b01s = HBUtil.getHBSession().createQuery(" from B01 where b0111 ='"+user.getOtherinfo()+"' ").list();
		B01 b01 = null;
		if (b01s != null && b01s.size() > 0) {
			b01 = b01s.get(0);
		}
		//????????????
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		//????ftp????????xml????
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
		info.setStypename("??????????");
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("??");
		info.setErrorinfo("??");
		info.setCbdStatus("2");//??????????????  0????????
		
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		//????xml??????
		String packageFile = "Pack_" +fileDir + ".xml";
		
		SFileDefine sf = new SFileDefine();
		sf.setTime(time);
		File zipFile1 = new File(zipFile);
		sf.setName(zipFile1.getName());
		sf.setSize(ct.getFileSize(zipFile1));
		sfile.add(sf);
		
		//????????????
		info.setDatainfo("??????????????????1????");
		
		info.setSfile(sfile);
		
		//????xml????
		FileUtil.createFile(pfFilePath + "/" + packageFile,
				JXUtil.Object2Xml(info, true), false, "UTF-8");
		
		//????????????????
		file1.delete();
		//????????
		File xmlfile = new File(pfFilePath + "/" + packageFile);
		File zipfile = new File(zipFile);
		File txmlfile = new File(pfFilePath + "/" +fileDir+"/"+ packageFile);
		File tzipfile = new File(pfFilePath+fileDir+"/"+fileDir+".zip");
		ReportCBDFilePageModel.copyFile(xmlfile, txmlfile);
		ReportCBDFilePageModel.copyFile(zipfile, tzipfile);
		String zipFile2 = pfFilePath+fileDir+"??????????"+".zip";
		SevenZipUtil.zip7z(pfFilePath+fileDir, zipFile2, null);
		txmlfile.delete();
		tzipfile.delete();
		//??????????????????
		file.delete();
		
		ct.changeStatus(cbd_id+"@"+filePath+"@2");
		
		String uuid = UUID.randomUUID().toString();
		//????????
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
