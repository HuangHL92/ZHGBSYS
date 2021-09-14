package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CadresPlanningAllocationAppendixPageModel extends PageModel {
	private String initalPath = (AppConfig.HZB_PATH + "/CadreRelated/document").replace("/", System.getProperty("file.separator"));
	CommQuery cqbs=new CommQuery();
	HBSession sess = null;

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		this.setNextEventName("fileGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �����ϴ�֮�󱣴渽����Ϣ
	 * @param params
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveAppendix.onclick")
	@Transaction
	public int saveAppendix_onclick(String params) throws RadowException, AppException {
		String[] param = params.split("&TTTT&");
		String filepath = param[0];
		String pid = param[1];
		//System.out.println(filepath + ",,,," + pid);
		String fileName = filepath.substring(filepath.lastIndexOf(System.getProperty("file.separator")) + 1);
		String partialPath = filepath.replace(initalPath, "").replace(fileName, "");
		String sql = " insert into WJGLADD values ( '" + pid + "', '', '" + UUID.randomUUID().toString().replace("-", "") + "', "
				+ "'" + fileName + "', '" + partialPath + "', '', '', '' ) ";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("�����ϴ��ɹ���");
		this.setNextEventName("fileGrid.dogridquery");
		String fujianInfo = fujianList( pid );
		this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * չʾ���и�������
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("fileGrid.dogridquery")
	public int fileGridQuery(int start, int limit) throws RadowException {
		String pid = this.getPageElement("pid").getValue();
		String sql = " select * from WJGLADD p where p.add00 = '" + pid + "'";
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
		this.getPageElement("pid").setValue(oid);
		//this.setMainMessage("�����ϴ��ɹ���");
		String sql="";
	try {
		sess = HBUtil.getHBSession();
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
				sql="update hz_sh_a01 set tp0115='"+tp0115+"',tp0118='"+tp0118+"',tp0119='"+tp0119+"' where sh000='"+oid+"'  ";
				//System.out.println(sql);
					//��������
					//st.execute(sql1);
				sess.createSQLQuery(sql).executeUpdate();
				
				}
				
		}
			} catch (Exception e) {
			} finally {
			}
			
				//this.setNextEventName("fileGrid.dogridquery");
				this.setMainMessage("�����ϴ��ɹ���");
				this.getExecuteSG().addExecuteCode("fg()");
				this.getExecuteSG().addExecuteCode("gg()");
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
	 * @throws SQLException 
	 */
	@PageEvent("deleteFile")
	public int deleteFile(String index) throws RadowException, AppException, SQLException {
		
	try {
		sess = HBUtil.getHBSession();
		Grid grid = (Grid)this.getPageElement("fileGrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		int cueindex=Integer.parseInt(index);
		HashMap<String, Object> map = list.get(cueindex);
		String filename = map.get("filename").toString();
		String add00 = map.get("add00").toString();
		String allPath = getAllPath(filename);
		// ɾ���ļ�
		if (allPath != null && !"".equals(allPath)) {
			File file = new File(allPath); 
		    if(file.exists()){     //�ж��Ƿ����  
		    	file.delete();      //ɾ��  
		    }
		}
		String sql1 = " delete from WJGLADD  where add00 = '" + add00 + "' ";
		String sql2 = " update hz_sh_a01 set tp0115='',tp0118='',tp0119='' where sh000='"+add00+"'    ";
		//this.setMainMessage("����ɾ���ɹ���");
		//��������
		//st.execute(sql1);
		sess.createSQLQuery(sql1).executeUpdate();
		sess.createSQLQuery(sql2).executeUpdate();
		
		List<Object[]> list1 = sess.createSQLQuery("select titleid,title05,title06 from hz_sh_title a where titleid='"+add00+"' ").list();
		if(list1!=null || list1.size()!=0||(list1.size()>0)&&list1.get(0)[2]!=null){
			sess.createSQLQuery("update hz_sh_title a set title05='',title06='',title07=''  where titleid='"+add00+"'  ").executeUpdate();
		}
		sess.flush();
		
	} catch (Exception e) {
	} finally {
	}
		
		
		//this.setNextEventName("fileGrid.dogridquery");
		this.setMainMessage("����ɾ���ɹ�");
		this.getExecuteSG().addExecuteCode("fg()");
		this.getExecuteSG().addExecuteCode("gg()");
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
		String sql = " select p.add00, p.fileurl, p.filename from WJGLADD p where p.filename = '" + filename + "' ";
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
	public String fujianList1(String pid ) throws AppException {
		String Info = "";
		CommQuery cqbs=new CommQuery();
		String sql = "select p.add00, p.fileurl, p.filename from WJGLADD p where p.add00 ='"+pid+"'";
		List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
		int i =0;
		if(list.size()>0)
		{
		   for(HashMap<String, Object> map : list){
			   i++;
			   String add00 = map.get("add00").toString();//����ID
			   String filename = map.get("filename").toString();//��������
			   String allPath = getAllPath(filename).replace(System.getProperty("file.separator"), "/");
			  // allPath ="javascript:downloadFile('" + allPath.replace(System.getProperty("file.separator"), "/") + "')";
			   allPath ="javascript:downloadFile('"+allPath+"')";
			   allPath = String.valueOf(i)+"��<a href="+ allPath +">"+ filename + "</a><br>";
			   Info += allPath;
		   }
		}
		return Info;
	}
	// ��ȡ������Ϣ
		public String fujianList(String pid ) throws AppException {
			String Info = "";
			CommQuery cqbs=new CommQuery();
			String sql = "select p.add00, p.fileurl, p.filename from WJGLADD p where p.add00 ='"+pid+"'";
			List<HashMap<String, Object>> list = cqbs.getListBySQL(sql);
			int i =0;
			if(list.size()>0)
			{
			   for(HashMap<String, Object> map : list){
				   i++;
				   String add00 = map.get("add00").toString();//����ID
				   String filename = map.get("filename").toString();//��������
				   String allPath = getAllPath(filename).replace(System.getProperty("file.separator"), "/");
				  
				   Info = filename;
			   }
			}
			return Info;
		}
	
}
