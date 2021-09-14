package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class JudgeRepeatPageModel extends PageModel{

	public JudgeRepeatPageModel() {
	}

	@Override
	public int doInit() throws RadowException {
		this.getPageElement("isnotchecked").setValue("1");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 列表双击事件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("DbClick_grid")
	public int DbClick_grid(String type) throws RadowException{
		
		//获取前台grid数据
		Grid grid = (Grid)this.getPageElement("repeatInfogrid");
		List<HashMap<String, Object>> list=grid.getValueList();
		if(list==null||list.size()==0){
			this.setMainMessage("请双击机构树查询!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String groupid="";
		
		for(int i=0;i<list.size();i++){
			groupid=groupid+(String)list.get(i).get("b0111")+",";
		}
		
		
		groupid=groupid.substring(0, groupid.length()-1);//取出，号
		
		String pardata = "";
		String ctxPath = request.getContextPath();
		
		String arr[]=type.split(",");
		pardata=arr[1]+","+groupid;
		request.getSession().setAttribute("unitidDbclAlter", pardata);//
		this.getExecuteSG().addExecuteCode("$h.openWin('updateOrgWin','pages.sysorg.org.UpdateSysOrg','机构信息修改页面',860,510,'','"+ctxPath+"');");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询
	 * @return
	 */
	@PageEvent("search.onclick")
	public int search(){
		this.setNextEventName("repeatInfogrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 列表分页显示
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("repeatInfogrid.dogridquery")
	public int dogridQuery(int start,int limit) throws RadowException, AppException{
		String  b0114=this.getPageElement("b0114").getValue();//机构编码
		if(b0114!=null&&b0114.length()>68){
			return EventRtnType.NORMAL_SUCCESS;
		}
		StringBuffer sb=new StringBuffer();
		String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();
		CommQuery cq=new CommQuery();
		if(DBType.ORACLE==DBUtil.getDBType()){
			sb.append(" select "
					+ " t1.b0111, "
					+ " t1.b0101, "
					+ " t1.b0104, "
					+ " t1.b0114, "
					+ " t1.b0180, "
					+ " (SELECT B0101 FROM B01 WHERE B0111 = t1.b0121) AS b0121, "
					+ " (case "
					+ " when t1.b0194 = '1' then "
					+ " '法人单位' "
					+ " when t1.b0194 = '2' then "
					+ " '内设机构' "
					+ " when t1.b0194 = '3' then "
					+ " '机构分组' "
					+ " else "
					+ " '' "
					+ " end) b0194, "
					+ " rank() over(partition by t1.b0194 order by t1.b0114) ranking  "
					+ " from b01 t1 where t1.b0114 in "
					+ " ( select s1.b0114 from "
					+ " (select t.b0114 from  b01 t, competence_userdept c "
					+ " where c.b0111 = t.b0111 "
					+ " and c.userid = '"+cueUserid+"') s1, "
					+ " (select k.b0114 "
					+ " from b01 k where k.b0114 is not null and length(trim(b0114))>0 ");
			if(b0114==null||"".equals(b0114.trim())){
				
			}else{
				sb.append( " and k.b0114='"+b0114+"' " );
			}
			sb.append(" group by k.b0114 "
					+ " having count(K.b0114) > 1) s2 where  s1.b0114=s2.b0114 )" );
			String checked=this.getPageElement("isnotchecked").getValue();
			if("1".equals(checked)){
				String sql=" select count(t.b0111) count from  b01 t, competence_userdept c "
						+ " where c.b0111 = t.b0111 and c.userid = '"+cueUserid+"' "
						+ " and (t.b0114 is null or length(trim(t.b0114))=0 )";
				List<HashMap<String, Object>> list=cq.getListBySQL(sql);
				String sql1=" select count(t.b0111) count from  b01 t "
						+ " where t.b0111!='-1' and "
						+ " (t.b0114 is null or length(trim(t.b0114))=0 )";
				List<HashMap<String, Object>> list1=cq.getListBySQL(sql1);
				if(list!=null&&Double.parseDouble((String)list.get(0).get("count"))>0&&list1!=null&&Double.parseDouble((String)list1.get(0).get("count"))>1){
					sb.append(" union all select  "
						+ " t1.b0111, "
						+ " t1.b0101, "
						+ " t1.b0104, "
						+ " t1.b0114, "
						+ " t1.b0180, "
						+ " (SELECT B0101 FROM B01 WHERE B0111 = t1.b0121) AS b0121, "
						+ " (case "
						+ " when t1.b0194 = '1' then "
						+ " '法人单位' "
						+ " when t1.b0194 = '2' then "
						+ " '内设机构' "
						+ " when t1.b0194 = '3' then "
						+ " '机构分组' "
						+ " else "
						+ " '' "
						+ " end) b0194, "
						+ " rank() over(partition by t1.b0194 order by t1.b0114) ranking  "
						+ " from b01 t1 where t1.b0111!='-1' and ( t1.b0114 is null or length(trim(t1.b0114))=0 ) ");
				}
			}
			
		 }else if(DBType.MYSQL==DBUtil.getDBType()){
			 sb.append(" select * from ( select  "
						+ " t1.b0111, "
			 			+ " t1.b0101, "
						+ " t1.b0104, "
						+ " t1.b0114, "
						+ " t1.b0180, "
						+ " (SELECT B0101 FROM B01 WHERE B0111 = t1.b0121) AS b0121, "
						+ " (case "
						+ " when t1.b0194 = '1' then "
						+ " '法人单位' "
						+ " when t1.b0194 = '2' then "
						+ " '内设机构' "
						+ " when t1.b0194 = '3' then "
						+ " '机构分组' "
						+ " else "
						+ " '' "
						+ " end) b0194 "
						+ " from b01 t1 where t1.b0114 in "
						+ " ( select s1.b0114 from "
						+ " (select t.b0114 from  b01 t, competence_userdept c "
						+ " where c.b0111 = t.b0111 "
						+ " and c.userid = '"+cueUserid+"') s1, "
						+ " (select k.b0114 "
						+ " from b01 k where k.b0114 is not null and length(trim(k.b0114))>0 ");
			 if(b0114==null||"".equals(b0114.trim())){
					
			}else{
				sb.append( " and k.b0114='"+b0114+"' " );
			}
			sb.append(" group by k.b0114 "
					+ " having count(K.b0114) > 1 ) s2 where  s1.b0114=s2.b0114 ) order by t1.b0114 ) ts  " );
			String checked=this.getPageElement("isnotchecked").getValue();
			if("1".equals(checked)){
				String sql=" select count(t.b0111) count from  b01 t where t.b0111 in (select b0111 from competence_userdept where userid = '"+cueUserid+"')  "
						+ " and (t.b0114 is null or length(trim(t.b0114))=0 ) ";
				String sql1=" select count(t.b0111) count from  b01 t where  t.b0111!='-1' and "
						+ " (t.b0114 is null or length(trim(t.b0114))=0 ) ";
				List<HashMap<String, Object>> list=cq.getListBySQL(sql);
				List<HashMap<String, Object>> list1=cq.getListBySQL(sql1);
				if(list!=null&&Double.parseDouble((String)list.get(0).get("count"))>0&&list1!=null&&Double.parseDouble((String)list1.get(0).get("count"))>1){
					sb.append(" union all ( select "
								+ " t1.b0111, "
								+ " t1.b0101, "
								+ " t1.b0104, "
								+ " t1.b0114, "
								+ " t1.b0180, "
								+ " (SELECT B0101 FROM B01 WHERE B0111 = t1.b0121) AS b0121, "
								+ " (case "
								+ " when t1.b0194 = '1' then "
								+ " '法人单位' "
								+ " when t1.b0194 = '2' then "
								+ " '内设机构' "
								+ " when t1.b0194 = '3' then "
								+ " '机构分组' "
								+ " else "
								+ " '' "
								+ " end) b0194 "
						+ " from b01 t1 where t1.b0111!='-1' and    "
							+ " (t1.b0114 is null or length(trim(t1.b0114))=0 ) )");
				}
			
			}
		 }
		this.pageQuery(sb.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}

}
