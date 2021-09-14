package com.insigma.siis.local.pagemodel.fxyp.base;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONObject;

public class JZHJ extends PageModel{
	private PageModel pm;
	public JZHJ(PageModel pageModel) {
		this.pm = pageModel;
	}

	
	public void getEChartsInfo(Map<String, Object> a0000sMap, Map<String, Object> mapCount, Map<String, List<String>> reverseSearchMap) throws RadowException, AppException{
		
		String mntp00 = this.pm.request.getParameter("mntp00");
		String mntp05 = this.pm.request.getParameter("mntp05");
		
		
		String b0111 = this.pm.request.getParameter("b0111");
		//String b01id = this.pm.request.getParameter("b01id");
		String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
		String all = this.pm.request.getParameter("all");
		if("1".equals(all)){
			b0111 = "001.001.004";//区县市
			b01id = "";
		}
	
		this.getJData(mntp00,mntp05,b01id,b0111,a0000sMap,mapCount,reverseSearchMap,false);
	}
	public void getEChartsInfo2(Map<String, Object> a0000sMap, Map<String, Object> mapCount, Map<String, List<String>> reverseSearchMap) throws RadowException, AppException{
		
		String mntp00 = this.pm.request.getParameter("mntp00");
		String mntp05 = this.pm.request.getParameter("mntp05");
		
		
		String b0111 = this.pm.request.getParameter("b0111");
		//String b01id = this.pm.request.getParameter("b01id");
		String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
		String all = this.pm.request.getParameter("all");
		if("1".equals(all)){
			b0111 = "001.001.004";//区县市
			b01id = "";
		}
		
		this.getJData(mntp00,mntp05,b01id,b0111,a0000sMap,mapCount,reverseSearchMap,true);
	}
	
	
	public int showOrgInfo() throws RadowException, AppException{
		
		String mntp00 = this.getPageElement("mntp00").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();
		
		
		String b0111 = this.getPageElement("b0111").getValue();
		String b01id = this.getPageElement("b01id").getValue();
  
		//判断重复
		Map<String,Object> a0000sMap = new HashMap<String, Object>();
		//统计
		Map<String,Object> mapCount = new HashMap<String, Object>();
		//反查
		Map<String,List<String>> reverseSearchMap = new HashMap<String, List<String>>();
	
		this.getJData(mntp00,mntp05,b01id,b0111,a0000sMap,mapCount,reverseSearchMap,false);
  
		String html = "";
		StringBuilder sb = new StringBuilder();
		sb.append("<p class=\"p1\">&ensp;&ensp;&ensp;&ensp;年龄结构：");
		String fontcolor = "style=\"color:#FF4500\"";
		boolean hasComma = false;
		if(mapCount.get("ageCount")!=null){
			float avgAge = (float)((Integer)mapCount.get("ageCount"))/a0000sMap.size();
	        DecimalFormat df = new DecimalFormat("0.0");
	        String result = df.format(avgAge);
			sb.append("平均年龄<span "+fontcolor+">"+result+"</span>岁");
			hasComma = true;
		}
		if(mapCount.get("ageLT45")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageLT45\" class=\"reverse-search\">45岁及以下<span "+fontcolor+">"+mapCount.get("ageLT45")+"</span>名</span>");
		}
		if(mapCount.get("ageGT55")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"ageGT55\" class=\"reverse-search\">55岁及以上<span "+fontcolor+">"+mapCount.get("ageGT55")+"</span>名</span>");
		}
		sb.append("</p><p class=\"p1\">&ensp;&ensp;&ensp;&ensp;学历结构：");
		hasComma = false;
		if(mapCount.get("xlxwLev1")!=null){
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev1\" class=\"reverse-search\">研究生及以上<span "+fontcolor+">"+mapCount.get("xlxwLev1")+"</span>名</span>");
		}
		if(mapCount.get("xlxwLev2")!=null){
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev2\" class=\"reverse-search\">（其中博士<span "+fontcolor+">"+mapCount.get("xlxwLev2")+"</span>名）</span>");
		}
		if(mapCount.get("xlxwLev3")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev3\" class=\"reverse-search\">大学<span "+fontcolor+">"+mapCount.get("xlxwLev3")+"</span>名</span>");
		}
		if(mapCount.get("xlxwLev4")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"xlxwLev4\" class=\"reverse-search\">大专及以下<span "+fontcolor+">"+mapCount.get("xlxwLev4")+"</span>名</span>");
		}
		sb.append("</p><p class=\"p1\">&ensp;&ensp;结构性干部：");
		hasComma = false;
		if(mapCount.get("female")!=null){
			hasComma = true;
			sb.append("<span dataType=\"female\" class=\"reverse-search\">女干部<span "+fontcolor+">"+mapCount.get("female")+"</span>名</span>");
		}
		//女干部缺配情况
		if("2".equals(mntp05)){
			if(mapCount.get("female1001")==null||mapCount.get("female1003")==null||mapCount.get("female1004")==null||mapCount.get("female1005")==null){
				hasComma = false;
				sb.append("（");
				if(mapCount.get("female1001")==null){
					hasComma = true;
					sb.append("党委缺配女干部<span "+fontcolor+">1</span>名");
				}
				if(mapCount.get("female1003")==null){
					if(hasComma){
						sb.append("，");
					}
					hasComma = true;
					sb.append("人大缺配女干部<span "+fontcolor+">1</span>名");
				}
				if(mapCount.get("female1004")==null){
					if(hasComma){
						sb.append("，");
					}
					hasComma = true;
					sb.append("政府缺配女干部<span "+fontcolor+">1</span>名");
				}
				
				if(mapCount.get("female1005")==null){
					if(hasComma){
						sb.append("，");
					}
					hasComma = true;
					sb.append("政协缺配女干部<span "+fontcolor+">1</span>名");
				}
				sb.append("）");
			}
		}
		if(mapCount.get("noZGParty")!=null){
			if(hasComma){
				sb.append("，");
			}
			hasComma = true;
			sb.append("<span dataType=\"noZGParty\" class=\"reverse-search\">党外干部<span "+fontcolor+">"+mapCount.get("noZGParty")+"</span>名</span>");
		}
		//四个班子党外干部（人大政府应配1名，政协3名）缺配几名
		if("2".equals(mntp05)){
			//四个班子党外干部（人大政府应配1名，政协3名）缺配几名
			if(mapCount.get("noZGParty1003")==null||mapCount.get("noZGParty1004")==null||(mapCount.get("noZGParty1005")==null||(mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3))){
				hasComma = false;
				sb.append("（");
				if(mapCount.get("noZGParty1003")==null){
					hasComma = true;
					sb.append("人大缺配党外干部<span "+fontcolor+">1</span>名");
				}
				if(mapCount.get("noZGParty1004")==null){
					if(hasComma){
						sb.append("，");
					}
					hasComma = true;
					sb.append("政府缺配党外干部<span "+fontcolor+">1</span>名");
				}
				
				if(mapCount.get("noZGParty1005")==null||(mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3)){
					if(hasComma){
						sb.append("，");
					}
					hasComma = true;
					sb.append("政协缺配党外干部<span "+fontcolor+">"+(3-(Integer)(mapCount.get("noZGParty1005")==null?0:mapCount.get("noZGParty1005")))+"</span>名");
				}
				sb.append("）");
			}
			
			sb.append("</p><p class=\"p1\">&ensp;&ensp;&ensp;&ensp;年轻干部：");
			hasComma = false;
			Integer dz10011004Count = Integer.valueOf(mapCount.get("dz10011004")==null?"0":mapCount.get("dz10011004").toString());
			if(mapCount.get("age40")!=null){//dz10011004
				
				hasComma = true;
				float avgAge40 = (float)((Integer)mapCount.get("age40"))/dz10011004Count*100;
		        DecimalFormat df = new DecimalFormat("0.00");
		        String result = df.format(avgAge40);
				sb.append("<span dataType=\"age40\" class=\"reverse-search\">40岁左右党政领导干部<span "+fontcolor+">"+mapCount.get("age40")+"</span>名，占比<span "+fontcolor+">"+result+"%</span></span>");
			}
			if(mapCount.get("age35")!=null){
				if(hasComma){
					sb.append("，");
				}
				hasComma = true;
				sb.append("<span dataType=\"age35\" class=\"reverse-search\">其中35岁左右党政领导干部<span "+fontcolor+">"+mapCount.get("age35")+"</span>名</span>");
			}
			
			
			
			
			sb.append("</p>");
		}
		
		
		
		//40岁左右党政领导干部 几名 占比，其中35岁左右党政领导干部几名，四个班子缺配女干部（应配1名）几名，
		if("2".equals(mntp05)){
			sb.append("</p><p class=\"p1\">&ensp;&ensp;&ensp;&ensp;交流名单：");
			hasComma = false;
			if(mapCount.get("el02_1")!=null){
				hasComma = true;
				sb.append("<span dataType=\"el02_1\" class=\"reverse-search\">党政班子成员、法检“两长”在同一职位任职满10年必须交流<span "+fontcolor+">"+mapCount.get("el02_1")+"</span>名</span>");
			}
			if(mapCount.get("el02_2")!=null){
				if(hasComma){
					sb.append("，</br>");
				}
				hasComma = true;
				sb.append("<span dataType=\"el02_2\" class=\"reverse-search\">党政班子成员、法检“两长”在同一职位上连续任职达到两个任期，不再推荐、提名担任同一职务<span "+fontcolor+">"+mapCount.get("el02_2")+"</span>名</span>");
			}
			
			if(mapCount.get("el02_3")!=null){
				if(hasComma){
					sb.append("，</br>");
				}
				hasComma = true;
				sb.append("<span dataType=\"el02_3\" class=\"reverse-search\">同一区、县（市）党政班子中担任同一层次领导职务满10年应当交流<span "+fontcolor+">"+mapCount.get("el02_3")+"</span>名</span>");
			}
			if(mapCount.get("el02_4")!=null){
				if(hasComma){
					sb.append("，</br>");
				}
				hasComma = true;
				sb.append("<span dataType=\"el02_4\" class=\"reverse-search\">区、县（市）纪委书记干满一届原则上要交流<span "+fontcolor+">"+mapCount.get("el02_4")+"</span>名</span>");
			}
			if(mapCount.get("el02_5")!=null){
				if(hasComma){
					sb.append("，</br>");
				}
				hasComma = true;
				sb.append("<span dataType=\"el02_5\" class=\"reverse-search\">党政领导干部担任同一层次领导职务累计达15年以上的，不再继续提名<span "+fontcolor+">"+mapCount.get("el02_5")+"</span>名</span>");
			}
			html = sb.toString();
		}else{
			html = sb.toString().replaceAll("&ensp;&ensp;(?!&ensp;&ensp;)", "")
					.replaceAll("class=\"p1\"", "class=\"p2\"");
		}
		
		JSONObject  pgridRecordsRS = JSONObject.fromObject(reverseSearchMap);
		
		this.pm.getExecuteSG().addExecuteCode("$('.OrgInfo').html('"+html+"')");
		this.pm.getExecuteSG().addExecuteCode("PgridInfo.pgridRecordsRS="+pgridRecordsRS+";reverseSearchClick()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	public void addCount(Map<String, Object> mapCount, String dataKey, Integer count, String a0000, String a00002, Map<String, List<String>> reverseSearchMap){
		if(mapCount.get(dataKey)!=null){
			mapCount.put(dataKey, (Integer)mapCount.get(dataKey)+count);
			reverseSearchMap.get(dataKey).add(a0000);
		}else{
			List<String> l = new ArrayList<String>();
			reverseSearchMap.put(dataKey, l);
			l.add(a0000);
			mapCount.put(dataKey, count);
		}
	}
	
	/**
	 * 调配后的人员统计
	 * @param mntp00
	 * @param mntp05
	 * @param b01id
	 * @param b0111
	 * @return
	 */
	public String getSQLTPH(String mntp00, String mntp05, String b01id, String b0111){
		String sql = "";
		
		  
		if("2".equals(mntp05)){//区县领导班子
  
			sql = "select a02.a0200,"
					+ "(select b0131 from b01 where b0111=a02.a0201b) fxyp06,"
				+ " (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
				+ " a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age "
				+ " FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		//+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
	  
	  
		}else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台 市直 国企高校
			  sql = "select  a02.a0200,(nvl(a02.a0201e,'Z')||'01') fxyp06,"
				+ " (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
				+ "a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
		   +"    AND a02.a0255 = '1' "
		  // + " and a02.a0201e in ('1','3')"
		   +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
		   +"    and a02.a0201b = '"+b0111+"'";
		}
  
  
		sql = "select * from ("+sql+") a01 ";
		
		String allSql = "and t.b01id='"+b01id+"'";
		if("".equals(b01id)){//区县市
			allSql = "and t.b01id in(select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
		}
		String unionSql = "select t.a0200,t.fxyp06,t.fxyp07,a01.a0000,"
				+ "a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age "
				+ " from v_mntp_gw_ry t, a01 "
		  		+ " where t.sortnum=1 and t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' "+allSql;
		  
		sql = "select a01.*,"
				+ "(select to_char(wm_concat(el02)) el02 from EXCHANGE_LIST el where el01='2020' and el.a0000=a01.a0000) el02"
				+ " from ("+sql+" union all "+unionSql+") a01 ";	
		return sql;
	}
	/**
	 * 调配前的人员统计
	 * @param mntp00
	 * @param mntp05
	 * @param b01id
	 * @param b0111
	 * @return
	 */
	public String getSQLTPQ(String mntp00, String mntp05, String b01id, String b0111){
		String sql = "";
		
		  
		if("2".equals(mntp05)){//区县领导班子
  
			sql = "select a02.a0200,"
					+ "(select b0131 from b01 where b0111=a02.a0201b) fxyp06,"
				+ " '' fxyp07,"
				+ " a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ " substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age "
				+ " FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		//+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
	  
	  
		}else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台 市直 国企高校
			  sql = "select  a02.a0200,(nvl(a02.a0201e,'Z')||'01') fxyp06,"
				+ " '' fxyp07,"
				+ "a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
		   +"    AND a02.a0255 = '1'"
		 //  + "   and a02.a0201e in ('1','3')"
		   +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
		   +"    and a02.a0201b = '"+b0111+"'";
		}
  
  
		sql = "select * from ("+sql+") a01 ";
		
		String allSql = "and t.b01id='"+b01id+"'";
		if("".equals(b01id)){
			allSql = "";
		}
		
		  
		sql = "select a01.*,"
				+ "(select to_char(wm_concat(el02)) el02 from EXCHANGE_LIST el where el01='2020' and el.a0000=a01.a0000) el02"
				+ " from ("+sql+") a01 ";	
		return sql;
	}
	public void getJData(String mntp00, String mntp05, String b01id, String b0111, Map<String, Object> a0000sMap, Map<String, Object> mapCount, Map<String, List<String>> reverseSearchMap,boolean istpq) throws AppException{
		String sql = "";
		if(istpq){
			sql = this.getSQLTPQ(mntp00, mntp05, b01id, b0111);
		}else{
			sql = this.getSQLTPH(mntp00, mntp05, b01id, b0111);
		}
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		
		for(HashMap<String, Object> dataMap : listCode){
			String a0000 = dataMap.get("a0000")==null?"":dataMap.get("a0000").toString();
			if(StringUtils.isEmpty(a0000)){
				continue;
			}
			if(a0000sMap.get(a0000)==null&&!"-1".equals(dataMap.get("fxyp07"))){//免职的不算
				a0000sMap.put(a0000, "");
				String age = dataMap.get("age")==null?"0":dataMap.get("age").toString();
				
				String dataKey = "ageCount";
				//总年龄
				this.addCount(mapCount,dataKey,Integer.valueOf(age),a0000,a0000,reverseSearchMap);
				
				//交流名单
				dataKey = "el02_1";
				if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("1")){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("2")){
					dataKey = "el02_2";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("3")){
					dataKey = "el02_3";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("4")){
					dataKey = "el02_4";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(dataMap.get("el02")!=null&&dataMap.get("el02").toString().contains("5")){
					dataKey = "el02_5";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//40岁左右党政领导干部
				dataKey = "age40";
				if( "1001".equals(dataMap.get("fxyp06")) || "1004".equals(dataMap.get("fxyp06") ) ){
					if(Integer.valueOf(age)<43 ){
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
						//其中35岁左右党政领导干部几名
						dataKey = "age35";
						if(Integer.valueOf(age)<38){
							this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
						}
					}
					dataKey = "dz10011004";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//55岁及以上几个
				dataKey = "ageGT55";
				if(Integer.valueOf(age)>=55){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					//60岁及以上几个
					dataKey = "ageGT60";
					if(Integer.valueOf(age)>=60){
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				
				//56-59岁
				dataKey = "ageGT56LT59";
				if(Integer.valueOf(age)>=56&&Integer.valueOf(age)<=59){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else  if(Integer.valueOf(age)>=51&&Integer.valueOf(age)<=55){
					dataKey = "ageGT51LT55";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else  if(Integer.valueOf(age)>=46&&Integer.valueOf(age)<=50){
					dataKey = "ageGT46LT50";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(Integer.valueOf(age)>=41&&Integer.valueOf(age)<=45){
					//41-45岁
					dataKey = "ageGT41LT45";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(Integer.valueOf(age)<=40){
					//40岁及以下
					dataKey = "ageLT40";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				
				
				
				
				//45岁及以下
				dataKey = "ageLT45";
				if(Integer.valueOf(age)<=45){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				
				
				//女干部几名
				dataKey = "female";
				String a0104 = dataMap.get("a0104")==null?"0":dataMap.get("a0104").toString();
				if("2".equals(a0104)){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					//四个班子缺配女干部（应配1名）几名
					if("1001".equals(dataMap.get("fxyp06"))){
						dataKey = "female1001";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1004".equals(dataMap.get("fxyp06"))){
						dataKey = "female1004";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1003".equals(dataMap.get("fxyp06"))){
						dataKey = "female1003";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1005".equals(dataMap.get("fxyp06"))){
						dataKey = "female1005";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				//党外干部几名
				dataKey = "noZGParty";
				String a0141 = dataMap.get("a0141")==null?"0":dataMap.get("a0141").toString();
				if(!"01".equals(a0141)){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					
					//四个班子党外干部（人大政府应配1名，政协3名）缺配几名
					if("1001".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1001";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1004".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1004";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1003".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1003";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}else if("1005".equals(dataMap.get("fxyp06"))){
						dataKey = "noZGParty1005";
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
				}
				
				//研究生及以上几名（其中博士几名），  大学几名，大专及以下几名
				String zzxl = dataMap.get("zzxl")==null?"":dataMap.get("zzxl").toString();
				String zzxw = dataMap.get("zzxw")==null?"":dataMap.get("zzxw").toString();
				String qrzxl = dataMap.get("qrzxl")==null?"":dataMap.get("qrzxl").toString();
				String qrzxw = dataMap.get("qrzxw")==null?"":dataMap.get("qrzxw").toString();
				String regx = "[\\s\\S]*(博士|硕士|研究生)[\\s\\S]*";
				if(zzxl.matches(regx)||zzxw.matches(regx)||qrzxl.matches(regx)||qrzxw.matches(regx)){
					dataKey = "xlxwLev1";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(zzxl.matches("[\\s\\S]*(大学|本科)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(大学|本科)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(大学|本科)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(大学|本科)[\\s\\S]*")){
					dataKey = "xlxwLev3";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}else if(zzxl.matches("[\\s\\S]*(大专|专科|高中|中技|中专|小学|初中)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(大专|专科|高中|中技|中专|小学|初中)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(大专|专科|高中|中技|中专|小学|初中)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(大专|专科|高中|中技|中专|小学|初中)[\\s\\S]*")){
					dataKey = "xlxwLev4";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
				if(zzxl.matches("[\\s\\S]*(博士)[\\s\\S]*")||zzxw.matches("[\\s\\S]*(博士)[\\s\\S]*")||qrzxl.matches("[\\s\\S]*(博士)[\\s\\S]*")||qrzxw.matches("[\\s\\S]*(博士)[\\s\\S]*")){
					dataKey = "xlxwLev2";
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
				}
			
			}
			
			//总数
			mapCount.put("totalCount", a0000sMap.size());
			
		}
	}


	
	public void getEChartsZYLX(Map<String, Object> a0000sMap, Map<String, Object> mapCount,
			Map<String, List<String>> reverseSearchMap) throws AppException {
		StringBuilder a0000s = new StringBuilder("'");
		for(String a0000 : a0000sMap.keySet()){
			a0000s.append(a0000+"','");
		}
		if(a0000s.length()==1){
			a0000s.append("'");
		}else{
			a0000s.delete(a0000s.length()-2, a0000s.length());
		}
		
		String sql = "select nvl(sum(attr101),0) s1,nvl(sum(attr102),0) s2,"
				+ "nvl(sum(attr103),0) s3,nvl(sum(attr104),0) s4"
				+ ",nvl(sum(attr105),0) s5,nvl(sum(attr106),0) s6,nvl(sum(attr107),0) s7"
				+ ",nvl(sum(attr108),0) s8,nvl(sum(attr109),0) s9"
				+ ",nvl(sum(attr110),0) s10,nvl(sum(attr111),0) s11,nvl(sum(attr112),0) s12 from attr_lrzw where a0000 in("+a0000s+")";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		
		HashMap<String, Object> dataMap = listCode.get(0);
		List<Integer> ZYLXList = new ArrayList<Integer>();
		for(int i=1;i<=12;i++){
			ZYLXList.add(Integer.valueOf(dataMap.get("s"+i)+""));
		}
		
		mapCount.put("ZYLXList", ZYLXList);
	}
}
