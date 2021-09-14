package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02Zwzc;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.Gbkh;
import com.insigma.siis.local.business.entity.WJGL;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class PersontiveFilePageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, PrivilegeException, AppException{

		 this.setNextEventName("clGrid.dogridquery");
	        return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("clGrid.rowclick")
	@GridDataRange
	public int persongridOnRowClick() throws RadowException{  //打开窗口的实例
		String a0000 = (String)this.getPageElement("clGrid").getValueList().get(this.getPageElement("clGrid").getCueRowIndex()).get("a0000");
		//this.getPageElement("a0000").setValue(a0000);
		//this.setNextEventName("clGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("clGrid.dogridquery")
	public int doLogQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select wj00,w.type,wj01,a0000,b0111,w.year,wj02,wj03,wj04,aaa005 || '/' || wj05 wj05,wj06,add00 " + 
				"from WJGL w,aa01 t  where w.a0000='"+a0000+"'  and w.type='2' and  t.aaa001='HZB_PATH' order by w.year desc ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}


	
	
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String a0000=this.getPageElement("a0000").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		if(a0000==null || "".equals(a0000) || checkedgroupid==null || "".equals(checkedgroupid)) {
			this.setMainMessage("请选择单位以及人员后上传！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode(" $h.openWin('CJWJGLAdd', 'pages.zwzc.CJWJGLAdd', '个人材料添加', 520, 350, null, '"+this.request.getContextPath()+"', null, { maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"' ,b0111:'"+checkedgroupid+"'})" );
		//this.getExecuteSG().addExecuteCode("$h.openPageModeWin('CJWJGLAdd','pages.zwzc.CJWJGLAdd','个人材料添加',520,350,{a0000:'"+a0000+"' ,b0111:'"+checkedgroupid+"'},'"+this.request.getContextPath()+"')" );
		//this.getExecuteSG().addExecuteCode(" $h.openPageModeWin('CJWJGLAdd', 'pages.zwzc.CJWJGLAdd', '个人材料添加', 520, 350,{ maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"' ,b0111:'"+checkedgroupid+"'},'"+this.request.getContextPath()+"')" );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRow")
	public int deleteRow(String wj00) throws AppException, RadowException {
		CommQuery query = new CommQuery();
		HBSession sess = HBUtil.getHBSession();
		
		WJGL wjgl=(WJGL)sess.get(WJGL.class, wj00);
		try {
			if(wjgl==null) {
				this.setMainMessage("删除失败");
				//reloadCustomQuery();

				return EventRtnType.NORMAL_SUCCESS;
			}
			String add00=wjgl.getAdd00();
			WJGLAdd wjgladd=(WJGLAdd)sess.get(WJGLAdd.class,add00);
			if(wjgladd==null) {
				String fileurl = null;
				fileurl=wjgl.getWj05();
				File file = new File(fileurl);
				deleteFile(file);
				HBUtil.executeUpdate("delete from wjgl where wj00 = '"+wj00+"'");
			}else {
				String fileurl = null;
				fileurl=wjgladd.getFileurl();
				File file = new File(fileurl);
				String parentPath = file.getParent();
				File parenfile = new File(parentPath);
				deleteFile(parenfile);
				HBUtil.executeUpdate("delete from wjgl where wj00 = '"+wj00+"'");			
				HBUtil.executeUpdate("delete from wjgladd where add00='"+add00+"'");
			}
			
							
			this.setMainMessage("删除成功！");
			this.setNextEventName("clGrid.dogridquery");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("删除失败！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void deleteFile(File file) {  
		if (file.exists()) {//判断文件是否存在  
			 if (file.isFile()) {//判断是否是文件  
				 file.delete();//删除文件  
			 } else if (file.isDirectory()) {//否则如果它是一个目录  
				 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				 for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
				       this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
				      } 
				 file.delete();//删除文件夹  
			 }
		} else {  
		     System.out.println("所删除的文件不存在");  
			
		}
	}

	
}