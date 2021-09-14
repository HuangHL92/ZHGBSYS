package com.insigma.siis.local.pagemodel.xbrm.fx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.PageEvents;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.bean.SQLInfo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.FxypBatch;
import com.insigma.siis.local.business.entity.YJMX;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelReturnParam;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;
import com.utils.CommonParamUtil;
import com.utils.DBUtils;

import net.sf.json.JSONObject;

public class FXYPPageModel extends PageModel {
	
	/**
	 * 职位信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("jggwInfoGrid.dogridquery")
	public int jggwInfoGriddoMemberQuery(int start,int limit) throws RadowException{
		
		String bm = this.getPageElement("orgref").getValue();
		String jgid = this.getPageElement("a0201b").getValue();
		String where = "";
		if(bm!=null&&!"".equals(bm)){
			where += " and p.grouptype = '"+bm+"'";
		}
		if(jgid!=null&&!"".equals(jgid)){
			where += " and f.b0111 = '"+jgid+"'";
		}
		//String sql = "select b0101,b0114, from b01 b,jggwconfig";
		String sql = "select jggwconfid,b0111,b0101,b0114,b0104,b0194,sortid,gwname,gwnum,gwcode,countnum,decode(sign(cz),1,cz,0) cz1,decode(sign(cz),-1,abs(cz),0) cz2 from (select f.jggwconfid,b.b0111,b.b0101,b.b0114,b.b0104,b.b0194,b.sortid,f.gwname,f.gwcode,f.gwnum,nvl(a.countnum,0) countnum,f.gwnum-nvl(a.countnum,0) cz "
				+ " from b01 b join Jggwconf f on b.b0111=f.b0111 left join (select A0215A_c,get_B0111NS(a0201b) a0201b,count(1) countnum from a02 where a0255='1' and A0215A_c is not null "
				+ " group by A0215A_c,get_B0111NS(a0201b)) a on f.gwcode=a.A0215A_c and f.b0111=get_B0111NS(a.a0201b) left join refb01group p on f.b0111=p.b0111 where 1=1 "
				+ where +") order by length(b0111),b0111,SORTID asc,cz ";
		System.out.println(sql);
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 已选职位信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvents({"gridComp.dogridquery"})
	public int gridCompdoMemberQuery(int start,int limit) throws RadowException{
		try {
			String userid = SysManagerUtils.getUserId();
            String sql = "select b.fy2500,c.a0000,b.fy0100,c.fy0102,c.fy0108,a.fy2201b,a.fy2203 from fy22 a,fy25 b,fy01 c"
            		+ " where a.fy2200=b.fy2200 and b.fy0100=c.fy0100 and a.userid='" + userid
            		+ "' order by a.sortid,c.fy0113";
            this.pageQuery(sql, "SQL", start, limit);
            return EventRtnType.SPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.SPE_SUCCESS;
        }
	}
	
	/**
	 * 已选职位信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvents({"jggwSelGrid.dogridquery","NiRenGrid.dogridquery"})
	public int jggwSelGriddoMemberQuery(int start,int limit) throws RadowException{
		String userid = SysManagerUtils.getUserId();
		String sql = "select * from fy22 where userid='"+userid+"' order by sortid ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 已选职位信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvents({"yrSelGrid.dogridquery","gridcq.dogridquery"})
	public int yrSelGriddoMemberQuery(int start,int limit) throws RadowException{
		try {
			String userid = SysManagerUtils.getUserId();
            String dc005 = "1";//类别标识

            String cur_hj = "0";//环节
            String cur_hj_4 = "4-1";//讨论决定分环节
            cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
            //RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
            
            String sql = "select * from (select distinct HAVE_FINE(fy01.a0000,fy01.fy0100) havefine,fy01.fy0119, fy01.fy0118,"
                    + " fy01.a0000,fy01.fy0100 fy0100,a0101,a0184,fy0108," + "fy0113"
                    + " from a01,fy01 where fy0122='1' and "
                    + " a01.a0000=fy01.a0000 and nvl(fy0120,'1')<>'2'"
                    + " and userid='" + userid + "'  " /*+ sm.hj4sql */
                    + " union select distinct HAVE_FINE(fy01.a0000,fy01.fy0100) havefine,fy01.fy0119, fy01.fy0118,"
                    + " fy01.a0000,fy01.fy0100 fy0100,a0101,a0184,fy0108," + "fy0113"
                    + " from v_js_a01 a01,fy01 where fy0122 in ('2','3','4') and a01.v_xt=fy01.fy0122 and"
                    + " a01.a0000=fy01.a0000 and nvl(fy0120,'1')<>'2'"
                    + " and userid='" + userid + "'  " /*+ sm.hj4sql */
                    + " ) order by " + "fy0113";
            this.pageQuery(sql, "SQL", start, limit);
            return EventRtnType.SPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.SPE_SUCCESS;
        }
	}
	@PageEvent("ycry")
	public int ycry(String ids) throws RadowException{
		String userid = SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		HBTransaction tr = null;
		try {
			tr = sess.beginTransaction();
			sess.createSQLQuery("delete from fy01 where fy0100 in ('"+ids.replace(",", "','")+"')").executeUpdate();
			sess.createSQLQuery("delete from fy25 where fy0100 in ('"+ids.replace(",", "','")+"')").executeUpdate();
			tr.commit();
			String tab = this.getPageElement("clicktabid").getValue();
			if(tab.equals("tab2")) {
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','移除成功');radow.doEvent('yrSelGrid.dogridquery');");
			} else {
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','移除成功');radow.doEvent('gridcq.dogridquery');");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			if(tr!=null) {
				try {
					tr.rollback();
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
			throw new RadowException("系统异常:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//按姓名查询，传递人员IDs
    @PageEvent("xzrySel")
    public int xzrySel(String listStr) throws Exception {
        //String tplb = this.getPageElement("tplb").getValue();//调配类别
        //调配类别 不在数据库里的则新增。
        HBSession sess = HBUtil.getHBSession();
        HBTransaction tr = sess.beginTransaction();

        String userid = SysManagerUtils.getUserId();
        String cur_hj = "0";//环节
        String cur_hj_4 = "4-1";//讨论决定分环节
        String dc005 = "1";
        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);

//        StringBuffer sql = new StringBuffer("insert into fy01(fy0100, a0000, fy_id,fy0102, fy0103, fy0104, fy0105, fy0106, fy0114,fy0108,fy0110,fy0109,dc001,fy0113,fy0122,userid)  "
//                + "(select sys_guid(),a0000,'' fy_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192a, a0288 || ' ' || a0192c rtzjsj,"
//                + "(select replace(TO_CHAR(wm_concat(a0243)), ',', ' ') from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" /*+ tplb*/ + "',deploy_classify_dc004.nextval,（VXT）,'"+userid+"' from （A01） a01 ");
        StringBuffer sql = new StringBuffer("insert into fy01(fy0100, a0000, fy_id,fy0102, fy0103, fy0104, fy0105, fy0106, fy0114,fy0108,fy0110,fy0109,dc001,fy0113,fy0122,userid)  "
                + "(select sys_guid(),a0000,'' fy_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192, a0288 rtzjsj,"
                + "(select max(decode(length(a0243), 6, a0243 || '01', 8, a0243, null)) from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" /*+ tplb*/ + "',deploy_classify_dc004.nextval,（VXT）,'"+userid+"' from （A01） a01 ");
        sql.append(" where a0000 in ('-1'");
        if (listStr != null && listStr.length() > 2) {
            listStr = listStr.substring(0, listStr.length() - 1);
            List<String> list = Arrays.asList(listStr.split(","));
            StringBuffer xt1a0000s = new StringBuffer();
            StringBuffer xt2a0000s = new StringBuffer();
            StringBuffer xt3a0000s = new StringBuffer();
            StringBuffer xt4a0000s = new StringBuffer();
            /*StringBuffer jshisfy0100 = new StringBuffer();
            StringBuffer jshisfy01002 = new StringBuffer();*/
            
            for (String idparam : list) {
            	String idarr[] = idparam.split("@");
				if(idarr[1].equals("1")) {
            		xt1a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("2")) {
            		xt2a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("3")) {
            		xt3a0000s.append(",'" + idarr[0].trim() + "'");
            	} else if(idarr[1].equals("4")) {
            		xt4a0000s.append(",'" + idarr[0].trim() + "'");
            	}
    				
            }
            String sqlend1 = ") and not exists (select 1 from fy01 where fy01.a0000=a01.a0000 and fy01.userid='" + userid + "' ))";
            String sqlend2 = ") and not exists (select 1 from fy01 where fy01.a0000=a01.a0000 and fy01.userid='" + userid + "' )";
            
            try {
            	if(xt1a0000s.length()>0) {
            		String sql1 = (sql.toString() + xt1a0000s+sqlend1).replace("（A01）", "a01").replace("（A02）", "a02").replace("and  a02.v_xt=（VXT）", "").replace("（VXT）", "'1'");
            		sess.createSQLQuery(sql1).executeUpdate();
            		System.out.println(sql1);
            	}
            	if(xt2a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt2a0000s+sqlend2+" and v_xt='2')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'2'");
            		sess.createSQLQuery(sql1).executeUpdate();
            	}
            	if(xt3a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt3a0000s+sqlend2+" and v_xt='3')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'3'");
            		sess.createSQLQuery(sql1).executeUpdate();
            	}
            	if(xt4a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt4a0000s+sqlend2+" and v_xt='4')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'4'");
            		sess.createSQLQuery(sql1).executeUpdate();
            	}
            	sess.flush();
            	tr.commit();
            	this.getExecuteSG().addExecuteCode("odin.alert('设置成功！');radow.doEvent('yrSelGrid.dogridquery');");
            } catch (Exception e) {
            	if(tr!=null) {
            		tr.rollback();
            	}
                this.setMainMessage("数据更新失败！");
                e.printStackTrace();
                
            }
            this.getExecuteSG().addExecuteCode("$('#tplb').val('');");
            this.setNextEventName("gridcq.dogridquery");
            return EventRtnType.NORMAL_SUCCESS;
        } else {
            this.setMainMessage("无法查询到该人员！");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	
	// 按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws RadowException {
		// System.out.println(listStr);
		String sid = this.request.getSession().getId();
		StringBuffer sql = new StringBuffer();
		sql.append(CommSQL.getComSQL(sid) + " where a0000 in ('-1'");
		if (listStr != null && listStr.length() > 2) {
			listStr = listStr.substring(1, listStr.length() - 1);
			List<String> list = Arrays.asList(listStr.split(","));
			for (String id : list) {
				sql.append(",'" + id.trim() + "'");
			}
			sql.append(")");
			this.getPageElement("sql").setValue(sql.toString());

			String queryType = (String) request.getSession().getAttribute("queryType");

			// 写死列表
			String tableType = "1";
			if ("1".equals(tableType)) {
				if ("define".equals(queryType)) {
					this.getExecuteSG().addExecuteCode("changeField()");
					request.getSession().removeAttribute("queryType");
				} else {
					this.setNextEventName("peopleInfoGrid.dogridquery");
				}
			}
			if ("2".equals(tableType)) {
				this.getExecuteSG().addExecuteCode("datashow()");
				this.setNextEventName("peopleInfoGrid.dogridquery");
			}
			if ("3".equals(tableType)) {
				this.getExecuteSG().addExecuteCode("picshow()");
				this.setNextEventName("peopleInfoGrid.dogridquery");
			}
			return EventRtnType.NORMAL_SUCCESS;
		} else {
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	public void deleteResult(String sid) {
		try {
			HBUtil.executeUpdate("delete from A01SEARCHTEMP where sessionid='" + sid + "'");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// mySQL: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql, String resultOptSQL) {
		try {
			String sid = this.request.getSession().getId();

			if (DBUtil.getDBType() == DBType.ORACLE) {
				sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + " ) tmp";

			} else { // mysql
				sql = "select tmp.a0000,'" + sid + "' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql
						+ " ) tmp";
			}

			// sql = sql.replace("sort,", "");

			HBUtil.executeUpdate(resultOptSQL.replace("{sql}", sql));
		} catch (AppException e) {
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	// oracle: 将查询的数据结果，存到A01SEARCHTEMP表
	private void optResult(String sql, String tmp_sql, String resultOptSQL) {
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			stat.executeUpdate(tmp_sql.replace("{sql}", sql));
			stat.executeUpdate(resultOptSQL);
			con.commit();

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}

	private void optResult(String sql, String tmp_sql, String tem_sql2, String resultOptSQL) {
		String sid = this.request.getSession().getId();
		Connection con = null;
		try {
			con = HBUtil.getHBSession().connection();
			con.setAutoCommit(false);
			Statement stat = con.createStatement();
			stat.executeUpdate(tmp_sql);

			if (DBUtil.getDBType() == DBType.ORACLE) {
				sql = "select tmp.a0000,'" + sid + "' sessionid,rownum i from ( " + sql + ") tmp";
			} else { // mysql
				sql = "select tmp.a0000,'" + sid + "' sessionid,(@i:=@i+1) as i from (select @i:=0) as it,( " + sql
						+ " ) tmp";
			}
			stat.executeUpdate(resultOptSQL.replace("{sql}", sql));
			stat.executeUpdate(tem_sql2);
			con.commit();

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			System.out.println(resultOptSQL.replace("{sql}", sql));
			e.printStackTrace();
		}
	}
	@PageEvent("peopleInfoGrid.dogridquery")
	public int doMemberQuery(int start, int limit) throws RadowException, AppException {
		String queryType = "";
		queryType = (String) request.getSession().getAttribute("queryType");
		if (queryType == null)
			queryType = "1";
		String querydb = this.getPageElement("querydb").getValue();
		boolean isgbk = true;
		if(querydb!=null && (querydb.equals("2") || querydb.equals("3") ||querydb.equals("4"))) {
			isgbk = false;
		}
		if ("define".equals(queryType)) {// 自定义查询
			// String
			// qvid=this.getPageElement("qvid").getValue();当页面直接打开时，此方法取不到qvid值，采用session中取值
			String qvid = this.request.getSession().getAttribute("qvid").toString();
			String viewnametb = HBUtil.getValueFromTab("viewnametb", "qryview", "qvid='" + qvid + "'");
			String sessionid = this.request.getSession().getId();
			String username = SysManagerUtils.getUserloginName();
			String sql = "";
			// if("system".equals(username)){
			/*sql = " select * from " + viewnametb + ",A01SEARCHTEMP " + " where " + viewnametb
					+ ".a01a0000=A01SEARCHTEMP.a0000  " + " and A01SEARCHTEMP.sessionid='" + sessionid
					+ "' order by A01SEARCHTEMP.sort asc  ";*///
		
			  //}else{ 
			
			
			String b01String = (String)this.getPageElement("SysOrgTreeIds").getValue();
			 
	        StringBuffer cu_b0111_sb = new StringBuffer("");
	       
	        if(b01String!=null && !"".equals(b01String)){//tree!=null && !"".equals(tree.trim()
				JSONObject jsonObject = JSONObject.fromObject(b01String);
				if(jsonObject.size()>0){
					cu_b0111_sb.append(" and (1=2 ");
				}
				Iterator<String> it = jsonObject.keys();
				// 遍历jsonObject数据，添加到Map对象
				while (it.hasNext()) {
					String nodeid = it.next(); 
					String operators = (String) jsonObject.get(nodeid);
					String[] types = operators.split(":");//[机构名称，是否包含下级，是否本级选中]
					if("true".equals(types[1])&&"true".equals(types[2])){
						cu_b0111_sb.append(" or "+CommSQL.subString("cu.b0111", 1, nodeid.length(), nodeid));
					}else if("true".equals(types[1])&&"false".equals(types[2])){
						cu_b0111_sb.append(" or "+CommSQL.subString2("cu.b0111", 1, nodeid.length(), nodeid));
					}else if("false".equals(types[1])&&"true".equals(types[2])){
						cu_b0111_sb.append(" or cu.b0111 = '"+nodeid+"' ");
					}
				}
				if(jsonObject.size()>0){
					cu_b0111_sb.append(" ) ");
				}
	        }
			
			
			
			
			
		      String userid = SysManagerUtils.getUserId();
			  sql=" select * from "+viewnametb+",A01SEARCHTEMP " +
			  " where "+viewnametb+".a01a0000=A01SEARCHTEMP.a0000  " +
			  " and A01SEARCHTEMP.sessionid='"+sessionid+"' " + " and "
			  +viewnametb+".a01a0000 in (select a02.a0000 from a02 where a02.A0201B in "
					+ "(select cu.b0111 from competence_userdept cu where cu.userid = '"+userid+"' "+cu_b0111_sb+") and a0281='true') " +
			  " order by A01SEARCHTEMP.sort asc  " + " ";
			  
			  
			  // }
			 
			// String sql="select * from "+viewnametb+" ";//移除在导出，移除无效
			//this.request.getSession().setAttribute("allSelect", "");
			StopWatch w = new StopWatch();
			w.start();
			if (this.request.getSession().getAttribute("pageSize") != null
					&& !this.request.getSession().getAttribute("pageSize").equals("")) {
				int pageSize = Integer.parseInt(this.request.getSession().getAttribute("pageSize").toString()); // 判断是否设置了自定义每页数量，如果设置了使用自定义
				limit = pageSize;
			}
			this.pageQuery(sql, "SQL", start, limit);
			w.stop();

			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + sql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}

		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		String sid = this.request.getSession().getId();
		if (attribute != null && !"".equals(attribute + "")) {
			String sql = this.getPageElement("sql").getValue();
			String startSql = "select temp.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=temp.A0195) as a0195 ";
			String allSql = startSql + ",'" + this.request.getSession().getId()
					+ "' sessionid from a01 temp where temp.a0000 in ( " + sql + ")";
			System.out.println("select count(temp1.a0000) from (" + allSql + ") temp1");
			Object count = HBUtil.getHBSession().createSQLQuery("select count(temp.a0000) from (" + allSql + ") temp")
					.uniqueResult();
			int totalcount = 0;
			if (count instanceof BigDecimal) {
				totalcount = ((BigDecimal) count).intValue();
			} else if (count instanceof BigInteger) {
				totalcount = ((BigInteger) count).intValue();
			}
			String resultOptSQL = "insert into A01SEARCHTEMP {sql} ";
			String aSql = "select temp.a0000,'" + this.request.getSession().getId()
					+ "' sessionid from A01 temp where temp.a0000 in ( " + sql + ")";
			this.deleteResult(sid);
			optResult(aSql, resultOptSQL);
			String userid = SysManagerUtils.getUserId();
			String ssql = "select temp.* ,(select b0101 from b01 where b0111=temp.A0195) from a01 temp where temp.a0000 in ( "
					+ sql + ")";
			String querySql = "select  a01.A0000 as a0000,A0101 as a0101,A0184 as a0184,A0192A as a0192a,A0192 as a0192,A0104 as a0104,A0107 as a0107,A0117 as a0117,A0111A as a0111,A0114A as a0114,A0134 as a0134,A0140 as a0140,QRZXL as qrzxl,QRZXLXX as qrzxlxx,QRZXW as qrzxw,QRZXWXX as qrzxwxx,ZZXL as zzxl,ZZXLXX as zzxlxx,ZZXW as zzxw,ZZXWXX as zzxwxx,A0192F as a0192f,A0221 as a0221,A0288 as a0288,A0192E as a0192e,A0192C as a0192c,A0120 as a0120,A0196 as a0196,A0122 as a0122,A0187A as a0187a,A0165 as a0165,A0160 as a0160,A0121 as a0121,A2949 as a2949,A0197 as a0197,A0128 as a0128,A0163 as a0163,ZGXL as zgxl,ZGXLXX as zgxlxx,ZGXW as zgxw,ZGXWXX as zgxwxx,(select b0101 from b01 where b0111=a01.A0195) as a0195  from ("
					+ ssql + ") a01 join (SELECT sort,a0000 from A01SEARCHTEMP where sessionid = '" + sid
					+ "') tp on a01.a0000 = tp.a0000 order by sort";
			// String querysql=allSql.replace(CommSQL.getComSQL(sid),
			// CommSQL.getComSQLQuery2(userid,sid));
			this.request.getSession().setAttribute("allSelect", querySql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@" + querySql);// 统计专用
			this.request.getSession().setAttribute("listAdd", "listAdd");// 标识符
			StopWatch w = new StopWatch();
			w.start();
			this.pageQueryNoCount(allSql, "SQL", 0, 20, totalcount);
			w.stop();
			this.request.getSession().removeAttribute("listAddGroupSession");
			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + allSql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}
		if (!CustomQueryPageModel.QUERYLISTFLAG) {
			CustomQueryPageModel.LISTADDCCQLI = "-1";
			CustomQueryPageModel.LISTADDNAME = "无";
		} else {
			CustomQueryPageModel.QUERYLISTFLAG = false;
		}
		// 判断是否启用“集体内排序”，

		String isContain = this.getPageElement("isContain").getValue();
		String radioC = this.getPageElement("radioC").getValue();
		// 当queryType = 1，isContain = 0，并且，设置标志isA0225 = 1
		if (queryType.equals("1") && isContain != null && isContain.equals("0") && radioC.equals("1")) {
			this.request.getSession().setAttribute("isA0225", "1");
		}

		String temporarySort = "";
		Object ob = this.request.getSession().getAttribute("temporarySort");
		if (ob != null) {
			temporarySort = ob.toString();
		}

		String isA0225 = "";
		Object obisA0225 = this.request.getSession().getAttribute("isA0225");
		if (obisA0225 != null) {
			isA0225 = obisA0225.toString();
		}
		// 当前查询机构id
		String a0200 = this.getPageElement("checkedgroupid").getValue();

		String sort = request.getParameter("sort");// 要排序的列名--无需定义，ext自动后传
		String isPageTurning = this.request.getParameter("isPageTurning");
		String startCache = request.getParameter("startCache");
		if (!"true".equals(isPageTurning) && startCache != null) {// 不是翻页
																	// 且点击字段排序
			start = Integer.valueOf(startCache);
		}
		if (sort != null && sort.equals("a0101")) {
			sort = "a0102";
		}

		String dir = request.getParameter("dir");// 要排序的方式--无需定义，ext自动后传
		String orderby = "";
		if (sort != null && !"".equals(sort)) {
			orderby = "order by " + sort + " " + dir;
		}

		String userid = SysManagerUtils.getUserId();
		HttpSession session = request.getSession();
		if (session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")) {
			int pageSize = Integer.parseInt(session.getAttribute("pageSize").toString()); // 判断是否设置了自定义每页数量，如果设置了使用自定义
			limit = pageSize;
		}
		// int maxRow = 50000;

		this.getPageElement("checkList").setValue("");
		queryType = (String) this.request.getSession().getAttribute("queryType");
		String sql = this.getPageElement("sql").getValue();
		// 替换当前session 查询方案中保存的sql还是以前的session
		if(isgbk) {
			sql = sql.replaceAll("(select  a01.a0000,')(.*)(' sessionid from A01 a01)", CommSQL.getComSQL(sid));
		} else {
			sql = sql.replaceAll("(select  a01.a0000,')(.*)(' sessionid from V_JS_A01 a01)", CommSQL.getComSQL(sid,isgbk,querydb));
		}
		
		// sql.replace(CommSQL.getComSQL(sid),"");

		// String radioC=this.getPageElement("radioC").getValue();
		String resultOptSQL = "";
		String tem_sql = "";
		String tem_sql2 = "";

		if ("true".equals(isPageTurning) && startCache == null) {// 说明是翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页翻页
			String str6 = SysfunctionManager.getCurrentSysfunction().getFunctionid();
			String str7 = this.getCueEventElementName();
			SQLInfo localSQLInfo = (SQLInfo) this.request.getSession().getAttribute(str6 + "@" + str7);
			if (localSQLInfo != null) {
				StopWatch w = new StopWatch();
				w.start();
				// CommonQueryBS.systemOut("sql---:"+querysql);
				this.pageQuery(localSQLInfo.getSql(), "SQL", start, limit);
				w.stop();

				PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + localSQLInfo.getSql() + "\r\n\r\n");
				return EventRtnType.SPE_SUCCESS;
			}
		}

		// 结果中查询
		if ("2".equals(radioC)) {
			if (DBUtil.getDBType() == DBType.ORACLE) {
				tem_sql = "insert into cdttttt (select a0000,sessionid from  A01SEARCHTEMP where sessionid='" + sid
						+ "' minus {sql})";
				// tem_sql = "create or replace view ttttt as (select
				// a0000,sessionid from A01SEARCHTEMP where sessionid='"+sid+"'
				// minus {sql})";
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"
						+ sid + "'";
				// resultOptSQL = "delete from A01SEARCHTEMP where a0000 in
				// (select a0000 from (select a0000 from A01SEARCHTEMP where
				// sessionid='"+sid+"' minus select a0000 from cdttttt )) and
				// sessionid='"+sid+"'";
			} else {
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 not in (select a0000 from ({sql}) ax ) ";
			}

			// 追加查询
		} else if ("3".equals(radioC)) {
			tem_sql = "update A01SEARCHTEMP set sessionid='" + sid + "temp' where sessionid='" + sid + "'";
			resultOptSQL = "insert into A01SEARCHTEMP {sql} ";
			tem_sql2 = "delete from A01SEARCHTEMP  where sessionid='" + sid + "temp'";
		} else if ("4".equals(radioC)) {// 排查查询
			if (DBUtil.getDBType() == DBType.ORACLE) {
				tem_sql = "insert into cdttttt  {sql}";
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from cdttttt ) and sessionid='"
						+ sid + "'";
				// resultOptSQL = "delete from A01SEARCHTEMP where exists ({sql}
				// and A01SEARCHTEMP.a0000=a01.a0000) and sessionid='"+sid+"'";
			} else {
				resultOptSQL = "delete from A01SEARCHTEMP where a0000 in (select a0000 from ({sql}) ax ) ";
			}

		} else {
			if (StringUtil.isEmpty(temporarySort)) {
				this.deleteResult(sid);
			}

			resultOptSQL = "insert into A01SEARCHTEMP {sql}";
		}
		// 管理类别 a0165
		String a0165 = PrivilegeManager.getInstance().getCueLoginUser().getRate();
		if (a0165 != null && !"".equals(a0165)) {
			sql = sql + " and a01.a0165 not in (" + a0165 + ")";
		}
		String iforder = (String) this.getPageElement("orderqueryhidden").getValue();
		/*
		 * String a0201bid = (String)this.getPageElement("a0201b").getValue();
		 * String[] jgid = a0201bid.split("\\."); //yinl a02条件增加分区 2017.08.02
		 * String v0201bs = (jgid.length >=
		 * AppConfig.PARTITION_FRAGMENT)?" and a02.V0201B='"+jgid[AppConfig.
		 * PARTITION_FRAGMENT-1]+"' ":"";
		 */
		// 修复两个排序都选时报错的bug
		// iforder = (iforder.equals("1") &&
		// this.getPageElement("paixu").getValue().equals("1")) ? "0" : iforder;
		String tabType = this.getPageElement("tabn").getValue().toString();
		if (!tabType.equals("tab3")) {
			String personViewSQL = " select 1 from COMPETENCE_USERPERSON cu ";
			// 人员查看权限

			// 某些情况下影响效率，且目前系统COMPETENCE_USERPERSON表已废弃，所以注释此段代码
			// sql=sql+ " and not exists ("+personViewSQL+" where
			// cu.a0000=a01.a0000 and
			// cu.userid='"+SysManagerUtils.getUserId()+"') ";

		} else {// 按机构查询
			String b01String = this.getPageElement("orgjson").getValue();
			StringBuffer sb = new StringBuffer();
			if (!"".equals(b01String) && b01String != null && !"{}".equals(b01String)) {
				// 选择机构
				JSONObject jsonObject = JSONObject.fromObject(b01String);
				sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where  "
						+ " a02.A0201B = cu.b0111 AND cu.userid = '" + SysManagerUtils.getUserId() + "' ");

				Iterator<String> it = jsonObject.keys();
				if (it.hasNext()) {
					sb.append(" and a02.a0281='true' ");
				}
				sb.append(" and (1=2 ");
				// 遍历jsonObject数据，添加到Map对象
				while (it.hasNext()) {
					String nodeid = it.next();
					String operators = (String) jsonObject.get(nodeid);
					String[] types = operators.split(":");
					if ("true".equals(types[1]) && "true".equals(types[2])) {
						sb.append(" or cu.b0111 like '" + nodeid + "%' ");
					} else if ("true".equals(types[1]) && "false".equals(types[2])) {
						sb.append("or cu.b0111 like '" + nodeid + ".%'");
					} else if ("false".equals(types[1]) && "true".equals(types[2])) {
						sb.append("or cu.b0111 = '" + nodeid + "'");
					}
				}
				sb.append(" ) ");
				sb.append(" ) and 1=1 ");

			} else {
				// sb.append(" and 1=2 ");
				if(isgbk) {
					sb.append(" and a01.a0000 in (select a0000 from a02, competence_userdept cu where "
							+ " a02.A0201B = cu.b0111 and a02.a0281='true' AND cu.userid = '" + SysManagerUtils.getUserId()
							+ "' ");
					sb.append(" ) ");
				}
			}
			sql = sql + sb.toString();
		}
		String querysql = "";
		int totalcount = 0;
		if (sql.equals("")) {
			return EventRtnType.NORMAL_SUCCESS;
		}
		//System.out.println(sql.replace(CommSQL.getComSQL(sid,isgbk,querydb), " select count(2) from V_JS_A01 a01 "));
		Object count = HBUtil.getHBSession()
				.createSQLQuery(isgbk?sql.replace(CommSQL.getComSQL(sid,isgbk,querydb), " select count(2) from a01 ")
						:" select count(2) from V_JS_A01 a01 ").uniqueResult();

		if (count instanceof BigDecimal) {
			totalcount = ((BigDecimal) count).intValue();
		} else if (count instanceof BigInteger) {
			totalcount = ((BigInteger) count).intValue();
		} else if (count instanceof Long) {
			totalcount = ((Long) count).intValue();
		}
		if (totalcount > CommSQL.MAXROW) {
			if (!"1".equals(radioC)) {
				throw new AppException("单次查询结果超过" + CommSQL.MAXROW + "行限制，无法进行集合操作！");
			}

			querysql = sql.replace(CommSQL.getComSQL(sid), CommSQL.getComSQLQuery2(userid, sid));

			this.request.getSession().setAttribute("allSelect", querysql);
			this.request.getSession().setAttribute("ry_tj_zy", "conditionForCount@@" + querysql);// 统计专用
			StopWatch w = new StopWatch();
			w.start();
			// CommonQueryBS.systemOut("sql---:"+querysql);
			this.pageQueryNoCount(querysql, "SQL", start, limit, totalcount);
			w.stop();

			PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + querysql + "\r\n\r\n");
			return EventRtnType.SPE_SUCCESS;
		}

		if (StringUtil.isEmpty(temporarySort)) { // temporarySort为空，不是临时排序
			if (("2".equals(radioC) || "4".equals(radioC)) && DBUtil.getDBType() == DBType.ORACLE) {
				this.optResult(sql, tem_sql, resultOptSQL);
				// this.optResult(sql, resultOptSQL);
			} else if ("3".equals(radioC)) {
				String qsql = sql.replace(CommSQL.getComSQL(sid), " select a0000 from A01 a01 ")
						+ "union all select a0000 from A01SEARCHTEMP where sessionid = '" + sid + "temp'";
				qsql = CommSQL.getComSQL(sid) + " where a0000 in (" + qsql + ")";
				// sql = sql + " or a01.a0000 in (select a0000 from
				// A01SEARCHTEMP where sessionid='"+sid+"temp')";
				qsql = qsql + ("".equals(orderby) ? CommSQL.OrderByF(userid, isA0225, a0200,qsql) : orderby);
				this.optResult(qsql, tem_sql, tem_sql2, resultOptSQL);
			} else {
				// this.optResult(sql+("".equals(orderby)?CommSQL.OrderBy(userid):orderby),
				// resultOptSQL);
				this.optResult(sql + ("".equals(orderby) ? CommSQL.OrderByF(userid, isA0225, a0200,sql) : orderby),
						resultOptSQL);
			}
		}

		querysql = CommSQL.getComSQLQuery(userid, sid, isgbk, querydb) + " /*" + UUID.randomUUID() + "*/" + CommSQL.OrderBy(sort+" "+dir);

		// this.getExecuteSG().addExecuteCode("document.getElementById('temporarySort').value
		// = '';");
		// this.getPageElement("temporarySort").setValue("");

		// 清空是否临时排序标记
		this.request.getSession().setAttribute("temporarySort", "");
		// 清空启用单机构集体内排序顺序标记
		this.request.getSession().setAttribute("isA0225", "");

		// }else{

		// querysql = CommSQL.getComSQLQuery(userid,sid)+" where a01.a0000 in
		// (select a0000 from A01SEARCHTEMP where sessionid='"+sid+"')
		// /*"+UUID.randomUUID()+"*/"
		// +("".equals(orderby)?CommSQL.OrderBy(userid):orderby) ;

		/*
		 * int index = querysql.indexOf("from");
		 * 
		 * StringBuffer sqlNew = new StringBuffer(querysql);
		 * sqlNew.insert(index-1,
		 * ",(select sort from A01SEARCHTEMP tmp where sessionid = '"
		 * +sid+"' and tmp.a0000 = a01.a0000) as sort "); querysql =
		 * sqlNew.toString();
		 * 
		 * int indexOd = querysql.indexOf("order by"); sqlNew.insert(indexOd+8,
		 * " sort,");
		 * 
		 * querysql = sqlNew.toString(); }
		 */

		// Object o =
		// this.request.getSession().getAttribute("queryConditionsCQ");
		// 不是固定条件查询并且排序标志未被取消,则添加排序
		// sql = sql + CommSQL.OrderBy("a01.a0148");

		// 调用同步查询交易，使分页查询及数据显示查询并行进行，用以减少查询时间。
		// this.request.getSession().setAttribute("allSelect", sql);

		if (!"1".equals(radioC)) {
			count = HBUtil.getHBSession()
					.createSQLQuery("select count(a0000) from A01SEARCHTEMP where sessionid='" + sid + "'")
					.uniqueResult();
			if (count instanceof BigDecimal) {
				totalcount = ((BigDecimal) count).intValue();
			} else if (count instanceof BigInteger) {
				totalcount = ((BigInteger) count).intValue();
			}

		}

		// if(StringUtil.isEmpty(temporarySort)){ //temporarySort为空，不是临时排序

		this.request.getSession().setAttribute("allSelect", querysql);
		this.request.getSession().setAttribute("ry_tj_zy", "tempForCount@@" + querysql);// 统计专用
		// }

		StopWatch w = new StopWatch();
		w.start();
		CommonQueryBS.systemOut("sql----CustomQueryPageModel-----:" + querysql);
		this.pageQueryNoCount(querysql, "SQL", start, limit, totalcount);
		w.stop();

		PhotosUtil.saveLog("信息查询总耗时：" + w.elapsedTime() + "\r\n执行的sql:" + querysql + "\r\n\r\n");
		return EventRtnType.SPE_SUCCESS;

	}
	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("jggwSelGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initListAddGroupFlag")
	public int doMemberQueryAdd() throws RadowException, AppException {
		Object attribute = this.request.getSession().getAttribute("listAddGroupSession");
		if (attribute != null || !"".equals(attribute + "")) {
			this.request.getSession().removeAttribute("listAddGroupSession");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("ycgw")
	public int ycgw(String ids) throws RadowException{
		String userid = SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		HBTransaction tr = null;
		try {
			tr = sess.beginTransaction();
			sess.createSQLQuery("delete from fy22 where fy2200 in ('"+ids.replace(",", "','")+"')").executeUpdate();
			sess.createSQLQuery("delete from fy25 where fy2200 in ('"+ids.replace(",", "','")+"')").executeUpdate();
			tr.commit();
			
			String tab = this.getPageElement("clicktabid").getValue();
			if(tab.equals("tab1")) {
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','移除成功');radow.doEvent('jggwSelGrid.dogridquery');");
			} else {
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','移除成功');radow.doEvent('NiRenGrid.dogridquery');");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(tr!=null) {
				try {
					tr.rollback();
				} catch (AppException e1) {
					e1.printStackTrace();
				}
			}
			throw new RadowException("系统异常:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("xzgw")
	public int xzgw() throws RadowException{
		String userid = SysManagerUtils.getUserId();
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			String ids = "";
			
			BigDecimal c = (BigDecimal) sess.createSQLQuery("select max(nvl(sortid,0)) from fy22 where "
    				+ " fy22.userid in ('"+userid+"')").uniqueResult();
			int sortid = (c==null ? 0:c.intValue()) + 1;
			String sql = "insert into fy22(fy2200,fy2201,fy2202,fy2203,fy2204,"
					+ "fy2201a,fy2201b,fy2201c,sortid,userid) values(?,?,?,?,?, ?,?,?,?,?)";
			conn = sess.connection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			
			List<HashMap<String, Object>> list = this.getPageElement("jggwInfoGrid").getValueList();
			int count = 0;
			for (int i = 0; i < list.size(); i++) {
				HashMap<String, Object> map = list.get(i);
				if(map.get("personcheck")!=null && !map.get("personcheck").toString().equals("") 
						&& (Boolean)(map.get("personcheck"))==true) {
					count ++ ;
					String fy2201 = map.get("b0101").toString();
					String fy2202 = map.get("b0111").toString();
					String fy2203 = map.get("gwname").toString();
					String fy2204 = map.get("gwcode").toString();
					String b0194 = map.get("b0194").toString();
					String b0104 = map.get("b0104")!=null ? map.get("b0104").toString():"";//简称
					//B01 b01 = (B01) sess.get(B01.class, fy2202);
					//String b0104 = b01.getB0104();//简称
		    		//String b0194 = b01.getB0194();//单位类型
		    		
		    		if(b0194.equals("3")) {
		    			throw new RadowException(fy2201+"为机构纷组，请去除！");
		    		} else {
		    			String id = UUID.randomUUID().toString();
		    			
		    			pstmt.setString(1, id);
		    			pstmt.setString(2, fy2201);
		    			pstmt.setString(3, fy2202);
		    			pstmt.setString(4, fy2203);
		    			pstmt.setString(5, fy2204);
		    			pstmt.setString(6, b0104);
		    			if(b0194.equals("2")) {
		    				String name[] = {"",""};
		    				getFrdwName(fy2202, name, conn);
		    				pstmt.setString(7, name[1]);
			    			pstmt.setString(8, name[0]);
		    			} else {
		    				pstmt.setString(7, b0104);
			    			pstmt.setString(8, fy2201);
		    			}
		    			pstmt.setInt(9, sortid);
		    			pstmt.setString(10, userid);
		    			pstmt.addBatch();
		    		}
				}
			}
			if(count == 0) {
				this.setMainMessage("请至少选择一个职位。");
			} else {
				pstmt.executeBatch();
				conn.commit();
				this.getExecuteSG().addExecuteCode("$h.alert('系统提示','选择成功');radow.doEvent('jggwSelGrid.dogridquery');");
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RadowException("系统异常:"+e.getMessage());
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private String[] getFrdwName(String codevalueparameter2, String name[], Connection conn) throws RadowException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from b01 where b0111='"+codevalueparameter2+"'");
			if(rs.next()) {
				String b0194 = rs.getString("b0194");
				String b0101 = rs.getString("b0101");
				String b0104 = rs.getString("b0104");
				String b0121 = rs.getString("b0121");
				
				if(b0194.equals("1") || codevalueparameter2.equals("001.001")) {
					name[0] = b0101+name[0];
					name[1] = b0104+name[1];
					return name;
				} else {
					name[0] = b0101+name[0];
					name[1] = b0104+name[1];
					return getFrdwName(b0121, name, conn);
				}
			} else {
				return name;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据异常!");
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	@PageEvent("allDelete")
	public int allDelete(String id) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		try {
			String sql1 = "delete from fy01 where fy_id='"+id+"'";
			sess.createSQLQuery(sql1).executeUpdate();
			String sql2 = "delete from fy25 where fy_id='"+id+"'";
			sess.createSQLQuery(sql2).executeUpdate();
			String sql3 = "delete from fy22 where fy_id='"+id+"'";
			sess.createSQLQuery(sql3).executeUpdate();
			String sql = "delete from fxyp_BATCH where fy_id='"+id+"'";
			sess.createSQLQuery(sql).executeUpdate();
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示', '删除成功',function(e){radow.doEvent('memberGrid.dogridquery');})");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("donev")
	public int donev(String fyid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String userid = SysManagerUtils.getUserId();
		try {
			/*FxypBatch fxyp = (FxypBatch) sess.get(FxypBatch.class, fyid);
			if(fxyp.getFystatus()!=null && fxyp.getFystatus().equals("1")) {
				this.setMainMessage("此条数据已完成，请重新查询。");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			String v_sql1 = "select count(1) from fy22 j ,fy25 k where j.fy2200=k.fy2200 and j.userid=k.userid and k.userid='"+userid+"'";
			BigDecimal b1 = (BigDecimal) sess.createSQLQuery(v_sql1).uniqueResult();
			if(b1.intValue()==0) {
				this.setMainMessage("人员和拟任职务未进行关联设置，请先进行设置！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String v_sql2 = "select j.fy2200 from fy22 j ,fy25 k where j.fy2200=k.fy2200 and j.userid=k.userid and k.userid='"+userid+"' group by j.fy2200 having count(1)>1";
			List b2 =  sess.createSQLQuery(v_sql2).list();
			if(b2.size() >= 1) {
				this.setMainMessage("存在一个职务对应多个人，请修改设置！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			//this.getExecuteSG().addExecuteCode("radow.doEvent('done','"+fyid+"')");
			this.getExecuteSG().addExecuteCode("createX();");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("系统异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("done")
	public int done(String fyid) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		HBTransaction tr = null;
		String tplb = "1";//调配类别
		String rbId = fyid;
        String cur_hj = "0";//环节
        String cur_hj_4 = "4_1";//讨论决定分环节
        String dc005 = this.request.getParameter("dc005");
		try {
			tr = sess.beginTransaction();
			FxypBatch fxyp = (FxypBatch) sess.get(FxypBatch.class, fyid);
			if(fxyp.getFystatus()!=null && fxyp.getFystatus().equals("1")) {
				this.setMainMessage("此条数据已完成，请重新查询。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			String insertsql = "insert into record_batch(rb_id,rb_name,rb_date,rb_type,rb_userid,rb_no,rb_applicant,rb_org,rb_sysno,rb_cdate,rb_updated,rb_meettype,rb_deploytype,rb_approve,rb_reportreson,rb_reportvalue,rb_leadview,rb_status,rbm_id,rbm_status) "
					+ " select fy_id,fy_name,fy_date,fy_type,fy_userid,fy_no,fy_applicant,fy_org,fy_sysno,fy_cdate,fy_updated,fy_meettype,fy_deploytype,fy_approve,fy_reportreson,fy_reportvalue,fy_leadview,'0',fym_id,fym_status from fxyp_batch where fy_id='"+fyid+"'";
			sess.createSQLQuery(insertsql).executeUpdate();
			
			//调配类别 不在数据库里的则新增。
	        List dclist = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where dc001=?").setString(0, tplb).list();
	        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
	        //调配类别 不在数据库里的则新增。
	        if (dclist.size() == 0 && !"".equals(tplb)) {
	            String id = UUID.randomUUID().toString();
	            String slq = "insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
	                    + " values('"+id+"','"+rbId+"','"+tplb+"',deploy_classify_dc004.nextval,'"+(RMHJ.REN_MIAN_ZHI.equals(cur_hj) ? "2" : "1")+"')";
	            sess.createSQLQuery(slq).executeUpdate();
	            tplb = id;
	            setGridCombo(fyid);
	        } else {
	        	List dclist2 = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where rb_id=?").setString(0, rbId).list();
	        	tplb = dclist2.get(0) + "";
	        }
	        RMHJ.InsertSqlMap sm = RMHJ.getInsertSqlMap(cur_hj, cur_hj_4, dc005, tplb);
	        StringBuffer sql = new StringBuffer("insert into js01(js0100, a0000, rb_id,js0102, js0103, js0104, js0105, js0106, js0114,js0108,js0110,js0109,dc001,js0113,js0122)  "
	                + "(select sys_guid(),a0000,'" + rbId + "' rb_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192a, a0288 || ' ' || a0192c rtzjsj,"
	                + "(select replace(TO_CHAR(wm_concat(a0243)), ',', ' ') from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" + tplb + "',deploy_classify_dc004.nextval,（VXT） from （A01） a01 ");
	        StringBuffer hjSql = new StringBuffer("insert into js_hj(js0100,JS_TYPE," + sm.hj_sort + sm.thapfield + ") (select js0100,'" + cur_hj + "',js0113 " + sm.thapvalue + " from js01 where rb_id='" + rbId + "' "
	                + " and not exists (select 1 from js_hj where js_hj.js0100=js01.js0100 and js_type='" + cur_hj + "')  ");
	        hjSql.append(" and a0000 in ('-1'");
	        sql.append(" where a0000 in ('-1'");
	        String sql_fy = "select distinct a.a0000,a.fy0122 from fy01 a,fy25 b where a.fy0100=b.fy0100 and b.fy_id='"+fyid+"' group by a.a0000,a.fy0122 ";
	        List<Object[]> list = sess.createSQLQuery(sql_fy).list();
	        if (list !=null && list.size() > 0) {
	            
	            StringBuffer xt1a0000s = new StringBuffer();
	            StringBuffer xt2a0000s = new StringBuffer();
	            StringBuffer xt3a0000s = new StringBuffer();
	            StringBuffer xt4a0000s = new StringBuffer();
	            StringBuffer jshisjs0100 = new StringBuffer();
	            StringBuffer jshisjs01002 = new StringBuffer();
	            
	            for (Object[] idparam : list) {
	            	String idarr[] = new String[2];
	            	idarr[0] = "" + idparam[0];
	            	idarr[1] = "" + idparam[1];
	            	String sql_1 = "select a0101,jsh001,js0100,js_his_id from js_his where a0000='"+idarr[0]+"' and js0122='"+idarr[1]+"' and jsh001 in('1','3') and jsh004=1";
	    			List<Object[]> list_1 = sess.createSQLQuery(sql_1).list();
	    			if(list_1!=null && list_1.size()>0) {
	    				jshisjs0100.append(",'" + list_1.get(0)[2] + "'");
	    			} else {
	    				if(idarr[1].equals("1")) {
	                		xt1a0000s.append(",'" + idarr[0].trim() + "'");
	                	} else if(idarr[1].equals("2")) {
	                		xt2a0000s.append(",'" + idarr[0].trim() + "'");
	                	} else if(idarr[1].equals("3")) {
	                		xt3a0000s.append(",'" + idarr[0].trim() + "'");
	                	} else if(idarr[1].equals("4")) {
	                		xt4a0000s.append(",'" + idarr[0].trim() + "'");
	                	}
	    				
	    			}
	            }
	            //sql.append(") and not exists (select 1 from js01 where js01.a0000=a01.a0000 and js01.rb_id='" + rbId + "' ))");
	            String sqlend1 = ") and not exists (select 1 from js01 where js01.a0000=a01.a0000 and js01.rb_id='" + rbId + "' ))";
	            String sqlend2 = ") and not exists (select 1 from js01 where js01.a0000=a01.a0000 and js01.rb_id='" + rbId + "' )";

                //hjSql.append("))");
            	if(xt1a0000s.length()>0) {
            		String sql1 = (sql.toString() + xt1a0000s+sqlend1).replace("（A01）", "a01").replace("（A02）", "a02").replace("and  a02.v_xt=（VXT）", "").replace("（VXT）", "'1'");
            		String sql2 = hjSql.toString() + xt1a0000s+"))";
            		/*System.out.println(sql1);
            		System.out.println(sql2);*/
            		sess.createSQLQuery(sql1).executeUpdate();
            		sess.createSQLQuery(sql2).executeUpdate();
            		
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt1a0000s+") and js0122='1'";
            		sess.createSQLQuery(u1).executeUpdate();
            	}
            	if(xt2a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt2a0000s+sqlend2+" and v_xt='2')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'2'");
            		String sql2 = hjSql.toString()+xt2a0000s+"))";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt2a0000s+") and js0122='2'";
            		sess.createSQLQuery(u1).executeUpdate();
            	}
            	if(xt3a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt3a0000s+sqlend2+" and v_xt='3')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'3'");
            		String sql2 = hjSql.toString()+xt3a0000s+"))";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt3a0000s+") and js0122='3'";
            		sess.createSQLQuery(u1).executeUpdate();
            	}
            	if(xt4a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt4a0000s+sqlend2+" and v_xt='4')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'4'");
            		String sql2 = hjSql.toString()+xt4a0000s+"))";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt4a0000s+") and js0122='4'";
            		sess.createSQLQuery(u1).executeUpdate();
            	}
            	if(jshisjs0100.length()>0) {
            		String sql1 = "insert into js01 select js0100,a0000,dc001,'"+rbId+"',js0102,js0103,js0104,js0105,js0106,js0107,js0108,js0109,js0110,js0111,js0112,js0113,js0114,js0115,js0116,js0117,js0118,js0119,js0120,js0121,js0122,js0123,js0111a,js0117a from js01_his where js0100 in ('-1'"+jshisjs0100+")";
            		//System.out.println(sql1);
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js02 select js0100,dc001,'"+rbId+"',js0202,js0203,js0204,js0205,js0206,js0207,js0208,js0209,js0210,js0211 from js02_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js03 select js0100,dc001,'"+rbId+"',js0302,js0303,js0304,js0305,js0306,js0307,js0308,js0309,js0310,js0311 from js03_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js04 select js0100,dc001,'"+rbId+"',js0402,js0403 from js04_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js06 select js0100,dc001,'"+rbId+"',js0602,js0603,js0604,js0605 from js06_his where js0100 in ('-1'"+jshisjs0100+")";
            		System.out.println(sql1);
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js08 select js0100,dc001,'"+rbId+"',js0802,js0803,js0804,js0805,js0806,js0807,js0808 from js08_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js09 select js0100,dc001,'"+rbId+"',js0902,js0903,js0904,js0905,js0906 from js09_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js10 select js0100,dc001,'"+rbId+"',js1002,js1003,js1004 from js10_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js11 select js0100,dc001,'"+rbId+"',js1102,js1103,js1104,js1105,js1106 from js11_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js14 select js0100,dc001,'"+rbId+"',js1401,js1402,js1403,js1404,js1405,js1406,js1407,js1408,js1409,js1410,js1411,js1412,js1413,js1414,js1415,js1416,js1417,js1418,js1419,js1420,js1421,js1422,js1423,js1424,js1425,js1426,js1427,js1428,js1429,js1430,js1431,js1432,js1433,js1434,js1435,js1436,js1437,js1438,js1439,js1440,js1441,js1442,js1443,js1444,js1445,js1446,js1447,js1448,js1449,js1414a from js14_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js15 select js0100,dc001,'"+rbId+"',js1501,js1502,js1503,js1504,js1505,js1506,js1507,js1508,js1509,js1510,js1511,js1512,js1513,js1514 from js15_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js18 select js0100,dc001,'"+rbId+"',js1801,js1802,js1803,js1804,js1805 from js18_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js19 select js0100,dc001,'"+rbId+"',js1902,js1903,js1904,js1905,js1906,js1801,js1907,js1908,js1909,js1910,js1911,js1912 from js19_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js20 select js0100,dc001,'"+rbId+"',js2002,js2003,js2004,js2005,js2006,js2007 from js20_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js21 select js0100,dc001,'"+rbId+"',js2100,js2101,js2102,js2103,js2104,js2105,js2106,js2107,js2108,js2109 from js21_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js22 select js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,js0100,js2300,rbd000,rbd001,js2201a,js2201b,js2201c,sortid,js2207,'"+rbId+"' from js22_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js23 select js0100,a0000,'"+rbId+"',js2300,js2301,js2302,js2303,js2304,js2305,js2306,js2307,js2308,js2309,js2302a,js2302b,js2303a,js2303b,js2310 from js23_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js24 select js2401,js2402,js2403,js2400,a0000,js2404,js2405,js2406,js0100,js2300,rbd000,rbd001,js2401a,js2401b,js2401c,a0200,'"+rbId+"' from js24_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js_hj select js0100,js_type,js_sort,js_class_dc001_2,js_class2,js_sort4,js_sort_dc005_2 from js_hj_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		
            		List<Object[]> list3 = sess.createSQLQuery("select jsa00,jsa07 from js_att_his where js0100  in ('-1'"+jshisjs0100+")").list();
					for (Object[] obj : list3) {
						String p = AppConfig.HZB_PATH + "/";
						String jsa00 = obj[0]!=null ? obj[0].toString():"";
						String jsa07 = obj[1]!=null ? obj[1].toString():"";
						String r = AppConfig.HZB_PATH + "/jshis/" + jsa07 + jsa00;
						File f = new File(r);
                        if (f.exists() && f.isFile()) {
                        	File f2 = new File(p + "zhgbuploadfiles" + File.separator + rbId + File.separator);
                        	if(!f2.exists()) {
                        		f2.mkdirs();
                        	}
                        	PhotosUtil.copyFile(f, new File(p + "zhgbuploadfiles" + File.separator + rbId + File.separator+jsa00));
                        }
					}
            		sql1 = "insert into js_att select jsa00,js0100,'"+rbId+"',jsa02,jsa03,jsa04,jsa05,jsa06,'zhgbuploadfiles"+File.separator + rbId + File.separator+"',jsa08 from js_att_his where js0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		
            		String u1 = "update JS_HIS set JSH004='0' where JS0100 in ('-1'"+jshisjs0100+")";
            		sess.createSQLQuery(u1).executeUpdate();
            	}
            	
            	String insertsql2 = "insert into js22(js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,js0100,js2300,rbd000,rbd001,js2201a,js2201b,js2201c,sortid,js2207,rb_id) "
    					+ " select fy2201,fy2202,fy2203,c.fy2200,a.a0000,fy2204,fy2205,fy2206,a.js0100,fy2300,rbd000,rbd001,fy2201a,fy2201b,fy2201c,sortid,fy2207,rb_id from js01 a,fy01 b,fy22 c,fy25 d where a.a0000=b.a0000 and a.js0122=b.fy0122 and b.fy0100=d.fy0100 and c.fy2200=d.fy2200 and d.fy_id=a.rb_id and d.fy_id='"+fyid+"'";
    			sess.createSQLQuery(insertsql2).executeUpdate();
            	System.out.println(insertsql2);
    			sess.createSQLQuery("update fxyp_batch set fy_status='1' where fy_id='"+fyid+"'").executeUpdate();
            	sess.flush();
            	tr.commit();
            
	        }
	        this.getExecuteSG().addExecuteCode("radow.doEvent('scyj','"+rbId+"');");
	        this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示', '设置完成',function(e){radow.doEvent('memberGrid.dogridquery');})");
		} catch (Exception e) {
			e.printStackTrace();
			if(tr!=null) {
        		try {
					tr.rollback();
				} catch (AppException e1) {
					e1.printStackTrace();
				}
        	}
			throw new RadowException("系统异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void setGridCombo(String rbId) throws RadowException, AppException {
        //设置下拉框
        //String rbId = this.getPageElement("rbId").getValue();
        //String dc005 = this.getPageElement("dc005").getValue();//类别标识
        String sql = "select  DC001,DC003,lpad(dc004,10,'0') dc004,dc005 from DEPLOY_CLASSIFY where RB_ID  ='" + rbId + "'  order by dc004";
        CommQuery cqbs = new CommQuery();
        List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql);
        HashMap<String, Object> mapCode_1 = new LinkedHashMap<String, Object>();
        HashMap<String, Object> mapCode_2 = new LinkedHashMap<String, Object>();
        for (int i = 0; i < listCode.size(); i++) {
            if (RMHJ.TIAO_PEI_LEI_BIE.equals(listCode.get(i).get("dc005"))) {
                mapCode_1.put(listCode.get(i).get("dc004").toString() + "@@" + listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
            } else if (RMHJ.TAN_HUA_AN_PAI.equals(listCode.get(i).get("dc005"))) {
                mapCode_2.put(listCode.get(i).get("dc004").toString() + "@@" + listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
            }
        }
        mapCode_1.put("999@@999", "其他");
        mapCode_2.put("999@@999", "其他");
       // ((Combo) this.getPageElement("dc001_grid")).setValueListForSelect(mapCode_1);
       // ((Combo) this.getPageElement("dc001_2_grid")).setValueListForSelect(mapCode_2);
    }
	/**
	 * 生成预警
	 * @throws RadowException 
	 */
	@PageEvent("scyj")
	@SuppressWarnings("unchecked")
	public void scyj(String rbId) throws RadowException{
		//String rbId = this.getPageElement("rbId").getValue();//批次
		String dc005 = "1";//类别标识
		
		String cur_hj = "0";//环节
		String cur_hj_4 = "1";//讨论决定分环节
		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
		
		String hjsql = "select distinct js01.js0100,js01.a0000,js01.js0122  from a01,js01,js_hj where "
				+ " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 "
				+ " and rb_id='"+rbId+"'  "+sm.hj4sql;
		Connection conn = null;
		PreparedStatement pst = null;
		
		String xzfn = CommonParamUtil.PARAM_MAP.get("default_warning_plan_id"); //使用默认方案
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> listhja0000s = sess.createSQLQuery(hjsql).list(); //拟任人员名单
			
			String sqlstr = "select t.chinesename,t.qrysql,t.yjtype,t.qrysql2 from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid and r.yf000=? "; 
			List<Object[]> listsqls = sess.createSQLQuery(sqlstr).setString(0, xzfn).list(); //方案里的条件
			
			//Map<String,List<String>> a0000map = new HashMap<String,List<String>>(); //预警人员和预警信息
			//Map<String, HashSet<String>> a0000map = new HashMap<String,HashSet<String>>(); //预警人员和预警信息
			//其他来源库人员信息 a0000+v_xt 
			Map<String, HashSet<String>> a0000map_ = new HashMap<String,HashSet<String>>(); //预警人员和预警信息
			
			//单个条件中的人员 和 对应中的条件名称。
			HashSet<String> a0000Set; 
			for(Object o[] : listsqls){
				String k = o[0]==null?"":o[0].toString();
				String yjtype = o[2]==null?"":o[2].toString();
				if(!"".equals(k)){
					String vsql = "select a01a0000||'@1' from ("+DBUtils.ClobToString((Clob)o[1])+") ";
					List<String> a0000s = sess.createSQLQuery(vsql).list();
					
					String vsql2 = "select a01a0000||'@'||v_xt from ("+DBUtils.ClobToString((Clob)o[3])+") ";
					List<String> a0000s2 = sess.createSQLQuery(vsql2).list();
					a0000s.addAll(a0000s2);
					a0000Set = new HashSet<String>(a0000s);
					//a0000map.put(k+"@_@"+yjtype, a0000Set);
					a0000map_.put(k+"@_@"+yjtype, a0000Set);
				}
				
			}
			
			//人员预警信息详细列表
			List<YJMX> yjmxList = new ArrayList<YJMX>();
			YJMX yjmx = null;
			
			//人员预警信息
			Map<String, String> ps = new HashMap<String, String>();
			Map<String, Integer> index = new HashMap<String, Integer>();
			String a0000,js0100,desc,vxt;
			for(String key : a0000map_.keySet()){
				Set<String> a0000s = a0000map_.get(key); //预警人员Set
				
				String name_type[] = key.split("@_@"); //预警信息
				String stylecolor = "";
				if("1".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,7,7)' ";
				}else if("2".equals(name_type[1])){
					stylecolor = " style='color:rgb(255,198,0)' ";
				}else{
					stylecolor = " style='color:rgb(0,169,0)' ";
				}
				for(Object o[]:listhja0000s){ //拟任名单
					//环节中的a0000
					a0000 = o[1].toString();
					js0100 = o[0].toString();
					
					vxt = o[2].toString();
					if(a0000s.contains(a0000+"@"+vxt)){//如果预警条件中的人员包含环节中的人员， 做记录
						
						//记录明细
						yjmx = new YJMX(UUID.randomUUID().toString(),a0000, js0100, name_type[0], name_type[1]);
						yjmxList.add(yjmx);
						
						desc = ps.get(js0100);
						if(desc==null){
							index.put(js0100, 1);
							ps.put(js0100, ""+index.get(js0100) +"、<span "+stylecolor+">"+ name_type[0]+"</span>;");
						}else{
							index.put(js0100, index.get(js0100)+1);
							ps.put(js0100, desc+"</br>"+index.get(js0100)+"、<span"+stylecolor+">"+name_type[0]+"</span>;");
						}
						String yjtype = ps.get(js0100+"_type");
						if(yjtype==null||"".equals(yjtype)){
							ps.put(js0100+"_type", name_type[1]);//预警类型
						}else{
							//跟当前预警级别比较  选出预警级别高的
							if(yjtype.compareTo(name_type[1])>0){
								ps.put(js0100+"_type", name_type[1]);//预警类型
							}
						}
					}
				}
			}
			
			if(ps.size()>0){
				conn = HBUtil.getHBSession().connection();
				conn.setAutoCommit(false);
				String sql = "update js01 set js0118=?,js0119=? where js0100=?";
				pst = conn.prepareStatement(sql);
				String js01002;
				for(Object o[]:listhja0000s){//更新批次所有人员，没有预警则置为空
					//环节中的a0000
					js01002 = o[0].toString();
					pst.setString(1, ps.get(js01002));
					pst.setString(2, ps.get(js01002+"_type"));
					pst.setString(3, js01002);
					pst.addBatch();
				}
				
				//明细是否重复(为啥要做这步操作，前面有坑...)
				String delete = "delete from YJMX where A0000 = ? and JS0100 = ? and CHINESENAME = ? and YJTYPE = ?";
				PreparedStatement pst2 = conn.prepareStatement(delete);
				for (YJMX entity : yjmxList) {
					pst2.setString(1, entity.getA0000());
					pst2.setString(2, entity.getJs0100());
					pst2.setString(3, entity.getChinesename());
					pst2.setString(4, entity.getYjtype());
					pst2.addBatch();
				}
				pst2.executeBatch();

				//保存明细
				String insert = "insert into YJMX (MX001, A0000, JS0100, CHINESENAME, YJTYPE) values (?, ?, ?, ?, ?)";
				PreparedStatement pst3 = conn.prepareStatement(insert);
				for (YJMX entity : yjmxList) {
					pst3.setString(1, entity.getMx001());
					pst3.setString(2, entity.getA0000());
					pst3.setString(3, entity.getJs0100());
					pst3.setString(4, entity.getChinesename());
					pst3.setString(5, entity.getYjtype());
					pst3.addBatch();
				}
				pst3.executeBatch();
				
				pst.executeBatch();
				conn.commit();
			}
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				
			}
			e.printStackTrace();
			this.setMainMessage("保存失败！");
		}finally {
			try {
				if(conn!=null)
					conn.close();
				if(pst!=null)
					pst.close();
			} catch (SQLException e1) {
				
			}
		}
	}
	
	@PageEvent("setC")
    @Synchronous(true)
    @NoRequiredValidate
    public int setC(String nrId) throws RadowException {
    	HBSession sess = HBUtil.getHBSession();
    	Connection conn = null;
    	Statement stmt = null;
    	PreparedStatement pstmt = null;
    	try {
    		String userid = SysManagerUtils.getUserId();
            conn = sess.connection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute("delete from fy25 where fy2200='"+nrId+"'");
            String sql = "insert into fy25 values (sys_guid(),'"+nrId+"',?,'','"+userid+"')";
            pstmt = conn.prepareStatement(sql);
            List<HashMap<String, Object>> list1 = this.getPageElement("gridcq").getValueList();
            int count = 0;
            for (HashMap<String, Object> hm : list1) {
				if(hm.get("pcheck")!=null&&!"".equals(hm.get("pcheck"))&& (hm.get("pcheck")+"").equals("true")){
					count++;
					pstmt.setString(1, hm.get("fy0100")+"");
					pstmt.addBatch();
				}
            }
            if(count>0) {
            	pstmt.executeBatch();
            }
            conn.commit();
            this.getExecuteSG().addExecuteCode("radow.doEvent('gridComp.dogridquery');");
            //去除前面勾选的勾
            this.getExecuteSG().addExecuteCode("deletegou();");
            this.setMainMessage("设置成功！");
    	} catch (Exception e) {
            e.printStackTrace();
            if(conn!=null) {
            	try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
            }
            this.setMainMessage("设置失败！");
        } finally {
        	if(stmt!=null) {
        		try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(pstmt!=null) {
        		try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
        	if(conn!=null) {
        		try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
        	}
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("cancelC")
    @Synchronous(true)
    @NoRequiredValidate
    public int cancelC(String ids)  throws RadowException {
    	HBSession sess = HBUtil.getHBSession();
    	try {
    		sess.createSQLQuery("delete from fy25 where fy2500 in ('"+ids.replace(",", "','")+"')").executeUpdate();
            this.setMainMessage("取消成功！");
            this.getExecuteSG().addExecuteCode("radow.doEvent('gridComp.dogridquery');");
    	} catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("取消失败！");
        }
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("down")
    @NoRequiredValidate
    public int down() throws RadowException {
    	HBSession sess = HBUtil.getHBSession();
    	String rootpath = getRootPath();
    	Workbook wb = null;
		FileInputStream in = null;
    	try {
    		String userid = SysManagerUtils.getUserId();
    		String path1 = AppConfig.HZB_PATH +"/temp/fxyp/"+UUID.randomUUID().toString()+"/";
			File file = new File(path1);
			if(!file.exists()) {
				file.mkdirs();
			}
    		Workbook workbook = new HSSFWorkbook();  
    		String sql = "select c.fy0102,c.fy0103,c.fy0108,a.fy2201b,a.fy2203,a.fy2200 from fy22 a,fy25 b,fy01 c"
            		+ " where a.fy2200=b.fy2200 and b.fy0100=c.fy0100 and a.userid='" + userid
            		+ "' order by a.sortid,c.fy0113";
    		List<Object[]> list = sess.createSQLQuery(sql).list();
    		in = new FileInputStream(new File(rootpath+"/pages/xbrm/fx/fxyp_1.xlsx"));
			wb = new XSSFWorkbook(in);
			CellStyle[] cellstyles = new CellStyle[5];
			Sheet sheet = wb.getSheetAt(0);
			Row row_old = sheet.getRow(2);
			short rowheihgt = row_old.getHeight();
			cellstyles[0] = row_old.getCell(0).getCellStyle();
			cellstyles[1] = row_old.getCell(1).getCellStyle();
			cellstyles[2] = row_old.getCell(2).getCellStyle();
			cellstyles[3] = row_old.getCell(3).getCellStyle();
			cellstyles[4] = row_old.getCell(4).getCellStyle();
			int start = 2;
			int ms = 0;
			int me = 0;
			String j = "";
			for (int i = 0; i < list.size(); i++) {
				Row row = sheet.createRow(start + i);
				Object[] obj = list.get(i);
				
				String js2200 = ""+obj[5];
				
				Cell cell0 = row.createCell(0);
				cell0.setCellStyle(cellstyles[0]);
				cell0.setCellValue(i+1);
				
				Cell cell1 = row.createCell(1);
				cell1.setCellStyle(cellstyles[1]);
				cell1.setCellValue(""+obj[3]+obj[4]);
				
				Cell cell2 = row.createCell(2);
				cell2.setCellStyle(cellstyles[2]);
				cell2.setCellValue(""+obj[0]);
				
				Cell cell3 = row.createCell(3);
				cell3.setCellStyle(cellstyles[3]);
				cell3.setCellValue(""+obj[1]);
				
				Cell cell4 = row.createCell(4);
				cell4.setCellStyle(cellstyles[4]);
				cell4.setCellValue(""+obj[2]);
				
				if(i==0) {
					ms = start + i;
					j = js2200;
				} else if(i == list.size()-1) {
					/*if(ms != start + i -1) {
						sheet.addMergedRegion(new CellRangeAddress(ms, start + i, 1, 1));
					}*/
					if(!js2200.equals(j)) {
						if(ms != start + i -1) {
							sheet.addMergedRegion(new CellRangeAddress(ms, start + i -1, 1, 1));
						}
						
						ms = start + i;
						j = js2200;
					} else {
						if(ms != start + i) {
							sheet.addMergedRegion(new CellRangeAddress(ms, start + i, 1, 1));
						}
					}
				} else {
					if(!js2200.equals(j)) {
						if(ms != start + i -1) {
							sheet.addMergedRegion(new CellRangeAddress(ms, start + i -1, 1, 1));
						}
						
						ms = start + i;
						j = js2200;
					}
				}
				
			}
			String filename = "分析研判.xlsx";
			in.close();
			
			FileOutputStream fout = new FileOutputStream(new File(path1+filename));
			wb.write(fout);
			fout.close();
		    wb.close();
		    this.getPageElement("f").setValue(path1+filename);
		    this.getExecuteSG().addExecuteCode("downFile();");
    	} catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("系统异常！");
        } finally {
			
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("down2")
    @NoRequiredValidate
    public int down2() throws RadowException {
    	HBSession sess = HBUtil.getHBSession();
    	String rootpath = getRootPath();
    	Statement stmt = null;
		ResultSet rs = null;
    	Workbook wb = null;
		FileInputStream in = null;
    	try {
    		String userid = SysManagerUtils.getUserId();
    		String path1 = AppConfig.HZB_PATH +"/temp/fxyp/"+UUID.randomUUID().toString()+"/";
			File file = new File(path1);
			if(!file.exists()) {
				file.mkdirs();
			}
    		String sql = "select * from (select distinct a01.a0000,a01.a0101, a01.a0107, a01.zgxl, a01.a0196, a01.a0141, a01.a0104, a01.a0192,a01.a0221,a01.a0288,a01.a0192e,a01.a0192c, "
    				+ " a01.a0104a, a01.qrzxl, a01.a0111a, a01.a0134, a01.a0140, a01.zcsj, a01.fcsj, fy01.fy0113, fy22.fy2201, fy22.fy2203,fy22.fy2200, fy22.sortid,'1' v_xt,"
    				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='1') A0243a,  "
    				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from a02 a,jggwconf f where get_b0111ns(a.a0201b)=f.b0111 and a.a0215a_c=f.gwcode and zwcode='1A32' and a.a0000=a01.a0000 and a.a0219='2') A0243b  "
    				+ " from a01, fy01, fy22, fy25 where fy01.fy0122 = '1' and a01.a0000 = fy01.a0000 and fy25.fy0100 = fy01.fy0100  and "
    				+ " fy25.fy2200 = fy22.fy2200   and fy01.userid = '"+userid+"'"
    				+ " union  select distinct a01.a0000,a01.a0101, a01.a0107, a01.zgxl, a01.a0196, a01.a0141, a01.a0104, a01.a0192, a01.a0221,a01.a0288,a01.a0192e,a01.a0192c,"
    				+ " a01.a0104a, a01.qrzxl, a01.a0111a, a01.a0134, a01.a0140, a01.zcsj, a01.fcsj, fy01.fy0113, fy22.fy2201, fy22.fy2203,fy22.fy2200, fy22.sortid, a01.v_xt,"
    				+ " (select min(decode(LENGTH(A0243),6,A0243||'01',A0243)) from v_js_a02 a where a.a0000=a01.a0000 and a.v_xt=a01.v_xt and A0255='1') A0243a, "
    				+ " '' A0243b "
    				+ " from v_js_a01 a01, fy01 , fy22, fy25 where a01.a0000=fy01.a0000 and fy01.fy0122=a01.v_xt and fy25.fy0100=fy01.fy0100 and fy25.fy2200=fy22.fy2200 "
    				+ " and fy0122 in ('2','3','4') and nvl(fy01.fy0120, '1')!='2' and fy01.userid = '"+userid+"')  order by sortid,fy0113";
    		in = new FileInputStream(new File(rootpath+"/pages/xbrm/fx/tlrmmd.xls"));
			wb = new HSSFWorkbook(in);
			Sheet sheet = wb.getSheetAt(0);
			sess = HBUtil.getHBSession();
			stmt = sess.connection().createStatement();
			rs = stmt.executeQuery(sql);
			//List<HashMap<String,Object>> list=CommonQueryBS.getListBySQL(sql);
			CellStyle style = ExcelStyleUtil.getCellStyle(wb);
			
			//日期
			Row rowdate = sheet.getRow(1);//第2行
			Cell celldate = rowdate.getCell(10);//第11列
			//将当前时间添加到第2行第11列
			celldate.setCellValue(new SimpleDateFormat("yyyy年MM月").format(new Date()) );
			int rowIndex = 3;
			int start = 2;
			String j = "";
			int ms = 0;
			String reg = "^([0-9]{4}[.][0-9]{2})$";
			String reg2 = "([0-9]{4}[.][0-9]{2})";
			while(rs.next()){
				
				String a0101=rs.getString("a0101");//人员姓名
				String a0192=rs.getString("a0192");//现任职务
				String js0111="";//拟任职务
				String fy2201=rs.getString("fy2201");
				String fy2203=rs.getString("fy2203");
				String fy2200=rs.getString("fy2200");
				js0111= fy2201 + fy2203;
				//String js0117=rs.getString("js0117");//拟免职务
				
				String a0104=rs.getString("a0104");//性别
				String a0107=rs.getString("a0107");//出生日期
			    a0107=a0107.substring(2, 4)+"."+a0107.substring(4, 6);
				String qrzxl=rs.getString("qrzxl");//全日制学历
				String a0111a=rs.getString("a0111a");//籍贯
				a0111a = a0111a.length()==4 ? a0111a.substring(0, 2) + "\r\n" +a0111a.substring(2): a0111a;
				String a0134=rs.getString("a0134");//工作时间
				a0134=a0134.substring(2, 4)+"."+a0134.substring(4, 6);
				String a0140=rs.getString("a0140");//入党时间
				boolean p = Pattern.matches(reg, a0140);
				if(p) {
					a0140 = a0140.substring(2);
				}
				Pattern pt = Pattern.compile(reg2);
				Matcher m = pt.matcher(a0140);
				while (m.find()) {
		            String mstr = m.group();
		            a0140 = a0140.replace(mstr, mstr.substring(2));
		        } 
				//String zcsj=rs.getString("zcsj");//正处时间
				//String fcsj=rs.getString("fcsj");//副处时间
				String a0221=rs.getString("a0221");//职务层次
				String a0288=rs.getString("a0288");//职务层次时间
				String a0192e=rs.getString("a0192e");//职级
				String a0192c=rs.getString("a0192c");//职级时间
				String a0243a=rs.getString("a0243a");//职务岗位对应县处级副职领导时间
				String a0243b=rs.getString("a0243b");//职务岗位对应县处级副职非领导时间
				String vxt=rs.getString("v_xt");//职务岗位对应县处级副职领导时间
				String zcfcsj = "";
				a0221 = a0221 ==null ?"":a0221;
				if(vxt.equals("1")) {
					if(a0221.equals("1A41")) {
						if(a0288!=null && !a0288.equals("")) {
							zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
						}
					} else if(a0221.equals("1A32")) {
						if(a0243a!=null && !a0243a.trim().equals("")) {
							zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						} else if(a0288!=null && !a0288.equals("")) {
							zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
						}
					} else if(a0221.equals("1A31")) {
						String f = "";
						if(a0243a!=null && !a0243a.trim().equals("")) {
							f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
							if(a0288!=null && !a0288.equals("")) {
								String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
								if(f.equals(z)) {
									zcfcsj = "("+z+")";
								} else {
									zcfcsj = f+"\r\n("+z+")";
								}
							} else {
								zcfcsj = f;
							}
						} else if(a0243b!=null && !a0243b.trim().equals("")) {
							f = a0243b.substring(2, 4)+"."+a0243b.substring(4, 6) ;
							if(a0288!=null && !a0288.equals("")) {
								String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
								if(f.equals(z)) {
									zcfcsj = "("+z+")";
								} else {
									zcfcsj = f+"\r\n("+z+")";
								}
							} else {
								zcfcsj = f;
							}
						} else if(a0288!=null && !a0288.equals("")) {
							zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
						}
					}
				} else {
					if(a0221.equals("1A41")) {
						if(a0288!=null && !a0288.equals("")) {
							zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6)+"\r\n" + "科级";
						}
					}/* else if(a0221.equals("1A32")) {
						if(a0243a!=null && !a0243a.trim().equals("")) {
							zcfcsj=a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
						} else if(a0288!=null && !a0288.equals("")) {
							zcfcsj=a0288.substring(2, 4)+"."+a0288.substring(4, 6);
						}
					} else if(a0221.equals("1A31")) {
						String f = "";
						if(a0243a!=null && !a0243a.trim().equals("")) {
							f = a0243a.substring(2, 4)+"."+a0243a.substring(4, 6) ;
							if(a0288!=null && !a0288.equals("")) {
								String z = a0288.substring(2, 4)+"."+a0288.substring(4, 6);
								if(f.equals(z)) {
									zcfcsj = "("+z+")";
								} else {
									zcfcsj = f+"\r\n("+z+")";
								}
							} else {
								zcfcsj = f;
							}
						} else if(a0288!=null && !a0288.equals("")) {
							zcfcsj = "("+a0288.substring(2, 4)+"."+a0288.substring(4, 6)+")";
						}
					}*/
				}
				
				String js0109=rs.getString("a0288");//任现职时间
				if(js0109!=null && js0109.length()>=6) {
					js0109=js0109.substring(2, 4)+"."+js0109.substring(4, 6);
				}
				//String js1509=rs.getString("js1509");//备注(部务会)
				//String js1510=rs.getString("js1510");//备注(常委会)
				String js1509="";//备注
				String js1510=js1509;//备注
				
				ExcelReturnParam setRowHeight = new ExcelReturnParam(true,30);
				Row row = ExcelStyleUtil.insertRow(sheet,rowIndex);
				
				Cell xm=row.createCell(0);//姓名
				ExcelStyleUtil.setCellValue(xm, style, a0101);
				
				Cell xrzw=row.createCell(1);//现任职务
				ExcelStyleUtil.setCellValue(xrzw, style, a0192);
				
				Cell nrmzw=row.createCell(2);//拟任免职务
				String nrm = "";
				if(js0111!=null && !js0111.equals("")) {
					nrm += "任" + js0111 +"";
				}
				/*if(js0117!=null && !js0117.equals("")) {
					nrm += "免" + js0117 ;
				}*/
				ExcelStyleUtil.setCellValue(nrmzw, style, nrm);
				
				Cell xb=row.createCell(3);//性别
				if("1".equals(a0104)){
					ExcelStyleUtil.setCellValue(xb, style,"男");
				}else if("2".equals(a0104)){
					ExcelStyleUtil.setCellValue(xb, style,"女");
				}else{
					ExcelStyleUtil.setCellValue(xb, style,"");
				}
				
				
				Cell csrq=row.createCell(4);//出生年月
				ExcelStyleUtil.setCellValue(csrq, style, a0107);
				
				Cell xl=row.createCell(5);//学历
				ExcelStyleUtil.setCellValue(xl, style, qrzxl);
				
				Cell jg=row.createCell(6);//籍贯
				ExcelStyleUtil.setCellValue(jg, style, a0111a);
				
				Cell gzsj=row.createCell(7);//工作时间
				ExcelStyleUtil.setCellValue(gzsj, style, a0134);
				
				Cell rdsj=row.createCell(8);//入党时间
				ExcelStyleUtil.setCellValue(rdsj, style, a0140);
				
				Cell fzcsj=row.createCell(9);//副处职（正处）时间
				/*if(fcsj!=null&&zcsj!=null){
					ExcelStyleUtil.setCellValue(fzcsj, style, fcsj+","+zcsj);
				}else if(fcsj!=null&&zcsj==null){
					ExcelStyleUtil.setCellValue(fzcsj, style, fcsj);
				}else if(fcsj==null&&zcsj!=null){
					ExcelStyleUtil.setCellValue(fzcsj, style, zcsj);
				}else{
					ExcelStyleUtil.setCellValue(fzcsj, style, "");
				}*/
				ExcelStyleUtil.setCellValue(fzcsj, style, zcfcsj);
				
				
				Cell xzsj=row.createCell(10);//现职时间
				ExcelStyleUtil.setCellValue(xzsj, style, js0109);
				
				Cell remark=row.createCell(11);//备注
				ExcelStyleUtil.setCellValue(remark, style, js1510);
/*				if("4_1".equals(tljlbxzlx)){//部务会
					ExcelStyleUtil.setCellValue(remark, style, js1509);
				}else if("4_3".equals(tljlbxzlx)){//常委会
					ExcelStyleUtil.setCellValue(remark, style, js1510);
				}	*/				
				if(rowIndex==3) {
					ms = rowIndex;
					j = fy2200;
				} /*else if(i == list.size()-1) {
					
					if(!fy2200.equals(j)) {
						if(ms != start + i -1) {
							sheet.addMergedRegion(new CellRangeAddress(ms, start + i -1, 1, 1));
						}
						
						ms = start + i;
						j = js2200;
					} else {
						if(ms != start + i) {
							sheet.addMergedRegion(new CellRangeAddress(ms, start + i, 1, 1));
						}
					}
				}*/ else {
					if(!fy2200.equals(j)) {
						if(ms != rowIndex -1) {
							sheet.addMergedRegion(new CellRangeAddress(ms, rowIndex -1, 2, 2));
						}
						ms = rowIndex;
						j = fy2200;
					}
				}
				rowIndex++;
			}
			if(ms != rowIndex-1) {
				sheet.addMergedRegion(new CellRangeAddress(ms, rowIndex-1, 2, 2));
			}
			String filename = "分析研判.xls";
			in.close();
			
			FileOutputStream fout = new FileOutputStream(new File(path1+filename));
			wb.write(fout);
			fout.close();
		    wb.close();
		    this.getPageElement("f").setValue(path1+filename);
		    this.getExecuteSG().addExecuteCode("downFile();");
    	} catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("系统异常！");
        } finally {
			
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
}
