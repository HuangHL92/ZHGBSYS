package com.insigma.siis.local.pagemodel.jzsp.jgld;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Sp01;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.entity.Sp_Bus_Log;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.jzsp.SPUtil;

public class ViewJGLDPageModel extends PageModel {
	
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save(String ss) throws RadowException{
			
			
		
		
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
		String sp0100 = this.getPageElement("sp0100").getValue();
		try {
			if(sp0100!=null&&!"".equals(sp0100)){
				HBSession sess = HBUtil.getHBSession();
				Sp01 sp01 = (Sp01)sess.get(Sp01.class, sp0100);
				if(sp01==null){
					this.setMainMessage("业务信息不存在！");
					return EventRtnType.NORMAL_SUCCESS;
				}
				PMPropertyCopyUtil.copyObjValueToElement(sp01, this);
				Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, sp0100);
				if(sb!=null){
					String spb04 = sb.getSpb04();//审批机构
					String spb03 = sb.getSpb03();//审批人
					if(spb04!=null&&!"".equals(spb04)){
						this.getPageElement("usertype").setValue("group");
						this.getPageElement("spb04").setValue(spb04);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spb04));
					}else if(spb03!=null&&!"".equals(spb03)){
						this.getPageElement("usertype").setValue("user");
						this.getPageElement("spb04").setValue(spb03);
						this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spb03));
						
					}
					
				}else{
					this.getExecuteSG().addExecuteCode("setDisabledInfo()");
				}
				
				
				//设置文件信息
				List<Sp_Att> spalist = sess.createQuery("from Sp_Att where spb00='"+sp0100+"'").list();
				
				if(spalist!=null){
					List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
					for(Sp_Att spa : spalist){
						
						Map<String, String> map = new HashMap<String, String>();
						map.put("id", spa.getSpa00());
						map.put("name", spa.getSpa02());
						map.put("fileSize", spa.getSpa06());
						//map2.put("readOnly", "true");
						
						listmap.add(map);
						this.setFilesInfo("file03",listmap,false);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("searchA01")
	public int searchA01(String name) throws RadowException, AppException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("searchA01ByA0000")
	public int searchA01ByA0000(String a0000) throws RadowException, AppException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
