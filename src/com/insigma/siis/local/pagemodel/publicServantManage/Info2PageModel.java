package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.Query;
import org.json.JSONException;
import org.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtGroup;
import com.insigma.odin.framework.privilege.entity.SmtUser;
import com.insigma.odin.framework.privilege.helper.GroupHelper;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.util.HashCodeUtil;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.MD5;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A05;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.A71;
import com.insigma.siis.local.business.entity.AddValue;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.entity.InfoGroup;
import com.insigma.siis.local.business.entity.UserDept;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.ReturnDO;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel;
/**
 * ????????????
 * @author huangcheng
 *
 */
public class Info2PageModel extends PageModel{
	
	
	protected static final String LEFTTREE = "1";//??????????????
	protected static final String OPENTREE = "2";//??????
	protected static final String THREETREE = "3";//????????
	protected static final String FOURTREE = "4";//????????????
	protected static final String FIVETREE = "5";//??????????????
	protected static final String SIXTREE = "6";//??????????????
	
	private LogUtil applog = new LogUtil();
	public static int treeType = 0;
	//??????????
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.createPageElement("div1",ElementType.NORMAL,false).setDisplay(true);		//????????????
		this.createPageElement("div2",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div3",ElementType.NORMAL,false).setDisplay(false);		//??????
		this.createPageElement("div4",ElementType.NORMAL,false).setDisplay(false);		//??????
		this.createPageElement("div5",ElementType.NORMAL,false).setDisplay(false);		//??????????????????
		this.createPageElement("div6",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div7",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div8",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div9",ElementType.NORMAL,false).setDisplay(false);		//??????????????????
		this.createPageElement("div10",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div11",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div12",ElementType.NORMAL,false).setDisplay(false);		//????????????
		this.createPageElement("div13",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div14",ElementType.NORMAL,false).setDisplay(false);		//????????
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//??????????????
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String a0000 = this.getRadow_parent_data();
		//String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a0000 = this.getPageElement("a0000").getValue();//????????????????
		
		//????"nowNumber"???????????????? ???????????? ????????????
		Object obj = this.request.getSession().getAttribute("nowNumberXX");
		if(obj==null || "".equals(obj.toString())){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('lastp').hide();odin.ext.getCmp('nextp').hide();");
		}
		
		HBSession sess = HBUtil.getHBSession();
		
		if(a0000==null||"".equals(a0000)){  		//????a0000??????????????
			String random = UUID.randomUUID().toString();	//????????
			A01 a01 = new A01();
			a01.setA0000(random);
			a01.setA0163("1");//????????????
			//a01.setA0104("1");//??????
			a01.setA14z101("??");//????????
			a01.setStatus("4");
			a01.setA0197("0");//????????????????????????
			addUserInfo(a01);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);		//????????a01??????????????
			
			
		}else{				//????????????????????????
			a0000 = a0000.substring(6);
			
			try {
				//????????????????
				A01 a01 = (A01)sess.get(A01.class, a0000);
				PMPropertyCopyUtil.copyObjValueToElement(a01, this);
				
				
				//????????????????????????????
				genResume();
				
				
				//??????????????
				this.setNextEventName("WorkUnitsGrid.dogridquery");
				//???????????????? ??????????????????
				if(a01.getA0195()!=null){
					String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
					if(a0195!=null){
						this.getPageElement("a0195_combo").setValue(a0195);//???????? ??????
					}
				}
				
				//??????????????
				String sqlA051 = "from A05 where a0000='" + a0000+ "' and a0531='1' order by a0525 desc,a0501b asc";
				List listA051 = sess.createQuery(sqlA051).list();
				A05 a05 = null;
				if(listA051 != null && listA051.size()>0){
					a05 = (A05) listA051.get(0);
					a05.setA0000(a0000);
				}

				if (a05 != null) {
					PMPropertyCopyUtil.copyObjValueToElement(a05, this);
				}
				this.setNextEventName("TrainingInfoGrid.dogridquery");// ??????????????
				
				
				//??????????????
				String sqlA050 = "from A05 where a0000='" + a0000+ "' and a0531='0' order by a0525 desc,a0501b asc";
				List listA050 = sess.createQuery(sqlA050).list();
				A05 a050 = null;
				
				if(listA050 != null && listA050.size()>0){
					
					a050 = (A05) listA050.get(0);
					a050.setA0000(a0000);
					
				}
				
				if (a050 != null) {
					
					this.getPageElement("a0500Post").setValue(a050.getA0500());
					this.getPageElement("a0501bPost").setValue(a050.getA0501b());
					this.getPageElement("a0524Post").setValue(a050.getA0524());
					this.getPageElement("a0504Post").setValue(a050.getA0504());
					this.getPageElement("a0517Post").setValue(a050.getA0517());
					this.getPageElement("a0504Post_1").setValue(a050.getA0504());
					this.getPageElement("a0517Post_1").setValue(a050.getA0517());
					
					/*this.getExecuteSG().addExecuteCode("document.getElementById('a0500Post').value = '"+a050.getA0500()+"';"
							+ "document.getElementById('a0501bPost').value = '"+a050.getA0501b()+"';"
							+ "document.getElementById('a0524Post').value = '"+a050.getA0524()+"';"
							+ "document.getElementById('a0504Post').value = '"+a050.getA0504()+"';"
							+ "document.getElementById('a0517Post').value = '"+a050.getA0517()+"';"
						);*/
					
				}
				this.setNextEventName("rankGrid.dogridquery");// ??????????????
				
				
				//????????????????????
				this.setNextEventName("professSkillgrid.dogridquery");
				
				
				//??????????????????
				this.setNextEventName("degreesgrid.dogridquery");
				
				//??????????????
				this.setNextEventName("RewardPunishGrid.dogridquery");
				
				
				//????????????????
				this.setNextEventName("AssessmentInfoGrid.dogridquery");//????????????????
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
				List<A15> list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					A15 a15 = list.get(0);
					this.getPageElement("a1527").setValue(a15.getA1527());
				}
				
				
				//??????????????????????????
				this.setNextEventName("familyid.dogridquery");
				
				
				//??????????????????
				A29 a29 = (A29) sess.get(A29.class, a0000);
				
				if(a29 != null){
					//????????????????????
					String a2944s = a29.getA2944();
					if(a2944s!=null && !a2944s.trim().equals("null")) {
						this.getExecuteSG().addExecuteCode("document.getElementById('a2944s_combo').value='"+a2944s+"';");
					}
					if (a29 != null) {
						PMPropertyCopyUtil.copyObjValueToElement(a29, this);
					}
				}
				
				
				
				
				
				//??????????????????
				A30 a30 = (A30) sess.get(A30.class, a0000);
				Map<String,String> map=new LinkedHashMap<String,String>();
				String sqlx="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
				List<Object[]> list1=sess.createSQLQuery(sqlx).list();
				if(list1!=null&&list1.size()>0){
					for(Object[] a02:list1){
						map.put(a02[1].toString(), a02[0].toString());
					}
					((Combo)this.getPageElement("orgid")).setValueListForSelect(map);
				}
				if (a30 != null) {
					PMPropertyCopyUtil.copyObjValueToElement(a30, this);
					
					//????????????????????????????????
					String a3001 = a30.getA3001();
					if(null != a3001 && !"".equals(a3001) && a3001.startsWith("3")){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
						if(null!=a01.getOrgid()){
							String orgName= HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getOrgid()+"'");
							if(orgName!=null){
								this.getPageElement("orgid_combo").setValue(orgName);
							}
						}
					}else if(null == a3001 || "".equals(a3001)){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
					}else if(null != a3001 && !"".equals(a3001) && (a3001.startsWith("1")||a3001.startsWith("2"))){
						this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
						if(null != a30.getA3007a()){
							String a3007a = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a30.getA3007a()+"'");
							if(a3007a!=null){
								this.getPageElement("a3007a_combo").setValue(a3007a);//???????? ??????
							}
						}
					}
				}else{
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				}
				
				
				
				//????????????????
				String sqlA53 = "from A53 where a0000='" + a0000 + "' and a5399='"+SysManagerUtils.getUserId()+"'";
				List listA53 = sess.createQuery(sqlA53).list();
				if (listA53 != null && listA53.size() > 0) {
					A53 a53 = (A53) listA53.get(0);
					PMPropertyCopyUtil.copyObjValueToElement(a53, this);
				}else{
					String date = DateUtil.getcurdate();
					A53 a53 = new A53();
					a53.setA5321(date);
					a53.setA5323(date);
					a53.setA5327(SysManagerUtils.getUserName());
				}
				
				
				//??????????????
				String sqlA71 = "from A71 where a0000='" + a0000 + "'";
				List listA71 = sess.createQuery(sqlA71).list();
				if (listA71 != null && listA71.size() > 0) {
					A71 a71 = (A71) listA71.get(0);
					PMPropertyCopyUtil.copyObjValueToElement(a71, this);
				}
				
				//????????????????
				readInfo_Extend(sess,a0000);
				
				
				//????????????????????c.type??????????????(0????????1??????)
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
				/*------------????????????????????????????--------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//????????????
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//??????????????????,??????????????????
					//????????????????????????????
					this.getExecuteSG().addExecuteCode("Ext.getCmp('InfoSaveA01').setDisabled(true);Ext.getCmp('save').setDisabled(true);"
							+ "Ext.getCmp('saveA06').setDisabled(true);Ext.getCmp('saveA08').setDisabled(true);Ext.getCmp('saveA14').setDisabled(true);"
							+ "Ext.getCmp('saveA15').setDisabled(true);Ext.getCmp('saveA36').setDisabled(true);Ext.getCmp('bc10').setDisabled(true);"
							+ "Ext.getCmp('bc11').setDisabled(true);Ext.getCmp('bc12').setDisabled(true);Ext.getCmp('bc13').setDisabled(true);"
							+ "Ext.getCmp('saveA050').setDisabled(true);Ext.getCmp('saveA051').setDisabled(true);"
							+ "document.getElementById('isUpdate').value = '2'"
							
						);
				}
				if(elist2==null||elist2.size()==0){//????????
					if(elist!=null&&elist.size()>0){//??????????
						//????????????????????????????
						this.getExecuteSG().addExecuteCode("Ext.getCmp('InfoSaveA01').setDisabled(true);Ext.getCmp('save').setDisabled(true);"
							+ "Ext.getCmp('saveA06').setDisabled(true);Ext.getCmp('saveA08').setDisabled(true);Ext.getCmp('saveA14').setDisabled(true);"
							+ "Ext.getCmp('saveA15').setDisabled(true);Ext.getCmp('saveA36').setDisabled(true);Ext.getCmp('bc10').setDisabled(true);"
							+ "Ext.getCmp('bc11').setDisabled(true);Ext.getCmp('bc12').setDisabled(true);Ext.getCmp('bc13').setDisabled(true);"
							+ "Ext.getCmp('saveA050').setDisabled(true);Ext.getCmp('saveA051').setDisabled(true);"
							+ "document.getElementById('isUpdate').value = '2'"
						);
					}else{	
						//????????????????????????????????????????????????????????????????????1 ??????????????????????????
						/*if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//??????????
							this.getExecuteSG().addExecuteCode("Ext.getCmp('InfoSaveA01').setDisabled(true);Ext.getCmp('save').setDisabled(true);"
									+ "Ext.getCmp('saveA06').setDisabled(true);Ext.getCmp('saveA08').setDisabled(true);Ext.getCmp('saveA14').setDisabled(true);"
									+ "Ext.getCmp('saveA15').setDisabled(true);Ext.getCmp('saveA36').setDisabled(true);Ext.getCmp('bc10').setDisabled(true);"
									+ "Ext.getCmp('bc11').setDisabled(true);Ext.getCmp('bc12').setDisabled(true);Ext.getCmp('bc13').setDisabled(true);"
									+ "Ext.getCmp('saveA050').setDisabled(true);Ext.getCmp('saveA051').setDisabled(true);"
									+ "document.getElementById('isUpdate').value = '2'"
								);
						}*/
					}
				}
				
				
			} catch (Exception e) {
				this.setMainMessage("??????????");
				return EventRtnType.FAILD;
			}
			
			
		}
		
		
		//????
		this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+a0000+"'");
		A57 a57 = (A57)sess.get(A57.class, a0000);
		if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
			String photourl = a57.getPhotopath();
			File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
			if(fileF.isFile()){
				long nLen = fileF.length();
				String imageSize = nLen/1024 + "K";
				this.getExecuteSG().addExecuteCode("document.getElementById('personImg').alt='????("+imageSize+")'");
			}
		}
		
		//????????????????????
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
		
		//????????????????????????????????????????IE8????????????????
		this.getExecuteSG().addExecuteCode("document.getElementById('a1701').focus();");
		
		//this.getExecuteSG().addExecuteCode("document.getElementById('a7101').focus();");
		
		
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");		//????????
		this.getExecuteSG().addExecuteCode("changedispaly();");			//????????
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");		//??????
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("lastp.onclick")
	@NoRequiredValidate
	public int lastp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000MapXX");
		Object o = this.request.getSession().getAttribute("nowNumberXX");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2XX");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumberXX");
		}
		if(num-1<0){
			throw new RadowException("??????????????????");
		}
		String nextA0000 = "update"+(String)map.get(num-1);
		this.request.getSession().setAttribute("nowNumberXX",num-1);//????num
		this.request.getSession().setAttribute("nowNumber2XX",num-1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		this.getPageElement("a0000").setValue(nextA0000);//????????????
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("nextp.onclick")
	@NoRequiredValidate
	public int nextp() throws RadowException{
		Map<Integer, Object> map = (Map)this.request.getSession().getAttribute("a0000MapXX");
		Object o = this.request.getSession().getAttribute("nowNumberXX");
		Integer num = 0;
		if(o == null || "".equals(o.toString()) ){
			num = (Integer)this.request.getSession().getAttribute("nowNumber2XX");
		}else{
			num = (Integer) this.request.getSession().getAttribute("nowNumberXX");
		}
		Integer bigNumber = (Integer) this.request.getSession().getAttribute("bigNumberXX");
		if(num + 1 >= bigNumber){
			throw new RadowException("????????????????????");
		}
		String nextA0000 = "update"+(String)map.get(num+1);
		this.request.getSession().setAttribute("nowNumberXX",num+1);//????num
		this.request.getSession().setAttribute("nowNumber2XX",num+1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		this.getPageElement("a0000").setValue(nextA0000);//????????????
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void addUserInfo(A01 a01) {
		a01.setTbr(SysManagerUtils.getUserId());
		//a01.setTbrjg(SysManagerUtils.getUserOrgid());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("????");
	}
	
	
	/**
	 * ????tab????????????????????????????????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("dj.onclick")
	@NoRequiredValidate
	public int dj(String b) throws RadowException {
		
		this.createPageElement("div1",ElementType.NORMAL,false).setDisplay(false);		//????????????
		this.createPageElement("div2",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div3",ElementType.NORMAL,false).setDisplay(false);		//??????
		this.createPageElement("div4",ElementType.NORMAL,false).setDisplay(false);		//??????
		this.createPageElement("div5",ElementType.NORMAL,false).setDisplay(false);		//??????????????????
		this.createPageElement("div6",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div7",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div8",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div9",ElementType.NORMAL,false).setDisplay(false);		//??????????????????
		this.createPageElement("div10",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div11",ElementType.NORMAL,false).setDisplay(false);		//??????????????
		this.createPageElement("div12",ElementType.NORMAL,false).setDisplay(false);		//????????????
		this.createPageElement("div13",ElementType.NORMAL,false).setDisplay(false);		//??????????
		this.createPageElement("div14",ElementType.NORMAL,false).setDisplay(false);		//??????????
		
		//??????????????????????
		if(b != null && !b.equals("")){
			if("11".equals(b)){
				HBSession sess=HBUtil.getHBSession();
				String a0000 = this.getPageElement("subWinIdBussessId").getValue();
				String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
				List<Object[]> list=sess.createSQLQuery(sql).list();
				if(list!=null&&list.size()>0){
					
				}else{
					this.setMainMessage("????????????????????????");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('bc11').disable()");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				}
			}
			this.createPageElement("div"+b,ElementType.NORMAL,false).setDisplay(true);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	 
	@PageEvent("InfoSave.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int infoSave(String confirm) throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		
		//String a0000 = this.getRadow_parent_data();//????????????????
		String a0000 = this.getPageElement("a0000").getValue();//????????????????
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????????????????????????????
		//??????????????
		String a0101 = this.getPageElement("a0101").getValue();
		if(a0101 == null || "".equals(a0101)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????
		String a0104 = this.getPageElement("a0104").getValue();
		if(a0104 == null || "".equals(a0104)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????
		String a0107 = this.getPageElement("a0107").getValue();
		if(a0107 == null || "".equals(a0107)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????
		String a0117 = this.getPageElement("a0117").getValue();
		if(a0117 == null || "".equals(a0117)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????
		String a0111 = this.getPageElement("a0111").getValue();
		if(a0111 == null || "".equals(a0111)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//????????????????
		String a0114 = this.getPageElement("a0114").getValue();
		if(a0114 == null || "".equals(a0114)){
			this.setMainMessage("????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????????
		String a0134 = this.getPageElement("a0134").getValue();
		if(a0134 == null || "".equals(a0134)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????
		String a0128 = this.getPageElement("a0128").getValue();
		if(a0128 == null || "".equals(a0128)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????????
		String a0184 = this.getPageElement("a0184").getValue();
		if(a0184 == null || "".equals(a0184)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//----------------------------????????????-----------------
		//????a0187a
		String a0187a = this.getPageElement("a0187a").getValue();
		if(a0187a != null || "".equals(a0187a)){
			if(a0187a.length() > 60){
				this.setMainMessage("????????????60????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//??????????????????
		String a0165 = this.getPageElement("a0165").getValue();
		if(a0165 == null || "".equals(a0165)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????
		String a0160 = this.getPageElement("a0160").getValue();
		if(a0160 == null || "".equals(a0160)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//??????????????????
		String a0121 = this.getPageElement("a0121").getValue();
		if(a0121 == null || "".equals(a0121)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Date date= new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		String now=sdf.format(date);
		if(a0134.length()==6){
			a0134 += "00";
		}
		if(a0134.compareTo(now)>0){
			this.setMainMessage("????????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		try {
			
			A01 a01=new A01();
			this.copyElementsValueToObj(a01, this);
			
			//????????????????
			ReturnDO<Boolean> returnDO = this.savePerson(confirm);			
			if(!returnDO.isSuccess()){
				this.setMainMessage(returnDO.getErrorMsg());
				return EventRtnType.FAILD;
			}
			
			//??????????????????????????????subWinIdBussessId????????a0000??????????????????????????????
			this.getExecuteSG().addExecuteCode("document.getElementById('subWinIdBussessId').value = '"+(a0000==null?"":a0000)+"';");
			
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("??????????");
		//this.getExecuteSG().addExecuteCode("radow.doEvent('getTreeJsonData')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ????????????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("savePerson")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public ReturnDO<Boolean> savePerson(String confirm)throws RadowException, AppException{
		ReturnDO<Boolean> returnDO = new ReturnDO<Boolean>();
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();//????????????????
		
		
		//????????????????????????????
		String a0134 = a01.getA0134();//????????????
		String a0107 = a01.getA0107();//????????
		if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a0107.length()==6){
				a0107 += "00";
			}
			if(a0134.compareTo(a0107)<=0){
				returnDO.setErrorMsg("99999", "????????????????????????????????!");
				return returnDO;
			}
		}
		
		
		HBSession sess = null;
		A01 a01_old = null;
		JSONObject jsonbject = null;
		try {
			sess = HBUtil.getHBSession();
			
			String idcard = a01.getA0184();//???????? ????????//??????????????????x?????????????? add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
						returnDO.setErrorMsg("99999", "??????????????????????????????????,????????");
						return returnDO;
			}
			
			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//????????
			
			if(a0000==null||"".equals(a0000)){
				//????????  1???????? 2??????
				a01.setA0163("1");
				this.getPageElement("a0163").setValue("1");
				sess.save(a01);	
			}else{
				a01_old = a01;
				Object old = sess.get(A01.class, a0000);
				if(old != null){			//????????????a01_old??????????????
					a01_old = (A01)old;
					a01.setA0221(a01_old.getA0221());		//??????
					a01.setA0288(a01_old.getA0288());		//??????????
					a01.setA0192e(a01_old.getA0192e());		//??????
					a01.setA0192c(a01_old.getA0192c());		//??????????
					
				}
				
				if("4".equals(a01.getStatus())){//??????????????????????????????????????  ????????????
					a01.setStatus("1");
					String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
					List<String> list2 = sess.createSQLQuery(sql2).list();
					for(int i=0;i<list2.size();i++){
						CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
					}
					this.getPageElement("status").setValue(a01.getStatus());
					new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A01(), a01)).start();
				}else{
					a01.setXgr(null);
					a01.setXgsj(null);
					a01_old.setXgr(null);
					a01_old.setXgsj(null);
					new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a01_old, a01)).start();
					
				}
				
				
				PropertyUtils.copyProperties(a01_old, a01);
				//??????????
				StringBuffer originaljl = new StringBuffer("");
				String jianli = formatJL(a01_old.getA1701(),originaljl);
				jsonbject = objectToJson(a01_old);
				a01_old.setA1701(originaljl.toString());
				this.getPageElement("a1701").setValue(jianli);
				sess.saveOrUpdate(a01_old);
			}
			
			//??????????????????????????????????????????title
			//this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");
			a0000 = a01.getA0000();
			
			
			sess.flush();			
			//???? ????????????
			
			String json = jsonbject.toString();
			//this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");   ????????????????????????????
			
			this.getExecuteSG().addExecuteCode("A01value="+json);
			//this.setMainMessage("??????????");
		} catch (Exception e) {
			e.printStackTrace();
			/*this.setMainMessage("????????????????????");
			return EventRtnType.FAILD;*/
			returnDO.setErrorMsg("99999", "????????????????????");
			return returnDO;
		}
		
		
		return returnDO;
	}
	
	
	
	
	/** *********************************************??????????????(a02)******************************************************************** */
	@PageEvent("saveWorkUnits.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveWorkUnits()throws RadowException, AppException{
		A02 a02 = new A02();
		this.copyElementsValueToObj(a02, this);
		String a0201bb = this.getPageElement("a0201b").getValue();
		String a0201a = this.getPageElement("a0201b_combo").getValue();//???????? ??????
		a02.setA0201a(a0201a);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String aa0201b = this.getPageElement("a0201b").getValue();
		if(aa0201b == null || "".equals(aa0201b)){
			this.setMainMessage("????????/???????? ??????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String a0255 = this.getPageElement("a0255").getValue();
		if(a0255 == null || "".equals(a0255)){
			this.setMainMessage("??????????????/???????? ??????????");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String b0194 = this.getPageElement("b0194Type").getValue();
		if("1".equals(b0194)){
			String a0201d = a02.getA0201d();
			
			/*if(a0201d == null || "".equals(a0201d.trim()) || a0201d.equals("0")){
				this.setMainMessage("????????????????????????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//???????????? ??'??'??
			if("1".equals(a0201d)){
				String a0201e = this.getPageElement("a0201e_combo").getValue();//a0201e_combo
				if(a0201e == null || "".equals(a0201e.trim())){
					this.setMainMessage("???????????? ??'??'??????????????????????");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		
		if("2".equals(b0194)){
			a02.setA0201d(null);
		}
		
		String a0216a = this.getPageElement("a0216a").getValue();
		if(a0216a == null || "".equals(a0216a)){
			this.setMainMessage("???????? ??????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a0216a != null || !"".equals(a0216a)){
			if (a0216a.indexOf(" ") >=0){
				this.setMainMessage("??????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String reg = "[\u4E00-\u9FA5a-zA-Z0-9]+";	//????????????????????????
			
			String a0216a_reg=a0216a.replace(",", "").replace("??", "").replace("??", "");
			if(!a0216a_reg.matches(reg)){
				this.setMainMessage("????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		//1????2??
		String aa0219 = a02.getA0219();
		if(aa0219 != null && "0".equals(aa0219)){
			a02.setA0219("2");
		}
		//????????????????????????
		String a0265 = a02.getA0265();//????????
		String a0243 = a02.getA0243();//????????
		if(a0265!=null&&!"".equals(a0265)&&a0243!=null&&!"".equals(a0243)){
			if(a0265.length()==6){
				a0265 += "00";
			}
			if(a0243.length()==6){
				a0243 += "00";
			}
			if(a0265.compareTo(a0243)<0){
				this.setMainMessage("????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//????????a0245
		String a0245 = this.getPageElement("a0245").getValue();
		if(a0245 != null || "".equals(a0245)){
			if(a0245.length() > 130){
				this.setMainMessage("????????????????130????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//????????a0267
		String a0267 = this.getPageElement("a0267").getValue();
		if(a0267 != null || "".equals(a0267)){
			if(a0267.length() > 12){
				this.setMainMessage("????????????????12????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//????????a0216a
		if(a0216a.length() > 50){
			this.setMainMessage("????????????????50????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a02.setA0000(a0000);
		String a0200 = this.getPageElement("a0200").getValue();
		Boolean updateJGZW = false;
		HBSession sess = null;
		try {
			
			String a0201b = a02.getA0201b();//??????????????			
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//????????????????????????????????????????????
			/*if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sql).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}*/
			
			if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
				String sql="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
				
				Object sqlA0225 = sess.createSQLQuery(sql).uniqueResult();
				
				String maxA0225  =  null;
				if(sqlA0225 != null){
					maxA0225  = sqlA0225.toString();
				}
				
				if(maxA0225!=null && !maxA0225.equals("")){
					Long a0225 = Long.valueOf(maxA0225);
					a02.setA0225(a0225 + 1L);
				}else{		//????????????????????????????????a0225
					a02.setA0225(1L);
				}
				
				//????????????????????????????N??N>1????????????????a0225??????????????????????????a0225????(????????????????????????a0225????)
				String sqlTwo="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sqlTwo).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
				
				
			}
			
			if(a0201bb!=null&&!"".equals(a0201bb)){//????????????
				if("-1".equals(a0201bb)){//????????
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201bb);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				
				
			}
			
			if(a0200==null||"".equals(a0200)){
				a02.setA0281("true");//????????
				this.getPageElement("a0281").setValue("true");
				
				applog.createLog("3021", "A02", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A02(), a02));
				sess.save(a02);	
				updateJGZW = true;
			}else{
				if(a02.getA0281()==null||"".equals(a02.getA0281())){
					a02.setA0281("true");//????????
					this.getPageElement("a0281").setValue("true");
				}
				
				A02 a02_old = (A02)sess.get(A02.class, a0200);
				String jg_old = a02_old.getA0201a();//????
				String jg =  a02.getA0201a();//????
				if(jg_old!=null&&!jg_old.equals(jg)){
					updateJGZW = true;
				}else if(jg_old==null&&jg!=null){
					updateJGZW = true;
				}
				String zw_old = a02_old.getA0216a();//????
				String zw =  a02.getA0216a();//????
				
				if(zw_old!=null&&!zw_old.equals(zw)){
					updateJGZW = true;
				}else if(zw_old==null&&zw!=null){
					updateJGZW = true;
				}
				String rzzt_old = a02_old.getA0255();//????????
				String rzzt = a02.getA0255();//????????
				if(rzzt_old!=null&&!rzzt_old.equals(rzzt)){
					updateJGZW = true;
				}else if(rzzt_old==null&&rzzt!=null){
					updateJGZW = true;
				}
				applog.createLog("3022", "A02", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a02_old, a02));
				String b0111 = a02_old.getA0201b();
				String b0111s = a02.getA0201b();
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111s);
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
				PropertyUtils.copyProperties(a02_old, a02);
				
				sess.update(a02_old);	
			}
			//????????
			String a0192a = this.getPageElement("a0192a").getValue();
			String a0192 = this.getPageElement("a0192").getValue();
			String a0197 = this.getPageElement("a0197").getValue();
			String a0195 = this.getPageElement("a0195").getValue();
			a01.setA0195(a0195);
			a01.setA0192a(a0192a);
			a01.setA0192(a0192);
			a01.setA0197(a0197);
			//????????????????
			//????????
			/*this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");*/
			updateA01(a01,sess);
			//??????????????????????????a01??a0148????????
			sess.update(a01);
			sess.flush();
			
			this.getPageElement("a0200").setValue(a02.getA0200());//??????????id??????????????
			//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//??????????????????????
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"radow.doEvent('genResume');");
			
			//????????????????????????????
			/*String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}*/
			
			if(updateJGZW){
				this.getExecuteSG().addExecuteCode("$h.confirm('????????','???????????????????????????????????????????????????',350,function(id){" +
						"if(id=='ok'){" +
							"radow.doEvent('UpdateTitleBtn.onclick');	" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				this.setMainMessage("??????????");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 */
	@PageEvent("genResume")
	@NoRequiredValidate
	public int genResume() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		//????????????
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlA02 = "from A02 where a0000='"+a0000+"'";
			List<A02> listA02 = sess.createQuery(sqlA02).list();
			Collections.sort(listA02, new Comparator<A02>(){
				@Override
				public int compare(A02 o1, A02 o2) {
					String o1sj = o1.getA0243()==null?"":o1.getA0243();//????????
					String o2sj = o2.getA0243()==null?"":o2.getA0243();//????????
					if("".equals(o1sj)){
						return -1;
					}else if("".equals(o2sj)){
						return 1;
					}else{
						if(o1sj.length()>=6){
							String d1 = o1sj.substring(0,6);
							String d2 = o2sj.substring(0,6);
							return d1.compareTo(d2);
						}
						
					}
					return 0;
				}
			});
			StringBuffer sb = new StringBuffer("");
			if(listA02!=null&&listA02.size()>0){
				
				for(int i=0;i<listA02.size();i++){
					A02 a02 = listA02.get(i);
					A02 a02Next = new A02();
					if(listA02.size()>i+1){
						a02Next = listA02.get(i+1);
					}
					String a0201a = a02.getA0201a()==null?"":a02.getA0201a();//????????
					String a03015 = a02.getA0216a()==null?"":a02.getA0216a();//????????
					String a0203 = a02.getA0243()==null?"":a02.getA0243();//????????
					String a0255 = a02.getA0255()==null?"":a02.getA0255();//????????
					String a0265 = a02.getA0265()==null?"":a02.getA0265();//????????
	
					B01 b01 = null;
					if(a02.getA0201b()!=null){
						b01 = (B01)sess.get(B01.class, a02.getA0201b());
					}
					
					if(b01 != null){//???????????? ?? ?????????????????? ???? ????????
						String b0194 = b01.getB0194();//1????????????2????????????3????????????
						if("2".equals(b0194)){//2??????????
							while(true){
								b01 = (B01)sess.get(B01.class, b01.getB0121());
								if(b01==null){
									break;
								}else{
									b0194 = b01.getB0194();
									if("2".equals(b0194)){//2??????????
										a0201a = b01.getB0101()+a0201a;
									}else if("3".equals(b0194)){//3??????????
										continue;
									}else if("1".equals(b0194)){//1??????????
										a0201a = b01.getB0101()+a0201a;
										break;
									}else{
										break;
									}
								}
							}
						}
					}
					
					
					
					String a0203Next = null;//a02Next.getA0243()==null?"":a02Next.getA0243();//????????
					if("1".equals(a0255)){//????
						a0203Next = "";
					}else{
						a0203Next = a0265;
					}
					if("".equals(a0203)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203.length()>=6){
							String year = a0203.substring(0,4);
							String month = a0203.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("--");
					if("".equals(a0203Next)){
						sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					}else{
						if(a0203Next.length()>=6){
							String year = a0203Next.substring(0,4);
							String month = a0203Next.substring(4,6);
							sb.append(year+"."+month);
						}
					}
					sb.append("&nbsp;&nbsp;"+a0201a+a03015+"<br/>");
				}
			}
			
			this.getExecuteSG().addExecuteCode("document.getElementById('contenttext2').innerHTML='"+sb.toString()+"'");
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@NoRequiredValidate
	private void updateA01(A01 a01, HBSession sess){
		//????a01 ?????????? a0148 a0149     a02 a0221
		String sql="select a0221 from A02 a where a0000='"+a01.getA0000()+"' and a0255='1'";
		List<String> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list, new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					if(o1==null||"".equals(o1)){
						return 1;
					}
					if(o2==null||"".equals(o2)){
						return -1;
					}
					return o1.compareTo(o2);
				}
			});
			
			a01.setA0148(list.get(0));
			//????????????????
			//????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='"+(a01.getA0148()==null?"":a01.getA0148())+"';");
		}else{
			//????????
			a01.setA0148("");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='';");
		}
		
	}
	
	
	
	/**
	 * ??????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.dogridquery")
	@NoRequiredValidate
	public int workUnitsGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from a02 where a0000='"+a0000+"' order by a0223 ";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ??????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsAddBtn.onclick")
	@NoRequiredValidate
	public int workUnitsWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A02 a02 = new A02();
		this.getPageElement("a0201b_combo").setValue("");
		
		PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');document.getElementById('a0201d').checked=false;document.getElementById('a0251b').checked=false;"
				+ "document.getElementById('a0219').checked=false;document.getElementById('a0255').value='1';document.getElementById('a02551').checked=true;setA0201eDisabled();a0255SelChange()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//??????????index
		int index = this.getPageElement("WorkUnitsGrid").getCueRowIndex();
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			String a0219 = a02.getA0219();
			if(a0219 != null && "2".equals(a0219)){
				a02.setA0219("0");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			setZB08Code(a02.getA0201b());
			this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//???????? ??????
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
			
			//????????,????????
			if(a02.getA0255() != null && a02.getA0255().equals("0")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=false;document.getElementById('a02550').checked=true;a0255SelChange();");
			}
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=true;document.getElementById('a02550').checked=false;a0255SelChange();");
			}
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * ??????????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("workUnitsgridchecked")
	@Transaction
	@NoRequiredValidate
	public int workUnitsgridchecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0200 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		try{
			if(a0200!=null){
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				Boolean checked = Boolean.valueOf(a02.getA0281());
				a02.setA0281(String.valueOf(!checked));
				sess.save(a02);
				PMPropertyCopyUtil.copyObjValueToElement(a02, this);
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//???????? ??????
				//this.getExecuteSG().addExecuteCode("a0222SelChange();");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//????????
		this.getExecuteSG().addExecuteCode("$h.confirm('????????','???????????????????????????????????????',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * ????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("subWinIdBussessId").getValue();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ????????and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//???? ????
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//???? ????
			
			String zrqm = "";//???? ????
			String ymqm = "";//????
			String zrjc = "";//????
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//????????
				String jgbm = a02.getA0201b();//????????
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//???????? ????
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//???????? ????
				String zwmc = a02.getA0216a()==null?"":a02.getA0216a();//????????
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//???????????????????? ????????????????
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1????????????2????????????3????????????
					if("2".equals(b0194)){//2??????????
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2??????????
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3??????????
									continue;
								}else if("1".equals(b0194)){//1??????????
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//????
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//?????????????? ?????????????? ?????????????????????????? ?????????????????? ?????????????????????????????????????????????????????????????? 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "??" + jgmcList.toString()+zwmc);
										
										
									}
									//????
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "??" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1???????????? ??????????????????????????????
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key ????_$_????????_$_????????
						}else{
							desc_full.put(key_full, value_full + "??" + zwmc);
						}
						
						//????
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "??" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//????
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//????
					//???????? ????????		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "??";
					}
				}else{//????
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "??";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//????
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//????
					//???????? ????????		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "??";
					}
				}else{//????
					if(!"".equals(jgzw)){
						ymjc += jgzw + "??";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(??"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(??"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//????????????????
			if(isEvent){
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
		}else{
			//this.getPageElement("a0192a").setValue("");
			//this.getPageElement("a0192").setValue("");
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			//????????????????
			if(isEvent){
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='';");
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
			
		}
		this.UpdateTimeBtn(id);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * ??????????????????
	 * @return
	 * @throws RadowException
	 */
	@Transaction
	@NoRequiredValidate
	public int UpdateTimeBtn(String id) throws RadowException {
		
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("subWinIdBussessId").getValue();
		} catch (RuntimeException e) {
			
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 ????????and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//???? ????
			
			
			String zrqm = "";//???? ????
			String ymqm = "";//????
			String zrjc = "";//????
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//????????
				String jgbm = a02.getA0201b();//????????
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//???????? ????
				//jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				jgmcList.add("");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//???????? ????
				String zwmc = a02.getA0216a()==null?"":a02.getA0216a();//????????
				
				String zwrzshj = "";//????????????
				if(a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8){
					zwrzshj = a02.getA0243().substring(0,4) + "." + a02.getA0243().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//???????????????????? ????????????????
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1????????????2????????????3????????????
					if("2".equals(b0194)){//2??????????
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2??????????
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3??????????
									continue;
								}else if("1".equals(b0194)){//1??????????
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//????
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//?????????????? ?????????????? ?????????????????????????? ?????????????????? ?????????????????????????????????????????????????????????????? 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "??" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "??" + zwrzshj);
										
									}
									
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1???????????? ??????????????????????????????
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							//desc_full.put(key_full, zwmc+zwrzshj);//key ????_$_????????_$_????????
							desc_full.put(key_full, zwrzshj);//key ????_$_????????_$_????????
						}else{
							//desc_full.put(key_full, value_full + "??" + zwmc+zwrzshj);
							desc_full.put(key_full, value_full + "??" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//????
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//????
					//???????? ????????		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "??";
					}
				}else{//????
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "??";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "("+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "("+ymjc+")";
			}
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(zrqm+ymqm);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
		}else{
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(null);
			sess.update(a01);
			
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	
	@PageEvent("deleteRowA02")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA02(String a0200)throws RadowException, AppException{
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			
			A01 a01 = (A01)sess.get(A01.class, a02.getA0000());
			applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A02(), new A02()));
			sess.delete(a02);
			sess.flush();
			//????????????
			updateA01(a01, sess);
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
			a02 = new A02();
			this.getPageElement("a0201b_combo").setValue("");
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String id)throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("WorkUnitsGrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0, j = 0;
			for(HashMap<String,String> m : list){
				String a0200 = m.get("a0200");//a02 id
				String a0255 = m.get("a0255");//???? ????
				/*if("1".equals(a0255)){//????
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}*/
				HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				
			}
			
			
			this.setNextEventName("WorkUnitsGrid.dogridquery");//??????????????????		
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("sortUseTime")
	@NoRequiredValidate
	@Transaction
	public int sortUseTime(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "From A02 where a0000='"+a0000+"' order by a0255 desc, a0243 desc ";//????????  ????????????
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			List<A02> list = sess.createQuery(sql).list();
			int i = 0,j = 0;
			if(list!=null&&list.size()>0){
				for(A02 a02 : list){
					String a0200 = a02.getA0200();//a02 id
					String a0255 = a02.getA0255();//???? ????
					if("1".equals(a0255)){//????
						HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
					}else{
						HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
					}
				}
			}
			for(A02 a02 : list){
				String a0200 = a02.getA0200();//a02 id
				String a0255 = a02.getA0255();//???? ????
				if("1".equals(a0255)){//????
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
			}
			this.setNextEventName("WorkUnitsGrid.dogridquery");//??????????????????		
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id)throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+id+"'";
		String B0194 = sess.createSQLQuery(sql).uniqueResult().toString();
		if(B0194 != null && B0194.equals("2")){								//??????????????????????????????????????????????????????
			
			this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','disabled');document.getElementById('a0201d').checked=false; "
					+ "document.getElementById('a0201e_combo').disabled=true;document.getElementById('a0201e_combo').style.backgroundColor='#EBEBE4';"
					+ "document.getElementById('a0201e_combo').style.backgroundImage='none';Ext.query('#a0201e_combo+img')[0].style.display='none';"
					+ "document.getElementById('a0201e').value='';document.getElementById('a0201e_combo').value='';document.getElementById('b0194Type').value='2';changea0201d(2);"
					);
		}else{
			if(B0194 != null && B0194.equals("1")){
				this.getExecuteSG().addExecuteCode("changea0201d(1);document.getElementById('b0194Type').value='1';");
			}else{
				this.getExecuteSG().addExecuteCode("changea0201d(2);document.getElementById('b0194Type').value='3';");
			}
			this.getExecuteSG().addExecuteCode("$('#a0201d').attr('disabled','');"
					+ "document.getElementById('a0201e_combo').readOnly=false;document.getElementById('a0201e_combo').disabled=false;"
					+ "document.getElementById('a0201e_combo').style.backgroundColor='#fff';"
					+ "Ext.query('#a0201e_combo+img')[0].style.display='block';"
					);
		}
		
		try {
			String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			if(v!=null){
				this.getPageElement("a0201b_combo").setValue(v);
			}else{
				this.getPageElement("a0201b_combo").setValue("");
			}
			
			
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//??????????????????????????????????????
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		if(B0194 != null && B0194.equals("2")){
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('????????','??????????????????????');document.getElementById('a0195').value='';"
					+ "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_combo').value='';"
					);
		}else{
			//????????????????????????????
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				//????????
				//this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/***********************************************????????????(a06)*********************************************************************/
	/**
	 * ????????
	 */
	@PageEvent("saveA06.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		A06 a06 = new A06();
		this.copyElementsValueToObj(a06, this);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a06.setA0000(a0000);
		String a0600 = this.getPageElement("a0600").getValue();
		HBSession sess = null;
		
		//??????????????????????
		String a0601 = this.getPageElement("a0601").getValue();
		if(a0601 == null || "".equals(a0601)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//????????????????????????
			String a0107 = a01.getA0107();//????????
			String a0604 = a06.getA0604();//????????????
			if(a0604!=null&&!"".equals(a0604)&&a0107!=null&&!"".equals(a0107)){
				if(a0604.length()==6){
					a0604 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0604.compareTo(a0107)<0){
					this.setMainMessage("????????????????????????????");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
			if(a0600==null||"".equals(a0600)){
				applog.createLog("3061", "A06", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A06(), a06));
				a06.setA0699("true");
				String sql = "select max(sortid)+1 from a06 where a0000='"+a0000+"'";
				List<Object> sortid = sess.createSQLQuery(sql).list();//oracle:BigDecimal,mysql:BigInteger
				if(sortid.get(0)==null){
					a06.setSortid(1l);
				}else{
					a06.setSortid(Long.valueOf(sortid.get(0).toString()));
				}
				sess.save(a06);	
			}else{
				A06 a06_old = (A06)sess.get(A06.class, a0600);
				applog.createLog("3062", "A06", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a06_old, a06));
				PropertyUtils.copyProperties(a06_old, a06);
				sess.update(a06_old);	
			}
			sess.flush();
			updateA01(a0000);
			/*this.setMainMessage("????????,????????????????????????!");
			this.setMessageType(EventMessageType.CONFIRM);
			addNextBackFunc(NextEventValue.YES, closeCueWindowEX());*/
			this.setMainMessage("??????????");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);//??????????id??????????????
		getPageElement("a0607").setValue(a06.getA0607());
		this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.parent.Ext.WindowMgr.getActive().close();";
	}
	private void updateA01(String a0000 ) throws AppException{
		//????????????
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"??");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('a0196').setValue('"+a0196s+"')");
		//????????????????????
		//??a0196??????????????????????
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0196').value='"+a0196s.toString()+"';window.parent.a0196onblur();");
		
		//????A10 a0196 ??????????????????   a06 a0602
		HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
	}
	/**
	 * ????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select a.* from A06 a where a0000='"+a0000+"' order by sortid";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A06 a06 = new A06();
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		this.getPageElement("a0196").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//??????????index
		int index = this.getPageElement("professSkillgrid").getCueRowIndex();
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
			getPageElement("a0607").setValue(a06.getA0607());
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}							
		//this.setRadow_parent_data("a0600:"+this.getPageElement("professSkillgrid").getValue("a0600",this.getPageElement("professSkillgrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRowA06")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA06(String a0600)throws RadowException, AppException{
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			A01 a01 = (A01)sess.get(A01.class, a06.getA0000());
			applog.createLog("3063", "A06", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A06(), new A06()));
			sess.delete(a06);
			this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
			sess.flush();
			updateA01(a06.getA0000());
			a06 = new A06();
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updateA06")
	@Transaction
	@NoRequiredValidate
	public int updateA06(String a0600) throws RadowException {
		
		try{
			if(a0600!=null){
				HBSession sess = HBUtil.getHBSession();
				A06 a06 = (A06)sess.get(A06.class, a0600);
				Boolean checked = "true".equals(a06.getA0699());
				a06.setA0699(String.valueOf(!checked));
				sess.save(a06);
				sess.flush();
				PMPropertyCopyUtil.copyObjValueToElement(a06, this);
				updateA01( a06.getA0000() );
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");//????????
		this.getExecuteSG().addExecuteCode("Ext.getCmp('professSkillgrid').getStore().reload()");//????????
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksortA06")
	@NoRequiredValidate
	@Transaction
	public int upsortA06()throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("professSkillgrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			String a0000 = this.getPageElement("subWinIdBussessId").getValue();
			int i = 0;
			StringBuffer a0196s = new StringBuffer("");
			for(HashMap<String,String> m : list){
				String a0600 = m.get("a0600");//a02 id
				HBUtil.executeUpdate("update a06 set sortid="+i+++" where a0600='"+a0600+"'");		
			}
			updateA01(a0000);
			
			this.setNextEventName("professSkillgrid.dogridquery");//??????????????????		
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/***********************************************????????(a08)*********************************************************************/
	@PageEvent("saveA08")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveDegrees()throws RadowException, AppException{
		A08 a08 = new A08();
		this.copyElementsValueToObj(a08, this);
		String a0800 = this.getPageElement("a0800").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//??????????????????
		String a0837 = this.getPageElement("a0837").getValue();
		if(a0837 == null || "".equals(a0837)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????????????????????
		String a0801b = a08.getA0801b();//????
		String a0901b = a08.getA0901b();//????
		if((a0801b==null||"".equals(a0801b))&&(a0901b==null||"".equals(a0901b))){
			this.setMainMessage("????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//????????????????????????????
		String a0807 = a08.getA0807();//????????
		String a0804 = a08.getA0804();//????????
		String a0904 = a08.getA0904();//????????????
		if(a0807!=null&&!"".equals(a0807)&&a0804!=null&&!"".equals(a0804)){
			if(a0807.length()==6){
				a0807 += "00";
			}
			if(a0804.length()==6){
				a0804 += "00";
			}
			if(a0807.compareTo(a0804)<0){
				this.setMainMessage("????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(a0804!=null&&!"".equals(a0804)&&a0904!=null&&!"".equals(a0904)){
			if(a0804.length()==6){
				a0804 += "00";
			}
			if(a0904.length()==6){
				a0904 += "00";
			}
			if(a0904.compareTo(a0804)<0){
				this.setMainMessage("????????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		a08.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//????????????????????????????????????????????????????????
			String a0107 = a01.getA0107();//????????
			if(a0107!=null&&!"".equals(a0107)){
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0807!=null&&!"".equals(a0807)){
					if(a0807.length()==6){
						a0807 += "00";
					}
					if(a0807.compareTo(a0107)<0){
						this.setMainMessage("????????????????????????");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0804!=null&&!"".equals(a0804)){
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0804.compareTo(a0107)<0){
						this.setMainMessage("????????????????????????");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0904!=null&&!"".equals(a0904)){
					if(a0904.length()==6){
						a0904 += "00";
					}
					if(a0904.compareTo(a0107)<0){
						this.setMainMessage("????????????????????????????");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			
			
			
			if(a0800==null||"".equals(a0800)){
				a08.setA0899("false");//????????
				
				
				applog.createLog("3081", "A08", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A08(), a08));
				
				sess.save(a08);	
			}else{
				if(a08.getA0899()==null||"".equals(a08.getA0899())){
					a08.setA0899("false");//????????
				}
				
				A08 a08_old = (A08)sess.get(A08.class, a0800);
				applog.createLog("3082", "A08", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a08_old, a08));
				PropertyUtils.copyProperties(a08_old, a08);
				
				sess.update(a08_old);
				//????????????????????????????????????
				/*updateZGXueliXuewei(a0000,sess);//????????????????
				updateZGZZXueliXuewei(a0000,sess);//????????????????????
				updateZGQRZXueliXuewei(a0000,sess);
				updateXueliXuewei(a0000,sess,"1");
				updateXueliXuewei(a0000,sess,"2");*/
				
			}	
			sess.flush();	
			printout(a0000,sess,"1");// ???????????????????? 
			printout(a0000,sess,"2");
			updateZGXueliXuewei(a0000,sess);//????????????????
			updateZGQRZXueliXuewei(a0000,sess);
			updateZGZZXueliXuewei(a0000,sess);//????????????????????
			updateXueliXuewei(a0000,sess,"1");
			updateXueliXuewei(a0000,sess,"2");
			this.setMainMessage("??????????");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0800").setValue(a08.getA0800());//??????????id??????????????
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('degreesgrid').getStore().reload()");//????????????????
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	


	private void updateZGXueliXuewei(String a0000, HBSession sess) {
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true'";//?????? ?????????????? 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//????????
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//????????
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		
		//????????????????????,??????????????????????????????
				if(list1!=null&&list1.size()==1){
					A08 a08=list1.get(0);
					String xuelidaima=a08.getA0801b();
					if(!StringUtil.isEmpty(xuelidaima)){
						a08.setA0834("1");
					}else{
						a08.setA0834("0");
					}
					sess.update(a08);
				}
				
				//??????????????,????????????????????????????????????,????????????????????????????????????????
				if(list1!=null&&list1.size()>1){
					A08 a08=list1.get(0);
					String xuelidaima=a08.getA0801b();
					if(!StringUtil.isEmpty(xuelidaima)){
						a08.setA0834("1");
						sess.update(a08);
						for(int i=1;i<list1.size();i++){
							A08 a08_x=list1.get(i);
							String xuelidaima_x=a08_x.getA0801b();
							String duibi=xuelidaima.substring(0,1);
							if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
								a08_x.setA0834("1");
							}else{
								a08_x.setA0834("0");
							}
							sess.update(a08_x);
							}
							}else{
								for(int i=0;i<list1.size();i++){
									A08 a08_x=list1.get(i);
									a08_x.setA0834("0");
									sess.update(a08_x);
								}
							}
						}
			String sql2="select * from a08 where a0000='"+a0000+"' and a0899='true' order by to_number(a0901b) asc";
			List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
						//????????????????????,??????????????????????????????
						if(list2!=null&& list2.size()==1){
							A08 a08=list2.get(0);
							String xueweidaima=a08.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima)){
								 a08.setA0835("1");
							}else{
								a08.setA0835("0");
							}
							sess.update(a08);
						}
						//??????????????,????????????????????????????????????,??????????????????????????????????????????????????
						if(list2!=null&&list2.size()>1){
							A08 a08_1=list2.get(0);
							String xueweidaima=a08_1.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima)){
								a08_1.setA0835("1");
								sess.update(a08_1);
								
								if(xueweidaima.startsWith("1")){
									for(int i=1;i<list2.size();i++){
										A08 a08_x=list2.get(i);
										String xueweidaima_x=a08_x.getA0901b();
										if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
											a08_x.setA0835("1");
										}else{
											a08_x.setA0835("0");
										}
										sess.update(a08_x);
									}
								}else{
									String reg=xueweidaima.substring(0,1);
										for(int i=1;i<list2.size();i++){
											A08 a08_x=list2.get(i);
											String xueweidaima_x=a08_x.getA0901b();
											if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
												a08_x.setA0835("1");
											}else{
												a08_x.setA0835("0");
											}
											sess.update(a08_x);
										}
									
								}
							
							}else{
								for(int i=0;i<list2.size();i++){
									A08 a08_x=list2.get(i);
									a08_x.setA0835("0");
									sess.update(a08_x);
								}
							}

						}
	}
	
	private void updateZGZZXueliXuewei(String a0000, HBSession sess) {
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2'";// ?????????????????? 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//????????
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//????????
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//????????????????????,??????????????????????????????
		if(list1!=null&&list1.size()==1){
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0838("1");
			}else{
				a08.setA0838("0");
			}
			sess.update(a08);
		}
		//??????????????,????????????????????????????????????,????????????????????????????????????????
		if(list1!=null&&list1.size()>1){
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0838("1");
				sess.update(a08);
				for(int i=1;i<list1.size();i++){
					A08 a08_x=list1.get(i);
					String xuelidaima_x=a08_x.getA0801b();
					String duibi=xuelidaima.substring(0,1);
					if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
						a08_x.setA0838("1");
					}else{
						a08_x.setA0838("0");
					}
					sess.update(a08_x);
				}
			}else{
				for(int i=0;i<list1.size();i++){
					A08 a08_x=list1.get(i);
					a08_x.setA0838("0");
					sess.update(a08_x);
				}
			}
		}
		String sql2="select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2' order by to_number(a0901b) asc";
		List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
		//????????????????????,??????????????????????????????
		if(list2!=null&& list2.size()==1){
			A08 a08=list2.get(0);
			String xueweidaima=a08.getA0901b();
			if(!StringUtil.isEmpty(xueweidaima)){
				 a08.setA0839("1");
			}else{
				a08.setA0839("0");
			}
			sess.update(a08);
		}
		//??????????????,????????????????????????????????????,??????????????????????????????????????????????????
		if(list2!=null&&list2.size()>1){
			A08 a08_1=list2.get(0);
			String xueweidaima=a08_1.getA0901b();
			if(!StringUtil.isEmpty(xueweidaima)){
				a08_1.setA0839("1");
				sess.update(a08_1);

				if(xueweidaima.startsWith("1")){
					for(int i=1;i<list2.size();i++){
						A08 a08_x=list2.get(i);
						String xueweidaima_x=a08_x.getA0901b();
						if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
							a08_x.setA0839("1");
						}else{
							a08_x.setA0839("0");
						}
						sess.update(a08_x);
					}
				}else{
					String reg=xueweidaima.substring(0,1);
						for(int i=1;i<list2.size();i++){
							A08 a08_x=list2.get(i);
							String xueweidaima_x=a08_x.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
								a08_x.setA0839("1");
							}else{
								a08_x.setA0839("0");
							}
							sess.update(a08_x);
						}
					
				}
			
			}else{
				for(int i=0;i<list2.size();i++){
					A08 a08_x=list2.get(i);
					a08_x.setA0839("0");
					sess.update(a08_x);
				}
			}
			
		}
	}
	
	private void updateZGQRZXueliXuewei(String a0000, HBSession sess) {
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1'";//???????????????????? 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//????????
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//????????
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		//????????????????????,??????????????????????????????
		if(list1!=null&&list1.size()==1){
			A08 a08=list1.get(0);
			String xuelidaima=a08.getA0801b();
			if(!StringUtil.isEmpty(xuelidaima)){
				a08.setA0831("1");
			}else{
				a08.setA0831("0");
			}
			sess.update(a08);
		}
		
		//??????????????,????????????????????????????????????,????????????????????????????????????????
				if(list1!=null&&list1.size()>1){
					A08 a08=list1.get(0);
					String xuelidaima=a08.getA0801b();
					if(!StringUtil.isEmpty(xuelidaima)){
						a08.setA0831("1");
						sess.update(a08);
						for(int i=1;i<list1.size();i++){
							A08 a08_x=list1.get(i);
							String xuelidaima_x=a08_x.getA0801b();
							String duibi=xuelidaima.substring(0,1);
							if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
								a08_x.setA0831("1");
							}else{
								a08_x.setA0831("0");
							}
							sess.update(a08_x);
						}
					}else{
						for(int i=0;i<list1.size();i++){
							A08 a08_x=list1.get(i);
							a08_x.setA0831("0");
							sess.update(a08_x);
						}
					}
				}
				
		String sql2="select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1' order by to_number(a0901b) asc";
		List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
				//????????????????????,??????????????????????????????
				if(list2!=null&& list2.size()==1){
					A08 a08=list2.get(0);
					String xueweidaima=a08.getA0901b();
					if(!StringUtil.isEmpty(xueweidaima)){
						 a08.setA0832("1");
					}else{
						a08.setA0832("0");
					}
					sess.update(a08);
				}
				//??????????????,????????????????????????????????????,??????????????????????????????????????????????????
				if(list2!=null&&list2.size()>1){
					A08 a08_1=list2.get(0);
					String xueweidaima=a08_1.getA0901b();
					if(!StringUtil.isEmpty(xueweidaima)){
						a08_1.setA0832("1");
						sess.update(a08_1);

						if(xueweidaima.startsWith("1")){
							for(int i=1;i<list2.size();i++){
								A08 a08_x=list2.get(i);
								String xueweidaima_x=a08_x.getA0901b();
								if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
									a08_x.setA0832("1");
								}else{
									a08_x.setA0832("0");
								}
								sess.update(a08_x);
							}
						}else{
							String reg=xueweidaima.substring(0,1);
								for(int i=1;i<list2.size();i++){
									A08 a08_x=list2.get(i);
									String xueweidaima_x=a08_x.getA0901b();
									if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
										a08_x.setA0832("1");
									}else{
										a08_x.setA0832("0");
									}
									sess.update(a08_x);
								}
							
						}
					
					}else{
						for(int i=0;i<list2.size();i++){
							A08 a08_x=list2.get(i);
							a08_x.setA0832("0");
							sess.update(a08_x);
						}
					}
					
				}
	}


	/*@SuppressWarnings("unchecked")
	private void updateXueliXuewei(String a0000, HBSession sess,String a0837,Boolean isnull) throws AppException {
		//??????????????
		A08 xueli = null;
		A08 xuewei = null;  

		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //??????????????????
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//????????
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//????????
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//????????
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//????????
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		if(list1!=null&&list1.size()>0){
			xueli = list1.get(0);
		}
		
		String sql2 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true' order by to_number(a0901b) asc";   //??????????????????
		List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
		if(list2!=null && list2.size()>0){
			xuewei = list2.get(0);
		}
			
		//????a01
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if(xueli!=null){
			checkXL(xueli,sess,a0837,a0000);
		}else{
			if("1".equals(a0837)){//??????
				a01.setQrzxl(a01.getQrzxl());//????
				a01.setQrzxlxx(a01.getQrzxlxx());
				a01.setQrzxw(a01.getQrzxw());//????
				a01.setQrzxwxx(a01.getQrzxwxx());
				//????????????????
				
				//??????????????????????????????A01??????????????
				if(!isnull){
					a01.setQrzxl(null);//????
					a01.setQrzxlxx(null);
					a01.setQrzxw(null);//????
					a01.setQrzxwxx(null);
				}
				
				
			}else{//????
				a01.setZzxl(a01.getZzxl());
				a01.setZzxlxx(a01.getZzxlxx());
				a01.setZzxw(a01.getZzxw());
				a01.setZzxwxx(a01.getZzxwxx());
				//????????????????
				
				//????????????????????????????????????????????A01??????????????
				if(!isnull){
					a01.setZzxl(null);
					a01.setZzxlxx(null);
					a01.setZzxw(null);
					a01.setZzxwxx(null);
				}
				
			}
		}
		sess.update(a01);
		sess.flush();
		
	}*/
	
	@SuppressWarnings("unchecked")
	private void updateXueliXuewei(String a0000, HBSession sess,String a0837) throws AppException {
		checkXL(sess,a0837,a0000);
		checkXW(sess,a0837,a0000);
		
	}
	private void checkXL(HBSession sess,String a0837,String a0000) {//a0837????????
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //??????????????????
				if("1".equals(a0837)){
					sql1+=" and a0831='1'";
				}
				if("2".equals(a0837)){
					sql1+=" and a0838='1'";
				}
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//????
					String zy_xl = xueli.getA0824();//????
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					if(!"".equals(zy_xl)){
						zy_xl += "????";
					}
					 xl = xueli.getA0801a();//???? ????
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//????a01 ????????????:????????qrzxl???? qrzxw???? qrzxlxx?????????????????? zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		
		/*String a0901a_xl = xueli.getA0901b();
		if(a0901a_xl != null && !"".equals(a0901a_xl)){//???????? ???? ????????????????????????????
			checkXW(true,xueli,sess,a0837,a0000);
		}else{//?????????? ???? ????
			checkXW(false,xueli,sess,a0837,a0000);
		}*/
	
		if("1".equals(a0837)){//??????
			a01.setQrzxl(xl);//????
			a01.setQrzxlxx(xlxx);
			//????????????????
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
		}else{//????
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
			//????????????????
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}

	private void checkXW(HBSession sess,String a0837,String a0000){
		String xw = "";//????????
		String xwxx = "";//??????????????
		String sql="select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";
		if("1".equals(a0837)){
			sql+=" and a0832='1'";
		}
		if("2".equals(a0837)){
			sql+=" and a0839='1'";
		}
		List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();
		if(list!=null&&list.size()>0){
			A08 xuewei=list.get(0);
			xw=xuewei.getA0901a();
			String yx_xw=xuewei.getA0814();
			String zy_xw=xuewei.getA0824();
			if(yx_xw==null){
				yx_xw = "";
			}
			if(zy_xw==null){
				zy_xw = "";
			}
			if(!"".equals(zy_xw)){
				zy_xw += "????";
			}
			xwxx = yx_xw+zy_xw;
			if(xw==null&&"".equals(xw)){
				xwxx=null;
			}
			if(list.size()>1){
				A08 xuewei2=list.get(1);
				String xw2=xuewei2.getA0901a();
				String yx_xw2=xuewei2.getA0814();
				String zy_xw2=xuewei2.getA0824();
				if(yx_xw2==null){
					yx_xw2 = "";
				}
				if(zy_xw2==null){
					zy_xw2 = "";
				}
				if(!"".equals(zy_xw2)){
					zy_xw2 += "????";
				}
				if(xw2!=null&&!"".equals(xw2)){
					if(xw==null&&"".equals(xw)){
						xw=xw2;
						xwxx= yx_xw2+zy_xw2;
					}else{
						xw=xw+","+xw2;
						xwxx=xwxx+","+yx_xw2+zy_xw2;
					}
				}
			}
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if("1".equals(a0837)){//??????
			a01.setQrzxw(xw);//????
			a01.setQrzxwxx(xwxx);
			//????????????????
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
		}else{//????
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
			//????????????????
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxw').value='"+(a01.getZzxw()==null?"":a01.getZzxw())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxwxx').value='"+(a01.getZzxwxx()==null?"":a01.getZzxwxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}


	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}
	
	
	/**
	 * ????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.dogridquery")
	@NoRequiredValidate
	public int degreesgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		String sql = "select * from A08 where a0000='"+a0000+"' order by a0837";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesAddBtn.onclick")
	@NoRequiredValidate
	public int opendegreesWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		
		String a0800 = this.getPageElement("a0800").getValue();
		String a0837 = this.getPageElement("a0837").getValue();//????????
		String a0801b = this.getPageElement("a0801b").getValue();//????
		String a0901b = this.getPageElement("a0901b").getValue();//????
		if(a0800==null||"".equals(a0800)){//??????????????????????????
			if(a0837!=null&&!"".equals(a0837)){
				if((a0801b!=null&&!"".equals(a0801b))||(a0901b!=null&&!"".equals(a0901b))){
					this.getExecuteSG().addExecuteCode("$h.confirm3btn('????????','?????????????????????',200,function(id){" +
							"if(id=='yes'){" +
								"saveDegree();	" +
								"}else if(id=='no'){" +
								"	radow.doEvent('clearCondition');" +
								"}else if(id=='cancel'){" +
								"	" +
								"}" +
							"});");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
		}
		
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		clearCondition();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ???? ????????
	 * @throws RadowException 
	 */
	@PageEvent("clearCondition")
	public void clearCondition() throws RadowException{
		A08 a08 = new A08();
		PMPropertyCopyUtil.copyObjValueToElement(a08, this);
	}
	
	/**
	 * ????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int degreesgridOnRowClick() throws RadowException{ 
		//this.openWindow("DegreesAddPage", "pages.publicServantManage.DegreesAddPage");
		//??????????index
		int index = this.getPageElement("degreesgrid").getCueRowIndex();
		String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * ????????	
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgridchecked")
	@Transaction
	@NoRequiredValidate
	public int degreesgridChecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0800 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		try{
			if(a0800!=null){
				HBSession sess = HBUtil.getHBSession();
				A08 a08 = (A08)sess.get(A08.class, a0800);
				Boolean checked = Boolean.valueOf(a08.getA0899());
				a08.setA0899(String.valueOf(!checked));
				sess.save(a08);
				sess.flush();
				PMPropertyCopyUtil.copyObjValueToElement(a08, this);
				//????a01 ????????????:????????qrzxl???? qrzxw???? qrzxlxx?????????????????? zzxl zzxw zzxlxx
				updateZGXueliXuewei(a0000,sess);//????????????????
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a0000,sess);//??????????????????????

				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a0000,sess);//????????????????????
				}
				updateXueliXuewei(a0000, sess, a0837);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");//????????
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	@PageEvent("deleteRowA08")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA08(String a0800)throws RadowException, AppException{
		Map map = this.getRequestParamer();
		//int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		//String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			
			A01 a01 = (A01)sess.get(A01.class, a08.getA0000());
			applog.createLog("3083", "A08", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A08(), new A08()));
			sess.delete(a08);
			sess.flush();
			Boolean checked = Boolean.valueOf(a08.getA0899());				
			//checked ??????true ????????????????  ????A01 ????????????
			if(checked){
				//????a01 ????????????:????????qrzxl???? qrzxw???? qrzxlxx?????????????????? zzxl zzxw zzxlxx
				updateZGXueliXuewei(a08.getA0000(),sess);//????????????????
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a08.getA0000(),sess);//??????????????????????	
				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a08.getA0000(),sess);//????????????????????
				}
				updateXueliXuewei(a08.getA0000(), sess, a0837);
			}
			this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
			a08 = new A08();
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//????????????????????????
		public int printout(String a0000,HBSession sess,String a0837) throws RadowException{
			String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"'";// ?????????????????? 
			List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
			if(list1!=null&&list1.size()>0){
				Collections.sort(list1,new Comparator<A08>(){//????????
					@Override
					public int compare(A08 o1, A08 o2) {
						String a0801b_1 = o1.getA0801b();//????????
						String a0801b_2 = o2.getA0801b();
						if(a0801b_1==null||"".equals(a0801b_1)){
							return 1;
						}
						if(a0801b_2==null||"".equals(a0801b_2)){
							return -1;
						}
						//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
						return a0801b_1.compareTo(a0801b_2);
					}
					
				});
			}
			//????????????????????,??????????????????????????????
			if(list1!=null&&list1.size()==1){
				A08 a08=list1.get(0);
				String xuelidaima=a08.getA0801b();
				if(!StringUtil.isEmpty(xuelidaima)){
					a08.setA0899("true");
				}else{
					a08.setA0899("false");
				}
				sess.update(a08);
			}
			//??????????????,????????????????????????????????????,????????????????????????????????????????
			if(list1!=null&&list1.size()>1){
				A08 a08=list1.get(0);
				String xuelidaima=a08.getA0801b();
				if(!StringUtil.isEmpty(xuelidaima)){
					a08.setA0899("true");
					sess.update(a08);
					for(int i=1;i<list1.size();i++){
						A08 a08_x=list1.get(i);
						String xuelidaima_x=a08_x.getA0801b();
						String duibi=xuelidaima.substring(0,1);
						if(!StringUtil.isEmpty(xuelidaima_x)&&duibi.equals(xuelidaima_x.substring(0,1))){
							a08_x.setA0899("true");
						}else{
							a08_x.setA0899("false");
						}
						sess.update(a08_x);
					}
				}else{
					for(int i=0;i<list1.size();i++){
						A08 a08_x=list1.get(i);
						a08_x.setA0899("false");
						sess.update(a08_x);
					}
				}
			}
			String sql2="select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' order by to_number(a0901b) asc";
			List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
			//????????????????????,??????????????????????????????
			if(list2!=null&& list2.size()==1){
				A08 a08=list2.get(0);
				String xueweidaima=a08.getA0901b();
				if(!StringUtil.isEmpty(xueweidaima)){
					 a08.setA0899("true");
				}
				sess.update(a08);
			}
			//??????????????,????????????????????????????????????,??????????????????????????????????????????????????
			if(list2!=null&&list2.size()>1){
				A08 a08_1=list2.get(0);
				String xueweidaima=a08_1.getA0901b();
				if(!StringUtil.isEmpty(xueweidaima)){
					a08_1.setA0899("true");
					sess.update(a08_1);

					if(xueweidaima.startsWith("1")){
						for(int i=1;i<list2.size();i++){
							A08 a08_x=list2.get(i);
							String xueweidaima_x=a08_x.getA0901b();
							if(!StringUtil.isEmpty(xueweidaima_x)&&(xueweidaima_x.startsWith("1")||xueweidaima_x.startsWith("2"))){
								a08_x.setA0899("true");
							}
							sess.update(a08_x);
						}
					}else{
						String reg=xueweidaima.substring(0,1);
							for(int i=1;i<list2.size();i++){
								A08 a08_x=list2.get(i);
								String xueweidaima_x=a08_x.getA0901b();
								if(!StringUtil.isEmpty(xueweidaima_x)&&xueweidaima_x.startsWith(reg)){
									a08_x.setA0899("true");
								}
								sess.update(a08_x);
							}
						
					}
				
				}	
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
	
	
	/***********************************************????????(a14)*********************************************************************/
	@PageEvent("saveA14")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveRewardPunish()throws RadowException, AppException{
		A14 a14 = new A14();
		this.copyElementsValueToObj(a14, this);
		String a1400 = this.getPageElement("a1400").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//??????????????????????
		String a1404b = this.getPageElement("a1404b").getValue();
		if(a1404b == null || "".equals(a1404b)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a1404a = this.getPageElement("a1404a").getValue();
		if(a1404a == null || "".equals(a1404a)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a1411a = this.getPageElement("a1411a").getValue();
		if(a1411a == null || "".equals(a1411a)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a1411a.length() > 30){
			this.setMainMessage("????????????????30????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		String a1407 = a14.getA1407();//????????
		//??????????????????????????????????????????????????????
		String a0134 = a01.getA0134();//????????????
		if(a0134!=null&&!"".equals(a0134)&&a1407!=null&&!"".equals(a1407)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a1407.length()==6){
				a1407 += "00";
			}
			if(a1407.compareTo(a0134)<0){
				this.setMainMessage("????????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//????????????????????????
		String a1424 = a14.getA1424();//????????
		if(a1424!=null&&!"".equals(a1424)&&a1407!=null&&!"".equals(a1407)){
			if(a1424.length()==6){
				a1424 += "00";
			}
			if(a1407.length()==6){
				a1407 += "00";
			}
			if(a1424.compareTo(a1407)<0){
				this.setMainMessage("????????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//????????a1404a
		if(a1404a.length() > 20){
			this.setMainMessage("????????????????20????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a14.setA0000(a0000);
		HBSession sess = HBUtil.getHBSession();;
		try {
			if(a1400==null||"".equals(a1400)){
				applog.createLog("3141", "A14", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A14(), a14));
				sess.save(a14);	
			}else{
				A14 a14_old = (A14)sess.get(A14.class, a1400);
				applog.createLog("3142", "A14", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a14_old, a14));
				PropertyUtils.copyProperties(a14_old, a14);
				sess.update(a14_old);	
			}
			//????????????
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a14z101 = "??";
				this.getPageElement("a14z101").setValue(a14z101);
			}
			a01.setA14z101(a14z101);
			//????????????????
			//??????????????????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			sess.update(a01);
			sess.flush();			
			this.setMainMessage("??????????");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a14.getA1400());//??????????id??????????????
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('RewardPunishGrid').getStore().reload()");//????????????????
		this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * ????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.dogridquery")
	@NoRequiredValidate
	public int rewardPunishGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select a.* from A14 a where a0000='"+a0000+"' order by SUBSTR(A1404B,0,2),a1407";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishAddBtn.onclick")
	@NoRequiredValidate
	public int rewardPunishWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A14 a14 = new A14();
		PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int rewardPunishGridOnRowClick() throws RadowException{ 
		//??????????index
		int index = this.getPageElement("RewardPunishGrid").getCueRowIndex();
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * ????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("appendonclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int appendonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		Map map = this.getRequestParamer();
		int index = Integer.valueOf(String.valueOf(map.get("eventParameter")));//????????
		String a14z101 = this.getPageElement("a14z101").getValue();//????????
		//????????????
		String a1404b = this.getPageElement("RewardPunishGrid").getValue("a1404b",index).toString();
		//????????
		String a1404a = this.getPageElement("RewardPunishGrid").getValue("a1404a",index).toString();
		//????????
		String a1411a = this.getPageElement("RewardPunishGrid").getValue("a1411a",index).toString();
		//????????
		String a1407 = this.getPageElement("RewardPunishGrid").getValue("a1407",index).toString();
		
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"??";
		}else{
			a1407 = "";
		}
		
		boolean haschr = false;
		if("??".equals(a14z101)){
			a14z101 = "";
		}
		if(a14z101.length()>0){
			String laststr = a14z101.substring(a14z101.length()-1);
			if(laststr.matches(",|.|;|??|??|??")){
				a14z101 = a14z101.substring(0,a14z101.length()-1);				
			}
			haschr = true;
		}
		
		StringBuffer desc = new StringBuffer(a14z101);
		if(haschr){
			desc.append("??");
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		
		if(a1404b.startsWith("01") || a1404b.startsWith("1")){//??
			
			if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
				desc.append("??"+a1411a+"??????").append("????"+a1404a+"??");
			}else{
				desc.append("??"+a1411a+"??????").append(a1404a+"??");
			}
		}else{//??
			desc.append("??"+a1411a+"??????").append("??"+a1404a+"??????");
		}
		this.getPageElement("a14z101").setValue(desc.toString());
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(desc.toString());
		sess.update(a01);
		//????????????????
		//????????????????????????????????????????
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * ????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addAll.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int addAllonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		List<HashMap<String, Object>> list = this.getPageElement("RewardPunishGrid").getValueList();
		if(list!=null&&list.size()>0){
			StringBuffer desc = new StringBuffer("");
			for(HashMap<String, Object> map : list){
				//????????????
				String a1404b = map.get("a1404b").toString();
				//????????
				String a1404a = map.get("a1404a").toString();
				//????????
				String a1411a = map.get("a1411a").toString();
				
				//????????
				String a1407 = map.get("a1407").toString();
				
				if(a1407!=null&&a1407.length()>=6){
					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"??";
				}else{
					a1407 = "";
				}
				if(desc.toString().endsWith("??")){
					desc.deleteCharAt(desc.length()-1).append("??");
				}
				if(!"".equals(a1407)){
					desc.append(a1407);
				}
				if(a1404b.startsWith("01")){//??
					desc.append("??"+a1411a+"??????").append(a1404a+"??");
				}else{//??
					
					desc.append("??"+a1411a+"??????").append("??"+a1404a+"??????");
				}
			}
			
			this.getPageElement("a14z101").setValue(desc.toString());
			a01.setA14z101(desc.toString());
			sess.update(a01);
			//????????????????
			//????????????????????????????????????????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		}else{
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a01.setA14z101("??");
				this.getPageElement("a14z101").setValue(a01.getA14z101());
				sess.update(a01);
				//????????????????????????????????????????????
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRowA14")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA14(String a1400)throws RadowException, AppException{
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			A01 a01 = (A01)sess.get(A01.class, a14.getA0000());
			A14 a14_new = a14;
			change_visiable(a14_new);
			applog.createLog("3143", "A14", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A14(), new A14()));
			sess.delete(a14);
			this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
			a14 = new A14();
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);	
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	private void change_visiable(A14 a14) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01= (A01)session.get(A01.class, a0000);
		String description = this.getPageElement("a14z101").getValue();//????????????
		StringBuffer desc = new StringBuffer("");
		String a1407 = a14.getA1407();
		String a1404b = a14.getA1404b();//????????????	
		String a1404a = a14.getA1404a();//????????
		String a1411a = a14.getA1411a();//????????
		if(a1411a==null){
			a1411a="";
		}
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"??";
		}else{
			a1407 = "";
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		if(a1404b.startsWith("01")){//??
			desc.append("??"+a1411a+"??????").append(a1404a+"??");
		}else{//??6
			desc.append("??"+a1411a+"??????").append("??"+a1404a+"??????");
		}
		System.out.println(description);
		System.out.println(desc);
		description = description.replaceAll(desc.toString().trim(), "");
		if(desc.toString().endsWith("??")){
			desc.deleteCharAt(desc.length()-1).append("??");
		}
		description= description.replaceAll(desc.toString(), "");
		if("".equals(description.trim())){
			description = "??";
		}
		//????????????
		this.getPageElement("a14z101").setValue(description);
		
		//????a01??
		a01.setA14z101(description);
		session.update(a01);
		//????????????????
		//??????????????????????
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		
	}
	
	
	
	/***********************************************????????????A15*********************************************************************/
	@PageEvent("saveA15.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
		String a1521s = this.getPageElement("a1521").getValue();
		String a1517 = this.getPageElement("a1517").getValue();
		
		//??????????????????
		if(a1521s == null || "".equals(a1521s)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//??????????????????????
		if(a1517 == null || "".equals(a1517)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		String [] a1521sNum = a1521s.split(",");
		if(a1521sNum.length>1){
			
			return saveAllInfo();
		}else{
			A15 a15 = new A15();
			this.copyElementsValueToObj(a15, this);
			//String a0000 = this.getRadow_parent_data();
			String a0000 = this.getPageElement("subWinIdBussessId").getValue();
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("??????????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a15.setA0000(a0000);
			String a1500 = this.getPageElement("a1500").getValue();
			HBSession sess = null;
			String isconnect = "";
			try {
				sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				//????????????????????
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a15.getA0000()+"' and a1521='"+a15.getA1521()+"'");			
				if(a1500!=null&&!"".equals(a1500)){
					sql.append(" and a1500!='"+a1500+"'");
				}
				List list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					this.setMainMessage("????????????!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a15.getA1521()!=null&&!"".equals(a15.getA1521())&&a15.getA1517()!=null&&!"".equals(a15.getA1517())){
					// ??????????
					if (a1500 == null || "".equals(a1500)) {
						
						applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A15(), a15));
						
						sess.save(a15);
					} else {
						A15 a15_old = (A15)sess.get(A15.class, a1500);
						applog.createLog("3152", "A15", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a15_old, a15));
						PropertyUtils.copyProperties(a15_old, a15);
						
						sess.update(a15_old);
					}	
				}
				sess.flush();
				//????a01????????????
				String a15z101 = this.getPageElement("a15z101").getValue();
				a01.setA15z101(a15z101);
				
				//??????????????
				isconnect = this.getPageElement("a0191").getValue();
				a01.setA0191(isconnect);
				if("1".equals(isconnect)){
					listAssociation();
				}
				//????????????????
				//??????????????????????????
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
				sess.update(a01);
				sess.flush();			
				this.setMainMessage("??????????");
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("??????????");
				return EventRtnType.FAILD;
			}
			
			this.getPageElement("a1500").setValue(a15.getA1500());//??????????id??????????????
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	/**
	 * ????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveAll.onclick")
	@Transaction
	@Synchronous(true)
	public int saveAllInfo()throws RadowException, AppException{
		String a1500 = this.getPageElement("a1500").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1521s = this.getPageElement("a1521").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1517 = this.getPageElement("a1517").getValue();//????????
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//????????????????????
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			//????????
			StringBuffer years = new StringBuffer("");
			if(list!=null&&list.size()>0){
				for(A15 a15y : list){
					years.append(a15y.getA1521()+",");
				}
			}
			String [] num = a1521s.split(",");
			//????????????????????
			for(int i=0;i<num.length;i++){
				if(years.indexOf(num[i])!=-1||a1517==null||"".equals(a1517)){
					continue;
				}			
				A15 a15save = new A15();
				a15save.setA1521(num[i]);
				a15save.setA1517(a1517);
				a15save.setA0000(a0000);
				
				applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A15(), a15save));
				
				sess.save(a15save);
			}
			sess.flush();
			//????a01????????????
			String a15z101 = this.getPageElement("a15z101").getValue();
			a01.setA15z101(a15z101);
			
			//??????????????
			String isconnect = this.getPageElement("a0191").getValue();
			a01.setA0191(isconnect);
			if("1".equals(isconnect)){
				listAssociation();
			}
			//????????????????
			//??????????????????????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
			sess.update(a01);
			sess.flush();			
			
			sess.flush();			
			//this.setMainMessage("??????????");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1500").setValue("");//????????id??
		this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");//????????
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.dogridquery")
	@NoRequiredValidate
	public int assessmentInfoGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A15 where a0000='"+a0000+"' order by to_number(a1521) asc";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int assessmentInfoGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("AssessmentInfoGrid").getCueRowIndex();
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * ????????????
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("listAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int listAssociation()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//????????????????
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1527 = this.getPageElement("a1527").getValue();//????????????
		if(a1527==null||"".equals(a1527)){
			return EventRtnType.NORMAL_SUCCESS;	
		}
		HBUtil.executeUpdate("update a15 set a1527='"+a1527+"' where a0000='"+a0000+"'");
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "from A15 where a0000='"+a0000+"' order by to_number(a1521) asc";
		List<A15> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();
		if(list!=null&&list.size()>0){
			int years = "".equals(a1527)?list.size():Integer.valueOf(a1527);
			if(years>list.size()){
				years = list.size();
			}
			StringBuffer desc = new StringBuffer("");
			for(int i=list.size()-years;i<list.size();i++){
				A15 a15 = list.get(i);
				//????????
				String a1521 = a15.getA1521();
				//????????
				String a1517 = a15.getA1517();
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				desc.append(a1521+"??????????"+a1517Name+"??");
			}
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "??");
			}
			this.getPageElement("a15z101").setValue(desc.toString());
			
			a01.setA15z101(desc.toString());
			sess.update(a01);
			//????????????????
			//??????????????????????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}else{
			String description = "??";
			this.getPageElement("a15z101").setValue(description);
			a01.setA15z101(description);
			sess.update(a01);
			//??????????????????????????
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteRowA15")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1500)throws RadowException, AppException{
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			A01 a01= (A01)sess.get(A01.class, a15.getA0000());
			String a1527 = a15.getA1527();
			
			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			sess.delete(a15);
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			a15 = new A15();
			a15.setA1527(a1527);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
			listAssociation();
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//????????
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |??][0-9| ]{2}[\\-|??]{1,2}[0-9| ]{4}[\\.| |??][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//??????????  (????)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	originaljl.append(jl).append("\r\n");
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	//????????
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//????
		//32??????????
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//???? 64??????????????????????????????
    		boolean hass = false;
    		while((end+offset)<llength){//32????????????????????
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32??????????????64?????? ??????
    				loffset++;
    				if((end+offset+loffset)>llength){//?????????? ????????
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//??????????????65????????????????????
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	 /**
     * ??????POJO??????JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
    
    /**
     * ??????POJO??????JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> String objectToString(T obj) throws JSONException, IOException {
    	
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "{}";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
	
    
    /***********************************************??????????????????(A36)*********************************************************************/
	@PageEvent("saveA36.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA36()throws RadowException, AppException{
		A36 a36 = new A36();
		this.copyElementsValueToObj(a36, this);
		String a3600 = this.getPageElement("a3600").getValue();					//????????
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();		//????a0000??????????????
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????????????
		String a3604a = this.getPageElement("a3604a").getValue();
		if(a3604a == null || "".equals(a3604a)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????????????
		String a3601 = this.getPageElement("a3601").getValue();
		if(a3601 == null || "".equals(a3601)){
			this.setMainMessage("??????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a3601.length() > 18){
			this.setMainMessage("????????????18??????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????????????????
		/*String a3607 = this.getPageElement("a3607").getValue();
		if(a3607 == null || "".equals(a3607)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		
		//????????????????
		String a3627 = this.getPageElement("a3627").getValue();
		if(a3627 == null || "".equals(a3627)){
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//??????????????????????
		String a3611 = this.getPageElement("a3611").getValue();
		if(a3611 == null || "".equals(a3611)){
			this.setMainMessage("????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a3611.length() > 50){
			this.setMainMessage("????????????50??????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a36.setA0000(a0000);			//set????
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			if(a3600==null||"".equals(a3600)){
				applog.createLog("3141", "A36", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A14(), a36));
				sess.save(a36);	
			}else{
				A36 a36_old = (A36)sess.get(A36.class, a3600);
				applog.createLog("3142", "A36", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a36_old, a36));
				PropertyUtils.copyProperties(a36_old, a36);
				sess.update(a36_old);	
			}
			
			sess.update(a01);
			sess.flush();			
			this.setMainMessage("??????????");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a36.getA3600());//??????????id??????????????
		this.getExecuteSG().addExecuteCode("radow.doEvent('familyid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * ??????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("familyid.dogridquery")
	@NoRequiredValidate
	public int familyid(int start,int limit) throws RadowException{
		
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select a.* from A36 a where a0000='"+a0000+"' order by SORTID";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * ??????????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("familyAddBtn.onclick")
	@NoRequiredValidate
	public int familyAddBtn(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A36 a36 = new A36();
		PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????????????????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("familyid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int familyidOnRowClick() throws RadowException{ 
		//??????????index
		int index = this.getPageElement("familyid").getCueRowIndex();
		String a3600 = this.getPageElement("familyid").getValue("a3600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//??????????????????????
	@PageEvent("deleteRowA36")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA36(String a3600)throws RadowException, AppException{
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			A01 a01 = (A01)sess.get(A01.class, a36.getA0000());
			applog.createLog("3143", "A36", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(new A36(), new A36()));
			sess.delete(a36);
			this.getExecuteSG().addExecuteCode("radow.doEvent('familyid.dogridquery')");
			a36 = new A36();
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);	
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
    
    /**
	 * ??????????????A29??????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("bc10")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bc10() throws RadowException {
		
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		try {
			//??????????????
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			
			//2????????????????????????????????
			String a2911_combo = a29.getA2911();
			if(a2911_combo==null || "".equals(a2911_combo.trim())){
				this.setMainMessage("????????????????????????????");
				return EventRtnType.FAILD;
			}
			//??????????????????????????
			String a2944s = this.getPageElement("a2944s_combo").getValue();
			if(a2944s !=null && !a2944s.trim().equals("")) {
				if(a2944s.length()>20) {
					this.setMainMessage("??????????????????????????????20??????????");
					return EventRtnType.FAILD;
				}
				a29.setA2944(a2944s);
			}
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//??????????????????????????????????????????????????18??????
			String a0107 = a01.getA0107();//????????
			String a2907 = a29.getA2907();//??????????????
			if(a0107!=null&&!"".equals(a0107)&&a2907!=null&&!"".equals(a2907)){
				int age = getAgeNew(a2907,a0107);
				if(age<18){
					this.setMainMessage("????????????????????????????????????????18??????");
					return EventRtnType.FAILD;
				}
			}
			
			a29.setA0000(a0000);
			A29 a29_old = (A29)sess.get(A29.class, a0000);
			if(a29_old==null){
				a29_old = new A29();
			}
			PropertyUtils.copyProperties(a29_old, a29);
			sess.saveOrUpdate(a29_old);	
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("??????????");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    
	/**
	 * ??????????????A30??????????????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("bc11")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bc11() throws RadowException {
		
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="select a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
		List<Object> list=sess.createSQLQuery(sql).list();
		
		try {
			//??????????????
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			
			//1????????????????????????????????
			//????????????????????????????????????????
			String a0134 = a01.getA0134();//????????????
			String a3004 = a30.getA3004();//??????????????
			if(a0134!=null&&!"".equals(a0134)&&a3004!=null&&!"".equals(a3004)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a3004.length()==6){
					a3004 += "00";
				}
				if(a3004.compareTo(a0134)<0){
					this.setMainMessage("??????????????????????????????????");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			String orgid=this.getPageElement("orgid").getValue();
			String a3007a=a30.getA3007a();
			String a3007=this.getPageElement("a3007a_combo").getValue();
			String a3001 = a30.getA3001();
			if(a3001!=null&&!"".equals(a3001)){
				if(!a3001.startsWith("1")&&!a3001.startsWith("2")&&StringUtil.isEmpty(orgid)){
					 this.setMainMessage("????????????????");
					 return EventRtnType.FAILD; 
				}
				//????????     ??????
				if(a3001.startsWith("1")||a3001.startsWith("2")){
					if(null == a30.getA3007a() || "".equals(a30.getA3007a())){
						this.setMainMessage("????????????????");
						return EventRtnType.FAILD;
					}
					if("-1".equals(a30.getA3007a())){
						a01.setA0163("2");
						a01.setStatus("2");
						a01.setOrgid(list.get(0).toString());
					}else{
						a01.setA0163("1");
						a01.setStatus("1");
						a01.setOrgid(list.get(0).toString());
					}
					
				}else if("35".equals(a3001)){//????  ??????????????       ??????????????
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31".equals(a3001)){//?????? ????????????????     ??????????????
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else{//?????????????????????????????????????????????????????? ????????????????     ??????????????
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}
			}else{
				//????????????????????
			}
			a30.setA0000(a0000);
			
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
				applog.createLog("3301", "A30", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a30_old,a30));
				
			}else{
				applog.createLog("3302", "A30", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a30_old,a30));
			}
			PropertyUtils.copyProperties(a30_old, a30);
			
			sess.saveOrUpdate(a30_old);
			
		
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("??????????");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    
	/**
	 * ????????????A53??????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("bc12.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bc12() throws RadowException {
		
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			
			
			//??????????	id??????????uuid ?? ?????????? ??id??????null
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			
			if(a53.getA5399()==null||"".equals(a53.getA5399())){
				a53.setA5399(SysManagerUtils.getUserId());
			}
			a53.setA0000(a0000);
			A53 a53_old = null;
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
				a53_old = new A53();
				applog.createLog("3531", "A53", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a53_old,a53));
			}else{
				a53_old = (A53)sess.get(A53.class, a53.getA5300());
				applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a53_old,a53));
			}
			PropertyUtils.copyProperties(a53_old, a53);
			
			sess.saveOrUpdate(a53_old);
			this.getPageElement("a5300").setValue(a53_old.getA5300());
			this.getPageElement("a5399").setValue(a53_old.getA5399());
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("??????????");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ????????????A71??????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("bc13.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int bc13() throws RadowException {
		
		HBSession sess = HBUtil.getHBSession();
		
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????a7101
		//String a7101 = this.getPageElement("a7101").getValue();
		String a7101 = this.request.getParameter("a7101");
		if(a7101 != null || "".equals(a7101)){
			if(a7101.length() > 1000){
				this.setMainMessage("????????????1000????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		try {
			//??????????
			A71 a71 = new A71();
			this.copyElementsValueToObj(a71, this);
			a71.setA0000(a0000);
			
			//??????????????????????????????A7100
			if(a71.getA7100() == null || a71.getA7100().equals("")){
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				a71.setA7100(uuid);
			}
			
			A71 a71_old = (A71)sess.get(A71.class, a0000);
			if(a71_old==null){
				a71_old = new A71();
				applog.createLog("3301", "A71", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a71_old,a71));
				
			}else{
				applog.createLog("3302", "A71", a01.getA0000(), a01.getA0101(), "????????", new Map2Temp().getLogInfo(a71_old,a71));
			}
			PropertyUtils.copyProperties(a71_old, a71);
			sess.saveOrUpdate(a71_old);	
			sess.flush();
			
			//????????????????????
			saveInfo_Extend(sess,a0000);
			
		} catch (Exception e) {
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("??????????");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ????????????????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("tabClick")
	@NoRequiredValidate
	public int tabClick(String tabid) throws RadowException {
		String id = null;
		if(tabid != null && !tabid.equals("")){
			id = tabid.substring(6);
		}
		
		String a0000check = (String)this.request.getSession().getAttribute("a0000");//??????????????????????
		String a0000 = id;
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			/*if(a0000==null||"".equals(a0000)||"add".equals(a0000)){
				this.setMainMessage("??????????");
				return EventRtnType.FAILD;
			}*/
			A01 a01 = null;
			/*if(a0000.indexOf("addTab-")!=-1){//??????????????????????????????????????????????????????????????
				a0000 = a0000.split("addTab-")[1];
				a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					a01 = new A01();
					a01.setA0000(a0000);
					a01.setA0163("1");//????????????
					a01.setA0104("1");//??????
					a01.setA14z101("??");//????????
					a01.setStatus("4");
					//a01.setA0197("0");//????????????????????????
					addUserInfo(a01);
					sess.save(a01);
					sess.flush();
					this.request.getSession().setAttribute("a0000", a0000);
					this.getExecuteSG().addExecuteCode("thisTab.initialConfig.personid='"+a0000+"';");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}*/
			//????????????updateWin.setTitle(title);
			if(a0000!=null && !a0000.equals("")){
				a01 = (A01)sess.get(A01.class, a0000);
				
				//????????????subWinIdBussessId????
				this.getExecuteSG().addExecuteCode("document.getElementById('subWinIdBussessId').value = '"+(a0000==null?"":a0000)+"';");
				
				//???? ???? ????
				String a0101 = a01.getA0101()==null?"":a01.getA0101();//????
				//String a0184 = a01.getA0184().toUpperCase();//????????//??????????????????x?????????????? add by lizs 20161110
				String a0107 = a01.getA0107();//????????
				String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
				String age = "";
				int agei = 0;
				
				if((agei = IdCardManageUtil.getAgefrombirth(a0107))!=-1){
					age = agei + "";
				}
				String title = a0101 + "??" + sex + "??" + age+"??";
				this.getExecuteSG().addExecuteCode("window.parent.tabs.getItem(thisTab.initialConfig.tabid).setTitle('"+title.replaceAll("<", "&lt;").replaceAll("'", "&acute;")+"');");
			}
			
		} catch (Exception e) {
			this.setRadow_parent_data("");
			this.request.getSession().setAttribute("a0000", "");
			e.printStackTrace();
			this.setMainMessage("??????????");
			return EventRtnType.FAILD;
		}
		this.setRadow_parent_data(a0000);
		this.request.getSession().setAttribute("a0000", a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public int getAgeNew(String entryDate,String birth) {
		int returnAge;

		String birthYear = birth.substring(0, 4);
		String birthMonth = birth.substring(4, 6);
		String birthDay = "";
		if(birth.length()==6){
			birthDay = "01";
		}
		if(birth.length()==8){
			birthDay = birth.substring(6, 8);
		}
		
		String nowYear = entryDate.substring(0, 4);
		String nowMonth = entryDate.substring(4, 6);
		String nowDay = "";
		if(entryDate.length()==6){
			nowDay = "01";
		}
		if(entryDate.length()==8){
			nowDay = entryDate.substring(6, 8);
		}
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // ????????0??
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // ??????
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// ??????
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// ??????
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// ???????????? ????????
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "??)";
		int msg = returnAge ;
		return msg;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	
	/***********************************************??????A051*********************************************************************/
	@PageEvent("saveA11.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveTrainingInfo() throws RadowException, AppException {
		A05 a05 = new A05();
		this.copyElementsValueToObj(a05, this);
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		a05.setA0531("1");
		if (a0000 == null || "".equals(a0000)) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (a05.getA0501b() == null || "".equals(a05.getA0501b())) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a05.setA0000(a0000);
		String a0500 = a05.getA0500();
		String a0504 = a05.getA0504();// 
		String a0517 = a05.getA0517();//
		String a0524 = a05.getA0524();//????????????????
		a05.setA0525(a0524);
		String a0501b = a05.getA0501b();//????
		int start =0;
		int end =0;
		if (a0504 != null && !"".equals(a0504)) {
			start = Integer.valueOf(a0504);
			a05.setA0504(a0504);
		}else{
			a05.setA0504(null);
		}
		if(a0517 != null && !"".equals(a0517)){
			end = Integer.valueOf(a0517);
			a05.setA0517(a0517);
		}else{
			a05.setA0517(null);
		}
		if (start!=0 && end!=0 && start > end) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			A05 a05_old = null;
			if (a0500 == null || "".equals(a0500)) {
				if("1".equals(a0524)){//????????????????????????????????????????????
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='1' and a0000='"+a0000+"'").list();//??????????????????????????
					if(list_a05.size()>0){//????????????????????????
						this.getExecuteSG().addExecuteCode("window.$h.alert('????????','????????????????????',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='1' ";//??????????????????????????0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0192e(a0501b); //?????????? ??????????????????
						a01.setA0192c(a0504);//??????????
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('"+(a0501b==null?"":a0501b)+"')");//???????? ??????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('"+(a0504==null?"":a0504)+"')");//???????? ??????????????????????
					}
				}
				a05_old = new A05();
				applog.createLog("3112", "A05", a01.getA0000(), a01.getA0101(), "????????",
						new Map2Temp().getLogInfo(a05_old, a05));
				sess.save(a05);
				sess.flush();
			} else {
				a05_old = (A05) sess.get(A05.class, a0500);
				if("1".equals(a0524)){//????????????????????????????????????????????
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='1' and a0000='"+a0000+"' and a0500<> '"+a0500+"'").list();//??????????????????????????
					if(list_a05.size()>0){//????????????????????????????
						this.getExecuteSG().addExecuteCode("window.$h.alert('????????','????????????????????',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='1' ";//??????????????????????????0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0192e(a0501b); //?????????? ??????????????????
						a01.setA0192c(a0504);//??????????
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('"+(a0501b==null?"":a0501b)+"')");//???????? ??????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('"+(a0504==null?"":a0504)+"')");//???????? ??????????????????????
					}
				}else if("0".equals(a0524)){//????????????
					if("1".equals(a05_old.getA0524())){//??????????
						a01.setA0192e(null);//???? ?????????????????? ????
						a01.setA0192c(null);//??????????
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('')");//???????? ??????????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue(''");//???????? ??????????????????????
					}
				}
				applog.createLog("3113", "A05", a01.getA0000(), a01.getA0101(), "????????",
						new Map2Temp().getLogInfo(a05_old, a05));
				PropertyUtils.copyProperties(a05_old, a05);
				sess.update(a05_old);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0500").setValue(a05.getA0500());// ??????????id??????????????
		this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteRowA051")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA051(String a0500) throws RadowException, AppException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			A01 a01 = (A01) sess.get(A01.class, a05.getA0000());
			applog.createLog("3114", "A05", a01.getA0000(), a01.getA0101(), "????????",
					new Map2Temp().getLogInfo(new A05(), new A05()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[] { a05.getA0500() });
			
			String a0524 = a05.getA0524();
			if("1".equals(a0524)){
				a01.setA0192e(null); //?????????? ??????????????????
				a01.setA0192c(null);//??????????
				sess.saveOrUpdate(a01);
				sess.flush();
				//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('')");//???????? ??????????????????????
				//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('')");//???????? ??????????????????????
			}
			
			sess.delete(a05);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a05 = new A05();
			PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????????grid????
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int trainingInforGridQuery(int start, int limit) throws RadowException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A05 where a0000='" + a0000 + "' and a0531='1'";
		this.pageQuery(sql, "SQL", start, limit); // ????????????
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 
	 * ????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoAddBtn.onclick")
	@NoRequiredValidate
	public int trainingInforAddBtnWin(String id) throws RadowException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A05 a05 = new A05();
		a05.setA0000(a0000);
		PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("TrainingInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int trainingInforGridOnRowClick() throws RadowException {
		int index = this.getPageElement("TrainingInfoGrid").getCueRowIndex();
		String a0500 = this.getPageElement("TrainingInfoGrid").getValue("a0500", index).toString();
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	/***********************************************??????A050*********************************************************************/
	@PageEvent("saveA12.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveRank() throws RadowException, AppException {
		A05 a05 = new A05();
		//this.copyElementsValueToObj(a05, this);
		
		a05.setA0500(this.getPageElement("a0500Post").getValue());
		a05.setA0501b(this.getPageElement("a0501bPost").getValue());
		a05.setA0524(this.getPageElement("a0524Post").getValue());
		a05.setA0504(this.getPageElement("a0504Post").getValue());
		a05.setA0517(this.getPageElement("a0517Post").getValue());
		
		
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		a05.setA0531("0");
		if (a0000 == null || "".equals(a0000)) {
			this.getExecuteSG().addExecuteCode("$h.alert('????????','??????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (a05.getA0501b() == null || "".equals(a05.getA0501b())) {
			this.getExecuteSG().addExecuteCode("$h.alert('????????','??????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a05.setA0000(a0000);
		String a0500 = a05.getA0500();
		String a0504 = a05.getA0504();// 
		String a0517 = a05.getA0517();//
		String a0524 = a05.getA0524();//????????????????
		String a0501b = a05.getA0501b();//????????
		a05.setA0525(a0524);//????????a0525==a0524
		int start = 0;
		int end = 0;
		if(a0504 != null && !"".equals(a0504)){
			start = Integer.valueOf(a0504);
			a05.setA0504(a0504);
		}else{
			a05.setA0504(null);
		}
		if(a0517 != null && !"".equals(a0517)){
			end = Integer.valueOf(a0517);
			a05.setA0517(a0517);
		}else{
			a05.setA0517(null);
		}
		if(start!=0 && end!=0 && start>end){
			this.getExecuteSG().addExecuteCode("$h.alert('????????','??????????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		A05 a05_old = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if (a0500 == null || "".equals(a0500)) {
				a05_old = new A05();
				if("1".equals(a0524)){//??????????
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"'").list();//??????????????????????????
					if(list_a05.size()>0){//????????????????????????
						this.getExecuteSG().addExecuteCode("$h.alert('????????','????????????????????????',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{//??????????????????????????
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//??????????????????????????????0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b);//?????????? ?????????????????????? ??????????????????
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//???????? ??????????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//???????? ??????????????????????????
					}
				}
				applog.createLog("3112", "A05", a01.getA0000(), a01.getA0101(), "????????",
						new Map2Temp().getLogInfo(a05_old, a05));
				sess.save(a05);
				sess.flush();
			} else {
				a05_old = (A05) sess.get(A05.class, a0500);
				if("1".equals(a0524)){//????????????????????????????????????????????
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"' and a0500<> '"+a0500+"'").list();//??????????????????????????
					if(list_a05.size()>0){//????????????????????????????
						this.getExecuteSG().addExecuteCode("$h.alert('????????','????????????????????',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//??????????????????????????0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b); //?????????? ?????????????????????? ??????????????????
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//???????? ??????????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//???????? ??????????????????????????
					}
				}else if("0".equals(a0524)){//????????????
					if("1".equals(a05_old.getA0524())){//??????????
						a01.setA0221(null);//???? ?????????????????????? ????
						a01.setA0288(null);
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('')");//???????? ??????????????????????
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//???????? ??????????????????????????
					}
				}
				applog.createLog("3113", "A05", a01.getA0000(), a01.getA0101(), "????????",
						new Map2Temp().getLogInfo(a05_old, a05));
				PropertyUtils.copyProperties(a05_old, a05);
				sess.update(a05_old);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('????????','??????????',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0500Post").setValue(a05.getA0500());// ??????????id??????????????
		// this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//????????????????????
		this.getExecuteSG().addExecuteCode("radow.doEvent('rankGrid.dogridquery')");
		this.setNextEventName("rankAddBtn.onclick");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteRowA050")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA050(String a0500) throws RadowException, AppException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			A01 a01 = (A01) sess.get(A01.class, a05.getA0000());
			applog.createLog("3114", "A05", a01.getA0000(), a01.getA0101(), "????????",
					new Map2Temp().getLogInfo(new A05(), new A05()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[] { a05.getA0500() });
			
			String a0524 = a05.getA0524();
			if("1".equals(a0524)){
				a01.setA0288(null);
				a01.setA0221(null);//???? ?????????????????????? ????
				sess.saveOrUpdate(a01);
				sess.flush();
				//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('')");//???????? ??????????????????????
				//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//???????? ??????????????????????????
			}
			
			sess.delete(a05);
			this.getExecuteSG().addExecuteCode("radow.doEvent('rankGrid.dogridquery')");
			a05 = new A05();
			//PMPropertyCopyUtil.copyObjValueToElement(a05, this);
			this.getPageElement("a0500Post").setValue(a05.getA0500());
			this.getPageElement("a0501bPost").setValue(a05.getA0501b());
			this.getPageElement("a0524Post").setValue(a05.getA0524());
			this.getPageElement("a0504Post").setValue(a05.getA0504());
			this.getPageElement("a0517Post").setValue(a05.getA0517());
			this.getPageElement("a0504Post_1").setValue(a05.getA0504());
			this.getPageElement("a0517Post_1").setValue(a05.getA0517());
			
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * ??????????grid????
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rankGrid.dogridquery")
	@NoRequiredValidate
	public int rankGridQuery(int start, int limit) throws RadowException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A05 where a0000='" + a0000 + "' and a0531='0'";
		this.pageQuery(sql, "SQL", start, limit); // ????????????
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 
	 * ????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rankAddBtn.onclick")
	@NoRequiredValidate
	public int rankAddBtn(String id) throws RadowException {
		
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		if (a0000 == null || "".equals(a0000)) {//
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????????????????',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A05 a05 = new A05();
		a05.setA0000(a0000);
		//PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		this.getPageElement("a0500Post").setValue(a05.getA0500());
		this.getPageElement("a0501bPost").setValue(a05.getA0501b());
		this.getPageElement("a0524Post").setValue(a05.getA0524());
		this.getPageElement("a0504Post").setValue(a05.getA0504());
		this.getPageElement("a0517Post").setValue(a05.getA0517());
		this.getPageElement("a0504Post_1").setValue(a05.getA0504());
		this.getPageElement("a0517Post_1").setValue(a05.getA0517());
		
		this.getExecuteSG().addExecuteCode("setA0517DisabledA050();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rankGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int rankGridOnRowClick() throws RadowException {
		int index = this.getPageElement("rankGrid").getCueRowIndex();
		String a0500 = this.getPageElement("rankGrid").getValue("a0500", index).toString();
		String a0000=this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A05 a05 = (A05) sess.get(A05.class, a0500);
			a05.setA0000(a0000);
			//PMPropertyCopyUtil.copyObjValueToElement(a05, this);
			this.getPageElement("a0500Post").setValue(a05.getA0500());
			this.getPageElement("a0501bPost").setValue(a05.getA0501b());
			this.getPageElement("a0524Post").setValue(a05.getA0524());
			this.getPageElement("a0504Post").setValue(a05.getA0504());
			this.getPageElement("a0517Post").setValue(a05.getA0517());
			
			//??????????????????????????????
			String a0504Time = null;
			if(a05.getA0504() != null && !a05.getA0504().equals("")){
				a0504Time = a05.getA0504().substring(0,4) + "." + a05.getA0504().substring(4,6);
			}
			
			
			//??????????????????????????????
			String a0517Time = null;
			if(a05.getA0517() != null && !a05.getA0517().equals("")){
				a0517Time = a05.getA0517().substring(0,4) + "." + a05.getA0517().substring(4,6);
			}
			
			
			this.getPageElement("a0504Post_1").setValue(a0504Time);
			this.getPageElement("a0517Post_1").setValue(a0517Time);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('????????','??????????',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setA0517DisabledA050();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("a0201bChange")
	@NoRequiredValidate
	public int a0201bChange(String a0201b) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0201b+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		
		//??????????????????????
		int num = this.getPageElement("WorkUnitsGrid").getStringValueList().size();
		if(num == 0){
			
			String a0195 = this.getPageElement("a0195").getValue();
			
			
				if(B0194 != null && !B0194.equals("2")){				//??????????????????????????????????????
					
					if((a0195 != null && a0195.equals("")) || num == 0){			//????????????????????????????????????????
					
						this.getPageElement("a0195").setValue(a0201b);
						String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+a0201b+"'");
						if(v!=null){
							this.getPageElement("a0195_combo").setValue(v);
						}else{
							this.getPageElement("a0195_combo").setValue("");
						}
					
					}
				}else{
					this.getPageElement("a0195").setValue("");
					this.getPageElement("a0195_combo").setValue("");
				}
			
			
			
		}
				
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//????????????????????????
	public static Map<String, List<Object[]>> getInfoExt(){
		String sql = getInfoSQL();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery(sql).list();
			Map<String, List<Object[]>> info_map = new LinkedHashMap<String, List<Object[]>>();
			if(list!=null&&list.size()>0){
				for(Object[] os : list){
					List<Object[]> os_list = info_map.get(os[0]+"___"+os[4]);
					if(os_list==null){
						os_list = new ArrayList<Object[]>();
						os_list.add(os);
						info_map.put(os[0].toString()+"___"+os[4].toString(), os_list);
					}else{
						os_list.add(os);
					}
					
				}
			}
			return info_map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//????????????????????sql
	private static String getInfoSQL(){
		return "select t.table_code, t.col_code,t.col_name,t.code_type,a.add_type_name,t.col_data_type_should " +
		" from (select ctc.table_code, ctc.col_code,ctc.col_name,ctc.code_type," +
		" ctc.col_data_type_should,ctc.is_new_code_col,av.isused from code_table_col ctc " +
		" left join add_value av on ctc.col_code=av.col_code) t " +
		" left join add_type a on t.table_code=a.table_code where t.is_new_code_col='1' and t.isused='1' and t.table_code " +
		" in('A01','A02','A06','A08','A11','A14','A15','A29','A30','A31','A36','A37','A53')";
	}
	
	
	
	private void saveInfo_Extend(HBSession sess, String a0000) throws Exception {
		String sql = getInfoSQL();

		List<Object[]> list = sess.createSQLQuery(sql).list();
		Map<String,String> field = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for(Object[] os : list){
				field.put(os[1].toString(), os[2].toString());
			}
		}
		sess.flush();
		
		
		List<Object> Info_Extends = sess.createSQLQuery("select a0000 from Info_Extend where a0000='"+a0000+"'").list();
		
		if(field.size()>0){//??????????
			if(Info_Extends==null||Info_Extends.size()==0){//????
				StringBuffer insert_sql = new StringBuffer("insert into Info_Extend(a0000,");
				StringBuffer values = new StringBuffer(" values('"+a0000+"',");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						insert_sql.append(key+",");
						values.append("?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						if(DBType.ORACLE == DBUtil.getDBType()){
							HBUtil.executeUpdate("alter table Info_Extend add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend."+key+" is '"+comment+"'");
							HBUtil.executeUpdate("alter table Info_Extend_Temp add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend_Temp."+key+" is '"+comment+"'");
						}else{
							HBUtil.executeUpdate("ALTER TABLE info_extend add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							HBUtil.executeUpdate("ALTER TABLE Info_Extend_Temp add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
						}
					}catch (Exception e) {
					}
				}
				insert_sql.deleteCharAt(insert_sql.length()-1).append(")");
				values.deleteCharAt(values.length()-1).append(")");
				insert_sql.append(values);
				HBUtil.executeUpdate(insert_sql.toString(), args);
			}else{//????
				StringBuffer update_sql = new StringBuffer("update Info_Extend set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						update_sql.append(key+"=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						if(DBType.ORACLE == DBUtil.getDBType()){
							HBUtil.executeUpdate("alter table Info_Extend add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend."+key+" is '"+comment+"'");
							HBUtil.executeUpdate("alter table Info_Extend_Temp add "+key+" varchar2(200)");
							HBUtil.executeUpdate("comment on column Info_Extend_Temp."+key+" is '"+comment+"'");
						}else{
							HBUtil.executeUpdate("ALTER TABLE info_extend add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							HBUtil.executeUpdate("ALTER TABLE Info_Extend_Temp add "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
						}
						
					}catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length()-1).append(" where a0000='"+a0000+"'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}
			
		}
	}
	
	
	private void readInfo_Extend(HBSession sess, String a0000) throws AppException {
		List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from Info_Extend where a0000='"+a0000+"'",null);
			if(Info_Extends!=null&&Info_Extends.size()>0){//
				Map<String,String> entity = Info_Extends.get(0);
				for(String key : entity.keySet()){
					try {
						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
						//????????????????????????????????
						
						if(entity.get(key).length() >= 6 && entity.get(key).length() <= 8){
							
							String timeShow = entity.get(key).substring(0,4) + "." + entity.get(key).substring(4,6);
							
							this.getPageElement(key.toLowerCase()+"_1").setValue(timeShow);
						}
						
						
					} catch (Exception e) {
					}
				}
			}
	}
	
	
	
	
	//----------------------------------------------------------????????--------------------------------------------------------
	
	
	//??????????????tree
	@PageEvent("folderTree")
	@NoRequiredValidate
	public int folderTreeJsonData() throws PrivilegeException, RadowException {
		String a0000 = this.request.getParameter("a0000New");
		
		
		String jsonStr = getJsonFolderTree("5","",a0000);
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	@NoRequiredValidate
	public String getJsonFolderTree(String type,String nodeother,String a0000) throws RadowException{
		
		treeType=0;
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		String node="";
		if("".equals(nodeother)){
			node = this.getParameter("node");
		}else{
			node = nodeother;
		}
		int nodelength = 0;
		if(node.equals("-1")){
			nodelength=3;
			node="001";				//????????????id??001
		}else{
			nodelength=node.length();
		}
		int nodelength1 =nodelength+4;
		int nodelength2 = nodelength1+2;
		
		String sql1 = "(select substr(id,1,"+nodelength1+") b01111,max(length(trim(substr(id,"+nodelength2+",3)))) count1 from FOLDERTREE t where a0000 = '"+ a0000 +"' and t.id like '"+node+".%' group by substr(id,1,"+nodelength1+")) cc";
		String sql ="select cc.count1,t.id,t.PARENTID,t.NAME,t.A0000 from FOLDERTREE t join "+sql1+" on t.id = cc.b01111 order by id";
		
		CommonQueryBS.systemOut(sql);
		CommonQueryBS.systemOut(sql);
		List<HashMap> list =null;
		try {
			list = CommonQueryBS.getQueryInfoByManulSQL(sql);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//????????????????
		
		StringBuffer jsonStr = new StringBuffer();
		if (list != null && !list.isEmpty()) {
			int i = 0;
			int last = list.size();
			for (HashMap group : list) {
				Object o = group.get("name");
				if(o!=null){
					group.put("name", o.toString().replaceAll("\r|\n|\r\n", ""));
				}
				if(i==0 && last==1) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				}else if (i == 0) {
					jsonStr.append("[");
					appendjson(type, group, jsonStr);
				}else if (i == (last - 1)) {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
					jsonStr.append("]");
				} else {
					jsonStr.append(",");
					appendjson(type, group, jsonStr);
				}
				i++;
			}
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		return jsonStr.toString();
		
	}
	
	@NoRequiredValidate
	private String appendjson(String type,HashMap map,StringBuffer sb_tree){
		String icon ="";
		
		icon="./main/images/tree/folder.gif";
		if(type.equals(LEFTTREE)){
				sb_tree.append(" {text: '"+map.get("name")+"',id:'"+map.get("b0111")+"',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyid','"+map.get("b0111")+"')\"}");
		}else if(type.equals(FIVETREE)){
				sb_tree.append(" {text: '"+map.get("name")+"',id:'"+map.get("id")+"',leaf:"+hasChildren((String)map.get("count1"))+",editable:'true',icon:'"+icon+"',\"dblclick\":\"javascript:radow.doEvent('folderGriddb','"+map.get("id")+"')\"}");
		}else{
				sb_tree.append(" {text: '"+map.get("name")+"',id:'"+map.get("b0111")+"|true',leaf:"+hasChildren((String)map.get("count1"))+",icon:'"+icon+"',\"href\":\"javascript:radow.doEvent('querybyidorg','"+map.get("b0111")+"|true--"+map.get("b0101")+"')\"}");
		}
		return sb_tree.toString();
	}
	
	@NoRequiredValidate
	private String hasChildren(String id){
		if("3".equals(id)){
			return "false";
		}
		return "true";
	}
	
	
	/**
	 * ????????tree????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("folderGriddb")
	@NoRequiredValidate
	public int folderGrid(String treeId) throws RadowException{
		this.getPageElement("treeId").setValue(treeId);
		this.setNextEventName("folderGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????????????????
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("folderGrid.dogridquery")
	@NoRequiredValidate
	public int folderGridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String treeId = this.getPageElement("treeId").getValue();
		String sql = "select * from FOLDER where a0000='"+a0000+"' and TREEID = '"+treeId+"' order by time desc";
		this.pageQuery(sql,"SQL", start, limit); //????????????
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	 * ??????????????????????????
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("openNewWindow")
	@NoRequiredValidate
	public int openNewWindow() throws RadowException {
		
		String treeId = this.getPageElement("treeId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(treeId==null || "".equals(treeId)) {
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a0000==null || "".equals(a0000)) {
			this.setMainMessage("??????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setRadow_parent_data("NEW,"+treeId+","+a0000);
		this.openWindow("AddFolderTree","pages.publicServantManage.AddFolderTree");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ??????????????????????tree????
	 * @param nodeId
	 * @return
	 * @throws RadowException
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("update")
	@NoRequiredValidate
	public int update() throws RadowException {
		String treeId = this.getPageElement("treeId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(treeId==null || "".equals(treeId)) {
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("001.001".equals(treeId)) {
			this.setMainMessage("????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setRadow_parent_data("update,"+treeId+","+a0000);
		this.openWindow("UpdateFolderTree","pages.publicServantManage.AddFolderTree");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ??????????
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@SuppressWarnings("unchecked")
	@PageEvent("delete")
	@NoRequiredValidate
	public int delete() throws RadowException {
		
		HBSession sess = HBUtil.getHBSession();
		String treeId = this.getPageElement("treeId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(treeId==null || "".equals(treeId)) {
			this.setMainMessage("??????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("001.001".equals(treeId)) {
			this.setMainMessage("????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//????tree??????????????????????????????????????????????
		String sqlCount = "select count(id) from FOLDER where a0000='"+a0000+"' and TREEID = '"+treeId+"'";
		Object count = sess.createSQLQuery(sqlCount).uniqueResult();
		if(!String.valueOf(count).equals("0")){
			this.setMainMessage("??????????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String sqlTreeId = "select max(id) from foldertree where parentid = '"+treeId+"'";
		Object maxTreeIdO = sess.createSQLQuery(sqlTreeId).uniqueResult();
		if(maxTreeIdO != null && !maxTreeIdO.equals("")){	//????????tree
			this.setMainMessage("??????????????????????????????????");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		this.addNextEvent(NextEventValue.YES, "sureClear","");									//????sureClear??????????????????
		this.addNextEvent(NextEventValue.CANNEL, "");										    //??????????????????????????????????????????????
		this.setMessageType(EventMessageType.CONFIRM);										    //??????????????confirm????????
		this.setMainMessage("????????????????????????");				//????????????	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ??????????????
	 * @param nodeId
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("sureClear")
	@NoRequiredValidate
	public int sureClear(String nodeId) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		
		String treeId = this.getPageElement("treeId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		//????tree
		sess.createSQLQuery("delete from FolderTree where id = '"+treeId+"' and a0000 = '"+a0000+"'").executeUpdate();
		
		this.getPageElement("treeId").setValue("");
		this.getExecuteSG().addExecuteCode("reloadTree();");
		this.setNextEventName("folderGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
