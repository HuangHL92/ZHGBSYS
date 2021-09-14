package com.insigma.siis.local.pagemodel.mntpsj.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.mntpsj.MNTPSJDWPageModel;
import com.insigma.siis.local.pagemodel.mntpsj.MNTPSJOPPageModel;

public class DWInfoUtil {
	
	
	
	
	public static int  showOrgInfo(String fabd00, MNTPSJOPPageModel page)  {
		
		try {
			String selfamx00 = page.getPageElement("selfamx00").getValue();
			String selorg = page.getPageElement("selorg").getValue();
			//['2','干部一处'],['1','干部二处'],['4','干部三处']
			String mntp05 = HBUtil.getValueFromTab("mntp05", "HZ_MNTP_SJFA", "fabd00='"+fabd00+"'");
			List<List<Map<String, Object>>> tpdws = MNTPSJDWPageModel.returnFA(fabd00,selorg,selfamx00,true);
			if(tpdws.size()>0) {
				for(List<Map<String, Object>> tpdw : tpdws){
					for(int i=0;i<tpdw.size();i++) {		
						Map<String, Object> m = tpdw.get(i);
						String b0111 = m.get("b0111")==null?"":m.get("b0111").toString();
						String famx00 = m.get("famx00")==null?"":m.get("famx00").toString();
						String b0131 = m.get("b0131")==null?"":m.get("b0131").toString();
						String sb= showOrgInfo(famx00,b0111,b0131,mntp05);
						
						page.getExecuteSG().addExecuteCode("setTPSM('"+famx00+"','"+b0111+"','"+sb+"');");
								
					}
				}
				
			}
		
			
		}catch (Exception e) {
			page.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static String showOrgInfo(String famx00, String b0111, String b0131, String mntp05) throws RadowException, AppException{
		//判断重复
		Map<String,Object> a0000sMap = new HashMap<String, Object>();
		//统计
		Map<String,Object> mapCount = new HashMap<String, Object>();
		Map<String,Object> gwCount = new HashMap<String, Object>();
		
	
		getJData(famx00,b0111,b0131,a0000sMap,mapCount,gwCount);
  
		String html = "";
		StringBuilder sb = new StringBuilder();
		sb.append("<p class=\"p1\">年龄结构：");
		String fontcolor = "style=\"color:#FF4500\"";
		boolean hasComma = false;
		
		/*if(mapCount.get("ageLT45")!=null){
			hasComma = true;
			sb.append("<span dataType=\"ageLT45\" >45岁及以下<span "+fontcolor+">"+mapCount.get("ageLT45")+"</span>名</span>");
		}*/
		//if(mapCount.get("ageLT43")!=null){
			hasComma = true;
			sb.append("<span dataType=\"ageLT43\" >40岁左右及以下<span "+fontcolor+">"+(mapCount.get("ageLT43")==null?"0":mapCount.get("ageLT43"))+"</span>名</span>");
		//}
		/*if(mapCount.get("ageLT40")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageLT40\" >40岁及以下<span "+fontcolor+">"+mapCount.get("ageLT40")+"</span>名</span>");
		}*/
		if(mapCount.get("ageLT19670201")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageLT19670201\" >1967年2月前出生的<span "+fontcolor+">"+mapCount.get("ageLT19670201")+"</span>名</span>");
		}
		if(hasComma){
			sb.append("。");
		}
		sb.append("</p><p class=\"p1\">结构性干部：");
		hasComma = false;
		//if(mapCount.get("female")!=null){
			hasComma = true;
			sb.append("<span dataType=\"female\" >女干部<span "+fontcolor+">"+(mapCount.get("female")==null?"0":mapCount.get("female"))+"</span>名</span>");
		//}
		
		if(mapCount.get("noZGParty")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"noZGParty\" >党外干部<span "+fontcolor+">"+mapCount.get("noZGParty")+"</span>名</span>");
		}
	
		//具有乡镇街道党政正职经历的x人
		//if(mapCount.get("XZJDDZZZ")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"XZJDDZZZ\" >具有乡镇街道党政正职经历的<span "+fontcolor+">"+(mapCount.get("XZJDDZZZ")==null?"0":mapCount.get("XZJDDZZZ"))+"</span>名</span>");
			
			//if(mapCount.get("QXSDZZZ")!=null){
				hasComma = true;
				sb.append("<span dataType=\"QXSDZZZ\" >（其中区县市党政正职中有<span "+fontcolor+">"+(mapCount.get("QXSDZZZ")==null?"0":mapCount.get("QXSDZZZ"))+"</span>名）</span>");
			
			
			//}
		
		//}
		
		if(hasComma){
			sb.append("。");
		}
		sb.append("</p><br/>");
		if(gwCount.size()>0){
			sb.append("<p class=\"p1\">");
			for(String gwkey : gwCount.keySet()){
				List<String> o = (List<String>)gwCount.get(gwkey);
				/*if(o.size()<=1){
					continue;
				}*/
				StringBuilder gwsb = new StringBuilder(gwkey+"（"+(o.size())+"选1）：");
				for(int i=1;i<=o.size();i++){
					gwsb.append(i+"、"+o.get(i-1)+"，");
				}
				gwsb.deleteCharAt(gwsb.length()-1);
				gwsb.append("；");
				sb.append(gwsb+"<br/>");
				
			}
			sb.delete(sb.length()-6, sb.length());
			sb.append("。</p>");
		}
		
		
		html = sb.toString();
	
		return html;
	}



	private static void getJData(String famx00, String b0111, String b0131, Map<String, Object> a0000sMap,
			Map<String, Object> mapCount, Map<String, Object> gwCount) throws AppException {
		
		
		//String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
		List<HashMap<String, Object>> listCode =  getTPXX(b0111, famx00, b0131);
		
		
		//遍历人员  重复人员不算
		for(HashMap<String, Object> dataMap : listCode){
			String a0000 = dataMap.get("a0000")==null?"":dataMap.get("a0000").toString();
			String a0215a = dataMap.get("a0215a")==null?"":dataMap.get("a0215a").toString();
			if(StringUtils.isEmpty(a0000)){
				continue;
			}
			if(a0000sMap.get(a0000)==null&&!"人武部主官".equals(a0215a)){
				a0000sMap.put(a0000, "");
				String age = dataMap.get("age")==null?"0":dataMap.get("age").toString();
				
				/*String dataKey = "ageCount";
				//总年龄
				addCount(mapCount,dataKey,Integer.valueOf(age),a0000);*/
				String dataKey = "ageLT45";
				if(Integer.valueOf(age)<=45 ){
					addCount(mapCount,dataKey,1);
					//其中35岁左右党政领导干部几名
					dataKey = "ageLT40";
					if(Integer.valueOf(age)<40){
						addCount(mapCount,dataKey,1);
					}
				}
				
				dataKey = "ageLT43";
				if(Integer.valueOf(age)<43 ){
					addCount(mapCount,dataKey,1);
				}
				
				dataKey = "ageLT19670201";
				String a0107 = dataMap.get("a0107")==null?"000000":dataMap.get("a0107").toString();
				if(a0107.length()<=6){
					a0107 = a0107 + "01";
				}
				if(a0107.compareTo("19670201")<0 ){
					addCount(mapCount,dataKey,1);
				}
				
				
				
				//女干部几名
				dataKey = "female";
				String a0104 = dataMap.get("a0104")==null?"0":dataMap.get("a0104").toString();
				if("2".equals(a0104)){
					addCount(mapCount,dataKey,1);
				}
				//党外干部几名
				dataKey = "noZGParty";
				String a0141 = dataMap.get("a0141")==null?"0":dataMap.get("a0141").toString();
				if(!"01".equals(a0141)){
					addCount(mapCount,dataKey,1);
				}
				//XZJDDZZZ
				dataKey = "XZJDDZZZ";
				String dzzz = dataMap.get("dzzz")==null?"0":dataMap.get("dzzz").toString();
				if("1".equals(dzzz)){
					addCount(mapCount,dataKey,1);
					
					dataKey = "QXSDZZZ";
					String qxsdzzz = dataMap.get("a0201e")==null?"0":dataMap.get("a0201e").toString();
					if("1".equals(qxsdzzz)){
						addCount(mapCount,dataKey,1);
					}
				}
				
				
				
				
				
			}
			
			//单岗位人选数
			String gwcount = dataMap.get("gwcount")==null?"":dataMap.get("gwcount").toString();
			String xingming = dataMap.get("xingming")==null?"":dataMap.get("xingming").toString();
			//人选排序
			//String rk = dataMap.get("rk")==null?"":dataMap.get("rk").toString();
			//String a0215a = dataMap.get("a0215a")==null?"":dataMap.get("a0215a").toString();
			if(Integer.valueOf(gwcount)>1){
				addCount(gwCount,a0215a,1,xingming);
			}
			
		}
		
	}
	
	
	
	private static void addCount(Map<String, Object> mapCount, String dataKey, int count, String xingming) {
		List<String> o = null;
		if(mapCount.get(dataKey)!=null){
			o = (List<String>)mapCount.get(dataKey);
			
		}else{
			o = new ArrayList<String>();
			mapCount.put(dataKey, o);
		}
		o.add(xingming);
		
	}



	public static void addCount(Map<String, Object> mapCount, String dataKey, Integer count){
		if(mapCount.get(dataKey)!=null){
			mapCount.put(dataKey, (Integer)mapCount.get(dataKey)+count);
		}else{
			mapCount.put(dataKey, count);
		}
	}
	
	
	
	
	
	
	
	
	
	public static List<HashMap<String, Object>> getTPXX(String b0111, String famx00, String b0131) throws AppException {
		String b0131sql = " and t.b0111='"+b0111+"' ";
		if(b0111.startsWith("001.001.004")&&b0111.length()==19&&("1001".equals(b0131) || "1004".equals(b0131))) {
			b0131sql = " and substr(t.b0111,0,15)='"+(b0111.substring(0,15))+"' and t.b0131 in ('1001','1004') ";
		}
		
		//String liurenTable = ",(select tpdesc,infoid tpyjid,a0200 from HZ_MNTP_SJFA_INFO where famx00f='"+famx00+"') info ";
		//String liurenWhere = " and info.a0200(+)=t.fxyp00 ";
		
		String sql = "select "
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age,"
				+ "count(1) over(partition by fxyp00)  gwcount,"
				//+ "rank() over(partition by fxyp00 order by b0111_ry,  sortnum)  rk,"
 +" a01.a0107,a0101 xingming,"
				
 + "  (select max('1') from hz_a17 b where b.a0000=a01.a0000 and a1705 in ('0901','0902')) dzzz,"
 +" t.a0000,fxyp02 a0215a,a01.a0104,a01.a0141,t.a0201e "
 

 + " from v_mntp_sj_gw_ry t,a01 " //+ liurenTable
 +" where t.a0000 = a01.a0000(+)  "//+liurenWhere
 + " and t.famx00='"+famx00+"'  "+b0131sql //+"and t.bzgw not in('201','202','209')"
 +" order by fxyp01,b0111_ry,sortnum";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(sql);
		return nmzwList;
	}
}
