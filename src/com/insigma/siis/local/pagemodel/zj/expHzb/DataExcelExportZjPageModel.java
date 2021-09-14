package com.insigma.siis.local.pagemodel.zj.expHzb;

import java.io.File;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;

public class DataExcelExportZjPageModel extends PageModel{

	public DataExcelExportZjPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		this.getPageElement("exptunit").setValue("1");
		this.getPageElement("expsy").setValue("1");
		this.getPageElement("exppicture").setValue("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("expbtn_onclick")
	public int expData() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		String uuid = "";
		String time = "";
		CurrentUser user = null;
		String name="";
		String orgname="";
		String sourcePath="";
		String exptemplate ="";
		try{
			time = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
			CurrentUser  currentuser  =SysUtil.getCacheCurrentUser();
			if(currentuser==null){
				this.setMainMessage("登录超时，请重新登录!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String fxzry = request.getParameter("fxzry");//非现职人员
			String zdcjg = request.getParameter("zdcjg"); //只导出机构信息
			String gzlbry =  request.getParameter("gzlbry");//所有工作人员类别
			String gllbry =  request.getParameter("gllbry");//所有管理人员类别
			String searchDeptid = request.getParameter("searchDeptid");//机构id
			String linkpsn =  request.getParameter("linkpsn");//联系人
			String linktel =  request.getParameter("linktel");//联系电话
			
			 exptemplate =  request.getParameter("exptemplate");
			String exptunit =  request.getParameter("exptunit");
			String expsy =  request.getParameter("expsy");
			String exppicture =  request.getParameter("exppicture");
			
			/*工作人员类别  管理人员类别 必选*/
			String gz_lb = "";//工作人员类别
			String gl_lb = "";//管理人员类别
			uuid = UUID.randomUUID().toString().replace("-", "");
			user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			
			String ss=File.separatorChar+"";
			String mbpath1=this.request.getSession().getServletContext().getRealPath("/")+"template\\excel\\Data.xls";
			mbpath1=mbpath1.replace("\\", ss);
			mbpath1=mbpath1.replace("/", ss);

			ZjDataExcelExportThread thr = new ZjDataExcelExportThread(mbpath1,uuid, user,fxzry,gzlbry,gllbry,searchDeptid,
					linkpsn,linktel,gz_lb,gl_lb,zdcjg,userVo,exptemplate,exptunit,expsy,exppicture); 
			sourcePath=thr.start();
			if("false".equals(exptemplate)){//不导出模板
				name=sourcePath.substring(sourcePath.lastIndexOf("/")+1);
				orgname=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+searchDeptid+"' ");
				String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101,zipfile) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','导出完成','"+orgname+"','"+sourcePath+"')";
				sess.createSQLQuery(sql1).executeUpdate();
				sess.flush();
			}
			this.setSelfDefResData("2@@@"+sourcePath);
			
		}catch(Exception e){
			e.printStackTrace();
			try{
				if("false".equals(exptemplate)){//不导出模板
					uuid = UUID.randomUUID().toString().replace("-", "");
					String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101,zipfile) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','文件导出失败!','"+orgname+"','"+sourcePath+"')";
					sess.createSQLQuery(sql1).executeUpdate();
					sess.flush();
				}
				this.setSelfDefResData("1@@@"+"导出失败!("+e.getMessage()+")");
			}catch(Exception e1){
				
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
