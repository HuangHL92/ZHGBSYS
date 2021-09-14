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
	 * ϵͳ������Ϣ
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
	 * ����ģ����
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
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//����
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
			this.setMainMessage("��ѡ��ģ�壡");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("������ͬʱ���ƶ���ģ�壡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!StringUtil.isEmpty(bzid)){
			//���Ʊ�׼ģ��
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_����','2',messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,typestate from listoutput where tpid = "+bzid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}else if(!StringUtil.isEmpty(zdyid)){
			//�����Զ���ģ��
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_����',tptype,messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,typestate from listoutput where tpid = "+zdyid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}else{
			//���ƹ���ģ��
			String fid = UUID.randomUUID().toString();
			String tpid = UUID.randomUUID().toString();
			String sql = "insert into listoutput (select '"+fid+"','"+tpid+"',tpname||'_����','2',messagec,messagee,zbrow,zbline,endtime,pagenu,tpkind,'1' from listoutput where tpid = "+gxyid.replace("|", "'")+")";
			String USER_TEMPLATE_ID = UUID.randomUUID().toString();
			String userid = SysManagerUtils.getUserId();
			String sql1 = "insert into user_template values ('"+USER_TEMPLATE_ID+"','"+tpid+"','"+userid+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.createSQLQuery(sql1).executeUpdate();
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}
		this.setMainMessage("�������");
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
				this.setMainMessage("��ѡ��ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){//
				this.setMainMessage("������ͬʱ�༭����ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(gxycount)&&!"0".equals(gxycount)){
				this.setMainMessage("����ģ�岻�ܱ༭��");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(bzcount)&&!"0".equals(bzcount)){
				this.setMainMessage("��׼ģ�岻�ܱ༭��");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				title = "�༭ģ��";
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", zdytpname.substring(1, zdytpname.length()-1));
				this.request.getSession().setAttribute("tpid", zdyid);
				this.request.getSession().setAttribute("isedit", "1");
				this.request.getSession().setAttribute("temtype","");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}
		}else if("1".equals(cztype)){//��ͬ���
			if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
				this.setMainMessage("��ѡ��ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
				this.setMainMessage("������ͬʱ��������ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(("0".equals(bzcount)||"".equals(bzcount)) && "1".equals(zdycount) && ("0".equals(gxycount) || "".equals(gxycount))){
				title = "��ͬ���";
				String namehouzhui  = zdytpname.substring(1, zdytpname.length()-1);
				System.out.println("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind");
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+zdytpname.substring(1, zdytpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", zdytpname.substring(1, zdytpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("��"), (namehouzhui.indexOf("��")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","2");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}else if(("0".equals(zdycount) || "".equals(zdycount)) &&"1".equals(bzcount) && ("0".equals(gxycount) ||"".equals(gxycount))){//������׼ģ��ͬ���
				title = "��ͬ���";
				String namehouzhui  = bztpname.substring(1, bztpname.length()-1);
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpname='"+bztpname.substring(1, bztpname.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", bztpname.substring(1, bztpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("��"), (namehouzhui.indexOf("��")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","1");
				this.request.getSession().setAttribute("tpkind",list.get(0));
			}else{
				title = "��ͬ���";
				String namehouzhui  = gxytpname.substring(1, gxytpname.length()-1);
				System.out.println("select t.tpkind from listoutput2 t where t.tpid='"+gxyid.substring(1, gxyid.length()-1)+"' group by t.tpkind");
//				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput t where t.tpname='"+gxytpname.substring(1, gxytpname.length()-1)+"' group by t.tpkind").list();
				List list = HBUtil.getHBSession().createSQLQuery("select t.tpkind from listoutput2 t where t.tpid='"+gxyid.substring(1, gxyid.length()-1)+"' group by t.tpkind").list();
				this.request.getSession().setAttribute("tpname", gxytpname.substring(1, gxytpname.length()-1));
				//this.request.getSession().setAttribute("namehouzhui",namehouzhui.substring(namehouzhui.indexOf("��"), (namehouzhui.indexOf("��")+1)));
				this.request.getSession().setAttribute("isedit", "3");
				this.request.getSession().setAttribute("tpid", "0");
				this.request.getSession().setAttribute("temtype","2");///·������û�н��,��ѯ����������?
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
						this.getPageElement("tpname").setValue(tpname+"_����");
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}else{
				this.getPageElement("tpname").setValue(tpname);
			}
			this.getPageElement("tpkind").setValue((this.request.getSession().getAttribute("tpkind")).toString());
			this.getExecuteSG().addExecuteCode("pageinit();");
			this.setMainMessage("���Ƴɹ�");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
			return EventRtnType.NORMAL_SUCCESS;
		}else if("0".equals(cztype)){
			title = "�½�ģ��-���";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","�����");
			this.request.getSession().setAttribute("tpkind","1");
		}else if("3".equals(cztype)){
			title = "�½�ģ��-��׼����";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","����׼���᡿");
			this.request.getSession().setAttribute("tpkind","2");
			this.request.getSession().setAttribute("isphoto","isphoto2");
		}else if("print".equals(cztype)){
			if(("".equals(bzcount)||"0".equals(bzcount)) && ("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount) || "0".equals(gxycount))){
				this.setMainMessage("��ѡ��ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(((bzcount != null && !"".equals(bzcount))? Integer.parseInt(bzcount):Integer.parseInt("0")) + ((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0"))+ ((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){//
				this.setMainMessage("������ͬʱ��ӡ����ģ�壡");
				return EventRtnType.NORMAL_SUCCESS;
			}/*else if(!"".equals(gxycount)&&!"0".equals(gxycount)){
				this.setMainMessage("����ģ��ѡ�к��ڡ���ͬ����д�ӡ��");
				return EventRtnType.NORMAL_SUCCESS;
			}else if(!"".equals(zdycount)&&!"0".equals(zdycount)){
				this.setMainMessage("�Զ���ģ�����ڡ��༭ģ�塱�д�ӡ��");
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
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin6','pages.publicServantManage.OtherTemShow','��ӡ',1000,900,'R','"+ctxPath+"');");
//			this.getExecuteSG().addExecuteCode("addTab('��ӡ','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			title = "�½�ģ��-������";
			this.request.getSession().setAttribute("tpname", "");
			this.request.getSession().setAttribute("isedit", "0");
			this.request.getSession().setAttribute("tpid", "0");
			this.request.getSession().setAttribute("temtype","�������᡿");
			this.request.getSession().setAttribute("tpkind","3");
			this.request.getSession().setAttribute("isphoto","isphoto");//��������ָ�������С���createTemplate.jsp
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
	 * ����ģ��
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
	 * ���н���
	 * xmlDoc �����xml�ַ�������
	 * TPname   ģ������
	 * TPtype  ģ������
	 */
	@PageEvent("openDataVerifyWin")
	public int openDataVerifyWin(String xmlDoc) throws RadowException {
		Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        String time = sf.format(date);
        time = time.substring(0,time.length()-1);
//		System.out.println(xmlDoc);
		//����һ���µ��ַ���
		StringReader read = new StringReader(xmlDoc);
		//�����µ�����ԴSAX ��������ʹ�� InputSource ������ȷ����ζ�ȡ XML ����
		InputSource source = new InputSource(read);
		//����һ���µ�SAXBuilder
		SAXBuilder sb = new SAXBuilder();
		//һ������������mapȷ����k��1�У�2�У�3���ݣ�v���б꣬�б����ݣ�
		//Map<String, String> map = new HashMap<String, String>();
		try {
			//ͨ������Դ����һ��Document
			Document doc = sb.build(source);
			//ȡ�ĸ�Ԫ��
			Element root = doc.getRootElement();
			//�����Ԫ�ص����ƣ����ԣ�
//			System.out.println(root.getName());
			//�õ���Ԫ��������Ԫ�صļ���
			List jiedian = root.getChildren();
			Namespace ns = root.getNamespace();
			int n = 0;
			n = jiedian.size();
			String uuid = UUID.randomUUID().toString();
			int p = 1;
			int a = 0;//����ɾ���Ĵ�������jiexi��������
			String TPname = this.getPageElement("tname").getValue();
			String houzhui = (String)request.getSession().getAttribute("namehouzhui");
			if(houzhui != null && (!TPname.contains("�����")&&!TPname.contains("����׼���᡿")&&!TPname.contains("�������᡿"))){
				TPname += houzhui;
				request.getSession().removeAttribute("namehouzhui");
			}
			if(houzhui != null && "�������᡿".equals(houzhui)){
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��");
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
						this.setMainMessage("ģ�����ظ��������������");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select count(1) from (select t.tpid from listoutput2 t where t.tpname='"+TPname+"' group by t.tpid) a").executeQuery();
				while(res.next()){
					int count = Integer.parseInt(res.getString(1));
					if(count>0){
						this.setMainMessage("ģ�����ظ��������������");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			for(int m = 0;m < jiedian.size()-1;m++ ){
				//list������map
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				n--;
				Element et = null;
				//���Worksheet�ڵ�
				et = (Element) jiedian.get(n);
				 //��ȡҳ��
	            String page = et.getAttributeValue("Name", ns);
	            String PageNu = n+"";
				//���Worksheet�ڵ��µ��ӽڵ�Table�ڵ�
				List Table = et.getChildren();
				//��ȡTable�ڵ�
				et = (Element) Table.get(0);
				//��ȡTable�ڵ��µ��ӽڵ�row�ڵ�
				List row = et.getChildren();
				//������������ƴ���ַ���
				String messageE = "";
				String MessageA = "";
				//���row>0��˵��������
				if (row.size() > 0) {
					//ѭ������row��ȡÿһ��row
					for (int i = 0; i < row.size(); i++) {
						//��ȡrow�ڵ�
						et = (Element) row.get(i);
						//��ȡ��ǩ���������
						String row1 = et.getAttributeValue("Index", ns);
						//1��ʾ��
						//��ȡrow�ڵ�������ӽڵ�
						List cell = et.getChildren();
						for (int j = 0; j < cell.size(); j++) {
							//��ȡcell�ڵ�
							et = (Element) cell.get(j);
							//��ȡ��ǩ���������
							String cell1 = et.getAttributeValue("Index", ns);
							//2��ʾ��
							//��ȡ������Ϣ
							String message = et.getChild("Data", ns).getText();
							//�ж������ǲ�����Ҫ�ı�������ݣ�<>�ڵ�������Ҫ���浽���ݿ�ģ�
							if (message.contains("<")) {
								//��ȡ������ȥ������ >
								String Message1 = message.substring(message.indexOf("<"), message.lastIndexOf(">"));
								//��>���н�ȡ
								String[] split1 = Message1.replace("<", "").split(">");
								for(int q = 0; q < split1.length ; q++){
									//���������ȡ���ݽ���ƴ���ַ���
									MessageA += split1[q]+",";
									//��ȡÿһ����Ϣ��
									String Message = split1[q];
									//ÿһ����Ϣ���붨�����Ϣ�н���ƥ�䣬��ȡȥ��Ϣ��Ӣ��֮��ƴ��Ӣ����Ϣ��
									
/*									if("����".equals(Message)){
										messageE += "a01.a0101,"; 
									}else if("���壨����".equals(Message)){
										messageE += "mzm,";
									}else if("�Ա�����".equals(Message)){
										messageE += "xbm,";
									}else if("��Ա״̬".equals(Message)){
										messageE += "ryzt,";
									}else if("��Ա���".equals(Message)){
										messageE += "a01.a0160,";
									}else if("���֤��".equals(Message)){
										messageE += "a01.a0184,";
									}else if("�Ա�".equals(Message)){
										messageE += "a01.a0104a,";
									}else if("��������".equals(Message)){//??0k
										messageE += "csny,";
									}else if("��������".equals(Message)){
										messageE += "a01.a0107,";
									}else if("����".equals(Message)){//??ok
										messageE += "nl,";
									}else if("��Ƭ".equals(Message)){//??ok
										messageE += "zp,";
									}else if("����".equals(Message)){   
										messageE += "a01.a0117a,";
									}else if("����".equals(Message)){ 
										messageE += "a01.a0111a,";
									}else if("������".equals(Message)){
										messageE += "a01.a0114a,";
									}else if("��һ����".equals(Message)){
										messageE += "a01.a0141,";
									}else if("�뵳ʱ��".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("�ڶ�����".equals(Message)){
										messageE += "dedp,";
									}else if("��������".equals(Message)){
										messageE += "dsdp,";
									}else if("�μӹ���ʱ��".equals(Message)){
										messageE += "a01.a0134,";
									}else if("����״��".equals(Message)){
										messageE += "a01.a0128,";
									}else if("רҵ����ְ��".equals(Message)){
										messageE += "a01.a0196,";
									}else if("��Ϥרҵ�к�ר��".equals(Message)){
										messageE += "a01.a0187a,";
									}else if("ѧ��".equals(Message)){//-----------------
										messageE += "zgxl,";
									}else if("��ҵѧУ��ѧ����".equals(Message)){
										messageE += "zgxlbyxx,";
									}else if("��ѧרҵ��ѧ����".equals(Message)){
										messageE += "zgxlsxzy,";
									}else if("��ѧʱ�䣨ѧ����".equals(Message)){
										messageE += "zgxlrxsj,";
									}else if("��ҵʱ�䣨ѧ����".equals(Message)){
										messageE += "zgxlbisj,";
									}else if("ѧλ".equals(Message)){
										messageE += "zgxw,";
									}else if("��ҵѧУ��ѧλ��".equals(Message)){
										messageE += "zgxwbyxx,";
									}else if("��ѧרҵ��ѧλ��".equals(Message)){
										messageE += "zgxwsxzy,";
									}else if("��ѧʱ�䣨ѧλ��".equals(Message)){
										messageE += "zgxwrxsj,";
									}else if("��ҵʱ�䣨ѧλ��".equals(Message)){
										messageE += "zgxwbisj,";
									}else if("ѧ����ȫ���ƣ�".equals(Message)){//------OK
										messageE += "xlqrz,";
									}else if("��ѧʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "rxsjqrz,";
									}else if("��ҵʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "bysjqrz,";
									}else if("ѧλ��ȫ���ƣ�".equals(Message)){//OK
										messageE += "xwqrz,";
									}else if("��ҵѧУ��ȫ����ѧ����".equals(Message)){//ok
										messageE += "xxjyxql,";
									}else if("��ҵѧУ��ȫ����ѧλ��".equals(Message)){//ok
										messageE +="xxjyxqw,";
									}else if("��ѧרҵ��ȫ����ѧ����".equals(Message)){//??ok
										messageE +="sxzyql,";
									}else if("��ѧרҵ��ȫ����ѧλ��".equals(Message)){//??ok
										messageE +="sxzyqw,";
									}else if("ѧ������ְ��".equals(Message)){//ok
										messageE +="xlzz,";
									}else if("��ѧʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="rxsjzz,";
									}else if("��ҵʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="bysjzz,";
									}else if("ѧλ����ְ��".equals(Message)){//ok
										messageE +="xwzz,";
									}else if("��ҵѧУ����ְѧ����".equals(Message)){//??ok
										messageE +="xxjyxzl,";
									}else if("��ҵѧУ����ְѧλ��".equals(Message)){//??ok
										messageE +="xxjyxzw,";
									}else if("��ѧרҵ����ְѧ����".equals(Message)){//??ok
										messageE +="sxzyzl,";
									}else if("��ѧרҵ����ְѧλ��".equals(Message)){//??ok
										messageE +="sxzyzw,";
									}else if("ȫ����ѧ���������".equals(Message)){//??ok
										messageE += "qrzxlrb,";
									}else if("ȫ����ѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "qrzxlxxrb,";
									}else if("ȫ����ѧλ�������".equals(Message)){//??ok
										messageE += "qrzxwrb,";
									}else if("ȫ����ѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "qrzxwxxrb,";
									}else if("��ְѧ���������".equals(Message)){//ok
										messageE += "zzxlrb,";
									}else if("��ְѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "zzxixxrb,";
									}else if("��ְѧλ�������".equals(Message)){//ok
										messageE += "zzxwrb,";
									}else if("��ְѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "zzxwxxrb,";
									}else if("������λ��ְ��ȫ��".equals(Message)){
										messageE += "a01.a0192a,";
									}else if("������λ��ְ�񣨼�".equals(Message)){
										messageE += "a01.a0192,";
									}else if("ͳ�ƹ�ϵ���ڵ�λ".equals(Message)){
										messageE += "a01.a0195,";//
									}else if("������ְ��".equals(Message)){//??""
										messageE += "jgwpx,";//
									}else if("ѡ�����÷�ʽ".equals(Message)){
										messageE += "a02.a0247,";
									}else if("��ְ�ĺ�".equals(Message)){
										messageE += "a02.a0245,";
									}else if("�Ƿ��쵼ְ��".equals(Message)){
										messageE += "a02.a0219,";//
									}else if("��������".equals(Message)){
										messageE += "a02.a0201a,";
									}else if("ְ������".equals(Message)){
										messageE += "a02.a0216a,";
									}else if("��ְ����".equals(Message)){//?? o k
										messageE += "zwcc,";
									}else if("��ְʱ��".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("����ְ����ʱ��".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("���ʱ�����Ŵ���".equals(Message)){//??ok
										messageE += "ccsjkhcl,";//-------/
									}else if("ְ������".equals(Message)){
										messageE += "a02.a0251,";
									}else if("��ְʱ��".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("���㹤����������".equals(Message)){
										messageE += "jcnx,";
									}else if("����".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("�������".equals(Message)){
										messageE += "a01.a14z101,";
									}else if("��ȿ��˽��".equals(Message)){ 
										messageE += "a01.a15z101,";
									}else if("��ע".equals(Message)){
										messageE += "a01.a0180,";
									}else if("��ν����ͥ��Ա��".equals(Message)){//??ok
										messageE += "cw,";
									}else if("��������ͥ��Ա��".equals(Message)){//??ok
										messageE += "xm,";
									}else if("�������£���ͥ��Ա��".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("���䣨��ͥ��Ա��".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("������ò����ͥ��Ա��".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("������λ��ְ�񣨼�ͥ��Ա��".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("���뱾��λ��ʽ".equals(Message)){
										messageE += "a29.a2911,";
									}else if("���뱾��λ����".equals(Message)){
										messageE += "a29.a2907,";
									}else if("ԭ��λ����".equals(Message)){
										messageE += "a29.a2921a,";
									}else if("��ԭ��λְ��".equals(Message)){
										messageE += "a29.a2941,";
									}else if("��ԭ��λְ����".equals(Message)){
										messageE += "a29.a2944,";
									}else if("���빫��Ա����ʱ��".equals(Message)){
										messageE += "a29.a2947,";
									}else if("����Ա�Ǽ�ʱ��".equals(Message)){
										messageE += "a29.a2949,";
									}else if("����ְ��".equals(Message)){
										messageE += "a53.a5304,";
									}else if("����ְ��".equals(Message)){
										messageE += "a53.a5315,";
									}else if("��������".equals(Message)){
										messageE += "a53.a5317,";
									}else if("�ʱ���λ".equals(Message)){
										messageE += "a53.a5319,";
									}else if("���ʱ��".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("�����".equals(Message)){
										messageE += "a53.a5327,";
									}else if("��������ʱ��".equals(Message)){
										messageE += "a53.a5321,";
									}else if("�˳�����ʽ".equals(Message)){
										messageE += "a30.a3001,";
									}else if("������λ".equals(Message)){
										messageE += "a30.a3007a,";
									}else if("�˳�����ʱ��".equals(Message)){
										messageE += "a30.a3004,";
									}else if("�˳���λ".equals(Message)){
										messageE += "a01.orgid,";
									}else if("�޸���".equals(Message)){
										messageE += "a01.xgr,";
									}else if("�޸�����".equals(Message)){
										messageE += "a01.xgsj,";
									}else if("��ǰ����".equals(Message)){//??ok
										messageE += "dqsj,";//----
									}else if("��ǰ�û���".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
*/								
									if("����".equals(Message)){
										messageE += "a0101-a01.a0101,"; 
									}else if("��Ա״̬".equals(Message)){
										messageE += "a0163-select code_name from code_value where  code_type = 'ZB126' and code_value = (select a0163 from a01 where a0000 = 'id' ) ',";
									}else if("��Ա���".equals(Message)){
										messageE += "a0160-select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='id',";
									}else if("���֤��".equals(Message)){
										messageE += "a0184-a01.a0184,";
									}else if("�Ա�".equals(Message)){
										messageE += "a0104-select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='id' and cv.code_value=a.a0104,";
									}else if("��������".equals(Message)){//??0k
										messageE += "csny,";
									}else if("����".equals(Message)){//??ok
										messageE += "nl,";
									}else if("��Ƭ".equals(Message)){//??ok
										messageE += "zp,";
									}else if("����".equals(Message)){   
										messageE += "a0117-select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='id' and cv.code_value=a.a0117,";
									}else if("����".equals(Message)){ 
										messageE += "a0111a-a01.a0111a,";
									}else if("������".equals(Message)){
										messageE += "a0114a-a01.a0114a,";
									}else if("��һ����".equals(Message)){
										messageE += "a0141-select cv.code_name from a01 a01,code_value cv where a01.a0000 ='id' and cv.code_type='GB4762' and cv.code_value=a01.a0141,";
									}else if("�뵳ʱ��".equals(Message)){//??NO
										messageE += "rdsj,";
									}else if("�ڶ�����".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("��������".equals(Message)){
										messageE += "select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = 'id' and cv.code_value=a01.a3921 and cv.code_type='GB4762',";
									}else if("�μӹ���ʱ��".equals(Message)){
										messageE += "a0134-a01.a0134,";
									}else if("����״��".equals(Message)){
										messageE += "a0128-a01.a0128,";
									}else if("רҵ����ְ��".equals(Message)){
										messageE += "a0196-a01.a0196,";
									}else if("��Ϥרҵ�к�ר��".equals(Message)){
										messageE += "a0187a-a01.a0187a,";
									}else if("ѧ��".equals(Message)){//-----------------
										messageE += "a0801a-select a08.a0801a from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ҵѧУ��ѧ����".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ѧרҵ��ѧ����".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ѧʱ�䣨ѧ����".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("��ҵʱ�䣨ѧ����".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a08.a0834 = '1' and a08.a0899 = 'true',";
									}else if("ѧλ".equals(Message)){
										messageE += "a0901a-select a08.a0901a from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ҵѧУ��ѧλ��".equals(Message)){
										messageE += "a0814-select a08.a0814 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ѧרҵ��ѧλ��".equals(Message)){
										messageE += "a0824-select a08.a0824 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ѧʱ�䣨ѧλ��".equals(Message)){
										messageE += "a0804-select a08.a0804 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("��ҵʱ�䣨ѧλ��".equals(Message)){
										messageE += "a0807-select a08.a0807 from a08 a08 where  a08.A0000 = 'id' and a0835 = '1' and a08.a0899 = 'true',";
									}else if("ѧ����ȫ���ƣ�".equals(Message)){//------OK
										messageE += "QRZXL-a01.QRZXL,";
									}else if("��ѧʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '' group by a0804,";
									}else if("��ҵʱ�䣨ȫ���ƣ�".equals(Message)){//??OK
										messageE += "a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = 'id' group by a0807,";
									}else if("ѧλ��ȫ���ƣ�".equals(Message)){//OK
										messageE += "QRZXW-a01.QRZXW,";
									}else if("��ҵѧУ��ȫ����ѧ����".equals(Message)){//ok
										messageE += "a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ҵѧУ��ȫ����ѧλ��".equals(Message)){//ok
										messageE +="a0814-select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ��ȫ����ѧ����".equals(Message)){//??ok
										messageE +="a0824-select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ��ȫ����ѧλ��".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = 'id',";
									}else if("ѧ������ְ��".equals(Message)){//ok
										messageE +="ZZXL-a01.ZZXL,";
									}else if("��ѧʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="A0804-select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = 'id' group by a0804,";
									}else if("��ҵʱ�䣨��ְ��".equals(Message)){//??ok
										messageE +="a0807-select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = 'id' group by a0807,";
									}else if("ѧλ����ְ��".equals(Message)){//ok
										messageE +="ZZXW-a01.ZZXW,";
									}else if("��ҵѧУ����ְѧ����".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ҵѧУ����ְѧλ��".equals(Message)){//??ok
										messageE +="a0814-select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ����ְѧ����".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("��ѧרҵ����ְѧλ��".equals(Message)){//??ok
										messageE +="A0824-select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = 'id',";
									}else if("ȫ����ѧ���������".equals(Message)){//??ok
										messageE += "QRZXL-a01.QRZXL,";
									}else if("ȫ����ѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "QRZXLXX-a01.QRZXLXX,";
									}else if("ȫ����ѧλ�������".equals(Message)){//??ok
										messageE += "QRZXW-a01.QRZXW,";
									}else if("ȫ����ѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "QRZXWXX-a01.QRZXWXX,";
									}else if("��ְѧ���������".equals(Message)){//ok
										messageE += "ZZXL-a01.ZZXL,";
									}else if("��ְѧ����Ϣ�������".equals(Message)){//??ok
										messageE += "ZZXLXX-a01.ZZXLXX,";
									}else if("��ְѧλ�������".equals(Message)){//ok
										messageE += "ZZXW-a01.ZZXW,";
									}else if("��ְѧλ��Ϣ�������".equals(Message)){//??ok
										messageE += "ZZXWXX-a01.ZZXWXX,";
									}else if("������λ��ְ��ȫ��".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("������λ��ְ�񣨼�".equals(Message)){
										messageE += "a0192-a01.a0192,";
									}else if("�ǼǺ�����ְ��".equals(Message)){
										messageE += "a0192a-a01.a0192a,";
									}else if("�ǼǺ���������".equals(Message)){
										messageE += "a0120-a01.a0120,";
									}else if("ͳ�ƹ�ϵ���ڵ�λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a01.a0000= a02.a0000 ) a on  a.a0195 = a02.a0201b  where a02.a0000 = 'id',";//
									}else if("ѡ�����÷�ʽ".equals(Message)){
										messageE += "a0247-select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = 'id',";
									}else if("��ְ�ĺ�".equals(Message)){
										messageE += "a0245-a02.a0245,";
									}else if("�Ƿ��쵼ְ��".equals(Message)){
										messageE += "a0219-a02.a0219,";//
									}else if("��������".equals(Message)){
										messageE += "a0201a-select a02.a0201a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("ְ������".equals(Message)){
										messageE += "a0216a-select a02.a0216a from a02 a02 where a02.a0000 ='id' and a0255= '1',";
									}else if("��ְ����".equals(Message)){//?? o k=========================================
										messageE += "a0221-select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = 'id' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09',";
									}else if("��ְʱ��".equals(Message)){//??o  k 
										messageE += "rzsj,";
									}else if("����ְ����ʱ��".equals(Message)){//?? ok
										messageE += "rgzwccsj,";
									}else if("ְ������".equals(Message)){
										messageE += "a0251-a02.a0251,";
									}else if("��ְʱ��".equals(Message)){//??o k
										messageE += "mzsj,";
									}else if("���㹤����������".equals(Message)){
										messageE += "a0197-select (case when a0197 = '1' then '��' else '��' end ) from a01 where a0000 = 'id',";
									}else if("����".equals(Message)){//??o  k
										messageE += "jl,";
									}else if("�������".equals(Message)){
										messageE += "a14z101-a01.a14z101,";
									}else if("��ȿ��˽��".equals(Message)){ 
										messageE += "a15z101-a01.a15z101,";
									}else if("��ע".equals(Message)){
										messageE += "a0180-a01.a0180,";
									}else if("��ν����ͥ��Ա��".equals(Message)){//??ok
										messageE += "cw,";
									}else if("��������ͥ��Ա��".equals(Message)){//??ok
										messageE += "xm,";
									}else if("�������£���ͥ��Ա��".equals(Message)){//??ok
										messageE += "csnyjy,";
									}else if("���䣨��ͥ��Ա��".equals(Message)){//??
										messageE += "nljy,";//-------
									}else if("������ò����ͥ��Ա��".equals(Message)){//??ok  
										messageE += "zzmmjy,";
									}else if("������λ��ְ�񣨼�ͥ��Ա��".equals(Message)){//??ok
										messageE += "gzdwjzw,";
									}else if("���뱾��λ��ʽ".equals(Message)){
										messageE += "a2911-select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = 'id',";
									}else if("���뱾��λ����".equals(Message)){
										messageE += "a2907-a29.a2907,";
									}else if("ԭ��λ����".equals(Message)){
										messageE += "a2921a-a29.a2921a,";
									}else if("��ԭ��λְ��".equals(Message)){
										messageE += "a2941-a29.a2941,";
									}else if("��ԭ��λְ����".equals(Message)){
										messageE += "a2944-a29.a2944,";
									}else if("���빫��Ա����ʱ��".equals(Message)){
										messageE += "a2947-a29.a2947,";
									}else if("����Ա�Ǽ�ʱ��".equals(Message)){
										messageE += "a2949-a29.a2949,";
									}else if("����ְ��".equals(Message)){
										messageE += "a5304-a53.a5304,";
									}else if("����ְ��".equals(Message)){
										messageE += "a5315-a53.a5315,";
									}else if("��������".equals(Message)){
										messageE += "a5317-a53.a5317,";
									}else if("�ʱ���λ".equals(Message)){
										messageE += "a5319-a53.a5319,";
									}else if("���ʱ��".equals(Message)){//??ok
										messageE += "tbsjn,";//-------
									}else if("�����".equals(Message)){
										messageE += "a5327-a53.a5327,";
									}else if("�˳�����ʽ".equals(Message)){
										messageE += "a3001-select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = 'id',";
									}else if("������λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = 'id' group by a02.a0201a,";
									}else if("�˳�����ʱ��".equals(Message)){
										messageE += "a3004-a30.a3004,";
									}else if("�˳���λ".equals(Message)){
										messageE += "a0201a-select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = 'id' group by a02.a0201a,";
									}else if("�޸���".equals(Message)){
										messageE += "xgr-a01.xgr,";
									}else if("�޸�����".equals(Message)){
										messageE += "xgsj,";
									}else if("��ǰ����".equals(Message)){//??ok
										messageE += "dqsj,";//----?????
									}else if("��ǰ�û���".equals(Message)){//??ok
										messageE += "dqyhm,";//----
									}
									 								
								}
								//��ȡ�������ݰ�ǰ��<>ȥ��
							//	String Message = message.substring(1, message.length() - 1);
								//3��ʾ����
								Map<String, String> map = new HashMap<String, String>();
								//1��ʾ��
								map.put("1", row1);
								//2��ʾ��
								map.put("2", cell1);
								//3��ʾ����
								MessageA =	MessageA.substring(0, MessageA.length()-1);
								map.put("3", MessageA);
								//Ӣ��������
//									System.out.println("44------------>"+messageE);
									if(messageE == null || "".equals(messageE)){
										this.setMainMessage("����ʧ�ܣ�");
										return EventRtnType.NORMAL_SUCCESS;
									}else {
										messageE =	messageE.substring(0, messageE.length()-1);
									}
								
								map.put("4", messageE);
								list.add(map);
								 messageE = "";
								 MessageA = "";
								List Data = et.getChildren();
								//��ȡData�ڵ�
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
					System.out.println("������");
				}
				p++;
			}
			this.getPageElement("savescript").setValue(uuid);
			CreateTemplatePageModel.tpid = uuid;
			this.getExecuteSG().addExecuteCode("tishi()");
			this.getExecuteSG().addExecuteCode("window.parent.tabs.remove(thistab.tabid);");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (JDOMException e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		} catch (IOException e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		} catch (SQLException e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
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
//		System.out.println("�������");
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
			this.setMainMessage("����ͬʱ����������ģ�壡");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(gxycount != null && !"".equals(gxycount) && "1".equals(gxycount)){
			//��ȡѡ�й���ģ���id
			String sql = "select uuid from powergx where modelid= '"+gxyid.substring(1,gxyid.length()-1)+"' and owenr = '"+SysManagerUtils.getUserId()+"' ";
			ResultSet rs = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
			if(!rs.next()){
				this.setMainMessage("�����߿����޸Ĵ�ģ��!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			this.setMainMessage("�����Զ���ģ���н���������!");
//		}else if((Integer.parseInt(zdycount) == 0 || "".equals(zdycount))&& (Integer.parseInt(gxycount) == 0  ||  "".equals(gxycount)) && (Integer.parseInt(bzcount) == 0 || "".equals(bzcount))){
		}else if(("".equals(zdycount)||"0".equals(zdycount)) && ("".equals(gxycount)||"0".equals(gxycount)) && ("".equals(bzcount) || "0".equals(bzcount))){
			this.setMainMessage("��ѡ��ģ�壡");
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
	 * ��Ϊ����ķ���
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
			this.setMainMessage("����ѡ����ű�ᣡ");
			return EventRtnType.NORMAL_SUCCESS;
		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("��׼ģ�岻����������");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("����ģ�岻���ٴι���");
			return EventRtnType.NORMAL_SUCCESS;*/
		}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) == 0){
			this.setMainMessage("��ѡ���Զ���ģ�壡");
			return EventRtnType.NORMAL_SUCCESS;
		}else{//5����������
			HBSession sess = HBUtil.getHBSession();
			zdyid = zdyid.substring(1, zdyid.length()-1);
			String update = "update listoutput set typestate = '2' where tpid = '"+zdyid+"'";
			sess.createSQLQuery(update).executeUpdate();
			//request.getSession().setAttribute("zdyid",zdyid);//��Ϊ����ʱ��ģ��id�浽powergx������
			//String ctxPath = request.getContextPath();
			//this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','�û���Ϣ',440,400,'JgTree','"+ctxPath+"');");
			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
			System.out.println(sql);
			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
			this.getPageElement("GXYtemCount").setValue("");
			this.setMainMessage("�������");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid1').store.reload();");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ȡ������ 
	 * @return
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvent("renameTPS2")
	public int renameTPS2() throws RadowException, SQLException {
		//----------------------------------�������------------------------
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
//			this.setMainMessage("����ѡ�����ģ�壡");
//			return EventRtnType.NORMAL_SUCCESS;
//		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
//			this.setMainMessage("��׼ģ�岻����������");
//			return EventRtnType.NORMAL_SUCCESS;
//		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
//			this.setMainMessage("����ģ�岻���ٴι���");
//			return EventRtnType.NORMAL_SUCCESS;*/
//		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) == 0){
//			this.setMainMessage("��ѡ����ģ�壡");
//			return EventRtnType.NORMAL_SUCCESS;
//		}else{
//			GXYtemID = GXYtemID.substring(1, GXYtemID.length()-1);
//			request.getSession().setAttribute("GXYtemID",GXYtemID);
//			String ctxPath = request.getContextPath();
//			this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','�û���Ϣ',440,400,'JgTree','"+ctxPath+"');");
//			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
//			System.out.println(sql);
//			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
//			this.getPageElement("GXYtemCount").setValue("");
//			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('templateInfoGrid2').store.reload();");
//	    }
		//------------------------------������----------------------------------
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
			this.setMainMessage("����ѡ����ű�ᣡ");
			return EventRtnType.NORMAL_SUCCESS;
		/*}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("��׼ģ�岻����������");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(((gxycount != null && !"".equals(gxycount))? Integer.parseInt(gxycount):Integer.parseInt("0")) > 1){
			this.setMainMessage("����ģ�岻���ٴι���");
			return EventRtnType.NORMAL_SUCCESS;*/
		}else if(((zdycount != null && !"".equals(zdycount))? Integer.parseInt(zdycount):Integer.parseInt("0")) == 0){
			this.setMainMessage("��ѡ���Զ���ģ�壡");
			return EventRtnType.NORMAL_SUCCESS;
		}else{//5����������
			HBSession sess = HBUtil.getHBSession();
			zdyid = zdyid.substring(1, zdyid.length()-1);
			String update = "update listoutput set typestate = '1' where tpid = '"+zdyid+"'";
			sess.createSQLQuery(update).executeUpdate();
			//request.getSession().setAttribute("zdyid",zdyid);//��Ϊ����ʱ��ģ��id�浽powergx������
			//String ctxPath = request.getContextPath();
			//this.getExecuteSG().addExecuteCode("$h.openWin('transferSysOrgWin','pages.sysorg.org.JgTree','�û���Ϣ',440,400,'JgTree','"+ctxPath+"');");
			/*String sql = "update listoutput set  tptype = '5' where tpid in '"+zdyid+"'";
			System.out.println(sql);
			int e = HBUtil.getHBSession().connection().createStatement().executeUpdate(sql);*/
			this.getPageElement("GXYtemCount").setValue("");
			this.setMainMessage("ȡ���ɹ�");
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
			this.setMainMessage("ɾ��ʧ�ܣ�");
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
			this.setMainMessage("ɾ��ʧ�ܣ�");
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ��ѯ�Ƿ���Ȩ��
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
			this.setMainMessage("���޴�Ȩ��");	
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
		this.getExecuteSG().addExecuteCode("$h.openWin('endAgeTimeWin','pages.publicServantManage.SetTemEndTime','�����������ʱ���',315,315,'"+templateid+"',ctxPath)");
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
			this.setMainMessage("ģ�����ظ�������ĺ��������룡");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��׼ģ�壬�б���ѡ���¼�
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
		//1.��ѡ���У�ȡ�������еĹ�ѡ  
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
		
		
		//���Զ���ģ�壬����ģ��Ĺ�ѡȥ��
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
	 * �Զ���ģ�壬�б���ѡ���¼�
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
		//1.��ѡ���У�ȡ�������еĹ�ѡ  
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
		
		//����׼ģ�壬����ģ��Ĺ�ѡȥ��
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
	 * ����ģ�壬�б���ѡ���¼�
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
		//1.��ѡ���У�ȡ�������еĹ�ѡ  
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
		
		//����׼ģ�壬�Զ���ģ��Ĺ�ѡȥ��
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
