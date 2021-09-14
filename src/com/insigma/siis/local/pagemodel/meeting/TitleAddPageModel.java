package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.JUpload.JUpload;

import org.apache.commons.fileupload.FileItem;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.MeetingTheme;
import com.insigma.siis.local.business.entity.Publish;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
    
public class TitleAddPageModel extends PageModel implements JUpload{
	CommQuery cqbs=new CommQuery();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public final static String disk = getPath();
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String titleid = this.getPageElement("titleid").getValue();
		String type = this.getPageElement("type").getValue();
		String publishid = this.getPageElement("publishid").getValue();
		try {
			if("update".equals(type)){
				CommQuery cqbs=new CommQuery();
				String sql="select * from hz_sh_title where titleid='"+titleid+"' ";
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				this.getPageElement("titlename").setValue(list.get(0).get("title01")==null?"":list.get(0).get("title01").toString());
				this.getPageElement("titleType").setValue(list.get(0).get("title02")==null?"":list.get(0).get("title02").toString());
				this.getPageElement("titleType").setDisabled(true);
				this.getPageElement("titleBz").setValue(list.get(0).get("title03")==null?"":list.get(0).get("title03").toString());
				this.getPageElement("sortid").setValue(list.get(0).get("sortid")==null?"":list.get(0).get("sortid").toString());
				//this.getPageElement("title05").setValue(list.get(0).get("title05")==null?"":list.get(0).get("title05").toString());
				//this.getPageElement("title06").setValue(list.get(0).get("title06")==null?"":list.get(0).get("title06").toString());
				//this.getPageElement("title07").setValue(list.get(0).get("title07")==null?"":list.get(0).get("title07").toString());
				
				
				/*if(list.get(0).get("title06")!=null) {
					HashMap<String, Object> map=list.get(0);
					path=disk +map.get("title05").toString()+map.get("title06").toString();
					path=path.replaceAll("\\\\", "/");
				}
				this.getPageElement("titlePath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
				Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
				map.put("titlefile", new ArrayList<Map<String, String>>());
				if (list != null&&list.get(0).get("title06")!=null) {
					for (HashMap<String, Object> ta : list) {
						List<Map<String, String>> list_temp;
						Map<String, String> map_temp = new HashMap<String, String>();
						String type2 ="titlefile";
						if(map.containsKey(type2)){
							list_temp = map.get(type2);
						}else{
							list_temp = new ArrayList<Map<String, String>>();
						}
						map_temp.put("id", ta.get("titleid")+"");
						map_temp.put("name", ta.get("title06")+"");
						map_temp.put("fileSize", ta.get("title07")+"");
						list_temp.add(map_temp);
						map.put(type2, list_temp);
					}
					//遍历map
					for(Map.Entry<String, List<Map<String, String>>> entry : map.entrySet()){
						this.setFilesInfo(entry.getKey(), entry.getValue(), false);
					}
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		//this.getPageElement("publishid").setValue(publishid);
		this.setNextEventName("fileGrid.dogridquery");  
		//this.getExecuteSG().addExecuteCode("gg()");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("fileGrid.dogridquery")
	public int galeGridQuery(int start, int limit) throws RadowException {
		String add00 = this.getPageElement("titleid").getValue();
		String sql = " select p.add00,p.filename,p.filesize,t.aaa005||'/'||p.fileurl as fileurl from WJGLADD p , aa01 t where  p.add00 = '" + add00 + "' and t.aaa001 = 'HZB_PATH'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 保存
	 */         
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		CommQuery cqbs=new CommQuery();
		String titlename = this.getPageElement("titlename").getValue().trim();
		if(null==titlename||"".equals(titlename)){
			this.setMainMessage("请填写标题名称");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String titleBz = this.getPageElement("titleBz").getValue();
		String title_type = this.getPageElement("titleType").getValue();
		if(null==title_type||"".equals(title_type)){
			this.setMainMessage("请填写标题类型");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String titleid = this.getPageElement("titleid").getValue();
		String type = this.getPageElement("type").getValue();
		
		
		
		try {
			String meetingid = this.getPageElement("meetingid").getValue();
			String publishid = this.getPageElement("publishid").getValue();
			
			String sqlg="select a.* from WJGLADD a where a.add00='"+titleid+"'  ";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sqlg);
			String path="";
			String title05="";
			String title06="";
			String title07="";
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				title05= map.get("fileurl")==null?"":map.get("fileurl").toString();
				title06 = map.get("filename")==null?"":map.get("filename").toString();
				title07 = map.get("filesize")==null?"":map.get("filesize").toString();
			}
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			String sql="";
			
			String sortid = this.getPageElement("sortid").getValue();
			int num=0;
			if("".equals(sortid)||sortid==null) {
				num=Integer.valueOf(getMax_sort(publishid))+1;
			}else {
				num=Integer.valueOf(sortid);
			}
			String logname="";
			if("add".equals(type)){//新增
				if("".equals(titleid)||titleid==null) {
					titleid="-1";
				}else {
					if("1".equals(title_type)) {
						titleid="-1";
					}else if("2".equals(title_type)){
						sql="select title04,title02 from hz_sh_title where titleid='"+titleid+"' ";
						List<HashMap<String, Object>> list_t1=cqbs.getListBySQL(sql);
						if("1".equals(list_t1.get(0).get("title02"))) {
							
						}else if("2".equals(list_t1.get(0).get("title02"))) {
							titleid=list_t1.get(0).get("title04").toString();
						}else if("3".equals(list_t1.get(0).get("title02"))) {
							sql="select title04,title02 from hz_sh_title where titleid='"+list_t1.get(0).get("title04")+"' ";
							List<HashMap<String, Object>> list_t2=cqbs.getListBySQL(sql);
							if("1".equals(list_t2.get(0).get("title02"))) {
								titleid=list_t1.get(0).get("title04").toString();
							}else if("2".equals(list_t2.get(0).get("title02"))){
								titleid=list_t2.get(0).get("title04").toString();
							}
						}
					}else if("3".equals(title_type)) {
						sql="select title04,title02 from hz_sh_title where titleid='"+titleid+"' ";
						List<HashMap<String, Object>> list_t1=cqbs.getListBySQL(sql);
						if("1".equals(list_t1.get(0).get("title02"))||"2".equals(list_t1.get(0).get("title02"))) {
							
						}else if("3".equals(list_t1.get(0).get("title02"))) {
							titleid=list_t1.get(0).get("title04").toString();
						}
					}
				}
				List list_sort = sess.createSQLQuery("select * from hz_sh_title where sortid='"+sortid+"' and publishid='"+publishid+"' and title04='"+titleid+"'"  ).list();
				if(list_sort.size()!=0){
					sql="update hz_sh_title set sortid=sortid+1 where to_number(sortid)>="+sortid+" and publishid='"+publishid+"' and title04='"+titleid+"'";
					stmt.executeUpdate(sql);
				}
				
				 sql = "insert into hz_sh_title (titleid,title01,title02,title03,title04,sortid,meetingid,publishid,title05,title06,title07) values "
						+ " (sys_guid(),'"+titlename+"','"+title_type+"','"+titleBz+"','"+titleid+"','"+num+"','"+meetingid+"','"+publishid+"','"+title05+"','"+title06+"','"+title07+"') ";
				
				//this.getExecuteSG().addExecuteCode("saveCallBack();");
				//this.setMainMessage("新增成功");
				 logname="新增标题";
			}else{
				List list_sort = sess.createSQLQuery("select * from hz_sh_title where sortid='"+sortid+"' and publishid='"+publishid+"' and title04=(select title04 from hz_sh_title where titleid='"+titleid+"') and titleid<>'"+titleid+"'"  ).list();
				if(list_sort.size()!=0){
					sql="update hz_sh_title set sortid=sortid+1 where to_number(sortid)>="+sortid+" and publishid='"+publishid+"' and title04=(select title04 from hz_sh_title where titleid='"+titleid+"')";
					stmt.executeUpdate(sql);
				}
				sql="update hz_sh_title set title01='"+titlename+"',title02='"+title_type+"',title03='"+titleBz+"',sortid='"+num+"',title05='"+title05+"' ,title06='"+title06+"' ,title07='"+title07+"'  where titleid='"+titleid+"' ";
				logname="修改标题";
			}
			stmt.executeUpdate(sql);
			stmt.close();
			this.upLoadFile("titlefile");
			this.setMainMessage("保存成功");
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),logname,"hz_sh_title",user.getId(),titlename, new ArrayList());
			this.setNextEventName("galeGrid.dogridquery");  
			this.getExecuteSG().addExecuteCode("saveCallBack();parent.queryPerson();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String publishid){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sortid),0) from hz_sh_title where publishid='"+publishid+"'").uniqueResult().toString();
		return sort;
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
	
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// 获得文件名称
		String isfile = formDataMap.get("Filename");
		// 判断是否上传了附件，没有上传则不进行文件处理
		if (isfile != null && !isfile.equals("")) {
			try {
				// 获取表单信息
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// 如果文件大于1M则显示M，小于则显示kb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}
	
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		String publishid = formDataMap.get("publishid");
		String titleid = formDataMap.get("titleid");
		String filename = formDataMap.get("Filename");
		//String id = UUID.randomUUID().toString();
		String id=titleid;
		try {
			String directory ="shfiles/"+publishid+ File.separator +titleid+ File.separator;
			String filePath = directory  + filename;
			File f = new File(disk + directory);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			HBSession sess = null;
			sess = HBUtil.getHBSession();
			String sql="update hz_sh_title set title05='"+directory+"',title06='"+filename+"',title07='"+fileSize+"' where publishid='"+publishid+"' and titleid='"+titleid+"' ";
			System.out.println(sql);
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			fi.write(new File(disk + filePath));
			String path=disk + filePath;
			path=path.replaceAll("\\\\", "/");;
			this.getPageElement("titlePath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
		}catch (Exception e) {
			throw new RadowException("上传附件失败！");
		}
		return id;
	}
	/**
	 * 附件上传之后保存附件信息
	 * @param params
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws SQLException 
	 */
	@PageEvent("file")
	public int fileGrid(String oid) throws RadowException, AppException, SQLException {
		this.getPageElement("pid").setValue(oid);
		//this.setMainMessage("附件上传成功！");
		String sql="";
	try {
		HBSession sess = HBUtil.getHBSession();
		if(oid!=null||!"".equals(oid)) {	
			String sqlg="select a.* from WJGLADD a where a.add00='"+oid+"'  ";
			List<HashMap<String, Object>> list;
				list = cqbs.getListBySQL(sqlg);
				HashMap<String, Object> map=list.get(0);
				String tp0115 = map.get("fileurl")==null?"":map.get("fileurl").toString();
				String tp0118 = map.get("filename")==null?"":map.get("filename").toString();
				String tp0119 = map.get("filesize")==null?"":map.get("filesize").toString();
			if(list.size()!=0||list.size()!=0){
				tp0115=tp0115.replaceAll("\\\\", "/");
				sql="update hz_sh_title set title05='"+tp0115+"',title06='"+tp0118+"',title07='"+tp0119+"' where titleid='"+oid+"'  ";
				//System.out.println(sql);
					//更新数据
					//st.execute(sql1);
				sess.createSQLQuery(sql).executeUpdate();
				sess.flush();
				}
				
		}
			} catch (Exception e) {
			} finally {
			}
			
				//this.setNextEventName("fileGrid.dogridquery");
				this.setMainMessage("附件上传成功！");
				//this.getExecuteSG().addExecuteCode("fg()");
				this.getExecuteSG().addExecuteCode("gg()");
				return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 删除数据并删除文件
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFi")
	public int deleteFi(String index) throws RadowException, AppException {

	try {
		HBSession sess = HBUtil.getHBSession();
		Grid grid = (Grid)this.getPageElement("fileGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String add00 = map.get("add00").toString();
		String filename = map.get("filename").toString();
		String allPath = getAllPath(filename);
		// 删除文件
		if (allPath != null && !"".equals(allPath)) {
			File file = new File(allPath); 
		    if(file.exists()){     //判断是否存在  
		    	file.delete();      //删除  
		    }
		}
		String sql1 = " delete from WJGLADD p where p.filename = '" + filename + "' ";
		//this.setMainMessage("附件删除成功！");
		//更新数据
		//st.execute(sql1);
		sess.createSQLQuery(sql1).executeUpdate();
		
		List<Object[]> list1 = sess.createSQLQuery("select titleid,title05,title06 from hz_sh_title a where titleid='"+add00+"' ").list();
		if(list1!=null || list1.size()!=0||(list1.size()>0)&&list1.get(0)[2]!=null){
			sess.createSQLQuery("update hz_sh_title a set title05='',title06='',title07=''  where titleid='"+add00+"'  ").executeUpdate();
		}
		sess.flush();
		
	} catch (Exception e) {
	} finally {
	}
		this.setMainMessage("附件删除成功！");
		//this.setNextEventName("galeGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("gg()");
		//String fujianInfo = fujianList( pid );
		//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 获取文件全路径
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public String getAllPath(String filename) throws AppException {
		String allPath = "";
		// 数据是否存在
		// String sql = " select p.pid, p.p3002, p.p3001 from P30 p where p.pid = '" + pid + "' ";
		String sql = " select p.add00, p.fileurl, p.filename from WJGLADD p where p.filename = '" + filename + "' ";
		CommQuery cq = new CommQuery();
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if (list == null || list.size() == 0) {
			return allPath;
		}
		
		String filepath = list.get(0).get("fileurl") == null ? "" : list.get(0).get("fileurl").toString();
		//String filename = list.get(0).get("filename") == null ? "" : list.get(0).get("filename").toString();
		// 删除文件
		if (!"".equals(filepath) ) {
			allPath =  filepath;
			
		}
		return allPath;
	}
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select titleid,title05,title06 from hz_sh_title a where titleid='"+id+"' ").list();
			if(list==null || list.size()==0||(list.size()>0)&&list.get(0)[2]==null){
				return null;//删除失败
			}
			Object[] arr = list.get(0);
			String directory = disk+arr[1];
			File f = new File(directory+arr[2]);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("update hz_sh_title a set title05='',title06='',title07=''  where titleid='"+id+"'  ").executeUpdate();
			sess.flush();
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
}
