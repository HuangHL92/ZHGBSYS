package com.insigma.siis.local.pagemodel.gbwh;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.GbkhAtt;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.PublishAtt;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class KHPJPageModel extends PageModel implements JUpload {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		String a0000=this.getPageElement("a0000").getValue();
//		this.getExecuteSG().addExecuteCode("alert('"+a0000+"')");
		this.setNextEventName("gbkhGrid.dogridquery");
		this.setNextEventName("grid1.dogridquery");
		return 0;
	}
	//干部考核
	@PageEvent("gbkhGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int gbkhSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		//String meetingid = this.getPageElement("meetingid").getValue();
		String a0000=this.getPageElement("a0000").getValue();

		
		String sql = "select p.gbkhid,p.year,p.grade, t.pat00s pat00s,t.pat04s pat04s from gbkh p left join (select gbkhid,LISTAGG(to_char(pat04), ',') WITHIN GROUP(ORDER BY sort) pat04s,LISTAGG(to_char(pat00), ',') WITHIN GROUP(ORDER BY sort) pat00s \r\n" + 
				"from gbkh_att  group by gbkhid) t on p.gbkhid = t.gbkhid where p.a0000 = '"+a0000+"'  group by p.gbkhid,p.year,p.sort,p.grade, t.pat00s,t.pat04s order by p.year desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("gbkhsort")
	public int gbkhsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("gbkhGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update gbkh set sort = ? where gbkhid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String gbkhid = m.get("gbkhid");
				ps.setInt(1, i);
				ps.setString(2, gbkhid);
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
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
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
				String id = saveFile(formDataMap, fi, fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return map;
	}

	@Override
	public String deleteFile(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// 获得人员信息id
		String gbkhid = formDataMap.get("gbkh_id");
		String filename = formDataMap.get("Filename");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		GbkhAtt pa=new GbkhAtt();
//		PublishAtt pa = new PublishAtt();
		pa.setGbkhid(gbkhid);
//		pa.setPat05(SysManagerUtils.getUserId());
//		pa.setPat06(sdf.format(new Date()));
		pa.setPat04(filename);
		pa.setPat00(UUID.randomUUID().toString().replaceAll("-", ""));

		String directory = "khpj" + File.separator +"gbkh" + File.separator + gbkhid + File.separator;
		String filePath = directory + pa.getPat04();
		File f = new File(disk + directory);

		if (!f.isDirectory()) {
			f.mkdirs();
		}
		fi.write(new File(disk + filePath));
		pa.setPat07(disk + filePath);
//		pa.setPat08(fileSize);
		pa.setSort(Integer.valueOf(getMax_sort(gbkhid)) + 1);
		HBUtil.getHBSession().save(pa);
		HBUtil.getHBSession().flush();

		return pa.getPat00();
	}

	/**
	 * 获取最大的排序号
	 * 
	 * @return
	 */
	public String getMax_sort(String gbkhid) {
		HBSession session = HBUtil.getHBSession();
		String sort = session
				.createSQLQuery("select nvl(max(sort),0) from gbkh_att where gbkhid='" + gbkhid + "'")
				.uniqueResult().toString();
		return sort;
	}

	public static String disk = JSGLBS.HZBPATH;

	@PageEvent("infoDelete.onclick")
	@Transaction
	public int delete() throws RadowException {
		String gbkhid = this.getPageElement("gbkh_id").getValue();
		if (gbkhid == null || "".equals(gbkhid)) {
			this.setMainMessage("请选择一行数据！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession session = HBUtil.getHBSession();
		List<String> address = session
				.createSQLQuery(
						"select pat07 from gbkh_att where gbkhid = '" + gbkhid + "' order by sort desc")
				.list();
		session.createSQLQuery("delete from gbkh where gbkhid = '" + gbkhid + "'").executeUpdate();
		session.createSQLQuery("delete from gbkh_att where gbkhid = '" + gbkhid + "'").executeUpdate();
		if (address.size()!= 0) {
			String directory =address.get(0).toString();
			int one = directory.lastIndexOf(File.separator);
			directory = directory.substring(0, one);
			deleteDirectory(directory);
		}

		this.setMainMessage("删除成功！");
		this.setNextEventName("gbkhGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("document.getElementById('gbkh_id').value=''");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	
	
	
	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile2(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists()) {//判断文件是否存在  
			 if (file.isFile()) {//判断是否是文件  
				 file.delete();//删除文件  
			 } else if (file.isDirectory()) {//否则如果它是一个目录  
				 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				 for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
					 String name=files[i].getName();
				      deleteFile2(name);//把每个文件用这个方法进行迭代  
				      } 
				 file.delete();//删除文件夹  
			 }
			 return true;
		} else {  
		     System.out.println("所删除的文件不存在");  
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile2(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}

	@PageEvent("sort")
	@Transaction
	public int changeSort(String param) {
		String pat00 = param.split("@")[0];
		String type = param.split("@")[1];
		HBSession session = HBUtil.getHBSession();
		if (type.equals("1")) {// 上移
			GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			if (pa.getSort() == 1) {
				this.setMainMessage("已在最顶端！");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				String gbkhid = pa.getGbkhid();
				int sort_before = pa.getSort() - 1;
				session.createSQLQuery("update gbkh_att set sort=" + pa.getSort() + " where gbkhid='" + gbkhid
						+ "' and sort=" + sort_before).executeUpdate();
				pa.setSort(sort_before);
				session.flush();
			}
		} else {// 下移
			GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			String max_sort = getMax_sort(pa.getGbkhid());
			if (pa.getSort() == Integer.valueOf(max_sort)) {
				this.setMainMessage("已在最底端！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String gbkhid = pa.getGbkhid();
			int sort_next = pa.getSort() + 1;
			session.createSQLQuery("update Gbkh_att set sort=" + pa.getSort() + " where gbkhid='" + gbkhid
					+ "' and sort=" + sort_next).executeUpdate();
			pa.setSort(sort_next);
			session.flush();
		}
		this.setNextEventName("gbkhGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public static void downFile(HttpServletRequest request, HttpServletResponse response) {
		HBSession session = HBUtil.getHBSession();
		String pat00 = request.getParameter("pat00");
		GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//		PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
		String filename = pa.getPat04();
		String path = pa.getPat07();

		try {
			response.reset();
			response.setHeader("pragma", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String(filename.getBytes("GBK"), "ISO8859-1"));
			// 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            /*创建输出流*/
            ServletOutputStream servletOS = response.getOutputStream();
            servletOS.write(buffer);
            servletOS.flush();
            servletOS.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@PageEvent("deleteAtt")
	@Transaction
	public int deleteAtt(String pat00){
		HBSession session = HBUtil.getHBSession();
		GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//		PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
		session.createSQLQuery("delete from gbkh_att where pat00='"+pat00+"'").executeUpdate();
		//删除文件
		String path = pa.getPat07();
		deleteFile2(path);
		this.setNextEventName("gbkhGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}


	//干部考察
	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String a0000=this.getPageElement("a0000").getValue();				
				

		String sql = "select a.id,a.a0000,a.checktime,a.checkfile,b.filename,b.fileurl from gbkc a left join gbkcfile b on a.checkfile = b.id where a0000='"+a0000+"' order by a.checktime desc";
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("查询sql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	
	//增加
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String a0000=this.getPageElement("a0000").getValue();							
		String ctxPath = request.getContextPath();
		
		this.getExecuteSG().addExecuteCode("$h.openWin('GBKCAdd','pages.gbwh.GBKCAdd','新建考察页面',600,300,'"+a0000+"','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//修改
	@PageEvent("update")
	public int update() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		String id="";
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				id=(String)map.get("id");
			}
		}				
		if(num>1){
			this.setMainMessage("仅能选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(num<1){
			this.setMainMessage("请选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a0000=this.getPageElement("a0000").getValue();			
//		String orgId = this.getPageElement("subWinIdBussessId").getValue();	
		String groupid = id+"@@@"+a0000;
		System.out.println("+++++++++++++="+groupid);
		String ctxPath = request.getContextPath();
		//this.setRadow_parent_data(groupid);
		this.getExecuteSG().addExecuteCode("$h.openWin('GBKCAdd','pages.gbwh.GBKCAdd','修改考察页面',600,300,'"+groupid+"','"+ctxPath+"');");
		
		//this.openWindow("Check", "pages.sysorg.org.Check");
								
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteBtn")
	public int delete3() throws RadowException, Exception {
		Grid grid = (Grid)this.getPageElement("grid1");

		List<HashMap<String, Object>> list=grid.getValueList();
				
		int num=0;
		//String groupid="";
		StringBuffer groupid = new StringBuffer();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map=list.get(i);
			String checked=map.get("personcheck")+"";
			if("true".equals(checked)){
				num=num+1;
				//groupid=groupid+",'"+(String)map.get("id")+"'";
				groupid.append("'").append(map.get("id") == null ? "" : map.get("id").toString()).append("',");// 被勾选的人员编号组装，用“，”分隔
				//groupid=(String)map.get("id");
			}
		}				
		if(num<1){
			this.setMainMessage("请选择一条纪录!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('系统提示','您确定要删除吗？'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");
		//this.addNextEvent(NextEventValue.YES, "doDelete", groupid);
		//this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
		
		
		//this.setMessageType(EventMessageType.CONFIRM); // 消息框类型(confirm类型窗口)
		//this.setMainMessage("确定要删除吗？");
		//this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deletesure")
	public int deleteconfirm() throws AppException, RadowException {
		CommQuery query = new CommQuery();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> listfile = new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map = new HashMap<String,Object>();
		HashMap<String,Object> mapfile = new HashMap<String,Object>();
		String ids = this.getPageElement("ids").getValue();	
		String checkfile = null;
		String fileurl = null;
		String sql = "select checkfile from Gbkc where id in (" +ids+ ")";
		list = query.getListBySQL(sql);
		for (int i = 0; i < list.size(); i++) {
			map = list.get(i);
			checkfile=map.get("checkfile").toString();
			
			String sqlfile = "select fileurl from GBkcfile where id = '"+checkfile+"'";	
			listfile = query.getListBySQL(sqlfile);
			for (int j = 0; j < listfile.size(); j++) {
				mapfile = listfile.get(j);
				fileurl = mapfile.get("fileurl").toString();
				File file = new File(fileurl);
				String parentPath = file.getParent();
				File parenfile = new File(parentPath);
				deleteFile(parenfile);
			}
			
			
			HBUtil.executeUpdate("delete from Gbkcfile where id = '"+checkfile+"'");			
		}
		
		HBUtil.executeUpdate("delete from Gbkc where id in (" +ids+ ")");
						
		this.setMainMessage("删除成功！");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private void deleteFile(File file) {  
		if (file.exists()) {//判断文件是否存在  
			 if (file.isFile()) {//判断是否是文件  
				 file.delete();//删除文件  
			 } else if (file.isDirectory()) {//否则如果它是一个目录  
				 File[] files = file.listFiles();//声明目录下所有的文件 files[];  
				 for (int i = 0;i < files.length;i ++) {//遍历目录下所有的文件  
				       this.deleteFile(files[i]);//把每个文件用这个方法进行迭代  
				      } 
				 file.delete();//删除文件夹  
			 }
		} else {  
		     System.out.println("所删除的文件不存在");  
			
		}
	}
}
