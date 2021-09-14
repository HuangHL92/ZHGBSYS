package com.insigma.siis.local.pagemodel.meeting;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class HjxjPlanningPageModel extends PageModel {
	CommQuery cqbs=new CommQuery();
	private String initalPath = (AppConfig.HZB_PATH + "/CadreRelated/document").replace("/", System.getProperty("file.separator"));

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
	 * 附件上传之后保存附件信息
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
		String sql = " insert into HJXJADD values ( '" + pid + "', '', '" + UUID.randomUUID().toString().replace("-", "") + "', "
				+ "'" + fileName + "', '" + partialPath + "', '', '', '' ) ";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("附件上传成功！");
		this.setNextEventName("fileGrid.dogridquery");
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
		String zbid = this.getPageElement("subWinIdBussessId2").getValue();
		String sql = " select * from HJXJADD where add00='"+zbid+"'";
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
	@PageEvent("personClear")
	public int personClear() throws RadowException {
		this.getPageElement("sh000").setValue("");
		this.getPageElement("tp0111").setValue("");
		this.getPageElement("tp0112").setValue("");
		this.getPageElement("tp0113").setValue("");
		this.getPageElement("tp0114").setValue("");
		this.getPageElement("tp0116").setValue("");
		this.getPageElement("tp0117").setValue("");
		this.getPageElement("tp0121").setValue("");
		this.getPageElement("tp0122").setValue("");
		this.getPageElement("a0101").setValue("");
		this.getPageElement("a0104").setValue("");
		this.getPageElement("a0107").setValue("");
		this.getPageElement("a0141").setValue("");
		this.getPageElement("zgxl").setValue("");
		this.getPageElement("zgxw").setValue("");
		this.getPageElement("a0192a").setValue("");
		this.getPageElement("sh001").setValue("");
		
		Map<String, List<Map<String, String>>> mapfile = new HashMap<String, List<Map<String, String>>>();
		mapfile.put("personfile", new ArrayList<Map<String, String>>());
		//遍历map
		for(Map.Entry<String, List<Map<String, String>>> entry : mapfile.entrySet()){
			this.setFilesInfo(entry.getKey(), entry.getValue(), false);
		}
		return EventRtnType.NORMAL_SUCCESS;
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
		//this.setMainMessage("附件上传成功！");
		String sql="";
	try {
		
		HBSession sess = HBUtil.getHBSession();
		if(oid!=null||!"".equals(oid)) {	
			String sqlg="select a.* from HJXJADD a where a.add00='"+oid+"'  ";
			List<HashMap<String, Object>> list;
				list = cqbs.getListBySQL(sqlg);
				HashMap<String, Object> map=list.get(0);
				String fileurl = map.get("fileurl")==null?"":map.get("fileurl").toString();
				String filename = map.get("filename")==null?"":map.get("filename").toString();
				String filesize = map.get("filesize")==null?"":map.get("filesize").toString();
			if(list.size()!=0||list.size()!=0){
				fileurl=fileurl.replaceAll("\\\\", "/");
				HBUtil.executeUpdate("insert into ZB_FJ(fjid,zbid,fileurl,filename,filesize) "
						+ "values(sys_guid(),?,?,?,?)",new Object[]{oid,fileurl,filename,filesize});
				///sql="update zb_FJ set fileurl='"+fileurl+"',filename='"+filename+"',filesize='"+filesize+"' where zbid='"+oid+"'  ";
				//System.out.println(sql);
					//更新数据
					//st.execute(sql1);
				//sess.createSQLQuery(sql).executeUpdate();
				
				}
				
		}
			} catch (Exception e) {
			} finally {
			}
			
				//this.setNextEventName("fileGrid.dogridquery");
				this.setMainMessage("附件上传成功！");
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
	 * 删除数据并删除文件
	 * @param id
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteFile")
	public int deleteFile(String add00) throws RadowException, AppException {

		try {
			HBSession sess  = HBUtil.getHBSession();
			String sql1 = " delete from HJXJADD p where p.add00 = '" + add00 + "' ";
			String sql2 = " update zb set fileurl='',filename='',filesize='' where zbid='"+add00+"'    ";
			//this.setMainMessage("附件删除成功！");
			//更新数据
			//st.execute(sql1);
			sess.createSQLQuery(sql1).executeUpdate();
			sess.createSQLQuery(sql2).executeUpdate();
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
		String sql = " select p.add00, p.fileurl, p.filename from HJXJADD p where p.filename = '" + filename + "' ";
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
}
