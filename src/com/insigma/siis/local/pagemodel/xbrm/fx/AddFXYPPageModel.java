package com.insigma.siis.local.pagemodel.xbrm.fx;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.FxypBatch;
import com.insigma.siis.local.business.entity.YJMX;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.utils.CommonParamUtil;
import com.utils.CommonQueryBS;
import com.utils.DBUtils;

public class AddFXYPPageModel extends PageModel {
	
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	
	public int save(String id) throws RadowException{
		String fy_id = this.getPageElement("fyId").getValue();
		FxypBatch fy = new FxypBatch();
		
		try {
			HBSession sess = HBUtil.getHBSession();
			PMPropertyCopyUtil.copyElementsValueToObj(fy, this);
			String rbidnew=UUID.randomUUID().toString();
			fy.setFystatus("0");
			fy.setFyId(rbidnew);
			fy.setFyUserid(SysManagerUtils.getUserId());
			fy.setFySysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
			sess.save(fy);
			sess.flush();
			
			this.getExecuteSG().addExecuteCode("window.close();");
			//this.getExecuteSG().addExecuteCode("saveCallBack('新增');");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	public int done() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String userid = SysManagerUtils.getUserId();
		//HBTransaction tr = null;
		String tplb = "101";//调配类别
		String fyid = UUID.randomUUID().toString();
		String rbId = fyid;
        String cur_hj = "0";//环节
        String cur_hj_4 = "4_1";//讨论决定分环节
        String dc005 = this.request.getParameter("dc005");
		try {
			//tr = sess.beginTransaction();
			/*FxypBatch fxyp = (FxypBatch) sess.get(FxypBatch.class, fyid);
			if(fxyp.getFystatus()!=null && fxyp.getFystatus().equals("1")) {
				this.setMainMessage("此条数据已完成，请重新查询。");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			String fy_name = this.getPageElement("fyName").getValue();
			String fy_date = this.getPageElement("fyDate").getValue();
			String fy_no = this.getPageElement("fyNo").getValue();
			String fy_applicant = this.getPageElement("fyApplicant").getValue();
			String fy_org = this.getPageElement("fyOrg").getValue();
			BigDecimal fy_sysno = BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004")));
			String fy_meettype = this.getPageElement("fymeettype").getValue();
			String fy_deploytype = this.getPageElement("fydeplytype").getValue();
			String fy_approve = this.getPageElement("fyapprove").getValue();
			String fy_reportreson = this.getPageElement("fyreportreson").getValue();
			String fy_reportvalue = this.getPageElement("fyreportvalue").getValue();
			tplb = fy_deploytype;
			
			String insertsql = "insert into record_batch(rb_id,rb_name,rb_date,rb_type,rb_userid,rb_no,rb_applicant,rb_org,rb_sysno,rb_cdate,rb_updated,rb_meettype,rb_deploytype,rb_approve,rb_reportreson,rb_reportvalue,rb_leadview,rb_status,rbm_id,rbm_status) values ("
					+ "'"+rbId+"','"+fy_name+"','"+fy_date+"','','"+userid+"','"+fy_no+"','"+fy_applicant+"','"+fy_org+"',"+fy_sysno+",sysdate,null,'"+fy_meettype+"','"+fy_deploytype+"','"+fy_approve+"','"+fy_reportreson+"','"+fy_reportvalue+"','','0','','')";
			sess.createSQLQuery(insertsql).executeUpdate();
			
			//调配类别 不在数据库里的则新增。
	        List dclist = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where dc001=?").setString(0, tplb).list();
	        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
	        //调配类别 不在数据库里的则新增。
	        if (dclist.size() == 0 && !"".equals(tplb)) {
	            String id = UUID.randomUUID().toString();
	            String slq = "insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
	                    + " values('"+id+"','"+rbId+"',(select CODE_NAME from code_value where CODE_TYPE='DPLX' and CODE_VALUE='"+tplb+"'),deploy_classify_dc004.nextval,'"+(RMHJ.REN_MIAN_ZHI.equals(cur_hj) ? "2" : "1")+"')";
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
	        String sql_fy = "select distinct a.a0000,a.fy0122 from fy01 a,fy25 b where a.fy0100=b.fy0100 and b.userid='"+userid+"' group by a.a0000,a.fy0122 ";
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
            	
            	/*String insertsql2 = "insert into js22(js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,js0100,js2300,rbd000,rbd001,js2201a,js2201b,js2201c,sortid,js2207,rb_id) "
    					+ " select fy2201,fy2202,fy2203,sys_guid(),a.a0000,fy2204,fy2205,fy2206,a.js0100,fy2300,rbd000,rbd001,fy2201a,fy2201b,fy2201c,sortid,fy2207,rb_id from js01 a,fy01 b,fy22 c,fy25 d where a.a0000=b.a0000 and a.js0122=b.fy0122 and b.fy0100=d.fy0100 and c.fy2200=d.fy2200 and a.rb_id='"+rbId+"' and d.userid='"+userid+"'";*/
            	//默认都是主职务，想修改可以去拟任窗口修改
            	String insertsql2 = "insert into js22(js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,js0100,js2300,rbd000,rbd001,js2201a,js2201b,js2201c,sortid,js2207,rb_id) "
    					+ " select fy2201,fy2202,fy2203,sys_guid(),a.a0000,fy2204,fy2205,fy2206,a.js0100,fy2300,rbd000,rbd001,fy2201a,fy2201b,fy2201c,sortid,'1',rb_id from js01 a,fy01 b,fy22 c,fy25 d where a.a0000=b.a0000 and a.js0122=b.fy0122 and b.fy0100=d.fy0100 and c.fy2200=d.fy2200 and a.rb_id='"+rbId+"' and d.userid='"+userid+"'";
            	sess.createSQLQuery(insertsql2).executeUpdate();
            	System.out.println(insertsql2);
            	//sess.flush();
            	//tr.commit();
            
	        }
	        this.getExecuteSG().addExecuteCode("radow.doEvent('scyj','"+rbId+"');");
	        
	        //删除已经设置好的职位 和  人员，包括 职位——人员 的对应表
	        String Deletesql = "select a.fy0100,b.fy2200 from fy01 a,fy25 b where a.fy0100 = b.fy0100 and b.userid = '"+userid+"'";
	        List<Object[]> listDelete = sess.createSQLQuery(Deletesql).list();
	        String fy01s = "";
	        String fy22s = "";
	        if(listDelete!=null&&listDelete.size()>0){
	        	for(Object[] objs : listDelete){
	        		String fy0100 = ""+objs[0];
	        		String fy2200 = ""+objs[1];
	        		
	        		fy01s = fy01s + "'" + fy0100 + "',";
	        		fy22s = fy22s + "'" + fy2200 + "',";
	        	}
	        	
	        	fy01s = fy01s.substring(0, fy01s.length()-1);
	        	fy22s = fy22s.substring(0, fy22s.length()-1);
	        }
	        sess.createSQLQuery("delete from fy01 where fy0100 in ("+fy01s+")").executeUpdate();
	        sess.createSQLQuery("delete from fy25 where fy0100 in ("+fy01s+")").executeUpdate();
	        sess.createSQLQuery("delete from fy22 where fy2200 in ("+fy22s+")").executeUpdate();
	        
	        this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示', '设置完成',function(e){window.close();"
	        		+ "realParent.radow.doEvent('gridComp.dogridquery');realParent.radow.doEvent('NiRenGrid.dogridquery');"
	        		+ "realParent.radow.doEvent('gridcq.dogridquery');"
	        		+ "})");
		} catch (Exception e) {
			e.printStackTrace();
			/*if(tr!=null) {
        		try {
					tr.rollback();
				} catch (AppException e1) {
					e1.printStackTrace();
				}
        	}*/
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
		
		//String xzfn = CommonParamUtil.PARAM_MAP.get("default_warning_plan_id"); //使用默认方案
		
		try {
			HBSession sess = HBUtil.getHBSession();
			List<Object[]> listhja0000s = sess.createSQLQuery(hjsql).list(); //拟任人员名单
			
			String sqlstr = "select t.chinesename,t.qrysql,t.yjtype,t.qrysql2 from JS_YJTJ t,JS_YJTJ_ref_fn r "
					+ " where t.qvid=r.qvid "; 
			List<Object[]> listsqls = sess.createSQLQuery(sqlstr).list(); //方案里的条件
			
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
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String fy_id = this.getPageElement("fyId").getValue();
		HBSession sess = HBUtil.getHBSession();
		
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		CommonQueryBS cq=new CommonQueryBS();
		String sql="select smt_usergroup.usergroupname,smt_user.loginname from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
		
		try {
			HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
			String usergroupname=(String)mapBySQL.get("usergroupname");
			String loginname=(String)mapBySQL.get("loginname");
			this.getPageElement("fyApplicant").setValue(loginname);
			this.getPageElement("fyOrg").setValue(usergroupname);
			
			if(fy_id!=null&&!"".equals(fy_id)){
				FxypBatch fy = (FxypBatch)sess.get(FxypBatch.class, fy_id);
				PMPropertyCopyUtil.copyObjValueToElement(fy, this);
				this.getExecuteSG().addExecuteCode("initselect();");
			} else {
				this.getPageElement("fyNo").setValue(DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmm"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
