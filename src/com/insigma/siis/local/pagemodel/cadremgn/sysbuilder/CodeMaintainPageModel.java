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
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.cadremgn.util.ExtTreeNodeStr;
import com.insigma.siis.local.pagemodel.cadremgn.util.JsonUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class CodeMaintainPageModel extends PageModel {
	
	private static final String supperId="40288103556cc97701556d629135000f";

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("init");
		return 0;
	}

	@PageEvent("init")
	@NoRequiredValidate
	public int init() throws RadowException, AppException {
		loadType();
		String sql="select * from code_value where code_type='TC03' order by code_value";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		HashMap<String, Object> map=new LinkedHashMap<String, Object>();
		for(int i=0;i<list.size();i++){
			map.put(list.get(i).get("code_value").toString(), list.get(i).get("code_name"));
		}
		this.getPageElement("td6").setValue(null);
		((Combo)this.getPageElement("td6")).setValueListForSelect(map); 
		
		cleanCode();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 系统system用户返回ture
	 * @return
	 */
	private static boolean permision() {
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String cueUserid = user.getId();
		if (cueUserid != null && cueUserid.equals(supperId)) {
			return true;
		} else {
			return false;
		}

	}
	
	/**
	 * 单击代码集，显示代码集信息以及下属代码
	 */
	@PageEvent("grid1.rowclick")
	@NoRequiredValidate
	public void tableChange() throws Exception{
		cleanCode();
		String type_name=this.getPageElement("grid1").getValue("rowsname",0).toString();
		String type_dsc=this.getPageElement("grid1").getValue("rowsdsc",0).toString();
		String[] arr=type_name.split("\\.");
		arr[1]=arr[1].replaceAll("&acute;","'").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		type_dsc=type_dsc.replaceAll("&acute;","'").replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		this.getPageElement("t1").setValue(arr[0]);
		this.getPageElement("t2").setValue(arr[1]);
		this.getPageElement("area2").setValue(type_dsc);
		this.getPageElement("codeType").setValue(arr[0]);
		if(arr[0].substring(0, 2).equals("GB")|| arr[0].substring(0, 2).equals("ZB")){
			if (CodeMaintainPageModel.permision()) {
				this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
				this.createPageElement("t2", ElementType.TEXT , false).setDisabled(true);
				
				this.getExecuteSG().addExecuteCode("hiddenbtn1();");
				this.getExecuteSG().addExecuteCode("showbtn2();");
				this.getExecuteSG().addExecuteCode("showbtn3();");
				this.getExecuteSG().addExecuteCode("hiddenbtn4();");
				this.getExecuteSG().addExecuteCode("hiddenbtn5();");
				this.getExecuteSG().addExecuteCode("hiddenbtn6();");
				
				this.createPageElement("area2", ElementType.TEXTAREA, false).setDisabled(false);
				this.getExecuteSG().addExecuteCode("updateTree();");
			} else {

				this.createPageElement("t1", ElementType.TEXT, false)
						.setDisabled(true);
				this.createPageElement("t2", ElementType.TEXT, false)
						.setDisabled(true);
				this.getExecuteSG().addExecuteCode("hiddenbtn1();");
				this.getExecuteSG().addExecuteCode("hiddenbtn2();");
				this.getExecuteSG().addExecuteCode("hiddenbtn3();");
				this.getExecuteSG().addExecuteCode("hiddenbtn4();");
				this.getExecuteSG().addExecuteCode("hiddenbtn5();");
				this.getExecuteSG().addExecuteCode("hiddenbtn6();");
				this.createPageElement("area2", ElementType.TEXTAREA, false)
						.setDisabled(true);
				this.getExecuteSG().addExecuteCode("updateTree();");
			}
		}else{
			this.createPageElement("t1", ElementType.TEXT, false).setDisabled(true);
			this.createPageElement("t2", ElementType.TEXT , false).setDisabled(true);
			
			this.getExecuteSG().addExecuteCode("hiddenbtn1();");
			this.getExecuteSG().addExecuteCode("showbtn2();");
			this.getExecuteSG().addExecuteCode("showbtn3();");
			this.getExecuteSG().addExecuteCode("hiddenbtn4();");
			this.getExecuteSG().addExecuteCode("hiddenbtn5();");
			this.getExecuteSG().addExecuteCode("hiddenbtn6();");
			
			this.createPageElement("area2", ElementType.TEXTAREA, false).setDisabled(false);
			this.getExecuteSG().addExecuteCode("updateTree();");
		}
	}
	
	/**
	 * 代码集树的更新
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("updateTree")
	public int updateTree() throws RadowException, AppException{
		String node=request.getParameter("node");
		String codeValue=node;
		String codeType=this.request.getParameter("codetype");

		String sql="select * from code_value where code_type='"+codeType+"' and sub_code_value='"+codeValue+"' order by code_value";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		List<ExtTreeNodeStr> listTree=new ArrayList<ExtTreeNodeStr>();
		if(list!=null&&list.size()>0){
			for(HashMap<String, Object> a:list){
				ExtTreeNodeStr etn=new ExtTreeNodeStr();
				etn.setId(a.get("code_value").toString());
				sql="select * from code_value where code_type='"+codeType+"' and sub_code_value='"+a.get("code_value").toString()+"'";
				List<HashMap<String, Object>> listFalg=cqbs.getListBySQL(sql);
				if(listFalg.size()>0){
					etn.setLeaf(false);
				}else{
					etn.setLeaf(true);
				}
				etn.setText(a.get("code_value").toString()+"."+a.get("code_name").toString());
				listTree.add(etn);
			}
		}
		String json = JsonUtil.toJSONString(listTree);
		System.out.println(json);
		this.setSelfDefResData(json);
		return EventRtnType.XML_SUCCESS;
	}
	
	
	/**
	 * 代码集编码命名校验
	 */
	@PageEvent("changeTypeValue")
	@NoRequiredValidate
	public void changeTypeValue() throws Exception{
		String typeValue=this.getPageElement("t1").getValue().trim();
		if(typeValue!=null&&!"".equals(typeValue)){
			if(typeValue.length()>2){
				typeValue=typeValue.toUpperCase();
				if("KZ".equals(typeValue.substring(0,2))){
					String regex="[0-9A-Z]*";
					if(typeValue.substring(2).matches(regex)){
						this.getPageElement("t1").setValue(typeValue);
					}else{
						this.setMainMessage("代码集编码只能输入字母或阿拉伯数字");
						this.getPageElement("t1").setValue("");
					}
				}else{
					this.setMainMessage("代码集编码必须以KZ开头");
					this.getPageElement("t1").setValue("");
				}
			}else{
				this.setMainMessage("代码集编码长度不能少于3位");
				this.getPageElement("t1").setValue("");
			}
		}else{
			this.setMainMessage("代码集编码不能为空");
		}
	}
	/**
	 * 代码集名称和描述校验
	 */
	@PageEvent("specialValue")
	@NoRequiredValidate
	public void specialValue() throws Exception {
		String t2Value = this.getPageElement("t2").getValue();
		String area2Value = this.getPageElement("area2").getValue();
		if (t2Value != null && !"".equals(t2Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (t2Value.matches(regex)) {
				this.getPageElement("t2").setValue(t2Value);
			} else {
				this.setMainMessage("代码集名称只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("t2").setValue("");
			}
		}
		if (area2Value != null && !"".equals(area2Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (area2Value.matches(regex)) {
				if (area2Value.length()> 100)
				  {
					area2Value = area2Value.substring(0, 100);
					this.getPageElement("area2").setValue(area2Value);
				 }
			} else {
				this.setMainMessage("代码集描述只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("area2").setValue("");
			}
		}
	}
	@PageEvent("checkValue")
	@NoRequiredValidate
	public void checkValue() throws Exception {
		String codeType=this.getPageElement("codeType").getValue();
		if("GRPCNYB".equals(codeType)){//字段后的连接符（组合字段二级代码）不校验
			return;
		}
		String td1Value = this.getPageElement("td1").getValue();
		String s2Value = this.getPageElement("s2").getValue();
		String td2Value = this.getPageElement("td2").getValue();
		String td3Value = this.getPageElement("td3").getValue();
		String td4Value = this.getPageElement("td4").getValue();
		String td5Value = this.getPageElement("td5").getValue();
		if (td1Value != null && !"".equals(td1Value)) {
			String regex = "^[0-9a-zA-Z]+$";
			if (td1Value.matches(regex)) {
				this.getPageElement("td1").setValue(td1Value);
			} else {
				this.setMainMessage("代码编号只能输入字母或阿拉伯数字");
				this.getPageElement("td1").setValue("");
			}
		}
		if (s2Value != null && !"".equals(s2Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (s2Value.matches(regex)) {
				this.getPageElement("s2").setValue(s2Value);
			} else {
				this.setMainMessage("代码名称只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("s2").setValue("");
			}
		}
		
		
		if (td2Value != null && !"".equals(td2Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (td2Value.matches(regex)) {
				if (td2Value.length()> 100)
				{
					td2Value = td2Value.substring(0, 100);
					this.getPageElement("td2").setValue(td2Value);
				}
			} else {
				this.setMainMessage("代码简称1只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("td2").setValue("");
			}
		}
		if (td3Value != null && !"".equals(td3Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (td3Value.matches(regex)) {
				if (td3Value.length()> 100)
				{
					td3Value = td3Value.substring(0, 100);
					this.getPageElement("td3").setValue(td3Value);
				}
			} else {
				this.setMainMessage("代码简称2只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("td3").setValue("");
			}
		}
		if (td4Value != null && !"".equals(td4Value)) {
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if (td4Value.matches(regex)) {
				if (td4Value.length()> 100)
				{
					td4Value = td4Value.substring(0, 100);
					this.getPageElement("td4").setValue(td4Value);
				}
			} else {
				this.setMainMessage("代码录入简称只能输入汉字、字母或阿拉伯数字");
				this.getPageElement("td4").setValue("");
			}
		}
		if (td5Value != null && !"".equals(td5Value)) {
			String regex = "^[a-zA-Z]+$";
			if (td5Value.matches(regex)) {
				if (td5Value.length()> 100)
				{
					td5Value = td5Value.substring(0, 100);
					this.getPageElement("td5").setValue(td5Value);
				}
			} else {
				this.setMainMessage("代码首拼录入只能输入字母");
				this.getPageElement("td5").setValue("");
			}
		}
	}
	
	
	/**
	 * 单击代码项
	 */
	@PageEvent("clickCodeValue")
	@NoRequiredValidate
	public void clickCodeValue(String codeValue) throws Exception{
		cleanCode();
		if("-1".equals(codeValue)){
			this.getPageElement("codeid").setValue(codeValue);
//			this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
			this.getExecuteSG().addExecuteCode("showbtn4();");
		}else{
			String codeType=this.getPageElement("t1").getValue();
			if(codeType!=null && !"".endsWith(codeType)){
				String sql="select * from code_value where code_type='"+codeType+"' and code_value='"+codeValue+"'";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				if(!list.isEmpty()){
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("codeid").setValue(codeValue);
				this.getPageElement("td1").setValue(entryStr(map.get("code_value")));
				this.getPageElement("s2").setValue(entryStr(map.get("code_name")));
				this.getPageElement("td2").setValue(entryStr(map.get("code_name2")));
				this.getPageElement("td3").setValue(entryStr(map.get("code_name3")));
				this.getPageElement("td4").setValue(entryStr(map.get("code_name")));
				this.getPageElement("td5").setValue(entryStr(map.get("code_spelling")));
				this.getPageElement("td6").setValue(entryStr(map.get("priority")));
				if ((codeType.substring(0, 2).equals("GB") || codeType
						.substring(0, 2).equals("ZB"))
						&& (!entryStr(map.get("sign")).equals("1"))) {
					if(CodeMaintainPageModel.permision()){
						this.getExecuteSG().addExecuteCode("showbtn4();");
						this.getExecuteSG().addExecuteCode("showbtn5();");
						this.getExecuteSG().addExecuteCode("showbtn6();");
					}else{
						this.getExecuteSG().addExecuteCode("showbtn4();");
						this.getExecuteSG().addExecuteCode("hiddenbtn5();");
						this.getExecuteSG().addExecuteCode("hiddenbtn6();");
					}
				}else{
					this.getExecuteSG().addExecuteCode("showbtn4();");
					this.getExecuteSG().addExecuteCode("showbtn5();");
					this.getExecuteSG().addExecuteCode("showbtn6();");
				}
			}
			}
		}
	}
	
	/**
	 * 新建代码集下面代码
	 */
	@PageEvent("creatCodeValue")
	public void creatCodeValue() throws Exception{
		String codeid=this.getPageElement("codeid").getValue();
		if("-1".equals(codeid)){
			String codeValue=this.getPageElement("td1").getValue();
			if(codeValue!=null&&!"".equals(codeValue)){
				codeValue=codeValue.toUpperCase().trim();
				String codeName=this.getPageElement("s2").getValue();
				if(codeName!=null&&!"".equals(codeName)){
					String codeName2=this.getPageElement("td2").getValue();
					if(codeName2!=null&&!"".equals(codeName2)){
						String codeType=this.getPageElement("t1").getValue();
						CommQuery cqbs=new CommQuery();
							String sql="select * from code_value where code_type='"+codeType+"' and code_value='"+codeValue+"'";
							List<HashMap<String, Object>> listctn=cqbs.getListBySQL(sql);
							if(listctn!=null&&listctn.size()>0){
								this.setMainMessage("代码编码重复");
							}else{
								String codeName3=this.getPageElement("td3").getValue();
								String codeSpeing=this.getPageElement("td5").getValue();
								String priority=this.getPageElement("td6").getValue();
								sql="select max(to_number(code_value_seq)) num from code_value";
								List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
								String number="1";
								if(list.get(0).get("num")!=null && !(list.get(0).get("num")).equals("")){
									number=list.get(0).get("num").toString();
									number=String.valueOf(Integer.valueOf(number)+1);
								}
								if ((codeType.substring(0, 2).equals("GB") || codeType.substring(0, 2).equals("ZB"))){
									sql="insert into code_value (CODE_VALUE_SEQ,CODE_TYPE,CODE_VALUE,SUB_CODE_VALUE,CODE_NAME"
											+ ",CODE_NAME2,CODE_NAME3,CODE_SPELLING,ISCUSTOMIZE,CODE_STATUS,CODE_LEAF,SIGN)"
											+ "  values "
											+ "('"+number+"','"+codeType+"','"+codeValue+"','"+codeid+"','"+codeName+"'"
											+ ",'"+codeName2+"','"+codeName3+"','"+codeSpeing+"','1','1','1','1')";
								}else{
									sql="insert into code_value (CODE_VALUE_SEQ,CODE_TYPE,CODE_VALUE,SUB_CODE_VALUE,CODE_NAME"
											+ ",CODE_NAME2,CODE_NAME3,CODE_SPELLING,ISCUSTOMIZE,CODE_STATUS,CODE_LEAF)"
											+ "  values "
											+ "('"+number+"','"+codeType+"','"+codeValue+"','"+codeid+"','"+codeName+"'"
											+ ",'"+codeName2+"','"+codeName3+"','"+codeSpeing+"','1','1','1')";
								}
								HBSession hbsess = HBUtil.getHBSession();
								Statement  stmt = hbsess.connection().createStatement();
								stmt.executeQuery(sql);
								stmt.close();
								cleanCode();
								GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
								this.setMainMessage("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新增成功");
								this.getExecuteSG().addExecuteCode("updateTree();");
							}
					}else{
						this.setMainMessage("代码简称1不能为空");
					}
				}else{
					this.setMainMessage("代码名称不能为空");
				}
			}else{
				this.setMainMessage("代码编码不能为空");
			}
		}else{
			cleanCode();
			this.getPageElement("newFlag").setValue("1");
			this.getPageElement("td1").setValue(codeid);
			this.getPageElement("codeid").setValue(codeid);
//			this.createPageElement("btn6", ElementType.BUTTON, false).setDisabled(false);
			this.getExecuteSG().addExecuteCode("showbtn6();");
		}
	}
	
	
	/**
	 * 修改代码集下面代码
	 */
	@PageEvent("saveCodeValue")
	public void saveCodeValue() throws Exception{
		String codeid=this.getPageElement("codeid").getValue();
		String newFlag=this.getPageElement("newFlag").getValue();
		String codeValue=this.getPageElement("td1").getValue();
		if(codeValue!=null&&!"".equals(codeValue)){
			codeValue=codeValue.toUpperCase().trim();
			String codeName=this.getPageElement("s2").getValue();
			if(codeName!=null&&!"".equals(codeName)){
				String codeName2=this.getPageElement("td2").getValue();
				if(codeName2!=null&&!"".equals(codeName2)){
					String codeType=this.getPageElement("t1").getValue();
					CommQuery cqbs=new CommQuery();
					if("1".equals(newFlag)){
						String sql="select * from code_value where code_type='"+codeType+"' and code_value='"+codeValue+"'";
						List<HashMap<String, Object>> listctn=cqbs.getListBySQL(sql);
						if(listctn!=null&&listctn.size()>0){
							this.setMainMessage("代码编码重复");
						}else{
							String codeName3=this.getPageElement("td3").getValue();
							String codeSpeing=this.getPageElement("td5").getValue();
							String priority=this.getPageElement("td6").getValue();
							sql="select max(to_number(code_value_seq)) num from code_value";
							List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
							String number=list.get(0).get("num").toString();
							number=String.valueOf(Integer.valueOf(number)+1);
							
							if ((codeType.substring(0, 2).equals("GB") || codeType.substring(0, 2).equals("ZB"))){
							sql="insert into code_value (CODE_VALUE_SEQ,CODE_TYPE,CODE_VALUE,SUB_CODE_VALUE,CODE_NAME"
								+ ",CODE_NAME2,CODE_NAME3,CODE_SPELLING,ISCUSTOMIZE,CODE_STATUS,CODE_LEAF,SIGN)"
								+ "  values "
								+ "('"+number+"','"+codeType+"','"+codeValue+"','"+codeid+"','"+codeName+"'"
								+ ",'"+codeName2+"','"+codeName3+"','"+codeSpeing+"','1','1','1','1')";
								
							}else{
								sql="insert into code_value (CODE_VALUE_SEQ,CODE_TYPE,CODE_VALUE,SUB_CODE_VALUE,CODE_NAME"
										+ ",CODE_NAME2,CODE_NAME3,CODE_SPELLING,ISCUSTOMIZE,CODE_STATUS,CODE_LEAF)"
										+ "  values "
										+ "('"+number+"','"+codeType+"','"+codeValue+"','"+codeid+"','"+codeName+"'"
										+ ",'"+codeName2+"','"+codeName3+"','"+codeSpeing+"','1','1','1')";
							}
							HBSession hbsess = HBUtil.getHBSession();
							Statement  stmt = hbsess.connection().createStatement();
							stmt.executeQuery(sql);
							stmt.close();
							cleanCode();
							GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
							this.getExecuteSG().addExecuteCode("updateTree();");
						}
					}else{
						String sql="select * from code_value where code_type='"+codeType+"' and code_value='"+codeValue+"' and code_value<>'"+codeid+"'";
						List<HashMap<String, Object>> listctn=cqbs.getListBySQL(sql);
						if(listctn!=null&&listctn.size()>0){
							this.setMainMessage("代码编码重复");
						}else{
							this.getExecuteSG().addExecuteCode("updateCodeValue();");
						}
					}
				}else{
					this.setMainMessage("代码简称1不能为空");
				}
			}else{
				this.setMainMessage("代码名称不能为空");
			}
		}else{
			this.setMainMessage("代码编码不能为空");
		}
			
	}
	

	@PageEvent("updateCodeValue")
	public void updateCodeValue() throws RadowException, SQLException, AppException{
		String codeValue=this.getPageElement("td1").getValue().toUpperCase().trim();
		String codeName=this.getPageElement("s2").getValue();
		String codeName2=this.getPageElement("td2").getValue();
		String codeName3=this.getPageElement("td3").getValue();
		String codeSpeing=this.getPageElement("td5").getValue();
		String priority=this.getPageElement("td6").getValue();
		String codeType=this.getPageElement("t1").getValue();
		String codeid=this.getPageElement("codeid").getValue();
		String sql="update code_value set CODE_VALUE='"+codeValue+"',CODE_NAME='"+codeName+"'"
				+ ",CODE_NAME2='"+codeName2+"',CODE_NAME3='"+codeName3+"',CODE_SPELLING='"+codeSpeing+"'"
				+ "  where code_type='"+codeType+"' and code_value='"+codeid+"'";
		HBSession hbsess = HBUtil.getHBSession();
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeQuery(sql);
		stmt.close();
		if(codeValue.equals(codeid)){
			
		}else{
			sql="select * from code_table_col where code_type='"+codeType+"'";
			CommQuery cqbs=new CommQuery();
			List<HashMap<String,Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&&list.size()>0){
				for(HashMap<String,Object> a:list){
					sql="update "+a.get("table_code")+" set "+a.get("col_code")+"='"+codeValue+"' where "+a.get("col_code")+"='"+codeid+"'";
					Statement  stmt1 = hbsess.connection().createStatement();
					stmt1.executeQuery(sql);
					stmt1.close();
				}
			}
		}
		cleanCode();
		GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
		this.getExecuteSG().addExecuteCode("updateTree();");
	}

	
	
	/**
	 * 删除代码集下面代码
	 */
	@PageEvent("deleteCodeValue")
	public void deleteCodeValue() throws Exception{
		String codeType=this.getPageElement("t1").getValue();
		String codeId =  this.getPageElement("codeid").getValue();
		CommQuery cqbs=new CommQuery();
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		String sql="select * from code_value where code_type='"+codeType+"' and code_value='"+codeId+"'";
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		if(list !=null && !list.isEmpty()){
			HashMap<String, Object> map=list.get(0);
			String codeValueSeq = entryStr(map.get("code_value_seq"));
			String deleteSql="delete from code_value where code_value_seq='"+codeValueSeq+"'";
			stmt.executeUpdate(deleteSql);
			String subSql1="select * from code_value where code_type='"+codeType+"' and sub_code_value='"+codeId+"'";
			List<HashMap<String, Object>> subList=cqbs.getListBySQL(subSql1);
			if(subList!=null && !subList.isEmpty()){
				String deleteSql1="delete from code_value where sub_code_value='"+codeId+"' and code_type='"+codeType+"'";
				stmt.executeUpdate(deleteSql1);
				for(int i=0;i<subList.size();i++){
					String codeValue = entryStr(subList.get(i).get("code_value"));
					String subSql2="select * from code_value where code_type='"+codeType+"' and sub_code_value='"+codeValue+"'";
					List<HashMap<String, Object>> subList2=cqbs.getListBySQL(subSql2);
					if(subList2!=null && !subList2.isEmpty()){
						String deleteSql2="delete from code_value where sub_code_value='"+codeValue+"' and code_type='"+codeType+"'";
						stmt.executeUpdate(deleteSql2);
						
					}
					
				}
				
			}
		}
		stmt.close();
		GenericCodeItem.getGenericCodeItem().reBuildGenericCodeItem();
		this.getExecuteSG().addExecuteCode("updateTree();");
		cleanCode();
	}
	
	
	public void loadType() throws AppException, RadowException{
		String sql="select code_type||'.'||type_name rowsname,code_description rowsdsc from code_type order by code_type";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
		this.getPageElement("grid1").setValueList(list);
	}
	public void cleanCode() throws RadowException{
		this.getPageElement("td1").setValue("");
		this.getPageElement("s2").setValue("");
		this.getPageElement("td2").setValue("");
		this.getPageElement("td3").setValue("");
		this.getPageElement("td4").setValue("");
		this.getPageElement("td5").setValue("");
		this.getPageElement("td6").setValue("");
		this.getPageElement("newFlag").setValue("");
		this.getExecuteSG().addExecuteCode("hiddenbtn4();");
		this.getExecuteSG().addExecuteCode("hiddenbtn5();");
		this.getExecuteSG().addExecuteCode("hiddenbtn6();");
		
	}
	public void cleanType() throws RadowException{
		this.getPageElement("t1").setValue("");
		this.getPageElement("t2").setValue("");
		this.getPageElement("area2").setValue("");
		this.createPageElement("t1", ElementType.TEXT, false).setDisabled(false);
		this.createPageElement("t2", ElementType.TEXT, false).setDisabled(false);
		this.getExecuteSG().addExecuteCode("showbtn1();");
		this.getExecuteSG().addExecuteCode("hiddenbtn2();");
		this.getExecuteSG().addExecuteCode("hiddenbtn3();");
		
	}
	
	/**
	 * 新增按钮btn1的单击事件
	 */
	@PageEvent("typeCreat")
	@NoRequiredValidate
	public void typeCreat() throws Exception{
		String typeValue=this.getPageElement("t1").getValue();
		String typeName=this.getPageElement("t2").getValue().trim();
		String typeDsc=this.getPageElement("area2").getValue().trim();
		if(typeValue!=null&&!"".equals(typeValue)){
			if(typeName!=null&&!"".equals(typeName)){
				if(typeDsc!=null&&!"".equals(typeDsc)){
					String sql="select * from code_type where code_type='"+typeValue+"'";
					CommQuery cqbs=new CommQuery();
					List<HashMap<String,Object>> listctn=cqbs.getListBySQL(sql);
					if(listctn!=null&&listctn.size()>0){
						this.setMainMessage("代码集编码与库中已有代码集编码重复");
					}else{
						sql="insert into code_type (code_type,type_name,code_description,iscustomize)"
							+ " values "
							+ "('"+typeValue+"','"+typeName+"','"+typeDsc+"','1')";
						HBSession hbsess = HBUtil.getHBSession();	
						Statement  stmt = hbsess.connection().createStatement();
						stmt.executeQuery(sql);
						stmt.close();
						loadType();
						cleanType();
						cleanCode();
						this.setMainMessage("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新增成功");
					}
				}else{
					this.setMainMessage("代码集描述不能为空");
				}
			}else{
				this.setMainMessage("代码集名称不能为空");
			}
		}else{
			this.setMainMessage("代码集编码不能为空");
		}
	}
	/**
	 * 删除按钮btn2的单击事件
	 */
	@PageEvent("typeDelete")
	@NoRequiredValidate
	public void typeDelete() throws Exception{
		cleanCode();
		String codeType=this.getPageElement("codeType").getValue();
		String deleteCodeType="delete from code_type where code_type='"+codeType+"'";
		String deleteCodevalue="delete from code_value where code_type='"+codeType+"'";
		HBSession hbsess = HBUtil.getHBSession();	
		Statement  stmt = hbsess.connection().createStatement();
		stmt.executeUpdate(deleteCodeType);
		stmt.executeUpdate(deleteCodevalue);
		stmt.close();
		loadType();
		cleanType();
		cleanCode();
		this.getExecuteSG().addExecuteCode("updateTree();");
	}
	/**
	 * 保存按钮btn3的单击事件
	 */
	@PageEvent("typeSave")
	@NoRequiredValidate
	public void typeSave() throws Exception{
		String typeValue=this.getPageElement("t1").getValue();
		String typeDsc=this.getPageElement("area2").getValue().trim();
		typeDsc=typeDsc.replaceAll("'", "&acute;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		if(typeDsc==null||"".equals(typeDsc)){
			this.setMainMessage("代码集描述不能为空");
		}else{
			String sql="update code_type set code_description='"+typeDsc+"' where code_type='"+typeValue+"'";
			HBSession hbsess = HBUtil.getHBSession();	
			Statement  stmt = hbsess.connection().createStatement();
			stmt.executeQuery(sql);
			stmt.close();
			loadType();
			cleanType();
			cleanCode();
		}
	}
	@PageEvent("selBtn")
	@NoRequiredValidate
	public void selBtn() throws Exception{
		String selValue=this.getPageElement("selt1").getValue();
			String regex = "^[A-Za-z0-9\u4e00-\u9fa5]+$";
			if(selValue.matches(regex)|| selValue.equals("")){
				String sql="select code_type||'.'||type_name rowsname,code_description rowsdsc from code_type t where lower(t.code_type) like lower('%"+selValue+"%') or lower(t.type_name) like lower('%"+selValue+"%') order by code_type";
				CommQuery cqbs=new CommQuery();
				List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
				this.getPageElement("grid1").setValueList(list);
			}else{
				this.setMainMessage("代码集名称请输入汉字、字母或阿拉伯数字");
				this.getPageElement("selt2").setValue("");
			}
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
