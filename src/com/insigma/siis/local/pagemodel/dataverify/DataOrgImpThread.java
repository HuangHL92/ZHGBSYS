package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
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
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgImpThread implements Runnable {
	
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
    public DataOrgImpThread(String uuid, CurrentUser user,String gjgs,String ltry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,String tabimp,String zdcjg) {
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
    }

	@Override
	public void run() {
		Map<String, String> map = new HashMap<String, String>();
		String infile = "";										// 文件
		String process_run = "1";								// 过程序号
		try {
			CommonQueryBS.systemOut(DateUtil.getTime());
			if(tabimp == null || tabimp.equals("") || tabimp.equals("1")){
				HBSession sess = HBUtil.getHBSession();
				B01 b01 = (B01) sess.get(B01.class, searchDeptid);
				//------------      -------------//
				StringBuilder b = new StringBuilder();
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					b.append("select a1.a0000 from a01 a1 LEFT JOIN a02 a2 on a1.a0000=a2.a0000 where 1=1");
					if(ltry.equals("0")){
						b.append(" and a1.status<>'3'");
					}
					if(!gz_lb.equals("")){
						if(gzlbry.equals("0")){
							b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
						}
					}
					if(!gl_lb.equals("")){
						if(gllbry.equals("0")){
							b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
						}
					}
					b.append(" and a2.a0201b in (select mt.b0111 from (select b0111 from b01 where b0111 like '"+ searchDeptid +"%') mt)");
					CommonQueryBS.systemOut(b.toString());
				} else if(DBUtil.getDBType().equals(DBType.ORACLE)){
					b.append("select a1.a0000 from a01 a1,a02 a2 where a1.a0000=a2.a0000(+)");
					if(ltry.equals("0")){
						b.append(" and a1.status<>'3'");
					}
					if(!gz_lb.equals("")){
						if(gzlbry.equals("0")){
							b.append(" and a1.a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
						}
					}
					if(!gl_lb.equals("")){
						if(gllbry.equals("0")){
							b.append(" and a1.a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
						}
					}
					b.append(" and a2.a0201b in (select b0111 from b01 start with b0111='"+ searchDeptid +"' connect by prior b0111= b0121)");
				}
				List<B01> list16 = null;
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					list16 = sess.createSQLQuery("select * from b01 where b0111 like '"+ searchDeptid +"%'").addEntity(B01.class).list();
				} else if(DBUtil.getDBType().equals(DBType.ORACLE)){
					list16 = sess.createSQLQuery("select * from b01 start with b0111='"+ searchDeptid +"' connect by prior b0111= b0121").addEntity(B01.class).list();
				}
				List<A01> list = (!zdcjg.equals("0"))? new ArrayList<A01>():sess.createSQLQuery("select * from a01 where a0000 in(" + b.toString() + ")").addEntity(A01.class).list();
				List<A02> list2 = (!zdcjg.equals("0"))? new ArrayList<A02>():sess.createSQLQuery("select * from A02 where a0000 in(" + b.toString() + ")").addEntity(A02.class).list();
				List<A06> list3 = (!zdcjg.equals("0"))? new ArrayList<A06>():sess.createSQLQuery("select * from A06 where a0000 in(" + b.toString() + ")").addEntity(A06.class).list();
				List<A08> list4 = (!zdcjg.equals("0"))? new ArrayList<A08>():sess.createSQLQuery("select * from A08 where a0000 in(" + b.toString() + ")").addEntity(A08.class).list();
				List<A11> list5 = (!zdcjg.equals("0"))? new ArrayList<A11>():sess.createSQLQuery("select * from A11 where a0000 in(" + b.toString() + ")").addEntity(A11.class).list();
				
				List<A14> list6 = (!zdcjg.equals("0"))? new ArrayList<A14>():sess.createSQLQuery("select * from A14 where a0000 in(" + b.toString() + ")").addEntity(A14.class).list();
				List<A15> list7 = (!zdcjg.equals("0"))? new ArrayList<A15>():sess.createSQLQuery("select * from A15 where a0000 in(" + b.toString() + ")").addEntity(A15.class).list();
				List<A29> list8 = (!zdcjg.equals("0"))? new ArrayList<A29>():sess.createSQLQuery("select * from A29 where a0000 in(" + b.toString() + ")").addEntity(A29.class).list();
				List<A30> list9 = (!zdcjg.equals("0"))? new ArrayList<A30>():sess.createSQLQuery("select * from A30 where a0000 in(" + b.toString() + ")").addEntity(A30.class).list();
				List<A31> list10 = (!zdcjg.equals("0"))? new ArrayList<A31>():sess.createSQLQuery("select * from A31 where a0000 in(" + b.toString() + ")").addEntity(A31.class).list();
				
				List<A36> list11 = (!zdcjg.equals("0"))? new ArrayList<A36>():sess.createSQLQuery("select * from A36 where a0000 in(" + b.toString() + ")").addEntity(A36.class).list();
				List<A37> list12 = (!zdcjg.equals("0"))? new ArrayList<A37>():sess.createSQLQuery("select * from A37 where a0000 in(" + b.toString() + ")").addEntity(A37.class).list();
				List<A41> list13 = (!zdcjg.equals("0"))? new ArrayList<A41>():sess.createSQLQuery("select * from A41 where a0000 in(" + b.toString() + ")").addEntity(A41.class).list();
				List<A53> list14 = (!zdcjg.equals("0"))? new ArrayList<A53>():sess.createSQLQuery("select * from A53 where a0000 in(" + b.toString() + ")").addEntity(A53.class).list();
				List<A57> list15 = (!zdcjg.equals("0"))? new ArrayList<A57>():sess.createSQLQuery("select * from A57 where a0000 in(" + b.toString() + ")").addEntity(A57.class).list();
				List<Map> list17 = new ArrayList<Map>();
				if(zdcjg.equals("0")){
					map.put("type", "21");  	//导出全数据
				} else {
					map.put("type", "22");		//导出机构数据
				}
				map.put("time", DateUtil.timeToString(DateUtil.getTimestamp()));
				
				map.put("dataversion", "20121221");
				map.put("psncount", (list!=null&&list.size()>0)? (list.size()+"") :"" );
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				list17.add(map);
				String path = getPath();
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
				KingbsconfigBS.saveImpDetail("1","2","完成",uuid);						//记录导入过程
				process_run = "2";
				int number1 = 1;														//已解析表的树木
				int number2 = 15;														//未解析标的树木
				KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
				if (gjgs!=null && gjgs.equals("1")) {
					
					zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".hzb";
					Xml4HZBUtil.List2Xml(list, "A01", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A02.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list2, "A02", zippath);
					
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A06.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list3, "A06", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A08.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list4, "A08", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A11.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list5, "A11", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A14.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					
					Xml4HZBUtil.List2Xml(list6, "A14", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A15.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list7, "A15", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A29.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					
					Xml4HZBUtil.List2Xml(list8, "A29", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A30.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list9, "A30", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A31.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list10, "A31", zippath);
					
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A36.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list11, "A36", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A37.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list12, "A37", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A41.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list13, "A41", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A53.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list14, "A53", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：A57.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list15, "A57", zippath);
					
					KingbsconfigBS.saveImpDetail(process_run,"1","文件"+(number1++)+"：B01.xml数据生成处理，剩余"+(number2--)+"",uuid);		//记录导入过程
					Xml4HZBUtil.List2Xml(list16, "B01", zippath);
					KingbsconfigBS.saveImpDetail(process_run,"1","数据说明文件生成处理中",uuid);	
					Xml4HZBUtil.List2Xml(list17, "info", zippath);
					
				} else if (gjgs!=null && gjgs.equals("2")) {
					zipfile = path + "按机构导出文件_" +b01.getB0114()+"_" +b01.getB0101() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".zb3";
					Xml4Zb3Util.List2Xml(list, "A01", zippath);
					Xml4Zb3Util.List2Xml(list2, "A02", zippath);
					Xml4Zb3Util.List2Xml(list3, "A06", zippath);
					Xml4Zb3Util.List2Xml(list4, "A08", zippath);
					Xml4Zb3Util.List2Xml(list5, "A11", zippath);
					
					Xml4Zb3Util.List2Xml(list6, "A14", zippath);
					Xml4Zb3Util.List2Xml(list7, "A15", zippath);
					Xml4Zb3Util.List2Xml(list8, "A29", zippath);
					Xml4Zb3Util.List2Xml(list9, "A30", zippath);
					Xml4Zb3Util.List2Xml(list10, "A31", zippath);
					
					Xml4Zb3Util.List2Xml(list11, "A36", zippath);
					Xml4Zb3Util.List2Xml(list12, "A37", zippath);
					Xml4Zb3Util.List2Xml(list13, "A41", zippath);
					Xml4Zb3Util.List2Xml(list14, "A53", zippath);
					Xml4Zb3Util.List2Xml(list15, "A57", zippath);
					
					Xml4Zb3Util.List2Xml(list16, "B01", zippath);
					Xml4Zb3Util.List2Xml(list17, "info", zippath);
					String frompath = getRootPath() + "/uploud/wlhxml/";
					UploadHelpFileServlet.copyDirectiory(frompath, zippathtable);
					
				}
				KingbsconfigBS.saveImpDetail(process_run,"1","人员照片头像生成处理中",uuid);	
				if(list15 != null && list15.size()>0){
					String photopath = zippath + "Photos/";
					File file2 =new File(photopath);    
					//如果文件夹不存在则创建    
					if  (!file2 .exists()  && !file2 .isDirectory())      
					{       
						file2 .mkdirs();    
					}
					for (int i = 0; i < list15.size(); i++) {
						A57 a57 = list15.get(i);
						if(a57.getA0000()!=null && !a57.getA0000().equals("") && a57.getPhotodata()!=null){
							File f = new File(photopath + a57.getA0000() +"." + "jpg");
							FileOutputStream fos = new FileOutputStream(f);
							InputStream is = a57.getPhotodata().getBinaryStream();// 读出数据后转换为二进制流
							byte[] data = new byte[1024];
							while (is.read(data) != -1) {
								fos.write(data);
							}
							fos.close();
							is.close();
						}
					}
				}
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid);			//记录导入过程
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","压缩中",uuid);			//记录导入过程
				infile = zipfile;
				NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				/*if (gjgs!=null && gjgs.equals("2")) {
					new LogUtil().createLog("421", "IMP_RECORD", "", "", "数据导出", new ArrayList());
				} else {
					new LogUtil().createLog("422", "IMP_RECORD", "", "", "数据导出", new ArrayList());
				}*/
				KingbsconfigBS.saveImpDetail(process_run,"2","完成",uuid,infile.replace("\\", "/"));	
			}
//			System.out.println(DateUtil.getTime());
//			this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
//			
//			this.getExecuteSG().addExecuteCode("window.reloadTree()");
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","失败:"+e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//结束提示信息。 
			e.printStackTrace();
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
