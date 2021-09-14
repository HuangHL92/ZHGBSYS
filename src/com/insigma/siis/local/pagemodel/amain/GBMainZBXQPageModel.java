package com.insigma.siis.local.pagemodel.amain;

import java.text.DecimalFormat;
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
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GBMainZBXQPageModel extends PageModel {
	

	GBMainBS bs = new GBMainBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	@Synchronous(true)
	public int initX()throws RadowException, AppException{
		StopWatch sw = new StopWatch();
		sw.start();
		try {
			CommQuery cqbs=new CommQuery();
			DecimalFormat df = new DecimalFormat("0.00%");
			StringBuilder js = new StringBuilder();
			
			
			
			//四、区、县（市）班子有关指标（指标4、5、16）
			//党政领导班子总统计
			String sql_ZB_QXS_QB = DTZBUtils.getSQLZB_QXS()[1];
			List<HashMap<String, String>> ret5 = cqbs.getListBySQL2(sql_ZB_QXS_QB);
			if(ret5.size()>0){
				HashMap<String, String> countMap = ret5.get(0);
				int ageCount2 = Integer.valueOf(countMap.get("总职数"));
				int jcrz = Integer.valueOf(countMap.get("交叉任职"));
				double ageCount = Double.valueOf(countMap.get("实配"));
				int age42 = Integer.valueOf(countMap.get("四十岁左右"));
				int dzzz = Integer.valueOf(countMap.get("具有乡镇街道党政正职"));
				int dzzzdb = Integer.valueOf(countMap.get("乡镇街道党政正职达标单位"));
				double dws = Integer.valueOf(countMap.get("单位数"));
				int qxdzzz = Integer.valueOf(countMap.get("党政正职"));
				double qxdzzzage = Integer.valueOf(countMap.get("四十岁左右党政正职"));
				String r1 = df.format(age42/ageCount);
				String r2 = df.format(dzzz/ageCount);
				String r3 = df.format(dzzzdb/dws);
				String r4 = df.format(qxdzzzage/qxdzzz);
				js.append("$('.zb4a1').text('"+ageCount2+"');");
				js.append("$('.zb4a2').text('"+(int)ageCount+"');");
				js.append("$('.zb4a3').text('"+(int)qxdzzzage+"');");
				js.append("$('.zb4a4').text('"+r4+"');");
				js.append("$('.zb4a5').text('"+age42+"');");
				js.append("$('.zb4a6').text('"+r1+"');");
				//具有乡镇（街道）党政正职经历的党政领导班子成员
				js.append("$('.zb4a7').text('"+dzzz+"');");
				js.append("$('.zb4a8').text('"+r2+"');");
				js.append("$('.zb4a9').text('"+dzzzdb+"');");
				js.append("$('.zb4a10').text('"+r3+"');");
				js.append("$('.zb4a11').text('"+jcrz+"');");
				
			}
			//党政领导班子分区县市
			String sql_ZB_QXS = DTZBUtils.getSQLZB_QXS()[0];
			List<HashMap<String, String>> ret_ZB_QXS = cqbs.getListBySQL2(sql_ZB_QXS);
			if(ret_ZB_QXS.size()>0){
				String json = JSON.toJSONString(ret_ZB_QXS);
				this.getExecuteSG().addExecuteCode("addZBXX4("+json+");");
			}
			
			
			
			
			//乡镇领导班子有关指标（指标6、7、8、9）乡镇街道
			String sql_ZB_QXS_XZJD_qb = DTZBUtils.getSQLZB_QXSXZ()[4];
			List<HashMap<String, String>> retQXS_XZJD_qb = cqbs.getListBySQL2(sql_ZB_QXS_XZJD_qb);
			if(retQXS_XZJD_qb.size()>0){
				String json = JSON.toJSONString(retQXS_XZJD_qb);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb7','全市',1,0);");
			}
			String sql_ZB_QXS_XZJD = "select b01.b0101,t.* from b01 join ("+DTZBUtils.getSQLZB_QXSXZ()[0]+") t on t.b0111=b01.b0111 order by b0269";
			List<HashMap<String, String>> ret_ZB_QXS_XZJD = cqbs.getListBySQL2(sql_ZB_QXS_XZJD);
			if(ret_ZB_QXS_XZJD.size()>0){
				String json = JSON.toJSONString(ret_ZB_QXS_XZJD);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb7',false,2);");
			}
			//乡镇领导班子有关指标（指标6、7、8、9）乡镇
			String sql_ZB_QXS_XZ_qb = DTZBUtils.getSQLZB_QXSXZ()[5];
			List<HashMap<String, String>> retQXS_XZ_qb = cqbs.getListBySQL2(sql_ZB_QXS_XZ_qb);
			if(retQXS_XZ_qb.size()>0){
				String json = JSON.toJSONString(retQXS_XZ_qb);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb5','全市',1,0);");
			}
			String sql_ZB_QXS_XZ = "select b01.b0101,t.* from b01 join ("+DTZBUtils.getSQLZB_QXSXZ()[1]+") t on t.b0111=b01.b0111 order by b0269";
			List<HashMap<String, String>> ret_ZB_QXS_XZ = cqbs.getListBySQL2(sql_ZB_QXS_XZ);
			if(ret_ZB_QXS_XZ.size()>0){
				String json = JSON.toJSONString(ret_ZB_QXS_XZ);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb5',false,2);");
			}
			//乡镇领导班子有关指标（指标6、7、8、9）街道
			String sql_ZB_QXS_JD_qb = DTZBUtils.getSQLZB_QXSXZ()[6];
			List<HashMap<String, String>> retQXS_JD_qb = cqbs.getListBySQL2(sql_ZB_QXS_JD_qb);
			if(retQXS_JD_qb.size()>0){
				String json = JSON.toJSONString(retQXS_JD_qb);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb6','全市',1,0);");
			}
			String sql_ZB_QXS_JD = "select b01.b0101,t.* from b01 join ("+DTZBUtils.getSQLZB_QXSXZ()[2]+") t on t.b0111=b01.b0111 order by b0269";
			List<HashMap<String, String>> ret_ZB_QXS_JD = cqbs.getListBySQL2(sql_ZB_QXS_JD);
			if(ret_ZB_QXS_JD.size()>0){
				String json = JSON.toJSONString(ret_ZB_QXS_JD);
				this.getExecuteSG().addExecuteCode("addZBXX7("+json+",'.tablezb6',false,2);");
			}
			
			
			
			//8.结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）全市
			String sql_8 = DTZBUtils.getSQLZB_QXS_NVDW()[1];
			List<HashMap<String, String>> ret8 = cqbs.getListBySQL2(sql_8);
			if(ret8.size()>0){
				HashMap<String, String> countMap = ret8.get(0);
				Integer dzn = Integer.valueOf(countMap.get("党政女干部总数"));
				Integer dn = Integer.valueOf(countMap.get("党委女干部总数"));
				Integer zn = Integer.valueOf(countMap.get("政府女干部总数"));
				js.append("$('.zb8a1').text('"+dzn+"');");
				js.append("$('.zb8a2').text('"+dn+"');");
				js.append("$('.zb8a3').text('"+zn+"');");
			}
			//8.结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）市直
			String sql_19 = DTZBUtils.getSQLZB_QXS_NVDW()[2];
			List<HashMap<String, String>> ret19 = cqbs.getListBySQL2(sql_19);
			if(ret19.size()>0){
				HashMap<String, String> countMap = ret19.get(0);
				double ageCount = Double.valueOf(countMap.get("班子总数"));
				double ngb = Integer.valueOf(countMap.get("女干部班子总数"));
				String r1 = df.format(ngb/ageCount);
				js.append("$('.zb8a4').text('"+(int)ageCount+"');");
				js.append("$('.zb8a5').text('"+(int)ngb+"');");
				js.append("$('.zb8a6').text('"+r1+"');");
			}
			//8.结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）
			String sql_ZB_QXS_DZNGB = "select b01.b0101,t.* from b01 join ("+DTZBUtils.getSQLZB_QXS_NVDW()[0]+") t on t.b0111=b01.b0111 order by b0269";
			List<HashMap<String, String>> ret_ZB_QXS_DZNGB = cqbs.getListBySQL2(sql_ZB_QXS_DZNGB);
			if(ret_ZB_QXS_DZNGB.size()>0){
				String json = JSON.toJSONString(ret_ZB_QXS_DZNGB);
				this.getExecuteSG().addExecuteCode("addZBXX8("+json+",'.tablezb8');");
			}
			
			
			
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）全市
			String sql_9 = DTZBUtils.getv_hz_dtzb_9()[0];
			List<HashMap<String, String>> ret9 = cqbs.getListBySQL2(sql_9);
			if(ret9.size()>0){
				HashMap<String, String> countMap = ret9.get(0);
				double 市管新提任40 = Integer.valueOf(countMap.get("市管新提任40"));
				int 市管新提任40全日制 = Integer.valueOf(countMap.get("市管新提任40全日制"));
				String r1 = df.format(市管新提任40全日制/市管新提任40);
				js.append("$('.zb9r1a1').text('"+市管新提任40全日制+"');");
				js.append("$('.zb9r1a2').text('"+r1+"');");
				
				double 市直处级新提任40 = Integer.valueOf(countMap.get("新提任40处级"));
				int 市直新40处级全日制 = Integer.valueOf(countMap.get("新40处级全日制"));
				String r2 = df.format(市直新40处级全日制/市直处级新提任40);
				js.append("$('.zb9r1a3').text('"+市直新40处级全日制+"');");
				js.append("$('.zb9r1a4').text('"+r2+"');");
				
				double 区县处级新提任40 = Integer.valueOf(countMap.get("区县处级新提任40"));
				int 区县新40处级全日制 = Integer.valueOf(countMap.get("区县新40处级全日制"));
				String r3 = df.format(区县新40处级全日制/区县处级新提任40);
				js.append("$('.zb9r1a5').text('"+区县新40处级全日制+"');");
				js.append("$('.zb9r1a6').text('"+r3+"');");
				
				double 市管干部 = Integer.valueOf(countMap.get("市管干部"));
				int 市管全日制 = Integer.valueOf(countMap.get("市管全日制"));
				String r4 = df.format(市管全日制/市管干部);
				js.append("$('.zb9r1a7').text('"+市管全日制+"');");
				js.append("$('.zb9r1a8').text('"+r4+"');");
				
				double 总职数 = Integer.valueOf(countMap.get("总职数"));
				int 总空缺 = Integer.valueOf(countMap.get("总空缺"));
				String r5 = df.format(总空缺/总职数);
				js.append("$('.zb9r1a10').text('"+(int)总职数+"');");
				js.append("$('.zb9r1a11').text('"+总空缺+"');");
				js.append("$('.zb9r1a12').text('"+r5+"');");
			}
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）市直全部
			String sql_szqb = DTZBUtils.getv_hz_dtzb_9()[1];
			List<HashMap<String, String>> retszqb = cqbs.getListBySQL2(sql_szqb);
			int rowIndex = 2;
			if(retszqb.size()>0){
				String json = JSON.toJSONString(retszqb);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9','市直部门',"+rowIndex+",'1');");
			}
			rowIndex++;
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）市直单位
			/*String sql_szdw = DTZBUtils.getv_hz_dtzb_9()[2];
			List<HashMap<String, String>> retszdw = cqbs.getListBySQL2(sql_szdw);
			if(retszdw.size()>0){
				String json = JSON.toJSONString(retszdw);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+");");
			}
			rowIndex += retszdw.size()+1;*/
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）国企全部
			String sql_gqqb = DTZBUtils.getv_hz_dtzb_9()[3];
			List<HashMap<String, String>> retgqqb = cqbs.getListBySQL2(sql_gqqb);
			if(retgqqb.size()>0){
				String json = JSON.toJSONString(retgqqb);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9','国企',"+rowIndex+",'2');");
			}
			rowIndex++;
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）国企单位
			/*String sql_gqdw = DTZBUtils.getv_hz_dtzb_9()[4];
			List<HashMap<String, String>> retgqdw = cqbs.getListBySQL2(sql_gqdw);
			if(retgqdw.size()>0){
				String json = JSON.toJSONString(retgqdw);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+");");
			}
			rowIndex += retgqdw.size()+1;*/
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）高校全部
			String sql_gxqb = DTZBUtils.getv_hz_dtzb_9()[5];
			List<HashMap<String, String>> retgxqb = cqbs.getListBySQL2(sql_gxqb);
			if(retgxqb.size()>0){
				String json = JSON.toJSONString(retgxqb);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9','高校',"+rowIndex+",'3');");
			}
			rowIndex++;
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）高校单位
			/*String sql_gxdw = DTZBUtils.getv_hz_dtzb_9()[6];
			List<HashMap<String, String>> retgxdw = cqbs.getListBySQL2(sql_gxdw);
			if(retgxdw.size()>0){
				String json = JSON.toJSONString(retgxdw);
				this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+");");
			}
			rowIndex += retgxdw.size()+1;*/
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）区县全部
			String sql_qxqb = DTZBUtils.getv_hz_dtzb_9()[7];
			List<HashMap<String, String>> retqxqb = cqbs.getListBySQL2(sql_qxqb);
			if(retqxqb.size()>0){
				String json = JSON.toJSONString(retqxqb);
				this.getExecuteSG().addExecuteCode("addZBXX9_qxqb("+json+",'.tablezb9','区、县（市）',"+rowIndex+",'4');");
			}
			rowIndex++;
			//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）区县单位
			/*String sql_qxdw = DTZBUtils.getv_hz_dtzb_9()[8];
			List<HashMap<String, String>> retqxdw = cqbs.getListBySQL2(sql_qxdw);
			if(retqxdw.size()>0){
				String json = JSON.toJSONString(retqxdw);
				this.getExecuteSG().addExecuteCode("addZBXX9_qxqb("+json+",'.tablezb9',false,"+rowIndex+");");
			}*/
			
			
			
			//二、市直单位领导干部有关指标（指标2） 全市
			rowIndex = 1;
			String sql_2qs = DTZBUtils.getv_hz_dtzb_2()[0];
			List<HashMap<String, String>> ret2qs = cqbs.getListBySQL2(sql_2qs);
			if(ret2qs.size()>0){
				String json = JSON.toJSONString(ret2qs);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2','全市',"+rowIndex+",'0');");
			}
			//二、市直单位领导干部有关指标（指标2） 市直全部
			rowIndex++;
			String sql_2szqb = DTZBUtils.getv_hz_dtzb_2()[1];
			List<HashMap<String, String>> ret2szqb = cqbs.getListBySQL2(sql_2szqb);
			rowIndex++;
			if(ret2szqb.size()>0){
				String json = JSON.toJSONString(ret2szqb);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2','市直部门',"+rowIndex+",'1');");
			}
			rowIndex++;
			//二、市直单位领导干部有关指标（指标2）市直单位
			/*String sql_2szdw = DTZBUtils.getv_hz_dtzb_2()[2];
			List<HashMap<String, String>> ret2szdw = cqbs.getListBySQL2(sql_2szdw);
			if(ret2szdw.size()>0){
				String json = JSON.toJSONString(ret2szdw);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+");");
			}
			rowIndex += ret2szdw.size()+1;*/
			//二、市直单位领导干部有关指标（指标2）国企全部
			String sql_2gqqb = DTZBUtils.getv_hz_dtzb_2()[3];
			List<HashMap<String, String>> ret2gqqb = cqbs.getListBySQL2(sql_2gqqb);
			if(ret2gqqb.size()>0){
				String json = JSON.toJSONString(ret2gqqb);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2','国企',"+rowIndex+",'2');");
			}
			rowIndex++;
			//二、市直单位领导干部有关指标（指标2）国企单位
			/*String sql_2gqdw = DTZBUtils.getv_hz_dtzb_2()[4];
			List<HashMap<String, String>> ret2gqdw = cqbs.getListBySQL2(sql_2gqdw);
			if(ret2gqdw.size()>0){
				String json = JSON.toJSONString(ret2gqdw);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+");");
			}
			rowIndex += ret2gqdw.size()+1;*/
			//二、市直单位领导干部有关指标（指标2）高校全部
			String sql_2gxqb = DTZBUtils.getv_hz_dtzb_2()[5];
			List<HashMap<String, String>> ret2gxqb = cqbs.getListBySQL2(sql_2gxqb);
			if(ret2gxqb.size()>0){
				String json = JSON.toJSONString(ret2gxqb);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2','高校',"+rowIndex+",'3');");
			}
			rowIndex++;
			//二、市直单位领导干部有关指标（指标2）高校单位
			/*String sql_2gxdw = DTZBUtils.getv_hz_dtzb_2()[6];
			List<HashMap<String, String>> ret2gxdw = cqbs.getListBySQL2(sql_2gxdw);
			if(ret2gxdw.size()>0){
				String json = JSON.toJSONString(ret2gxdw);
				this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+");");
			}*/
			this.getExecuteSG().addExecuteCode("addZBXXClick();addZBXXDWClick();");
			this.getExecuteSG().addExecuteCode(js.toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("初始化失败");
		}
		
		sw.stop();
		System.out.println(sw.elapsedTime());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("showDWData")
	@NoRequiredValidate
	@Synchronous(true)
	public int showDWData(String p)throws RadowException, AppException{
		String[] parm = p.split("@@");
		String b0111 = parm[1];
		String tablename = parm[0];
		String rowIndex = parm[2];
		
		CommQuery cqbs=new CommQuery();
		DecimalFormat df = new DecimalFormat("0.00%");
		StringBuilder js = new StringBuilder();
		if("tablezb2".equals(tablename)){
			if(("1").equals(b0111)){
				//二、市直单位领导干部有关指标（指标2）市直单位
				String sql_2szdw = DTZBUtils.getv_hz_dtzb_2()[2];
				List<HashMap<String, String>> ret2szdw = cqbs.getListBySQL2(sql_2szdw);
				if(ret2szdw.size()>0){
					String json = JSON.toJSONString(ret2szdw);
					this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+",1,true);");
				}
			}else if(("2").equals(b0111)){
				//二、市直单位领导干部有关指标（指标2）国企单位
				String sql_2gqdw = DTZBUtils.getv_hz_dtzb_2()[4];
				List<HashMap<String, String>> ret2gqdw = cqbs.getListBySQL2(sql_2gqdw);
				if(ret2gqdw.size()>0){
					String json = JSON.toJSONString(ret2gqdw);
					this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+",2,true);");
				}
			}else if(("3").equals(b0111)){
				//二、市直单位领导干部有关指标（指标2）高校单位
				String sql_2gxdw = DTZBUtils.getv_hz_dtzb_2()[6];
				List<HashMap<String, String>> ret2gxdw = cqbs.getListBySQL2(sql_2gxdw);
				if(ret2gxdw.size()>0){
					String json = JSON.toJSONString(ret2gxdw);
					this.getExecuteSG().addExecuteCode("addZBXX2("+json+",'.tablezb2',false,"+rowIndex+",3,true);");
				}
			}
		}else if("tablezb9".equals(tablename)){
			if(("1").equals(b0111)){
				//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）市直单位
				String sql_szdw = DTZBUtils.getv_hz_dtzb_9()[2];
				List<HashMap<String, String>> retszdw = cqbs.getListBySQL2(sql_szdw);
				if(retszdw.size()>0){
					String json = JSON.toJSONString(retszdw);
					this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+",1,true);");
				}
			}else if(("2").equals(b0111)){
				//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）国企单位
				String sql_gqdw = DTZBUtils.getv_hz_dtzb_9()[4];
				List<HashMap<String, String>> retgqdw = cqbs.getListBySQL2(sql_gqdw);
				if(retgqdw.size()>0){
					String json = JSON.toJSONString(retgqdw);
					this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+",2,true);");
				}
			}else if(("3").equals(b0111)){
				//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）高校单位
				String sql_gxdw = DTZBUtils.getv_hz_dtzb_9()[6];
				List<HashMap<String, String>> retgxdw = cqbs.getListBySQL2(sql_gxdw);
				if(retgxdw.size()>0){
					String json = JSON.toJSONString(retgxdw);
					this.getExecuteSG().addExecuteCode("addZBXX9_szqb("+json+",'.tablezb9',false,"+rowIndex+",3,true);");
				}
			}else if(("4").equals(b0111)){
				//9干部日常管理有关指标（指标10、12、13、14、15、21、22、27、28）区县单位
				String sql_qxdw = DTZBUtils.getv_hz_dtzb_9()[8];
				List<HashMap<String, String>> retqxdw = cqbs.getListBySQL2(sql_qxdw);
				if(retqxdw.size()>0){
					String json = JSON.toJSONString(retqxdw);
					this.getExecuteSG().addExecuteCode("addZBXX9_qxqb("+json+",'.tablezb9',false,"+rowIndex+",4,true);");
				}
			}
		}
		
		this.getExecuteSG().addExecuteCode("addZBXXClick();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
