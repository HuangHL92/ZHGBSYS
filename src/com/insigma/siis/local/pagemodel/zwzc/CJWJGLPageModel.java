package com.insigma.siis.local.pagemodel.zwzc;

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

public class CJWJGLPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX(String ets00) throws RadowException, PrivilegeException, AppException{

		return 0;
	}
	
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ryGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//定义用来组装sql的变量
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		StringBuffer str = new StringBuffer();
		if("1".equals(isContain)) {
			str.append("select a.a0000,a.a0101,a.a0107,a.a0192a," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='政治素质考核材料' ) cl01," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='干部提任考察材料' ) cl02," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='平时考核表现材料' ) cl03," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='年度考核' ) cl04," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='民主生活会材料' ) cl05," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='整改清单' ) cl06," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='向组织说说心里话' ) cl07," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='其他' ) cl08 "+
					"  from (select a01.a0000, a01.a0101, decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
					"       a0192a" + 
					"          from A01 a01" + 
					"         where 1 = 1" + 
					"           and fkly is null" + 
					"           and a01.status != '4'" + 
					"           and a0163 like '1%'" + 
					"           and a01.a0000 in" + 
					"               (select a02.a0000" + 
					"                  from a02" + 
					"                 where a02.A0201B in" + 
					"                       (select cu.b0111" + 
					"                          from competence_userdept cu" + 
					"                         where cu.userid = '"+SysManagerUtils.getUserId()+"')" + 
					"                   and a0281 = 'true'" + 
					"                   and a02.a0201b like '"+checkedgroupid+"%'" + 
					"                   )" + 
					"         order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
					"                      from (select a02.a0000," + 
					"                                   b0269," + 
					"                                   a0225," + 
					"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
					"                              from a02, b01" + 
					"                             where a02.a0201b = b01.b0111" + 
					"                               and a0281 = 'true'" + 
					"                               and a0201b like '"+checkedgroupid+"%') t" + 
					"                     where rn = 1" + 
					"                       and t.a0000 = a01.a0000))) a");
		}else {
			str.append("select a.a0000,a.a0101,a.a0107,a.a0192a," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='政治素质考核材料' ) cl01," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='干部提任考察材料' ) cl02," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='平时考核表现材料' ) cl03," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='年度考核' ) cl04," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='民主生活会材料' ) cl05," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='整改清单' ) cl06," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='向组织说说心里话' ) cl07," + 
					" (select count(1) from wjgl where wjgl.a0000=a.a0000 and type='2' and wj02='其他' ) cl08 "+
					"  from (select a01.a0000, a01.a0101, decode(substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2),'.',null,substr(a01.a0107, 0, 4) || '.' || substr(a01.a0107, 5, 2)) a0107," + 
					"       a0192a" + 
					"          from A01 a01" + 
					"         where 1 = 1" + 
					"           and fkly is null" + 
					"           and a01.status != '4'" + 
					"           and a0163 like '1%'" + 
					"           and a01.a0000 in" + 
					"               (select a02.a0000" + 
					"                  from a02" + 
					"                 where a02.A0201B in" + 
					"                       (select cu.b0111" + 
					"                          from competence_userdept cu" + 
					"                         where cu.userid = '"+SysManagerUtils.getUserId()+"')" + 
					"                   and a0281 = 'true'" + 
					"                   and a02.a0201b = '"+checkedgroupid+"'" + 
					"                   )" + 
					"         order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
					"                      from (select a02.a0000," + 
					"                                   b0269," + 
					"                                   a0225," + 
					"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
					"                              from a02, b01" + 
					"                             where a02.a0201b = b01.b0111" + 
					"                               and a0281 = 'true'" + 
					"                               and a0201b ='"+checkedgroupid+"') t" + 
					"                     where rn = 1" + 
					"                       and t.a0000 = a01.a0000))) a");
		}
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("ryGrid.rowclick")
	@GridDataRange
	public int persongridOnRowClick() throws RadowException{  //打开窗口的实例
		String a0000 = (String)this.getPageElement("ryGrid").getValueList().get(this.getPageElement("ryGrid").getCueRowIndex()).get("a0000");
		this.getPageElement("a0000").setValue(a0000);
		this.setNextEventName("clGrid.dogridquery");
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
	
	@PageEvent("dwGrid.dogridquery")
	public int dodwQuery(int start,int limit) throws RadowException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String sql = "select wj00,w.type,wj01,a0000,b0111,w.year,wj02,wj03,wj04,aaa005 || '/' || wj05 wj05,wj06,add00 " + 
				"from WJGL w,aa01 t where  t.aaa001='HZB_PATH' and w.b0111='"+checkedgroupid+"'  and w.type='1' order by w.year desc ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	


	
	
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String a0000=this.getPageElement("a0000").getValue();
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		if("1".equals(isContain)) {
			this.setMainMessage("上传文件请勿勾选包含下级，防止人员单位储存错误");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a0000==null || "".equals(a0000) || checkedgroupid==null || "".equals(checkedgroupid)) {
			this.setMainMessage("请选择单位以及人员后上传！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode(" $h.openWin('CJWJGLAdd', 'pages.zwzc.CJWJGLAdd', '个人材料添加', 520, 350, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close',a0000:'"+a0000+"' ,b0111:'"+checkedgroupid+"'})" );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("update1")
	public int update1() throws RadowException, Exception {
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		String train_id = this.getPageElement("train_id").getValue();
		if(train_id==null || "".equals(train_id)) {
			this.setMainMessage("请选择材料后修改！");
		}else {
			this.getExecuteSG().addExecuteCode(" $h.openWin('CJWJGLdw', 'pages.zwzc.CJWJGLdw', '单位材料修改', 520, 350, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close' ,b0111:'"+checkedgroupid+"',train_id:'"+train_id+"'})" );
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("insert1")
	public int insert1() throws RadowException, Exception {
		String isContain = this.getPageElement("isContain").getValue();
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String ctxPath = request.getContextPath();
		if("1".equals(isContain)) {
			this.setMainMessage("上传文件请勿勾选包含下级，防止单位储存错误");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(checkedgroupid==null || "".equals(checkedgroupid)) {
			this.setMainMessage("请选择单位后上传！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getExecuteSG().addExecuteCode(" $h.openWin('CJWJGLdw', 'pages.zwzc.CJWJGLdw', '单位材料添加', 520, 350, null, ctxPath, null, { maximizable: false,resizable: false,closeAction: 'close' ,b0111:'"+checkedgroupid+"'})" );
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
			this.setNextEventName("ryGrid.dogridquery");
			this.setNextEventName("clGrid.dogridquery");
			this.setNextEventName("dwGrid.dogridquery");
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
	
	@PageEvent("ryGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("ryGrid").getValue("a0000",this.getPageElement("ryGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	
	@PageEvent("dwGrid.rowclick")
	@GridDataRange
	public int persongridDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String wj00=this.getPageElement("dwGrid").getValue("wj00",this.getPageElement("dwGrid").getCueRowIndex()).toString();
		if(wj00!=null){
			this.getPageElement("train_id").setValue(wj00);
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}

	
}