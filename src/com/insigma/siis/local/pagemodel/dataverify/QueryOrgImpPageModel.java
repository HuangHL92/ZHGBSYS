package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hsqldb.lib.StringUtil;

import com.fr.data.core.DataUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.ImpProcess;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.zhgbDatav.ImpmodelThreadZHGB;

public class QueryOrgImpPageModel extends PageModel {
	
	@PageEvent("uploadbtn.onclick")
	@NoRequiredValidate
	public int grantbtnOnclick()throws RadowException{
		this.openWindow("simpleExpWin", "pages.dataverify.DataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 重置查询条件
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("reset.onclick")
	@NoRequiredValidate           
	@OpLog
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("createtimesta").setValue("");
		this.getPageElement("createtimeend").setValue("");
//		this.getPageElement("gsearch").setValue("");
//		this.getPageElement("comboxArea_gsearch").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询 -点击查询按钮，进行查询
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query.onclick")
	@NoRequiredValidate           
	@OpLog
	public int queryonclick()throws RadowException, AppException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("MGrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String userid = SysUtil.getCacheCurrentUser().getId();
//		Object deptid = HBUtil.getHBSession().createSQLQuery("SELECT a.b0111 FROM COMPETENCE_USERDEPT a WHERE a.userid='"+userid+"'").uniqueResult();
		String to_format = DBUtil.getDBType().equals(DBType.MYSQL)? "DATE_FORMAT(imp_time,'%Y%m%d %T')": "to_char(imp_time,'yyyymmdd hh24:mi:ss')";
		StringBuffer sql = new StringBuffer("select file_name filename,"+to_format+" imptime,emp_dept_name empgroupname," +
				"is_virety isvirety, wrong_number wrongnumber,total_number totalnumber,imp_record_id imprecordid,imp_stutas impstutas " +
				"from Imp_record where imp_user_id ='"+userid+"' and imp_type in('2','1','4') and PROCESS_STATUS = '2' " );
		String st = this.getPageElement("createtimesta").getValue();
		String et = this.getPageElement("createtimeend").getValue();
		String impstutas = this.getPageElement("impstutas1").getValue();
		
		if(impstutas != null && !impstutas.equals("0")){
			sql.append(" and imp_stutas='"+ impstutas +"' ");
		}
		if(st != null && !st.equals("")){
			sql.append(" and "+to_format+" >='" +st+ "'");
		}
		if(et != null && !et.equals("")){
			sql.append(" and "+to_format+" <='" +et+ "'");
		}
		sql.append(" order by imp_time desc,imp_stutas asc");
		CommonQueryBS.systemOut(sql.toString());
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 打开校验窗口
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String imprecordid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			Imprecord imd = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String type = imd.getImpstutas();
			if(type!=null && type.equals("3")){
				this.setMainMessage("数据已拒绝，不能校验！");
			} else if(type!=null && type.equals("4")){
				this.setMainMessage("数据接收中，不能校验，请点击‘详情’查看进度！");
			} else if(type!=null && type.equals("2")){
				this.setMainMessage("数据已接收，不能校验！");
			} else {
//				this.setRadow_parent_data("2@"+imprecordid);
//				this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
				this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','校验窗口',700,599, '','/hzb',null,{ids:'2@"+imprecordid+"',maximizable:false,resizable:false});");
			}
		} catch (Exception e) {
			this.setMainMessage("数据异常。");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@Override
	public int doInit() throws RadowException {
		
		HBSession sess = HBUtil.getHBSession();
		
		//按机构导入数据(HZB1.0)按钮，是否显示控制
		String import_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'IMPORT_ISUSEFUL'").uniqueResult().toString();
		
		if(import_isuseful != null && import_isuseful.equals("ON")){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('uploadOldbtn').show();");
		}else{
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('uploadOldbtn').hide();");
		}
		
		//按机构导入数据(套改版)按钮，是否显示控制
		String import_isusefulTg = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'IMPORT_ISUSEFULTG'").uniqueResult().toString();
		
		if(import_isusefulTg != null && import_isusefulTg.equals("ON")){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('uploadMakebtn').show();");
			
		}else{
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('uploadMakebtn').hide();");
		}
		
		
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询 -点击查询按钮，进行查询
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("query2.onclick")
	@NoRequiredValidate           
	public int queryonclick2()throws RadowException, AppException {
		CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
		String uuid = "1234567890";
		String rootPath = "";									// 项目路径
		String imprecordid = "";								// 导入记录id
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// 业务处理bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// 上传文件整体路径
		String unzip = "";										// 解压路径
		try {
			// 记录日志文件
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
			String upload_file = rootPath + "upload/" + uuid + "/";									// 上传路径
			unzip = rootPath + "upload/unzip/" + uuid + "/";										// 解压路径
			File file = new File(unzip);															// 如果文件夹不存在则创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdirs();
			}
			String houzhui = "zb3";  //文件后缀
			filePath = upload_file + "/" + uuid + "." +houzhui;									    //上传文件整体路径
			String from_file = unzip + "Photos/";													//解压后图片存放路径
			
			// 002================  文件解压   =========================================================================
			CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				NewSevenZipUtil.extractilenew(filePath, unzip, "1");
//			} else {
//				NewSevenZipUtil.extractilenew(filePath, unzip, "2");
//			}
			CommonQueryBS.systemOut("END UPZIP---------" +DateUtil.getTime()+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
			
			//002================  解析头文件   =========================================================================
			List<Map<String, String>> headlist = Dom4jUtil.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"+ user.getId() + "')").list();
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
				imprecord.setFilename("xxxxxxx.zb3");
				imprecord.setFiletype(houzhui);
				imprecord.setImptype(houzhui.equalsIgnoreCase("hzb")? "1" : "2");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setLinkpsn(headlist.get(0).get("linkpsn"));
				imprecord.setLinktel(headlist.get(0).get("linktel"));
				
				sess.save(imprecord);
				imprecordid = imprecord.getImprecordid();
				int t_n = 0;
				//==========  解析单个文件，倒入数据库   =================================================================================
				int number1 = 1;																//已解析表的树木
				int number2 = 15;																//未解析标的树木
				
				
				//NO.006.002 导入A02表
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A02", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.002 A02数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
				
				
				//NO.006.003 导入A06表
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A06", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a06数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A08", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a08数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A11", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a11数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A14", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a14数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A15", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a15数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A29", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a29数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A30", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a30数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A31", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a31数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A36", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a36数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A37", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a37数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A41", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a41数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				CommonQueryBS.systemOut("a53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A53", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("aa53数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			 
				
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A57", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a57数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				//NO.006.001 导入B01表
				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("NO.006.001 B01数据导入"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");

				
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据开始");
				t_n = uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "A01", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
				CommonQueryBS.systemOut("a01数据"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>导入数据结束");
			
				
				imprecord.setTotalnumber(t_n + "");
				sess.update(imprecord);
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
//			if (houzhui.equalsIgnoreCase("hzb")) {
//				new LogUtil().createLog("421", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
//			} else {
//				new LogUtil().createLog("422", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
//			}
//			this.delFolder(unzip);
//			this.delFolder(filePath);
			CommonQueryBS.systemOut("delete file END---------" +DateUtil.getTime());
		} catch (AppException e) {
			e.printStackTrace();
			try {
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			uploadbs.rollbackImp(imprecordid);
//			if(sess != null)
//				sess.getTransaction().rollback();
//			this.delFolder(unzip);
//			this.delFolder(filePath);
		} catch (Exception e) {
			e.printStackTrace();
			try {
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			uploadbs.rollbackImp(imprecordid);
//			this.delFolder(unzip);
//			this.delFolder(filePath);
//			if(sess != null)
//				sess.getTransaction().rollback();
			e.printStackTrace();
		}
		return 0;
	}
	
	@PageEvent("phototrans1")
	@NoRequiredValidate
	public int phototrans1()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			List list = sess.createQuery(" from ImpProcess where imprecordid='PhotoTrans'").list();
			if(list == null || list.size()<1){
				ImpProcess pro = new ImpProcess();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setImprecordid("PhotoTrans");
				pro.setProcessinfo("图片处理");
				pro.setProcessname("处理中");
				pro.setProcesstype("1");
				pro.setProcessstatus("1");
				sess.save(pro);
				sess.flush();
				PhotoTransThread thread = new PhotoTransThread("PhotoTrans");
				new Thread(thread).start();
			}
			this.openWindow("photoInfoWin", "pages.dataverify.PhotoInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("phototrans2")
	@NoRequiredValidate
	public int phototrans2()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.createQuery("delete from ImpProcess where imprecordid='PhotoTrans'").executeUpdate();
			List list = sess.createQuery(" from ImpProcess where imprecordid='PhotoTrans'").list();
			if(list == null || list.size()<1){
				ImpProcess pro = new ImpProcess();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setImprecordid("PhotoTrans");
				pro.setProcessinfo("图片处理");
				pro.setProcessname("处理中");
				pro.setProcesstype("1");
				pro.setProcessstatus("1");
				sess.save(pro);
				sess.flush();
				PhotoTransThread thread = new PhotoTransThread("PhotoTrans");
				new Thread(thread).start();
			}
			this.openWindow("photoInfoWin", "pages.dataverify.PhotoInfo");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("phototrans.onclick")
	@NoRequiredValidate
	public int phototransonclick() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		try {
			List list = sess.createQuery(" from ImpProcess where imprecordid='PhotoTrans'").list();
			if (list != null && list.size()>=1) {
				this.setMainMessage("是否重新抽取图片？");
				this.setMessageType(EventMessageType.CONFIRM); 
				this.addNextEvent(NextEventValue.YES,"phototrans2");
				this.addNextEvent(NextEventValue.CANNEL, "phototrans1");
			}else{
				this.setNextEventName("phototrans1");
			}
			
		} catch (Exception e) {
			this.setMainMessage("处理图片失败!");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("MGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("impinfocheck"))&&(Boolean) hm.get("impinfocheck")){
				listString=listString+"@|"+hm.get("imprecordid")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
		System.out.println("---->"+listString);
 		this.getPageElement("checkList").setValue(listString
 				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}
	
	@PageEvent("delimpinfo")
	public int delimpinfo() throws RadowException{
		String impids = this.getPageElement("checkList").getValue();
		if("".equals(impids)){
			this.setMainMessage("请勾选要删除的信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		dialog_set("delimpconfirm","您确定要删除选中的信息么？");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delimpconfirm")
	public int delimpconfirm() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String impids = this.getPageElement("checkList").getValue();
		String ids = impids.replace("|", "'").replace("@", ",");
		String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
				"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
				"I_E", "B_E","A60", "A61", "A62", "A63", "A64" ,"A05", "A68", "A69", "A71", "A99Z1" };
		try {
//			HBUtil.executeUpdate("delete from Imp_record where imp_record_id in ("+ids+")");
			//删除其他关联表数据 zxf
			List<Imprecord> imprecords = sess.createQuery(" from Imprecord where imprecordid in ("+ids+")").list();
			for (int i = 0; i < imprecords.size(); i++) {
				Imprecord rpd = imprecords.get(i);
				String tableExt = rpd.getImptemptable();
				for (int j = 0; j < tables.length; j++) {
					try {
//						System.out.println("drop table "+tables[j] +tableExt+";");
						 Object obj = null;
		            	 if(DBUtil.getDBType().equals(DBType.ORACLE)){
		            		 obj = sess.createSQLQuery("select count(*) from user_tables where table_name = '"+tables[j]+""+tableExt+"'").uniqueResult();
		            	 }else{
		            		 obj = sess.createSQLQuery("select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '"+tables[j]+""+tableExt+"' and TABLE_SCHEMA = 'ZWHZYQ'").uniqueResult();
		            	 }
		            	 Integer num = Integer.parseInt("" + obj);
		        		 if(num != 0){
		        			 sess.createSQLQuery(" drop table " +tables[j]+""+tableExt+"").executeUpdate();
		        			 sess.flush();
		        		 }
					} catch (Exception e) {
//						e.printStackTrace();
					}
				}
			}
			sess.createQuery("delete from ImpProcess where imprecordid in ("+ids+")").executeUpdate();
			sess.createQuery("delete from Datarecrejlog where imprecordid in ("+ids+")").executeUpdate();
			sess.createQuery("delete from Imprecord where imprecordid in ("+ids+")").executeUpdate();
			this.getPageElement("checkList").setValue("");
			this.setNextEventName("MGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("删除失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 打开对比窗口 张翔飞
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("dataComp")
	public int dataCompWin(String imprecordid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			Imprecord imd = (Imprecord) sess.get(Imprecord.class, imprecordid);
			
			//接收数据前的校验工作
			String imptemptable = imd.getImptemptable().toUpperCase();
			if(imptemptable!=null && !"".equals(imptemptable.toString())){
				//this.getExecuteSG().addExecuteCode("ShowCellCover('start','系统提示','正在进行接收前数据校验,请稍等...');");
				//1、机构编码不能为空
				/*String sql1 = "SELECT COUNT (1) FROM B01"+imptemptable+" B WHERE 1 = 1 AND B.B0114 IS NULL";
				Object obj1 = sess.createSQLQuery(sql1).uniqueResult();
				if(obj1!=null){
					Integer one = Integer.parseInt(""+obj1);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含空的机构编码）！');");
						return EventRtnType.FAILD;
					}
				}*/
				
				/*//2、机构编码不能重复
				String sql2 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql2 = "SELECT COUNT(1) FROM B01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM B01 B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B0111 LIKE '"+imptemptable+"%'))";
				}else{
					sql2 = "SELECT COUNT(1) FROM B01,B01 AS B WHERE B.B0114 = B01.B0114 AND B.B0111 != B01.B0111 AND B01.B0111 LIKE '"+imptemptable+"%'";
				}
				Object obj2 = sess.createSQLQuery(sql2).uniqueResult();
				if(obj2!=null){
					Integer one = Integer.parseInt(""+obj2);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含重复机构编码）！');");
						return EventRtnType.FAILD;
					}
				}
				
				//3、机构内人员身份证不能为空
				String sql3 = "SELECT COUNT(1) FROM A01"+imptemptable+" A WHERE 1 = 1 AND A.A0184 IS NULL";
				Object obj3 = sess.createSQLQuery(sql3).uniqueResult();
				if(obj3!=null){
					Integer one = Integer.parseInt(""+obj3);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含空的人员身份证）！');");
						return EventRtnType.FAILD;
					}
				}
				
				//4、机构内人员身份证不能重复  
				String sql4 = "";
				if(DBType.ORACLE == DBUtil.getDBType()){
					sql4 = "SELECT COUNT(1) FROM A01 WHERE 1 = 1 AND (EXISTS (SELECT 1 FROM A01 A,A02 WHERE A.A0184 = A01.A0184 AND A .a0000 != A01.a0000 AND A02.A0201B LIKE '"+imptemptable+"%' AND A.A0000 = A02.A0000))";
				}else{
					sql4 = "SELECT COUNT(1) FROM A01,A01 AS A,A02 WHERE A.A0184 = A01.A0184 AND A.a0000 != A01.a0000 AND A02.A0201B LIKE '"+imptemptable+"%' AND A.A0000 = A02.A0000";
				}
				Object obj4 = sess.createSQLQuery(sql4).uniqueResult();
				if(obj4!=null){
					Integer one = Integer.parseInt(""+obj4);
					if(one > 0){
						this.getExecuteSG().addExecuteCode("ShowCellCover('failure','系统提示','无法接受数据（发现数据中含重复人员身份证）！');");
						return EventRtnType.FAILD;
					}
				}*/
				
				//如果是按人员导入则对B01进行机构编码为空的校验
				if(imd.getImptype().equals("4")){
					String sql1 = "";
					if(DBType.ORACLE == DBUtil.getDBType()){
						sql1 = "SELECT COUNT(1) FROM B01,COMPETENCE_USERDEPT t WHERE 1 = 1 AND B01.B0111 != '-1' AND B01.B0114 IS NULL AND t.USERID = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"' AND t.B0111 = B01.B0111";
					}else{
						sql1 = "SELECT COUNT(1) FROM B01,COMPETENCE_USERDEPT t WHERE 1 = 1 AND B01.B0111 != '-1' AND B01.B0114 IS NULL OR B01.B0114 = '' AND t.USERID = '"+SysUtil.getCacheCurrentUser().getUserVO().getId()+"' AND t.B0111 = B01.B0111";
					}
					Object obj1 = sess.createSQLQuery(sql1).uniqueResult();
					if(obj1!=null){
						Integer one = Integer.parseInt(""+obj1);
						if(one > 0){
							this.getExecuteSG().addExecuteCode("$h2.confirm('系统提示','检测到本库部分机构存在空的机构编码，继续导入，可能会导致人员丢失！是否继续？',200,function(id){" +
			                        "if(id=='ok'){" +
			                        "           radow.doEvent('impconfirmBtn2','1');" +
			                            "}else if(id=='cancel'){}"
			                            + "});");
							return EventRtnType.FAILD;
						}
					}
				}
				
				//this.getExecuteSG().addExecuteCode("Ext.MessageBox.hide();");
			}else{
				this.setMainMessage("临时表不存在，不能接收数据！");
				return EventRtnType.FAILD;
			}
			
			/*String type = imd.getImpstutas();
			if(type!=null && type.equals("3")){
				this.setMainMessage("数据已拒绝，不能接收数据！");
			} else if(type!=null && type.equals("4")){
				this.setMainMessage("数据接收中，不能接收数据！");
			} else if(type!=null && type.equals("2")){
				this.setMainMessage("数据已接收，不能接收数据！");
			} else {*/
				//this.getExecuteSG().addExecuteCode("$h.openWin('dataComparisonWin','pages.dataverify.DataComparison','校验窗口',900,550, '"+imprecordid+"','"+request.getContextPath()+"');");
			
			batchPrintBefore(imprecordid);
			/*}*/
		} catch (Exception e) {
			this.setMainMessage("数据异常。");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 打开对比窗口 张翔飞
	 * @param name
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("impdataCompWin")
	public int impdataCompWin(String imprecordid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			Imprecord imd = (Imprecord) sess.get(Imprecord.class, imprecordid);
			String type = imd.getImpstutas();
			if(type!=null && type.equals("3")){
				this.setMainMessage("数据已拒绝，不能对比数据！");
			} else if(type!=null && type.equals("4")){
				this.setMainMessage("数据接收中，不能对比数据！");
			} else if(type!=null && type.equals("2")){
				this.setMainMessage("数据已接收，不能对比数据！");
			} else {
				this.getExecuteSG().addExecuteCode("$h.openWin('dataComparisonWin','pages.dataverify.DataComparison','校验窗口',1050,598, '"+imprecordid+",1','"+request.getContextPath()+"');addCloseL('dataComparisonWin','"+imprecordid+"');");
			}
		} catch (Exception e) {
			this.setMainMessage("数据异常。");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
    @NoRequiredValidate
    public int batchPrintBefore(String imprecordid) throws RadowException, AppException, SQLException{
        HBSession sess = HBUtil.getHBSession();
        Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
        String b0114 = imp.getB0114();
        Connection conn = sess.connection();
        Statement stmt = conn.createStatement();
        ResultSet rs =  null;
        String sql5 = "select count(b0111) from b01 where b0111 <> '-1'";
        rs = stmt.executeQuery(sql5);
        int size = 0;
        while(rs.next()){
            size = rs.getInt(1);
        }
//      String sql3 = "select b0114 from b01 where B0114 = '"+ b0114 +"'";
//      rs = stmt.executeQuery(sql3);
        if((size== 0 && "001.001".equals(imp.getImpdeptid())) || size > 0 || "4".equals(imp.getImptype())){
            String impdeptid = imp.getImpdeptid();
            String imptype = imp.getImptype();
            
            String ftype = imp.getFiletype();
//          if(imp.getIsvirety() == null || imp.getIsvirety().equals("") ||imp.getIsvirety().equals("0")){
//              this.setMainMessage("请先进行校验，再导入数据");
//              return EventRtnType.NORMAL_SUCCESS;
//          }
            if(imp.getImpstutas() != null && !imp.getImpstutas().equals("1")){
                if(imp.getImpstutas().equals("2")){
                    this.setMainMessage("数据已导入，不能重复导入。");
                } else if(imp.getImpstutas().equals("4")){
                    this.setMainMessage("数据接收中，不能重复接收。");
                } else {
                    this.setMainMessage("数据已拒绝，不能导入。");
                }
                return EventRtnType.NORMAL_SUCCESS;
            }
            //this.getExecuteSG().addExecuteCode("var grid = odin.ext.getCmp('errorGrid9');document.getElementById('grid9_totalcount').value=grid.getStore().getTotalCount();");
            if(imptype.equals("4") || (size== 0 && imp.getImpdeptid().equals("001.001"))){
                this.getExecuteSG().addExecuteCode("radow.doEvent('impconfirmBtn2','1')");
            } else {
            	this.getExecuteSG().addExecuteCode("$h2.confirm4('系统提示','是否更新导入的机构信息？<br/>"
            			+ "<span style=\""+"color:red"+"\">提示：点击‘是’，将覆盖之前选择的机构节点（包含其所有下级单位），以及机构节点下的所有人员</span>',300,function(id){" +
                        "if(id=='ok'){" +
                        "           radow.doEvent('impconfirmBtn2','1');" +
                            "}else if(id=='cancel'){" +
                            "   " +
                            "}" +
                        "});");
            }
        }else{
            this.setMainMessage("没有对应机构,请新建该机构!");
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	/**
     * 导入正确数据前提示信息
     * @return
     * @throws RadowException
     */
    @PageEvent("impconfirmBtn2")
    @NoRequiredValidate
    public int impmodelOnclickBefore(String str) throws RadowException{
        //导入校验(业务类型为2) 
        //String bsType = this.getPageElement("bsType").getValue();
        String imprecordid = this.getPageElement("imprecordid").getValue();
        HBSession sess = HBUtil.getHBSession();
        try {
     	   Imprecord  impRecord = (Imprecord) sess.get(Imprecord.class, imprecordid);
     	   //数据包内含人数
     	   //Long psn = impRecord.getPsncount();
     	   String psn = impRecord.getTotalnumber();
     	   
     	   StringBuffer sqlbf = new StringBuffer();
     	   sqlbf.append("SELECT count(1) ")
	          		.append(" FROM A01"+impRecord.getImptemptable()+" A,")
	          		.append(" (SELECT DISTINCT A01.A0000, A01.A0101, A01.A0184")
	          		.append(" FROM A01, A02")
	          		.append(" WHERE A01.A0000 = A02.A0000")
	          		.append(" AND A02.A0201B LIKE '"+impRecord.getImpdeptid()+"%'")
	          		.append(" ) B")
	          		.append(" WHERE A.A0184 = B.A0184");
     	   Object obj = sess.createSQLQuery(sqlbf.toString()).uniqueResult();
     	   
     	   if(Integer.parseInt(obj.toString())>=(Integer.parseInt(psn))){
     			 obj = psn;
     	   }
     	   
     	   StringBuffer sqlbf2 = new StringBuffer();
     	   sqlbf2.append("select count(1) from b01 t, b01"+impRecord.getImptemptable()+" k where ")
	          		.append(" t.b0114=k.b0114 and t.b0111 not like '"+impRecord.getImpdeptid()+"%'");
     	   Object obj2 = sess.createSQLQuery(sqlbf2.toString()).uniqueResult();
     	   if(Integer.parseInt(obj.toString())>0 || Integer.parseInt(obj2.toString())>0){
               /* this.setMainMessage((Integer.parseInt(obj.toString())>0?"导入数据中的人员身份证号码与系统中重复 "+obj.toString()+" 人！":"")
             		   +(Integer.parseInt(obj2.toString())>0?"导入数据中的机构编码与系统中其他机构重复 "+obj2.toString()+" 个！":"")+ "是否继续接收信息？");
                this.setMessageType(EventMessageType.CONFIRM); 
                this.addNextEvent(NextEventValue.YES,"impmodel", str);*/
                String tip = (Integer.parseInt(obj.toString())>0?"导入数据中的人员身份证号码与系统中重复 "+obj.toString()+" 人！":"")
              		   +(Integer.parseInt(obj2.toString())>0?"导入数据中的机构编码与系统中其他机构重复 "+obj2.toString()+" 个！":"") + "是否继续接收信息？";
                this.getExecuteSG().addExecuteCode("$h1.confirm('系统提示','"+tip+"',300,function(id){" +
                        "if(id=='ok'){" +
                        "           radow.doEvent('impmodel','"+str+"');" +
                            "}else if(id=='cancel'){}"
                            + "});");
     	   }else{
                this.impmodelOnclick(str);
            }
			} catch (Exception e) {
				e.printStackTrace();
			}
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 导入正确数据
     * @return
     * @throws RadowException
     */
    @PageEvent("impmodel")
    @NoRequiredValidate
    public int impmodelOnclick(String str)throws RadowException{
        HBSession sess = HBUtil.getHBSession();
        try {
            CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
            String imprecordid = this.getPageElement("imprecordid").getValue();
            /**
             * 历史库代码暂无
             */
            Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
            
            UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
            //-------------------------------------------------
            String sql1 = "delete from Datarecrejlog where imprecordid = '"+imprecordid+"' ";
            sess.createQuery(sql1).executeUpdate();
            //------------------------------------------------
            ImpmodelThread thr = new ImpmodelThread(imp,str, user,userVo); 
            new Thread(thr).start();
            this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin1','pages.dataverify.RefreshOrgRecRej','接收窗口',700,445, '"+imprecordid+"','"+request.getContextPath()+"');");
        } catch (Exception e) {
            if(sess!=null)
                sess.getTransaction().rollback();
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 全部退回
     * @return
     * @throws RadowException
     */
    @PageEvent("rejectBtn")
    @NoRequiredValidate
    public int rejectBtnOnclick(String imprecordid)throws RadowException{
        String b0111 = imprecordid;
        String bsType = "2";//导入校验
        HBSession sess = HBUtil.getHBSession();
        try {
            sess.beginTransaction();
            Imprecord  imp = (Imprecord) sess.get(Imprecord.class, imprecordid);
            String imptemptable = imp.getImptemptable();
            String ftype = imp.getFiletype();
            if(imp.getImpstutas().equals("2")){
                this.setMainMessage("数据已导入，不能拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            } else if(imp.getImpstutas().equals("4")){
                this.setMainMessage("数据接收中，不能拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            } else if(imp.getImpstutas().equals("3")){
                this.setMainMessage("数据已拒绝，不能重复拒绝。");
                return EventRtnType.NORMAL_SUCCESS;
            }
            String str = "";
//          String str = this.FkImpData(imprecordid, "");
            //打回后清除错误信息Verify_Error_List
            sess.createSQLQuery(" delete from Verify_Error_List where vel004 = '"+b0111+"' and vel005='"+bsType+"'").executeUpdate();
            String tables1[] = {"A01", "A02","A06","A08", "A11", "A14", "A15", "A29","A30", 
                    "A31","A36","A37","A41", "A53","A57","A60","A61","A62","A63","A64","A65", "B01", "B_E", "I_E","A05", "A68", "A69", "A71", "A99Z1"};
            for (int i = 0; i < tables1.length; i++) {
         	   Object obj = null;
         	   if(DBUtil.getDBType().equals(DBType.ORACLE)){
         		   obj = sess.createSQLQuery("select count(*) from user_tables where table_name = '"+tables1[i]+""+imptemptable+"'").uniqueResult();
         	   }else{
         		   obj = sess.createSQLQuery("select count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME = '"+tables1[i]+""+imptemptable+"' and TABLE_SCHEMA = 'ZWHZYQ'").uniqueResult();
         	   }
         	   Integer num = Integer.parseInt("" + obj);
     		   if(num != 0){
     			   sess.createSQLQuery(" drop table " + tables1[i] + ""+imptemptable+"").executeUpdate();
     		   }
            }
            imp.setImpstutas("3");
            sess.update(imp);
            sess.flush();
            sess.getTransaction().commit();
            PhotosUtil.removDirImpCmd(imprecordid,"");
            if(str != null && !str.equals(""))
                this.getExecuteSG().addExecuteCode("var w=window.open('ProblemDownServlet?method=downFile&prid="+ URLEncoder.encode(URLEncoder.encode(str,"UTF-8"),"UTF-8")+"');  setTimeout(cc,600); function cc(){w.close();}");
            if (ftype.equalsIgnoreCase("hzb") || ftype.equalsIgnoreCase("zb3")) {
                new LogUtil().createLog("462", "IMP_RECORD", "", "", "拒绝清除", new ArrayList());
            } else if (ftype.equalsIgnoreCase("zzb3")){
                new LogUtil().createLog("472", "IMP_RECORD", "", "", "拒绝清除", new ArrayList());
            }
            //this.getExecuteSG().addExecuteCode("realParent.odin.ext.getCmp('MGrid').store.reload();");
            this.getExecuteSG().addExecuteCode("odin.ext.getCmp('MGrid').store.reload();");
            this.setMainMessage("已拒绝！");
        } catch (UnsupportedEncodingException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } catch (AppException e) {
            sess.getTransaction().rollback();
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
}
