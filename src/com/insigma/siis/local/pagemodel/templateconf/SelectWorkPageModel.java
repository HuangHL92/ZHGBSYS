package com.insigma.siis.local.pagemodel.templateconf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class SelectWorkPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("TrainingInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("TrainingInfoGrid.dogridquery")
	@NoRequiredValidate
	public int TrainingInfoGridQuery(int start,int limit) throws RadowException{
		String value = this.getPageElement("subWinIdBussessId2").getValue();
		if(value!=null&&!"".equals(value)){
			String[] val = value.split("\\$");
			String a0000 = val[0];
			String gid = val[1];

			String sql = "select f.gwcode,f.gwname from Jggwconf f where b0111='" + gid + "' order by gwcode";
		    this.pageQuery(sql, "SQL", start, limit);
		}

		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	public int save() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("TrainingInfoGrid").getValueList();
		int countNum = 0;
		String code = "";
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("personcheck");
			if (check1!= null && check1.equals(true)) {
				code = ""+map.get("gwcode");
				countNum++;
			}
		}
		if(countNum==0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(countNum>1){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','只能选择一条职位！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0000 = "";
		String gid = "";
		String value = this.getPageElement("subWinIdBussessId2").getValue();
		if(value!=null&&!"".equals(value)){
			String[] val = value.split("\\$");
			a0000 = val[0];
			gid = val[1];
		}
		
		HBSession sess = HBUtil.getHBSession();
		//判断该人是否任同一个职位，如果是，提醒
		String sssql = "select a01.a0000 from code_a0215a_c a01 where a01.b0111 = '"+gid+"' and a01.a0215a_c = '"+code+"' and a01.a0000 = '"+a0000+"'";
		Object objss = sess.createSQLQuery(sssql).uniqueResult();
		if(objss!=null&&!"".equals(objss)){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','人员不能重复任同一单位的同一职位！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}

		sssql = "insert into code_a0215a_c select a01.a0000, a01.a0101, a01.a0192a, '"+gid+"', '"+code+"', sys_guid(), '"+SysManagerUtils.getUserId()+"' from a01 "
				+ "where a01.a0000 = '"+a0000+"'";
		sess.createSQLQuery(sssql).executeUpdate();
		
		String sql = "select f.b0111,f.b0101,f.gwnum,f.gwcode,f.gwname from Jggwconf f where b0111='" + gid + "' order by gwcode";
		String b0111 = "";
		String b0101 = "";
		int yp = 0;//应配
		int sp = 0;//实配
		LinkedHashMap<String,List<String>> map = new LinkedHashMap<String , List<String>>();
		
		String jg = "";
		List<Object[]> obj = sess.createSQLQuery(sql).list();
		if(obj!=null&&obj.size()>0){
			for(Object[] objs : obj){
				b0111 = ""+objs[0];
				b0101 = ""+objs[1];
				Object gwnum = objs[2];
				if(gwnum==null||"null".equals(gwnum)||"".equals(gwnum)){
					gwnum = "0";
				}
				yp = yp + Integer.parseInt(""+gwnum);
				/*Object countnum = objs[3];
				if(countnum==null||"null".equals(countnum)||"".equals(countnum)){
					countnum = "0";
				}
				sp = sp + Integer.parseInt(""+countnum);*/
				
				Object gwcode = objs[3];
				Object gwname = objs[4];
				
				String ssql = "select a01.a0000, a01.a0101, a01.a0192a from code_a0215a_c a01 where a01.userid = '"+SysManagerUtils.getUserId()+"' and a01.b0111 = '"+gid+"' and a01.a0215a_c = '"+gwcode+"'";
				
				List<Object[]> persons = sess.createSQLQuery(ssql).list();
				
				List<String> strs = new ArrayList<String>();
				if(persons!=null&&persons.size()>0){
					for(Object[] person : persons){
						sp++;
						String a0000n = ""+person[0];
						String a0101 = ""+person[1];
						String a0192a = ""+person[2];
						String str = a0000n + "@" + a0101 + "@" + a0192a;
						strs.add(str);
					}
				}
				map.put(gwcode+"@"+gwname+"@"+gwnum, strs);
			}
			jg = b0111 + "@" + b0101 + "@" + yp + "@" + sp;
		}
		/*String personSql = "select distinct a.a0000 from Jggwconf f, (select A0000,A0215A_c from a02 "
				+ "where a0255 = '1' and a0201b = '"+gid+"' and A0215A_c is not null) a "
				+ "where f.gwcode = a.A0215A_c and b0111 = '"+gid+"'";//实配人员SQL
*/		
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject);
	    this.getExecuteSG().addExecuteCode("realParent.znzsvalue('"+jg+"','"+jsonObject+"');realParent.reflesh();window.close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
