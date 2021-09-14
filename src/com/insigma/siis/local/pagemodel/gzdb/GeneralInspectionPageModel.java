package com.insigma.siis.local.pagemodel.gzdb;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.DataKcImpDBThread;
import com.insigma.siis.local.pagemodel.customquery.DataKcbThread;
import com.insigma.siis.local.pagemodel.customquery.DataKcnThread;
import com.insigma.siis.local.pagemodel.customquery.DataKcvThread;
import com.insigma.siis.local.pagemodel.customquery.DataSHPadImpDBThread;

public class GeneralInspectionPageModel extends PageModel {
	CommQuery cqbs=new CommQuery();
	private String initalPath = (AppConfig.HZB_PATH + "/CadreRelated/document").replace("/", System.getProperty("file.separator"));

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return 0;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		this.setNextEventName("mdgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("download")
	@Transaction
	public int download(String params) throws RadowException, AppException {
		/*CommQuery cq = new CommQuery();
		String[] param = params.split("&&");
		String pid = param[0];
		String xjqy = param[1];
		String Info = "";
		String sql = "select t.giid,t.xjqy,t.tjlx,t.filename,b.aaa005 || t.FILEURL FILEURL from GI t,aa01 b where 1=1 and b.aaa001='HZB_PATH'  and  t.tjlx='"+pid+"' and xjqy='"+xjqy+"' ";
		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
		int i =0;
		if(list.size()>0)
		{
		   for(HashMap<String, Object> map : list){
			   String fileurl = map.get("fileurl").toString();//附件ID
			   String filename = map.get("filename").toString();//附件名称
			   fileurl=fileurl.replaceAll("\\\\", "/");
			   fileurl ="javascript:downloadFile(\"" + fileurl+ "\")";
			   fileurl = "<a href="+ fileurl +">"+ filename + "</a>&nbsp";
			   Info += fileurl;
		   }
		}*/
		String fujianInfo = fujian( params );
		//this.getExecuteSG().addExecuteCode("win( '"+Info+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
		
	// 获取附件信息
		public String fujian(String params ) throws AppException {
			CommQuery cq = new CommQuery();
			String[] param = params.split("&&");
			String pid = param[0];
			String xjqy = param[1];
			String Info = "";
			String sql = "select t.giid,t.xjqy,t.tjlx,t.filename,b.aaa005 || t.FILEURL FILEURL from GI t,aa01 b where 1=1 and b.aaa001='HZB_PATH'  and  t.tjlx='"+pid+"' and xjqy='"+xjqy+"' ";
			List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			int i =0;
			if(list.size()>0)
			{
			   for(HashMap<String, Object> map : list){
				   String fileurl = map.get("fileurl").toString();//附件ID
				   String filename = map.get("filename").toString();//附件名称
				   fileurl=fileurl.replaceAll("\\\\", "/");
				   fileurl ="javascript:downloadFile(\"" + fileurl+ "\")";
				   fileurl = "<a href="+ fileurl +">"+ filename + "</a>&nbsp;";
				   Info += fileurl;
			   }
			}
			return Info;
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
		String sql = " insert into WJGLADD values ( '" + pid + "', '', '" + UUID.randomUUID().toString().replace("-", "") + "', "
				+ "'" + fileName + "', '" + partialPath + "', '', '', '' ) ";
		HBUtil.executeUpdate(sql);
		this.setMainMessage("附件上传成功！");
		this.setNextEventName("fileGrid.dogridquery");
		String fujianInfo = fujianList( pid );
		this.getExecuteSG().addExecuteCode("colseWin( '"+fujianInfo+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("sqRow")
	@GridDataRange
	public int sqRow(String code_value) throws RadowException, AppException{  //打开窗口的实例
		this.getPageElement("mnur01_combotree").setValue("");
		this.getPageElement("mnur01").setValue("");
		String userid=SysManagerUtils.getUserId();
		this.getPageElement("code_value").setValue(code_value);
		List mdlist = HBUtil.getHBSession().createSQLQuery("select 1 from HZ_GIQ y where  userid='"+userid+"' and code_value='"+code_value+"'").list();
		if(mdlist.size()==0){
			this.setMainMessage("无法授权！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "select listagg (T.mnur01, ',') WITHIN GROUP (ORDER BY mnur05) k,  "
				+ "listagg (T.mnur04, ',') WITHIN GROUP (ORDER BY mnur05) v "
				+ "from hz_gi t where code_value='"+code_value+"' and mnur02='"+userid+"'";

		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list.size()>0){
			Object[] o = list.get(0);
			this.getPageElement("mnur01_combotree").setValue(o[1]==null?"":o[1].toString());
			this.getPageElement("mnur01").setValue(o[0]==null?"":o[0].toString());
		}else{
			this.getPageElement("mnur01_combotree").setValue("");
			this.getPageElement("mnur01").setValue("");
		}
		this.getExecuteSG().addExecuteCode("openSQWin();");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("saveSQInfo")
	@GridDataRange
	public int saveSQInfo() throws RadowException, AppException{ 
		String curuserid=SysManagerUtils.getUserId();
		String mnur01 = this.getPageElement("mnur01").getValue();
		String mnur01_combotree = this.getPageElement("mnur01_combotree").getValue();
		String code_value=this.getPageElement("code_value").getValue();
		//HBSession sess = HBUtil.getHBSession();
		String mdid=this.getPageElement("mdid").getValue();
		String gg=UUID.randomUUID().toString().replace("-", "");
		try {
			HBUtil.executeUpdate("delete from hz_gi where mdid=? and mnur02=?",new Object[]{mdid,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into hz_gi(mnur00,mdid,mnur01,mnur02,mnur04,mnur05,code_value) "
							+ "values(sys_guid(),?,?,?,?,?,?)",new Object[]{gg,mnur01s[i],curuserid,mnur01_combotrees[i],i,code_value});
					HBUtil.executeUpdate("insert into hz_giq(code_value,userid,sub_code_value,mdid) "
							+ "values(?,?,?,?)",new Object[]{code_value,mnur01s[i],i,gg});
					//sess.createSQLQuery("update hz_giq set code_name=(select code_name from code_value where code_type='QUXZ' and code_value=? ) where userid=?")
					//.setString(0, code_value).setString(1, mnur01s[i]).executeUpdate();
					//sess.flush();
					HBUtil.executeUpdate("update hz_giq set code_name=(select code_name from code_value where code_type='QUXZ' and code_value=? ) where mdid=?",new Object[]{code_value,gg});
					HBUtil.executeUpdate("delete from hz_gi where mnur01=mnur02 and mdid='"+mdid+"'");
				}
			}else {
				HBUtil.executeUpdate("delete from hz_gi where code_value=? and mnur02=?",new Object[]{code_value,curuserid});
				HBUtil.executeUpdate("delete from hz_giq where code_value=? and userid <>?",new Object[]{code_value,curuserid});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("mdgrid.dogridquery")
	@NoRequiredValidate
	public int gridQuery(int start,int limit) throws RadowException,AppException{
		String userid=SysManagerUtils.getUserId();
		StringBuffer sql=new StringBuffer();
		sql.append("select y.*," + 
				"      (select username from smt_user where userid = y.userid) mnur02,'1' type" + 
				"  from HZ_GIQ y" + 
				" where userid = '"+userid+"' " + 
				" union all" + 
				" select y.*," + 
				"       (select (select username from smt_user where userid = u.mnur02)" + 
				"          from hz_gi u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and y.mdid = u.mdid) mnur02,'2' type" + 
				"  from HZ_GIQ y" + 
				" where exists (select 1" + 
				"          from hz_gi u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and y.mdid = u.mdid)");
		sql.toString();
		String finsql="select * from ("+sql+")  where type='1' order by to_number(sub_code_value) ";
		this.pageQuery(finsql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	} 
	/**
	 * 刷新
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("mdgrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		//String code_value = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		//this.getPageElement("code_value").setValue(code_value);
		this.setNextEventName("fileGrid.dogridquery");
		this.setNextEventName("file1Grid.dogridquery");
		this.setNextEventName("file2Grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("fileGrid.dogridquery")
	@NoRequiredValidate
	public int gridDoQuery(int start,int limit) throws RadowException,AppException{
		String xjqy = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		StringBuffer sql=new StringBuffer();
		
		//sql.append("select distinct t.tjlx,t.XJQY_CODE,t.TJLX_CODE tjlxl from GI_TYPE t where  t.XJQY_CODE='"+xjqy+"' and t.tjlx_code like 'tjl%'  ");
		sql.append("select n.tjlx,n.xjqy_code,n.tjlxl,m.fileurl,m.filename from (select distinct g.xjqy,g.tjlx,LISTAGG((b.aaa005 || g.FILEURL),'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILEURL," + 
				"LISTAGG(filename,'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILENAME" + 
				"                 from gi g, aa01 b" + 
				"                where 1 = 1" + 
				"                  and g.xjqy = '"+xjqy+"'  " + 
				"                  and b.aaa001 = 'HZB_PATH'" + 
				"                  and g.tjlx like 'tjl%') m  right join " + 
				"                  (" + 
				"select distinct t.px,t.tjlx, t.XJQY_CODE, t.TJLX_CODE tjlxl" + 
				"          from GI_TYPE t" + 
				"         where t.XJQY_CODE = '"+xjqy+"' " + 
				"           and t.tjlx_code like 'tjl%') n" + 
				"  on n.XJQY_CODE=m.xjqy and n.tjlxl=m.tjlx order by n.px ");
		
		//sql.append("select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='tjl' "
					//+ " union all select  t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cpl'"
							//+ "union all select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cll'");
		
		//判断组织部有没有 请销假审核角色
		//UniteUserBs qxjObj = new UniteUserBs();
		//if(!qxjObj.isLeaderRS() ){
		    //  sql.append(" and t.userid = '"+user.getId()+"'");
		//}
			//sql.append("select  t.hjxjid,t.xjqy,t.yjwc,t.ejwc,t.sajwc,t.sijwc,t.wjwc,t.jd" +
					//",t.cz,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from HJXJ t where 1=1 and quxz='2' ");
			
			//判断组织部有没有 请销假审核角色
			//UniteUserBs qxjObj = new UniteUserBs();
			//if(!qxjObj.isLeaderRS() ){
			    //  sql.append(" and t.userid = '"+user.getId()+"'");
			//}
		//sql.append(" order by createdon asc ");
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("file1Grid.dogridquery")
	@NoRequiredValidate
	public int gridDo1Query(int start,int limit) throws RadowException,AppException{
		String xjqy = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		StringBuffer sql=new StringBuffer();
		
		sql.append("select n.tjlx,n.xjqy_code,n.tjlxl,m.fileurl,m.filename from (select distinct g.xjqy,g.tjlx,LISTAGG((b.aaa005 || g.FILEURL),'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILEURL," + 
				"LISTAGG(filename,'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILENAME" + 
				"                 from gi g, aa01 b" + 
				"                where 1 = 1" + 
				"                  and g.xjqy = '"+xjqy+"'  " + 
				"                  and b.aaa001 = 'HZB_PATH'" + 
				"                  and g.tjlx like 'cpl%') m  right join " + 
				"                  (" + 
				"select distinct t.px,t.tjlx, t.XJQY_CODE, t.TJLX_CODE tjlxl" + 
				"          from GI_TYPE t" + 
				"         where t.XJQY_CODE = '"+xjqy+"' " + 
				"           and t.tjlx_code like 'cpl%') n" + 
				"  on n.XJQY_CODE=m.xjqy and n.tjlxl=m.tjlx order by n.px ");
		
		//sql.append("select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='tjl' "
					//+ " union all select  t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cpl'"
							//+ "union all select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cll'");
		
		//判断组织部有没有 请销假审核角色
		//UniteUserBs qxjObj = new UniteUserBs();
		//if(!qxjObj.isLeaderRS() ){
		    //  sql.append(" and t.userid = '"+user.getId()+"'");
		//}
			//sql.append("select  t.hjxjid,t.xjqy,t.yjwc,t.ejwc,t.sajwc,t.sijwc,t.wjwc,t.jd" +
					//",t.cz,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from HJXJ t where 1=1 and quxz='2' ");
			
			//判断组织部有没有 请销假审核角色
			//UniteUserBs qxjObj = new UniteUserBs();
			//if(!qxjObj.isLeaderRS() ){
			    //  sql.append(" and t.userid = '"+user.getId()+"'");
			//}
		//sql.append(" order by createdon asc ");
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	@PageEvent("file2Grid.dogridquery")
	@NoRequiredValidate
	public int gridDo2Query(int start,int limit) throws RadowException,AppException{
		String xjqy = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		StringBuffer sql=new StringBuffer();
		
		sql.append("select n.tjlx,n.xjqy_code,n.tjlxl,m.fileurl,m.filename from (select distinct g.xjqy,g.tjlx,LISTAGG((b.aaa005 || g.FILEURL),'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILEURL," + 
				"LISTAGG(filename,'&')WITHIN GROUP (ORDER BY g.tjlx) " + 
				"OVER (PARTITION BY g.tjlx) AS FILENAME" + 
				"                 from gi g, aa01 b" + 
				"                where 1 = 1" + 
				"                  and g.xjqy = '"+xjqy+"'  " + 
				"                  and b.aaa001 = 'HZB_PATH'" + 
				"                  and g.tjlx like 'cll%') m  right join " + 
				"                  (" + 
				"select distinct t.px,t.tjlx, t.XJQY_CODE, t.TJLX_CODE tjlxl" + 
				"          from GI_TYPE t" + 
				"         where t.XJQY_CODE = '"+xjqy+"' " + 
				"           and t.tjlx_code like 'cll%') n" + 
				"  on n.XJQY_CODE=m.xjqy and n.tjlxl=m.tjlx order by n.px ");
		
		//sql.append("select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='tjl' "
					//+ " union all select  t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cpl'"
							//+ "union all select t.giid,t.xjqy,t.filename,t.fileurl,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from GI t where 1=1 and  t.xjqy='"+xjqy+"' and t.tjlx='cll'");
		
		//判断组织部有没有 请销假审核角色
		//UniteUserBs qxjObj = new UniteUserBs();
		//if(!qxjObj.isLeaderRS() ){
		    //  sql.append(" and t.userid = '"+user.getId()+"'");
		//}
			//sql.append("select  t.hjxjid,t.xjqy,t.yjwc,t.ejwc,t.sajwc,t.sijwc,t.wjwc,t.jd" +
					//",t.cz,to_char (t.createdon, 'YYYY-MM-DD HH:mm:ss') createdon from HJXJ t where 1=1 and quxz='2' ");
			
			//判断组织部有没有 请销假审核角色
			//UniteUserBs qxjObj = new UniteUserBs();
			//if(!qxjObj.isLeaderRS() ){
			    //  sql.append(" and t.userid = '"+user.getId()+"'");
			//}
		//sql.append(" order by createdon asc ");
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
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
					//更新数据
					//st.execute(sql1);
				sess.createSQLQuery(sql).executeUpdate();
				
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
	public int deleteFile(String ar) throws RadowException, AppException {

		try {
			HBSession sess = HBUtil.getHBSession();
			String[] param = ar.split("&&");
			String giid = param[0];
			String fileurl = param[1];
			// 删除文件
			if (fileurl != null && !"".equals(fileurl)) {
				File file = new File(fileurl); 
			    if(file.exists()){     //判断是否存在  
			    	file.delete();      //删除  
			    }
			}
			String sql1 = " delete from GI p where p.giid = '" + giid + "' ";
			//this.setMainMessage("附件删除成功！");
			//更新数据
			//st.execute(sql1);
			sess.createSQLQuery(sql1).executeUpdate();
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
	
	//导出个人数据
		@PageEvent("importSHBtn")
		public int expHzbWin(String code_value) throws RadowException, UnsupportedEncodingException {
			
			HBSession session = HBUtil.getHBSession();

			try {
				CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				Runnable thr = null;
				String zipid=UUID.randomUUID().toString().replace("-", "");
				try {
					KingbsconfigBS.saveImpDetailInit3(zipid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String userid = SysManagerUtils.getUserId();
				thr=new DataKcImpDBThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),code_value,userid);
				new Thread(thr,"Thread_sheexp").start();
				this.setRadow_parent_data(zipid);
			    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+zipid+"',g_contextpath)");
				return EventRtnType.NORMAL_SUCCESS;

			} catch (Exception e) {
				e.printStackTrace();
			}
			return EventRtnType.NORMAL_SUCCESS;
		}
		//导出个人数据
				@PageEvent("importKc")
				public int expHz(String kc) throws RadowException, UnsupportedEncodingException {
					
					HBSession session = HBUtil.getHBSession();
					String[] param = kc.split("&&");
					String code_value = param[0];
					String type = param[1];
					try {
						CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
						UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
						Runnable thv = null;
						Runnable thb = null;
						Runnable thn = null;
						String zipid=UUID.randomUUID().toString().replace("-", "");
						try {
							KingbsconfigBS.saveImpDetailInit3(zipid);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String userid = SysManagerUtils.getUserId();
						if(type.equals("1")) {
							thv=new DataKcvThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),code_value,userid);
							new Thread(thv,"Thread_sheexpv").start();
						}else if(type.equals("2")) {
							thb=new DataKcbThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),code_value,userid);
							new Thread(thb,"Thread_sheexpb").start();
						}else if(type.equals("3")) {
							thn=new DataKcnThread(zipid,PrivilegeManager.getInstance().getCueLoginUser(),code_value,userid);
							new Thread(thn,"Thread_sheexpn").start();
						}
						this.setRadow_parent_data(zipid);
					    this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','导出进度',500,300,'"+zipid+"',g_contextpath)");
						return EventRtnType.NORMAL_SUCCESS;

					} catch (Exception e) {
						e.printStackTrace();
					}
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
	
   // 获取附件信息
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
			   String add00 = map.get("add00").toString();//附件ID
			   String filename = map.get("filename").toString();//附件名称
			   String allPath = getAllPath(filename);
			  // allPath ="javascript:downloadFile('" + allPath.replace(System.getProperty("file.separator"), "/") + "')";
			   allPath ="javascript:downloadFile(\"" + allPath.replace(System.getProperty("file.separator"), "/") + "\")";
			   allPath = String.valueOf(i)+"、<a href="+ allPath +">"+ filename + "</a><br>";
			   Info += allPath;
		   }
		}
		return Info;
	}
}
