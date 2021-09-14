package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

/**
 * 任免表界面的日志管理
 * @author a
 *
 */
public class LogMangerPageModel extends PageModel{

	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridMain.dogridquery");
		this.setNextEventName("gridAudit.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 主日志信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridMain.dogridquery")
	public int doMainQuery(int start, int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		
		String userlog = this.getPageElement("userlog").getValue();
		String eventtype = this.getPageElement("eventtype").getValue();
		String eventobject = this.getPageElement("eventobject").getValue();
		String startTime = this.getPageElement("startTime").getValue();
		String endTime = this.getPageElement("endTime").getValue();
		
		String sql = "select a.system_log_id,b.username,a.eventtype,a.eventobject,a.system_operate_date"
				+ " from LOG_MAIN a,SMT_USER b where a.userlog=b.userid and a.objectid='"+a0000+"' ";
		
		if(StringUtils.isNotEmpty(userlog)){
			sql += " and USERLOG = '"+userlog+"'";
		}
		if(StringUtils.isNotEmpty(eventtype)){
			sql += " and EVENTTYPE = '"+eventtype+"'";
		}
		if(StringUtils.isNotEmpty(eventobject)){
			sql += " and eventobject = '"+eventobject+"'";
		}
		if(StringUtils.isNotEmpty(startTime)){
			sql += " and system_operate_date >= to_date('"+startTime+"','yyyy-MM-dd')";
		}
		if(StringUtils.isNotEmpty(endTime)){
			sql += " and system_operate_date <= to_date('"+endTime+"','yyyy-MM-dd')";
		}
		
		sql += " order by a.system_operate_date desc";
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 子日志信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridDeatil.dogridquery")
	public int doDeatilQuery(int start, int limit) throws RadowException{
		String mainid = this.getPageElement("system_log_id").getValue();
		
		String sqlStart ="select (CASE "
				+ "when A .dataname like '%A0192F' THEN '任职时间（A0192F）'"
				+ "when A .dataname like '%TBR' THEN '填报人id（TBR）'"
				+ "when A .dataname like '%XGSJ' THEN '修改时间（XGSJ）'"
				+ "when A .dataname like '%TBSJ' THEN '填表时间（TBSJ）'"
				+ "when A .dataname like '%XGR' THEN '修改人（XGR）'"
				+ "when A .dataname like '%STATUS' THEN '照片状态（STATUS）'"
				+ "when A .dataname like '%A0114' THEN '出生地（A0114A）'"
				+ "when A .dataname like '%A0111' THEN '籍贯（A0111A）' "				
				+ "when A .dataname like '%A0155' THEN '公务员登记时间（A0155）'"
				+ "when A .dataname like '%a0251' THEN '是否破格提拔（a0251）'"
				+ "when A .dataname like '%A0221A' THEN '职务等级(采集)（A0221A）'"
				+ "WHEN A .dataname LIKE '%A0251' THEN '任职变动类型（A0251）'"
				+ "WHEN A .dataname LIKE '%A0222A' THEN '领导非领导统计标识（A0222A）'"
				+ "WHEN A .dataname LIKE '%A0188' THEN '具有乡镇党政正职经历（A0188）'"
				+ "WHEN A .dataname LIKE '%A0283G' THEN '名册名称（A0283G）'"
				+ "WHEN A .dataname LIKE '%A3684' THEN '身份证号码（A3684）'"	
				+ "WHEN A .dataname LIKE '%QRZXWXX' THEN '全日制院校系专业（QRZXWXX）'"
				+ "WHEN A .dataname LIKE '%QRZXW' THEN '最高全日制学位（QRZXW）'"
				+ "WHEN A .dataname LIKE '%QRZXL' THEN '最高全日制学位（QRZXL）'"
				+ "WHEN A .dataname LIKE '%ZGXL' THEN '最高学历（ZGXL）'"
				+ "WHEN A .dataname LIKE '%ZGXLXX' THEN '最高院校系专业（ZGXLXX）'"
				+ "WHEN A .dataname LIKE '%ZGXW' THEN '最高学位（ZGXW）'"
				+ "WHEN A .dataname LIKE '%ZZXLXX' THEN '在职院校系专业（ZZXLXX）'"
				+ "WHEN A .dataname LIKE '%ZZXL' THEN '在职学历（ZZXL）'"
				+ "WHEN A .dataname LIKE '%ZZXW' THEN '在职学位（ZZXW）'"
//				+ "WHEN A .dataname LIKE '%SORTID' THEN '排序号'"	
//				+ "WHEN A .dataname LIKE '%UPDATED' THEN '校验标识'"
				//+ ""	
				//+ ""	
				//+ ""	
				+"else A .dataname END ) dataname,";
		String sql = "a.oldvalue,a.newvalue,a.changedatetime"
				+ " from LOG_DETAIL a where a.system_log_id='"+mainid+"'"
				+ " and a.dataname!='A0000'"
				+ " and a.dataname!='A0200'"
				+ " and a.dataname!='A0600'"
				+ " and a.dataname!='A0500'"
				+ " and a.dataname!='A0800'"
				+ " and a.dataname!='A1400'"
				+ " and a.dataname!='A1500'"
				+ " and a.dataname!='A3600'"
				+ " and a.dataname!='N2900'"
				+ " and a.dataname!='A1527'"; //A1527不知道给的数据库备注写的啥
		sql=sqlStart+sql;
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * 子日志信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridAudit.dogridquery")
	public int gridAuditQuery(int start, int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select adt00,a0000,adt01,userid,adt02,adt03,"
				+ "(select username from smt_user u where u.userid=t.userid) username from A01_AUDIT t where a0000='"+a0000+"'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 刷新子日志
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridMain.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{
		String mainid = String.valueOf(this.getPageElement("gridMain").getValueList().get(this.getPageElement("gridMain").getCueRowIndex()).get("system_log_id"));
		this.getPageElement("system_log_id").setValue(mainid);
		this.setNextEventName("gridDeatil.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 下拉框数据
	 * @return
	 * @throws RadowException 
	 */
	@SuppressWarnings("unchecked")
	public String getSelectData(String type) throws RadowException{
		/*
		select userid,username from smt_user where exists(
			select userid from (select userid from competence_userdept where B0111 like (
			select B0111||'%' from (
			select B0111 from competence_userdept where userid =:userid order by length(B0111)
			) where rownum=1
			) group by userid) temp where temp.userid=smt_user.userid
			)
		*/
		
		StringBuffer data = new StringBuffer();
		HBSession session = HBUtil.getHBSession();
		String sql = null;
		List<Object[]> list = null;
		SQLQuery query = null;
		if("1".equals(type)){
			sql = "select userid,username from smt_user";
		} else if ("2".equals(type)){
			sql = "select code_name,code_name from code_value where code_type='OPERATE_TYPE' and ( code_value like '3%' or code_value like 'n%' )";
		} else if ("3".equals(type)){
			sql = "select code_name,code_name from code_value where code_type='TABLE_NAME'";
		}
		query = session.createSQLQuery(sql);
		list = query.list();
		for (Object[] info : list) {
			data.append("['"+info[0]+"','"+info[1]+"'],");
		}
		return data.substring(0, data.length()-1);
	}
	
}
