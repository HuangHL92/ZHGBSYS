package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.lrmx.ExpRar;

public class Appointmentto_newPageModel extends PageModel{


	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
//		this.getExecuteSG().addExecuteCode("showdata()");
		return 0;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		this.setNextEventName("templateInfoGrid1.dogridquery");
//		this.setNextEventName("templateInfoGrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;

	}
	@PageEvent("templateInfoGrid1.dogridquery")
	public int doMemberQuery13(int start,int limit) throws RadowException, SQLException{
//		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind in('2') group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		String sql = "select t.tpid, t.tpname, t.tptype, t.tpkind             "+
				"  from listoutput2 t                                    "+
				" where t.tptype = '1'                                   "+
				" group by t.tpid, t.tpname, t.tptype, t.tpkind, t.pxid  "+
				" order by t.pxid                                        ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("templateInfoGrid2.dogridquery")
	public int doMemberQuery14(int start,int limit) throws RadowException, SQLException{
//		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype='2' and t.tpkind='1' group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid and t.tptype != '1'  group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind,t.tpname";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	@PageEvent("expPdf")
	public int expPdf(String tpid)throws AppException, RadowException, IOException{
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			List<String> pdfPaths = null;
			try {
				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"pdf",ids, pdfPaths, personids);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
			QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
			newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
			newPDFPath = "/hzb"+ newPDFPath;
			this.getPageElement("pdfPath").setValue(newPDFPath);
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
//			this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+newPDFPath+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("expWord")
	public int expWord(String tpid)throws AppException, RadowException, IOException{
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			List<String> wordPaths = null;
			try {
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids, wordPaths, personids);
				String zipPath = wordPaths.get(0);
				String infile = "";
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
						infile = zipPath + "公务员登记备案表.doc" ;
			        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
			        	infile = zipPath + "参照登记备案表.doc" ;
			        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
			        	infile = zipPath + "干部花名册（一人一行）.doc" ;
			        }else{
			        	infile = zipPath + "干部花名册（按机构分组）.doc" ;
			        }
				}else{
					infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
				    SevenZipUtil.zip7z(zipPath, infile, null);
				}
				
			    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
//	@PageEvent("templateInfoGrid1.rowdbclick")
	@GridDataRange
	public int dbClick1(String id) throws RadowException{
		int i = this.getPageElement("templateInfoGrid1").getCueRowIndex();
		String tpid=this.getPageElement("templateInfoGrid1").getValue("tpid",i).toString();
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
		}else{
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWinzh7','pages.publicServantManage.Appointmentzh','数据展示',1200,900,'"+tpid+"','"+ctxPath+"');");
//			String[] personids = ids.split("@");
//			List<String> list2 = new ArrayList<String>();
//			for(int j = 0; j < personids.length; j++){
//				String a0000 = personids[j].replace("|", "");
//				list2.add(a0000);
//			}
//			List<String> pdfPaths = null;
//			try {
//				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid);
//			} catch (AppException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
//			QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
//			newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
//			newPDFPath = "/hzb"+ newPDFPath;
//			this.getPageElement("pdfPath").setValue(newPDFPath);
//			String ctxPath = request.getContextPath();
//			this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
			//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+newPDFPath+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("templateInfoGrid2.rowdbclick")
	@GridDataRange
	public int dbClick2(String id) throws RadowException{
		int i = this.getPageElement("templateInfoGrid2").getCueRowIndex();
		String tpid=this.getPageElement("templateInfoGrid2").getValue("tpid",i).toString();
		String a0000 = request.getParameter("initParams");
		if("allperson".equals(a0000)){
			StringBuffer ids = new StringBuffer();
			String allSelect = (String)this.request.getSession().getAttribute("allSelect");
			String newsql = allSelect.replace("*", "a0000");
			List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
			if (allPeople != null && allPeople.size() > 0) {
				for(Object sa0000 : allPeople){
					ids.append("|").append(sa0000).append("|@");
				}
			}
			a0000 = ids.substring(0, ids.length() - 1);
		}
		this.request.getSession().setAttribute("tpid", tpid);
		this.request.getSession().setAttribute("personids",a0000);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin7','pages.publicServantManage.OtherTemShow','自定义表格',1200,900,'R','"+ctxPath+"');");
		///closeCueWindow("alertWin");
		//return EventRtnType.SPE_SUCCESS;
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
	this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}
	
	
	


}
