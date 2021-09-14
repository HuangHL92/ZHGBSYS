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

public class TrainScorePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Map<String, Object> map_nd = new LinkedHashMap<String, Object>();
		List list_nd = sess.createSQLQuery("select g11020 from train group by g11020 order by g11020 desc").list();
		for(Object g11020 : list_nd){
			map_nd.put(g11020.toString(), g11020.toString());
		}
		((Combo)this.getPageElement("nd")).setValueListForSelect(map_nd); 
		this.getPageElement("nd_combo").setValue(list_nd.get(0).toString());
		this.getPageElement("nd").setValue(list_nd.get(0).toString());
		this.setNextEventName("scoreGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("scoreGrid.dogridquery")
	@NoRequiredValidate         
	public int scoreGridQuery(int start,int limit) throws RadowException, AppException, PrivilegeException{
		String nd = this.getPageElement("nd").getValue();
		String a0101 = this.getPageElement("seachName").getValue();
		String g11027 = this.getPageElement("seachG11027").getValue();
		String g11039 = this.getPageElement("seachG11039").getValue();
		String sql = "select * from Train_Score where g11020='"+nd+"'   ";
		if(!StringUtils.isEmpty(a0101)){
			sql = sql + "  and a0101 like '%"+a0101+"%'  ";
		}
		if(!StringUtils.isEmpty(g11027)){
			g11027 = "("+g11027+")";
			sql = sql + "  and g11027 in "+g11027;
		}
		if(!StringUtils.isEmpty(g11039)){
			sql = sql + "  and g11039 in "+g11039;
		}
		sql = sql+"    order by a1108 desc";
		this.pageQuery(sql, "SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	@Transaction
	@PageEvent("deleteScore")
	@NoRequiredValidate         
	public int deleteScore() throws RadowException, AppException, PrivilegeException{
		StringBuffer checkIds = new StringBuffer();
		PageElement pe = this.getPageElement("scoreGrid");
		if(pe!=null){
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int j=0;j<list.size();j++){
				HashMap<String, Object> map = list.get(j);
				Object usercheck = map.get("pcheck");
				if(usercheck.toString().equals("true")){
					checkIds.append("'").append(map.get("scoreid").toString()).append("',");
				}
			}
		}
		if(checkIds.length() == 0){
			this.setMainMessage("请一个勾选需要删除的人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String scoreid = checkIds.toString();
		scoreid = scoreid.substring(0, scoreid.length()-1);
		HBSession session = HBUtil.getHBSession();
		session.createSQLQuery("delete from train_score where scoreid in ("+scoreid+")").executeUpdate();
		this.getExecuteSG().addExecuteCode("deleteCallBack();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
