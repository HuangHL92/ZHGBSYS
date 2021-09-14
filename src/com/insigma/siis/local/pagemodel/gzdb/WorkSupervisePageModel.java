package com.insigma.siis.local.pagemodel.gzdb;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.GZDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

public class WorkSupervisePageModel extends PageModel{

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
	 
	
	@PageEvent("grid1.dogridquery")
	@NoRequiredValidate
	public int gridQuery(int start,int limit) throws RadowException,AppException{
		GZDB gzdb=new GZDB();
		this.copyElementsValueToObj(gzdb,this);

		StringBuffer sql=new StringBuffer();
		sql.append("select  t.gzdbid,t.sxmc,t.jtwt,t.zgmb,t.qtld,t.zrcs,t.zgrw,t.wcsx,t.wcl" +
				",t.wcbz1,t.wcbz2,t.cs001,t.cjr,to_char (t.createdon, 'YYYY-MM-DD') createdon from GZDB t where 1=1 ");
		
		//�ж���֯����û�� ��������˽�ɫ
		//UniteUserBs qxjObj = new UniteUserBs();
		//if(!qxjObj.isLeaderRS() ){
		    //  sql.append(" and t.userid = '"+user.getId()+"'");
		//}
		
		sql.append(" order by t.createdon asc ");
		sql.toString();
		//System.out.println(":::" + sql );
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
		
	}
	/**
	 * ɾ����ǰ���ݼƻ���,�Լ����ݼƻ���Ա
	 * @param p3200
	 */
	@Transaction
	@PageEvent("delete")
	public int deleteGZDBBygzdbid(String gzdbid){
		HBUtil.getHBSession().createSQLQuery("delete from GZDB where gzdbid = '"+gzdbid+"'").executeUpdate();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("deleteBtn.onclick")	
	@NoRequiredValidate
	public int delete() throws RadowException{
		
		List<HashMap<String,Object>> list = this.getPageElement("grid1").getValueList();
		List<HashMap<String,Object>> list2=new ArrayList<HashMap<String,Object>>();

		for (HashMap<String, Object> map : list) {
			if(map.get("checked").toString().equals("true")){
				list2.add(map);	
			}
		}
		if(list2.size()==0){
			this.setMainMessage("��ѡ��Ҫɾ���Ĺ�����������!");
		}else{
			
			for(int i=0;i<list2.size();i++){
				String gzdbid=list2.get(i).get("gzdbid").toString();
				this.addNextEvent(NextEventValue.YES,"delete",gzdbid);
				this.deleGZDB(gzdbid);
				this.deleRWWCQK(gzdbid);
				this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");
				this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
				this.setMainMessage("��ȷʵҪɾ��ѡ�еĹ����������");
			}
			this.setNextEventName("grid1.dogridquery");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	public void deleGZDB(String gzdbid){
		HBUtil.getHBSession().createSQLQuery("delete from GZDB where gzdbid = '"+gzdbid+"'").executeUpdate();
	}
	public void deleRWWCQK(String gzdbid){
		HBUtil.getHBSession().createSQLQuery("delete from RWWCQK where gzdbid = '"+gzdbid+"'").executeUpdate();
	}
	
}
