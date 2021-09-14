package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import com.insigma.siis.local.business.entity.BZFX;
import com.insigma.siis.local.business.entity.BZNDKH;
import com.insigma.siis.local.business.entity.GQBZFX;
import com.insigma.siis.local.business.entity.GQJYZB;
import com.insigma.siis.local.business.entity.QXSBZNDKH;
import com.insigma.siis.local.business.entity.QXSBZTP;

public class GQBZYPPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		//初始化核定职数
		this.setNextEventName("init1");
		return 0;
	}
	
	
	//初始化单位名和核定职数
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer hd=new StringBuffer();
		if(checkedgroupid==null||"".equals(checkedgroupid)){//
			this.setMainMessage("请先选择机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String zzhd=null;
		String fzhd=null;
		String zshd=null;
		String dwm=null;
		
		try {
			@SuppressWarnings("unchecked")
			List<String> dwmList= HBUtil.getHBSession().createSQLQuery("select b0101 from b01 where b0111='"+checkedgroupid+"'").list();
			if(dwmList.size()>=1) {
				dwm= String.valueOf(dwmList.get(0));
			}else {
				dwm= "空";
			}
			//正职核定职数
			@SuppressWarnings("unchecked")
			List<String> zzhdList= HBUtil.getHBSession().createSQLQuery("select b0183 from b01 where b0111='"+checkedgroupid+"'").list();

			if(zzhdList.get(0)!=null) {
				zzhd= String.valueOf(zzhdList.get(0));
			}else {
				zzhd="0";
			}
			
			//副职核定职数
			@SuppressWarnings("unchecked")
			List<String> fzhdList= HBUtil.getHBSession().createSQLQuery("select b0185 from b01 where b0111='"+checkedgroupid+"'").list();

			if(fzhdList.get(0)!=null) {
				fzhd= String.valueOf(fzhdList.get(0));
			}else {
				fzhd="0";
			}
			
			//总师核定职数
			@SuppressWarnings("unchecked")
			List<String> zshdList= HBUtil.getHBSession().createSQLQuery("select b0246 from b01 where b0111='"+checkedgroupid+"'").list();
			if(zshdList.get(0)!=null) {
				zshd= String.valueOf(zshdList.get(0));
			}else {
				zshd="0";
			}
			hd.append("应配正职"+zzhd+"名，应配副职"+fzhd+"名");
			if(!"0".equals(zshd) && !"".equals(zshd) && (zshd)!=null) {
				hd.append("，应配总师"+zshd+"名");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init2");// 实配职数
		this.getExecuteSG().addExecuteCode("document.getElementById(\"dwm\").innerHTML='"+dwm+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"hdzs\").innerHTML='"+hd+"'");
		this.setNextEventName("BZTPGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化实配职数以及提任情况
	@PageEvent("init2")
	@NoRequiredValidate
	public  int  init2() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer sp=new StringBuffer();
		String zzsp=null;
		String fzsp=null;
		String zssp=null;
		StringBuffer trqk=new StringBuffer();
		
		StringBuffer ztpj=new StringBuffer();
		StringBuffer bzbz=new StringBuffer();
		StringBuffer yhjy=new StringBuffer();
		StringBuffer jbgk=new StringBuffer();
		StringBuffer fzdw=new StringBuffer();
		//StringBuffer bdgb=new StringBuffer();
		//StringBuffer bjylxtr=new StringBuffer();
		
		try {
			//正职实配人数
			@SuppressWarnings("unchecked")
			List<String> zzspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02,b01 t, a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"
					).list();
			 zzsp=String.valueOf(zzspList.get(0));
			 
			 
			//副职实配人数
			@SuppressWarnings("unchecked")
			List<String> fzspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02, b01 t,a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='3' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"  
					).list();
			fzsp=String.valueOf(fzspList.get(0));
			
			
			//总师实配人数
			@SuppressWarnings("unchecked")
			List<String> zsspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02, b01 t,a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='31' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"
					).list();
			zssp=String.valueOf(zsspList.get(0));
			sp.append("实配正职<u id=\"zzcx\">"+zzsp+"</u>名，实配副职<u id=\"fzcx\">"+fzsp+"</u>名");
			if(!"0".equals(zssp) && !"".equals(zssp) && (zssp)!=null) {
				sp.append("，实配总师<u id=\"zscx\">"+zssp+"</u>名");
			}
			//本地 干部
			/*@SuppressWarnings("unchecked")
			List<String> bdgb1= HBUtil.getHBSession().createSQLQuery("select bdgb from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bdgb1.size()>0 && bdgb1.get(0)!=null) {
				bdgb.append(String.valueOf(bdgb1.get(0)));
			}*/
			//本届以来新提任
			/*@SuppressWarnings("unchecked")
			List<String> bjylxtr1= HBUtil.getHBSession().createSQLQuery("select bjylxtr from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bjylxtr1.size()>0 && bjylxtr1.get(0)!=null) {
				bjylxtr.append(String.valueOf(bjylxtr1.get(0)));
			}*/
			//总体评价
			@SuppressWarnings("unchecked")
			List<String> ztpjs= HBUtil.getHBSession().createSQLQuery("select ztpj from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(ztpjs.size()>0 && ztpjs.get(0)!=null) {
				ztpj.append(String.valueOf(ztpjs.get(0)));
			}
			
			//班子不足
			@SuppressWarnings("unchecked")
			List<String> bzbzs= HBUtil.getHBSession().createSQLQuery("select bzbz from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzbzs.size()>0 && bzbzs.get(0)!=null) {
				bzbz.append(String.valueOf(bzbzs.get(0)));
			}
			
			//优化建议
			@SuppressWarnings("unchecked")
			List<String> yhjys= HBUtil.getHBSession().createSQLQuery("select yhjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(yhjys.size()>0 && yhjys.get(0)!=null) {
				yhjy.append(String.valueOf(yhjys.get(0)));
			}
			
			//基本概况
			@SuppressWarnings("unchecked")
			List<String> jbgks1= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,0,2000)) from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks1.size()>0 && jbgks1.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks2= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,2001,2000)) from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks2.size()>0 && jbgks2.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks3= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,4001,2000)) from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks3.size()>0 && jbgks3.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks4= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,6001,2000)) from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks4.size()>0 && jbgks4.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks4.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> jbgks5= HBUtil.getHBSession().createSQLQuery(" select to_char(substr(jbgk,8001,2000)) from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(jbgks5.size()>0 && jbgks5.get(0)!=null) {
				jbgk.append(String.valueOf(jbgks5.get(0)));
			}
			
			//发展定位
			@SuppressWarnings("unchecked")
			List<String> fzdws= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,0,2000)) fzdw from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws.size()>0 && fzdws.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws1= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,2001,2000)) fzdw from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws1.size()>0 && fzdws1.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws1.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws2= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,4001,2000)) fzdw from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws2.size()>0 && fzdws2.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws2.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws3= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,6001,2000)) fzdw from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws3.size()>0 && fzdws3.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws3.get(0)));
			}
			@SuppressWarnings("unchecked")
			List<String> fzdws4= HBUtil.getHBSession().createSQLQuery("select to_char(substr(fzdw,8001,2000)) fzdw from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(fzdws4.size()>0 && fzdws4.get(0)!=null) {
				fzdw.append(String.valueOf(fzdws4.get(0)));
			}
//			//提任情况
//			@SuppressWarnings("unchecked")
//			List<String> trqkb= HBUtil.getHBSession().createSQLQuery("select trqk from BZFX where BZFX.B0111='"+checkedgroupid+"'").list();
//			if(trqkb.size()>0 && trqkb.get(0)!=null) {
//				trqk.append(String.valueOf(trqkb.get(0)));
//			}
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init3");// 年度考核，专业考核结果
		this.getExecuteSG().addExecuteCode("document.getElementById(\"spzs\").innerHTML='"+sp+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ztpj\").value='"+ztpj.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzbz\").value='"+bzbz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"yhjy\").value='"+yhjy.toString()+"'");
//		this.getExecuteSG().addExecuteCode("document.getElementById(\"trqk\").value='"+trqk.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jbgk\").value='"+jbgk.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"fzdw\").value='"+fzdw.toString()+"'");
		//this.getExecuteSG().addExecuteCode("document.getElementById(\"bdgb\").value='"+bdgb.toString()+"'");
		//this.getExecuteSG().addExecuteCode("document.getElementById(\"bjylxtr\").value='"+bjylxtr.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化年度考核结果
	@PageEvent("init3")
	@NoRequiredValidate
	public  int  init3() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer ndyxgr=new StringBuffer();
		StringBuffer ndkhjg=new StringBuffer();
		
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
//		if(this.getPageElement("year").getValue()==null || "0".equals(this.getPageElement("year").getValue())) {
		String now=String.valueOf(year-1);
		this.getPageElement("year").setValue(now); 
//		}
		
		
		
		try {
			String khnd=this.getPageElement("year").getValue();
			//年度考核优秀个人
			@SuppressWarnings("unchecked")
			List<String> ndyxgrs = HBUtil.getHBSession().createSQLQuery("SELECT distinct a01.a0101 FROM a02, a01,b01 t,a15\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and a15.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3'or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a15.a1517='1' and a15.a1521='"+khnd+"' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(ndyxgrs.size()>0) {
				for(int i=0;i<ndyxgrs.size();i++) {
					ndyxgr.append(String.valueOf(ndyxgrs.get(i))+",");
					}
					if(ndyxgr.length()>0){
						ndyxgr.deleteCharAt(ndyxgr.length()-1);
					}
			}
			
			
			//年度考核结果
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select NDKHJG from BZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				if("1".equals(ndkh.get(0).toString())) {
					ndkhjg.append("优秀");
				}else if("2".equals(ndkh.get(0).toString())) {
					ndkhjg.append("良好");
				}else if("3".equals(ndkh.get(0).toString())) {
					ndkhjg.append("一般");
				}else if("4".equals(ndkh.get(0).toString())) {
					ndkhjg.append("较差");
				}
			}else{
				ndkhjg.append("");
			}
			this.getPageElement("ndkhjg").setValue(ndkhjg.toString()); 
			
			
			//专项考核
			@SuppressWarnings("unchecked")
			List<String> list1 = HBUtil.getHBSession().createSQLQuery("select ZXKHSJ from BZZXKH where b0111='"+checkedgroupid+"' order by ZXKHSJ asc").list();
			@SuppressWarnings("unchecked")
			List<String> list2 = HBUtil.getHBSession().createSQLQuery("select ZXKHM from BZZXKH where b0111='"+checkedgroupid+"' order by ZXKHSJ asc").list();
			@SuppressWarnings("unchecked")
			List<String> list3 = HBUtil.getHBSession().createSQLQuery("select ZXKHJG from BZZXKH where b0111='"+checkedgroupid+"' order by ZXKHSJ asc").list();
			//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();

			StringBuffer desc = new StringBuffer("");
			for(int i=0;i<list1.size();i++) {
				String ZXKHSJ=list1.get(i);
				String ZXKHM=list2.get(i);
				String ZXKHJG=list3.get(i);
				String JG="";
				if("1".equals(ZXKHJG)) {
					JG="优秀";
				}else if("2".equals(ZXKHJG)) {
					JG="良好";
				}else if("3".equals(ZXKHJG)) {
					JG="一般";
				}else if("4".equals(ZXKHJG)) {
					JG="较差";
				}
				desc.append(ZXKHSJ.substring(0,4)+"."+ZXKHSJ.substring(4,6)+ZXKHM+JG+";");	
			}
			
			
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "。");
			}
			this.getPageElement("zxkh").setValue(desc.toString());
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
		this.setNextEventName("init4");// 班子结构分析
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化班子结构分析
	@PageEvent("init4")
	@NoRequiredValidate
	public  int  init4() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer nljg=new StringBuffer();
		StringBuffer lyjg=new StringBuffer();
		StringBuffer zyjg=new StringBuffer();
		StringBuffer jgxgb=new StringBuffer();
		try {
			//平均年龄
			@SuppressWarnings("unchecked")
			List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			nljg.append("平均年龄").append(String.valueOf(avgAge.get(0))).append("岁，50岁以下干部");
			
			//50岁以下干部人数
			@SuppressWarnings("unchecked")
			List<String> NumAge50 = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=50 and a02.a0201b=t.b0111 and a02.a0201b='"
					+checkedgroupid+"'").list();
			nljg.append(String.valueOf(NumAge50.get(0))).append("名(");
			
			//50岁以下干部人名
			@SuppressWarnings("unchecked")
			List<String> Age50 = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=50 and a02.a0201b=t.b0111 and a02.a0201b='"
					+checkedgroupid+"'").list();
			for(int i=0;i<Age50.size();i++) {
				nljg.append("<a>"+String.valueOf(Age50.get(i))+"</a>，");	
			}
			if(nljg.length()>0){
				nljg.deleteCharAt(nljg.length()-1);
			}
			if(Age50.size()>0) {
				nljg.append(")");
			}
			
			//来源结构
			//本部门产生人数
			@SuppressWarnings("unchecked")
			List<String> BBMCSRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			
			//本部门产生人数
			@SuppressWarnings("unchecked")
			List<String> BBMCS = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			if(BBMCS.size()>0) {
				lyjg.append("本部门产生"+String.valueOf(BBMCSRS.get(0))+"人：");
				for(int i=0;i<BBMCS.size();i++) {
					lyjg.append("<a>"+String.valueOf(BBMCS.get(i))+"</a>，");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("无本部门产生");
			}
			
			lyjg.append("<br/>");
			//市直机关交流人数
			@SuppressWarnings("unchecked")
			List<String> WBJLRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='2'").list();
			
			//市直机关交流人数
			@SuppressWarnings("unchecked")
			List<String> WBJL = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='2'").list();
			if(WBJL.size()>0) {
				lyjg.append("市直机关交流"+String.valueOf(WBJLRS.get(0))+"人：");
				for(int i=0;i<WBJL.size();i++) {
					lyjg.append("<a>"+String.valueOf(WBJL.get(i))+"</a>，");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("无市直机关交流");
			}
			lyjg.append("<br/>");
			
			
			//区县市交流人数
			@SuppressWarnings("unchecked")
			List<String> QXSRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='3'").list();
			
			//区县市交流人数
			@SuppressWarnings("unchecked")
			List<String> QXS = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='3'").list();
			if(QXS.size()>0) {
				lyjg.append("区县市交流"+String.valueOf(QXSRS.get(0))+"人：");
				for(int i=0;i<QXS.size();i++) {
					lyjg.append("<a>"+String.valueOf(QXS.get(i))+"</a>，");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("无区县市交流");
			}
			lyjg.append("<br/>");
			
			
			
			//副师值军转人数
			@SuppressWarnings("unchecked")
			List<String> FSZRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='4'").list();
			
			//副师值军转人数
			@SuppressWarnings("unchecked")
			List<String> FSZ = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='4'").list();
			if(FSZ.size()>0) {
				lyjg.append("副师职军转"+String.valueOf(FSZRS.get(0))+"人：");
				for(int i=0;i<FSZ.size();i++) {
					lyjg.append("<a>"+String.valueOf(FSZ.get(i))+"</a>，");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("无副师职军转");
			}
			lyjg.append("<br/>");
			
			
			//其他人数
			@SuppressWarnings("unchecked")
			List<String> QTRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='5'").list();
			
			//其他人数
			@SuppressWarnings("unchecked")
			List<String> QT = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='5'").list();
			if(QT.size()>0) {
				lyjg.append("其他来源"+String.valueOf(QTRS.get(0))+"人：");
				for(int i=0;i<QT.size();i++) {
					lyjg.append("<a>"+String.valueOf(QT.get(i))+"</a>，");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("无其他来源");
			}
			
			
			
			
			//专业结构
			for(int i=101;i<113;i++) {
				StringBuffer names=new StringBuffer();
				names.append("ATTR").append(i);
				//指定专业干部人数
				@SuppressWarnings("unchecked")
				List<String> NumZYGB = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  attr_lrzw."+names.toString()+"='1' and a02.a0201b='"+checkedgroupid+"'").list();
				
				//指定专业干部人名
				@SuppressWarnings("unchecked")
				List<String> ZYGB = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  attr_lrzw."+names.toString()+"='1' and a02.a0201b='"+checkedgroupid+"'").list();
				
				String name=null;
				if(names.toString().equals("ATTR101")) {
					name="党务类";
				}else if(names.toString().equals("ATTR102")) {
					name="综合管理类";
				}else if(names.toString().equals("ATTR103")) {
					name="制造业和工业经济类";
				}else if(names.toString().equals("ATTR104")) {
					name="大数据和信息技术类";
				}else if(names.toString().equals("ATTR105")) {
					name="城建城管类";
				}else if(names.toString().equals("ATTR106")) {
					name="教育卫生类";
				}else if(names.toString().equals("ATTR107")) {
					name="服务商贸类";
				}else if(names.toString().equals("ATTR108")) {
					name="农业农村类";
				}else if(names.toString().equals("ATTR109")) {
					name="文化发展和旅游类";
				}else if(names.toString().equals("ATTR110")) {
					name="公检法政法类";
				}else if(names.toString().equals("ATTR111")) {
					name="企业经营管理类";
				}else if(names.toString().equals("ATTR112")) {
					name="金融财务类";
				}
				if(ZYGB.size()>0) {
					zyjg.append(name).append("专业干部").append(String.valueOf(NumZYGB.get(0))).append("名：");
					for(int j=0;j<ZYGB.size();j++) {
						zyjg.append("<a>"+String.valueOf(ZYGB.get(j))+"</a>，" );
						}
						if(zyjg.length()>0){
							zyjg.deleteCharAt(zyjg.length()-1);
						zyjg.append("<br/> ");
					}
				}		
			}
			if(zyjg.length()>0){
				zyjg.deleteCharAt(zyjg.length()-1);
			}
			
			
			//结构性干部
			//女性干部人数
			@SuppressWarnings("unchecked")
			List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			//女性干部人名
			@SuppressWarnings("unchecked")
			List<String> Female = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104='2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(Female.size()>0) {
				jgxgb.append("女性干部").append(String.valueOf(NumFemale.get(0))).append("名(");
				for(int j=0;j<Female.size();j++) {
					jgxgb.append("<a >"+String.valueOf(Female.get(j))+"</a>，");
					}
				if(jgxgb.length()>0){
					jgxgb.deleteCharAt(jgxgb.length()-1);
					jgxgb.append(")，");
				}
			}
			
			//党外干部人数
			@SuppressWarnings("unchecked")
			List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			//党外干部人名
			@SuppressWarnings("unchecked")
			List<String> DW = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(DW.size()>0) {
				jgxgb.append("党外干部").append(String.valueOf(NumDW.get(0))).append("名(");
				for(int j=0;j<DW.size();j++) {
					jgxgb.append("<a>"+String.valueOf(DW.get(j))+"</a>，");
					}
				if(jgxgb.length()>0){
					jgxgb.deleteCharAt(jgxgb.length()-1);
					jgxgb.append(")，");
				}
			}
			if(jgxgb.length()>0){
				jgxgb.deleteCharAt(jgxgb.length()-1);
			}
			
			
				
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init5");//重点关注干部
		this.getExecuteSG().addExecuteCode("document.getElementById(\"nljg\").innerHTML='"+nljg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"lyjg\").innerHTML='"+lyjg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"zyjg\").innerHTML='"+zyjg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jgxgb\").innerHTML='"+jgxgb.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//初始化重点关注干部
	@PageEvent("init5")
	@NoRequiredValidate
	public  int  init5() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bxyxsgzz=new StringBuffer();
		StringBuffer jqtrsgzz=new StringBuffer();
		StringBuffer bxyxsgfz=new StringBuffer();
		StringBuffer jqtrsgfz=new StringBuffer();
		StringBuffer yxnqgb=new StringBuffer();
		StringBuffer xgzzz=new StringBuffer();
		StringBuffer xgzfz=new StringBuffer();
		
		try {

			//表现优秀的市管正职
			@SuppressWarnings("unchecked")
			List<String> yxsgzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='综合评价优秀的市管正职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(yxsgzz.size()>0) {
				for(int i=0;i<yxsgzz.size();i++) {
					bxyxsgzz.append("<a>"+String.valueOf(yxsgzz.get(i))+"</a>，");
					}
					if(bxyxsgzz.length()>0){
						bxyxsgzz.deleteCharAt(bxyxsgzz.length()-1);
					}
			}
			
			//近期可提任市管正职
			@SuppressWarnings("unchecked")
			List<String> trsgzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='近期可提任市管正职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(trsgzz.size()>0) {
				for(int i=0;i<trsgzz.size();i++) {
					jqtrsgzz.append("<a>"+String.valueOf(trsgzz.get(i))+"</a>，");
					}
					if(jqtrsgzz.length()>0){
						jqtrsgzz.deleteCharAt(jqtrsgzz.length()-1);
					}
			}
			
			//表现优秀的市管副职
			@SuppressWarnings("unchecked")
			List<String> yxsgfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='综合评价比较优秀的市管副职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(yxsgfz.size()>0) {
				for(int i=0;i<yxsgfz.size();i++) {
					bxyxsgfz.append("<a>"+String.valueOf(yxsgfz.get(i))+"</a>，");
					}
					if(bxyxsgfz.length()>0){
						bxyxsgfz.deleteCharAt(bxyxsgfz.length()-1);
					}
			}
			
			
			//近期可提任市管副职
			@SuppressWarnings("unchecked")
			List<String> trsgfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='近期可提任市管副职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(trsgfz.size()>0) {
				for(int i=0;i<trsgfz.size();i++) {
					jqtrsgfz.append("<a>"+String.valueOf(trsgfz.get(i))+"</a>，");
					}
					if(jqtrsgfz.length()>0){
						jqtrsgfz.deleteCharAt(jqtrsgfz.length()-1);
					}
			}
			
			
			//需进一步关注的市管正职
			@SuppressWarnings("unchecked")
			List<String> gzzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='需进一步关注的市管正职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(gzzz.size()>0) {
				for(int i=0;i<gzzz.size();i++) {
					xgzzz.append("<a>"+String.valueOf(gzzz.get(i))+"</a>，");
					}
					if(xgzzz.length()>0){
						xgzzz.deleteCharAt(xgzzz.length()-1);
					}
			}
			
			//需进一步关注的市管副职
			@SuppressWarnings("unchecked")
			List<String> gzfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='需进一步关注的市管副职' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(gzfz.size()>0) {
				for(int i=0;i<gzfz.size();i++) {
					xgzfz.append("<a>"+String.valueOf(gzfz.get(i))+"</a>，");
					}
					if(xgzfz.length()>0){
						xgzfz.deleteCharAt(xgzfz.length()-1);
					}
			}
			
			//列入优秀年轻干部调研名单
			@SuppressWarnings("unchecked")
			List<String> nqgb= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a02.a0201b='"+checkedgroupid+"' and fkly is null\r\n" + 
					"           and 1 = 1\r\n" + 
					"           and concat(a01.a0000, '') in\r\n" + 
					"               (select a02.a0000\r\n" + 
					"                  from A02 a02\r\n" + 
					"                 where 1 = 1\r\n" + 
					"                   and a02.a0281 = 'true')\r\n" + 
					"           and a0163 = '1'\r\n" + 
					"           and (1 = 1 and (1 = 2 or a01.a0165 like '%16%'))").list();
			if(nqgb.size()>0) {
				for(int i=0;i<nqgb.size();i++) {
					yxnqgb.append("<a>"+String.valueOf(nqgb.get(i))+"</a>，");
					}
					if(yxnqgb.length()>0){
						yxnqgb.deleteCharAt(yxnqgb.length()-1);
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bxyxsgzz\").innerHTML='"+bxyxsgzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jqtrsgzz\").innerHTML='"+jqtrsgzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bxyxsgfz\").innerHTML='"+bxyxsgfz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jqtrsgfz\").innerHTML='"+jqtrsgfz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"yxnqgb\").innerHTML='"+yxnqgb.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"xgzzz\").innerHTML='"+xgzzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"xgzfz\").innerHTML='"+xgzfz.toString()+"'");
		this.setNextEventName("init6");// 班子研判建议
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	//初始化班子研判建议
	@PageEvent("init6")
	@NoRequiredValidate
	public  int  init6() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bzypjy=new StringBuffer();
		StringBuffer pbjy=new StringBuffer();
		StringBuffer bztd=new StringBuffer();
		StringBuffer zywt=new StringBuffer();
		try {
			//班子研判建议
			@SuppressWarnings("unchecked")
			List<String> bzypjyb= HBUtil.getHBSession().createSQLQuery("select bzypjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzypjyb.size()>0 && bzypjyb.get(0)!=null) {
				bzypjy.append(String.valueOf(bzypjyb.get(0)));
			}	
			
			//班子配备建议
			@SuppressWarnings("unchecked")
			List<String> pbjyb= HBUtil.getHBSession().createSQLQuery("select pbjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(pbjyb.size()>0 && pbjyb.get(0)!=null) {
				pbjy.append(String.valueOf(pbjyb.get(0)));
			}	
			
			//班子特点
			
			@SuppressWarnings("unchecked")
			List<String> bztdb= HBUtil.getHBSession().createSQLQuery("select bztd from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bztdb.size()>0 && bztdb.get(0)!=null) {
				bztd.append(String.valueOf(bztdb.get(0)));
			}
			
			
			//主要问题
			
			@SuppressWarnings("unchecked")
			List<String> zywtb= HBUtil.getHBSession().createSQLQuery("select zywt from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(zywtb.size()>0 && zywtb.get(0)!=null) {
				zywt.append(String.valueOf(zywtb.get(0)));
			}
			//班子专业性默认
			@SuppressWarnings("unchecked")
			List<String> zyxbm= HBUtil.getHBSession().createSQLQuery("select zyxbm from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(zyxbm.size()>0) {
				if(zyxbm.get(0)!=null && !"".equals(zyxbm.get(0))) {
					this.getPageElement("zyxbm").setValue(zyxbm.get(0));
				}
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzypjy\").value='"+bzypjy.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"pbjy\").value='"+pbjy.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bztd\").value='"+bztd.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"zywt\").value='"+zywt.toString()+"'");
		this.setNextEventName("init7");// 班子分析
		this.setNextEventName("init8");// 经营指标
		this.getExecuteSG().addExecuteCode("openrmb()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//初始化班子分析
	@PageEvent("init7")
	@NoRequiredValidate
	public  int  init7() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bzfx=new StringBuffer();
		try {
//			//班子分析
//			@SuppressWarnings("unchecked")
//			List<String> bzfxb= HBUtil.getHBSession().createSQLQuery("select bzfx from BZFX where BZFX.B0111='"+checkedgroupid+"'").list();
//			if(bzfxb.size()>0 && bzfxb.get(0)!=null) {
//				bzfx.append(String.valueOf(bzfxb.get(0)));
//			}
		
				
			
			//班子分析 实配情况
			bzfx.append("实配情况：");
			//空缺正职
			@SuppressWarnings("unchecked")
			List<String> kqzz= HBUtil.getHBSession().createSQLQuery("select b0234 from b01  where B0111='"+checkedgroupid+"'").list();
		
			//空缺副职
			@SuppressWarnings("unchecked")
			List<String> kqfz= HBUtil.getHBSession().createSQLQuery("select b0235 from b01  where B0111='"+checkedgroupid+"'").list();
			
			//超配职位
			@SuppressWarnings("unchecked")
			List<String> cpzw= HBUtil.getHBSession().createSQLQuery("select b0180 from b01  where B0111='"+checkedgroupid+"'").list();
			
			if(kqzz.get(0)==null && kqfz.get(0)==null && cpzw.get(0)==null) {
				bzfx.append("班子已配满。");
			}else {
				if(kqzz.get(0)!=null) {
					bzfx.append("班子空缺正职"+String.valueOf(kqzz.get(0))+"名，");
				}
				if(kqfz.get(0)!=null) {
					bzfx.append("班子空缺副职"+String.valueOf(kqfz.get(0))+"名，");
				}
				if(cpzw.get(0)!=null) {
					bzfx.append("班子超配情况："+String.valueOf(cpzw.get(0))+"，");
				}
			}
			bzfx.deleteCharAt(bzfx.length()-1);
			
			
			bzfx.append("\n年龄结构");
			
			
			//平均年龄
			@SuppressWarnings("unchecked")
			List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			
			//最小年龄
			@SuppressWarnings("unchecked")
			List<String> minAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(min(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			
			
			//最大年龄
			@SuppressWarnings("unchecked")
			List<String> maxAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(max(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			double avg=0;
			if(avgAge.get(0)!=null && !"".equals(avgAge.get(0))) {
				avg=Double.parseDouble(String.valueOf(avgAge.get(0)));
			}
			
			double min=0;
			if(minAge.get(0)!=null && !"".equals(minAge.get(0))) {
				min=Double.parseDouble(String.valueOf(minAge.get(0)));
			}
			double max=0;
			if(maxAge.get(0)!=null && !"".equals(maxAge.get(0))) {
				max=Double.parseDouble(String.valueOf(maxAge.get(0)));
			}
			if(avg>51.4) {
				if(min>=50) {
					bzfx.append("(红灯)：大于市直平均年龄且没有50岁以下干部。");
					this.getPageElement("nljgyj").setValue("3");
				}else{
					bzfx.append("(黄灯)：大于市直平均年龄，但有50岁以下干部。");
					this.getPageElement("nljgyj").setValue("2");
				}
			}else {
				if(min>=50) {
					bzfx.append("(黄灯)：小于市直平均年龄，但没有50岁以下干部。");
					this.getPageElement("nljgyj").setValue("2");
				}else {
					if((max-min)<=5){
						bzfx.append("(黄灯)：小于市直平均年龄，有50岁以下干部但成员中年龄差距在5岁以内。");
						this.getPageElement("nljgyj").setValue("2");
					}else {
						bzfx.append("(绿灯)：小于市直平均年龄，有50岁以下干部且成员中年龄差距在5岁以上。");
						this.getPageElement("nljgyj").setValue("1");
					}
				}
			}
			
			bzfx.append("\n来源结构");
			//本部门产生人数
			@SuppressWarnings("unchecked")
			List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			
			double bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
			
			
//			//本部门产生人数
//			@SuppressWarnings("unchecked")
//			List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("select BDWRS from SZBDW where b0111='"+checkedgroupid+"'").list();
//			double bbm=0;
//			if(bbmRs.size()>0) {
//				if(bbmRs.get(0)!=null && !"".equals(bbmRs.get(0))) {
//					bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
//				}
//			}
			
			//总人数
			@SuppressWarnings("unchecked")
			List<String> totalRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t \r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			
			double total=Double.parseDouble(String.valueOf(totalRs.get(0)));
			
			double lyzb=0;
			if(total!=0) {
				lyzb=bbm/total;
			}		
			if(lyzb>=(2/3.0)) {
				bzfx.append("(红灯)：班子成员中三分之二以上内部产生。");
				this.getPageElement("lyjgyj").setValue("3");
			}else if(lyzb>=1/2.0) {
				bzfx.append("(黄灯)：班子成员中二分之一以上内部产生。");
				this.getPageElement("lyjgyj").setValue("2");
			}else {
				bzfx.append("(绿灯)：班子成员中不足二分之一内部产生。");
				this.getPageElement("lyjgyj").setValue("1");
			}
			
			if(bbmRs.size()<1) {
				this.getPageElement("lyjgyj").setValue("");
			}else{
				if(bbmRs.get(0)==null ||"".equals(bbmRs.get(0))) {
					this.getPageElement("lyjgyj").setValue("");
				}
			}
			
			bzfx.append("\n专业结构");
			
			@SuppressWarnings("unchecked")
			List<String> zygbrs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t,attr_lrzw\r\n" + 
					"     WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
					"         and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"            and a02.a0201b=t.b0111 and  (attr_lrzw.attr101='1' or attr_lrzw.attr102='1' or attr_lrzw.attr103='1'\r\n" + 
					"            or attr_lrzw.attr104='1'or attr_lrzw.attr105='1'or  attr_lrzw.attr106='1'\r\n" + 
					"           or  attr_lrzw.attr107='1' or attr_lrzw.attr108='1' or attr_lrzw.attr109='1' or attr_lrzw.attr110='1'\r\n" + 
					"           or attr_lrzw.attr111='1' or attr_lrzw.attr112='1'\r\n" + 
					"            ) and a02.a0201b='"+checkedgroupid+"'").list();
			 double zygb=Double.parseDouble(String.valueOf(zygbrs.get(0)));
			 double zyzb=0;
			 if(total!=0) {
				 zyzb=zygb/total;
			 }
			 
			 String zyxbm=this.getPageElement("zyxbm").getValue();
//			 System.out.print(zyzb);
			 if("0".equals(zyxbm)) {
				 bzfx.append("：非专业性部门");
				 this.getPageElement("zyjgyj").setValue("");
			 }else {
				 if(zyzb<(1/3.0)) {
					 bzfx.append("(红灯)：专业部门专业化干部不足三分之一。");
					 this.getPageElement("zyjgyj").setValue("3");
				 }else if(zyzb<(1/2.0)) {
					 bzfx.append("(黄灯)：专业部门专业化干部不足二分之一。");
					 this.getPageElement("zyjgyj").setValue("2"); 
				 }else{
					 bzfx.append("(绿灯)：专业部门专业化干部超过二分之一。");
					 this.getPageElement("zyjgyj").setValue("1");
				 }
			 }
			bzfx.append("\n");
			 //女性干部人数
			@SuppressWarnings("unchecked")
			List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			
			//党外干部人数
			@SuppressWarnings("unchecked")
			List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			bzfx.append("女性干部"+String.valueOf(NumFemale.get(0))+"名，党外干部"+String.valueOf(NumDW.get(0))+"名。");
		
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzfx\").value='"+bzfx.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//经营指标
	@PageEvent("init8")
	@NoRequiredValidate
	public  int  init8() throws RadowException{
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<100;i++){
			map.put(""+(year-i), year-i);
			if((year-i)==2017) {
				break;
			}
		}
		((Combo)this.getPageElement("year1")).setValueListForSelect(map); 
		String now=String.valueOf(year-1);
		this.getPageElement("year1").setValue(now); 
		
		
		
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year1").getValue();
			@SuppressWarnings("unchecked")
			List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select BZ00 from GQJYZB where B0111='"+checkedgroupid+"' and year='"+khnd+"'").list();
			if(bz00s.size()>0 && bz00s.get(0)!=null) {
				sess = HBUtil.getHBSession();
				GQJYZB gqjyzb=(GQJYZB)sess.get(GQJYZB.class, bz00s.get(0));
//				QXSBZNDKH qxsbzndkh = (QXSBZNDKH)sess.get(QXSBZNDKH.class, bz00s.get(0));
				PMPropertyCopyUtil.copyObjValueToElement(gqjyzb, this);
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
	
	
	//班子分析以及提任情况数据保存
	@PageEvent("saveFX")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveFX() throws RadowException, AppException {
		GQBZFX gqbzfx=new GQBZFX();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		gqbzfx.setB0111(checkedgroupid);
		String zyxbm=this.getPageElement("zyxbm").getValue();
		gqbzfx.setZyxbm(zyxbm);
		String nljgyj=this.getPageElement("nljgyj").getValue();
		gqbzfx.setNljgyj(nljgyj);
		String lyjgyj=this.getPageElement("lyjgyj").getValue();
		gqbzfx.setLyjgyj(lyjgyj);
		String zyjgyj=this.getPageElement("zyjgyj").getValue();
		gqbzfx.setZyjgyj(zyjgyj);
		String ztpj=this.getPageElement("ztpj").getValue();
		gqbzfx.setZtpj(ztpj);
		String bzbz=this.getPageElement("bzbz").getValue();
		gqbzfx.setBzbz(bzbz);
		String yhjy=this.getPageElement("yhjy").getValue();
		gqbzfx.setYhjy(yhjy);
		
		String jbgk=this.getPageElement("jbgk").getValue();
		gqbzfx.setJbgk(jbgk);
		String fzdw=this.getPageElement("fzdw").getValue();
		gqbzfx.setFzdw(fzdw);
		this.copyElementsValueToObj(gqbzfx, this);
		HBSession sess = null;
		
		try {
			sess = HBUtil.getHBSession();
			
			@SuppressWarnings("unchecked")
			List<String> b01ids= HBUtil.getHBSession().createSQLQuery("select b01id from B01 where b0111='"+checkedgroupid+"'").list();
			String b01id=b01ids.get(0);
			gqbzfx.setB01id(b01id);
			
			
			@SuppressWarnings("unchecked")
			List<String> b0269s= HBUtil.getHBSession().createSQLQuery("select b0269 from B01 where b0111='"+checkedgroupid+"'").list();
			String b0269=b0269s.get(0);
			gqbzfx.setB0269(b0269);
			
			@SuppressWarnings("unchecked")
			List<String> b0101s= HBUtil.getHBSession().createSQLQuery("select b0101 from B01 where b0111='"+checkedgroupid+"'").list();
			String b0101=b0101s.get(0);
			gqbzfx.setB0101(b0101);
			
			@SuppressWarnings("unchecked")
			List<String> b0111= HBUtil.getHBSession().createSQLQuery("select b0111 from GQBZFX where b0111='"+checkedgroupid+"'").list();
			
			if(b0111.size()>0) {
				@SuppressWarnings("unchecked")
				List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select bz00 from GQBZFX where b0111='"+checkedgroupid+"'").list();
				String bz00=bz00s.get(0);
				gqbzfx.setBz00(bz00);
				sess.update(gqbzfx);
			}else {
				sess.save(gqbzfx);
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
		BZNDKH bzndkh=new BZNDKH();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		bzndkh.setB0111(checkedgroupid);
		this.copyElementsValueToObj(bzndkh, this);
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year").getValue();
			sess = HBUtil.getHBSession();
//			bzndkh.setYear(khnd);
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select bz00 from BZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				String bz00=ndkh.get(0);
				bzndkh.setBz00(bz00);
				if(bzndkh.getNdkhjg().length()>1) {
					this.setMainMessage("请修改考核结果后保存！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				sess.update(bzndkh);
			}else{
				if("".equals(bzndkh.getNdkhjg()) || bzndkh.getNdkhjg()==null) {
					this.setMainMessage("请选择考核结果！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				sess.save(bzndkh);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','年度考核保存成功！',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','年度考核保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//保存经营指标
	@PageEvent("saveJYZB")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveJYZB() throws RadowException, AppException {
		GQJYZB gqjyzb=new GQJYZB();
//		QXSBZNDKH qxsbzndkh=new QXSBZNDKH();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		gqjyzb.setB0111(checkedgroupid);
		this.copyElementsValueToObj(gqjyzb, this);
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year1").getValue();
			sess = HBUtil.getHBSession();
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select bz00 from GQJYZB where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				gqjyzb.setYear(khnd);	
				sess.update(gqjyzb);
			}else{
				gqjyzb.setYear(khnd);		
				sess.save(gqjyzb);
			}
				sess.flush();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','经营指标保存成功！',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','经营指标保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//切换年份
	@PageEvent("yearQuery")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int yearQuery() throws RadowException, AppException {
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer ndyxgr=new StringBuffer();
		StringBuffer ndkhjg=new StringBuffer();
		
		
		try {
			String khnd=this.getPageElement("year").getValue();
			//年度考核优秀个人
			@SuppressWarnings("unchecked")
			List<String> ndyxgrs = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,a15\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and a15.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3'or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a15.a1517='1' and a15.a1521='"+khnd+"' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(ndyxgrs.size()>0) {
				for(int i=0;i<ndyxgrs.size();i++) {
					ndyxgr.append(String.valueOf(ndyxgrs.get(i))+",");
					}
					if(ndyxgr.length()>0){
						ndyxgr.deleteCharAt(ndyxgr.length()-1);
					}
			}
			
			//年度考核结果
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select NDKHJG from BZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				if("1".equals(ndkh.get(0).toString())) {
					ndkhjg.append("优秀");
				}else if("2".equals(ndkh.get(0).toString())) {
					ndkhjg.append("良好");
				}else if("3".equals(ndkh.get(0).toString())) {
					ndkhjg.append("一般");
				}else if("4".equals(ndkh.get(0).toString())) {
					ndkhjg.append("较差");
				}else{
					ndkhjg.append("");
				}
			}else{
				ndkhjg.append("");
			}
			
			
			
			this.getPageElement("ndkhjg").setValue(ndkhjg.toString()); 
			this.getPageElement("ndyxgr").setValue(ndyxgr.toString());
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','年份切换失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
//		this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"');}}, 500);");

		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//经营指标切换年份
	@PageEvent("yearQuery1")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int yearQuery1() throws RadowException, AppException {
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		HBSession sess = null;
		try {
			String khnd=this.getPageElement("year1").getValue();
			@SuppressWarnings("unchecked")
			List<String> bz00s= HBUtil.getHBSession().createSQLQuery("select BZ00 from GQJYZB where B0111='"+checkedgroupid+"' and year='"+khnd+"'").list();
			if(bz00s.size()>0 && bz00s.get(0)!=null) {
				sess = HBUtil.getHBSession();
				GQJYZB gqjyzb=(GQJYZB)sess.get(GQJYZB.class, bz00s.get(0));
//				QXSBZNDKH qxsbzndkh = (QXSBZNDKH)sess.get(QXSBZNDKH.class, bz00s.get(0));
				PMPropertyCopyUtil.copyObjValueToElement(gqjyzb, this);
			}else {
				this.getPageElement("zzc").setValue("");
				this.getPageElement("jzc").setValue("");
				this.getPageElement("yysr").setValue("");
				this.getPageElement("jlr").setValue("");
				
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
		List<String> a0000s = HBUtil.getHBSession().createSQLQuery("select a01.a0000 from a02,a01 where a02.a0000=a01.a0000 and  a01.a0101='"+a0101+"' and a02.a0201b='"+checkedgroupid+"'").list();
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
	
}
