package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
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
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgRepThread implements Runnable {
	
	private String uuid;
	private String gjgs;
	private CurrentUser user;
	private String ltry;
	private String gzlbry;
	private String gllbry;
	private String searchDeptid;
	private String linkpsn;
	private String linktel;
	private String remark;
	private String gz_lb;
	private String gl_lb;
	private String ftpid;
	private UserVO userVo;
	private String lsry;
    public DataOrgRepThread(String uuid, CurrentUser user,String gjgs,String ltry,String lsry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,UserVO userVo,String ftpid) {
        this.lsry = lsry;
        this.uuid = uuid;
        this.gjgs = gjgs;
        this.user = user;
        this.ltry = ltry;
        this.gzlbry = gzlbry;
        this.gllbry = gllbry;
        this.searchDeptid = searchDeptid;
        this.linkpsn = linkpsn;
        this.linktel = linktel;
        this.remark = remark;
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.userVo = userVo;
        this.ftpid = ftpid;
        
    }

	@Override
	public void run() {
		//----- 上报数据，头文件和对应的数据文件（hzb格式） 数据文件分包处理 ---------------------------------------------
		//----- 按 packcount 每 15000 人进行分包，生成多个汇总版文件  ------------------------------------------------
		HBSession sess = HBUtil.getHBSession();
		Map<String, String> map = new HashMap<String, String>();	
		String localf = AppConfig.LOCAL_BACKUP_FILE;		//fpt 本地备份数据存放根目录
		int packcount = 15000;								//定义每个数据包 包含的人数
		String process_run = "1";							//流程过程序号
		try {
			// ---- 01-构造sql查询 上报人数 ---------------------------------------------------------------------
			StringBuilder b = new StringBuilder();
			if (DBUtil.getDBType().equals(DBType.MYSQL)) {	//mysql 
				b.append("select distinct a1.a0000 a0000 from a01 a1 where 1=1");
				if (ltry.equals("0")) {						//不查询离退人员
					b.append(" and a1.status<>'3'");
				}
				if (lsry.equals("0")) {						//不查询历史人员
					b.append(" and a1.status<>'2'");
				}
				if(!gz_lb.equals("")){						//所选工作类别不为空
					if(gzlbry.equals("0")){					//工作类别（全选），不加条件，否则 按勾选类别查询
						b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
					}
				}
				if(!gl_lb.equals("")){						//所选管理类别不为空
					if(gllbry.equals("0")){					//管理类别（全选），不加条件，否则 按勾选类别查询
						b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
					}
				}
				b.append(" order by rand() ");
			} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
				b.append("select a0000,rownum rn from(select distinct a1.a0000 a0000 from a01 a1 where 1=1");
				if (ltry.equals("0")) {						//不查询离退人员
					b.append(" and a1.status<>'3'");
				}
				if (lsry.equals("0")) {						//不查询历史人员
					b.append(" and a1.status<>'2'");
				}
				if(!gz_lb.equals("")){						//所选工作类别不为空
					if(gzlbry.equals("0")){					//工作类别（全选），不加条件，否则 按勾选类别查询
						b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
					}
				}
				if(!gl_lb.equals("")){						//所选管理类别不为空
					if(gllbry.equals("0")){					//管理类别（全选），不加条件，否则 按勾选类别查询
						b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
					}
				}
				b.append(" order by a1.a0000 )");
			}
//			if (DBUtil.getDBType().equals(DBType.MYSQL)) {
//				b.append("select a0000 from a01 where ");
//			} else {
//				b.append("select a0000,rownum rn from a01 where ");
//			}
//			//是否选中选中是1
//			if(ltry.equals("1") && lsry.equals("1")){
//				b.append("  ((status='3' OR   status='2' )");
//				b.append("and ORGID like '"+ searchDeptid +"%' " );
//				b.append(") ");
//				b.append(" or ");
//			} else  if(ltry.equals("1")){
//				b.append(" ( status='3'");
//				b.append("and ORGID like '"+ searchDeptid +"%' " );
//				b.append(") ");
//				b.append(" or ");
//			}else  if(lsry.equals("1")){
//				b.append(" (  status='2'");
//				b.append("and ORGID like '"+ searchDeptid +"%' " );
//				b.append(") ");
//				b.append(" or ");
//				}
//			b.append(" (  status='1' and a0000 in (select a0000 from a02 where A0255='1' and ");
//			b.append(" a0201b like '"+ searchDeptid +"%' ))");
//----------------------------------------------------------------------------------------------------

//			if(!gz_lb.equals("")){
//				if(gzlbry.equals("0")){
//					b.append(" and a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
//				}
//			}
//			if(!gl_lb.equals("")){
//				if(gllbry.equals("0")){
//					b.append(" and a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
//				}
//			}
			Object obj = HBUtil.getHBSession().createSQLQuery(			//查询导出人数
					"select count(s.a0000) from (" + b.toString() + ") s").uniqueResult();
			// ---- 02-构造头文件信息 -----------------------------------------------------------------------
			int count = 1;												//导出人数默认值为1
			B01 b01 = (B01) sess.get(B01.class, searchDeptid);			//查询根机构
			String path = getPath();									//生成文件临时路径
			java.sql.Timestamp now = DateUtil.getTimestamp();			//获取当前时间
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			ZwhzPackDefine info = new ZwhzPackDefine();					//头文件实体类，用来生成头文件
			String sid = UUID.randomUUID().toString().replace("-", "");	//文件内置id
			info.setId(sid);
			info.setB0101(b01.getB0101());								//设置机构信息
			info.setB0111(b01.getB0111());
			info.setB0114(b01.getB0114());
			info.setB0194(b01.getB0194());
			info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
					"yyyyMMdd"));
			info.setLinkpsn(linkpsn);									//设置机构信息
			info.setLinktel(linktel);									//设置机构信息
			info.setRemark(remark);										//备注
			info.setStype("2");											//2 为数据上报类别
			info.setStypename("按机构导出");
			info.setTime(time);
			info.setTranstype("up");									//上报
			info.setErrortype("无");
			info.setErrorinfo("无");
			if (obj != null) {											//导出人数 实际值
				if (DBUtil.getDBType().equals(DBType.MYSQL)) {			
					count = ((BigInteger) obj).intValue()/ packcount
							+ (((BigInteger) obj).intValue() % packcount != 0 ? 1 : 0);
					info.setPersoncount(Long.valueOf(((BigInteger) obj)
							.intValue()));
				} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
					count = ((BigDecimal) obj).intValue()/ packcount
							+ (((BigDecimal) obj).intValue() % packcount != 0 ? 1 : 0);
					info.setPersoncount(Long.valueOf(((BigDecimal) obj).intValue()));
				}
			}
			if(count == 0){
				count = 1;
			}
			
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();				//包含数据文件（hzb）集合
			String packageFile = "Pack_按机构导出文件_" + b01.getB0114() + "_"		//头文件名称
					+ b01.getB0101() + "_" + time1 + ".xml";
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
			process_run = "2";
			KingbsconfigBS.saveImpDetail(process_run,"1","处理中",uuid);			//记录导入过程
			// ---- 03-生成数据文件 ----------------------------------------------------------------------------
			for (int i = 1; i <= count; i++) {									
				SFileDefine sf = new SFileDefine();
				StringBuilder a01sql = new StringBuilder();
				if (DBUtil.getDBType().equals(DBType.MYSQL)) {				//本包包含人员 id ，人员相关信息通过该查询确定
					a01sql.append(" select t.a0000 from(");
					a01sql.append(b);
					a01sql.append(" limit " + ((i - 1) * packcount) + "," + packcount);
					a01sql.append(") as t ");
				} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
					a01sql.append(" select a0000 from(");
					a01sql.append(b);
					a01sql.append(") where rn >=" + ((i - 1) * packcount + 1));
					a01sql.append(" and rn <=" + (i * packcount));
				}
				Long orgrows = 0L;											//本包包含机构数目
				if (i == 1) {
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {
						BigInteger b01size = (BigInteger) sess.createSQLQuery("select count(1) from b01 ").uniqueResult();
						info.setOrgcount(b01size.longValue());
						orgrows = b01size.longValue();
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						BigDecimal b01size = (BigDecimal) sess.createSQLQuery("select count(1) from b01 ").uniqueResult();
						info.setOrgcount(b01size.longValue());
						orgrows = b01size.longValue();
					}
				}
				Long personrows = Long.valueOf(packcount);					//本包包含人员数目
				if (i == count) {
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {
						BigInteger a01size = (BigInteger) sess.createSQLQuery("select count(1) from (" + b +" limit " + ((i - 1) * packcount) + "," + packcount+") as t ").uniqueResult();
						personrows = a01size.longValue();
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						BigDecimal a01size = (BigDecimal) sess.createSQLQuery("select count(1) from ("+a01sql+") ").uniqueResult();
						personrows = a01size.longValue();
					}
				}

				List<Map> list17 = new ArrayList<Map>();					//数据包中头文件信息 gwyinfo.xml
				map.put("type", "2");
				map.put("time", time);
				map.put("dataversion", "20121221");
				map.put("psncount", personrows+"");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				list17.add(map);
				
				//数据文件包含信息 生成时间 包含人数，机构数 
				sf.setTime(time);											
				sf.setOrgrows(orgrows);
				sf.setPersonrows(personrows);
				
				String number = ("000" + i).substring(("000" + i).length() - 3);	//确定数据生成文件夹 通过 i 生成001 002 等文件夹
				String zippath = path + "按机构导出文件_" + b01.getB0111() + "_"
						+ b01.getB0101() + "_" + time1 + "/" + number + "/";		//临时文件整体路径
				File file = new File(zippath);
				if (!file.exists() && !file.isDirectory()) {						
					file.mkdirs();
				}
				String zippathtable = path + "按机构导出文件_" + b01.getB0111() + "_"	//临时文件table存放数据xml的路径
						+ b01.getB0101() + "_" + time1 + "/" + number
						+ "/Table/";
				File file1 = new File(zippathtable);
				if (!file1.exists() && !file1.isDirectory()) {
					file1.mkdirs();
				}
				String zipfile = localf + "/" + "按机构导出文件_" + b01.getB0114() + "_"	//文件名
					+ b01.getB0101() + "_" + time1 + "_" + number + ".hzb";
				int number1 = 1;														//已解析表的树木
				int number2 = 20;														//未解析标的树木
				//---- 03-01 生成数据文件 ---------------------------------------------------------------------
				Connection conn = sess.connection();
				Statement stmt = null;
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE);  
				ResultSet rs_a01 = stmt.executeQuery("select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
					"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
					"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
					"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
					"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
					"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
					"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
					"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
					"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult from a01 t where a0000 in(" + a01sql.toString() + ")");
				 
				CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				Xml4HZBUtil2.List2Xml(rs_a01, "A01", zippath);
				CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				rs_a01.close();
				stmt.close();
				CommonQueryBS.systemOut(DateUtil.getTime());
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A02.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a02 = stmt.executeQuery("select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
					"t.A0209,t.A0215A,t.A0215B,t.A0216A,t.A0219,t.A0219W,t.A0221,t.A0221W,t.A0222,t.A0223,"+
					" t.A0225,t.A0229,t.A0243,t.A0245,t.A0247,t.A0251,t.A0251B,t.A0255,t.A0256,t.A0256A,"+
					"t.A0256B,t.A0256C,t.A0259,t.A0265,t.A0267,t.A0271,t.A0277,t.A0281,t.A0284,t.A0288,"+
       				"t.A0289,t.A0295,t.A0299,t.A4901,t.A4904,t.A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239 from A02 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a02, "A02", zippath);
				rs_a02.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A06.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a06 = stmt.executeQuery("select t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED from A06 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a06, "A06", zippath);
				rs_a06.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A08.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a08 = stmt.executeQuery("select t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827,"+
					" t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898,t.A0899,t.A0901A,"+
					" t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used from A08 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a08, "A08", zippath);
				rs_a08.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A11.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a11 = stmt.executeQuery("select t.A0000,t.A1100,t.A1101,t.A1104,t.A1107,t.A1107A,t.a1107b ,t.a1111 ,t.a1114 ,t.a1121a ,"+
					"t.a1127 ,t.a1131 ,t.a1134 ,t.a1151 ,t.updated,t.A1108,t.A1107C from A11 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a11, "A11", zippath);
				rs_a11.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A14.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a14 = stmt.executeQuery("select t.a0000,t.a1400,t.a1404a,t.a1404b,t.a1407,t.a1411a,t.a1414,t.a1415,t.a1424,t.a1428,"+ 
					"t.sortid ,t.updated from A14 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a14, "A14", zippath);
				rs_a14.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A15.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_a15 = stmt.executeQuery("select t.a0000, t.a1500, t.a1517, t.a1521, t.updated, t.a1527 from A15 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_a15, "A15", zippath);
				rs_a15.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A29.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A29 = stmt.executeQuery("select t.a0000,t.a2907 ,t.a2911,t.a2921a ,t.a2941,t.a2944,t.a2947 ,t.a2949, t.updated," +
					"t.a2947a,t.A2921B,t.A2947B,t.A2921C,t.A2921d from A29 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A29, "A29", zippath);
				rs_A29.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A30.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_A30 = stmt.executeQuery("select t.a0000,t.a3001,t.a3004,t.a3007a ,t.a3034 ,t.updated from A30 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A30, "A30", zippath);
				rs_A30.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A31.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A31 = stmt.executeQuery("select t.a0000,t.a3101,t.a3104,t.a3107,t.a3117a,t.a3118,t.a3137,t.a3138 ,t.updated,t.a3110,t.a3109,t.a3108 from A31 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A31, "A31", zippath);
				rs_A31.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A36.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A36 = stmt.executeQuery("select t.a0000,t.a3600,t.a3601,t.a3604a,t.a3607,t.a3611,t.a3627 ,t.sortid ,t.updated from A36 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A36, "A36", zippath);
				rs_A36.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A37.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A37 = stmt.executeQuery("select t.a0000,t.a3701,t.a3707a,t.a3707c,t.a3707e,t.a3707b,t.a3708,t.a3711,t.a3714,t.updated from A37 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A37, "A37", zippath);
				rs_A37.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A41.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A41 = stmt.executeQuery("select t.a4100,t.a0000,t.a1100 ,t.a4101,t.a4102,t.a4103 ,t.a4104,t.a4105 ,t.a4199 from A41 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A41, "A41", zippath);
				rs_A41.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A53.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A53 = stmt.executeQuery("select t.a0000,t.a5300,t.a5304,t.a5315,t.a5317,t.a5319,t.a5321,t.a5323,t.a5327,t.a5399,t.updated from A53 t where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A53, "A53", zippath);
				rs_A53.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A57.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A57 = stmt.executeQuery("select a0000,a5714,photoname,photstype,updated,photopath from A57 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A57, "A57", zippath);
				rs_A57.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A60.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A60 = stmt.executeQuery("select A0000,A6001,A6002,A6003,A6004,A6005,A6006,"
							+ "A6007,A6008,A6009,A6010,A6011,A6012,A6013,"
							+ "A6014,A6015,A6016,A6017 from A60 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A60, "A60", zippath);
				rs_A60.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A61.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A61 = stmt.executeQuery("select A6116,A0000,A2970,A2970A,A2970B,A6104,"
							+ "A2970C,A6107,A6108,A6109,A6110,A6111,"
							+ "A6112,A6113,A6114,A6115 from A61 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A61, "A61", zippath);
				rs_A61.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A62.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A62 = stmt.executeQuery("select A0000,A2950,A6202,A6203,A6204,A6205"
						+ " from A62 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A62, "A62", zippath);
				rs_A62.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A63.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A63 = stmt.executeQuery("select A0000,A2951,A6302,A6303,A6304,A6305,"
							+ "A6306,A6307,A6308,A6309,A6310 from A63 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A63, "A63", zippath);
				rs_A63.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A64.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_A64 = stmt.executeQuery("select A0000,A6401,A6402,A6403,A6404,A6405,"
							+ "A6406,A6407,A6408,A64TYPE,A6400 from A64 where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_A64, "A64", zippath);
				rs_A64.close();
				stmt.close();
				
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：B01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				if (i == 1) {
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(5000);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					ResultSet rs_b01 = stmt.executeQuery("select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
							"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
							"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
							"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234 from b01 t where b0111 <> '-1'");
					Xml4HZBUtil2.List2Xml(rs_b01, "B01", zippath);
					rs_b01.close();
					stmt.close();
				} else {
					Xml4HZBUtil.List2Xml(new ArrayList(), "B01", zippath);
				}
				
				//--------- 补充信息xml生成 INFO_EXTEND.xml\B01_EXT.xml---------------------------------------------
				KingbsconfigBS.saveImpDetail(process_run,"1","补充数据生成处理",uuid);		//记录导入过程
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs_info = stmt.executeQuery("select * from INFO_EXTEND where a0000 in(" + a01sql.toString() + ")");
				Xml4HZBUtil2.List2Xml(rs_info, "INFO_EXTEND", zippath);
				rs_info.close();
				stmt.close();
				
				if (i == 1) {
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(5000);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					ResultSet b01_info = stmt.executeQuery("select * from B01_EXT where b0111 <> '-1'");
					Xml4HZBUtil2.List2Xml(b01_info, "B01_EXT", zippath);
					b01_info.close();
					stmt.close();
				} else {
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(5000);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					ResultSet b01_info = stmt.executeQuery("select * from B01_EXT where 1<>1");
					Xml4HZBUtil2.List2Xml(b01_info, "B01_EXT", zippath);
					b01_info.close();
					stmt.close();
				}
				CommonQueryBS.systemOut("数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","数据说明文件生成处理中",uuid);	
				Xml4HZBUtil2.List2Xml(list17, "info", zippath);
				KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中",uuid);	
				stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				stmt.setFetchSize(5000);
				stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
				ResultSet rs2_A57 = stmt.executeQuery("select a0000,a5714,photoname,photopath from A57 where a0000 in(" + a01sql.toString() + ")");
				if(rs2_A57!=null){
					String photopath = zippath + "Photos/";				//生成图片路径      
					File file2 =new File(photopath);    
					if  (!file2 .exists()  && !file2 .isDirectory()){   //如果文件夹不存在则创建       
						file2 .mkdirs();    
					}
					List<String> photolist = new ArrayList<String>();
					while (rs2_A57.next()) {
						String a0000 = rs2_A57.getString("a0000");
						String photoname = rs2_A57.getString("photoname");
						String photop = rs2_A57.getString("photopath");
						String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
						photolist.add(photop+photon);
					}
					PhotosUtil.copyPhotos(photolist, photopath);
//					PhotosUtil.copyCmd(photolist, photopath);
					photolist.clear();
					photolist = null;
				}
				rs2_A57.close();
				stmt.close();
				conn.close();
				NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
				sf.setSize(getFileSize(file0));
				sfile.add(sf);
			}
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
			process_run = "3";
			KingbsconfigBS.saveImpDetail(process_run,"1","处理中",uuid);			//记录导入过程
			info.setDatainfo("机构单位信息集数据" + info.getOrgcount() + "条，人员基本信息集"
					+ info.getPersoncount() + "条。");
			info.setSfile(sfile);
			CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
			FileUtil.createFile(localf + "/" + packageFile,
					JXUtil.Object2Xml(info, true), false, "UTF-8");
			if (StringUtil.isEmpty(ftpid)) {
				throw new AppException("上报单位为空，请重新选择！");
			}
			//--------- 根据上行id获取上行fpt对象信息 -------------------------------------
			TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(
					TransConfig.class, ftpid);
			//---------------上报信息记录，设置对应值保存至数据库-------------
			Reportftp rfpt = new Reportftp();
			rfpt.setFilename("");
			rfpt.setPackageindex(sid);
			rfpt.setPackagename(packageFile);
			rfpt.setRecieveftpuserid(jfcc.getId());
			rfpt.setRecieveftpusername(jfcc.getName());
			rfpt.setReporttime(DateUtil.getTimestamp());
			rfpt.setB0111(b01.getB0111());
			rfpt.setReporttype("1");
			rfpt.setReportuser(user.getId());
			rfpt.setReportusername(user.getName());
			try {
				sess.beginTransaction();
				sess.save(rfpt);
				//--------- 上报hzb信息 ---------------------------------------------
				ZwhzFtpClient.uploadHzb(jfcc, localf + "/" + packageFile);
				sess.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					KingbsconfigBS.saveImpDetail(process_run,"4", e.getMessage(),uuid);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				sess.getTransaction().rollback();
				throw new AppException(e.getMessage());

			}
			KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,"datarep");			//记录导入过程
			//--------- 上报hzb信息 ---------------------------------------------
			try {
				new LogUtil("431", "REPORT_FTP", "", "", "数据上报", new ArrayList(),userVo).start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run,"4", e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	public static void appendFileContent(String fileName, String content) {
        try {
            //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
	
	private String getPath() {
		String classPath = getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String uuid = UUID.randomUUID().toString().replace("-", "");
		String rootPath  = ""; 
		
		//windows下 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		//linux下 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		//上传路径
		String upload_file = rootPath + "zipload/";
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
	
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
}
