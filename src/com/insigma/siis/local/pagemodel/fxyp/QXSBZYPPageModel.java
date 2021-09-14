package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.BZFX;
import com.insigma.siis.local.business.entity.BZNDKH;
import com.insigma.siis.local.business.entity.BZZXKH;
import com.insigma.siis.local.business.entity.QXSBZFX;
import com.insigma.siis.local.business.entity.QXSBZNDKH;
import com.insigma.siis.local.business.entity.QXSBZTP;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import net.sf.json.JSONObject;

public class QXSBZYPPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		//初始化核定职数
		this.setNextEventName("init1");
		return 0;
	}
	
	
	//初始化区县市名以及职数配备情况
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer hd=new StringBuffer();
		if(checkedgroupid==null||"".equals(checkedgroupid)){//
			this.setMainMessage("请先选择机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String qxs=null;
		StringBuffer zspb=new StringBuffer("");

		try {
			@SuppressWarnings("unchecked")
			List<String> qxsList= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+checkedgroupid+"'").list();
			if(qxsList.size()>=1) {
				qxs= String.valueOf(qxsList.get(0));
			}else {
				qxs= "空";
			}
			
			//职数配备情况
			@SuppressWarnings("unchecked")
			List<String> zspbList= HBUtil.getHBSession().createSQLQuery("select B0236 from QXSLDBZHZB where b0111='"+checkedgroupid+"'").list();
			if(zspbList.size()>0 && zspbList.get(0)!=null) {
				zspb.append("缺配"+zspbList.get(0));
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init2");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"qxs\").innerHTML='"+qxs+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"zspb\").innerHTML='"+zspb+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化党政班子“两头”干部
	@PageEvent("init2")
	@NoRequiredValidate
	public  int  init2() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer xdcs=new StringBuffer("");
		StringBuffer bjyx=new StringBuffer("");
		StringBuffer fxyb=new StringBuffer("");
		
		StringBuffer ztpj=new StringBuffer();
		StringBuffer bzbz=new StringBuffer();
		StringBuffer yhjy=new StringBuffer();
		StringBuffer jbgk=new StringBuffer();
		StringBuffer fzdw=new StringBuffer();
		//StringBuffer bdgb=new StringBuffer();
		//StringBuffer bjylxtr=new StringBuffer();
		try {
			//相对成熟的市管干部
			@SuppressWarnings("unchecked")
			List<String> xdcsgb= HBUtil.getHBSession().createSQLQuery(" SELECT distinct a01.a0101 FROM a02, a01,b01 t,extra_tags  e    \r\n" + 
					"WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0165 like '%03%' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and (e.a0196c='近期可提任市管正职' or e.a0196c='近期可提任市管副职') and length(a02.a0201b)=19\r\n" + 
					" and a02.a0201b like '%"+checkedgroupid+"%'").list();
			if(xdcsgb.size()>0) {
				for(int i=0;i<xdcsgb.size();i++) {
					xdcs.append("<a>"+String.valueOf(xdcsgb.get(i))+"</a>，");
					}
					if(xdcs.length()>0){
						xdcs.deleteCharAt(xdcs.length()-1);
					}
			}
			
			
			//其他比较优秀的市管干部
			@SuppressWarnings("unchecked")
			List<String> bjyxgb= HBUtil.getHBSession().createSQLQuery(" SELECT distinct a01.a0101 FROM a02, a01,b01 t,extra_tags  e    \r\n" + 
					"WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0165 like '%03%' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and (e.a0196c='综合评价优秀的市管正职' or e.a0196c='综合评价比较优秀的市管副职') and length(a02.a0201b)=19\r\n" + 
					" and a02.a0201b like '%"+checkedgroupid+"%'").list();
			if(bjyxgb.size()>0) {
				for(int i=0;i<bjyxgb.size();i++) {
					bjyx.append("<a>"+String.valueOf(bjyxgb.get(i))+"</a>，");
					}
					if(bjyx.length()>0){
						bjyx.deleteCharAt(bjyx.length()-1);
					}
			}
			
			//反响一般市管干部
			@SuppressWarnings("unchecked")
			List<String> fxybgb= HBUtil.getHBSession().createSQLQuery(" SELECT distinct a01.a0101 FROM a02, a01,b01 t,extra_tags  e    \r\n" + 
					"WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0165 like '%03%' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and (e.a0196c='需进一步关注的市管正职' or e.a0196c='需进一步关注的市管副职') and length(a02.a0201b)=19\r\n" + 
					" and a02.a0201b like '%"+checkedgroupid+"%'").list();
			if(fxybgb.size()>0) {
				for(int i=0;i<fxybgb.size();i++) {
					fxyb.append("<a>"+String.valueOf(fxybgb.get(i))+"</a>，");
					}
					if(fxyb.length()>0){
						fxyb.deleteCharAt(fxyb.length()-1);
					}
			}
			//本地 干部
			/*@SuppressWarnings("unchecked")
			List<String> bdgb1= HBUtil.getHBSession().createSQLQuery("select bdgb from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(bdgb1.size()>0 && bdgb1.get(0)!=null) {
				bdgb.append(String.valueOf(bdgb1.get(0)));
			}*/
			//本届以来新提任
			/*@SuppressWarnings("unchecked")
			List<String> bjylxtr1= HBUtil.getHBSession().createSQLQuery("select bjylxtr from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(bjylxtr1.size()>0 && bjylxtr1.get(0)!=null) {
				bjylxtr.append(String.valueOf(bjylxtr1.get(0)));
			}*/
			//总体评价
			@SuppressWarnings("unchecked")
			List<String> ztpjs= HBUtil.getHBSession().createSQLQuery("select ztpj from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(ztpjs.size()>0 && ztpjs.get(0)!=null) {
				ztpj.append(String.valueOf(ztpjs.get(0)));
			}
			
			//班子不足
			@SuppressWarnings("unchecked")
			List<String> bzbzs= HBUtil.getHBSession().createSQLQuery("select bzbz from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzbzs.size()>0 && bzbzs.get(0)!=null) {
				bzbz.append(String.valueOf(bzbzs.get(0)));
			}
			
			//优化建议
			@SuppressWarnings("unchecked")
			List<String> yhjys= HBUtil.getHBSession().createSQLQuery("select yhjy from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(yhjys.size()>0 && yhjys.get(0)!=null) {
				yhjy.append(String.valueOf(yhjys.get(0)));
			}
			
			//基本概况
			@SuppressWarnings("unchecked")
			List<String> jbgks1= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,0,2000)) from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks1.size()>0 && jbgks1.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks2= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,2001,2000)) from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks2.size()>0 && jbgks2.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks3= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,4001,2000)) from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks3.size()>0 && jbgks3.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks4= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,6001,2000)) from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks4.size()>0 && jbgks4.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks4.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks5= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,8001,2000)) from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks5.size()>0 && jbgks5.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks5.get(0)));
			}
			
			//发展定位
			@SuppressWarnings("unchecked")
			List<String> fzdws= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,0,2000)) fzdw from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws.size()>0 && fzdws.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws1= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,2001,2000)) fzdw from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws1.size()>0 && fzdws1.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws2= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,4001,2000)) fzdw from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws2.size()>0 && fzdws2.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws3= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,6001,2000)) fzdw from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws3.size()>0 && fzdws3.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws4= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,8001,2000)) fzdw from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws4.size()>0 && fzdws4.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws4.get(0)));
			}

		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init3");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"xdcs\").innerHTML='"+xdcs.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bjyx\").innerHTML='"+bjyx.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"fxyb\").innerHTML='"+fxyb.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ztpj\").value='"+ztpj.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzbz\").value='"+bzbz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"yhjy\").value='"+yhjy.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jbgk\").value='"+jbgk.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"fzdw\").value='"+fzdw.toString()+"'");
		//this.getExecuteSG().addExecuteCode("document.getElementById(\"bdgb\").value='"+bdgb.toString()+"'");
		//this.getExecuteSG().addExecuteCode("document.getElementById(\"bjylxtr\").value='"+bjylxtr.toString()+"'");
		this.getExecuteSG().addExecuteCode("openrmb()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化结构型干部配备情况
	@PageEvent("init3")
	@NoRequiredValidate
	public  int  init3() throws RadowException{
		String mntp05 ="2";	//区县领导班子
		String b0111 =this.getPageElement("checkedgroupid").getValue();
		@SuppressWarnings("unchecked")
		List<String> b01ids= HBUtil.getHBSession().createSQLQuery("select b01id from B01 where b0111='"+b0111+"'").list();
		String b01id=b01ids.get(0);

		StringBuilder sb = new StringBuilder();		

		try {
			
			//判断重复
			Map<String,Object> a0000sMap = new HashMap<String, Object>();
			//统计
			Map<String,Object> mapCount = new HashMap<String, Object>();
			//反查
			Map<String,List<String>> reverseSearchMap = new HashMap<String, List<String>>();
			this.getJData(mntp05,b01id,b0111,a0000sMap,mapCount,reverseSearchMap);
			  
			String html = "";	
			boolean hasComma = false;
			String fontcolor = "style=\"color:#FF4500\"";
			if(mapCount.get("female")!=null){
				hasComma = true;
				sb.append("<span dataType=\"female\" class=\"reverse-search\">女干部<span "+fontcolor+">"+mapCount.get("female")+"</span>名</span>");
			}
			if(mapCount.get("female1001")==null || mapCount.get("female1003")==null || mapCount.get("female1004")==null || mapCount.get("female1005")==null
					) {
				if(hasComma){
					sb.append("(");
				}
				hasComma = true;
			}
					
			if(mapCount.get("female1001")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("党委缺配女干部<span "+fontcolor+">1</span>名，");
			}
			if(mapCount.get("female1003")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("人大缺配女干部<span "+fontcolor+">1</span>名，");
			}
			if(mapCount.get("female1004")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("政府缺配女干部<span "+fontcolor+">1</span>名，");
			}
			
			if(mapCount.get("female1005")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("政协缺配女干部<span "+fontcolor+">1</span>名，");
			}
			if(mapCount.get("female1001")==null || mapCount.get("female1003")==null || mapCount.get("female1004")==null || mapCount.get("female1005")==null
					) {
				sb.deleteCharAt(sb.length()-1);
				if(hasComma){
					sb.append(")");
				}
				hasComma = true;
			}
			
			if(mapCount.get("noZGParty")!=null){
				if(hasComma){
					sb.append("，");
				}
				hasComma = true;
				sb.append("<span dataType=\"noZGParty\" class=\"reverse-search\">党外干部<span "+fontcolor+">"+mapCount.get("noZGParty")+"</span>名</span>");
			}
			
			if(mapCount.get("noZGParty1003")==null || mapCount.get("noZGParty1004")==null || (mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3)) {
				if(hasComma){
					sb.append("(");
				}
				hasComma = true;
			}
			//四个班子党外干部（人大政府应配1名，政协3名）缺配几名
			if(mapCount.get("noZGParty1003")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("人大缺配党外干部<span "+fontcolor+">1</span>名，");
			}
			if(mapCount.get("noZGParty1004")==null){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("政府缺配党外干部<span "+fontcolor+">1</span>名，");
			}
			
			if(mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3){
				if(hasComma){
					sb.append("");
				}
				hasComma = true;
				sb.append("政协缺配党外干部<span "+fontcolor+">"+(3-(Integer)mapCount.get("noZGParty1005"))+"</span>名，");
			}
			if( mapCount.get("noZGParty1003")==null || mapCount.get("noZGParty1004")==null || (mapCount.get("noZGParty1005")!=null&&(Integer)mapCount.get("noZGParty1005")<3)) {
				sb.deleteCharAt(sb.length()-1);
				if(hasComma){
					sb.append(")");
				}
				hasComma = true;
			}
			//40岁左右党政领导干部 几名 占比，其中35岁左右党政领导干部几名，四个班子缺配女干部（应配1名）几名，
			if("2".equals(mntp05)){
				sb.append("</p><p class=\"p1\">年轻干部：");
				hasComma = false;
				if(mapCount.get("age40")!=null){
					hasComma = true;
					float avgAge40 = (float)((Integer)mapCount.get("age40"))/a0000sMap.size()*100;
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
				html = sb.toString();
			}else{
				html = sb.toString().replaceAll("&ensp;&ensp;(?!&ensp;&ensp;)", "")
						.replaceAll("class=\"p1\"", "class=\"p2\"");
			}
			
			JSONObject  pgridRecordsRS = JSONObject.fromObject(reverseSearchMap);
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init4");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jgxgbpb\").innerHTML='"+sb.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化班子分析和调配建议
	@PageEvent("init4")
	@NoRequiredValidate
	public  int  init4() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bzfx=new StringBuffer();
		StringBuffer tpjy=new StringBuffer();
		try{
			//班子分析
			@SuppressWarnings("unchecked")
			List<String> bzfxb= HBUtil.getHBSession().createSQLQuery("select bzfx from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzfxb.size()>0 && bzfxb.get(0)!=null) {
				bzfx.append(String.valueOf(bzfxb.get(0)));
			}
			//班子研判建议
			@SuppressWarnings("unchecked")
			List<String> tpjyb= HBUtil.getHBSession().createSQLQuery("select tpjy from QXSBZFX where QXSBZFX.B0111='"+checkedgroupid+"'").list();
			if(tpjyb.size()>0 && tpjyb.get(0)!=null) {
				tpjy.append(String.valueOf(tpjyb.get(0)));
			}		

		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init5");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzfx\").value='"+bzfx.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"tpjy\").value='"+tpjy.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化考核测评情况
	@PageEvent("init5")
	@NoRequiredValidate
	public  int  init5() throws RadowException{
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<100;i++){
			map.put(""+(year-i), year-i);
			if((year-i)==2017) {
				break;
			}
		}
		((Combo)this.getPageElement("year")).setValueListForSelect(map); 
		if(this.getPageElement("year").getValue()==null || "0".equals(this.getPageElement("year").getValue())) {
			String now=String.valueOf(year-1);
			this.getPageElement("year").setValue(now); 
		}
		
		
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year").getValue();
			@SuppressWarnings("unchecked")
			List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select BZ00 from QXSBZNDKH where B0111='"+checkedgroupid+"' and year='"+khnd+"'").list();
			if(bz00s.size()>0 && bz00s.get(0)!=null) {
				sess = HBUtil.getHBSession();
				QXSBZNDKH qxsbzndkh = (QXSBZNDKH)sess.get(QXSBZNDKH.class, bz00s.get(0));
				PMPropertyCopyUtil.copyObjValueToElement(qxsbzndkh, this);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("BZTPGrid.dogridquery");//班子调配列表	
//		this.setNextEventName("init3");
//		this.getExecuteSG().addExecuteCode("document.getElementById(\"tpjy\").value='"+tpjy.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	// * 干部调配情况列表
	@PageEvent("BZTPGrid.dogridquery")
	@NoRequiredValidate
	public int BZTPGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String b0111=this.getPageElement("checkedgroupid").getValue();
		String sql = "select TP00,B0111,TPGW,'设置条件' as TPTJ,TPRY from QXSBZTP where b0111='"+b0111+"'";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	//删除干部调配数据
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String tp00)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			QXSBZTP qxsbztp=(QXSBZTP)sess.get(QXSBZTP.class, tp00);
//			A15 a15 = (A15)sess.get(A15.class, a1500);
//			A01 a01= (A01)sess.get(A01.class, a15.getA0000());
//			String a1527 = a15.getA1527();
			
			//applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			//记录了删除哪条
//			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(a15, new A15()));
			HBUtil.executeUpdate("delete from QXYPCONTITION where tp00='"+tp00+"'");
			HBUtil.executeUpdate("delete from QXYPRY where tp00='"+tp00+"'");
			sess.delete(qxsbztp);
			this.getExecuteSG().addExecuteCode("radow.doEvent('BZTPGrid.dogridquery')");
			qxsbztp = new QXSBZTP();
			PMPropertyCopyUtil.copyObjValueToElement(qxsbztp, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.setMainMessage("删除成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	//新增一条干部调配数据
	@PageEvent("BZTPAddBtn.onclick")
	@NoRequiredValidate
	public int BZTPAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String b0111 =this.getPageElement("checkedgroupid").getValue();
		if(b0111==null||"".equals(b0111)){//
			this.setMainMessage("请先保存机构基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		QXSBZTP qxsbztp=new QXSBZTP();
		qxsbztp.setB0111(b0111);
		HBSession sess = null;
//		a15.setA1527(this.getPageElement("a1527").getValue());
		try {
			sess = HBUtil.getHBSession();
			sess.save(qxsbztp);
			sess.flush();
			this.setMainMessage("创建成功！");
			this.setNextEventName("BZTPGrid.dogridquery");//更新班子调配列表	
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("创建失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//修改调配表
	@PageEvent("BZTPGrid.rowdbclick")
	@GridDataRange
	@NoRequiredValidate
	public int BZTPGridOnRowDbClick() throws RadowException{  

		int index = this.getPageElement("BZTPGrid").getCueRowIndex();
		String tp00 = this.getPageElement("BZTPGrid").getValue("tp00",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			QXSBZTP qxsbztp=(QXSBZTP) sess.get(QXSBZTP.class, tp00);
			this.getPageElement("tp00").setValue(tp00);
//			A15 a15 = (A15)sess.get(A15.class, a1500);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
//		String show=this.getPageElement("tp00").getValue();
//		this.getExecuteSG().addExecuteCode("alert('"+show+"')");
		this.getExecuteSG().addExecuteCode("openBZTP()");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//切换年份
	@PageEvent("yearQuery")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int yearQuery() throws RadowException, AppException {
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year").getValue();
			@SuppressWarnings("unchecked")
			List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select BZ00 from QXSBZNDKH where B0111='"+checkedgroupid+"' and year='"+khnd+"'").list();
			if(bz00s.size()>0 && bz00s.get(0)!=null) {
				sess = HBUtil.getHBSession();
				QXSBZNDKH qxsbzndkh = (QXSBZNDKH)sess.get(QXSBZNDKH.class, bz00s.get(0));
				PMPropertyCopyUtil.copyObjValueToElement(qxsbzndkh, this);
			}else {
				this.getPageElement("dwyxl").setValue("");
				this.getPageElement("rdyxl").setValue("");
				this.getPageElement("zfyxl").setValue("");
				this.getPageElement("zxyxl").setValue("");
				this.getPageElement("zzdf").setValue("");
				this.getPageElement("kpdc").setValue("");
				this.getPageElement("khzf").setValue("");
				this.getPageElement("pm").setValue("");
				this.getPageElement("ndkhqk").setValue("");
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','年份切换失败！',null,'220')");
			return EventRtnType.FAILD;
		}
//		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
//		this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"');}}, 500);");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//查看个人任免表
	@PageEvent("openrmb")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int openrmb() throws RadowException, AppException {
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String a0101=this.getPageElement("a0101").getValue();
		String a0000=null;
		@SuppressWarnings("unchecked")
		List<String> a0000s = HBUtil.getHBSession().createSQLQuery("select a01.a0000 from a02,a01 where a02.a0000=a01.a0000 and  a01.a0101='"+a0101+"' and length(a02.a0201b)=19 and a02.a0201b like '%"+checkedgroupid+"%'").list();
		if(a0000s.size()>0) {
			a0000=String.valueOf(a0000s.get(0));
		}
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	
	//班子分析以及调配建议数据保存
	@PageEvent("saveFX")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveFX() throws RadowException, AppException {
		QXSBZFX qxsbzfx=new QXSBZFX();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		qxsbzfx.setB0111(checkedgroupid);
		String ztpj=this.getPageElement("ztpj").getValue();
		qxsbzfx.setZtpj(ztpj);
		String bzbz=this.getPageElement("bzbz").getValue();
		qxsbzfx.setBzbz(bzbz);
		String yhjy=this.getPageElement("yhjy").getValue();
		qxsbzfx.setYhjy(yhjy);
		String jbgk=this.getPageElement("jbgk").getValue();
		qxsbzfx.setJbgk(jbgk);
		String fzdw=this.getPageElement("fzdw").getValue();
		qxsbzfx.setFzdw(fzdw);
		this.copyElementsValueToObj(qxsbzfx, this);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();		
			@SuppressWarnings("unchecked")
			List<String> b01ids= HBUtil.getHBSession().createSQLQuery("select b01id from B01 where b0111='"+checkedgroupid+"'").list();
			String b01id=b01ids.get(0);
			qxsbzfx.setB01id(b01id);
		
			@SuppressWarnings("unchecked")
			List<String> b0111= HBUtil.getHBSession().createSQLQuery("select b0111 from QXSBZFX where b0111='"+checkedgroupid+"'").list();
				
			if(b0111.size()>0) {
				@SuppressWarnings("unchecked")
				List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select bz00 from QXSBZFX where b0111='"+checkedgroupid+"'").list();
				String bz00=bz00s.get(0);
				qxsbzfx.setBz00(bz00);
				sess.update(qxsbzfx);
			}else {
				sess.save(qxsbzfx);
			}		
				
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存成功！',null,'220')");
		} catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	//年度考核数据保存
	@PageEvent("saveNDKH")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveNDKH() throws RadowException, AppException {
		QXSBZNDKH qxsbzndkh=new QXSBZNDKH();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		qxsbzndkh.setB0111(checkedgroupid);
		this.copyElementsValueToObj(qxsbzndkh, this);
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year").getValue();
			sess = HBUtil.getHBSession();
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select bz00 from QXSBZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
					
				sess.update(qxsbzndkh);
			}else{

				sess.save(qxsbzndkh);
			}
				sess.flush();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','区县市年度考核保存成功！',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','区县市年度考核保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public void getJData(String mntp05, String b01id, String b0111, Map<String, Object> a0000sMap, Map<String, Object> mapCount, Map<String, List<String>> reverseSearchMap) throws AppException{
		String sql = "";
		
		  
		if("2".equals(mntp05)){//区县领导班子
  
			sql = "select a02.a0200,"
					+ "(select b0131 from b01 where b0111=a02.a0201b) fxyp06,"
				+ "a01.a0000,a01.a0104,a01.a0141,qrzxl,qrzxw,zzxl,zzxw,a01.a0101,"
				+ "substr(to_char(sysdate, 'yyyymm') - substr(a0107, 1, 6), 1, 2) age FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
	  
	  
		}
  
  
		sql = "select * from ("+sql+") a01 ";
		
		String allSql = "and t.b01id='"+b01id+"'";
		if("".equals(b01id)){
			allSql = "";
		}
		  
		sql = "select a01.*,"
				+ "(select to_char(wm_concat(el02)) el02 from EXCHANGE_LIST el where el01='2020' and el.a0000=a01.a0000) el02"
				+ " from ("+sql+") a01 ";	
		
		
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
		
		for(HashMap<String, Object> dataMap : listCode){
			String a0000 = dataMap.get("a0000").toString();
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
				if(Integer.valueOf(age)<43 && ( "1001".equals(dataMap.get("fxyp06")) || "1004".equals(dataMap.get("fxyp06")) ) ){
					this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					//其中35岁左右党政领导干部几名
					dataKey = "age35";
					if(Integer.valueOf(age)<38){
						this.addCount(mapCount,dataKey,1,a0000,a0000,reverseSearchMap);
					}
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
			
			
			//总数
			mapCount.put("totalCount", a0000sMap.size());
			
		}
	
		}
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
		

}
