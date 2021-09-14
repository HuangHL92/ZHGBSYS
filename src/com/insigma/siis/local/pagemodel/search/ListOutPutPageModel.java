package com.insigma.siis.local.pagemodel.search;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;
import sun.net.www.http.Hurryable;

import org.hibernate.Session;

import com.fr.data.core.DataUtils;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.entity.SmtUserselfcolumn;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.listoutput;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysorg.org.OrgNodeTree;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
//import com.insigma.siis.local.business.sysorg.SysOrgBs;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.publicServantManage.CreateTemplatePageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.xml.sax.InputSource;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * @author lixy
 *
 */
public class ListOutPutPageModel extends PageModel {

	public static String tpid="";
	/**
	 * 系统区域信息
	 */
	
	public ListOutPutPageModel(){
		
	}

	@Override
	public int doInit() {
		this.request.getSession().setAttribute("opentype", "0");
		this.setNextEventName("templateInfoGrid.dogridquery");
		this.setNextEventName("templateInfoGrid1.dogridquery");
		this.setNextEventName("templateInfoGrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		
		String listString=null;
		String tpnameString = null;
		int cnt=0;
		PageElement pe = this.getPageElement("templateInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+"@|"+hm.get("tpid")+"|";
				tpnameString = tpnameString+"@|"+hm.get("tpname")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
		if(!"".equals(tpnameString)&&tpnameString!=null)
			tpnameString=tpnameString.substring(tpnameString.indexOf("@")+1,tpnameString.length());
		this.getPageElement("BZtemCount").setValue(cnt+"");
 		this.getPageElement("BZtemID").setValue(listString);
 		this.getPageElement("BZtemName").setValue(tpnameString);
 		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList_excel")
	public int getCheckList_excel() throws RadowException, AppException{
		getCheckList();
		getCheckList1();
		getCheckList2();
		this.getExecuteSG().addExecuteCode("daochuexcel_1();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList_cll")
	public int getCheckList_cll() throws RadowException, AppException{
		getCheckList();
		getCheckList1();
		getCheckList2();
		this.getExecuteSG().addExecuteCode("daochucll_1();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList1_del")
	public int getCheckList1_del() throws RadowException, AppException{
		getCheckList1();
		this.getExecuteSG().addExecuteCode("delTP2();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("getCheckList1")
	public int getCheckList1() throws RadowException, AppException{
		String listString=null;
		String tpnameString = null;
		int cnt=0;
		PageElement pe = this.getPageElement("templateInfoGrid1");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck1"))&&(Boolean) hm.get("personcheck1")){
				listString=listString+"@|"+hm.get("tpid")+"|";
				tpnameString = tpnameString+"@|"+hm.get("tpname")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
		if(!"".equals(tpnameString)&&tpnameString!=null)
			tpnameString=tpnameString.substring(tpnameString.indexOf("@")+1,tpnameString.length());
		this.getPageElement("ZDYtemCount").setValue(cnt+"");
 		this.getPageElement("ZDYtemID").setValue(listString);
 		this.getPageElement("ZDYtemName").setValue(tpnameString);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryByName")
	public int queryByName(String name) throws RadowException, AppException{
		this.getPageElement("typename").setValue(name);
		this.setNextEventName("templateInfoGrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 共享模板用
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("getCheckList2")
	public int getCheckList2() throws RadowException, AppException{
		String listString=null;
		String tpnameString = null;
		int cnt=0;
		PageElement pe = this.getPageElement("templateInfoGrid2");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				listString=listString+"@|"+hm.get("tpid")+"|";
				tpnameString = tpnameString+"@|"+hm.get("tpname")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
		if(!"".equals(tpnameString)&&tpnameString!=null)
			tpnameString=tpnameString.substring(tpnameString.indexOf("@")+1,tpnameString.length());
		this.getPageElement("GXYtemCount").setValue(cnt+"");
		this.getPageElement("GXYtemID").setValue(listString);
 		this.getPageElement("GXYtemName").setValue(tpnameString);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("personsort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String pageInfo)throws RadowException{
		String[] pfs = pageInfo.split(",");
		int pn = Integer.valueOf(pfs[1]);
		int pSize = Integer.valueOf(pfs[0]);
		
		List<HashMap<String,String>> list = this.getPageElement("templateInfoGrid1").getStringValueList();
		int page = list.size();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0;
			if(pn>1){
				i = pSize*pn;
			}
			for(HashMap<String,String> m : list){
				String tpid = m.get("tpid");
				HBUtil.executeUpdate("update listoutput set sortid="+i+" where tpid='"+tpid+"'");
				i++;
			}
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//复制
	@NoRequiredValidate
	@PageEvent("copyType")
	public int copyType() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			getCheckList();
			getCheckList1();
			getCheckList2();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bzcount = this.getPageElement("BZtemCount").getValue();
		if("".equals(bzcount))
			bzcount = "0";
		String zdycount = this.getPageElement("ZDYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String gxycount = this.getPageElement("GXYtemCount").getValue();
		if("".equals(zdycount))
			gxycount = "0";
		String bzid = this.getPageElement("BZtemID").getValue();
		String zdyid = this.getPageElement("ZDYtemID").getValue();
		String gxyid = this.getPageElement("GXYtemID").getValue();
		if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
			this.setMainMessage("请选择模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("不可以同时复制多张模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!StringUtil.isEmpty(bzid)){
			//复制标准模板
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_副本','2',messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,typestate from listoutput where tpid = "+bzid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}else if(!StringUtil.isEmpty(zdyid)){
			//复制自定义模板
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_副本',tptype,messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,typestate from listoutput where tpid = "+zdyid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}else{
			//复制共享模板
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_副本','2',messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,'1' from listoutput where tpid = "+gxyid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}
		this.setMainMessage("复制完成");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("createNewtem")
	public int imgVerify() throws RadowException {
		try {
			getCheckList();
			getCheckList1();
			getCheckList2();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String cztype = this.getPageElement("cztype").getValue();
		String bzcount = this.getPageElement("BZtemCount").getValue();
		if("".equals(bzcount))
			bzcount = "0";
		String zdycount = this.getPageElement("ZDYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String gxycount = this.getPageElement("GXYtemCount").getValue();
		if("".equals(zdycount))
			gxycount = "0";
		String bzid = this.getPageElement("BZtemID").getValue();
		String zdyid = this.getPageElement("ZDYtemID").getValue();
		String gxyid = this.getPageElement("GXYtemID").getValue();
		String bztpname = this.getPageElement("BZtemName").getValue();
		String zdytpname = this.getPageElement("ZDYtemName").getValue();
		String gxytpname = this.getPageElement("GXYtemName").getValue();
		String title = "";
		if("2".equals(cztype)){
			if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
				this.setMainMessage("请选择模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){//
				this.setMainMessage("不可以同时编辑多张模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(gxycount)&&!"0".equals(gxycount)){
				this.setMainMessage("共享模板不能编辑！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(bzcount)&&!"0".equals(bzcount)){
				this.setMainMessage("标准模板不能编辑！");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				title = "编辑模板";
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", zdytpname.substring(1, zdytpname.length()-1));
				this.request.getSession().setAttribute("tpid", zdyid);
				this.request.getSession().setAttribute("isedit", "1");
				this.request.getSession().setAttribute("temtype","");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}
		}else if("1".equals(cztype)){//建同类表
			if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
				this.setMainMessage("请选择模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
				this.setMainMessage("不可以同时建立多张模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(("0".equals(bzcount)||"".equals(bzcount)) && "1".equals(zdycount) && ("0".equals(gxycount) || "".equals(gxycount))){
				title = "建同类表";
				String namehouzhui  = zdytpname.substring(1, zdytpname.length()-1);
				System.out.println("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind");
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", zdytpname.substring(1, zdytpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("【"), (namehouzhui.indexOf("】")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","2");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}else if(("0".equals(zdycount) || "".equals(zdycount)) &&"1".equals(bzcount) && ("0".equals(gxycount) ||"".equals(gxycount))){//建立标准模板同类表
				title = "建同类表";
				String namehouzhui  = bztpname.substring(1, bztpname.length()-1);
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+bztpname.substring(1, bztpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", bztpname.substring(1, bztpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("【"), (namehouzhui.indexOf("】")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","1");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}else{
				title = "建同类表";
				String namehouzhui  = gxytpname.substring(1, gxytpname.length()-1);
				System.out.println("select t.tpkind from listoutput2 t where t.tpid='"+gxyid.substring(1, gxyid.length()-1)+"' group by t.tpkind");
//				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput t where t.tpname='"+gxytpname.substring(1, gxytpname.length()-1)+"' group by t.tpkind").list();
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpid='"+gxyid.substring(1, gxyid.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", gxytpname.substring(1, gxytpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("【"), (namehouzhui.indexOf("】")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","2");///路径问题没有解决,查询不能用名称?
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}
			//----------------------wuh-------------------------
			String tpname = this.request.getSession().getAttribute("tpname").toString();
			this.getPageElement("isedit").setValue((this.request.getSession().getAttribute("isedit")).toString());
//			System.out.println((this.request.getSession().getAttribute("temtype")).toString());
			this.getPageElement("temtype").setValue((this.request.getSession().getAttribute("temtype")).toString());
			if(!"1".equals((this.request.getSession().getAttribute("temtype")).toString())){
				String sql = "select tpid from listoutput where tpname='"+tpname+"' group by tpid";
				try{
					ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
					while(rs.next()){
						this.getPageElement("tpid").setValue(rs.getString(1));
						this.getPageElement("tpname").setValue(tpname+"_副本");
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}else{
				this.getPageElement("tpname").setValue(tpname);
			}
			this.getPageElement("tpkind").setValue((this.request.getSession().getAttribute("tpkind")).toString());
			this.getExecuteSG().addExecuteCode("pageinit();");
			this.setMainMessage("复制成功");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
			return EventRtnType.NORMAL_SUCCESS;
		}else if("0".equals(cztype)){
			title = "新建模板-表格";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","【表格】");
			this.request.getSession().setAttribute("tpkind","1");
		}else if("3".equals(cztype)){
			title = "新建模板-标准名册";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","【标准名册】");
			this.request.getSession().setAttribute("tpkind","2");
			this.request.getSession().setAttribute("isphoto","isphoto2");
		}else if("print".equals(cztype)){
			if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
				this.setMainMessage("请选择模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){//
				this.setMainMessage("不可以同时打印多张模板！");
				return EventRtnType.NORMAL_SUCCESS;
			}/*else if(!"".equals(gxycount)&&!"0".equals(gxycount)){
				this.setMainMessage("共享模板选中后在“建同类表”中打印！");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(zdycount)&&!"0".equals(zdycount)){
				this.setMainMessage("自定义模板请在“编辑模板”中打印！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			String id = bzid;
			if(id==null||"".equals(id)){
				id=zdyid;
			}
			if(id==null||"".equals(id)){
				id=gxyid;
			}
			this.request.getSession().setAttribute("print", "print");
			id = id.substring(1, id.length()-1);
			this.request.getSession().setAttribute("tpid",id);
			this.request.getSession().setAttribute("personids","1 and 1=2 ");
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin6','pages.publicServantManage.OtherTemShow','打印',1000,900,'R','"+ctxPath+"');");
//			this.getExecuteSG().addExecuteCode("addTab('打印','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			title = "新建模板-花名册";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","【花名册】");
			this.request.getSession().setAttribute("tpkind","3");
			this.request.getSession().setAttribute("isphoto","isphoto");//用来隐藏指定的行列。在createTemplate.jsp
		}
//		this.getExecuteSG().addExecuteCode("addTab('"+title+"','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.CreateTemplate',true,false)");
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin6','pages.publicServantManage.CreateTemplate','"+title+"',1200,900,'R','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("templateInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput2 t where t.tptype = '1' group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
        this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("templateInfoGrid1.dogridquery")
	public int doMemberQuery1(int start,int limit) throws RadowException{
//		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput t,user_template ut where (t.tptype = '2' or t.tptype = '3') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind,t.typestate,t.sortid from listoutput2 t,user_template ut where ut.tpid in (select tpid from USER_TEMPLATE where userid = '"+SysManagerUtils.getUserId()+"') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid group by t.tpid,t.tpname,t.tptype,t.tpkind,t.typestate,t.sortid order by t.sortid,t.tpkind,t.tpname";
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("templateInfoGrid2.dogridquery")
	public int doMemberQuery21(int start,int limit) throws RadowException, SQLException{
//		String  modelid = "";
//		String  modelid1 = "";
//		String sql2 = "select modelid from powergx where userid = '"+SysManagerUtils.getUserId()+"'  or owenr = '"+SysManagerUtils.getUserId()+"' group by modelid   ";
//		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql2);
//		while(re.next()){
//			modelid += "'"+ re.getString(1) +"'"+",";
//		}
//		if(modelid.length()>0){
//			modelid = modelid.substring(0, modelid.length()-1);
//			modelid1 = "t.tpid in ("+modelid+")";
//		}else{
//			modelid1 = " 1=2 ";
//		}
//		String sql = "select x.uuid, t.tpid,t.tptype,x.tpname tpname,s.LOGINNAME as owenr from listoutput t,powergx x,SMT_USER s where  "+modelid1+" and x.tpname <> 'null' and x.modelid = t.tpid and s.USERID = x.OWENR group by x.uuid,t.tpid,x.tpname,t.tptype,s.LOGINNAME,t.tpkind order by t.tpkind";
		String name = this.getPageElement("typename").getValue();
		String sql = "";
		if(StringUtil.isEmpty(name)){
			sql = "select t.tpid, t.tpname, t.tptype, t.tpkind, t.typestate from listoutput2 t where t.typestate = '2' group by t.tpid, t.tpname, t.tptype, t.tpkind,t.typestate order by t.tpkind, t.tpname";
		}else{
			sql = "select t.tpid, t.tpname, t.tptype, t.tpkind, t.typestate from listoutput2 t where t.typestate = '2' and t.tpname = '"+name+"' group by t.tpid, t.tpname, t.tptype, t.tpkind,t.typestate order by t.tpkind, t.tpname";
		}
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	
	
	/**
	 * 共享模板
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("templateInfoGrids2.dogridquery")
	public int doMemberQuery2(int start,int limit) throws RadowException{
		String sql = "select t.tpid,t.tpname,t.tptype,t.tpkind from listoutput2 t,user_template ut where (t.tptype = '5') and ut.userid='"+SysManagerUtils.getUserId()+"' and t.tpid=ut.tpid group by t.tpid,t.tpname,t.tptype,t.tpkind order by t.tpkind";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	/*
	 * 多行解析
	 * xmlDoc 传入的xml字符串参数
	 * TPname   模板名称
	 * TPtype  模板类型
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String xmlDoc) throws RadowException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String time = sf.format(date);
        time = time.substring(0,time.length()-1);
//		System.out.println(xmlDoc);
		//创建一个新的字符串
		StringReader read = new StringReader(xmlDoc);
		//创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		//创建一个新的SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		//一个格子由三个map确定，k（1行，2列，3内容）v（行标，列表，内容）
		//Map<String, String> map = new HashMap<String, String>();
		try {
			//通过输入源构造一个Document
			Document doc = sb.build(source);
			//取的根元素
			Element root = doc.getRootElement();
			//输出根元素的名称（测试）
//			System.out.println(root.getName());
			//得到根元素所有子元素的集合
			List jiedian = root.getChildren();
			Namespace ns = root.getNamespace();
			int n = 0;
			n = jiedian.size();
			String uuid = UUID.randomUUID().toString();
			int p = 1;
			int a = 0;//控制删除的次数，在jiexi方法里面
			String TPname = this.getPageElement("tname").getValue();
			String houzhui = (String)request.getSession().getAttribute("namehouzhui");
			if(houzhui != null && (!TPname.contains("【表格】")&&!TPname.contains("【标准名册】")&&!TPname.contains("【花名册】"))){
				TPname += houzhui;
				request.getSession().removeAttribute("namehouzhui");
			}
			if(houzhui != null && "【花名册】".equals(houzhui)){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月");
				String date1 = sdf.format(cal.getTime());
				TPname = date1+TPname;
			}
			String TPname1 = this.getPageElement("tpname").getValue();
			String isedit = this.getPageElement("isedit").getValue();
			String tpkind = this.getPageElement("tpkind").getValue();
			if("1".equals(isedit)){
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput t where t.tpname='"+TPname1+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("模板名重复！请更改命名！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput2 t where t.tpname='"+TPname+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("模板名重复！请更改命名！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			for(int m = 0;m < jiedian.size()-1;m++ ){
				//list里面存放map
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				n--;
				Element et = null;
				//获得Worksheet节点
				et = (Element) jiedian.get(n);
				 //获取页数
	            String page = et.getAttributeValue("Name", ns);
	            String PageNu = n+"";
				//获得Worksheet节点下的子节点Table节点
				List Table = et.getChildren();
				//获取Table节点
				et = (Element) Table.get(0);
				//获取Table节点下的子节点row节点
				List row = et.getChildren();
				//定义连个变量拼接字符串
				String messageE = "";
				String MessageA = "";
				//如果row>0就说明有数据
				if (row.size() > 0) {
					//循环遍历row获取每一个row
					for (int i = 0; i < row.size(); i++) {
						//获取row节点
						et = (Element) row.get(i);
						//获取标签里面的内容
						String row1 = et.getAttributeValue("Index", ns);
						//1表示行
						//获取row节点下面的子节点
						List cell = et.getChildren();
						for (int j = 0; j < cell.size(); j++) {
							//获取cell节点
							et = (Element) cell.get(j);
							//获取标签里面的内容
							String cell1 = et.getAttributeValue("Index", ns);
							//2表示列
							//获取数据信息
							String message = et.getChild("Data", ns).getText();
							//判断内容是不是需要的保存的内容（<>内的数据是要保存到数据库的）
							if (message.contains("<")) {
								//获取的内容去掉最后的 >
								String Message1 = message.substring(message.indexOf("<"), message.lastIndexOf(">"));
								//按>进行截取
								String[] split1 = Message1.replace("<", "").split(">");
								for(int q = 0; q < split1.length ; q++){
									//遍历数组获取内容进行拼接字符串
									MessageA += split1[q]+",";
									//获取每一个信息项
									String Message = split1[q];
									//每一个信息项与定义好信息行进行匹配，获取去信息项英文之后拼接英文信息项
									
/*									if("姓名".equals(Message)){
										messageE += "a01.a0101,"; 
									}else if("民族（名）".equals(Message)){
										messageE += "mzm,";
									}else if("性别（名）".equals(Message)){
										messageE += "xbm,";
									}else if("人员状态".equals(Message)){
										messageE += "ryzt,";
									}else if("人员类别".equals(Message)){
										messageE += "a01.a0160,";
									}else if("身份证号".equals(Message)){
										messageE += "a01.a0184,";
									}else if("性别".equals(Message)){
										messageE += "a01.a0104a,";
									}else if("出生年月".equals(Message)){//??0k
										messageE += "csny,";
									}else if("出生日期".equals(Message)){
										messageE += "a01.a0107,";
									}else if("年龄".equals(Message)){//??ok
										messageE += "nl,";
									}else if("照片".equals(Message)){//??ok
										messageE += "zp,";
									}else if("民族".equals(Message)){   
										messageE += "a01.a0117a,";
									}else if("籍贯".equals(Message)){ 
										messageE += "a01.a0111a,";
									}else if("出生地".equals(Message)){
										messageE += "a01.a0114a,";
									}else if("第一党派".equals(Message)){
										messageE += "a01.a0141,";
									}else if("入党时间".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("第二党派".equals(Message)){
										messageE += "dedp,";
									}else if("第三党派".equals(Message)){
										messageE += "dsdp,";
									}else if("参加工作时间".equals(Message)){
										messageE += "a01.a0134,";
									}else if("健康状况".equals(Message)){
										messageE += "a01.a0128,";
									}else if("专业技术职务".equals(Message)){
										messageE += "a01.a0196,";
									}else if("熟悉专业有何专长".equals(Message)){
										messageE += "a01.a0187a,";
									}else if("学历".equals(Message)){//-----------------
										messageE += "zgxl,";
									}else if("毕业学校（学历）".equals(Message)){
										messageE += "zgxlbyxx,";
									}else if("所学专业（学历）".equals(Message)){
										messageE += "zgxlsxzy,";
									}else if("入学时间（学历）".equals(Message)){
										messageE += "zgxlrxsj,";
									}else if("毕业时间（学历）".equals(Message)){
										messageE += "zgxlbisj,";
									}else if("学位".equals(Message)){
										messageE += "zgxw,";
									}else if("毕业学校（学位）".equals(Message)){
										messageE += "zgxwbyxx,";
									}else if("所学专业（学位）".equals(Message)){
										messageE += "zgxwsxzy,";
									}else if("入学时间（学位）".equals(Message)){
										messageE += "zgxwrxsj,";
									}else if("毕业时间（学位）".equals(Message)){
										messageE += "zgxwbisj,";
									}else if("学历（全日制）".equals(Message)){//------OK
										messageE += "xlqrz,";
									}else if("入学时间（全日制）".equals(Message)){//??OK
										messageE += "rxsjqrz,";
									}else if("毕业时间（全日制）".equals(Message)){//??OK
										messageE += "bysjqrz,";
									}else if("学位（全日制）".equals(Message)){//OK
										messageE += "xwqrz,";
									}else if("毕业学校（全日制学历）".equals(Message)){//ok
										messageE += "xxjyxql,";
									}else if("毕业学校（全日制学位）".equals(Message)){//ok
										messageE +="xxjyxqw,";
									}else if("所学专业（全日制学历）".equals(Message)){//??ok
										messageE +="sxzyql,";
									}else if("所学专业（全日制学位）".equals(Message)){//??ok
										messageE +="sxzyqw,";
									}else if("学历（在职）".equals(Message)){//ok
										messageE +="xlzz,";
									}else if("入学时间（在职）".equals(Message)){//??ok
										messageE +="rxsjzz,";
									}else if("毕业时间（在职）".equals(Message)){//??ok
										messageE +="bysjzz,";
									}else if("学位（在职）".equals(Message)){//ok
										messageE +="xwzz,";
									}else if("毕业学校（在职学历）".equals(Message)){//??ok
										messageE +="xxjyxzl,";
									}else if("毕业学校（在职学位）".equals(Message)){//??ok
										messageE +="xxjyxzw,";
									}else if("所学专业（在职学历）".equals(Message)){//??ok
										messageE +="sxzyzl,";
									}else if("所学专业（在职学位）".equals(Message)){//??ok
										messageE +="sxzyzw,";
									}else if("全日制学历（任免表）".equals(Message)){//??ok
										messageE += "qrzxlrb,";
									}else if("全日制学历信息（任免表）".equals(Message)){//??ok
										messageE += "qrzxlxxrb,";
									}else if("全日制学位（任免表）".equals(Message)){//??ok
										messageE += "qrzxwrb,";
									}else if("全日制学位信息（任免表）".equals(Message)){//??ok
										messageE += "qrzxwxxrb,";
									}else if("在职学历（任免表）".equals(Message)){//ok
										messageE += "zzxlrb,";
									}else if("在职学历信息（任免表）".equals(Message)){//??ok
										messageE += "zzxixxrb,";
									}else if("在职学位（任免表）".equals(Message)){//ok
										messageE += "zzxwrb,";
									}else if("在职学位信息（任免表）".equals(Message)){//??ok
										messageE += "zzxwxxrb,";
									}else if("工作单位及职务（全）".equals(Message)){
										messageE += "a01.a0192a,";
									}else if("工作单位及职务（简）".equals(Message)){
										messageE += "a01.a0192,";
									}else if("统计关系所在单位".equals(Message)){
										messageE += "a01.a0195,";//
									}else if("机构外职务".equals(Message)){//??""
										messageE += "jgwpx,";//
									}else if("选拔任用方式".equals(Message)){
										messageE += "a02.a0247,";
									}else if("任职文号".equals(Message)){
										messageE += "a02.a0245,";
									}else if("是否领导职务".equals(Message)){
										messageE += "a02.a0219,";//
									}else if("机构名称".equals(Message)){
										messageE += "a02.a0201a,";
									}else if("职务名称".equals(Message)){
										messageE += "a02.a0216a,";
									}else if("现职务层次".equals(Message)){//?? o k
										messageE += "zwcc,";
									}else if("任职时间".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("任现职务层次时间".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("层次时间括号处理".equals(Message)){//??ok
										messageE += "ccsjkhcl,";//-------/
									}else if("职动类型".equals(Message)){
										messageE += "a02.a0251,";
									}else if("免职时间".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("基层工作经历年限".equals(Message)){
										messageE += "jcnx,";
									}else if("简历".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("奖惩情况".equals(Message)){
										messageE += "a01.a14z101,";
									}else if("年度考核结果".equals(Message)){ 
										messageE += "a01.a15z101,";
									}else if("备注".equals(Message)){
										messageE += "a01.a0180,";
									}else if("称谓（家庭成员）".equals(Message)){//??ok
										messageE += "cw,";
									}else if("姓名（家庭成员）".equals(Message)){//??ok
										messageE += "xm,";
									}else if("出生年月（家庭成员）".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("年龄（家庭成员）".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("政治面貌（家庭成员）".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("工作单位及职务（家庭成员）".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("进入本单位方式".equals(Message)){
										messageE += "a29.a2911,";
									}else if("进入本单位日期".equals(Message)){
										messageE += "a29.a2907,";
									}else if("原单位名称".equals(Message)){
										messageE += "a29.a2921a,";
									}else if("在原单位职务".equals(Message)){
										messageE += "a29.a2941,";
									}else if("在原单位职务层次".equals(Message)){
										messageE += "a29.a2944,";
									}else if("进入公务员队伍时间".equals(Message)){
										messageE += "a29.a2947,";
									}else if("公务员登记时间".equals(Message)){
										messageE += "a29.a2949,";
									}else if("拟任职务".equals(Message)){
										messageE += "a53.a5304,";
									}else if("拟免职务".equals(Message)){
										messageE += "a53.a5315,";
									}else if("任免理由".equals(Message)){
										messageE += "a53.a5317,";
									}else if("呈报单位".equals(Message)){
										messageE += "a53.a5319,";
									}else if("填表时间".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("填表人".equals(Message)){
										messageE += "a53.a5327,";
									}else if("计算年龄时间".equals(Message)){
										messageE += "a53.a5321,";
									}else if("退出管理方式".equals(Message)){
										messageE += "a30.a3001,";
									}else if("调往单位".equals(Message)){
										messageE += "a30.a3007a,";
									}else if("退出管理时间".equals(Message)){
										messageE += "a30.a3004,";
									}else if("退出单位".equals(Message)){
										messageE += "a01.orgid,";
									}else if("修改人".equals(Message)){
										messageE += "a01.xgr,";
									}else if("修改日期".equals(Message)){
										messageE += "a01.xgsj,";
									}else if("当前日期".equals(Message)){//??ok
										messageE += "dqsj,";//----
									}else if("当前用户名".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
*/								
									if("姓名".equals(Message)){
										messageE += "a0101-a01.a0101,"; 
									}else if("人员状态".equals(Message)){
										messageE += "a0163-select code_name from code_value where  code_type = 'ZB126' and code_value = (select a0163 from a01 where a0000 = 'id' ) ',";
									}else if("人员类别".equals(Message)){
										messageE += "a0160-select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='id',";
									}else if("身份证号".equals(Message)){
										messageE += "a0184-a01.a0184,";
									}else if("性别".equals(Message)){
										messageE += "a0104-select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='id' and cv.code_value=a.a0104,";
									}else if("出生年月".equals(Message)){//??0k
										messageE += "csny,";
									}else if("年龄".equals(Message)){//??ok
										messageE += "nl,";
									}else if("照片".equals(Message)){//??ok
										messageE += "zp,";
									}else if("民族".equals(Message)){   
										messageE += "a0117-select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='id' and cv.code_value=a.a0117,";
									}else if("籍贯".equals(Message)){ 
										messageE += "a0111a-a01.a0111a,";
									}else if("出生地".equals(Message)){
										messageE += "a0114a-a01.a0114a,";
									}else if("第一党派".equals(Message)){
										messageE += "a0141-select cv.code_name from a01 a01,code_value cv where a01.a0000 ='id' and cv.code_type='GB4762' and cv.code_value=a01.a0141,";
									}else if("入党时间".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("第二党派".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("第三党派".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("参加工作时间".equals(Message)){
										messageE += "a0134-a01.a0134,";
									}else if("健康状况".equals(Message)){
										messageE += "a0128-a01.a0128,";
									}else if("专业技术职务".equals(Message)){
										messageE += "a0196-a01.a0196,";
									}else if("熟悉专业有何专长".equals(Message)){
										messageE += "a0187a-a01.a0187a,";
									}else if("学历".equals(Message)){//-----------------
										messageE += "a0801a-select a08.a0801a from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("毕业学校（学历）".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("所学专业（学历）".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("入学时间（学历）".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("毕业时间（学历）".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("学位".equals(Message)){
										messageE += "a0901a-select a08.a0901a from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("毕业学校（学位）".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("所学专业（学位）".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("入学时间（学位）".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("毕业时间（学位）".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("学历（全日制）".equals(Message)){//------OK
										messageE += "QRZXL-a01.QRZXL,";
									}else if("入学时间（全日制）".equals(Message)){//??OK
										messageE += "A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '' group by a0804,";
									}else if("毕业时间（全日制）".equals(Message)){//??OK
										messageE += "a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = 'id' group by a0807,";
									}else if("学位（全日制）".equals(Message)){//OK
										messageE += "QRZXW-a01.QRZXW,";
									}else if("毕业学校（全日制学历）".equals(Message)){//ok
										messageE += "a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("毕业学校（全日制学位）".equals(Message)){//ok
										messageE +="a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（全日制学历）".equals(Message)){//??ok
										messageE +="a0824-select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（全日制学位）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("学历（在职）".equals(Message)){//ok
										messageE +="ZZXL-a01.ZZXL,";
									}else if("入学时间（在职）".equals(Message)){//??ok
										messageE +="A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = 'id' group by a0804,";
									}else if("毕业时间（在职）".equals(Message)){//??ok
										messageE +="a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = 'id' group by a0807,";
									}else if("学位（在职）".equals(Message)){//ok
										messageE +="ZZXW-a01.ZZXW,";
									}else if("毕业学校（在职学历）".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("毕业学校（在职学位）".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（在职学历）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("所学专业（在职学位）".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("全日制学历（任免表）".equals(Message)){//??ok
										messageE += "QRZXL-a01.QRZXL,";
									}else if("全日制学历信息（任免表）".equals(Message)){//??ok
										messageE += "QRZXLXX-a01.QRZXLXX,";
									}else if("全日制学位（任免表）".equals(Message)){//??ok
										messageE += "QRZXW-a01.QRZXW,";
									}else if("全日制学位信息（任免表）".equals(Message)){//??ok
										messageE += "QRZXWXX-a01.QRZXWXX,";
									}else if("在职学历（任免表）".equals(Message)){//ok
										messageE += "ZZXL-a01.ZZXL,";
									}else if("在职学历信息（任免表）".equals(Message)){//??ok
										messageE += "ZZXLXX-a01.ZZXLXX,";
									}else if("在职学位（任免表）".equals(Message)){//ok
										messageE += "ZZXW-a01.ZZXW,";
									}else if("在职学位信息（任免表）".equals(Message)){//??ok
										messageE += "ZZXWXX-a01.ZZXWXX,";
									}else if("工作单位及职务（全）".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("工作单位及职务（简）".equals(Message)){
										messageE += "a0192-a01.a0192,";
									}else if("登记后所任职务".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("登记后所定级别".equals(Message)){
										messageE += "a0120-a01.a0120,";
									}else if("统计关系所在单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a01.a0000= a02.a0000 ) a on  a.a0195 = a02.a0201b  where a02.a0000 = 'id',";//
									}else if("选拔任用方式".equals(Message)){
										messageE += "a0247-select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = 'id',";
									}else if("任职文号".equals(Message)){
										messageE += "a0245-a02.a0245,";
									}else if("是否领导职务".equals(Message)){
										messageE += "a0219-a02.a0219,";//
									}else if("机构名称".equals(Message)){
										messageE += "a0201a-select a02.a0201a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("职务名称".equals(Message)){
										messageE += "a0216a-select a02.a0216a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("现职务层次".equals(Message)){//?? o k=========================================
										messageE += "a0221-select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = 'id' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09',";
									}else if("任职时间".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("任现职务层次时间".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("职动类型".equals(Message)){
										messageE += "a0251-a02.a0251,";
									}else if("免职时间".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("基层工作经历年限".equals(Message)){
										messageE += "a0197-select (case when a0197 = '1' then '是' else '否' end ) from a01 where a0000 = 'id',";
									}else if("简历".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("奖惩情况".equals(Message)){
										messageE += "a14z101-a01.a14z101,";
									}else if("年度考核结果".equals(Message)){ 
										messageE += "a15z101-a01.a15z101,";
									}else if("备注".equals(Message)){
										messageE += "a0180-a01.a0180,";
									}else if("称谓（家庭成员）".equals(Message)){//??ok
										messageE += "cw,";
									}else if("姓名（家庭成员）".equals(Message)){//??ok
										messageE += "xm,";
									}else if("出生年月（家庭成员）".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("年龄（家庭成员）".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("政治面貌（家庭成员）".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("工作单位及职务（家庭成员）".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("进入本单位方式".equals(Message)){
										messageE += "a2911-select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = 'id',";
									}else if("进入本单位日期".equals(Message)){
										messageE += "a2907-a29.a2907,";
									}else if("原单位名称".equals(Message)){
										messageE += "a2921a-a29.a2921a,";
									}else if("在原单位职务".equals(Message)){
										messageE += "a2941-a29.a2941,";
									}else if("在原单位职务层次".equals(Message)){
										messageE += "a2944-a29.a2944,";
									}else if("进入公务员队伍时间".equals(Message)){
										messageE += "a2947-a29.a2947,";
									}else if("公务员登记时间".equals(Message)){
										messageE += "a2949-a29.a2949,";
									}else if("拟任职务".equals(Message)){
										messageE += "a5304-a53.a5304,";
									}else if("拟免职务".equals(Message)){
										messageE += "a5315-a53.a5315,";
									}else if("任免理由".equals(Message)){
										messageE += "a5317-a53.a5317,";
									}else if("呈报单位".equals(Message)){
										messageE += "a5319-a53.a5319,";
									}else if("填表时间".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("填表人".equals(Message)){
										messageE += "a5327-a53.a5327,";
									}else if("退出管理方式".equals(Message)){
										messageE += "a3001-select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = 'id',";
									}else if("调往单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = 'id' group by a02.a0201a,";
									}else if("退出管理时间".equals(Message)){
										messageE += "a3004-a30.a3004,";
									}else if("退出单位".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = 'id' group by a02.a0201a,";
									}else if("修改人".equals(Message)){
										messageE += "xgr-a01.xgr,";
									}else if("修改日期".equals(Message)){
										messageE += "xgsj,";
									}else if("当前日期".equals(Message)){//??ok
										messageE += "dqsj,";//----?????
									}else if("当前用户名".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
									 								
								}
								//获取到的内容把前后<>去掉
							//	String Message = message.substring(1, message.length() - 1);
								//3表示数据
								Map<String, String> map = new HashMap<String, String>();
								//1表示行
								map.put("1", row1);
								//2表示列
								map.put("2", cell1);
								//3表示数据
								MessageA =	MessageA.substring(0, MessageA.length()-1);
								map.put("3", MessageA);
								//英文数据项
//									System.out.println("44------------>"+messageE);
									if(messageE == null || "".equals(messageE)){
										this.setMainMessage("保存失败！");
										return EventRtnType.NORMAL_SUCCESS;
									}else {
										messageE =	messageE.substring(0, messageE.length()-1);
									}
								
								map.put("4", messageE);
								list.add(map);
								 messageE = "";
								 MessageA = "";
								List Data = et.getChildren();
								//获取Data节点
								//et = (Element) Data.get(0);
								//System.out.println(et.getChild("Data",ns).getText());
							}
						}
					}
					if("1".equals(isedit)){
						jiexi(list,TPname1,uuid,PageNu,"2",tpkind,"1",a);
						a++;
					}else{
						jiexi(list,TPname,uuid,PageNu,"2",tpkind,"2",a);
						a++;
					}
				} else {
					System.out.println("无数据");
				}
				p++;
			}
			this.getPageElement("savescript").setValue(uuid);
			CreateTemplatePageModel.tpid = uuid;
			this.getExecuteSG().addExecuteCode("tishi()");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.remove(thistab.tabid);");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (JDOMException e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		} catch (IOException e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		} catch (SQLException e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
	}
	
	

	public void jiexi(List list,String TPname,String TPID,String PageNu,String TPType,String TPKind,String type,int a ) {
		int tn = 0;
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		String sql = "";
		PreparedStatement Stemt = null;
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String tpname = "";
		try {
		if("1".equals(type)){
			String tptype = "";
			String endtime = "";
			String tpkind = "";
//			System.out.println("---->"+TPname);
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpname,tptype,endtime,tpkind from listoutput2 where tpid='"+TPname+"'").executeQuery();
			while(res.next()){
				tpname = res.getString(1);
				tptype = res.getString(2);
				endtime = res.getString(3);
				tpkind = res.getString(4);
			}
			try {
				if(a == 0){
					HBUtil.executeUpdate("delete from listoutput2 where tpid='"+TPname+"'");
				}
				
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,PageNu,MessageE,TPName,TPID,TPType,EndTime,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, PageNu);
				Stemt.setString(5, MessageE);
				Stemt.setString(6, tpname);
				Stemt.setString(7, TPname);
				Stemt.setString(8, tptype);
				Stemt.setString(9, endtime);
				Stemt.setString(10, tpkind);
				Stemt.setString(11, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}else{
			sql = "insert into ListOutPut2(MessageC,ZBRow,ZBLine,TPName,TPID,PageNu,TPType,MessageE,TPKind,FId) values(?,?,?,?,?,?,?,?,?,?)";
			Stemt = conn.prepareStatement(sql);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				String row = (String) map.get("1");
				String cell = (String) map.get("2");
				String message = (String) map.get("3");
				String MessageE = (String) map.get("4");
				Stemt.setString(1, message);
				Stemt.setString(2, row);
				Stemt.setString(3, cell);
				Stemt.setString(4, TPname);
				Stemt.setString(5, TPID);
				Stemt.setString(6, PageNu);
				Stemt.setString(7, TPType);
				Stemt.setString(8, MessageE);
				Stemt.setString(9, TPKind);
				Stemt.setString(10, uuid);
				Stemt.addBatch();
				tn++;
				if (tn % 200 == 0) {
					Stemt.executeBatch();
					Stemt.clearBatch();
				}
			}
		}
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template(user_template_id,tpid,userid) values('"+uuid+"','"+TPID+"','"+userid+"')";
			HBUtil.getHBSession().connection().prepareStatement(sql1).executeUpdate();
			if (tn % 200 != 0) {
				Stemt.executeBatch();
				Stemt.clearBatch();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (Stemt != null) {
					Stemt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (Exception e) {
			}

		}
//		System.out.println("保存完成");
	}
	
	@NoRequiredValidate
	@PageEvent("renameTP")
	public int renameTP() throws RadowException, SQLException {
		try {
			getCheckList1();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bzcount = this.getPageElement("BZtemCount").getValue();
		if("".equals(bzcount))
			bzcount = "0";
		String zdycount = this.getPageElement("ZDYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String gxycount = this.getPageElement("GXYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String bzid = this.getPageElement("BZtemID").getValue();
		String zdyid = this.getPageElement("ZDYtemID").getValue();
		String gxyid = this.getPageElement("GXYtemID").getValue();
		String bztpname = this.getPageElement("BZtemName").getValue();
		String zdytpname = this.getPageElement("ZDYtemName").getValue();
		 if(((bzcount != null && !"".equals(bzcount))?Integer.parseInt(bzcount):Integer.parseInt("0"))+((zdycount != null && !"".equals(zdycount))?Integer.parseInt(zdycount):Integer.parseInt("0"))+((gxycount != null && !"".equals(gxycount))?Integer.parseInt(gxycount):Integer.parseInt("0"))>1){
			this.setMainMessage("不可同时重命名多张模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(gxycount != null && !"".equals(gxycount) && "1".equals(gxycount)){
			//获取选中共享模板的id
			String sql = "select uuid from powergx where modelid= '"+gxyid.substring(1,gxyid.length()-1)+"' and owenr = '"+SysManagerUtils.getUserId()+"' ";
			ResultSet rs = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
			if(!rs.next()){
				this.setMainMessage("共享者可以修改此模板!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("请在自定义模板列进行重命名!");
//		}else if((Integer.parseInt(zdycount) == 0 || "".equals(zdycount))&& (Integer.parseInt(gxycount) == 0  ||  "".equals(gxycount)) && (Integer.parseInt(bzcount) == 0 || "".equals(bzcount))){
		}else if(("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount)||"0".equals(gxycount)) && ("".equals(bzcount) || "0".equals(bzcount))){
			this.setMainMessage("请选择模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			if(gxyid != null && !"".equals(gxyid)){
				zdyid = gxyid;
			}
			this.request.getSession().setAttribute("opentype", "1");
			this.setRadow_parent_data(zdyid.substring(1,zdyid.length()-1));
			/*this.getPageElement("BZtemCount").setValue("0");
			this.getPageElement("ZDYtemCount").setValue("0");
			this.getPageElement("GXYtemCount").setValue("0");*/
			request.getSession().setAttribute("zdytpname", zdytpname);
			this.openWindow("renameWin", "pages.publicServantManage.saveTemplate");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("refuesto")
	public int refuesto( ) throws RadowException {
		this.getPageElement("BZtemCount").setValue("0");
		this.getPageElement("ZDYtemCount").setValue("0");
		this.getPageElement("GXYtemCount").setValue("0");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 设为共享的方法
	 * @return
	 * @throws RadowException
	 * @throws SQLException 
	 */
	@NoRequiredValidate
	@Transaction
	@PageEvent("renameTPS")
	public int renameTPS() throws RadowException, SQLException {
		try {
			getCheckList1();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bzcount = this.getPageElement("BZtemCount").getValue();
		if("".equals(bzcount))
			bzcount = "0";
		String zdycount = this.getPageElement("ZDYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String gxycount = this.getPageElement("GXYtemCount").getValue();
		if("".equals(zdycount))
			gxycount = "0";
		
		String zdyid = this.getPageElement("ZDYtemID").getValue();
		
		if(((bzcount != null && !"".equals(bzcount))?Integer.parseInt(bzcount):Integer.parseInt("0"))+((zdycount != null && !"".equals(zdycount))?Integer.parseInt(zdycount):Integer.parseInt("0"))+((gxycount != null && !"".equals(gxycount))?Integer.parseInt(gxycount):Integer.parseInt("0"))>1){
			this.setMainMessage("不能选择多张表册！");
			return EventRtnType.NORMAL_SUCCESS;
		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("标准模板不可重命名！");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("共享模板不能再次共享！");
			return EventRtnType.NORMAL_SUCCESS;*/
		}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) == 0){
			this.setMainMessage("请选择自定义模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{//5代表共享类型
			HBSession sess = HBUtil.getHBSession();
			zdyid = zdyid.substring(1, zdyid.length()-1);
			String update = "update listoutput set typestate = '2' where tpid = '"+zdyid+"'";
			sess.createSQLQuery(update).executeUpdate();
			//request.getSession().setAttribute("zdyid",zdyid);//设为共享时把模板id存到powergx表里用
			//String ctxPath = request.getContextPath();
			//this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','用户信息',440,400,'JgTree','"+ctxPath+"');");
			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
			System.out.println(sql);
			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
			this.getPageElement("GXYtemCount").setValue("");
			this.setMainMessage("共享完成");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 取消共享 
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("renameTPS2")
	public int renameTPS2() throws RadowException, SQLException {
		//----------------------------------国庆代码------------------------
//		String bzcount = this.getPageElement("BZtemCount").getValue();
//		if("".equals(bzcount))
//			bzcount = "0";
//		String zdycount = this.getPageElement("ZDYtemCount").getValue();
//		if("".equals(zdycount))
//			zdycount = "0";
//		String gxycount = this.getPageElement("GXYtemCount").getValue();
//		if("".equals(zdycount))
//			gxycount = "0";
//		
//		String GXYtemID = this.getPageElement("GXYtemID").getValue();
//		
//		if(((bzcount != null && !"".equals(bzcount))?Integer.parseInt(bzcount):Integer.parseInt("0"))+((zdycount != null && !"".equals(zdycount))?Integer.parseInt(zdycount):Integer.parseInt("0"))+((gxycount != null && !"".equals(gxycount))?Integer.parseInt(gxycount):Integer.parseInt("0"))>1){
//			this.setMainMessage("不能选择多张模板！");
//			return EventRtnType.NORMAL_SUCCESS;
//		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
//			this.setMainMessage("标准模板不可重命名！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
//			this.setMainMessage("共享模板不能再次共享！");
//			return EventRtnType.NORMAL_SUCCESS;*/
//		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) == 0){
//			this.setMainMessage("请选择共享模板！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}else{
//			GXYtemID = GXYtemID.substring(1, GXYtemID.length()-1);
//			request.getSession().setAttribute("GXYtemID",GXYtemID);
//			String ctxPath = request.getContextPath();
//			this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','用户信息',440,400,'JgTree','"+ctxPath+"');");
//			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
//			System.out.println(sql);
//			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
//			this.getPageElement("GXYtemCount").setValue("");
//			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
//	    }
		//------------------------------吴皓代码----------------------------------
		try {
			getCheckList1();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String bzcount = this.getPageElement("BZtemCount").getValue();
		if("".equals(bzcount))
			bzcount = "0";
		String zdycount = this.getPageElement("ZDYtemCount").getValue();
		if("".equals(zdycount))
			zdycount = "0";
		String gxycount = this.getPageElement("GXYtemCount").getValue();
		if("".equals(zdycount))
			gxycount = "0";
		
		String zdyid = this.getPageElement("ZDYtemID").getValue();
		
		if(((bzcount != null && !"".equals(bzcount))?Integer.parseInt(bzcount):Integer.parseInt("0"))+((zdycount != null && !"".equals(zdycount))?Integer.parseInt(zdycount):Integer.parseInt("0"))+((gxycount != null && !"".equals(gxycount))?Integer.parseInt(gxycount):Integer.parseInt("0"))>1){
			this.setMainMessage("不能选择多张表册！");
			return EventRtnType.NORMAL_SUCCESS;
		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("标准模板不可重命名！");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("共享模板不能再次共享！");
			return EventRtnType.NORMAL_SUCCESS;*/
		}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) == 0){
			this.setMainMessage("请选择自定义模板！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{//5代表共享类型
			HBSession sess = HBUtil.getHBSession();
			zdyid = zdyid.substring(1, zdyid.length()-1);
			String update = "update listoutput set typestate = '1' where tpid = '"+zdyid+"'";
			sess.createSQLQuery(update).executeUpdate();
			//request.getSession().setAttribute("zdyid",zdyid);//设为共享时把模板id存到powergx表里用
			//String ctxPath = request.getContextPath();
			//this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','用户信息',440,400,'JgTree','"+ctxPath+"');");
			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
			System.out.println(sql);
			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
			this.getPageElement("GXYtemCount").setValue("");
			this.setMainMessage("取消成功");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("deltp")
	public int deltp(String zdyid) throws RadowException {
		try {
			List listj = HBUtil.getHBSession().createSQLQuery("select tpname from powergx where modelid = "+zdyid.replace("|", "'").replace("@", ",")+" ").list();
			if(listj.size()==0||listj == null){
				HBUtil.executeUpdate("delete from listoutput where tpid in ("+ zdyid.replace("|", "'").replace("@", ",") + ")");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
				this.getPageElement("BZtemCount").setValue("0");
				this.getPageElement("ZDYtemCount").setValue("0");
				this.getPageElement("GXYtemCount").setValue("0");
			}else{
				this.getExecuteSG().addExecuteCode("info()");
			}
			
		} catch (AppException e) {
			this.setMainMessage("删除失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@NoRequiredValidate
	@PageEvent("goon")
	public int goon(String zdyid) throws RadowException {
		try {
			HBUtil.executeUpdate("delete from listoutput where tpid in ("+ zdyid.replace("|", "'").replace("@", ",") + ")");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
			this.getPageElement("BZtemCount").setValue("0");
			this.getPageElement("ZDYtemCount").setValue("0");
			this.getPageElement("GXYtemCount").setValue("0");
		} catch (AppException e) {
			this.setMainMessage("删除失败！");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 查询是否有权限
	 * @param zdyid
	 * @return
	 * @throws RadowException
	 * @throws SQLException 
	 */
	@NoRequiredValidate
	@PageEvent("deltp2")
	public int deltp2(String zdyid) throws RadowException, SQLException {
		String sql = "select tpid from USER_TEMPLATE where tpid in ("+ zdyid.replace("|", "'").replace("@", ",") +") and userid = '"+SysManagerUtils.getUserId()+"'  ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if(re.next()){
			System.out.println(re.getString(1));
			this.getExecuteSG().addExecuteCode("delTP2('"+zdyid+"')");
		}else{
			this.setMainMessage("您无此权限");	
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("endAgeTime")
	public int endAgeTime(String templateid) throws RadowException {
		this.request.getSession().setAttribute("templateid", templateid);
		this.getPageElement("BZtemCount").setValue("0");
		this.getPageElement("ZDYtemCount").setValue("0");
		this.getPageElement("GXYtemCount").setValue("0");
		this.getExecuteSG().addExecuteCode("$h.openWin('endAgeTimeWin','pages.publicServantManage.SetTemEndTime','设置年龄计算时间点',315,315,'"+templateid+"',ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("clear.onclick")
	public int reload() throws RadowException{
		this.getPageElement("BZtemCount").setValue("0");
		this.getPageElement("ZDYtemCount").setValue("0");
		this.getPageElement("GXYtemCount").setValue("0");
		this.getExecuteSG().addExecuteCode("refresh();");
		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("uploadfile")
	public int uploadfile() throws RadowException{
		String savePath = request.getSession().getServletContext().getRealPath("/template")+"/"+tpid+".cll";
		try {
			File file = new File(savePath);
			file.createNewFile();
			ServletInputStream in = request.getInputStream();
			FileOutputStream out = new FileOutputStream(savePath);
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer,0,len);
			}
			in.close();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("success");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("isovername")
	public int isovername(String tpname) throws RadowException{
		System.out.println("----->"+tpname);
		List countlist = HBUtil.getHBSession().createSQLQuery("select count(1) from (select t.tpid from listoutput2 t where t.tpname='"+tpname+"' group by t.tpid) a").list();
		if(Integer.parseInt(countlist.get(0).toString())==0){
//			jiexi(list,TPname,uuid,PageNu,"2",tpkind,"2");
			this.getPageElement("nameF").setValue(tpname);
			this.getExecuteSG().addExecuteCode("doXmljx()");
		}else{
			this.setMainMessage("模板名重复！请更改后再做导入！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 标准模板，列表单击选中事件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("templateInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int templateInfoGridOnRowClick() throws RadowException, AppException {
		
		int index = this.getPageElement("templateInfoGrid").getCueRowIndex();
		String tpid = this.getPageElement("templateInfoGrid").getValue("tpid", index).toString();
		
		Grid grid = (Grid)this.getPageElement("templateInfoGrid");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		//1.勾选该行，取消其他行的勾选  
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			
			if(tpid.equals(map.get("tpid"))){
				map.put("personcheck", true);
			}else{
				map.put("personcheck", false);
			}
			
			newList.add(map);
		}
		grid.setValueList(newList);
		
		
		//将自定义模板，共享模板的勾选去除
		Grid grid1 = (Grid)this.getPageElement("templateInfoGrid1");
		List<HashMap<String,Object>> newList1 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList1 = grid1.getValueList();
		for(int i=0;i<gridList1.size();i++){
			HashMap<String,Object> map = gridList1.get(i);
			map.put("personcheck1", false);
			newList1.add(map);
		}
		grid1.setValueList(newList1);
		
		Grid grid2 = (Grid)this.getPageElement("templateInfoGrid2");
		List<HashMap<String,Object>> newList2 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList2 = grid2.getValueList();
		for(int i=0;i<gridList2.size();i++){
			HashMap<String,Object> map = gridList2.get(i);
			map.put("personcheck2", false);
			newList2.add(map);
		}
		grid2.setValueList(newList2);
		
		
		this.getCheckList();
		this.getCheckList1();
		this.getCheckList2();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 自定义模板，列表单击选中事件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("templateInfoGrid1.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int templateInfoGrid1OnRowClick() throws RadowException, AppException {
		
		int index = this.getPageElement("templateInfoGrid1").getCueRowIndex();
		String tpid = this.getPageElement("templateInfoGrid1").getValue("tpid", index).toString();
		
		Grid grid = (Grid)this.getPageElement("templateInfoGrid1");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		//1.勾选该行，取消其他行的勾选  
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			
			if(tpid.equals(map.get("tpid"))){
				map.put("personcheck1", true);
			}else{
				map.put("personcheck1", false);
			}
			
			newList.add(map);
		}
		grid.setValueList(newList);
		
		//将标准模板，共享模板的勾选去除
		Grid grid1 = (Grid)this.getPageElement("templateInfoGrid");
		List<HashMap<String,Object>> newList1 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList1 = grid1.getValueList();
		for(int i=0;i<gridList1.size();i++){
			HashMap<String,Object> map = gridList1.get(i);
			map.put("personcheck", false);
			newList1.add(map);
		}
		grid1.setValueList(newList1);
		
		Grid grid2 = (Grid)this.getPageElement("templateInfoGrid2");
		List<HashMap<String,Object>> newList2 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList2 = grid2.getValueList();
		for(int i=0;i<gridList2.size();i++){
			HashMap<String,Object> map = gridList2.get(i);
			map.put("personcheck2", false);
			newList2.add(map);
		}
		grid2.setValueList(newList2);
		
		this.getCheckList();
		this.getCheckList1();
		this.getCheckList2();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 共享模板，列表单击选中事件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("templateInfoGrid2.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int templateInfoGrid2OnRowClick() throws RadowException, AppException {
		
		int index = this.getPageElement("templateInfoGrid2").getCueRowIndex();
		String uuid = this.getPageElement("templateInfoGrid2").getValue("uuid", index).toString();
		
		Grid grid = (Grid)this.getPageElement("templateInfoGrid2");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		List<HashMap<String,Object>> newList = new ArrayList<HashMap<String,Object>>();
		//1.勾选该行，取消其他行的勾选  
		for(int i=0;i<gridList.size();i++){
			HashMap<String,Object> map = gridList.get(i);
			
			if(uuid.equals(map.get("uuid"))){
				map.put("personcheck2", true);
			}else{
				map.put("personcheck2", false);
			}
			
			newList.add(map);
		}
		grid.setValueList(newList);
		
		//将标准模板，自定义模板的勾选去除
		Grid grid1 = (Grid)this.getPageElement("templateInfoGrid");
		List<HashMap<String,Object>> newList1 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList1 = grid1.getValueList();
		for(int i=0;i<gridList1.size();i++){
			HashMap<String,Object> map = gridList1.get(i);
			map.put("personcheck", false);
			newList1.add(map);
		}
		grid1.setValueList(newList1);
		
		Grid grid2 = (Grid)this.getPageElement("templateInfoGrid1");
		List<HashMap<String,Object>> newList2 = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> gridList2 = grid2.getValueList();
		for(int i=0;i<gridList2.size();i++){
			HashMap<String,Object> map = gridList2.get(i);
			map.put("personcheck1", false);
			newList2.add(map);
		}
		grid2.setValueList(newList2);
		
		this.getCheckList();
		this.getCheckList1();
		this.getCheckList2();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
