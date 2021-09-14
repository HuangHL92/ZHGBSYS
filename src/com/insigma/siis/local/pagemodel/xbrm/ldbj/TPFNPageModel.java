package com.insigma.siis.local.pagemodel.xbrm.ldbj;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.JsYjtj;

public class TPFNPageModel extends PageModel {
	
	
	public static String jiaoyan = "0";//0是打开 1是点击
	
	@Override
	public int doInit() throws RadowException {
//		System.setProperty("sun.net.client.defaultConnectTimeout", "120000");
//		System.setProperty("sun.net.client.defaultReadTimeout", "120000");
		this.setNextEventName("viewListGrid2.dogridquery");
		this.setNextEventName("viewListGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 保存条件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveschemename")
	@Transaction
	public int saveschemename() throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			//视图名
			String chinesename=this.getPageElement("queryName").getValue();
			//生成的sql
//			String qrysql=this.getPageElement("qrysql").getValue();
//			if(qrysql!=null){
//				qrysql=qrysql.replaceAll("@@", "\'");
//			}
			if(chinesename==null||chinesename.length()==0){
				this.setMainMessage("请输入查询名称!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String userid=PrivilegeManager.getInstance().getCueLoginUser().getId();

			//String yjtype=this.getPageElement("yjtype").getValue();//预警类型
			String qvid=this.getPageElement("qvid").getValue();//主键
			if(qvid!=null&&qvid.length()>0){//更新
				JsYjtj jsyjtj=(JsYjtj)session.get(JsYjtj.class, qvid);
				//视图名
				jsyjtj.setChinesename(chinesename);
				
				session.update(jsyjtj);
				//jsyjtj.setYjtype(yjtype);
				session.flush();
			}else{//保存
				JsYjtj jsyjtj=new JsYjtj();
				//视图名
				jsyjtj.setChinesename(chinesename);
				//创建时间
				SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
				Date date=new Date();
				String createtime=sdf.format(date);
				jsyjtj.setCreatetime(createtime);
				//视图类型1 自定义视图2 组合视图 3复杂视图 4自定义方案
				jsyjtj.setType("4");
				//用户id
				jsyjtj.setUserid(userid);
				//jsyjtj.setYjtype(yjtype);
				session.save(jsyjtj);
				
				session.flush();
				qvid=jsyjtj.getQvid();
			}
			this.getExecuteSG().addExecuteCode("savescheall('"+qvid+"');");
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("viewListGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		String yf000=this.getPageElement("yf000").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username =SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			instr="";
		}else{
			//instr="  and ( userid='"+userid+"'  or qvid in (select t.viewid from COMPETENCE_USERQRYVIEW t where t.userid = '"+userid+"' and t.type = '1' ) ) ";
		}
		String sql="select nvl2(r.qvid,1,0) pcheck,q.yjtype,"
				+ "  q.qvid, "       //主键
				+ " type, "        //视图类型1 自定义视图2 组合视图 3复杂视图
				+ " viewname, "    //视图名称
				+ " chinesename, " //中文名
				+ " q.uses, "         //用途
				+ " funcdesc "    //功能描述
		+ " from JS_YJTJ q left join JS_YJTJ_ref_fn r on q.qvid=r.qvid and r.yf000='"+yf000+"' "
		+ " where 1=1   "
		+ instr
		//+ " and type='4' "
		+ " order by createtime asc ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 双击已建视图行记录
	 * copy视图信息到页面
	 * @return
	 */
	@PageEvent("savetoqv")
	public int savetoqv(String qvid) throws RadowException{
		try{
			HBSession session = HBUtil.getHBSession();
			JsYjtj jsyjtj = (JsYjtj)session.get(JsYjtj.class, qvid);
			//中文名
			this.getPageElement("queryName").setValue(jsyjtj.getChinesename());
			//主键
			this.getPageElement("qvid").setValue(qvid);
			this.getPageElement("qrysql").setValue(jsyjtj.getQrysql());
			//this.getPageElement("yjtype").setValue(jsyjtj.getYjtype()==null?"0":jsyjtj.getYjtype());
			//清空设置条件tab页面的全局js变量
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.clearConditionPar();");
			//传递视图主键到iframe页面
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.setQvId('"+qvid+"');");
			//加载信息集列表
			this.getExecuteSG().addExecuteCode("document.getElementById('iframeCondition').contentWindow.initList();");
			//判断是否已经定义sql
			this.getExecuteSG().addExecuteCode("odin.ext.getCmp('tab').activate('tab1');");
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveInfo")
	public int saveInfo(String txt) throws RadowException{
		
		String yf000=this.getPageElement("yf000").getValue();
		String yf004=this.getPageElement("fnlb2").getValue();//方案类别 人员预警 和 人员筛选
		
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = HBUtil.getHBSession().connection();
			conn.setAutoCommit(false);
			if(yf000==null||"".equals(yf000)){//新增
				String uuid = UUID.randomUUID().toString();
				String sql = "insert into JS_YJTJ_fn(yf000,yf002,yf003,yf004) values(?,?,deploy_classify_dc004.nextval,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, txt);
				pst.setString(3, yf004);
				pst.executeUpdate();
				yf000 = uuid;
				
				//conn.commit();
			}else{
				String sql = "delete from JS_YJTJ_ref_fn where yf000=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, yf000);
				pst.executeUpdate();
				sql = "update JS_YJTJ_fn set yf002=?,yf004=? where yf000=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, txt);
				pst.setString(2, yf004);
				pst.setString(3, yf000);
				pst.executeUpdate();
				//conn.commit();
			}
			this.setNextEventName("viewListGrid2.dogridquery");
			pst.close();
			Grid viewListGrid2 = (Grid)this.getPageElement("viewListGrid");
			List<HashMap<String, Object>> viewListGrid2list=viewListGrid2.getValueList();
			if(viewListGrid2list.size()>0){
				String sql = "insert into JS_YJTJ_ref_fn(qvid, yf000 ) values(?,?)";
				pst = conn.prepareStatement(sql);
				for(int i=0;i<viewListGrid2list.size();i++){
					
					HashMap<String,Object> m = viewListGrid2list.get(i);
					String pcheck = m.get("pcheck")==null?"":m.get("pcheck").toString();
					if("true".equals(pcheck)||"1".equals(pcheck)){
						pst.setString(1, m.get("qvid").toString());
						pst.setString(2, yf000);
						pst.addBatch();
					}
					
				}
				pst.executeBatch();
			}
			
			conn.commit();
			this.getPageElement("yf000").setValue(yf000);
			this.getPageElement("yf002").setValue(txt);
			this.toastmessage("保存成功！");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
			this.setMainMessage("保存失败！");
		}finally {
			try {
				if(conn!=null)
					conn.close();
				if(pst!=null)
					pst.close();
			} catch (SQLException e1) {
			}
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("viewListGrid2.dogridquery")
	public int viewListGrid2(int start,int limit) throws RadowException, AppException{
		String sql="select yf000,yf002,yf004 from JS_YJTJ_fn q  order by yf004, yf003 asc ";
		this.pageQuery(sql, "SQL", start,limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("delFN")
	public int delInfo(String s) throws RadowException{
		String yf000=this.getPageElement("yf000").getValue();
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = HBUtil.getHBSession().connection();
			conn.setAutoCommit(false);
			if(yf000!=null&&!"".equals(yf000)){//
				String sql = "delete from JS_YJTJ_ref_fn where yf000=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, yf000);
				pst.executeUpdate();
				pst.close();
				pst = conn.prepareStatement("delete from JS_YJTJ_fn where yf000=?");
				pst.setString(1, yf000);
				pst.executeUpdate();
				pst.close();
			}
			
			
			
			conn.commit();
			this.toastmessage("删除成功！");
			this.getPageElement("yf000").setValue("");
			this.getPageElement("yf002").setValue("");
			this.setNextEventName("viewListGrid2.dogridquery");
			this.setNextEventName("viewListGrid.dogridquery");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
			this.setMainMessage("删除失败！");
		}finally {
			try {
				if(conn!=null)
					conn.close();
				if(pst!=null)
					pst.close();
			} catch (SQLException e1) {
			}
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}