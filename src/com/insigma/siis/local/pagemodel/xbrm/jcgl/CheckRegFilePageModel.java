package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.JSGLPageModel;
import com.insigma.siis.local.pagemodel.xbrm.OracleToSqlite;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;

import sun.misc.BASE64Encoder;

public class CheckRegFilePageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String deptid = user.getDept();
		String regname1 = this.getPageElement("regname1").getValue();
		String regno = this.getPageElement("regno1").getValue();
		String xm = this.getPageElement("xm").getValue();
		String sfz = this.getPageElement("sfz").getValue();
		String where1 = "";
		/*if(!"c76c922860fe40c4972db05b0bc21be8".equals(deptid)) {
			where1 = " and userid = '"+user.getId()+"' ";
		} */
		String where = "";
		if(regname1!=null&&!"".equals(regname1)){
			where += " and t.regname like '%"+regname1+"%'";
		}
		if(regno!=null&&!"".equals(regno)){
			where += " and t.regno like '%"+regno+"%'";
		}
		if((xm!=null&&!"".equals(xm)) || (sfz!=null&&!"".equals(sfz))){
			where += " and t.checkregid in (select k.checkregid from checkregperson k where 1=1 ";
			if(xm!=null&&!"".equals(xm)) {
				where += " and k.CRP001 like '%"+xm+"%'";
			}
			if(sfz!=null&&!"".equals(sfz)) {
				where += " and k.CRP006 like '%"+sfz+"%'";
			}
			where += ") ";
		}
		
		String sql="select * from CHECKREG t where 1=1 and REGSTATUS='1' "+where1+" "+where+" order by createtime desc";
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("allDelete")
	public int allDelete(String checkregid) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			String sql = "delete from checkreg where checkregid='"+checkregid+"'";
			sess.createSQLQuery(sql).executeUpdate();
			//批次目录
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#rb_id').val(''); $('#rb_name').val('');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("chaneStatus")
	public int chaneStatus(String chaneStatus) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			String sql = "update checkreg set regstatus='"+chaneStatus
					+"' where checkregid='"+checkregid+"'";
			sess.createSQLQuery(sql).executeUpdate();
			this.setMainMessage("数据更新成功！");
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("操作数据异常!");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
