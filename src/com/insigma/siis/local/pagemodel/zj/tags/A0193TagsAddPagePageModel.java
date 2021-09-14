package com.insigma.siis.local.pagemodel.zj.tags;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.AttrLrzw;
import com.insigma.siis.local.business.entity.extra.ExtraTags;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * ������Ҫְ����Ҫ����
 * 
 * @author zhubo
 *
 */
public class A0193TagsAddPagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<AttrLrzw> list = sess.createQuery("from AttrLrzw where a0000='"+a0000+"'").list();
		AttrLrzw attribute = new AttrLrzw();
		if(list.size()!=0){
			attribute = list.get(0);
		}else{
			attribute.setA0000(a0000);
			sess.saveOrUpdate(attribute);
			sess.flush();
		}
		if(!"1".equals(attribute.getAttr2408())) {
			this.getExecuteSG().addExecuteCode(
					"$('#attr2409').attr('disabled','disabled')");
		}
		
		//this.getPageElement("attr2101").getValue(null);
		PMPropertyCopyUtil.copyObjValueToElement(attribute, this);  //��ֵ��ֵ��ҳ��
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0193zInfo() throws RadowException, AppException {
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}

		try {
			HBSession session = HBUtil.getHBSession();
			StringBuilder a0193z = new StringBuilder();
			//Attribute attribute = new Attribute();
			AttrLrzw attribute = (AttrLrzw) session.createQuery("from AttrLrzw where a0000='"+a0000+"'").uniqueResult();
			
			A01 a01 = (A01) session.get(A01.class, a0000);
			boolean isAdd = true;
			AttrLrzw attribute_old = null;
			if(a01==null){
				this.setMainMessage("���ȱ�����Ա������Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(attribute==null){//������־
				attribute = new AttrLrzw();
				attribute.setA0000(a0000);
			}else{//�޸���־
				isAdd = false;
				attribute_old = attribute.clone();
			}
			this.copyElementsValueToObj(attribute, this);
						
			attribute.setA0000(a0000);
			session.saveOrUpdate(attribute);
			session.flush();
			/*if(isAdd){
				applog.createLogNew(a0000,"�ɲ���������","ATTRIBUTEN",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new Attributen(),attribute));
			}else{
				applog.createLogNew(a0000,"�ɲ������޸�","ATTRIBUTEN",a0000,a01.getA0101(), new Map2Temp().getLogInfo(attribute_old,attribute));
			}*/
			
			
			Map<String, String> attributeMap = Map2Temp.convertBean(attribute);
			for(String k : attrMap.keySet()){
				if(k.toUpperCase().replace("ATTR", "").length()==4){
					if("1".equals(attributeMap.get(k.toUpperCase()))){
						if(k.equals("attr2408")) {
							a0193z.append("��"+attribute.getAttr2409()+"���쵼��ϵ��"+"��");
						}else {
							a0193z.append(attrMap.get(k)+"��");
						}
					}
				}
				
			}
			if(a0193z.length()>0){
				a0193z.deleteCharAt(a0193z.length()-1);
			}
			ExtraTags extratags = (ExtraTags) session.get(ExtraTags.class, a0000);
			if (extratags == null) {
				extratags = new ExtraTags();
				extratags.setA0000(a0000);
			}
			extratags.setA0193z(a0193z.toString());
			this.getExecuteSG().addExecuteCode(
					"window.realParent.document.getElementById('a0193z').value='" + a0193z + "'");
			session.saveOrUpdate(attribute);
			session.saveOrUpdate(extratags);
			session.flush();
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	private String returnA0193z(String a0193, String a0193z) throws AppException {
		String a0193Name = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE",
				"CODE_TYPE='TAGZB131' and CODE_VALUE='" + a0193 + "'");
		a0193z += a0193Name + "��";
		return a0193z;

	}
	
	
	public static Map<String, String> attrMap = new LinkedHashMap<String, String>(){
		{
			put("attr2101", "�����а��ӳ�Ա");
			put("attr2102", "����ֵ�����");
			put("attr2103", "����ֵ�������Ҫ������");
			put("attr2104", "����ί���������Ź�������");
			put("attr2105", "���ɹ���������������");
			put("attr2106", "�����ί��ǻ���������ί���ӳ�Ա");
			put("attr2107", "�ͼ��첿��");
			put("attr2108", "���꼰���ϻ��㹤������");
			put("attr2201", "��Ӫ��ҵ������");
			put("attr2202", "��Ӫ��ҵ�в�");
			put("attr2203", "������ҵ�з�������");
			put("attr2204", "������ҵ�ⷽ����");
			put("attr2205", "������ҵ�в�");
			put("attr2206", "������ҵ������");
			put("attr2207", "������ҵ���Ÿ�����");
			put("attr2208", "�����������������֯");
			put("attr2209", "������֯");
			put("attr2301", "��ְ");
			put("attr2302", "��ְ");
			put("attr2303", "������ְ");
			put("attr2304", "���Ÿ�ְ");
			put("attr2305", "����ѧԺ����֯���");
			put("attr2306", "����ѧԺԺ��");
			put("attr2307", "����ѧԺ��ְ");
			put("attr2308", "У��ί");
			put("attr2401", "����ѧϰ����");
			put("attr2402", "���⹤������");
			put("attr2403", "Ƹ���ƹ���Ա");
			put("attr2404", "����ѡ��");
			put("attr2405", "��ѧ�����");
			put("attr2406", "������תҵ����");
			put("attr2407", "��ʦְ���Ͼ�ת");
			put("attr2408", "�쵼��ϵ��");
			put("attr41", "���簲ȫ����Ϣ");
			put("attr42", "�����ۺ�");
			put("attr43", "��������");
			put("attr44", "����");
			put("attr45", "��֯���º͵���");
			put("attr46", "���ý��ڣ��������أ�");
			put("attr31", "��ҵ��Ӫ����");
			put("attr47", "��ҵ����Ͷ��");
			put("attr48", "�������");
			put("attr49", "Ѳ�Ӻͼͼ���");
			put("attr50", "�滮�͹��̻���");
			put("attr05", "ͳս");
			put("attr06", "����");
			put("attr07", "Ⱥ��");
			put("attr19", "�Ƽ�");
			put("attr51", "����ҽҩ����");
			put("attr52", "����ý�������");
			put("attr13", "�Ļ�����");
			put("attr21", "����");
			put("attr99", "����");
			//put("a0000", "����");

		}
	};
	
	

}
