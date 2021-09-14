package com.insigma.siis.local.pagemodel.yntp;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.a00hz.HzTphjZw;

public class TpbzwPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String tp0100 = this.getPageElement("tp0100").getValue();				
		String sql = "select t.*,(select b0101 from b01 where b01.b01id=t.b01id) b0101 from HZ_TPHJ_zw t where tp0100='"+tp0100+"'";
		this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	
	@PageEvent("grid1.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int grid1OnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("grid1").getCueRowIndex();
		String tpzw00 = this.getPageElement("grid1").getValue("tpzw00",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			HzTphjZw hzTphjZw = (HzTphjZw)sess.load(HzTphjZw.class, tpzw00);
			if(hzTphjZw!=null){
				PMPropertyCopyUtil.copyObjValueToElement(hzTphjZw, this);
			}
			String v = HBUtil.getValueFromTab("b0111", "B01", "b01id='"+hzTphjZw.getB01id()+"'");
			if(v!=null){
				this.getPageElement("b0111").setValue(v);
			}
			v = HBUtil.getValueFromTab("b0101", "B01", "b01id='"+hzTphjZw.getB01id()+"'");
			if(v!=null){
				this.getPageElement("b0111_combo").setValue(v);
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	
	

}
