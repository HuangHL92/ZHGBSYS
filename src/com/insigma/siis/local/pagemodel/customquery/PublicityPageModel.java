package com.insigma.siis.local.pagemodel.customquery;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.yntp.YNTPFileExportBS;

import net.sf.json.JSONObject;

public class PublicityPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("peopleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}


	
	@PageEvent("peopleGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int peopleSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String sql = "select a.a0000,a.a0101,a.a0104,a.a0117,a.a0107,a.a0111a,a.a0192a,b.sortid from  a01 a,publicity b where a.a0000=b.a0000 and b.userid='"+SysManagerUtils.getUserId()+"'  order by sortid";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("delperson")
	@Transaction
	@Synchronous(true)
	public int delperson(String a0000) throws RadowException, AppException, PrivilegeException {
		HBSession sess=HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="delete from publicity where a0000='"+a0000+"' and userid='"+SysManagerUtils.getUserId()+"'";
			stmt.executeUpdate(sql);
			this.setNextEventName("peopleGrid.dogridquery");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteAll")
	@Transaction
	@Synchronous(true)
	public int deleteAll() throws RadowException, AppException, PrivilegeException {
		HBSession sess=HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="delete from publicity where userid='"+SysManagerUtils.getUserId()+"'";
			stmt.executeUpdate(sql);
			this.setNextEventName("peopleGrid.dogridquery");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//公示
	@PageEvent("expGSZipBtn")
	@Transaction
	public int expGSZipBtn() throws RadowException{
		HBSession sess=HBUtil.getHBSession();
		String sql_photo="select * from A57 where a0000 in (select a0000 from publicity where userid='"+SysManagerUtils.getUserId()+"') order by a0000 ";
		String sql_grjl="select a0000,a0192a,a0102,"
				+ "case when qrzxldj>zzxldj then '在职'||zzxl else qrzxl end zgxl,"
				+ "case when qrzxldj>zzxldj then zzxldj else qrzxldj end zgxldj,"
				+ "case when qrzxwdj>zzxwdj then '在职'||zzxw else qrzxw end zgxw,"
				+ "case when qrzxwdj>zzxwdj then zzxwdj else qrzxwdj end zgxwdj from "
				+ "(select a.a0000,a.a0192a,a0101,"
				+ "a0101||'，'||decode(a0104,'1','男','2','女')||'，'||(select c.code_name from code_value c where code_type='GB3304' and c.code_value=a.a0117)"
				+ "||'，'||substr(a0107,1,4)||'年'||to_number(substr(a0107,5,2))||'月生，'||a0111a||'人，'||decode(a0141,'01',substr(a0144,1,4)||'年'||to_number(substr(a0144,5,2))||'月加入中国共产党','02',substr(a0144,1,4)||'年'||to_number(substr(a0144,5,2))||'月成为预备党员',a0140)"
				+ "||decode(a0140,'','','，')||substr(a0134,1,4)||'年'||to_number(substr(a0134,5,2))||'月参加工作，' a0102,"
				+ "case when qrzxl like '%研究生%' or qrzxl like '%硕士%' then '研究生' when qrzxl like '%大学%' or qrzxl like '%学士%'   then '大学' when qrzxl like '%大专%'  then '大专' when qrzxl='中专' then '中专'  when qrzxl='职业高中' then '职高' when qrzxl='技工学校' then '技校' else qrzxl end  qrzxl, "
				+ "case when qrzxw like '%博士%'  then '博士' when qrzxw like '%硕士%'  then '硕士' when qrzxw like '%学士%'  then '学士' else '' end  qrzxw, "
				+ "case when qrzxl like '%研究生%' or qrzxl like '%硕士%' then '2' when qrzxl like '%大学%' or qrzxl like '%学士%' then '3' when qrzxl like '%大专%'  then '4' when qrzxl='中专' then '5'  when qrzxl='职业高中' then '6' when qrzxl='技工学校' then '7' else '8' end  qrzxldj, "
				+ "case when qrzxw like '%博士%'  or qrzxw like '%硕士%'  then '2' when qrzxw like '%学士%'  then '3' else '9' end  qrzxwdj,"
				
				+ "case when zzxl like '%研究生%' or zzxl like '%硕士%' then '研究生' when zzxl like '%大学%' or zzxl like '%学士%'   then '大学' when zzxl like '%大专%'  then '大专' when zzxl='中专' then '中专'  when zzxl='职业高中' then '职高' when zzxl='技工学校' then '技校' else zzxl end  zzxl, "
				+ "case when zzxw like '%博士%'  then '博士' when zzxw like '%硕士%'  then '硕士' when zzxw like '%学士%'  then '学士' else '' end  zzxw, "
				+ "case when zzxl like '%研究生%' or zzxl like '%硕士%' then '2' when zzxl like '%大学%' or zzxl like '%学士%' then '3' when zzxl like '%大专%'  then '4' when zzxl='中专' then '5'  when zzxl='职业高中' then '6' when zzxl='技工学校' then '7' else '8' end  zzxldj, "
				+ "case when zzxw like '%博士%'  or zzxw like '%硕士%'  then '2' when zzxw like '%学士%'  then '3' else '9' end  zzxwdj"
				+ "    from (select a.a0000,a.a0101,a.a0104,a.a0117,a.a0107,a.a0111a,a.a0140,a.a0141,a.a0144,a.a0134,a.qrzxl,a.qrzxw,a.zzxl,a.zzxw,a.a0192a from a01 a where a0000 in (select a0000 from publicity where userid='"+SysManagerUtils.getUserId()+"')) a ) a  order by a0101";
		
		
		String a0101="";
		String filePath = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'PHOTO_PATH'").uniqueResult();
		String SOURCEFILEPATH = filePath;
		String path = getPath();
		String zippath = path + DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss") + "/";
		File file = new File(zippath);
		// 如果文件夹不存在则创建
		if (!file.exists() && !file.isDirectory()) {
			file.mkdirs();
		}
		String photopath = zippath + "Photos/";
		File file_p =new File(photopath);   
		if (!file_p.exists() && !file_p.isDirectory()) {
			file_p.mkdirs();
		}
		CommQuery commQuery =new CommQuery();
		String zipfile = "";
		zipfile = "导出数据包_"+ DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".zip";
		List<A57> photolist = sess.createSQLQuery(sql_photo).addEntity(A57.class).list();
		if(photolist != null && photolist.size()>0){
			for (int i = 0; i < photolist.size(); i++) {
				A57 a57 = photolist.get(i);
				if(a57.getA0000()!=null && !a57.getA0000().equals("") && a57.getPhotoname()!=null&& a57.getPhotoname()!=""&& a57.getPhotopath()!=null&& a57.getPhotopath()!=""){
					String sql_a0101="select a0101 from a01 where a0000='"+a57.getA0000()+"' ";
					try {
						a0101=commQuery.getListBySQL(sql_a0101).get(0).get("a0101").toString();
					} catch (AppException e1) {
						e1.printStackTrace();
					}
					File sourcefile = new File(SOURCEFILEPATH+"\\"+a57.getPhotopath()+a57.getPhotoname());
					File targetFile = new File(photopath +"{"+(i+1)+"_"+a0101+"}." + "jpg");
					if (sourcefile.exists() && sourcefile.isFile()) {
						try {
							UploadHelpFileServlet.copyFile(sourcefile, targetFile);
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						
					}
				}
			}
			
		}
		String name = zippath+"个人简介_"+DateUtil.timeToString(DateUtil.getTimestamp(),"yyyyMMddHHmmss")+".doc";
//				try {
//					foo(name2);
//				} catch (Exception e2) {
//					e2.printStackTrace();
//				}
		try {
			
			List<HashMap<String, Object>>  list = commQuery.getListBySQL(sql_grjl); 

			FileOutputStream out = new FileOutputStream(name);
			PrintStream ps = new PrintStream(out);
			ps.print("");
			String str="";
			if(list!=null&&list.size()>0) {
				for(HashMap<String, Object> map:list) {
					str=str+map.get("a0102").toString()+map.get("zgxl").toString()+"学历";
					if(Integer.valueOf(map.get("zgxldj").toString())>Integer.valueOf(map.get("zgxwdj").toString())) {
						str=str+"，"+map.get("zgxw").toString()+"学位";
					}
					str=str+"。现任"+map.get("a0192a").toString()+"\r\r";
//					ps.append(str);
//					ps.append("\r\r");
				}
			}
			ps.append(str);
			ps.close();
			out.close();

			
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		Zip7z.zip7Z(zippath, zipfile, null);
		this.delFolder(zippath);
		try {
			this.getPageElement("downfile").setValue(zipfile.replace("\\", "/"));
		} catch (RadowException e) {

			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.reloadTree()");
		return EventRtnType.NORMAL_SUCCESS;
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
		String zip = upload_file + UUID.randomUUID().toString().replaceAll("-", "") + "/";
		return zip;
	}
	
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
	

		 //复制方法
	public void foo(String name) throws FileNotFoundException, IOException {
		ExportAsposeBS export_as=new ExportAsposeBS();
		String path=export_as.getRootPath()+"grjlmb.docx";
		DataInputStream in = new DataInputStream(new FileInputStream(path));
		DataOutputStream out = new DataOutputStream(new FileOutputStream(name));
		byte[] buffer = new byte[2048];
		int offset = 0;
		while ((offset = in.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, offset);
		}
		in.close();
		out.close();
	}
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("peopleGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update publicity set sortid = ? where a0000=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String a0000 = m.get("a0000");
				ps.setInt(1, i);
				ps.setString(2, a0000);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("peopleGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
