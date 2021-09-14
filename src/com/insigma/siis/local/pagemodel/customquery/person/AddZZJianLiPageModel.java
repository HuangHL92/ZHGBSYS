package com.insigma.siis.local.pagemodel.customquery.person;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.publicServantManage.AddJianLiAddPageBS;

import net.sf.json.JSONObject;


public class AddZZJianLiPageModel extends PageModel{

	private AddJianLiAddPageBS bs1 = new AddJianLiAddPageBS();
	
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		HBSession sess=HBUtil.getHBSession();
		String param = this.getPageElement("subWinIdBussessId").getValue();
		String a1700 = "";
		String a0000 = "";
		String a1799 = "";
		try {
			JSONObject paramJson = JSONObject.fromObject(param);
			a1700 = paramJson.getString("a1700");
			a0000 = paramJson.getString("a0000");
			a1799 = paramJson.getString("a1799");
		} catch (Exception e) {
			this.setMainMessage("������������쳣��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("a1700").setValue(a1700);
		this.getPageElement("a0000").setValue(a0000);
		this.getPageElement("a1799").setValue(a1799);
		A17 a17 = (A17) sess.get(A17.class, a1700);
		
		if(a17!=null) {
			String a1701Str=pjA17(a17)==null?"":pjA17(a17);
			this.copyObjValueToElement(a17,this);
			this.getExecuteSG().addExecuteCode("toA1701('"+a1701Str+"');");
		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1705=a17.getA1705();
		if(a1705!=null && !"".equals(a1705)) {
			@SuppressWarnings("unchecked")
			List<String> a1705name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1705+"'  and code_type='JL02'").list();
			if(a1705name.size()>0 && a1705name.get(0)!=null) {
				this.getPageElement("a1705_combotree").setValue(a1705name.get(0));
			}
		}
		this.setNextEventName("grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String pjA17(A17 a17) {
		if(a17!=null) {
			StringBuffer a1701z= new StringBuffer();
			a1701z.append(DateUtil.dataStrFormart(a17.getA1701(), ".", "", "") + "--");
			a1701z.append(DateUtil.dataStrFormart(a17.getA1702(), ".", "", "") + "  ");
			a1701z.append(a17.getComplete()==null?"":a17.getComplete());
			String a1701Str = a1701z.toString();
			a1701Str = a1701Str.replace("--  ", "--         ");
			return a1701Str;
		}else {
			return null;
		}
	}
	/**
	*====================================================================================================
	* ��������:�����걨�б�<br>
	* ������������:2019��07��19��<br>
	* ����������Ա:zhangxw<br>
	* ��������޸�����:2019��07��19��<br>
	* ��������޸���Ա:zhangxw<br>
	* ������������:���� �������в�ѯ,�������걨�б� - ��ҳչʾ<br>
	* ��Ʋο��ĵ�:XXXX--XXX--XXXX��ϸ���<br>
	* ����ṹ����:
	*====================================================================================================
	 * @throws RadowException
	*/
	@PageEvent("grid.dogridquery")
	@NoRequiredValidate
	public int doGridQuery(int start,int limit) throws RadowException {
		String a1700 = this.getPageElement("a1700").getValue();
		String sql="select a0000,a1700,a1701,a1702,a1703,a1704,a1705,a1706,a1707,a1708,case when " + DBUtil.getColumnIsNull("a1701") + " and " + DBUtil.getColumnIsNull("a1702") + " then '1' else '0' end as a1798,a1799,a0221,a0192e,complete " + "from hz_a17 where belong_to_a1700 = '" + a1700 + "' and a1709 in ('3','4') order by to_number(a1799)";
		this.pageQuery(sql, "SQL", 0, 50);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid.rowdbclick")
	@GridDataRange
	public int OnRowDbClick() throws RadowException, AppException { // �򿪴��ڵ�ʵ��
		String a1700parent=this.getPageElement("a1700").getValue();
		String a1700 = this.getPageElement("grid").getValue("a1700", this.getPageElement("grid").getCueRowIndex()).toString();
		String a0000 = this.getPageElement("a0000").getValue();
		if (a1700parent != null) {
			if(a1700==null || "".equals(a1700)) {
				a1700=UUID.randomUUID().toString().replaceAll("-", "");
			}

			this.request.getSession().setAttribute("personIdSet", null);

			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode(" $h.openWin('AddQJJianLi', 'pages.customquery.person.AddQJJianLi', '��������Ϣά��', 720, 350, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close',a1700:'"+a1700+"' ,a1700parent:'"+a1700parent+"' ,a0000:'"+a0000+"'})" );
			//this.getExecuteSG().addExecuteCode("$h.openWin('cjqkupdate','pages.gbjd.cjqkupdate','�ͽ����',750,405,ctxPath,null,{maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"'})");

			// initA0000Map(a0000);
			// this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			// this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			throw new AppException("��ѡ��һ����¼��");

		}
	}
	
	/**
	*====================================================================================================
	* ��������:composeall �������<br>
	* ������������:2019��07��19��<br>
	* ����������Ա:zhangxw<br>
	* ��������޸�����:2019��07��19��<br>
	* ��������޸���Ա:zhangxw<br>
	* ������������:�����������б�������ϲ����浽��Ա������Ϣ��<br>
	*====================================================================================================
	 * @throws RadowException 
	 * @throws AppException 
	*/
	@PageEvent("compose")
	@Transaction
	@NoRequiredValidate
	public int composeall() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		HBSession session = HBUtil.getHBSession();
		List<HashMap<String,Object>> list = this.getPageElement("grid").getValueList();
		
		String a1700 = this.getPageElement("a1700").getValue();
		A17 a17 = (A17) session.get(A17.class, a1700);
		String a1701= this.getPageElement("a1701").getValue();
		String a1702= this.getPageElement("a1702").getValue();
		String a1703= this.getPageElement("a1703").getValue();
		String a1704= this.getPageElement("a1704").getValue();
		String a1705= this.getPageElement("a1705").getValue();
		String a1706= this.getPageElement("a1706").getValue();
		String a1707= this.getPageElement("a1707").getValue();
		String a1708= this.getPageElement("a1708").getValue();
		String a0000= this.getPageElement("a0000").getValue();
		String a1799= this.getPageElement("a1799").getValue();
		if(a17==null) {
			a17=new A17();
			a17.setA1700(a1700);
			a17.setA0000(a0000);
		}
		a17.setA1701(a1701);
		a17.setA1702(a1702 == null ? "" : a1702 + "");
		a17.setA1703(a1703 == null ? "" : a1703 + "");
		a17.setA1704(a1704 == null ? "" : a1704 + "");
		a17.setA1705(a1705 == null ? "" : a1705 + "");
		a17.setA1706(a1706 == null ? "" : a1706 + "");
		a17.setA1707(a1707 == null ? "" : a1707 + "");
		a17.setA1708(a1708 == null ? "" : a1708 + "");
		a17.setA1799(a1799);
		a17.setA1709("2");
		a17.setUserid(userid);
		a17.setBelongToA1700(a1700);
		String complete=bs1.saveZZA17(list,a0000,a17);
		a17.setComplete(complete);
		session.saveOrUpdate(a17);
		session.flush();
		bs1.pjA1701(a0000);
		String a1701Str=pjA17(a17);
		if(a1701Str==null){
			a1701Str = "";
		}
		this.getExecuteSG().addExecuteCode("toA1701('"+a1701Str+"');");
		this.setMainMessage("����ɹ����������Ѱ���ʼʱ���Զ�����");
		this.setNextEventName("grid.dogridquery");
		this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('initX');realParent.changeToShow();");
		//this.getExecuteSG().addExecuteCode("realParent.updateA1701Content('"+a1701+"');self.close();realParent.is_changeToTrue();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deleteRow")
	@NoRequiredValidate
	@Transaction
	public int deleteRow(String a1700) {
		HBSession session = HBUtil.getHBSession();
		if(!"".equals(a1700) && a1700!=null) {
			String delSql = "delete from hz_a17 where a1700 = '"+a1700+"'";
			session.createSQLQuery(delSql).executeUpdate();
			session.flush();
			this.setNextEventName("grid.dogridquery");
		}else {
			this.setMainMessage("ɾ��ʧ�ܣ�");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("changeA1704name")
	@NoRequiredValidate
	@Transaction
	public int changeA1704name()throws RadowException {
		HBSession session = HBUtil.getHBSession();
		String a1704=this.getPageElement("a1704").getValue();
		@SuppressWarnings("unchecked")
		List<String> a1704name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1704+"'  and code_type='JL02'").list();
		if(a1704name.size()>0 && a1704name.get(0)!=null) {
			this.getPageElement("a1704_combo").setValue(a1704name.get(0));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
