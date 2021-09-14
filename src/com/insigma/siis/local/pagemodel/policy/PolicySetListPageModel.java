package com.insigma.siis.local.pagemodel.policy;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class PolicySetListPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("policySetgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 政策法规列表数据加载
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("policySetgrid.dogridquery")
	@NoRequiredValidate
	public int policySetgrid(int start,int limit) throws RadowException{
		//获取登录用户的信息
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		
		String sql = "select * from POLICY where a0000='"+user.getId()+"' order by UPDATETIME desc";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	//点击树查询事件
		@PageEvent("getFileList")
		@NoRequiredValidate
		@AutoNoMask
		public int getDBmsgs(String id) throws RadowException {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			try {
				String sql = "select filename,title,updatetime,fileurl from POLICY where a0000='"+user.getId()+"' order by UPDATETIME desc";
				List<HashMap> list = CommonQueryBS.getQueryInfoByManulSQL(sql);
				this.setSelfDefResData(JSONArray.fromObject(list).toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return EventRtnType.XML_SUCCESS;
		}
	
}
