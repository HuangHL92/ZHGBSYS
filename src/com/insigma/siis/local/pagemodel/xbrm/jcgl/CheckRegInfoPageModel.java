package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.utils.CommonQueryBS;

public class CheckRegInfoPageModel extends PageModel {
	
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String id) throws RadowException{
		String checkregid = this.getPageElement("checkregid").getValue();
		Checkreg cr = new Checkreg();
		try {
			HBSession sess = HBUtil.getHBSession();
			if(checkregid==null||"".equals(checkregid)){//新增
				PMPropertyCopyUtil.copyElementsValueToObj(cr, this);
				cr.setCreatetime(DateUtil.getTimestamp());
				cr.setRegstatus("0");
				cr.setRegtype("1");
				sess.save(cr);
				sess.flush();
				//初始化类型
				this.getPageElement("checkregid").setValue(cr.getCheckregid());
			}else{
				cr = (Checkreg)sess.get(Checkreg.class, checkregid);
				Timestamp createtime = cr.getCreatetime();
				PMPropertyCopyUtil.copyElementsValueToObj(cr, this);
				cr.setCreatetime(createtime);
				sess.update(cr);
				sess.flush();
			}
			this.getExecuteSG().addExecuteCode("Ext.MessageBox.alert('消息提示', '保存成功！', function(e){ if ('ok' == e){saveCallBack('');}});");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
			
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String checkregid = this.getPageElement("checkregid").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			if(checkregid!=null&&!"".equals(checkregid)){
				Checkreg rb = (Checkreg)sess.get(Checkreg.class, checkregid);
				PMPropertyCopyUtil.copyObjValueToElement(rb, this);
				if(rb.getRegstatus()!=null && !rb.getRegstatus().equals("0")) {
					this.getExecuteSG().addExecuteCode("Ext.getCmp('save').setDisabled(true);");
				}
			} else {
				UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
				String cueUserid = user.getId();
				CommonQueryBS cq=new CommonQueryBS();
				String sql="select smt_usergroup.usergroupname,smt_usergroup.id,smt_user.loginname,smt_user.userid from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
				
				HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
				String usergroupname=(String)mapBySQL.get("usergroupname");
				String loginname=(String)mapBySQL.get("loginname");
				String groupid=(String)mapBySQL.get("id");
				String userid=(String)mapBySQL.get("userid");
				this.getPageElement("checkdate").setValue(DateUtil.getcurdate());
				this.getPageElement("regno").setValue(DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmm"));
				this.getPageElement("userid").setValue(userid);
				this.getPageElement("groupid").setValue(groupid);
				this.getPageElement("groupname").setValue(usergroupname);
				this.getPageElement("reguser").setValue(loginname);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
