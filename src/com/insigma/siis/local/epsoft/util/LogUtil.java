package com.insigma.siis.local.epsoft.util;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Inf;
import com.insigma.siis.local.business.entity.LogDetail;
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;


public class LogUtil extends Thread{
	private String eventtype;
	private String eventobject;
	private String objectid;
	private String objectname;
	private String operate_cBomments;
	private List list;
	UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
	
	public LogUtil(UserVO user) {
		super();
		this.user = user;
	}
	public void run() {
		try {
			createLog(eventtype,eventobject,objectid,objectname, operate_cBomments,list);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public LogUtil() {
		super();
	}
	public LogUtil(String eventtype, String eventobject, String objectid,
			String objectname, String operate_cBomments, List list) {
		super();
		this.eventtype = eventtype;
		this.eventobject = eventobject;
		this.objectid = objectid;
		this.objectname = objectname;
		this.operate_cBomments = operate_cBomments;
		this.list = list;
	}
	public LogUtil(String eventtype, String eventobject, String objectid,
			String objectname, String operate_cBomments, List list,UserVO user) {
		super();
		this.eventtype = eventtype;
		this.eventobject = eventobject;
		this.objectid = objectid;
		this.objectname = objectname;
		this.operate_cBomments = operate_cBomments;
		this.list = list;
		this.user = user;
	}
	/**
	 * 写主日志
	 * @param session
	 * @param taskTypecode
	 * @param paramstr
	 * @param userid
	 * @return
	 * @throws SQLException
	 */
	public void createLog(String eventtype,String eventobject,String objectid,String objectname, String operate_cBomments,
			List list) throws SQLException{
		
		//判断执行的操作是否需要记录日志：系统登入和系统退出时记录主日志，其他情况下数据没有改变时不记录日志  3开头为人员模块， 3开头为人员
		if(eventtype.startsWith("3") && eventtype.endsWith("2")){
			if(list==null || list.size()<=0){
				return;
			}
		}
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		if(user == null)user = this.user;
		if(user == null)return;
		//HBSession sess = HBUtil.getHBSession();
		Session sess = null;
		Transaction trans = null;
		try{
			sess = HBUtil.getHBSessionFactory().openSession();
			trans = sess.beginTransaction();
			LogMain logmain = new LogMain();
			String str1="From CodeValue t where t.codeType='OPERATE_TYPE' and t.codeValue='"+eventtype+"'";
			List type = sess.createQuery(str1).list();
			if(type.size()!=0){
				CodeValue c = (CodeValue)type.get(0);
				logmain.setEventtype(c.getCodeName());
			}else{
				return;
				//logmain.setEventtype("");
			}
			String str2="From CodeValue t where t.codeType='TABLE_NAME' and t.codeValue='"+eventobject+"'";
			List type2 = sess.createQuery(str2).list();
			if(type2.size()!=0){
				CodeValue d = (CodeValue)type2.get(0);
				logmain.setEventobject(d.getCodeName());
			}else{
				logmain.setEventobject("");
			}
			logmain.setObjectid(objectid);
			logmain.setObjectname(objectname);
			logmain.setUserlog(user.getId());
			logmain.setOperatecomments(operate_cBomments);
			logmain.setTableName(eventobject);
			try {
				logmain.setSystemoperatedate(new Date());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			sess.save(logmain);
			
	//		ts.commit();
			if(list!=null && list.size()!=0){
				List<LogDetail> list1 = changeType(list);
				createDetailLog(logmain.getSystemlogid(),list1,eventobject);
			}
			//sess.flush();
			trans.commit();
		}catch(Exception e){
			if(trans!=null && trans.isActive()){
				trans.rollback();
			}
			e.printStackTrace();
		}finally{
			if(sess!=null )sess.close();
			trans = null;
		}
	}
	public void createDetailLog(String logmainid,List<LogDetail> list, String eventobject){
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<list.size();i++){
//			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			list.get(i).setSystemlogid(logmainid);
			try {
				list.get(i).setChangedatetime(new Date());
				list.get(i).setTableName(eventobject);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sess.saveOrUpdate(list.get(i));;
//			ts.commit();
		}
		sess.flush();
	}
	public List<LogDetail> changeType(List list){
		List<LogDetail> list1 = new ArrayList<LogDetail>();
		HBSession sess = HBUtil.getHBSession();
		for(int i=0;i<list.size();i++){
			LogDetail lg = new LogDetail();
			Object[] obj = (Object[]) list.get(i);
			obj[1] = obj[1]==null?"":obj[1];
			obj[2] = obj[2]==null?"":obj[2];
			//字段表述
			/*String hql = "From Inf t where t.infoid='"+obj[0]+"'";
			List list2 = sess.createQuery(hql).list();
			if(list2.size()!=0){
				Inf inf = (Inf)list2.get(0);
				lg.setDataname(inf.getInfoname());
			}else{
				lg.setDataname("");
			}*/
			lg.setDataname(obj[3].toString());
			
			String infoid = (String)obj[0];
			//如果字段包含DATE
			if(infoid.equals("CREATEDATE")||infoid.equals("UPDATEDATE")){
				if(!obj[1].equals("")){
					Date date = new Date(Long.valueOf((String)obj[1] ));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					lg.setOldvalue(sdf.format(date));
					lg.setNewvalue(sdf.format(DateUtil.getSysDate()));
				}else{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					lg.setNewvalue(sdf.format(DateUtil.getSysDate()));
				}
			}else{
				lg.setOldvalue((String) obj[1]);
				lg.setNewvalue((String) obj[2]);
			}
			list1.add(lg);
		}
		return list1;
	}
	
	
	/**
	 * 修改人员信息时，传参数输出日志
	 * @param operation		具体操作
	 * @param codeName		操作项类型（信息集）
	 * @param a0000			操作涉及对象编码
	 * @param name			当前操作所涉及对象的名称
	 */
	public void createLogNew(String opid, String operation,String tableName,String a0000,String name, List list){
		
		
		//判断执行的操作是否需要记录日志：系统登入和系统退出时记录主日志，其他情况下数据没有改变时不记录日志  3开头为人员模块， 2开头为机构
		//如果list无记录，表示没有更改，不需要记录日志
		if(operation.endsWith("修改")){
			if(list==null || list.size()<=0){
				return;
			}
		}
		
		
		//HBSession sess = HBUtil.getHBSession();
		Session sess = HBUtil.getHBSessionFactory().openSession();
		Transaction trans = sess.beginTransaction();
		
		LogMain logmain = new LogMain();
		logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//主键id
		
		logmain.setUserlog(this.user.getId()); 	//操作用户（此处为用户登录名）。
		logmain.setSystemoperatedate(new Date()); 			//系统操作时间
		logmain.setEventtype(operation); 				//具体操作
		logmain.setEventobject(tableName); 				//操作项类型（信息集）
		logmain.setObjectid(a0000); 						//操作涉及对象编码
		logmain.setObjectname(name);   			//当前操作所涉及对象的名称
		logmain.setTableName(tableName);
		logmain.setOperatecomments(opid);
		sess.save(logmain);
		sess.flush();
		
		
		if(list!=null && list.size()!=0){
			List<LogDetail> list1 = changeType(list);
			createDetailLog(logmain.getSystemlogid(),list1,tableName);
		}
		
		trans.commit();
		
	}
	
	
	/**
	 * 修改人员信息时，传参数输出日志
	 * @param operation		具体操作
	 * @param codeName		操作项类型（信息集）
	 * @param a0000			操作涉及对象编码
	 * @param name			当前操作所涉及对象的名称
	 */
	public void createLogNewF(String operation,String codeName,String a0000,String name){
		
		HBSession sess = HBUtil.getHBSession();
		
		LogMain logmain = new LogMain();
		logmain.setSystemlogid(UUID.randomUUID().toString().trim().replaceAll("-", "")); 							//主键id
		logmain.setUserlog(SysManagerUtils.getUserName()); 	//操作用户（此处为用户登录名）。
		logmain.setSystemoperatedate(new Date()); 			//系统操作时间
		logmain.setEventtype(operation); 				//具体操作
		logmain.setEventobject(codeName); 				//操作项类型（信息集）
		logmain.setObjectid(a0000); 						//操作涉及对象编码
		logmain.setObjectname(name);   			//当前操作所涉及对象的名称
		sess.save(logmain);
		sess.flush();
		
	}
	
	
}
