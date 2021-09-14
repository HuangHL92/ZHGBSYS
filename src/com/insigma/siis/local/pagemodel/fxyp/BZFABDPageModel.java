package com.insigma.siis.local.pagemodel.fxyp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.base.FABDUtil;
import com.insigma.siis.local.pagemodel.gwdz.MNTPlistPageModel;

import net.sf.json.JSONArray;

public class BZFABDPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
//		this.setExecuteSG(executeSG);
//		this.setNextEventName("pgrid.dogridquery");
//		this.setNextEventName("pgrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	

	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		try{
			String fabd00_o = this.getPageElement("fabd00").getValue();
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> TPQCode=cqbs.getListBySQL("select fabd00,fabd02 "
					+ " from HZ_MNTP_FABD  where fabd04='"+SysManagerUtils.getUserId()+"' order by fabd04,fabd01  ");
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(HashMap<String, Object> m : TPQCode){
				String fabd00 = m.get("fabd00")==null?"":m.get("fabd00").toString();
				String fabd02 = m.get("fabd02")==null?"":m.get("fabd02").toString();
				mapCode.put(fabd00, fabd02);
			}
			((Combo)this.getPageElement("fabd00")).setValueListForSelect(mapCode);
			
			
			String fabd00cur = this.getPageElement("parentfabd00").getValue();
			if(!StringUtils.isEmpty(fabd00cur)){
				this.getExecuteSG().addExecuteCode("odin.setSelectValue('fabd00','"+fabd00cur+"');query();");
			}else{
				this.getExecuteSG().addExecuteCode("odin.setSelectValue('fabd00','"+fabd00_o+"');");
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("showData")
	public int showData() throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		
		// 方案列表    单位列表  单位信息
		//现状或调配块 配置
		List<List<Map<String, Object>>> b0111s = MNTPlistPageModel.ReturnFA(fabd00);
		
		
		
		
		//String[][] b0111s = new String[][]{{"001.001.004.001.001","001.001.004.001.002"},{"001.001.004.001.001","001.001.004.001.002"}};
		
		List<List<Map<String, String>>> tableData = new ArrayList<List<Map<String, String>>>();
		
		int cfgLength = FABDUtil.getLength(b0111s);
		
		TableCount tableCount = new TableCount();
		//机构   先渲染所有表头
		for(int jgi=0;jgi<b0111s.size();jgi++){
			Map<String, Object> cfgMap = b0111s.get(jgi).get(0);
			String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
			if("1".equals(famx01)){
				addXZTitle(tableData,tableCount);
			}else if("2".equals(famx01)){
				addTPTitle(tableData,tableCount);
			}
			
			if(jgi==0){
				//下移两行
				tableCount.rowPointer=2;
				tableCount.rowStart=2;
				//tableCount.colPointer=0;
			}
		}
		
		
		//循环单位数  按方案明细中单位最多的单位数循环    //以横向按单位平铺
		for(int i=0;i<cfgLength;i++){
			
			//机构循环  数据   //横向单位
			for(int jgi=0;jgi<b0111s.size();jgi++){
				List<Map<String, Object>> b0111Block = b0111s.get(jgi);
				//判断是否超出下标   超出下标说明该条明细没有单位
				if(b0111Block.size()>i){
					Map<String, Object> cfgMap = b0111Block.get(i);
					String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
					String b0131 = cfgMap.get("b0131")==null?"":cfgMap.get("b0131").toString();
					String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
					String mntp00 = cfgMap.get("mntp00")==null?"":cfgMap.get("mntp00").toString();
					if("1".equals(famx01)){
						//获取b0111
						String orgid = HBUtil.getValueFromTab("b0111", "b01", "b0121='"+b0111+"' and b0131='"+b0131+"'");
						List<HashMap<String, Object>> dataMap = FABDUtil.getXZXX(orgid);
						addXZData(tableData,tableCount,dataMap);
					}else if("2".equals(famx01)){
						String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
						List<HashMap<String, Object>> dataMap = FABDUtil.getTPXX(b01id,b0111,b0131,mntp00);
						addTPData(tableData,tableCount,dataMap);
						
					}
					
				}else{
					Map<String, Object> cfgMap = b0111Block.get(0);
					String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
					if("1".equals(famx01)){
						addXZempty(tableData,tableCount);
					}else if("2".equals(famx01)){
						addTPempty(tableData,tableCount);
					}
					
				}
				
				//一行最后一个单位
				if(jgi!=b0111s.size()-1){
					tableCount.rowPointer = tableCount.rowStart;
				}else{
					tableCount.rowStart = tableCount.rowPointer;
				}
				
			}
			//初始化
			tableCount.init();
		}
		JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(tableData);
		this.getExecuteSG().addExecuteCode("showData("+updateunDataStoreObject2.toString()+");");
		this.getExecuteSG().addExecuteCode("setTableWidth("+tableCount.tableWidth+");");
		return EventRtnType.NORMAL_SUCCESS;
	}



	




	private void addTPempty(List<List<Map<String, String>>> tableData, TableCount tableCount) {
		//空数据
		if(tableCount.rowMaxCount==-1){
			tableCount.rowMaxCount = 0;
		}else if(tableCount.rowMaxCount==0){
			
		}else{
			addOffSetMapSel(tableData.size()-tableCount.rowStart,tableCount.TP_COLS,tableData,tableCount);
		}
		//结束后列标
		//tableCount.colPointer =  tableCount.colPointer + tableCount.TP_COLS;
		//结束后续补位的列数
		tableCount.colCountOffset = tableCount.colCountOffset + tableCount.TP_COLS;
		
	}



	/**
	 * 1若是起始列，  单位最大行数设为0，   当横向执行下个单位会补全前面的空位
	 * 2若不是起始列， 补全自己的空位  调整行标
	 * 3调整列标  和列偏移量
	 * @param tableData
	 * @param tableCount
	 */
	private void addXZempty(List<List<Map<String, String>>> tableData, TableCount tableCount) {
		
		
		//空数据
		if(tableCount.rowMaxCount==-1){
			tableCount.rowMaxCount = 0;
		}else if(tableCount.rowMaxCount==0){
			
		}else{
			addOffSetMapSel(tableData.size()-tableCount.rowStart,tableCount.XZ_COLS,tableData,tableCount);
		}
		//结束后列标
		//tableCount.colPointer =  tableCount.colPointer + tableCount.XZ_COLS;
		//结束后续补位的列数
		tableCount.colCountOffset = tableCount.colCountOffset + tableCount.XZ_COLS;
	}



	/**
	 * 1调整横向单位最大行数   若有超出  前面补空
	 * 2调整列标  和列偏移量
	 * 3填充数据 调整行标
	 * 4若自己行数不够  自己补空 调整行标
	 * @param tableData
	 * @param tableCount
	 * @param dataMap
	 * @throws AppException
	 */
	private void addXZData(List<List<Map<String, String>>> tableData, TableCount tableCount,
			List<HashMap<String, Object>> dataMap) throws AppException {
		int merge = dataMap.size();
		
		for(int i=0; i<dataMap.size(); i++){
			HashMap<String, Object> m = dataMap.get(i);
			//第一行 第一列  单位名称
			if(i==0){
				//行合并所在的列
				//tableCount.colMergeList.add(tableCount.colPointer);
				//行合并值
				//结束后行标
				//tableCount.rowPointer = tableCount.rowPointer + merge;
				
				//第一次单位最大行数
				if(tableCount.rowMaxCount==-1){
					tableCount.rowMaxCount = merge;
				}
				//单位最大行
				if(merge>tableCount.rowMaxCount){
					//前面补空的行数
					int rowOffset = merge - tableCount.rowMaxCount;
					addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount);
					tableCount.rowMaxCount = merge;
					
				}
				//结束后列标
				//tableCount.colPointer =  tableCount.colPointer + tableCount.XZ_COLS;
				//结束后续补位的列数
				tableCount.colCountOffset = tableCount.colCountOffset + tableCount.XZ_COLS;
			}
			
			
			
			List<Map<String, String>> rows = null;
			//第一行
			if(tableCount.rowPointer<tableData.size()){
				rows = tableData.get(tableCount.rowPointer);
			}else{
				rows = new ArrayList<Map<String, String>>();
				tableData.add(rows);
			}
			
			String qx = m.get("qx")==null?"":m.get("qx").toString();
			String b0131 = m.get("b0131")==null?"":m.get("b0131").toString();
			String a0215a = m.get("a0215a")==null?"":m.get("a0215a").toString();
			String a0101 = m.get("a0101")==null?"":m.get("a0101").toString();
			String a0107 = m.get("a0107")==null?"":m.get("a0107").toString();
			String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
			
			
			tableCount.rowPointer++;
			Map<String, String> cellMap = new HashMap<String, String>();
			//合并
			if(i==0){
				String qxmc = HBUtil.getValueFromTab("b0101", "b01", "b0111='"+qx+"'");
				String b0131mc = HBUtil.getCodeName("ZB04", b0131);
				cellMap.put("text", qxmc);
				cellMap.put("rowspan", merge+"");
				cellMap.put("sclass", "unit");
				cellMap.put("qx", qx.substring(0,15));
				rows.add(cellMap);
				
				cellMap = new HashMap<String, String>();
				cellMap.put("text", b0131mc);
				cellMap.put("rowspan", merge+"");
				cellMap.put("b0131", b0131);
				cellMap.put("qx", qx);
				rows.add(cellMap);
			}
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0215a);
			cellMap.put("a0000", a0000);
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0101);
			cellMap.put("a0000", a0000);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0107);
			cellMap.put("a0000", a0000);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			//调配意见
			cellMap = new HashMap<String, String>();
			cellMap.put("text", "");
			cellMap.put("a0000", a0000);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			
			if(i==dataMap.size()-1){
				if(merge<tableCount.rowMaxCount){
					//前面补空的行数
					int rowOffset =  tableCount.rowMaxCount - merge;
					addOffSetMapSel(rowOffset,tableCount.XZ_COLS,tableData,tableCount);
					
				}
			}
			
			
		}
		
	}

	private void addTPData(List<List<Map<String, String>>> tableData, TableCount tableCount,
			List<HashMap<String, Object>> dataMap) throws AppException {
		int merge = dataMap.size();
		for(int i=0; i<dataMap.size(); i++){
			HashMap<String, Object> m = dataMap.get(i);
			//第一行 第一列  单位名称
			if(i==0){
				//行合并所在的列
				//tableCount.colMergeList.add(tableCount.colPointer);
				//行合并值
				//结束后行标
				//tableCount.rowPointer = tableCount.rowPointer + merge;
				
				//第一次单位最大行数
				if(tableCount.rowMaxCount==-1){
					tableCount.rowMaxCount = merge;
				}
				//单位最大行
				if(merge>tableCount.rowMaxCount){
					//前面补空的行数
					int rowOffset = merge - tableCount.rowMaxCount;
					addOffSetMap(rowOffset,tableCount.colCountOffset,tableData,tableCount);
					tableCount.rowMaxCount = merge;
					
				}
				//结束后列标
				//tableCount.colPointer =  tableCount.colPointer + tableCount.TP_COLS;
				//结束后续补位的列数
				tableCount.colCountOffset = tableCount.colCountOffset + tableCount.TP_COLS;
			}
			
			
			
			List<Map<String, String>> rows = null;
			//第一行
			if(tableCount.rowPointer<tableData.size()){
				rows = tableData.get(tableCount.rowPointer);
			}else{
				rows = new ArrayList<Map<String, String>>();
				tableData.add(rows);
			}
			
			String b01id = m.get("b01id")==null?"":m.get("b01id").toString();
			String qx = m.get("a0201b")==null?"":m.get("a0201b").toString();
			String b0131 = m.get("zrrx")==null?"":m.get("zrrx").toString();
			String a0215a = m.get("a0215a")==null?"":m.get("a0215a").toString();
			String a0101 = m.get("a0101")==null?"":m.get("a0101").toString();
			String a0107 = m.get("a0107")==null?"":m.get("a0107").toString();
			String a0000 = m.get("a0000")==null?"":m.get("a0000").toString();
			String a0192a = m.get("a0192a")==null?"":m.get("a0192a").toString();
			String zgxl = m.get("zgxl")==null?"":m.get("zgxl").toString();
			String qrzxl = m.get("qrzxl")==null?"":m.get("qrzxl").toString();
			String rybz = m.get("rybz")==null?"":m.get("rybz").toString();
			String personStatus = m.get("personstatus")==null?"":m.get("personstatus").toString();
			
			
			tableCount.rowPointer++;
			Map<String, String> cellMap = new HashMap<String, String>();
			//合并
			if(i==0){
				String qxmc = HBUtil.getValueFromTab("b0101", "b01", "b01id='"+b01id+"'");
				String qxid = HBUtil.getValueFromTab("b0111", "b01", "b01id='"+b01id+"'");
				String b0131mc = HBUtil.getCodeName("ZB04", b0131);
				cellMap.put("text", qxmc);
				cellMap.put("rowspan", merge+"");
				cellMap.put("sclass", "unit");
				
				cellMap.put("qx", qxid.substring(0,15));
				
				rows.add(cellMap);
				
				cellMap = new HashMap<String, String>();
				cellMap.put("text", b0131mc);
				cellMap.put("rowspan", merge+"");
				cellMap.put("b0131", b0131);
				cellMap.put("qx", qx);
				rows.add(cellMap);
			}
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0215a);
			cellMap.put("a0000", a0000);
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0101);
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0107);
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", a0192a);
			cellMap.put("style", "text-align:left;");
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", qrzxl);
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			cellMap = new HashMap<String, String>();
			cellMap.put("text", zgxl);
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			//调配意见
			cellMap = new HashMap<String, String>();
			cellMap.put("text", rybz);
			cellMap.put("a0000", a0000);
			cellMap.put("personStatus", personStatus);
			cellMap.put("sclass", "name");
			rows.add(cellMap);
			
			//机构备注
			//合并
			if(i==0){
				cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				cellMap.put("a0000", a0000);
				cellMap.put("rowspan", merge+"");
				rows.add(cellMap);
			}
			
			
			
			if(i==dataMap.size()-1){
				if(merge<tableCount.rowMaxCount){
					//前面补空的行数
					int rowOffset =  tableCount.rowMaxCount - merge;
					addOffSetMapSel(rowOffset,tableCount.TP_COLS,tableData,tableCount);
					
				}
			}
			
			
		}
		
	}

	/**
	 * 前面单位补空
	 * @param i 起始行 
	 * @param rowOffset  有多少行
	 * @param colCountOffset 有多少列
	 * @param tableData 
	 * @param tableCount 
	 */ 
	private void addOffSetMap( int rowOffset, int colCountOffset, List<List<Map<String, String>>> tableData, TableCount tableCount) {
		/*List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		
		for(int i=0;i<rowOffset;i++){
			for(int j=0;j<colCountOffset;j++){
				Map<String, String> cellMap = new HashMap<String, String>();
				cellMap.put("text", "");
				rows.add(cellMap);
			}
			tableData.add(rows);
		}
		
		
		//更新行合并
		for(int i=0;i<tableCount.colMergeList.size();i++){
			int cols = tableCount.colMergeList.get(i);
			if(cols<tableCount.colPointer){
				//获取合并所在行
				List<Map<String, String>> l = tableData.get(tableCount.rowStart);
				//获取单元格
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
		cellMap.put("colspan", colCountOffset+"");
		rows.add(cellMap);
		
		tableData.add(rows);
	}

	/**
	 * 自己单位补空
	 * @param i 起始行 
	 * @param rowOffset  有多少行
	 * @param colCountOffset 有多少列
	 * @param tableData 
	 * @param tableCount 
	 */ 
	private void addOffSetMapSel( int rowOffset, int colCountOffset, List<List<Map<String, String>>> tableData, TableCount tableCount) {
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
		rows.add(cellMap);
		tableCount.rowPointer = tableCount.rowPointer+rowOffset;
		
		/*int cols = tableCount.colMergeList.get(tableCount.colMergeList.size()-1);
		//获取合并所在行
		List<Map<String, String>> l = tableData.get(tableCount.rowStart);
		//获取单元格
		Map<String, String> cellMap = l.get(cols);
		cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");
		cellMap = l.get(cols+1);
		cellMap.put("rowspan", (Integer.valueOf(cellMap.get("rowspan"))+rowOffset)+"");*/
		
	}


	/**
	 * 现状 表头
	 * @param tableData
	 * @param tableCount 
	 */
	private void addXZTitle(List<List<Map<String, String>>> tableData, TableCount tableCount) {
		List<Map<String, String>> rows = null;
		//第一行
		if(tableCount.rowPointer>0){
			rows = tableData.get(0);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		
		cellMap.put("text", "单位");
		cellMap.put("rowspan", "2");
		cellMap.put("colspan", "2");
		cellMap.put("style", "width:90px;");
		tableCount.tableWidth += 90;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "岗位");
		cellMap.put("rowspan", "2");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth += 80;
		cellMap.put("sclass", "titleColor nrzwwidth");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "现状");
		cellMap.put("colspan", "3");
		
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		
		
		//第二行
		if(tableCount.rowPointer>0){
			rows = tableData.get(1);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		
		/*cellMap = new HashMap<String, String>();
		cellMap.put("empty", "empty");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("empty", "empty");
		rows.add(cellMap);*/
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "姓名");
		cellMap.put("style", "width:60px;");
		tableCount.tableWidth+=60;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "出生年月");
		cellMap.put("style", "width:70px;");
		tableCount.tableWidth+=70;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "调配意见");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth+=80;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
	}
	
	
	/**
	 * 调配 表头
	 * @param tableData
	 * @param tableCount 
	 */
	private void addTPTitle(List<List<Map<String, String>>> tableData, TableCount tableCount) {
		List<Map<String, String>> rows = null;
		//第一行
		if(tableCount.rowPointer>0){
			rows = tableData.get(0);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		Map<String, String> cellMap = new HashMap<String, String>();
		
		cellMap.put("text", "单位");
		cellMap.put("rowspan", "2");
		cellMap.put("colspan", "2");
		cellMap.put("style", "width:90px;");
		tableCount.tableWidth += 90;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "岗位");
		cellMap.put("rowspan", "2");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth += 80;
		cellMap.put("sclass", "titleColor nrzwwidth");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "调配方案");
		cellMap.put("colspan", "7");
		
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		
		
		//第二行
		if(tableCount.rowPointer>0){
			rows = tableData.get(1);
		}else{
			rows = new ArrayList<Map<String, String>>();
			tableData.add(rows);
		}
		
		/*cellMap = new HashMap<String, String>();
		cellMap.put("empty", "empty");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("empty", "empty");
		rows.add(cellMap);*/
		
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "姓名");
		cellMap.put("style", "width:60px;");
		tableCount.tableWidth += 60;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "出生年月");
		cellMap.put("style", "width:70px;");
		tableCount.tableWidth += 70;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "任现工作单位及职务");
		cellMap.put("style", "width:120px;text-align:left;");
		tableCount.tableWidth += 120;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "全日制学历");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth += 80;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "最高学历");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth += 80;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "调配意见");
		cellMap.put("style", "width:80px;");
		tableCount.tableWidth += 80;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
		cellMap = new HashMap<String, String>();
		cellMap.put("text", "单位备注");
		cellMap.put("style", "width:100px;");
		tableCount.tableWidth += 100;
		cellMap.put("sclass", "titleColor");
		rows.add(cellMap);
		
	}
	
	//单位相关信息
	private class TableCount{
		//单位行开始
		int rowStart = -1;
		//单位最大行数
		int rowMaxCount = -1;
		
		//合并行 所在的列
		//List<Integer> colMergeList = new ArrayList<Integer>();
		
		int rowPointer = -1;
		//int colPointer = -1;
		//现状列数
		final int XZ_COLS = 6;
		//调配列数
		final int TP_COLS = 10;
		
		//现状列数补位
		//final int XZ_COLS_OFFSET = 4;
		//调配列数补位
		//final int TP_COLS_OFFSET = 8;
		
		//总共需补位数
		int colCountOffset = 0;
		int tableWidth = 0;
		
		void init(){
			this.rowMaxCount = -1;
			this.colCountOffset = 0;
			//this.colPointer = 0;
			//this.colMergeList.clear();
		}
		
	}
	
	
	
	
}