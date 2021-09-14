package com.insigma.siis.local.pagemodel.customquery.person;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A17;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.pagemodel.publicServantManage.AddJianLiAddPageBS;


public class AddJianLiAddPagePageModel extends PageModel{

	private AddJianLiAddPageBS bs1 = new AddJianLiAddPageBS();
	
	@Override
	@NoRequiredValidate
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("a0000").getValue();//获取被操作人员唯一标识
		HBSession session = HBUtil.getHBSession();
		String sql="select * from a01 where a0000='"+a0000+"'";
		List<A01> a01List=session.createSQLQuery(sql).addEntity(A01.class).list();
		this.getPageElement("a0000").setValue(a0000);
		this.getPageElement("a0101").setValue(a01List.get(0).getA0101());
		this.getPageElement("a0184").setValue(a01List.get(0).getA0184());
		List<A17> a17ListZ=session.createSQLQuery("select * from hz_a17 where a0000='"+a0000+"' and a1709 = '2'").addEntity(A17.class).list();
		if(a17ListZ==null||a17ListZ.size()==0) {
			this.getExecuteSG().addExecuteCode("initJLForOne();");
		}
		List<A17> a17List=session.createSQLQuery("select * from hz_a17 where a0000='"+a0000+"' and a1709 = '1'").addEntity(A17.class).list();
		String important="";
		if(a17List.size()>0) {
			for(A17 a17:a17List) {
				important=important+a17.getA1703()+"\n";
			}
		}
		
		this.getPageElement("lead").setValue(important);
		//this.getExecuteSG().addExecuteCode("document.getElementById('contenttext2').value='"+bs1.genResume(a0000).replaceAll("'", "\\\\'")+"'");
		//this.getExecuteSG().addExecuteCode("toA1701('"+bs1.getA1701(a0000)+"');");
		/**
		 * 查看是否存在已拆分列表
		 */
		if(!"0".equals(bs1.havaA17ByA0000(a0000))){
			this.setNextEventName("grid1.dogridquery");
		};
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("writeLead")
	@NoRequiredValidate
	public int writeLead() throws RadowException {
		HBSession session = HBUtil.getHBSession();
		String a0000=this.getPageElement("a0000").getValue();
		List<A17> a17List=session.createSQLQuery("select * from hz_a17 where a0000='"+a0000+"' and a1709 = '1'").addEntity(A17.class).list();
		String important="";
		if(a17List.size()>0) {
			for(A17 a17:a17List) {
				important=important+a17.getA1703()+"\n";
			}
		}
		
		this.getPageElement("lead").setValue(important);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	*====================================================================================================
	* 方法名称:加载申报列表<br>
	* 方法创建日期:2019年07月19日<br>
	* 方法创建人员:zhangxw<br>
	* 方法最后修改日期:2019年07月19日<br>
	* 方法最后修改人员:zhangxw<br>
	* 方法功能描述:按照 条件进行查询,并加载申报列表 - 分页展示<br>
	* 设计参考文档:XXXX--XXX--XXXX详细设计<br>
	* 结果结构详述:
	*====================================================================================================
	 * @throws RadowException
	*/
	@PageEvent("grid1.dogridquery")
	@NoRequiredValidate
	public int doGridQuery(int start,int limit) throws RadowException {
		String a0000=this.getPageElement("a0000").getValue();
		this.pageQuery(bs1.getMainGridStr(a0000), "SQL", 0, 50);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	*====================================================================================================
	* 方法名称:composeall 简历组合<br>
	* 方法创建日期:2019年07月19日<br>
	* 方法创建人员:zhangxw<br>
	* 方法最后修改日期:2019年07月19日<br>
	* 方法最后修改人员:zhangxw<br>
	* 方法功能描述:将简历按照列表逐条组合并保存到人员基本信息集<br>
	*====================================================================================================
	 * @throws RadowException 
	 * @throws AppException 
	*/
	@PageEvent("compose")
	@NoRequiredValidate
	@Transaction
	public int composeall() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		HBSession session = HBUtil.getHBSession();
		List<HashMap<String,Object>> list = this.getPageElement("grid1").getValueList();
		String a0000 = this.getPageElement("a0000").getValue();
		int size = list.size();
		if(list == null || size == 0){
			this.setMainMessage("请添加简历信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1701 = bs1.saveA17(list, a0000);
		String leadA1703 = this.getPageElement("lead").getValue();
		A17 a17;
		if(!"".equals(leadA1703.trim())&&leadA1703.trim()!=null) {
			a17 = new A17();
			a17.setA1700(UUID.randomUUID().toString().replaceAll("-", ""));
			a17.setA0000(a0000);
			a17.setA1703(leadA1703 == null ? "" : leadA1703 + "");
			a17.setUserid(userid);
			a17.setA1799("" + (list.size()));
			a17.setA1709("1");
			session.save(a17);
			session.flush();
			a1701 = a1701+"\n"+"--重要职务重要经历："+leadA1703;
		}
		bs1.saveA01(a1701, a0000);
		this.setMainMessage("简历保存成功，简历输出样式以预览效果为准，如有误请继续维护！");
		this.getExecuteSG().addExecuteCode("toA1701('"+a1701+"');" );
		this.setNextEventName("grid1.dogridquery");
		//this.getExecuteSG().addExecuteCode("realParent.updateA1701Content('"+a1701+"');self.close();realParent.is_changeToTrue();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:加载申报列表<br>
	* 方法创建日期:2019年07月19日<br>
	* 方法创建人员:zhangxw<br>
	* 方法最后修改日期:2019年07月19日<br>
	* 方法最后修改人员:zhangxw<br>
	* 方法功能描述:按照 条件进行查询,并加载申报列表 - 分页展示<br>
	* 设计参考文档:XXXX--XXX--XXXX详细设计<br>
	* 结果结构详述:
	*====================================================================================================
	 * @throws RadowException 
	 * @throws AppException 
	*/
	@PageEvent("save")
	@NoRequiredValidate
	public int selectall() throws RadowException, AppException {
		List<HashMap<String,Object>> list = this.getPageElement("grid1").getValueList();
		String a0000 = this.getPageElement("a0000").getValue();
		int size = list.size();
		if(list == null || size == 0){
			this.setMainMessage("请添加简历信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		HashMap<String,Object> hash=new HashMap<String,Object>();
		int start = 0;
		int end = 0;
		Object a1701s;
		Object a1702s;
		for(int i =0;i<list.size();i++) {
			hash=list.get(i);
			a1701s=hash.get("a1701");
			a1702s=hash.get("a1702");
			if("".equals(a1701s)||a1701s == null) {
				start++;
			}
			if("".equals(a1702s)||a1702s == null) {
				end++;
			}
		}
		if(end>=2||start>=2) {
			this.setMainMessage("开始时间与结束时间不能有两条或超过两条记录为空！");
			return EventRtnType.FAILD;
		}
		String a1701 = bs1.saveA17(list, a0000);
		this.setNextEventName("grid1.dogridquery");
		//this.getExecuteSG().addExecuteCode("toA1701('"+a1701+"');" );
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	*====================================================================================================
	* 方法名称:splitFunc 拆分简历为列表<br>
	* 方法创建日期:2019年07月19日<br>
	* 方法创建人员:zhangxw<br>
	* 方法最后修改日期:2019年07月19日<br>
	* 方法最后修改人员:zhangxw<br>
	* 方法功能描述:按照 条件进行查询,并加载申报列表 - 分页展示<br>
	* 设计参考文档:XXXX--XXX--XXXX详细设计<br>
	* 结果结构详述:
	*====================================================================================================
	 * @throws RadowException 
	*/
	@PageEvent("splitFunc")
	@NoRequiredValidate
	public int splitFunc(String a1701) throws RadowException {
		String a0000=this.getPageElement("a0000").getValue();
		try {
			String insertA17 = bs1.insertA17WithSD(a0000, a1701);
			this.getExecuteSG().addExecuteCode("isHaveRow('"+insertA17+"');");
			this.setNextEventName("grid1.dogridquery");
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deleteRow")
	@NoRequiredValidate
	@Transaction
	public int deleteRow(String a1700) {
		HBSession session = HBUtil.getHBSession();
		if(!"".equals(a1700) && a1700!=null) {
			String delSql = "delete from hz_a17 where belong_to_a1700 = '"+a1700+"' or a1700 = '"+a1700+"'";
			session.createSQLQuery(delSql).executeUpdate();
			session.flush();
			this.setNextEventName("grid1.dogridquery");
		}else {
			this.setMainMessage("删除失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	//表格双击事件
//	@PageEvent("grid1.rowdbclick")
//	@GridDataRange
//	public int gridrowDbClick() throws RadowException {
//		HBSession session = HBUtil.getHBSession();
//		int rowIndex = this.getPageElement("grid1").getCueRowIndex();
//		String a1700 = this.getPageElement("grid1").getValue("a1700", rowIndex).toString();
//		if("".equals(a1700) || a1700==null) {
//			a1700=UUID.randomUUID().toString().replaceAll("-", "");
//		}
//		this.getExecuteSG().addExecuteCode("zzjlShow('"+a1700+"','"+rowIndex+"');");;
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	
	//表格单击事件 
	@PageEvent("grid1.rowclick")
	@GridDataRange
	public int gridrowClick() throws RadowException {
		HBSession sess=HBUtil.getHBSession();
		HBSession session = HBUtil.getHBSession();
		int rowIndex = this.getPageElement("grid1").getCueRowIndex();
		String a1700 = this.getPageElement("grid1").getValue("a1700", rowIndex).toString();
		String a1799= this.getPageElement("grid1").getValue("a1799", rowIndex).toString();
		String a0000=this.getPageElement("a0000").getValue();
		this.getPageElement("a1700").setValue(a1700);
		this.getPageElement("a1799").setValue(a1799);
		A17 a17 = (A17) sess.get(A17.class, a1700);
		
		if(a17!=null) {
			String a1701Str=pjA17(a17)==null?"":pjA17(a17);
			this.getPageElement("a1705_combotree").setValue("");
			this.copyObjValueToElement(a17,this);
			this.getExecuteSG().addExecuteCode("toA1701_1('"+a1701Str+"');");
		}else{
			a17=new A17();
			this.copyObjValueToElement(a17,this);
			this.getPageElement("a1705_combotree").setValue("");
			this.getPageElement("a1701z").setValue("");
			this.getPageElement("a0000").setValue(a0000);
		}
		String a1705=a17.getA1705();
		if(a1705!=null && !"".equals(a1705)) {
			@SuppressWarnings("unchecked")
			List<String> a1705name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1705+"'  and code_type='JL02'").list();
			if(a1705name.size()>0 && a1705name.get(0)!=null) {
				this.getPageElement("a1705_combotree").setValue(a1705name.get(0));
			}
		}
		this.setNextEventName("grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	@PageEvent("addJL")
//	@GridDataRange
//	public int addJL() throws RadowException {
//		HBSession session = HBUtil.getHBSession();
//		String a1700 = UUID.randomUUID().toString().replaceAll("-", "");
//		this.getExecuteSG().addExecuteCode("zzjlShow('"+a1700+"','"+rowIndex+"');");;
//		return EventRtnType.NORMAL_SUCCESS;
//	}
	@PageEvent("saveA1701")
	@NoRequiredValidate
	@Transaction
	public int saveA1701(String a1701) throws RadowException {
		HBSession session = HBUtil.getHBSession();
		String a0000=this.getPageElement("a0000").getValue();
		String sql="select * from a01 where a0000='"+a0000+"'";
		List<A01> a01List=session.createSQLQuery(sql).addEntity(A01.class).list();
		A01 a01 = a01List.get(0);
		String jlnr=a1701.trim();
		if(a01!=null && !"".equals(jlnr) && jlnr!=null) {
			a01.setA1701(a1701);
			this.setNextEventName("initX");
			this.getExecuteSG().addExecuteCode("changeTab();");
			return EventRtnType.NORMAL_SUCCESS;
		}else {
			this.setMainMessage("请正确输入简历内容！");
			return EventRtnType.FAILD;
		}
		
	}
	
	@PageEvent("grid1.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		String userid = SysUtil.getCacheCurrentUser().getId();
		String a0000= this.getPageElement("a0000").getValue();
		Grid grid6 = (Grid) this.getPageElement("grid1");
		String a1700 = grid6.getValue("a1700") +"";
		String a1701 = grid6.getValue("a1701") +"";
		String a1702 = grid6.getValue("a1702") +"";
		String a1703 = grid6.getValue("a1703") +"";
		String a1704 = "";
		String a1705 = grid6.getValue("a1705") +"";
		String a1799= this.getPageElement("a1799").getValue();
		if(a1701.length()!=6) {
			this.setMainMessage("起始时间应为6位数字");
			this.setNextEventName("grid1.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a1702.length()!=6) {
			this.setMainMessage("截止时间应为6位数字");
			this.setNextEventName("grid1.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(a1705.length()>1) {
			a1704=a1705.substring(0, 2);
		}		
//		@SuppressWarnings("unchecked")
//		List<String> a1705s= HBUtil.getHBSession().createSQLQuery("select code_value from code_value where code_name='"+a1705name+"'  and code_type='JL02'").list();
//		if(a1705s.size()>0 && a1705s.get(0)!=null) {
//			a1705=a1705s.get(0);
//			a1704=a1705.substring(0, 2);
//		}
		String a1706 = grid6.getValue("a1706") +"";
		String a1707 = grid6.getValue("a1707") +"";
		String a1708 = grid6.getValue("a1708") +"";
		List<HashMap<String,Object>> list = this.getPageElement("grid1").getValueList();
		HBSession sess = HBUtil.getHBSession();
		if(a1700!=null && !"".equals(a1700)) {
			sess.createSQLQuery("update hz_a17 set  a1701=? ,a1702=?,a1703=?,a1704=?,a1705=?,a1706=?,a1707=?,a1708=? "
					+ "where a1700=?")
			.setString(0, a1701).setString(1, a1702).setString(2, a1703).setString(3, a1704).setString(4, a1705)
			.setString(5, a1706).setString(6, a1707).setString(7, a1708)
			.setString(8, a1700).executeUpdate();
			sess.flush();
			this.setNextEventName("grid1.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
			
		}else {
			this.setMainMessage("新增条目请在下方简历详情维护中维护！");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}


	@PageEvent("changeA1704name")
	@NoRequiredValidate
	@Transaction
	public int changeA1704name()throws RadowException {
		HBSession session = HBUtil.getHBSession();
		String a1704=this.getPageElement("a1704").getValue();
		@SuppressWarnings("unchecked")
		List<String> a1704name= HBUtil.getHBSession().createSQLQuery("select code_name from code_value where code_value='"+a1704+"'  and code_type='JL02'").list();
		if(a1704name.size()>0 && a1704name.get(0)!=null) {
			this.getPageElement("a1704_combo").setValue(a1704name.get(0));
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("compose1")
	@Transaction
	@NoRequiredValidate
	public int composeall1() throws RadowException, AppException {
		String userid = SysUtil.getCacheCurrentUser().getId();
		HBSession session = HBUtil.getHBSession();
		List<HashMap<String,Object>> list = this.getPageElement("grid").getValueList();
		List<HashMap<String,Object>> list1 = this.getPageElement("grid1").getValueList();
		String a1700 = this.getPageElement("a1700").getValue();
		if(a1700 ==null || "".equals(a1700)) {
			a1700=UUID.randomUUID().toString().replaceAll("-", "");
		}
		A17 a17 = (A17) session.get(A17.class, a1700);
		String a1701= this.getPageElement("a1701").getValue();
		String a1702= this.getPageElement("a1702").getValue();
		String a1703= this.getPageElement("a1703").getValue();
		String a1704= this.getPageElement("a1704").getValue();
		String a1705= this.getPageElement("a1705").getValue();
		String a1706= this.getPageElement("a1706").getValue();
		String a1707= this.getPageElement("a1707").getValue();
		String a1708= this.getPageElement("a1708").getValue();
		String a0000= this.getPageElement("a0000").getValue();
		String a1799= this.getPageElement("a1799").getValue();
		if(a17==null) {
			a17=new A17();
			a17.setA1700(a1700);
			a17.setA0000(a0000);
		}
		a17.setA1701(a1701);
		a17.setA1702(a1702 == null ? "" : a1702 + "");
		a17.setA1703(a1703 == null ? "" : a1703 + "");
		a17.setA1704(a1704 == null ? "" : a1704 + "");
		a17.setA1705(a1705 == null ? "" : a1705 + "");
		a17.setA1706(a1706 == null ? "" : a1706 + "");
		a17.setA1707(a1707 == null ? "" : a1707 + "");
		a17.setA1708(a1708 == null ? "" : a1708 + "");
		a17.setA1799(a1799);
		a17.setA1709("2");
		a17.setUserid(userid);
		a17.setBelongToA1700(a1700);
		String complete=bs1.saveZZA17(list,a0000,a17);
		a17.setComplete(complete);
		session.saveOrUpdate(a17);
		session.flush();
		bs1.pjA1701(a0000);
		String a1701Str=pjA17(a17);
		if(a1701Str==null){
			a1701Str = "";
		}
		String a1701main = bs1.saveA17_1(list1, a0000);
		this.getExecuteSG().addExecuteCode("toA1701('"+a1701main+"');");
		this.getExecuteSG().addExecuteCode("toA1701_1('"+a1701Str+"');");
		this.setMainMessage("保存成功！主简历已按开始时间自动排序！");
		this.setNextEventName("grid.dogridquery");
		this.getExecuteSG().addExecuteCode("radow.doEvent('initX');changeToShow();");
		//this.getExecuteSG().addExecuteCode("realParent.updateA1701Content('"+a1701+"');self.close();realParent.is_changeToTrue();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String pjA17(A17 a17) {
		if(a17!=null) {
			StringBuffer a1701z= new StringBuffer();
			a1701z.append(DateUtil.dataStrFormart(a17.getA1701(), ".", "", "") + "--");
			a1701z.append(DateUtil.dataStrFormart(a17.getA1702(), ".", "", "") + "  ");
			a1701z.append(a17.getComplete()==null?"":a17.getComplete());
			String a1701Str = a1701z.toString();
			a1701Str = a1701Str.replace("--  ", "--         ");
			return a1701Str;
		}else {
			return null;
		}
	}

	@PageEvent("grid.dogridquery")
	@NoRequiredValidate
	public int doGridQuery1(int start,int limit) throws RadowException {
		String a1700 = this.getPageElement("a1700").getValue();
		String sql="select a0000,a1700,a1701,a1702,a1703,a1704,a1705,a1706,a1707,a1708,case when " + DBUtil.getColumnIsNull("a1701") + " and " + DBUtil.getColumnIsNull("a1702") + " then '1' else '0' end as a1798,a1799,a0221,a0192e,complete " + "from hz_a17 where belong_to_a1700 = '" + a1700 + "' and a1709 in ('3','4') order by a1709,to_number(a1701)";
		this.pageQuery(sql, "SQL", 0, 50);
		return EventRtnType.SPE_SUCCESS;
	}
	

	
	@PageEvent("grid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit1() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("grid");
		String a1700 = grid6.getValue("a1700") +"";
		String a1701 = grid6.getValue("a1701") +"";
		String a1702 = grid6.getValue("a1702") +"";
		String a1703 = grid6.getValue("a1703") +"";
		String a1704 = "";
		String a1705 = grid6.getValue("a1705") +"";
		if(a1705.length()>1) {
			a1704=a1705.substring(0, 2);
		}		
//		@SuppressWarnings("unchecked")
//		List<String> a1705s= HBUtil.getHBSession().createSQLQuery("select code_value from code_value where code_name='"+a1705name+"'  and code_type='JL02'").list();
//		if(a1705s.size()>0 && a1705s.get(0)!=null) {
//			a1705=a1705s.get(0);
//			a1704=a1705.substring(0, 2);
//		}
		String a1706 = grid6.getValue("a1706") +"";
		String a1707 = grid6.getValue("a1707") +"";
		String a1708 = grid6.getValue("a1708") +"";
		List<HashMap<String,Object>> list = this.getPageElement("grid").getValueList();
		HBSession sess = HBUtil.getHBSession();
		if(a1700!=null && !"".equals(a1700)) {
			sess.createSQLQuery("update hz_a17 set  a1701=? ,a1702=?,a1703=?,a1704=?,a1705=?,a1706=?,a1707=?,a1708=? "
					+ "where a1700=?")
			.setString(0, a1701).setString(1, a1702).setString(2, a1703).setString(3, a1704).setString(4, a1705)
			.setString(5, a1706).setString(6, a1707).setString(7, a1708)
			.setString(8, a1700).executeUpdate();
			sess.flush();
			this.setNextEventName("grid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
			
		}else {
			String a1700main=this.getPageElement("a1700").getValue();
			String a1700new=UUID.randomUUID().toString().replaceAll("-", "");
			String a0000=this.getPageElement("a0000").getValue();
			String userid = SysUtil.getCacheCurrentUser().getId();
			List<HashMap<String,Object>> list1 = this.getPageElement("grid").getValueList();
			sess.createSQLQuery("insert into hz_a17(a1700,a0000,a1701,a1702,a1703,userid,a1709,belong_to_a1700,complete,a1704,a1705,\r\n" + 
					"a1706,a1707,a1708) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
			.setString(0, a1700new).setString(1, a0000).setString(2, a1701).setString(3, a1702).setString(4, a1703)
			.setString(5, userid).setString(6, "3").setString(7, a1700main)
			.setString(8, a1703).setString(9, a1704).setString(10, a1705).setString(11, a1706)
			.setString(12, a1707).setString(13, a1708).executeUpdate();
			sess.flush();
			this.setNextEventName("grid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	@PageEvent("deleteRow1")
	@NoRequiredValidate
	@Transaction
	public int deleteRow1(String a1700) {
		HBSession session = HBUtil.getHBSession();
		if(!"".equals(a1700) && a1700!=null) {
			String delSql = "delete from hz_a17 where a1700 = '"+a1700+"'";
			session.createSQLQuery(delSql).executeUpdate();
			session.flush();
			this.setNextEventName("grid.dogridquery");
		}else {
			this.setMainMessage("删除失败！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
