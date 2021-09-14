package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.File;
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
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.util.StringUtil;

/**
 * 任免表界面的日志管理
 * @author hongy
 *
 */
public class GBRSPageModel extends PageModel{
	public static String disk;
	private HBSession sess;
	/**
	 * 页面初始化
	 */
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridMain.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 材料信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("gridMain.dogridquery")
	public int doMainQuery(int start, int limit) throws RadowException{
		String a0000 = this.getPageElement("a0000").getValue();
		String type=this.getPageElement("type").getValue();
		String sql = "select a.fileid,b.username,a.filename,a.createon" + 
				"	from tablefile a ,SMT_USER b where a.userid=b.userid and a.a0000='"+a0000+"'"
						+ "and a.filetype='"+type+"'";
		sql += " order by a.createon desc";
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String fileid)throws RadowException, AppException{
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			deleteFile(fileid);
			sess.createSQLQuery("delete from tablefile where fileid = '"+fileid+"'").executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("radow.doEvent('gridMain.dogridquery')");
			this.setMainMessage("删除成功！");
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/***
	 * 下载材料
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("downBtn")
	public int downBtn(String fileid) throws RadowException, AppException {
		try {
			sess = HBUtil.getHBSession();
			disk = (String)sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'UPLOAD_PATH'").uniqueResult();
			String sql="select t.filepath,t.filename from tablefile t where t.fileid = '"+fileid+"'";
			List<Object[]> lists=sess.createSQLQuery(sql).list();
			if(lists!=null) {
				for(Object[] obj:lists) {
					//obj[0] 文件路径
					//obj[1] 文件名
					String directory = disk +"\\"+ obj[0]+"\\"+obj[1];
					this.getPageElement("downfile").setValue(directory.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.reloadTree()");
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/****
	 * 删除文件
	 */
	public String deleteFile(String id) {
		try {
			sess = HBUtil.getHBSession();
			disk = (String) sess.createSQLQuery("select AAA005 from AA01 where AAA001 = 'UPLOAD_PATH'").uniqueResult();
			String sql="select t.filepath,t.filename from tablefile t where t.fileid = '"+id+"'";
			List<Object[]> lists=sess.createSQLQuery(sql).list();
			if(lists!=null) {
				for(Object[] obj:lists) {
					//obj[0] 文件路径
					//obj[1] 文件名
					String directory = disk +"\\"+ obj[0]+"\\"+obj[1];
					File f = new File(directory);
					if (f.exists()) {
						deleteFile(f);
					}
				}
			}
			return id;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
	
	/**
	 * 
	 *
	 * @param dirFile 要被删除的文件或者目录
	 * @return 删除成功返回true, 否则返回false
	 */
	public static boolean deleteFile(File dirFile) {
	    // 如果dir对应的文件不存在，则退出
	    if (!dirFile.exists()) {
	        return false;
	    }
	    else if(dirFile.isFile()) {
	        return dirFile.delete();
	    }
	    return dirFile.delete();
	}
	
	
}
