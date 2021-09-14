package com.insigma.siis.local.pagemodel.gzdb;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CadresWordPageModel extends PageModel {
	CommQuery cqbs=new CommQuery();
	private String initalPath = (AppConfig.HZB_PATH + "/CadreRelated/document").replace("/", System.getProperty("file.separator"));

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		this.setNextEventName("fileGrid1.dogridquery");
		this.setNextEventName("fileGrid2.dogridquery");
		this.setNextEventName("fileGrid3.dogridquery");
		this.getExecuteSG().addExecuteCode("gg();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * չʾ���и�������
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fileGrid1.dogridquery")
	public int fileGridQuery1(int start, int limit) throws RadowException {
		String dhxjid=this.getPageElement("subWinIdBussessId").getValue();
		this.getPageElement("dhxjid").setValue(dhxjid);
		String sql = "select t.dhxjid,t.filename,b.aaa005 || t.FILEURL FILEURL from dhxj t,aa01 b where 1=1 and b.aaa001='HZB_PATH'  and  dhxjid='"+dhxjid+"' ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * չʾ���и�������
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fileGrid2.dogridquery")
	public int fileGridQuery2(int start, int limit) throws RadowException {
		String dhxjid=this.getPageElement("subWinIdBussessId").getValue();
		this.getPageElement("dhxjid").setValue(dhxjid);
		String sql = "select t.dhxjid,t.jfilename,b.aaa005 || t.JFILEURL JFILEURL from dhxj t,aa01 b where 1=1 and b.aaa001='HZB_PATH'  and  dhxjid='"+dhxjid+"' ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * չʾ���и�������
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fileGrid3.dogridquery")
	public int fileGridQuery3(int start, int limit) throws RadowException {
		String dhxjid=this.getPageElement("subWinIdBussessId").getValue();
		this.getPageElement("dhxjid").setValue(dhxjid);
		String sql = "select t.dhxjid,t.hfilename,b.aaa005 || t.HFILEURL HFILEURL from dhxj t,aa01 b where 1=1 and b.aaa001='HZB_PATH'  and  dhxjid='"+dhxjid+"' ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * �����ϴ�֮�󱣴渽����Ϣ
	 * @param params
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws SQLException 
	 */
	@PageEvent("file")
	public int fileGrid(String oid) throws RadowException, AppException, SQLException {
		//this.setMainMessage("�����ϴ��ɹ���");
		String tjlx = this.getPageElement("pid").getValue();
		String xjqy = this.getPageElement("xjqy").getValue();
		//String xjqy = (String)this.getPageElement("fileGrid").getValueList().get(this.getPageElement("fileGrid").getCueRowIndex()).get("xjqy");
		//String tjlx = this.getPageElement("tjlx").getValue();
		//String xjqy = this.getPageElement("xjqy").getValue();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
        String userid = user.getId(); //�û�id
		String sql="";
	try {
		
		HBSession sess = HBUtil.getHBSession();
		if(oid!=null||!"".equals(oid)) {	
			String sqlg="select a.* from TJLXADD a where a.add00='"+oid+"'  ";
			List<HashMap<String, Object>> list;
				list = cqbs.getListBySQL(sqlg);
				HashMap<String, Object> map=list.get(0);
				String fileurl = map.get("fileurl")==null?"":map.get("fileurl").toString();
				String filename = map.get("filename")==null?"":map.get("filename").toString();
				String filesize = map.get("filesize")==null?"":map.get("filesize").toString();
			if(list.size()!=0||list.size()!=0){
				fileurl=fileurl.replaceAll("\\\\", "/");
				HBUtil.executeUpdate("insert into gi(giid,tjlx,xjqy,fileurl,filename,filesize,userid) "
						+ "values(?,?,?,?,?,?,?)",new Object[]{oid,tjlx,xjqy,fileurl,filename,filesize,userid});
				//sql="update gi set fileurl='"+fileurl+"',filename='"+filename+"',filesize='"+filesize+"',giid='"+oid+"'  where giid='"+oid+"'  ";
				//System.out.println(sql);
					//��������
					//st.execute(sql1);
				//sess.createSQLQuery(sql).executeUpdate();
				
				}
				
		}
			} catch (Exception e) {
			} finally {
			}
			
				//this.setNextEventName("fileGrid.dogridquery");
				this.setMainMessage("�����ϴ��ɹ���");
				//this.getExecuteSG().addExecuteCode("fg()");
				this.getExecuteSG().addExecuteCode("gg();");
				//this.setNextEventName("galeGrid.dogridquery");  
				//this.getExecuteSG().addExecuteCode("gg()");
				return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("downloadFile")
	public int downloadFile(String index) throws RadowException, AppException {
		Grid grid = (Grid)this.getPageElement("fileGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String filename = map.get("filename").toString();
		String allPath = getAllPath(filename);
		if ("".equals(allPath)) {
			this.setNextEventName("fileGrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		// allPath = allPath.replace(System.getProperty("file.separator"), "/");
		this.getExecuteSG().addExecuteCode("downloadFile('" + allPath.replace(System.getProperty("file.separator"), "/") + "')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ɾ�����ݲ�ɾ���ļ�
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFile1")
	public int deleteFile1(String index) throws RadowException, AppException {

		try {
			HBSession sess = HBUtil.getHBSession();
			Grid grid = (Grid)this.getPageElement("fileGrid1");
			List<HashMap<String, Object>> list = grid.getValueList();
			int cueindex=Integer.parseInt(index);
			HashMap<String, Object> map = list.get(cueindex);
			String dhxjid = map.get("dhxjid").toString();
			// ɾ���ļ�
			String sql1 = " update dhxj set filename='',fileurl='',filesize='' where dhxjid = '" + dhxjid + "' ";
			//this.setMainMessage("����ɾ���ɹ���");
			//��������
			//st.execute(sql1);
			sess.createSQLQuery(sql1).executeUpdate();
			sess.flush();
			
		} catch (Exception e) {
		} finally {
		}
			this.setMainMessage("����ɾ���ɹ���");
			//this.setNextEventName("galeGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("gg1()");
			//String fujianInfo = fujianList( pid );
			//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	/**
	 * ɾ�����ݲ�ɾ���ļ�
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFile2")
	public int deleteFile2(String index) throws RadowException, AppException {

		try {
			HBSession sess = HBUtil.getHBSession();
			Grid grid = (Grid)this.getPageElement("fileGrid2");
			List<HashMap<String, Object>> list = grid.getValueList();
			int cueindex=Integer.parseInt(index);
			HashMap<String, Object> map = list.get(cueindex);
			String dhxjid = map.get("dhxjid").toString();
			String sql1 = " update dhxj set jfilename='',jfileurl='',jfilesize='' where dhxjid = '" + dhxjid + "' ";
			//this.setMainMessage("����ɾ���ɹ���");
			//��������
			//st.execute(sql1);
			sess.createSQLQuery(sql1).executeUpdate();
			sess.flush();
			
		} catch (Exception e) {
		} finally {
		}
			this.setMainMessage("����ɾ���ɹ���");
			//this.setNextEventName("galeGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("gg2()");
			//String fujianInfo = fujianList( pid );
			//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	/**
	 * ɾ�����ݲ�ɾ���ļ�
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFile3")
	public int deleteFile3(String index) throws RadowException, AppException {

		try {
			HBSession sess = HBUtil.getHBSession();
			Grid grid = (Grid)this.getPageElement("fileGrid3");
			List<HashMap<String, Object>> list = grid.getValueList();
			int cueindex=Integer.parseInt(index);
			HashMap<String, Object> map = list.get(cueindex);
			String dhxjid = map.get("dhxjid").toString();
			String sql1 = " update dhxj set hfilename='',hfileurl='',hfilesize='' where dhxjid = '" + dhxjid + "' ";
			//this.setMainMessage("����ɾ���ɹ���");
			//��������
			//st.execute(sql1);
			sess.createSQLQuery(sql1).executeUpdate();
			sess.flush();
			
		} catch (Exception e) {
		} finally {
		}
			this.setMainMessage("����ɾ���ɹ���");
			//this.setNextEventName("galeGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("gg3()");
			//String fujianInfo = fujianList( pid );
			//this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
			return EventRtnType.NORMAL_SUCCESS;
		}

	/**
	 * ��ȡ�ļ�ȫ·��
	 * @param id
	 * @return
	 * @throws AppException
	 */
	public String getAllPath(String filename) throws AppException {
		String allPath = "";
		// �����Ƿ����
		// String sql = " select p.pid, p.p3002, p.p3001 from P30 p where p.pid = '" + pid + "' ";
		String sql = " select p.add00, p.fileurl, p.filename from TJLXADD p where p.filename = '" + filename + "' ";
		CommQuery cq = new CommQuery();
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		if (list == null || list.size() == 0) {
			return allPath;
		}
		
		String filepath = list.get(0).get("fileurl") == null ? "" : list.get(0).get("fileurl").toString();
		//String filename = list.get(0).get("filename") == null ? "" : list.get(0).get("filename").toString();
		// ɾ���ļ�
		if (!"".equals(filepath) ) {
			allPath =  filepath;
			
		}
		return allPath;
	}
	
   // ��ȡ������Ϣ
	public String fujianList(String pid ) throws AppException {
		String Info = "";
		CommQuery cqbs=new CommQuery();
		String sql = "select p.add00, p.fileurl, p.filename from TJLXADD p where p.add00 ='"+pid+"'";
		List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
		int i =0;
		if(list.size()>0)
		{
		   for(HashMap<String, Object> map : list){
			   i++;
			   String add00 = map.get("add00").toString();//����ID
			   String filename = map.get("filename").toString();//��������
			   String allPath = getAllPath(filename);
			  // allPath ="javascript:downloadFile('" + allPath.replace(System.getProperty("file.separator"), "/") + "')";
			   allPath ="javascript:downloadFile(\"" + allPath.replace(System.getProperty("file.separator"), "/") + "\")";
			   allPath = String.valueOf(i)+"��<a href="+ allPath +">"+ filename + "</a><br>";
			   Info += allPath;
		   }
		}
		return Info;
	}
}
