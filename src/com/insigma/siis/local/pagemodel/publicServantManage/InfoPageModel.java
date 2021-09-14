package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

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
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A60;
import com.insigma.siis.local.business.entity.A61;
import com.insigma.siis.local.business.entity.A62;
import com.insigma.siis.local.business.entity.A63;
import com.insigma.siis.local.business.entity.A71;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.ReturnDO;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
/**
 * 信息录入页面
 * @author huangcheng
 *
 */
public class InfoPageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	String color = "Grey";	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//页面数据初始化
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		//String a0000 = this.getRadow_parent_data();
		//String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a0000 = (String)this.request.getSession().getAttribute("a0000");
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		Calendar  c = new  GregorianCalendar();
		int year = c.get(Calendar.YEAR);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<80;i++){
			map.put(""+(year-i), year-i);
		}
		((Combo)this.getPageElement("a1521")).setValueListForSelect(map); 
		this.setNextEventName("AssessmentInfoGrid.dogridquery");//年度考核情况列表		
		//a01中年度考核情况
		try {
			HBSession sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			PMPropertyCopyUtil.copyObjValueToElement(a01, this);
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			if(list!=null&&list.size()>0){
				A15 a15 = list.get(0);
				this.getPageElement("a1527").setValue(a15.getA1527());
			}
			
			
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("changedispaly();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	/**
	*====================================================================================================
	* 方法名称:getTreeJsonData.生成信息录入信息集树形结构<br>
	* 方法创建日期:2017年06月9日<br>
	* 输入参数
	* <table>
	*  参数序号				参数名称				参数描述				参数数据类型
	*  <li>(01)
	* </table>
	* 返回结果
	* <table>
	*  结果序号				结果名称				结果描述				结果数据类型
	*  <li>(01)	 EventRtnType.NORMAL_SUCCESS   返回成功状态				  int
	* </table>
	* 结果结构详述:生成补充信息集树形结构页面
	*====================================================================================================
	*/
	@PageEvent("getTreeJsonData")
	public int getTreeJsonData() throws RadowException{                               //获取所有的补充信息类型集合
		StringBuffer jsonStr = new StringBuffer();                            //创建字符串容器对象，用来传递给EXT树
		//学历学位
		jsonStr.append("[{\"text\" :\"常用信息集\" ,\"id\" :\"menu1\",\"cls\" :\"folder\",");
		jsonStr.append("\"children\":[");
		//jsonStr.append("{\"text\" :\"综合信息集\" ,\"id\" :\"leaf_All\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"人员基本信息\" ,\"id\" :\"leaf_A01\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"职务信息集\" ,\"id\" :\"leaf_A02\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"专业技术任职资格信息集\" ,\"id\" :\"leaf_A06\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"学历学位信息集\" ,\"id\" :\"leaf_A08\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"奖惩信息集\" ,\"id\" :\"leaf_A14\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"考核信息集\" ,\"id\" :\"leaf_A15\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"家庭成员及社会关系信息集\" ,\"id\" :\"leaf_A36\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"备注信息集\" ,\"id\" :\"leaf_A71\",\"cls\" :\"folder\",\"leaf\":true}]},");
		
		//职务信息
		jsonStr.append("{\"text\" :\"业务信息集\" ,\"id\" :\"menu2\",");
		jsonStr.append("\"children\":[");
		//jsonStr.append("{\"text\" :\"培训信息集\" ,\"id\" :\"leaf_A11\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"进入管理信息集\" ,\"id\" :\"leaf_A29\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"退出管理信息集\" ,\"id\" :\"leaf_A30\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"住址通信\" ,\"id\" :\"leaf_A37\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"培训人员\" ,\"id\" :\"leaf_A41\",\"cls\" :\"folder\",\"leaf\":true},");
		jsonStr.append("{\"text\" :\"拟任免信息集\" ,\"id\" :\"leaf_A53\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		//jsonStr.append("{\"text\" :\"考试录用人员\" ,\"id\" :\"leaf_A60\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"选调生信息集\" ,\"id\" :\"leaf_A61\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"遴选方式信息集\" ,\"id\" :\"leaf_A62\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"公开选调人员信息集\" ,\"id\" :\"leaf_A63\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"考试信息集\" ,\"id\" :\"leaf_A64\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		//jsonStr.append("{\"text\" :\"公务员登记信息集\" ,\"id\" :\"leaf_A68\",\"cls\" :\"folder\",\"leaf\":true},");
		//jsonStr.append("{\"text\" :\"职务层次与职级信息集\" ,\"id\" :\"leaf_A69\",\"cls\" :\"folder\",\"leaf\":true}]}]");
		
		
		this.setSelfDefResData(jsonStr.toString());                           //将字符串对象传递给框架里的方法
		return EventRtnType.XML_SUCCESS;
		
	}
	
	
	@PageEvent("InfoSave.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int infoSave(String confirm) throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		
		String a0000 = this.getRadow_parent_data();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			
			A01 a01=new A01();
			this.copyElementsValueToObj(a01, this);
			//判断基本信息中，身份证号和姓名是否输入，不输入不保存
			
			/*a01.setA0000(a0000);
			A01 a01_old=(A01)sess.get(A01.class, a0000);
			PropertyUtils.copyProperties(a01_old, a01);
			sess.saveOrUpdate(a01_old);	*/
			ReturnDO<Boolean> returnDO = this.savePerson(confirm);			//人员基本信息保存
			if(!returnDO.isSuccess()){
				this.setMainMessage(returnDO.getErrorMsg());
				return EventRtnType.FAILD;
			}
			
			this.getExecuteSG().addExecuteCode("document.getElementById('subWinIdBussessId').value = '"+(a0000==null?"":a0000)+"';");
			
			//A01保存需要特殊处理
			
			//进入管理信息集
			A29 a29 = new A29();
			this.copyElementsValueToObj(a29, this);
			a29.setA0000(a0000);
			A29 a29_old = (A29)sess.get(A29.class, a0000);
			if(a29_old==null){
				a29_old = new A29();
			}
			PropertyUtils.copyProperties(a29_old, a29);
			sess.saveOrUpdate(a29_old);	
			//退出管理信息集
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			a30.setA0000(a0000);
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
			}
			PropertyUtils.copyProperties(a30_old, a30);
			sess.saveOrUpdate(a30_old);	
			//住址通信信息集
			A37 a37 = new A37();
			this.copyElementsValueToObj(a37, this);
			a37.setA0000(a0000);
			A37 a37_old = (A37)sess.get(A37.class, a0000);
			if(a37_old==null){
				a37_old = new A37();
			}
			PropertyUtils.copyProperties(a37_old, a37);
			sess.saveOrUpdate(a37_old);	
			
			//拟任免信息集
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			a53.setA0000(a0000);
			A53 a53_old = (A53)sess.get(A53.class, a0000);
			if(a53_old==null){
				a53_old = new A53();
			}
			PropertyUtils.copyProperties(a53_old, a53);
			sess.saveOrUpdate(a53_old);	
			
			//考试录用人员信息集
			A60 a60 = new A60();
			this.copyElementsValueToObj(a60, this);
			a60.setA0000(a0000);
			A60 a60_old = (A60)sess.get(A60.class, a0000);
			if(a60_old==null){
				a60_old = new A60();
			}
			PropertyUtils.copyProperties(a60_old, a60);
			sess.saveOrUpdate(a60_old);	
			
			//选调生信息集
			A61 a61 = new A61();
			this.copyElementsValueToObj(a61, this);
			a61.setA0000(a0000);
			A61 a61_old = (A61)sess.get(A61.class, a0000);
			if(a61_old==null){
				a61_old = new A61();
			}
			PropertyUtils.copyProperties(a61_old, a61);
			sess.saveOrUpdate(a61_old);	
			
			//遴选方式
			A62 a62 = new A62();
			this.copyElementsValueToObj(a62, this);
			a62.setA0000(a0000);
			A62 a62_old = (A62)sess.get(A62.class, a0000);
			if(a62_old==null){
				a62_old = new A62();
			}
			PropertyUtils.copyProperties(a62_old, a62);
			sess.saveOrUpdate(a62_old);	
			
			//公开选调人员信息集
			A63 a63 = new A63();
			this.copyElementsValueToObj(a63, this);
			a63.setA0000(a0000);
			A63 a63_old = (A63)sess.get(A63.class, a0000);
			if(a63_old==null){
				a63_old = new A63();
			}
			PropertyUtils.copyProperties(a63_old, a63);
			sess.saveOrUpdate(a63_old);	
			
			//备注信息集
			A71 a71 = new A71();
			this.copyElementsValueToObj(a71, this);
			a71.setA0000(a0000);
			A71 a71_old = (A71)sess.get(A71.class, a0000);
			if(a71_old==null){
				a71_old = new A71();
			}
			PropertyUtils.copyProperties(a71_old, a71);
			sess.saveOrUpdate(a71_old);	
			
			
			
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
		//this.getExecuteSG().addExecuteCode("radow.doEvent('getTreeJsonData')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 人员基本信息保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("savePerson")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public ReturnDO<Boolean> savePerson(String confirm)throws RadowException, AppException{
		ReturnDO<Boolean> returnDO = new ReturnDO<Boolean>();
		A01 a01 = new A01();
		this.copyElementsValueToObj(a01, this);
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getRadow_parent_data();//获取页面人员内码
		
		a01.setA0000(a0000); 			//set主键
		
		//参加工作时间不能小于出生年月
		String a0134 = a01.getA0134();//参加工作时间
		String a0107 = a01.getA0107();//出生年月
		if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
			if(a0134.length()==6){
				a0134 += "00";
			}
			if(a0107.length()==6){
				a0107 += "00";
			}
			if(a0134.compareTo(a0107)<0){
				returnDO.setErrorMsg("99999", "参加工作时间不能小于出生年月!");
				return returnDO;
			}
		}
		
		
		HBSession sess = null;
		A01 a01_old = null;
		JSONObject jsonbject = null;
		try {
			sess = HBUtil.getHBSession();
			
			String idcard = a01.getA0184();//身份证号 重名校验//将身份证的最后一位x转换为大写字符 add by lizs 20161110
			if(idcard!=null){
				idcard = idcard.toUpperCase();
				a01.setA0184(idcard);
			}
			String sql = "select count(1) from A01 where  a0000!='"+a0000+"' and a0184='"+idcard+"'";//and a0101='"+a01.getA0101()+"'
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
						returnDO.setErrorMsg("99999", "系统中已存在具有相同身份证号码人员,请修改！");
						return returnDO;
			}
			
			a01.setA0102(new ChineseSpelling().getPYString(a01.getA0101()));//拼音简称
			
			if(a0000==null||"".equals(a0000)){
				//职务为空  1现职人员2离退人员3调出人员4已去世5其他人员
				a01.setA0163("1");
				this.getPageElement("a0163").setValue("1");
				sess.save(a01);	
			}else{
				a01_old = (A01)sess.get(A01.class, a0000);
				if("4".equals(a01.getStatus())){//如果是临时数据，保存时状态设为现职人员  日志记为新增
					a01.setStatus("1");
					String sql2 = "select t.a0201b from a02 t where t.a0000 = '"+a0000+"'";
					List<String> list2 = sess.createSQLQuery(sql2).list();
					for(int i=0;i<list2.size();i++){
						CreateSysOrgBS.updateB01UpdatedWithZero(list2.get(i));
					}
					this.getPageElement("status").setValue(a01.getStatus());
					new LogUtil("31", "A01", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A01(), a01)).start();
				}else{
					a01.setXgr(null);
					a01.setXgsj(null);
					a01_old.setXgr(null);
					a01_old.setXgsj(null);
					new LogUtil("32", "A01", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a01_old, a01)).start();
					
				}
				
				
				PropertyUtils.copyProperties(a01_old, a01);
				//简历格式化
				StringBuffer originaljl = new StringBuffer("");
				String jianli = formatJL(a01_old.getA1701(),originaljl);
				jsonbject = objectToJson(a01_old);
				a01_old.setA1701(originaljl.toString());
				this.getPageElement("a1701").setValue(jianli);
				sess.update(a01_old);
			}
			
			//新增修改保存时父页面设置人员内码参数。更新title
			//this.getExecuteSG().addExecuteCode("radow.doEvent('tabClick','"+a01.getA0000()+"');");
			a0000 = a01.getA0000();
			
			
			//家庭成员信息保存，暂时注释
			/*Integer rowLength = Integer.valueOf(this.getPageElement("rowLength").getValue());//行数
			StringBuffer sb = new StringBuffer("{");
			for(int i=1;i<=rowLength;i++){
				A36 a36 = new A36();
				String a3600 = this.getPageElement("a3600_"+i).getValue();
				String a3604a = this.getPageElement("a3604a_"+i).getValue();
				String a3601 = this.getPageElement("a3601_"+i).getValue();
				String a3607 = this.getPageElement("a3607_"+i).getValue();
				String a3627 = this.getPageElement("a3627_"+i).getValue();
				String a3611 = this.getPageElement("a3611_"+i).getValue();
				sb
				.append("a3604a_"+i+":'"+a3604a+"',")
				.append("a3601_"+i+":'"+a3601+"',")
				.append("a3607_"+i+":'"+a3607+"',")
				.append("a3627_"+i+":'"+a3627+"',")
				.append("a3611_"+i+":'"+a3611+"',");
				a36.setA0000(a0000);
				if((a3604a==null||"".equals(a3604a))&&
						(a3601==null||"".equals(a3601))&&
						(a3607==null||"".equals(a3607))&&
						(a3627==null||"".equals(a3627))&&
										(a3611==null||"".equals(a3611))){
					sb.append("a3600_"+i+":'',");
					this.getPageElement("a3600_"+i).setValue("");
					if(a3600==null||"".equals(a3600)){
						
						continue;
					}else{
						a36.setA3600(a3600);
						new LogUtil("3363", "A36", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A36(), new A36())).start();
						sess.delete(a36);
						continue;
					}
					
				}
				a36.setA3604a(a3604a);
				a36.setA3601(a3601);
				a36.setA3607(a3607);
				a36.setA3627(a3627);
				a36.setA3611(a3611);
				a36.setSortid(BigDecimal.valueOf((long)i));
				if(a3600==null||"".equals(a3600)){
					a36.setA3600(null);
					sess.save(a36);
					sess.flush();
					new LogUtil("3361", "A36", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A36(), a36)).start();
					
					this.getPageElement("a3600_"+i).setValue(a36.getA3600());
					
				}else{
					a36.setA3600(a3600);
					A36 a36_old = (A36)sess.get(A36.class, a3600);
					new LogUtil("3362", "A36", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a36_old, a36)).start();
					PropertyUtils.copyProperties(a36_old, a36);
					sess.update(a36_old);
				}
				sb.append("a3600_"+i+":'"+a36.getA3600()+"',");
			}
			for(int i=rowLength+1;i<=30;i++){
				sb.append("a3600_"+i+":'',")
				.append("a3604a_"+i+":'',")
				.append("a3601_"+i+":'',")
				.append("a3607_"+i+":'',")
				.append("a3627_"+i+":'',")
				.append("a3611_"+i+":'',");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("}");*/
			
			sess.flush();			
			//旧值 判断是否修改
			
			String json = jsonbject.toString();
			//this.getExecuteSG().addExecuteCode("A01value="+json+";A36value="+sb+";");   包含了家庭成员信息，暂时注释
			
			this.getExecuteSG().addExecuteCode("A01value="+json);
			//this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			/*this.setMainMessage("任免表信息保存失败！");
			return EventRtnType.FAILD;*/
			returnDO.setErrorMsg("99999", "任免表信息保存失败！");
			return returnDO;
		}
		
		/*this.getPageElement("a0000").setValue(a01_old.getA0000());//保存成功将id返回到页面上。
		this.getPageElement("age").setValue(a01_old.getAge()+"");
		this.setRadow_parent_data(a01_old.getA0000());*/
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0000').value = '"+a01_old.getA0000()+"';");
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('age').value = '"+a01_old.getAge()+"';");
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('radow_parent_data').value = '"+a01_old.getA0000()+"';");
		
		//判断父页面是否是人员信息维护列表页面，是 则重新加载列表
		/*this.getExecuteSG().addExecuteCode("try{" +
				"if(parent.document.getElementById('I'+thisTab.initialConfig.personListTabId)){" +
				"	var personListWindow = parent.document.getElementById('I'+thisTab.initialConfig.personListTabId).contentWindow;" +
				"	personListWindow.Ext.getCmp('persongrid').store.reload();}" +
				"}catch(e){} "
				);
		
		this.getExecuteSG().addExecuteCode(" if(document.getElementById('Iorthers')){"+
					"var orthersWindow = document.getElementById('Iorthers').contentWindow;"+
					"if(orthersWindow){"+
					"	orthersWindow.radow.doEvent('saveOthers.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('系统提示','保存成功!');" +
						"}" +
					"" +
					"}"+
				"}else if(document.getElementById('IBusinessInfo')){"+
					"var BusinessInfoWindow = document.getElementById('IBusinessInfo').contentWindow;"+
					"if(BusinessInfoWindow){"+
					"	BusinessInfoWindow.radow.doEvent('save.onclick','"+confirm+"');"+
					"}else{" +
					"	if('true'=='"+confirm+"'){" +
							"window.parent.tabs.remove(thisTab.tabid);" +
						"}else{" +
							"$h.alert('系统提示','保存成功!');" +
						"}" +
					"" +
					"}"+
				"}else{" +
				"	if('true'=='"+confirm+"'){" +
					"window.parent.tabs.remove(thisTab.tabid);" +
					"}else{" +
						"$h.alert('系统提示','任免表信息保存成功!');" +
					"}" +
				"}");*/
		
		//this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','最后一条数据无法删除！');");
		
		//this.setMainMessage("保存成功！");
		return returnDO;
	}
	
	
	
	
	/** *********************************************工作单位及职务(a02)******************************************************************** */
	@PageEvent("saveWorkUnits.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveWorkUnits()throws RadowException, AppException{
		A02 a02 = new A02();
		this.copyElementsValueToObj(a02, this);
		String a0201bb = this.getPageElement("a0201b").getValue();
		String a0201a = this.getPageElement("a0201b_combo").getValue();//机构名称 中文。
		a02.setA0201a(a0201a);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0255 = this.getPageElement("a0255").getValue();
		if(a0255 == null || "".equals(a0255)){
			this.setMainMessage("该职务任职状态/工作状态 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String aa0201b = this.getPageElement("a0201b").getValue();
		if(aa0201b == null || "".equals(aa0201b)){
			this.setMainMessage("任职机构/工作机构 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		String a0216a = this.getPageElement("a0216a").getValue();
		if(a0216a == null || "".equals(a0216a)){
			this.setMainMessage("职务名称 不能为空！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//1是，2否
		String aa0219 = a02.getA0219();
		if(aa0219 != null && "0".equals(aa0219)){
			a02.setA0219("2");
		}
		//免职时间不能早于任职时间
		String a0265 = a02.getA0265();//免职时间
		String a0243 = a02.getA0243();//任职时间
		if(a0265!=null&&!"".equals(a0265)&&a0243!=null&&!"".equals(a0243)){
			if(a0265.length()==6){
				a0265 += "00";
			}
			if(a0243.length()==6){
				a0243 += "00";
			}
			if(a0265.compareTo(a0243)<0){
				this.setMainMessage("免职时间不能早于任职时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		
		a02.setA0000(a0000);
		String a0200 = this.getPageElement("a0200").getValue();
		Boolean updateJGZW = false;
		HBSession sess = null;
		try {
			
			String a0201b = a02.getA0201b();//任职机构编码。			
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			if(a0201b!=null&&!"".equals(a0201b)&&(a02.getA0225()==null||a02.getA0225()==0)){//根据任职结构编码获取相同机构下职务的排序值。
				String sql="from A02 a where a0000='"+a0000+"' and a0201b='"+a0201b+"'";
				if(a0200!=null&&!"".equals(a0200)){
					sql+=" and a0200!='"+a0200+"'";
				}
				List<A02> list = sess.createQuery(sql).list();
				if(list!=null&&list.size()>0){
					A02 a02Sort = list.get(0);
					Long a0225 = a02Sort.getA0225();
					a02.setA0225(a0225);
				}
			}
			if(a0201bb!=null&&!"".equals(a0201bb)){//获取机构简称
				if("-1".equals(a0201bb)){//其它单位
					a02.setA0201c(a0201a);
				}else{
					B01 b01 = (B01)sess.get(B01.class, a0201bb);
					if(b01!=null){
						String a0201c = b01.getB0104();
						a02.setA0201c(a0201c);
					}
				}
				
				
			}
			
			if(a0200==null||"".equals(a0200)){
				a02.setA0281("true");//是否输出
				this.getPageElement("a0281").setValue("true");
				
				applog.createLog("3021", "A02", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A02(), a02));
				sess.save(a02);	
				updateJGZW = true;
			}else{
				if(a02.getA0281()==null||"".equals(a02.getA0281())){
					a02.setA0281("true");//是否输出
					this.getPageElement("a0281").setValue("true");
				}
				
				A02 a02_old = (A02)sess.get(A02.class, a0200);
				String jg_old = a02_old.getA0201a();//机构
				String jg =  a02.getA0201a();//机构
				if(jg_old!=null&&!jg_old.equals(jg)){
					updateJGZW = true;
				}else if(jg_old==null&&jg!=null){
					updateJGZW = true;
				}
				String zw_old = a02_old.getA0216a();//职务
				String zw =  a02.getA0216a();//职务
				
				if(zw_old!=null&&!zw_old.equals(zw)){
					updateJGZW = true;
				}else if(zw_old==null&&zw!=null){
					updateJGZW = true;
				}
				String rzzt_old = a02_old.getA0255();//任职状态
				String rzzt = a02.getA0255();//任职状态
				if(rzzt_old!=null&&!rzzt_old.equals(rzzt)){
					updateJGZW = true;
				}else if(rzzt_old==null&&rzzt!=null){
					updateJGZW = true;
				}
				applog.createLog("3022", "A02", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a02_old, a02));
				String b0111 = a02_old.getA0201b();
				String b0111s = a02.getA0201b();
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111s);
				CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
				PropertyUtils.copyProperties(a02_old, a02);
				
				sess.update(a02_old);	
			}
			//更新名称
			String a0192a = this.getPageElement("a0192a").getValue();
			String a0192 = this.getPageElement("a0192").getValue();
			String a0197 = this.getPageElement("a0197").getValue();
			a01.setA0192a(a0192a);
			a01.setA0192(a0192);
			a01.setA0197(a0197);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			updateA01(a01,sess);
			sess.update(a01);
			sess.flush();
			
			this.getPageElement("a0200").setValue(a02.getA0200());//保存成功将id返回到页面上。
			//this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//刷新工作单位及职务列表
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery');" +
					"window.parent.radow.doEvent('genResume');");
			
			//修改父页面的统计关系所在单位
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}
			
			if(updateJGZW){
				this.getExecuteSG().addExecuteCode("parent.$h.confirm('系统提示','是否用本职务信息重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
						"if(id=='ok'){" +
							"radow.doEvent('UpdateTitleBtn.onclick');	" +
							"}else if(id=='cancel'){" +
							"	" +
							"}" +
						"});");
			}else{
				this.setMainMessage("保存成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void updateA01(A01 a01, HBSession sess){
		//更新a01 职务层次。 a0148 a0149     a02 a0221
		String sql="select a0221 from A02 a where a0000='"+a01.getA0000()+"' and a0255='1'";
		List<String> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Collections.sort(list, new Comparator<String>(){
				@Override
				public int compare(String o1, String o2) {
					if(o1==null||"".equals(o1)){
						return 1;
					}
					if(o2==null||"".equals(o2)){
						return -1;
					}
					return o1.compareTo(o2);
				}
			});
			
			a01.setA0148(list.get(0));
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='"+(a01.getA0148()==null?"":a01.getA0148())+"';");
		}else{
			//职务为空
			a01.setA0148("");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0148').value='';");
		}
		
	}
	
	
	
	/**
	 * 工作单位及职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.dogridquery")
	@NoRequiredValidate
	public int workUnitsGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from a02 where a0000='"+a0000+"' order by a0255 desc, a0223 ";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 工作单位及职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsAddBtn.onclick")
	@NoRequiredValidate
	public int workUnitsWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A02 a02 = new A02();
		this.getPageElement("a0201b_combo").setValue("");
		
		PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		this.getExecuteSG().addExecuteCode("document.getElementById('a0251b').checked=false;document.getElementById('a0219').checked=false;setA0201eDisabled();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 工作单位及职务修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("WorkUnitsGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int workUnitsGridOnRowClick() throws RadowException{ 
		//获取选中行index
		int index = this.getPageElement("WorkUnitsGrid").getCueRowIndex();
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			String a0219 = a02.getA0219();
			if(a0219 != null && "2".equals(a0219)){
				a02.setA0219("0");
			}
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
			setZB08Code(a02.getA0201b());
			this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
			this.getExecuteSG().addExecuteCode("setA0201eDisabled();");
			//this.getExecuteSG().addExecuteCode("a0222SelChange();a0221achange();a0221change();a0284Change();"); 
			//this.getExecuteSG().addExecuteCode("a0222SelChange();a0221achange();a0221change();"); //改
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出复选框选中事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("workUnitsgridchecked")
	@Transaction
	@NoRequiredValidate
	public int workUnitsgridchecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0200 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		try{
			if(a0200!=null){
				HBSession sess = HBUtil.getHBSession();
				A02 a02 = (A02)sess.get(A02.class, a0200);
				Boolean checked = Boolean.valueOf(a02.getA0281());
				a02.setA0281(String.valueOf(!checked));
				sess.save(a02);
				PMPropertyCopyUtil.copyObjValueToElement(a02, this);
				this.getPageElement("a0201b_combo").setValue(a02.getA0201a());//机构名称 中文。
				//this.getExecuteSG().addExecuteCode("a0222SelChange();");
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('WorkUnitsGrid').getStore().reload()");//刷新列表
		this.getExecuteSG().addExecuteCode("parent.$h.confirm('系统提示','是否重新生成并覆盖现有职务的全称和简称?',350,function(id){" +
				"if(id=='ok'){" +
					"radow.doEvent('UpdateTitleBtn.onclick');	" +
					"}else if(id=='cancel'){" +
					"	" +
					"}" +
				"});");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	/**
	 * 更新名称
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("UpdateTitleBtn.onclick")
	@Transaction
	@NoRequiredValidate
	public int UpdateTitleBtn(String id) throws RadowException {
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		boolean isEvent = false;
		
		String a0000=null;
		try {
			a0000 = this.getPageElement("subWinIdBussessId").getValue();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		if(a0000==null||"".equals(a0000)){
			a0000 = id;
		}else{
			isEvent = true;
		}
		HBSession sess = HBUtil.getHBSession();
		String sql = "from A02 where a0000='"+a0000+"' and a0281='true' order by a0223";//-1 其它单位and a0201b!='-1'
		List<A02> list = sess.createQuery(sql).list();
		if(list!=null&&list.size()>0){
			Map<String,String> desc_full = new LinkedHashMap<String, String>();//描述 全称
			Map<String,String> desc_short = new LinkedHashMap<String, String>();//描述 简称
			
			String zrqm = "";//全名 在任
			String ymqm = "";//以免
			String zrjc = "";//简称
			String ymjc = "";
			for(A02 a02 : list){
				String a0255 = a02.getA0255();//任职状态
				String jgbm = a02.getA0201b();//机构编码
				List<String> jgmcList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};//机构名称 全名
				jgmcList.add(a02.getA0201a()==null?"":a02.getA0201a());
				
				
				List<String> jgmc_shortList = new ArrayList<String>(){
					@Override
					public String toString() {
						StringBuffer sb = new StringBuffer("");
						for(int i=this.size()-1;i>=0;i--){
							sb.append(this.get(i));
						}
						return sb.toString();
					}
				};
				jgmc_shortList.add(a02.getA0201c()==null?"":a02.getA0201c());//机构名称 简称
				String zwmc = a02.getA0216a()==null?"":a02.getA0216a();//职务名称
				B01 b01 = null;
				if(jgbm!=null&&!"".equals(jgbm)){//导入的数据有些为空。 机构编码不为空。
					b01 = (B01)sess.get(B01.class, jgbm);
				}
				if(b01 != null){
					String b0194 = b01.getB0194();//1—法人单位；2—内设机构；3—机构分组。
					if("2".equals(b0194)){//2—内设机构
						while(true){
							b01 = (B01)sess.get(B01.class, b01.getB0121());
							if(b01==null){
								break;
							}else{
								b0194 = b01.getB0194();
								if("2".equals(b0194)){//2—内设机构
									//jgmc = b01.getB0101()+jgmc;
									jgmcList.add(b01.getB0101());
									jgmc_shortList.add(b01.getB0104());
								}else if("3".equals(b0194)){//3—机构分组
									continue;
								}else if("1".equals(b0194)){//1—法人单位
									//jgmc = b01.getB0101()+jgmc;
									//jgmc_short = b01.getB0104()+jgmc_short;
									//全称
									String key_full = b01.getB0111()+"_$_"+b01.getB0101() + "_$_" + a0255;
									String value_full = desc_full.get(key_full);
									if(value_full==null){
										desc_full.put(key_full, jgmcList.toString()+zwmc);
									}else{//相同内设机构下 直接顿号隔开， 内设机构不同，上级（递归） 法人单位机构相同， 如第一次描述包含第二次，第二次顿号隔开，不描述机构；反之则显示 
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmcList.size()-1;i>=0;i--){
											if(value_full.indexOf(jgmcList.get(i))>=0){
												romvelist.add(jgmcList.get(i));
											}
										}
										jgmcList.removeAll(romvelist);
										
										desc_full.put(key_full,value_full + "、" + jgmcList.toString()+zwmc);
										
										
									}
									//简称
									String key_short = b01.getB0111()+"_$_"+b01.getB0104() + "_$_" + a0255;
									String value_short = desc_short.get(key_short);
									if(value_short==null){
										desc_short.put(key_short, jgmc_shortList.toString()+zwmc);
									}else{
										List<String> romvelist = new ArrayList<String>();
										for(int i=jgmc_shortList.size()-1;i>=0;i--){
											if(value_short.indexOf(jgmc_shortList.get(i))>=0){
												romvelist.add(jgmc_shortList.get(i));
											}
										}
										jgmc_shortList.removeAll(romvelist);
										desc_short.put(key_short, value_short + "、" + jgmc_shortList.toString()+zwmc);
									}
									break;
								}else{
									break;
								}
							}
						}
					}else if("1".equals(b0194)){//1—法人单位； 第一次就是法人单位，不往上递归
						String key_full = jgbm + "_$_" + jgmcList.toString() + "_$_" + a0255;
						String value_full = desc_full.get(key_full);
						if(value_full == null){
							desc_full.put(key_full, zwmc);//key 编码_$_机构名称_$_是否已免
						}else{
							desc_full.put(key_full, value_full + "、" + zwmc);
						}
						
						//简称
						String key_short = jgbm + "_$_" + jgmc_shortList.toString() + "_$_" + a0255;
						String value_short = desc_short.get(key_short);
						if(value_short==null){
							desc_short.put(key_short, zwmc);
						}else{
							desc_short.put(key_short, value_short  + "、" +  zwmc);
						}
					}
					
				}
				
			}
			
			for(String key : desc_full.keySet()){//全名
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_full.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrqm += jgzw + "，";
					}
				}else{//以免
					
					if(!"".equals(jgzw)){
						ymqm += jgzw + "，";
					}
				}
			}
			
			
			for(String key : desc_short.keySet()){//简称
				String[] parm = key.split("_\\$_");
				String jgzw = parm[1]+desc_short.get(key);
				if("1".equals(parm[2])){//在任
					//任职机构 职务名称		
					if(!"".equals(jgzw)){
						zrjc += jgzw + "，";
					}
				}else{//以免
					if(!"".equals(jgzw)){
						ymjc += jgzw + "，";
					}
				}
			}
			
			
			
			if(!"".equals(zrqm)){
				zrqm = zrqm.substring(0, zrqm.length()-1);
			}
			if(!"".equals(ymqm)){
				ymqm = ymqm.substring(0, ymqm.length()-1);
				ymqm = "(原"+ymqm+")";
			}
			if(!"".equals(zrjc)){
				zrjc = zrjc.substring(0, zrjc.length()-1);
			}
			if(!"".equals(ymjc)){
				ymjc = ymjc.substring(0, ymjc.length()-1);
				ymjc = "(原"+ymjc+")";
			}
			//this.getPageElement("a0192a").setValue(zrqm+ymqm);
			//this.getPageElement("a0192").setValue(zrjc+ymjc);
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(zrqm+ymqm);
			a01.setA0192(zrjc+ymjc);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
		}else{
			//this.getPageElement("a0192a").setValue("");
			//this.getPageElement("a0192").setValue("");
			A01 a01= (A01)sess.get(A01.class, a0000);
			a01.setA0192a(null);
			a01.setA0192(null);
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192').value='"+a01.getA0192()+"';");
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0192b').value='"+a01.getA0192b()+"';");
			if(isEvent){
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192a').value='';");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0192').value='';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192a').value='"+(a01.getA0192a()==null?"":a01.getA0192a())+"';");
				this.getExecuteSG().addExecuteCode("window.document.getElementById('a0192').value='"+(a01.getA0192()==null?"":a01.getA0192())+"';");
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteRowA02")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA02(String a0200)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0200 = this.getPageElement("WorkUnitsGrid").getValue("a0200",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A02 a02 = (A02)sess.get(A02.class, a0200);
			
			A01 a01 = (A01)sess.get(A01.class, a02.getA0000());
			applog.createLog("3023", "A02", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A02(), new A02()));
			sess.delete(a02);
			sess.flush();
			//更新人员状态
			updateA01(a01, sess);
			this.getExecuteSG().addExecuteCode("radow.doEvent('WorkUnitsGrid.dogridquery')");
			a02 = new A02();
			this.getPageElement("a0201b_combo").setValue("");
			PMPropertyCopyUtil.copyObjValueToElement(a02, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksort")
	@NoRequiredValidate
	@Transaction
	public int upsort(String id)throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("WorkUnitsGrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			int i = 0, j = 0;
			for(HashMap<String,String> m : list){
				String a0200 = m.get("a0200");//a02 id
				String a0255 = m.get("a0255");//任职 状态
				if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
				
				
			}
			
			
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@PageEvent("sortUseTime")
	@NoRequiredValidate
	@Transaction
	public int sortUseTime(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "From A02 where a0000='"+a0000+"' order by a0255 desc, a0243 desc ";//任职状态  任职时间降序
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			
			List<A02> list = sess.createQuery(sql).list();
			int i = 0,j = 0;
			if(list!=null&&list.size()>0){
				for(A02 a02 : list){
					String a0200 = a02.getA0200();//a02 id
					String a0255 = a02.getA0255();//任职 状态
					if("1".equals(a0255)){//在职
						HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
					}else{
						HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
					}
				}
			}
			for(A02 a02 : list){
				String a0200 = a02.getA0200();//a02 id
				String a0255 = a02.getA0255();//任职 状态
				if("1".equals(a0255)){//在职
					HBUtil.executeUpdate("update a02 set a0223="+i+++" where a0200='"+a0200+"'");
				}else{
					HBUtil.executeUpdate("update a02 set a0223="+j+++" where a0200='"+a0200+"'");
				}
			}
			this.setNextEventName("WorkUnitsGrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("setZB08Code")
	@NoRequiredValidate
	@Transaction
	public int setZB08Code(String id)throws RadowException{
		
		
		try {
			String v = HBUtil.getValueFromTab("b0101", "B01", "b0111='"+id+"'");
			if(v!=null){
				this.getPageElement("a0201b_combo").setValue(v);
			}else{
				this.getPageElement("a0201b_combo").setValue("");
			}
			
			
		} catch (AppException e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//检查统计关系所在单位是否为“内设机构”
	@PageEvent("a0195Change")
	public int a0195Change(String a0195) throws RadowException, AppException, UnsupportedEncodingException{
		HBSession sess = HBUtil.getHBSession();
		String sql = "select B0194 from B01 where  B0111 ='"+a0195+"'";
		String B0194 = sess.createSQLQuery(sql).uniqueResult().toString();
		if(B0194 != null && B0194.equals("2")){
			((Combo)this.getPageElement("a0195")).setValue("");
			this.getExecuteSG().addExecuteCode("Ext.Msg.alert('系统提示','不可选择内设机构单位！');document.getElementById('a0195').value='';"
					+ "document.getElementById('a0195key').value = '';document.getElementById('a0195value').value = '';document.getElementById('a0195_combo').value='';"
					);
		}else{
			//修改父页面的统计关系所在单位
			String key = this.getPageElement("a0195key").getValue();
			String value = this.getPageElement("a0195value").getValue();
			if(!("".equals(key) && "".equals(value))){
				this.getExecuteSG().addExecuteCode("parent.setA0195Value('"+key+"','"+value+"');");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/***********************************************专业技术职务(a06)*********************************************************************/
	/**
	 * 保存修改
	 */
	@PageEvent("saveA06.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveProfessSkill()throws RadowException, AppException{		
		A06 a06 = new A06();
		this.copyElementsValueToObj(a06, this);
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		a06.setA0000(a0000);
		String a0600 = this.getPageElement("a0600").getValue();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//资格获得时间早于出生年月
			String a0107 = a01.getA0107();//出生年月
			String a0604 = a06.getA0604();//资格获得时间
			if(a0604!=null&&!"".equals(a0604)&&a0107!=null&&!"".equals(a0107)){
				if(a0604.length()==6){
					a0604 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0604.compareTo(a0107)<0){
					this.setMainMessage("资格获得时间不能小于出生年月");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			String sql = "select max(sortid)+1 from a06";
			List<Object> sortid = sess.createSQLQuery(sql).list();//oracle:BigDecimal,mysql:BigInteger
			if(sortid.get(0)==null){
				a06.setSortid(1l);
			}else{
				a06.setSortid(Long.valueOf(sortid.get(0).toString()));
			}
			if(a0600==null||"".equals(a0600)){
				applog.createLog("3061", "A06", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A06(), a06));
				a06.setA0699("true");
				sess.save(a06);	
			}else{
				A06 a06_old = (A06)sess.get(A06.class, a0600);
				applog.createLog("3062", "A06", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a06_old, a06));
				PropertyUtils.copyProperties(a06_old, a06);
				sess.update(a06_old);	
			}
			sess.flush();
			updateA01(a0000);
			this.setMainMessage("保存成功,点击【确定】关闭当前窗口!");
			this.setMessageType(EventMessageType.CONFIRM);
			addNextBackFunc(NextEventValue.YES, closeCueWindowEX());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);//保存成功将id返回到页面上。
		getPageElement("a0607").setValue(a06.getA0607());
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('professSkillgrid').getStore().reload()");//刷新专业技术职务列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public String closeCueWindowEX(){
		return "window.parent.Ext.WindowMgr.getActive().close();";
	}
	private void updateA01(String a0000 ) throws AppException{
		//当前页面赋值
		List<String> list = HBUtil.getHBSession().createSQLQuery("select a0602 from a06 where a0000='"+a0000+"' "
				+ " and a0699='true' order by sortid").list();//and sortid=(select max(sortid) from a06 where a0000='"+a0000+"')
		//String a0196 = HBUtil.getValueFromTab("a0602", "a06", "a0000='"+a0000+"' and sortid=(select max(sortid) from a06 where a0000='"+a0000+"')");
		//a0196 = a0196==null?"":a0196;
		StringBuffer a0196s = new StringBuffer();
		for(String a0602 : list){
			a0602 = a0602==null?"":a0602;
			a0196s.append(a0602+"，");
		}
		if(a0196s.length()>0){
			a0196s.deleteCharAt(a0196s.length()-1);
		}
		this.getExecuteSG().addExecuteCode("Ext.getCmp('a0196').setValue('"+a0196s+"')");
		//人员基本信息字段更新。
		//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.document.getElementById('a0196').value='"+a0196+"'");
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a0196').value='"+a0196s.toString()+"';window.parent.a0196onblur();");
		
		//更新A10 a0196 专业技术职务字段。   a06 a0602
		HBUtil.executeUpdate("update a01 set a0196='"+a0196s.toString()+"' where a0000='"+a0000+"'");
	}
	/**
	 * 专业技术职务列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.dogridquery")
	@NoRequiredValidate
	public int professSkillgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select a.* from A06 a where a0000='"+a0000+"' order by sortid";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 专业技术职务新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillAddBtn.onclick")
	@NoRequiredValidate
	public int openprofessSkillWin(String id)throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A06 a06 = new A06();
		PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		this.getPageElement("a0196").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 修改专业技术职务的事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("professSkillgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int professSkillOnRowClick() throws RadowException{ 
		//this.openWindow("professSkillAddPage", "pages.publicServantManage.ProfessSkillAddPage");
		//获取选中行index
		int index = this.getPageElement("professSkillgrid").getCueRowIndex();
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
			getPageElement("a0607").setValue(a06.getA0607());
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		//this.setRadow_parent_data("a0600:"+this.getPageElement("professSkillgrid").getValue("a0600",this.getPageElement("professSkillgrid").getCueRowIndex()).toString());
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	@PageEvent("deleteRowA06")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRowA06(String a0600)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a0600 = this.getPageElement("professSkillgrid").getValue("a0600",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A06 a06 = (A06)sess.get(A06.class, a0600);
			A01 a01 = (A01)sess.get(A01.class, a06.getA0000());
			applog.createLog("3063", "A06", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A06(), new A06()));
			sess.delete(a06);
			this.getExecuteSG().addExecuteCode("radow.doEvent('professSkillgrid.dogridquery')");
			sess.flush();
			updateA01(a06.getA0000());
			a06 = new A06();
			PMPropertyCopyUtil.copyObjValueToElement(a06, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 输出复选框选中事件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updateA06")
	@Transaction
	@NoRequiredValidate
	public int updateA06(String a0600) throws RadowException {
		
		try{
			if(a0600!=null){
				HBSession sess = HBUtil.getHBSession();
				A06 a06 = (A06)sess.get(A06.class, a0600);
				Boolean checked = "true".equals(a06.getA0699());
				a06.setA0699(String.valueOf(!checked));
				sess.save(a06);
				sess.flush();
				PMPropertyCopyUtil.copyObjValueToElement(a06, this);
				updateA01( a06.getA0000() );
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("更新失败！");
			return EventRtnType.FAILD;
		}

		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("worksortA06")
	@NoRequiredValidate
	@Transaction
	public int upsortA06()throws RadowException{
		
		List<HashMap<String,String>> list = this.getPageElement("professSkillgrid").getStringValueList();
		//HBSession sess = null;
		try {
			//sess = HBUtil.getHBSession();
			String a0000 = this.getPageElement("subWinIdBussessId").getValue();
			int i = 0;
			StringBuffer a0196s = new StringBuffer("");
			for(HashMap<String,String> m : list){
				String a0600 = m.get("a0600");//a02 id
				HBUtil.executeUpdate("update a06 set sortid="+i+++" where a0600='"+a0600+"'");		
			}
			updateA01(a0000);
			
			this.setNextEventName("professSkillgrid.dogridquery");//工作单位及职务列表		
		} catch (Exception e) {
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		
		//CodeType2js.getCodeTypeJS();
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/***********************************************学位学历(a08)*********************************************************************/
	@PageEvent("saveA08.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveDegrees()throws RadowException, AppException{
		A08 a08 = new A08();
		this.copyElementsValueToObj(a08, this);
		String a0800 = this.getPageElement("a0800").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//学历学位不能同时为空
		String a0801b = a08.getA0801b();//学历
		String a0901b = a08.getA0901b();//学位
		if((a0801b==null||"".equals(a0801b))&&(a0901b==null||"".equals(a0901b))){
			this.setMainMessage("学历学位不能同时为空");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//毕业时间不能早于早于入学时间
		String a0807 = a08.getA0807();//毕业时间
		String a0804 = a08.getA0804();//入学时间
		String a0904 = a08.getA0904();//学位授予时间
		if(a0807!=null&&!"".equals(a0807)&&a0804!=null&&!"".equals(a0804)){
			if(a0807.length()==6){
				a0807 += "00";
			}
			if(a0804.length()==6){
				a0804 += "00";
			}
			if(a0807.compareTo(a0804)<0){
				this.setMainMessage("毕业时间不能早于入学时间");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		a08.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			
			//入学时间、毕业时间、学位授予时间可以早于出生年月并保存。
			String a0107 = a01.getA0107();//出生年月
			if(a0107!=null&&!"".equals(a0107)){
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0807!=null&&!"".equals(a0807)){
					if(a0807.length()==6){
						a0807 += "00";
					}
					if(a0807.compareTo(a0107)<0){
						this.setMainMessage("毕业时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0804!=null&&!"".equals(a0804)){
					if(a0804.length()==6){
						a0804 += "00";
					}
					if(a0804.compareTo(a0107)<0){
						this.setMainMessage("入学时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
				if(a0904!=null&&!"".equals(a0904)){
					if(a0904.length()==6){
						a0904 += "00";
					}
					if(a0904.compareTo(a0107)<0){
						this.setMainMessage("学位授予时间不能小于出生年月");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			
			
			
			if(a0800==null||"".equals(a0800)){
				a08.setA0899("false");//是否输出
				
				
				applog.createLog("3081", "A08", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A08(), a08));
				
				sess.save(a08);	
			}else{
				if(a08.getA0899()==null||"".equals(a08.getA0899())){
					a08.setA0899("false");//是否输出
				}
				
				A08 a08_old = (A08)sess.get(A08.class, a0800);
				applog.createLog("3082", "A08", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a08_old, a08));
				PropertyUtils.copyProperties(a08_old, a08);
				
				sess.update(a08_old);
				
			}	
			sess.flush();	
			updateXueliXuewei(a0000,sess,"1");
			updateXueliXuewei(a0000,sess,"2");
			updateZGXueliXuewei(a0000,sess);//最高学历学位标志
			this.setMainMessage("保存成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a0800").setValue(a08.getA0800());//保存成功将id返回到页面上。
		//this.getExecuteSG().addExecuteCode("Ext.getCmp('degreesgrid').getStore().reload()");//刷新学历学位列表
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	


	private void updateZGXueliXuewei(String a0000, HBSession sess) {
		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0899='true'";//只显示 输出的最高学历 
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		A08 xl = null;
		if(list1!=null&&list1.size()>0){
			for(int i=0;i<list1.size();i++){
				if(i==0){
					list1.get(i).setA0834("1");//
				}else{
					list1.get(i).setA0834("0");
				}
				sess.update(list1.get(i));
			}
			xl = list1.get(0);
		}
		
		
		if(xl!=null){
			if(xl.getA0901a()!=null && !"".equals(xl.getA0901a())){//最高学历，他的学位也最高
				xl.setA0835("1");
				sess.update(xl);
			}else{
				String sql2 = "select * from a08 where a0000='"+a0000+"' and a0899='true' order by to_number(a0901b) asc";//只显示 输出的最高学位 
				List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
				if(list2!=null && list2.size()>0){
					for(int i=0;i<list2.size();i++){
						if(i==0){
							list2.get(i).setA0835("1");
						}else{
							list2.get(i).setA0835("0");
						}
						sess.update(list2.get(i));
					}
				}
			}
		}
		
	}



	@SuppressWarnings("unchecked")
	private void updateXueliXuewei(String a0000, HBSession sess,String a0837) throws AppException {
		//全日制学位学历
		A08 xueli = null;
		A08 xuewei = null;

		String sql1 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true'";  //以学历编码升序排列
		List<A08> list1 = sess.createSQLQuery(sql1).addEntity(A08.class).list();
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		if(list1!=null&&list1.size()>0){
			Collections.sort(list1,new Comparator<A08>(){//学历排序
				@Override
				public int compare(A08 o1, A08 o2) {
					String a0801b_1 = o1.getA0801b();//学历代码
					String a0801b_2 = o2.getA0801b();
					if(a0801b_1==null||"".equals(a0801b_1)){
						return 1;
					}
					if(a0801b_2==null||"".equals(a0801b_2)){
						return -1;
					}
					//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
					return a0801b_1.compareTo(a0801b_2);
				}
				
			});
		}
		if(list1!=null&&list1.size()>0){
			xueli = list1.get(0);
		}
		
		String sql2 = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' and a0899='true' order by to_number(a0901b) asc";   //以学位编码升序排列
		List<A08> list2 = sess.createSQLQuery(sql2).addEntity(A08.class).list();
		if(list2!=null && list2.size()>0){
			xuewei = list2.get(0);
		}
			
		//更新a01
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if(xueli!=null){
			checkXL(xueli,sess,a0837,a0000);
		}else{
			if("1".equals(a0837)){//全日制
				a01.setQrzxl(null);//学历
				a01.setQrzxlxx(null);
				a01.setQrzxw(null);//学位
				a01.setQrzxwxx(null);
				//人员基本信息界面
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
			}else{//在职
				a01.setZzxl(null);
				a01.setZzxlxx(null);
				a01.setZzxw(null);
				a01.setZzxwxx(null);
				//人员基本信息界面
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxw').value='"+(a01.getZzxw()==null?"":a01.getZzxw())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxwxx').value='"+(a01.getZzxwxx()==null?"":a01.getZzxwxx())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
			}
		}
		
	}

	private void checkXL(A08 xueli, HBSession sess,String a0837,String a0000) {//a0837教育类别
		//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
		A01 a01= (A01)sess.get(A01.class, a0000);
		String yx_xl = xueli.getA0814();//院系
		String zy_xl = xueli.getA0824();//专业
		if(yx_xl==null){
			yx_xl = "";
		}
		if(zy_xl==null){
			zy_xl = "";
		}
		if(!"".equals(zy_xl)){
			zy_xl += "专业";
		}
		String xl = xueli.getA0801a();//学历 中文
		String xlxx = yx_xl+zy_xl;
		if(xl==null||"".equals(xl)){
			xlxx = null;
		}
		String a0901a_xl = xueli.getA0901b();
		if(a0901a_xl != null && !"".equals(a0901a_xl)){//最高学历 存在 学位，则此学位一定为最高学位
			checkXW(true,xueli,sess,a0837,a0000);
		}else{//最高学历不 存在 学位
			checkXW(false,xueli,sess,a0837,a0000);
		}
	
		if("1".equals(a0837)){//全日制
			a01.setQrzxl(xl);//学历
			a01.setQrzxlxx(xlxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxl').value='"+(a01.getQrzxl()==null?"":a01.getQrzxl())+"'");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxlxx').value='"+(a01.getQrzxlxx()==null?"":a01.getQrzxlxx())+"'");
		}else{//在职
			a01.setZzxl(xl);
			a01.setZzxlxx(xlxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxl').value='"+(a01.getZzxl()==null?"":a01.getZzxl())+"'");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxlxx').value='"+(a01.getZzxlxx()==null?"":a01.getZzxlxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}

	private void checkXW(boolean flag,A08 xueli, HBSession sess,String a0837,String a0000){
		String xw = "";//学位中文
		String xwxx = "";//学位的院系专业
		String alike = "";//最高学位属于哪一种级别   1-名誉博士 2-博士 3-硕士 4-学士
		if(flag==true){//最高学历 存在 学位，则此学位一定为最高学位
			xw = xueli.getA0901a();
			String yx_xw = xueli.getA0814();//院系
			String zy_xw = xueli.getA0824();//专业
			if(yx_xw==null){
				yx_xw = "";
			}
			if(zy_xw==null){
				zy_xw = "";
			}
			if(!"".equals(zy_xw)){
				zy_xw += "专业";
			}
			xwxx = yx_xw+zy_xw;

			String a0901b = xueli.getA0901b();//学位代码
			List<A08> list = null;
			alike = a0901b.substring(0,1);
			String sql = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' "
					+ " and a0899='true' and substr(a0901b,0,1)='"+alike+"' and a0800<> '"+xueli.getA0800()+"' ";
			list = sess.createSQLQuery(sql).addEntity(A08.class).list();//除最高学位那条外，存在 学位级别一致的记录
			if(list!=null&&list.size()>0){
				Collections.sort(list,new Comparator<A08>(){//学历排序
					@Override
					public int compare(A08 o1, A08 o2) {
						String a0801b_1 = o1.getA0801b();//学历代码
						String a0801b_2 = o2.getA0801b();
						if(a0801b_1==null||"".equals(a0801b_1)){
							return 1;
						}
						if(a0801b_2==null||"".equals(a0801b_2)){
							return -1;
						}
						//return sortmap.get(a0801b_1).compareTo(sortmap.get(a0801b_2));
						return a0801b_1.compareTo(a0801b_2);
					}
					
				});
			}
			if(list!=null && list.size()>0){
				A08 xuewei2 = (A08)list.get(0);
				String xw_2 = xuewei2.getA0901a();
				String xw_yx = xuewei2.getA0814();
				String xw_zy = xuewei2.getA0824();//专业
				if(xw_yx==null){
					xw_yx="";
				}
				if(xw_zy==null){
					xw_zy="";
				}
				if(!"".equals(xw_zy)){
					xw_zy += "专业";
				}
				if(xw_2!=null && !"".equals(xw_2)){
					xw+=","+xw_2;	
				}
				String xwxx_2 = xw_yx+xw_zy;
				xwxx =xwxx_2; 
			}
		}else{//最高学历不 存在 学位
			String sql = "select * from a08 where a0000='"+a0000+"' and a0837='"+a0837+"' "
					+ " and a0899='true' and a0800<> '"+xueli.getA0800()+"' "
					+ " order by to_number(a0901b) asc";  
			List<A08> list = sess.createSQLQuery(sql).addEntity(A08.class).list();//查看最高学位排序
			if(list!=null && list.size()==1){//只有一条记录
				A08 xuewei3 = (A08)list.get(0);
				String xw_yx = xuewei3.getA0814();
				String xw_zy = xuewei3.getA0824();//专业
				String xw_3 = xuewei3.getA0901a();
				if(xw_yx==null){
					xw_yx="";
				}
				if(xw_zy==null){
					xw_zy="";
				}
				if(!"".equals(xw_zy)){
					xw_zy += "专业";
				}
				if(xw_3!=null && !"".equals(xw_3)){
					xw = xw_3;
					String xwxx_3 = xw_yx+xw_zy;
					xwxx =xwxx_3;
				}			 
			}else if(list!=null && list.size()>1){
				A08 xuewei4 = (A08)list.get(0);
				String xw_4_a0901b = xuewei4.getA0901b();
				if(xw_4_a0901b!=null && !"".equals(xw_4_a0901b)){//即存在最高学位
					String xw_yx4 = xuewei4.getA0814();
					String xw_zy4 = xuewei4.getA0824();//专业
					String xw_4 = xuewei4.getA0901a();
					if(xw_yx4==null){
						xw_yx4="";
					}
					if(xw_zy4==null){
						xw_zy4="";
					}
					if(!"".equals(xw_zy4)){
						xw_zy4 += "专业";
					}
					if(xw_4!=null && !"".equals(xw_4)){
						xw = xw_4;
						String xwxx_3 = xw_yx4+xw_zy4;
						xwxx =xwxx_3;
					}
					//String xwxx_2 = xw_yx+xw_zy;
					//xwxx +=xwxx_2;
					alike = xw_4_a0901b.substring(0,1);
					
					A08 xw_5 = (A08)list.get(0);
					String xw_5_a0901b = xw_5.getA0901b();
					if(xw_5_a0901b!=null && !"".equals(xw_5_a0901b) && alike.equals(xw_5_a0901b.substring(0,1))){//即存在最高学位
						/*String xw_yx5 = xw_5.getA0814();
						String xw_zy5 = xw_5.getA0824();//专业
						String xwxx_5 = xw_yx5+xw_zy5+"专业";
						xwxx +=xwxx_5;*/
						xw+=","+xw_5.getA0901a();
					}				
				}	
			}
		}
		A01 a01 = (A01) sess.get(A01.class, a0000);
		if("1".equals(a0837)){//全日制
			a01.setQrzxw(xw);//学位
			a01.setQrzxwxx(xwxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxw').value='"+(a01.getQrzxw()==null?"":a01.getQrzxw())+"'");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('qrzxwxx').value='"+(a01.getQrzxwxx()==null?"":a01.getQrzxwxx())+"'");
		}else{//在职
			a01.setZzxw(xw);
			a01.setZzxwxx(xwxx);
			//人员基本信息界面
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxw').value='"+(a01.getZzxw()==null?"":a01.getZzxw())+"'");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('zzxwxx').value='"+(a01.getZzxwxx()==null?"":a01.getZzxwxx())+"'");
		}
		sess.update(a01);
		sess.flush();
	}


	public String reverse(String s) {
		char[] array = s.toCharArray();
		String reverse = "";
		for (int i = array.length - 1; i >= 0; i--)
			reverse += array[i];

		return reverse;
	}
	
	
	/**
	 * 学位学历列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.dogridquery")
	@NoRequiredValidate
	public int degreesgridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		String sql = "select * from A08 where a0000='"+a0000+"' order by a0837";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 学位学历新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesAddBtn.onclick")
	@NoRequiredValidate
	public int opendegreesWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		
		String a0800 = this.getPageElement("a0800").getValue();
		String a0837 = this.getPageElement("a0837").getValue();//教育类别
		String a0801b = this.getPageElement("a0801b").getValue();//学历
		String a0901b = this.getPageElement("a0901b").getValue();//学位
		if(a0800==null||"".equals(a0800)){//新增提示是否保存当前信息。
			if(a0837!=null&&!"".equals(a0837)){
				if((a0801b!=null&&!"".equals(a0801b))||(a0901b!=null&&!"".equals(a0901b))){
					this.getExecuteSG().addExecuteCode("$h.confirm3btn('系统提示','是否保存当前新增信息?',200,function(id){" +
							"if(id=='yes'){" +
								"radow.doEvent('save.onclick');	" +
								"}else if(id=='no'){" +
								"	radow.doEvent('clearCondition');" +
								"}else if(id=='cancel'){" +
								"	" +
								"}" +
							"});");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			
		}
		
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		clearCondition();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空 条件列表
	 * @throws RadowException 
	 */
	@PageEvent("clearCondition")
	public void clearCondition() throws RadowException{
		A08 a08 = new A08();
		PMPropertyCopyUtil.copyObjValueToElement(a08, this);
	}
	
	/**
	 * 修改学位学历修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int degreesgridOnRowClick() throws RadowException{ 
		//this.openWindow("DegreesAddPage", "pages.publicServantManage.DegreesAddPage");
		//获取选中行index
		int index = this.getPageElement("degreesgrid").getCueRowIndex();
		String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	/**
	 * 输出设置	
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("degreesgridchecked")
	@Transaction
	@NoRequiredValidate
	public int degreesgridChecked() throws RadowException {
		Map map = this.getRequestParamer();
		String a0800 = map.get("eventParameter")==null?null:String.valueOf(map.get("eventParameter"));
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
//		String a0000 = this.getRadow_parent_data();
		try{
			if(a0800!=null){
				HBSession sess = HBUtil.getHBSession();
				A08 a08 = (A08)sess.get(A08.class, a0800);
				Boolean checked = Boolean.valueOf(a08.getA0899());
				a08.setA0899(String.valueOf(!checked));
				sess.save(a08);
				sess.flush();
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				updateXueliXuewei(a0000, sess, "1");//全日制
				updateXueliXuewei(a0000, sess, "2");//在职
				PMPropertyCopyUtil.copyObjValueToElement(a08, this);
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		
		this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");//刷新列表
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	@PageEvent("deleteRowA08")
	@Transaction
	@Synchronous(true)
	public int deleteRowA08(String a0800)throws RadowException, AppException{
		Map map = this.getRequestParamer();
		//int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		//String a0800 = this.getPageElement("degreesgrid").getValue("a0800",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A08 a08 = (A08)sess.get(A08.class, a0800);
			
			A01 a01 = (A01)sess.get(A01.class, a08.getA0000());
			applog.createLog("3083", "A08", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A08(), new A08()));
			sess.delete(a08);
			sess.flush();
			Boolean checked = Boolean.valueOf(a08.getA0899());				
			//checked 如果为true 数据是被输出的。  修改A01 否则不做更改
			if(checked){
				//更新a01 学历学位信息:全日制：qrzxl学历 qrzxw学位 qrzxlxx院校系及专业。在职 zzxl zzxw zzxlxx
				updateXueliXuewei(a08.getA0000(), sess, "1");//全日制
				updateXueliXuewei(a08.getA0000(), sess, "2");//在职
			}
			this.getExecuteSG().addExecuteCode("radow.doEvent('degreesgrid.dogridquery')");
			a08 = new A08();
			PMPropertyCopyUtil.copyObjValueToElement(a08, this);
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/***********************************************奖惩情况(a14)*********************************************************************/
	@PageEvent("saveA14.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveRewardPunish()throws RadowException, AppException{
		A14 a14 = new A14();
		this.copyElementsValueToObj(a14, this);
		String a1400 = this.getPageElement("a1400").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//撤销日期不能早于批准日期
		String a1424 = a14.getA1424();//撤销日期
		String a1407 = a14.getA1407();//批准日期
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
		
		
		a14.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			if(a1400==null||"".equals(a1400)){
				
				applog.createLog("3141", "A14", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A14(), a14));
				
				sess.save(a14);	
			}else{
				
				A14 a14_old = (A14)sess.get(A14.class, a1400);
				applog.createLog("3142", "A14", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a14_old, a14));
				PropertyUtils.copyProperties(a14_old, a14);
				sess.update(a14_old);	
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
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			sess.update(a01);
			sess.flush();			
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
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select a.* from A14 a where a0000='"+a0000+"' order by a1407";
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
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
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
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
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
			desc.append("经"+a1411a+"批准，").append(a1404a+"。");
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
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
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
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
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
				if(desc.toString().endsWith("。")){
					desc.deleteCharAt(desc.length()-1).append("；");
				}
				if(!"".equals(a1407)){
					desc.append(a1407);
				}
				if(a1404b.startsWith("01")){//奖
					desc.append("经"+a1411a+"批准，").append(a1404a+"。");
				}else{//惩
					
					desc.append("经"+a1411a+"批准，").append("受"+a1404a+"处分。");
				}
			}
			
			this.getPageElement("a14z101").setValue(desc.toString());
			a01.setA14z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		}else{
			String a14z101 = this.getPageElement("a14z101").getValue();
			if("".equals(a14z101)){
				a01.setA14z101("无");
				this.getPageElement("a14z101").setValue(a01.getA14z101());
				sess.update(a01);
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("deleteRowA14")
	@Transaction
	@Synchronous(true)
	public int deleteRowA14(String a1400)throws RadowException, AppException{
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
			
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void change_visiable(A14 a14) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
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
		this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a14z101').value='"+a01.getA14z101()+"'");
		
	}
	
	
	
	/***********************************************年度考核情况A15*********************************************************************/
	@PageEvent("saveA15.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveAssessmentInfo()throws RadowException, AppException{
		String a1521s = this.getPageElement("a1521").getValue();
		String a1517 = this.getPageElement("a1517").getValue();
		String [] a1521sNum = a1521s.split(",");
		if(a1521sNum.length>1){
			
			return saveAllInfo();
		}else{
			A15 a15 = new A15();
			this.copyElementsValueToObj(a15, this);
			//String a0000 = this.getRadow_parent_data();
			String a0000 = this.getPageElement("subWinIdBussessId").getValue();
			if(a0000==null||"".equals(a0000)){
				this.setMainMessage("请先保存人员基本信息！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			a15.setA0000(a0000);
			String a1500 = this.getPageElement("a1500").getValue();
			HBSession sess = null;
			String isconnect = "";
			try {
				sess = HBUtil.getHBSession();
				A01 a01 = (A01)sess.get(A01.class, a0000);
				//检查该年度是否已录入
				StringBuffer sql = new StringBuffer("from A15 where a0000='"+a15.getA0000()+"' and a1521='"+a15.getA1521()+"'");			
				if(a1500!=null&&!"".equals(a1500)){
					sql.append(" and a1500!='"+a1500+"'");
				}
				List list = sess.createQuery(sql.toString()).list();
				if(list!=null&&list.size()>0){
					this.setMainMessage("该年填写重复!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				if(a15.getA1521()!=null&&!"".equals(a15.getA1521())&&a15.getA1517()!=null&&!"".equals(a15.getA1517())){
					// 新增或修改
					if (a1500 == null || "".equals(a1500)) {
						
						applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15));
						
						sess.save(a15);
					} else {
						A15 a15_old = (A15)sess.get(A15.class, a1500);
						applog.createLog("3152", "A15", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a15_old, a15));
						PropertyUtils.copyProperties(a15_old, a15);
						
						sess.update(a15_old);
					}	
				}
				sess.flush();
				//更新a01年度考核情况
				String a15z101 = this.getPageElement("a15z101").getValue();
				a01.setA15z101(a15z101);
				
				//是否与列表关联
				isconnect = this.getPageElement("a0191").getValue();
				a01.setA0191(isconnect);
				if("1".equals(isconnect)){
					listAssociation();
				}
				//人员基本信息界面
				//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
				this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
				sess.update(a01);
				sess.flush();			
				this.setMainMessage("保存成功！");
			} catch (Exception e) {
				e.printStackTrace();
				this.setMainMessage("保存失败！");
				return EventRtnType.FAILD;
			}
			
			this.getPageElement("a1500").setValue(a15.getA1500());//保存成功将id返回到页面上。
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	/**
	 * 批量保存
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveAll.onclick")
	@Transaction
	@Synchronous(true)
	public int saveAllInfo()throws RadowException, AppException{
		String a1500 = this.getPageElement("a1500").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1521s = this.getPageElement("a1521").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String a1517 = this.getPageElement("a1517").getValue();//考核结果
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			//检查该年度是否已录入
			StringBuffer sql = new StringBuffer("from A15 where a0000='"+a0000+"'");
			List<A15> list = sess.createQuery(sql.toString()).list();
			//已录年度
			StringBuffer years = new StringBuffer("");
			if(list!=null&&list.size()>0){
				for(A15 a15y : list){
					years.append(a15y.getA1521()+",");
				}
			}
			String [] num = a1521s.split(",");
			//循环批量新增考核信息
			for(int i=0;i<num.length;i++){
				if(years.indexOf(num[i])!=-1||a1517==null||"".equals(a1517)){
					continue;
				}			
				A15 a15save = new A15();
				a15save.setA1521(num[i]);
				a15save.setA1517(a1517);
				a15save.setA0000(a0000);
				
				applog.createLog("3151", "A15", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(new A15(), a15save));
				
				sess.save(a15save);
			}
			sess.flush();
			//更新a01年度考核情况
			String a15z101 = this.getPageElement("a15z101").getValue();
			a01.setA15z101(a15z101);
			
			//是否与列表关联
			String isconnect = this.getPageElement("a0191").getValue();
			a01.setA0191(isconnect);
			if("1".equals(isconnect)){
				listAssociation();
			}
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
			sess.update(a01);
			sess.flush();			
			
			sess.flush();			
			//this.setMainMessage("增加成功！");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("增加失败！");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1500").setValue("");//清空页面id。
		this.getExecuteSG().addExecuteCode("Ext.getCmp('AssessmentInfoGrid').getStore().reload()");//刷新列表
		/*A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);*/
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * 年度考核情况列表
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.dogridquery")
	@NoRequiredValidate
	public int assessmentInfoGridQuery(int start,int limit) throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = "select * from A15 where a0000='"+a0000+"' order by to_number(a1521) asc";
		this.pageQuery(sql,"SQL", start, limit); //处理分页查询
	    return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 年度考核情况新增按钮
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoAddBtn.onclick")
	@NoRequiredValidate
	public int assessmentInfoAddBtnWin(String id)throws RadowException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		A15 a15 = new A15();
		a15.setA1527(this.getPageElement("a1527").getValue());
		PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 年度考核情况的修改事件
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("AssessmentInfoGrid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int assessmentInfoGridOnRowDbClick() throws RadowException{  
		int index = this.getPageElement("AssessmentInfoGrid").getCueRowIndex();
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();
		
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			return EventRtnType.FAILD;
		}			
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	
	/**
	 * 考核情况描述
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("listAssociation.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int listAssociation()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//获取页面人员内码
		//String a0000 = this.getRadow_parent_data();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String a1527 = this.getPageElement("a1527").getValue();//选择年度个数
		if(a1527==null||"".equals(a1527)){
			return EventRtnType.NORMAL_SUCCESS;	
		}
		HBUtil.executeUpdate("update a15 set a1527='"+a1527+"' where a0000='"+a0000+"'");
		
		HBSession sess = HBUtil.getHBSession();
		A01 a01= (A01)sess.get(A01.class, a0000);
		String sql = "from A15 where a0000='"+a0000+"' order by to_number(a1521) asc";
		List<A15> list = HBUtil.getHBSession().createQuery(sql.toString()).list();
		//List<HashMap<String, Object>> list = this.getPageElement("AssessmentInfoGrid").getValueList();
		if(list!=null&&list.size()>0){
			int years = "".equals(a1527)?list.size():Integer.valueOf(a1527);
			if(years>list.size()){
				years = list.size();
			}
			StringBuffer desc = new StringBuffer("");
			for(int i=list.size()-years;i<list.size();i++){
				A15 a15 = list.get(i);
				//考核年度
				String a1521 = a15.getA1521();
				//考核结果
				String a1517 = a15.getA1517();
				String a1517Name = HBUtil.getCodeName("ZB18",a1517);
				desc.append(a1521+"年年度考核"+a1517Name+"；");
			}
			if(desc.length()>0){
				desc.replace(desc.length()-1, desc.length(), "。");
			}
			this.getPageElement("a15z101").setValue(desc.toString());
			
			a01.setA15z101(desc.toString());
			sess.update(a01);
			//人员基本信息界面
			//this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('tab_baseInfo').contentWindow.Ext.getCmp('a15z101').setValue('"+a01.getA15z101()+"')");
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}else{
			String description = "无";
			this.getPageElement("a15z101").setValue(description);
			a01.setA15z101(description);
			sess.update(a01);
			this.getExecuteSG().addExecuteCode("window.parent.document.getElementById('a15z101').value='"+a01.getA15z101()+"'");
		}
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	@PageEvent("deleteRowA15")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int deleteRow(String a1500)throws RadowException, AppException{
		/*Map map = this.getRequestParamer();
		int index = map.get("eventParameter")==null?null:Integer.valueOf(String.valueOf(map.get("eventParameter")));
		String a1500 = this.getPageElement("AssessmentInfoGrid").getValue("a1500",index).toString();*/
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A15 a15 = (A15)sess.get(A15.class, a1500);
			A01 a01= (A01)sess.get(A01.class, a15.getA0000());
			String a1527 = a15.getA1527();
			
			applog.createLog("3153", "A15", a01.getA0000(), a01.getA0101(), "删除记录", new Map2Temp().getLogInfo(new A15(), new A15()));
			
			sess.delete(a15);
			this.getExecuteSG().addExecuteCode("radow.doEvent('AssessmentInfoGrid.dogridquery')");
			a15 = new A15();
			a15.setA1527(a1527);
			PMPropertyCopyUtil.copyObjValueToElement(a15, this);
			listAssociation();
		} catch (Exception e) {
			this.setMainMessage("删除失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
	
	
	//处理简历
	public static String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null&&!"".equals(a1701)){
			String[] jianli = a1701.split("\r\n|\r|\n");
			StringBuffer jlsb = new StringBuffer("");
			for(int i=0;i<jianli.length;i++){
				String jl = jianli[i].trim();
				//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
				Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |．][0-9| ]{2}[\\-|─]{1,2}[0-9| ]{4}[\\.| |．][0-9| ]{2}");     
		        Matcher matcher = pattern.matcher(jl);     
		        if (matcher.find()) {
		        	String line1 = matcher.group(0);  
		        	int index = jl.indexOf(line1);
		        	if(index==0){//以日期开头  (一段)
		        		jlsb.append(line1).append("  ");
		        		String line2 = jl.substring(line1.length()).trim();
			        	parseJL(line2, jlsb,true);
			        	originaljl.append(jl).append("\r\n");
		        	}else{
		        		parseJL(jl, jlsb,false);
		        		if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
			        		originaljl.delete(originaljl.length()-2, originaljl.length());
			        	}
		        		originaljl.append(jl).append("\r\n");
		        	}
		        }else{
		        	parseJL(jl, jlsb,false);
		        	if(originaljl.lastIndexOf("\r\n")==originaljl.length()-2 && i!=jianli.length-1){
		        		originaljl.delete(originaljl.length()-2, originaljl.length());
		        	}
		        	originaljl.append(jl).append("\r\n");
		        }
			}
			
			return jlsb.toString();
			
		}
		return a1701;
	}
	
	//处理简历
	private static void parseJL(String line2, StringBuffer jlsb, boolean isStart){
		int llength = line2.length();//总长
		//32个字一行。
		int oneline = 22;
    	if(llength>oneline){
    		int j = 0;
    		int end = 0;
    		int offset = 0;//不足 64个字节往后偏移，直到足够为止。
    		boolean hass = false;
    		while((end+offset)<llength){//32个字一行，换行符分割
    			end = oneline*(j+1);
    			if(end+offset>llength){
    				end = llength-offset;
    			}
    			String l = line2.substring(oneline*j+offset,end+offset);
    			int loffset=0;
    			while(l.getBytes().length<oneline<<1){//32个字一行但不足64个字节 往右移
    				loffset++;
    				if((end+offset+loffset)>llength){//超过总长度 退出循环
    					loffset--;
    					break;
    				}
    				l = line2.substring(oneline*j+offset,end+offset+loffset);
    				if(l.getBytes().length>oneline<<1){//可能会出现一行65个字节，往前退一格。
    					loffset--;
    					l = line2.substring(oneline*j+offset,end+offset+loffset);
    					break;
    				}
    			}
    			offset = offset+loffset;
    			if(isStart&&!hass){
    				jlsb.append(l).append("\r\n");
    				hass = true;
    			}else{
    				jlsb.append("                  ").append(l).append("\r\n");
    			}
    			
    			j++;
    		}
    	}else{
    		if(isStart){
    			jlsb.append(line2).append("\r\n");
    		}else{
    			jlsb.append("                  ").append(line2).append("\r\n");
    		}
    	}
	}
	
	 /**
     * 将实体POJO转化为JSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> JSONObject objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return new JSONObject(jsonStr);
    }
	
}
