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
 * У�˺�������
 * @author lianghf
 *
 */
public class CheckFunctionPageModel extends PageModel {  
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
	@Override
	public int doInit() throws RadowException {
		try {
			//���غ����б�
			List<HashMap<String, Object>> list = getFunctionList();
			this.getPageElement("functionList").setValueList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ú����б�
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
	 * ���ݵ���ĺ��������Ӧ��ֵ
	 */
	@PageEvent("showFunction")
	public int showFunction(){	
		try {
			String id=this.getPageElement("id").getValue();
			String sql=" select num,paradescribe,type, is_select from check_function_parameter t where check_function_id='"+id+"' order by num asc";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String, Object>> list= cqbs.getListBySQL(sql);
			this.getPageElement("grid").setValueList(list);
			//���������Ĭ����ʾ��һ������
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
	 * ɾ������
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
			//ˢ�º���
			List<HashMap<String, Object>> list = getFunctionList();
			this.getPageElement("functionList").setValueList(list);
			this.getExecuteSG().addExecuteCode("alert('ɾ���ɹ�!');");
			cleanCond();//�������
//			this.setMainMessage("ɾ���ɹ���");
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * ��������
	 */
	@PageEvents("addbtn.onclick")
	@Transaction
	@Synchronous(true)
	public void addClick(){
		try {
			cleanCond();//�������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * �����������б�
	 */
	@PageEvents("saveconhandler")
	@GridDataRange
	@AutoNoMask
	public int saveconhandler() throws RadowException, AppException{  
		PageElement pe = this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();//�����б�
		String num= this.getPageElement("num").getValue();
		String paradescribe= this.getPageElement("paradescribe").getValue();
		String type= this.getPageElement("type").getValue();
		String is_select= this.getPageElement("is_select").getValue();
		boolean flag=false;//�Ƿ����ظ��Ĳ������
		for(HashMap<String, Object>  map :list){
			if(num.equals(map.get("num"))){
				//���ظ��������ʱ�滻value
				map.put("num",num);
				map.put("paradescribe",(Object)paradescribe);
				if(type.equals("char")){
					map.put("type",(Object)"�ֽ�");
				}else if(type.equals("varchar2")){
					map.put("type",(Object)"�ַ���");
				}else if(type.equals("binary_double")){
					map.put("type",(Object)"˫������");
				}else if(type.equals("number")){
					map.put("type",(Object)"����");
				}else if(type.equals("date")){
					map.put("type",(Object)"����/ʱ��");
				}else{
					map.put("type",(Object)type);
				}
				map.put("is_select",is_select);
				flag=true;
//				this.setMainMessage("�޸Ĳ����ɹ���");
			}
		}
	
		if(!flag){
			//���ظ��������ʱ���������б�
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("num",num);
			map.put("paradescribe",(Object)paradescribe);
			if(type.equals("char")){
				map.put("type",(Object)"�ֽ�");
			}else if(type.equals("varchar2")){
				map.put("type",(Object)"�ַ���");
			}else if(type.equals("binary_double")){
				map.put("type",(Object)"˫������");
			}else if(type.equals("number")){
				map.put("type",(Object)"����");
			}else if(type.equals("date")){
				map.put("type",(Object)"����/ʱ��");
			}else{
				map.put("type",(Object)type);
			}
			map.put("is_select",is_select);
			list.add(map);
//			this.setMainMessage("��������ɹ���");
		}
		pe.setValueList(list);
		//��ղ��������
		this.getPageElement("num").setValue(null);
		this.getPageElement("paradescribe").setValue(null);
		this.getPageElement("type").setValue(null);
		this.getPageElement("is_select").setValue("0");
		this.getPageElement("cueRowIndex").setValue("");//�������ݺ�ѡ�������
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * ���溯��
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
			List<HashMap<String, Object>> list = pe.getValueList();//�����б�	
//			//��֤�����б��Ƿ�Ϊ��
//			if(list.size()==0||list==null){
//				this.getExecuteSG().addExecuteCode("alert('�������������');");
//				return EventRtnType.NORMAL_SUCCESS;
//			}	
			String pk_id=this.getPageElement("id").getValue().trim();//����
			String checkname=this.getPageElement("checkname").getValue();
			//�滻�������źͶ���ΪӢ��
			if(checkname.contains("��") || checkname.contains("��")||checkname.contains("��")){
				checkname = checkname.replace("��", "(");
				checkname = checkname.replace("��", ")");
				checkname = checkname.replace("��", ",");
			}
			String dateString = sf.format(new Date());
			if(pk_id.isEmpty()){
				//����
				
				String describe=this.getPageElement("describe").getValue();
				//���溯��
				String sql = "insert into CHECK_FUNCTION (ID,CHECKNAME,DESCRIBE,CREATE_TIME) values(?,?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				String id=UUID.randomUUID().toString();
				ps.setString(1, id);
				ps.setString(2, checkname);
				ps.setString(3, describe);
				ps.setString(4, dateString);
				ps.execute();
			
				//�����������
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
						type="�ֽ�";
					}else if(type.equals("varchar2")){
						type="�ַ���";
					}else if(type.equals("binary_double")){
						type="˫������";
					}else if(type.equals("number")){
						type="����";
					}else if(type.equals("date")){
						type="����/ʱ��";
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
				//�޸�
				String describe=this.getPageElement("describe").getValue();
				//�޸ĺ���
				String updateSql="  update check_function  set checkname='"+checkname+"',describe='"+describe+"' where id='"+pk_id+"'";
				System.out.println(updateSql);
				session.createSQLQuery(updateSql).executeUpdate();
				
				//ɾ��ԭ�еĲ���
				String deleteSql=" delete from check_function_parameter where check_function_id='"+pk_id+"'";
				HBUtil.getHBSession().createSQLQuery(deleteSql).executeUpdate();			
			
				//�����������
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
						type="�ֽ�";
					}else if(type.equals("varchar2")){
						type="�ַ���";
					}else if(type.equals("binary_double")){
						type="˫������";
					}else if(type.equals("number")){
						type="����";
					}else if(type.equals("date")){
						type="����/ʱ��";
					}				
					paraps.setString(5, type);
					paraps.setInt(6, Integer.parseInt(map.get("is_select").toString()));
					paraps.setString(7, dateString);
					paraps.addBatch();
				}
				paraps.executeBatch();
				paraps.close();
			}
			//���»�ȡ���������б�
			List<HashMap<String, Object>> newList = getFunctionList();
			this.getPageElement("functionList").setValueList(newList);	
			if(pk_id.isEmpty()){
				this.getExecuteSG().addExecuteCode("alert('����ɹ�!');");
			}else{
				this.getExecuteSG().addExecuteCode("alert('�޸ĳɹ�!');");
			}
			cleanCond();
		}catch(Exception e){
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �������ֵ
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
	 * ����������͵�ѡ���ֵ
	 * @throws RadowException 
	 */
	@PageEvent("setPara")
	public void setParameter() throws RadowException{
		this.getPageElement("type").setValue(this.getPageElement("type").getValue());
		this.getPageElement("is_select").setValue(this.getPageElement("is_select_para").getValue().toString());
	}
}
