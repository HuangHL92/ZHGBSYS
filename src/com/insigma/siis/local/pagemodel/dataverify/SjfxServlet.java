package com.insigma.siis.local.pagemodel.dataverify;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.utils.CommonQueryBS;

/**
 * Servlet implementation class InsjfxServlet
 */
public class SjfxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		 String method = request.getParameter("method");
		 if(method.equals("initX")){
			 
			 try {
				initX(request,response);
			} catch (AppException e) {
				e.printStackTrace();
			}
		 }else if(method.equals("initY")) {
			 try {
				 initY(request,response);
				} catch (AppException e) {
					e.printStackTrace();
				}
		 }else if(method.equals("initZ")) {
			 try {
				 initZ(request,response);
				} catch (AppException e) {
					e.printStackTrace();
				}
		 }else if(method.equals("initM")) {
			 try {
				 initM(request,response);
				} catch (AppException e) {
					e.printStackTrace();
				}
		 }
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request,response);
    }
	
	
	public void initX(HttpServletRequest request,
			HttpServletResponse response) throws AppException{
	   CommonQueryBS cq=new CommonQueryBS();
	   HBSession sess =HBUtil. getHBSession ();
	   String sql = "select sum (CASE WHEN  b.a0201b  like '001.001%'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) zong ," + 
	   		"        sum (CASE WHEN a.a0104 = '2'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nx ," + 
	   		"        sum (CASE WHEN a.a0117 != '01'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) ssmz ," + 
	   		"        sum (CASE WHEN a.a0141 != '01'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) fdy ," + 
	   		"        sum (CASE WHEN a.a0104 = '2'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) bkxl ," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=30 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl1 ," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=35 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl2," + 
	   		"        sum (CASE WHEN a.a0104 = '2' and a.a0221 in ('1A32','1A31','1A22','1A21','1A12','1A11')" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) cjnx  " + 
	   		"  from A01 a" + 
	   		"  left join (select *" + 
	   		"               from A02" + 
	   		"              where A0279 = '1'" + 
	   		"                and A0255 = '1') b" + 
	   		"    on a.a0000 = b.a0000" + 
	   		" where substr(a.a0163, 0, 1) = '1'" + 
	   		"   and a.status != '4'" + 
	   		"   and b.a0201b like '001.001%'";
		HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
		String zong = (String)mapBySQL.get("zong");     //总人数
		String nx = (String)mapBySQL.get("nx");    		//女性人数
		String nxb = eqxs(zong, nx); 					//女性百分比
		String ssmz = (String)mapBySQL.get("ssmz");     //少数民族人数
		String ssmzb = eqxs(zong, ssmz); 
		String fdy = (String)mapBySQL.get("fdy");    	//非中共人数
		String fdyb = eqxs(zong, fdy); 
		String bkxl = (String)mapBySQL.get("bkxl");     //本科学历以上人数
		String bkxlb = eqxs(zong, bkxl); 
		String nl1 = (String)mapBySQL.get("nl1");       //30岁以下人数
		String nl1b = eqxs(zong, nl1); 
		String nl2 = (String)mapBySQL.get("nl2");       //35岁以下人数
		String nl2b = eqxs(zong, nl2); 
		String cjnx = (String)mapBySQL.get("cjnx");       //处级以上人数
		String cjnxb = eqxs(zong, cjnx); 
		
		String rs = zong+"&"+nx+"&"+nxb+"&"+ssmz+"&"+ssmzb+"&"+fdy+"&"+fdyb+"&"+bkxl+"&"+bkxlb+"&"+nl1+"&"+nl1b+"&"+nl2+"&"+nl2b+"&"+cjnx+"&"+cjnxb;

	   PrintWriter out = null;
	   InputStream in = null;
		try {
			out = response.getWriter();
			out.write(rs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.flush();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	}	

	public void initY(HttpServletRequest request,
			HttpServletResponse response) throws AppException{
	   CommonQueryBS cq=new CommonQueryBS();
	   HBSession sess =HBUtil. getHBSession ();
	   String sql = "select sum (CASE WHEN  b.a0201b like '001.001%'" + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"          END) zong ," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                 THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong1," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A11' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zs," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A12' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fs," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A21' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zt,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A22' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ft," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A31' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zc," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A32' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fc, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A41' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zk, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A42' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fk,     " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A50' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ky," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A60' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) bsy," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 30" + 
	   		"                and a.a0221 = '1A98' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) syq," + 
	   		"       " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                 THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong2," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A11' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zs2," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A12' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fs2," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A21' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zt2,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A22' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ft2," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A31' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zc2," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A32' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fc2, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A41' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zk2, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A42' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fk2,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A50' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ky2," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A60' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) bsy2," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 31" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 40" + 
	   		"                and a.a0221 = '1A98' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) syq2," + 
	   		"       " + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                 THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong3," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A11' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zs3," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A12' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fs3," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A21' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zt3,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A22' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ft3," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A31' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zc3," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A32' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fc3, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A41' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zk3, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A42' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fk3,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A50' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ky3," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A60' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) bsy3," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 41" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 50" + 
	   		"                and a.a0221 = '1A98' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) syq3," + 
	   		"       " + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                 THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong4," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A11' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zs4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A12' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fs4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A21' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zt4,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A22' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ft4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A31' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zc4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A32' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fc4, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A41' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zk4, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"          and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A42' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fk4,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A50' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ky4," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A60' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) bsy4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 51" + 
	   		"        and TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) <= 60" + 
	   		"                and a.a0221 = '1A98' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) syq4," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong5," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A11' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zs5," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A12' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fs5," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A21' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zt5,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A22' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ft5," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A31' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zc5," + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A32' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fc5, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A41' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zk5, " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A42' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) fk5,    " + 
	   		"       sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A50' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) ky5," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A60' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) bsy5," + 
	   		"     sum(CASE" + 
	   		"             WHEN TRUNC(months_between(sysdate,to_date(substr(nvl(a.a0107,'100001'), 0, 6) || '01','yyyymmdd')) / 12) >= 61" + 
	   		"                and a.a0221 = '1A98' THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) syq5" + 
	   		"  from A01 a" + 
	   		"  left join (select *" + 
	   		"               from A02" + 
	   		"              where A0279 = '1'" + 
	   		"                and A0255 = '1') b" + 
	   		"    on a.a0000 = b.a0000" + 
	   		" where substr(a.a0163, 0, 1) = '1'" + 
	   		"   and a.status != '4'" + 
	   		"   and b.a0201b like '001.001%'";
	   HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
	   String zong = (String)mapBySQL.get("zong");    //总人数
	   String zong1 = (String)mapBySQL.get("zong1");    //总人数
	   String zong2 = (String)mapBySQL.get("zong2");    //总人数
	   String zong3 = (String)mapBySQL.get("zong3");    //总人数
	   String zong4 = (String)mapBySQL.get("zong4");    //总人数
	   String zong5 = (String)mapBySQL.get("zong5");    //总人数
	   
	    String zs = (String)mapBySQL.get("zs"); 
	    String zsb = eqxs(zong, zs); 
		String fs = (String)mapBySQL.get("fs");     
		String fsb = eqxs(zong, fs); 
		String zt = (String)mapBySQL.get("zt");  
		String ztb = eqxs(zong, zt);
		String ft = (String)mapBySQL.get("ft"); 
		String ftb = eqxs(zong, ft);
		String zc = (String)mapBySQL.get("zc");    
		String zcb = eqxs(zong, zc);
		String fc = (String)mapBySQL.get("fc");   
		String fcb = eqxs(zong, fc);
		String zk = (String)mapBySQL.get("zk");  
		String zkb = eqxs(zong, zk);
		String fk = (String)mapBySQL.get("fk");     
		String fkb = eqxs(zong, fk);
		String ky = (String)mapBySQL.get("ky");     
		String kyb = eqxs(zong, ky);
		String bsy = (String)mapBySQL.get("bsy");     
		String bsyb = eqxs(zong, bsy);
		String syq = (String)mapBySQL.get("syq");   
		String syqb = eqxs(zong, syq);
		String qtb = eqxs(zong, Integer.parseInt(zong1)-Integer.parseInt(zs)-Integer.parseInt(fs)-Integer.parseInt(zt)-Integer.parseInt(ft)-Integer.parseInt(zc)-Integer.parseInt(fc)-Integer.parseInt(zk)-Integer.parseInt(fk)-Integer.parseInt(ky)-Integer.parseInt(bsy)-Integer.parseInt(syq)+"");
		
		String zs2 = (String)mapBySQL.get("zs2"); 
		String zs2b = eqxs(zong, zs2);
		String fs2 = (String)mapBySQL.get("fs2");   
		String fs2b = eqxs(zong, fs2);
		String zt2 = (String)mapBySQL.get("zt2");  
		String zt2b = eqxs(zong, zt2);
		String ft2 = (String)mapBySQL.get("ft2");   
		String ft2b = eqxs(zong, ft2);
		String zc2 = (String)mapBySQL.get("zc2");   
		String zc2b = eqxs(zong, zc2);
		String fc2 = (String)mapBySQL.get("fc2");  
		String fc2b = eqxs(zong, fc2);
		String zk2 = (String)mapBySQL.get("zk2");   
		String zk2b = eqxs(zong, zk2);
		String fk2 = (String)mapBySQL.get("fk2");   
		String fk2b = eqxs(zong, fk2);
		String ky2 = (String)mapBySQL.get("ky2");
		String ky2b = eqxs(zong, ky2);
		String bsy2 = (String)mapBySQL.get("bsy2");  
		String bsy2b = eqxs(zong, bsy2);
		String syq2 = (String)mapBySQL.get("syq2");
		String syq2b = eqxs(zong, syq2);
		String qt2b = eqxs(zong, Integer.parseInt(zong2)-Integer.parseInt(zs2)-Integer.parseInt(fs2)-Integer.parseInt(zt2)-Integer.parseInt(ft2)-Integer.parseInt(zc2)-Integer.parseInt(fc2)-Integer.parseInt(zk2)-Integer.parseInt(fk2)-Integer.parseInt(ky2)-Integer.parseInt(bsy2)-Integer.parseInt(syq2)+"");
		
		String zs3 = (String)mapBySQL.get("zs3");   
		String zs3b = eqxs(zong, zs3);
		String fs3 = (String)mapBySQL.get("fs3");
		String fs3b = eqxs(zong, fs3);
		String zt3 = (String)mapBySQL.get("zt3");   
		String zt3b = eqxs(zong, zt3);
		String ft3 = (String)mapBySQL.get("ft3");   
		String ft3b = eqxs(zong, ft3);
		String zc3 = (String)mapBySQL.get("zc3"); 
		String zc3b = eqxs(zong, zc3);
		String fc3 = (String)mapBySQL.get("fc3"); 
		String fc3b = eqxs(zong, fc3);
		String zk3 = (String)mapBySQL.get("zk3");   
		String zk3b = eqxs(zong, zk3);
		String fk3 = (String)mapBySQL.get("fk3");   
		String fk3b = eqxs(zong, fk3);
		String ky3 = (String)mapBySQL.get("ky3");
		String ky3b = eqxs(zong, ky3);
		String bsy3 = (String)mapBySQL.get("bsy3"); 
		String bsy3b = eqxs(zong, bsy3);
		String syq3 = (String)mapBySQL.get("syq3");
		String syq3b = eqxs(zong, syq3);
		String qt3b = eqxs(zong, Integer.parseInt(zong3)-Integer.parseInt(zs3)-Integer.parseInt(fs3)-Integer.parseInt(zt3)-Integer.parseInt(ft3)-Integer.parseInt(zc3)-Integer.parseInt(fc3)-Integer.parseInt(zk3)-Integer.parseInt(fk3)-Integer.parseInt(ky3)-Integer.parseInt(bsy3)-Integer.parseInt(syq3)+"");

		
		String zs4 = (String)mapBySQL.get("zs4");   
		String zs4b = eqxs(zong, zs4);
		String fs4 = (String)mapBySQL.get("fs4");   
		String fs4b = eqxs(zong, fs4);
		String zt4 = (String)mapBySQL.get("zt4");   
		String zt4b = eqxs(zong, zt4);
		String ft4 = (String)mapBySQL.get("ft4");   
		String ft4b = eqxs(zong, ft4);
		String zc4 = (String)mapBySQL.get("zc4"); 
		String zc4b = eqxs(zong, zc4);
		String fc4 = (String)mapBySQL.get("fc4");   
		String fc4b = eqxs(zong, fc4);
		String zk4 = (String)mapBySQL.get("zk4");   
		String zk4b = eqxs(zong, zk4);
		String fk4 = (String)mapBySQL.get("fk4");  
		String fk4b = eqxs(zong, fk4);
		String ky4 = (String)mapBySQL.get("ky4");
		String ky4b = eqxs(zong, ky4);
		String bsy4 = (String)mapBySQL.get("bsy4"); 
		String bsy4b = eqxs(zong, bsy4);
		String syq4 = (String)mapBySQL.get("syq4");
		String syq4b = eqxs(zong, syq4);
		String qt4b = eqxs(zong, Integer.parseInt(zong4)-Integer.parseInt(zs4)-Integer.parseInt(fs4)-Integer.parseInt(zt4)-Integer.parseInt(ft4)-Integer.parseInt(zc4)-Integer.parseInt(fc4)-Integer.parseInt(zk4)-Integer.parseInt(fk4)-Integer.parseInt(ky4)-Integer.parseInt(bsy4)-Integer.parseInt(syq4)+"");

		
		String zs5 = (String)mapBySQL.get("zs5");   
		String zs5b = eqxs(zong, zs5);
		String fs5 = (String)mapBySQL.get("fs5");  
		String fs5b = eqxs(zong, fs5);
		String zt5 = (String)mapBySQL.get("zt5"); 
		String zt5b = eqxs(zong, zt5);
		String ft5 = (String)mapBySQL.get("ft5");  
		String ft5b = eqxs(zong, ft5);
		String zc5 = (String)mapBySQL.get("zc5"); 
		String zc5b = eqxs(zong, zc5);
		String fc5 = (String)mapBySQL.get("fc5");   
		String fc5b = eqxs(zong, fc5);
		String zk5 = (String)mapBySQL.get("zk5");   
		String zk5b = eqxs(zong, zk5);
		String fk5 = (String)mapBySQL.get("fk5");   
		String fk5b = eqxs(zong, fk5);
		String ky5 = (String)mapBySQL.get("ky5");
		String ky5b = eqxs(zong, ky5);
		String bsy5 = (String)mapBySQL.get("bsy5");   
		String bsy5b = eqxs(zong, bsy5);
		String syq5 = (String)mapBySQL.get("syq5");
		String syq5b = eqxs(zong, syq5);
		String qt5b = eqxs(zong, Integer.parseInt(zong5)-Integer.parseInt(zs5)-Integer.parseInt(fs5)-Integer.parseInt(zt5)-Integer.parseInt(ft5)-Integer.parseInt(zc5)-Integer.parseInt(fc5)-Integer.parseInt(zk5)-Integer.parseInt(fk5)-Integer.parseInt(ky5)-Integer.parseInt(bsy5)-Integer.parseInt(syq5)+"");

				
		String rs = zsb+"&"+fsb+"&"+ztb+"&"+ftb+"&"+zcb+"&"+fcb+"&"+zkb+"&"+fkb+"&"+kyb+"&"+bsyb+"&"+syqb+"&"+qtb+"&"+zs2b+"&"+fs2b+"&"+zt2b+"&"+ft2b+"&"+zc2b+"&"+fc2b+"&"+zk2b+"&"+fk2b+"&"+ky2b+"&"+bsy2b+"&"+syq2b+"&"+qt2b+"&"+zs3b+"&"+fs3b+"&"+zt3b+"&"+ft3b+"&"+zc3b+"&"+fc3b+"&"+zk3b+"&"+fk3b+"&"+ky3b+"&"+bsy3b+"&"+syq3b+"&"+qt3b+"&"+zs4b+"&"+fs4b+"&"+zt4b+"&"+ft4b+"&"+zc4b+"&"+fc4b+"&"+zk4b+"&"+fk4b+"&"+ky4b+"&"+bsy4b+"&"+syq4b+"&"+qt4b+"&"+zs5b+"&"+fs5b+"&"+zt5b+"&"+ft5b+"&"+zc5b+"&"+fc5b+"&"+zk5b+"&"+fk5b+"&"+ky5b+"&"+bsy5b+"&"+syq5b+"&"+qt5b;

	   PrintWriter out = null;
	   InputStream in = null;
		try {
			out = response.getWriter();
			out.write(rs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.flush();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	   
	   
	}  
	
	public void initZ(HttpServletRequest request,
			HttpServletResponse response) throws AppException{
	   CommonQueryBS cq=new CommonQueryBS();
	   HBSession sess =HBUtil. getHBSession ();
	   String sql = "select sum(CASE" + 
	   		"             WHEN 1=1" + 
	   		"                   THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zong," + 
	   		"       sum(CASE" + 
	   		"             WHEN b.a0801b in('11','1A','1B','13')" + 
	   		"                   THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) yjs," + 
	   		"       sum(CASE" + 
	   		"             WHEN b.a0801b in('21','2A','2B')" + 
	   		"                   THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) dxbk," + 
	   		"       sum(CASE" + 
	   		"             WHEN b.a0801b in('31','34','37','3B')" + 
	   		"                   THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) dxzk," + 
	   		"       sum(CASE" + 
	   		"             WHEN b.a0801b in('41','47','61','71','81','91')" + 
	   		"                   THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"           END) zzyx" + 
	   		"  from A01 a" + 
	   		"  left join (select *" + 
	   		"               from A08" + 
	   		"              where A0834 = '1') b" + 
	   		"    on a.a0000 = b.a0000" + 
	   		" where substr(a.a0163, 0, 1) = '1'" + 
	   		"   and a.status != '4'";
	   HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
	   String zong = (String)mapBySQL.get("zong");    //总人数
	   String yjs = (String)mapBySQL.get("yjs");    //研究生
	   String dxbk = (String)mapBySQL.get("dxbk");    //大学本科
	   String dxzk = (String)mapBySQL.get("dxzk");    //大学专科
	   String zzyx = (String)mapBySQL.get("zzyx");    //中专以下
	   String bkxl = Integer.parseInt(yjs)+Integer.parseInt(dxbk)+"";     //本科学历
	   String bkxlb = eqxs(zong, Integer.parseInt(yjs)+Integer.parseInt(dxbk)+""); //本科以上百分比
	   int qt = Integer.parseInt(zong)-Integer.parseInt(yjs)-Integer.parseInt(dxbk)-Integer.parseInt(dxzk)-Integer.parseInt(zzyx);
	   zzyx = Integer.parseInt(zzyx)+qt+"";
	   String rs = yjs+"&"+dxbk+"&"+dxzk+"&"+zzyx+"&"+bkxl+"&"+bkxlb;

	   PrintWriter out = null;
	   InputStream in = null;
		try {
			out = response.getWriter();
			out.write(rs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.flush();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	}
	
	public void initM(HttpServletRequest request,
			HttpServletResponse response) throws AppException{
	   CommonQueryBS cq=new CommonQueryBS();
	   HBSession sess =HBUtil. getHBSession ();
	   String sql = "select  sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=20 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl1 ," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=25 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>20 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl2," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=30 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>25 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl3," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=35 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>30 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl4," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=40 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>35 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl5," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=45 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>40 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl6," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=50 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>45 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl7, " + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=55 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>50 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl8," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=60 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>55 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl9," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)<=65 " + 
	   		"                   and TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>60 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl10," + 
	   		"        sum (CASE WHEN TRUNC(months_between(sysdate, to_date(substr(nvl(a.a0107,'100001'),0,6)||'01' ,'yyyymmdd'))/12)>=66 " + 
	   		"             THEN" + 
	   		"              1" + 
	   		"             ELSE" + 
	   		"              0" + 
	   		"        END) nl11                        " + 
	   		"           from A01 a left join (select * from A02 where A0279='1' and A0255='1') b on a.a0000 = b.a0000 " + 
	   		"           where substr(a.a0163, 0, 1) = '1'" + 
	   		"           and a.status != '4'" + 
	   		"           and b.a0201b like '001.001%'";
	   HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
	   String nl1 = (String)mapBySQL.get("nl1");    
	   String nl2 = (String)mapBySQL.get("nl2");    
	   String nl3 = (String)mapBySQL.get("nl3");    
	   String nl4 = (String)mapBySQL.get("nl4");    
	   String nl5 = (String)mapBySQL.get("nl5");    
	   String nl6 = (String)mapBySQL.get("nl6");    
	   String nl7 = (String)mapBySQL.get("nl7");    
	   String nl8 = (String)mapBySQL.get("nl8");    
	   String nl9 = (String)mapBySQL.get("nl9");    
	   String nl10 = (String)mapBySQL.get("nl10");    
	   String nl11 = (String)mapBySQL.get("nl11");    
	   
	   String rs = nl1+"&"+nl2+"&"+nl3+"&"+nl4+"&"+nl5+"&"+nl6+"&"+nl7+"&"+nl8+"&"+nl9+"&"+nl10+"&"+nl11;

	   PrintWriter out = null;
	   InputStream in = null;
		try {
			out = response.getWriter();
			out.write(rs);
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.flush();
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(out!=null){
				out.close();
			}
		}
	}
	
	/**
	 * 处理百分比方法
	 * @param list
	 * @return
	 */
	public String eqxs(String zs,String fs){
		String percent = "";
		BigDecimal aBD = new BigDecimal(zs);
        BigDecimal bBD = new BigDecimal(fs);
        if(zs.equals("0")){
        	  DecimalFormat df = new DecimalFormat("0%");  
              BigDecimal d=new BigDecimal("0");
              percent=df.format(d);
        	
        }else{
        	BigDecimal resultBD = bBD.divide(aBD,2, java.math.BigDecimal.ROUND_HALF_UP);
            String result=resultBD.toString();
            DecimalFormat df = new DecimalFormat("0%");  
            BigDecimal d=new BigDecimal(result);
            percent=df.format(d);
        }
        
		return percent;
    }
	
	/**
	 * 处理小数方法
	 * @param list
	 * @return
	 */
	public String eqxx(String zs,String fs){
		BigDecimal aBD = new BigDecimal(zs);
        BigDecimal bBD = new BigDecimal(fs);
        BigDecimal resultBD = bBD.divide(aBD,2, java.math.BigDecimal.ROUND_HALF_UP);
        String result=resultBD.toString();
        DecimalFormat df = new DecimalFormat("0%");  
        BigDecimal d=new BigDecimal(result);
        String percent=df.format(d);
        String xs=percent.replace("%","");
		return xs;
    }
	
	/**
	 * 换算成以万为单位的数
	 * @param result
	 * @return
	 */
	public String eqw(String result){
		int a=Integer.valueOf(getString(result));
		if(a>=10000){
			BigDecimal aBD = new BigDecimal(result);
	        BigDecimal bBD = new BigDecimal("10000");
	        BigDecimal resultBD = aBD.divide(bBD,4, java.math.BigDecimal.ROUND_HALF_UP);
	        String re=resultBD.toString();
	        return re;
		}else{
			return result;
		}
		
			
		
    }
	
	
	/**
	 * 处理取数中含有小数点的值
	 * @param str
	 * @return
	 */
	public String getString(String str){
		if(str.indexOf(".")>-1){
		  str=str.substring(0, str.indexOf("."));
	 	}
		return str;
	}

}
