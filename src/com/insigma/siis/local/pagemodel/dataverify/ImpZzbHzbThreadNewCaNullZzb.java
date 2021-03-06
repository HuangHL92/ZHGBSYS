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
import java.util.Random;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBSZzb;
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
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpZzbHzbThreadNewCaNullZzb implements Runnable {
	
	private String uuid;
	private String filename;
	private String b0111new;
	private String a0165;
	private CurrentUser user;
	private UserVO userVo;
	private String fxz;
    public ImpZzbHzbThreadNewCaNullZzb(String filename, String uuid, String b0111new,String a0165, CurrentUser user,UserVO userVo,String fxz) {
        this.uuid = uuid;
        this.filename = filename;
        this.b0111new = b0111new;
        this.a0165 = a0165;
        this.user = user;
        this.userVo = userVo;
        this.fxz = fxz;
    }
    
	@Override
	public void run() {
		String imprecordid = uuid;								// ????????id
		String process_run = "1";								// ????????????
		UploadHzbFileBSZzb uploadbs = new UploadHzbFileBSZzb();		// ????????bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// ????????????????
		String unzip = "";										// ????????
		String upload_file = "";								//
		String tableExt = "";									//??????
		try {
			// ????????????
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "????????:"+ DateUtil.getTime()+"\n");
			// 001==============???????? ????  ????  ????????  ????????==============================================================
			
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //????????
			
			List<Map<String, String>> headlist = null;
			String from_file = "";
			
			/*if(houzhui.equalsIgnoreCase("zip")){
				upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";									    // ????????
				unzip = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/" + uuid;										// ????????
				File file = new File(unzip);															// ??????????????????????
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				File[] subs = file.listFiles();
				File f = subs[0];
				
				unzip = unzip + "/" + f.getName()  + "/";
				
				filePath = upload_file + uuid + "." +houzhui;									    //????????????????
				from_file = unzip + "Photos/";													//??????????????????
				File f_file = new File(from_file);
				if (!f_file.exists() && !f_file.isDirectory()) {
					f_file.mkdirs();
				}
				tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
				// 002================  ????????   =========================================================================
				CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun??"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "??????????:"+ DateUtil.getTime()+"\n");					//????????
				
				appendFileContent(logfilename, "??????????:"+ DateUtil.getTime()+"\n");					//????????
				KingbsconfigBS.saveImpDetail("1","2","????",imprecordid);								//????????????
				process_run = "2";																		//????????
				CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun??"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//002================  ??????????   =========================================================================
				KingbsconfigBS.saveImpDetail(process_run,"1","??????",imprecordid);						//????????????
				headlist = Dom4jUtil.gwyinfoF(unzip + "gwyinfo.xml");
			}else{*/
				upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";									    // ????????
				unzip = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid + "/";										// ????????
				File file = new File(unzip);															// ??????????????????????
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				
				filePath = upload_file + uuid + "." +houzhui;									    //????????????????
				from_file = unzip + "Photos/";													//??????????????????
				File f_file = new File(from_file);
				if (!f_file.exists() && !f_file.isDirectory()) {
					f_file.mkdirs();
				}
				tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
				// 002================  ????????   =========================================================================
				CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun??"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "??????????:"+ DateUtil.getTime()+"\n");					//????????
				
				if (houzhui.equalsIgnoreCase("hzb")) {
					NewSevenZipUtil.unzip7zAll(filePath, unzip, "1");
				} else if(houzhui.equalsIgnoreCase("7z")){
					Zip7z.unzip7zAll(filePath, unzip, "20171020");
				} else if(houzhui.equalsIgnoreCase("zip")){
					Zip7z.unzip7zAll(filePath, unzip, null);
				} else {
					NewSevenZipUtil.unzip7zAll(filePath, unzip, "2");
				}
				appendFileContent(logfilename, "??????????:"+ DateUtil.getTime()+"\n");					//????????
				KingbsconfigBS.saveImpDetail("1","2","????",imprecordid);								//????????????
				process_run = "2";																		//????????
				CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun??"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				//002================  ??????????   =========================================================================
				KingbsconfigBS.saveImpDetail(process_run,"1","??????",imprecordid);						//????????????
				headlist = Dom4jUtil.gwyinfoF(unzip + "" + "gwyinfo.xml");
			/*}*/
			List<B01> grps = HBUtil.getHBSession().createQuery("from B01 t where t.b0111 in(select b0111 from UserDept where userid='"+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			List<B01> detps = null;
			String B0111 = headlist.get(0).get("B0111");							// ??????????????id
			String deptid = "";														// ??????????????id
			String impdeptid = "";													//??????????id
			detps = HBUtil.getHBSession().createQuery("from B01 t where t.b0111='" + b0111new + "'").list();
			if (detps != null && detps.size() > 0) {
				impdeptid = detps.get(0).getB0111();
				deptid = detps.get(0).getB0121();
			} else if (b0111new.equals("001.001")) {
				impdeptid = b0111new;
				deptid = "-1";
			} else {
				throw new Exception("????????????????");
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
				if(houzhui.equalsIgnoreCase("zip")){
					imprecord.setLinkpsn(headlist.get(0).get("contact"));
					imprecord.setLinktel(headlist.get(0).get("tel"));
				}else{
					imprecord.setLinkpsn(headlist.get(0).get("linkpsn"));
					imprecord.setLinktel(headlist.get(0).get("linktel"));
				}
				imprecord.setImprecordid(uuid);
				imprecord.setImptemptable(tableExt);
				imprecord.setProcessstatus("1");
				imprecord.setA0165(a0165);
				sess.update(imprecord);
				appendFileContent(logfilename, "??????????:"+ DateUtil.getTime()+"\n");					//????????
				if("7z".equalsIgnoreCase(houzhui)||"zip".equalsIgnoreCase(houzhui)){
					uploadbs.createTempTableTYGS(tableExt);
				}else{
					uploadbs.createTempTable(tableExt);
				}
				appendFileContent(logfilename, "??????????wabcheng:"+ DateUtil.getTime()+"\n");					//????????
				KingbsconfigBS.saveImpDetail(process_run,"2","????",imprecordid);				//????????????
				ImpSynControl control = new ImpSynControl();
				ImpZzbPart1ThreadZzb t1 = new ImpZzbPart1ThreadZzb(userVo, houzhui, imprecordid, uuid, from_file, B0111, deptid, impdeptid, logfilename, control,"1",fxz);
				ImpZzbPart1ThreadZzb t2 = new ImpZzbPart1ThreadZzb(userVo, houzhui, imprecordid, uuid, from_file, B0111, deptid, impdeptid, logfilename, control,"2",fxz);
				control.setThd1(new Thread(t1, "ImpZzbPart_1_" + imprecordid));
				control.setThd2(new Thread(t2, "ImpZzbPart_2_" + imprecordid));
				control.setFilePath(filePath);
				control.setUnzip(unzip);
				control.setTableExt(tableExt);
				control.setUpload_file(upload_file);
				if(houzhui.equals("hzb")){
					control.setNumber2(14);
				}
				if(houzhui.equals("7z")||houzhui.equals("zip")){
					control.setNumber2(11);
				}
				control.start();
			
			}
			
		} catch (AppException e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","??????"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//?????????????? 
			uploadbs.rollbackImpTable(imprecordid, tableExt);
			if(sess != null)
				sess.getTransaction().rollback();
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","????:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//?????????????? 
			//uploadbs.rollbackImp(imprecordid);
			uploadbs.rollbackImpTable(imprecordid, tableExt);
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
			if(sess != null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
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
            //????????????????????????????????????????true????????????????????
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	// ??????????
	// param folderPath ??????????????????
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ??????????????????
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ????????????
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ????????????????????????
	// param path ??????????????????
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
				delAllFile(path + "/" + tempList[i]);// ??????????????????????
				delFolder(path + "/" + tempList[i]);// ??????????????
				flag = true;
			}
		}
		return flag;
	}
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
	}
}
