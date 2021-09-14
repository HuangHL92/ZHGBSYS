package com.insigma.siis.local.pagemodel.amain;

import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class GBMainPageModel extends PageModel {
	

	GBMainBS bs = new GBMainBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("nljgPie");
		this.setNextEventName("warning");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	@Synchronous(true)
	public int initX()throws RadowException, AppException{
		try {
			/*Object sgbb_count_o = bs.getCountInfo(bs.countFiled,"SGGB");
			Object szdw_count_o = bs.getCountInfo(bs.countFiled,"SZDW");
			Object qxs_count_o = bs.getCountInfo(bs.countFiled,"QXS");
			Object gqgx_count_o = bs.getCountInfo(bs.countFiled,"GQGX");
			Object sgzz_count_o = bs.getCountInfo(bs.countFiled,"SGZZ");
			Object sgfz_count_o = bs.getCountInfo(bs.countFiled,"SGFZ");
			Object sgqt_count_o = bs.getCountInfo(bs.countFiled,"SGQT");*/
			HashMap<String, Object> m = bs.getCountInfoAll();
			Object sgbb_count_o = m.get("sggb");
			Object szdw_count_o = m.get("szdw");
			Object qxs_count_o = m.get("qxs");
			Object gqgx_count_o = m.get("gqgx");
			Object sgzz_count_o = m.get("sgzz");
			Object sgfz_count_o = m.get("sgfz");
			Object sgqt_count_o = m.get("sgqt");
			this.getExecuteSG().addExecuteCode("initData({'sgbb_count':"+sgbb_count_o+","
					+ "'szdw_count':"+szdw_count_o+","
					+ "'gqgx_count':"+gqgx_count_o+","
					+ "'sgzz_count':"+sgzz_count_o+","
					+ "'sgfz_count':"+sgfz_count_o+","
					+ "'sgqt_count':"+sgqt_count_o+","
					+ "'qxs_count':"+qxs_count_o+""
							+ "})");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化市管干部统计失败");
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("nljgPie")
	@NoRequiredValidate
	@Synchronous(true)
	public int nljgPie()throws RadowException, AppException{
		try {
			HashMap<String, Object> m = bs.getCountAgeInfoAll();
			//JSONArray  updateunDataStoreObject = JSONArray.fromObject(m);
			String json = JSON.toJSONString(m);
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-1',"+json+");");
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-3',"+json+");");
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-2',"+json+");");
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-4',"+json+");");
			List<HashMap<String, String>> ml = bs.getCountZhuanYeInfoAll();
			json = JSON.toJSONString(ml);
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-5',"+json+");");
			//经历业绩
			HashMap<String, Object> m1 = bs.getCountJLYJ();
			String json1 = JSON.toJSONString(m1);
			this.getExecuteSG().addExecuteCode("initEchartsData('chart1-6',"+json1+");");
			//JSONArray  updateunDataStoreObject = JSONArray.fromObject(m);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化年龄统计失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("warning")
	@NoRequiredValidate
	@Synchronous(true)
	public int warning()throws RadowException, AppException{
		try {
			String sql=" select count(1) as dl" + 
					"  from (SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a" + 
					"          FROM (SELECT T.newA0107 Birthday," + 
					"                       T.Today," + 
					"                       MONTHS_BETWEEN(T.Today, T.newA0107) age," + 
					"                       T.a0104 sex," + 
					"                       T.a0000" + 
					"                  FROM (SELECT b.a0104," + 
					"                               b.A0000," + 
					"                               to_date((SUBSTR(RPAD(b.A0107, 8, '01'), 0, 6))," + 
					"                                       'yyyyMM') newA0107," + 
					"                               to_date(TO_CHAR(sysdate, 'yyyyMM'), 'yyyyMM') Today" + 
					"                          FROM ZHGBSYS.a01 b" + 
					"                         WHERE b.a0163 = '1'" + 
					"                           and (b.A0165 like '%10%' or b.A0165 like '%11%' or" + 
					"                               b.A0165 like '%18%' or b.A0165 like '%19%')" + 
					"                           AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8)) T) Aa," + 
					"               ZHGBSYS.a01" + 
					"         WHERE 720 = Aa.age" + 
					"           AND aa.a0000 = a01.a0000) t" + 
					" union all" + 
					" select   count(1) as dl from a01 where a0192a like '%试用期%' and a0165 like '%11%' " + 
					" and  MONTHS_BETWEEN(to_date(to_char(sysdate,'yyyymm'),'yyyymm'),to_date(substr(a0288,0,6),'yyyymm'))>=12";
			@SuppressWarnings("unchecked")
			List<String> sum= HBUtil.getHBSession().createSQLQuery(sql).list();
			boolean flag=false;
			for(int i=0;i<sum.size();i++) {
				int num=Integer.valueOf(String.valueOf(sum.get(i)));
				if(num>0) {
					flag=true;
				}
			}
			if(flag==true) {
				this.getExecuteSG().addExecuteCode("$('#yjtx').addClass('jg')");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化年龄统计失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
