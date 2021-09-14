package com.insigma.siis.local.pagemodel.publicServantManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * ������������޸�ҳ��
 * @author Administrator
 *
 */
public class RewardPunishAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("RewardPunishGrid.dogridquery");//��������б�		
		//a01�еĽ������
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//saveA14  ����ɷ���
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveRewardPunish()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		boolean in = false;
		A14 a14 = new A14();
		this.copyElementsValueToObj(a14, this);
		String a1400 = this.getPageElement("a1400").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		String a1404b = this.getPageElement("a1404b").getValue();
		String a1404a = this.getPageElement("a1404a").getValue();
		String a1411a = this.getPageElement("a1411a").getValue();
		String a1415 = this.getPageElement("a1415").getValue();
		String a1414 = this.getPageElement("a1414").getValue();
		String a1428 = this.getPageElement("a1428").getValue();
		String a1407 = this.getPageElement("a1407").getValue();	//��׼����
		String a1424 = this.getPageElement("a1424").getValue(); //��������
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(a01==null){
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if((a1404b != null && !"".equals(a1404b)) || (a1404a != null && !"".equals(a1404a)) || (a1411a != null && !"".equals(a1411a)) 
				|| (a1415 != null && !"".equals(a1415)) || (a1414 != null && !"".equals(a1414)) || (a1428 != null && !"".equals(a1428)) 
				|| (a1407 != null && !"".equals(a1407)) || (a1424 != null && !"".equals(a1424))){		//ֻҪ��һ����ֵ,�����ֵ���ж�
			
			in = true;
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("���ȱ�����Ա������Ϣ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
			
			//�������ƴ��벻����Ϊ��
			
			if(a1404b == null || "".equals(a1404b)){
				this.setMainMessage("�������ƴ��벻��Ϊ�գ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			if(a1404a == null || "".equals(a1404a)){
				this.setMainMessage("�������Ʋ���Ϊ�գ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
		/*	if(a1411a == null || "".equals(a1411a)){
				this.setMainMessage("��׼���ز���Ϊ�գ�");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			if(a1411a.length() > 30){
				this.setMainMessage("��׼���ز��ܳ���30�֣�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
			//������׼���ڣ���μӹ���ʱ��Ƚϣ�Ӧ���ڲμӹ���ʱ�䡣
			String a0134 = a01.getA0134();//�μӹ���ʱ��
			if(a0134!=null&&!"".equals(a0134)&&a1407!=null&&!"".equals(a1407)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a1407.length()==6){
					a1407 += "00";
				}
				if(a1407.compareTo(a0134)<0){
					this.setMainMessage("��׼���ڲ������ڲμӹ���ʱ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//�������ڲ���������׼����
			
			if(a1424!=null&&!"".equals(a1424)&&a1407!=null&&!"".equals(a1407)){
				if(a1424.length()==6){
					a1424 += "00";
				}
				if(a1407.length()==6){
					a1407 += "00";
				}
				if(a1424.compareTo(a1407)<0){
					this.setMainMessage("�������ڲ���������׼����");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
			//��������a1404a
			if(a1404a.length() > 20){
				this.setMainMessage("�������Ʋ��ܳ���20�֣�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//��׼���ڲ���Ϊ��
			if(a1407 == null || "".equals(a1407)){
				this.setMainMessage("��׼���ڲ���Ϊ�գ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a14.setA0000(a0000);
			
		}
		try {
			
			if(in){
				if(a1400==null||"".equals(a1400)){
					
					applog.createLog("3141", "A14", a01.getA0000(), a01.getA0101(), "������¼",  new Map2Temp().getLogInfo(new A14(), a14));
					
					sess.save(a14);	
				}else{
					
					A14 a14_old = (A14)sess.get(A14.class, a1400);
					applog.createLog("3142", "A14", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a14_old, a14));
					PropertyUtils.copyProperties(a14_old, a14);
					sess.update(a14_old);	
				}
			}
			//���½�������
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a14z101 = "��";
				this.getPageElement("a14z101").setValue(a14z101);
			}
			a01.setA14z101(a14z101);
			//��Ա������Ϣ����
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a14z101').setValue('"+a01.getA14z101()+"')");
			//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			sess.update(a01);
			sess.flush();
			
			CustomQueryBS.setA01(a0000);
	    	A01 a01F = (A01)sess.get(A01.class, a0000);
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

			
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a14.getA1400());//����ɹ���id���ص�ҳ���ϡ�
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('RewardPunishGrid').getStore().reload()");//ˢ�½�������б�
		this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************�������(a14)*********************************************************************/
	
	/**
	 * ��������б�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.dogridquery")
	@NoRequiredValidate
	public int rewardPunishGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a.* from A14 a where a0000='"+a0000+"' order by a1407 desc,SUBSTR(A1404B,0,2)";
		this.pageQuery(sql,"SQL", start, limit); //�����ҳ��ѯ
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * �������������ť
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishAddBtn.onclick")
	@NoRequiredValidate
	public int rewardPunishWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A14 a14 = new A14();
		PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����������޸��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int rewardPunishGridOnRowClick() throws RadowException{ 
		//��ȡѡ����index
		int index = this.getPageElement("RewardPunishGrid").getCueRowIndex();
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * �����������׷��
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("appendonclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int appendonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		Map map = this.getRequestParamer();
		int index = Integer.valueOf(String.valueOf(map.get("eventParameter")));//��ǰ�к�
		String a14z101 = this.getPageElement("a14z101").getValue();//��������
		//�������ƴ���
		String a1404b = this.getPageElement("RewardPunishGrid").getValue("a1404b",index).toString();
		//��������
		String a1404a = this.getPageElement("RewardPunishGrid").getValue("a1404a",index).toString();
		//��׼����
		String a1411a = this.getPageElement("RewardPunishGrid").getValue("a1411a",index).toString();
		//��׼ʱ��
		String a1407 = this.getPageElement("RewardPunishGrid").getValue("a1407",index).toString();
		
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"��";
		}else{
			a1407 = "";
		}
		
		boolean haschr = false;
		if("��".equals(a14z101)){
			a14z101 = "";
		}
		if(a14z101.length()>0){
			String laststr = a14z101.substring(a14z101.length()-1);
			if(laststr.matches(",|.|;|��|��|��")){
				a14z101 = a14z101.substring(0,a14z101.length()-1);				
			}
			haschr = true;
		}
		
		StringBuffer desc = new StringBuffer(a14z101);
		if(haschr){
			desc.append("��");
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		
		if(a1404b.startsWith("01") || a1404b.startsWith("1")){//��
			
			if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
				desc.append("��"+a1411a+"��׼��").append("�ٻ�"+a1404a+"��");
			}else{
				desc.append("��"+a1411a+"��׼��").append(a1404a+"��");
			}
			
		}else{//��
			desc.append("��"+a1411a+"��׼��").append("��"+a1404a+"���֡�");
		}
		this.getPageElement("a14z101").setValue(desc.toString());
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(desc.toString());
		sess.update(a01);
		//��Ա������Ϣ����
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a14z101').setValue('"+a01.getA14z101()+"')");
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * �����������ȫ���滻
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addAll.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int addAllonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		List<HashMap<String, Object>> list = this.getPageElement("RewardPunishGrid").getValueList();
		if(list!=null&&list.size()>0){
			StringBuffer desc = new StringBuffer("");
			for(HashMap<String, Object> map : list){
				//�������ƴ���
				String a1404b = map.get("a1404b").toString();
				//��������
				String a1404a = map.get("a1404a").toString();
				//��׼����
				String a1411a = map.get("a1411a").toString();
				
				//��׼ʱ��
				String a1407 = map.get("a1407").toString();
				
				if(a1407!=null&&a1407.length()>=6){
					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"��";
				}else{
					a1407 = "";
				}
				
				if(a1404b.startsWith("01")){//��
					//desc.append("��"+a1411a+"��׼��").append(a1404a+"��");
					
					desc.insert(0, a1404a+"��").insert(0, "��"+a1411a+"��׼��");
				}else{//��
					
					//desc.append("��"+a1411a+"��׼��").append("��"+a1404a+"���֡�");
					
					desc.insert(0, "��"+a1404a+"���֡�").insert(0, "��"+a1411a+"��׼��");
				}
				
				if(!"".equals(a1407)){
					//desc.append(a1407);
					
					desc.insert(0, a1407);
				}
				
				//�б�˳������׼ʱ�䵹�����ɵý������������������Ҫ����׼ʱ������
				if(desc.toString().endsWith("��")){
					//desc.deleteCharAt(desc.length()-1).append("��");
					
					desc.deleteCharAt(desc.length()-1).append("��");
				}
				
				
				
			}
			
			this.getPageElement("a14z101").setValue(desc.toString());
			a01.setA14z101(desc.toString());
			sess.update(a01);
			//��Ա������Ϣ����
			
			//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		}else{
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a01.setA14z101("��");
				this.getPageElement("a14z101").setValue(a01.getA14z101());
				sess.update(a01);
				//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1400)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			A01 a01 = (A01)sess.get(A01.class, a14.getA0000());
			A14 a14_new = a14;
			change_visiable(a14_new);
			applog.createLog("3143", "A14", a01.getA0000(), a01.getA0101(), "ɾ����¼", new Map2Temp().getLogInfo(new A14(), new A14()));
			sess.delete(a14);
			this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
			a14 = new A14();
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);	
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
		} catch (Exception e) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void change_visiable(A14 a14) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01= (A01)session.get(A01.class, a0000);
		String description = this.getPageElement("a14z101").getValue();//ҳ����������
		StringBuffer desc = new StringBuffer("");
		String a1407 = a14.getA1407();
		String a1404b = a14.getA1404b();//�������ƴ���	
		String a1404a = a14.getA1404a();//��������
		String a1411a = a14.getA1411a();//��׼����
		if(a1411a==null){
			a1411a="";
		}
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"��";
		}else{
			a1407 = "";
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		if(a1404b.startsWith("01")){//��
			desc.append("��"+a1411a+"��׼��").append(a1404a+"��");
		}else{//��6
			desc.append("��"+a1411a+"��׼��").append("��"+a1404a+"���֡�");
		}
		System.out.println(description);
		System.out.println(desc);
		description = description.replaceAll(desc.toString().trim(), "");
		if(desc.toString().endsWith("��")){
			desc.deleteCharAt(desc.length()-1).append("��");
		}
		description= description.replaceAll(desc.toString(), "");
		if("".equals(description.trim())){
			description = "��";
		}
		//���ͽ�������
		this.getPageElement("a14z101").setValue(description);
		
		//�޸�a01��
		a01.setA14z101(description);
		session.update(a01);
		//��Ա������Ϣ����
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		
	}
	
	
	/**
	 * �����������ͬ���������������A01
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("synchro.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int synchroClick() throws RadowException{
		
		String a0000 = this.getPageElement("a0000").getValue();
		String a14z101 = this.getPageElement("a14z101").getValue();
		
		if(a14z101 == null || a14z101.equals("")){
			a14z101 = "��";
		}
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(a14z101);
		sess.update(a01);
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
