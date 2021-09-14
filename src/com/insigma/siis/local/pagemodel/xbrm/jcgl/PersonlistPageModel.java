package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.entity.Checkregperson;

public class PersonlistPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			Checkreg cr = (Checkreg) HBUtil.getHBSession().get(Checkreg.class, checkregid);
			if(cr.getRegstatus()!=null && !cr.getRegstatus().equals("0")) {
				this.getExecuteSG().addExecuteCode("Ext.getCmp('doAddPerson').setDisabled(true);"
						+ "Ext.getCmp('infoDelete').setDisabled(true);"
						+ "Ext.getCmp('editAddPerson').setDisabled(true);"
						+ "Ext.getCmp('editSavePerson').setDisabled(true);"
						+ "Ext.getCmp('editAddfamily').setDisabled(true);"
						+ "Ext.getCmp('deletefamily').setDisabled(true);");
			}
			List list = HBUtil.getHBSession().createSQLQuery("select ckfileid from checkregfile where checkregid='"+checkregid+"' and filetype='bsgsdr'").list();
			if(list.size()>0) {
				this.getPageElement("ckfileid").setValue(list.get(0)+"");
				this.getExecuteSG().addExecuteCode("Ext.getCmp('downf').setVisible(true);");
			} else {
				this.getExecuteSG().addExecuteCode("Ext.getCmp('downf').setVisible(false);");
			}
			this.setNextEventName("gridcq.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridcq.dogridquery")
	@NoRequiredValidate
	public int gridcqQuery(int start,int limit) throws RadowException{
		String checkregid = this.getPageElement("checkregid").getValue();
		String crp001_ = this.getPageElement("crp001_").getValue();
		String crp006_ = this.getPageElement("crp006_").getValue();
		String wsql = "";
		if(crp001_!=null && !crp001_.trim().equals("")) {
			wsql += " and crp001 like '%"+crp001_+"%' ";
		}
		if(crp006_!=null && !crp006_.trim().equals("")) {
			wsql += " and crp006 like '%"+crp006_+"%' ";
		}
		
		String sql="select * from CHECKREGPERSON t where t.checkregid='"+checkregid+
				"' "+wsql+" and CRP007='1' order by SORTID1 ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 删除信息
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("infoDelete")
	@NoRequiredValidate
	public int infoDelete() throws RadowException{
		//列表 
		StringBuffer crp008 = new StringBuffer();
		List<HashMap<String,Object>> list = this.getPageElement("gridcq").getValueList();
		int countNum = 0;
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("pcheck");
			if (check1!= null && check1.equals(true)) {
				crp008.append(map.get("crp008")==null?"":map.get("crp008").toString()).append(",");//被勾选的人员编号组装，用“，”分隔
				countNum++;
			}
		}
		if(countNum==0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
			
		if(crp008==null || crp008.toString().trim().equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*this.setMainMessage("你确定要删除所选数据吗？");
		this.setMessageType(EventMessageType.CONFIRM);
		this.addNextEvent(NextEventValue.YES, "deletePerson1", a0000.toString());*/
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示：','您确定要删除所选数据吗？',240,function(id) { if('ok'==id){radow.doEvent('deletePerson1','"+crp008.toString()+"');}else{return false;}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除信息
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deletePerson1")
	@NoRequiredValidate
	public int deletePerson1(String crp008) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.createSQLQuery("delete from CHECKREGPERSON where crp008 in ('"+
					(crp008.substring(0, crp008.length()-1)).replace(",", "','")+"')").executeUpdate();
			this.setMainMessage("删除成功！");
			this.getExecuteSG().addExecuteCode("radow.doEvent('gridcq.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据删除异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择显示人员
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rowPclick")
	@NoRequiredValidate
	public int rowPclick(String crp000) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			if(crp000 != null && !crp000.equals("")) {
				Checkregperson crp = (Checkregperson) sess.get(Checkregperson.class, crp000);
				this.copyObjValueToElement(crp, this);
			} else {
				Checkregperson crp = new Checkregperson();
				this.copyObjValueToElement(crp, this);
				this.getPageElement("crp000").setValue("");
				this.getPageElement("checkregid").setValue(checkregid);
			}
			this.setNextEventName("familyGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据信息异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 家庭成员信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("familyGrid.dogridquery")
	@NoRequiredValidate
	public int fgridcqQuery(int start,int limit) throws RadowException{
		String checkregid = this.getPageElement("checkregid").getValue();
		String crp008 = this.getPageElement("crp008").getValue();
		String sql="select * from CHECKREGPERSON t where t.checkregid='"+checkregid+
				"' and CRP007='2' and crp008='"+crp008+"' order by SORTID2 ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 保存信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */ 
	@PageEvent("saveInfo")
	public int saveInfo() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String checkregid = this.getPageElement("checkregid").getValue();
		//List<Checkregperson> insertlist = new ArrayList<Checkregperson>();
		HBTransaction tr = null;
		try {
			tr = sess.beginTransaction();
			Checkregperson crp = new Checkregperson();
			this.copyElementsValueToObj(crp, this);
			String zzmm = this.getPageElement("crp005_combo").getValue();
			crp.setCrp005(zzmm);
			if(crp.getCrp000()!=null && !crp.getCrp000().equals("")) {
				String crp008 = this.getPageElement("crp008").getValue();
				List<HashMap<String,Object>> list = this.getPageElement("familyGrid").getValueList();
				String ids = crp.getCrp000() + ",";
				if(list.size()>0) {
					for (int j = 0; j < list.size();j++) {
						Checkregperson crp2 = new Checkregperson();
						HashMap<String, Object> map = list.get(j);
						String crp000 = map.get("crp000")!=null? map.get("crp000").toString():"";
						String crp001 = map.get("crp001")!=null? map.get("crp001").toString():"";
						String crp002 = map.get("crp002")!=null? map.get("crp002").toString():"";
						String crp006 = map.get("crp006")!=null? map.get("crp006").toString():"";
						if(crp001.trim().equals("") || crp001.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写姓名信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if(crp006.trim().equals("") || crp006.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写身份证信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if(crp001.trim().equals("") || crp001.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写称谓证信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						crp2.setCheckregid(checkregid);
						crp2.setCrp001(crp001);
						crp2.setCrp002(crp002);
						crp2.setCrp003(map.get("crp003")!=null? map.get("crp003").toString():"");
						crp2.setCrp004(map.get("crp004")!=null? map.get("crp004").toString():"");
						crp2.setCrp005(map.get("crp005")!=null? map.get("crp005").toString():"");
						crp2.setCrp006(crp006);
						crp2.setCrp007("2");
						crp2.setCrp008(crp008);
						crp2.setCrp018(crp.getCrp018());
						crp2.setCrp009(map.get("crp009")!=null? map.get("crp009").toString():"");
						crp2.setSortid1(crp.getSortid1());
						crp2.setSortid2(j+1L);
						crp2.setCrp010(map.get("crp010")!=null? map.get("crp010").toString():"");
						if(crp000.trim().equals("") || crp000.trim().equals("null")) {
							sess.save(crp2);
						} else {
							crp2.setCrp000(map.get("crp000")!=null? map.get("crp000").toString():"");
							crp2.setA0000(map.get("a0000")!=null? map.get("a0000").toString():"");
							crp2.setA3600(map.get("a3600")!=null? map.get("a3600").toString():"");
							sess.update(crp2);
						}
						ids = ids + crp2.getCrp000() + ",";
					}
				}
				sess.update(crp);
				ids = ids.substring(0, ids.length()-1).replace(",", "','");
				sess.createQuery("delete from Checkregperson where crp008='"+crp008+"' and crp000 not in ('"+ids+"')").executeUpdate();
			} else {
				BigDecimal obj = (BigDecimal) sess.createSQLQuery("select max(sortid1) from Checkregperson where checkregid='" 
						+ checkregid+"'").uniqueResult();
				long sortid1 = obj!=null ? obj.intValue() + 1L:1l;
				String crp008 = UUID.randomUUID().toString();
				crp.setCrp008(crp008);
				crp.setCrp002("报告人");
				crp.setCrp007("1");
				crp.setSortid2(0L);
				crp.setSortid1(sortid1);
				List<HashMap<String,Object>> list = this.getPageElement("familyGrid").getValueList();
				if(list.size()>0) {
					for (int j = 0; j < list.size();j++) {
						Checkregperson crp2 = new Checkregperson();
						HashMap<String, Object> map = list.get(j);
						String crp001 = map.get("crp001")!=null? map.get("crp001").toString():"";
						String crp002 = map.get("crp002")!=null? map.get("crp002").toString():"";
						String crp006 = map.get("crp006")!=null? map.get("crp006").toString():"";
						if(crp001.trim().equals("") || crp001.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写姓名信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if(crp006.trim().equals("") || crp006.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写身份证信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						if(crp001.trim().equals("") || crp001.trim().equals("null")) {
							this.setMainMessage("家庭成员中需要填写称谓证信息！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						crp2.setCheckregid(checkregid);
						crp2.setCrp001(crp001);
						crp2.setCrp002(crp002);
						crp2.setCrp003(map.get("crp003")!=null? map.get("crp003").toString():"");
						crp2.setCrp004(map.get("crp004")!=null? map.get("crp004").toString():"");
						crp2.setCrp005(map.get("crp005")!=null? map.get("crp005").toString():"");
						crp2.setCrp006(crp006);
						crp2.setCrp007("2");
						crp2.setCrp008(crp008);
						crp2.setCrp018(crp.getCrp018());
						crp2.setCrp009(map.get("crp009")!=null? map.get("crp009").toString():"");
						crp2.setSortid1(sortid1);
						crp2.setSortid2(j+1L);
						//insertlist.add(crp2);
						sess.save(crp2);
					}
				}
				sess.save(crp);
			}
			sess.flush();
			tr.commit();
			this.getPageElement("crp000").setValue(crp.getCrp000());
			this.setMainMessage("保存成功！");
			this.setNextEventName("gridcq.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tr.rollback();
			} catch (AppException e1) {
				e1.printStackTrace();
			}
			this.setMainMessage("保存异常！");
			this.setMessageType(EventMessageType.ERROR);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
