package com.insigma.siis.local.pagemodel.fxyp;


import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.xbrm2.zsrm.Zsrm;

import net.sf.json.JSONArray;

public class BZYPChoosePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.controlButton();
		return 0;
	}
	
	@PageEvent("queryFromData")
	public int queryFromData() throws RadowException{
		
		String sql2=" ";
		String uderID = SysManagerUtils.getUserId();
		HBSession sess=HBUtil.getHBSession();
		String selectByInputYnIdHidden = this.getPageElement("selectByInputYnIdHidden").getValue();

			//获得姓名或者身份证
			String name = this.getPageElement("queryName").getValue();
			//获得查询类型，姓名或身c份证
			String tpye = this.getPageElement("tpye").getValue();
			
			String col1 = "a0101";
			String col2 = "a0102";
			
			boolean multiUnit = false;//是否包含下级单位
			
			//邹志林 2018-08-22
			String selectUnitId = this.getPageElement("selectUnitId").getValue();
			JSONObject objUnitId = new JSONObject(selectUnitId);
			StringBuffer sqlBuffer = new StringBuffer();

			multiUnit = objUnitId.keySet().size()>1;		
			for (Object oKey:objUnitId.keySet()) {
				String unitId = (String)oKey;
				String array[] = objUnitId.get(unitId).toString().split(":");
				if ("true".equals(array[1]) && "true".equals(array[2])) {
					sqlBuffer.append(" OR a0201b like '"+unitId+"%' ");
					multiUnit = true;
				}else if (!"true".equals(array[1]) && "true".equals(array[2])) {
					sqlBuffer.append(" OR a02.a0201b = '"+unitId+"' ");
				}
				 
			} 
			sqlBuffer.delete(0, 3);

		if (selectByInputYnIdHidden != null && !"".equals(selectByInputYnIdHidden)) {
			//按照导入批次选择
			sql2="select  a01.a0000, a0101, a0104,a0192a,a0163 from "+Zsrm.getZjkTableName("a01")+" where a0000 in (select a0000 from "+Zsrm.getZjkTableName("TPHJPERSON")+" where TPZZ02='"+selectByInputYnIdHidden+"' )";
			this.getPageElement("sql").setValue(sql2);
		}else {
			//===========================以下按照单位人员选择


			@SuppressWarnings("unused")
			String appointment = this.getPageElement("appointment").getValue();

//			String unionsql=" ";
			//if ("1".equals(appointment)) {
				//正式任命
				//sql2="select  distinct a01.torder,a0225,a01.a0000, a0101, a0104,a0192a,a0163 from a01 a01  where a01.A0000=a02.A0000 and A0163='1' and status <> '4' and remoteserver = remoteserver() and a01.a0000  in (select a02.a0000 from a02 a02 where  a02.a0281='true' and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"'))  ";

//				unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163 from a01 a01  where remoteserver = remoteserver() and "
//						+ " not exists (select 1 from a02 a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
//							+ " and a01.status!='4' ";
			//} else {
				//单招单位人员选择
				String mult=multiUnit?"":"a0225,";  //选择多个单位去除本级排序

				sql2="select distinct "+mult+" a01.a0000,a01.a0101,a01.a0104,a01.a0192a,a01.a0163 from A01 a01,A02 a02  where a01.A0000=a02.A0000  and status <> '4' and a01.a0000  in (select a02.a0000 from a02 a02 where a02.a0281='true' "  +
						"and  a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"'))  ";

//				unionsql="select  a01.a0000, a0101, a0104,a0192a,a0163 from a01 a01  where "
//						+ " not exists (select 1 from a02 a02   where a02.a0000=a01.a0000 and a0281='true' and a0201b in(select b0111 from b01 where b0111!='-1') )   "
//							+ " and a01.status!='4' ";
			//}
			//这里增加单位权限的控制
			
			if(tpye != null && tpye.equals("2")){
				col1 = "a0184";
				col2 = "a0184";
			}
			
			if(name!=null && !name.trim().equals("")){
				name = StringEscapeUtils.escapeSql(name.trim());
				name = name.replaceAll("\\s+"," ");
				name = name.replaceAll(" ", ",");
				name = name.replace(".", ",");
				name = name.replace("&", ",");
				name = name.replace("#", ",");
				name = name.replaceAll("[\\t\\n\\r]", ",");
				String[] names = name.trim().split(",|，");
				//包含逗号则进行精确查询
				if(names.length>1){
					
					/*	StringBuffer sb = new StringBuffer(" a01.a0101 in(");
						StringBuffer pingyin = new StringBuffer(" a01.a0102 in(");*/
						
						StringBuffer sb = new StringBuffer(" a01."+col1+" in(");
						StringBuffer pingyin = new StringBuffer(" a01."+col2+" in(");
						for(int i=0;i<names.length;i++){
							name = names[i].replaceAll("\\s", "");
							sb.append("'"+name+"',");
							pingyin.append("'"+name.toUpperCase()+"',");
						}
						sb.deleteCharAt(sb.length()-1);
						sb.append(")");
						pingyin.deleteCharAt(pingyin.length()-1);
						pingyin.append(")");
						sql2 = sql2 + " and (" + sb.toString() + " or " + pingyin.toString()+")";
//						unionsql = unionsql + " and (" + sb.toString() + " or " + pingyin.toString()+")";
					}else{ 
					//不包含逗号则进行模糊查询
						name = name.replaceAll("\\s", "");
						/*sql2 = sql2 +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";
						unionsql = unionsql +" and (a01.a0101 like '%"+name+"%' or a01.a0102 like '"+name.toUpperCase()+"%')";*/
						 
						sql2 = sql2 +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
//						unionsql = unionsql +" and (a01."+col1+" like '%"+name+"%' or a01."+col2+" like '"+name.toUpperCase()+"%')";
					}
				if (sqlBuffer.length()>0) {
					sql2+=	"and a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"' and "+ sqlBuffer.toString() +  " )";
				}
			} else {
				if (sqlBuffer.length()>0) {
					sql2+=" and a01.a0000 in (select a0000 from a02 where  a0281='true' and " + sqlBuffer.toString() +  ") ";
					
					String   personq = "1";
					if("1".equals(personq)){
						sql2+="and a0163 like '1%'and A0255='1'"; 
					}else if("2".equals(personq)){
						sql2+="and a0163 like '2%'and A0255='0'"; 
					}else if(personq==null||"".equals(personq)){
						sql2+="";
					}else if("3".equals(personq)){
						
					}else {
						sql2+="and a0163='"+personq+"'";
					}

					
					sql2+=	"and a02.A0201B in (select cu.b0111 from competence_userdept cu where cu.userid = '"+uderID+"' and "+ sqlBuffer.toString() +  " )";
				}
			}
			
			String tElementSQL = "";
			String str="1=1";
			if (sqlBuffer.length()>0) {
				str=sqlBuffer+"";
			}
			if (multiUnit) {

				tElementSQL = sql2 + " order by ((select rpad(b0269, 25, '.') || lpad(a0225, 25, '0')"
						+ " from (select a02.a0000, b0269,a0225,"
						+ "	row_number() over(partition by a02.a0000 order by nvl(a02.a0279, 0) desc, b0269) rn"
						+ "	from a02, b01 where a02.a0201b = b01.b0111  and a0281 = 'true' and ("+str+")) t"
						+ "	where rn = 1 and t.a0000 = a01.a0000))  ";
			} else {
				tElementSQL = sql2 + " order by (select lpad(max(a0225), 25, '0') from a02"
						+ " where a01.a0000 = a02.a0000 and a02.a0281 = 'true' and ("+str+")) ";
			}
			System.out.println(tElementSQL);
			this.getPageElement("sql").setValue(tElementSQL); //this.getPageElement("sql").setValue(sql2+" union all " + unionsql);
		}
		
		int DISPLAY_PERSON = 100;  //显示人员数量
		
		String count_sql = "select count(*) from ("+sql2+") a where rownum<"+(DISPLAY_PERSON+1)+" ";
		System.out.println(count_sql);
		
		Object number = sess.createSQLQuery(count_sql).list().size();
		
		int count = 0;
		if(number != null && !"".equals(number.toString())){
			count = Integer.parseInt(number.toString());
		}
		//如果查询结果少于DISPLAY_PERSON条则在待选列表中展示(若只有一条则自动右移到输出列表  jsp:190)
		if(count < DISPLAY_PERSON){ 
			this.getPageElement("mark").setValue("1");
			this.setNextEventName("gridcq.dogridquery");
		
		//如果多于DISPLAY_PERSON条则不予查询展示并提示	
		}else{
			this.setMainMessage("查询结果过多，请缩小查询范围并重新查询");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("gridcq.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String sql = this.getPageElement("sql").getValue();
			if(limit<1000) {
				limit=1000;
			}
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("查询失败！");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	//添加
	@PageEvent("rigthBtn.onclick")
	public int rigthBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				addlist.add(hm);
			}
		}
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		String gridcqdata = JSONArray.fromObject(list).toString();
		
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
 		pe.setValue(gridcqdata);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部添加
	@PageEvent("rigthAllBtn.onclick")
	public int rigthAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("gridcq");
		List<HashMap<String, Object>> list = pe.getValueList();//查询人员列表
		List<HashMap<String, Object>> listSelect=this.getPageElement("selectName").getValueList();//选择人员列表
		for (HashMap<String, Object> hm : list) {
			addlist.add(hm);
		}
		//去掉查询列表中被添加的人员
		list.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(list).toString());
		List<HashMap<String, Object>> addListFinal=new LinkedList<HashMap<String,Object>>();
		one:for(HashMap<String, Object> hm:addlist){
			for(HashMap<String, Object> sel:listSelect){
				if(hm.get("a0000").equals(sel.get("a0000"))){
					continue one;
				}
			}
			addListFinal.add(hm);
		}
 		//将数据添加到登记人员列表
		listSelect.addAll(addListFinal);
		String data= JSONArray.fromObject(listSelect).toString();
 		this.getPageElement("selectName").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//移除
	@PageEvent("liftBtn.onclick")
	public int liftBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			if(hm.get("personcheck2")!=null&&!"".equals(hm.get("personcheck2"))&&(Boolean) hm.get("personcheck2")){
				addlist.add(hm);
			}
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//全部移除
	@PageEvent("liftAllBtn.onclick")
	public int liftAllBtn() throws RadowException, AppException{
		List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		List<HashMap<String, Object>> list=this.getPageElement("gridcq").getValueList();//查询人员列表
		for (HashMap<String, Object> hm : listSelect) {
			addlist.add(hm);
		}
		//去掉查询列表去除的数据
		listSelect.removeAll(addlist);
		pe.setValue(JSONArray.fromObject(listSelect).toString());
 		//将数据添加到登记人员列表
		list.addAll(addlist);
		String data= JSONArray.fromObject(list).toString();
 		this.getPageElement("gridcq").setValue(data);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//关闭
	@PageEvent("clearSelect")
	public int clearSelect() throws RadowException, AppException{
		/*List<HashMap<String, Object>> addlist=new LinkedList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("selectName");//选择人员列表
		//去掉查询列表去除的数据
		pe.setValue(JSONArray.fromObject(addlist).toString());*/
		//this.closeCueWindow("findById");
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('findById').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//确认保存
	@PageEvent("saveSelect")
	public int saveSelect(String a) throws RadowException, AppException{
		String checkedgroupid=this.getPageElement("checkedgroupid").getValue();
		String g=checkedgroupid.substring(0,11);
		StringBuffer bjylxtr=new StringBuffer();
		StringBuffer bjylxtra=new StringBuffer();
		//StringBuffer bdgb=new StringBuffer();
		//获得选择的人员ID
		//List<String> addlist=new LinkedList<String>();
		PageElement pe = this.getPageElement(change("selectName"));//选择人员列表
		List<HashMap<String, Object>> listSelect = pe.getValueList();
		
		if(listSelect.size()<1){
			this.setMainMessage("请选择人员");
			return EventRtnType.NORMAL_SUCCESS;
		}else {
				for(int i=0;i<listSelect.size();i++) {
					bjylxtr.append(String.valueOf(listSelect.get(i).get("a0101"))+",");
					bjylxtra.append(String.valueOf(listSelect.get(i).get("a0000"))+",");
					}
					if(bjylxtr.length()>0){
						bjylxtr.deleteCharAt(bjylxtr.length()-1);
						bjylxtra.deleteCharAt(bjylxtra.length()-1);
					}
		}
		String sql="";
		HBSession sess = HBUtil.getHBSession();
		
		try {
			//int i=Integer.valueOf(getMax_sort(bz00,sess))+1;
			Statement stmt = sess.connection().createStatement();
			if(g.equals("001.001.002")) {
				sql="update BZFX set bjylxtr='',bjylxtra=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update BZFX set bjylxtr='"+bjylxtr+"', bjylxtra='"+bjylxtra+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}else if(g.equals("001.001.003")) {
				sql="update GQBZFX set bjylxtr='',bjylxtra=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update GQBZFX set bjylxtr='"+bjylxtr+"', bjylxtra='"+bjylxtra+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}else if(g.equals("001.001.004")) {
				sql="update QXSBZFX set bjylxtr='',bjylxtra=''  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				sql="update QXSBZFX set bjylxtr='"+bjylxtr+"', bjylxtra='"+bjylxtra+"'  where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
				stmt.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getExecuteSG().addExecuteCode("window.close();");
		//this.getExecuteSG().addExecuteCode("window.realParent.doAddPerson.queryByNameAndIDS('"+addlist+"','"+a+"');");
		this.getExecuteSG().addExecuteCode("parent.queryPerson();");
		this.getPageElement(change("selectName")).setValue("[]");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	private String change(String name) throws RadowException {
		if ("1".equals(this.getPageElement("selectType").getValue())) {
			name=name+"2";
		}
		return name;
	}
	//清除输出列表
	@PageEvent("clearRst")
	public int clearRst() throws RadowException, AppException{
		this.getPageElement("gridcq").setValue("[]");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public static void main(String[] args) {
		String s = "陈.接.                    空";
		System.out.println(s.replace(" ", ","));
	}
	
	/**
	 * 获取最大的排序号
	 * @return
	 */
	public String getMax_sort(String titleid,HBSession session){
		String sort = session.createSQLQuery("select nvl(max(sh001),0) from (select sh001 from hz_sh_a01 where titleid='"+titleid+"' union "
				+ " select sh001 from personcite where titleid_new='"+titleid+"') ").uniqueResult().toString();
		return sort;
	}

}
