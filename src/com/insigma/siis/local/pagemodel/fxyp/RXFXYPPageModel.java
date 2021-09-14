package com.insigma.siis.local.pagemodel.fxyp;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.HzJs2Yntp;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.fxyp.base.MNTPUtil;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

import net.sf.json.JSONArray;
/**
 * 人选分析研判
 * @author epsoft
 *
 */
public class RXFXYPPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		
		//设置下拉框
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String userid = SysManagerUtils.getUserId();
		String mntp00 = this.getPageElement("mntp00").getValue();
		String sql = "select  (select count(1) from rxfxyp rx where  rx.zwqc00=f.zwqc00) countf,"+
"f.a0192a fxyp02,f.fxyp07,f.zwqc00 fxyp00,r.tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, r.tp0104,"+
"r.tp0105, r.tp0106, r.tp0107,r.tp0108,r.tp0109,r.tp0110, r.tp0111,"+
"r.tp0112,r.tp0113,r.tp0114,r.tp0115 "
+ ",f.zwqc00,v.a0192a nm_a0192a, v.zwqc00 nm_zwqc00, v.a0000 nm_a0000, v.tp0100 nm_tp0100,f.zwqc01 "
+ " from hz_mntp_zwqc f left join rxfxyp r on f.zwqc00=r.zwqc00 "
+ " left join "
	+ "	(select f.a0192a,f.zwqc00,r.a0000,r.tp0100 from hz_mntp_zwqc f left join rxfxyp r "
	+ "on f.zwqc00 = r.zwqc00 where f.mntp00 = '"+mntp00+"' and f.fxyp07=-1) v on r.a0000=v.a0000 "
+ " where  f.mntp00='"+mntp00+"'"+
		/* " and not exists "
		+ " (select 1 from GWZWREF gw where gw.a0200 = a02.a0200 "
		+ " and gw.mntp00 = '"+mntp00+"') "+*/
"order by f.fxyp07 desc, f.sortnum asc,f.zwqc00, r.sortnum";//f.fxyp05='"+userid+"' and
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode = cqbs.getListBySQL(sql.toString());
			
			
			List<String> a0000List = new ArrayList<String>();
			List<HashMap<String, Object>> listCodeRemove = new ArrayList<HashMap<String,Object>>();
			for(HashMap<String, Object> gwm : listCode){
				String fxyp07 = gwm.get("fxyp07").toString(); //1拟任-1拟免
				String nm_a0000 = gwm.get("nm_a0000")==null?"":gwm.get("nm_a0000").toString(); 
				if(!"-1".equals(fxyp07)){
					a0000List.add(nm_a0000);
				}
			}
			for(HashMap<String, Object> gwm : listCode){
				String fxyp07 = gwm.get("fxyp07").toString(); //1拟任-1拟免
				String nm_a0000 = gwm.get("nm_a0000")==null?"":gwm.get("nm_a0000").toString(); 
				if("-1".equals(fxyp07)&&a0000List.contains(nm_a0000)){
					
					listCodeRemove.add(gwm);
				}
			}
			listCode.removeAll(listCodeRemove);
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+");");
			/*//处理合并
			Map<String,List<HashMap<String, Object>>> gwListMap = new LinkedHashMap<String,List<HashMap<String,Object>>>();
			for(HashMap<String, Object> gwm : listCode){
				String fxyp00 = gwm.get("fxyp00").toString();
				List<HashMap<String, Object>> gwV = gwListMap.get(fxyp00);
				if(gwV == null){
					gwV = new ArrayList<HashMap<String,Object>>();
					gwListMap.put(fxyp00, gwV);
					gwV.add(gwm);
				}else{
					//String fxyp07 = gwm.get("fxyp07").toString(); //1拟任-1拟免
					//String a0000 = gwm.get("a0000").toString();
					//String fxyp02 = gwm.get("fxyp02")==null?"":gwm.get("fxyp02").toString();
					gwV.add(gwm);
				}
			}
			
			//判断是否合并
			List<String> fxyp00List = new ArrayList<String>();
			for(String fxyp00 : gwListMap.keySet()){
				if(!fxyp00List.contains(fxyp00)){
					hasEquals(fxyp00,gwListMap.get(fxyp00),gwListMap,fxyp00List);
				}
			}
			//去除重复
			for(String fxyp00 : fxyp00List){
				gwListMap.remove(fxyp00);
			}
			
			
			List<HashMap<String, Object>> listView=new ArrayList<HashMap<String,Object>>();
			for(String fxyp00 : gwListMap.keySet()){
				listView.addAll(gwListMap.get(fxyp00));
			}
			JSONArray  listViewObject = JSONArray.fromObject(listView);
			this.getExecuteSG().addExecuteCode("doAddPersonView.addPerson("+listViewObject.toString()+");");*/
			
			
			
			
			//设置推送下拉选回显
			String mnur01v = HBUtil.getValueFromTab("to_char(wm_concat(mnur01))", "hz_mntp_userref", "mntp00='"+mntp00+"' and mnur02='"+userid+"'");
			String mnur01t = HBUtil.getValueFromTab("to_char(wm_concat(mnur04))", "hz_mntp_userref", "mntp00='"+mntp00+"' and mnur02='"+userid+"'");
			this.getPageElement("mnur01").setValue(mnur01v);
			this.getPageElement("mnur01"+"_combotree").setValue(mnur01t);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/*private void hasEquals(String fxyp00, List<HashMap<String, Object>> listSS,
			Map<String, List<HashMap<String, Object>>> gwListMapCopy, List<String> fxyp00List) {
		List<String> a0000s = new ArrayList<String>();
		String a0000SS = getA0000List(listSS);
		for(String fxyp00Copy : gwListMapCopy.keySet()){
			if(!fxyp00.equals(fxyp00Copy)){
				List<HashMap<String, Object>> listCopy = gwListMapCopy.get(fxyp00Copy);
				String a0000Copy = getA0000List(listCopy);
				//合并
				if(a0000SS.equals(a0000Copy)){
					fxyp00List.add(fxyp00Copy);
					addFxyp02(listSS,listCopy);
				}
			}
		}
		
	}*/



	/*private void addFxyp02(List<HashMap<String, Object>> listSS, List<HashMap<String, Object>> listCopy) {
		for(HashMap<String, Object> gwm : listSS){
			String fxyp02 = gwm.get("fxyp02")==null?"":gwm.get("fxyp02").toString();
			String fxypCopy = listCopy.get(0).get("fxyp02")==null?"":listCopy.get(0).get("fxyp02").toString();
			if(StringUtils.isEmpty(fxyp02)){
				gwm.put("fxyp02", fxypCopy);
			}else if(!StringUtils.isEmpty(fxypCopy)){
				gwm.put("fxyp02", fxyp02+"，"+fxypCopy);
			}
		}
		
	}*/



	/*private String getA0000List(List<HashMap<String, Object>> listSS) {
		List<String> a0000s = new ArrayList<String>();
		for(HashMap<String, Object> m : listSS){
			if(m.get("a0000")!=null){
				a0000s.add(m.get("a0000").toString()+m.get("fxyp07").toString());
			}
			
		}
		Collections.sort(a0000s);
		return a0000s.toString();
	}*/



	@PageEvent("deletePerson")
	public int deletePerson(String delInfo) throws RadowException, AppException{
		String mntp00 = this.getPageElement("mntp00").getValue();
		if(delInfo==null){
			this.setMainMessage("删除失败！参数为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] delid = delInfo.split("@@");
		//tp0100,delFormIndex,fxyp00,delType
		if(delid.length!=9){
			this.setMainMessage("删除失败！参数错误");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		HBSession sess = HBUtil.getHBSession();
		try {
			if("P".equals(delid[3])){//删除人选  需要重新排序
				HBUtil.executeUpdate("update rxfxyp set sortnum=sortnum-1 where zwqc00=? and sortnum>(select sortnum from  rxfxyp where tp0100=?)",
						new Object[]{delid[4],delid[0]});
				
				//分析研判表删除人员若只有一个人，则删除岗位
				Object count = sess.createSQLQuery("select count(1) from rxfxyp where zwqc00="
						+ "(select zwqc00 from rxfxyp where tp0100=?)").setString(0, delid[0]).uniqueResult();
				int totalcount =0;
				if(count instanceof BigDecimal){
		  			totalcount = ((BigDecimal)count).intValue();
		  		}else if(count instanceof BigInteger){
		  			totalcount = ((BigInteger)count).intValue();
		  		}
//delId+"@@"+delFormIndex+"@@"+fxyp00+"@@"+delType+"@@"+rowData["zwqc00"]+
				//"@@"+(rowData["nm_zwqc00"]||"")+"@@"+(rowData["nm_tp0100"]||"")+"@@"+nmid
				//如果有拟免。  拟免可能前面已经删掉了，不过不影响程序执行，  如果多个人选删除其中一个人选，这该人选有拟免，就需要删除
				/*if(!"".equals(delid[5])){
					//最多只会有一条，根据tp0100 获取该人员所在的拟免岗位a0200上是否有拟任人选  。 若有拟任人选，  判断是否包含自己，若不包含，则增加该人作为新增人选
					List<HashMap<String, Object>> nmrxList = MNTPUtil.getNMRXByTP0100(delid[6],mntp00);
					for(HashMap<String, Object> nmrxMap : nmrxList){
						String a0200 = nmrxMap.get("a0200").toString();
						
						List<HashMap<String, Object>> nrgwAllList = MNTPUtil.getNRRXALLByA0200(a0200,mntp00);
						if(nrgwAllList.size()>0){//有拟任人选
							String zwqc00 = nrgwAllList.get(0).get("zwqc00").toString();
							List<HashMap<String, Object>> nrgwList = MNTPUtil.getNRRXByA0200(a0200,mntp00);
							if(nrgwList.size()==0){//没有自己
								this.getExecuteSG().addExecuteCode("realParent.$('#a0000s').val('"+nmrxMap.get("a0000")+"');"
										+ "realParent.$('#gwa02').val('');"
										+ "realParent.doAddPerson.queryByNameAndIDS_JZHJ('"+zwqc00+"');");
								//新增人选
							}
						}
					}
					
					
					HBUtil.executeUpdate("delete from fxyp where zwqc00=?",new Object[]{delid[5]});
					
					HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00=?",new Object[]{delid[5]});
					
					HBUtil.executeUpdate("delete from rxfxyp where tp0100=?", new Object[]{delid[6]});
				}*/
				
				
				if(totalcount==1){
					HBUtil.executeUpdate("delete from fxyp t where  exists (select fxyp07 from hz_mntp_zwqc h where  t.zwqc00=h.zwqc00 "
							+ " and zwqc00=? and fxyp07=-1) ",new Object[]{delid[4]});
					
					
					//如果删除的是拟任岗位 则删除该岗位对应 的 拟任关联   如果是拟免的 以下语句不会删除数据，  拟免的全称不会关联到GWZWREF
					/*HBUtil.executeUpdate("delete from GWZWREF t where  exists (select fxyp07 from hz_mntp_zwqc h where  t.zwqc00=h.zwqc00 "
							+ " and zwqc00=? and fxyp07=-1) ",new Object[]{delid[4]});*/
					
					HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00=? and fxyp07=-1",new Object[]{delid[4]});
				}
				HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid=? and a01bztype=? ",
						new Object[]{mntp00,delid[0],"2"});
				HBUtil.executeUpdate("delete from rxfxyp where tp0100=?", new Object[]{delid[0]});
			}else if("GW".equals(delid[3])){//删除岗位  
				HBUtil.executeUpdate("delete from rxfxyp where zwqc00=?", new Object[]{delid[4]});
				HBUtil.executeUpdate("delete from fxyp where zwqc00=?", new Object[]{delid[4]});
				HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00=?",new Object[]{delid[4]});
				
				//如果删除的是拟任岗位 则删除该岗位对应 的 拟任关联   如果是拟免的 以下语句不会删除数据，  拟免的全称不会关联到GWZWREF
				HBUtil.executeUpdate("delete from GWZWREF where zwqc00=?",new Object[]{delid[4]});
				//只需删除拟任的
				HBUtil.executeUpdate("delete from  HZ_MNTP_BZ where mntp00=? and a01bzid in (select tp0100 from rxfxyp where zwqc00=?) ",
						new Object[]{mntp00,delid[4]});
				
				/*//如果有拟免。  拟免可能前面已经删掉了，不过不影响程序执行，  如果多个人选删除其中一个人选，这该人选有拟免，就需要删除
				if(!"".equals(delid[7])){
					String zwqc00s = delid[7].replace(",", "','");
					HBUtil.executeUpdate("delete from fxyp where zwqc00 in ('"+zwqc00s+"')");
					HBUtil.executeUpdate("delete from hz_mntp_zwqc where zwqc00 in ('"+zwqc00s+"')");
					HBUtil.executeUpdate("delete from rxfxyp where zwqc00 in ('"+zwqc00s+"')");
				}*/
				return EventRtnType.NORMAL_SUCCESS;
			}else if("ADD".equals(delid[3])||"APPEND".equals(delid[3])){//增加人选  增加岗位
			}else if("ZD".equals(delid[3])){//置顶
				HBUtil.executeUpdate("update rxfxyp set sortnum=sortnum+1 where zwqc00=? and sortnum<(select sortnum from  rxfxyp where tp0100=?)", 
						new Object[]{delid[4],delid[0]});
				HBUtil.executeUpdate("update rxfxyp set sortnum=1 where tp0100=?", new Object[]{delid[0]});
			}else{
				this.setMainMessage("删除失败！参数错误");
				return EventRtnType.NORMAL_SUCCESS;
			}
		} catch (Exception e1) {
			this.setMainMessage("删除失败！");
			e1.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		String userid = SysManagerUtils.getUserId();
		String sql = "select  (select count(1) from rxfxyp rx where  rx.zwqc00=f.zwqc00) countf,"+
				"f.a0192a fxyp02,f.fxyp07,f.zwqc00 fxyp00,r.tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, r.tp0104,"+
				"r.tp0105, r.tp0106, r.tp0107,r.tp0108,r.tp0109,r.tp0110, r.tp0111,"+
				"r.tp0112,r.tp0113,r.tp0114,r.tp0115 "
				+ ",f.zwqc00,v.a0192a nm_a0192a, v.zwqc00 nm_zwqc00, v.a0000 nm_a0000, v.tp0100 nm_tp0100 ,f.zwqc01"
				+ " from hz_mntp_zwqc f left join rxfxyp r on f.zwqc00=r.zwqc00 "
				+ " left join "
					+ "	(select f.a0192a,f.zwqc00,r.a0000,r.tp0100 from hz_mntp_zwqc f left join rxfxyp r "
					+ "on f.zwqc00 = r.zwqc00 where f.mntp00 = '"+mntp00+"' and f.fxyp07=-1) v on r.a0000=v.a0000 "
				+ " where  f.mntp00='"+mntp00+"' and f.zwqc00='"+delid[4]+"'" +
				"order by f.fxyp07 desc, f.sortnum asc,f.a0192a, r.sortnum";
		
		
		
		try {
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql.toString());
			JSONArray  updateunDataStoreObject = JSONArray.fromObject(listCode);
			//System.out.println(updateunDataStoreObject.toString());
			this.getExecuteSG().addExecuteCode("doAddPerson.addPerson("+updateunDataStoreObject.toString()+","+delid[1]+");");
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	//按姓名查询，传递人员IDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String fxyp00) throws Exception{
		//System.out.println(listStr);
		HBSession sess = HBUtil.getHBSession();
		PreparedStatement ps = null;
		Connection conn = null;
		String a0000s = this.getPageElement("a0000s").getValue();
		a0000s = a0000s.replaceAll(",", "','");
		StringBuffer sql = new StringBuffer();
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
						//String jianli = m.get("tp0103")==null?"":m.get("tp0103").toString();
						/*String[] jianliArray = jianli.split("\r\n|\r|\n");
						if(jianliArray.length>0){
							String jl = jianliArray[jianliArray.length-1].trim();
							//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
							Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─|-]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
					        Matcher matcher = pattern.matcher(jl);     
					        if (matcher.find()) {
					        	String line1 = matcher.group(0);  
					        	m.put("tp0103",line1.substring(0,7));
					        }else{
					        	m.put("tp0103",null);
					        }
						}else{
							m.put("tp0103",null);
						}*/
						
						
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
					this.getExecuteSG().addExecuteCode("menus.rowsDelete(null,'ADD');hideWin();");
					
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
		}else{
			this.setMainMessage("无法查询到该人员！");
			return EventRtnType.NORMAL_SUCCESS;
		}
			
			
		return EventRtnType.NORMAL_SUCCESS;
		
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
	
	//保存
	@PageEvent("save")
	public int save(String saveInfo) throws Exception{
		if(saveInfo==null){
			this.setMainMessage("保存失败！参数为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] delid = saveInfo.split("@@");
		//colName,value,fxyp00,tp0100
		if(delid.length!=7){
			this.setMainMessage("保存失败！参数错误");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			if("fxyp02".equals(delid[0])){//更新岗位名称
				//HBUtil.executeUpdate("update fxyp set fxyp02=? where fxyp00=?", new Object[]{delid[1],delid[2]});
				HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=? where zwqc00=?", new Object[]{delid[1],delid[4]});
			}else if("nm_a0192a".equals(delid[0])){//更新岗位名称
				//HBUtil.executeUpdate("update fxyp set fxyp02=? where fxyp00=?", new Object[]{delid[1],delid[2]});
				HBUtil.executeUpdate("update hz_mntp_zwqc set a0192a=? where zwqc00=?", new Object[]{delid[1],delid[5]});
			}else{//更新人选
				HBUtil.executeUpdate("update rxfxyp set "+delid[0]+"=? where tp0100=?", new Object[]{delid[1],delid[3]});
			}
		} catch (Exception e1) {
			this.setMainMessage("保存失败！");
			e1.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("genTPB")
	public int genTPB() throws Exception{
		
		String mntp00 = this.getPageElement("mntp00").getValue();
		try {
			HzJs2Yntp yn = new HzJs2Yntp();
			HBSession sess = HBUtil.getHBSession();
			String yn_id = UUID.randomUUID().toString();
			yn.setYnName(new SimpleDateFormat("yyyyMMdd").format(new Date())+"干部调配建议方案");
			yn.setYnType("TPHJ1");
			yn.setYnId(yn_id);
			yn.setYnUserid(SysManagerUtils.getUserId());
			yn.setYnSysno(BigDecimal.valueOf(Integer.valueOf(HBUtil.getSequence("deploy_classify_dc004"))));
			yn.setYnDate(new Timestamp( new Date().getTime()));
			sess.save(yn);
			Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);//获取年份
	        int month=cal.get(Calendar.MONTH)+1;//获取月份
	        int day=cal.get(Calendar.DATE);//获取日
	        String uname = SysManagerUtils.getUserloginName();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ1','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ2','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ3','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ4','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
			sess.createSQLQuery("insert into hz_js2_yntp_info(info00,info01,yn_id,info0y,info0m,info0d,INFO04 ) values(sys_guid(),'TPHJ5','"+yn_id+"','"+year+"','"+month+"','"+day+"','"+uname+"')").executeUpdate();
		
			String insertSql = "insert into hz_TPHJ1("
					+ "tp0100, a0000, type, tp0101, tp0102, tp0103, "
					+ "tp0104, tp0105, tp0106, tp0107, sortnum, yn_id,"
					+ "tp0116,tp0111,tp0112,tp0115 )";
			String selectSql = "select sys_guid() tp0100, a0000, type, tp0101, tp0102, tp0103, "
					+ "tp0104, tp0105, tp0106, tp0107,rownum sortnum, yn_id,"
					+ "tp0116,tp0111,tp0112,tp0115 from (select  "+
					"r.tp0100, r.a0000, r.type, r.tp0101, r.tp0102, r.tp0103, "
					+ "r.tp0104, r.tp0105, r.tp0106, case r.sortnum when 1 then '任'||f.fxyp02  else '' end tp0107,'"+yn_id+"' yn_id,"
					+ "'TPHJ1' tp0116,case r.sortnum when 1 then f.fxyp02  else '' end tp0111,'' tp0112,'' tp0115 from fxyp f inner join rxfxyp r on f.fxyp00=r.fxyp00 "
+ " where f.fxyp05='"+yn.getYnUserid()+"' and f.mntp00='"+mntp00+"' order by f.fxyp07 desc, f.fxyp04 asc,r.sortnum asc) ";
			sess.createSQLQuery(insertSql+"("+selectSql+")").executeUpdate();
			sess.flush();

			//this.setMainMessage("“"+yn.getYnName()+"”生成成功！");
			this.getExecuteSG().addExecuteCode("openGDview('"+yn_id+"')");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败");
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
	
	@PageEvent("addInfo")
	@Transaction
	public int addInfo(String txt) throws RadowException, AppException{
		
		try {
			String fxyp00 = UUID.randomUUID().toString();
			String userID = SysManagerUtils.getUserId();
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("insert into fxyp(fxyp00,fxyp02,fxyp05) values(?,?,?)",new Object[]{fxyp00,txt,userID});
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("menus.setAppendInfo('APPEND','"+fxyp00+"');openRenXuanTiaoJian('"+fxyp00+"');");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
                    	if(!StringUtils.isEmpty(id)){
                        	selected.add(id);
                        }
                    }
                }
            }
        }
        // 从列表或者小资料中获取选择的人员
        String a0000s = this.getPageElement("a0000srybd").getValue();
        if(!StringUtils.isEmpty(a0000s)){
        	String[] a0000Array = a0000s.split(",");
    		for(int i=0;i<a0000Array.length;i++){
    			selected.add(a0000Array[i]);
    		}
        }
        
        
        if (selected.size() == 0) {
//			this.setMainMessage("请选择人员");
            this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','请先选择记录！',null,150);");
            return EventRtnType.FAILD;
        } else {
            String json = JSON.toJSONString(selected);
            this.getExecuteSG().addExecuteCode("$h.openWin('tpbjWindow','pages.fxyp.GbglTpbj','同屏比较',1500,731,null,'"+this.request.getContextPath()+"',null,{" + "maximizable:false,resizable:false,RMRY:'同屏比较',addPerson:true,data:" + json + "},true)");
            return EventRtnType.NORMAL_SUCCESS;
        }
    }
	
	
	
	@PageEvent("saveTuiSongInfo.onclick")
    public int saveTuiSongInfo() throws RadowException {
		String curuserid = this.getCurUserid();
		String mntp00 = this.getPageElement("mntp00").getValue();
		String mnur01 = this.getPageElement("mnur01").getValue();
		String mnur01_combotree = this.getPageElement("mnur01_combotree").getValue();
		try {
			HBUtil.executeUpdate("delete from hz_mntp_userref where mntp00=? and mnur02=?",new Object[]{mntp00,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into hz_mntp_userref(mnur00,mntp00,mnur01,mnur02,mnur04) "
							+ "values(sys_guid(),?,?,?,?)",new Object[]{mntp00,mnur01s[i],curuserid,mnur01_combotrees[i]});
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('tuiSong').hide();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("gwSort")
	@Transaction
	public int saveSort()throws Exception{
		
		
		String gwSort = this.getPageElement("gwSort").getValue();	
		String[] sortInfoArray = gwSort.split("@");
		
		for(int i=0;i<sortInfoArray.length;i++){
			if(!StringUtils.isEmpty(sortInfoArray[i])){
				HBUtil.executeUpdate("update hz_mntp_zwqc set sortnum=? where zwqc00=?", new Object[]{i,sortInfoArray[i]});
			}
		}
		
		
		
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}