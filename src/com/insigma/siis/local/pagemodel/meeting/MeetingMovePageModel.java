package com.insigma.siis.local.pagemodel.meeting;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;

import net.sf.json.JSONArray;

public class MeetingMovePageModel  extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("initX")
	public int initX(String sh000) {
		try {
			String type=this.getPageElement("type").getValue();
			String sql="select a0201a,a0201b,(select b.b0101 from b01 b where b.b0111=a.a0201b) a0201b_combo,a0215a,sh002 from meetingryzw a where sh000='"+sh000+"' and type='"+type+"' order by sortid ";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201a').value='"+map.get("a0201a")+"';"
						+ "document.getElementById('a0201b').value='"+map.get("a0201b")+"';"
						+ "document.getElementById('a0201b_combo').value='"+map.get("a0201b_combo")+"';"
						+ "document.getElementById('a0215a').value='"+map.get("a0215a")+"';"
						+ "document.getElementById('sh002').value='"+map.get("sh002")+"';"
						+ "document.getElementById('flag').value='2';");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("ZWGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ZWGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int ZWSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String sh000=this.getPageElement("sh000").getValue();
		String type=this.getPageElement("type").getValue();
		String sql="select sh002,sh000,a0201a,a0201b,a0215a,type,sortid from meetingryzw where sh000='"+sh000+"' and type='"+type+"' order by sortid ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 工作单位及职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save")
	@NoRequiredValidate
	public int save(String id)throws RadowException{
		String sh000=this.getPageElement("sh000").getValue();
		String type=this.getPageElement("type").getValue();//拟任/拟免标识
		String flag=this.getPageElement("flag").getValue();//新增或者修改标识
		String a0201a=this.getPageElement("a0201a").getValue();
		String a0201b=this.getPageElement("a0201b").getValue();
		String a0215a=this.getPageElement("a0215a").getValue();
		String zongshu=this.getPageElement("zongshu").getValue();
		String sh002="";
		
		try {
			String sql="";
			if("1".equals(flag)) {
				//新增职务
				sh002=UUID.randomUUID().toString();
				sql="insert into meetingryzw (sh002,sh000,a0201a,a0201b,a0215a,type) values "
					+ " ('"+sh002+"','"+sh000+"','"+a0201a+"','"+a0201b+"','"+a0215a+"','"+type+"')";
				this.getExecuteSG().addExecuteCode("document.getElementById('flag').value='2';document.getElementById('sh002').value='"+sh002+"';");
			}else {
				//修改职务
				sh002=this.getPageElement("sh002").getValue();
				sql="update meetingryzw set a0201a='"+a0201a+"',a0201b='"+a0201b+"',a0215a='"+a0215a+"' "
					+ " where sh002='"+sh002+"' ";
			}
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			if("1".equals(type)) {
				//拟任
				sql="update hz_sh_a01 set tp0111='"+zongshu+"' where sh000='"+sh000+"' ";
				this.getExecuteSG().addExecuteCode("parent.document.getElementById('tp0111').value='"+zongshu+"';");
			}else {
				//拟免
				sql="update hz_sh_a01 set tp0112='"+zongshu+"' where sh000='"+sh000+"' ";
				this.getExecuteSG().addExecuteCode("parent.document.getElementById('tp0112').value='"+zongshu+"';");
			}
			stmt.close();
			this.setMainMessage("保存成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("ZWGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 职务列表单击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("ZWGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//System.out.println("1121212------------------------------------------");
		//获取选中行index
		int index = this.getPageElement("ZWGrid").getCueRowIndex();
		String sh002 = this.getPageElement("ZWGrid").getValue("sh002",index).toString();
		try {
			String sql="select a0201a,a0201b,(select b.b0101 from b01 b where b.b0111=a.a0201b) a0201b_combo,a0215a   from meetingryzw a where sh002='"+sh002+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201a').value='"+map.get("a0201a")+"';"
						+ "document.getElementById('a0201b').value='"+map.get("a0201b")+"';"
						+ "document.getElementById('a0201b_combo').value='"+map.get("a0201b_combo")+"';"
						+ "document.getElementById('a0215a').value='"+map.get("a0215a")+"';"
						+ "document.getElementById('sh002').value='"+sh002+"';"
						+ "document.getElementById('flag').value='2';");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 职务删除
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteZW")
	@GridDataRange
	@NoRequiredValidate
	public int deleteZW(String sh002) throws RadowException{
		String sql="delete from meetingryzw where sh002='"+sh002+"' ";
		HBSession sess = HBUtil.getHBSession();
		Statement stmt;
		try {
			stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getPageElement("a0201a").setValue("");
		this.getPageElement("a0201b").setValue("");
		this.getPageElement("a0201b_combo").setValue("");
		this.getPageElement("a0215a").setValue("");
		this.getPageElement("sh002").setValue("");
		this.getPageElement("flag").setValue("1");
		this.setNextEventName("ZWGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 任职机构修改
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a0201bChange")
	@GridDataRange
	@NoRequiredValidate
	public int a0201bChange() throws RadowException{
		String a0201b = this.getPageElement("a0201b").getValue();
		try {
			String sql="select b0111,b0101 from b01 b where b.b0111='"+a0201b+"' and b0194<>'3'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0) {
				this.getExecuteSG().addExecuteCode("document.getElementById('a0201a').value=document.getElementById('a0201b_combo').value;");
			}else {
				this.getExecuteSG().addExecuteCode("alert('机构分组无法作为任职机构');document.getElementById('a0201b').value='';document.getElementById('a0201b_combo').value='';");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("rolesort")
	  @Transaction
	  public int rolesort() throws RadowException {
	    List<HashMap<String, String>> list = this.getPageElement("ZWGrid").getStringValueList();
	    HBSession sess = HBUtil.getHBSession();
	    Connection con = null;
	    try {
	      con = sess.connection();
	      con.setAutoCommit(false);
	      String sql = "update meetingryzw set sortid = ? where sh002=?";
	      PreparedStatement ps = con.prepareStatement(sql);
	      int i = 1;
	      for (HashMap<String, String> m : list) {
	        String sh002 = m.get("sh002");
	        ps.setInt(1, i);
	        ps.setString(2, sh002);
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
	    this.setNextEventName("ZWGrid.dogridquery");
	    return EventRtnType.NORMAL_SUCCESS;
	  }
	
}
