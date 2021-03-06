package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.base.DWInfoUtil;
import com.insigma.siis.local.pagemodel.mntpsj.base.FABDUtil;
import com.insigma.siis.local.pagemodel.mntpsj.base.SHUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MNTPSJOPPageModel extends PageModel{

	
	Map<String, String> JIAO_LIU_MING_DAN = new HashMap<String, String>();
	{
		CommQuery cqbs=new CommQuery();
		String sql="select t.* from EXCHANGE_LIST t where el01='2021'";
		try {
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
			if (list != null && list.size() > 0) {
				for (HashMap<String, String> map1 : list) {
					JIAO_LIU_MING_DAN.put(map1.get("a0000"), map1.get("el03"));
				}
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int doInit() throws RadowException {
		CommQuery cqbs=new CommQuery();
		String sql="select wayid,wayname from ZDGW_WAY order by sortid";
		List<HashMap<String, Object>> list;
		try {
			list = cqbs.getListBySQL(sql);
			Map<String, String> map = new LinkedHashMap<String, String>();
			if (list != null && list.size() > 0) {
				for (HashMap<String, Object> map1 : list) {
					map.put(map1.get("wayid").toString(), map1.get("wayname").toString());
				}
			}
			((Combo)this.getPageElement("gwmc")).setValueListForSelect(map);
			
			
			
		}catch (Exception e) {
			this.setMainMessage("????????????????????????????");
			e.printStackTrace();
		}
		
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		try{
			String fabd00 = this.getPageElement("fabd00").getValue();
			String userid = SysManagerUtils.getUserId();
			
			if(!FABDUtil.editable(userid, fabd00)){
				NextEvent ne = new NextEvent();
				ne.setNextEventValue(NextEventValue.CANNEL);
				ne.setNextBackFunc("window.top.location.href= g_contextpath+'/';");
				this.addNextEvent(ne);
				NextEvent nec = new NextEvent();
				nec.setNextEventValue(NextEventValue.YES);
				nec.setNextBackFunc("window.top.location.href= g_contextpath+'/';");
				this.addNextEvent(nec);
				this.setMessageType(EventMessageType.CONFIRM); // ??????????????confirm????????
				this.setMainMessage("????????????"); // ????????????
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			String fabd02 = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String fabd05 = HBUtil.getValueFromTab("fabd05", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String fabd06 = HBUtil.getValueFromTab("fabd06", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String mntp05 = HBUtil.getValueFromTab("mntp05", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list = cqbs.getListBySQL(
					"select b0111,b0101 from b01 where b0121='001.001.004' "
					+ " and b0111 in (select substr(b0111,0,15) from HZ_MNTP_SJFA_ORG where famx00 in "
					+ " (select famx00 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"') and status='1' and length(b0111)=19) order by b0269");
			Map<String, String> map = new LinkedHashMap<String, String>();
			String fb0111=this.getPageElement("selorg").getValue();
			if (list != null && list.size() > 0) {
				map.put("????", "??????????");
				for (HashMap<String, Object> map1 : list) {
					map.put(map1.get("b0111").toString(), map1.get("b0101").toString());
				}
				
				if(StringUtils.isEmpty(fb0111)&&"2".equals(mntp05)){
					fb0111 = list.get(0).get("b0111").toString();
				}
				
			}
			//??????????????????
			((Combo)this.getPageElement("selorg")).setValueListForSelect(map);
			if("2".equals(mntp05)&&"1".equals(fabd05)){//??????????????
				this.getPageElement("selorg").setValue(fb0111);
			}else{
				this.getPageElement("selorg").setValue("");
			}
			
			
			list = cqbs.getListBySQL(
					"select famx00,famx03 from HZ_MNTP_SJFA_famx t where fabd00='"+fabd00+"' and famx01='2' order by famx02");
			map = new LinkedHashMap<String, String>();
			String selfamx00=this.getPageElement("selfamx00").getValue();
			if (list != null && list.size() > 0) {
				int i=1;
				map.put("????", "????????");
				for (HashMap<String, Object> map1 : list) {
					map.put(map1.get("famx00").toString(), "????????"+i++);
				}
				if(StringUtils.isEmpty(selfamx00)){
					selfamx00 = list.get(0).get("famx00").toString();
				}
			}
			//??????????????????
			((Combo)this.getPageElement("selfamx00")).setValueListForSelect(map);
			this.getPageElement("selfamx00").setValue(selfamx00);
			
			
			
			
			this.getPageElement("fabd05").setValue(fabd05);
			this.getPageElement("mntp05").setValue(mntp05);
			this.getPageElement("fabd02").setValue(fabd02);
			//??????????????????
			if("1".equals(fabd05)){
				if("1".equals(fabd06)){//????????
					this.getExecuteSG().addExecuteCode("$('.DWSZ').show();Ext.getCmp('selfamx00_combo').show();");
				}else{//????????
					this.getExecuteSG().addExecuteCode("$('.DWSZ').show();Ext.getCmp('mntp05_combo').show();Ext.getCmp('selfamx00_combo').show();");
				}
			}else if("2".equals(fabd05)){//????????
				this.getExecuteSG().addExecuteCode("$('.XZGW').show();");
			}
			if("2".equals(mntp05)&&"1".equals(fabd05)){//??????????????
				this.getExecuteSG().addExecuteCode("Ext.getCmp('selorg_combo').show();");
			}else{
				this.getExecuteSG().addExecuteCode("Ext.getCmp('selorg_combo').hide();");
			}
			this.getExecuteSG().addExecuteCode("infoSearch();$('.QXS').show();");
		} catch (Exception e) {
			this.setMainMessage("??????????");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("showData")
	public int showData() throws Exception{
		StopWatch w = new StopWatch();
		w.start();
		String fabd00 = this.getPageElement("fabd00").getValue();
		String selfamx00 = this.getPageElement("selfamx00").getValue();
		String selorg = this.getPageElement("selorg").getValue();
		
		// ????????    ????????  ????????
		//???????????? ????
		List<List<Map<String, Object>>> b0111s = MNTPSJDWPageModel.returnFA(fabd00,selorg,selfamx00);
		//String[][] b0111s = new String[][]{{"001.001.004.001.001","001.001.004.001.002"},{"001.001.004.001.001","001.001.004.001.002"}};
		
		List<List<Map<String, String>>> tableData = new ArrayList<List<Map<String, String>>>();
		
		int cfgLength = FABDUtil.getLength(b0111s);
		
		TableCount tableCount = new TableCount();
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fabd05 = this.getPageElement("fabd05").getValue();
		tableCount.init(fabd05, mntp05);
		//????   ??????????????
		for(int jgi=0;jgi<b0111s.size();jgi++){
			Map<String, Object> cfgMap = b0111s.get(jgi).get(0);
			String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
			String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
			if("1".equals(famx01)){
				addXZTitle2(tableData,tableCount,famx00);
			}else if("2".equals(famx01)){
				addTPTitle2(tableData,tableCount,famx00);
			}
			tableCount.rowPointer=0;
			tableCount.rowStart=0;
		}
		
		tableCount.rowPointer=1;
		tableCount.rowStart=1;
		//??????????  ????????????????????????????????    //????????????????
		for(int i=0;i<cfgLength;i++){
			
			//????????  ????   //????????
			genDataMap(b0111s, tableData, i, fabd00, tableCount,selfamx00);
		}
		int tw = this.getTableWidth(b0111s);
		JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(tableData);
		this.getExecuteSG().addExecuteCode("showData("+updateunDataStoreObject2.toString()+","+tw+");");
		
		w.stop();
		System.out.println(w.elapsedTime());
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????table??????
	 * @param b0111s
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	private int getTableWidth(List<List<Map<String, Object>>> b0111s) throws RadowException, AppException{
		TableCount tableCount = new TableCount();
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fabd05 = this.getPageElement("fabd05").getValue();
		tableCount.init(fabd05, mntp05);
		List<List<Map<String, String>>> tableData = new ArrayList<List<Map<String, String>>>();
		for(int jgi=0;jgi<b0111s.size();jgi++){
			Map<String, Object> cfgMap = b0111s.get(jgi).get(0);
			String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
			if("1".equals(famx01)){
				addXZTitle(tableData,tableCount);
			}else if("2".equals(famx01)){
				addTPTitle(tableData,tableCount);
			}
			tableCount.rowPointer=0;
			tableCount.rowStart=0;
		}
		return tableCount.tableWidth;
	}

	private void genDataMap(List<List<Map<String, Object>>> b0111s,List<List<Map<String, String>>> tableData,
			int i, String fabd00,TableCount tableCount, String selfamx00) throws AppException, RadowException{

		tableCount.tableWidth = 0;
		//????????  ????   //????????
		for(int jgi=0;jgi<b0111s.size();jgi++){
			List<Map<String, Object>> b0111Block = b0111s.get(jgi);
			//????????????????   ????????????????????????????
			if(b0111Block.size()>i){
				Map<String, Object> cfgMap = b0111Block.get(i);
				String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
				String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
				String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
				if("1".equals(famx01)){
					List<HashMap<String, Object>> dataMap = FABDUtil.getXZXXSZ(b0111,fabd00,famx00,selfamx00);
					if(dataMap.size()==0){
						addXZempty(tableData,tableCount,i);
					}else{
						addXZData(tableData,tableCount,dataMap,i,cfgMap);
					}
					
				}else if("2".equals(famx01)){
					List<HashMap<String, Object>> dataMap = FABDUtil.getTPXX(b0111,famx00);
					if(dataMap.size()==0){
						addTPempty(tableData,tableCount,i);
					}else{
						addTPData(tableData,tableCount,dataMap,i,cfgMap);
					}
				}
				
			}else{
				Map<String, Object> cfgMap = b0111Block.get(0);
				String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
				if("1".equals(famx01)){
					addXZempty(tableData,tableCount,i);
				}else if("2".equals(famx01)){
					addTPempty(tableData,tableCount,i);
				}
				
			}
			
			//????????????????
			if(jgi!=b0111s.size()-1){
				tableCount.rowPointer = tableCount.rowStart;
			}else{
				tableCount.rowStart = tableCount.rowPointer;
			}
			
		}
		//?????????????? ????????????????
		if(i==0){
			tableCount.TABLEWIDTH = tableCount.tableWidth;
		}
		//??????
		tableCount.finish();
	
	}

	@PageEvent("showDataSyn")
	public int showDatasyn(String pi) throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String selorg = this.getPageElement("selorg").getValue();
		String selfamx00 = this.getPageElement("selfamx00").getValue();
		if(StringUtils.isEmpty(pi)){
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] piArray = pi.split("@@");
		int i = Integer.valueOf(piArray[0]);
		
		// ????????    ????????  ????????
		//???????????? ????
		List<List<Map<String, Object>>> b0111s = MNTPSJDWPageModel.returnFA(fabd00,selorg,selfamx00);
		List<List<Map<String, String>>> tableData = new ArrayList<List<Map<String, String>>>();
		
		//int cfgLength = FABDUtil.getLength(b0111s);
		
		TableCount tableCount = new TableCount();
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fabd05 = this.getPageElement("fabd05").getValue();
		tableCount.init(fabd05, mntp05);
			//????????  ????   //????????
		genDataMap(b0111s, tableData, i, fabd00, tableCount,selfamx00);
				
		int tw = this.getTableWidth(b0111s);	
		JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(tableData);
		this.getExecuteSG().addExecuteCode("showDataSyn.showDataSyn("+updateunDataStoreObject2.toString()+","+piArray[1]+","+tw+");");
		return EventRtnType.NORMAL_SUCCESS;
	}
	




	private void addTPempty(List<List<Map<String, String>>> tableData, TableCount tableCount, int i) {
		//??????
		if(tableCount.rowMaxCount==-1){
			tableCount.rowMaxCount = 0;
		}else if(tableCount.rowMaxCount==0){
			
		}else{
			addOffSetMapSel(tableData.size()-tableCount.rowStart,tableCount.TP_COLS,tableData,tableCount,i);
		}
		//??????????
		//tableCount.colPointer =  tableCount.colPointer + tableCount.TP_COLS;
		//??????????????????
		tableCount.colCountOffset = tableCount.colCountOffset + tableCount.TP_COLS;
		
	}



	/**
	 * 1????????????  ????????????????0??   ??????????????????????????????????
	 * 2?????????????? ??????????????  ????????
	 * 3????????  ??????????
	 * @param tableData
	 * @param tableCount
	 * @param i 
	 */
	private void addXZempty(List<List<Map<String, String>>> tableData, TableCount tableCount, int i) {
		
		
		//??????
		if(tableCount.rowMaxCount==-1){
			tableCount.rowMaxCount = 0;
		}else if(tableCount.rowMaxCount==0){
			
		}else{
			addOffSetMapSel(tableData.size()-tableCount.rowStart,tableCount.XZ_COLS,tableData,tableCount,i);
		}
		//??????????
		//tableCount.colPointer =  tableCount.colPointer + tableCount.XZ_COLS;
		//??????????????????
		tableCount.colCountOffset = tableCount.colCountOffset + tableCount.XZ_COLS;
	}



	/**
	 * 1????????????????????   ????????  ????????
	 * 2????????  ??????????
	 * 3???????? ????????
	 * 4??????????????  ???????? ????????
	 * @param tableData
	 * @param tableCount
	 * @param dataMap
	 * @param famx00 
	 * @param dwIndex 
	 * @param cfgMap 
	 * @throws AppException
	 * @throws RadowException 
	 */
	private void addXZData(List<List<Map<String, String>>> tableData, TableCount tableCount,
			List<HashMap<String, Object>> dataMap,  int dwIndex, Map<String, Object> cfgMap) throws AppException, RadowException {
		String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
		String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
		String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fabd05 = this.getPageElement("fabd05").getValue();
		int merge = dataMap.size()+tableCount.DATA_MERGE_OFFSET;
		
		for(int i=0; i<dataMap.size(); i++){
			HashMap<String, Object> m = dataMap.get(i);
			//?????? ??????  ????????
			if(i==0){
				//??????????????
				//tableCount.colMergeList.add(tableCount.colPointer);
				//????????
				//??????????
				//tableCount.rowPointer = tableCount.rowPointer + merge;
				
				//??????????????????
				if(tableCount.rowMaxCount==-1){
					tableCount.rowMaxCount = merge;
				}
				//??????????
				if(merge>tableCount.rowMaxCount){
					//??????????????
					int rowOffset = merge - tableCount.rowMaxCount;
					if(tableCount.rowMaxCount==0){
						addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount,dwIndex);
					}else{
						addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount,-1);
					}
					
					tableCount.rowMaxCount = merge;
					
				}
				//??????????
				//tableCount.colPointer =  tableCount.colPointer + tableCount.XZ_COLS;
				//??????????????????
				tableCount.colCountOffset = tableCount.colCountOffset + tableCount.XZ_COLS;
				
				
			}
			
			
			
			List<Map<String, String>> rows = null;
			//??????
			if(tableCount.rowPointer<tableData.size()){
				rows = tableData.get(tableCount.rowPointer);
			}else{
				rows = new ArrayList<Map<String, String>>();
				tableData.add(rows);
			}
			
			String qx = m.get("qx")==null?"":m.get("qx").toString();
			
			String clickdw = qx;
			if(qx.startsWith("001.001.004")){
				clickdw = qx.substring(0,15);
			}
			
			String a0215a = m.get("a0215a")==null?"":m.get("a0215a").toString();
			String a0101 = m.get("a0101")==null?"":m.get("a0101").toString();
			String a0107 = m.get("a0107")==null?"":m.get("a0107").toString();
			String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
			String a0288 = m.get("a0288")==null?"":m.get("a0288").toString();
			String a0192f = m.get("a0192f")==null?"":m.get("a0192f").toString();
			String a0200 = m.get("a0200")==null?"":m.get("a0200").toString();
			
			tableCount.rowPointer++;
			Map<String, String> cellMap = new HashMap<String, String>();
			//????????
			if(i==0){
			
				String qxmc = b0101;
				
				
				cellMap.put("text", qxmc);
				cellMap.put("colspan", tableCount.XZ_COLS+"");
				cellMap.put("sclass", "dwxz unit titleColor classBR classBT");
				cellMap.put("dwIndex", dwIndex+"");
				cellMap.put("qx", clickdw);
				cellMap.put("famx01", famx01);
				cellMap.put("b0111", qx);
				rows.add(cellMap);
				
				
				addXZTitle(tableData, tableCount);
				//??????
				if(tableCount.rowPointer<tableData.size()){
					rows = tableData.get(tableCount.rowPointer);
				}else{
					rows = new ArrayList<Map<String, String>>();
					tableData.add(rows);
				}
				tableCount.rowPointer++;
			}
			//????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0215a);
			cellMap.put("a0000", a0000);
			rows.add(cellMap);
			//????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0101.replace("\n", "<br/>"));
			cellMap.put("a0000", a0000);
			cellMap.put("a0200", a0200);
			if(StringUtils.isEmpty(a0000)){
				//cellMap.put("sclass", "name");
			}else{
				
				if(StringUtils.isEmpty(JIAO_LIU_MING_DAN.get(a0000))){
					cellMap.put("sclass", "name moveFrom");
				}else{
					cellMap.put("sclass", "name moveFrom jlmd");
					cellMap.put("jlmdtext", JIAO_LIU_MING_DAN.get(a0000));
				}
			}
			
			rows.add(cellMap);
			//????????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0107);
			rows.add(cellMap);
			
			//????????
			if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
				//??????????
				cellMap = new HashMap<String, String>();
				cellMap.put("text", a0192f);
				rows.add(cellMap);
				
				//????????????????
				cellMap = new HashMap<String, String>();
				cellMap.put("text", a0288);
				rows.add(cellMap);
			}
			
			
			
			//String liuren = m.get("liuren")==null?"":m.get("liuren").toString();
			String texttpyj2 =  m.get("tpdesc")==null?"":m.get("tpdesc").toString();
			String tpyjid =  m.get("tpyjid")==null?"":m.get("tpyjid").toString();
			
			
			
			String quxiang = m.get("quxiang")==null?"":m.get("quxiang").toString();
			String textqx = quxiang;
			/********************????????????**************************/
			//String texttpyj = "";
			//String tiba = "";
			//String quxianga0201e = m.get("quxianga0201e")==null?"":m.get("quxianga0201e").toString();
			//String a0201e = m.get("a0201e")==null?"":m.get("a0201e").toString();
			/*if("".equals(a0201e)&&!"".equals(quxianga0201e)){
				tiba = "????";
			}
			//??????????
			if(!"1".equals(a0201e)&&"1".equals(quxianga0201e)){
				tiba = "????";
			}
			
			if("1".equals(liuren)){
				if(!"".equals(tiba)){
					texttpyj = "????" + tiba;
				}else{
					texttpyj = "????";
				}
			}else if("2".equals(liuren)){
				if(!"".equals(tiba)){
					texttpyj = "????" + tiba;
				}else{
					texttpyj = "????????";
				}
			}
			
			if(!StringUtils.isEmpty(quxiang)){
				if(!StringUtils.isEmpty(texttpyj)){
					texttpyj += ",";
				}
				texttpyj += "????" + tiba;
				
			}
			
			if(StringUtils.isEmpty(texttpyj)&&!StringUtils.isEmpty(a0000)){
				texttpyj = "????";
			}*/
			
			//????
			String[] results = FABDUtil.getMatcher("??[0-9a-zA-Z\\.]{0,}??", textqx);
			String zgxl = m.get("zgxl")==null?"":m.get("zgxl").toString();
			String qrzxl = m.get("qrzxl")==null?"":m.get("qrzxl").toString();
			//????????
			cellMap = new HashMap<String, String>();
			//cellMap.put("text", "<button type='button' onclick=\"openQuXiangDanWei('"+a0000+"','"+clickdw+"','"+famx00+"')\" style='margin-top:20px;'>????</button>");
			cellMap.put("text", texttpyj2);
			cellMap.put("a0000", a0000);
			cellMap.put("a0200", a0200);
			cellMap.put("qx", qx);
			cellMap.put("famx01", famx01);
			cellMap.put("colname", "TPDESC");
			cellMap.put("zgxl", zgxl.replaceAll("\r|\n", ""));
			cellMap.put("qrzxl", qrzxl.replaceAll("\r|\n", ""));
			cellMap.put("tpyjid", tpyjid);
			cellMap.put("quxiangb0111s", results[0]);
			cellMap.put("a0107", a0107);
			cellMap.put("famx00", famx00);
			//cellMap.put("style", "position:relative ");
			String bkclass = " ";
			/*if(texttpyj.contains("????")){
				bkclass = " tiba ";
			}else if(texttpyj.contains("????")){
				bkclass = " jiaoliu ";
			}else if(texttpyj.contains("????")){
				bkclass = " mianzhi ";
			}*/
			//??????????????????
			if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
				cellMap.put("sclass", "button-tpyj-over input-editor"+bkclass);
			}else{
				cellMap.put("sclass", "button-tpyj-over classBR input-editor"+bkclass);
			}
			rows.add(cellMap);
			
			
			//???????? ????
			if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
				//????
				
				cellMap = new HashMap<String, String>();
				cellMap.put("text", results[1]);
				
				cellMap.put("sclass", "classBR");
				rows.add(cellMap);
			}
			
			
			
			if(i==0){
				/*for(Map<String, String> m1 : rows){
					String className = m1.get("sclass");
					if(StringUtils.isEmpty(className)){
						m1.put("sclass", tableCount.classBT);
					}else if(!className.contains(tableCount.classBT)){
						m1.put("sclass", className+tableCount.classBT);
					}
				}*/
			}
			
			
		}
		
		if(merge<tableCount.rowMaxCount){
			//??????????????
			int rowOffset =  tableCount.rowMaxCount - merge;
			addOffSetMapSel(rowOffset,tableCount.XZ_COLS,tableData,tableCount,-1);
			
		}
		
	}

	private void addTPData(List<List<Map<String, String>>> tableData, TableCount tableCount,
			List<HashMap<String, Object>> dataMap, int dwIndex, Map<String, Object> cfgMap) throws AppException, RadowException {
		
		/*//????????????
		Map<Integer, String> rowspanMapCFG = new HashMap<Integer, String>();
		for(int i=0; i<dataMap.size(); i++){
			HashMap<String, Object> m = dataMap.get(i);
			//????????????
			String gwcount = m.get("gwcount")==null?"":m.get("gwcount").toString();
			//????????
			String rk = m.get("rk")==null?"":m.get("rk").toString();
			//????
			if(Integer.valueOf(gwcount)>=1&&Integer.valueOf(rk)==1){
				
			}else{
				rowspanMapCFG.put(i, "");
			}
			
		}*/
		
		
		
		
		
		
		String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
		String b0101 = cfgMap.get("b0101")==null?"":cfgMap.get("b0101").toString();
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();	
		String fabd05 = this.getPageElement("fabd05").getValue();
		int merge = dataMap.size()+tableCount.DATA_MERGE_OFFSET;
		int mergerowspan = dataMap.size();
		for(int i=0; i<dataMap.size(); i++){
			HashMap<String, Object> m = dataMap.get(i);
			//?????? ??????  ????????
			if(i==0){
				//??????????????
				//tableCount.colMergeList.add(tableCount.colPointer);
				//????????
				//??????????
				//tableCount.rowPointer = tableCount.rowPointer + merge;
				
				//??????????????????
				if(tableCount.rowMaxCount==-1){
					tableCount.rowMaxCount = merge;
				}
				//??????????
				if(merge>tableCount.rowMaxCount){
					//??????????????
					int rowOffset = merge - tableCount.rowMaxCount;
					//?????????????? ??????????????????
					if(tableCount.rowMaxCount==0){
						addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount,dwIndex);
					}else{
						addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount,-1);
					}
					
					tableCount.rowMaxCount = merge;
					
				}
				//??????????
				//tableCount.colPointer =  tableCount.colPointer + tableCount.TP_COLS;
				//??????????????????
				tableCount.colCountOffset = tableCount.colCountOffset + tableCount.TP_COLS;
			}
			
			
			
			List<Map<String, String>> rows = null;
			//??????
			if(tableCount.rowPointer<tableData.size()){
				rows = tableData.get(tableCount.rowPointer);
			}else{
				rows = new ArrayList<Map<String, String>>();
				tableData.add(rows);
			}
			
			String b0111 = m.get("b0111")==null?"":m.get("b0111").toString();
			String jgjc = m.get("jgjc")==null?"":m.get("jgjc").toString();
			String clickdw = b0111;
			if(b0111.startsWith("001.001.004")&&b0111.length()==19){
				clickdw = b0111.substring(0,15);
			}
			String a0215a = m.get("a0215a")==null?"":m.get("a0215a").toString();
			String a0215a_bz = m.get("a0215a_bz")==null?"":m.get("a0215a_bz").toString();
			String a0101 = m.get("a0101")==null?"":m.get("a0101").toString();
			String a0107 = m.get("a0107")==null?"":m.get("a0107").toString();
			String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
			String fxyp00 = m.get("fxyp00")==null?"":m.get("fxyp00").toString();
			String fxyp00_ry = m.get("fxyp00_ry")==null?"":m.get("fxyp00_ry").toString();
			String zwqc00 = m.get("zwqc00")==null?"":m.get("zwqc00").toString();
			String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
			String zgxl = m.get("zgxl")==null?"":m.get("zgxl").toString();
			String a0288 = m.get("a0288")==null?"":m.get("a0288").toString();
			String qrzxl = m.get("qrzxl")==null?"":m.get("qrzxl").toString();
			String tpdesc = m.get("tpdesc")==null?"":m.get("tpdesc").toString();
			String tpdesc2 = m.get("tpdesc2")==null?"":m.get("tpdesc2").toString();
			String tpyjid = m.get("tpyjid")==null?"":m.get("tpyjid").toString();
			String famx00 = m.get("famx00")==null?"":m.get("famx00").toString();
			String gwmc = m.get("gwmc")==null?"":m.get("gwmc").toString();
			//String personStatus = m.get("personstatus")==null?"":m.get("personstatus").toString();
			
			
			tableCount.rowPointer++;
			Map<String, String> cellMap = new HashMap<String, String>();
			//????  ????
			if(i==0){
				
				String qxmc = b0101;
				
				cellMap.put("text", "<span>"+qxmc+"</span>");
				cellMap.put("colspan", tableCount.TP_COLS+"");
				cellMap.put("sclass", "unit titleColor classBR classBT");
				cellMap.put("dwIndex", dwIndex+"");
				
				//???????????? ??????????????????????????
				cellMap.put("style", "position:relative;background-clip:padding-box  ");
				cellMap.put("famx00", famx00);
				cellMap.put("famx01", famx01);
				cellMap.put("qx", clickdw);
				cellMap.put("b0111", b0111);
				rows.add(cellMap);
				
				
				addTPTitle(tableData, tableCount);
				//??????
				if(tableCount.rowPointer<tableData.size()){
					rows = tableData.get(tableCount.rowPointer);
				}else{
					rows = new ArrayList<Map<String, String>>();
					tableData.add(rows);
				}
				tableCount.rowPointer++;
			}
			//????????????
			String gwcount = m.get("gwcount")==null?"":m.get("gwcount").toString();
			//????????
			String rk = m.get("rk")==null?"":m.get("rk").toString();
			//????
			String gw = StringUtils.isEmpty(a0215a_bz)?a0215a:a0215a_bz;
			if(Integer.valueOf(gwcount)>=1&&Integer.valueOf(rk)==1){
				cellMap = new HashMap<String, String>();
				cellMap.put("rowspan", gwcount);
				cellMap.put("text", gw+
						"<img onclick=\"editOpenGWWin($(this).parent())\" class=\"btn_editor\" src=\"pages/mntpsj/resourse/edit.jpg\" role=\"button\" title=\"????????\"/>"
						+"<a href=\"javascript:void(0)\" onclick=\"radow.doEvent('delgw','"+fxyp00+"');\" class=\"btn_minus\" role=\"button\" title=\"????????\"></a>"
						);
				cellMap.put("fxyp00", fxyp00);
				cellMap.put("gwmc", gwmc);
				cellMap.put("zwqc00", zwqc00);
				
				cellMap.put("style", "position:relative ");
				cellMap.put("sclass", "gwtj moveTo delIconGW ");//????????
				
				cellMap.put("a0000", a0000);
				rows.add(cellMap);
			}
			
			//????
			cellMap = new HashMap<String, String>();
			String p = fxyp00_ry+"@@"+a0000;
			if(!StringUtils.isEmpty(a0000)){
				cellMap.put("text", a0101.replace("\n", "<br/>")+"<a href=\"javascript:void(0)\" onclick=\"radow.doEvent('delperson','"+p+"');\" class=\"btn_minus\" role=\"button\" title=\"????\"></a>"
						+"<input name=\"a01a0000\" type=\"checkbox\"   class=\"ckbox\"   />");
				cellMap.put("a0000", a0000);
				cellMap.put("sclass", "name delIcon ");
				cellMap.put("fxyp00", fxyp00);
				cellMap.put("fxyp00_ry", fxyp00_ry);
				
			}else{
				cellMap.put("text", a0101);
			}
			cellMap.put("famx00", famx00);
			cellMap.put("gwName", a0215a.replaceAll("\r|\n", ""));
			cellMap.put("jgjc", jgjc.replaceAll("\r|\n", ""));
			cellMap.put("style", "position:relative ");
			rows.add(cellMap);
			//????????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0107);
			rows.add(cellMap);
			//????????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0192a);
			cellMap.put("style", "text-align:left");
			rows.add(cellMap);
			//??????????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", qrzxl);
			rows.add(cellMap);
			//????????
			cellMap = new HashMap<String, String>();
			cellMap.put("text", zgxl);
			rows.add(cellMap);
			
			//???????? ????????????????
			if("2".equals(fabd05)){
				//????????????????
				cellMap = new HashMap<String, String>();
				cellMap.put("text", a0288);
				rows.add(cellMap);
				//????????????????
				if(Integer.valueOf(gwcount)>=1&&Integer.valueOf(rk)==1){
					cellMap = new HashMap<String, String>();
					cellMap.put("text", tpdesc2);
					cellMap.put("rowspan", gwcount);
					cellMap.put("colname", "TPDESC2");
					cellMap.put("a0200", fxyp00);
					cellMap.put("tpyjid", tpyjid);
					cellMap.put("famx00", famx00);
					cellMap.put("a0000", a0000);
					cellMap.put("sclass", "input-editor");
					rows.add(cellMap);
				}
				//????????????????
				cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				rows.add(cellMap);
				
			}
			
			
			//???????? ???????? ????????????????
			if(Integer.valueOf(gwcount)>=1&&Integer.valueOf(rk)==1){
				String a0201b = m.get("a0201b")==null?"":m.get("a0201b").toString();
				cellMap = new HashMap<String, String>();
				cellMap.put("text", tpdesc);
				cellMap.put("rowspan", gwcount);
				cellMap.put("a0200", fxyp00);
				cellMap.put("tpyjid", tpyjid);
				cellMap.put("famx00", famx00);
				cellMap.put("colname", "TPDESC");
				cellMap.put("famx01", famx01);
				cellMap.put("a0000", a0000);
				cellMap.put("laiyuanb0111", a0201b);
				cellMap.put("zgxl", zgxl.replaceAll("\r|\n", ""));
				cellMap.put("qrzxl", qrzxl.replaceAll("\r|\n", ""));
				cellMap.put("a0107", a0107);
				cellMap.put("qx", b0111);
				
				if("2".equals(fabd05)){
					cellMap.put("sclass", "input-editor classBR");
				}else{
					cellMap.put("sclass", "input-editor");
				}
				rows.add(cellMap);
			}
			
			
			//????????
			if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
				//????
				String laiyuan = m.get("laiyuan")==null?"":m.get("laiyuan").toString();
				if("".equals(laiyuan)){
					
					String gwa0200 = m.get("gwa0200")==null?"":m.get("gwa0200").toString();
					String rya0200 = m.get("rya0200")==null?"":m.get("rya0200").toString();
					if(!gwa0200.equals(rya0200)&&!"".equals(rya0200)){
						String rya0201e = m.get("rya0201e")==null?"":m.get("rya0201e").toString();
						String a02a0201e = m.get("a02a0201e")==null?"":m.get("a02a0201e").toString();
						String tiba = "";
						if("".equals(a02a0201e)&&!"".equals(rya0201e)){
							tiba = "????";
						}
						//??????????
						if(!"1".equals(a02a0201e)&&"1".equals(rya0201e)){
							tiba = "????";
						}
						if("".equals(tiba)){
							//laiyuan = "????????";
						}else{
							//laiyuan = "????????";
						}
					}
				}
				cellMap = new HashMap<String, String>();
				cellMap.put("text", laiyuan);
				cellMap.put("sclass", "classBR");
				rows.add(cellMap);
			}
			
			//???????? ????????
			//????
			//???????????????? ????????
			if(i==0&&"1".equals(fabd05)&&"2".equals(mntp05)){
				cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				//cellMap.put("a0000", a0000);
				cellMap.put("b0111", b0111);
				cellMap.put("famx00", famx00);
				cellMap.put("style", "text-algin:left ");
				cellMap.put("rowspan", mergerowspan+"");
				cellMap.put("sclass", "tpsm classBR");
				rows.add(cellMap);
				
				/*for(Map<String, String> m1 : rows){
					String className = m1.get("sclass");
					if(StringUtils.isEmpty(className)){
						m1.put("sclass", tableCount.classBT);
					}else if(!className.contains(tableCount.classBT)){
						m1.put("sclass", className+tableCount.classBT);
					}
				}*/
			}
			
			
			
			
			
		}
		
		if(merge<tableCount.rowMaxCount){
			//??????????????
			int rowOffset =  tableCount.rowMaxCount - merge;
			addOffSetMapSel(rowOffset,tableCount.TP_COLS,tableData,tableCount,-1);
			
		}
		
	}

	/**
	 * ????????????
	 * @param i ?????? 
	 * @param rowOffset  ????????
	 * @param colCountOffset ????????
	 * @param tableData 
	 * @param tableCount 
	 * @param dwIndex 
	 */ 
	private void addOffSetMap( int rowOffset, int colCountOffset, List<List<Map<String, String>>> tableData, TableCount tableCount, int dwIndex) {
		/*List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		
		for(int i=0;i<rowOffset;i++){
			for(int j=0;j<colCountOffset;j++){
				Map<String, String> cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				rows.add(cellMap);
			}
			tableData.add(rows);
		}
		
		
		//??????????
		for(int i=0;i<tableCount.colMergeList.size();i++){
			int cols = tableCount.colMergeList.get(i);
			if(cols<tableCount.colPointer){
				//??????????????
				List<Map<String, String>> l = tableData.get(tableCount.rowStart);
				//??????????
				Map<String, String> cellMap = l.get(cols);
				cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");
				cellMap = l.get(cols+1);
				cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");
			}
		}
		*/
		
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		//rows = tableData.get(tableCount.rowPointer);
		Map<String, String> cellMap = new HashMap<String, String>();
		cellMap.put("text", "");
		cellMap.put("rowspan", rowOffset+"");
		if(dwIndex>=0){
			cellMap.put("sclass", "classBR classBT");
			cellMap.put("dwIndex", dwIndex+"");
		}else{
			cellMap.put("sclass", "classBR");
		}
		cellMap.put("colspan", colCountOffset+"");
		rows.add(cellMap);
		
		tableData.add(rows);
	}

	/**
	 * ????????????
	 * @param i ?????? 
	 * @param rowOffset  ????????
	 * @param colCountOffset ????????
	 * @param tableData 
	 * @param tableCount 
	 * @param i 
	 */ 
	private void addOffSetMapSel( int rowOffset, int colCountOffset, List<List<Map<String, String>>> tableData, TableCount tableCount, int dwIndex) {
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		
		/*for(int i=0;i<rowOffset;i++){
			rows = tableData.get(tableCount.rowPointer);
			tableCount.rowPointer++;
			for(int j=0;j<colCountOffset;j++){
				Map<String, String> cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				rows.add(cellMap);
			}
		}*/
		
		rows = tableData.get(tableCount.rowPointer);
		Map<String, String> cellMap = new HashMap<String, String>();
		cellMap.put("text", "");
		cellMap.put("rowspan", rowOffset+"");
		
		cellMap.put("colspan", colCountOffset+"");
		if(dwIndex>=0){
			cellMap.put("sclass", "classBR classBT");
			cellMap.put("dwIndex", dwIndex+"");
		}else{
			cellMap.put("sclass", "classBR");
		}
		
		rows.add(cellMap);
		tableCount.rowPointer = tableCount.rowPointer+rowOffset;
		
		/*int cols = tableCount.colMergeList.get(tableCount.colMergeList.size()-1);
		//??????????????
		List<Map<String, String>> l = tableData.get(tableCount.rowStart);
		//??????????
		Map<String, String> cellMap = l.get(cols);
		cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");
		cellMap = l.get(cols+1);
		cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");*/
		
	}
	
	/**
	 * ???? ????
	 * @param tableData
	 * @param tableCount 
	 * @param famx00 
	 * @throws RadowException 
	 * @throws AppException 
	 */
	private void addXZTitle2(List<List<Map<String, String>>> tableData, TableCount tableCount, String famx00) throws RadowException, AppException {
		String fabd00 = this.getPageElement("fabd00").getValue();
		List<Map<String, String>> rows = null;
		//??????
		if(tableCount.rowPointer<tableData.size()){
			rows = tableData.get(tableCount.rowPointer);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		
		
		Map<String, String> cellMap = new HashMap<String, String>();
		
		String xzcolspan =  tableCount.XZ_COLS - 0 +"";
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "??????????");
		cellMap.put("famx00", famx00);
		cellMap.put("famx01", "1");
		cellMap.put("colspan", xzcolspan);
		cellMap.put("style", "position:relative ");
		cellMap.put("sclass", "titleColor tableTile classBR");
		rows.add(cellMap);
		
		
		tableCount.rowPointer++;
	}


	/**
	 * ???? ????
	 * @param tableData
	 * @param tableCount 
	 * @throws RadowException 
	 * @throws AppException 
	 */
	private void addXZTitle(List<List<Map<String, String>>> tableData, TableCount tableCount) throws RadowException, AppException {
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();	
		String fabd05 = this.getPageElement("fabd05").getValue();	
		List<Map<String, String>> rows = null;
		Map<String, String> cellMap = new HashMap<String, String>();
		
		//??????
		if(tableCount.rowPointer<tableData.size()){
			rows = tableData.get(tableCount.rowPointer);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????");
		cellMap.put("rowspan", "1");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "8");
		tableCount.tableWidth += 8;
		cellMap.put("sclass", "titleColor nrzwwidth");
		rows.add(cellMap);
		
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????");
		cellMap.put("style", "width:");
		if("1".equals(fabd05)&&"2".equals(mntp05)){
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
		}else{
			cellMap.put("tableWidth", "5");
			tableCount.tableWidth+=5;
		}
		
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????<br/>????");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "4");
		tableCount.tableWidth+=4;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		//????????
		if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "??????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "4");
			tableCount.tableWidth+=4;
			cellMap.put("sclass", "titleColor");
			rows.add(cellMap);
			
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????????????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "4");
			tableCount.tableWidth+=4;
			cellMap.put("sclass", "titleColor");
			rows.add(cellMap);
		}
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????????");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "6");
		tableCount.tableWidth+=6;
		//????????
		if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
			cellMap.put("sclass", "titleColor");
		}else{
			cellMap.put("sclass", "titleColor classBR");
		}
		
		rows.add(cellMap);
		
		//????????
		if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "6");
			tableCount.tableWidth+=6;
			cellMap.put("sclass", "titleColor classBR");
			rows.add(cellMap);
		}
		
		for(Map<String, String> m1 : rows){
			String className = m1.get("sclass");
			if(StringUtils.isEmpty(className)){
				m1.put("sclass", "classBTTitle");
			}else if(!className.contains("classBTTitle")){
				m1.put("sclass", className+" classBTTitle ");
			}
		}
		
		
		tableCount.rowPointer++;
		
		
	}
	
	/**
	 * ???? ????
	 * @param tableData
	 * @param tableCount 
	 * @param famx00 
	 * @throws RadowException 
	 * @throws AppException 
	 */
	private void addTPTitle2(List<List<Map<String, String>>> tableData, TableCount tableCount, String famx00) throws RadowException, AppException {
		String fabd00 = this.getPageElement("fabd00").getValue();
		List<Map<String, String>> rows = null;
		//??????
		if(tableCount.rowPointer<tableData.size()){
			rows = tableData.get(tableCount.rowPointer);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		
		String tpcolspan = tableCount.TP_COLS - 0 +"";
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????????????");
		cellMap.put("colspan", tpcolspan);
		cellMap.put("famx00", famx00);
		cellMap.put("famx01", "2");
		cellMap.put("style", "position:relative ");
		
		cellMap.put("sclass", "titleColor tableTile classBR");
		rows.add(cellMap);
		
		tableCount.rowPointer++;
	}
	/**
	 * ???? ????
	 * @param tableData
	 * @param tableCount 
	 * @throws RadowException 
	 * @throws AppException 
	 */
	private void addTPTitle(List<List<Map<String, String>>> tableData, TableCount tableCount) throws RadowException, AppException {
		String fabd00 = this.getPageElement("fabd00").getValue();
		//['2','????????'],['1','????????'],['4','????????']
		String mntp05 = this.getPageElement("mntp05").getValue();		
		String fabd05 = this.getPageElement("fabd05").getValue();		
		List<Map<String, String>> rows = null;
		Map<String, String> cellMap = new HashMap<String, String>();
		
		
		//??????
		if(tableCount.rowPointer<tableData.size()){
			rows = tableData.get(tableCount.rowPointer);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????");
		cellMap.put("rowspan", "1");
		cellMap.put("style", "width:");
		//????????
		if("2".equals(fabd05)){
			cellMap.put("tableWidth", "15");
			tableCount.tableWidth+=15;
		}else{
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
		}
		cellMap.put("sclass", "titleColor nrzwwidth");
		rows.add(cellMap);
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????");
		cellMap.put("style", "width:");
		//???????? 
		if("2".equals(fabd05)){
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
		}else if("1".equals(fabd05)&&"2".equals(mntp05)){//????????????????
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
		}else{
			cellMap.put("tableWidth", "5");
			tableCount.tableWidth+=5;
		}
		
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????<br/>????");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "5");
		tableCount.tableWidth += 5;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????????????????");
		cellMap.put("style", "width:");
		//????????
		if("2".equals(fabd05)){
			cellMap.put("tableWidth", "25");
			tableCount.tableWidth+=25;
		}else if("1".equals(fabd05)&&"2".equals(mntp05)){//????????????????
			cellMap.put("tableWidth", "22");
			tableCount.tableWidth+=22;
		}else{
			cellMap.put("tableWidth", "18");
			tableCount.tableWidth+=18;
		}
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "??????????");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "7");
		tableCount.tableWidth += 7;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????????");
		cellMap.put("style", "width:");
		cellMap.put("tableWidth", "7");
		tableCount.tableWidth += 7;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		//????????
		if("2".equals(fabd05)){
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????????????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "6");
			tableCount.tableWidth+=6;
			cellMap.put("sclass", "titleColor");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
			cellMap.put("sclass", "titleColor");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
			cellMap.put("sclass", "titleColor");
			rows.add(cellMap);
		}
		
		//???????? ???????? ????????
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "????");
		cellMap.put("style", "width:");
		//????????
		if("2".equals(fabd05)){
			cellMap.put("tableWidth", "8");
			tableCount.tableWidth+=8;
			cellMap.put("sclass", "titleColor classBR");
		}else{
			cellMap.put("tableWidth", "6");
			tableCount.tableWidth+=6;
			cellMap.put("sclass", "titleColor");
		}
		
		rows.add(cellMap);
		
		//????????
		if("1".equals(fabd05)&&"1,4,5".contains(mntp05)){
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "7");
			tableCount.tableWidth += 7;
			cellMap.put("sclass", "titleColor classBR");
			rows.add(cellMap);
		}
		
		
		//????????  ????????????
		if("1".equals(fabd05)&&"2".equals(mntp05)){
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "????????");
			cellMap.put("style", "width:");
			cellMap.put("tableWidth", "10");
			tableCount.tableWidth += 10;
			cellMap.put("sclass", "titleColor classBR");
			rows.add(cellMap);
		}
		
		
		for(Map<String, String> m1 : rows){
			String className = m1.get("sclass");
			if(StringUtils.isEmpty(className)){
				m1.put("sclass", "classBTTitle");
			}else if(!className.contains("classBTTitle")){
				m1.put("sclass", className+" classBTTitle ");
			}
		}
		
		tableCount.rowPointer++;
	}
	
	//????????????
	private class TableCount{
		//??????????
		int rowStart = -1;
		//????????????
		int rowMaxCount = -1;
		
		int rowPointer = -1;
		//int colPointer = -1;
		//???????? 
		int XZ_COLS = 0;
		//????????
		int TP_COLS = 0;
		
		//??????????????2??????
		final int DATA_MERGE_OFFSET=2; 
		
		//????????????
		int colCountOffset = 0;
		int tableWidth = 0;
		int TABLEWIDTH = 0;
		
		void finish(){
			this.rowMaxCount = -1;
			this.colCountOffset = 0;
			//this.colPointer = 0;
			//this.colMergeList.clear();
		}
		void init(String fabd05, String mntp05){
			//????????????????
			if("1".equals(fabd05)&&"2".contains(mntp05)){
				this.TP_COLS = 8;
				this.XZ_COLS = 4;
			}else if("1".equals(fabd05)&&"1,5".contains(mntp05)){//????????????????
				this.TP_COLS = 8;
				this.XZ_COLS = 7;
			}else if("1".equals(fabd05)&&"4".contains(mntp05)){//????????????????
				this.TP_COLS = 8;
				this.XZ_COLS = 7;
			}else if("2".equals(fabd05)){//????????????????
				this.TP_COLS = 10;
				this.XZ_COLS = 4;
			}
			this.rowPointer=0;
			this.rowStart=0;
		}
		
	}
	
	
	
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String fxyp00) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		String a0000s = this.getPageElement("a0000s").getValue();
		
		//??????????????????????????a0200????
		String a0200s = this.getPageElement("a0200s").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		
		
		String fabd00 = this.getPageElement("fabd00").getValue();
		String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
		String gwname = HBUtil.getValueFromTab("fxyp02", "hz_fxyp_SJFA", "fxyp00='"+fxyp00+"'");
		new LogUtil().createLogNew("????????????????","????????????????????","??????????????????",fabd00,mntpname+"("+gwname+")", new ArrayList());

		
		
		
		
		//????
		if(a0000s!=null&&!"".equals(a0000s)){//????????????
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " a02.a0200,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'"+fxyp00+"' fxyp00 from a01 t,"
							+ "(select * "
			+"                   from (select a.a0000,a.a0200, "
			+"                                a0201b, "
			+"                                row_number() over(partition by a.a0000 order by nvl(a.a0279,0) desc,b0269) row_number "
			+"                           from a02 a ,b01 where a.a0201b=b01.b0111 "
			+"                          and a0281 = 'true' and a0201b not like '001.001.001%') t "
			+"                  where t.row_number = 1) a02 ");
		
			sql.append(" where a02.a0000=t.a0000 and t.a0000 in ('"+a0000s+"') ");
			sql.append(" and not exists (select 1 from v_mntp_sj_gw_ry p where p.a0000=t.a0000");
			sql.append(" and fxyp00='"+fxyp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into hz_rxfxyp_SJFA(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115,a0200 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					String maxsortnum = HBUtil.getValueFromTab("max(sortnum)", "hz_rxfxyp_SJFA", "fxyp00='"+fxyp00+"'");
					int i=Integer.valueOf(maxsortnum==null?"0":maxsortnum);
					ps = conn.prepareStatement(insertsql);
					for(HashMap m : listCode){
						//????????????
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//??????????????
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//??????????????
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						
						ps.setString(1, m.get("tp0100").toString());
						ps.setString(2, textFormat(m.get("a0000")));
						ps.setString(3, m.get("type").toString());
						ps.setString(4, textFormat(m.get("tp0101")));
						ps.setString(5, textFormat(m.get("tp0102")));
						ps.setString(6, textFormat(m.get("tp0103")));
						ps.setString(7, textFormat(m.get("tp0104")));
						ps.setString(8, textFormat(m.get("tp0105")));
						ps.setString(9, textFormat(m.get("tp0106")));
						ps.setString(10, textFormat(m.get("tp0107")));
						ps.setInt(11, ++i);
						ps.setString(12, fxyp00);
						ps.setString(13, "");
						ps.setString(14, textFormat(m.get("tp0111")));
						ps.setString(15, textFormat(m.get("tp0112")));
						ps.setString(16, textFormat(m.get("tp0115")));
						ps.setString(17, "".equals(a0200s)?textFormat(m.get("a0200")):a0200s);
						ps.addBatch();
						this.getExecuteSG().addExecuteCode("infoSearchSyn('add','"+textFormat(m.get("a0000"))+"');");
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("??????????");
					e.printStackTrace();
				}
				this.setMainMessage("??????????");
				e.printStackTrace();
			}
			
			
			
			this.getExecuteSG().addExecuteCode("infoSearchSyn();hidewin();");	
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			//this.setMainMessage("??????????????????");
			//return EventRtnType.NORMAL_SUCCESS;
		}
			
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	private String textFormat(Object v){
		String value = null;
		if(v!=null){
			if("null".equals(v.toString())){
				return null;
			}
			value = v.toString().replace("{/n}", "\n");
		}
		return value;
	}
	private String getFTime(Object tex){
		String text = null;
		if(tex!=null){
			text = tex.toString();
			if(text.length()>=6){
				return text.substring(0, 4)+"."+text.substring(4, 6);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	
	@PageEvent("showOrgInfo")
	public int showOrgInfo() throws RadowException, AppException{
		StopWatch w = new StopWatch();
		w.start();
		String fabd00 = this.getPageElement("fabd00").getValue();
		String fabd05 = this.getPageElement("fabd05").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();	
		
		//???????? ????????????
		if("1".equals(fabd05)&&"2".equals(mntp05)){
			DWInfoUtil.showOrgInfo(fabd00,this);
		}
		w.stop();
		System.out.println(w.elapsedTime());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 * ??????????a0200????????????????????????  
	 * 1???????? ????????  ??????????????????
	 * 2???????????????????????????????????????????? ?????????? ????????????????????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("setLiuRen")
	public int setLiuRen() throws RadowException, AppException{
		try{
			String fabd00 = this.getPageElement("fabd00").getValue();
			//????????id
			String a0000 = this.getPageElement("a0000s").getValue();
			//????????id
			String a0200 = this.getPageElement("a0200s").getValue();
			String b0111f = this.getPageElement("b0111").getValue();
			String sql = "select fxyp00,a0000,b0111,gwa0200,rya0200 from v_mntp_sj_gw_ry"
					+ " where fabd00='"+fabd00+"' and (gwa0200='"+a0200+"' or rya0200='"+a0200+"')";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, String>> nmzwList = cqbs.getListBySQL2(sql);
			String fxyp00,b0111,gwa0200,rya0200;
			for(HashMap<String, String> rowMap : nmzwList){
				fxyp00 = rowMap.get("fxyp00");
				b0111 = rowMap.get("b0111");
				gwa0200 = rowMap.get("gwa0200");
				rya0200 = rowMap.get("rya0200");
				//???????? ??????????????   ????????????????????????
				if(StringUtils.isEmpty(rya0200)||!rya0200.equals(gwa0200)){
					this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS('"+a0000+"','"+fxyp00+"','"+gwa0200+"');");
				}else {
					if(b0111f.equals(b0111)){
						//????????
					}else{
						String p = fxyp00+"@@"+a0000;
						this.getExecuteSG().addExecuteCode("radow.doEvent('delperson','"+p+"');");
					}
				}
			}
			
			this.getExecuteSG().addExecuteCode("Ext.example.msg('','??????????????',1);$('.button-div').hide();");	
		} catch (Exception e) {
			this.setMainMessage("??????????");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 * ??????????a0200????????????????????????  
	 * ??????????????????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("setMianZhi")
	public int setMianZhi() throws RadowException, AppException{
		try{
			String fabd00 = this.getPageElement("fabd00").getValue();
			//????????id
			String a0000 = this.getPageElement("a0000s").getValue();
			//????????id
			String a0200 = this.getPageElement("a0200s").getValue();
			String b0111f = this.getPageElement("b0111").getValue();
			String sql = "select fxyp00,a0000,b0111,gwa0200,rya0200 from v_mntp_sj_gw_ry"
					+ " where fabd00='"+fabd00+"' and (rya0200='"+a0200+"')";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, String>> nmzwList = cqbs.getListBySQL2(sql);
			String fxyp00,b0111,gwa0200,rya0200;
			for(HashMap<String, String> rowMap : nmzwList){
				fxyp00 = rowMap.get("fxyp00");
				rya0200 = rowMap.get("rya0200");
				//???????? ??????????????
				if(!StringUtils.isEmpty(rya0200)){
					String p = fxyp00+"@@"+a0000;
					this.getExecuteSG().addExecuteCode("radow.doEvent('delperson','"+p+"');");
				}
			}
			
			this.getExecuteSG().addExecuteCode("Ext.example.msg('','??????????????',1);$('.button-div').hide();");	
		} catch (Exception e) {
			this.setMainMessage("??????????");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//????????
	@PageEvent("delperson")
	@GridDataRange
	@NoRequiredValidate
	public int delperson(String str) throws RadowException{ 
		HBSession sess = HBUtil.getHBSession();
		String[] arr=str.split("@@");
		String fxyp00=arr[0];
		String a0000=arr[1];
		try {
			String fabd00 = this.getPageElement("fabd00").getValue();
			String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String tp0101 = HBUtil.getValueFromTab("tp0101", "hz_rxfxyp_SJFA", "fxyp00='"+fxyp00+"' and a0000='"+a0000+"'");
			new LogUtil().createLogNew("????????????????","????????????????????","??????????????????",fabd00,mntpname+"("+tp0101+")", new ArrayList());

			
			Statement stmt = sess.connection().createStatement();
			//String sql2 = "update rxfxyp set sortnum=sortnum-1 where fxyp00='"+fxyp00+"' and sortnum>(select sortnum from  rxfxyp where fxyp00='"+fxyp00+"' and a0000='"+a0000+"')";
			String sql="delete from hz_rxfxyp_SJFA where fxyp00='"+fxyp00+"' and a0000='"+a0000+"'";
			//stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("infoSearchSyn('del','"+a0000+"','');infoSearchSyn()");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//????
	@PageEvent("updateTPYJ")
	public int save(String a) throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String tpyjInfoJSON = this.getPageElement("tpyjInfoJSON").getValue().replace("\n", "{/n}");
		JSONObject  ID_ROWINFOObject = JSONObject.fromObject(tpyjInfoJSON);
		Map<String,String> ID_ROWINFOMap = (Map<String,String>)ID_ROWINFOObject;
		String updatetype = ID_ROWINFOMap.get("updatetype");
		String a0200 = ID_ROWINFOMap.get("a0200");
		String tpyjid = ID_ROWINFOMap.get("tpyjid");
		String famx00 = ID_ROWINFOMap.get("famx00");
		
		String colname = ID_ROWINFOMap.get("colname");
		String updatevalue = ID_ROWINFOMap.get(colname);
		if("insert".equals(updatetype)){
			HBUtil.executeUpdate("delete from HZ_MNTP_SJFA_INFO where famx00f=? and a0200=?", new Object[]{famx00,a0200});
			String sql = "insert into HZ_MNTP_SJFA_INFO(famx00f,fabd00,a0200,infoid,"+colname+") values(?,?,?,?,?)";
			HBUtil.executeUpdate(sql, new Object[]{famx00,fabd00,a0200,tpyjid,updatevalue});
		}else{
			String sql = "update HZ_MNTP_SJFA_INFO set "+colname+"=? where infoid=?";
			HBUtil.executeUpdate(sql, new Object[]{updatevalue,tpyjid});
		}
		
		String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");

		new LogUtil().createLogNew("????????","????????????","??????????????",fabd00,mntpname+"("+updatevalue+")", new ArrayList());

		
		this.getExecuteSG().addExecuteCode("delete GLOBLE['ID_ROWINFO']['"+tpyjid+"']['updatetype'];");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//????????????
	@PageEvent("setGWSQL")
	@GridDataRange
	@NoRequiredValidate
	public int queryByPerson(String fxyp00) throws RadowException {
		Object[] query = (Object[]) HBUtil.getHBSession()
				.createSQLQuery("select b.sql,b.wayname from hz_fxyp_SJFA a, zdgw_way b where a.fxyp00='" + fxyp00
						+ "' and  a.gwmc = b.wayid ")
				.uniqueResult();
		String sql = "";
		String name = "";
		String flag = " order by sort ";
		if (query != null) {
			Object sqlo = query[0];
			if(sqlo==null){
				this.setMainMessage("??????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				sql = sqlo.toString();
			}
			name = query[1].toString();
		}
		this.request.getSession().setAttribute("gbmcName", name);
		this.request.getSession().setAttribute("gbmcSql", sql);
		this.request.getSession().setAttribute("gbmcFlag", flag);
		this.getExecuteSG().addExecuteCode("openYouGuanRenXuann('"+fxyp00+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("tpbj.onclick")
	public int tpbj() throws RadowException {
		LinkedHashSet<String> selected = new LinkedHashSet<String>();
		// ??cookie??????????????????????id
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("jggl.tpbj.ids".equals(cookie.getName())) {
					String cookieValue = cookie.getValue();
					String[] ids = cookieValue.split("#");
					for (String id : ids) {
						if (!StringUtils.isEmpty(id)) {
							selected.add(id);
						}
					}
				}
			}
		}
		// ????????????????????????????????
		String a0000s = this.getPageElement("a0000rybd").getValue();
		if (!StringUtils.isEmpty(a0000s)) {
			String[] a0000Array = a0000s.split(",");
			for (int i = 0; i < a0000Array.length; i++) {
				selected.add(a0000Array[i]);
			}
		}

		if (selected.size() == 0) {
//			this.setMainMessage("??????????");
			this.getExecuteSG().addExecuteCode("$h.alert('??????????','??????????????',null,150);");
			return EventRtnType.FAILD;
		} else {
			String json = JSON.toJSONString(selected);
			this.getExecuteSG()
					.addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','????????',1500,731,null,'"
							+ this.request.getContextPath() + "',null,{"
							+ "maximizable:false,resizable:false,RMRY:'????????',data:" + json + "},true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	
	@PageEvent("delgw")
	@NoRequiredValidate
	public int delGW2(String fxyp00) throws RadowException, AppException{ 
		HBSession sess = HBUtil.getHBSession();
		try {
			String fabd00 = this.getPageElement("fabd00").getValue();
			String sjdw00 = HBUtil.getValueFromTab("sjdw00", "hz_fxyp_SJFA", "fxyp00='"+fxyp00+"'");
			String fxyp02 = HBUtil.getValueFromTab("fxyp02", "hz_fxyp_SJFA", "fxyp00='"+fxyp00+"'");
			String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			
			new LogUtil().createLogNew("????????????","????????????????","??????????????",sjdw00,mntpname+"("+fxyp02+")", new ArrayList());

			List<String> a0000list = HBUtil.getHBSession().createSQLQuery("select a0000 from hz_rxfxyp_SJFA where fxyp00='"+fxyp00+"'").list();
			for(String a0000:a0000list){
				this.getExecuteSG().addExecuteCode("infoSearchSyn('del','"+a0000+"','');");
			}
			String sql="delete from hz_rxfxyp_SJFA where fxyp00='"+fxyp00+"'";
			HBUtil.executeUpdate(sql);
			
			if(a0000list.size()==0){
				sql="delete from hz_fxyp_SJFA where fxyp00='"+fxyp00+"'";
				HBUtil.executeUpdate(sql);
			}
			
			//????????????
			//?????????????????? 
			String fabd05 = this.getPageElement("fabd05").getValue();
			//if(fabd05.equals("2")){
				int delrow = HBUtil.executeUpdate("delete from HZ_MNTP_SJFA_ORG where (select count(1) from hz_fxyp_SJFA where sjdw00='"+sjdw00+"')=0 and sjdw00='"+sjdw00+"'");
				if(delrow==1){//??????????????????????
					this.getExecuteSG().addExecuteCode("infoSearch()");
					return EventRtnType.NORMAL_SUCCESS;		
				}
			//}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("infoSearchSyn()");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * ????????????
	 * @param fxyp00
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("setGWInfo")
	@NoRequiredValidate
	public int setGWInfo(String fxyp00) throws RadowException, AppException{ 
		CommQuery cqbs=new CommQuery();
		
		try {
			String sql="select a.bzgw,(select code_name from code_value where code_type='KZ01' and code_value=a.bzgw) bzgwname,a.fxyp02,a.sjdw00,a.gwmc,a.a0201e,b.famx00,b.b0111,(select b0104 from b01 where b01.b0111=b.b0111) b0104 "
					+ " from hz_fxyp_SJFA a,HZ_MNTP_SJFA_ORG b where "
					+ " a.sjdw00=b.sjdw00 and fxyp00='"+fxyp00+"'";
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);	
			if(list.size()>0){
				HashMap<String, String> fxypMap = list.get(0);
				this.getPageElement("fxyp00").setValue(fxyp00);
				this.getPageElement("gwname").setValue(fxypMap.get("fxyp02"));
				this.getPageElement("gwtype").setValue(fxypMap.get("a0201e"));
				this.getPageElement("gwmc").setValue(fxypMap.get("gwmc"));
				this.getPageElement("b0111gw").setValue(fxypMap.get("b0111"));
				this.getPageElement("mntp_b01").setValue(fxypMap.get("b0104"));
				this.getPageElement("bzgw").setValue(fxypMap.get("bzgw"));
				this.getPageElement("bzgw_combotree").setValue(fxypMap.get("bzgwname"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("openGWWin(false)");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * ????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("saveGW")
	@NoRequiredValidate
	public int saveGW()throws RadowException{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String famx00 = this.getPageElement("famx00").getValue();
		//????????b0111????????b0111  HZ_MNTP_SJFA_ORG????b0111
		String b0111=this.getPageElement("b0111gw").getValue();
		
		String gwname = this.getPageElement("gwname").getValue();
		String gwtype = this.getPageElement("gwtype").getValue();
		String fxyp00 = this.getPageElement("fxyp00").getValue();
		String gwmc = this.getPageElement("gwmc").getValue();
		String bzgw = this.getPageElement("bzgw").getValue();
		String mntp_b01 = this.getPageElement("mntp_b01").getValue();
		
		
				
		String zwqc00 = "";
		if("102".equals(bzgw)||"201".equals(bzgw)){
			zwqc00 = (b0111.substring(0, 15))+"102201";
		}else if("115".equals(bzgw)||"209".equals(bzgw)){
			zwqc00 = (b0111.substring(0, 15))+"115209";
		}else if("108".equals(bzgw)||"202".equals(bzgw)){
			zwqc00 = (b0111.substring(0, 15))+"108202";
		}
		String userid = SysManagerUtils.getUserId();
		try {
			String mntpname = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			String famx03 = HBUtil.getValueFromTab("famx03", "HZ_MNTP_SJFA_famx", "famx00='"+famx00+"'")+":"+mntp_b01;
			HBSession sess = HBUtil.getHBSession();
			String sjdw00 = HBUtil.getValueFromTab("sjdw00", "HZ_MNTP_SJFA_ORG", "famx00='"+famx00+"' and b0111='"+b0111+"'");
			String sql="";
			if(StringUtils.isEmpty(fxyp00)) {
				//????
				fxyp00=UUID.randomUUID().toString();
				String pxh = sess.createSQLQuery("select nvl(max(fxyp01),0)+1 from hz_fxyp_SJFA where sjdw00='"+sjdw00+"'" ).uniqueResult().toString();

				sql="insert into hz_fxyp_SJFA  (fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,sjdw00,famx00,gwmc,bzgw,zwqc00) values "
					+ " ('"+fxyp00+"','"+pxh+"','"+gwname+"','"+userid+"','"+gwtype+"','1','"+b0111+"','"+sjdw00+"','"+famx00+"','"+gwmc+"','"+bzgw+"','"+famx00+zwqc00+"')";
			
				
				new LogUtil().createLogNew("????????????","????????????????","??????????????",sjdw00,mntpname+"("+famx03+":"+gwname+")", new ArrayList());

			}else {
				//????
				new LogUtil().createLogNew("????????????","????????????????","??????????????",sjdw00,mntpname+"("+famx03+":"+gwname+")", new ArrayList());
				
				
				fxyp00=this.getPageElement("fxyp00").getValue();
				sql="update hz_fxyp_SJFA set fxyp02='"+gwname+"',a0201e='"+gwtype+"',gwmc='"+gwmc+"',bzgw='"+bzgw+"'"+("".equals(zwqc00)?",zwqc00=null":",zwqc00=famx00||'"+zwqc00+"'")+"  where fxyp00='"+fxyp00+"' ";
			}
			
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
			this.toastmessage("??????????");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('gwwinid').hide()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("infoSearchSyn();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveFabd02")
	@Transaction
	public int saveFabd02() throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String fabd02 = this.getPageElement("fabd02").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();
		HBUtil.executeUpdate("update HZ_MNTP_SJFA set fabd02=?,mntp05=?"
				+ " where fabd00=?",new Object[]{fabd02,mntp05,fabd00});
		this.toastmessage("??????????");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 * @return
	 * @throws Exception
	 */
	@PageEvent("renxuanpaixu")
	@Transaction
	public int renxuanpaixu(String param) throws Exception{
		String[] params = param.split("@@");
		if(params.length!=4){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		final String fxyp00_drag = params[0];
		final String a0000_drag = params[1];
		final String a0000_drop = params[2];
		final String pointer = params[3];
		//????drag??????
		String dragSort = HBUtil.getValueFromTab("sortnum", "hz_rxfxyp_SJFA", "fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drag+"'");
		String dropSort = HBUtil.getValueFromTab("sortnum", "hz_rxfxyp_SJFA", "fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drop+"'");
		if(StringUtils.isEmpty(dragSort)||StringUtils.isEmpty(dropSort)){
			this.setMainMessage("??????????????????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(Integer.valueOf(dragSort)<Integer.valueOf(dropSort)){
			//?????????????????? drop????????
			HBUtil.executeUpdate(
					"update hz_rxfxyp_SJFA set sortnum="
					+ " (select sortnum from hz_rxfxyp_SJFA where fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drop+"' ) "
					+ " where fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drag+"'");
			//????????????????????drop??????
			HBUtil.executeUpdate("update hz_rxfxyp_SJFA set sortnum=sortnum-1 "
					+ " where  fxyp00='"+fxyp00_drag+"' and sortnum>"+dragSort+" and sortnum<="+dropSort+" and a0000!='"+a0000_drag+"'");
		}else{//????
			//?????????????????? drop????????
			HBUtil.executeUpdate(
					"update hz_rxfxyp_SJFA set sortnum="
					+ " (select sortnum from hz_rxfxyp_SJFA where fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drop+"' ) "
					+ " where fxyp00='"+fxyp00_drag+"' and a0000='"+a0000_drag+"'");
			//????????????????????drop??????
			HBUtil.executeUpdate("update hz_rxfxyp_SJFA set sortnum=sortnum+1 "
					+ " where  fxyp00='"+fxyp00_drag+"' and sortnum>="+dropSort+" and sortnum<"+dragSort+" and a0000!='"+a0000_drag+"'");
		}
		
		
		
		this.toastmessage("??????????");
		this.getExecuteSG().addExecuteCode("infoSearchSyn();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ????????
	 * @return
	 * @throws Exception
	 */
	@PageEvent("genSH")
	@Transaction
	public int genSH(String param) throws Exception{
		SHUtils.shanghui(param,this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}




