package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.zhgb.ZHGBDataOrgExpNewThread;

public class DataOrgImpUni2PageModel extends PageModel {
	
	
	
	@PageEvent("expbtn.onclick")
	public int expbtn(String name) throws RadowException{
		String tabimp = this.getPageElement("tabimp").getValue();
		try {
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){
				String gjgs = this.getPageElement("gjgs").getValue();
				if (gjgs!=null && gjgs.equals("1")) {
					expbtn2(name);
				} else{
					this.setMainMessage("数据不全");
				}
			}
//			HBSession sess = HBUtil.getHBSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void expbtn2(String name) throws RadowException {
		try {
			String id = UUID.randomUUID().toString().replace("-", "");
			String tabimp = this.getPageElement("tabimp").getValue();
			String inx_type = this.getPageElement("inx_type").getValue();
			CommonQueryBS.systemOut(DateUtil.getTime());
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){
				String gjgs = this.getPageElement("gjgs").getValue();
				String fxzry = this.getPageElement("fxzry").getValue();
				
				String zdcjg = this.getPageElement("zdcjg").getValue();
				String gzlbry = this.getPageElement("gzlbry").getValue();
				String gllbry = this.getPageElement("gllbry").getValue();
				String searchDeptid = this.getPageElement("searchDeptid").getValue();
				String linkpsn = this.getPageElement("linkpsn").getValue();
				String linktel = this.getPageElement("linktel").getValue();
				String remark = this.getPageElement("remark").getValue();
				
				String sign = inx_type + ".ZDY";
				if(inx_type == null || "".equals(inx_type.trim())){
					this.setMainMessage("交换格式不能为空!");
					return ;
				}
				if(searchDeptid == null || "".equals(searchDeptid.trim())){
					this.setMainMessage("机构不能为空!");
					return ;
				}
				String B0114Sql = "SELECT B01.B0114 FROM B01 WHERE B01.B0111 = '"+searchDeptid+"'";
				Object b0114 = HBUtil.getHBSession().createSQLQuery(B0114Sql).uniqueResult();
				if(b0114==null || "".equals(b0114.toString().trim())){
					this.setMainMessage("导出机构的机构编码不能为空!");
					return ;
				}
				
				String gz_lb = "";
				String gl_lb = "";
				List list = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
						" codeType='ZB125' order by codeValue").list();
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						CodeValue code = (CodeValue) list.get(i);
						int k = i + 1;
						String gzlb="gz_"+k;	
						String gl_index = this.getPageElement(gzlb).getValue();
						gz_lb = gz_lb +(gl_index.equals("0")?"": "'" + code.getCodeValue()+"',");
					}
				}
				List list2 = HBUtil.getHBSession().createQuery(" from CodeValue t where " +
						" codeType='ZB130' order by codeValue").list();
				if(list2!=null && list2.size()>0){
					for(int i=0;i<list2.size();i++){
						CodeValue code = (CodeValue) list2.get(i);
						int k = i + 1;
						String gllb="gl_"+k;	
						String gl_index = this.getPageElement(gllb).getValue();
						gl_lb = gl_lb +(gl_index.equals("0")?"": "'" +code.getCodeValue()+"',");
					}
				}
				
				if(zdcjg.equals("0")){
					if(gz_lb.equals("")){
						this.setMainMessage("至少设置一个工作人员类别!");
						return ;
					}
					if(gl_lb.equals("")){
						this.setMainMessage("至少设置一个管理人员类别!");
						return ;
					}
				}
				
				
				CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
				KingbsconfigBS.saveImpDetailInit3(id);
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				ZHGBDataOrgExpNewThread thr = new ZHGBDataOrgExpNewThread(id, user,gjgs,fxzry,gzlbry,gllbry,searchDeptid,
						linkpsn,linktel,remark,gz_lb,gl_lb,tabimp,zdcjg,userVo,sign); 
				new Thread(thr,"数据导出线程1").start();
				this.setRadow_parent_data(id);
				this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出详情',600,300,'"+id+"','"+request.getContextPath()+"');");
				this.getExecuteSG().addExecuteCode("parent.Ext.getCmp(subWinId).close(); ");
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	

	


	@PageEvent("searchDeptBtn.ontriggerclick")
	@NoRequiredValidate
	public int searchDept(String name) throws RadowException{
		this.openWindow("deptWin", "pages.dataverify.DeptWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX(){
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	
	

}
