package com.insigma.siis.local.pagemodel.customquery;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.zj.Utils;

public class NotePickUpPageModel extends PageModel {

	public NotePickUpPageModel() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	public int initX() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		//String value = this.getRadow_parent_data();
		String value = this.getPageElement("subWinIdBussessId2").getValue();
		String[] values = value.split("@");
		if (values.length > 1) {
			String sql = values[0];
			StringBuffer sb = new StringBuffer();
			sql = sql.replaceAll("\\$", "\\'");
			String newsql = sql.replace("*", "a0000");
			List allSelect = sess.createSQLQuery(newsql).list();
			if (allSelect.size() > 0) {
				/*for (int i = 0; i < allSelect.size(); i++) {
					String a0000 = allSelect.get(i).toString();
					sb.append("'").append(a0000).append("',");
				}
				if (sb.length() == 0) {
					this.setMainMessage("所选人员不可操作！");
					return EventRtnType.FAILD;
				}
				String ids = sb.substring(0, sb.length() - 1);
				this.getPageElement("ids").setValue(ids);*/
				this.getPageElement("ids").setValue(newsql);
			} else {
				this.setMainMessage("请先进行人员查询！");
				return EventRtnType.FAILD;
			}
		} else {
			String[] id_arr = value.split(",");
			String ids_temp = "";
			for(int i = 0; i < id_arr.length; i ++){
				ids_temp += "'" + id_arr[i] + "',";
			}
			String ids = ids_temp.substring(0,ids_temp.length()-1);
			this.getPageElement("ids").setValue(ids);
		}

		this.setNextEventName("peopleInfoGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("peopleInfoGrid.dogridquery")
	public int queryById(int start,int limit)throws RadowException{

		String ids = this.getPageElement("ids").getValue();
		String sql = "select a01.a0000,a01.a0101,a01.a0192a, CASE a01.JLCONFIRM WHEN '1' THEN '已确认' ELSE '' END as sign from a01 where a01.a0000 in ("+ids+") order by TORGID,TORDER";
		System.out.println("----------------"+sql);
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}

	@PageEvent("initJLForOne")
	@Transaction
	public int initJLForOne() throws RadowException, AppException{
		String a0000 = this.getParameter("a0000");
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement pst = null;
		//一、初始化简历
		gridPeople(sess,conn,pst,a0000);

		this.setMainMessage("初始化成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initJL")
	@Transaction
	public int initJL() throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		Connection conn = sess.connection();
		PreparedStatement pst = null;
		//一、先初始化简历
		gridPeople(sess,conn,pst,"");

		this.setMainMessage("初始化成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public int gridPeople(HBSession sess,Connection conn,PreparedStatement pst,String a0000one) throws RadowException, AppException{
		//List<String> list = new ArrayList<String>();
		//String ids = this.getPageElement("ids").getValue();
		String userid = SysManagerUtils.getUserId();
		String sql = "";
		if(a0000one!=null&&!"".equals(a0000one)){
			sql = "select a01.a0000,a01.a1701,a01.a0101,a01.a0184 a0184,a01.a0192a from a01 where a01.a0000 = '"+a0000one+"'";
		}else{
			sql = "select a01.a0000,a01.a1701,a01.a0101,a01.a0184 a0184,a01.a0192a from a01 where not exists (select 1 from hz_a17 a17 where a17.a0000 = a01.a0000 and a17.a1709 is not null) ";
		}
		List<Object[]> vals = (List<Object[]>)sess.createSQLQuery(sql).list();

		/*System.out.println("delete from a17 t where t.a0000 in (select a01.a0000 from a01,A01SEARCHTEMP b where a01.a0000 in ("+ids+") "
				+ "and a01.a0000 in (select a0000 from a17 where a17.is_check <> '1'  "
				+ "union select a01.a0000 from a01 where not exists (select 1 from a17 where a17.a0000 = a01.a0000)) "
				+ "and a01.a0000 = b.a0000 and sessionid = '"+sid+"')");*/
		//先删除
		if(a0000one!=null&&!"".equals(a0000one)){
			sess.createSQLQuery("delete from hz_a17 t where t.a0000 = '"+a0000one+"'").executeUpdate();
		}else{
			//sess.createSQLQuery("delete from hz_a17 t where t.a0000 in (select a01.a0000 from a01 where not exists (select 1 from hz_a17 a17 where a17.a0000 = a01.a0000 and a17.a1709 is not null ))").executeUpdate();
		}


		//Connection conn = sess.connection();
		//PreparedStatement pst = null;
		String a1701 = "";
		String a0000 = "";
		String a0101 = "";
		String a0184 = "";
		String a0192a = "";
		
		String errorMeg = "";
		List<String> listErrorPerson = new ArrayList<String>();
		try {
			pst = conn.prepareStatement("insert into HZ_A17(a1700,a0000,a1701,a1702,a1703,userid,a1799,a1709,belong_to_a1700,complete) values (?,?,?,?,?,'"+userid+"',?,?,?,?)");
			int i = 0;
			if(vals!=null&&vals.size()>0){
				for(Object[] val : vals){
					a0000 = ""+val[0];
					a1701 = "";
					Object data = val[1];
					a0101 = ""+val[2];
					a0184 = ""+val[3];
					a0192a = ""+val[4];
					java.sql.Clob clob = (java.sql.Clob)data;
					if(clob!=null){
						Reader inStream = clob.getCharacterStream();
						char[] c = new char[(int) clob.length()];
						inStream.read(c);
						//data是读出并需要返回的数据，类型是String
						a1701 = new String(c);
						inStream.close();
						
						//简历处理
						try {
							int num = initA1701(a1701,a0000,pst,i);
							i = num;
						} catch (Exception e) {
							errorMeg = "("+a0000+")"+a0101+","+a0184+","+a0192a;
							listErrorPerson.add(errorMeg);
							continue;
							// TODO: handle exception
						}
						
					}
					
					
				}
				
				pst.executeBatch();
		        pst.clearParameters();
		        System.out.println("------------第"+i/5000+"次-------------5000一次-----------");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			for(String msg : listErrorPerson){
				System.out.println(msg);
			}
			if(a0000one!=null&&!"".equals(a0000one)&&listErrorPerson!=null&&listErrorPerson.size()==1){
				throw new RadowException(a0101+","+a0184+"简历拆分失败！");
			}
			if(pst!=null){
				try {
					pst.close();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RadowException(e.getMessage());
				}
			}
		}
		return EventRtnType.NORMAL_SUCCESS;

	}

	public int initA1701(String a1701,String a0000,PreparedStatement pst,int num)throws Exception{
		if(a1701!=null&&!"".equals(a1701)){
			List<Map<String,String>> lista17=new ArrayList<Map<String, String>>();
			a1701 = a1701.replaceAll("&lt;", "<");
			a1701 = a1701.replaceAll("&gt;", ">");
			a1701 = a1701.replaceAll("\\(", "\\（");
			a1701 = a1701.replaceAll("\\;", "\\；");
			a1701 = a1701.replaceAll("\\)", "\\）");
			a1701 = a1701.replaceAll("\\:", "\\：");
			a1701 = a1701.replaceAll("期间", "其间");
			a1701 = a1701.replaceAll("——", "--");
			a1701 = Utils.clearA1701(a1701);
			HBUtil.getHBSession().createSQLQuery("update a01 set a1701 = ? where a0000 = '"+a0000+"'")
				.setString(0, a1701).executeUpdate();
			HBUtil.getHBSession().flush();
			//1、替换后，   如果简历里面有  （其间：，替换为   \r\n|\r|\n （其间
			a1701 = a1701.replaceAll("\\（其间\\：", "\r\n（其间");
			//2、替换后，如果简历有还有（  ，判断（后是否跟的是日期，如果是，则也替换为   \r\n|\r|\n（

			Pattern pattern = Pattern.compile("\\（[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-—]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");
			Matcher matc = pattern.matcher(a1701);
			while(matc.find()) {
			     //System.out.println(matc.group());
			     String str = matc.group();

			     String strNew = str.replaceAll("\\（", "\r\n（");

			     a1701 = a1701.replaceAll(str, strNew);
			}

			String[] jianli = a1701.split("\r\n|\r|\n");
			String jlnr = "";
			String start = "";
			String end = "";
			String a1709 = "";
			String a1700 = "";
			String belongToA1700="";
			pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-—]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");
			Pattern pattern22 = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}");
			Pattern pattern33 = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}起");
			String err="";
			for(int i=0;i<jianli.length;i++){
				try {
				Map<String,String> map=new HashMap<String,String>();
				a1709 = "2";
				String jl = jianli[i].trim();
				//jl=jl.replaceAll(" ", "");
				if(jl==null||"".equals(jl)){
					continue;
				}
				err=jl;
				a1700 = UUID.randomUUID().toString().replaceAll("-", "");
				//如果前面是（其间，则去除，同时去除最后的）
//				if(!"".equals(jl) && jl!=null && jl.length()<4) {
//					lista17.get(lista17.size()-1).put("a1703",lista17.get(lista17.size()-1).get("a1703")+jl);
//					lista17.get(lista17.size()-1).put("complete",lista17.get(lista17.size()-1).get("complete")+jl);
//					continue;
//				}
//				else 
				if(!"".equals(jl) && jl!=null&&"（其间".equals(jl.substring(0,3))){
	        		a1709 = "3";
	        		jl = jl.substring(3);
	        		jl = jl.substring(0, jl.length()-1);
	        	}
	        	//如果前面是（，也去除，同时去除最后的）
	        	else if(!"".equals(jl) && jl!=null&&"（".equals(jl.substring(0,1))){
	        		a1709 = "4";
	        		jl = jl.substring(1);
	        		jl = jl.substring(0, jl.length()-1);
	        	}else if(!"".equals(jl) && jl!=null&&"--".equals(jl.substring(0,2))) {
	        		a1709 = "1";
	        		jl = jl.substring(11);
	        	}
	        	else {
	        		belongToA1700=a1700;
	        	}
	        	//再判断里面是否包含";"或者"；",如果有，在拆分
        		if(jl.indexOf("；")>0){
        			jl = jl.replaceAll("\\；", "\r\n");
        			String[] newjls = jl.split("\r\n|\r|\n");
        			for(String newjl : newjls){
        				Map<String,String> map0=new HashMap<String,String>();
        				Matcher matcher = pattern.matcher(newjl);
        				Matcher matcher33 = pattern33.matcher(newjl);
        				Matcher matcher22 = pattern22.matcher(newjl);
    			        if (matcher.find()) {
    			        	jlnr = "";
    						start = "";
    						end = "";
    			        	String line1 = matcher.group(0);

    			        	String[] date = line1.split("[\\-|─|-—]{1,2}");
    			        	start = date[0];
    			        	if(start!=null){
    			        		start = start.replaceAll("\\.", "");
    			        	}
    			        	end = date[1];
    			        	if(end!=null){
    			        		end = end.replaceAll("\\.", "");
    			        	}
    			        	jlnr = newjl.substring(line1.length()).trim();
    			        }else if(matcher33.find()){
    			        	jlnr = "";
    						start = "";
    						end = "";
    			        	String line1 = matcher33.group(0);

    			        	if(line1!=null){
    			        		start = line1.replaceAll("\\.", "").replace("起", "");
    			        	}
    			        	jlnr = newjl.substring(line1.length()).trim();
				        }else if(matcher22.find()){
    			        	jlnr = "";
    						start = "";
    						end = "";
    			        	String line1 = matcher22.group(0);

    			        	if(line1!=null){
    			        		end = line1.replaceAll("\\.", "");
    			        	}
    			        	jlnr = newjl.substring(line1.length()).trim();
				        }else{
				        	jlnr = "";
    						start = "";
    						end = "";

    			        	jlnr = newjl;
				        }
    			        a1700=UUID.randomUUID().toString().replaceAll("-", "");
    			        map0.put("a1700", a1700);
    			        map0.put("a0000", a0000);
    			        map0.put("a1701", start.trim());
    			        map0.put("a1702", end.trim());
    			        map0.put("a1703", jlnr);
    			        map0.put("a1799", ""+i);
    			        map0.put("a1709", a1709);
    			        map0.put("belongToA1700", belongToA1700);
    			        map0.put("complete",jlnr);
    			        lista17.add(map0);
    			        num++;
    			        if(num%5000==0){
    						pst.executeBatch();
    						pst.clearParameters();
    						System.out.println("------------第"+(num/5000)+"次-------------5000一次-----------");
    					}
        			}
        		}else{
        			Matcher matcher = pattern.matcher(jl);
        			Matcher matcher22 = pattern22.matcher(jl);
			        if (matcher.find()) {
			        	jlnr = "";
						start = "";
						end = "";
			        	String line1 = matcher.group(0);

			        	String[] date = line1.split("[\\-|─|-—]{1,2}");
			        	start = date[0];
			        	if(start!=null){
			        		start = start.replaceAll("\\.", "");
			        	}
			        	end = date[1];
			        	if(end!=null){
			        		end = end.replaceAll("\\.", "");
			        	}
			        	jlnr = jl.substring(line1.length()).trim();
			        }else if(matcher22.find()){
			        	jlnr = "";
						start = "";
						end = "";
			        	String line1 = matcher22.group(0);

			        	if(line1!=null){
			        		start = line1.replaceAll("\\.", "");
			        	}
			        	jlnr = jl.substring(line1.length()).trim();
			        }else{
			        	jlnr = "";
						start = "";
						end = "";

			        	jlnr = jl;
			        }
			        map.put("a1700", a1700);
			        map.put("a0000", a0000);
			        map.put("a1701", start.trim());
			        map.put("a1702", end.trim());
			        map.put("a1703", jlnr);
			        map.put("a1799", ""+i);
			        map.put("a1709", a1709);
			        map.put("belongToA1700", belongToA1700);
			        map.put("complete",jlnr);
			        lista17.add(map);
			        num++;
			        if(num%5000==0){
						pst.executeBatch();
						pst.clearParameters();
						System.out.println("------------第"+(num/5000)+"次-------------5000一次-----------");
					}
        		}
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				//Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}[ ]{2}");
				//Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");
			}catch(Exception e) {
				lista17.get(lista17.size()-1).put("a1703",lista17.get(lista17.size()-1).get("a1703")+err);
				lista17.get(lista17.size()-1).put("complete",lista17.get(lista17.size()-1).get("complete")+err);
			}
			}
			int index=0;
			for(int i=0;i<lista17.size();i++) {
				Map<String,String> map=lista17.get(i);
				if(i!=index && i>0) {
					if("3".equals(map.get("a1709"))) {
						String complete = lista17.get(index).get("complete");
						/*if(complete.indexOf("\n（")!=-1) {
							complete = complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701"), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702"), ".", "", "")+"  "+map.get("a1703")+"）";
						}else if(complete.indexOf("其间")!=-1) {
							complete = complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701"), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702"), ".", "", "")+"  "+map.get("a1703")+"）";
						}else {
							complete = complete+"（其间："+DateUtil.dataStrFormart(map.get("a1701"), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702"), ".", "", "")+"  "+map.get("a1703")+"）";
						}*/
						if(complete.indexOf("\n（")!=-1||complete.indexOf("其间")!=-1) {
							if(StringUtils.isEmpty(map.get("a1701").toString())){
								complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "") +map.get("a1703").toString()+"）";
							}else{
								if(StringUtils.isEmpty(map.get("a1702").toString())){
									complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"起" +map.get("a1703").toString()+"）";
								}else{
									complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "")+""+map.get("a1703").toString()+"）";
								}
							}
						}else {
							if(StringUtils.isEmpty(map.get("a1701").toString())){
								complete=complete+"（其间："+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "")+map.get("a1703").toString()+"）";

							}else{
								if(StringUtils.isEmpty(map.get("a1702").toString())){
									complete=complete+"（其间："+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"起"+map.get("a1703").toString()+"）";
								}else{
									complete=complete+"（其间："+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "")+""+map.get("a1703").toString()+"）";

								}
							}
						}
						lista17.get(index).put("complete", complete);
						pst.setString(1, map.get("a1700"));
						pst.setString(2, map.get("a0000"));
						pst.setString(3, map.get("a1701"));
						pst.setString(4, map.get("a1702"));
						pst.setString(5, map.get("a1703"));
						pst.setString(6, map.get("a1799"));
						pst.setString(7, map.get("a1709"));
						pst.setString(8, map.get("belongToA1700"));
						pst.setString(9, map.get("complete"));
						pst.addBatch();
						if(i==lista17.size()-1) {
							Map<String,String> map0=lista17.get(index);
							pst.setString(1, map0.get("a1700"));
							pst.setString(2, map0.get("a0000"));
							pst.setString(3, map0.get("a1701"));
							pst.setString(4, map0.get("a1702"));
							pst.setString(5, map0.get("a1703"));
							pst.setString(6, map0.get("a1799"));
							pst.setString(7, map0.get("a1709"));
							pst.setString(8, map0.get("belongToA1700"));
							pst.setString(9, map0.get("complete"));
							pst.addBatch();
						}
					}else if ("4".equals(map.get("a1709"))) {
						String complete = lista17.get(index).get("complete");
						//complete=complete.replace("（其间：", "\n（");
						/*if(complete.indexOf("\n（")!=-1) {
							complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701"), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702"), ".", "", "")+"  "+map.get("a1703")+"）";
						}else {
							complete=complete+"\n（"+DateUtil.dataStrFormart(map.get("a1701"), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702"), ".", "", "")+"  "+map.get("a1703")+"）";
						}*/
						
						
						if(complete.indexOf("\n（")!=-1) {
							if(StringUtils.isEmpty(map.get("a1702").toString())){
								complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"起" +map.get("a1703").toString()+"）";
							}else if(StringUtils.isEmpty(map.get("a1701").toString())){
								complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "") +map.get("a1703").toString()+"）";
							}else{
								
								complete=complete.substring(0, complete.length()-1)+"；"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "")+""+map.get("a1703").toString()+"）";
							}
						}else {
							if(StringUtils.isEmpty(map.get("a1702").toString())){
								complete=complete+"\n（"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"起"+map.get("a1703").toString()+"）";
							}else if(StringUtils.isEmpty(map.get("a1701").toString())){
								complete=complete+"\n（"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "") +map.get("a1703").toString()+"）";
							}else{
								complete=complete+"\n（"+DateUtil.dataStrFormart(map.get("a1701").toString(), ".", "", "")+"--"+DateUtil.dataStrFormart(map.get("a1702").toString(), ".", "", "")+""+map.get("a1703").toString()+"）";
							}
						}
						
						
						lista17.get(index).put("complete", complete);
						pst.setString(1, map.get("a1700"));
						pst.setString(2, map.get("a0000"));
						pst.setString(3, map.get("a1701"));
						pst.setString(4, map.get("a1702"));
						pst.setString(5, map.get("a1703"));
						pst.setString(6, map.get("a1799"));
						pst.setString(7, map.get("a1709"));
						pst.setString(8, map.get("belongToA1700"));
						pst.setString(9, map.get("complete"));
						pst.addBatch();
						if(i==lista17.size()-1) {
							Map<String,String> map0=lista17.get(index);
							pst.setString(1, map0.get("a1700"));
							pst.setString(2, map0.get("a0000"));
							pst.setString(3, map0.get("a1701"));
							pst.setString(4, map0.get("a1702"));
							pst.setString(5, map0.get("a1703"));
							pst.setString(6, map0.get("a1799"));
							pst.setString(7, map0.get("a1709"));
							pst.setString(8, map0.get("belongToA1700"));
							pst.setString(9, map0.get("complete"));
							pst.addBatch();
						}
					}
				}
				if("2".equals(map.get("a1709"))) {
					if(i!=0) {
						Map<String,String> map0=lista17.get(index);
						pst.setString(1, map0.get("a1700"));
						pst.setString(2, map0.get("a0000"));
						pst.setString(3, map0.get("a1701"));
						pst.setString(4, map0.get("a1702"));
						pst.setString(5, map0.get("a1703"));
						pst.setString(6, map0.get("a1799"));
						pst.setString(7, map0.get("a1709"));
						pst.setString(8, map0.get("belongToA1700"));
						pst.setString(9, map0.get("complete"));
						pst.addBatch();
					}
					index=i;
					if(i==lista17.size()-1) {
						pst.setString(1, map.get("a1700"));
						pst.setString(2, map.get("a0000"));
						pst.setString(3, map.get("a1701"));
						pst.setString(4, map.get("a1702"));
						pst.setString(5, map.get("a1703"));
						pst.setString(6, map.get("a1799"));
						pst.setString(7, map.get("a1709"));
						pst.setString(8, map.get("belongToA1700"));
						pst.setString(9, map.get("complete"));
						pst.addBatch();
					}
				}
				if("1".equals(map.get("a1709"))&&i==lista17.size()-1) {
					Map<String,String> map0=lista17.get(index);
					pst.setString(1, map0.get("a1700"));
					pst.setString(2, map0.get("a0000"));
					pst.setString(3, map0.get("a1701"));
					pst.setString(4, map0.get("a1702"));
					pst.setString(5, map0.get("a1703"));
					pst.setString(6, map0.get("a1799"));
					pst.setString(7, map0.get("a1709"));
					pst.setString(8, map0.get("belongToA1700"));
					pst.setString(9, map0.get("complete"));
					pst.addBatch();
					pst.setString(1, map.get("a1700"));
					pst.setString(2, map.get("a0000"));
					pst.setString(3, map.get("a1701"));
					pst.setString(4, map.get("a1702"));
					pst.setString(5, map.get("a1703"));
					pst.setString(6, map.get("a1799"));
					pst.setString(7, map.get("a1709"));
					pst.setString(8, map.get("belongToA1700"));
					pst.setString(9, map.get("complete"));
					pst.addBatch();
				}
			}
		}
		return num;
	}

	@PageEvent("peopleInfoGrid.rowclick")
	@GridDataRange
	public int persongridOnRowClick() throws RadowException, AppException{  //打开窗口的实例
		int row = this.getPageElement("peopleInfoGrid").getCueRowIndex();
		String gridA0000=this.getPageElement("peopleInfoGrid").getValue("a0000",row).toString();
		this.getPageElement("cueRowIndex").setValue(""+row);

		showView(row,gridA0000);

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("save")
	@Transaction
	public int save() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String data = this.getParameter("jsonStr");
		String a0000 = this.getParameter("a0000");
		JSONArray json = JSONArray.fromObject(data); // 首先把字符串转成 JSONArray 对象

		if (json.size() > 0) {
			List<String> listRegoin = sess.createSQLQuery("select code_name from code_ZB01").list();
			List<String> listB0101s = sess.createSQLQuery("select b0101 from code_b0101").list();

			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				// System.out.println(job.get("content")+"=") ; // 得到 每个对象中的属性值

				String a1700 = "" + job.get("a1700");
				String start = "" + job.get("start");
				String end = "" + job.get("end");
				// String content = ""+job.get("content");
				String unit = "" + job.get("unit");
				String president = "" + job.get("president");
				String region = "" + job.get("region");

				// update
				sess.createSQLQuery("update hz_a17 set start_date='" + start + "',end_date='" + end + "',unit='" + unit + "',president='" + president + "'," + "is_check='1',region='" + region + "' where a1700 = '" + a1700 + "'").executeUpdate();

				// 单位更新。对 is_check='0' 的简历进行批量更新 （针对 unit、president两个字段）
				if (unit != null && !"".equals(unit)) {
					if (!listB0101s.contains(unit)) {
						try {
							sess.createSQLQuery("insert into code_b0101 values ('" + unit + "')").executeUpdate();
							sess.createSQLQuery("update hz_a17 a set " + " a.unit = substr(a.entry_content,0,instr(a.entry_content,'" + unit + "')+length('" + unit + "')-1)," + " a.president = substr(a.entry_content,instr(a.entry_content,'" + unit + "')+length('" + unit + "')) " + " where exists(select b.a1700 from HZ_A17 b where b.a1700 = a.a1700 and b.entry_content like '%" + unit + "%' and b.is_check = '0')").executeUpdate();
						} catch (Exception e) {
						}
					}
				}

				// 地域更新。将新的地域加入到 code_zb01 中，并对其他未审的人员的地域进行更新
				if (region != null && !"".equals(region)) {
					if (!listRegoin.contains(region)) {
						try {
							sess.createSQLQuery("insert into code_zb01 values ('" + region + "')").executeUpdate();
							sess.createSQLQuery("update hz_a17 a set a.region = '" + region + "' where exists(select b.a1700 from hz_a17 b where b.a1700 = a.a1700 and b.entry_content like '%" + region + "%' and b.is_check = '0')").executeUpdate();
						} catch (Exception e) {
						}
					}
					//根据客户新要求，保存时，对单位一致的地域，也进行更新
					sess.createSQLQuery("update hz_a17 a set a.region = '" + region + "' where a.unit = '"+unit+"' and a.is_check = '0'").executeUpdate();
				}
			}

			//JLCONFIRM A01  人员简历确认
			//String a0000 = this.getPageElement("gridA0000").getValue();
			sess.createSQLQuery("update a01 set a01.JLCONFIRM = '1' where a01.a0000 = '"+a0000+"'").executeUpdate();
		}
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/*@Override
	public void closeCueWindow(String arg0) {
	this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}*/

	private String isStrNULL(String str){
		if(str==null||"".equals(str)||"null".equals(str)){
			return "";
		}else{
			return str;
		}
	}

	@PageEvent("up.onclick")
	public int up() throws RadowException, AppException{
		//selectRow
		Grid pe = (Grid) this.getPageElement("peopleInfoGrid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("到顶了！");
			}
		}
		//int row = pe.getCueRowIndex();
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(""+(cueRowIndex-1));
		String gridA0000=this.getPageElement("peopleInfoGrid").getValue("a0000",cueRowIndex-1).toString();
		showView(cueRowIndex-1,gridA0000);

		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("next.onclick")
	public int next() throws RadowException, AppException{
		//selectRow
		int size = this.getPageElement("peopleInfoGrid").getStringValueList().size();
		Grid pe = (Grid) this.getPageElement("peopleInfoGrid");pe.addRow();
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("请先选中一行！");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(size-1)){
				throw new AppException("到底了！");
			}
		}
		//int row = pe.getCueRowIndex();
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(""+(cueRowIndex+1));
		String gridA0000=this.getPageElement("peopleInfoGrid").getValue("a0000",cueRowIndex+1).toString();
		showView(cueRowIndex+1,gridA0000);

		return EventRtnType.NORMAL_SUCCESS;
	}

	public void showView(int cueRowIndex,String gridA0000) throws RadowException{
		this.getPageElement("gridA0000").setValue(gridA0000);
		//System.out.println(this.getPageElement("gridA0000").getValue());
		StringBuffer buffer = new StringBuffer();
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		Map<String, String> colMap = null;
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> list = sess.createSQLQuery("select t.* from hz_a17 t where t.a0000 = '"+gridA0000+"' order by t.start_date,t.end_date").list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				Object[] objs = list.get(i);
				String a1700 = ""+objs[0];
				String a0000 = ""+objs[1];
				String start = ""+objs[13];
				String end = ""+objs[12];
				String content = ""+objs[4];
				String unit = ""+objs[5];
				String president = ""+objs[6];
				String region = ""+objs[8];

				colMap = new HashMap<String, String>();
				colMap.put("a1700", isStrNULL(a1700));
				colMap.put("a0000", isStrNULL(a0000));
				colMap.put("start", isStrNULL(start));
				colMap.put("end", isStrNULL(end));
				colMap.put("content", isStrNULL(content));
				colMap.put("unit", isStrNULL(unit));
				colMap.put("president", isStrNULL(president));
				colMap.put("region", isStrNULL(region));

				map.put("tr"+i, colMap);

				/*buffer.append("<table width='100%' align='center'><tr><td><div id='gridDiv_"+this.property+"' style='width:100%'></div></td><tr/></table>");*/
			}
			JSONObject jsonObject = JSONObject.fromObject(map);

			this.getExecuteSG().addExecuteCode("appendTable('"+list.size()+"','"+jsonObject+"')");
		}
	}

	@PageEvent("delA1700")
	@Transaction
	public int delA1700(String a1700) throws RadowException{
		if(a1700==null||"".equals(a1700)){
			this.setMainMessage("请先选择一条简历！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess = HBUtil.getHBSession();
		//先暂时保存  is_check还是等于0
		//String data = this.getParameter("jsonStr");
		String data = this.getPageElement("jsonOne").getValue();
		// String a0000 = this.getParameter("a0000");
		JSONArray json = JSONArray.fromObject(data); // 首先把字符串转成 JSONArray 对象

		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				// System.out.println(job.get("content")+"=") ; // 得到 每个对象中的属性值

				String a1700now = "" + job.get("a1700");
				String start = "" + job.get("start");
				String end = "" + job.get("end");
				// String content = ""+job.get("content");
				String unit = "" + job.get("unit");
				String president = "" + job.get("president");
				String region = "" + job.get("region");

				// update
				sess.createSQLQuery("update hz_a17 set start_date='" + start + "',end_date='" + end + "',unit='" + unit + "',president='" + president + "'," + "region='" + region + "' where a1700 = '" + a1700now + "'").executeUpdate();
			}
		}

		sess.createSQLQuery("delete from hz_a17 where a1700 = '"+a1700+"'").executeUpdate();

		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = Integer.parseInt(cueRowIndexStr);
		String gridA0000 = this.getPageElement("gridA0000").getValue();
		showView(cueRowIndex,gridA0000);
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("addA1700")
	@Transaction
	public int addA1700(String a0000) throws RadowException{
		String a1700 = this.getPageElement("a1700").getValue();
		if(a1700==null||"".equals(a1700)){
			this.setMainMessage("请先选择一条简历！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先选择人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HBSession sess =HBUtil.getHBSession();
		//先暂时保存  is_check还是等于0
		//String data = this.getParameter("jsonStr");
		String data = this.getPageElement("jsonOne").getValue();
		// String a0000 = this.getParameter("a0000");
		JSONArray json = JSONArray.fromObject(data); // 首先把字符串转成 JSONArray 对象

		if (json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject job = json.getJSONObject(i); // 遍历 jsonarray 数组，把每一个对象转成 json 对象
				// System.out.println(job.get("content")+"=") ; // 得到 每个对象中的属性值

				String a1700now = "" + job.get("a1700");
				String start = "" + job.get("start");
				String end = "" + job.get("end");
				// String content = ""+job.get("content");
				String unit = "" + job.get("unit");
				String president = "" + job.get("president");
				String region = "" + job.get("region");

				// update
				sess.createSQLQuery("update hz_a17 set start_date='" + start + "',end_date='" + end + "',unit='" + unit + "',president='" + president + "'," + "region='" + region + "' where a1700 = '" + a1700now + "'").executeUpdate();
			}
		}

		List<Object[]> list = sess.createSQLQuery("select START_DATE,END_DATE from hz_a17 where a1700 = '"+a1700+"'").list();
		if(list!=null&&list.size()>0){
			Object[] objs = list.get(0);
			String start = ""+objs[0];
			String end = ""+objs[1];

			String a1700new = UUID.randomUUID().toString();
			sess.createSQLQuery("insert into hz_a17(a1700,a0000,START_DATE) values ('"+a1700new.replaceAll("-", "")+"','"+a0000+"','"+start+"')").executeUpdate();

			String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
			Integer cueRowIndex = Integer.parseInt(cueRowIndexStr);
			showView(cueRowIndex,a0000);
		}


		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("saveJL")
	@Transaction
	public int saveJL(){
		HBSession sess = HBUtil.getHBSession();

		String data = this.request.getParameter("text");
		String a0000 = this.request.getParameter("a0000");
		System.out.println(data);

		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a01==null){
			throw new RuntimeException("无法找到该人员！保存失败！");
		}else{
			a01.setA1701(data);
			sess.update(a01);
			sess.flush();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

}
