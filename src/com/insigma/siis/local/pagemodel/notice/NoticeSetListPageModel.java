package com.insigma.siis.local.pagemodel.notice;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;

public class NoticeSetListPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		this.setNextEventName("noticeSetgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		
		
		try {
			
			//获取登录用户的信息
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			
			HBSession sess = HBUtil.getHBSession();
			String sqlB0111 = "select B0111 from b01 where b0111 = '"+user.getOtherinfo()+"'";
			Object b0111 = sess.createSQLQuery(sqlB0111).uniqueResult();
			
			if(b0111 == null || b0111.equals("") ){
				
				this.getExecuteSG().addExecuteCode("document.getElementById('b0111').value='';");
			}else{
				
				this.getExecuteSG().addExecuteCode("document.getElementById('b0111').value='"+b0111.toString()+"';");
			}
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 通知公告列表数据加载
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	@NoRequiredValidate
	public int noticeSetgrid(int start,int limit) throws RadowException{
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		/*HBSession sess = HBUtil.getHBSession();
		String sqlB0111 = "select B0111 from b01 where b0111 = '"+user.getOtherinfo()+"'";
		Object b0111 = sess.createSQLQuery(sqlB0111).uniqueResult();
		
		if(b0111 != null || !b0111.equals("") ){
			
			//this.getPageElement("b0111").setValue(b0111.toString());
			
			this.getExecuteSG().addExecuteCode("document.getElementById('b0111').value='"+b0111.toString()+"';");
			//out.println("<script>parent.odin.alert('用户所属机构不存在，不可发布通知公告！');</script>");
		}else{
			this.getExecuteSG().addExecuteCode("document.getElementById('b0111').value='';");
		}*/
		
		
		String sql = "select * from Notice where a0000='"+user.getId()+"' order by UPDATETIME desc";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
}
