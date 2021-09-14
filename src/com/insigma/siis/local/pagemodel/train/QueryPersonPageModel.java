package com.insigma.siis.local.pagemodel.train;

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
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class QueryPersonPageModel extends PageModel {

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
		List list_nd = sess.createSQLQuery("select g11020 from (select g11020 from train group by g11020 union all select g11020 from train_elearning group by g11020 union all select g11020 from Train_Score group by g11020) a where g11020 is not null group by g11020 order by g11020 desc ").list();
		for (Object g11020 : list_nd) {
			map_nd.put(g11020.toString(), g11020.toString());
		}
		((Combo) this.getPageElement("nd")).setValueListForSelect(map_nd);
		this.getPageElement("nd_combo").setValue(list_nd.get(0).toString());
		this.getPageElement("nd").setValue(list_nd.get(0).toString());
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("QueryPersonGrid.dogridquery")
	@NoRequiredValidate
	public int QueryPersonGridQuery(int start, int limit) throws RadowException, AppException, PrivilegeException {
		String nd = this.getPageElement("nd").getValue();
		String nameOrIdCard = this.getPageElement("seachNameOrIdCard").getValue();
		String trainClass = this.getPageElement("seachTrainClass").getValue();
		String sql ="";
		if(StringUtils.isEmpty(trainClass)){
			sql = "select tp.personnelid id,tp.a0101 a0101,tp.a0104 a0104,tp.a0184 a0184,tp.g11027 g11027,tp.a0192a a0192a,'1' trainclass,tp.a1108 a1108,t.a1131 a1131,'' g11042,'' g11039 from train t left join train_personnel tp on t.trainid=tp.trainid where t.g11020="+nd+" and tp.a0101='"+nameOrIdCard+"' or tp.a0184='"+nameOrIdCard+"' "+
					"union all select tl.leacerid id,tl.a0101 a0101,tl.a0104 a0104,tl.a0184 a0184,tl.g11027 g11027,tl.a0192a a0192a,'2' trainclass,tl.a1108 a1108,t.a1131 a1131,'' g11042,'' g11039 from train t left join Train_Leader tl on t.trainid=tl.trainid where t.g11020="+nd+" and tl.a0101='"+nameOrIdCard+"' or tl.a0184='"+nameOrIdCard+"' "+
					"union all select te.elearningid id,te.a0101 a0101,te.a0104 a0104,te.a0184 a0184,te.g11027 g11027,te.a0192a a0192a,'3' trainclass,te.a1108 a1108,'' a1131,te.g11042 g11042,'' g11039 from Train_Elearning te where te.g11020="+nd+" and te.a0101='"+nameOrIdCard+"' or te.a0184='"+nameOrIdCard+"' "+
					"union all select ts. scoreid id,ts.a0101 a0101,ts.a0104 a0104,ts.a0184 a0184,ts.g11027 g11027,ts.a0192a a0192a,'4' trainclass,ts.a1108 a1108,'' a1131,'' g11042,ts.g11039 g11039 from Train_Score ts where ts.g11020="+nd+" and ts.a0101='"+nameOrIdCard+"' or ts.a0184='"+nameOrIdCard+"'";
		}else{
			for(String str : trainClass.split(",")){
				if("1".equals(str)){
					sql = sql+" select tp.personnelid id,tp.a0101 a0101,tp.a0104 a0104,tp.a0184 a0184,tp.g11027 g11027,tp.a0192a a0192a,'1' trainclass,tp.a1108 a1108,t.a1131 a1131,'' g11042,'' g11039 from train t left join train_personnel tp on t.trainid=tp.trainid where t.g11020="+nd+" and tp.a0101='"+nameOrIdCard+"' or tp.a0184='"+nameOrIdCard+"' union all ";
				}else if("2".equals(str)){
					sql = sql+" select tl.leacerid id,tl.a0101 a0101,tl.a0104 a0104,tl.a0184 a0184,tl.g11027 g11027,tl.a0192a a0192a,'2' trainclass,tl.a1108 a1108,t.a1131 a1131,'' g11042,'' g11039 from train t left join Train_Leader tl on t.trainid=tl.trainid where t.g11020="+nd+" and tl.a0101='"+nameOrIdCard+"' or tl.a0184='"+nameOrIdCard+"' union all ";
				}else if("3".equals(str)){
					sql = sql+" select te.elearningid id,te.a0101 a0101,te.a0104 a0104,te.a0184 a0184,te.g11027 g11027,te.a0192a a0192a,'3' trainclass,te.a1108 a1108,'' a1131,te.g11042 g11042,'' g11039 from Train_Elearning te where te.g11020="+nd+" and te.a0101='"+nameOrIdCard+"' or te.a0184='"+nameOrIdCard+"' union all ";
				}else{
					sql = sql+" select ts. scoreid id,ts.a0101 a0101,ts.a0104 a0104,ts.a0184 a0184,ts.g11027 g11027,ts.a0192a a0192a,'4' trainclass,ts.a1108 a1108,'' a1131,'' g11042,ts.g11039 g11039 from Train_Score ts where ts.g11020="+nd+" and ts.a0101='"+nameOrIdCard+"' or ts.a0184='"+nameOrIdCard+"' union all ";
				}
			}
			sql = sql.substring(0, sql.lastIndexOf("union all"));
		}
		/*if("0".equals(trainClass)){
			
		}else if("1".equals(trainClass)){
			
		}else if(){
			
		}
		String sql = 
		String sql = "select * from TRAIN_ELEARNING where g11020='" + nd + "'   ";
		if (!StringUtils.isEmpty(a0101)) {
			sql = sql + "  and a0101 like '%" + a0101 + "%'  ";
		}
		if (!StringUtils.isEmpty(g11027)) {
			g11027 = "(" + g11027 + ")";
			sql = sql + "  and g11027 in " + g11027;
		}
		sql = sql + "    order by a1108 desc";*/
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	public static void main(String[] args) {
		String test = "j21234214213432s abc ";
		int one = test.lastIndexOf("abc");
		System.out.println(test.substring(0, one));
		System.out.println(one);
	}
}