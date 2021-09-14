package com.insigma.siis.local.pagemodel.dataverify;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.naming.Context;
import javax.naming.InitialContext;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.xml.sax.helpers.AttributesImpl;

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
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.util.ZipCompressing;

public class DataOrgExpPartThreadDUJG implements Runnable {
	
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
	
	public DataOrgExpPartThreadDUJG(String uuid, String gjgs, String searchDeptid,
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
		/*String tables[] = {"A01", "A02","A05","A06","A08", "A11", "A14", "A15", "A29","A30", 
				"A31","A36","A37","A41", "A53","A57", "B01", "A60", "A61", "A62", "A63", "A64"
				,"A68","A69","A71","INFO_EXTEND","B01_EXT","PHOTO","A99Z1"};*/
		String tables[] = {"A01", "A02","A05","A06","A08", "A14", "A15","A36","A57", "B01"
							,"INFO_EXTEND","B01_EXT","PHOTO","A99Z1"};
		
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
				if(number > 13){
					break;
				}
				/*if(number > 28){
					break;
				}*/
				String table = tables[number];
				if(("zip".equals(sign)||"7z".equals(sign)) && ("INFO_EXTEND".equals(table)||"B01_EXT".equals(table)||"PHOTO".equals(table)) ){
					continue;
				}
				CommonQueryBS.systemOut("thd---"+no +"----"+number+"---"+table);
				appendFileContent(logfilename, "thd---"+no +"----"+number+"---"+table+":"+" "+w.elapsedTime()+"    "+ DateUtil.getTime()+"\n");	
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number)+"："+table+".xml数据生成处理，剩余"+(control.getNumber2())+"",uuid);		//记录导入过程
				String ring = table.equals("B01")||table.equals("B01_EXT")?searchDeptid:b.toString();
				if(table.equals("B01")||table.equals("B01_EXT")){
					Xml4HZBNewUtil.data2Xml(ring, table, zippath, conn, fetchsize,logfilename,sign,userVo);
				}else{
					Element zrow = null;
					Element col = null;
					XMLWriter out = null;
					BufferedWriter bw = null;
					OutputStreamWriter osw = null;
					FileOutputStream fos = null;
					
					try {
						File xmlFile = new File(zippath + "Table/" + table+".xml");// 输出xml的路径
						fos = new FileOutputStream(xmlFile);
						osw = new OutputStreamWriter(fos, "UTF-8");// 指定编码，防止写中文乱码
						bw = new BufferedWriter(osw);
						// 对xml输出格式化
						OutputFormat format = OutputFormat.createPrettyPrint();
						format.setEncoding("UTF-8");
						out = new XMLWriter(fos, format);

						out.setEscapeText(false);
						out.startDocument();
						Document document = DocumentHelper.createDocument();
						// 添加元素 xml
						Element xmlElement = document.addElement("xml");
						out.writeOpen(xmlElement);
						
						
						//-------------------------------
//						Element rsdata = xmlElement.addElement("data");
//						out.writeOpen(rsdata);
						AttributesImpl a = new AttributesImpl();
						out.startElement("", "", "data", a);
						/**
						 * 加载数据
						 */
						//--------------------------------------------------
//						out.writeClose(rsdata);
						out.endElement("", "", "data");
						out.writeClose(xmlElement);
						xmlElement.clearContent();
						out.endDocument();
						out.close();
						System.gc();
//						HBUtil.getHBSession().createSQLQuery("drop table " + newTable).executeUpdate();
					} catch (Exception e) {
						e.printStackTrace();
						throw e;
					} finally {
						try {
							if (out != null) 
								out.close();
							if (bw != null) 
								bw.close();
							if (osw != null) 
								osw.close();
							if (fos != null) 
								fos.close();
						} catch (Exception e) {
							e.printStackTrace();
							throw e;
						}
					}
				}
			}
			if(control.getStatus()==1){
				
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
				infile = zipfile;
				appendFileContent(logfilename, "压缩---"+":"+ DateUtil.getTime()+"\n");	
				w.start();
				//获取追加命令
				//执行命令复制图片
//				MingLing7zUtil.add(zipfile, zippath);
				//页面详情信息-----------------------------------
				String sql3 = "update expinfo set STATUS = '文件压缩中请稍候...' where ID = '"+uuid+"'";
				sess.createSQLQuery(sql3).executeUpdate();
				//------------------------------------------------
				if("7z".equals(sign)){
					Zip7z.zip7Z(zippath, zipfile, "20171020");
				}else if("zip".equals(sign)){
					Zip7z.zip7Z(zippath, zipfile, null);
				}else{
					NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				}
				w.stop();
				appendFileContent(logfilename, "压缩完成---"+":"+ DateUtil.getTime()+"\n");	
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
				appendFileContent(logfilename, "压缩完成:"+"\n"+w.elapsedTime()+"\n");
				//------------------------------------------------------------
				//记录页面详情
				String time2 = DateUtil.timeToString(DateUtil.getTimestamp(),"yyyy-MM-dd HH:mm:ss");
				String sql2 = "update EXPINFO set endtime = '"+time2+"',STATUS = '导出完成',zipfile = '"+zipfile+"' where id = '"+uuid+"'";
				sess.createSQLQuery(sql2).executeUpdate();
				//---------------------------------------------------------------
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
				HBUtil.getHBSession().createSQLQuery("drop table "+ b +"").executeUpdate();
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
