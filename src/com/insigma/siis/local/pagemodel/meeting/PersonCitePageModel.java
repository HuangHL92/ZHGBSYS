package com.insigma.siis.local.pagemodel.meeting;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;
import java.util.UUID;
import net.sf.json.JSONArray;

public class PersonCitePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		try {
			String userid = SysManagerUtils.getUserId();
			String sql = "select meetingid,meetingname from "
			+ "(select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where userid='"+userid+"'"
			+ " union "
			+ "  select meetingid,meetingname,meetingtype,time,userid from meetingtheme a where "
			+ "		meetingid in (select b.meetingid from publish b,publishuser c where b.publishid=c.publishid and c.userid='"+userid+"' and c.ischange='1')"
			+ "	) t order by time desc";
			
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("meetingid").toString();
				mapCode.put(tp0116, listCode.get(i).get("meetingname").toString());
			}
			((Combo)this.getPageElement("meetingname")).setValueListForSelect(mapCode);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		return 0;
	}
	
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String titleid=this.getPageElement("titlename").getValue();
			String sql = "select a.a0000,a.a0101,a.a0104,a0107,a.a0192a,a.sh000,a.yy_flag from "
				+ "(select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000,a.sh001,'2' yy_flag from hz_sh_a01 a where titleid='"+titleid+"' "
				+ "			union select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000,b.sh001,'1' yy_flag from hz_sh_a01 a,personcite b where a.sh000=b.sh000 and b.titleid_new='"+titleid+"' "
				+ "		) a order by sh001,a0101";
			
//			String sql = "select a.a0000,a.a0101,a.a0104,substr(a.a0107,1,4)||'.'||substr(a.a0107,5,2) a0107,a.a0192a,a.sh000 from hz_sh_a01 a where  a.titleid='"+titleid+"'";

			this.pageQuery(sql, "SQL", start, 1000);

        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	
	
	//添加
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		
		
		
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		String gridcqdata = JSONArray.fromObject(list).toString();
		
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
		
		
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部添加
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			addlist.add(hm);
		}
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(list).toString());
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//移除
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck2")!=null&&!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				addlist.add(hm);
			}
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部移除
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 保存
	 */  
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException {
		String publishid=this.getPageElement("publishid").getValue();
		String titleid=this.getPageElement("titleid").getValue();
		String agendaname=this.getPageElement("agendaname").getValue();
		String titlename=this.getPageElement("titlename").getValue();
		
		
		List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		if(publishid.equals(agendaname)) {
			this.setMainMessage("被引用的议题与引用到的议题不能相同");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(agendaname==null||"".equals(agendaname)) {
			this.setMainMessage("被引用的议题不能为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(titlename==null||"".equals(titlename)) {
			this.setMainMessage("被引用的标题不能为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		if(listSelect.size()<1){
			this.setMainMessage("请选择人员");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String sql="";
		HBSession sess = HBUtil.getHBSession();
		try {
			MeetingByNamePageModel mb=new MeetingByNamePageModel();
			int i=Integer.valueOf(mb.getMax_sort(titleid,sess))+1;
			
			Statement stmt = sess.connection().createStatement();
//			String condititon="";
			for (HashMap<String, Object> hm : listSelect) {
//				condititon=condititon+"'"+hm.get("sh000")+"',";
				sql="delete from personcite where  titleid_new='"+titleid+"'  and publishid_new='"+publishid+"' and sh000='"+hm.get("sh000")+"'";
				stmt.executeUpdate(sql);
				sql="insert into personcite (titleid_old,titleid_new,sh000,publishid_old,publishid_new,a0000,sh001)  select titleid,'"+titleid+"',sh000,publishid,'"+publishid+"',a0000,'"+i+"' from hz_sh_a01 where sh000='"+hm.get("sh000")+"'";
				stmt.executeUpdate(sql);
				i++;
			}
//			condititon=condititon.substring(0,condititon.length()-1);
			stmt.close();
			
			String xtitlename = this.getPageElement("xtitlename").getValue();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"引用人员","personcite",user.getId(),xtitlename, new ArrayList());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("saveCallBack();");
		
		return EventRtnType.NORMAL_SUCCESS;
		
		
		
	}
	
	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name=name+"2";
		}
		return name;
	}
	//清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 更新议题下拉选
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updatepublish")
	@Transaction
	public int updatepublish() throws RadowException {
		String meetingname = this.getPageElement("meetingname").getValue();
		String userid = SysManagerUtils.getUserId();
		CommQuery cqbs=new CommQuery();
		String sql="select *  from publish where meetingid='"+meetingname+"' and (publishid in (select publishid from publishuser where ischange='1' and userid='"+userid+"') or userid='"+userid+"') order by to_number(sort) ";
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("publishid").toString();
				mapCode.put(tp0116, listCode.get(i).get("agendaname").toString());
			}
			((Combo)this.getPageElement("agendaname")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 更新标题下拉选
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updatetitle")
	@Transaction
	public int updatetitle() throws RadowException {
		String agendaname = this.getPageElement("agendaname").getValue();
		CommQuery cqbs=new CommQuery();
		String sql="select * from hz_sh_title where publishid='"+agendaname+"' order by title02,to_number(sortid) ";
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(sql);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				String tp0116 = listCode.get(i).get("titleid").toString();
				mapCode.put(tp0116, listCode.get(i).get("title01").toString());
			}
			((Combo)this.getPageElement("titlename")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
