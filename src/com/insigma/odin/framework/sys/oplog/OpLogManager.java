/*
 * 创建日期 2005-6-7
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package com.insigma.odin.framework.sys.oplog;

import java.io.*;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import org.hibernate.Hibernate;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.sys.entity.SbdsUserlog;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StringCompress;
import com.insigma.odin.framework.util.SysUtil;



/**
 * @$comment 操作日志管理
 * @author zhangy
 *
 */
public class OpLogManager {
	private static final ThreadLocal userlogCache=new ThreadLocal();
	
	private static OplogHelp oplogHelp = null;
	
	public static OplogHelp getOplogHelp() {
		return oplogHelp;
	}

	public static void setOplogHelp(OplogHelp oplogHelp) {
		OpLogManager.oplogHelp = oplogHelp;
	}

	public static void setUserlog(String userlog){
		userlogCache.set(userlog);
	}
	
	public static String getUserlog(){
		return (String)userlogCache.get();
	}
	/**
	 * 建用户操作表
	 * @throws Exception
	 */
	public static void cutab() throws Exception {
		if(DBUtil.getDBType().equals(DBType.MYSQL)){
			return;
		}
		if(1==1){
			return ;
		}
		HBSession hbsess=HBUtil.getHBSession();
		hbsess.flush();
		Connection conn=hbsess.connection();
		CallableStatement cstmt=conn.prepareCall("{call glog.cutab}");
		cstmt.execute();
		if(cstmt!=null) cstmt.close();
	}
	
	/**
	 * 删用户操作表
	 * @throws Exception
	 */
	public static void dutab() throws AppException {
		if(DBUtil.getDBType().equals(DBType.MYSQL)){
			return;
		}
		if(1==1){
			return ;
		}
		HBSession hbsess=HBUtil.getHBSession();
		hbsess.flush();
		Connection conn=hbsess.connection();
		CallableStatement cstmt;
		try {
			cstmt = conn.prepareCall("{call glog.dutab}");
			cstmt.execute();
			if(cstmt!=null) cstmt.close();
		} catch (SQLException e) {
			throw new AppException("数据库处理异常！",e);
		}
	}
	
	/**
	 * 取操作序列号
	 * @return
	 * @throws Exception
	 */
	public static long getOpseno() throws AppException {
		if(DBUtil.getDBType().equals(DBType.MYSQL)){
			return -1l;
		}
		if(1==1){
			return -1l;
		}
		long opseno;
		HBSession hbsess=HBUtil.getHBSession();
		hbsess.flush();
		try{
			Connection conn=hbsess.connection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("select glog.getopseno() from dual");
			if(rs.next()){
				opseno=rs.getLong(1);
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
//				if(opseno==0){
//					throw new AppException("无法取得操作日志序列号");
//				}
				return opseno;
			}else{
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				throw new AppException("无法取得操作日志序列号");
			}
		}catch(Exception e){
			throw new AppException("数据库处理出现异常！",e);
		}
	}
	
//	public static String getAAE074(Session sess,String opseno,String senoType) throws Exception {
//		String aae074="";
//		sess.flush();
//		Connection conn=sess.connection();
//		Statement stmt=conn.createStatement();
//		String sql="";
//		if(senoType.equals("1")){//财务收支号
//			sql="select glog.getead022("+opseno+") from dual";
//		}else if(senoType.equals("2")){//结算流水号
//			sql="select glog.geteae016("+opseno+") from dual";	
//		}else{//默认为核定业务流水号
//			sql="select glog.getaae074("+opseno+") from dual";
//		}
//		ResultSet rs=stmt.executeQuery(sql);
//		if(rs.next()){
//			aae074=rs.getString(1);
//			if(rs!=null) rs.close();
//			if(stmt!=null) stmt.close();
//			return aae074;
//		}else{
//			if(rs!=null) rs.close();
//			if(stmt!=null) stmt.close();
//			throw new AppException(810601002,"无法取得核定业务流水号");
//		}
//	}
	
	/**
	 * 业务回退
	 */
	public static void dbrol(long opseno,String userid) throws AppException{
		if(DBUtil.getDBType().equals(DBType.MYSQL)){
			return;
		}
		if(1==1){
			return ;
		}
		HBSession hbsess=HBUtil.getHBSession();
		hbsess.flush();
		Connection conn=hbsess.connection();
		try{
			CallableStatement cstmt=conn.prepareCall("{call glog.dbrol(?,?)}");
			cstmt.setLong(1,opseno);
			cstmt.setString(2,userid);
			cstmt.execute();
			if(cstmt!=null) cstmt.close();
		}catch(Exception e){
			throw new AppException("数据库处理异常！",e);
		}
	}
	
	/**
	 * 保存操作日志
	 * @throws Exception
	 */
	public static void saveOpLog() throws AppException{
		if(DBUtil.getDBType().equals(DBType.MYSQL)){
			return;
		}
		if(1==1){
			return ;
		}
		HBSession hbsess=HBUtil.getHBSession();
		String strUserlog=(String)userlogCache.get();
		JSONObject jsonobject = JSONObject.fromObject(strUserlog);
		SbdsUserlog userlog=new SbdsUserlog();
		userlog.setFunctionid(jsonobject.getString("functionid"));
		if(!"".equals(jsonobject.getString("aac001"))){
			userlog.setAac001(Long.parseLong(jsonobject.getString("aac001")));
		}
		userlog.setAab001(jsonobject.getString("aab001"));
		userlog.setDigest(jsonobject.getString("digest"));
		userlog.setPrcol1(jsonobject.getString("prcol1"));
		userlog.setPrcol2(jsonobject.getString("prcol2"));
		userlog.setPrcol3(jsonobject.getString("prcol3"));
		userlog.setPrcol4(jsonobject.getString("prcol4"));
		userlog.setPrcol5(jsonobject.getString("prcol5"));
		userlog.setPrcol6(jsonobject.getString("prcol6"));
		userlog.setPrcol7(jsonobject.getString("prcol7"));
		userlog.setPrcol8(jsonobject.getString("prcol8"));
		String cueUrl = SysfunctionManager.getCurrentSysfunction().getLocation();
		if(cueUrl.indexOf("/pages/comm/commAction")>=0){
			userlog.setKfff("1");
		}else if(cueUrl.indexOf("/radowAction")>=0){
			userlog.setKfff("0");
		}
		//userlog.setOrisource(jsonobject.getString("orisource"));
		byte[] b=StringCompress.compress(jsonobject.getString("orisource"));
		if(b==null) b=new byte[0];
		Blob orisourceb=Hibernate.createBlob(b);
		userlog.setOrisourceb(orisourceb);
		Long opseno=new Long(getOpseno());
		//Sysfunction moduleSysfunction=SysfunctionManager.getModuleSysfunction();
		userlog.setOpseno(opseno);
		//userlog.setFunctionid(moduleSysfunction.getFunctionid());
		Date sysdate=HBUtil.getSysdate();
		//业务年月暂时设为当前年月
		userlog.setAae002(OpLogManager.getOplogHelp().getBusinessPeriod(opseno,userlog.getFunctionid()));
		CurrentUser currentUser=SysUtil.getCacheCurrentUser();
		userlog.setAae011(currentUser.getLoginname());
		userlog.setAae036(sysdate);
		userlog.setAaa027(oplogHelp.getOverareaCode());
		userlog.setEae024("0");   
		hbsess.save(userlog);
		hbsess.flush();
		
		userlogCache.set(null);
//		Vector userlogList=(Vector)((HashMap)request.getBody()).get("userlog");
//		Vector uscrlogList=(Vector)((HashMap)request.getBody()).get("uscrlog");
//		User user=request.getHead().getUser();
//		Integer aae002=null;
//		
//		if(userlogList==null||userlogList.size()==0){
//			throw new AppException(810601001,"操作日志配置错误，请与系统管理员联系");
//		}
//		
//		//cutab(sess);
//		//long opseno=getOpseno(sess);
//		Long opseno=new Long(getOpseno(sess));
//		SysCal cal=new SysCal();
//		
//		HashMap hmUserlog=(HashMap)userlogList.elementAt(0);
//		String functionid=(String)hmUserlog.get("functionid");
//		Syscontrol ctrl=(Syscontrol)sess.load(Syscontrol.class,functionid);
//		if(ctrl.getUptype()==null){
//			aae002=cal.getOpym();
//		}else if(ctrl.getUptype().equals("0")){
//			aae002=cal.getOpym();
//		}else{
//			aae002=cal.getPyym();
//		}
//		String aac001=(String)hmUserlog.get("aac001");
//		String aab001=(String)hmUserlog.get("aab001");
//		String prcol1=(String)hmUserlog.get("prcol1");
//		String prcol2=(String)hmUserlog.get("prcol2");
//		String prcol3=(String)hmUserlog.get("prcol3");
//		String prcol4=(String)hmUserlog.get("prcol4");
//		String prcol5=(String)hmUserlog.get("prcol5");
//		String prcol6=(String)hmUserlog.get("prcol6");
//		String prcol7=(String)hmUserlog.get("prcol7");
//		String prcol8=(String)hmUserlog.get("prcol8");
//		String userid=user.getUserId();
//		String siOrgCode=user.getSiOrgCode();
//		SbdsUserlog userlog=new SbdsUserlog();
//		userlog.setOpseno(opseno);
//		userlog.setFunctionid(functionid);
//		userlog.setAae002(aae002);
//		userlog.setAac001(aac001);
//		userlog.setAab001(aab001);
//		userlog.setPrcol1(prcol1);
//		userlog.setPrcol2(prcol2);
//		userlog.setPrcol3(prcol3);
//		userlog.setPrcol4(prcol4);
//		userlog.setPrcol5(prcol5);
//		userlog.setPrcol6(prcol6);
//		userlog.setPrcol7(prcol7);
//		userlog.setPrcol8(prcol8);
//		userlog.setAae011(userid);
//		userlog.setAae036(cal.getSysdate());
//		userlog.setAab034(siOrgCode);
//		userlog.setAae013(null);
//		userlog.setEae024("0");
//		sess.save(userlog);
	}
	
	public static String getOriSource(Blob oriSourceB){
		if(oriSourceB==null){
			return null;
		}
		ByteArrayOutputStream os=null;
		InputStream is=null;
		try{
			os = new ByteArrayOutputStream();
		 	is = oriSourceB.getBinaryStream();
		 	byte[] buffer = new byte[1024];
		 	int len = 0;
		 	while((len = is.read(buffer) )!= -1){
		 		os.write(buffer,0,len);
		 	}
		 	String s=StringCompress.decompress(os.toByteArray());
		 	return s;
		}catch(Exception e){
			if(os!=null){try{ os.close();}catch(Exception e1){}}
			if(is!=null){try{ is.close();}catch(Exception e1){}}
			return null;
		}
	}
}
