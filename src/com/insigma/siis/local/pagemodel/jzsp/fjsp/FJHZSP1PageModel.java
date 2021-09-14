package com.insigma.siis.local.pagemodel.jzsp.fjsp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.entity.Sp_Att;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SQLiteUtil;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;
import com.insigma.siis.local.pagemodel.xbrm.OracleToSqlite;
import com.insigma.siis.local.pagemodel.xbrm.expword.ExpJSGLRMB;

import sun.misc.BASE64Encoder;

public class FJHZSP1PageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		//String sp0102 = this.getPageElement("sp0102").getValue();
		String spp08 = this.getPageElement("spp08").getValue();
		String where = "";
		String daiban = "";
		/*if(sp0102!=null&&!"".equals(sp0102)){
			where += " and t.sp0102 like '%"+sp0102+"%'";
		}*/
		if(spp08!=null&&!"".equals(spp08)&&!"all".equals(spp08)){
			if("99".equals(spp08)){
				//待办
				where += " and t.spp08 in ('1') and b.spb06='2' ";//处室节点
				daiban = " and 1=2 ";
			}else{
				where += " and t.spp08 = '"+spp08+"'";
			}
		}
		String sql="select t.spp00,t.spp02,t.spp03,t.spp04,t.spp05,t.spp08,t.spp09,"
				+ " t.spp13,b.spb03,b.spb04,b.spb02,b.spb06 "
				+ " from SP01_pc t left join SP_BUS b on t.spp00=b.spb00 "
				+ " where (exists "
				+ " (select 1 from SP_BUS_LOG s where s.spb00=t.spp00 and "
				+ " s.spbl01='"+SysManagerUtils.getUserId()+"' "+daiban+") "
				+ " or ((b.spb03='"+SysManagerUtils.getUserId()+"' "
				+ " or b.spb04='"+SysManagerUtils.getUserGroupid()+"') and b.spb02!='0')) "+where+" order by t.spp03 desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	public static String  disk = JSGLBS.HZBPATH ;
	@PageEvent("allDelete")
	public int allDelete(String sp0100) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery("delete from SP01 where sp0100=?").setString(0, sp0100).executeUpdate();
			sess.createSQLQuery("delete from SP_BUS where spb00=?").setString(0, sp0100).executeUpdate();
			sess.createSQLQuery("delete from SP_BUS_LOG where spb00=?").setString(0, sp0100).executeUpdate();
			this.getExecuteSG().addExecuteCode("$('#sp0100').val('');$('#a0000').val('');");
			List<Sp_Att> spalist = sess.createQuery("from Sp_Att where spb00='"+sp0100+"'").list();
			String directory = null;
			for(Sp_Att spa : spalist){
				directory = disk+spa.getSpa05();
				File f = new File(directory+spa.getSpa00());
				if(f.isFile()){
					f.delete();
				}
				sess.delete(spa);
				
			}
			if(directory!=null){
				File f = new File(directory);
				if(f.isDirectory()){
					f.delete();
				}
			}
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
}
