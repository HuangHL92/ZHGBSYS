package com.insigma.siis.local.pagemodel.meeting;

import java.util.ArrayList;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
    
public class MeetingAddPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String meetingid = this.getPageElement("meetingid").getValue();
		try {
			if(meetingid!=null&&!"".equals(meetingid)){
				HBSession sess = HBUtil.getHBSession();
				MeetingTheme mt = (MeetingTheme)sess.get(MeetingTheme.class, meetingid);
				PMPropertyCopyUtil.copyObjValueToElement(mt, this);
				if("3".equals(mt.getMeetingtype())) {
					this.getExecuteSG().addExecuteCode("document.getElementById('tr1').style.display='';document.getElementById('tr2').style.display='';");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 保存
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String meetingid = this.getPageElement("meetingid").getValue();
		String meetingname = this.getPageElement("meetingname").getValue().trim();
		String meetingtype = this.getPageElement("meetingtype").getValue();
		String meetingjc = this.getPageElement("meetingjc").getValue();
		String meetingpc = this.getPageElement("meetingpc").getValue();
		if(null==meetingname||"".equals(meetingname)){
			this.setMainMessage("请填写会议名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(null==meetingtype||"".equals(meetingtype)){
			this.setMainMessage("请填写会议类型");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String time = this.getPageElement("time").getValue();
		if(null==time||"".equals(time)){
			this.setMainMessage("请填写会议时间");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if((null==meetingjc||"".equals(meetingjc))&&"3".equals(meetingtype)){
			this.setMainMessage("请填写常委会届次");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if((null==meetingpc||"".equals(meetingpc))&&"3".equals(meetingtype)){
			this.setMainMessage("请填写常委会批次");
			return EventRtnType.NORMAL_SUCCESS;
		}
		MeetingTheme mt = new MeetingTheme();
		try {
			HBSession sess = HBUtil.getHBSession();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			if(meetingid==null||"".equals(meetingid)){//新增
				List list = sess.createSQLQuery("select * from meetingtheme where meetingname='"+meetingname+"'").list();
				if(list.size()!=0){
					this.setMainMessage("会议名称已存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String userid = SysManagerUtils.getUserId();
				mt.setMeetingname(meetingname);
				mt.setMeetingpc(meetingpc);
				mt.setMeetingjc(meetingjc);
				mt.setMeetingtype(meetingtype);
				mt.setTime(time);
				mt.setUserid(userid);
				sess.save(mt);
				sess.flush();
				this.getPageElement("meetingid").setValue(mt.getMeetingid());
				this.getExecuteSG().addExecuteCode("saveCallBack('新增成功');");
				//this.setMainMessage("新增成功");
				new LogUtil(user).createLogNew(user.getId(),"新建会议","meetingtheme",user.getId(),meetingname, new ArrayList());
			}else{
				String userid = this.getPageElement("userid").getValue();
				mt.setMeetingpc(meetingpc);
				mt.setMeetingjc(meetingjc);
				mt.setMeetingname(meetingname);
				mt.setMeetingtype(meetingtype);
				mt.setTime(time);
				mt.setMeetingid(meetingid);
				mt.setUserid(userid);
				sess.update(mt);
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack('修改成功');");
				//this.setMainMessage("修改成功");
				new LogUtil(user).createLogNew(user.getId(),"修改会议","meetingtheme",user.getId(),meetingname, new ArrayList());
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
