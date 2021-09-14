package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.hsqldb.lib.HashMap;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;

public class OtherTemShowPageModel extends PageModel {

	public static int i = 0;
	public static int k = 0;
	//学校及院系  所学专业
	public static Map<String,String> XXJYXmap; 
	//全日制学历学位信息
	public static Map<String,String> QRZmap; 
	//在职学历学位信息
	public static Map<String,String> ZZXXmap;
	@Override
	public int doInit() throws RadowException {
		i = 0;
		this.getPageElement("tpid").setValue((this.request.getSession().getAttribute("tpid")).toString());
		this.getPageElement("personids").setValue((this.request.getSession().getAttribute("personids")).toString());
		String personId = this.request.getSession().getAttribute("personids").toString();
		if("1 and 1=2 ".equals(personId)){
			this.getExecuteSG().addExecuteCode("hide()");
		}
		this.getExecuteSG().addExecuteCode("showdata()");
		return 0;
	}

	public String init(String personids) throws RadowException{
		String pid = personids.replace("|", "'").replace("@", ",");
		String script = "";
		script += "<script type='text/javascript'>\n";
		script += "function pageinit(){\n";
		int i = getcharnu(pid,",");
		for(int j=0;j<=i;j++){
			String querypid = "";
			if(pid.indexOf(",") == -1){
				querypid = pid;
			}else{
				querypid = pid.substring(0, pid.indexOf(","));
			}
			try {
				ResultSet res = HBUtil.getHBSession().connection().prepareStatement("select a01.a0101 from a01 where a01.a0000="+querypid+"").executeQuery();
				while(res.next()){
					script += "document.getElementById('personinfo').options.add(new Option('"+res.getString(1)+"',"+querypid+"));\n";
					
				}
				pid = pid.substring(pid.indexOf(",")+1,pid.length());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		script +="}\n";
		script +="</script>";
		return script;
	}
	@PageEvent("personShow")
	public int showTemByPid(String id) throws RadowException{
		Map<String,String> mapxl = new java.util.HashMap<String, String>();
		Map<String,String> mapxw = new java.util.HashMap<String, String>();
		Map<String,String> zmapxl = new java.util.HashMap<String, String>();
		Map<String,String> zmapxw = new java.util.HashMap<String, String>();
		String tpid = this.getPageElement("tpid").getValue();
		System.out.println(id);
		String script = "";
		String tpname = "";
		String tptype = "";
		String coljsp =""; //用来记录简历的行列
		String rowjsp =""; //用来记录简历的行列
		String jlmes ="";//用来存简历内容
		script += "var cell = document.getElementById('cellweb1');\n";
		script += "var path = getPath();\n";
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("select TPName,TPID,TPType from listoutput where TPID = '"+tpid+"' group by TPName,TPID,TPType").executeQuery();
			while(rs.next()){
				tpname = rs.getString(1);
				tptype = rs.getString(3);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if("1".equals(tptype)){
			script += "cell.openfile(ctpath+'/template/'+pinyin.getCamelChars('"+tpname+"').replace('【','').replace('】','').replace('（','').replace('）','')+'.cll','');\n";
		}else{
			script += "cell.openfile(ctpath+'/template/'+'"+tpid+"'+'.cll','');\n";
		}
//		script += "cell.WorkbookReadonly=true;\n";//设置不可编辑
		try {
			List<Object[]> listXX = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,ENDTime from listoutput where TPID = '"+tpid+"' ORDER BY PageNu ASC").list();
			for(int i=0;i<listXX.size();i++){
				int sheet = Integer.parseInt((String)listXX.get(i)[4])-1;
				String messageE = (String) listXX.get(i)[1];//此处后需添加多种判断
				String row = (String) listXX.get(i)[2];
				String line = (String) listXX.get(i)[3];
				String endtime = (String)listXX.get(i)[5];
				if("".equals(endtime)||endtime==null){
					endtime = DateUtil.getcurdate();
				}
				//多个信息项处理
				int count = getcharnu1(messageE,",");
				if(count > 0){
					String value = "";
					String ms = messageE+",";
					for(int i1=0;i1<=count;i1++){
						messageE = ms.substring(0,ms.indexOf(","));
						if(messageE.contains("a01")){
							if("a01.a0192a".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									value += "";
								}else{
									value +=listj.get(0);
								}
							}else if("a01.a0134".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
								String mes = "";
								if(listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								if(mes.length() >= 6){
									value += mes.substring(0, 4)+"."+mes.substring(4, 6);
								}else{
									value += mes;
								}
							}else if("a01.a14z101".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
								String mes = "";
								if(listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								value += mes;
								//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							}else if("a01.a15z101".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
								String mes = "";
								if(listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								value += mes;
								//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							}else if("a01.a0141".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if( listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else if("a01.a0160".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if(listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else if("a01.a0104a".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if(listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else if("a01.a0117a".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if(listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else if("a01.orgid".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if(listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else if("a01.xgsj".equals(messageE)){
								String mes = "";
								List listj1 = null;
								if(DBUtil.getDBType()==DBType.MYSQL){
									listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
								}else{
									listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
								}
								if(listj1.size()==0){
									listj1.add("");
								}
								if(listj1.size()>0){
									mes += listj1.get(0); 
								}
								value += mes;
							}else if("a01.a0195".equals(messageE)){
								List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a0000= '"+id+"') a on  a.a0195 = a02.a0201b  where a02.a0000 = '"+id+"'").list();
								if(listj1.size()==0){
									listj1.add("");
								}
								String mes = "";
								if(listj1.size()==0 || "null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
								value += mes;
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									value += "";
								}else{
									value +=listj.get(0);
								}
							}
						}else if(messageE.contains("a02")){
							if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 a02 where a02.a0000 ='"+id+"' and a0255= '1' ").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("a02.a0247".equals(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("a02.a0219".equals(messageE)){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 a02 where a02.a0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									if("1".equals(mess)){
										value += "是";
										}
									if("2".equals(mess)){
										value += "否";
										}
									}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
										}
								 }
						}else if(messageE.contains("a08")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if(messageE.contains("a29")){
							String mess = "";
							if("a29.a2907".contains(messageE)||"a29.a2949".contains(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
								}
									
							}else if("a29.a2911".contains(messageE)){
								List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if(messageE.contains("a30")){
							if(messageE.equals("a30.a3001")){
								List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if(messageE.equals("a30.a3004")){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
								}
							}else if(messageE.equals("a30.a3007a")){
								List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if(messageE.contains("a31")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if(messageE.contains("a37")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if(messageE.contains("a53")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("csny".equals(messageE)){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes =(String)listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
							value += mes;
						}else if("nl".equals(messageE) ){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								if("1".equals(tptype)){
									mes +="("+listj.get(0)+")岁";
								}else{ 
									mes +=listj.get(0);
								}
							}
							value += mes;
						}else if("zp".equals(messageE) ){
							String mes = "";
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
							if(listj.size() ==0){
								mes = "";
							}else{
								Object[] sz = listj.get(0);
								Object photopath = sz[0];
								Object photoname = sz[1];
								String ptpath = photopath.toString().toUpperCase();
								//插入数据
								//2017.04.19 yinl 修改图片地址
								String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
								script+="cell.SetCellImage('"+line+"','"+row+"', "+sheet+",cell.AddImage('"+imagepath+"'),'1','1','1');";
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'');";
							}
						}else if("ryzt".equals(messageE) ){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null || "".equals(listj.get(0))){
								mes = "";
							}else{
								mes = (String) listj.get(0);
								if("1".equals(mes)){
									mes="现职人员";
								}else if("2".equals(mes)){
									mes="离退人员";
								}else if("3".equals(mes)){
									mes="调出人员";
								}else if("4".equals(mes)){
									mes="已去世";
								}else{
									mes="其他人员";
								}
							}
							value += mes;
						}else if("rdsj".equals(messageE) ){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								int length = mes.length();
								if(mes.contains(".")){
									mes = ((String)listj.get(0)).replace(".", "");
									int indexOf = mes.indexOf("(");
									int indexOf2 = mes.indexOf(")");
									String sub = mes.substring(indexOf, length);
									String year = sub.substring(1,5)+".";
									String yue = sub.substring(5,7);
									mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
								}else if(length>=6){
									mes = ((String)listj.get(0)).replace(".", "");
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}else{
									mes = ((String)listj.get(0));
								}
							}
							value += mes;
							//-----------------------------------------------------------
						}else if("zgxl".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxlbyxx".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxlsxzy".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxlrxsj".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes= "";
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								mes = (String)listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
							value += mes;
						}else if("zgxlbisj".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes= "";
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								mes = (String)listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
							value += mes;
						}else if("zgxw".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxwbyxx".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxwsxzy".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("zgxwrxsj".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes= "";
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								mes = (String)listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
							value += mes;
						}else if("zgxwbisj".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes= "";
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								mes = (String)listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
							value += mes;
						}else if("xlqrz".equals(messageE) ){
							//------------------------------------------------------------
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += (String)listj.get(0);
							}
						}else if("rxsjqrz".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
							}
						}else if("bysjqrz".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
							}
						}else if("xwqrz".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("xxjyxql".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("xxjyxqw".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("sxzyql".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("sxzyqw".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("xlzz".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("rxsjzz".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess =(String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
							}
						}else if("bysjzz".equals(messageE) ){
							String mess =  "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
							}
						}else if("xwzz".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("xxjyxzl".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("xxjyxzw".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("sxzyzl".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("sxzyzw".equals(messageE) ){
							if(DBUtil.getDBType()==DBType.MYSQL){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else{
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}
						}else if("qrzxlrb".equals(messageE) ){
//							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0801A from a08 a08 where a08.a0837='1' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0 ||listj.get(0) ==null||"".equals(listj.get(0)) ){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("qrzxlxxrb".equals(messageE) ){
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
							String mes = "";
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
							value += mes;
						}else if("qrzxwrb".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("qrzxwxxrb".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
							String mes = "";
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
							value += mes;
						}else if("zzxlrb".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("zzxixxrb".equals(messageE) ){
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
							String mes = "";
							if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
							value += mes;
						}else if("zzxwrb".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("zzxwxxrb".equals(messageE) ){
							List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
							String mes = "";
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
							value += mes;
						}else if("dedp".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("dsdp".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("zwcc".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = '"+id+"' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("rzsj".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6);
							}
						}else if("rgzwccsj".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6);
							}
						}else if("mzsj".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								value += listj.get(0);
							}
						}else if("jl".equals(messageE) ){
							ResultSet rs;
							try {
								rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
								int t = rs.getRow();
								if(t==0){
									script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+"".replace("\n", "\\\\r\\\\n")+"');";
									script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
								}
								rs.previous();
								while(rs.next()){
									String str = rs.getString(1);
									value +=  formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
								}
								//写入状态调用简历单元格改变事件
								coljsp = line;
								rowjsp = row;
								jlmes = value;
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}else if("cw".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A order by a36.sortId,a36.a3600").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("xm".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("csnyjy".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("nljy".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("zzmmjy".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 order by a36.sortId,a36.a3600").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("gzdwjzw".equals(messageE) ){
							List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
							if(listj.size()==0){
								listj.add("");
							}
							String mes = "";
							int j = 0;
							for(int i11=0;i11<listj.size();i11++){
								if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
									mes += "";
								}else{
									mes = listj.get(i11)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if(j==6)
									break;
							}
						}else if("tbsjn".equals(messageE) ){
							String mess = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								value += "";
							}else{
								mess = (String)listj.get(0);
								value += mess.substring(0, 4)+"."+mess.substring(4, 6);
							}
						}else if("dqsj".equals(messageE) ){
							String sysDate = DateUtil.getDayOfMonth();
							sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
							value += sysDate;
						}else if("dqyhm".equals(messageE) ){
							UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
							String loginnname=user.getLoginname();
							//插入数据
							value += loginnname;
						}else if("jcnx".equals(messageE) ){
							String  mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								mes = (String)listj.get(0);//查询数据库后的信息
								if("1".equals(mes)){
									mes = "是";
								}else if("0".equals(mes)){
									mes = "否";
								}else{
									mes = "";
								}
							}
							value += mes;
						}else if("mzm".equals(messageE)){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								if(!"汉族".equals(listj.get(0))){
									mes = listj.get(0).toString();
								}else{
									mes = "";
								}
							}
							value += mes;
						}else if("xbm".equals(messageE)){
							String mes = "";
							List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
							if(listj.size()==0){
								listj.add("");
							}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
								mes = "";
							}else{
								if("男".equals(listj.get(0))){
									mes = "";
								}else{
									mes = listj.get(0).toString();
								}
							}
							value += mes;
						}else{
							
						}
						ms = ms.substring(ms.indexOf(",")+1,ms.length());
					} 
					if("1".equals(tptype)&&!"nl".equals(messageE)){
						if(XXJYXmap!=null){
							String xlmess = XXJYXmap.get("xl")+XXJYXmap.get("xlzy");
							String xwmess = XXJYXmap.get("xw")+XXJYXmap.get("xwzy");
							String xlrow = XXJYXmap.get("xlrow");
							String xwrow = XXJYXmap.get("xwrow");
							String xlline = XXJYXmap.get("xlline");
							if(xlmess.equals(xwmess)){
								script+="cell.MergeCells('"+xlline+"','"+xlrow+"','14','"+xwrow+"');";
								script+="cell.SetCellString('"+xlline+"','"+row+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}else{
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
					}else{
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
//						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}
					value = "";
					k = 0;
				}else{
					if(messageE.contains("a01")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj.size() == 0){
							listj.add("");
						}else if(listj.size() == 1 && listj.get(0) == null){
							listj.remove(0);
							listj.add("");
						}
						String mes = "";
						if("a01.a0192a".equals(messageE)||"a01.a0192".equals(messageE)){
							if(listj.size()>0){
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
						}else if("a01.a0184".equals(messageE)){
							if(listj.size()>0){
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							if("公务员登记表【表格】".equals(tpname)){
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
							}else{
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else if("a01.a0187a".equals(messageE)){
							if(listj.size()>0){
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							if("干部简要情况表【表格】".equals(tpname)){
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
							}else{
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else if("a01.a0134".equals(messageE)){
							if(listj.size()>0){
								if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
									if(listj.get(0).toString().length() == 6){
										mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
									}
									if(listj.get(0).toString().length() == 8){
										mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
									}
								}//查询数据库后的信息
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}else if("a01.a14z101".equals(messageE)){
							mes = (String) listj.get(0);
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						}else if("a01.a15z101".equals(messageE)){
							mes = (String) listj.get(0);
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						}else if("a01.a0141".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.orgid".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
							if(listj1.size()==0){
								listj1.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0160".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0104a".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0117a".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.xgsj".equals(messageE)){
							List listj1 = null;
							if(DBUtil.getDBType()==DBType.MYSQL){
								listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
							}else{
								listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
							}
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0195".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a0000= '"+id+"') a on  a.a0195 = a02.a0201b  where a02.a0000 = '"+id+"'").list();
							if(listj.size()>0){
								if(listj == null || listj.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",2);";
						}else{
							script += returnscript(listj, line, row,sheet);
						}
					}else if(messageE.contains("a02")){
						if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 a02 where a02.a0000 ='"+id+"' and a0255= '1'  ").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else if("a02.a0247".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
							script += returnscript(listj, line, row,sheet);
						}else if("a02.a0219".equals(messageE)){
							List list = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 where a0000 = '"+id+"'").list();
							String mes = "";
							if(list.size()>0){
								for(int q=0;q<list.size();q++){
									if(list == null || list.size() == 0||"".equals(list.get(q))||list.get(q)==null){
										mes += "";
									}else{
										mes += list.get(q).toString().replace("\n", "\\\\r\\\\n");
									}
								}
							}
							if("1".equals(mes)){
								mes = "是";
							}
							if("2".equals(mes)){
								mes = "否";
							}
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+mes+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",0);";
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
							script += returnscript(listj, line, row,sheet);
						}
					}else if(messageE.contains("a08")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a29")){
						if("a29.a2907".contains(messageE)||"a29.a2949".contains(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscriptsj(listj, line, row,sheet);
						}else if("a29.a2911".contains(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
						
					}else if(messageE.contains("a30")){
						if(messageE.equals("a30.a3001")){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else if(messageE.equals("a30.a3004")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscriptsj(listj, line, row,sheet);
						}else if(messageE.equals("a30.a3007a")){
							List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
						
					}else if(messageE.contains("a31")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a37")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a53")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)||"a53.a5319".equals(messageE)||"a53.a5317".equals(messageE)){
							String mes = "";
							if(listj.size()>0){
								if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
						}else{
						script += returnscript(listj, line, row,sheet);
						}
					}else if("csny".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj.size() ==0){
							mes = "";
						}else{
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("nl".equals(messageE) ){
						String nowdata = DateUtil.getcurdate();
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if( listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							if("1".equals(tptype)){
								mes +="("+listj.get(0)+")岁";
							}else{
								mes +=listj.get(0);
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("zp".equals(messageE) ){
						String mes = "";
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
						if(listj.size() ==0){
							mes = "";
						}else{
							Object[] sz = listj.get(0);
							Object photopath = sz[0];
							Object photoname = sz[1];
							String ptpath = photopath.toString().toUpperCase();
							//插入数据
							//2017.04.19 yinl 修改图片地址
							String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
							script+="cell.SetCellImage('"+line+"','"+row+"', "+sheet+",cell.AddImage('"+imagepath+"'),'1','1','1');";
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'');";
					}else if("ryzt".equals(messageE) ){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0  || "".equals(((String)listj.get(0))) ||listj.get(0) == null ||"".equals(listj.get(0))){
							mes = "";
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
							}else{
								mes = (String) listj.get(0);
								if("1".equals(mes)){
									mes="现职人员";
									}else if("2".equals(mes)){
										mes="离退人员";
									}else if("3".equals(mes)){
										mes="调出人员";
									}else if("4".equals(mes)){
										mes="已去世";
									}else{
										mes="其他人员";
									}
									script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
									script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
								}
					}else if("rdsj".equals(messageE) ){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							int length = mes.length();
							if(mes.contains(".")){
								mes = ((String)listj.get(0)).replace(".", "");
								int indexOf = mes.indexOf("(");
								int indexOf2 = mes.indexOf(")");
								String sub = mes.substring(indexOf, length);
								String year = sub.substring(1,5)+".";
								String yue = sub.substring(5,7);
								mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
							}else if(length>=6){
								mes = ((String)listj.get(0)).replace(".", "");
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}else{
								mes = ((String)listj.get(0));
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("xlqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rxsjqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
	
					}else if("bysjqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("xwqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("xxjyxql".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null  and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xxjyxqw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyql".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyqw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xlzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rxsjzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("bysjzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("xwzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("xxjyxzl".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
	//						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != ''  and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xxjyxzw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and  a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and  a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyzl".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyzw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("zgxl".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlbyxx".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlsxzy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlrxsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxlbisj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwbyxx".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwsxzy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwrxsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxwbisj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("qrzxlrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0 ||listj.get(0)==null||"".equals(listj.get(0)) ){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
							if(listj!=null&&listj.size()>0){
								script += returnscript(listj, line, row,sheet);
							/*	if(listj.get(0) != null ){
									script += returnscript(listj, line, row,sheet);
	//								linec = line;
	//								rowc = row;
								}*//*else{
									typec = "0";
									linec = line;
									rowc = row;
								}*/
							}
						}
						
					}else if("qrzxlxxrb".equals(messageE) ){
	//					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj.size() ==0){
							mes = "";
						}else{
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
						}
						if("任免审批表【表格】".equals(tpname)){
							mapxl.put("xlrb", mes);
							mapxl.put("xlrbl", line);
							mapxl.put("xlrbr", row);
							mapxl.put("xlsheet",String.valueOf(sheet));
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
	//===============================================================
						/*if("1".equals(tptype)){
							if(QRZmap==null){
								QRZmap = new java.util.HashMap<String, String>();
							}
							QRZmap.put("qrzxlxxrb", mes);
							QRZmap.put("qrzxlxxrbline", line);
							QRZmap.put("qrzxlxxrbrow", row);
						}else{*/
							//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							//script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
	//					}
	//===========================================================						
					}else if("qrzxwrb".equals(messageE) ){
	//					List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0901A from a08 a08 where a08.a0837='1' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
							if(listj!=null&&listj.size()>0){
							/*	if(listj.get(0) != null ){
									if("0".equals(typec)){
										script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
										script += returnscript(listj, linec, rowc,sheet);
									}*/
									script += returnscript(listj, line, row,sheet);
								/*}else{
									script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
								}*/
							}
						}
					}else if("qrzxwxxrb".equals(messageE) ){
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj.size() ==0){
							mes = "";
						}else{
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
						}
						if("任免审批表【表格】".equals(tpname)){
							mapxw.put("xwrb", mes);
							mapxw.put("xwrbl", line);
							mapxw.put("xwrbr", row);
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
	//===========================================================================					
						/*if("1".equals(tptype)){
							if(QRZmap==null){
								QRZmap = new java.util.HashMap<String, String>();
							}
							QRZmap.put("qrzxwxxrb", mes);
							QRZmap.put("qrzxwxxrbline", line);
							QRZmap.put("qrzxwxxrbrow", row);
							QRZmap.put("sheet", sheet+"");
						}else{*/
							//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							//script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
	//					}
	//===========================================================================					
					}else if("zzxlrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
					//		if(listj.get(0) != null ){
								script += returnscript(listj, line, row,sheet);
	//							linec = line;
	//							rowc = row;
								/*	}else{
								typec = "0";
								linec = line;
								rowc = row;
							}*/
						}
					}else if("zzxixxrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						System.out.println(listj.size());
						if(listj.size() ==0){
							mes = "";
						}else{
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
						}
						if("任免审批表【表格】".equals(tpname)){
							zmapxl.put("xlrb", mes);
							zmapxl.put("xlrbl", line);
							zmapxl.put("xlrbr", row);
							zmapxl.put("xlsheet",String.valueOf(sheet));
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
						/*if("1".equals(tptype)){
							if(ZZXXmap==null){
								ZZXXmap = new java.util.HashMap<String, String>();
							}
							ZZXXmap.put("zzxixxrb", mes);
							ZZXXmap.put("zzxixxrbline", line);
							ZZXXmap.put("zzxixxrbrow", row);
							ZZXXmap.put("sheet", sheet+"");
						}else{
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							
	//					}
	*/				}else if("zzxwrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
					//		if(listj.get(0) != null ){
								/*if("0".equals(typec)){
									script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
									script += returnscript(listj, linec, rowc,sheet);
								}*/
								script += returnscript(listj, line, row,sheet);
								/*	}else{
								script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
							}*/
						}
					}else if("zzxwxxrb".equals(messageE) ){
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj.size() ==0){
							mes = "";
						}else{
							if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
								mes += "";
							}else{
								mes +=listj.get(0);
							}
						}
						if("任免审批表【表格】".equals(tpname)){
							zmapxw.put("xwrb", mes);
							zmapxw.put("xwrbl", line);
							zmapxw.put("xwrbr", row);
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
						/*if("1".equals(tptype)){
							if(ZZXXmap==null){
								ZZXXmap = new java.util.HashMap<String, String>();
							}
							ZZXXmap.put("zzxwxxrb", mes);
							ZZXXmap.put("zzxwxxrbline", line);
							ZZXXmap.put("zzxwxxrbrow", row);
							ZZXXmap.put("sheet", sheet+"");
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
	//					}
	*/				}else if("dedp".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("dsdp".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3927 and cv.code_type='GB4762'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zwcc".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = '"+id+"' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rzsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'');";
						}else{
							String value = (String)listj.get(0);
							value = value.substring(0, 4)+"."+value.substring(4, 6);
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+value+"');";
						}
					}else if("rgzwccsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("mzsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						if(listj.size()>0){
							if(listj.get(0)!=null&&!"".equals(listj.get(0))){
								if(listj.get(0).toString().length()>=4){
									mes += listj.get(0).toString().substring(0,4);
									if(listj.get(0).toString().length()>=6){
										mes += "."+listj.get(0).toString().substring(4,6);
										if(listj.get(0).toString().length()>=8){
											mes += "."+listj.get(0).toString().substring(6,8);
										}
									}
								}
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("jl".equals(messageE) ){
						ResultSet rs;
						try {
							rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
							int t = rs.getRow();
							if(t==0){
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+"".replace("\n", "\\\\r\\\\n")+"');";
								script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
							}
							//rs.previous();
							while(rs.next()){
								String mes = "";
								if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
									mes = "";
								}else{
									mes = formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
								}
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
								jlmes = mes;
							}
							coljsp = line;
							rowjsp = row;
							
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else if("cw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("xm".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("csnyjy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("nljy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("zzmmjy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("SELECT (select cv.code_name from code_value cv where cv.code_type='GB4762' and cv.code_value=a36.a3627) a from a36 a36 where a36.a0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()!=0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("gzdwjzw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("tbsjn".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("dqsj".equals(messageE) ){
						String sysDate = DateUtil.getcurdate();
						sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+sysDate+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("dqyhm".equals(messageE) ){
						UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
						String loginnname=user.getLoginname();
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+loginnname+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("jcnx".equals(messageE) ){
						 //3，如果匹配成功之编写sql获取信息，
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							mes = (String)listj.get(0);//查询数据库后的信息
							if("1".equals(mes)){
								mes = "是";
							}else if("0".equals(mes)){
								mes = "否";
							}else{
								mes = "";
							}
						}
						 //4, 获取该项的行列信息值进行插入
						//插入数据
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						
					}else if("mzm".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							if(!"汉族".equals(listj.get(0))){
								mes = listj.get(0).toString();
							}else{
								mes = "";
							}
						}
						List<String> list = new ArrayList<String>();
						list.add(mes);
						script += returnscript(list, line, row,sheet);
					}else if("xbm".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							if("男".equals(listj.get(0))){
								mes = "";
							}else{
								mes = listj.get(0).toString();
							}
						}
						List<String> list = new ArrayList<String>();
						list.add(mes);
						script += returnscript(list, line, row,sheet);
					}
				}
				//用于简历字体的调试
				if("公务员登记表【表格】".equals(tpname)  && "jl".equals(messageE) ){
					script+="var len = '"+jlmes+"'.length;";
					script+="if(len > 910){";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 1);";
					script+="}else{";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 0);";
					script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', 0, 14);";
					script+="}";	
				}
				//用于简历字体的调试
				if("干部简要情况表【表格】".equals(tpname)&& "jl".equals(messageE) ){
					script+="var len = '"+jlmes+"'.length;";
					script+="if(len > 825){";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 1);";
					script+="}else{";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 0);";
					script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', 0, 11);";
					script+="}";	
				}
				if("奖励审批表【表格】".equals(tpname) && "jl".equals(messageE) ){
					script+="var len = '"+jlmes+"'.length;";
					script+="if(len > 500){";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 1);";
					script+="}else{";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 0);";
					script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', 0, 11);";
					script+="}";	
				}
				if("任免审批表【表格】".equals(tpname) && "jl".equals(messageE) ){
					script+="var len = '"+jlmes+"'.length;";
					script+="if(len > 1044){";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 1);";
					script+="}else{";
					script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', 0, 0);";
					script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', 0, 14);";
					script+="}";	
				}
			}
			if("任免审批表【表格】".equals(tpname)){
				String xlmes = mapxl.get("xlrb");
				String xlrbline = mapxl.get("xlrbl");
				String xlrbrow = mapxl.get("xlrbr");
				String xlsheet = mapxl.get("xlsheet");
				String xwmes = mapxw.get("xwrb");
				String xwrbline = mapxw.get("xwrbl");
				String xwrbrow = mapxw.get("xwrbr");
				if((!"".equals(xlmes)||xlmes !=null) && (!"".equals(xwmes)||xwmes != null) && xlmes.equals(xwmes)){
					script+="cell.SetCellString("+xlrbline+","+xlrbrow+", "+xlsheet+",' ');";
					script+="cell.SetCellString("+xwrbline+","+xwrbrow+", "+xlsheet+",' ');";
					script+="cell.MergeCells("+ xlrbline +","+ xlrbrow +", '24',"+xwrbrow+");";
					script+="cell.SetCellString("+xlrbline+","+xlrbrow+", "+xlsheet+",'"+xlmes+"');";
					script+="cell.SetCellAlign("+xlrbline+","+xlrbrow+", "+xlsheet+",'36');";
					script+="cell.SetCellTextStyle("+xlrbline+","+xlrbrow+", "+xlsheet+",'2');";
				}else if((xlmes == null || "".equals(xlmes)) && (xwmes != null && !"".equals(xwmes))){
					script+="cell.SetCellString('"+xlrbline+"','"+xlrbrow+"', "+xlsheet+",'');";
					script+="cell.SetCellString('"+xwrbline+"','"+xwrbrow+"', "+xlsheet+",'');";
					script+="cell.SetCellString('"+xwrbline+"','"+xwrbrow+"', "+xlsheet+",'"+xwmes+"');";
					script+="cell.SetCellAlign("+xwrbline+","+xwrbrow+", "+xlsheet+",'36');";
					script+="cell.SetCellTextStyle("+xwrbline+","+xwrbrow+", "+xlsheet+",'2');";
				}else if((xlmes != null && !"".equals(xlmes)) && (xwmes == null ||"".equals(xwmes))){
					script+="cell.SetCellString('"+xlrbline+"','"+xlrbrow+"', "+xlsheet+",'');";
					script+="cell.SetCellString('"+xwrbline+"','"+xwrbrow+"', "+xlsheet+",'');";
					script+="cell.SetCellString('"+xlrbline+"','"+xlrbrow+"', "+xlsheet+",'"+xlmes+"');";
					script+="cell.SetCellTextStyle("+xlrbline+","+xlrbrow+", "+xlsheet+",'2');";
					script+="cell.SetCellAlign("+xlrbline+","+xlrbrow+", "+xlsheet+",'36');";
				}else{
					script+="cell.SetCellString('"+xlrbline+"','"+xlrbrow+"', "+xlsheet+",'');";
					script+="cell.SetCellString('"+xwrbline+"','"+xwrbrow+"', "+xlsheet+",'');";
				}
				String zxlmes = zmapxl.get("xlrb");
				String zxlrbline = zmapxl.get("xlrbl");
				String zxlrbrow = zmapxl.get("xlrbr");
				String zxlsheet = zmapxl.get("xlsheet");
				String zxwmes = zmapxw.get("xwrb");
				String zxwrbline = zmapxw.get("xwrbl");
				String zxwrbrow = zmapxw.get("xwrbr");
				if((!"".equals(zxlmes)||zxlmes !=null) && (!"".equals(zxwmes)||zxwmes != null) && zxlmes.equals(zxwmes)){
					script+="cell.SetCellString("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",' ');";
					script+="cell.SetCellString("+zxwrbline+","+zxwrbrow+", "+zxlsheet+",' ');";
					script+="cell.MergeCells("+ zxlrbline +","+ zxlrbrow +", '24',"+zxwrbrow+");";
					script+="cell.SetCellString("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",'"+zxlmes+"');";
					script+="cell.SetCellAlign("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",'36');";
					script+="cell.SetCellTextStyle("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",'2');";
				}else if((zxlmes == null || "".equals(zxlmes)) && (zxwmes != null && !"".equals(zxwmes))){
					script+="cell.SetCellString('"+zxlrbline+"','"+zxlrbrow+"', "+zxlsheet+",'');";
					script+="cell.SetCellString('"+zxwrbline+"','"+zxwrbrow+"', "+zxlsheet+",'');";
					script+="cell.SetCellString('"+zxwrbline+"','"+zxwrbrow+"', "+zxlsheet+",'"+zxwmes+"');";
					script+="cell.SetCellAlign("+zxwrbline+","+zxwrbrow+", "+zxlsheet+",'36');";
					script+="cell.SetCellTextStyle("+zxwrbline+","+zxwrbrow+", "+zxlsheet+",'2');";
				}else if((zxlmes != null && !"".equals(zxlmes)) && (zxwmes == null ||"".equals(zxwmes))){
					script+="cell.SetCellString('"+zxlrbline+"','"+zxlrbrow+"', "+zxlsheet+",'');";
					script+="cell.SetCellString('"+zxwrbline+"','"+zxwrbrow+"', "+zxlsheet+",'');";
					script+="cell.SetCellString('"+zxlrbline+"','"+zxlrbrow+"', "+zxlsheet+",'"+zxlmes+"');";
					script+="cell.SetCellAlign("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",'36');";
					script+="cell.SetCellTextStyle("+zxlrbline+","+zxlrbrow+", "+zxlsheet+",'2');";
				}else{
					script+="cell.SetCellString('"+zxlrbline+"','"+zxlrbrow+"', "+zxlsheet+",'');";
					script+="cell.SetCellString('"+zxwrbline+"','"+zxwrbrow+"', "+zxlsheet+",'');";
				}
			
			}
			if("1".equals(tptype)){
				if("干部任免审批表（无拟任免）【表格】".equals(tpname)||"干部任免审批表【表格】".equals(tpname)){
					if(QRZmap!=null){
						String xlmess = QRZmap.get("qrzxlxxrb");
						String xwmess = QRZmap.get("qrzxwxxrb");
						String xlrow = QRZmap.get("qrzxlxxrbrow");
						String xwrow = QRZmap.get("qrzxwxxrbrow");
						String xlline = QRZmap.get("qrzxlxxrbline");
						String sheet = QRZmap.get("sheet");
						if(xlmess.equals(xwmess)){
							script+="cell.MergeCells('"+xlline+"','"+xlrow+"','24','"+xwrow+"');";
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
						}else{
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
						}
					
					}
					if(ZZXXmap!=null){
						String xlmess = ZZXXmap.get("zzxixxrb");
						String xwmess = ZZXXmap.get("zzxwxxrb");
						String xlrow = ZZXXmap.get("zzxixxrbrow");
						String xwrow = ZZXXmap.get("zzxwxxrbrow");
						String xlline = ZZXXmap.get("zzxixxrbline");
						String sheet = ZZXXmap.get("sheet");
						if(xlmess.equals(xwmess)){
							script+="cell.MergeCells('"+xlline+"','"+xlrow+"','24','"+xwrow+"');";
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
						}else{
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
						}
					}
				}if("公务员登记表【表格】".equals(tpname)){
					if(QRZmap!=null){
						String xlmess = QRZmap.get("qrzxlxxrb");
						String xwmess = QRZmap.get("qrzxwxxrb");
						String xlrow = QRZmap.get("qrzxlxxrbrow");
						String xwrow = QRZmap.get("qrzxwxxrbrow");
						String xlline = QRZmap.get("qrzxlxxrbline");
						String sheet = QRZmap.get("sheet");
						if(xlmess.equals(xwmess)){
							script+="cell.MergeCells('"+xlline+"','"+xlrow+"','15','"+xwrow+"');";
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							/*script += " var aa = cell.GetCellString2('6','12',"+sheet+");";
							script += "alert(aa);";
							script += " var aa = cell.GetCellString('6','12',"+sheet+");";
							script += "alert(aa);";*/
							
							
						}else{
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
							/*script += " var aa = cell.GetCellString2('6','12',"+sheet+");";
							script += "alert(aa);";
							script += " var aa = cell.GetCellString('6','12',"+sheet+");";
							script += "alert(aa);";*/
							

						}
					}
					if(ZZXXmap!=null){
						String xlmess = ZZXXmap.get("zzxixxrb");
						String xwmess = ZZXXmap.get("zzxwxxrb");
						String xlrow = ZZXXmap.get("zzxixxrbrow");
						String xwrow = ZZXXmap.get("zzxwxxrbrow");
						String xlline = ZZXXmap.get("zzxixxrbline");
						String sheet = ZZXXmap.get("sheet");
						if(xlmess.equals(xwmess)){
							script+="cell.MergeCells('"+xlline+"','"+xlrow+"','15','"+xwrow+"');";
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
						}else{
							script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
						}
					}
				}
			}
//			System.out.println(script);
			this.getPageElement("selectedid").setValue(script);
			this.getExecuteSG().addExecuteCode("docellshow()");
		} catch (Exception e) {
			e.printStackTrace();
		}
		XXJYXmap = null;
		QRZmap = null;
		ZZXXmap = null;
		k = 0;
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	//获得某一字符串包含某一字符的个数
	public int getcharnu(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			i++;
			getcharnu(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return i;
		}
		return 0;
	}
	
	//获得某一字符串包含某一字符的个数
	/*public int getcharnu1(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			k++;
			getcharnu(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return k;
		}
		return 0;
	}*/
	public int getcharnu1(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			k++;
			getcharnu1(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return k;
		}
		return 0;
	}
	
	public String returnscript(List list,String line,String row,int sheet){
		String script = "";
		String mes = "";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if("".equals(list.get(i))||list.get(i)==null){
					mes += "";
				}else{
					mes += list.get(i).toString().replace("\n", "\\\\r\\\\n")+"\\\\r\\\\n";
				}
			}
		}
/*		if(!"".equals(mes)){
			mes = mes.substring(0, mes.length()-6);
		}*/
		script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+mes+"');";
		script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
		script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",0);";
//		script += "cell.SetCellFontAutoZoom("+line+","+row+", "+sheet+",1);";
		return script;
	}
	//处理时间
	public String returnscriptsj(List list,String line,String row,int sheet){
		String script = "";
		String mes = "";
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				if("".equals(list.get(i))||list.get(i)==null){
					mes += "";
				}else{
					mes += list.get(i).toString().replace("\n", "\\\\r\\\\n")+"\\\\r\\\\n";
				}
			}
		}
		if(!"".equals(mes)){
			mes = mes.substring(0, 4)+"."+mes.substring(4, 6);

		}
		script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+mes+"');";
		script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
		script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",0);";
//		script += "cell.SetCellFontAutoZoom("+line+","+row+", "+sheet+",1);";
		return script;
	}
	
	public String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	originaljl.append(jl).append("\r\n");
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	private void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//32个字一行。
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 64个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//32个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32个字一行但不足64个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行65个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	@PageEvent("uploadfile")
	public int uploadfile() throws RadowException,Exception{
//		String savepath = "C:/Users/lzy/Desktop/ceshi.doc";
//		try {
//			File file = new File(savepath);
//			file.createNewFile();
//			InputStream in =new FileInputStream("C:/Users/lzy/Desktop/ceshi.xls");  
//			FileOutputStream out = new FileOutputStream(savepath);
//			byte buffer[] = new byte[1024];
//			int len = 0;
//			while((len=in.read(buffer))>0){
//				out.write(buffer,0,len);
//			}
//			in.close();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("success");
		InputStream bodyIs = new FileInputStream("C:\\Users\\lzy\\Desktop\\测试.html");
//		File file = new File("C:\\Users\\lzy\\Desktop\\测试.html");
//		if(file.exists()&&file.isFile()){
//			file.delete();
//		}
//		InputStream cssIs = new FileInputStream("C:\\Users\\lzy\\Desktop\\测试.css");
		String body = this.getContent(bodyIs);
//		String css = this.getContent(cssIs);
		//拼一个标准的HTML格式文档
		String content = "<html><head><style>" +"" + "</style></head><body>" + body + "</body></html>";
		InputStream is = new ByteArrayInputStream(content.getBytes("GBK"));
		OutputStream os = new FileOutputStream("C:\\Users\\lzy\\Desktop\\测试.doc");
		this.inputStreamToWord(is, os);
		File file = new File("C:\\Users\\lzy\\Desktop\\测试.html");
		if(file.exists()&&file.isFile()){
			file.delete();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	public void htmlToWord2() throws Exception {
//		InputStream bodyIs = new FileInputStream("f:\\1.html");
//		InputStream cssIs = new FileInputStream("f:\\1.css");
//		String body = this.getContent(bodyIs);
//		String css = this.getContent(cssIs);
//		//拼一个标准的HTML格式文档
//		String content = "<html><head><style>" + css + "</style></head><body>" + body + "</body></html>";
//		InputStream is = new ByteArrayInputStream(content.getBytes("GBK"));
//		OutputStream os = new FileOutputStream("f:\\1.doc");
//		this.inputStreamToWord(is, os);
//	}
	      
	/**
	* 把is写入到对应的word输出流os中
	* 不考虑异常的捕获，直接抛出
	* @param is
	* @param os
	* @throws IOException
	*/
	private void inputStreamToWord(InputStream is, OutputStream os) throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem();
		//对应于org.apache.poi.hdf.extractor.WordDocument
		fs.createDocument(is, "WordDocument");
		fs.writeFilesystem(os);
		os.close();
		is.close();
	}
	      
	/**
	* 把输入流里面的内容以UTF-8编码当文本取出。
	* 不考虑异常，直接抛出
	* @param ises
	* @return
	* @throws IOException
	*/
	private String getContent(InputStream... ises) throws IOException {
		if (ises != null) {
			StringBuilder result = new StringBuilder();
			BufferedReader br;
			String line;
			for (InputStream is : ises) {
				br = new BufferedReader(new InputStreamReader(is, "GB2312"));
				while ((line=br.readLine()) != null) {
					result.append(line);
				}
			}
			return result.toString();
		}
		return null;
	}
	
	@PageEvent("personupdate")
	public int personupdate(String a0000) throws RadowException,AppException{
		request.getSession().setAttribute("tema0000", a0000);
		//this.getExecuteSG().addExecuteCode("addTab('','"+this.getPageElement("peopleInfoGrid").getValue("a0000",this.getPageElement("peopleInfoGrid").getCueRowIndex()).toString()+"','"+request.getContextPath()+"/pages/publicServantManage/AddPerson2.jsp?x=1',false,false)");
		this.getExecuteSG().addExecuteCode("$h.openWin('workUnits','pages.publicServantManage.AddPerson2','修改人员信息',950,660,document.getElementById('personinfo').value,ctxPath)");
		this.setRadow_parent_data(a0000);
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	@PageEvent("openExpPathWin")
	 public int openExpPathWin() throws RadowException{
		this.setRadow_parent_data("ExpPathWin"); 
		this.openWindow("ExpPathWin", "pages.publicServantManage.PDFExpPath");
		return EventRtnType.NORMAL_SUCCESS;
	 }
	//批量处理
	@PageEvent("openExpPathWin2")
	public int openExpPathWin2(String type) throws RadowException, SQLException{
		String id = "";
		String personids  = this.request.getSession().getAttribute("personids").toString();
		String[] pid = personids.replace("|", "").replace("@", ",").split(",");
		String script = "";
			String qrzxlc = "";
			String typec = "";
			List listc = new ArrayList();
			String linec =  "";
			String rowc =  "";
			String tpid = this.getPageElement("tpid").getValue();
			String tpname = "";
			String tptype = "";
			String coljsp =""; //用来记录简历的行列
			String rowjsp =""; //用来记录简历的行列
			String jlmes ="";//用来存简历内容
			script += "var cell = document.getElementById('cellweb1');\n";
			script += "var path = getPath();\n";
			try {
				ResultSet rs = HBUtil.getHBSession().connection().prepareStatement("select TPName,TPID,TPType from listoutput where TPID = '"+tpid+"' group by TPName,TPID,TPType").executeQuery();
				while(rs.next()){
					tpname = rs.getString(1);
					tptype = rs.getString(3);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if("1".equals(tptype)){
				script += "cell.openfile(ctpath+'/template/'+pinyin.getCamelChars('"+tpname+"').replace('【','').replace('】','').replace('（','').replace('）','')+'.cll','');\n";
			}else{
				script += "cell.openfile(ctpath+'/template/'+'"+tpid+"'+'.cll','');\n";
			}
			script += "cell.WorkbookReadonly=true;\n";//设置不可编辑
			/*script += "cell.InsertSheet(2,1);";
			script += "cell.InsertSheet(3,1);";
			script += "cell.CopySheet(2,0);";
			script += "cell.CopySheet(3,1);";*/
			int maxpagu = 0;
			ResultSet rss = HBUtil.getHBSession().connection().prepareStatement("select max(pagenu) from listoutput where TPID = '"+tpid+"'").executeQuery();
			while(rss.next()){
				maxpagu = Integer.parseInt(rss.getString(1));
			}
			for(int b =0;b<pid.length;b++){
				if(b==0){
					for(int a = 0;a<maxpagu;a++){
						script += "cell.SetSheetLabel('"+a+"','第"+a+"页');";
					}
					/*script += "cell.SetSheetLabel('0','第0页');";
					script += "cell.SetSheetLabel('1','第1页');";*/
					int a = maxpagu;
					for(int w = 0;w<(pid.length-1)*maxpagu;w++){
						script += "cell.InsertSheet("+a+",1);";
						if(a%2 == 0){
							script += "cell.CopySheet("+a+",0);";
						}else{
							script += "cell.CopySheet("+a+",1);";
						}
						a++;
					}
				}
				id = pid[b].toString();
			try {
				List<Object[]> listXX = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,ENDTime from listoutput where TPID = '"+tpid+"' ORDER BY PageNu ASC").list();
				for(int i=0;i<listXX.size();i++){
					int sheet = Integer.parseInt((String)listXX.get(i)[4])-1;
					sheet = b*maxpagu +sheet;
					String messageE = (String) listXX.get(i)[1];//此处后需添加多种判断
					if(messageE.equals("a08")){
					}
					String row = (String) listXX.get(i)[2];
					String line = (String) listXX.get(i)[3];
					String endtime = (String)listXX.get(i)[5];
					if("".equals(endtime)||endtime==null){
						endtime = DateUtil.getcurdate();
					}
					//多个信息项处理
					int count = getcharnu1(messageE,",");
					if(count > 0){
						String value = "";
						String ms = messageE+",";
						for(int i1=0;i1<=count;i1++){
							messageE = ms.substring(0,ms.indexOf(","));
							if(messageE.contains("a01")){
								if("a01.a0192a".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										value += "";
									}else{
										value +=listj.get(0);
									}
								}else if("a01.a0134".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
									String mes = "";
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										mes += "";
									}else{
										mes +=listj.get(0);
									}
									if(mes.length() >= 6){
										value += mes.substring(0, 4)+"."+mes.substring(4, 6);
									}else{
										value += mes;
									}
								}else if("a01.a14z101".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
									String mes = "";
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										mes += "";
									}else{
										mes +=listj.get(0);
									}
									value += mes;
									//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								}else if("a01.a15z101".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
									String mes = "";
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										mes += "";
									}else{
										mes +=listj.get(0);
									}
									value += mes;
									//script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								}else if("a01.a0141".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else if("a01.a0160".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else if("a01.a0104a".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else if("a01.a0117a".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else if("a01.orgid".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else if("a01.xgsj".equals(messageE)){
									String mes = "";
									List listj1 = null;
									if(DBUtil.getDBType()==DBType.MYSQL){
										listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
									}else{
										listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
									}
									if(listj1.size()==0){
										listj1.add("");
									}
									if(listj1.size()>0){
										mes += listj1.get(0); 
									}
									value += mes;
								}else if("a01.a0195".equals(messageE)){
									List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a0000= '"+id+"') a on  a.a0195 = a02.a0201b  where a02.a0000 = '"+id+"'").list();
									if(listj1.size()==0){
										listj1.add("");
									}
									String mes = "";
									if(listj1 == null || listj1.size() == 0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
										mes += "";
									}else{
										mes +=listj1.get(0);
									}
									value += mes;
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										value += "";
									}else{
										value +=listj.get(0);
									}
								}
							
							}else if(messageE.contains("a02")){
								if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 a02 where a02.a0000 ='"+id+"' and a0255= '1' ").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else if("a02.a0247".equals(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else if("a02.a0219".equals(messageE)){
									String mess = "";
									List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 a02 where a02.a0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										mess = (String)listj.get(0);
										if("1".equals(mess)){
											value += "是";
										}
										if("2".equals(mess)){
											value += "否";
										}
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if(messageE.contains("a08")){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if(messageE.contains("a29")){
								String mess = "";
								if("a29.a2907".contains(messageE)||"a29.a2949".contains(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj == null || listj.size() == 0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										value += "";
									}else{
										mess = (String)listj.get(0);
										value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
									}
								}else if("a29.a2911".contains(messageE)){
									List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if(messageE.contains("a30")){
								if(messageE.equals("a30.a3001")){
									List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else if(messageE.equals("a30.a3004")){
									String mess = "";
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
										value += "";
									}else{
										mess = (String)listj.get(0);
										value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
									}
								}else if(messageE.equals("a30.a3007a")){
									List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
								
							}else if(messageE.contains("a31")){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if(messageE.contains("a37")){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if(messageE.contains("a53")){
								List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("csny".equals(messageE)){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\\\r\\\\n";
								}
								value += mes;
							}else if("nl".equals(messageE) ){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes += "";
								}else{
									if("1".equals(tptype)){
										mes +="("+listj.get(0)+")岁";
									}else{ 
										mes +=listj.get(0);
									}
								}
								value += mes;
							}else if("zp".equals(messageE) ){
								String mes = "";
								List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
								if(listj.size() ==0){
									mes = "";
								}else{
									Object[] sz = listj.get(0);
									Object photopath = sz[0];
									Object photoname = sz[1];
									String ptpath = photopath.toString().toUpperCase();
									//插入数据
									//2017.04.19 yinl 修改图片地址
									String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
									script+="cell.SetCellImage('"+line+"','"+row+"', "+sheet+",cell.AddImage('"+imagepath+"'),'1','1','1');";
									script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'');";
								}
							}else if("ryzt".equals(messageE) ){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null ||"".equals(listj.get(0))){
									mes = "";
								}else{
									mes = (String) listj.get(0);
									if("1".equals(mes)){
										mes="现职人员";
									}else if("2".equals(mes)){
										mes="离退人员";
									}else if("3".equals(mes)){
										mes="调出人员";
									}else if("4".equals(mes)){
										mes="已去世";
									}else{
										mes="其他人员";
									}
								}
								value += mes;
							}else if("rdsj".equals(messageE) ){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									int length = mes.length();
									if(mes.contains(".")){
										mes = ((String)listj.get(0)).replace(".", "");
										int indexOf = mes.indexOf("(");
										int indexOf2 = mes.indexOf(")");
										String sub = mes.substring(indexOf, length);
										String year = sub.substring(1,5)+".";
										String yue = sub.substring(5,7);
										mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
									}else if(length>=6){
										mes = ((String)listj.get(0)).replace(".", "");
										mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
									}else{
										mes = ((String)listj.get(0));
									}
								}
								value += mes;
							}else if("zgxl".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
								
							}else if("zgxlbyxx".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
								
							}else if("zgxlsxzy".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
								
							}else if("zgxlrxsj".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes= "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									mes = (String)listj.get(0);
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}
								value += mes;
							}else if("zgxlbisj".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes= "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									mes = (String)listj.get(0);
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}
								value += mes;
							}else if("zgxw".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
								
							}else if("zgxwbyxx".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
								
							}else if("zgxwsxzy".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += (String)listj.get(0);
								}
							}else if("zgxwrxsj".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes= "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									mes = (String)listj.get(0);
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}
								value += mes;
							}else if("zgxwbisj".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes= "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									mes = (String)listj.get(0);
									mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
								}
								value += mes;
							}else if("xlqrz".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
							    if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("rxsjqrz".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
								}
							}else if("bysjqrz".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
								}
							}else if("xwqrz".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("xxjyxql".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("xxjyxqw".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("sxzyql".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("sxzyqw".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("xlzz".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("rxsjzz".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess =(String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";
								}
							}else if("bysjzz".equals(messageE) ){
								String mess =  "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6)+"\\\\r\\\\n";

								}
							}else if("xwzz".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("xxjyxzl".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("xxjyxzw".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("sxzyzl".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("sxzyzw".equals(messageE) ){
								if(DBUtil.getDBType()==DBType.MYSQL){
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}else{
									List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
									if(listj.size()==0){
										listj.add("");
									}
									if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
										value += "";
									}else{
										value += listj.get(0);
									}
								}
							}else if("qrzxlrb".equals(messageE) ){
//								List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0801A from a08 a08 where a08.a0837='1' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0 ||listj.get(0) ==null||"".equals(listj.get(0)) ){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("qrzxlxxrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
								String mes = "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								value += mes;
							}else if("qrzxwrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("qrzxwxxrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
								String mes = "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								value += mes;
							}else if("zzxlrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("zzxixxrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
								String mes = "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes += "";
								}else{
									mes +=listj.get(0);
								}	
								value += mes;
							}else if("zzxwrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("zzxwxxrb".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
								String mes = "";
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
								value += mes;
							}else if("dedp".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("dsdp".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("zwcc".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = '"+id+"' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("rzsj".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6);
								}
							}else if("rgzwccsj".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6);

								}
							}else if("mzsj".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									value += listj.get(0);
								}
							}else if("jl".equals(messageE) ){
								ResultSet rs;
								try {
									rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
									int t = rs.getRow();
									if(t==0){
										script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+"".replace("\n", "\\\\r\\\\n")+"');";
										script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
									}
									rs.previous();
									while(rs.next()){
										String str = rs.getString(1);
										value +=  formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
									}
									//写入状态调用简历单元格改变事件
									coljsp = line;
									rowjsp = row;
									jlmes = value;
								} catch (SQLException e) {
									e.printStackTrace();
								}
							}else if("cw".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A order by a36.sortId,a36.a3600").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("xm".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("csnyjy".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("nljy".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("zzmmjy".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 order by a36.sortId,a36.a3600").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("gzdwjzw".equals(messageE) ){
								List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
								if(listj.size()==0){
									listj.add("");
								}
								String mes = "";
								int j = 0;
								for(int i11=0;i11<listj.size();i11++){
									if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
										mes += "";
									}else{
										mes = listj.get(i11)+"";
									}
									script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes+"');";
									script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
									j++;
									if(j==6)
										break;
								}
							}else if("tbsjn".equals(messageE) ){
								String mess = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									value += "";
								}else{
									mess = (String)listj.get(0);
									value += mess.substring(0, 4)+"."+mess.substring(4, 6);
								}
							}else if("dqsj".equals(messageE) ){
								String sysDate = DateUtil.getDayOfMonth();
								sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
								value += sysDate;
							}else if("dqyhm".equals(messageE) ){
								UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
								String loginnname=user.getLoginname();
								//插入数据
								value += loginnname;
							}else if("jcnx".equals(messageE) ){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									mes = (String)listj.get(0);//查询数据库后的信息
									if("1".equals(mes)){
										mes = "是";
									}else if("0".equals(mes)){
										mes = "否";
									}else{
										mes = "";
									}
								}
								value += mes;
							}else if("mzm".equals(messageE)){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									if(!"汉族".equals(listj.get(0))){
										mes = listj.get(0).toString();
									}else{
										mes = "";
									}
								}
								value += mes;
							}else if("xbm".equals(messageE)){
								String mes = "";
								List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
								if(listj.size()==0){
									listj.add("");
								}
								if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
									mes = "";
								}else{
									if("男".equals(listj.get(0))){
										mes = "";
									}else{
										mes = listj.get(0).toString();
									}
								}
								value += mes;
							}
							ms = ms.substring(ms.indexOf(",")+1,ms.length());
						} 
						if("1".equals(tptype)&&!"nl".equals(messageE)){
							if(XXJYXmap!=null){
								String xlmess = XXJYXmap.get("xl")+XXJYXmap.get("xlzy");
								String xwmess = XXJYXmap.get("xw")+XXJYXmap.get("xwzy");
								String xlrow = XXJYXmap.get("xlrow");
								String xwrow = XXJYXmap.get("xwrow");
								String xlline = XXJYXmap.get("xlline");
								if(xlmess.equals(xwmess)){
									script+="cell.MergeCells('"+xlline+"','"+xlrow+"','14','"+xwrow+"');";
									script+="cell.SetCellString('"+xlline+"','"+row+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
									script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
								}else{
									script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
									script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
								}
							}else{
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else{
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+value.replace("\n", "\\\\r\\\\n")+"');";
//							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
						value = "";
						k = 0;
					
					}else{
					if(messageE.contains("a01")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj.size() == 0){
							listj.add("");
						}else if(listj.size() == 1 && listj.get(0) == null){
							listj.remove(0);
							listj.add("");
						}
						String mes = "";
						if("a01.a0192a".equals(messageE)||"a01.a0192".equals(messageE)){
							if(listj.size()>0){
								if(listj == null  || listj.size()==0 || "null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
						}else if("a01.a0184".equals(messageE)){
							if(listj.size()>0){
								if(listj == null  || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							if("公务员登记表【表格】".equals(tpname)){
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
							}else{
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else if("a01.a0187a".equals(messageE)){
							if(listj.size()>0){
								if( listj == null  || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							if("干部简要情况表【表格】".equals(tpname)){
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
							}else{
								script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							}
						}else if("a01.a0134".equals(messageE)){
							if(listj.size()>0){
								if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
									if(listj.get(0).toString().length() == 6){
										mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
									}
									if(listj.get(0).toString().length() == 8){
										mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
									}
								}//查询数据库后的信息
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}else if("a01.a14z101".equals(messageE)){
							mes = (String) listj.get(0);
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						}else if("a01.a15z101".equals(messageE)){
							mes = (String) listj.get(0);
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						}else if("a01.a0141".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
							if(listj1.size()==0){
								listj1.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if ("a01.orgid".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
							if(listj1.size()==0){
								listj1.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0160".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0104a".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0117a".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.xgsj".equals(messageE)){
							List listj1 = null;
							if(DBUtil.getDBType()==DBType.MYSQL){
								listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
							}else{
								listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
							}
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj1, line, row,sheet);
						}else if("a01.a0195".equals(messageE)){
							List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a01.a0000= a02.a0000 ) a on  a.a0195 = a02.a0201b  where a02.a0000 = '"+id+"'").list();
							if(listj.size()>0){
								if("null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
									mes += "";
								}else{
									mes +=listj1.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",2);";
						}else{
							script += returnscript(listj, line, row,sheet);
						}
					}else if(messageE.contains("a02")){
						if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 a02 where a02.a0000 ='"+id+"' and a0255= '1'  ").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else if("a02.a0247".equals(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
							script += returnscript(listj, line, row,sheet);
						}else if("a02.a0219".equals(messageE)){
							List list = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 where a0000 = '"+id+"'  ").list();
							String mes = "";
							if(list.size()>0){
								for(int q=0;q<list.size();q++){
									if("".equals(list.get(q))||list.get(q)==null){
										mes += "";
									}else{
										mes += list.get(q).toString().replace("\n", "\\\\r\\\\n");
									}
								}
							}
							if("1".equals(mes)){
								mes = "是";
							}
							if("2".equals(mes)){
								mes = "否";
							}
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+mes+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellTextStyle("+line+","+row+", "+sheet+",0);";
							
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
							script += returnscript(listj, line, row,sheet);
						}
					}else if(messageE.contains("a08")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a29")){
						if("a29.a2907".contains(messageE)||"a29.a2949".contains(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscriptsj(listj, line, row,sheet);
						}else if("a29.a2911".contains(messageE)){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscriptsj(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if(messageE.contains("a30")){
						if(messageE.equals("a30.a3001")){
							List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else if(messageE.equals("a30.a3004")){
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscriptsj(listj, line, row,sheet);
						}else if(messageE.equals("a30.a3007a")){
							List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
						
					}else if(messageE.contains("a31")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a37")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if(messageE.contains("a53")){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)||"a53.a5319".equals(messageE)||"a53.a5317".equals(messageE)){
							String mes = "";
							if(listj.size()>0){
								if(listj == null  || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
									mes += "";
								}else{
									mes +=listj.get(0);
								}
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",32);";
						}else{
						script += returnscript(listj, line, row,sheet);
						}
					}else if("csny".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj == null  || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
							mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("nl".equals(messageE) ){
						String nowdata = DateUtil.getcurdate();
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj == null  || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							if("1".equals(tptype)){
								mes +="("+listj.get(0)+")岁";
							}else{
								mes +=listj.get(0);
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("zp".equals(messageE) ){
						String mes = "";
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
						if(listj.size() ==0){
							mes = "";
						}else{
							Object[] sz = listj.get(0);
							Object photopath = sz[0];
							Object photoname = sz[1];
							String ptpath = photopath.toString().toUpperCase();
							//插入数据
							//2017.04.19 yinl 修改图片地址
							String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+ptpath+photoname;
							script+="cell.SetCellImage('"+line+"','"+row+"', "+sheet+",cell.AddImage('"+imagepath+"'),'1','1','1');";
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'');";
					}else if("ryzt".equals(messageE) ){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null ||"".equals(listj.get(0))){
							mes = "";
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
						}else{
							mes = (String) listj.get(0);
							if("1".equals(mes)){
								mes="现职人员";
							}else if("2".equals(mes)){
								mes="离退人员";
							}else if("3".equals(mes)){
								mes="调出人员";
							}else if("4".equals(mes)){
								mes="已去世";
							}else{
								mes="其他人员";
							}
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
						}
						
					}else if("rdsj".equals(messageE) ){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0||"".equals(((String)listj.get(0)))||"".equals(listj.get(0))||((String)listj.get(0))==null){
							mes = "";
						}else{
							int length = mes.length();
							if(mes.contains(".")){
								mes = ((String)listj.get(0)).replace(".", "");
								int indexOf = mes.indexOf("(");
								int indexOf2 = mes.indexOf(")");
								String sub = mes.substring(indexOf, length);
								String year = sub.substring(1,5)+".";
								String yue = sub.substring(5,7);
								mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
							}else if(length>=6){
								mes = ((String)listj.get(0)).replace(".", "");
								mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}else{
								mes = ((String)listj.get(0));
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("xlqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rxsjqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("bysjqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("xwqrz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("xxjyxql".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xxjyxqw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyql".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyqw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xlzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rxsjzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("bysjzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("xwzz".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("xxjyxzl".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A != ''  and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("xxjyxzw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and  a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and  a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyzl".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}
					}else if("sxzyzw".equals(messageE) ){
						if(DBUtil.getDBType()==DBType.MYSQL){
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet);
						}else{
							List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
							if(listj.size()==0){
								listj.add("");
							}
							script += returnscript(listj, line, row,sheet); 
						}
					}else if("zgxl".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlbyxx".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlsxzy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxlrxsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxlbisj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwbyxx".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwsxzy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zgxwrxsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("zgxwbisj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("qrzxlrb".equals(messageE) ){
//						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0801A from a08 a08 where a08.a0837='1' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
							if(listj!=null&&listj.size()>0){
								if(listj.get(0) != null ){
									script += returnscript(listj, line, row,sheet);
//									linec = line;
//									rowc = row;
								}/*else{
									typec = "0";
									linec = line;
									rowc = row;
								}*/
							}
						}
						
					}else if("qrzxlxxrb".equals(messageE) ){
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}	
						/*if("1".equals(tptype)){
							if(QRZmap==null){
								QRZmap = new java.util.HashMap<String, String>();
							}
							QRZmap.put("qrzxlxxrb", mes);
							QRZmap.put("qrzxlxxrbline", line);
							QRZmap.put("qrzxlxxrbrow", row);
						}else{*/
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
//						}
					}else if("qrzxwrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
							if(listj!=null&&listj.size()>0){
							/*	if(listj.get(0) != null ){
									if("0".equals(typec)){
										script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
										script += returnscript(listj, linec, rowc,sheet);
									}*/
									script += returnscript(listj, line, row,sheet);
								/*}else{
									script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
								}*/
							}
						}
					}else if("qrzxwxxrb".equals(messageE) ){
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						/*if("1".equals(tptype)){
							if(QRZmap==null){
								QRZmap = new java.util.HashMap<String, String>();
							}
							QRZmap.put("qrzxwxxrb", mes);
							QRZmap.put("qrzxwxxrbline", line);
							QRZmap.put("qrzxwxxrbrow", row);
							QRZmap.put("sheet", sheet+"");
						}else{*/
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
//						}
					}else if("zzxlrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
					//		if(listj.get(0) != null ){
								script += returnscript(listj, line, row,sheet);
//								linec = line;
//								rowc = row;
								/*	}else{
								typec = "0";
								linec = line;
								rowc = row;
							}*/
						}
					}else if("zzxixxrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						/*if("1".equals(tptype)){
							if(ZZXXmap==null){
								ZZXXmap = new java.util.HashMap<String, String>();
							}
							ZZXXmap.put("zzxixxrb", mes);
							ZZXXmap.put("zzxixxrbline", line);
							ZZXXmap.put("zzxixxrbrow", row);
							ZZXXmap.put("sheet", sheet+"");
						}else{*/
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
							script += "cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							
//						}
					}else if("zzxwrb".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("干部简要情况表【表格】".equals(tpname)||"奖励审批表【表格】".equals(tpname)){
							script += returnscript(listj, line, row,sheet);
						}else{
					//		if(listj.get(0) != null ){
								/*if("0".equals(typec)){
									script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
									script += returnscript(listj, linec, rowc,sheet);
								}*/
								script += returnscript(listj, line, row,sheet);
								/*	}else{
								script += "cell.MergeCells("+linec+","+rowc+",'8',"+row+");";
							}*/
						}
					}else if("zzxwxxrb".equals(messageE) ){
						List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
						String mes = "";
						if(listj==null || listj.size()==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						/*if("1".equals(tptype)){
							if(ZZXXmap==null){
								ZZXXmap = new java.util.HashMap<String, String>();
							}
							ZZXXmap.put("zzxwxxrb", mes);
							ZZXXmap.put("zzxwxxrbline", line);
							ZZXXmap.put("zzxwxxrbrow", row);
							ZZXXmap.put("sheet", sheet+"");
						}else{*/
							script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
							script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
//						}
					}else if("dedp".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("dsdp".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3927 and cv.code_type='GB4762'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("zwcc".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value coa join (select a0221 from  a01 a01 where a01.a0000 = '"+id+"' order by a01.a0221 desc ) a01 on  coa.code_value = a01.a0221 and coa.code_type = 'ZB09'").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscript(listj, line, row,sheet);
					}else if("rzsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
							if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'');";
						}else{
							String value = (String)listj.get(0);
							value = value.substring(0, 4)+"."+value.substring(4, 6);
							script += "cell.SetCellString("+line+","+row+", "+sheet+",'"+value+"');";

						}
//						script += returnscript(listj, line, row,sheet);
					}else if("rgzwccsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0288 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("mzsj".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						if(listj.size()>0){
							if(listj.get(0)!=null&&!"".equals(listj.get(0))){
								if(listj.get(0).toString().length()>=4){
									mes += listj.get(0).toString().substring(0,4);
									if(listj.get(0).toString().length()>=6){
										mes += "."+listj.get(0).toString().substring(4,6);
										if(listj.get(0).toString().length()>=8){
											mes += "."+listj.get(0).toString().substring(6,8);
										}
									}
								}
							}
						}
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("jl".equals(messageE) ){
						ResultSet rs;
						try {
							rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
							int t = rs.getRow();
							if(t==0){
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+"".replace("\n", "\\\\r\\\\n")+"');";
								script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
							}
							//rs.previous();
							while(rs.next()){
								String mes = "";
								if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
									mes = "";
								}else{
									mes = formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
								}
								script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script+="cell.SetCellNumType('"+line+"','"+row+"', "+sheet+",0);";
								jlmes = mes;
							}
							coljsp = line;
							rowjsp = row;
							
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}else if("cw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("xm".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("csnyjy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("nljy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("zzmmjy".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("SELECT (select cv.code_name from code_value cv where cv.code_type='GB4762' and cv.code_value=a36.a3627) a from a36 a36 where a36.a0000='"+id+"' order by a36.sortId,a36.a3600").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()!=0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("gzdwjzw".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
						if(listj.size()==0){
							listj.add("");
						}
						String mes = "";
						int j = 0;
						if(listj.size()>0){
							for(int i1=0;i1<listj.size();i1++){
								if("null".equals(listj.get(i1))||listj.get(i1)==null){
									mes = "";
								}else{
									mes = listj.get(i1)+"";
								}
								script += "cell.SetCellString("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",'"+mes.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+line+","+(Integer.parseInt(row)+j)+", "+sheet+",36);";
								j++;
								if("干部任免审批表【表格】".equals(tpname)||"干部任免审批表（无拟任免）【表格】".equals(tpname)){
									if(j==7)
										break;
								}else{
									if(j==9)
										break;
								}
							}
						}
					}else if("tbsjn".equals(messageE) ){
						List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
						if(listj.size()==0){
							listj.add("");
						}
						script += returnscriptsj(listj, line, row,sheet);
					}else if("dqsj".equals(messageE) ){
						String sysDate = DateUtil.getcurdate();
						sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
						//插入数据
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+sysDate+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("dqyhm".equals(messageE) ){
						UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
						String loginnname=user.getLoginname();
						//插入数据
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+loginnname+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("jcnx".equals(messageE) ){
						 //3，如果匹配成功之编写sql获取信息，
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if("".equals(((String)listj.get(0)))||listj==null || listj.size()==0||"".equals(listj.get(0))||listj.get(0) == null){
							mes = "";
						}else{
							mes = (String)listj.get(0);//查询数据库后的信息
							if("1".equals(mes)){
								mes = "是";
							}else if("0".equals(mes)){
								mes = "否";
							}else{
								mes = "";
							}
						}
						 //4, 获取该项的行列信息值进行插入
						//插入数据
						script+="cell.SetCellString('"+line+"','"+row+"', "+sheet+",'"+mes+"');";
						script += "cell.SetCellAlign("+line+","+row+", "+sheet+",36);";
					}else if("mzm".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							if(!"汉族".equals(listj.get(0))){
								mes = listj.get(0).toString();
							}else{
								mes = "";
							}
						}
						List<String> list = new ArrayList<String>();
						list.add(mes);
						script += returnscript(list, line, row,sheet);
					}else if("xbm".equals(messageE)){
						String mes = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mes = "";
						}else{
							if("男".equals(listj.get(0))){
								mes = "";
							}else{
								mes = listj.get(0).toString();
							}
						}
						List<String> list = new ArrayList<String>();
						list.add(mes);
						script += returnscript(list, line, row,sheet);
					}
					}
					//用于简历字体的调试
					if("公务员登记表【表格】".equals(tpname)  && "jl".equals(messageE) ){
						script+="var len = '"+jlmes+"'.length;";
						script+="if(len > 910){";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 1);";
						script+="}else{";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 0);";
						script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 11);";
						script+="}";	
					}
					//用于简历字体的调试
					if("干部简要情况表【表格】".equals(tpname)&& "jl".equals(messageE) ){
						script+="var len = '"+jlmes+"'.length;";
						script+="if(len > 825){";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 1);";
						script+="}else{";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 0);";
						script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 11);";
						script+="}";	
					}
					if("奖励审批表【表格】".equals(tpname) && "jl".equals(messageE) ){
						script+="var len = '"+jlmes+"'.length;";
						script+="if(len > 500){";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 1);";
						script+="}else{";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 0);";
						script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 11);";
						script+="}";	
					}
					if("任免审批表【表格】".equals(tpname) && "jl".equals(messageE) ){
						script+="var len = '"+jlmes+"'.length;";
						script+="if(len > 1044){";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 1);";
						script+="}else{";
						script+="cell.SetCellFontAutoZoom('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 0);";
						script+="cell.SetCellFontSize('"+coljsp+"', '"+rowjsp+"', '"+sheet+"', 11);";
						script+="}";	
					}
					
				}
				if("1".equals(tptype)){
					if("干部任免审批表（无拟任免）【表格】".equals(tpname)||"干部任免审批表【表格】".equals(tpname)){
						if(QRZmap!=null){
							String xlmess = QRZmap.get("qrzxlxxrb");
							String xwmess = QRZmap.get("qrzxwxxrb");
							String xlrow = QRZmap.get("qrzxlxxrbrow");
							String xwrow = QRZmap.get("qrzxwxxrbrow");
							String xlline = QRZmap.get("qrzxlxxrbline");
							String sheet = QRZmap.get("sheet");
							if(xlmess.equals(xwmess)){
								script+="cell.MergeCells('"+xlline+"','"+xlrow+"','24','"+xwrow+"');";
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							}else{
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
								script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
							}
						
						}
						if(ZZXXmap!=null){
							String xlmess = ZZXXmap.get("zzxixxrb");
							String xwmess = ZZXXmap.get("zzxwxxrb");
							String xlrow = ZZXXmap.get("zzxixxrbrow");
							String xwrow = ZZXXmap.get("zzxwxxrbrow");
							String xlline = ZZXXmap.get("zzxixxrbline");
							String sheet = ZZXXmap.get("sheet");
							if(xlmess.equals(xwmess)){
								script+="cell.MergeCells('"+xlline+"','"+xlrow+"','24','"+xwrow+"');";
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							}else{
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
								script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
							}
						}
					}if("公务员登记表【表格】".equals(tpname)){
						if(QRZmap!=null){
							String xlmess = QRZmap.get("qrzxlxxrb");
							String xwmess = QRZmap.get("qrzxwxxrb");
							String xlrow = QRZmap.get("qrzxlxxrbrow");
							String xwrow = QRZmap.get("qrzxwxxrbrow");
							String xlline = QRZmap.get("qrzxlxxrbline");
							String sheet = QRZmap.get("sheet");
							if(xlmess.equals(xwmess)){
								script+="cell.MergeCells('"+xlline+"','"+xlrow+"','15','"+xwrow+"');";
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
								/*script += " var aa = cell.GetCellString2('6','12',"+sheet+");";
								script += "alert(aa);";
								script += " var aa = cell.GetCellString('6','12',"+sheet+");";
								script += "alert(aa);";*/
								
								
							}else{
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
								script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
								/*script += " var aa = cell.GetCellString2('6','12',"+sheet+");";
								script += "alert(aa);";
								script += " var aa = cell.GetCellString('6','12',"+sheet+");";
								script += "alert(aa);";*/
								

							}
						}
						if(ZZXXmap!=null){
							String xlmess = ZZXXmap.get("zzxixxrb");
							String xwmess = ZZXXmap.get("zzxwxxrb");
							String xlrow = ZZXXmap.get("zzxixxrbrow");
							String xwrow = ZZXXmap.get("zzxwxxrbrow");
							String xlline = ZZXXmap.get("zzxixxrbline");
							String sheet = ZZXXmap.get("sheet");
							if(xlmess.equals(xwmess)){
								script+="cell.MergeCells('"+xlline+"','"+xlrow+"','15','"+xwrow+"');";
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
							}else{
								script+="cell.SetCellString('"+xlline+"','"+xlrow+"', "+sheet+",'"+xlmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xlrow+", "+sheet+",36);";
								script+="cell.SetCellString('"+xlline+"','"+xwrow+"', "+sheet+",'"+xwmess.replace("\n", "\\\\r\\\\n")+"');";
								script += "cell.SetCellAlign("+xlline+","+xwrow+", "+sheet+",36);";
							}
						}
					}
				}
//				System.out.println(script);
			} catch (Exception e) {
				e.printStackTrace();
			}
			XXJYXmap = null;
			QRZmap = null;
			ZZXXmap = null;
			k = 0;
		}
		this.getPageElement("selectedid").setValue(script);
		this.getExecuteSG().addExecuteCode("docellshow()");
		/*if("2".equals(type)){
			this.setRadow_parent_data("ExpPathWin"); 
			this.openWindow("ExpPathWin", "pages.publicServantManage.PDFExpPath");
		}else{
			this.getExecuteSG().addExecuteCode("expexcel()");
		}*/
		
		return EventRtnType.NORMAL_SUCCESS;
	}
}
