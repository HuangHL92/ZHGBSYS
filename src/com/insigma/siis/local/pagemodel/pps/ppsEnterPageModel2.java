package com.insigma.siis.local.pagemodel.pps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import org.hibernate.Hibernate;
import org.json.JSONException;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.util.JsonUtil;

public class ppsEnterPageModel2 extends PageModel{

	//列表查询列
	String column = "select distinct a01.A0000,a0184,a0101,a0192a,B0127,A01.A0221,A0219 from a01 inner join a02 on a02.a0000=a01.a0000 inner join b01 on a01.a0195=b01.b0111 ";
	
	Map<String, int []> mapAll = new LinkedHashMap<String, int []>();
	
	//存放明确范围的抽数a0000集合(4310)
	
	List<String> listposts=new ArrayList<String>();
	ArrayList listpersons=new ArrayList();
	
	Boolean supplement = true;
	
	//存放明确范围的抽数a0000字符串
	StringBuffer a0000String = new StringBuffer();
	
	String a0000S = "''";
	//确定人唯一
	String a0200S = "''";
	//中央sql
	String zhySql = "  SELECT * FROM (SELECT row_number () over (PARTITION BY a01.a0221 ORDER BY dbms_random.random) rn,a01.a0000,a02.a0200,CASE a01.a0221 WHEN '1A21' THEN '100' WHEN '1A22' THEN '100' WHEN '1A31' THEN '101' WHEN '1A32' THEN '101' "+
		"ELSE '102' END AS a0221,CASE a02.A0219 WHEN '1' THEN '103' ELSE '104' END AS a0219,CASE b01.B0127 WHEN ' ' THEN '109' ELSE '109' END AS B0127 FROM a01 LEFT JOIN a02 ON a01.A0000 = a02.A0000 LEFT JOIN b01 ON a01.a0195 = b01.B0111 "+
"WHERE(a01.a0221) IN (SELECT a01.a0221 FROM a01 LEFT JOIN a02 ON a01.A0000 = a02.A0000 LEFT JOIN b01 ON a01.a0195 = b01.B0111 WHERE a02.a0255 = '1' AND a01.A0221 IN ('1A21','1A22','1A31','1A32','1A41','1A42','1A50','1A60','1A98') "+
		"AND a02.A0219 IN ('1', '2') GROUP BY a01.a0221)) WHERE rn = 1";
	
//	地方sql
	String dfSql = "SELECT * FROM  (SELECT row_number() over(PARTITION BY a01.a0221,b01.B0127 ORDER BY dbms_random.random)rn,a01.a0000,a02.a0200,CASE a01.a0221 WHEN '1A21' THEN  '100'   WHEN '1A22' THEN  '100'  WHEN '1A31' THEN   '101'  WHEN '1A32' THEN    '101'   ELSE  '102' END AS a0221,CASE a02.A0219  WHEN '1' THEN  '103'  ELSE  '104'  END AS a0219,CASE b01.B0127 WHEN '103A' THEN  '105' WHEN '103B' THEN  '105' WHEN '104A' THEN  '105' WHEN '104B' THEN  '105' WHEN '105' THEN  '106' WHEN '106' THEN  '106' WHEN '107' THEN  '107' WHEN '108' THEN  '107' ELSE  '108' END AS B0127 FROM  a01 LEFT JOIN a02 ON a01.A0000 = a02.A0000 LEFT JOIN b01 ON a01.a0195 = b01.B0111 WHERE  (a01.a0221,b01.B0127) IN (SELECT  a01.a0221,b01.B0127   FROM a01 LEFT JOIN a02 ON a01.A0000 = a02.A0000  LEFT JOIN b01 ON a01.a0195 = b01.B0111  WHERE  a02.a0255 = '1'  AND a01.A0221 IN ('1A21','1A22','1A31','1A32','1A41','1A42','1A50','1A60','1A98') AND a02.A0219 IN ('1', '2') AND b01.b0127 IN ('103A','103B','104A','104B','105','106','107','108','109','110','111','999') GROUP BY  a01.a0221,b01.B0127)) WHERE rn = 1";
	
	private CustomQueryBS cbBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//点击pps抽数执行的方法
	@PageEvent("execute.onclick")
	public int ppsStart() throws RadowException, AppException{
		
		long stratTime = System.currentTimeMillis();
		
		HBSession sess=HBUtil.getHBSession();
		this.getExecuteSG().addExecuteCode("");
		
		if(DBUtil.getDBType()==DBType.MYSQL){	
			//中央sql，mysql版本
			zhySql = "SELECT a01.a0000,CASE a01.a0221 WHEN '1A21' THEN '100' WHEN '1A22' THEN '100' WHEN '1A31' THEN '101' WHEN '1A32' THEN '101' ELSE '102' END AS a0221 FROM a01 WHERE a0221 IN ('1A21','1A22','1A31','1A32','1A41','1A42','1A50','1A60','1A98') ORDER BY RAND() LIMIT 20;";
			
			//地方sql，mysql版本
			dfSql = "SELECT * FROM (SELECT a01.A0101 as rn,a01.a0000,CASE a01.a0221 WHEN '1A21' THEN  '100'   WHEN '1A22' THEN  '100'  WHEN '1A31' THEN   '101'  WHEN '1A32' THEN    '101'   ELSE  '104' END AS a0221,CASE a02.A0219  WHEN '1' THEN  '103'  ELSE  '104'  END AS a0219,CASE b01.B0127 WHEN '103A' THEN  '105' WHEN '103B' THEN  '105' WHEN '104A' THEN  '105' WHEN '104B' THEN  '105' WHEN '106' THEN  '106' WHEN '106' THEN  '106' WHEN '107' THEN  '107' WHEN '108' THEN  '107' ELSE  '108' END AS B0127 FROM a01 LEFT JOIN a02 ON a01.A0000 = a02.A0000 LEFT JOIN b01 ON a01.a0195 = b01.B0111  WHERE  a02.a0255 = '1'  AND a01.A0221 IN ('1A21','1A22','1A31','1A32','1A41','1A42','1A50','1A60','1A98') AND a02.A0219 IN ('1', '2') AND b01.b0127 IN ('103A','103B','104A','104B','105','106','107','108','109','110','111','999') ORDER BY RAND()) AS temp GROUP BY A0221,A0219,B0127";

		}
		
		//获得类型
		String type = this.getPageElement("type").getValue();
		int num = Integer.parseInt(this.getPageElement("sort").getValue());
		//抽数具体数量分配方法
//		mapAll = StaticData.initMap(num, type);
		StaticData map= new StaticData();
		mapAll = map.initMap(num, type);
		Boolean isok = true;
		do {
			
			List<Object[]>  list = new ArrayList<Object[]>();
			//String whr = " and a01.a0000 not in (" + listpersons.toString().replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(" ", "").replaceAll(",", "','")+")";
			if(type.equals("2")){		//中央
				list = sess.createSQLQuery(zhySql/*+whr*/).
						//addScalar("RN", Hibernate.STRING).
						addScalar("A0000", Hibernate.STRING).
						addScalar("A0221", Hibernate.STRING).
						//addScalar("A0219", Hibernate.STRING).
						//addScalar("B0127", Hibernate.STRING).
						//addScalar("A0200", Hibernate.STRING).
						list();
			}else{
				list = sess.createSQLQuery(dfSql/*+whr*/).
						addScalar("RN", Hibernate.STRING).
						addScalar("A0000", Hibernate.STRING).
						addScalar("A0221", Hibernate.STRING).
						addScalar("A0219", Hibernate.STRING).
						addScalar("B0127", Hibernate.STRING).
						addScalar("A0200", Hibernate.STRING).
						list();
			}
			
			isok = ratio(list,num);
			long endTime = System.currentTimeMillis();
			
			long time = System.currentTimeMillis();
			
			if((time - stratTime) > 48000 ){
				supplement = false;
			}
			//System.out.println("消耗时间"+(endTime - stratTime));
			
			if((endTime - stratTime) > 60000 ){
				isok = false;
			}
			
		} while (isok);
		
		//数据的调整
		a0000S = listpersons.toString().replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(" ", "").replaceAll(",", "','");
		
		this.getPageElement("a0000S").setValue(a0000S);
		a0200S = listposts.toString().replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(" ", "").replaceAll(",", "','");
		
		this.getPageElement("a0200S").setValue(a0200S);
		
		this.setNextEventName("ppsData.dogridquery");
		
		//System.out.println(a0000S);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("ppsData.dogridquery")
	public int ppsQuery(int start,int limit)throws RadowException{
		
		String a0000S = this.getPageElement("a0000S").getValue();
		String a0200S = this.getPageElement("a0200S").getValue();
		String sql = column + "where a01.a0000 in ("+a0000S+") and a02.a0200 in ("+a0200S+") ORDER BY b0127,a01.a0221,a0219";
		
		this.request.getSession().setAttribute("sql_pps", sql);
		
		this.pageQuery(sql, "SQL", start, limit);
		
		
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	//根据通过sql语句查询出来的人员进行比例分配
	public Boolean ratio(List<Object[]> jh,int num) throws RadowException{
		
		
		
		
		ArrayList<String> list = new ArrayList<String>();
		for (Object[] strings : jh) {		//循环所有的集合元素
			if(mapadd(strings,num)){
				try {
					System.out.println(listpersons.size()+":"+JsonUtil.object2Json(mapAll).toString().replaceAll("\"100\"","厅100").replaceAll("\"101\"","处101").replaceAll("\"102\"","科以下102"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			if(listpersons.size() >= num){		//抽数完毕返回false
				return false;
			}
		}
		
		
		return true;
	}
	
	public boolean mapadd(Object[] strings,int num){
		//判断当前集合是否已包含此人，包含跳过
		if(listpersons.contains(strings[1])){
			return false;
		}
		//机构层次
		//String b0127 = strings[4].toString();
		//职务层次
		//String a0501B = strings[2].toString();
		//职务类型
		String a0219 = strings[1].toString();
		
		
//		//通过机构层次去取得，机构层次抽数信息mapAll
//		int [] intB0127 = mapAll.get(b0127);
//		
//		//职务层次抽数信息
//		int [] intA0501B = mapAll.get(a0501B);
		
		//职务类型抽数信息
		int [] intA0219 = mapAll.get(a0219);
		
//		for (Map.Entry<String, int[]> entry : mapAll.entrySet()) {
//	           System.out.println(entry.getKey() +"------"+ 
//	                   + entry.getValue()[0]);
//	           
//	        }
//		
		Boolean isok = (/*intB0127[0] == intB0127[1] ||  intA0501B[0] == intA0501B[1]*/intA0219[0] == intA0219[1]);
		
		if(!supplement){
			isok = false;
		}
		
		
		/*if(intB0127[0] == intB0127[1]
				||  intA0501B[0] == intA0501B[1] || intA0219[0] == intA0219[1]){*/
		if(isok){
			return false;//抽数完毕返回false
		}else if(listpersons.size() < num){	
			//对当前节点的计数器加一
//			intB0127[1] = intB0127[1] + 1;
//			intA0501B[1] = intA0501B[1] +1;
			intA0219[1] = intA0219[1] +1;
//			mapAll.put(b0127,intB0127);
//			mapAll.put(a0501B,intA0501B);
			mapAll.put(a0219,intA0219);
			listposts.add(strings[5].toString());
			listpersons.add(strings[1]);
			
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 保存列表
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 * @throws UnsupportedEncodingException 
	 * @throws SQLException 
	 * @throws SerialException 
	 */
	@PageEvent("dataSave.onclick")
	@NoRequiredValidate
	@Transaction
	public int dataSave() throws UnsupportedEncodingException, SerialException, AppException, SQLException, RadowException{
		
		//判断列表是否有数据
		List<HashMap<String,Object>> list22 = this.getPageElement("ppsData").getValueList();
		if(list22.size() == 0){
			this.getExecuteSG().addExecuteCode("$h.alert('系统提示：','没有要保存的数据！',null,180);");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String saveName = "pps抽数结果列表";
		String sql = this.request.getSession().getAttribute("sql_pps").toString();
		
		String loginName=SysUtil.getCacheCurrentUser().getLoginname();
		sql = "select a0000 from ("+sql+") a";
		cbBs.saveSWTXList(saveName, "", "", loginName,sql);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("ppsData.rowdbclick")
	@GridDataRange
	public int persongrid1OnRowDbClick() throws RadowException, AppException{  //打开窗口的实例
		String a0000=this.getPageElement("ppsData").getValue("a0000",this.getPageElement("ppsData").getCueRowIndex()).toString();
		A01 ac01=(A01) HBUtil.getHBSession().get(A01.class, a0000);
		if(ac01!=null){
			this.request.getSession().setAttribute("personIdSet", null);
			/*this.getExecuteSG().addExecuteCode("$h.showModalDialog('personInfoOP','"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"','人员信息修改',1009,630,null,"
					+ "{a0000:'"+a0000+"',gridName:'ppsData',maximizable:false,resizable:false,draggable:false},true);");*/
			this.getExecuteSG().addExecuteCode("window.open('"+request.getContextPath()+"/rmb/ZHGBrmb.jsp?a0000="+a0000+"', '_blank', 'height=645, width=1009, top=200, left=200, toolbar=no, menubar=no, scrollbars=no, resizable=yes, location=no, status=no')");
			return EventRtnType.NORMAL_SUCCESS;	
		}else{
			throw new AppException("该人员不在系统中！");
		}
	}
	
}
