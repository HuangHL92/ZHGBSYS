package com.insigma.siis.local.pagemodel.comm;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.data.PMHList;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.ObjectUtil;
import com.insigma.odin.framework.sys.comm.CommQueryBS;

public class CPDelPageModel extends PageModel {

	/**
	 * 查询的sql
	 * Ab01 aab001 村社区编码 AAZ001 组织内码 AAB004 村社区名称 AAB301 所属行政区域代码 EAB014 组织类型 AAF015 所属街道 EAB026 组织状态 
	 */
	private final static String sql = "select aab001,aaz001,aab004,aab301,eab014,aaf015,eab026"
		+ " from ab01 a " + " where 1=1 /*1*/ ";
	
	@Override
	public int doInit() throws RadowException {
		this.isShowMsg=false;
		this.setNextEventName("revokegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("cpquery.ontriggerclick")
	public int cpqueryOnTriggerClick(String params) throws RadowException{
		this.isShowMsg=false;
		PageElement pe = this.getPageElement("queryPanel.aaf015_a");
		PageElement pe2 = this.getPageElement("queryPanel.cpquery");
		if(pe==null&&pe.getValue().equals("")){
			if(pe2==null&&pe2.getValue().equals("")){
				this.setMainMessage("请先输入搜索条件");
			}
		}
		this.setNextEventName("revokegrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("doReset.onclick")
	public int doResetOnClick() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	@PageEvent("doSave.onclick")
	public int doSaveOnClick() throws RadowException{
		PMHList  pmlist = new PMHList();
		PageElement pe = this.getPageElement("div_2");
		List<HashMap<String,String>> list = pe.getStringValueList();
		for(int i=0;i<list.size();i++){
			if(ObjectUtil.equals(pe.getValue("eae204", i), "")){
				this.setMainMessage("村没有选择变动原因，请选择再保存！");
				return EventRtnType.FAILD;
			}
			HashMap<String,String> map = list.get(0);
			String logchecked = map.get("logchecked");
			if(logchecked.equals("1")){
				String aaz001 = this.getPageElement("div_2").getStringValue("aaz001", i);
				String sql = "select count(*) cn from sbdv_ac20 where aac008!='3' and aaz001='"
					+ aaz001 + "' ";
				pmlist.setSql(sql);
				Long count = pmlist.getLong("cn");
				if (count > 0) {
					this.setMainMessage(this.getPageElement("div_2").getValue("aab004",i)
							+ "村还存在参保人员，不符合注销条件，请先将参保人员全部分流到其他村！");
					return EventRtnType.FAILD;
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("revokegrid.dogridquery")
	@EventDataRange("queryPanel")
	public int revokegriddoQuery(int start,int limit) throws RadowException{
		CommQueryBS commBS = new CommQueryBS();
		PageElement pe = this.getPageElement("queryPanel.aaf015_a");
		PageElement pe2 = this.getPageElement("queryPanel.cpquery");
		String newsql = sql;
		if(pe!=null&&!pe.getValue().equals("")){
			if(pe2!=null&&!pe2.getValue().equals("")){
				newsql = newsql.replace("/*1*/", " and a.aaf015 like '%" + pe.getValue() +"%'  and  a.aab004 like '%"+ pe2.getValue()+"%' /*1*/");
			}else{
				newsql = newsql.replace("/*1*/", " and a.aaf015 like '%" + pe.getValue() +"%' /*1*/");
			}
		}else{
			if(pe2!=null&&!pe2.getValue().equals("")){
				newsql = newsql.replace("/*1*/", " and a.aab004 like '%" + pe2.getValue() +"%' /*1*/");
			}
		}
		try {
			PageQueryData pd = commBS.query(newsql, "SQL", start, limit);
			this.setSelfDefResData(pd);
		} catch (AppException e) {
			this.isShowMsg=true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.SPE_SUCCESS;
	}
	
}
