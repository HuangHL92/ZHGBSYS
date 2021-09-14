package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.CodeTable;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MNTPGWPZPageModel  extends PageModel{

	@Override
	public int doInit() throws RadowException {
		CommQuery cqbs=new CommQuery();
		String sql="select wayid,wayname from ZDGW_WAY order by sortid";
		List<HashMap<String, Object>> list;
		try {
			list = cqbs.getListBySQL(sql);
			Map<String, String> map = new TreeMap<String, String>();
			if (list != null && list.size() > 0) {
				for (HashMap<String, Object> map1 : list) {
					map.put(map1.get("wayid").toString(), map1.get("wayname").toString());
				}
			}
			StringBuffer dataarrayjs = new StringBuffer("[");
			Set<String> tablecodes = map.keySet();
			for(String tablecode : tablecodes){
				dataarrayjs.append("['"+tablecode+"','"+map.get(tablecode)+"'],");
			}
			if(map.size()>0){
				dataarrayjs.deleteCharAt(dataarrayjs.length()-1);
			}
			dataarrayjs.append("]");
			this.getExecuteSG().addExecuteCode("$h.changeComboStore({gridid:'allGrid',colIndex:8,dataArray:"+dataarrayjs+"});");

		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("init();");
		return 0;
	}
	
	@PageEvent("updateGrid")
	public int updateGrid() {
		try {
			CommQuery cqbs=new CommQuery();
			String fabd00=this.getPageElement("fabd00").getValue();
			
			String sql="select * from HZ_MNTP_SJFA where fabd00='"+fabd00+"'";
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			this.getPageElement("mntpname").setValue(list.get(0).get("fabd02").toString());
			sql="select b0111,name from ("
					
					+ "	 select b.famx00||'@@'||a.sjdw00||'@@'||a.b0111||'@@' b0111,"
					+ " b.famx03||'：'||(select b0101 from b01 where b01.b0111=a.b0111) name,"
					+ "       (select b0269 from b01 where b01.b0111=a.b0111) b0269,famx02"
					+ " from HZ_MNTP_SJFA_ORG a,HZ_MNTP_SJFA_famx b where a.famx00=b.famx00 and fabd00='"+fabd00+"'  and a.status='1' and b.famx01='2'"
					+ " ) t order by famx02,b0269";
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				mapCode.put(listCode.get(i).get("b0111").toString(), listCode.get(i).get("name"));
			}
			((Combo)this.getPageElement("mntp_b01")).setValueListForSelect(mapCode);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("allGrid.dogridquery")
	@Transaction
	@Synchronous(true)
	public int allSelect(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String mntp_b01=this.getPageElement("mntp_b01").getValue();
		String sql="";
		try {
			String[] arr=mntp_b01.split("@@");
			String famx00=arr[0];
			String sjdw00=arr[1];
			String b0111=arr[2];
			String b0131="";
			if(arr.length==3||"".equals(arr[3])) {
				
			}else {
				b0131=arr[3];
			}
			sql="select a.bzgw,a.fxyp00,b.a0000,a.fxyp02 gwname,a.a0201e gwtype,b.tp0101 a0101,b.tp0100,b.sortnum,a.gwmc,"
					+ " b.tp0106 a0192a,a.fxyp01 sortid from hz_fxyp_SJFA a,hz_rxfxyp_SJFA b "
					+ " where a.famx00='"+famx00+"' and a.fxyp00=b.fxyp00(+) and a.b0111='"+b0111+"' ";
			if(!"".equals(b0131)) {
				sql=sql+" and a.b0131='"+b0131+"'";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql=sql+" order by fxyp01, sortnum";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("save")
	@NoRequiredValidate
	public int save()throws RadowException{
		String mntp_b01=this.getPageElement("mntp_b01").getValue();
		String[] arr=mntp_b01.split("@@");
		String famx00=arr[0];
		String sjdw00=arr[1];
		String b0111=arr[2];
		String b0131="";
		if(arr.length==3||"".equals(arr[3])) {
			
		}else {
			b0131=arr[3];
		}
		
		String flag=this.getPageElement("flag").getValue();//新增或者修改标识
		String gwname=this.getPageElement("gwname").getValue();
		String gwtype=this.getPageElement("gwtype").getValue();
		String fxyp00="";
		String userid = SysManagerUtils.getUserId();
		try {
			String sql="";
			String mntpname = this.getPageElement("mntpname").getValue();
			String mntp_b01_text = this.getPageElement("mntp_b01_combo").getValue();
			if("1".equals(flag)) {
				new LogUtil().createLogNew("单位调配岗位新增","单位调配方案岗位新增","模拟调配岗位表",sjdw00,mntpname+"("+mntp_b01_text+":"+gwname+")", new ArrayList());
				//新增
				fxyp00=UUID.randomUUID().toString();
				String pxh=getMax_sort(sjdw00);
				sql="insert into hz_fxyp_SJFA  (fxyp00,fxyp01,fxyp02,fxyp05,a0201e,status,b0111,b0131,sjdw00,famx00) values "
					+ " ('"+fxyp00+"','"+pxh+"','"+gwname+"','"+userid+"','"+gwtype+"','1','"+b0111+"','"+b0131+"','"+sjdw00+"','"+famx00+"')";
				this.getExecuteSG().addExecuteCode("document.getElementById('flag').value='2';document.getElementById('fxyp00').value='"+fxyp00+"';");
			}else {
				new LogUtil().createLogNew("修改单位调配岗位","修改单位调配方案岗位","模拟调配岗位表",sjdw00,mntpname+"("+mntp_b01_text+":"+gwname+")", new ArrayList());
				//修改
				fxyp00=this.getPageElement("fxyp00").getValue();
				sql="update hz_fxyp_SJFA set fxyp02='"+gwname+"',a0201e='"+gwtype+"'  where fxyp00='"+fxyp00+"' ";
			}
			HBSession sess = HBUtil.getHBSession();
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			
			stmt.close();
			this.setMainMessage("保存成功");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.setNextEventName("allGrid.dogridquery");
		//this.getExecuteSG().addExecuteCode("parent.infoSearch();");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delGW2")
	@GridDataRange
	@NoRequiredValidate
	public int delGW2(String fxyp00) throws RadowException{ 
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sjdw00 = HBUtil.getValueFromTab("sjdw00", "hz_fxyp_SJFA", "fxyp00='"+fxyp00+"'");
			
			
			
			
			String sql="delete from hz_fxyp_SJFA where fxyp00='"+fxyp00+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			stmt = sess.connection().createStatement();
			sql="delete from hz_rxfxyp_SJFA where fxyp00='"+fxyp00+"'";
			stmt.executeUpdate(sql);
			stmt.close();
			
			int delrow = HBUtil.executeUpdate("delete from HZ_MNTP_SJFA_ORG where (select count(1) from hz_fxyp_SJFA where sjdw00='"+sjdw00+"')=0 and sjdw00='"+sjdw00+"'");
			if(delrow==1){
				this.setNextEventName("updateGrid");
			}
			
			String mntpname = this.getPageElement("mntpname").getValue();
			String mntp_b01_text = this.getPageElement("mntp_b01_combo").getValue();
			String gwname=this.getPageElement("gwname").getValue();
			new LogUtil().createLogNew("删除单位调配岗位","删除单位调配方案岗位","模拟调配岗位表",sjdw00,mntpname+"("+mntp_b01_text+":"+gwname+")", new ArrayList());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("addGW();");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("delperson")
	@GridDataRange
	@NoRequiredValidate
	public int delperson(String str) throws RadowException{ 
		HBSession sess = HBUtil.getHBSession();
		try {
			String[] arr=str.split("@@");
			String fxyp00=arr[0];
			String a0000=arr[1];
			
			
			String mntpname = this.getPageElement("mntpname").getValue();
			String mntp_b01_text = this.getPageElement("mntp_b01_combo").getValue();
			String gwname=this.getPageElement("gwname").getValue();
			new LogUtil().createLogNew("删除单位调配岗位人选","删除单位调配方案岗位人选","模拟调配岗位人选表",fxyp00,mntpname+"("+mntp_b01_text+":"+gwname+")", new ArrayList());
			
			
			
			Statement stmt = sess.connection().createStatement();
			//String sql2 = "update rxfxyp set sortnum=sortnum-1 where fxyp00='"+fxyp00+"' and sortnum>(select sortnum from  rxfxyp where fxyp00='"+fxyp00+"' and a0000='"+a0000+"')";
			String sql="delete from hz_rxfxyp_SJFA where fxyp00='"+fxyp00+"' and a0000='"+a0000+"'";
			//stmt.executeUpdate(sql2);
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("allGrid.dogridquery");
		this.getExecuteSG().addExecuteCode("addGW();");
		//this.getExecuteSG().addExecuteCode("parent.infoSearch();");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 职务列表单击事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	//@PageEvent("allGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int GridOnRowClick() throws RadowException{ 
		//System.out.println("1121212------------------------------------------");
		//获取选中行index
		int index = this.getPageElement("allGrid").getCueRowIndex();
		String fxyp00 = this.getPageElement("allGrid").getValue("fxyp00",index).toString();
		String gwname = this.getPageElement("allGrid").getValue("gwname",index).toString();
		String gwtype = this.getPageElement("allGrid").getValue("gwtype",index).toString();
		try {
			this.getPageElement("fxyp00").setValue(fxyp00);
			this.getPageElement("gwname").setValue(gwname);
			this.getPageElement("flag").setValue("2");
			this.getPageElement("gwtype").setValue(gwtype);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("allgwsort")
	@Transaction
	public int allgwsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("allGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update hz_fxyp_SJFA set fxyp01 = ? where fxyp00=? ";
			String sql2 = "update hz_rxfxyp_SJFA set sortnum = ? where tp0100=? ";
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps2 = con.prepareStatement(sql2);
			int i = 1;
			int j = 1;
			String fxyp00;
			String fxyp00_o = "";
			for (HashMap<String, String> m : list) {
				fxyp00 = m.get("fxyp00");
				String tp0100 = m.get("tp0100");
				
				if(fxyp00!=null&&fxyp00.equals(fxyp00_o)){
					
				}else{
					fxyp00_o = fxyp00;
					j=1;
					
					ps.setInt(1, i);
			        ps.setString(2, fxyp00);
			        ps.addBatch();
					i++;
				}
				if(!StringUtils.isEmpty(tp0100)){
					ps2.setInt(1, j);
					ps2.setString(2, tp0100);
					ps2.addBatch();
					j++;
				}
			}
			ps.executeBatch();
			ps2.executeBatch();
			con.commit();
			ps.close();
			ps2.close();
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
		this.setNextEventName("allGrid.dogridquery");
		//this.getExecuteSG().addExecuteCode("parent.infoSearch();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String sjdw00){
		HBSession session = HBUtil.getHBSession();
		String sort = session.createSQLQuery("select nvl(max(fxyp01),0)+1 from hz_fxyp_SJFA where sjdw00='"+sjdw00+"'" ).uniqueResult().toString();
		return sort;
	}
	
	
	@PageEvent("allGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogridAfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("allGrid");
		String fxyp00 = grid6.getValue("fxyp00") +"";
		String gwmc = grid6.getValue("gwmc") +"";
		String bzgw = grid6.getValue("bzgw") +"";
		HBSession sess = HBUtil.getHBSession();
		sess.createSQLQuery("update HZ_FXYP_SJFA set gwmc=?, bzgw=? where fxyp00=?")
		.setString(0, gwmc).setString(1, bzgw).setString(2, fxyp00).executeUpdate();
		sess.flush();
		this.setNextEventName("allGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
