package com.insigma.siis.local.pagemodel.search;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

import org.hibernate.Session;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
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
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
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
import com.insigma.siis.local.pagemodel.publicServantManage.chooseZDYtemPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;

/**
 * @author lixy
 *
 */
public class ComSearchPageModel extends PageModel {
	public static String b01String="";
	public static Map<Integer, String> map;
	/**
	 * ????????????
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType2="0";//1??????????????2????????????
	public static String tag="0";//0??????1????
	
	public ComSearchPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);
			
			HBSession sess = HBUtil.getHBSession();
			Object[] area = null;
			if(groups.isEmpty() || issupermanager ||loginnname.equals("admin")){
				area = SysOrgBS.queryInit();
				areaInfo.put("manager", "true");
			}else{
				area =  SysOrgBS.queryInit();
				areaInfo.put("manager", "false");
			}
			if(area!=null ) { 
				if(area[2].equals("1")){
					area[2]="picOrg";
				}else if(area[2].equals("2")){
					area[2]="picInnerOrg";
				}else{
					area[2]="picGroupOrg";
				}
				areaInfo.put("areaname", area[0]);
				areaInfo.put("areaid", area[1]);
				areaInfo.put("picType", area[2]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int doInit() {
		/*this.getExecuteSG().addExecuteCode("odin.ext.getCmp('peopleInfoGrid').view.refresh();");
		this.setNextEventName("peopleInfoGrid.dogridquery");*/
	    return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ??????????????????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //??????????????
		this.getExecuteSG().addExecuteCode("addTab('','"+this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	//??????????????
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {
		/*if(!SysRuleBS.havaRule(id))
			throw new AppException("????????????????");*/
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))){
				this.setMainMessage("??????????????????");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String sql = CustomQueryPageModel.getcommSQL() + 
		" where  exists (select 1 from a02 where  a0201b='"+id+"' and (a02.a0201b !='-1' and a0255='1') and " +
				" a01.a0000=a02.a0000)  and a01.status='1' ";
		this.getPageElement("sql").setValue(sql);
        this.getPageElement("a0201b").setValue(id);
        this.request.getSession().setAttribute("queryType2", "1");
        this.getExecuteSG().addExecuteCode("lict()");
		this.setNextEventName("peopleInfoGrid.dogridquery");		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("selectAllBtn.onclick")
	public int selectAll() throws RadowException {
//		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("optionGroup.onchange")
	public void showmessage(){
		this.setMainMessage("sdfasdfa");
	}
	
	@PageEvent("duty.onchange")
	@NoRequiredValidate
	@AutoNoMask
	public int eab025change() throws RadowException{
		String str=this.getPageElement("duty").getValue();
		PageElement pe=this.createPageElement("duty1",ElementType.SELECT,false);
		String arg0 = "substr(aaa102,0,1)='"+str.substring(0, 1)+"'";
//		String arg0 = "aaa102='132'";
		pe.loadDataForSelectStore("ZB09","",arg0,true);
		this.getPageElement("duty1").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		this.getPageElement("checkList").setValue("");
		String groupid = this.getPageElement("a0201b").getValue();
		String userID = SysManagerUtils.getUserId();
		queryType2=(String) this.request.getSession().getAttribute("queryType2");
		if(queryType2.equals("1")){
			String sql2="";
			Object obj = this.getPageElement("existsCheckbox").getValue();
			String a0201bsql = "";
//			if("1".equals(obj)){// 
//				a0201bsql = "a0201b like '"+b0111+"%'";
//			}else{
//				a0201bsql = "a0201b='"+b0111+"'";
//			}
//			sql = "select * from (select  a01.a0000, a0101,cbdresult,a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age," +
//			" a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL ,a0107,a0140,a0134," +
//			"a0165,a0121,a0184,orgid,status from  A01 a01,a02 a02,competence_userdept cu " +
//			" where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+userID+"' " +
//			" and a02."+a0201bsql+" and a02.a0255='1' and a01.status='1' ) a01 where 1=1 order by (select max(a0225) from a02,a01 cu where cu.a0000=a02.a0000 and a02.a0201b='"+b0111+"')";
//				this.pageQuery(sql, "SQL", start, limit); 
				if("1".equals(obj)){
					if(DBUtil.getDBType()==DBType.MYSQL){
						sql2 = "select * from (select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from "+ 
		                           "A01 a01,a02 a02,competence_userdept cu  where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' "+ 
		                           "and a02.a0201b like '"+groupid+"%' and a02.a0255='1' and a01.status='1' ) a01 where 1=1 "+  
		                           "and not exists ( select 1 from COMPETENCE_USERPERSON cu  where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') "+ 
		                           " group by a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"'), a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		               			   " ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status order by a01.a0148,(select max(a0225) from b01,a02  where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
					}else{
						sql2 = "select * from (select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from "+ 
		                           "A01 a01,a02 a02,competence_userdept cu  where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' "+ 
		                           "and a02.a0201b like '"+groupid+"%' and a02.a0255='1' and a01.status='1' ) a01 where 1=1 "+  
		                           "and not exists ( select 1 from COMPETENCE_USERPERSON cu  where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') "+ 
		                           "group by a01.a0000, a0101, a0104, age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL  ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status "+
		                           "order by a01.a0148,(select max(a0225) from b01,a02  where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
					}
					
				}else{
					if(DBUtil.getDBType()==DBType.MYSQL){
						sql2 = "select * from (select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from "+ 
		                           "A01 a01,a02 a02,competence_userdept cu  where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' "+ 
		                           "and a02.a0201b = '"+groupid+"' and a02.a0255='1' and a01.status='1' ) a01 where 1=1 "+  
		                           "and not exists ( select 1 from COMPETENCE_USERPERSON cu  where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') "+ 
		                           " group by a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"'), a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		               			   " ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status order by a01.a0148,(select max(a0225) from b01,a02  where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
					}else{
						sql2 = "select * from (select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from "+ 
		                           "A01 a01,a02 a02,competence_userdept cu  where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' "+ 
		                           "and a02.a0201b = '"+groupid+"' and a02.a0255='1' and a01.status='1' ) a01 where 1=1 "+  
		                           "and not exists ( select 1 from COMPETENCE_USERPERSON cu  where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') "+ 
		                           "group by a01.a0000, a0101, a0104, age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL  ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status "+
		                           "order by a01.a0148,(select max(a0225) from b01,a02  where a01.a0000 = a02.a0000 and a02.a0201b = b01.b0111 and a02.a0255 = '1')";
					}
				
				}
				this.pageQuery(sql2, "SQL", start, limit); 
				return EventRtnType.SPE_SUCCESS;
		}else{
			String sql=this.getPageElement("sql").getValue();
			String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
	    	//????????????
	        sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";
	        
	    	/*if(DBUtil.getDBType()==DBType.MYSQL){
	    		sql = sql + " group by a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"'), a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
	        			" ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status ";
	    	}else{
	    		sql = sql + " group by a01.a0000, a0101, a0104, age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
	        			" ,a0107,a0140,a0134,a0165,a0121,a0184,orgid,status ";
	    	}*/
	    	StopWatch w = new StopWatch();
	    	w.start();
	    	CommonQueryBS.systemOut("sql---:"+sql);
	    	this.pageQuery(sql, "SQL", start, limit);
	    	w.stop();
	    	
	    	PhotosUtil.saveLog("????????????????"+w.elapsedTime()+"\r\n??????sql:"+sql+"\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}
		
	}
	
	
	@PageEvent("query2")
	public int query2(String value) throws RadowException{
		String userID = SysManagerUtils.getUserId();
//		List alist = new ArrayList();
//		String b0111= SysUtil.getCacheCurrentUser().getUserGroups().get(0).getId();
		String b01String = value;
		//=========================================??????????????????b01String????chooseall????????????????{
		request.getSession().setAttribute("true", "????");
		//=========================================}
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        if(b01String!=null && !"".equals(b01String)){
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// ????jsonObject????????????Map????
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[????????????????????????????????????]
				if("true".equals(types[1])&&"true".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+"%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+"%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("true".equals(types[1])&&"false".equals(types[2])){
					//a02_a0201b_sb.append(" or a02.a0201b like '"+nodeid+".%' ");
					a02_a0201b_sb.append(" or "+CommSQL.subString2("a02.a0201b", 1, nodeid.length(), nodeid));
					//cu_b0111_sb.append(" or cu.b0111 like '"+nodeid+".%' ");
					cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
				}else if("false".equals(types[1])&&"true".equals(types[2])){
					a02_a0201b_sb.append(" or a02.a0201b = '"+nodeid+"' ");
					cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
				}
			}
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" ) ");
				cu_b0111_sb.append(" ) ");
			}
        }
		StringBuffer sb = new StringBuffer("");
			String a0160 = this.getPageElement("a0160").getValue();//????????
			String a0163 = this.getPageElement("a0163").getValue();//????????
			String ageS = this.getPageElement("age").getValue();//????????
			String ageE = this.getPageElement("age1").getValue();//????????
			String female = this.getPageElement("female").getValue();//??????????
			if(female.equals("1")){
				female = "2";
			}
			String minority = this.getPageElement("minority").getValue();//????????????
			String nonparty = this.getPageElement("nonparty").getValue();//??????????????	
			String duty = this.getPageElement("duty").getValue();//????????????
			String duty1 = this.getPageElement("duty1").getValue();//????????????
			CommonQueryBS.systemOut("duty:::::"+duty);;
			CommonQueryBS.systemOut("duty1:::::"+duty1);
//			if(duty==""|| duty.length()<=0){
////				duty = "199";
//			}
//			if(duty1==""|| duty1.length()<=0){
////				duty1 = "020";
//			}
			String dutynow = this.getPageElement("dutynow").getValue();//????????????????
			String dutynow1 = this.getPageElement("dutynow1").getValue();//????????????????
			String a0219 = this.getPageElement("a0219").getValue();//????????
			String edu = this.getPageElement("edu").getValue();//????????
			String edu1 = this.getPageElement("edu1").getValue();//????????
			
//			String a0201b=this.getPageElement("a0201b").getValue();
//			if(edu==""|| edu.length()<=0){
//				edu = "81";
//			}
//			if(edu1==""|| edu1.length()<=0){
//				edu1 = "1";
//			}
			String allday = this.getPageElement("allday").getValue();//??????????
			if(!"1".equals(allday)){
//				allday="2";
	            allday="";  //????????????????????????????  ?????????????????? ??bug??????708??  
            }
			
			if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
				CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
				CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
				if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
					try {
						throw new AppException("????????????????????????????????????");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				//???????? ?????? ??????????????
				if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
					try {
						throw new AppException("????????????????????????????");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
				if(dutynow.compareTo(dutynow1)>0){
					try {
						throw new AppException("????????????????????????????????????");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
				CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
				CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
				//???? ?????? ??????????????
				if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
					try {
						throw new AppException("????????????????????????");
					} catch (AppException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			
			//???????? end
			StringBuffer a01sb = new StringBuffer("");
			if (!a0160.equals("")){
				a01sb.append(" and ");
				a01sb.append("a01.a0160 ='"+a0160+"'");
			}
			/*if (!a0163.equals("")){
				a01sb.append(" and ");
				a01sb.append("a01.a0163='"+a0163+"'");
			}*/
			if (!female.equals("0")){
				a01sb.append(" and ");
				a01sb.append("a01.a0104='"+female+"'");
			}
			if (!minority.equals("0")){
				a01sb.append(" and ");
				a01sb.append("a01.a0117!='01'");
			}
			if (nonparty.equals("1")){
				a01sb.append(" and ");
				a01sb.append("a01.a0141!='01'");			
			}
	        if(!"".equals(duty1)){
	        	a01sb.append(" and ");
	        	a01sb.append("a01.a0148>='"+duty1+"'");		
	        }
	        if(!"".equals(duty)){
	        	a01sb.append(" and ");
	        	a01sb.append("a01.a0148<='"+duty+"'");		
	        }      
	        if (!ageS.equals("")){
	        	a01sb.append(" and ");
	        	a01sb.append("get_birthday(a01.a0107,'"+DateUtil.getcurdate()+"')>"+ageS+"");
			}
			if (!ageE.equals("")){
				a01sb.append(" and ");
				a01sb.append("get_birthday(a01.a0107,'"+DateUtil.getcurdate()+"')<"+ageE+"");
			}
	        StringBuffer a02sb = new StringBuffer("");  
	        if(!"".equals(dutynow)){
	        	a02sb.append(" and ");
	        	a02sb.append("a02.a0288>='"+dutynow+"'");
	        }
	        if(!"".equals(dutynow1)){
	        	a02sb.append(" and ");
	        	a02sb.append(" a02.a0288<='"+dutynow1+"'");
	        }
	        if(!"".equals(a0219)){
	        	a02sb.append(" and ");
	        	a02sb.append(" a02.a0219='"+a0219+"' ");
	        }
	        
	        StringBuffer orther_sb = new StringBuffer("");
	        if(!"".equals(edu)&&"".equals(edu1)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        if(!"".equals(edu1)&&"".equals(edu)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        if(!"".equals(edu1)&&!"".equals(edu)){
	        	orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
	            if(!"".equals(allday)){
	            	orther_sb.append(" and ");
	            	orther_sb.append("a0837 = '"+allday+"'");		
	            }
	            orther_sb.append(")");
	        }
	        String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163);
	        
	        this.getPageElement("sql").setValue("select * from ("+finalsql+") a01 where 1=1 ");
	        //????????????????????chooseall??
	        ////====================================={
	        this.request.getSession().setAttribute("truesql", "select * from ("+finalsql+") a01 where 1=1 ");
	        this.getPageElement("checkList").setValue("");
	        ////=====================================}

//			sb.append(CustomQueryPageModel.getcommSQL() +
//					" where    a01.status='1' ");
//			
//			if (!a0160.equals("")){
//				sb.append(" and ");
//				sb.append("a01.a0160 ='"+a0160+"'");
//			}
//			if (!a0163.equals("")){
//				sb.append(" and ");
//				sb.append("a01.a0163='"+a0163+"'");
//			}
//			if (!ageS.equals("")){
//				sb.append(" and ");
//				sb.append("a01.age>'"+ageS+"'");
//			}
//			if (!ageE.equals("")){
//				sb.append(" and ");
//				sb.append("a01.age<'"+ageE+"'");
//			}
//			if (!female.equals("0")){
//				sb.append(" and ");
//				sb.append("a01.a0104='"+female+"'");
//			}
//			if (!minority.equals("0")){
//				sb.append(" and ");
//				sb.append("a01.a0117<>'01'");
//			}
//			if (nonparty.equals("1")){
//				sb.append(" and ");
//				sb.append("a01.a0141<>'01'");			
//			}
//            if(!"".equals(duty1)){
//				sb.append(" and ");
//				sb.append("a01.a0148<='"+duty1+"'");		
//            }
//            if(!"".equals(duty)){
//				sb.append(" and ");
//				sb.append("a01.a0148>='"+duty+"'");		
//            }
//            
//            sb.append(" and exists (select 1 from a02 where  a02.a0201b !='-1' and a0255='1' and  a01.a0000=a02.a0000 ");
//            
//            if(!"".equals(dutynow)){
//				sb.append(" and ");
//				sb.append("a02.a0288>='"+dutynow+"'");		
//            }
//            if(!"".equals(dutynow1)){
//				sb.append(" and ");
//				sb.append("a02.a0288<='"+dutynow1+"'");		
//            }
//            if(!"".equals(a0219)){
//				sb.append(" and ");
//				sb.append("a02.a0219='"+a0219+"'");		
//            }
//            if(!"".equals(b01String)&&b01String!=null){
//				sb.append(" and ");
//				sb.append("a02.A0201B in ("+b01String+")");
//                this.getPageElement("a0201b").setValue(b01String.replace("'", "|").replace(",", "@"));	
//            }
//            sb.append(")");
//            
//            
//            if(!"".equals(edu)&&"".equals(edu1)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
//            if(!"".equals(edu1)&&"".equals(edu)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
//            if(!"".equals(edu1)&&!"".equals(edu)){
//				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
//	            if(!"".equals(allday)){
//					sb.append(" and ");
//					sb.append("a0837 = '"+allday+"'");		
//	            }
//	            sb.append(")");
//            }
            this.request.getSession().setAttribute("queryType2", "2");
            
//    		this.getPageElement("sql").setValue(sb.toString());
    		//CommonQueryBS.systemOut(this.getPageElement("sql").getValue());
			this.setNextEventName("peopleInfoGrid.dogridquery");

			return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	public List<B01> selectListBySubId(String id){
		List<B01> list = new ArrayList<B01>();
		String sql="from B01 where B0111 like '"+id+"%'";
		list = HBUtil.getHBSession().createQuery(sql).list();
		return list;
	}
	
	@PageEvent("dogrant")
	public  int  gettree(String value) throws RadowException {
		String ret=null;
		String[] nodes = null;
		HashMap<String,String> nodemap = new HashMap<String,String>();
		if(value !=null) {
			nodes = value.split(",");
			B01 b01 =new B01();
			for(int i=0;i<nodes.length;i++) {
				if(nodes[i].split(":")[2].equals("1")){
					List<B01> b01s = selectListBySubId(nodes[i].split(":")[0]);
					for(int j=0;j<b01s.size();j++){
						b01=b01s.get(j);
						nodemap.put(b01.getB0111(),nodes[i].split(":")[1]);
					}
				}else if(nodes[i].split(":")[2].equals("2")){
					List<B01> b01s = selectListBySubId(nodes[i].split(":")[0]);
					for(int j=0;j<b01s.size();j++){
						b01=b01s.get(j);
						nodemap.put(b01.getB0111(),nodes[i].split(":")[1]);
					}
				}else{
					nodemap.put(nodes[i].split(":")[0], nodes[i].split(":")[1]);
				}
			}
		}
		StringBuffer addresourceIds = new StringBuffer();
		StringBuffer removeresourceIds = new StringBuffer();
		for(String node :nodemap.keySet()) {
			if(nodemap.get(node).equals("true")) {
				addresourceIds.append("'"+node+"',");
			}else if(nodemap.get(node).equals("false")) {
				removeresourceIds.append(node+",");
			}
		}
		ret=addresourceIds.toString();
		if(ret!=null&&!"".equals(ret)){
		   b01String=ret.substring(0,ret.lastIndexOf(","));
		   this.request.getSession().setAttribute("b01String", b01String);
		  // this.request.getSession().setAttribute("queryType", "1");
		}else{
			this.request.getSession().setAttribute("b01String", "");
		}
//		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("checkPer")
	public int checkPer(String value) throws RadowException, AppException{
		
		String [] values = value.split("@");
		String a0000 = values[0];
		String a0101 = values[1];
		HBSession sess = HBUtil.getHBSession();
		StringBuffer sql2 = new StringBuffer();
		String [] a0000s = a0000.split(",");
		String [] a00001s = a0000s;
		for(int i=0;i<a0000s.length;i++){
			sql2=sql2.append("'"+a0000s[i]+"',");
		}
		String sql3 = sql2.substring(0, sql2.length()-1);
		String sql = "select a0101 from Rydjb where a0000 in ("+sql3+")";
		String peopleId = "select a0000 from Rydjb where a0000 in ("+sql3+")";
		List list = sess.createQuery(sql).list();
		List list2 = sess.createQuery(peopleId).list();
		//????????????list
		List list3 = new ArrayList(Arrays.asList(a00001s));
		if(list.size()>0){
			StringBuffer name = new StringBuffer();
			StringBuffer ids = new StringBuffer();
			String allIds = "";
			//????????????????????????????
			for(int i=0;i<list.size();i++){
				name=name.append(""+list.get(i)+",");
			}
			//??????????????id????
			for(int j=0;j<list2.size();j++){
				for(int z=0;z<list3.size();z++){
					if(list3.get(z).equals(list2.get(j))){
						list3.remove(z);
					}
				}
			}
			//??????????????id????
			for(int i=0;i<list3.size();i++){
				ids=ids.append(""+list3.get(i)+",");
			}
			if(!StringUtil.isEmpty(ids.toString())){
				//??????????????????????????????????????????????????????????
				allIds = ids.substring(0,ids.length()-1);
				String allName = name.substring(0, name.length()-1);
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('????????','"+allName+",??????????????????????????????????????????????????????????????????????????????????????',200,function(id){" +
						"if(id=='yes'){" +
						"	ml('"+a0000+"','"+allName+"');		" +
							"}else if(id=='no'){" +
							"	ml('"+allIds+"','"+allName+"');" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				String allName = name.substring(0, name.length()-1);
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('????????','"+allName+",??????????????????????????????????????????????????????????????????????????????????????',200,function(id){" +
						"if(id=='yes'){" +
						"	ml('"+a0000+"','"+allName+"');		" +
							"}else if(id=='no'){" +
							"	expExcelTemp();" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}
			
			
		}else{
			this.getExecuteSG().addExecuteCode("ml('"+a0000+"','"+a0101+"');");
		}		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+"@|"+hm.get("a0000")+"|";
				++cnt;
			}
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkList").setValue(listString
 				);
 		Map<Integer,String> map = PreSubmitPageModel.map;
 		map.put(1, listString);
 		System.out.println("--->"+listString);

// 		PageElement pedjb = this.createPageElement("getDjb",
//				ElementType.BUTTON, false);
// 		PageElement createDjb = this.createPageElement("createDjb",
//				ElementType.BUTTON, false); 		 		
//        if(cnt>1){
//        	pedjb.setDisabled(true);
//        	createDjb.setDisabled(true);         	
//        }else{
//        	pedjb.setDisabled(false);
//        	createDjb.setDisabled(false);            	
//        }
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ????????
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("clear.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int clear() throws RadowException {
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String jsonStr =OrgNodeTree.getCodeTypeJS("4");
		this.setSelfDefResData(jsonStr);
		return EventRtnType.XML_SUCCESS;
	}
	
	/**
	 * ????/????????
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("modifyAttach")
	public int editFile(String value) throws RadowException{
		this.setRadow_parent_data(value);
		this.openWindow("modifyFileWindow", "pages.search.ModifyAttach");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ????????
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */


	@PageEvent("tiaozhuan")
	public int tiaozhuan( ) throws RadowException{

		this.getExecuteSG().addExecuteCode("addTab('????','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.PreSubmit',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	//??????????????
	@PageEvent("choosedata")
	public int choosedata() throws RadowException{
		String s = "";
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpid,tpname from listoutput where tptype=1 and tpkind=1 group by tpid,tpname").executeQuery();
			while (res.next()) {
				s = s+"["+res.getString(1)+",'"+res.getString(2)+"'],";
			}
			this.request.getSession().setAttribute("s", s);
//			this.setRadow_parent_data(this.getPageElement("checkList").getValue());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//??????????????
	@PageEvent("showtem")
	public int showtem(String value) throws RadowException{
		this.request.getSession().setAttribute("tpid", value);
		this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
		this.getExecuteSG().addExecuteCode("addTab('????','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//????????????
	@PageEvent("showtemplate")
	public int showtemplate( ) throws RadowException{
		String tabname = this.getPageElement("tabname").getValue();
		this.getExecuteSG().addExecuteCode("addTab('"+tabname+"','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.PreSubmit',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//??????????
	@PageEvent("chooseout")
	public int chooseout() throws RadowException{
		String ss = "['50','??????????'],";
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpid,tpname from listoutput where (tptype=2 or tptype=3) and tpkind=2 group by tpid,tpname ").executeQuery();
			while(res.next()) {
				ss = ss+"['"+res.getString(1)+"','"+res.getString(2)+"'],";
			}
			ss = ss.substring(0, ss.length()-1);
			this.getPageElement("ddd").setValue(ss);
			this.request.getSession().setAttribute("s", ss);
			this.getExecuteSG().addExecuteCode("zdy()");
			ss = "";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//??????????
	@PageEvent("zpzdy")
	public int zpzdy() throws RadowException{
		String ss = "";
		try {
			ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select tpid,tpname from listoutput where (tptype=2 or tptype=3) and tpkind=3 group by tpid,tpname ").executeQuery();
			while(res.next()) {
				ss = ss+"['"+res.getString(1)+"','"+res.getString(2)+"'],";
			}
			if(!"".equals(ss)){
				ss = ss.substring(0, ss.length()-1);
			}
			this.getPageElement("ddd").setValue(ss);
			this.request.getSession().setAttribute("s", ss);
			this.getExecuteSG().addExecuteCode("zpzdy()");
			ss = "";

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("chooseall")
	public int chooseall() throws RadowException{
		String sql = "";
		String id = "";
		String check = (String) request.getSession().getAttribute("true");
		if("????".equals(check)){
			sql = 	(String) request.getSession().getAttribute("truesql");
			List<Object[]> idall = HBUtil.getHBSession().createSQLQuery(sql).list();
			for(int a = 0;a<idall.size();a++){
				String ido = (String) idall.get(a)[0];
				id += "|"+ido+"|@";
			}
		}else{
			String groupid = this.getPageElement("a0201b").getValue();
			Object obj = this.getPageElement("existsCheckbox").getValue();
			if("1".equals(obj)){
//				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0255 = '1' ";
				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0201b like '"+groupid+"%' and a02.a0255='1' and a01.status='1' group by a01.a0000";
			}else{
				sql = "select a01.a0000 from a01 a01,a02 a02 where a01.a0000 = a02.a0000 and a02.a0201b = '"+groupid+"' and a02.a0255='1' and a01.status='1' group by a01.a0000";
			}
			List idall = HBUtil.getHBSession().createSQLQuery(sql).list();
			for(int a = 0;a<idall.size();a++){
				  String ido = (String) idall.get(a);
				id += "|"+ido+"|@";
			}
		}
		id = id.substring(0,id.length()-1);
		this.request.getSession().setAttribute("checkListAll",id);
		this.request.getSession().setAttribute("personidsall",id);
		this.request.getSession().setAttribute("yListall",id);
		return EventRtnType.NORMAL_SUCCESS;
	}

	
}














