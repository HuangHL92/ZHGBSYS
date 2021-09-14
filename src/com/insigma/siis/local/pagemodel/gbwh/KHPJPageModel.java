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
	//�ɲ�����
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
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.toastmessage("�����ѱ��棡");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				// ��ȡ����Ϣ
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// ����ļ�����1M����ʾM��С������ʾkb
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
		// �����Ա��Ϣid
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
	 * ��ȡ���������
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
			this.setMainMessage("��ѡ��һ�����ݣ�");
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

		this.setMainMessage("ɾ���ɹ���");
		this.setNextEventName("gbkhGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("document.getElementById('gbkh_id').value=''");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	
	
	
	/**
	 * ɾ�������ļ�
	 *
	 * @param fileName
	 *            Ҫɾ�����ļ����ļ���
	 * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteFile2(String fileName) {
		File file = new File(fileName);
		// ����ļ�·������Ӧ���ļ����ڣ�������һ���ļ�����ֱ��ɾ��
		if (file.exists()) {//�ж��ļ��Ƿ����  
			 if (file.isFile()) {//�ж��Ƿ����ļ�  
				 file.delete();//ɾ���ļ�  
			 } else if (file.isDirectory()) {//�����������һ��Ŀ¼  
				 File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
				 for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
					 String name=files[i].getName();
				      deleteFile2(name);//��ÿ���ļ�������������е���  
				      } 
				 file.delete();//ɾ���ļ���  
			 }
			 return true;
		} else {  
		     System.out.println("��ɾ�����ļ�������");  
			return false;
		}
	}

	/**
	 * ɾ��Ŀ¼��Ŀ¼�µ��ļ�
	 *
	 * @param dir
	 *            Ҫɾ����Ŀ¼���ļ�·��
	 * @return Ŀ¼ɾ���ɹ�����true�����򷵻�false
	 */
	public static boolean deleteDirectory(String dir) {
		// ���dir�����ļ��ָ�����β���Զ�����ļ��ָ���
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// ���dir��Ӧ���ļ������ڣ����߲���һ��Ŀ¼�����˳�
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�" + dir + "�����ڣ�");
			return false;
		}
		boolean flag = true;
		// ɾ���ļ����е������ļ�������Ŀ¼
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// ɾ�����ļ�
			if (files[i].isFile()) {
				flag = deleteFile2(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// ɾ����Ŀ¼
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("ɾ��Ŀ¼ʧ�ܣ�");
			return false;
		}
		// ɾ����ǰĿ¼
		if (dirFile.delete()) {
			System.out.println("ɾ��Ŀ¼" + dir + "�ɹ���");
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
		if (type.equals("1")) {// ����
			GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			if (pa.getSort() == 1) {
				this.setMainMessage("������ˣ�");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				String gbkhid = pa.getGbkhid();
				int sort_before = pa.getSort() - 1;
				session.createSQLQuery("update gbkh_att set sort=" + pa.getSort() + " where gbkhid='" + gbkhid
						+ "' and sort=" + sort_before).executeUpdate();
				pa.setSort(sort_before);
				session.flush();
			}
		} else {// ����
			GbkhAtt pa=(GbkhAtt)session.get(GbkhAtt.class, pat00);
//			PublishAtt pa = (PublishAtt) session.get(PublishAtt.class, pat00);
			String max_sort = getMax_sort(pa.getGbkhid());
			if (pa.getSort() == Integer.valueOf(max_sort)) {
				this.setMainMessage("������׶ˣ�");
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
			// ��������ʽ�����ļ���
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            /*���������*/
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
		//ɾ���ļ�
		String path = pa.getPat07();
		deleteFile2(path);
		this.setNextEventName("gbkhGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}


	//�ɲ�����
	@PageEvent("grid1.dogridquery")
	public int dogridquery(int start,int limit) throws Exception{
		String a0000=this.getPageElement("a0000").getValue();				
				

		String sql = "select a.id,a.a0000,a.checktime,a.checkfile,b.filename,b.fileurl from gbkc a left join gbkcfile b on a.checkfile = b.id where a0000='"+a0000+"' order by a.checktime desc";
		this.pageQuery(sql, "SQL", start, limit);
        System.out.println("��ѯsql"+sql);
    	return EventRtnType.SPE_SUCCESS;
			
	}
	
	//����
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		String a0000=this.getPageElement("a0000").getValue();							
		String ctxPath = request.getContextPath();
		
		this.getExecuteSG().addExecuteCode("$h.openWin('GBKCAdd','pages.gbwh.GBKCAdd','�½�����ҳ��',600,300,'"+a0000+"','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//�޸�
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
			this.setMainMessage("����ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a0000=this.getPageElement("a0000").getValue();			
//		String orgId = this.getPageElement("subWinIdBussessId").getValue();	
		String groupid = id+"@@@"+a0000;
		System.out.println("+++++++++++++="+groupid);
		String ctxPath = request.getContextPath();
		//this.setRadow_parent_data(groupid);
		this.getExecuteSG().addExecuteCode("$h.openWin('GBKCAdd','pages.gbwh.GBKCAdd','�޸Ŀ���ҳ��',600,300,'"+groupid+"','"+ctxPath+"');");
		
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
				groupid.append("'").append(map.get("id") == null ? "" : map.get("id").toString()).append("',");// ����ѡ����Ա�����װ���á������ָ�
				//groupid=(String)map.get("id");
			}
		}				
		if(num<1){
			this.setMainMessage("��ѡ��һ����¼!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("ids").setValue(groupid.toString().substring(0, groupid.length()-1));	
		this.getExecuteSG().addExecuteCode("Ext.Msg.confirm('ϵͳ��ʾ','��ȷ��Ҫɾ����'," 
				+ "function(id) { if('yes'==id){radow.doEvent('deletesure','');}else{return;}});");
		//this.addNextEvent(NextEventValue.YES, "doDelete", groupid);
		//this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
		
		
		//this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ������(confirm���ʹ���)
		//this.setMainMessage("ȷ��Ҫɾ����");
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
						
		this.setMainMessage("ɾ���ɹ���");
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private void deleteFile(File file) {  
		if (file.exists()) {//�ж��ļ��Ƿ����  
			 if (file.isFile()) {//�ж��Ƿ����ļ�  
				 file.delete();//ɾ���ļ�  
			 } else if (file.isDirectory()) {//�����������һ��Ŀ¼  
				 File[] files = file.listFiles();//����Ŀ¼�����е��ļ� files[];  
				 for (int i = 0;i < files.length;i ++) {//����Ŀ¼�����е��ļ�  
				       this.deleteFile(files[i]);//��ÿ���ļ�������������е���  
				      } 
				 file.delete();//ɾ���ļ���  
			 }
		} else {  
		     System.out.println("��ɾ�����ļ�������");  
			
		}
	}
}
