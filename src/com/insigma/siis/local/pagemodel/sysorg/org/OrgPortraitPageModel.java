package com.insigma.siis.local.pagemodel.sysorg.org;
import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.util.DefaultPermission;
import com.insigma.odin.framework.privilege.vo.GroupVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.bean.SQLInfo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.helperUtil.CodeType2js;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysorg.org.SysOrgBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.betchModifyPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.picCut.servlet.SaveLrmFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @author lixy
 *
 */
public class OrgPortraitPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		HttpSession session = request.getSession();
		 
		String orgid =  (String)session.getAttribute("orgid");
		String isContain =  (String)session.getAttribute("isContain");
		System.out.println("++++++++++传入id"+orgid);
		System.out.println("——————————传入id"+isContain);
		
		
		//this.setNextEventName("initX");
		//this.setNextEventName("initM");
		try {			
			initX();
			initM();
			initN();
			
			
			this.getExecuteSG().addExecuteCode("init()");
			
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	public int initN()throws RadowException, AppException{
		String sid = this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		HttpSession session = request.getSession();
		String orgid =  (String)session.getAttribute("orgid");
		String isContain =  (String)session.getAttribute("isContain");
		
		
		String a0201bsql = "";
		//String a01Orgidsql = "";
		String is_use_a0000_idx_sql = "";
		int idLength = orgid.length();
		if("1".equals(isContain)){//是否包含下级
			this.request.getSession().setAttribute("isContainCQ", true);
			//CommSQL.getComFields();
			a0201bsql = CommSQL.subString("a02.a0201b", 1, idLength,orgid);
			//a01Orgidsql = CommSQL.subString("cu.b0111", 1, idLength,id);
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			//is_use_a0000_idx_sql = CommSQL.concat("a01.a0000","''");
			
			is_use_a0000_idx_sql = "a01.a0000";
		}else{
			this.request.getSession().setAttribute("isContainCQ", false);
			a0201bsql = "a02.a0201b='"+orgid+"'";
			//a01Orgidsql = "a01.orgid='"+id+"'";
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			is_use_a0000_idx_sql = "a01.a0000";
		}

		String mllb = "";		//管理类别
		//String sql=CommSQL.getComSQL(sid)+" where 1=1 ";  
		String sql="select  a01.a0000,TRUNC(months_between(sysdate, to_date(substr(a01.a0107,0,6)||'01' ,'yyyymmdd'))/12) age ,'FA9BF0A9B63E6451E5D98C9C804944FF' from A01 a01  where 1=1";  
		
		String a0163sql = "";
		//String a0163 = this.getPageElement("personq").getValue();
		String a0163 = "1";
		if("2".equals(a0163)){
			a0163sql = " and a0163 in('2','21','22','23','29')";
		}else if("".equals(a0163)){
			
		}else{
			a0163sql = " and a0163='"+a0163+"'";
		}
				
		//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       其它现职人员  历史人员（删除）  离退人员 职务为空的其它现职人员
         sql = sql + " and a01.status!='4' " +  (!"".equals(mllb) ? "  and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
					+ " and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a0281='true' "
					+ "  and "+a0201bsql+")";			

        sql = "select  age ,count(*) from ("+sql+") group  by  age";
        
        System.out.println("查询年龄比例sql语句"+sql);
        StringBuffer str = new StringBuffer();
        String string = "";
        List<HashMap<String,Object>> queryZWCC = query(sql);
        if(queryZWCC.size()==0){
			System.out.println("画像信息提示：无人员民族信息！");
		}else{
			str.append("[{name:\"人数\",").append("type:\"bar\",data: [");
			//int num=0;  
			int num1=0;     int num2=0;
			int num3=0;     int num4=0;
			int num5=0;
			int num6=0;    //小于20岁
			for(int i = 1; i < queryZWCC.size(); i++){
				int a[] = new int[queryZWCC.size()];
				
				//HashMap<String,Object> map0 = queryZWCC.get(0);
				HashMap<String,Object> map1 = queryZWCC.get(i);
				//String num0 = (String) map0.get("count(*)");
				String m = (String) map1.get("age");
				String j = (String) map1.get("count(*)");
				int age = Integer.parseInt(m);
				int num = Integer.parseInt(j);
				if (age<20){
				     num6 = num6+num;
				}else if (age>=20&&age<29){
			       num1 = num1+num;
			    }else if(age>=30&&age<39){
			       num2 = num2+num;
			    }else if(age>=40&&age<49){
			       num3 = num3+num;
			    }else if(age>=50&&age<59){
			       num4 = num4+num;
			    }else if(age>=60){
				       num5 = num5+num;
				    }			   											
			}

			System.out.println("-20"+num6+"20-30"+num1+"30-40"+num2+"40-50"+num3+"50-60"+num4+"60-"+num5);
			str.append(num6).append(",").append(num1).append(",").append(num2).append(",").append(num3).append(",").append(num4).append(",").append(num5).append("]");
			str.append("},]");
			
			string = str.toString();
			System.out.println(string);
			this.getPageElement("jsonDatanl").setValue(string);
			
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	public int initX()throws RadowException, AppException{
		String sid = this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		HttpSession session = request.getSession();
		String orgid =  (String)session.getAttribute("orgid");
		String isContain =  (String)session.getAttribute("isContain");
		
		
		String a0201bsql = "";
		//String a01Orgidsql = "";
		String is_use_a0000_idx_sql = "";
		int idLength = orgid.length();
		if("1".equals(isContain)){//是否包含下级
			this.request.getSession().setAttribute("isContainCQ", true);
			//CommSQL.getComFields();
			a0201bsql = CommSQL.subString("a02.a0201b", 1, idLength,orgid);
			//a01Orgidsql = CommSQL.subString("cu.b0111", 1, idLength,id);
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			//is_use_a0000_idx_sql = CommSQL.concat("a01.a0000","''");
			
			is_use_a0000_idx_sql = "a01.a0000";
		}else{
			this.request.getSession().setAttribute("isContainCQ", false);
			a0201bsql = "a02.a0201b='"+orgid+"'";
			//a01Orgidsql = "a01.orgid='"+id+"'";
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			is_use_a0000_idx_sql = "a01.a0000";
		}
		/*String radioC = this.getPageElement("radioC").getValue();
		String hasQueried = this.getPageElement("sql").getValue();
		if(!"1".equals(radioC)){
			if("".equals(hasQueried) || hasQueried==null)
				throw new AppException("未进行过查询请先查询!");
		}*/
		/*String xzry = this.getPageElement("xzry").getValue();		//现职人员
		String lsry = this.getPageElement("lsry").getValue();		//历史人员
		String ltry = this.getPageElement("ltry").getValue();		//离退人员
		String paixu = "1"; //this.getPageElement("paixu").getValue();		//按职务层次排序(默认1)
		String mllb = this.getPageElement("mllb").getValue();		//管理类别
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		m.put("xzry", "1".equals(xzry));
		m.put("lsry", "1".equals(lsry));
		m.put("ltry", "1".equals(ltry));
		m.put("paixu", "1".equals(paixu));
		m.put("isContain", "1".equals(isContain));
		this.request.getSession().setAttribute("queryConditionsCQ", m);
		this.request.getSession().setAttribute("queryConditionsMLLB", mllb);*/
		String mllb = "";		//管理类别
		//String sql=CommSQL.getComSQL(sid)+" where 1=1 ";  
		String sql="select  a01.a0000,a01.a0104 ,'FA9BF0A9B63E6451E5D98C9C804944FF' from A01 a01  where 1=1 ";  
		
		String a0163sql = "";
		//String a0163 = this.getPageElement("personq").getValue();
		String a0163 = "1";
		if("2".equals(a0163)){
			a0163sql = " and a0163 in('2','21','22','23','29')";
		}else if("".equals(a0163)){
			
		}else{
			a0163sql = " and a0163='"+a0163+"'";
		}
				
		//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       其它现职人员  历史人员（删除）  离退人员 职务为空的其它现职人员
         sql = sql + " and a01.status!='4' " +  (!"".equals(mllb) ? "  and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
					+ " and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a0281='true' "
					+ "  and "+a0201bsql+")";			

        sql = "select  A0104 ,count(*) from("+sql+") group  by  A0104";
        
        System.out.println("查询性别比例sql语句"+sql);
        StringBuffer str = new StringBuffer();
        String string = "";
        List<HashMap<String,Object>> queryZWCC = query(sql);
        if(queryZWCC.size()==0){
			System.out.println("画像信息提示：无人员性别信息！");
		}else{
			str.append("[{name:\"性别比例\",").append("type:\"pie\",radius:\"55%\",center:[\"50%\", \"60%\"],data: [");
			
			HashMap<String,Object> map0 = queryZWCC.get(0);
			HashMap<String,Object> map1 = queryZWCC.get(1);
			String num0 = (String) map0.get("count(*)");
			String num1 = (String) map1.get("count(*)");
			System.out.println("男："+num0+"女"+num1);
			str.append("{value:").append(num0).append(", name:'男'},");
			str.append("{value:").append(num1).append(", name:'女'},");
			str.append("],}]");
			string = str.toString();
			System.out.println(string);
			this.getPageElement("jsonData").setValue(string);
		}
        
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	public int initM()throws RadowException, AppException{
		String sid = this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		HttpSession session = request.getSession();
		String orgid =  (String)session.getAttribute("orgid");
		String isContain =  (String)session.getAttribute("isContain");
		
		
		String a0201bsql = "";
		//String a01Orgidsql = "";
		String is_use_a0000_idx_sql = "";
		int idLength = orgid.length();
		if("1".equals(isContain)){//是否包含下级
			this.request.getSession().setAttribute("isContainCQ", true);
			//CommSQL.getComFields();
			a0201bsql = CommSQL.subString("a02.a0201b", 1, idLength,orgid);
			//a01Orgidsql = CommSQL.subString("cu.b0111", 1, idLength,id);
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			//is_use_a0000_idx_sql = CommSQL.concat("a01.a0000","''");
			
			is_use_a0000_idx_sql = "a01.a0000";
		}else{
			this.request.getSession().setAttribute("isContainCQ", false);
			a0201bsql = "a02.a0201b='"+orgid+"'";
			//a01Orgidsql = "a01.orgid='"+id+"'";
			String[] jgid = orgid.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			is_use_a0000_idx_sql = "a01.a0000";
		}

		String mllb = "";		//管理类别
		//String sql=CommSQL.getComSQL(sid)+" where 1=1 ";  
		String sql="select  a01.a0000,a01.a0117 ,'FA9BF0A9B63E6451E5D98C9C804944FF' from A01 a01  where 1=1 ";  
		
		String a0163sql = "";
		//String a0163 = this.getPageElement("personq").getValue();
		String a0163 = "1";
		if("2".equals(a0163)){
			a0163sql = " and a0163 in('2','21','22','23','29')";
		}else if("".equals(a0163)){
			
		}else{
			a0163sql = " and a0163='"+a0163+"'";
		}
				
		//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       其它现职人员  历史人员（删除）  离退人员 职务为空的其它现职人员
         sql = sql + " and a01.status!='4' " +  (!"".equals(mllb) ? "  and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
					+ " and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a0281='true' "
					+ "  and "+a0201bsql+")";			

        sql = "select  A0117 ,count(*) from("+sql+") group  by  A0117";
        
        System.out.println("查询民族比例sql语句"+sql);
        StringBuffer str = new StringBuffer();
        String string = "";
        List<HashMap<String,Object>> queryZWCC = query(sql);
        if(queryZWCC.size()==0){
			System.out.println("画像信息提示：无人员民族信息！");
		}else{
			str.append("[{name:\"民族比例\",").append("type:\"pie\",radius:\"55%\",center:[\"50%\", \"60%\"],data: [");
			int number = 0; 
			int num = 0;
			for(int i = 1; i < queryZWCC.size(); i++){
				HashMap<String,Object> map0 = queryZWCC.get(0);
				HashMap<String,Object> map1 = queryZWCC.get(i);
				String num0 = (String) map0.get("count(*)");
				String num1 = (String) map1.get("count(*)");
			    num = Integer.parseInt(num0);
				int j = Integer.parseInt(num1);				
				number = number+j;									
			}		   
			System.out.println("汉："+num+"其他"+number);
			str.append("{value:").append(num).append(", name:'汉族'},");
			str.append("{value:").append(number).append(", name:'少数民族'},");
			str.append("],}]");
			string = str.toString();
			System.out.println(string);
			this.getPageElement("jsonDatamz").setValue(string);
			
		}
        
        
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//查询文字对照信息
		public static List<HashMap<String,Object>> query(String sql){
			CommQuery query = new CommQuery();
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			try {
				list = query.getListBySQL(sql);
				return list;
			} catch (AppException e) {
				e.printStackTrace();
			}
			return null;
		}
}