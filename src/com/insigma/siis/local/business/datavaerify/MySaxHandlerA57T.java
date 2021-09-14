package com.insigma.siis.local.business.datavaerify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import oracle.sql.BLOB;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.hibwrap.WrapConnetionImpl;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class MySaxHandlerA57T implements ElementHandler {
	
	public String docname;
	public String lowerCase;
	public String table;
	public String imprecordid;
	public int t_n;
	public int batchNum;
	public String uuid;
	public String from_file;
	public String B0111;
	public String impdeptid;
	public String deptid;
	public PreparedStatement pstmt1;
	public Connection conn1;
	public boolean isError = false;
	public MySaxHandlerA57T() {
	}
	

	
	public MySaxHandlerA57T(String docname, String lowerCase, String table,
			String imprecordid, int t_n, String uuid, String from_file,
			String B0111, String deptid, String impdeptid, Connection conn1, PreparedStatement pstmt1,int batchNum) {
		super();
	
		this.docname = docname;
		this.lowerCase = lowerCase;
		this.table = table;
		this.imprecordid = imprecordid;
		this.t_n = t_n;
		this.uuid = uuid;
		this.from_file = from_file;
		this.B0111 = B0111;
		this.impdeptid = impdeptid;
		this.deptid = deptid;
		this.pstmt1 = pstmt1;
		this.conn1= conn1;
		this.batchNum = batchNum;
	}

	@Override
	public void onStart(ElementPath ep) {
		if(isError){
			return;
		}
		Element element = ep.getCurrent(); // 获得当前节点
		if(element!=null){
			HBSession sess = HBUtil.getHBSession();
			try {

				
				Map rowData = new HashMap();
			
				List<Attribute> attrs = element.attributes();  
				for (Attribute attr : attrs) {  
					rowData.put(attr.getName().toUpperCase(), attr.getValue());
	            }
				
				if(rowData.get("A0000")!=null && !rowData.get("A0000").toString().equals("")){
					pstmt1.setObject(1, rowData.get("A0000"));
					pstmt1.setObject(2, rowData.get("A5714"));
					pstmt1.setObject(3, "1");
					pstmt1.setObject(4, "");
					pstmt1.setObject(5, "0");
					pstmt1.setObject(6, imprecordid);
					
					String a5714 = rowData.get("A5714")!=null?rowData.get("A5714").toString():"";
					String a5714_arr[] = a5714.split("\\|");
//					File file_p = new File(from_file + "/" +(a5714_arr[0].equals("")?(rowData.get("A0000")+".jpg"):a5714_arr[0]));
//				    FileInputStream fin = null;
//				    if(file_p!=null && file_p.exists()&& file_p.isFile()){
//			        	fin = new FileInputStream(file_p);   
//			        	pstmt1.setBinaryStream(7, fin, fin.available());  //第二个参数为文件的内容  
//			        } else {
//			        	pstmt1.setObject(7, null);
//			        }
					pstmt1.setObject(7, null);
					String photoname = a5714_arr[0].equals("")?(rowData.get("A0000")+".jpg"):a5714_arr[0];
				    pstmt1.setObject(8, photoname);
				    pstmt1.setObject(9, "jpg");
				    String photopath = "";
				    String subphotoname = photoname.substring(0, photoname.indexOf("."));
				    if (subphotoname.length() >= 2) {
				    	String str = subphotoname.substring(0, 2);
				    	if(PhotosUtil.isLetterDigit(str)){
				    		photopath = photoname.charAt(0)+"/"+photoname.charAt(1)+"/";
				    	} else {
				    		photopath = photoname.charAt(0)+"/";
				    	}
					} else if (photoname.substring(0, photoname.indexOf(".")).length() == 1) {
						photopath = photoname+"/";
					}
				    pstmt1.setObject(10, photopath);
//			    	pstmt1.executeUpdate();
//			    	pstmt1.clearParameters();
			    	pstmt1.addBatch();
//			    	if(fin!=null)
//			    		fin.close();
					rowData.clear();
					rowData=null;
				}
				
				attrs.clear();
		    	attrs = null;
				sess.flush();
				t_n++;
				if(t_n%5000==0){

					//=================================================
					StopWatch w = new StopWatch();
					//================================
					w.start();
					pstmt1.executeBatch();
					w.stop();
//					appendFileContent("D:\\ceShiDaoRuShiJian.txt", "A57每5000次执行一次的时间"+w.elapsedTime()+"\n");

					pstmt1.clearBatch();
					CommonQueryBS.systemOut(table+"======  NO."+t_n/5000+"  ========"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
		    		System.gc();
				}
			} catch (Exception e1) {
				isError = true;
				try {
					KingbsconfigBS.saveImpDetail("3" ,"4","失败:"+e1.getMessage(),uuid);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				e1.printStackTrace();
				new UploadHzbFileBS().rollbackImp(imprecordid);
			}
		}
		element.detach(); // 记得从内存中移去
	}

	@Override
	public void onEnd(ElementPath ep) {
//		Element urlNode = ep.getCurrent();  
//        urlNode.detach();
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