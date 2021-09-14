package com.insigma.siis.local.pagemodel.train;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Train;

public class TrainMainPageModel extends PageModel{
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		Map<String, Object> map_nd = new LinkedHashMap<String, Object>();
		List list_nd = sess.createSQLQuery("select g11020 from train group by g11020 order by g11020 desc").list();
		for(Object g11020 : list_nd){
			map_nd.put(g11020.toString(), g11020.toString());
		}
		Map<String, Object> map_trainname = new LinkedHashMap<String, Object>();
		map_trainname.put("", "全部班次");
		
		if(list_nd.size()!=0){
			((Combo)this.getPageElement("nd")).setValueListForSelect(map_nd); 
			this.getPageElement("nd_combo").setValue(list_nd.get(0).toString());
			this.getPageElement("nd").setValue(list_nd.get(0).toString());
			List list_trainname = sess.createSQLQuery("select a1131 from train where g11020="+list_nd.get(0)).list();
			for(Object trainname :list_trainname ){
				map_trainname.put(trainname.toString(), trainname.toString());
			}
		}
		/*((Combo)this.getPageElement("trainname")).setValueListForSelect(map_trainname); 
		this.getPageElement("trainname").setValue("");
		this.getPageElement("trainname_combo").setValue("全部班次");*/
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid1.dogridquery")
	@NoRequiredValidate         
	public int grid1Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String nd = this.getPageElement("nd").getValue();
		String trainname = this.getPageElement("trainname").getValue();
		String trainclass = this.getPageElement("trainclass").getValue();
		String mainunit = this.getPageElement("mainunit").getValue();
		String joinnum = this.getPageElement("joinnum").getValue();
		String trainunitclass = this.getPageElement("trainunitclass").getValue();
		String sql = "select * from TRAIN where 1=1 ";
		if(!StringUtils.isEmpty(nd)){
			sql = sql + " and g11020 = '"+nd+"'";
		}
		if(!StringUtils.isEmpty(trainname)){
			sql = sql+" and a1131 like '%"+trainname+"%'";
		}
		
		if(!StringUtils.isEmpty(trainclass)){
			sql = sql+" and a1101 = '"+trainclass+"'";
		}
		
		if(!StringUtils.isEmpty(mainunit)){
			sql = sql+" and a1114 = '"+mainunit+"'";
		}
		
		if(!StringUtils.isEmpty(joinnum)){
			if("$".equals(joinnum)){
				sql = sql +" and g11023>100";
			}else{
				String num1 = joinnum.split(",")[0];
				String num2 = joinnum.split(",")[1];
				sql = sql+" and g11023>"+num1+" and g11023<"+num2;
			}
		}
		if(!StringUtils.isEmpty(trainunitclass)){
			sql = sql+" and a1127 = '"+trainunitclass+"'";
		}
		sql = sql+ " order by g11020 desc,a1131";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("grid2.dogridquery")
	@NoRequiredValidate         
	public int grid2Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String trainid=this.getPageElement("train_id").getValue();
		String pname=this.getPageElement("seachName").getValue();
		String pga0184=this.getPageElement("pga0184").getValue();
		/*String g11027 = "";
		if(!StringUtils.isEmpty(pg11027)){
			StringBuilder sb_g11027 = new StringBuilder();
			for(String str : pg11027.split(",")){
				sb_g11027.append("'"+str+"',");
			}
			sb_g11027.deleteCharAt(sb_g11027.length()-1);
			g11027 = "("+sb_g11027.toString()+")";
			System.out.println(pg11027);
			if (pg11027.equals("1")) {
				g11027=" in ('1','1A','1A01','1A02','1A11','1A12','1A21','1A22','1A31')";
			}
			if (pg11027.equals("2")) {
				g11027=" not in ('1','1A','1A01','1A02','1A11','1A12','1A21','1A22','1A31')";
			}
		}*/
		//String sql = "select a.personnelid,a.trainid,a.a0101,a.a0104,a.a0184,b.a0192a,a.a1108 from TRAIN_PERSONNEL a,a01 b where a.trainid='"+trainid+"' and a.a0184=b.a0184 ";
		
		String sql = "select a.personnelid,a.trainid,a.a0101,a.a0104,a.a0184,b.a0192a,a.g11021 from (select b.*,c.g11021 from TRAIN_PERSONNEL b,TRAIN c where b.trainid=c.trainid) a  left join a01 b on a.a0184=b.a0184  and b.a0163='1'  where a.trainid='"+trainid+"'   ";
		
		
		if(!StringUtils.isEmpty(pname)){
			sql = sql+" and a.a0101 like '%"+pname+"%' ";
		}
		if(!StringUtils.isEmpty(pga0184)){
			sql = sql+" and a.a0184= '"+pga0184+"' ";
		}
		sql = sql+" order by a.a0101";
		System.out.println(sql);
		this.pageQueryByAsynchronous(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("grid3.dogridquery")
	@NoRequiredValidate         
	public int grid3Query(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String trainid = this.getPageElement("train_id").getValue();
		String sql = "select * from TRAIN_LEADER where trainid='"+trainid+"' order by A0177,G11027";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@Transaction
	@PageEvent("deleteTrain")
	@NoRequiredValidate         
	public int deleteTrain(String trainid) throws RadowException, AppException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		Train t = new Train();
		t.setTrainid(trainid);
		sess.delete(t);
		sess.flush();
		this.getExecuteSG().addExecuteCode("deleteCallBack();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Transaction
	@PageEvent("deletePerson")
	@NoRequiredValidate         
	public int deletePerson() throws RadowException, AppException, PrivilegeException{
		StringBuffer checkIds = new StringBuffer();
		PageElement pe = this.getPageElement("grid2");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("pcheck");
				if(usercheck.toString().equals("true")){
					checkIds.append("'").append(map.get("personnelid").toString()).append("',");
				}
			}
		}
		if(checkIds.length() == 0){
			this.setMainMessage("请一个勾选需要删除的人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String personnelid = checkIds.toString();
		personnelid = personnelid.substring(0, personnelid.length()-1);
		HBSession session = HBUtil.getHBSession();
		session.createSQLQuery("delete from train_personnel where personnelid in ("+personnelid+")").executeUpdate();
		this.getExecuteSG().addExecuteCode("deleteCallBack_p();");
		return EventRtnType.NORMAL_SUCCESS;
	}
		
	@Transaction
	@PageEvent("changeNd")
	@NoRequiredValidate         
	public int changeNd() throws RadowException, AppException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		String nd = this.getPageElement("nd").getValue();
		Map<String, Object> map_trainname = new LinkedHashMap<String, Object>();
		map_trainname.put("", "全部班次");
		List list_trainname = sess.createSQLQuery("select a1131 from train where g11020="+nd).list();
		for(Object trainname :list_trainname ){
			map_trainname.put(trainname.toString(), trainname.toString());
		}
		((Combo)this.getPageElement("trainname")).setValueListForSelect(map_trainname); 
		this.getPageElement("trainname").setValue("");
		this.getPageElement("trainname_combo").setValue("全部班次");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Transaction
	@PageEvent("QueryData")
	@NoRequiredValidate         
	public int clearg11027() throws RadowException, AppException, PrivilegeException{
		/*this.getPageElement("seachName").setValue("");
		this.getPageElement("pg11027").setValue("");
		this.getPageElement("pg11027_combo").setValue("");
		HBSession sess = HBUtil.getHBSession();
		Map<String, Object> map_g11027 = new LinkedHashMap<String, Object>();
		List<Object[]> list = sess.createSQLQuery("select code_value,code_name from CODE_VALUE where code_type='TrainZB09'").list();
		for(Object[] o : list){
			map_g11027.put(o[0].toString(), o[1].toString());
		}
		((Combo)this.getPageElement("pg11027")).setValueListForSelect(map_g11027);*/
		this.setNextEventName("grid2.dogridquery");
		/*this.setNextEventName("grid3.dogridquery");*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Transaction
	@PageEvent("openTrain")
	@NoRequiredValidate         
	public int openTrain(String trainid) throws RadowException, AppException, PrivilegeException{
		HBSession sess = HBUtil.getHBSession();
		List list_g11052 = sess.createSQLQuery("select g11052 from TRAIN_ATT where trainid='"+trainid+"'").list();
		if(list_g11052.size()>0){
			this.getExecuteSG().addExecuteCode("openTrain1('"+trainid+"');");
		}else{
			this.getExecuteSG().addExecuteCode("openTrain2('"+trainid+"');");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}