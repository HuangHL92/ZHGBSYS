package com.insigma.siis.local.pagemodel.dataverify.zhgb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.fr.third.org.apache.commons.dbcp.BasicDataSource;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.worktable.openapi.util;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil;
import com.insigma.siis.local.business.utils.kingbs.CommandImpUtil;
import com.insigma.siis.local.business.utils.kingbs.EncryptUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.DataOrgExpControl;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.util.SqlUtil;
import com.insigma.siis.local.util.ZipCompressing;

public class ZHGBDataOrgExpPartThread implements Runnable {
	
	private String uuid;
	private String gjgs;
	private String searchDeptid;
	private String zdcjg;
	private UserVO userVo;
	private String b;
	private B01 b01;
	private DataOrgExpControl control;
	private String no;
	private String logfilename;
	private String sign;
	
	public ZHGBDataOrgExpPartThread(String uuid, String gjgs, String searchDeptid,
			String zdcjg, UserVO userVo, String b, B01 b01,
			DataOrgExpControl control, String logfilename, String no, String sign) {
		super();
		this.uuid = uuid;
		this.gjgs = gjgs;
		this.searchDeptid = searchDeptid;
		this.zdcjg = zdcjg;
		this.userVo = userVo;
		this.b = b;
		this.b01 = b01;
		this.control = control;
		this.no = no;
		this.logfilename = logfilename;
		this.sign = sign;
	}

	@Override
	public void run() {
		StopWatch w = new StopWatch();
		String infile = "";					 					// 文件
		String process_run = "2";								// 过程序号
		//pywu    20170609 加表a05  a68 a69 a71
		/*String tables[] = {"A01", "A02","A05","A06","A08", "A11", "A14", "A15", "A29","A30", 
				"A31","A36","A37","A41", "A53","A57", "B01", "A60", "A61", "A62", "A63", "A64"
				,"A68","A69","A71","INFO_EXTEND","B01_EXT","PHOTO","A99Z1"};*/
		String tables[] = {"A01", "A02","A05","A06","A08", "A14", "A15", "A36","A57", "B01","INFO_EXTEND","B01_EXT","PHOTO","A99Z1"};
//		String tables[] = {"A01","A15"};
		try {
			w.start();
			String path = control.getPath();
			String zippath = control.getZippath();
			HBSession sess = HBUtil.getHBSession();
			Connection conn = null;
			PreparedStatement stmt = null;
			//String zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".hzb";
			String zipfile = "";
			if("zip".equals(sign)){
				zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".zip";
			}else if("7z".equals(sign)){
				zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".7z";
			}else{
				zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".hzb";
			}
			
			int fetchsize = 100;
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				Context	env = (Context) new InitialContext();
		        BasicDataSource datasourceRef = (BasicDataSource) env.lookup("java:comp/env/jdbc/insiis");
		        String url= datasourceRef.getUrl();
		        Class.forName("com.mysql.jdbc.Driver");
		        conn=DriverManager.getConnection(url,datasourceRef.getUsername(),datasourceRef.getPassword()); 
		        fetchsize = Integer.MIN_VALUE;
			} else {
				conn = sess.connection();
			}
			int number = 0;
			w.stop();
			appendFileContent(logfilename, "线程"+no+"_1:"+"\n"+w.elapsedTime()+"\n"+ DateUtil.getTime()+"\n");	
			while (true) {
				w.start();
				number = control.getNewNumber();
				if(number>13){
					break;
				}
				/*if(number>28){
					break;
				}*/
				String table = tables[number];
				CommonQueryBS.systemOut("thd---"+no +"----"+number+"---"+table);
				if(table.equals("PHOTO")){
					KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中",uuid);	
					//页面详情信息照片文件生成中-----------------------------------
					String sql2 = "update expinfo set STATUS = '照片生成中请稍候...' where ID = '"+uuid+"'";
					sess.createSQLQuery(sql2).executeUpdate();
					//------------------------------------------------
					String photopath = zippath + "Photos/";				//生成图片路径      
					File file2 =new File(photopath);    
					if  (!file2 .exists()  && !file2.isDirectory()){
						file2.mkdirs();    
					}
					
					//pywu 20170608  A57表所有字段
					//查询人员图片数据
		            String createsql = SqlUtil.getSqlByTableName("A57", "t") + "where EXISTS (SELECT 1 from " + b + " v where v.A0000=t.A0000)" ;
		            
//					String createsql = "select a0000,a5714,photoname,photstype,updated,photopath "+ 		//查询人员图片数据
//							" FROM A57 t where EXISTS (SELECT 1 from " + b + " v where v.A0000=t.A0000)" ;
					stmt = conn.prepareStatement(createsql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(fetchsize);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						String a0000 = rs.getString("a0000");
						String photoname = rs.getString("photoname");
						String photop = rs.getString("photopath");
						String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
						PhotosUtil.copyFile(photop+photon, photopath);
					}
					w.stop();
					appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
				
				} else {
					appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number)+"："+table+".xml数据生成处理，剩余"+(control.getNumber2())+"",uuid);		//记录导入过程
					//
					ZHGBXml4HZBNewUtil.data2Xml((table.equals("B01")||table.equals("B01_EXT"))?searchDeptid:b.toString(), table, zippath, conn, fetchsize, logfilename,sign,userVo);
					w.stop();
					appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
				}
				
			}	
				//---- 复制人员图片 ---------------------------------------------------------------------------
			if(control.getStatus()==2){
				HBUtil.getHBSession().createSQLQuery("drop table " + b).executeUpdate();
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
				
				
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
				infile = zipfile;
				appendFileContent(logfilename, "压缩---"+":"+ DateUtil.getTime()+"\n");	
				w.start();
				//页面详情信息-----------------------------------
				String sql3 = "update expinfo set STATUS = '文件压缩中请稍候...' where ID = '"+uuid+"'";
				sess.createSQLQuery(sql3).executeUpdate();
				if("zip".equals(sign)){
					Zip7z.zip7Z(zippath, zipfile, null);
				}else if("7z".equals(sign)){					
					Zip7z.zip7Z(zippath, zipfile, "20171020");
				}else if(sign.startsWith("f2e5de60-4eae-4e7e-990e-e46aef4d6fce")){	//培训信息交换导出			
					Zip7z.zip7Z(zippath, zipfile, "pxxx");
				}else if(sign.endsWith(".ZDY")){	//自定义				
					Zip7z.zip7Z(zippath, zipfile, null);
				}else{
					NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				}
				w.stop();
				appendFileContent(logfilename, "压缩完成---"+":"+ DateUtil.getTime()+"\n");	
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
				appendFileContent(logfilename, "压缩完成:"+"\n"+w.elapsedTime()+"\n");

					
				//-------------------------------------------------------------------------------
				String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '导出完成',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
				sess.createSQLQuery(sql2).executeUpdate();
				//-----------------------------------------------------------------------------------
				try {
					if ("7z".equals(sign)||"zip".equals(sign)) {
						new LogUtil("412", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
					} else {
						new LogUtil("411", "IMP_RECORD", "", "", "数据导出", new ArrayList(),userVo).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.delFolder(zippath);
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				String sql4 = "update expinfo set STATUS = '文件导出异常!' where ID = '"+uuid+"'";
				HBUtil.getHBSession().createSQLQuery(sql4).executeUpdate();
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(no.equals("1")){
				control.errStatus("1");
			}else {
				control.errStatus("2");
			}
			this.delFolder(control.getZippath());
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
	private static String getNo(){
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
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
}
