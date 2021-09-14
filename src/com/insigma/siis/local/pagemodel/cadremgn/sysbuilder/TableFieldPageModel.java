package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class TableFieldPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		this.getPageElement("select1").setValue("H01");
		CommQuery cqbs=new CommQuery();
		String sql="select COL_CODE||'.'||COL_NAME rowsname from code_table_col "
				+ " where TABLE_CODE in (select TABLE_CODE from code_table where ISCOMPFLD='2') "
				+ " and isuse='1' order by COL_CODE ";
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		this.getPageElement("grid2").setValueList(listCode);
		
		
		sql="select procedurename,procedurename||'.'||proceduredesc procedure from PROCEDURENAME";
		List<HashMap<String, Object>> listProcedure=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapProcedure=new LinkedHashMap<String, Object>();
		for(int i=0;i<listProcedure.size();i++){
			mapProcedure.put(listProcedure.get(i).get("procedurename").toString(), listProcedure.get(i).get("procedure"));
		}
		this.getPageElement("checknum").setValue(null);
		((Combo)this.getPageElement("checknum")).setValueListForSelect(mapProcedure);
		
		sql="select CODE_VALUE,CODE_NAME from code_value where code_type='TC01' order by code_value_seq";
		List<HashMap<String, Object>> listType=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapType=new LinkedHashMap<String, Object>();
		for(int i=0;i<listType.size();i++){
			mapType.put(listType.get(i).get("code_value").toString(), listType.get(i).get("code_name"));
		}
		this.getPageElement("s2").setValue(null);
		((Combo)this.getPageElement("s2")).setValueListForSelect(mapType);
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 指标项新增
	 * @throws RadowException 
	 * @throws SQLException 
	 * @throws AppException 
	 */
	@PageEvent("codeAdd")
	@NoRequiredValidate
	public void codeAdd() throws RadowException, SQLException, AppException{
		Statement  stmt=null;
		try{
			String cadeValue=this.getPageElement("t3").getValue().trim().toUpperCase();
			String cadeDes=this.getPageElement("t4").getValue().trim();
			String cadeType=this.getPageElement("s2").getValue();
			//String cadeClass=this.getPageElement("s3").getValue();
			String cadeLen=this.getPageElement("t5").getValue().trim();
			//String cadeShow=this.getPageElement("t6").getValue().trim();
			String check=this.getPageElement("check1").getValue();
			String checknum=this.getPageElement("checknum").getValue();
			if(cadeValue!=null&&!"".equals(cadeValue)){
				if(cadeValue.length()>2){
					String regex="^[A-Za-z]$";
					if(cadeValue.substring(0,1).matches(regex)){
						regex="^[0-9]{2}$";
						if(cadeValue.substring(1,3).matches(regex)){
							if(cadeDes!=null&&!"".equals(cadeDes)){
								if(cadeType!=null&&!"".equals(cadeType)){
									if(cadeLen!=null&&!"".equals(cadeLen)){
										regex="[0-9]*";
										if(cadeLen.matches(regex)){
											if(Integer.valueOf(cadeLen)<=8000){
												if(cadeLen.equals("0")&&("char".equals(cadeType)||"varchar2".equals(cadeType)||
														"number".equals(cadeType))){
													this.setMainMessage("指标项长度不能为0");
													return;
												}else{
//												if(cadeShow!=null&&!"".equals(cadeShow)){
//													if(cadeShow.matches(regex)){
														String tablename=this.getPageElement("select1").getValue().trim().toUpperCase();
														String condition="";
														if("char".equals(cadeType)||"varchar2".equals(cadeType)||
															"number".equals(cadeType)){
															condition=cadeType+"("+cadeLen+")";
														}else{
															condition=cadeType.endsWith("file")?"blob":"date";
														}
														CommQuery cqbs=new CommQuery();
														String sql="select * from code_table_col where COL_CODE='"+cadeValue+"'";
														List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
														if(listctn!=null&&listctn.size()>0){
															this.setMainMessage("指标项代码与库中已有指标项代码重复");
														}else{
															sql="select * from code_table where TABLE_CODE='"+tablename+"'";
															List<HashMap<String,Object>> listTbl=cqbs.getListBySQL(sql);
															String tableDsc=listTbl.get(0).get("table_name").toString();
															sql="alter table "+tablename+" add  "+cadeValue+"  "+condition;
															HBSession hbsess = HBUtil.getHBSession();	
															stmt = hbsess.connection().createStatement();
															stmt.executeQuery(sql);
															sql="comment on column   "+tablename+"."+cadeValue+"  is '"+cadeDes+"'";
															stmt.executeQuery(sql);
															sql="select max(to_number(ctci)) num from code_table_col";
															List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
															String number=list.get(0).get("num").toString();
															number=String.valueOf(Integer.valueOf(number)+1);
															if("date".equals(cadeType)){
																cadeType="varchar2";
															}
															cadeType=cadeType.toUpperCase();
															sql="insert into code_table_col (CTCI,TABLE_CODE,COL_CODE,COL_NAME,CODE_TYPE"
																+ ",COL_LECTION_CODE,COL_LECTION_NAME,IS_NEW_CODE_COL,IS_ZBX,ZBX_TJ,COL_DATA_TYPE_SHOULD"
																+ ",ISUSE,FLDLTH,DSPWDH,YESPDF,ISLOOK)"
																+ "  values "
																+ "('"+number+"','"+tablename+"','"+cadeValue+"','"+cadeDes+"','"+""+"'"
																+ ",'"+tablename+"','"+tableDsc+"','1','0','0','"+cadeType+"'"
																+ ",'1','"+cadeLen+"','"+""+"','0','1')";
															stmt.executeQuery(sql);
															changeValueCom();
															stmt.close();
															if("1".equals(check)&&!"".equals(checknum)){
																ProcedureMethods pm=new ProcedureMethods();
																if("GenAggrAge".equals(checknum)){
																	pm.GenAggrAge(tablename,cadeValue);
																}else if("GenAwardFine".equals(checknum)){
																	pm.GenAwardFine(tablename,cadeValue);
																}else if("GenInPartyDate".equals(checknum)){
																	pm.GenInPartyDate(tablename,cadeValue);
																}else if("GenInPartyDate_short".equals(checknum)){
																	pm.GenInPartyDate_short(tablename,cadeValue);
																}else if("GenMultiDuty".equals(checknum)){
																	pm.GenMultiDuty();
																}else if("GenMultiTopZC".equals(checknum)){
																	pm.GenMultiTopZC(tablename,cadeValue);
																}else if("GenSingleDuty".equals(checknum)){
																	pm.GenSingleDuty();
																}else if("GenWZAnnualEvaluation".equals(checknum)){
																	pm.GenWZAnnualEvaluation(tablename,cadeValue);
																}else if("GetAllDuty".equals(checknum)){
																	pm.GetAllDuty(tablename,cadeValue);
																}else if("GetAllDutyDmabr1".equals(checknum)){
																	pm.GetAllDutyDmabr1(tablename,cadeValue);
																}else if("GetAllDutyDmabr2".equals(checknum)){
																	pm.GetAllDutyDmabr2(tablename,cadeValue);
																}else if("GetAllDutyDwmc".equals(checknum)){
																	pm.GetAllDutyDwmc(tablename,cadeValue);
																}
																sql="delete from ISPROCESS where grptable='"+tablename+"' and grpcode='"+cadeValue+"'";
																stmt = hbsess.connection().createStatement();
																stmt.executeQuery(sql);
																String uuid=UUID.randomUUID().toString().replace("-", "");
																sql="insert into ISPROCESS(ctci,isprocess,methodname,processname,grptable,grpcode)values"
																	+ "('"+uuid+"','1','"+checknum+"','"+checknum+"','"+tablename+"','"+cadeValue+"')";
																stmt.executeQuery(sql);
																stmt.close();
																hbsess.close();
															}
														}
//													}else{
//														this.setMainMessage("显示宽度必须为数值");
//													}
//												}else{
//													this.setMainMessage("显示宽度不能为空");
//												}
												}
											}else{
												this.setMainMessage("指标项长度不能大于8000");
											}
										}else{
											this.setMainMessage("指标项长度必须为数值");
										}
									}else{
										this.setMainMessage("指标项长度不能为空");
									}
								}else{
									this.setMainMessage("指标项类型不能为空");
								}
							}else{
								this.setMainMessage("指标项名称不能为空");
							}
						}else{
							this.setMainMessage("指标项代码2,3位必须为数字");
						}
					}else{
						this.setMainMessage("指标项代码第一位必须是A-Z字母");
					}
				}else{
					this.setMainMessage("指标项代码长度不小于3位");
				}
			}else{
				this.setMainMessage("指标项代码不能为空");
			}
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
	
	public void changeValueCom() throws RadowException, AppException{
		String tablename=this.getPageElement("select1").getValue();
		CommQuery cqbs=new CommQuery();
		String sql="select COL_CODE||'.'||COL_NAME rowsname from code_table_col where TABLE_CODE='"+tablename+"' and isuse='1' order by COL_CODE ";
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		this.getPageElement("grid2").setValueList(listCode);
		cheanCode();
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		this.getPageElement("check1").setValue("0");
		this.getPageElement("checknum").setValue("");
	}
	
	/**
	 * 指标项页面全部清空
	 * @throws RadowException 
	 */
	public void cheanCode() throws RadowException{
		this.getPageElement("t3").setValue("");
		this.getPageElement("t4").setValue("");
		this.getPageElement("t5").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("check1").setValue("0");
		this.getPageElement("checknum").setValue("");
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(false);
		//this.createPageElement("s3", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("checknum", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
	}
	/**
	 * 指标项删除
	 * @throws RadowException 
	 * @throws AppException 
	 * @throws SQLException 
	 */
	@PageEvent("codeDelete")
	@NoRequiredValidate
	public void codeDelete() throws RadowException, AppException, SQLException{
	
		this.setMainMessage("该指标项已被调用，无法删除");
	}
	/**
	 * 指标项修改保存
	 * @throws RadowException 
	 * @throws SQLException 
	 * @throws AppException 
	 */
	@PageEvent("codeSave")
	@NoRequiredValidate
	public void codeSave() throws RadowException, SQLException, AppException{
		Statement  stmt=null;
		try{
			String cadeValue=this.getPageElement("t3").getValue().toUpperCase();
			String cadeDes=this.getPageElement("t4").getValue().trim();
			String cadeType=this.getPageElement("s2").getValue();
			String cadeLen=this.getPageElement("t5").getValue().trim();
			//String cadeShow=this.getPageElement("t6").getValue().trim();
			String tablename=this.getPageElement("select1").getValue().trim().toUpperCase();
			String check=this.getPageElement("check1").getValue();
			String checknum=this.getPageElement("checknum").getValue();
			if(cadeDes!=null&&!"".equals(cadeDes)){
				if(cadeLen!=null&&!"".equals(cadeLen)){
					String regex="[0-9]*";
					if(cadeLen.matches(regex)){
						if(Integer.valueOf(cadeLen)<=8000){
							if (cadeLen.equals("0")&& ("char".equals(cadeType)|| "varchar2".equals(cadeType) || "number".equals(cadeType))) {
								this.setMainMessage("指标项长度不能为0");
								return;
							} else {
									String sql="update code_table_col set col_name='"+cadeDes+"',fldlth='"+cadeLen+"'"
											+ " "
											+ "     where col_code='"+cadeValue+"' and table_code='"+tablename+"'";
									HBSession hbsess = HBUtil.getHBSession();	
									stmt = hbsess.connection().createStatement();
									stmt.executeQuery(sql);
									sql="comment on column   "+tablename+"."+cadeValue+"  is '"+cadeDes+"'";
									stmt.executeQuery(sql);
									if("varchar2".equals(cadeType)||"number".equals(cadeType)){
										sql="alter table  "+tablename+"  modify ("+cadeValue+"  "+cadeType+"("+cadeLen+"))";
										stmt.executeQuery(sql);
									}
									changeValueCom();
									stmt.close();
									if("1".equals(check)&&!"".equals(checknum)){
										ProcedureMethods pm=new ProcedureMethods();
										if("GenAggrAge".equals(checknum)){
											pm.GenAggrAge(tablename,cadeValue);
										}else if("GenAwardFine".equals(checknum)){
											pm.GenAwardFine(tablename,cadeValue);
										}else if("GenInPartyDate".equals(checknum)){
											pm.GenInPartyDate(tablename,cadeValue);
										}else if("GenInPartyDate_short".equals(checknum)){
											pm.GenInPartyDate_short(tablename,cadeValue);
										}else if("GenMultiDuty".equals(checknum)){
											pm.GenMultiDuty();
										}else if("GenMultiTopZC".equals(checknum)){
											pm.GenMultiTopZC(tablename,cadeValue);
										}else if("GenSingleDuty".equals(checknum)){
											pm.GenSingleDuty();
										}else if("GenWZAnnualEvaluation".equals(checknum)){
											pm.GenWZAnnualEvaluation(tablename,cadeValue);
										}else if("GetAllDuty".equals(checknum)){
											pm.GetAllDuty(tablename,cadeValue);
										}else if("GetAllDutyDmabr1".equals(checknum)){
											pm.GetAllDutyDmabr1(tablename,cadeValue);
										}else if("GetAllDutyDmabr2".equals(checknum)){
											pm.GetAllDutyDmabr2(tablename,cadeValue);
										}else if("GetAllDutyDwmc".equals(checknum)){
											pm.GetAllDutyDwmc(tablename,cadeValue);
										}
										sql="delete from ISPROCESS where grptable='"+tablename+"' and grpcode='"+cadeValue+"'";
										stmt = hbsess.connection().createStatement();
										stmt.executeQuery(sql);
										String uuid=UUID.randomUUID().toString().replace("-", "");
										sql="insert into ISPROCESS(ctci,isprocess,methodname,processname,grptable,grpcode)values"
											+ "('"+uuid+"','1','"+checknum+"','"+checknum+"','"+tablename+"','"+cadeValue+"')";
										stmt.executeQuery(sql);
										stmt.close();
										hbsess.close();
									}
							}
						}else{
							this.setMainMessage("指标项长度不能大于8000");
						}
					}else{
						this.setMainMessage("指标项长度必须为数值");
					}
				}else{
					this.setMainMessage("指标项长度不能为空");
				}
			}else{
				this.setMainMessage("指标项名称不能为空");
			}
		}catch(Exception e){
			try{
				if(stmt!=null){
					stmt.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 单击组合字段，进行选择
	 */
	@PageEvent("grid2.rowclick")
	@NoRequiredValidate
	public void tableChange() throws Exception{
		cheanCode();
		String table_name=this.getPageElement("grid2").getValue("rowsname",0).toString();
		String[] arr=table_name.split("\\.");
		this.getPageElement("t3").setValue(arr[0]);
		this.getPageElement("t4").setValue(arr[1]);
		CommQuery cqbs=new CommQuery();
		String sql="select ctci,col_data_type_should,code_type,fldlth,dspwdh,yesmst,yespss,cdttyp,cdtval1,cdtval2,cdterrdscp from  code_table_col where  col_code='"+arr[0]+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=list.get(0);
		String codeType=map.get("col_data_type_should").toString().toLowerCase();
		if("varchar2".equals(codeType)){
			codeType="varchar2";
		}else if("date".equals(codeType)||"number".equals(codeType)||
				"char".equals(codeType)||"binary_double".equals(codeType)){
			
		}else{
			codeType="file";
		}
		String tablename=this.getPageElement("select1").getValue().trim().toUpperCase();
		sql="select * from ISPROCESS where grptable='"+tablename+"' and grpcode='"+arr[0]+"'";
		List<HashMap<String, Object>> listCtci=cqbs.getListBySQL(sql);
		if(listCtci!=null&&listCtci.size()>0){
			this.getPageElement("check1").setValue("1");
			String str=listCtci.get(0).get("methodname").toString();
			this.getPageElement("checknum").setValue(listCtci.get(0).get("methodname").toString());
			this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		}else{
			this.getPageElement("check1").setValue("0");
			this.createPageElement("checknum", ElementType.SELECT, false).setDisabled(true);
		}
		this.getPageElement("s2").setValue(codeType);
		//this.getPageElement("s3").setValue(entryStr(map.get("code_type")));
		this.getPageElement("t5").setValue(entryStr(map.get("fldlth")));
		//this.getPageElement("t6").setValue(entryStr(map.get("dspwdh")));
		this.getPageElement("ctci").setValue(entryStr(map.get("ctci")));
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(true);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(true);
		//this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
	}
	/**
	 * 为null转换为空字符串
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
}
