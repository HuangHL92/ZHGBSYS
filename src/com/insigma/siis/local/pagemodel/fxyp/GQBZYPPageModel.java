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
		//��ʼ���˶�ְ��
		this.setNextEventName("init1");
		return 0;
	}
	
	
	//��ʼ����λ���ͺ˶�ְ��
	@PageEvent("init1")
	@NoRequiredValidate
	public  int  init1() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer hd=new StringBuffer();
		if(checkedgroupid==null||"".equals(checkedgroupid)){//
			this.setMainMessage("����ѡ�������");
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
				dwm= "��";
			}
			//��ְ�˶�ְ��
			@SuppressWarnings("unchecked")
			List<String> zzhdList= HBUtil.getHBSession().createSQLQuery("select b0183 from b01 where b0111='"+checkedgroupid+"'").list();

			if(zzhdList.get(0)!=null) {
				zzhd= String.valueOf(zzhdList.get(0));
			}else {
				zzhd="0";
			}
			
			//��ְ�˶�ְ��
			@SuppressWarnings("unchecked")
			List<String> fzhdList= HBUtil.getHBSession().createSQLQuery("select b0185 from b01 where b0111='"+checkedgroupid+"'").list();

			if(fzhdList.get(0)!=null) {
				fzhd= String.valueOf(fzhdList.get(0));
			}else {
				fzhd="0";
			}
			
			//��ʦ�˶�ְ��
			@SuppressWarnings("unchecked")
			List<String> zshdList= HBUtil.getHBSession().createSQLQuery("select b0246 from b01 where b0111='"+checkedgroupid+"'").list();
			if(zshdList.get(0)!=null) {
				zshd= String.valueOf(zshdList.get(0));
			}else {
				zshd="0";
			}
			hd.append("Ӧ����ְ"+zzhd+"����Ӧ�丱ְ"+fzhd+"��");
			if(!"0".equals(zshd) && !"".equals(zshd) && (zshd)!=null) {
				hd.append("��Ӧ����ʦ"+zshd+"��");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init2");// ʵ��ְ��
		this.getExecuteSG().addExecuteCode("document.getElementById(\"dwm\").innerHTML='"+dwm+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"hdzs\").innerHTML='"+hd+"'");
		this.setNextEventName("BZTPGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ʼ��ʵ��ְ���Լ��������
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
			//��ְʵ������
			@SuppressWarnings("unchecked")
			List<String> zzspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02,b01 t, a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"
					).list();
			 zzsp=String.valueOf(zzspList.get(0));
			 
			 
			//��ְʵ������
			@SuppressWarnings("unchecked")
			List<String> fzspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02, b01 t,a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='3' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"  
					).list();
			fzsp=String.valueOf(fzspList.get(0));
			
			
			//��ʦʵ������
			@SuppressWarnings("unchecked")
			List<String> zsspList = HBUtil.getHBSession().createSQLQuery("SELECT count(a01.a0000) FROM a02, b01 t,a01\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and a02.a0201e='31' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'"
					).list();
			zssp=String.valueOf(zsspList.get(0));
			sp.append("ʵ����ְ<u id=\"zzcx\">"+zzsp+"</u>����ʵ�丱ְ<u id=\"fzcx\">"+fzsp+"</u>��");
			if(!"0".equals(zssp) && !"".equals(zssp) && (zssp)!=null) {
				sp.append("��ʵ����ʦ<u id=\"zscx\">"+zssp+"</u>��");
			}
			//���� �ɲ�
			/*@SuppressWarnings("unchecked")
			List<String> bdgb1= HBUtil.getHBSession().createSQLQuery("select bdgb from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bdgb1.size()>0 && bdgb1.get(0)!=null) {
				bdgb.append(String.valueOf(bdgb1.get(0)));
			}*/
			//��������������
			/*@SuppressWarnings("unchecked")
			List<String> bjylxtr1= HBUtil.getHBSession().createSQLQuery("select bjylxtr from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bjylxtr1.size()>0 && bjylxtr1.get(0)!=null) {
				bjylxtr.append(String.valueOf(bjylxtr1.get(0)));
			}*/
			//��������
			@SuppressWarnings("unchecked")
			List<String> ztpjs= HBUtil.getHBSession().createSQLQuery("select ztpj from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(ztpjs.size()>0 && ztpjs.get(0)!=null) {
				ztpj.append(String.valueOf(ztpjs.get(0)));
			}
			
			//���Ӳ���
			@SuppressWarnings("unchecked")
			List<String> bzbzs= HBUtil.getHBSession().createSQLQuery("select bzbz from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzbzs.size()>0 && bzbzs.get(0)!=null) {
				bzbz.append(String.valueOf(bzbzs.get(0)));
			}
			
			//�Ż�����
			@SuppressWarnings("unchecked")
			List<String> yhjys= HBUtil.getHBSession().createSQLQuery("select yhjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(yhjys.size()>0 && yhjys.get(0)!=null) {
				yhjy.append(String.valueOf(yhjys.get(0)));
			}
			
			//�����ſ�
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
			
			//��չ��λ
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
//			//�������
//			@SuppressWarnings("unchecked")
//			List<String> trqkb= HBUtil.getHBSession().createSQLQuery("select trqk from BZFX where BZFX.B0111='"+checkedgroupid+"'").list();
//			if(trqkb.size()>0 && trqkb.get(0)!=null) {
//				trqk.append(String.valueOf(trqkb.get(0)));
//			}
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init3");// ��ȿ��ˣ�רҵ���˽��
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
	
	
	//��ʼ����ȿ��˽��
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
			//��ȿ����������
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
			
			
			//��ȿ��˽��
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select NDKHJG from BZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				if("1".equals(ndkh.get(0).toString())) {
					ndkhjg.append("����");
				}else if("2".equals(ndkh.get(0).toString())) {
					ndkhjg.append("����");
				}else if("3".equals(ndkh.get(0).toString())) {
					ndkhjg.append("һ��");
				}else if("4".equals(ndkh.get(0).toString())) {
					ndkhjg.append("�ϲ�");
				}
			}else{
				ndkhjg.append("");
			}
			this.getPageElement("ndkhjg").setValue(ndkhjg.toString()); 
			
			
			//ר���
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
					JG="����";
				}else if("2".equals(ZXKHJG)) {
					JG="����";
				}else if("3".equals(ZXKHJG)) {
					JG="һ��";
				}else if("4".equals(ZXKHJG)) {
					JG="�ϲ�";
				}
				desc.append(ZXKHSJ.substring(0,4)+"."+ZXKHSJ.substring(4,6)+ZXKHM+JG+";");	
			}
			
			
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "��");
			}
			this.getPageElement("zxkh").setValue(desc.toString());
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
		this.setNextEventName("init4");// ���ӽṹ����
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ʼ�����ӽṹ����
	@PageEvent("init4")
	@NoRequiredValidate
	public  int  init4() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer nljg=new StringBuffer();
		StringBuffer lyjg=new StringBuffer();
		StringBuffer zyjg=new StringBuffer();
		StringBuffer jgxgb=new StringBuffer();
		try {
			//ƽ������
			@SuppressWarnings("unchecked")
			List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			nljg.append("ƽ������").append(String.valueOf(avgAge.get(0))).append("�꣬50�����¸ɲ�");
			
			//50�����¸ɲ�����
			@SuppressWarnings("unchecked")
			List<String> NumAge50 = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=50 and a02.a0201b=t.b0111 and a02.a0201b='"
					+checkedgroupid+"'").list();
			nljg.append(String.valueOf(NumAge50.get(0))).append("��(");
			
			//50�����¸ɲ�����
			@SuppressWarnings("unchecked")
			List<String> Age50 = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)<=50 and a02.a0201b=t.b0111 and a02.a0201b='"
					+checkedgroupid+"'").list();
			for(int i=0;i<Age50.size();i++) {
				nljg.append("<a>"+String.valueOf(Age50.get(i))+"</a>��");	
			}
			if(nljg.length()>0){
				nljg.deleteCharAt(nljg.length()-1);
			}
			if(Age50.size()>0) {
				nljg.append(")");
			}
			
			//��Դ�ṹ
			//�����Ų�������
			@SuppressWarnings("unchecked")
			List<String> BBMCSRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			
			//�����Ų�������
			@SuppressWarnings("unchecked")
			List<String> BBMCS = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			if(BBMCS.size()>0) {
				lyjg.append("�����Ų���"+String.valueOf(BBMCSRS.get(0))+"�ˣ�");
				for(int i=0;i<BBMCS.size();i++) {
					lyjg.append("<a>"+String.valueOf(BBMCS.get(i))+"</a>��");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("�ޱ����Ų���");
			}
			
			lyjg.append("<br/>");
			//��ֱ���ؽ�������
			@SuppressWarnings("unchecked")
			List<String> WBJLRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='2'").list();
			
			//��ֱ���ؽ�������
			@SuppressWarnings("unchecked")
			List<String> WBJL = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='2'").list();
			if(WBJL.size()>0) {
				lyjg.append("��ֱ���ؽ���"+String.valueOf(WBJLRS.get(0))+"�ˣ�");
				for(int i=0;i<WBJL.size();i++) {
					lyjg.append("<a>"+String.valueOf(WBJL.get(i))+"</a>��");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("����ֱ���ؽ���");
			}
			lyjg.append("<br/>");
			
			
			//�����н�������
			@SuppressWarnings("unchecked")
			List<String> QXSRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='3'").list();
			
			//�����н�������
			@SuppressWarnings("unchecked")
			List<String> QXS = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='3'").list();
			if(QXS.size()>0) {
				lyjg.append("�����н���"+String.valueOf(QXSRS.get(0))+"�ˣ�");
				for(int i=0;i<QXS.size();i++) {
					lyjg.append("<a>"+String.valueOf(QXS.get(i))+"</a>��");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("�������н���");
			}
			lyjg.append("<br/>");
			
			
			
			//��ʦֵ��ת����
			@SuppressWarnings("unchecked")
			List<String> FSZRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='4'").list();
			
			//��ʦֵ��ת����
			@SuppressWarnings("unchecked")
			List<String> FSZ = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='4'").list();
			if(FSZ.size()>0) {
				lyjg.append("��ʦְ��ת"+String.valueOf(FSZRS.get(0))+"�ˣ�");
				for(int i=0;i<FSZ.size();i++) {
					lyjg.append("<a>"+String.valueOf(FSZ.get(i))+"</a>��");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("�޸�ʦְ��ת");
			}
			lyjg.append("<br/>");
			
			
			//��������
			@SuppressWarnings("unchecked")
			List<String> QTRS = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='5'").list();
			
			//��������
			@SuppressWarnings("unchecked")
			List<String> QT = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='5'").list();
			if(QT.size()>0) {
				lyjg.append("������Դ"+String.valueOf(QTRS.get(0))+"�ˣ�");
				for(int i=0;i<QT.size();i++) {
					lyjg.append("<a>"+String.valueOf(QT.get(i))+"</a>��");	
				}
				if(lyjg.length()>0){
					lyjg.deleteCharAt(lyjg.length()-1);
				}
			}else {
				lyjg.append("��������Դ");
			}
			
			
			
			
			//רҵ�ṹ
			for(int i=101;i<113;i++) {
				StringBuffer names=new StringBuffer();
				names.append("ATTR").append(i);
				//ָ��רҵ�ɲ�����
				@SuppressWarnings("unchecked")
				List<String> NumZYGB = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  attr_lrzw."+names.toString()+"='1' and a02.a0201b='"+checkedgroupid+"'").list();
				
				//ָ��רҵ�ɲ�����
				@SuppressWarnings("unchecked")
				List<String> ZYGB = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  attr_lrzw."+names.toString()+"='1' and a02.a0201b='"+checkedgroupid+"'").list();
				
				String name=null;
				if(names.toString().equals("ATTR101")) {
					name="������";
				}else if(names.toString().equals("ATTR102")) {
					name="�ۺϹ�����";
				}else if(names.toString().equals("ATTR103")) {
					name="����ҵ�͹�ҵ������";
				}else if(names.toString().equals("ATTR104")) {
					name="�����ݺ���Ϣ������";
				}else if(names.toString().equals("ATTR105")) {
					name="�ǽ��ǹ���";
				}else if(names.toString().equals("ATTR106")) {
					name="����������";
				}else if(names.toString().equals("ATTR107")) {
					name="������ó��";
				}else if(names.toString().equals("ATTR108")) {
					name="ũҵũ����";
				}else if(names.toString().equals("ATTR109")) {
					name="�Ļ���չ��������";
				}else if(names.toString().equals("ATTR110")) {
					name="���취������";
				}else if(names.toString().equals("ATTR111")) {
					name="��ҵ��Ӫ������";
				}else if(names.toString().equals("ATTR112")) {
					name="���ڲ�����";
				}
				if(ZYGB.size()>0) {
					zyjg.append(name).append("רҵ�ɲ�").append(String.valueOf(NumZYGB.get(0))).append("����");
					for(int j=0;j<ZYGB.size();j++) {
						zyjg.append("<a>"+String.valueOf(ZYGB.get(j))+"</a>��" );
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
			
			
			//�ṹ�Ըɲ�
			//Ů�Ըɲ�����
			@SuppressWarnings("unchecked")
			List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			//Ů�Ըɲ�����
			@SuppressWarnings("unchecked")
			List<String> Female = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104='2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(Female.size()>0) {
				jgxgb.append("Ů�Ըɲ�").append(String.valueOf(NumFemale.get(0))).append("��(");
				for(int j=0;j<Female.size();j++) {
					jgxgb.append("<a >"+String.valueOf(Female.get(j))+"</a>��");
					}
				if(jgxgb.length()>0){
					jgxgb.deleteCharAt(jgxgb.length()-1);
					jgxgb.append(")��");
				}
			}
			
			//����ɲ�����
			@SuppressWarnings("unchecked")
			List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			//����ɲ�����
			@SuppressWarnings("unchecked")
			List<String> DW = HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(DW.size()>0) {
				jgxgb.append("����ɲ�").append(String.valueOf(NumDW.get(0))).append("��(");
				for(int j=0;j<DW.size();j++) {
					jgxgb.append("<a>"+String.valueOf(DW.get(j))+"</a>��");
					}
				if(jgxgb.length()>0){
					jgxgb.deleteCharAt(jgxgb.length()-1);
					jgxgb.append(")��");
				}
			}
			if(jgxgb.length()>0){
				jgxgb.deleteCharAt(jgxgb.length()-1);
			}
			
			
				
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("init5");//�ص��ע�ɲ�
		this.getExecuteSG().addExecuteCode("document.getElementById(\"nljg\").innerHTML='"+nljg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"lyjg\").innerHTML='"+lyjg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"zyjg\").innerHTML='"+zyjg.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jgxgb\").innerHTML='"+jgxgb.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//��ʼ���ص��ע�ɲ�
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

			//����������й���ְ
			@SuppressWarnings("unchecked")
			List<String> yxsgzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='�ۺ�����������й���ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(yxsgzz.size()>0) {
				for(int i=0;i<yxsgzz.size();i++) {
					bxyxsgzz.append("<a>"+String.valueOf(yxsgzz.get(i))+"</a>��");
					}
					if(bxyxsgzz.length()>0){
						bxyxsgzz.deleteCharAt(bxyxsgzz.length()-1);
					}
			}
			
			//���ڿ������й���ְ
			@SuppressWarnings("unchecked")
			List<String> trsgzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='���ڿ������й���ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(trsgzz.size()>0) {
				for(int i=0;i<trsgzz.size();i++) {
					jqtrsgzz.append("<a>"+String.valueOf(trsgzz.get(i))+"</a>��");
					}
					if(jqtrsgzz.length()>0){
						jqtrsgzz.deleteCharAt(jqtrsgzz.length()-1);
					}
			}
			
			//����������йܸ�ְ
			@SuppressWarnings("unchecked")
			List<String> yxsgfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='�ۺ����۱Ƚ�������йܸ�ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(yxsgfz.size()>0) {
				for(int i=0;i<yxsgfz.size();i++) {
					bxyxsgfz.append("<a>"+String.valueOf(yxsgfz.get(i))+"</a>��");
					}
					if(bxyxsgfz.length()>0){
						bxyxsgfz.deleteCharAt(bxyxsgfz.length()-1);
					}
			}
			
			
			//���ڿ������йܸ�ְ
			@SuppressWarnings("unchecked")
			List<String> trsgfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='���ڿ������йܸ�ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(trsgfz.size()>0) {
				for(int i=0;i<trsgfz.size();i++) {
					jqtrsgfz.append("<a>"+String.valueOf(trsgfz.get(i))+"</a>��");
					}
					if(jqtrsgfz.length()>0){
						jqtrsgfz.deleteCharAt(jqtrsgfz.length()-1);
					}
			}
			
			
			//���һ����ע���й���ְ
			@SuppressWarnings("unchecked")
			List<String> gzzz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='���һ����ע���й���ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(gzzz.size()>0) {
				for(int i=0;i<gzzz.size();i++) {
					xgzzz.append("<a>"+String.valueOf(gzzz.get(i))+"</a>��");
					}
					if(xgzzz.length()>0){
						xgzzz.deleteCharAt(xgzzz.length()-1);
					}
			}
			
			//���һ����ע���йܸ�ְ
			@SuppressWarnings("unchecked")
			List<String> gzfz= HBUtil.getHBSession().createSQLQuery("SELECT a01.a0101 FROM a02, a01,b01 t,extra_tags e\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and e.a0196c='���һ����ע���йܸ�ְ' and a02.a0201b='"+checkedgroupid+"'").list();
			
			if(gzfz.size()>0) {
				for(int i=0;i<gzfz.size();i++) {
					xgzfz.append("<a>"+String.valueOf(gzfz.get(i))+"</a>��");
					}
					if(xgzfz.length()>0){
						xgzfz.deleteCharAt(xgzfz.length()-1);
					}
			}
			
			//������������ɲ���������
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
					yxnqgb.append("<a>"+String.valueOf(nqgb.get(i))+"</a>��");
					}
					if(yxnqgb.length()>0){
						yxnqgb.deleteCharAt(yxnqgb.length()-1);
					}
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bxyxsgzz\").innerHTML='"+bxyxsgzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jqtrsgzz\").innerHTML='"+jqtrsgzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bxyxsgfz\").innerHTML='"+bxyxsgfz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"jqtrsgfz\").innerHTML='"+jqtrsgfz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"yxnqgb\").innerHTML='"+yxnqgb.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"xgzzz\").innerHTML='"+xgzzz.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"xgzfz\").innerHTML='"+xgzfz.toString()+"'");
		this.setNextEventName("init6");// �������н���
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	//��ʼ���������н���
	@PageEvent("init6")
	@NoRequiredValidate
	public  int  init6() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bzypjy=new StringBuffer();
		StringBuffer pbjy=new StringBuffer();
		StringBuffer bztd=new StringBuffer();
		StringBuffer zywt=new StringBuffer();
		try {
			//�������н���
			@SuppressWarnings("unchecked")
			List<String> bzypjyb= HBUtil.getHBSession().createSQLQuery("select bzypjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bzypjyb.size()>0 && bzypjyb.get(0)!=null) {
				bzypjy.append(String.valueOf(bzypjyb.get(0)));
			}	
			
			//�����䱸����
			@SuppressWarnings("unchecked")
			List<String> pbjyb= HBUtil.getHBSession().createSQLQuery("select pbjy from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(pbjyb.size()>0 && pbjyb.get(0)!=null) {
				pbjy.append(String.valueOf(pbjyb.get(0)));
			}	
			
			//�����ص�
			
			@SuppressWarnings("unchecked")
			List<String> bztdb= HBUtil.getHBSession().createSQLQuery("select bztd from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(bztdb.size()>0 && bztdb.get(0)!=null) {
				bztd.append(String.valueOf(bztdb.get(0)));
			}
			
			
			//��Ҫ����
			
			@SuppressWarnings("unchecked")
			List<String> zywtb= HBUtil.getHBSession().createSQLQuery("select zywt from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(zywtb.size()>0 && zywtb.get(0)!=null) {
				zywt.append(String.valueOf(zywtb.get(0)));
			}
			//����רҵ��Ĭ��
			@SuppressWarnings("unchecked")
			List<String> zyxbm= HBUtil.getHBSession().createSQLQuery("select zyxbm from GQBZFX where GQBZFX.B0111='"+checkedgroupid+"'").list();
			if(zyxbm.size()>0) {
				if(zyxbm.get(0)!=null && !"".equals(zyxbm.get(0))) {
					this.getPageElement("zyxbm").setValue(zyxbm.get(0));
				}
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzypjy\").value='"+bzypjy.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"pbjy\").value='"+pbjy.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bztd\").value='"+bztd.toString()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById(\"zywt\").value='"+zywt.toString()+"'");
		this.setNextEventName("init7");// ���ӷ���
		this.setNextEventName("init8");// ��Ӫָ��
		this.getExecuteSG().addExecuteCode("openrmb()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ʼ�����ӷ���
	@PageEvent("init7")
	@NoRequiredValidate
	public  int  init7() throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer bzfx=new StringBuffer();
		try {
//			//���ӷ���
//			@SuppressWarnings("unchecked")
//			List<String> bzfxb= HBUtil.getHBSession().createSQLQuery("select bzfx from BZFX where BZFX.B0111='"+checkedgroupid+"'").list();
//			if(bzfxb.size()>0 && bzfxb.get(0)!=null) {
//				bzfx.append(String.valueOf(bzfxb.get(0)));
//			}
		
				
			
			//���ӷ��� ʵ�����
			bzfx.append("ʵ�������");
			//��ȱ��ְ
			@SuppressWarnings("unchecked")
			List<String> kqzz= HBUtil.getHBSession().createSQLQuery("select b0234 from b01  where B0111='"+checkedgroupid+"'").list();
		
			//��ȱ��ְ
			@SuppressWarnings("unchecked")
			List<String> kqfz= HBUtil.getHBSession().createSQLQuery("select b0235 from b01  where B0111='"+checkedgroupid+"'").list();
			
			//����ְλ
			@SuppressWarnings("unchecked")
			List<String> cpzw= HBUtil.getHBSession().createSQLQuery("select b0180 from b01  where B0111='"+checkedgroupid+"'").list();
			
			if(kqzz.get(0)==null && kqfz.get(0)==null && cpzw.get(0)==null) {
				bzfx.append("������������");
			}else {
				if(kqzz.get(0)!=null) {
					bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqzz.get(0))+"����");
				}
				if(kqfz.get(0)!=null) {
					bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqfz.get(0))+"����");
				}
				if(cpzw.get(0)!=null) {
					bzfx.append("���ӳ��������"+String.valueOf(cpzw.get(0))+"��");
				}
			}
			bzfx.deleteCharAt(bzfx.length()-1);
			
			
			bzfx.append("\n����ṹ");
			
			
			//ƽ������
			@SuppressWarnings("unchecked")
			List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			
			//��С����
			@SuppressWarnings("unchecked")
			List<String> minAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(min(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
			
			
			//�������
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
					bzfx.append("(���)��������ֱƽ��������û��50�����¸ɲ���");
					this.getPageElement("nljgyj").setValue("3");
				}else{
					bzfx.append("(�Ƶ�)��������ֱƽ�����䣬����50�����¸ɲ���");
					this.getPageElement("nljgyj").setValue("2");
				}
			}else {
				if(min>=50) {
					bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��û��50�����¸ɲ���");
					this.getPageElement("nljgyj").setValue("2");
				}else {
					if((max-min)<=5){
						bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��50�����¸ɲ�����Ա����������5�����ڡ�");
						this.getPageElement("nljgyj").setValue("2");
					}else {
						bzfx.append("(�̵�)��С����ֱƽ�����䣬��50�����¸ɲ��ҳ�Ա����������5�����ϡ�");
						this.getPageElement("nljgyj").setValue("1");
					}
				}
			}
			
			bzfx.append("\n��Դ�ṹ");
			//�����Ų�������
			@SuppressWarnings("unchecked")
			List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
					" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
					" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
			
			double bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
			
			
//			//�����Ų�������
//			@SuppressWarnings("unchecked")
//			List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("select BDWRS from SZBDW where b0111='"+checkedgroupid+"'").list();
//			double bbm=0;
//			if(bbmRs.size()>0) {
//				if(bbmRs.get(0)!=null && !"".equals(bbmRs.get(0))) {
//					bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
//				}
//			}
			
			//������
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
				bzfx.append("(���)�����ӳ�Ա������֮�������ڲ�������");
				this.getPageElement("lyjgyj").setValue("3");
			}else if(lyzb>=1/2.0) {
				bzfx.append("(�Ƶ�)�����ӳ�Ա�ж���֮һ�����ڲ�������");
				this.getPageElement("lyjgyj").setValue("2");
			}else {
				bzfx.append("(�̵�)�����ӳ�Ա�в������֮һ�ڲ�������");
				this.getPageElement("lyjgyj").setValue("1");
			}
			
			if(bbmRs.size()<1) {
				this.getPageElement("lyjgyj").setValue("");
			}else{
				if(bbmRs.get(0)==null ||"".equals(bbmRs.get(0))) {
					this.getPageElement("lyjgyj").setValue("");
				}
			}
			
			bzfx.append("\nרҵ�ṹ");
			
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
				 bzfx.append("����רҵ�Բ���");
				 this.getPageElement("zyjgyj").setValue("");
			 }else {
				 if(zyzb<(1/3.0)) {
					 bzfx.append("(���)��רҵ����רҵ���ɲ���������֮һ��");
					 this.getPageElement("zyjgyj").setValue("3");
				 }else if(zyzb<(1/2.0)) {
					 bzfx.append("(�Ƶ�)��רҵ����רҵ���ɲ��������֮һ��");
					 this.getPageElement("zyjgyj").setValue("2"); 
				 }else{
					 bzfx.append("(�̵�)��רҵ����רҵ���ɲ���������֮һ��");
					 this.getPageElement("zyjgyj").setValue("1");
				 }
			 }
			bzfx.append("\n");
			 //Ů�Ըɲ�����
			@SuppressWarnings("unchecked")
			List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
			
			
			//����ɲ�����
			@SuppressWarnings("unchecked")
			List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
					"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
					"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
					"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
			
			bzfx.append("Ů�Ըɲ�"+String.valueOf(NumFemale.get(0))+"��������ɲ�"+String.valueOf(NumDW.get(0))+"����");
		
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"bzfx\").value='"+bzfx.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//��Ӫָ��
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
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("BZTPGrid.dogridquery");//���ӵ����б�	
//		this.setNextEventName("init3");
//		this.getExecuteSG().addExecuteCode("document.getElementById(\"tpjy\").value='"+tpjy.toString()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//���ӷ����Լ�����������ݱ���
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
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ɹ���',null,'220')");
		} catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��ȿ������ݱ���
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
					this.setMainMessage("���޸Ŀ��˽���󱣴棡");
					return EventRtnType.NORMAL_SUCCESS;
				}
				sess.update(bzndkh);
			}else{
				if("".equals(bzndkh.getNdkhjg()) || bzndkh.getNdkhjg()==null) {
					this.setMainMessage("��ѡ�񿼺˽����");
					return EventRtnType.NORMAL_SUCCESS;
				}
				sess.save(bzndkh);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��ȿ��˱���ɹ���',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��ȿ��˱���ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//���澭Ӫָ��
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
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��Ӫָ�걣��ɹ���',null,'220')");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��Ӫָ�걣��ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�л����
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
			//��ȿ����������
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
			
			//��ȿ��˽��
			@SuppressWarnings("unchecked")
			List<String> ndkh = HBUtil.getHBSession().createSQLQuery("select NDKHJG from BZNDKH where year='"+khnd+"' and B0111='"+checkedgroupid+"'").list();
			if(ndkh.size()>0) {
				if("1".equals(ndkh.get(0).toString())) {
					ndkhjg.append("����");
				}else if("2".equals(ndkh.get(0).toString())) {
					ndkhjg.append("����");
				}else if("3".equals(ndkh.get(0).toString())) {
					ndkhjg.append("һ��");
				}else if("4".equals(ndkh.get(0).toString())) {
					ndkhjg.append("�ϲ�");
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
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����л�ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
//		this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"');}}, 500);");

		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//��Ӫָ���л����
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
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����л�ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
//		this.getExecuteSG().addExecuteCode("document.getElementById(\"ndyxgr\").value='"+ndyxgr.toString()+"'");
//		this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+"52AA3795-9203-4278-99F3-8D4D9367CF8B"+"');}}, 500);");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//�鿴���������
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
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}

	
	
	// * �ɲ���������б�
	@PageEvent("BZTPGrid.dogridquery")
	@NoRequiredValidate
	public int BZTPGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String b0111=this.getPageElement("checkedgroupid").getValue();
		String sql = "select TP00,B0111,TPGW,'��������' as TPTJ,TPRY from QXSBZTP where b0111='"+b0111+"'";
		this.pageQuery(sql,"SQL", start, limit); //�����ҳ��ѯ
	    return EventRtnType.SPE_SUCCESS;
	}

	
	
	//ɾ���ɲ���������
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
			
			//applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			//��¼��ɾ������
//			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(a15, new A15()));
			HBUtil.executeUpdate("delete from QXYPCONTITION where tp00='"+tp00+"'");
			HBUtil.executeUpdate("delete from QXYPRY where tp00='"+tp00+"'");
			sess.delete(qxsbztp);
			this.getExecuteSG().addExecuteCode("radow.doEvent('BZTPGrid.dogridquery')");
			qxsbztp = new QXSBZTP();
			PMPropertyCopyUtil.copyObjValueToElement(qxsbztp, this);
		} catch (Exception e) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//����һ���ɲ���������
	@PageEvent("BZTPAddBtn.onclick")
	@NoRequiredValidate
	public int BZTPAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		//String a0000 = this.getRadow_parent_data();
		String b0111 =this.getPageElement("checkedgroupid").getValue();
		if(b0111==null||"".equals(b0111)){//
			this.setMainMessage("���ȱ������������Ϣ��");
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
			this.setMainMessage("�����ɹ���");
			this.setNextEventName("BZTPGrid.dogridquery");//���°��ӵ����б�	
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//�޸ĵ����
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
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
//		String show=this.getPageElement("tp00").getValue();
//		this.getExecuteSG().addExecuteCode("alert('"+show+"')");
		this.getExecuteSG().addExecuteCode("openBZTP()");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
}
