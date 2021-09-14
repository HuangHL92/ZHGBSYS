package com.insigma.siis.local.pagemodel.xbrm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;
import com.utils.CommonQueryBS;

import sun.misc.BASE64Encoder;

public class BatchMerge02PageModel extends PageModel {
	
	JSGLBS bs = new JSGLBS();
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("gridright.dogridquery");
		this.setNextEventName("gridleft.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("gridleft.dogridquery")
	public int dogridleftQuery(int start,int limit) throws RadowException{
//		String rb_name1 = this.getPageElement("rb_name1").getValue();
//		String rb_date1 = this.getPageElement("rb_date1").getValue();
//		String where = "";
//		if(rb_name1!=null&&!"".equals(rb_name1)){
//			where += " and t.rb_name like '%"+rb_name1+"%'";
//		}
//		if(rb_date1!=null&&!"".equals(rb_date1)){
//			where += " and t.rb_date like '%"+rb_date1+"%'";
//		}
		String sql="select * from RECORD_BATCH where nvl(RBM_STATUS,'0')='1' and rbm_dept='02' order by rb_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("gridright.dogridquery")
	public int dogridrightQuery(int start,int limit) throws RadowException{
		String sql="select * from RECORD_BATCH_MERGE where rbm_dept='02' order by rbm_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("splitrbm")
	public int splitrbm() throws RadowException{
		String rbmIds=this.getPageElement("rbmIds").getValue();	
		HBSession sess = HBUtil.getHBSession();
		try {
			if(!StringUtil.isEmpty(rbmIds)){
				rbmIds=rbmIds.substring(0, rbmIds.length()-1).replaceAll(",", "','");
			}
			
			String ids = "";
			String a0200ssql="select rbd000 from record_batch_docno where rbm_id in ('"+rbmIds+"')";
	    	CommonQueryBS cq=new CommonQueryBS();
	    	List<HashMap<String, Object>> list = cq.getListBySQL(a0200ssql);
	    	if(list.size()>0){
	    		for (int i = 0; i < list.size(); i++) {
	    			HashMap<String, Object> map = list.get(i);
	    			if(ids.equals("")) {
	    				ids +=   map.get("rbd000") ;
	    			} else {
	    				ids +=  "','"+ map.get("rbd000") ;
	    			}
	    			
				}
	    	}
			
			String sql_1 = "delete from js23 where js2300 in (select js2300 from js22 where RBD000 in ('"+ids
					+"')) or js2300 in (select js2300 from js24 where RBD000 in ('"+ids+"'))" ;
			sess.createSQLQuery(sql_1).executeUpdate();
			
			String a22sql_u = "update js22 set js2300='',RBD000='',RBD001='' where RBD000 in ('"+ids+"')";
			sess.createSQLQuery(a22sql_u).executeUpdate();
			
			String sql_0 = "delete from js24 where rbd000 in ('"+ids+"')";
			sess.createSQLQuery(sql_0).executeUpdate();
			
			String sql = "delete from RECORD_BATCH_DOCNO where rbd000 in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			
			String updaterb="update record_batch set RBM_ID='',RBM_STATUS='1' where rbm_id in('"+rbmIds+"')";
			sess.createSQLQuery(updaterb).executeUpdate();
			String updaterb2="delete RECORD_BATCH_MERGE where rbm_id in('"+rbmIds+"')";
			sess.createSQLQuery(updaterb2).executeUpdate();
			this.getExecuteSG().addExecuteCode("f5rightgird();");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("拆分异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
     * 信息导出
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("ExpGird")
    @Transaction
    public int ExpGird(String rbdId) throws RadowException {
    	QCJSFileExportBS fileBS = new QCJSFileExportBS();
        try {
        	String rbId = rbdId;//批次
            String cur_hj = "";//环节
            String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
            String dc005 = "";

            this.request.setAttribute("rbId", rbId);
            this.request.setAttribute("cur_hj", cur_hj);
            this.request.setAttribute("cur_hj_4", cur_hj_4);
            this.request.setAttribute("dc005", dc005);

            /*String js0100s = this.request.getParameter("js0100s");
            String js0100WhereSql = "";
            if (!"".equals(js0100s)) {
                js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
                ExcelStyleUtil.js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql + "')";
            } else {
                
            }*/

            fileBS.setPm(this);
            fileBS.setSheetName("讨论任免干部名单");
            fileBS.setOutputpath(JSGLBS.HZBPATH + "zhgboutputfiles/" + rbId + "/");
            File f = new File(fileBS.getOutputpath());

            if (f.isDirectory()) {//先清空输出目录
                JSGLBS.deleteDirectory(fileBS.getOutputpath());
            }
			fileBS.exportTLRMGBMD2();
			//ExcelStyleUtil.js0100WhereSql = "";

	        List<String> fns = fileBS.getOutputFileNameList();
	        String downloadPath = "";
	        String downloadName = "";
	        if (fns.size() == 1) {//一个文件直接下载
	            downloadPath = fileBS.getOutputpath() + fns.get(0);
	            downloadName = fns.get(0);
	        } else if (fns.size() > 1) {//压缩
	            downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
	            downloadName = fileBS.getSheetName() + ".zip";
	            Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
	            
	        } else {
	            return EventRtnType.NORMAL_SUCCESS;
	        }
	        String downloadUUID = UUID.randomUUID().toString();
	        request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
	        this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
	        this.getExecuteSG().addExecuteCode("downloadByUUID();");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("导出异常！");
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
	
}
