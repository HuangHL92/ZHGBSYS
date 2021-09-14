package com.insigma.siis.local.pagemodel.gzdb;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.DHXJ;
import com.insigma.siis.local.business.entity.GZDB;
import com.insigma.siis.local.business.entity.HJXJ;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.SQLQuery;

public class MeetingElectionPageModel extends PageModel{

	UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
	
	@Override
	public int doInit() throws RadowException {
		
		  //List list_nd = TrainingCoursePageModel.getYearInToList(); list_nd.remove(0);
		  //Map<String, Object> map_nd = TrainingCoursePageModel.getYearInToMap();
		  //map_nd.remove(""); //((Combo)
		 // this.getPageElement("p3101")).setValueListForSelect(map_nd);
		 // this.getPageElement("p3101_combo").setValue(list_nd.get(0).toString());
		 // this.getPageElement("p3101").setValue(list_nd.get(0).toString());
		 

		this.getExecuteSG().addExecuteCode("Func.init();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("mdgrid.dogridquery")
	@NoRequiredValidate
	public int gridQuery(int start,int limit) throws RadowException,AppException{

		StringBuffer sql=new StringBuffer();
		/*
		 * sql.
		 * append("select distinct decode(xjqy,'001.001.004.001','上城区','001.001.004.004','拱墅区','001.001.004.005','西湖区','001.001.004.006','滨江区','001.001.004.007','萧山区','001.001.004.008','余杭区','001.001.004.00E','临平区','001.001.004.00F','钱塘区','001.001.004.009','富阳区',"
		 * +
		 * "'001.001.004.00A','临安区','001.001.004.00B','桐庐县','001.001.004.00C','淳安县','001.001.004.00D','建德市') xjqy from DHXJ  where 1=1  "
		 * );
		 */
		sql.append("select* from code_value where code_type='QUXZ' ");
		//判断组织部有没有 请销假审核角色
		//UniteUserBs qxjObj = new UniteUserBs();
		//if(!qxjObj.isLeaderRS() ){
		    //  sql.append(" and t.userid = '"+user.getId()+"'");
		//}
		
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
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
		String code_value = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		this.getPageElement("xjqy").setValue(code_value);
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	@PageEvent("memberGrid.dogridquery")
	@NoRequiredValidate
	public int gridDoQuery(int start,int limit) throws RadowException,AppException{
		String xjqy = (String)this.getPageElement("mdgrid").getValueList().get(this.getPageElement("mdgrid").getCueRowIndex()).get("code_value");
		StringBuffer sql=new StringBuffer();
		sql.append("select  t.dhxjid,t.xjqy,t.hylx,t.hymc,t.filename,b.aaa005 || t.FILEURL FILEURL,t.jfilename,b.aaa005 || t.JFILEURL JFILEURL,t.sj,t.hfilename,b.aaa005 || t.HFILEURL HFILEURL" +
				" from DHXJ t,aa01 b where 1=1 and b.aaa001='HZB_PATH' and  t.xjqy='"+xjqy+"' ");
		
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
		sql.append(" order by t.sj desc ");
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	
	/**
	 * 删除当前年休计划表,以及年休计划人员
	 * @param p3200
	 */
	@Transaction
	@PageEvent("delete")
	public int deleteHJXJByhjxjid(String hjxjid){
		HBUtil.getHBSession().createSQLQuery("delete from HJXJ where hjxjid = '"+hjxjid+"'").executeUpdate();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteBtn.onclick")	
	@NoRequiredValidate
	public int delete() throws RadowException{
		
		List<HashMap<String,Object>> list = this.getPageElement("memberGrid").getValueList();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();

		for (HashMap<String, Object> map : list) {
			if(map.get("checked").toString().equals("true")){
				list2.add(map);	
			}
		}
		if(list2.size()==0){
			this.setMainMessage("请选择要删除的换届数据!");
		}else{
			
			for(int i=0;i<list2.size();i++){
				String hjxjid=list2.get(i).get("hjxjid").toString();
				this.addNextEvent(NextEventValue.YES,"delete",hjxjid);
				this.deleHJXJ(hjxjid);
				this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
				this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
				this.setMainMessage("您确实要删除选中的换届数据？");
			}
			this.setNextEventName("memberGrid.dogridquery");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	@PageEvent("addEGLInfo")
	@Transaction
	public int addEGLInfo() throws Exception{
		String xjqy = this.getPageElement("xjqy").getValue();
		String hylx = this.getPageElement("hylx").getValue();
		String hymc = this.getPageElement("hymc").getValue();
		String sj = this.getPageElement("sj").getValue();
		String dhxjid = this.getPageElement("dhxjid").getValue();
		 HBSession sess = HBUtil.getHBSession();
		 DHXJ dhxj = new DHXJ(); // 
		 DHXJ dhxj1 = new DHXJ(); // 
		 String uid = UUID.randomUUID().toString().replaceAll("-", "");
		if(StringUtils.isEmpty(dhxjid)){
			
			dhxj.setXjqy(xjqy);
			dhxj.setHylx(hylx);
			dhxj.setHymc(hymc);
			dhxj.setDhxjid(uid);
			dhxj.setSj(sj);
			sess.save(dhxj);
		}else{
			dhxj1 = (DHXJ) HBUtil.getHBSession().get(DHXJ.class, dhxjid);
			dhxj1.setHylx(hylx);
			dhxj1.setHymc(hymc);
			dhxj1.setSj(sj);
			sess.save(dhxj1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('addEGL').hide();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addEGLInfo1")
	@Transaction
	public int addEGLInfo1() throws Exception{
		String xjqy = this.getPageElement("xjqy").getValue();
		String yjwc = this.getPageElement("yjwc").getValue();
		String ejwc = this.getPageElement("ejwc").getValue();
		String sajwc = this.getPageElement("sajwc").getValue();
		String jd = this.getPageElement("jd").getValue();
		String hjxjid = this.getPageElement("hjxjid").getValue();
		String quxz = this.getPageElement("quxz").getValue();
		 HBSession sess = HBUtil.getHBSession();
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//String Createdon =sdf.format(new Date());
		HJXJ hjxj = new HJXJ(); // 
		HJXJ hjxj1 = new HJXJ(); // 
		if(StringUtils.isEmpty(hjxjid)){
			hjxj.setXjqy(xjqy);
			hjxj.setHjxjid(UUID.randomUUID().toString().replaceAll("-", ""));
			hjxj.setYjwc(yjwc);
			hjxj.setEjwc(ejwc);
			hjxj.setSajwc(sajwc);
			hjxj.setJd(jd);
			hjxj.setCreatedon(new Date());
			if(quxz.equals("区县市换届数据")) {
				hjxj.setQuxz("1");
			}else if(quxz.equals("县乡换届数据")) {
				hjxj.setQuxz("2");
			}
			sess.save(hjxj);
		}else{
			hjxj1 = (HJXJ) HBUtil.getHBSession().get(HJXJ.class, hjxjid);
			hjxj1.setXjqy(xjqy);
			hjxj1.setYjwc(yjwc);
			hjxj1.setEjwc(ejwc);
			hjxj1.setSajwc(sajwc);
			hjxj1.setJd(jd);
			hjxj1.setCreatedon(new Date());
			if(quxz.equals("区县市换届数据")) {
				hjxj.setQuxz("1");
			}else if(quxz.equals("县乡换届数据")) {
				hjxj.setQuxz("2");
			}
			sess.save(hjxj1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('addEGL').hide();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("queryPublish")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select replace(wm_concat(a0215a),',','  ') dwzs from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1001'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish1")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish1(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select replace(wm_concat(a0215a),',','  ') dwzs from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1003'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish2")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish2(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select replace(wm_concat(a0215a),',','  ') dwzs from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1004'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish3")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish3(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select replace(wm_concat(a0215a),',','  ') dwzs from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1005'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish5")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish5(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select count(*) count from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1001'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish6")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish6(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select count(*) count from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1003'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish7")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish7(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select count(*) count from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1004'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	@PageEvent("queryPublish8")
	@NoRequiredValidate
	public List<HashMap<String, Object>> queryPublish8(String xjqy) throws RadowException {
//		String publishname="";
		List<HashMap<String, Object>> list=null;
		try{
			String userid = SysManagerUtils.getUserId();
			CommQuery commQuery =new CommQuery();
			String sql="select count(*) count from(" + 
					"select  " + 
					"               a02.a0215a " + 
					"          from a02,a01,b01 " + 
					"         WHERE a01.A0000 = a02.a0000 and a02.a0201b=b01.b0111" + 
					"           AND a01.a0163 = '1'" + 
					"           and a01.status = '1'" + 
					"           and a02.a0281 = 'true'" + 
					"           AND a02.a0255 = '1'" + 
					"           and a02.a0201b like '"+xjqy+"%'" + 
					"           and b01.b0131 ='1005'" + 
					"           order by a02.a0225)";
			list = commQuery.getListBySQL(sql); 
//			if(list!=null&&list.size()>0) {
//				publishname=list.get(0).get("agendaname").toString();
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	//保存分值
	@PageEvent("saveGrade")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveGrade(String confirm)throws RadowException, AppException{
		String grade = this.request.getParameter("grade");
		String b01id = this.request.getParameter("b01id");	
		String et00 = this.request.getParameter("et00");	
		String etc00 = this.request.getParameter("etc00");	
		String eg00 = this.request.getParameter("eg00");	
		String egl00 = this.request.getParameter("egl00");	
		
		if(!"".equals(grade)){
			try{
				Float.valueOf(grade);
			}catch (Exception e) {
				this.setMainMessage("非法数字:"+grade+"！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		HBSession hbSession = HBUtil.getHBSession();
		List<Object> egList = hbSession.createSQLQuery("select 1 from EVALUATION_grade where eg00=?").setString(0, eg00).list();
		if(egList.size()==0){//新增
			hbSession.createSQLQuery("insert into EVALUATION_grade(eg00,b01id,grade,etc00,et00,egl00) values(?,?,?,?,?,?)")
			.setString(0, eg00).setString(1, b01id).setString(2, grade).setString(3, etc00).setString(4, et00).setString(5, egl00)
			.executeUpdate();
		}else{//更新
			if(StringUtils.isEmpty(grade)){
				hbSession.createSQLQuery("delete from EVALUATION_grade where eg00 ='"+eg00+"'").executeUpdate();
			}else{
				hbSession.createSQLQuery("update EVALUATION_grade set grade = '"+grade+"' where eg00 ='"+eg00+"'").executeUpdate();
			}
			
		}
		
		hbSession.flush();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public void deleHJXJ(String hjxjid){
		HBUtil.getHBSession().createSQLQuery("delete from HJXJ where hjxjid = '"+hjxjid+"'").executeUpdate();
	}
	public void deleRWWCQK(String gzdbid){
		HBUtil.getHBSession().createSQLQuery("delete from RWWCQK where gzdbid = '"+gzdbid+"'").executeUpdate();
	}
	
}
