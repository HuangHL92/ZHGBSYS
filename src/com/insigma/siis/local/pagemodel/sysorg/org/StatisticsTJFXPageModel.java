package com.insigma.siis.local.pagemodel.sysorg.org;

import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;

public class StatisticsTJFXPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		String SQ005 = SysManagerUtils.getUserId();
		String sql="select SQ001,SQ002,SQ003,SQ004 from statistics_query where (sq005 = '"+SQ005+"' or sq005 = '1')";
		List<Object[]> Objects=HBUtil.getHBSession().createSQLQuery(sql).list();
		for(Object[] obj:Objects){
			String id=(String) obj[0];
			String name=(String) obj[1];
			String i=(String) obj[2];
			String SQ004=(String) obj[3];
			this.getExecuteSG().addExecuteCode("addChild ('"+i+"','"+name+"','"+id+"','"+SQ004+"')");
		}
		return 0;
	}
	
	@PageEvent("closeBtn.onclick")
	public int close() throws RadowException{
		this.closeCueWindow("cytjfxWin");
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("deleteBtn.onclick")
	public int delete() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String queryid=this.getPageElement("checknodeid").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		String qc_sql = "DELETE FROM query_condition WHERE SQ001 = '"+SQ001+"'";
		sess.createSQLQuery(qc_sql).executeUpdate();
		String sq_sql = "DELETE FROM statistics_query WHERE SQ001 = '"+SQ001+"'";
		sess.createSQLQuery(sq_sql).executeUpdate();
		//this.getExecuteSG().addExecuteCode("Ext.msg.alert(删除成功);");
		this.setMainMessage("删除成功");
		this.reloadPageByYes();
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("tjBtn.onclick")
	public int starttj() throws RadowException{
		String groupid=this.getRadow_parent_data();//机构id
		String queryid=this.getPageElement("checknodeid").getValue();
		String[] Str=queryid.split(",");
		String Stype=Str[1];
		String SQ001=Str[0];
		if( "6".equals(SQ001) ||  "7".equals(SQ001) ||  "8".equals(SQ001)){
			this.setNextEventName("fixed_query");
			return EventRtnType.NORMAL_SUCCESS;
		}else if(SQ001.length()<5){
			this.setNextEventName("gdcy_query");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("1".equals(Stype)){
			this.getExecuteSG().addExecuteCode("tjfx('"+groupid+"\\$"+SQ001+"')");
		}
		if("2".equals(Stype)){
			HBSession sess = HBUtil.getHBSession();
			groupid = groupid.replace("|", "'");			
			String querysql = "SELECT a01.a0000 FROM a01,a02,a08,competence_userdept cu "
							+ "WHERE a01.a0000 = a02.a0000 "
							+ "and not exists (select 1 from COMPETENCE_USERPERSON cu "
							+ "				   where cu.a0000 = a01.a0000 "
							+ "				   and cu.userid = '"+SysManagerUtils.getUserId()+"' ) "
							+ "and a02.A0201B=cu.B0111 "
							+ "and cu.userid = '"+SysManagerUtils.getUserId()+"' "
							+ "and a01.a0000=a08.a0000 "
							+ "and a02.a0255 = '1'  "
							+ "and a01. STATUS = '1' ";
			Integer vert_num = 0;
			Integer tran_num = 0;
			String vert_name = "";
			String tran_name = "";
			String data = "";
			String querysql_do = null;
			String querysql_con = null;
			Integer maxvalue = 0;
			String sql = "select qc003 from query_condition where sq001 = '"+SQ001+"'";
			String qc003 = sess.createSQLQuery(sql).uniqueResult().toString();
			String[] qc003_arr = qc003.split("\\|");
			String vert_data = qc003_arr[0];
			String tran_data = qc003_arr[1];
			String[] vert_data_arr = vert_data.split("@");
			String[] tran_data_arr = tran_data.split("@");
			for(int i=1;i<vert_data_arr.length;i++){
				String vert_i = vert_data_arr[i];
				if(vert_i !=null && vert_i.length()>0){
					String vert_i_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+vert_i+"' ";
					List<Object[]>  list_vert = HBUtil.getHBSession().createSQLQuery(vert_i_sql).list();
					Object[] oo_vert=null;
					if(list_vert != null && ! list_vert.isEmpty()){
						oo_vert = list_vert.get(0);
						querysql_con = querysql + oo_vert[1]+" ";
						vert_name += oo_vert[0]+"@";
						vert_num += 1;
					}else{
						this.setMainMessage("数据异常");
					}
					for(int j=1;j<tran_data_arr.length;j++){
						String tran_j = tran_data_arr[j];
						if(tran_j !=null && tran_j.length()>0){
							String tran_j_sql = "select ti002,ti003 from TWODSTATISTICSINFO_ITEM where ti001 = '"+tran_j+"' ";
							List<Object[]>  list_tran = HBUtil.getHBSession().createSQLQuery(tran_j_sql).list();
							Object[] oo_tran=null;
							if(list_tran != null && ! list_tran.isEmpty()){
								oo_tran = list_tran.get(0);
								querysql_do = querysql_con + oo_tran[1];
								if(vert_num==1){
									tran_name += oo_tran[0]+"@";
									tran_num += 1;
								}
							}else{
								this.setMainMessage("数据异常");
							}
							String number = "";
							if(DBUtil.getDBType()==DBType.ORACLE){
								//number = sess.createSQLQuery("select count(1) from ( "+tj+" )").uniqueResult().toString();
								number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+")").uniqueResult().toString();
							}else{
								//number = sess.createSQLQuery("select count(1) from ( "+tj+" ) as t").uniqueResult().toString();
								number = sess.createSQLQuery("select count(1) from ( "+querysql_do+groupid+") as t").uniqueResult().toString();
							}
							data += number + "@";
							if(Integer.parseInt(number)>maxvalue){
								maxvalue = Integer.parseInt(number);
							}
						}
					}
					if(data.length()>0 && data.substring(data.length()-1).equals("@")){
						data = data.substring(0,data.length()-1);
						data += "#";
					}
				}
			}	
			/*String pass1 = "tran_name:'"+tran_name.substring(0,tran_name.length()-1)+"','tran_num':'"+tran_num+"',"
						+ "'vert_name':'"+vert_name.substring(0,vert_name.length()-1)+"','vert_num':'"+vert_num+"',"
						+ "'data':'"+data.substring(0,data.length()-1)+""; */
			String pass = tran_name.substring(0,tran_name.length()-1)+"$"+tran_num+"$"
						+vert_name.substring(0,vert_name.length()-1)+"$"+vert_num+"$"
						+data.substring(0,data.length()-1)+"$"+maxvalue;
			this.getExecuteSG().addExecuteCode("tjfx2('"+pass+"');");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("editorBtn.onclick")
	public int editorBtn() throws RadowException{
		String groupid=this.getRadow_parent_data();
		//获取树的id（查询条件id+统计类型）
		String queryid=this.getPageElement("checknodeid").getValue();
		String[] Str=queryid.split(",");
		String Stype=Str[1];
		String SQ001=Str[0];
		if("1".equals(Stype)){
			this.getExecuteSG().addExecuteCode("tytj('"+groupid+"@"+SQ001+"')");
		}
		if("2".equals(Stype)){
			String sql = "select QC001,QC002,QC003 from query_condition where sq001 = '"+SQ001+"'";
			List<Object[]> Objects=HBUtil.getHBSession().createSQLQuery(sql).list();
			String name = "";
			String str = "";
			String qc001 = "";
			for(Object[] obj:Objects){
				qc001=(String) obj[0];
				name=(String) obj[1];
				str=(String) obj[2];
			}
			String param = name+"#"+str+"#"+groupid+"#"+qc001;
			this.getExecuteSG().addExecuteCode("ewedit('"+param+"')");
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("fixed_query")
	public int fixed_query() throws RadowException{
		String groupid = this.getRadow_parent_data();
		String queryid=this.getPageElement("checknodeid").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		this.getExecuteSG().addExecuteCode("fixed_query('"+groupid+"\\$"+SQ001+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("gdcy_query")
	public int gdcy_query() throws RadowException{
		String groupid = this.getRadow_parent_data();
		String queryid=this.getPageElement("checknodeid").getValue();
		String[] Str=queryid.split(",");
		String SQ001=Str[0];
		this.getExecuteSG().addExecuteCode("tjfx('"+groupid+"\\$"+SQ001+"\\$gdcy')");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
