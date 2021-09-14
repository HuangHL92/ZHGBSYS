package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.dom4j.Element;

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
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgImpThread2 implements Runnable {
	
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
	private String tabimp;
	private String zdcjg;
	private UserVO userVo;
	private String lsry;
    public DataOrgImpThread2(String uuid, CurrentUser user,String gjgs,String ltry,String lsry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,String tabimp,String zdcjg,UserVO userVo) {
        this.lsry = lsry;
        this.uuid = uuid;
        this.gjgs = gjgs;
        this.user = user;
        this.zdcjg = zdcjg;
        this.ltry = ltry;
        this.gzlbry = gzlbry;
        this.gllbry = gllbry;
        this.searchDeptid = searchDeptid;
        this.linkpsn = linkpsn;
        this.linktel = linktel;
        this.remark = remark;
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.tabimp = tabimp;
        this.userVo = userVo;
    }

	@Override
	public void run() {
		Map<String, String> map = new HashMap<String, String>();
		String infile = "";										// 文件
		String process_run = "1";								// 过程序号
		String path = "";
		try {
			CommonQueryBS.systemOut("数据导出---------"+DateUtil.getTime());
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){/*
				HBSession sess = HBUtil.getHBSession();
				B01 b01 = (B01) sess.get(B01.class, searchDeptid);
				StringBuilder b = new StringBuilder();
				b.append("select a0000 from a01 where "); //修改这里注意修改导出工具类的问题。
				//是否选中选中是1
				if(ltry.equals("1") && lsry.equals("1")){
					b.append("  ((status='3' OR   status='2' )");
					b.append("and ORGID like '"+ searchDeptid +"%' " );
					b.append(") ");
					b.append(" or ");
				} else  if(ltry.equals("1")){
					b.append(" ( status='3'");
					b.append("and ORGID like '"+ searchDeptid +"%' " );
					b.append(") ");
					b.append(" or ");
				}else  if(lsry.equals("1")){
					b.append(" (  status='2'");
					b.append("and ORGID like '"+ searchDeptid +"%' " );
					b.append(") ");
					b.append(" or ");
  				}
				b.append(" (  status='1' and a0000 in (select a0000 from a02 where A0255='1' and ");
				b.append(" a0201b like '"+ searchDeptid +"%' ))");
//----------------------------------------------------------------------------------------------------

				if(!gz_lb.equals("")){
					if(gzlbry.equals("0")){
						b.append(" and a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
					}
				}
				if(!gl_lb.equals("")){
					if(gllbry.equals("0")){
						b.append(" and a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
					}
				}
				CommonQueryBS.systemOut(b.toString());
				
				CommonQueryBS.systemOut("数据导出-02--------"+DateUtil.getTime());
				Object psn = sess.createSQLQuery("select count(1) from a01 where a0000 in(" + b.toString() + ")").uniqueResult();
				CommonQueryBS.systemOut("数据导出-03--------"+DateUtil.getTime());
				List<Map> list17 = new ArrayList<Map>();
				if(zdcjg.equals("0")){
					map.put("type", "21");  	//导出全数据
				} else {
					map.put("type", "22");		//导出机构数据
				}
				map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
				//     ??
				map.put("dataverion", "20160506");
				map.put("psncount", (psn!=null)? psn.toString() : "");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				list17.add(map);
				CommonQueryBS.systemOut("数据导出-04--------"+DateUtil.getTime());
				path = getPath();
				String zippath = path + "按机构导出文件_" +b01.getB0111()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +"/";
				File file =new File(zippath);    
				//如果文件夹不存在则创建    
				if  (!file .exists()  && !file .isDirectory())      
				{       
				    file .mkdirs();    
				}
				String zippathtable = path + "按机构导出文件_" +b01.getB0111()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +"/Table/";
				File file1 =new File(zippathtable);    
				//如果文件夹不存在则创建    
				if  (!file1 .exists()  && !file1 .isDirectory())      
				{       
				    file1 .mkdirs();    
				}
				String zipfile = path + "按机构导出文件_" +b01.getB0111()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".7z";
				CommonQueryBS.systemOut("数据导出-05--------"+DateUtil.getTime());
				KingbsconfigBS.saveImpDetail("1","2","完成",uuid);						//记录导入过程
				CommonQueryBS.systemOut("数据导出-06--------"+DateUtil.getTime());
				process_run = "2";
				int number1 = 1;														//已解析表的树木
				int number2 = 20;														//未解析标的树木
//				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				Connection conn = sess.connection();
				PreparedStatement stmt = null;
				if (gjgs!=null && gjgs.equals("1")) {
					CommonQueryBS.systemOut("数据导出-07--------"+DateUtil.getTime());
					if(zdcjg.equals("0")){
						CommonQueryBS.systemOut("数据导出-08--------"+DateUtil.getTime());
						zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".hzb";
						CommonQueryBS.systemOut(DateUtil.getTime());
						if(false){
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							
							CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							Xml4HZBNewUtil.data2Xml(b.toString(), "A01", zippath, conn);
							CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							CommonQueryBS.systemOut(DateUtil.getTime());
							CommonQueryBS.systemOut("数据导出-10--------"+DateUtil.getTime());
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A02.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A02", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A06.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A06", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A08.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A08", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A11.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A11", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A14.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A14", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A15.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A15", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A29.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A29", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A30.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A30", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A31.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A31", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A36.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A36", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A37.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A37", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A41.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A41", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A53.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A53", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A57.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A57", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：B01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(searchDeptid, "B01", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A60.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A60", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A61.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A61", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A62.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A62", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A63.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A63", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A64.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "A64", zippath, conn);
							
							KingbsconfigBS.saveImpDetail(process_run,"1","补充数据生成处理",uuid);		//记录导入过程
							Xml4HZBNewUtil.data2Xml(b.toString(), "INFO_EXTEND", zippath, conn);
							
							Xml4HZBNewUtil.data2Xml(searchDeptid, "B01_EXT", zippath, conn);
						} else {
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							String a01sql = "select t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
									"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
									"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
									"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
									"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
									"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
									"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
									"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
									"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult from a01 t where a0000 in(" + b.toString() + ")";
							stmt = conn.prepareStatement(a01sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY );
							CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE);  
							ResultSet rs_a01 = stmt.executeQuery();
							 
							CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							Xml4HZBUtil2.List2Xml(rs_a01, "A01", zippath);
							CommonQueryBS.systemOut("A01数据导入"+ DateUtil.getTime()+"\n"+"neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
							rs_a01.close();
							stmt.close();
							CommonQueryBS.systemOut(DateUtil.getTime());
							
							String a02sql = "select t.a0000,t.a0200,t.a0201,t.a0201a,t.a0201b,t.a0201c, t.a0201d,t.a0201e,t.a0204,t.a0207,"+
									"t.A0209,t.A0215A,t.A0215B,t.A0216A,t.A0219,t.A0219W,t.A0221,t.A0221W,t.A0222,t.A0223,"+
									" t.A0225,t.A0229,t.A0243,t.A0245,t.A0247,t.A0251,t.A0251B,t.A0255,t.A0256,t.A0256A,"+
									"t.A0256B,t.A0256C,t.A0259,t.A0265,t.A0267,t.A0271,t.A0277,t.A0281,t.A0284,t.A0288,"+
				       				"t.A0289,t.A0295,t.A0299,t.A4901,t.A4904,t.A4907,t.updated,t.wage_used,t.a0221a,t.b0238,t.b0239 from A02 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A02.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(a02sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a02 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a02, "A02", zippath);
							rs_a02.close();
							stmt.close();
							
							String sql = "select t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED from A06 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A06.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a06 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a06, "A06", zippath);
							rs_a06.close();
							stmt.close();
							
							sql = "select t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827,"+
									" t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898,t.A0899,t.A0901A,"+
									" t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used from A08 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A08.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a08 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a08, "A08", zippath);
							rs_a08.close();
							stmt.close();
							
							sql ="select t.A0000,t.A1100,t.A1101,t.A1104,t.A1107,t.A1107A,t.a1107b ,t.a1111 ,t.a1114 ,t.a1121a ,"+
									"t.a1127 ,t.a1131 ,t.a1134 ,t.a1151 ,t.updated,t.A1108,t.A1107C from A11 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A11.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a11 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a11, "A11", zippath);
							rs_a11.close();
							stmt.close();
							sql ="select t.a0000,t.a1400,t.a1404a,t.a1404b,t.a1407,t.a1411a,t.a1414,t.a1415,t.a1424,t.a1428,"+ 
									"t.sortid ,t.updated from A14 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A14.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a14 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a14, "A14", zippath);
							rs_a14.close();
							stmt.close();
							sql ="select t.a0000, t.a1500, t.a1517, t.a1521, t.updated, t.a1527 from A15 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A15.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_a15 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_a15, "A15", zippath);
							rs_a15.close();
							stmt.close();
							sql ="select t.a0000,t.a2907 ,t.a2911,t.a2921a ,t.a2941,t.a2944,t.a2947 ,t.a2949, t.updated," +
									"t.a2947a,t.A2921B,t.A2947B,t.A2921C,t.A2921d from A29 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A29.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A29 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A29, "A29", zippath);
							rs_A29.close();
							stmt.close();
							sql ="select t.a0000,t.a3001,t.a3004,t.a3007a ,t.a3034 ,t.updated from A30 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A30.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							ResultSet rs_A30 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A30, "A30", zippath);
							rs_A30.close();
							stmt.close();
							sql ="select t.a0000,t.a3101,t.a3104,t.a3107,t.a3117a,t.a3118,t.a3137,t.a3138 ,t.updated,t.a3110,t.a3109,t.a3108 from A31 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A31.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A31 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A31, "A31", zippath);
							rs_A31.close();
							stmt.close();
							sql ="select t.a0000,t.a3600,t.a3601,t.a3604a,t.a3607,t.a3611,t.a3627 ,t.sortid ,t.updated from A36 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A36.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A36 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A36, "A36", zippath);
							rs_A36.close();
							stmt.close();
							sql ="select t.a0000,t.a3701,t.a3707a,t.a3707c,t.a3707e,t.a3707b,t.a3708,t.a3711,t.a3714,t.updated from A37 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A37.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A37 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A37, "A37", zippath);
							rs_A37.close();
							stmt.close();
							sql ="select t.a4100,t.a0000,t.a1100 ,t.a4101,t.a4102,t.a4103 ,t.a4104,t.a4105 ,t.a4199 from A41 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A41.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A41 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A41, "A41", zippath);
							rs_A41.close();
							stmt.close();
							sql ="select t.a0000,t.a5300,t.a5304,t.a5315,t.a5317,t.a5319,t.a5321,t.a5323,t.a5327,t.a5399,t.updated from A53 t where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A53.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A53 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A53, "A53", zippath);
							rs_A53.close();
							stmt.close();
							sql ="select a0000,a5714,photoname,photstype,updated,photopath from A57 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A57.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A57 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A57, "A57", zippath);
							rs_A57.close();
							stmt.close();
							sql ="select A0000,A6001,A6002,A6003,A6004,A6005,A6006,"
									+ "A6007,A6008,A6009,A6010,A6011,A6012,A6013,"
									+ "A6014,A6015,A6016,A6017 from A60 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A60.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A60 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A60, "A60", zippath);
							rs_A60.close();
							stmt.close();
							sql ="select A6116,A0000,A2970,A2970A,A2970B,A6104,"
									+ "A2970C,A6107,A6108,A6109,A6110,A6111,"
									+ "A6112,A6113,A6114,A6115 from A61 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A61.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A61 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A61, "A61", zippath);
							rs_A61.close();
							stmt.close();
							sql ="select A0000,A2950,A6202,A6203,A6204,A6205"
									+ " from A62 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A62.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A62 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A62, "A62", zippath);
							rs_A62.close();
							stmt.close();
							sql ="select A0000,A2951,A6302,A6303,A6304,A6305,"
									+ "A6306,A6307,A6308,A6309,A6310 from A63 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A63.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A63 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A63, "A63", zippath);
							rs_A63.close();
							stmt.close();
							sql ="select A0000,A6401,A6402,A6403,A6404,A6405,"
									+ "A6406,A6407,A6408 from A64 where a0000 in(" + b.toString() + ")";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A64.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_A64 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_A64, "A64", zippath);
							rs_A64.close();
							stmt.close();
							sql ="select b0101,b0104,b0107,b0111,b0114,b0117,b0121,b0124,b0127,b0131,"+
									"b0140,b0141,b0142,b0143,b0150,b0180,b0183,b0185,b0188,b0189,"+
									"b0190,b0191,b0191a,b0192,b0193,b0194,b01trans,b01ip,b0227,b0232,"+
									"b0233,sortid,used,t.updated,create_user,create_date,update_user,update_date,t.status,b0238,b0239,b0234 from b01 t where b0111 like '"+ searchDeptid +"%'";
							KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：B01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_b01 = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_b01, "B01", zippath);
							rs_b01.close();
							stmt.close();
							sql ="select * from INFO_EXTEND where a0000 in(" + b.toString() + ")";
							//--------- 补充信息xml生成 INFO_EXTEND.xml\B01_EXT.xml---------------------------------------------
							KingbsconfigBS.saveImpDetail(process_run,"1","补充数据生成处理",uuid);		//记录导入过程
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet rs_info = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(rs_info, "INFO_EXTEND", zippath);
							rs_info.close();
							stmt.close();
							sql ="select * from B01_EXT where b0111 like '"+ searchDeptid +"%'";
							stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
							stmt.setFetchSize(5000);
							stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
							ResultSet b01_info = stmt.executeQuery();
							Xml4HZBUtil2.List2Xml(b01_info, "B01_EXT", zippath);
							b01_info.close();
							stmt.close();
						}
						
						KingbsconfigBS.saveImpDetail(process_run,"1","数据说明文件生成处理中",uuid);	
						Xml4HZBUtil2.List2Xml(list17, "info", zippath);
					} else {
						zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".hzb";
						CommonQueryBS.systemOut(DateUtil.getTime());
						ResultSet rs = null;
						Xml4HZBUtil2.List2Xml(rs, "A01", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A02", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A06", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A08", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A11", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A14", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A15", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A29", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A30", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A31", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A36", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A37", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A41", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A53", zippath);
						Xml4HZBUtil2.List2Xml(rs, "A57", zippath);
						KingbsconfigBS.saveImpDetail(process_run,"1","文件"+"：B01.xml数据生成处理",uuid);		//记录导入过程
						Xml4HZBNewUtil.data2Xml(searchDeptid, "B01", zippath, conn);
						KingbsconfigBS.saveImpDetail(process_run,"1","数据说明文件生成处理中",uuid);	
						Xml4HZBUtil2.List2Xml(list17, "info", zippath);
					}
				}
//				if(zdcjg.equals("0")){
//					KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中",uuid);	
//					Object obj = HBUtil.getHBSession().createSQLQuery(			//计算数据总量
//							"select count(1) from a57 t where a0000 in (" + b.toString() + ") ").uniqueResult();
//					int count = 0;		//分批此处
//					if (obj != null) {
//						if (DBUtil.getDBType().equals(DBType.MYSQL)) {			
//							count = ((BigInteger) obj).intValue()/ 2000
//									+ (((BigInteger) obj).intValue() % 2000 != 0 ? 1 : 0);
//						} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
//							count = ((BigDecimal) obj).intValue()/ 2000
//									+ (((BigDecimal) obj).intValue() % 2000 != 0 ? 1 : 0);
//						}
//					}
//					if(count == 0){
//						count = 1;
//					}
//					for (int i = 0; i < count; i++) {
//						stmt = conn.prepareStatement(sql,ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//						stmt.setFetchSize(500);
//						stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
//						CommonQueryBS.systemOut("neicun："+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
//						ResultSet rs = null;
//						if(DBUtil.getDBType().equals(DBType.MYSQL)){
//							rs = stmt.executeQuery("select a0000,a5714,photoname,photstype,updated,photopath from A57 where a0000 in(" + b.toString() + ") " +
//									" order by a0000 limit " + (2000*i) +"," + 2000 );
//						} else {
//							rs = stmt.executeQuery("select a0000,a5714,photoname,photstype,updated,photopath from " +
//									"(select a0000,a5714,photoname,photstype,updated,photopath,rownum rn from " +
//									"(select a0000,a5714,photoname,photstype,updated,photopath from A57 where a0000 in(" + b.toString() + ") " +
//									" order by a0000) k " +
//									"where rownum<=" + (2000*(i+1))+ " ) where rn>" + (2000*i));
//						}
//						
//						if(rs!=null){
//							String photopath = zippath + "Photos/";				//生成图片路径      
//							File file2 =new File(photopath);    
//							if  (!file2 .exists()  && !file2 .isDirectory()){   //如果文件夹不存在则创建       
//								file2 .mkdirs();    
//							}
//							List<String> photolist = new ArrayList<String>();
//							while (rs.next()) {
//								String a0000 = rs.getString("a0000");
//								String photoname = rs.getString("photoname");
//								String photop = rs.getString("photopath");
//								String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
//								photolist.add(photop+photon);
//							}
////							PhotosUtil.copyCmd(photolist, photopath);
//							PhotosUtil.copyPhotos(photolist, photopath);
//						}
//						rs.close();
//						stmt.close();
//					}
//				}
				
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
				infile = zipfile;
				NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
				try {
					if (gjgs!=null && gjgs.equals("2")) {
						new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
					} else {
						new LogUtil("411", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.delFolder(zippath);
			*/}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.delFolder(path);
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
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
}
