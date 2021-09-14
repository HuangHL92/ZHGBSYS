package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.ParaSetBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;


public class ParaSetPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("vsl006Grid.dogridquery");
		Combo tableCode =  (Combo)this.createPageElement("tableCode", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		tableCode.setValueListForSelect(map);
		
		return 0;
	}
	
	/**
	 * 信息集onchange
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("tableCode.onchange")
	@NoRequiredValidate
	public int tableCodeOnchange() throws RadowException, AppException{
		String tableCode = this.getPageElement("tableCode").getValue();
		Combo colCode = (Combo)this.getPageElement("colCode");
		colCode.setValue("");
		TreeMap<String,String> treeMap = null;
		if(!StringUtil.isEmpty(tableCode)){
			try {
				treeMap = RuleSqlListBS.getVru005byVru004(tableCode, false);
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException("根据信息集获取信息项错误！异常信息："+e.getMessage());
			}
		}else{
			return EventRtnType.FAILD;
		}
		colCode.setValueListForSelect(treeMap);
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 信息项onchange
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws SQLException 
	 */
	@PageEvent("colCode.onchange")
	public int colCodeOnchange() throws RadowException, AppException, SQLException{
		String colCode = this.getPageElement("colCode").getValue();
		String tableCode = this.getPageElement("tableCode").getValue();
		if(!StringUtil.isEmpty(colCode) && !StringUtil.isEmpty(tableCode)){
			//this.setNextEventName("vsl006Grid.dogridquery");
			String vsl006s=RuleSqlListBS.getVsl006(tableCode, colCode);
			PageElement pe = this.getPageElement("vsl006Grid");
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(vsl006s.contains(map.get("vsl006").toString())){
					map.put("checked", "true");
				}else{
					map.put("checked", "false");
				}
				
			}
			pe.setValueList(list);
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询表、字段对应的运算符
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("vsl006Grid.dogridquery")
	public int girdQuery(int start,int limit) throws RadowException{
		/*String colCode = this.getPageElement("colCode").getValue();
		String tableCode = this.getPageElement("tableCode").getValue();
		if(!StringUtil.isEmpty(colCode) && !StringUtil.isEmpty(tableCode)){*/
			/*StringBuffer sqlBf = 
				new StringBuffer(" Select Aaa102 vsl006,                                                 ")
						.append("        Case                                                           ")
						.append("          When Trim(Vsl006s) Is Not Null Then                          ")
						.append("           'true'                                                      ")
						.append("          Else                                                         ")
						.append("           'false'                                                     ")
						.append("        End checked                                                    ")
						.append("   From (Select Vsl006s From Code_Table_Col Where 						")
						.append("    table_code = '"+tableCode+"' and col_code = '"+colCode+"') a     	")
						.append("  Right Join v_Aa10 b                                                  ")
						.append("     On a.Vsl006s Like concat(concat('%', b.Aaa102), '%')              ")
						.append("  Where b.Aaa100 = 'VSL006'    order by    vsl006       			    ");*/
			String sql="select 'false' checked,code_value vsl006,code_name vsl006_name from code_value where code_type='VSL006'";
			this.pageQuery(sql,"SQL", start, 100);//现在有84条，默认展示50还没有分页，暂时定死
		/*}*/
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 保存
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("btnSave.onclick")
	@Transaction
	public int save() throws RadowException, AppException{
		String colCode = this.getPageElement("colCode").getValue();
		String tableCode = this.getPageElement("tableCode").getValue();
		List<HashMap<String,Object>> list = this.getPageElement("vsl006Grid").getValueList();
		if(!StringUtil.isEmpty(colCode) && !StringUtil.isEmpty(tableCode)){
			try {
				ParaSetBS.save(tableCode, colCode, list);
			} catch (AppException e) {
				e.printStackTrace();
				throw new AppException("保存失败："+e.getMessage());
			}
		}else{
			this.setMainMessage("请选择信息集或信息项！");
			return EventRtnType.FAILD;
		}
		
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}