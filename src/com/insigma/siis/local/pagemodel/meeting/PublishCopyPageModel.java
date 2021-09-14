package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class PublishCopyPageModel extends PageModel{
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String publishid = this.getPageElement("publishid").getValue();
		try {
			if(publishid!=null&&!"".equals(publishid)){
				HBSession sess = HBUtil.getHBSession();
				Publish p = (Publish)sess.get(Publish.class, publishid);
				PMPropertyCopyUtil.copyObjValueToElement(p, this);
			}
			String userid = SysManagerUtils.getUserId();
			String sql = "select meetingid,meetingname from "
					+ "(select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where userid='"+userid+"'"
					+ " union "
					+ "  select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where "
					+ "		meetingid in (select b.meetingid from publish b,publishuser c where b.publishid=c.publishid and c.userid='"+userid+"' and c.ischange='1')"
					+ "	) t order by time desc";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("meetingid").toString();
				mapCode.put(tp0116, listCode.get(i).get("meetingname").toString());
			}
			((Combo)this.getPageElement("xmeetingname")).setValueListForSelect(mapCode);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
//		String sql="select a.agendaname,b.meetingname from publish a,meetingtheme b where a.meetingid=b.meetingid and a.publishid='"+publishid+"'";
//		CommQuery cqbs=new CommQuery();
//		try {
//			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
//			this.getPageElement("agendaname").setValue(list.get(0).get("agendaname").toString());
//			this.getPageElement("meetingname").setValue(list.get(0).get("meetingname").toString());
//		} catch (AppException e) {
//			e.printStackTrace();
//		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private static String getPath() {
        String upload_file = AppConfig.HZB_PATH + "/";
        try {
            File file = new File(upload_file);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //解压路径
        return upload_file;
    }
	
	public final static String disk = getPath();
	
	/**
	 * 保存
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String publishid = this.getPageElement("publishid").getValue();
		String xmeetingname = this.getPageElement("xmeetingname").getValue();
		String xagendaname = this.getPageElement("xagendaname").getValue();
		String userid = SysManagerUtils.getUserId();
		String uuid_p=UUID.randomUUID().toString().replaceAll("-", "");
		//议题复制（标题复制，人员引用）
		try {
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			String sql="insert into publish (publishid,meetingid,agendatype,agendaname,userid,sort) select '"+uuid_p+"','"+xmeetingname+"',agendatype,'"+xagendaname+"','"+userid+"',sort from publish where publishid='"+publishid+"'";
			stmt.executeUpdate(sql);
			
			CommQuery cqbs=new CommQuery();
			//第一层
			sql="select * from hz_sh_title where publishid='"+publishid+"' and title04='-1'";
			List<HashMap<String, Object>> list_t1=cqbs.getListBySQL(sql);
			if(list_t1!=null&&list_t1.size()>0) {
				for(HashMap<String, Object> map_t1:list_t1) {
					String uuid_t1=UUID.randomUUID().toString().replaceAll("-", "");
					sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
							+ " select '"+uuid_t1+"',title01,title02,title03,title04,sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t1.get("titleid")+"'";
					stmt.executeUpdate(sql);
					if(map_t1.get("title06")!=null&&!"".equals(map_t1.get("title06"))) {
						String oldPath=disk +map_t1.get("title05").toString()+map_t1.get("title06").toString();
						String newPath=disk +isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p)+map_t1.get("title06").toString();
						File f = new File(disk +isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p));
						if(!f.isDirectory()){
							f.mkdirs();
						}
						int bytesum = 0; 
			            int byteread = 0; 
			            File oldfile = new File(oldPath); 
			            if (oldfile.exists()) { //文件存在时 
			               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
			               FileOutputStream fs = new FileOutputStream(newPath); 
			               byte[] buffer = new byte[1024*2]; 
			               int length; 
			               while ( (byteread = inStream.read(buffer)) != -1) { 
			                   bytesum += byteread; //字节数 文件大小 
			                   System.out.println(bytesum); 
			                   fs.write(buffer, 0, byteread); 
			               } 
			               inStream.close(); 
			            } 
				     } 
					
					//第一层人员
					sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
							+ " select '"+map_t1.get("titleid")+"','"+uuid_t1+"',sh000,'"+publishid+"','"+uuid_p+"',a0000,sh001 from hz_sh_a01 where titleid='"+map_t1.get("titleid")+"'";
					stmt.executeUpdate(sql);
					
					sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
							+ " select titleid_old,'"+uuid_t1+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t1.get("titleid")+"'";
					stmt.executeUpdate(sql);
					
					//第二层标题
					sql="select * from hz_sh_title where  publishid='"+publishid+"' and title04='"+map_t1.get("titleid").toString()+"'";
					List<HashMap<String, Object>> list_t2=cqbs.getListBySQL(sql);
					if(list_t2!=null&&list_t2.size()>0) {
						for(HashMap<String, Object> map_t2:list_t2) {
							String uuid_t2=UUID.randomUUID().toString().replaceAll("-", "");
							sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
									+ " select '"+uuid_t2+"',title01,title02,title03,'"+uuid_t1+"',sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t2.get("title05")).replace(map_t2.get("titleid").toString(), uuid_t2).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t2.get("titleid")+"'";
							stmt.executeUpdate(sql);
							//第二层人员
							sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
									+ " select '"+map_t2.get("titleid")+"','"+uuid_t2+"',sh000,'"+publishid+"','"+uuid_p+"',a0000,sh001 from hz_sh_a01 where titleid='"+map_t2.get("titleid")+"'";
							stmt.executeUpdate(sql);
							
							sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
									+ " select titleid_old,'"+uuid_t2+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t2.get("titleid")+"'";
							stmt.executeUpdate(sql);
								
							
							//第三层
							sql="select * from hz_sh_title where  publishid='"+publishid+"' and title04='"+map_t2.get("titleid").toString()+"'";
							List<HashMap<String, Object>> list_t3=cqbs.getListBySQL(sql);
							if(list_t3!=null&&list_t3.size()>0) {
								for(HashMap<String, Object> map_t3:list_t3) {
									String uuid_t3=UUID.randomUUID().toString().replaceAll("-", "");
									sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
											+ " select '"+uuid_t3+"',title01,title02,title03,'"+uuid_t2+"',sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t3.get("title05")).replace(map_t3.get("titleid").toString(), uuid_t3).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t3.get("titleid")+"'";
									stmt.executeUpdate(sql);
									
									//第三层人员
									sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
											+ " select '"+map_t3.get("titleid")+"','"+uuid_t3+"',sh000,'"+publishid+"','"+uuid_p+"',a0000,sh001 from hz_sh_a01 where titleid='"+map_t3.get("titleid")+"'";
									stmt.executeUpdate(sql);
									
									sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
											+ " select titleid_old,'"+uuid_t3+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t3.get("titleid")+"'";
									stmt.executeUpdate(sql);
											
								}
							}
						}
					}
				}
			}
			stmt.close();
			
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"复制议题","publish",user.getId(),xagendaname, new ArrayList());
			
			this.getExecuteSG().addExecuteCode("saveCallBack();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
//		议题复制（标题、人员复制）
//		try {
//			HBSession sess = HBUtil.getHBSession();
//			Statement stmt = sess.connection().createStatement();
//			String sql="insert into publish (publishid,meetingid,agendatype,agendaname,userid) select '"+uuid_p+"','"+xmeetingname+"',agendatype,'"+xagendaname+"','"+userid+"' from publish where publishid='"+publishid+"'";
//			stmt.executeUpdate(sql);
//			
//			CommQuery cqbs=new CommQuery();
//			//第一层
//			sql="select * from hz_sh_title where publishid='"+publishid+"' and title04='-1'";
//			List<HashMap<String, Object>> list_t1=cqbs.getListBySQL(sql);
//			if(list_t1!=null&&list_t1.size()>0) {
//				for(HashMap<String, Object> map_t1:list_t1) {
//					String uuid_t1=UUID.randomUUID().toString().replaceAll("-", "");
//					sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
//							+ " select '"+uuid_t1+"',title01,title02,title03,title04,sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t1.get("titleid")+"'";
//					stmt.executeUpdate(sql);
//					if(map_t1.get("title06")!=null&&!"".equals(map_t1.get("title06"))) {
//						String oldPath=disk +map_t1.get("title05").toString()+map_t1.get("title06").toString();
//						String newPath=disk +isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p)+map_t1.get("title06").toString();
//						File f = new File(disk +isnull(map_t1.get("title05")).replace(map_t1.get("titleid").toString(), uuid_t1).replace(publishid, uuid_p));
//						if(!f.isDirectory()){
//							f.mkdirs();
//						}
//						int bytesum = 0; 
//			            int byteread = 0; 
//			            File oldfile = new File(oldPath); 
//			            if (oldfile.exists()) { //文件存在时 
//			               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
//			               FileOutputStream fs = new FileOutputStream(newPath); 
//			               byte[] buffer = new byte[1024*2]; 
//			               int length; 
//			               while ( (byteread = inStream.read(buffer)) != -1) { 
//			                   bytesum += byteread; //字节数 文件大小 
//			                   System.out.println(bytesum); 
//			                   fs.write(buffer, 0, byteread); 
//			               } 
//			               inStream.close(); 
//			            } 
//				     } 
//					
//					//第一层人员
//					sql="select * from hz_sh_a01 where publishid='"+publishid+"' and titleid='"+map_t1.get("titleid").toString()+"'";
//					List<HashMap<String, Object>> list_p1=cqbs.getListBySQL(sql);
//					if(list_p1!=null&&list_p1.size()>0) {
//						for(HashMap<String, Object> map_p1:list_p1) {
//							String uuid_p1=UUID.randomUUID().toString().replaceAll("-", "");
//							sql="insert into hz_sh_a01 (sh000,a0000,publishid,sh001,titleid,tp0111,tp0112,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a) "
//									+ " select '"+uuid_p1+"',a0000,'"+uuid_p+"',sh001,'"+uuid_t1+"',tp0111,tp0112,tp0113,tp0114,'"+isnull(map_p1.get("tp0115")).replace(publishid, uuid_p)+"',tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a from hz_sh_a01 where sh000='"+map_p1.get("sh000")+"'";
//							stmt.executeUpdate(sql);
//							
//							sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
//									+ " select titleid_old,'"+uuid_t1+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t1.get("titleid")+"'";
//							stmt.executeUpdate(sql);
//							
//							if(map_p1.get("tp0118")!=null&&!"".equals(map_p1.get("tp0118"))) {
//								String oldPath=disk +map_p1.get("tp0115").toString()+map_p1.get("tp0118").toString();
//								String newPath=disk +isnull(map_p1.get("tp0115")).replace(publishid, uuid_p)+map_p1.get("tp0118").toString();
//								File f = new File(disk +isnull(map_p1.get("tp0115")).replace(publishid, uuid_p));
//								if(!f.isDirectory()){
//									f.mkdirs();
//								}
//								int bytesum = 0; 
//					            int byteread = 0; 
//					            File oldfile = new File(oldPath); 
//					            if (oldfile.exists()) { //文件存在时 
//					               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
//					               FileOutputStream fs = new FileOutputStream(newPath); 
//					               byte[] buffer = new byte[1024*2]; 
//					               int length; 
//					               while ( (byteread = inStream.read(buffer)) != -1) { 
//					                   bytesum += byteread; //字节数 文件大小 
//					                   System.out.println(bytesum); 
//					                   fs.write(buffer, 0, byteread); 
//					               } 
//					               inStream.close(); 
//					            } 
//						     } 
//						}
//					}
//					
//					//第二层标题
//					sql="select * from hz_sh_title where  publishid='"+publishid+"' and title04='"+map_t1.get("titleid").toString()+"'";
//					List<HashMap<String, Object>> list_t2=cqbs.getListBySQL(sql);
//					if(list_t2!=null&&list_t2.size()>0) {
//						for(HashMap<String, Object> map_t2:list_t2) {
//							String uuid_t2=UUID.randomUUID().toString().replaceAll("-", "");
//							sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
//									+ " select '"+uuid_t2+"',title01,title02,title03,'"+uuid_t1+"',sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t2.get("title05")).replace(map_t2.get("titleid").toString(), uuid_t2).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t2.get("titleid")+"'";
//							stmt.executeUpdate(sql);
//							//第二层人员
//							sql="select * from hz_sh_a01 where publishid='"+publishid+"' and titleid='"+map_t2.get("titleid").toString()+"'";
//							List<HashMap<String, Object>> list_p2=cqbs.getListBySQL(sql);
//							if(list_p2!=null&&list_p2.size()>0) {
//								for(HashMap<String, Object> map_p2:list_p2) {
//									String uuid_p2=UUID.randomUUID().toString().replaceAll("-", "");
//									sql="insert into hz_sh_a01 (sh000,a0000,publishid,sh001,titleid,tp0111,tp0112,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a) "
//											+ " select '"+uuid_p2+"',a0000,'"+uuid_p+"',sh001,'"+uuid_t2+"',tp0111,tp0112,tp0113,tp0114,'"+isnull(map_p2.get("tp0115")).replace(publishid, uuid_p)+"',tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a from hz_sh_a01 where sh000='"+map_p2.get("sh000")+"'";
//									stmt.executeUpdate(sql);
//									
//									sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
//											+ " select titleid_old,'"+uuid_t2+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t2.get("titleid")+"'";
//									stmt.executeUpdate(sql);
//									
//									if(map_p2.get("tp0118")!=null&&!"".equals(map_p2.get("tp0118"))) {
//										String oldPath=disk +map_p2.get("tp0115").toString()+map_p2.get("tp0118").toString();
//										String newPath=disk +isnull(map_p2.get("tp0115")).replace(publishid, uuid_p)+map_p2.get("tp0118").toString();
//										File f = new File(disk +isnull(map_p2.get("tp0115")).replace(publishid, uuid_p));
//										if(!f.isDirectory()){
//											f.mkdirs();
//										}
//										int bytesum = 0; 
//							            int byteread = 0; 
//							            File oldfile = new File(oldPath); 
//							            if (oldfile.exists()) { //文件存在时 
//							               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
//							               FileOutputStream fs = new FileOutputStream(newPath); 
//							               byte[] buffer = new byte[1024*2]; 
//							               int length; 
//							               while ( (byteread = inStream.read(buffer)) != -1) { 
//							                   bytesum += byteread; //字节数 文件大小 
//							                   System.out.println(bytesum); 
//							                   fs.write(buffer, 0, byteread); 
//							               } 
//							               inStream.close(); 
//							            } 
//								     }
//								}
//							}
//							
//							//第三层
//							sql="select * from hz_sh_title where  publishid='"+publishid+"' and title04='"+map_t2.get("titleid").toString()+"'";
//							List<HashMap<String, Object>> list_t3=cqbs.getListBySQL(sql);
//							if(list_t3!=null&&list_t3.size()>0) {
//								for(HashMap<String, Object> map_t3:list_t3) {
//									String uuid_t3=UUID.randomUUID().toString().replaceAll("-", "");
//									sql="insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) "
//											+ " select '"+uuid_t3+"',title01,title02,title03,'"+uuid_t2+"',sortid,'"+xmeetingname+"','"+uuid_p+"','"+isnull(map_t3.get("title05")).replace(map_t3.get("titleid").toString(), uuid_t3).replace(publishid, uuid_p)+"',title06,title07 from hz_sh_title where titleid='"+map_t3.get("titleid")+"'";
//									stmt.executeUpdate(sql);
//									
//									//第三层人员
//									sql="select * from hz_sh_a01 where publishid='"+publishid+"' and titleid='"+map_t3.get("titleid").toString()+"'";
//									List<HashMap<String, Object>> list_p3=cqbs.getListBySQL(sql);
//									if(list_p3!=null&&list_p3.size()>0) {
//										for(HashMap<String, Object> map_p3:list_p3) {
//											String uuid_p3=UUID.randomUUID().toString().replaceAll("-", "");
//											sql="insert into hz_sh_a01 (sh000,a0000,publishid,sh001,titleid,tp0111,tp0112,tp0113,tp0114,tp0115,tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a) "
//													+ " select '"+uuid_p3+"',a0000,'"+uuid_p+"',sh001,'"+uuid_t3+"',tp0111,tp0112,tp0113,tp0114,'"+isnull(map_p3.get("tp0115")).replace(publishid, uuid_p)+"',tp0116,tp0117,tp0118,tp0119,tp0121,tp0122,a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a from hz_sh_a01 where sh000='"+map_p3.get("sh000")+"'";
//											stmt.executeUpdate(sql);
//											
//											sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001) "
//													+ " select titleid_old,'"+uuid_t3+"',sh000,publishid_old,'"+uuid_p+"',a0000,sh001  from personcite where titleid_new='"+map_t3.get("titleid")+"'";
//											stmt.executeUpdate(sql);
//											
//											if(map_p3.get("tp0118")!=null&&!"".equals(map_p3.get("tp0118"))) {
//												String oldPath=disk +map_p3.get("tp0115").toString()+map_p3.get("tp0118").toString();
//												String newPath=disk +isnull(map_p3.get("tp0115")).replace(publishid, uuid_p)+map_p3.get("tp0118").toString();
//												File f = new File(disk +isnull(map_p3.get("tp0115")).replace(publishid, uuid_p));
//												if(!f.isDirectory()){
//													f.mkdirs();
//												}
//												int bytesum = 0; 
//									            int byteread = 0; 
//									            File oldfile = new File(oldPath); 
//									            if (oldfile.exists()) { //文件存在时 
//									               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
//									               FileOutputStream fs = new FileOutputStream(newPath); 
//									               byte[] buffer = new byte[1024*2]; 
//									               int length; 
//									               while ( (byteread = inStream.read(buffer)) != -1) { 
//									                   bytesum += byteread; //字节数 文件大小 
//									                   System.out.println(bytesum); 
//									                   fs.write(buffer, 0, byteread); 
//									               } 
//									               inStream.close(); 
//									            } 
//										     }
//										}
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//			stmt.close();
//			this.getExecuteSG().addExecuteCode("saveCallBack();");
//		} catch (Exception e) {
//			e.printStackTrace();
//			this.setMainMessage("保存失败");
//		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	public String isnull (Object obj) {
		String str="";
		if(obj==null||"".equals(obj)) {
			
		}else {
			str=obj.toString();
		}
		return str;	
	}
	
}
