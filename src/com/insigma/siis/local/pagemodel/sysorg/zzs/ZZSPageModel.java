package com.insigma.siis.local.pagemodel.sysorg.zzs;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.IDUtil;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.hzentity.B01Zzs;
import com.insigma.siis.local.business.hzentity.B01ZzsLd;

public class ZZSPageModel extends PageModel {

    @Override
    public int doInit() throws RadowException {
    	this.setNextEventName("memberGridMain.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }



    @PageEvent("save.onclick")
    public int save() throws RadowException, AppException {
        String zzs00 = this.getPageElement("zzs00").getValue();
        String b0111 = this.getPageElement("b0111").getValue();
        String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
        HBSession session = HBUtil.getHBSession();
        if (StringUtils.isBlank(b01id)) {
        	this.setMainMessage("请选择单位！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        if (StringUtils.isBlank(zzs00)) {
            // 新增数据
        	B01Zzs record = new B01Zzs();
        	PMPropertyCopyUtil.copyElementsValueToObj(record, this);
        	record.setB01id(b01id);
            //PMPropertyCopyUtil.copyObjValueToElement(record, this);
            zzs00 = IDUtil.generateUUID();
            record.setZzs00(zzs00);
            session.save(record);
            session.flush();
            this.getPageElement("zzs00").setValue(zzs00);
        } else {
            // 修改记录
        	B01Zzs record = (B01Zzs) session.get(B01Zzs.class, zzs00);
        	PMPropertyCopyUtil.copyElementsValueToObj(record, this);
            session.update(record);
            session.flush();
        }
        this.setNextEventName("memberGridMain.dogridquery");
        this.setMainMessage("保存成功");
        return EventRtnType.NORMAL_SUCCESS;
    }

    
    
    
    
    @PageEvent("addETCInfo")
	@Transaction
	public int addInfo() throws Exception{
    	String ld00 = this.getPageElement("ld00").getValue();
    	String zzs00 = this.getPageElement("zzs00").getValue();
    	
		String ld01 = this.getPageElement("ld01").getValue();
		String ld02 = this.getPageElement("ld02").getValue();
		String ld03 = this.getPageElement("ld03").getValue();
		String ld04 = this.getPageElement("ld04").getValue();
		if(StringUtils.isEmpty(zzs00)){
			this.setMainMessage("请先保存组织史信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession session = HBUtil.getHBSession();
		
		if(StringUtils.isEmpty(ld00)){
			ld00 = UUID.randomUUID().toString();
			B01ZzsLd record = new B01ZzsLd();
			PMPropertyCopyUtil.copyElementsValueToObj(record, this);
            record.setZzs00(zzs00);
            record.setLd00(ld00);
            session.save(record);
            session.flush();
            this.getPageElement("ld00").setValue(ld00);
		}else{
			B01ZzsLd record = (B01ZzsLd) session.get(B01ZzsLd.class, ld00);
			PMPropertyCopyUtil.copyElementsValueToObj(record, this);
			session.update(record);
            session.flush();
		}
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    @PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
    	String zzs00 = this.getPageElement("zzs00").getValue();
		//定义用来组装sql的变量
		StringBuffer str = new StringBuffer();
		str.append("select * from b01_zzs_ld where zzs00 ='"+zzs00+"'");
		
		
		
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
    @PageEvent("memberGridMain.dogridquery")
    public int doMemberGridMainQuery(int start,int limit) throws RadowException, AppException{
    	String b0111 = this.getPageElement("b0111").getValue();
        String b01id = HBUtil.getValueFromTab("b01id", "b01", "b0111='"+b0111+"'");
        HBSession session = HBUtil.getHBSession();
        if (StringUtils.isBlank(b01id)) {
        	this.setMainMessage("请选择单位！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
    	//定义用来组装sql的变量
    	StringBuffer str = new StringBuffer();
    	str.append("select * from b01_zzs where b01id ='"+b01id+"'");
    	
    	
    	
    	this.pageQuery(str.toString(), "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
    
    /**
	 * 选择行修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGridMain.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int assessmentInfoGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("memberGridMain").getCueRowIndex();
		String zzs00 = this.getPageElement("memberGridMain").getValue("zzs00",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			B01Zzs record = (B01Zzs) sess.get(B01Zzs.class, zzs00);
			PMPropertyCopyUtil.copyObjValueToElement(record, this);
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
    @PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String ld00)throws RadowException, AppException{
		try {
			
			HBUtil.executeUpdate("delete from b01_zzs_ld where ld00=?",
					new Object[]{ld00});
			this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery');");
			
			
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
    @PageEvent("deleteRowMain")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int deleteRowMain(String zzs00)throws RadowException, AppException{
    	try {
    		
    		HBUtil.executeUpdate("delete from b01_zzs_ld where zzs00=?",
    				new Object[]{zzs00});
    		HBUtil.executeUpdate("delete from b01_zzs where zzs00=?",
    				new Object[]{zzs00});
    		this.setNextEventName("memberGridMain.dogridquery");
    		
    		
    	} catch (Exception e) {
    		this.setMainMessage("删除失败！");
    		return EventRtnType.FAILD;
    	}
    	
    	
    	
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("AddBtn.onclick")
	@NoRequiredValidate
	public int AddBtn(String id)throws RadowException{
		
    	B01Zzs zzs = new B01Zzs();
		
		PMPropertyCopyUtil.copyObjValueToElement(zzs, this);
		this.getExecuteSG().addExecuteCode("radow.doEvent('memberGrid.dogridquery');");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
