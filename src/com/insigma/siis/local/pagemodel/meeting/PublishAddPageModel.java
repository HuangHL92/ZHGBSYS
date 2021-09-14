package com.insigma.siis.local.pagemodel.meeting;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
    
public class PublishAddPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String publishid = this.getPageElement("publishid").getValue();
		try {
			if(publishid!=null&&!"".equals(publishid)){
				HBSession sess = HBUtil.getHBSession();
				Publish p = (Publish)sess.get(Publish.class, publishid);
				PMPropertyCopyUtil.copyObjValueToElement(p, this);
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
		String agendaname = this.getPageElement("agendaname").getValue().trim();
		if(null==agendaname||"".equals(agendaname)){
			this.setMainMessage("请填写议题名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String agendatype = this.getPageElement("agendatype").getValue();
		if(null==agendatype||"".equals(agendatype)){
			this.setMainMessage("请填写议题类型");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String publishid = this.getPageElement("publishid").getValue();
		Publish p = new Publish();
		String meetingid = this.getPageElement("meetingid").getValue();
		String sort = this.getPageElement("sort").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			if(publishid==null||"".equals(publishid)){//新增
				List list = sess.createSQLQuery("select * from publish where agendaname='"+agendaname+"' and meetingid='"+meetingid+"'").list();
				if(list.size()!=0){
					this.setMainMessage("议题名称已存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				//int max_sort= Integer.valueOf(getMax_sort(meetingid));
				if("".equals(sort)||sort==null) {
					p.setSort(Integer.valueOf(getMax_sort(meetingid))+1);
				}else {
					List list_sort = sess.createSQLQuery("select * from publish where sort='"+sort+"' and meetingid='"+meetingid+"'").list();
					if(list_sort.size()!=0){
						String sql="update publish set sort=sort+1 where to_number(sort)>="+sort+" and meetingid='"+meetingid+"'";
						Statement stmt = sess.connection().createStatement();
						stmt.executeUpdate(sql);
						stmt.close();
					}
					p.setSort(Integer.valueOf(sort));
				}
				String userid = SysManagerUtils.getUserId();
				p.setMeetingid(meetingid);
				p.setAgendaname(agendaname);
				p.setAgendatype(agendatype);
				p.setUserid(userid);
				sess.save(p); 
				/*//保存附件表
				PublishAtt pa = new PublishAtt();
				pa.setPublishid(p.getPublishid());
				pa.setPat00(UUID.randomUUID().toString().replaceAll("-", ""));
				pa.setPat04("-");
				sess.save(pa);*/
				sess.flush();
				this.getPageElement("publishid").setValue(p.getPublishid());
				new LogUtil(user).createLogNew(user.getId(),"新建议题","publish",user.getId(),agendaname, new ArrayList());
			}else{
				Publish p_db = (Publish)sess.get(Publish.class, publishid);
				p_db.setAgendaname(agendaname);
				p_db.setAgendatype(agendatype);
				if("".equals(sort)||sort==null) {
					p_db.setSort(Integer.valueOf(getMax_sort(meetingid))+1);
				}else {
					List list_sort = sess.createSQLQuery("select * from publish where sort='"+sort+"' and publishid<>'"+publishid+"' and meetingid='"+meetingid+"'").list();
					if(list_sort.size()!=0){
						String sql="update publish set sort=sort+1 where to_number(sort)>="+sort+" and meetingid='"+meetingid+"'";
						Statement stmt = sess.connection().createStatement();
						stmt.executeUpdate(sql);
						stmt.close();
					}
					p_db.setSort(Integer.valueOf(sort));
				}
				String userid=p_db.getUserid();
				p_db.setUserid(userid);
				sess.update(p_db);
				sess.flush();
				new LogUtil(user).createLogNew(user.getId(),"修改议题","publish",user.getId(),agendaname, new ArrayList());
			}
			this.setMainMessage("保存成功");
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String meetingid){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sort),0) from publish where meetingid='"+meetingid+"'").uniqueResult().toString();
		return sort;
	}
	
}
