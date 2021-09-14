package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GroupSqlResPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		this.getExecuteSG().addExecuteCode("init();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initpage")
	public int initpage(String result) throws RadowException, AppException {
		CommQuery cq=new CommQuery();
		String sql="select funcexpress from EXPRESSFUNC where functype='1'";
		List<HashMap<String,Object>> liststr=cq.getListBySQL(sql);
		HashMap<String, Object> mapstr=new LinkedHashMap<String, Object>();
		for(int i=0;i<liststr.size();i++){
			mapstr.put(liststr.get(i).get("funcexpress").toString(), liststr.get(i).get("funcexpress"));
		}
		this.getPageElement("strfunc").setValue(null);
		((Combo)this.getPageElement("strfunc")).setValueListForSelect(mapstr);
		sql="select * from EXPRESSFUNC where functype='2'";
		List<HashMap<String,Object>> listdate=cq.getListBySQL(sql);
		HashMap<String, Object> mapdate=new LinkedHashMap<String, Object>();
		for(int i=0;i<listdate.size();i++){
			mapdate.put(listdate.get(i).get("funcexpress").toString(), listdate.get(i).get("funcexpress"));
		}
		this.getPageElement("datefunc").setValue(null);
		((Combo)this.getPageElement("datefunc")).setValueListForSelect(mapdate);
		sql="select * from EXPRESSFUNC where functype='3'";
		List<HashMap<String,Object>> listnum=cq.getListBySQL(sql);
		HashMap<String, Object> mapnum=new LinkedHashMap<String, Object>();
		for(int i=0;i<listnum.size();i++){
			mapnum.put(listnum.get(i).get("funcexpress").toString(), listnum.get(i).get("funcexpress"));
		}
		this.getPageElement("numberfunc").setValue(null);
		((Combo)this.getPageElement("numberfunc")).setValueListForSelect(mapnum);
		sql="select * from EXPRESSFUNC where functype='4'";
		List<HashMap<String,Object>> listoper=cq.getListBySQL(sql);
		HashMap<String, Object> mapoper=new LinkedHashMap<String, Object>();
		for(int i=0;i<listoper.size();i++){
			mapoper.put(listoper.get(i).get("funcexpress").toString(), listoper.get(i).get("funcexpress"));
		}
		this.getPageElement("operatfunc").setValue(null);
		((Combo)this.getPageElement("operatfunc")).setValueListForSelect(mapoper);
		
		String[] arr= result.split(";");
		HashMap<String, Object> mapres=new LinkedHashMap<String, Object>();
		for(int i=1;i<arr.length;i++){
			String[] arrval=arr[i].trim().split(" ");
			mapres.put(arrval[0].trim(), arr[i].trim());
		}
		this.getPageElement("databaseco").setValue(null);
		((Combo)this.getPageElement("databaseco")).setValueListForSelect(mapres);
		
		String ctci=this.getPageElement("ctci").getValue();
		sql="select resexpress,expressdesc from RESULTSPELL where ctci='"+ctci+"'";
		List<HashMap<String,Object>> listexpress=cq.getListBySQL(sql);
		if(listexpress!=null&&listexpress.size()>0){
			this.getPageElement("spellexpress").setValue(entryStr(listexpress.get(0).get("resexpress")));
			this.getPageElement("expexplain").setValue(entryStr(listexpress.get(0).get("expressdesc")));
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("spellexp")
	public void spellexp(String result) throws RadowException  {
		String[] arr= result.split(";");
		String tablename="";
		for(int i=1;i<arr.length;i++){
			String[] arrval=arr[i].trim().split("\\.");
			if(tablename.contains(arrval[0].trim())){
				
			}else{
				if(i==1){
					tablename=tablename+arrval[0].trim();
				}else{
					tablename=tablename+","+arrval[0].trim();
				}
			}
		}
		String spellsql="select  "+this.getPageElement("spellexpress").getValue()+"  from  "+tablename ;
		CommQuery cq=new CommQuery();
		try {
			cq.getListBySQL(spellsql);
			this.setMainMessage("校验成功");
		} catch (AppException e) {
			this.setMainMessage("表达式非法");
		}
	}
	
	@PageEvent("saveSqlExpress")
	public void saveSqlExpress(String result) throws RadowException, SQLException {
		String[] arr= result.split(";");
		String tablename="";
		for(int i=1;i<arr.length;i++){
			String[] arrval=arr[i].trim().split("\\.");
			if(tablename.contains(arrval[0].trim())){
				
			}else{
				if(i==1){
					tablename=tablename+arrval[0].trim();
				}else{
					tablename=tablename+","+arrval[0].trim();
				}
			}
		}
		String resexpress=entryStr(this.getPageElement("spellexpress").getValue());
		String expressdesc=entryStr(this.getPageElement("expexplain").getValue());
		String spellsql="select  "+resexpress+"  from  "+tablename ;
		CommQuery cq=new CommQuery();
		try {
			cq.getListBySQL(spellsql);
			String ctci=this.getPageElement("ctci").getValue();
			String sql="delete from RESULTSPELL where ctci='"+ctci+"'";
			//HBSession hbsess = HBUtil.getHBSession();	
			//Statement  stmt = hbsess.connection().createStatement();
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
//			stmt.executeUpdate(sql);
			resexpress=resexpress.replace("\'", "''");
			sql="insert into RESULTSPELL(ctci,resexpress,expressdesc)values('"+ctci+"','"+resexpress+"','"+expressdesc+"')";
			HBUtil.getHBSession().createSQLQuery(sql).executeUpdate();
//			stmt.executeUpdate(sql);
//			stmt.close();
			this.getExecuteSG().addExecuteCode("closewin();");
		} catch (AppException e) {
			this.setMainMessage("表达式非法");
		}
	}
	
	/**
	 * 为null转换为空字符串
	 * @param obj
	 * @return
	 */
	public String entryStr(Object obj){
		String result="";
		if(obj!=null){
			result=obj.toString();
		}
		return result;
	}

}
