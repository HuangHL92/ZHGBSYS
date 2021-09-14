package com.insigma.siis.local.pagemodel.customquery;
import java.beans.IntrospectionException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.alibaba.fastjson.JSON;
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
import com.insigma.siis.local.pagemodel.customquery.util.ExpExcelPList;
import com.insigma.siis.local.pagemodel.dataverify.DataPsnImpThread;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.publicServantManage.betchModifyPageModel;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
import com.insigma.siis.local.pagemodel.xbrm.QCJSFileExportBS;
import com.insigma.siis.local.pagemodel.xbrm.pojo.ExcelA57Pojo;
import com.picCut.servlet.SaveLrmFile;
import com.tests.Tests;
import com.utils.DBUtils;

import net.sf.json.JSONObject;
/**
 * @author lixy
 *
 */
public class CustomQueryPageModel extends PageModel {

	//获得日志对象
	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	private LogUtil applog = new LogUtil();

	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String queryType="0";//1点击机构树查询2点击按钮查询
	public static String tag="0";//0未执行1执行
	public static String LISTADDCCQLI="-1";
	public static boolean QUERYLISTFLAG=false;
	public static String LISTADDNAME="无";
	private final static int ON_ONE_CHOOSE=-1;
	private final static int CHOOSE_OVER_TOW=-2;
	public static String getcommSQL(String type){
		return "select  a01.a0000, a0101, a0104, GET_BIRTHDAY(a01.a0107,'"+DateUtil.getcurdate()+"') age, a0117, a0141, a0192a, a0148,A0160,A0192D,A0120,QRZXL,ZZXL " +
		",a0107,a0140,a0134,a0165,a0121,a0184,orgid,status from A01 a01 ";
	}
	public static String getcommSQL(){
		return getcommSQL("0");
	}
	public CustomQueryPageModel(){
		try {
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String cueUserid = user.getId();
			String loginnname=user.getLoginname();
			List<GroupVO> groups = PrivilegeManager.getInstance().getIGroupControl().findGroupByUserId(cueUserid);
			UserVO vo =PrivilegeManager.getInstance().getIUserControl().findUserByUserId(cueUserid);
			boolean issupermanager=new DefaultPermission().isSuperManager(vo);


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

		PageElement pe = this.getPageElement("peopleInfoGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String a0000 = "";

		for (HashMap<String, Object> hm : list) {

			if(!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listString=listString+"@|"+hm.get("a0000")+"|";
				++cnt;

				if(cnt==1){
					a0000 = hm.get("a0000").toString();

				}
			}

		}
		if (!"".equals(listString) && listString != null)
			listString = listString.substring(listString.indexOf("@") + 1);
 		this.getPageElement("checkList").setValue(listString);

 		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		/*String listString = this.getPageElement("checkList").getValue();
		int sign = 0;
		if("".equals(listString)){
			sign = 1;//说明是首次修改
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

 		//old start支持多选生成和导出
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

		String sql = "SELECT a0201A a0201a,a0201B a0201b,CASE A0201D WHEN '1' THEN '是' WHEN '0' THEN '否' END a0201d,a0201E a0201e,a0215A a0215a,a0219,"
				+ "a0221,a0222,a0223,a0225,a0229,a0243,a0245,a0247,a0251,"
				+ "CASE A0251B WHEN '1' THEN '是' WHEN '0' THEN '否' END a0251b,a0255,a0265,a0267,a0272,a0281 "
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
	
/*	@PageEvent("tableList")
	public int tableList() throws RadowException {
		int start=1;
		int limit=10;
		String sql = "select  a01.A0000 as a0000,(case  when length(a0101)=2 then (Substr(a0101,0,1))||'　'||(Substr(a0101,2,2))else a0101 end) as a0101,A0192A as a0192a,A0104 as a0104,A0117 as a0117,qrzxl as qrzxl,QRZXLXX as qrzxlxx,zzxl as zzxl,ZZXLXX as zzxlxx,A0107 as a0107,A0140 as a0140,A0134 as a0134,A0192F as a0192f,A0192C as a0192c,A0288 as a0288  from A01 a01 ";
		this.pageQuery(sql, "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}*/
	

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

		String sql = "SELECT a6001,a6002,a6003,a6004,a6005,a6006,a6007,a6008,a6009,CASE a6010 WHEN '1' THEN '是' WHEN '0' THEN '否' END,"
				+ "CASE a6011 WHEN '1' THEN '是' WHEN '0' THEN '否' END,CASE a6012 WHEN '1' THEN '是' WHEN '0' THEN '否' END,"
				+ "CASE a6013 WHEN '1' THEN '是' WHEN '0' THEN '否' END,CASE a6014 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6015,"
				+ "CASE a6016 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6017 FROM A60 WHERE A0000 = '";
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
				+ "CASE a6112 WHEN '1' THEN '是' WHEN '0' THEN '否' END,CASE a6113 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6114,"
				+ "CASE a6115 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6116 FROM A61 WHERE A0000 = '";
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
				+ "CASE a6307 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6308,"
				+ "CASE a6309 WHEN '1' THEN '是' WHEN '0' THEN '否' END,a6310 FROM A63 WHERE A0000 = '";
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
	 * 自定义查询，加载列表属性
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
//				this.setMainMessage("操作失败："+e.getDetailMessage());
//				e.printStackTrace();
//				return EventRtnType.FAILD;
//			}
//		}
		String qvid=this.request.getParameter("qvid");
		String viewnametb=HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='"+qvid+"'");
		String sessionid=this.request.getSession().getId();
		HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='"+sessionid+"' ");
		//删除A01SEARCHTEMP
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
		this.request.getSession().setAttribute("queryType", "define");//设置自定义查询列表标志
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
	public int persongridOnRowClick() throws RadowException, AppException{  //打开窗口的实例
		int index = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		this.getExecuteSG().addExecuteCode("getCheckList3('"+index+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}*/

	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("peopleInfoGrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

		String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			/*try {
				CallableStatement x = HBUtil.getHBSession().connection().prepareCall("{call GBDJDSYS.PERSON_XF_INFO(?)}");
				x.setString(1, ac01.getA0000());
		        x.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
			this.request.getSession().setAttribute("personIdSet", null);
			String rmbs=this.getPageElement("rmbs").getValue();
			if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			String checkedgroupid = this.getPageElement("checkedgroupid").getValue();
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			//this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-150)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');");
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"&&checkedgroupid="+checkedgroupid+"', '_blank', 'height='+(screen.height-90)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
//			this.getExecuteSG().addExecuteCode("openZHGBRMB('"+a0000+"')");
			//initA0000Map(a0000);
			//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			//this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
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
		//机构  浏览  编辑  标记
		String lookOrWrite = this.getPageElement("lookOrWrite").getValue();
		//获取编辑或者浏览的权限表
		String deptTable = getdeptTableName(lookOrWrite,id);

		//是否包含下级
		String isContain = this.getPageElement("isContain").getValue();
		if("1".equals(isContain))
		{
			HBSession session = HBUtil.getHBSession();
			//如果包含下级则查询该机构是否有下级机构，如果没有下级机构，逻辑上查询应该走该单位内人员排序
			Object count = (Object)session.createSQLQuery("select count(1) a from b01 b where b.b0111 like '"+id+"%'").uniqueResult();
			int totalcount=0;
			if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}else if(count instanceof Long){
	  			totalcount = ((Long)count).intValue();
	  		}
			if(totalcount==1)
			{
				isContain="0";
				this.getPageElement("isContain2").setValue("0");
			}else {
				this.getPageElement("isContain2").setValue("1");
			}


		}
		this.getPageElement("tabn").setValue("tab1");
		String a0201bsql = "";
		//String a01Orgidsql = "";
		String is_use_a0000_idx_sql = "";
		int idLength = id.length();
		if("1".equals(isContain)){//是否包含下级
			this.request.getSession().setAttribute("isContainCQ", true);
			//CommSQL.getComFields();
			a0201bsql = CommSQL.subString("a02.a0201b", 1, idLength,id);
			//a01Orgidsql = CommSQL.subString("cu.b0111", 1, idLength,id);
			String[] jgid = id.split("\\.");
			//yinl a02关联条件增加分区条件 2017.08.02
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
			//yinl a02关联条件增加分区条件 2017.08.02
			if(DBUtil.getDBType()==DBType.ORACLE){
				a0201bsql = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?a0201bsql+" and a02.V0201B = '"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":a0201bsql+" ";
			}
			is_use_a0000_idx_sql = "a01.a0000";
		}
		/*
		 * String radioC = this.getPageElement("radioC").getValue(); String hasQueried =
		 * this.getPageElement("sql").getValue(); if(!"1".equals(radioC)){
		 * if("".equals(hasQueried) || hasQueried==null) throw new
		 * AppException("未进行过查询请先查询!"); }
		 */
		String xzry = this.getPageElement("xzry").getValue();		//现职人员
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
		this.request.getSession().setAttribute("queryConditionsMLLB", mllb);
		String sql=CommSQL.getComSQL(sid)+" where 1=1  ";

		String a0163sql = "";//人员状态
		String isDisplay = "and a0281='true'";
		String a0163 = this.getPageElement("personq").getValue();
		System.out.println("测试有没有值："+a0163);
		if("2".equals(a0163)){
			a0163sql = " and a0163 like '2%' ";
		}else if("1".equals(a0163)){
			a0163sql = " and a0163 like '1%' ";
		}else if("3".equals(a0163)){

		}else if("4".equals(a0163)){
			/*isDisplay = (String) HBUtil.getHBSession().createSQLQuery("select AAA005 from AA01 where AAA001='ISDISPLAY'").uniqueResult();
			System.out.println("ISDISPLAY=" +isDisplay);
			if(isDisplay.equals("1")) //222.189服务器
			isDisplay="";
			else isDisplay="and a0281='true'";*/
		}else if("".equals(a0163)|| a0163==null){

		}else{
			a0163sql = " and a0163 = '"+a0163+"'";
		}


		//1现职人员 2离退人员 3调出人员 4已去世 5其他人员       其它现职人员  历史人员（删除）  离退人员 职务为空的其它现职人员
		if("X001".equals(id)){//其它现职人员
			sql=sql+ " and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and (a01.status!='4' or a01.status is  null) " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");

		}else if("X002".equals(id)){

			//主职务已免 或者 没有主职务
			sql=sql+ " and (not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0279 = '1') or A0000  in  (select A02.A0000  from  A02  where  A02.A0279 = '1' and A02.A0255 = '0' ))  "
					+ " and (a01.status!='4' or a01.status is  null) " + a0163sql+ (!"".equals(mllb) ? "and a01.a0165 = '"+mllb+"'" : "");
		}

		else{

			sql = sql + " and a01.status!='4' " + (!"".equals(mllb) ? "  and a01.a0165 = '" + mllb + "'" : "") + a0163sql
					+ " and " + is_use_a0000_idx_sql + "  in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from " + deptTable + " cu where cu.userid = '" + userID + "') " + isDisplay + ""
					+ " and " + a0201bsql + ")";

		}

		CommonQueryBS.systemOut("++++++++++++" + sql);
		this.getPageElement("sql").setValue(sql);
		this.getPageElement("a0201b").setValue(id);//双击后，选中的，机构id
		//this.getExecuteSG().addExecuteCode("showgrid()");			//重新点击树，切换到列表页面，将小资料和照片隐藏

		// this.getExecuteSG().addExecuteCode("refreshPerson()");

		String shsql = "select A01.A0000,A0190,A0189 from A01 right join (" + sql + ")m on A01.A0000=m.A0000";
		String str11 = setShenHeValue(shsql);

		this.getPageElement("shinfo").setValue(str11);


        this.getExecuteSG().addExecuteCode("document.getElementById('orgjson').value=''");
		this.setNextEventName("peopleInfoGrid.dogridquery");

		String spsql = "select b0241 from b01 where b0111='"+id+"'";
		//HBSession hs = HBUtil.getHBSession();
		CommQuery query = new CommQuery();
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
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 根据不同的标记获取用户相应的应查询的权限表
	 * @param lookOrWrite 编辑或浏览标识
	 * @param id 用户ID
	 * @param deptTable
	 * @return
	 */
	private String getdeptTableName(String lookOrWrite, String id) {
		String deptTable;
		//根据不同的标记获取用户相应的应查询的权限表
		if("look".equals(lookOrWrite)&&!"40288103556cc97701556d629135000f".equals(id)){
					deptTable = "competence_userdept";
		}else{
					deptTable = "competence_userdept";
				}
		return deptTable;
	}
	/**
	 * 机构界面不能删除机构，查看人员
	 * @return
	 */
	@PageEvent("delete_per_search")
	public int delete_per_search(String unit_id){
		if(unit_id==null||unit_id.equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}

		return EventRtnType.NORMAL_SUCCESS;
	}

	//点击树查询事件
	@PageEvent("setA0281.onclick")
	@NoRequiredValidate
	public int setA0281(String id) throws Exception {
		String checkedgroupid = this.getPageElement("checkedgroupid").getValue();
		if(StringUtils.isEmpty(checkedgroupid)){
			this.setMainMessage("请选择机构！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBUtil.executeUpdate("update a02 set a0281='false' where a0201b=? and a0255='0'",new Object[]{checkedgroupid});
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//点击树查询事件
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {
		//点击 干部管理 页面的机构，标记
		this.request.getSession().setAttribute("isGBGL", "1");

		this.getPageElement("checkedgroupid").setValue(id);

		this.request.getSession().setAttribute("queryType", "1");
		String isContain = this.getPageElement("isContain").getValue();

		String radioC=this.getPageElement("radioC").getValue();
		//当queryType = 1，isContain = 0，并且，设置标志isA0225 = 1
		if (isContain != null && isContain.equals("0") && radioC.equals("1")) {
			this.request.getSession().setAttribute("isA0225", "1");
		}

		//初始化自定义显示列的数据
		String userid = SysManagerUtils.getUserId();
		CommSQL.initA01_config(userid);

		this.getPageElement("isContainHidden").setValue(isContain);
		String userID = SysManagerUtils.getUserId();
		String codevalue = this.getPageElement("checkedgroupid").getValue();
		//====把机构id传到OtherTemShowTowPageModel 类里面 花名册用===============
		request.getSession().setAttribute("listofname", codevalue);
		//========================================================================
        //根据标签判断是否具有编辑或浏览权限
		String lookOrWrite = this.getPageElement("lookOrWrite").getValue();
		if ("look".equals(lookOrWrite)) {
			Map<String, String> map = PublicWindowPageModel.isHasRuleLook(codevalue, userID);
			if (!map.isEmpty() || map == null) {
				if ("2".equals(map.get("type"))) {
					this.setMainMessage("您没有该机构的浏览权限");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}else{
			Map<String, String> map = PublicWindowPageModel.isHasRule(codevalue, userID);
			if (!map.isEmpty()||map==null) {
				if ("2".equals(map.get("type"))){
					this.setMainMessage("您没有该机构的权限");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
	 //将根据页面条件编写sql并插入a01临时表中，用于页面sql左连接查询
	 querybyid(id);
	 return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * 打开列表页面
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("loadList.onclick")
	@NoRequiredValidate
	public int loadList()throws RadowException, AppException{
		//this.openWindow("win2", "pages.customquery.QueryList");
		this.getExecuteSG().addExecuteCode("$h.openWin('win2','pages.customquery.QueryList','人员组列表',560,500,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 打开保存页面
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveList.onclick")
	@NoRequiredValidate
	public int saveList()throws RadowException, AppException{

		String hasQueried = this.getPageElement("sql").getValue();
		if("".equals(hasQueried) || hasQueried==null){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要保存的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		//this.openWindow("win3", "pages.customquery.ListSaveWin");
		this.getExecuteSG().addExecuteCode("$h.openWin('win3','pages.customquery.ListSaveWin','保存列表',560,200,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 保存列表
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
	public int doSaveList()throws RadowException, AppException, UnsupportedEncodingException, SerialException, SQLException {

		String sql = "select a0000 from A01SEARCHTEMP where sessionid='" + this.request.getSession().getId() + "'";
		String cqli = this.getPageElement("cqli").getValue();
		String saveName = this.getPageElement("saveName").getValue();
		String loginName = SysUtil.getCacheCurrentUser().getLoginname();
		CustomQueryBS.saveList(saveName, "", "", loginName, sql, cqli);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * 保存：（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveCon.onclick")
    @Transaction
	public int saveCon() throws RadowException, AppException {
		StringBuffer sb = new StringBuffer();

		String b01String = this.getPageElement("SysOrgTreeIds").getValue();//机构信息
		b01String = b01String.replace("|", "'").replace("@", ",");
		//b01String=b01String.replace("{", "").replace("}", "");
		String a0101 = this.getPageElement("a0101A").getValue();//人员姓名
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//人员身份证
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String age = this.getPageElement("ageA").getValue();//起始年龄
		String age1 = this.getPageElement("age1").getValue();//结束年龄
		String female = this.getPageElement("female").getValue();//性别是否女
		if (female.equals("1")) {
				female = "2";
			}
			String minority = this.getPageElement("minority").getValue();//是否少数民族
			String nonparty = this.getPageElement("nonparty").getValue();//是否非中共党员
			String duty = this.getPageElement("duty").getValue();//起始职务层次
			String duty1 = this.getPageElement("duty1").getValue();//结束职务层次
			String dutynow = this.getPageElement("dutynow").getValue();//职务层次起始日期
			String dutynow1 = this.getPageElement("dutynow1").getValue();//职务层次结束日期
			String a0219 = this.getPageElement("a0219").getValue();//职务类别
			String a0221aS = this.getPageElement("a0221aS").getValue();//职务等级
			String a0221aE = this.getPageElement("a0221aE").getValue();//职务等级
			String a0192dS = this.getPageElement("a0192dS").getValue();//职务职级
			String a0192dE = this.getPageElement("a0192dE").getValue();//职务职级
			String edu = this.getPageElement("edu").getValue();//起始学历
			String edu1 = this.getPageElement("edu1").getValue();//结束学历

			String allday = this.getPageElement("allday").getValue();//是否全日制
			if(!"1".equals(allday)){
//				allday="2";
				allday="";  //根据需求改为，未勾选则不添加  学历是否全日制条件 （bug编号：708）
			}

			//范围校验 start mengl 20160630
			if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("年龄范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
				CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
				CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
				if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
					throw new AppException("职务层次范围不属于同一类别，请检查！");
				}
				//职务层次 值越小 字面意思越高级
				if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
					throw new AppException("职务层次范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
				if(dutynow.compareTo(dutynow1)>0){
					throw new AppException("任现职务层次时间范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
				CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
				CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);
				//TODO 职务等级范围是否校验 待定
				if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
					throw new AppException("职务等级范围不属于同一类别，请检查！");
				}
				//职务等级 值越小 字面意思越高级
				if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
					throw new AppException("职务等级范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
				CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
				CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
				if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
					throw new AppException("职级范围不属于同一类别，请检查！");
				}
				//职级 值越小 字面意思越高级
				if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
					throw new AppException("职级范围不正确，请检查！");
				}
			}

			if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
				CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
				CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
				/*if(!eduCodeValue.getSubCodeValue().equalsIgnoreCase(edu1CodeValue.getSubCodeValue())){
					throw new AppException("学历范围不属于同一类别，请检查！");
				}*/
				//职级 值越小 字面意思越高级
				if (eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue()) < 0) {
					throw new AppException("学历范围不正确，请检查！");
				}
			}


		//范围校验 end


		StringBuffer customData = new StringBuffer();//编辑器网格显示数据

//			sb.append("select  a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000 ");
		sb.append(getcommSQL() + " where 1=1 ");

		if (!a0101.equals("")) {
			sb.append(" and ");
			sb.append("a01.a0101 like '" + a0101 + "%'");
			customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'" + a0101 + "','logicSymbols':' and ','colValues':'" + a0101 + "','colNamesValue':'A0101','colNames':'人员姓名','leftBracket':'','rightBracket':''}");
		}
		if (!a0184.equals("")) {
				sb.append(" and ");
				sb.append("a01.a0101 like '"+a0184+"%'");
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'"+a0184+"','logicSymbols':' and ','colValues':'"+a0184+"','colNamesValue':'A0101','colNames':'人员姓名','leftBracket':'','rightBracket':''}");
			}
			if (!a0160.equals("")) {
				sb.append(" and ");
				sb.append("a01.a0160 ='" + a0160 + "'");
				String colValueView = CustomQueryBS.getAaa103("A0160", a0160, cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0160 + "','colNamesValue':'A0160','colNames':'人员类别','leftBracket':'','rightBracket':''}");
			}
			if (!a0163.equals("")) {
				sb.append(" and ");
				sb.append("a01.a0163='" + a0163 + "'");
				String colValueView = CustomQueryBS.getAaa103("A0163", a0163, cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0163 + "','colNamesValue':'A0163','colNames':'人员状态','leftBracket':'','rightBracket':''}");
			}
			if (!age.equals("")){
				sb.append(" and ");
				sb.append("a01.age>="+age);

				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'"+age+"','logicSymbols':' and ','colValues':'"+age+"','colNamesValue':'AGE','colNames':'年龄','leftBracket':'','rightBracket':''}");
			}
			if (!age1.equals("")){
				sb.append(" and ");
				sb.append("a01.age<="+age1);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'"+age1+"','logicSymbols':' and ','colValues':'"+age1+"','colNamesValue':'AGE','colNames':'年龄','leftBracket':'','rightBracket':''}");
			}
			if (!female.equals("0")) {
				sb.append(" and ");
				sb.append("a01.a0104='" + female + "'");
				String colValueView = CustomQueryBS.getAaa103("A0104", female, cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + female + "','colNamesValue':'A0104','colNames':'性别','leftBracket':'','rightBracket':''}");

			}
			if (!minority.equals("0")) {
				sb.append(" and ");
				sb.append("a01.a0117!='01'");
				String colValueView = CustomQueryBS.getAaa103("A0117", "01", cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'01','colNamesValue':'A0117','colNames':'民族','leftBracket':'','rightBracket':''}");
			}
			if (nonparty.equals("1")) {
				sb.append(" and ");
				sb.append("a01.a0141!='01'");
				String colValueView = CustomQueryBS.getAaa103("A0141", "01", cbBs.ctcList);
				customData.append(",{'opeartors':'!={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'01','colNamesValue':'A0141','colNames':'政治面貌','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(duty1)) {
				sb.append(" and ");
//				sb.append("a01.a0148<='"+duty1+"'");
				sb.append("a01.a0148>='" + duty1 + "'");
				String colValueView = CustomQueryBS.getAaa103("A0148", duty1, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + duty1 + "','colNamesValue':'A0148','colNames':'职务层次','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(duty)) {
				sb.append(" and ");
//				sb.append("a01.a0148>='"+duty+"'");
				sb.append("a01.a0148<='" + duty + "'");
				String colValueView = CustomQueryBS.getAaa103("A0148", duty, cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + duty + "','colNamesValue':'A0148','colNames':'职务层次','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(a0192dE)) {
				sb.append(" and ");
				sb.append("a01.a0192d>='" + a0192dE + "'");
				String colValueView = CustomQueryBS.getAaa103("A0192D", a0192dE, cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0192dE + "','colNamesValue':'A0192D','colNames':'职务职级','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(a0192dS)) {
				sb.append(" and ");
				sb.append("a01.a0192d<='" + a0192dS + "'");
				String colValueView = CustomQueryBS.getAaa103("A0192D", a0192dS, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A01','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0192dS + "','colNamesValue':'A0192D','colNames':'职务职级','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(a0221aS)) {
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a<='" + a0221aS + "')");
				String colValueView = CustomQueryBS.getAaa103("A0221A", a0221aS, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0221aS + "','colNamesValue':'A0221A','colNames':'职务等级','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(a0221aE)) {
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0221a>='" + a0221aE + "')");
				String colValueView = CustomQueryBS.getAaa103("A0221A", a0221aE, cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0221aE + "','colNamesValue':'A0221A','colNames':'职务等级','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(dutynow)){
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288>='"+dutynow+"')");
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A02','colValuesView':'"+dutynow+"','logicSymbols':' and ','colValues':'"+dutynow+"','colNamesValue':'A0288','colNames':'职务层次起始日期','leftBracket':'','rightBracket':''}");
            }
            if(!"".equals(dutynow1)) {
				sb.append(" and ");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0288<='" + dutynow1 + "')");
				String colValueView = CustomQueryBS.getAaa103("A0288", dutynow1, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A02','colValuesView':'" + dutynow1 + "','logicSymbols':' and ','colValues':'" + dutynow1 + "','colNamesValue':'A0288','colNames':'职务层次结束日期','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(a0219)) {
				sb.append(" and ");
//				sb.append("a02.a0219='"+a0219+"'");
				sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.a0219='" + a0219 + "')");
				String colValueView = CustomQueryBS.getAaa103("A0219", a0219, cbBs.ctcList);
				customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A02','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + a0219 + "','colNamesValue':'A0219','colNames':'职务类别','leftBracket':'','rightBracket':''}");
			}
            if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
    			//选择机构
    			JSONObject jsonObject = JSONObject.fromObject(b01String);
    			sb.append("and exists (select 1 from a02 where a02.a0000=a01.a0000 ");
    			sb.append(" and (1=2 ");
    			Iterator<String> it = jsonObject.keys();
    			// 遍历jsonObject数据，添加到Map对象
    			while (it.hasNext()) {
    				String nodeid = it.next();
    				String operators = (String) jsonObject.get(nodeid);
    				String[] types = operators.split(":");
    				if("true".equals(types[1])&&"true".equals(types[2])) {
						sb.append(" or a02.A0201B like '" + nodeid + "%' ");
						String leftBracket = "";
						String rightBracket = "";
						String logicSymbols = " or ";
						leftBracket = "(";
						rightBracket = ")";
						logicSymbols = " and ";
						String colValueView = CustomQueryBS.getAaa103("B0111", nodeid, cbBs.ctcList);
						customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'" + colValueView + "','logicSymbols':'" + logicSymbols + "','colValues':'" + nodeid + "','colNamesValue':'B0111','colNames':'单位','leftBracket':'" + leftBracket + "','rightBracket':'" + rightBracket + "'}");
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
        					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+o1+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");
    						count++;
    					}*/
    				}else if("true".equals(types[1])&&"false".equals(types[2])) {
						sb.append("or a02.A0201B like '" + nodeid + ".%'");
						String leftBracket = "";
						String rightBracket = "";
						String logicSymbols = " or ";
						leftBracket = "(";
						rightBracket = ")";
						logicSymbols = " and ";
						String colValueView = CustomQueryBS.getAaa103("B0111", nodeid + ".", cbBs.ctcList);
						customData.append(",{'opeartors':'like {v%}','logchecked':'','tableName':'B01','colValuesView':'" + colValueView + "','logicSymbols':'" + logicSymbols + "','colValues':'" + nodeid + ".','colNamesValue':'B0111','colNames':'单位','leftBracket':'" + leftBracket + "','rightBracket':'" + rightBracket + "'}");
					}else if("false".equals(types[1])&&"true".equals(types[2])) {
						sb.append("or a02.A0201B = '" + nodeid + "'");
						String leftBracket = "";
						String rightBracket = "";
						String logicSymbols = " or ";
						leftBracket = "(";
						rightBracket = ")";
						logicSymbols = " and ";
						String colValueView = CustomQueryBS.getAaa103("B0111", nodeid, cbBs.ctcList);
						customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'" + colValueView + "','logicSymbols':'" + logicSymbols + "','colValues':'" + nodeid + "','colNamesValue':'B0111','colNames':'单位','leftBracket':'" + leftBracket + "','rightBracket':'" + rightBracket + "'}");
					}
    			}
    			sb.append(" ) ");
    			sb.append(" ) ");
				//sb.append(" and ");
//				sb.append("a02.A0201B in ("+b01String+")");
				//sb.append("exists (select 1 from a02 where a02.a0000=a01.a0000 and a02.A0201B in ("+b01String+"))");

				//String [] arrB01=b01String.split(",");
				/*if(arrB01.length>200){
					throw new AppException("不能选择超过200个单位！");
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
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'B01','colValuesView':'"+colValueView+"','logicSymbols':'"+logicSymbols+"','colValues':'"+b0111.replace("'", "")+"','colNamesValue':'B0111','colNames':'单位','leftBracket':'"+leftBracket+"','rightBracket':'"+rightBracket+"'}");
				}*/
            }
            if(!"".equals(edu)&&"".equals(edu1)) {
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='" + edu + "'");
				if (!"".equals(allday)) {
					sb.append(" and ");
					sb.append("a0837 = '" + allday + "'");
				}
				sb.append(")");

				String colValueView = CustomQueryBS.getAaa103("A0801B", edu, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + edu + "','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				if (!StringUtil.isEmpty(allday)) {
					colValueView = CustomQueryBS.getAaa103("A0837", allday, cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + allday + "','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");
				}

			}
            if(!"".equals(edu1)&&"".equals(edu)) {
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='" + edu1 + "'");
				if (!"".equals(allday)) {
					sb.append(" and ");
					sb.append("a0837 = '" + allday + "'");
				}
				sb.append(")");
				String colValueView = CustomQueryBS.getAaa103("A0801B", edu1, cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + edu1 + "','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				if (!StringUtil.isEmpty(allday)) {
					colValueView = CustomQueryBS.getAaa103("A0837", allday, cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + allday + "','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");
				}
			}
            if(!"".equals(edu1)&&!"".equals(edu)) {
				sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B between '" + edu1 + "' and '" + edu + "'");
				if (!"".equals(allday)) {
					sb.append(" and ");
					sb.append("a0837 = '" + allday + "'");
				}
				sb.append(")");

				String colValueView = CustomQueryBS.getAaa103("A0801B", edu, cbBs.ctcList);
				customData.append(",{'opeartors':'<={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + edu + "','colNamesValue':'A0801B','colNames':'学历','leftBracket':'(','rightBracket':''}");
				colValueView = CustomQueryBS.getAaa103("A0801B", edu1, cbBs.ctcList);
				customData.append(",{'opeartors':'>={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + edu1 + "','colNamesValue':'A0801B','colNames':'学历','leftBracket':'','rightBracket':''}");
				if (!StringUtil.isEmpty(allday)) {
					colValueView = CustomQueryBS.getAaa103("A0837", allday, cbBs.ctcList);
					customData.append(",{'opeartors':'={v}','logchecked':'','tableName':'A08','colValuesView':'" + colValueView + "','logicSymbols':' and ','colValues':'" + allday + "','colNamesValue':'A0837','colNames':'是否全日制','leftBracket':'','rightBracket':')'}");
				}
			}
		String data = "";
		if (!StringUtil.isEmpty(customData.toString()) && customData.length() > 0) {
			data = "[" + customData.toString().substring(1, customData.length()).replace("'", "\"") + "]";
		} else {
			data = "[]";
		}

		//lzy--update--2017.05.24
		sb.append(" and a01.status = '1' and concat(a01.a0000, '') in (select a02.a0000 from a02 where a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '" + SysManagerUtils.getUserId() + "') and a02.a0255 = '1' )");
		CustomQueryBS.delComm();
		CustomQueryBS.saveOrUodateCq("", "常用条件", sb.toString(), "", SysUtil.getCacheCurrentUser().getLoginname(), data);
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
			this.setMainMessage("正在建设中");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
		String sql=this.getPageElement("sql").getValue();
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		    String additionalSql=(String) this.request.getSession().getAttribute("additionalSql");
		    if(additionalSql==null||"".equals(additionalSql)){
		    	String b01String = this.getPageElement("orgjson").getValue();
				StringBuffer sb = new StringBuffer();
				if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
	    			//选择机构
	    			JSONObject jsonObject = JSONObject.fromObject(b01String);
	    			sb.append(" and exists (select 1 from a02, competence_userdept cu where a02.a0000=a01.a0000 and"
	    					+ " a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");
	    			sb.append(" and (1=2 ");
	    			Iterator<String> it = jsonObject.keys();
	    			// 遍历jsonObject数据，添加到Map对象
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
		StringBuffer sb = new StringBuffer();
//		sb.append("select   a01.a0000, a0101, a0104, age, a0117, a0141, a0192, a0148,A0160,A0192D,A0120,a02.A0221A,QRZXL,ZZXL,a02.a0288,a02.A0243 from A01 a01,A02 a02 where a01.a0000=a02.a0000 ");
		sb.append(getcommSQL() + " where 1=1 ");

		String name = this.getPageElement("name").getValue();
		String isOnDuty = this.getPageElement("isOnDuty").getValue();

		if (name != null && !"".equals(name)) {
			sb.append(" and (a01.A0101 like '%" + name + "%' or a01.A0102 like '" + name.toUpperCase() + "%') ");
		} else {
			throw new AppException("请输入查询人员姓名！");
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
		this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		String b01String = this.getPageElement("SysOrgTreeIds").getValue();
		/*b01String=b01String.replace("|", "'").replace("@", ",");//组织机构
		String [] arrB01=b01String.split(",");
		if(arrB01!=null && arrB01.length>200){
			throw new AppException("不能选择超过200个单位！");
		}*/
		StringBuffer a02_a0201b_sb = new StringBuffer();
		StringBuffer cu_b0111_sb = new StringBuffer();
        /*if(!"".equals(b01String)&&b01String!=null){
        	a02_a0201b_sb.append(" and a02.a0201b in ("+b01String+") ");
        	cu_b0111_sb.append(" and cu.b0111 in ("+b01String+") ");
            this.getPageElement("a0201b").setValue(b01String);
        }*/
		//选择机构
		String tree = this.getPageElement("SysOrgTree").getValue();
		if (b01String != null && !"".equals(b01String) &&
				tree != null && !"".equals(tree.trim())) {
			JSONObject jsonObject = JSONObject.fromObject(b01String);
			if(jsonObject.size()>0){
				a02_a0201b_sb.append(" and (1=2 ");
				cu_b0111_sb.append(" and (1=2 ");
			}
			Iterator<String> it = jsonObject.keys();
			// 遍历jsonObject数据，添加到Map对象
			while (it.hasNext()) {
				String nodeid = it.next();
				String operators = (String) jsonObject.get(nodeid);
				String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
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
		String a0101 = this.getPageElement("a0101A").getValue();//人员姓名
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//身份证号
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String ageS = this.getPageElement("ageA").getValue();//起始年龄
		String ageE = this.getPageElement("age1").getValue();//结束年龄
		String female = this.getPageElement("female").getValue();//性别是否女
		if(female.equals("1")){
			female = "2";
		}
		String minority = this.getPageElement("minority").getValue();//是否少数民族
		String nonparty = this.getPageElement("nonparty").getValue();//是否非中共党员
		String duty = this.getPageElement("duty").getValue();//起始职务层次
		String duty1 = this.getPageElement("duty1").getValue();//结束职务层次
		String a0221aS = this.getPageElement("a0221aS").getValue();//职务等级
		String a0221aE = this.getPageElement("a0221aE").getValue();//职务等级
		String a0192dS = this.getPageElement("a0192dS").getValue();//职务职级
		String a0192dE = this.getPageElement("a0192dE").getValue();//职务职级
		String dutynow = this.getPageElement("dutynow").getValue();//职务层次起始日期
		String dutynow1 = this.getPageElement("dutynow1").getValue();//职务层次结束日期
		String a0219 = this.getPageElement("a0219").getValue();//职务类别
		String edu = this.getPageElement("edu").getValue();//起始学历
		String edu1 = this.getPageElement("edu1").getValue();//结束学历
		String jiezsj = this.getPageElement("jiezsj").getValue();//年龄年限计算截止
		String radioC = this.getPageElement("radioC").getValue();
		String allday = this.getPageElement("allday").getValue();//是否全日制
		String sql = this.getPageElement("sql").getValue();
		//年龄范围转换成日期范围
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
				this.setMainMessage("年龄范围错误！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (NumberFormatException e2) {
			this.setMainMessage("年龄格式错误！");
			return EventRtnType.NORMAL_SUCCESS;
		}catch (Exception e1) {
			this.setMainMessage("截止日期格式错误！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!"1".equals(allday)){
//						allday="2";
			allday="";  //根据需求改为，未勾选则不添加  学历是否全日制条件 （bug编号：708）
		}
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		}
		//范围校验 start mengl 20160630
		/*if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){

			try {
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("年龄范围不正确，请检查！");
				}
			} catch (NumberFormatException e) {
				throw new AppException("年龄数值不正确，请检查！");
			}
		}*/

		if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
			if(dutynow.compareTo(dutynow1)>0){
				throw new AppException("任现职务层次时间范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
			CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
			CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);

			//TODO 职务等级范围是否校验待定
			if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
				throw new AppException("职务等级范围不属于同一类别，请检查！");
			}
			//职务等级 值越小 字面意思越高级
			if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
				throw new AppException("职务等级范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
			CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
			CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
			if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
				throw new AppException("职级范围不属于同一类别，请检查！");
			}
			//职级 值越小 字面意思越高级
			if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
				throw new AppException("职级范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
			CodeValue eduCodeValue = RuleSqlListBS.getCodeValue("ZB64", edu);
			CodeValue edu1CodeValue = RuleSqlListBS.getCodeValue("ZB64", edu1);
			//职级 值越小 字面意思越高级
			if (eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue()) < 0) {
				throw new AppException("学历范围不正确，请检查！");
			}
		}


		//范围校验 end
		StringBuffer a01sb = new StringBuffer();
		if (!a0101.equals("")) {
			a01sb.append(" and ");
			a01sb.append("a01.a0101 like'" + a0101 + "%'");
		}
		if (!a0184.equals("")) {
			a01sb.append(" and ");
			a01sb.append("a01.a0184 like'" + a0184 + "%'");
		}
		if (!a0160.equals("")) {
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
			a01sb.append("a01.a0148<='" + duty + "'");
		}
		if (!"".equals(a0192dE)) {
			a01sb.append(" and ");
			a01sb.append("a01.a0192d>='" + a0192dE + "'");
		}
		if (!"".equals(a0192dS)) {
			a01sb.append(" and ");
			a01sb.append("a01.a0192d<='" + a0192dS + "'");
		}
		StringBuffer a02sb = new StringBuffer();
		if (!"".equals(a0221aS)) {
			a02sb.append(" and ");
			a02sb.append("a02.a0221a<='" + a0221aS + "'");
		}
		if (!"".equals(a0221aE)) {
			a02sb.append(" and ");
			a02sb.append("a02.a0221a>='" + a0221aE + "'");
		}
		if (!"".equals(dutynow)) {
			a02sb.append(" and ");
        	a02sb.append("a02.a0288>='"+dutynow+"'");
        }
        if(!"".equals(dutynow1)){
			a02sb.append(" and ");
			a02sb.append(" a02.a0288<='" + dutynow1 + "'");
		}
		if (!"".equals(a0219)) {
			a02sb.append(" and ");
			a02sb.append(" a02.a0219='" + a0219 + "' ");
		}


		StringBuffer orther_sb = new StringBuffer();
		if (!"".equals(edu) && "".equals(edu1)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B <='" + edu + "'");
			if (!"".equals(allday)) {
				orther_sb.append(" and ");
				orther_sb.append("a0837 = '" + allday + "'");
			}
			orther_sb.append(")");
		}
		if (!"".equals(edu1) && "".equals(edu)) {
			orther_sb.append(" and a01.a0000 in (select a0000 from a08 where a0801B >='" + edu1 + "'");
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
	 * 查询（注：范围查询中代码的大小与字面逻辑的高低正好相反，所以判断逻辑也是相反的处理）
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("mQuery.onclick")
	public int query() throws RadowException, AppException{

		this.getPageElement("checkedgroupid").setValue(null);
		this.request.getSession().setAttribute("queryType", "1");
		StringBuffer sb = new StringBuffer();
		if (1 == 1) {
			return queryNew();
		}

		sb.append(getcommSQL() + " where " +
				" 1=1 ");//*where a.a0255='1' 常用查询可查以免职务


		//queryType = (String) this.request.getSession().getAttribute("queryType");
		String b01String = this.getPageElement("SysOrgTreeIds").getValue();
		b01String = b01String.replace("|", "'").replace("@", ",");//组织机构
		String[] arrB01 = b01String.split(",");
		if (arrB01 != null && arrB01.length > 200) {
			throw new AppException("不能选择超过200个单位！");
		}
		String a0101 = this.getPageElement("a0101A").getValue();//人员姓名
		String a0184 = this.getPageElement("a0184A").getValue().toUpperCase();//身份证号
		String a0160 = this.getPageElement("a0160").getValue();//人员类别
		String a0163 = this.getPageElement("a0163").getValue();//人员状态
		String age = this.getPageElement("ageA").getValue();//起始年龄
		String age1 = this.getPageElement("age1").getValue();//结束年龄
		String female = this.getPageElement("female").getValue();//性别是否女
		if(female.equals("1")){
			female = "2";
		}
		String minority = this.getPageElement("minority").getValue();//是否少数民族
		String nonparty = this.getPageElement("nonparty").getValue();//是否非中共党员
		String duty = this.getPageElement("duty").getValue();//起始职务层次
		String duty1 = this.getPageElement("duty1").getValue();//结束职务层次
		String a0221aS = this.getPageElement("a0221aS").getValue();//职务等级
		String a0221aE = this.getPageElement("a0221aE").getValue();//职务等级
		String a0192dS = this.getPageElement("a0192dS").getValue();//职务职级
		String a0192dE = this.getPageElement("a0192dE").getValue();//职务职级
		String dutynow = this.getPageElement("dutynow").getValue();//职务层次起始日期
		String dutynow1 = this.getPageElement("dutynow1").getValue();//职务层次结束日期
		String a0219 = this.getPageElement("a0219").getValue();//职务类别
		String edu = this.getPageElement("edu").getValue();//起始学历
		String edu1 = this.getPageElement("edu1").getValue();//结束学历
		String jiezsj = this.getPageElement("jiezsj").getValue();//年龄年限计算截止
		String radioC = this.getPageElement("radioC").getValue();
		String allday = this.getPageElement("allday").getValue();//是否全日制
		String sql = this.getPageElement("sql").getValue();
		if(!"1".equals(allday)){
//						allday="2";
			allday="";  //根据需求改为，未勾选则不添加  学历是否全日制条件 （bug编号：708）
		}
		if(!"1".equals(radioC)){
			if("".equals(sql)||sql==null)
				throw new AppException("未进行过查询请先查询!");
		}
		//范围校验 start mengl 20160630
		if(!StringUtil.isEmpty(age) && !StringUtil.isEmpty(age1)){

			try {
				if(Integer.parseInt(age)>Integer.parseInt(age1)){
					throw new AppException("年龄范围不正确，请检查！");
				}
			} catch (NumberFormatException e) {
				throw new AppException("年龄数值不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(duty) && !StringUtil.isEmpty(duty1)){
			CodeValue dutyCodeValue =RuleSqlListBS.getCodeValue("ZB09", duty);
			CodeValue duty1CodeValue =RuleSqlListBS.getCodeValue("ZB09", duty1);
			if(!dutyCodeValue.getSubCodeValue().equalsIgnoreCase(duty1CodeValue.getSubCodeValue())){
				throw new AppException("职务层次范围不属于同一类别，请检查！");
			}
			//职务层次 值越小 字面意思越高级
			if(dutyCodeValue.getCodeValue().compareTo(duty1CodeValue.getCodeValue())<0){
				throw new AppException("职务层次范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(dutynow) && !StringUtil.isEmpty(dutynow1)){
			if(dutynow.compareTo(dutynow1)>0){
				throw new AppException("任现职务层次时间范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(a0221aS) && !StringUtil.isEmpty(a0221aE)){
			CodeValue a0221aSCodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aS);
			CodeValue a0221aECodeValue =RuleSqlListBS.getCodeValue("ZB136", a0221aE);

			//TODO 职务等级范围是否校验待定
			if(!a0221aSCodeValue.getSubCodeValue().equalsIgnoreCase(a0221aECodeValue.getSubCodeValue())){
				throw new AppException("职务等级范围不属于同一类别，请检查！");
			}
			//职务等级 值越小 字面意思越高级
			if(a0221aSCodeValue.getCodeValue().compareTo(a0221aECodeValue.getCodeValue())<0){
				throw new AppException("职务等级范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(a0192dS) && !StringUtil.isEmpty(a0192dE)){
			CodeValue a0192dSSCodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dS);
			CodeValue a0192dECodeValue =RuleSqlListBS.getCodeValue("ZB133", a0192dE);
			if(!a0192dSSCodeValue.getSubCodeValue().equalsIgnoreCase(a0192dECodeValue.getSubCodeValue())){
				throw new AppException("职级范围不属于同一类别，请检查！");
			}
			//职级 值越小 字面意思越高级
			if(a0192dSSCodeValue.getCodeValue().compareTo(a0192dECodeValue.getCodeValue())<0){
				throw new AppException("职级范围不正确，请检查！");
			}
		}

		if(!StringUtil.isEmpty(edu) && !StringUtil.isEmpty(edu1)){
			CodeValue eduCodeValue =RuleSqlListBS.getCodeValue("ZB64", edu);
			CodeValue edu1CodeValue =RuleSqlListBS.getCodeValue("ZB64", edu1);
			//职级 值越小 字面意思越高级
			if(eduCodeValue.getCodeValue().compareTo(edu1CodeValue.getCodeValue())<0){
				throw new AppException("学历范围不正确，请检查！");
			}
		}


		//范围校验 end

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
		String a02_a0201b_sb = this.getPageElement("a02_a0201b_sb").getValue();
		this.getPageElement("a02_a0201b_sb").setValue("");
		
		
		String queryType = "";
		queryType=(String)request.getSession().getAttribute("queryType");
		if(queryType==null)queryType="1";

		//自定义查询  start
		if("define".equals(queryType)){
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
//			String sql="select * from "+viewnametb+" ";//移除在导出，移除无效
			this.request.getSession().setAttribute("allSelect","");
			StopWatch w = new StopWatch();
	      	w.start();
	      	if(this.request.getSession().getAttribute("pageSize") != null && !this.request.getSession().getAttribute("pageSize").equals("")){
				int pageSize = Integer.parseInt(this.request.getSession().getAttribute("pageSize").toString()); 				//判断是否设置了自定义每页数量，如果设置了使用自定义
				limit = pageSize;
			}

	      	this.pageQuery(sql, "SQL", start, limit);
	      	w.stop();

	      	PhotosUtil.saveLog("信息查询总耗时："+w.elapsedTime()+"\r\n执行的sql:"+sql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}   //自定义查询   end

		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		String sid = this.request.getSession().getId();   //获取当前用户 的sessionID
		//获取请输入姓名的值
		String name = this.getPageElement("seachName").getValue();
		if(name.equals("输入姓名")) {
			name=null;
		}else {
			name = name.replace("，", ",").replaceAll("[\\t\\n\\r]", ",");

		}

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
			String querySql="select  a01.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=a01.A0195) as a0195  from ("+ssql+") a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '"+sid+"') tp on a01.a0000 = tp.a0000 order by instr(',"+name+",',','||a0101||',')";
			//String querysql=allSql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid,sid));
			this.request.getSession().setAttribute("allSelect", querySql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@"+querySql);//统计专用
			this.request.getSession().setAttribute("listAdd", "listAdd");//标识符
			StopWatch w = new StopWatch();
	      	w.start();
	      	this.pageQueryNoCount(allSql, "SQL", 0, 20,totalcount);
	      	w.stop();
	      	this.request.getSession().removeAttribute("listAddGroupSession");
	      	PhotosUtil.saveLog("信息查询总耗时："+w.elapsedTime()+"\r\n执行的sql:"+allSql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}
		if( !CustomQueryPageModel.QUERYLISTFLAG ) {
			CustomQueryPageModel.LISTADDCCQLI="-1";
			CustomQueryPageModel.LISTADDNAME="无";
		}else {
			CustomQueryPageModel.QUERYLISTFLAG=false;
		}
		//判断是否启用“集体内排序”，


		String isContain = this.getPageElement("isContain").getValue();  //包含下级
		String isContain2 = this.getPageElement("isContain2").getValue();  //单机构点击和多机构点击区分
		if(isContain2!=null&&"0".equals(isContain2))
		{
			isContain = "0";

		}


		String radioC=this.getPageElement("radioC").getValue();
		//当queryType = 1，isContain = 0，并且，设置标志isA0225 = 1
		if(queryType.equals("1") && isContain != null && isContain.equals("0") && radioC.equals("1")){
			//this.request.getSession().setAttribute("isA0225", "1");
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
		//当前查询机构id
		String a0200 = this.getPageElement("checkedgroupid").getValue();

		String sort= request.getParameter("sort");//要排序的列名--无需定义，ext自动后传
		String isPageTurning = this.request.getParameter("isPageTurning");
		String startCache = request.getParameter("startCache");
		if(!"true".equals(isPageTurning)&&startCache!=null){//不是翻页  且点击字段排序
			start = Integer.valueOf(startCache);
		}
		if(sort != null && sort.equals("a0101")){
			sort = "a0102";
		}


        String dir= request.getParameter("dir");//要排序的方式--无需定义，ext自动后传
        String orderby = "";


		String userid = SysManagerUtils.getUserId();
		HttpSession session=request.getSession();
		if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
			int pageSize = Integer.parseInt(session.getAttribute("pageSize").toString()); 				//判断是否设置了自定义每页数量，如果设置了使用自定义
			limit = pageSize;
		}
		//int maxRow = 50000;

		this.getPageElement("checkList").setValue("");
		queryType=(String) this.request.getSession().getAttribute("queryType");
	    String sql=this.getPageElement("sql").getValue();
	    String LWflag = this.getPageElement("LWflag").getValue();
	    if(LWflag.equals("1"))
	    {
	    	sql =sql.replace("competence_userdept", "competence_userdept");
	    	//System.out.println(sql);
	    	sql =sql.replace("competence_userdept_look", "competence_userdept");
	    }
	    //替换当前session  查询方案中保存的sql还是以前的session
	    sql = sql.replaceAll("(select  a01.a0000,')(.*)(' sessionid from A01 a01)", CommSQL.getComSQL(sid));
	    //sql.replace(CommSQL.getComSQL(sid),"");

		//String radioC=this.getPageElement("radioC").getValue();
		String resultOptSQL = "";
		String tem_sql = "";
		String tem_sql2 = "";

		if("true".equals(isPageTurning)&&startCache==null){//说明是翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页
			String str6 = SysfunctionManager.getCurrentSysfunction().getFunctionid();
            String str7 = this.getCueEventElementName();
            SQLInfo localSQLInfo = (SQLInfo)this.request.getSession().getAttribute(str6 + "@" + str7);
			if(localSQLInfo!=null){
				StopWatch w = new StopWatch();
		      	w.start();
		      	//CommonQueryBS.systemOut("sql---:"+querysql);
		      	this.pageQuery(localSQLInfo.getSql(), "SQL", start, limit);
		      	w.stop();

		      	PhotosUtil.saveLog("信息查询总耗时："+w.elapsedTime()+"\r\n执行的sql:"+localSQLInfo.getSql()+"\r\n\r\n");
		  		return EventRtnType.SPE_SUCCESS;
			}
		}


	  //结果中查询
        if("2".equals(radioC)){
        	if(DBUtil.getDBType()==DBType.ORACLE){
        		tem_sql = "insert into cdttttt (select a0000,sessionid from  A01SEARCHTEMP where sessionid='"+sid+"' minus {sql})";
            	//tem_sql = "create or replace view ttttt as (select a0000,sessionid from A01SEARCHTEMP where sessionid='"+sid+"' minus {sql})";
    		    resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"+sid+"'";
    		    //删除A01SEARCHTEMP
            	//resultOptSQL = "delete from A01SEARCHTEMP where a0000  in (select a0000 from (select a0000 from  A01SEARCHTEMP where sessionid='"+sid+"' minus select a0000 from cdttttt )) and sessionid='"+sid+"'";
        	}else{
        		resultOptSQL = "delete from A01SEARCHTEMP where a0000 not in (select a0000 from ({sql}) ax ) ";
        	}


        //追加查询
        }else if("3".equals(radioC)){
        	tem_sql = "update A01SEARCHTEMP set sessionid='"+sid+"temp' where sessionid='"+sid+"'";
 		    resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
 		    tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='"+sid+"temp'";
        }else if("4".equals(radioC)){//排查查询
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
		//管理类别  a0165
		String a0165 = PrivilegeManager.getInstance().getCueLoginUser().getRate();
		if (a0165 != null && !"".equals(a0165)) {
			//2018年10月16日 去掉管理类别权限
			sql = sql + " and a01.a0165 not in (" + a0165 + ")";
		}
		//String iforder = this.getPageElement("orderqueryhidden").getValue();
  		/*String a0201bid = (String)this.getPageElement("a0201b").getValue();
		String[] jgid = a0201bid.split("\\.");
		//yinl a02条件增加分区 2017.08.02
		String v0201bs = (jgid.length >= AppConfig.PARTITION_FRAGMENT)?" and a02.V0201B='"+jgid[AppConfig.PARTITION_FRAGMENT-1]+"' ":"";*/
		//修复两个排序都选时报错的bug
		//iforder = (iforder.equals("1") && this.getPageElement("paixu").getValue().equals("1")) ? "0" : iforder;
		String tabType = this.getPageElement("tabn").getValue();
		if (!tabType.equals("tab3")) {
			String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
			//人员查看权限

			//某些情况下影响效率，且目前系统COMPETENCE_USERPERSON表已废弃，所以注释此段代码
			//sql=sql+ "  and not exists ("+personViewSQL+" where cu.a0000=a01.a0000 and cu.userid='"+SysManagerUtils.getUserId()+"') ";

		} else {//按机构查询
			String b01String = this.getPageElement("orgjson").getValue();
			StringBuffer sb = new StringBuffer();
  			if(!"".equals(b01String)&&b01String!=null&&!"{}".equals(b01String)){
      			//选择机构
      			JSONObject jsonObject = JSONObject.fromObject(b01String);
      			sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where  "
      					+ " a02.A0201B = cu.b0111 AND cu.userid = '"+SysManagerUtils.getUserId()+"' ");

      			Iterator<String> it = jsonObject.keys();
      			if(it.hasNext()){
      				sb.append(" and a02.a0281='true' ");
      			}
      			sb.append(" and (1=2 ");
      			// 遍历jsonObject数据，添加到Map对象
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
  		String replace = null;
		String selectCount =  " select count(2) from A01 a01 ";
		String replaceSql = "select   a01.a0000, a01.a1701 from A01 a01";
		if(sql.contains(CommSQL.getComSQL(sid))) {
			replace = sql.replace(CommSQL.getComSQL(sid), selectCount);
		}else if (sql.contains(replaceSql)) {
			replace = sql.replace(replaceSql, selectCount);
		}else {
			replace = selectCount;
		}
		
		//干部名册查询 特殊处理
		String selectCount2 =  " select count(2) ";
		String replaceSql2 = "SELECT a01.a0000, a01.a0101, a01.a0104, a01.A0107, a01.A0192a";
		if (sql.contains(replaceSql2)) {
			replace = sql.replace(replaceSql2, selectCount2);
		}
		
		Object count = HBUtil.getHBSession().createSQLQuery(replace).uniqueResult();

  		if(count instanceof BigDecimal){
  			totalcount = ((BigDecimal)count).intValue();
  		}else if(count instanceof BigInteger){
  			totalcount = ((BigInteger)count).intValue();
  		}else if(count instanceof Long){
  			totalcount = ((Long)count).intValue();
  		}
		if(totalcount>CommSQL.MAXROW){
			if(!"1".equals(radioC)){
				throw new AppException("单次查询结果超过"+CommSQL.MAXROW+"行限制，无法进行集合操作！");
			}

			querysql = sql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid,sid));

			this.request.getSession().setAttribute("allSelect", querysql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@"+querysql);//统计专用
			StopWatch w = new StopWatch();
	      	w.start();
	      	//CommonQueryBS.systemOut("sql---:"+querysql);
	      	this.pageQueryNoCount(querysql, "SQL", start, limit,totalcount);
	      	w.stop();

	      	PhotosUtil.saveLog("信息查询总耗时："+w.elapsedTime()+"\r\n执行的sql:"+querysql+"\r\n\r\n");
	  		return EventRtnType.SPE_SUCCESS;
		}

		String type=(String) this.request.getSession().getAttribute("Man_Similarity");
		//System.out.println("session ===================="+this.request.getSession().getAttribute("zb09Word"));
  		if(StringUtil.isEmpty(temporarySort)){			//temporarySort为空，不是临时排序
  			if(("2".equals(radioC)||"4".equals(radioC))&&DBUtil.getDBType()==DBType.ORACLE){
  	  			this.optResult(sql, tem_sql,resultOptSQL);
  	  			//this.optResult(sql, resultOptSQL);
  	  		}else if("3".equals(radioC)){
  	  			String qsql = "";
	  	  		if(sql.contains(CommSQL.getComSQL(sid))) {
	  	  			qsql = sql.replace(CommSQL.getComSQL(sid), " select a0000 from A01 a01 ") + " union all select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"temp'";
	  			}else if (sql.contains(replaceSql)) {
	  				qsql = sql.replace(replaceSql, " select a0000 from A01 a01 ") + " union all select a0000 from A01SEARCHTEMP where sessionid = '"+sid+"temp'";
	  			}
  	  			
  	  		    qsql = CommSQL.getComSQL(sid) + " where a0000 in ("+qsql+")";
	  	  		//sql = sql + " or a01.a0000 in (select a0000 from A01SEARCHTEMP where sessionid='"+sid+"temp')";
  	  		    qsql = qsql+("".equals(orderby)?CommSQL.OrderByF(userid,isA0225,a0200,qsql,a02_a0201b_sb):orderby);
	  	  		this.optResult(qsql, tem_sql, tem_sql2, resultOptSQL);
  	  		}/*else if(name!=null) {
  	  			orderby=" order by instr(',"+name+",',','||a0101||',')";
  	  			this.optResult(sql+(orderby=" order by instr(',"+name+",',','||a0101||',')"), resultOptSQL);
  	  		}*/else{
  	  			if(type !=null && type.length()>0) {
  	  				this.optResult(sql+("".equals(orderby)?CommSQL.OrderByF(userid,isA0225,a0200,sql,a02_a0201b_sb):orderby), resultOptSQL,0);
  	  			}else {
  	  				this.optResult(sql+("".equals(orderby)?CommSQL.OrderByF(userid,isA0225,a0200,sql,a02_a0201b_sb):orderby), resultOptSQL);
  	  			}
  	  		}
  		}
  		/* 无锡加 */
      	//是否已审
      	String isAudit = this.getPageElement("isAudit").getValue();
  		querysql = CommSQL.getComSQLQuery(userid,sid)+" /*"+UUID.randomUUID()+"*/"
  				+ ("0".equals(isAudit)?"":" and a0189='1' and a0190='1' ");

  		//根据贵州客户要求，如果是包含下级查询，则输出的任现职时间，只按任职在当前点击的机构输出。（如果存在多条，只取在任的、时间最早的那一条）
  		String isGBGL=(String)request.getSession().getAttribute("isGBGL");
  		if("1".equals(isGBGL)&&isContain!=null&&"0".equals(isContain)){
  			//querysql = querysql.replace("A0192F as a0192f", "concat(concat(substr(cp.A0243,0,4),'.'),substr(cp.A0243,5,2)) as a0192f");

  			querysql = querysql.replace("A0192A as a0192a", "nvl(cp.A0283G,a0192a) as a0192a ");

  			String b0111 = this.getPageElement("a0201b").getValue();
  			querysql = querysql + "join (select T.a0000,T.a0243,T.a0283G from ( "+
  						"SELECT ROW_NUMBER() OVER(PARTITION BY a02.A0000 ORDER BY a02.a0243) rn,a02.a0000,a02.a0243,a02.a0283G "+
  						"FROM A02 a02 WHERE a02.a0201b = '"+b0111+"' and a02.a0281 = 'true' ) T WHERE T.RN = 1) cp on cp.a0000 = a01.a0000 ";
  			request.getSession().setAttribute("isGBGL",null);
  		}

  		querysql = querysql + CommSQL.OrderBy(sort+" "+dir) ;

  		//清空是否临时排序标记
  		this.request.getSession().setAttribute("temporarySort", "");
  		//清空启用单机构集体内排序顺序标记
  		this.request.getSession().setAttribute("isA0225", "");
      	if(!"1".equals(radioC)){
			count = HBUtil.getHBSession().createSQLQuery("select count(a0000) from A01SEARCHTEMP where sessionid='"+sid+"'").uniqueResult();
	  		if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}

	     }

		//修改sql成 连接百分比查询  并设置zb09display""
      	if(type !=null && type.length()>0) {
  			querysql=querysql.replace("(case  when length(a0101)=2 then (Substr(a0101,0,1))||'　'||(Substr(a0101,2,2))else a0101 end) as a0101", "(case  when length(a0101)=2 then (Substr(a0101,0,1))||'　'||(Substr(a0101,2,2))else a0101 end)||(SELECT Percentage_Number FROM Percentage per WHERE sessionID = '"+sid + "' AND per.A0000 = a01.a0000) AS a0101");
  			this.setNextEventName("Eliminate");
  			this.request.getSession().setAttribute("Man_Similarity", "");
  		}

      		this.request.getSession().setAttribute("allSelect", querysql);
      		this.request.getSession().setAttribute("ry_tj_zy", "tempForCount@@"+querysql);//统计专用

      	StopWatch w = new StopWatch();
      	w.start();
      	String a0163 = this.getPageElement("personq").getValue();
      	//a0163='4' 说明是全员库查询，不对a0281是否输出做判断
      	if(a0163.equals("4")) {
      		querysql=querysql.replace("and a02.a0281 = 'true'", "");
      	}
      	CommonQueryBS.systemOut("sql----CustomQueryPageModel-----:"+querysql);
      	this.pageQueryNoCount(querysql, "SQL", start, limit,totalcount);
      	w.stop();
      	System.out.println(querysql);
      	PhotosUtil.saveLog("信息查询总耗时："+w.elapsedTime()+"\r\n执行的sql:"+querysql+"\r\n\r\n");
  		return EventRtnType.SPE_SUCCESS;
	}

	public String setShenHeValue(String sql){
		String sql1="select count(1) from("+sql+" where A0189='1')";//干部一处已审核
		String sql2="select count(1) from("+sql+" where A0189='0' or A0189 is null) ";//干部一处未审核
		String sql3="select count(1) from("+sql+" where A0190='1') ";//干部处已审核
		String sql4="select count(1) from("+sql+" where A0190='0' or A0190 is null) ";//干部处未审核
		String sql5="select count(1) from("+sql+" where A0190='1' and A0189='1') ";//已完成

		HBSession session = null;
		Statement stmt = null;
		ResultSet rs = null;
		session = HBUtil.getHBSession();

		String chu1yes=session.createSQLQuery(sql1).uniqueResult().toString();
		String chu1no=session.createSQLQuery(sql2).uniqueResult().toString();
		String chuyes=session.createSQLQuery(sql3).uniqueResult().toString();
		String chuno=session.createSQLQuery(sql4).uniqueResult().toString();
		String wanc=session.createSQLQuery(sql5).uniqueResult().toString();

		String bacStr="干部一处已审核："+chu1yes+"人，未审核："+chu1no+"人。干部处室已审核："+chuyes+"人，未审核："+chuno+"人。已完成审核："+wanc+"人。";
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


	//mySQL: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql,String resultOptSQL){
		try {


			String sid = this.request.getSession().getId();

			if (DBUtil.getDBType() == DBType.ORACLE) {
				sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + " ) tmp";

			} else {            //mysql
				sql = "select tmp.a0000,'" + sid + "' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql + " ) tmp";
			}

			System.out.println(resultOptSQL.replace("{sql}", sql));
			//sql = sql.replace("sort,", "");
			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}

	//mySQL: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql,String resultOptSQL,int type){
		sql=sql.substring(0, sql.lastIndexOf(")")) +")";
		try {
			String sid = this.request.getSession().getId();

			if(DBUtil.getDBType()==DBType.ORACLE){
				sql = "select tmp.a0000,'"+sid+"' sessionid,rownum i from ( " + sql + " ) tmp";

			}else{			//mysql
				sql = "select tmp.a0000,'"+sid+"' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql + " ) tmp";
			}

			System.out.println(sql);
			//sql = sql.replace("sort,", "");

			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}


	//移除
	private void optResultDelete(String sql){

		String resultOptSQL = "insert into A01SEARCHTEMP {sql} " ;
		try {
			String sid = this.request.getSession().getId();

			//先将临时表的sessionid更改，之后删除这些记录
			String tem_sql = "update A01SEARCHTEMP set sessionid='"+sid+"temp' where sessionid='"+sid+"'";

 		    String tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='"+sid+"temp'";

 		    //改造sql
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

	//oracle: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql,String tmp_sql, String resultOptSQL){
		String sid = this.request.getSession().getId();
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			
			String replaceSql2 = ", a01.a0101, a01.a0104, a01.A0107, a01.A0192a";
			if(sql.contains(replaceSql2)) {
				sql = sql.replace(replaceSql2, ",'"+sid+"' ");
			}
			String replaceSql = ", a01.a1701";
			if(sql.contains(replaceSql)) {
				sql = sql.replace(replaceSql, ",'"+sid+"' ");
			}
			System.out.println(tmp_sql.replace("{sql}", sql));
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
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','"+allName+",已生成登记表，是否重新生成？注：该人员已生成登记表，如再次生成得覆盖原内容且无法恢复。',200,function(id){" +
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
				this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','"+allName+",已生成登记表，是否重新生成？注：该人员已生成登记表，如再次生成得覆盖原内容且无法恢复。',200,function(id){" +
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
	 * 清除条件
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("clearCon.onclick")
	@NoRequiredValidate
	public int clearCon()throws RadowException, AppException{
		String a = this.getPageElement("female").getValue();
		//this.getPageElement("a0160").setValue("");
		this.getPageElement("a0160_combo").setValue("");        //人员类别-fujun
		this.getPageElement("a0101A").setValue("");
		this.getPageElement("a0184A").setValue("");
		//this.getPageElement("a0163").setValue("");
		this.getPageElement("a0163_combo").setValue("");        //人员状态-fujun
		this.getPageElement("ageA").setValue("");
		this.getPageElement("age1").setValue("");
		this.getPageElement("female").setValue("0");
		this.getPageElement("minority").setValue("0");
		this.getPageElement("nonparty").setValue("0");
		//this.getPageElement("duty").setValue("");
		this.getPageElement("duty_combo").setValue("");            //职务层次 ——fujun
		//this.getPageElement("duty1").setValue("");
		this.getPageElement("duty1_combo").setValue("");        //职务层次 ——fujun
		//this.getPageElement("dutynow").setValue("");
		this.getPageElement("dutynow_1").setValue("");            //任现职务层次时间 ——fujun
		//this.getPageElement("dutynow1").setValue("");
		this.getPageElement("dutynow1_1").setValue("");            //任现职务层次时间 ——fujun
		//this.getPageElement("a0219").setValue("");
		this.getPageElement("a0219_combo").setValue("");		//职务类别——fujun
		//this.getPageElement("edu").setValue("");
		this.getPageElement("edu_combo").setValue("");		//学历——fujun
		//this.getPageElement("edu1").setValue("");
		this.getPageElement("edu1_combo").setValue("");		//学历——fujun
		this.getPageElement("allday").setValue("0");
		this.getPageElement("SysOrgTree").setValue("");
		this.getPageElement("SysOrgTreeIds").setValue("");
	    //this.getPageElement("a0221aS").setValue("");//职务等级
		this.getPageElement("a0221aS_combo").setValue("");		//职务等级——fujun
		//this.getPageElement("a0221aE").setValue("");//职务等级
		this.getPageElement("a0221aE_combo").setValue("");		//职务等级——fujun

		//this.getPageElement("a0192dS").setValue("");//职务职级
		this.getPageElement("a0192dS_combo").setValue("");		//职务职级——fujun
		//this.getPageElement("a0192dE").setValue("");//职务职级
		this.getPageElement("a0192dE_combo").setValue("");			//职务职级——fujun

		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 打开模型脚本查询页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("mscript.onclick")
	@NoRequiredValidate
	public int mscriptOnclick()throws RadowException{
		//this.setRadow_parent_data("");
		//this.openWindow("win1", "pages.customquery.QueryCondition");
		this.getExecuteSG().addExecuteCode("$h.openWin('win1','pages.customquery.QueryCondition','查询方案',1250,500,'','"+request.getContextPath()+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}


	@Override
	public int doInit() throws RadowException {
		/*
		 * try { //B01Transfer.deployPhotos(); Tests.A01_A15(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		String userid = SysUtil.getCacheCurrentUser().getId();
		if(!"40288103556cc97701556d629135000f".equals(userid)) {
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('deletePersonBtn').hide();");
		}
		this.getExecuteSG().addExecuteCode("resizeframe();");
		LISTADDCCQLI="-1";
		LISTADDNAME="无";
		this.deleteResult(this.request.getSession().getId());
		this.request.getSession().setAttribute("isContainCQ", null);
		this.request.getSession().setAttribute("queryType", null);
		this.request.getSession().setAttribute("queryConditionsCQ", null);
		this.request.getSession().setAttribute("queryTypeEX", null);
		this.request.getSession().setAttribute("additionalSql", null);
		this.request.getSession().setAttribute("allSelect", null);
		this.request.getSession().setAttribute("groupBy", null);
		//清空是否临时排序标记
  		this.request.getSession().setAttribute("temporarySort", "");
  		//清空启用单机构集体内排序顺序标记
  		this.request.getSession().setAttribute("isA0225", "");
  		//获取上次提交pad zip 文件时间及路径
  		String latestDate = getLatestDate();
  		this.getPageElement("latestTime").setValue(latestDate);

		this.getExecuteSG().addExecuteCode("getShowFour();");//显示常规查询tab
		this.setNextEventName("initNext");//删除机构时查看人员、工作台图表查看人员
        SimpleDateFormat myFmt1=new SimpleDateFormat("yyyyMMdd");
		//PageElement pe= this.getPageElement("isOnDuty");
		//pe.setValue("1");
        String datestr = myFmt1.format(new Date());
		this.getPageElement("radioC").setValue("1");
        //this.getPageElement("jiezsj").setValue(datestr);
        //this.getPageElement("jiezsj_1").setValue(datestr.substring(0, 4)+"."+datestr.substring(4, 6));


		//对直统功能做是否显示控制
		HBSession sess = HBUtil.getHBSession();
		String import_isuseful = sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'ZHT_ISUSEFUL'").uniqueResult().toString();

		if(import_isuseful != null && import_isuseful.equals("ON")){
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('hztjBtnNlmenu').hidden = '';");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('nlqkztBtn').hidden = '';");

		}else{
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('hztjBtnNlmenu').hidden = 'true';");
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('nlqkztBtn').hidden = 'true';");
		}
		
		if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tjfx').hide();odin.ext.getCmp('skbs').hide();odin.ext.getCmp('tpbj').hide();document.getElementById('gbmccx').style.display = 'none'");
		}

		//审核锁定控制
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
		this.setNextEventName("consoleButtons");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("consoleButtons")
	public void consoleButtons(){
		this.controlButton();
	}

	/**
	 * 机构树不能删除，查询人员
	 * 工作台统计图，单击查询人员
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws UnsupportedEncodingException
	 */
	@PageEvent("initNext")
	public int initNext() throws RadowException, AppException, UnsupportedEncodingException{
		//判断是否是机构查询调用方法
		String groupID = this.getPageElement("groupID").getValue();
		if(groupID==null||groupID.trim().equals("")){
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(groupID.split("@@").length==2//机构树不能删除，查询人员
				||groupID.split("@@").length==3//工作台统计图，单击查询人员
				 ){

		}else{
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("jgcx_delete".equals(groupID.split("@@")[0])//成立则是，机构不能删除，查看人员
				||"zwflcustom".equals(groupID.split("@@")[0])||//工作台统计图，单击查询人员
				"jgcxOrlt_delete".equals(groupID.split("@@")[0])){//机构不能删除，查看历史或离退人员
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
		    	if("jgcx_delete".equals(groupID.split("@@")[0])){//成立则是，机构不能删除，查看人员 在职人员
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
		    	}else if("jgcxOrlt_delete".equals(groupID.split("@@")[0])){//机构不能删除，查看历史或离退人员
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
		    	}else if("zwflcustom".equals(groupID.split("@@")[0])){//工作台统计图，单击查询人员
		    		String temp=java.net.URLDecoder.decode(groupID.split("@@")[2],"utf-8");
		    		//根据职务类别，与职务名称，获取职务代码
		    		String zwlb=HBUtil.getValueFromTab("code_value",
		    				"code_value",
		    				"code_name3='"+temp+"' "
		    						+ " and code_type='ZB09' "
		    						+ " and sub_code_value='"+groupID.split("@@")[1]+"' ");
		    		a0201bsql=" a01.a0221='"+zwlb+"' ";
		    		String userid=SysUtil.getCacheCurrentUser().getId();
		    		CommQuery cq=new CommQuery();
		    		//人员管理类别权限
		    		List<HashMap<String, Object>> list_user=cq.getListBySQL(" select rate,empid from smt_user t where t.userid = '"+userid+"' ");
		    		String rylb="";
					if (list_user != null && list_user.size() > 0) {
						String tempuser = (String) list_user.get(0).get("rate");//人员类别 不可浏览项
						if (tempuser != null && tempuser.length() > 0) {
							rylb = tempuser;
						}
						tempuser = (String) list_user.get(0).get("empid");//人员类别 不可维护项
						if (tempuser != null && tempuser.length() > 0) {
							rylb = rylb + "," + tempuser;
						}
					}
					if (rylb != null && rylb.indexOf("'") != -1) {
						a0201bsql = a0201bsql + " and a01.A0165 not in ( " + rylb + ") ";
					}
				}
				is_use_a0000_idx_sql = "a01.a0000";
			    String xzry = this.getPageElement("xzry").getValue();    //现职人员
			    String lsry = this.getPageElement("lsry").getValue();    //历史人员
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
	 * 对选中人员进行数据校验
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
				this.setMainMessage("没有设置机构权限");
				return EventRtnType.FAILD;
			}else{
				this.getExecuteSG().addExecuteCode("document.getElementById('isContain').checked=true;");
				this.getExecuteSG().addExecuteCode("radow.doEvent('querybyid','"+b01+"')");
				peopleid="all";
			}
		}else{
		/*if(!SysRuleBS.havaRule(groupid)){
			throw new RadowException("您无此权限!");
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
				this.setMainMessage("无人员信息");
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
			  this.setMainMessage("无人员信息");
				return EventRtnType.FAILD;
		  }

		}
	}
		String ctxPath = request.getContextPath();
		String paradata = "3@"+sid+"@"+peopleid;

		this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','人员信息校核',700,599,'','"+ctxPath+"',null,{ids:'"+paradata+"',maximizable:false,resizable:false});");
		//this.setRadow_parent_data("1@"+groupid);
		//this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
		return EventRtnType.NORMAL_SUCCESS;
	}






	//信息集录入页面
	@PageEvent("infoAdd.onclick")
	@NoRequiredValidate
	public int infoAdd(String id)throws RadowException{
		HttpSession session =  request.getSession();
		if(session.getAttribute("order")==null) {
			//设置默认顺序
			List<String> list = new ArrayList<String>();
			list.add("姓名");
			list.add("性别");
			list.add("身份证号");
			list.add("出生年月");
			list.add("参加工作时间");
			list.add("民族");
			list.add("籍贯");
			list.add("出生地");
			list.add("健康状况");
			list.add("人员类别");
			list.add("管理类别");
			list.add("编制类型");
			list.add("统计关系所在单位");
			list.add("政治面貌");
			list.add("入党时间");
			list.add("第二党派");
			list.add("第三党派");
			list.add("成长地");
			list.add("公务员登记时间");
			list.add("级别");
			list.add("专长");
			list.add("备注");
			request.getSession().setAttribute("order", list);
		}
		if(session.getAttribute("a02Order")==null) {
			//设置默认顺序
			List<String> list = new ArrayList<String>();
			list.add("任职机构");
			list.add("任职机构名称");
			list.add("职务名称");
			list.add("任免状态");
			list.add("主职务");
			list.add("领导职务");
			list.add("领导成员");
			list.add("成员类别");
			list.add("破格提拔");
			list.add("选拔任用方式");
			list.add("任职时间");
			list.add("任职文号");
			list.add("免职时间");
			list.add("免职文号");
			list.add("职务变动原因综述");
			request.getSession().setAttribute("a02Order", list);
		}
		String a0000 = UUID.randomUUID().toString();
		this.request.getSession().setAttribute("nowNumber","");
		this.request.getSession().setAttribute("personIdSet", null);
		this.getExecuteSG().addExecuteCode("$h.openWin('infoNew','pages.publicServantManage.InfoNew','信息集新增人员',1400,700,document.getElementById('a0000').value,'"+request.getContextPath()+"');");

		return EventRtnType.NORMAL_SUCCESS;
	}


	


	/**
	 * 勾选修改人员任免表信息
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("rmbUpdate.onclick")
	@GridDataRange
	public int rmbUpdate() throws RadowException, AppException{  //打开窗口的实例
		String sqlf = this.getPageElement("sql").getValue();
		if (sqlf.equals("")) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//列表
		if ("1".equals(tableType)) {
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			List<HashMap<String, Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
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
				//this.setMainMessage("请勾选人员！");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;

			}else if(countNum>1){

				//this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅勾选一人进行操作！',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//小资料、照片
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
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("请仅勾选一人进行操作！");

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000 = a0000s;
		}

		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息修改',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/

			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-90)+', width=1024, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			//initA0000Map(a0000);
			//this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			//this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
		/*A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			initA0000Map(a0000);
			this.getExecuteSG().addExecuteCode("addTab('','"+a0000.toString()+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
			this.setRadow_parent_data(this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			//throw new AppException("该人员不在系统中！");
			//this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','该人员不在系统中！');");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','该人员不在系统中！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
	}

	@PageEvent("modify")
	public int modify(String a0000)throws AppException, RadowException{
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){

			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息修改',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,645,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-90)+', width=1024, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}

	@PageEvent("deletePeople")
	public int deletePeople(String a0000)throws AppException, RadowException{

		String tipStr = this.getPageElement("deleteTip").getValue();

		dialog_set("deleteconfirm1", "您确定删除  " + tipStr + "  吗？", a0000.replace("'", "^"));
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("printTrue")
	public int printTrue(String a0000)throws AppException, RadowException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null || StringUtil.isEmpty(a01.getA0184())){
			throw new AppException("人员信息有误！");
		}
		try {
			pdfView(a0000+",true");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("printFalse")
	public int printFalse(String a0000)throws AppException, RadowException{
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(StringUtil.isEmpty(a0000) || a01==null){
			throw new AppException("人员信息有误！");
		}
		try {
			pdfView(a0000+",false");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			int x=0;
			while(FileUtil.checkFile(zippath+name+".lrm")) {
				x++;
				name+=x;
			}
			FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
			List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
			if(list15.size()>0){
				A57 a57 = list15.get(0);
				if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
					File f = new File(zippath + name+".pic");
					FileOutputStream fos = new FileOutputStream(f);
					File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
					if(f2.exists() && f2.isFile()){
						InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
						byte[] data = new byte[1024];
						while (is.read(data) != -1) {
							fos.write(data);
						}
						is.close();
					}
					fos.close();

				}
			}
//			String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//			p = rt.exec(cmdd);
//			p.waitFor();
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		try {
			infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//			String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//			p = Runtime.getRuntime().exec(cmd);
			SevenZipUtil.zip7z(zippath, infile, null);
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.downloadword()");
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("expLrmx")
	public int expLrmx(String a0000)throws AppException, RadowException{
		String zippath =ExpRar.expFile();
		String name = "";
		String infile = "";
		PersonXml per = createLrmxStr(a0000,"");
		per.setJianLi(per.getJianLi().replaceAll("&lt;", "&1lt;"));
		per.setJianLi(per.getJianLi().replaceAll("&gt;", "&1gt;"));
		name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//		System.out.println(zippath);
		try {
			int x=0;
			while(FileUtil.checkFile(zippath+name+".lrmx")) {
				x++;
				name+=x;
			}
			FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
//			String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//			p = rt.exec(cmdd);
//			p.waitFor();
			A01 a01log = new A01();
			new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		try {
			infile =zippath.substring(0,zippath.length()-1)+"\\"+name+".lrmx";
//			String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//			p = Runtime.getRuntime().exec(cmd);
			/* SevenZipUtil.zip7z(zippath, infile, null); */
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.downloadword()");
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("alertW.onclick")
	@NoRequiredValidate
	public int alertW(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		this.getExecuteSG().addExecuteCode("alertW('alertWin','弹出窗口',"+random+")");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("createDjbd.onclick")
	@NoRequiredValidate
	public int openUpdateWin1(String id)throws RadowException{
		this.setRadow_parent_data("add");
		String random = UUID.randomUUID().toString();
		//新增时删除传递的UUID tongzj
		//this.getExecuteSG().addExecuteCode("addTab('人员新增窗口','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.getExecuteSG().addExecuteCode("addTab('表册输出窗口','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.search.ListOutPut',false,false)");
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("createDjb2.onclick")//常用固定表册按钮删除
	@NoRequiredValidate
	public int createDjb2(String id)throws RadowException{

		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("createDjbr.onclick")
	@NoRequiredValidate
	public int createDjb3(String id)throws RadowException, SQLException{
		/*String sql = "select tpid from listoutput where tpname like '公务员任免表%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		re.next();
		String value = re.getString(1);
		this.request.getSession().setAttribute("tpid", value);
		this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
		this.getExecuteSG().addExecuteCode("addTab('表格','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");*/
		this.setMainMessage("暂时不做");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//干部任免表
	@PageEvent("createDjrmb.onclick")
	@NoRequiredValidate
	public int createDjrmb()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//判断是否点击全部人员
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
		String sql = "select tpid from listoutput where tpname = '任免审批表【表格】' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		String ctxPath = request.getContextPath();
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('干部任免表表格','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin','pages.publicServantManage.OtherTemShow','干部任免审批表',1200,900,'R','"+ctxPath+"');");

		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	//公务员登记表
	@PageEvent("createDjbj.onclick")
	@NoRequiredValidate
	public int createDjb5()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		HBSession sess = HBUtil.getHBSession();
		if("1".equals(tableType)){
			//判断是否点击全部人员
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
		String sql = "select tpid from listoutput where tpname like '公务员登记表%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('公务员登记表格','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin1','pages.publicServantManage.OtherTemShow','公务员登记表格',1200,900,'R','"+ctxPath+"');");

		}
		return EventRtnType.NORMAL_SUCCESS;

	}
	//干部简要情况表
	@PageEvent("createGanBu.onclick")
	@NoRequiredValidate
	public int createGanBu()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//判断是否点击全部人员
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
		String sql = "select tpid from listoutput where tpname like '干部简要情况表%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('干部简要表格','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin2','pages.publicServantManage.OtherTemShow','干部简要表格',1200,900,'R','"+ctxPath+"');");

		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	//奖励审批表
	@PageEvent("createJiangLi.onclick")
	@NoRequiredValidate
	public int createJiangLi()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//判断是否点击全部人员
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
		String sql = "select tpid from listoutput where tpname like '奖励审批表%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('奖励审批表格','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin3','pages.publicServantManage.OtherTemShow','奖励审批表格',1200,900,'R','"+ctxPath+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//年度考核表
	@PageEvent("createKaoHe.onclick")
	@NoRequiredValidate
	public int createKaoHe()throws RadowException, SQLException{
		String tableType = this.getPageElement("tableType").getValue();
		String person = "";
		if("1".equals(tableType)){
			//判断是否点击全部人员
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
		String sql = "select tpid from listoutput where tpname like '年度考核表%' group by tpid ";
		ResultSet re = HBUtil.getHBSession().connection().createStatement().executeQuery(sql);
		if( re != null && re.next()){
			String value = re.getString(1);
			this.request.getSession().setAttribute("tpid", value);
			this.request.getSession().setAttribute("personids", person);
//			String aid = "R"+(Math.random()*Math.random()*100000000);
//			this.getExecuteSG().addExecuteCode("addTab('年度考核表格','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
			String ctxPath = request.getContextPath();
			this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin4','pages.publicServantManage.OtherTemShow','年度考核表格',1200,900,'R','"+ctxPath+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//花名册
	@PageEvent("createHuaMc")
	public int createHuaMc(String valuee)throws RadowException, SQLException{
		/*//获取页面是否包含下级
		String isContain = this.getPageElement("isContain").getValue();
		//把状态转到OtherTemShowTowPageModel里面 判断目录的展示形式是否包含下级
		request.getSession().setAttribute("isContain", isContain);
		String checkList = "";
		String tpname = "";
		//获取模板名称
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
			//通过机构编码查询该机构及下级的所有人员；
			String att = (String)request.getSession().getAttribute("over");
			if("true".equals(att)){
				//listofname是从CustomQueryPageModel 类 传来的 机构编码；
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
			this.getExecuteSG().addExecuteCode("addTab('花名册','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShowTow',false,false)");
		}*/
		//获取页面是否包含下级
		String isContain = this.getPageElement("isContain").getValue();
		//把状态转到OtherTemShowTowPageModel里面 判断目录的展示形式是否包含下级
		request.getSession().setAttribute("isContain", isContain);
		//传的是模板id
		this.request.getSession().setAttribute("tpid", valuee);
		//通过机构编码查询该机构及下级的所有人员；
		String checkList = this.getPageElement("checkList").getValue();
		//传到othertemshowtopagemodel 里面
		this.request.getSession().setAttribute("personids", checkList);
		request.getSession().setAttribute("viewType", "11");
		//this.getExecuteSG().addExecuteCode("refresh();");
//		String aid = "R"+(Math.random()*Math.random()*100000000);
//		this.getExecuteSG().addExecuteCode("addTab('花名册','"+aid+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShowTow',false,false)");
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openWin('addOrgWin5','pages.publicServantManage.OtherTemShowTow','花名册',1200,900,'R','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//人员修改
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
		if (countNum == 0) {
			throw new AppException("请勾选人员！");
		} else if (countNum > 1) {
			throw new AppException("请仅勾选一人进行操作！");
		}
		if (a0000 == null || a0000.trim().equals("")) {
			throw new AppException("数据获取异常！");
		}
		this.getExecuteSG().addExecuteCode("addTab('','" + a0000 + "','" + request.getContextPath() + "/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
	}





	//删除事件
	@PageEvent("deletePersonBtn.onclick")
	@GridDataRange
	public int deletePerson() throws RadowException, AppException {

		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String type = this.getPageElement("tableType").getValue();
		StringBuffer a0000 = new StringBuffer();
		String firstCheckName = "";
		String tipStr = "";
		//列表
		if("1".equals(type)){
			/*//判断是否点击全部人员
			Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					this.setMainMessage("请先选中要删除的人员");
					return EventRtnType.FAILD;
				}
				if(StringUtil.isEmpty(allSelect)){
					this.setMainMessage("请先选中要删除的人员");
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
						a0000.append("'").append(map.get("a0000")==null?"":map.get("a0000").toString()).append("',");//被勾选的人员编号组装，用“，”分隔
						countNum++;
					}
				}
				if(countNum==0){
					//throw new AppException("请勾选人员！");
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(countNum == 1){
					tipStr = firstCheckName;
				}else if(countNum > 1){
					tipStr = firstCheckName+"等"+countNum+"人";
				}
		}
		//小资料、照片
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
					//throw new AppException("请勾选人员！");
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				a0000 = new StringBuffer(a0000s);

				String[] a0000_arr = a0000s.split(",");
				int countNum = a0000_arr.length;

				if(countNum == 1){
					tipStr = firstCheckName;
				}else if(countNum > 1){
					tipStr = firstCheckName+"等"+countNum+"人";
				}
		}
//		else if(countNum>1){
//			throw new AppException("请仅勾选一人进行操作！");
//		}
		if(a0000==null || a0000.toString().trim().equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
			//throw new AppException("数据获取异常！");
		}


		/*this.setRadow_parent_data(a0000.toString());
	    this.openWindow("deletePersonWin", "pages.publicServantManage.DeletePersonPage");*/
		//this.getExecuteSG().addExecuteCode("$h.openWin('deletePersonWin','pages.publicServantManage.DeletePersonPage','人员删除',850,450,document.getElementById('a0000').value,ctxPath)");
		//System.out.println(a0000.toString());
		this.getPageElement("deleteTip").setValue(tipStr);

		String deleteA0000S = a0000.toString().replace("'", "^");
		this.getPageElement("deleteA0000S").setValue(deleteA0000S);		//删除的id

		//dialog_set("deleteconfirm1","您确定要删除"+tipStr+"吗？",a0000.toString().replace("'", "^"));
		this.getExecuteSG().addExecuteCode("$h2.confirm('系统提示：','您确定要删除"+tipStr+"吗？',240,function(id) { if('ok'==id){radow.doEvent('deleteconfirm1');}else{return false;}});");

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteconfirm1")
	public int deleteconfirm1(String a0000)throws AppException, RadowException{
		String tipStr = this.getPageElement("deleteTip").getValue();
		//dialog_set("deleteconfirm","请再次确认您要删除"+tipStr+"吗？",a0000);

		this.getExecuteSG().addExecuteCode("$h2.confirm('系统提示：','请再次确认您要删除"+tipStr+"吗？',240,function(id) { if('ok'==id){radow.doEvent('deleteconfirm');}else{return false;}});");
		return EventRtnType.NORMAL_SUCCESS;
	}

	private void dialog_set(String fnDelte, String strHint,String a0000){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName(fnDelte);
		ne.setNextEventParameter(a0000);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage(strHint); // 窗口提示信息
	}

	//删除事件
		@PageEvent("deleteconfirm")
		@Transaction
		public int deleteconfirm(String peopleId)throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException{
			peopleId = this.getPageElement("deleteA0000S").getValue();

			//获取被勾选的人员编号
			String sa0000 = "";
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
						//判断是否有删除权限。c.type：机构权限类型(0：浏览，1：维护)
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
			/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
						String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
						if(type == null || !type.contains("'")){
							type ="'zz'";//替换垃圾数据
						}
						List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
						if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
							continue;
						}
						if(elist2==null||elist2.size()==0){//维护权限
							if(elist!=null&&elist.size()>0){//有浏览权限
								continue;
							}else{
								//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
								if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
						throw new AppException("所选人员不可操作！");
					}
					sa0000 = sb.substring(0, sb.length() - 1);
				} else {
					throw new AppException("请先选中要删除的人员！");
				}
			}else{
				//将最后面的逗号截掉
				String a0 = a0000s.substring(0,a0000s.length()-1);
				String[] s =  a0.split(",");
				for(String str : s){

					//system可以直接删除无职务人员，无职务人员判定方式为：a02无记录，或任职机构全部为其他单位（-1）
					if(PrivilegeManager.getInstance().getCueLoginUser().getId() != null && PrivilegeManager.getInstance().getCueLoginUser().getId().equals("40288103556cc97701556d629135000f")){
						//当前用户为system,判断是否为无职务人员

						String sql = "select 1 from  a02 where a0000 = "+str+" and a0201b != '-1'";		//查询a02是否有非其他单位的职务记录
						List a02List = sess.createSQLQuery(sql).list();

						if(a02List.size() < 1){		//如果没有非其他单位的任职记录，则此人为无职务人员，可以直接删除
							sa0000 = sa0000 + str + ",";
							continue;
						}

					}


					A01 a01 = (A01)sess.get(A01.class, str.replace("'", ""));
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000="+str+" and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' ";
					List elist = sess.createSQLQuery(editableSQL).list();   //是否有浏览权限，有值则表示：有浏览权限(目前根据权限的设计，浏览权限判断无意义，以下程序将不对他进行判断，2017-9-20)
					List elist2 = sess.createSQLQuery(editableSQL2).list();	//是否有维护权限，有值则表示：有维护权限
		/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){		//如果type为空，则表示当前用户有：管理类别维护权限
						type ="'zz'";//替换垃圾数据，让下面的elist3查询无结果，表示此用户有当前操作人员的管理类别维护权限
					}

					//elist3有值则表示，当前用户没有操作此人员的管理类别维护权限，即没有权限修改
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000="+str+" and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
						continue;
					}
					if(elist2==null||elist2.size()==0){//无维护权限
						/*if(elist!=null&&elist.size()>0){//有浏览权限

						}else{
							//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员

							}else {
								sa0000 = sa0000 + str + ",";
							}

						}*/


					}else {		//有维护权限
						sa0000 = sa0000 + str + ",";
					}
				}
				if(sa0000.length() > 0){
					sa0000 = sa0000.substring(0, sa0000.length() - 1);
				}
			}
			//判断：如果有值被选中
			if(!"".equals(sa0000) && sa0000 != null){

					//a01.setStatus("0");//修改完全删除
					//sess.saveOrUpdate(a01);
					deletePerson(sa0000);
					this.setMainMessage("删除成功！");
					//sess.flush();
					//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('delete');");


			}else{
				throw new AppException("所选人员不可操作！");
			}
			String tableType = this.getPageElement("tableType").getValue();
			//列表
			if("1".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("reloadGrid();");
				this.getExecuteSG().addExecuteCode("document.getElementById('checkList').value = '';");
			}
			//小资料
			if("2".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("datashow();");
			}
			//TODO
			//照片
			if("3".equals(tableType)){
				//this.closeCueWindow("deletePersonWin");
				this.getExecuteSG().addExecuteCode("picshow();");
			}

			return EventRtnType.NORMAL_SUCCESS;

		}

		private void deletePerson(String a0000) throws AppException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
			List<A01> a01_list = (List<A01>) HBUtil.getHBSession().createQuery("from A01 where a0000 in("+a0000+")").list();

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
			Statement stmt=null;
			Connection conn = HBUtil.getHBSession().connection();
			try{
				conn.setAutoCommit(false);
				stmt = conn.createStatement();

				//String a0000 = a01.getA0000();
				stmt.executeUpdate("delete from a02 where a0000 in("+a0000+")");
				stmt.executeUpdate("delete from a05 where a0000 in("+a0000+")");
				stmt.executeUpdate("delete from a06 where a0000 in("+a0000+")");
				stmt.executeUpdate("delete from a08 where a0000 in("+a0000+")");
				
				stmt.executeUpdate("delete from a15 where a0000 in("+a0000+")");
				stmt.executeUpdate("delete from a36 where a0000 in("+a0000+")");
				
				try {
					stmt.executeUpdate("delete from a14 where a0000 in("+a0000+")");
					stmt.executeUpdate("delete from a11 where a1100 in(select a1100 from a41 where a0000 in ("+a0000+"))");
					stmt.executeUpdate("delete from a41 where a0000 in ("+a0000+")");
				} catch (Exception e) {    
				}
				

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

				stmt.executeUpdate("delete from FOLDER where a0000 in("+a0000+")");			//电子档案文件信息
				stmt.executeUpdate("delete from FOLDERTREE where a0000 in("+a0000+")");		//电子档案文件夹tree信息
				stmt.executeUpdate("delete from A99Z1 where a0000 in("+a0000+")");			//补充信息
				stmt.executeUpdate("delete from verify_error_list where vel002 in("+a0000+")");		//人员校验信息

				conn.commit();
			}catch(Exception e){
				conn.rollback();
				throw new AppException("数据库处理异常！",e);
			}finally{
				if (stmt != null){
					try {
						stmt.close();
					} catch (SQLException e) {
						throw new AppException("数据库处理异常！", e);
					}
				}

			}

			for(int i=0;i<a01_list.size();i++){

				new LogUtil("33", "A01", a01_list.get(i).getA0000(), a01_list.get(i).getA0101(), "删除记录", new ArrayList()).start();

				//获得当前登陆用户的id和名称
				UserVO user = PrivilegeManager.getInstance().getCueLoginUser();

				String logMsg = "id为："+a01_list.get(i).getA0000()+"  姓名为："+ a01_list.get(i).getA0101()+"的人员被删除！  删除人id为："+user.getId()+"  账号为："+user.getName();

				//将人员删除记录记录到tomcat日志中
				log.info(logMsg);
			}

		}

	//删除成功
	@PageEvent("delete")
	public int delete() throws RadowException {
	    this.setMainMessage("删除成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 私有方法，是否选中用户
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
			return ON_ONE_CHOOSE;// 没有选中任何用户
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// 选中多于一个用户
		}
		if (result == 2) {
			return 1;// 选中多于一个用户
		}
		return number;// 选中第几个用户
	}

	//干部画像
	@PageEvent("PortraitBtn.onclick")
	@GridDataRange
	public int Portrait() throws RadowException, AppException{
		String sqlf = this.getPageElement("sql").getValue();
		if (sqlf.equals("")) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//列表
		if ("1".equals(tableType)) {
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			List<HashMap<String, Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
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
				//this.setMainMessage("请勾选人员！");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;

			}else if(countNum>1){

				//this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅勾选一人进行操作！',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//小资料、照片
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
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("请仅勾选一人进行操作！");

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000 = a0000s;
		}

		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getPageElement("a0000").setValue(a0000);
			//this.getExecuteSG().addExecuteCode("$h.openWin('jdinformation','pages.publicServantManage.JdInformation','监督信息',990,460,'"+a0000+"',ctxPath)");
			this.getExecuteSG().addExecuteCode("peoplePortrait();");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}


	}

	    //干部材料
		@PageEvent("PersionFileBtn.onclick")
		@GridDataRange
		public int persionFile() throws RadowException, AppException{  //打开窗口的实例
			String sqlf = this.getPageElement("sql").getValue();
			if (sqlf.equals("")) {
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}


			String tableType = this.getPageElement("tableType").getValue();
			String a0000 = null;
			//列表
			if ("1".equals(tableType)) {
				/*Object obj = this.getPageElement("checkAll").getValue();
				if("1".equals(obj)){
					throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
				}*/
				List<HashMap<String, Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
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
					//this.setMainMessage("请勾选人员！");
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;

				}else if(countNum>1){

					//this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅勾选一人进行操作！',null,180);");
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a0000==null || a0000.trim().equals("")){

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//小资料、照片
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
					throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
				}*/
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}

				a0000s = a0000s.substring(0, a0000s.length()-1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");
				if(str.length>1){
					//throw new AppException("请仅勾选一人进行操作！");

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}

				a0000 = a0000s;
			}

			//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
			A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
			if(ac01!=null){
				this.request.getSession().setAttribute("personIdSet", null);
				//this.getExecuteSG().addExecuteCode("$h.openWin('persionfile','pages.publicServantManage.PersionFile','干部材料',1080,600,'"+a0000+"',ctxPath)");
				this.getPageElement("a0000").setValue(a0000);
				this.getExecuteSG().addExecuteCode("openPersionFile()");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				throw new AppException("该人员不在系统中！");
			}
		}

	//监督信息
	@PageEvent("jdinformationBtn.onclick")
	@GridDataRange
	public int jdinformation() throws RadowException, AppException{  //打开窗口的实例
		String sqlf = this.getPageElement("sql").getValue();
		if (sqlf.equals("")) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		String tableType = this.getPageElement("tableType").getValue();
		String a0000 = null;
		//列表
		if ("1".equals(tableType)) {
			/*Object obj = this.getPageElement("checkAll").getValue();
			if("1".equals(obj)){
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			List<HashMap<String, Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
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
				//this.setMainMessage("请勾选人员！");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;

			}else if(countNum>1){

				//this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅勾选一人进行操作！',null,180);");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(a0000==null || a0000.trim().equals("")){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//小资料、照片
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
				throw new AppException("任免表修改不支持全选操作,请勾选一人进行操作！");
			}*/
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");
			if(str.length>1){
				//throw new AppException("请仅勾选一人进行操作！");

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请仅选择一条记录操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			a0000 = a0000s;
		}

		//String a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			this.getExecuteSG().addExecuteCode("$h.openWin('jdinformation','pages.publicServantManage.JdInformation','监督信息',990,460,'"+a0000+"',ctxPath)");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}


	}

	//批量修改
	@PageEvent("betchModifyBtn.onclick")
	@GridDataRange
	public int betchModify() throws RadowException, AppException{  //打开窗口的实例
		HBSession sess = HBUtil.getHBSession();
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String tableType = this.getPageElement("tableType").getValue();
		//列表
		if("1".equals(tableType)){
			betchModifyPageModel.type=1;

			CommonQueryBS.systemOut("value-------"+this.getPageElement("checkAll"));
			int i = choose("peopleInfoGrid","personcheck");
			if (i == ON_ONE_CHOOSE ) {
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
				List allSelect = sess.createSQLQuery(sql).list();
				if (!(allSelect.size() > 0)) {
					/*this.setMainMessage("请先进行人员查询！");
					return EventRtnType.FAILD;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(allSelect.size() > 500){
					/*this.setMainMessage("人员过多，请一次选择少于500人进行批量修改！");
					return EventRtnType.FAILD;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String param = sql+"@1";
				param = param.replaceAll("\\'", "\\$");
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量维护',990,500,'"+param+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				StringBuffer ids = new StringBuffer();
				PageElement pe = this.getPageElement("peopleInfoGrid");
				if(pe!=null){
					List<HashMap<String, Object>> list = pe.getValueList();
					if(list.size() > 500){
						/*this.setMainMessage("人员过多，请一次选择少于500人进行批量修改！");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					for(int j=0;j<list.size();j++){
						HashMap<String, Object> map = list.get(j);
						Object usercheck = map.get("personcheck");
						if(usercheck.equals(true)){
							//判断是否有修改权限。
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
				/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
							String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
							if(type == null || !type.contains("'")){
								type ="'zz'";//替换垃圾数据
							}
							List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
							if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
								continue;
							}
							if(elist2==null||elist2.size()==0){//维护权限
								/*if(elist!=null&&elist.size()>0){//有浏览权限
									continue;
								}else{
									//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
									if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
					/*this.setMainMessage("所选人员不可操作！");
					return EventRtnType.NORMAL_SUCCESS;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员不可操作！',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String allids = ids.substring(0,ids.length()-1);
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量修改',990,500,'"+allids+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		//小资料、照片
		if("2".equals(tableType) || "3".equals(tableType)){
			String a0000s = this.getPageElement("picA0000s").getValue();
			if(a0000s==null || "".equals(a0000s.trim())){
				this.getPageElement("picA0000s").setValue("");
				String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
				List allSelect = sess.createSQLQuery(sql).list();
				if (!(allSelect.size() > 0)) {

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(allSelect.size() > 500){

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String param = sql+"@1";
				param = param.replaceAll("\\'", "\\$");
				this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量修改',990,500,'"+param+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//判断是否有修改权限。
			a0000s = a0000s.substring(0, a0000s.length()-1);
			a0000s = a0000s.replaceAll("\\'", "");
			String[] str = a0000s.split(",");

			//批量修改人数不可超过500人
			if(str.length > 500){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			//HBSession sess = HBUtil.getHBSession();
			StringBuffer ids = new StringBuffer();
			for(String s : str){
				//判断是否有修改权限。
				A01 a01 = (A01)sess.get(A01.class, s);
				String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
						" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
						" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
				String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
				" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
				" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
				List elist = sess.createSQLQuery(editableSQL).list();
				List elist2 = sess.createSQLQuery(editableSQL2).list();
	/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
				String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
				if(type == null || !type.contains("'")){
					type ="'zz'";//替换垃圾数据
				}
				List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+s+"' and a01.a0165 in ("+type+")").list();
				if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
					continue;
				}
				if(elist2==null||elist2.size()==0){//维护权限
					if(elist!=null&&elist.size()>0){//有浏览权限
						continue;
					}else{
						//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
						if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
				/*this.setMainMessage("所选人员不可操作！");
				return EventRtnType.NORMAL_SUCCESS;*/

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员不可操作！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String allids = ids.substring(0,ids.length()-1);
			this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量维护',990,500,'"+allids+"',ctxPath)");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}



	//简历提取
		@PageEvent("getA17.onclick")
		@GridDataRange
		public int getA17() throws RadowException, AppException{  //打开窗口的实例
			HBSession sess = HBUtil.getHBSession();
			String sqlf = this.getPageElement("sql").getValue();
			if(sqlf.equals("")){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			String tableType = this.getPageElement("tableType").getValue();
			//列表
			if("1".equals(tableType)){
				betchModifyPageModel.type=1;

				CommonQueryBS.systemOut("value-------"+this.getPageElement("checkAll"));
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List allSelect = sess.createSQLQuery(sql).list();
					if (!(allSelect.size() > 0)) {
						/*this.setMainMessage("请先进行人员查询！");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}/*else if(allSelect.size() > 500){
						this.setMainMessage("人员过多，请一次选择少于500人进行批量修改！");
						return EventRtnType.FAILD;

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}*/
					String param = sql+"@1";
					param = param.replaceAll("\\'", "\\$");
					//this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量维护',990,460,'"+param+"',ctxPath)");
					this.getExecuteSG().addExecuteCode("$h.openPageModeWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+param+"',ctxPath,window)");
					//this.getExecuteSG().addExecuteCode("$h.openWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+param+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					StringBuffer ids = new StringBuffer();
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						if(list.size() > 1000){
							/*this.setMainMessage("人员过多，请一次选择少于500人进行批量修改！");
							return EventRtnType.FAILD;*/

							this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于1000人简历提取！',null,220);");
							return EventRtnType.NORMAL_SUCCESS;
						}
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								//判断是否有修改权限。
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
					/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
								String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
								if(type == null || !type.contains("'")){
									type ="'zz'";//替换垃圾数据
								}
								List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+a0000+"' and a01.a0165 in ("+type+")").list();
								if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
									continue;
								}
								if(elist2==null||elist2.size()==0){//维护权限
									/*if(elist!=null&&elist.size()>0){//有浏览权限
										continue;
									}else{
										//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
										if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
						/*this.setMainMessage("所选人员不可操作！");
						return EventRtnType.NORMAL_SUCCESS;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员不可操作！',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String allids = ids.substring(0,ids.length()-1);
					//this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量修改',990,380,'"+allids+"',ctxPath)");
					this.getExecuteSG().addExecuteCode("$h.openPageModeWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+allids+"',ctxPath,window)");
					//this.getExecuteSG().addExecuteCode("$h.openWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+allids+"',ctxPath)");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//小资料、照片
			if("2".equals(tableType) || "3".equals(tableType)){
				String a0000s = this.getPageElement("picA0000s").getValue();
				if(a0000s==null || "".equals(a0000s.trim())){
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"' order by sort";
					List allSelect = sess.createSQLQuery(sql).list();
					if (!(allSelect.size() > 0)) {

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
						return EventRtnType.NORMAL_SUCCESS;
					}else if(allSelect.size() > 500){

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
						return EventRtnType.NORMAL_SUCCESS;
					}
					String param = sql+"@1";
					param = param.replaceAll("\\'", "\\$");
					//this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量修改',990,380,'"+param+"',ctxPath)");
					this.getExecuteSG().addExecuteCode("$h.openPageModeWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+param+"',ctxPath,window)");
					return EventRtnType.NORMAL_SUCCESS;
				}
				//判断是否有修改权限。
				a0000s = a0000s.substring(0, a0000s.length()-1);
				a0000s = a0000s.replaceAll("\\'", "");
				String[] str = a0000s.split(",");

				//批量修改人数不可超过500人
				if(str.length > 500){
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请选择少于500人批量修改！',null,220);");
					return EventRtnType.NORMAL_SUCCESS;
				}

				//HBSession sess = HBUtil.getHBSession();
				StringBuffer ids = new StringBuffer();
				for(String s : str){
					//判断是否有修改权限。
					A01 a01 = (A01)sess.get(A01.class, s);
					String editableSQL = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
							" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
							" and c.type='0' and b.a0255='1' and a.status='1' and a.a0163='1'";
					String editableSQL2 = "select 1 from a01 a,a02 b,COMPETENCE_USERDEPT c where a.a0000=b.a0000 and " +
					" b.a0201b=c.b0111 and a.a0000='"+s+"' and c.userid='"+SysManagerUtils.getUserId()+"' " +
					" and c.type='1' and b.a0255='1' and a.status='1' and a.a0163='1'";
					List elist = sess.createSQLQuery(editableSQL).list();
					List elist2 = sess.createSQLQuery(editableSQL2).list();
		/*			判断该人员的管理类别浏览权限------------------------------------------------------------------------------------------------------*/
					String type = PrivilegeManager.getInstance().getCueLoginUser().getEmpid();
					if(type == null || !type.contains("'")){
						type ="'zz'";//替换垃圾数据
					}
					List elist3 = sess.createSQLQuery("select 1 from a01 where a01.a0000='"+s+"' and a01.a0165 in ("+type+")").list();
					if(elist3.size()>0){//无管理类别维护权限,即人员信息不可编辑
						continue;
					}
					if(elist2==null||elist2.size()==0){//维护权限
						if(elist!=null&&elist.size()>0){//有浏览权限
							continue;
						}else{
							//有两种情况：非现职人员，其他现职人员。非现职人员只能查看，不能编辑；其他现职人员可查看，可编辑
							if(a01.getA0163() != null && !a01.getA0163().equals("1")){		//非现职人员
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
					/*this.setMainMessage("所选人员不可操作！");
					return EventRtnType.NORMAL_SUCCESS;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员不可操作！',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String allids = ids.substring(0,ids.length()-1);
				//this.getExecuteSG().addExecuteCode("$h.openWin('betchModifyWin','pages.publicServantManage.betchModify','批量维护',990,460,'"+allids+"',ctxPath)");
				this.getExecuteSG().addExecuteCode("$h.openPageModeWin('getA17','pages.customquery.NotePickUp','简历提取',1350,1100,'"+allids+"',ctxPath,window)");
			}
			return EventRtnType.NORMAL_SUCCESS;
		}





	//任免表录入页面
	@PageEvent("loadadd.onclick")
	@NoRequiredValidate
	public int openUpdateWin(String id)throws RadowException{
		//this.setRadow_parent_data("add");
		//String random = UUID.randomUUID().toString();
		String a0000 = UUID.randomUUID().toString();
		this.request.getSession().setAttribute("nowNumber","");
		this.request.getSession().setAttribute("personIdSet", null);
		//this.getExecuteSG().addExecuteCode("addTab('新增人员窗口','addTab-"+random+"','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false)");
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息新增',851,630,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息新增',1009,645,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"&signTemp=add', '_blank', 'height='+(screen.height-90)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		//this.openWindow("addwin", "pages.publicServantManage.PersonAddTab");
		return EventRtnType.NORMAL_SUCCESS;
	}






	//查询按钮点击事件
	@PageEvent("btn1.onclick")
	public int selectAll() throws RadowException, PrivilegeException{
		this.request.getSession().setAttribute("queryType", "2");
		this.getPageElement("checkedgroupid").setValue("");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//事务提醒
	@PageEvent("warnWinBtn.onclick")
	public int warnWin() throws RadowException {
	    //this.openWindow("warnWin", "pages.publicServantManage.WarnWindow");
	    this.getExecuteSG().addExecuteCode("$h.openWin('warnWin','pages.publicServantManage.WarnWindow','事务提醒',450,300,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}



	//导出个人数据
	@PageEvent("importHzbBtn")
	public int expHzbWin(String gsType) throws RadowException, UnsupportedEncodingException {

		String tableType = this.getPageElement("tableType").getValue();
		StringBuffer ids = new StringBuffer();
		String hasQueried = this.getPageElement("sql").getValue();

		//列表
		if("".equals(hasQueried) || hasQueried==null){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "";
		if("1".equals(tableType)){
			//Object obj = this.getPageElement("checkAll").getValue();
			int all = choose("peopleInfoGrid","personcheck");

			if(all == ON_ONE_CHOOSE){		//列表没有勾选，则操作全部
				//String allSelect = (String)this.request.getSession().getAttribute("allSelect");
				/*int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {
					this.setMainMessage("请先选中要导出的人员");
					return EventRtnType.FAILD;
				}*/
				/*if(StringUtil.isEmpty(allSelect)){
					this.setMainMessage("请先选中要导出的人员");
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
					/*this.setMainMessage("请先选中要导出的人员");
					return EventRtnType.FAILD;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
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
		//小资料、照片
		if("2".equals(tableType) || "3".equals(tableType)){
			String a0000s = this.getPageElement("picA0000s").getValue();  //为空，则操作全部
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
					/*this.setMainMessage("请先选中要导出的人员");
					return EventRtnType.FAILD;*/

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				ids = new StringBuffer(a0000s);
			}
		}

		//判断当前列表是否有人员
		if("".equals(sql) && ids.length() < 1){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String allids = "".equals(sql)?ids.substring(0,ids.length()-1):sql;
		String id = UUID.randomUUID().toString().replace("-", "");

		try {
			CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
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
			else if("zip".equals(gsType)){
				String zipid=UUID.randomUUID().toString().replace("-", "");
				try {
					KingbsconfigBS.saveImpDetailInit3(zipid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String padCons = this.getPageElement("padZipCons").getValue();
				String excelfile = this.getPageElement("excelfile").getValue();
				String excelPath = URLDecoder.decode(excelfile,"UTF-8");
				thr=new DataPadImpDBThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),padCons,excelPath,allids);
				new Thread(thr,"Thread_psnexp").start();
				this.setRadow_parent_data(zipid);
			    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+zipid+"',ctxPath)");
				return EventRtnType.NORMAL_SUCCESS;
			}
			new Thread(thr,"Thread_psnexp").start();
			this.setRadow_parent_data(id);
		    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+id+"',ctxPath)");


		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	//任免表导出
	@PageEvent("exportLrmxBtn")
	public int expWin() throws RadowException {
		/*int i = choose("peopleInfoGrid","personcheck");
		if (i == ON_ONE_CHOOSE ) {
			this.setMainMessage("请选择要导出的人员");
			return EventRtnType.FAILD;
		}*/
		String hasQueried = this.getPageElement("sql").getValue();
		if("".equals(hasQueried) || hasQueried==null){
			//this.setMainMessage("未进行过查询请先查询!");

			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		this.setNextEventName("export");
		//this.openWindow("expTimeWin", "pages.publicServantManage.ExpTimeWindow");
//	    this.getExecuteSG().addExecuteCode("$h.openWin('expTimeWin','pages.publicServantManage.ExpTimeWindow','任免表导出',450,300,document.getElementById('a0000').value,ctxPath)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//批量打印
//	@PageEvent("batchPrint.onclick")
//	public int batchPrintTime() throws RadowException, AppException{
//		int i = choose("peopleInfoGrid","personcheck");
//		if (i == ON_ONE_CHOOSE ) {
//			this.setMainMessage("请先查询人员信息，并勾选要批量打印的人员！");
//			return EventRtnType.FAILD;
//		}
//		this.openWindow("batchPrintTimeWin", "pages.publicServantManage.batchPrintTimeWindow");
//		//this.getExecuteSG().addExecuteCode("$h.openWin('batchPrintTimeWin','pages.publicServantManage.batchPrintTimeWindow','批量打印',600,250,document.getElementById('a0000').value,ctxPath)");
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	//导出Lrm
		@PageEvent("exportLrmBtn")
		@GridDataRange
		public int exportLrm() throws RadowException{
			String tableType = this.getPageElement("tableType").getValue();
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			//判断列表是否有数据
			List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

			if(list22.size() == 0){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			if("1".equals(tableType)){
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {			//列表没有勾选，则操作全部
					HBSession sess = HBUtil.getHBSession();
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();

							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrm")) {
//									x++;
//									name+=x;
//								}
								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							if(i==1) {
								infile=zippath.substring(0,zippath.length()-1)+"\\"+name+".lrmx";
							}else {
								infile =zippath.substring(0,zippath.length()-1)+".zip" ;
								SevenZipUtil.zip7z(zippath, infile, null);
							}
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}

					/*String a0000 = "";
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
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}

						}
						try {


							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);


							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}*/
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
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
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
//									int x=0;
//									while(FileUtil.checkFile(zippath+name+".lrm")) {
//										x++;
//										name+=x;
//									}
									//原模板为：xxx1.Lrmx,xxx12.Lrmx
									//20200506 hy
									//现修改为:xxx1.Lrmx,xxx2.Lrmx
									if(renameMap.containsKey(name)) {
										int num=renameMap.get(name);
										renameMap.remove(name);
										renameMap.put(name,(num+1));
										name=name+num;
									}else {
										renameMap.put(name, 1);
									}
									FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
									List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
									if(list15.size()>0){
										A57 a57 = list15.get(0);
										if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
											File f = new File(zippath + name+".pic");
											FileOutputStream fos = new FileOutputStream(f);
											File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
											if(f2.exists() && f2.isFile()){
												InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
												byte[] data = new byte[1024];
												while (is.read(data) != -1) {
													fos.write(data);
												}
												is.close();
											}
											fos.close();

										}
									}
//									String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//									p = rt.exec(cmdd);
//									p.waitFor();
								} catch (Exception e) {
									this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
								}
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//							String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//							p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
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
					HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
//					System.out.println(zippath);
					String infile ="";
					try {
//						int x=0;
//						while(FileUtil.checkFile(zippath+name+".lrm")) {
//							x++;
//							name+=x;
//						}

						//原模板为：xxx1.Lrmx,xxx12.Lrmx
						//20200506 hy
						//现修改为:xxx1.Lrmx,xxx2.Lrmx
						if(renameMap.containsKey(name)) {
							int num=renameMap.get(name);
							renameMap.remove(name);
							renameMap.put(name,(num+1));
							name=name+num;
						}else {
							renameMap.put(name, 1);
						}
						FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
						List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
						if(list15.size()>0){
							A57 a57 = list15.get(0);
							if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
								File f = new File(zippath + name+".pic");
								FileOutputStream fos = new FileOutputStream(f);
								File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
								if(f2.exists() && f2.isFile()){
									InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
//						String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//						p = rt.exec(cmdd);
//						p.waitFor();
//						String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//						p = Runtime.getRuntime().exec(cmd);
						SevenZipUtil.zip7z(zippath, infile, null);
					} catch (Exception e) {
						e.printStackTrace();
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				}
			}else if("2".equals(tableType) || "3".equals(tableType)){ //小资料、照片
				HBSession sess = HBUtil.getHBSession();
				String a0000s = this.getPageElement("picA0000s").getValue();

				if(a0000s == null || "".equals(a0000s.trim())){	//没有勾选 则导出全部

					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();

							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrm")) {
//									x++;
//									name+=x;
//								}
								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}

						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}else{			//按照勾选人员导出
					a0000s = a0000s.substring(0, a0000s.length()-1);
					a0000s = a0000s.replaceAll("\\'", "");
					String[] str = a0000s.split(",");
					if(str != null && str.length > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0; j < str.length; j++){
							String a0000 = str[j];
							String lrm = createLrmStr(a0000);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
//								System.out.println(zippath);
							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrm")) {
//									x++;
//									name+=x;
//								}
								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
											byte[] data = new byte[1024];
											while (is.read(data) != -1) {
												fos.write(data);
											}
											is.close();
										}
										fos.close();

									}
								}
//									String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
//									p = rt.exec(cmdd);
//									p.waitFor();
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
//							String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
//							p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
//					System.out.println(ids.toString());
					return EventRtnType.NORMAL_SUCCESS;
				}

			}
			return EventRtnType.NORMAL_SUCCESS;
		}

		//导出Pdf
		@PageEvent("exportPdfBtn.onclick")
		@GridDataRange
		public int exportPdf() throws RadowException{
			String tableType = this.getPageElement("tableType").getValue();
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				//this.setMainMessage("未进行过查询请先查询!");

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			//判断列表是否有数据
			List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

			if(list22.size() == 0){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}


			if("1".equals(tableType)){
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {					//列表没有勾选，则操作全部
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
								int x=0;
								while(FileUtil.checkFile(zippath+name+".lrm")) {
									x++;
									name+=x;
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
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
									int x=0;
									while(FileUtil.checkFile(zippath+name+".lrm")) {
										x++;
										name+=x;
									}
									FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
									List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
									if(list15.size()>0){
										A57 a57 = list15.get(0);
										if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
											File f = new File(zippath + name+".pic");
											FileOutputStream fos = new FileOutputStream(f);
											File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
											if(f2.exists() && f2.isFile()){
												InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
		//							String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
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
									this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
								}
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
		//					String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
		//					p = Runtime.getRuntime().exec(cmd);
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
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
						int x=0;
						while(FileUtil.checkFile(zippath+name+".lrm")) {
							x++;
							name+=x;
						}
						FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
						List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
						if(list15.size()>0){
							A57 a57 = list15.get(0);
							if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
								File f = new File(zippath + name+".pic");
								FileOutputStream fos = new FileOutputStream(f);
								File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
								if(f2.exists() && f2.isFile()){
									InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
		//				String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
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
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				}

			}else if("2".equals(tableType) || "3".equals(tableType)){
				HBSession sess = HBUtil.getHBSession();
				String a0000s = this.getPageElement("picA0000s").getValue();

				if(a0000s == null || "".equals(a0000s.trim())){	//没有勾选 则导出全部

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
								int x=0;
								while(FileUtil.checkFile(zippath+name+".lrm")) {
									x++;
									name+=x;
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}else{			//按照勾选人员导出
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
								int x=0;
								while(FileUtil.checkFile(zippath+name+".lrm")) {
									x++;
									name+=x;
								}
								FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
								List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
								if(list15.size()>0){
									A57 a57 = list15.get(0);
									if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
										File f = new File(zippath + name+".pic");
										FileOutputStream fos = new FileOutputStream(f);
										File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
										if(f2.exists() && f2.isFile()){
											InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							infile =zippath.substring(0,zippath.length()-1)+".zip" ;
							SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
				}
			}
			return EventRtnType.NORMAL_SUCCESS;
		}

		//导出Lrmx
		@PageEvent("export")
		@GridDataRange
		public int exportLrmx(String time) throws RadowException{
			int i = choose("peopleInfoGrid","personcheck");
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				this.setMainMessage("未进行过查询请先查询!");
			}
			time = "";//去除导出时间窗口后默认时间为空，以后要恢复时注释即可
			String tableType = this.getPageElement("tableType").getValue();
			if("1".equals(tableType)){

				if (i == ON_ONE_CHOOSE ) {
					//没有选中操作全部
					HBSession sess = HBUtil.getHBSession();
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							PersonXml per = Parenthesisprocessing(a0000,time);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrmx")) {
//									x++;
//									name+=x;
//								}
								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+(j+1)+"_"+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							if(i==1) {
								infile=zippath.substring(0,zippath.length()-1)+"\\1_"+name+".lrmx";
							}else {
								infile =zippath.substring(0,zippath.length()-1)+".zip" ;
								SevenZipUtil.zip7z(zippath, infile, null);
							}
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
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
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						Runtime rt = Runtime.getRuntime();
						Process p = null;
						int x=1;
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
								PersonXml per = Parenthesisprocessing(a0000,time);
								name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
			//					System.out.println(zippath);
								try {
//									int x=0;
//									while(FileUtil.checkFile(zippath+name+".lrmx")) {
//										x++;
//										name+=x;
//									}
									//原模板为：xxx1.Lrmx,xxx12.Lrmx
									//20200506 hy
									//现修改为:xxx1.Lrmx,xxx2.Lrmx
									if(renameMap.containsKey(name)) {
										int num=renameMap.get(name);
										renameMap.remove(name);
										renameMap.put(name,(num+1));
										name=name+num;
									}else {
										renameMap.put(name, 1);
									}
									FileUtil.createFile(zippath+x+"_"+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
									x++;
			//						String cmdd = "\"D:\\Program Files (x86)\\任免表编辑器V3.0\\ZZBRMBService.exe\" "+zippath+name+".lrm"+" "+zippath;
			//						p = rt.exec(cmdd);
			//						p.waitFor();
									A01 a01log = new A01();
									new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
								} catch (Exception e) {
									this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
								}
							}
						}
						try {
							if(i==1) {
								infile=zippath.substring(0,zippath.length()-1)+"\\1_"+name+".lrmx";
							}else {
								infile =zippath.substring(0,zippath.length()-1)+".zip" ;
								//				String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
								//				p = Runtime.getRuntime().exec(cmd);
												SevenZipUtil.zip7z(zippath, infile, null);
							}

							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
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
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j=0;j<list.size();j++){
							HashMap<String, Object> map = list.get(j);
							Object usercheck = map.get("personcheck");
							if(usercheck.equals(true)){
								a0000=this.getPageElement("peopleInfoGrid").getValue("a0000",j).toString();
								PersonXml per = Parenthesisprocessing(a0000,time);
								name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
				//					System.out.println(zippath);
								try {
//									int x=0;
//									while(FileUtil.checkFile(zippath+name+".lrmx")) {
//										x++;
//										name+=x;
//									}
									//原模板为：xxx1.Lrmx,xxx12.Lrmx
									//20200506 hy
									//现修改为:xxx1.Lrmx,xxx2.Lrmx
									if(renameMap.containsKey(name)) {
										int num=renameMap.get(name);
										renameMap.remove(name);
										renameMap.put(name,(num+1));
										name=name+num;
									}else {
										renameMap.put(name, 1);
									}
									FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
									A01 a01log = new A01();
									new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
								} catch (Exception e) {
									this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
								}
							}
						}
						try {

							infile =zippath+name+".lrmx" ;
							//SevenZipUtil.zip7z(zippath, infile, null);
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}

				}
			}else if("2".equals(tableType) || "3".equals(tableType)){
				HBSession sess = HBUtil.getHBSession();
				String a0000s = this.getPageElement("picA0000s").getValue();

				if(a0000s == null || "".equals(a0000s.trim())){	//没有勾选 则导出全部

					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							PersonXml per = Parenthesisprocessing(a0000,time);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrmx")) {
//									x++;
//									name+=x;
//								}

								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							if(i==1) {
								infile=zippath.substring(0,zippath.length()-1)+"\\"+name+".lrmx";
							}else {
								infile =zippath.substring(0,zippath.length()-1)+".zip" ;
								//String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
								//p = Runtime.getRuntime().exec(cmd);
								SevenZipUtil.zip7z(zippath, infile, null);
							}
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
					return EventRtnType.NORMAL_SUCCESS;
				}else{			//按照勾选人员导出
					a0000s = a0000s.substring(0, a0000s.length()-1);
					a0000s = a0000s.replaceAll("\\'", "");
					String[] str = a0000s.split(",");
					if(str != null && str.length > 0){
						String zippath =ExpRar.expFile();
						String infile ="";
						String name ="";
						HashMap<String, Integer> renameMap = new HashMap<String,Integer>();
						for(int j = 0; j < str.length; j++){
							String a0000 = str[j];
							PersonXml per = Parenthesisprocessing(a0000,time);
							name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
							try {
//								int x=0;
//								while(FileUtil.checkFile(zippath+name+".lrmx")) {
//									x++;
//									name+=x;
//								}

								//原模板为：xxx1.Lrmx,xxx12.Lrmx
								//20200506 hy
								//现修改为:xxx1.Lrmx,xxx2.Lrmx
								if(renameMap.containsKey(name)) {
									int num=renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name,(num+1));
									name=name+num;
								}else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath+name+".lrmx", JXUtil.Object2Xml(per,true), false, "UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
						try {
							if(i==1) {
								infile=zippath.substring(0,zippath.length()-1)+"\\"+name+".lrmx";
							}else {
								infile =zippath.substring(0,zippath.length()-1)+".zip" ;
								//String cmd="cmd /c start d:\\Program Files (x86)\\7-Zip\\7z.exe a -tzip "+infile+ " "+zippath+"\\*";
								//p = Runtime.getRuntime().exec(cmd);
								SevenZipUtil.zip7z(zippath, infile, null);
							}
							this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
							this.getExecuteSG().addExecuteCode("window.downloadword()");
						} catch (Exception e) {
							this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
						}
					}
				}
			}
//				System.out.println(ids.toString());
			return EventRtnType.NORMAL_SUCCESS;
		}

	// 转译特殊符号
	public PersonXml Parenthesisprocessing(String a0000, String time) {
		PersonXml per = createLrmxStr(a0000, time);
		if (per.getJianLi() != null && per.getJianLi() != "") {
			per.setJianLi(per.getJianLi().replaceAll("&lt;", "&1lt;"));
			per.setJianLi(per.getJianLi().replaceAll("&gt;", "&1gt;"));
			per.setJianLi(per.getJianLi().replaceAll("&amp;", "&1amp;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null && per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		return per;
	}

/**
 * 生成Lrm文件，默认打印拟任免信息
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
 * 生成Lrm文件
 * @param ids 人员ID a0000
 * @param falg 是否打印拟任免信息：true-打印拟任免信息
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
		str = (String) HBUtil.getHBSession().createSQLQuery("select to_char('\"'||t.a0101||'\",\"'||( select code_name from code_value where code_value = t.a0104 and code_type = 'GB2261' and code_status = '1')||'\",\"'||substr(T.A0107,0,6)||'\",\"'||"
				+"( select code_name from code_value where code_value = t.A0117 and code_type = 'GB3304' and code_status = '1')||'\",\"'||T.A0111A||'\",\"'||replace(replace(t.a0140, '(', ''), ')', '')||'\",\"'||"
				+"t.a0128||'\",\"'|| T.a0114A||'\",\"'|| substr(T.a0134,0,6)||'\",\"'||"
				+"t.qrzxl||'#'||t.qrzxw||'@'||t.zzxl||'#'||t.zzxw||"
				+"'\",\"'||t.qrzxlxx||'#'||t.qrzxwxx||'@'||t.zzxlxx||'#'||t.zzxwxx||'\",\"'||"
				+"T.A0196||'\",\"\",\"\",\"\",\"\",\"'||t.a0187a||'\",\"'||"
				+"t.A1701||'\",\"'||(case t.A14z101  when '无' then '(无)' else nvl(t.A14z101,'(无)') end)||'\",\"'||nvl(t.A15Z101,'(无)')||'\"')"
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
	//是否打印拟任免信息
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
	String sqla36 = "from A36 where a0000='"+a0000+"' order by sortid,"
					+ "case " +
					"        when A3604A='丈夫' or A3604A='妻子' then 1 " +
					"        when A3604A='儿子' or A3604A='女儿'or A3604A='长女'or A3604A='长子' or A3604A='次女' or A3604A='次子' or A3604A='三女' or A3604A='三子' or A3604A='四女' or A3604A='四子' or A3604A='五女' or A3604A='五子' or A3604A='其他女儿' or A3604A='其他子' then 2 " +
					"        when A3604A='父亲'  then 3 " +
					"        when A3604A='母亲'  then 4 " +
					"        when A3604A='继父'  then 5 " +
					"        when A3604A='继母'  then 6 " +
					"      end   , " +
		 			" case "+
		            "    when "+
		            "      A3604A='儿子' or A3604A='女儿' or A3604A='长女'  or "+
		            "      A3604A='长子' or A3604A='次女' or A3604A='次子'  or "+
		            "      A3604A='三女' or A3604A='三子' or A3604A='四女'  or "+
		            "      A3604A='四子' or A3604A='五女' or A3604A='五子'  or "+
		            "      A3604A='其他女儿' or A3604A='其他子' "+
			        "    then   "+
			        "      to_number(GETAGE(A3607)) "+
		            "    end"+
		            "        desc";
	List lista36 = sess.createQuery(sqla36).list();
	String userid = SysUtil.getCacheCurrentUser().getId();
	A01 a01 = (A01)sess.get(A01.class, a0000);
	a01.setQrzxlxx(a01.getQrzxlxx()==null?"":a01.getQrzxlxx());
	if(a01.getQrzxlxx().equals(a01.getQrzxwxx())) {
		a01.setQrzxwxx(null);
	}
	A57 a57 = (A57)sess.get(A57.class, a0000);
	String sqla53 = "from A53 where a0000='"+a0000+"' and a5399='"+userid+"'";
	//List<A53> list = sess.createQuery(sqla53).list();
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
	a.setChuShengNianYue(a01.getA0107().length()>6?a01.getA0107().substring(0, 6):a01.getA0107());
	a.setMinZu(mz!=null?mz.toString():"");
	a.setJiGuan(a01.getComboxArea_a0111());
	a.setChuShengDi(a01.getComboxArea_a0114());
	a.setRuDangShiJian(a01.getA0140());
	a.setCanJiaGongZuoShiJian(a01.getA0134().length()>6?a01.getA0134().substring(0, 6):a01.getA0134());
	a.setJianKangZhuangKuang(a01.getA0128());
	a.setZhuanYeJiShuZhiWu(a01.getA0196());
	a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
	a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
	a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
	a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
	if(a01.getQrzxlxx()!=null&&a01.getQrzxlxx().equals(a01.getQrzxwxx())) {
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi("");
	}else {
		a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
	}
	a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
	a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
	a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
	if(a01.getZzxlxx()!=null&&a01.getZzxlxx().equals(a01.getZzxwxx())) {
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi("");
	}else {
		a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
	}
	a.setXianRenZhiWu(a01.getA0192a());
	a.setJianLi(a01.getA1701());
	a.setTianBiaoRen(a01.getCbdw()!= null ? a01.getCbdw() : "");
	/*
	 * hy
	 * 据要求，无果为空，改为(无)
	 */
	if(a01.getA14z101()==null||a01.getA14z101().equals("无")||a01.getA14z101().isEmpty())
		a.setJiangChengQingKuang("(无)");
	else
		a.setJiangChengQingKuang(a01.getA14z101());
	if(a01.getA15z101()==null||a01.getA15z101().isEmpty())
		a.setNianDuKaoHeJieGuo("(无)");
	else
		a.setNianDuKaoHeJieGuo(a01.getA15z101());
	a.setShenFenZheng(a01.getA0184());
//	if(list==null||list.size()==0){
//		a.setNiRenZhiWu("");
//		a.setNiMianZhiWu("");
//		a.setRenMianLiYou("");
//		a.setChengBaoDanWei("");
//		a.setJiSuanNianLingShiJian("");
//		a.setTianBiaoShiJian(time);
//		a.setTianBiaoRen("");
//	}else{
//		List lista53 = sess.createQuery(sqla53).list();
//		A53 a53 = (A53)lista53.get(0);
//		a.setNiRenZhiWu(a53.getA5304());
//		a.setNiMianZhiWu(a53.getA5315());
//		a.setRenMianLiYou(a53.getA5317());
//		a.setChengBaoDanWei(a53.getA5319());
//		a.setJiSuanNianLingShiJian("");
//		if(StringUtil.isEmpty(time)){
//			a.setTianBiaoShiJian(a53.getA5323());
//		}else{
//		a.setTianBiaoShiJian(time);
////		}
//		a.setTianBiaoRen("");
//	}
	if(lista36!=null&&lista36.size()>0){
		for(int i=1;i<=lista36.size();i++){
			ItemXml b = new ItemXml();
			A36 a36 = (A36) lista36.get(i - 1);
			//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
			b.setChengWei(a36.getA3604a() != null ? a36.getA3604a() : "");
			b.setChuShengRiQi(lengthToSix(a36.getA3607()));
			b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
			b.setXingMing(a36.getA3601());
			//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
			b.setZhengZhiMianMao(a36.getA3627() != null ? a36.getA3627() : "");
			itemlist.add(b);
		}
	}
	jiaTingChengYuanXml.setItem(itemlist);
	jtcyList.add(jiaTingChengYuanXml);
	a.setJiaTingChengYuan(jtcyList);
	return a;
}

private String lengthToSix(String str) {
	if(str!=null&&!"".equals(str)) {
		if(str.length()==8) {
			String dateStr = str.substring(0, 6);
			return dateStr;
		}else {
			return str;
		}
	}
	return "";
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
	a.setChuShengNianYue(a01.getA0107().length()>6?a01.getA0107().substring(0, 6):a01.getA0107());
	a.setMinZu(mz!=null?mz.toString():"");
	a.setJiGuan(a01.getComboxArea_a0111());
	a.setChuShengDi(a01.getComboxArea_a0114());
	a.setRuDangShiJian(a01.getA0140());
	a.setCanJiaGongZuoShiJian(a01.getA0134().length()>6?a01.getA0134().substring(0, 6):a01.getA0134());
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
	a.setTianBiaoRen(a01.getCbdw()!= null ? a01.getCbdw() : "");
	/*
	 * hy
	 * 据要求，如果为空，改为(无)
	 */
	if(a01.getA14z101()==null||a01.getA14z101().equals("无")||a01.getA14z101().isEmpty())
		a.setJiangChengQingKuang("(无)");
	else
		a.setJiangChengQingKuang(a01.getA14z101());
	if(a01.getA15z101()==null||a01.getA15z101().isEmpty())
		a.setNianDuKaoHeJieGuo("(无)");
	else
		a.setNianDuKaoHeJieGuo(a01.getA15z101());
	a.setShenFenZheng(a01.getA0184());

	List list = sess.createQuery(" from Js01 where a0000='"+a0000+"' and js0122='"+xt+"'").list();
//	if(list==null||list.size()==0){
//		a.setNiRenZhiWu("");
//		a.setNiMianZhiWu("");
//		a.setRenMianLiYou("");
//		a.setChengBaoDanWei("");
//		a.setJiSuanNianLingShiJian("");
//		a.setTianBiaoShiJian(time);
//		a.setTianBiaoRen("");
//	}else{
//		//List lista53 = sess.createQuery(sqla53).list();
//		Js01 js01 = (Js01)list.get(0);
//		a.setNiRenZhiWu(js01.getJs0111());
//		a.setNiMianZhiWu(js01.getJs0117());
//		a.setRenMianLiYou("组织选拔任用");
//		a.setChengBaoDanWei("无锡市委组织部");
//		a.setJiSuanNianLingShiJian("");
////		if(StringUtil.isEmpty(time)){
////			a.setTianBiaoShiJian(a53.getA5323());
////		}else{
//		a.setTianBiaoShiJian(time);
////		}
//		a.setTianBiaoRen("");
//	}
	if(lista36!=null&&lista36.size()>0){
		for(int i=1;i<=lista36.size();i++){
			ItemXml b = new ItemXml();
			A36 a36 = (A36) lista36.get(i - 1);
			//Object cw = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4761' and t.code_value = '"+a36.getA3604a()+"'").uniqueResult();
			b.setChengWei(a36.getA3604a() != null ? a36.getA3604a() : "");
			b.setChuShengRiQi(a36.getA3607());
			b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
			b.setXingMing(a36.getA3601());
			//Object zzmm = HBUtil.getHBSession().createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB4762' and t.code_value = '"+a36.getA3627()+"'").uniqueResult();
			b.setZhengZhiMianMao(a36.getA3627() != null ? a36.getA3627() : "");
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
 * 打开PDF预览界面
 *
 * @param a0000AndFlag a0000 （人员ID）与 flag（是否打印拟任免信息）拼接的参数，用逗号分隔
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
	String a0000 = params[0];                                        //人员ID
	Boolean flag = params[1].equalsIgnoreCase("true");    //是否打印拟任免信息
	String pdfPath = "";                                            //pdf文件路径

	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		if ("pdf1.1".equals(pdfmenu)) {
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list, "eebdefc2-4d67-4452-a973-5f7939530a11", "pdf1.1", a0000, list2, null);
			this.getPageElement("printPdf").setValue("");
		}else{
			pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2,null);
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
	this.getExecuteSG().addExecuteCode("$h.openPageModeWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
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
	this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,'"+a0000+"',ctxPath)");
	//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+pdfPath+"')");
*/	return EventRtnType.NORMAL_SUCCESS;
}


@PageEvent("printViewNew")
public int pdfViewNew(String a0000AndFlag) throws RadowException, AppException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0];                                        //人员ID
	Boolean flag = params[1].equalsIgnoreCase("true");    //是否打印拟任免信息
	String pdfPath = "";                                            //pdf文件路径

	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		if ("pdf1.1".equals(pdfmenu)) {
			try {
				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf1.1",a0000,list2,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.getPageElement("printPdf").setValue("");
		}else{
			try {
				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
/*	//直接打印  不用打开pdf预览界面  2019.12.04 yzk
	 File f = new File(pdfPaths.get(0));
	    if (!f.exists()) {
	      this.setMainMessage("文件不存在！");
	      return EventRtnType.NORMAL_SUCCESS;
	    }
	    File fa[] = f.listFiles();
		for (int i = 0; i < fa.length; i++) {
			File fs = fa[i];
			if (fs.isDirectory()) {
				System.out.println(fs.getName() + " [目录]");
			} else {
				//printFile(wordPaths.get(0) + fs.getName(), printer);
				String path = pdfPaths.get(0) + fs.getName();
				String rootPath = PhotosUtil.getRootPath();
				path = path.replace(rootPath, "").replace("\\", "/");
				this.getExecuteSG().addExecuteCode("printStart('"+path+"');");
			}
		}
	*/







		  String newPDFPath =
		  ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".Pdf";
		  QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath); newPDFPath =
		  newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		  newPDFPath = "/hzb"+ newPDFPath;
		  this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
		  this.getPageElement("pdfPath").setValue(newPDFPath); String ctxPath =
		  request.getContextPath();



	//this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
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
	this.getExecuteSG().addExecuteCode("$h.openWin('pdfViewWin','pages.publicServantManage.PdfView','打印任免表',700,500,'"+a0000+"',ctxPath)");
	//this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+pdfPath+"')");
*/	return EventRtnType.NORMAL_SUCCESS;
}


@PageEvent("prtRmb_print")
public int prtRmb_print(String a0000AndFlag) throws RadowException, AppException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0];                                        //人员ID
	Boolean flag = params[1].equalsIgnoreCase("true");    //是否打印拟任免信息
	String pdfPath = "";                                            //pdf文件路径

	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		if ("pdf1.1".equals(pdfmenu)) {
			try {
				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","pdf1.1",a0000,list2,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.getPageElement("printPdf").setValue("");
		}else{
			try {
				pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,"eebdefc2-4d67-4452-a973-5f7939530a11","word",a0000,list2,null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} catch (AppException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//直接打印  不用打开pdf预览界面  2019.12.04 yzk
	File f = new File(pdfPaths.get(0));
	if (!f.exists()) {
		this.setMainMessage("文件不存在！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	File[] fa = f.listFiles();
	for (int i = 0; i < fa.length; i++) {
		File fs = fa[i];
		if (fs.isDirectory()) {
			System.out.println(fs.getName() + " [目录]");
		} else {
			//printFile(wordPaths.get(0) + fs.getName(), printer);
			String path = pdfPaths.get(0) + fs.getName();
			String rootPath = PhotosUtil.getRootPath();
			path = path.replace(rootPath, "").replace("\\", "/");
			this.getExecuteSG().addExecuteCode("printStart('" + path + "');");
			}
		}




		this.setMainMessage("打印完成");
		return EventRtnType.NORMAL_SUCCESS;
}




/*
 * 任免表导出套改表
 */
@PageEvent("prtTG")
public int prtTG(String a0000AndFlag) throws RadowException, AppException, IOException{
	String[] params = a0000AndFlag.split(",");
	String a0000 = params[0];                                        //人员ID
	Boolean flag = params[1].equalsIgnoreCase("true");    //是否打印拟任免信息
	String pdfPath = "";                                            //pdf文件路径

	List<String> list = new ArrayList<String>();
	List<String> list2 = new ArrayList<String>();
	list.add(a0000);
	List<String> pdfPaths = null;
	String pdfmenu = this.getPageElement("printPdf").getValue();
	try {
		pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list, "28bc4d39-dccd-4f07-8aa9", "pdf", a0000, list2, null);
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
 * 对勾选的人员批量打印前置验证
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
		this.setMainMessage("请先查询人员信息，并勾选要批量打印的人员！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','打印任免表中是否包含拟任免信息？',200,function(id){" +
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
 * 对勾选的人员批量打印
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
		//this.setMainMessage("未进行过查询请先查询!");

		this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
		return EventRtnType.NORMAL_SUCCESS;
	}


	//判断列表是否有数据
	List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

	if(list22.size() == 0){
		this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
		return EventRtnType.NORMAL_SUCCESS;
	}

	boolean flagNrm =true;	//是否打印拟任免信息
	int i = choose("peopleInfoGrid","personcheck");
	if (i == ON_ONE_CHOOSE ) {
		String pdfPath = "";
		HBSession sess = HBUtil.getHBSession();
		String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
		List<String> list = sess.createSQLQuery(sql).list();
		String a0000 = "";
		if(list !=null && list.size() > 0){
			List<String> list2 = new ArrayList<String>();
			for(int j = 0;j < list.size(); j++){
				a0000 = list.get(j);
				list2.add(a0000);
			}
			String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2);
			String infile ="";
			try {
				if(i==1) {

					String name =name = (String) sess.createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
					infile =pdfPaths.substring(0,pdfPaths.length()-1)+"\\1_"+name+".pdf";
				}else{
				infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
				SevenZipUtil.zip7z(pdfPaths, infile, null);
				}
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.downloadword()");
			} catch (Exception e) {
				this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
			}
		}

		/*String pdfPath = "";
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
				if(i==1) {

					String name =getName(pe);
					infile =pdfPaths.substring(0,pdfPaths.length()-1)+"\\1_"+name+".pdf";
				}else{
				infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
				SevenZipUtil.zip7z(pdfPaths, infile, null);
				}
				this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.downloadword()");
			} catch (Exception e) {
				this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
			}

		}else{
			this.setMainMessage("请查询人员");
			return EventRtnType.NORMAL_SUCCESS;
		}*/
	}else{
		String printTime = "";
		String newPDFPath = "";
		List<HashMap<String, Object>> list = this.getPageElement(
				"peopleInfoGrid").getValueList();
		// 勾选的人员ID
		List<String> a0000s = new ArrayList<String>();
		String infile ="";
		for (HashMap<String, Object> map : list) {
			if (map != null&& map.get("personcheck").toString().equalsIgnoreCase("true")) {
					a0000s.add((String) map.get("a0000"));
				}
		}
		String pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(a0000s);
		try {
			if(i==1) {
					/*
					 * PageElement pe = this.getPageElement("peopleInfoGrid"); String name
					 * =getName(pe);
					 */
				String a0000 = a0000s.get(0);
				Map<String, Object> dataMap  = FiledownServlet.getMap(a0000);
				String a0101 = (String) dataMap.get("a0101");
				infile =pdfPaths.substring(0,pdfPaths.length()-1)+"\\1_"+a0101+".pdf";
			}else{
			infile =pdfPaths.substring(0,pdfPaths.length()-1)+".zip" ;
			SevenZipUtil.zip7z(pdfPaths, infile, null);
			}
			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.downloadword()");
		} catch (Exception e) {
			this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
		}
		System.out.println("目录路径是"+infile);
	}

	return EventRtnType.NORMAL_SUCCESS;
}

/**
 * 对勾选的人员批量打印
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
		//this.setMainMessage("未进行过查询请先查询!");

		this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
		return EventRtnType.NORMAL_SUCCESS;
	}


	//判断列表是否有数据
	List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

	if(list22.size() == 0){
		this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
		return EventRtnType.NORMAL_SUCCESS;
	}

	boolean flagNrm =true;	//是否打印拟任免信息
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
			this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+newPDFPath+"')");
		}else{
			this.setMainMessage("请查询人员");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}else{
		String printTime = "";
		String newPDFPath = "";
		List<HashMap<String,Object>> list =  this.getPageElement("peopleInfoGrid").getValueList();
		newPDFPath = new QueryPersonListBS().batchPrint(list,flagNrm,printTime);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.getExecuteSG().addExecuteCode("openPdfPage('pdfViewWin','任免表预览界面','"+newPDFPath+"')");
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
		this.setMainMessage("排序失败！");
		return EventRtnType.FAILD;
	}
	return EventRtnType.NORMAL_SUCCESS;
}

//事务提醒中查看按钮事件
@PageEvent("view")
public int view(String a0000s) throws RadowException, PrivilegeException{
	this.request.getSession().setAttribute("queryType", "3");
	this.getPageElement("viewValue").setValue(a0000s);
	this.setNextEventName("peopleInfoGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;
}

@PageEvent("queryByName")
public int queryByName(String name) throws RadowException, AppException{

	String name2 = "";

	String sqlName = "";
	if(name!=null&&!"输入姓名".equals(name)){
		name = name.replaceAll("，", ",").replaceAll("[\\t\\n\\r]", ",");

		//判断name是否包含","
		if(name.indexOf(",")>0){
			String[] names = name.split(",");
			String newName = "";
			for(String n : names){
				if(n==null||"".equals(n)){
					continue;
				}
				newName = newName + "'" + n + "',";
			}
			if(newName!=null){
				newName = newName.substring(0, newName.length()-1);
				name2 = newName.toUpperCase();
				sqlName = sqlName + " and (a01.a0184 in ("+newName+") or a01.a0101 in ("+newName+") or a01.A0102 in ("+name2+")) ";
			}
		}else{
			name2 = name.toUpperCase();
			String whr = "a01.a0101 like '%"+name+"%' or a01.A0102 like '%"+name2+"%'";
			if(name.contains("?")||name.contains("%")||name.contains("？")||name.contains("_")||name.contains("*")){																			//若存在数字视为查询身份证号
				whr = "a01.a0101 like '" + name.replace("?", "_").replace("？", "_").replace("*", "%") + "' or a01.a0101 like '" + name2.replace("?", "_").replace("？", "_").replace("*", "%") + "'";																//将查询身份证号
			}
			sqlName = sqlName + " and (a01.a0184 = '"+name+"' or "+whr+") ";
		}
	}
	/*String sql = this.getPageElement("sql").getValue();

	 sql = sql + " and a01.A0101 like '%"+name+"%'";*/

	//将name大写化
	//name = name.toUpperCase();


	//改为全库查询
	String sid = this.request.getSession().getId();
	String userID = SysManagerUtils.getUserId();
	String a0163 = this.getPageElement("personq").getValue();
	String isDisplay = "a02.a0281='true' and ";
	if("4".equals(a0163)) {
		isDisplay = (String) HBUtil.getHBSession().createSQLQuery("select AAA005 from AA01 where AAA001='ISDISPLAY'").uniqueResult();
		System.out.println("ISDISPLAY ="+isDisplay );
		if(isDisplay.equals("1")) //222.189服务器
		isDisplay="";
		else isDisplay="a02.a0281='true' and ";
	}
	String sql ="select a0000,a0163 from a01 where fkly is null and a01.a0000  in "
			+ "(select a02.a0000 from a02 where "+isDisplay+"" //职务信息中要输出状态
			+ " a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+userID+"'))";
	sql = sql + sqlName;


	//sql = sql + " and (a01.a0101 like '"+name+"%' or a01.A0102 like '"+name+"%') ";

	sql = sql + " union all " +
	"select a0000,a0163 from a01 where fkly is null and not exists (select 1 from a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
					+ " and a01.status!='4' ";
	sql = sql + sqlName;


	String wheresql="1=1";

	String personq=this.getPageElement("personq").getValue();//""为全部,1为现职，2为非现职
	if("1".equals(personq)){
		wheresql="a0163 like '1%'";
	}else if("2".equals(personq)){
		wheresql="a0163 like '2%'";
	}else if("3".equals(personq)||(personq==null||"".equals(personq))){
		wheresql=" 1 = 1 ";
	}
	System.out.println(CommSQL.getComSQL(sid)+" where a01.a0000  in ( select a0000 from("+sql+") cc where "+wheresql+" ) ");
	this.getPageElement("sql").setValue(CommSQL.getComSQL(sid)+" where a01.a0000  in ( select a0000 from("+sql+") cc where "+wheresql+" ) ");
	this.getPageElement("listSql").setValue("");
    //this.getPageElement("a0201b").setValue(id);
	this.setNextEventName("peopleInfoGrid.dogridquery");
	return EventRtnType.NORMAL_SUCCESS;
}

//自定义模板展示
@PageEvent("showtem")
public int showtem(String value) throws RadowException{
	this.request.getSession().setAttribute("tpid", value);
	this.request.getSession().setAttribute("personids", this.getPageElement("checkList").getValue());
	this.getExecuteSG().addExecuteCode("addTab('表格','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OtherTemShow',false,false)");
	return EventRtnType.NORMAL_SUCCESS;
}
//按姓名查询，传递人员IDs
@PageEvent("queryByNameAndIDS")
public int queryByNameAndIDS(String listStr) throws RadowException{
	//System.out.println(listStr);
	String sid = this.request.getSession().getId();
	StringBuffer sql = new StringBuffer();
	sql.append(CommSQL.getComSQL(sid)+" where a0000 in ('-1'");
	if(listStr!=null && listStr.length()>2){
		listStr = listStr.substring(1, listStr.length() - 1);
		String[] list = listStr.split(",");
		for (String id : list) {
			sql.append(",'" + id.trim() + "'");
		}
		sql.append(")");
		this.getPageElement("sql").setValue(sql.toString());
		//判断列表、小资料、照片
		String tableType = this.getPageElement("tableType").getValue();
		if ("1".equals(tableType)) {
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
		this.setMainMessage("无法查询到该人员！");
		return EventRtnType.NORMAL_SUCCESS;
	}
}

	@PageEvent("personq.onchange")
	public int change() throws RadowException{
		//List<HashMap<String,Object>> s = this.getPageElement("personQ").getValueList();
		//System.out.println(s);
		this.getPageElement("xzry").setValue("0");
		this.getPageElement("lsry").setValue("0");
		this.getPageElement("ltry").setValue("0");

		String personQ=this.getPageElement("personq_combo").getValue();
		System.out.println("!!!!!!!!!!!"+personQ);
		if(personQ!=null && !"".equals(personQ)){
			String[] s = personQ.split(",");
				for(String type : s){
					type = type.trim();
					if("现职人员".equals(type)){
						this.getPageElement("xzry").setValue("1");
					}
					if("历史人员".equals(type)){
						this.getPageElement("lsry").setValue("1");
						this.getPageElement("ltry").setValue("1");
					}
					/*if("历史人员".equals(type)){
						this.getPageElement("lsry").setValue("1");
					}
					if("离退人员".equals(type)){
						this.getPageElement("ltry").setValue("1");
					}*/
				}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}


	//照片页面数据加载显示
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
		String newsql = afa.replace(CommSQL.getComFields(userid,sid), "a0000,a0101,a0192a,a0107,a0117,a0111a,a0140,a0134,a0196,a0104");

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
			Object a0192a=info[2];
			this.getExecuteSG().addExecuteCode("document.getElementById('i"+i+"').src='"+this.request.getContextPath()+"/servlet/DownloadUserHeadImage?a0000="+URLEncoder.encode(URLEncoder.encode(a0000.toString(),"UTF-8"),"UTF-8")+"'");
			this.getExecuteSG().addExecuteCode("showpic('"+i+"','"+a0000+"','"+a0101+"','"+a0192a+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	//TODO
	//照片
	@PageEvent("Show")
	public int Show() throws RadowException{
		HBSession sess=HBUtil.getHBSession();

		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		String afa = this.request.getSession().getAttribute("allSelect").toString();
//		afa = afa.replace("a01."+CommSQL.getComFields(userid,sid), " count(2) ");

		afa = afa.replace("order by sort", "");
		ArrayList sizeList=(ArrayList) sess.createSQLQuery(afa).list();
		this.getExecuteSG().addExecuteCode("setlength('"+sizeList.size()+"')");
		if(sizeList.size()==0){
			this.setMainMessage("无人员信息");
			return EventRtnType.FAILD;
		}

		this.setNextEventName("picshow");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//小资料
	@PageEvent("ShowData")
	public int ShowData() throws RadowException{
		HBSession sess=HBUtil.getHBSession();

		String userid = SysManagerUtils.getUserId();
		String sid = this.request.getSession().getId();
		String afa = this.request.getSession().getAttribute("allSelect").toString();

//		afa = afa.replace("a01."+CommSQL.getComFields(userid,sid), " count(2) ");
		afa = afa.replace("order by sort", "");

		ArrayList sizeList = (ArrayList) sess.createSQLQuery(afa).list();

		this.getExecuteSG().addExecuteCode("setlength2('"+sizeList.size()+"')");

		if(sizeList.size()==0){
			this.setMainMessage("无人员信息");
			return EventRtnType.FAILD;
		}

		this.setNextEventName("datashow");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//小资料页面数据加载
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
		if(newsql.equals(afa)) {
		//	newsql = afa.replace("a01.A0000 as a0000,(case  when length(a0101)=2 then (Substr(a0101,0,1))||'　'||(Substr(a0101,2,2))else a0101 end) as a0101,nvl(cp.A0283G,a0192a) as a0192a ,A0104 as a0104,decode(A0107,'','',substr(A0107,1,4)||'.'||substr(A0107,5,2) ) as a0107,decode(A0288,'','',substr(A0288,1,4)||'.'||substr(A0288,5,2) ) as a0288,a0165 as a0165,A0117 as a0117,decode(A0134,'','',substr(A0134,1,4)||'.'||substr(A0134,5,2) ) as a0134,decode(a0288,null,substr(a0192c,1,4)||'.'||substr(a0192c,5,2),substr(a0288,1,4)||'.'||substr(a0288,5,2)||decode(a0192c,null,'',CHR(10)||substr(a0192c,1,4)||'.'||substr(a0192c,5,2)))  as rxjbsj,decode(A0192C,'','',substr(A0192C,1,4)||'.'||substr(A0192C,5,2) ) as a0192c", "a01.a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a");
		  newsql="select a01.a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a from A01 a01 "+afa.split("from A01 a01")[1];
		}
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
			File f=new File(path);//判断照片存在不存在
			if(!f.exists()){
				path="picCut/image/photo.jpg";
			}*/
			Object a0101=info[1];//姓名
			Object a0104=info[2];//性别
			if(a0104!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB2261' AND CODE_VALUE = '"+a0104+"'";
				a0104 = sess.createSQLQuery(s).uniqueResult();
//				data = data + a0104.toString() + ",";
			}
			Object a0107=info[3];//出生年月
			if(a0107!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0107.toString().matches(reg) || a0107.toString().matches(reg2)){
					String msg = getAgeNew(a0107.toString());
					data = data + msg + ",";
				}
			}
			Object a0117=info[4];//民族
			if(a0117!=null){
				String s = "SELECT CODE_NAME3 FROM CODE_VALUE WHERE CODE_TYPE = 'GB3304' AND CODE_VALUE = '"+a0117+"'";
				a0117 = sess.createSQLQuery(s).uniqueResult();
				data = data + a0117.toString() + ",";
			}
			Object a0111=info[5];//籍贯
			if(a0111!=null){
				data = data + a0111.toString() + "人,";
			}
			Object a0140=info[6];//入党时间
			if(a0140!=null){
				String reg = "[0-9]{4}\\.[0-9]{2}";
				Pattern p1 = Pattern.compile(reg);
			    Matcher matcher = p1.matcher(a0140.toString());
			    if (matcher.find()) {
			    	String s = matcher.group();
			    	s = s.replace(".", "年");
			    	data = data + s + "月入党,";
				}
			}
			Object a0134=info[7];//参加工作时间
			if(a0134!=null){
				String reg = "^[0-9]{6}$";
				String reg2 = "^[0-9]{8}$";
				if(a0134.toString().matches(reg) || a0134.toString().matches(reg2)){
					String year = a0134.toString().substring(0, 4);
					String month = a0134.toString().substring(4, 6);
					data = data + year + "年" + month + "月参加工作";
				}

			}


			//最高学历
			String zgxlSQL = "select A0801A from A08 where A0834 = '1' and a0000 = '"+a0000+"'";

			List<Object[]> zgxlS = sess.createSQLQuery(zgxlSQL).list();

			if(zgxlS.size() > 0){
				Object zgxl = zgxlS.get(0);

				if(zgxl != null){
					data = data  + "," + zgxl.toString()+"学历";
				}

			}

			//最高学位
			String zgxwSQL = "select A0901A from A08 where A0835 = '1' and a0000 = '"+a0000+"'";

			List<Object[]> zgxwS = sess.createSQLQuery(zgxwSQL).list();


			if(zgxwS.size() > 0){
				Object zgxw = zgxwS.get(0);

				if(zgxw != null){
					zgxw = zgxw.toString().replace("学位", "");
					data = data + "," + zgxw.toString() + "。";
				}else{
					data = data + "。";
				}


			}else{
				data = data + "。";
			}

			//专业技术职务
			Object zyjs = info[8];
			//String zyjsStr = zyjs.toString();

			String zyjsStr = "";
			if(zyjs != null && !zyjs.equals("")){
				zyjsStr = zyjs.toString();
			}


			zyjsStr = zyjsStr.replace(" ", "");
			if(zyjsStr != null && !zyjsStr.equals("")){
				data = data + zyjsStr + ",";
			}

			//String s = "select LISTAGG(A0201A || A0216A,'、') WITHIN GROUP( ORDER BY A0200) from A02 where a0000 = '"+a0000+"' and A0255 = '1' and A0281 = 'true'";
			Object zwOb = info[9];		//工作单位及职务

			String zw = "";
			if(zwOb != null && !zwOb.equals("")){
				zw = zwOb.toString();
			}


			zw = zw.replace(" ", "");

			if(zw!=null && !zw.equals("")){
				data = data + zw + ",";
			}
			data = data.substring(0, data.length()-1) + "。";
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
			//判断是否有修改权限。

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


		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		this.request.getSession().setAttribute("personIdSet", null);
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/rmb.jsp?a0000="+a0000+"','人员信息修改',851,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,645,null,"
				+ "{a0000:'"+a0000+"',gridName:'peopleInfoGrid',maximizable:false,resizable:false,draggable:false},true);");*/
		this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"&&checkedgroupid="+checkedgroupid+"', '_blank', 'height='+(screen.height-90)+', width=1024, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}

	/**
	 * 照片检测
	 * @return
	 * @throws RadowException
	 */
	@NoRequiredValidate
	@PageEvent("imgVerify.onclick")
	public int imgVerify() throws RadowException {
		this.getExecuteSG().addExecuteCode("addTab('照片检测','','"+request.getContextPath()+"/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.orgdataverify.OrgPersonImgVerify',false,false)");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static String getAgeNew(String value) {
		int returnAge;

		String birthYear = value.substring(0, 4);
		String birthMonth = value.substring(4, 6);
		String birthDay = "";
		if (value.length() == 6) {
			birthDay = "01";
		}
		if (value.length() == 8) {
			birthDay = value.substring(6, 8);
		}
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String s = sdf.format(d);
		String nowYear = s.substring(0, 4);
		String nowMonth = s.substring(4, 6);
		String nowDay = s.substring(6, 8);
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
		String msg = "" + returnAge + "岁(" + birthYear + "年" + birthMonth + "月生)";
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
					num = i;//当前的人员顺序
				}
				map.put(i, obj[0]);
				++i;
			}
		}
		this.request.getSession().setAttribute("a0000Map",map);
		this.request.getSession().setAttribute("nowNumber",num);
		this.request.getSession().setAttribute("nowNumber2",num);//nowNumber2用于后期备份查询用
		this.request.getSession().setAttribute("bigNumber",i);
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
/*		//把人员id存到session中在表册展示的地方用
		request.getSession().setAttribute("personidy", a0000);*/
		HBSession sess = HBUtil.getHBSession();

		String tableType = this.getPageElement("tableType").getValue();  //人员展现方式，1：列表；2：小资料；3：照片
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
					//把人员id存到session中在表册展示的地方用
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

			if(picA0000s == null || "".equals(picA0000s.trim())){	//没有勾选 则导出全部

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


	//人员状态变更
	@PageEvent("changeState.onclick")
	@GridDataRange
	public int changeState() throws RadowException, AppException {
		String sqlf = this.getPageElement("sql").getValue();
		if(sqlf.equals("")){
			//this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','请双击机构树查询！');");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		String type = this.getPageElement("tableType").getValue();
		StringBuffer a0000 = new StringBuffer();

		int all = choose("peopleInfoGrid","personcheck");
		//列表
		if("1".equals(type)){

			/*if(all == ON_ONE_CHOOSE){		//列表没有勾选，则操作全部
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
					a0000.append(map.get("a0000") == null ? "" : map.get("a0000").toString()).append(",");//被勾选的人员编号组装，用“，”分隔


					countNum++;
				}
			}
			if(countNum==0){
				//throw new AppException("请勾选人员！");
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			/*}*/
		}
		//小资料、照片
		if("2".equals(type) || "3".equals(type)){

			/*if(all == ON_ONE_CHOOSE){		//列表没有勾选，则操作全部
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
					//throw new AppException("请勾选人员！");

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}
				a0000s = a0000s.replace("'", "");

				a0000 = new StringBuffer(a0000s);


			/*}*/
		}

		if(a0000==null || a0000.toString().trim().equals("")){
			//throw new AppException("数据获取异常！");

			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}


		//this.setRadow_parent_data(a0000.toString());
		HttpSession session=request.getSession();
		session.setAttribute("a0000s",a0000.toString());
		session.setAttribute("type",type);

		String a0000F = a0000.toString();
		a0000F = a0000F.substring(0,a0000F.length()-1);
		String[] values = a0000F.split(",");
		String oneId = values[0];				//第一个人id

		StringBuffer a0000SQl = new StringBuffer();
		for (int i = 0; i < values.length; i++) {
			a0000SQl.append("'").append(values[i] == null ? "" : values[i]).append("',");
		}
		String a0000SQLF = a0000SQl.toString();
		a0000SQLF = a0000SQLF.substring(0,a0000SQLF.length()-1);

		String sql = "select count(1) from a01 where a0163 = (select a0163 from a01 where a0000 = '"+oneId+"') and a0000 in ("+a0000SQLF+")";
		String allPeople = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
		if(Integer.valueOf(allPeople) != values.length){
			//throw new AppException("所选人员有不同的管理状态！");

			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员有不同的管理状态！',null,220);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		//将第一个的人员管理状态赋值到页面
		String sqlA0163 = "select a0163 from a01 where a0000 = '"+oneId+"'";
		String a0163 = HBUtil.getHBSession().createSQLQuery(sqlA0163).uniqueResult().toString();

		String a0163NameSQL =  "select code_name from code_value t where t.code_type='ZB126' and t.code_value = '"+a0163+"'";
		String a0163Name = HBUtil.getHBSession().createSQLQuery(a0163NameSQL).uniqueResult().toString();

		this.getPageElement("a0163").setValue(a0163);			//人员管理状态
		this.getPageElement("a0163_combo").setValue(a0163Name);	//人员管理状态文本


	    //this.openWindow("changeStateWin", "pages.publicServantManage.ChangeState");
		this.getExecuteSG().addExecuteCode("$h.openWin('changeStateWin','pages.publicServantManage.ChangeState','人员状态变更',450,250,document.getElementById('tableType').value,ctxPath)");

		return EventRtnType.NORMAL_SUCCESS;
	}



	//人员查重
	@NoRequiredValidate
	@PageEvent("sameId.onclick")
	public int sameId() throws RadowException {
		//this.openWindow("sameIdCheckWin", "pages.sysorg.org.sameIdCheck");

		this.getExecuteSG().addExecuteCode("$h.openWin('sameIdCheckWin','pages.sysorg.org.sameIdCheck','人员查重',975,528,null,ctxPath)");
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
				this.setMainMessage("操作失败："+e.getDetailMessage());
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
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveA01_config_T")
	public int saveA01_config_T() throws RadowException {
		String type = this.request.getParameter("type");
		String userid=SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		if("1".equals(type) ) {
			try {
				Statement stmt;
				stmt = sess.connection().createStatement();
				String sql="update a01_config set isvali ='false' where userid='"+userid+"' and dicid<>'a0000' ";
				stmt.executeUpdate(sql); 
				String sql2="update a01_config a set isvali ='true',a.orderid=(select a01_config_num from A01_CONFIG_TYPE b where a.dicid=b.a01_config_dicid and a01_config_type='"+type+"') where userid='"+userid+"' and dicid in (select a01_config_dicid from A01_CONFIG_TYPE) ";
				stmt.executeUpdate(sql2);
				stmt.close();
				List<Object[]> list = HBUtil.getHBSession().createSQLQuery("select t.dicid,t.code,t.name,t.gridwidth,t.aboutcode,t.formtype,t.tdesc,t.renderer,t.orderid,t.align  "
	                    + " from a01_config t where t.isvali='true' and userid='" + userid + "' order by t.orderid").list();
				CommSQL.A01_CONFIG_LIST.put(userid, list);
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
				String slll=cm.toString()+"{split}"+dm.toString();
				this.getExecuteSG().addExecuteCode(cm.toString()+"{split}"+dm.toString());
				//this.reloadPage();
				this.setMainMessage("保存成功");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			
		}
		
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//初始化组织机构树
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


	//初始化列表自定义排序字段机构树
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
				this.setMainMessage("操作失败："+e.getDetailMessage());
				e.printStackTrace();
				return EventRtnType.FAILD;
			}
		}

		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}


	@PageEvent("expword")
	public int expWord(String type)throws Exception{
		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		if(type.equals("1")){
			//干部任免审批表
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("1_2")){
			//干部任免审批表(出生年月)
			tpid = "B73E508A87A44EF889430ABA451AC85C";
		}else if(type.equals("1_1")){
			//干部任免审批表(审核用)
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//公务员登记表
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//参照登记表
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//公务员登记备案表
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//参照登记备案表
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//年度考核登记表
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//奖励审批表
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//干部名册（一人一行）
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//干部名册（按机构分组）
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//公务员录用表
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//公务员调任审批表
			tpid = "9e7e1226-6fa1-46a1-8270";
		}else if(type.equals("12")){
			//公务员职级套转表
			tpid = "28bc4d39-dccd-4f07-8aa9";
		}else if(type.equals("15")) {
			tpid = "excel";
		}else if(type.equals("16")) {
			tpid = "47b1011d70f34aefb89365bbfce";
		}

		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		String sqlbya0000 = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'  order by sort";
		List<String> list = sess.createSQLQuery(sqlbya0000).list();
		if(list.size()==0) {
			this.setMainMessage("查询失败，请选择机构树!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			//ids = (String)request.getSession().getAttribute("personidall");

				String a0000ofid = "";
				if(list !=null && list.size() > 0){
					for(int j = 0;j < list.size(); j++){
						a0000ofid = list.get(j);
						a0000ofid=a0000ofid.trim();
						if(j==0) {
							ids="|"+a0000ofid;
						}else {
							ids=ids+"|@|"+a0000ofid;
						}
					}
				}ids=ids+"|";

		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();

		/*if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
			return EventRtnType.NORMAL_SUCCESS;
		}*/

		//无锡客户要求改为2000
		if (length >2000 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择2000人进行处理!");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			List<String> list2 = new ArrayList<String>();
			if(idss != null && !"".equals(idss)){
				String[] personids = ids.split("@");

				for(int j = 0; j < personids.length; j++){
					String a0000 = personids[j].replace("|", "");
					list2.add(a0000);
				}
			}else {
				String hasQueried = this.getPageElement("sql").getValue();
				if("".equals(hasQueried) || hasQueried==null){
					//this.setMainMessage("未进行过查询请先查询!");

					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
					return EventRtnType.NORMAL_SUCCESS;
				}


				//判断列表是否有数据
				List<HashMap<String,Object>> list22 = this.getPageElement("peopleInfoGrid").getValueList();

				if(list22.size() == 0){
					this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
					return EventRtnType.NORMAL_SUCCESS;
				}

					String a0000ofid = "";
					if(list !=null && list.size() > 0){
						for(int j = 0;j < list.size(); j++){
							a0000ofid = list.get(j);
							list2.add(a0000ofid);
						}
					}


			}

			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
				//判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals(""
						+ "")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
					//公务员
					for(int i=0;i<list2.size();i++){
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
						if(pertype.size()>0){
							String a0160 = pertype.get(0)+"";
							if(!a0160.equals("1")){//!
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
										+ " '提示', "
										+ " '所选人员包含非公务员，是否继续导出？', "
										+ " function (btn){ "
										+ "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','"+type+"'); "
										+ "	} "
										+ "} "
									    + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}else{
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}else{
					//非公务员
					for(int i=0;i<list2.size();i++){
						List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
						if(pertype.size()>0){
							String a0160 = pertype.get(0)+"";
							if(a0160.equals("1")){
								this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
										+ " '提示', "
										+ " '所选人员包含公务员，是否继续导出？', "
										+ " function (btn){ "
										+ "	if(btn=='yes'){ "
										+ "		radow.doEvent('exp','"+type+"'); "
										+ "	} "
										+ "} "
									    + ");");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}else{
							this.setMainMessage("数据异常！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
				//获取机构树结构
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
								//throw new RadowException("机构读取异常");
							}else{
								a0201b = n.getId();
							}
//							if(n.getText() == null||"".equals(n.getText())){
//								throw new RadowException("机构读取异常");
//							}
							if(!"1".equals(cn.getText())){//不是法人单位,继续往上找机构
								continue;
							}else{//法人单位
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
					if (result.size() == 0) {
						//如果所选人员没有相同法人单位则提示
						this.setMainMessage("所选人员不属于同一法人单位");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			List<String> wordPaths = null;
			String[] sub = null;
			String a0200 = this.getPageElement("checkedgroupid").getValue();
			if (!a0200.equals("")) {
				String Initials = a0200.substring(0, 1);
				if (Initials.equals("{")) {
					JSONObject jsonObject = JSONObject.fromObject(a0200);
					sub = new String[jsonObject.size()];
					int count = 0;
					for (Object oKey : jsonObject.keySet()) {
						String unitId = (String) oKey;
						int num = count++;
					sub[num]=unitId;
				}
			}else{
			sub=new String[1];
			sub[0]=a0200;
			}
			}
			try {
				if("1_1".equals(type)){
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word1",ids,result,sub);
				}
				else if(type.equals("15") || type.equals("16")) {
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,type,ids,result,sub);

				}else{
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result,sub);
				}

				String zipPath = wordPaths.get(0);
				String infile = "";
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")||tpid.equals("excel")){

					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
						infile = zipPath + "公务员登记备案表.doc" ;
			        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
			        	infile = zipPath + "参照登记备案表.doc" ;
			        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")&&type.equals("16")){
			        	infile = zipPath + "干部花名册（一人一行）.xls";
			        }
			        else if(tpid.equals("excel")){
			        	infile = zipPath + "干部花名册（按机构分组）.xls" ;
			        }
			        else if(tpid.equals("47b1011d70f34aefb89365bbfce")&&type.equals("8")) {
			        	infile = zipPath + "干部花名册（一人一行）.doc";
			        }
			        else {
			        	infile = zipPath + "干部花名册（按机构分组）.doc" ;
			        }
				}
				else{
					if(length >1) {
						infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
					    SevenZipUtil.zip7z(zipPath, infile, null);
					}else {
						PageElement pe = this.getPageElement("peopleInfoGrid");
						String name =getName(pe);
						String username="";
						if(("eebdefc2-4d67-4452-a973-5f7939530a11".equals(tpid)||
								"B73E508A87A44EF889430ABA451AC85C".equals(tpid))) {
							Map<String, Object> dataMap;
							dataMap = FiledownServlet.getMap(list2.get(0),tpid);
							username = (String) dataMap.get("a0101");
						}else {
							username =getUserName(list2.get(0),tpid);
						}	
						infile =zipPath.substring(0,zipPath.length()-1)+"\\1_"+username+".doc";				
					}

				}
			    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.downloadword()");
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
			//干部任免审批表
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//公务员登记表
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//参照登记表
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//公务员登记备案表
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//参照登记备案表
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//年度考核登记表
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//奖励审批表
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//干部名册（一人一行）
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//干部名册（按机构分组）
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//公务员录用表
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//公务员调任审批表
			tpid = "9e7e1226-6fa1-46a1-8270";
		}

		String ids = "";
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			ids = (String)request.getSession().getAttribute("personidall");
		}
		if(StringUtil.isEmpty(ids)){
			//如果ids取值为空的话则说明是右键生成的表册，取单个人的id
			ids = this.getPageElement("expword_personid").getValue();
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)) {
			this.setMainMessage("请选择200人进行处理!");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			String[] personids = ids.split("@");
			List<String> list2 = new ArrayList<String>();
			for(int j = 0; j < personids.length; j++){
				String a0000 = personids[j].replace("|", "");
				list2.add(a0000);
			}
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
				//判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
				//获取机构树结构
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
								//throw new RadowException("机构读取异常");
							}else{
								a0201b = n.getId();
							}
//							if(n.getText() == null||"".equals(n.getText())){
//								throw new RadowException("机构读取异常");
//							}
							if(!"1".equals(cn.getText())){//不是法人单位,继续往上找机构
								continue;
							}else{//法人单位
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
						//如果所选人员没有相同法人单位则提示
						this.setMainMessage("所选人员不属于同一法人单位");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			List<String> wordPaths = null;
			try {
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word",ids,result,null);
				String zipPath = wordPaths.get(0);
				String infile = "";
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
						||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
						infile = zipPath + "公务员登记备案表.doc" ;
			        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
			        	infile = zipPath + "参照登记备案表.doc" ;
			        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
			        	infile = zipPath + "干部花名册（一人一行）.xls" ;
			        }else{
			        	infile = zipPath + "干部花名册（按机构分组）.doc" ;
			        }
				}else{
					infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
				    SevenZipUtil.zip7z(zipPath, infile, null);
				}

			    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
				this.getExecuteSG().addExecuteCode("window.downloadword()");
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
     * @方法描述：获取两个ArrayList的交集
     * @param firstArrayList 第一个ArrayList
     * @param secondArrayList 第二个ArrayList
     * @return resultList 交集ArrayList
     */
    public static List<String> receiveCollectionList(List<String> firstArrayList, List<String> secondArrayList) {
        List<String> resultList = new ArrayList<String>();
        LinkedList<String> result = new LinkedList<String>(firstArrayList);// 大集合用linkedlist
        HashSet<String> othHash = new HashSet<String>(secondArrayList);// 小集合用hashset
        Iterator<String> iter = result.iterator();// 采用Iterator迭代器进行数据的操作
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

	//右键导出word
	@PageEvent("expword1")
	public int expWord1(String type)throws Exception{
		HBSession sess = HBUtil.getHBSession();
		String a0000=this.getPageElement("expword_personid").getValue();
		String name = (String) HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='"+a0000+"'").uniqueResult();
		String tpid = "";
		if(type.equals("1")){
			//干部任免审批表
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("13")){
			//干部任免审批表(审核中)
			tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
		}else if(type.equals("2")){
			//公务员登记表
			tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
		}else if(type.equals("3")){
			//参照登记表
			tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
		}else if(type.equals("4")){
			//公务员登记备案表
			tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
		}else if(type.equals("5")){
			//参照登记备案表
			tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
		}else if(type.equals("6")){
			//年度考核登记表
			tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
		}else if(type.equals("7")){
			//奖励审批表
			tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
		}else if(type.equals("8")){
			//干部名册（一人一行）
			tpid = "47b1011d70f34aefb89365bbfce";
		}else if(type.equals("9")){
			//干部名册（按机构分组）
			tpid = "eebdefc2-4d67-4452-a973";
		}else if(type.equals("10")){
			//公务员录用表
			tpid = "3de527c0-ea23-42c4-a66f";
		}else if(type.equals("11")){
			//公务员调任审批表
			tpid = "9e7e1226-6fa1-46a1-8270";
		}else if(type.equals("12")){
			//公务员职级套转表
			tpid = "28bc4d39-dccd-4f07-8aa9";
		}

		String id = this.getPageElement("expword_personid").getValue();
		List<String> list2 = new ArrayList<String>();
		list2.add(id);
		List<String> result = new ArrayList<String>();
		if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
				||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")){
			//判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
				//公务员
				for(int i=0;i<list2.size();i++){
					List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
					if(pertype.size()>0){
						String a0160 = pertype.get(0)+"";
						if(!a0160.equals("1")){
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
									+ " '提示', "
									+ " '所选人员包含非公务员，是否继续导出？', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('exp','"+type+"'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}else{
						this.setMainMessage("数据异常！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}else{
				//非公务员
				for(int i=0;i<list2.size();i++){
					List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
					if(pertype.size()>0){
						String a0160 = pertype.get(0)+"";
						if(a0160.equals("1")){
							this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
									+ " '提示', "
									+ " '所选人员包含公务员，是否继续导出？', "
									+ " function (btn){ "
									+ "	if(btn=='yes'){ "
									+ "		radow.doEvent('exp','"+type+"'); "
									+ "	} "
									+ "} "
								    + ");");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}else{
						this.setMainMessage("数据异常！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			//获取机构树结构
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
							//throw new RadowException("机构读取异常");
						}else{
							a0201b = n.getId();
						}
//						if(n.getText() == null||"".equals(n.getText())){
//							throw new RadowException("机构读取异常");
//						}
						if(!"1".equals(cn.getText())){//不是法人单位,继续往上找机构
							continue;
						}else{//法人单位
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
					//如果所选人员没有相同法人单位则提示
					this.setMainMessage("所选人员不属于同一法人单位");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		List<String> wordPaths = null;
		try {
			if("13".equals(type)){
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word1","'"+id+"'",result,null);
			}else{
				wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word","'"+id+"'",result,null);
			}
			//wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2,tpid,"word","'"+id+"'",result);
			String zipPath = wordPaths.get(0);
			String infile = "";
			if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")
					||tpid.equals("47b1011d70f34aefb89365bbfce")||tpid.equals("eebdefc2-4d67-4452-a973")){
				if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")){
					infile = zipPath + "公务员登记备案表.doc" ;
		        }else if(tpid.equals("40e9b81c-5a53-445f-a027-6e00a9f6")){
		        	infile = zipPath + "参照登记备案表.doc" ;
		        }else if(tpid.equals("47b1011d70f34aefb89365bbfce")){
		        	infile = zipPath + "干部花名册（一人一行）.xls" ;
		        }else{
		        	infile = zipPath + "干部花名册（按机构分组）.doc" ;
		        }
			}else{
				String username =getUserName(a0000,tpid);
				infile =zipPath.substring(0,zipPath.length()-1)+"\\1_"+username+".doc";

				/*
				 * infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
				 * SevenZipUtil.zip7z(zipPath, infile, null);
				 */
			}

		    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
			this.getExecuteSG().addExecuteCode("window.downloadword()");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}


	//导出干部任免审批表
		@NoRequiredValidate
		@PageEvent("exportGBDocBtn.onclick")
		public int exportGBDocBtn() throws RadowException, AppException {
			HBSession sess = HBUtil.getHBSession();
			String tableType = this.getPageElement("tableType").getValue();
			String a0000s = "";
			String name ="";
			String hasQueried = this.getPageElement("sql").getValue();
			if("".equals(hasQueried) || hasQueried==null){
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请双击机构树查询！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("1".equals(tableType)){	//列表
				int i = choose("peopleInfoGrid","personcheck");
				if (i == ON_ONE_CHOOSE ) {			//列表没有勾选，则操作全部
					String a0000 = "";
					PageElement pe = this.getPageElement("peopleInfoGrid");
					if(pe!=null){
						List<HashMap<String, Object>> list = pe.getValueList();
						for(int j = 0; j < list.size(); j++){
							HashMap<String, Object> map = list.get(j);
							a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
							a0000s += "'" + a0000 + "',";
							name += HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult() + ",";
						}
					}

					if(a0000s.length() < 1){
						/*this.setMainMessage("当前列表没有人员！");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
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
								a0000 = this.getPageElement("peopleInfoGrid").getValue("a0000", j).toString();
								a0000s += "'" + a0000 + "',";
								name += HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult() + ",";
							}
						}
					}
					if(a0000s.length() < 1){
						/*this.setMainMessage("当前列表没有人员！");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}

					a0000s = a0000s.substring(0, a0000s.length()-1);
				}
			}else if("2".equals(tableType) || "3".equals(tableType)){

				String picA0000s = this.getPageElement("picA0000s").getValue();

				if(picA0000s == null || "".equals(picA0000s.trim())){	//没有勾选 则导出全部
					this.getPageElement("picA0000s").setValue("");
					String sql = "select a0000 from A01SEARCHTEMP where sessionid='"+this.request.getSession().getId()+"'";
					List<String> list = sess.createSQLQuery(sql).list();
					String a0000 = "";
					if(list !=null && list.size() > 0){
						for(int j = 0;j < list.size(); j++){
							a0000 = list.get(j);
							a0000s += "'" + a0000 + "',";
							name += HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult() + ",";
						}
					}
					if(a0000s.length() < 1){
						/*this.setMainMessage("当前列表没有人员！");
						return EventRtnType.FAILD;*/

						this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
						return EventRtnType.NORMAL_SUCCESS;
					}

					a0000s = a0000s.substring(0, a0000s.length()-1);
				}else{
					a0000s = picA0000s.substring(0, picA0000s.length()-1);
					String[] a0000_arr = a0000s.replaceAll("\\'", "").split(",");
					if(a0000_arr != null && a0000_arr.length > 0){
						for(int j = 0; j < a0000_arr.length; j++){
							String a0000 = a0000_arr[j];
							name += HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult() + ",";
						}
					}
				}
			}

			if(name.length() < 1){
				/*this.setMainMessage("当前列表没有人员！");
				return EventRtnType.FAILD;*/

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			name = name.substring(0,name.length()-1);

			this.getPageElement("docA0101s").setValue(name);
			this.getPageElement("docA0000s").setValue(a0000s);
			this.getExecuteSG().addExecuteCode("exportGBDoc();");
			return EventRtnType.NORMAL_SUCCESS;
		}



		//右键：人员状态变更
		@PageEvent("changeStateRigth")
		@GridDataRange
		public int changeStateRigth(String a0000) throws RadowException, AppException {

			HttpSession session = request.getSession();
			session.setAttribute("a0000s", a0000 + ",");

			String a0000F = a0000;
			a0000F = a0000F + ",";
			a0000F = a0000F.substring(0, a0000F.length() - 1);
			String[] values = a0000F.split(",");
			String oneId = values[0];                //第一个人id

			StringBuffer a0000SQl = new StringBuffer();
			for (int i = 0; i < values.length; i++) {
				a0000SQl.append("'").append(values[i] == null ? "" : values[i]).append("',");
			}
			String a0000SQLF = a0000SQl.toString();
			a0000SQLF = a0000SQLF.substring(0,a0000SQLF.length()-1);

			String sql = "select count(1) from a01 where a0163 = (select a0163 from a01 where a0000 = '"+oneId+"') and a0000 in ("+a0000SQLF+")";
			String allPeople = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult().toString();
			if(Integer.valueOf(allPeople) != values.length){

				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','所选人员有不同的管理状态！',null,220);");
				return EventRtnType.NORMAL_SUCCESS;
			}

			//将第一个的人员管理状态赋值到页面
			String sqlA0163 = "select a0163 from a01 where a0000 = '"+oneId+"'";
			String a0163 = HBUtil.getHBSession().createSQLQuery(sqlA0163).uniqueResult().toString();

			String a0163NameSQL =  "select code_name from code_value t where t.code_type='ZB126' and t.code_value = '"+a0163+"'";
			String a0163Name = HBUtil.getHBSession().createSQLQuery(a0163NameSQL).uniqueResult().toString();

			this.getPageElement("a0163").setValue(a0163);			//人员管理状态
			this.getPageElement("a0163_combo").setValue(a0163Name);	//人员管理状态文本


		    //this.openWindow("changeStateWin", "pages.publicServantManage.ChangeState");
			this.getExecuteSG().addExecuteCode("$h.openWin('changeStateWin','pages.publicServantManage.ChangeState','人员状态变更',450,250,document.getElementById('tableType').value,ctxPath)");

			return EventRtnType.NORMAL_SUCCESS;
		}

		//右键：表册输出
		@PageEvent("out")
		public int out(String a0000)throws AppException, RadowException, SQLException{
	/*		//把人员id存到session中在表册展示的地方用
			request.getSession().setAttribute("personidy", a0000);*/
			request.getSession().setAttribute("personidy", "|"+a0000+"|@");
			request.getSession().removeAttribute("personidall");
			return EventRtnType.NORMAL_SUCCESS;
		}



		//右键：导出任免表Word
		@NoRequiredValidate
		@PageEvent("exportGBDocRigth")
		public int exportGBDocRigth(String a0000) throws RadowException, AppException {
			HBSession sess = HBUtil.getHBSession();

			String a0000s = "";
			String name = "";

			a0000s += "'" + a0000 + "',";
			name += HBUtil.getHBSession().createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult() + ",";

			a0000s = a0000s.substring(0, a0000s.length() - 1);

			name = name.substring(0, name.length() - 1);

			this.getPageElement("docA0101s").setValue(name);
			this.getPageElement("docA0000s").setValue(a0000s);
			this.getExecuteSG().addExecuteCode("exportGBDoc();");
			return EventRtnType.NORMAL_SUCCESS;
		}



		//右击：导出任免表PDF
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
					int x=0;
					while(FileUtil.checkFile(zippath+name+".lrm")) {
						x++;
						name+=x;
					}
					FileUtil.createFile(zippath+name+".lrm", lrm, false, "GBK");
					List<A57> list15 = HBUtil.getHBSession().createSQLQuery("select * from A57 where a0000='"+a0000+"'").addEntity(A57.class).list();
					if(list15.size()>0){
						A57 a57 = list15.get(0);
						if(a57.getPhotopath()!=null && !a57.getPhotopath().equals("")){
							File f = new File(zippath + name+".pic");
							FileOutputStream fos = new FileOutputStream(f);
							File f2 = new File(PhotosUtil.PHOTO_PATH+a57.getPhotopath()+a57.getPhotoname());
							if(f2.exists() && f2.isFile()){
								InputStream is = new FileInputStream(f2);// 读出数据后转换为二进制流
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
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}

				try {
					infile =zippath.substring(0,zippath.length()-1)+"\\"+name+".pdf";
					SevenZipUtil.zip7z(zippath, infile, null);
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				} catch (Exception e) {
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}
			}

			return EventRtnType.NORMAL_SUCCESS;

		}


		//右击：导出任免表HZB
		@PageEvent("importHzb")
		public int importHzb(String a0000) throws RadowException {

			String allids = "'"+a0000+"'";
			//String allids = ids.substring(0,ids.length()-1);
			String id = UUID.randomUUID().toString().replace("-", "");

			try {
				CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
				KingbsconfigBS.saveImpDetailInit3(id);
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				DataPsnImpThread thr = new DataPsnImpThread(id, user,"","","","","",
						"","","","","","","",userVo,allids);
				new Thread(thr,"Thread_psnexp").start();
				this.setRadow_parent_data(id);
			    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+id+"',ctxPath)");

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

		//打印任免表
		@PageEvent("printRmb")
		@Synchronous
		public int printRmb() throws Exception {
			//打印机的选择判断
			HttpSession session = request.getSession();
			String printer = (String) session.getAttribute("Printer");
			if (session.getAttribute("PrintNum").toString().equals("")) {
				this.setMainMessage("请设置打印份数！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			int printNum = Integer.parseInt((String) session.getAttribute("PrintNum"));
			if (printer.equals("")) {
				this.setMainMessage("请先去打印机设置中选择一种打印机！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if (printNum <= 0) {
				this.setMainMessage("打印份数必须大于0并且不能为空！");
	        	return EventRtnType.NORMAL_SUCCESS;
	        }


			HBSession sess = HBUtil.getHBSession();
			String tpid = "";
			String type= "1";
			if(type.equals("1")){
				//干部任免审批表
				tpid = "eebdefc2-4d67-4452-a973-5f7939530a11";
			}else if(type.equals("2")){
				//公务员登记表
				tpid = "5d3cef0f0d8b430cb35b2ac2cb3bf927";
			}else if(type.equals("3")){
				//参照登记表
				tpid = "0f6e25ab-ee0a-4b23-b52d-7c6774dfc462";
			}else if(type.equals("4")){
				//公务员登记备案表
				tpid = "3de1c725-d71b-476a-b87c-6c8d2184";
			}else if(type.equals("5")){
				//参照登记备案表
				tpid = "40e9b81c-5a53-445f-a027-6e00a9f6";
			}else if(type.equals("6")){
				//年度考核登记表
				tpid = "a43d8c50-400d-42fe-9e0d-5665ed0b0508";
			}else if(type.equals("7")){
				//奖励审批表
				tpid = "04f59673-9c3a-4d9c-b016-a5b789d636e2";
			}else if(type.equals("8")){
				//干部名册（一人一行）
				tpid = "47b1011d70f34aefb89365bbfce";
			}else if(type.equals("9")){
				//干部名册（按机构分组）
				tpid = "eebdefc2-4d67-4452-a973";
			}else if(type.equals("10")){
				//公务员录用表
				tpid = "3de527c0-ea23-42c4-a66f";
			}else if(type.equals("11")){
				//公务员调任审批表
				tpid = "9e7e1226-6fa1-46a1-8270";
			}

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
			List<String> result = new ArrayList<String>();
			if (length >200 && "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
					.contains(tpid)) {
				this.setMainMessage("请选择200人进行处理!");
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
					//判断所有人是否属于同一法人单位下，针对公务员登记备案表和参公登记备案表
					if(tpid.equals("3de1c725-d71b-476a-b87c-6c8d2184")||tpid.equals(""
							+ "")||tpid.equals("0f6e25ab-ee0a-4b23-b52d-7c6774dfc462")||tpid.equals("5d3cef0f0d8b430cb35b2ac2cb3bf927")){
						//公务员
						for(int i=0;i<list2.size();i++){
							List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
							if(pertype.size()>0){
								String a0160 = pertype.get(0)+"";
								if(!a0160.equals("1")){//!
									this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
											+ " '提示', "
											+ " '所选人员包含非公务员，是否继续导出？', "
											+ " function (btn){ "
											+ "	if(btn=='yes'){ "
											+ "		radow.doEvent('exp','"+type+"'); "
											+ "	} "
											+ "} "
										    + ");");
									return EventRtnType.NORMAL_SUCCESS;
								}
							}else{
								this.setMainMessage("数据异常！");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
					}else{
						//非公务员
						for(int i=0;i<list2.size();i++){
							List pertype = sess.createSQLQuery("select a0160 from a01 where a0000 = '"+list2.get(i)+"'").list();
							if(pertype.size()>0){
								String a0160 = pertype.get(0)+"";
								if(a0160.equals("1")){
									this.getExecuteSG().addExecuteCode("Ext.MessageBox.confirm( "
											+ " '提示', "
											+ " '所选人员包含公务员，是否继续导出？', "
											+ " function (btn){ "
											+ "	if(btn=='yes'){ "
											+ "		radow.doEvent('exp','"+type+"'); "
											+ "	} "
											+ "} "
										    + ");");
									return EventRtnType.NORMAL_SUCCESS;
								}
							}else{
								this.setMainMessage("数据异常！");
								return EventRtnType.NORMAL_SUCCESS;
							}
						}
					}
					//获取机构树结构
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
									//throw new RadowException("机构读取异常");
								}else{
									a0201b = n.getId();
								}
//								if(n.getText() == null||"".equals(n.getText())){
//									throw new RadowException("机构读取异常");
//								}
								if(!"1".equals(cn.getText())){//不是法人单位,继续往上找机构
									continue;
								}else{//法人单位
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
							//如果所选人员没有相同法人单位则提示
							this.setMainMessage("所选人员不属于同一法人单位");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
				}
				List<String> wordPaths = null;
				try {
					wordPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list2, tpid, "word", ids, result, null);

					File f = new File(wordPaths.get(0));
					if (!f.exists()) {
						this.setMainMessage("文件不存在！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					File[] fa = f.listFiles();
					for (int p = 1; p <= printNum; p++) {
						for (int i = 0; i < fa.length; i++) {
							File fs = fa[i];
							if (fs.isDirectory()) {
								System.out.println(fs.getName() + " [目录]");
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
			this.setMainMessage("打印完毕");
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
		this.getExecuteSG().addExecuteCode("$h.openWin('ppsDataWin','pages.pps.ppsEnter','pps数据抽样',975,528,'ss','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}

	//记录默认的打印机名字
	@PageEvent("printSetInit")
	public int printSetInit(String printer) {
		HttpSession session = request.getSession();
		session.setAttribute("Printer", printer);
		if(session.getAttribute("PrintNum")==null) {
			session.setAttribute("PrintNum", "1");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	//自定义导出弹框
	@PageEvent("expBtnwordonclick")
	@NoRequiredValidate
	public int word()throws RadowException{
		 this.getExecuteSG().addExecuteCode("view()");
		return EventRtnType.NORMAL_SUCCESS;
	}
/*	//自定义导出弹框
	@PageEvent("expBtnword.onclick")
	@NoRequiredValidate
	public int word()throws RadowException{
		this.getExecuteSG().addExecuteCode("view()");
		return EventRtnType.NORMAL_SUCCESS;
	}
*/
	@PageEvent("expwordw")//自定义word导出
	public int expWordw(String namebs)throws AppException, RadowException{
		String[] nbs= namebs.split(",");
		 String name = nbs[0];
		 String bs = nbs[1];
		 String rad = nbs[2];


		  //打印机的选择判断
		 /*if("dy".equals(bs)&&bs!=null){
			 HttpSession session = request.getSession();
		        String printer= (String) session.getAttribute("Printer");
		        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
		        	this.setMainMessage("请设置打印份数！");
		        	return EventRtnType.NORMAL_SUCCESS;
		        }
		        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
		        if(printer.equals("")) {
		        	this.setMainMessage("请先去打印机设置中选择一种打印机！");
		        	return EventRtnType.NORMAL_SUCCESS;
		        }
		        if(printNum<=0) {
		        	this.setMainMessage("打印份数必须大于0并且不能为空！");
		        	return EventRtnType.NORMAL_SUCCESS;
		        }
		 }*/



		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
		String ids = "";
		String idfromss = (String)request.getSession().getAttribute("personfromidy");
		String idss = (String)request.getSession().getAttribute("personidy");
		if(idfromss != null && !"".equals(idfromss)){
			//勾选人员id
			ids = idfromss;
			request.getSession().removeAttribute("personfromidy");
		}else if(idss != null && !"".equals(idss)){
			//勾选人员id
			ids = idss;
		}else{
			//未勾选人员，全选
			ids = (String)request.getSession().getAttribute("personidall");
		}
		int length = ids.split("@").length;
		List<String> result = new ArrayList<String>();
		if (length >200 /*&& "eebdefc2-4d67-4452-a973-5f7939530a11,5d3cef0f0d8b430cb35b2ac2cb3bf927,a43d8c50-400d-42fe-9e0d-5665ed0b0508,0f6e25ab-ee0a-4b23-b52d-7c6774dfc462,a43d8c50-400d-42fe-9e0d-5665ed0b0508,3de1c725-d71b-476a-b87c-6c8d2184,40e9b81c-5a53-445f-a027-6e00a9f6,04f59673-9c3a-4d9c-b016-a5b789d636e2"
				.contains(tpid)*/) {
			this.setMainMessage("请选择200人进行处理!");
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
					this.setMainMessage("没有找到相应模板");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(wordPaths.get(0).contains("@")){
					this.getPageElement("downfile").setValue(wordPaths.get(0).replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				}else{
					if("xz".equals(bs)&&bs!=null){
						String zipPath = wordPaths.get(0);
						String infile = "";

						if(length==1) {
							PageElement pe = this.getPageElement("peopleInfoGrid");
							String names =getName(pe);
							infile =zipPath.substring(0,zipPath.length()-1)+"\\1_"+names+".doc";
						}else {
			        	infile =zipPath.substring(0,zipPath.length()-1)+".zip" ;
					    SevenZipUtil.zip7z(zipPath, infile, null);
						}

					    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
						this.getExecuteSG().addExecuteCode("window.downloadword()");
					}else{
						File f = new File(wordPaths.get(0));
						if (!f.exists()) {
							this.setMainMessage("文件不存在！");
							return EventRtnType.NORMAL_SUCCESS;
						}
						File[] fa = f.listFiles();
						HttpSession session = request.getSession();
						//int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
						for (int p = 1; p <= 1; p++) {
							for (int i = 0; i < fa.length; i++) {
								File fs = fa[i];
								if (fs.isDirectory()) {
									System.out.println(fs.getName() + " [目录]");
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
						this.setMainMessage("打印完毕");
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
	public int expWorde(String namebs) throws AppException, RadowException, IOException {
		String[] nbs = namebs.split(",");
		String name = nbs[0];
		String bs = nbs[1];
		String rad = nbs[2];

		//打印机的选择判断
        /*HttpSession session = request.getSession();
        String printer= (String) session.getAttribute("Printer");
        if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
        	this.setMainMessage("请设置打印份数！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
        if(printer.equals("")) {
        	this.setMainMessage("请先去打印机设置中选择一种打印机！");
        	return EventRtnType.NORMAL_SUCCESS;
        }
        if(printNum<=0) {
        	this.setMainMessage("打印份数必须大于0并且不能为空！");
        	return EventRtnType.NORMAL_SUCCESS;
        }*/

		HBSession sess = HBUtil.getHBSession();
		String tpid = "";
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
		List<String> result = new ArrayList<String>();
		if (length >200) {
			this.setMainMessage("请选择200人进行处理!");
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

		//String doctpl = request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+ name;//路径和模板名
		//String jbmc = request.getSession().getServletContext().getRealPath("webOffice/word").replace("\\webOffice\\word", "")+"\\ziploud/"+UUID.randomUUID().toString().replace("-", "")+"/"+"expFiles_"+System.currentTimeMillis()+"/";
        String exportPath=request.getSession().getServletContext().getRealPath("webOffice/word");
		String infile = JbmcExp.expJbmc(name, result, AppConfig.HZB_PATH+"\\WebOffice",null,rad,exportPath);
		if("没有模板".equals(infile)){
			this.setMainMessage("没有对应的模板!");
			return EventRtnType.NORMAL_SUCCESS;
		}
        //String infile=jbmc.substring(0, jbmc.length()-1)+".zip"
       // String infile=jbmc+"基本名册.xls";
        //SevenZipUtil.zip7z(jbmc, jbmc.substring(0, jbmc.length()-1)+".zip", null);
		//this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		//this.getExecuteSG().addExecuteCode("window.downloadword()");
       // this.setMainMessage("导出成功!!");

		if("xz".equals(bs)&&bs!=null){
    	   this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
    	   this.getExecuteSG().addExecuteCode("window.downloadword()");
       }else{
    	   for (int p = 1; p <= 1; p++) {
    		   this.getExecuteSG().addExecuteCode("printStart1('"+infile.substring(infile.indexOf("ziploud")).replace("\\", "\\\\")+"');");
    	   }
    	   this.setMainMessage("打印完毕");
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
public int expworddw(String namebs) throws AppException, RadowException, IOException {

	String[] nbs = namebs.split(",");
	String name = nbs[0];
	String bs = nbs[1];
	String rad = nbs[2];
	//打印机的选择判断
 /* HttpSession session = request.getSession();
  String printer= (String) session.getAttribute("Printer");
  if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
  	this.setMainMessage("请设置打印份数！");
  	return EventRtnType.NORMAL_SUCCESS;
  }
  int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
  if(printer.equals("")) {
  	this.setMainMessage("请先去打印机设置中选择一种打印机！");
  	return EventRtnType.NORMAL_SUCCESS;
  }
  if(printNum<=0) {
  	this.setMainMessage("打印份数必须大于0并且不能为空！");
  	return EventRtnType.NORMAL_SUCCESS;
  }*/
	//获取所有被选中人的id
	HBSession sess = HBUtil.getHBSession();
	String tpid = "";
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
	List<String> result = new ArrayList<String>();
	if (length >200) {
		this.setMainMessage("请选择200人进行处理!");
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

	//根据id获取每个人的任职机构
	ArrayList<String> rzjgs = new ArrayList<String>();
	for(int i=0;i<result.size();i++){
		String a0000 = result.get(i);
		String sql="select distinct a0201b from a02 where a0000='"+a0000+"'";
		//查询id为a0000的人员所属的单位
		List list = sess.createSQLQuery(sql).list();
		String rzjgStr="";
		for (Object obj : list) {
			rzjgStr += obj + " ";
		}
		rzjgStr=rzjgStr.trim();

		String rzjg=a0000+","+rzjgStr;
		rzjgs.add(rzjg);

	}

	//取到所有任职机构
	TreeSet<String> jg = new TreeSet<String>();
	for(int i=0;i<rzjgs.size();i++){

		String str=rzjgs.get(i).split(",")[1];
		String[] split = str.split(" ");
		for (String string : split) {
			jg.add(string);
		}
	}

	//创建map<机构id,a0000>
	HashMap<String, String> map = new HashMap<String, String>();
	Iterator<String> iterator = jg.iterator();
	while(iterator.hasNext()){
		String next = iterator.next();
		//机构下的所有人员id
		String a0000s="";
		for(int i=0;i<rzjgs.size();i++){
			String str=rzjgs.get(i).split(",")[0];//取人员id
			String jgids=rzjgs.get(i).split(",")[1];//取机构id
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

	//创建Hssf读取excel模板内容
	String doctpl = AppConfig.HZB_PATH+"\\WebOffice"+"\\"+ name;//路径和模板名
	File file=new File(doctpl);
	HSSFWorkbook workbook = null;
	if(file.exists()){
		workbook = new HSSFWorkbook(new FileInputStream(file));
	}else{
		this.setMainMessage("没有对应模板!");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//获取excel的第一个sheet
	HSSFSheet sheet = workbook.getSheetAt(0);

	 //获取所有标题内容
    HashMap<String, String> headLine = new HashMap<String, String>();
    HashMap<String, HSSFCellStyle> headLineStyle = new HashMap<String, HSSFCellStyle>();
    HSSFRow row3 = sheet.getRow(2);
    if (row3 != null) {
        for (int k = 0; k < row3.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
            if (row3.getCell(k) != null) { // getCell 获取单元格数据
               // System.out.print(row1.getCell(k) + "\t");
            	headLine.put(k+"", row3.getCell(k).getStringCellValue());
            	headLineStyle.put(k+"", row3.getCell(k).getCellStyle());
            }
        }
    }

  //定义list获取所有需要查询的字段
    List<String> queryField= new ArrayList<String>();
	//获取sheet中的第二行所有单元格数据
	//定义map存储第二行的所有数据
	HashMap<String, String> twoRowMap = new HashMap<String, String>();
	 HashMap<String, HSSFCellStyle> twoRowMapStyle = new HashMap<String, HSSFCellStyle>();
   HSSFRow row = sheet.getRow(3);
   if (row != null) {
       for (int k = 0; k < row.getLastCellNum(); k++) {// getLastCellNum，是获取最后一个不为空的列是第几个
    	   HSSFCell cell = row.getCell(k);
           if (row.getCell(k) != null) { // getCell 获取单元格数据
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
	//定义map保存表和对应需要查询的字段
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




   //定义新的excel用于导出
   HSSFWorkbook newwork = new HSSFWorkbook();
   HSSFSheet sheet1 = newwork.createSheet("sheet1");
   Integer count=0;
  //循坏map
   for (Map.Entry<String, String> entry : map.entrySet()) {
	   String dwid = entry.getKey();//此机构的id
	   String a0000s = entry.getValue();//此机构所有的人员id
	   String[] split1 = a0000s.split(",");
	 //定义list保存所有数据
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
							allData += x + "@" + objects[x] + "!!";
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

       //添加机构名称
       HSSFCell createCell2 = row0.createCell(0);
       //查询机构名称
       String uniqueResult = (String) sess.createSQLQuery("select B0101 from b01 where b0111='"+dwid+"'").uniqueResult();
       createCell2.setCellValue(uniqueResult);

       //设置表头样式
       HSSFCellStyle setBorder = newwork.createCellStyle();

       row0.setHeight(sheet.getRow(0).getHeight());

       	//二、设置边框:
       //setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
       //setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
       //setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
       //setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
       //三、设置居中:
       setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
    //   四、设置字体:
       HSSFFont font = newwork.createFont();
       font.setFontName("黑体");
       font.setFontHeightInPoints((short) 20);//设置字体大小
       setBorder.setFont(font);//选择需要用到的字体格式
    //   五、设置列宽:
       sheet.setColumnWidth(0, 3766); //第一个参数代表列id(从0开始),第2个参数代表宽度值
      // 六、设置自动换行:
       setBorder.setWrapText(true);//设置自动换行
       HSSFCellStyle cellStyle = newwork.createCellStyle();
       cellStyle.cloneStyleFrom(sheet.getRow(0).getCell(0).getCellStyle());
         createCell2.setCellStyle(cellStyle);



       //合并单元格
       CellRangeAddress cra =new CellRangeAddress(count, count, 0, headLine.size()); // 起始行, 终止行, 起始列, 终止列
       CellRangeAddress cra1 =new CellRangeAddress(count+1, count+1, 0, headLine.size()); // 起始行, 终止行, 起始列, 终止列
       sheet1.addMergedRegion(cra);
       sheet1.addMergedRegion(cra1);

      //MergerRegion(sheet, sheet1);

       //表头样式
      /* HSSFCellStyle headstyle = newwork.createCellStyle();
       HSSFFont font2 = newwork.createFont();
       font2.setFontName("黑体");
       //font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
       font2.setFontHeightInPoints((short)10);  //字体大小

       headstyle.setFont(font2);//选择需要用到的字体格式
       headstyle.setWrapText(true);//设置自动换行
*/
       //添加表头内容
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

      //设置数据样式
     /* HSSFCellStyle datastyle = newwork.createCellStyle();
      HSSFFont font3 = newwork.createFont();
      font3.setFontName("宋体");
      //font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
      font3.setFontHeightInPoints((short) 10);  //字体大小
      datastyle.setFont(font3);
      datastyle.setWrapText(true);//设置自动换行
*/
     //添加数据
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

   newwork.write(new File(jbmc+"单位名册.xls"));

   newwork.close();//最后记得关闭工作簿

   //String infile=jbmc.substring(0, jbmc.length()-1)+".zip"
   String infile=jbmc+"单位名册.xls";
   //SevenZipUtil.zip7z(jbmc, jbmc.substring(0, jbmc.length()-1)+".zip", null);
   if("xz".equals(bs)&&bs!=null){
	   this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
	   this.getExecuteSG().addExecuteCode("window.downloadword()");
   }else{
	   for (int p = 1; p <= 1; p++) {
		   this.getExecuteSG().addExecuteCode("printStart1('"+infile.substring(infile.indexOf("ziploud")).replace("\\", "\\\\")+"');");
	   }
	   this.setMainMessage("打印完毕");
   }


	return EventRtnType.NORMAL_SUCCESS;
}
@PageEvent("expExcel")
public int expExcel(String namebs) throws Exception{

	String[] nbs= namebs.split(",");
	 String name = nbs[0];
	 String bs = nbs[1];
	 String rad = nbs[2];

	//打印机的选择判断
  /* HttpSession session = request.getSession();
   String printer= (String) session.getAttribute("Printer");
   if(((String) session.getAttribute("PrintNum").toString()).equals("")) {
   	this.setMainMessage("请设置打印份数！");
   	return EventRtnType.NORMAL_SUCCESS;
   }
   int printNum =  Integer.parseInt((String) session.getAttribute("PrintNum"));
   if(printer.equals("")) {
   	this.setMainMessage("请先去打印机设置中选择一种打印机！");
   	return EventRtnType.NORMAL_SUCCESS;
   }
   if(printNum<=0) {
   	this.setMainMessage("打印份数必须大于0并且不能为空！");
   	return EventRtnType.NORMAL_SUCCESS;
   }*/
	//所有选中人员id
	HBSession sess = HBUtil.getHBSession();
	String tpid = "";
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
	List<String> result = new ArrayList<String>();
	if (length >200) {
		this.setMainMessage("请选择200人进行处理!");
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

	//读取excel
	//创建Hssf读取excel模板内容
	String exportPath=request.getSession().getServletContext().getRealPath("webOffice/word");
	String doctpl = request.getSession().getServletContext().getRealPath("webOffice/word") +"\\"+ name;//路径和模板名
	String infile = JbmcExp.expRmb(name, result, AppConfig.HZB_PATH+"\\WebOffice", null,rad,exportPath);
	if("没有模板".equals(infile)){
		this.setMainMessage("没有对应的模板");
		return EventRtnType.NORMAL_SUCCESS;
	}
//    this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
//	this.getExecuteSG().addExecuteCode("window.downloadword()");
	if("xz".equals(bs)&&bs!=null){
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.downloadword()");
	}else{
		String printPath = infile.substring(0, infile.length() - 4) + "/";
		File f = new File(printPath);
		if (!f.exists()) {
			this.setMainMessage("文件不存在！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		File[] fa = f.listFiles();
		for (int p = 1; p <= 1; p++) {
			for (int i = 0; i < fa.length; i++) {
				File fs = fa[i];
				if (fs.isDirectory()) {
					System.out.println(fs.getName() + " [目录]");
				} else {
					//printFile(wordPaths.get(0) + fs.getName(), printer);
					//String path = wordPaths.get(0) + fs.getName();//D:\apache-tomcat-6.0.43\webapps\hzb_qd2018\ziploud\f3f5941bcb1848babd2a3763ad66c292\expFiles_1530269619433\邢红伟1.xls
					String rootPath = PhotosUtil.getRootPath();
					//path = path.replace(rootPath, "").replace("\\", "/");
					this.getExecuteSG().addExecuteCode("printStart1('"+fs.toString().substring(fs.toString().indexOf("ziploud")).replace("\\", "\\\\")+"');");
				}
			}
		}
	    this.setMainMessage("打印完毕");
	}

	return EventRtnType.NORMAL_SUCCESS;
}

// Clob类型 转String
public String ClobToString(Clob clob) throws SQLException, IOException {
  String reString = "";
  Reader is = clob.getCharacterStream();// 得到流
  BufferedReader br = new BufferedReader(is);
  String s = br.readLine();
  StringBuffer sb = new StringBuffer();
  while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
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



	/* 无锡加 */

	/**
	 * 干部处审核
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
		//人员内码
		String[] a0000_array = a0000s.split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);

		HBSession session = HBUtil.getHBSession();

		//校验人员是否已被审核
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
			this.setMainMessage(sb.substring(0, sb.length()-1)+"等已审核!");
			return EventRtnType.FAILD;
		} else{
			//记录日志
			A01 a01_new;
			A01 a01_temp;
			for (A01 a01 : a01_list) {
				a01_new = new A01();
				a01_temp = new A01();
				a01_temp.setA0190(a01.getA0190());
				a01_new.setA0190("1");
				applog.createLog("3801", "A01", a01.getA0000(), a01.getA0101(), "干部处审核", new Map2Temp().getLogInfo(a01_temp, a01_new));
			}

			//所选人员没被审核，则更新为干部处已审
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

		this.setMainMessage("审核成功!");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 干部一处审核
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
		//人员内码
		String[] a0000_array = a0000s.split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);

		HBSession session = HBUtil.getHBSession();

		//校验人员是否已被审核
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
			this.setMainMessage(sb.substring(0, sb.length()-1)+"等已审核!");
			return EventRtnType.FAILD;
		} else{
			//记录日志
			A01 a01_new;
			for (A01 a01 : a01_list) {
				a01_new = new A01();
				a01_new.setA0189("1");
				applog.createLog("3802", "A01", a01.getA0000(), a01.getA0101(), "干部一处审核", new Map2Temp().getLogInfo(a01, a01_new));
			}

			//所选人员没被审核，则更新为干部处已审
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

		this.setMainMessage("审核成功!");
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 干部一处解锁
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
		String flag=a0000s.split(",")[0];//1干部处取消审核，2干部一处取消审核，all取消所有审核

		String[] a0000_array = a0000s.split(",")[1].split("@#@");
		List<String> a0000_list = Arrays.asList(a0000_array);

		HBSession session = HBUtil.getHBSession();

		//获取所选人员
		List<A01> a01_list = new ArrayList<A01>();
		A01 temp;
		for (String a0000 : a0000_list) {
			temp = (A01) session.get(A01.class, a0000);
			a01_list.add(temp);
		}

		//记录日志
		A01 a01_new;
		for (A01 a01 : a01_list) {
			a01_new = new A01();
			String logtext="";
			if("1".equals(flag)){// A0190干部处
				a01_new.setA0190("0");
				logtext="干部处解锁审核";
			}else if("2".equals(flag)){
				a01_new.setA0189("0");
				logtext="干部一处解锁审核";
			}else{
				a01_new.setA0189("0");
				a01_new.setA0190("0");
				logtext="干部处、干部一处解锁审核";
			}
			applog.createLog("3803", "A01", a01.getA0000(), a01.getA0101(), logtext, new Map2Temp().getLogInfo(a01, a01_new));
		}


		String sql;
		if(a0000_list.size()>=1000){
			if("1".equals(flag)){// A0190干部处
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
			if("1".equals(flag)){// A0190干部处
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

		this.setMainMessage("取消成功!");
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
				this.setMainMessage("导出成功");
			}else{
				this.setMainMessage("导出失败");
			}
		}

	}

	/* poi导出excel
	 *
	 * gl
	 * */
	@PageEvent("expExcelFromGrid")
	public int expExcelFromGrid() throws RadowException, AppException {
		PageElement pe = this.getPageElement("peopleInfoGrid");
		String sql = (String) this.request.getSession().getAttribute("allSelect");
		String field = "	select (" +
				"		SELECT" +
				"			CODE_NAME" +
				"		FROM" +
				"			code_value" +
				"		WHERE" +
				"			code_type = 'GB3304'" +
				"		AND code_value = A0117" +
				"	) AS a01171, "+
				" (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB09' " +
				"           AND code_value = A0221) AS A02211, "
				+ "(SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB148' " +
				"           AND code_value = A0192E) AS A0192E1, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB134' " +
				"           AND code_value = A0120) AS A01201, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB139' " +
				"           AND code_value = A0122) AS A01221, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB130' " +
				"           AND code_value = A0165) AS A01651, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB125' " +
				"           AND code_value = A0160) AS A01601, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB135' " +
				"           AND code_value = A0121) AS A01211, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB135' " +
				"           AND code_value = A0197) AS A01971, " +
				"        (SELECT CODE_NAME " +
				"          FROM code_value " +
				"         WHERE code_type = 'ZB126' " +
				"           AND code_value = A0163) AS A01631,"+
				" zzxl as zzxl1,qrzxl as qrzxl1,";
		sql = sql.substring(6);
		sql = field + sql;
		List<Map<String, Object>> list = this.exportPoi(sql);
		if (list == null) {
			this.setMainMessage("查询失效！请重选机构树");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String infile = this.insertList(list,pe);
		infile = infile + "人员信息.xls";
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.downloadword()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* poi导出excel
	 *
	 * gl
	 * */
	@PageEvent("expExcelFromGrid2")
	public int expExcelFromGrid2(String excelname) throws Exception {
		PageElement pe = this.getPageElement("peopleInfoGrid");
		String sql = (String) this.request.getSession().getAttribute("allSelect");
		
		
		String infile = ExpExcelPList.insertList(sql,excelname+ ".xls");
		this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		this.getExecuteSG().addExecuteCode("window.downloadword()");
		return EventRtnType.NORMAL_SUCCESS;
	}


	public List<Map<String, Object>> exportPoi(String sql) throws RadowException, AppException {
		List<Map<String, Object>> list = null;
		HBSession session = HBUtil.getHBSession();
		CommonQueryBS query = new CommonQueryBS();
		query.setConnection(session.connection());
		query.setQuerySQL(sql);
		Vector<?> vector = query.query();
		Iterator<?> iterator = vector.iterator();
		if (iterator.hasNext()) {
			list = new ArrayList<Map<String, Object>>();
			while (iterator.hasNext()) {
				Map<String, Object> tmp = (Map<String, Object>) iterator.next();
				if(tmp.containsKey("a0104")) {
				String sex=(String) tmp.get("a0104");
				if(sex.equals("1")) {
					sex="男";
					tmp.put("a0104", sex);
				}else {
					sex="女";
					tmp.put("a0104", sex);
				}
				}
				//是否具有两年以上基层工作经历
				if(tmp.containsKey("a0197")) {
				String a0197=(String) tmp.get("a0197");
				if(a0197.equals("1")) {
					a0197="是";
					tmp.put("a0197", a0197);
				}else {
					a0197="否";
					tmp.put("a0197", a0197);
				}
				}
				//时间格式化
				String a0107=(String) tmp.get("a0107");
				if(a0107!=null&&a0107!="") {
					a0107=this.DataFormat(a0107);
					tmp.put("a0107", a0107);
				}
				String a0288=(String) tmp.get("a0288");
				if(a0288!=null&&a0288!="") {
					a0288=this.DataFormat(a0288);
					tmp.put("a0288", a0288);
				}
				String a0134=(String) tmp.get("a0134");
				if(a0134!=null&&a0134!="") {

					a0134=this.DataFormat(a0134);
					tmp.put("a0134", a0134);

				}


				String a0192c=(String) tmp.get("a0192c");
				if(a0192c!=null&&a0192c!="") {
					a0192c=this.DataFormat(a0192c);
					tmp.put("a0192c", a0192c);
				}

				String a2949=(String) tmp.get("a2949");
				if(a2949!=null&&a2949!="") {

					a2949=this.DataFormat(a2949);
					tmp.put("a2949", a2949);

				}

				list.add(tmp);

			}
		}

	return list;
	}
	public  String insertList(List<Map<String, Object>> list,PageElement pe) {
		List<HashMap<String, String>> stringValueList = pe.getStringValueList();
		//将页面显示字段放入一个map中
		HashMap<String, String> gridMap=new HashMap<String, String>();
		for(HashMap<String, String> map:stringValueList) {
			Set<Entry<String,String>> entrySet = map.entrySet();
			for(Entry<String,String> entry:entrySet) {
				gridMap.put(entry.getKey(), entry.getValue());
			}
		}

		ExportAsposeBS exp = new ExportAsposeBS();
		//poi导出excel
		String rootPath = ExportAsposeBS.getRootPath();
		String tempPath = rootPath + "gbhmc3.xls";
		String path = ExpRar.expFile();
		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = exp.createNewFile1(tempPath, path);
		InputStream is = null;

		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			HSSFWorkbook workbook = new HSSFWorkbook(is); //
			HSSFCellStyle setBorder = workbook.createCellStyle();

			HSSFFont font = workbook.createFont();

			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
			setBorder.setFont(font);
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			OutputStream fos = new FileOutputStream(newFile);
			Row row = sheet.getRow(0);
			    //根据表格显示，拼接导出Excel显示的字段
			    int temp=0;
			    if(gridMap.containsKey("a0000")) {

			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("姓名");  //名字
			    	temp++;
			    }
			    if(gridMap.containsKey("a0192a")) {
			    	sheet.setColumnWidth(temp, 256*50+184);//256*width+184
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("现任职务");  //现任职务
			    	temp++;
			    }
			    if(gridMap.containsKey("a0104")) {
			    	sheet.setColumnWidth(temp, 256*4+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("性别");  //性别
			    	temp++;
			    }
			    if(gridMap.containsKey("a0117")) {
			    	sheet.setColumnWidth(temp, 256*4+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("民族");  //民族
			    	temp++;
			    }
			    if(gridMap.containsKey("a0111")) {
			    	sheet.setColumnWidth(temp, 256*10+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("籍贯");  //籍贯
			    	temp++;
			    }
			    if(gridMap.containsKey("qrzxl")) {
			    	sheet.setColumnWidth(temp, 256*15+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("学历（全日制）");  //全日制学历
			    	temp++;
			    }
			    if(gridMap.containsKey("qrzxlxx")) {
			    	sheet.setColumnWidth(temp, 256*26+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("毕业院校及专业（全日制）");  //全日制学历信息
			    	temp++;
			    }
			    if(gridMap.containsKey("zzxl")) {
			    	sheet.setColumnWidth(temp, 256*16+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("学历（在职教育）");  //在职学历
			    	temp++;
			    }
			    if(gridMap.containsKey("zzxlxx")) {
			    	sheet.setColumnWidth(temp, 256*27+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("毕业院校及专业（在职教育）");  //在职学历信息
			    	temp++;
			    }
			    if(gridMap.containsKey("a0107")) {
			    	sheet.setColumnWidth(temp, 256*8+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("出生年月");  //出生年月
			    	temp++;
			    }
			    if(gridMap.containsKey("a0140")) {
			    	sheet.setColumnWidth(temp, 256*8+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("入党时间");  //入党时间
			    	temp++;
			    }
			    if(gridMap.containsKey("nl")) {
			    	sheet.setColumnWidth(temp, 256*4+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("年龄");  //年龄
			    	temp++;
			    }
			    if(gridMap.containsKey("a0134")) {
			    	sheet.setColumnWidth(temp, 256*12+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("参加工作时间");  //参加工作时间
			    	temp++;
			    }
			    if(gridMap.containsKey("a0192f")) {
			    	sheet.setColumnWidth(temp, 256*12+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("任现职时间");  //任现职时间
			    	temp++;
			    }
			    if(gridMap.containsKey("a0192c")) {
			    	sheet.setColumnWidth(temp, 256*13+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("任现职级时间");  //任现职级时间
			    	temp++;
			    }
			    if(gridMap.containsKey("a0184")) {
			    	sheet.setColumnWidth(temp, 256*20+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("身份证号");  //身份证号
			    	temp++;
			    }
			    if(gridMap.containsKey("a0192")) {
			    	sheet.setColumnWidth(temp, 256*30+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("工作单位及职务简称");  //工作单位及职务简称
			    	temp++;
			    }
			    if(gridMap.containsKey("a0114")) {
			    	sheet.setColumnWidth(temp, 256*10+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("出生地");  //出生地
			    	temp++;
			    }
			    if(gridMap.containsKey("qrzxw")) {
			    	sheet.setColumnWidth(temp, 256*13+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高全日制学位");  //最高全日制学位
			    	temp++;
			    }
			    if(gridMap.containsKey("qrzxwxx")) {
			    	sheet.setColumnWidth(temp, 256*20+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("院校系专业");  //最高全日制学位院校系专业
			    	temp++;
			    }
			    if(gridMap.containsKey("zzxw")) {
			    	sheet.setColumnWidth(temp, 256*11+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高在职学位");  //最高在职学位
			    	temp++;
			    }
			    if(gridMap.containsKey("zzxwxx")) {
			    	sheet.setColumnWidth(temp, 256*20+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高在职学位院校系专业");  //最高在职学位院校系专业
			    	temp++;
			    }
			    if(gridMap.containsKey("a0221")) {
			    	sheet.setColumnWidth(temp, 256*11+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("职务层次");  //职务层次
			    	temp++;
			    }
			    if(gridMap.containsKey("a0288")) {
			    	sheet.setColumnWidth(temp, 256*15+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("任现职务层次时间");  //任现职务层次时间
			    	temp++;
			    }
			    if(gridMap.containsKey("a0192e")) {
			    	sheet.setColumnWidth(temp, 256*13+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("现职级");  //现职级
			    	temp++;
			    }
			    if(gridMap.containsKey("a0120")) {
			    	sheet.setColumnWidth(temp, 256*7+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("级别");  //级别
			    	temp++;
			    }
			    if(gridMap.containsKey("a0196")) {
			    	sheet.setColumnWidth(temp, 256*15+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("专业技术职称");  //专业技术职称
			    	temp++;
			    }
			    if(gridMap.containsKey("a0122")) {
			    	sheet.setColumnWidth(temp, 256*23+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("专业技术类公务员任职资格");  //专业技术类公务员任职资格
			    	temp++;
			    }
			    if(gridMap.containsKey("a0187a")) {
			    	sheet.setColumnWidth(temp, 256*30+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("专长");  //专长
			    	temp++;
			    }
			    if(gridMap.containsKey("a0165")) {
			    	sheet.setColumnWidth(temp, 256*23+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("管理类别");  //管理类别
			    	temp++;
			    }
			    if(gridMap.containsKey("a0160")) {
			    	sheet.setColumnWidth(temp, 256*23+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("人员类别");  //人员类别
			    	temp++;
			    }
			    if(gridMap.containsKey("a0121")) {
			    	sheet.setColumnWidth(temp, 256*18+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("编制类型");  //编制类型
			    	temp++;
			    }
			    if(gridMap.containsKey("a2949")) {
			    	sheet.setColumnWidth(temp, 256*13+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("公务员登记时间");  //公务员登记时间
			    	temp++;
			    }
			    if(gridMap.containsKey("a0197")) {
			    	sheet.setColumnWidth(temp, 256*22+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("是否两年以上基层工作经历");  //是否两年以上基层工作经历
			    	temp++;
			    }
			    if(gridMap.containsKey("a0128")) {
			    	sheet.setColumnWidth(temp, 256*7+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("健康状况");  //健康状况
			    	temp++;
			    }
			    if(gridMap.containsKey("a0163")) {
			    	sheet.setColumnWidth(temp, 256*13+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("人员管理状态");  //人员管理状态
			    	temp++;
			    }
			    if(gridMap.containsKey("zgxl")) {
			    	sheet.setColumnWidth(temp, 256*8+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高学历");  //最高学历
			    	temp++;
			    }
			    if(gridMap.containsKey("zgxlxx")) {
			    	sheet.setColumnWidth(temp, 256*27+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高学历学校");  //最高学历学校
			    	temp++;
			    }
			    if(gridMap.containsKey("zgxw")) {
			    	sheet.setColumnWidth(temp, 256*8+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高学位");  //最高学位
			    	temp++;
			    }
			    if(gridMap.containsKey("zgxwxx")) {
			    	sheet.setColumnWidth(temp, 256*27+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("最高学位学校");  //最高学位学校
			    	temp++;
			    }
			    if(gridMap.containsKey("a0195")) {
			    	sheet.setColumnWidth(temp, 256*24+184);
			    	Cell cell = row.createCell(temp);
			    	cell.setCellStyle(setBorder);
			    	cell.setCellValue("统计关系所在单位");  //统计关系所在单位
			    	temp++;
			    }


	           for (int i = 0; i < list.size(); i++) {
	        		Map<String, Object> map = list.get(i);
	        	   row = sheet.createRow(i+1);
	        	   	int num =0;
	        	   	if(gridMap.containsKey("a0000")) {
				    	Cell cell = row.createCell(num);
				    	cell.setCellValue((String) map.get("a0101"));  //名字
				    	num++;
				    }
				    if(gridMap.containsKey("a0192a")) {
				    	Cell cell1 = row.createCell(num);
				    	cell1.setCellValue((String) map.get("a0192a"));  //现任职务
				    	num++;
				    }
				    if(gridMap.containsKey("a0104")) {
				    	Cell cell2 = row.createCell(num);
				    	cell2.setCellValue((String) map.get("a0104"));   //性别
				    	num++;
				    }
				    if(gridMap.containsKey("a0117")) {
			    	    Cell cell3 = row.createCell(num);
		                cell3.setCellValue((String) map.get("a01171"));    //民族
				    	num++;
				    }
				    if(gridMap.containsKey("a0111")) {
				    	Cell cell4 = row.createCell(num);
		                cell4.setCellValue((String) map.get("a0111"));    //籍贯
				    	num++;
				    }
				    if(gridMap.containsKey("qrzxl")) {
				    	Cell cell5 = row.createCell(num);
		                cell5.setCellValue((String) map.get("qrzxl1"));    //全日制最高学历
				    	num++;
				    }
				    if(gridMap.containsKey("qrzxlxx")) {
				    	Cell cell6 = row.createCell(num);
		                cell6.setCellValue((String) map.get("qrzxlxx"));    //全日制最高学历学校
				    	num++;
				    }
				    if(gridMap.containsKey("zzxl")) {
				    	Cell cell7 = row.createCell(num);
		                cell7.setCellValue((String) map.get("zzxl1"));    //在职最高学历
				    	num++;
				    }
				    if(gridMap.containsKey("zzxlxx")) {
			    	    Cell cell8 = row.createCell(num);
		                cell8.setCellValue((String) map.get("zzxlxx"));    //在职最高学历学校
				    	num++;
				    }
				    if(gridMap.containsKey("a0107")) {
				    	Cell cell9 = row.createCell(num);
		                cell9.setCellValue((String) map.get("a0107"));    //出生年月
				    	num++;
				    }
				    if(gridMap.containsKey("a0140")) {
				    	Cell cell10 = row.createCell(num);
		                cell10.setCellValue((String) map.get("a0140"));    //入党时间
				    	num++;
				    }
				    if(gridMap.containsKey("nl")) {
			    	    Cell cell11 = row.createCell(num);
		                cell11.setCellValue((String) map.get("nl"));    //年龄
				    	num++;
				    }
				    if(gridMap.containsKey("a0134")) {
				    	Cell cell14 = row.createCell(num);
		                cell14.setCellValue((String) map.get("a0134"));    //参加工作时间
				    	num++;
				    }
				    if(gridMap.containsKey("a0192f")) {
				    	Cell cell12 = row.createCell(num);
			            cell12.setCellValue((String) map.get("a0192f"));    //现任职时间
				    	num++;
				    }
				    if(gridMap.containsKey("a0192c")) {
				    	Cell cell13 = row.createCell(num);
			            cell13.setCellValue((String) map.get("a0192c"));    //现任职级时间
				    	num++;
				    }
				    if(gridMap.containsKey("a0184")) {
				    	Cell cell14 = row.createCell(num);
			            cell14.setCellValue((String) map.get("a0184"));    //身份证号
				    	num++;
				    }
				    if(gridMap.containsKey("a0192")) {
				    	Cell cell15 = row.createCell(num);
			            cell15.setCellValue((String) map.get("a0192"));    //工作单位及职务简称
				    	num++;
				    }
				    if(gridMap.containsKey("a0114")) {
				    	Cell cell16 = row.createCell(num);
			            cell16.setCellValue((String) map.get("a0114"));  //出生地
			            num++;
				    }
				    if(gridMap.containsKey("qrzxw")) {
				    	Cell cell17 = row.createCell(num);
			            cell17.setCellValue((String) map.get("qrzxw"));  //最高全日制学位
			            num++;
				    }
				    if(gridMap.containsKey("qrzxwxx")) {
				    	Cell cell18 = row.createCell(num);
			            cell18.setCellValue((String) map.get("qrzxwxx"));  //最高全日制学位院校系专业
			            num++;
				    }
				    if(gridMap.containsKey("zzxw")) {
				    	Cell cell19 = row.createCell(num);
			            cell19.setCellValue((String) map.get("zzxw"));  //最高在职学位
			            num++;
				    }
				    if(gridMap.containsKey("zzxwxx")) {
				    	Cell cell20 = row.createCell(num);
			            cell20.setCellValue((String) map.get("zzxwxx"));  //最高在职学位院校系专业
			            num++;
				    }
				    if(gridMap.containsKey("a0221")) {
				    	Cell cell21 = row.createCell(num);
			            cell21.setCellValue((String) map.get("a02211")); //职务层次
			            num++;
				    }
				    if(gridMap.containsKey("a0288")) {
				    	Cell cell22 = row.createCell(num);
			            cell22.setCellValue((String) map.get("a0288"));  //任现职务层次时间
			            num++;
				    }
				    if(gridMap.containsKey("a0192e")) {
				    	Cell cell22 = row.createCell(num);
			            cell22.setCellValue((String) map.get("a0192e1"));  //现职级
			            num++;
				    }
				    if(gridMap.containsKey("a0120")) {
				    	Cell cell22 = row.createCell(num);
			            cell22.setCellValue((String) map.get("a01201"));  //级别
			            num++;
				    }
				    if(gridMap.containsKey("a0196")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a0196"));   //专业技术职称
				    	num++;
				    }
				    if(gridMap.containsKey("a0122")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a01221"));   //专业技术类公务员任职资格
				    	num++;
				    }
				    if(gridMap.containsKey("a0187a")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a0187a"));  //专长
				    	num++;
				    }
				    if(gridMap.containsKey("a0165")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a01651"));  //管理类别
				    	num++;
				    }
				    if(gridMap.containsKey("a0160")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a01601"));  //人员类别
				    	num++;
				    }
				    if(gridMap.containsKey("a0121")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a01211"));  //编制类型
				    	num++;
				    }
				    if(gridMap.containsKey("a2949")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a2949"));   //公务员登记时间
				    	num++;
				    }
				    if(gridMap.containsKey("a0197")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a0197")); //是否两年以上基层工作经历
				    	num++;
				    }
				    if(gridMap.containsKey("a0128")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a0128")); //健康状况
				    	num++;
				    }
				    if(gridMap.containsKey("a0163")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a01631"));  //人员管理状态
				    	num++;
				    }
				    if(gridMap.containsKey("zgxl")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("zgxl"));  //最高学历
				    	num++;
				    }
				    if(gridMap.containsKey("zgxlxx")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("zgxlxx"));  //最高学历学校
				    	num++;
				    }
				    if(gridMap.containsKey("zgxw")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("zgxw"));  //最高学位
				    	num++;
				    }
				    if(gridMap.containsKey("zgxwxx")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("zgxwxx"));  //最高学位学校
				    	num++;
				    }
				    if(gridMap.containsKey("a0195")) {
				    	Cell cell = row.createCell(num);
			            cell.setCellValue((String) map.get("a0195"));  //统计关系所在单位
				    	num++;
				    }


	            }

			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;


	}
	public  String insertList(List<Map<String, Object>> list) {
		ExportAsposeBS exp = new ExportAsposeBS();
		//poi导出excel
		String rootPath = ExportAsposeBS.getRootPath();
		String tempPath = rootPath + "gbhmc3.xls";
		String path = ExpRar.expFile();
		// 读取excel模板，并复制到新文件中供写入和下载
		File newFile = exp.createNewFile1(tempPath, path);
		InputStream is = null;

		try {
			// 将excel文件转为输入流
			is = new FileInputStream(newFile);
			// 创建个workbook，
			/* XSSFWorkbook workbook = new XSSFWorkbook(is); */
			Workbook workbook = new HSSFWorkbook(is); //
			// 获取第一个sheet
			Sheet sheet = workbook.getSheetAt(0);
			OutputStream fos = new FileOutputStream(newFile);
			Row row = sheet.getRow(0);
	           for (int i = 0; i < list.size(); i++) {
	        		Map<String, Object> map = list.get(i);
	        	   row = sheet.createRow(i+1);

	                Cell cell = row.createCell(0);
	                cell.setCellValue((String) map.get("a0101"));  //名字

	                Cell cell1 = row.createCell(1);
	                cell1.setCellValue((String) map.get("a0192a"));  //现任职务

	                Cell cell2 = row.createCell(2);
	                cell2.setCellValue((String) map.get("a0104"));   //性别

	                Cell cell3 = row.createCell(3);
	                cell3.setCellValue((String) map.get("a01171"));    //民族

	                Cell cell4 = row.createCell(4);
	                cell4.setCellValue((String) map.get("a0111"));    //籍贯

	                Cell cell5 = row.createCell(5);
	                cell5.setCellValue((String) map.get("qrzxl1"));    //全日制最高学历

	                Cell cell6 = row.createCell(6);
	                cell6.setCellValue((String) map.get("qrzxlxx"));    //全日制最高学历


	                Cell cell7 = row.createCell(7);
	                cell7.setCellValue((String) map.get("zzxl1"));    //在职最高学历

	                Cell cell8 = row.createCell(8);
	                cell8.setCellValue((String) map.get("zzxlxx"));    //在职最高学历

	                Cell cell9 = row.createCell(9);
	                cell9.setCellValue((String) map.get("a0107"));    //出生年月

	                Cell cell10 = row.createCell(10);
	                cell10.setCellValue((String) map.get("a0140"));    //入党时间

	                Cell cell11 = row.createCell(11);
	                cell11.setCellValue((String) map.get("nl"));    //年龄

	                Cell cell14 = row.createCell(12);
	                cell14.setCellValue((String) map.get("a0134"));    //参加工作时间

	                Cell cell12 = row.createCell(13);
	                cell12.setCellValue((String) map.get("a0192f"));    //现任职时间

	                Cell cell13 = row.createCell(14);
	                cell13.setCellValue((String) map.get("a0192c"));    //现任职级时间

	            }

			workbook.write(fos);
			fos.flush();
			fos.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return path;


	}
	/* 时间格式化处理 */
	public String DataFormat(String date) {
		String result=null;
		String one=date.substring(0, 4);
		String two =date.substring(4,6);
		result=one+"."+two;
		return result;
	}

	/**
	 * 预警信息
	 * @return
	 * @throws ParseException
	 */
	@PageEvent("warningPerson")
	public int warningPerson() throws ParseException {
		String sid = this.request.getSession().getId();
		sid = "a01.a0000,'"+sid+"' sessionid";
		String userid = SysManagerUtils.getUserId();
		String time = com.insigma.siis.local.util.DateUtil.formatDateStr(new Date(), "yyyyMMdd");
		String manTX = com.insigma.siis.local.util.DateUtil.plusDay(-365*59, time, "yyyyMMdd");//男性退休时间
		//获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		String dateNowStr = sdf.format(new Date());
		//获取三十天后的日期
	    long days = System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 30);
	    Date date = new Date();
		date.setTime(days);
		String dateStr = sdf.format(date);
		//取出数据库中现职务任职日期
		String subDate = "substr(decode(length(A0192C),8,A0192C,6,concat(A0192C,'01')),5,8)";
		//当当前月份为十二月时 判断时间范围条件为or 其他为and
		String condition;
		if(dateNowStr.substring(0,2).equals("12")) {
			 condition = "or";
		}else {
			 condition = "and";
		}
		@SuppressWarnings("unused")
		String womanTX = com.insigma.siis.local.util.DateUtil.plusDay(-365*55, time, "yyyyMMdd");//女性退休时间
		String [] text = {"试用期满 一年人员共（NUM）人","59岁及以上的人员(NUM)","任现职级时间在当前日期三十天后人员(NUM)"};
		String [] sql  = {"select  sid "//统一替代符
				          + "from A01 a01  where fkly is null and  1=1  and concat(a01.a0000, '') in "
				          + "(select a02.a0000 from a02 where a02.A0201B in "
				          + "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"') "
				          + "and a02.a0281='true'  and a02.a0215a like '%试用期%' and a02.a0255 = '1' "
				          + "and decode(length(a02.a0243),8,a02.a0243,6,concat(a02.a0243,'01'))<='"+time+"')  and a0163='1'"
				         ,"select  sid "//统一替代符
				          + "from A01 a01  where fkly is null and  1=1  and concat(a01.a0000, '') in "
				          + "(select a02.a0000 from a02 where a02.A0201B in "
				          + "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"') and a02.a0281='true' )  "
				          + "and a0163='1' and "
				          + " decode(length(a01.a0107),8,a01.a0107,6,concat(a01.a0107,'01'))<'"+manTX+"'"
				          ,"select  sid "//统一替代符
				          + "from A01 a01  where fkly is null and  1=1  and concat(a01.a0000, '') in "
				          + "(select a02.a0000 from a02 where a02.A0201B in "
				          + "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"')"
                          + "and a02.a0255 = '1' ) "
				          + "and a0163='1' and "
                          + " "+subDate+" >= "+dateNowStr+" "+condition+"   "+subDate+" <= "+dateStr+" "};
		getCountFW(text,sql);
		for (int i = 0; i < sql.length; i++) {
			sql[i] = sql[i].replace("sid", sid);
		}
		String warningJson = getWarningJson(text,sql);
		this.getExecuteSG().addExecuteCode("warningPerson("+warningJson+")");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 根据sql执行，取得人数替换text
	 */
	private void getCountFW(String [] text , String [] sql ) {
		String [] tempsql = new String[sql.length];
		StringBuffer allsql = new StringBuffer("select * from ");
		for (int i = 0; i < tempsql.length; i++) {
			tempsql[i]=sql[i].replace("sid", "count(1) a"+i);
			allsql.append("("+tempsql[i]+"),");
		}
		allsql.deleteCharAt(allsql.length()-1);
		HBSession session = HBUtil.getHBSession();

		if(sql.length>1)
		{
			Object [] result = (Object [])session.createSQLQuery(allsql.toString()).uniqueResult();
		for (int i = 0; i < result.length; i++) {
			if(result[i]==null||result[i].toString().equals("0"))
			{
				text[i]="-1";
				sql[i]="-1";
			}
			else {
				text[i] = text[i].replace("NUM", result[i].toString());
			}
		}
		}else {
			Object result = session.createSQLQuery(allsql.toString()).uniqueResult();
			if (result == null || result == new BigDecimal(0)) {
				text[0] = "-1";
				sql[0] = "-1";
			} else {
				text[0] = text[0].replace("NUM", "" + result);
			}
		}

	}
	private String getWarningJson(String [] text , String [] sql ) {
		StringBuffer json = new StringBuffer("[");
		for (int i = 0; i < sql.length; i++) {
			if(sql[i].equals("-1"))//为-1时意味着该条件无异常人员
				continue;
			json.append("{");
			json.append("xtype:'button', width: 300, height: 20,");
			json.append("value:'"+sql[i].replace("'", "\"")+"',text:'"+text[i]+"',");
			json.append("handler: 'function() {runSql(this.value); }'");
			json.append("},");
		}
		if(json.toString().equals("["))//全部为空时，返回空的JSON数组
			return "[]";
		json.deleteCharAt(json.length()-1);
		json.append("]");
		return json.toString();
	}
	public String  getName(PageElement pe) {
		String name = null;

		List<HashMap<String, Object>> list = pe.getValueList();
		for (int j = 0; j < list.size(); j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 =  map.get("personcheck");

			if (check1 != null && check1.equals(true)) {

				 name =map.get("a0101").toString();
			}
		}
		return name;

	}
	public String getUserName(String a0000,String tpid) throws Exception {
		Map<String, Object> dataMap;
		dataMap = FiledownServlet.getMap(a0000,tpid);
		String a0101 = (String) dataMap.get("a0101");
		return ExportAsposeBS.getHanziInitials(a0101);
	}

	/**
	 * 将上传文件时间存入数据库
	 * @param updateTime
	 */
	@PageEvent("sentUpdateTime")
	public int sentUpdateTime(String updateTime) {
		HBSession sess = HBUtil.getHBSession();
		String userID = SysManagerUtils.getUserId();
		try {
			sess.beginTransaction();
			String excelPath = this.getPageElement("excelfile").getValue();
			excelPath =  URLDecoder.decode(excelPath,"UTF-8");
			String countuser_str = sess.createSQLQuery("select count(*) from XLSUPDATE where USERID = '"+userID+"'").uniqueResult().toString();
			Integer countuser = Integer.parseInt(countuser_str);
			if(countuser==0) {
			sess.createSQLQuery("insert into XLSUPDATE(USERID,EXCELPATH,LATESTDATE) "
					+ "values('"+userID+"','"+excelPath+"','"+updateTime+"')");
			}
			else{
				sess.createSQLQuery("update XLSUPDATE t set t.USERID = '"+userID+"',"+
						" t.EXCELPATH = '"+excelPath+"',"+
						" t.LATESTDATE = '" + updateTime+"'").executeUpdate();
			}
			sess.getTransaction().commit();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String getLatestDate() {
		String latestDate = "";
		String userID = SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		String XLSSQL = "select LATESTDATE from XLSUPDATE where USERID = '"+userID+"'";
		latestDate = (String) sess.createSQLQuery(XLSSQL).uniqueResult();
		return latestDate;
	}
	@PageEvent("exportZipbtn_Hy.onclick")//干部人事数据导出
	public int exportZipBtn(String name) throws RadowException{

		exportZipbtn2();
		return EventRtnType.NORMAL_SUCCESS;
	}
	private void exportZipbtn2()throws RadowException {
		try {
			String id = UUID.randomUUID().toString().replace("-", "");
			String linkpsnthis=this.getPageElement("linkpsn").getValue();
			String linktel=this.getPageElement("linktel").getValue();
			//Zip标记
			String sign = "zip";
			String a01Sql = "SELECT *  FROM a01 ";
		    CurrentUser user = SysUtil.getCacheCurrentUser();
		    KingbsconfigBS.saveImpDetailInit3(id);
		    UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
		    DataZipImpDBThread thr=new DataZipImpDBThread(id,user,sign,linkpsnthis,linktel,userVo);
		 	new Thread(thr,"数据导出线程1").start();
			this.setRadow_parent_data(id);
			this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出详情',600,300,'"+id+"','"+request.getContextPath()+"');");
//				this.getExecuteSG().addExecuteCode("parent.Ext.getCmp(subWinId).close(); ");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
//	public String getExcelPath() {
//		String ExcelPath = "";
//		String userID = SysManagerUtils.getUserId();
//		HBSession sess = HBUtil.getHBSession();
//		String xlsSQL = "select EXCELPATH from XLSUPDATE where USERID = '"+userID+"'";
//		ExcelPath = (String) sess.createSQLQuery(xlsSQL).uniqueResult();
//		return ExcelPath;
//	}
//
	@PageEvent("Eliminate")
	public void Eliminate() throws RadowException{
		System.out.println("qingchu");
		this.getPageElement("zb09Words").setValue("");
	}

	@PageEvent("tpbj.onclick")
    public int tpbj() throws RadowException {
        String tableType = this.getPageElement("tableType").getValue();
        LinkedHashSet<String> selected = new LinkedHashSet<String>();
        // 从cookie中的获取之前选择的人员id
        Cookie[] cookies = this.request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if ("jggl.tpbj.ids".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
                    String[] ids = cookieValue.split("#");
                    for (String id : ids) {
                    	if(!StringUtils.isEmpty(id)){
                        	selected.add(id);
                        }
                    }
                }
            }
        }
        // 从列表或者小资料中获取选择的人员
        if ("1".equals(tableType)) { // 列表
            List<HashMap<String, Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
            for (int i = 0; i < list.size(); i++) {
                HashMap<String, Object> item = list.get(i);
                Object check = item.get("personcheck");
                if (check != null && check.equals(true)) {
                    String id = item.get("a0000") == null ? "" : item.get("a0000").toString();
                    selected.add(id);
                }
            }
        } else if ("2".equals(tableType) || "3".equals(tableType)) {// 小资料、照片
            String a0000s = this.getPageElement("picA0000s").getValue();
            if (StringUtils.isNotBlank(a0000s)) {
                a0000s = a0000s.substring(0, a0000s.length() - 1);
                a0000s = a0000s.replaceAll("\\'", "");
                String[] strs = a0000s.split(",");
                selected.addAll(Arrays.asList(strs));
            }
        }
        if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
            this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
            return EventRtnType.NORMAL_SUCCESS;
        } else {
            String json = JSON.toJSONString(selected);
            this.getExecuteSG().addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','同屏比较',1500,731,null,'"+this.request.getContextPath()+"',null,{" + "maximizable:false,resizable:false,RMRY:'同屏比较',addPerson:true,data:" + json + "},true)");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	
	//公示
	@PageEvent("addpublicity")
	@Transaction
	public int addpublicity() throws RadowException{
		String idss = (String)request.getSession().getAttribute("personidy");
		HBSession sess=HBUtil.getHBSession();
		String sql_photo="";
		String sql_grjl="";
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			String[] arr=idss.split("@");
			if(arr.length>200) {
				this.setMainMessage("请选择200人进行处理!");
				return EventRtnType.NORMAL_SUCCESS;
			}else {
				idss=idss.replaceAll("\\|", "");
				idss="'"+idss.replaceAll("@", "','")+"'";
				try {
					Statement stmt = sess.connection().createStatement();
					//String sql="delete from publicity where a0000 in ("+idss+") and userid='"+SysManagerUtils.getUserId()+"'";
					//stmt.executeUpdate(sql);
					List<String> dup= HBUtil.getHBSession().createSQLQuery("select a0000 from publicity where userid='"+SysManagerUtils.getUserId()+"'").list();
					if(dup!=null&&dup.size()>0) {
					for(int i=0;i<dup.size();i++) {
						idss=idss.replace(dup.get(i), "重复人员");
					}
					}
					List<String> maxnumber= HBUtil.getHBSession().createSQLQuery("select sortid from (select nvl(sortid,0) sortid from publicity where userid='"+SysManagerUtils.getUserId()+"' order by nvl(sortid,0) desc) where rownum=1").list();
					int maxn;
					if(maxnumber!=null&&maxnumber.size()>0) {
						maxn=Integer.parseInt(String.valueOf(maxnumber.get(0)));
					}else {
						maxn=0;
					}
					String[] oneidss=idss.split(",");
					for(int i=0;i<oneidss.length;i++)
					{
						maxn=maxn+1;
						String sql = "insert into publicity (a0000,userid,sortid) select a0000,'"+SysManagerUtils.getUserId()+"',"+maxn+" from a01 where a0000 = "+oneidss[i];
						stmt.executeUpdate(sql);
					}
					stmt.close();
					this.setMainMessage("添加成功!");
					return EventRtnType.NORMAL_SUCCESS;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			this.setMainMessage("请先选择人员!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//历史名单
	@PageEvent("addHistoryPer")
	@Transaction
	public int addHistoryPer(String str) throws RadowException{
		String idss = (String)request.getSession().getAttribute("personidy");
		HBSession sess=HBUtil.getHBSession();
		String sql_photo="";
		String sql_grjl="";

		List<String> maxnumber= HBUtil.getHBSession().createSQLQuery("select sortid from (select nvl(sortid,0) sortid from historyPer where mdid='"+str+"' order by nvl(sortid,0) desc) where rownum=1").list();
		int maxn;
		if(maxnumber!=null&&maxnumber.size()>0) {
			maxn=Integer.parseInt(String.valueOf(maxnumber.get(0)));
		}else {
			maxn=0;
		}
		String userid=SysManagerUtils.getUserId();
		if(idss != null && !"".equals(idss)){
			//勾选人员id
			String[] arr=idss.split("@");
			if(arr.length>200) {
				this.setMainMessage("请选择200人进行处理!");
				return EventRtnType.NORMAL_SUCCESS;
			}else {
				idss=idss.replaceAll("\\|", "");
				idss="'"+idss.replaceAll("@", "','")+"'";
				try {
					Statement stmt = sess.connection().createStatement();
					//String sql="delete from publicity where a0000 in ("+idss+") and userid='"+SysManagerUtils.getUserId()+"'";
					//stmt.executeUpdate(sql);
					//List<String> dup= HBUtil.getHBSession().createSQLQuery("select a0000 from historyPer where mdid='"+str+"'").list();
					HBUtil.getHBSession().createSQLQuery("delete from historyPer where mdid='"+str+"' and a0000 in ("+idss+")").executeUpdate();
					/*if(dup!=null&&dup.size()>0) {
						for(int i=0;i<dup.size();i++) {
							idss=idss.replace(dup.get(i), "重复人员");
						}
					}*/
					String[] oneidss=idss.split(",");
					List list = HBUtil.getHBSession().createSQLQuery("select code from a01_config_init where dicid in ('qrzxlxw', 'qrzxlxwxx', 'zzxlxw', 'zzxlxwxx')").list();
					String ids = org.apache.commons.lang.StringUtils.join(list.toArray(),",");
					for(int i=0;i<oneidss.length;i++)
					{
						maxn=maxn+1;
						String sql = "insert into historyPer (a0000,a0101,a0104,a0117,a0111a,a0134,a0144,a0141,a0221,a0196,a0107,a0192a,a0192f,qrzxlxw,qrzxlxwxx,zzxlxw,zzxlxwxx,mdid,sortid,id,a0184,a0193,a1705,a0188) select a.a0000,a.a0101,a.a0104,a.a0117 , a.a0111a, a.a0134, a.a0144 ,a.a0141,a.a0221,a.a0196 ,a.a0107,a.a0192a,substr(replace(a0192f,'.',''),0,6),"+ids+",'"+str+"',"+maxn+",sys_guid(),a.a0184,'' a0193,'' a1705,'"+userid+"' from "
								+ " a01 a,a01searchtemp b  "
										+ "  where a.a0000=b.a0000 and  a.a0000 ="+oneidss[i]+"";
						stmt.executeUpdate(sql);                       
					}
					stmt.close();
					this.setMainMessage("添加成功!");
					return EventRtnType.NORMAL_SUCCESS;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			String sessionid=this.request.getSession().getId();
			HBUtil.getHBSession().createSQLQuery("delete from historyPer where mdid='"+str+"' and a0000 in (select a0000 from a01searchtemp b where b.sessionid='"+sessionid+"') ").executeUpdate();
			List list = HBUtil.getHBSession().createSQLQuery("select code from a01_config_init where dicid in ('qrzxlxw', 'qrzxlxwxx', 'zzxlxw', 'zzxlxwxx')").list();
			String ids = org.apache.commons.lang.StringUtils.join(list.toArray(),",");
			String sql = "insert into historyPer (a0000,a0101,a0104,a0117,a0111a,a0134,a0144,a0141,a0221,a0196,a0107,a0192a,a0192f,qrzxlxw,qrzxlxwxx,zzxlxw,zzxlxwxx,mdid,sortid,id,a0184,a0193,a1705,a0188) select a.a0000,a.a0101,a.a0104 ,a.a0117 , a.a0111a, a.a0134, a.a0144 ,a.a0141,a.a0221,a.a0196 ,a.a0107,a.a0192a, substr(replace(a0192f,'.','、'),0,6),"+ids+",'"+str+"',"+maxn+"+b.sort,sys_guid(),a.a0184,'' a0193,'' a1705,'"+userid+"' from "
					+ "  a01 a,a01searchtemp b  where b.sessionid='"+sessionid+"' and a.a0000=b.a0000 "
					//+ " and  b.a0000 not in (select a0000 from historyPer where mdid='"+str+"') "
							+ " order by b.sort";
			Statement stmt;
			try {
				stmt = sess.connection().createStatement();
				stmt.executeUpdate(sql);
				stmt.close();
				this.setMainMessage("添加成功!");
				return EventRtnType.NORMAL_SUCCESS;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
