package com.insigma.siis.local.pagemodel.sysmanager.parammgmt;

import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Aa01;

public class UpdateParamPageModel extends PageModel {

	@SuppressWarnings("unchecked")
	@Override
	public int doInit() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String sql = "from Aa01 where aaa001='"+this.getRadow_parent_data()+"'";
		List list = sess.createQuery(sql).list();
		Aa01 aa01 = (Aa01) list.get(0);	
		PMPropertyCopyUtil.copyObjValueToElement(aa01, this);
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * 取消后执行的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("cannelEvent")
	public int cannelEvent() throws RadowException{ //带参数的自定义事件
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/**
	 * 修改参数信息
	 * 
	 * @throws RadowException
	 * @throws RadowException
	 */
	@PageEvent("saveBtn.onclick")
	public int deleteBntOnClick() throws RadowException {
		this.addNextEvent(NextEventValue.YES, "saveUpdateEvent");
		this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");// 其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您确实要保存修改好的信息吗？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveUpdateEvent")
	public int saveOnClick() throws RadowException{
		Aa01 aa01 = new Aa01();
		HBSession sess = HBUtil.getHBSession();
		try {
			this.copyElementsValueToObj(aa01, this);
			sess.beginTransaction();
			sess.saveOrUpdate(aa01);
			sess.flush();
			sess.getTransaction().commit();
		} catch (AppException e) {
			this.setMainMessage("修改参数错误");
			e.printStackTrace();
			return EventRtnType.FAILD;
		}
		this.createPageElement("codegrid", "grid", true).reload();
		this.closeCueWindow("UpdateWin");
		return EventRtnType.NORMAL_SUCCESS;
	} 
}
