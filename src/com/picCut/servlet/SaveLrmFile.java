package com.picCut.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import bea.jolt.Session;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBTransaction;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ItemXml;
import com.insigma.siis.local.lrmx.PersonXml;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.util.FileUtil;
import com.insigma.siis.local.util.Sortutil;
import com.insigma.siis.local.util.up_down.IAfterFileUpload;

public class SaveLrmFile implements IAfterFileUpload {
	private static Logger log = Logger.getLogger(SaveLrmFile.class);
	private static Map<Integer,String> reg_map = new HashMap<Integer, String>();
	static {
			reg_map.put(1, "^\\d{4}.\\d{2}$"); //只有第一党派且为中共党员
			reg_map.put(2, "^\\([\u4e00-\u9fa5]{0,}\\)$"); //只有一个党派,且不为中共党员 
			reg_map.put(3, "^\\([\u4e00-\u9fa5]{0,}、[\u4e00-\u9fa5]{0,}\\)$"); //有两个党派,且第一党派不为中共党员
			reg_map.put(4, "^\\([\u4e00-\u9fa5]{0,}、[\u4e00-\u9fa5]{0,}、[\u4e00-\u9fa5]{0,}\\)$"); //有三个党派,且第一党派不为中共党员
			reg_map.put(5, "^\\d{6}$"); //中共党员:  “197204”
			reg_map.put(6, "^[\u4e00-\u9fa5]{0,}$"); //单个民主党派成员或无党派人士
			reg_map.put(7, "^[\u4e00-\u9fa5]{0,}；[\u4e00-\u9fa5]{0,}$"); //两个民主党派,各项之间用分号隔开,如:	“民建；民盟”
			reg_map.put(8, "^[\u4e00-\u9fa5]{0,}；[\u4e00-\u9fa5]{0,}；[\u4e00-\u9fa5]{0,}$"); //三个民主党派,各项之间用分号隔开,如:	“九三；民建；民盟”
			reg_map.put(9, "^\\d{6}；[\u4e00-\u9fa5]{0,}$"); //即是民主党派成员又是中共党员,先填写加入中共时间,分号隔开后再填写民主党派名称,如:		“200108；民建”
			reg_map.put(10, "^\\d{6}；[\u4e00-\u9fa5]{0,}；[\u4e00-\u9fa5]{0,}$"); //即是民主党派成员又是中共党员,先填写加入中共时间,分号隔开后再填写民主党派名称
			reg_map.put(11, "^[\u4e00-\u9fa5]{0,}\\d{4}.\\d{2}$"); //即是民主党派成员又是中共党员,先填写民主党派名称,,再填写加入中共时间,如:		“民建2001.08”
			reg_map.put(12, "^[\u4e00-\u9fa5]{0,}\\d{6}$"); //即是民主党派成员又是中共党员,先填写民主党派名称,,再填写加入中共时间,如:		“民建200108”
	}
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
		 }

	public Object DoSomethingElse(List<HashMap<String, Object>> list)
			throws Exception {
		Object obj = null;
		if (list.get(0).get("method") == null||list.get(0).get("method").equals("Again")) {
			for (HashMap<String, Object> map : list) {
				String fullpath = map.get("attachmentadds").toString();
				String a0000 = map.get("a0000").toString();
				Object method = list.get(0).get("method")==null?"":"Again";
				log.info(fullpath);
				File file = new File(fullpath);
				PersonXml personXml = null;
				if (file.exists()) {
					if (fullpath.endsWith(".lrmx")) {
						String xmlStr = FileUtil.read6String(file);
						personXml = (PersonXml) JXUtil.Xml2Object(xmlStr,
								PersonXml.class);
						obj = saveLrmx(personXml, (String) list.get(0).get("a0201b"),(String)method);

					} else {
						if (fullpath.endsWith(".lrm")) {
							String xmlStr = FileUtil.read4String(file);
							obj = saveLrm(xmlStr,
									(String) list.get(0).get("a0201b"), a0000,(String) method);
							if(obj!=null&&obj.toString().startsWith("updateUpLoad")){
								return obj;
							}
						}
						if (fullpath.endsWith(".pic")||fullpath.endsWith(".jpg")) {
							savePic(file, a0000);
						}
					}
				}
				file.delete();
			}
		} else if(list.get(0).get("method").equals("Update")){
			String xmlStr = "";
			for (HashMap<String, Object> map : list) {
				String fullpath = map.get("attachmentadds").toString();
				String a0000 = map.get("a0000").toString();
				Object method = list.get(0).get("method")==null?"":"Again";
				log.info(fullpath);
				File file = new File(fullpath);
				PersonXml personXml = null;
				if (file.exists()) {
					if (fullpath.endsWith(".lrmx")) {
						String xmlString = FileUtil.read6String(file);
						personXml = (PersonXml) JXUtil.Xml2Object(xmlString,
								PersonXml.class);
						obj = updateLrmx(personXml, (String) list.get(0).get("a0201b"));

					} else {
						if (fullpath.endsWith(".lrm")) {
							 xmlStr = FileUtil.read4String(file);
							obj = updateLrm(xmlStr,
									(String) list.get(0).get("a0201b"));
						}
						if (fullpath.endsWith(".pic")||fullpath.endsWith(".jpg")) {
							updatePic(file,xmlStr);
						}
					}
				}
				file.delete();
			}
		}
		return obj;
	}

	public static String updateLrm(String str, String a0201b) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = null;
		str = str.substring(1, str.length());
		try {
			trans = sess.beginTransaction();
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String loginnname = user.getLoginname();// 操作人工号
			Calendar cal = Calendar.getInstance();
			String[] value = str.split("\",\"");
			String value2 = "";
			String value0 = "";
			String sql = "";
			if(!StringUtil.isEmpty(value[2])&&value[2].length()>5){
				value2 = value[2].substring(0, 6);
				value0 = value[0];
				if(DBUtil.getDBType()==DBType.ORACLE){
					sql = "from A01 t where t.a0101 = '" + value0 + "' and substr(t.a0107,0,6)='"+value2+"' ";
				}else{
					sql = "from A01 t where t.a0101 = '" + value0 + "' and substring(t.a0107,1,6) = '"+value2+"' ";
				}
			}else{
				value0 = value[0];
				if(DBUtil.getDBType()==DBType.ORACLE){
					sql = "from A01 t where t.a0101 = '" + value0 + "' ";
				}else{
					sql = "from A01 t where t.a0101 = '" + value0 + "' ";
				}
			}
			List<A01> list = sess.createQuery(sql).list();
			A01 a01s = list.get(0);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					A01 a01ToDelete = list.get(i);
					String a0000ToDelete = a01ToDelete.getA0000();
					HBUtil.executeUpdate("delete from a02 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a06 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a08 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a14 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a15 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a36 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a11 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a29 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a53 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a37 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a31 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a30 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a57 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a01 where a0000='"+a0000ToDelete+"'");
				}
			}
			String a0000 = a01s.getA0000();
			List<String> a0200s = sess.createSQLQuery("select a0200 from A02 where a0000 = '"+a0000+"' ").list();
			for(int i=0;i<a0200s.size();i++){
				sess.createSQLQuery("delete from A02 where a0200 = '"+a0200s.get(i)+"'").executeUpdate();
			}
			List<String> list2 = sess.createSQLQuery("select a3600 from A36 where a0000 = '"+a0000+"'").list();
			sess.getSession().evict(a01s);
			A01 a01 = new A01();
			A02 a02 = new A02();
			A06 a06 = new A06();
			A53 a53 = new A53();
			a01.setA0000(a0000);
			a01.setStatus("1");
			a01.setA0163("1");
			a01.setTbr(user.getId());
			a01.setTbsj(DateUtil.getTimestamp().getTime());
			// a01.setA0180("导入LRM文件");
			a01.setA0180("");
			a01.setA0101(value[0]);
			a01.setA0104(codeValue("GB2261", value[1]));
			a01.setA0104a(value[1]);
			a01.setA0107(value[2]);
			if (!StringUtil.isEmpty(value[2])) {
				int birthYear = Integer.parseInt(value[2].substring(0, 4));
				int year = cal.get(Calendar.YEAR);// 得到当前年
				Long age = (long) year - birthYear;
				a01.setAge(age);
			}
			a01.setA0117(codeValue("GB3304", value[3]));
			a01.setA0117a(value[3]);
			a01.setA0111(codeValue2("ZB01", value[4]));
			a01.setComboxArea_a0111(value[4]);
			
			//入党时间反填
			String rdsj = value[5];
			if(StringUtil.isEmpty(rdsj)){
				a01.setA0140(rdsj);
			}else{
/*				if(isNumeric(rdsj)){
					a01.setA0140(rdsj.substring(0,4)+"."+rdsj.substring(4,6));
				}else{
					a01.setA0140(rdsj);
				}*/
				rdsjAnalyze(rdsj, a01);
				a01.setA0140(rdsj);
			}
			a01.setA0128(value[6]);
			a01.setA0114(codeValue2("ZB01", value[7]));
			a01.setComboxArea_a0114(value[7]);
			a01.setA0134(value[8]);
			String[] a = value[9].split("@");
			if (a[0].length() > 1) {
				String[] b = a[0].split("#");
				if (b[0].length() > 0) {
					a01.setQrzxl(b[0]);
				}
				if (b.length > 1) {
					a01.setQrzxw(b[1]);
				}
			}
			if (a[1].length() > 1) {
				String[] b = a[1].split("#");
				if (b[0].length() > 0) {
					a01.setZzxl(b[0]);
				}
				if (b.length > 1) {
					a01.setZzxw(b[1]);
				}
			}
			String[] c = value[10].split("@");
			if (c[0].length() > 1) {
				String[] b = c[0].split("#");
				if (b[0].length() > 0) {
					a01.setQrzxlxx(b[0]);
				}
				if (b.length > 1) {
					a01.setQrzxwxx(b[1]);
				}
			}
			if (c[1].length() > 1) {
				String[] b = c[1].split("#");
				if (b[0].length() > 0) {
					a01.setZzxlxx(b[0]);
				}
				if (b.length > 1) {
					a01.setZzxwxx(b[1]);
				}
			}
			a01.setA0196(value[11]);
			a01.setA0187a(value[16]);
			a01.setA1701(value[17]);
			a01.setA14z101(value[18]);
			a01.setA15z101(value[19]);
			a01.setA0192a(value[25]);
			a01.setRmly(value[28]);
			a01.setA0195(A0195(a0201b));
			// a01.setTbrjg(SysManagerUtils.getUserOrgid());
			sess.saveOrUpdate(a01);
			
			//考核反填
			try{
				String khxx = a01.getA15z101().replace("；", ";").trim();
				if(khxx.contains(";")){		//多条
					String[] khxx_arr = khxx.split(";");
					for(int i = 0; i < khxx_arr.length; i++){
						String khxx_str = khxx_arr[i].trim();
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(khxx_str);
						String a1521 = m.replaceAll("").trim();
						if(a1521.length() > 4 || a1521.length() < 4){
							continue;
						}
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx_str.contains("基本称职")){
							a1517 = "3";
						}else if(khxx_str.contains("不称职")){
							a1517 = "4";
						}else if(khxx_str.contains("优秀")){
							a1517 = "1";
						}else if(khxx_str.contains("称职")){
							a1517 = "2";
						}else if(khxx_str.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx_str.contains("不合格")){
							a1517 = "4B";
						}else if(khxx_str.contains("合格")){
							a1517 = "2B";
						}else if(khxx_str.contains("不定等次")){
							a1517 = "5";
						}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx_str.contains("不进行考核")){
							a1517 = "6";
						}else if(khxx_str.contains("其他")){
							a1517 = "7";
						}
						a15.setA1517(a1517);
						a15.setA1527("" + khxx_arr.length);
						sess.save(a15);
					}
				}else{				//单条
					String regEx="[^0-9]";   
					Pattern p = Pattern.compile(regEx);   
					Matcher m = p.matcher(khxx);
					String a1521 = m.replaceAll("").trim();
					if(a1521.length() > 4 || a1521.length() < 4){
						
					}else{
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx.contains("基本称职")){
							a1517 = "3";
						}else if(khxx.contains("不称职")){
							a1517 = "4";
						}else if(khxx.contains("优秀")){
							a1517 = "1";
						}else if(khxx.contains("称职")){
							a1517 = "2";
						}else if(khxx.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx.contains("不合格")){
							a1517 = "4B";
						}else if(khxx.contains("合格")){
							a1517 = "2B";
						}else if(khxx.contains("不定等次")){
							a1517 = "5";
						}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx.contains("不进行考核")){
							a1517 = "6";
						}else if(khxx.contains("其他")){
							a1517 = "7";
						}
						a15.setA1517(a1517);
						a15.setA1527("1");
						sess.save(a15);
					}
				}
			}catch(Exception e){
				
			}
			
			//奖惩信息反填
			try{
				String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
				if(jcxx.contains(";")){		//多条
					String[] jcxx_arr = jcxx.split(";");
					for(int i = 0; i < jcxx_arr.length; i++){
						String jcxx_str = jcxx_arr[i].trim();
						String[] jcxx_str_arr = jcxx_str.split(",");
						String pzsj = jcxx_str_arr[0];
						String pzjg = jcxx_str_arr[1];
						String jcmc = jcxx_str_arr[2];
						A14 a14 = new A14();
						a14.setA0000(a0000);
						//处理时间字符串中年月日和小数点
						a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
						//处理批准机关名称
						a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
						//处理奖惩名称和代码
						a14.setA1404a(jcmc.replace("。", ""));
						a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
						sess.save(a14);
					}
				}else{			//单条
					String[] jcxx_str_arr = jcxx.split(",");
					String pzsj = jcxx_str_arr[0];
					String pzjg = jcxx_str_arr[1];
					String jcmc = jcxx_str_arr[2];
					A14 a14 = new A14();
					a14.setA0000(a0000);
					//处理时间字符串中年月日和小数点
					a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
					//处理批准机关名称
					a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
					//处理奖惩名称和代码
					a14.setA1404a(jcmc.replace("。", ""));
					a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
					sess.save(a14);
				}
			}catch(Exception e){	//出现异常则跳过奖惩反填
				
			}
			
			//学历学位反填
			String qzrxl = a01.getQrzxl();
			String qzrxw = a01.getQrzxw();
			String qzrxlxx = a01.getQrzxlxx();
			String qzrxwxx = a01.getQrzxwxx();
			String zzxl = a01.getZzxl();
			String zzxw = a01.getZzxw();
			String zzxlxx = a01.getZzxlxx();
			String zzxwxx = a01.getZzxwxx();
			if(qzrxl != null && !"".equals(qzrxl)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0801a(qzrxl);
				a08.setA0801b(codeValue("ZB64",qzrxl));
				a08.setA0814(qzrxlxx);
				a08.setA0899("true");
				a08.setA0837("1");
				sess.save(a08);
			}
			if(qzrxw != null && !"".equals(qzrxw)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0901a(qzrxw);
				a08.setA0901b(codeValue("GB6864",qzrxw));
				if(qzrxwxx != null && !"".equals(qzrxwxx)){
					a08.setA0814(qzrxwxx);
				}else{
					a08.setA0814(qzrxlxx);
				}
				a08.setA0899("true");
				a08.setA0837("1");
				sess.save(a08);
			}
			if(zzxl != null && !"".equals(zzxl)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0801a(zzxl);
				a08.setA0801b(codeValue("ZB64",zzxl));
				a08.setA0814(zzxlxx);
				a08.setA0899("true");
				a08.setA0837("2");
				sess.save(a08);
			}
			if(zzxw != null && !"".equals(zzxw)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0901a(zzxw);
				a08.setA0901b(codeValue("GB6864",zzxw));
				if(zzxwxx != null && !"".equals(zzxwxx)){
					a08.setA0814(zzxwxx);
				}else{
					a08.setA0814(zzxlxx);
				}
				a08.setA0899("true");
				a08.setA0837("2");
				sess.save(a08);
			}
			
			//专业技术反填
			if(value[11] != null && !"".equals(value[11])){
				a06.setA0000(a0000);
				a06.setA0601(A0601(value[11]));
				a06.setA0602(value[11]);
				a06.setA0699("true");
				sess.saveOrUpdate(a06);
			}
			
			//职务反填
			if (!"".equals(a0201b)) {
				a02.setA0000(a0000);
				String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
				String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
				a02.setA0201a(a0201a);
				a02.setA0201b(a0201b);
				a02.setA0255("1");
				a02.setA0281("true");
				a02.setA0279("1");
				a02.setA0219("2");
				a02.setA0201d("0");
				a02.setA0251b("0");
				a02.setA0215a(value[25]);
				sess.save(a02);
				CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
			} else {
				a02.setA0000(a0000);
				a02.setA0201a("其他单位");
				a02.setA0201b("-1");
				a02.setA0255("1");
				a02.setA0281("true");
				a02.setA0279("1");
				a02.setA0219("2");
				a02.setA0201d("0");
				a02.setA0251b("0");
				a02.setA0215a(value[25]);
				sess.save(a02);
				CreateSysOrgBS.updateB01UpdatedWithZero("-1");
			}
			a53.setA0000(a0000);
			a53.setA5304(value[26]);
			a53.setA5315(value[27]);
			a53.setA5317(value[28]);
			a53.setA5319(value[29]);
			a53.setA5321(value[30]);
			value[32] = value[32].replace("\"", "");
			if (value[32].length() > 0) {
				a53.setA5323(value[32].length() == 2 ? "" : value[32]
						.substring(0, 8));
			}
			a53.setA5327(user.getName());
			a53.setA5399(user.getId());
			sess.saveOrUpdate(a53);
			if (value[20].length() > 12) {
				String[] b = value[20].split("@");
				String[] d = value[21].split("@");
				String[] e = value[22].split("@");
				String[] f = value[23].split("@");
				String[] g = value[24].split("@");
				//add chenys
				//获取家庭成员称谓 存入list中
				List<String> listCw = new ArrayList<String>();
				for (int i = 0; i < b.length; i++) {
					listCw.add(b[i]);
				}
				//计算各个称谓排序值
				List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
				for (int i = 0; i < b.length; i++) {
					A36 a36 = new A36();
					a36.setA0000(a0000);
					if(!StringUtil.isEmpty(list2.get(i))){
						a36.setA3600(list2.get(i));
					}
					//从map中取出当前称谓对应的序号
					a36.setSortid(sortList.get(i));
					if (b[i].length() > 1) {
						a36.setA3604a( b[i]);
					}
					if (d.length > i && !StringUtil.isEmpty(d[i])) {
						a36.setA3601(d[i]);
					}
					if (e.length > i && !StringUtil.isEmpty(e[i])) {
						a36.setA3607(e[i]);
					}
					if (f.length > i && !StringUtil.isEmpty(f[i])) {
						a36.setA3627(f[i]);
					}
					if (g.length > i && !StringUtil.isEmpty(g[i])) {
						a36.setA3611(g[i]);
					}
					sess.saveOrUpdate(a36);
				}
			}
			A01 a01log = new A01();
			new LogUtil().createLog("34", "A01", a01log.getA0000(), a01log.getA0101(),
					"LRM导入", new Map2Temp().getLogInfo(new A01(), a01log));
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	public static String updateLrmx(PersonXml personXml, String a0201b)
			throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = sess.beginTransaction();
		try {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String loginnname = user.getLoginname();// 操作人工号
			Calendar cal = Calendar.getInstance();
			String value2 = "";
			String value0 = "";
			String sql = "";
			if(!StringUtil.isEmpty(personXml.getChuShengNianYue())&&personXml.getChuShengNianYue().length()>5){
				value2 = personXml.getChuShengNianYue().substring(0, 6);
				value0 = personXml.getXingMing();
				if(DBUtil.getDBType()==DBType.ORACLE){
					sql = "from A01 t where t.a0101 = '" + value0 + "' and substr(t.a0107,0,6)='"+value2+"' ";
				}else{
					sql = "from A01 t where t.a0101 = '" + value0 + "' and substring(t.a0107,1,6) = '"+value2+"' ";
				}
			}else{
				value0 = personXml.getXingMing();
				if(DBUtil.getDBType()==DBType.ORACLE){
					sql = "from A01 t where t.a0101 = '" + value0 + "' ";
				}else{
					sql = "from A01 t where t.a0101 = '" + value0 + "' ";
				}
			}
			List<A01> list = sess.createQuery(sql).list();
			A01 a01s = list.get(0);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					A01 a01ToDelete = list.get(i);
					String a0000ToDelete = a01ToDelete.getA0000();
					HBUtil.executeUpdate("delete from a02 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a05 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a99z1 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a06 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a08 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a14 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a15 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a36 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a11 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a29 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a53 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a37 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a31 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a30 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a57 where a0000='"+a0000ToDelete+"'");
					HBUtil.executeUpdate("delete from a01 where a0000='"+a0000ToDelete+"'");
				}
			}
			String a0000 = a01s.getA0000();
			List<String> a0200s = sess.createSQLQuery("select a0200 from A02 where a0000 = '"+a0000+"' ").list();
			for(int i=0;i<a0200s.size();i++){
				sess.createSQLQuery("delete from A02 where a0200 = '"+a0200s.get(i)+"'").executeUpdate();
			}
			List<String> list2 = sess.createSQLQuery("select a3600 from A36 where a0000 = '"+a0000+"'").list();
			sess.getSession().evict(a01s);
			A01 a01 = new A01();
			A02 a02 = new A02();
			A06 a06 = new A06();
			A53 a53 = new A53();
			a01.setA0000(a0000);
			a01.setTbr(user.getId());
			a01.setTbsj(DateUtil.getTimestamp().getTime());
			// a01.setA0180("导入LRMX文件");
			a01.setA0180("");
			a01.setA0101(personXml.getXingMing());
			if (personXml.getXingBie() == null) {
				a01.setA0104("");
				a01.setA0104a("");
			} else {
				a01.setA0104(personXml.getXingBie().equals("男") ? "1" : "2");
				a01.setA0104a(personXml.getXingBie());
			}
			a01.setA0107(personXml.getChuShengNianYue());
			if (!StringUtil.isEmpty(personXml.getChuShengNianYue())) {
				int birthYear = Integer.parseInt(personXml.getChuShengNianYue()
						.substring(0, 4));
				int year = cal.get(Calendar.YEAR);// 得到当前年
				Long age = (long) year - birthYear;
				a01.setAge(age);
			}
			a01.setA0117(codeValue("GB3304", personXml.getMinZu()));
			a01.setA0117a(personXml.getMinZu());
			a01.setA0111(codeValue2("ZB01", personXml.getJiGuan()));
			a01.setComboxArea_a0111(personXml.getJiGuan());
			a01.setA0114(codeValue2("ZB01", personXml.getChuShengDi()));
			a01.setComboxArea_a0114(personXml.getChuShengDi());
			
			//入党时间反填
			String rdsj = personXml.getRuDangShiJian();
			if(StringUtil.isEmpty(personXml.getRuDangShiJian())){
				a01.setA0140(rdsj);
			}else{
/*				if(isNumeric(rdsj)){
					a01.setA0140(personXml.getRuDangShiJian().substring(0,4)+"."+personXml.getRuDangShiJian().substring(4,6));
				}else{
					a01.setA0140(rdsj);
				}*/
				rdsjAnalyze(rdsj, a01);
				a01.setA0140(rdsj);
			}
			a01.setA0134(personXml.getCanJiaGongZuoShiJian());
			a01.setA0128(personXml.getJianKangZhuangKuang());
			a01.setA0196(personXml.getZhuanYeJiShuZhiWu());
			a01.setA0187a(personXml.getShuXiZhuanYeYouHeZhuanChang());
			a01.setQrzxl(personXml.getQuanRiZhiJiaoYu_XueLi());
			a01.setQrzxw(personXml.getQuanRiZhiJiaoYu_XueWei());
			a01.setQrzxlxx(personXml.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
			a01.setQrzxwxx(personXml.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
			a01.setZzxl(personXml.getZaiZhiJiaoYu_XueLi());
			a01.setZzxw(personXml.getZaiZhiJiaoYu_XueWei());
			a01.setZzxlxx(personXml.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
			a01.setZzxwxx(personXml.getZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
			a01.setA0192a(personXml.getXianRenZhiWu());
			a01.setA0192(personXml.getXianRenZhiWu());
			a01.setA1701(personXml.getJianLi());
			a01.setA14z101(personXml.getJiangChengQingKuang());
			a01.setA15z101(personXml.getNianDuKaoHeJieGuo());
			a01.setRmly(personXml.getRenMianLiYou());
			a01.setTbrjg(SysManagerUtils.getUserOrgid());
			a01.setA0184(personXml.getShenFenZheng());
			a01.setStatus("1");
			a01.setA0163("1");
			a01.setA0195(A0195(a0201b));
			sess.saveOrUpdate(a01);
			
			//考核反填
			try{
				String khxx = a01.getA15z101().replace("；", ";").trim();
				if(khxx.contains(";")){		//多条
					String[] khxx_arr = khxx.split(";");
					for(int i = 0; i < khxx_arr.length; i++){
						String khxx_str = khxx_arr[i].trim();
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(khxx_str);
						String a1521 = m.replaceAll("").trim();
						if(a1521.length() > 4 || a1521.length() < 4){
							continue;
						}
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx_str.contains("基本称职")){
							a1517 = "3";
						}else if(khxx_str.contains("不称职")){
							a1517 = "4";
						}else if(khxx_str.contains("优秀")){
							a1517 = "1";
						}else if(khxx_str.contains("称职")){
							a1517 = "2";
						}else if(khxx_str.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx_str.contains("不合格")){
							a1517 = "4B";
						}else if(khxx_str.contains("合格")){
							a1517 = "2B";
						}else if(khxx_str.contains("不定等次")){
							a1517 = "5";
						}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx_str.contains("其他原因不进行考核")){
							a1517 = "6B";
						}else if(khxx_str.contains("不进行考核")){
							a1517 = "6";
						}
						a15.setA1517(a1517);
						a15.setA1527("" + khxx_arr.length);
						sess.save(a15);
					}
				}else{				//单条
					String regEx="[^0-9]";   
					Pattern p = Pattern.compile(regEx);   
					Matcher m = p.matcher(khxx);
					String a1521 = m.replaceAll("").trim();
					if(a1521.length() > 4 || a1521.length() < 4){
						
					}else{
						A15 a15 = new A15();
						a15.setA0000(a0000);
						a15.setA1521(a1521);
						String a1517 = "";
						if(khxx.contains("基本称职")){
							a1517 = "3";
						}else if(khxx.contains("不称职")){
							a1517 = "4";
						}else if(khxx.contains("优秀")){
							a1517 = "1";
						}else if(khxx.contains("称职")){
							a1517 = "2";
						}else if(khxx.contains("基本合格")){
							a1517 = "3B";
						}else if(khxx.contains("不合格")){
							a1517 = "4B";
						}else if(khxx.contains("合格")){
							a1517 = "2B";
						}else if(khxx.contains("不定等次")){
							a1517 = "5";
						}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
							a1517 = "5A";
						}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
							a1517 = "5B";
						}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
							a1517 = "5C";
						}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
							a1517 = "6A";
						}else if(khxx.contains("其他原因不进行考核")){
							a1517 = "6B";
						}else if(khxx.contains("不进行考核")){
							a1517 = "6";
						}
						a15.setA1517(a1517);
						a15.setA1527("1");
						sess.save(a15);
					}
				}
			}catch(Exception e){
				
			}
			
			//奖惩信息反填
			try{
				String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
				if(jcxx.contains(";")){		//多条
					String[] jcxx_arr = jcxx.split(";");
					for(int i = 0; i < jcxx_arr.length; i++){
						String jcxx_str = jcxx_arr[i].trim();
						String[] jcxx_str_arr = jcxx_str.split(",");
						String pzsj = jcxx_str_arr[0];
						String pzjg = jcxx_str_arr[1];
						String jcmc = jcxx_str_arr[2];
						A14 a14 = new A14();
						a14.setA0000(a0000);
						//处理时间字符串中年月日和小数点
						a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
						//处理批准机关名称
						a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
						//处理奖惩名称和代码
						a14.setA1404a(jcmc.replace("。", ""));
						a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
						sess.save(a14);
					}
				}else{			//单条
					String[] jcxx_str_arr = jcxx.split(",");
					String pzsj = jcxx_str_arr[0];
					String pzjg = jcxx_str_arr[1];
					String jcmc = jcxx_str_arr[2];
					A14 a14 = new A14();
					a14.setA0000(a0000);
					//处理时间字符串中年月日和小数点
					a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
					//处理批准机关名称
					a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
					//处理奖惩名称和代码
					a14.setA1404a(jcmc.replace("。", ""));
					a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
					sess.save(a14);
				}
			}catch(Exception e){	//出现异常则跳过奖惩反填
				
			}
			
			//学历学位反填
			String qzrxl = a01.getQrzxl();
			String qzrxw = a01.getQrzxw();
			String qzrxlxx = a01.getQrzxlxx();
			String qzrxwxx = a01.getQrzxwxx();
			String zzxl = a01.getZzxl();
			String zzxw = a01.getZzxw();
			String zzxlxx = a01.getZzxlxx();
			String zzxwxx = a01.getZzxwxx();
			if(qzrxl != null && !"".equals(qzrxl)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0801a(qzrxl);
				a08.setA0801b(codeValue("ZB64",qzrxl));
				a08.setA0814(qzrxlxx);
				/*a08.setA0899("true");
				a08.setA0837("1");
				sess.save(a08);
			}
			if(qzrxw != null && !"".equals(qzrxw)){
				A08 a08 = new A08();
				a08.setA0000(a0000);*/
				a08.setA0901a(qzrxw);
				a08.setA0901b(codeValue("GB6864",qzrxw));
				if(qzrxwxx != null && !"".equals(qzrxwxx)){
					a08.setA0814(qzrxwxx);
				}else{
					a08.setA0814(qzrxlxx);
				}
				a08.setA0899("true");
				a08.setA0837("1");
				sess.save(a08);
			}
			if(zzxl != null && !"".equals(zzxl)){
				A08 a08 = new A08();
				a08.setA0000(a0000);
				a08.setA0801a(zzxl);
				a08.setA0801b(codeValue("ZB64",zzxl));
				a08.setA0814(zzxlxx);
				/*a08.setA0899("true");
				a08.setA0837("2");
				sess.save(a08);
			}
			if(zzxw != null && !"".equals(zzxw)){
				A08 a08 = new A08();
				a08.setA0000(a0000);*/
				a08.setA0901a(zzxw);
				a08.setA0901b(codeValue("GB6864",zzxw));
				if(zzxwxx != null && !"".equals(zzxwxx)){
					a08.setA0814(zzxwxx);
				}else{
					a08.setA0814(zzxlxx);
				}
				a08.setA0899("true");
				a08.setA0837("2");
				sess.save(a08);
			}
			
			//专业技术反填
			if(personXml.getZhuanYeJiShuZhiWu() != null && !"".equals(personXml.getZhuanYeJiShuZhiWu())){
				a06.setA0000(a0000);
				a06.setA0601(A0601(personXml.getZhuanYeJiShuZhiWu()));
				a06.setA0602(personXml.getZhuanYeJiShuZhiWu());
				a06.setA0699("true");
				sess.saveOrUpdate(a06);
			}
			
			//职务反填
			if (!"".equals(a0201b)) {
				a02.setA0000(a0000);
				String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
				String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
				a02.setA0201a(a0201a);
				a02.setA0201b(a0201b);
				a02.setA0255("1");
				a02.setA0281("true");
				a02.setA0279("1");
				a02.setA0219("2");
				a02.setA0201d("0");
				a02.setA0251b("0");
				a02.setA0215a(personXml.getXianRenZhiWu());
				sess.save(a02);
				CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
			} else {
				a02.setA0000(a0000);
				a02.setA0201a("其他单位");
				a02.setA0201b("-1");
				a02.setA0255("1");
				a02.setA0281("true");
				a02.setA0279("1");
				a02.setA0219("2");
				a02.setA0201d("0");
				a02.setA0251b("0");
				a02.setA0215a(personXml.getXianRenZhiWu());
				sess.save(a02);
				CreateSysOrgBS.updateB01UpdatedWithZero("-1");
			}
			a53.setA0000(a0000);
			a53.setA5304(personXml.getNiRenZhiWu());
			a53.setA5315(personXml.getNiMianZhiWu());
			a53.setA5317(personXml.getRenMianLiYou());
			a53.setA5319(personXml.getChengBaoDanWei());
			a53.setA5321(personXml.getJiSuanNianLingShiJian());
			a53.setA5323(personXml.getTianBiaoShiJian());
			a53.setA5327(user.getName());
			a53.setA5399(user.getId());
			sess.saveOrUpdate(a53);
			if (personXml.getZhaoPian() != null) {
				A57 a57 = new A57();
				a57.setA0000(a0000);
				PhotosUtil.savePhotoData(a57, sess, personXml.getZhaoPian());
			}

			if (personXml.getJiaTingChengYuan() != null
					&& personXml.getJiaTingChengYuan().size() > 0
					&& personXml.getJiaTingChengYuan().get(0) != null
					&& personXml.getJiaTingChengYuan().get(0).getItem() != null
					&& personXml.getJiaTingChengYuan().get(0).getItem().size() > 0) {
				//add chenys
				//获取家庭成员称谓 存入list中
				List listCw = new ArrayList();
				for (int i = 0; i < personXml.getJiaTingChengYuan().get(0).getItem().size(); i++) {
					ItemXml ix = personXml.getJiaTingChengYuan().get(0).getItem().get(i);
					if (ix != null) {
						listCw.add(ix.getChengWei());
					}
				}
				//计算各个称谓排序值
				List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
				for (int i = 0; i < personXml.getJiaTingChengYuan().get(0)
						.getItem().size(); i++) {
					ItemXml ix = personXml.getJiaTingChengYuan().get(0)
							.getItem().get(i);
					if (ix != null) {
							A36 a36 = (A36) sess.get(A36.class, list2.get(i));
							if(a36 == null){
								a36 = new A36();
							}
							a36.setA0000(a0000);
							a36.setA3601(ix.getXingMing());
							/*a36.setA3604a(codeValue("GB4761", ix.getChengWei()));*/
							a36.setA3604a(ix.getChengWei());
							a36.setA3607(ix.getChuShengRiQi());
							a36.setA3611(ix.getGongZuoDanWeiJiZhiWu());
							//a36.setA3627(codeValue("GB4762",ix.getZhengZhiMianMao()));
							a36.setA3627(ix.getZhengZhiMianMao());
							a36.setSortid(sortList.get(i));
							sess.saveOrUpdate(a36);
					}
				}
			}
			A01 a01log = new A01();
			new LogUtil().createLog("35", "A01", a01log.getA0000(), a01log.getA0101(),
					"LRMX导入", new Map2Temp().getLogInfo(new A01(), a01log));
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
//			throw new Exception(e.getMessage());
		}
		return null;
	}
	
	public static void updatePic(File file,String str) throws Exception {
		if(StringUtil.isEmpty(str)){
			throw new AppException("未检测到lrm格式文件");
		}
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = null;
		str = str.substring(1, str.length());
		try {
			trans = sess.beginTransaction();
			String[] value = str.split("\",\"");
			String value2 = value[2].substring(0, 6);
			String sql="";
			if(DBUtil.getDBType()==DBType.ORACLE){
				sql = "from A01 t where t.a0101 = '" + value[0] + "' and substr(t.a0107,0,6)='"+value2+"' and t.a0184 is null";
			}else{
				sql = "from A01 t where t.a0101 = '" + value[0] + "' and substring(t.a0107,1,6) = '"+value2+"' and t.a0184 is null";
			}
			List<A01> list = sess.createQuery(sql).list();
			A01 a01s = list.get(0);
			String a0000 = a01s.getA0000();
			sess.getSession().evict(a01s);
			long fileSize = file.length();
			if (fileSize > Integer.MAX_VALUE) {
				CommonQueryBS.systemOut("file too big...");
			}
			FileInputStream fi;
			fi = new FileInputStream(file);
			byte[] buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length
					&& (numRead = fi.read(buffer, offset, buffer.length
							- offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset != buffer.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			fi.close();
			A57 a57 = new A57();
			a57.setA0000(a0000);
			PhotosUtil.savePhotoData(a57, sess, buffer);
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw new Exception(e.getMessage());
		}
	}

	public static String saveLrm(String str, String a0201b, String a0000,String method)
			throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = null;
		str = str.substring(1, str.length());
		try {
			trans = sess.beginTransaction();
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			Calendar cal = Calendar.getInstance();
			String[] value = str.split("\",\"");
			if(!method.equals("Again")){
				if (!StringUtil.isEmpty(value[0])) {
					String value2 = "";
					String value0 = "";
					String sql = "";
					if(!StringUtil.isEmpty(value[2])&&value[2].length()>5){
						//value2 = value[2].replace(".", "").substring(0, 6);
						value2 = value[2].replace(".", "");
						value0 = value[0];
						if(DBUtil.getDBType()==DBType.ORACLE){
							/*sql = "from A01 t where t.a0101 = '" + value0 + "' and substr(t.a0107,0,6)='"+value2+"' ";*/
							sql = "from A01 t where t.a0101 = '" + value0 + "' and t.a0107 = '"+value2+"' ";
						}else{
							//sql = "from A01 t where t.a0101 = '" + value0 + "' and substring(t.a0107,1,6) = '"+value2+"' ";
							sql = "from A01 t where t.a0101 = '" + value0 + "' and t.a0107 = '"+value2+"' ";
						}
					}else{
						value0 = value[0];
						if(DBUtil.getDBType()==DBType.ORACLE){
							sql = "from A01 t where t.a0101 = '" + value0 + "' ";
						}else{
							sql = "from A01 t where t.a0101 = '" + value0 + "' ";
						}
					}
					List<A01> list = sess.createQuery(sql).list();
					if (list.size() > 0) {
						return "updateUpLoad('" + value[0] + "');";
					}else{
						A01 a01 = new A01();
						A02 a02 = new A02();
						A06 a06 = new A06();
						A53 a53 = new A53();
						A37 a37 = new A37();
						a37.setA0000(a0000);
						sess.save(a37);
						a01.setA0000(a0000);
						a01.setStatus("1");
						a01.setA0163("1");
						a01.setTbr(user.getId());
						a01.setTbsj(DateUtil.getTimestamp().getTime());
						// a01.setA0180("导入LRM文件");
						a01.setA0180("");
						a01.setA0101(value[0]);
						a01.setA0104(codeValue("GB2261", value[1]));
						a01.setA0104a(value[1]);
						//a01.setA0107(value[2]);
						a01.setA0107(value[2].replace(".", ""));
						if (!StringUtil.isEmpty(value[2])) {
							int birthYear = Integer.parseInt(value[2].substring(0, 4));
							int year = cal.get(Calendar.YEAR);// 得到当前年
							Long age = (long) year - birthYear;
							a01.setAge(age);
						}
						a01.setA0117(codeValue("GB3304", value[3]));
						a01.setA0117a(value[3]);
						a01.setA0111(codeValue2("ZB01", value[4]));
						a01.setComboxArea_a0111(value[4]);
					
						//入党时间反填
						String rdsj = value[5];
						//如果没有加'.'，则先加上
						if(rdsj!=null && rdsj.matches("^\\d{6}$")){
							rdsj = rdsj.substring(0, 4)+"."+rdsj.substring(4,6);
						}
						if(StringUtil.isEmpty(rdsj)){
							a01.setA0140(rdsj);
						}else{
/*							if(isNumeric(rdsj)){
								a01.setA0140(rdsj.substring(0,4)+"."+rdsj.substring(4,6));
							}else{
								a01.setA0140(rdsj);
							}*/
							rdsjAnalyze(rdsj, a01);
							a01.setA0140(rdsj);
						}
						a01.setA0128(value[6]);
						a01.setA0114(codeValue2("ZB01", value[7]));
						a01.setComboxArea_a0114(value[7]);
						a01.setA0134(value[8].replace(".", ""));
						String[] a = value[9].split("@");
						if (a[0].length() > 1) {
							String[] b = a[0].split("#");
							if (b[0].length() > 0) {
								a01.setQrzxl(b[0]);
							}
							if (b.length > 1) {
								a01.setQrzxw(b[1]);
							}
						}
						if (a[1].length() > 1) {
							String[] b = a[1].split("#");
							if (b[0].length() > 0) {
								a01.setZzxl(b[0]);
							}
							if (b.length > 1) {
								a01.setZzxw(b[1]);
							}
						}
						String[] c = value[10].split("@");
						if (c[0].length() > 1) {
							String[] b = c[0].split("#");
							if (b[0].length() > 0) {
								a01.setQrzxlxx(b[0]);
							}
							if (b.length > 1) {
								a01.setQrzxwxx(b[1]);
							}
						}
						if (c[1].length() > 1) {
							String[] b = c[1].split("#");
							if (b[0].length() > 0) {
								a01.setZzxlxx(b[0]);
							}
							if (b.length > 1) {
								a01.setZzxwxx(b[1]);
							}
						}
						a01.setA0196(value[11]);
						a01.setA0187a(value[16]);
						a01.setA1701(value[17]);
						a01.setA14z101(value[18]);
						a01.setA15z101(value[19]);
						a01.setA0192a(value[25]);
						a01.setA0192(value[25]);
						a01.setRmly(value[28]);
						a01.setA0195(A0195(a0201b));
						// a01.setTbrjg(SysManagerUtils.getUserOrgid());
						sess.save(a01);
						
						//考核反填
						try{
							String khxx = a01.getA15z101().replace("；", ";").trim();
							if(khxx.contains(";")){		//多条
								String[] khxx_arr = khxx.split(";");
								for(int i = 0; i < khxx_arr.length; i++){
									String khxx_str = khxx_arr[i].trim();
									String regEx="[^0-9]";   
									Pattern p = Pattern.compile(regEx);   
									Matcher m = p.matcher(khxx_str);
									String a1521 = m.replaceAll("").trim();
									if(a1521.length() > 4 || a1521.length() < 4){
										continue;
									}
									A15 a15 = new A15();
									a15.setA0000(a0000);
									a15.setA1521(a1521);
									String a1517 = "";
									if(khxx_str.contains("基本称职")){
										a1517 = "3";
									}else if(khxx_str.contains("不称职")){
										a1517 = "4";
									}else if(khxx_str.contains("优秀")){
										a1517 = "1";
									}else if(khxx_str.contains("称职")){
										a1517 = "2";
									}else if(khxx_str.contains("基本合格")){
										a1517 = "3B";
									}else if(khxx_str.contains("不合格")){
										a1517 = "4B";
									}else if(khxx_str.contains("合格")){
										a1517 = "2B";
									}else if(khxx_str.contains("不定等次")){
										a1517 = "5";
									}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
										a1517 = "5A";
									}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
										a1517 = "5B";
									}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
										a1517 = "5C";
									}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
										a1517 = "6A";
									}else if(khxx_str.contains("其他原因不进行考核")){
										a1517 = "6B";
									}else if(khxx_str.contains("不进行考核")){
										a1517 = "6";
									}
									a15.setA1517(a1517);
									a15.setA1527("" + khxx_arr.length);
									sess.save(a15);
								}
							}else{				//单条
								String regEx="[^0-9]";   
								Pattern p = Pattern.compile(regEx);   
								Matcher m = p.matcher(khxx);
								String a1521 = m.replaceAll("").trim();
								if(a1521.length() > 4 || a1521.length() < 4){
									
								}else{
									A15 a15 = new A15();
									a15.setA0000(a0000);
									a15.setA1521(a1521);
									String a1517 = "";
									if(khxx.contains("基本称职")){
										a1517 = "3";
									}else if(khxx.contains("不称职")){
										a1517 = "4";
									}else if(khxx.contains("优秀")){
										a1517 = "1";
									}else if(khxx.contains("称职")){
										a1517 = "2";
									}else if(khxx.contains("基本合格")){
										a1517 = "3B";
									}else if(khxx.contains("不合格")){
										a1517 = "4B";
									}else if(khxx.contains("合格")){
										a1517 = "2B";
									}else if(khxx.contains("不定等次")){
										a1517 = "5";
									}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
										a1517 = "5A";
									}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
										a1517 = "5B";
									}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
										a1517 = "5C";
									}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
										a1517 = "6A";
									}else if(khxx.contains("其他原因不进行考核")){
										a1517 = "6B";
									}else if(khxx.contains("不进行考核")){
										a1517 = "6";
									}
									a15.setA1517(a1517);
									a15.setA1527("1");
									sess.save(a15);
								}
							}
						}catch(Exception e){
							
						}
						
						//奖惩信息反填
						try{
							String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
							if(jcxx.contains(";")){		//多条
								String[] jcxx_arr = jcxx.split(";");
								for(int i = 0; i < jcxx_arr.length; i++){
									String jcxx_str = jcxx_arr[i].trim();
									String[] jcxx_str_arr = jcxx_str.split(",");
									String pzsj = jcxx_str_arr[0];
									String pzjg = jcxx_str_arr[1];
									String jcmc = jcxx_str_arr[2];
									if(jcxx_str_arr.length == 3 && pzsj.length()<12){
										String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
										Pattern pattern = Pattern.compile("[0-9]*");
										if(pattern.matcher(pat).matches()&&pat.length()<=8){
											A14 a14 = new A14();
											a14.setA0000(a0000);
											//处理时间字符串中年月日和小数点
											a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
											//处理批准机关名称
											a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
											//处理奖惩名称和代码
											a14.setA1404a(jcmc.replace("。", ""));
											a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
											sess.save(a14);
										}
									}
								}
							}else{			//单条
								String[] jcxx_str_arr = jcxx.split(",");
								String pzsj = jcxx_str_arr[0];
								String pzjg = jcxx_str_arr[1];
								String jcmc = jcxx_str_arr[2];
								if(jcxx_str_arr.length == 3 && pzsj.length()<12){
									String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
									Pattern pattern = Pattern.compile("[0-9]*");
									if(pattern.matcher(pat).matches()&&pat.length()<=8){
										A14 a14 = new A14();
										a14.setA0000(a0000);
										//处理时间字符串中年月日和小数点
										a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
										//处理批准机关名称
										a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
										//处理奖惩名称和代码
										a14.setA1404a(jcmc.replace("。", ""));
										a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
										sess.save(a14);
									}
								}
							}
						}catch(Exception e){	//出现异常则跳过奖惩反填
							
						}
						
						//学历学位反填
						String qzrxl = a01.getQrzxl();
						String qzrxw = a01.getQrzxw();
						String qzrxlxx = a01.getQrzxlxx();
						String qzrxwxx = a01.getQrzxwxx();
						String zzxl = a01.getZzxl();
						String zzxw = a01.getZzxw();
						String zzxlxx = a01.getZzxlxx();
						String zzxwxx = a01.getZzxwxx();
						if(qzrxl != null && !"".equals(qzrxl.trim())){
							A08 a08 = new A08();
							a08.setA0000(a0000);
							a08.setA0801a(qzrxl);
							a08.setA0801b(codeValue("ZB64",qzrxl));
							a08.setA0814(qzrxlxx);
							/*a08.setA0899("true");
							a08.setA0837("1");
							sess.save(a08);
						}
						if(qzrxw != null && !"".equals(qzrxw)){
							A08 a08 = new A08();
							a08.setA0000(a0000);*/
							a08.setA0901a(qzrxw);
							a08.setA0901b(codeValue("GB6864",qzrxw));
							if(qzrxwxx != null && !"".equals(qzrxwxx.trim())){
								a08.setA0814(qzrxwxx);
							}else{
								a08.setA0814(qzrxlxx);
							}
							a08.setA0899("true");
							a08.setA0837("1");
							sess.save(a08);
						}
						if(zzxl != null && !"".equals(zzxl.trim())){
							A08 a08 = new A08();
							a08.setA0000(a0000);
							a08.setA0801a(zzxl);
							a08.setA0801b(codeValue("ZB64",zzxl));
							a08.setA0814(zzxlxx);
							/*a08.setA0899("true");
							a08.setA0837("2");
							sess.save(a08);
						}
						if(zzxw != null && !"".equals(zzxw)){
							A08 a08 = new A08();
							a08.setA0000(a0000);*/
							a08.setA0901a(zzxw);
							a08.setA0901b(codeValue("GB6864",zzxw));
							if(zzxwxx != null && !"".equals(zzxwxx.trim())){
								a08.setA0814(zzxwxx);
							}else{
								a08.setA0814(zzxlxx);
							}
							a08.setA0899("true");
							a08.setA0837("2");
							sess.save(a08);
						}
						
						//专业技术反填
						if(value[11] != null && !"".equals(value[11].trim())){
							a06.setA0000(a0000);
							a06.setA0601(A0601(value[11]));
							a06.setA0602(value[11]);
							a06.setA0699("true");
							sess.save(a06);
						}
						
						//职务反填
						if (!"".equals(a0201b)) {
							a02.setA0000(a0000);
							String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
							String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
							a02.setA0201a(a0201a);
							a02.setA0201b(a0201b);
							a02.setA0255("1");
							a02.setA0281("true");
							a02.setA0279("1");
							a02.setA0219("2");
							
							//集体内排序		
							if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
								String sqla="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
								Object sqlA0225 = sess.createSQLQuery(sqla).uniqueResult();
								String maxA0225  =  null;
								if(sqlA0225 != null){
									maxA0225  = sqlA0225.toString();
								}
								if(maxA0225!=null && !maxA0225.equals("")){
									Long a0225 = Long.valueOf(maxA0225);
									a02.setA0225(a0225 + 1L);
								}else{		//该机构下第一次有任职人员，初始化a0225
									a02.setA0225(1L);
								}
							}
							
							a02.setA0201d("0");
							a02.setA0251b("0");
							a02.setA0215a(value[25]);
							sess.save(a02);
							CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
						} else {
							a02.setA0000(a0000);
							a02.setA0201a("其他单位");
							a02.setA0201b("-1");
							a02.setA0255("1");
							a02.setA0281("true");
							a02.setA0279("1");
							a02.setA0219("2");
							a02.setA0201d("0");
							a02.setA0251b("0");
							a02.setA0215a(value[25]);
							sess.save(a02);
							CreateSysOrgBS.updateB01UpdatedWithZero("-1");
						}
						a53.setA0000(a0000);
						a53.setA5304(value[26]);
						a53.setA5315(value[27]);
						a53.setA5317(value[28]);
						a53.setA5319(value[29]);
						a53.setA5321(value[30]);
						if(value.length>32){
							value[32] = value[32].replace("\"", "");
							if (value[32].length() > 0) {
								a53.setA5323(value[32].length() == 2 ? "" : value[32]
										.substring(0, 8));
							}
						}
						a53.setA5327(user.getName());
						a53.setA5399(user.getId());
						sess.save(a53);
						if (value[20].length() > 1) {
							String[] b = value[20].split("@");
							String[] d = value[21].split("@");
							String[] e = value[22].split("@");
							String[] f = value[23].split("@");
							String[] g = value[24].split("@");
							//add chenys
							//获取家庭成员称谓 存入list中
							List listCw = new ArrayList();
							for (int i = 0; i < b.length; i++) {
								listCw.add(b[i]);
							}
							//计算各个称谓排序值
							List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
							for (int i = 0; i < b.length; i++) {
								A36 a36 = new A36();
								a36.setA0000(a0000);
								a36.setSortid(sortList.get(i));
								if (b[i].length() > 1) {
									a36.setA3604a(b[i]);
								}
								if (d.length > i && !StringUtil.isEmpty(d[i])) {
									a36.setA3601(d[i]);
								}
								if (e.length > i && !StringUtil.isEmpty(e[i])) {
									a36.setA3607(e[i]);
								}
								if (f.length > i && !StringUtil.isEmpty(f[i])) {
									a36.setA3627(f[i]);
								}
								if (g.length > i && !StringUtil.isEmpty(g[i])) {
									a36.setA3611(g[i]);
								}
								sess.save(a36);
							}
						}
					
						//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
						HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
						HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");
					}
				}
			}else{
				A01 a01 = new A01();
				A02 a02 = new A02();
				A06 a06 = new A06();
				A53 a53 = new A53();
				A37 a37 = new A37();
				a37.setA0000(a0000);
				sess.save(a37);
				a01.setA0000(a0000);
				a01.setStatus("1");
				a01.setA0163("1");
				a01.setTbr(user.getId());
				a01.setTbsj(DateUtil.getTimestamp().getTime());
				// a01.setA0180("导入LRM文件");
				a01.setA0180("");
				a01.setA0101(value[0]);
				a01.setA0104(codeValue("GB2261", value[1]));
				a01.setA0104a(value[1]);
				a01.setA0107(value[2].replace(".", ""));
				if (StringUtil.isEmpty(value[2])) {
					int birthYear = Integer.parseInt(value[2].substring(0, 4));
					int year = cal.get(Calendar.YEAR);// 得到当前年
					Long age = (long) year - birthYear;
					a01.setAge(age);
				}
				a01.setA0117(codeValue("GB3304", value[3]));
				a01.setA0117a(value[3]);
				a01.setA0111(codeValue2("ZB01", value[4]));
				a01.setComboxArea_a0111(value[4]);
				
				//入党时间反填
				String rdsj = value[5];
				//如果没有加'.'，则先加上
				if(rdsj!=null && rdsj.matches("^\\d{6}$")){
					rdsj = rdsj.substring(0, 4)+"."+rdsj.substring(4,6);
				}
				if(StringUtil.isEmpty(rdsj)){
					a01.setA0140(rdsj);
				}else{
/*					if(isNumeric(rdsj)){
						a01.setA0140(rdsj.substring(0,4)+"."+rdsj.substring(4,6));
					}else{
						a01.setA0140(rdsj);
					}*/
					rdsjAnalyze(rdsj, a01);
					a01.setA0140(rdsj);
				}
				a01.setA0128(value[6]);
				a01.setA0114(codeValue2("ZB01", value[7]));
				a01.setComboxArea_a0114(value[7]);
				a01.setA0134(value[8].replace(".", ""));
				String[] a = value[9].split("@");
				if (a[0].length() > 1) {
					String[] b = a[0].split("#");
					if (b[0].length() > 0) {
						a01.setQrzxl(b[0]);
					}
					if (b.length > 1) {
						a01.setQrzxw(b[1]);
					}
				}
				if (a[1].length() > 1) {
					String[] b = a[1].split("#");
					if (b[0].length() > 0) {
						a01.setZzxl(b[0]);
					}
					if (b.length > 1) {
						a01.setZzxw(b[1]);
					}
				}
				String[] c = value[10].split("@");
				if (c[0].length() > 1) {
					String[] b = c[0].split("#");
					if (b[0].length() > 0) {
						a01.setQrzxlxx(b[0]);
					}
					if (b.length > 1) {
						a01.setQrzxwxx(b[1]);
					}
				}
				if (c[1].length() > 1) {
					String[] b = c[1].split("#");
					if (b[0].length() > 0) {
						a01.setZzxlxx(b[0]);
					}
					if (b.length > 1) {
						a01.setZzxwxx(b[1]);
					}
				}
				a01.setA0196(value[11]);
				a01.setA0187a(value[16]);
				a01.setA1701(value[17]);
				a01.setA14z101(value[18]);
				a01.setA15z101(value[19]);
				a01.setA0192a(value[25]);
				a01.setA0192(value[25]);
				a01.setRmly(value[28]);
				a01.setA0195(A0195(a0201b));
				// a01.setTbrjg(SysManagerUtils.getUserOrgid());
				sess.save(a01);
				
				
				//考核反填
				try{
					String khxx = a01.getA15z101().replace("；", ";").trim();
					if(khxx.contains(";")){		//多条
						String[] khxx_arr = khxx.split(";");
						for(int i = 0; i < khxx_arr.length; i++){
							String khxx_str = khxx_arr[i].trim();
							String regEx="[^0-9]";   
							Pattern p = Pattern.compile(regEx);   
							Matcher m = p.matcher(khxx_str);
							String a1521 = m.replaceAll("").trim();
							if(a1521.length() > 4 || a1521.length() < 4){
								continue;
							}
							A15 a15 = new A15();
							a15.setA0000(a0000);
							a15.setA1521(a1521);
							String a1517 = "";
							if(khxx_str.contains("基本称职")){
								a1517 = "3";
							}else if(khxx_str.contains("不称职")){
								a1517 = "4";
							}else if(khxx_str.contains("优秀")){
								a1517 = "1";
							}else if(khxx_str.contains("称职")){
								a1517 = "2";
							}else if(khxx_str.contains("基本合格")){
								a1517 = "3B";
							}else if(khxx_str.contains("不合格")){
								a1517 = "4B";
							}else if(khxx_str.contains("合格")){
								a1517 = "2B";
							}else if(khxx_str.contains("不定等次")){
								a1517 = "5";
							}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
								a1517 = "5A";
							}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
								a1517 = "5B";
							}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
								a1517 = "5C";
							}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
								a1517 = "6A";
							}else if(khxx_str.contains("其他原因不进行考核")){
								a1517 = "6B";
							}else if(khxx_str.contains("不进行考核")){
								a1517 = "6";
							}
							a15.setA1517(a1517);
							a15.setA1527("" + khxx_arr.length);
							sess.save(a15);
						}
					}else{				//单条
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(khxx);
						String a1521 = m.replaceAll("").trim();
						if(a1521.length() > 4 || a1521.length() < 4){
							
						}else{
							A15 a15 = new A15();
							a15.setA0000(a0000);
							a15.setA1521(a1521);
							String a1517 = "";
							if(khxx.contains("基本称职")){
								a1517 = "3";
							}else if(khxx.contains("不称职")){
								a1517 = "4";
							}else if(khxx.contains("优秀")){
								a1517 = "1";
							}else if(khxx.contains("称职")){
								a1517 = "2";
							}else if(khxx.contains("基本合格")){
								a1517 = "3B";
							}else if(khxx.contains("不合格")){
								a1517 = "4B";
							}else if(khxx.contains("合格")){
								a1517 = "2B";
							}else if(khxx.contains("不定等次")){
								a1517 = "5";
							}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
								a1517 = "5A";
							}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
								a1517 = "5B";
							}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
								a1517 = "5C";
							}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
								a1517 = "6A";
							}else if(khxx.contains("其他原因不进行考核")){
								a1517 = "6B";
							}else if(khxx.contains("不进行考核")){
								a1517 = "6";
							}
							a15.setA1517(a1517);
							a15.setA1527("1");
							sess.save(a15);
						}
					}
				}catch(Exception e){
					
				}
				//奖惩信息反填
				try{
					String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
					if(jcxx.contains(";")){		//多条
						String[] jcxx_arr = jcxx.split(";");
						for(int i = 0; i < jcxx_arr.length; i++){
							String jcxx_str = jcxx_arr[i].trim();
							String[] jcxx_str_arr = jcxx_str.split(",");
							String pzsj = jcxx_str_arr[0];
							String pzjg = jcxx_str_arr[1];
							String jcmc = jcxx_str_arr[2];
							if(jcxx_str_arr.length == 3 && pzsj.length()<12){
								String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
								Pattern pattern = Pattern.compile("[0-9]*");
								if(pattern.matcher(pat).matches()&&pat.length()<=8){
									A14 a14 = new A14();
									a14.setA0000(a0000);
									//处理时间字符串中年月日和小数点
									a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
									//处理批准机关名称
									a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
									//处理奖惩名称和代码
									a14.setA1404a(jcmc.replace("。", ""));
									a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
									sess.save(a14);
								}
							}
						}
					}else{			//单条
						String[] jcxx_str_arr = jcxx.split(",");
						String pzsj = jcxx_str_arr[0];
						String pzjg = jcxx_str_arr[1];
						String jcmc = jcxx_str_arr[2];
						if(jcxx_str_arr.length == 3 && pzsj.length()<12){
							String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
							Pattern pattern = Pattern.compile("[0-9]*");
							if(pattern.matcher(pat).matches()&&pat.length()<=8){
								A14 a14 = new A14();
								a14.setA0000(a0000);
								//处理时间字符串中年月日和小数点
								a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
								//处理批准机关名称
								a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
								//处理奖惩名称和代码
								a14.setA1404a(jcmc.replace("。", ""));
								a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
								sess.save(a14);
							}
						}
					}
				}catch(Exception e){	//出现异常则跳过奖惩反填
					
				}
				
				//学历学位反填
				String qzrxl = a01.getQrzxl();
				String qzrxw = a01.getQrzxw();
				String qzrxlxx = a01.getQrzxlxx();
				String qzrxwxx = a01.getQrzxwxx();
				String zzxl = a01.getZzxl();
				String zzxw = a01.getZzxw();
				String zzxlxx = a01.getZzxlxx();
				String zzxwxx = a01.getZzxwxx();
				if(qzrxl != null && !"".equals(qzrxl.trim())){
					A08 a08 = new A08();
					a08.setA0000(a0000);
					a08.setA0801a(qzrxl);
					a08.setA0801b(codeValue("ZB64",qzrxl));
					a08.setA0814(qzrxlxx);
					/*a08.setA0899("true");
					a08.setA0837("1");
					sess.save(a08);
				}
				if(qzrxw != null && !"".equals(qzrxw)){
					A08 a08 = new A08();
					a08.setA0000(a0000);*/
					a08.setA0901a(qzrxw);
					a08.setA0901b(codeValue("GB6864",qzrxw));
					if(qzrxwxx != null && !"".equals(qzrxwxx.trim())){
						a08.setA0814(qzrxwxx);
					}else{
						a08.setA0814(qzrxlxx);
					}
					a08.setA0899("true");
					a08.setA0837("1");
					sess.save(a08);
				}
				if(zzxl != null && !"".equals(zzxl.trim())){
					A08 a08 = new A08();
					a08.setA0000(a0000);
					a08.setA0801a(zzxl);
					a08.setA0801b(codeValue("ZB64",zzxl));
					a08.setA0814(zzxlxx);
					/*a08.setA0899("true");
					a08.setA0837("2");
					sess.save(a08);
				}
				if(zzxw != null && !"".equals(zzxw)){
					A08 a08 = new A08();
					a08.setA0000(a0000);*/
					a08.setA0901a(zzxw);
					a08.setA0901b(codeValue("GB6864",zzxw));
					if(zzxwxx != null && !"".equals(zzxwxx.trim())){
						a08.setA0814(zzxwxx);
					}else{
						a08.setA0814(zzxlxx);
					}
					a08.setA0899("true");
					a08.setA0837("2");
					sess.save(a08);
				}
				
				//专业技术反填
				if(value[11] != null && !"".equals(value[11].trim())){
					a06.setA0000(a0000);
					a06.setA0601(A0601(value[11]));
					a06.setA0602(value[11]);
					a06.setA0699("true");
					sess.save(a06);
				}
				
				//职务反填
				if (!"".equals(a0201b)) {
					a02.setA0000(a0000);
					String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
					String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
					a02.setA0201a(a0201a);
					a02.setA0201b(a0201b);
					a02.setA0255("1");
					a02.setA0281("true");
					a02.setA0279("1");
					a02.setA0219("2");
					
					//集体内排序		
					if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
						String sqla="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
						Object sqlA0225 = sess.createSQLQuery(sqla).uniqueResult();
						String maxA0225  =  null;
						if(sqlA0225 != null){
							maxA0225  = sqlA0225.toString();
						}
						if(maxA0225!=null && !maxA0225.equals("")){
							Long a0225 = Long.valueOf(maxA0225);
							a02.setA0225(a0225 + 1L);
						}else{		//该机构下第一次有任职人员，初始化a0225
							a02.setA0225(1L);
						}
					}
					
					a02.setA0201d("0");
					a02.setA0251b("0");
					a02.setA0215a(value[25]);
					sess.save(a02);
					CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
				} else {
					a02.setA0000(a0000);
					a02.setA0201a("其他单位");
					a02.setA0201b("-1");
					a02.setA0255("1");
					a02.setA0281("true");
					a02.setA0279("1");
					a02.setA0219("2");
					a02.setA0201d("0");
					a02.setA0251b("0");
					a02.setA0215a(value[25]);
					sess.save(a02);
					CreateSysOrgBS.updateB01UpdatedWithZero("-1");
				}
				a53.setA0000(a0000);
				a53.setA5304(value[26]);
				a53.setA5315(value[27]);
				a53.setA5317(value[28]);
				a53.setA5319(value[29]);
				a53.setA5321(value[30]);
				if(value.length>32){
					value[32] = value[32].replace("\"", "");
					if (value[32].length() > 0) {
						a53.setA5323(value[32].length() == 2 ? "" : value[32]
								.substring(0, 8));
					}
				}
				a53.setA5327(user.getName());
				a53.setA5399(user.getId());
				sess.save(a53);
				if (value[20].length() > 1) {
					String[] b = value[20].split("@");
					String[] d = value[21].split("@");
					String[] e = value[22].split("@");
					String[] f = value[23].split("@");
					String[] g = value[24].split("@");
					//add chenys
					//获取家庭成员称谓 存入list中
					List listCw = new ArrayList();
					for (int i = 0; i < b.length; i++) {
						listCw.add(b[i]);
					}
					//计算各个称谓排序值
					List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
					for (int i = 0; i < b.length; i++) {
						A36 a36 = new A36();
						a36.setA0000(a0000);
						a36.setSortid(sortList.get(i));
						if (b[i].length() > 1) {
							a36.setA3604a(b[i]);
						}
						if (d.length > i && !StringUtil.isEmpty(d[i])) {
							a36.setA3601(d[i]);
						}
						if (e.length > i && !StringUtil.isEmpty(e[i])) {
							a36.setA3607(e[i]);
						}
						if (f.length > i && !StringUtil.isEmpty(f[i])) {
							a36.setA3627(f[i]);
						}
						if (g.length > i && !StringUtil.isEmpty(g[i])) {
							a36.setA3611(g[i]);
						}
						sess.save(a36);
					}
				}
				
				//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");  
				
			}
			A01 a01log = new A01();
			new LogUtil().createLog("34", "A01", a01log.getA0000(), a01log.getA0101(),
					"LRM导入", new Map2Temp().getLogInfo(new A01(), a01log));
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public static void savePic(File file, String a0000) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = null;
		try {
			trans = sess.beginTransaction();
			long fileSize = file.length();
			if (fileSize > Integer.MAX_VALUE) {
				CommonQueryBS.systemOut("file too big...");
			}
			FileInputStream fi;
			fi = new FileInputStream(file);
			byte[] buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length
					&& (numRead = fi.read(buffer, offset, buffer.length
							- offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset != buffer.length) {
				throw new IOException("Could not completely read file "
						+ file.getName());
			}
			fi.close();
			A57 a57 = new A57();
			a57.setA0000(a0000);
			PhotosUtil.savePhotoData(a57, sess, buffer);
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			throw new Exception(e.getMessage());
		}
	}

	public static String saveLrmx(PersonXml personXml, String a0201b,String method)
			throws Exception {
		HBSession sess = HBUtil.getHBSession();
		HBTransaction trans = sess.beginTransaction();
		try {
			UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
			String loginnname = user.getLoginname();// 操作人工号
			Calendar cal = Calendar.getInstance();
			if(!method.equals("Again")){
				if (!StringUtil.isEmpty(personXml.getXingMing())) {
					//String value2 = "";
					String value0 = "";
					String sql = "";
					if(!StringUtil.isEmpty(personXml.getShenFenZheng())){
						//value2 = personXml.getChuShengNianYue().substring(0, 6);
						//value2 = personXml.getChuShengNianYue().replace(".", "");
						value0 = personXml.getShenFenZheng();
						if(DBUtil.getDBType()==DBType.ORACLE){
							//sql = "from A01 t where t.a0101 = '" + value0 + "' and substr(t.a0107,0,6)='"+value2+"' ";
							//根据身份证判断
							sql = "from A01 t where t.a0184 = '"+value0+"' ";
						}else{
							//sql = "from A01 t where t.a0101 = '" + value0 + "' and substring(t.a0107,1,6) = '"+value2+"' ";
							//根据身份证与姓名判断
							sql = "from A01 t where t.a0184 = '"+value0+"' ";
						}
					}else{
						value0 = "20171020ABC";
						if(DBUtil.getDBType()==DBType.ORACLE){
							sql = "from A01 t where t.a0184 = '"+value0+"' ";
						}else{
							sql = "from A01 t where t.a0184 = '"+value0+"' ";
						}
					}
					List<A01> list = sess.createQuery(sql).list();
					if (list.size() > 0) {
						return "updateUpLoad('" + personXml.getShenFenZheng() + "');";
					}else{
						A01 a01 = new A01();
						A02 a02 = new A02();
						A06 a06 = new A06();
						A53 a53 = new A53();
						A37 a37 = new A37();
						String a0000 = UUID.randomUUID().toString();
						a37.setA0000(a0000);
						sess.save(a37);
						a01.setA0000(a0000);
						a01.setTbr(user.getId());
						a01.setTbsj(DateUtil.getTimestamp().getTime());
						// a01.setA0180("导入LRMX文件");
						a01.setA0180("");
						a01.setA0101(personXml.getXingMing());
						if (personXml.getXingBie() == null) {
							a01.setA0104("");
							a01.setA0104a("");
						} else {
							a01.setA0104(personXml.getXingBie().equals("男") ? "1" : "2");
							a01.setA0104a(personXml.getXingBie());
						}
						a01.setA0107(personXml.getChuShengNianYue().replace(".", ""));
						if (!StringUtil.isEmpty(personXml.getChuShengNianYue())) {
							int birthYear = Integer.parseInt(personXml.getChuShengNianYue()
									.substring(0, 4));
							int year = cal.get(Calendar.YEAR);// 得到当前年
							Long age = (long) year - birthYear;
							a01.setAge(age);
						}
						a01.setA0117(codeValue("GB3304", personXml.getMinZu()));
						a01.setA0117a(personXml.getMinZu());
						a01.setA0111(codeValue2("ZB01", personXml.getJiGuan()));
						a01.setComboxArea_a0111(personXml.getJiGuan());
						a01.setA0114(codeValue2("ZB01", personXml.getChuShengDi()));
						a01.setComboxArea_a0114(personXml.getChuShengDi());
						
						//入党时间反填
						String rdsj = personXml.getRuDangShiJian();
						//如果没有加'.'，则先加上
						if(rdsj!=null && rdsj.matches("^\\d{6}$")){
							rdsj = rdsj.substring(0, 4)+"."+rdsj.substring(4,6);
						}
						if(StringUtil.isEmpty(personXml.getRuDangShiJian())){
							a01.setA0140(rdsj);
						}else{
/*							if(isNumeric(rdsj)){
								a01.setA0140(personXml.getRuDangShiJian().substring(0,4)+"."+personXml.getRuDangShiJian().substring(4,6));
							}else{
								a01.setA0140(rdsj);
							}*/
							rdsjAnalyze(rdsj, a01);
							a01.setA0140(rdsj);
						}
						a01.setA0134(personXml.getCanJiaGongZuoShiJian().replace(".", ""));
						a01.setA0128(personXml.getJianKangZhuangKuang());
						a01.setA0196(personXml.getZhuanYeJiShuZhiWu());
						a01.setA0187a(personXml.getShuXiZhuanYeYouHeZhuanChang());
						a01.setQrzxl(personXml.getQuanRiZhiJiaoYu_XueLi());
						a01.setQrzxw(personXml.getQuanRiZhiJiaoYu_XueWei());
						a01.setQrzxlxx(personXml.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
						a01.setQrzxwxx(personXml.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
						a01.setZzxl(personXml.getZaiZhiJiaoYu_XueLi());
						a01.setZzxw(personXml.getZaiZhiJiaoYu_XueWei());
						a01.setZzxlxx(personXml.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
						a01.setZzxwxx(personXml.getZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
						a01.setA0192a(personXml.getXianRenZhiWu());
						a01.setA0192(personXml.getXianRenZhiWu());
						a01.setA1701(personXml.getJianLi());
						a01.setA14z101(personXml.getJiangChengQingKuang());
						a01.setA15z101(personXml.getNianDuKaoHeJieGuo());
						a01.setRmly(personXml.getRenMianLiYou());
						a01.setTbrjg(SysManagerUtils.getUserOrgid());
						a01.setA0184(personXml.getShenFenZheng());
						a01.setStatus("1");
						a01.setA0163("1");
						a01.setA0195(A0195(a0201b));
						sess.save(a01);
						
						//考核反填
						try{
							String khxx = a01.getA15z101().replace("；", ";").trim();
							if(khxx.contains(";")){		//多条
								String[] khxx_arr = khxx.split(";");
								for(int i = 0; i < khxx_arr.length; i++){
									String khxx_str = khxx_arr[i].trim();
									String regEx="[^0-9]";   
									Pattern p = Pattern.compile(regEx);   
									Matcher m = p.matcher(khxx_str);
									String a1521 = m.replaceAll("").trim();
									if(a1521.length() > 4 || a1521.length() < 4){
										continue;
									}
									A15 a15 = new A15();
									a15.setA0000(a0000);
									a15.setA1521(a1521);
									String a1517 = "";
									if(khxx_str.contains("基本称职")){
										a1517 = "3";
									}else if(khxx_str.contains("不称职")){
										a1517 = "4";
									}else if(khxx_str.contains("优秀")){
										a1517 = "1";
									}else if(khxx_str.contains("称职")){
										a1517 = "2";
									}else if(khxx_str.contains("基本合格")){
										a1517 = "3B";
									}else if(khxx_str.contains("不合格")){
										a1517 = "4B";
									}else if(khxx_str.contains("合格")){
										a1517 = "2B";
									}else if(khxx_str.contains("不定等次")){
										a1517 = "5";
									}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
										a1517 = "5A";
									}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
										a1517 = "5B";
									}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
										a1517 = "5C";
									}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
										a1517 = "6A";
									}else if(khxx_str.contains("其他原因不进行考核")){
										a1517 = "6B";
									}else if(khxx_str.contains("不进行考核")){
										a1517 = "6";
									}
									a15.setA1517(a1517);
									a15.setA1527("" + khxx_arr.length);
									sess.save(a15);
								}
							}else{				//单条
								String regEx="[^0-9]";   
								Pattern p = Pattern.compile(regEx);   
								Matcher m = p.matcher(khxx);
								String a1521 = m.replaceAll("").trim();
								if(a1521.length() > 4 || a1521.length() < 4){
									
								}else{
									A15 a15 = new A15();
									a15.setA0000(a0000);
									a15.setA1521(a1521);
									String a1517 = "";
									if(khxx.contains("基本称职")){
										a1517 = "3";
									}else if(khxx.contains("不称职")){
										a1517 = "4";
									}else if(khxx.contains("优秀")){
										a1517 = "1";
									}else if(khxx.contains("称职")){
										a1517 = "2";
									}else if(khxx.contains("基本合格")){
										a1517 = "3B";
									}else if(khxx.contains("不合格")){
										a1517 = "4B";
									}else if(khxx.contains("合格")){
										a1517 = "2B";
									}else if(khxx.contains("不定等次")){
										a1517 = "5";
									}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
										a1517 = "5A";
									}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
										a1517 = "5B";
									}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
										a1517 = "5C";
									}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
										a1517 = "6A";
									}else if(khxx.contains("其他原因不进行考核")){
										a1517 = "6B";
									}else if(khxx.contains("不进行考核")){
										a1517 = "6";
									}
									a15.setA1517(a1517);
									a15.setA1527("1");
									sess.save(a15);
								}
							}
						}catch(Exception e){
							
						}
						
						//奖惩信息反填
						try{
							String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
							if(jcxx.contains(";")){		//多条
								String[] jcxx_arr = jcxx.split(";");
								for(int i = 0; i < jcxx_arr.length; i++){
									String jcxx_str = jcxx_arr[i].trim();
									String[] jcxx_str_arr = jcxx_str.split(",");
									String pzsj = jcxx_str_arr[0];
									String pzjg = jcxx_str_arr[1];
									String jcmc = jcxx_str_arr[2];
									if(jcxx_str_arr.length == 3 && pzsj.length()<12){
										String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
										Pattern pattern = Pattern.compile("[0-9]*");
										if(pattern.matcher(pat).matches()&&pat.length()<=8){
											A14 a14 = new A14();
											a14.setA0000(a0000);
											//处理时间字符串中年月日和小数点
											a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
											//处理批准机关名称
											a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
											//处理奖惩名称和代码
											a14.setA1404a(jcmc.replace("。", ""));
											a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
											sess.save(a14);
										}
									}
								}
							}else{	//单条
								String[] jcxx_str_arr = jcxx.split(",");
								String pzsj = jcxx_str_arr[0];
								String pzjg = jcxx_str_arr[1];
								String jcmc = jcxx_str_arr[2];
								if(jcxx_str_arr.length == 3 && pzsj.length()<12){
									String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
									Pattern pattern = Pattern.compile("[0-9]*");
									if(pattern.matcher(pat).matches()&&pat.length()<=8){
										A14 a14 = new A14();
										a14.setA0000(a0000);
										//处理时间字符串中年月日和小数点
										a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
										//处理批准机关名称
										a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
										//处理奖惩名称和代码
										a14.setA1404a(jcmc.replace("。", ""));
										a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
										sess.save(a14);
									}
								}
							}
						}catch(Exception e){	//出现异常则跳过奖惩反填
							
						}
						
						//学历学位反填
						String qzrxl = a01.getQrzxl();
						String qzrxw = a01.getQrzxw();
						String qzrxlxx = a01.getQrzxlxx();
						String qzrxwxx = a01.getQrzxwxx();
						String zzxl = a01.getZzxl();
						String zzxw = a01.getZzxw();
						String zzxlxx = a01.getZzxlxx();
						String zzxwxx = a01.getZzxwxx();
						if(qzrxl != null && !"".equals(qzrxl.trim())){
							A08 a08 = new A08();
							a08.setA0000(a0000);
							a08.setA0801a(qzrxl);
							a08.setA0801b(codeValue("ZB64",qzrxl));
							a08.setA0814(qzrxlxx);
							/*a08.setA0899("true");
							a08.setA0837("1");
							sess.save(a08);
						}
						if(qzrxw != null && !"".equals(qzrxw)){
							A08 a08 = new A08();
							a08.setA0000(a0000);*/
							a08.setA0901a(qzrxw);
							a08.setA0901b(codeValue("GB6864",qzrxw));
							if(qzrxwxx != null && !"".equals(qzrxwxx.trim())){
								a08.setA0814(qzrxwxx);
							}else{
								a08.setA0814(qzrxlxx);
							}
							a08.setA0899("true");
							a08.setA0837("1");
							sess.save(a08);
						}
						if(zzxl != null && !"".equals(zzxl.trim())){
							A08 a08 = new A08();
							a08.setA0000(a0000);
							a08.setA0801a(zzxl);
							a08.setA0801b(codeValue("ZB64",zzxl));
							a08.setA0814(zzxlxx);
							/*a08.setA0899("true");
							a08.setA0837("2");
							sess.save(a08);
						}
						if(zzxw != null && !"".equals(zzxw)){
							A08 a08 = new A08();
							a08.setA0000(a0000);*/
							a08.setA0901a(zzxw);
							a08.setA0901b(codeValue("GB6864",zzxw));
							if(zzxwxx != null && !"".equals(zzxwxx.trim())){
								a08.setA0814(zzxwxx);
							}else{
								a08.setA0814(zzxlxx);
							}
							a08.setA0899("true");
							a08.setA0837("2");
							sess.save(a08);
							
						}
						SysRuleBS.call(a0000);
						//执行a08
						
						//专业技术反填
						if(personXml.getZhuanYeJiShuZhiWu() != null && !"".equals(personXml.getZhuanYeJiShuZhiWu().trim())){
							a06.setA0000(a0000);
							a06.setA0601(A0601(personXml.getZhuanYeJiShuZhiWu()));
							a06.setA0602(personXml.getZhuanYeJiShuZhiWu());
							a06.setA0699("true");
							sess.save(a06);
						}
						
						//职务反填
						if (!"".equals(a0201b)) {
							a02.setA0000(a0000);
							String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
							String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
							a02.setA0201a(a0201a);
							a02.setA0201b(a0201b);
							a02.setA0255("1");
							a02.setA0281("true");
							a02.setA0279("1");
							a02.setA0219("2");
							
							//集体内排序		
							if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
								String sqla="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
								Object sqlA0225 = sess.createSQLQuery(sqla).uniqueResult();
								String maxA0225  =  null;
								if(sqlA0225 != null){
									maxA0225  = sqlA0225.toString();
								}
								if(maxA0225!=null && !maxA0225.equals("")){
									Long a0225 = Long.valueOf(maxA0225);
									a02.setA0225(a0225 + 1L);
								}else{		//该机构下第一次有任职人员，初始化a0225
									a02.setA0225(1L);
								}
							}
							
							a02.setA0201d("0");
							a02.setA0251b("0");
							a02.setA0215a(personXml.getXianRenZhiWu());
							sess.save(a02);
							CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
						} else {
							a02.setA0000(a0000);
							a02.setA0201a("其他单位");
							a02.setA0201b("-1");
							a02.setA0255("1");
							a02.setA0281("true");
							a02.setA0279("1");
							a02.setA0219("2");
							a02.setA0201d("0");
							a02.setA0251b("0");
							a02.setA0215a(personXml.getXianRenZhiWu());
							sess.save(a02);
							CreateSysOrgBS.updateB01UpdatedWithZero("-1");
						}
						a53.setA0000(a0000);
						a53.setA5304(personXml.getNiRenZhiWu());
						a53.setA5315(personXml.getNiMianZhiWu());
						a53.setA5317(personXml.getRenMianLiYou());
						a53.setA5319(personXml.getChengBaoDanWei());
						a53.setA5321(personXml.getJiSuanNianLingShiJian());
						a53.setA5323(personXml.getTianBiaoShiJian());
						a53.setA5327(user.getName());
						a53.setA5399(user.getId());
						sess.save(a53);
						if (personXml.getZhaoPian() != null) {
							A57 a57 = new A57();
							a57.setA0000(a0000);
							PhotosUtil.savePhotoData(a57, sess, personXml.getZhaoPian());
						}

						if (personXml.getJiaTingChengYuan() != null
								&& personXml.getJiaTingChengYuan().size() > 0
								&& personXml.getJiaTingChengYuan().get(0) != null
								&& personXml.getJiaTingChengYuan().get(0).getItem() != null
								&& personXml.getJiaTingChengYuan().get(0).getItem().size() > 0) {
							//add chenys
							//获取家庭成员称谓 存入list中
							List listCw = new ArrayList();
							for (int i = 0; i < personXml.getJiaTingChengYuan().get(0).getItem().size(); i++) {
								ItemXml ix = personXml.getJiaTingChengYuan().get(0).getItem().get(i);
								if (ix != null) {
									listCw.add(ix.getChengWei());
								}
							}
							//计算各个称谓排序值
							List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
							for (int i = 0; i < personXml.getJiaTingChengYuan().get(0)
									.getItem().size(); i++) {
								ItemXml ix = personXml.getJiaTingChengYuan().get(0)
										.getItem().get(i);
								if (ix != null) {
									A36 a36 = new A36();
									a36.setA0000(a0000);
									a36.setA3601(ix.getXingMing());
									/*a36.setA3604a(codeValue("GB4761", ix.getChengWei()));*/
									a36.setA3604a(ix.getChengWei());
									a36.setA3607(ix.getChuShengRiQi());
									a36.setA3611(ix.getGongZuoDanWeiJiZhiWu());
									//a36.setA3627(codeValue("GB4762",ix.getZhengZhiMianMao()));
									a36.setA3627(ix.getZhengZhiMianMao());
									//从map中取出当前称谓对应的序号
									a36.setSortid(sortList.get(i));
									sess.save(a36);
								}
							}
						}
					
						//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
						HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
						HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");
						/*
						 * //对家庭成员重新排序 String updateSortid = "update a36 a1 set sortid=\r\n" +
						 * "	(select  row1  from (select row_number() over( order by \r\n" +
						 * "       case when A3604A  in ('丈夫','妻子') then \r\n" +
						 * "                 1\r\n" +
						 * "            when  A3604A in ('儿子','女儿','长子','次子','三子','四子','五子','养子','继子','继女','长女','次女','三女','四女','五女','养女','其他女儿') then \r\n"
						 * + "                 2\r\n" +
						 * "        end )  as  row1 ,a3600 from a36 a2 where a0000='"+a0000+"')  a2\r\n"
						 * + "		where a2.a3600 = a1.a3600)\r\n" + "where a0000='"+a0000+"' ";
						 * HBUtil.executeUpdate(updateSortid);
						 */
						//增加家庭成员类别值
						String updateUpdated = "update a36 a set updated = (select distinct c.sub_code_value from code_value c where c.code_Type='GB4761' and sub_code_value != -1 and c. code_value=a.a3604a) where a0000='"+a0000+"'" ; 
						HBUtil.executeUpdate(updateUpdated);
					}
					
				}
			}else{
				A01 a01 = new A01();
				A02 a02 = new A02();
				A06 a06 = new A06();
				A53 a53 = new A53();
				A37 a37 = new A37();
				String a0000 = UUID.randomUUID().toString();
				a37.setA0000(a0000);
				sess.save(a37);
				a01.setA0000(a0000);
				a01.setTbr(user.getId());
				a01.setTbsj(DateUtil.getTimestamp().getTime());
				// a01.setA0180("导入LRMX文件");
				a01.setA0180("");
				a01.setA0101(personXml.getXingMing());
				if (personXml.getXingBie() == null) {
					a01.setA0104("");
					a01.setA0104a("");
				} else {
					a01.setA0104(personXml.getXingBie().equals("男") ? "1" : "2");
					a01.setA0104a(personXml.getXingBie());
				}
				a01.setA0107(personXml.getChuShengNianYue().replace(".", ""));
				if (!StringUtil.isEmpty(personXml.getChuShengNianYue())) {
					int birthYear = Integer.parseInt(personXml.getChuShengNianYue()
							.substring(0, 4));
					int year = cal.get(Calendar.YEAR);// 得到当前年
					Long age = (long) year - birthYear;
					a01.setAge(age);
				}
				a01.setA0117(codeValue("GB3304", personXml.getMinZu()));
				a01.setA0117a(personXml.getMinZu());
				a01.setA0111(codeValue2("ZB01", personXml.getJiGuan()));
				a01.setComboxArea_a0111(personXml.getJiGuan());
				a01.setA0114(codeValue2("ZB01", personXml.getChuShengDi()));
				a01.setComboxArea_a0114(personXml.getChuShengDi());
				
				//入党时间反填
				String rdsj = personXml.getRuDangShiJian();
				//如果没有加'.'，则先加上
				if(rdsj!=null && rdsj.matches("^\\d{6}$")){
					rdsj = rdsj.substring(0, 4)+"."+rdsj.substring(4,6);
				}
				if(StringUtil.isEmpty(personXml.getRuDangShiJian())){
					a01.setA0140(rdsj);
				}else{
/*					if(isNumeric(rdsj)){
						a01.setA0140(personXml.getRuDangShiJian().substring(0,4)+"."+personXml.getRuDangShiJian().substring(4,6));
					}else{
						a01.setA0140(rdsj);
					}*/
					rdsjAnalyze(rdsj, a01);
					a01.setA0140(rdsj);
				}
				a01.setA0134(personXml.getCanJiaGongZuoShiJian().replace(".", ""));
				a01.setA0128(personXml.getJianKangZhuangKuang());
				a01.setA0196(personXml.getZhuanYeJiShuZhiWu());
				a01.setA0187a(personXml.getShuXiZhuanYeYouHeZhuanChang());
				a01.setQrzxl(personXml.getQuanRiZhiJiaoYu_XueLi());
				a01.setQrzxw(personXml.getQuanRiZhiJiaoYu_XueWei());
				a01.setQrzxlxx(personXml.getQuanRiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
				a01.setQrzxwxx(personXml.getQuanRiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
				a01.setZzxl(personXml.getZaiZhiJiaoYu_XueLi());
				a01.setZzxw(personXml.getZaiZhiJiaoYu_XueWei());
				a01.setZzxlxx(personXml.getZaiZhiJiaoYu_XueLi_BiYeYuanXiaoXi());
				a01.setZzxwxx(personXml.getZaiZhiJiaoYu_XueWei_BiYeYuanXiaoXi());
				a01.setA0192a(personXml.getXianRenZhiWu());
				a01.setA0192(personXml.getXianRenZhiWu());
				a01.setA1701(personXml.getJianLi());
				a01.setA14z101(personXml.getJiangChengQingKuang());
				a01.setA15z101(personXml.getNianDuKaoHeJieGuo());
				a01.setRmly(personXml.getRenMianLiYou());
				a01.setTbrjg(SysManagerUtils.getUserOrgid());
				a01.setA0184(personXml.getShenFenZheng());
				a01.setStatus("1");
				a01.setA0163("1");
				a01.setA0195(A0195(a0201b));
				sess.save(a01);
				
				//考核反填
				try{
					String khxx = a01.getA15z101().replace("；", ";").trim();
					if(khxx.contains(";")){		//多条
						String[] khxx_arr = khxx.split(";");
						for(int i = 0; i < khxx_arr.length; i++){
							String khxx_str = khxx_arr[i].trim();
							String regEx="[^0-9]";   
							Pattern p = Pattern.compile(regEx);   
							Matcher m = p.matcher(khxx_str);
							String a1521 = m.replaceAll("").trim();
							if(a1521.length() > 4 || a1521.length() < 4){
								continue;
							}
							A15 a15 = new A15();
							a15.setA0000(a0000);
							a15.setA1521(a1521);
							String a1517 = "";
							if(khxx_str.contains("基本称职")){
								a1517 = "3";
							}else if(khxx_str.contains("不称职")){
								a1517 = "4";
							}else if(khxx_str.contains("优秀")){
								a1517 = "1";
							}else if(khxx_str.contains("称职")){
								a1517 = "2";
							}else if(khxx_str.contains("基本合格")){
								a1517 = "3B";
							}else if(khxx_str.contains("不合格")){
								a1517 = "4B";
							}else if(khxx_str.contains("合格")){
								a1517 = "2B";
							}else if(khxx_str.contains("不定等次")){
								a1517 = "5";
							}else if(khxx_str.contains("新录用人员试用期年度考核不确定等次")){
								a1517 = "5A";
							}else if(khxx_str.contains("被立案调查尚未结案年度考核不确定等次")){
								a1517 = "5B";
							}else if(khxx_str.contains("受政纪处分期间年度考核不确定等次")){
								a1517 = "5C";
							}else if(khxx_str.contains("病、事假累计超过考核年度半年不进行考核")){
								a1517 = "6A";
							}else if(khxx_str.contains("其他原因不进行考核")){
								a1517 = "6B";
							}else if(khxx_str.contains("不进行考核")){
								a1517 = "6";
							}
							a15.setA1517(a1517);
							a15.setA1527("" + khxx_arr.length);
							sess.save(a15);
						}
					}else{				//单条
						String regEx="[^0-9]";   
						Pattern p = Pattern.compile(regEx);   
						Matcher m = p.matcher(khxx);
						String a1521 = m.replaceAll("").trim();
						if(a1521.length() > 4 || a1521.length() < 4){
							
						}else{
							A15 a15 = new A15();
							a15.setA0000(a0000);
							a15.setA1521(a1521);
							String a1517 = "";
							if(khxx.contains("基本称职")){
								a1517 = "3";
							}else if(khxx.contains("不称职")){
								a1517 = "4";
							}else if(khxx.contains("优秀")){
								a1517 = "1";
							}else if(khxx.contains("称职")){
								a1517 = "2";
							}else if(khxx.contains("基本合格")){
								a1517 = "3B";
							}else if(khxx.contains("不合格")){
								a1517 = "4B";
							}else if(khxx.contains("合格")){
								a1517 = "2B";
							}else if(khxx.contains("不定等次")){
								a1517 = "5";
							}else if(khxx.contains("新录用人员试用期年度考核不确定等次")){
								a1517 = "5A";
							}else if(khxx.contains("被立案调查尚未结案年度考核不确定等次")){
								a1517 = "5B";
							}else if(khxx.contains("受政纪处分期间年度考核不确定等次")){
								a1517 = "5C";
							}else if(khxx.contains("病、事假累计超过考核年度半年不进行考核")){
								a1517 = "6A";
							}else if(khxx.contains("其他原因不进行考核")){
								a1517 = "6B";
							}else if(khxx.contains("不进行考核")){
								a1517 = "6";
							}
							a15.setA1517(a1517);
							a15.setA1527("1");
							sess.save(a15);
						}
					}
				}catch(Exception e){
					
				}
				
				//奖惩信息反填
				try{
					String jcxx = a01.getA14z101().replace("；", ";").replace("，", ",").trim();
					if(jcxx.contains(";")){		//多条
						String[] jcxx_arr = jcxx.split(";");
						for(int i = 0; i < jcxx_arr.length; i++){
							String jcxx_str = jcxx_arr[i].trim();
							String[] jcxx_str_arr = jcxx_str.split(",");
							String pzsj = jcxx_str_arr[0];
							String pzjg = jcxx_str_arr[1];
							String jcmc = jcxx_str_arr[2];
							if(jcxx_str_arr.length == 3 && pzsj.length()<12){
								String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
								Pattern pattern = Pattern.compile("[0-9]*");
								if(pattern.matcher(pat).matches()&&pat.length()<=8){
									A14 a14 = new A14();
									a14.setA0000(a0000);
									//处理时间字符串中年月日和小数点
									a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
									//处理批准机关名称
									a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
									//处理奖惩名称和代码
									a14.setA1404a(jcmc.replace("。", ""));
									a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
									sess.save(a14);
								}
							}
						}
					}else{			//单条
						String[] jcxx_str_arr = jcxx.split(",");
						String pzsj = jcxx_str_arr[0];
						String pzjg = jcxx_str_arr[1];
						String jcmc = jcxx_str_arr[2];
						if(jcxx_str_arr.length == 3 && pzsj.length()<12){
							String pat = pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "");
							Pattern pattern = Pattern.compile("[0-9]*");
							if(pattern.matcher(pat).matches()&&pat.length()<=8){
								A14 a14 = new A14();
								a14.setA0000(a0000);
								//处理时间字符串中年月日和小数点
								a14.setA1407(pzsj.trim().replace(".","").replace("年", "").replace("月", "").replace("日", "")); 
								//处理批准机关名称
								a14.setA1411a(pzjg.trim().replace("经", "").replace("批准", ""));
								//处理奖惩名称和代码
								a14.setA1404a(jcmc.replace("。", ""));
								a14.setA1404b(codeValue("ZB65", jcmc.replace("。", "")));
								sess.save(a14);
							}
						}
					}
				}catch(Exception e){	//出现异常则跳过奖惩反填
					
				}
				
				//学历学位反填
				String qzrxl = a01.getQrzxl();
				String qzrxw = a01.getQrzxw();
				String qzrxlxx = a01.getQrzxlxx();
				String qzrxwxx = a01.getQrzxwxx();
				String zzxl = a01.getZzxl();
				String zzxw = a01.getZzxw();
				String zzxlxx = a01.getZzxlxx();
				String zzxwxx = a01.getZzxwxx();
				if(qzrxl != null && !"".equals(qzrxl.trim())){
					A08 a08 = new A08();
					a08.setA0000(a0000);
					a08.setA0801a(qzrxl);
					a08.setA0801b(codeValue("ZB64",qzrxl));
					a08.setA0814(qzrxlxx);
					/*a08.setA0899("true");
					a08.setA0837("1");
					sess.save(a08);
				}
				if(qzrxw != null && !"".equals(qzrxw)){
					A08 a08 = new A08();
					a08.setA0000(a0000);*/
					a08.setA0901a(qzrxw);
					a08.setA0901b(codeValue("GB6864",qzrxw));
					if(qzrxwxx != null && !"".equals(qzrxwxx.trim())){
						a08.setA0814(qzrxwxx);
					}else{
						a08.setA0814(qzrxlxx);
					}
					a08.setA0899("true");
					a08.setA0837("1");
					sess.save(a08);
				}
				if(zzxl != null && !"".equals(zzxl.trim())){
					A08 a08 = new A08();
					a08.setA0000(a0000);
					a08.setA0801a(zzxl);
					a08.setA0801b(codeValue("ZB64",zzxl));
					a08.setA0814(zzxlxx);
					/*a08.setA0899("true");
					a08.setA0837("2");
					sess.save(a08);
				}
				if(zzxw != null && !"".equals(zzxw)){
					A08 a08 = new A08();
					a08.setA0000(a0000);*/
					a08.setA0901a(zzxw);
					a08.setA0901b(codeValue("GB6864",zzxw));
					if(zzxwxx != null && !"".equals(zzxwxx.trim())){
						a08.setA0814(zzxwxx);
					}else{
						a08.setA0814(zzxlxx);
					}
					a08.setA0899("true");
					a08.setA0837("2");
					sess.save(a08);
				}
				
				//专业技术反填
				if(personXml.getZhuanYeJiShuZhiWu() != null && !"".equals(personXml.getZhuanYeJiShuZhiWu().trim())){
					a06.setA0000(a0000);
					a06.setA0601(A0601(personXml.getZhuanYeJiShuZhiWu()));
					a06.setA0602(personXml.getZhuanYeJiShuZhiWu());
					a06.setA0699("true");
					sess.save(a06);
				}
				
				//职务反填
				if (!"".equals(a0201b)) {
					a02.setA0000(a0000);
					String sql2 = "select t.b0101 from B01 t where t.b0111 = '"+a0201b+"' ";
					String a0201a = sess.createSQLQuery(sql2).uniqueResult().toString();
					a02.setA0201a(a0201a);
					a02.setA0201b(a0201b);
					a02.setA0255("1");
					a02.setA0281("true");
					a02.setA0279("1");
					a02.setA0219("2");
					
					//集体内排序		
					if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){
						String sqla="select max(a0225) from A02 a where a0201b='"+a0201b+"'";
						Object sqlA0225 = sess.createSQLQuery(sqla).uniqueResult();
						String maxA0225  =  null;
						if(sqlA0225 != null){
							maxA0225  = sqlA0225.toString();
						}
						if(maxA0225!=null && !maxA0225.equals("")){
							Long a0225 = Long.valueOf(maxA0225);
							a02.setA0225(a0225 + 1L);
						}else{		//该机构下第一次有任职人员，初始化a0225
							a02.setA0225(1L);
						}
					}
					
					a02.setA0201d("0");
					a02.setA0251b("0");
					a02.setA0215a(personXml.getXianRenZhiWu());
					sess.save(a02);
					CreateSysOrgBS.updateB01UpdatedWithZero(a0201b);
				} else {
					a02.setA0000(a0000);
					a02.setA0201a("其他单位");
					a02.setA0201b("-1");
					a02.setA0255("1");
					a02.setA0281("true");
					a02.setA0279("1");
					a02.setA0219("2");
					a02.setA0201d("0");
					a02.setA0251b("0");
					a02.setA0215a(personXml.getXianRenZhiWu());
					sess.save(a02);
					CreateSysOrgBS.updateB01UpdatedWithZero("-1");
				}
				a53.setA0000(a0000);
				a53.setA5304(personXml.getNiRenZhiWu());
				a53.setA5315(personXml.getNiMianZhiWu());
				a53.setA5317(personXml.getRenMianLiYou());
				a53.setA5319(personXml.getChengBaoDanWei());
				a53.setA5321(personXml.getJiSuanNianLingShiJian());
				a53.setA5323(personXml.getTianBiaoShiJian());
				a53.setA5327(user.getName());
				a53.setA5399(user.getId());
				sess.save(a53);
				if (personXml.getZhaoPian() != null) {
					A57 a57 = new A57();
					a57.setA0000(a0000);
					PhotosUtil.savePhotoData(a57, sess, personXml.getZhaoPian());
				}
				
				if (personXml.getJiaTingChengYuan() != null
						&& personXml.getJiaTingChengYuan().size() > 0
						&& personXml.getJiaTingChengYuan().get(0) != null
						&& personXml.getJiaTingChengYuan().get(0).getItem() != null
						&& personXml.getJiaTingChengYuan().get(0).getItem().size() > 0) {
					//add chenys
					//获取家庭成员称谓 存入list中
					List listCw = new ArrayList();
					for (int i = 0; i < personXml.getJiaTingChengYuan().get(0).getItem().size(); i++) {
						ItemXml ix = personXml.getJiaTingChengYuan().get(0).getItem().get(i);
						if (ix != null) {
							listCw.add(ix.getChengWei());
						}
					}
					//计算各个称谓排序值
					List<BigDecimal> sortList = Sortutil.CreateSortId(listCw);
					for (int i = 0; i < personXml.getJiaTingChengYuan().get(0)
							.getItem().size(); i++) {
						ItemXml ix = personXml.getJiaTingChengYuan().get(0)
								.getItem().get(i);
						if (ix != null) {
							A36 a36 = new A36();
							a36.setA0000(a0000);
							a36.setA3601(ix.getXingMing());
							/*a36.setA3604a(codeValue("GB4761", ix.getChengWei()));*/
							a36.setA3604a(ix.getChengWei());
							a36.setA3607(ix.getChuShengRiQi());
							a36.setA3611(ix.getGongZuoDanWeiJiZhiWu());
							//a36.setA3627(codeValue("GB4762",ix.getZhengZhiMianMao()));
							a36.setA3627(ix.getZhengZhiMianMao());
							//从map中取出当前称谓对应的序号
							a36.setSortid(sortList.get(i));
							/* a36.setSortid(new BigDecimal(i + 1)); */
							sess.save(a36);
						}
					}
				}
				//对a01表的两个排序字段做维护TORGID（最高机构），TORDER（最高机构所在机体内排序）
				HBUtil.executeUpdate("update a01 set a01.torgid= get_torgid(a0000) where a0000 = '"+a0000+"'");
				HBUtil.executeUpdate("update a01 set a01.torder= lpad((select max(a0225) from a02 where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and a01.torgid=a02.a0201b),5,'0') where a01.a0000 = '"+a0000+"'");
				/*
				 * //对家庭成员重新排序 String updateSortid = "update a36 a1 set sortid=\r\n" +
				 * "	(select  row1  from (select row_number() over( order by \r\n" +
				 * "       case when A3604A  in ('丈夫','妻子') then \r\n" +
				 * "                 1\r\n" +
				 * "            when  A3604A in ('儿子','女儿','长子','次子','三子','四子','五子','养子','继子','继女','长女','次女','三女','四女','五女','养女','其他女儿') then \r\n"
				 * + "                 2\r\n" +
				 * "        end )  as  row1 ,a3600 from a36 a2 where a0000='"+a0000+"')  a2\r\n"
				 * + "		where a2.a3600 = a1.a3600)\r\n" + "where a0000='"+a0000+"' ";
				 * HBUtil.executeUpdate(updateSortid);
				 */
				//增加家庭成员类别值
				String updateUpdated = "update a36 a set updated = (select distinct c.sub_code_value from code_value c where c.code_Type='GB4761' and sub_code_value != -1 and c. code_value=a.a3604a) where a0000='"+a0000+"'" ; 
				HBUtil.executeUpdate(updateUpdated);
			}
			
			A01 a01log = new A01();
			new LogUtil().createLog("35", "A01", a01log.getA0000(), a01log.getA0101(),
					"LRMX导入", new Map2Temp().getLogInfo(new A01(), a01log));
			trans.commit();
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public static String codeValue(String codeType, String name) {
		String sql = "select code_value from code_value t where t.code_name ='"
				+ name + "' and t.code_type='" + codeType + "'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String str = "";
		if (list.size() > 0) {
			str = list.get(0).toString();
		}
		return str;

	}
	
	public static String codeValue2(String codeType, String name) {
		String sql = "select code_value from code_value t where t.code_name3 like '%"
				+ name + "%' and t.code_type='" + codeType + "'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String str = "";
		if (list.size() > 0) {
			str = list.get(0).toString();
		}
		return str;

	}
	
	
	public static void rdsjAnalyze(String str,A01 a01) {
		
		for (Entry<Integer, String> entry : reg_map.entrySet()) {  
		    // 编译正则表达式
		    Pattern pattern = Pattern.compile(entry.getValue());
		    Matcher matcher = pattern.matcher(str);
		    // 查找字符串中是否有匹配正则表达式的字符/字符串
		    if(matcher.find()){
				switch (entry.getKey()) {
					//只有第一党派且为中共党员 
				case 1:  
					a01.setA0141("01");
					a01.setA0144(str.replace(".", ""));
					break;
					//只有一个党派,且不为中共党员
				case 2:
					a01.setA0141(codeValue("GB4762",str.replace("(", "").replace(")", "")));
					break;
					//有两个党派,且第一党派不为中共党员 
				case 3:
					a01.setA0141(codeValue("GB4762",str.split("、")[0].replace("(", "")));
					a01.setA3921(codeValue("GB4762",str.split("、")[1].replace(")", "")));
					break;
					//有三个党派,且第一党派不为中共党员 
				case 4:
					a01.setA0141(codeValue("GB4762",str.split("、")[0].replace("(", "")));
					a01.setA3921(codeValue("GB4762",str.split("、")[1]));
					a01.setA3927(codeValue("GB4762",str.split("、")[2].replace(")", "")));
					break;
					//任免表编辑器:
					//中共党员:  “197204”
				case 5:
					a01.setA0141("01");
					a01.setA0144(str.replace(".", ""));
					break;
					//单个民主党派成员或无党派人士: “民建”
				case 6:
					a01.setA0141(codeValue("GB4762",str));
					break;
					//两个民主党派,各项之间用分号隔开,如:	“民建；民盟”
				case 7:
					a01.setA0141(codeValue("GB4762",str.split("；")[0]));
					a01.setA3921(codeValue("GB4762",str.split("；")[1]));
					break;
					//三个民主党派,各项之间用分号隔开,如:	“九三；民建；民盟”
				case 8:
					a01.setA0141(codeValue("GB4762",str.split("；")[0]));
					a01.setA3921(codeValue("GB4762",str.split("；")[1]));
					a01.setA3927(codeValue("GB4762",str.split("；")[2]));
					break;
					//即是民主党派成员又是中共党员,先填写加入中共时间,分号隔开后再填写民主党派名称,如:		“200108；民建”  
				case 9:
					a01.setA0144(str.split("；")[0]);
					a01.setA0141("01");
					a01.setA3921(codeValue("GB4762",str.split("；")[1]));
					break;
					//即是民主党派成员又是中共党员,先填写加入中共时间,分号隔开后再填写民主党派名称,如:		“200108；民建；民革” 
				case 10:
					a01.setA0144(str.split("；")[0]);
					a01.setA0141(codeValue("GB4762",str.split("；")[1]));
					a01.setA3921(codeValue("GB4762",str.split("；")[2]));
					break;
					//即是民主党派成员又是中共党员,先填写民主党派名称,,再填写加入中共时间,如:		“民建2001.08”
				case 11:
					String a0144_11 = str.substring(str.length()-7);
					String a3921_11 = str.substring(0, str.length()-7);
					a01.setA0144(a0144_11.replace(".", ""));
					a01.setA0141("01");
					a01.setA3921(codeValue("GB4762",a3921_11));
					break;
					//即是民主党派成员又是中共党员,先填写民主党派名称,,再填写加入中共时间,如:		“民建200108”
				case 12:
					String a0144_12 = str.substring(str.length()-6);
					String a3921_12 = str.substring(0, str.length()-6);
					a01.setA0144(a0144_12);
					a01.setA0141("01");
					a01.setA3921(codeValue("GB4762",a3921_12));
					break;
				default:
					break;
				}
				break;
		    }
		} 

	}
	
	public static String A0195(String a0201b){
		String a0195 = "";
		HBSession sess = HBUtil.getHBSession();
		B01 b01 = null;
		if(a0201b != null && !"".equals(a0201b)){//导入的数据有些为空。 机构编码不为空。
			b01 = (B01)sess.get(B01.class, a0201b);
		}
		if(b01 != null){
			String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
			a0195 = b01.getB0111();
			if("2".equals(b0194)){//2—内设机构
				while(true){
					b01 = (B01)sess.get(B01.class, b01.getB0121());
					if(b01 == null){
						break;
					}else{
						b0194 = b01.getB0194();
						if("2".equals(b0194)){//2—内设机构
							continue;
						}else if("3".equals(b0194)){//3—机构分组
							continue;
						}else if("1".equals(b0194)){//1—法人单位
							a0195 = b01.getB0111();
							break;
						}else{
							break;
						}
					}
				}
			}
		}
		return a0195;
	}
	
	public static String A0601(String code_name){
		String sql = "select code_value from code_value t where t.code_name like '%"
				+ code_name + "%' and t.code_type='GB8561'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String code_value = "";
		if (list.size() > 0) {
			code_value = list.get(0).toString();
		}
		return code_value;
	}

	
}
