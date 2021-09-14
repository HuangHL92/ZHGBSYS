package com.insigma.siis.local.pagemodel.dataverify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class DataExpDefinPageModel extends PageModel{

	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("loadtable");
		this.setNextEventName("expDefineGrid.dogridquery");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("expDefineGrid.dogridquery")
	@NoRequiredValidate
	public int expDefineGridQuery(int start,int limit) throws RadowException{
		String sql = "select * from INTERCHANGE_FORMAT   ";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 双击定义列表
	 * @param INXid
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("loadtable")
	@Synchronous(true)
	public int loadtable(String INXid) throws RadowException{
		
		try{
			CommQuery cq=new CommQuery();
			//checked
			String sql=""
					+ " select "
					+ " a.table_code tblcod,"//
					+ " concat(concat(a.table_code,' '),a.table_name) tblcpt ,"//
					+ " nvl2(t.table_code,'true','false') checked"
					+ " from code_table a left join (select distinct c.table_code from CODE_TABLE_COL c,INTERCHANGE_FORMAT_DETAIL f where c.ctci=f.ctci  and f.inx_id='"+INXid+"') t "
					+ " on a.table_code=t.table_code"
					+ " where 1=1 "
					+ " order by a.table_code asc ";
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			this.getPageElement("tableList2Grid").setValueList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		expDefineGridclick = true;
		this.setNextEventName("tabletofld");
	    return EventRtnType.NORMAL_SUCCESS;
	}
	static boolean expDefineGridclick = false;
	/**
	 * 根据选中信息集，查询信息项
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("tabletofld")
	public int tabletofld() throws RadowException{
		String INXid=this.getPageElement("INXid").getValue();
		if(!expDefineGridclick){
			INXid = "";
		}
		try{
			Grid codeList2Grid1 = (Grid)this.getPageElement("codeList2Grid1");
			List<HashMap<String, Object>> codeList2Grid1list=codeList2Grid1.getValueList();
			
			
			
			
			CommQuery cq=new CommQuery();
			Grid grid = (Grid)this.getPageElement("tableList2Grid");
			List<HashMap<String, Object>> list=grid.getValueList();
			if(list==null||list.size()==0){
				this.setMainMessage("数据异常，检测不到信息集!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String str="";
			for(int i=0;i<list.size();i++){
				if("true".equals(list.get(i).get("checked")+"")){
					str=str+"'"+list.get(i).get("tblcod").toString().trim()+"'"+",";
				}
			}
			
			ArrayList<HashMap<String,Object>> liststay = new ArrayList<HashMap<String,Object>>();
			for(int i=0;i<codeList2Grid1list.size();i++){
				HashMap<String,Object> m = codeList2Grid1list.get(i);
				if(str.indexOf(m.get("table_code").toString())!=-1){
					liststay.add(m);
				}
			}
			
			
			if("".equals(str)){//无选中表时
				this.getPageElement("codeList2Grid").setValueList(new ArrayList<HashMap<String,Object>>());
				this.getPageElement("codeList2Grid1").setValueList(new ArrayList<HashMap<String,Object>>());
				return EventRtnType.NORMAL_SUCCESS;
			}else{
				//这里处理两个部分，预选信息项与未选信息项
				str=str.substring(0, str.length()-1);
				String sqlnew=" select c.ctci, concat(concat(concat(concat(c.table_code,'.'),c.col_code),' '),c.col_name) col_name1,"
						+ " c.col_name, c.code_type, c.col_data_type_should, c.col_code, c.col_data_type,"
						+ " c.table_code, q.FLDNUM "
						+ " from INTERCHANGE_FORMAT_DETAIL q left join code_table_col c  on c.ctci=q.ctci"
						+ " where 1 = 1 and "
						+ " c.table_code in ("+str+") and q.inx_id='"+INXid+"' "
						+ " order by q.fldnum asc";
				List<HashMap<String, Object>> listfldnew=cq.getListBySQL(sqlnew);
				ArrayList<HashMap<String,Object>> removelist = new ArrayList<HashMap<String,Object>>();
				if(!expDefineGridclick){
					
					for(HashMap<String,Object> m : listfldnew){
						String citi = m.get("ctci").toString();
						for(HashMap<String,Object> s : liststay){
							if(citi.equals(s.get("ctci").toString())){
								removelist.add(s);
							}
						}
					}
					liststay.removeAll(removelist);
					listfldnew.addAll(liststay);
				}
				
				
				this.getPageElement("codeList2Grid1").setValueList(listfldnew);
				
				liststay.addAll(removelist);
				
				/* 未选项 */
				String sqlold="select c.ctci, concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"
						+ " col_name,code_type, "
						+ " col_data_type_should,col_code,"
						+ " col_data_type,"//青岛对应列数据类型
						+ " table_code from code_table_col c "
						+ " where 1=1 and "
						+ " table_code  in ("+str+")  "
						+ " and not exists (select 1 from INTERCHANGE_FORMAT_DETAIL f where c.ctci=f.ctci and f.inx_id='"+INXid+"' )"
						+ " order by table_code , col_code asc";
				List<HashMap<String, Object>> listfldold=cq.getListBySQL(sqlold);
				
				if(!expDefineGridclick){
					removelist = new ArrayList<HashMap<String,Object>>();
					for(HashMap<String,Object> m : listfldold){
						String citi = m.get("ctci").toString();
						for(HashMap<String,Object> s : liststay){
							if(citi.equals(s.get("ctci").toString())){
								removelist.add(m);
								break;
							}
						}
					}
					listfldold.removeAll(removelist);
				}
				
				
				
				this.getPageElement("codeList2Grid").setValueList(listfldold);

			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		expDefineGridclick = false;
		return EventRtnType.NORMAL_SUCCESS;
	}

	//实现选择预选项
	@PageEvent("checkClickCode")
	public int checkClickCode(String num) throws RadowException{
		checkCode(num,false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("checkClickCodeAll")
	public int checkClickCodeAll() throws RadowException{
		checkCode("",true);
		return 1;
	}
	public int checkCode(String num,boolean flag ) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//未选项
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//预选项
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(flag==false){
				String n=num;
				if(null==n||"0".equals(n)){n="0";}//解决：grid 第一行返回null
				int rownum = Integer.parseInt(n);
				HashMap<String,Object> hashMap1 = listCode.get(rownum);
				hashMap1.put("weizhi", n);
				listCode.remove(Integer.parseInt(n));//未选项 -- 移除
				listCode1.add(hashMap1);//预选项 -- 增加
		}else{
			int size=listCode.size();
			for(int i=0;i<size;i++){
				HashMap<String,Object> hashMap1 = listCode.get(i);
				hashMap1.put("weizhi", i);
				listCode1.add(hashMap1);//预选项 -- 增加
			}
			for(int i=(size-1);i>=0;i--){
				listCode.remove(i);//未选项 -- 移除
			}
		}
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return 1;
	}
	
	//实现移除预选项
	@PageEvent("delThisOne")
	public int delThisOne(String num) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//未选项
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//预选项
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0";}//解决：grid 第一行返回null
		int rownum = Integer.parseInt(num);
		HashMap<String,Object> hashMap1 = listCode1.get(rownum);
		listCode1.remove(Integer.parseInt(num));//预选项  -- 移除
		listCode.add(hashMap1);//未选项-- 增加至最后
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	@PageEvent("saveInfo")
	public int saveInfo(String txt) throws RadowException{
		String INXid=this.getPageElement("INXid").getValue();
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = HBUtil.getHBSession().connection();
			conn.setAutoCommit(false);
			if(txt!=null&&!"".equals(txt)){//新增
				String uuid = UUID.randomUUID().toString();
				String sql = "insert into INTERCHANGE_FORMAT(inx_id,inx_name) values(?,?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, txt);
				pst.executeUpdate();
				INXid = uuid;
				this.setNextEventName("expDefineGrid.dogridquery");
				//conn.commit();
			}else if(INXid!=null&&!"".equals(INXid)){
				String sql = "delete from INTERCHANGE_FORMAT_DETAIL where inx_id=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, INXid);
				pst.executeUpdate();
				//conn.commit();
			}
			pst.close();
			Grid codeList2Grid1 = (Grid)this.getPageElement("codeList2Grid1");
			List<HashMap<String, Object>> codeList2Grid1list=codeList2Grid1.getValueList();
			if(codeList2Grid1list.size()>0){
				String sql = "insert into INTERCHANGE_FORMAT_DETAIL(inx_id,ctci,fldnum) values(?,?,?)";
				pst = conn.prepareStatement(sql);
				for(int i=0;i<codeList2Grid1list.size();i++){
					HashMap<String,Object> m = codeList2Grid1list.get(i);
					pst.setString(1, INXid);
					pst.setString(2, m.get("ctci").toString());
					pst.setInt(3, i);
					pst.addBatch();
				}
				pst.executeBatch();
			}
			
			conn.commit();
			this.getExecuteSG().addExecuteCode("document.getElementById('INXid').value='"+INXid+"'");
			this.setMainMessage("保存成功！");
			
		} catch (SQLException e) {
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
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("delInfo")
	public int delInfo(String inx_id) throws RadowException{
		String INXid = inx_id;
		if("f2e5de60-4eae-4e7e-990e-e46aef4d6fce".equals(INXid)){
			this.setMainMessage("保留方案无法删除！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			conn = HBUtil.getHBSession().connection();
			conn.setAutoCommit(false);
			if(inx_id!=null&&!"".equals(inx_id)){//新增
				String sql = "delete from INTERCHANGE_FORMAT_DETAIL where inx_id=?";
				pst = conn.prepareStatement(sql);
				pst.setString(1, INXid);
				pst.executeUpdate();
				pst.close();
				pst = conn.prepareStatement("delete from INTERCHANGE_FORMAT where inx_id=?");
				pst.setString(1, INXid);
				pst.executeUpdate();
				pst.close();
			}
			
			
			
			conn.commit();
			this.setMainMessage("删除成功！");
			this.getExecuteSG().addExecuteCode("document.getElementById('INXid').value=''");
			this.setNextEventName("expDefineGrid.dogridquery");
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
			}
			e.printStackTrace();
			this.setMainMessage("删除失败！");
		}finally {
			try {
				if(conn!=null)
					conn.close();
				if(pst!=null)
					pst.close();
			} catch (SQLException e1) {
			}
			
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
