package com.insigma.siis.local.pagemodel.gzdb;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.Gbkc;
import com.insigma.siis.local.business.entity.Supervision;
import com.insigma.siis.local.business.entity.WJGL;
import com.insigma.siis.local.business.entity.WJGLAdd;
import com.insigma.siis.local.business.entity.YearCheck;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CJWJGLdwPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
				
		this.setNextEventName("initx");
		return EventRtnType.NORMAL_SUCCESS;
	}
     
	@PageEvent("initx")
	@NoRequiredValidate
	public int initx() throws Exception{	
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
	 * 关闭
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	/*@PageEvent("close")
	public int close() throws RadowException, Exception {
				
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('grid1').store.reload();");
		this.closeCueWindow("Check");
		return EventRtnType.NORMAL_SUCCESS;		
	}*/
	
	
	/**
	 * 保存
	 * 
	 * @return
	 * @throws RadowException
	 * @throws Exception
	 */
	@PageEvent("save")
	public int save() throws RadowException, Exception {

		//reloadCustomQuery();
		this.setNextEventName("insert");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("insert")
	public int insert() throws RadowException, Exception {
		HBSession sess = HBUtil.getHBSession();
		String zbmc = this.getPageElement("zbmc").getValue();
		String zbid = this.getPageElement("zbid").getValue();
		CommQuery cqbs=new CommQuery();
		try {
			
			if(zbid!=null||!"".equals(zbid)) {	
				
				String sqlg="select a.* from HJXJADD a where a.add00='"+zbid+"'  ";
				List<HashMap<String, Object>> list;
					list = cqbs.getListBySQL(sqlg);
					HashMap<String, Object> map=list.get(0);
					String fileurl = map.get("fileurl")==null?"":map.get("fileurl").toString();
					String filename = map.get("filename")==null?"":map.get("filename").toString();
					String filesize = map.get("filesize")==null?"":map.get("filesize").toString();
				if(list.size()!=0||list.size()!=0){
					fileurl=fileurl.replaceAll("\\\\", "/");
					HBUtil.executeUpdate("insert into ZB_FJ(fjid,zbid,fileurl,filename,filesize,zbmc) "
							+ "values(sys_guid(),?,?,?,?,?)",new Object[]{zbid,fileurl,filename,filesize,zbmc});
					///sql="update zb_FJ set fileurl='"+fileurl+"',filename='"+filename+"',filesize='"+filesize+"' where zbid='"+oid+"'  ";
					//System.out.println(sql);
						//更新数据
						//st.execute(sql1);
					//sess.createSQLQuery(sql).executeUpdate();
					
					}
				this.getExecuteSG().addExecuteCode("saveCallBack('保存成功');");
				//reloadCustomQuery();
				this.getExecuteSG().addExecuteCode("gg();");
					
		
			}
		} catch (Exception e) {
		} finally {
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
	public int fileg(String oid) throws RadowException, AppException, SQLException {
		this.getExecuteSG().addExecuteCode("gg();");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
}
