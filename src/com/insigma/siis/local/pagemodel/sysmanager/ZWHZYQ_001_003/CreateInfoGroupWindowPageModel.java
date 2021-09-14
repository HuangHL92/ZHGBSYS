package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtAcl;
import com.insigma.odin.framework.privilege.entity.SmtAct;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PmHListUtil;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class CreateInfoGroupWindowPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String infogroupid = this.getRadow_parent_data();
		if(!"1".equals(infogroupid)){
			HBSession sess = HBUtil.getHBSession();
			String hql = "FROM InfoGroup t WHERE t.infogroupid = '"+infogroupid+"'";
			List<InfoGroup> list = sess.createQuery(hql).list();
			String infogroupname = list.get(0).getInfogroupname();
			this.getPageElement("infogroupname").setValue(infogroupname);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("infogroupname.onchange")
	@NoRequiredValidate
	public int InfoGroupNameOnChonge() throws RadowException{
		String infogroupname = this.getPageElement("infogroupname").getValue();
		HBSession sess = HBUtil.getHBSession();
		String hql = "FROM InfoGroup t WHERE t.infogroupname = '"+infogroupname+"'";
		List<InfoGroup> list = sess.createQuery(hql).list();
		if(list.size()==0){
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setMainMessage("该名称已经存在");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("savebut.onclick")
	@NoRequiredValidate
	public int savebutOnclick() throws RadowException{
		String infogroupname = this.getPageElement("infogroupname").getValue();
		if(infogroupname.equals("")) {
			this.setMainMessage("请输入信息项组名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
			this.saveUser("yes");
			return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("saveUser")
	public int saveUser(String acredit) throws RadowException{
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String infogroupname = this.getPageElement("infogroupname").getValue();
		InfoGroup ig = new InfoGroup();
		String infogroupid = this.getRadow_parent_data();
		Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
		HBSession sess = HBUtil.getHBSession();
		if("1".equals(infogroupid)){
			if(DBUtil.getDBType() == DBType.ORACLE){
				ig.setCreateuserid(cueUserid);
				ig.setInfogroupname(infogroupname);
				try {
					String nid = (String) sess.createSQLQuery("SELECT max(INFOGROUPID) FROM COMPETENCE_INFOGROUP").uniqueResult();
					nid = String.valueOf(Integer.valueOf(nid)+1);
					ig.setInfogroupid(nid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String area = (String) sess.createSQLQuery("SELECT a.AAA005 FROM AA01 a WHERE a.AAA001='AREA_ID'").uniqueResult();
				ig.setOrdernum(area);
				sess.save(ig);
				ts.commit();
				List list = new ArrayList();
				try {
					new LogUtil().createLog("614", "COMPETENCE_INFOGROUP",ig.getInfogroupid(),ig.getInfogroupname(), "", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					String nid = (String) sess.createSQLQuery("SELECT max(INFOGROUPID) FROM COMPETENCE_INFOGROUP").uniqueResult();
					nid = String.valueOf(Integer.valueOf(nid)+1);
				String area = (String) sess.createSQLQuery("SELECT a.AAA005 FROM AA01 a WHERE a.AAA001='AREA_ID'").uniqueResult();
				String sql = "insert into COMPETENCE_INFOGROUP(infogroupid,infogroupname,ordernum,createuserid) values('"+nid+"','"+infogroupname+"','"+area+"','"+cueUserid+"')";
				HBUtil.executeUpdate(sql);
				ts.commit();
				List list = new ArrayList();
				InfoGroup ig1 = new InfoGroup();
				new LogUtil().createLog("614", "COMPETENCE_INFOGROUP",ig1.getInfogroupid(),ig1.getInfogroupname(), "", list);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else{
			ig = (InfoGroup)sess.createQuery("From InfoGroup t Where t.infogroupid='"+infogroupid+"'").list().get(0);
			ig.setInfogroupname(infogroupname);
			sess.saveOrUpdate(ig);
			ts.commit();
			List list = new ArrayList();
			try {
				new LogUtil().createLog("615", "COMPETENCE_INFOGROUP",ig.getInfogroupid(),ig.getInfogroupname(), "", list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		this.setMainMessage("保存成功");
		this.closeCueWindowByYes("CreateIGWin");
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
