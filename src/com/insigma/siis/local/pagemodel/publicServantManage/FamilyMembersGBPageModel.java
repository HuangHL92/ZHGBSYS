package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.utils.DBUtils;

public class FamilyMembersGBPageModel extends PageModel {
	private LogUtil applog = new LogUtil();

	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000 = this.getPageElement("a0000").getValue();//String a0000 = this.getRadow_parent_data();
		if (a0000 == null || "".equals(a0000)) {//
			//this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setNextEventName("TrainInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �����޸�
	 */
	/*@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String startdate = this.getPageElement("startdate").getValue();
		String enddate = this.getPageElement("enddate").getValue();
		String entrycontent = this.getPageElement("entrycontent").getValue();
		
		String a1700 = this.getPageElement("a1700").getValue();
		if(a1700==null||"".equals(a1700)){
			this.setMainMessage("����ѡ��һ����ý����Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			Object obj = sess.createSQLQuery("select a1700 from a17 where a1700 = '"+a1700+"'").uniqueResult();
			if(obj!=null){
				//�ж�a1700�Ƿ��Ѿ����ڣ�������ڣ�update����������ڣ�insert��
				sess.createSQLQuery("update a17 set a17.start_date = '"+startdate+"',a17.end_date = '"+enddate+"',"
						+ "a17.entry_content = '"+entrycontent+"' where a17.a1700 = '"+a1700+"'").executeUpdate();
			}else{
				sess.createSQLQuery("insert into a17(a0000,a1700,start_date,end_date,entry_content,is_check) values ('"+a0000+"','"+a1700+"','"+startdate+"','"+enddate+"','"+entrycontent+"','1')").executeUpdate();
			}
			sess.flush();
			this.setMainMessage("����ɹ�!");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("TrainInfoGrid.dogridquery");//ˢ��
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.close();";
	}*/
	/**
	 * �б�
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainInfoGrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select * from ( "+
						"select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,a0111gz,a0115gz,a0111gzb,a3621,a3631,a3641,updated from A36 where A0000 = '"+a0000+"' order by case " + 
								"        when A3604A='�ɷ�' or A3604A='����' then 1 " + 
								"        when A3604A='����' or A3604A='Ů��'or A3604A='��Ů'or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='��Ů' or A3604A='����' or A3604A='����Ů��' or A3604A='������' then 2 " + 
								"        when A3604A='����'  then 3 " + 
								"        when A3604A='ĸ��'  then 4 " + 
								"        when A3604A='�̸�'  then 5 " + 
								"        when A3604A='��ĸ'  then 6 " + 
								"      end   , " + 
								"      to_number(GETAGE(A3607))  desc  "+
						") "+
						"union all "+
						"select * from ( "+
						"select a3600,a3604a,a3601,a3607,a3627,a3611,a0184gz,a0111gz,a0115gz,a0111gzb,a3621,a3631,a3641,updated from A36Z1 where A0000 = '"+a0000+"' order by SORTID "+
						")";
		this.pageQuery(sql,"SQL", start, limit); //�����ҳ��ѯ
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ������ť
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	/*@PageEvent("TrainAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//��ȡҳ����Ա����
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1700 = UUID.randomUUID().toString().replaceAll("-", "");
		this.getPageElement("a1700").setValue(a1700);
		this.getPageElement("startdate").setValue("");
		this.getPageElement("enddate").setValue("");
		this.getPageElement("entrycontent").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	
	/**
	 * 
	 * @return
	 * @throws RadowException
	 */
	/*@PageEvent("TrainInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//��ȡѡ����index
		int index = this.getPageElement("TrainInfoGrid").getCueRowIndex();
		String a1700 = this.getPageElement("TrainInfoGrid").getValue("a1700",index).toString();
		
		HBSession sess = HBUtil.getHBSession();
		Object[] objs = (Object[]) sess.createSQLQuery("select t.start_date,t.end_date,t.entry_content from A17 t where t.a1700 = '"+a1700+"'").uniqueResult();
		if(objs!=null){
			this.getPageElement("startdate").setValue(""+objs[0]);
			this.getPageElement("enddate").setValue(""+objs[1]);
			this.getPageElement("entrycontent").setValue(""+objs[2]);
		}
		this.getPageElement("a1700").setValue(a1700);
		return EventRtnType.NORMAL_SUCCESS;		
	}*/
	
	
	/*@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1700)throws RadowException, AppException{
		Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			sess.createSQLQuery("delete from a17 where a1700 = '"+a1700+"'").executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainInfoGrid.dogridquery')");
			this.setMainMessage("ɾ���ɹ���");
			this.getPageElement("a1700").setValue("");
			this.getPageElement("startdate").setValue("");
			this.getPageElement("enddate").setValue("");
			this.getPageElement("entrycontent").setValue("");
		} catch (Exception e) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}*/
	

}
