package com.insigma.siis.local.pagemodel.jzsp.jgld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Sp01_Pc;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.entity.Sp_Bus;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class ViewSTJZHZPageModel extends PageModel{
	
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sp0100s = this.getPageElement("sp0100s").getValue();
		if(sp0100s==null||"".equals(sp0100s)){
			String spp00 = this.getPageElement("spp00").getValue();
			String sql="select * from SP01 t where spp00 ='"+spp00+"' "
					+ " order by t.sp0116 desc";
			this.pageQuery(sql, "SQL", start, limit);
			return EventRtnType.SPE_SUCCESS;
		}
		sp0100s = sp0100s.substring(0,sp0100s.length()-1).replace(",", "','");
		String sql="select * from SP01 t where sp0100 in('"+sp0100s+"') "
				+ " order by t.sp0116 desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String spp00 = this.getPageElement("spp00").getValue();
		HBSession sess = HBUtil.getHBSession();
		if(spp00==null||"".equals(spp00)){
			this.getExecuteSG().addExecuteCode("setDisabled1();");
		}else{
			Sp01_Pc spc = (Sp01_Pc)sess.get(Sp01_Pc.class, spp00);
			if(spc==null){
				this.setMainMessage("查询失败");
				return EventRtnType.NORMAL_SUCCESS;
			}
			Sp_Bus sb = (Sp_Bus)sess.get(Sp_Bus.class, spp00);
			if(sb!=null){
				String spp09 = sb.getSpb03();
				String spp10 = sb.getSpb04();
				if(spp10!=null&&!"".equals(spp10)){
					this.getPageElement("usertype").setValue("group");
					this.getPageElement("spb04").setValue(spp10);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getGroupName(spp10));
				}else if(spp09!=null&&!"".equals(spp09)){
					this.getPageElement("usertype").setValue("user");
					this.getPageElement("spb04").setValue(spp09);
					this.getPageElement("spb04_combotree").setValue(SysManagerUtils.getUserName(spp09));
				}
			}else{
				this.getExecuteSG().addExecuteCode("setspb04hidden();");
				
			}
			
			PMPropertyCopyUtil.copyObjValueToElement(spc, this);
			this.getExecuteSG().addExecuteCode("setDisabled1();");
			
			
			
			//设置文件信息
			List<Sp_Att> spalist = sess.createQuery("from Sp_Att where spb00='"+spp00+"'").list();
			
			if(spalist!=null){
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for(Sp_Att spa : spalist){
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", spa.getSpa00());
					map.put("name", spa.getSpa02());
					map.put("fileSize", spa.getSpa06());
					map.put("readOnly", "true");
					
					listmap.add(map);
					this.setFilesInfo("file03",listmap,false);
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
