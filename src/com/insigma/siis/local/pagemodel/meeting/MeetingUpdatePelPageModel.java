package com.insigma.siis.local.pagemodel.meeting;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;

import net.sf.json.JSONArray;

public class MeetingUpdatePelPageModel  extends PageModel implements JUpload{

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("queryPerson")
	public int queryPerson(String sh000) {
		try {
			String publishid=this.getPageElement("publish_id").getValue();
			String titleid=this.getPageElement("title_id").getValue();
			String sql="select a.* from hz_sh_a01 a where a.sh000='"+sh000+"'  "
					+ " and a.publishid='"+publishid+"' and a.titleid='"+titleid+"' ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			String path="";
			if(list!=null&&list.size()>0){
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("a0101").setValue(map.get("a0101")==null?"":map.get("a0101").toString());
				this.getPageElement("a0104").setValue(map.get("a0104")==null?"":map.get("a0104").toString());
				this.getPageElement("a0107").setValue(map.get("a0107")==null?"":map.get("a0107").toString());
				this.getPageElement("a0141").setValue(map.get("a0141")==null?"":map.get("a0141").toString());
				this.getPageElement("zgxl").setValue(map.get("zgxl")==null?"":map.get("zgxl").toString());
				this.getPageElement("zgxw").setValue(map.get("zgxw")==null?"":map.get("zgxw").toString());
				this.getPageElement("a0192a").setValue(map.get("a0192a")==null?"":map.get("a0192a").toString());
				this.getPageElement("tp0111").setValue(map.get("tp0111")==null?"":map.get("tp0111").toString());
				this.getPageElement("tp0112").setValue(map.get("tp0112")==null?"":map.get("tp0112").toString());
				this.getPageElement("tp0113").setValue(map.get("tp0113")==null?"":map.get("tp0113").toString());
				this.getPageElement("tp0114").setValue(map.get("tp0114")==null?"":map.get("tp0114").toString());
				this.getPageElement("tp0116").setValue(map.get("tp0116")==null?"1":map.get("tp0116").toString());
				this.getPageElement("tp0117").setValue(map.get("tp0117")==null?"1":map.get("tp0117").toString());
				this.getPageElement("tp0121").setValue(map.get("tp0121")==null?"":map.get("tp0121").toString());
				this.getPageElement("tp0122").setValue(map.get("tp0122")==null?"":map.get("tp0122").toString());
				this.getPageElement("tp0123").setValue(map.get("tp0123")==null?"":map.get("tp0123").toString());
				this.getPageElement("tp0124").setValue(map.get("tp0124")==null?"":map.get("tp0124").toString());
				this.getPageElement("tp0125").setValue(map.get("tp0125")==null?"":map.get("tp0125").toString());
				this.getPageElement("sh001").setValue(map.get("sh001")==null?"":map.get("sh001").toString());
//				if(map.get("tp0118")!=null) {
//					path=disk +map.get("tp0115").toString()+map.get("tp0118").toString();
//					path=path.replaceAll("\\\\", "/");
//				}
//				this.getPageElement("personPath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
//				
//				Map<String, List<Map<String, String>>> mapfile = new HashMap<String, List<Map<String, String>>>();
//				mapfile.put("personfile", new ArrayList<Map<String, String>>());
//				if (list != null&&list.get(0).get("tp0118")!=null) {
//					for (HashMap<String, Object> ta : list) {
//						List<Map<String, String>> list_temp;
//						Map<String, String> map_temp = new HashMap<String, String>();
//						String type ="personfile";
//						if(mapfile.containsKey(type)){
//							list_temp = mapfile.get(type);
//						}else{
//							list_temp = new ArrayList<Map<String, String>>();
//						}
//						map_temp.put("id", ta.get("sh000")+"");
//						map_temp.put("name", ta.get("tp0118")+"");
//						map_temp.put("fileSize", ta.get("tp0119")+"");
//						list_temp.add(map_temp);
//						mapfile.put(type, list_temp);
//					}
//					//遍历map
//					for(Map.Entry<String, List<Map<String, String>>> entry : mapfile.entrySet()){
//						this.setFilesInfo(entry.getKey(), entry.getValue(), false);
//					}
//				}
			}else {
				this.getPageElement("tp0117").setValue("0");
				this.getPageElement("tp0117").setDisabled(true);
				sh000=UUID.randomUUID().toString();
				this.getPageElement("sh000").setValue(sh000);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 展示所有附件数据
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fileGrid.dogridquery")
	public int fileGridQuery(int start, int limit) throws RadowException {
		String pid = this.getPageElement("sh000").getValue();
		String sql = " select * from WJGLADD p where p.add00 = '" + pid + "'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 附件上传之后保存附件信息
	 * @param params
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("file")
	public int fileGrid(String oid) throws RadowException {
		this.setMainMessage("附件上传成功！");
		this.setNextEventName("fileGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("personSave")
	public int personSave() throws RadowException {
		String publishid=this.getPageElement("publish_id").getValue();
		String titleid=this.getPageElement("title_id").getValue();
		String titlename=this.getPageElement("titlename").getValue();
		String sh000=this.getPageElement("sh000").getValue();
		String tp0111=this.getPageElement("tp0111").getValue();
		String tp0112=this.getPageElement("tp0112").getValue();
		String tp0113=this.getPageElement("tp0113").getValue();
		String tp0114=this.getPageElement("tp0114").getValue();
		String tp0116=this.getPageElement("tp0116").getValue();
		String tp0117=this.getPageElement("tp0117").getValue();
		String tp0121=this.getPageElement("tp0121").getValue();
		String tp0122=this.getPageElement("tp0122").getValue();
		String tp0123=this.getPageElement("tp0123").getValue();
		String tp0124=this.getPageElement("tp0124").getValue();
		String tp0125=this.getPageElement("tp0125").getValue();
		String a0101=this.getPageElement("a0101").getValue();
		String a0104=this.getPageElement("a0104").getValue();
		String a0107=this.getPageElement("a0107").getValue();
		String a0141=this.getPageElement("a0141").getValue();
		String zgxl=this.getPageElement("zgxl").getValue();
		String zgxw=this.getPageElement("zgxw").getValue();
		String a0192a=this.getPageElement("a0192a").getValue();
		String sh001=this.getPageElement("sh001").getValue();
		String p_type=this.getPageElement("p_type").getValue();
		int maxid=Integer.valueOf(getTitMax_sort(titleid,sh000));
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="";
			
			if(sh001==null||"".equals(sh001)) {
				sh001=maxid+1+"";
			}else {
				List list_sort = sess.createSQLQuery("select * from hz_sh_a01 where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid='"+titleid+"'"  ).list();
				List list_sort2 = sess.createSQLQuery("select * from personcite where sh000<>'"+sh000+"' and sh001='"+sh001+"' and titleid_new='"+titleid+"'"  ).list();
				if(list_sort.size()!=0||list_sort2.size()!=0){
					sql="update hz_sh_a01 set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
					
					sql="update personcite set sh001=sh001+1 where to_number(sh001)>="+sh001+" and titleid_new='"+titleid+"' ";
					sess.createSQLQuery(sql).executeUpdate();
				}
			}
			String logname="";
			//手工新增录入
			if("1".equals(p_type)) {
				this.getPageElement("p_type").setValue("2");
				String sh0=UUID.randomUUID().toString().replaceAll("-", "");
				sql="insert into hz_sh_a01 (sh000,publishid,titleid,tp0111,tp0112,tp0113,tp0114,tp0116,tp0117,tp0121,tp0122,tp0123,tp0124,tp0125"
						+ ",a0101,a0104,a0107,a0141,zgxl,zgxw,a0192a,sh001) "
						+ "	  values "
						+ " ('"+sh0+"','"+publishid+"','"+titleid+"','"+tp0111+"','"+tp0112+"','"+tp0113+"','"+tp0114+"','"+tp0116+"'"
						+ "		,'"+tp0117+"','"+tp0121+"','"+tp0122+"','"+tp0123+"','"+tp0124+"','"+tp0125+"','"+a0101+"','"+a0104+"','"+a0107+"','"+a0141+"','"+zgxl+"','"+zgxw+"','"+a0192a+"','"+sh001+"')";
				logname="新增上会人员（手工录入）";
			}else {//修改信息
				sql="update hz_sh_a01 set tp0111='"+tp0111+"',tp0112='"+tp0112+"',tp0113='"+tp0113+"',tp0114='"+tp0114+"'"
					+ ",tp0116='"+tp0116+"',tp0117='"+tp0117+"',tp0121='"+tp0121+"',tp0122='"+tp0122+"',tp0123='"+tp0123+"',tp0124='"+tp0124+"',tp0125='"+tp0125+"',a0101='"+a0101+"',a0104='"+a0104+"'"
					+ ",a0107='"+a0107+"',a0141='"+a0141+"',zgxl='"+zgxl+"',zgxw='"+zgxw+"',a0192a='"+a0192a+"',sh001='"+sh001+"'   "
					+ "		where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
				logname="修改上会人员";
			}
			sess.createSQLQuery(sql).executeUpdate();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),logname,"hz_sh_a01",user.getId(),titlename, new ArrayList());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.upLoadFile("personfile");
		this.getExecuteSG().addExecuteCode("alert('保存成功');parent.queryPerson();");
		return EventRtnType.NORMAL_SUCCESS;
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
	
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String sh000 = formDataMap.get("sh000");
		String publishid = formDataMap.get("publish_id");
		String titleid = formDataMap.get("title_id");
		String filename = formDataMap.get("Filename");
		//String id = UUID.randomUUID().toString();
		String id="";
		try {
			String sql="select a.* from hz_sh_a01 a where a.sh000='"+sh000+"' "
					+ " and a.publishid='"+publishid+"' and a.titleid='"+titleid+"' ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				HashMap<String, Object> map=list.get(0);
				id=map.get("sh000").toString();
			}
			String directory ="shfiles/"+publishid+ File.separator +id+ File.separator;
			String filePath = directory  + filename;
			File f = new File(disk + directory);
			if(!f.isDirectory()){
				f.mkdirs();
			}
			HBSession sess = null;
			sess = HBUtil.getHBSession();
			sql="update hz_sh_a01 set tp0115='"+directory+"',tp0118='"+filename+"',tp0119='"+fileSize+"' where sh000='"+sh000+"' and publishid='"+publishid+"' and titleid='"+titleid+"' ";
			System.out.println(sql);
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			fi.write(new File(disk + filePath));
			String path=disk + filePath;
			path=path.replaceAll("\\\\", "/");;
			this.getPageElement("personPath").setValue(URLEncoder.encode(URLEncoder.encode(path, "utf-8"), "utf-8"));
		}catch (Exception e) {
			throw new RadowException("上传附件失败！");
		}
		return id;
	}

	@Override
	public String deleteFile(String id) {

		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> list = sess.createSQLQuery("select sh000,tp0115,tp0118 from hz_sh_a01 a where sh000='"+id+"' ").list();
			if(list==null || list.size()==0||(list.size()>0)&&list.get(0)[2]==null){
				return null;//删除失败
			}
			Object[] arr = list.get(0);
			String directory = disk+arr[1];
			File f = new File(directory+arr[2]);
			if(f.isFile()){
				f.delete();
			}
			sess.createSQLQuery("update hz_sh_a01 a set tp0115='',tp0118='',tp0119=''  where sh000='"+id+"'  ").executeUpdate();
			sess.flush();
			return id;
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getTitMax_sort(String titleid,String sh000){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(sh001),0) from (select sh001,sh000 from hz_sh_a01 where titleid='"+titleid+"' union select b.sh001,b.sh000 from personcite b where  b.titleid_new='"+titleid+"') where sh000<>'"+sh000+"'" ).uniqueResult().toString();
		return sort;
	}
	
}
