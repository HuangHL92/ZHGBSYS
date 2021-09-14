package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A32;


public class SetWarnWindowPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
	    return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		HBSession sess = null;
	    sess = HBUtil.getHBSession();
	    String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();	    
	    String sqlA32 = "from A32 where userid='"+userid+"'";
	    List list = sess.createQuery(sqlA32).list();
	    if(list.size()>0){
	    	A32 a32 = (A32) list.get(0);
	    	this.getPageElement("userid").setValue(""+a32.getUserid());
	    	this.getPageElement("mage").setValue(""+a32.getMage());
	    	this.getPageElement("fmage").setValue(""+a32.getFmage());
	    	this.getPageElement("syday").setValue(""+a32.getSyday());
	    	this.getPageElement("birthday").setValue(""+a32.getBirthday());
	    }
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//保存事件
	@PageEvent("saveBtn.onclick")
	@Transaction
	public int save() throws RadowException {
		String syDay = this.getPageElement("syday").getValue();
		String birthDay = this.getPageElement("birthday").getValue();
		String fmage = this.getPageElement("fmage").getValue();
		String mage = this.getPageElement("mage").getValue();
		if(StringUtil.isEmpty(fmage)||StringUtil.isEmpty(mage)){
			this.setMainMessage("退休年龄设置不能为空！");
			return EventRtnType.FAILD;
		}
		if(StringUtil.isEmpty(syDay)){
			this.setMainMessage("试用期到期设置不能为空！");
			return EventRtnType.FAILD;
		}
		if(StringUtil.isEmpty(birthDay)){
			this.setMainMessage("生日到期设置不能为空！");
			return EventRtnType.FAILD;
		}
		int syDay1 = Integer.parseInt(syDay);
		int birthDay1 = Integer.parseInt(birthDay);
		int fmage1 = Integer.parseInt(fmage);
		int mage1 = Integer.parseInt(mage);
		if(fmage1<0||mage1<0){
			this.setMainMessage("退休年龄设置不能小于零！");
			return EventRtnType.FAILD;
		}
		if(syDay1<0){
			this.setMainMessage("试用期到期设置不能小于零！");
			return EventRtnType.FAILD;
		}
		if(birthDay1<0){
			this.setMainMessage("生日到期设置不能小于零！");
			return EventRtnType.FAILD;
		}
		String userid = this.getPageElement("userid").getValue();
	    HBSession sess = null;
	    try{
	    sess = HBUtil.getHBSession();
	    A32 a32 = null;
	    if(userid == null||"".equals(userid)){
	    	a32 =  new A32();
	    	userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
	    	 a32.setUserid(userid);
	    }else{
	    	a32 = (A32) sess.get(A32.class, userid);
	    	    
	    }
	    String sql1 = "select useful from SMT_USER where userid = '"+userid+"'";
	    String useful = sess.createSQLQuery(sql1).list().get(0).toString();
	    a32.setUseful(useful);
        a32.setSyday(BigDecimal.valueOf(Long.parseLong(syDay)));
	    a32.setBirthday(BigDecimal.valueOf(Long.parseLong(birthDay)));
	    this.copyElementsValueToObj(a32, this);
	    a32.setUserid(userid);
	   // String sql2 = "update A32 set userid = '"+userid+"',userful = '"+userful+"',mage = '"+mage+"',fmage = '"+fmage+"',syday = '"+syday+"',birthday = '"+birthday+"'";
	    sess.saveOrUpdate(a32);
	    //this.setMainMessage("保存成功！请点击'关闭'按钮进行页面刷新！");
	    this.getExecuteSG().addExecuteCode("saveSuccess()");
	    }catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
	    return EventRtnType.NORMAL_SUCCESS;
	}
	
	//关闭窗口
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		this.closeCueWindow("setWarnWin");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('reload')");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
