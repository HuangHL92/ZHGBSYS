package com.insigma.siis.local.pagemodel.cbdHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Transaction;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CBDInfo;
import com.insigma.siis.local.business.entity.Cbdstatus;

public class AddUPCBDInfoPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		
		String value = this.getRadow_parent_data();
		String[] values = value.split("@");
		if("U".equals(values[1])){
			
			HBSession sess = HBUtil.getHBSession();
			List<CBDInfo> cbdinfo =  sess.createQuery("from CBDInfo where objectno = '"+values[0]+"' and cbd_path = '2'").list();
			if(cbdinfo.size()>0){
				
				this.getPageElement("cdb_word_year_no").setValue(cbdinfo.get(0).getCdb_word_year_no());
				this.getPageElement("cbd_leader").setValue(cbdinfo.get(0).getCbd_leader());
				this.getPageElement("cbd_organ").setValue(cbdinfo.get(0).getCbd_organ());
				this.getPageElement("cbd_text").setValue(cbdinfo.get(0).getCbd_text());
				this.getPageElement("cbd_date1").setValue(cbdinfo.get(0).getCbd_date1());
				this.getPageElement("cbd_id").setValue(cbdinfo.get(0).getCbd_id());
				this.getPageElement("objectno").setValue(cbdinfo.get(0).getObjectno());
			}
		}
		return 0;
	}

	@PageEvent("makeCBD.onclick")
	public int makeCBD() throws RadowException{
		
		//获取页面参数
		String word = this.getPageElement("cdb_word_year_no").getValue();
		String cbd_leader = this.getPageElement("cbd_leader").getValue();
		String cbd_organ = this.getPageElement("cbd_organ").getValue();
		String cbd_text = this.getPageElement("cbd_text").getValue();
		String cbd_date1 = this.getPageElement("cbd_date1").getValue();
		
		if(StringUtil.isEmpty(word)){
			this.setMainMessage("请输入呈报单审批字_年_号");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_leader)){
			this.setMainMessage("请输入主送机关");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_organ)){
			this.setMainMessage("请输入发文机关");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_text)){
			this.setMainMessage("请输入呈报单正文");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(cbd_date1)){
			this.setMainMessage("请输入呈报单日期");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("saveCBDInfo");		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveCBDInfo")
	public int saveCBDInfo() throws RadowException{

		HBSession sess = HBUtil.getHBSession();
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		//获取时间
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		String createdate = sdf.format(new Date());
		
		CBDInfo cbdinfo = new CBDInfo();
		String cbdid = this.getPageElement("cbd_id").getValue();
		if(cbdid != null && !"".equals(cbdid)){
			cbdinfo.setCbd_id(cbdid);
		}else{
			
			String uuid = UUID.randomUUID().toString();
			cbdinfo.setCbd_id(uuid);
		}
		//获取本级呈报单的编号
		String parent_cbd_id = this.getRadow_parent_data();
		if("U".equals(parent_cbd_id.split("@")[1])){
			parent_cbd_id = this.getPageElement("objectno").getValue();
		}else{
			parent_cbd_id = this.getRadow_parent_data().split("@")[0];
		}
		CBDInfo cbdinfo_p = null;
		//获取本级呈报单信息
		List<CBDInfo> list = sess.createQuery("from CBDInfo where cbd_id = '"+parent_cbd_id+"'").list();
		if(list.size()>0){
			
			cbdinfo_p = list.get(0);
			
			cbdinfo.setCbd_date(createdate);
			cbdinfo.setCbd_date1(this.getPageElement("cbd_date1").getValue());
			cbdinfo.setCdb_word_year_no(this.getPageElement("cdb_word_year_no").getValue());
			cbdinfo.setCbd_leader(this.getPageElement("cbd_leader").getValue());
			cbdinfo.setCbd_organ(this.getPageElement("cbd_organ").getValue());
			cbdinfo.setCbd_text(this.getPageElement("cbd_text").getValue());
			cbdinfo.setCbd_userid(user.getId());
			cbdinfo.setCbd_username(user.getName());
			cbdinfo.setObjectno(parent_cbd_id);
			cbdinfo.setCbd_personid(cbdinfo_p.getCbd_personid());
			cbdinfo.setCbd_personname(cbdinfo_p.getCbd_personname());
			cbdinfo.setCbd_name(cbdinfo_p.getCbd_name());
			//设置呈报单类型  1：本级呈报单，2：上报呈报单，3：接收页面的本级呈报单
			cbdinfo.setCbd_path("2");
			cbdinfo.setStatus("0");
			Transaction ts = PrivilegeManager.getInstance().getHbSession().beginTransaction();
			sess.saveOrUpdate(cbdinfo);
			this.setMainMessage("录入上报呈报单成功！");
			ts.commit();
			this.createPageElement("sb_cbd_id", ElementType.HIDDEN, true).setValue(cbdinfo.getCbd_id());
			this.closeCueWindow("newUpCBD");
			//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reload')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
