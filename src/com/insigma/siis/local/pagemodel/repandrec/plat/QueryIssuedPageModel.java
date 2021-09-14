package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.chainsaw.Main;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

public class QueryIssuedPageModel extends PageModel {
	
	@PageEvent("clear.onclick")
	@NoRequiredValidate           
	public int resetonclick()throws RadowException, AppException {
		this.getPageElement("reporttype").setValue("");
		this.getPageElement("createtimesta").setValue("");
		this.getPageElement("createtimeend").setValue("");
//		this.getPageElement("createtimeend").setValue("");
//		this.getPageElement("gsearch").setValue("");
//		this.getPageElement("comboxArea_gsearch").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("query.onclick")
	@NoRequiredValidate           
	@OpLog
	public int query()throws RadowException, AppException {
		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	static String lpath = AppConfig.LOCAL_FILE_BASEURL;
	static String fpath = AppConfig.TRANS_SERVER_BASEURL;
	public static void issued(String stype, String b0111, String linkpsn,
			String linktel, String remark, String datainfo, File file, List<String> ftpsid){
		
		ZwhzPackDefine info = new ZwhzPackDefine();
		info.setId(UUID.randomUUID().toString().replace("-", ""));
		B01 b01 = (B01) HBUtil.getHBSession().get(B01.class, b0111);
		java.sql.Timestamp now = DateUtil.getTimestamp();
		String time = DateUtil.timeToString(now);
		String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
		info.setB0101(b01.getB0101());
		info.setB0111(b01.getB0111());
		info.setB0114(b01.getB0114());
		info.setB0194(b01.getB0194());
		info.setDatainfo(datainfo);
		info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(), "yyyyMMdd"));
		info.setLinkpsn(linkpsn);
		info.setLinktel(linktel);
		info.setPersoncount(0L);
		info.setOrgcount(0L);
		info.setRemark(remark);
		info.setStype(stype);
		info.setStypename(getStypename(stype));
		info.setTime(time);
		info.setTranstype("down");
		info.setErrortype("无");
		info.setErrorinfo("无");
		String dataFile = getStypename(stype) + "_" +b01.getB0111()+"_" +b01.getB0101()+"_" + time1 
			+ file.getName().substring(file.getName().lastIndexOf("."));
		String packageFile = "Pack_"+ getStypename(stype)+ "_" +b01.getB0111()+"_" +b01.getB0101()+"_" + time1 +".xml";
		String fptzml = getFptzml(stype);
		List<SFileDefine> sfile = new ArrayList<SFileDefine>();
		SFileDefine sf = new SFileDefine();
		sf.setName(dataFile);
		sf.setOrgrows(0l);
		sf.setPersonrows(0l);
		sf.setSize(file.getUsableSpace());
		sf.setTime(time);
		info.setSfile(sfile);
		try {
			FileUtil.createFile(lpath+ packageFile, JXUtil.Object2Xml(info,true), false, "UTF-8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (String fid : ftpsid) {
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(TransConfig.class, fid);
			String n = fpath + "/" + jfcc.getUsername();
			nioTransferCopy(file, new File(n +"/" + fptzml + "/" + dataFile));
			nioTransferCopy(new File(lpath+ packageFile), new File(n +"/" + fptzml + "/" + packageFile));
		}
	}
	private static String getFptzml(String stype) {
		// TODO Auto-generated method stub
		if(stype.equals("3")){
			return ZwhzFtpPath.ZB_DOWN;
		} else if(stype.equals("4")){
			return ZwhzFtpPath.DM_DOWN;
		} else if(stype.equals("5")){
			return ZwhzFtpPath.FK_DOWN;
		} else if(stype.equals("6")){
			return ZwhzFtpPath.JC_DOWN;
		} else if(stype.equals("7")){
			return ZwhzFtpPath.OTHER_DOWN;
		}
		return ZwhzFtpPath.OTHER_DOWN;
	}
	private static void nioTransferCopy(File source, File target) {  
	    FileChannel in = null;  
	    FileChannel out = null;  
	    FileInputStream inStream = null;  
	    FileOutputStream outStream = null;  
	    try {  
	        inStream = new FileInputStream(source);  
	        outStream = new FileOutputStream(target);  
	        in = inStream.getChannel();  
	        out = outStream.getChannel();  
	        in.transferTo(0, in.size(), out);  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {
	    	try {
				inStream.close();
				in.close();
		        outStream.close();
		        out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }  
	}  

	private static String getStypename(String stype) {
		// TODO Auto-generated method stub
		if(stype.equals("3")){
			return "补充信息项配置方案";
		} else if(stype.equals("4")){
			return "补充代码集配置方案";
		} else if(stype.equals("5")){
			return "数据校验方案";
		} else if(stype.equals("6")){
			return "主题分库模型";
		} else if(stype.equals("7")){
			return "其他";
		}
		return "其他";
	}
//	@PageEvent("reset.onclick")
//	@NoRequiredValidate
//	public int resetOnclick()throws RadowException{
//		List<String> s = new ArrayList<String>();
//		s.add("12312");
//		File file = new File("D:/提交检测可执行框架代码.rar");
//		issued("3", "330700", "z", "131", "re", "data", file, s);
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	@PageEvent("codePubBtn.onclick")
	@NoRequiredValidate
	public int codePubBtnOnclick()throws RadowException{
		this.openWindow("codePubwin", "pages.repandrec.plat.CodePub");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("normPubBtn.onclick")
	@NoRequiredValidate
	public int normPub(String name) throws RadowException{
		this.openWindow("normPubwin", "pages.repandrec.plat.NormPub");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("verificationPubBtn.onclick")
	@NoRequiredValidate
	public int grantbtnOnclick()throws RadowException{
		this.openWindow("verificationPubwin", "pages.repandrec.plat.VerificationPub");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("modelPubBtn.onclick")
	@NoRequiredValidate
	public int modelPub(String name) throws RadowException{
		this.openWindow("modelPubwin", "pages.repandrec.plat.ModelPub");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询
	 * @param start
	 * @param limit
	 * @return
	 */
	@PageEvent("MGrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
//		String userid = SysUtil.getCacheCurrentUser().getId();
//		Object deptid = HBUtil.getHBSession().createSQLQuery("SELECT a.b0111 FROM COMPETENCE_USERDEPT a WHERE a.userid='"+userid+"'").uniqueResult();
		StringBuffer sql = new StringBuffer("select file_name filename,to_char(imp_time,'yyyymmdd') imptime,emp_dept_name empgroupname," +
				"is_virety isvirety, wrong_number wrongnumber,total_number totalnumber,imp_record_id imprecordid " +
				"from Imp_record where 1=1 ");
		String st = this.getPageElement("createtimesta").getValue();
		String et = this.getPageElement("createtimeend").getValue();
		
		if(st != null && !st.equals("")){
			sql.append(" and to_char(imp_time,'yyyymmdd') >='" +st+ "'");
		}
		if(et != null && !et.equals("")){
			sql.append(" and to_char(imp_time,'yyyymmdd') <='" +et+ "'");
		}
		
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
//		this.setNextEventName("MGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
