package com.insigma.siis.local.pagemodel.cadremgn.sysbuilder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class GroupFieldPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		String sql="select table_code code, table_code||'.'||table_name codename from code_table where ISCOMPFLD='1'";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=new LinkedHashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).get("code").toString(), list.get(i).get("codename"));
		}
		this.getPageElement("select1").setValue(null);
		((Combo)this.getPageElement("select1")).setValueListForSelect(map);
		
		sql="select CODE_VALUE,CODE_NAME from code_value where code_type='TC01' order by code_value_seq";
		List<HashMap<String, Object>> listType=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapType=new LinkedHashMap<String, Object>();
		for(int i=0;i<listType.size();i++){
			mapType.put(listType.get(i).get("code_value").toString(), listType.get(i).get("code_name"));
		}
		this.getPageElement("s2").setValue(null);
		((Combo)this.getPageElement("s2")).setValueListForSelect(mapType);
		
		sql="select code_type,code_type||'.'||type_name rowsname from code_type";
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			mapCode.put(listCode.get(i).get("code_type").toString(), listCode.get(i).get("rowsname"));
		}
		this.getPageElement("s3").setValue(null);
		((Combo)this.getPageElement("s3")).setValueListForSelect(mapCode);
		
		sql="select procedurename,procedurename||'.'||proceduredesc procedure from PROCEDURENAME";
		List<HashMap<String, Object>> listProcedure=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapProcedure=new LinkedHashMap<String, Object>();
		for(int i=0;i<listProcedure.size();i++){
			mapProcedure.put(listProcedure.get(i).get("procedurename").toString(), listProcedure.get(i).get("procedure"));
		}
		this.getPageElement("checknum").setValue(null);
		((Combo)this.getPageElement("checknum")).setValueListForSelect(mapProcedure);
		
//		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择信息集
	 */
	@PageEvent("changeValue")
	@NoRequiredValidate
	public void changeValue() throws Exception{
		changeValueCom();
	}
	
	public void changeValueCom() throws RadowException, AppException{
		String tablename=this.getPageElement("select1").getValue();
		CommQuery cqbs=new CommQuery();
		String sql="select COL_CODE||'.'||COL_NAME rowsname from code_table_col where TABLE_CODE='"+tablename+"' "
				//+ " and isuse='1' "
				+ " and islook='1'"
				+ " order by COL_CODE ";
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		this.getPageElement("grid1").setValueList(listCode);
		cheanCode();
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		this.getPageElement("check1").setValue("0");
		this.getPageElement("checknum").setValue("");
	}
	
	/**
	 * 单击组合字段，进行选择
	 */
	@PageEvent("grid1.rowclick")
	@NoRequiredValidate
	public void tableChange() throws Exception{
		cheanCode();
		String table_name=this.getPageElement("grid1").getValue("rowsname",0).toString();
		String[] arr=table_name.split("\\.");
		this.getPageElement("t3").setValue(arr[0]);
		this.getPageElement("t4").setValue(arr[1]);
		CommQuery cqbs=new CommQuery();
		String sql="select ctci,col_data_type_should,code_type,fldlth,dspwdh,yesmst,yespss,cdttyp,cdtval1,cdtval2,cdterrdscp from  code_table_col where  col_code='"+arr[0]+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=list.get(0);
		Object o=map.get("col_data_type_should");
		String codeType="";
		if(o!=null){
			codeType=o.toString().toLowerCase();
		}
		if("varchar2".equals(codeType)){
			codeType="varchar2";
		}else if("date".equals(codeType)||"number".equals(codeType)||
				"char".equals(codeType)||"binary_double".equals(codeType)){
			
		}else{
			codeType=null;
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
		this.getPageElement("s3").setValue(entryStr(map.get("code_type")));
		this.getPageElement("t5").setValue(entryStr(map.get("fldlth")));
		this.getPageElement("t6").setValue(entryStr(map.get("dspwdh")));
		this.getPageElement("ctci").setValue(entryStr(map.get("ctci")));
		this.createPageElement("btn1", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btn3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(true);
		/*this.createPageElement("s2", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(true);*/
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
		String cadeValue=this.getPageElement("t3").getValue().trim().toUpperCase();
		String cadeDes=this.getPageElement("t4").getValue().trim();
		String cadeType=this.getPageElement("s2").getValue();
		String cadeClass=this.getPageElement("s3").getValue();
		String cadeLen=this.getPageElement("t5").getValue().trim();
		String cadeShow=this.getPageElement("t6").getValue().trim();
		String check=this.getPageElement("check1").getValue();
		String checknum=this.getPageElement("checknum").getValue();
		if("1".equals(check)&&"".equals(checknum)){
			this.setMainMessage("请选择具体的存储过程字段");
		}else if(cadeValue!=null&&!"".equals(cadeValue)){
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
											if(cadeShow!=null&&!"".equals(cadeShow)){
												if(cadeShow.matches(regex)){
													String tablename=this.getPageElement("select1").getValue().trim().toUpperCase();
													String condition="";
													if("char".equals(cadeType)||"varchar2".equals(cadeType)||
														"number".equals(cadeType)){
														condition=cadeType+"("+cadeLen+")";
													}else{
														condition=cadeType;
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
														Statement  stmt = hbsess.connection().createStatement();
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
															+ "('"+number+"','"+tablename+"','"+cadeValue+"','"+cadeDes+"','"+cadeClass+"'"
															+ ",'"+tablename+"','"+tableDsc+"','1','0','0','"+cadeType+"'"
															+ ",'1','"+cadeLen+"','"+cadeShow+"','0','1')";
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
															Statement  stmtIs = hbsess.connection().createStatement();
															stmtIs.executeQuery(sql);
															String uuid=UUID.randomUUID().toString().replace("-", "");
															sql="insert into ISPROCESS(ctci,isprocess,processname,grptable,grpcode) values"//methodname
																+ "('"+uuid+"','1','"+checknum+"','"+tablename+"','"+cadeValue+"')";
															stmtIs.executeQuery(sql);
															stmtIs.close();
															this.setMainMessage("指标项新增成功！");
														}
													}
												}else{
													this.setMainMessage("显示宽度必须为数值");
												}
											}else{
												this.setMainMessage("显示宽度不能为空");
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
		String cadeValue=this.getPageElement("t3").getValue().toUpperCase();
		String cadeDes=this.getPageElement("t4").getValue().trim();
		String cadeType=this.getPageElement("s2").getValue();
		String cadeLen=this.getPageElement("t5").getValue().trim();
		String cadeShow=this.getPageElement("t6").getValue().trim();
		String tablename=this.getPageElement("select1").getValue().trim().toUpperCase();
		String check=this.getPageElement("check1").getValue();
		String checknum=this.getPageElement("checknum").getValue();
		if(cadeDes!=null&&!"".equals(cadeDes)){
			if(cadeLen!=null&&!"".equals(cadeLen)){
				String regex="[0-9]*";
				if(cadeLen.matches(regex)){
					if(Integer.valueOf(cadeLen)<=8000){
						if(cadeShow!=null&&!"".equals(cadeShow)){
							if(cadeShow.matches(regex)){
								String cadeClass=this.getPageElement("s3").getValue();
								String sql="update code_table_col set col_name='"+cadeDes+"',fldlth='"+cadeLen+"',"
										+ "dspwdh='"+cadeShow+"'"
										+ ",code_type='"+cadeClass+"'"
										+ ",COL_DATA_TYPE_SHOULD='"+cadeType+"'"
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
									Statement  stmtIs = hbsess.connection().createStatement();
									stmtIs.executeQuery(sql);
									String uuid=UUID.randomUUID().toString().replace("-", "");
									sql="insert into ISPROCESS(ctci,isprocess,methodname,processname,grptable,grpcode)values"
										+ "('"+uuid+"','1','"+checknum+"','"+checknum+"','"+tablename+"','"+cadeValue+"')";
									stmtIs.executeQuery(sql);
									stmtIs.close();
								}
							}else{
								this.setMainMessage("显示宽度必须为数值");
							}
						}else{
							this.setMainMessage("显示宽度不能为空");
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
		this.setMainMessage("组合字段定义生成成功!");
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
		//ProcedureMethods pm=new ProcedureMethods();
		//pm.GetAllDuty();
		this.setMainMessage("该指标项已被调用，无法删除");
	}
	
	/**
	 * 指标项页面全部清空
	 * @throws RadowException 
	 */
	public void cheanCode() throws RadowException{
		this.getPageElement("t3").setValue("");
		this.getPageElement("t4").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("s3").setValue("");
		this.getPageElement("t5").setValue("");
		this.getPageElement("t6").setValue("");
		this.getPageElement("check1").setValue("0");
		this.getPageElement("checknum").setValue("");
		this.createPageElement("t3", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("s2", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("s3", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("checknum", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
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
	
	/**
	 * 指标项名称检验
	 * @throws RadowException 
	 */
	@PageEvent("changeCodeName")
	@NoRequiredValidate
	public void changeCodeName() throws RadowException{
		String cadeValue=this.getPageElement("t3").getValue().trim().toUpperCase();
		if(cadeValue!=null&&!"".equals(cadeValue)){
			if(cadeValue.length()>2){
				String regex="^[A-Za-z]$";
				if(cadeValue.substring(0,1).matches(regex)){
					regex="^[0-9]{2}$";
					if(cadeValue.substring(1,3).matches(regex)){
						this.getPageElement("t3").setValue(cadeValue);
					}else{
						this.setMainMessage("指标项代码2,3位必须为数字");
						this.getPageElement("t3").setValue("");
					}
				}else{
					this.setMainMessage("指标项代码第一位必须是A-Z字母");
					this.getPageElement("t3").setValue("");
				}
			}else{
				this.setMainMessage("指标项代码长度不小于3位");
				this.getPageElement("t3").setValue("");
			}
		}else{
			this.setMainMessage("指标项代码不能为空");
			this.getPageElement("t3").setValue("");
		}
	}
	@PageEvent("changeColDataTypeShould")
	@NoRequiredValidate
	public int changeColDataTypeShould() throws RadowException, AppException{
		String s3=this.getPageElement("s3").getValue();
		String sql="select * from (select * from (select length(code_value) length,code_value from code_value where code_type='"+s3+"') order by length desc ) where rownum=1";
		CommQuery cq=new CommQuery();
		List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
		if(list!=null&&list.size()>0){
			HashMap<String,Object> map=list.get(0);
			if(map!=null&&!map.isEmpty()){
				Object o=map.get("length");
				if(o!=null){
					this.getPageElement("t5").setValue(o.toString());
				}
			}
		}
		return 1;
	}
	/**
	 * 指标项类型变化
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
			}
			this.createPageElement("t5", ElementType.TEXT, false).setDisabled(true);
		}
	}
	
	/**
	 * 是否存储过程和设置按钮的编辑控制
	 * @throws RadowException
	 */
	@PageEvent("changecheck")
	@NoRequiredValidate
	public void changecheck() throws RadowException{
		String check1=this.getPageElement("check1").getValue();
		if("1".equals(check1)){
			this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("checknum", ElementType.SELECT, false).setDisabled(false);
		}else{
			this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
			this.getPageElement("checknum").setValue("");
			this.createPageElement("checknum", ElementType.SELECT, false).setDisabled(true);
		}
	}
}
