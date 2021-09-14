package com.insigma.siis.local.pagemodel.customquery;


import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.amain.DTZBUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class DTJCZBPageModel extends PageModel{
	
	
	@Override
	public int doInit() throws RadowException {
		CommQuery cqbs=new CommQuery();
		DecimalFormat df = new DecimalFormat("0.00%");
		StringBuilder js = new StringBuilder();
		try {
			
			
			//指标2,3
			String sql_2_3 = DTZBUtils.getv_hz_dtzb_2()[0];
			List<HashMap<String, String>> ret_2_3 = cqbs.getListBySQL2(sql_2_3);
			if(ret_2_3.size()>0){
				HashMap<String, String> countMap = ret_2_3.get(0);
				double ageCount = Double.valueOf(countMap.get("市直市管干部"));
				double age47 = Integer.valueOf(countMap.get("市直市管干部45"));
				double age42 = Integer.valueOf(countMap.get("市直市管干部40"));
				String r1 = df.format(age47/ageCount);
				String r2 = df.format(age42/ageCount);
				js.append("$('.count2').text('"+r1+" / " + r2+"');");
				
				double 市直处级干部 = Double.valueOf(countMap.get("市直处级干部"));
				double age40 = Integer.valueOf(countMap.get("市直处级干部40"));
				double age37 = Integer.valueOf(countMap.get("市直处级干部35"));
				String r3 = df.format(age40/市直处级干部);
				String r4 = df.format(age37/市直处级干部);
				js.append("$('.count3').text('"+r3+" / " + r4+"');");
			}
			
			
			
			
			//指标6,7,8,9
			String sql_6 = DTZBUtils.getSQLZB_QXSXZ()[3];
			List<HashMap<String, String>> ret6 = cqbs.getListBySQL2(sql_6);
			if(ret6.size()>0){
				HashMap<String, String> countMap = ret6.get(0);
				double ageCount = Double.valueOf(countMap.get("实配"));
				double age35 = Integer.valueOf(countMap.get("三十五岁以下"));
				String r1 = df.format(age35/ageCount);
				js.append("$('.count6').text('"+r1+"');");
				
				String age30 = countMap.get("三十岁以下");
				js.append("$('.count7').text('"+age30+"');");
				
				double dzzz = Double.valueOf(countMap.get("党政正职"));
				double dzzzage35 = Integer.valueOf(countMap.get("三十五岁党政正职"));
				String r2 = df.format(dzzzage35/dzzz);
				js.append("$('.count8').text('"+r2+"');");
				
				double dzzzage35sj = Integer.valueOf(countMap.get("三十五岁党政正职党委书记"));
				String r3 = df.format(dzzzage35sj/dzzzage35);
				js.append("$('.count9').text('"+r3+"');");
			}
			
			
			
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）全市
			String sql_9 = DTZBUtils.getv_hz_dtzb_9()[0];
			List<HashMap<String, String>> ret9 = cqbs.getListBySQL2(sql_9);
			if(ret9.size()>0){
				HashMap<String, String> countMap = ret9.get(0);
				//指标13
				double 市管新提任40 = Integer.valueOf(countMap.get("市管新提任40"));
				int 市管新提任40全日制 = Integer.valueOf(countMap.get("市管新提任40全日制"));
				String r1 = df.format(市管新提任40全日制/市管新提任40);
				js.append("$('.count13').text('"+r1+"');");
				//指标14
				double 市直处级新提任40 = Integer.valueOf(countMap.get("新提任40处级"));
				int 市直新40处级全日制 = Integer.valueOf(countMap.get("新40处级全日制"));
				String r2 = df.format(市直新40处级全日制/市直处级新提任40);
				js.append("$('.count14').text('"+r2+"');");
				//指标15
				double 区县处级新提任40 = Integer.valueOf(countMap.get("区县处级新提任40"));
				int 区县新40处级全日制 = Integer.valueOf(countMap.get("区县新40处级全日制"));
				String r3 = df.format(区县新40处级全日制/区县处级新提任40);
				js.append("$('.count15').text('"+r3+"');");
				
				//指标12
				double 市管干部 = Integer.valueOf(countMap.get("市管干部"));
				int 市管全日制 = Integer.valueOf(countMap.get("市管全日制"));
				String r4 = df.format(市管全日制/市管干部);
				js.append("$('.count12').text('"+r4+"');");
				//指标21
				double 总职数 = Integer.valueOf(countMap.get("总职数"));
				int 总空缺 = Integer.valueOf(countMap.get("总空缺"));
				String r5 = df.format(总空缺/总职数);
				js.append("$('.count21').text('"+r5+"');");
			}
			
			//指标4,5,16
			String sql_16 = DTZBUtils.getSQLZB_QXS()[1];
			List<HashMap<String, String>> ret16 = cqbs.getListBySQL2(sql_16);
			if(ret16.size()>0){
				HashMap<String, String> countMap = ret16.get(0);
				double ageCount = Double.valueOf(countMap.get("单位数"));
				double perc = Integer.valueOf(countMap.get("乡镇街道党政正职达标单位"));
				String r1 = df.format(perc/ageCount);
				js.append("$('.count16').text('"+r1+"');");
				//指标4
				int qxdzzz = Integer.valueOf(countMap.get("党政正职"));
				double qxdzzzage = Integer.valueOf(countMap.get("四十岁左右党政正职"));
				String r4 = df.format(qxdzzzage/qxdzzz);
				js.append("$('.count4').text('"+r4+"');");
				//指标5
				double sp = Double.valueOf(countMap.get("实配"));
				int age42 = Integer.valueOf(countMap.get("四十岁左右"));
				String r5 = df.format(age42/sp);
				js.append("$('.count5').text('"+r5+"');");
			}
			
			String sql_17 = DTZBUtils.getSQLZB_QXS_NVDW()[1];
			List<HashMap<String, String>> ret17 = cqbs.getListBySQL2(sql_17);
			if(ret17.size()>0){
				HashMap<String, String> countMap = ret17.get(0);
				String r1 = countMap.get("党委女干部总数");
				js.append("$('.count17').text('"+r1+"');");
				
				String r2 = countMap.get("政府女干部总数");
				js.append("$('.count18').text('"+r2+"');");
				
				double ageCount = Double.valueOf(countMap.get("政协干部总数"));
				double dwgb = Integer.valueOf(countMap.get("政协党外干部总数"));
				String r3 = df.format(dwgb/ageCount);
				js.append("$('.count20').text('"+r3+"');");
			}
			
			
			String sql_19 = DTZBUtils.getSQLZB_QXS_NVDW()[2];
			List<HashMap<String, String>> ret19 = cqbs.getListBySQL2(sql_19);
			if(ret19.size()>0){
				HashMap<String, String> countMap = ret19.get(0);
				double ageCount = Double.valueOf(countMap.get("班子总数"));
				double ngb = Integer.valueOf(countMap.get("女干部班子总数"));
				String r1 = df.format(ngb/ageCount);
				js.append("$('.count19').text('"+r1+"');");
			}
			
			
			
			this.getExecuteSG().addExecuteCode(js.toString());
		} catch (AppException e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static void main(String[] args) {
        double d = 0.2365566789;
 
        //最简便的方法，调用DecimalFormat类
        DecimalFormat df = new DecimalFormat("0.00%");
        System.out.println(df.format(d));
    }

	
}
