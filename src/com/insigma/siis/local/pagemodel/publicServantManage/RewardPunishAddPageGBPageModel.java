package com.insigma.siis.local.pagemodel.publicServantManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;


/**
 * 奖惩情况新增修改页面
 * @author Administrator
 *
 */
public class RewardPunishAddPageGBPageModel extends PageModel {	
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.setNextEventName("RewardPunishGrid.dogridquery");//奖惩情况列表		
		//a01中的奖惩情况
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//saveA14  保存旧方法
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveRewardPunish()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		boolean in = false;
		A14 a14 = new A14();
		this.copyElementsValueToObj(a14, this);
		String a1400 = this.getPageElement("a1400").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		String a1404b = this.getPageElement("a1404b").getValue();
		String a1404a = this.getPageElement("a1404a").getValue();
		String a1411a = this.getPageElement("a1411a").getValue();
		String a1415 = this.getPageElement("a1415").getValue();
		String a1414 = this.getPageElement("a1414").getValue();
		String a1428 = this.getPageElement("a1428").getValue();
		String a1407 = this.getPageElement("a1407").getValue();	//批准日期
		String a1424 = this.getPageElement("a1424").getValue(); //撤销日期
		A01 a01 = (A01)HBUtil.getHBSession().get(A01.class, a0000);
		if(a01==null){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if((a1404b != null && !"".equals(a1404b)) || (a1404a != null && !"".equals(a1404a)) || (a1411a != null && !"".equals(a1411a)) 
				|| (a1415 != null && !"".equals(a1415)) || (a1414 != null && !"".equals(a1414)) || (a1428 != null && !"".equals(a1428)) 
				|| (a1407 != null && !"".equals(a1407)) || (a1424 != null && !"".equals(a1424))){		//只要有一个填值,则进行值的判断
			
			in = true;
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
			
			//奖惩名称代码不允许为空
			
			if(a1404b == null || "".equals(a1404b)){
				this.setMainMessage("奖惩名称代码不能为空！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			if(a1404a == null || "".equals(a1404a)){
				this.setMainMessage("奖惩名称不能为空！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
		/*	if(a1411a == null || "".equals(a1411a)){
				this.setMainMessage("批准机关不能为空！");
				return EventRtnType.NORMAL_SUCCESS;
			}*/
			
			if(a1411a.length() > 30){
				this.setMainMessage("批准机关不能超过30字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			
			
			//奖惩批准日期：与参加工作时间比较，应晚于参加工作时间。
			String a0134 = a01.getA0134();//参加工作时间
			if(a0134!=null&&!"".equals(a0134)&&a1407!=null&&!"".equals(a1407)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a1407.length()==6){
					a1407 += "00";
				}
				if(a1407.compareTo(a0134)<0){
					this.setMainMessage("批准日期不能早于参加工作时间");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			//撤销日期不能早于批准日期
			
			if(a1424!=null&&!"".equals(a1424)&&a1407!=null&&!"".equals(a1407)){
				if(a1424.length()==6){
					a1424 += "00";
				}
				if(a1407.length()==6){
					a1407 += "00";
				}
				if(a1424.compareTo(a1407)<0){
					this.setMainMessage("撤销日期不能早于批准日期");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
			//奖惩名称a1404a
			if(a1404a.length() > 20){
				this.setMainMessage("奖惩名称不能超过20字！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//批准日期不能为空
			if(a1407 == null || "".equals(a1407)){
				this.setMainMessage("批准日期不能为空！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			a14.setA0000(a0000);
			
		}
		try {
			
			if(in){
				if(a1400==null||"".equals(a1400)){
					
					applog.createLog("3141", "A14", a01.getA0000(), a01.getA0101(), "新增记录",  new Map2Temp().getLogInfo(new A14(), a14));
					
					sess.save(a14);	
				}else{
					
					A14 a14_old = (A14)sess.get(A14.class, a1400);
					applog.createLog("3142", "A14", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a14_old, a14));
					PropertyUtils.copyProperties(a14_old, a14);
					sess.update(a14_old);	
				}
			}
			//更新奖惩综述
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a14z101 = "无";
				this.getPageElement("a14z101").setValue(a14z101);
			}
			a01.setA14z101(a14z101);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a14z101').setValue('"+a01.getA14z101()+"')");
			//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			sess.update(a01);
			sess.flush();
			
			CustomQueryBS.setA01(a0000);
	    	A01 a01F = (A01)sess.get(A01.class, a0000);
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));

			
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1400").setValue(a14.getA1400());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('RewardPunishGrid').getStore().reload()");//刷新奖惩情况列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
/***********************************************奖惩情况(a14)*********************************************************************/
	
	/**
	 * 奖惩情况列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.dogridquery")
	@NoRequiredValidate
	public int rewardPunishGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		String sql = "select a.* from A14 a where a0000='"+a0000+"' order by a1407 desc,SUBSTR(A1404B,0,2)";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 奖惩情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishAddBtn.onclick")
	@NoRequiredValidate
	public int rewardPunishWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A14 a14 = new A14();
		PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 奖惩情况的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("RewardPunishGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int rewardPunishGridOnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("RewardPunishGrid").getCueRowIndex();
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 奖惩情况描述追加
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("appendonclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int appendonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		Map map = this.getRequestParamer();
		int index = Integer.valueOf(String.valueOf(map.get("eventParameter")));//当前行号
		String a14z101 = this.getPageElement("a14z101").getValue();//奖惩描述
		//奖惩名称代码
		String a1404b = this.getPageElement("RewardPunishGrid").getValue("a1404b",index).toString();
		//奖惩名称
		String a1404a = this.getPageElement("RewardPunishGrid").getValue("a1404a",index).toString();
		//批准机关
		String a1411a = this.getPageElement("RewardPunishGrid").getValue("a1411a",index).toString();
		//批准时间
		String a1407 = this.getPageElement("RewardPunishGrid").getValue("a1407",index).toString();
		
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
		}else{
			a1407 = "";
		}
		
		boolean haschr = false;
		if("无".equals(a14z101)){
			a14z101 = "";
		}
		if(a14z101.length()>0){
			String laststr = a14z101.substring(a14z101.length()-1);
			if(laststr.matches(",|.|;|，|。|；")){
				a14z101 = a14z101.substring(0,a14z101.length()-1);				
			}
			haschr = true;
		}
		
		StringBuffer desc = new StringBuffer(a14z101);
		if(haschr){
			desc.append("；");
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		
		if(a1404b.startsWith("01") || a1404b.startsWith("1")){//奖
			
			if(!a1404b.equals("01111") && a1404b.startsWith("01111")){
				desc.append("经"+a1411a+"批准，").append("荣获"+a1404a+"。");
			}else{
				desc.append("经"+a1411a+"批准，").append(a1404a+"。");
			}
			
		}else{//惩
			desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
		}
		this.getPageElement("a14z101").setValue(desc.toString());
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(desc.toString());
		sess.update(a01);
		//人员基本信息界面
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a14z101').setValue('"+a01.getA14z101()+"')");
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 奖惩情况描述全部替换
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addAll.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int addAllonClick() throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		List<HashMap<String, Object>> list = this.getPageElement("RewardPunishGrid").getValueList();
		if(list!=null&&list.size()>0){
			StringBuffer desc = new StringBuffer("");
			for(HashMap<String, Object> map : list){
				//奖惩名称代码
				String a1404b = map.get("a1404b").toString();
				//奖惩名称
				String a1404a = map.get("a1404a").toString();
				//批准机关
				String a1411a = map.get("a1411a").toString();
				
				//批准时间
				String a1407 = map.get("a1407").toString();
				
				if(a1407!=null&&a1407.length()>=6){
					a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
				}else{
					a1407 = "";
				}
				
				if(a1404b.startsWith("01")){//奖
					//desc.append("经"+a1411a+"批准，").append(a1404a+"。");
					
					desc.insert(0, a1404a+"；").insert(0, "经"+a1411a+"批准，");
				}else{//惩
					
					//desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
					
					desc.insert(0, "受"+a1404a+"处分。").insert(0, "经"+a1411a+"批准，");
				}
				
				if(!"".equals(a1407)){
					//desc.append(a1407);
					
					desc.insert(0, a1407);
				}
				
				//列表顺序是批准时间倒序，生成得奖惩情况的文字描述需要是批准时间正序
				if(desc.toString().endsWith("；")){
					//desc.deleteCharAt(desc.length()-1).append("；");
					
					desc.deleteCharAt(desc.length()-1).append("。");
				}
				
				
				
			}
			
			this.getPageElement("a14z101").setValue(desc.toString());
			a01.setA14z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			
			//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		}else{
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a01.setA14z101("无");
				this.getPageElement("a14z101").setValue(a01.getA14z101());
				sess.update(a01);
				//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1400)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1400 = this.getPageElement("RewardPunishGrid").getValue("a1400",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A14 a14 = (A14)sess.get(A14.class, a1400);
			A01 a01 = (A01)sess.get(A01.class, a14.getA0000());
			A14 a14_new = a14;
			change_visiable(a14_new);
			applog.createLog("3143", "A14", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A14(), new A14()));
			sess.delete(a14);
			this.getExecuteSG().addExecuteCode("radow.doEvent('RewardPunishGrid.dogridquery')");
			a14 = new A14();
			PMPropertyCopyUtil.copyObjValueToElement(a14, this);	
			
			CustomQueryBS.setA01(a01.getA0000());
	    	A01 a01F = (A01)sess.get(A01.class, a01.getA0000());
			//this.getExecuteSG().addExecuteCode(AddRmbPageModel.setTitle(a01F));
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void change_visiable(A14 a14) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		A01 a01= (A01)session.get(A01.class, a0000);
		String description = this.getPageElement("a14z101").getValue();//页面文字描述
		StringBuffer desc = new StringBuffer("");
		String a1407 = a14.getA1407();
		String a1404b = a14.getA1404b();//奖惩名称代码	
		String a1404a = a14.getA1404a();//奖惩名称
		String a1411a = a14.getA1411a();//批准机关
		if(a1411a==null){
			a1411a="";
		}
		if(a1407!=null&&a1407.length()>=6){
			a1407 = a1407.substring(0,4)+"."+a1407.substring(4,6)+"，";
		}else{
			a1407 = "";
		}
		if(!"".equals(a1407)){
			desc.append(a1407);
		}
		if(a1404b.startsWith("01")){//奖
			desc.append("经"+a1411a+"批准，").append(a1404a+"。");
		}else{//惩6
			desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
		}
		System.out.println(description);
		System.out.println(desc);
		description = description.replaceAll(desc.toString().trim(), "");
		if(desc.toString().endsWith("。")){
			desc.deleteCharAt(desc.length()-1).append("；");
		}
		description= description.replaceAll(desc.toString(), "");
		if("".equals(description.trim())){
			description = "无";
		}
		//奖惩界面内容
		this.getPageElement("a14z101").setValue(description);
		
		//修改a01表
		a01.setA14z101(description);
		session.update(a01);
		//人员基本信息界面
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		
	}
	
	
	/**
	 * 奖惩情况描述同步到任免表，保存入A01
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("synchro.onclick")
	@GridDataRange
	@Transaction
	@NoRequiredValidate
	public int synchroClick() throws RadowException{
		
		String a0000 = this.getPageElement("a0000").getValue();
		String a14z101 = this.getPageElement("a14z101").getValue();
		
		if(a14z101 == null || a14z101.equals("")){
			a14z101 = "无";
		}
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		a01.setA14z101(a14z101);
		sess.update(a01);
		//this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
