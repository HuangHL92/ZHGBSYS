package com.insigma.siis.local.pagemodel.otherdb.nqgb;
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
import java.sql.PreparedStatement;
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
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
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
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.business.entity.Js01;
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
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.customquery.DataPadImpDBThread;
import com.insigma.siis.local.pagemodel.customquery.JbmcExp;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.ImpModLogThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.publicServantManage.betchModifyPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.insigma.siis.local.pagemodel.xbrm.QCJSFileExportBS;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.pojo.ExcelA57Pojo;
import com.picCut.servlet.SaveLrmFile;
import com.utils.DBUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @author lixy
 *
 */
public class CustomQueryNQPageModel extends PageModel {
	
	//�����־����
	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	private LogUtil applog = new LogUtil();
	
	/**
	 * ϵͳ������Ϣ
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType="0";//1�����������ѯ2�����ť��ѯ
	public static String tag="0";//0δִ��1ִ��
	public static String LISTADDCCQLI="-1";
	public static boolean QUERYLISTFLAG=false;
	public static String LISTADDNAME="��";
	private final static int ON_ONE_CHOOSE=-1;
	private final static int CHOOSE_OVER_TOW=-2;
	public static String getcommSQL(String type){
		return "select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		",a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from A01 a01 ";
	}
	public static String getcommSQL(){
		return getcommSQL("0");
	}
	public CustomQueryNQPageModel(){
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
	

	CustomQueryBS cbBs=new CustomQueryBS();
	
	@PageEvent("getCheckList")
	public int getCheckList() throws RadowException, AppException{
		
		String listString=null;
		int cnt=0;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String a0000 = "";
		String a0101 = "";
		for (HashMap<String, Object> hm : list) {
			
			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+"@|"+hm.get("a0000")+"|";
				++cnt;
				
				if(cnt==1){
					a0000 = hm.get("a0000").toString();
					a0101 = hm.get("a0101").toString();
				}
			}
			
		}
		if(!"".equals(listString)&&listString!=null)
		    listString=listString.substring(listString.indexOf("@")+1,listString.length());
 		this.getPageElement("checkList").setValue(listString);
 		
 		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		/*String listString = this.getPageElement("checkList").getValue();
		int sign = 0;
		if("".equals(listString)){
			sign = 1;//˵�����״��޸�
		}
		
		String a0000 = "";
		int index = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		String personcheck = this.getPageElement("peopleInfoGrid").getValue("personcheck", index).toString();
		if("true".equals(personcheck)){
			String personid = this.getPageElement("peopleInfoGrid").getValue("a0000", index).toString();
			a0000 = personid;
			listString=listString+"@|"+personid+"|";
		}
		if(sign == 1){
			listString=listString.substring(listString.indexOf("@")+1,listString.length());
		}
		this.getPageElement("checkList").setValue(listString);*/
		
 		//String s = this.getPageElement("showOrHid").getValue();
 		if(true){
 			this.getPageElement("a0000s").setValue(a0000);
 			//this.getExecuteSG().addExecuteCode("document.getElementById('peopleName').innerHTML = '"+a0101+"';");
 	 		String tabID = this.getPageElement("showTabID").getValue();
 	 		if(tabID!=null){
 	 			if("".equals(tabID) || "showTab1".equals(tabID)){
 	 				this.setNextEventName("zwxx.dogridquery");
 	 			}
 	 			if("showTab2".equals(tabID)){
 	 				this.setNextEventName("zhuanyexx.dogridquery");
 	 			}
 	 			if("showTab3".equals(tabID)){
 	 				this.setNextEventName("xuelixx.dogridquery");
 	 			}
 	 			if("showTab4".equals(tabID)){
 	 				this.setNextEventName("peixunxx.dogridquery");
 	 			}
 	 			if("showTab5".equals(tabID)){
 	 				this.setNextEventName("jiangchengxx.dogridquery");
 	 			}
 	 			if("showTab6".equals(tabID)){
 	 				this.setNextEventName("kaohexx.dogridquery");
 	 			}
 	 			if("showTab7".equals(tabID)){
 	 				this.setNextEventName("jinruxx.dogridquery");
 	 			}
 	 			if("showTab8".equals(tabID)){
 	 				this.setNextEventName("tuichuxx.dogridquery");
 	 			}
 	 			if("showTab9".equals(tabID)){
 	 				this.setNextEventName("jiatingxx.dogridquery");
 	 			}
 	 			/*if("showTab10".equals(tabID)){
 	 				this.setNextEventName("zhuzhixx.dogridquery");
 	 			}
 	 			if("showTab11".equals(tabID)){
 	 				this.setNextEventName("peixunrenyuan.dogridquery");
 	 			}*/
 	 			if("showTab12".equals(tabID)){
 	 				this.setNextEventName("nirenxx.dogridquery");
 	 			}
 	 			/*if("showTab13".equals(tabID)){
 	 				this.setNextEventName("kaoshiluyong.dogridquery");
 	 			}
 	 			if("showTab14".equals(tabID)){
 	 				this.setNextEventName("xuantiaosheng.dogridquery");
 	 			}
 	 			if("showTab15".equals(tabID)){
 	 				this.setNextEventName("linxuan.dogridquery");
 	 			}
 	 			if("showTab16".equals(tabID)){
 	 				this.setNextEventName("gongkaixtxx.dogridquery");
 	 			}
 	 			if("showTab17".equals(tabID)){
 	 				this.setNextEventName("kaoshixx.dogridquery");
 	 			}
 	 			if("showTab18".equals(tabID)){
 	 				this.setNextEventName("gongwuyuanxx.dogridquery");
 	 			}
 	 			if("showTab19".equals(tabID)){
 	 				this.setNextEventName("zhiwuxx.dogridquery");
 	 			}*/
 	 			if("showTab20".equals(tabID)){
 	 				this.setNextEventName("beizhuxx.dogridquery");
 	 			}
 	 		}
 		}
 		
 		//old start֧�ֶ�ѡ���ɺ͵���
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
        //end
        
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("zwxx.dogridquery")
	public int zwxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a0201A a0201a,a0201B a0201b,CASE A0201D WHEN '1' THEN '��' WHEN '0' THEN '��' END a0201d,a0201E a0201e,a0215A a0215a,a0219,"
				+ "a0221,a0222,a0223,a0225,a0229,a0243,a0245,a0247,a0251,"
				+ "CASE A0251B WHEN '1' THEN '��' WHEN '0' THEN '��' END a0251b,a0255,a0265,a0267,a0272,a0281 "
				+ "FROM A02 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}

		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("zhuanyexx.dogridquery")
	public int zhuanyexxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a0601,a0602,a0604,a0607,a0611 FROM A06 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("xuelixx.dogridquery")
	public int xuelixxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a0801A a0801a,a0801B a0801b,a0901A a0901a,a0901B a0901b,a0804,a0807,a0904,a0814,a0824,a0827,a0837,a0811 FROM A08 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("peixunxx.dogridquery")
	public int peixunxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a1101,a1104,a1107,a1111,a1107C a1107c,a1108,a1114,a1121A a1121a,a1127,a1131,a1151 FROM A11 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("jiangchengxx.dogridquery")
	public int jiangchengxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a1404A a1404a,a1404B a1404b,a1407,a1411A a1411a,a1414,a1415,a1424,a1428 FROM A14 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("kaohexx.dogridquery")
	public int kaohexxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a1517,a1521 FROM A15 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("jinruxx.dogridquery")
	public int jinruxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a2907,a2911,a2921A a2921a,a2941,a2944,a2949 FROM A29 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("tuichuxx.dogridquery")
	public int tuichuxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a3001,a3004,a3034 FROM A30 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("jiatingxx.dogridquery")
	public int jiatingxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a3601,a3604A a3604a,a3607,a3611,a3627,a3684 FROM A36 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("zhuzhixx.dogridquery")
	public int zhuzhixxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a3701,a3707A a3707a,a3707B a3707b,a3707C a3707c,a3707E a3707e,a3708,a3711,a3714 FROM A37 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("peixunrenyuan.dogridquery")
	public int peixunrenyuanQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a1100,a4101,a4102,a4103,a4104,a4105,a4199 FROM A41 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("nirenxx.dogridquery")
	public int nirenxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a5304,a5315,a5317,a5319,a5321,a5323,a5327 FROM A53 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("kaoshiluyong.dogridquery")
	public int kaoshiluyongQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a6001,a6002,a6003,a6004,a6005,a6006,a6007,a6008,a6009,CASE a6010 WHEN '1' THEN '��' WHEN '0' THEN '��' END,"
				+ "CASE a6011 WHEN '1' THEN '��' WHEN '0' THEN '��' END,CASE a6012 WHEN '1' THEN '��' WHEN '0' THEN '��' END,"
				+ "CASE a6013 WHEN '1' THEN '��' WHEN '0' THEN '��' END,CASE a6014 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6015,"
				+ "CASE a6016 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6017 FROM A60 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("xuantiaosheng.dogridquery")
	public int xuantiaosheng(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a2970,a2970A a2970a,a2970B a2970b,a6104,a2970C a2970c,a6107,a6108,a6109,a6110,a6111,"
				+ "CASE a6112 WHEN '1' THEN '��' WHEN '0' THEN '��' END,CASE a6113 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6114,"
				+ "CASE a6115 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6116 FROM A61 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("linxuan.dogridquery")
	public int linxuan(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a2950,a6202,a6203,a6204,a6205 FROM A62 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("gongkaixtxx.dogridquery")
	public int gongkaixtxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a2951,a6302,a6303,a6304,a6305,a6306,"
				+ "CASE a6307 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6308,"
				+ "CASE a6309 WHEN '1' THEN '��' WHEN '0' THEN '��' END,a6310 FROM A63 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("kaoshixx.dogridquery")
	public int kaoshixxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a6401,a6402,a6403,a6404,a6405,a6406,a6407,a6408 FROM A64 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	/**
	 * �Զ����ѯ�������б�����
	 * @throws AppException 
	 */
	@PageEvent("query_config")
	public int query_config() throws RadowException, AppException {
		
		//String jsonp = this.request.getParameter("jsonp");
		//System.out.println(jsonp);
//		if(jsonp!=null&&!"".equals(jsonp)){
//			try {
//				CommSQL.updateA01_config(jsonp,SysManagerUtils.getUserId());
//			} catch (AppException e) {
//				this.setMainMessage("����ʧ�ܣ�"+e.getDetailMessage());
//				e.printStackTrace();
//				return EventRtnType.FAILD;
//			}
//		}
		String qvid=this.request.getParameter("qvid");
		String viewnametb=HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='"+qvid+"'");
		String sessionid=this.request.getSession().getId();
		HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='"+sessionid+"' ");
		
		//(@i:=@i+1) as i from (select @i:=0) as it,
		if(DBUtil.getDBType()==DBType.ORACLE){	
			HBUtil.executeUpdate("insert into A01SEARCHTEMP (A0000,SESSIONID,SORT) select a01a0000,'"+sessionid+"',rownum i from (select a01a0000 from  "+viewnametb+" group by a01a0000 )");
			
		}else{			//mysql
			HBUtil.executeUpdate("insert into A01SEARCHTEMP (A0000,SESSIONID,SORT) select a01a0000,'"+sessionid+"',(@i:=@i+1) as i from (select @i:=0) as it, (select a01a0000 from  "+viewnametb+" group by a01a0000 ) tt");
		}
		
		StringBuilder cm = new StringBuilder("[");
		StringBuilder dm = new StringBuilder("[");
		cm.append("new Ext.grid.RowNumberer({locked:true,header:'',width:23}), "
				+ "{locked:true,header: \"<div class='x-grid3-check-col-td'><div class='x-grid3-check-col' alowCheck='true' id='selectall_peopleInfoGrid_personcheck' onclick='odin.selectAllFuncForE3(\\\"peopleInfoGrid\\\",this,\\\"personcheck\\\");getCheckList(\\\"peopleInfoGrid\\\",\\\"personcheck\\\",this);'></div></div>\","
				+ "hidden:false,align:'center', width: 40, sortable: false, enterAutoAddRow:false,"
				+ "renderer:function(value, params, record,rowIndex,colIndex,ds){var rtn = '';params.css=' x-grid3-check-col-td';if(value==true || value=='true'){rtn=\"<div class='x-grid3-check-col-on' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}else{rtn=\"<div class='x-grid3-check-col' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}return odin.renderEdit(rtn,params,record,rowIndex,colIndex,ds);}, "
				+ "editable:false,  hideable: false, dataIndex: 'personcheck', editor: new Ext.form.Checkbox({inputType:'',fireKey:odin.doAccSpecialkey})},");
		dm.append("{name: 'personcheck'},{name: 'a0000'},");
		int i = 0;
		HBSession sess = HBUtil.getHBSession();
		String sql="select "
				+ "concat(tblname,fldname) fldname1,"
				+ "fldnamenote,"
				+ "(select code_type from code_table_col where table_code=tblname and col_code=fldname) code_type  "
				+ " from qryviewfld t where qvid='"+qvid+"' and fldname!='a0000' "
						+ " order by "+CommonSQLUtil.to_number("fldnum")+" asc";
		List<Object[]> list=sess.createSQLQuery(sql).list();
		for(Object[] o : list){
			String name = o[0].toString().toLowerCase();
			String header = o[1].toString();
			//String width = o[3].toString();
			String width ="100";
			String codeType = o[2]==null||"null".equals(o[2])||"".equals(o[2].toString().trim())
					?"":o[2].toString().toUpperCase();
			//String editor = o[5].toString().toLowerCase();
			String editor = "text";
			if(codeType!=null&&codeType.trim().length()!=0){
				editor = "select";
			}else{
				editor = "text";
			}
			//String desc = o[6].toString();
			//String renderer = o[7]==null?"":o[7].toString();
			String renderer = "";
			//String align = o[9].toString();
			String align ="left";
			if(!"".equals(renderer)){
				//renderer = "var v="+renderer+"(a,b,c,d,e,f);";
				renderer = "function(a,b,c,d,e,f){var v="+renderer+"(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}else{
				renderer = "function(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}
			boolean locked =false;
			if("a01a0101".equals(name)){
				locked = true;
			}
		//	i++;
			if(!"a01a0000".equals(name)){
				if("text".equals(editor)){
					cm.append("{locked:"+locked+",header: \""+header+"\",hidden:false,align:'"+align+"', width: "+width+", sortable: true,"
							+ " enterAutoAddRow:false,renderer:"+renderer+", editable:false, "
							+ " dataIndex: '"+name+"', editor: new Ext.form.TextField({allowBlank:true ,fireKey:odin.doAccSpecialkey})},");
				
					dm.append("{name: '"+name+"'},");

				}else if("select".equals(editor)){
					cm.append("{locked:"+locked+",header: \""+header+"\",hidden:false,align:'"+align+"', width: "+width+", sortable: true, "
							+ "enterAutoAddRow:false,renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('peopleInfoGrid','"+name+"',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}, editable:false,  dataIndex: '"+name+"', "
							+ "editor: new Ext.form.ComboBox({store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:"
							+CodeType2js.getCodeTypeJS(codeType)
							+","
							+ "createFilterFn:odin.createFilterFn}),displayField:'value',typeAhead: false,mode: 'local',triggerAction: 'all',"
							+ "editable:true,selectOnFocus:true,hideTrigger:false,expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},allowBlank:true ,fireKey:odin.doAccSpecialkey})},");
				
					dm.append("{name: '"+name+"'},");
				}
				
			}
			
		}
		cm.deleteCharAt(cm.length()-1);
		cm.append("]");
		dm.deleteCharAt(dm.length()-1);
		dm.append("]");
		this.request.getSession().setAttribute("queryType", "define");//�����Զ����ѯ�б��־
		StringBuffer sb=new StringBuffer();
		sb.append(cm.toString()+"{split}"+dm.toString());
		this.getExecuteSG().addExecuteCode(sb.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static String getGllb2(){
		if("40288103556cc97701556d629135000f".equals(SysManagerUtils.getUserId())){
			return "";
		}else{
			String sql_s = "SELECT managerid  FROM COMPETENCE_USERMANAGER t WHERE USERID = '"+SysManagerUtils.getUserId()+"' and t.type = '1'";
			Object obj = HBUtil.getHBSession().createSQLQuery(sql_s).uniqueResult();
			String value = "";
			if(obj != null){
				value = obj.toString().replaceAll("\\'", "");
			}
			String temp="";
			if(!"".equals(value)){
				String[] s = value.split(",");
				for(int i=0;i<s.length;i++){
					String v = s[i];
					temp=temp+"'"+v+"',";
				}
			}
			if(!"".equals(temp)){
				temp=temp.substring(0, temp.length()-1);
				temp=" (select a0000 from a01 where a0165 in ("+temp+") ) ";
			}else{
				temp=" (select a0000 from a01 where 1=2 ) ";
			}
			return temp;
		}
	}
	
	
	@PageEvent("gongwuyuanxx.dogridquery")
	public int gongwuyuanxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a6801,a6802,a6803,a6804 FROM A68 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("zhiwuxx.dogridquery")
	public int zhiwuxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a6901,a6902,a6903,a6904 FROM A69 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("beizhuxx.dogridquery")
	public int beizhuxxQuery(int start, int limit) throws RadowException {
		String a0000s = this.getPageElement("a0000s").getValue();
		
		String sql = "SELECT a7101 FROM A71 WHERE A0000 = '";
		if(a0000s!=null && !"".equals(a0000s)){
			sql = sql + a0000s +"' ORDER BY A0000";
		}else{
			sql = sql + "1' ORDER BY A0000";
		}
		
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/*@PageEvent("peopleInfoGrid.rowclick")
	@GridDataRange
	public int persongridOnRowClick() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		int index = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		this.getExecuteSG().addExecuteCode("getCheckList3('"+index+"')");
		return EventRtnType.NORMAL_SUCCESS;	
	}*/
	
	/**
	 * �޸���Ա��Ϣ��˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			
			//initA0000Map(a0000);
			//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			//this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
	
	public static String getNewSQL(String userID, String a0201bsql, String orgidsql){//GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age,
		return "select  a01.a0000, a0101, a0104,  a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		",a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from " +
		" A01 a01,a02 a02,competence_userdept cu " +
		" where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+userID+"' "+a0201bsql+orgidsql;
	}
	public static String getNewSQLComQuery(String userID){
		return "select  a01.a0000, a0101, a0104, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		",a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from " +
		" A01 a01,a02 a02,competence_userdept cu " +
		" where a01.a0000 = a02.a0000 AND a02.A0201B = cu.b0111 AND cu.userid = '"+userID+"' ";
	}
	
	private int querybyid(String id) throws RadowException, AppException {
		String sid = this.request.getSession().getId();
		String userID = SysManagerUtils.getUserId();
		String isContain = this.getPageElement("isContain").getValue();
		this.getPageElement("tabn").setValue("tab1");
		String a0201bsql = "";
		//String a01Orgidsql = "";
		String is_use_a0000_idx_sql = "";
		int idLength = id.length();
		if("1".equals(isContain)){//�Ƿ�����¼�
			this.request.getSession().setAttribute("isContainCQ", true);
			//CommSQL.getComFields();
			a0201bsql = CommSQL.subString("a02.a0201b", 1, idLength,id);
			//a01Orgidsql = CommSQL.subString("cu.b0111", 1, idLength,id);
			String[] jgid = id.split("\\.");
			//yinl a02�����������ӷ������� 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			//is_use_a0000_idx_sql = CommSQL.concat("a01.a0000","''");
			
			is_use_a0000_idx_sql = "a01.a0000";
		}else{
			this.request.getSession().setAttribute("isContainCQ", false);
			a0201bsql = "a02.a0201b='"+id+"'";
			//a01Orgidsql = "a01.orgid='"+id+"'";
			String[] jgid = id.split("\\.");
			//yinl a02�����������ӷ������� 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			is_use_a0000_idx_sql = "a01.a0000";
		}
		String radioC = this.getPageElement("radioC").getValue();
		String hasQueried = this.getPageElement("sql").getValue();
		if(!"1".equals(radioC)){
			if("".equals(hasQueried) || hasQueried==null)
				throw new AppException("δ���й���ѯ���Ȳ�ѯ!");
		}
		String xzry = this.getPageElement("xzry").getValue();		//��ְ��Ա
		String lsry = this.getPageElement("lsry").getValue();		//��ʷ��Ա
		String ltry = this.getPageElement("ltry").getValue();		//������Ա
		String paixu = "1"; //this.getPageElement("paixu").getValue();		//��ְ��������(Ĭ��1)
		String mllb = this.getPageElement("mllb").getValue();		//�������
		Map<String, Boolean> m = new HashMap<String, Boolean>();
		m.put("xzry", "1".equals(xzry));
		m.put("lsry", "1".equals(lsry));
		m.put("ltry", "1".equals(ltry));
		m.put("paixu", "1".equals(paixu));
		m.put("isContain", "1".equals(isContain));
		this.request.getSession().setAttribute("queryConditionsCQ", m);
		this.request.getSession().setAttribute("queryConditionsMLLB", mllb);
		String sql=CommSQL.getComSQL(sid)+" where 1=1 ";
		
		String a0163sql = "";
		String a0163 = this.getPageElement("personq").getValue();
		if("2".equals(a0163)){
			a0163sql = " and a0163 in('2','21','22','23','29')";
		}else if("".equals(a0163)){
			
		}else{
			a0163sql = " and a0163='"+a0163+"'";
		}
		
		
		//1��ְ��Ա 2������Ա 3������Ա 4��ȥ�� 5������Ա       ������ְ��Ա  ��ʷ��Ա��ɾ����  ������Ա ְ��Ϊ�յ�������ְ��Ա
		if("X001".equals(id)){//������ְ��Ա
			//sql=sql+ " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
			//		+ " and (a01.status!='4' or a01.status is  null) " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
			sql=sql+ " and  A0000 not in (select A0000  from  A02) and A0101 is not null  "
							+ " and (a01.status!='4' or a01.status is  null) " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
					
			
			
			
			//this.request.getSession().setAttribute("queryTypeEX", "����");
			/*sql=sql+" and  exists (select 1 from a02   where a02.a0000=a01.a0000) " +
					" and not exists " +
					" (select 1 from a02   where a02.a0000=a01.a0000 and (a02.a0201b !='-1' and a0255='1')) " +//ְ��Ϊ������λ����ְΪ��ְ�� ����Ҫѡ����
					"   and a01.status='1' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : ""); */
			
			/*sql=sql+ " and (  NOT EXISTS (SELECT 1 FROM a02 WHERE	a02.a0000 = a01.a0000	OR a02.A0201B IS NULL	) or ( not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : ""+"))");*///������ְ����Ա��ֻҪ��a02��û����Ϣ����a02�е�a0201b��...�����ൽ��ְ����Ա��sucf���ݶ���������
			//�����¼� ��� ������ְ��Ա ʱ������ ְ��Ϊ�յ�������ְ��Ա ����Ա
			/*if("1".equals(isContain)){
				sql= sql + " union " + CommSQL.getComSQL(sid)+ " where 1=1 and not exists (select 1 from a02   where a02.a0000=a01.a0000)   and a01.status!='4' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
			}*/
		}else if("X002".equals(id)){
			sql=sql+ " and   A0000  in  (select distinct A0000  from  A02  where  A0201B is  null)  "
				+ " and (a01.status!='4' or a01.status is  null) " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
			
			
		}
		
		
		
		/*else if("X0010".equals(id)){//ְ��Ϊ�յ�������ְ��Ա
			this.request.getSession().setAttribute("queryTypeEX", "����");
			sql=sql+ " and not exists (select 1 from a02   where a02.a0000=a01.a0000)   and a01.status!='4' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
		}*/else{
			//this.request.getSession().setAttribute("queryTypeEX", "�¸Ĳ�ѯ��ʽ");
			
			sql = sql + " and a01.status!='4' " +  (!"".equals(mllb) ? "  and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
					+ " and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a0281='true' "
					+ "  and "+a0201bsql+")";
			
			
			
			/*if(m.get("xzry")){
				sql = sql + " and a01.status = '1' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ " and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
						+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') "
						+ "and a02.a0255 = '1'   and "+a0201bsql+")";
			}else{
				sql = sql + " and 1=2";
			}
			if(m.get("ltry")&&m.get("lsry")){
				sql = sql + " UNION ALL ("+CommSQL.getComSQL(sid)+"where a01.orgid in "
						+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"' "
								+ " and "+a01Orgidsql+") AND (a01.status = '3' OR a01.status = '2') " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ ")";
			}else if(m.get("ltry")){
				sql = sql + " UNION ALL ("+CommSQL.getComSQL(sid)+"where a01.orgid in "
						+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"' "
								+ " and "+a01Orgidsql+") AND a01.status = '3' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ ")";
			}else if(m.get("lsry")){
				sql = sql + " UNION ALL ("+CommSQL.getComSQL(sid)+"where a01.orgid in "
						+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"' "
								+ " and "+a01Orgidsql+") AND a01.status = '2' " + (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ ")";
			}else{
				
			}
			sql = "select * from ("+sql +") a01 where 1=1 ";*/
		}
		
		CommonQueryBS.systemOut("++++++++++++"+sql.toString());
		this.getPageElement("sql").setValue(sql);
        this.getPageElement("a0201b").setValue(id);//˫����ѡ�еģ�����id	
        //this.getExecuteSG().addExecuteCode("showgrid()");			//���µ�������л����б�ҳ�棬��С���Ϻ���Ƭ����
        
       // this.getExecuteSG().addExecuteCode("refreshPerson()");
        
//        String shsql="select A01.A0000,A0190,A0189 from A01 right join ("+sql+")m on A01.A0000=m.A0000";
//        String str11=setShenHeValue(shsql);
        
//      	this.getPageElement("shinfo").setValue(str11);
        
        
        this.getExecuteSG().addExecuteCode("document.getElementById('orgjson').value=''");
		this.setNextEventName("peopleInfoGrid.dogridquery");
				
		String spsql = "select b0241 from b01 where b0111='"+id+"'";
		//HBSession hs = HBUtil.getHBSession();
		/*CommQuery query = new CommQuery();
		List<HashMap<String,Object>> li = query.getListBySQL(spsql);
		HashMap<String,Object> map = new HashMap<String,Object>();
		if(li.size()!=0){
			map = li.get(0);
			if(map.get("b0241")!=null){
				String b0241 = map.get("b0241").toString();
				this.getPageElement("sp").setValue(b0241);
			}else{
				this.getPageElement("sp").setValue("");
			}
		}else{
			this.getPageElement("sp").setValue("");
		}*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������治��ɾ���������鿴��Ա
	 * @return
	 */
	@PageEvent("delete_per_search")
	public int delete_per_search(String unit_id){
		if(unit_id==null||unit_id.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�������ѯ�¼�
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {
		this.getPageElement("checkedgroupid").setValue(id);
		this.request.getSession().setAttribute("queryType", "1");
		String isContain = this.getPageElement("isContain").getValue();
		
		String radioC=this.getPageElement("radioC").getValue();
		//��queryType = 1��isContain = 0�����ң����ñ�־isA0225 = 1
		if(isContain != null && isContain.equals("0") && radioC.equals("1")){
			this.request.getSession().setAttribute("isA0225", "1");
		}
		
		//��ʼ���Զ�����ʾ�е�����
		String userid = SysManagerUtils.getUserId();
		CommSQL.initA01_config(userid);
		
		this.getPageElement("isContainHidden").setValue(isContain);
		String userID = SysManagerUtils.getUserId();
		String codevalue = this.getPageElement("checkedgroupid").getValue().toString();
		//====�ѻ���id����OtherTemShowTowPageModel ������ ��������===============
		request.getSession().setAttribute("listofname", codevalue);
		//========================================================================
		Map<String, String> map = PublicWindowPageModel.isHasRule(codevalue, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))){
				this.setMainMessage("��û�иû�����Ȩ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(1==1){
			return querybyid(id);
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ���б�ҳ��
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("loadList.onclick")
	@NoRequiredValidate
	public int loadList()throws RadowException, AppException{
		//this.openWindow("win2", "pages.customquery.QueryList");
		this.getExecuteSG().addExecuteCode("$h.openWin('win2','pages.customquery.QueryList','��Ա���б�',560,500,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �򿪱���ҳ��
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("saveList.onclick")
	@NoRequiredValidate
	public int saveList()throws RadowException, AppException{
		
		String hasQueried = this.getPageElement("sql").getValue();
		if("".equals(hasQueried) || hasQueried==null){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//�ж��б��Ƿ�������
		List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ��������ݣ�',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//this.openWindow("win3", "pages.customquery.ListSaveWin");
		this.getExecuteSG().addExecuteCode("$h.openWin('win3','pages.customquery.ListSaveWin','�����б�',560,200,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �����б�
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("doSaveList")
	@NoRequiredValidate
	@Transaction
	public int doSaveList()throws RadowException, AppException, UnsupportedEncodingException, SerialException, SQLException{
		
		String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
		String cqli = this.getPageElement("cqli").getValue();
		String saveName = this.getPageElement("saveName").getValue();
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		cbBs.saveList(saveName, "", "", loginName,sql,cqli);
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ���棺��ע����Χ��ѯ�д���Ĵ�С�������߼��ĸߵ������෴�������ж��߼�Ҳ���෴�Ĵ���
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveCon.onclick")
    @Transaction
	public int saveCon() throws RadowException, AppException{
		StringBuffer sb = new StringBuffer("");
		StringBuffer b01Ids = new StringBuffer("");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();//������Ϣ
		b01String=b01String.replace("|", "'").replace("@", ",");
		//b01String=b01String.replace("{", "").replace("}", "");
			String a0101 = this.getPageElement("a0101A").getValue();//��Ա����
			String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//��Ա���֤
			String a0160 = this.getPageElement("a0160").getValue();//��Ա���
			String a0163 = this.getPageElement("a0163").getValue();//��Ա״̬
			String age = this.getPageElement("ageA").getValue();//��ʼ����
			String age1 = this.getPageElement("age1").getValue();//��������
			String female = this.getPageElement("female").getValue();//�Ա��Ƿ�Ů
			if(female.equals("1")){
				female = "2";
			}
			String minority = this.getPageElement("minority").getValue();//�Ƿ���������
			String nonparty = this.getPageElement("nonparty").getValue();//�Ƿ���й���Ա	
			String duty = this.getPageElement("duty").getValue();//��ʼְ����
			String duty1 = this.getPageElement("duty1").getValue();//����ְ����
			String dutynow = this.getPageElement("dutynow").getValue();//ְ������ʼ����
			String dutynow1 = this.getPageElement("dutynow1").getValue();//ְ���ν�������
			String a0219 = this.getPageElement("a0219").getValue();//ְ�����
			String a0221aS = this.getPageElement("a0221aS").getValue();//ְ��ȼ�
			String a0221aE = this.getPageElement("a0221aE").getValue();//ְ��ȼ�			
			String a0192dS = this.getPageElement("a0192dS").getValue();//ְ��ְ��
			String a0192dE = this.getPageElement("a0192dE").getValue();//ְ��ְ��
			String edu = this.getPageElement("edu").getValue();//��ʼѧ��
			String edu1 = this.getPageElement("edu1").getValue();//����ѧ��

			String allday = this.getPageElement("allday").getValue();//�Ƿ�ȫ����
			if(!"1".equals(allday)){
//				allday="2";
				allday="";  //���������Ϊ��δ��ѡ�����  ѧ���Ƿ�ȫ�������� ��bug��ţ�708��  
			}
			
			//��ΧУ�� start mengl 20160630
			if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("���䷶Χ����ȷ�����飡");
				}
			}
			
			if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
				CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
				CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
				if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
					throw new AppException("ְ���η�Χ������ͬһ������飡");
				}
				//ְ���� ֵԽС ������˼Խ�߼�
				if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
					throw new AppException("ְ���η�Χ����ȷ�����飡");
				}
			}
			
			if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
				if(dutynow.compareTo(dutynow1)>0){
					throw new AppException("����ְ����ʱ�䷶Χ����ȷ�����飡");
				}
			}

			if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
				CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
				CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
				//TODO ְ��ȼ���Χ�Ƿ�У�� ����
				if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
					throw new AppException("ְ��ȼ���Χ������ͬһ������飡");
				}
				//ְ��ȼ� ֵԽС ������˼Խ�߼�
				if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
					throw new AppException("ְ��ȼ���Χ����ȷ�����飡");
				}
			}
			
			if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
				CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
				CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
				if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
					throw new AppException("ְ����Χ������ͬһ������飡");
				}
				//ְ�� ֵԽС ������˼Խ�߼�
				if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
					throw new AppException("ְ����Χ����ȷ�����飡");
				}
			}
			
			if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
				CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
				CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
				/*if(!eduCodeValue.getSubCodeValue().equalsIgnoreCase(edu1CodeValue.getSubCodeValue())){
					throw new AppException("ѧ����Χ������ͬһ������飡");
				}*/
				//ְ�� ֵԽС ������˼Խ�߼�
				if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
					throw new AppException("ѧ����Χ����ȷ�����飡");
				}
			}
			
			
			//��ΧУ�� end
			
			
			StringBuffer customData=new StringBuffer("");//�༭��������ʾ����
			
//			sb.append("select  a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000 ");
			sb.append(getcommSQL() + " where 1=1 ");

			if (!a0101.equals("")){
				sb.append(" and ");
				sb.append("a01.a0101 like '"+a0101+"%'");
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+a0101+"','logicSymbols':' and ','colValues':'"+a0101+"','colNamesValue':'A0101','colNames':'��Ա����','leftBracket':'','rightBracket':''}");
			}
			if (!a0184.equals("")){
				sb.append(" and ");
				sb.append("a01.a0101 like '"+a0184+"%'");
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+a0184+"','logicSymbols':' and ','colValues':'"+a0184+"','colNamesValue':'A0101','colNames':'��Ա����','leftBracket':'','rightBracket':''}");
			}
			if (!a0160.equals("")){
				sb.append(" and ");
				sb.append("a01.a0160 ='"+a0160+"'");
				String colValueView=cbBs.getAaa103("A0160", a0160,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0160+"','colNamesValue':'A0160','colNames':'��Ա���','leftBracket':'','rightBracket':''}");
			}
			if (!a0163.equals("")){
				sb.append(" and ");
				sb.append("a01.a0163='"+a0163+"'");
				String colValueView=cbBs.getAaa103("A0163", a0163,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0163+"','colNamesValue':'A0163','colNames':'��Ա״̬','leftBracket':'','rightBracket':''}");				
			}
			if (!age.equals("")){
				sb.append(" and ");
				sb.append("a01.age>="+age);
				
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+age+"','logicSymbols':' and ','colValues':'"+age+"','colNamesValue':'AGE','colNames':'����','leftBracket':'','rightBracket':''}");								
			}
			if (!age1.equals("")){
				sb.append(" and ");
				sb.append("a01.age<="+age1);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+age1+"','logicSymbols':' and ','colValues':'"+age1+"','colNamesValue':'AGE','colNames':'����','leftBracket':'','rightBracket':''}");							
			}
			if (!female.equals("0")){
				sb.append(" and ");
				sb.append("a01.a0104='"+female+"'");
				String colValueView=cbBs.getAaa103("A0104", female,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+female+"','colNamesValue':'A0104','colNames':'�Ա�','leftBracket':'','rightBracket':''}");				
				
			}
			if (!minority.equals("0")){
				sb.append(" and ");
				sb.append("a01.a0117!='01'");
				String colValueView=cbBs.getAaa103("A0117","01",cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'01','colNamesValue':'A0117','colNames':'����','leftBracket':'','rightBracket':''}");								
			}
			if (nonparty.equals("1")){
				sb.append(" and ");
				sb.append("a01.a0141!='01'");
				String colValueView=cbBs.getAaa103("A0141","01",cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'01','colNamesValue':'A0141','colNames':'������ò','leftBracket':'','rightBracket':''}");												
			}
            if(!"".equals(duty1)){
				sb.append(" and ");
//				sb.append("a01.a0148<='"+duty1+"'");
				sb.append("a01.a0148>='"+duty1+"'");
				String colValueView=cbBs.getAaa103("A0148", duty1,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+duty1+"','colNamesValue':'A0148','colNames':'ְ����','leftBracket':'','rightBracket':''}");								
            }
            if(!"".equals(duty)){
				sb.append(" and ");
//				sb.append("a01.a0148>='"+duty+"'");	
				sb.append("a01.a0148<='"+duty+"'");	
				String colValueView=cbBs.getAaa103("A0148", duty,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+duty+"','colNamesValue':'A0148','colNames':'ְ����','leftBracket':'','rightBracket':''}");												
            }
            if(!"".equals(a0192dE)){
				sb.append(" and ");
				sb.append("a01.a0192d>='"+a0192dE+"'");
				String colValueView=cbBs.getAaa103("A0192D", a0192dE,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0192dE+"','colNamesValue':'A0192D','colNames':'ְ��ְ��','leftBracket':'','rightBracket':''}");				
            }
            if(!"".equals(a0192dS)){
				sb.append(" and ");
				sb.append("a01.a0192d<='"+a0192dS+"'");
				String colValueView=cbBs.getAaa103("A0192D", a0192dS,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0192dS+"','colNamesValue':'A0192D','colNames':'ְ��ְ��','leftBracket':'','rightBracket':''}");								
            }            
            if(!"".equals(a0221aS)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a<='"+a0221aS+"')");	
				String colValueView=cbBs.getAaa103("A0221A", a0221aS,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0221aS+"','colNamesValue':'A0221A','colNames':'ְ��ȼ�','leftBracket':'','rightBracket':''}");												
            }
            if(!"".equals(a0221aE)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a>='"+a0221aE+"')");
				String colValueView=cbBs.getAaa103("A0221A", a0221aE,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0221aE+"','colNamesValue':'A0221A','colNames':'ְ��ȼ�','leftBracket':'','rightBracket':''}");																
            }            
            if(!"".equals(dutynow)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288>='"+dutynow+"')");
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'"+dutynow+"','logicSymbols':' and ','colValues':'"+dutynow+"','colNamesValue':'A0288','colNames':'ְ������ʼ����','leftBracket':'','rightBracket':''}");																				
            }
            if(!"".equals(dutynow1)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288<='"+dutynow1+"')");	
				String colValueView=cbBs.getAaa103("A0288", dutynow1,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'"+dutynow1+"','logicSymbols':' and ','colValues':'"+dutynow1+"','colNamesValue':'A0288','colNames':'ְ���ν�������','leftBracket':'','rightBracket':''}");																							
            }
            if(!"".equals(a0219)){
				sb.append(" and ");
//				sb.append("a02.a0219='"+a0219+"'");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0219='"+a0219+"')");
				String colValueView=cbBs.getAaa103("A0219", a0219,cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A02','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+a0219+"','colNamesValue':'A0219','colNames':'ְ�����','leftBracket':'','rightBracket':''}");							
            }
            if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
    			//ѡ�����
    			JSONObject jsonObject = JSONObject.fromObject(b01String);
    			sb.append("and exists (select 1 from a02 where a02.a0000=a01.a0000 ");
    			sb.append(" and (1=2 ");
    			Iterator<String> it = jsonObject.keys();
    			// ����jsonObject���ݣ���ӵ�Map����
    			while (it.hasNext()) {
    				String nodeid = it.next(); 
    				String operators = (String) jsonObject.get(nodeid);
    				String[] types = operators.split(":");
    				if("true".equals(types[1])&&"true".equals(types[2])){
    					sb.append(" or a02.A0201B like '"+nodeid+"%' ");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid,cbBs.ctcList);
    					customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+"','colNamesValue':'B0111','colNames':'��λ','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    					/*String sql = "select a02.A0201B from a02 a02 where a02.A0201B like '"+nodeid+"%'";
    					List<Object> b01S = HBUtil.getHBSession().createSQLQuery(sql).list();
    					int count = 0;
    					for(Object o:b01S){
    						String leftBracket="";
    						String rightBracket="";
    						String logicSymbols=" or ";
    						String o1 = "";
    						if(count==0){
    							leftBracket="(";
    						}
    						if(count==(b01S.size()-1)){
    							rightBracket=")";
    							logicSymbols=" and ";
    						}
    						if(o!=null&&!"".equals(o)){
    							o1=o.toString();
    						}
    						String colValueView=cbBs.getAaa103("B0111",o1,cbBs.ctcList);
        					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+o1+"','colNamesValue':'B0111','colNames':'��λ','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    						count++;
    					}*/
    				}else if("true".equals(types[1])&&"false".equals(types[2])){
    					sb.append("or a02.A0201B like '"+nodeid+".%'");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid+".",cbBs.ctcList);
    					customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+".','colNamesValue':'B0111','colNames':'��λ','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    				}else if("false".equals(types[1])&&"true".equals(types[2])){
    					sb.append("or a02.A0201B = '"+nodeid+"'");
    					String leftBracket="";
						String rightBracket="";
						String logicSymbols=" or ";
						leftBracket="(";
						rightBracket=")";
						logicSymbols=" and ";
						String colValueView=cbBs.getAaa103("B0111",nodeid,cbBs.ctcList);
    					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+nodeid+"','colNamesValue':'B0111','colNames':'��λ','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
    				}
    			}
    			sb.append(" ) ");
    			sb.append(" ) ");
				//sb.append(" and ");
//				sb.append("a02.A0201B in ("+b01String+")");	
				//sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.A0201B in ("+b01String+"))");	
				
				//String [] arrB01=b01String.split(",");
				/*if(arrB01.length>200){
					throw new AppException("����ѡ�񳬹�200����λ��");
				}
				
				for(int i=0;i<arrB01.length;i++){
					String b0111=arrB01[i];
					String leftBracket="";
					String rightBracket="";
					String logicSymbols=" or ";
					if(i==0){
						leftBracket="(";
					}
					if(i==(arrB01.length-1)){
						rightBracket=")";
						logicSymbols=" and ";
					}
					String colValueView=cbBs.getAaa103("B0111", b0111.replace("'", ""),cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+b0111.replace("'", "")+"','colNamesValue':'B0111','colNames':'��λ','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");												
				}*/
            }
            if(!"".equals(edu)&&"".equals(edu1)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
	            
				String colValueView=cbBs.getAaa103("A0801B", edu,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu+"','colNamesValue':'A0801B','colNames':'ѧ��','leftBracket':'(','rightBracket':''}");
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'�Ƿ�ȫ����','leftBracket':'','rightBracket':')'}");													      				
				}
					
			}
            if(!"".equals(edu1)&&"".equals(edu)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
				String colValueView=cbBs.getAaa103("A0801B", edu1,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu1+"','colNamesValue':'A0801B','colNames':'ѧ��','leftBracket':'(','rightBracket':''}");
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'�Ƿ�ȫ����','leftBracket':'','rightBracket':')'}");													      					            
				}
			}
            if(!"".equals(edu1)&&!"".equals(edu)){
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
	            if(!"".equals(allday)){
					sb.append(" and ");
					sb.append("a0837 = '"+allday+"'");		
	            }
	            sb.append(")");
	            
				String colValueView=cbBs.getAaa103("A0801B", edu,cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu+"','colNamesValue':'A0801B','colNames':'ѧ��','leftBracket':'(','rightBracket':''}");
				colValueView=cbBs.getAaa103("A0801B", edu1,cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+edu1+"','colNamesValue':'A0801B','colNames':'ѧ��','leftBracket':'','rightBracket':''}");				
				if(!StringUtil.isEmpty(allday)){
					colValueView=cbBs.getAaa103("A0837", allday,cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'"+colValueView+"','logicSymbols':' and ','colValues':'"+allday+"','colNamesValue':'A0837','colNames':'�Ƿ�ȫ����','leftBracket':'','rightBracket':')'}");													      				
				}
            }
            String data="";
            if(!StringUtil.isEmpty(customData.toString()) && customData.length()>0){
            	data =	"["+customData.toString().substring(1, customData.length()).replace("'", "\"")+"]";
            }else{
            	data = "[]";
            }
            
            //lzy--update--2017.05.24
            sb.append(" and a01.status = '1' and concat(a01.a0000, '') in (select a02.a0000 from a02 where a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+SysManagerUtils.getUserId()+"') and a02.a0255 = '1' )");
            cbBs.delComm();
            cbBs.saveOrUodateCq("", "��������", sb.toString(), "", SysUtil.getCacheCurrentUser() .getLoginname(), data); 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	

	
	@PageEvent("duty.onchange")
	@NoRequiredValidate
	@AutoNoMask
	public int eab025change() throws RadowException{
		String str=this.getPageElement("duty").getValue();
		PageElement pe=this.createPageElement("duty1",ElementType.SELECT,false);
		String arg0 = "substr(aaa102,0,2)='"+str.substring(0, 2)+"'";
//		String arg0 = "aaa102='132'";
		pe.loadDataForSelectStore("ZB09","",arg0,true);
		this.getPageElement("duty1").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("clear.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int clear() throws RadowException{
        this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//@PageEvent("radioC.onclick")
	@NoRequiredValidate
	@AutoNoMask
	public int radioC() throws RadowException, AppException{
		String radioC=this.getPageElement("radioC").getValue();
		/*if("4".equals(radioC)){
			this.setMainMessage("���ڽ�����");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String sql=this.getPageElement("sql").getValue();
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("δ���й���ѯ���Ȳ�ѯ!");
		    String additionalSql=(String) this.request.getSession().getAttribute("additionalSql");
		    if(additionalSql==null||"".equals(additionalSql)){
		    	String b01String = this.getPageElement("orgjson").getValue();
				StringBuffer sb = new StringBuffer();
				if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
	    			//ѡ�����
	    			JSONObject jsonObject = JSONObject.fromObject(b01String);
	    			sb.append(" and exists (select 1 from a02, competence_userdept cu where a02.a0000=a01.a0000 and"
	    					+ " a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	    			sb.append(" and (1=2 ");
	    			Iterator<String> it = jsonObject.keys();
	    			// ����jsonObject���ݣ���ӵ�Map����
	    			while (it.hasNext()) {
	    				String nodeid = it.next(); 
	    				String operators = (String) jsonObject.get(nodeid);
	    				String[] types = operators.split(":");
	    				if("true".equals(types[1])&&"true".equals(types[2])){
	    					sb.append(" or cu.b0111 like '"+nodeid+"%' ");
	    				}else if("true".equals(types[1])&&"false".equals(types[2])){
	    					sb.append("or cu.b0111 like '"+nodeid+".%'");
	    				}else if("false".equals(types[1])&&"true".equals(types[2])){
	    					sb.append("or cu.b0111 = '"+nodeid+"'");
	    				}
	    			}
	    			sb.append(" ) ");
	    			sb.append(" ) and 1=1 ");
	    			
	            }else{
	            	//sb.append("  and 1=2 ");
	            	sb.append(" and exists (select 1 from a02, competence_userdept cu where a02.a0000=a01.a0000 and"
	    					+ " a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	            	sb.append(" ) ");
	            }
				sql=sql+sb.toString();
				this.request.getSession().setAttribute("additionalSql", sql);
		    }
			  
//		    this.getPageElement("additionalSql").setValue(sql);
		}else{
//			 this.getPageElement("additionalSql").setValue("");
			this.request.getSession().setAttribute("additionalSql", "");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("seachByName.onclick")
	public int seachByName() throws RadowException, AppException{
		StringBuffer sb = new StringBuffer("");
//		sb.append("select   a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000 ");
		sb.append(getcommSQL()+" where 1=1 ");
		
		String name=this.getPageElement("name").getValue();
		String isOnDuty=this.getPageElement("isOnDuty").getValue();
	
	    if(name!=null&&!"".equals(name)){ 	
		    sb.append(" and (a01.A0101 like '%"+name+"%' or a01.A0102 like '"+name.toUpperCase()+"%') ");
	    }else{
	    	throw new AppException("�������ѯ��Ա������");
	    }
		if(!"".equals(isOnDuty)){
			sb.append(" and exists (select 1 from A02 a02 where  a01.a0000=a02.a0000 and  a02.A0255='"+isOnDuty+"')");
		}
	    this.getPageElement("sql").setValue(sb.toString());
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	private int queryNew() throws RadowException, AppException{
		String userID = SysManagerUtils.getUserId();
		this.request.getSession().setAttribute("queryTypeEX", "�¸Ĳ�ѯ��ʽ");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		/*b01String=b01String.replace("|", "'").replace("@", ",");//��֯����
		String [] arrB01=b01String.split(",");
		if(arrB01!=null && arrB01.length>200){
			throw new AppException("����ѡ�񳬹�200����λ��");
		}*/
		StringBuffer a02_a0201b_sb = new StringBuffer("");
        StringBuffer cu_b0111_sb = new StringBuffer("");
        /*if(!"".equals(b01String)&&b01String!=null){
        	a02_a0201b_sb.append(" and a02.a0201b in ("+b01String+") ");
        	cu_b0111_sb.append(" and cu.b0111 in ("+b01String+") ");
            this.getPageElement("a0201b").setValue(b01String);					
        }*/
		//ѡ�����
        String tree = this.getPageElement("SysOrgTree").getValue();
        if(b01String!=null && !"".equals(b01String)&& 
        		tree!=null && !"".equals(tree.trim())){
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// ����jsonObject���ݣ���ӵ�Map����
			while (it.hasNext()) {
				String nodeid = it.next(); 
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[�������ƣ��Ƿ�����¼����Ƿ񱾼�ѡ��]
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
		//StringBuffer sb = new StringBuffer("");
		//sb.append(getNewSQLComQuery(userID));
		String a0101 = this.getPageElement("a0101A").getValue();//��Ա����
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//���֤��
		String a0160 = this.getPageElement("a0160").getValue();//��Ա���
		String a0163 = this.getPageElement("a0163").getValue();//��Ա״̬
		String ageS = this.getPageElement("ageA").getValue();//��ʼ����
		String ageE = this.getPageElement("age1").getValue();//��������
		String female = this.getPageElement("female").getValue();//�Ա��Ƿ�Ů
		if(female.equals("1")){
			female = "2";
		}
		String minority = this.getPageElement("minority").getValue();//�Ƿ���������
		String nonparty = this.getPageElement("nonparty").getValue();//�Ƿ���й���Ա	
		String duty = this.getPageElement("duty").getValue();//��ʼְ����
		String duty1 = this.getPageElement("duty1").getValue();//����ְ����
		String a0221aS = this.getPageElement("a0221aS").getValue();//ְ��ȼ�
		String a0221aE = this.getPageElement("a0221aE").getValue();//ְ��ȼ�			
		String a0192dS = this.getPageElement("a0192dS").getValue();//ְ��ְ��
		String a0192dE = this.getPageElement("a0192dE").getValue();//ְ��ְ��			
		String dutynow = this.getPageElement("dutynow").getValue();//ְ������ʼ����
		String dutynow1 = this.getPageElement("dutynow1").getValue();//ְ���ν�������
		String a0219 = this.getPageElement("a0219").getValue();//ְ�����
		String edu = this.getPageElement("edu").getValue();//��ʼѧ��
		String edu1 = this.getPageElement("edu1").getValue();//����ѧ��
		String jiezsj = this.getPageElement("jiezsj").getValue();//�������޼����ֹ
		String radioC = this.getPageElement("radioC").getValue();
		String allday = this.getPageElement("allday").getValue();//�Ƿ�ȫ����
		String sql = this.getPageElement("sql").getValue();
		//���䷶Χת�������ڷ�Χ
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyyMMdd" );
		Calendar calendar=Calendar.getInstance();   
		
		if(jiezsj==null||"".equals(jiezsj)){
			jiezsj = sdf.format(new Date());
		}
		String dstrat = "";
		String dend = "";
		try {
			if(jiezsj.length()==6){
				jiezsj = jiezsj + "01";
			}
			Date djiezsj = sdf.parse(jiezsj);
			calendar.setTime(djiezsj); 
			int iages=0,iagee=200;
			if(ageS!=null&&!"".equals(ageS)){
				iages = Integer.parseInt(ageS);
				calendar.add(Calendar.YEAR, -iages);
				dend = sdf.format(calendar.getTime());
				calendar.setTime(djiezsj); 
			}
			
			if(ageE!=null&&!"".equals(ageE)){
				iagee = Integer.parseInt(ageE)+1;
				calendar.add(Calendar.YEAR, -iagee);
				dstrat = sdf.format(calendar.getTime());
			}
			if(iagee < iages){
				this.setMainMessage("���䷶Χ����");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (NumberFormatException e2) {
			this.setMainMessage("�����ʽ����");
			return EventRtnType.NORMAL_SUCCESS;
		}catch (Exception e1) {
			this.setMainMessage("��ֹ���ڸ�ʽ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"1".equals(allday)){
//						allday="2";
			allday="";  //���������Ϊ��δ��ѡ�����  ѧ���Ƿ�ȫ�������� ��bug��ţ�708��  
		}
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("δ���й���ѯ���Ȳ�ѯ!");
		}
		//��ΧУ�� start mengl 20160630
		/*if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
			
			try {
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("���䷶Χ����ȷ�����飡");
				}
			} catch (NumberFormatException e) {
				throw new AppException("������ֵ����ȷ�����飡");
			}
		}*/
		
		if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("ְ���η�Χ������ͬһ������飡");
			}
			//ְ���� ֵԽС ������˼Խ�߼�
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("ְ���η�Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
			if(dutynow.compareTo(dutynow1)>0){
				throw new AppException("����ְ����ʱ�䷶Χ����ȷ�����飡");
			}
		}

		if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
			CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
			CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
			
			//TODO ְ��ȼ���Χ�Ƿ�У�����
			if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
				throw new AppException("ְ��ȼ���Χ������ͬһ������飡");
			}
			//ְ��ȼ� ֵԽС ������˼Խ�߼�
			if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
				throw new AppException("ְ��ȼ���Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
			CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
			CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
			if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
				throw new AppException("ְ����Χ������ͬһ������飡");
			}
			//ְ�� ֵԽС ������˼Խ�߼�
			if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
				throw new AppException("ְ����Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
			CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
			CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
			//ְ�� ֵԽС ������˼Խ�߼�
			if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
				throw new AppException("ѧ����Χ����ȷ�����飡");
			}
		}
		
		
		//��ΧУ�� end
		StringBuffer a01sb = new StringBuffer("");
		if (!a0101.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like'"+a0101+"%'");
		}
		if (!a0184.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like'"+a0184+"%'");
		}
		if (!a0160.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0160 ='"+a0160+"'");
		}
		/*if (!a0163.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0163='"+a0163+"'");
		}*/
		if(jiezsj.equals("")){
			jiezsj = DateUtil.getcurdate();
		}
		if (!dstrat.equals("")){
			a01sb.append(" and ");
			a01sb.append("a01.a0107>='"+dstrat+"'");
		}
		if (!dend.equals("")){
			a01sb.append(" and ");
			a01sb.append(" a01.a0107<='"+dend+"'");
		}
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
        if(!"".equals(a0192dE)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0192d>='"+a0192dE+"'");		
        }
        if(!"".equals(a0192dS)){
        	a01sb.append(" and ");
        	a01sb.append("a01.a0192d<='"+a0192dS+"'");		
        }       
        StringBuffer a02sb = new StringBuffer("");
        if(!"".equals(a0221aS)){
        	a02sb.append(" and ");
        	a02sb.append("a02.a0221a<='"+a0221aS+"'");
        }
        if(!"".equals(a0221aE)){
        	a02sb.append(" and ");
        	a02sb.append("a02.a0221a>='"+a0221aE+"'");
        }   
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
        
        String finalsql = CommSQL.getCondiQuerySQL(userID,a01sb,a02sb,a02_a0201b_sb,cu_b0111_sb,orther_sb,a0163,"0");
        
        this.getPageElement("sql").setValue("select * from ("+finalsql+") a01 where 1=1 ");
        Map<String, Boolean> m = new HashMap<String, Boolean>();
        m.put("paixu", true);
        this.request.getSession().setAttribute("queryConditionsCQ",m);
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��ѯ��ע����Χ��ѯ�д���Ĵ�С�������߼��ĸߵ������෴�������ж��߼�Ҳ���෴�Ĵ���
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQuery.onclick")
	public int query() throws RadowException, AppException{
		
		this.getPageElement("checkedgroupid").setValue(null);
		this.request.getSession().setAttribute("queryType", "1");
		StringBuffer sb = new StringBuffer("");
		if(1==1){
			return queryNew();
		}
		
		sb.append(getcommSQL()+" where " +
				" 1=1 ");//*where a.a0255='1' ���ò�ѯ�ɲ�����ְ��
	    
		
		//queryType = (String) this.request.getSession().getAttribute("queryType");
		String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
		b01String=b01String.replace("|", "'").replace("@", ",");//��֯����
		String [] arrB01=b01String.split(",");
		if(arrB01!=null && arrB01.length>200){
			throw new AppException("����ѡ�񳬹�200����λ��");
		}
		String a0101 = this.getPageElement("a0101A").getValue();//��Ա����
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//���֤��
		String a0160 = this.getPageElement("a0160").getValue();//��Ա���
		String a0163 = this.getPageElement("a0163").getValue();//��Ա״̬
		String age = this.getPageElement("ageA").getValue();//��ʼ����
		String age1 = this.getPageElement("age1").getValue();//��������
		String female = this.getPageElement("female").getValue();//�Ա��Ƿ�Ů
		if(female.equals("1")){
			female = "2";
		}
		String minority = this.getPageElement("minority").getValue();//�Ƿ���������
		String nonparty = this.getPageElement("nonparty").getValue();//�Ƿ���й���Ա	
		String duty = this.getPageElement("duty").getValue();//��ʼְ����
		String duty1 = this.getPageElement("duty1").getValue();//����ְ����
		String a0221aS = this.getPageElement("a0221aS").getValue();//ְ��ȼ�
		String a0221aE = this.getPageElement("a0221aE").getValue();//ְ��ȼ�			
		String a0192dS = this.getPageElement("a0192dS").getValue();//ְ��ְ��
		String a0192dE = this.getPageElement("a0192dE").getValue();//ְ��ְ��			
		String dutynow = this.getPageElement("dutynow").getValue();//ְ������ʼ����
		String dutynow1 = this.getPageElement("dutynow1").getValue();//ְ���ν�������
		String a0219 = this.getPageElement("a0219").getValue();//ְ�����
		String edu = this.getPageElement("edu").getValue();//��ʼѧ��
		String edu1 = this.getPageElement("edu1").getValue();//����ѧ��
		String jiezsj = this.getPageElement("jiezsj").getValue();//�������޼����ֹ
		String radioC = this.getPageElement("radioC").getValue();
		String allday = this.getPageElement("allday").getValue();//�Ƿ�ȫ����
		String sql = this.getPageElement("sql").getValue();
		if(!"1".equals(allday)){
//						allday="2";
			allday="";  //���������Ϊ��δ��ѡ�����  ѧ���Ƿ�ȫ�������� ��bug��ţ�708��  
		}
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("δ���й���ѯ���Ȳ�ѯ!");
		}
		//��ΧУ�� start mengl 20160630
		if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
			
			try {
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("���䷶Χ����ȷ�����飡");
				}
			} catch (NumberFormatException e) {
				throw new AppException("������ֵ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("ְ���η�Χ������ͬһ������飡");
			}
			//ְ���� ֵԽС ������˼Խ�߼�
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("ְ���η�Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
			if(dutynow.compareTo(dutynow1)>0){
				throw new AppException("����ְ����ʱ�䷶Χ����ȷ�����飡");
			}
		}

		if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
			CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
			CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
			
			//TODO ְ��ȼ���Χ�Ƿ�У�����
			if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
				throw new AppException("ְ��ȼ���Χ������ͬһ������飡");
			}
			//ְ��ȼ� ֵԽС ������˼Խ�߼�
			if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
				throw new AppException("ְ��ȼ���Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
			CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
			CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
			if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
				throw new AppException("ְ����Χ������ͬһ������飡");
			}
			//ְ�� ֵԽС ������˼Խ�߼�
			if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
				throw new AppException("ְ����Χ����ȷ�����飡");
			}
		}
		
		if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
			CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
			CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
			//ְ�� ֵԽС ������˼Խ�߼�
			if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
				throw new AppException("ѧ����Χ����ȷ�����飡");
			}
		}
		
		
		//��ΧУ�� end
		
		if (!a0101.equals("")){
			sb.append(" and ");
			sb.append("a01.a0101 like'%"+a0101+"%'");
		}
		if (!a0184.equals("")){
			sb.append(" and ");
			sb.append("a01.a0184 like'%"+a0184+"%'");
		}
		if (!a0160.equals("")){
			sb.append(" and ");
			sb.append("a01.a0160 ='"+a0160+"'");
		}
		if (!a0163.equals("")){
			sb.append(" and ");
			sb.append("a01.a0163='"+a0163+"'");
		}
		if(jiezsj.equals("")){
			jiezsj = DateUtil.getcurdate();
		}
		if (!age.equals("")){
			sb.append(" and ");
			sb.append("GET_BIRTHDAY(a01.a0107,'"+jiezsj+"')>="+age);
		}
		if (!age1.equals("")){
			sb.append(" and ");
			sb.append("GET_BIRTHDAY(a01.a0107,'"+jiezsj+"')<="+age1);
		}
		if (!female.equals("0")){
			sb.append(" and ");
			sb.append("a01.a0104='"+female+"'");
		}
		if (!minority.equals("0")){
			sb.append(" and ");
			sb.append("a01.a0117!='01'");
		}
		if (nonparty.equals("1")){
			sb.append(" and ");
			sb.append("a01.a0141!='01'");			
		}
        if(!"".equals(duty1)){
			sb.append(" and ");
//						sb.append("a01.a0148<='"+duty1+"'");		
			sb.append("a01.a0148>='"+duty1+"'");		
        }
        if(!"".equals(duty)){
			sb.append(" and ");
//						sb.append("a01.a0148>='"+duty+"'");		
			sb.append("a01.a0148<='"+duty+"'");		
        }
        if(!"".equals(a0192dE)){
			sb.append(" and ");
			sb.append("a01.a0192d>='"+a0192dE+"'");		
        }
        if(!"".equals(a0192dS)){
			sb.append(" and ");
			sb.append("a01.a0192d<='"+a0192dS+"'");		
        }            
        if(!"".equals(a0221aS)){
			sb.append(" and ");
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.a0221a<='"+a0221aS+"')");		
        }
        if(!"".equals(a0221aE)){
			sb.append(" and ");
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.a0221a>='"+a0221aE+"')");			
        }   
        if(!"".equals(dutynow)){
			sb.append(" and ");
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.a0288>='"+dutynow+"')");	
        }
        if(!"".equals(dutynow1)){
			sb.append(" and ");
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.a0288<='"+dutynow1+"')");	
        }
        if(!"".equals(a0219)){
			sb.append(" and ");
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.a0219='"+a0219+"')");
        }
        if(!"".equals(b01String)&&b01String!=null){
            sb.append(" and (a01.status = '1' or (a01.status in('2','3') and a01.orgid in ("+b01String+"))))");
			sb.append(" and ");
//						sb.append("a02.A0201B in ("+b01String+")");	
			sb.append("exists (select 1 from A02 a02 where a02.a0000=a01.a0000 and a02.A0201B in ("+b01String+"))");		
            this.getPageElement("a0201b").setValue(b01String);					
        }else{
        	sb.append(" and (a01.status = '1' or (a01.status in('2','3') and exists (select 1" +
                    " from b01" +
                    " where b01.b0111 = a01.orgid)))");
        }
        if(!"".equals(edu)&&"".equals(edu1)){
			sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='"+edu+"'");
            if(!"".equals(allday)){
				sb.append(" and ");
				sb.append("a0837 = '"+allday+"'");		
            }
            sb.append(")");
        }
        if(!"".equals(edu1)&&"".equals(edu)){
			sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='"+edu1+"'");	
            if(!"".equals(allday)){
				sb.append(" and ");
				sb.append("a0837 = '"+allday+"'");		
            }
            sb.append(")");
        }
        if(!"".equals(edu1)&&!"".equals(edu)){
			sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '"+edu1+"' and '"+edu+"'");
            if(!"".equals(allday)){
				sb.append(" and ");
				sb.append("a0837 = '"+allday+"'");		
            }
            sb.append(")");
        }
        this.getPageElement("sql").setValue(sb.toString());
        CommonQueryBS.systemOut("|||||||||||||||||||||||||||||||||"+sb.toString());
        
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String queryType = "";
		queryType=(String)request.getSession().getAttribute("queryType");
		if(queryType==null)queryType="1";
	
		if("define".equals(queryType)){//�Զ����ѯ
			String qvid=this.getPageElement("qvid").getValue();
			String viewnametb=HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='"+qvid+"'");
			String sessionid=this.request.getSession().getId();
			String username=SysManagerUtils.getUserloginName();
			String sql="";
			//if("system".equals(username)){
				sql=" select * from "+viewnametb+",A01SEARCHTEMP "
						+ " where "+viewnametb+".a01a0000=A01SEARCHTEMP.a0000  "
								+ " and A01SEARCHTEMP.sessionid='"+sessionid+"' order by A01SEARCHTEMP.sort asc  ";//
			/*}else{
				String userid = SysManagerUtils.getUserId();
				sql=" select * from "+viewnametb+",A01SEARCHTEMP "
						+ " where "+viewnametb+".a01a0000=A01SEARCHTEMP.a0000  "
								+ " and A01SEARCHTEMP.sessionid='"+sessionid+"' "
								+ " and "+viewnametb+".a01a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) "
								+ " order by A01SEARCHTEMP.sort asc  "
								+ " ";//
			}*/
//			String sql="select * from "+viewnametb+" ";//�Ƴ��ڵ������Ƴ���Ч
			this.request.getSession().setAttribute("allSelect","");
			StopWatch w = new StopWatch();
	      	w.start();
	      	if(this.request.getSession().getAttribute("pageSize") != null && !this.request.getSession().getAttribute("pageSize").equals("")){
				int pageSize = Integer.parseInt(this.request.getSession().getAttribute("pageSize").toString()); 				//�ж��Ƿ��������Զ���ÿҳ���������������ʹ���Զ���
				limit = pageSize;
			}
	      	
	      	this.pageQuery(sql, "SQL", start, limit);
	      	w.stop();
	      	
	      	PhotosUtil.saveLog("��Ϣ��ѯ�ܺ�ʱ��"+w.elapsedTime()+"\r\nִ�е�sql:"+sql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}
		
		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		String sid = this.request.getSession().getId();
		System.out.println("sid��"+sid);
		if(attribute != null && !"".equals(attribute+"")){
			String sql=this.getPageElement("sql").getValue();
			String startSql="select temp.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=temp.A0195) as a0195 ";
			String allSql=startSql+",'"+this.request.getSession().getId()+"' sessionid from a01 temp where temp.a0000 in ( "+sql+")";
			System.out.println("select count(temp1.a0000) from ("+allSql+") temp1");
			Object count = HBUtil.getHBSession().createSQLQuery("select count(temp.a0000) from ("+allSql+") temp").uniqueResult();
			int totalcount =0;
			if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}
			String resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
			String aSql="select temp.a0000,'"+this.request.getSession().getId()+"' sessionid from A01 temp where temp.a0000 in ( "+sql+")";
			this.deleteResult(sid);
			optResult(aSql, resultOptSQL);
			String userid = SysManagerUtils.getUserId();
			String ssql="select temp.* ,(select b0101 from b01 where b0111=temp.A0195) from a01 temp where temp.a0000 in ( "+sql+")";
			String querySql="select  a01.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=a01.A0195) as a0195  from ("+ssql+") a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '"+sid+"') tp on a01.a0000 = tp.a0000 order by sort";
			//String querysql=allSql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid,sid));
			this.request.getSession().setAttribute("allSelect", querySql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@"+querySql);//ͳ��ר��
			this.request.getSession().setAttribute("listAdd", "listAdd");//��ʶ��
			StopWatch w = new StopWatch();
	      	w.start();
	      	this.pageQueryNoCount(allSql, "SQL", 0, 20,totalcount);
	      	w.stop();
	      	this.request.getSession().removeAttribute("listAddGroupSession");
	      	PhotosUtil.saveLog("��Ϣ��ѯ�ܺ�ʱ��"+w.elapsedTime()+"\r\nִ�е�sql:"+allSql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}
		if( !CustomQueryNQPageModel.QUERYLISTFLAG ) {
			CustomQueryNQPageModel.LISTADDCCQLI="-1";
			CustomQueryNQPageModel.LISTADDNAME="��";
		}else {
			CustomQueryNQPageModel.QUERYLISTFLAG=false;
		}
		//�ж��Ƿ����á����������򡱣�
	
		
		String isContain = this.getPageElement("isContain").getValue();
		String radioC=this.getPageElement("radioC").getValue();
		//��queryType = 1��isContain = 0�����ң����ñ�־isA0225 = 1
		if(queryType.equals("1") && isContain != null && isContain.equals("0") && radioC.equals("1")){
			this.request.getSession().setAttribute("isA0225", "1");
		}
		
		
		String temporarySort = "";
		Object ob =  this.request.getSession().getAttribute("temporarySort");
		if(ob != null){
			temporarySort = ob.toString();
		}
		
		String isA0225 = "";
		Object obisA0225 =  this.request.getSession().getAttribute("isA0225");
		if(obisA0225 != null){
			isA0225 = obisA0225.toString();
		}
		//��ǰ��ѯ����id
		String a0200 = this.getPageElement("checkedgroupid").getValue();	
		
		String sort= request.getParameter("sort");//Ҫ���������--���趨�壬ext�Զ���
		String isPageTurning = this.request.getParameter("isPageTurning");
		String startCache = request.getParameter("startCache");
		if(!"true".equals(isPageTurning)&&startCache!=null){//���Ƿ�ҳ  �ҵ���ֶ�����
			start = Integer.valueOf(startCache);
		}
		if(sort != null && sort.equals("a0101")){
			sort = "a0102";
		}
		
		
        String dir= request.getParameter("dir");//Ҫ����ķ�ʽ--���趨�壬ext�Զ���
        String orderby = "";
        if(sort!=null&&!"".equals(sort)){
        	orderby = "order by "+sort+" "+dir;
        }
		
		String userid = SysManagerUtils.getUserId();
		HttpSession session=request.getSession(); 
		if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
			int pageSize = Integer.parseInt(session.getAttribute("pageSize").toString()); 				//�ж��Ƿ��������Զ���ÿҳ���������������ʹ���Զ���
			limit = pageSize;
		}
		//int maxRow = 50000;
		
		this.getPageElement("checkList").setValue("");
		queryType=(String) this.request.getSession().getAttribute("queryType");
	    String sql=this.getPageElement("sql").getValue();
	    //�滻��ǰsession  ��ѯ�����б����sql������ǰ��session
	    sql = sql.replaceAll("(select  a01.a0000,')(.*)(' sessionid from A01 a01)", CommSQL.getComSQL(sid));
	    //sql.replace(CommSQL.getComSQL(sid),"");
	    		
		//String radioC=this.getPageElement("radioC").getValue();
		String resultOptSQL = "";
		String tem_sql = "";
		String tem_sql2 = "";
		
		
		
		if("true".equals(isPageTurning)&&startCache==null){//˵���Ƿ�ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ��ҳ
			String str6 = SysfunctionManager.getCurrentSysfunction().getFunctionid();
            String str7 = this.getCueEventElementName();
            SQLInfo localSQLInfo = (SQLInfo)this.request.getSession().getAttribute(str6 + "@" + str7);
			if(localSQLInfo!=null){
				StopWatch w = new StopWatch();
		      	w.start();
		      	//CommonQueryBS.systemOut("sql---:"+querysql);
		      	this.pageQuery(localSQLInfo.getSql(), "SQL", start, limit);
		      	w.stop();
		      	
		      	PhotosUtil.saveLog("��Ϣ��ѯ�ܺ�ʱ��"+w.elapsedTime()+"\r\nִ�е�sql:"+localSQLInfo.getSql()+"\r\n\r\n");
		  		return EventRtnType.SPE_SUCCESS;
			}
		}
		
		
	  //����в�ѯ
        if("2".equals(radioC)){
        	if(DBUtil.getDBType()==DBType.ORACLE){
        		tem_sql = "insert into cdttttt (select a0000,sessionid from  A01SEARCHTEMP where sessionid='"+sid+"' minus {sql})";
            	//tem_sql = "create or replace view ttttt as (select a0000,sessionid from A01SEARCHTEMP where sessionid='"+sid+"' minus {sql})";
    		    resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"+sid+"'";
            	//resultOptSQL = "delete from A01SEARCHTEMP where a0000  in (select a0000 from (select a0000 from  A01SEARCHTEMP where sessionid='"+sid+"' minus select a0000 from cdttttt )) and sessionid='"+sid+"'";
        	}else{
        		resultOptSQL = "delete from A01SEARCHTEMP where a0000 not in (select a0000 from ({sql}) ax ) ";
        	}
        	

        //׷�Ӳ�ѯ    
        }else if("3".equals(radioC)){
        	tem_sql = "update A01SEARCHTEMP set sessionid='"+sid+"temp' where sessionid='"+sid+"'";
 		    resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
 		    tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='"+sid+"temp'";
        }else if("4".equals(radioC)){//�Ų��ѯ
        	if(DBUtil.getDBType()==DBType.ORACLE){
        		tem_sql = "insert into cdttttt  {sql}";
        		resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"+sid+"'";
        		// resultOptSQL = "delete from A01SEARCHTEMP where exists ({sql} and A01SEARCHTEMP.a0000=a01.a0000) and sessionid='"+sid+"'";
        	}else{
        		resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from ({sql}) ax ) ";
        	}
        	
        }else{
        	if(StringUtil.isEmpty(temporarySort)){	
        		this.deleteResult(sid);
        	}
        	
        	resultOptSQL = "insert into A01SEARCHTEMP {sql}";
        }
      //�������  a0165
  		String a0165 = PrivilegeManager.getInstance().getCueLoginUser().getRate();
  		if(a0165!=null&&!"".equals(a0165)){
  			//2018��10��16�� ȥ���������Ȩ��
  			sql=sql+" and a01.a0165 not in ("+a0165+")";
  		}
  		String iforder = (String)this.getPageElement("orderqueryhidden").getValue();
  		/*String a0201bid = (String)this.getPageElement("a0201b").getValue();
		String[] jgid = a0201bid.split("\\.");
		//yinl a02�������ӷ��� 2017.08.02
		String v0201bs = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?" and a02.V0201B='"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":"";*/
  		//�޸���������ѡʱ�����bug
  		//iforder = (iforder.equals("1") && this.getPageElement("paixu").getValue().equals("1")) ? "0" : iforder;
  		String tabType = this.getPageElement("tabn").getValue().toString();
  		if(!tabType.equals("tab3")){
  			String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
  	    	//��Ա�鿴Ȩ��
  			
  			//ĳЩ�����Ӱ��Ч�ʣ���ĿǰϵͳCOMPETENCE_USERPERSON���ѷ���������ע�ʹ˶δ���
  			//sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";
          
  		}else{//��������ѯ
  			String b01String = this.getPageElement("orgjson").getValue();
  			StringBuffer sb = new StringBuffer();
  			if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
      			//ѡ�����
      			JSONObject jsonObject = JSONObject.fromObject(b01String);
      			sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where  "
      					+ " a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
      			
      			Iterator<String> it = jsonObject.keys();
      			if(it.hasNext()){
      				sb.append(" and a02.a0281='true' ");
      			}
      			sb.append(" and (1=2 ");
      			// ����jsonObject���ݣ���ӵ�Map����
      			while (it.hasNext()) {
      				String nodeid = it.next(); 
      				String operators = (String) jsonObject.get(nodeid);
      				String[] types = operators.split(":");
      				if("true".equals(types[1])&&"true".equals(types[2])){
      					sb.append(" or cu.b0111 like '"+nodeid+"%' ");
      				}else if("true".equals(types[1])&&"false".equals(types[2])){
      					sb.append("or cu.b0111 like '"+nodeid+".%'");
      				}else if("false".equals(types[1])&&"true".equals(types[2])){
      					sb.append("or cu.b0111 = '"+nodeid+"'");
      				}
      			}
      			sb.append(" ) ");
      			sb.append(" ) and 1=1 ");
      			
      		
              }else{
              	//sb.append("  and 1=2 ");
              	sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where "
      					+ " a02.A0201B = cu.b0111 and a02.a0281='true' AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
              	sb.append(" ) ");
              }
  			sql=sql+sb.toString();
  		}
  		String querysql = "";
  		int totalcount = 0;
  		if(sql.equals("")) {
  			return EventRtnType.NORMAL_SUCCESS;
  		}
  		Object count = HBUtil.getHBSession().createSQLQuery(sql.replace(CommSQL.getComSQL(sid), " select count(2) from A01 a01 ")).uniqueResult();
  		
  		if(count instanceof BigDecimal){
  			totalcount = ((BigDecimal)count).intValue();
  		}else if(count instanceof BigInteger){
  			totalcount = ((BigInteger)count).intValue();
  		}else if(count instanceof Long){
  			totalcount = ((Long)count).intValue();
  		}
		if(totalcount>CommSQL.MAXROW){
			if(!"1".equals(radioC)){
				throw new AppException("���β�ѯ�������"+CommSQL.MAXROW+"�����ƣ��޷����м��ϲ�����");
			}
			
			querysql = sql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid,sid));
			
			this.request.getSession().setAttribute("allSelect", querysql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@"+querysql);//ͳ��ר��
			StopWatch w = new StopWatch();
	      	w.start();
	      	//CommonQueryBS.systemOut("sql---:"+querysql);
	      	this.pageQueryNoCount(querysql, "SQL", start, limit,totalcount);
	      	w.stop();
	      	
	      	PhotosUtil.saveLog("��Ϣ��ѯ�ܺ�ʱ��"+w.elapsedTime()+"\r\nִ�е�sql:"+querysql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}


		
  		if(StringUtil.isEmpty(temporarySort)){			//temporarySortΪ�գ�������ʱ����
  			if(("2".equals(radioC)||"4".equals(radioC))&&DBUtil.getDBType()==DBType.ORACLE){
  	  			this.optResult(sql, tem_sql,resultOptSQL);
  	  			//this.optResult(sql, resultOptSQL);
  	  		}else if("3".equals(radioC)){
  	  			String qsql = sql.replace(CommSQL.getComSQL(sid), " select a0000 from A01 a01 ") + "union all select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"temp'";
  	  		    qsql = CommSQL.getComSQL(sid) + " where a0000 in ("+qsql+")";
	  	  		//sql = sql + " or a01.a0000 in (select a0000 from A01SEARCHTEMP where sessionid='"+sid+"temp')";
  	  		    qsql = qsql+("".equals(orderby)?CommSQL.OrderByF(userid,isA0225,a0200,qsql):orderby);
	  	  		this.optResult(qsql, tem_sql, tem_sql2, resultOptSQL);
  	  		}else{
  	  			//this.optResult(sql+("".equals(orderby)?CommSQL.OrderBy(userid):orderby), resultOptSQL);
  	  			this.optResult(sql+("".equals(orderby)?CommSQL.OrderByF(userid,isA0225,a0200,sql):orderby), resultOptSQL);
  	  		}
  		}
  		
  		/* ������ */
      	//�Ƿ�����
      	String isAudit = this.getPageElement("isAudit").getValue();
  		querysql = CommSQL.getComSQLQuery(userid,sid)+" /*"+UUID.randomUUID()+"*/"
  				+ ("0".equals(isAudit)?"":" and a0189='1' and a0190='1' ")
  				+ CommSQL.OrderBy(sort+" "+dir) ;

  		
  		//this.getExecuteSG().addExecuteCode("document.getElementById('temporarySort').value = '';");
  		//this.getPageElement("temporarySort").setValue("");
  		
  		//����Ƿ���ʱ������
  		this.request.getSession().setAttribute("temporarySort", "");
  		//������õ���������������˳����
  		this.request.getSession().setAttribute("isA0225", "");
  		
  		//}else{
  			
  			//querysql = CommSQL.getComSQLQuery(userid,sid)+" where a01.a0000 in (select a0000 from A01SEARCHTEMP where sessionid='"+sid+"') /*"+UUID.randomUUID()+"*/" +("".equals(orderby)?CommSQL.OrderBy(userid):orderby) ;
  			
  			/*int index = querysql.indexOf("from");
  			
  			StringBuffer sqlNew = new StringBuffer(querysql);
  			sqlNew.insert(index-1, ",(select sort from A01SEARCHTEMP tmp where sessionid = '"+sid+"' and tmp.a0000 = a01.a0000) as sort ");
  			querysql = sqlNew.toString();
  			
  			int indexOd = querysql.indexOf("order by");
  			sqlNew.insert(indexOd+8, " sort,");
  			
  			querysql = sqlNew.toString();
  		}*/
		
		  //Object o = this.request.getSession().getAttribute("queryConditionsCQ");
		  //���ǹ̶�������ѯ���������־δ��ȡ��,���������
		 // sql = sql + CommSQL.OrderBy("a01.a0148");
		  
      	//����ͬ����ѯ���ף�ʹ��ҳ��ѯ��������ʾ��ѯ���н��У����Լ��ٲ�ѯʱ�䡣
      	//this.request.getSession().setAttribute("allSelect", sql);
  		 
      	
      	if(!"1".equals(radioC)){
			count = HBUtil.getHBSession().createSQLQuery("select count(a0000) from A01SEARCHTEMP where sessionid='"+sid+"'").uniqueResult();
	  		if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}
	  		
	     }
      	
      	//if(StringUtil.isEmpty(temporarySort)){			//temporarySortΪ�գ�������ʱ����
      		
      		this.request.getSession().setAttribute("allSelect", querysql);
      		this.request.getSession().setAttribute("ry_tj_zy", "tempForCount@@"+querysql);//ͳ��ר��
      	//}
      	
      	StopWatch w = new StopWatch();
      	w.start();
      	CommonQueryBS.systemOut("sql----CustomQueryPageModel-----:"+querysql);
      	this.pageQueryNoCount(querysql, "SQL", start, limit,totalcount);
      	w.stop();
      	
      	PhotosUtil.saveLog("��Ϣ��ѯ�ܺ�ʱ��"+w.elapsedTime()+"\r\nִ�е�sql:"+querysql+"\r\n\r\n");
  		return EventRtnType.SPE_SUCCESS;
	}
	
	public String setShenHeValue(String sql){
		String sql1="select count(1) from("+sql+" where A0189='1')";//�ɲ�һ�������
		String sql2="select count(1) from("+sql+" where A0189='0' or A0189 is null) ";//�ɲ�һ��δ���
		String sql3="select count(1) from("+sql+" where A0190='1') ";//�ɲ��������
		String sql4="select count(1) from("+sql+" where A0190='0' or A0190 is null) ";//�ɲ���δ���
		String sql5="select count(1) from("+sql+" where A0190='1' and A0189='1') ";//�����
		
		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();
		
		String chu1yes=session.createSQLQuery(sql1).uniqueResult().toString();
		String chu1no=session.createSQLQuery(sql2).uniqueResult().toString();
		String chuyes=session.createSQLQuery(sql3).uniqueResult().toString();
		String chuno=session.createSQLQuery(sql4).uniqueResult().toString();
		String wanc=session.createSQLQuery(sql5).uniqueResult().toString();
		
		String bacStr="�ɲ�һ������ˣ�"+chu1yes+"�ˣ�δ��ˣ�"+chu1no+"�ˡ��ɲ���������ˣ�"+chuyes+"�ˣ�δ��ˣ�"+chuno+"�ˡ��������ˣ�"+wanc+"�ˡ�";
		return bacStr;
	}
	
	@PageEvent("initListAddGroupFlag")
	public int doMemberQueryAdd() throws RadowException, AppException{
		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		if(attribute != null || !"".equals(attribute+"")){
			 this.request.getSession().removeAttribute("listAddGroupSession");
		}
  		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public void deleteResult(String sid){
		try {
			HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='"+sid+"'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//mySQL: ����ѯ�����ݽ�����浽A01SEARCHTEMP��
	private void optResult(String sql,String resultOptSQL){
		try {
			String sid = this.request.getSession().getId();
			
			if(DBUtil.getDBType()==DBType.ORACLE){	
				sql = "select tmp.a0000,'"+sid+"' sessionid,rownum i from ( " + sql + " ) tmp";
				
			}else{			//mysql
				sql = "select tmp.a0000,'"+sid+"' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql + " ) tmp";
			}
			
			
			//sql = sql.replace("sort,", "");
			
			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	
	
	//�Ƴ�
	private void optResultDelete(String sql){
		
		String resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
		try {
			String sid = this.request.getSession().getId();
			
			//�Ƚ���ʱ���sessionid���ģ�֮��ɾ����Щ��¼
			String tem_sql = "update A01SEARCHTEMP set sessionid='"+sid+"temp' where sessionid='"+sid+"'";
 		    
 		    String tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='"+sid+"temp'";
			
 		    //����sql
 		   if(DBUtil.getDBType()==DBType.ORACLE){	
 			  sql = "SELECT tmp.a0000,'"+sid+"' sessionid,rownum i FROM (SELECT a01t.a0000,'"+sid+"' sessionid FROM A01SEARCHTEMP a01t "
 	 		    		+"WHERE 1=1 and a01t.sessionid = '"+sid+"temp' and a01t.a0000 not in ("+sql+") order by sort) tmp";
			}else{			//mysql
				//sql = "select tmp.a0000,'"+sid+"' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql + " ) tmp";
				
				sql = "SELECT tmp.a0000,'"+sid+"' sessionid,(@i:=@i+1) AS i FROM (SELECT @i:=0) AS it,"
				 + "(SELECT a01t.a0000,'"+sid+"' sessionid FROM A01SEARCHTEMP a01t WHERE 1=1 and a01t.sessionid = '"+sid+"temp'"
					+ " and a01t.a0000 not in ("+sql+") order by sort) tmp";
				
			}
 		    
 		    
 		    
 		    HBUtil.executeUpdate(tem_sql);
			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
			HBUtil.executeUpdate(tem_sql2);
			
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	
	//oracle: ����ѯ�����ݽ�����浽A01SEARCHTEMP��
	private void optResult(String sql,String tmp_sql, String resultOptSQL){
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			stat.executeUpdate(tmp_sql.replace("{sql}", sql));
			stat.executeUpdate(resultOptSQL);
			con.commit();
			
		} catch (Exception e) {
			if(con!=null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	
	private void optResult(String sql,String tmp_sql,String tem_sql2, String resultOptSQL){
		String sid = this.request.getSession().getId();
		Connection con = null;
			try {
				con = HBUtil.getHBSession().connection();
				con.setAutoCommit(false);
				Statement stat = con.createStatement();
				stat.executeUpdate(tmp_sql);
				
				if(DBUtil.getDBType()==DBType.ORACLE){	
					sql = "select tmp.a0000,'"+sid+"' sessionid,rownum i from ( " + sql + ") tmp";
				}else{			//mysql
					sql = "select tmp.a0000,'"+sid+"' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql + " ) tmp";
				}
				stat.executeUpdate(resultOptSQL.replace("{sql}", sql));
				stat.executeUpdate(tem_sql2);
				con.commit();
				
			} catch (Exception e) {
				if(con!=null){
					try {
						con.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				System.out.println(resultOptSQL.replace("{sql}", sql));
				e.printStackTrace();
			}
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
		List list3 = new ArrayList(Arrays.asList(a00001s));
		if(list.size()>0){
			StringBuffer name = new StringBuffer();
			StringBuffer ids = new StringBuffer();
			String allIds = "";
			for(int i=0;i<list.size();i++){
				name=name.append(""+list.get(i)+",");
			}
			
			for(int j=0;j<list2.size();j++){
				for(int z=0;z<list3.size();z++){
					if(list3.get(z).equals(list2.get(j))){
						list3.remove(z);
					}
				}
			}
			
			for(int i=0;i<list3.size();i++){
				ids=ids.append(""+list3.get(i)+",");
			}
			if(!StringUtil.isEmpty(ids.toString())){
				allIds = ids.substring(0,ids.length()-1);
				String allName = name.substring(0, name.length()-1);
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','"+allName+",�����ɵǼǱ��Ƿ��������ɣ�ע������Ա�����ɵǼǱ����ٴ����ɵø���ԭ�������޷��ָ���',200,function(id){" +
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
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','"+allName+",�����ɵǼǱ��Ƿ��������ɣ�ע������Ա�����ɵǼǱ����ٴ����ɵø���ԭ�������޷��ָ���',200,function(id){" +
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
	
	

	
	
	
	
	
	/**
	 * �������
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("clearCon.onclick")
	@NoRequiredValidate
	public int clearCon()throws RadowException, AppException{
		String a= this.getPageElement("female").getValue();
		//this.getPageElement("a0160").setValue("");
		this.getPageElement("a0160_combo").setValue("");		//��Ա���-fujun
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		//this.getPageElement("a0163").setValue("");
		this.getPageElement("a0163_combo").setValue("");		//��Ա״̬-fujun
		this.getPageElement("ageA").setValue("");
		this.getPageElement("age1").setValue("");
		this.getPageElement("female").setValue("0");;
		this.getPageElement("minority").setValue("0");
		this.getPageElement("nonparty").setValue("0");
		//this.getPageElement("duty").setValue("");
		this.getPageElement("duty_combo").setValue("");			//ְ���� ����fujun
		//this.getPageElement("duty1").setValue("");
		this.getPageElement("duty1_combo").setValue("");		//ְ���� ����fujun
		//this.getPageElement("dutynow").setValue("");
		this.getPageElement("dutynow_1").setValue("");			//����ְ����ʱ�� ����fujun
		//this.getPageElement("dutynow1").setValue("");
		this.getPageElement("dutynow1_1").setValue("");			//����ְ����ʱ�� ����fujun
		//this.getPageElement("a0219").setValue("");
		this.getPageElement("a0219_combo").setValue("");		//ְ����𡪡�fujun
		//this.getPageElement("edu").setValue("");
		this.getPageElement("edu_combo").setValue("");		//ѧ������fujun
		//this.getPageElement("edu1").setValue("");
		this.getPageElement("edu1_combo").setValue("");		//ѧ������fujun
		this.getPageElement("allday").setValue("0");
		this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");
	    //this.getPageElement("a0221aS").setValue("");//ְ��ȼ�
		this.getPageElement("a0221aS_combo").setValue("");		//ְ��ȼ�����fujun
		//this.getPageElement("a0221aE").setValue("");//ְ��ȼ�
		this.getPageElement("a0221aE_combo").setValue("");		//ְ��ȼ�����fujun
		
		//this.getPageElement("a0192dS").setValue("");//ְ��ְ��
		this.getPageElement("a0192dS_combo").setValue("");		//ְ��ְ������fujun
		//this.getPageElement("a0192dE").setValue("");//ְ��ְ��	
		this.getPageElement("a0192dE_combo").setValue("");			//ְ��ְ������fujun	
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ģ�ͽű���ѯҳ��
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("mscript.onclick")
	@NoRequiredValidate
	public int mscriptOnclick()throws RadowException{
		//this.setRadow_parent_data("");
		//this.openWindow("win1", "pages.customquery.QueryCondition");
		this.getExecuteSG().addExecuteCode("$h.openWin('win1','pages.customquery.QueryCondition','��ѯ����',1250,500,'','"+request.getContextPath()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@Override
	public int doInit() throws RadowException {
		LISTADDCCQLI="-1";
		LISTADDNAME="��";
		this.deleteResult(this.request.getSession().getId());
		this.request.getSession().setAttribute("isContainCQ", null);
		this.request.getSession().setAttribute("queryType", null);
		this.request.getSession().setAttribute("queryConditionsCQ", null);
		this.request.getSession().setAttribute("queryTypeEX", null);
		this.request.getSession().setAttribute("additionalSql", null);
		this.request.getSession().setAttribute("allSelect", null);
		this.request.getSession().setAttribute("groupBy", null);
		//����Ƿ���ʱ������
  		this.request.getSession().setAttribute("temporarySort", "");
  		//������õ���������������˳����
  		this.request.getSession().setAttribute("isA0225", "");
  		
		//this.getExecuteSG().addExecuteCode("getShowFour();");//��ʾ�����ѯtab
		this.setNextEventName("initNext");//ɾ������ʱ�鿴��Ա������̨ͼ��鿴��Ա
        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMdd");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
		this.getPageElement("radioC").setValue("1");
        //this.getPageElement("jiezsj").setValue(datestr);
        //this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));
		
		
		//��ֱͳ�������Ƿ���ʾ����
		HBSession sess = HBUtil.getHBSession();
		String import_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'ZHT_ISUSEFUL'").uniqueResult().toString();
		
		if(import_isuseful != null && import_isuseful.equals("ON")){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('hztjBtnNlmenu').hidden = '';");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('nlqkztBtn').hidden = '';");
			
		}else{
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('hztjBtnNlmenu').hidden = 'true';");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('nlqkztBtn').hidden = 'true';");
		}
		
		//�����������
		String loginName = PrivilegeManager.getInstance().getCueLoginUser().getLoginname();
		List<String> oaudit = Arrays.asList(sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'ONE_AUDIT'").uniqueResult().toString().split(","));
		List<String> ttfaudit = Arrays.asList(sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'TTF_AUDIT'").uniqueResult().toString().split(","));
		
		if(oaudit.contains(loginName) && ttfaudit.contains(loginName)){
			//this.getExecuteSG().addExecuteCode("hiddenSubAudit()");
		} else if(oaudit.contains(loginName)){
			this.getExecuteSG().addExecuteCode("hiddenTTFAudit()");
		} else if(ttfaudit.contains(loginName)){
			this.getExecuteSG().addExecuteCode("hiddenOAudit()");
		} else{
			this.getExecuteSG().addExecuteCode("hiddenAudit()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����������ɾ������ѯ��Ա
	 * ����̨ͳ��ͼ��������ѯ��Ա
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 */
	@PageEvent("initNext")
	public int initNext() throws RadowException, AppException, UnsupportedEncodingException{
		//�ж��Ƿ��ǻ�����ѯ���÷���
		String groupID = this.getPageElement("groupID").getValue();
		if(groupID==null||groupID.trim().equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupID.split("@@").length==2//����������ɾ������ѯ��Ա
				||groupID.split("@@").length==3//����̨ͳ��ͼ��������ѯ��Ա
				 ){
			
		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("jgcx_delete".equals(groupID.split("@@")[0])//�������ǣ���������ɾ�����鿴��Ա
				||"zwflcustom".equals(groupID.split("@@")[0])||//����̨ͳ��ͼ��������ѯ��Ա
				"jgcxOrlt_delete".equals(groupID.split("@@")[0])){//��������ɾ�����鿴��ʷ��������Ա
				String id=groupID.split("@@")[1];
			    String sid = this.request.getSession().getId();
			    String userID = SysManagerUtils.getUserId();
			    String isContain = this.getPageElement("isContain").getValue();
			    this.getPageElement("tabn").setValue("tab1");
			    String a0201bsql = "";
				String is_use_a0000_idx_sql = "";
			    this.request.getSession().setAttribute("isContainCQ", false);
		    	int n=id.split(",").length;
//				sql = "SELECT count(a01.a0000) FROM A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.status IN ('2', '3') AND A02.A0201B LIKE '"+groupid+"%' AND EXISTS (SELECT a30.a0000 FROM a30 WHERE a30.a0000 = a01.a0000 AND a30.a3001 IN ('31', '32'))";
		    	if("jgcx_delete".equals(groupID.split("@@")[0])){//�������ǣ���������ɾ�����鿴��Ա ��ְ��Ա
		    		if(n==1){
			    		a0201bsql = "a02.a0201b like '"+id+"%'";
			    	}else if(n>1){
			    		String [] ids=id.split(",");
			    		for(int i=0;i<ids.length;i++){
			    			a0201bsql=a0201bsql+"a02.a0201b like '"+ids[i]+"%' or ";
			    		}
			    		a0201bsql=a0201bsql.substring(0, a0201bsql.length()-3);
			    		a0201bsql="("+a0201bsql+")";
			    	}
		    	}else if("jgcxOrlt_delete".equals(groupID.split("@@")[0])){//��������ɾ�����鿴��ʷ��������Ա
		    		if(n==1){
			    		a0201bsql = "a02.a0201b like '"+id+"%'";
			    	}else if(n>1){
			    		String [] ids=id.split(",");
			    		for(int i=0;i<ids.length;i++){
			    			a0201bsql=a0201bsql+"a02.a0201b like '"+ids[i]+"%' or ";
			    		}
			    		a0201bsql=a0201bsql.substring(0, a0201bsql.length()-3);
			    		a0201bsql="("+a0201bsql+")";
			    	}
		    	}else if("zwflcustom".equals(groupID.split("@@")[0])){//����̨ͳ��ͼ��������ѯ��Ա
		    		String temp=java.net.URLDecoder.decode(groupID.split("@@")[2],"utf-8");
		    		//����ְ�������ְ�����ƣ���ȡְ�����
		    		String zwlb=HBUtil.getValueFromTab("code_value", 
		    				"code_value",
		    				"code_name3='"+temp+"' "
		    						+ " and code_type='ZB09' "
		    						+ " and sub_code_value='"+groupID.split("@@")[1]+"' ");
		    		a0201bsql=" a01.a0221='"+zwlb+"' ";
		    		String userid=SysUtil.getCacheCurrentUser().getId();
		    		CommQuery cq=new CommQuery();
		    		//��Ա�������Ȩ��
		    		List<HashMap<String, Object>> list_user=cq.getListBySQL(" select rate,empid from smt_user t where t.userid = '"+userid+"' ");
		    		String rylb="";
		    		if(list_user!=null&&list_user.size()>0){
		    			String tempuser=(String)list_user.get(0).get("rate");//��Ա��� ���������
		    			if(tempuser!=null&&tempuser.length()>0){
		    				rylb=tempuser;
		    			}
		    			tempuser=(String)list_user.get(0).get("empid");//��Ա��� ����ά����
		    			if(tempuser!=null&&tempuser.length()>0){
		    				rylb=rylb+","+tempuser;
		    			}
		    		}
		    		if(rylb!=null&&rylb.indexOf("\'")!=-1){
		    			a0201bsql=a0201bsql+" and a01.A0165 not in ( "+rylb+") ";
		    		}
		    	}
				is_use_a0000_idx_sql = "a01.a0000";
			    String xzry = this.getPageElement("xzry").getValue();    //��ְ��Ա
			    String lsry = this.getPageElement("lsry").getValue();    //��ʷ��Ա
			    String ltry = this.getPageElement("ltry").getValue();		//������Ա
				String paixu = "1"; //this.getPageElement("paixu").getValue();		//��ְ��������(Ĭ��1)
				String mllb = this.getPageElement("mllb").getValue();		//�������
				Map<String, Boolean> m = new HashMap<String, Boolean>();
				m.put("xzry", "1".equals(xzry));
				m.put("lsry", "1".equals(lsry));
				m.put("ltry", "1".equals(ltry));
				m.put("paixu", "1".equals(paixu));
				m.put("isContain", "1".equals(isContain));
				this.request.getSession().setAttribute("queryConditionsCQ", m);
				this.request.getSession().setAttribute("queryConditionsMLLB", mllb);
				String sql=CommSQL.getComSQL(sid)+" where 1=1 ";
				String a0163sql = "";
				String a0163 = this.getPageElement("personq").getValue();
			    a0163sql = " and a0163='"+a0163+"'";
			    String sqlquery="";
			    String username=SysUtil.getCacheCurrentUser().getLoginname();
			    if("system".equals(username)){
			    	if("jgcx_delete".equals(groupID.split("@@")[0])){
				    	
				    	sqlquery = sql +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.a0255 = '1' "//a0281='true'
								+ "  and "+a0201bsql+")";
			    	}else if("zwflcustom".equals(groupID.split("@@")[0])){
			    		sqlquery = sql +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.a0255 = '1' "//a0281='true'
								+ " ) and "+a0201bsql+" ";
			    	}else if("jgcxOrlt_delete".equals(groupID.split("@@")[0])){
			    		sqlquery = sql.substring(0, sql.indexOf("A01"))+" A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.a0163 IN ('21','22','23','29')" +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")//+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where   "//a0281='true'
								+ "  and "+a0201bsql+") ";
			    	}
		    	}else{
		    		if("jgcx_delete".equals(groupID.split("@@")[0])){
				    	
				    	sqlquery = sql +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
								+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a02.a0255 = '1' "//a0281='true'
								+ "  and "+a0201bsql+")";
			    	}else if("zwflcustom".equals(groupID.split("@@")[0])){
			    		sqlquery = sql +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
								+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"') and a02.a0255 = '1' "//a0281='true'
								+ " ) and "+a0201bsql+" ";
			    	}else if("jgcxOrlt_delete".equals(groupID.split("@@")[0])){
			    		sqlquery = sql.substring(0, sql.indexOf("A01"))+" A01 JOIN A02 ON A01.A0000 = A02.A0000 WHERE a01.a0163 IN ('21','22','23','29')" +  (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "")//+ a0163sql
								+ " and a01.status!='4' and "+is_use_a0000_idx_sql+"  in (select a02.a0000 from a02 where a02.A0201B in "
								+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"')  "//a0281='true'
								+ "  and "+a0201bsql+") ";
			    	}
		    	}
				this.getPageElement("sql").setValue(sqlquery);
		        this.getExecuteSG().addExecuteCode("showgrid()");
		        this.getExecuteSG().addExecuteCode("document.getElementById('orgjson').value=''");
				this.setNextEventName("peopleInfoGrid.dogridquery");
					
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/*public String getCustomData(String sql){
		String getCustomData=null;
//		String head="select   a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000";
		String head="select   a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,QRZXL,ZZXL from A01 a01 where exists (select 1 from A02 a02 where  a01.a0000=a02.a0000) ";
		sql=sql.replace(head, "");
		String [] arrConditon=sql.split("and");
		for(String con:arrConditon){
			
		}
		
		return getCustomData;
	}*/

	
	/**
	 * ��ѡ����Ա��������У��
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("dataVerify.onclick")
	public int dataVerify() throws RadowException {
		HBSession sess=HBUtil.getHBSession();
		String peopleid="";
		String sid = this.request.getSession().getId();
		String groupid = this.getPageElement("checkedgroupid").getValue();
		String sql=(String) this.request.getSession().getAttribute("allSelect");
		if(StringUtil.isEmpty(sql)){
			String cueUserid=SysUtil.getCacheCurrentUser().getId();
			String b0111sql="";
			if(DBType.ORACLE==DBUtil.getDBType()){
				b0111sql="select b0111 from ( select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc) where ROWNUM =1";
			}else if(DBType.MYSQL==DBUtil.getDBType()){
				b0111sql="select b0111 from competence_userdept t where userid='"+cueUserid+"' order by length(b0111) asc limit 1";
			}
			Object b01=sess.createSQLQuery(b0111sql).uniqueResult();
			if(b01==null||"".equals(b01)){
				this.setMainMessage("û�����û���Ȩ��");
				return EventRtnType.FAILD;
			}else{
				this.getExecuteSG().addExecuteCode("document.getElementById('isContain').checked=true;");
				this.getExecuteSG().addExecuteCode("radow.doEvent('querybyid','"+b01+"')");
				peopleid="all";
			}
		}else{
		/*if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("���޴�Ȩ��!");
		}*/
		
		String tableType=this.getPageElement("tableType").getValue();
		Grid grid = (Grid)this.getPageElement("peopleInfoGrid");
		List<HashMap<String,Object>> gridList = grid.getValueList();
		if("1".equals(tableType)){
			if(gridList!=null&&gridList.size()>0){
				for(int i=0;i<gridList.size();i++){
					HashMap<String,Object> map=gridList.get(i);
					Object checked=map.get("personcheck");
					if("true".equals(checked.toString())){
						peopleid+=map.get("a0000")+",";
					}
				}
				if(StringUtil.isEmpty(peopleid)){
					peopleid="selectall";
				}
			}else{
				this.setMainMessage("����Ա��Ϣ");
				return EventRtnType.FAILD;
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			if(gridList!=null&&gridList.size()>0){
			peopleid = this.getPageElement("picA0000s").getValue();
			if(StringUtil.isEmpty(peopleid)){
				peopleid="selectall";
			}else{
				peopleid = peopleid.replace("'", "");
				peopleid = peopleid.substring(0,peopleid.lastIndexOf(","));
			}
		  }else{
			  this.setMainMessage("����Ա��Ϣ");
				return EventRtnType.FAILD;
		  }
			
		}
	}
		String ctxPath = request.getContextPath();
		String paradata = "3@"+sid+"@"+peopleid;
		
		this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','��Ա��ϢУ��',700,599,'','"+ctxPath+"',null,{ids:'"+paradata+"',maximizable:false,resizable:false});");
		//this.setRadow_parent_data("1@"+groupid);
		//this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�����¼��ҳ��
	@PageEvent("loadadd.onclick")
	@NoRequiredValidate
	public int openUpdateWin(String id)throws RadowException{
		//this.setRadow_parent_data("add");
		//String random = UUID.randomUUID().toString();
		String a0000 = UUID.randomUUID().toString();
		this.request.getSession().setAttribute("nowNumber","");
		this.request.getSession().setAttribute("personIdSet", null);
		//this.getExecuteSG().addExecuteCode("addTab('������Ա����','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','��Ա��Ϣ����',851,630,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ����',1009,645,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��Ϣ��¼��ҳ�棨�ɣ�
	@PageEvent("info.onclick")
	@NoRequiredValidate
	public int info(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		this.request.getSession().setAttribute("nowNumberXX","");
		//this.getExecuteSG().addExecuteCode("addTab('��Ա������Ϣ������','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info2',false,false)");
		this.getExecuteSG().addExecuteCode("addTab('��Ա������Ϣ������','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info2',false,false)");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��Ϣ��¼��ҳ��
	@PageEvent("infoAdd.onclick")
	@NoRequiredValidate
	public int infoAdd(String id)throws RadowException{
		HttpSession session =  request.getSession();
		if(session.getAttribute("order")==null) {
			//����Ĭ��˳��
			List<String> list = new ArrayList<String>();
			list.add("����");
			list.add("�Ա�");
			list.add("���֤��");
			list.add("��������");
			list.add("�μӹ���ʱ��");
			list.add("����");
			list.add("����");
			list.add("������");
			list.add("����״��");
			list.add("��Ա���");
			list.add("�������");
			list.add("��������");
			list.add("ͳ�ƹ�ϵ���ڵ�λ");
			list.add("������ò");
			list.add("�뵳ʱ��");
			list.add("�ڶ�����");
			list.add("��������");
			list.add("�ɳ���");
			list.add("����Ա�Ǽ�ʱ��");
			list.add("����");
			list.add("ר��");
			list.add("��ע");
			request.getSession().setAttribute("order", list);
		}
		if(session.getAttribute("a02Order")==null) {
			//����Ĭ��˳��
			List<String> list = new ArrayList<String>();
			list.add("��ְ����");
			list.add("��ְ��������");
			list.add("ְ������");
			list.add("����״̬");
			list.add("��ְ��");
			list.add("�쵼ְ��");
			list.add("�쵼��Ա");
			list.add("��Ա���");
			list.add("�Ƹ����");
			list.add("ѡ�����÷�ʽ");
			list.add("��ְʱ��");
			list.add("��ְ�ĺ�");
			list.add("��ְʱ��");
			list.add("��ְ�ĺ�");
			list.add("ְ��䶯ԭ������");
			request.getSession().setAttribute("a02Order", list);
		}
		String a0000 = UUID.randomUUID().toString();
		this.request.getSession().setAttribute("nowNumber","");
		this.request.getSession().setAttribute("personIdSet", null);
		this.getExecuteSG().addExecuteCode("$h.openWin('infoNew','pages.publicServantManage.InfoNew','��Ϣ��������Ա',1400,700,document.getElementById('a0000').value,'"+request.getContextPath()+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//��Ա��Ϣ���޸�
	@PageEvent("infoUpdate.onclick")
	public int infoUpdateBtn() throws RadowException, AppException {
		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//�б�
		if("1".equals(tableType)){
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("��Ϣ���޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
			int countNum = 0;
			for (int j = 0; j < list.size();j++) {
				HashMap<String, Object> map = list.get(j);
				Object check1 = map.get("personcheck");
				if (check1!= null && check1.equals(true)) {
					a0000=map.get("a0000")==null?"":map.get("a0000").toString();
					countNum++;
				}
			}
			if(countNum==0){
				throw new AppException("�빴ѡ��Ա��");
			}else if(countNum>1){
				throw new AppException("�����ѡһ�˽��в�����");
			}
			if(a0000==null || a0000.trim().equals("")){
				throw new AppException("���ݻ�ȡ�쳣��");
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			/*Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				throw new AppException("��Ϣ���޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				throw new AppException("�빴ѡ��Ա��");
			}
			
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				throw new AppException("�����ѡһ�˽��в�����");
			}
			
			a0000 = a0000s;
		}
		initA0000MapXX(a0000);
		this.getExecuteSG().addExecuteCode("addTab('','"+ "update" + a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info2',false,false)");
		this.setRadow_parent_data(a0000.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��ѡ�޸���Ա�������Ϣ
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("rmbUpdate.onclick")
	@GridDataRange
	public int rmbUpdate() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		
		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//�б�
		if("1".equals(tableType)){
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
			int countNum = 0;
			for (int j = 0; j < list.size();j++) {
				HashMap<String, Object> map = list.get(j);
				Object check1 = map.get("personcheck");
				if (check1!= null && check1.equals(true)) {
					a0000=map.get("a0000")==null?"":map.get("a0000").toString();
					countNum++;
				}
			}
			if(countNum==0){
				//this.setMainMessage("�빴ѡ��Ա��");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
				
			}else if(countNum>1){
				
				//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�����ѡһ�˽��в�����',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			/*Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("�����ѡһ�˽��в�����");
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000 = a0000s;
		}
		
		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			//initA0000Map(a0000);
			//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			//this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
		/*A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			initA0000Map(a0000);
			this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			//throw new AppException("����Ա����ϵͳ�У�");
			//this.getExecuteSG().addExecuteCode("Ext.Msg.alert('ϵͳ��ʾ','����Ա����ϵͳ�У�');");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����Ա����ϵͳ�У�',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
	}
	
	@PageEvent("modify")
	public int modify(String a0000)throws AppException, RadowException{
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
	
	@PageEvent("deletePeople")
	public int deletePeople(String a0000)throws AppException, RadowException{
		
		String tipStr = this.getPageElement("deleteTip").getValue();
		
		dialog_set("deleteconfirm1","��ȷ��ɾ��  "+tipStr+"  ��",a0000.toString().replace("'", "^"));
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("printTrue")
	public int printTrue(String a0000)throws AppException, RadowException, IOException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("��Ա��Ϣ����");
		}
		pdfView(a0000+",true");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("printFalse")
	public int printFalse(String a0000)throws AppException, RadowException, IOException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null){
			throw new AppException("��Ա��Ϣ����");
		}
		pdfView(a0000+",false");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("expLrm")
	public int expLrm(String a0000)throws AppException, RadowException{
		String infile ="";
		String name = "";
		String zippath =ExpRar.expFile();
		String lrm = createLrmStr(a0000);
		name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//		System.out.println(zippath);
		try {
			FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
			List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
			if(list15.size()>0){
				A57 a57 = list15.get(0);
				if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
					File f = new File(zippath + name+".pic");
					FileOutputStream fos = new FileOutputStream(f);
					File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
					if(f2.exists() && f2.isFile()){
						InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
						byte[] data = new byte[1024];
						while (is.read(data) != -1) {
							fos.write(data);
						}
						is.close();
					}
					fos.close();
					
				}
			}
//			String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//			p = rt.exec(cmdd);
//			p.waitFor();
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
		try {
			infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//			String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//			p = Runtime.getRuntime().exec(cmd);
			SevenZipUtil.zip7z(zippath, infile, null);
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("expLrmx")
	public int expLrmx(String a0000)throws AppException, RadowException{
		String zippath =ExpRar.expFile();
		String name = "";
		String infile = "";
		PersonXml per = createLrmxStr(a0000,"");
		name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//		System.out.println(zippath);
		try {
			FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
//			String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//			p = rt.exec(cmdd);
//			p.waitFor();
			A01 a01log = new A01();
			new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
		try {
			infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//			String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//			p = Runtime.getRuntime().exec(cmd);
			SevenZipUtil.zip7z(zippath, infile, null);
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("alertW.onclick")
	@NoRequiredValidate
	public int alertW(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		this.getExecuteSG().addExecuteCode("alertW('alertWin','��������',"+random+")");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("createDjbd.onclick")
	@NoRequiredValidate
	public int openUpdateWin1(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		//����ʱɾ�����ݵ�UUID tongzj
		//this.getExecuteSG().addExecuteCode("addTab('��Ա��������','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.getExecuteSG().addExecuteCode("addTab('����������','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.ListOutPut',false,false)");
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("createDjb2.onclick")//���ù̶���ᰴťɾ��
	@NoRequiredValidate
	public int createDjb2(String id)throws RadowException{
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("createDjbr.onclick")
	@NoRequiredValidate
	public int createDjb3(String id)throws RadowException, SQLException{
		/*String sql = "select tpid from listoutput where tpname like '����Ա�����%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		re.next();
		String value = re.getString(1);
		this.request.getSession().setAttribute("tpid", value);
		this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
		this.getExecuteSG().addExecuteCode("addTab('���','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");*/
		this.setMainMessage("��ʱ����");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�ɲ������
	@PageEvent("createDjrmb.onclick")
	@NoRequiredValidate
	public int createDjrmb()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}else{
				person = this.getPageElement("checkList").getValue();
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}
			else{
				person = this.getPageElement("picA0000s").getValue();
				if(person!=null && !"".equals(person.trim())){
					person = person.replaceAll("\\'", "\\|").replaceAll("\\,", "\\@");
					person = person.substring(0, person.length()-1);
				}
			}
		}
		String sql = "select tpid from listoutput where tpname = '�������������' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		String ctxPath = request.getContextPath();
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('�ɲ��������','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.publicServantManage.OtherTemShow','�ɲ�����������',1200,900,'R','"+ctxPath+"');");

		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	//����Ա�ǼǱ�
	@PageEvent("createDjbj.onclick")
	@NoRequiredValidate
	public int createDjb5()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		HBSession sess = HBUtil.getHBSession();
		if("1".equals(tableType)){
			//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = sess.createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}else{
				person = this.getPageElement("checkList").getValue();
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = sess.createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}
			else{
				person = this.getPageElement("picA0000s").getValue();
				if(person!=null && !"".equals(person.trim())){
					person = person.replaceAll("\\'", "\\|").replaceAll("\\,", "\\@");
					person = person.substring(0, person.length()-1);
				}
			}
		}
		String ctxPath = request.getContextPath();
		String sql = "select tpid from listoutput where tpname like '����Ա�ǼǱ�%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('����Ա�ǼǱ��','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin1','pages.publicServantManage.OtherTemShow','����Ա�ǼǱ��',1200,900,'R','"+ctxPath+"');");

		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	//�ɲ���Ҫ�����
	@PageEvent("createGanBu.onclick")
	@NoRequiredValidate
	public int createGanBu()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}else{
				person = this.getPageElement("checkList").getValue();
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}
			else{
				person = this.getPageElement("picA0000s").getValue();
				if(person!=null && !"".equals(person.trim())){
					person = person.replaceAll("\\'", "\\|").replaceAll("\\,", "\\@");
					person = person.substring(0, person.length()-1);
				}
			}
		}
		String sql = "select tpid from listoutput where tpname like '�ɲ���Ҫ�����%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('�ɲ���Ҫ���','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin2','pages.publicServantManage.OtherTemShow','�ɲ���Ҫ���',1200,900,'R','"+ctxPath+"');");

		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	//����������
	@PageEvent("createJiangLi.onclick")
	@NoRequiredValidate
	public int createJiangLi()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}else{
				person = this.getPageElement("checkList").getValue();
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}
			else{
				person = this.getPageElement("picA0000s").getValue();
				if(person!=null && !"".equals(person.trim())){
					person = person.replaceAll("\\'", "\\|").replaceAll("\\,", "\\@");
					person = person.substring(0, person.length()-1);
				}
			}
		}
		String sql = "select tpid from listoutput where tpname like '����������%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('�����������','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin3','pages.publicServantManage.OtherTemShow','�����������',1200,900,'R','"+ctxPath+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//��ȿ��˱�
	@PageEvent("createKaoHe.onclick")
	@NoRequiredValidate
	public int createKaoHe()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}else{
				person = this.getPageElement("checkList").getValue();
			}
		}
		if("2".equals(tableType) || "3".equals(tableType)){
			Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				StringBuffer ids = new StringBuffer();
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				List allPeople = HBUtil.getHBSession().createSQLQuery(newsql).list();
				if (allPeople != null && allPeople.size() > 0) {
					for(Object sa0000 : allPeople){
						ids.append("|").append(sa0000).append("|@");
					}
				}
				person = ids.substring(0, ids.length() - 1);
			}
			else{
				person = this.getPageElement("picA0000s").getValue();
				if(person!=null && !"".equals(person.trim())){
					person = person.replaceAll("\\'", "\\|").replaceAll("\\,", "\\@");
					person = person.substring(0, person.length()-1);
				}
			}
		}
		String sql = "select tpid from listoutput where tpname like '��ȿ��˱�%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('��ȿ��˱��','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)"); 
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin4','pages.publicServantManage.OtherTemShow','��ȿ��˱��',1200,900,'R','"+ctxPath+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//������
	@PageEvent("createHuaMc")
	public int createHuaMc(String valuee)throws RadowException, SQLException{
		/*//��ȡҳ���Ƿ�����¼�
		String isContain = this.getPageElement("isContain").getValue();
		//��״̬ת��OtherTemShowTowPageModel���� �ж�Ŀ¼��չʾ��ʽ�Ƿ�����¼�
		request.getSession().setAttribute("isContain", isContain);
		String checkList = "";
		String tpname = "";
		//��ȡģ������
		String sql1 = "select tpname from listoutput where tpid = '"+valuee+"' group by tpid,tpname ";
		ResultSet ree = HBUtil.getHBSession().connection().createStatement().executeQuery(sql1);
		if( ree != null && ree.next()){
			tpname = ree.getString(1);
		}
		String sql = "select tpid from listoutput where tpname = '"+tpname+"' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			//ͨ�����������ѯ�û������¼���������Ա��
			String att = (String)request.getSession().getAttribute("over");
			if("true".equals(att)){
				//listofname�Ǵ�CustomQueryPageModel �� ������ �������룻
				String  Encoder = (String) request.getSession().getAttribute("listofname");
				String sqle = "select  a0000 from a02 where a0201b like '"+Encoder+".%' and a0255 = '1' group by a0000";
				ResultSet res = HBUtil.getHBSession().connection().createStatement().executeQuery(sqle);
				if(res != null || !"".equals(res)){
					while(res.next()){
						checkList += "|";
						checkList += res.getString(1)+"|@";
					}
				}
			}else{
				checkList = this.getPageElement("checkList").getValue();
			}
			this.request.getSession().setAttribute("personids", checkList);
			request.getSession().setAttribute("viewType", "11");
			this.getExecuteSG().addExecuteCode("refresh();");
			this.getExecuteSG().addExecuteCode("addTab('������','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShowTow',false,false)"); 
		}*/
		//��ȡҳ���Ƿ�����¼�
		String isContain = this.getPageElement("isContain").getValue();
		//��״̬ת��OtherTemShowTowPageModel���� �ж�Ŀ¼��չʾ��ʽ�Ƿ�����¼�
		request.getSession().setAttribute("isContain", isContain);
		//������ģ��id
		this.request.getSession().setAttribute("tpid", valuee);
		//ͨ�����������ѯ�û������¼���������Ա��
		String checkList = this.getPageElement("checkList").getValue();
		//����othertemshowtopagemodel ����
		this.request.getSession().setAttribute("personids", checkList);
		request.getSession().setAttribute("viewType", "11");
		//this.getExecuteSG().addExecuteCode("refresh();");
//		String aid = "R"+(Math.random()*Math.random()*100000000);
//		this.getExecuteSG().addExecuteCode("addTab('������','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShowTow',false,false)"); 
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin5','pages.publicServantManage.OtherTemShowTow','������',1200,900,'R','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��Ա�޸�
	@PageEvent("modifyBtn.onclick")
	public int modify() throws RadowException, AppException {
		List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
		int countNum = 0;
		String a0000 = null;
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("personcheck");
			if (check1!= null && check1.equals(true)) {
				a0000=map.get("a0000")==null?"":map.get("a0000").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new AppException("�빴ѡ��Ա��");
		}else if(countNum>1){
			throw new AppException("�����ѡһ�˽��в�����");
		}
		if(a0000==null || a0000.trim().equals("")){
			throw new AppException("���ݻ�ȡ�쳣��");
		}
		this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(a0000.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	//ɾ���¼�
	@PageEvent("deletePersonBtn.onclick")
	@GridDataRange
	public int deletePerson() throws RadowException, AppException {
		
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		String type = this.getPageElement("tableType").getValue();
		StringBuffer a0000 = new StringBuffer();
		String firstCheckName = "";
		String tipStr = "";
		//�б� 
		if("1".equals(type)){
			/*//�ж��Ƿ���ȫ����Ա
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					this.setMainMessage("����ѡ��Ҫɾ������Ա");
					return EventRtnType.FAILD;
				}
				if(StringUtil.isEmpty(allSelect)){
					this.setMainMessage("����ѡ��Ҫɾ������Ա");
					return EventRtnType.FAILD;
				}
				a0000 = new StringBuffer(allSelect + "@1");
			}else{*/
				List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
				int countNum = 0;
				for (int j = 0; j < list.size();j++) {
					HashMap<String, Object> map = list.get(j);
					Object check1 = map.get("personcheck");
					if (check1!= null && check1.equals(true)) {
						if("".equals(firstCheckName)){
							firstCheckName = map.get("a0101").toString();
						}
						a0000.append("'").append(map.get("a0000")==null?"":map.get("a0000").toString()).append("',");//����ѡ����Ա�����װ���á������ָ�
						countNum++;
					}
				}
				if(countNum==0){
					//throw new AppException("�빴ѡ��Ա��");
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(countNum == 1){
					tipStr = firstCheckName;
				}else if(countNum > 1){
					tipStr = firstCheckName+"��"+countNum+"��";
				}
		}
		//С���ϡ���Ƭ
		if("2".equals(type) || "3".equals(type)){
			/*Object obj = null;
			if("2".equals(type)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(type)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				a0000 = new StringBuffer(allSelect + "@1");
			}
			else{*/
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					//throw new AppException("�빴ѡ��Ա��");
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				a0000 = new StringBuffer(a0000s);
				
				String[] a0000_arr = a0000s.split(",");
				int countNum = a0000_arr.length;
				
				if(countNum == 1){
					tipStr = firstCheckName;
				}else if(countNum > 1){
					tipStr = firstCheckName+"��"+countNum+"��";
				}
		}
//		else if(countNum>1){
//			throw new AppException("�����ѡһ�˽��в�����");
//		}
		if(a0000==null || a0000.toString().trim().equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
			//throw new AppException("���ݻ�ȡ�쳣��");
		}
		
		
		/*this.setRadow_parent_data(a0000.toString());
	    this.openWindow("deletePersonWin", "pages.publicServantManage.DeletePersonPage");*/
		//this.getExecuteSG().addExecuteCode("$h.openWin('deletePersonWin','pages.publicServantManage.DeletePersonPage','��Աɾ��',850,450,document.getElementById('a0000').value,ctxPath)");
		//System.out.println(a0000.toString());
		this.getPageElement("deleteTip").setValue(tipStr);
		
		String deleteA0000S = a0000.toString().replace("'", "^");
		this.getPageElement("deleteA0000S").setValue(deleteA0000S);		//ɾ����id
		
		//dialog_set("deleteconfirm1","��ȷ��Ҫɾ��"+tipStr+"��",a0000.toString().replace("'", "^"));
		this.getExecuteSG().addExecuteCode("$h2.confirm('ϵͳ��ʾ��','��ȷ��Ҫ�Ƴ�"+tipStr+"��',240,function(id) { if('ok'==id){radow.doEvent('deleteconfirm1');}else{return false;}});");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteconfirm1")
	public int deleteconfirm1(String a0000)throws AppException, RadowException{
		String tipStr = this.getPageElement("deleteTip").getValue();
		//dialog_set("deleteconfirm","���ٴ�ȷ����Ҫɾ��"+tipStr+"��",a0000);
		
		this.getExecuteSG().addExecuteCode("$h2.confirm('ϵͳ��ʾ��','���ٴ�ȷ����Ҫ�Ƴ�"+tipStr+"��',240,function(id) { if('ok'==id){radow.doEvent('deleteconfirm');}else{return false;}});");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void dialog_set(String fnDelte, String strHint,String a0000){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // �������Ϣ�����ȷ��ʱ�������´��¼�
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(a0000);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// �������Ϣ�����ȡ��ʱ�������´��¼�
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage(strHint); // ������ʾ��Ϣ
	}
	
	//ɾ���¼�
		@PageEvent("deleteconfirm")
		@Transaction
		public int deleteconfirm(String peopleId)throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
			peopleId = this.getPageElement("deleteA0000S").getValue();
			
			//��ȡ����ѡ����Ա���
			String sa0000 = "";
			String sa00002 = "";
			String a0000s = peopleId.replace("^", "'");
			HBSession sess = HBUtil.getHBSession();
			String[] values = a0000s.split("@");
			if (values.length > 1) {
				StringBuffer sb = new StringBuffer();
				String sql = values[0];
				if(!sql.contains("*")){
					sql="select * from ("+sql+")";
				}
				String newsql = sql.replace("*", "a0000");
				List allSelect = sess.createSQLQuery(newsql).list();
				if (allSelect.size() > 0) {
					for (int i = 0; i < allSelect.size(); i++) {
						//�ж��Ƿ���ɾ��Ȩ�ޡ�c.type������Ȩ������(0�������1��ά��)
						String a0000 = allSelect.get(i).toString();
						A01 a01 = (A01)sess.get(A01.class, a0000);
						String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
								" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
								" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
						String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
						List elist = sess.createSQLQuery(editableSQL).list();
						List elist2 = sess.createSQLQuery(editableSQL2).list();
			/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
						String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
						if(type == null || !type.contains("'")){
							type ="'zz'";//�滻��������
						}
						List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
						if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
							continue;
						}
						if(elist2==null||elist2.size()==0){//ά��Ȩ��
							if(elist!=null&&elist.size()>0){//�����Ȩ��
								continue;
							}else{
								//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
								if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
									continue;
								}else {
									sb.append("'").append(a0000).append("',");
								}
								
							}
						}else {
							sb.append("'").append(a0000).append("',");
						}
					}
					if (sb.length() == 0) {
						throw new AppException("��ѡ��Ա���ɲ�����");
					}
					sa0000 = sb.substring(0, sb.length() - 1);
				} else {
					throw new AppException("����ѡ��Ҫɾ������Ա��");
				}
			}else{
				//�������Ķ��Žص�
				String a0 = a0000s.substring(0,a0000s.length()-1);
				String[] s =  a0.split(",");
				for(String str : s){
					A01 a01 = (A01)sess.get(A01.class, str.replace("'", ""));
					String fkly = a01.getFkly();	//������Դֱ��ɾ��
					if(fkly!=null) {
						sa0000 = sa0000 + str + ","; 
						continue;
					}
					//system����ֱ��ɾ����ְ����Ա����ְ����Ա�ж���ʽΪ��a02�޼�¼������ְ����ȫ��Ϊ������λ��-1��
					if(PrivilegeManager.getInstance().getCueLoginUser().getId() != null && PrivilegeManager.getInstance().getCueLoginUser().getId().equals("40288103556cc97701556d629135000f")){
						//��ǰ�û�Ϊsystem,�ж��Ƿ�Ϊ��ְ����Ա
						
						/*String sql = "select 1 from  a02 where a0000 = "+str+" and a0201b != '-1'";		//��ѯa02�Ƿ��з�������λ��ְ���¼
						List a02List = sess.createSQLQuery(sql).list(); 
						
						if(a02List.size() < 1){		//���û�з�������λ����ְ��¼�������Ϊ��ְ����Ա������ֱ��ɾ��
							sa0000 = sa0000 + str + ","; 
							continue;
						}*/
						
						sa00002 = sa00002 + str + ","; 
						continue;
					}
					
					
					//A01 a01 = (A01)sess.get(A01.class, str.replace("'", ""));
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' ";
					List elist = sess.createSQLQuery(editableSQL).list();   //�Ƿ������Ȩ�ޣ���ֵ���ʾ�������Ȩ��(Ŀǰ����Ȩ�޵���ƣ����Ȩ���ж������壬���³��򽫲����������жϣ�2017-9-20)
					List elist2 = sess.createSQLQuery(editableSQL2).list();	//�Ƿ���ά��Ȩ�ޣ���ֵ���ʾ����ά��Ȩ��
		/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){		//���typeΪ�գ����ʾ��ǰ�û��У��������ά��Ȩ��
						type ="'zz'";//�滻�������ݣ��������elist3��ѯ�޽������ʾ���û��е�ǰ������Ա�Ĺ������ά��Ȩ��
					}
					
					//elist3��ֵ���ʾ����ǰ�û�û�в�������Ա�Ĺ������ά��Ȩ�ޣ���û��Ȩ���޸�
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000="+str+" and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
						continue;
					}
					if(elist2==null||elist2.size()==0){//��ά��Ȩ��
						/*if(elist!=null&&elist.size()>0){//�����Ȩ��
							
						}else{
							//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
								
							}else {
								sa0000 = sa0000 + str + ","; 
							}
							
						}*/
						
						
					}else {		//��ά��Ȩ��
						sa00002 = sa00002 + str + ","; 
					}
				}
				if(sa0000.length() > 0){
					sa0000 = sa0000.substring(0, sa0000.length() - 1);
				}
				if(sa00002.length() > 0){
					sa00002 = sa00002.substring(0, sa00002.length() - 1);
				}
			}
			//�жϣ������ֵ��ѡ��
			if((!"".equals(sa0000) && sa0000 != null) || (!"".equals(sa00002) && sa00002 != null)){

					//a01.setStatus("0");//�޸���ȫɾ��
					//sess.saveOrUpdate(a01);
					deletePerson(sa0000,sa00002);
					//updateFKBSPerson(sa00002);
					this.setMainMessage("�Ƴ��ɹ���");
					//sess.flush();
					//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete');");
					
				
			}else{
				throw new AppException("��ѡ��Ա���ɲ�����");
			}
			String tableType = this.getPageElement("tableType").getValue();
			//�б�
			if("1".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("reloadGrid();");
				this.getExecuteSG().addExecuteCode("document.getElementById('checkList').value = '';");
			}
			//С����
			if("2".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("datashow();");
			}
			//��Ƭ
			if("3".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("picshow();");
			}
			
			return EventRtnType.NORMAL_SUCCESS;
			
		}
		
		private void deletePerson(String a0000,String a00002) throws AppException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
			List<A01> a01_list = null;
			if(a00002!=null && !a00002.trim().equals("")) {
				a01_list = (List<A01>) HBUtil.getHBSession().createQuery("from A01 where a0000 in("+a00002+")").list();
			}
			if(a0000!=null && !a0000.trim().equals("")) {
				List<A57> a57_list = (List<A57>) HBUtil.getHBSession().createQuery("from A57 where a0000 in("+a0000+")").list();
				for(A57 a57 : a57_list){
					if(a57!=null&&a57.getPhotopath()!=null&&!"".equals(a57.getPhotopath())){
						String photourl = a57.getPhotopath();
						File fileF = new File(PhotosUtil.PHOTO_PATH+photourl+a57.getPhotoname());
						if(fileF.isFile()){
							fileF.delete();
						}
						
					}
				}
			}
			
			Statement stmt=null;
			Connection conn = HBUtil.getHBSession().connection();
			try{
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				if(a0000!=null && !a0000.trim().equals("")) {
					//String a0000 = a01.getA0000();
					stmt.executeUpdate("delete from a02 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a05 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a06 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a08 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a14 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a15 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a36 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a11 where a1100 in(select a1100 from a41 where a0000 in ("+a0000+"))");
					stmt.executeUpdate("delete from a41 where a0000 in ("+a0000+")");
					
					stmt.executeUpdate("delete from a29 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a53 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a37 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a31 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a30 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a57 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a60 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a61 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a62 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a63 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a64 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a01 where a0000 in("+a0000+")");
					
					stmt.executeUpdate("delete from FOLDER where a0000 in("+a0000+")");			//���ӵ����ļ���Ϣ
					stmt.executeUpdate("delete from FOLDERTREE where a0000 in("+a0000+")");		//���ӵ����ļ���tree��Ϣ
					stmt.executeUpdate("delete from A99Z1 where a0000 in("+a0000+")");			//������Ϣ
					stmt.executeUpdate("delete from verify_error_list where vel002 in("+a0000+")");		//��ԱУ����Ϣ
				}
				if(a00002!=null && !a00002.trim().equals("")) {
					stmt.executeUpdate("update a01 set fkbs=null,fkly=null where a0000 in("+a00002+")");
				}
				
				conn.commit();
			}catch(Exception e){
				conn.rollback();
				throw new AppException("���ݿ⴦���쳣��",e);
			}finally{
				if (stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
						throw new AppException("���ݿ⴦���쳣��", e);
					}
				}
					
			}
			
			if(a01_list!=null && a01_list.size()>0) {
				for(int i=0;i<a01_list.size();i++){
					
					new LogUtil("33", "A01", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "ɾ����¼", new ArrayList()).start();
					
					//��õ�ǰ��½�û���id������
					UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
					
					String logMsg = "idΪ��"+a01_list.get(i).getA0000()+"  ����Ϊ��"+ a01_list.get(i).getA0101()+"����Ա��ɾ����  ɾ����idΪ��"+user.getId()+"  �˺�Ϊ��"+user.getName();
					
					//����Աɾ����¼��¼��tomcat��־��
					log.info(logMsg);
				}
			}
		}
	
	//ɾ���ɹ�
	@PageEvent("delete")
	public int delete() throws RadowException {
	    this.setMainMessage("ɾ���ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ˽�з������Ƿ�ѡ���û�
	 * 
	 * @throws RadowException
	 */
	private int choose(String gridid,String checkId) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checkId);
			if(check1==null){
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// û��ѡ���κ��û�
		}
		if (result >= 2) {
			return CHOOSE_OVER_TOW;// ѡ�ж���һ���û�
		}
		return number;// ѡ�еڼ����û�
	}
	
	//�ɲ�����
	@PageEvent("PortraitBtn.onclick")
	@GridDataRange
	public int Portrait() throws RadowException, AppException{
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		
		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//�б�
		if("1".equals(tableType)){
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
			int countNum = 0;
			for (int j = 0; j < list.size();j++) {
				HashMap<String, Object> map = list.get(j);
				Object check1 = map.get("personcheck");
				if (check1!= null && check1.equals(true)) {
					a0000=map.get("a0000")==null?"":map.get("a0000").toString();
					countNum++;
				}
			}
			if(countNum==0){
				//this.setMainMessage("�빴ѡ��Ա��");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
				
			}else if(countNum>1){
				
				//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�����ѡһ�˽��в�����',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			/*Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("�����ѡһ�˽��в�����");
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000 = a0000s;
		}
		
		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getPageElement("a0000").setValue(a0000);
			//this.getExecuteSG().addExecuteCode("$h.openWin('jdinformation','pages.publicServantManage.JdInformation','�ල��Ϣ',990,460,'"+a0000+"',ctxPath)");
			this.getExecuteSG().addExecuteCode("peoplePortrait();");		
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
			
			
	}
	
	    //�ɲ�����
		@PageEvent("PersionFileBtn.onclick")
		@GridDataRange
		public int persionFile() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
			String sqlf = this.getPageElement("sql").getValue();
			if(sqlf.equals("")){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			};
			
			
			String tableType = this.getPageElement("tableType").getValue();
			String a0000 = null;
			//�б�
			if("1".equals(tableType)){
				/*Object obj = this.getPageElement("checkAll").getValue();
				if("1".equals(obj)){
					throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
				}*/
				List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
				int countNum = 0;
				for (int j = 0; j < list.size();j++) {
					HashMap<String, Object> map = list.get(j);
					Object check1 = map.get("personcheck");
					if (check1!= null && check1.equals(true)) {
						a0000=map.get("a0000")==null?"":map.get("a0000").toString();
						countNum++;
					}
				}
				if(countNum==0){
					//this.setMainMessage("�빴ѡ��Ա��");
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
					
				}else if(countNum>1){
					
					//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�����ѡһ�˽��в�����',null,180);");
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a0000==null || a0000.trim().equals("")){
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//С���ϡ���Ƭ
			if("2".equals(tableType) || "3".equals(tableType)){
				/*Object obj = null;
				if("2".equals(tableType)){
					obj = this.getPageElement("checkAll2").getValue();
				}
				if("3".equals(tableType)){
					obj = this.getPageElement("checkAll3").getValue();
				}
				if("1".equals(obj)){
					this.getPageElement("picA0000s").setValue("");
					throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
				}*/
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
				a0000s = a0000s.substring(0, a0000s.length()-1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				if(str.length>1){
					//throw new AppException("�����ѡһ�˽��в�����");
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
				a0000 = a0000s;
			}
			
			//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
			A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
			if(ac01!=null){
				this.request.getSession().setAttribute("personIdSet", null);
				//this.getExecuteSG().addExecuteCode("$h.openWin('persionfile','pages.publicServantManage.PersionFile','�ɲ�����',1080,600,'"+a0000+"',ctxPath)");		
				this.getPageElement("a0000").setValue(a0000);
				this.getExecuteSG().addExecuteCode("openPersionFile()");
				return EventRtnType.NORMAL_SUCCESS;	
			}else{
				throw new AppException("����Ա����ϵͳ�У�");
			}									
		}	
		
	//�ල��Ϣ
	@PageEvent("jdinformationBtn.onclick")
	@GridDataRange
	public int jdinformation() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		
		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//�б�
		if("1".equals(tableType)){
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
			int countNum = 0;
			for (int j = 0; j < list.size();j++) {
				HashMap<String, Object> map = list.get(j);
				Object check1 = map.get("personcheck");
				if (check1!= null && check1.equals(true)) {
					a0000=map.get("a0000")==null?"":map.get("a0000").toString();
					countNum++;
				}
			}
			if(countNum==0){
				//this.setMainMessage("�빴ѡ��Ա��");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
				
			}else if(countNum>1){
				
				//this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','�����ѡһ�˽��в�����',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			/*Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}
			if("1".equals(obj)){
				this.getPageElement("picA0000s").setValue("");
				throw new AppException("������޸Ĳ�֧��ȫѡ����,�빴ѡһ�˽��в�����");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("�����ѡһ�˽��в�����");
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','���ѡ��һ����¼������',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a0000 = a0000s;
		}
		
		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.openWin('jdinformation','pages.publicServantManage.JdInformation','�ල��Ϣ',990,460,'"+a0000+"',ctxPath)");		
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
		
					
	}
	
	//�����޸�
	@PageEvent("betchModifyBtn.onclick")
	@GridDataRange
	public int betchModify() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
		HBSession sess = HBUtil.getHBSession();
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		String tableType = this.getPageElement("tableType").getValue();
		//�б�
		if("1".equals(tableType)){
			betchModifyPageModel.type=1;
			
			CommonQueryBS.systemOut("value-------"+this.getPageElement("checkAll"));
			int i = choose("peopleInfoGrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
				List allSelect = sess.createSQLQuery(sql).list();
				if (!(allSelect.size() > 0)) {
					/*this.setMainMessage("���Ƚ�����Ա��ѯ��");
					return EventRtnType.FAILD;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(allSelect.size() > 500){
					/*this.setMainMessage("��Ա���࣬��һ��ѡ������500�˽��������޸ģ�");
					return EventRtnType.FAILD;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������500�������޸ģ�',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String param = sql+"@1";
				param = param.replaceAll("\\'", "\\$");
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','����ά��',990,460,'"+param+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				StringBuffer ids = new StringBuffer();
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					if(list.size() > 500){
						/*this.setMainMessage("��Ա���࣬��һ��ѡ������500�˽��������޸ģ�");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������500�������޸ģ�',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							//�ж��Ƿ����޸�Ȩ�ޡ�
							String a0000 = map.get("a0000").toString();
							A01 a01 = (A01)sess.get(A01.class, a0000);
							String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
									" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
									" and c.type='0'";
							String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='1' ";
							List elist = sess.createSQLQuery(editableSQL).list();
							List elist2 = sess.createSQLQuery(editableSQL2).list();
				/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
							String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
							if(type == null || !type.contains("'")){
								type ="'zz'";//�滻��������
							}
							List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
							if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
								continue;
							}
							if(elist2==null||elist2.size()==0){//ά��Ȩ��
								/*if(elist!=null&&elist.size()>0){//�����Ȩ��
									continue;
								}else{
									//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
									if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
										continue;
									}else {
										ids.append(map.get("a0000").toString()).append(",");
									}
									
								}*/
							}else {
								ids.append(map.get("a0000").toString()).append(",");
							}
						}
					}
				}
				if(ids.length()==0){
					/*this.setMainMessage("��ѡ��Ա���ɲ�����");
					return EventRtnType.NORMAL_SUCCESS;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա���ɲ�����',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String allids = ids.substring(0,ids.length()-1);
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','�����޸�',990,380,'"+allids+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
				List allSelect = sess.createSQLQuery(sql).list();
				if (!(allSelect.size() > 0)) {
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(allSelect.size() > 500){
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������500�������޸ģ�',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String param = sql+"@1";
				param = param.replaceAll("\\'", "\\$");
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','�����޸�',990,380,'"+param+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//�ж��Ƿ����޸�Ȩ�ޡ�
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			
			//�����޸��������ɳ���500��
			if(str.length > 500){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������500�������޸ģ�',null,220);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//HBSession sess = HBUtil.getHBSession();
			StringBuffer ids = new StringBuffer();
			for(String s : str){
				//�ж��Ƿ����޸�Ȩ�ޡ�
				A01 a01 = (A01)sess.get(A01.class, s);
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
	/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//�滻��������
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+s+"' and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
					continue;
				}
				if(elist2==null||elist2.size()==0){//ά��Ȩ��
					if(elist!=null&&elist.size()>0){//�����Ȩ��
						continue;
					}else{
						//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
						if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
							continue;
						}else {
							ids.append(s).append(",");
						}
						
					}
				}else {
					ids.append(s).append(",");
				}
			}
			if(ids.length()==0){
				/*this.setMainMessage("��ѡ��Ա���ɲ�����");
				return EventRtnType.NORMAL_SUCCESS;*/
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա���ɲ�����',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String allids = ids.substring(0,ids.length()-1);
			this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','����ά��',990,460,'"+allids+"',ctxPath)");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	
	
	
	
	//����¼��
		@PageEvent("quickInBtn.onclick")
		@GridDataRange
		public int quickIn() throws RadowException, AppException{  //�򿪴��ڵ�ʵ��
			HBSession sess = HBUtil.getHBSession();
			String sqlf = this.getPageElement("sql").getValue();
			if(sqlf.equals("")){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			};
			
			String tableType = this.getPageElement("tableType").getValue();
			//�б�
			if("1".equals(tableType)){
				betchModifyPageModel.type=1;
				
				CommonQueryBS.systemOut("value-------"+this.getPageElement("checkAll"));
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
					List allSelect = sess.createSQLQuery(sql).list();
					if (!(allSelect.size() > 0)) {
						/*this.setMainMessage("���Ƚ�����Ա��ѯ��");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}else if(allSelect.size() > 100){
						/*this.setMainMessage("��Ա���࣬��һ��ѡ������500�˽��������޸ģ�");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������100�˽��п���¼�룡',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String param = sql+"@1";
					param = param.replaceAll("\\'", "\\$");
					this.getExecuteSG().addExecuteCode("$h.openWin('quickInWin','pages.publicServantManage.InfoInput','����¼��',1290,600,'"+param+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					StringBuffer ids = new StringBuffer();
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						if(list.size() > 100){
							/*this.setMainMessage("��Ա���࣬��һ��ѡ������500�˽��������޸ģ�");
							return EventRtnType.FAILD;*/
							
							this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������100�˽��п���¼�룡',null,220);");
							return EventRtnType.NORMAL_SUCCESS;
						}
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								//�ж��Ƿ����޸�Ȩ�ޡ�
								String a0000 = map.get("a0000").toString();
								A01 a01 = (A01)sess.get(A01.class, a0000);
								String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
										" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
										" and c.type='0'";
								String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
								" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
								" and c.type='1' ";
								List elist = sess.createSQLQuery(editableSQL).list();
								List elist2 = sess.createSQLQuery(editableSQL2).list();
					/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
								String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
								if(type == null || !type.contains("'")){
									type ="'zz'";//�滻��������
								}
								List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
								if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
									continue;
								}
								if(elist2==null||elist2.size()==0){//ά��Ȩ��
									/*if(elist!=null&&elist.size()>0){//�����Ȩ��
										continue;
									}else{
										//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
										if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
											continue;
										}else {
											ids.append(map.get("a0000").toString()).append(",");
										}
										
									}*/
								}else {
									ids.append(map.get("a0000").toString()).append(",");
								}
							}
						}
					}
					if(ids.length()==0){
						/*this.setMainMessage("��ѡ��Ա���ɲ�����");
						return EventRtnType.NORMAL_SUCCESS;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա���ɲ�����',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String allids = ids.substring(0,ids.length()-1);
					this.getExecuteSG().addExecuteCode("$h.openWin('quickInWin','pages.publicServantManage.InfoInput','�����޸�',1290,600,'"+allids+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//С���ϡ���Ƭ
			if("2".equals(tableType) || "3".equals(tableType)){
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
					List allSelect = sess.createSQLQuery(sql).list();
					if (!(allSelect.size() > 0)) {
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}else if(allSelect.size() > 100){
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������100�˽��п���¼�룡',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String param = sql+"@1";
					param = param.replaceAll("\\'", "\\$");
					this.getExecuteSG().addExecuteCode("$h.openWin('quickInWin','pages.publicServantManage.InfoInput','�����޸�',1290,600,'"+param+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}
				//�ж��Ƿ����޸�Ȩ�ޡ�
				a0000s = a0000s.substring(0, a0000s.length()-1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				
				//�����޸��������ɳ���500��
				if(str.length > 100){
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ������100�˽��п���¼�룡',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				
				//HBSession sess = HBUtil.getHBSession();
				StringBuffer ids = new StringBuffer();
				for(String s : str){
					//�ж��Ƿ����޸�Ȩ�ޡ�
					A01 a01 = (A01)sess.get(A01.class, s);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
		/*			�жϸ���Ա�Ĺ���������Ȩ��------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){
						type ="'zz'";//�滻��������
					}
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+s+"' and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//�޹������ά��Ȩ��,����Ա��Ϣ���ɱ༭
						continue;
					}
					if(elist2==null||elist2.size()==0){//ά��Ȩ��
						if(elist!=null&&elist.size()>0){//�����Ȩ��
							continue;
						}else{
							//���������������ְ��Ա��������ְ��Ա������ְ��Աֻ�ܲ鿴�����ܱ༭��������ְ��Ա�ɲ鿴���ɱ༭
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//����ְ��Ա
								continue;
							}else {
								ids.append(s).append(",");
							}
							
						}
					}else {
						ids.append(s).append(",");
					}
				}
				if(ids.length()==0){
					/*this.setMainMessage("��ѡ��Ա���ɲ�����");
					return EventRtnType.NORMAL_SUCCESS;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա���ɲ�����',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String allids = ids.substring(0,ids.length()-1);
				this.getExecuteSG().addExecuteCode("$h.openWin('quickInWin','pages.publicServantManage.InfoInput','����ά��',1290,600,'"+allids+"',ctxPath)");
			}
			return EventRtnType.NORMAL_SUCCESS;	
		}
	
	
	
	

	
	//��ѯ��ť����¼�
	@PageEvent("btn1.onclick")
	public int selectAll() throws RadowException, PrivilegeException{
		this.request.getSession().setAttribute("queryType", "2");
		this.getPageElement("checkedgroupid").setValue("");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��������
	@PageEvent("warnWinBtn.onclick")
	public int warnWin() throws RadowException {
	    //this.openWindow("warnWin", "pages.publicServantManage.WarnWindow");
	    this.getExecuteSG().addExecuteCode("$h.openWin('warnWin','pages.publicServantManage.WarnWindow','��������',450,300,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//������������
	@PageEvent("importHzbBtn")
	public int expHzbWin(String gsType) throws RadowException {
		
		String tableType = this.getPageElement("tableType").getValue();
		StringBuffer ids = new StringBuffer();
		String hasQueried = this.getPageElement("sql").getValue();
		
		
		if("zip".equals(gsType)){
			String zipid=UUID.randomUUID().toString().replace("-", "");
			try {
				KingbsconfigBS.saveImpDetailInit3(zipid);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Runnable thr=new DataPadImpDBThread(zipid,PrivilegeManager.getInstance().getCueLoginUser());
			new Thread(thr,"Thread_psnexp").start();
			this.setRadow_parent_data(zipid);
		    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','��������',500,300,'"+zipid+"',ctxPath)");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//�б�
		if("".equals(hasQueried) || hasQueried==null){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "";
		if("1".equals(tableType)){
			//Object obj = this.getPageElement("checkAll").getValue();
			int all = choose("peopleInfoGrid","personcheck");
			
			if(all == ON_ONE_CHOOSE){		//�б�û�й�ѡ�������ȫ��
				//String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				/*int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					this.setMainMessage("����ѡ��Ҫ��������Ա");
					return EventRtnType.FAILD;
				}*/
				/*if(StringUtil.isEmpty(allSelect)){
					this.setMainMessage("����ѡ��Ҫ��������Ա");
					return EventRtnType.FAILD;
				}*/
				//String newsql = allSelect.replace("*", "a0000");
				sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
				
				/*List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
				if (allPeople.size() > 0) {
					for(Object a0000 : allPeople){
						ids.append("'").append(a0000).append("',");
					}
				}*/
			}
			else{
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					/*this.setMainMessage("����ѡ��Ҫ��������Ա");
					return EventRtnType.FAILD;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							ids.append("'"+map.get("a0000").toString()).append("',");
						}
					}
				}
			}
		}
		//С���ϡ���Ƭ
		if("2".equals(tableType) || "3".equals(tableType)){
			String a0000s = this.getPageElement("picA0000s").getValue();  //Ϊ�գ������ȫ��
			/*Object obj = null;
			if("2".equals(tableType)){
				obj = this.getPageElement("checkAll2").getValue();
			}
			if("3".equals(tableType)){
				obj = this.getPageElement("checkAll3").getValue();
			}*/
			if("".equals(a0000s)){
				
				/*String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				String newsql = allSelect.replace("*", "a0000");
				*/
				sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
				/*List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
				if (allPeople.size() > 0) {
					for(Object a0000 : allPeople){
						ids.append("'").append(a0000).append("',");
					}
				}*/
			}else{
				//String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					/*this.setMainMessage("����ѡ��Ҫ��������Ա");
					return EventRtnType.FAILD;*/
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				ids = new StringBuffer(a0000s);
			}
		}
		
		//�жϵ�ǰ�б��Ƿ�����Ա
		if("".equals(sql) && ids.length() < 1){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String allids = "".equals(sql)?ids.substring(0,ids.length()-1):sql;
		String id = UUID.randomUUID().toString().replace("-", "");
		
		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			KingbsconfigBS.saveImpDetailInit3(id);
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			Runnable thr = null;
			if("hzb".equals(gsType)){
				thr = new DataPsnImpThread(id, user,"","","","","","","","hzb","","","","",userVo,allids);
			}else if("7z".equals(gsType)){
				thr = new DataPsnImpThread(id, user,"","","","","","","","7z","","","","",userVo,allids);
			}
//			else if("zip".equals(gsType)){
//				//String allSelect=(String)this.request.getSession().getAttribute("allSelect");
//				//allSelect="select a0000 from ("+allSelect+") tem11";
//				//thr = new DataPsnImpDBThread(id, user,"","","","","","","","zip","","","","",userVo,allids,allSelect,this.request.getSession().getId());
//				thr=new DataPadImpDBThread(id,userVo);
//			}
			new Thread(thr,"Thread_psnexp").start();
			this.setRadow_parent_data(id);
		    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','��������',500,300,'"+id+"',ctxPath)");
		    
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�������
	@PageEvent("exportLrmxBtn")
	public int expWin() throws RadowException {
		/*int i = choose("peopleInfoGrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("��ѡ��Ҫ��������Ա");
			return EventRtnType.FAILD;
		}*/
		String hasQueried = this.getPageElement("sql").getValue();
		if("".equals(hasQueried) || hasQueried==null){
			//this.setMainMessage("δ���й���ѯ���Ȳ�ѯ!");
			
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//�ж��б��Ƿ�������
		List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
		
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		this.setNextEventName("export");
		//this.openWindow("expTimeWin", "pages.publicServantManage.ExpTimeWindow");
//	    this.getExecuteSG().addExecuteCode("$h.openWin('expTimeWin','pages.publicServantManage.ExpTimeWindow','�������',450,300,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//������ӡ
//	@PageEvent("batchPrint.onclick")
//	public int batchPrintTime() throws RadowException, AppException{
//		int i = choose("peopleInfoGrid","personcheck");
//		if (i == ON_ONE_CHOOSE ) {
//			this.setMainMessage("���Ȳ�ѯ��Ա��Ϣ������ѡҪ������ӡ����Ա��");
//			return EventRtnType.FAILD;
//		}
//		this.openWindow("batchPrintTimeWin", "pages.publicServantManage.batchPrintTimeWindow");
//		//this.getExecuteSG().addExecuteCode("$h.openWin('batchPrintTimeWin','pages.publicServantManage.batchPrintTimeWindow','������ӡ',600,250,document.getElementById('a0000').value,ctxPath)");
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	//����Lrm
		@PageEvent("exportLrmBtn")
		@GridDataRange
		public int exportLrm() throws RadowException{ 
			String tableType = this.getPageElement("tableType").getValue();
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//�ж��б��Ƿ�������
			List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
			
			if(list22.size() == 0){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			if("1".equals(tableType)){
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {			//�б�û�й�ѡ�������ȫ��
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						Runtime rt = Runtime.getRuntime();
						Process p = null;
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							
							a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
										
									}
								}
								
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
							
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(i==CHOOSE_OVER_TOW){
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						Runtime rt = Runtime.getRuntime();
						Process p = null;
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
								String lrm = createLrmStr(a0000);
								name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//								System.out.println(zippath);
								try {
									FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
									List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
									if(list15.size()>0){
										A57 a57 = list15.get(0);
										if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
											File f = new File(zippath + name+".pic");
											FileOutputStream fos = new FileOutputStream(f);
											File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
											if(f2.exists() && f2.isFile()){
												InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
												byte[] data = new byte[1024];
												while (is.read(data) != -1) {
													fos.write(data);
												}
												is.close();
											}
											fos.close();
											
										}
									}
//									String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//									p = rt.exec(cmdd);
//									p.waitFor();
								} catch (Exception e) {
									this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
								}
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//							String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//							p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
//					System.out.println(ids.toString());
					return EventRtnType.NORMAL_SUCCESS;
				}else {
					PageElement pe = this.getPageElement("peopleInfoGrid");
					String a0000 ="";
					if(pe!=null){
						StringBuffer ids = new StringBuffer();
						List<HashMap<String, Object>> list = pe.getValueList();
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								ids.append(this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString());
							}
						}
						a0000 = ids.toString();
					}
					
					String lrm = createLrmStr(a0000);
					String name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
					String zippath =ExpRar.expFile();
//					System.out.println(zippath);
					String infile ="";
					try {
						FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
						List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
						if(list15.size()>0){
							A57 a57 = list15.get(0);
							if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
								File f = new File(zippath + name+".pic");
								FileOutputStream fos = new FileOutputStream(f);
								File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
								if(f2.exists() && f2.isFile()){
									InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
									byte[] data = new byte[1024];
									while (is.read(data) != -1) {
										fos.write(data);
									}
									is.close();
								}
								fos.close();
								
							}
						}
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
						Runtime rt = Runtime.getRuntime();
						Process p = null;
//						String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//						p = rt.exec(cmdd);
//						p.waitFor();
//						String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//						p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
					} catch (Exception e) {
						e.printStackTrace();
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				}
			}else if("2".equals(tableType) || "3".equals(tableType)){ //С���ϡ���Ƭ
				HBSession sess = HBUtil.getHBSession();
				String a0000s = this.getPageElement("picA0000s").getValue();
				
				if(a0000s == null || "".equals(a0000s.trim())){	//û�й�ѡ �򵼳�ȫ��
					
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
										
									}
								}
								
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
							
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}else{			//���չ�ѡ��Ա����
					a0000s = a0000s.substring(0, a0000s.length()-1);
					a0000s = a0000s.replaceAll("\\'", "");
					String[] str = a0000s.split(",");
					if(str != null && str.length > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						for(int j = 0; j < str.length; j++){
							String a0000 = str[j];
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//								System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
										
									}
								}
//									String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//									p = rt.exec(cmdd);
//									p.waitFor();
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//							String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//							p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
//					System.out.println(ids.toString());
					return EventRtnType.NORMAL_SUCCESS;
				}
					
			}
			return EventRtnType.NORMAL_SUCCESS;	
		}
		
		//����Pdf
		@PageEvent("exportPdfBtn.onclick")
		@GridDataRange
		public int exportPdf() throws RadowException{ 
			String tableType = this.getPageElement("tableType").getValue();
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				//this.setMainMessage("δ���й���ѯ���Ȳ�ѯ!");
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//�ж��б��Ƿ�������
			List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
			
			if(list22.size() == 0){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			if("1".equals(tableType)){
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {					//�б�û�й�ѡ�������ȫ��
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						Runtime rt = Runtime.getRuntime();
						Process p = null;
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							
							a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
										
									}
								}
								SevenZipUtil.zzbRmb(zippath, name);
								
								File dec = new File(zippath);
								File[] files = dec.listFiles();
								for (File f0 : files) {
									if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
										f0.delete();
									}
								}
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(i==CHOOSE_OVER_TOW){
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						Runtime rt = Runtime.getRuntime();
						Process p = null;
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
								String lrm = createLrmStr(a0000);
								name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		//						System.out.println(zippath);
								try {
									FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
									List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
									if(list15.size()>0){
										A57 a57 = list15.get(0);
										if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
											File f = new File(zippath + name+".pic");
											FileOutputStream fos = new FileOutputStream(f);
											File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
											if(f2.exists() && f2.isFile()){
												InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
												byte[] data = new byte[1024];
												while (is.read(data) != -1) {
													fos.write(data);
												}
												is.close();
											}
											fos.close();
											
										}
									}
									SevenZipUtil.zzbRmb(zippath, name);
		//							String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
		//							p = rt.exec(cmdd);
		//							p.waitFor();
									File dec = new File(zippath);
									File[] files = dec.listFiles();
									for (File f0 : files) {
										if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
											f0.delete();
										}
									}
								} catch (Exception e) {
									this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
								}
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
		//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
		//					p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
		//			System.out.println(ids.toString());
					return EventRtnType.NORMAL_SUCCESS;
				}else {
					PageElement pe = this.getPageElement("peopleInfoGrid");
					String a0000 ="";
					if(pe!=null){
						StringBuffer ids = new StringBuffer();
						List<HashMap<String, Object>> list = pe.getValueList();
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								ids.append(this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString());
							}
						}
						a0000 = ids.toString();
					}
					
					String lrm = createLrmStr(a0000);
					String name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
					String zippath =ExpRar.expFile();
		//			System.out.println(zippath);
					String infile ="";
					try {
						FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
						List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
						if(list15.size()>0){
							A57 a57 = list15.get(0);
							if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
								File f = new File(zippath + name+".pic");
								FileOutputStream fos = new FileOutputStream(f);
								File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
								if(f2.exists() && f2.isFile()){
									InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
									byte[] data = new byte[1024];
									while (is.read(data) != -1) {
										fos.write(data);
									}
									is.close();
								}
								fos.close();
								
							}
						}
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
		//				Runtime rt = Runtime.getRuntime();
		//				Process p = null;
						SevenZipUtil.zzbRmb(zippath, name);
		//				String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
		//				p = rt.exec(cmdd);
		//				p.waitFor();
						File dec = new File(zippath);
						File[] files = dec.listFiles();
						for (File f0 : files) {
							if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
								f0.delete();
							}
						}
		//				String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";					
		//				p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				}
				
			}else if("2".equals(tableType) || "3".equals(tableType)){
				HBSession sess = HBUtil.getHBSession();
				String a0000s = this.getPageElement("picA0000s").getValue();
				
				if(a0000s == null || "".equals(a0000s.trim())){	//û�й�ѡ �򵼳�ȫ��
					
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
									}
								}
								SevenZipUtil.zzbRmb(zippath, name);
								File dec = new File(zippath);
								File[] files = dec.listFiles();
								for (File f0 : files) {
									if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
										f0.delete();
									}
								}
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}else{			//���չ�ѡ��Ա����
					a0000s = a0000s.substring(0, a0000s.length()-1);
					a0000s = a0000s.replaceAll("\\'", "");
					String[] str = a0000s.split(",");
					if(str != null && str.length > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						for(int j = 0; j < str.length; j++){
							String a0000 = str[j];
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//							System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();
										
									}
								}
								SevenZipUtil.zzbRmb(zippath, name);
								File dec = new File(zippath);
								File[] files = dec.listFiles();
								for (File f0 : files) {
									if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
										f0.delete();
									}
								}
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.reloadTree()");
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
				}
			}
			return EventRtnType.NORMAL_SUCCESS;	
		}

	//����Lrmx
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String time) throws RadowException{ 
		String hasQueried = this.getPageElement("sql").getValue();
		if("".equals(hasQueried) || hasQueried==null){
			this.setMainMessage("δ���й���ѯ���Ȳ�ѯ!");
		}
		time = "";//ȥ������ʱ�䴰�ں�Ĭ��ʱ��Ϊ�գ��Ժ�Ҫ�ָ�ʱע�ͼ���
		String tableType = this.getPageElement("tableType").getValue();
		if("1".equals(tableType)){
			int i = choose("peopleInfoGrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {						//û��ѡ�в���ȫ��
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						
						a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
						PersonXml per = createLrmxStr(a0000,time);
						name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
						
						try {
							FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
		
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
						
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
						
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
				
				return EventRtnType.NORMAL_SUCCESS;
				
			}
			if(i==CHOOSE_OVER_TOW){
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					Runtime rt = Runtime.getRuntime();
					Process p = null;
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
							PersonXml per = createLrmxStr(a0000,time);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		//					System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
		//						String cmdd = "\"D:\\Program Files (x86)\\�����༭��V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
		//						p = rt.exec(cmdd);
		//						p.waitFor();
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
		//				String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
		//				p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
		//		System.out.println(ids.toString());
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				String a0000 = "";
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					String zippath =ExpRar.expFile();
					String infile ="";
					String name="";
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
							PersonXml per = createLrmxStr(a0000,time);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
			//					System.out.println(zippath);
							try {
								FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
							}
						}
					}
					try {
						infile =zippath+name+".lrmx" ;
						//SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
			
			}
		}else if("2".equals(tableType) || "3".equals(tableType)){
			HBSession sess = HBUtil.getHBSession();
			String a0000s = this.getPageElement("picA0000s").getValue();
			
			if(a0000s == null || "".equals(a0000s.trim())){	//û�й�ѡ �򵼳�ȫ��
				
				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
				List<String> list = sess.createSQLQuery(sql).list();
				String a0000 = "";
				if(list !=null && list.size() > 0){
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					for(int j = 0;j < list.size(); j++){
						a0000 = list.get(j);
						PersonXml per = createLrmxStr(a0000,time);
						name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
						try {
							FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
				return EventRtnType.NORMAL_SUCCESS;
			}else{			//���չ�ѡ��Ա����
				a0000s = a0000s.substring(0, a0000s.length()-1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				if(str != null && str.length > 0){
					String zippath =ExpRar.expFile();
					String infile ="";
					String name ="";
					for(int j = 0; j < str.length; j++){
						String a0000 = str[j];
						PersonXml per = createLrmxStr(a0000,time);
						name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
						try {
							FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
							A01 a01log = new A01();
							new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX����", new Map2Temp().getLogInfo(new A01(), a01log));
						} catch (Exception e) {
							this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
						}
					}
					try {
						infile =zippath.substring(0,zippath.length()-1)+".zip" ;
						SevenZipUtil.zip7z(zippath, infile, null);
						this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					} catch (Exception e) {
						this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
					}
				}
			}
		}
//			System.out.println(ids.toString());
		return EventRtnType.NORMAL_SUCCESS;
	}

/**
 * ����Lrm�ļ���Ĭ�ϴ�ӡ��������Ϣ
 * @param ids
 * @return
 */
public String createLrmStr(String ids){
	return createLrmStr(ids,true);
}

public String createLrmStr(String ids,boolean falg){
	return createLrmStr(ids,falg,null);
}

/**
 * ����Lrm�ļ�
 * @param ids ��ԱID a0000
 * @param falg �Ƿ��ӡ��������Ϣ��true-��ӡ��������Ϣ
 * @return
 */
public String createLrmStr(String ids,boolean falg,String printTime){
	
	String useTime = printTime;
	String a0000 = ids;
	String userid = SysUtil.getCacheCurrentUser().getUserVO().getId();
	DBType cueDBType = DBUtil.getDBType();
	String str ="";
	String jiatingchengyuan="";
	String laststr2 = "";
	String laststr1 ="";
	if(cueDBType==DBType.MYSQL){
		ResultSet rs = null;
		try {
			rs = HBUtil.getHBSession().connection().prepareStatement("select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''), ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
					+"from a01 t  where t.a0000='"+a0000+"'").executeQuery();
			if(rs != null && rs.next()){
				str = rs.getString(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		str = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"',t.a0101,'\",\"',( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1'),'\",\"',T.A0107,'\",\"',( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1'),'\",\"',T.A0111A,'\",\"',replace(replace(t.a0140, '(', ''), ')', ''),'\",\"',t.a0128,'\",\"', T.a0114A,'\",\"', T.a0134,'\",\"',t.qrzxl,'#',t.qrzxw,'@',t.zzxl,'#',t.zzxw,'\",\"',t.qrzxlxx,'#',t.qrzxwxx,'@',t.zzxlxx,'#',t.zzxwxx,'\",\"',T.A0196,'\",\"\",\"\",\"\",\"\",\"',t.a0187a,'\",\"',t.A1701,'\",\"',t.A14Z101,'\",\"',t.A15Z101,'\"') "
//				+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();	
		jiatingchengyuan = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , replace(group_concat(t.a3604a order by sortid), ',', '@') , '|\",\"' , replace(group_concat(t.a3601 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3607 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3627 order by sortid), ',', '@') , '|\",\"' ,       replace(group_concat(t.a3611 order by sortid), ',', '@') , '|\"')  "
			 +"from (select a0000, ifnull(a3604a, '#') a3604a, ifnull(a3601, '#') a3601, ifnull(a3607, '#') a3607, ifnull(a3627, '#') a3627, ifnull(a3611, '#') a3611, sortid from a36 where a36.a0000 = '"+a0000+"' order by a36.sortid) t").uniqueResult();
		if(StringUtil.isEmpty(useTime)){
			laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
		}else{
			laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"' , max(ifnull(t.a5304, '')) , '\",\"' , max(ifnull(t.a5315, '')) ,'\",\"' , max(ifnull(t.a5317, '')) , '\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , date_format(now(), '%Y%m%d') , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("+useTime+") , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
		}
		
		laststr1 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"',max(ifnull(t.a0192a,'')), '\"') from a01 t where t.a0000='"+a0000+"'").uniqueResult();
	}else{
		str = (String) HBUtil.getHBSession().createSQLQuery("select to_char('\"'||t.a0101||'\",\"'||( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1')||'\",\"'||T.A0107||'\",\"'||"
				+"( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1')||'\",\"'||T.A0111A||'\",\"'||replace(replace(t.a0140, '(', ''), ')', '')||'\",\"'||"
				+"t.a0128||'\",\"'|| T.a0114A||'\",\"'|| T.a0134||'\",\"'||"
				+"t.qrzxl||'#'||t.qrzxw||'@'||t.zzxl||'#'||t.zzxw||"
				+"'\",\"'||t.qrzxlxx||'#'||t.qrzxwxx||'@'||t.zzxlxx||'#'||t.zzxwxx||'\",\"'||"
				+"T.A0196||'\",\"\",\"\",\"\",\"\",\"'||t.a0187a||'\",\"'||"
				+"t.A1701||'\",\"'||t.A14Z101||'\",\"'||t.A15Z101||'\"')"
				+"from a01 t  where t.a0000='"+a0000+"'").uniqueResult();
		jiatingchengyuan = (String) HBUtil.getHBSession().createSQLQuery("select to_char('\"' || replace(wm_concat(t.a3604a), ',', '@') || '|\",\"' ||"
			       +"replace(wm_concat(t.a3601), ',', '@') || '|\",\"' ||"
			       +"replace(wm_concat(t.a3607), ',', '@') || '|\",\"' ||"
			       +"replace(wm_concat(t.a3627), ',', '@') || '|\",\"' ||"
			       +"replace(wm_concat(t.a3611), ',', '@') || '|\"')"
			 +" from (select a0000,  nvl( a3604a,'#') a3604a,  nvl(a3601,'#')a3601,  nvl(a3607,'#')a3607, nvl( a3627,'#') a3627,nvl(a3611,'#')a3611,sortid from a36 where a36.a0000 = '"+a0000+"' order by a36.sortid)  t").uniqueResult();
		if(StringUtil.isEmpty(useTime)){
			laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
		}else{
			laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a5304,''))||'\",\"'||max(nvl(t.a5315,''))||'\",\"'||max(nvl(t.a5317,''))||'\",\"'||max(nvl(t.a5319,''))||'\",\"'||to_char(sysdate ,'YYYYMMDD')||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("+useTime+")||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
		}
		
		laststr1 = (String) HBUtil.getHBSession().createSQLQuery("select '\"'||max(nvl(t.a0192a,''))|| '\"' from a01 t where t.a0000='"+a0000+"'").uniqueResult();
	}
//	System.out.println(str);
	String count =    HBUtil.getHBSession().createSQLQuery("Select Count(1) From a36 t where t.a0000='"+a0000+"'").list().get(0).toString();
	String append ="";
	if(Integer.valueOf(count)<13){
		for(int j=6 ;j>Integer.valueOf(count)-1;j--){
			append+="@";
		}
	}
	jiatingchengyuan=jiatingchengyuan.replace("#", "");
	CommonQueryBS.systemOut(jiatingchengyuan.replace("|",append));
//	System.out.println(laststr1);
//	System.out.println(str+","+jiatingchengyuan.replace("|",append)+","+laststr);
	//�Ƿ��ӡ��������Ϣ
	if(!falg){
		if(cueDBType==DBType.MYSQL){
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max(ifnull(t.a5323, '')) , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select CONCAT_WS('','\"','\",\"','\",\"','\",\"' , max(ifnull(t.a5319, '')) ,'\",\"' , max(ifnull(t.a5321, '')) , '\",\"' , max(ifnull(t.a5327, '')) ,'\",\"' , max("+useTime+") , '\"')from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
		}else{
			if(StringUtil.isEmpty(useTime)){
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max(nvl(t.a5323,''))||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}else{
				laststr2 = (String) HBUtil.getHBSession().createSQLQuery("select '\"\",\"\",\"\",\"'||max(nvl(t.a5319,''))||'\",\"'||max(nvl(t.a5321,''))||'\",\"'||max(nvl(t.a5327,''))||'\",\"'||max("+useTime+")||'\"' from a53 t where t.a0000 = '"+a0000+"' and t.a5399 = '"+userid+"'").uniqueResult();
			}
		}	
	}
	
	String lrm = str+","+jiatingchengyuan.replace("|",append)+","+laststr1+","+laststr2;
	return lrm;
}

public PersonXml createLrmxStr(String ids,String time){
	String a0000 = ids;
//	String content = "";
//	try {
//		content = QueryPersonListBS.XmlContentBuilder(QueryPersonListBS.getObjXml(a0000));
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return content;
	PersonXml a = new PersonXml();
	JiaTingChengYuanXml jiaTingChengYuanXml=new JiaTingChengYuanXml();
	List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
	List<ItemXml> itemlist = new ArrayList<ItemXml>();
	HBSession sess = HBUtil.getHBSession();
	String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid";
	List lista36 = sess.createQuery(sqla36).list();
	String userid = SysUtil.getCacheCurrentUser().getId();
	A01 a01 = (A01)sess.get(A01.class, a0000);
	A57 a57 = (A57)sess.get(A57.class, a0000);
	String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
	List<A53> list = sess.createQuery(sqla53).list();
	Object xb = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"+a01.getA0104()+"'").uniqueResult();
	Object mz = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"+a01.getA0117()+"'").uniqueResult();
	if(a57!=null){
    	byte[] data = PhotosUtil.getPhotoData(a57);
		if(data!=null){
			a.setZhaoPian(data);
		}
    }
	a.setXingMing(a01.getA0101());
	a.setXingBie(xb!=null?xb.toString():"");                                    
	a.setChuShengNianYue(a01.getA0107());                            
	a.setMinZu(mz!=null?mz.toString():"");                                      
	a.setJiGuan(a01.getComboxArea_a0111());                                     
	a.setChuShengDi(a01.getComboxArea_a0114());
	a.setRuDangShiJian(a01.getA0140());
	a.setCanJiaGongZuoShiJian(a01.getA0134());
	a.setJianKangZhuangKuang(a01.getA0128());      
	a.setZhuanYeJiShuZhiWu(a01.getA0196());
	a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
	a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
	a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
	a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
	a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
	a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
	a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
	a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
	a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
	a.setXianRenZhiWu(a01.getA0192a());                               
	a.setJianLi(a01.getA1701());                                     
	a.setJiangChengQingKuang(a01.getA14z101());                        
	a.setNianDuKaoHeJieGuo(a01.getA15z101());
	a.setShenFenZheng(a01.getA0184());
	if(list==null||list.size()==0){
		a.setNiRenZhiWu("");                                 
		a.setNiMianZhiWu("");
		a.setRenMianLiYou("");                               
		a.setChengBaoDanWei("");                             
		a.setJiSuanNianLingShiJian("");                      
		a.setTianBiaoShiJian(time);
		a.setTianBiaoRen("");
	}else{
		List lista53 = sess.createQuery(sqla53).list();
		A53 a53 = (A53)lista53.get(0);
		a.setNiRenZhiWu(a53.getA5304());                                 
		a.setNiMianZhiWu(a53.getA5315());
		a.setRenMianLiYou(a53.getA5317());                               
		a.setChengBaoDanWei(a53.getA5319());                             
		a.setJiSuanNianLingShiJian(""); 
//		if(StringUtil.isEmpty(time)){
//			a.setTianBiaoShiJian(a53.getA5323());
//		}else{
		a.setTianBiaoShiJian(time);
//		}
		a.setTianBiaoRen("");
	}
	if(lista36!=null&&lista36.size()>0){
		for(int i=1;i<=lista36.size();i++){
			ItemXml b =new ItemXml();
			A36 a36 = (A36) lista36.get(i-1);
			//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
		    b.setChengWei(a36.getA3604a()!=null?a36.getA3604a().toString():"");
			b.setChuShengRiQi(a36.getA3607());
			b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
			b.setXingMing(a36.getA3601());
			//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
		    b.setZhengZhiMianMao(a36.getA3627()!=null?a36.getA3627().toString():"");
			itemlist.add(b);
		}
	}
	jiaTingChengYuanXml.setItem(itemlist);
	jtcyList.add(jiaTingChengYuanXml);
	a.setJiaTingChengYuan(jtcyList);
	return a;
}

public PersonXml createLrmxStr(String ids,String time, String xt){
	String a0000 = ids;
//	String content = "";
//	try {
//		content = QueryPersonListBS.XmlContentBuilder(QueryPersonListBS.getObjXml(a0000));
//	} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//	return content;
	PersonXml a = new PersonXml();
	JiaTingChengYuanXml jiaTingChengYuanXml=new JiaTingChengYuanXml();
	List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
	List<ItemXml> itemlist = new ArrayList<ItemXml>();
	HBSession sess = HBUtil.getHBSession();
	String sqla36 = "select * from A36 where a0000='"+a0000+"' order by sortid";
	A01 a01 = null;
	A57 a57 = null;
	if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
		sqla36 = "select * from v_js_A36 where a0000='"+a0000+"' and v_xt='"+xt+"' order by sortid";
		List lista01 = sess.createSQLQuery("select * from  v_js_A01 where a0000='"+a0000+"' and v_xt='"+xt+"'").addEntity(A01.class).list();
		List lista57 = sess.createSQLQuery("select * from  v_js_A57 where a0000='"+a0000+"' and v_xt='"+xt+"'").addEntity(A57.class).list();
		a01 = (A01) lista01.get(0);
		if(lista57.size()>0) {
			a57 = (A57) lista57.get(0);
		}
	} else {
		xt = "1";
		a01 = (A01)sess.get(A01.class, a0000);
		a57 = (A57)sess.get(A57.class, a0000);
	}
	List lista36 = sess.createSQLQuery(sqla36).addEntity(A36.class).list();
	String userid = SysUtil.getCacheCurrentUser().getId();
	
	//String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
	//List<A53> list = sess.createQuery(sqla53).list();
	Object xb = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"+a01.getA0104()+"'").uniqueResult();
	Object mz = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"+a01.getA0117()+"'").uniqueResult();
	if(a57!=null){
    	byte[] data = PhotosUtil.getPhotoData(a57,xt);
		if(data!=null){
			a.setZhaoPian(data);
		}
    }
	a.setXingMing(a01.getA0101());
	a.setXingBie(xb!=null?xb.toString():"");                                    
	a.setChuShengNianYue(a01.getA0107());                            
	a.setMinZu(mz!=null?mz.toString():"");                                      
	a.setJiGuan(a01.getComboxArea_a0111());                                     
	a.setChuShengDi(a01.getComboxArea_a0114());
	a.setRuDangShiJian(a01.getA0140());
	a.setCanJiaGongZuoShiJian(a01.getA0134());
	a.setJianKangZhuangKuang(a01.getA0128());      
	a.setZhuanYeJiShuZhiWu(a01.getA0196());
	a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
	a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
	a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
	a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
	a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
	a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
	a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
	a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
	a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
	a.setXianRenZhiWu(a01.getA0192a());                               
	a.setJianLi(a01.getA1701());                                     
	a.setJiangChengQingKuang(a01.getA14z101());                        
	a.setNianDuKaoHeJieGuo(a01.getA15z101());
	a.setShenFenZheng(a01.getA0184());
	
	List list = sess.createQuery(" from Js01 where a0000='"+a0000+"' and js0122='"+xt+"'").list();
	if(list==null||list.size()==0){
		a.setNiRenZhiWu("");                                 
		a.setNiMianZhiWu("");
		a.setRenMianLiYou("");                               
		a.setChengBaoDanWei("");                             
		a.setJiSuanNianLingShiJian("");                      
		a.setTianBiaoShiJian(time);
		a.setTianBiaoRen("");
	}else{
		//List lista53 = sess.createQuery(sqla53).list();
		Js01 js01 = (Js01)list.get(0);
		a.setNiRenZhiWu(js01.getJs0111());                                 
		a.setNiMianZhiWu(js01.getJs0117());
		a.setRenMianLiYou("��֯ѡ������");                               
		a.setChengBaoDanWei("������ί��֯��");                             
		a.setJiSuanNianLingShiJian(""); 
//		if(StringUtil.isEmpty(time)){
//			a.setTianBiaoShiJian(a53.getA5323());
//		}else{
		a.setTianBiaoShiJian(time);
//		}
		a.setTianBiaoRen("");
	}
	if(lista36!=null&&lista36.size()>0){
		for(int i=1;i<=lista36.size();i++){
			ItemXml b =new ItemXml();
			A36 a36 = (A36) lista36.get(i-1);
			//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
		    b.setChengWei(a36.getA3604a()!=null?a36.getA3604a().toString():"");
			b.setChuShengRiQi(a36.getA3607());
			b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
			b.setXingMing(a36.getA3601());
			//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
		    b.setZhengZhiMianMao(a36.getA3627()!=null?a36.getA3627().toString():"");
			itemlist.add(b);
		}
	}
	jiaTingChengYuanXml.setItem(itemlist);
	jtcyList.add(jiaTingChengYuanXml);
	a.setJiaTingChengYuan(jtcyList);
	return a;
}

public static byte[] toByte(Blob blob) throws SQLException, IOException{
	  byte[] im = new byte[(int) blob.length()];
	  BufferedInputStream is = new BufferedInputStream(blob.getBinaryStream());
	  int len = (int) blob.length();
	  int offset = 0;
	  int read = 0;
	  
	  try {
	   while (offset < len && (read = is.read(im, offset, len - offset)) >= 0) {
	     offset += read;
	    }
	  } catch (IOException e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }finally{
	   is.close();
	   is=null;
	  }
	  
	  return im;
	 }


/**
 * ��PDFԤ������
 * 
 * @param a0000AndFlag a0000 ����ԱID���� flag���Ƿ��ӡ��������Ϣ��ƴ�ӵĲ������ö��ŷָ�
 * @return
 * @throws RadowException
 * @throws AppException
 * @author mengl
 * @throws IOException 
 * @date 2016-06-03
 */
@PageEvent("printView")
public int pdfView(String a0000AndFlag) throws RadowException, AppException, IOException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0]; 										//��ԱID
	Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
	String pdfPath = "";  											//pdf�ļ�·��
	
	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		if("pdf1.1".equals(pdfmenu)){
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf1.1",a0000,list2, params);			
			this.getPageElement("printPdf").setValue("");
		}else{
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2, params);			
		}
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
	QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
	newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
	newPDFPath = "/hzb"+ newPDFPath;
	this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
	this.getPageElement("pdfPath").setValue(newPDFPath);
	String ctxPath = request.getContextPath();
	this.getExecuteSG().addExecuteCode("$h.openPageModeWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,1,'"+ctxPath+"')");
	/*List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
	
	pdfPath = pdfPaths.get(0);
	pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
	pdfPath = "/hzb"+pdfPath;
//	pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//	String contextStr = this.request.getContextPath().replace("/", "\\");
//	pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
	
	//this.setRadow_parent_data(pdfPath);
	//this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");
	this.getPageElement("pdfPath").setValue(pdfPath);
	this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,'"+a0000+"',ctxPath)");
	//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+pdfPath+"')");
*/	return EventRtnType.NORMAL_SUCCESS;
}


@PageEvent("printViewNew")
public int pdfViewNew(String a0000AndFlag) throws RadowException, AppException, IOException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0]; 										//��ԱID
	Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
	String pdfPath = "";  											//pdf�ļ�·��
	
	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		if("pdf1.1".equals(pdfmenu)){
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf1.1",a0000,list2, params);			
			this.getPageElement("printPdf").setValue("");
		}else{
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2, params);			
		}
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
	QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
	newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
	newPDFPath = "/hzb"+ newPDFPath;
	this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
	this.getPageElement("pdfPath").setValue(newPDFPath);
	String ctxPath = request.getContextPath();
	//this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,1,'"+ctxPath+"')");
	/*List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list,flag);
	
	pdfPath = pdfPaths.get(0);
	pdfPath = pdfPath.substring(pdfPath.indexOf("ziploud")-1).replace("\\", "/");
	pdfPath = "/hzb"+pdfPath;
//	pdfPath = pdfPath.substring(pdfPath.indexOf("insiis6")-1).replace("\\", "/");
//	String contextStr = this.request.getContextPath().replace("/", "\\");
//	pdfPath = pdfPath.substring(pdfPath.indexOf(contextStr)).replace("\\", "/");
	
	//this.setRadow_parent_data(pdfPath);
	//this.openWindow("pdfViewWin", "pages.publicServantManage.PdfView");
	this.getPageElement("pdfPath").setValue(pdfPath);
	this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','��ӡ�����',700,500,'"+a0000+"',ctxPath)");
	//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+pdfPath+"')");
*/	return EventRtnType.NORMAL_SUCCESS;
}
/*
 * ��������׸ı�
 */
@PageEvent("prtTG")
public int prtTG(String a0000AndFlag) throws RadowException, AppException, IOException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0]; 										//��ԱID
	Boolean flag = params[1].equalsIgnoreCase("true")?true:false;  	//�Ƿ��ӡ��������Ϣ
	String pdfPath = "";  											//pdf�ļ�·��
	
	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"28bc4d39-dccd-4f07-8aa9","pdf",a0000,list2, params);			
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
	QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
	newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
	newPDFPath = "/hzb"+ newPDFPath;
	this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
	this.getPageElement("pdfPath").setValue(newPDFPath);
	String ctxPath = request.getContextPath();
	return EventRtnType.NORMAL_SUCCESS;
}



/**
 * �Թ�ѡ����Ա������ӡǰ����֤
 * @return
 * @throws RadowException 
 * @throws AppException 
 * @author mengl
 */
//@PageEvent("batchPrint.onclick")
//@NoRequiredValidate
public int batchPrintBefore() throws RadowException, AppException{
	
	
	List<HashMap<String,Object>> list =  this.getPageElement("peopleInfoGrid").getValueList();
	if(list==null || list.size()<1 ){
		this.setMainMessage("���Ȳ�ѯ��Ա��Ϣ������ѡҪ������ӡ����Ա��");
		return EventRtnType.NORMAL_SUCCESS;
	}
	this.getExecuteSG().addExecuteCode("$h.confirm3btn('ϵͳ��ʾ','��ӡ��������Ƿ������������Ϣ��',200,function(id){" +
			"if(id=='yes'){" +
			"			radow.doEvent('batchPrint','true');" +
				"}else if(id=='no'){" +
				"			radow.doEvent('batchPrint','false');" +
				"}else if(id=='cancel'){" +
				"	" +
				"}" +
			"});");
	
	return EventRtnType.NORMAL_SUCCESS;
}

/**
 * �Թ�ѡ����Ա������ӡ
 * @return
 * @throws RadowException 
 * @throws AppException 
 * @author mengl
 */
@PageEvent("exportPdfForAspose.onclick")
@NoRequiredValidate
public int exportPdfForAspose() throws RadowException, AppException{
	
	String hasQueried = this.getPageElement("sql").getValue();
	if("".equals(hasQueried) || hasQueried==null){
		//this.setMainMessage("δ���й���ѯ���Ȳ�ѯ!");
		
		this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//�ж��б��Ƿ�������
	List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
	
	if(list22.size() == 0){
		this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	boolean flagNrm =true;	//�Ƿ��ӡ��������Ϣ
	int i = choose("peopleInfoGrid","personcheck");
	if (i == ON_ONE_CHOOSE ) {
		String pdfPath = "";
		String a0000 = "";
		PageElement pe = this.getPageElement("peopleInfoGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < list.size(); j++){
				HashMap<String, Object> map = list.get(j);
				a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
				list2.add(a0000);
			}
			String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2);
			String infile ="";
			try {
				infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//					p = Runtime.getRuntime().exec(cmd);
				SevenZipUtil.zip7z(pdfPaths, infile, null);
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.reloadTree()");
			} catch (Exception e) {
				this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
			}
			
		}else{
			this.setMainMessage("���ѯ��Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}else{
		String printTime = "";
		String newPDFPath = "";
		List<HashMap<String, Object>> list = this.getPageElement(
				"peopleInfoGrid").getValueList();
		// ��ѡ����ԱID
		List<String> a0000s = new ArrayList<String>();
		String infile ="";
		for (HashMap<String, Object> map : list) {
			if (map != null&& map.get("personcheck").toString().equalsIgnoreCase("true")) {
					a0000s.add((String) map.get("a0000"));
				}
		}
		String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(a0000s);
		try {
			infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
//				String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//				p = Runtime.getRuntime().exec(cmd);
			SevenZipUtil.zip7z(pdfPaths, infile, null);
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
		}
	}
	
	return EventRtnType.NORMAL_SUCCESS;
}

/**
 * �Թ�ѡ����Ա������ӡ
 * @return
 * @throws RadowException 
 * @throws AppException 
 * @author mengl
 */
@PageEvent("batchPrint.onclick")
@NoRequiredValidate
public int batchPrint() throws RadowException, AppException{
	
	String hasQueried = this.getPageElement("sql").getValue();
	if("".equals(hasQueried) || hasQueried==null){
		//this.setMainMessage("δ���й���ѯ���Ȳ�ѯ!");
		
		this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//�ж��б��Ƿ�������
	List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
	
	if(list22.size() == 0){
		this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	boolean flagNrm =true;	//�Ƿ��ӡ��������Ϣ
	int i = choose("peopleInfoGrid","personcheck");
	if (i == ON_ONE_CHOOSE ) {
		String pdfPath = "";
		String a0000 = "";
		PageElement pe = this.getPageElement("peopleInfoGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < list.size(); j++){
				HashMap<String, Object> map = list.get(j);
				a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
				list2.add(a0000);
			}
			List<String> pdfPaths = new QueryPersonListBS().getPdfsByA000s(list2,flagNrm);
			String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
			QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
			newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
			newPDFPath = "/hzb"+ newPDFPath;
			this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+newPDFPath+"')");
		}else{
			this.setMainMessage("���ѯ��Ա");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}else{
		String printTime = "";
		String newPDFPath = "";
		List<HashMap<String,Object>> list =  this.getPageElement("peopleInfoGrid").getValueList();
		newPDFPath = new QueryPersonListBS().batchPrint(list,flagNrm,printTime);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','�����Ԥ������','"+newPDFPath+"')");
	}
	
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
	
	List<HashMap<String,String>> list = this.getPageElement("peopleInfoGrid").getStringValueList();
	int page = list.size();
	String a0200 = this.getPageElement("checkedgroupid").getValue();		
	//HBSession sess = null;
	try {
		//sess = HBUtil.getHBSession();
		int i = 0;
		if(pn>1){
			i = pSize*pn;
		}
		for(HashMap<String,String> m : list){
			String a0000 = m.get("a0000");
			HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0200+"'");
			i++;
		}
	} catch (Exception e) {
		this.setMainMessage("����ʧ�ܣ�");
		return EventRtnType.FAILD;
	}
	return EventRtnType.NORMAL_SUCCESS;
}

//���������в鿴��ť�¼�
@PageEvent("view")
public int view(String a0000s) throws RadowException, PrivilegeException{
	this.request.getSession().setAttribute("queryType", "3");
	this.getPageElement("viewValue").setValue(a0000s);
	this.setNextEventName("peopleInfoGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;
}

@PageEvent("queryByName")
public int queryByName(String name) throws RadowException, AppException{
	
	
	String name2 = name;
	String personq=this.getPageElement("personq").getValue();//""Ϊȫ��,1Ϊ��ְ��2Ϊ����ְ
	String seachCardid=this.getPageElement("seachCardid").getValue();//""Ϊȫ��,1Ϊ��ְ��2Ϊ����ְ
	
	if(name!=null){
		name2 = name.toUpperCase();
	}
	/*String sql = this.getPageElement("sql").getValue();
	
	 sql = sql + " and a01.A0101 like '%"+name+"%'";*/
	
	//��name��д��
	//name = name.toUpperCase();
	
	//��Ϊȫ���ѯ
	String sid = this.request.getSession().getId();
	String userID = SysManagerUtils.getUserId();
	String sql ="select a0000,a0163 from a01 where fkbs='1' and a01.a0000  in "
			+ "(select a02.a0000 from a02 where a02.a0281='true' and " //ְ����Ϣ��Ҫ���״̬
			+ " a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"'))";
	if(name!=null && !name.trim().equals("")) {
		sql = sql + " and (a01.a0101 like '%"+name+"%' or a01.A0102 like '%"+name2+"%') ";  
	}
	if(seachCardid!=null && !seachCardid.trim().equals("")) {
		sql = sql + " and (a01.a0101 like '%"+(seachCardid.toUpperCase())+"%' or a01.A0102 like '%"+(seachCardid.toLowerCase())+"%') ";  
	}
	
	
	
	//sql = sql + " and (a01.a0101 like '"+name+"%' or a01.A0102 like '"+name+"%') ";  
	
	sql = sql + " union all " +
	"select a0000,a0163 from a01 where fkbs='1' and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' " ;
				 // + " and (a01.a0184 = '"+name+"' or a01.a0101 like '%"+name+"%' or a01.A0102 like '%"+name2+"%') ";
	if(name!=null && !name.trim().equals("")) {
		sql = sql + " and (a01.a0101 like '%"+name+"%' or a01.A0102 like '%"+name2+"%') ";  
	}
	if(seachCardid!=null && !seachCardid.trim().equals("")) {
		sql = sql + " and (a01.a0101 like '%"+(seachCardid.toUpperCase())+"%' or a01.A0102 like '%"+(seachCardid.toLowerCase())+"%') ";  
	}
	
	String wheresql="1=1";
	if("1".equals(personq)){
		wheresql="a0163='1'";
	}else if("2".equals(personq)){
		wheresql="a0163!='1'";
	}else if(!"".equals(personq)){
		wheresql="a0163='"+personq+"'";
	}
	String qsql = CommSQL.getComSQL(sid)+" where a01.a0000  in ( select a0000 from("+sql+") cc where "+wheresql+" ) ";
	this.getPageElement("sql").setValue(qsql);
	System.out.println(qsql);
	//this.getPageElement("a0201b").setValue(id);					
	this.setNextEventName("peopleInfoGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;	
}

//�Զ���ģ��չʾ
@PageEvent("showtem")
public int showtem(String value) throws RadowException{
	this.request.getSession().setAttribute("tpid", value);
	this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
	this.getExecuteSG().addExecuteCode("addTab('���','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
	return EventRtnType.NORMAL_SUCCESS;
}
	//��������ѯ��������ԱIDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws RadowException{
		//System.out.println(listStr);
		String sid = this.request.getSession().getId();
		StringBuffer sql = new StringBuffer();
		sql.append(CommSQL.getComSQL(sid)+" where a0000 in ('-1'");
		if(listStr!=null && listStr.length()>2){
			listStr = listStr.substring(1, listStr.length()-1);
			List<String> list = Arrays.asList(listStr.split(","));
			for(String id:list){
				sql.append(",'"+id.trim()+"'");
			}
			sql.append(")");
			this.getPageElement("sql").setValue(sql.toString());
			//�ж��б�С���ϡ���Ƭ
		    String tableType = this.getPageElement("tableType").getValue();
		    if("1".equals(tableType)){
		    	this.setNextEventName("peopleInfoGrid.dogridquery");
		    }
		    if("2".equals(tableType)){
		    	this.getExecuteSG().addExecuteCode("datashow()");
		    	this.setNextEventName("peopleInfoGrid.dogridquery");
		    }
		    if("3".equals(tableType)){
		    	this.getExecuteSG().addExecuteCode("picshow()");
		    	this.setNextEventName("peopleInfoGrid.dogridquery");
		    }
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.setMainMessage("�޷���ѯ������Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	//��������ѯ��������ԱIDs
	@PageEvent("queryByNameAndIDS2")
	public int queryByNameAndIDS2(String listStr) throws RadowException{
        //������� �������ݿ������������
        HBSession sess = HBUtil.getHBSession();
        HBTransaction tr = null;
        if (listStr != null && listStr.length() > 2) {
            listStr = listStr.substring(1, listStr.length() - 1);
            List<String> list = Arrays.asList(listStr.split(","));
            StringBuffer xt1a0000s = new StringBuffer();
            StringBuffer xt2a0000s = new StringBuffer();
            StringBuffer xt3a0000s = new StringBuffer();
            StringBuffer xt4a0000s = new StringBuffer();
            
            for (String idparam : list) {
            	String idarr[] = idparam.split("@");
            	if(idarr[1].equals("1")) {
            		xt1a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("2")) {
            		xt2a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("3")) {
            		xt3a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("4")) {
            		xt4a0000s.append(",'" + idarr[0].trim() + "'");
            	}
            }
            
            try {
            	tr = sess.beginTransaction();
                //hjSql.append("))");
            	if(xt1a0000s.length()>0) {
            		String sql = "update a01 set fkbs='1' where a0000 in (''"+xt1a0000s+")";
            		sess.createSQLQuery(sql).executeUpdate();
            	}
            	List<B01> listb = sess.createQuery(" from B01 where b0114='C29.F19.ZZY'").list();
            	String tjdw = "-1"; //ͳ�ƹ�ϵ���ڵ�λ
            	if(listb!=null && listb.size()>0) {
            		B01 b = listb.get(0);
            		tjdw = b.getB0111();
            	}
            	if(xt2a0000s.length()>0) {
            		transferXX(xt2a0000s, "2",sess,tjdw);
            	}
            	if(xt3a0000s.length()>0) {
            		transferXX(xt2a0000s, "3",sess,tjdw);
            	}
            	if(xt4a0000s.length()>0) {
            		transferXX(xt2a0000s, "4",sess,tjdw);
            	}/***/
            	
            	sess.flush();
            	tr.commit();
            } catch (Exception e) {
                this.setMainMessage("����ʧ�ܣ�");
                e.printStackTrace();
                if(tr!=null) {
            		try {
						tr.rollback();
					} catch (AppException e1) {
						e1.printStackTrace();
					}
            	}
            }
            //this.getExecuteSG().addExecuteCode("$('#tplb').val('');");
            //this.setNextEventName("queryByName");
            return EventRtnType.NORMAL_SUCCESS;
        } else {
            this.setMainMessage("�޷���ѯ������Ա��");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	private void transferXX(StringBuffer xt2a0000s, String v_xt, HBSession sess, String tjdw) {
		//�Ѿ����ڵ�a0000s
		StringBuffer sb = new StringBuffer();
		String sql = "select a0000 from a01 where a0000 in (''"+xt2a0000s+") or a0184 in (select k.a0184 from "
				+ " v_js_a01 k where k.a0000 in (''"+xt2a0000s+"))";
		List<Object> list = sess.createSQLQuery(sql).list();
		for (int i = 0; i < list.size(); i++) {
			sb.append(",'").append(list.get(i).toString()).append("'");
		}
		System.out.println(sb);
		//a06
		String a06 = " MERGE INTO a06 a USING (select * from (select * from v_js_a06 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a0600 = t.a0600)  WHEN MATCHED THEN UPDATE " +
				"SET a.A0000=t.A0000,a.A0601=t.A0601,a.A0602=t.A0602,a.A0604=t.A0604,a.A0607=t.A0607," +
				"a.A0611=t.A0611,a.A0614=t.A0614,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A0699=t.A0699 " +
				" WHEN NOT MATCHED THEN INSERT " +
				"(A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, SORTID, UPDATED, A0699)"+
				"VALUES (t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED, t.A0699) "+ 
				"  ";
		System.out.println(a06);
		sess.createSQLQuery(a06).executeUpdate();
		//a08
		String a08 = " MERGE INTO a08 a USING (select * from (select * from v_js_a08 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (t.a0800 = a.a0800)  WHEN MATCHED THEN UPDATE " +
				" SET a.A0000=t.A0000,a.A0801A=t.A0801A,a.A0801B=t.A0801B,a.A0804=t.A0804,a.A0807=t.A0807,a.A0811=t.A0811,a.A0814=t.A0814,a.A0824=t.A0824," +
				" a.A0827=t.A0827,a.A0831=t.A0831,a.A0832=t.A0832,a.A0834=t.A0834,a.A0835=t.A0835,a.A0837=t.A0837,a.A0838=t.A0838,a.A0839=t.A0839," +
				" a.A0898=t.A0898,a.A0899=t.A0899,a.A0901A=t.A0901A,a.A0901B=t.A0901B,a.A0904=t.A0904,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.WAGE_USED=t.WAGE_USED" + 
				"  WHEN NOT MATCHED THEN INSERT" +
				"(A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827, A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898," +
				" A0899,A0901A, A0901B,A0904,SORTID,updated,wage_used)"+
				" VALUES (t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827, t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898," +
				" t.A0899,t.A0901A, t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used ) "
				+ "  ";
		sess.createSQLQuery(a08).executeUpdate();
		//a11
		String a11 = " MERGE INTO a11 a USING (select * from v_js_a11 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a1100 = t.a1100)  WHEN MATCHED THEN UPDATE" +
				" SET a.A1127=t.A1127,a.A1131=t.A1131,a.A1134=t.A1134,a.A1151=t.A1151,a.UPDATED=t.UPDATED,a.A1108=t.A1108,a.A1107C=t.A1107C," +
				" a.A0000=t.A0000,a.A1101=t.A1101,a.A1104=t.A1104,a.A1107=t.A1107," +
				"a.A1107A=t.A1107A,a.A1107B=t.A1107B,a.A1111=t.A1111,a.A1114=t.A1114,a.A1121A=t.A1121A, "
				+ "a.g11025=t.g11025, a.g11024=t.g11024, a.g11023=t.g11023, a.g11022=t.g11022,"
				+ " a.g11021=t.g11021, a.g11020=t.g11020, a.g11003=t.g11003, a.g11006=t.g11006 " +
				" WHEN NOT MATCHED THEN INSERT" +
				"(A0000,A1100,A1101,A1104,A1107,A1107A,a1107b,a1111,a1114,a1121a,a1127,a1131,a1134,a1151,updated,A1108,A1107C "
				+ ",g11025,g11024,g11023,g11022,g11021,g11020,g11003,g11006)"+
				" VALUES (t.A0000,t.A1100,t.A1101,t.A1104,t.A1107,t.A1107A,t.a1107b ,t.a1111 ,t.a1114 ,t.a1121a ,t.a1127 ,t.a1131 ,t.a1134 ,t.a1151 ,t.updated,t.A1108,t.A1107C"
				+ ",t.g11025,t.g11024,t.g11023,t.g11022,t.g11021,t.g11020,t.g11003,t.g11006 )" +
				"  ";
		sess.createSQLQuery(a11).executeUpdate();
		//a14
		String a14 = " MERGE INTO a14 a USING (select * from v_js_a14 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a1400 = t.a1400)  WHEN MATCHED THEN UPDATE " +
				" SET a.A0000=t.A0000,a.A1404A=t.A1404A,a.A1404B=t.A1404B,a.A1407=t.A1407,a.A1411A=t.A1411A,a.A1414=t.A1414," +
				" a.A1415=t.A1415,a.A1424=t.A1424,a.A1428=t.A1428,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED " +
				"  WHEN NOT MATCHED THEN INSERT" +
				"(a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,sortid ,updated )"+
				" VALUES (t.a0000, t.a1400, t.a1404a, t.a1404b, t.a1407 ,t.a1411a ,t.a1414 ,t.a1415, t.a1424, t.a1428,t.sortid ,t.updated )" +
				"  ";
		sess.createSQLQuery(a14).executeUpdate();
		//a15
		String a15 = " MERGE INTO a15 a USING (select * from v_js_a15 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a1500 = t.a1500)  WHEN MATCHED THEN UPDATE" +
				" SET a.A0000=t.A0000,a.A1517=t.A1517,a.A1521=t.A1521,a.UPDATED=t.UPDATED,a.A1527=t.A1527" +
				"  WHEN NOT MATCHED THEN INSERT" +
				"(a0000, a1500, a1517,a1521, updated, a1527)"+
				" VALUES (t.a0000, t.a1500, t.a1517,t.a1521, t.updated, t.a1527) "
				+ "  ";
		sess.createSQLQuery(a15).executeUpdate();
		//a29
		String a29 = " MERGE INTO a29 a USING (select * from v_js_a29 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
				" SET a.A2907=t.A2907,a.A2911=t.A2911,a.A2921A=t.A2921A,a.A2941=t.A2941,a.A2944=t.A2944,a.A2947=t.A2947,a.A2949=t.A2949,a.UPDATED=t.UPDATED," +
				" a.A2921B=t.A2921B,a.A2947B=t.A2947B" +
				",a.A2921C=t.A2921C,a.A2921D=t.A2921D  " +
				" WHEN NOT MATCHED THEN INSERT" +
				"(a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2949, updated,A2921B,A2947B,A2921C,A2921D,A2947)"+
				" VALUES (t.a0000, t.a2907 ,t.a2911, t.a2921a ,t.a2941, t.a2944, t.a2949, t.updated,t.A2921B,t.A2947B,t.A2921C,t.A2921D,t.A2947)" +
				"  ";
		sess.createSQLQuery(a29).executeUpdate();
		//a30
		String a30 = " MERGE INTO a30 a USING (select * from v_js_a30 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
				" SET a.A3001=t.A3001,a.A3004=t.A3004,a.A3034=t.A3034,a.UPDATED=t.UPDATED,a.A3007A=t.A3007A,a.A3038=t.A3038" +
				" " +
				" WHEN NOT MATCHED THEN INSERT(a0000,a3001, a3004, a3034 ,updated,A3007A,a3038) VALUES (t.a0000,t.a3001, t.a3004, t.a3034 ,t.updated,t.A3007A,t.a3038)" +
				"  ";
		sess.createSQLQuery(a30).executeUpdate();
		//a31
		String a31 = "MERGE INTO a31 a USING (select * from v_js_a31 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE " +
				" SET a.A3101=t.A3101,a.A3117A=t.A3117A,a.A3137=t.A3137," +
				" a.A3138=t.A3138,a.UPDATED=t.UPDATED,a.A3104=t.A3104,a.A3107=t.A3107,a.A3118=t.A3118" +
				"  WHEN NOT MATCHED THEN INSERT " +
				"(a0000,a3101,  a3117a,a3137, a3138 ,updated,a3104,a3107,a3118)"+
				" VALUES (t.a0000,t.a3101,  t.a3117a,t.a3137, t.a3138 ,t.updated,t.a3104,t.a3107,t.a3118)" +
				"  ";
		sess.createSQLQuery(a31).executeUpdate();
		//a36
		String a36 = "MERGE INTO a36 a USING (select * from v_js_a36 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a3600 = t.a3600)  WHEN MATCHED THEN UPDATE" +
				" SET a.A0000=t.A0000,a.A3601=t.A3601,a.A3604A=t.A3604A,a.A3607=t.A3607,a.A3611=t.A3611,a.A3627=t.A3627,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A3684=t.A3684 " +
				"  WHEN NOT MATCHED THEN INSERT " +
				"(a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid ,updated,A3684)"+
				"VALUES (t.a0000,t.a3600, t.a3601, t.a3604a, t.a3607, t.a3611, t.a3627 ,t.sortid ,t.updated,t.A3684) " +
				"  ";
		sess.createSQLQuery(a36).executeUpdate();
		//a37
		String a37 = " MERGE INTO a37 a USING (select * from v_js_a37 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
				" SET a.A3701=t.A3701,a.A3707A=t.A3707A,a.A3707C=t.A3707C,a.A3707E=t.A3707E,a.A3707B=t.A3707B,a.A3708=t.A3708,a.A3711=t.A3711,a.A3714=t.A3714,a.UPDATED=t.UPDATED" +
				" WHEN NOT MATCHED THEN INSERT " +
				"(a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,updated)"+
				" VALUES (t.a0000,t.a3701,t.a3707a,t.a3707c,t.a3707e,t.a3707b,t.a3708,t.a3711,t.a3714,t.updated)" +
				"  ";
		sess.createSQLQuery(a37).executeUpdate();
		//a41
		String a41 = "MERGE INTO a41 a USING (select * from v_js_a41 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A4100 = t.A4100)  WHEN MATCHED THEN UPDATE" +
				" SET a.A0000=t.A0000,a.A1100=t.A1100,a.A4101=t.A4101,a.A4102=t.A4102,a.A4103=t.A4103,a.A4104=t.A4104,a.A4105=t.A4105,a.A4199=t.A4199" +
				" WHEN NOT MATCHED THEN INSERT" +
				"(a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199)"+
				" VALUES (t.a4100, t.a0000,t.a1100 ,t.a4101, t.a4102, t.a4103 ,t.a4104, t.a4105 ,t.a4199)" +
				"  ";
		sess.createSQLQuery(a41).executeUpdate();
		//a57
		String a57 = " MERGE INTO a57 a USING (select * from v_js_a57 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE" +
				" SET a.A5714=t.A5714,a.UPDATED=t.UPDATED,a.PHOTONAME=t.PHOTONAME,a.PHOTSTYPE=t.PHOTSTYPE,a.PHOTOPATH=t.PHOTOPATH,a.PICSTATUS=t.PICSTATUS" +
				"  WHEN NOT MATCHED THEN INSERT" +
				" (a0000,a5714 ,updated,PHOTONAME,PHOTSTYPE,PHOTOPATH,PICSTATUS) VALUES (t.a0000,t.a5714 ,t.updated,t.PHOTONAME,t.PHOTSTYPE,t.PHOTOPATH,'1')" +
				"  ";
		sess.createSQLQuery(a57).executeUpdate();
		//a05
		String a05 = "MERGE INTO a05 a USING (select * from v_js_a05 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A0500 = t.A0500)  WHEN MATCHED THEN UPDATE"+
				" SET a.A0000=t.A0000,a.A0531=t.A0531,a.A0501B=t.A0501B,a.A0504=t.A0504,a.A0511=t.A0511,a.A0517=t.A0517,a.A0524=t.A0524,a.A0525=t.A0525 "+
				"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A0500,a.A0531,a.A0501B,a.A0504,a.A0511,a.A0517,a.A0524,a.A0525)" +
				" VALUES (t.A0000,t.A0500,t.A0531,t.A0501B,t.A0504,t.A0511,t.A0517,t.A0524,t.A0525)"+
				"  ";
		sess.createSQLQuery(a05).executeUpdate();
		//a99Z1
		String a99Z1 = "MERGE INTO a99Z1 a USING (select * from v_js_a99Z1 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A99Z100 = t.A99Z100)  WHEN MATCHED THEN UPDATE"+
				" SET a.A0000=t.A0000,a.A99Z101=t.A99Z101,a.A99Z102=t.A99Z102,a.A99Z103=t.A99Z103,a.A99Z104=t.A99Z104"+
				"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A99Z100,a.A99Z101,a.A99Z102,a.A99Z103,a.A99Z104)" +
				" VALUES (t.A0000,t.A99Z100,t.A99Z101,t.A99Z102,t.A99Z103,t.A99Z104)"+
				"  ";
		sess.createSQLQuery(a99Z1).executeUpdate();
		//a01
		String a01_1 = " MERGE INTO a01 a USING (select * from v_js_a01 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.A0000 = t.A0000)  " +
			"  WHEN NOT MATCHED THEN INSERT" +
			" (a.A0192E, a.A0199, a.A01K01, a.A01K02, a.CBDRESULT, a.CBDW, a.ISVALID, a.NL, a.NMZW, a.NRZW, a.ORGID, a.QRZXL, "
			+ "a.QRZXLXX, a.QRZXW, a.QRZXWXX, a.RMLY, a.STATUS, a.TBR, a.TBRJG, a.USERLOG, a.XGR, a.ZZXL, a.ZZXLXX, a.ZZXW, "
			+ "a.ZZXWXX, a.A0155, a.AGE, a.JSNLSJ, a.RESULTSORTID, a.TBSJ, a.XGSJ, a.SORTID, a.A0194, a.A0104A, a.A0111, "
			+ "a.A0114, a.A0117A, a.A0128B, a.A0141D, a.A0144B, a.A0144C, a.A0148, a.A0148C, a.A0149, a.A0151, a.A0153, "
			+ "a.A0157, a.A0158, a.A0159, a.A015A, a.A0161, a.A0162, a.A0180, a.A0191, a.A0192B, a.A0193, a.A0194U, "
			+ "a.A0198, a.A0000, a.A0101, a.A0102, a.A0104, a.A0107, a.A0111A, a.A0114A, a.A0115A, a.A0117, a.A0128, "
			+ "a.A0134, a.A0140, a.A0141, a.A0144, a.A3921, a.A3927, a.A0160, a.A0163, a.A0165, a.A0184, a.A0187A, a.A0192, "
			+ "a.A0192A, a.A0221, a.A0288, a.A0192D, a.A0192C, a.A0196, a.A0197, a.A0195, a.A1701, a.A14Z101, a.A15Z101,"
			+ " a.A0120, a.A0121, a.A2949, a.A0122, a.a0192f, a.ZGXL, a.ZGXW, a.ZGXLXX, a.ZGXWXX,a.TCSJSHOW,a.TCFSSHOW,a.zcsj,a.fcsj,a.a0190,a.a0189,a.a6506,a.a0188,a.fkbs,a.fkly)" +
			" VALUES (t.A0192E, t.A0199, t.A01K01, t.A01K02, t.CBDRESULT, t.CBDW, t.ISVALID, t.NL, t.NMZW, t.NRZW, t.ORGID,"
			+ " t.QRZXL, t.QRZXLXX, t.QRZXW, t.QRZXWXX, t.RMLY, t.STATUS, t.TBR, t.TBRJG, t.USERLOG, t.XGR, t.ZZXL, t.ZZXLXX, "
			+ "t.ZZXW, t.ZZXWXX, t.A0155, t.AGE, t.JSNLSJ, t.RESULTSORTID, t.TBSJ, t.XGSJ, t.SORTID, t.A0194, t.A0104A, t.A0111, "
			+ "t.A0114, t.A0117A, t.A0128B, t.A0141D, t.A0144B, t.A0144C, t.A0148, t.A0148C, t.A0149, t.A0151, t.A0153, t.A0157,"
			+ " t.A0158, t.A0159, t.A015A, t.A0161, t.A0162, t.A0180, t.A0191, t.A0192B, t.A0193, t.A0194U, t.A0198, t.A0000,"
			+ " t.A0101, t.A0102, t.A0104, t.A0107, t.A0111A, t.A0114A, t.A0115A, t.A0117, t.A0128, t.A0134, t.A0140, t.A0141, "
			+ "t.A0144, t.A3921, t.A3927, t.A0160, t.A0163, t.A0165, t.A0184, t.A0187A, t.A0192, t.A0192A, t.A0221, t.A0288,"
			+ " t.A0192D, t.A0192C, t.A0196, t.A0197, '" + tjdw + "', t.A1701, t.A14Z101, t.A15Z101, t.A0120, t.A0121, t.A2949, "
			+ "t.A0122,t.a0192f,t.ZGXL,t.ZGXW,t.ZGXLXX,t.ZGXWXX,t.TCSJSHOW,t.TCFSSHOW,t.zcsj,t.fcsj,t.a0190,t.a0189,t.a6506,t.a0188, '1','2') "
			+ "  ";
		sess.createSQLQuery(a01_1).executeUpdate();
		System.out.println(a01_1);
		String a01_2 = "update a01 set fkbs='1',fkly='"+v_xt+"' where a0000 in (''"+sb+")";
		sess.createSQLQuery(a01_2).executeUpdate();
		System.out.println(a01_2);
		//a02
		String a02 = " MERGE INTO a02 a USING (select * from v_js_a02 where v_xt='"+v_xt+"' and a0000 in (''"+xt2a0000s+") and a0000 not in ('--'"+sb+")) t ON (a.a0200 = t.a0200)  WHEN MATCHED THEN UPDATE" +
				" SET a.A0000=t.A0000,a.A0201A=t.A0201A,a.A0201B=t.A0201B,a.A0201D=t.A0201D,a.A0201E=t.A0201E,a.A0215A=t.A0215A,"
				+ "a.A0219=t.A0219,a.A0223=t.A0223,a.A0225=t.A0225,a.A0243=t.A0243,a.A0245=t.A0245,a.A0247=t.A0247,a.A0251B=t.A0251B,"
				+ "a.A0255=t.A0255,a.A0265=t.A0265,a.A0267=t.A0267,a.A0272=t.A0272,a.A0281=t.A0281,a.A0221T=t.A0221T,a.B0238=t.B0238,"
				+ "a.B0239=t.B0239,a.A0221A=t.A0221A,a.WAGE_USED=t.WAGE_USED,a.UPDATED=t.UPDATED,a.A4907=t.A4907,a.A4904=t.A4904,"
				+ "a.A4901=t.A4901,a.A0299=t.A0299,a.A0295=t.A0295,a.A0289=t.A0289,a.A0288=t.A0288,a.A0284=t.A0284,a.A0277=t.A0277,"
				+ "a.A0271=t.A0271,a.A0259=t.A0259,a.A0256C=t.A0256C,a.A0256B=t.A0256B,a.A0256A=t.A0256A,a.A0256=t.A0256,a.A0251=t.A0251,"
				+ "a.A0229=t.A0229,a.A0222=t.A0222,a.A0221W=t.A0221W,a.A0221=t.A0221,a.A0219W=t.A0219W,a.A0216A=t.A0216A,a.A0215B=t.A0215B,"
				+ "a.A0209=t.A0209,a.A0207=t.A0207,a.A0204=t.A0204,a.A0201C=t.A0201C,a.A0201=t.A0201,a.A0279=t.A0279 " +
				"  WHEN NOT MATCHED THEN INSERT" +
				"(a.A0000,a.A0200,a.A0201A,a.A0201B,a.A0201D,a.A0201E,a.A0215A,a.A0219,a.A0223,a.A0225,a.A0243,a.A0245,a.A0247,a.A0251B,"
				+ "a.A0255,a.A0265,a.A0267,a.A0272,a.A0281,a.A0221T,a.B0238,a.B0239,a.A0221A,a.WAGE_USED,a.UPDATED,a.A4907,a.A4904,a.A4901,"
				+ "a.A0299,a.A0295,a.A0289,a.A0288,a.A0284,a.A0277,a.A0271,a.A0259,a.A0256C,a.A0256B,a.A0256A,a.A0256,a.A0251,a.A0229,"
				+ "a.A0222,a.A0221W,a.A0221,a.A0219W,a.A0216A,a.A0215B,a.A0209,a.A0207,a.A0204,a.A0201C,a.A0201,a.A0279)"+
				" VALUES (t.A0000,t.A0200,t.A0201A,t.A0201B,t.A0201D,t.A0201E,t.A0215A,t.A0219,t.A0223,t.A0225,t.A0243,t.A0245,t.A0247,"
				+ "t.A0251B,t.A0255,t.A0265,t.A0267,t.A0272,t.A0281,t.A0221T,t.B0238,t.B0239,t.A0221A,t.WAGE_USED,t.UPDATED,t.A4907,"
				+ "t.A4904,t.A4901,t.A0299,t.A0295,t.A0289,t.A0288,t.A0284,t.A0277,t.A0271,t.A0259,t.A0256C,t.A0256B,t.A0256A,t.A0256,"
				+ "t.A0251,t.A0229,t.A0222,t.A0221W,t.A0221,t.A0219W,t.A0216A,t.A0215B,t.A0209,t.A0207,t.A0204,t.A0201C,t.A0201,t.A0279)" +
				"  ";
		sess.createSQLQuery(a02).executeUpdate();
	}
		
	@PageEvent("personq.onchange")
	public int change() throws RadowException{
		//List<HashMap<String,Object>> s = this.getPageElement("personQ").getValueList();
		//System.out.println(s);
		this.getPageElement("xzry").setValue("0");
		this.getPageElement("lsry").setValue("0");
		this.getPageElement("ltry").setValue("0");
		
		String personQ=this.getPageElement("personq_combo").getValue();
		if(personQ!=null && !"".equals(personQ)){
			String[] s = personQ.split(",");
				for(String type : s){
					type = type.trim();
					if("��ְ��Ա".equals(type)){
						this.getPageElement("xzry").setValue("1");
					}
					if("��ʷ��Ա".equals(type)){
						this.getPageElement("lsry").setValue("1");
						this.getPageElement("ltry").setValue("1");
					}
					/*if("��ʷ��Ա".equals(type)){
						this.getPageElement("lsry").setValue("1");
					}
					if("������Ա".equals(type)){
						this.getPageElement("ltry").setValue("1");
					}*/
				}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��Ƭҳ�����ݼ�����ʾ
	@PageEvent("picshow")
	public int picshow() throws RadowException, AppException, UnsupportedEncodingException{
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		
		String afa = this.request.getSession().getAttribute("allSelect").toString();
		
		HBSession sess=HBUtil.getHBSession();
		String page=this.getPageElement("page").getValue();
		int pagesize=10;
		int pages=Integer.valueOf(page);
		int start=(pages-1)*pagesize;
		int end=pages*pagesize;
		
		
		//String newsql = afa.replace(CommSQL.getComFields(userid,sid), "a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a"+",(select sort from A01SEARCHTEMP tmp where sessionid = '"+sid+"' and tmp.a0000 = a01.a0000) as sort ");
		String newsql = afa.replace(CommSQL.getComFields(userid,sid), "a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a");
		
		String fysql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			fysql="select tt.* from (select t.*,rownum rn from ("+newsql+") t ) tt where rn>"+start+" and rn<="+end;
		}else{
			fysql = "select * from ("+newsql+") t limit "+start+","+end;
		}
		
		List<Object[]>  list=sess.createSQLQuery(fysql).list();
		for(int i=0;i<list.size();i++){
			Object[] info=list.get(i);
			Object a0000=info[0];
			Object a0101=info[1];
			Object a0192a=info[9];
			this.getExecuteSG().addExecuteCode("document.getElementById('i"+i+"').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+URLEncoder.encode(URLEncoder.encode(a0000.toString(),"UTF-8"),"UTF-8")+"'");
			this.getExecuteSG().addExecuteCode("showpic('"+i+"','"+a0000+"','"+a0101+"','"+a0192a+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	//��Ƭ
	@PageEvent("Show")
	public int Show() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		String afa = this.request.getSession().getAttribute("allSelect").toString();
		afa = afa.replace("a01."+CommSQL.getComFields(userid,sid), " count(2) ");
		
		afa = afa.replace("order by sort", "");
		
		String size = sess.createSQLQuery(afa).uniqueResult().toString();
		
		this.getExecuteSG().addExecuteCode("setlength('"+size+"')");
		
		if("0".equals(size)){
			this.setMainMessage("����Ա��Ϣ");
			return EventRtnType.FAILD;
		}
		
		this.setNextEventName("picshow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//С����
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		String afa = this.request.getSession().getAttribute("allSelect").toString();
		
		afa = afa.replace("a01."+CommSQL.getComFields(userid,sid), " count(2) ");
		afa = afa.replace("order by sort", "");
		
		String size = sess.createSQLQuery(afa).uniqueResult().toString();
		
		this.getExecuteSG().addExecuteCode("setlength2('"+size+"')");
		
		if("0".equals(size)){
			this.setMainMessage("����Ա��Ϣ");
			return EventRtnType.FAILD;
		}
		
		this.setNextEventName("datashow");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//С����ҳ�����ݼ���
	@PageEvent("datashow")
	public int datashow() throws RadowException, UnsupportedEncodingException{
		
		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		
		String afa = this.request.getSession().getAttribute("allSelect").toString();
		
		
		HBSession sess=HBUtil.getHBSession();
		String page=this.getPageElement("page3").getValue();
		int pagesize=6;
		int pages=Integer.valueOf(page);
		int start=(pages-1)*pagesize;
		int end=pages*pagesize;
		
		String newsql = afa.replace(CommSQL.getComFields(userid,sid), "a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a");
		
		String fysql = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			fysql = "select tt.* from (select t.*,rownum rn from ("+newsql+") t ) tt where rn>"+start+" and rn<="+end;
		}else{
			fysql = "select * from ("+newsql+") t limit "+start+","+end;
		}
		
		
		List<Object[]>  list=sess.createSQLQuery(fysql).list();
		for(int i=0;i<list.size();i++){
			String data = "";
			Object[] info=list.get(i);
			Object a0000=info[0];
			/*String picsql="select photopath,photoname from a57 where a0000='"+a0000+"'";
			Object[] p=(Object[]) sess.createSQLQuery(picsql).uniqueResult();
			String path="";
			if(p!=null&&p.length>0){
				path=AppConfig.PHOTO_PATH+"/"+p[0]+p[1];
			}
			File f=new File(path);//�ж���Ƭ���ڲ�����
			if(!f.exists()){
				path="picCut/image/photo.jpg";
			}*/
			Object a0101=info[1];//����
			Object a0104=info[2];//�Ա�
			if(a0104!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '"+a0104+"'";
				a0104 = sess.createSQLQuery(s).uniqueResult();
				data = data + a0104.toString() + ",";
			}
			Object a0107=info[3];//��������
			if(a0107!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0107.toString().matches(reg) || a0107.toString().matches(reg2)){
					String msg = getAgeNew(a0107.toString());
					data = data + msg + ",";
				}
			}
			Object a0117=info[4];//����
			if(a0117!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
				a0117 = sess.createSQLQuery(s).uniqueResult();
				data = data + a0117.toString() + ",";
			}
			Object a0111=info[5];//����
			if(a0111!=null){
				data = data + a0111.toString() + "��,";
			}
			Object a0140=info[6];//�뵳ʱ��
			if(a0140!=null){
				String reg = "[0-9]{4}\\.[0-9]{2}";
				Pattern p1 = Pattern.compile(reg); 
			    Matcher matcher = p1.matcher(a0140.toString());
			    if (matcher.find()) {
			    	String s = matcher.group();
			    	s = s.replace(".", "��");
			    	data = data + s + "���뵳,";
				}
			}
			Object a0134=info[7];//�μӹ���ʱ��
			if(a0134!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0134.toString().matches(reg) || a0134.toString().matches(reg2)){
					String year = a0134.toString().substring(0, 4);
					String month = a0134.toString().substring(4, 6);
					data = data + year + "��" + month + "�²μӹ���";
				}
				
			}
			
			
			//���ѧ��
			String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '"+a0000+"'";
			
			List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();
			
			if(zgxlS.size() > 0){
				Object zgxl = zgxlS.get(0);
				
				if(zgxl != null){
					data = data  + "," + zgxl.toString()+"ѧ��";
				}
				
			}
			
			//���ѧλ
			String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a0000+"'";
			
			List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();
			
			
			if(zgxwS.size() > 0){
				Object zgxw = zgxwS.get(0);
				
				if(zgxw != null){
					zgxw = zgxw.toString().replace("ѧλ", "");
					data = data + "," + zgxw.toString() + "��";
				}else{
					data = data + "��";
				}
				
				
			}else{
				data = data + "��";
			}
			
			//רҵ����ְ��
			Object zyjs = info[8];	
			//String zyjsStr = zyjs.toString();
			
			String zyjsStr = "";
			if(zyjs != null && !zyjs.equals("")){
				zyjsStr = zyjs.toString();
			}
			
			
			zyjsStr = zyjsStr.replace(" ", "");
			if(zyjsStr != null && !zyjsStr.equals("")){
				data = data + zyjsStr.toString() + ",";
			}
			
			//String s = "select LISTAGG(A0201A || A0216A,'��') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '"+a0000+"' and A0255 = '1' and A0281 = 'true'";
			Object zwOb = info[9];		//������λ��ְ��
			
			String zw = "";
			if(zwOb != null && !zwOb.equals("")){
				zw = zwOb.toString();
			}
			
			
			zw = zw.replace(" ", "");
			
			if(zw!=null && !zw.equals("")){
				data = data + zw.toString() + ",";
			}
			data = data.substring(0, data.length()-1) + "��";
			//select LISTAGG(A0201A || A0216A,'<br/>') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '' and A0255 = '1' and A0281 = 'true'
			this.getExecuteSG().addExecuteCode("document.getElementById('datai"+i+"').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+URLEncoder.encode(URLEncoder.encode(a0000.toString(),"UTF-8"),"UTF-8")+"'");
			this.getExecuteSG().addExecuteCode("showdata('"+i+"','"+a0000+"','"+a0101+"','"+data+"')");
		}
		//this.getExecuteSG().addExecuteCode("strShowData()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("pic.dbclick")
	public int picdbclick(String a0000) throws RadowException, AppException{
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			/*HBSession sess = HBUtil.getHBSession();
			//�ж��Ƿ����޸�Ȩ�ޡ�
			
			String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
			String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
			" b.a0201b=c.b0111 and a.a0000='"+a0000+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
			" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
			List elist = sess.createSQLQuery(editableSQL).list();
			List elist2 = sess.createSQLQuery(editableSQL2).list();
			if(elist2==null||elist2.size()==0){
				if(elist!=null&&elist.size()>0){
					this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson2',false,false)");
				}
			}
			else{*/
				//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			/*}*/
		//this.getExecuteSG().addExecuteCode("addTab('','"+this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString()+"','"+request.getContextPath()+"/pages/publicServantManage/AddPerson2.jsp?x=1',false,false)");
		//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson2',false,false)");
		
		this.request.getSession().setAttribute("personIdSet", null);
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','��Ա��Ϣ�޸�',1009,645,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("����Ա����ϵͳ�У�");
		}
	}
	
	/**
	 * ��Ƭ���
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("imgVerify.onclick")
	public int imgVerify() throws RadowException {
		this.getExecuteSG().addExecuteCode("addTab('��Ƭ���','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public String getAgeNew(String value) {
		int returnAge;

		String birthYear = value.toString().substring(0, 4);
		String birthMonth = value.toString().substring(4, 6);
		String birthDay = "";
		if(value.length()==6){
			birthDay = "01";
		}
		if(value.length()==8){
			birthDay = value.toString().substring(6, 8);
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(d);
		String nowYear = s.toString().substring(0, 4);
		String nowMonth = s.toString().substring(4, 6);
		String nowDay = s.toString().substring(6, 8);
		if (Integer.parseInt(nowYear) == Integer.parseInt(birthYear)) {
			returnAge = 0; // ͬ�귵��0��
		} else {
			int ageDiff = Integer.parseInt(nowYear) - Integer.parseInt(birthYear); // ��ֻ��
			if (ageDiff > 0) {
				if (Integer.parseInt(nowMonth) == Integer.parseInt(birthMonth)) {
					int dayDiff = Integer.parseInt(nowDay) - Integer.parseInt(birthDay);// ��֮��
					if (dayDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				} else {
					int monthDiff = Integer.parseInt(nowMonth) - Integer.parseInt(birthMonth);// ��֮��
					if (monthDiff < 0) {
						returnAge = ageDiff - 1;
					} else {
						returnAge = ageDiff;
					}
				}
			} else {
				returnAge = -1;// �������ڴ��� ���ڽ���
			}

		}
		//String msg = value.toString().substring(0, 6) + "(" + returnAge + "��)";
		String msg = "" + returnAge + "��(" + birthYear + "��" + birthMonth + "����)";
		return msg;
	}
	
	private void initA0000Map(String nowA0000){
		//String nowA0000 = (String)this.request.getSession().getAttribute("a0000");
		Object sql = this.request.getSession().getAttribute("allSelect");
		int num = 0;
		Integer i = 0;
		Map<Integer, Object> map = new HashMap<Integer,Object>();
		if(sql != null){
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql.toString()).list();
			for(Object[] obj : list){
				if(nowA0000.equals(obj[0])){
					num = i;//��ǰ����Ա˳��
				}
				map.put(i, obj[0]);
				++i;
			}
		}
		this.request.getSession().setAttribute("a0000Map",map);
		this.request.getSession().setAttribute("nowNumber",num);
		this.request.getSession().setAttribute("nowNumber2",num);//nowNumber2���ں��ڱ��ݲ�ѯ��
		this.request.getSession().setAttribute("bigNumber",i);
	}
	
	private void initA0000MapXX(String nowA0000){
		//String nowA0000 = (String)this.request.getSession().getAttribute("a0000");
		Object sql = this.request.getSession().getAttribute("allSelect");
		int num = 0;
		Integer i = 0;
		Map<Integer, Object> map = new HashMap<Integer,Object>();
		if(sql != null){
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql.toString()).list();
			for(Object[] obj : list){
				if(nowA0000.equals(obj[0])){
					num = i;//��ǰ����Ա˳��
				}
				map.put(i, obj[0]);
				++i;  
			}
		}
		this.request.getSession().setAttribute("a0000MapXX",map);
		this.request.getSession().setAttribute("nowNumberXX",num);
		this.request.getSession().setAttribute("nowNumber2XX",num);//nowNumber2���ں��ڱ��ݲ�ѯ��
		this.request.getSession().setAttribute("bigNumberXX",i);
	}
	
	
	@NoRequiredValidate
	@PageEvent("removePersons")
	public int removePersons(String v) throws RadowException {
		String a0000s = this.getPageElement("removePersonIDs").getValue();
		if(!"".equals(a0000s)){
			a0000s = a0000s.replace(",", "','");
			a0000s = "'"+a0000s.substring(0,a0000s.length()-2);
			String sid = this.request.getSession().getId();
			//String resultOptSQL = "delete from A01SEARCHTEMP where a0000 in({sql}) and sessionid='"+sid+"'";
			//this.optResult(a0000s, resultOptSQL);
			this.optResultDelete(a0000s);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("chuidrusesson")
	public int chuidrusesson()throws AppException, RadowException, SQLException{
/*		//����Աid�浽session���ڱ��չʾ�ĵط���
		request.getSession().setAttribute("personidy", a0000);*/
		HBSession sess = HBUtil.getHBSession();
		
		String tableType = this.getPageElement("tableType").getValue();  //��Աչ�ַ�ʽ��1���б�2��С���ϣ�3����Ƭ
		String a0000 = "";
		String idsp = "";
		String idall = "";
		PageElement pe = this.getPageElement("peopleInfoGrid");
		
		
		
			if(pe!=null){
				List<HashMap<String, Object>> list = pe.getValueList();
				String name ="";
				
				Process p = null;
				for(int j=0;j<list.size();j++){
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("personcheck");
					if(usercheck.equals(true)){
						a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
						idsp += "|"+a0000+"|@";
					}
				}
				if(idsp != null && idsp.length()>0){
					idsp = idsp.substring(0, idsp.length()-1);
					//����Աid�浽session���ڱ��չʾ�ĵط���
					request.getSession().setAttribute("personidy", idsp);
					request.getSession().removeAttribute("personidall");
				}
			}
			if("".equals(idsp)){
				List<HashMap<String, Object>> list = pe.getValueList();
				String name ="";
				
				for(int j=0;j<list.size();j++){
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("personcheck");
					a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
					idall += "|"+a0000+"|@";
				}
				idall = idall.substring(0, idall.length()-1);
				request.getSession().setAttribute("personidall", idall);	
				request.getSession().removeAttribute("personidy");
			}
		
		
		String a0000s = "";
		if("2".equals(tableType) || "3".equals(tableType)){
			
			String picA0000s = this.getPageElement("picA0000s").getValue();
			
			if(picA0000s == null || "".equals(picA0000s.trim())){	//û�й�ѡ �򵼳�ȫ��
				
			}else{
				idsp = "";
				a0000s = picA0000s.substring(0, picA0000s.length()-1);
				String[] a0000_arr = a0000s.replaceAll("\\'", "").split(",");
				if(a0000_arr != null && a0000_arr.length > 0){
					for(int j = 0; j < a0000_arr.length; j++){
						String a0000New = a0000_arr[j];
						
						
						idsp = "|"+a0000New+"|@" + idsp;
					}
				}
				
				if(idsp != null && idsp.length()>0){
					idsp = idsp.substring(0, idsp.length()-1);
					request.getSession().setAttribute("personidy", idsp);
					request.getSession().removeAttribute("personidall");
				}
				
			}
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	//��Ա״̬���
	@PageEvent("changeState.onclick")
	@GridDataRange
	public int changeState() throws RadowException, AppException {
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			//this.getExecuteSG().addExecuteCode("Ext.Msg.alert('ϵͳ��ʾ','��˫����������ѯ��');");
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		};
		
		String type = this.getPageElement("tableType").getValue();
		StringBuffer a0000 = new StringBuffer();
		
		int all = choose("peopleInfoGrid","personcheck");
		//�б� 
		if("1".equals(type)){
			
			/*if(all == ON_ONE_CHOOSE){		//�б�û�й�ѡ�������ȫ��
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
				
				List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
				if (allPeople.size() > 0) {
					for(Object a00 : allPeople){
						a0000.append("").append(a00).append(",");
					}
				}
			}else{*/
			List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
			int countNum = 0;
			for (int j = 0; j < list.size();j++) {
				HashMap<String, Object> map = list.get(j);
				Object check1 = map.get("personcheck");
				if (check1!= null && check1.equals(true)) {
					a0000.append("").append(map.get("a0000")==null?"":map.get("a0000").toString()).append(",");//����ѡ����Ա�����װ���á������ָ�
					
					
					countNum++;
				}
			}
			if(countNum==0){
				//throw new AppException("�빴ѡ��Ա��");
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			/*}*/
		}
		//С���ϡ���Ƭ
		if("2".equals(type) || "3".equals(type)){
			
			/*if(all == ON_ONE_CHOOSE){		//�б�û�й�ѡ�������ȫ��
				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
				
				List allPeople = HBUtil.getHBSession().createSQLQuery(sql).list();
				if (allPeople.size() > 0) {
					for(Object a00 : allPeople){
						a0000.append("").append(a00).append(",");
					}
				}
			}
			else{*/
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					//throw new AppException("�빴ѡ��Ա��");
					
					this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				a0000s = a0000s.replace("'", "");
				
				a0000 = new StringBuffer(a0000s);
				
				
			/*}*/
		}
		
		if(a0000==null || a0000.toString().trim().equals("")){
			//throw new AppException("���ݻ�ȡ�쳣��");
			
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		//this.setRadow_parent_data(a0000.toString());
		HttpSession session=request.getSession(); 
		session.setAttribute("a0000s",a0000.toString()); 
		session.setAttribute("type",type); 
		
		String a0000F = a0000.toString();
		a0000F = a0000F.substring(0,a0000F.length()-1);
		String[] values = a0000F.split(",");
		String oneId = values[0];				//��һ����id
		
		StringBuffer a0000SQl = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			a0000SQl.append("'").append(values[i]==null?"":values[i].toString()).append("',");
		}
		String a0000SQLF = a0000SQl.toString();
		a0000SQLF = a0000SQLF.substring(0,a0000SQLF.length()-1);
		
		String sql = "select count(1) from a01 where a0163 = (select a0163 from a01 where a0000 = '"+oneId+"') and a0000 in ("+a0000SQLF+")";
		String allPeople = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
		if(Integer.valueOf(allPeople) != values.length){
			//throw new AppException("��ѡ��Ա�в�ͬ�Ĺ���״̬��");
			
			this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա�в�ͬ�Ĺ���״̬��',null,220);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//����һ������Ա����״̬��ֵ��ҳ��
		String sqlA0163 = "select a0163 from a01 where a0000 = '"+oneId+"'";
		String a0163 = HBUtil.getHBSession().createSQLQuery(sqlA0163).uniqueResult().toString();
		
		String a0163NameSQL =  "select code_name from code_value t where t.code_type='ZB126' and t.code_value = '"+a0163+"'";
		String a0163Name = HBUtil.getHBSession().createSQLQuery(a0163NameSQL).uniqueResult().toString();
		
		this.getPageElement("a0163").setValue(a0163);			//��Ա����״̬
		this.getPageElement("a0163_combo").setValue(a0163Name);	//��Ա����״̬�ı�
		
		
	    //this.openWindow("changeStateWin", "pages.publicServantManage.ChangeState");
		this.getExecuteSG().addExecuteCode("$h.openWin('changeStateWin','pages.publicServantManage.ChangeState','��Ա״̬���',450,250,document.getElementById('tableType').value,ctxPath)");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//��Ա����
	@NoRequiredValidate
	@PageEvent("sameId.onclick")
	public int sameId() throws RadowException {
		//this.openWindow("sameIdCheckWin", "pages.sysorg.org.sameIdCheck");
		
		this.getExecuteSG().addExecuteCode("$h.openWin('sameIdCheckWin','pages.sysorg.org.sameIdCheck','��Ա����',975,528,null,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveA01_config")
	public int saveA01_config() throws RadowException {
		String jsonp = this.request.getParameter("jsonp");
		//System.out.println(jsonp);
		if(jsonp!=null&&!"".equals(jsonp)){
			try {
				CommSQL.updateA01_config(jsonp,SysManagerUtils.getUserId());
			} catch (AppException e) {
				this.setMainMessage("����ʧ�ܣ�"+e.getDetailMessage());
				e.printStackTrace();
				return EventRtnType.FAILD;
			}
		}
		StringBuilder cm = new StringBuilder("[");
		StringBuilder dm = new StringBuilder("[");
		cm.append("new Ext.grid.RowNumberer({locked:true,header:'',width:23}), "
				+ "{locked:true,header: \"<div class='x-grid3-check-col-td'><div class='x-grid3-check-col' alowCheck='true' id='selectall_peopleInfoGrid_personcheck' onclick='odin.selectAllFuncForE3(\\\"peopleInfoGrid\\\",this,\\\"personcheck\\\");getCheckList(\\\"peopleInfoGrid\\\",\\\"personcheck\\\",this);'></div></div>\","
				+ "hidden:false,align:'center', width: 40, sortable: false, enterAutoAddRow:false,"
				+ "renderer:function(value, params, record,rowIndex,colIndex,ds){var rtn = '';params.css=' x-grid3-check-col-td';if(value==true || value=='true'){rtn=\"<div class='x-grid3-check-col-on' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}else{rtn=\"<div class='x-grid3-check-col' alowCheck='true' onclick='odin.accCheckedForE3(this,\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");getCheckList2(\"+rowIndex+\",\"+colIndex+\",\\\"personcheck\\\",\\\"peopleInfoGrid\\\");'></div>\";}return odin.renderEdit(rtn,params,record,rowIndex,colIndex,ds);}, "
				+ "editable:false,  hideable: false, dataIndex: 'personcheck', editor: new Ext.form.Checkbox({inputType:'',fireKey:odin.doAccSpecialkey})},");
		dm.append("{name: 'personcheck'},{name: 'a0000'},");
		int i = 0;
		for(Object[] o : CommSQL.A01_CONFIG_LIST.get(SysManagerUtils.getUserId())){
			String name = o[0].toString();
			String editor = o[5].toString().toLowerCase();
			String header = o[2].toString();
			String desc = o[6].toString();
			String width = o[3].toString();
			String codeType = o[4]==null?"":o[4].toString();
			String renderer = o[7]==null?"":o[7].toString();
			String align = o[9].toString();
			if(!"".equals(renderer)){
				//renderer = "var v="+renderer+"(a,b,c,d,e,f);";
				renderer = "function(a,b,c,d,e,f){var v="+renderer+"(a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}else{
				renderer = "function(v,b,c,d,e,f){odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}";
			}
			boolean locked =false;
			if("a0101".equals(name)){
				locked = true;
			}
		//	i++;
			if(!"a0000".equals(name)){
				if("text".equals(editor)){
					cm.append("{locked:"+locked+",header: \""+header+"\",hidden:false,align:'"+align+"', width: "+width+", sortable: true,"
							+ " enterAutoAddRow:false,renderer:"+renderer+", editable:false, "
							+ " dataIndex: '"+name+"', editor: new Ext.form.TextField({allowBlank:true ,fireKey:odin.doAccSpecialkey})},");
				
					dm.append("{name: '"+name+"'},");

				}else if("select".equals(editor)){
					cm.append("{locked:"+locked+",header: \""+header+"\",hidden:false,align:'"+align+"', width: "+width+", sortable: true, "
							+ "enterAutoAddRow:false,renderer:function(a,b,c,d,e,f){var v=odin.doGridSelectColRender('peopleInfoGrid','"+name+"',a,b,c,d,e,f);odin.renderEdit(v,b,c,d,e,f);return radow.renderAlt(v,b,c,d,e,f,\"\")}, editable:false,  dataIndex: '"+name+"', "
							+ "editor: new Ext.form.ComboBox({store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:"+CodeType2js.getCodeTypeJS(codeType)+","
							+ "createFilterFn:odin.createFilterFn}),displayField:'value',typeAhead: false,mode: 'local',triggerAction: 'all',"
							+ "editable:true,selectOnFocus:true,hideTrigger:false,expand:function(){odin.setListWidth(this);Ext.form.ComboBox.prototype.expand.call(this);},allowBlank:true ,fireKey:odin.doAccSpecialkey})},");
				
					dm.append("{name: '"+name+"'},");
				}
				
			}
			
		}
		cm.deleteCharAt(cm.length()-1);
		cm.append("]");
		dm.deleteCharAt(dm.length()-1);
		dm.append("]");
		this.getExecuteSG().addExecuteCode(cm.toString()+"{split}"+dm.toString());
		//this.reloadPage();
		this.setMainMessage("����ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��ʼ����֯������
	@PageEvent("a01_config_tree")
	public int a01_config_tree() throws PrivilegeException {
		PublicWindowPageModel publicWindowPageModel =new PublicWindowPageModel();
		StringBuilder sb_tree = new StringBuilder("[");
		List<Object[]> list = CommSQL.getA01_config(SysManagerUtils.getUserId());

		for(Object[] o : list){
			String name = o[0].toString();
			String editor = o[5].toString().toLowerCase();
			String header = o[2].toString();
			String desc = o[6].toString();
			String width = o[3].toString();
			String codeType = o[4]==null?"":o[4].toString();
			String renderer = o[7]==null?"":o[7].toString();
			sb_tree.append(" {text: '"+desc+"',id:'"+name+"',leaf:true,checked:"+o[9].toString()+"},");
		}
		sb_tree.append("]");
		this.setSelfDefResData(sb_tree.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	//��ʼ���б��Զ��������ֶλ�����
	@PageEvent("a01_sort_tree")
	public int a01_sort_tree() throws PrivilegeException {
		PublicWindowPageModel publicWindowPageModel =new PublicWindowPageModel();
		StringBuilder sb_tree = new StringBuilder("[");
		List<Object[]> list = CommSQL.getA01_sort_config(SysManagerUtils.getUserId());
		
		for(Object[] o : list){
			String name = o[0].toString();
			String desc = o[4].toString();
			
			//String codeType = o[4]==null?"":o[4].toString();
			
			sb_tree.append(" {text: '"+desc+"',id:'"+name+"',leaf:true,checked:"+o[6].toString()+",orderType:"+o[3].toString()+"},");
		}
		sb_tree.append("]");
		this.setSelfDefResData(sb_tree.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	@PageEvent("saveA01_sortConfig")
	public int saveA01_sortConfig() throws RadowException {
		
		String jsonp = this.request.getParameter("jsonp");
		//System.out.println(jsonp);
		if(jsonp!=null&&!"".equals(jsonp)){
			try {
				CommSQL.updateA01_sortConfig(jsonp,SysManagerUtils.getUserId());
			} catch (AppException e) {
				this.setMainMessage("����ʧ�ܣ�"+e.getDetailMessage());
				e.printStackTrace();
				return EventRtnType.FAILD;
			}
		}
		
		this.setMainMessage("����ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("expword")
	public int expWord(String type)throws AppException, RadowException, IOException{
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		if(type.equals("1")){
			//�ɲ�����������
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("1_1")){
			//�ɲ�����������(�����)
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//����Ա�ǼǱ�
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//���յǼǱ�
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//����Ա�ǼǱ�����
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//���յǼǱ�����
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//��ȿ��˵ǼǱ�
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//����������
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//�ɲ����ᣨһ��һ�У�
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//�ɲ����ᣨ���������飩
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//����Ա¼�ñ�
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//����Ա����������
			tpid = "9e7e1226-6fa1-46a1-8270";
		}else if(type.equals("12")){
			//����Աְ����ת��
			tpid = "28bc4d39-dccd-4f07-8aa9";
		}
		 
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//��ѡ��Աid
			ids = idss;
		}else{
			//δ��ѡ��Ա��ȫѡ
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		
		/*if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("��ѡ��200�˽��д���!");
			return EventRtnType.NORMAL_SUCCESS;	
		}*/
 
		//�����ͻ�Ҫ���Ϊ2000
		if (length >2000 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("��ѡ��2000�˽��д���!");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
				//�ж��������Ƿ�����ͬһ���˵�λ�£���Թ���Ա�ǼǱ�����Ͳι��ǼǱ�����
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals(""
						+ "")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
					//����Ա
					for(int i=0;i<list2.size();i++){
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
						if(pertype.size()>0){
							String a0160 = pertype.get(0)+"";
							if(!a0160.equals("1")){//!
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
										+ " '��ʾ', "
										+ " '��ѡ��Ա�����ǹ���Ա���Ƿ����������', "
										+ " function (btn){ "
										+ "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','"+type+"'); "
										+ "	} "
										+ "} "
									    + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}else{
							this.setMainMessage("�����쳣��");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}else{
					//�ǹ���Ա
					for(int i=0;i<list2.size();i++){
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
						if(pertype.size()>0){
							String a0160 = pertype.get(0)+"";
							if(a0160.equals("1")){
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
										+ " '��ʾ', "
										+ " '��ѡ��Ա��������Ա���Ƿ����������', "
										+ " function (btn){ "
										+ "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','"+type+"'); "
										+ "	} "
										+ "} "
									    + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}else{
							this.setMainMessage("�����쳣��");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
				//��ȡ�������ṹ
				List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
				
				Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
				for(Object[] treedata : listres){
					TreeNode rootnode = genNode(treedata);
					nodemap.put(rootnode.getId(), rootnode);
				}
				
				for(int i=0;i<list2.size();i++){
					String sql = "select a0201b from a02 where a0000 = '"+list2.get(i)+"' and a0255 = '1'";
					List list3 = sess.createSQLQuery(sql).list();
					List<String> compare = new ArrayList<String>();
					for(int j=0;j<list3.size();j++){
						String a0201b = list3.get(j).toString();
						while(true){
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if(n == null){
								a0201b = "-1";
								//throw new RadowException("������ȡ�쳣");
							}else{
								a0201b = n.getId();
							}
//							if(n.getText() == null||"".equals(n.getText())){
//								throw new RadowException("������ȡ�쳣");
//							}
							if(!"1".equals(cn.getText())){//���Ƿ��˵�λ,���������һ���
								continue;
							}else{//���˵�λ
								compare.add(cn.getId());
								break;
							}
						}
					}
					if(i==0){
						result = compare;
					}else{
						result = receiveCollectionList(result, compare);
					}
					if(result.size()==0){
						//�����ѡ��Աû����ͬ���˵�λ����ʾ
						this.setMainMessage("��ѡ��Ա������ͬһ���˵�λ");
						return EventRtnType.NORMAL_SUCCESS;	
					}
				}
			}
			List<String> wordPaths = null;
			try {
				if("1_1".equals(type)){
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word1",ids,result, personids);
				}else{
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result, personids);
				}
				
				String zipPath = wordPaths.get(0);
				String infile = "";
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
						infile = zipPath + "����Ա�ǼǱ�����.doc" ;
			        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
			        	infile = zipPath + "���յǼǱ�����.doc" ;
			        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
			        	infile = zipPath + "�ɲ������ᣨһ��һ�У�.doc" ;
			        }else{
			        	infile = zipPath + "�ɲ������ᣨ���������飩.doc" ;
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
	
	@PageEvent("exp")
	public int exp(String type)throws AppException, RadowException, IOException{
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		if(type.equals("1")){
			//�ɲ�����������
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//����Ա�ǼǱ�
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//���յǼǱ�
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//����Ա�ǼǱ�����
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//���յǼǱ�����
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//��ȿ��˵ǼǱ�
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//����������
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//�ɲ����ᣨһ��һ�У�
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//�ɲ����ᣨ���������飩
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//����Ա¼�ñ�
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//����Ա����������
			tpid = "9e7e1226-6fa1-46a1-8270";
		}
		
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//��ѡ��Աid
			ids = idss;
		}else{
			//δ��ѡ��Ա��ȫѡ
			ids = (String)request.getSession().getAttribute("personidall");
		}
		if(StringUtil.isEmpty(ids)){
			//���idsȡֵΪ�յĻ���˵�����Ҽ����ɵı�ᣬȡ�����˵�id
			ids = this.getPageElement("expword_personid").getValue();
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("��ѡ��200�˽��д���!");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
				//�ж��������Ƿ�����ͬһ���˵�λ�£���Թ���Ա�ǼǱ�����Ͳι��ǼǱ�����
				//��ȡ�������ṹ
				List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
				
				Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
				for(Object[] treedata : listres){
					TreeNode rootnode = genNode(treedata);
					nodemap.put(rootnode.getId(), rootnode);
				}
				
				for(int i=0;i<list2.size();i++){
					String sql = "select a0201b from a02 where a0000 = '"+list2.get(i)+"' and a0255 = '1'";
					List list3 = sess.createSQLQuery(sql).list();
					List<String> compare = new ArrayList<String>();
					for(int j=0;j<list3.size();j++){
						String a0201b = list3.get(j).toString();
						while(true){
							TreeNode cn = nodemap.get(a0201b);
							TreeNode n = nodemap.get(cn.getParentid());
							if(n == null){
								a0201b = "-1";
								//throw new RadowException("������ȡ�쳣");
							}else{
								a0201b = n.getId();
							}
//							if(n.getText() == null||"".equals(n.getText())){
//								throw new RadowException("������ȡ�쳣");
//							}
							if(!"1".equals(cn.getText())){//���Ƿ��˵�λ,���������һ���
								continue;
							}else{//���˵�λ
								compare.add(cn.getId());
								break;
							}
						}
					}
					if(i==0){
						result = compare;
					}else{
						result = receiveCollectionList(result, compare);
					}
					if(result.size()==0){
						//�����ѡ��Աû����ͬ���˵�λ����ʾ
						this.setMainMessage("��ѡ��Ա������ͬһ���˵�λ");
						return EventRtnType.NORMAL_SUCCESS;	
					}
				}
			}
			List<String> wordPaths = null;
			try {
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result, personids);
				String zipPath = wordPaths.get(0);
				String infile = "";
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
						infile = zipPath + "����Ա�ǼǱ�����.doc" ;
			        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
			        	infile = zipPath + "���յǼǱ�����.doc" ;
			        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
			        	infile = zipPath + "�ɲ������ᣨһ��һ�У�.doc" ;
			        }else{
			        	infile = zipPath + "�ɲ������ᣨ���������飩.doc" ;
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
	
	/**
     * @������������ȡ����ArrayList�Ľ���
     * @param firstArrayList ��һ��ArrayList
     * @param secondArrayList �ڶ���ArrayList
     * @return resultList ����ArrayList
     */
    public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);// �󼯺���linkedlist  
        HashSet<String> othHash = new HashSet<String>(secondArrayList);// С������hashset  
        Iterator<String> iter = result.iterator();// ����Iterator�������������ݵĲ���  
        while(iter.hasNext()) {
            if(!othHash.contains(iter.next())) {  
                iter.remove();            
            }     
        }
        resultList = new ArrayList<String>(result);
        return resultList;
    }

	
	private static TreeNode genNode(Object[] treedata) {
		TreeNode node = new TreeNode();
		node.setId(treedata[0].toString());
		node.setText(treedata[1].toString());
		node.setLink(treedata[3].toString());
		node.setLeaf(true);
		if(treedata[2]!=null)
			node.setParentid(treedata[2].toString());
		node.setOrderno((short)1);

		return node;
	}
	
	//�Ҽ�����word
	@PageEvent("expword1")
	public int expWord1(String type)throws AppException, RadowException, IOException{
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		if(type.equals("1")){
			//�ɲ�����������
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("13")){
			//�ɲ�����������(�����)
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//����Ա�ǼǱ�
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//���յǼǱ�
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//����Ա�ǼǱ�����
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//���յǼǱ�����
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//��ȿ��˵ǼǱ�
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//����������
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//�ɲ����ᣨһ��һ�У�
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//�ɲ����ᣨ���������飩
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//����Ա¼�ñ�
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//����Ա����������
			tpid = "9e7e1226-6fa1-46a1-8270";
		}else if(type.equals("12")){
			//����Աְ����ת��
			tpid = "28bc4d39-dccd-4f07-8aa9";
		}
		
		String id = this.getPageElement("expword_personid").getValue();
		List<String> list2 = new ArrayList<String>();
		list2.add(id);
		List<String> result = new ArrayList<String>();
		if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
				||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
			//�ж��������Ƿ�����ͬһ���˵�λ�£���Թ���Ա�ǼǱ�����Ͳι��ǼǱ�����
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
				//����Ա
				for(int i=0;i<list2.size();i++){
					List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
					if(pertype.size()>0){
						String a0160 = pertype.get(0)+"";
						if(!a0160.equals("1")){
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
									+ " '��ʾ', "
									+ " '��ѡ��Ա�����ǹ���Ա���Ƿ����������', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('exp','"+type+"'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}else{
						this.setMainMessage("�����쳣��");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				//�ǹ���Ա
				for(int i=0;i<list2.size();i++){
					List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
					if(pertype.size()>0){
						String a0160 = pertype.get(0)+"";
						if(a0160.equals("1")){
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
									+ " '��ʾ', "
									+ " '��ѡ��Ա��������Ա���Ƿ����������', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('exp','"+type+"'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}else{
						this.setMainMessage("�����쳣��");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			//��ȡ�������ṹ
			List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
			
			Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
			for(Object[] treedata : listres){
				TreeNode rootnode = genNode(treedata);
				nodemap.put(rootnode.getId(), rootnode);
			}
			
			for(int i=0;i<list2.size();i++){
				String sql = "select a0201b from a02 where a0000 = '"+list2.get(i)+"' and a0255 = '1'";
				List list3 = sess.createSQLQuery(sql).list();
				List<String> compare = new ArrayList<String>();
				for(int j=0;j<list3.size();j++){
					String a0201b = list3.get(j).toString();
					while(true){
						TreeNode cn = nodemap.get(a0201b);
						TreeNode n = nodemap.get(cn.getParentid());
						if(n == null){
							a0201b = "-1";
							//throw new RadowException("������ȡ�쳣");
						}else{
							a0201b = n.getId();
						}
//						if(n.getText() == null||"".equals(n.getText())){
//							throw new RadowException("������ȡ�쳣");
//						}
						if(!"1".equals(cn.getText())){//���Ƿ��˵�λ,���������һ���
							continue;
						}else{//���˵�λ
							compare.add(cn.getId());
							break;
						}
					}
				}
				if(i==0){
					result = compare;
				}else{
					result = receiveCollectionList(result, compare);
				}
				if(result.size()==0){
					//�����ѡ��Աû����ͬ���˵�λ����ʾ
					this.setMainMessage("��ѡ��Ա������ͬһ���˵�λ");
					return EventRtnType.NORMAL_SUCCESS;	
				}
			}
		}
		List<String> wordPaths = null;
		try {
			if("13".equals(type)){
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word1","'"+id+"'",result, null);
			}else{
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word","'"+id+"'",result, null);
			}
			//wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word","'"+id+"'",result);
			String zipPath = wordPaths.get(0);
			String infile = "";
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
					infile = zipPath + "����Ա�ǼǱ�����.doc" ;
		        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
		        	infile = zipPath + "���յǼǱ�����.doc" ;
		        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
		        	infile = zipPath + "�ɲ������ᣨһ��һ�У�.doc" ;
		        }else{
		        	infile = zipPath + "�ɲ������ᣨ���������飩.doc" ;
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
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	//�����ɲ�����������
		@NoRequiredValidate
		@PageEvent("exportGBDocBtn.onclick")
		public int exportGBDocBtn() throws RadowException, AppException {
			HBSession sess = HBUtil.getHBSession();
			String tableType = this.getPageElement("tableType").getValue();
			String a0000s = "";
			String name ="";
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(tableType)){	//�б�
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {			//�б�û�й�ѡ�������ȫ��
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						for(int j = 0; j < list.size(); j++){
							HashMap<String, Object> map = list.get(j);
							a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
							a0000s += "'"+a0000+"',";
							name += (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult()+",";
						}
					}
					
					if(a0000s.length() < 1){
						/*this.setMainMessage("��ǰ�б�û����Ա��");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					
					a0000s = a0000s.substring(0, a0000s.length()-1);
				}else{
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe != null){
						String a0000 = "";
						List<HashMap<String, Object>> list = pe.getValueList();
						String zippath =ExpRar.expFile();
						String infile ="";
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
								a0000s += "'"+a0000+"',";
								name += (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult()+",";
							}
						}
					}
					if(a0000s.length() < 1){
						/*this.setMainMessage("��ǰ�б�û����Ա��");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					
					a0000s = a0000s.substring(0, a0000s.length()-1);
				}
			}else if("2".equals(tableType) || "3".equals(tableType)){
				
				String picA0000s = this.getPageElement("picA0000s").getValue();
				
				if(picA0000s == null || "".equals(picA0000s.trim())){	//û�й�ѡ �򵼳�ȫ��
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							a0000s += "'"+a0000+"',";
							name += (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult()+",";
						}
					}
					if(a0000s.length() < 1){
						/*this.setMainMessage("��ǰ�б�û����Ա��");
						return EventRtnType.FAILD;*/
						
						this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					
					a0000s = a0000s.substring(0, a0000s.length()-1);
				}else{
					a0000s = picA0000s.substring(0, picA0000s.length()-1);
					String[] a0000_arr = a0000s.replaceAll("\\'", "").split(",");
					if(a0000_arr != null && a0000_arr.length > 0){
						for(int j = 0; j < a0000_arr.length; j++){
							String a0000 = a0000_arr[j];
							name += (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult()+",";
						}
					}
				}
			}
			
			if(name.length() < 1){
				/*this.setMainMessage("��ǰ�б�û����Ա��");
				return EventRtnType.FAILD;*/
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			name = name.substring(0,name.length()-1);
			
			this.getPageElement("docA0101s").setValue(name);
			this.getPageElement("docA0000s").setValue(a0000s);
			this.getExecuteSG().addExecuteCode("exportGBDoc();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		//�Ҽ�����Ա״̬���
		@PageEvent("changeStateRigth")
		@GridDataRange
		public int changeStateRigth(String a0000) throws RadowException, AppException {
			
			HttpSession session=request.getSession(); 
			session.setAttribute("a0000s",a0000+","); 
			
			String a0000F = a0000.toString();
			a0000F = a0000F + ",";
			a0000F = a0000F.substring(0,a0000F.length()-1);
			String[] values = a0000F.split(",");
			String oneId = values[0];				//��һ����id
			
			StringBuffer a0000SQl = new StringBuffer();
			for (int i = 0; i < values.length; i++) {
				a0000SQl.append("'").append(values[i]==null?"":values[i].toString()).append("',");
			}
			String a0000SQLF = a0000SQl.toString();
			a0000SQLF = a0000SQLF.substring(0,a0000SQLF.length()-1);
			
			String sql = "select count(1) from a01 where a0163 = (select a0163 from a01 where a0000 = '"+oneId+"') and a0000 in ("+a0000SQLF+")";
			String allPeople = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
			if(Integer.valueOf(allPeople) != values.length){
				
				this.getExecuteSG().addExecuteCode("$h.alert('ϵͳ��ʾ��','��ѡ��Ա�в�ͬ�Ĺ���״̬��',null,220);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//����һ������Ա����״̬��ֵ��ҳ��
			String sqlA0163 = "select a0163 from a01 where a0000 = '"+oneId+"'";
			String a0163 = HBUtil.getHBSession().createSQLQuery(sqlA0163).uniqueResult().toString();
			
			String a0163NameSQL =  "select code_name from code_value t where t.code_type='ZB126' and t.code_value = '"+a0163+"'";
			String a0163Name = HBUtil.getHBSession().createSQLQuery(a0163NameSQL).uniqueResult().toString();
			
			this.getPageElement("a0163").setValue(a0163);			//��Ա����״̬
			this.getPageElement("a0163_combo").setValue(a0163Name);	//��Ա����״̬�ı�
			
			
		    //this.openWindow("changeStateWin", "pages.publicServantManage.ChangeState");
			this.getExecuteSG().addExecuteCode("$h.openWin('changeStateWin','pages.publicServantManage.ChangeState','��Ա״̬���',450,250,document.getElementById('tableType').value,ctxPath)");
			
			return EventRtnType.NORMAL_SUCCESS;
		}
	
		//�Ҽ���������
		@PageEvent("out")
		public int out(String a0000)throws AppException, RadowException, SQLException{
	/*		//����Աid�浽session���ڱ��չʾ�ĵط���
			request.getSession().setAttribute("personidy", a0000);*/
			request.getSession().setAttribute("personidy", "|"+a0000+"|@");
			request.getSession().removeAttribute("personidall");
			return EventRtnType.NORMAL_SUCCESS;	
		}
		
		
		
		//�Ҽ������������Word
		@NoRequiredValidate
		@PageEvent("exportGBDocRigth")
		public int exportGBDocRigth(String a0000) throws RadowException, AppException {
			HBSession sess = HBUtil.getHBSession();
			
			String a0000s = "";
			String name ="";
			
			a0000s += "'"+a0000+"',";
			name += (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult()+",";
			
			a0000s = a0000s.substring(0, a0000s.length()-1);
			
			name = name.substring(0,name.length()-1);
			
			this.getPageElement("docA0101s").setValue(name);
			this.getPageElement("docA0000s").setValue(a0000s);
			this.getExecuteSG().addExecuteCode("exportGBDoc();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		//�һ������������PDF
		@PageEvent("exportPdf")
		@GridDataRange
		public int exportPdfRigth(String a0000) throws RadowException{ 
			
			PageElement pe = this.getPageElement("peopleInfoGrid");
			if(pe!=null){
				List<HashMap<String, Object>> list = pe.getValueList();
				String zippath =ExpRar.expFile();
				String infile ="";
				String name ="";
				Runtime rt = Runtime.getRuntime();
				Process p = null;
				
				
				String lrm = createLrmStr(a0000);
				name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
				
				try {
					FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
					List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
					if(list15.size()>0){
						A57 a57 = list15.get(0);
						if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
							File f = new File(zippath + name+".pic");
							FileOutputStream fos = new FileOutputStream(f);
							File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
							if(f2.exists() && f2.isFile()){
								InputStream is = new FileInputStream(f2);// �������ݺ�ת��Ϊ��������
								byte[] data = new byte[1024];
								while (is.read(data) != -1) {
									fos.write(data);
								}
								is.close();
							}
							fos.close();
							
						}
					}
					SevenZipUtil.zzbRmb(zippath, name);
					
					File dec = new File(zippath);
					File[] files = dec.listFiles();
					for (File f0 : files) {
						if((f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("bmp")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("lrm")||(f0.getName().substring(f0.getName().lastIndexOf(".")+1)).equalsIgnoreCase("pic")){
							f0.delete();
						}
					}
				} catch (Exception e) {
					this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
				}
				
				try {
					infile =zippath.substring(0,zippath.length()-1)+".zip" ;
					SevenZipUtil.zip7z(zippath, infile, null);
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				} catch (Exception e) {
					this.setMainMessage("�����ļ���������ϵ����Ա"); // ������ʾ��Ϣ
				}
			}
			
			return EventRtnType.NORMAL_SUCCESS;
				
		}
		
		
		//�һ������������HZB
		@PageEvent("importHzb")
		public int importHzb(String a0000) throws RadowException {
			
			String allids = "'"+a0000+"'";
			//String allids = ids.substring(0,ids.length()-1);
			String id = UUID.randomUUID().toString().replace("-", "");
			
			try {
				CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
				KingbsconfigBS.saveImpDetailInit3(id);
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				DataPsnImpThread thr = new DataPsnImpThread(id, user,"","","","","",
						"","","","","","","",userVo,allids);
				new Thread(thr,"Thread_psnexp").start();
				this.setRadow_parent_data(id);
			    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','��������',500,300,'"+id+"',ctxPath)");
			    
			} catch (Exception e) {
				e.printStackTrace();
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		@PageEvent("setTemporarySort")
		public int setTemporarySort() throws RadowException {
			
			HttpSession session=request.getSession(); 
			session.setAttribute("temporarySort","1"); 
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//��ӡ�����
		@PageEvent("printRmb")
		@Synchronous
		public int printRmb() throws Exception {
			  //��ӡ����ѡ���ж�
	        HttpSession session = request.getSession();
	        String printer= (String) session.getAttribute("Printer");
	        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
	        	this.setMainMessage("�����ô�ӡ������");
	        	return EventRtnType.NORMAL_SUCCESS;
	        }
	        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
	        if(printer.equals("")) {
	        	this.setMainMessage("����ȥ��ӡ��������ѡ��һ�ִ�ӡ����");
	        	return EventRtnType.NORMAL_SUCCESS;
	        }
	        if(printNum<=0) {
	        	this.setMainMessage("��ӡ�����������0���Ҳ���Ϊ�գ�");
	        	return EventRtnType.NORMAL_SUCCESS;
	        }
	        
	        
			HBSession sess = HBUtil.getHBSession();
			String tpid = "";
			String type= "1";
			if(type.equals("1")){
				//�ɲ�����������
				tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
			}else if(type.equals("2")){
				//����Ա�ǼǱ�
				tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
			}else if(type.equals("3")){
				//���յǼǱ�
				tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
			}else if(type.equals("4")){
				//����Ա�ǼǱ�����
				tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
			}else if(type.equals("5")){
				//���յǼǱ�����
				tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
			}else if(type.equals("6")){
				//��ȿ��˵ǼǱ�
				tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
			}else if(type.equals("7")){
				//����������
				tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
			}else if(type.equals("8")){
				//�ɲ����ᣨһ��һ�У�
				tpid = "47b1011d70f34aefb89365bbfce";
			}else if(type.equals("9")){
				//�ɲ����ᣨ���������飩
				tpid = "eebdefc2-4d67-4452-a973";
			}else if(type.equals("10")){
				//����Ա¼�ñ�
				tpid = "3de527c0-ea23-42c4-a66f";
			}else if(type.equals("11")){
				//����Ա����������
				tpid = "9e7e1226-6fa1-46a1-8270";
			}
			 
			String ids = "";
			String idss = (String)request.getSession().getAttribute("personidy");
			if(idss != null && !"".equals(idss)){
				//��ѡ��Աid
				ids = idss;
			}else{
				//δ��ѡ��Ա��ȫѡ
				ids = (String)request.getSession().getAttribute("personidall");
			}
			int length = ids.split("@").length;
			List<String> result = new ArrayList<String>();
			if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
					.contains(tpid)) {
				this.setMainMessage("��ѡ��200�˽��д���!");
				return EventRtnType.NORMAL_SUCCESS;	
			}else{
				String[] personids = ids.split("@");
				List<String> list2 = new ArrayList<String>();
				for(int j = 0; j < personids.length; j++){
					String a0000 = personids[j].replace("|", "");
					list2.add(a0000);
				}
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
					//�ж��������Ƿ�����ͬһ���˵�λ�£���Թ���Ա�ǼǱ�����Ͳι��ǼǱ�����
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals(""
							+ "")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
						//����Ա
						for(int i=0;i<list2.size();i++){
							List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
							if(pertype.size()>0){
								String a0160 = pertype.get(0)+"";
								if(!a0160.equals("1")){//!
									this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
											+ " '��ʾ', "
											+ " '��ѡ��Ա�����ǹ���Ա���Ƿ����������', "
											+ " function (btn){ "
											+ "	if(btn=='yes'){ "
											+ "		radow.doEvent('exp','"+type+"'); "
											+ "	} "
											+ "} "
										    + ");");
									return EventRtnType.NORMAL_SUCCESS;
								}
							}else{
								this.setMainMessage("�����쳣��");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
					}else{
						//�ǹ���Ա
						for(int i=0;i<list2.size();i++){
							List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
							if(pertype.size()>0){
								String a0160 = pertype.get(0)+"";
								if(a0160.equals("1")){
									this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( " 
											+ " '��ʾ', "
											+ " '��ѡ��Ա��������Ա���Ƿ����������', "
											+ " function (btn){ "
											+ "	if(btn=='yes'){ "
											+ "		radow.doEvent('exp','"+type+"'); "
											+ "	} "
											+ "} "
										    + ");");
									return EventRtnType.NORMAL_SUCCESS;
								}
							}else{
								this.setMainMessage("�����쳣��");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
					}
					//��ȡ�������ṹ
					List<Object[]> listres = sess.createSQLQuery("select b0111,b0194,b0121,B0101 from b01").list();
					
					Map<String,TreeNode> nodemap = new LinkedHashMap<String, TreeNode>();
					for(Object[] treedata : listres){
						TreeNode rootnode = genNode(treedata);
						nodemap.put(rootnode.getId(), rootnode);
					}
					
					for(int i=0;i<list2.size();i++){
						String sql = "select a0201b from a02 where a0000 = '"+list2.get(i)+"' and a0255 = '1'";
						List list3 = sess.createSQLQuery(sql).list();
						List<String> compare = new ArrayList<String>();
						for(int j=0;j<list3.size();j++){
							String a0201b = list3.get(j).toString();
							while(true){
								TreeNode cn = nodemap.get(a0201b);
								TreeNode n = nodemap.get(cn.getParentid());
								if(n == null){
									a0201b = "-1";
									//throw new RadowException("������ȡ�쳣");
								}else{
									a0201b = n.getId();
								}
//								if(n.getText() == null||"".equals(n.getText())){
//									throw new RadowException("������ȡ�쳣");
//								}
								if(!"1".equals(cn.getText())){//���Ƿ��˵�λ,���������һ���
									continue;
								}else{//���˵�λ
									compare.add(cn.getId());
									break;
								}
							}
						}
						if(i==0){
							result = compare;
						}else{
							result = receiveCollectionList(result, compare);
						}
						if(result.size()==0){
							//�����ѡ��Աû����ͬ���˵�λ����ʾ
							this.setMainMessage("��ѡ��Ա������ͬһ���˵�λ");
							return EventRtnType.NORMAL_SUCCESS;	
						}
					}
				}
				List<String> wordPaths = null;
				try {
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result, personids);
					
					 File f = new File(wordPaths.get(0));
					    if (!f.exists()) {
					      this.setMainMessage("�ļ������ڣ�");
					      return EventRtnType.NORMAL_SUCCESS;
					    }
					    File fa[] = f.listFiles();
					for (int p = 1; p <= printNum; p++) {
						for (int i = 0; i < fa.length; i++) {
							File fs = fa[i];
							if (fs.isDirectory()) {
								System.out.println(fs.getName() + " [Ŀ¼]");
							} else {
								//printFile(wordPaths.get(0) + fs.getName(), printer);
								String path = wordPaths.get(0) + fs.getName();
								String rootPath = PhotosUtil.getRootPath();
								path = path.replace(rootPath, "").replace("\\", "/");
								this.getExecuteSG().addExecuteCode("printStart('"+path+"');");
							}
						}
					}
						   
					
				} catch (AppException e) {
					e.printStackTrace();
				}
			this.setMainMessage("��ӡ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
	}
	
	/*public static void main(String[] args) {
		List<String> list = getPrinters();
		System.out.println(list);
	}*/
	@PageEvent("ppsData.onclick")
	public int ppsData() {
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('ppsDataWin','pages.pps.ppsEnter','pps���ݳ���',975,528,'ss','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//��¼Ĭ�ϵĴ�ӡ������
	@PageEvent("printSetInit")
	public int printSetInit(String printer) {
		HttpSession session = request.getSession();
		session.setAttribute("Printer", printer);
		if(session.getAttribute("PrintNum")==null) {
			session.setAttribute("PrintNum", "1");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�Զ��嵼������
	@PageEvent("expBtnwordonclick")
	@NoRequiredValidate
	public int word()throws RadowException{
		 this.getExecuteSG().addExecuteCode("view()");
		return EventRtnType.NORMAL_SUCCESS;
	}
/*	//�Զ��嵼������
	@PageEvent("expBtnword.onclick")
	@NoRequiredValidate
	public int word()throws RadowException{
		this.getExecuteSG().addExecuteCode("view()");
		return EventRtnType.NORMAL_SUCCESS;
	}
*/			
	@PageEvent("expwordw")//�Զ���word����
	public int expWordw(String namebs)throws AppException, RadowException{
		String[] nbs= namebs.split(",");
		 String name = nbs[0];
		 String bs = nbs[1];
		 String rad = nbs[2];
		 
		 
		  //��ӡ����ѡ���ж�
		 /*if("dy".equals(bs)&&bs!=null){
			 HttpSession session = request.getSession();
		        String printer= (String) session.getAttribute("Printer");
		        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
		        	this.setMainMessage("�����ô�ӡ������");
		        	return EventRtnType.NORMAL_SUCCESS;
		        }
		        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
		        if(printer.equals("")) {
		        	this.setMainMessage("����ȥ��ӡ��������ѡ��һ�ִ�ӡ����");
		        	return EventRtnType.NORMAL_SUCCESS;
		        }
		        if(printNum<=0) {
		        	this.setMainMessage("��ӡ�����������0���Ҳ���Ϊ�գ�");
		        	return EventRtnType.NORMAL_SUCCESS;
		        } 
		 }*/
	        
		 
		 
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		String ids = "";
		String idfromss = (String)request.getSession().getAttribute("personfromidy");
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idfromss != null && !"".equals(idfromss)){
			//��ѡ��Աid
			ids = idfromss;
			request.getSession().removeAttribute("personfromidy");
		}else if(idss != null && !"".equals(idss)){
			//��ѡ��Աid
			ids = idss;
		}else{
			//δ��ѡ��Ա��ȫѡ
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length >200 /*&& "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)*/) {
			this.setMainMessage("��ѡ��200�˽��д���!");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			List<String> wordPaths = null;
			try {
				wordPaths = new ExportAsposeBS().getPdfsByA000s_asposew(list2,tpid,name,ids,result,request,rad, bs);
				if(wordPaths==null){
					this.setMainMessage("û���ҵ���Ӧģ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(wordPaths.get(0).contains("@")){
					this.getPageElement("downfile").setValue(wordPaths.get(0).replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				}else{
					if("xz".equals(bs)&&bs!=null){
						String zipPath = wordPaths.get(0);
						String infile = "";
			        	infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
					    SevenZipUtil.zip7z(zipPath, infile, null);
					    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.reloadTree()");
					}else{
						 File f = new File(wordPaths.get(0));
						    if (!f.exists()) {
						      this.setMainMessage("�ļ������ڣ�");
						      return EventRtnType.NORMAL_SUCCESS;
						    }
						    File fa[] = f.listFiles();
						    HttpSession session = request.getSession();
						    //int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
						for (int p = 1; p <= 1; p++) {
							for (int i = 0; i < fa.length; i++) {
								File fs = fa[i];
								if (fs.isDirectory()) {
									System.out.println(fs.getName() + " [Ŀ¼]");
								} else {
									//printFile(wordPaths.get(0) + fs.getName(), printer);
									/*String path = wordPaths.get(0) + fs.getName();
									String rootPath = PhotosUtil.getRootPath();
									path = path.replace(rootPath, "").replace("\\", "/");
									this.getExecuteSG().addExecuteCode("printStart('"+fs.toString().replace("\\", "\\\\")+"');");*/
									
									
									//printFile(wordPaths.get(0) + fs.getName(), printer);
									String path = wordPaths.get(0) + fs.getName();
									String rootPath = PhotosUtil.getRootPath();
									path= path.replace(rootPath, "").replace("//", "\\");
									this.getExecuteSG().addExecuteCode("printStart('"+path+"');");
								}
							}
						}
						this.setMainMessage("��ӡ���");
					}
					
				}
				
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("expworde")
	public int expWorde(String namebs)throws AppException, RadowException, FileNotFoundException, IOException{
		String[] nbs= namebs.split(",");
		 String name = nbs[0];
		 String bs = nbs[1];
		 String rad = nbs[2];
		 
		//��ӡ����ѡ���ж�
        /*HttpSession session = request.getSession();
        String printer= (String) session.getAttribute("Printer");
        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
        	this.setMainMessage("�����ô�ӡ������");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
        if(printer.equals("")) {
        	this.setMainMessage("����ȥ��ӡ��������ѡ��һ�ִ�ӡ����");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        if(printNum<=0) {
        	this.setMainMessage("��ӡ�����������0���Ҳ���Ϊ�գ�");
        	return EventRtnType.NORMAL_SUCCESS;
        }*/
	        
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//��ѡ��Աid
			ids = idss;
		}else{
			//δ��ѡ��Ա��ȫѡ
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length >200) {
			this.setMainMessage("��ѡ��200�˽��д���!");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			
			result.addAll(list2);
			
		}
		
		
		//System.out.println(result);
		
		//String doctpl = request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+ name;//·����ģ����
		//String jbmc = request.getSession().getServletContext().getRealPath("webOffice/word").replace("\\webOffice\\word", "")+"\\ziploud/"+UUID.randomUUID().toString().replace("-", "")+"/"+"expFiles_"+System.currentTimeMillis()+"/";
        String exportPath=request.getSession().getServletContext().getRealPath("webOffice/word");
		String infile = JbmcExp.expJbmc(name, result, AppConfig.HZB_PATH+"\\WebOffice",null,rad,exportPath);
		if("û��ģ��".equals(infile)){
			this.setMainMessage("û�ж�Ӧ��ģ��!");
			return EventRtnType.NORMAL_SUCCESS;
		}
        //String infile=jbmc.substring(0, jbmc.length()-1)+".zip"
       // String infile=jbmc+"��������.xls";
        //SevenZipUtil.zip7z(jbmc, jbmc.substring(0, jbmc.length()-1)+".zip", null);
		//this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		//this.getExecuteSG().addExecuteCode("window.reloadTree()");
       // this.setMainMessage("�����ɹ�!!");
		
		if("xz".equals(bs)&&bs!=null){
    	   this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
    	   this.getExecuteSG().addExecuteCode("window.reloadTree()");
       }else{
    	   for (int p = 1; p <= 1; p++) {
    		   this.getExecuteSG().addExecuteCode("printStart1('"+infile.substring(infile.indexOf("ziploud")).replace("\\", "\\\\")+"');");
    	   }
    	   this.setMainMessage("��ӡ���");
       }
       
		
		return EventRtnType.NORMAL_SUCCESS;	
}
	
/*public static void MergerRegion(HSSFSheet sheetCreat, HSSFSheet sheet) {
	 int sheetMergerCount = sheet.getNumMergedRegions();
	 for (int i = 0; i < sheetMergerCount; i++) {
		CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
		sheetCreat.addMergedRegion(mergedRegion);
	 }
}*/
	
//expworddw
@PageEvent("expworddw")
public int expworddw(String namebs)throws AppException, RadowException, FileNotFoundException, IOException{
	
	 String[] nbs= namebs.split(",");
	 String name = nbs[0];
	 String bs = nbs[1];
	 String rad = nbs[2];
	//��ӡ����ѡ���ж�
 /* HttpSession session = request.getSession();
  String printer= (String) session.getAttribute("Printer");
  if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
  	this.setMainMessage("�����ô�ӡ������");
  	return EventRtnType.NORMAL_SUCCESS;
  }
  int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
  if(printer.equals("")) {
  	this.setMainMessage("����ȥ��ӡ��������ѡ��һ�ִ�ӡ����");
  	return EventRtnType.NORMAL_SUCCESS;
  }
  if(printNum<=0) {
  	this.setMainMessage("��ӡ�����������0���Ҳ���Ϊ�գ�");
  	return EventRtnType.NORMAL_SUCCESS;
  }*/
	//��ȡ���б�ѡ���˵�id
	HBSession sess = HBUtil.getHBSession();
	String tpid = "";
	String ids = "";
	String idss = (String)request.getSession().getAttribute("personidy");
	if(idss != null && !"".equals(idss)){
		//��ѡ��Աid
		ids = idss;
	}else{
		//δ��ѡ��Ա��ȫѡ
		ids = (String)request.getSession().getAttribute("personidall");
	}
	int length = ids.split("@").length;
	List<String> result = new ArrayList<String>();
	if (length >200) {
		this.setMainMessage("��ѡ��200�˽��д���!");
		return EventRtnType.NORMAL_SUCCESS;	
	}else{
		String[] personids = ids.split("@");
		List<String> list2 = new ArrayList<String>();
		for(int j = 0; j < personids.length; j++){
			String a0000 = personids[j].replace("|", "");
			list2.add(a0000);
		}
		
		result.addAll(list2);
		
	}
	
	//����id��ȡÿ���˵���ְ����
	ArrayList<String> rzjgs = new ArrayList<String>();
	for(int i=0;i<result.size();i++){
		String a0000 = result.get(i);
		String sql="select distinct a0201b from a02 where a0000='"+a0000+"'";
		//��ѯidΪa0000����Ա�����ĵ�λ
		List list = sess.createSQLQuery(sql).list();
		String rzjgStr="";
		for (Object obj : list) {
			rzjgStr+=(String)obj+" ";
		}
		rzjgStr=rzjgStr.trim();
		
		String rzjg=a0000+","+rzjgStr;
		rzjgs.add(rzjg);
		
	}
	
	//ȡ��������ְ����
	TreeSet<String> jg = new TreeSet<String>();
	for(int i=0;i<rzjgs.size();i++){
		
		String str=rzjgs.get(i).split(",")[1];
		String[] split = str.split(" ");
		for (String string : split) {
			jg.add(string);
		}
	}
	
	//����map<����id,a0000>
	HashMap<String, String> map = new HashMap<String, String>();
	Iterator<String> iterator = jg.iterator();
	while(iterator.hasNext()){
		String next = iterator.next();
		//�����µ�������Աid
		String a0000s="";
		for(int i=0;i<rzjgs.size();i++){
			String str=rzjgs.get(i).split(",")[0];//ȡ��Աid
			String jgids=rzjgs.get(i).split(",")[1];//ȡ����id
			String[] jgidarr = jgids.split(" ");
			ArrayList<String> arrayList = new ArrayList<String>();
			for (String string : jgidarr) {
				arrayList.add(string);
			}
			if(arrayList.contains(next)){
				a0000s+=str+",";
			}
		}
		a0000s=a0000s.substring(0, a0000s.length()-1);
		map.put(next, a0000s);
	}
	
	System.out.println(map);
	
	//����Hssf��ȡexcelģ������
	String doctpl = AppConfig.HZB_PATH+"\\WebOffice"+"\\"+ name;//·����ģ����
	File file=new File(doctpl);
	HSSFWorkbook workbook = null;
	if(file.exists()){
		workbook = new HSSFWorkbook(new FileInputStream(file));
	}else{
		this.setMainMessage("û�ж�Ӧģ��!");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	//��ȡexcel�ĵ�һ��sheet
	HSSFSheet sheet = workbook.getSheetAt(0);
	
	 //��ȡ���б�������
    HashMap<String, String> headLine = new HashMap<String, String>();
    HashMap<String, HSSFCellStyle> headLineStyle = new HashMap<String, HSSFCellStyle>();
    HSSFRow row3 = sheet.getRow(2);
    if (row3 != null) {
        for (int k = 0; k < row3.getLastCellNum(); k++) {// getLastCellNum���ǻ�ȡ���һ����Ϊ�յ����ǵڼ���
            if (row3.getCell(k) != null) { // getCell ��ȡ��Ԫ������
               // System.out.print(row1.getCell(k) + "\t");
            	headLine.put(k+"", row3.getCell(k).getStringCellValue());
            	headLineStyle.put(k+"", row3.getCell(k).getCellStyle());
            } 
        }
    }
    
  //����list��ȡ������Ҫ��ѯ���ֶ�
    List<String> queryField= new ArrayList<String>();
	//��ȡsheet�еĵڶ������е�Ԫ������
	//����map�洢�ڶ��е���������
	HashMap<String, String> twoRowMap = new HashMap<String, String>();
	 HashMap<String, HSSFCellStyle> twoRowMapStyle = new HashMap<String, HSSFCellStyle>();
   HSSFRow row = sheet.getRow(3);
   if (row != null) {
       for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum���ǻ�ȡ���һ����Ϊ�յ����ǵڼ���
    	   HSSFCell cell = row.getCell(k);
           if (row.getCell(k) != null) { // getCell ��ȡ��Ԫ������
               //System.out.print(row.getCell(k) + "\t");
        	   //replaceAll("\\$\\{", "").replaceAll("\\}", "")
        	   String cellvalue="";
        	   switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					cellvalue=cell.getStringCellValue();
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					cellvalue=cell.getCellFormula();
					break;
				default:
					break;
				}
               if(!StringUtils.isEmpty(cellvalue)){
            	   twoRowMap.put(k+"", cellvalue.replaceAll("\\$\\{", "").replaceAll("\\}", ""));
            	   if(cellvalue.matches("\\$\\{[\\w]+\\}")){
            		   
            		   queryField.add(cellvalue.replaceAll("\\$\\{", "").replaceAll("\\}", ""));
            	   }
            	   twoRowMapStyle.put(k+"", cell.getCellStyle());
               }
              
              
           } 
       }
   }
   
   System.out.println(headLine);
   System.out.println(twoRowMap);
   System.out.println(headLine.size());
   System.out.println(twoRowMap.size());
   
   //String queryFieldStr="";
   ArrayList<String> queryFieldStr = new ArrayList<String>();
   String tableStr="";
   TreeSet<String> table = new TreeSet<String>();
   for (String str : queryField) {
		String[] split = str.split("_");
		String sqlcodeType="select code_type from code_table_col where table_code='"+split[0]+"' and col_code='"+split[1]+"'";
		String codeType = (String) sess.createSQLQuery(sqlcodeType).uniqueResult();
		String qq="";
		if(!StringUtils.isEmpty(codeType) && rad != null && (rad.equals("yes") || rad.equals("undefined"))){
			qq="CONCAT((select code_name from code_value where code_type='"+codeType+"' and code_value="+split[0]+"."+split[1]+"),'&&&"+str+"')";
		}else{
			qq="CONCAT("+split[0]+"."+split[1]+",'&&&"+str+"')";
		}
		queryFieldStr.add(qq);
		tableStr+=split[0]+",";
	}
   
   String[] arr = new String[table.size()];
   for(int i=0;i<arr.length;i++){
   	arr[i]=table.pollFirst();
   }
   for(int i=0;i<arr.length;i++){
   	tableStr+=arr[i]+",";
   }
   tableStr=tableStr.substring(0, tableStr.length()-1);
   
   //queryFieldStr=queryFieldStr.substring(0, queryFieldStr.length()-1);
   System.out.println(queryFieldStr);
   System.out.println(tableStr);
  // String a0000And="";
   String[] tableStrs = tableStr.split(",");
   
   String[] queryTableArr = tableStr.split(",");
	Object[] queryFiledArr = queryFieldStr.toArray();
	//����map�����Ͷ�Ӧ��Ҫ��ѯ���ֶ�
	HashMap<String, String> tableAndFields = new HashMap<String, String>();
	for(int i=0;i<queryTableArr.length;i++){
		String ff="";
		for(int j=0;j<queryFiledArr.length;j++){
			String field=(String) queryFiledArr[j];
			String[] split = field.split("&&&");
			String replace = split[split.length-1];
			if(replace.split("_")[0].equals(queryTableArr[i])){
				ff+=field+",";
				
			}
		}
		
		ff=ff.substring(0, ff.length()-1);
		tableAndFields.put(queryTableArr[i], ff);
	}
   
   
   
   
   //�����µ�excel���ڵ���
   HSSFWorkbook newwork = new HSSFWorkbook();
   HSSFSheet sheet1 = newwork.createSheet("sheet1");
   Integer count=0;
  //ѭ��map
   for (Map.Entry<String, String> entry : map.entrySet()) {
	   String dwid = entry.getKey();//�˻�����id
	   String a0000s = entry.getValue();//�˻������е���Աid
	   String[] split1 = a0000s.split(",");
	 //����list������������
       ArrayList<Object[]> listAll = new ArrayList<Object[]>();
       for(int i=0;i<split1.length;i++){
    	   ArrayList<Object> arrayList = new ArrayList<Object>();
       		for(Map.Entry<String, String> entry2 : tableAndFields.entrySet()){
       		String key = entry2.getKey();
       		String value = entry2.getValue();
       		String sql="select "+value+" from "+key+" where a0000='"+split1[i]+"'";
       		System.out.println(sql);
       		List<Object[]> list = sess.createSQLQuery(sql).list();
       		if(list != null){
       			if(list.size()>1){
       				String allData="";
               		for (int j=0;j<list.size();j++) {
               			
               			Object object = list.get(j);
               			Object[] objects = null;
               			if(!(object instanceof Object[])){
               				objects = new Object[1];
               				objects[0]=object;
               			}else{
               				Object[] objectaa = list.get(j);
               				objects=objectaa;
               			}
               			
               			for(int x=0;x<objects.length;x++){
               				allData+=x+"@"+(String)objects[x]+"!!";
               			}
               			
   					}
               		allData=allData.substring(0, allData.length()-2);
               		String[] split = allData.split("!!");
               		TreeSet<String> treeSet2 = new  TreeSet<String>();
               		for (String string : split) {
   						String[] split2 = string.split("@");
   						treeSet2.add(split2[0]);
   					}
               		
               		String[] s=new String[treeSet2.size()];
               		for (int j=0;j<s.length;j++) {
   						s[j]=treeSet2.pollFirst();
   					}
               		HashMap<String, String> dataMap = new HashMap<String, String>();
               		for (String string : s) {
               			String data="";
               			String bb="";
               			for (String str : split) {
               				String[] split2 = str.split("@");
               				if(str.contains(string+"@")){
               					data+=split2[1].split("&&&")[0]+"\r\n";
               					bb=split2[1].split("&&&")[1];
               				}
               			}
               			//System.out.println(data);
               			//System.out.println("\r\n".length());
               			data=data.substring(0, data.length()-2);
               			
               			data+="&&&"+bb;
               			dataMap.put(string, data);
   					}
               		int max=0;
               		for (String string : s) {
               			if(max<Integer.parseInt(string)){
               				max=Integer.parseInt(string);
               			}
               		}
               		Object[] obja= new Object[max+1];
               		for(Map.Entry<String, String> entry1 : dataMap.entrySet()){
               			obja[Integer.parseInt(entry1.getKey())]=entry1.getValue();
               		}
               		
               		//ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
               		//arrayList.add(obja);
               		for (Object object : obja) {
               			arrayList.add(object);
   					}
       			}else if(list.size()==1){
        			
       				Object object = list.get(0);
        			
        			Object[] objects = null;
        			if(!(object instanceof Object[])){
        				objects = new Object[1];
        				objects[0]=object;
        			}else{
        				Object[] objectaa = list.get(0);
        				objects=objectaa;
        			}
        			for (Object obj : objects) {
        				arrayList.add(obj);
					}
        		}
       			
       		}

       	}
       	Object[] array = arrayList.toArray();
       	ArrayList<Object[]> list = new ArrayList<Object[]>();
       	list.add(array);
       	listAll.addAll(list);
     }
       
  
     
       HSSFRow row0 = sheet1.createRow(count);
       HSSFRow row1 = sheet1.createRow(count+2);
       
       //��ӻ�������
       HSSFCell createCell2 = row0.createCell(0);
       //��ѯ��������
       String uniqueResult = (String) sess.createSQLQuery("select B0101 from b01 where b0111='"+dwid+"'").uniqueResult();
       createCell2.setCellValue(uniqueResult);
       
       //���ñ�ͷ��ʽ
       HSSFCellStyle setBorder = newwork.createCellStyle();
       
       row0.setHeight(sheet.getRow(0).getHeight());
      
       	//�������ñ߿�:
       //setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //�±߿�
       //setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//��߿�
       //setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//�ϱ߿�
       //setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//�ұ߿�
       //�������þ���:
       setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����
    //   �ġ���������:
       HSSFFont font = newwork.createFont();
       font.setFontName("����");
       font.setFontHeightInPoints((short) 20);//���������С 
       setBorder.setFont(font);//ѡ����Ҫ�õ��������ʽ
    //   �塢�����п�:
       sheet.setColumnWidth(0, 3766); //��һ������������id(��0��ʼ),��2������������ֵ
      // ���������Զ�����:
       setBorder.setWrapText(true);//�����Զ�����
       HSSFCellStyle cellStyle = newwork.createCellStyle();
       cellStyle.cloneStyleFrom(sheet.getRow(0).getCell(0).getCellStyle());
         createCell2.setCellStyle(cellStyle);
    
        
       
       //�ϲ���Ԫ��
       CellRangeAddress cra =new CellRangeAddress(count, count, 0, headLine.size()); // ��ʼ��, ��ֹ��, ��ʼ��, ��ֹ��  
       CellRangeAddress cra1 =new CellRangeAddress(count+1, count+1, 0, headLine.size()); // ��ʼ��, ��ֹ��, ��ʼ��, ��ֹ��  
       sheet1.addMergedRegion(cra);  
       sheet1.addMergedRegion(cra1);
       
      //MergerRegion(sheet, sheet1);
       
       //��ͷ��ʽ
      /* HSSFCellStyle headstyle = newwork.createCellStyle();
       HSSFFont font2 = newwork.createFont();    
       font2.setFontName("����");    
       //font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ    
       font2.setFontHeightInPoints((short)10);  //�����С  
           
       headstyle.setFont(font2);//ѡ����Ҫ�õ��������ʽ
       headstyle.setWrapText(true);//�����Զ�����
*/           
       //��ӱ�ͷ����
       for (Map.Entry<String, String> entry1 : headLine.entrySet()) {
    	   HSSFCellStyle hssfCellStyle = headLineStyle.get(entry1.getKey());
           //System.out.println(entry.getKey() + ":" + entry.getValue());
       		HSSFCell createCell = row1.createCell(Integer.parseInt(entry1.getKey()));
       		createCell.setCellValue(entry1.getValue());
       		
       		HSSFCellStyle style = newwork.createCellStyle();
       		style.cloneStyleFrom(hssfCellStyle);
       		createCell.setCellStyle(style);
      
            	
       }
       
      row1.setHeight(sheet.getRow(2).getHeight());
      
      //����������ʽ
     /* HSSFCellStyle datastyle = newwork.createCellStyle();
      HSSFFont font3 = newwork.createFont();    
      font3.setFontName("����");    
      //font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//������ʾ    
      font3.setFontHeightInPoints((short) 10);  //�����С  
      datastyle.setFont(font3);
      datastyle.setWrapText(true);//�����Զ�����   
*/           
     //�������
       for (int i=0;i<listAll.size();i++) {
       	HSSFRow createRow = sheet1.createRow(i+count+3);
       	Object[] obj = listAll.get(i);
       	int maxHeight=0;
       	for (int j=0;j<obj.length;j++) {
       		HSSFCell createCell =null;
       		String objv=(String) obj[j];
       		for (Map.Entry<String, String> entry2 : twoRowMap.entrySet()) {
       			HSSFCellStyle hssfCellStyle = twoRowMapStyle.get(entry2.getKey());
       			String value = entry2.getValue();
       			if(value.matches("[A-Z]+[\\d]+[\\&\\+\\-\\*\\/][A-Z]+[\\d]+")){
       				createCell = createRow.createCell(Integer.parseInt(entry2.getKey()));
       				HSSFCellStyle style = newwork.createCellStyle();
               		style.cloneStyleFrom(hssfCellStyle);
               		createCell.setCellFormula(value);
               		createCell.setCellStyle(style);
       			}else{
       				if(objv.split("&&&")[1].equals(entry2.getValue())){
           				createCell = createRow.createCell(Integer.parseInt(entry2.getKey()));
           				HSSFCellStyle style = newwork.createCellStyle();
                   		style.cloneStyleFrom(hssfCellStyle);
           				createCell.setCellValue(new HSSFRichTextString(objv.split("&&&")[0]+" "));
           				style.setWrapText(true);
           				createCell.setCellStyle(style);
           				if(maxHeight<objv.split("&&&")[0].split("\r\n").length){
               				maxHeight=objv.split("&&&")[0].split("\r\n").length;
               			}
           			}
       			}
       			
       		}

			}
       		
           	short firstCellNum = createRow.getFirstCellNum();
			short lastCellNum = createRow.getLastCellNum();
			for(int d=firstCellNum;d<=lastCellNum;d++){
				sheet1.autoSizeColumn(d);
			}
       		
       		createRow.setHeight(sheet.getRow(3).getHeight());
       		
       	
		}
       count=count+listAll.size()+3;

   }
   
   
   String jbmc = request.getSession().getServletContext().getRealPath("webOffice/word").replace("\\webOffice\\word", "")+"\\ziploud/"+UUID.randomUUID().toString().replace("-", "")+"/"+"expFiles_"+System.currentTimeMillis()+"/";
   File file2 = new File(jbmc);
   if(!file2.exists()){
   	file2.mkdirs();
   }
   
   newwork.write(new File(jbmc+"��λ����.xls"));  
   
   newwork.close();//���ǵùرչ�����  
   
   //String infile=jbmc.substring(0, jbmc.length()-1)+".zip"
   String infile=jbmc+"��λ����.xls";
   //SevenZipUtil.zip7z(jbmc, jbmc.substring(0, jbmc.length()-1)+".zip", null);
   if("xz".equals(bs)&&bs!=null){
	   this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
	   this.getExecuteSG().addExecuteCode("window.reloadTree()");
   }else{
	   for (int p = 1; p <= 1; p++) {
		   this.getExecuteSG().addExecuteCode("printStart1('"+infile.substring(infile.indexOf("ziploud")).replace("\\", "\\\\")+"');");
	   }
	   this.setMainMessage("��ӡ���");
   }
   
	
	return EventRtnType.NORMAL_SUCCESS;	
}
@PageEvent("expExcel")
public int expExcel(String namebs) throws Exception{
	
	String[] nbs= namebs.split(",");
	 String name = nbs[0];
	 String bs = nbs[1];
	 String rad = nbs[2];
	 
	//��ӡ����ѡ���ж�
  /* HttpSession session = request.getSession();
   String printer= (String) session.getAttribute("Printer");
   if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
   	this.setMainMessage("�����ô�ӡ������");
   	return EventRtnType.NORMAL_SUCCESS;
   }
   int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
   if(printer.equals("")) {
   	this.setMainMessage("����ȥ��ӡ��������ѡ��һ�ִ�ӡ����");
   	return EventRtnType.NORMAL_SUCCESS;
   }
   if(printNum<=0) {
   	this.setMainMessage("��ӡ�����������0���Ҳ���Ϊ�գ�");
   	return EventRtnType.NORMAL_SUCCESS;
   }*/
	//����ѡ����Աid
	HBSession sess = HBUtil.getHBSession();
	String tpid = "";
	String ids = "";
	String idss = (String)request.getSession().getAttribute("personidy");
	if(idss != null && !"".equals(idss)){
		//��ѡ��Աid
		ids = idss;
	}else{
		//δ��ѡ��Ա��ȫѡ
		ids = (String)request.getSession().getAttribute("personidall");
	}
	int length = ids.split("@").length;
	List<String> result = new ArrayList<String>();
	if (length >200) {
		this.setMainMessage("��ѡ��200�˽��д���!");
		return EventRtnType.NORMAL_SUCCESS;	
	}else{
		String[] personids = ids.split("@");
		List<String> list2 = new ArrayList<String>();
		for(int j = 0; j < personids.length; j++){
			String a0000 = personids[j].replace("|", "");
			list2.add(a0000);
		}
		
		result.addAll(list2);
		
	}
	
	//��ȡexcel
	//����Hssf��ȡexcelģ������
	String exportPath=request.getSession().getServletContext().getRealPath("webOffice/word");
	String doctpl = request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+ name;//·����ģ����
	String infile = JbmcExp.expRmb(name, result, AppConfig.HZB_PATH+"\\WebOffice", null,rad,exportPath);
	if("û��ģ��".equals(infile)){
		this.setMainMessage("û�ж�Ӧ��ģ��");
		return EventRtnType.NORMAL_SUCCESS;
	}
//    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
//	this.getExecuteSG().addExecuteCode("window.reloadTree()");
	if("xz".equals(bs)&&bs!=null){
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
	}else{
		String printPath=infile.substring(0, infile.length()-4)+"/";
		File f = new File(printPath);
	    if (!f.exists()) {
	      this.setMainMessage("�ļ������ڣ�");
	      return EventRtnType.NORMAL_SUCCESS;
	    }
	    File fa[] = f.listFiles();
	    for (int p = 1; p <= 1; p++) {
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isDirectory()) {
					System.out.println(fs.getName() + " [Ŀ¼]");
				} else {
					//printFile(wordPaths.get(0) + fs.getName(), printer);
					//String path = wordPaths.get(0) + fs.getName();//D:\apache-tomcat-6.0.43\webapps\hzb_qd2018\ziploud\f3f5941bcb1848babd2a3763ad66c292\expFiles_1530269619433\�Ϻ�ΰ1.xls
					String rootPath = PhotosUtil.getRootPath();
					//path = path.replace(rootPath, "").replace("\\", "/");
					this.getExecuteSG().addExecuteCode("printStart1('"+fs.toString().substring(fs.toString().indexOf("ziploud")).replace("\\", "\\\\")+"');");
				}
			}
		}
	    this.setMainMessage("��ӡ���");
	}
    
	return EventRtnType.NORMAL_SUCCESS;
}

// Clob���� תString  
public String ClobToString(Clob clob) throws SQLException, IOException {  
  String reString = "";  
  Reader is = clob.getCharacterStream();// �õ���  
  BufferedReader br = new BufferedReader(is);  
  String s = br.readLine();  
  StringBuffer sb = new StringBuffer();  
  while (s != null) {// ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING  
      sb.append(s+"\n");
      s = br.readLine();
  }
  reString = sb.toString();
  if(br!=null){
      br.close();
  }
  if(is!=null){
      is.close();
  }
  return reString;
 }  

public static void MergerRegion(HSSFSheet sheetCreat, HSSFSheet sheet) {
	 int sheetMergerCount = sheet.getNumMergedRegions();
	 for (int i = 0; i < sheetMergerCount; i++) {
		CellRangeAddress mergedRegion = sheet.getMergedRegion(i);
		sheetCreat.addMergedRegion(mergedRegion);
	 }
}

	
	
	
	public static void main(String[] args) throws Exception {
	}
	
	
	
	/* ������ */
	
	/**
	 * �ɲ������
	 * @param a0000s
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws SQLException 
	 */
	@PageEvent("cadresTTFAudit")
	public int cadresTTFAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
		//��Ա����
		String[] a0000_array = a0000s.split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);
		
		HBSession session = HBUtil.getHBSession();
		
		//У����Ա�Ƿ��ѱ����
		StringBuffer sb = new StringBuffer();
		List<A01> a01_list = new ArrayList<A01>();
		A01 temp;
		for (String a0000 : a0000_list) {
			temp = (A01) session.get(A01.class, a0000);
			a01_list.add(temp);
		}
		for (A01 a01 : a01_list) {
			if((Integer.valueOf(a01.getA0190()==null?"0":a01.getA0190()) & Integer.valueOf(a01.getA0189()==null?"0":a01.getA0189())) == 1){
				sb.append(a01.getA0101() + ",");
			}
		}
		
		if(sb.length()>0){
			this.setMainMessage(sb.substring(0, sb.length()-1)+"�������!");
			return EventRtnType.FAILD;
		} else{ 
			//��¼��־
			A01 a01_new;
			A01 a01_temp;
			for (A01 a01 : a01_list) {
				a01_new = new A01();
				a01_temp = new A01();
				a01_temp.setA0190(a01.getA0190());
				a01_new.setA0190("1");
				applog.createLog("3801", "A01", a01.getA0000(), a01.getA0101(), "�ɲ������", new Map2Temp().getLogInfo(a01_temp, a01_new));
			}
			
			//��ѡ��Աû����ˣ������Ϊ�ɲ�������
			String sql;
			if(a0000_list.size()>=1000){
				sql = "update A01 set A0190='1' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
				List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
				List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a00001", a0000_list_1);
				query.setParameterList("a00002", a0000_list_2);
				query.setParameterList("a00003", a0000_list_3);
				query.executeUpdate();
			} else {
				sql = "update A01 set A0190='1' where A0000 in (:a0000)";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a0000", a0000_list);
				query.executeUpdate();
			}
		}
		
		this.setMainMessage("��˳ɹ�!");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ɲ�һ�����
	 * @param a0000s
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws SQLException 
	 */
	@PageEvent("cadresOAudit")
	public int cadresOAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
		//��Ա����
		String[] a0000_array = a0000s.split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);
		
		HBSession session = HBUtil.getHBSession();
		
		//У����Ա�Ƿ��ѱ����
		StringBuffer sb = new StringBuffer();
		List<A01> a01_list = new ArrayList<A01>();
		A01 temp;
		for (String a0000 : a0000_list) {
			temp = (A01) session.get(A01.class, a0000);
			a01_list.add(temp);
		}
		for (A01 a01 : a01_list) {
			if((Integer.valueOf(a01.getA0190()==null?"0":a01.getA0190()) & Integer.valueOf(a01.getA0189()==null?"0":a01.getA0189())) == 1){
				sb.append(a01.getA0101() + ",");
			}
		}
		
		if(sb.length()>0){
			this.setMainMessage(sb.substring(0, sb.length()-1)+"�������!");
			return EventRtnType.FAILD;
		} else{ 
			//��¼��־
			A01 a01_new;
			for (A01 a01 : a01_list) {
				a01_new = new A01();
				a01_new.setA0189("1");
				applog.createLog("3802", "A01", a01.getA0000(), a01.getA0101(), "�ɲ�һ�����", new Map2Temp().getLogInfo(a01, a01_new));
			}
			
			//��ѡ��Աû����ˣ������Ϊ�ɲ�������
			String sql;
			if(a0000_list.size()>=1000){
				sql = "update A01 set A0189='1' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
				List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
				List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
				List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a00001", a0000_list_1);
				query.setParameterList("a00002", a0000_list_2);
				query.setParameterList("a00003", a0000_list_3);
				query.executeUpdate();
			} else {
				sql = "update A01 set A0189='1' where A0000 in (:a0000)";
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameterList("a0000", a0000_list);
				query.executeUpdate();
			}
		}
		
		this.setMainMessage("��˳ɹ�!");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ɲ�һ������
	 * @param a0000s
	 * @return
	 * @throws AppException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IntrospectionException 
	 * @throws SQLException 
	 */
	@PageEvent("unLockAudit")
	public int unLockAudit(String a0000s) throws SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException, AppException{
		String flag=a0000s.split(",")[0];//1�ɲ���ȡ����ˣ�2�ɲ�һ��ȡ����ˣ�allȡ���������
		
		String[] a0000_array = a0000s.split(",")[1].split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);
		
		HBSession session = HBUtil.getHBSession();
		
		//��ȡ��ѡ��Ա
		List<A01> a01_list = new ArrayList<A01>();
		A01 temp;
		for (String a0000 : a0000_list) {
			temp = (A01) session.get(A01.class, a0000);
			a01_list.add(temp);
		}
		
		//��¼��־
		A01 a01_new;
		for (A01 a01 : a01_list) {
			a01_new = new A01();
			String logtext="";
			if("1".equals(flag)){// A0190�ɲ���
				a01_new.setA0190("0");
				logtext="�ɲ����������";
			}else if("2".equals(flag)){
				a01_new.setA0189("0");
				logtext="�ɲ�һ���������";
			}else{
				a01_new.setA0189("0");
				a01_new.setA0190("0");
				logtext="�ɲ������ɲ�һ���������";
			}
			applog.createLog("3803", "A01", a01.getA0000(), a01.getA0101(), logtext, new Map2Temp().getLogInfo(a01, a01_new));
		}
		
		
		String sql;
		if(a0000_list.size()>=1000){
			if("1".equals(flag)){// A0190�ɲ���
				sql = "update A01 set A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
			}else if("2".equals(flag)){
				sql = "update A01 set A0189='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
			}else{
				sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
			}
			//sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a00001) or A0000 in (:a00002) or A0000 in (:a00003)";
			List<String> a0000_list_1 = DBUtils.averageAssign(a0000_list, 3).get(0);
			List<String> a0000_list_2 = DBUtils.averageAssign(a0000_list, 3).get(1);
			List<String> a0000_list_3 = DBUtils.averageAssign(a0000_list, 3).get(2);
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameterList("a00001", a0000_list_1);
			query.setParameterList("a00002", a0000_list_2);
			query.setParameterList("a00003", a0000_list_3);
			query.executeUpdate();
		} else {
			if("1".equals(flag)){// A0190�ɲ���
				sql = "update A01 set A0190='0' where A0000 in (:a0000)";
			}else if("2".equals(flag)){
				sql = "update A01 set A0189='0' where A0000 in (:a0000)";
			}else{
				sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a0000)";
			}
			//sql = "update A01 set A0189='0',A0190='0' where A0000 in (:a0000)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameterList("a0000", a0000_list);
			query.executeUpdate();
		}
		
		this.setMainMessage("ȡ���ɹ�!");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("impHistoryExcel")
	public void impHistoryExcel(){
		HBSession sess = HBUtil.getHBSession();
		
		String sql="select a0101,a0184,photoname,photopath from (select A0000,A0184,A0101 from A01 where A0000 not in("
				+ "select A0000 from A57_HISTORY group by A0000) and a0163='1') t left join  A57 on t.A0000=A57.A0000 ";
		//List<ExcelA57Pojo> list =sess.createSQLQuery("SELECT a0184,a0101,photoname,photopath FROM A01 LEFT JOIN A57 ON A01.A0000=A57.A0000").setResultTransformer(Transformers.aliasToBean(ExcelA57Pojo.class)).list();
		List<ExcelA57Pojo> list =sess.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(ExcelA57Pojo.class)).list();
		if(list.size()>0){
			boolean bak=new QCJSFileExportBS().impHistoryExcel(list);
			if(bak){
				this.setMainMessage("�����ɹ�");
			}else{
				this.setMainMessage("����ʧ��");
			}
		}
	
	}
	
}