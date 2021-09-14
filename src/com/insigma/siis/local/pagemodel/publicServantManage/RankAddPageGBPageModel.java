package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.insigma.siis.local.business.entity.LogMain;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class RankAddPageGBPageModel extends PageModel {
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
		String a0000=this.getPageElement("a0000").getValue();
		this.getExecuteSG().addExecuteCode("document.getElementById('a0000').value='"+a0000+"';");
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "from A05 where a0000='" + a0000+ "' and a0531='0' order by a0525 desc,a0501b asc";
			List list = sess.createQuery(sql).list();
			A05 a05 = null;
			if(list != null && list.size()>0){
				a05 = (A05) list.get(0);
				a05.setA0000(a0000);
			}

			if (a05 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a05, this);
				// ��ֵ �ж��Ƿ��޸�
				String json = objectToJson(a05);
				// this.getExecuteSG().addExecuteCode("parent.A61value="+json+";");
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainingInfoGrid.dogridquery");// ��Ϣ�б�
		//this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//
	@PageEvent("saveA11.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo() throws RadowException, AppException {
		A05 a05 = new A05();
		this.copyElementsValueToObj(a05, this);
		String a0000=this.getPageElement("a0000").getValue();
		a05.setA0531("0");
		if (a0000 == null || "".equals(a0000)) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','���ȱ�����Ա������Ϣ��',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (a05.getA0501b() == null || "".equals(a05.getA0501b())) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','ְ���β���Ϊ�գ�',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a05.setA0000(a0000);
		String a0500 = a05.getA0500();
		String a0504 = a05.getA0504();// 
		String a0517 = a05.getA0517();//
		String a0524 = a05.getA0524();//��ȡҳ���״ֵ̬
		String a0501b = a05.getA0501b();//ְ����
		a05.setA0525(a0524);//������a0525==a0524
		int start = 0;
		int end = 0;
		if(a0504 != null && !"".equals(a0504)){
			start = Integer.valueOf((a0504+"01").substring(0, 8));
			a05.setA0504(a0504);
		}else{
			a05.setA0504(null);
		}
		if(a0517 != null && !"".equals(a0517)){
			end = Integer.valueOf((a0517+"01").substring(0, 8));
			a05.setA0517(a0517);
		}else{
			a05.setA0517(null);
		}
		
		
		//�ж���׼ʱ�䲻�����ڲμӹ���ʱ��
		String a0134 = this.getPageElement("a0134").getValue();//�μӹ���ʱ��
		if(a0134!=null&&!"".equals(a0134)){
			if (a0134.length() == 6) {
				a0134 += "01";
			}
			
			if(a0134!=null&&!"".equals(a0134)){
				int startWork = Integer.valueOf(a0134);      	//��������
				
				if (start < startWork) {
					this.setMainMessage("��׼���ڲ������ڲμӹ���ʱ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
			}
		}
		
		if(start!=0 && end!=0 && start>end){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��׼ʱ�䲻�ܴ��ڽ���ʱ�䣡',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		A05 a05_old = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if (a0500 == null || "".equals(a0500)) {
				a05_old = new A05();
				if("1".equals(a0524)){//״̬Ϊ����
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"'").list();//����Ƿ�֮ǰ��״̬Ϊ���ε�
					if(list_a05.size()>0){//��⵽֮ǰ��״̬Ϊ���ε�
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��ְ���β����ظ����Σ�',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{//��⵽֮ǰû��״̬Ϊ���ε�
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//��ͬһ�˵�����ְ����״̬��Ϊ0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b);//���ݿ����� ������Ϣ�����ְ���� ��ǰ��ѡ��ְ����
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						
						//���ְ��������
						String a0501bName = "";
						
						if(a0501b!=null){
							a0501bName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE", " code_type='ZB09' and code_value = '"+a0501b+"'");
							
						}
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"','"+a0501bName+"')");//ҳ������ ������Ϣ�����ְ����
						
						
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//ҳ������ ������Ϣ�����ְ����
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//ҳ������ ������Ϣ�����ְ����ʱ��
					}
				}
				/*applog.createLog("", "A05", a01.getA0000(), a01.getA0101(), "������¼",
						new Map2Temp().getLogInfo(a05_old, a05));*/
				
				applog.createLogNew("3A05","��ְ��������","��ְ����",a0000,a01.getA0101(), new Map2Temp().getLogInfo(new A05(),a05));
				
				sess.save(a05);
				sess.flush();
			} else {
				a05_old = (A05) sess.get(A05.class, a0500);
				if("1".equals(a0524)){//״̬Ϊ����ʱ�������жϣ���֮ǰ�����ε�����ʾ
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"' and a0500<> '"+a0500+"'").list();//����Ƿ�֮ǰ��״̬Ϊ���ε�
					if(list_a05.size()>0){//��⵽����ְ����״̬Ϊ���ε�
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','��ְ�������ظ����Σ�',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//��ͬһ�˵�����ְ��״̬��Ϊ0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b); //���ݿ����� ������Ϣ�����ְ���� ��ǰ��ѡ��ְ����
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						
						//���ְ��������
						String a0501bName = "";
						
						if(a0501b!=null){
							a0501bName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE", " code_type='ZB09' and code_value = '"+a0501b+"'");
							
						}
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"','"+a0501bName+"')");//ҳ������ ������Ϣ�����ְ����
						
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//ҳ������ ������Ϣ�����ְ����
						
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//ҳ������ ������Ϣ�����ְ����ʱ��
					}
				}else if("0".equals(a0524)){//״̬Ϊ����ʱ
					if("1".equals(a05_old.getA0524())){//ԭ��������
						a01.setA0221(null);//���� ������Ϣ�����ְ���� Ϊ��
						a01.setA0288(null);
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('','')");//ҳ������ ������Ϣ�����ְ��Ϊ��
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//ҳ������ ������Ϣ�����ְ����ʱ��
					}
				}
				/*applog.createLog("��ְ�����޸�", "A05", a01.getA0000(), a01.getA0101(), "�޸ļ�¼",
						new Map2Temp().getLogInfo(a05_old, a05));*/
				//������־��¼
				a05_old.setA0525(a05.getA0525());
				applog.createLogNew("3A05","��ְ�����޸�","��ְ����",a0000,a01.getA0101(), new Map2Temp().getLogInfo(a05_old,a05));
				PropertyUtils.copyProperties(a05_old, a05);
				sess.update(a05_old);
				
				
				CustomQueryBS.setA01(a0000);
		    	A01 a01F = (A01)sess.get(A01.class, a0000);
				//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
				
				
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ɹ���',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ','����ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		
		
		this.getPageElement("a0500").setValue(a05.getA0500());// ����ɹ���id���ص�ҳ���ϡ�
		// this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//ˢ��רҵ����ְ���б�
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		//this.setNextEventName("TrainingInfoAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a0500) throws RadowException, AppException {
/*		Map map = this.getRequestParamer();
		int index = map.get("eventParameter") == null ? null
				: Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();*/
		String a0000=this.getPageElement("").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			A01 a01 = (A01) sess.get(A01.class, a05.getA0000());
			applog.createLog("3114", "A05", a01.getA0000(), a01.getA0101(), "ɾ����¼",
					new Map2Temp().getLogInfo(new A05(), new A05()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[] { a05.getA0500() });
			
			String a0524 = a05.getA0524();
			if("1".equals(a0524)){
				a01.setA0288(null);
				a01.setA0221(null);//���� ������Ϣ�����ְ���� Ϊ��
				sess.saveOrUpdate(a01);
				sess.flush();
				//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('','')");//ҳ������ ������Ϣ�����ְ��Ϊ��
				//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//ҳ������ ������Ϣ�����ְ����ʱ��
			}
			
			sess.delete(a05);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a05 = new A05();
			PMPropertyCopyUtil.copyObjValueToElement(a05, this);
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
			
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','ɾ��ʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("selectFun.click")
	@Transaction
	public int selectFuns(String index) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0525 = this.getPageElement("a0525").getValue();
		//int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String[] arr = index.split("_");
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", Integer.parseInt(arr[0])).toString();
		String a0000=this.getPageElement("").getValue();
		HBSession sess = null;
		try{
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			String sql = "update a05 set a0525 = '0' where a0000='"+a05.getA0000()+"'";
			sess.createSQLQuery(sql).executeUpdate();
			sess.flush();
			a05.setA0525(a0525);
			sess.saveOrUpdate(a05);
			sess.flush();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if(arr[1] != null && arr[1].equals("0")){
				a01.setA0221(null);
				sess.saveOrUpdate(a01);
				sess.flush();
			}else if(arr[1] != null && arr[1].equals("1")){
				String a0501b = a05.getA0501b();
				a01.setA0221(a0501b);
				sess.saveOrUpdate(a01);
				sess.flush();
			}
			
			String a0501b = a05.getA0501b();
			
			//���ְ��������
			String a0501bName = "";
			
			if(a0501b!=null){
				a0501bName = HBUtil.getValueFromTab("CODE_NAME", "CODE_VALUE", " code_type='ZB09' and code_value = '"+a0501b+"'");
				
			}
			//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"','"+a0501bName+"')");//ҳ������ ������Ϣ�����ְ����
			
			//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a01.getA0221()==null?"":a01.getA0221())+"')");
			return EventRtnType.NORMAL_SUCCESS;
		}catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','���������ʶʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
	}
	/**
	 * ��ʾְ��ְ��grid���
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
		String a0000=this.getPageElement("a0000").getValue();
		String sql = "select * from A05 where a0000='" + a0000 + "' and a0531='0'";
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
		String a0000=this.getPageElement("a0000").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','���ȱ�����Ա������Ϣ��',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A05 a05 = new A05();
		a05.setA0000(a0000);
		PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
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
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();
		String a0000=this.getPageElement("a0000").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('ϵͳ��ʾ','��ѯʧ�ܣ�',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
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
