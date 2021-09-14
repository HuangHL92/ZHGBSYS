package com.insigma.siis.local.pagemodel.customquery;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import oracle.sql.CLOB;

import org.apache.commons.codec.binary.Base64;

import com.aspose.words.FieldFillIn;
import com.fr.data.core.db.dml.Update;
import com.fr.third.org.apache.poi.hssf.record.ContinueRecord;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.DataToDB;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.util.ExcelToSql;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;

/**
 * 导出Pad数据，所有人员
 * @author 15084
 *
 */
public class DataPadImpDBThread implements Runnable {
	
	private String uuid;
	private UserVO userVo;
	private String padCons;
	private String excelPath;
	private String ids;
	
	public DataPadImpDBThread(String uuid,UserVO userVo) {
        this.uuid = uuid;
        this.userVo = userVo;
    }
	
	public DataPadImpDBThread(String uuid,UserVO userVo, String padCons) {
		this.uuid = uuid;
        this.userVo = userVo;
        this.padCons = padCons;
	}
	
	public DataPadImpDBThread(String uuid,UserVO userVo, String padCons, String excelPath) {
		this.uuid = uuid;
		this.userVo = userVo;
		this.padCons = padCons;
		this.excelPath = excelPath;
	}
	
	public DataPadImpDBThread(String uuid,UserVO userVo, String padCons, String excelPath, String ids) {
		this.uuid = uuid;
		this.userVo = userVo;
		this.padCons = padCons;
		this.excelPath = excelPath;
		this.ids = ids;
	}
	
	public void updateStatus(String sql) throws AppException {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = sess.beginTransaction();
		Connection conn = sess.connection();
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.executeUpdate();
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		trans.commit();
	}
	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String zipPath=getZipPath();
		Connection conn = sess.connection();
		File mdatafile = new File(this.getClass().getClassLoader().getResource("./mdata.db").getPath());
		File file = new File(zipPath+"mdata.db");
		File photofile=new File(this.getClass().getClassLoader().getResource("./photo.db").getPath());
		System.out.println(this.getClass().getClassLoader().getResource("./photo.db").getPath());
		File targetphotofile = new File(zipPath+"photo.db");
		String realPhotoPath=sess.createSQLQuery("select AAA005 from AA01 WHERE AAA001='PHOTO_PATH'").uniqueResult().toString()+"/";
		String pad = "",padStr = "",code_status="";
		String[] split = padCons.split(",");
		for(int i=0;i<split.length;i++) {//分离中管干部干部画像和新增的选框value值
			if(split[i].length()>3) {
				pad +=split[i]+",";
			}else {
				padStr += "'"+split[i]+"',";
			}
		}
		if (pad.length()>0) {
			padCons= pad.substring(0, pad.length()-1);
		}else {
			padCons="";
		}
		if(padStr.length()>3) {//有选中打勾
			String statusSql = "update code_value set code_status='0' where code_type='BZS01'";
			try {
				updateStatus(statusSql);
			} catch (AppException e) {
				e.printStackTrace();
			}
			padStr= padStr.substring(0, padStr.length()-1);
			code_status = "update code_value  set code_status='1' where code_value in ("+padStr+") and code_type='BZS01'";
		}else {//默认全选
			code_status = "update code_value  set code_status='1' where code_type='BZS01'";
		}
		try {
			updateStatus(code_status);
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		//增加权限
		//导出zip包中控制中管干部和干部画像
		String isZGGB = "";
		String showGBHX = "";
		if(!padCons.isEmpty()){
				//for(int i=0;i<padcons.length;i++){
					//String cons=padcons[i];
			if(!padCons.contains("isZGGB")){
				//isZGGB = " and (A01.A0165!='01' or A01.A0165 is null)";
			}if(padCons.contains("showGBHX")){
				showGBHX = " update pad_config set value='superleader' where name='padtype'";
			}else if(!padCons.contains("showGBHX)")){
				showGBHX = " update pad_config set value='businessleader' where name='padtype'";
			}
				//}
		}else {
			//isZGGB = " and (A01.A0165!='01' or A01.A0165 is null)";
			showGBHX = " update pad_config set value='businessleader' where name='padtype'";
		}
		String userid=userVo.getId();
		String b0111sql="select b0111 from competence_userdept where userid='"+userid+"'";
		String unitidsql="select unitid from b01 where b0111 in (" +b0111sql+")";
		String hdsy_tablestr=" UNITID,B1,B2,B3,B4,B5,B6,B7,B8,B9,B10,B11,B12,B13,B14,B15,B16,B17,B18,B19,B20,B21,B22,B23,B24,B25,B26,B27,B28,B29,B30,B31,B32,B33,B34,B35,B36,B37,B38,B39,B40,B41,B42,B43,B44,B45,B46,B47,B48,B49,B50,B51,B52,B53,B54,B55,B56,B57,B58,B59,B60,B61,B62,B63,B64,B65,B66,B67,B68,B69,B70,S1,S2,S3,S4,S5,S6,S7,S8,S9,S10,S11,S12,S13,S14,S15,S16,S17,S18,S19,S20,S21,S22,S23,S24,S25,S26,S27,S28,S29,S30,S31,S32,S33,S34,S35,S36,S37,S38,S39,S40,S41,S42,S43,S44,S45,S46,S47,S48,S49,S50,S51,S52,S53,S54,S55,S56,S57,S58,S59,S60,S61,S62,S63,S64,S65,S66,S67,S68,S69,S70,S71,S72,S73,S74,S75,S76,S77,S78,S79,S80,S81,S82,S83,S84,S85,S86,S87,S88,S89,S90,S91,S92,S93,S94,S95,S96,S97,S98,S99,S100,S101,S102,S103,S104,S105,S106,S107,S108,S109,S110,S111,S112,S113,S114,S115,S116,S117,S118,S119,S120,S121,S122,S123,S124,S125,S126,S127,S128,S129,S130 ";
		String jgsy_commonstr=" JGSY_CODE,JGSY_PARENT_CODE,DEP_CODE,JGSY_NAME,JGSY_TYPE,JGSY_PARENT_TYPE,JGSY_SYSTEM_CODE,JGSY_STATES,JGSY_CHILDRENSUM,JGSY_CODE_ORDER,UPDATE_TIME,CHEXIAO_TIME,CHEXIAO_WH,CHEXIAO_SYSTEM_TIME,CHEXIAO_YY,EDITION,UPDATE_UNIT,JGSY_IFVERTICAL,JGSY_RYSBFS,JGSY_SZBM,JGSY_CHECKSTATE,JGSY_IFZFJG,IF_SHENPI,JGBZ_ID,JGSY_CHAOJG,JGSY_CHAOBZ,JGSY_CHAOZS,JGSY_CHAOBZJG,JGSY_STANDBY1,JGSY_STANDBY2,CREDIT_CODE,JGBM,CBYY,LCLASS,LTYPE,SYDWLX,HYFL,JGSX,JB,NCLASS,UNITIDOLD,UNITID,SE_NAME,ST_NAME,SPW_NUM,MAIN_DEPT,REGISTER,TYPEID,ORGWEAVE,UNIT_JB,OUTLAYID,LS_GX,SIGN_CODE,GKID,BZR_NUM,MANNUM,DEPTNUM,LEADNUM,MIDNUM,ADDRESS,LINKMAN,PHONE,POSTALCODE,PARENT,CHILD,CLASS,INTYPEID,FLAG,ZYZN,CS_NO,PX_NO,REMARK,ZS_TAG,PRINT_PXH,UNITPXH,BD_TIME,BDLX,DWLB,FDR,ORGCODE,BZXH,SANGONG,DELFLAG,KFQFLAG,NCLASS1,UNIFY_CODE,ZFSYFLAG "; 
		//modify zepeng 20191013 and a01.a0165 in ('01','02')
		
		//String a0000sql="select A0000 from A01 where 1=1 and a01.status!='4' and a01.status!='4' and a0163='1' and a01.a0000 is not null and exists(select 1 from a02 a02 where (exists(select 1 from competence_userdept cu where a02.A0201B=b0111 and cu.userid = '"+userid+"') or  1=2 ) and a0281='true' and a01.a0000=a02.a0000 ) "+ isZGGB;// and substr(a02.a0201b, 1, 7)='001.001'
		//String a0000sql="select A0000 from A01 where a0000 in(" +ids + ") "+ isZGGB;
		String a0000sql="select A0000 from A01 where a0000 in(" +ids + ") ";
		
		try{
			SQLiteUtil.copyFile(mdatafile, file);
			Connection sqlitconn=new SQLiteUtil(zipPath+"mdata.db").getConnection();
			KingbsconfigBS.saveImpDetail("1","1","正在进行数据校验",uuid);
			KingbsconfigBS.saveImpDetail("1","2","完成",uuid);
			
			
			recreateSourceINX(sess);

			new DataToDB().execute(sqlitconn, "create table a01all as select * from a01 where 1=2");
			new DataToDB().execute(sqlitconn, "create table a02all as select * from a02 where 1=2");
			createA01INX(sqlitconn);
			createA02INX(sqlitconn);
			
			
			int number1 = 1;
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：A01数据生成处理",uuid);
			System.out.println("A01开始");
			//String a01="select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192D,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,A7865,N0150,A0131,N0152,TCSJSHOW,TCFSSHOW,ZCSJ,FCSJ from A01 where A0163='1' and A0000 in (select A0000 from A02 where A0201B in("+b0111sql+") group by A0000)";
			String a01="select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192D,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,A7865,N0150,A0131,N0152,TCSJSHOW,TCFSSHOW,ZCSJ,FCSJ,A0132,A0133 from A01 where A0163='1' and A0000 in ("+a0000sql+")";
			//String a01="select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,replace(A0192,',','，') as A0192,replace(A0192A,',','，')  as A0192A,A0221,A0288,A0192D,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,A7865,N0150,A0131,N0152,TCSJSHOW,TCFSSHOW,ZCSJ,FCSJ from A01 where 1=1 and a01.status!='4' and a01.status!='4' and a0163='1' and a01.a0000 is not null and exists(select 1 from a02 a02 where (exists(select 1 from competence_userdept cu where a02.A0201B=b0111 and cu.userid = '"+userid+"') or  1=2 ) and a0281='true' and a01.a0000=a02.a0000  ) " + isZGGB;//and substr(a02.a0201b, 1, 7)='001.001'
			//pad_config中的value update
			Statement stmt = sqlitconn.createStatement();
			if (!showGBHX.isEmpty()) {
				stmt.executeUpdate(showGBHX);
			}
			System.out.println(a01);
			ResultSet a01rs=execQuery(conn,a01);
			new DataToDB().insert(sqlitconn, a01rs,"a01");
			System.out.println("A01结束");
			

			System.out.println("A01ALL开始");
			//String a01="select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,A0192,A0192A,A0221,A0288,A0192D,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,A7865,N0150,A0131,N0152,TCSJSHOW,TCFSSHOW,ZCSJ,FCSJ from A01 where A0163='1' and A0000 in (select A0000 from A02 where A0201B in("+b0111sql+") group by A0000)";
			String a01ALL="select A0000,A0101,A0102,A0104,A0107,A0111A,A0114A,A0115A,A0117,A0128,A0134,A0140,A0141,A0144,A3921,A3927,A0160,A0163,A0165,A0184,A0187A,replace(A0192,',','，') as A0192,replace(A0192A,',','，')  as A0192A,A0221,A0288,A0192D,A0192C,A0196,A0197,A0195,A1701,A14Z101,A15Z101,A0120,A0121,A2949,A0122,A0104A,A0111,A0114,A0117A,A0128B,A0141D,A0144B,A0144C,A0148,A0148C,A0149,A0151,A0153,A0157,A0158,A0159,A015A,A0161,A0162,A0180,A0191,A0192B,A0193,A0194U,A0198,A0199,A01K01,A01K02,CBDRESULT,CBDW,ISVALID,NL,NMZW,NRZW,ORGID,QRZXL,QRZXLXX,QRZXW,QRZXWXX,RMLY,STATUS,TBR,TBRJG,USERLOG,XGR,ZZXL,ZZXLXX,ZZXW,ZZXWXX,A0155,AGE,JSNLSJ,RESULTSORTID,TBSJ,XGSJ,SORTID,A0194,A0192E,A0192F,TORGID,TORDER,ZGXL,ZGXLXX,ZGXW,ZGXWXX,A7865,N0150,A0131,N0152,TCSJSHOW,TCFSSHOW,ZCSJ,FCSJ,A0132,A0133 from A01 where 1=1 " ;//and substr(a02.a0201b, 1, 7)='001.001'
			System.out.println(a01ALL);
			ResultSet a01rsALL=execQuery(conn,a01ALL);
			new DataToDB().insert(sqlitconn, a01rsALL,"a01ALL");
			System.out.println("A01ALL结束");
			
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：A02数据生成处理",uuid);
			System.out.println("A02开始");
			//modify zepeng 20190926 贵州增加A0222A领导非领导统计标识，因客户的统计表都依据此指标而来，要求平板以该统计表作为首页
			String a02="select A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279,A0241,A0242,A0244,A0246,A0248,A0222A,A0283G from A02 where A0000 in("+a0000sql+")";
			ResultSet a02rs=execQuery(conn,a02);
			new DataToDB().insert(sqlitconn, a02rs,"a02");
			//add zepeng 20190826 导出平板数据，把所有在任职务改为主职务
			new DataToDB().execute(sqlitconn, "update a02 set a0201=a0279");
			new DataToDB().execute(sqlitconn, "update a02 set a0279='1' where a0255='1'");
			//new DataToDB().execute(sqlitconn, "update a02 set a0201=null");
			//add zepeng 按照贵州统计逻辑设置主职务，便于统计表出数
			//new DataToDB().execute(sqlitconn, "update a02 set a0201='1' where a0201b=(select min(a0201b) from a02 a where A02.a0000=a.a0000 and a0255='1' and A0201B in (select b0111 from b01 where b0114 IN ('201', '202', '203', '204', '205', '206', '207', '207a', '208', '208a', '209', '20A', '20B', '20C', '20D', '20E', '20F', '211', '212', '214', '214a', '215', '216', '217', '221', '222', '222a', '223', '224', '225', '226', '300', '310', '320', '330', '410', '411', '412', '413', '414', '415', '416', '419', '420', '421', '422', '423', '424', '425', '426', '427', '428', '429', '430', '431', '432', '433', '434', '435', '441', '451', '452', '454', '455', '457', '458', '45D', '45E', '45g', '45h', '45i', '45j', '45k', '460', '461', '464', '465', '466', '470', '471', '472', '473', '474', '475', '476', '477', '478', '47B', '47C', '481', '495', '49a', '49b', '49e', '49g', '49h', '49i', '49j', '49k', '49m', '49p', '49r', '49t', '49u', '49v', '4A0', '4A1', '4A3', '51', '52', '53', '54', '55', '56', '57', '58', '59', '5A', '5B', '5C', '5D', '5E', '5F', '5H', '5I', '611', '612', '613', '614', '615', '616', '617', '618', '619', '61A', '61C', '61Ca', '61D', '61E', '61F', '61G', '61H', '61I', '61J', '61K', '61L', '622', '623', '623a', '624', '625', '626', '627', '631', '632', '634', '635', '636', '637', '638', '639', '63A', '63B', '63C', '63D', '63E', '63F', '63G', '63H', '63I', '63J', '63K', '63L', '63M', '63N', '63O', '63P', '650', '651', '652', '653', '656', '658', '71', '72', '73', '74', '75', '76', '77', '801', '802', '803', '804', '805', '806', '807', '808', '809', '810', '812', '813', '814', '815', '817', '818', '819', '820', '821', '910', '911', '912', '913', '914', '915', '916', '917', '918', '919', '91a', '91b', '920', '921', '922', '923', '924', '925', '926', '927', '928', '929', '930', '931', '932', '933', '934', '935', '936', '937', '939', '940', '941', '942', '943', '944', '945', '946', '947', '948', '950', '951', '952', '953', '954', '955', '956', '957', '958', '95a', '960', '961', '962', '963', '964', '965', '966', '967', '968', '970', '971', '972', '973', '974', '975', '976', '977', '978', '980', '981', '982', '983', '984', '985', '986', '987', '988', '990', '991', '992', '993', '994', '995', '996', '997', '998', '9a', '9b')))");
			System.out.println("A02结束");
			
			
			System.out.println("A02ALL开始");
			//modify zepeng 20190926 贵州增加A0222A领导非领导统计标识，因客户的统计表都依据此指标而来，要求平板以该统计表作为首页
			String a02ALL="select A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0223,A0225,A0243,A0245,A0247,A0251B,A0255,A0265,A0267,A0272,A0281,A0221T,B0238,B0239,A0221A,WAGE_USED,UPDATED,A4907,A4904,A4901,A0299,A0295,A0289,A0288,A0284,A0277,A0271,A0259,A0256C,A0256B,A0256A,A0256,A0251,A0229,A0222,A0221W,A0221,A0219W,A0216A,A0215B,A0209,A0207,A0204,A0201C,A0201,A0279,A0241,A0242,A0244,A0246,A0248,A0222A,A0283G from A02";
			ResultSet a02rsALL=execQuery(conn,a02ALL);
			new DataToDB().insert(sqlitconn, a02rsALL,"a02ALL");
			//add zepeng 20190826 导出平板数据，把所有在任职务改为主职务
			new DataToDB().execute(sqlitconn, "update a02ALL set a0201='1' where a0279='1'");
			new DataToDB().execute(sqlitconn, "update a02ALL set a0279='1' where a0255='1'");
			//new DataToDB().execute(sqlitconn, "update a02 set a0201=null");
			//add zepeng 按照贵州统计逻辑设置主职务，便于统计表出数
			//new DataToDB().execute(sqlitconn, "update a02ALL set a0201='1' where a0201b=(select min(a0201b) from a02ALL a where A02ALL.a0000=a.a0000 and a0255='1')");
			System.out.println("A02ALL结束");
			
			
			
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：A36数据生成处理",uuid);
			System.out.println("A36开始");
			String a36="select A0000,A3600,A3601,A3604A,A3607,A3611,A3627,SORTID,UPDATED,A3684,A3645,A3617 from A36 where A0000 in ("+a0000sql+")";
			ResultSet a36rs=execQuery(conn,a36);
			new DataToDB().insert(sqlitconn, a36rs,"a36");
			System.out.println("A36结束");
			System.out.println("A36Z1开始");
			//modify zepeng 20190926 贵州增加A36Z1亲戚关系表，原来的A36表存直系亲属
			String a36Z1="select A0000,A3600,A3601,A3604A,A3607,A3611,A3627,SORTID,UPDATED,A3684,A3645,A3617 from A36Z1 where A0000 in ("+a0000sql+")";
			ResultSet a36Z1rs=execQuery(conn,a36Z1);
			new DataToDB().insert(sqlitconn, a36Z1rs,"a36Z1");
			System.out.println("A36Z1结束");
			System.out.println("a06开始");
			String a06="select * from A06 where A0000 in ("+a0000sql+")";
			ResultSet a06rs=execQuery(conn,a06);
			new DataToDB().insert(sqlitconn, a06rs,"a06");
			System.out.println("a06结束");
			System.out.println("a08开始");
			String a08="select A0000,A0800,A0801A,A0801B,A0901A,A0901B,A0804,A0807,A0904,A0814,A0824,A0827,A0837,A0811,A0898,A0831,A0832,A0834,A0835,A0838,A0839,A0899,UPDATED,SORTID,WAGE_USED from A08 where A0000 in ("+a0000sql+")";
			ResultSet a08rs=execQuery(conn,a08);
			new DataToDB().insert(sqlitconn, a08rs,"a08");
			System.out.println("a08结束");
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：A37数据生成处理",uuid);
			System.out.println("A37开始");
			String a37="select A0000,A3701,A3707A,A3707B,A3707C,A3707E,A3708,A3711,A3714,UPDATED,A3721 from A37 where A0000 in ("+a0000sql+")";
			ResultSet a37rs=execQuery(conn,a37);
			new DataToDB().insert(sqlitconn, a37rs,"a37");
			System.out.println("A37结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：熟悉领域数据生成处理",uuid);
			System.out.println("EXTRA_TAGS开始");
			String EXTRA_TAGS="select * from EXTRA_TAGS where a0000 in ("+a0000sql+")";
			ResultSet EXTRA_TAGSrs=execQuery(conn,EXTRA_TAGS);
			new DataToDB().insert(sqlitconn, EXTRA_TAGSrs,"EXTRA_TAGS");
			System.out.println("EXTRA_TAGS结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：重要任职经历数据生成处理",uuid);
			System.out.println("attr_lrzw开始");
			String attr_lrzw="select * from attr_lrzw where a0000 in ("+a0000sql+")";
			ResultSet attr_lrzwrs=execQuery(conn,attr_lrzw);
			new DataToDB().insert(sqlitconn, attr_lrzwrs,"attr_lrzw");
			System.out.println("attr_lrzw结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：市直单位职数情况汇总表",uuid);
			System.out.println("SZDWHZB开始");
			String SZDWHZB="select jgmc,jgjb,nvl(zzhd,0)+nvl(fzhd,0)+nvl(zshd,0) xjhd,nvl(zzsp,0)+nvl(fzsp,0)+nvl(zssp,0) xjsp,"
					+ "zzhd,zzsp,fzhd,fzsp,zshd,zssp,bzzs,nvl(zthd,0)+nvl(fthd,0) zwxjhd,nvl(ztsp,0)+nvl(ftsp,0)zwxjsp,zthd,ztsp,"
					+ "fthd,ftsp,fjhd,fjsp,b0111,nvl(zzhd,0)-nvl(zzsp,0) zzqp,nvl(fzhd,0)-nvl(fzsp,0) fzqp,nvl(zshd,0)-nvl(zssp,0) zsqp,bz,b0234,b0235,b0236 "
					+ " from SZDWHZB t"; 
			ResultSet SZDWHZB2=execQuery(conn,SZDWHZB);
			new DataToDB().insert(sqlitconn, SZDWHZB2,"SZDWHZB");
			System.out.println("SZDWHZB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：区、县（市）领导班子职数统计表",uuid);
			System.out.println("QXSLDBZHZB开始");
			String QXSLDBZHZB="select b0101 jgmc,b0111,bzdw+bzzf+bzrd+bzzx bzhj,bzdw, bzzf, bzrd,bzzx,"
					+ "bzspdw+bzspzf+bzsprd+bzspzx bzsphj,bzspdw,bzspzf,bzsprd,bzspzx,fy+jcy fj,fysp+jcysp fjsp,"
					+ "fy-fysp fyqp,jcy-jcysp jcyqp,bzdw-bzspdw bzqpdw,bzzf-bzspzf bzqpzf,bzrd-bzsprd bzqprd,"
					+ "bzzx-bzspzx bzqpzx,bz,b0236  from QXSLDBZHZB"; 
			ResultSet QXSLDBZHZB2=execQuery(conn,QXSLDBZHZB);
			new DataToDB().insert(sqlitconn, QXSLDBZHZB2,"QXSLDBZHZB");
			System.out.println("QXSLDBZHZB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：区、县（市）平台领导班子职数统计表",uuid);
			System.out.println("QXSPTHZB开始");
			String QXSPTHZB="select b0101 jgmc,b0111,bzzz,bzfz,bzspzz,bzspfz,nvl(bzzz,0)-nvl(bzspzz,0) bzqpzz,"
					+ "nvl(bzfz,0)-nvl(bzspfz,0) bzqpfz ,bz  from QXSPTHZB"; 
			ResultSet QXSPTHZB2=execQuery(conn,QXSPTHZB);
			new DataToDB().insert(sqlitconn, QXSPTHZB2,"QXSPTHZB");
			System.out.println("QXSPTHZB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：国企高校职数情况汇总表",uuid);
			System.out.println("GQGXHZB开始");
			String GQGXHZB="select jgmc,jgjb,nvl(zzhd,0)+nvl(fzhd,0) xjhd,nvl(zzsp,0)+nvl(fzsp,0) xjsp,zzhd,zzsp,fzhd,"
					+ "fzsp,zshd,zssp,bzzs,nvl(zthd,0)+nvl(fthd,0) zwxjhd,nvl(ztsp,0)+nvl(ftsp,0)zwxjsp,zthd,ztsp,fthd,"
					+ "ftsp,fjhd,fjsp,b0111,nvl(zzhd,0)-nvl(zzsp,0) zzqp,nvl(fzhd,0)-nvl(fzsp,0) fzqp,nvl(zshd,0)-nvl(zssp,0) zsqp,"
					+ "bz,b0234,b0235,b0236  from GQGXHZB t"; 
			ResultSet GQGXHZB2=execQuery(conn,GQGXHZB);
			new DataToDB().insert(sqlitconn, GQGXHZB2,"GQGXHZB");
			System.out.println("GQGXHZB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：区、县（市）党政领导干部情况统计表",uuid);
			System.out.println("QXDZLDGBB开始");
			String QXDZLDGBB="select a.ssxq,a.xqdm b0111,(select count(1) from QXDZLDGBB b where a.ssxq = b.ssxq) dzzs,"
					+ "(select count(1) from QXDZLDGBB b where a.ssxq = b.ssxq and b.age < 43) age43, round(100 * (select count(1)"
					+ " from QXDZLDGBB b where a.ssxq = b.ssxq and b.age < 43) /(select count(1) from QXDZLDGBB b where a.ssxq = b.ssxq),2) || '%' slzb43,"
					+ "(select count(1) from QXDZLDGBB b  where a.ssxq = b.ssxq and b.age < 38) age38 from (select xqdm, ssxq from QXDZLDGBB group by xqdm, ssxq) a"
					+ ",b01 t where a.xqdm=t.b0111 order by t.b0269"; 
			ResultSet QXDZLDGBB2=execQuery(conn,QXDZLDGBB);
			new DataToDB().insert(sqlitconn, QXDZLDGBB2,"QXDZLDGBB");
			System.out.println("QXDZLDGBB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：区、县（市）党政领导干部情况统计表明细",uuid);
			System.out.println("QXDZLDGBB_MX开始");
			String QXDZLDGBB_MX="select * from  QXDZLDGBB"; 
			ResultSet QXDZLDGBB_MX2=execQuery(conn,QXDZLDGBB_MX);
			new DataToDB().insert(sqlitconn, QXDZLDGBB_MX2,"QXDZLDGBB_MX");
			System.out.println("QXDZLDGBB_MX结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：区、县（市）四套领导班子结构性干部情况统计表",uuid);
			System.out.println("QXSTLDBZJGB开始");
			String QXSTLDBZJGB="select qxmc, dwmc, b0111,'1' ngbyp, ngbsp,1-ngbsp ngbqp, dwgbyp,dwgbsp,dwgbyp-dwgbsp dwgbqp  "
					+ " from QXSTLDBZJGB order by b0121, b0131"; 
			ResultSet QXSTLDBZJGB2=execQuery(conn,QXSTLDBZJGB);
			new DataToDB().insert(sqlitconn, QXSTLDBZJGB2,"QXSTLDBZJGB");
			System.out.println("QXSTLDBZJGB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：全市市管干部情况统计表（130家单位）",uuid);
			System.out.println("SGGBTJB开始");
			String SGGBTJB="select a0165mc t00,decode(a0165mc,'市管正职','10','市管副职','11','其他市管干部','18') b0111,count(*) t01,"
					+ " round(avg(age),1) t02,sum(decode( a0107,'1960',1,0)) t03,sum(decode( a0107,'1961',1,0)) t04, "
					+ "sum(decode( a0107,'1962',1,0)) t05,sum(decode( a0107,'1963',1,0)) t06,sum(decode( a0107,'1964',1,0)) t07,"
					+ "sum(decode( a0107,'1965',1,0)) t08,sum(decode( a0107,'1966',1,0)) t09,sum(decode( a0107,'1967',1,0)) t10,"
					+ "sum(decode( a0107,'1968',1,0)) t11,sum(decode( a0107,'1969',1,0)) t12,sum(decode( a0107,'1970',1,0)) t13,"
					+ "sum(decode( a0107,'1971',1,0)) t14,sum(decode( a0107,'1972',1,0)) t15,sum(decode( a0107,'1973',1,0)) t16,"
					+ "sum(decode( a0107,'1974',1,0)) t17,sum(decode( a0107,'1975',1,0)) t18,sum(decode( a0107,'1976',1,0)) t19,"
					+ "sum(decode( a0107,'1977',1,0)) t20,sum(decode( a0107,'1978',1,0)) t21,sum(decode( a0107,'1979',1,0)) t22,"
					+ "sum(decode( a0107,'1980',1,0)) t23,sum(decode( a0107,'1981',1,0)) t24,sum(decode( a0107,'1982',1,0)) t25,"
					+ "sum(decode( a0107,'1983',1,0)) t26,sum(decode( a0107,'1984',1,0)) t27,sum(decode( a0107,'1985',1,0)) t28,"
					+ "sum(decode( a0107,'1986',1,0)) t29,sum(case when age<'48' then 1 else 0 end) t30,"
					+ "to_char( sum(case when age<'48' then 1 else 0 end)*100/count(1),'fm990.0')||'%' t31,"
					+ "sum(case when age<'43' then 1 else 0 end) t32,"
					+ "to_char(sum(case when age<'43' then 1 else 0 end)*100/count(*),'fm990.0')||'%' t33,"
					+ "sum(case when age<'38' then 1 else 0 end) t34,sum(decode(a0104,2,1,0))t35,"
					+ "sum(case when a0141 not in ('01', '02', '03') then 1 else 0 end) t36,sum(case when a0801b  in ('22', '2C', '2D','2F','41','44','47','6','7','8','9')THEN 1 ELSE 0 end) t37,"
					+ "SUM(CASE when a0801b  in ('21', '23', '24','2A','2B','2E') THEN 1 ELSE 0 END) t38,sum(case when a0801b  in ('21', '23', '24','2A','2B','2E') and a0837='1' then 1 else 0 end) t39,"
					+ "sum(case when substr(a0801b,1,1)='1' then 1 else 0  END) t40,SUM(CASE WHEN substr(a0801b,1,1)='1' and a0837='1' THEN 1 ELSE 0 END) t41  "
					+ " from SGGBTJB_pad a  group by a0165mc ORDER BY b0111 "; 
			ResultSet SGGBTJB2=execQuery(conn,SGGBTJB);
			new DataToDB().insert(sqlitconn, SGGBTJB2,"SGGBTJB"); 
			System.out.println("SGGBTJB结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：全市市管干部情况统计表（130家单位）明细",uuid);
			System.out.println("SGGBTJB_MX开始");
			String SGGBTJB_MX="select * from  SGGBTJB_pad"; 
			ResultSet SGGBTJB_MX2=execQuery(conn,SGGBTJB_MX);
			new DataToDB().insert(sqlitconn, SGGBTJB_MX2,"SGGBTJB_MX"); 
			System.out.println("SGGBTJB_MX结束");
			
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：干部名册数据生成处理",uuid);
			System.out.println("a01_gbmc开始");
			
			String padSQL = "";
			List<Object[]> querys = HBUtil.getHBSession().createSQLQuery("select sql,CHECKEDGROUPID,id,name,type,sortid from A01_GBMC a").list();
			for(int i=0;i<querys.size();i++) {
				Object[] query = querys.get(i);
				String checkedgroupid = "";
				if(query[1]==null||"".equals(query[1])) {
					checkedgroupid = "001.001";
				}else {
					checkedgroupid = query[1].toString();
				}
				
				Clob clob = (Clob)query[0];
				String sql = "";
				try {
					sql = ClobToString(clob);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				String replaceSql2 = ", a01.a0101, a01.a0104, a01.A0107, a01.A0192a";
				String replaceSql = ", a01.a1701";
				if (sql.contains(replaceSql2)) {
					sql = sql.replace(replaceSql2, "");
				}
				if(sql.contains(replaceSql)){
					sql = sql.replace(replaceSql, "");
				}
				
				//添加排序号
				String orderSql = " order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')" + 
						"                      from (select a02.a0000," + 
						"                                   b0269," + 
						"                                   a0225," + 
						"                                   row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn" + 
						"                              from a02, b01" + 
						"                             where a02.a0201b = b01.b0111" + 
						"                               and a0281 = 'true'" + 
						"                               and a0201b like '"+checkedgroupid+"%') t" + 
						"                     where rn = 1" + 
						"                       and t.a0000 = a01.a0000))";
				
				//总人数
				Object total = HBUtil.getHBSession().createSQLQuery("select count(*) from ("+sql+") hz").uniqueResult();
				
				//男性人数
				Object male = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where a01.a0000 in ("+sql+") and a01.a0104 = '1'").uniqueResult();
				
				//女性人数
				Object female = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where a01.a0000 in ("+sql+") and a01.a0104 = '2'").uniqueResult();

				//正职人数
				Object zz = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where a01.a0000 in ("+sql+") and concat(a01.a0000, '') in" + 
						"               (select a02.a0000" + 
						"                  from A02 a02" + 
						"                 where 1 = 1  " + 
						"                   and a02.a0281 = 'true'" + 
						"                   and a02.a0201e in ('1'))").uniqueResult();

				//副职人数
				Object fz = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where a01.a0000 in ("+sql+") and concat(a01.a0000, '') in" + 
						"               (select a02.a0000" + 
						"                  from A02 a02" + 
						"                 where 1 = 1  " + 
						"                   and a02.a0281 = 'true'" + 
						"                   and a02.a0201e in ('3'))").uniqueResult();
				
				//其他人数
				Object qt = HBUtil.getHBSession().createSQLQuery("select count(*) from a01 where a01.a0000 in ("+sql+") and concat(a01.a0000, '') in" + 
						"               (select a02.a0000" + 
						"                  from A02 a02" + 
						"                 where 1 = 1  " + 
						"                   and a02.a0281 = 'true'" + 
						"                   and (a02.a0201e not in ('1','3') or a02.a0201e is null))").uniqueResult();
				
				String a01_gbmc_pad="select '"+query[2]+"' as id,'"+query[3]+"' as name,'"+query[4]+"' as type,'"+query[5]+"' as sortid"
						+ ",'"+total+"' as total"
						+ ",'"+male+"' as male,'"+female+"' as female,"
						+ "'"+zz+"' as zz,'"+fz+"' as fz,'"+qt+"' as qt from dual ";
				padSQL = padSQL + a01_gbmc_pad;
				if(i!=(querys.size()-1)) {
					padSQL = padSQL + " union all ";
				}
				
				
				//人员数据处理
				String a01GbmcA0000 = "select '"+query[2]+"' as id,hz.a0000 as a0000,rownum as sortid from ("+sql+ " " + orderSql+") hz";
				System.out.println("--"+i+"-------------:"+a01GbmcA0000);
				ResultSet a01_gbmc_a0000rs=execQuery(conn,a01GbmcA0000);
				new DataToDB().insert(sqlitconn, a01_gbmc_a0000rs,"a01_gbmc_a0000");
				
			}
			
			
			ResultSet a01_gbmc_padrs=execQuery(conn,padSQL);
			new DataToDB().insert(sqlitconn, a01_gbmc_padrs,"a01_gbmc_pad");
			System.out.println("a01_gbmc结束");
			
			/*
			 * KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：熟悉领域数据生成处理",uuid);
			 * System.out.println("a0194_Tag开始"); String
			 * a0194_Tag="select * from a0194_Tag where a0000 in ("+a0000sql+")"; ResultSet
			 * a0194_Tagrs=execQuery(conn,a0194_Tag); new DataToDB().insert(sqlitconn,
			 * a0194_Tagrs,"a0194_Tag"); System.out.println("a0194_Tag结束");
			 */
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：B01数据生成处理",uuid);
			System.out.println("B01开始");
			String b01="select B0101,B0104,B0107,B0111,B0114,B0117,B0121,B0124,B0127,B0131,B0140,B0141,B0142,B0143,B0150,B0180,B0183,B0185,B0188,B0189,B0190,B0191,B0191A,B0192,B0193,B0194,B01TRANS,B01IP,B0227,B0232,B0233,SORTID,USED,UPDATED,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE,STATUS,B0238,B0239,B0234,B0235,B0236,B0240,B0241,B0242,B0243,B0244,B0246,B0247,B0248,B0249,B0250,B0256,B0257,B0258,B0259,B0260,B0269,unitid from B01 where b0111 in("+b0111sql+")";
			ResultSet b01rs=execQuery(conn,b01);
			new DataToDB().insert(sqlitconn, b01rs,"b01");
			System.out.println("B01结束");
			
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：B01_COUNT数据生成处理",uuid);
			System.out.println("b01_count 开始");
			//String b01_count="select B0101,B0104,B0107,B0111,B0114,B0117,B0121,B0124,B0127,B0131,B0140,B0141,B0142,B0143,B0150,B0180,B0183,B0185,B0188,B0189,B0190,B0191,B0191A,B0192,B0193,B0194,B01TRANS,B01IP,B0227,B0232,B0233,B0111 SORTID,USED,UPDATED,CREATE_USER,CREATE_DATE,UPDATE_USER,UPDATE_DATE,STATUS,B0238,B0239,B0234,B0235,B0236,B0240,B0241,B0242,B0243,B0244,B0246,B0247,B0248,B0249,B0250,B0256,B0257,B0258,B0259,B0260,unitid from B01 where b0111 in("+b0111sql+")";
//			String b01_count="select * from b01_count";
//			String b01_count=
//					" SELECT B0104|| '本级' NAME,  " +
//							"        B0111||'.999'  ID,  " +
//							"        B0111  PID,  " +
//							"        B0194  TYPE,    " +
//							"        -99 SORTID,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b = b1.b0111  )) ZRS,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b = b1.b0111)  " +
//							"            AND a1.A0104 = '1') MAN,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b = b1.b0111)  " +
//							"            AND a1.A0104 = '2') WOMEN,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                   AND a2.a0201b = b1.b0111  " +
//							"                    AND a2.a0201e = '1')) Z,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                   AND a2.a0201b = b1.b0111  " +
//							"                    AND a2.a0201e = '3')) F  " +
//							" FROM b01 b1  " +
//							" UNION all   " +
//							" SELECT B0104 NAME,  " +
//							"        B0111  ID,  " +
//							"        B0121  PID,  " +
//							"        B0194  TYPE,    " +
//							"        SORTID,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b LIKE b1.b0111 || '%')) ZRS,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b LIKE b1.b0111 || '%')  " +
//							"            AND a1.A0104 = '1') MAN,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b LIKE b1.b0111 || '%')  " +
//							"            AND a1.A0104 = '2') WOMEN,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b LIKE b1.b0111 || '%'  " +
//							"                    AND a2.a0201e = '1')) Z,  " +
//							"        (SELECT count(*)  " +
//							"           FROM a01 a1  " +
//							"          WHERE EXISTS (SELECT 1  " +
//							"                   FROM a02 a2  " +
//							"                  WHERE a2.a0000 = a1.a0000  " +
//							"                    AND a2.a0281 = 'true'  " +
//							"                    AND a2.a0201b LIKE b1.b0111 || '%'  " +
//							"                    AND a2.a0201e = '3')) F  " +
//							"   FROM b01 b1  ";
			String b01_count="select NAME,																"
				+"	       ID,                                                                          "
				+"	       PID,                                                                         "
				+"	       TYPE,                                                                        "
				+"	       SORTID,                                                                      "
				+"	       sum(count) zrs,                                                              "
				+"	       sum(CASE                                                                     "
				+"	             WHEN A0104 = '1' THEN                                                  "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) MAN,                                                                "
				+"	       sum(CASE                                                                     "
				+"	             WHEN A0104 = '2' THEN                                                  "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) WOMEN,                                                              "
				+"	       sum(CASE                                                                     "
				+"	             WHEN a0201e = '1' THEN                                                 "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) z,                                                                  "
				+"	       sum(CASE                                                                     "
				+"	             WHEN a0201e = '3' THEN                                                 "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) f                                                                   "
				+"	  from (select NAME, ID, PID, TYPE, SORTID, a0201e, A0104, count(*) count           "
				+"	          from (SELECT B0104 || '本级' NAME,                                        "
				+"	                       B0111 || '.999' ID,                                          "
				+"	                       B0111 PID,                                                   "
				+"	                       B0194 TYPE,                                                  "
				+"	                       -99 SORTID,                                                  "
				+"	                       a02.a0201e,                                                  "
				+"	                       a01.A0104                                                    "
				+"	                                                                                    "
				+"	                  FROM b01 b1, a02 a02, a01                                         "
				+"	                 where a02.a0281 = 'true'                                           "
				+"	                   AND a02.a0201b = b1.b0111 										"
				+"					   and a01.a0163='1' and a01.status='1'                             "
				+"	                   and a01.a0000 = a02.a0000) a01                                   "
				+"	         group by NAME, ID, PID, TYPE, SORTID, a0201e, A0104) a01                   "
				+"	 group by NAME, ID, PID, TYPE, SORTID                                               "
				+"	union all                                                                           "
				+"	select NAME,                                                                        "
				+"	       ID,                                                                          "
				+"	       PID,                                                                         "
				+"	       TYPE,                                                                        "
				+"	       SORTID,                                                                      "
				+"	       sum(count) zrs,                                                              "
				+"	       sum(CASE                                                                     "
				+"	             WHEN A0104 = '1' THEN                                                  "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) MAN,                                                                "
				+"	       sum(CASE                                                                     "
				+"	             WHEN A0104 = '2' THEN                                                  "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) WOMEN,                                                              "
				+"	       sum(CASE                                                                     "
				+"	             WHEN a0201e = '1' THEN                                                 "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) z,                                                                  "
				+"	       sum(CASE                                                                     "
				+"	             WHEN a0201e = '3' THEN                                                 "
				+"	              count                                                                 "
				+"	             ELSE                                                                   "
				+"	              0                                                                     "
				+"	           END) f                                                                   "
				+"	  from (select NAME, ID, PID, TYPE, SORTID, a0201e, A0104, count(*) count           "
				+"	          from (SELECT B0104      NAME,                                             "
				+"	                       B0111      ID,                                               "
				+"	                       B0121      PID,                                              "
				+"	                       B0194      TYPE,                                             "
				+"	                       b1.sortid     SORTID,                                           "
				+"	                       a02.a0201e,                                                  "
				+"	                       a01.A0104                                                    "
				+"	                  FROM b01 b1, a02 a02, a01                                         "
				+"	                 where a02.a0281 = 'true'                                           "
				+"	                   AND a02.a0201b LIKE b1.b0111 || '%'                              "
				+"					   and a01.a0163='1' and a01.status='1'                             "
				+"	                   and a01.a0000 = a02.a0000) a01                                   "
				+"	         group by NAME, ID, PID, TYPE, SORTID, a0201e, A0104) a01                   "
				+"	 group by NAME, ID, PID, TYPE, SORTID												";
			ResultSet b01_count_rs=execQuery(conn,b01_count);
			new DataToDB().insert(sqlitconn, b01_count_rs,"b01_count");
			System.out.println("b01_count 结束");

			System.out.println("a14开始");
			String a14="select * from a14 where A0000 in ("+a0000sql+")";
			ResultSet a14rs=execQuery(conn,a14);
			new DataToDB().insert(sqlitconn, a14rs,"a14");
			System.out.println("a14结束");
			
			System.out.println("codetype开始");
			String codetype="select * from code_type";
			ResultSet codetypers=execQuery(conn,codetype);
			new DataToDB().insert(sqlitconn, codetypers,"code_type");
			System.out.println("codetype结束");
			
			System.out.println("codevalue开始");
			String codevalue="select * from code_value";
			ResultSet codevaluers=execQuery(conn,codevalue);
			new DataToDB().insert(sqlitconn, codevaluers,"code_value");
			System.out.println("codevalue结束");
			
			String updatetime="select to_char(sysdate, 'yyyy')||'-'||to_char(sysdate, 'mm')||'-'||to_char(sysdate, 'dd') updatetime from dual";
			ResultSet updatetimes=execQuery(conn,updatetime);
			new DataToDB().insert(sqlitconn, updatetimes,"updatetime");
			
			/*
			 * KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：SYSTJB表数据生成处理",uuid);
			 * System.out.println("SYSTJB开始"); String
			 * SYSTJB="select distinct a3.a0201b as b0111,code_value as SYSTYPE, \r\n" +
			 * "case when code_value='01' then ZZ \r\n" +
			 * "  when code_value='02' then FZ \r\n" + "  when code_value='03' then XZ\r\n"
			 * + "  when code_value='04' then CG\r\n" +
			 * "  when code_value='05' then FCG\r\n" + "  when code_value='06' then GQ\r\n"
			 * + "  when code_value='07' then QT \r\n" +
			 * "  when code_value='08' then ZS \r\n" + "  when code_value='09' then JJ \r\n"
			 * + "  when code_value='10' then ZZB \r\n" +
			 * "  when code_value='11' then QTJZ\r\n" +
			 * "  when code_value='12' then JZ \r\n" + "  when code_value='13' then ZT\r\n"
			 * + "  when code_value='14' then FT\r\n" +
			 * "  when code_value='15' then ZC \r\n" + "  when code_value='16' then FC \r\n"
			 * + "  when code_value='17' then YJXSY \r\n" +
			 * "  when code_value='18' then EJXSY  \r\n" + "    else 0 end as count\r\n" +
			 * "   from \r\n" + "(SELECT \r\n" + "        a0201b,\r\n" +
			 * "  SUM(case when a02.a0281 = 'true' AND a02.a0255 = '1' and a02.a0201e = '1' and a02.a0219 = '1' then 1 else 0 end)as \"ZZ\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a02.a0255 = '1' and a02.a0201e like '3%' and a02.a0219 = '1' then 1 else 0 end)as \"FZ\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a01.a0121='1'  and a01.a0163='1'  then 1 else 0 end)as \"XZ\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a01.a0121='2'  and a01.a0163='1'  then 1 else 0 end)as \"CG\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a01.a0121='3'  and a01.a0163='1'  then 1 else 0 end)as \"FCG\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a01.a0121='4'  and a01.a0163='1'  then 1 else 0 end)as \"GQ\",\r\n"
			 * +
			 * "  SUM(case when a02.a0281 = 'true' AND a01.a0121='9'  and a01.a0163='1'  then 1 else 0 end)as \"QT\",\r\n"
			 * + "  (select b01.b0256 from b01 where b01.b0111 = a02.a0201b) as \"ZS\",\r\n"
			 * + "  (select b01.b0257 from b01 where b01.b0111 = a02.a0201b) as \"JJ\",\r\n"
			 * +
			 * "  (select b01.b0258 from b01 where b01.b0111 = a02.a0201b) as \"ZZB\",\r\n"
			 * +
			 * "  (select b01.b0259 from b01 where b01.b0111 = a02.a0201b) as \"QTJZ\",\r\n"
			 * + "  (select b01.b0260 from b01 where b01.b0111 = a02.a0201b) as \"JZ\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('1A21','51G','XY51') then 1 else 0 end)as \"ZT\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('1A22','52G','XY52') then 1 else 0 end)as \"FT\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('1A31') then 1 else 0 end)as \"ZC\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('1A32') then 1 else 0 end)as \"FC\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('53G') then 1 else 0 end)as \"YJXSY\",\r\n"
			 * +
			 * "  SUM(case when a02.a0255='1' and a01.a0221 in ('54G') then 1 else 0 end)as \"EJXSY\"\r\n"
			 * +
			 * "FROM a02,a01 WHERE a01.a0000=a02.a0000 GROUP BY a02.A0201B) js,a02 a3 ,code_value where \r\n"
			 * +
			 * "js.a0201b = a3.a0201b and code_type='BZS01' and code_value.code_status='1' order by a3.a0201b,code_value"
			 * ; ResultSet SYSTJBrs=execQuery(conn,SYSTJB); new DataToDB().insert(sqlitconn,
			 * SYSTJBrs,"SYSTJB"); System.out.println("SYSTJB结束");
			 */
			
			/*
			 * KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：RSTJB数据生成处理",uuid);
			 * System.out.println("RSTJB开始"); String RSTJB="select a02.A0201B as B0111,\r\n"
			 * + "  SUM(case when a0281 = 'true' then 1 else 0 end)as \"ZRS\",\r\n" +
			 * "  SUM(case when a0281 = 'true' AND a01.A0104 = '1' then 1 else 0 end)as \"MAN\",\r\n"
			 * +
			 * "  SUM(case when a0281 = 'true' AND a01.A0104 = '2' then 1 else 0 end)as \"WOMAN\",\r\n"
			 * +
			 * "  SUM(case when a0281 = 'true' AND a02.A0219 = '1'  then 1 else 0 end)as \"ZZ\",\r\n"
			 * +
			 * "  SUM(case when a0281 = 'true' AND a02.A0219 = '2'  then 1 else 0 end)as \"FZ\"\r\n"
			 * +
			 * "FROM a02,a01 WHERE a01.a0000=a02.a0000 and a02.A0201B is not null GROUP BY a02.A0201B order by a02.A0201B"
			 * ; ResultSet RSTJBrs=execQuery(conn,RSTJB); new DataToDB().insert(sqlitconn,
			 * RSTJBrs,"RSTJB"); System.out.println("RSTJB结束");
			 */
			
			/*
			 * KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：hdsy_table数据生成处理",
			 * uuid); System.out.println("hdsy_table开始"); String hdsytable = "select "
			 * +hdsy_tablestr+" from gzdba.hdsy_table where gzdba.hdsy_table.unitid in ("
			 * +unitidsql+")"; ResultSet hdsytablers=execQuery(conn,hdsytable); new
			 * DataToDB().insert(sqlitconn, hdsytablers,"hdsy_table");
			 * System.out.println("hdsy_table结束");
			 * 
			 * KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：jgsy_common数据生成处理",
			 * uuid); System.out.println("hdsy_table开始"); String jgsycommon = "select "
			 * +jgsy_commonstr+" from gzdba.jgsy_common where gzdba.jgsy_common.unitid in ("
			 * +unitidsql+")"; ResultSet jgsycommonrs=execQuery(conn,jgsycommon); new
			 * DataToDB().insert(sqlitconn, jgsycommonrs,"jgsy_common");
			 * System.out.println("jgsy_commonrs结束");
			 */
			
			
			if(excelPath.equals("")||file==null) {
				
			}else {
				System.out.println("老干部名册导出开始");
				new ExcelToSql(excelPath,file);
				System.out.println("老干部名册导出结束");
			}
			KingbsconfigBS.saveImpDetail("2","1","文件"+(number1++)+"：A57数据生成处理",uuid);
			//照片
			System.out.println("A57开始");
			SQLiteUtil.copyFile(photofile, targetphotofile);
			Connection photoconn=new SQLiteUtil(zipPath+"photo.db").getConnection();
			
			a01rs=execQuery(conn,a01); 
			new DataToDB().insert(photoconn, a01rs,"a01");
			  
			createA01INX(photoconn);
			
			
//			new DataToDB().execute(photoconn, "CREATE TABLE A57_BASE64SALL (A0000  TEXT NOT NULL,PHOTONAME  TEXT,PHOTOBASE64  TEXT,PRIMARY KEY (A0000))");
			
			extPhoto(conn, realPhotoPath, a0000sql,"A57_BASE64S",photoconn);
//			extPhoto(conn, realPhotoPath, "","A57_BASE64SALL",photoconn);
			
			sqlitconn.commit();
			sqlitconn.close();
			photoconn.close();
			
			File updatefile=new File(zipPath+"time"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyy-MM-dd"));
			if(!updatefile.exists()){
				updatefile.createNewFile();
			}
			
			KingbsconfigBS.saveImpDetail("2","2","完成",uuid);
			KingbsconfigBS.saveImpDetail("3","1","压缩中",uuid);	
			
			//KingbsconfigBS.saveImpDetail("3","2","完成",uuid);
			String zipfile=zipPath.substring(0, zipPath.length()-1)+".zip";
			//记录页面详情
			String sql3 = "update EXPINFO set STATUS = '文件压缩中请稍候...' where id = '"+uuid+"'";
			sess.createSQLQuery(sql3).executeUpdate();
			//---------------------------------------------------------------
			Zip7z.zip7Z(zipPath.substring(0, zipPath.length()-1), zipPath.substring(0, zipPath.length()-1)+".zip", null);
			KingbsconfigBS.saveImpDetail("3","2","完成",uuid,zipfile.replace("\\", "/"));	
			//------------------------------------------------------------
			//记录页面详情
			String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
			String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '导出完成',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
			sess.createSQLQuery(sql2).executeUpdate();
			
			try {
				new LogUtil("421", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			this.delFolder(zipPath);
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				
			}
		}
		
	}

	private void extPhoto(Connection conn, String realPhotoPath, String a0000sql,String padtable,Connection photoconn)
			throws SQLException, Exception {
		String zipPath=getZipPath();
		photoconn.setAutoCommit(false);
		String photoSql="insert into "+padtable+"(A0000,'PHOTONAME','PHOTOBASE64') values(?,?,?)";
		PreparedStatement prestate=photoconn.prepareStatement(photoSql);                        
		
		String a57="select A0000,PHOTOPATH,PHOTONAME from A57 where A0000 in ("+a0000sql+")";
		if("".equals(a0000sql)) {
			a57="select A0000,PHOTOPATH,PHOTONAME from A57";
		}
		ResultSet a57rs=execQuery(conn,a57);
		int count=0;int num=0;
		while(a57rs.next()){
			count=count+1;
			String A0000=a57rs.getString("A0000");
			String phototPath=realPhotoPath+a57rs.getString("PHOTOPATH")+a57rs.getString("PHOTONAME");
			String photobase64=phototobase64(phototPath);
			if(photobase64==null||"".equals(photobase64)) {
				continue;
			}
			prestate.setObject(1, A0000);
			prestate.setObject(2, "");
			prestate.setObject(3, photobase64);
			prestate.addBatch();
			
			if(count%200==0){
				num++;
				prestate.executeBatch();
				prestate.clearBatch();
				
				//CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---导入照片---200一次" + num +",内存："+((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())%(1024*1024)));
				System.gc();
			}
		}
		
		prestate.executeBatch();
		prestate.clearBatch();
		photoconn.commit();
		
		num++;
		CommonQueryBS.systemOut("   "+DateUtil.getTime()+"---导入照片---200一次" + num +",内存："+((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())%(1024*1024)));
		System.gc();
		
		prestate.close();
	}

	private void recreateSourceINX(HBSession sess) {
		try{
			sess.createSQLQuery("DROP INDEX IN_A01_A0195").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A01_A0195 ON A01(A0195)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A02_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A02_A0000 ON A02(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A05_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A05_A0000 ON A05(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A08_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A08_A0000 ON A08(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A11_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A11_A0000 ON A11(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A14_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A14_A0000 ON A14(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A15_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A15_A0000 ON A15(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A36_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A36_A0000 ON A36(A0000)").executeUpdate();
}catch(Exception e){}
try{
			
}catch(Exception e){}
try{
			sess.createSQLQuery("DROP INDEX IN_A57_A0000").executeUpdate();
}catch(Exception e){}
try{
			sess.createSQLQuery("CREATE INDEX IN_A57_A0000 ON A57(A0000)").executeUpdate();
}catch(Exception e){}
	}

	private void createA01INX(Connection sqlitconn) {
		try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IDX_A01_TORDER");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IDX_A01_TORDER ON A01 (TORDER ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IDX_A01_TORGID");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IDX_A01_TORGID ON A01 (TORGID ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01_A0000");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01_A0000 ON A01 (A0000 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01_A0221");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01_A0221 ON A01 (A0221 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01_TORDER");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01_TORDER ON A01 (TORDER ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01_TORGID");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01_TORGID ON A01 (TORGID ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01_1");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01_1 ON A01 (A0107 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01_2");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01_2 ON A01 (A0221 ASC, A0288 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01_a0163");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01_a0163 ON A01 (A0163 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01_a0184");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01_a0184 ON A01 (A0184 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01_statusa0163");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01_statusa0163 ON A01 (STATUS ASC, A0163 ASC)");
			}catch(Exception e){}

			try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IDX_A01ALL_TORDER");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IDX_A01ALL_TORDER ON A01ALL (TORDER ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IDX_A01ALL_TORGID");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IDX_A01ALL_TORGID ON A01ALL (TORGID ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01ALL_A0000");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01ALL_A0000 ON A01ALL (A0000 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01ALL_A0221");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01ALL_A0221 ON A01ALL (A0221 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01ALL_TORDER");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01ALL_TORDER ON A01ALL (TORDER ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX IN_A01ALL_TORGID");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX IN_A01ALL_TORGID ON A01ALL (TORGID ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01ALL_1");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01ALL_1 ON A01ALL (A0107 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01ALL_2");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01ALL_2 ON A01ALL (A0221 ASC, A0288 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01ALL_a0163");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01ALL_a0163 ON A01ALL (A0163 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01ALL_a0184");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01ALL_a0184 ON A01ALL (A0184 ASC)");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "DROP INDEX idx_A01ALL_statusa0163");
				}catch(Exception e){}
				try{
				new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A01ALL_statusa0163 ON A01ALL (STATUS ASC, A0163 ASC)");
				}catch(Exception e){}
	}
	private void createA02INX(Connection sqlitconn) {
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02_a0000");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02_a0000 ON A02 (A0000 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02_a0200");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02_a0200 ON A02 (A0200 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02_a0201b");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02_a0201b ON A02 (A0201B ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02_a0201ba0000");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02_a0201ba0000 ON A02 (A0201B ASC, A0000 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02_a0201ba0000a0281");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02_a0201ba0000a0281 ON A02 (A0201B ASC, A0000 ASC, A0281 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02ALL_a0000");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02ALL_a0000 ON A02ALL (A0000 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02ALL_a0200");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02ALL_a0200 ON A02ALL (A0200 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02ALL_a0201b");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02ALL_a0201b ON A02ALL (A0201B ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02ALL_a0201ba0000");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02ALL_a0201ba0000 ON A02ALL (A0201B ASC, A0000 ASC)");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "DROP INDEX idx_A02ALL_a0201ba0000a0281");
			}catch(Exception e){}
			try{
			new DataToDB().execute(sqlitconn, "CREATE INDEX idx_A02ALL_a0201ba0000a0281 ON A02ALL (A0201B ASC, A0000 ASC, A0281 ASC)");
			}catch(Exception e){}
	}
	
	
	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	
	
	public String phototobase64(String phototPath) throws Exception {
		File photoFile=new File(phototPath);
		if(photoFile.exists()){
			InputStream photoInputStream=new FileInputStream(new File(phototPath));
			byte[] photodata=new byte[photoInputStream.available()];
			photoInputStream.read(photodata);
			photoInputStream.close();
			String photoBase64=new String(Base64.encodeBase64(photodata));
			return photoBase64;
		}else{
			return "";
		}
	}



	public ResultSet execQuery(Connection conn,String sql) throws SQLException {
		Statement s =null;
		try {
			s = conn.createStatement();
			ResultSet rs = s.executeQuery(sql);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getZipPath() {
		//"D:/HZB//temp/zipload/"+uuid+"/";
		String path=getPath();
		String zipPath=path+"给PAD导出文件_"+DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss")+"/";
		File file = new File(zipPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("zipPath:"+zipPath);
		return zipPath;
	}
	
	private String getPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
		try {
			File file =new File(upload_file);    
			//如果文件夹不存在则创建    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//解压路径
		String zip = upload_file + uuid + "/";
		return zip;
	}
	private static BufferedImage makeThumbnail(Image img, int width, int height) {
		BufferedImage tag = new BufferedImage(width, height, 1);
		Graphics g = tag.getGraphics();
		//原图的缩放img.getScaledInstance(width, height, 4)
		g.drawImage(img.getScaledInstance(width, height, 4), 0, 0, null);
		g.dispose();
		return tag;
	}

	private static  String saveSubImage(BufferedImage image,
			Rectangle subImageBounds, String filename,String path,String isupdate) throws IOException {
		
		BufferedImage subImage = new BufferedImage(subImageBounds.width,
				subImageBounds.height, 1);
		Graphics g = subImage.getGraphics();
		
		//处理各种边界问题  共4总情况
		if (subImageBounds.x < 0||
				subImageBounds.y<0||
				(image.getHeight()-subImageBounds.y)<subImageBounds.height||
				(image.getWidth()-subImageBounds.x)<subImageBounds.width
				) {
			int left=0,//截图的偏移位置
				top=0,//截图的偏移位置	
				x=subImageBounds.x,	//	原图的偏移位
				y=subImageBounds.y, //	原图的偏移位
				width=subImageBounds.width,		//	原图所截取的宽度
				height=subImageBounds.height;   //	原图所截取的高度
			
			if(subImageBounds.x < 0){//图片的左边界 未达到 截图框的左边界
				left = -subImageBounds.x;////截图框左边的偏移位置，从该偏移位置开始画图
				if(left>subImageBounds.width){//如果 截图框左边的偏移位置 超出右边界
					left=subImageBounds.width;//截图框左边的偏移位置等于截图框的宽长
				}
				x=0;//图片从最左边开始截图
				width=subImageBounds.width+subImageBounds.x;//图片所截的宽度等于 截图框的长度减去偏移的长度
				if(width>image.getWidth()){//如果计算出所截的宽度大于图片本身的宽度
					width = image.getWidth();//图片所截的宽度等于图片的宽度
				}else if(width<=0){//如果计算出所截的宽度小于0
					width=1;//宽度等于1. 宽度为0要报错
				}
			}else if((image.getWidth()-subImageBounds.x)<subImageBounds.width){//图片的右边界 未达到截图框的右边界
				left=0;//截图框左边起始位置开始画图
				x=subImageBounds.x;//图片从左边偏离度开始截图
				if(x-image.getWidth()>0){//如果图片左边开始的偏离度比图片宽度还要宽
					x = image.getWidth();//偏离度等于图片宽度
				}
				width=image.getWidth()-subImageBounds.x;//图片截图的宽度等于图片的宽度减去偏离度
				if(width<=0){//如果所截的图片的宽度小于0
					width=1;//宽度为1
					x=x-1;//偏离度往回减一
				}
			}
			if(subImageBounds.y<0){//图片的上边界 未达到 截图框的上边界
				top = -subImageBounds.y;
				if(top>subImageBounds.height){
					top=subImageBounds.height;
				}
				y=0;
				height=subImageBounds.height+subImageBounds.y;
				if(height>image.getHeight()){
					height = image.getHeight();
				}else if(height<=0){
					height=1;
				}
			}else if((image.getHeight()-subImageBounds.y)<subImageBounds.height){//图片的下边界 未达到 截图框的下边界
				top=0;
				y=subImageBounds.y;
				if(y-image.getHeight()>0){
					y=image.getHeight();
				}
				height=image.getHeight()-subImageBounds.y;
				if(height<=0){
					height=1;
					y=y-1;
				}
			}
			
			g.setColor(Color.white);
			g.fillRect(0, 0, subImageBounds.width, subImageBounds.height);
			g.drawImage(image.getSubimage(x, y, width, height), left, top, null);
		} else {
			g.drawImage(image.getSubimage(subImageBounds.x, subImageBounds.y,
					subImageBounds.width, subImageBounds.height), 0, 0, null);
		}
		g.dispose();
		//保存人员头像
		if("update".equals(isupdate)){
			saveImg(subImage,filename,"jpg",path,isupdate);
			return "";
		}
		String base64 = saveImg(subImage,filename,"jpg",path,isupdate);
		//ImageIO.write(subImage, formatName, subImageFile);
		return base64;
	}

	private static String saveImg(BufferedImage image, String fileName,String formatName,String path,
			String isupdate) {
		String photourl = path + File.separator;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		if(formatName==null||"".equals(formatName)){
			formatName = "jpg";
		}
		String base64 = "";
		try {
			fileName = fileName+"."+formatName;
			photourl = photourl+fileName;
			
			
			File fileD = new File(photourl);
			if(!fileD.isDirectory()){
				fileD.mkdirs();
			}
			File fileF = new File(photourl+fileName);
			if("update".equals(isupdate)){
				ImageIO.write(image, formatName, fileF);
				return "";
			}
			ImageIO.write(image, formatName, out);
			//base64 = phototobase64(out.toByteArray());
			
			
			
		}  catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return base64;
	}

	
	public static String cut(String srcImageFile,  String filename,String path,String isupdate) throws IOException {
		Image image = ImageIO.read(new File(srcImageFile));
		Rectangle rect = new Rectangle(0, 0, 272, 340);
		int width = 272;
		int height = 340;
		
		BufferedImage bImage = makeThumbnail(image, width, height);
		if("update".equals(isupdate)){
			saveSubImage(bImage, rect, filename, path,isupdate);
			return "";
		}
		String bas64 = saveSubImage(bImage, rect,filename,path,isupdate);
		return bas64;
	}
	
	public static void main(String[] args) {
		System.out.println(DateUtil.timeToString(DateUtil.getTimestamp(), "yyyy-MM-dd"));
	}
	
	public String ClobToString(Clob clob) throws SQLException, IOException { 
    	
        String reString = ""; 
        java.io.Reader is = clob.getCharacterStream();// 得到流 
        BufferedReader br = new BufferedReader(is); 
        String s = br.readLine(); 
        StringBuffer sb = new StringBuffer(); 
        while (s != null) {// 执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING 
            sb.append(s); 
            s = br.readLine(); 
        } 
        reString = sb.toString(); 
        return reString; 
    }
}
