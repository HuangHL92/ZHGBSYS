package com.insigma.siis.local.pagemodel.zwzc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02ZwzcMx;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.Historyper;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.JiaTingChengYuanXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class LsmdPageModel extends PageModel {
	private final static int ON_ONE_CHOOSE=-1;
	private final static int CHOOSE_OVER_TOW=-2;

	@Override
	public int doInit() throws RadowException {
		this.createPageElement("BQLX", ElementType.SELECT, false).setDisabled(true);
		this.setNextEventName("mdgrid.dogridquery");
		this.getExecuteSG().addExecuteCode("initX();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("mdgrid.dogridquery")
	@NoRequiredValidate
	public int mdgridGridQuery(int start,int limit) throws RadowException, AppException {
		String userid=SysManagerUtils.getUserId();
		String searchOnePerson=this.getPageElement("seachName").getValue();
		StringBuilder sb = new StringBuilder("select y.*," + 
				"      (select username from smt_user where userid = y.userid) mnur02,'1' type" + 
				"  from historymd y" + 
				" where userid = '"+userid+"' and y.mdmc like  '%"+searchOnePerson+"%' " + 
				" union all" + 
				" select y.*," + 
				"       (select (select username from smt_user where userid = u.mnur02)" + 
				"          from hz_LSMD_userref u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and y.mdid = u.mdid) mnur02,'2' type" + 
				"  from historymd y" + 
				" where exists (select 1" + 
				"          from hz_LSMD_userref u" + 
				"         where mnur01 = '"+userid+"'" + 
				"           and u.mdid = y.mdid) and y.mdmc like  '%"+searchOnePerson+"%'" );	
		
		String finsql="select * from ("+sb+") order by createdate desc";
		this.pageQuery(finsql, "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	//点击名单列表
	@PageEvent("mdgrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String mdj = this.getPageElement("mdj").getValue();
		String mdid = "";
		if("1".equals(mdj)){
			mdid = this.getPageElement("mdid").getValue();
			this.getPageElement("mdj").setValue("0");
		}else{
			mdid = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("mdid");
			this.getPageElement("mdid").setValue(mdid);
		}
		//String locked = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("locked");
		
		
		String username = SysManagerUtils.getUserName();
		this.getPageElement("ss").setValue(username);
		
		CommQuery cqbs=new CommQuery();
		String sql="select mdmc,(select username from smt_user where userid = y.userid) username,createdate,remark"
				+ " ,locked,decode(locked,'1','已锁定','未锁定') lockdesc"
				+ " ,(select code_name from code_value where code_type='GZMD02' and code_value = y.mdtype) mdtypen,mdtype"
				+ " ,(select code_name from code_value where code_type='GZMD01' and code_value = y.bqtype) bqtypen,bqtype  "
				+ "	 from historymd y where mdid='"+mdid+"' ";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=list.get(0);
		this.getPageElement("MDName").setValue(map.get("mdmc")==null?"":map.get("mdmc").toString());
		//this.getPageElement("MDLX").setValue(map.get("mdtype")==null?"":map.get("mdtype").toString());
		this.getPageElement("MDLX_combo").setValue(map.get("mdtypen")==null?"":map.get("mdtypen").toString());
		this.getPageElement("BQLX").setValue(map.get("bqtype")==null?"":map.get("bqtype").toString());
		String mdlx=map.get("mdtype")==null?"":map.get("mdtype").toString();
		this.getPageElement("MDLX").setValue(mdlx);
		this.getPageElement("BQLX_combo").setValue(map.get("bqtypen")==null?"":map.get("bqtypen").toString());
		this.getPageElement("MDSD").setValue(map.get("locked")==null?"":map.get("locked").toString());
		this.getPageElement("MDSD_combo").setValue(map.get("lockdesc")==null?"":map.get("lockdesc").toString());
		this.getPageElement("MDremark").setValue(map.get("remark")==null?"":map.get("remark").toString());
		this.getPageElement("MDCJR").setValue(map.get("username")==null?"":map.get("username").toString());
		this.getPageElement("MDCJSJ").setValue(map.get("createdate")==null?"":map.get("createdate").toString());
		
		String locked=map.get("locked")==null?"":map.get("locked").toString();
		this.getPageElement("locked").setValue(locked);
		
		String g1=map.get("username")==null?"":map.get("username").toString();
		
		this.getPageElement("username").setValue(g1);
		if(!g1.equals(username)) {
			this.getExecuteSG().addExecuteCode("tt();");
		}else {
			this.getExecuteSG().addExecuteCode("pp();");
		}
		if(!"03".equals(mdlx)) {
			this.createPageElement("BQLX", ElementType.SELECT, false).setDisabled(true);
		}else {
			this.createPageElement("BQLX", ElementType.SELECT, false).setDisabled(false);
		}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("save")
	@Transaction
	@AutoNoMask
	public int save() throws Exception {
		//获取页面数据
		HBSession sess = HBUtil.getHBSession();
		String username = SysManagerUtils.getUserName();
		String MDCJR=this.getPageElement("MDCJR").getValue();
		String MDName=this.getPageElement("MDName").getValue();
		String MDLX=this.getPageElement("MDLX").getValue();
		String BQLX=this.getPageElement("BQLX").getValue();
		String MDSD=this.getPageElement("MDSD").getValue();
		String MDremark=this.getPageElement("MDremark").getValue();
		String mdid = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("mdid");
		if(!MDCJR.equals(username)) {
			this.setMainMessage("该名单只能由创建人进行修改！");
			this.setNextEventName("mdgrid.dogridquery");
			this.setNextEventName("mdgrid.rowclick");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(!"03".equals(mdtype)&&!"".equals(bqtype)) {
//			this.setMainMessage("不是标签名单无法选标签类型！");
//			this.setNextEventName("mdgrid.dogridquery");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(!"03".equals(MDLX)) {
			BQLX="";
		}
		sess.createSQLQuery("update HISTORYMD set mdmc=?,remark=?,locked=?,mdtype=?,bqtype=? where mdid=?")
		.setString(0, MDName).setString(1, MDremark).setString(2, MDSD).setString(3, MDLX).setString(4, BQLX).setString(5, mdid).executeUpdate();
		sess.flush();
		this.getPageElement("MDSD").setValue(MDSD);
		this.setMainMessage("保存成功！");
		this.setNextEventName("mdgrid.dogridquery");
		this.setNextEventName("mdgrid.rowclick");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrid.dogridquery")
	@NoRequiredValidate
	public int editgridGridQuery(int start, int limit) throws RadowException {
		String mdid=this.getPageElement("mdid").getValue();
		StringBuilder sb = new StringBuilder("select t.*,a01.a0192a a0192anow,(select username from smt_user c where t.a0188=c.userid) username " + 
				" from HISTORYPER t,a01 where t.mdid='"+mdid+"' and t.a0000=a01.a0000(+) " + 
				" order by t.sortid");	
		this.pageQuery(sb.toString(), "SQL", start, limit); // 处理分页查询
		return EventRtnType.SPE_SUCCESS;
	}
	
	//点击名单列表
	@PageEvent("editgrid.rowclick")
	@GridDataRange
	public int editgridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String delid = (String)this.getPageElement("editgrid").getValueList().get(this.getPageElement("editgrid").getCueRowIndex()).get("id");
		this.getPageElement("delid").setValue(delid);
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("savePerInfo")
	@Transaction
	public int savePerInfo() throws Exception{
		String userid=SysManagerUtils.getUserId();
		Historyper historyper=new Historyper();
		String a0000 = this.getPageElement("a0000").getValue();
		String a0101=this.getPageElement("a0101").getValue();
		String a0192a=this.getPageElement("a0192a").getValue();
		String a0104=this.getPageElement("a0104").getValue();
		String a0117=this.getPageElement("a0117").getValue();
		String a0107=this.getPageElement("a0107").getValue();
		String a0111a=this.getPageElement("a0111a").getValue();
		String a0134=this.getPageElement("a0134").getValue();
		String a0144=this.getPageElement("a0144").getValue();
		String a0141=this.getPageElement("a0141").getValue();
		String a0221=this.getPageElement("a0221").getValue();
		String qrzxlxw=this.getPageElement("qrzxlxw").getValue();
		String qrzxlxwxx=this.getPageElement("qrzxlxwxx").getValue();
		String zzxlxw=this.getPageElement("zzxlxw").getValue();
		String zzxlxwxx=this.getPageElement("zzxlxwxx").getValue();
		String a0196=this.getPageElement("a0196").getValue();		
		String a0192f=this.getPageElement("a0192f").getValue();
		String remark=this.getPageElement("remark").getValue();
		String mdid = this.getPageElement("mdid").getValue();
		String id=this.getPageElement("id").getValue();
		String a0184=this.getPageElement("a0184").getValue();
		String sortid=this.getPageElement("sortid").getValue();
		
		historyper.setA0000(a0000);
		historyper.setA0101(a0101);
		historyper.setA0192a(a0192a);
		historyper.setA0104(a0104);
		historyper.setA0117(a0117);
		historyper.setA0107(a0107);
		historyper.setA0111a(a0111a);
		historyper.setA0134(a0134);
		historyper.setA0144(a0144);
		historyper.setA0141(a0141);
		historyper.setA0221(a0221);
		historyper.setQrzxlxw(qrzxlxw);
		historyper.setQrzxlxwxx(qrzxlxwxx);
		historyper.setZzxlxw(zzxlxw);
		historyper.setZzxlxwxx(zzxlxwxx);
		historyper.setA0196(a0196);
		historyper.setA0192f(a0192f);
		historyper.setRemark(remark);
		historyper.setMdid(mdid);
		historyper.setA0184(a0184);
		if(sortid==null||"".equals(sortid)) {
			@SuppressWarnings("unchecked")
			List<String> sortids = HBUtil.getHBSession().createSQLQuery("select sortid from HISTORYPER where mdid='"+mdid+"' order by sortid desc").list();
			if(sortids.size()>0) {
				if(sortids.get(0)!=null) {
					sortid=Integer.valueOf(String.valueOf(sortids.get(0)))+1+"";
				}		
			}else {
				sortid="1";
			}
		}
		historyper.setSortid(Integer.valueOf(sortid));
//		this.copyElementsValueToObj(historyper, this);
		
		if(StringUtils.isEmpty(mdid) || "".equals(mdid)){
			this.setMainMessage("请选择人员名单！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtils.isEmpty(a0101) || "".equals(a0101)){
			this.setMainMessage("请输入姓名！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = null;
		try{
			sess = HBUtil.getHBSession();
			if(StringUtils.isEmpty(id)) {
				historyper.setA0188(userid);
				historyper.setId(UUID.randomUUID().toString());
				
				sess.save(historyper);
			}else {
				historyper.setId(id);
				List<String> userids = HBUtil.getHBSession().createSQLQuery("select a0188 from HISTORYPER where id='"+id+"'").list();
				historyper.setA0188(userids.get(0));
				sess.update(historyper);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','保存失败！',null,'220')");
			return EventRtnType.FAILD;
		}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("delper")
	@Transaction
	public int delper() throws Exception{
		Grid grid = (Grid) this.getPageElement("editgrid");
		List<HashMap<String, Object>> list = grid.getValueList();
		List<String> selected = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> item = list.get(i);
			if (item.get("checked").equals(true)) {
				String id = item.get("id") == null ? "" : item.get("id").toString();
				selected.add(id);
			}
		}
		if (selected.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.FAILD;
		} else {
			String sql = "delete from historyper where id = ?";
			PreparedStatement ps = null;
			PreparedStatement ps2 = null;
			try {
				ps = HBUtil.getHBSession().connection().prepareStatement(sql);
				for (int i = 0; i < selected.size(); i++) {
					ps.setString(1, selected.get(i));
					ps.addBatch();
				}
				ps.executeBatch();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','删除成功！',null,150);");
				grid.reload();
			} catch (SQLException e) {
				e.printStackTrace();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','删除失败！',null,150);");
				return EventRtnType.FAILD;
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delmd")
	@Transaction
	public int delmd() throws Exception{
		String mdid=this.getPageElement("mdid").getValue();
		String username = SysManagerUtils.getUserName();
		String MDCJR=this.getPageElement("MDCJR").getValue();
		try{
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("delete HISTORYMD where mdid=?",new Object[]{mdid});
			HBUtil.executeUpdate("delete HISTORYPER where mdid=?",new Object[]{mdid});
			HBUtil.executeUpdate("delete HZ_LSMD_USERREF where mdid=?",new Object[]{mdid});
			this.getPageElement("mdid").setValue("");
		}catch (Exception e) {
			e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','删除失败！',null,'220')");
			return EventRtnType.FAILD;
		}
//		if(StringUtils.isEmpty(et00)){
//			et00 = UUID.randomUUID().toString();
//			HBUtil.executeUpdate("insert into EVALUATION_TARGET(et00,et01,et02,et03,et04,et05,etc00) values(?,?,?,?,?,seq_sort.nextval,?)",
//					new Object[]{et00,et01,et02,et03,et04,etc00id});
//		}else{
//			HBUtil.executeUpdate("update EVALUATION_TARGET set et01=?,et02=?,et03=?,et04=? where et00=?",
//					new Object[]{et01,et02,et03,et04,et00});
//		}
		
		this.setMainMessage("删除成功！");
		this.setNextEventName("mdgrid.dogridquery");
		this.setNextEventName("editgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("editgrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update HISTORYPER set sortid = ? where id=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String id = m.get("id");
				ps.setInt(1, i);
				ps.setString(2, id);
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
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editgrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("editgrid");
		String id = grid6.getValue("id") +"";
		String remark = grid6.getValue("remark") +"";
		HBSession sess = HBUtil.getHBSession();
		
		sess.createSQLQuery("update HISTORYPER set remark=? where id=?")
		.setString(0, remark).setString(1, id).executeUpdate();
		sess.flush();
		this.setNextEventName("editgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("mdgrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int mdAfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("mdgrid");
		String mdid = grid6.getValue("mdid") +"";
		String mdmc = grid6.getValue("mdmc") +"";
		String remark = grid6.getValue("remark") +"";
		String locked = grid6.getValue("locked") +"";
		String mduserid = grid6.getValue("userid") +"";
		String mdtype = grid6.getValue("mdtype") +"";
		String bqtype = grid6.getValue("bqtype") +"";
		HBSession sess = HBUtil.getHBSession();
		String userid = SysUtil.getCacheCurrentUser().getId();
		if(!mduserid.equals(userid)) {
			this.setMainMessage("该名单只能由创建人进行修改！");
			this.setNextEventName("mdgrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(!"03".equals(mdtype)&&!"".equals(bqtype)) {
//			this.setMainMessage("不是标签名单无法选标签类型！");
//			this.setNextEventName("mdgrid.dogridquery");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(!"03".equals(mdtype)) {
			bqtype="";
		}
		sess.createSQLQuery("update HISTORYMD set mdmc=?,remark=?,locked=?,mdtype=?,bqtype=? where mdid=?")
		.setString(0, mdmc).setString(1, remark).setString(2, locked).setString(3, mdtype).setString(4, bqtype).setString(5, mdid).executeUpdate();
		sess.flush();
		this.getPageElement("locked").setValue(locked);
		this.setNextEventName("mdgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("openrmb")
	@GridDataRange
	public int openrmb(String a0000) throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();

	
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			String rmbs=this.getPageElement("rmbs").getValue();
			/*if(rmbs.contains(a0000)) {
				this.setMainMessage("已经打开了");
				return EventRtnType.FAILD;
			}*/
			this.getPageElement("rmbs").setValue(rmbs+"@"+a0000);
			this.getExecuteSG().addExecuteCode("var rmbWin=window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');var loop = setInterval(function() {if(rmbWin.closed) {clearInterval(loop);removeRmbs('"+a0000+"');}}, 500);");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
	@PageEvent("updateSort")
	@GridDataRange
	public int updateSort(String mdid) throws RadowException, AppException{ 
		
		String uuid=UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15);
		
		HBSession hbsess = HBUtil.getHBSession();	
		try {
			Statement  stmt = hbsess.connection().createStatement();
			String sql="create table HISTORYPER_"+uuid+" as select id,rownum sortid from (select id from HISTORYPER where mdid='"+mdid+"'  order by nvl(sortid,999999)) ";
			stmt.executeUpdate(sql);
			sql="update HISTORYPER a set a.sortid=(select b.sortid from HISTORYPER_"+uuid+" b where a.id=b.id) where mdid='"+mdid+"' ";
			stmt.executeUpdate(sql);
			sql="drop table HISTORYPER_"+uuid;
			stmt.executeUpdate(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setNextEventName("editgrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sqRow")
	@GridDataRange
	public int sqRow(String mdid) throws RadowException, AppException{  //打开窗口的实例
		this.getPageElement("mnur01_combotree").setValue("");
		this.getPageElement("mnur01").setValue("");
		String userid=SysManagerUtils.getUserId();
		this.getPageElement("mdid").setValue(mdid);
		List mdlist = HBUtil.getHBSession().createSQLQuery("select 1 from historyMd y where userid='"+userid+"' and mdid='"+mdid+"'").list();
		if(mdlist.size()==0){
			this.setMainMessage("无法授权！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql = "select listagg (T.mnur01, ',') WITHIN GROUP (ORDER BY mnur05) k,  "
				+ "listagg (T.mnur04, ',') WITHIN GROUP (ORDER BY mnur05) v "
				+ "from hz_LSMD_userref t where mdid='"+mdid+"' and mnur02='"+userid+"'";

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
		String mdid=this.getPageElement("mdid").getValue();
		try {
			HBUtil.executeUpdate("delete from hz_LSMD_userref where mdid=? and mnur02=?",new Object[]{mdid,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into hz_LSMD_userref(mnur00,mdid,mnur01,mnur02,mnur04,mnur05) "
							+ "values(sys_guid(),?,?,?,?,?)",new Object[]{mdid,mnur01s[i],curuserid,mnur01_combotrees[i],i});
					HBUtil.executeUpdate("delete from hz_LSMD_userref where mnur01=mnur02 and mdid='"+mdid+"'");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setMainMessage("授权成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("combine")
	@GridDataRange
	public int combine() throws RadowException, AppException{  //打开窗口的实例
		this.getPageElement("mdid_HB").setValue("");
		this.getPageElement("mdmc_HB").setValue("");
		CommQuery cqbs=new CommQuery();
		String userid=SysManagerUtils.getUserId();
		String sql="select mdid,mdmc "
				+ " from historyMd y where userid='"+userid+"'"
				+ " or exists (select 1 from hz_LSMD_userref u where mnur01='"+userid+"' and u.mdid=y.mdid)";
		List<HashMap<String, Object>> list;
		HashMap<String, Object> map=new LinkedHashMap<String, Object>();
		try {
			list = cqbs.getListBySQL(sql);
			for(int i=0;i<list.size();i++){
				String mdid = list.get(i).get("mdid").toString();
				String mdmc = list.get(i).get("mdmc").toString();
				map.put(mdid, mdmc);
			}
			((Combo)this.getPageElement("mdid_HB")).setValueListForSelect(map);
		}catch (AppException e) {
			e.printStackTrace();
		}
		
		this.getExecuteSG().addExecuteCode("openHBWin();");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveHBInfo")
	@GridDataRange
	public int saveHBInfo() throws RadowException, AppException{ 
		String userid=SysManagerUtils.getUserId();
		CommQuery cqbs=new CommQuery();
		String mdids=this.getPageElement("mdid_HB").getValue();
		String newmdmc=this.getPageElement("mdmc_HB").getValue();
		String newmdid=UUID.randomUUID().toString().replace("-", "");
		String mdid_HB=mdids.replace(",", "','");

		try {
			if(newmdmc ==null ||"".equals(newmdmc)||mdids ==null ||"".equals(mdids)) {
				this.setMainMessage("合并名单以及新名单名不能为空！");
				return EventRtnType.NORMAL_SUCCESS;
			}

			//判断有无重复人员
//			String sql="select a0000 from historyper where a0000 is not null and mdid in ('"+mdid_HB+"') " + 
//					" group by a0000 having count(1)>1";
//			List<String> list=HBUtil.getHBSession().createSQLQuery(sql).list();
//			if(list.size()>0) {
//				StringBuffer cfry=new StringBuffer();
//				for(int i=0;i<list.size();i++) {
//					@SuppressWarnings("unchecked")
//					List<String> a0101s= HBUtil.getHBSession().createSQLQuery("select a0101 from a01 where a0000='"+list.get(i)+"'").list();
//					if(a0101s.size()>0) {
//						cfry.append(a0101s.get(0));
//						if(i!=list.size()-1) {
//							cfry.append("，");
//						}
//					}
//				}
//				this.setMainMessage("合并失败！，存在重复人员"+cfry);
//				return EventRtnType.NORMAL_SUCCESS;
//			}else {
				SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createdate = sf.format(System.currentTimeMillis());
				
				String mdtype=this.getPageElement("mdtype_HB").getValue();
				String bqtype=this.getPageElement("bqtype_HB").getValue();
				HBUtil.executeUpdate("insert into historymd(mdid,mdmc,userid,createdate,mdtype,bqtype) values('"+newmdid+"','"+newmdmc+"','"+userid+"','"+createdate+"','"+mdtype+"','"+bqtype+"')");
//				HBUtil.executeUpdate("insert into historyper select sys_guid() id, t.a0000,t.a0101,t.a0192a,t.a0104,t.a0117,t.a0107,t.a0111a,t.a0134, " + 
//						" t.a0144,t.qrzxlxw ,t.qrzxlxwxx ,t.zzxlxw ,t.zzxlxwxx,t.a0196,t.a0192f,t.remark,'"+newmdid+"' mdid,null sortid,t.a0184,t.a0221,t.a0141,t.a1705,t.a0193  " + 
//						" from historyper t where mdid in('"+mdid_HB+"')");
				
				HBUtil.executeUpdate("insert into historyper "
						+ "  select sys_guid() id, t.a0000,t.a0101,t.a0192a,t.a0104,t.a0117,t.a0107,t.a0111a,t.a0134, "
						+ " t.a0144,t.qrzxlxw ,t.qrzxlxwxx ,t.zzxlxw ,t.zzxlxwxx,t.a0196,t.a0192f,t.remark,'"+newmdid+"',1 sortid,t.a0184,t.a0221,t.a0141,t.a1705,t.a0193,'"+userid+"'  "
						+ "	 from (select * from (  select a.*,row_number() over(partition by a.A0000 order by b.createdate  desc) row_number                                                                                     "
						+ "       from historyper a,historymd b where a.mdid=b.mdid and a.mdid in('"+mdid_HB+"') and a0000 is not null) where row_number=1) t"
						+ " union "
						+ "      select sys_guid() id, t.a0000,t.a0101,t.a0192a,t.a0104,t.a0117,t.a0107,t.a0111a,t.a0134, "
						+ "			 t.a0144,t.qrzxlxw ,t.qrzxlxwxx ,t.zzxlxw ,t.zzxlxwxx,t.a0196,t.a0192f,t.remark,'"+newmdid+"',1 sortid,t.a0184,t.a0221,t.a0141,t.a1705,t.a0193,'"+userid+"' "
						+ "			 from historyper t where mdid in('"+mdid_HB+"') and a0000 is null");
				this.setMainMessage("合并成功！");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setNextEventName("mdgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setDisable")
	public int setDisable(String str) throws Exception {
		if("3".equals(str)){
			this.createPageElement("BQLX", ElementType.SELECT, false).setDisabled(false);
		}else if("4".equals(str)){
			this.createPageElement("BQLX", ElementType.SELECT, false).setDisabled(true);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("setDisablee")
	public int setDisablee(String str) throws Exception {
		if("3".equals(str)){
			this.createPageElement("bqtype_HB", ElementType.SELECT, false).setDisabled(false);
		}else if("4".equals(str)){
			this.createPageElement("bqtype_HB", ElementType.SELECT, false).setDisabled(true);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	// 任免表导出
	@PageEvent("exportLrmxBtn")
	public int expWin() throws RadowException {
		// 判断列表是否有数据
		List<HashMap<String, Object>> list22 = this.getPageElement("editgrid").getValueList();
		if (list22.size() == 0) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
		}
		this.setNextEventName("export");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//导出Lrmx
	@PageEvent("export")
	@GridDataRange
	public int exportLrmx(String time) throws RadowException{
		int i = choose("editgrid", "checked");
		if (i == ON_ONE_CHOOSE) {
			// 没有选中操作全部
			HBSession sess = HBUtil.getHBSession();
			String sql = " select  a0000 from HISTORYPER  where mdid='" + this.getPageElement("mdid").getValue()
					+ "' and a0000 is not null ";
			List<String> list = sess.createSQLQuery(sql).list();
			String a0000 = "";
			if (list != null && list.size() > 0) {
				String zippath = ExpRar.expFile();
				String infile = "";
				String name = "";
				HashMap<String, Integer> renameMap = new HashMap<String, Integer>();
				for (int j = 0; j < list.size(); j++) {
					a0000 = list.get(j);
					PersonXml per = Parenthesisprocessing(a0000, time);
					name = (String) HBUtil.getHBSession()//查询出人名
							.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'").uniqueResult();
					try {
						if (renameMap.containsKey(name)) {
							int num = renameMap.get(name);
							renameMap.remove(name);
							renameMap.put(name, (num + 1));
							name = name + num;
						} else {
							renameMap.put(name, 1);
						}
						FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false, "UTF-8");
						A01 a01log = new A01();
						new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
								new Map2Temp().getLogInfo(new A01(), a01log));
					} catch (Exception e) {
						this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
					}
				}
				try {
					if (i == 1) {
						infile = zippath.substring(0, zippath.length() - 1) + "\\" + name + ".lrmx";
					} else {
						infile = zippath.substring(0, zippath.length() - 1) + ".zip";
						SevenZipUtil.zip7z(zippath, infile, null);
					}
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				} catch (Exception e) {
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}
			}

			return EventRtnType.NORMAL_SUCCESS;

		}
		if (i == CHOOSE_OVER_TOW) {//选中多个
			String a0000 = "";
			PageElement pe = this.getPageElement("editgrid");
			if (pe != null) {
				List<HashMap<String, Object>> list = pe.getValueList();
				String zippath = ExpRar.expFile();
				String infile = "";
				String name = "";
				HashMap<String, Integer> renameMap = new HashMap<String, Integer>();
				Runtime rt = Runtime.getRuntime();
				Process p = null;
				for (int j = 0; j < list.size(); j++) {
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("checked");
					if (usercheck.equals(true)) {
						a0000 = this.getPageElement("editgrid").getValue("a0000", j).toString();
						if (a0000 == null || "".equals(a0000)) {
							this.setMainMessage("新增的人员无法导出lrmx"); // 窗口提示信息
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							PersonXml per = Parenthesisprocessing(a0000, time);
							name = (String) HBUtil.getHBSession()
									.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'")
									.uniqueResult();
							try {
								if (renameMap.containsKey(name)) {
									int num = renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name, (num + 1));
									name = name + num;
								} else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false,
										"UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
										new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
					}
				}
				try {
					infile = zippath.substring(0, zippath.length() - 1) + ".zip";
					SevenZipUtil.zip7z(zippath, infile, null);
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				} catch (Exception e) {
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}
			}
			// System.out.println(ids.toString());
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			String a0000 = "";
			PageElement pe = this.getPageElement("editgrid");
			if (pe != null) {
				List<HashMap<String, Object>> list = pe.getValueList();
				String zippath = ExpRar.expFile();
				String infile = "";
				String name = "";
				HashMap<String, Integer> renameMap = new HashMap<String, Integer>();
				for (int j = 0; j < list.size(); j++) {
					HashMap<String, Object> map = list.get(j);
					Object usercheck = map.get("checked");
					if (usercheck.equals(true)) {
						a0000 = this.getPageElement("editgrid").getValue("a0000", j).toString();
						if (a0000 == null || "".equals(a0000)) {
							this.setMainMessage("新增的人员无法导出lrmx"); // 窗口提示信息
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							PersonXml per = Parenthesisprocessing(a0000, time);
							name = (String) HBUtil.getHBSession()
									.createSQLQuery("select a0101 from a01 t where t.a0000='" + a0000 + "'")
									.uniqueResult();
							try {
								if (renameMap.containsKey(name)) {
									int num = renameMap.get(name);
									renameMap.remove(name);
									renameMap.put(name, (num + 1));
									name = name + num;
								} else {
									renameMap.put(name, 1);
								}
								FileUtil.createFile(zippath + name + ".lrmx", JXUtil.Object2Xml(per, true), false,
										"UTF-8");
								A01 a01log = new A01();
								new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出",
										new Map2Temp().getLogInfo(new A01(), a01log));
							} catch (Exception e) {
								this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
							}
						}
					}
				}
				try {

					infile = zippath + name + ".lrmx";
					this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
					this.getExecuteSG().addExecuteCode("window.downloadword()");
				} catch (Exception e) {
					this.setMainMessage("生成文件错误，请联系管理员"); // 窗口提示信息
				}
			}

		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 私有方法，是否选中用户
	 *
	 * @throws RadowException
	 */

	private int choose(String gridid, String checkId) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get(checkId);
			if (check1 == null) {
				continue;
			}
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// 没有选中任何用户
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// 选中多于一个用户
		}
		if (result == 2) {
			return 1;// 选中多于一个用户
		}
		return number;// 选中第几个用户

	}

	// 转译特殊符号
	public PersonXml Parenthesisprocessing(String a0000, String time) {
		PersonXml per = createLrmxStr(a0000, time);
		if (per.getJianLi() != null && per.getJianLi() != "") {
			per.setJianLi(per.getJianLi().replaceAll("&lt;", "&1lt;"));
			per.setJianLi(per.getJianLi().replaceAll("&gt;", "&1gt;"));
			per.setJianLi(per.getJianLi().replaceAll("&amp;", "&1amp;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != null
				&& per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi() != "") {
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(
					per.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		if (per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != null && per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi() != "") {
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("&", "&1amp;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll("<", "&1lt;"));
			per.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(
					per.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi().replaceAll(">", "&1gt;"));
		}
		return per;
	}

	public PersonXml createLrmxStr(String ids, String time) {
		String a0000 = ids;
		PersonXml a = new PersonXml();
		JiaTingChengYuanXml jiaTingChengYuanXml = new JiaTingChengYuanXml();
		List<JiaTingChengYuanXml> jtcyList = new ArrayList<JiaTingChengYuanXml>();
		List<ItemXml> itemlist = new ArrayList<ItemXml>();
		HBSession sess = HBUtil.getHBSession();
		String sqla36 = "from A36 where a0000='" + a0000 + "' order by sortid," + "case "
				+ "        when A3604A='丈夫' or A3604A='妻子' then 1 "
				+ "        when A3604A='儿子' or A3604A='女儿'or A3604A='长女'or A3604A='长子' or A3604A='次女' or A3604A='次子' or A3604A='三女' or A3604A='三子' or A3604A='四女' or A3604A='四子' or A3604A='五女' or A3604A='五子' or A3604A='其他女儿' or A3604A='其他子' then 2 "
				+ "        when A3604A='父亲'  then 3 " + "        when A3604A='母亲'  then 4 "
				+ "        when A3604A='继父'  then 5 " + "        when A3604A='继母'  then 6 " + "      end   , "
				+ " case " + "    when " + "      A3604A='儿子' or A3604A='女儿' or A3604A='长女'  or "
				+ "      A3604A='长子' or A3604A='次女' or A3604A='次子'  or "
				+ "      A3604A='三女' or A3604A='三子' or A3604A='四女'  or "
				+ "      A3604A='四子' or A3604A='五女' or A3604A='五子'  or " + "      A3604A='其他女儿' or A3604A='其他子' "
				+ "    then   " + "      to_number(GETAGE(A3607)) " + "    end" + "        desc";
		List lista36 = sess.createQuery(sqla36).list();
		String userid = SysUtil.getCacheCurrentUser().getId();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		a01.setQrzxlxx(a01.getQrzxlxx() == null ? "" : a01.getQrzxlxx());
		if (a01.getQrzxlxx().equals(a01.getQrzxwxx())) {
			a01.setQrzxwxx(null);
		}
		A57 a57 = (A57) sess.get(A57.class, a0000);
		String sqla53 = "from A53 where a0000='" + a0000 + "' and a5399='" + userid + "'";
		List<A53> list = sess.createQuery(sqla53).list();
		Object xb = HBUtil.getHBSession()
				.createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB2261' and t.code_value = '"
						+ a01.getA0104() + "'")
				.uniqueResult();
		Object mz = HBUtil.getHBSession()
				.createSQLQuery("select t.code_name from code_value t where t.code_type = 'GB3304' and t.code_value = '"
						+ a01.getA0117() + "'")
				.uniqueResult();
		if (a57 != null) {
			byte[] data = PhotosUtil.getPhotoData(a57);
			if (data != null) {
				a.setZhaoPian(data);
			}
		}
		a.setXingMing(a01.getA0101());
		a.setXingBie(xb != null ? xb.toString() : "");
		a.setChuShengNianYue(a01.getA0107().length() > 6 ? a01.getA0107().substring(0, 6) : a01.getA0107());
		a.setMinZu(mz != null ? mz.toString() : "");
		a.setJiGuan(a01.getComboxArea_a0111());
		a.setChuShengDi(a01.getComboxArea_a0114());
		a.setRuDangShiJian(a01.getA0140());
		a.setCanJiaGongZuoShiJian(a01.getA0134().length() > 6 ? a01.getA0134().substring(0, 6) : a01.getA0134());
		a.setJianKangZhuangKuang(a01.getA0128());
		a.setZhuanYeJiShuZhiWu(a01.getA0196());
		a.setShuXiZhuanYeYouHeZhuanChang(a01.getA0187a());
		a.setQuanRiZhiJiaoYu_XueLi(a01.getQrzxl());
		a.setQuanRiZhiJiaoYu_XueWei(a01.getQrzxw());
		a.setQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getQrzxlxx());
		if (a01.getQrzxlxx() != null && a01.getQrzxlxx().equals(a01.getQrzxwxx())) {
			a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi("");
		} else {
			a.setQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getQrzxwxx());
		}
		a.setZaiZhiJiaoYu_XueLi(a01.getZzxl());
		a.setZaiZhiJiaoYu_XueWei(a01.getZzxw());
		a.setZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi(a01.getZzxlxx());
		if (a01.getZzxlxx() != null && a01.getZzxlxx().equals(a01.getZzxwxx())) {
			a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi("");
		} else {
			a.setZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi(a01.getZzxwxx());
		}
		a.setXianRenZhiWu(a01.getA0192a());
		a.setJianLi(a01.getA1701());
		a.setTianBiaoRen(a01.getCbdw()!= null ? a01.getCbdw() : "");
		/*
		 * hy 据要求，无果为空，改为(无)
		 */
		if (a01.getA14z101() == null || a01.getA14z101().equals("无") || a01.getA14z101().isEmpty())
			a.setJiangChengQingKuang("(无)");
		else
			a.setJiangChengQingKuang(a01.getA14z101());
		if (a01.getA15z101() == null || a01.getA15z101().isEmpty())
			a.setNianDuKaoHeJieGuo("(无)");
		else
			a.setNianDuKaoHeJieGuo(a01.getA15z101());
		a.setShenFenZheng(a01.getA0184());
//		if (list == null || list.size() == 0) {
//			a.setNiRenZhiWu("");
//			a.setNiMianZhiWu("");
//			a.setRenMianLiYou("");
//			a.setChengBaoDanWei("");
//			a.setJiSuanNianLingShiJian("");
//			a.setTianBiaoShiJian(time);
//			a.setTianBiaoRen("");
//		} else {
//			List lista53 = sess.createQuery(sqla53).list();
//			A53 a53 = (A53) lista53.get(0);
//			a.setNiRenZhiWu(a53.getA5304());
//			a.setNiMianZhiWu(a53.getA5315());
//			a.setRenMianLiYou(a53.getA5317());
//			a.setChengBaoDanWei(a53.getA5319());
//			a.setJiSuanNianLingShiJian("");
//			a.setTianBiaoShiJian(time);
//			a.setTianBiaoRen("");
//		}
		if (lista36 != null && lista36.size() > 0) {
			for (int i = 1; i <= lista36.size(); i++) {
				ItemXml b = new ItemXml();
				A36 a36 = (A36) lista36.get(i - 1);
				b.setChengWei(a36.getA3604a() != null ? a36.getA3604a() : "");
				b.setChuShengRiQi(lengthToSix(a36.getA3607()));
				b.setGongZuoDanWeiJiZhiWu(a36.getA3611());
				b.setXingMing(a36.getA3601());
				b.setZhengZhiMianMao(a36.getA3627() != null ? a36.getA3627() : "");
				itemlist.add(b);
			}
		}
		jiaTingChengYuanXml.setItem(itemlist);
		jtcyList.add(jiaTingChengYuanXml);
		a.setJiaTingChengYuan(jtcyList);
		return a;
	}

	private String lengthToSix(String str) {
		if (str != null && !"".equals(str)) {
			if (str.length() == 8) {
				String dateStr = str.substring(0, 6);
				return dateStr;
			} else {
				return str;
			}
		}
		return "";
	}

}

