package com.insigma.siis.local.pagemodel.dataverify;

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

public class DataExcelExportZzbPageModel extends PageModel{

	public DataExcelExportZzbPageModel() {
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
				this.setMainMessage("��¼��ʱ�������µ�¼!");
				return EventRtnType.NORMAL_SUCCESS;
			}
//			String fxzry = this.getPageElement("fxzry").getValue();//����ְ��Ա
//			String zdcjg = this.getPageElement("zdcjg").getValue();//ֻ����������Ϣ
//			String gzlbry = this.getPageElement("gzlbry").getValue();//���й�����Ա���
//			String gllbry = this.getPageElement("gllbry").getValue();//���й�����Ա���
//			String searchDeptid = this.getPageElement("searchDeptid").getValue();//����id
//			String linkpsn = this.getPageElement("linkpsn").getValue();//��ϵ��
//			String linktel = this.getPageElement("linktel").getValue();//��ϵ�绰
			String fxzry = request.getParameter("fxzry");//����ְ��Ա
			String zdcjg = request.getParameter("zdcjg"); //ֻ����������Ϣ
			String gzlbry =  request.getParameter("gzlbry");//���й�����Ա���
			String gllbry =  request.getParameter("gllbry");//���й�����Ա���
			String searchDeptid = request.getParameter("searchDeptid");//����id
			String linkpsn =  request.getParameter("linkpsn");//��ϵ��
			String linktel =  request.getParameter("linktel");//��ϵ�绰
			
			 exptemplate =  request.getParameter("exptemplate");
			String exptunit =  request.getParameter("exptunit");
			String expsy =  request.getParameter("expsy");
			String exppicture =  request.getParameter("exppicture");
			
			/*������ѡ*/
//			if(searchDeptid == null || "".equals(searchDeptid.trim())){
//				this.setMainMessage("��ѡ�����!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			/*����Ϊ��У��*/
//			String B0114Sql = "SELECT B01.B0114 FROM B01 WHERE B01.B0111 = '"+searchDeptid+"'";
//			Object b0114 = HBUtil.getHBSession().createSQLQuery(B0114Sql).uniqueResult();
//			if(b0114==null || "".equals(b0114.toString().trim())){
//				this.setMainMessage("���������Ļ������벻��Ϊ��!");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			
			/*������Ա���  ������Ա��� ��ѡ*/
			String gz_lb = "";//������Ա���
			String gl_lb = "";//������Ա���
//			if("0".equals(zdcjg)){//������������Ա��Ϣ
//				@SuppressWarnings("unchecked")
//				List<CodeValue> list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
//						" codeType='ZB125' order by codeValue").list();
//				if(list!=null && list.size()>0){
//					for(int i=0;i<list.size();i++){
//						CodeValue code = (CodeValue) list.get(i);
//						int k = i + 1;
//						String gzlb="gz_"+k;	
//						String gl_index = this.getPageElement(gzlb).getValue();
//						gz_lb = gz_lb +(gl_index.equals("0")?"": "'" + code.getCodeValue()+"',");
//					}
//				}
//				@SuppressWarnings("unchecked")
//				List<CodeValue> list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
//						" codeType='ZB130' order by codeValue").list();
//				if(list2!=null && list2.size()>0){
//					for(int i=0;i<list2.size();i++){
//						CodeValue code = (CodeValue) list2.get(i);
//						int k = i + 1;
//						String gllb="gl_"+k;	
//						String gl_index = this.getPageElement(gllb).getValue();
//						gl_lb = gl_lb +(gl_index.equals("0")?"": "'" +code.getCodeValue()+"',");
//					}
//				}
//				if(gz_lb.equals("")){
//					this.setMainMessage("��������һ��������Ա���!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//				if(gl_lb.equals("")){
//					this.setMainMessage("��������һ��������Ա���!");
//					return EventRtnType.NORMAL_SUCCESS;
//				}
//			}
			uuid = UUID.randomUUID().toString().replace("-", "");
			 user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			//KingbsconfigBS.saveImpDetailInit3(uuid);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			
			String ss=File.separatorChar+"";
			//String mbpath1=this.request.getSession().getServletContext().getRealPath("/")+"template\\excel\\Data.xlsx";
			String mbpath1=this.request.getSession().getServletContext().getRealPath("/")+"template\\excel\\Data.xls";
			mbpath1=mbpath1.replace("\\", ss);
			mbpath1=mbpath1.replace("/", ss);
			
			
			//this.getExecuteSG().addExecuteCode("parent.Ext.getCmp(subWinId).close(); ");
			DataExcelExportZzbThread thr = new DataExcelExportZzbThread(mbpath1,uuid, user,fxzry,gzlbry,gllbry,searchDeptid,
					linkpsn,linktel,gz_lb,gl_lb,zdcjg,userVo,exptemplate,exptunit,expsy,exppicture); 
			sourcePath=thr.start();
			if("false".equals(exptemplate)){//������ģ��
				name=sourcePath.substring(sourcePath.lastIndexOf("/")+1);
				orgname=HBUtil.getValueFromTab("b0101", "b01", "b0111='"+searchDeptid+"' ");
				String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101,zipfile) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','�������','"+orgname+"','"+sourcePath+"')";
				sess.createSQLQuery(sql1).executeUpdate();
				sess.flush();
			}
			this.setSelfDefResData("2@@@"+sourcePath);
			//this.setRadow_parent_data(uuid);
			//this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','��������',600,300,'"+uuid+"','"+request.getContextPath()+"');");
			
		}catch(Exception e){
			e.printStackTrace();
			try{
				if("false".equals(exptemplate)){//������ģ��
					uuid = UUID.randomUUID().toString().replace("-", "");
					String sql1 = "insert into EXPINFO (ID,NAME,STARTTIME,CREATEUSER,STATUS,B0101,zipfile) values ('"+uuid+"','"+name+"','"+time+"','"+user.getId()+"','�ļ�����ʧ��!','"+orgname+"','"+sourcePath+"')";
					sess.createSQLQuery(sql1).executeUpdate();
					sess.flush();
				}
				this.setSelfDefResData("1@@@"+"����ʧ��!("+e.getMessage()+")");
			}catch(Exception e1){
				
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
