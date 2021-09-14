package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.cadremgn.comm.GenericCodeItem;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class BaseFieldPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		String sql="select t.\"GrpCod\" code, t.\"GrpCod\"||'.'|| t.\"GrpCpt\" rowsname from GRPDFN t order by t.\"GrpCod\"";
		System.out.println(sql);
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=new LinkedHashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			String rowsName = (String) list.get(i).get("rowsname");
			rowsName=rowsName.replaceAll("&acute;","'").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replace("&middot;", ".");
			map.put(list.get(i).get("code").toString(), rowsName);
		}
		this.getPageElement("select1").setValue(null);
		((Combo)this.getPageElement("select1")).setValueListForSelect(map); 
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(true);
		
		sql="select code_type,code_type||'.'||type_name rowsname from code_type";
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			mapCode.put(listCode.get(i).get("code_type").toString(), listCode.get(i).get("rowsname"));
		}
		this.getPageElement("s3").setValue(null);
		((Combo)this.getPageElement("s3")).setValueListForSelect(mapCode);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		
		sql="select CODE_VALUE,CODE_NAME from code_value where code_type='TC01' order by code_value_seq";
		List<HashMap<String, Object>> listType=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapType=new LinkedHashMap<String, Object>();
		for(int i=0;i<listType.size();i++){
			mapType.put(listType.get(i).get("code_value").toString(), listType.get(i).get("code_name"));
		}
		this.getPageElement("s2").setValue(null);
		((Combo)this.getPageElement("s2")).setValueListForSelect(mapType);
		
		sql="select CODE_VALUE,CODE_NAME from code_value where code_type='TC02' order by code_value_seq";
		List<HashMap<String, Object>> verifyType=cqbs.getListBySQL(sql);
		HashMap<String, Object> verifyMap=new LinkedHashMap<String, Object>();
		for(int i=0;i<verifyType.size();i++){
			verifyMap.put(verifyType.get(i).get("code_value").toString(), verifyType.get(i).get("code_name"));
		}
		this.getPageElement("s4").setValue(null);
		((Combo)this.getPageElement("s4")).setValueListForSelect(verifyMap);
		
		sql="select * from code_value where code_type='TC04' order by code_value";
		List<HashMap<String, Object>> secretlist=cqbs.getListBySQL(sql);
		HashMap<String, Object> secretMap=new LinkedHashMap<String, Object>();
		for(int i=0;i<secretlist.size();i++){
			secretMap.put(secretlist.get(i).get("code_value").toString(), secretlist.get(i).get("code_name"));
		}
		this.getPageElement("t0").setValue(null);
		((Combo)this.getPageElement("t0")).setValueListForSelect(secretMap);
		this.getPageElement("t7").setValue(null);
		((Combo)this.getPageElement("t7")).setValueListForSelect(secretMap);
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ѡ����ϢȺ
	 */
	@PageEvent("changeValue")
	@NoRequiredValidate
	public void changeValue() throws Exception{
		String value=this.getPageElement("select1").getValue();
		loadTable(value);
		cheanCode();
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(false);
		this.getPageElement("t0").setValue("");
		this.getPageElement("t1").setValue("");
		this.getPageElement("t2").setValue("");
		this.getPageElement("r1").setValue("0");
		this.getPageElement("isGroupCode").setValue("0");
		this.getExecuteSG().addExecuteCode("changeable();");
	}
	
	/**
	 * �޸ļ�����
	 */
	@PageEvent("changeTablecode")
	@NoRequiredValidate
	public void changeTablecode() throws Exception{
		String tablecode=this.getPageElement("t1").getValue().toUpperCase();
		String value=this.getPageElement("select1").getValue();
		if(tablecode.length()>=3){
			if(value.equals(tablecode.substring(0,1).toUpperCase())){
				String regex="^[0-9]{2}$";
				if(tablecode.substring(1,3).matches(regex)){
					if(tablecode.length()==3){
						this.getPageElement("t1").setValue(tablecode);
					}else{
						regex="^[0-9A-Z]$";
						if(tablecode.substring(3).matches(regex)){
							this.getPageElement("t1").setValue(tablecode);
						}else{
							this.setMainMessage("�������4λ����Ϊ���ֻ�Ӣ����ĸ");
							this.getPageElement("t1").setValue("");
						}
					}
				}else{
					this.setMainMessage("������2��3λ����Ϊ����");
					this.getPageElement("t1").setValue("");
				}
			}else{
				this.setMainMessage("�������һλ�������ϢȺ���"+value+"һ��");
				this.getPageElement("t1").setValue("");
			}
		}else{
			this.setMainMessage("�����볤�ȱ���Ϊ3λ");
			this.getPageElement("t1").setValue("");
		}
		loadTable(value);
		/*this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);*/
	}
	
	/**
	 * ������Ϣ������ʾ��Ϣ����Ϣ
	 */
	@PageEvent("grid1.rowclick")
	@NoRequiredValidate
	public void tableChange() throws Exception{
		cheanCode();
		String secretlevel=this.getPageElement("grid1").getValue("secretlevel",0).toString();
		String table_name=this.getPageElement("grid1").getValue("rowsname",0).toString();
		String numflag=this.getPageElement("grid1").getValue("numflag",0).toString();
		String complag=this.getPageElement("grid1").getValue("compflag",0).toString();
		if("".equals(complag.trim())){
			complag="0";
		}		
		this.getPageElement("r1").setValue(numflag);
		this.getPageElement("isGroupCode").setValue(complag);
		String[] arr=table_name.split("\\.");
		this.getPageElement("t0").setValue(secretlevel);
		this.getPageElement("t1").setValue(arr[0]);
		this.getPageElement("t2").setValue(arr[1]);
		this.getPageElement("tt").setValue(arr[0]);
		loadCode(arr[0]);
		//this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(true);
 		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
 		this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(true);
 		
 		//this.createPageElement("r1", ElementType.RADIO , false).setDisabled(true);
 		this.getExecuteSG().addExecuteCode("changeDisable();");
	}
	
	/**
	 * ���������Ϣ��
	 */
	@PageEvent("tableadd")
	@NoRequiredValidate
	public void tableadd() throws Exception{
		/*this.getPageElement("tt").setValue("");
		this.getPageElement("t0").setValue("");
		this.getPageElement("t1").setValue("");
		this.getPageElement("t2").setValue("");
		this.getPageElement("r1").setValue("0");
		this.getPageElement("isGroupCode").setValue("0");
				
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(false);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.getExecuteSG().addExecuteCode("changeable();");   */
		//cheanCode();
		
		String table=this.getPageElement("t1").getValue().toUpperCase();
		if(table!=null&&!"".equals(table.trim())){
			table=table.toUpperCase().trim();
			String name=this.getPageElement("t2").getValue();
			if(name.length()>50){
				this.setMainMessage("�����Ƴ��ȹ���");
			}else if(name!=null&&!"".equals(name.trim())){
				String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
				if (name.matches(regex)) {
					this.getPageElement("t2").setValue(name);
				String sql="select * from code_table where  table_code='"+table+"'";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					this.setMainMessage("��Ϣ����������������Ϣ������ظ���������");
				}else{
					String num=this.getPageElement("isGroupCode").getValue();
					String value=this.getPageElement("select1").getValue();
					String radionum=this.getPageElement("r1").getValue();
					String secretlevel=this.getPageElement("t0").getValue();
					sql="insert into code_table (TABLE_CODE,TABLE_NAME,TABLE_GROUP,CMPRLT_QD,TBLPRP_QD,ISCOMPFLD,secretlevel,createtime) values('"+table+"','"+name+"','"+value+"','"+radionum+"','0',"+num+",'"+secretlevel+"',sysdate)";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeUpdate(sql);
					
					String id="A0000";
					if("B".equals(value)){
						id="B0111";
					}
					sql="create table "+table+"("+table+"00  varchar(120) primary key,"+id+"  varchar(120)";
					if("0".endsWith(radionum)){
						sql=sql+","+table+"99  varchar(10),ID  varchar(50)";
					}
					sql=sql+")";
					stmt.executeUpdate(sql);
					generateFlowField(stmt,value,table,name,radionum);// add mtf 2017.7.11 ׷��һ��Ĭ�ϵ����̱��õ����ֶ�
					loadTable(value);
					cheanCode();
					this.setMainMessage("��Ϣ�������ɹ���");
					this.getPageElement("t1").setValue("");
					this.getPageElement("t2").setValue("");
					this.getPageElement("t0").setValue("");
					this.getPageElement("r1").setValue("0");
				}
				} else {
					this.setMainMessage("������ֻ�����뺺�֡���ĸ����������");
					this.getPageElement("t2").setValue("");
				}
			}else{
				this.setMainMessage("�����Ʋ���Ϊ��");
			}
		}else{
			this.setMainMessage("�����벻��Ϊ��");
		}
	}
	
	public void tableinsert() throws Exception{
		/*this.getPageElement("t0").setValue("");
		this.getPageElement("t1").setValue("");
		this.getPageElement("t2").setValue("");
		this.getPageElement("r1").setValue("0");
		this.getPageElement("isGroupCode").setValue("0");
				
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(false);
		this.getExecuteSG().addExecuteCode("changeable();"); */  
		//cheanCode();
		
		String table=this.getPageElement("t1").getValue().toUpperCase();
		if(table!=null&&!"".equals(table.trim())){
			table=table.toUpperCase().trim();
			String name=this.getPageElement("t2").getValue();
			if(name.length()>50){
				this.setMainMessage("�����Ƴ��ȹ���");
			}else if(name!=null&&!"".equals(name.trim())){
				String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
				if (name.matches(regex)) {
					this.getPageElement("t2").setValue(name);
				String sql="select * from code_table where  table_code='"+table+"'";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				if(list!=null&&list.size()>0){
					this.setMainMessage("��Ϣ����������������Ϣ������ظ���������");
				}else{
					String num=this.getPageElement("isGroupCode").getValue();
					String value=this.getPageElement("select1").getValue();
					String radionum=this.getPageElement("r1").getValue();
					String secretlevel=this.getPageElement("t0").getValue();
					sql="insert into code_table (TABLE_CODE,TABLE_NAME,TABLE_GROUP,CMPRLT_QD,TBLPRP_QD,ISCOMPFLD,secretlevel,createtime) values('"+table+"','"+name+"','"+value+"','"+radionum+"','0',"+num+",'"+secretlevel+"',sysdate)";
					HBSession hbsess = HBUtil.getHBSession();	
					Statement  stmt = hbsess.connection().createStatement();
					stmt.executeUpdate(sql);
					
					String id="A0000";
					if("B".equals(value)){
						id="B0111";
					}
					sql="create table "+table+"("+table+"00  varchar(120) primary key,"+id+"  varchar(120)";
					if("0".endsWith(radionum)){
						sql=sql+","+table+"99  varchar(10),ID  varchar(50)";
					}
					sql=sql+")";
					stmt.executeUpdate(sql);
					generateFlowField(stmt,value,table,name,radionum);// add mtf 2017.7.11 ׷��һ��Ĭ�ϵ����̱��õ����ֶ�
					loadTable(value);
					cheanCode();
					this.setMainMessage("��Ϣ�������ɹ���");
					this.getPageElement("t1").setValue("");
					this.getPageElement("t2").setValue("");
					this.getPageElement("t0").setValue("");
					this.getPageElement("r1").setValue("0");
				}
				} else {
					this.setMainMessage("������ֻ�����뺺�֡���ĸ����������");
					this.getPageElement("t2").setValue("");
				}
			}else{
				this.setMainMessage("�����Ʋ���Ϊ��");
			}
		}else{
			this.setMainMessage("�����벻��Ϊ��");
		}		
	}
	/**
	 * ����޸���Ϣ��
	 */
	@PageEvent("tablechange")
	@NoRequiredValidate
	public void tablechange() throws Exception{
		String tt = this.getPageElement("tt").getValue();
		String tablecode=this.getPageElement("t1").getValue().toUpperCase();
		String tablename=this.getPageElement("t2").getValue();
		String secretlevel=this.getPageElement("t0").getValue();
		if(tt==null||"".equals(tt)){
			tableinsert();
		}else{
			if(tablename.length()>50){
				this.setMainMessage("�����Ƴ��ȹ���");
			}else if(tablename!=null&&!"".equals(tablename.trim())){
				String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
				if (tablename.matches(regex)) {
					this.getPageElement("t2").setValue(tablename);
				HBSession hbsess = HBUtil.getHBSession();	
				Statement  stmt = hbsess.connection().createStatement();
				String sql="update code_table set table_name='"+tablename+"',secretlevel='"+secretlevel+"',updatetime=sysdate  where table_code='"+tablecode+"'";
				stmt.executeUpdate(sql);
				sql="update code_table_col set COL_LECTION_NAME='"+tablename+"' where COL_LECTION_CODE='"+tablecode+"'";
				stmt.executeUpdate(sql);
				String value=this.getPageElement("select1").getValue();
				loadTable(value);
				cheanCode();
				this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
				this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
				this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(false);
				this.getPageElement("t0").setValue("");
				this.getPageElement("t1").setValue("");
				this.getPageElement("t2").setValue("");
				this.getPageElement("r1").setValue("0");
				this.getPageElement("isGroupCode").setValue("0");
				this.getExecuteSG().addExecuteCode("changeable();");
				} else {
					this.setMainMessage("������ֻ�����뺺�֡���ĸ����������");
					this.getPageElement("t2").setValue("");
				}
			}else{
				this.setMainMessage("�����Ʋ���Ϊ��");
			}
		}
		
	}
	
	/**
	 * ���ɾ����Ϣ��
	 */
	@PageEvent("tabledelete")
	@NoRequiredValidate
	public void tabledelete() throws Exception{
		String tablecode=this.getPageElement("t1").getValue().toUpperCase();
		CommQuery cqbs=new CommQuery();
		String sql="select * from  code_table  where table_code='"+tablecode+"' and TBLPRP_QD=0";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			sql="select * from code_table_col  where COL_LECTION_CODE='"+tablecode+"'";
			List<HashMap<String, Object>> listcode=cqbs.getListBySQL(sql);
			if(listcode!=null&&listcode.size()>0){
				this.setMainMessage("����Ϣ�����Ѷ�����ָ�������ɾ��");
			}else{
				HBSession hbsess = HBUtil.getHBSession();	
				Statement  stmt = hbsess.connection().createStatement();
				sql="delete from code_table where table_code='"+tablecode+"'";
				stmt.executeUpdate(sql);
				sql="Drop  table  "+tablecode;
				stmt.executeUpdate(sql);
				
				String value=this.getPageElement("select1").getValue();
				loadTable(value);
				cheanCode();
				this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
				this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
				this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
				this.createPageElement("isGroupCode", ElementType.CHECKBOX, false).setDisabled(false);
				this.getPageElement("t1").setValue("");
				this.getPageElement("t2").setValue("");
				this.getPageElement("r1").setValue("0");
				this.getPageElement("isGroupCode").setValue("0");
				this.getExecuteSG().addExecuteCode("changeable();");
				this.setMainMessage("ɾ���ɹ�");
			}
		}else{
			this.setMainMessage("���Զ�����Ϣ��������ɾ��");
		}
		//this.setMainMessage("����Ϣ���ѱ����ã��޷�ɾ��");
	}
	
	/**
	 * ����ָ�����ʾָ������Ϣ
	 */
	@PageEvent("grid2.rowclick")
	@NoRequiredValidate
	public void codeChange() throws Exception{
		String code_name=this.getPageElement("grid2").getValue("rowsname",0).toString();
		String[] codearr=code_name.split("\\.");
		this.getPageElement("t3").setValue(codearr[0]);
		this.getPageElement("t4").setValue(codearr[1]);
		this.getPageElement("ttt").setValue(codearr[0]);
		CommQuery cqbs=new CommQuery();
		String sql="select col_data_type_should,code_type,fldlth,dspwdh,yesmst,yespss,cdttyp,cdtval1,cdtval2,cdterrdscp,yespdf,secretlevel from  code_table_col where  col_code='"+codearr[0]+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap map=list.get(0);
		String codeType=map.get("col_data_type_should").toString().toLowerCase();
		if("varchar2".equals(codeType)){
			codeType="varchar2";
		}else if("date".equals(codeType)||"number".equals(codeType)||
				"char".equals(codeType)||"binary_double".equals(codeType)||"file".equals(codeType)){
			
		}else{
			codeType=null;
		}
		this.getPageElement("s2").setValue(codeType);
		this.getPageElement("s3").setValue(entryStr(map.get("code_type")));
		this.getPageElement("t5").setValue(entryStr(map.get("fldlth")));
		//this.getPageElement("t6").setValue(entryStr(map.get("dspwdh")));
		this.getPageElement("t7").setValue(entryStr(map.get("secretlevel")));
		this.getPageElement("check1").setValue(entryStr(map.get("yesmst")));
		this.getPageElement("check2").setValue(entryStr(map.get("yespss")));
		this.getPageElement("s4").setValue(entryStr(map.get("cdttyp")));
		this.getPageElement("e1").setValue(entryStr(map.get("cdtval1")));
		this.getPageElement("e2").setValue(entryStr(map.get("cdtval2")));
		this.getPageElement("area1").setValue(entryStr(map.get("cdterrdscp")));
		String type=entryStr(map.get("cdttyp"));
		if(type.contains("B")){
			this.createPageElement("e2", ElementType.TEXT, false).setDisabled(false);
		}else{
			this.createPageElement("e2", ElementType.TEXT, false).setDisabled(true);
		}
		
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(true);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		if(map.get("yespdf")!=null&&"0".equals(map.get("yespdf"))){
			if("date".equals(codeType)||"number".equals(codeType)||
					"char".equals(codeType)||"binary_double".equals(codeType)||"file".equals(codeType)){
				this.createPageElement("t5", ElementType.TEXT, false).setDisabled(true);
			}else{
				this.createPageElement("t5", ElementType.TEXT, false).setDisabled(false);
			}
		}else{
			this.createPageElement("t5", ElementType.TEXT, false).setDisabled(true);
		}
		//this.createPageElement("t6", ElementType.TEXT, false).setDisabled(false);
		
		//this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);  //816����
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(false);
		
		
	}
	
	
	/**
	 * ��ѯ��Ϣ��
	 * @param value
	 * @throws AppException
	 * @throws RadowException
	 */
	public void loadTable(String value) throws AppException, RadowException{
		CommQuery cqbs=new CommQuery();
		String sql="select secretlevel,CMPRLT_QD numflag,ISCOMPFLD compflag,TABLE_CODE||'.'||TABLE_NAME rowsname from code_table where TABLE_GROUP='"+value+"' order by TABLE_CODE";
		List<HashMap<String, Object>> listTbale=cqbs.getListBySQL(sql);
		this.getPageElement("grid1").setValueList(listTbale);
	}
	/**
	 * ��ѯָ����
	 * @param value
	 * @throws AppException
	 * @throws RadowException
	 */
	public void loadCode(String tablename) throws AppException, RadowException{
		CommQuery cqbs=new CommQuery();
		//String sql="select col_code, COL_CODE||'.'||COL_NAME rowsname from code_table_col where TABLE_CODE='"+tablename+"' and isuse='1' order by COL_CODE ";
		String sql="select col_code, COL_CODE||'.'||COL_NAME rowsname from code_table_col where TABLE_CODE='"+tablename+"' and isuse='1' order by NUMORDER ";
		List<HashMap<String, Object>> listTbale=cqbs.getListBySQL(sql);
		this.getPageElement("grid2").setValueList(listTbale);
	}
	
	
	@PageEvent("btnsave.onclick")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		List<HashMap<String,String>> list = this.getPageElement("grid2").getStringValueList();
		//String a0201b = this.getPageElement("a0201b").getValue();	
		//String cura0000 = this.getPageElement("a0000").getValue();	
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			//int i = list.size();
			int i = 1;
			for(HashMap<String,String> m : list){
				String col_code = m.get("col_code");

				
				HBUtil.executeUpdate("update code_table_col set numorder="+i+" where COL_CODE='"+col_code+"' ");
				//HBUtil.executeUpdate("update a02 set a0225="+i+" where a0000='"+a0000+"' and a0201b='"+a0201b+"'");
				/*if(a0000.equals(cura0000)){
					this.getPageElement("a0225").setValue(i+"");
					//this.getExecuteSG().addExecuteCode("parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById('a0225').value='"+i+"'");
					
					this.getExecuteSG().addExecuteCode("parent.document.getElementById('a0225').value='"+i+"'");
				}*/
				i++;
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		//this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
	
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ָ����ҳ��ȫ�����
	 * @throws RadowException 
	 */
	public void cheanCode() throws RadowException{
		List<HashMap<String, Object>> listTbale=new ArrayList<HashMap<String, Object>>();
		this.getPageElement("grid2").setValueList(listTbale);
		this.getPageElement("t3").setValue("");
		this.getPageElement("t4").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("s3").setValue("");
		this.getPageElement("t5").setValue("");
		//this.getPageElement("t6").setValue("");
		this.getPageElement("t7").setValue("");
		this.getPageElement("check1").setValue("0");
		this.getPageElement("check2").setValue("0");
		this.getPageElement("s4").setValue("");
		this.getPageElement("e1").setValue("");
		this.getPageElement("e2").setValue("");
		this.getPageElement("area1").setValue("");
		
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("t5", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("e2", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(false);
		
	}
	/**
	 * Ϊnullת��Ϊ���ַ���
	 * @param obj
	 * @return
	 */
	public String entryStr(Object obj){
		String result="";
		if(obj!=null){
			result=obj.toString();
		}
		return result;
	}
	
	/**
	 * ָ�������ͱ仯
	 * @throws RadowException
	 */
	@PageEvent("changeCodeType")
	@NoRequiredValidate
	public void changeCodeType() throws RadowException{
		String type=this.getPageElement("s2").getValue();
		this.getPageElement("s3").setValue("");
		this.getPageElement("t5").setValue("");
		if("varchar2".equals(type)){
			this.createPageElement("s3", ElementType.SELECT, false).setDisabled(false);
			this.createPageElement("t5", ElementType.TEXT, false).setDisabled(false);
		}else{
			this.getPageElement("s3").setValue("");
			this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
			if("char".equals(type)){
				this.getPageElement("t5").setValue("1");
			}else if("binary_double".equals(type)){
				this.getPageElement("t5").setValue("12");
			}else if("number".equals(type)){
				this.getPageElement("t5").setValue("2");
			}else if("date".equals(type)){
				this.getPageElement("t5").setValue("8");
			}else if("file".equals(type)){
				this.getPageElement("t5").setValue("50");
			}
			this.createPageElement("t5", ElementType.TEXT, false).setDisabled(true);
		}
	}
	
	/**
	 * У�����ͱ仯
	 * @throws RadowException
	 */
	@PageEvent("changeVerifyType")
	@NoRequiredValidate
	public void changeVerifyType() throws RadowException{
		String type=this.getPageElement("s4").getValue();
		this.getPageElement("e1").setValue("");
		this.getPageElement("e2").setValue("");
		if(type.contains("B")){
			this.createPageElement("e2", ElementType.TEXT, false).setDisabled(false);
		}else{
			this.createPageElement("e2", ElementType.TEXT, false).setDisabled(true);
		}
	}
	
	/**
	 * ָ�����޸ı���
	 * @throws RadowException 
	 * @throws SQLException 
	 * @throws AppException 
	 */
	@PageEvent("codeSave")
	@NoRequiredValidate
	public void codeSave() throws RadowException, SQLException, AppException{
		String ttt=this.getPageElement("ttt").getValue().toUpperCase();
		String cadeValue=this.getPageElement("t3").getValue().toUpperCase();
		String cadeDes=this.getPageElement("t4").getValue().trim();
		String cadeType=this.getPageElement("s2").getValue();
		String cadeLen=this.getPageElement("t5").getValue().trim();
		//String cadeShow=this.getPageElement("t6").getValue().trim();
		String secretlevel=this.getPageElement("t7").getValue().trim();
		String cadeMst=this.getPageElement("check1").getValue().trim();
		String cadePss=this.getPageElement("check2").getValue().trim();
		String verType=this.getPageElement("s4").getValue().trim();
		String verValue1=this.getPageElement("e1").getValue().trim();
		String verValue2=this.getPageElement("e2").getValue().trim();
		String verErr=this.getPageElement("area1").getValue().trim();
		if(ttt==null||"".equals(ttt)){
			codeinsert();			
		}else{
			if(cadeDes!=null&&!"".equals(cadeDes)){
				String checkValue = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
				if (cadeDes.matches(checkValue)) {
					if (cadeDes.length() > 50) {
						cadeDes = cadeDes.substring(0, 50);
						this.getPageElement("t4").setValue(cadeDes);
					}
				} else {
					this.setMainMessage("ָ��������ֻ�����뺺�֡���ĸ����������");
					this.getPageElement("t4").setValue("");
					return;
				}
				if(cadeLen!=null&&!"".equals(cadeLen)){
					String regex="[0-9]*";
					if(cadeLen.matches(regex)){
						if(Integer.valueOf(cadeLen)<=8000){
							if("varchar2".equals(cadeType) && cadeLen.equals("0")){
								this.setMainMessage("ָ����Ȳ���Ϊ0");
								return;
							}
							if (verErr.length() > 800) {
								verErr = verErr.substring(0, 800);
								this.getPageElement("area1").setValue(verErr);
							}
							String tablename=this.getPageElement("t1").getValue().trim().toUpperCase();
							String sql="update code_table_col set col_name='"+cadeDes+"',fldlth='"+cadeLen+"',"
									+ "yesmst='"+cadeMst+"',yespss='"+cadePss+"',cdttyp='"+verType+"',"
									+ "cdtval1='"+verValue1+"',cdtval2='"+verValue2+"',cdterrdscp='"+verErr+"',secretlevel='"+secretlevel+"',updatetime=sysdate "
									+ "     where col_code='"+cadeValue+"' and table_code='"+tablename+"'";
							HBSession hbsess = HBUtil.getHBSession();	
							Statement  stmt = hbsess.connection().createStatement();
							stmt.executeQuery(sql);
							sql="comment on column   "+tablename+"."+cadeValue+"  is '"+cadeDes+"'";
							stmt.executeQuery(sql);
							if("varchar2".equals(cadeType)||"number".equals(cadeType)){
								sql="alter table  "+tablename+"  modify ("+cadeValue+"  "+cadeType+"("+cadeLen+"))";
								stmt.executeQuery(sql);
							}
							cheanCodeValue();
							stmt.close();
							GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
						}else{
							this.setMainMessage("ָ����Ȳ��ܴ���8000");
						}
					}else{
						this.setMainMessage("ָ����ȱ���Ϊ��ֵ");
					}
				}else{
					this.setMainMessage("ָ����Ȳ���Ϊ��");
				}
			}else{
				this.setMainMessage("ָ�������Ʋ���Ϊ��");
			}
		}
		
	}
	
	/**
	 * ָ����ɾ��
	 * @throws RadowException 
	 * @throws AppException 
	 * @throws SQLException 
	 */
	@PageEvent("codeDelete")
	@NoRequiredValidate
	public void codeDelete() throws RadowException, AppException, SQLException{
		String cadeValue=this.getPageElement("t3").getValue().toUpperCase();
		CommQuery cqbs=new CommQuery();
		String sql="select * from code_table_col where COL_CODE='"+cadeValue+"'";
		List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
		String flag=list.get(0).get("yespdf").toString();
		if("0".equals(flag)){
			sql="delete from code_table_col where COL_CODE='"+cadeValue+"'";
			HBSession hbsess = HBUtil.getHBSession();	
			Statement  stmt = hbsess.connection().createStatement();
			stmt.executeQuery(sql);
			String tablename=this.getPageElement("t1").getValue().trim().toUpperCase();
			sql="alter table  "+tablename+"  drop  column  "+cadeValue;
			stmt.executeQuery(sql);
			cheanCodeValue();
			//this.setMainMessage("���Զ���ָ�����ѱ����ã��޷�ɾ��");
			this.setMainMessage("ɾ���ɹ�");
		}else{
			this.setMainMessage("���Զ���ָ����޷�ɾ��");
		}
	}
	
	
	@PageEvent("codeAdd")
	@NoRequiredValidate
	public void codeAdd() throws RadowException, SQLException, AppException{
		this.getPageElement("ttt").setValue("");
		this.getPageElement("t3").setValue("");
		this.getPageElement("t4").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("s3").setValue("");
		this.getPageElement("t5").setValue("");
		//this.getPageElement("t6").setValue("");
		this.getPageElement("t7").setValue("");
		this.getPageElement("check1").setValue("0");
		this.getPageElement("check2").setValue("0");
		this.getPageElement("s4").setValue("");
		this.getPageElement("e1").setValue("");
		this.getPageElement("e2").setValue("");
		this.getPageElement("area1").setValue("");
		
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("t5", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("e2", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(false);
		
	}
	/**
	 * ָ��������
	 * @throws RadowException 
	 * @throws SQLException 
	 * @throws AppException 
	 */

	public void codeinsert() throws RadowException, SQLException, AppException{
		String cadeValue=this.getPageElement("t3").getValue().trim().toUpperCase();
		String cadeDes=this.getPageElement("t4").getValue().trim();
		String cadeType=this.getPageElement("s2").getValue();
		String cadeClass=this.getPageElement("s3").getValue();
		String cadeLen=this.getPageElement("t5").getValue().trim();
		//String cadeShow=this.getPageElement("t6").getValue().trim();
		String secretlevel=this.getPageElement("t7").getValue();
		String cadeMst=this.getPageElement("check1").getValue().trim();
		String cadePss=this.getPageElement("check2").getValue().trim();
		String verType=this.getPageElement("s4").getValue().trim();
		String verValue1=this.getPageElement("e1").getValue().trim();
		String verValue2=this.getPageElement("e2").getValue().trim();
		String verErr=this.getPageElement("area1").getValue().trim();
		if(cadeValue!=null&&!"".equals(cadeValue)){
			if(cadeValue.length()>2){
				String regex="^[A-Za-z]$";
				if(cadeValue.substring(0,1).matches(regex)){
					regex="^[0-9]{2}$";
					if(cadeValue.substring(1,3).matches(regex)){
						if(cadeDes!=null&&!"".equals(cadeDes)){
							String checkValue = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
							if (cadeDes.matches(checkValue)) {
								if (cadeDes.length() > 50) {
									cadeDes = cadeDes.substring(0, 50);
									this.getPageElement("t4").setValue(cadeDes);
								}
							} else {
								this.setMainMessage("ָ��������ֻ�����뺺�֡���ĸ����������");
								this.getPageElement("t4").setValue("");
								return;
							}
							if(cadeType!=null&&!"".equals(cadeType)){
								if(cadeLen!=null&&!"".equals(cadeLen)){
									regex="[0-9]*";
									if(cadeLen.matches(regex)){
										if(Integer.valueOf(cadeLen)<=8000){
											if("varchar2".equals(cadeType) && cadeLen.equals("0")){
												this.setMainMessage("ָ����Ȳ���Ϊ0");
												return;
											}
											if (verErr.length() > 800) {
												verErr = verErr.substring(0, 800);
												this.getPageElement("area1").setValue(verErr);
											}
											String tablename=this.getPageElement("t1").getValue().trim().toUpperCase();
											String condition="";
											if("char".equals(cadeType)||"varchar2".equals(cadeType)||
												"number".equals(cadeType)){
												condition=cadeType+"("+cadeLen+")";
											}else if("file".equals(cadeType)){
												condition="varchar2("+cadeLen+")";
											}else{
												condition=cadeType;
											}
											CommQuery cqbs=new CommQuery();
											
											String sql="select * from code_table_col where COL_CODE='"+cadeValue+"' and table_code='"+tablename+"'";
											List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
											if(cadeValue.equals(tablename+"00")||cadeValue=="id"){
												this.setMainMessage("ָ��������Ϊ��������Ĭ���ѱ�����");
											}else if(listctn!=null&&listctn.size()>0){
												this.setMainMessage("ָ����������������ָ��������ظ�");
											}else{
												sql="select * from code_table where TABLE_CODE='"+tablename+"'";
												List<HashMap<String,Object>> listTbl=cqbs.getListBySQL(sql);
												String tableDsc=listTbl.get(0).get("table_name").toString();
												sql="alter table "+tablename+" add  "+cadeValue+"  "+condition;
												HBSession hbsess = HBUtil.getHBSession();	
												Statement  stmt = hbsess.connection().createStatement();
												try {
													stmt.executeQuery(sql);
												} catch (Exception e) {
													this.setMainMessage("ָ�����������������ָ���������ظ�");
												}
												sql="comment on column   "+tablename+"."+cadeValue+"  is '"+cadeDes+"'";
												stmt.executeQuery(sql);
												sql="select max(to_number(ctci)) num from code_table_col";
												List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
												String number=list.get(0).get("num").toString();
												number=String.valueOf(Integer.valueOf(number)+1);
												if("date".equals(cadeType)){
													cadeType="varchar2";
												}
												String str_type="";
												if("date".equals(cadeType)){
													str_type="T";
												}else if("number".equals(cadeType)){
													str_type="I";
												}else if("binary_double".equals(cadeType)){
													str_type="R";
												}else{
													str_type="C";
												}
												cadeType=cadeType.toUpperCase();
												sql="insert into code_table_col (CTCI,TABLE_CODE,COL_CODE,COL_NAME,CODE_TYPE"
													+ ",COL_LECTION_CODE,COL_LECTION_NAME,IS_NEW_CODE_COL,IS_ZBX,ZBX_TJ,COL_DATA_TYPE_SHOULD"
													+ ",ISUSE,FLDLTH,YESMST,YESPSS,CDTTYP,CDTVAL1,CDTVAL2,CDTERRDSCP,YESPDF,secretlevel,createtime,col_data_type,islook)"
													+ "  values "
													+ "('"+number+"','"+tablename+"','"+cadeValue+"','"+cadeDes+"','"+cadeClass+"'"
													+ ",'"+tablename+"','"+tableDsc+"','1','0','0','"+cadeType+"'"
													+ ",'1','"+cadeLen+"','"+cadeMst+"','"+cadePss+"','"+verType+"'"
													+ ",'"+verValue1+"','"+verValue2+"','"+verErr+"','0','"+secretlevel+"',sysdate,'"+str_type+"','1')";
												stmt.executeQuery(sql);
												cheanCodeValue();
												stmt.close();
												GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
												this.setMainMessage("ָ������ӳɹ���");
											}
										}else{
											this.setMainMessage("ָ����Ȳ��ܴ���8000");
										}
									}else{
										this.setMainMessage("ָ����ȱ���Ϊ��ֵ");
									}
								}else{
									this.setMainMessage("ָ����Ȳ���Ϊ��");
								}
							}else{
								this.setMainMessage("ָ�������Ͳ���Ϊ��");
							}
						}else{
							this.setMainMessage("ָ�������Ʋ���Ϊ��");
						}
					}else{
						this.setMainMessage("ָ�������2,3λ����Ϊ����");
					}
				}else{
					this.setMainMessage("ָ��������һλ������A-Z��ĸ");
				}
			}else{
				this.setMainMessage("ָ������볤�Ȳ�С��3λ");
			}
		}else{
			this.setMainMessage("ָ������벻��Ϊ��");
		}
		
	}
	
	/**
	 * ָ�������Ƽ���
	 * @throws RadowException 
	 */
	@PageEvent("changeCodeName")
	@NoRequiredValidate
	public void changeCodeName() throws RadowException{
		String cadeValue=this.getPageElement("t3").getValue().trim().toUpperCase();
		if (cadeValue != null && !"".equals(cadeValue)) {
			if (cadeValue.length() > 2) {
				String regex = "^[A-Za-z]$";
				if (cadeValue.substring(0, 1).matches(regex)) {
					regex = "^[0-9]{2}$";
					if (cadeValue.substring(1, 3).matches(regex)) {
						regex = "^[0-9a-zA-Z]+$";
						if (cadeValue.matches(regex)) {
							this.getPageElement("t3").setValue(cadeValue);
						} else {
							this.setMainMessage("ָ����������Ϊ���ֻ�Ӣ����ĸ");
							this.getPageElement("t3").setValue("");
						}
					} else {
						this.setMainMessage("ָ�������2,3λ����Ϊ����");
						this.getPageElement("t3").setValue("");
					}
				} else {
					this.setMainMessage("ָ��������һλ������A-Z��ĸ");
					this.getPageElement("t3").setValue("");
				}
			} else {
				this.setMainMessage("ָ������볤�Ȳ�С��3λ");
				this.getPageElement("t3").setValue("");
			}
		} else {
			this.setMainMessage("ָ������벻��Ϊ��");
			this.getPageElement("t3").setValue("");
		}
	}
	
	/**
	 * ָ�������Ƽ���
	 * @throws RadowException 
	 */
	@PageEvent("changeCodeName2")
	@NoRequiredValidate
	public void changeCodeName2() throws RadowException{
		String cadeValue=this.getPageElement("t4").getValue().trim().toUpperCase();
		if(cadeValue!=null&&!"".equals(cadeValue)){
			if(cadeValue.length()>2){
				if(cadeValue.length()<60){
					this.getPageElement("t4").setValue(cadeValue);
				}else{
					this.setMainMessage("ָ������볤�Ȳ��ܳ���60");
					this.getPageElement("t4").setValue("");
				}
			}else{
				this.setMainMessage("ָ�������Ƴ��Ȳ�С��3λ");
				this.getPageElement("t4").setValue("");
			}
		}else{
			this.setMainMessage("ָ�������Ʋ���Ϊ��");
			this.getPageElement("t4").setValue("");
		}
	}
	
	/**
	 * ָ����ҳ�����ˢ�£��Ҳ�ȫ�����
	 * @throws RadowException 
	 * @throws AppException 
	 */
	public void cheanCodeValue() throws RadowException, AppException{
		loadCode(this.getPageElement("t1").getValue().toUpperCase());
		this.getPageElement("t3").setValue("");
		this.getPageElement("t4").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("s3").setValue("");
		this.getPageElement("t5").setValue("");
		//this.getPageElement("t6").setValue("");
		this.getPageElement("t7").setValue("");
		this.getPageElement("check1").setValue("0");
		this.getPageElement("check2").setValue("0");
		this.getPageElement("s4").setValue("");
		this.getPageElement("e1").setValue("");
		this.getPageElement("e2").setValue("");
		this.getPageElement("area1").setValue("");
		
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("t5", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("e2", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(false);
		
	}
	
	/**
	 * ����������������Ҫ���ֶ�
	 * @param tableGroup
	 * @param tableName
	 * @throws SQLException 
	 */
	public void generateFlowField(Statement stmt,String tableGroup,String tableName,String tableDesc,String singleMulitFlag){
		if("F".equals(tableGroup.toUpperCase())){
			/*
			 * 1.�жϱ��Ƿ��ǵ���¼
			 * 2.׷��һ���ֶ�ABOUTUSER Ϊ�������̹������û�
			 * 3.��code_table_col���������ֶεļ�¼ code_type ΪSYSUSER
			 */
			try {
				CommQuery cq = new CommQuery();
				if("0".equals(singleMulitFlag)){
					stmt.executeUpdate("update code_table set CMPRLT_QD = '1' WHERE table_code = '"+tableName+"'");
				}
				stmt.executeUpdate("alter table "+tableName+" add aboutuser varchar2(50)");
				String sql="select max(to_number(ctci)) num from code_table_col";
				List<HashMap<String,Object>> list=cq.getListBySQL(sql);
				String number=list.get(0).get("num").toString();
				number=String.valueOf(Integer.valueOf(number)+1);
				sql="insert into code_table_col (CTCI,TABLE_CODE,COL_CODE,COL_NAME,CODE_TYPE"
						+ ",COL_LECTION_CODE,COL_LECTION_NAME,IS_NEW_CODE_COL,IS_ZBX,ZBX_TJ,COL_DATA_TYPE_SHOULD"
						+ ",ISUSE,FLDLTH,YESMST,YESPSS,CDTTYP,CDTVAL1,CDTVAL2,CDTERRDSCP,YESPDF,secretlevel,createtime,col_data_type)"
						+ "  values "
						+ "('"+number+"','"+tableName+"','"+"ABOUTUSER"+"','���̹����û�','SYSUSER'"
						+ ",'"+tableName+"','"+tableDesc+"','1','0','0','VARCHAR2'"
						+ ",'1','"+50+"','"+0+"','"+0+"',''"
						+ ",'','','','0','',sysdate,'C')";
				stmt.execute(sql);
			} catch (Exception e) {
				//�쳣����,��Ӱ�������Ĵ���
				e.printStackTrace();
			} 
		}
	}
	
	
}