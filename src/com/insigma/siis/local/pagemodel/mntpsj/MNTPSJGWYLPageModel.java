package com.insigma.siis.local.pagemodel.mntpsj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.base.FABDUtil;

import net.sf.json.JSONArray;

public class MNTPSJGWYLPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}

	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		new LogUtil().createLogNew("人员列表","模拟调配人员列表查看","模拟调配人员列表查看功能","","模拟调配", new ArrayList());
		this.getExecuteSG().addExecuteCode("Photo_List.ajaxSubmit('ShowData','pages.mntpsj.MNTPSJGWYL',{},callBackData,true)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		try {
			String fabd00 = this.request.getParameter("fabd00");
			
			if(StringUtils.isEmpty(fabd00)){
				this.setMainMessage("查询失败！");
				return EventRtnType.FAILD;
			}
			// 方案列表    单位列表  单位信息
			//现状或调配块 配置
			List<List<Map<String, Object>>> b0111s = MNTPSJDWPageModel.returnFA(fabd00);
			int cfgLength = FABDUtil.getLength(b0111s);
			if(cfgLength==0){
				this.setMainMessage("该方案无数据！");
				return EventRtnType.FAILD;
			}
			for(int i=0;i<cfgLength;i++){
				
				//机构循环  数据   //横向单位
				genDataMap(b0111s, i, fabd00);
			}
			
			
			
			
			
			
			
			//String selsql = "select  a01.a0000,a01.a0101,a01.a0104,a01.a0107,a0117,a0111a,a0140,a0141,a0144,a0134,a0196,a01.a0192a,a01.zgxw,a01.zgxl,a01.a0192c,a01.a0288,a0192f ";

		
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static List<HashMap<String, Object>> getTPXX(String b0111, String famx00) throws AppException {
		
		String liurenTable = ",(select tpdesc,infoid tpyjid,a0200 from HZ_MNTP_SJFA_INFO where famx00f='"+famx00+"') info ";
		String liurenWhere = " and info.a0200(+)=t.fxyp00 ";
		String selsql = " a01.a0000,a01.a0101,a01.a0104,a01.a0107,a01.a0117,a01.a0111a,a01.a0140,a01.a0141,a01.a0144,a01.a0134,a01.a0196,a01.a0192a,a01.zgxw,a01.zgxl,a01.qrzxl,a01.a0192c,a01.a0288,a0192f ";
		
		String sql = "select b0111,b0131,famx00,fxyp02 a0215a,fxyp00,info.tpyjid,info.tpdesc,"
				+ " (select b0104 from b01 where b01.b0111 = a02.a0201b and b01.b0111!='"+b0111+"') laiyuan, "
				+ " t.a0201e rya0201e,a02.a0201e a02a0201e,t.gwa0200,t.rya0200,t.gwmc,"
				+ "count(1) over(partition by fxyp00)  gwcount,"
				+ "rank() over(partition by fxyp00 order by b0111_ry,  sortnum)  rk,"
 + selsql
 + " from v_mntp_sj_gw_ry t,a01,a02 " + liurenTable
 +" where t.a0000 = a01.a0000(+) and t.rya0200=a02.a0200(+) "+liurenWhere
 + " and t.famx00='"+famx00+"' and t.b0111='"+b0111+"'  "
 +" order by fxyp01,b0111_ry,sortnum";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
	
	private void genDataMap(List<List<Map<String, Object>>> b0111s, int i, String fabd00) throws AppException {
		String b0111sel = this.request.getParameter("b0111");
		String famx00sel = this.request.getParameter("famx00");
		for(int jgi=0;jgi<b0111s.size();jgi++){
			
			List<Map<String, Object>> b0111Block = b0111s.get(jgi);
			//判断是否超出下标   超出下标说明该条明细没有单位
			if(b0111Block.size()>i){
				Map<String, Object> cfgMap = b0111Block.get(i);
				String b0111 = cfgMap.get("b0111")==null?"":cfgMap.get("b0111").toString();
				String famx01 = cfgMap.get("famx01")==null?"":cfgMap.get("famx01").toString();
				String famx00 = cfgMap.get("famx00")==null?"":cfgMap.get("famx00").toString();
				if(!"".equals(famx00sel)&&!famx00.equals(famx00sel)){
					continue;
				}
				if(!"".equals(b0111sel)&&!b0111.startsWith(b0111sel)){
					continue;
				}
				if("2".equals(famx01)){
					List<HashMap<String, Object>> dataMap = getTPXX(b0111,famx00);
					if(dataMap.size()!=0){
						//单位名称
						String qxmc = HBUtil.getValueFromTab("b0101", "b01", "b0111='"+b0111+"'");
						this.getExecuteSG().addExecuteCode("addDWMC('"+qxmc+"');");
						//数据
						JSONArray  updateunDataStoreObject2 = JSONArray.fromObject(dataMap);
						this.getExecuteSG().addExecuteCode("addData('"+updateunDataStoreObject2+"');");
					}
				}
				
			}
			
			
		
		}
		
	}
}
