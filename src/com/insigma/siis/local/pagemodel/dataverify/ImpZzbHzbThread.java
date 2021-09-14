package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpZzbHzbThread implements Runnable {
	
	private String uuid;
	private String filename;
	private CurrentUser user;
	private UserVO userVo;
    public ImpZzbHzbThread(String filename, String uuid, CurrentUser user,UserVO userVo) {
        this.uuid = uuid;
        this.filename = filename;
        this.user = user;
        this.userVo = userVo;
    }

	@Override
	public void run() {
		String rootPath = "";									// 项目路径
		String imprecordid = uuid;								// 导入记录id
		String process_run = "1";								// 导入过程序号
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// 业务处理bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// 上传文件整体路径
		String unzip = "";										// 解压路径
		String upload_file = "";
		try {
			// 记录日志文件
			String logfilename = getRootPath() + "upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "开始导入:"+ DateUtil.getTime()+"\n");
			// 001==============处理文件 后缀  格式  上传路径  解压路径==============================================================
			String classPath = getClass().getClassLoader().getResource("/").getPath();				// class 路径
			if ("\\".equals(File.separator)) {														// windows下
				rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("/", "\\");
			}
			if ("/".equals(File.separator)) {														// linux下
				rootPath = classPath.substring(0, classPath.indexOf("WEB-INF/classes"));
				rootPath = rootPath.replace("\\", "/");
			}
			rootPath = URLDecoder.decode(rootPath, "GBK");
			upload_file = rootPath + "upload/" + uuid + "/";									    // 上传路径
			unzip = rootPath + "upload/unzip/" + uuid + "/";										// 解压路径
			File file = new File(unzip);															// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //文件后缀
			filePath = upload_file + "/" + uuid + "." +houzhui;									    //上传文件整体路径
			String from_file = unzip + "Photos/";													//解压后图片存放路径
			File f_file = new File(from_file);
			if (!f_file.exists() && !f_file.isDirectory()) {
				file.mkdirs();
			}
			
			// 002================  文件解压   =========================================================================
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			appendFileContent(logfilename, "解压缩开始:"+ DateUtil.getTime()+"\n");					//记录日志
			if (houzhui.equalsIgnoreCase("hzb")) {
				NewSevenZipUtil.extractilenew(filePath, unzip, "1");
			} else {
				NewSevenZipUtil.extractilenew(filePath, unzip, "2");
			}
			appendFileContent(logfilename, "解压缩结束:"+ DateUtil.getTime()+"\n");					//记录日志
			KingbsconfigBS.saveImpDetail("1","2","完成",imprecordid);								//记录导入过程
			process_run = "2";																		//导入过程
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			
			//002================  解析头文件   =========================================================================
			KingbsconfigBS.saveImpDetail(process_run,"1","处理中",imprecordid);						//记录导入过程
			List<Map<String, String>> headlist = Dom4jUtil
					.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			List<B01> detps = null;
			String B0111 = headlist.get(0).get("B0111");							// 根节点上级机构id
			String deptid = "";														// 根节点上级机构id
			String impdeptid = "";													//根节点机构id
			detps = HBUtil.getHBSession().createQuery("from B01 t where t.b0101='" + headlist.get(0).get("B0101")
					+ "' and t.b0114='" + headlist.get(0).get("B0114") + "'").list();
			if (detps != null && detps.size() > 0) {
				impdeptid = detps.get(0).getB0111();
				deptid = detps.get(0).getB0121();
			} else {
				throw new Exception("未匹配到根机构！");
			}
			List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}
				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype(houzhui);
				imprecord.setImptype(houzhui.equalsIgnoreCase("hzb")? "1" : "2");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setLinkpsn(headlist.get(0).get("linkpsn"));
				imprecord.setLinktel(headlist.get(0).get("linktel"));
				imprecord.setImprecordid(uuid);
				imprecord.setProcessstatus("1");
				sess.update(imprecord);
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",imprecordid);				//记录导入过程
				process_run = "3";																//导入过程
				int t_n = 0;
				//==========  解析单个文件，倒入数据库   =================================================================================
				int number1 = 1;																//已解析表的树木
				int number2 = 0;
				if(houzhui.equalsIgnoreCase("hzb")){
					number2 = 20;																//未解析标的树木
				}else{
					number2 = 15;
				}
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A02数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//NO.006.002 导入A02表
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A02数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A02", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				
				appendFileContent(logfilename, "A02数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A06数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//NO.006.003 导入A06表
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A06数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A06", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A06数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A08数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A08数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A08", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A08数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A11数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A11数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A11", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A11数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A14数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A14数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A14", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A14数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A15数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A15数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A15", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A15数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A29数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A29数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A29", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A29数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A30数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A30数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A30", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A30数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A31数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A31数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A31", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A31数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A36数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A36数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A36", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A36数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A37数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A37数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A37", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A37数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A41数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A41数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A41", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A41数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A53数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A53数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A53", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("aa53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			 
				appendFileContent(logfilename, "A53数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A57数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A57数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A57", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
				appendFileContent(logfilename, "A57数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				if(houzhui.equalsIgnoreCase("hzb")){	//汇总版新增表--单独处理
					
					appendFileContent(logfilename, "a60数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
					KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A60数据， 剩余"+(number2--)+"张。",imprecordid);
					CommonQueryBS.systemOut("a60数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A60", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a60数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				 
					appendFileContent(logfilename, "A60数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A61数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A61数据， 剩余"+(number2--)+"张。",imprecordid);
					CommonQueryBS.systemOut("a61数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A61", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a61数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				 
					appendFileContent(logfilename, "A61数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A62数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A62数据， 剩余"+(number2--)+"张。",imprecordid);
					CommonQueryBS.systemOut("a62数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A62", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a62数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				 
					appendFileContent(logfilename, "A62数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A63数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A63数据， 剩余"+(number2--)+"张。",imprecordid);
					CommonQueryBS.systemOut("a63数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A63", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a63数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				 
					appendFileContent(logfilename, "A63数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
					appendFileContent(logfilename, "A64数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					
					
					KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A64数据， 剩余"+(number2--)+"张。",imprecordid);
					CommonQueryBS.systemOut("a64数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A64", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("a64数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
					
					appendFileContent(logfilename, "A64数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					appendFileContent(logfilename, "==============================================="+"\n");
				}
				appendFileContent(logfilename, "B01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				

//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------				
				//NO.006.001 导入B01表
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表B01数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");

				appendFileContent(logfilename, "B01数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "==============================================="+"\n");
				appendFileContent(logfilename, "A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","提取第"+(number1++)+"张表A01数据， 剩余"+(number2--)+"张。",imprecordid);
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				appendFileContent(logfilename, "A01数据导入完成"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				if(houzhui.equalsIgnoreCase("hzb")){
					KingbsconfigBS.saveImpDetail(process_run,"1","提取补充信息。",imprecordid);
					CommonQueryBS.systemOut("补充信息"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "INFO_EXTEND", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("补充信息"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
					t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01_EXT", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("补充信息"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
					
				}
				imprecord.setTotalnumber(t_n + "");
				imprecord.setProcessstatus("2");
				sess.update(imprecord);
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
			appendFileContent(logfilename, "导入完成"+"\n");
			/**/
			KingbsconfigBS.saveImpDetail(process_run, "2", "提取完成", imprecordid);
			try {
				if (houzhui.equalsIgnoreCase("hzb")) {
					new LogUtil("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList(),userVo).start();
				} else {
					new LogUtil("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil().createLog("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
					} else {
						new LogUtil().createLog("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			try {
				this.delFolder(unzip+"Table/");
				this.delFolder(unzip+"gwyinfo.xml");
				this.delFolder(filePath);
				this.delFolder(upload_file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			appendFileContent(logfilename, "删除缓存文件"+ DateUtil.getTime()+"\n");
			CommonQueryBS.systemOut("delete file END---------" +DateUtil.getTime());
		} catch (AppException e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			uploadbs.rollbackImp(imprecordid);
			if(sess != null)
				sess.getTransaction().rollback();
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			uploadbs.rollbackImp(imprecordid);
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
			if(sess != null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}

	}
	
	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
}
