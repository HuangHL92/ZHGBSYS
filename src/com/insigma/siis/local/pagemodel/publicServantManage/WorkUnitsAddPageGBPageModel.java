package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SQLQuery;

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
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * ������λ��ְ�������޸�ҳ��
 * @author Administrator
 *
 */
public class WorkUnitsAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			HBSession sess = HBUtil.getHBSession();
			List<A02> list = sess.createQuery("from A02 where a0000='"+a0000+"'").list();
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�		
		//a01�й�����λ��ְ��
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
/*			Long a0194 = a01.getA0194();
			if(a0194!=null){
				this.getPageElement("a0194_y").setValue(a0194/12+"");
				this.getPageElement("a0194_m").setValue(a0194%12+"");
			}*/
			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			
		/*	//ͳ�ƹ�ϵ���ڵ�λ ���ƣ���ø�ҳ��ֵ
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//�������� ���ġ�
				}
			}*/
			/*
			
			if(a01.getA0197() == null || a01.getA0197().equals("")){		//�Ƿ�����������ϻ��㹤������Ϊ��
				this.getExecuteSG().addExecuteCode("document.getElementById('a0197').checked=false;");
			}*/
			
			//�����ְ��ְ������idΪnull����"",��id����Ϊ-1��������λ��
			//HBUtil.executeUpdate("update a02 set a0201b='-1' where a0000='"+a0000+"' and (a0201b='' or a0201b is null)");
			
			
			//���ͳ�����ڵ�λҳ����ʾ
			/*if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("setParentA0194Value();");
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
		/*String a0000 = this.getPageElement("subWinIdBussessId").getValue();//this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�		
		//a01�й�����λ��ְ��
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			Long a0194 = a01.getA0194();
			if(a0194!=null){
				this.getPageElement("a0194_y").setValue(a0194/12+"");
				this.getPageElement("a0194_m").setValue(a0194%12+"");
			}
			
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			
			//ͳ�ƹ�ϵ���ڵ�λ ���ƣ���ø�ҳ��ֵ
			if(a01.getA0195()!=null){
				String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
				if(a0195!=null){
					this.getPageElement("a0195_combo").setValue(a0195);//�������� ���ġ�
				}
			}
			
			
			if(a01.getA0197() == null || a01.getA0197().equals("")){		//�Ƿ�����������ϻ��㹤������Ϊ��
				this.getExecuteSG().addExecuteCode("document.getElementById('a0197').checked=false;");
			}
			
			//�����ְ��ְ������idΪnull����"",��id����Ϊ-1��������λ��
			//HBUtil.executeUpdate("update a02 set a0201b='-1' where a0000='"+a0000+"' and (a0201b='' or a0201b is null)");
			
			
			//���ͳ�����ڵ�λҳ����ʾ
			if(a01.getA0195() != null && !a01.getA0195().equals("") && a01.getA0195().equals("-1")){	
				
				((Combo)this.getPageElement("a0195")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0195').value='';"
						+ "document.getElementById('a0195_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("setParentA0194Value();");
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");*/
	}
	
	
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveWorkUnits()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A01 a01 = (A01)sess.get(A01.class, a0000);
		
		A02 a02 = new A02();
		this.copyElementsValueToObj(a02, this);
		/*if(a02.getA0200() == null || "".equals(a02.getA0200())){
			this.getExecuteSG().addExecuteCode("realParent.addResume('"+a02.getA0201a()+"','"+a02.getA0215a()+"','"+a02.getA0243()+"');");
		}*/
		
		
		/*if(!a01.getA0163().equals("1")){	//����ְ��Ա
			//����ְ��Ա����������ְ��
			String msgf = "����ְ��Ա����������ְ��";
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.setMainMessage(msgf);
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String sqlF = "from A02 where a0000='"+a0000+"' and A0255='1'";	//���ε�ְ��
			List<A02> list = sess.createQuery(sqlF).list();
			if(list.size() != 0){
				this.setMainMessage(msgf);
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}*/
		
		
		String a0279 = a02.getA0279();			//��ְ���ʶ
		//�ж���ְ�������Ƿ���ȷ
		//���������ǰ�����ְ������ְ����鿴�Ƿ�����ְ���������ְ��������ְ��A0279����Ϊ0
		/*if(a0279 == null || a0279.equals("0")){
			
			String sqlOne = null;
			String msg = null;
			//��Ա����״̬��1����ְ��Ա��
			if(a01.getA0163().equals("1")){
				msg = "��ְ��Ա������һ��������ְ��";
				sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//���Ρ���ְ���ְ��
				
				msg = "������һ����ְ��";
				sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//���Ρ���ְ���ְ��
				
			}else{			//����ְ��Ա
				
				//msg = "����ְ��Ա������һ����ְ��";
				
				msg = "������һ����ְ��";
				//sqlOne = "from A02 where a0000='"+a0000+"' and a0281='true' and a0279='1' order by a0223";//�������ְ���ְ��
				sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//��ְ���ְ��
			}
			
			
			List<A02> list = sess.createQuery(sqlOne).list();
			if(list.size() == 0){
				this.setMainMessage(msg);
				return EventRtnType.NORMAL_SUCCESS;
			}
		}*/
		if(a0279 != null && a0279.equals("1")){				//�������ְ��������ְ��a0279����Ϊ0
			
			/*if(!a02.getA0255().equals("1")){
				//this.setMainMessage("��ְ��Ա������һ��������ְ��");
				
				this.setMainMessage("������һ����ְ��");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			HBUtil.executeUpdate("update a02 set a0279 = '"+0+"' where a0000 = '"+a0000+"'");
			sess.flush();
		}
		
		
		String a0201bb = this.getPageElement("a0201b").getValue();
		
		String a0201a = a02.getA0201a();
		//String a0201a = this.getPageElement("a0201b_combo").getValue();//�������� ���ġ�
		//a02.setA0201a(a0201a);
		
		

		String aa0201b = this.getPageElement("a0201b").getValue();
		if(aa0201b == null || "".equals(aa0201b)){
			this.setMainMessage("��ְ����/�������� ����Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String a0255 = this.getPageElement("a0255").getValue();
		if(a0255 == null || "".equals(a0255)){
			this.setMainMessage("��ְ����ְ״̬/����״̬ ����Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String b0194 = this.getPageElement("b0194Type").getValue();
		/*if("1".equals(b0194)){*/
			String a0201d = a02.getA0201d();
			
			/*if(a0201d == null || "".equals(a0201d.trim()) || a0201d.equals("0")){
				this.setMainMessage("��ְ����Ϊ���˵�λʱ���Ƿ���ӳ�Ա����ѡ��");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//�Ƿ���ӳ�Ա Ϊ'��'ʱ
			if("1".equals(a0201d)){
				String a0201e = this.getPageElement("a0201e_combo").getValue();//a0201e_combo
				if(a0201e == null || "".equals(a0201e.trim())){
					this.setMainMessage("�쵼��Ա Ϊ'��'ʱ����Ա��������д��");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		/*}*/
		
		/*if("2".equals(b0194)){
			a02.setA0201d(null);
		}
		*/
		//ְ�����ƣ�������д�Ҳ������пո�������Ǻ����ַ�
		String a0215a = this.getPageElement("a0215a").getValue();
		if(a0215a == null || "".equals(a0215a)){
			this.setMainMessage("ְ�����Ʋ���Ϊ�գ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//��ְ�����ƵĹ淶���ж�
		/*if(a0215a != null || !"".equals(a0215a)){
			if (a0215a.indexOf(" ") >=0){
				this.setMainMessage("ְ�����Ʋ��ܰ����ո�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//String reg = "[\\u4e00-\\u9fa5]+";//��ʾ+��ʾһ����������
			String reg = "[\u4E00-\u9FA5a-zA-Z0-9]+";	//ֻ�ܰ������֣�Ӣ�ĺ�����
			
			String a0215a_reg=a0215a.replace(",", "").replace("��", "").replace("��", "");
			if(!a0215a_reg.matches(reg)){
				this.setMainMessage("ְ�����������ʽ����ȷ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}*/
		
		//��ְ�ĺ�a0245
		String a0245 = this.getPageElement("a0245").getValue();
		if(a0245 != null || "".equals(a0245)){
			if(a0245.length() > 130){
				this.setMainMessage("��ְ�ĺŲ��ܳ���130�֣�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//��ְ�ĺ�a0267
		String a0267 = this.getPageElement("a0267").getValue();
		if(a0267 != null || "".equals(a0267)){
			if(a0267.length() > 24){
				this.setMainMessage("��ְ�ĺŲ��ܳ���24�֣�");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//ְ������a0215a
		if(a0215a.length() > 50){
			this.setMainMessage("ְ�����Ʋ��ܳ���50�֣�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//1�ǣ�2��
		String aa0219 = a02.getA0219();
		if(aa0219 != null && "0".equals(aa0219)){
			a02.setA0219("2");
		}
		//��ְʱ�䲻��������ְʱ��
		String a0265 = a02.getA0265();//��ְʱ��
		String a0243 = a02.getA0243();//��ְʱ��
		if(a0265!=null&&!"".equals(a0265)&&a0243!=null&&!"".equals(a0243)){
			if(a0265.length()==6){
				a0265 += "00";
			}
			if(a0243.length()==6){
				a0243 += "00";
			}
			if(a0265.compareTo(a0243)<0){
				this.setMainMessage("��ְʱ�䲻��������ְʱ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		a02.setA0000(a0000);
		String a0200 = this.getPageElement("a0200").getValue();
		Boolean updateJGZW = false;
		//HBSession sess = null;
		try {
			
			String a0201b = a02.getA0201b();//��ְ�������롣			
			//sess = HBUtil.getHBSession();
			
			//������ְ�ṹ�����ȡ��ͬ������ְ�������ֵ��
			/*if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sql).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}*/
			if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
				
				Object sqlA0225 = sess.createSQLQuery(sql).uniqueResult();
				
				String maxA0225  =  null;
				if(sqlA0225 != null){
					maxA0225  = sqlA0225.toString();
				}
				
				if(maxA0225!=null && !maxA0225.equals("")){
					Long a0225 = Long.valueOf(maxA0225);
					a02.setA0225(a0225 + 1L);
				}else{		//�û����µ�һ������ְ��Ա����ʼ��a0225
					a02.setA0225(1L);
				}
				
				//��������ĳ�˵ģ�ͬ�����µ�N��N>1����ְ����Ϣ����a0225��Ҫ���������������ְ���a0225һ��(��ĳ��ͬ���������е�ְ��a0225һ��)
				String sqlTwo="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sqlTwo).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}
			
			
			

			if(a0201bb!=null&&!"".equals(a0201bb)){//��ȡ�������
				if("-1".equals(a0201bb)){//������λ
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201bb);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				
				
			}
			
			if(a0200==null||"".equals(a0200)){
				
				if(a02.getA0255().equals("0")){
					a02.setA0281("false");//�Ƿ����
					this.getPageElement("a0281").setValue("false");
				}else{
					a02.setA0281("true");//�Ƿ����
					this.getPageElement("a0281").setValue("true");
				}
				
				
				applog.createLog("3021", "A02", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(new A02(), a02));
				sess.save(a02);	
				updateJGZW = true;
			}else{
				if(a02.getA0281()==null||"".equals(a02.getA0281())){
					
					if(a02.getA0255().equals("0")){
						a02.setA0281("false");//�Ƿ����
						this.getPageElement("a0281").setValue("false");
					}else{
						a02.setA0281("true");//�Ƿ����
						this.getPageElement("a0281").setValue("true");
					}
					
				}
				
				A02 a02_old = (A02)sess.get(A02.class, a0200);
				String jg_old = a02_old.getA0201a();//����
				String jg =  a02.getA0201a();//����
				if(jg_old!=null&&!jg_old.equals(jg)){
					updateJGZW = true;
				}else if(jg_old==null&&jg!=null){
					updateJGZW = true;
				}
				String zw_old = a02_old.getA0215a();//ְ��
				String zw =  a02.getA0215a();//ְ��
				
				if(zw_old!=null&&!zw_old.equals(zw)){
					updateJGZW = true;
				}else if(zw_old==null&&zw!=null){
					updateJGZW = true;
				}
				String rzzt_old = a02_old.getA0255();//��ְ״̬
				String rzzt = a02.getA0255();//��ְ״̬
				if(rzzt_old!=null&&!rzzt_old.equals(rzzt)){
					updateJGZW = true;
				}else if(rzzt_old==null&&rzzt!=null){
					updateJGZW = true;
				}
				
				String rzsj_old = a02_old.getA0243();//��ְʱ��
				String rzsj = a02.getA0243();//��ְʱ��
				if(rzsj_old!=null&&!rzsj_old.equals(rzsj)){
					updateJGZW = true;
				}else if(rzsj_old==null&&rzsj!=null){
					updateJGZW = true;
				}
				
				
				applog.createLog("3022", "A02", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a02_old, a02));
				String b0111 = a02_old.getA0201b();
				String b0111s = a02.getA0201b();
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111s);
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
				PropertyUtils.copyProperties(a02_old, a02);
				
				sess.update(a02_old);	
			}
			
			
			String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //���ְ��
			List<A02> list = sess.createQuery(sqlOut).list();
			if(list.size() == 0){
				this.setMainMessage("����Ҫ��һ�����ְ��");
				return EventRtnType.FAILD;
			}
			
			
			
			//��������
			String a0192a = this.getPageElement("a0192a").getValue();
			String a0192 = this.getPageElement("a0192").getValue();
			a01.setA0192a(a0192a);
			a01.setA0192(a0192);
			//��Ա������Ϣ����
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			updateA01(a01,sess);
			sess.update(a01);
			sess.flush();
			
			
			//��a01������������ֶ���ά��TORGID����߻�������TORDER����߻������ڻ���������
			HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
			HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");  
			
			
			//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//ˢ�¹�����λ��ְ���б�
			/*this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"window.realParent.ajaxSubmit('genResume');");*/
			
			//�޸ĸ�ҳ���ͳ�ƹ�ϵ���ڵ�λ
			/*String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();*/
		/*	if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("realParent.setA0195Value('"+key+"','"+value+"');");
			}*/
			
			CustomQueryBS.setA01(a0000);
	    	A01 a01F = (A01)sess.get(A01.class, a0000);
			/*this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));*/
			
			
			
			if(a0279 == null || a0279.equals("0")){
				
				String sqlOne = null;
				String msg = null;
				//��Ա����״̬��1����ְ��Ա��
				if(a01.getA0163().equals("1")){
					/*msg = "��ְ��Ա������һ��������ְ��";
					sqlOne = "from A02 where a0000='"+a0000+"' and a0255='1' and a0279='1' order by a0223";//���Ρ���ְ���ְ��
*/					
					msg = "������һ����ְ��";
					sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//���Ρ���ְ���ְ��
					
				}else{			//����ְ��Ա
					
					//msg = "����ְ��Ա������һ����ְ��";
					
					msg = "������һ����ְ��";
					//sqlOne = "from A02 where a0000='"+a0000+"' and a0281='true' and a0279='1' order by a0223";//�������ְ���ְ��
					sqlOne = "from A02 where a0000='"+a0000+"' and a0279='1' order by a0223";//��ְ���ְ��
				}
				
				
				List<A02> list2 = sess.createQuery(sqlOne).list();
				if(list2.size() == 0){
					this.setMainMessage(msg);
					return EventRtnType.FAILD;
				}
			}
			
			
			this.getPageElement("a0200").setValue(a02.getA0200());//����ɹ���id���ص�ҳ���ϡ�
			if(updateJGZW){
				this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','�Ƿ��ñ�ְ����Ϣ�������ɲ���������ְ���ȫ�ƺͼ��?',350,function(id){" +
						"if(id=='ok'){" +
							"radow.doEvent('UpdateTitleBtn.onclick');	" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				this.setMainMessage("����ɹ���");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void updateA01(A01 a01, HBSession sess){
		//����a01 ְ���Ρ� a0148 a0149     a02 a0221
		String sql="select a0221 from A02 a where a0000='"+a01.getA0000()+"' and a0255='1'";
		List<String> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list, new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					if(o1==null||"".equals(o1)){
						return 1;
					}
					if(o2==null||"".equals(o2)){
						return -1;
					}
					return o1.compareTo(o2);
				}
			});
			
			a01.setA0148(list.get(0));
			/*//��Ա������Ϣ����
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='"+(a01.getA0148()==null?"":a01.getA0148())+"';");*/
		}else{
			//ְ��Ϊ��
			a01.setA0148("");
		/*	this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0148').value='';");*/
		}
		
	}
	
	
	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}
/** *********************************************������λ��ְ��(a02)******************************************************************** */
	
	/**
	 * ������λ��ְ���б�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.dogridquery")
	@NoRequiredValidate
	public int workUnitsGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select * from a02 where a0000='"+a0000+"' order by lpad(a0223,5,'"+0+"') ";
		this.pageQuery(sql,"SQL", start, limit); //�����ҳ��ѯ
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ������λ��ְ��������ť
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsAddBtn.onclick")
	@NoRequiredValidate
	public int workUnitsWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A02 a02 = new A02();
		this.getPageElement("a0201b_combo").setValue("");
		
		PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		this.getPageElement("a0000").setValue(a0000);
		this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');document.getElementById('a0201d').checked=false;document.getElementById('a0251b').checked=false;"
				+ "document.getElementById('a0219').checked=false;document.getElementById('a0255').value='1';document.getElementById('a0279').checked=false;"
				+ "document.getElementById('a02551').checked=true;setA0201eDisabled();a0255SelChange()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������λ��ְ���޸��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//��ȡѡ����index
		int index = this.getPageElement("WorkUnitsGrid").getCueRowIndex();
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();
		HBSession sess = null;
		try {
			
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			String a0219 = a02.getA0219();
			if(a0219 != null && "2".equals(a0219)){
				a02.setA0219("0");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			setZB08Code(a02.getA0201b());
			
			//fujun
			/*if(a02.getA0201b() != null && !a02.getA0201b().equals("")){		//��ְ����id��Ϊ��
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//�������� ���ġ�
			}*/
			
			this.getPageElement("a0201a").setValue(a02.getA0201a());	//��ְ��������
			
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
			
			//��ְ״̬,���⴦��
			if(a02.getA0255() != null && a02.getA0255().equals("0")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=false;document.getElementById('a02550').checked=true;a0255SelChange();");
			}
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=true;document.getElementById('a02550').checked=false;a0255SelChange();");
			}
			
			
			//�����ְ����Ϊ-1����ҳ�治��ʾ
			/*if(a02.getA0201b() != null && !a02.getA0201b().equals("") && a02.getA0201b().equals("-1")){	
				
				((Combo)this.getPageElement("a0201b")).setValue("");
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201b').value='';"
						+ "document.getElementById('a0201b_combo').value='';"
						);
				
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			
			//�������ְ��a0279���������쵼ְ��(a0219)�������쵼��Ա(a0201d)�������Ƹ����(a0251b)�����ݲ�Ϊ1����ҳ�治��ѡ
			if(a02.getA0279() == null || !a02.getA0279().equals("1")){		//��ְ��Ϊ��
				this.getExecuteSG().addExecuteCode("document.getElementById('a0279').checked=false;");
			}
			
			if(a02.getA0219() == null || !a02.getA0219().equals("1")){		//�쵼ְ��Ϊ��
				this.getExecuteSG().addExecuteCode("document.getElementById('a0219').checked=false;");
			}
			
			if(a02.getA0201d() == null || !a02.getA0201d().equals("1")){		//�쵼��Ա
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201d').checked=false;");
			}
			
			if(a02.getA0251b() == null || !a02.getA0251b().equals("1")){		//�Ƹ����
				this.getExecuteSG().addExecuteCode("document.getElementById('a0251b').checked=false;");
			}
			
			
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * �����ѡ��ѡ���¼�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("workUnitsgridchecked")
	@Transaction
	@NoRequiredValidate
	public int workUnitsgridchecked(String listString) throws RadowException {
		Map map = this.getRequestParamer();
		//String a0200 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		
		String[] listF = listString.split(",");
		
		String a0200 =  listF[0];
		String a0281 = listF[1];
		
		try{
			if(a0200!=null){
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				/*Boolean checked = Boolean.valueOf(a02.getA0281());
				a02.setA0281(String.valueOf(!checked));*/
				
				a02.setA0281(a0281);
				sess.save(a02);
				PMPropertyCopyUtil.copyObjValueToElement(a02, this);
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//�������� ���ġ�
				//this.getExecuteSG().addExecuteCode("a0222SelChange();");
				
				//ˢ���б�
				//this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
				
				String a0000 = this.getPageElement("a0000").getValue();
				String sqlOut = "from A02 where a0000='"+a0000+"' and a0281='true'";   //���ְ��
				List<A02> list = sess.createQuery(sqlOut).list();
				if(list.size() == 0){
					//this.getPageElement("a0281").setValue("true");
					this.getExecuteSG().addExecuteCode("changebox();");
					this.setMainMessage("����Ҫ��һ�����ְ��");
					return EventRtnType.FAILD;
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		
		//this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�		
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");
		this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','�Ƿ��������ɲ���������ְ���ȫ�ƺͼ��?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"  " +
					"}" +
				"});");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * ��������
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("a0000").getValue();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ������λand a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//���� ���
			
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String zrjc = "";//���
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//��ְ״̬
				String jgbm = a02.getA0201b();//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//�������� ���
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//ְ������
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc);
										
										
									}
									//���
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "��" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key ����_$_��������_$_�Ƿ�����
						}else{
							desc_full.put(key_full, value_full + "��" + zwmc);
						}
						
						//���
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "��" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else{//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//���
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "��";
					}
				}else{//����
					if(!"".equals(jgzw)){
						ymjc += jgzw + "��";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(ԭ"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(ԭ"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//��Ա������Ϣ����
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
		}else{
			//this.getPageElement("a0192a").setValue("");
			//this.getPageElement("a0192").setValue("");
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			//��Ա������Ϣ����
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
			
		}
		
		this.UpdateTimeBtn(id);
		this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�	
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * �������ƶ�Ӧ��ʱ��
	 * @return
	 * @throws RadowException
	 */
	@Transaction
	@NoRequiredValidate
	public int UpdateTimeBtn(String id) throws RadowException {
		
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("a0000").getValue();
		} catch (RuntimeException e) {
			
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ������λand a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//���� ȫ��
			
			
			String zrqm = "";//ȫ�� ����
			String ymqm = "";//����
			String zrjc = "";//���
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//��ְ״̬
				String jgbm = a02.getA0201b();//��������
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//�������� ȫ��
				//jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				jgmcList.add("");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//�������� ���
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//ְ������
				
				String zwrzshj = "";//ְ����ְʱ��
				if(a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8){
					zwrzshj = a02.getA0243().substring(0,4) + "." + a02.getA0243().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//�����������ЩΪ�ա� �������벻Ϊ�ա�
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1�����˵�λ��2�����������3���������顣
					if("2".equals(b0194)){//2���������
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2���������
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3����������
									continue;
								}else if("1".equals(b0194)){//1�����˵�λ
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//ȫ��
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//��ͬ��������� ֱ�ӶٺŸ����� ���������ͬ���ϼ����ݹ飩 ���˵�λ������ͬ�� ���һ�����������ڶ��Σ��ڶ��ζٺŸ�������������������֮����ʾ 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "��" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "��" + zwrzshj);
										
									}
									
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1�����˵�λ�� ��һ�ξ��Ƿ��˵�λ�������ϵݹ�
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							//desc_full.put(key_full, zwmc+zwrzshj);//key ����_$_��������_$_�Ƿ�����
							desc_full.put(key_full, zwrzshj);//key ����_$_��������_$_�Ƿ�����
						}else{
							//desc_full.put(key_full, value_full + "��" + zwmc+zwrzshj);
							desc_full.put(key_full, value_full + "��" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//ȫ��
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//����
					//��ְ���� ְ������		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "��";
					}
				}else{//����
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "��";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "("+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "("+ymjc+")";
			}
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(zrqm+ymqm);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
		}else{
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(null);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a0200)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			
			A01 a01 = (A01)sess.get(A01.class, a02.getA0000());
			applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A02(), new A02()));
			sess.delete(a02);
			sess.flush();
			//������Ա״̬
			updateA01(a01, sess);
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
			
			//ˢ���б����Ҹ��¼�����Ϣ
			/*this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"window.realParent.ajaxSubmit('genResume');");*/
			
			a02 = new A02();
			this.getPageElement("a0201b_combo").setValue("");
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
		} catch (Exception e) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		//��ʾ�Ƿ����ȫ�ơ����
		/*this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','�Ƿ��ñ�ְ����Ϣ�������ɲ���������ְ���ȫ�ƺͼ��?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");*/
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String id)throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("WorkUnitsGrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0, j = 0;
			for(HashMap<String,String> m : list){
				String a0200 = m.get("a0200");//a02 id
				String a0255 = m.get("a0255");//��ְ ״̬
				/*if("1".equals(a0255)){//��ְ
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}*/
				HBUtil.executeUpdate("update a02 set a0223="+ j++ +" where a0200='"+a0200+"'");
				
			}
			
			
			this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�		
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("sortUseTime")
	@NoRequiredValidate
	@Transaction
	public int sortUseTime(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "From A02 where a0000='"+a0000+"' order by a0243 desc ";//��ְ״̬  ��ְʱ�併��
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			List<A02> list = sess.createQuery(sql).list();
			int i = 0,j = 0;
			if(list!=null&&list.size()>0){
				for(A02 a02 : list){
					String a0200 = a02.getA0200();//a02 id
					String a0255 = a02.getA0255();//��ְ ״̬
					/*if("1".equals(a0255)){//��ְ
						HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
					}else{
						HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
					}*/
					HBUtil.executeUpdate("update a02 set a0223="+ j++ +" where a0200='"+a0200+"'");
				}
			}
			/*for(A02 a02 : list){
				String a0200 = a02.getA0200();//a02 id
				String a0255 = a02.getA0255();//��ְ ״̬
				if("1".equals(a0255)){//��ְ
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
			}*/
			this.setNextEventName("WorkUnitsGrid.dogridquery");//������λ��ְ���б�		
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id)throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+id+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		
		if(B0194 != null && B0194.equals("3")){	
			
			String msg = "����ѡ��������鵥λ��";
			
			((Combo)this.getPageElement("a0201b")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('ϵͳ��ʾ','"+msg+"');document.getElementById('a0201b').value='';"
					+ "document.getElementById('a0201b_combo').value='';"
					);
			
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		if(B0194 != null && B0194.equals("2")){								//��ְ�������ѡ�����������������ְ״̬��ְ�����Ʋ���ѡ
			
			/*this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','disabled');document.getElementById('a0201d').checked=false; "
					+ "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
					+ "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
					+ "document.getElementById('a0201e').value='';document.getElementById('a0201e_combo').value='';document.getElementById('b0194Type').value='2';changea0201d(2);"
					);*/
			
			this.getExecuteSG().addExecuteCode(""
					+ "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
					+ "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
					+ "document.getElementById('b0194Type').value='2';changea0201d(2);"
					);
			this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
		}else{
			if(B0194 != null && B0194.equals("1")){
				this.getExecuteSG().addExecuteCode("changea0201d(1);document.getElementById('b0194Type').value='1';");
			}else{
				this.getExecuteSG().addExecuteCode("changea0201d(2);document.getElementById('b0194Type').value='3';");
			}
			/*this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');"
					+ "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
					+ "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
					+ "Ext.query('#a0201e_combo+img')[0].style.display='block';"
					);*/
			
			this.getExecuteSG().addExecuteCode(""
					+ "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
					+ "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
					+ "Ext.query('#a0201e_combo+img')[0].style.display='block';"
					);
			this.getExecuteSG().addExecuteCode("setA0201eDisabled()");
		}
		
		try {
			String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			if(v!=null){
				this.getPageElement("a0201b_combo").setValue(v);
			}else{
				//this.getPageElement("a0201b_combo").setValue("");
			}
			/*
			 
			 String v = HBUtil.getValueFromTab("b0131", "B01", "b0111='"+id+"'");
			if(v!=null&&("1001".equals(v)||"1002".equals(v)||"1003".equals(v)||"1004".equals(v)
					||"1005".equals(v)||"1006".equals(v)||"1007".equals(v))){
				this.getPageElement("ChangeValue").setValue(v);
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.show();");
			}else{
				this.getExecuteSG().addExecuteCode("var combo = Ext.getCmp('a0215a_combo');" +
						"combo.setValue('');" +
						"document.getElementById('a0215a').value='';" +
						"combo.hide();");
			}
			 */
			
			
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		//var newStore = a0255f.getStore();
		//newStore.removeAll();
		//newStore.add(new Ext.data.Record({key:'1',value:'����'}));
		//newStore.add(new Ext.data.Record(item.two));
		
		/*List<String> jsondata = CodeType2js.getCodeTypeList("ZB08", null, null);
		StringBuffer bf = new StringBuffer("var a0215a = Ext.getCmp('a0215a_combo');var newStore = a0215a.getStore();");
		bf.append("newStore.removeAll();");
		for(String s : jsondata){
			bf.append("newStore.add(new Ext.data.Record("+s+"));");
		}
		this.getExecuteSG().addExecuteCode(bf.toString());*/
		
		//����ְ�������Ƹ�ֵ��ҳ��a0201a
		if(id != null && !id.equals("")){
			String a0201a = this.getPageElement("a0201b_combo").getValue();//�������� ���ġ�
			this.getPageElement("a0201a").setValue(a0201a);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/*//���ͳ�ƹ�ϵ���ڵ�λ�Ƿ�Ϊ�����������
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		if(B0194 == null){
			B0194 = "";
		}
		
		if(B0194 != null && B0194.equals("2") || B0194.equals("3")){
			
			String msg = "����ѡ�����������λ��";
			if(B0194.equals("3")){
				msg = "����ѡ��������鵥λ��";
			}
			
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('ϵͳ��ʾ','"+msg+"');document.getElementById('a0195').value='';"
					+ "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_combo').value='';"
					);
		}else{
			//�޸ĸ�ҳ���ͳ�ƹ�ϵ���ڵ�λ
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("realParent.setA0195Value('"+key+"','"+value+"');");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	
	@PageEvent("a0201bChange")
	@NoRequiredValidate
	public int a0201bChange(String a0201b) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0201b+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();				//��������
		
		//û��ְ����Ϣʱ��������
		int num = this.getPageElement("WorkUnitsGrid").getStringValueList().size();
		if(num == 0){
			
			/*String a0195 = this.getPageElement("a0195").getValue();		//ͳ�����ڵ�λ
*/			
			
				if(B0194 != null && !B0194.equals("2")){				//�������ְ������Ϊ�����������������
					
					
					/*if((a0195 != null && a0195.equals("")) || num == 0){			//�����ͳ�����ڵ�λ���Ѿ����ڣ���������
					
						this.getPageElement("a0195").setValue(a0201b);
						String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+a0201b+"'");
						if(v!=null){
							this.getPageElement("a0195_combo").setValue(v);
							this.getPageElement("a0195value").setValue(v);
						}else{
							this.getPageElement("a0195_combo").setValue("");
							this.getPageElement("a0195value").setValue("");
						}
						
						
						this.getPageElement("a0195key").setValue(a0201b);
						
						this.a0195Change(a0201b);
						
					}*/
				
				}else if(B0194.equals("2")){		//���Ϊ�����������һ�����˵�λ��ֵ����ͳ�����ڵ�λ
					
					//��ȡ�������ṹ
					List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
					
					Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
					for(Object[] treedata : listres){
						TreeNode rootnode = genNode(treedata);
						nodemap.put(rootnode.getId(), rootnode);
					}
					
					TreeNode bcn = nodemap.get(a0201b);
					
					//String sql = "select B0121 from B01 where  B0111 ='"+a0201b+"'";
					String b0194 = "";
					if(bcn != null){
						b0194 = bcn.getText();
						
						
						while(true){
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if(n == null){
								throw new RadowException("������ȡ�쳣");
							}
							if(n.getText() == null||"".equals(n.getText())){
								throw new RadowException("������ȡ�쳣");
							}
							a0201b = n.getId();
							if(!"1".equals(n.getText())){//���Ƿ��˵�λ,���������һ���
								continue;
							}else{			//���˵�λ
								
								String B0111 = n.getId();			//����id
								String B0101 = n.getLink(); 		//��������
								
								
								/*this.getPageElement("a0195").setValue(B0111);
								this.getPageElement("a0195key").setValue(B0111);
								this.getPageElement("a0195_combo").setValue(B0101);
								this.getPageElement("a0195value").setValue(B0101);*/
								
								/*this.a0195Change(B0111);*/
								
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
						
					}else{
						throw new RadowException("������ȡ�쳣");
					}
					
				}else {
					/*this.getPageElement("a0195").setValue("");
					this.getPageElement("a0195_combo").setValue("");
					
					this.getPageElement("a0195key").setValue("");
					this.getPageElement("a0195value").setValue("");
					this.a0195Change("");*/
				}
			
			
			
		}
				
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*//�ж�ͳ�ƻ�����ϵ�Ƿ����
	@PageEvent("check")
	@NoRequiredValidate
	public int check() throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String a0195 = this.getPageElement("a0195").getValue();
		String a0201b = this.getPageElement("a0201b").getValue();
		
		if(a0195 == null || "".equals(a0195)){
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
				+ " '��ʾ', "
				+ " 'ͳ�ƹ�ϵ���ڵ�λΪ�գ��Ƿ�������棿', "
				+ " function (btn){ "
				+ "	if(btn=='yes'){ "
				+ "		radow.doEvent('save'); "
				+ "	} "
				+ "} "
			    + ");");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//��ȡ�������ṹ
		List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
		
		Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
		for(Object[] treedata : listres){
			TreeNode rootnode = genNode(treedata);
			nodemap.put(rootnode.getId(), rootnode);
		}
		TreeNode bcn = nodemap.get(a0201b);
		String b0194 = "";
		if(bcn != null){
			b0194 = bcn.getText();
			if(!"2".equals(b0194)){
				this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
						+ " '��ʾ', "
						+ " 'ͳ�ƹ�ϵ���ڵ�λ����ְ�������Ʋ���ͬ���Ƿ�������棿', "
						+ " function (btn){ "
						+ "	if(btn=='yes'){ "
						+ "		radow.doEvent('save'); "
						+ "	} "
						+ "} "
					    + ");");
					return EventRtnType.NORMAL_SUCCESS;
			}else{
				while(true){
					TreeNode cn = nodemap.get(a0201b);
					TreeNode n = nodemap.get(cn.getParentid());
					if(n == null){
						throw new RadowException("������ȡ�쳣");
					}
					if(n.getText() == null||"".equals(n.getText())){
						throw new RadowException("������ȡ�쳣");
					}
					a0201b = n.getId();
					if(!"1".equals(n.getText())){//���Ƿ��˵�λ,���������һ���
						continue;
					}else{//���˵�λ
						if(a0195.equals(n.getId())){
							this.getExecuteSG().addExecuteCode("radow.doEvent('save');");
							return EventRtnType.NORMAL_SUCCESS;
						}else{
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
									+ " '��ʾ', "
									+ " 'ͳ�ƹ�ϵ���ڵ�λ����ְ�������Ʋ���ͬ���Ƿ�������棿', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('save'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
			}
		}else{
			throw new RadowException("������ȡ�쳣");
		}
	}*/
	
	private static TreeNode genNode(Object[] treedata) {
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[1].toString());
		node.setLink(treedata[3].toString());
		node.setLeaf(true);
		if(treedata[2]!=null)
			node.setParentid(treedata[2].toString());
		node.setOrderno((short)1);

		return node;
	}
	private static void genTree(Map<String, TreeNode> nodemap) {
		for(String key : nodemap.keySet()){
			TreeNode node = nodemap.get(key);
			if(!"-1".equals(node.getParentid())){
				String a = node.getParentid();
				/*TreeNode node2 =  nodemap.get(node.getParentid());
				if(node2!=null){
					node2.addChild(node);
				}*/
			}
		}
	}
}
