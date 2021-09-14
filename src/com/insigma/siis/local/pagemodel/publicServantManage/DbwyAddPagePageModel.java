package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

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
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.Association;
import com.insigma.siis.local.business.entity.DBWY;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class DbwyAddPagePageModel extends PageModel{
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public  int  initX() throws RadowException{
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		this.getExecuteSG().addExecuteCode("document.getElementById('a0000').value='"+a0000+"';");
		if (a0000 == null || "".equals(a0000)) {//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//��Ա�����������
		if(DBUtils.isAudit(a0000)){
			this.getExecuteSG().addExecuteCode("lockINFO()");
		}
		
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from DBWY where a0000='" + a0000 +"'  ";
			List list = sess.createQuery(sql).list();
			DBWY dbwy= null;
			if(list != null && list.size()>0){
				dbwy = (DBWY) list.get(0);
				dbwy.setA0000(a0000);
			}

			if (dbwy != null) {
				PMPropertyCopyUtil.copyObjValueToElement(dbwy, this);
				// ��ֵ �ж��Ƿ��޸�
				String json = objectToJson(dbwy);
				// this.getExecuteSG().addExecuteCode("parent.A61value="+json+";");
			}
			updateA01(a0000);

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainingInfoGrid.dogridquery");// ��Ϣ�б�
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//���ݱ���
	@PageEvent("saveDbwy.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo() throws RadowException, AppException {
		DBWY dbwy =new DBWY();
		this.copyElementsValueToObj(dbwy, this);
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','���ȱ�����Ա������Ϣ��',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (dbwy.getRank() == null || "".equals(dbwy.getRank())) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','������Ϊ�գ�',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		dbwy.setA0000(a0000);
		String rzsj=dbwy.getRzsj();
		String mzsj=dbwy.getMzsj();
		String id=dbwy.getId();
//		String a0500 = a05.getA0500();
//		String a0504 = a05.getA0504();// 
//		String a0517 = a05.getA0517();//
//		String a0524 = a05.getA0524();//��ȡҳ���״ֵ̬
//		String a0501b = a05.getA0501b();//ְ���
		int start = 0;
		int end = 0;
		if(rzsj != null && !"".equals(rzsj)){
			start = Integer.valueOf((rzsj+"01").substring(0, 8));
			dbwy.setRzsj(rzsj);
		}else{
			dbwy.setRzsj(null);
		}
		if(mzsj != null && !"".equals(mzsj)){
			end = Integer.valueOf((mzsj+"01").substring(0, 8));
			dbwy.setMzsj(mzsj);
		}else{
			dbwy.setMzsj(null);
		}
		//�Ƚ���ֹʱ�����׼ʱ��
		if(mzsj!=null && !"".equals(mzsj)){
			if(mzsj.compareTo(rzsj)<0 ) {
				this.setMainMessage("��ְʱ�䲻��������ְʱ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
			
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if(id==null ||"".equals(id)) {
				sess.save(dbwy);
			}else {
				DBWY dbwy_old=(DBWY)sess.get(DBWY.class, id);
				PropertyUtils.copyProperties(dbwy_old, dbwy);
				
				sess.update(dbwy_old);
			}	
			sess.flush();
			updateA01(dbwy.getA0000());
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ɹ���',null,'220')");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		
		
		this.getPageElement("id").setValue(dbwy.getId());// ����ɹ���id���ص�ҳ���ϡ�
		// this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//ˢ�������б�
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String id) throws RadowException, AppException {
/*		Map map = this.getRequestParamer();
		int index = map.get("eventParameter") == null ? null
				: Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();*/
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		System.out.println(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			DBWY dbwy=(DBWY)sess.get(DBWY.class, id);
			dbwy.setA0000(a0000);
			A01 a01 = (A01) sess.get(A01.class, dbwy.getA0000());
			
			//applog.createLogNew("3A05","��ְ����ɾ��","��ְ����",a0000,a01.getA0101(), new ArrayList<Object[]>());
			
			//��¼ɾ���ĸ�

			sess.delete(dbwy);
			sess.flush();	
			updateA01(dbwy.getA0000());
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			dbwy = new DBWY();
			PMPropertyCopyUtil.copyObjValueToElement(dbwy, this);
			
			//CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','ɾ��ʧ�ܣ�',null,'220')");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����A01�е�STJZ�ֶ�
	private void updateA01(String a0000 ) throws AppException{
		//��ǰҳ�渳ֵ
		@SuppressWarnings("unchecked")
		List<String> list1 = HBUtil.getHBSession().createSQLQuery("select rank from DBWY where a0000='"+a0000+"' and mzsj is null "
		+" order by dbwy "		).list();
		
		@SuppressWarnings("unchecked")
		List<String> list2 = HBUtil.getHBSession().createSQLQuery("select dbwy from DBWY where a0000='"+a0000+"' and mzsj is null "
		+" order by dbwy "		).list();
		StringBuffer dbwystr = new StringBuffer();
		for(int i=0;i<list1.size();i++) {
			if("1".equals(list1.get(i))) {
				dbwystr.append("����");
			}else if("2".equals(list1.get(i))) {
				dbwystr.append("ʡ");
			}else if("3".equals(list1.get(i))) {
				dbwystr.append("��");
			}else if("4".equals(list1.get(i))) {
				dbwystr.append("������");
			}
			if("1".equals(list2.get(i))) {
				dbwystr.append("��ίίԱ");
			}else if("2".equals(list2.get(i))) {
				dbwystr.append("�˴�ί");
			}else if("3".equals(list2.get(i))) {
				dbwystr.append("�˴����");
			}else if("4".equals(list2.get(i))) {
				dbwystr.append("��ЭίԱ");
			}else if("5".equals(list2.get(i))) {
				dbwystr.append("������");
			}else if("6".equals(list2.get(i))) {
				dbwystr.append("��ίίԱ");
			}
			dbwystr.append("��");
		}
		if(dbwystr.length()>0){
			dbwystr.deleteCharAt(dbwystr.length()-1);
		}
		
	
		
		//����A10 a0196 רҵ����ְ���ֶΡ�   a06 a0602
		HBUtil.executeUpdate("update a01 set DBWY='"+dbwystr.toString()+"' where a0000='"+a0000+"'");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('Dbwy_p').innerHTML='"+(dbwystr.toString()==null?"":dbwystr.toString())+"';");
	}
	
	/**
	 * ��ʾ����ίԱ��ְgrid���
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int trainingInforGridQuery(int start, int limit) throws RadowException {
		// String a0000 = this.getPageElement("a0000").getValue();
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from DBWY where a0000='" + a0000 + "' order by dbwy";
		this.pageQuery(sql, "SQL", start, limit); // �����ҳ��ѯ
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	 * 
	 * ���������ť�¼�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id) throws RadowException {
		// String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','���ȱ�����Ա������Ϣ��',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		DBWY dbwy=new DBWY();
		dbwy.setA0000(a0000);
		PMPropertyCopyUtil.copyObjValueToElement(dbwy, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �޸��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int trainingInforGridOnRowClick() throws RadowException {
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String id = this.getPageElement("TrainingInfoGrid").getValue("id", index).toString();
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			DBWY dbwy=(DBWY)sess.get(DBWY.class,id);
			dbwy.setA0000(a0000);
			PMPropertyCopyUtil.copyObjValueToElement(dbwy, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','��ѯʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("a0501bChange();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��ʵ��POJOת��ΪJSON
	 * 
	 * @param obj
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	public static <T> String objectToJson(T obj) throws JSONException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		// Convert object to JSON string
		String jsonStr = "{}";
		try {
			jsonStr = mapper.writeValueAsString(obj);
		} catch (IOException e) {
			throw e;
		}
		return jsonStr;
	}
	
}
