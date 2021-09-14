package com.insigma.siis.local.pagemodel.gbwh;

import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Gbkh;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
    
public class GBKHAddPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String gbkhid = this.getPageElement("gbkhid").getValue();
		try {
			if(gbkhid!=null&&!"".equals(gbkhid)){
				HBSession sess = HBUtil.getHBSession();
				Gbkh p=(Gbkh)sess.get(Gbkh.class,gbkhid);
//				Publish p = (Publish)sess.get(Publish.class, publishid);
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
		String year = this.getPageElement("year").getValue();
		String grade = this.getPageElement("grade").getValue();
		if(null==year||"".equals(year)){
			this.setMainMessage("请输入年份");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		String agendatype = this.getPageElement("agendatype").getValue();
//		if(null==agendatype||"".equals(agendatype)){
//			this.setMainMessage("请填写议程类型");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		String gbkhid = this.getPageElement("gbkhid").getValue();
//		String publishid = this.getPageElement("publishid").getValue();
		Gbkh p = new Gbkh();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			if(gbkhid==null||"".equals(gbkhid)){//新增
				String a0000 = this.getPageElement("a0000").getValue();
				List list = sess.createSQLQuery("select * from GBKH where year='"+year+"' and a0000='"+a0000+"'").list();
				if(list.size()!=0){
					this.setMainMessage("该年份已存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				int max_sort= Integer.valueOf(getMax_sort(a0000));
				p.setA0000(a0000);
				p.setYear(year);
				p.setGrade(grade);
				p.setSort(max_sort+1);
				sess.save(p);
				/*//保存附件表
				PublishAtt pa = new PublishAtt();
				pa.setPublishid(p.getPublishid());
				pa.setPat00(UUID.randomUUID().toString().replaceAll("-", ""));
				pa.setPat04("-");
				sess.save(pa);*/
				sess.flush();
				this.getPageElement("gbkhid").setValue(p.getGbkhid());
				this.getExecuteSG().addExecuteCode("saveCallBack();");
				//this.setMainMessage("新增成功");
			}else{
				Gbkh p_db=(Gbkh)sess.get(Gbkh.class, gbkhid);
//				Publish p_db = (Publish)sess.get(Publish.class, publishid);
				p_db.setYear(year);
				p_db.setGrade(grade);
//				p_db.setAgendaname(agendaname);
//				p_db.setAgendatype(agendatype);
				sess.update(p_db);
				sess.flush();
				this.getExecuteSG().addExecuteCode("saveCallBack();");
			}
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
	public String getMax_sort(String a0000){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sort),0) from GBKH where a0000='"+a0000+"'").uniqueResult().toString();
		return sort;
	}
	
}
