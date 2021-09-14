package com.insigma.siis.local.pagemodel.xbrm.fx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.FxypBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.utils.CommonQueryBS;

public class DealFXYPPageModel extends PageModel {
	
	
	/**
	 * 批次信息修改保存
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save")
	public int save(String id) throws RadowException{
    	String fy2201=this.getPageElement("fy2201").getValue();
    	String fy2202=this.getPageElement("fy2202").getValue();
    	String fy2203=this.getPageElement("fy2203").getValue();
    	String fy2204=this.getPageElement("fy2204").getValue();
    	String fy2207=this.getPageElement("fy2207").getValue();
    	
    	String fy2200=UUID.randomUUID().toString();
  
    	String fyId= this.getPageElement("fyId").getValue();
    	HBSession sess =HBUtil.getHBSession();
    	try {
    		B01 b01 = (B01) sess.get(B01.class, fy2202);
    		
    		BigDecimal c = (BigDecimal) sess.createSQLQuery("select max(nvl(sortid,0)) from fy22 where "
    				+ " fy22.fy_Id in ('"+fyId+"')").uniqueResult();
    		int sortid = (c==null ? 0:c.intValue()) + 1;
    		String b0104 = b01.getB0104();//简称
    		String b0194 = b01.getB0194();//单位类型
    		if(b0194.equals("3")) {
    			this.setMainMessage("不能选择机构分组！");
    			return EventRtnType.NORMAL_SUCCESS;
    		} else if(b0194.equals("2")) {
    			String name[] = new String[] {"",""};
    			name = getFrdwName(fy2202 , name);
    			HBUtil.executeUpdate("insert into fy22(fy2200,fy2201,fy2202,fy2203,a0000,fy2204,fy0100,"
    					+ "fy2201a,fy2201b,fy2201c,sortid,fy2207,fy_id) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)",new Object[]{fy2200,fy2201,fy2202
    					,fy2203,"",fy2204,"",b0104,name[1],name[0],sortid,fy2207,fyId});
    		} else {
    			HBUtil.executeUpdate("insert into fy22(fy2200,fy2201,fy2202,fy2203,a0000,fy2204,fy0100,"
    					+ "fy2201a,fy2201b,fy2201c,sortid,fy2207,fy_id) values(?,?,?,?,?, ?,?,?,?,?, ?,?,?)",new Object[]{fy2200,fy2201,fy2202
    					,fy2203,"",fy2204,"",b0104,b0104,fy2202,sortid,fy2207,fyId});
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败!");
			return EventRtnType.NORMAL_SUCCESS;
		}
    	this.setNextEventName("NiRenGrid.dogridquery");
    	return EventRtnType.NORMAL_SUCCESS;
    }
	private String[] getFrdwName(String codevalueparameter2, String name[]) throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		try {
			B01 b01 = (B01) sess.get(B01.class, codevalueparameter2);
			if(b01.getB0194().equals("1") || codevalueparameter2.equals("001.001")) {
				name[0] = b01.getB0101()+name[0];
				name[1] = b01.getB0104()+name[1];
				return name;
			} else {
				name[0] = b01.getB0101()+name[0];
				name[1] = b01.getB0104()+name[1];
				return getFrdwName(b01.getB0121(), name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据异常!");
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
			this.getExecuteSG().addExecuteCode("radow.doEvent('NiRenGrid.dogridquery');radow.doEvent('gridcq.dogridquery');radow.doEvent('gridComp.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("NiRenGrid.dogridquery")
    @NoRequiredValidate
    public int  niRenGridQuery(int start,int limit) throws RadowException{
		String fy_id = this.getPageElement("fyId").getValue();
    	String sql="select * from fy22 where fy_Id='"+fy_id+"' order by sortid";
    	this.pageQuery(sql, "SQL", start, limit);
    	return EventRtnType.SPE_SUCCESS;
    }
	@PageEvent("delete22row")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int delete22row(String fy2200) {
		HBSession sess = HBUtil.getHBSession();
    	try {
    		sess.createSQLQuery("delete from fy22 where fy2200='"+fy2200+"'").executeUpdate();
    		sess.createSQLQuery("delete from fy25 where fy2200='"+fy2200+"'").executeUpdate();
			this.setNextEventName("NiRenGrid.dogridquery");
			this.setMainMessage("删除成功!");
			this.getExecuteSG().addExecuteCode("radow.doEvent('NiRenGrid.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("删除失败!");
		}	
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	//按姓名查询，传递人员IDs
    @PageEvent("queryByNameAndIDS")
    public int queryByNameAndIDS(String listStr) throws Exception {
        //String tplb = this.getPageElement("tplb").getValue();//调配类别
        //调配类别 不在数据库里的则新增。
        HBSession sess = HBUtil.getHBSession();
        HBTransaction tr = sess.beginTransaction();
        //List dclist = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where dc001=?").setString(0, tplb).list();

        String fy_id = this.getPageElement("fyId").getValue();
        String cur_hj = "0";//环节
        String cur_hj_4 = "4-1";//讨论决定分环节
        String dc005 = "1";
        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);

        //调配类别 不在数据库里的则新增。
        /*if (dclist.size() == 0 && !"".equals(tplb)) {
            String id = UUID.randomUUID().toString();
            String slq = "insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
                    + " values('"+id+"','"+rbId+"','"+tplb+"',deploy_classify_dc004.nextval,'"+(RMHJ.REN_MIAN_ZHI.equals(cur_hj) ? "2" : "1")+"')";
            sess.createSQLQuery(slq).executeUpdate();
            tplb = id;
            setGridCombo();
        } else {
        	List dclist2 = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where rb_id=?").setString(0, rbId).list();
        	tplb = dclist2.get(0) + "";
        }*/
        //RMHJ.InsertSqlMap sm = RMHJ.getInsertSqlMap(cur_hj, cur_hj_4, dc005, tplb);
        StringBuffer sql = new StringBuffer("insert into fy01(fy0100, a0000, fy_id,fy0102, fy0103, fy0104, fy0105, fy0106, fy0114,fy0108,fy0110,fy0109,dc001,fy0113,fy0122)  "
                + "(select sys_guid(),a0000,'" + fy_id + "' fy_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192a, a0288 || ' ' || a0192c rtzjsj,"
                + "(select replace(TO_CHAR(wm_concat(a0243)), ',', ' ') from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" /*+ tplb*/ + "',deploy_classify_dc004.nextval,（VXT） from （A01） a01 ");
        /*StringBuffer hjSql = new StringBuffer("insert into js_hj(fy0100,JS_TYPE," + sm.hj_sort + sm.thapfield + ") (select fy0100,'" + cur_hj + "',fy0113 " + sm.thapvalue + " from fy01 where rb_id='" + rbId + "' "
                + " and not exists (select 1 from js_hj where js_hj.fy0100=fy01.fy0100 and js_type='" + cur_hj + "')  ");
        hjSql.append(" and a0000 in ('-1'");*/
        sql.append(" where a0000 in ('-1'");
        if (listStr != null && listStr.length() > 2) {
            listStr = listStr.substring(1, listStr.length() - 1);
            List<String> list = Arrays.asList(listStr.split(","));
            StringBuffer xt1a0000s = new StringBuffer();
            StringBuffer xt2a0000s = new StringBuffer();
            StringBuffer xt3a0000s = new StringBuffer();
            StringBuffer xt4a0000s = new StringBuffer();
            /*StringBuffer jshisfy0100 = new StringBuffer();
            StringBuffer jshisfy01002 = new StringBuffer();*/
            
            for (String idparam : list) {
               /* sql.append(",'" + id.trim() + "'");
                hjSql.append(",'" + id.trim() + "'");*/
            	String idarr[] = idparam.split("@");
            	/*String sql_1 = "select a0101,jsh001,fy0100,js_his_id from js_his where a0000='"+idarr[0]+"' and fy0122='"+idarr[1]+"' and jsh001 in('1','3') and jsh004=1";
    			List<Object[]> list_1 = sess.createSQLQuery(sql_1).list();
    			if(list_1!=null && list_1.size()>0) {
    				jshisfy0100.append(",'" + list_1.get(0)[2] + "'");
    			} else {*/
    				if(idarr[1].equals("1")) {
                		xt1a0000s.append(",'" + idarr[0].trim() + "'");
                	} else if(idarr[1].equals("2")) {
                		xt2a0000s.append(",'" + idarr[0].trim() + "'");
                	} else if(idarr[1].equals("3")) {
                		xt3a0000s.append(",'" + idarr[0].trim() + "'");
                	} else if(idarr[1].equals("4")) {
                		xt4a0000s.append(",'" + idarr[0].trim() + "'");
                	}
    				
    			/*}*/
            }
            //sql.append(") and not exists (select 1 from fy01 where fy01.a0000=a01.a0000 and fy01.fy_id='" + rbId + "' ))");
            String sqlend1 = ") and not exists (select 1 from fy01 where fy01.a0000=a01.a0000 and fy01.fy_id='" + fy_id + "' ))";
            String sqlend2 = ") and not exists (select 1 from fy01 where fy01.a0000=a01.a0000 and fy01.fy_id='" + fy_id + "' )";
            
            try {
                //hjSql.append("))");
            	if(xt1a0000s.length()>0) {
            		String sql1 = (sql.toString() + xt1a0000s+sqlend1).replace("（A01）", "a01").replace("（A02）", "a02").replace("and  a02.v_xt=（VXT）", "").replace("（VXT）", "'1'");
            		/*String sql2 = hjSql.toString() + xt1a0000s+"))";*/
            		sess.createSQLQuery(sql1).executeUpdate();
            		/*sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt1a0000s+") and fy0122='1'";
            		sess.createSQLQuery(u1).executeUpdate();*/
            	}
            	if(xt2a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt2a0000s+sqlend2+" and v_xt='2')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'2'");
            		/*String sql2 = hjSql.toString()+xt2a0000s+"))";*/
            		sess.createSQLQuery(sql1).executeUpdate();
            		/*sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt2a0000s+") and fy0122='2'";
            		sess.createSQLQuery(u1).executeUpdate();*/
            	}
            	if(xt3a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt3a0000s+sqlend2+" and v_xt='3')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'3'");
            		/*String sql2 = hjSql.toString()+xt3a0000s+"))";*/
            		sess.createSQLQuery(sql1).executeUpdate();
            		/*sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt3a0000s+") and fy0122='3'";
            		sess.createSQLQuery(u1).executeUpdate();*/
            	}
            	if(xt4a0000s.length()>0) {
            		String sql1 = (sql.toString()+xt4a0000s+sqlend2+" and v_xt='4')").replace("（A01）", "V_js_A01").replace("（A02）", "V_js_A02").replace("（VXT）", "'4'");
            		/*String sql2 = hjSql.toString()+xt4a0000s+"))";*/
            		sess.createSQLQuery(sql1).executeUpdate();
            		/*sess.createSQLQuery(sql2).executeUpdate();
            		String u1 = "update JS_HIS set JSH004='0' where a0000 in ('-1'"+xt4a0000s+") and fy0122='4'";
            		sess.createSQLQuery(u1).executeUpdate();*/
            	}
            	/*if(jshisfy0100.length()>0) {
            		String sql1 = "insert into fy01 select fy0100,a0000,dc001,'"+rbId+"',fy0102,fy0103,fy0104,fy0105,fy0106,fy0107,fy0108,fy0109,fy0110,fy0111,fy0112,fy0113,fy0114,fy0115,fy0116,fy0117,fy0118,fy0119,fy0120,fy0121,fy0122,fy0123,fy0111a,fy0117a from fy01_his where fy0100 in ('-1'"+jshisfy0100+")";
            		//System.out.println(sql1);
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js02 select fy0100,dc001,'"+rbId+"',js0202,js0203,js0204,js0205,js0206,js0207,js0208,js0209,js0210,js0211 from js02_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js03 select fy0100,dc001,'"+rbId+"',js0302,js0303,js0304,js0305,js0306,js0307,js0308,js0309,js0310,js0311 from js03_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js04 select fy0100,dc001,'"+rbId+"',js0402,js0403 from js04_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js06 select fy0100,dc001,'"+rbId+"',js0602,js0603,js0604,js0605 from js06_his where fy0100 in ('-1'"+jshisfy0100+")";
            		System.out.println(sql1);
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js08 select fy0100,dc001,'"+rbId+"',js0802,js0803,js0804,js0805,js0806,js0807,js0808 from js08_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js09 select fy0100,dc001,'"+rbId+"',js0902,js0903,js0904,js0905,js0906 from js09_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js10 select fy0100,dc001,'"+rbId+"',js1002,js1003,js1004 from js10_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js11 select fy0100,dc001,'"+rbId+"',js1102,js1103,js1104,js1105,js1106 from js11_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js14 select fy0100,dc001,'"+rbId+"',js1401,js1402,js1403,js1404,js1405,js1406,js1407,js1408,js1409,js1410,js1411,js1412,js1413,js1414,js1415,js1416,js1417,js1418,js1419,js1420,js1421,js1422,js1423,js1424,js1425,js1426,js1427,js1428,js1429,js1430,js1431,js1432,js1433,js1434,js1435,js1436,js1437,js1438,js1439,js1440,js1441,js1442,js1443,js1444,js1445,js1446,js1447,js1448,js1449,js1414a from js14_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js15 select fy0100,dc001,'"+rbId+"',js1501,js1502,js1503,js1504,js1505,js1506,js1507,js1508,js1509,js1510,js1511,js1512,js1513,js1514 from js15_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js18 select fy0100,dc001,'"+rbId+"',js1801,js1802,js1803,js1804,js1805 from js18_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js19 select fy0100,dc001,'"+rbId+"',js1902,js1903,js1904,js1905,js1906,js1801,js1907,js1908,js1909,js1910,js1911,js1912 from js19_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js20 select fy0100,dc001,'"+rbId+"',js2002,js2003,js2004,js2005,js2006,js2007 from js20_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js21 select fy0100,dc001,'"+rbId+"',js2100,js2101,js2102,js2103,js2104,js2105,js2106,js2107,js2108,js2109 from js21_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js22 select js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,fy0100,js2300,rbd000,rbd001,js2201a,js2201b,js2201c,sortid,js2207 from js22_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js23 select fy0100,a0000,'"+rbId+"',js2300,js2301,js2302,js2303,js2304,js2305,js2306,js2307,js2308,js2309,js2302a,js2302b,js2303a,js2303b,js2310 from js23_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js24 select js2401,js2402,js2403,js2400,a0000,js2404,js2405,js2406,fy0100,js2300,rbd000,rbd001,js2401a,js2401b,js2401c,a0200 from js24_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		sql1 = "insert into js_hj select fy0100,js_type,js_sort,js_class_dc001_2,js_class2,js_sort4,js_sort_dc005_2 from js_hj_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		
            		List<Object[]> list3 = sess.createSQLQuery("select jsa00,jsa07 from js_att_his where fy0100  in ('-1'"+jshisfy0100+")").list();
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
            		sql1 = "insert into js_att select jsa00,fy0100,'"+rbId+"',jsa02,jsa03,jsa04,jsa05,jsa06,'zhgbuploadfiles"+File.separator + rbId + File.separator+"',jsa08 from js_att_his where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(sql1).executeUpdate();
            		
            		String u1 = "update JS_HIS set JSH004='0' where fy0100 in ('-1'"+jshisfy0100+")";
            		sess.createSQLQuery(u1).executeUpdate();
            	}*/
            	sess.flush();
            	tr.commit();
            } catch (Exception e) {
            	if(tr!=null) {
            		tr.rollback();
            	}
                this.setMainMessage("保存失败！");
                e.printStackTrace();
                
            }
            //this.setMainMessage("保存成功！");
            /*if (!"".equals(sm.javascript)) {
                this.getExecuteSG().addExecuteCode(sm.javascript);
            }*/
            this.getExecuteSG().addExecuteCode("$('#tplb').val('');");
            this.setNextEventName("gridcq.dogridquery");
            return EventRtnType.NORMAL_SUCCESS;
        } else {
            this.setMainMessage("无法查询到该人员！");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
    
    @PageEvent("gridcq.dogridquery")
    @AutoNoMask
    public int doMemberQuery(int start, int limit) throws RadowException {
        try {
        	String fy_id = this.getPageElement("fyId").getValue();//批次
            String dc005 = "1";//类别标识

            String cur_hj = "0";//环节
            String cur_hj_4 = "4-1";//讨论决定分环节
            cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
            //RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
            
            String sql = "select * from (select distinct HAVE_FINE(fy01.a0000,fy01.fy0100) havefine,fy01.fy0119, fy01.fy0118,"
                    + " fy01.a0000,fy01.fy0100 fy0100,a0101,fy0108," + "fy0113"
                    + " from a01,fy01 where fy0122='1' and "
                    + " a01.a0000=fy01.a0000 and nvl(fy0120,'1')<>'2'"
                    + " and fy_id='" + fy_id + "'  " /*+ sm.hj4sql */
                    + " union select distinct HAVE_FINE(fy01.a0000,fy01.fy0100) havefine,fy01.fy0119, fy01.fy0118,"
                    + " fy01.a0000,fy01.fy0100 fy0100,a0101,fy0108," + "fy0113"
                    + " from v_js_a01 a01,fy01 where fy0122 in ('2','3','4') and a01.v_xt=fy01.fy0122 and"
                    + " a01.a0000=fy01.a0000 and nvl(fy0120,'1')<>'2'"
                    + " and fy_id='" + fy_id + "'  " /*+ sm.hj4sql */
                    + " ) order by " + "fy0113";
            this.pageQuery(sql, "SQL", start, limit);
            return EventRtnType.SPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.SPE_SUCCESS;
        }
    }
    @PageEvent("delete01row")
    @Transaction
    @Synchronous(true)
    @NoRequiredValidate
    public int delete01row(String fy0100s) {
		HBSession sess = HBUtil.getHBSession();
    	try {
    		sess.createSQLQuery("delete from fy01 where fy0100 in ('"+fy0100s.replace(",", "','")+"')").executeUpdate();
    		sess.createSQLQuery("delete from fy25 where fy0100 in ('"+fy0100s.replace(",", "','")+"')").executeUpdate();
			this.setMainMessage("删除成功!");
			this.getExecuteSG().addExecuteCode("radow.doEvent('gridcq.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("删除失败!");
		}	
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("gridComp.dogridquery")
    @AutoNoMask
    public int gridCompdoMemberQuery(int start, int limit) throws RadowException {
        try {
        	String fy_id = this.getPageElement("fyId").getValue();//批次
            
            String sql = "select b.fy2500,c.a0000,b.fy0100,c.fy0102,c.fy0108,a.fy2201b,a.fy2203 from fy22 a,fy25 b,fy01 c"
            		+ " where a.fy2200=b.fy2200 and b.fy0100=c.fy0100 and a.fy_id='" + fy_id
            		+ "' order by a.sortid,c.fy0113";
            this.pageQuery(sql, "SQL", start, limit);
            return EventRtnType.SPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.SPE_SUCCESS;
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
        	String fy_id = this.getPageElement("fyId").getValue();//批次
            conn = sess.connection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.execute("delete from fy25 where fy2200='"+nrId+"'");
            String sql = "insert into fy25 values (sys_guid(),'"+nrId+"',?,'"+fy_id+"')";
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
    		String fy_id = this.getPageElement("fyId").getValue();//批次
    		String path1 = AppConfig.HZB_PATH +"/temp/fxyp/"+fy_id+"/";
			File file = new File(path1);
			if(!file.exists()) {
				file.mkdirs();
			}
    		Workbook workbook = new HSSFWorkbook();  
    		String sql = "select c.fy0102,c.fy0103,c.fy0108,a.fy2201b,a.fy2203,a.fy2200 from fy22 a,fy25 b,fy01 c"
            		+ " where a.fy2200=b.fy2200 and b.fy0100=c.fy0100 and a.fy_id='" + fy_id
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
