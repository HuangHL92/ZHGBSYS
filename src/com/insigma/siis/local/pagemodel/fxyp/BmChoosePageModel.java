package com.insigma.siis.local.pagemodel.fxyp;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fr.stable.StringUtils;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;

public class BmChoosePageModel extends PageModel {


	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException {
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 展示查询人员
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException, AppException{
		// String p1600 = this.getPageElement("p1600").getValue();	// 工作任务主键
		String b0111 = this.getPageElement("b0111").getValue();	// 部门职责的责任单位
		String stype = this.getPageElement("stype").getValue();//查询类型
		String colsm = this.getPageElement("colsm").getValue(); //查询条件
		UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
		String userid = user.getId();
		//SmtUser su = (SmtUser)UtilBS.queryById(SmtUser.class, userid);
		
		//String paramSql = "";
		/*
		 * if(StringUtils.isEmpty(b0111)) { String userid = SysManagerUtils.getUserId();
		 * paramSql =
		 * " (a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"
		 * +userid+"') or "+AppConfig.IGNORE_PERMISSIONS+") "; } else { // paramSql =
		 * " a0201b like '"+b0111+"%' "; paramSql = " SUBSTR(a02.A0201B, 0, " +
		 * b0111.length() + ") = '" + b0111 + "' "; }
		 */
//		String sql = "select  a.a0000, a.a0101, a.a0104, a.a0184, a.a0192a ,a.a0163 from A01 a "
//				+ " where "
//				+ " a.a0195 <> '-1' "
//				+ " and a.a0163 = 1 "
//				// + " and a.a0000 in (select a0000 from a02 where 1=1 and " + paramSql + "  )"
//				+ " and exists "
//				+ " (select 1 from a02 where "
//				+ "		 1=1 and a02.a0000 = a.a0000"
//				// + "		 and a02.a0279 = '1' "
//				+ "		 and " + paramSql + ") ";
		String sql = "select distinct a.a0000 , a.a0101 , a.a0104 ,a.a0184,a.a0192a ,a.a0163 " +
				" from A01 a,A02 b where a.a0163 = 1 and b.a0255='1' " +
				" and  a.a0000 =b.a0000 and substr(b.a0201b, 1, 11) in ('001.001.002','001.001.003','001.001.004') "+
		        " and  b.a0281='true' and substr(b.a0201b, 1, 15)='"+b0111+"'";
		
		
		 if(!StringUtils.isEmpty(stype)&&!StringUtils.isEmpty(colsm)){
			 if(stype.equals("name")){ 
				 sql += " and a.a0101 like '"+colsm+"%' "; 
		  }else if(stype.equals("idCard")){ 
			  sql += " and a.a0184 = '"+colsm+"' "; 
		  	} 
		  }else{
			  sql+= "order by (((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' )  t where rn=1 and t.a0000=a.a0000)))"; 
		  }
		this.pageQuery(sql,"SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	/**
	 * 选人
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save.onclick")
	public int save_onclick() throws RadowException {
		List<HashMap<String,Object>> list = this.getPageElement("peopleInfoGrid").getValueList();
		String checkedgroupid=this.getPageElement("b0111").getValue();
		String g=checkedgroupid.substring(0,11);
		HBSession sess = HBUtil.getHBSession();
		StringBuffer bdgb=new StringBuffer();
		StringBuffer bdgba=new StringBuffer();
		int count = 0;	// 选中的人员数
		// 获取所有的勾选的人员数据 拼接
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				count ++;
				bdgb.append(String.valueOf(map.get("a0101"))+",");
				bdgba.append(String.valueOf(map.get("a0000"))+",");
				
			}
			
		}
		if(bdgb.length()>0){
			bdgb.deleteCharAt(bdgb.length()-1);
			bdgba.deleteCharAt(bdgba.length()-1);
		}
		// 未勾选人员 提示
		if(count == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="";
		try {
			//int i=Integer.valueOf(getMax_sort(bz00,sess))+1;
			Statement stmt = sess.connection().createStatement();
			if(g.equals("001.001.002")) {
				sql="update BZFX set bdgb='',bdgba=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update BZFX set bdgb='"+bdgb+"', bdgba='"+bdgba+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}else if(g.equals("001.001.003")) {
				sql="update GQBZFX set bdgb='',bdgba=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update GQBZFX set bdgb='"+bdgb+"', bdgba='"+bdgba+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}else if(g.equals("001.001.004")) {
				sql="update QXSBZFX set bdgb='',bdgba=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update QXSBZFX set bdgb='"+bdgb+"', bdgba='"+bdgba+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.close();");
		this.getExecuteSG().addExecuteCode("parent.queryPerson();");
		return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * 获取已经存在和需要删除的人员A0000数据
	 * @param a0000s
	 * @param p18List
	 * @return
	 */
	private Map<String, String> isExistOrDelA0000(String a0000s, List<String> p18List) {
		String existA0000s = "";
		String delA0000s = "";
		for (String a0000 : p18List) {
			if ("".equals(a0000)) {
				continue;
			}
			if (a0000s.contains(a0000 + ",")) {
				existA0000s += a0000 + ",";
			} else {
				delA0000s += a0000 + ",";
			}
		}
		// 已存在的人员主键，需保留数据
		if (!"".equals(existA0000s)) {
			existA0000s = existA0000s.substring(0, existA0000s.length() - 1);
		}
		// 原先存在的人员数据，现需去除
		
		  if (!"".equals(delA0000s)) { 
			  delA0000s = delA0000s.substring(0,delA0000s.length() - 1); 
		  }
		 
		Map<String, String> map = new HashMap<String, String>();
		map.put("existA0000s", existA0000s);
		map.put("delA0000s", delA0000s);
		return map;
	}

}
