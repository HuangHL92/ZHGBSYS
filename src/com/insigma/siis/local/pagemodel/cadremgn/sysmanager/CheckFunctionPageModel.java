package com.insigma.siis.local.pagemodel.cadremgn.sysmanager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.PageEvents;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
/**
 * 校核函数定义
 * @author lianghf
 *
 */
public class CheckFunctionPageModel extends PageModel {  
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	@Override
	public int doInit() throws RadowException {
		try {
			//加载函数列表
			List<HashMap<String, Object>> list = getFunctionList();
			this.getPageElement("functionList").setValueList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 获得函数列表
	 * @return
	 */
	private List<HashMap<String, Object>> getFunctionList(){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>() ;
		try {
			String sql=" select * from check_function t order by t.checkname asc ";
			CommQuery cqbs=new CommQuery();
			list= cqbs.getListBySQL(sql);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据点击的函数获得相应的值
	 */
	@PageEvent("showFunction")
	public int showFunction(){	
		try {
			String id=this.getPageElement("id").getValue();
			String sql=" select num,paradescribe,type, is_select from check_function_parameter t where check_function_id='"+id+"' order by num asc";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list= cqbs.getListBySQL(sql);
			this.getPageElement("grid").setValueList(list);
			//参数输入框默认显示第一个参数
			HashMap<String, Object> map=list.get(0);
			this.getPageElement("num").setValue(map.get("num").toString());
			this.getPageElement("paradescribe").setValue(map.get("paradescribe").toString());
			this.getPageElement("type").setValue(map.get("type").toString());
			this.getPageElement("is_select").setValue(map.get("is_select").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除函数
	 */
	@PageEvents("deletebtn.onclick")
	@Transaction
	@Synchronous(true)
	public int deleteClick() throws RadowException, AppException{
		try {
 			HBSession sesstion=HBUtil.getHBSession();
			String id=this.getPageElement("id").getValue();
			String sql=" delete from check_function where id='"+id+"'";
			sesstion.createSQLQuery(sql).executeUpdate();
			String sql2=" delete from check_function_parameter  where check_function_id='"+id+"'";
			sesstion.createSQLQuery(sql2).executeUpdate();
			//刷新函数
			List<HashMap<String, Object>> list = getFunctionList();
			this.getPageElement("functionList").setValueList(list);
			this.getExecuteSG().addExecuteCode("alert('删除成功!');");
			cleanCond();//清空条件
//			this.setMainMessage("删除成功！");
		} catch (Exception e) {
			this.setMainMessage("操作失败！");
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * 新增函数
	 */
	@PageEvents("addbtn.onclick")
	@Transaction
	@Synchronous(true)
	public void addClick(){
		try {
			cleanCond();//清空条件
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 新增条件到列表
	 */
	@PageEvents("saveconhandler")
	@GridDataRange
	@AutoNoMask
	public int saveconhandler() throws RadowException, AppException{  
		PageElement pe = this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();//参数列表
		String num= this.getPageElement("num").getValue();
		String paradescribe= this.getPageElement("paradescribe").getValue();
		String type= this.getPageElement("type").getValue();
		String is_select= this.getPageElement("is_select").getValue();
		boolean flag=false;//是否有重复的参数序号
		for(HashMap<String, Object>  map :list){
			if(num.equals(map.get("num"))){
				//有重复参数序号时替换value
				map.put("num",num);
				map.put("paradescribe",(Object)paradescribe);
				if(type.equals("char")){
					map.put("type",(Object)"字节");
				}else if(type.equals("varchar2")){
					map.put("type",(Object)"字符串");
				}else if(type.equals("binary_double")){
					map.put("type",(Object)"双精度数");
				}else if(type.equals("number")){
					map.put("type",(Object)"整数");
				}else if(type.equals("date")){
					map.put("type",(Object)"日期/时间");
				}else{
					map.put("type",(Object)type);
				}
				map.put("is_select",is_select);
				flag=true;
//				this.setMainMessage("修改参数成功！");
			}
		}
	
		if(!flag){
			//无重复参数序号时新增参数列表
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("num",num);
			map.put("paradescribe",(Object)paradescribe);
			if(type.equals("char")){
				map.put("type",(Object)"字节");
			}else if(type.equals("varchar2")){
				map.put("type",(Object)"字符串");
			}else if(type.equals("binary_double")){
				map.put("type",(Object)"双精度数");
			}else if(type.equals("number")){
				map.put("type",(Object)"整数");
			}else if(type.equals("date")){
				map.put("type",(Object)"日期/时间");
			}else{
				map.put("type",(Object)type);
			}
			map.put("is_select",is_select);
			list.add(map);
//			this.setMainMessage("保存参数成功！");
		}
		pe.setValueList(list);
		//清空参数输入框
		this.getPageElement("num").setValue(null);
		this.getPageElement("paradescribe").setValue(null);
		this.getPageElement("type").setValue(null);
		this.getPageElement("is_select").setValue("0");
		this.getPageElement("cueRowIndex").setValue("");//新增数据后，选中行清空
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 保存函数
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("savebtnhand")
	@Transaction
	@Synchronous(true)
	public int savePerson()throws RadowException, AppException{
		try{
			HBSession session=HBUtil.getHBSession();
			Connection conn = session.connection();
			PageElement pe = this.getPageElement("grid");	
			List<HashMap<String, Object>> list = pe.getValueList();//参数列表	
//			//验证参数列表是否为空
//			if(list.size()==0||list==null){
//				this.getExecuteSG().addExecuteCode("alert('请先输入参数！');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}	
			String pk_id=this.getPageElement("id").getValue().trim();//主键
			String checkname=this.getPageElement("checkname").getValue();
			//替换中文括号和逗号为英文
			if(checkname.contains("（") || checkname.contains("）")||checkname.contains("，")){
				checkname = checkname.replace("（", "(");
				checkname = checkname.replace("）", ")");
				checkname = checkname.replace("，", ",");
			}
			String dateString = sf.format(new Date());
			if(pk_id.isEmpty()){
				//新增
				
				String describe=this.getPageElement("describe").getValue();
				//保存函数
				String sql = "insert into CHECK_FUNCTION (ID,CHECKNAME,DESCRIBE,CREATE_TIME) values(?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				String id=UUID.randomUUID().toString();
				ps.setString(1, id);
				ps.setString(2, checkname);
				ps.setString(3, describe);
				ps.setString(4, dateString);
				ps.execute();
			
				//批量保存参数
				String parasql = "insert into CHECK_FUNCTION_PARAMETER (ID,CHECK_FUNCTION_ID,NUM,PARADESCRIBE,TYPE,IS_SELECT,CREATE_TIME) values(?,?,?,?,?,?,?)";
				PreparedStatement paraps = conn.prepareStatement(parasql);
				for(int i=0;i<list.size();i++){
					HashMap<String, Object> map=list.get(i);
					String pkid=UUID.randomUUID().toString();
					paraps.setString(1, pkid);
					paraps.setString(2, id);
					paraps.setInt(3, Integer.parseInt(map.get("num").toString()));
					paraps.setString(4, map.get("paradescribe").toString());
					String type=map.get("type").toString();
					if(type.equals("char")){
						type="字节";
					}else if(type.equals("varchar2")){
						type="字符串";
					}else if(type.equals("binary_double")){
						type="双精度数";
					}else if(type.equals("number")){
						type="整数";
					}else if(type.equals("date")){
						type="日期/时间";
					}				
					paraps.setString(5, type);
					paraps.setInt(6, Integer.parseInt(map.get("is_select").toString()));
					paraps.setString(7, dateString);
					paraps.addBatch();
				}
				paraps.executeBatch();
				ps.close();
				paraps.close();
			}else{
				//修改
				String describe=this.getPageElement("describe").getValue();
				//修改函数
				String updateSql="  update check_function  set checkname='"+checkname+"',describe='"+describe+"' where id='"+pk_id+"'";
				System.out.println(updateSql);
				session.createSQLQuery(updateSql).executeUpdate();
				
				//删除原有的参数
				String deleteSql=" delete from check_function_parameter where check_function_id='"+pk_id+"'";
				HBUtil.getHBSession().createSQLQuery(deleteSql).executeUpdate();			
			
				//批量保存参数
				String parasql = "insert into CHECK_FUNCTION_PARAMETER (ID,CHECK_FUNCTION_ID,NUM,PARADESCRIBE,TYPE,IS_SELECT,CREATE_TIME) values(?,?,?,?,?,?,?)";
				PreparedStatement paraps = conn.prepareStatement(parasql);
				for(int i=0;i<list.size();i++){
					HashMap<String, Object> map=list.get(i);
					String pkid=UUID.randomUUID().toString();
					paraps.setString(1, pkid);
					paraps.setString(2, pk_id);
					paraps.setInt(3, Integer.parseInt(map.get("num").toString()));
					paraps.setString(4, map.get("paradescribe").toString());
					String type=map.get("type").toString();
					if(type.equals("char")){
						type="字节";
					}else if(type.equals("varchar2")){
						type="字符串";
					}else if(type.equals("binary_double")){
						type="双精度数";
					}else if(type.equals("number")){
						type="整数";
					}else if(type.equals("date")){
						type="日期/时间";
					}				
					paraps.setString(5, type);
					paraps.setInt(6, Integer.parseInt(map.get("is_select").toString()));
					paraps.setString(7, dateString);
					paraps.addBatch();
				}
				paraps.executeBatch();
				paraps.close();
			}
			//重新获取函数名称列表
			List<HashMap<String, Object>> newList = getFunctionList();
			this.getPageElement("functionList").setValueList(newList);	
			if(pk_id.isEmpty()){
				this.getExecuteSG().addExecuteCode("alert('保存成功!');");
			}else{
				this.getExecuteSG().addExecuteCode("alert('修改成功!');");
			}
			cleanCond();
		}catch(Exception e){
			e.printStackTrace();
			this.setMainMessage("操作失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * 清空所有值
	 * @throws RadowException 
	 */
	private void cleanCond()  {
		try {
			this.getPageElement("id").setValue(null);
			this.getPageElement("checkname").setValue(null);
			this.getPageElement("describe").setValue(null);
			this.getPageElement("num").setValue(null);
			this.getPageElement("paradescribe").setValue(null);
			this.getPageElement("type").setValue(null);
			this.getPageElement("is_select").setValue("0");
			this.getPageElement("grid").setValueList(null);
		} catch (RadowException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置下拉框和单选框的值
	 * @throws RadowException 
	 */
	@PageEvent("setPara")
	public void setParameter() throws RadowException{
		this.getPageElement("type").setValue(this.getPageElement("type").getValue());
		this.getPageElement("is_select").setValue(this.getPageElement("is_select_para").getValue().toString());
	}
}
