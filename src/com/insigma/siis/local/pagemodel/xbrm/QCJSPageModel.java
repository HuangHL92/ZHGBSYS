package com.insigma.siis.local.pagemodel.xbrm;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;



import com.JUpload.JUpload;
import com.fr.third.org.hsqldb.lib.StringUtil;
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
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.Js08;
import com.insigma.siis.local.business.entity.Js19;
import com.insigma.siis.local.business.entity.Js20;
import com.insigma.siis.local.business.entity.Js33;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.HttpClientFactory;
import com.insigma.siis.local.epsoft.util.HttpUtils;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.JsonUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.publicServantManage.AddPersonPageModel;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js13Pojo;
import com.insigma.siis.local.pagemodel.xbrm.pojo.Js99Pojo;
import com.insigma.siis.local.pagemodel.xbrm.pojo.QgysPojo;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;
import com.utils.CommonQueryBS;

public class QCJSPageModel extends PageModel implements JUpload {

    JSGLBS bs = new JSGLBS();

    @Override
    public int doInit() throws RadowException {
        this.setNextEventName("initX");
        //设置下拉框
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 通过单位查询js12，存在展示在页面，不存在页面展示0
     *
     * @throws AppException
     * @throws RadowException 
     */
    @PageEvent("selectJs12ByDW")
    public int selectJs12ByDW() throws AppException, RadowException {
        String js1201 = this.request.getParameter("js1201");
        String rbid = this.request.getParameter("rbid");
        
        String js1201_ = js1201;
        HBSession sess = HBUtil.getHBSession();
        /*CommonQueryBS commQ=new CommonQueryBS();
        commQ.getListBySQL("select b0183,b0185");*/
        /*
        //查询单位名字
        String sqlb01 = "select b0101 from b01 where b0111 = '" + js1201 + "'";
        Object uniqueResult = sess.createSQLQuery(sqlb01).uniqueResult();
        if (uniqueResult != null) {
            js1201 = uniqueResult.toString();
        }*/
        CommonQueryBS cq = new CommonQueryBS();
        String sql = "select js1202,js1203,js1204,js1205,js1206,js1207,js1208,js1209,js1210,js1211,js1212,js1213,js1214 from js12 where js1201='" + js1201 + "' and rb_id='"+rbid+"'";
        List<HashMap<String, Object>> listBySQL = cq.getListBySQL(sql);
        if (listBySQL.size() > 0) {
            HashMap<String, Object> hashMap = listBySQL.get(0);
            JSONObject fromObject = JSONObject.fromObject(hashMap);
            this.setSelfDefResData(fromObject.toString());
            return EventRtnType.XML_SUCCESS;
        } else {
        	
        	String sb_sql = "select js0123 from js01 where rb_id='"+rbid+"' and js0122='1'";
        	/*List<Object> list = sess.createSQLQuery(sql_).list();
        	StringBuffer sb = new StringBuffer();
        	for (int j = 0; j < list.size(); j++) {
        		Object obj = list.get(0);
        		if(obj!=null && !obj.toString().equals("")) {
        			sb.append(obj+",");
        		}
			}
        	String sb_ = sb.length()>0 ? sb.substring(0,sb.length()-1) : "";
        	sb_ = sb_.replace(",", "','");
        	*/
        	String b0111_sql = "'"+js1201_+"'";
        	if(b0111_sql!=null) {
        		HashMap<String, Object> hashMap = new HashMap<String, Object>();
        		//hashMap.put("js1202", sqlb01);
        		int[] arr = {0,0,0,0, 0,0,0,0, 0,0};
        		//核定数目
            	String hd = "select b0111,sum(case when a0219='1' then nvl(gwnum,0) end) hdld,sum(case when a0219 is null or a0219='0' then nvl(gwnum,0) end) hdfld from Jggwconf where b0111 in ("+b0111_sql+") group by b0111 ";
            	List<Object[]> list_hd = sess.createSQLQuery(hd).list();
            	//现配数目
            	//String xp = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0201b in ("+b0111_sql+")  group by a0201b ";
            	String xp = "select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1  end) hdfld from (select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1'  and a0255 = '1' and a0201b in ("+b0111_sql+"))  group by a0201b";
            	List<Object[]> list_xp = sess.createSQLQuery(xp).list();
            	//拟免数目
            	//String nm = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0201b in ("+b0111_sql+") and a0200 in ('"+sb_+"') group by a0201b ";
            	String nm = "select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1 end) hdfld from ( select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1' and a0255 = '1' and a0201b in ("+b0111_sql+")  and a0200 in ("+sb_sql+"))  group by a0201b";
            	List<Object[]> list_nm = sess.createSQLQuery(nm).list();
            	//拟配数目
            	
            	////String np = "select b0111,sum(case when a0219='1' then 1 end) hdld,sum(case when a0219 is null or a0219='0' then 1 end) hdfld from Jggwconf a,js22 b where a.gwcode=b.js2204 and iscount='1' and a.b0111 in ("+b0111_sql+") and b.js0100 in (select j.js0100 from js01 j where j.rb_id='"+rbid+"') group by b0111 ";
            	String np = "select js2202, sum(case when a0219 = '1' then  1 end) hdld,  sum(case when a0219 is null or a0219 = '0' then 1  end) hdfld from (select a01.a0101, js22.js2202, js22.js2204 from a01, js01 j, js22 where a01.a0000 = j.a0000 and j.js0100 = js22.js0100 and js22.js2202 in ("+b0111_sql+") and j.rb_id = '"+rbid+"') a left outer join JGGWCONF t on a.js2204 = t.gwcode and a.js2202 = t.b0111 and t.iscount = '1' group by js2202";
            	List<Object[]> list_np = sess.createSQLQuery(np).list();
            	
            	if (list_hd.size()>0) {
            		Object[] o = list_hd.get(0);
                	int ld = o[1]!=null? ((BigDecimal)o[1]).intValue() :0;
    				int fld = o[2]!=null? ((BigDecimal)o[2]).intValue() :0;
    				arr[0] = ld;
    				arr[1] = fld;
            	}
				
				if (list_xp.size()>0) {
					Object[] obj = list_xp.get(0);
					int ld1 = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld1 = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					arr[2] = ld1;
					arr[3] = fld1;
				}
				if (list_nm.size()>0) {
            		Object[] obj = list_nm.get(0);
					int ld1 = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld1 = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					arr[4] = ld1;
					arr[5] = fld1;
				}
				if (list_np.size()>0) {
					Object[] obj = list_np.get(0);
					int ld1 = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld1 = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					arr[6] = ld1;
					arr[7] = fld1;
				}
				arr[8] = arr[0]>=arr[2]-arr[4]+arr[6]? 0 :1;
				arr[9] = arr[1]>=arr[3]-arr[5]+arr[7]? 0 :1;
            	hashMap.put("js1202", "1");
            	hashMap.put("js1203", arr[0]);
				hashMap.put("js1204", arr[1]);
				hashMap.put("js1205", arr[2]);
				hashMap.put("js1206", arr[3]);
				hashMap.put("js1207", arr[4]);
				hashMap.put("js1208", arr[5]);
				hashMap.put("js1209", arr[6]);
				hashMap.put("js1210", arr[7]);
				hashMap.put("js1211", arr[8]);
				hashMap.put("js1212", arr[9]);
            	
            	hashMap.put("js1213", "");
            	JSONObject fromObject = JSONObject.fromObject(hashMap);
                this.setSelfDefResData(fromObject.toString());
                return EventRtnType.XML_SUCCESS;
        	}
            return EventRtnType.NORMAL_SUCCESS;
        }
    }

    
    
    /**
     * 保存考核与听取意见
     * @throws Exception 
     */
    public void saveJS21(String js0100,HBSession sess) throws Exception{
    	String tqyjcheck=this.getPageElement("checkboxtqyj").getValue();
        String[] tqyj={};
        String js2101="2";
        String js2102="2";
        String js2103="2";
        String js2104="2";
        String js2105="2";
        String js2106="2";
        if(!tqyjcheck.isEmpty()){
        	if(tqyjcheck.contains(",")){
        		 String[] tqyjs = tqyjcheck.split(",");
        		 for(int i=0;i<tqyjs.length;i++){
        			 String name=tqyjs[i];
        			 if("name1".equals(name)){
        				 js2101="1";
        			 }else if("name2".equals(name)){
        				 js2102="1";
        			 }else if("name3".equals(name)){
        				 js2103="1";
        			 }else if("name4".equals(name)){
        				 js2104="1";
        			 }else if("name5".equals(name)){
        				 js2105="1";
        			 }else if("name6".equals(name)){
        				 js2106="1";
        			 }
        		 }
        	}else{
        		 if("name1".equals(tqyjcheck)){
   				 	 js2101="1";
	   			 }else if("name2".equals(tqyjcheck)){
	   				 js2102="1";
	   			 }else if("name3".equals(tqyjcheck)){
	   				 js2103="1";
	   			 }else if("name4".equals(tqyjcheck)){
	   				 js2104="1";
	   			 }else if("name5".equals(tqyjcheck)){
	   				 js2105="1";
	   			 }else if("name6".equals(tqyjcheck)){
	   				 js2106="1";
	   			 }
        	}
        }
    	String js2107=this.getPageElement("grsxch").getValue();	
    	if(!StringUtils.isEmpty(js2107)){
    		if("1".equals(js2107)){
    			js2107="1";
    		}else if("0".equals(js2107)){
    			js2107="2";
    		}
    	}
    	String js2108=this.getPageElement("gzsy").getValue();
    	String js2109=this.getPageElement("qtsy").getValue();
    	
    	String rbId=this.getPageElement("rbId").getValue();
    	
    	List list = sess.createSQLQuery("select js2100 from JS21 where JS0100='"+js0100+"' and RB_ID='"+rbId+"'").list();
    	if(list.size()>0){
    		String js2100=list.get(0).toString();
    		HBUtil.executeUpdate("update JS21 set js2101=?,js2102=?,js2103=?,js2104=?,js2105=?,js2106=?,js2107=?,js2108=?,js2109=? where JS2100=?",
    				new Object[]{js2101,js2102,js2103,js2104,js2105,js2106,js2107,js2108,js2109,js2100});
    	}else{
    		String js2100=UUID.randomUUID().toString().replaceAll("-", "");
    		HBUtil.executeUpdate("insert into JS21(JS0100,RB_ID,JS2100,JS2101,JS2102,JS2103,JS2104,JS2105,JS2106,JS2107,JS2108,JS2109) "
    				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)",new Object[]{js0100,rbId,js2100,js2101,js2102,js2103,js2104,js2105,js2106,js2107,js2108,js2109});
    	}
    }
    
    
    @PageEvent("importSheet")
    public int importSheet(String data) throws RadowException, AppException {
        QCJSFileExportBS fileBS = new QCJSFileExportBS();
        	
        String rbId = this.getPageElement("rbId").getValue();//批次
        String dc005 = this.getPageElement("dc005").getValue();//类别标识
        String cur_hj = this.getPageElement("cur_hj").getValue();//环节
        String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
        
        String js0100s=this.getPageElement("js0100s").getValue();
        String js0100WhereSql="";
        if(!StringUtil.isEmpty(js0100s)){
        	js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
        }
        //审签单
        String personsStr = fileBS.getSheetPersons(rbId, dc005, cur_hj, cur_hj_4, 2,js0100WhereSql);
        data = data + "," + personsStr;
        String[] Flds = {"tqyj1", "tqyj2", "tqyj3", "tqyj4", "tqyj5", "tqyj6", "sxch1", "sxch2", "gzsy", "rydx"};
        String[] Vals = data.split(",");
        String path = new ExportAsposeBS().signatureSheet(Flds, Vals, "sqd.doc", "审签单.doc", null);
        //任职前听取公安部门意见word
        String[] gonganFlds = {"names","time"};
        String personsStrOne = fileBS.getSheetPersons(rbId, dc005, cur_hj, cur_hj_4, 2,js0100WhereSql);
        		
        String time=DateUtil.dateToString(new Date(), "YYYY年MM月DD日");
        String[] gonganVals = {personsStrOne.replace("（名单附后）", ""),time};
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "gonganyj.doc", "任职前听取公安部门意见表.doc", path);
        //任职前听取检察机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "jianchajiguanyj.doc", "任职前听取检察机关意见表.doc", path);
        //听取纪检监察机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "jijianyj.doc", "听取纪检监察机关意见表.doc", path);
        //任职前听取审判机关意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "shenpanyj.doc", "任职前听取审判机关意见表.doc", path);
        //任职前听取卫生计生部门意见表
        new ExportAsposeBS().signatureSheet(gonganFlds, gonganVals, "weishengyj.doc", "任职前听取卫生计生部门意见表.doc", path);
        //听取意见人员名单,设置最多8个人
        /*StringBuffer personInfoS = new StringBuffer();
        for (int i = 1; i < 9; i++) {
            if (i == 1) {
                personInfoS.append("num1");
            } else {
                personInfoS.append("," + "num" + i);
            }
            personInfoS.append("," + "name" + i);
            personInfoS.append("," + "sex" + i);
            personInfoS.append("," + "idcard" + i);
            personInfoS.append("," + "zw" + i);
        }
        String[] personInfoFlds = personInfoS.toString().split(",");
        String personsinfo = fileBS.getPersonsInfo(rbId, dc005, cur_hj, cur_hj_4,js0100WhereSql);
        String[] personInfoVals = personsinfo.split(",");
        new ExportAsposeBS().signatureSheet(personInfoFlds, personInfoVals, "tingqumd.doc", "听取意见人员名单.doc", path);
        */
        //听取意见人员名单
        fileBS.createTqyjrymd1(rbId, dc005, cur_hj, cur_hj_4,js0100WhereSql, path);
        
        //听取意见人员及配偶名单
        /*StringBuffer matepersonInfoS = new StringBuffer();
        for (int i = 1; i < 9; i++) {
            if (i == 1) {
                matepersonInfoS.append("n1");
            } else {
                matepersonInfoS.append("," + "n" + i);
            }
            matepersonInfoS.append("," + "na" + i);
            matepersonInfoS.append("," + "s" + i);
            matepersonInfoS.append("," + "i" + i);
            matepersonInfoS.append("," + "z" + i);
            matepersonInfoS.append("," + "p" + i);
            matepersonInfoS.append("," + "ps" + i);
            matepersonInfoS.append("," + "pi" + i);
            matepersonInfoS.append("," + "pz" + i);
        }

        String[] matepersonInfoFlds = matepersonInfoS.toString().split(",");
        String matepersonsinfo = fileBS.matePersonInfo(rbId, dc005, cur_hj, cur_hj_4,js0100WhereSql);
        String[] matepersonInfoVals = matepersonsinfo.split(",");
        new ExportAsposeBS().signatureSheet(matepersonInfoFlds, matepersonInfoVals, "peiomd.doc", "听取意见人员及配偶名单.doc", path);
         */
        fileBS.createTqyjrymd2(rbId, dc005, cur_hj, cur_hj_4,js0100WhereSql, path);
        if (path.length() == 0) {
            return EventRtnType.FAILD;
        }
        String infile = path.substring(0, path.length() - 1) + ".zip";
        SevenZipUtil.zip7z(path, infile, null);
        this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
        this.getExecuteSG().addExecuteCode("window.reloadTree()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("authorLetter")
    public int authorLetter(String data) throws RadowException, AppException {
        QCJSFileExportBS fileBS = new QCJSFileExportBS();
        
        String rbId = this.getPageElement("rbId").getValue();//批次
        String dc005 = this.getPageElement("dc005").getValue();//类别标识
        String cur_hj = this.getPageElement("cur_hj").getValue();//环节
        String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
        
        String js0100s=this.getPageElement("js0100s").getValue();
        String js0100WhereSql="";
        if(!StringUtil.isEmpty(js0100s)){
        	js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
        }
        
        String personsStrOne = (fileBS.getSheetPersons(rbId, dc005, cur_hj, cur_hj_4, 1,js0100WhereSql)).replace("（名单附后）", "");
        //时间
        String nowDate = DateUtil.timeToString(DateUtil.getTimestamp(), "MM月dd日");
        //核准配备
        int personum = fileBS.getPersonsNum(rbId, dc005, cur_hj, cur_hj_4,js0100WhereSql);
        
        String[] Flds = {"personInfo", "time", "hzpb", "qtsy"};
        String[] Vals = {personsStrOne, nowDate, personum + "", data};
        String path = new ExportAsposeBS().signatureSheet(Flds, Vals, "wth.doc", "委托函.doc", null);
        if (path.length() == 0) {
            return EventRtnType.FAILD;
        }
        String infile = path.substring(0, path.length() - 1) + ".zip";
        SevenZipUtil.zip7z(path, infile, null);
        this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
        this.getExecuteSG().addExecuteCode("window.reloadTree()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    //任免表导出
    @PageEvent("exportLrmxBtn")
    public int expWin() throws RadowException, Exception {
        CustomQueryPageModel cus = new CustomQueryPageModel();
        HBSession sess = HBUtil.getHBSession();
        String rbId = this.request.getParameter("rbId");//批次
        String cur_hj = this.request.getParameter("cur_hj");//环节
        String cur_hj_4 = this.request.getParameter("cur_hj_4");//讨论决定分环节
        String dc005 = this.request.getParameter("dc005");
        String js0100s = this.request.getParameter("js0100s");
        String js0100WhereSql = "";
        if (!"".equals(js0100s)) {
            js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
            //ExcelStyleUtil.js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql + "')";
            js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql + "')";
            this.request.setAttribute("js0100WhereSql", js0100WhereSql);
        } else {
            //ExcelStyleUtil.js0100WhereSql = "";
        	this.request.setAttribute("js0100WhereSql", "");
            
        }
        String sql = ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId, dc005,js0100WhereSql);
        System.out.println(sql);
        sql = "select m.a0000,m.a0101,m.js0122 from (" + sql + ") m,js02 where  m.js0100 = js02.js0100";

        //判断列表是否有数据
        List<Object[]> list = sess.createSQLQuery(sql).list();

        if (list.size() == 0) {
            this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要导出的数据！',null,180);");
            return EventRtnType.NORMAL_SUCCESS;
        }
        Runtime rt = Runtime.getRuntime();
        String zippath = ExpRar.expFile();
        String infile = "";
        for (Object[] object : list) {
            String a0000 = (String) object[0];
            String a0101 = (String) object[1];
            String js0211 = (String) object[2];
            PersonXml per = cus.createLrmxStr(a0000, "", js0211);
            FileUtil.createFile(zippath + a0101 + ".lrmx", JXUtil.Object2Xml(per, true), false, "UTF-8");

            A01 a01log = new A01();
            new LogUtil().createLog("36", "A01", a0000, per.getXingMing(), "LRMX导出", new Map2Temp().getLogInfo(new A01(), a01log));
        }

        infile = zippath.substring(0, zippath.length() - 1) + ".zip";
        SevenZipUtil.zip7z(zippath, infile, null);
        this.getExecuteSG().addExecuteCode("document.getElementById('downfile').value='" + infile.replace("\\", "/") + "';");
        this.getExecuteSG().addExecuteCode("reloadTree()");
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("fwexpdo")
    public int fwexpdo() throws RadowException{
    	HBSession sess = HBUtil.getHBSession();
    	String rbId=this.getPageElement("rbId").getValue();
    	String js0100s=this.getPageElement("js0100s").getValue();
    	String rb_status = this.getPageElement("rb_status").getValue();
    	String js0100WhereSql = "";
    	String sql="";
        if (!"".equals(js0100s)) {
            js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
            sql="select a0000,js0100,js2301,js2302,js2303,js2310 from js23 where js0100 in('"+js0100WhereSql+"')";
        } else {
        	js0100WhereSql="";
        	rb_status = "true";
        	sql="select a0000,js0100,js2301,js2302,js2303,js2310 from js23 where rb_id='"+rbId+"'";
        }
    	List<String> list2 = new ArrayList<String>();
    	//保存当前选中人员的发文信息 
    	String js0100=this.getPageElement("js0100").getValue();
    	saveJs23(js0100, sess);
    	
    	String a0000=this.getPageElement("a0000").getValue();
    	ExportAsposeBS exportAsposeBS = new ExportAsposeBS();
    	
    	String path=ExpRar.expFile();
    	
    	CommonQueryBS cq=new CommonQueryBS();
    	
    	String updatejs0100s="";
    	
    	try {
    		List<HashMap<String, Object>> list = cq.getListBySQL(sql);
			//List<HashMap<String, Object>> list = cq.getListBySQL("select js2301,js2302,js2303 from js23 where a0000 ='"+a0000+"'");
			if(list.size()==0) {
				this.setMainMessage("请填写发文信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			List<String> deflist = new ArrayList<String>();
			List<String> samelist = new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				String newa0000 = inullback(map.get("a0000"))+"@@"+inullback(map.get("js2310"));
				/*if(i==0){
					deflist.add(newa0000);
					continue;
				}*/
				if(deflist.contains(newa0000)){
					samelist.add(newa0000);//存在相同时间发文的人
				}else{
					deflist.add(newa0000);
				}
			}
			HashMap<String,HashMap<String,Object>> sameMap = new HashMap<String, HashMap<String,Object>>();
    		for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				HashMap<String,Object> newsame = new HashMap<String, Object>();
				String a0000exp=inullback(map.get("a0000"));
				String updatejs0100=inullback(map.get("js0100"));
				String js2301=inullback(map.get("js2301"));
				String js2302=inullback(map.get("js2302"));
				String js2303=inullback(map.get("js2303"));
				updatejs0100s=updatejs0100s+updatejs0100+",";
				String newupdatejs0100 = a0000exp+"@@"+inullback(map.get("js2310"));
				if(!samelist.contains(newupdatejs0100)){
					exportAsposeBS.getFwWordByA0000(a0000exp,js2301,js2302,js2303,path,updatejs0100);					
				}else{
					if(sameMap.containsKey(updatejs0100)){//判断原来的之前是否有同一个人同一时间发文
						HashMap<String,Object> map1 = sameMap.get(updatejs0100);
						StringBuffer sb2301 = new StringBuffer();//拼接新的发文任免信息
						sb2301.append(inullback(map1.get("js2301")) +js2301+"&");
						sb2301.append(!"".equals(js2302)?"任"+js2302:"");
						sb2301.append(!"".equals(js2302)&&!"".equals(js2303)?",":"");
						sb2301.append(!"".equals(js2303)?"免"+js2303:"");
						sb2301.append("@");
						js2301 = sb2301.toString();
						//js2301 = inullback(map1.get("js2301")) +js2301+"&"+"任"+js2302+"免"+js2303 + "@";
						if("".equals(js2302)){
							js2302 = inullback(map1.get("js2302"));
						}else{
							js2302 = inullback(map1.get("js2302"))+js2302 + "@";
						}
						if("".equals(js2303)){
							js2303 = inullback(map1.get("js2303"));
						}else{
							js2303 = inullback(map1.get("js2303"))+js2303 + "@";
						}
					}else{
						StringBuffer sb = new StringBuffer();//拼接任免职务加发文号
						sb.append(js2301+"&");
						sb.append(!"".equals(js2302)?"任"+js2302:"");
						sb.append(!"".equals(js2302)&&!"".equals(js2303)?",":"");
						sb.append(!"".equals(js2303)?"免"+js2303:"");
						sb.append("@");
						js2301 = sb.toString();
						//js2301 = js2301+"&"+"任"+js2302+"免"+js2303 + "@";
						js2302 = js2302==""?"":(js2302 + "@");
						js2303 = js2303==""?"":(js2303 + "@");						
					}
					newsame.put("js2301", js2301);
					newsame.put("js2302", js2302);
					newsame.put("js2303", js2303);
					newsame.put("a0000", a0000exp);
					sameMap.put(updatejs0100,newsame);
				}
			}
    		if(!sameMap.isEmpty()&&sameMap!=null){
    			Set<String> key = sameMap.keySet();
    			for(String updatejs0100:key){
    				HashMap<String,Object> map = sameMap.get(updatejs0100);
    				String a0000exp=inullback(map.get("a0000"));
    				String js2301=inullback(map.get("js2301"));
    				if(!"".equals(js2301)){
    					js2301 = js2301.substring(0, js2301.length()-1);
    				}
    				String js2302=inullback(map.get("js2302"));
    				String js2303=inullback(map.get("js2303"));
    				exportAsposeBS.getFwWordByA0000(a0000exp,js2301,js2302,js2303,path,updatejs0100);
    			}
    		}
    	} catch (AppException e) {
			e.printStackTrace();
			this.setMainMessage("导出失败");
		}
    	String infile = path.substring(0, path.length() - 1) + ".zip";
    	SevenZipUtil.zip7z(path, infile, null);
    	//更新js01状态
    	if (!"".equals(updatejs0100s)) {
    		String updatejs0100WhereSql = updatejs0100s.substring(0, updatejs0100s.length() - 1).replace(",", "','");
    		sess.createSQLQuery("update js01 set js0121='1' where rb_id='"+rbId+"' and js0100 in('"+updatejs0100WhereSql+"')").executeUpdate();
        	sess.flush();
        }
    	if("true".equals(rb_status)){//全部发文结束，批次结束，更新批次状态
    		//sess.createSQLQuery("update record_batch set rb_status = '1' where rb_id='"+rbId+"'").executeUpdate();
    		//sess.flush();
    	}
        this.getExecuteSG().addExecuteCode("document.getElementById('downfile').value='" + infile.replace("\\", "/") + "';");
        this.getExecuteSG().addExecuteCode("reloadTree()");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    private void setSelectCombo() throws RadowException {
        String sql = "select b0111, jsdw002  from js_dw order by jsdw003";
        CommQuery cqbs = new CommQuery();
        List<HashMap<String, Object>> listCode = new ArrayList<HashMap<String, Object>>();
        try {
            listCode = cqbs.getListBySQL(sql);
        } catch (AppException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> mapCode_1 = new LinkedHashMap<String, Object>();
        for (int i = 0; i < listCode.size(); i++) {
            mapCode_1.put(listCode.get(i).get("b0111").toString(), listCode.get(i).get("jsdw002").toString());
        }
        ((Combo) this.getPageElement("js0115")).setValueListForSelect(mapCode_1);
       // ((Combo) this.getPageElement("js0116")).setValueListForSelect(mapCode_1);
    }

    private void setGridCombo() throws RadowException, AppException {
        //设置下拉框
        String rbId = this.getPageElement("rbId").getValue();
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

    @PageEvent("initX")
    public int initX() throws RadowException, AppException {
        String rbId = this.getPageElement("rbId").getValue();
        RecordBatch rb = (RecordBatch) HBUtil.getHBSession().get(RecordBatch.class, rbId);
        if (rb != null) {
            if ("1".equals(rb.getRbUpdated())) {
                this.getExecuteSG().addExecuteCode("Ext.getCmp('updateNRM').addClass('bh');");
            }
        }
        
        //判断是都显示常委会 
        HBSession sess = HBUtil.getHBSession();
        List list = sess.createSQLQuery("select RB_MEETTYPE from record_batch where RB_ID='"+rbId+"'").list();
        if(list.size()>0){
        	String meettype=list.get(0).toString();
        	if("101".equals(meettype)){
        		//类型为 部务会时 讨论决定下不显示常委会信息
        		this.getExecuteSG().addExecuteCode("document.getElementById('meettype').value='1'");
        	}
        }else{
        	this.getExecuteSG().addExecuteCode("document.getElementById('meettype').value='2'");
        }
        
        //判断动议下是否显示审批等信息
        List list2 = sess.createSQLQuery("select RB_APPROVE from record_batch where RB_ID='"+rbId+"'").list();
        if(list2.size()>0){
        	String approve=list2.get(0).toString();//等于1，表示整个批次都需要事前报告了，每人就不需要了
        	if("1".equals(approve)){
        		this.getExecuteSG().addExecuteCode("this.hidesp();");
        	}
        }
        
        setGridCombo();
        //setSelectCombo();
        
        //setShowImg();
        this.setNextEventName("gridcq.dogridquery");
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    @PageEvent("setShowImg")
    public void setShowImg() throws RadowException {
		String rbId = this.getPageElement("rbId").getValue();
		HBSession sess = HBUtil.getHBSession();
		List personcountList = sess.createSQLQuery("select js0100 from JS01 where rb_id='" + rbId + "'").list();
		List<Integer> list=new ArrayList<Integer>();
		List list3 = sess.createSQLQuery("select js1200 from js12 where rb_id='" + rbId + "' and js1214 is not null").list();
        if(list3.size()>0) {
        	list.add(3);
        }
        List list1=sess.createSQLQuery("select js0100 from JS01 where rb_id='"+rbId+"'  and js0111 is  null  and  js0117 is  null").list();
        if(list1.size()==0) {
        	list.add(1);
        }
        List list2 = sess.createSQLQuery("select js0100 from js02 where rb_id='" + rbId + "' and js0202 is not null").list();
        if(list2.size()==personcountList.size()) {
        	list.add(2);
        }
        List list5 = sess.createSQLQuery("select crp000 from CHECKREGPERSON where checkregid ='"+rbId+"' and crp010 is not null").list();
        if(list5.size()>0) {
        	list.add(5);
        }
        List list6 = sess.createSQLQuery("select js0100 from js20 where rb_id='" + rbId + "' and js2002 is not null").list();
        if(list6.size()==personcountList.size()) {
        	list.add(6);
        }
        List list7 = sess.createSQLQuery("select js0100 from js14 where rb_id='" + rbId + "'and js1402 is not null").list();
        if(list7.size()==personcountList.size()) {
        	list.add(7);
        }
        List list8 = sess.createSQLQuery("select js0100 from js15 where rb_id='" + rbId + "' and js1513 is not null").list();
        if(list8.size()==personcountList.size()) {
        	list.add(8);
        }
        List list9 = sess.createSQLQuery("select js0100 from js08 where rb_id='" + rbId + "' and js0806 is not null").list();
        if(list9.size()==personcountList.size()) {
        	list.add(9);
        }
        List list10 = sess.createSQLQuery("select a0000 from js23 where rb_id='" + rbId + "' and js2310 is not null group by a0000").list();
        if(list10.size()==personcountList.size()) {
        	list.add(10);
        }
        for(Integer i:list) {
        	this.getExecuteSG().addExecuteCode("document.getElementById('tip"+i+"').style.display=''; ");
        }
        for(int i=1;i<=10;i++) {
        	if(!list.contains(i)) {
        		this.getExecuteSG().addExecuteCode("document.getElementById('tip"+i+"').style.display='none'; ");
        	}
        }
        List<Integer> list4=new ArrayList<Integer>();
        //青干预审（4）先忽略
        list4.add(1);list4.add(2);list4.add(3);list4.add(5);list4.add(6);
        list4.add(9);list4.add(10);list4.add(7);list4.add(8);
        
        
        if(list.containsAll(list4)) {
        	sess.createSQLQuery("update record_batch set rb_status = '1' where rb_id='"+rbId+"'").executeUpdate();
    		sess.flush();
        }else {
        	sess.createSQLQuery("update record_batch set rb_status = '0' where rb_id='"+rbId+"'").executeUpdate();
    		sess.flush();
        }
	}


	/*private void setPageValue(String id, String value){
		value = value.replaceAll("'", "\\\\'");
		this.getExecuteSG().addExecuteCode("document.getElementById('"+id+"').value='"+value+"'");
	}
	private String SV(String v){
		return v==null?"":v;
	}*/

    @PageEvent("gridcq.dogridquery")
    @AutoNoMask
    public int doMemberQuery(int start, int limit) throws RadowException {
        try {
            String rbId = this.getPageElement("rbId").getValue();//批次
            String dc005 = this.getPageElement("dc005").getValue();//类别标识

            String cur_hj = this.getPageElement("cur_hj").getValue();//环节
            String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
            cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
            RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
            
            /*String sql = "select distinct HAVE_FINE(js01.a0000,js01.js0100) havefine,js01.js0119, js01.js0118, js_hj.js0100,nvl((select lpad(dc004,10,'0') dc004 from deploy_classify t where t.dc001=" + sm.ref_dc001 + "),999)||'@@'||nvl(" + sm.ref_dc001 + ",'999') " + sm.dc001_alias + ","
                    + " js01.a0000,a0101," + sm.orderbyfield
                    + " from a01,js01,js_hj where "
                    + " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 and nvl(JS0120,'1')<>'2'"
                    + " and rb_id='" + rbId + "'  " + sm.hj4sql + "order by" + sm.orderbyfield;*/
            String sql = "select * from (select distinct HAVE_FINE(js01.a0000,js01.js0100) havefine,js01.js0119, js01.js0118, js_hj.js0100,nvl((select dc003 from deploy_classify t where t.rb_id ='"+rbId+"'),'其他') dc001,"
                    + " js01.a0000,js01.js0100 js01001,a0101," + sm.orderbyfield
                    + " from a01,js01,js_hj where js0122='1' and "
                    + " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 and nvl(JS0120,'1')<>'2'"
                    + " and rb_id='" + rbId + "'  " + sm.hj4sql 
                    + " union select distinct HAVE_FINE(js01.a0000,js01.js0100) havefine,js01.js0119, js01.js0118, js_hj.js0100,nvl((select dc003 from deploy_classify t where t.rb_id ='"+rbId+"'),'其他') dc001,"
                    + " js01.a0000,js01.js0100 js01001,a0101," + sm.orderbyfield
                    + " from v_js_a01 a01,js01,js_hj where js0122 in ('2','3','4') and a01.v_xt=js01.js0122 and"
                    + " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 and nvl(JS0120,'1')<>'2'"
                    + " and rb_id='" + rbId + "'  " + sm.hj4sql 
                    + " ) order by" + sm.orderbyfield;
            this.pageQuery(sql, "SQL", start, limit);
            return EventRtnType.SPE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.SPE_SUCCESS;
        }
    }

    //按姓名查询，传递人员IDs
    @PageEvent("queryByNameAndIDS")
    public int queryByNameAndIDS(String listStr) throws Exception {
        //System.out.println(listStr);
        String tplb = this.getPageElement("tplb").getValue();//调配类别
        //调配类别 不在数据库里的则新增。
        HBSession sess = HBUtil.getHBSession();
        HBTransaction tr = sess.beginTransaction();
        List dclist = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where dc001=?").setString(0, tplb).list();

        String rbId = this.getPageElement("rbId").getValue();
        String cur_hj = this.getPageElement("cur_hj").getValue();//环节
        String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
        String dc005 = this.request.getParameter("dc005");
        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);

        //调配类别 不在数据库里的则新增。
        if (dclist.size() == 0 && !"".equals(tplb)) {
            String id = UUID.randomUUID().toString();
            /*HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
                            + " values(?,?,?,deploy_classify_dc004.nextval,?)",
                    new Object[]{id, rbId, tplb, RMHJ.REN_MIAN_ZHI.equals(cur_hj) ? "2" : "1"});*/
            String slq = "insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
                    + " values('"+id+"','"+rbId+"','"+tplb+"',deploy_classify_dc004.nextval,'"+(RMHJ.REN_MIAN_ZHI.equals(cur_hj) ? "2" : "1")+"')";
            sess.createSQLQuery(slq).executeUpdate();
            tplb = id;
            setGridCombo();
        } else {
        	List dclist2 = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where rb_id=?").setString(0, rbId).list();
        	tplb = dclist2.get(0) + "";
        }
        RMHJ.InsertSqlMap sm = RMHJ.getInsertSqlMap(cur_hj, cur_hj_4, dc005, tplb);
//        StringBuffer sql = new StringBuffer("insert into js01(js0100, a0000, rb_id,js0102, js0103, js0104, js0105, js0106, js0114,js0108,js0110,js0109,dc001,js0113,js0122)  "
//                + "(select sys_guid(),a0000,'" + rbId + "' rb_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192a, a0288 || ' ' || a0192c rtzjsj,"
//                + "(select replace(TO_CHAR(wm_concat(a0243)), ',', ' ') from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" + tplb + "',deploy_classify_dc004.nextval,（VXT） from （A01） a01 ");
        StringBuffer sql = new StringBuffer("insert into js01(js0100, a0000, rb_id,js0102, js0103, js0104, js0105, js0106, js0114,js0108,js0110,js0109,dc001,js0113,js0122)  "
                + "(select sys_guid(),a0000,'" + rbId + "' rb_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192, a0288 rtzjsj,"
                + "(select max(decode(length(a0243), 6, a0243 || '01', 8, a0243, null)) from （A02） a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true' and  a02.v_xt=（VXT）) a0243,'" + tplb + "',deploy_classify_dc004.nextval,（VXT） from （A01） a01 ");
        StringBuffer hjSql = new StringBuffer("insert into js_hj(js0100,JS_TYPE," + sm.hj_sort + sm.thapfield + ") (select js0100,'" + cur_hj + "',js0113 " + sm.thapvalue + " from js01 where rb_id='" + rbId + "' "
                + " and not exists (select 1 from js_hj where js_hj.js0100=js01.js0100 and js_type='" + cur_hj + "')  ");
        hjSql.append(" and a0000 in ('-1'");
        sql.append(" where a0000 in ('-1'");
        if (listStr != null && listStr.length() > 2) {
            listStr = listStr.substring(1, listStr.length() - 1);
            List<String> list = Arrays.asList(listStr.split(","));
            StringBuffer xt1a0000s = new StringBuffer();
            StringBuffer xt2a0000s = new StringBuffer();
            StringBuffer xt3a0000s = new StringBuffer();
            StringBuffer xt4a0000s = new StringBuffer();
            StringBuffer jshisjs0100 = new StringBuffer();
            StringBuffer jshisjs01002 = new StringBuffer();
            
            for (String idparam : list) {
               /* sql.append(",'" + id.trim() + "'");
                hjSql.append(",'" + id.trim() + "'");*/
            	String idarr[] = idparam.split("@");
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
            
            try {
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
            	sess.flush();
            	tr.commit();
            } catch (Exception e) {
                this.setMainMessage("保存失败！");
                e.printStackTrace();
                if(tr!=null) {
            		tr.rollback();
            	}
            }
            //this.setMainMessage("保存成功！");
            if (!"".equals(sm.javascript)) {
                this.getExecuteSG().addExecuteCode(sm.javascript);
            }
            this.getExecuteSG().addExecuteCode("$('#tplb').val('');");
            this.setNextEventName("gridcq.dogridquery");
            return EventRtnType.NORMAL_SUCCESS;
        } else {
            this.setMainMessage("无法查询到该人员！");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }


    @PageEvent("personsort")
    @Transaction
    @Synchronous(true)
    public int savePersonsort() throws RadowException, AppException {

        String cur_hj = this.getPageElement("cur_hj").getValue();//环节
        String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//讨论决定分环节
        String dc005 = this.getPageElement("dc005").getValue();//类别标识

        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
        RMHJ.SortSqlMap sm = RMHJ.getSortSqlMap(cur_hj, cur_hj_4, dc005);

        List<HashMap<String, String>> list = this.getPageElement("gridcq").getStringValueList();
        HBSession sess = HBUtil.getHBSession();
        Connection con = null;
        try {
            con = sess.connection();
            con.setAutoCommit(false);
            String sql = "update " + sm.table + " set " + sm.sortfield + "=? where js0100=?" + sm.where;
            PreparedStatement ps = con.prepareStatement(sql);
            int i = 1;
            for (HashMap<String, String> m : list) {
                String js0100 = m.get("js0100");
                ps.setInt(1, i);
                ps.setString(2, js0100);
                ps.addBatch();
                i++;
            }
            ps.executeBatch();
            con.commit();
            ps.close();
            con.close();
        } catch (Exception e) {
            try {
                con.rollback();
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            this.setMainMessage("排序失败！");
            return EventRtnType.FAILD;
        }
        //this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
        this.toastmessage("排序已保存！");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 实时保存任免信息
     *
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("saveNRMValue")
    @Transaction
    @Synchronous(true)
    public int saveNRMValue() throws RadowException, AppException {
        String nrmid = this.getPageElement("nrmid").getValue();//字段
        String nrmvalue = this.getPageElement("nrmvalue").getValue();//值
        String js0100 = this.getPageElement("js0100").getValue();//值
        String nrmdesc = this.getPageElement("nrmdesc").getValue();//中文
        String js0102 = this.getPageElement("js0102").getValue();
        String rbId = this.getPageElement("rbId").getValue();//批次

        if (!"".equals(js0100)) {
            HBSession sess = HBUtil.getHBSession();

            Connection con = null;
            try {
                con = sess.connection();
                con.setAutoCommit(false);
                String sql = "update js01 set " + nrmid + "=? where js0100=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nrmvalue);
                ps.setString(2, js0100);
                ps.addBatch();
                ps.executeBatch();


                con.commit();
                if ("js0111".equals(nrmid) || "js0117".equals(nrmid)) {//更新拟任免信息集
                    Js01 js01 = (Js01) sess.get(Js01.class, js0100);
                    bs.setPm(this);
                    bs.saveA053(js01, sess);
                }


                ps.close();
                con.close();
            } catch (Exception e) {
                try {
                    con.rollback();
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
                this.toastmessage(js0102 + "：“" + nrmdesc + "”信息更新失败！", 2);
                return EventRtnType.FAILD;
            }
            //this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
        }
        this.toastmessage(js0102 + "：“" + nrmdesc + "”信息已更新！", 2);
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 推荐表
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("tjb.onclick")
    public int tjb() {
        try {
            String tuijianNum = this.getPageElement("tjrs").getValue();
            if (tuijianNum == null || tuijianNum.length() == 0) {
                this.setMainMessage("推荐人数不能为空!");
                return EventRtnType.FAILD;
            }
            QCJSFileExportBS fileBS = new QCJSFileExportBS();

            fileBS.exportTJB(tuijianNum);
            List<String> fns = fileBS.getOutputFileNameList();
            String downloadPath = "";
            String downloadName = "";
            if (fns.size() == 1) {//一个文件直接下载
                downloadPath = fileBS.getOutputpath() + fns.get(0);
                downloadName = fns.get(0);
            } else if (fns.size() > 1) {//压缩
                downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
                downloadName = fileBS.getSheetName() + ".zip";
                Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
            } else {

            }
            String downloadUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
            this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
            this.getExecuteSG().addExecuteCode("downloadByUUID();");


            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return EventRtnType.FAILD;
        }
    }

    /**
     * 生成推荐结果表
     *
     * @return
     */
    @PageEvent("tjjg")
    @Transaction
    public int tjxx() {
        try {
            String rbId = this.getPageElement("rbId").getValue();//批次id
            String js0100 = this.getPageElement("js0100").getValue();//任免人员id
            String a0000 = this.getPageElement("a0000").getValue();//人员id

            String tjsj = this.getPageElement("tjsj").getValue();
            String tjcjrs = this.getPageElement("tjcjrs").getValue();
            String tjother = this.getPageElement("tjother").getValue();
            String js0100s=this.getPageElement("js0100s").getValue();
            String js0100WhereSql = "";
            if (!"".equals(js0100s)) {
                js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
            } else {
            	js0100WhereSql="";
            }
            
            HBSession sess = HBUtil.getHBSession();
            //查询js18,共同部分
            String sqljs18 = "select js1801,js1802,js1803,js1804,RB_ID from JS18";
            CommonQueryBS cq = new CommonQueryBS();
            HashMap<String, Object> js18map = cq.getMapBySQL(sqljs18);
            //查询JS19，得分部分
 /*           String sqljs19 ="";
            if(!StringUtil.isEmpty(js0100WhereSql)){
            	sqljs19 = "select JS0100,DC001,RB_ID,JS1902,JS1903,JS1904,JS1905,JS1906,JS1801 from JS19 where RB_ID='" + js18map.get("rb_id") + "'"
                		+ " and  js0100 in(select js0100 from js01 where nvl(js0120,'1')!='2' and js0100 in('"+js0100WhereSql+"'))";
            }else{
            	sqljs19 = "select JS0100,DC001,RB_ID,JS1902,JS1903,JS1904,JS1905,JS1906,JS1801 from JS19 where RB_ID='" + js18map.get("rb_id") + "'"
                		+ " and  js0100 in(select js0100 from js01 where nvl(js0120,'1')!='2')";
            }
            
            List<HashMap<String, Object>> js19list = cq.getListBySQL(sqljs19);

            List<Js19> sortlist = ranking(js19list);      */
            //新的民主推荐查JS33表
            String js33sql="select * from JS33 where RB_ID='" + js18map.get("rb_id") + "'"
            		+ " and  js0100 in(select js0100 from js01 where nvl(js0120,'1')!='2' and js0100 in('"+js0100WhereSql+"'))";
            List<Js33> js33list=sess.createSQLQuery(js33sql).addEntity(Js33.class).list();
            Collections.sort(js33list);
            QCJSFileExportBS fileBS = new QCJSFileExportBS();
            fileBS.exportTJJGB(js33list, js18map);
            List<String> fns = fileBS.getOutputFileNameList();
            String downloadPath = "";
            String downloadName = "";
            if (fns.size() == 1) {//一个文件直接下载
                downloadPath = fileBS.getOutputpath() + fns.get(0);
                downloadName = fns.get(0);
            } else if (fns.size() > 1) {//压缩
                downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
                downloadName = fileBS.getSheetName() + ".zip";
                Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
            } else {

            }
            String downloadUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
            this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
            this.getExecuteSG().addExecuteCode("downloadByUUID();");
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("导出失败");
            return EventRtnType.FAILD;
        }
    }

    //排序
    public List<Js19> ranking(List<HashMap<String, Object>> list) {
        List<Js19> js19list = new ArrayList<Js19>();
        for (Map<String, Object> map : list) {
            String JS0100 = inullback(map.get("js0100"));
            String DC001 = inullback(map.get("dc001"));
            String RB_ID = inullback(map.get("rb_id"));
            String A0000 = inullback(map.get("a0000"));
            String JS1902 = inullback(map.get("js1902"));
            String JS1903 = inullback(map.get("js1903"));
            String JS1904 = inullback(map.get("js1904"));
            String JS1905 = inullback(map.get("js1905"));
            String JS1906 = inullback(map.get("js1906"));
            String JS1801 = inullback(map.get("js1801"));
            Js19 js19 = new Js19(JS0100, DC001, RB_ID, A0000, JS1902, JS1903, JS1904, JS1905, JS1906, JS1801);
            js19list.add(js19);
        }
        Collections.sort(js19list);
        return js19list;
    }

    //如果未null返回空字符串
    public String inullback(Object obj) {
        return obj == null ? "" : obj.toString();
    }


    @PageEvent("tjgzfa")
    @Transaction
    public int tjgzfa() {
        try {
            /*String js2002 = this.getPageElement("js2002").getValue();
            String js2003 = this.getPageElement("js2003").getValue();
            String js2004 = this.getPageElement("js2004").getValue();
            String js2005 = this.getPageElement("js2005").getValue();
            String js2006 = this.getPageElement("js2006").getValue();
            String js2007 = this.getPageElement("js2007").getValue();*/
            
            String js0100s = this.getPageElement("js0100s").getValue();
            String rbId = this.getPageElement("rbId").getValue();
            String js0100WhereSql = "";
            if (!"".equals(js0100s)) {
                js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
            } else {
            	js0100WhereSql="";
            }
            
            String sql="";
            if(!StringUtil.isEmpty(js0100WhereSql)){
            	//选人
            	sql="select js2002,js2003,js2004,js2005,js2006,js2007 from js20 where  js20.rb_id='"+rbId+"' and js20.js0100 in('"+js0100WhereSql+"')";
            }else{
            	//导出所有的
            	sql="select js0102,js2002,js2003,js2004,js2005,js2006,js2007 from js20,js01 where js01.js0100=js20.js0100 and js20.rb_id='"+rbId+"'";
            }
            CommonQueryBS cq=new CommonQueryBS();
            QCJSFileExportBS fileBS = new QCJSFileExportBS();
            List<HashMap<String, Object>> listBySQL = cq.getListBySQL(sql);
            String path = UUID.randomUUID().toString().replaceAll("-", "");
        	for(int i=0;i<listBySQL.size();i++){
        		HashMap<String, Object> map = listBySQL.get(i);
        		String js2002=(String)map.get("js2002");
        		String js2003=(String)map.get("js2003");
        		String js2004=(String)map.get("js2004");
        		String js2005=(String)map.get("js2005");
        		String js2006=(String)map.get("js2006");
        		String js2007=(String)map.get("js2007");
        		//String js0102=(String)map.get("js0102");
        		fileBS.exportTJGZFA(path,js2002, js2003, js2004, js2005, js2006, js2007,i+1);
        	}
        				
            List<String> fns = fileBS.getOutputFileNameList();
            String downloadPath = "";
            String downloadName = "";
            /*if (fns.size() == 1) {//一个文件直接下载
                downloadPath = fileBS.getOutputpath() + fns.get(0);
                downloadName = fns.get(0);
            } else if (fns.size() > 1) {//压缩
                downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
                downloadName = fileBS.getSheetName() + ".zip";
                Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
            } else {

            }*/
            
            downloadPath = fileBS.getOutputpath() + path+File.separator+"推荐方案" + ".zip";
            downloadName = "推荐方案" + ".zip";
            Zip7z.zip7Z(fileBS.getOutputpath() + path+File.separator, downloadPath, null);
            
            String downloadUUID = UUID.randomUUID().toString();
            request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
            this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
            this.getExecuteSG().addExecuteCode("downloadByUUID();");
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("导出失败");
            return EventRtnType.FAILD;
        }
    }

    /**
     * 保存推荐
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("savetj.onclick")
    public int savetj(String js0100,HBSession sess) throws RadowException {
        String rbId = this.getPageElement("rbId").getValue();//批次id
        String a0000 = this.getPageElement("a0000").getValue();//人员id
        try {
            Js01 js01 = (Js01) sess.get(Js01.class, js0100);
            if (js01 == null) {
                this.setMainMessage("人员信息不存在！");
                return EventRtnType.NORMAL_SUCCESS;
            }
            //点击保存
            //存推荐共用信息(JS18)（每次都删除后再保存）
            //存推荐票数(JS19)
            
            String tjsj = this.getPageElement("tjsj").getValue();
            String tjcjrs = this.getPageElement("tjcjrs").getValue();
            String tjother = this.getPageElement("tjother").getValue();
            String tjrs = this.getPageElement("tjrs").getValue();
            //清空推荐信息表
            String sql = "delete from JS18";
            SQLQuery query = sess.createSQLQuery(sql);
            query.executeUpdate();
            sess.flush();

            String js1801 = UUID.randomUUID().toString().replaceAll("-", "");
            //插入数据
            HBUtil.executeUpdate("insert into JS18(js0100,rb_id,js1801,js1802,js1803,js1804,js1805)"
                            + " values(?,?,?,?,?,?,?)",
                    new Object[]{js0100, rbId, js1801, tjsj, tjcjrs, tjother,tjrs});


 /*           //存推荐票数(JS19)----------hytjp、thtjp
            String hytjps = this.getPageElement("hytjp").getValue();
            String thtjps = this.getPageElement("thtjp").getValue();
//	        System.out.println(hytjps);
//	        System.out.println(thtjps);
            String hyyxp = this.getPageElement("hyyxp").getValue();
            String thyxp = this.getPageElement("thyxp").getValue();
            String xbzh = this.getPageElement("xbzh").getValue();
            String zztjqk = this.getPageElement("zztjqk").getValue();
            
            String thtjfw = this.getPageElement("thtjfw").getValue();//谈话推荐范围
            String hytjfw = this.getPageElement("hytjfw").getValue();//会议推荐范围
            

            //同一个人保存两次，
            Object js19obj = sess.createSQLQuery("select * from JS19 where js0100='" + js0100 + "'").uniqueResult();
            if (js19obj == null) {
                //插入数据
                HBUtil.executeUpdate("insert into JS19(js0100,rb_id,js1902,js1903,js1904,js1905,js1906,js1801,js1907,js1908,js1909,js1910,js1911,js1912)"
                                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{js0100, rbId, js01.getJs0102(), js01.getJs0108(), hytjps, thtjps, null, js1801,hyyxp,thyxp,xbzh,zztjqk,thtjfw,hytjfw});
            } else {
                String upsql = "update JS19 set js1801='" + js1801 + "', js1904='" + hytjps + "',js1905='" + thtjps + "',js1907='"+hyyxp+"',js1908='"+thyxp+"',js1909='"+xbzh+"',js1910='"+zztjqk+"',js1911='"+thtjfw+"',js1912='"+hytjfw+"' where js0100='" + js0100 + "'";
                SQLQuery queryUp = sess.createSQLQuery(upsql);
                queryUp.executeUpdate();
                sess.flush();
            }
  */
          //新的民主推荐保存
            String  js33row=this.getPageElement("js33row").getValue();
            int rownum=Integer.parseInt(js33row);
            List js33List=sess.createSQLQuery("select * from JS33 where js0100='" + js0100 + "'").list();
            //有记录先删除再重新保存
            if(js33List.size()>0) {
            	HBUtil.executeUpdate("delete from js33 where js0100='" + js0100 + "'");
            }
            for(int i=1;i<=rownum;i++){
            	String  js3302=this.getPageElement("js3302_"+i).getValue();
            	String  js3312=this.getPageElement("js3312_"+i).getValue();
            	String  thyxp=this.getPageElement("thyxp_"+i).getValue();
            	String  thtjp=this.getPageElement("thtjp_"+i).getValue();
            	String  thtjfw=this.getPageElement("thtjfw_"+i).getValue();
            	String  hyyxp=this.getPageElement("hyyxp_"+i).getValue();
            	String  hytjp=this.getPageElement("hytjp_"+i).getValue();
            	String  hytjfw=this.getPageElement("hytjfw_"+i).getValue();
            	String  xbzh=this.getPageElement("xbzh_"+i).getValue();
            	String  zztjqk=this.getPageElement("zztjqk_"+i).getValue();
            	Js33 js33=new Js33();
            	js33.setJs3301(UUID.randomUUID().toString().replaceAll("-", ""));
            	js33.setJs3302(js3302);
            	js33.setJs3312(js3312);
            	js33.setJs3304(thyxp);
            	js33.setJs3305(thtjp);
            	js33.setJs3306(thtjfw);
            	js33.setJs3307(hyyxp);
            	js33.setJs3308(hytjp);
            	js33.setJs3309(hytjfw);
            	js33.setJs3310(xbzh);
            	js33.setJs3311(zztjqk);
            	js33.setJs1801(js1801);
            	js33.setRb_id(rbId);
            	js33.setJs0100(js0100);
            	sess.save(js33);
            	
            }
            
            Js20 js20=(Js20)sess.get(Js20.class, js0100);
            if(js20==null){
            	js20=new Js20();
            	js20.setJs0100(js0100);
            	js20.setRbId(rbId);
            }
            String js2002=this.getPageElement("js2002").getValue();
            String js2003=this.getPageElement("js2003").getValue();
            String js2004=this.getPageElement("js2004").getValue();
            String js2005=this.getPageElement("js2005").getValue();
            String js2006=this.getPageElement("js2006").getValue();
            String js2007=this.getPageElement("js2007").getValue();
            js20.setJs2002(js2002);
            js20.setJs2003(js2003);
            js20.setJs2004(js2004);
            js20.setJs2005(js2005);
            js20.setJs2006(js2006);
            js20.setJs2007(js2007);
            
            sess.saveOrUpdate(js20);
            
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("保存失败：" + e.getCause().getMessage());
        }
        return 0;
    }

    
    /**
     * 点击tab的时候判断是否显示推送按钮
     * @return
     * @throws RadowException 
     */
    @PageEvent("tabclickshowts")
    public int tabclickshowts() throws RadowException{
    	String a0000=this.getPageElement("a0000").getValue();
    	String rbId=this.getPageElement("rbId").getValue();
    	HBSession sess = HBUtil.getHBSession();
		 List list2 = sess.createSQLQuery("select CRP014 from checkregperson where A0000='"+a0000+"' and checkregid='"+rbId+"'").list();
	     if(list2!=null && list2.size()>0){
	     	if(list2.get(0)!=null){
	     		String tsresult=(String)list2.get(0);
	         	this.getExecuteSG().addExecuteCode("this.hideTsgbjd('"+tsresult+"');");
	     	}else{
	     		this.getExecuteSG().addExecuteCode("this.hideTsgbjd('待审核');");
	     	}
	     }else{
	    	//查询该批次是否在监督处存在
	    	List list = sess.createSQLQuery("select checkregid from checkreg where checkregid='"+rbId+"'").list();
	     	if(list.size()>0){
	     		this.getExecuteSG().addExecuteCode("this.hideTsgbjd('');");
	     	}else{
	     		this.getExecuteSG().addExecuteCode("this.showTsgbjd();");
	     	}
	     }
    	return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 数据回显
     *
     * @param id
     * @return
     * @throws RadowException
     * @throws AppException 
     */
    @PageEvent("peopleInfo")
    @Synchronous(true)
    public int peopleInfo(String id) throws RadowException, AppException {
    	HBSession sess = HBUtil.getHBSession();
    	String js0111 = "";
    	String js0117 = "";
    	String js0100 = this.getPageElement("js0100").getValue();
    	String rbId = this.getPageElement("rbId").getValue();
    	Js01 js01 = (Js01) sess.get(Js01.class, js0100);
    	String nrsql="select * from js22 where a0000='"+id+"' and JS0100='"+js0100+"' order by sortid";
    	List<Object[]> nrlist = sess.createSQLQuery(nrsql).list();
    	if(nrlist.size()==1) {
    		js0111 = nrlist.get(0)[0].toString()+nrlist.get(0)[2].toString();
    		System.out.println("js0111:"+js0111);
    		this.getPageElement("js0111").setValue(js0111);
    	}
    	
    	String nmsql = "select * from a02 where a0255='1' and a0000='" + id + "' order by lpad(a0223,5,'" + 0 + "') ";
    	String a0200ssql="select js0123 from js01 where js01.a0000='"+id+"' and js01.rb_id='"+rbId+"'";
    	CommonQueryBS cq=new CommonQueryBS();
    	List<HashMap<String, Object>> nmlist = cq.getListBySQL(a0200ssql);
    	String js0123s="";
    	if(nmlist.size()>0){
    		HashMap<String, Object> map = nmlist.get(0);
    		String js0123 = inullback(map.get("js0123"));
    		js0123s=js0123.replaceAll(",", "','");
    	}
    	/*if(!"top".equals("top")){
    		nmsql = "select * from a02 where a0255='1' and a0000='" + id + "' and a0200 in('"+js0123s+"') order by lpad(a0223,5,'" + 0 + "') ";
    		if(js01.getJs0122()!=null && (js01.getJs0122().equals("2")
            		|| js01.getJs0122().equals("3") || js01.getJs0122().equals("4"))) {
    			nmsql = "select * from v_js_a02 where a0255='1' and a0000='" + id + "' and a0200 in('"+js0123s+"') and v_xt='"+js01.getJs0122()+"' order by lpad(a0223,5,'" + 0 + "') ";
    		}
    	} else {*/
    		if(js01.getJs0122()!=null && (js01.getJs0122().equals("2")
            		|| js01.getJs0122().equals("3") || js01.getJs0122().equals("4"))) {
    			nmsql = "select * from v_js_a02 where a0255='1' and a0000='" + id + "' and v_xt='"+js01.getJs0122()+"' order by lpad(a0223,5,'" + 0 + "') ";
    		}
    	//}
    	List<Object[]> nmlist2 = sess.createSQLQuery(nmsql).list();
    	if(nmlist2.size()==1) {
    		js0117 = nmlist2.get(0)[2].toString()+nmlist2.get(0)[6].toString();
    		System.out.println("js0117:"+js0117);
    		this.getPageElement("js0117").setValue(js0117);
    	}
    	
    	try {
        	String xt = js01.getJs0122();
            String a0000id = js01.getA0000();
            String fysql = "";
            if(xt.equals("1")) {
            	fysql = "select a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192,a0192a from a01 where a0000='" + a0000id + "'";
            } else {
            	fysql = "select a0000,a0101,a0104,a0107,a0117,a0111a,a0140,a0134,a0196,a0192,a0192a from v_js_a01 where a0000='" + a0000id + "' and v_xt='"+xt+"'";
            }
            String data;
            List<Object[]> list = sess.createSQLQuery(fysql).list();
            if (list.size() == 1) {
                data = bs.getPersonInfo(list, sess, xt);
            } else {
                data = "";
                a0000id = "";
            }
            
            //查询干部监督中是否存在
            String rbid=this.getPageElement("rbId").getValue();
            List list2 = sess.createSQLQuery("select CRP014 from checkregperson where A0000='"+a0000id+"' and checkregid='"+rbid+"'").list();
            if(list2!=null && list2.size()>0){
            	if(list2.get(0)!=null){
            		String tsresult=(String)list2.get(0);
            		this.getExecuteSG().addExecuteCode("this.hideTsgbjd('"+tsresult+"');");
            	}else{
            		this.getExecuteSG().addExecuteCode("this.hideTsgbjd('待审核');");
            	}
            }else{
            	this.getExecuteSG().addExecuteCode("this.showTsgbjd();");
            }
            
            this.getExecuteSG().addExecuteCode("document.getElementById('personImg').src='" + this.request.getContextPath() + "/servlet/DownloadUserHeadImage?a0000=" + URLEncoder.encode(URLEncoder.encode(a0000id.toString(), "UTF-8"), "UTF-8") + "&xt="+xt+"'");
            this.getExecuteSG().addExecuteCode("document.getElementById('pdata').innerHTML='" +
                    data.replaceAll("<", "&lt;").replaceAll("'", "&acute;").replaceAll("\\(lt;", "<") + "'");
            this.setBusinessInfo(sess,js0111,js0117);
            return EventRtnType.NORMAL_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询失败！");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }

    
    
    
    private void setBusinessInfo(HBSession sess,String js0111,String js0117) {
        try {
            String js0100 = this.getPageElement("js0100").getValue();
            //String a0000 = this.getPageElement("a0000").getValue();
            //List<Js01> list = sess.createQuery("from Js01 where a0000='"+a0000+"'").list();

            Js01 js01 = (Js01) sess.get(Js01.class, js0100);
            if (js01 == null) {//清空页面信息
                js01 = new Js01();
                js01.setA0000("");
                js01.setJs0100("");
            }
            bs.setPm(this);
            bs.setPageInfo(js01, sess ,js0111,js0117);
            //设置文件信息
            List<JsAtt> jsattlist = sess.createQuery("from JsAtt where js0100='" + js0100 + "'").list();

            if (jsattlist != null) {
                Map<String, List<Map<String, String>>> m = new HashMap<String, List<Map<String, String>>>();
                List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
                for (JsAtt jsatt : jsattlist) {
                    String tablename = jsatt.getJsa02();
                    listmap = m.get(tablename);
                    if (listmap == null) {
                        listmap = new ArrayList<Map<String, String>>();
                        m.put(tablename, listmap);
                    }

                    Map<String, String> map = new HashMap<String, String>();
                    map.put("id", jsatt.getJsa00());
                    map.put("name", jsatt.getJsa04());
                    map.put("fileSize", jsatt.getJsa08());
                    //map2.put("readOnly", "true");

                    listmap.add(map);
                }

                String[] nums = {"02", "99", "19", "14", "07", "08"};
                for (int i = 0; i < nums.length; i++) {
                    String key = "JS" + nums[i];
                    listmap = m.get(key);
                    if (listmap == null) {
                        listmap = new ArrayList<Map<String, String>>();
                    }
                    this.setFilesInfo(key.replace("JS", "file"), listmap, true);
                }

//				String fx;
//				for(int i=2; i<12; i++){
//					if(i<10){
//						fx = "0"+i;
//					}else{
//						fx = i+"";
//					}
//					String key = "JS"+fx;
//					listmap = m.get(key);
//					if(listmap==null){
//						listmap = new ArrayList<Map<String, String>>();
//					}
//					this.setFilesInfo(key.replace("JS", "file"),listmap,true);
//				}
//				this.getExecuteSG().addExecuteCode("$('.ui-tabs-active').click()");
            }


        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("查询出错！");
        }
    }

    /**
     * 职数预审修改保存
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("savech.onclick")
    public int savech(String id) throws RadowException {
    	
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 职数预审修改保存
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("saveZS.onclick")
    public int saveZS(String id) throws RadowException {
        String rbId = this.getPageElement("rbId").getValue();//批次id
        String block = this.getPageElement("block").getValue();//职数预审展示行数
        try {
            HBSession sess = HBUtil.getHBSession();

            bs.setPm(this);
            bs.saveJs12(block, rbId, sess);
            sess.flush();
            this.setMainMessage("保存成功，待干部监督处审核。");
            //this.getExecuteSG().addExecuteCode("showimg()");
            this.setNextEventName("TableDataR");
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("保存失败：" + e.getCause().getMessage());
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 青干预审修改保存
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("saveQG.onclick")
    public int saveQG() throws RadowException {
        String rbId = this.getPageElement("rbId").getValue();//批次id
        try {
            HBSession session = HBUtil.getHBSession();
            bs.setPm(this);

            //update
            List<Js13Pojo> js13Pojos = session.createSQLQuery(" select * from JS13 js13 where js13.RB_ID = :rbid ").setParameter("rbid", rbId).setResultTransformer(Transformers.aliasToBean(Js13Pojo.class)).list();
            if (js13Pojos.size() > 0) {
                bs.updateJs13(js13Pojos.get(0).getJS1300(), rbId, session);
            } else {
                bs.saveJs13(rbId, session);
            }
            List<Js99Pojo> js99Pojos = session.createSQLQuery(" select * from JS99 js99 where js99.RB_ID = :rbid ").setParameter("rbid", rbId).setResultTransformer(Transformers.aliasToBean(Js99Pojo.class)).list();
            if (js99Pojos.size() > 0) {
                bs.updateJs99(js99Pojos.get(0).getJS9900(), rbId, session);
            } else {
                bs.saveJs99(rbId, session);
            }
            session.flush();
            this.setMainMessage("保存成功，待青年干部处审核。");
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("保存失败：" + e.getCause().getMessage());
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    //存民主推荐
    public void savejs1819(String js0100, String rbid) throws RadowException, AppException {
        //js18
        String JS1802 = this.getPageElement("tjsj").getValue();//推荐时间
        String JS1803 = this.getPageElement("tjcjrs").getValue();//推荐参加人数
        String JS1804 = this.getPageElement("tjother").getValue();//其他
        //js19
 /*       String JS1907 = this.getPageElement("hyyxp").getValue();//会议-有效票
        String JS1904 = this.getPageElement("hytjp").getValue();//会议-的票
        String JS1908 = this.getPageElement("thyxp").getValue();//谈话-有效票
        String JS1905 = this.getPageElement("thtjp").getValue();//谈话-de票
        String JS1909 = this.getPageElement("xbzh").getValue();//竞争性选拔综合情况
        String JS1910 = this.getPageElement("zztjqk").getValue();//个人推荐或组织推荐情况   */
        HBSession sess = HBUtil.getHBSession();
        //插入数据
        String JS1801 = UUID.randomUUID().toString().replaceAll("-", "");
        try {
            //js18
            boolean ishasRecord = bs.ishasRecord("js18", js0100);
            if (ishasRecord) {
                //update  有记录
                String updatesql = "update js18 set RB_ID='" + rbid + "',JS1802='" + JS1802 + "',JS1803='" + JS1803 + "',JS1804='" + JS1804 + "' where js0100='" + js0100 + "'";
                sess.createSQLQuery(updatesql).executeUpdate();
                sess.flush();
            } else {
                HBUtil.executeUpdate("insert into js18(JS0100,RB_ID,JS1801,JS1802,JS1803,JS1804)"
                                + " values(?,?,?,?,?,?)",
                        new Object[]{js0100, rbid, JS1801, JS1802, JS1803, JS1804});
            }

            //js19
/*           boolean ishasRecord1 = bs.ishasRecord("js19", js0100);
            if (ishasRecord1) {
                //update  有记录
                String updatesql = "update js19 set RB_ID='" + rbid + "',JS1904='" + JS1904 + "',JS1905='" + JS1905 + "',JS1907='" + JS1907 + "',JS1908='" + JS1908 + "',JS1909='" + JS1909 + "',JS1910='" + JS1910 + "'";
                sess.createSQLQuery(updatesql).executeUpdate();
                sess.flush();
            } else {
                HBUtil.executeUpdate("insert into js19(JS0100,RB_ID,JS1904,JS1905,JS1907,JS1908,JS1909,JS1910)"
                                + " values(?,?,?,?,?,?,?,?)",
                        new Object[]{js0100, rbid, JS1904, JS1905, JS1907, JS1908, JS1909, JS1910});
            }                  */
            String  js33row=this.getPageElement("js33row").getValue();
            int rownum=Integer.parseInt(js33row);
            List js33List=sess.createSQLQuery("select * from JS33 where js0100='" + js0100 + "'").list();
            if(js33List.size()>0) {
            	HBUtil.executeUpdate("delete from js33 where js0100='" + js0100 + "'");
            }
            for(int i=1;i<=rownum;i++){
            	String  js3302=this.getPageElement("js3302_"+i).getValue();
            	String  thyxp=this.getPageElement("thyxp_"+i).getValue();
            	String  thtjp=this.getPageElement("thtjp_"+i).getValue();
            	String  thtjfw=this.getPageElement("thtjfw_"+i).getValue();
            	String  hyyxp=this.getPageElement("hyyxp_"+i).getValue();
            	String  hytjp=this.getPageElement("hytjp_"+i).getValue();
            	String  hytjfw=this.getPageElement("hytjfw_"+i).getValue();
            	String  xbzh=this.getPageElement("xbzh_"+i).getValue();
            	String  zztjqk=this.getPageElement("zztjqk_"+i).getValue();
            	Js33 js33=new Js33();
            	js33.setJs3301(UUID.randomUUID().toString().replaceAll("-", ""));
            	js33.setJs3302(js3302);
            	js33.setJs3304(thyxp);
            	js33.setJs3305(thtjp);
            	js33.setJs3306(thtjfw);
            	js33.setJs3307(hyyxp);
            	js33.setJs3308(hytjp);
            	js33.setJs3309(hytjfw);
            	js33.setJs3310(xbzh);
            	js33.setJs3311(zztjqk);
            	js33.setJs1801(JS1801);
            	js33.setRb_id(rbid);
            	js33.setJs0100(js0100);
            	sess.save(js33);
            	
            }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //存考察
    public void savejs14(String js0100, String rbid) throws RadowException, AppException {
        String JS1407 = this.getPageElement("sfjtyj").getValue();//是否集体研究确定考察人选
        String JS1402 = this.getPageElement("js1402").getValue();//考察时间
        String JS1405 = this.getPageElement("js1405").getValue();//考察组成员
        String JS1408 = this.getPageElement("cpycl").getValue();//民主测评优称率%
        String JS1409 = this.getPageElement("zqyjqk").getValue();//征求意见情况
        String JS1410 = this.getPageElement("jjjdyj").getValue();//纪检（监察）鉴定意见
        String JS1411 = this.getPageElement("kcqjjb").getValue();//考察期间有无举报
        String JS1412 = this.getPageElement("hcqk").getValue();//查核情况
        String JS1413 = this.getPageElement("jsnkhqk").getValue();//近三年年度考核情况
        String JS1414 = this.getPageElement("slllshqk").getValue();//干部档案及“三龄两历一身份”审核情况
        String JS1415 = this.getPageElement("sqbgqk").getValue();//执行事前报告情况
        String JS1416 = this.getPageElement("grsxchqk").getValue();//个人有关事项报告查核情况
        HBSession sess = HBUtil.getHBSession();
        boolean ishasRecord = bs.ishasRecord("js14", js0100);
        if (ishasRecord) {
            String updatesql = "update js14 set RB_ID='" + rbid + "',JS1402='" + JS1402 + "',JS1407='" + JS1407 + "',JS1405='" + JS1405 + "',JS1408='" + JS1408 + "',JS1409='" + JS1409 + "',JS1410='" + JS1410 + "',JS1411='" + JS1411 + "',JS1412='" + JS1412 + "',JS1413='" + JS1413 + "',JS1414='" + JS1414 + "',JS1415='" + JS1415 + "',JS1416='" + JS1416 + "' where js0100='" + js0100 + "'";
            sess.createSQLQuery(updatesql).executeUpdate();
            sess.flush();
        } else {
            try {
                HBUtil.executeUpdate("insert into js14(JS0100,RB_ID,JS1407,JS1402,JS1405,JS1408,JS1409,JS1410,JS1411,JS1412,JS1413,JS1414,JS1415,JS1416)"
                                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                        new Object[]{js0100, rbid, JS1407, JS1402, JS1405, JS1408, JS1409, JS1410, JS1411, JS1412, JS1413, JS1414, JS1415, JS1416});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //存讨论决定
    public void savejs15(String js0100, String rbid) throws RadowException, AppException {
        String JS1511 = this.getPageElement("bhtljg").getValue();//部会时间
        String JS1501 = this.getPageElement("js1501").getValue();//部会参加人数
        String JS1513 = this.getPageElement("bhbjqk").getValue();//部会表决情况
        String JS1512 = this.getPageElement("cwtljg").getValue();//常委时间
        String JS1505 = this.getPageElement("js1505").getValue();//常委参加人数
        String JS1514 = this.getPageElement("cwbjqk").getValue();//常委表决情况
        HBSession sess = HBUtil.getHBSession();
        boolean ishasRecord = bs.ishasRecord("js15", js0100);
        if (ishasRecord) {
            String updatesql = "update js15 set RB_ID='" + rbid + "',JS1511='" + JS1511 + "',JS1501='" + JS1501 + "',JS1513='" + JS1513 + "',JS1512='" + JS1512 + "',JS1505='" + JS1505 + "',JS1514='" + JS1514 + "' where js0100='" + js0100 + "'";
            sess.createSQLQuery(updatesql).executeUpdate();
            sess.flush();
        } else {
            try {
                HBUtil.executeUpdate("insert into js15(JS0100,RB_ID,JS1511,JS1501,JS1513,JS1512,JS1505,JS1514)"
                                + " values(?,?,?,?,?,?,?,?)",
                        new Object[]{js0100, rbid, JS1511, JS1501, JS1513, JS1512, JS1505, JS1514});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //存任职
    public void savejs08(String js0100, String rbid) throws RadowException, AppException {
        String JS0806 = this.getPageElement("gsxs").getValue();//公示形式
        String JS0802 = this.getPageElement("js0802").getValue();//公示形式
        String JS0807 = this.getPageElement("gsjywjb").getValue();//公示期间有无举报
        String JS0808 = this.getPageElement("chqk").getValue();//查核情况
        HBSession sess = HBUtil.getHBSession();
        boolean ishasRecord = bs.ishasRecord("js08", js0100);
        if (ishasRecord) {
            String updatesql = "update js08 set RB_ID='" + rbid + "',JS0806='" + JS0806 + "',JS0802='" + JS0802 + "',JS0807='" + JS0807 + "',JS0808='" + JS0808 + "' where js0100='" + js0100 + "'";
            sess.createSQLQuery(updatesql).executeUpdate();
            sess.flush();
        } else {
            try {
                HBUtil.executeUpdate("insert into js08(JS0100,RB_ID,JS0806,JS0802,JS0807,JS0808)"
                                + " values(?,?,?,?,?,?)",
                        new Object[]{js0100, rbid, JS0806, JS0802, JS0807, JS0808});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 判断是否上传了文件,并返回没有上传文件的环节
     *
     * @throws AppException
     */
    @PageEvent("hasUploadAll")
    public int hasUploadAll() throws RadowException, AppException {
        String js0100 = this.request.getParameter("js0100");//批次
        HBSession sess = HBUtil.getHBSession();
        Js01 js01 = (Js01) sess.get(Js01.class, js0100);
        if (js01 == null) {
            this.setMainMessage("人员信息不存在！");
            return EventRtnType.NORMAL_SUCCESS;
        }
        String sql = "select jsa02 from js_att where js0100='" + js01.getJs0100() + "'";
        CommQuery cq = new CommQuery();
        List<HashMap<String, Object>> attlist = cq.getListBySQL(sql);

        Map<String, String> map = new HashMap<String, String>();
        for (HashMap<String, Object> map1 : attlist) {
            String jsa02 = map1.get("jsa02").toString();
            map.put(jsa02, "");
        }
        StringBuffer msgBuf = new StringBuffer();
        String[] array = {"JS02", "JS99", "JS19", "JS14", "JS07", "JS08"};
        for (int i = 0; i < array.length; i++) {
            String js = array[i];
            if (!map.containsKey(js)) {
                if (js.equals("JS02")) {
                    msgBuf.append("[动议]、");
                } else if (js.equals("JS99")) {
                    msgBuf.append("[考核与听取意见]、");
                } else if (js.equals("JS19")) {
                    msgBuf.append("[民主推荐]、");
                } else if (js.equals("JS14")) {
                    msgBuf.append("[组织考察]、");
                } else if (js.equals("JS07")) {
                    msgBuf.append("[讨论决定]、");
                } else if (js.equals("JS08")) {
                    msgBuf.append("[任前公示]、");
                }
            }
        }
        if (msgBuf.toString().length() != 0) {//表示有环节没有上传文件
            String mm = msgBuf.toString();
            this.setSelfDefResData("{\"msg\":\"" + mm.substring(0, mm.length() - 1) + "\"}");
            return EventRtnType.XML_SUCCESS;
        } else {
            //上传成功
            return EventRtnType.NORMAL_SUCCESS;
        }
    }

    /**
     * 导出全程纪实表
     *
     * @return
     */
    //@PageEvent("qcjs.onclick")
    @PageEvent("qcjs")
    public int qcjs() throws RadowException {
        String rbId = this.getPageElement("rbId").getValue();//批次id
        //String js0100 = this.getPageElement("qcjsjs0100").getValue().substring(1, this.getPageElement("qcjsjs0100").getValue().length());//任免人员id
        //String a0000 = this.getPageElement("a0000").getValue().substring(1, this.getPageElement("a0000").getValue().length());//人员id
        String js0100 = this.getPageElement("qcjsjs0100").getValue();
        String a0000 = this.getPageElement("a0000").getValue();
        String ryid = this.getPageElement("qcjsid").getValue();//人员号码
        String rbno = this.getPageElement("rb_no").getValue();//批次编号
        if (js0100 == null || "".equals(js0100)) {
            this.setMainMessage("请选择人员！");
            return EventRtnType.NORMAL_SUCCESS;
        }
        
        //String[] js0100arr = js0100.split(",");
        //String[] a0000arr = a0000.split(",");
        //for(int i = 0;i<js0100arr.length;i++) {
        //	js0100 = js0100arr[i];
        //	a0000 = a0000arr[i];
        //	ryid = i+1+"";
        	
        	String filename = "全程纪实.doc";
            //两个VALS,Flds域,Vals值：取值
            StringBuffer sbuf = new StringBuffer();
            sbuf.append(rbno +"00"+ryid +",,,");
            String xt = "";
            try {
                //存页面值到数据库，失败不报错，因为设置了页面display:none,提高页面加载速度
                try {
                    savejs08(js0100, rbId);
                    savejs15(js0100, rbId);
                    savejs14(js0100, rbId);
                    savejs1819(js0100, rbId);
                } catch (Exception e) {

                }


                //上传文件
                //file02(动议)  考核与听取意见(file99)   民主推荐(file19)   组织考察(file14)  讨论决定file07   任前公示file08
                String[] files = {"file02", "file99", "file19", "file14", "file07", "file08"};
                for (int i = 0; i < files.length; i++) {
                    String file = files[i];
                    this.upLoadFile(file, true);
                }


                //查js01
                HBSession sess = HBUtil.getHBSession();
                Js01 js01 = (Js01) sess.get(Js01.class, js0100);
                if (js01 == null) {
                    this.setMainMessage("人员信息不存在！");
                    return EventRtnType.NORMAL_SUCCESS;
                }
                xt = js01.getJs0122();
                sbuf.append(js01.getJs0102() + ",,,");
                filename = js01.getJs0102() + "_全程纪实.doc";
                if (js01.getJs0114() != null && "2".equals(js01.getJs0114())) {
                    sbuf.append("女" + ",,,");
                } else {
                    sbuf.append("男" + ",,,");
                }
                sbuf.append(js01.getJs0103() + ",,,");
                sbuf.append(js01.getJs0104() + ",,,");
                sbuf.append(js01.getJs0105() + ",,,");
                sbuf.append(js01.getJs0106() + ",,,");
                sbuf.append(js01.getJs0108() + ",,,");
                sbuf.append(js01.getJs0109() + ",,,");
                sbuf.append(js01.getJs0111() + ",,,");

                //查A01,根据A0000
                CommonQueryBS cq = new CommonQueryBS();
                String queryA01 = "select a0117,a0111a,a0114a,to_char(a01.A1701) as a1701 from a01 where a0000='" + a0000 + "'";
                if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
                	queryA01 = "select a0117,a0111a,a0114a,to_char(a01.A1701) as a1701 from v_js_a01 where a0000='" + a0000 + "' and v_xt='"+xt+"'";
                }
                HashMap<String, Object> mapA01 = cq.getMapBySQL(queryA01);
                String A0117 = getValueFromCodeValue("A0117", (String) mapA01.get("a0117"));
                String a0111a = (String) mapA01.get("a0111a");
                String a0114a = (String) mapA01.get("a0114a");
                String a1701 = (String) mapA01.get("a1701");
                if(a1701 != null){
    				//简历格式化
    				StringBuffer originaljl = new StringBuffer("");
    				String jianli = AddPersonPageModel.formatJL(a1701,originaljl);
    				a1701 = jianli;
    			}
                sbuf.append(A0117 + ",,,");
                sbuf.append(a0111a + ",,,");
                sbuf.append(a0114a + ",,,");
                sbuf.append(a1701 + ",,,");

                //A36  只展示4个
                String a36sql = "select A3604A,A3601,A3607,A3627,A3611 from A36 where a0000='" + a0000 + "'";
                if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
                	queryA01 = "select A3604A,A3601,A3607,A3627,A3611 from v_js_A36 where a0000='" + a0000 + "' and v_xt='"+xt+"'";
                }
                List<HashMap<String, Object>> lista36 = cq.getListBySQL(a36sql);
                for (int i = 0; i < lista36.size(); i++) {
                    if (i == 4) {
                        break;
                    }
                    HashMap<String, Object> a36map = lista36.get(i);
                    String A3604A = (String) a36map.get("a3604a");
                    String A3601 = (String) a36map.get("a3601");
                    String A3607 = (String) a36map.get("a3607");
                    String A3627 = (String) a36map.get("a3627");
                    String A3611 = (String) a36map.get("a3611");

                    sbuf.append(A3604A + ",,,");
                    sbuf.append(A3601 + ",,,");
                    sbuf.append(A3607 + ",,,");
                    sbuf.append(A3627 + ",,,");
                    sbuf.append(A3611 + ",,,");
                }
                if (lista36.size() < 4) {
                    for (int i = lista36.size() + 1; i <= 4; i++) {
                        sbuf.append(" " + ",,,");
                        sbuf.append(" " + ",,,");
                        sbuf.append(" " + ",,,");
                        sbuf.append(" " + ",,,");
                        sbuf.append(" " + ",,,");
                    }
                }

                //民主推荐
                String js19sql = "select js1802,js1803,js1908,js1907,js1905,js1904,js1909,js1910 from JS19 left join JS18 on JS18.js0100=JS19.js0100 and JS19.js0100='" + js01.getJs0100() + "'";
                HashMap<String, Object> mapjs19 = cq.getMapBySQL(js19sql);
                sbuf.append(mapjs19.get("js1802") + ",,,");
                sbuf.append(mapjs19.get("js1803") + ",,,");
                sbuf.append(mapjs19.get("js1908") + ",,,");
                sbuf.append(mapjs19.get("js1905") + ",,,");
                sbuf.append(mapjs19.get("js1802") + ",,,");
                sbuf.append(mapjs19.get("js1803") + ",,,");
                sbuf.append(mapjs19.get("js1907") + ",,,");
                sbuf.append(mapjs19.get("js1904") + ",,,");
                sbuf.append(mapjs19.get("js1909") + ",,,");
                sbuf.append(mapjs19.get("js1910") + ",,,");

                //考察
                String js14sql = "select js1407,js1402,js1405,js1408,js1409,js1410,js1410,js1411,js1412,js1413,js1414,js1415,js1416 from js14 where js0100='" + js01.getJs0100() + "'";
                HashMap<String, Object> mapjs14 = cq.getMapBySQL(js14sql);
                String js1407 = (String) mapjs14.get("js1407");
                if ("是".equals(js1407) || "否".equals(js1407)) {
                    sbuf.append(mapjs14.get("js1407") + ",,,");
                } else {
                    sbuf.append("" + ",,,");
                }

                sbuf.append(mapjs14.get("js1402") + ",,,");
                sbuf.append(mapjs14.get("js1405") + ",,,");
                sbuf.append(mapjs14.get("js1408") + ",,,");
                sbuf.append(mapjs14.get("js1409") + ",,,");
                sbuf.append(mapjs14.get("js1410") + ",,,");
                sbuf.append(mapjs14.get("js1411") + ",,,");
                sbuf.append(mapjs14.get("js1412") + ",,,");
                sbuf.append(mapjs14.get("js1413") + ",,,");
                sbuf.append(mapjs14.get("js1414") + ",,,");
                sbuf.append(mapjs14.get("js1415") + ",,,");
                sbuf.append(mapjs14.get("js1416") + ",,,");

                //讨论决定
                String js15sql = "select js1511,js1501,js1513,js1512,js1505,js1514 from js15 where js0100='" + js01.getJs0100() + "'";
                HashMap<String, Object> mapjs15 = cq.getMapBySQL(js15sql);
                sbuf.append(mapjs15.get("js1511") + ",,,");
                sbuf.append(mapjs15.get("js1501") + ",,,");
                sbuf.append(mapjs15.get("js1513") + ",,,");
                sbuf.append(mapjs15.get("js1512") + ",,,");
                sbuf.append(mapjs15.get("js1505") + ",,,");
                sbuf.append(mapjs15.get("js1514") + ",,,");

                //任职
                String js08sql = "select js0806,js0802,js0807,js0808 from js08 where js0100='" + js01.getJs0100() + "'";
                HashMap<String, Object> mapjs08 = cq.getMapBySQL(js08sql);
                sbuf.append(mapjs08.get("js0806") + ",,,");
                sbuf.append(mapjs08.get("js0802") + ",,,");
                sbuf.append(mapjs08.get("js0807") + ",,,");
                sbuf.append(mapjs08.get("js0808") + ",,,");
                
            } catch (Exception e) {
                e.printStackTrace();
            }

            QCJSFileExportBS fileBS = new QCJSFileExportBS();
            String[] Flds = {"qcjsid","JS0102", "JS0114", "JS0103", "JS0104", "JS0105", "JS0106", "JS0108", "JS0109", "JS0111", "A0117", "A0111A", "A0114A", "A1701"
                    , "A3604A", "A3601", "A3607", "A3627", "A3611"
                    , "A3604A_1", "A3601_1", "A3607_1", "A3627_1", "A3611_1"
                    , "A3604A_2", "A3601_2", "A3607_2", "A3627_2", "A3611_2"
                    , "A3604A_3", "A3601_3", "A3607_3", "A3627_3", "A3611_3"
                    , "JS1802", "JS1803", "JS1908", "JS1905", "JS1802_1", "JS1803_1", "JS1907", "JS1904", "JS1909", "JS1910"
                    , "JS1407", "JS1402", "JS1405", "JS1408", "JS1409", "JS1410", "JS1411", "JS1412", "js1413", "JS1414", "JS1415", "JS1416"
                    , "JS1511", "JS1501", "JS1513", "JS1512", "JS1505", "JS1514"
                    , "JS0806", "JS0802", "JS0807", "JS0808", "image"};
            String[] Vals = sbuf.toString().split(",,,");

            StringBuffer sval = new StringBuffer();
            for (int i = 0; i < Vals.length; i++) {
                String v = Vals[i];
                if (v.equals("null")) {
                    sval.append(" " + ",,,");
                } else {
                    sval.append(v + ",,,");
                }
            }
            //显示图片
            String imagevalue = " ";
            if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
            	List<A57> list = HBUtil.getHBSession().createSQLQuery("select * from v_js_a57 where a0000='"+a0000+"' and v_xt='"+xt+"' ").addEntity(A57.class).list();
    			A57 a57 = null;
    			if(list.size() > 0) {
    				a57 = list.get(0);
    			}
    			if (a57 != null) {
    				byte[] image = PhotosUtil.getPhotoData(a57,xt);
    				if (image != null) {
    					imagevalue = PhotosUtil.PHOTO_PATH +"qcjs/"+xt+"/"
    							+ a57.getPhotopath()
    							+ a57.getPhotoname();
    				}
    			}
            } else {
            	A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
                if (a57 != null) {
                    byte[] image = PhotosUtil.getPhotoData(a57);
                    if (image != null) {
                        imagevalue = PhotosUtil.PHOTO_PATH
                                + a57.getPhotopath()
                                + a57.getPhotoname();
                    }
                }
            }
            
            sval.append(imagevalue);

            String[] Vals1 = sval.toString().split(",,,");
            String path = new ExportAsposeBS().signatureSheet(Flds, Vals1, "qcjs.doc", "qcjs.doc", null);
            if (path.length() == 0) {
                return EventRtnType.FAILD;
            }
        //}
        
        
		//String infile =path.substring(0,path.length()-1)+".zip" ;
		//SevenZipUtil.zip7z(path, infile, null);
		//this.getPageElement("downfile").setValue(path.replace("\\", "/"));
		//this.getExecuteSG().addExecuteCode("window.reloadTree()");

        String downloadUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(downloadUUID, new String[]{path + "qcjs.doc", filename});
        this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
        this.getExecuteSG().addExecuteCode("downloadByUUID();");


        return EventRtnType.NORMAL_SUCCESS;
    }


    public String getValueFromCodeValue(String CODE_COLUMN_NAME, String code_value) {
        CommonQueryBS cq = new CommonQueryBS();
        String sql = "select code_name from code_value where CODE_COLUMN_NAME='" + CODE_COLUMN_NAME + "' and code_value='" + code_value + "'";
        try {
            HashMap<String, Object> map = cq.getMapBySQL(sql);
            if (map != null && map.size() > 0) {
                return (String) map.get("code_name");
            }
        } catch (AppException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }
    
    /**
     * 保存发文信息
     * @throws RadowException 
     */
	public void saveJs23(String js0100, HBSession sess) throws RadowException {
		String msgNum=this.getPageElement("msg").getValue();
		String rbId=this.getPageElement("rbId").getValue();
		String a0000=this.getPageElement("a0000").getValue();
		int num=3;
		String now=DateUtil.dateToString(new Date(), "yyyyMMdd");
		String js2304="";
		String js2305="";
		String js2306=now;
		String js2307="";
		String js2308="1";
		String js2309=now;
		try{
			num=Integer.parseInt(msgNum);
			CommonQueryBS cq=new CommonQueryBS();
			List<HashMap<String, Object>> list = cq.getListBySQL("select rb_name,rb_org,rb_applicant,rb_cdate from record_batch where rb_id='"+rbId+"'");
			if(list.size()>0){
				HashMap<String, Object> map = list.get(0);
				js2304=inullback(map.get("rb_name"));
				js2305=inullback(map.get("rb_org"));
				js2307=inullback(map.get("rb_applicant"));
			}
			
		}catch(Exception e){
			
		}
		Connection conn = null;
		Statement state=null;
		
		try {
			conn=sess.connection();
			conn.setAutoCommit(false);
			state = conn.createStatement();
			
			String delsql1="delete from js24 where js0100='"+js0100+"'";
			state.addBatch(delsql1);
			String delsql="delete from js23 where rb_id='"+rbId+"' and js0100='"+js0100+"'";
			state.addBatch(delsql);
			String upsql1="update js22 set js2205='0', js2206='0',js2300='',rbd001='' where js0100 in('"+js0100+"')";
			state.addBatch(upsql1);
			for(int i=1;i<=num;i++){
				String js2300=UUID.randomUUID().toString().replaceAll("-", "");
				String js2301=this.getPageElement("fwh"+i).getValue();
				String js2302=this.getPageElement("nrzw"+i).getValue();
				String js2303=this.getPageElement("nmzw"+i).getValue();
				String js2310=this.getPageElement("fwsj"+i).getValue();
				String js2302a=this.getPageElement("nrzw"+i +"a").getValue();
				String js2303a=this.getPageElement("nmzw"+i +"a").getValue();
				
				String js2302b=this.getPageElement("nrzw"+i +"b").getValue();
				String js2303b=this.getPageElement("nmzw"+i +"b").getValue();
				if(StringUtil.isEmpty(js2301)){
					continue;
				}
				String sql="insert into js23(js0100,a0000,rb_id,js2300,js2301,js2302,js2303,js2304,js2305,js2306,js2307,js2308,js2309,js2302a,js2302b,js2303a,js2303b,js2310"
						+ ") values('"+js0100+"','"+a0000+"','"+rbId+"','"+js2300+"','"+js2301+"','"+js2302+"','"+js2303+"','"+js2304+"'"
								+ ",'"+js2305+"','"+js2306+"','"+js2307+"','"+js2308+"','"+js2309+"','"+js2302a+"','"+js2302b+"','"+js2303a+"','"+js2303b+"','"+js2310+"')";
				//JS23的时候，更新JS22标记，已标记的职务点击按钮，会更新到A02
				
				
				state.addBatch(sql);
				
				String upsql="update js22 set js2205='1',js2300='"+js2300+"',rbd001='"+js2301+"' where js2200 in('"+js2302b.replace(",", "','")+"')";
				state.addBatch(upsql);
				
				String a0200s[] = js2303b.split(",");
				for (int j = 0; j < a0200s.length; j++) {
					String a0200 = a0200s[j];
					String issql="insert into js24(js2400,a0000,js2300,rbd000,rbd001,js0100,a0200,rb_id) values(sys_guid(),'"+a0000+"','"+js2300+"','','"+js2301+"','"+js0100+"','"+a0200+"','"+rbId+"')";
					state.addBatch(issql);
				}
				
			}
			state.executeBatch();
			conn.setAutoCommit(true);
			sess.flush();
		} catch (SQLException e1) {
			e1.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e) {
				
			}
		}finally{
			try{
				if(conn!=null){
					conn.close();
				}
				if(state!=null){
					state.close();
				}
			}catch(Exception e){
				
			}
		}
	}
    
    
    /**
     * 纪实信息修改保存
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("save")
    public int save() throws RadowException{

        String rbId = this.getPageElement("rbId").getValue();//批次id
        String js0100 = this.getPageElement("js0100").getValue();//任免人员id
        String a0000 = this.getPageElement("a0000").getValue();//人员id
        if (js0100 == null || "".equals(js0100)) {
            this.setMainMessage("请选择人员！");
            return EventRtnType.NORMAL_SUCCESS;
        }

        try {
            HBSession sess = HBUtil.getHBSession();

            Js01 js01 = (Js01) sess.get(Js01.class, js0100);
            if (js01 == null) {
                this.setMainMessage("人员信息不存在！");
                return EventRtnType.NORMAL_SUCCESS;
            }
            
            bs.setPm(this);
            
            saveJs23(js0100,sess);
            
            saveJS21(js0100,sess);
            //保存推荐共用信息
            savetj(js0100,sess);
            bs.saveJs01(js01, sess);
            bs.saveJs02(js0100, sess);
            bs.saveJs03(js0100, sess);
            bs.saveJs04(js0100, sess);
            //bs.saveJs05(js0100,sess);
            bs.saveJs14(js0100, sess);//组织考察表
            bs.saveJs06(js0100, sess);
            //bs.saveJs07(js0100,sess);
            bs.saveJs15(js0100, sess);
            bs.saveJs08(js0100, sess);
            bs.saveJs09(js0100, sess);
            bs.saveJs10(js0100, sess);
            bs.saveJs11(js0100, sess);
            //更新拟任免信息集
           // bs.saveA053(js01, sess);
            String a5369=this.getPageElement("a5369").getValue();
            if(!"".equals(a5369)){
            	saveA53(a0000,js01.getJs0111(),js01.getJs0117(),SysManagerUtils.getUserId(),rbId,js01.getJs0116(),a5369);
            }else{
            	updateA53(a0000,js01.getJs0111(),js01.getJs0117(),SysManagerUtils.getUserId(),rbId,js01.getJs0116(),a5369);
            }
            sess.flush();

            //bs.updateNRM(rbId, sess);
//			sess.flush();


            //上传文件
            //file02(动议)  考核与听取意见(file99)   民主推荐(file19)   组织考察(file14)  讨论决定file07   任前公示file08
            String[] files = {"file02", "file99", "file19", "file14", "file07", "file08"};
            for (int i = 0; i < files.length; i++) {
                String file = files[i];
                this.upLoadFile(file, true);
            }
            String lx = this.getPageElement("slllshqklx").getValue();
            String tabvalue = this.getPageElement("tabvalue").getValue();
            if(lx.equals("1")) {
            	//setShowImg();          //判断tab是否打勾

        		this.setNextEventName("gridcq.dogridquery");
        		if(tabvalue.equals("5")) {
        			this.setMainMessage("保存成功，待干部监督处反馈。");
        		}else {
        			this.setMainMessage("保存成功");
        		}
        		
            } else {
            	this.getExecuteSG().addExecuteCode("removeJsPerson('"+js0100+"');");
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.setMainMessage("保存失败：" + e.getCause().getMessage());
        }
        return EventRtnType.NORMAL_SUCCESS;
    }
    
    
    public void updateA53(String a0000,String a5304,String a5315,String a5399,String rbId,String a5368,String a5369) throws Exception{
    	
    	//String a5300=UUID.randomUUID().toString().replaceAll("-", "");
    	String a5321=com.utils.DateUtil.dateToString(new Date(), "yyyyMMdd");
		//HBSession sess = HBUtil.getHBSession();
	//	UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		//String cueUserid = user.getId();
		/*CommonQueryBS cq=new CommonQueryBS();
		String sql="select smt_usergroup.usergroupname,smt_user.loginname from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
		HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
		String usergroupname=(String)mapBySQL.get("usergroupname");
		String loginname=(String)mapBySQL.get("loginname");*/
    	
    	
    	HBUtil.executeUpdate("update A53 set A5304=?,A5368=?,A5321=?,A5323=? where A0000=? and A5315=? and RB_ID=?", 
				 new Object[]{a5304,a5368,a5321,a5321,a0000,a5315,rbId});
    }
    
    
    public void saveA53(String a0000,String a5304,String a5315,String a5399,String rbId,String a5368,String a5369) throws Exception{
    	/*A53 a53=new A53();
    	a53.setA0000(a0000);
    	
    	a53.setA5300(a5300);
    	a53.setA5304(a5304);//拟任职务
    	a53.setA5315(a5315);//拟免职务
    	a53.setA5317();//任免理由
    	a53.setA5319();//呈报单位
    	
    	a53.setA5321(a5321);//计算年龄时间
    	a53.setA5323(a5321);//填表时间 
*/    	String a5300=UUID.randomUUID().toString().replaceAll("-", "");
    	String a5321=com.utils.DateUtil.dateToString(new Date(), "yyyyMMdd");
		HBSession sess = HBUtil.getHBSession();
		UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		CommonQueryBS cq=new CommonQueryBS();
		String sql="select smt_usergroup.usergroupname,smt_user.loginname from smt_user right join smt_usergroup on smt_user.dept=smt_usergroup.id where smt_user.userid='"+cueUserid+"'";
		HashMap<String, Object> mapBySQL = cq.getMapBySQL(sql);
		String usergroupname=(String)mapBySQL.get("usergroupname");
		String loginname=(String)mapBySQL.get("loginname");
    	
//    	a53.setA5327(loginname);//填表人
//    	a53.setA5399(a5399);//用户ID
//    	a53.setRbId(rbId);
//    	a53.setA5365("1");//拟任状态
    	
//    	HBSession hbSession = HBUtil.getHBSession();
//    	hbSession.save(a53);
//    	hbSession.flush();
    	
    	
    	HBUtil.executeUpdate("insert into A53(A0000,A5300,A5304,A5315,A5317,A5319,A5321,A5323,A5327,A5399,RB_ID,A5365,A5368,A5369) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", 
    							 new Object[]{a0000,a5300,a5304,a5315,"组织选拔任用","无锡市委组织部",a5321,a5321,loginname,a5399,rbId,"1",a5368,a5369});
    	//HBUtil.executeUpdate("update A53 set A5365='0' where A0000='"+a0000+"' and A5300!='"+a5300+"'");
    	
    }
    
    
    @Override
    public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
        Map<String, String> map = new HashMap<String, String>();
        // 获得文件名称
        String isfile = formDataMap.get("Filename");
        // 判断是否上传了附件，没有上传则不进行文件处理
        if (isfile != null && !isfile.equals("")) {
            try {
                // 获取表单信息
                FileItem fi = fileItem.get(0);
                DecimalFormat df = new DecimalFormat("#.00");
                String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
                // 如果文件大于1M则显示M，小于则显示kb
                if (fi.getSize() < 1048576) {
                    fileSize = (int) fi.getSize() / 1024 + "KB";
                }
                if (fi.getSize() < 1024) {
                    fileSize = (int) fi.getSize() / 1024 + "B";
                }
                String id = saveFile(formDataMap, fi, fileSize);
                map.put("file_pk", id);
                map.put("file_name", isfile);
            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return map;
    }

    public static String disk = JSGLBS.HZBPATH;

    private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
        // 获得人员信息id
        String js0100 = formDataMap.get("js0100");
        String fileid = formDataMap.get("fileid");
        String filename = formDataMap.get("Filename");
        String rbId = formDataMap.get("rbId");//批次id
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        JsAtt st = new JsAtt();
        st.setJs0100(js0100);//人员信息
        st.setJsa00(UUID.randomUUID().toString());//主键
        st.setJsa05(SysManagerUtils.getUserId());//用户id
        st.setJsa06(sdf.format(new Date()));//上传时间
        st.setRbId(rbId);//批次id
        st.setJsa04(filename);

        String directory = "zhgbuploadfiles" + File.separator + rbId + File.separator;
        String filePath = directory + st.getJsa00();
        File f = new File(disk + directory);

        if (!f.isDirectory()) {
            f.mkdirs();
        }
        fi.write(new File(disk + filePath));
        st.setJsa07(directory);
        st.setJsa08(fileSize);
        st.setJsa02(fileid.replace("file", "JS"));//环节id
        HBUtil.getHBSession().save(st);
        HBUtil.getHBSession().flush();

        return st.getJsa00();
    }

    @Override
    public String deleteFile(String id) {
        try {
            HBSession sess = HBUtil.getHBSession();
            JsAtt ja = (JsAtt) sess.get(JsAtt.class, id);
            if (ja == null) {
                return null;//删除失败
            }
            String directory = disk + ja.getJsa07();
            File f = new File(directory + id);
            if (f.isFile()) {
                f.delete();
            }
            sess.delete(ja);
            sess.flush();

            return id;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 显示职数预审记录
     */
    @PageEvent("TableData")
    public int TableData(String str) throws RadowException, Exception {
        String rbid = this.getPageElement("rbId").getValue();
        HBSession session = null;
        Statement stmt = null;
        ResultSet rs = null;
        session = HBUtil.getHBSession();
        stmt = session.connection().createStatement();
        if (str.equals("file12")) {
            rs = stmt.executeQuery("select j.*,b.b0101 from js12 j,b01 b where j.js1201=b.b0111 and rb_id = '" + rbid + "'");
            int i = 1;
            while (rs.next()) {
                String js1201 = rs.getString("js1201");
                String b0101 = rs.getString("b0101");
                String js1202 = rs.getString("js1202");
                String js1203 = rs.getString("js1203");
                String js1204 = rs.getString("js1204");
                String js1205 = rs.getString("js1205");
                String js1206 = rs.getString("js1206");
                String js1207 = rs.getString("js1207");
                String js1208 = rs.getString("js1208");
                String js1209 = rs.getString("js1209");
                String js1210 = rs.getString("js1210");
                String js1211 = rs.getString("js1211");
                String js1212 = rs.getString("js1212");
                String js1213 = rs.getString("js1213");
                String js1214 = rs.getString("js1214");
                this.getPageElement("js1201_" + i).setValue(js1201);
                this.getPageElement("js1201_" + i + "_combo").setValue(b0101);
                this.getPageElement("js1202_" + i).setValue(js1202);
                this.getPageElement("js1203_" + i).setValue(js1203);
                this.getPageElement("js1204_" + i).setValue(js1204);
                this.getPageElement("js1205_" + i).setValue(js1205);
                this.getPageElement("js1206_" + i).setValue(js1206);
                this.getPageElement("js1207_" + i).setValue(js1207);
                this.getPageElement("js1208_" + i).setValue(js1208);
                this.getPageElement("js1209_" + i).setValue(js1209);
                this.getPageElement("js1210_" + i).setValue(js1210);
                this.getPageElement("js1211_" + i).setValue(js1211);
                this.getPageElement("js1212_" + i).setValue(js1212);
                this.getPageElement("js1213_" + i).setValue(js1213);
                this.getPageElement("js1214").setValue(js1214);
                //如果js1214填写信息，说明干部监督处已经进行审批，则前段就不能再修改
                if(js1214!=null&&!"".equals(js1214)){
                	this.getExecuteSG().addExecuteCode("controlColor('"+i+"');");
                }
                i++;
            }
            if (i > 1) {
                i = i - 1;//有数据则显示总的数据条数
            } else {
            	String sb_sql = "select js0123 from js01 where rb_id='"+rbid+"' and js0122='1'";
            	/*List<Object> list = session.createSQLQuery(sql).list();
            	StringBuffer sb = new StringBuffer();
            	for (int j = 0; j < list.size(); j++) {
            		Object obj = list.get(j);
            		if(obj!=null && !obj.toString().equals("")) {
            			sb.append(obj+",");
            		}
				}
            	String sb_ = sb.length()>0 ? sb.substring(0,sb.length()-1) : "";
            	sb_ = sb_.replace(",", "','");*/
            	//String b0111_sql = "select b0111 from b01 where b0111 in (select a0201b from a02 where a0200 in ('"+sb_+"')) or b0111 in (select js2202 from js22 t join js01 j on t.js0100=j.js0100 where j.rb_id='"+rbid+"')";
            	String b0111_sql = "select b0111 from b01 where b0111 in (select a0201b from a02 where a0200 in ("+sb_sql+")) or b0111 in (select js2202 from js22 t join js01 j on t.js0100=j.js0100 where j.rb_id='"+rbid+"')";
            	//System.out.println(b0111_sql);
            	List<Object[]> list_b01 = session.createSQLQuery(b0111_sql).list();
            	if(list_b01!=null && list_b01.size()>0) {
            		Map<String,int[]> map = new LinkedHashMap<String, int[]>();
            		//核定数目
                	//String hd = "select b0111,sum(case when a0219='1' then nvl(gwnum,0) end) hdld,sum(case when a0219 is null or a0219='0' then nvl(gwnum,0) end) hdfld from Jggwconf where b0111 in ("+b0111_sql+") group by b0111 ";
                	String hd="select b0111,sum(case when a0219='1' then nvl(gwnum,0) end) hdld,sum(case when a0219 is null or a0219='0' then nvl(gwnum,0) end) hdfld from Jggwconf where b0111 in ("+b0111_sql+") group by b0111 ";
            		List<Object[]> list_hd = session.createSQLQuery(hd).list();
                	//现配数目
                	//String xp = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0201b in ("+b0111_sql+")  group by a0201b ";
                	String xp="select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1  end) hdfld from (select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1'  and a0255 = '1' and a0201b in ("+b0111_sql+"))  group by a0201b";
                	List<Object[]> list_xp = session.createSQLQuery(xp).list();
                	//拟免数目
                	//String nm = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0200 in ("+sb_sql+")  group by a0201b ";
                	String nm="select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1 end) hdfld from ( select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1' and a0255 = '1'and a0200 in ("+sb_sql+"))  group by a0201b";
                	List<Object[]> list_nm = session.createSQLQuery(nm).list();
                	//拟配数目
                	//String np = "select b0111,sum(case when a0219='1' then 1 end) hdld,sum(case when a0219 is null or a0219='0' then 1 end) hdfld from Jggwconf a,js22 b where a.gwcode=b.js2204 and iscount='1' and a.b0111 in ("+b0111_sql+") and b.js0100 in (select j.js0100 from js01 j where j.rb_id='"+rbid+"')  group by b0111 ";
                	String np = "select js2202, sum(case when a0219 = '1' then  1 end) hdld,  sum(case when a0219 is null or a0219 = '0' then "
                			+ "1  end) hdfld from (select a01.a0101, js22.js2202, js22.js2204 from a01, js01 j, js22 where a01.a0000 = j.a0000 and j.js0100 = js22.js0100 "
                			+ "and j.rb_id = '"+rbid+"') a left outer join JGGWCONF t on a.js2204 = t.gwcode and a.js2202 = t.b0111 and t.iscount = '1' group by js2202";
                	List<Object[]> list_np = session.createSQLQuery(np).list();
                	
                	for (Object[] obj : list_hd) {
						String b0111 = obj[0].toString();
						int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
						int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
						map.put(b0111, new int[] {ld,fld,0,0, 0,0,0,0, 0,0});
					}
                	for (Object[] obj : list_xp) {
						String b0111 = obj[0].toString();
						int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
						int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
						if(map.containsKey(b0111)) {
							int[] arr = map.get(b0111);
							arr[2] = ld;
							arr[3] = fld;
							map.put(b0111, arr);
						} else {
							map.put(b0111, new int[] {0,0,ld,fld, 0,0,0,0, 0,0});
						}
					}
                	for (Object[] obj : list_nm) {
						String b0111 = obj[0].toString();
						int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
						int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
						if(map.containsKey(b0111)) {
							int[] arr = map.get(b0111);
							arr[4] = ld;
							arr[5] = fld;
							map.put(b0111, arr);
						} else {
							map.put(b0111, new int[] {0,0,0,0, ld,fld,0,0, 0,0});
						}
					}
                	for (Object[] obj : list_np) {
						String b0111 = obj[0].toString();
						int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
						int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
						if(map.containsKey(b0111)) {
							int[] arr = map.get(b0111);
							arr[6] = ld;
							arr[7] = fld;
							map.put(b0111, arr);
						} else {
							map.put(b0111, new int[] {0,0,0,0, 0,0,ld,fld, ld>0?1:0,fld>0?1:0});
						}
					}
                	
                	
                    Iterator it = map.keySet().iterator();
                    
                    while(it.hasNext()){
                        String key = (String) it.next();
                        int[] arr  = map.get(key);
                        B01 b01 = (B01) session.get(B01.class, key);
                        this.getPageElement("js1201_" + i).setValue(key);
                        this.getPageElement("js1201_" + i + "_combo").setValue(b01.getB0101());
                        //this.getPageElement("js1202_" + i).setValue(js1202);
                        this.getPageElement("js1203_" + i).setValue(arr[0]+"");
                        this.getPageElement("js1204_" + i).setValue(arr[1]+"");
                        this.getPageElement("js1205_" + i).setValue(arr[2]+"");
                        this.getPageElement("js1206_" + i).setValue(arr[3]+"");
                        this.getPageElement("js1207_" + i).setValue(arr[4]+"");
                        this.getPageElement("js1208_" + i).setValue(arr[5]+"");
                        this.getPageElement("js1209_" + i).setValue(arr[6]+"");
                        this.getPageElement("js1210_" + i).setValue(arr[7]+"");
                        arr[8] = arr[0]>=arr[2]-arr[4]+arr[6]? 0 :1;
    					arr[9] = arr[1]>=arr[3]-arr[5]+arr[7]? 0 :1;
                        this.getPageElement("js1211_" + i).setValue(arr[8]+"");
                        this.getPageElement("js1212_" + i).setValue(arr[9]+"");
                        this.getExecuteSG().addExecuteCode("$('#selectjs1211_"+i+"').find(\"option[value='"+arr[8]+"']\").attr(\"selected\",true);"
                        		+ "$('#selectjs1212_"+i+"').find(\"option[value='"+arr[9]+"']\").attr(\"selected\",true);");
                        
                        if(i == 20) {
                        	break;
                        }
                        i++;
                    }
            	} else {
            		i = 5;//没有数据则显示5条空的数据
            	}
            	
            }
            this.getPageElement("block").setValue(i + "");
            this.getExecuteSG().addExecuteCode("showArow();");
        } else {
            setJs13Value();
            setJs99Value();
            
            this.getExecuteSG().addExecuteCode("isHiddenQGYS();");
        }

        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 显示职数预审记录
     */
    @PageEvent("TableDataR")
    public int TableDataR(String str) throws RadowException, Exception {
        String rbid = this.getPageElement("rbId").getValue();
        HBSession session = null;
        Statement stmt = null;
        ResultSet rs = null;
        session = HBUtil.getHBSession();
        stmt = session.connection().createStatement();

        rs = stmt.executeQuery("select j.*,b.b0101 from js12 j,b01 b where j.js1201=b.b0111 and rb_id = '" + rbid + "'");
        int i = 1;
        while (rs.next()) {
            String js1201 = rs.getString("js1201");
            String b0101 = rs.getString("b0101");
            String js1202 = rs.getString("js1202");
            String js1203 = rs.getString("js1203");
            String js1204 = rs.getString("js1204");
            String js1205 = rs.getString("js1205");
            String js1206 = rs.getString("js1206");
            String js1207 = rs.getString("js1207");
            String js1208 = rs.getString("js1208");
            String js1209 = rs.getString("js1209");
            String js1210 = rs.getString("js1210");
            String js1211 = rs.getString("js1211");
            String js1212 = rs.getString("js1212");
            String js1213 = rs.getString("js1213");
            String js1214 = rs.getString("js1214");
            this.getPageElement("js1201_" + i).setValue(js1201);
            this.getPageElement("js1201_" + i + "_combo").setValue(b0101);
            this.getPageElement("js1202_" + i).setValue(js1202);
            this.getPageElement("js1203_" + i).setValue(js1203);
            this.getPageElement("js1204_" + i).setValue(js1204);
            this.getPageElement("js1205_" + i).setValue(js1205);
            this.getPageElement("js1206_" + i).setValue(js1206);
            this.getPageElement("js1207_" + i).setValue(js1207);
            this.getPageElement("js1208_" + i).setValue(js1208);
            this.getPageElement("js1209_" + i).setValue(js1209);
            this.getPageElement("js1210_" + i).setValue(js1210);
            this.getPageElement("js1211_" + i).setValue(js1211);
            this.getPageElement("js1212_" + i).setValue(js1212);
            this.getPageElement("js1213_" + i).setValue(js1213);
            this.getPageElement("js1214").setValue(js1214);
            i++;
        }
        
        for (int j = i; j <= 20; j++) {
        	this.getPageElement("js1201_" + i).setValue("");
            this.getPageElement("js1201_" + i + "_combo").setValue("");
            this.getPageElement("js1202_" + i).setValue("");
            this.getPageElement("js1203_" + i).setValue("");
            this.getPageElement("js1204_" + i).setValue("");
            this.getPageElement("js1205_" + i).setValue("");
            this.getPageElement("js1206_" + i).setValue("");
            this.getPageElement("js1207_" + i).setValue("");
            this.getPageElement("js1208_" + i).setValue("");
            this.getPageElement("js1209_" + i).setValue("");
            this.getPageElement("js1210_" + i).setValue("");
            this.getPageElement("js1211_" + i).setValue("");
            this.getPageElement("js1212_" + i).setValue("");
            this.getPageElement("js1213_" + i).setValue("");
            this.getPageElement("js1214").setValue("");
		}
        
        if (i > 1) {
            i = i - 1;//有数据则显示总的数据条数
        } else {
        	String sql = "select js0123 from js01 where rb_id='"+rbid+"' and js0122='1'";
        	/*List<Object> list = session.createSQLQuery(sql).list();
        	StringBuffer sb = new StringBuffer();
        	for (int j = 0; j < list.size(); j++) {
        		Object obj = list.get(0);
        		if(obj!=null && !obj.toString().equals("")) {
        			sb.append(obj+",");
        		}
			}
        	String sb_ = sb.length()>0 ? sb.substring(0,sb.length()-1) : "";
        	sb_ = sb_.replace(",", "','");*/
        	//String b0111_sql = "select b0111 from b01 where b0111 in (select a0201b from a02 where a0200 in ('"+sb_+"')) or b0111 in (select js2202 from js22 t join js01 j on t.js0100=j.js0100 where j.rb_id='"+rbid+"')";
        	String b0111_sql = "select b0111 from b01 where b0111 in (select a0201b from a02 where a0200 in ("+sql+")) or b0111 in (select js2202 from js22 t join js01 j on t.js0100=j.js0100 where j.rb_id='"+rbid+"')";
        	//System.out.println(b0111_sql);
        	List<Object[]> list_b01 = session.createSQLQuery(b0111_sql).list();
        	if(list_b01!=null && list_b01.size()>0) {
        		Map<String,int[]> map = new LinkedHashMap<String, int[]>();
        		//核定数目
            	String hd = "select b0111,sum(case when a0219='1' then nvl(gwnum,0) end) hdld,sum(case when a0219 is null or a0219='0' then nvl(gwnum,0) end) hdfld from Jggwconf where b0111 in ("+b0111_sql+") group by b0111 ";
            	List<Object[]> list_hd = session.createSQLQuery(hd).list();
            	//现配数目
            	//String xp = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0201b in ("+b0111_sql+")  group by a0201b ";
            	String xp="select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1  end) hdfld from (select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1'  and a0255 = '1' and a0201b in ("+b0111_sql+"))  group by a0201b";
            	
            	List<Object[]> list_xp = session.createSQLQuery(xp).list();
            	//拟免数目
            	//String nm = "select a0201b,sum(case when b.a0219='1' then 1 end) hdld,sum(case when b.a0219 is null or b.a0219='0' then 1 end) hdfld from a01 a join a02 b on a.a0000=b.a0000 left outer join Jggwconf f on b.a0201b=f.b0111 and b.a0215a_c=f.gwcode where f.iscount='1' and a0255='1' and a0200 in ('"+sb_+"')  group by a0201b ";
            	String nm="select a0201b,sum(case when a0219 = '1' then 1 end) hdld,sum(case when a0219 is null or a0219 = '2' then 1 end) hdfld from ( select distinct a0101,b.a0219,b.a0201b from a01 a join a02 b on a.a0000 = b.a0000 left outer join Jggwconf f on b.a0201b = f.b0111  /*and b.a0215a_c = f.gwcode*/ where f.iscount = '1' and a0255 = '1'and a0200 in ("+sql+"))  group by a0201b";
            	List<Object[]> list_nm = session.createSQLQuery(nm).list();
            	//拟免数目
            	//String np = "select b0111,sum(case when a0219='1' then 1 end) hdld,sum(case when a0219 is null or a0219='0' then 1 end) hdfld from Jggwconf a,js22 b where a.gwcode=b.js2204 and iscount='1' and a.b0111 in ("+b0111_sql+") and b.js0100 in (select j.js0100 from js01 j where j.rb_id='"+rbid+"')  group by b0111 ";
            	String np = "select js2202, sum(case when a0219 = '1' then  1 end) hdld,  sum(case when a0219 is null or a0219 = '0' then "
            			+ "1  end) hdfld from (select a01.a0101, js22.js2202, js22.js2204 from a01, js01 j, js22 where a01.a0000 = j.a0000 and j.js0100 = js22.js0100 "
            			+ "and j.rb_id = '"+rbid+"') a left outer join JGGWCONF t on a.js2204 = t.gwcode and a.js2202 = t.b0111 and t.iscount = '1' group by js2202";
            	List<Object[]> list_np = session.createSQLQuery(np).list();
            	
            	for (Object[] obj : list_hd) {
					String b0111 = obj[0].toString();
					int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					map.put(b0111, new int[] {ld,fld,0,0, 0,0,0,0, 0,0});
				}
            	for (Object[] obj : list_xp) {
					String b0111 = obj[0].toString();
					int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					if(map.containsKey(b0111)) {
						int[] arr = map.get(b0111);
						arr[2] = ld;
						arr[3] = fld;
						map.put(b0111, arr);
					} else {
						map.put(b0111, new int[] {0,0,ld,fld, 0,0,0,0, 0,0});
					}
				}
            	for (Object[] obj : list_nm) {
					String b0111 = obj[0].toString();
					int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					if(map.containsKey(b0111)) {
						int[] arr = map.get(b0111);
						arr[4] = ld;
						arr[5] = fld;
						map.put(b0111, arr);
					} else {
						map.put(b0111, new int[] {0,0,0,0, ld,fld,0,0, 0,0});
					}
				}
            	for (Object[] obj : list_np) {
					String b0111 = obj[0].toString();
					int ld = obj[1]!=null? ((BigDecimal)obj[1]).intValue() :0;
					int fld = obj[2]!=null? ((BigDecimal)obj[2]).intValue() :0;
					if(map.containsKey(b0111)) {
						int[] arr = map.get(b0111);
						arr[6] = ld;
						arr[7] = fld;
						map.put(b0111, arr);
					} else {
						map.put(b0111, new int[] {0,0,0,0, 0,0,ld,fld, ld>0?1:0,fld>0?1:0});
					}
				}
            	
            	
                Iterator it = map.keySet().iterator();
                
                while(it.hasNext()){
                    String key = (String) it.next();
                    int[] arr  = map.get(key);
                    B01 b01 = (B01) session.get(B01.class, key);
                    this.getPageElement("js1201_" + i).setValue(key);
                    this.getPageElement("js1201_" + i + "_combo").setValue(b01.getB0101());
                    //this.getPageElement("js1202_" + i).setValue(js1202);
                    this.getPageElement("js1203_" + i).setValue(arr[0]+"");
                    this.getPageElement("js1204_" + i).setValue(arr[1]+"");
                    this.getPageElement("js1205_" + i).setValue(arr[2]+"");
                    this.getPageElement("js1206_" + i).setValue(arr[3]+"");
                    this.getPageElement("js1207_" + i).setValue(arr[4]+"");
                    this.getPageElement("js1208_" + i).setValue(arr[5]+"");
                    this.getPageElement("js1209_" + i).setValue(arr[6]+"");
                    this.getPageElement("js1210_" + i).setValue(arr[7]+"");
                    arr[8] = arr[0]>=arr[2]-arr[4]+arr[6]? 0 :1;
					arr[9] = arr[1]>=arr[3]-arr[5]+arr[7]? 0 :1;
                    this.getPageElement("js1211_" + i).setValue(arr[8]+"");
                    this.getPageElement("js1212_" + i).setValue(arr[9]+"");
                    this.getExecuteSG().addExecuteCode("$('#selectjs1211_"+i+"').find(\"option[value='"+arr[8]+"']\").attr(\"selected\",true);"
                    		+ "$('#selectjs1212_"+i+"').find(\"option[value='"+arr[9]+"']\").attr(\"selected\",true);");
                    
                    if(i == 20) {
                    	break;
                    }
                    i++;
                }
        	} else {
        		i = 5;//没有数据则显示5条空的数据
        	}
        	
        }
        this.getPageElement("block").setValue(i + "");
        this.getExecuteSG().addExecuteCode("showArow();");
    

        return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 暂缓人员
     * @return
     */
    @PageEvent("setDelete")
    public int setDelete(String js0100){
    	HBSession sess = HBUtil.getHBSession();
    	if(js0100.contains(",")){
    		String[] js0100array = js0100.split(",");
    		for(int i=0;i<js0100array.length;i++){
    			String js0100one = js0100array[i];
    			Js01 js01=(Js01)sess.get(Js01.class, js0100one);
    	    	if(js01 == null){
    	    		this.setMainMessage("无人员信息!");
    	    		return EventRtnType.NORMAL_SUCCESS;
    	    	}
    	    	js01.setJs0120("2");
    	    	sess.update(js01);
    	    	sess.flush();
    		}
    	}else if(!StringUtil.isEmpty(js0100)){
			Js01 js01=(Js01)sess.get(Js01.class, js0100);
	    	if(js01 == null){
	    		this.setMainMessage("无人员信息!");
	    		return EventRtnType.NORMAL_SUCCESS;
	    	}
	    	js01.setJs0120("2");
	    	sess.update(js01);
	    	sess.flush();
    	}
    			
    	this.setMainMessage("人员暂缓成功!");
    	this.setNextEventName("gridcq.dogridquery");
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    /**
     * 删除单个人员记录
     *
     * @param js0100
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("allDelete")
    public int allDelete(String js0100) throws RadowException, AppException {
    		
        try {
        	if(js0100.contains(",")){
        		String[] js0100array = js0100.split(",");
        		for(int i=0;i<js0100array.length;i++){
        			HBSession sess = HBUtil.getHBSession();
                    bs.setPm(this);
                    String onejs0100=js0100array[i];
                    
                    bs.deletePersonInfo(onejs0100, sess);
                    
                    List<JsAtt> jsattList = sess.createQuery("from JsAtt where js0100='" + onejs0100 + "'").list();
                    for (JsAtt ja : jsattList) {
                        String directory = disk + ja.getJsa07();
                        File f = new File(directory + ja.getJsa00());
                        if (f.isFile()) {
                            f.delete();
                        }
                        sess.delete(ja);

                    }
                    
                    //删除明细
                    String sql = "delete from YJMX where JS0100 = :js0100";
                    SQLQuery query = sess.createSQLQuery(sql);
                    query.setString("js0100", onejs0100);
                    query.executeUpdate();
                    
                    sess.flush();
                    this.getExecuteSG().addExecuteCode("deleteGridJsonStoreRecord('" + onejs0100 + "');");
                    
        		}
        	}else if(!StringUtil.isEmpty(js0100)){
        		HBSession sess = HBUtil.getHBSession();
                bs.setPm(this);
                bs.deletePersonInfo(js0100, sess);

                List<JsAtt> jsattList = sess.createQuery("from JsAtt where js0100='" + js0100 + "'").list();
                for (JsAtt ja : jsattList) {
                    String directory = disk + ja.getJsa07();
                    File f = new File(directory + ja.getJsa00());
                    if (f.isFile()) {
                        f.delete();
                    }
                    sess.delete(ja);

                }
                
                //删除明细
                String sql = "delete from YJMX where JS0100 = :js0100";
                SQLQuery query = sess.createSQLQuery(sql);
                query.setString("js0100", js0100);
                query.executeUpdate();
                
                sess.flush();
                this.getExecuteSG().addExecuteCode("deleteGridJsonStoreRecord('" + js0100 + "');");
        	}
        	
            this.setNextEventName("gridcq.dogridquery");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return EventRtnType.NORMAL_SUCCESS;
        }
        this.toastmessage("人员已删除。");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 更新拟任免信息集
     *
     * @param a
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("updateNRM")
    public int updateNRM(String a) throws RadowException, AppException {
        String rbId = this.getPageElement("rbId").getValue();
        try {
            HBSession sess = HBUtil.getHBSession();
            bs.setPm(this);
            bs.updateNRM(rbId, sess);

            sess.flush();
            this.getExecuteSG().addExecuteCode("Ext.getCmp('updateNRM').addClass('bh');");
            this.setNextEventName("gridcq.dogridquery");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return EventRtnType.NORMAL_SUCCESS;
        }
        this.toastmessage("基础信息已更新。");
        return EventRtnType.NORMAL_SUCCESS;
    }

    /**
     * 删除单个人员环节记录
     *
     * @param js0100
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("hjDelete")
    public int hjDelete(String js0100) throws RadowException, AppException {
        String cur_hj = this.getPageElement("cur_hj").getValue();
        String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();
        cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
        RMHJ.HjDelSqlMap sm = RMHJ.getHjDelSqlMap(cur_hj, cur_hj_4);
        try {
            if (!"".equals(sm.mainMessage)) {
                this.setMainMessage(sm.mainMessage);
                return EventRtnType.NORMAL_SUCCESS;
            }
            HBUtil.executeUpdate("delete from js_hj where js0100 =? and js_type like ?||'%'", new Object[]{js0100, cur_hj});
            //若在其他环节中不存在该人员了，则删除js01
            List list = HBUtil.getHBSession().createSQLQuery("select 1 from js_hj where js0100 =?").setString(0, js0100).list();
            if (list.size() == 0) {
                allDelete(js0100);
            }
            if (!"".equals(sm.javascript)) {
                this.getExecuteSG().addExecuteCode(sm.javascript);
            }
            this.toastmessage("人员已移除。");
            this.setNextEventName("gridcq.dogridquery");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return EventRtnType.NORMAL_SUCCESS;
    }

    
    
    
    /**
     * 列表调配类别保存
     *
     * @param str
     * @return
     * @throws RadowException
     * @throws AppException
     */
    @PageEvent("savedata")
    public int savedata(String str) throws RadowException, AppException {
        String rbId = this.getPageElement("rbId").getValue();
        String[] s = str.split("@@@");
        String js0100;
        String dc003;
        if (s.length == 1) {
            js0100 = s[0];
            dc003 = "";
        } else {
            js0100 = s[0];
            dc003 = s[1];
        }

        String cur_hj = this.getPageElement("cur_hj").getValue();
        String dc005 = this.getPageElement("dc005").getValue();

        RMHJ.SavedSqlMap sm = RMHJ.getSavedSqlMap(cur_hj, dc005);

        if (dc003 == null || "".equals(dc003)) {
            String sql1 = "update " + sm.table + " set " + sm.field + " = null where  js0100 = '" + js0100 + "'" + sm.where;
            HBUtil.executeUpdate(sql1);
            this.toastmessage("已保存。");
            return 0;
        } else {
            String sql = "select dc001 from deploy_classify where dc001 = '" + dc003 + "' and rb_id = '" + rbId + "'";

            CommQuery cq = new CommQuery();
            List<HashMap<String, Object>> list = cq.getListBySQL(sql);
            if (list.size() == 0) {

                this.setMainMessage("请选择正确的类别！！！");
                return 0;
            } else {
                HashMap<String, Object> map = list.get(0);
                String dc001 = (String) map.get("dc001");
                String sql1 = "update " + sm.table + " set " + sm.field + " = '" + dc001 + "' where  js0100 = '" + js0100 + "'" + sm.where;
                HBUtil.executeUpdate(sql1);
            }
            this.toastmessage("已保存。");
            return 0;
        }

    }


    /**
     * 信息导出
     *
     * @return
     * @throws RadowException
     */
    @PageEvent("ExpGird")
    @Transaction
    public int ExpGird(String param) throws RadowException {
        QCJSFileExportBS fileBS = new QCJSFileExportBS();
        //String rbId = this.getPageElement("rbId").getValue();
        String rbId = this.request.getParameter("rbId");//批次
        String cur_hj = this.request.getParameter("cur_hj");//环节
        String cur_hj_4 = this.request.getParameter("cur_hj_4");//讨论决定分环节
        String dc005 = this.request.getParameter("dc005");
        String buttontext = this.request.getParameter("buttontext");
        String js0100 = this.request.getParameter("js0100");//任免人员id

        String js0802 = this.request.getParameter("js0802");
        String js0803 = this.request.getParameter("js0803");
        String js0804 = this.request.getParameter("js0804");
        String js0805 = this.request.getParameter("js0805");


        this.request.setAttribute("rbId", rbId);
        this.request.setAttribute("cur_hj", cur_hj);
        this.request.setAttribute("cur_hj_4", cur_hj_4);
        this.request.setAttribute("dc005", dc005);


        String js0100s = this.request.getParameter("js0100s");
        String js0100WhereSql = "";
        if (!"".equals(js0100s)) {
            js0100WhereSql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
            js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql + "')";
            //ExcelStyleUtil.js0100WhereSql = " and js01.js0100 in('" + js0100WhereSql + "')";
            this.request.setAttribute("js0100WhereSql", js0100WhereSql);
        } else {
            //ExcelStyleUtil.js0100WhereSql = "";
            this.request.setAttribute("js0100WhereSql", "");
        }

        fileBS.setPm(this);
        fileBS.setSheetName(buttontext);
        fileBS.setOutputpath(JSGLBS.HZBPATH + "zhgboutputfiles/" + rbId + "/");
        File f = new File(fileBS.getOutputpath());

        if (f.isDirectory()) {//先清空输出目录
            JSGLBS.deleteDirectory(fileBS.getOutputpath());
        }
        String buttonid = this.request.getParameter("buttonid");
        if ("btn0".equals(buttonid)) {//干部调配建议方案
            try {
                String sql = "select m.dc003 from (" + ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId, dc005,js0100WhereSql) + ")m group by dc003";
                HBSession sess = HBUtil.getHBSession();
                List list = sess.createSQLQuery(sql).list();
                String dc003 = list.get(0).toString();
                if (list.size() > 1) {
                    this.setMainMessage("存在一种以上调配类别，无法导出！");
                    return 0;
                } else if (dc003.equals("其他")) {
                    this.setMainMessage("调配类别为其他！");
                    return 0;
                } else {
                    fileBS.exportGBDYJY(dc003);
                }
                //fileBS.exportGBTP("1");
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btnKc".equals(buttonid)) {//组织考察表
            try {
                fileBS.exportZZKCMD();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btnRmmd".equals(buttonid)) {//讨论任免干部名单
            try {
            	//判断是否能导出该表
            	HBSession sess = HBUtil.getHBSession();
            	RecordBatch rb=(RecordBatch)sess.get(RecordBatch.class, rbId);
            	//RB_APPROVE= 2   RB_LEADVIEW不等于1
            	//RB_APPROVE= 1  查js02  js0208等于2 js0211 不等于1
            	//是否需要事前报告，
            	String rbapprove=rb.getRbapprove();		//1 需要审核 2不需要审核
            	String rbleadview=rb.getRbleadview();	//1同意 2不同意
            	String sql = "";
        		/*if(!js0100WhereSql.equals("")) {
        			sql = " and js0100 in ('"+js0100WhereSql+"') ";
        		}*/
        		if (!"".equals(js0100s)) {
        			sql = js0100s.substring(0, js0100s.length() - 1).replace(",", "','");
        			sql = " and js0100 in('" + sql + "')";
        		}
            	if("2".equals(rbapprove)){	//
            		/*if(!"1".equals(rbleadview)){
            			this.setMainMessage("该批次下的人员没有通过审核，不能导出这个表!");
            			return EventRtnType.NORMAL_SUCCESS;
            		}else{
            			Js02 js02=(Js02)sess.get(Js02.class, js0100);
            			if(js02 == null) {
                			this.setMainMessage("没有讨论决定信息，不能导出这个表!");
            				return EventRtnType.NORMAL_SUCCESS;
                		}
                		String js0208=js02.getJs0208();
                		String js0211 = js02.getJs0211();
                		if("2".equals(js0208)){
                			if(!"1".equals(js0211)){
                				this.setMainMessage("该人员没有通过审核，不能导出这个表!");
                				return EventRtnType.NORMAL_SUCCESS;
                			}
                		}
            		}*/
            		BigDecimal a = (BigDecimal) sess.createSQLQuery("select count(1) from js15 where js15.js0100 in (select js01.js0100 from js01 where js01.rb_id='"+rbId
            				+"') " + sql).uniqueResult();
            		if(a.intValue()==0) {
            			this.setMainMessage("没有讨论决定信息，不能导出这个表!");
        				return EventRtnType.NORMAL_SUCCESS;
            		}
            		BigDecimal c = (BigDecimal) sess.createSQLQuery("select count(1) from js02 where rb_id='"+rbId+
            				"' and ((js0208='1' and js0211='1') or js0208='2')" + sql).uniqueResult();
            		if(c.intValue()==0) {
            			this.setMainMessage("没有通过审核人员，不能导出这个表!");
        				return EventRtnType.NORMAL_SUCCESS;
            		}
            	}else if("1".equals(rbapprove)){
            		/*Js02 js02=(Js02)sess.get(Js02.class, js0100);
            		if(js02 == null) {
            			this.setMainMessage("没有讨论决定信息，不能导出这个表!");
        				return EventRtnType.NORMAL_SUCCESS;
            		}
            		String js0208=js02.getJs0208();
            		String js0211 = js02.getJs0211();
            		if("2".equals(js0208)){
            			if(!"1".equals(js0211)){
            				this.setMainMessage("该人员没有通过审核，不能导出这个表!");
            				return EventRtnType.NORMAL_SUCCESS;
            			}
            		}*/
            		if(!"1".equals(rbleadview)){
            			this.setMainMessage("该批次有通过审核，不能导出这个表!");
            			return EventRtnType.NORMAL_SUCCESS;
            		} else {
            			BigDecimal a = (BigDecimal) sess.createSQLQuery("select count(1) from js15 where js15.js0100 in (select js01.js0100 from js01 where js01.rb_id='"+rbId
                				+"') " + sql).uniqueResult();
                		if(a.intValue()==0) {
                			this.setMainMessage("没有讨论决定信息，不能导出这个表!");
            				return EventRtnType.NORMAL_SUCCESS;
                		}
            		}
            	}
            	String tljlbxzlx = this.getParameter("tljlbxzlx");
            	fileBS.exportTLRMGBMD(rbapprove , js0100WhereSql, tljlbxzlx);
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btnBjd".equals(buttonid)) {//表决单
            try {
                fileBS.exportBjd();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btnBgd".equals(buttonid)) {//报告单
            try {
                fileBS.exportBgd();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn1".equals(buttonid)) {//动议干部名单
            try {
                fileBS.exportDYMD();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }

        } else if ("btn14".equals(buttonid)) {//干部谈话安排方案
            try {
                fileBS.exportGBTHAN();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
            //fileBS.exportGBTHAN();
        } else if ("btn4".equals(buttonid)) {//职数预审表
            try {
                fileBS.exportZSYS();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn5".equals(buttonid)) {//青干预审表
            try {
            	//fileBS.exportQGYS();
            	fileBS.exportQGYS2();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn5_1".equals(buttonid)) {//青干预审表
            try {
            	fileBS.exportQGYS3();
                //fileBS.exportQGYS();
                //fileBS.exportJYRX();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn2".equals(buttonid)) {//分管市领导的通气材料
            try {
                fileBS.exportTQCL();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn17".equals(buttonid)) {//分管市领导的通气材料（任免职）
            try {
                fileBS.exportTQCLRMZ();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn3".equals(buttonid)) {//拟推荐考察对象人选征求省纪委意见名单
            try {
                fileBS.exportTJRX("拟推荐考察对象人选征求省纪委意见名单.xls");
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn7".equals(buttonid)) {//考察对象民义情况汇总表
            fileBS.exportKCDX();
        } else if ("btn8".equals(buttonid)) {//部会干部调配建议方案
            try {
                fileBS.exportBHGBTP();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn9".equals(buttonid)) {//书记专题会干部调配建议方案
            fileBS.exportSJHGBTP();
        } else if ("btn10".equals(buttonid)) {//常委会干部调配建议方案
            fileBS.exportCWHGBTP();
        } else if ("btn11".equals(buttonid)) {//常委会表决票
            try {
                fileBS.exportPJP();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn12".equals(buttonid)) {//常委会表决票情况
            try {
                fileBS.exportPJQK();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn14".equals(buttonid)) {//干部谈话安排方案
            try {
                this.request.setAttribute("dc005", RMHJ.TAN_HUA_AN_PAI);
                fileBS.exportTHAP();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }
        } else if ("btn16".equals(buttonid)) {//公示
            try {
                //保存公示结果
                HBSession sess = HBUtil.getHBSession();
                if (js0100 == null || js0100 == "") {
                    this.setMainMessage("请选择人员！");
                    return EventRtnType.NORMAL_SUCCESS;
                }

                Js01 js01 = (Js01) sess.get(Js01.class, js0100);
                if (js01 == null) {
                    this.setMainMessage("人员信息不存在！");
                    return EventRtnType.NORMAL_SUCCESS;
                }
                //判断在JS08中存在？存在更新：不存在insert
//					Js08 js08=new Js08(js0100,"",rbId,js0802,js0803,js0804,js0805);
                Js08 queryjs08 = (Js08) sess.get(Js08.class, js0100);

                if (queryjs08 != null) {
                    String upsql = "update JS08 set rb_id='" + rbId + "',js0802='" + js0802 + "',js0803='" + js0803 + "',js0804='" + js0804 + "',js0805='" + js0805 + "' where js0100='" + js0100 + "'";
                    SQLQuery queryUp = sess.createSQLQuery(upsql);
                    queryUp.executeUpdate();
                } else {
                    HBUtil.executeUpdate("insert into JS08(js0100,rb_id,js0802,js0803,js0804,js0805)"
                                    + " values(?,?,?,?,?,?)",
                            new Object[]{js0100, rbId, js0802, js0803, js0804, js0805});
                }
                sess.flush();
                fileBS.exportGS2();
            } catch (Exception e) {
                this.setMainMessage(e.getMessage());
                e.printStackTrace();
            }

        }
        //ExcelStyleUtil.js0100WhereSql = "";

        List<String> fns = fileBS.getOutputFileNameList();
        String downloadPath = "";
        String downloadName = "";
        if (fns.size() == 1) {//一个文件直接下载
            downloadPath = fileBS.getOutputpath() + fns.get(0);
            downloadName = fns.get(0);
        } else if (fns.size() > 1) {//压缩
            downloadPath = fileBS.getOutputpath() + fileBS.getSheetName() + ".zip";
            downloadName = fileBS.getSheetName() + ".zip";
            Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
            
        } else {
            return EventRtnType.NORMAL_SUCCESS;
        }
        String downloadUUID = UUID.randomUUID().toString();
        request.getSession().setAttribute(downloadUUID, new String[]{downloadPath, downloadName});
        this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='" + downloadUUID + "';");
        this.getExecuteSG().addExecuteCode("downloadByUUID();");
        return EventRtnType.NORMAL_SUCCESS;
    }


    @PageEvent("changeTableValue")
    public int changeTableValue() {
        getPageValue();
        return EventRtnType.NORMAL_SUCCESS;
    }

    @PageEvent("changeTableValue2")
    public int changeTableValue2() {
        getTable2PageValue();
        return EventRtnType.NORMAL_SUCCESS;
    }


    // ------------------------------- private -------------------------------//

    /**
     * return percent
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return
     */
    private BigDecimal percentCalc(BigDecimal divisor, BigDecimal dividend) {
        if (dividend.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("0");
        } else {
            return divisor.divide(dividend, 4, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal("100")).setScale(2);
        }
    }

    private void getPageValue() {
        Js13Pojo js13Pojo = new Js13Pojo();
        try {
            js13Pojo.setJS1311(super.getPageElement("r1c1").getValue());
            js13Pojo.setJS1312(super.getPageElement("r1c2").getValue());
            js13Pojo.setJS1313(super.getPageElement("r1c3").getValue());
            js13Pojo.setJS1314(super.getPageElement("r1c4").getValue());
            js13Pojo.setJS1315(addRowPersion(js13Pojo.getJS1311(), js13Pojo.getJS1312(), js13Pojo.getJS1313(), js13Pojo.getJS1314()));
            js13Pojo.setJS1316("0");
            js13Pojo.setJS1321(super.getPageElement("r2c1").getValue());
            js13Pojo.setJS1322(super.getPageElement("r2c2").getValue());
            js13Pojo.setJS1323(super.getPageElement("r2c3").getValue());
            js13Pojo.setJS1324(super.getPageElement("r2c4").getValue());
            js13Pojo.setJS1325(addRowPersion(js13Pojo.getJS1321(), js13Pojo.getJS1322(), js13Pojo.getJS1323(), js13Pojo.getJS1324()));
            js13Pojo.setJS1326(percentCalc(new BigDecimal(js13Pojo.getJS1325()), new BigDecimal(js13Pojo.getJS1315())).toString());
            js13Pojo.setJS1331(super.getPageElement("r3c1").getValue());
            js13Pojo.setJS1332(super.getPageElement("r3c2").getValue());
            js13Pojo.setJS1333(super.getPageElement("r3c3").getValue());
            js13Pojo.setJS1334(super.getPageElement("r3c4").getValue());
            js13Pojo.setJS1335(addRowPersion(js13Pojo.getJS1331(), js13Pojo.getJS1332(), js13Pojo.getJS1333(), js13Pojo.getJS1334()));
            js13Pojo.setJS1336(percentCalc(new BigDecimal(js13Pojo.getJS1335()), new BigDecimal(js13Pojo.getJS1315())).toString());
            js13Pojo.setJS1341(super.getPageElement("r4c1").getValue());
            js13Pojo.setJS1342(super.getPageElement("r4c2").getValue());
            js13Pojo.setJS1343(super.getPageElement("r4c3").getValue());
            js13Pojo.setJS1344(super.getPageElement("r4c4").getValue());
            js13Pojo.setJS1345(addRowPersion(js13Pojo.getJS1341(), js13Pojo.getJS1342(), js13Pojo.getJS1343(), js13Pojo.getJS1344()));
            js13Pojo.setJS1346("0");
            js13Pojo.setJS1351(super.getPageElement("r5c1").getValue());
            js13Pojo.setJS1352(super.getPageElement("r5c2").getValue());
            js13Pojo.setJS1353(super.getPageElement("r5c3").getValue());
            js13Pojo.setJS1354(super.getPageElement("r5c4").getValue());
            js13Pojo.setJS1355(addRowPersion(js13Pojo.getJS1351(), js13Pojo.getJS1352(), js13Pojo.getJS1353(), js13Pojo.getJS1354()));
            js13Pojo.setJS1356(percentCalc(new BigDecimal(js13Pojo.getJS1355()), new BigDecimal(js13Pojo.getJS1345())).toString());
            js13Pojo.setJS1361(super.getPageElement("r6c1").getValue());
            js13Pojo.setJS1362(super.getPageElement("r6c2").getValue());
            js13Pojo.setJS1363(super.getPageElement("r6c3").getValue());
            js13Pojo.setJS1364(super.getPageElement("r6c4").getValue());
            js13Pojo.setJS1365(addRowPersion(js13Pojo.getJS1361(), js13Pojo.getJS1362(), js13Pojo.getJS1363(), js13Pojo.getJS1364()));
            js13Pojo.setJS1366(percentCalc(new BigDecimal(js13Pojo.getJS1365()), new BigDecimal(js13Pojo.getJS1345())).toString());

            js13Pojo.setJS1395(super.getPageElement("r1c7").getValue());
            js13Pojo.setJS1396(super.getPageElement("js1396").getValue());
            js13Pojo.setJS1397(super.getPageElement("js1397").getValue());
            js13Pojo.setJS1398(super.getPageElement("js1398").getValue());

        } catch (RadowException e) {
            e.printStackTrace();
        }
        setPageValue(null, js13Pojo);
    }

    private void getTable2PageValue() {
        Js99Pojo js99Pojo = new Js99Pojo();
        try {
            js99Pojo.setJS9911(super.getPageElement("t2r1c1").getValue());
            js99Pojo.setJS9912(super.getPageElement("t2r1c2").getValue());
            js99Pojo.setJS9913(super.getPageElement("t2r1c3").getValue());
            js99Pojo.setJS9914(super.getPageElement("t2r1c4").getValue());
            js99Pojo.setJS9915(addRowPersion(js99Pojo.getJS9911(), js99Pojo.getJS9912(), js99Pojo.getJS9913(), js99Pojo.getJS9914()));
            js99Pojo.setJS9916("0");
            js99Pojo.setJS9921(super.getPageElement("t2r2c1").getValue());
            js99Pojo.setJS9922(super.getPageElement("t2r2c2").getValue());
            js99Pojo.setJS9923(super.getPageElement("t2r2c3").getValue());
            js99Pojo.setJS9924(super.getPageElement("t2r2c4").getValue());
            js99Pojo.setJS9925(addRowPersion(js99Pojo.getJS9921(), js99Pojo.getJS9922(), js99Pojo.getJS9923(), js99Pojo.getJS9924()));
            js99Pojo.setJS9926(percentCalc(new BigDecimal(js99Pojo.getJS9925()), new BigDecimal(js99Pojo.getJS9915())).toString());
            js99Pojo.setJS9931(super.getPageElement("t2r3c1").getValue());
            js99Pojo.setJS9932(super.getPageElement("t2r3c2").getValue());
            js99Pojo.setJS9933(super.getPageElement("t2r3c3").getValue());
            js99Pojo.setJS9934(super.getPageElement("t2r3c4").getValue());
            js99Pojo.setJS9935(addRowPersion(js99Pojo.getJS9931(), js99Pojo.getJS9932(), js99Pojo.getJS9933(), js99Pojo.getJS9934()));
            js99Pojo.setJS9936(percentCalc(new BigDecimal(js99Pojo.getJS9935()), new BigDecimal(js99Pojo.getJS9915())).toString());
            js99Pojo.setJS9941(super.getPageElement("t2r4c1").getValue());
            js99Pojo.setJS9942(super.getPageElement("t2r4c2").getValue());
            js99Pojo.setJS9943(super.getPageElement("t2r4c3").getValue());
            js99Pojo.setJS9944(super.getPageElement("t2r4c4").getValue());
            js99Pojo.setJS9945(addRowPersion(js99Pojo.getJS9941(), js99Pojo.getJS9942(), js99Pojo.getJS9943(), js99Pojo.getJS9944()));
            js99Pojo.setJS9946("0");
            js99Pojo.setJS9951(super.getPageElement("t2r5c1").getValue());
            js99Pojo.setJS9952(super.getPageElement("t2r5c2").getValue());
            js99Pojo.setJS9953(super.getPageElement("t2r5c3").getValue());
            js99Pojo.setJS9954(super.getPageElement("t2r5c4").getValue());
            js99Pojo.setJS9955(addRowPersion(js99Pojo.getJS9951(), js99Pojo.getJS9952(), js99Pojo.getJS9953(), js99Pojo.getJS9954()));
            js99Pojo.setJS9956(percentCalc(new BigDecimal(js99Pojo.getJS9955()), new BigDecimal(js99Pojo.getJS9945())).toString());
            js99Pojo.setJS9961(super.getPageElement("t2r6c1").getValue());
            js99Pojo.setJS9962(super.getPageElement("t2r6c2").getValue());
            js99Pojo.setJS9963(super.getPageElement("t2r6c3").getValue());
            js99Pojo.setJS9964(super.getPageElement("t2r6c4").getValue());
            js99Pojo.setJS9965(addRowPersion(js99Pojo.getJS9961(), js99Pojo.getJS9962(), js99Pojo.getJS9963(), js99Pojo.getJS9964()));
            js99Pojo.setJS9966(percentCalc(new BigDecimal(js99Pojo.getJS9965()), new BigDecimal(js99Pojo.getJS9945())).toString());

            js99Pojo.setJS9995(super.getPageElement("t2r1c7").getValue());
            js99Pojo.setJS9996(super.getPageElement("js9996").getValue());
            js99Pojo.setJS9997(super.getPageElement("js9997").getValue());
            js99Pojo.setJS9998(super.getPageElement("js9998").getValue());


        } catch (RadowException e) {
            e.printStackTrace();
        }
        setTable2PageValue(null, js99Pojo);
    }

    private void setPageValue(QgysPojo qgysPojo, Js13Pojo js13) {
        try {
            if (qgysPojo != null) {
                super.getPageElement("r1c1").setValue(qgysPojo.getJS1311().toString());
                super.getPageElement("r1c2").setValue(qgysPojo.getJS1312().toString());
                super.getPageElement("r1c3").setValue(qgysPojo.getJS1313().toString());
                super.getPageElement("r1c4").setValue(qgysPojo.getJS1314().toString());
                super.getPageElement("r1c5").setValue(qgysPojo.getJS1315().toString());
                super.getPageElement("r1c6").setValue(qgysPojo.getJS1316().toString());

                super.getPageElement("r2c1").setValue(qgysPojo.getJS1321().toString());
                super.getPageElement("r2c2").setValue(qgysPojo.getJS1322().toString());
                super.getPageElement("r2c3").setValue(qgysPojo.getJS1323().toString());
                super.getPageElement("r2c4").setValue(qgysPojo.getJS1324().toString());
                super.getPageElement("r2c5").setValue(qgysPojo.getJS1325().toString());
                super.getPageElement("r2c6").setValue(qgysPojo.getJS1326().toString());

                super.getPageElement("r3c1").setValue(qgysPojo.getJS1331().toString());
                super.getPageElement("r3c2").setValue(qgysPojo.getJS1332().toString());
                super.getPageElement("r3c3").setValue(qgysPojo.getJS1333().toString());
                super.getPageElement("r3c4").setValue(qgysPojo.getJS1334().toString());
                super.getPageElement("r3c5").setValue(qgysPojo.getJS1335().toString());
                super.getPageElement("r3c6").setValue(qgysPojo.getJS1336().toString());

                super.getPageElement("r4c1").setValue(qgysPojo.getJS1341().toString());
                super.getPageElement("r4c2").setValue(qgysPojo.getJS1342().toString());
                super.getPageElement("r4c3").setValue(qgysPojo.getJS1343().toString());
                super.getPageElement("r4c4").setValue(qgysPojo.getJS1344().toString());
                super.getPageElement("r4c5").setValue(qgysPojo.getJS1345().toString());
                super.getPageElement("r4c6").setValue(qgysPojo.getJS1346().toString());

                super.getPageElement("r5c1").setValue(qgysPojo.getJS1351().toString());
                super.getPageElement("r5c2").setValue(qgysPojo.getJS1352().toString());
                super.getPageElement("r5c3").setValue(qgysPojo.getJS1353().toString());
                super.getPageElement("r5c4").setValue(qgysPojo.getJS1354().toString());
                super.getPageElement("r5c5").setValue(qgysPojo.getJS1355().toString());
                super.getPageElement("r5c6").setValue(qgysPojo.getJS1356().toString());

                super.getPageElement("r6c1").setValue(qgysPojo.getJS1361().toString());
                super.getPageElement("r6c2").setValue(qgysPojo.getJS1362().toString());
                super.getPageElement("r6c3").setValue(qgysPojo.getJS1363().toString());
                super.getPageElement("r6c4").setValue(qgysPojo.getJS1364().toString());
                super.getPageElement("r6c5").setValue(qgysPojo.getJS1365().toString());
                super.getPageElement("r6c6").setValue(qgysPojo.getJS1366().toString());

            } else {

                super.getPageElement("r1c1").setValue(js13.getJS1311());
                super.getPageElement("r1c2").setValue(js13.getJS1312());
                super.getPageElement("r1c3").setValue(js13.getJS1313());
                super.getPageElement("r1c4").setValue(js13.getJS1314());
                super.getPageElement("r1c5").setValue(js13.getJS1315());
                super.getPageElement("r1c6").setValue(js13.getJS1316());

                super.getPageElement("r2c1").setValue(js13.getJS1321());
                super.getPageElement("r2c2").setValue(js13.getJS1322());
                super.getPageElement("r2c3").setValue(js13.getJS1323());
                super.getPageElement("r2c4").setValue(js13.getJS1324());
                super.getPageElement("r2c5").setValue(js13.getJS1325());
                super.getPageElement("r2c6").setValue(js13.getJS1326());

                super.getPageElement("r3c1").setValue(js13.getJS1331());
                super.getPageElement("r3c2").setValue(js13.getJS1332());
                super.getPageElement("r3c3").setValue(js13.getJS1333());
                super.getPageElement("r3c4").setValue(js13.getJS1334());
                super.getPageElement("r3c5").setValue(js13.getJS1335());
                super.getPageElement("r3c6").setValue(js13.getJS1336());

                super.getPageElement("r4c1").setValue(js13.getJS1341());
                super.getPageElement("r4c2").setValue(js13.getJS1342());
                super.getPageElement("r4c3").setValue(js13.getJS1343());
                super.getPageElement("r4c4").setValue(js13.getJS1344());
                super.getPageElement("r4c5").setValue(js13.getJS1345());
                super.getPageElement("r4c6").setValue(js13.getJS1346());

                super.getPageElement("r5c1").setValue(js13.getJS1351());
                super.getPageElement("r5c2").setValue(js13.getJS1352());
                super.getPageElement("r5c3").setValue(js13.getJS1353());
                super.getPageElement("r5c4").setValue(js13.getJS1354());
                super.getPageElement("r5c5").setValue(js13.getJS1355());
                super.getPageElement("r5c6").setValue(js13.getJS1356());

                super.getPageElement("r6c1").setValue(js13.getJS1361());
                super.getPageElement("r6c2").setValue(js13.getJS1362());
                super.getPageElement("r6c3").setValue(js13.getJS1363());
                super.getPageElement("r6c4").setValue(js13.getJS1364());
                super.getPageElement("r6c5").setValue(js13.getJS1365());
                super.getPageElement("r6c6").setValue(js13.getJS1366());

                super.getPageElement("r1c7").setValue(js13.getJS1395());
                super.getPageElement("js1396").setValue(js13.getJS1396());
                super.getPageElement("js1397").setValue(js13.getJS1397());
                super.getPageElement("js1398").setValue(js13.getJS1398());

            }
        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

    private void setTable2PageValue(QgysPojo qgysPojo, Js99Pojo js99Pojo) {
        try {
            if (qgysPojo != null) {
                super.getPageElement("t2r1c1").setValue(qgysPojo.getJS1311().toString());
                super.getPageElement("t2r1c2").setValue(qgysPojo.getJS1312().toString());
                super.getPageElement("t2r1c3").setValue(qgysPojo.getJS1313().toString());
                super.getPageElement("t2r1c4").setValue(qgysPojo.getJS1314().toString());
                super.getPageElement("t2r1c5").setValue(qgysPojo.getJS1315().toString());
                super.getPageElement("t2r1c6").setValue(qgysPojo.getJS1316().toString());

                super.getPageElement("t2r2c1").setValue(qgysPojo.getJS1321().toString());
                super.getPageElement("t2r2c2").setValue(qgysPojo.getJS1322().toString());
                super.getPageElement("t2r2c3").setValue(qgysPojo.getJS1323().toString());
                super.getPageElement("t2r2c4").setValue(qgysPojo.getJS1324().toString());
                super.getPageElement("t2r2c5").setValue(qgysPojo.getJS1325().toString());
                super.getPageElement("t2r2c6").setValue(qgysPojo.getJS1326().toString());

                super.getPageElement("t2r3c1").setValue(qgysPojo.getJS1331().toString());
                super.getPageElement("t2r3c2").setValue(qgysPojo.getJS1332().toString());
                super.getPageElement("t2r3c3").setValue(qgysPojo.getJS1333().toString());
                super.getPageElement("t2r3c4").setValue(qgysPojo.getJS1334().toString());
                super.getPageElement("t2r3c5").setValue(qgysPojo.getJS1335().toString());
                super.getPageElement("t2r3c6").setValue(qgysPojo.getJS1336().toString());

                super.getPageElement("t2r4c1").setValue(qgysPojo.getJS1341().toString());
                super.getPageElement("t2r4c2").setValue(qgysPojo.getJS1342().toString());
                super.getPageElement("t2r4c3").setValue(qgysPojo.getJS1343().toString());
                super.getPageElement("t2r4c4").setValue(qgysPojo.getJS1344().toString());
                super.getPageElement("t2r4c5").setValue(qgysPojo.getJS1345().toString());
                super.getPageElement("t2r4c6").setValue(qgysPojo.getJS1346().toString());

                super.getPageElement("t2r5c1").setValue(qgysPojo.getJS1351().toString());
                super.getPageElement("t2r5c2").setValue(qgysPojo.getJS1352().toString());
                super.getPageElement("t2r5c3").setValue(qgysPojo.getJS1353().toString());
                super.getPageElement("t2r5c4").setValue(qgysPojo.getJS1354().toString());
                super.getPageElement("t2r5c5").setValue(qgysPojo.getJS1355().toString());
                super.getPageElement("t2r5c6").setValue(qgysPojo.getJS1356().toString());

                super.getPageElement("t2r6c1").setValue(qgysPojo.getJS1361().toString());
                super.getPageElement("t2r6c2").setValue(qgysPojo.getJS1362().toString());
                super.getPageElement("t2r6c3").setValue(qgysPojo.getJS1363().toString());
                super.getPageElement("t2r6c4").setValue(qgysPojo.getJS1364().toString());
                super.getPageElement("t2r6c5").setValue(qgysPojo.getJS1365().toString());
                super.getPageElement("t2r6c6").setValue(qgysPojo.getJS1366().toString());

            } else {

                super.getPageElement("t2r1c1").setValue(js99Pojo.getJS9911());
                super.getPageElement("t2r1c2").setValue(js99Pojo.getJS9912());
                super.getPageElement("t2r1c3").setValue(js99Pojo.getJS9913());
                super.getPageElement("t2r1c4").setValue(js99Pojo.getJS9914());
                super.getPageElement("t2r1c5").setValue(js99Pojo.getJS9915());
                super.getPageElement("t2r1c6").setValue(js99Pojo.getJS9916());

                super.getPageElement("t2r2c1").setValue(js99Pojo.getJS9921());
                super.getPageElement("t2r2c2").setValue(js99Pojo.getJS9922());
                super.getPageElement("t2r2c3").setValue(js99Pojo.getJS9923());
                super.getPageElement("t2r2c4").setValue(js99Pojo.getJS9924());
                super.getPageElement("t2r2c5").setValue(js99Pojo.getJS9925());
                super.getPageElement("t2r2c6").setValue(js99Pojo.getJS9926());

                super.getPageElement("t2r3c1").setValue(js99Pojo.getJS9931());
                super.getPageElement("t2r3c2").setValue(js99Pojo.getJS9932());
                super.getPageElement("t2r3c3").setValue(js99Pojo.getJS9933());
                super.getPageElement("t2r3c4").setValue(js99Pojo.getJS9934());
                super.getPageElement("t2r3c5").setValue(js99Pojo.getJS9935());
                super.getPageElement("t2r3c6").setValue(js99Pojo.getJS9936());

                super.getPageElement("t2r4c1").setValue(js99Pojo.getJS9941());
                super.getPageElement("t2r4c2").setValue(js99Pojo.getJS9942());
                super.getPageElement("t2r4c3").setValue(js99Pojo.getJS9943());
                super.getPageElement("t2r4c4").setValue(js99Pojo.getJS9944());
                super.getPageElement("t2r4c5").setValue(js99Pojo.getJS9945());
                super.getPageElement("t2r4c6").setValue(js99Pojo.getJS9946());

                super.getPageElement("t2r5c1").setValue(js99Pojo.getJS9951());
                super.getPageElement("t2r5c2").setValue(js99Pojo.getJS9952());
                super.getPageElement("t2r5c3").setValue(js99Pojo.getJS9953());
                super.getPageElement("t2r5c4").setValue(js99Pojo.getJS9954());
                super.getPageElement("t2r5c5").setValue(js99Pojo.getJS9955());
                super.getPageElement("t2r5c6").setValue(js99Pojo.getJS9956());

                super.getPageElement("t2r6c1").setValue(js99Pojo.getJS9961());
                super.getPageElement("t2r6c2").setValue(js99Pojo.getJS9962());
                super.getPageElement("t2r6c3").setValue(js99Pojo.getJS9963());
                super.getPageElement("t2r6c4").setValue(js99Pojo.getJS9964());
                super.getPageElement("t2r6c5").setValue(js99Pojo.getJS9965());
                super.getPageElement("t2r6c6").setValue(js99Pojo.getJS9966());

                super.getPageElement("t2r1c7").setValue(js99Pojo.getJS9995());
                super.getPageElement("js9996").setValue(js99Pojo.getJS9996());
                super.getPageElement("js9997").setValue(js99Pojo.getJS9997());
                super.getPageElement("js9998").setValue(js99Pojo.getJS9998());

            }
        } catch (RadowException e) {
            e.printStackTrace();
        }
    }

    private String addRowPersion(String r1, String r2, String r3, String r4) {
        return new BigDecimal(r1).add(new BigDecimal(r2)).add(new BigDecimal(r3)).add(new BigDecimal(r4)).toString();
    }

    private void setJs13Value() {
        String rbid = null;
        try {
            rbid = this.getPageElement("rbId").getValue();
        } catch (RadowException e) {
            e.printStackTrace();
        }
        HBSession session = HBUtil.getHBSession();
        List<Js13Pojo> js13Pojos = session.createSQLQuery(" select * from JS13 js13 where js13.RB_ID = :rbid ").setParameter("rbid", rbid).setResultTransformer(Transformers.aliasToBean(Js13Pojo.class)).list();

        // if js13 is null execute sql else get data
        if (js13Pojos.size() == 0) {
            String sqlQgys =
                    //现配备县处级正(副)职+主职务+(非)领导职务+在任+(非江阴至经济开发区)
                    /*" select " +
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1311 , " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1312 , " +
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1313 , " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1314 , " +
                            // 现配备40岁以上
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1321, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1322, " +
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1323, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1324, " +
                            // 现配备80后(1980-1989)
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1331, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1332, " +
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1333, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1334, " +
                            // 拟提拔
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1341, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1342, " +
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1343, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1344, " +
                            // 拟提拔40岁以上
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1351, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1352, " +
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1353, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1354, " +
                            // 拟提拔80后(1980-1989)
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1361, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1362, " +
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1363, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B not in(select b01.B0111 from B01 b01 where b01.B0121 in ('001.001.008','001.001.009','001.001.010','001.001.011','001.001.012','001.001.013','001.001.014','001.001.022','001.001.020')) then 1 else 0 end) JS1364 " +
                            " from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 left join A53 a53 on a53.A0000 = a01.A0000 right join B01 b01 on b01.b0111 = a02.A0201B ";*/
            	" select " +
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1'  then 1 else 0 end),0) JS1311 , " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1'  then 1 else 0 end),0) JS1312 , " +
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='2'  then 1 else 0 end),0) JS1313 , " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='2'  then 1 else 0 end),0) JS1314 , " +
                    // 现配备40岁以上
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1321, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1322, " +
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1323, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1324, " +
                    // 现配备80后(1980-1989)
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1331, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1332, " +
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1333, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1334 " +
                    
                    " from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 "
                   /* + "left join A53 a53 on a53.A0000 = a01.A0000"*/
                    + " right join (select b0111,(case when b0194='1' or b0194='3' then b0124 else get_B0124NS(b0111) end) b0124 from B01) b01 on b01.b0111 = a02.A0201B where 1=1"
                    + " and a02.a0279='1' and a02.a0255='1'"
                    + " and (b0124 like '1%' or b0124 like '2%')";
                    //+ " and b0124 not in ('7','71','72','73','74','75','76','8','81','82','83')";
            List<QgysPojo> qgysPojoList = session.createSQLQuery(sqlQgys).setResultTransformer(Transformers.aliasToBean(QgysPojo.class)).list();
            QgysPojo qgysPojo = qgysPojoList.get(0);
            
            //判断人员在青干预审中属于什么类别
            //拟任职务查询语句            
            String sql5 = "select * from js01 left join b01" + 
            		"    on js01.js0116 = b01.b0111 left join js22 on js01.js0100 = js22.js0100 left join jggwconf" + 
            		"    on js22.js2204 = jggwconf.gwcode" + 
            		"   and js22.js2202 = jggwconf.b0111" + 
            		" where js22.js2207 = '1' and js01.rb_id = '"+rbid+"'";
            //拟任职务查询语句            
            String sql6 = "select * from js01 left join a02" + 
            		"    on js01.js0123 = a02.a0200" + 
            		"  left join jggwconf\r\n" + 
            		"    on a02.a0215a_c = jggwconf.gwcode and a02.a0201b = jggwconf.b0111" + 
            		"  left join b01" + 
            		"    on a02.a0201b = b01.b0111" + 
            		" where js01.rb_id = '"+rbid+"'";
            
            
            //拟任职务
            //省直
            int nr1 = 0 ; int nr2 = 0 ; int nr3 = 0 ; int nr4 = 0 ;   //总人数
            int nr11 = 0 ; int nr12 = 0 ; int nr13 = 0 ; int nr14 = 0 ;//45岁以下人数
            int nr21 = 0 ; int nr22 = 0 ; int nr23 = 0 ; int nr24 = 0 ;  //80后人数       
            //市州
            int nr31 = 0 ; int nr32 = 0 ; int nr33 = 0 ; int nr34 = 0 ;   //总人数
            int nr41 = 0 ; int nr42 = 0 ; int nr43 = 0 ; int nr44 = 0 ;//45岁以下人数
            int nr51 = 0 ; int nr52 = 0 ; int nr53 = 0 ; int nr54 = 0 ;  //80后人数       
            
            //拟任职务
            //省直
            int nm1 = 0 ; int nm2 = 0 ; int nm3 = 0 ; int nm4 = 0 ;   //总人数
            int nm11 = 0 ; int nm12 = 0 ; int nm13 = 0 ; int nm14 = 0 ;//45岁以下人数
            int nm21 = 0 ; int nm22 = 0 ; int nm23 = 0 ; int nm24 = 0 ;  //80后人数       
            //市州
            int nm31 = 0 ; int nm32 = 0 ; int nm33 = 0 ; int nm34 = 0 ;   //总人数
            int nm41 = 0 ; int nm42 = 0 ; int nm43 = 0 ; int nm44 = 0 ;//45岁以下人数
            int nm51 = 0 ; int nm52 = 0 ; int nm53 = 0 ; int nm54 = 0 ;  //80后人数       
            
            CommonQueryBS cq=new CommonQueryBS();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
            String dateString = formatter.format(date);
            int year = Integer.parseInt(dateString.substring(0, 4))-45;   //为计算45岁以下人员
            String newdate = year+dateString.substring(4, 6);
            try {
            	System.out.println("sql5:"+sql5);
				List<HashMap<String, Object>> listBySQL = cq.getListBySQL(sql5);
				for(int i=0;i<listBySQL.size();i++) {
					HashMap<String, Object> map = listBySQL.get(i);
					//人数
					if("21".equals(map.get("b0124"))) {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr1 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr11 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr21 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr3 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr13 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr23 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr2 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr12 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr22 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr14 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr24 ++ ;
								}
							}
						}
					}else {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr31 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr41 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr51 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr33 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr43 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr53 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr32 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr42 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr52 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr44 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr54 ++ ;
								}
							}
						}
					}					
					System.out.println("nr1:"+nr1);
					System.out.println("nr2:"+nr2);
					System.out.println("nr3:"+nr3);
					System.out.println("nr4:"+nr4);
					System.out.println("nr11:"+nr11);
					System.out.println("nr12:"+nr12);
					System.out.println("nr13:"+nr13);
					System.out.println("nr14:"+nr14);
					System.out.println("nr21:"+nr21);
					System.out.println("nr22:"+nr22);
					System.out.println("nr23:"+nr23);
					System.out.println("nr24:"+nr24);
					
					System.out.println("nr31:"+nr31);
					System.out.println("nr32:"+nr32);
					System.out.println("nr33:"+nr33);
					System.out.println("nr34:"+nr34);
					System.out.println("nr41:"+nr41);
					System.out.println("nr42:"+nr42);
					System.out.println("nr43:"+nr43);
					System.out.println("nr44:"+nr44);
					System.out.println("nr51:"+nr51);
					System.out.println("nr52:"+nr52);
					System.out.println("nr53:"+nr53);
					System.out.println("nr54:"+nr54);
				}
				
				
				List<HashMap<String, Object>> listBySQL2 = cq.getListBySQL(sql6);
				for(int i=0;i<listBySQL.size();i++) {
					HashMap<String, Object> map = listBySQL2.get(i);
					if("21".equals(map.get("b0124"))) {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm1 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm11 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm21 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm3 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm13 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm23 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm2 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm12 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm22 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm14 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm24 ++ ;
								}
							}
						}
					}else {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm31 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm41 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm51 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm33 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm43 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm53 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm32 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm42 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm52 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm44 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm54 ++ ;
								}
							}
						}
					}
				}
				System.out.println("nm1:"+nm1);
				System.out.println("nm2:"+nm2);
				System.out.println("nm3:"+nm3);
				System.out.println("nm4:"+nm4);
				System.out.println("nm11:"+nm11);
				System.out.println("nm12:"+nm12);
				System.out.println("nm13:"+nm13);
				System.out.println("nm14:"+nm14);
				System.out.println("nm21:"+nm21);
				System.out.println("nm22:"+nm22);
				System.out.println("nm23:"+nm23);
				System.out.println("nm24:"+nm24);
				
				System.out.println("nm31:"+nm31);
				System.out.println("nm32:"+nm32);
				System.out.println("nm33:"+nm33);
				System.out.println("nm34:"+nm34);
				System.out.println("nm41:"+nm41);
				System.out.println("nm42:"+nm42);
				System.out.println("nm43:"+nm43);
				System.out.println("nm44:"+nm44);
				System.out.println("nm51:"+nm51);
				System.out.println("nm52:"+nm52);
				System.out.println("nm53:"+nm53);
				System.out.println("nm54:"+nm54);
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            
            
            String sql1 = "a0000,a0101,a0102,a0104,a0107,a0111a,a0114a,a0115a,a0117,a0128,a0134,a0140,a0141,a0144,a3921,a3927,a0160,a0163,a0165,a0184,a0187a,a0192,a0192a,a0221,a0288,a0192d,a0192c,a0196,a0197,a0195,to_char(a1701) a1701,a14z101,a15z101,a0120,a0121,a2949,a0122,a0104a,a0111,a0114,a0117a,a0128b,a0141d,a0144b,a0144c,a0148,a0148c,a0149,a0151,a0153,a0157,a0158,a0159,a015a,a0161,a0162,a0180,a0191,a0192b,a0193,a0194u,a0198,a0199,a01k01,a01k02,cbdresult,cbdw,isvalid,nl,nmzw,nrzw,orgid,qrzxl,qrzxlxx,qrzxw,qrzxwxx,rmly,status,tbr,tbrjg,userlog,xgr,zzxl,zzxlxx,zzxw,zzxwxx,a0155,age,jsnlsj,resultsortid,tbsj,xgsj,sortid,a0194,a0192e,a0192f,torgid,torder,zgxl,zgxlxx,zgxw,zgxwxx,a7865,n0150,a0131,n0152,tcsjshow,tcfsshow,zcsj,fcsj,a0190,a0189,a6506,a0188,fkbs,fkly,'1' v_XT";
            String sql2 = 
		            " select " +
		            // 拟提拔
		            " nvl(SUM(case when f.zwcode = '1A21' then 1 else 0 end),0) JS1341, " +
		            " nvl(SUM(case when f.zwcode = '1A22' then 1 else 0 end),0) JS1342, " +
		            " nvl(SUM(case when f.zwcode = '1A21' then 1 else 0 end),0) JS1343, " +
		            " nvl(SUM(case when f.zwcode = '1A22' then 1 else 0 end),0) JS1344, " +
		            // 拟提拔40岁以上
		            " nvl(SUM(case when f.zwcode = '1A21' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1351, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1352, " +
		            " nvl(SUM(case when f.zwcode = '1A21' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1353, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1354, " +
		            // 拟提拔80后(1980-1989)
		            " nvl(SUM(case when f.zwcode = '1A21' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1361, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1362, " +
		            " nvl(SUM(case when f.zwcode = '1A21' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1363, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1364 " +
		            " from (select "+sql1+" from A01 union select * from v_js_A01) a01 join js01 js01 on a01.a0000 = js01.A0000 and js01.js0122=a01.v_xt join js22 js22 on js22.js0100 = js01.js0100"
		            + " join (select b0111,(case when b0194='1' or b0194='3' then b0124 else get_B0124NS(b0111) end) b0124 from B01) b01 on b01.b0111 = js22.js2202 join jggwconf f on b01.b0111=f.b0111 and js22.js2204=f.gwcode where 1=1 "+ 
		            " and b0124 not in ('7','71','72','73','74','75','76','8','81','82','83')";
            List<QgysPojo> qgysPojoList2 = session.createSQLQuery(sql2).setResultTransformer(Transformers.aliasToBean(QgysPojo.class)).list();
		    QgysPojo qgysPojo2 = qgysPojoList2.get(0);
            
            /*qgysPojo.setJS1341(qgysPojo2.getJS1341());
            qgysPojo.setJS1342(qgysPojo2.getJS1342());
            qgysPojo.setJS1343(qgysPojo2.getJS1343());
            qgysPojo.setJS1344(qgysPojo2.getJS1344());
            qgysPojo.setJS1351(qgysPojo2.getJS1351());
            qgysPojo.setJS1352(qgysPojo2.getJS1352());
            qgysPojo.setJS1353(qgysPojo2.getJS1353());
            qgysPojo.setJS1354(qgysPojo2.getJS1354());
            qgysPojo.setJS1361(qgysPojo2.getJS1361());
            qgysPojo.setJS1362(qgysPojo2.getJS1362());
            qgysPojo.setJS1363(qgysPojo2.getJS1363());
            qgysPojo.setJS1364(qgysPojo2.getJS1364());*/
		    qgysPojo.setJS1341(qgysPojo.getJS1311().add(BigDecimal.valueOf((int)nr1)).subtract(BigDecimal.valueOf((int)nm1)));
            qgysPojo.setJS1342(qgysPojo.getJS1312().add(BigDecimal.valueOf((int)nr2)).subtract(BigDecimal.valueOf((int)nm2)));
            qgysPojo.setJS1343(qgysPojo.getJS1313().add(BigDecimal.valueOf((int)nr3)).subtract(BigDecimal.valueOf((int)nm3)));
            qgysPojo.setJS1344(qgysPojo.getJS1314().add(BigDecimal.valueOf((int)nr4)).subtract(BigDecimal.valueOf((int)nm4)));
            
            qgysPojo.setJS1351(qgysPojo.getJS1321().add(BigDecimal.valueOf((int)nr11)).subtract(BigDecimal.valueOf((int)nm11)));
            qgysPojo.setJS1352(qgysPojo.getJS1322().add(BigDecimal.valueOf((int)nr12)).subtract(BigDecimal.valueOf((int)nm12)));
            qgysPojo.setJS1353(qgysPojo.getJS1323().add(BigDecimal.valueOf((int)nr13)).subtract(BigDecimal.valueOf((int)nm13)));
            qgysPojo.setJS1354(qgysPojo.getJS1324().add(BigDecimal.valueOf((int)nr14)).subtract(BigDecimal.valueOf((int)nm14)));
            
            qgysPojo.setJS1361(qgysPojo.getJS1331().add(BigDecimal.valueOf((int)nr21)).subtract(BigDecimal.valueOf((int)nm21)));
            qgysPojo.setJS1362(qgysPojo.getJS1332().add(BigDecimal.valueOf((int)nr22)).subtract(BigDecimal.valueOf((int)nm22)));
            qgysPojo.setJS1363(qgysPojo.getJS1333().add(BigDecimal.valueOf((int)nr23)).subtract(BigDecimal.valueOf((int)nm23)));
            qgysPojo.setJS1364(qgysPojo.getJS1334().add(BigDecimal.valueOf((int)nr24)).subtract(BigDecimal.valueOf((int)nm24)));
            
            qgysPojo.setAdd1();
            qgysPojo.setAdd2();
            qgysPojo.setAdd3();
            qgysPojo.setAdd4();
            qgysPojo.setAdd5();
            qgysPojo.setAdd6();
            qgysPojo.setJS1326(percentCalc(qgysPojo.getJS1325(), qgysPojo.getJS1315()));
            qgysPojo.setJS1336(percentCalc(qgysPojo.getJS1335(), qgysPojo.getJS1315()));
            qgysPojo.setJS1356(percentCalc(qgysPojo.getJS1355(), qgysPojo.getJS1345()));
            qgysPojo.setJS1366(percentCalc(qgysPojo.getJS1365(), qgysPojo.getJS1345()));
            setPageValue(qgysPojo, null);
        } else {
            setPageValue(null, js13Pojos.get(0));
        }
    }

    private void setJs99Value() {
        String rbid = null;
        try {
            rbid = this.getPageElement("rbId").getValue();
        } catch (RadowException e) {
            e.printStackTrace();
        }
        HBSession session = HBUtil.getHBSession();
        List<Js99Pojo> js99Pojos = session.createSQLQuery(" select * from JS99 js99 where js99.RB_ID = :rbid ").setParameter("rbid", rbid).setResultTransformer(Transformers.aliasToBean(Js99Pojo.class)).list();

        // if js99 is null execute sql else get data
        if (js99Pojos.size() == 0) {
            String sqlQgys =
                    /*" select " +
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1311, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1312, " +
                            " SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1313, " +
                            " SUM(case when a01.A0221 in ( '1A22' , '1A21' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and a02.A0201B in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1314, " +
                            //现配备40岁以上
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1321, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1322, " +
                            " SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1323, " +
                            " SUM(case when a01.A0221 in ( '1A22' , '1A22' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B  in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1324, " +
                            //现配备80后(1980-1989)
                            " SUM(case when a01.A0221 = '1A21' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003' " +
                            ") then 1 else 0 end) JS1331, " +
                            " SUM(case when a01.A0221 = '1A22' and a02.a0279='1' and a02.a0219='1' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003' " +
                            ") then 1 else 0 end) JS1332, " +
                            " SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1333, " +
                            " SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0279='1' and a02.a0219='2' and a02.a0255='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1334, " +
                            //拟提拔
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and a02.A0201 in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1341, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1342, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1343, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and a02.A0201B in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1344, " +
                            //拟提拔40岁以上
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1351, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1352, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1353, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-40) >= 0 and a02.A0201B in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1354, " +
                            //拟提拔80后(1980-1989)
                            " SUM(case when a53.A5366 = '县处级正职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1361, " +
                            " SUM(case when a53.A5366 = '县处级副职' and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.001','001.001.009.001','001.001.008.003','001.001.009.003') then 1 else 0 end) JS1362, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.008.002','001.001.009.002','001.001.010.002','001.001.011.002','001.001.012.002','001.001.013.002','001.001.014.002','001.001.008.004','001.001.009.004','001.001.010.004','001.001.011.004','001.001.012.004','001.001.013.004','001.001.014.004') then 1 else 0 end) JS1363, " +
                            " SUM(case when a53.A5366 in ( '县处级正职', '县处级副职' ) and a53.a5365='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989' and a02.A0201B in('001.001.020.002','001.001.020.003','001.001.020.004','001.001.020.005','001.001.020.006','001.001.020.007','001.001.020.008','001.001.020.009','001.001.008.005','001.001.008.006','001.001.008.007','001.001.008.008','001.001.008.009','001.001.008.010','001.001.008.011','001.001.009.005','001.001.009.006','001.001.009.007','001.001.009.008','001.001.009.009','001.001.009.010','001.001.009.011','001.001.010.001','001.001.010.003','001.001.010.005','001.001.010.006','001.001.010.007','001.001.010.008','001.001.011.001','001.001.011.003','001.001.011.005','001.001.011.006','001.001.011.007','001.001.011.008','001.001.011.009','001.001.011.010','001.001.011.011','001.001.012.001','001.001.012.003','001.001.012.005','001.001.012.006','001.001.012.007','001.001.012.008','001.001.012.010','001.001.013.001','001.001.013.003','001.001.013.005','001.001.013.006','001.001.013.007','001.001.013.008','001.001.013.010','001.001.013.011','001.001.013.012','001.001.014.001','001.001.014.003','001.001.022.001','001.001.014.005','001.001.014.006','001.001.014.008','001.001.014.009','001.001.014.011','001.001.014.012','001.001.011.012','001.001.012.011','001.001.013.013','001.001.008.012','001.001.009.012','001.001.010.009') then 1 else 0 end) JS1364 " +
                            " from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 left join A53 a53 on a53.A0000 = a01.A0000 right join B01 b01 on b01.b0111 = a02.A0201B ";*/
            		" select " +
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1'  then 1 else 0 end),0) JS1311, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1'  then 1 else 0 end),0) JS1312, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0219='2'  then 1 else 0 end),0) JS1313, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A22' , '1A21' ) and a02.a0219='2'  then 1 else 0 end),0) JS1314, " +
                    //现配备40岁以上
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1321, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1322, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1323, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A22' , '1A22' ) and a02.a0219='2' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) <= 0  then 1 else 0 end),0) JS1324, " +
                    //现配备80后(1980-1989)
                    " nvl(SUM(case when a01.A0221 = '1A21' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1331, " +
                    " nvl(SUM(case when a01.A0221 = '1A22' and a02.a0219='1' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1332, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1333, " +
                    " nvl(SUM(case when a01.A0221 in ( '1A21' , '1A22' ) and a02.a0219='2' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1334 " +
                    //" from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 left join A53 a53 on a53.A0000 = a01.A0000 right join B01 b01 on b01.b0111 = a02.A0201B ";
		            " from A01 a01 left join A02 a02 on a01.a0000 = a02.A0000 right join (select b0111,(case when b0194='1' or b0194='3' then b0124 else get_B0124NS(b0111) end) b0124 from B01) b01 on b01.b0111 = a02.A0201B where 1=1"
		            + " and a02.a0279='1' and a02.a0255='1' and b0124 not like '1%' and b0124 not like '2%'";
            List<QgysPojo> qgysPojoList = session.createSQLQuery(sqlQgys).setResultTransformer(Transformers.aliasToBean(QgysPojo.class)).list();
            QgysPojo qgysPojo = qgysPojoList.get(0);
            
            
          //判断人员在青干预审中属于什么类别
            //拟任职务查询语句            
            String sql5 = "select * from js01 left join b01" + 
            		"    on js01.js0116 = b01.b0111 left join js22 on js01.js0100 = js22.js0100 left join jggwconf" + 
            		"    on js22.js2204 = jggwconf.gwcode" + 
            		"   and js22.js2202 = jggwconf.b0111" + 
            		" where js22.js2207 = '1' and js01.rb_id = '"+rbid+"'";
            //拟任职务查询语句            
            String sql6 = "select * from js01 left join a02" + 
            		"    on js01.js0123 = a02.a0200" + 
            		"  left join jggwconf\r\n" + 
            		"    on a02.a0215a_c = jggwconf.gwcode and a02.a0201b = jggwconf.b0111" + 
            		"  left join b01" + 
            		"    on a02.a0201b = b01.b0111" + 
            		" where js01.rb_id = '"+rbid+"'";
            
            
            //拟任职务
            //省直
            int nr1 = 0 ; int nr2 = 0 ; int nr3 = 0 ; int nr4 = 0 ;   //总人数
            int nr11 = 0 ; int nr12 = 0 ; int nr13 = 0 ; int nr14 = 0 ;//45岁以下人数
            int nr21 = 0 ; int nr22 = 0 ; int nr23 = 0 ; int nr24 = 0 ;  //80后人数       
            //市州
            int nr31 = 0 ; int nr32 = 0 ; int nr33 = 0 ; int nr34 = 0 ;   //总人数
            int nr41 = 0 ; int nr42 = 0 ; int nr43 = 0 ; int nr44 = 0 ;//45岁以下人数
            int nr51 = 0 ; int nr52 = 0 ; int nr53 = 0 ; int nr54 = 0 ;  //80后人数       
            
            //拟任职务
            //省直
            int nm1 = 0 ; int nm2 = 0 ; int nm3 = 0 ; int nm4 = 0 ;   //总人数
            int nm11 = 0 ; int nm12 = 0 ; int nm13 = 0 ; int nm14 = 0 ;//45岁以下人数
            int nm21 = 0 ; int nm22 = 0 ; int nm23 = 0 ; int nm24 = 0 ;  //80后人数       
            //市州
            int nm31 = 0 ; int nm32 = 0 ; int nm33 = 0 ; int nm34 = 0 ;   //总人数
            int nm41 = 0 ; int nm42 = 0 ; int nm43 = 0 ; int nm44 = 0 ;//45岁以下人数
            int nm51 = 0 ; int nm52 = 0 ; int nm53 = 0 ; int nm54 = 0 ;  //80后人数       
            
            CommonQueryBS cq=new CommonQueryBS();
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
            String dateString = formatter.format(date);
            int year = Integer.parseInt(dateString.substring(0, 4))-45;   //为计算45岁以下人员
            String newdate = year+dateString.substring(4, 6);
            try {
            	System.out.println("sql5:"+sql5);
				List<HashMap<String, Object>> listBySQL = cq.getListBySQL(sql5);
				for(int i=0;i<listBySQL.size();i++) {
					HashMap<String, Object> map = listBySQL.get(i);
					//人数
					if("21".equals(map.get("b0124"))) {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr1 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr11 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr21 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr3 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr13 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr23 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr2 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr12 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr22 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr14 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr24 ++ ;
								}
							}
						}
					}else {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr31 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr41 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr51 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr33 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr43 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr53 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nr32 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr42 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr52 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nr4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nr44 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nr54 ++ ;
								}
							}
						}
					}					
					/*System.out.println("nr1:"+nr1);
					System.out.println("nr2:"+nr2);
					System.out.println("nr3:"+nr3);
					System.out.println("nr4:"+nr4);
					System.out.println("nr11:"+nr11);
					System.out.println("nr12:"+nr12);
					System.out.println("nr13:"+nr13);
					System.out.println("nr14:"+nr14);
					System.out.println("nr21:"+nr21);
					System.out.println("nr22:"+nr22);
					System.out.println("nr23:"+nr23);
					System.out.println("nr24:"+nr24);
					
					System.out.println("nr31:"+nr31);
					System.out.println("nr32:"+nr32);
					System.out.println("nr33:"+nr33);
					System.out.println("nr34:"+nr34);
					System.out.println("nr41:"+nr41);
					System.out.println("nr42:"+nr42);
					System.out.println("nr43:"+nr43);
					System.out.println("nr44:"+nr44);
					System.out.println("nr51:"+nr51);
					System.out.println("nr52:"+nr52);
					System.out.println("nr53:"+nr53);
					System.out.println("nr54:"+nr54);*/
				}
				
				
				List<HashMap<String, Object>> listBySQL2 = cq.getListBySQL(sql6);
				for(int i=0;i<listBySQL.size();i++) {
					HashMap<String, Object> map = listBySQL2.get(i);
					if("21".equals(map.get("b0124"))) {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm1 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm11 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm21 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm3 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm13 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm23 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm2 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm12 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm22 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm14 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm24 ++ ;
								}
							}
						}
					}else {
						if("1A21".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm31 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm41 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm51 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm33 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm43 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm53 ++ ;
								}
							}						
						}else if("1A22".equals(map.get("zwcode"))) {
							if("1".equals(map.get("a0219"))) {
								nm32 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm42 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm52 ++ ;
								}
							}else if("0".equals(map.get("a0219"))) {
								nm4 ++;
								if(Integer.parseInt(newdate) < Integer.parseInt(map.get("js0103").toString().substring(0, 6))) {
									nm44 ++ ;
								}
								if(Integer.parseInt(map.get("js0103").toString().substring(0, 4))>=1980&&Integer.parseInt(map.get("js0103").toString().substring(0, 4))<=1989) {
									nm54 ++ ;
								}
							}
						}
					}
				}
				/*System.out.println("nm1:"+nm1);
				System.out.println("nm2:"+nm2);
				System.out.println("nm3:"+nm3);
				System.out.println("nm4:"+nm4);
				System.out.println("nm11:"+nm11);
				System.out.println("nm12:"+nm12);
				System.out.println("nm13:"+nm13);
				System.out.println("nm14:"+nm14);
				System.out.println("nm21:"+nm21);
				System.out.println("nm22:"+nm22);
				System.out.println("nm23:"+nm23);
				System.out.println("nm24:"+nm24);
				
				System.out.println("nm31:"+nm31);
				System.out.println("nm32:"+nm32);
				System.out.println("nm33:"+nm33);
				System.out.println("nm34:"+nm34);
				System.out.println("nm41:"+nm41);
				System.out.println("nm42:"+nm42);
				System.out.println("nm43:"+nm43);
				System.out.println("nm44:"+nm44);
				System.out.println("nm51:"+nm51);
				System.out.println("nm52:"+nm52);
				System.out.println("nm53:"+nm53);
				System.out.println("nm54:"+nm54);*/
			} catch (AppException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            String sql1 = "a0000,a0101,a0102,a0104,a0107,a0111a,a0114a,a0115a,a0117,a0128,a0134,a0140,a0141,a0144,a3921,a3927,a0160,a0163,a0165,a0184,a0187a,a0192,a0192a,a0221,a0288,a0192d,a0192c,a0196,a0197,a0195,to_char(a1701) a1701,a14z101,a15z101,a0120,a0121,a2949,a0122,a0104a,a0111,a0114,a0117a,a0128b,a0141d,a0144b,a0144c,a0148,a0148c,a0149,a0151,a0153,a0157,a0158,a0159,a015a,a0161,a0162,a0180,a0191,a0192b,a0193,a0194u,a0198,a0199,a01k01,a01k02,cbdresult,cbdw,isvalid,nl,nmzw,nrzw,orgid,qrzxl,qrzxlxx,qrzxw,qrzxwxx,rmly,status,tbr,tbrjg,userlog,xgr,zzxl,zzxlxx,zzxw,zzxwxx,a0155,age,jsnlsj,resultsortid,tbsj,xgsj,sortid,a0194,a0192e,a0192f,torgid,torder,zgxl,zgxlxx,zgxw,zgxwxx,a7865,n0150,a0131,n0152,tcsjshow,tcfsshow,zcsj,fcsj,a0190,a0189,a6506,a0188,fkbs,fkly,'1' v_XT";
            String sql2 = 
		            " select " +
		            // 拟提拔
		            " nvl(SUM(case when f.zwcode = '1A21' then 1 else 0 end),0) JS1341, " +
		            " nvl(SUM(case when f.zwcode = '1A22' then 1 else 0 end),0) JS1342, " +
		            " nvl(SUM(case when f.zwcode = '1A21' then 1 else 0 end),0) JS1343, " +
		            " nvl(SUM(case when f.zwcode = '1A22' then 1 else 0 end),0) JS1344, " +
		            // 拟提拔40岁以上
		            " nvl(SUM(case when f.zwcode = '1A21' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1351, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1352, " +
		            " nvl(SUM(case when f.zwcode = '1A21' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1353, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and sign(GET_BIRTHDAY(a01.A0107,to_char(sysdate,'yyyymmdd'))-45) >= 0  then 1 else 0 end),0) JS1354, " +
		            // 拟提拔80后(1980-1989)
		            " nvl(SUM(case when f.zwcode = '1A21' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1361, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1362, " +
		            " nvl(SUM(case when f.zwcode = '1A21' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1363, " +
		            " nvl(SUM(case when f.zwcode = '1A22' and SUBSTR(a01.A0107, 0 ,4) BETWEEN '1980' and '1989'  then 1 else 0 end),0) JS1364 " +
		            " from (select "+sql1+" from A01 union select * from v_js_A01) a01 join js01 js01 on a01.a0000 = js01.A0000 and js01.js0122=a01.v_xt join js22 js22 on js22.js0100 = js01.js0100"
		            + " join (select b0111,(case when b0194='1' or b0194='3' then b0124 else get_B0124NS(b0111) end) b0124 from B01) b01 on b01.b0111 = js22.js2202 join jggwconf f on b01.b0111=f.b0111 and js22.js2204=f.gwcode where 1=1 "+ 
		            " and b0124 in ('7','71','72','73','74','75','76','8','81','82','83')";
            List<QgysPojo> qgysPojoList2 = session.createSQLQuery(sql2).setResultTransformer(Transformers.aliasToBean(QgysPojo.class)).list();
		    QgysPojo qgysPojo2 = qgysPojoList2.get(0);
            /*qgysPojo.setJS1341(qgysPojo2.getJS1341());
            qgysPojo.setJS1342(qgysPojo2.getJS1342());
            qgysPojo.setJS1343(qgysPojo2.getJS1343());
            qgysPojo.setJS1344(qgysPojo2.getJS1344());
            qgysPojo.setJS1351(qgysPojo2.getJS1351());
            qgysPojo.setJS1352(qgysPojo2.getJS1352());
            qgysPojo.setJS1353(qgysPojo2.getJS1353());
            qgysPojo.setJS1354(qgysPojo2.getJS1354());
            qgysPojo.setJS1361(qgysPojo2.getJS1361());
            qgysPojo.setJS1362(qgysPojo2.getJS1362());
            qgysPojo.setJS1363(qgysPojo2.getJS1363());
            qgysPojo.setJS1364(qgysPojo2.getJS1364());*/
		    qgysPojo.setJS1341(qgysPojo.getJS1311().add(BigDecimal.valueOf((int)nr31)).subtract(BigDecimal.valueOf((int)nm31)));
            qgysPojo.setJS1342(qgysPojo.getJS1312().add(BigDecimal.valueOf((int)nr32)).subtract(BigDecimal.valueOf((int)nm32)));
            qgysPojo.setJS1343(qgysPojo.getJS1313().add(BigDecimal.valueOf((int)nr33)).subtract(BigDecimal.valueOf((int)nm33)));
            qgysPojo.setJS1344(qgysPojo.getJS1314().add(BigDecimal.valueOf((int)nr34)).subtract(BigDecimal.valueOf((int)nm34)));
            qgysPojo.setJS1351(qgysPojo.getJS1321().add(BigDecimal.valueOf((int)nr41)).subtract(BigDecimal.valueOf((int)nm41)));
            qgysPojo.setJS1352(qgysPojo.getJS1322().add(BigDecimal.valueOf((int)nr42)).subtract(BigDecimal.valueOf((int)nm42)));
            qgysPojo.setJS1353(qgysPojo.getJS1323().add(BigDecimal.valueOf((int)nr43)).subtract(BigDecimal.valueOf((int)nm43)));
            qgysPojo.setJS1354(qgysPojo.getJS1324().add(BigDecimal.valueOf((int)nr44)).subtract(BigDecimal.valueOf((int)nm44)));
            qgysPojo.setJS1361(qgysPojo.getJS1331().add(BigDecimal.valueOf((int)nr51)).subtract(BigDecimal.valueOf((int)nm51)));
            qgysPojo.setJS1362(qgysPojo.getJS1332().add(BigDecimal.valueOf((int)nr52)).subtract(BigDecimal.valueOf((int)nm52)));
            qgysPojo.setJS1363(qgysPojo.getJS1333().add(BigDecimal.valueOf((int)nr53)).subtract(BigDecimal.valueOf((int)nm53)));
            qgysPojo.setJS1364(qgysPojo.getJS1334().add(BigDecimal.valueOf((int)nr54)).subtract(BigDecimal.valueOf((int)nm54)));
            
            qgysPojo.setAdd1();
            qgysPojo.setAdd2();
            qgysPojo.setAdd3();
            qgysPojo.setAdd4();
            qgysPojo.setAdd5();
            qgysPojo.setAdd6();
            qgysPojo.setJS1326(percentCalc(qgysPojo.getJS1325(), qgysPojo.getJS1315()));
            qgysPojo.setJS1336(percentCalc(qgysPojo.getJS1335(), qgysPojo.getJS1315()));
            qgysPojo.setJS1356(percentCalc(qgysPojo.getJS1355(), qgysPojo.getJS1345()));
            qgysPojo.setJS1366(percentCalc(qgysPojo.getJS1365(), qgysPojo.getJS1345()));
            setTable2PageValue(qgysPojo, null);
        } else {
            setTable2PageValue(null, js99Pojos.get(0));
        }
    }
    
    @Transaction
    @PageEvent("tsGBJD")
    public int tsGBJD() throws RadowException{
    	String rb_id=this.getPageElement("rbId").getValue();
    	
    	HBSession hbSession = HBUtil.getHBSession();
    	Connection conn = null;
    	Statement state=null;
    	ResultSet rs=null;
    	try{
    		RecordBatch rb = (RecordBatch) hbSession.get(RecordBatch.class, rb_id);
    		conn=hbSession.connection();
    		conn.setAutoCommit(false);
    		state=conn.createStatement();
    		rs=state.executeQuery("select regotherid from checkreg where regotherid='"+rb_id+"'");
    		
    		//如果没有这个批次的话就更新批次表信息
			if (!rs.next()) {
				String nowTime=com.utils.DateUtil.dateToString(new Date(), "yyyy/MM/dd");
				state.executeUpdate("insert into checkreg(CHECKREGID,REGNO,REGNAME,CHECKDATE,REGSTATUS,PUBLISHTIME,REGUSER,USERID,GROUPID,GROUPNAME,CREATETIME,REGTYPE,REGOTHERID) "
						+ "select RB_ID,RB_NO,RB_NAME||'(选拔任用)',RB_DATE,'0',null,RB_APPLICANT,(SELECT min(USERID) FROM SMT_USER WHERE LOGINNAME=RB_APPLICANT),(SELECT min(ID) FROM SMT_USERGROUP WHERE USERGROUPNAME=RB_ORG),RB_ORG,TO_DATE('"+nowTime+"','yyyy/MM/dd HH24:MI:SS'),'2',RB_ID "
						+ "from record_batch where RB_ID='"+rb_id+"'");
			}
			
			rs = state.executeQuery("select max(sortid1) from CHECKREGPERSON where checkregid ='"+rb_id+"'");
			int num = 1;
			if(rs.next()) {
				num = rs.getInt(1) +1;
			}
			HBSession sess = HBUtil.getHBSession();
			//查询批次下所有人员,不包括已经推荐过的该批次人员
			rs=state.executeQuery("select A0000,js0122 from js01 where RB_ID='"+rb_id+"' and nvl(js0120,'1')!='2' and a0000 not in(select A0000 from CHECKREGPERSON where CRP000='"+rb_id+"')");
			while(rs.next()){
				String a0000 = rs.getString("A0000");
				String xt = rs.getString("js0122");
				//保存批次下人员
				if(xt!=null && (xt.equals("2") || xt.equals("3") || xt.equals("4"))) {
					String crp008 = UUID.randomUUID().toString();
					String sql1 = "insert into CHECKREGPERSON select sys_guid(),'"+a0000+"',null,a0101,"
							+ "'报告人',A0107,a0192a,(select code_name from code_value where code_type='GB4762' " + 
							"and code_value=A0141),"+num+",0,a0184,'1','"+rb_id+"','"+crp008+"'，case when a0104='1' "
							+ " then '男' else '女' end,'' ,'','','','','','','','' from v_js_a01 where v_xt='"+xt+"' and a0000='"+a0000+"'";
					String sql2 = "insert into CHECKREGPERSON select c1,c2,c3,c4,c5,c6,c7,c8,c9, rownum c10,c11,c12,c13,c14,"
							+ "c15,c16,c17,c18,c19,c20,c21,c22,c23,'' from (select sys_guid() c1,'"+a0000+"' c2,a3600 c3,A3601 c4,"
							+ "case when A3604A IN ('丈夫','妻子') then '配偶' when A3604A IN ('儿媳','女婿') then '子女的配偶' else '子女' end c5,A3607 c6,A3611 c7,A3627 c8,"+num+" c9,"
							+ "0 c10,A3684 c11,'2' c12,'"+rb_id+"' c13,'"+crp008+"' c14,case when "
							+ "A3604A in ('长女','次女','继女','女儿','妻子','其他女儿','三女','四女','五女','养女','儿媳') then '女' else '男' end c15,'' c16,'' c17,'' c18,'' c19,'' c20,'' c21,'' c22,'' c23  from v_js_a36 where v_xt='"+xt+"' and a0000='"+a0000+"'"
							+ " and A3604A in ('长女','长子','次女','次子','儿子','继女','继子','女儿','妻子','其他女儿','三女','三子','四女','四子','五女','五子','养女','养子','丈夫','儿媳','女婿') order by sortid)";
					state.addBatch(sql1);
					state.addBatch(sql2);
				} else {
					String crp008 = UUID.randomUUID().toString();
					String sql1 = "insert into CHECKREGPERSON select sys_guid(),'"+a0000+"',null,a0101,"
							+ "'报告人',A0107,a0192a,(select code_name from code_value where code_type='GB4762' " + 
							"and code_value=A0141),"+num+",0,a0184,'1','"+rb_id+"','"+crp008+"'，case when a0104='1' "
							+ " then '男' else '女' end,'' ,'','','','','','','','' from a01 where a0000='"+a0000+"'";
					String sql2 = "insert into CHECKREGPERSON select c1,c2,c3,c4,c5,c6,c7,c8,c9, rownum c10,c11,c12,c13,c14,"
							+ "c15,c16,c17,c18,c19,c20,c21,c22,c23,'' from (select sys_guid() c1,'"+a0000+"' c2,a3600 c3,A3601 c4,"
							+ "case when A3604A IN ('丈夫','妻子') then '配偶' when A3604A IN ('儿媳','女婿') then '子女的配偶' else '子女' end c5,A3607 c6,A3611 c7,A3627 c8,"+num+" c9,"
							+ "0 c10,A3684 c11,'2' c12,'"+rb_id+"' c13,'"+crp008+"' c14,case when "
							+ "A3604A in ('长女','次女','继女','女儿','妻子','其他女儿','三女','四女','五女','养女','儿媳') then '女' else '男' end c15,'' c16,'' c17,'' c18,'' c19,'' c20,'' c21,'' c22,'' c23  from a36 where a0000='"+a0000+"'"
							+ " and A3604A in ('长女','长子','次女','次子','儿子','继女','继子','女儿','妻子','其他女儿','三女','三子','四女','四子','五女','五子','养女','养子','丈夫','儿媳','女婿') order by sortid)";
					state.addBatch(sql1);
					state.addBatch(sql2);
				}
				num=num+1;
			}
			state.executeBatch();
			conn.commit();
			//this.setMessageType("推送成功!");
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','该批次下全部人员推送成功!',null,200);this.hideTsgbjd('未审核');");
			//this.getExecuteSG().addExecuteCode("todoInfo('DB0001','"+rb_id+"','"+rb.getRbName()+"','"+rb_id+"','"+rb.getRbName()+"(选拔任用)');");
    	}catch(Exception e){
    		e.printStackTrace();
    		try {
				if(conn!=null){
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
    		this.setMainMessage("推送失败!");
    	}finally{
    		try{
    			if(rs!=null){
        			rs.close();	
        		}
    			if(state!=null){
    				state.close();
    			}
    			if(conn!=null){
    				conn.close();
    			}
    		}catch(SQLException e){
    			e.printStackTrace();
    		}
    	}
    	 	
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("clearnrzw")
    public int clearnrzw() throws RadowException{
    	String a0000=this.getPageElement("a0000").getValue();
    	try {
			HBUtil.executeUpdate("update js22 set js2205='' where a0000='"+a0000+"'");
			this.setMainMessage("清除成功!");
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.setMainMessage("清除失败!");
		}
    	return EventRtnType.NORMAL_SUCCESS;
    }
    
    @PageEvent("updateA02")
    public int updateA02(){
    	HBSession sess = HBUtil.getHBSession();
    	Connection conn = null;
    	Statement sm=null;
    	try{
    		String js0100=this.getPageElement("js0100").getValue();
    		Js01 js01 = (Js01) sess.get(Js01.class, js0100);
    		String a0000 = js01.getA0000();
    		String v_xt = js01.getJs0122();
    		
    		conn=sess.connection();
    		conn.setAutoCommit(false);
    		if(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3") || v_xt.equals("4"))) {
    			copyVJsA01ToA01(sess, a0000, v_xt);
    		}
    		sm = conn.createStatement();
    		CommonQueryBS cq=new CommonQueryBS();
    		String sql1="select js2200,js2201,js2202,js2203,js2200,a0000,js2204,js2205,js2206,js2207,js2300 from JS22 where js0100='"+js0100+"' and js2205='1' order by sortid";
    		List<HashMap<String, Object>> list = cq.getListBySQL(sql1);
    		String nowtime=DateUtil.dateToString(new Date(), "yyyyMMdd");
    		boolean zzw = false;	//包含主职务
    		for(HashMap<String, Object> map:list){
    			String js2206=inullback(map.get("js2206"));//判断是否已更新
    			if("1".equals(js2206) && !(v_xt!=null && (v_xt.equals("2") || v_xt.equals("3") || v_xt.equals("4")))){
    				continue;
    			}
    			
    			String a0200=UUID.randomUUID().toString().replaceAll("-", "");
    			String a0201a=inullback(map.get("js2201"));
    			String a0201b=inullback(map.get("js2202"));
    			String gwcode=inullback(map.get("js2204"));
    			String a0201d="";
    			String a0201e="";
    			String a0215a=inullback(map.get("js2203"));
    			String a0219="";
    			String a0225="1";//默认都排序到第一个
    			String a0243=nowtime;
    			String a0245="";
    			String a0247="2";//选拔
    			String a0251b="0";//是否破格提拨,默认否
    			String a0255="1";//任职状态，在任
    			String a0281="true";//职务输出标识
    			String a0221a="0";//职务等级
    			String a0251="0";//任职变动类型
    			String a0201c=inullback(map.get("js2201"));//任职机构简称
    			String a0279=inullback(map.get("js2207"));//??? 主职务标识,待做 
    			if(zzw) {
    				a0279 = "0";
    			}
    			if(a0279.equals("1")) {
    				zzw = true;
    			}
    			String js2300=inullback(map.get("js2300"));
    			//String v0201b="001";//虚拟列
    			
    			String sql="select b0194 from b01 where b0111='"+a0201b+"'";
    			List<HashMap<String, Object>> b01list = cq.getListBySQL(sql);
    			if(b01list.size()>0){
    				HashMap<String, Object> b01map = b01list.get(0);
    				String b0194 = inullback(b01map.get("b0194"));
    				if(!"1".equals(b0194) && !"2".equals(b0194)){
    					this.setMainMessage("更新失败，机构非法人单位或内设机构。");
        				return EventRtnType.NORMAL_SUCCESS;
    				}
    			}else{
    				this.setMainMessage("更新失败，不能查询到机构代码。");
    				return EventRtnType.NORMAL_SUCCESS;
    			}
    			
    			String sql2="select a0201d,a0201e,a0219 from jggwconf where b0111='"+a0201b+"' and gwcode='"+gwcode+"'";
    			List<HashMap<String, Object>> list1 = cq.getListBySQL(sql2);
    			if(list1.size()>0){
    				HashMap<String, Object> jgmap = list1.get(0);
    				a0201d=inullback(jgmap.get("a0201d"));
    				a0201e=inullback(jgmap.get("a0201e"));
    				a0219=inullback(jgmap.get("a0219"));
    				
    			}else{
    				this.setMainMessage("更新失败,更新的职务没有在机构下配置!");
    				return EventRtnType.NORMAL_SUCCESS;
    			}
    			
    			String sql3="select js2301 from js23 where js2300='"+js2300+"'";
    			List<HashMap<String, Object>> list2 = cq.getListBySQL(sql3);
    			if(list2.size()>0){
    				HashMap<String, Object> js23map = list2.get(0);
    				a0245=inullback(js23map.get("js2301"));//发文号
    			}
    			
    			String insertsql = "insert into A02(A0000,A0200,A0201A,A0201B,A0201D,A0201E,A0215A,A0219,A0225,A0243,A0245,"
    					+ "A0247,A0251B,A0255,A0281,A0221A,A0251,A0201C,A0279) values("
    					+ "'"+a0000+"','"+a0200+"','"+a0201a+"','"+a0201b+"','"+a0201d+"','"+a0201e+"','"+a0215a+"','"+a0219+"','"+a0225+"','"+a0243+"','"+a0245+"',"
    							+ "'"+a0247+"','"+a0251b+"','"+a0255+"','"+a0281+"','"+a0221a+"','"+a0251+"','"+a0201c+"','"+a0279+"')";
    			sm.addBatch(insertsql);
    			//System.out.println(insertsql);
    			
    			//更新状态
    			String js2200=inullback(map.get("js2200"));
    			String upjs2206sql="update js22 set js2206='1' where js2200='"+js2200+"'";
    			sm.addBatch(upjs2206sql);
    		}
    		
    		//将拟免职务更新到A02
    		/*String sql4="select js2303 from js23 where a0000='"+a0000+"'";
    		List<HashMap<String, Object>> list3 = cq.getListBySQL(sql4);
    		String js2303s="";
    		if(list3.size()>0){
    			for(HashMap<String, Object> map:list3){
    				String js2303=inullback(map.get("js2303"));
    				js2303s=js2303s+js2303+",";
    			}
    		}
    		String sql5="select a0200,a0201a,a0215a from A02 where A0000='"+a0000+"' and a0255='1'";
    		List<HashMap<String, Object>> list4 = cq.getListBySQL(sql5);
    		if(list4.size()>0){
    			for(HashMap<String, Object> map:list4){
    				String a0201a=inullback(map.get("a0201a"));
    				String a0215a=inullback(map.get("a0215a"));
    				String a0200=inullback(map.get("a0200"));
    				String nm=a0201a+a0215a;
    				if(js2303s.indexOf(nm)!=-1){
    					String upsql="update a02 set a0265='"+nowtime+"',a0281='false',a0255='0' where a0200='"+a0200+"'";
    					sm.addBatch(upsql);
    				}
    			}
    		}*/
    		String sqlzzw0 = "";	//如果主职务
    		if(zzw) {
    			sqlzzw0 = ",a0279='0' ";
    		}
    		String sql2="select js2400,js2300,a0200 from JS24 where js0100='"+js0100+"'";
    		List<HashMap<String, Object>> list4 = cq.getListBySQL(sql2);
    		if(list4.size()>0){
    			for(HashMap<String, Object> map:list4){
    				String a0200=inullback(map.get("a0200"));
    				String upsql="update a02 set a0265='"+nowtime+"',a0281='false',a0255='0'"+sqlzzw0+" where a0200='"+a0200+"'";
					System.out.println(upsql);
    				sm.addBatch(upsql);
    			}
    		}
    		
    		sm.executeBatch();
    		conn.setAutoCommit(true);
    		//this.setMainMessage("更新成功!");
    		this.getExecuteSG().addExecuteCode("$h.confirm('系统提示','是否重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
    				"if(id=='ok'){" +
    					"radow.doEvent('UpdateTitleBtn.onclick','"+a0000+"');	" +
    					"}else if(id=='cancel'){" +
    					"  " +
    					"}" +
    				"});");
    	}catch(Exception e){
    		try {
    			if(conn!=null){
    				conn.rollback();
    			}
			} catch (SQLException e1) {
			
				e1.printStackTrace();
			}
    		e.printStackTrace();
    		this.setMainMessage("更新失败!");
    		
    	}finally{
    		try{
    			if(sm!=null){
    				sm.close();
    			}
    			if(conn!=null){
    				conn.close();
    			}
    		}catch(SQLException e1){
    			
    		}
    	}
    	return EventRtnType.NORMAL_SUCCESS;
    }

	private void copyVJsA01ToA01(HBSession sess, String a0000, String v_xt) throws RadowException {//oracle    pywu 20170609核对字段是否对应数据库
		String A0000Sql = "'"+a0000+"'";
		String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
		try {
			//a06 //delete 
			sess.createSQLQuery(" delete from a06 where A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a06 a USING (select * from v_js_a06 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a0600 = t.a0600)  WHEN MATCHED THEN UPDATE " +
					"SET a.A0000=t.A0000,a.A0601=t.A0601,a.A0602=t.A0602,a.A0604=t.A0604,a.A0607=t.A0607," +
					"a.A0611=t.A0611,a.A0614=t.A0614,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A0699=t.A0699 " +
					" WHEN NOT MATCHED THEN INSERT " +
					"(A0600,A0000,A0601,A0602, A0604, A0607, A0611, A0614, SORTID, UPDATED, A0699)"+
					"VALUES (t.A0600,t.A0000,t.A0601,t.A0602, t.A0604, t.A0607, t.A0611, t.A0614, t.SORTID, t.UPDATED, t.A0699) " +
					"").executeUpdate();
			//a08 //delete 
			sess.createSQLQuery(" delete from a08 where  A0000 in ("+A0000Sql+")").executeUpdate();
			//insert
			sess.createSQLQuery(" MERGE INTO a08 a USING (select * from v_js_a08 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (t.a0800 = a.a0800)  WHEN MATCHED THEN UPDATE " +
					" SET a.A0000=t.A0000,a.A0801A=t.A0801A,a.A0801B=t.A0801B,a.A0804=t.A0804,a.A0807=t.A0807,a.A0811=t.A0811,a.A0814=t.A0814,a.A0824=t.A0824," +
					" a.A0827=t.A0827,a.A0831=t.A0831,a.A0832=t.A0832,a.A0834=t.A0834,a.A0835=t.A0835,a.A0837=t.A0837,a.A0838=t.A0838,a.A0839=t.A0839," +
					" a.A0898=t.A0898,a.A0899=t.A0899,a.A0901A=t.A0901A,a.A0901B=t.A0901B,a.A0904=t.A0904,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.WAGE_USED=t.WAGE_USED" + 
					"  WHEN NOT MATCHED THEN INSERT" +
					"(A0000,A0800,A0801A,A0801B,A0804,A0807,A0811,A0814,A0824,A0827, A0831,A0832,A0834,A0835,A0837,A0838,A0839,A0898," +
					" A0899,A0901A, A0901B,A0904,SORTID,updated,wage_used)"+
					" VALUES (t.A0000,t.A0800,t.A0801A,t.A0801B,t.A0804,t.A0807,t.A0811,t.A0814,t.A0824,t.A0827, t.A0831,t.A0832,t.A0834,t.A0835,t.A0837,t.A0838,t.A0839,t.A0898," +
					" t.A0899,t.A0901A, t.A0901B,t.A0904,t.SORTID,t.updated,t.wage_used ) ").executeUpdate();
			//a11 //delete 
			sess.createSQLQuery(" delete from a11 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a11 a USING (select * from v_js_a11 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a1100 = t.a1100)  WHEN MATCHED THEN UPDATE" +
					" SET a.A1127=t.A1127,a.A1131=t.A1131,a.A1134=t.A1134,a.A1151=t.A1151,a.UPDATED=t.UPDATED,a.A1108=t.A1108,a.A1107C=t.A1107C," +
					" a.A0000=t.A0000,a.A1101=t.A1101,a.A1104=t.A1104,a.A1107=t.A1107," +
					"a.A1107A=t.A1107A,a.A1107B=t.A1107B,a.A1111=t.A1111,a.A1114=t.A1114,a.A1121A=t.A1121A, "
					+ "a.g11025=t.g11025, a.g11024=t.g11024, a.g11023=t.g11023, a.g11022=t.g11022,"
					+ " a.g11021=t.g11021, a.g11020=t.g11020, a.g11003=t.g11003, a.g11006=t.g11006 " +
					" WHEN NOT MATCHED THEN INSERT" +
					"(A0000,A1100,A1101,A1104,A1107,A1107A,a1107b,a1111,a1114,a1121a,a1127,a1131,a1134,a1151,updated,A1108,A1107C "
					+ ",g11025,g11024,g11023,g11022,g11021,g11020,g11003,g11006)"+
					" VALUES (t.A0000,t.A1100,t.A1101,t.A1104,t.A1107,t.A1107A,t.a1107b ,t.a1111 ,t.a1114 ,t.a1121a ,t.a1127 ,t.a1131 ,t.a1134 ,t.a1151 ,t.updated,t.A1108,t.A1107C"
					+ ",t.g11025,t.g11024,t.g11023,t.g11022,t.g11021,t.g11020,t.g11003,t.g11006 )" +
					" ").executeUpdate();
			
			//a14 //delete 
			sess.createSQLQuery(" delete from a14 where  A0000 in ("+A0000Sql+")").executeUpdate();
			//insert
			sess.createSQLQuery(" MERGE INTO a14 a USING (select * from v_js_a14 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a1400 = t.a1400)  WHEN MATCHED THEN UPDATE " +
					" SET a.A0000=t.A0000,a.A1404A=t.A1404A,a.A1404B=t.A1404B,a.A1407=t.A1407,a.A1411A=t.A1411A,a.A1414=t.A1414," +
					" a.A1415=t.A1415,a.A1424=t.A1424,a.A1428=t.A1428,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED " +
					"  WHEN NOT MATCHED THEN INSERT" +
					"(a0000, a1400, a1404a, a1404b, a1407 ,a1411a ,a1414 ,a1415, a1424, a1428,sortid ,updated )"+
					" VALUES (t.a0000, t.a1400, t.a1404a, t.a1404b, t.a1407 ,t.a1411a ,t.a1414 ,t.a1415, t.a1424, t.a1428,t.sortid ,t.updated )" +
					" ").executeUpdate();
			//a15 //delete 
			sess.createSQLQuery(" delete from a15 where  A0000 in ("+A0000Sql+")").executeUpdate();
			//insert
			sess.createSQLQuery(" MERGE INTO a15 a USING (select * from v_js_a15 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a1500 = t.a1500)  WHEN MATCHED THEN UPDATE" +
					" SET a.A0000=t.A0000,a.A1517=t.A1517,a.A1521=t.A1521,a.UPDATED=t.UPDATED,a.A1527=t.A1527" +
					"  WHEN NOT MATCHED THEN INSERT" +
					"(a0000, a1500, a1517,a1521, updated, a1527)"+
					" VALUES (t.a0000, t.a1500, t.a1517,t.a1521, t.updated, t.a1527) ").executeUpdate();
			//a29 //delete 
			sess.createSQLQuery(" delete from a29 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a29 a USING (select * from v_js_a29 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
					" SET a.A2907=t.A2907,a.A2911=t.A2911,a.A2921A=t.A2921A,a.A2941=t.A2941,a.A2944=t.A2944,a.A2947=t.A2947,a.A2949=t.A2949,a.UPDATED=t.UPDATED," +
					" a.A2921B=t.A2921B,a.A2947B=t.A2947B" +
					",a.A2921C=t.A2921C,a.A2921D=t.A2921D  " +
					" WHEN NOT MATCHED THEN INSERT" +
					"(a0000, a2907 ,a2911, a2921a ,a2941, a2944, a2949, updated,A2921B,A2947B,A2921C,A2921D,A2947)"+
					" VALUES (t.a0000, t.a2907 ,t.a2911, t.a2921a ,t.a2941, t.a2944, t.a2949, t.updated,t.A2921B,t.A2947B,t.A2921C,t.A2921D,t.A2947)" +
					" ").executeUpdate();
			//a30 //delete 
			sess.createSQLQuery(" delete from a30 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a30 a USING (select * from v_js_a30 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE" +
					" SET a.A3001=t.A3001,a.A3004=t.A3004,a.A3034=t.A3034,a.UPDATED=t.UPDATED,a.A3007A=t.A3007A,a.A3038=t.A3038" +
					" " +
					" WHEN NOT MATCHED THEN INSERT(a0000,a3001, a3004, a3034 ,updated,A3007A,a3038) VALUES (t.a0000,t.a3001, t.a3004, t.a3034 ,t.updated,t.A3007A,t.a3038)" +
					" ").executeUpdate();
			//a31 //delete 
			sess.createSQLQuery(" delete from a31 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery("MERGE INTO a31 a USING (select * from v_js_a31 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a0000 = t.a0000)  WHEN MATCHED THEN UPDATE " +
					" SET a.A3101=t.A3101,a.A3117A=t.A3117A,a.A3137=t.A3137," +
					" a.A3138=t.A3138,a.UPDATED=t.UPDATED,a.A3104=t.A3104,a.A3107=t.A3107,a.A3118=t.A3118" +
					"  WHEN NOT MATCHED THEN INSERT " +
					"(a0000,a3101,  a3117a,a3137, a3138 ,updated,a3104,a3107,a3118)"+
					" VALUES (t.a0000,t.a3101,  t.a3117a,t.a3137, t.a3138 ,t.updated,t.a3104,t.a3107,t.a3118)" +
					" ").executeUpdate();
			
			
			//a36 //delete 
			sess.createSQLQuery(" delete from a36 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery("MERGE INTO a36 a USING (select * from v_js_a36 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a3600 = t.a3600)  WHEN MATCHED THEN UPDATE" +
					" SET a.A0000=t.A0000,a.A3601=t.A3601,a.A3604A=t.A3604A,a.A3607=t.A3607,a.A3611=t.A3611,a.A3627=t.A3627,a.SORTID=t.SORTID,a.UPDATED=t.UPDATED,a.A3684=t.A3684 " +
					"  WHEN NOT MATCHED THEN INSERT " +
					"(a0000,a3600,a3601,a3604a,a3607,a3611,a3627 ,sortid ,updated,A3684)"+
					"VALUES (t.a0000,t.a3600, t.a3601, t.a3604a, t.a3607, t.a3611, t.a3627 ,t.sortid ,t.updated,t.A3684) " +
					"").executeUpdate();
			
			//a37 //delete 
			sess.createSQLQuery(" delete from a37 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a37 a USING (select * from v_js_a37 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
					" SET a.A3701=t.A3701,a.A3707A=t.A3707A,a.A3707C=t.A3707C,a.A3707E=t.A3707E,a.A3707B=t.A3707B,a.A3708=t.A3708,a.A3711=t.A3711,a.A3714=t.A3714,a.UPDATED=t.UPDATED" +
					" WHEN NOT MATCHED THEN INSERT " +
					"(a0000,a3701,a3707a,a3707c,a3707e,a3707b,a3708,a3711,a3714,updated)"+
					" VALUES (t.a0000,t.a3701,t.a3707a,t.a3707c,t.a3707e,t.a3707b,t.a3708,t.a3711,t.a3714,t.updated)" +
					" ").executeUpdate();
			//a41 //delete 
			sess.createSQLQuery(" delete from a41 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery("MERGE INTO a41 a USING (select * from v_js_a41 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A4100 = t.A4100)  WHEN MATCHED THEN UPDATE" +
					" SET a.A0000=t.A0000,a.A1100=t.A1100,a.A4101=t.A4101,a.A4102=t.A4102,a.A4103=t.A4103,a.A4104=t.A4104,a.A4105=t.A4105,a.A4199=t.A4199" +
					" WHEN NOT MATCHED THEN INSERT" +
					"(a4100, a0000,a1100 ,a4101, a4102, a4103 ,a4104, a4105 ,a4199)"+
					" VALUES (t.a4100, t.a0000,t.a1100 ,t.a4101, t.a4102, t.a4103 ,t.a4104, t.a4105 ,t.a4199)" +
					" ").executeUpdate();
			//a53 //delete 
			/*sess.createSQLQuery(" delete from a53 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a53 a USING v_js_a53 t ON (a.A5300 = t.A5300 and t.a0000="+A0000Sql+" and t.v_xt='"+v_xt+"')  WHEN MATCHED THEN UPDATE" +
					" SET a.A0000=t.A0000,a.A5304=t.A5304,a.A5315=t.A5315,a.A5317=t.A5317,a.A5319=t.A5319,a.A5321=t.A5321,a.A5323=t.A5323,a.A5327=t.A5327,a.A5399=t.A5399,a.UPDATED=t.UPDATED" +
					" WHEN NOT MATCHED THEN INSERT" +
					"(a0000,a5300,a5304,a5315,a5317,a5319,a5321,a5323,a5327,a5399,updated)"+
					" VALUES (t.a0000,t.a5300,t.a5304,t.a5315,t.a5317,t.a5319,t.a5321,t.a5323,t.a5327,t.a5399,t.updated)" +
					" ").executeUpdate();*/
			
			//a57 //delete 
			sess.createSQLQuery(" delete from a57 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a57 a USING (select * from v_js_a57 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE" +
					" SET a.A5714=t.A5714,a.UPDATED=t.UPDATED,a.PHOTONAME=t.PHOTONAME,a.PHOTSTYPE=t.PHOTSTYPE,a.PHOTOPATH=t.PHOTOPATH,a.PICSTATUS=t.PICSTATUS" +
					"  WHEN NOT MATCHED THEN INSERT" +
					" (a0000,a5714 ,updated,PHOTONAME,PHOTSTYPE,PHOTOPATH,PICSTATUS) VALUES (t.a0000,t.a5714 ,t.updated,t.PHOTONAME,t.PHOTSTYPE,t.PHOTOPATH,'1')" +
					" ").executeUpdate();
			
			//A05---
			sess.createSQLQuery(" delete from A05 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery("MERGE INTO a05 a USING (select * from v_js_a05 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A0500 = t.A0500)  WHEN MATCHED THEN UPDATE"+
					" SET a.A0000=t.A0000,a.A0531=t.A0531,a.A0501B=t.A0501B,a.A0504=t.A0504,a.A0511=t.A0511,a.A0517=t.A0517,a.A0524=t.A0524,a.A0525=t.A0525 "+
					"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A0500,a.A0531,a.A0501B,a.A0504,a.A0511,a.A0517,a.A0524,a.A0525)" +
					" VALUES (t.A0000,t.A0500,t.A0531,t.A0501B,t.A0504,t.A0511,t.A0517,t.A0524,t.A0525)"+
					" ").executeUpdate();
			
			//A99Z1---
			sess.createSQLQuery(" delete from A99Z1 where  A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery("MERGE INTO a99Z1 a USING (select * from v_js_a99Z1 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A99Z100 = t.A99Z100)  WHEN MATCHED THEN UPDATE"+
					" SET a.A0000=t.A0000,a.A99Z101=t.A99Z101,a.A99Z102=t.A99Z102,a.A99Z103=t.A99Z103,a.A99Z104=t.A99Z104"+
					"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A99Z100,a.A99Z101,a.A99Z102,a.A99Z103,a.A99Z104)" +
					" VALUES (t.A0000,t.A99Z100,t.A99Z101,t.A99Z102,t.A99Z103,t.A99Z104)"+
					" ").executeUpdate();
			
			//a01
			sess.createSQLQuery(" delete from A01 where A0000 in ("+A0000Sql+")").executeUpdate();
			
			sess.createSQLQuery("create table jsB01"+tableExt+" (b0111 VARCHAR2(199),b0121 VARCHAR2(199),psnb0111 VARCHAR2(199),b0101 VARCHAR2(200),b0114 VARCHAR2(200))").executeUpdate();
			sess.createSQLQuery("insert into jsB01"+tableExt+" select t.b0111,t.b0121,'',t.b0101,t.b0114 from v_js_b01 t where v_xt='"+v_xt+"'").executeUpdate();
			sess.createSQLQuery("update jsB01"+tableExt+" t set t.psnb0111 =(select b.b0111 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114) where exists (select 1 from b01 b where b.b0101=t.b0101 and b.b0114=t.b0114)").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a01 a USING (select * from v_js_a01 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.A0000 = t.A0000)  WHEN MATCHED THEN UPDATE " +
					" SET a.A0192E=t.A0192E,a.A0199=t.A0199,a.A01K01=t.A01K01,a.A01K02=t.A01K02,a.CBDRESULT=t.CBDRESULT,a.CBDW=t.CBDW,"
					+ "a.ISVALID=t.ISVALID,a.NL=t.NL,a.NMZW=t.NMZW,a.NRZW=t.NRZW,a.ORGID=t.ORGID,a.QRZXL=t.QRZXL,a.QRZXLXX=t.QRZXLXX,"
					+ "a.QRZXW=t.QRZXW,a.QRZXWXX=t.QRZXWXX,a.RMLY=t.RMLY,a.STATUS=t.STATUS,a.TBR=t.TBR,a.TBRJG=t.TBRJG,"
					+ "a.USERLOG=t.USERLOG,a.XGR=t.XGR,a.ZZXL=t.ZZXL,a.ZZXLXX=t.ZZXLXX,a.ZZXW=t.ZZXW,a.ZZXWXX=t.ZZXWXX,a.A0155=t.A0155,"
					+ "a.AGE=t.AGE,a.JSNLSJ=t.JSNLSJ,a.RESULTSORTID=t.RESULTSORTID,a.TBSJ=t.TBSJ,a.XGSJ=t.XGSJ,a.SORTID=t.SORTID,"
					+ "a.A0194=t.A0194,a.A0104A=t.A0104A,a.A0111=t.A0111,a.A0114=t.A0114,a.A0117A=t.A0117A,a.A0128B=t.A0128B,"
					+ "a.A0141D=t.A0141D,a.A0144B=t.A0144B,a.A0144C=t.A0144C,a.A0148=t.A0148,a.A0148C=t.A0148C,a.A0149=t.A0149,"
					+ "a.A0151=t.A0151,a.A0153=t.A0153,a.A0157=t.A0157,a.A0158=t.A0158,a.A0159=t.A0159,a.A015A=t.A015A,a.A0161=t.A0161,"
					+ "a.A0162=t.A0162,a.A0180=t.A0180,a.A0191=t.A0191,a.A0192B=t.A0192B,a.A0193=t.A0193,a.A0194U=t.A0194U,"
					+ "a.A0198=t.A0198,a.A0101=t.A0101,a.A0102=t.A0102,a.A0104=t.A0104,a.A0107=t.A0107,"
					+ "a.A0111A=t.A0111A,a.A0114A=t.A0114A,a.A0115A=t.A0115A,a.A0117=t.A0117,a.A0128=t.A0128,a.A0134=t.A0134,"
					+ "a.A0140=t.A0140,a.A0141=t.A0141,a.A0144=t.A0144,a.A3921=t.A3921,a.A3927=t.A3927,a.A0160=t.A0160,a.A0163=t.A0163,"
					+ "a.A0165=t.A0165,a.A0184=t.A0184,a.A0187A=t.A0187A,a.A0192=t.A0192,a.A0192A=t.A0192A,a.A0221=t.A0221,"
					+ "a.A0288=t.A0288,a.A0192D=t.A0192D,a.A0192C=t.A0192C,a.A0196=t.A0196,a.A0197=t.A0197,a.A0195=t.A0195,"
					+ "a.A1701=to_clob(t.A1701),a.A14Z101=t.A14Z101,a.A15Z101=t.A15Z101,a.A0120=t.A0120,a.A0121=t.A0121,a.A2949=t.A2949,"
					+ "a.A0122=t.A0122,a.a0192f=t.a0192f,a.ZGXL=t.ZGXL,a.ZGXW=t.ZGXW,a.ZGXLXX=t.ZGXLXX,a.ZGXWXX=t.ZGXWXX " +
					"  WHEN NOT MATCHED THEN INSERT" +
					" (a.A0192E, a.A0199, a.A01K01, a.A01K02, a.CBDRESULT, a.CBDW, a.ISVALID, a.NL, a.NMZW, a.NRZW, a.ORGID, a.QRZXL, "
					+ "a.QRZXLXX, a.QRZXW, a.QRZXWXX, a.RMLY, a.STATUS, a.TBR, a.TBRJG, a.USERLOG, a.XGR, a.ZZXL, a.ZZXLXX, a.ZZXW, "
					+ "a.ZZXWXX, a.A0155, a.AGE, a.JSNLSJ, a.RESULTSORTID, a.TBSJ, a.XGSJ, a.SORTID, a.A0194, a.A0104A, a.A0111, "
					+ "a.A0114, a.A0117A, a.A0128B, a.A0141D, a.A0144B, a.A0144C, a.A0148, a.A0148C, a.A0149, a.A0151, a.A0153, "
					+ "a.A0157, a.A0158, a.A0159, a.A015A, a.A0161, a.A0162, a.A0180, a.A0191, a.A0192B, a.A0193, a.A0194U, "
					+ "a.A0198, a.A0000, a.A0101, a.A0102, a.A0104, a.A0107, a.A0111A, a.A0114A, a.A0115A, a.A0117, a.A0128, "
					+ "a.A0134, a.A0140, a.A0141, a.A0144, a.A3921, a.A3927, a.A0160, a.A0163, a.A0165, a.A0184, a.A0187A, a.A0192, "
					+ "a.A0192A, a.A0221, a.A0288, a.A0192D, a.A0192C, a.A0196, a.A0197, a.A0195, a.A1701, a.A14Z101, a.A15Z101,"
					+ " a.A0120, a.A0121, a.A2949, a.A0122, a.a0192f, a.ZGXL, a.ZGXW, a.ZGXLXX, a.ZGXWXX)" +
					" VALUES (t.A0192E, t.A0199, t.A01K01, t.A01K02, t.CBDRESULT, t.CBDW, t.ISVALID, t.NL, t.NMZW, t.NRZW, t.ORGID,"
					+ " t.QRZXL, t.QRZXLXX, t.QRZXW, t.QRZXWXX, t.RMLY, t.STATUS, t.TBR, t.TBRJG, t.USERLOG, t.XGR, t.ZZXL, t.ZZXLXX, "
					+ "t.ZZXW, t.ZZXWXX, t.A0155, t.AGE, t.JSNLSJ, t.RESULTSORTID, t.TBSJ, t.XGSJ, t.SORTID, t.A0194, t.A0104A, t.A0111, "
					+ "t.A0114, t.A0117A, t.A0128B, t.A0141D, t.A0144B, t.A0144C, t.A0148, t.A0148C, t.A0149, t.A0151, t.A0153, t.A0157,"
					+ " t.A0158, t.A0159, t.A015A, t.A0161, t.A0162, t.A0180, t.A0191, t.A0192B, t.A0193, t.A0194U, t.A0198, t.A0000,"
					+ " t.A0101, t.A0102, t.A0104, t.A0107, t.A0111A, t.A0114A, t.A0115A, t.A0117, t.A0128, t.A0134, t.A0140, t.A0141, "
					+ "t.A0144, t.A3921, t.A3927, t.A0160, t.A0163, t.A0165, t.A0184, t.A0187A, t.A0192, t.A0192A, t.A0221, t.A0288,"
					+ " t.A0192D, t.A0192C, t.A0196, t.A0197, t.A0195, to_clob(t.A1701), t.A14Z101, t.A15Z101, t.A0120, t.A0121, t.A2949, "
					+ "t.A0122,t.a0192f,t.ZGXL,t.ZGXW,t.ZGXLXX,t.ZGXWXX ) ").executeUpdate();
			
			sess.createSQLQuery("update A01 t set t.a0195 =(select b.psnb0111 from jsB01"+tableExt+" b where b.b0111=t.a0195) where  exists (select 1 from jsB01"+tableExt+" b where b.b0111=t.a0195) and a0000="+A0000Sql).executeUpdate();
			
			
			//a02 先删除a02数据 ----- ‘现有数据’与‘来源数据’ 中已存在的同样人员（身份证相同）数据  ------ 之后插入临时表数据
			sess.createSQLQuery(" delete from a02 a2 where  a2.A0000 in ("+A0000Sql+")").executeUpdate();
			sess.createSQLQuery(" delete from a02 where a02.A0000 not in (select a0000 from a01)").executeUpdate();
			sess.createSQLQuery(" MERGE INTO a02 a USING (select * from v_js_a02 where a0000="+A0000Sql+" and v_xt='"+v_xt+"') t ON (a.a0200 = t.a0200)  WHEN MATCHED THEN UPDATE" +
					" SET a.A0000=t.A0000,a.A0201A=t.A0201A,a.A0201B=t.A0201B,a.A0201D=t.A0201D,a.A0201E=t.A0201E,a.A0215A=t.A0215A,"
					+ "a.A0219=t.A0219,a.A0223=t.A0223,a.A0225=t.A0225,a.A0243=t.A0243,a.A0245=t.A0245,a.A0247=t.A0247,a.A0251B=t.A0251B,"
					+ "a.A0255=t.A0255,a.A0265=t.A0265,a.A0267=t.A0267,a.A0272=t.A0272,a.A0281=t.A0281,a.A0221T=t.A0221T,a.B0238=t.B0238,"
					+ "a.B0239=t.B0239,a.A0221A=t.A0221A,a.WAGE_USED=t.WAGE_USED,a.UPDATED=t.UPDATED,a.A4907=t.A4907,a.A4904=t.A4904,"
					+ "a.A4901=t.A4901,a.A0299=t.A0299,a.A0295=t.A0295,a.A0289=t.A0289,a.A0288=t.A0288,a.A0284=t.A0284,a.A0277=t.A0277,"
					+ "a.A0271=t.A0271,a.A0259=t.A0259,a.A0256C=t.A0256C,a.A0256B=t.A0256B,a.A0256A=t.A0256A,a.A0256=t.A0256,a.A0251=t.A0251,"
					+ "a.A0229=t.A0229,a.A0222=t.A0222,a.A0221W=t.A0221W,a.A0221=t.A0221,a.A0219W=t.A0219W,a.A0216A=t.A0216A,a.A0215B=t.A0215B,"
					+ "a.A0209=t.A0209,a.A0207=t.A0207,a.A0204=t.A0204,a.A0201C=t.A0201C,a.A0201=t.A0201,a.A0279=t.A0279 " +
					"  WHEN NOT MATCHED THEN INSERT" +
					"(a.A0000,a.A0200,a.A0201A,a.A0201B,a.A0201D,a.A0201E,a.A0215A,a.A0219,a.A0223,a.A0225,a.A0243,a.A0245,a.A0247,a.A0251B,"
					+ "a.A0255,a.A0265,a.A0267,a.A0272,a.A0281,a.A0221T,a.B0238,a.B0239,a.A0221A,a.WAGE_USED,a.UPDATED,a.A4907,a.A4904,a.A4901,"
					+ "a.A0299,a.A0295,a.A0289,a.A0288,a.A0284,a.A0277,a.A0271,a.A0259,a.A0256C,a.A0256B,a.A0256A,a.A0256,a.A0251,a.A0229,"
					+ "a.A0222,a.A0221W,a.A0221,a.A0219W,a.A0216A,a.A0215B,a.A0209,a.A0207,a.A0204,a.A0201C,a.A0201,a.A0279)"+
					" VALUES (t.A0000,t.A0200,t.A0201A,t.A0201B,t.A0201D,t.A0201E,t.A0215A,t.A0219,t.A0223,t.A0225,t.A0243,t.A0245,t.A0247,"
					+ "t.A0251B,t.A0255,t.A0265,t.A0267,t.A0272,t.A0281,t.A0221T,t.B0238,t.B0239,t.A0221A,t.WAGE_USED,t.UPDATED,t.A4907,"
					+ "t.A4904,t.A4901,t.A0299,t.A0295,t.A0289,t.A0288,t.A0284,t.A0277,t.A0271,t.A0259,t.A0256C,t.A0256B,t.A0256A,t.A0256,"
					+ "t.A0251,t.A0229,t.A0222,t.A0221W,t.A0221,t.A0219W,t.A0216A,t.A0215B,t.A0209,t.A0207,t.A0204,t.A0201C,t.A0201,t.A0279)" +
					" ").executeUpdate();
			sess.createSQLQuery("update A02 t set t.a0201b ='-1' where not exists (select 1 from jsB01"+tableExt+" b where b.b0111=t.a0201b) and a0000="+A0000Sql).executeUpdate();
			sess.createSQLQuery("update A02 t set t.a0201b =(select b.psnb0111 from jsB01"+tableExt+" b where b.b0111=t.a0201b) where  exists (select 1 from jsB01"+tableExt+" b where b.b0111=t.a0201b) and a0000="+A0000Sql).executeUpdate();
			sess.createSQLQuery("drop table jsB01"+tableExt+" ").executeUpdate();
			
			/*String tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			String torgidSql = "create table torgid"+tableExt+"(A0000 VARCHAR2(120),TORGID VARCHAR2(200))";
			sess.createSQLQuery(torgidSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into torgid"+tableExt+" SELECT W.A0000,W.A0201B FROM (SELECT ROW_NUMBER () OVER (PARTITION BY V.a0000 ORDER BY B01.SORTID DESC) rn,A02.A0000,"
					+ "A02.A0201B FROM (SELECT A01.A0000,MIN (LENGTH(A0201B)) minlength FROM a02,a01 WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0201B LIKE '"+impdeptid+"%'"
					+ "GROUP BY A01.A0000) V,A02,B01 WHERE V.A0000 = A02.A0000 AND B01.B0111 = A02.A0201B AND A02.A0281 = 'true' AND LENGTH (A02.A0201B) "
					+ "= V.minlength) W WHERE W.RN = 1").executeUpdate();
			sess.createSQLQuery("Create index tid" + tableExt + " on torgid" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("UPDATE a01 SET A01.TORGID = (SELECT t.TORGID FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torgid"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
			sess.createSQLQuery("drop table torgid"+tableExt).executeUpdate();
			
			String torderSql = "create table torder"+tableExt+"(A0000 VARCHAR2(120),TORDER VARCHAR2(8))";
			sess.createSQLQuery(torderSql.toUpperCase()).executeUpdate();
			sess.createSQLQuery("insert into torder"+tableExt+" SELECT a01.a0000,LPAD (MAX(a02.a0225), 5, 0) FROM a02,a01 WHERE a01.a0000 = a02.a0000 "
					+ "AND a02.a0281 = 'true' AND a01.torgid = a02.a0201b AND a01.torgid LIKE '"+impdeptid+"%' GROUP BY a01.a0000").executeUpdate();
			sess.createSQLQuery("Create index tder" + tableExt + " on torder" + tableExt + "(A0000)").executeUpdate();
			sess.createSQLQuery("UPDATE a01 SET A01.torder = (SELECT t.torder FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 ) WHERE EXISTS (SELECT 1 FROM torder"+tableExt+" t WHERE t.a0000 = A01.A0000 )").executeUpdate();
			sess.createSQLQuery("drop table torder"+tableExt).executeUpdate();*/
		} catch (Exception e) {
			e.printStackTrace();
			try {
				sess.createSQLQuery("drop table jsB01"+tableExt+" ").executeUpdate();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw new RadowException("迁移数据异常！");
		}
		
		String sql = "select photopath,photoname from a57 where  A0000 in ("+A0000Sql+")";
		List<Object[]> list = sess.createSQLQuery(sql).list();
		if(list!=null && list.size()>0) {
			Object[] arr = list.get(0);
			String p = arr[0]!=null ? arr[0].toString().trim() :"";
			String n = arr[1]!=null ? arr[1].toString().trim() :"";
			if(!p.trim().equals("") && !n.trim().equals("") ) {
				String r = PhotosUtil.PHOTO_PATH +"/qcjs/"+v_xt+"/"+p+n;
				File file = new File(r);
				if(file.exists() && file.isFile()) {
					String t = PhotosUtil.PHOTO_PATH +"/"+p;
					File file2 = new File(t);
					if(!file2.exists()) {
						file2.mkdirs();
					}
					List<String> list2 = new ArrayList<String>();
					list2.add(r);
					PhotosUtil.copyAbsoluteFiles(list2, t);
				}
			}
		}
		
	}
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		return no;
	}
	
	/**
	 * 更新名称
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		boolean isEvent = false;
		
		String a0000=null;
		/*try {
			a0000 = this.getPageElement("subWinIdBussessId").getValue();
		} catch (RuntimeException e) {
			//e.printStackTrace();
		}*/
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//职务名称
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwmc);
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "、" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
		}else{
			//this.getPageElement("a0192a").setValue("");
			//this.getPageElement("a0192").setValue("");
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192a').value='';");
//				this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a0192').value='';");
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
			
		}
		
		this.UpdateTimeBtn(id);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 更新名称对应的时间
	 * @return
	 * @throws RadowException
	 */
	@Transaction
	@NoRequiredValidate
	public int UpdateTimeBtn(String id) throws RadowException {
		
		boolean isEvent = false;
		
		String a0000=null;
		/*try {
			a0000 = this.getPageElement("subWinIdBussessId").getValue();
		} catch (RuntimeException e) {
			
		}*/
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				//jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				jgmcList.add("");
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0215a()==null?"":a02.getA0215a();//职务名称
				
				String zwrzshj = "";//职务任职时间
				if(a02.getA0243() != null && a02.getA0243().length() >= 6 && a02.getA0243().length() <= 8){
					zwrzshj = a02.getA0243().substring(0,4) + "." + a02.getA0243().substring(4,6);
				}
				
				
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										//desc_full.put(key_full, jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full, zwrzshj);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										//desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc+zwrzshj);
										desc_full.put(key_full,value_full + "、" + zwrzshj);
										
									}
									
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							//desc_full.put(key_full, zwmc+zwrzshj);//key 编码_$_机构名称_$_是否已免
							desc_full.put(key_full, zwrzshj);//key 编码_$_机构名称_$_是否已免
						}else{
							//desc_full.put(key_full, value_full + "、" + zwmc+zwrzshj);
							desc_full.put(key_full, value_full + "、" + zwrzshj);
						}
						
					
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				//String jgzw = parm[1]+desc_full.get(key);
				String jgzw = desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "("+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "("+ymjc+")";
			}
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(zrqm+ymqm);
			sess.update(a01);
			
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
		}else{
			
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192f(null);
			sess.update(a01);
			
			if(isEvent){
//				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192f').value='"+(a01.getA0192f()==null?"":a01.getA0192f())+"';");
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	@PageEvent("removeJsPerson")
    @Synchronous(true)
    public int removeJsPerson(String js0100s) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		Statement stmt3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String rbId = this.getPageElement("rbId").getValue();
		String lx = this.getPageElement("slllshqklx").getValue();
		String dellx = "";
		String sm = this.getPageElement("slllshqk").getValue();
		try {
			if(lx==null || lx.equals("") || lx.equals("1")) {
				this.setMainMessage("请选择一个移除类型。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(lx.equals("2")) {
				dellx = "2";
			} else {
				dellx = "1";
			}
			String js01sql = "select a0000,js0122,js0100,js0102 from js01 where js0100 in ('"+(js0100s)+"')";
			List<Object[]> list = sess.createSQLQuery(js01sql).list();
			conn = sess.connection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			for (int i = 0; i < list.size(); i++) {
				Object[] o = list.get(i);
				String a0000 = o[0].toString();
				String js0122 = o[1].toString();
				String js0100new = o[2].toString();
				String js0102 = o[3].toString();
				if(dellx.equals("1") || dellx.equals("2") || dellx.equals("3")) {
					//查询原有备份库数据，原有信息，更新标识，删除原有备份
					String sql = "select JS_HIS_ID,js0100 from JS_HIS t where a0000='"+a0000+"' and js0122='"+js0122+"'";
					rs = stmt.executeQuery(sql);
					if(rs.next()) {
						String js_his_id = rs.getString(1);
						String js0100 = rs.getString(2);
						String u1 = "update JS_HIS set JSH004='0' where JS_HIS_ID='"+js_his_id+"'";
						stmt2.addBatch(u1);
						
						String d1 = "delete from js01_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//动议表
						d1 = "delete from js02_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//民主推荐表
						d1 = "delete from js03_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//考察人选确定表 ？
						d1 = "delete from js04_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//酝酿表？
						d1 = "delete from js06_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//任前公示表
						d1 = "delete from js08_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//依法办理任免表
						d1 = "delete from js09_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//试用期表
						d1 = "delete from js10_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//其他情况表
						d1 = "delete from js11_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//组织考察表（无锡）
						d1 = "delete from js14_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//讨论决定表（无锡）
						d1 = "delete from js15_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//推荐
						d1 = "delete from js18_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js19_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js20_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//保存考核与听取意见
						d1 = "delete from js21_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//发文
						d1 = "delete from js22_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js23_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js24_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						//删除附件
						stmt3 = conn.createStatement();
						rs2 = stmt3.executeQuery("select * from js_att_his where js0100 ='"+js0100+"'");
						while(rs2.next()) {
							String p = AppConfig.HZB_PATH + "/jshis/";
							String jsa00 = rs2.getString("jsa00");
							String jsa07 = rs2.getString("jsa07");
							File f = new File(p + jsa07 + jsa00);
	                        if (f.isFile()) {
	                            f.delete();
	                        }
						}
						rs2.close();
						stmt3.close();
						d1 = "delete from js_att_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
						d1 = "delete from js_hj_his where js0100 ='"+js0100+"'";
						stmt2.addBatch(d1);
					}
					//插入备份库信息
					if(dellx.equals("1") || dellx.equals("3")) {
						String i1 = "insert into js01_his select * from js01 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						i1 = "insert into js02_his select * from js02 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js03_his select * from js03 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js04_his select * from js04 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js06_his select * from js06 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js08_his select * from js08 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js09_his select * from js09 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js10_his select * from js10 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js11_his select * from js11 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js14_his select * from js14 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js15_his select * from js15 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js18_his select * from js18 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js19_his select * from js19 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js20_his select * from js20 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js21_his select * from js21 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js22_his select * from js22 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js23_his select * from js23 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						i1 = "insert into js24_his select * from js24 where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1); 
						//删除附件
						stmt3 = conn.createStatement();
						rs2 = stmt3.executeQuery("select * from js_att where js0100 ='"+js0100new+"'");
						while(rs2.next()) {
							String p = AppConfig.HZB_PATH + "/";
							String jsa00 = rs2.getString("jsa00");
							String jsa07 = rs2.getString("jsa07");
							String r = AppConfig.HZB_PATH + "/jshis/" + jsa07;
							File f = new File(r);
	                        if (!f.exists()) {
	                            f.mkdirs();
	                        }
	                        File f2 = new File(p + jsa07 + jsa00);
	                        if(f2.exists() && f2.isFile()) {
	                        	 PhotosUtil.copyFile(f2, new File(r + jsa00));
	                        }
	                       
						}
						rs2.close();
						stmt3.close();
						
						i1 = "insert into js_att_his select * from js_att where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						i1 = "insert into js_hj_his select * from js_hj where js0100 ='"+js0100new+"'";
						stmt2.addBatch(i1);
						
						i1 = "insert into JS_HIS values (sys_guid(),'"+js0100new+"','"+a0000+"','"+js0122+"','"+dellx
								+"','"+sm+"',sysdate,'1','"+js0102+"','')";
						stmt2.addBatch(i1);
					} else {
						String i1 = "insert into JS_HIS values (sys_guid(),'"+js0100new+"','"+a0000+"','"+js0122+"','"+dellx
								+"','"+sm+"',sysdate,'1','"+js0102+"','')";
						System.out.println(i1);
						stmt2.addBatch(i1);
					}
				}
				//移除正式库信息
				String d1 = "delete from js01 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//动议表
				d1 = "delete from js02 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//民主推荐表
				d1 = "delete from js03 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//考察人选确定表 ？
				d1 = "delete from js04 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//酝酿表？
				d1 = "delete from js06 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//任前公示表
				d1 = "delete from js08 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//依法办理任免表
				d1 = "delete from js09 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//试用期表
				d1 = "delete from js10 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//其他情况表
				d1 = "delete from js11 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//组织考察表（无锡）
				d1 = "delete from js14 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//讨论决定表（无锡）
				d1 = "delete from js15 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//推荐
				d1 = "delete from js18 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js19 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js20 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//保存考核与听取意见
				d1 = "delete from js21 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//发文
				d1 = "delete from js22 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js23 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js24 where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				d1 = "delete from js_hj where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
				//删除附件
				stmt3 = conn.createStatement();
				rs2 = stmt3.executeQuery("select * from js_att where js0100 ='"+js0100new+"'");
				while(rs2.next()) {
					String p = AppConfig.HZB_PATH + "/";
					String jsa00 = rs2.getString("jsa00");
					String jsa07 = rs2.getString("jsa07");
					File f = new File(p + jsa07 + jsa00);
                    if (f.isFile()) {
                        f.delete();
                    }
				}
				rs2.close();
				stmt3.close();
				d1 = "delete from js_att where js0100 ='"+js0100new+"'";
				stmt2.addBatch(d1);
			}
			stmt2.executeBatch();
			conn.commit();
			this.setMainMessage("移除成功");
			this.getExecuteSG().addExecuteCode("radow.doEvent('gridcq.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn!=null) 
					conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			throw new RadowException("数据异常！");
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt2!=null) {
				try {
					stmt2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt!=null) {
				try {
					stmt.close();
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
	
	@PageEvent("printViewNew")
	public int pdfViewNew(String a0000) throws RadowException, AppException{
		String pdfPath = "";  											//pdf文件路径
		List<String> list = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		list.add(a0000);
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01) sess.get(A01.class, a0000);
		String a0101 = a01.getA0101();
		List<String> pdfPaths = null;
		/*
		 * try { pdfPaths = new ExportAsposeBS().getPdfsByA000s_aspose(list,
		 * "eebdefc2-4d67-4452-a973-5f7939530a11","pdf",a0000,list2); } catch
		 * (AppException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		//String newPDFPath = pdfPaths.get(0).replace("\\", "/")+"/"+"1_"+a0101+".doc";
		String newPDFPath = ExpRar.expFile()+UUID.randomUUID().toString().replace("-", "")+".doc";
		QueryPersonListBS.mergePdfs(pdfPaths,newPDFPath);
		newPDFPath = newPDFPath.substring(newPDFPath.indexOf("ziploud")-1).replace("\\", "/");
		newPDFPath = "/hzb"+ newPDFPath;
		this.request.getSession().setAttribute("pdfFilePath", newPDFPath);
		String ctxPath = request.getContextPath();
		this.getExecuteSG().addExecuteCode("$h.openPageModeWin('pdfViewWinNew','pages.publicServantManage.PdfView','打印任免表',700,500,1,'"+ctxPath+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("searchScore")
	public void searchScore() throws RadowException{
		String rbId = this.getPageElement("rbId").getValue();
		CommonQueryBS comquery=new CommonQueryBS();
		Map<String,Object> map=new HashMap<String,Object>();
		try {
			List<HashMap<String, Object>> list = comquery.getListBySQL("select * from RECORD_BATCH where RB_ID='"+rbId+"'");
			if(list.size()>0){
				HashMap<String, Object> recordBatch = list.get(0);
				map.put("id", isnullbak(recordBatch.get("rb_id")));
				map.put("pid", isnullbak(recordBatch.get("rb_name")));
				map.put("sq_date", isnullbak(recordBatch.get("rb_date")));
				map.put("p_no", isnullbak(recordBatch.get("rb_no")));
				map.put("sq_org", isnullbak(recordBatch.get("rb_org")));
				map.put("sq_user", isnullbak(recordBatch.get("rb_applicant")));
				map.put("sp_result", isnullbak(recordBatch.get("rb_leadview")));
				map.put("status", isnullbak(recordBatch.get("rb_status")));
				map.put("merging_result", isnullbak(recordBatch.get("rbm_status")));
				List<Map<String,String>> persion=new ArrayList<Map<String,String>>();
				List<HashMap<String, Object>> persionlist = comquery.getListBySQL("select JS0100,JS0102,JS0108,A0184 from js01 left join a01 on js01.a0000=a01.a0000 where js01.RB_ID='"+rbId+"'");
				for(HashMap<String, Object> pmap:persionlist){
					Map<String,String> addmap = new HashMap<String,String>();
					addmap.put("user_id", isnullbak(pmap.get("js0100")));
					addmap.put("user_name", isnullbak(pmap.get("js0102")));
					addmap.put("id_card", isnullbak(pmap.get("a0184")));
					addmap.put("user_job", isnullbak(pmap.get("js0108")));
					persion.add(addmap);
				}
				map.put("persion", persion);
			}
		} catch (AppException e) {
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','失败!');");
		}
		String result="";
		System.out.println(JSONObject.fromObject(map).toString());
		HttpClient client = HttpClientFactory.getInstance().create();
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("accept","application/json");
			headers.put("content-type","application/json");
			result=HttpUtils.excutePost(client, "http://192.168.1.109:8989/gbjypx/service/gb/sendUser", headers, JSONObject.fromObject(map).toString(), "utf-8");
			System.out.println(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','失败,未联通干部教育培训库!');");
		}
		
		if("success".equals(result)){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','成功!');");
		}else{
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示','失败!');");
		}
	}
	
	private String isnullbak(Object obj){
		if(obj==null){
			return "";
		}else{
			return obj.toString();
		}
	}
	
	
	public static void main(String[] args) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		String a ="{\n" +
                "\t\"id\": \"321123\",\n" +
                "\t\"pid\": \"第一批\",\n" +
                "\t\"sq_date\": \"20190701\",\n" +
                "\t\"p_no\": \"2019080808\",\n" +
                "\t\"sq_org\": \"市委组织部\",\n" +
                "\t\"sq_user\": \"system\",\n" +
                "\t\"persion\": [{\n" +
                "\t\t\t\"user_id\": \"123\",\n" +
                "\t\t\t\"id_card\": \"234567890987654321\",\n" +
                "\t\t\t\"user_job\": \"地球国防部\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"user_id\": \"321\",\n" +
                "\t\t\t\"id_card\": \"234567890987654321\",\n" +
                "\t\t\t\"user_job\": \"地球国防部\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"user_id\": \"123321\",\n" +
                "\t\t\t\"id_card\": \"234567890987654321\",\n" +
                "\t\t\t\"user_job\": \"地球国防部\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";
		HttpClient client = HttpClientFactory.getInstance().create();
		try {
			Map<String, String> headers = new HashMap<String, String>();
			headers.put("accept","application/json");
			headers.put("content-type","application/json; charset=UTF-8");
		//	String encode = Base64.encodeBase64String(a.getBytes("utf-8"));
			String result=HttpUtils.excutePost(client, "http://localhost:8989/gbjypx/service/gb/sendUser", headers, a, "UTF-8");
			System.out.println(result);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@PageEvent("setColor")
	public int setColor(String num) throws RadowException {
		String rbId = this.getPageElement("rbId").getValue();
		HBSession sess = HBUtil.getHBSession();
		String js0100s="";
		List<String> list=new ArrayList<String>();
		//1是基本情况  判断满足的条件是 拟任职务或拟免职务不为空
		if("1".equals(num)) {
			 list=sess.createSQLQuery("select js0100 from JS01 where rb_id='"+rbId+"'  and (js0111 is not null  or  js0117 is not  null)").list(); 
        	
		}else if("2".equals(num)) {
			//2是动议 判断满足的条件是 空缺职位部位空
			list = sess.createSQLQuery("select js0100 from js02 where rb_id='" + rbId + "' and js0202 is not null").list();
		}else if("5".equals(num)) {
			//5是查核与听取意见 判断满足的条件是 工作事由不为空
			list = sess.createSQLQuery("select js0100 from js21 where rb_id='" + rbId + "' and js2108 is not null").list();
		}else if("6".equals(num)) {
			//6是民族推荐 判断满足的条件是推荐职位不为空
			list = sess.createSQLQuery("select js0100 from js20 where rb_id='" + rbId + "' and js2002 is not null").list();
		}else if("7".equals(num)) {
			//7是组织考察  判断满足的条件是考察时间不为空
			list = sess.createSQLQuery("select js0100 from js14 where rb_id='" + rbId + "'and js1402 is not null").list();
		}else if("8".equals(num)) {
			//8 是讨论决定 判断满足的条件是表决情况不为空
			list= sess.createSQLQuery("select js0100 from js15 where rb_id='" + rbId + "' and js1513 is not null").list();
		}else if("9".equals(num)) {
			//9是任前公示 判断满足的条件是公示形式不为空
			list = sess.createSQLQuery("select js0100 from js08 where rb_id='" + rbId + "' and js0806 is not null").list();
		}else if("10".equals(num)) {
			//10是发文登记 判断满足的条件是发文时间不为空
			list= sess.createSQLQuery("select js0100 from js23 where rb_id='" + rbId + "' and js2310 is not null group by js0100").list();
		} 
		for(String js0100:list) {
    		js0100s+=js0100+",";
    		
    	}
		if(!js0100s.equals("")) {
			js0100s=js0100s.substring(0,js0100s.length()-1);
			this.getPageElement("js0100s_finish").setValue(js0100s);
			this.getExecuteSG().addExecuteCode("showColor()");
		}else {
			this.getExecuteSG().addExecuteCode("setwhite()");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
