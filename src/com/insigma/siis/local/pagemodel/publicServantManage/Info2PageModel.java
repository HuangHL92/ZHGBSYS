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
 * 信息录入页面
 * @author huangcheng
 *
 */
public class Info2PageModel extends PageModel{
	
	
	protected static final String LEFTTREE = "1";//机构信息维护树
	protected static final String OPENTREE = "2";//弹出树
	protected static final String THREETREE = "3";//邹磊用树
	protected static final String FOURTREE = "4";//名册导出用树
	protected static final String FIVETREE = "5";//人员信息维护树
	protected static final String SIXTREE = "6";//人员信息维护树
	
	private LogUtil applog = new LogUtil();
	public static int treeType = 0;
	//页面初始化
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.createPageElement("div1",ElementType.NORMAL,false).setDisplay(true);		//人员基本信息
		this.createPageElement("div2",ElementType.NORMAL,false).setDisplay(false);		//职务信息集
		this.createPageElement("div3",ElementType.NORMAL,false).setDisplay(false);		//现职级
		this.createPageElement("div4",ElementType.NORMAL,false).setDisplay(false);		//现职务
		this.createPageElement("div5",ElementType.NORMAL,false).setDisplay(false);		//专业技术职务信息集
		this.createPageElement("div6",ElementType.NORMAL,false).setDisplay(false);		//学历学位信息集
		this.createPageElement("div7",ElementType.NORMAL,false).setDisplay(false);		//奖惩信息集
		this.createPageElement("div8",ElementType.NORMAL,false).setDisplay(false);		//考核信息集
		this.createPageElement("div9",ElementType.NORMAL,false).setDisplay(false);		//家庭成员及社会关系
		this.createPageElement("div10",ElementType.NORMAL,false).setDisplay(false);		//进入管理信息集
		this.createPageElement("div11",ElementType.NORMAL,false).setDisplay(false);		//退出管理信息集
		this.createPageElement("div12",ElementType.NORMAL,false).setDisplay(false);		//拟任免信息集
		this.createPageElement("div13",ElementType.NORMAL,false).setDisplay(false);		//备注信息集
		this.createPageElement("div14",ElementType.NORMAL,false).setDisplay(false);		//电子档案
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//页面数据初始化
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String a0000 = this.getRadow_parent_data();
		//String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		
		//通过"nowNumber"是否有值，来判断 人员上下翻页 按钮是否显示
		Object obj = this.request.getSession().getAttribute("nowNumberXX");
		if(obj==null || "".equals(obj.toString())){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('lastp').hide();odin.ext.getCmp('nextp').hide();");
		}
		
		HBSession sess = HBUtil.getHBSession();
		
		if(a0000==null||"".equals(a0000)){  		//如果a0000为空，表示新增
			String random = UUID.randomUUID().toString();	//生成主键
			A01 a01 = new A01();
			a01.setA0000(random);
			a01.setA0163("1");//默认现职人员
			//a01.setA0104("1");//默认男
			a01.setA14z101("无");//奖惩描述
			a01.setStatus("4");
			a01.setA0197("0");//基层工作经历时间两年以上
			addUserInfo(a01);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);		//将初始化a01的值赋值到页面
			
			
		}else{				//修改，做页面数据的预加载
			a0000 = a0000.substring(6);
			
			try {
				//人员基本信息加载
				A01 a01 = (A01)sess.get(A01.class, a0000);
				PMPropertyCopyUtil.copyObjValueToElement(a01, this);
				
				
				//弹窗中按照职务信息生成的简历
				genResume();
				
				
				//职务信息集加载
				this.setNextEventName("WorkUnitsGrid.dogridquery");
				//统计关系所在单位 名称，获得父页面值
				if(a01.getA0195()!=null){
					String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
					if(a0195!=null){
						this.getPageElement("a0195_combo").setValue(a0195);//机构名称 中文。
					}
				}
				
				//现职级信息加载
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
				this.setNextEventName("TrainingInfoGrid.dogridquery");// 现职级信息列表
				
				
				//现职务信息加载
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
				this.setNextEventName("rankGrid.dogridquery");// 现职务信息列表
				
				
				//专业技术职务信息加载
				this.setNextEventName("professSkillgrid.dogridquery");
				
				
				//学历学位信息集加载
				this.setNextEventName("degreesgrid.dogridquery");
				
				//奖惩信息集加载
				this.setNextEventName("RewardPunishGrid.dogridquery");
				
				
				//年度考核信息加载
				this.setNextEventName("AssessmentInfoGrid.dogridquery");//年度考核情况列表
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
				List<A15> list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					A15 a15 = list.get(0);
					this.getPageElement("a1527").setValue(a15.getA1527());
				}
				
				
				//家庭成员及社会关系信息加载
				this.setNextEventName("familyid.dogridquery");
				
				
				//进入管理信息集加载
				A29 a29 = (A29) sess.get(A29.class, a0000);
				
				if(a29 != null){
					//在原单位职务层次写入
					String a2944s = a29.getA2944();
					if(a2944s!=null && !a2944s.trim().equals("null")) {
						this.getExecuteSG().addExecuteCode("document.getElementById('a2944s_combo').value='"+a2944s+"';");
					}
					if (a29 != null) {
						PMPropertyCopyUtil.copyObjValueToElement(a29, this);
					}
				}
				
				
				
				
				
				//退出管理信息集加载
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
					
					//判断退出管理方式是否为调入或转出
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
								this.getPageElement("a3007a_combo").setValue(a3007a);//机构名称 中文。
							}
						}
					}
				}else{
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				}
				
				
				
				//拟任免信息集加载
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
				
				
				//备注信息集加载
				String sqlA71 = "from A71 where a0000='" + a0000 + "'";
				List listA71 = sess.createQuery(sqlA71).list();
				if (listA71 != null && listA71.size() > 0) {
					A71 a71 = (A71) listA71.get(0);
					PMPropertyCopyUtil.copyObjValueToElement(a71, this);
				}
				
				//初始化扩展信息项
				readInfo_Extend(sess,a0000);
				
				
				//判断是否有修改权限。c.type：机构权限类型(0：浏览，1：维护)
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
				/*------------判断该人员的管理类别浏览权限--------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//替换垃圾数据
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
					//页面所有信息集保存按钮不可用
					this.getExecuteSG().addExecuteCode("Ext.getCmp('InfoSaveA01').setDisabled(true);Ext.getCmp('save').setDisabled(true);"
							+ "Ext.getCmp('saveA06').setDisabled(true);Ext.getCmp('saveA08').setDisabled(true);Ext.getCmp('saveA14').setDisabled(true);"
							+ "Ext.getCmp('saveA15').setDisabled(true);Ext.getCmp('saveA36').setDisabled(true);Ext.getCmp('bc10').setDisabled(true);"
							+ "Ext.getCmp('bc11').setDisabled(true);Ext.getCmp('bc12').setDisabled(true);Ext.getCmp('bc13').setDisabled(true);"
							+ "Ext.getCmp('saveA050').setDisabled(true);Ext.getCmp('saveA051').setDisabled(true);"
							+ "document.getElementById('isUpdate').value = '2'"
							
						);
				}
				if(elist2==null||elist2.size()==0){//维护权限
					if(elist!=null&&elist.size()>0){//有浏览权限
						//页面所有信息集保存按钮不可用
						this.getExecuteSG().addExecuteCode("Ext.getCmp('InfoSaveA01').setDisabled(true);Ext.getCmp('save').setDisabled(true);"
							+ "Ext.getCmp('saveA06').setDisabled(true);Ext.getCmp('saveA08').setDisabled(true);Ext.getCmp('saveA14').setDisabled(true);"
							+ "Ext.getCmp('saveA15').setDisabled(true);Ext.getCmp('saveA36').setDisabled(true);Ext.getCmp('bc10').setDisabled(true);"
							+ "Ext.getCmp('bc11').setDisabled(true);Ext.getCmp('bc12').setDisabled(true);Ext.getCmp('bc13').setDisabled(true);"
							+ "Ext.getCmp('saveA050').setDisabled(true);Ext.getCmp('saveA051').setDisabled(true);"
							+ "document.getElementById('isUpdate').value = '2'"
						);
					}else{	
						//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；1 其他现职人员可查看，可编辑
						/*if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
				this.setMainMessage("查询失败！");
				return EventRtnType.FAILD;
			}
			
			
		}
		
		
		//照片
		this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+a0000+"'");
		A57 a57 = (A57)sess.get(A57.class, a0000);
		if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
			String photourl = a57.getPhotopath();
			File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
			if(fileF.isFile()){
				long nLen = fileF.length();
				String imageSize = nLen/1024 + "K";
				this.getExecuteSG().addExecuteCode("document.getElementById('personImg').alt='照片("+imageSize+")'");
			}
		}
		
		//考核年度下拉框初始化
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
		
		//页面数据加载时，默认简历获取焦点，以便在IE8下，简历显示正常
		this.getExecuteSG().addExecuteCode("document.getElementById('a1701').focus();");
		
		//this.getExecuteSG().addExecuteCode("document.getElementById('a7101').focus();");
		
		
		this.getExecuteSG().addExecuteCode("setA0201eDisabled();");		//职务职级
		this.getExecuteSG().addExecuteCode("changedispaly();");			//年度考核
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");		//现职级
		
		
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
			throw new RadowException("已经是第一位人员！");
		}
		String nextA0000 = "update"+(String)map.get(num-1);
		this.request.getSession().setAttribute("nowNumberXX",num-1);//设置num
		this.request.getSession().setAttribute("nowNumber2XX",num-1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		this.getPageElement("a0000").setValue(nextA0000);//页面人员内码
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
			throw new RadowException("已经是最后一位人员！");
		}
		String nextA0000 = "update"+(String)map.get(num+1);
		this.request.getSession().setAttribute("nowNumberXX",num+1);//设置num
		this.request.getSession().setAttribute("nowNumber2XX",num+1);
		this.request.getSession().setAttribute("a0000",nextA0000);
		this.getPageElement("a0000").setValue(nextA0000);//页面人员内码
		tabClick(nextA0000);
		doInit();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void addUserInfo(A01 a01) {
		a01.setTbr(SysManagerUtils.getUserId());
		//a01.setTbrjg(SysManagerUtils.getUserOrgid());
		a01.setTbsj(DateUtil.getTimestamp().getTime());
		a01.setA0155(DateUtil.getTimestamp().toString());
		a01.setA0128("健康");
	}
	
	
	/**
	 * 点击tab选项卡的信息集，切换相应的信息集
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("dj.onclick")
	@NoRequiredValidate
	public int dj(String b) throws RadowException {
		
		this.createPageElement("div1",ElementType.NORMAL,false).setDisplay(false);		//人员基本信息
		this.createPageElement("div2",ElementType.NORMAL,false).setDisplay(false);		//职务信息集
		this.createPageElement("div3",ElementType.NORMAL,false).setDisplay(false);		//现职级
		this.createPageElement("div4",ElementType.NORMAL,false).setDisplay(false);		//现职务
		this.createPageElement("div5",ElementType.NORMAL,false).setDisplay(false);		//专业技术职务信息集
		this.createPageElement("div6",ElementType.NORMAL,false).setDisplay(false);		//学历学位信息集
		this.createPageElement("div7",ElementType.NORMAL,false).setDisplay(false);		//奖惩信息集
		this.createPageElement("div8",ElementType.NORMAL,false).setDisplay(false);		//考核信息集
		this.createPageElement("div9",ElementType.NORMAL,false).setDisplay(false);		//家庭成员及社会关系
		this.createPageElement("div10",ElementType.NORMAL,false).setDisplay(false);		//进入管理信息集
		this.createPageElement("div11",ElementType.NORMAL,false).setDisplay(false);		//退出管理信息集
		this.createPageElement("div12",ElementType.NORMAL,false).setDisplay(false);		//拟任免信息集
		this.createPageElement("div13",ElementType.NORMAL,false).setDisplay(false);		//备注信息集
		this.createPageElement("div14",ElementType.NORMAL,false).setDisplay(false);		//备注信息集
		
		//对点击的信息集进行显示
		if(b != null && !b.equals("")){
			if("11".equals(b)){
				HBSession sess=HBUtil.getHBSession();
				String a0000 = this.getPageElement("subWinIdBussessId").getValue();
				String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
				List<Object[]> list=sess.createSQLQuery(sql).list();
				if(list!=null&&list.size()>0){
					
				}else{
					this.setMainMessage("职务信息为空无法退出管理");
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
		
		//String a0000 = this.getRadow_parent_data();//获取页面人员内码
		String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//对人员信息必填项进行信息验证
		//姓名不允许为空
		String a0101 = this.getPageElement("a0101").getValue();
		if(a0101 == null || "".equals(a0101)){
			this.setMainMessage("姓名不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//性别不允许为空
		String a0104 = this.getPageElement("a0104").getValue();
		if(a0104 == null || "".equals(a0104)){
			this.setMainMessage("性别不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//出生年月不允许为空
		String a0107 = this.getPageElement("a0107").getValue();
		if(a0107 == null || "".equals(a0107)){
			this.setMainMessage("出生年月不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//民族不允许为空
		String a0117 = this.getPageElement("a0117").getValue();
		if(a0117 == null || "".equals(a0117)){
			this.setMainMessage("民族不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//籍贯不允许为空
		String a0111 = this.getPageElement("a0111").getValue();
		if(a0111 == null || "".equals(a0111)){
			this.setMainMessage("籍贯不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//出生地不允许为空
		String a0114 = this.getPageElement("a0114").getValue();
		if(a0114 == null || "".equals(a0114)){
			this.setMainMessage("出生地不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//参加工作时间不允许为空
		String a0134 = this.getPageElement("a0134").getValue();
		if(a0134 == null || "".equals(a0134)){
			this.setMainMessage("参加工作时间不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//健康状况不允许为空
		String a0128 = this.getPageElement("a0128").getValue();
		if(a0128 == null || "".equals(a0128)){
			this.setMainMessage("健康状况不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//公民身份号码不允许为空
		String a0184 = this.getPageElement("a0184").getValue();
		if(a0184 == null || "".equals(a0184)){
			this.setMainMessage("公民身份号码不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//----------------------------对长度做验证-----------------
		//专长a0187a
		String a0187a = this.getPageElement("a0187a").getValue();
		if(a0187a != null || "".equals(a0187a)){
			if(a0187a.length() > 60){
				this.setMainMessage("专长不能超过60字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//管理类别不允许为空
		String a0165 = this.getPageElement("a0165").getValue();
		if(a0165 == null || "".equals(a0165)){
			this.setMainMessage("管理类别不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//人员类别不允许为空
		String a0160 = this.getPageElement("a0160").getValue();
		if(a0160 == null || "".equals(a0160)){
			this.setMainMessage("人员类别不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//编制类型不允许为空
		String a0121 = this.getPageElement("a0121").getValue();
		if(a0121 == null || "".equals(a0121)){
			this.setMainMessage("编制类型不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Date date= new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd");
		String now=sdf.format(date);
		if(a0134.length()==6){
			a0134 += "00";
		}
		if(a0134.compareTo(now)>0){
			this.setMainMessage("参加工作时间不能小于当前时间");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		try {
			
			A01 a01=new A01();
			this.copyElementsValueToObj(a01, this);
			
			//人员基本信息保存
			ReturnDO<Boolean> returnDO = this.savePerson(confirm);			
			if(!returnDO.isSuccess()){
				this.setMainMessage(returnDO.getErrorMsg());
				return EventRtnType.FAILD;
			}
			
			//人员基本信息保存成功后，给页面subWinIdBussessId标识赋值a0000主键，标示人员基本信息已经保存
			this.getExecuteSG().addExecuteCode("document.getElementById('subWinIdBussessId').value = '"+(a0000==null?"":a0000)+"';");
			
			
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		//this.getExecuteSG().addExecuteCode("radow.doEvent('getTreeJsonData')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 人员基本信息保存
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
		//String a0000 = this.getRadow_parent_data();//获取页面人员内码
		
		
		//参加工作时间不能小于出生年月
		String a0134 = a01.getA0134();//参加工作时间
		String a0107 = a01.getA0107();//出生年月
		if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a0107.length()==6){
				a0107 += "00";
			}
			if(a0134.compareTo(a0107)<=0){
				returnDO.setErrorMsg("99999", "参加工作时间不能小于等于出生年月!");
				return returnDO;
			}
		}
		
		
		HBSession sess = null;
		A01 a01_old = null;
		JSONObject jsonbject = null;
		try {
			sess = HBUtil.getHBSession();
			
			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
						returnDO.setErrorMsg("99999", "系统中已存在具有相同身份证号码人员,请修改！");
						return returnDO;
			}
			
			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称
			
			if(a0000==null||"".equals(a0000)){
				//职务为空  1现职人员 2非现职
				a01.setA0163("1");
				this.getPageElement("a0163").setValue("1");
				sess.save(a01);	
			}else{
				a01_old = a01;
				Object old = sess.get(A01.class, a0000);
				if(old != null){			//如果是修改则a01_old需要查询数据库
					a01_old = (A01)old;
					a01.setA0221(a01_old.getA0221());		//现职务
					a01.setA0288(a01_old.getA0288());		//现职级时间
					a01.setA0192e(a01_old.getA0192e());		//现职务
					a01.setA0192c(a01_old.getA0192c());		//现职务时间
					
				}
				
				if("4".equals(a01.getStatus())){//如果是临时数据，保存时状态设为现职人员  日志记为新增
					a01.setStatus("1");
					String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
					List<String> list2 = sess.createSQLQuery(sql2).list();
					for(int i=0;i<list2.size();i++){
						CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
					}
					this.getPageElement("status").setValue(a01.getStatus());
					new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(), a01)).start();
				}else{
					a01.setXgr(null);
					a01.setXgsj(null);
					a01_old.setXgr(null);
					a01_old.setXgsj(null);
					new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old, a01)).start();
					
				}
				
				
				PropertyUtils.copyProperties(a01_old, a01);
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = formatJL(a01_old.getA1701(),originaljl);
				jsonbject = objectToJson(a01_old);
				a01_old.setA1701(originaljl.toString());
				this.getPageElement("a1701").setValue(jianli);
				sess.saveOrUpdate(a01_old);
			}
			
			//新增修改保存时父页面设置人员内码参数。更新title
			//this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");
			a0000 = a01.getA0000();
			
			
			sess.flush();			
			//旧值 判断是否修改
			
			String json = jsonbject.toString();
			//this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");   包含了家庭成员信息，暂时注释
			
			this.getExecuteSG().addExecuteCode("A01value="+json);
			//this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			/*this.setMainMessage("任免表信息保存失败！");
			return EventRtnType.FAILD;*/
			returnDO.setErrorMsg("99999", "信息集录入保存失败！");
			return returnDO;
		}
		
		
		return returnDO;
	}
	
	
	
	
	/** *********************************************工作单位及职务(a02)******************************************************************** */
	@PageEvent("saveWorkUnits.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveWorkUnits()throws RadowException, AppException{
		A02 a02 = new A02();
		this.copyElementsValueToObj(a02, this);
		String a0201bb = this.getPageElement("a0201b").getValue();
		String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
		a02.setA0201a(a0201a);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String aa0201b = this.getPageElement("a0201b").getValue();
		if(aa0201b == null || "".equals(aa0201b)){
			this.setMainMessage("任职机构/工作机构 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		/*String a0255 = this.getPageElement("a0255").getValue();
		if(a0255 == null || "".equals(a0255)){
			this.setMainMessage("该职务任职状态/工作状态 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String b0194 = this.getPageElement("b0194Type").getValue();
		if("1".equals(b0194)){
			String a0201d = a02.getA0201d();
			
			/*if(a0201d == null || "".equals(a0201d.trim()) || a0201d.equals("0")){
				this.setMainMessage("任职机构为法人单位时，是否班子成员必须选择！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			//是否班子成员 为'是'时
			if("1".equals(a0201d)){
				String a0201e = this.getPageElement("a0201e_combo").getValue();//a0201e_combo
				if(a0201e == null || "".equals(a0201e.trim())){
					this.setMainMessage("是否班子成员 为'是'时，成员类别必须填写！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		
		if("2".equals(b0194)){
			a02.setA0201d(null);
		}
		
		String a0216a = this.getPageElement("a0216a").getValue();
		if(a0216a == null || "".equals(a0216a)){
			this.setMainMessage("职务名称 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a0216a != null || !"".equals(a0216a)){
			if (a0216a.indexOf(" ") >=0){
				this.setMainMessage("职务名称不能包含空格！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String reg = "[\u4E00-\u9FA5a-zA-Z0-9]+";	//只能包括数字，英文和中文
			
			String a0216a_reg=a0216a.replace(",", "").replace("、", "").replace("，", "");
			if(!a0216a_reg.matches(reg)){
				this.setMainMessage("职务名称输入格式不正确！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		//1是，2否
		String aa0219 = a02.getA0219();
		if(aa0219 != null && "0".equals(aa0219)){
			a02.setA0219("2");
		}
		//免职时间不能早于任职时间
		String a0265 = a02.getA0265();//免职时间
		String a0243 = a02.getA0243();//任职时间
		if(a0265!=null&&!"".equals(a0265)&&a0243!=null&&!"".equals(a0243)){
			if(a0265.length()==6){
				a0265 += "00";
			}
			if(a0243.length()==6){
				a0243 += "00";
			}
			if(a0265.compareTo(a0243)<0){
				this.setMainMessage("免职时间不能早于任职时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//任职文号a0245
		String a0245 = this.getPageElement("a0245").getValue();
		if(a0245 != null || "".equals(a0245)){
			if(a0245.length() > 130){
				this.setMainMessage("任职文号不能超过130字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//免职文号a0267
		String a0267 = this.getPageElement("a0267").getValue();
		if(a0267 != null || "".equals(a0267)){
			if(a0267.length() > 12){
				this.setMainMessage("免职文号不能超过12字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//职务名称a0216a
		if(a0216a.length() > 50){
			this.setMainMessage("职务名称不能超过50字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a02.setA0000(a0000);
		String a0200 = this.getPageElement("a0200").getValue();
		Boolean updateJGZW = false;
		HBSession sess = null;
		try {
			
			String a0201b = a02.getA0201b();//任职机构编码。			
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//根据任职结构编码获取相同机构下职务的排序值。
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
				}else{		//该机构下第一次有任职人员，初始化a0225
					a02.setA0225(1L);
				}
				
				//如果是添加某人的，同机构下第N（N>1）条职务信息，则a0225需要和这个机构下其他职务的a0225一样(即某人同机构下所有的职务a0225一样)
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
			
			if(a0201bb!=null&&!"".equals(a0201bb)){//获取机构简称
				if("-1".equals(a0201bb)){//其它单位
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
				a02.setA0281("true");//是否输出
				this.getPageElement("a0281").setValue("true");
				
				applog.createLog("3021", "A02", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A02(), a02));
				sess.save(a02);	
				updateJGZW = true;
			}else{
				if(a02.getA0281()==null||"".equals(a02.getA0281())){
					a02.setA0281("true");//是否输出
					this.getPageElement("a0281").setValue("true");
				}
				
				A02 a02_old = (A02)sess.get(A02.class, a0200);
				String jg_old = a02_old.getA0201a();//机构
				String jg =  a02.getA0201a();//机构
				if(jg_old!=null&&!jg_old.equals(jg)){
					updateJGZW = true;
				}else if(jg_old==null&&jg!=null){
					updateJGZW = true;
				}
				String zw_old = a02_old.getA0216a();//职务
				String zw =  a02.getA0216a();//职务
				
				if(zw_old!=null&&!zw_old.equals(zw)){
					updateJGZW = true;
				}else if(zw_old==null&&zw!=null){
					updateJGZW = true;
				}
				String rzzt_old = a02_old.getA0255();//任职状态
				String rzzt = a02.getA0255();//任职状态
				if(rzzt_old!=null&&!rzzt_old.equals(rzzt)){
					updateJGZW = true;
				}else if(rzzt_old==null&&rzzt!=null){
					updateJGZW = true;
				}
				applog.createLog("3022", "A02", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a02_old, a02));
				String b0111 = a02_old.getA0201b();
				String b0111s = a02.getA0201b();
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111s);
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
				PropertyUtils.copyProperties(a02_old, a02);
				
				sess.update(a02_old);	
			}
			//更新名称
			String a0192a = this.getPageElement("a0192a").getValue();
			String a0192 = this.getPageElement("a0192").getValue();
			String a0197 = this.getPageElement("a0197").getValue();
			String a0195 = this.getPageElement("a0195").getValue();
			a01.setA0195(a0195);
			a01.setA0192a(a0192a);
			a01.setA0192(a0192);
			a01.setA0197(a0197);
			//人员基本信息界面
			//暂时注释
			/*this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");*/
			updateA01(a01,sess);
			//计算出当前最高职务层次，为a01的a0148进行更新
			sess.update(a01);
			sess.flush();
			
			this.getPageElement("a0200").setValue(a02.getA0200());//保存成功将id返回到页面上。
			//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//刷新工作单位及职务列表
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"radow.doEvent('genResume');");
			
			//修改父页面的统计关系所在单位
			/*String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}*/
			
			if(updateJGZW){
				this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否用本职务信息重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
						"if(id=='ok'){" +
							"radow.doEvent('UpdateTitleBtn.onclick');	" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				this.setMainMessage("保存成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 生成简历
	 */
	@PageEvent("genResume")
	@NoRequiredValidate
	public int genResume() throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null){
			a0000 = (String)this.request.getSession().getAttribute("a0000");
		}
		//自动生成简历
		try {
			HBSession sess = HBUtil.getHBSession();
			String sqlA02 = "from A02 where a0000='"+a0000+"'";
			List<A02> listA02 = sess.createQuery(sqlA02).list();
			Collections.sort(listA02, new Comparator<A02>(){
				@Override
				public int compare(A02 o1, A02 o2) {
					String o1sj = o1.getA0243()==null?"":o1.getA0243();//任职时间
					String o2sj = o2.getA0243()==null?"":o2.getA0243();//任职时间
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
					String a0201a = a02.getA0201a()==null?"":a02.getA0201a();//任职机构
					String a03015 = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
					String a0203 = a02.getA0243()==null?"":a02.getA0243();//任职时间
					String a0255 = a02.getA0255()==null?"":a02.getA0255();//任职状态
					String a0265 = a02.getA0265()==null?"":a02.getA0265();//免职时间
	
					B01 b01 = null;
					if(a02.getA0201b()!=null){
						b01 = (B01)sess.get(B01.class, a02.getA0201b());
					}
					
					if(b01 != null){//机构拼接规则 与 工作单位与职务全称 机构 拼接一致
						String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
						if("2".equals(b0194)){//2—内设机构
							while(true){
								b01 = (B01)sess.get(B01.class, b01.getB0121());
								if(b01==null){
									break;
								}else{
									b0194 = b01.getB0194();
									if("2".equals(b0194)){//2—内设机构
										a0201a = b01.getB0101()+a0201a;
									}else if("3".equals(b0194)){//3—机构分组
										continue;
									}else if("1".equals(b0194)){//1—法人单位
										a0201a = b01.getB0101()+a0201a;
										break;
									}else{
										break;
									}
								}
							}
						}
					}
					
					
					
					String a0203Next = null;//a02Next.getA0243()==null?"":a02Next.getA0243();//任职时间
					if("1".equals(a0255)){//在任
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
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@NoRequiredValidate
	private void updateA01(A01 a01, HBSession sess){
		//更新a01 职务层次。 a0148 a0149     a02 a0221
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
			//人员基本信息界面
			//暂时注释
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='"+(a01.getA0148()==null?"":a01.getA0148())+"';");
		}else{
			//职务为空
			a01.setA0148("");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='';");
		}
		
	}
	
	
	
	/**
	 * 工作单位及职务列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 工作单位及职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsAddBtn.onclick")
	@NoRequiredValidate
	public int workUnitsWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
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
	 * 工作单位及职务修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//获取选中行index
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
			this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
			
			//任职状态,特殊处理
			if(a02.getA0255() != null && a02.getA0255().equals("0")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=false;document.getElementById('a02550').checked=true;a0255SelChange();");
			}
			if(a02.getA0255() != null && a02.getA0255().equals("1")){
				this.getExecuteSG().addExecuteCode("document.getElementById('a02551').checked=true;document.getElementById('a02550').checked=false;a0255SelChange();");
			}
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出复选框选中事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("workUnitsgridchecked")
	@Transaction
	@NoRequiredValidate
	public int workUnitsgridchecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0200 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		try{
			if(a0200!=null){
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				Boolean checked = Boolean.valueOf(a02.getA0281());
				a02.setA0281(String.valueOf(!checked));
				sess.save(a02);
				PMPropertyCopyUtil.copyObjValueToElement(a02, this);
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
				//this.getExecuteSG().addExecuteCode("a0222SelChange();");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//刷新列表
		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 更新名称
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
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
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
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
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
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
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwmc);
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "、" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//人员基本信息界面
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
			//人员基本信息界面
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
	 * 更新名称对应的时间
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
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
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
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
				
				String zwrzshj = "";//职务任职时间
				if(a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8){
					zwrzshj = a02.getA0243().substring(0,4) + "." + a02.getA0243().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "、" + zwrzshj);
										
									}
									
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							//desc_full.put(key_full, zwmc+zwrzshj);//key 编码_$_机构名称_$_是否已免
							desc_full.put(key_full, zwrzshj);//key 编码_$_机构名称_$_是否已免
						}else{
							//desc_full.put(key_full, value_full + "、" + zwmc+zwrzshj);
							desc_full.put(key_full, value_full + "、" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
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
			applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A02(), new A02()));
			sess.delete(a02);
			sess.flush();
			//更新人员状态
			updateA01(a01, sess);
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
			a02 = new A02();
			this.getPageElement("a0201b_combo").setValue("");
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
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
				String a0255 = m.get("a0255");//任职 状态
				/*if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}*/
				HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				
			}
			
			
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
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
		String sql = "From A02 where a0000='"+a0000+"' order by a0255 desc, a0243 desc ";//任职状态  任职时间降序
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			List<A02> list = sess.createQuery(sql).list();
			int i = 0,j = 0;
			if(list!=null&&list.size()>0){
				for(A02 a02 : list){
					String a0200 = a02.getA0200();//a02 id
					String a0255 = a02.getA0255();//任职 状态
					if("1".equals(a0255)){//在职
						HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
					}else{
						HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
					}
				}
			}
			for(A02 a02 : list){
				String a0200 = a02.getA0200();//a02 id
				String a0255 = a02.getA0255();//任职 状态
				if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
			}
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
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
		if(B0194 != null && B0194.equals("2")){								//任职机构如果选择了内设机构，则任职状态和职务名称不可选
			
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
	
	
	//检查统计关系所在单位是否为“内设机构”
	@PageEvent("a0195Change")
	@NoRequiredValidate
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		Object B0194 = sess.createSQLQuery(sql).uniqueResult();
		if(B0194 != null && B0194.equals("2")){
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','不可选择内设机构单位！');document.getElementById('a0195').value='';"
					+ "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_combo').value='';"
					);
		}else{
			//修改父页面的统计关系所在单位
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				//暂时注释
				//this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/***********************************************专业技术职务(a06)*********************************************************************/
	/**
	 * 保存修改
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a06.setA0000(a0000);
		String a0600 = this.getPageElement("a0600").getValue();
		HBSession sess = null;
		
		//专业技术资格不允许为空
		String a0601 = this.getPageElement("a0601").getValue();
		if(a0601 == null || "".equals(a0601)){
			this.setMainMessage("专业技术资格不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//资格获得时间早于出生年月
			String a0107 = a01.getA0107();//出生年月
			String a0604 = a06.getA0604();//资格获得时间
			if(a0604!=null&&!"".equals(a0604)&&a0107!=null&&!"".equals(a0107)){
				if(a0604.length()==6){
					a0604 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0604.compareTo(a0107)<0){
					this.setMainMessage("资格获得时间不能小于出生年月");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
			if(a0600==null||"".equals(a0600)){
				applog.createLog("3061", "A06", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A06(), a06));
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
				applog.createLog("3062", "A06", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a06_old, a06));
				PropertyUtils.copyProperties(a06_old, a06);
				sess.update(a06_old);	
			}
			sess.flush();
			updateA01(a0000);
			/*this.setMainMessage("保存成功,点击【确定】关闭当前窗口!");
			this.setMessageType(EventMessageType.CONFIRM);
			addNextBackFunc(NextEventValue.YES, closeCueWindowEX());*/
			this.setMainMessage("保存成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);//保存成功将id返回到页面上。
		getPageElement("a0607").setValue(a06.getA0607());
		this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.parent.Ext.WindowMgr.getActive().close();";
	}
	private void updateA01(String a0000 ) throws AppException{
		//当前页面赋值
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"，");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('a0196').setValue('"+a0196s+"')");
		//人员基本信息字段更新
		//将a0196回写到父页面，暂时注释
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0196').value='"+a0196s.toString()+"';window.parent.a0196onblur();");
		
		//更新A10 a0196 专业技术职务字段。   a06 a0602
		HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
	}
	/**
	 * 专业技术职务列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 专业技术职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A06 a06 = new A06();
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		this.getPageElement("a0196").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改专业技术职务的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("professSkillgrid").getCueRowIndex();
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
			getPageElement("a0607").setValue(a06.getA0607());
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
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
			applog.createLog("3063", "A06", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A06(), new A06()));
			sess.delete(a06);
			this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
			sess.flush();
			updateA01(a06.getA0000());
			a06 = new A06();
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 输出复选框选中事件
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
			this.setMainMessage("更新失败！");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");//刷新列表
		this.getExecuteSG().addExecuteCode("Ext.getCmp('professSkillgrid').getStore().reload()");//刷新列表
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
			
			this.setNextEventName("professSkillgrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/***********************************************学位学历(a08)*********************************************************************/
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//教育类别不允许为空
		String a0837 = this.getPageElement("a0837").getValue();
		if(a0837 == null || "".equals(a0837)){
			this.setMainMessage("教育类别不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//学历学位不能同时为空
		String a0801b = a08.getA0801b();//学历
		String a0901b = a08.getA0901b();//学位
		if((a0801b==null||"".equals(a0801b))&&(a0901b==null||"".equals(a0901b))){
			this.setMainMessage("学历学位不能同时为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//毕业时间不能早于早于入学时间
		String a0807 = a08.getA0807();//毕业时间
		String a0804 = a08.getA0804();//入学时间
		String a0904 = a08.getA0904();//学位授予时间
		if(a0807!=null&&!"".equals(a0807)&&a0804!=null&&!"".equals(a0804)){
			if(a0807.length()==6){
				a0807 += "00";
			}
			if(a0804.length()==6){
				a0804 += "00";
			}
			if(a0807.compareTo(a0804)<0){
				this.setMainMessage("毕业时间不能早于入学时间");
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
				this.setMainMessage("学位授予时间不能早于入学时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		a08.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//入学时间、毕业时间、学位授予时间可以早于出生年月并保存。
			String a0107 = a01.getA0107();//出生年月
			if(a0107!=null&&!"".equals(a0107)){
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0807!=null&&!"".equals(a0807)){
					if(a0807.length()==6){
						a0807 += "00";
					}
					if(a0807.compareTo(a0107)<0){
						this.setMainMessage("毕业时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0804!=null&&!"".equals(a0804)){
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0804.compareTo(a0107)<0){
						this.setMainMessage("入学时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0904!=null&&!"".equals(a0904)){
					if(a0904.length()==6){
						a0904 += "00";
					}
					if(a0904.compareTo(a0107)<0){
						this.setMainMessage("学位授予时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			
			
			
			if(a0800==null||"".equals(a0800)){
				a08.setA0899("false");//是否输出
				
				
				applog.createLog("3081", "A08", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A08(), a08));
				
				sess.save(a08);	
			}else{
				if(a08.getA0899()==null||"".equals(a08.getA0899())){
					a08.setA0899("false");//是否输出
				}
				
				A08 a08_old = (A08)sess.get(A08.class, a0800);
				applog.createLog("3082", "A08", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a08_old, a08));
				PropertyUtils.copyProperties(a08_old, a08);
				
				sess.update(a08_old);
				//如果修改了重新计算最高学历和输出信息
				/*updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
				updateZGQRZXueliXuewei(a0000,sess);
				updateXueliXuewei(a0000,sess,"1");
				updateXueliXuewei(a0000,sess,"2");*/
				
			}	
			sess.flush();	
			printout(a0000,sess,"1");// 设置最高学历学位输出 
			printout(a0000,sess,"2");
			updateZGXueliXuewei(a0000,sess);//最高学历学位标志
			updateZGQRZXueliXuewei(a0000,sess);
			updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
			updateXueliXuewei(a0000,sess,"1");
			updateXueliXuewei(a0000,sess,"2");
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0800").setValue(a08.getA0800());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('degreesgrid').getStore().reload()");//刷新学历学位列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	


	private void updateZGXueliXuewei(String a0000, HBSession sess) {
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true'";//只显示 输出的最高学历 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
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
		
		//如果只有一条学历信息,如果学历代码不为空就是最高学历
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
				
				//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也为最高学历
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
						//如果只有一条学位信息,如果学位代码不为空就是最高学位
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
						//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='2'";// 输出的最高在职学历 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
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
		//如果只有一条学历信息,如果学历代码不为空就是最高学历
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
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也为最高学历
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
		//如果只有一条学位信息,如果学位代码不为空就是最高学位
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
		//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true' and a0837='1'";//输出的最高全日制学历 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
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
		//如果只有一条学历信息,如果学历代码不为空就是最高学历
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
		
		//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也为最高学历
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
				//如果只有一条学位信息,如果学位代码不为空就是最高学位
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
				//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
		//全日制学位学历
		A08 xueli = null;
		A08 xuewei = null;  

		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //以学历编码升序排列
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
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
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
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
		
		String sql2 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true' order by to_number(a0901b) asc";   //以学位编码升序排列
		List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
		if(list2!=null && list2.size()>0){
			xuewei = list2.get(0);
		}
			
		//更新a01
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if(xueli!=null){
			checkXL(xueli,sess,a0837,a0000);
		}else{
			if("1".equals(a0837)){//全日制
				a01.setQrzxl(a01.getQrzxl());//学历
				a01.setQrzxlxx(a01.getQrzxlxx());
				a01.setQrzxw(a01.getQrzxw());//学位
				a01.setQrzxwxx(a01.getQrzxwxx());
				//人员基本信息界面
				
				//如果是删除或者设置不输出，那么A01表中的信息清空
				if(!isnull){
					a01.setQrzxl(null);//学历
					a01.setQrzxlxx(null);
					a01.setQrzxw(null);//学位
					a01.setQrzxwxx(null);
				}
				
				
			}else{//在职
				a01.setZzxl(a01.getZzxl());
				a01.setZzxlxx(a01.getZzxlxx());
				a01.setZzxw(a01.getZzxw());
				a01.setZzxwxx(a01.getZzxwxx());
				//人员基本信息界面
				
				//如果是删除输出的学历学位或者设置不输出，那么A01表中的信息清空
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
	private void checkXL(HBSession sess,String a0837,String a0000) {//a0837教育类别
		String xl="";
		String xlxx="";
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //以学历编码升序排列
				if("1".equals(a0837)){
					sql1+=" and a0831='1'";
				}
				if("2".equals(a0837)){
					sql1+=" and a0838='1'";
				}
         List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
				if(list1!=null&&list1.size()>0){
					A08 xueli = list1.get(0);
					String yx_xl = xueli.getA0814();//院系
					String zy_xl = xueli.getA0824();//专业
					if(yx_xl==null){
						yx_xl = "";
					}
					if(zy_xl==null){
						zy_xl = "";
					}
					if(!"".equals(zy_xl)){
						zy_xl += "专业";
					}
					 xl = xueli.getA0801a();//学历 中文
					 xlxx = yx_xl+zy_xl;
					if(xl==null||"".equals(xl)){
						xlxx = null;
					}
				}
		//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		
		/*String a0901a_xl = xueli.getA0901b();
		if(a0901a_xl != null && !"".equals(a0901a_xl)){//最高学历 存在 学位，则此学位一定为最高学位
			checkXW(true,xueli,sess,a0837,a0000);
		}else{//最高学历不 存在 学位
			checkXW(false,xueli,sess,a0837,a0000);
		}*/
	
		if("1".equals(a0837)){//全日制
			a01.setQrzxl(xl);//学历
			a01.setQrzxlxx(xlxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
		}else{//在职
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}

	private void checkXW(HBSession sess,String a0837,String a0000){
		String xw = "";//学位中文
		String xwxx = "";//学位的院系专业
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
				zy_xw += "专业";
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
					zy_xw2 += "专业";
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
		if("1".equals(a0837)){//全日制
			a01.setQrzxw(xw);//学位
			a01.setQrzxwxx(xwxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
			this.getExecuteSG().addExecuteCode("document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
		}else{//在职
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
			//人员基本信息界面
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
	 * 学位学历列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 学位学历新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesAddBtn.onclick")
	@NoRequiredValidate
	public int opendegreesWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		
		String a0800 = this.getPageElement("a0800").getValue();
		String a0837 = this.getPageElement("a0837").getValue();//教育类别
		String a0801b = this.getPageElement("a0801b").getValue();//学历
		String a0901b = this.getPageElement("a0901b").getValue();//学位
		if(a0800==null||"".equals(a0800)){//新增提示是否保存当前信息。
			if(a0837!=null&&!"".equals(a0837)){
				if((a0801b!=null&&!"".equals(a0801b))||(a0901b!=null&&!"".equals(a0901b))){
					this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','是否保存当前新增信息?',200,function(id){" +
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		clearCondition();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空 条件列表
	 * @throws RadowException 
	 */
	@PageEvent("clearCondition")
	public void clearCondition() throws RadowException{
		A08 a08 = new A08();
		PMPropertyCopyUtil.copyObjValueToElement(a08, this);
	}
	
	/**
	 * 修改学位学历修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int degreesgridOnRowClick() throws RadowException{ 
		//this.openWindow("DegreesAddPage", "pages.publicServantManage.DegreesAddPage");
		//获取选中行index
		int index = this.getPageElement("degreesgrid").getCueRowIndex();
		String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出设置	
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgridchecked")
	@Transaction
	@NoRequiredValidate
	public int degreesgridChecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0800 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
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
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				updateZGXueliXuewei(a0000,sess);//最高学历学位标志
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a0000,sess);//最高全日制学历学位标志

				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a0000,sess);//最高在职学历学位标志
				}
				updateXueliXuewei(a0000, sess, a0837);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");//刷新列表
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
			applog.createLog("3083", "A08", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A08(), new A08()));
			sess.delete(a08);
			sess.flush();
			Boolean checked = Boolean.valueOf(a08.getA0899());				
			//checked 如果为true 数据是被输出的。  修改A01 否则不做更改
			if(checked){
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				updateZGXueliXuewei(a08.getA0000(),sess);//最高学历学位标志
				String a0837=a08.getA0837();
				if(a0837!=null&&"1".equals(a0837)){
					updateZGQRZXueliXuewei(a08.getA0000(),sess);//最高全日制学历学位标志	
				}
				if(a0837!=null&&"2".equals(a0837)){
					updateZGZZXueliXuewei(a08.getA0000(),sess);//最高在职学历学位标志
				}
				updateXueliXuewei(a08.getA0000(), sess, a0837);
			}
			this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
			a08 = new A08();
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//判断最高的学历学位并输出
		public int printout(String a0000,HBSession sess,String a0837) throws RadowException{
			String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"'";// 输出的最高在职学历 
			List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
			if(list1!=null&&list1.size()>0){
				Collections.sort(list1,new Comparator<A08>(){//学历排序
					@Override
					public int compare(A08 o1, A08 o2) {
						String a0801b_1 = o1.getA0801b();//学历代码
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
			//如果只有一条学历信息,如果学历代码不为空就是最高学历
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
			//如果有多条记录,第一条记录学历代码不为空就是最高学历,剩余学历代码如果与第一条一样也为最高学历
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
			//如果只有一条学位信息,如果学位代码不为空就是最高学位
			if(list2!=null&& list2.size()==1){
				A08 a08=list2.get(0);
				String xueweidaima=a08.getA0901b();
				if(!StringUtil.isEmpty(xueweidaima)){
					 a08.setA0899("true");
				}
				sess.update(a08);
			}
			//如果有多条记录,第一条记录学位代码不为空就是最高学位,剩余学位代码如果与第一条对比后规则一样也为最高学位
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
	
	
	/***********************************************奖惩情况(a14)*********************************************************************/
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//奖惩名称代码不允许为空
		String a1404b = this.getPageElement("a1404b").getValue();
		if(a1404b == null || "".equals(a1404b)){
			this.setMainMessage("奖惩名称代码不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a1404a = this.getPageElement("a1404a").getValue();
		if(a1404a == null || "".equals(a1404a)){
			this.setMainMessage("奖惩名称不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a1411a = this.getPageElement("a1411a").getValue();
		if(a1411a == null || "".equals(a1411a)){
			this.setMainMessage("批准机关不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a1411a.length() > 30){
			this.setMainMessage("批准机关不能超过30字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		String a1407 = a14.getA1407();//批准日期
		//奖惩批准日期：与参加工作时间比较，应晚于参加工作时间。
		String a0134 = a01.getA0134();//参加工作时间
		if(a0134!=null&&!"".equals(a0134)&&a1407!=null&&!"".equals(a1407)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a1407.length()==6){
				a1407 += "00";
			}
			if(a1407.compareTo(a0134)<0){
				this.setMainMessage("批准日期不能早于参加工作时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//撤销日期不能早于批准日期
		String a1424 = a14.getA1424();//撤销日期
		if(a1424!=null&&!"".equals(a1424)&&a1407!=null&&!"".equals(a1407)){
			if(a1424.length()==6){
				a1424 += "00";
			}
			if(a1407.length()==6){
				a1407 += "00";
			}
			if(a1424.compareTo(a1407)<0){
				this.setMainMessage("撤销日期不能早于批准日期");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		//奖惩名称a1404a
		if(a1404a.length() > 20){
			this.setMainMessage("奖惩名称不能超过20字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a14.setA0000(a0000);
		HBSession sess = HBUtil.getHBSession();;
		try {
			if(a1400==null||"".equals(a1400)){
				applog.createLog("3141", "A14", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A14(), a14));
				sess.save(a14);	
			}else{
				A14 a14_old = (A14)sess.get(A14.class, a1400);
				applog.createLog("3142", "A14", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a14_old, a14));
				PropertyUtils.copyProperties(a14_old, a14);
				sess.update(a14_old);	
			}
			//更新奖惩综述
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a14z101 = "无";
				this.getPageElement("a14z101").setValue(a14z101);
			}
			a01.setA14z101(a14z101);
			//人员基本信息界面
			//回写奖惩综述，暂时注释
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			sess.update(a01);
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a14.getA1400());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('RewardPunishGrid').getStore().reload()");//刷新奖惩情况列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * 奖惩情况列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 奖惩情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishAddBtn.onclick")
	@NoRequiredValidate
	public int rewardPunishWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A14 a14 = new A14();
		PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 奖惩情况的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int rewardPunishGridOnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("RewardPunishGrid").getCueRowIndex();
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 奖惩情况描述追加
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("appendonclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int appendonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		Map map = this.getRequestParamer();
		int index = Integer.valueOf(String.valueOf(map.get("eventParameter")));//当前行号
		String a14z101 = this.getPageElement("a14z101").getValue();//奖惩描述
		//奖惩名称代码
		String a1404b = this.getPageElement("RewardPunishGrid").getValue("a1404b",index).toString();
		//奖惩名称
		String a1404a = this.getPageElement("RewardPunishGrid").getValue("a1404a",index).toString();
		//批准机关
		String a1411a = this.getPageElement("RewardPunishGrid").getValue("a1411a",index).toString();
		//批准时间
		String a1407 = this.getPageElement("RewardPunishGrid").getValue("a1407",index).toString();
		
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
		}else{
			a1407 = "";
		}
		
		boolean haschr = false;
		if("无".equals(a14z101)){
			a14z101 = "";
		}
		if(a14z101.length()>0){
			String laststr = a14z101.substring(a14z101.length()-1);
			if(laststr.matches(",|.|;|，|。|；")){
				a14z101 = a14z101.substring(0,a14z101.length()-1);				
			}
			haschr = true;
		}
		
		StringBuffer desc = new StringBuffer(a14z101);
		if(haschr){
			desc.append("；");
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		
		if(a1404b.startsWith("01") || a1404b.startsWith("1")){//奖
			
			if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
				desc.append("经"+a1411a+"批准，").append("荣获"+a1404a+"。");
			}else{
				desc.append("经"+a1411a+"批准，").append(a1404a+"。");
			}
		}else{//惩
			desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
		}
		this.getPageElement("a14z101").setValue(desc.toString());
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(desc.toString());
		sess.update(a01);
		//人员基本信息界面
		//奖惩情况描述追加，回写奖惩综述，暂时注释
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 奖惩情况描述全部替换
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addAll.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int addAllonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		List<HashMap<String, Object>> list = this.getPageElement("RewardPunishGrid").getValueList();
		if(list!=null&&list.size()>0){
			StringBuffer desc = new StringBuffer("");
			for(HashMap<String, Object> map : list){
				//奖惩名称代码
				String a1404b = map.get("a1404b").toString();
				//奖惩名称
				String a1404a = map.get("a1404a").toString();
				//批准机关
				String a1411a = map.get("a1411a").toString();
				
				//批准时间
				String a1407 = map.get("a1407").toString();
				
				if(a1407!=null&&a1407.length()>=6){
					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
				}else{
					a1407 = "";
				}
				if(desc.toString().endsWith("。")){
					desc.deleteCharAt(desc.length()-1).append("；");
				}
				if(!"".equals(a1407)){
					desc.append(a1407);
				}
				if(a1404b.startsWith("01")){//奖
					desc.append("经"+a1411a+"批准，").append(a1404a+"。");
				}else{//惩
					
					desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
				}
			}
			
			this.getPageElement("a14z101").setValue(desc.toString());
			a01.setA14z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			//奖惩情况描述全部替换，回写奖惩综述，暂时注释
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		}else{
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a01.setA14z101("无");
				this.getPageElement("a14z101").setValue(a01.getA14z101());
				sess.update(a01);
				//奖惩情况描述全部替换，回写奖惩综述，暂时注释
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
			applog.createLog("3143", "A14", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A14(), new A14()));
			sess.delete(a14);
			this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
			a14 = new A14();
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);	
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	private void change_visiable(A14 a14) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01= (A01)session.get(A01.class, a0000);
		String description = this.getPageElement("a14z101").getValue();//页面文字描述
		StringBuffer desc = new StringBuffer("");
		String a1407 = a14.getA1407();
		String a1404b = a14.getA1404b();//奖惩名称代码	
		String a1404a = a14.getA1404a();//奖惩名称
		String a1411a = a14.getA1411a();//批准机关
		if(a1411a==null){
			a1411a="";
		}
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
		}else{
			a1407 = "";
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		if(a1404b.startsWith("01")){//奖
			desc.append("经"+a1411a+"批准，").append(a1404a+"。");
		}else{//惩6
			desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
		}
		System.out.println(description);
		System.out.println(desc);
		description = description.replaceAll(desc.toString().trim(), "");
		if(desc.toString().endsWith("。")){
			desc.deleteCharAt(desc.length()-1).append("；");
		}
		description= description.replaceAll(desc.toString(), "");
		if("".equals(description.trim())){
			description = "无";
		}
		//奖惩界面内容
		this.getPageElement("a14z101").setValue(description);
		
		//修改a01表
		a01.setA14z101(description);
		session.update(a01);
		//人员基本信息界面
		//回写奖惩综述，暂时注释
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		
	}
	
	
	
	/***********************************************年度考核情况A15*********************************************************************/
	@PageEvent("saveA15.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
		String a1521s = this.getPageElement("a1521").getValue();
		String a1517 = this.getPageElement("a1517").getValue();
		
		//考核年度不允许为空
		if(a1521s == null || "".equals(a1521s)){
			this.setMainMessage("考核年度不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//考核结论类别不允许为空
		if(a1517 == null || "".equals(a1517)){
			this.setMainMessage("考核结论类别不能为空！");
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
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a15.setA0000(a0000);
			String a1500 = this.getPageElement("a1500").getValue();
			HBSession sess = null;
			String isconnect = "";
			try {
				sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				//检查该年度是否已录入
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a15.getA0000()+"' and a1521='"+a15.getA1521()+"'");			
				if(a1500!=null&&!"".equals(a1500)){
					sql.append(" and a1500!='"+a1500+"'");
				}
				List list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					this.setMainMessage("该年填写重复!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a15.getA1521()!=null&&!"".equals(a15.getA1521())&&a15.getA1517()!=null&&!"".equals(a15.getA1517())){
					// 新增或修改
					if (a1500 == null || "".equals(a1500)) {
						
						applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15));
						
						sess.save(a15);
					} else {
						A15 a15_old = (A15)sess.get(A15.class, a1500);
						applog.createLog("3152", "A15", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a15_old, a15));
						PropertyUtils.copyProperties(a15_old, a15);
						
						sess.update(a15_old);
					}	
				}
				sess.flush();
				//更新a01年度考核情况
				String a15z101 = this.getPageElement("a15z101").getValue();
				a01.setA15z101(a15z101);
				
				//是否与列表关联
				isconnect = this.getPageElement("a0191").getValue();
				a01.setA0191(isconnect);
				if("1".equals(isconnect)){
					listAssociation();
				}
				//人员基本信息界面
				//回写年度考核情况，暂时注释
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
				sess.update(a01);
				sess.flush();			
				this.setMainMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("保存失败！");
				return EventRtnType.FAILD;
			}
			
			this.getPageElement("a1500").setValue(a15.getA1500());//保存成功将id返回到页面上。
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	/**
	 * 批量保存
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1517 = this.getPageElement("a1517").getValue();//考核结果
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//检查该年度是否已录入
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			//已录年度
			StringBuffer years = new StringBuffer("");
			if(list!=null&&list.size()>0){
				for(A15 a15y : list){
					years.append(a15y.getA1521()+",");
				}
			}
			String [] num = a1521s.split(",");
			//循环批量新增考核信息
			for(int i=0;i<num.length;i++){
				if(years.indexOf(num[i])!=-1||a1517==null||"".equals(a1517)){
					continue;
				}			
				A15 a15save = new A15();
				a15save.setA1521(num[i]);
				a15save.setA1517(a1517);
				a15save.setA0000(a0000);
				
				applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15save));
				
				sess.save(a15save);
			}
			sess.flush();
			//更新a01年度考核情况
			String a15z101 = this.getPageElement("a15z101").getValue();
			a01.setA15z101(a15z101);
			
			//是否与列表关联
			String isconnect = this.getPageElement("a0191").getValue();
			a01.setA0191(isconnect);
			if("1".equals(isconnect)){
				listAssociation();
			}
			//人员基本信息界面
			//回写年度考核情况，暂时注释
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
			sess.update(a01);
			sess.flush();			
			
			sess.flush();			
			//this.setMainMessage("增加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("增加失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1500").setValue("");//清空页面id。
		this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");//刷新列表
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 年度考核情况列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 年度考核情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 年度考核情况的修改事件
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
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 考核情况描述
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("listAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int listAssociation()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1527 = this.getPageElement("a1527").getValue();//选择年度个数
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
				//考核年度
				String a1521 = a15.getA1521();
				//考核结果
				String a1517 = a15.getA1517();
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				desc.append(a1521+"年年度考核"+a1517Name+"；");
			}
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "。");
			}
			this.getPageElement("a15z101").setValue(desc.toString());
			
			a01.setA15z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			//回写年度考核情况，暂时注释
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}else{
			String description = "无";
			this.getPageElement("a15z101").setValue(description);
			a01.setA15z101(description);
			sess.update(a01);
			//回写年度考核情况，暂时注释
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
			
			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			sess.delete(a15);
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			a15 = new A15();
			a15.setA1527(a1527);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
			listAssociation();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//处理简历
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
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
	
	//处理简历
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//32个字一行。
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 64个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//32个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32个字一行但不足64个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行65个字节，往前退一格。
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
     * 将实体POJO转化为JSON
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
     * 将实体POJO转化为JSON
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
	
    
    /***********************************************家庭成员及社会关系(A36)*********************************************************************/
	@PageEvent("saveA36.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA36()throws RadowException, AppException{
		A36 a36 = new A36();
		this.copyElementsValueToObj(a36, this);
		String a3600 = this.getPageElement("a3600").getValue();					//获取主键
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();		//获得a0000，人员信息主键
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//称谓不能为空
		String a3604a = this.getPageElement("a3604a").getValue();
		if(a3604a == null || "".equals(a3604a)){
			this.setMainMessage("称谓不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//姓名不能为空
		String a3601 = this.getPageElement("a3601").getValue();
		if(a3601 == null || "".equals(a3601)){
			this.setMainMessage("姓名不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a3601.length() > 18){
			this.setMainMessage("姓名不能超过18个字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//出生年月不能为空
		/*String a3607 = this.getPageElement("a3607").getValue();
		if(a3607 == null || "".equals(a3607)){
			this.setMainMessage("出生年月不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		
		//政治面貌不能为空
		String a3627 = this.getPageElement("a3627").getValue();
		if(a3627 == null || "".equals(a3627)){
			this.setMainMessage("政治面貌不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//工作单位及职务不能为空
		String a3611 = this.getPageElement("a3611").getValue();
		if(a3611 == null || "".equals(a3611)){
			this.setMainMessage("工作单位及职务不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a3611.length() > 50){
			this.setMainMessage("姓名不能超过50个字！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		a36.setA0000(a0000);			//set主键
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			if(a3600==null||"".equals(a3600)){
				applog.createLog("3141", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A14(), a36));
				sess.save(a36);	
			}else{
				A36 a36_old = (A36)sess.get(A36.class, a3600);
				applog.createLog("3142", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36));
				PropertyUtils.copyProperties(a36_old, a36);
				sess.update(a36_old);	
			}
			
			sess.update(a01);
			sess.flush();			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a36.getA3600());//保存成功将id返回到页面上。
		this.getExecuteSG().addExecuteCode("radow.doEvent('familyid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * 家庭成员及社会关系列表
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 家庭成员及社会关系新增按钮
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A36 a36 = new A36();
		PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 家庭成员及社会关系的修改事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("familyid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int familyidOnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("familyid").getCueRowIndex();
		String a3600 = this.getPageElement("familyid").getValue("a3600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A36 a36 = (A36)sess.get(A36.class, a3600);
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//删除家庭成员及社会关系
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
			applog.createLog("3143", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36()));
			sess.delete(a36);
			this.getExecuteSG().addExecuteCode("radow.doEvent('familyid.dogridquery')");
			a36 = new A36();
			PMPropertyCopyUtil.copyObjValueToElement(a36, this);	
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
    
    /**
	 * 进入管理信息集A29，保存
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		try {
			//进入管理信息集
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			
			//2、进入本单位变动类别：必须填写。
			String a2911_combo = a29.getA2911();
			if(a2911_combo==null || "".equals(a2911_combo.trim())){
				this.setMainMessage("进入本单位变动类别不可为空！");
				return EventRtnType.FAILD;
			}
			//对在原单位职务层次进行处理
			String a2944s = this.getPageElement("a2944s_combo").getValue();
			if(a2944s !=null && !a2944s.trim().equals("")) {
				if(a2944s.length()>20) {
					this.setMainMessage("‘在原单位职务层次’输入过长，20个汉字内！");
					return EventRtnType.FAILD;
				}
				a29.setA2944(a2944s);
			}
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//判断进入本单位日期：与出生日期进行比较，一般应大于18周岁。
			String a0107 = a01.getA0107();//出生年月
			String a2907 = a29.getA2907();//进入本单位日期
			if(a0107!=null&&!"".equals(a0107)&&a2907!=null&&!"".equals(a2907)){
				int age = getAgeNew(a2907,a0107);
				if(age<18){
					this.setMainMessage("进入本单位日期与出生日期进行比较，应大于18周岁！");
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
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    
	/**
	 * 退出管理信息集A30，保存并下一步
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="select a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
		List<Object> list=sess.createSQLQuery(sql).list();
		
		try {
			//退出管理信息集
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			
			//1、退出本单位变动类别：必须填写。
			//判断退出本单位日期：应晚于参加工作时间。
			String a0134 = a01.getA0134();//参加工作时间
			String a3004 = a30.getA3004();//退出本单位日期
			if(a0134!=null&&!"".equals(a0134)&&a3004!=null&&!"".equals(a3004)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a3004.length()==6){
					a3004 += "00";
				}
				if(a3004.compareTo(a0134)<0){
					this.setMainMessage("退出本单位日期不能早于参加工作时间");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			String orgid=this.getPageElement("orgid").getValue();
			String a3007a=a30.getA3007a();
			String a3007=this.getPageElement("a3007a_combo").getValue();
			String a3001 = a30.getA3001();
			if(a3001!=null&&!"".equals(a3001)){
				if(!a3001.startsWith("1")&&!a3001.startsWith("2")&&StringUtil.isEmpty(orgid)){
					 this.setMainMessage("退出单位不能为空");
					 return EventRtnType.FAILD; 
				}
				//调出人员     历史库
				if(a3001.startsWith("1")||a3001.startsWith("2")){
					if(null == a30.getA3007a() || "".equals(a30.getA3007a())){
						this.setMainMessage("调往单位不能为空");
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
					
				}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}
			}else{
				//不覆盖【离退】的状态
			}
			a30.setA0000(a0000);
			
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
				applog.createLog("3301", "A30", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a30_old,a30));
				
			}else{
				applog.createLog("3302", "A30", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a30_old,a30));
			}
			PropertyUtils.copyProperties(a30_old, a30);
			
			sess.saveOrUpdate(a30_old);
			
		
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
    
    
	/**
	 * 拟任免信息集A53，保存
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			
			
			//拟任免保存	id生成方式为uuid 。 如果是新增 将id设置为null
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
				applog.createLog("3531", "A53", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a53_old,a53));
			}else{
				a53_old = (A53)sess.get(A53.class, a53.getA5300());
				applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a53_old,a53));
			}
			PropertyUtils.copyProperties(a53_old, a53);
			
			sess.saveOrUpdate(a53_old);
			this.getPageElement("a5300").setValue(a53_old.getA5300());
			this.getPageElement("a5399").setValue(a53_old.getA5399());
			
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 自定义信息集A71，保存
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//备注a7101
		//String a7101 = this.getPageElement("a7101").getValue();
		String a7101 = this.request.getParameter("a7101");
		if(a7101 != null || "".equals(a7101)){
			if(a7101.length() > 1000){
				this.setMainMessage("备注不能超过1000字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		try {
			//备注信息集
			A71 a71 = new A71();
			this.copyElementsValueToObj(a71, this);
			a71.setA0000(a0000);
			
			//如果是新增备注信息，则生成主键A7100
			if(a71.getA7100() == null || a71.getA7100().equals("")){
				String uuid = UUID.randomUUID().toString().replaceAll("-", "");
				a71.setA7100(uuid);
			}
			
			A71 a71_old = (A71)sess.get(A71.class, a0000);
			if(a71_old==null){
				a71_old = new A71();
				applog.createLog("3301", "A71", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a71_old,a71));
				
			}else{
				applog.createLog("3302", "A71", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a71_old,a71));
			}
			PropertyUtils.copyProperties(a71_old, a71);
			sess.saveOrUpdate(a71_old);	
			sess.flush();
			
			//保存人员自定义信息集
			saveInfo_Extend(sess,a0000);
			
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 新增保存时设置人员内码。
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
		
		String a0000check = (String)this.request.getSession().getAttribute("a0000");//第一次打开人员信息页面
		String a0000 = id;
		
		try {
			HBSession sess = HBUtil.getHBSession();
			
			/*if(a0000==null||"".equals(a0000)||"add".equals(a0000)){
				this.setMainMessage("查询失败！");
				return EventRtnType.FAILD;
			}*/
			A01 a01 = null;
			/*if(a0000.indexOf("addTab-")!=-1){//打开新增页面，检查是否有人员内码，如果有则是新增，否则是修改。
				a0000 = a0000.split("addTab-")[1];
				a01 = (A01)sess.get(A01.class, a0000);
				if(a01==null){
					a01 = new A01();
					a01.setA0000(a0000);
					a01.setA0163("1");//默认现职人员
					a01.setA0104("1");//默认男
					a01.setA14z101("无");//奖惩描述
					a01.setStatus("4");
					//a01.setA0197("0");//基层工作经历时间两年以上
					addUserInfo(a01);
					sess.save(a01);
					sess.flush();
					this.request.getSession().setAttribute("a0000", a0000);
					this.getExecuteSG().addExecuteCode("thisTab.initialConfig.personid='"+a0000+"';");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}*/
			//更改窗口名称updateWin.setTitle(title);
			if(a0000!=null && !a0000.equals("")){
				a01 = (A01)sess.get(A01.class, a0000);
				
				//修改页面，给subWinIdBussessId赋值
				this.getExecuteSG().addExecuteCode("document.getElementById('subWinIdBussessId').value = '"+(a0000==null?"":a0000)+"';");
				
				//姓名 性别 年龄
				String a0101 = a01.getA0101()==null?"":a01.getA0101();//姓名
				//String a0184 = a01.getA0184().toUpperCase();//身份证号//将身份证的最后一位x转换为大写字符 add by lizs 20161110
				String a0107 = a01.getA0107();//出生日期
				String sex = HBUtil.getCodeName("GB2261", a01.getA0104());
				String age = "";
				int agei = 0;
				
				if((agei = IdCardManageUtil.getAgefrombirth(a0107))!=-1){
					age = agei + "";
				}
				String title = a0101 + "，" + sex + "，" + age+"岁";
				this.getExecuteSG().addExecuteCode("window.parent.tabs.getItem(thisTab.initialConfig.tabid).setTitle('"+title.replaceAll("<", "&lt;").replaceAll("'", "&acute;")+"');");
			}
			
		} catch (Exception e) {
			this.setRadow_parent_data("");
			this.request.getSession().setAttribute("a0000", "");
			e.printStackTrace();
			this.setMainMessage("查询失败！");
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
			returnAge = 0; // 同年返回0岁
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // 年只差
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// 日之差
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// 月之差
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// 出生日期错误 晚于今年
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "岁)";
		int msg = returnAge ;
		return msg;
	}

	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	
	/***********************************************现职级A051*********************************************************************/
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (a05.getA0501b() == null || "".equals(a05.getA0501b())) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','职务层次不能为空！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a05.setA0000(a0000);
		String a0500 = a05.getA0500();
		String a0504 = a05.getA0504();// 
		String a0517 = a05.getA0517();//
		String a0524 = a05.getA0524();//获取页面的状态值
		a05.setA0525(a0524);
		String a0501b = a05.getA0501b();//职务
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','批准时间不能大于结束时间！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			A05 a05_old = null;
			if (a0500 == null || "".equals(a0500)) {
				if("1".equals(a0524)){//状态为在任时，进行判断，若之前有在任的则提示
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='1' and a0000='"+a0000+"'").list();//检测是否之前有状态为在任的
					if(list_a05.size()>0){//检测到之前有状态为在任的
						this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','现职级不可重复在任！',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='1' ";//将同一人的其他职级状态设为0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0192e(a0501b); //数据库设置 人物信息表的现职级
						a01.setA0192c(a0504);//现职级时间
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('"+(a0501b==null?"":a0501b)+"')");//页面设置 人物信息表的现职务
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('"+(a0504==null?"":a0504)+"')");//页面设置 人物信息表的现职务时间
					}
				}
				a05_old = new A05();
				applog.createLog("3112", "A05", a01.getA0000(), a01.getA0101(), "新增记录",
						new Map2Temp().getLogInfo(a05_old, a05));
				sess.save(a05);
				sess.flush();
			} else {
				a05_old = (A05) sess.get(A05.class, a0500);
				if("1".equals(a0524)){//状态为在任时，进行判断，若之前有在任的则提示
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='1' and a0000='"+a0000+"' and a0500<> '"+a0500+"'").list();//检测是否之前有状态为在任的
					if(list_a05.size()>0){//检测到其他职级有状态为在任的
						this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','现职级不可重复在任！',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='1' ";//将同一人的其他职级状态设为0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0192e(a0501b); //数据库设置 人物信息表的现职级
						a01.setA0192c(a0504);//现职级时间
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('"+(a0501b==null?"":a0501b)+"')");//页面设置 人物信息表的现职务
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('"+(a0504==null?"":a0504)+"')");//页面设置 人物信息表的现职务时间
					}
				}else if("0".equals(a0524)){//状态为以免时
					if("1".equals(a05_old.getA0524())){//原本是在任
						a01.setA0192e(null);//设置 人物信息表的现职级 为空
						a01.setA0192c(null);//现职级时间
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('')");//页面设置 人物信息表的现职务为空
						//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue(''");//页面设置 人物信息表的现职务时间
					}
				}
				applog.createLog("3113", "A05", a01.getA0000(), a01.getA0101(), "修改记录",
						new Map2Temp().getLogInfo(a05_old, a05));
				PropertyUtils.copyProperties(a05_old, a05);
				sess.update(a05_old);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','保存成功！',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0500").setValue(a05.getA0500());// 保存成功将id返回到页面上。
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
			applog.createLog("3114", "A05", a01.getA0000(), a01.getA0101(), "删除记录",
					new Map2Temp().getLogInfo(new A05(), new A05()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[] { a05.getA0500() });
			
			String a0524 = a05.getA0524();
			if("1".equals(a0524)){
				a01.setA0192e(null); //数据库设置 人物信息表的现职级
				a01.setA0192c(null);//现职级时间
				sess.saveOrUpdate(a01);
				sess.flush();
				//this.getExecuteSG().addExecuteCode("realParent.setA0192eValue('')");//页面设置 人物信息表的现职务为空
				//this.getExecuteSG().addExecuteCode("realParent.setA0192cValue('')");//页面设置 人物信息表的现职务时间
			}
			
			sess.delete(a05);
			this.getExecuteSG().addExecuteCode("radow.doEvent('TrainingInfoGrid.dogridquery')");
			a05 = new A05();
			PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 显示职务职级grid表格
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
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 
	 * 点击显示信息
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A05 a05 = new A05();
		a05.setA0000(a0000);
		PMPropertyCopyUtil.copyObjValueToElement(a05, this);
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 修改事件
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','查询失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("setA0517Disabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	/***********************************************现职务A050*********************************************************************/
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
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if (a05.getA0501b() == null || "".equals(a05.getA0501b())) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','职务层次不能为空！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a05.setA0000(a0000);
		String a0500 = a05.getA0500();
		String a0504 = a05.getA0504();// 
		String a0517 = a05.getA0517();//
		String a0524 = a05.getA0524();//获取页面的状态值
		String a0501b = a05.getA0501b();//职务层次
		a05.setA0525(a0524);//调整后，a0525==a0524
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
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','批准时间不能大于结束时间！',null,'220')");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		A05 a05_old = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01) sess.get(A01.class, a0000);
			if (a0500 == null || "".equals(a0500)) {
				a05_old = new A05();
				if("1".equals(a0524)){//状态为在任
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"'").list();//检测是否之前有状态为在任的
					if(list_a05.size()>0){//检测到之前有状态为在任的
						this.getExecuteSG().addExecuteCode("$h.alert('系统提示','现职务层次不可重复在任！',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{//检测到之前没有状态为在任的
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//将同一人的其他职务层次状态设为0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b);//数据库设置 人物信息表的现职务层次 当前被选的职务层次
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//页面设置 人物信息表的现职务层次
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//页面设置 人物信息表的现职务层次时间
					}
				}
				applog.createLog("3112", "A05", a01.getA0000(), a01.getA0101(), "新增记录",
						new Map2Temp().getLogInfo(a05_old, a05));
				sess.save(a05);
				sess.flush();
			} else {
				a05_old = (A05) sess.get(A05.class, a0500);
				if("1".equals(a0524)){//状态为在任时，进行判断，若之前有在任的则提示
					List list_a05 = sess.createSQLQuery("select a0524,a0500 from a05 where a0524='1' and a0531='0' and a0000='"+a0000+"' and a0500<> '"+a0500+"'").list();//检测是否之前有状态为在任的
					if(list_a05.size()>0){//检测到其他职级有状态为在任的
						this.getExecuteSG().addExecuteCode("$h.alert('系统提示','现职级不可重复在任！',null,'220')");
						return EventRtnType.NORMAL_SUCCESS;
					}else{
						String sql = "update a05 set a0525 = '0' where a0000='"+a0000+"' and a0531='0' ";//将同一人的其他职级状态设为0
						sess.createSQLQuery(sql).executeUpdate();
						sess.flush();
						a01.setA0221(a0501b); //数据库设置 人物信息表的现职务层次 当前被选的职务层次
						a01.setA0288(a05.getA0504());
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('"+(a0501b==null?"":a0501b)+"')");//页面设置 人物信息表的现职务层次
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('"+(a05.getA0504()==null?"":a05.getA0504())+"')");//页面设置 人物信息表的现职务层次时间
					}
				}else if("0".equals(a0524)){//状态为以免时
					if("1".equals(a05_old.getA0524())){//原本是在任
						a01.setA0221(null);//设置 人物信息表的现职务层次 为空
						a01.setA0288(null);
						sess.saveOrUpdate(a01);
						sess.flush();
						//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('')");//页面设置 人物信息表的现职务为空
						//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//页面设置 人物信息表的现职务层次时间
					}
				}
				applog.createLog("3113", "A05", a01.getA0000(), a01.getA0101(), "修改记录",
						new Map2Temp().getLogInfo(a05_old, a05));
				PropertyUtils.copyProperties(a05_old, a05);
				sess.update(a05_old);
			}
			sess.flush();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存成功！',null,'220')");
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0500Post").setValue(a05.getA0500());// 保存成功将id返回到页面上。
		// this.getExecuteSG().addExecuteCode("Ext.getCmp('TrainingInfoGrid').getStore().reload()");//刷新专业技术职务列表
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
			applog.createLog("3114", "A05", a01.getA0000(), a01.getA0101(), "删除记录",
					new Map2Temp().getLogInfo(new A05(), new A05()));
			HBUtil.executeUpdate("delete from a41 where a1100=?", new Object[] { a05.getA0500() });
			
			String a0524 = a05.getA0524();
			if("1".equals(a0524)){
				a01.setA0288(null);
				a01.setA0221(null);//设置 人物信息表的现职务层次 为空
				sess.saveOrUpdate(a01);
				sess.flush();
				//this.getExecuteSG().addExecuteCode("realParent.setA0221Value('')");//页面设置 人物信息表的现职务为空
				//this.getExecuteSG().addExecuteCode("realParent.setA0288Value('')");//页面设置 人物信息表的现职务层次时间
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * 显示现职务grid表格
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
		this.pageQuery(sql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 
	 * 新增
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
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','请先保存人员基本信息！',null,'220')");
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
	 * 修改事件
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
			
			//对时间进行特殊处理，显示到页面
			String a0504Time = null;
			if(a05.getA0504() != null && !a05.getA0504().equals("")){
				a0504Time = a05.getA0504().substring(0,4) + "." + a05.getA0504().substring(4,6);
			}
			
			
			//免职时间可能为空，需要有空判断
			String a0517Time = null;
			if(a05.getA0517() != null && !a05.getA0517().equals("")){
				a0517Time = a05.getA0517().substring(0,4) + "." + a05.getA0517().substring(4,6);
			}
			
			
			this.getPageElement("a0504Post_1").setValue(a0504Time);
			this.getPageElement("a0517Post_1").setValue(a0517Time);
		} catch (Exception e) {
			this.getExecuteSG().addExecuteCode("window.$h.alert('系统提示','查询失败！',null,'220')");
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
		
		//没有职务信息时，做联动
		int num = this.getPageElement("WorkUnitsGrid").getStringValueList().size();
		if(num == 0){
			
			String a0195 = this.getPageElement("a0195").getValue();
			
			
				if(B0194 != null && !B0194.equals("2")){				//如果“任职机构”为内设机构，则不作联动
					
					if((a0195 != null && a0195.equals("")) || num == 0){			//如果“统计所在单位”已经存在，则不作联动
					
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
	
	
	//自定义人员信息项数据封装
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
	
	//查询自定义人员信息项sql
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
		
		if(field.size()>0){//有扩展字段
			if(Info_Extends==null||Info_Extends.size()==0){//新增
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
			}else{//修改
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
						//如果是日期，则特殊处理显示到页面
						
						if(entity.get(key).length() >= 6 && entity.get(key).length() <= 8){
							
							String timeShow = entity.get(key).substring(0,4) + "." + entity.get(key).substring(4,6);
							
							this.getPageElement(key.toLowerCase()+"_1").setValue(timeShow);
						}
						
						
					} catch (Exception e) {
					}
				}
			}
	}
	
	
	
	
	//----------------------------------------------------------电子档案--------------------------------------------------------
	
	
	//电子档案文件夹tree
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
			node="001";				//默认根文件夹id为001
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
		}//得到当前组织信息
		
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
	 * 电子档案tree双击事件
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
	 * 电子档案列表数据加载
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
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	 * 打开新建电子档案列表树窗口
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("openNewWindow")
	@NoRequiredValidate
	public int openNewWindow() throws RadowException {
		
		String treeId = this.getPageElement("treeId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		if(treeId==null || "".equals(treeId)) {
			this.setMainMessage("请您选择一个文件夹");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(a0000==null || "".equals(a0000)) {
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setRadow_parent_data("NEW,"+treeId+","+a0000);
		this.openWindow("AddFolderTree","pages.publicServantManage.AddFolderTree");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 打开修改电子档案文件夹tree窗口
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
			this.setMainMessage("请您选择一个文件夹");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("001.001".equals(treeId)) {
			this.setMainMessage("此文件夹不允许修改！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.setRadow_parent_data("update,"+treeId+","+a0000);
		this.openWindow("UpdateFolderTree","pages.publicServantManage.AddFolderTree");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 删除代码集
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
			this.setMainMessage("请您选择一个文件夹");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if("001.001".equals(treeId)) {
			this.setMainMessage("此文件夹不允许删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//一旦tree文件夹，存在下级文件夹，或者存在文件，不可删除
		String sqlCount = "select count(id) from FOLDER where a0000='"+a0000+"' and TREEID = '"+treeId+"'";
		Object count = sess.createSQLQuery(sqlCount).uniqueResult();
		if(!String.valueOf(count).equals("0")){
			this.setMainMessage("该文件夹下存在文件，不可删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String sqlTreeId = "select max(id) from foldertree where parentid = '"+treeId+"'";
		Object maxTreeIdO = sess.createSQLQuery(sqlTreeId).uniqueResult();
		if(maxTreeIdO != null && !maxTreeIdO.equals("")){	//存在下级tree
			this.setMainMessage("该文件夹存在下级文件夹，不可删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		this.addNextEvent(NextEventValue.YES, "sureClear","");									//调用sureClear方法，执行脚本删除
		this.addNextEvent(NextEventValue.CANNEL, "");										    //其下次事件需要参数值，在此设置下次事件的参数值
		this.setMessageType(EventMessageType.CONFIRM);										    //消息框类型，即confirm类型窗口
		this.setMainMessage("您确定要删除该文件夹吗？");				//窗口提示信息	
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 确认删除代码集
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
		
		//删除tree
		sess.createSQLQuery("delete from FolderTree where id = '"+treeId+"' and a0000 = '"+a0000+"'").executeUpdate();
		
		this.getPageElement("treeId").setValue("");
		this.getExecuteSG().addExecuteCode("reloadTree();");
		this.setNextEventName("folderGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
