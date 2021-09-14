package com.insigma.siis.local.pagemodel.fxyp;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.base.CommonSelSQL;
import com.insigma.siis.local.pagemodel.fxyp.base.CommonSelSQLView;
import com.insigma.siis.local.pagemodel.fxyp.base.JZHJ;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class KqzsjzhjViewPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("noticeSetgrid.dogridquery");
		return 0;
	}

	
	
	/**
	 *  查询职数信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("noticeSetgrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		CommonSelSQLView comsql = new CommonSelSQLView(this);
		String sql = comsql.getNoticeSetgrid();
		this.pageQuery(sql, "SQL", start, 300);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 修改人员信息的双击事件
	 *
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("noticeSetgrid.rowdbclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		//获得当前页面是浏览  还是  编辑  机构树
		//String lookOrWrite = this.getPageElement("lookOrWrite").getValue();
		//String mntp00 = this.getPageElement("mntp00").getValue();
		//String b01id=this.getPageElement("noticeSetgrid").getValue("b01id",this.getPageElement("noticeSetgrid").getCueRowIndex()).toString();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("addInfo")
	@Transaction
	public int addInfo(String txt) throws RadowException, AppException{
		
		try {
			//String[] txtArray = txt.split("@@");
			String mntp00 = this.getPageElement("mntp00").getValue();
			String b01id = this.getPageElement("b01idkq").getValue();
			String fxyp06 = this.getPageElement("fxyp06").getValue();
			String a0201e = this.getPageElement("a0201e").getValue();
			String fxyp00 = this.getPageElement("yxgwSel").getValue();
			String fxyp02 = this.getPageElement("yxgwSel_combo").getValue();
			if(fxyp00==null||"".equals(fxyp00)){
				fxyp00 = UUID.randomUUID().toString();
				fxyp02 = this.getPageElement("dwmckqgw").getValue();
				String userID = SysManagerUtils.getUserId();
				HBSession sess = HBUtil.getHBSession();
				HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,a0201e) values(?,?,?,?,?,?,?)",
						new Object[]{fxyp00,fxyp02,userID,fxyp06,b01id,mntp00,a0201e});
				sess.flush();
			}
			this.getExecuteSG().addExecuteCode("doAddPerson.queryByNameAndIDS_JZHJ('"+fxyp00+"');");
			//this.getExecuteSG().addExecuteCode("openRenXuanTiaoJian('"+fxyp00+"');");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//查询，传递人员IDs  放入暂存区
	@PageEvent("queryByNameAndIDS_ZCQ")
	public int queryByNameAndIDS_ZCQ(String a0000s) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		String mntp00 = this.getPageElement("mntp00").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		String Sql = "select t.a0201b,t.a0200,t.a0000,a01.a0101,"
		  		+ " a01.a0192a,t.a0215a,"
		  		+ "nvl((select max('2') from fxyp f where f.a0200=t.a0200 and mntp00='"+mntp00+"'),'34') personStatus,"
		  				+ "'' fxyp07,"
		  		+ " '' zrrx,(select b01id from b01 where b0111=t.a0201b) b01id from a02 t, a01 "
		  		+ " where t.a0000=a01.a0000  and a01.a0000 in('"+a0000s+"')";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(Sql.toString());
		JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
		this.getExecuteSG().addExecuteCode("doAddPerson.addPgridBuffer("+updateunDataStoreObject.toString()+");");

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//传入对应的岗位，传递人员IDs//拟任
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String fxyp00) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		String a0000s = this.getPageElement("a0000s").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
		
		//拟任
		if(a0000s!=null&&!"".equals(a0000s)){//插入岗位人员
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,"
					+ " '' tp0111,'' tp0112,'' tp0113,'' tp0114,'' tp0115,'"+fxyp00+"' fxyp00 from a01 t");
		
			sql.append(" where t.a0000 in ('"+a0000s+"') ");
			sql.append(" and not exists (select 1 from RXFXYP p where p.a0000=t.a0000");
			sql.append(" and fxyp00='"+fxyp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into RXFXYP(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					String maxsortnum = HBUtil.getValueFromTab("max(sortnum)", "RXFXYP", "fxyp00='"+fxyp00+"'");
					int i=Integer.valueOf(maxsortnum==null?"0":maxsortnum);
					ps = conn.prepareStatement(insertsql);
					for(HashMap m : listCode){
						//出生年月处理
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						
						ps.setString(1, m.get("tp0100").toString());
						ps.setString(2, textFormat(m.get("a0000")));
						ps.setString(3, m.get("type").toString());
						ps.setString(4, textFormat(m.get("tp0101")));
						ps.setString(5, textFormat(m.get("tp0102")));
						ps.setString(6, textFormat(m.get("tp0103")));
						ps.setString(7, textFormat(m.get("tp0104")));
						ps.setString(8, textFormat(m.get("tp0105")));
						ps.setString(9, textFormat(m.get("tp0106")));
						ps.setString(10, textFormat(m.get("tp0107")));
						ps.setInt(11, ++i);
						ps.setString(12, fxyp00);
						ps.setString(13, "");
						ps.setString(14, textFormat(m.get("tp0111")));
						ps.setString(15, textFormat(m.get("tp0112")));
						ps.setString(16, textFormat(m.get("tp0115")));
						ps.addBatch();
						
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					
					
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("保存失败！");
					e.printStackTrace();
				}
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
			
			int add = -1;
			//非新增岗位
			if(!"".equals(this.getPageElement("yxgwSel").getValue())){
				add = 0;
			}
			this.getExecuteSG().addExecuteCode("refreshPageData.refresh('"+fxyp00+"',"+(add)+");");
		}else{
			//this.setMainMessage("无法查询到该人员！");
			//return EventRtnType.NORMAL_SUCCESS;
		}
			
		
			
		
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	
	//拟免
	@PageEvent("movePB_by_nm")
	public int movePB_by_nm(String isNMOnly) throws Exception{
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		//拟免
		String mntp00 = this.getPageElement("mntp00").getValue();
		String a0200s = this.getPageElement("a0200s").getValue();
		StringBuffer sql = new StringBuffer();
		if(a0200s!=null&&!"".equals(a0200s)){
			Map<String, String[]> a0200Map = new HashMap<String, String[]>();
			String[] a0200arr = a0200s.split(",");
			a0200s = "";
			for(int i=0;i<a0200arr.length;i++){
				String[] a0200M = a0200arr[i].split(":");
				//以拟免
				if(a0200M[3].indexOf("2")==-1){
					a0200Map.put(a0200M[0], new String[]{a0200M[1],a0200M[2]});
					a0200s += a0200M[0]+",";
				}
				
			}
			a0200s = a0200s.substring(0,a0200s.length()-1);
			a0200s = a0200s.replaceAll(",", "','");
			sql.append("select  sys_guid() tp0100, t.a0000 a0000,"
					+ " '3' type,GET_tpbXingming(t.a0101,t.a0104,t.a0117,t.a0141) tp0101,t.a0107 tp0102,t.a0192f tp0103, t.a0288 tp0104,"
					+ " GET_TPBXUELI2(t.qrzxl,t.zzxl,t.qrzxw,t.zzxw) tp0105,"
					+ " t.a0192a tp0106,'' tp0107,'' tp0108,'' tp0109,'' tp0110,a02.a0215a,a02.a0200,"
					+ " '' tp0111,a02.a0215a tp0112,'' tp0113,'' tp0114,'' tp0115  from a01 t,a02");
		
			sql.append(" where t.a0000=a02.a0000 and a02.a0200 in ('"+a0200s+"') ");
			sql.append(" and not exists (select 1 from FXYP p where p.a0200=a02.a0200");
			sql.append(" and mntp00='"+mntp00+"')");
			try {
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
				if(listCode.size()>0){
					String insertsql = "insert into RXFXYP(tp0100, a0000, type, tp0101, tp0102, tp0103, "
							+ "tp0104, tp0105, tp0106, tp0107, sortnum, fxyp00,tp0116,tp0111,tp0112,tp0115 )"
							+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					
					sess = HBUtil.getHBSession();
					conn = sess.connection();
					conn.setAutoCommit(false);
					ps = conn.prepareStatement(insertsql);
					for(HashMap m : listCode){
						String fxyp00_nm = UUID.randomUUID().toString();
						//minus--;
						//出生年月处理
						String text = this.getFTime(m.get("tp0102"));
						m.put("tp0102",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0104"));
						m.put("tp0104",text);
						//任现职时间处理
						text = this.getFTime(m.get("tp0103"));
						m.put("tp0103",text);
						
						
						
						ps.setString(1, m.get("tp0100").toString());
						ps.setString(2, textFormat(m.get("a0000")));
						ps.setString(3, m.get("type").toString());
						ps.setString(4, textFormat(m.get("tp0101")));
						ps.setString(5, textFormat(m.get("tp0102")));
						ps.setString(6, textFormat(m.get("tp0103")));
						ps.setString(7, textFormat(m.get("tp0104")));
						ps.setString(8, textFormat(m.get("tp0105")));
						ps.setString(9, textFormat(m.get("tp0106")));
						ps.setString(10, textFormat(m.get("tp0107")));
						ps.setInt(11, 1);
						ps.setString(12, fxyp00_nm);
						ps.setString(13, "");
						ps.setString(14, textFormat(m.get("tp0111")));
						ps.setString(15, textFormat(m.get("tp0112")));
						ps.setString(16, textFormat(m.get("tp0115")));
						ps.addBatch();
						
						
						
						String userID = SysManagerUtils.getUserId();
						HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05,fxyp06,b01id,mntp00,fxyp07,a0200) values(?,?,?,?,?,?,-1,?)",new Object[]{fxyp00_nm,textFormat(m.get("a0215a")),userID,a0200Map.get(m.get("a0200"))[0],a0200Map.get(m.get("a0200"))[1],mntp00,textFormat(m.get("a0200"))});
						sess.flush();
					}
					ps.executeBatch();
					conn.commit();
					ps.close();
					//JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
					//System.out.println(updateunDataStoreObject.toString());
					
					if("1".equals(isNMOnly)){
						this.setNextEventName("pgrid.dogridquery");
						this.setNextEventName("pgrid2.dogridquery");
						this.setNextEventName("noticeSetgrid.dogridquery");
					}
				}
			} catch (Exception e) {
				try{
					if(conn!=null)
						conn.rollback();
					if(conn!=null)
						conn.close();
				}catch(Exception e1){
					this.setMainMessage("保存失败！");
					e.printStackTrace();
				}
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private String textFormat(Object v){
		String value = null;
		if(v!=null){
			if("null".equals(v.toString())){
				return null;
			}
			value = v.toString().replace("{/n}", "\n");
		}
		return value;
	}
	private String getFTime(Object tex){
		String text = null;
		if(tex!=null){
			text = tex.toString();
			if(text.length()>=6){
				return text.substring(0, 4)+"."+text.substring(4, 6);
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	/**
	 * 在职人员
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid.dogridquery")
	public int pgrid(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  /*String b01id = this.getPageElement("b01id").getValue();
	  String sql = "select a.a0000,a.a0101,a.a0192a,m.fxyp02 yxgw,r.tp0100 from a01 a,hz_mntp_gw m,rxfxyp r "
	  		+ " where m.b01id='"+b01id+"' and m.fxyp00=r.fxyp00 and m.mntp00='"+mntp00+"' and a.a0000=r.a0000";*/
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//区县领导班子
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
		  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
		  		
		  		+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"

		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ "'"+b01id+"' b01id FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
       
       + "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"
       
       + " a02.a0201e zrrx, '"+b01id+"' b01id"
       +"   from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
       +"    AND a02.a0255 = '1' and a02.a0201e in ('1','3')"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'";
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  /*String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,tp0100,"
			
			+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = t.tp0100 and bz.mntp00='"+mntp00+"' and a01bztype='2') a01bzdesc,"
	  		
	  		+ " t.fxyp06 zrrx,'"+b01id+"' b01id from v_mntp_gw_ry t, a01 "
	  		+ " where t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 ";	*/
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	
	/**
	 * 调配后
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("pgrid2.dogridquery")
	public int pgrid2(int start,int limit) throws RadowException{
	  String mntp00 = this.getPageElement("mntp00").getValue();
	  String b0111 = this.getPageElement("b0111").getValue();
	  String b01id = this.getPageElement("b01id").getValue();
	  
	  String mntp05 = this.getPageElement("mntp05").getValue();
		
	  String ordersql = "((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0') from ( select a02.a0000,b0269,a0225,row_number()over(partition by a02.a0000 order by nvl(a02.a0279,0) desc,b0269) rn from a02,b01 where a02.a0201b=b01.b0111 and a0281 = 'true' and a0201b like '"+b0111+"%')  t where rn=1 and t.a0000=a01.a0000))";

	  
	  String sql = "";
		
	  
	  if("2".equals(mntp05)){//区县领导班子
	  
		  sql = "SELECT a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
		  		+ "nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
		  		+ "(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp07,"
		  		+ "(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) fxyp00,"
		  		+ "(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1) tp0100,"
		  		
		  		+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"

		  		+ " (select b0131 from b01 where b0111=a02.a0201b) zrrx,"
		  		+ "'"+b01id+"' b01id FROM a02, a01 "
		  		+ " WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
		  		+ " and a02.a0201e in('1','3') "
		  		+ " and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') "
		  		+ " and a02.a0201b in "
		  		+ " (select b0111 from b01 b where b.b0131 in('1001','1003','1004','1005','1006','1007') "
		  		+ " and b.b0111 like '"+b0111+".%')"
		  				+ " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";
		  
		  
	  }else if("3".equals(mntp05)||"1".equals(mntp05)||"4".equals(mntp05)){//区县平台
		  sql = "select a02.a0201b,a02.a0200,a01.a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,a01.a0192a,a02.a0215a,"
	   +"nvl((select decode(gw.fxyp07,1,'1',-1,'2','34') from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1),'34') personStatus,"
       +"(select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp07,"
       +"(select gw.fxyp00 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) fxyp00,"
       +"(select gw.tp0100 from v_mntp_gw_ry gw where gw.a0200 = a02.a0200 and gw.mntp00 = '"+mntp00+"' and fxyp07 = -1) tp0100,"
       
       + "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = a02.a0200 and bz.mntp00='"+mntp00+"' and a01bztype='1') a01bzdesc,"
       
       + " a02.a0201e zrrx, '"+b01id+"' b01id"
       +"   from a01, a02 where a01.A0000 = a02.a0000 AND a02.a0281 = 'true'"
       +"    AND a02.a0255 = '1' and a02.a0201e in ('1','3')"
       +"    and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')"
       +"    and a02.a0201b = '"+b0111+"'"
       + " and not exists (select gw.fxyp07 from hz_mntp_gw gw where gw.a0200 = a02.a0200 and gw.mntp00='"+mntp00+"' and fxyp07=-1)";;
	  }
	  
	  
	  sql += " order by " + ordersql;
	  sql = "select * from ("+sql+") a01 ";
	  String unionSql = "select '' a0201b,t.a0200 a0200,t.a0000 a0000,GET_tpbXingming(a01.a0101,a01.a0104,a01.a0117,a01.a0141) a0101,"
	  		+ " a01.a0192a a0192a,t.fxyp02 a0215a,'1' personStatus,t.fxyp07 fxyp07,fxyp00,tp0100,"
			  
			+ "(select bz.a01bzdesc from HZ_MNTP_BZ bz where bz.a01bzid = t.tp0100 and bz.mntp00='"+mntp00+"' and a01bztype='2') a01bzdesc,"
	  		
	  		+ " decode(length(t.fxyp06),4,t.fxyp06,t.a0201e) zrrx,'"+b01id+"' b01id from v_mntp_gw_ry t, a01 "
	  		+ " where t.a0000=a01.a0000 and t.fxyp07=1 and t.mntp00='"+mntp00+"' and t.b01id='"+b01id+"'"
	  				+ " order by t.fxyp00, t.sortnum ";
	  unionSql = " select * from ("+unionSql+") ";
	  sql = "select * from ("+sql+" union all "+unionSql+") a01 ";	
	  this.pageQuery(sql, "SQL", start, limit);
	  return EventRtnType.SPE_SUCCESS;
	}
	
	
	
	
	
	
	
	@PageEvent("DeleteP")
	@Transaction
	public int DeleteP(String tp0100) throws RadowException, AppException{
		try {
			//String[] param = tp0100Info.split("@@");
			//String tp0100 = param[0];
			//String fxyp07 = param[1];
			String mntp00 = this.getPageElement("mntp00").getValue();
			HBSession sess = HBUtil.getHBSession();
			
			Object count = sess.createSQLQuery("select count(1) from rxfxyp where fxyp00="
					+ "(select fxyp00 from rxfxyp where tp0100=?)").setString(0, tp0100).uniqueResult();
			int totalcount =0;
			if(count instanceof BigDecimal){
	  			totalcount = ((BigDecimal)count).intValue();
	  		}else if(count instanceof BigInteger){
	  			totalcount = ((BigInteger)count).intValue();
	  		}
			if(totalcount==1){//拟免肯定是1  拟任如果是1 则删除岗位
				HBUtil.executeUpdate("delete from fxyp where fxyp00="
						+ "(select fxyp00 from rxfxyp where tp0100=?)",new Object[]{tp0100});
				HBUtil.executeUpdate("delete from rxfxyp where tp0100=?",new Object[]{tp0100});
			}else{//拟任是多人选 则删除人选 更新序号
				HBUtil.executeUpdate("update rxfxyp set sortnum=sortnum-1 where fxyp00=(select fxyp00 from rxfxyp where tp0100=?) and sortnum>(select sortnum from  rxfxyp where tp0100=?)",
						new Object[]{tp0100,tp0100});
				HBUtil.executeUpdate("delete from rxfxyp where tp0100=?",new Object[]{tp0100});
			}
			
			HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid=? and a01bztype=? ",
					new Object[]{mntp00,tp0100,"2"});
			sess.flush();
			this.setNextEventName("pgrid.dogridquery");
			this.setNextEventName("pgrid2.dogridquery");
			this.setNextEventName("noticeSetgrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	//初始化组织机构树
	@SuppressWarnings("unchecked")
	@PageEvent("org_config_tree")
	public int org_config_tree() throws PrivilegeException {
		String userid = this.getCurUserid();
		String mntp00 = this.request.getParameter("mntp00");
		String mntp05 = this.request.getParameter("mntp05");
		//String dwmc = this.request.getParameter("dwmc");
		StringBuilder sb_tree = new StringBuilder("[");
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			HBSession sess = HBUtil.getHBSession();
			String sql = "";
			
			if("2".equals(mntp05)){//区县
				  sql = "select s.b01id,s.b0101 jgmc,nvl2(d.b01id,'false','true') from QXSLDBZHZB s"
				  		+ "  left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("1".equals(mntp05)){//市直
				  sql = "select s.b01id,jgmc,nvl2(d.b01id,'false','true') vali from" 
						  +" SZDWHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("4".equals(mntp05)){//国企
				  sql = "select s.b01id,jgmc,nvl2(d.b01id,'false','true') from" 
						  +" GQGXHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }else if("3".equals(mntp05)){//平台
				  
				  sql = "select s.b01id,s.b0101 jgmc,nvl2(d.b01id,'false','true') from" 
						  +" QXSPTHZB s left join hz_mntp_dwref d on s.b01id=d.b01id and d.mntp00='"+mntp00+"' ";
			  }	
			list = sess.createSQLQuery(sql).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for(Object[] o : list){
			String name = o[0].toString();
			//String editor = o[5].toString().toLowerCase();
			//String header = o[2].toString();
			String desc = o[1].toString();
			//String width = o[3].toString();
			//String codeType = o[4]==null?"":o[4].toString();
			//String renderer = o[7]==null?"":o[7].toString();
			sb_tree.append(" {text: '"+desc+"',id:'"+name+"',leaf:true,checked:"+o[2]+"},");
		}
		sb_tree.append("]");
		this.setSelfDefResData(sb_tree.toString());
		return EventRtnType.XML_SUCCESS;
	}
	
	
	@PageEvent("saveOrg_config")
	public int saveOrg_config() throws RadowException {
		String jsonp = this.request.getParameter("jsonp");
		String mntp00 = this.request.getParameter("mntp00");
		String mntp05 = this.request.getParameter("mntp05");
		//System.out.println(jsonp);
		if(jsonp!=null&&!"".equals(jsonp)){
			JSONObject jsonObject = JSONObject.fromObject(jsonp);
			Iterator<String> it = jsonObject.keys();
			
			HBSession sess = HBUtil.getHBSession();
			Connection con = sess.connection();
			try {
				
				con.setAutoCommit(false);
				con.createStatement().execute("delete from hz_mntp_dwref where mntp00='"+mntp00+"' and mntp05='"+mntp05+"'");
				PreparedStatement pstat = con.prepareStatement("insert into hz_mntp_dwref(mntp00,b01id,mntp05) values(?,?,?)");
				// 遍历jsonObject数据，添加到Map对象
				int i = 0;
				while (it.hasNext()) {
					String nodeid = it.next();
					String isvali =  jsonObject.get(nodeid).toString();
					if("false".equals(isvali)){
						pstat.setString(1, mntp00);
						pstat.setString(2, nodeid);
						pstat.setString(3, mntp05);
						pstat.addBatch();
					}
					i++;
				}
				pstat.executeBatch();
				con.commit();
				
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
		this.setMainMessage("保存成功");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("showOrgInfo")
	@Transaction
	public int showOrgInfo() throws RadowException, AppException{
		JZHJ jzhj = new JZHJ(this);
		jzhj.setPageModelParser(this.getPageModelParser());
		return jzhj.showOrgInfo();
	}
	
	/**
	 * 图表数据
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("EChartsInfo")
	@Transaction
	public int EChartsInfo() throws RadowException, AppException{
		JZHJ jzhj = new JZHJ(this);
		jzhj.setPageModelParser(this.getPageModelParser());
		//判断重复
		Map<String,Object> a0000sMap = new HashMap<String, Object>();
		//统计
		Map<String,Object> mapCount = new HashMap<String, Object>();
		//反查
		Map<String,List<String>> reverseSearchMap = new HashMap<String, List<String>>();
		jzhj.getEChartsInfo(a0000sMap,mapCount,reverseSearchMap);
		//专业类型
		jzhj.getEChartsZYLX(a0000sMap,mapCount,reverseSearchMap);
		
		
		//调配前
		//判断重复
		Map<String,Object> a0000sMap2 = new HashMap<String, Object>();
		//统计
		Map<String,Object> mapCount2 = new HashMap<String, Object>();
		//反查
		Map<String,List<String>> reverseSearchMap2 = new HashMap<String, List<String>>();
		jzhj.getEChartsInfo2(a0000sMap2,mapCount2,reverseSearchMap2);
		//专业类型
		jzhj.getEChartsZYLX(a0000sMap2,mapCount2,reverseSearchMap2);
		
		
		Map<String,Map<String,Object>> retData = new HashMap<String, Map<String,Object>>();
		retData.put("data1", mapCount);
		retData.put("data2", mapCount2);
		this.setSelfDefResData(retData);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("setGWInfo")
	@Transaction
	public int test() throws RadowException, AppException{
		
		String mntp00 = this.getPageElement("mntp00").getValue();
		String mntp05 = this.getPageElement("mntp05").getValue();
		String fxyp06 = this.getPageElement("fxyp06").getValue();
		String b01idkq = this.getPageElement("b01idkq").getValue();
		String a0000s = this.getPageElement("a0000s").getValue();
		
		//List<A02> a02list = HBUtil.getHBSession().createQuery("").list();
		//设置需要拟免的职务信息
		a0000s = a0000s.replaceAll(",", "','");
		String Sql = "select t.a0201a,t.a0201b,t.a0200,t.a0000,a01.a0101,"
		  		+ " a01.a0192a,t.a0215a,"
		  		+ "nvl((select max('2') from fxyp f where f.a0200=t.a0200 and mntp00='"+mntp00+"'),'34') personStatus,"
		  				+ "'' fxyp07,"
		  		+ " '' zrrx,(select b01id from b01 where b0111=t.a0201b) b01id from a02 t, a01 "
		  		+ " where t.a0000=a01.a0000  and a01.a0000 in('"+a0000s+"') order by a0223";
		CommQuery cqbs=new CommQuery();
		
		//拼接拟任职务设置  已经拟免的置灰  提示已经设置拟免
		StringBuilder html = new StringBuilder();
		//<input type="checkbox" name="mxz" id="mxz" style="border: none!important;" checked="checked" />
		//<label for="mxz">是否免现职</label></td>
		List<HashMap<String, Object>> nmzwList = cqbs.getListBySQL(Sql.toString());
		for(int i=0;i<nmzwList.size();i++){
			HashMap<String, Object> m = nmzwList.get(i);
			String[] info = new String[]{"",""};
			if("2".equals(m.get("personstatus"))){
				info[0] = " disabled='disabled'  title='该职务已经设置拟免！'";
			}
			info[1] = " pt='"+m.get("personstatus")+"'" +
					  " zrrx='"+(m.get("zrrx")==null?"":m.get("zrrx"))+"'"  +
					  " b01id='"+m.get("b01id")+"'"
					  ;
			html.append("<input type='checkbox' name='NMZW' id='mxz"+i+"' style='border: none!important;' "+info[0]+info[1]+"  checked='checked' value='"+m.get("a0200")+"'/>");
			html.append("<label  "+info[0]+"   for='mxz"+i+"'>"+m.get("a0201a")+"&nbsp;&nbsp;&nbsp;&nbsp;"+m.get("a0215a")+"</label>");
			html.append("<br/>");
		}
		
		this.getExecuteSG().addExecuteCode("$('.NMinfo').html(\""+html.toString()+"\")");
		
		
		
		
		
		String selSqL = "select fxyp00,fxyp02 from hz_mntp_gw m where m.b01id='"+b01idkq+"' and "
				+ " m.fxyp06='"+fxyp06+"' and fxyp07=1 and m.mntp00='"+mntp00+"'";
		//CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(selSqL);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			mapCode.put(listCode.get(i).get("fxyp00").toString(), listCode.get(i).get("fxyp02"));
		}
		((Combo)this.getPageElement("yxgwSel")).setValueListForSelect(mapCode);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	@PageEvent("tpbj.onclick")
	public int tpbj() throws RadowException {
		LinkedHashSet<String> selected = new LinkedHashSet<String>();
		// 从cookie中的获取之前选择的人员id
		Cookie[] cookies = this.request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("jggl.tpbj.ids".equals(cookie.getName())) {
					String cookieValue = cookie.getValue();
					String[] ids = cookieValue.split("#");
					for (String id : ids) {
						if (!StringUtils.isEmpty(id)) {
							selected.add(id);
						}
					}
				}
			}
		}
		// 人员id
		String a0000s = this.getPageElement("a0000sBD").getValue();
		if (!StringUtils.isEmpty(a0000s)) {
			String[] a0000Array = a0000s.split(",");
			for (int i = 0; i < a0000Array.length; i++) {
				selected.add(a0000Array[i]);
			}
		}

		if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
			return EventRtnType.FAILD;
		} else {
			String json = JSON.toJSONString(selected);
			this.getExecuteSG()
					.addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','同屏比较',1500,731,null,'"
							+ this.request.getContextPath() + "',null,{"
							+ "maximizable:false,resizable:false,RMRY:'同屏比较',addPerson:true,data:" + json + "},true)");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	
	@PageEvent("addETCInfo")
	@Transaction
	public int addInfo() throws Exception{
		String a01bzdesc = this.getPageElement("a01bzdesc").getValue();
		String a01bztype = this.getPageElement("a01bztype").getValue();
		String a01bzid = this.getPageElement("a01bzid").getValue();
		String mntp00 = this.getPageElement("mntp00").getValue();
		HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid=? and a01bztype=? ",
				new Object[]{mntp00,a01bzid,a01bztype});
		if(!StringUtils.isEmpty(a01bzdesc)){
			HBUtil.executeUpdate("insert into HZ_MNTP_BZ(mntp00,a01bzid,a01bztype,a01bzdesc) values(?,?,?,?)",
					new Object[]{mntp00,a01bzid,a01bztype,a01bzdesc});
		}
		
		
		this.setNextEventName("pgrid.dogridquery");
		this.setNextEventName("pgrid2.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
