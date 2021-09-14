package com.insigma.siis.local.pagemodel.customquery;


import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class addHistoryMDPageModel extends PageModel {

	//“≥√Ê≥ı ºªØ
	@Override
	public int doInit() throws RadowException {
		CommQuery cqbs=new CommQuery();
		String userid=SysManagerUtils.getUserId();
		String sql="select mdid,mdmc,"
				+ "(select  (select username from smt_user where userid=u.mnur02) from hz_LSMD_userref u where mnur01='"+userid+"' and y.mdid=u.mdid) mnur02"
				+ " from historyMd y where (locked<> '1' or locked is null)  and (userid='"+userid+"'   "
				+ " or exists (select 1 from hz_LSMD_userref u where mnur01='"+userid+"' and u.mdid=y.mdid))";
		List<HashMap<String, Object>> list;
		try {
			list = cqbs.getListBySQL(sql);
			HashMap<String, Object> map=new LinkedHashMap<String, Object>();
			for(int i=0;i<list.size();i++){
				String mdid = list.get(i).get("mdid").toString();
				String mdmc = list.get(i).get("mdmc").toString();
				Object mnur02 = list.get(i).get("mnur02");
				if(mnur02!=null&&!StringUtils.isEmpty(mnur02.toString())){
					mdmc = mdmc+"("+mnur02+")";
				}
				map.put(mdid, mdmc);
			}
			((Combo)this.getPageElement("mdtype")).setValueListForSelect(map);
			this.createPageElement("mdtype2", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(true);
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("save1")
	public int save1(String str) throws RadowException {
		//this.saveUserRef(str);
		this.getExecuteSG().addExecuteCode("parent.addHistoryPer('"+str+"');window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setMnur01")
	public int setMnur01() throws RadowException {
		String userid=SysManagerUtils.getUserId();
		String mdtype =  this.getPageElement("mdtype").getValue();
		
//		List mdlist = HBUtil.getHBSession().createSQLQuery("select 1 from historyMd y where userid='"+userid+"' and mdid='"+mdtype+"'").list();
//		if(mdlist.size()==0){
//			//this.getPageElement("mnur01_combotree").setValue("");
//			//this.getPageElement("mnur01").setValue("");
//			this.getExecuteSG().addExecuteCode("$('#tr3').hide();");
//		}else{
//			this.getExecuteSG().addExecuteCode("$('#tr3').show();");
//		}
//		String sql = "select listagg (T.mnur01, ',') WITHIN GROUP (ORDER BY mnur05) k,  "
//				+ "listagg (T.mnur04, ',') WITHIN GROUP (ORDER BY mnur05) v "
//				+ "from hz_LSMD_userref t where mdid='"+mdtype+"' and mnur02='"+userid+"'";
//
//		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
//		if(list.size()>0){
//			Object[] o = list.get(0);
//			this.getPageElement("mnur01_combotree").setValue(o[1]==null?"":o[1].toString());
//			this.getPageElement("mnur01").setValue(o[0]==null?"":o[0].toString());
//		}else{
//			this.getPageElement("mnur01_combotree").setValue("");
//			this.getPageElement("mnur01").setValue("");
//		}
		
		String sql = "select mdtype mdtype2,bqtype  from historyMd where mdid='"+mdtype+"'";

		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list.size()>0){
			Object[] o = list.get(0);
			this.getPageElement("mdtype2").setValue(o[0]==null?"":o[0].toString());
			this.getPageElement("bqtype").setValue(o[1]==null?"":o[1].toString());
		}
		this.createPageElement("mdtype2", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(true);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save2")
	public int save2(String str) throws Exception {
		String userid=SysManagerUtils.getUserId();
		String uuid=UUID.randomUUID().toString().replaceAll("-", "");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String createdate = sf.format(System.currentTimeMillis());
		String mdtype2 =  this.getPageElement("mdtype2").getValue();
		String bqtype =  this.getPageElement("bqtype").getValue();
		String sql="insert into historyMd(mdid,mdmc,userid,createdate,mdtype,bqtype) values ('"+uuid+"','"+str+"','"+userid+"','"+createdate+"','"+mdtype2+"','"+bqtype+"')";
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeUpdate(sql);
		//this.saveUserRef(uuid);
		this.getExecuteSG().addExecuteCode("parent.addHistoryPer('"+uuid+"');window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setDisable")
	public int setDisable(String str) throws Exception {
		if("1".equals(str)) {
			this.createPageElement("mdtype2", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(true);
		}else if("2".equals(str)){
			this.createPageElement("mdtype2", ElementType.SELECT, false).setDisabled(false);
			this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(true);
		}else if("3".equals(str)){
			this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(false);
		}else if("4".equals(str)){
			this.createPageElement("bqtype", ElementType.SELECT, false).setDisabled(true);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private void saveUserRef(String mdid) throws RadowException{
		String curuserid=SysManagerUtils.getUserId();
		String mnur01 = this.getPageElement("mnur01").getValue();
		String mnur01_combotree = this.getPageElement("mnur01_combotree").getValue();
		try {
			HBUtil.executeUpdate("delete from hz_LSMD_userref where mdid=? and mnur02=?",new Object[]{mdid,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into hz_LSMD_userref(mnur00,mdid,mnur01,mnur02,mnur04,mnur05) "
							+ "values(sys_guid(),?,?,?,?,?)",new Object[]{mdid,mnur01s[i],curuserid,mnur01_combotrees[i],i});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
