package com.insigma.siis.local.pagemodel.sysorg.org;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.YearCheckFile;
import com.insigma.siis.local.business.helperUtil.ReturnDO;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class UpdateSysOrgPageModel extends PageModel {
	/**
	 * 系统区域信息
	 */
	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String tag="0";//0未执行1执行
	public static String b0101stauts="0";//0未改变1改变
	public static String b0104stauts="0";//0未改变1改变
	public static String b0194Type="0";//单位类型1单位 2内设机构 3分组
	@Override
	public int doInit() throws RadowException {
//		String unitidDbclAlter=(String)request.getSession().getAttribute("unitidDbclAlter");
//		if(unitidDbclAlter==null||unitidDbclAlter.length()==0){
//			unitidDbclAlter="";
//		}else{
//			request.getSession().setAttribute("unitidDbclAlter", "");//属性字节过大 清空 此属性
//		}
//		this.getPageElement("unitidDbclAlter").setValue(unitidDbclAlter);
		//执行页面初始化
		
		this.getExecuteSG().addExecuteCode("initpage('1')");	
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	public int init(){
		this.getExecuteSG().addExecuteCode("consoleSaveBtn();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 上一个按钮不可编辑
	 * @return
	 */
	@PageEvent("nextsno")
	public int nextsno(){
		 this.createPageElement("nexts", ElementType.BUTTON, false).setDisabled(true);
		 return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 上一个按钮可编辑
	 * @return
	 */
	@PageEvent("nextsyes")
	public int nextsyes(){
		 this.createPageElement("nexts", ElementType.BUTTON, false).setDisabled(false);
		 return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 下一个按钮不可编辑
	 * @return
	 */
	@PageEvent("nextxno")
	public int nextxno(){
		 this.createPageElement("nextx", ElementType.BUTTON, false).setDisabled(true);
		 return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 下一个按钮可编辑
	 * @return
	 */
	@PageEvent("nextxyes")
	public int nextxyes(){
		 this.createPageElement("nextx", ElementType.BUTTON, false).setDisabled(false);
		 return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 下一个或上一个机构初始化
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("nextUnit")
	public int nextUnit(String groupID) throws RadowException, AppException{
		//将属性值传递到页面
		B01 b01 = (B01) HBUtil.getHBSession().get(B01.class, groupID);
		HBSession sess = HBUtil.getHBSession();
		
		String orifileid = b01.getB0242();
		String orifileid2 = b01.getB0243();
		String orifileid3 = b01.getB0244();
		
		if(orifileid==null||"".equals(orifileid)){
			String uuid = UUID.randomUUID().toString();
			orifileid=uuid;
		}
		if(orifileid2==null||"".equals(orifileid2)){
			String uuid = UUID.randomUUID().toString();
			orifileid2=uuid;
		}
		if(orifileid3==null||"".equals(orifileid3)){
			String uuid = UUID.randomUUID().toString();
			orifileid3=uuid;
		}
		
		this.getPageElement("orifileid").setValue(orifileid);
		this.getPageElement("orifileid2").setValue(orifileid2);
		this.getPageElement("orifileid3").setValue(orifileid3);
		
		String sqlF = "from YearCheckFile where id='"+b01.getB0242()+"'";	
		List<YearCheckFile> list = sess.createQuery(sqlF).list();
		if(list.size() != 0){
			
			String html = "";
			
			for (YearCheckFile yearcheckfile : list) {
				
				//html += "<a href='javascript:downloadNoticeFile(id)'>"+yearcheckfile.getFilename()+"</a>";
				//javascript:download
				//html += "<a href=\"javascript:void(0)\" onclick=\"download("+yearcheckfile.getId()+")\">"+yearcheckfile.getFileName()+"</a>";
				html += "<a href=\"javascript:download('"+yearcheckfile.getId()+"')\"><span style='font-size:12px'> "+yearcheckfile.getFileName()+"</span></a>";
			}			
			this.getPageElement("htmlx").setValue(html);
			//this.getExecuteSG().addExecuteCode("document.getElementById('fileList').innerHTML='"+html+"';");
			//this.getExecuteSG().addExecuteCode("init()");    
		}
		
		String sqlG = "from YearCheckFile where id='"+b01.getB0243()+"'";	
		List<YearCheckFile> list2 = sess.createQuery(sqlG).list();
		if(list2.size() != 0){
			
			String html = "";
			
			for (YearCheckFile yearcheckfile : list2) {
				
				//html += "<a href='javascript:downloadNoticeFile(id)'>"+yearcheckfile.getFilename()+"</a>";
				
				html += "<a href=\"javascript:download('"+yearcheckfile.getId()+"')\"><span style='font-size:12px'>  "+yearcheckfile.getFileName()+"</span></a>";
			}
			this.getPageElement("htmly").setValue(html);
			//this.getExecuteSG().addExecuteCode("document.getElementById('fileList').innerHTML='"+html+"';");
			//this.getExecuteSG().addExecuteCode("init()");    
		}
		
		String sqlH = "from YearCheckFile where id='"+b01.getB0244()+"'";	
		List<YearCheckFile> list3 = sess.createQuery(sqlH).list();
		if(list3.size() != 0){
			
			String html = "";
			
			for (YearCheckFile yearcheckfile : list3) {
				
				//html += "<a href='javascript:downloadNoticeFile(id)'>"+yearcheckfile.getFilename()+"</a>";
				
				html += "<a href=\"javascript:download('"+yearcheckfile.getId()+"')\"><span style='font-size:12px'>  "+yearcheckfile.getFileName()+"</span></a>";
			}
			this.getPageElement("htmlz").setValue(html);
			//this.getExecuteSG().addExecuteCode("document.getElementById('fileList').innerHTML='"+html+"';");
			
		}
		this.getExecuteSG().addExecuteCode("init()");    
		
		
		PMPropertyCopyUtil.copyObjValueToElement(b01,this);
		//获取上级机构名称与上级机构编码
		B01 b01up = (B01) HBUtil.getHBSession().get(B01.class, b01.getB0121());
		if(b01up!=null){
			this.getPageElement("optionGroup").setValue(b01up.getB0101());//上级机构名称
			this.getPageElement("parentb0114").setValue(b01up.getB0114());//上级机构编码
		}
		//设置机构id和机构名称是否改变
		this.getExecuteSG().addExecuteCode("document.getElementById('b0101old').value='"+b01.getB0101()+"'");
		this.getExecuteSG().addExecuteCode("document.getElementById('b0104old').value='"+b01.getB0104()+"'");
		//设置该机构属性
		String b0194Type = b01.getB0194();
		if(b0194Type!= null){
			if(b0194Type.trim().equals("1")){//法人
				//正职
				this.getPageElement("b0183_1").setValue("0");
				//副职
				this.getPageElement("b0185_1").setValue("0");
				this.getExecuteSG().addExecuteCode("$('#b0194a').attr('checked','checked');");
				this.getExecuteSG().addExecuteCode("Check1();");
			}else if(b0194Type.trim().equals("2")){//内设
				//正职
				this.getPageElement("b0183_1").setValue((b01.getB0183()==null?0:b01.getB0183())+"");
				//副职
				this.getPageElement("b0185_1").setValue((b01.getB0185()==null?0:b01.getB0185())+"");
				this.getExecuteSG().addExecuteCode("$('#b0194b').attr('checked','checked');");
				this.getExecuteSG().addExecuteCode("Check2();");
			}else{//分组
				//正职
				this.getPageElement("b0183_1").setValue("0");
				//副职
				this.getPageElement("b0185_1").setValue("0");
				this.getExecuteSG().addExecuteCode("$('#b0194c').attr('checked','checked');");
				this.getExecuteSG().addExecuteCode("Check3();");
			}
		}
		
		
		String b0234 = b01.getB0234();
		if(b0234==null || "".equals(b0234.trim())) {
			this.getPageElement("b0234").setValue("");
		}else {
			this.getPageElement("b0234").setValue(b0234.trim());
		}
		
		String b0235 = b01.getB0235();
		if(b0235==null || "".equals(b0235.trim())) {
			this.getPageElement("b0235").setValue("");
		}else {
			this.getPageElement("b0235").setValue(b0235.trim());
		}
		
//		String b0236 = b01.getB0236();
//		if(b0236==null || "".equals(b0236.trim())) {
//			this.getPageElement("b0236").setValue("");
//		}else {
//			this.getPageElement("b0236").setValue(b0236.trim());
//		}
		
		//显示备注信息
		String b0180 = b01.getB0180();
		if(b0180==null || "".equals(b0180.trim())) {
			this.getPageElement("b0180").setValue("");
		}else {
			this.getPageElement("b0180").setValue(b0180.trim());
		}
		
		try {
		//String sql1 = "select count(a0121) xz from a01 where a0121='1'  and a0163='1' and torgid = '"+groupID+"'";
		//String sql2 = "select count(a0121) cg from a01 where a0121='2' and a0163='1' and torgid = '"+groupID+"'";
		//String sql3 = "select count(a0121) fcg from a01 where a0121='3' and a0163='1' and torgid = '"+groupID+"'";
		//String sql4 = "select count(a0121) gq from a01 where a0121='4' and a0163='1' and torgid = '"+groupID+"'";
		//String sql5 = "select count(a0121) qt from a01 where a0121='9' and a0163='1' and torgid = '"+groupID+"'";
		
		CommQuery query = new CommQuery();
		//List<HashMap<String, Object>> li1 = query.getListBySQL(sql1);
		//List<HashMap<String, Object>> li2 = query.getListBySQL(sql2);
		//List<HashMap<String, Object>> li3 = query.getListBySQL(sql3);
		//List<HashMap<String, Object>> li4 = query.getListBySQL(sql4);
		//List<HashMap<String, Object>> li5 = query.getListBySQL(sql5);

		//HashMap<String,Object> map = new HashMap<String,Object>();
		//HashMap<String,Object> map2 = new HashMap<String,Object>();
		HashMap<String,Object> map3 = new HashMap<String,Object>();
		HashMap<String,Object> map4 = new HashMap<String,Object>();
		HashMap<String,Object> map5 = new HashMap<String,Object>();
		
		String sql6 = "select count(*) ZZ from (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and a01.a0163='1' and a01.status='1'  "
				+ "and a02.a0201e='1' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+groupID+"') tt ";
		Object o6 = sess.createSQLQuery(sql6).uniqueResult();
		int zz = Integer.valueOf(o6.toString());
		String sql7 = "select count(*) FZ from (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and a01.a0163='1' and a01.status='1'  "
				+ "and a02.a0201e = '3' and (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') and a02.a0201b='"+groupID+"') tt ";
		Object o7 = sess.createSQLQuery(sql7).uniqueResult();
		int fz = Integer.valueOf(o7.toString());
		
		
		
		String sql8 = "select count(*) ZZ from (SELECT A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and a01.a0163='1' and a01.status='1'  "
				+ "and a02.a0201e='31' and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0')  and a02.a0201b='"+groupID+"') tt ";
		Object o8 = sess.createSQLQuery(sql8).uniqueResult();
		String zs = o8.toString();
		this.getPageElement("b0256").setValue(zs);
		String sql9 = "select count(*) ZZ from (SELECT ROW_NUMBER() "
				+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and a01.a0163='1' and a01.status='1' "
				+ "and a02.a0201e='32'  and a02.a0219='1'  and a02.a0201b='"+groupID+"') tt "
				+ "where tt.rn=1";
		Object o9 = sess.createSQLQuery(sql9).uniqueResult();
		String jj = o9.toString();
		this.getPageElement("b0257").setValue(jj);
		String sql10 = "select count(*) ZZ from (SELECT ROW_NUMBER() "
				+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and a01.a0163='1' and a01.status='1' "
				+ "and a02.a0201e='33'  and a02.a0219='1'  and a02.a0201b='"+groupID+"') tt "
				+ "where tt.rn=1";
		Object o10 = sess.createSQLQuery(sql10).uniqueResult();
		String zzb = o10.toString();
		this.getPageElement("b0258").setValue(zzb);
		String sql11 = "select count(*) ZZ from (SELECT ROW_NUMBER() "
				+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'  and a01.a0163='1' and a01.status='1' "
				+ "and a02.a0201e='34'  and a02.a0219='1'  and a02.a0201b='"+groupID+"') tt "
				+ "where tt.rn=1";
		Object o11 = sess.createSQLQuery(sql11).uniqueResult();
		String qtjz = o11.toString();
		this.getPageElement("b0259").setValue(qtjz);
		/*String sql12 = "select count(*) ZZ from (SELECT ROW_NUMBER() "
				+ "OVER(PARTITION BY a01.a0000 ORDER BY a02.a0223 DESC) rn, A01.A0000 FROM a02, a01 "
				+ "WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' "
				+ "and a02.a0201e='34'  and a02.a0219='1'  and a02.a0201b='"+groupID+"') tt "
				+ "where tt.rn=1";*/
		//Object o12 = sess.createSQLQuery(sql12).uniqueResult();
		//String jz = o12.toString();
		//this.getPageElement("b0260").setValue(jz);
		
		this.getPageElement("yp0183_1").setValue(zz+"");
		this.getPageElement("yp0185_1").setValue(fz+"");
		
		this.getPageElement("y0150").setValue(zz+fz+"");
		this.getPageElement("y0183").setValue(zz+"");
		this.getPageElement("y0185").setValue(fz+"");
		
		/*if(li1.size()!=0){
			map = li1.get(0);
			if(map.get("xz")!=null){
				String xz = map.get("xz").toString();
				this.getPageElement("y0227_1").setValue(xz);
			}else{
				this.getPageElement("y0227_1").setValue("0");
			}
		}else{
			this.getPageElement("y0227_1").setValue("0");
		}*/
		/*if(li2.size()!=0){
			map2 = li2.get(0);
			if(map2.get("cg")!=null){
				String cg = map2.get("cg").toString();
				this.getPageElement("y0232_1").setValue(cg);
			}else{
				this.getPageElement("y0232_1").setValue("0");
			}
		}else{
			this.getPageElement("y0232_1").setValue("0");
		}*/
		/*if(li3.size()!=0){
			map3 = li3.get(0);
			if(map3.get("fcg")!=null){
				String fcg = map3.get("fcg").toString();
				this.getPageElement("y0233_1").setValue(fcg);
			}else{
				this.getPageElement("y0233_1").setValue("0");
			}
		}else{
			this.getPageElement("y0233_1").setValue("0");
		}*/
		/*if(li4.size()!=0){
			map4 = li4.get(0);
			if(map4.get("gq")!=null){
				String gq = map4.get("gq").toString();
				this.getPageElement("y0236_1").setValue(gq);
			}else{
				this.getPageElement("y0236_1").setValue("0");
			}
		}else{
			this.getPageElement("y0236_1").setValue("0");
		}*/
		/*if(li5.size()!=0){
			map5 = li5.get(0);
			if(map5.get("qt")!=null){
				String qt = map5.get("qt").toString();
				this.getPageElement("y0234_1").setValue(qt);
			}else{
				this.getPageElement("y0234_1").setValue("0");
			}
		}else{
			this.getPageElement("y0234_1").setValue("0");
		}*/
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//修改按钮
	@PageEvent("save.onclick")
	public int updateWinBtn() throws RadowException {
		String b0111 =this.getPageElement("b0111").getValue();//机构编码
		if(b0111.equals("")||null==b0111){
			this.setMainMessage("请选择机构");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!SysRuleBS.havaRule(b0111)){
			throw new RadowException("您无此权限!");
		}
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//效验
	@PageEvent("validation.result")
	public int validation() throws RadowException{
		String b0114 =this.getPageElement("b0114").getValue();
		String b0111 =this.getPageElement("b0111").getValue();//机构编码
		if(b0111==null){
			this.setMainMessage("数据异常！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String radio = this.getPageElement("b0194").getValue();
		String b0101 = this.getPageElement("b0101").getValue();
		String b0104 = this.getPageElement("b0104").getValue();
		String b0194Type=this.getPageElement("b0194").getValue();
		if(b0114==null || b0114.trim().equals("")){
			this.setMainMessage("请输入机构编码！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//122.a23.aaa.11a ......
		if(!java.util.regex.Pattern.matches("^[0-9a-zA-Z]{3}(\\.{1}[0-9a-zA-Z]{3}){0,}$",b0114)){
			this.setMainMessage("机构编码不合法,请重新输入！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(b0101==null || b0101.trim().equals("")){
			this.setMainMessage("请输入机构名称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(b0104==null || b0104.trim().equals("")){
//			this.setMainMessage("请输入机构简称！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(radio == null || radio.trim().equals("")){
			this.setMainMessage("请选择机构类型！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		B01 b01 =new B01();
		PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
		ReturnDO<String> returnDO = CreateSysOrgBS.groupValidate(b01);//机构类型校验
		if(!returnDO.isSuccess()){
			this.setMainMessage(returnDO.getErrorMsg());
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(b01.getB0101().equals("")){
			this.setMainMessage("请输入机构名称！");
			return EventRtnType.NORMAL_SUCCESS;
		}
//		if(b01.getB0104().equals("")){
//			this.setMainMessage("请输入简称！");
//			return EventRtnType.NORMAL_SUCCESS;
//		}
		if(radio.equals("LegalEntity")){//法人单位
			if("".equals(b01.getB0117())){//所在政区
				this.setMainMessage("请选择所在政区！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0124())){//隶属关系
				this.setMainMessage("请选择隶属关系！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0131())){//机构类别
				this.setMainMessage("请选择机构类别！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if("".equals(b01.getB0127())){//机构级别
				this.setMainMessage("请选择机构级别！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else if(radio.equals("InnerOrg")){//内设机构
			String b0183_1=this.getPageElement("b0183_1").getValue();
			String b0185_1=this.getPageElement("b0185_1").getValue();
			if(b0185_1==null||b0185_1.length()==0){
				b0185_1="0";
			}
			if(b0183_1==null||b0183_1.length()==0){
				b0183_1="0";
			}
			if(b01.getB0150()==null){
				b01.setB0150(0l);
			}
			if(b01.getB0150()<(Long.parseLong(b0185_1)+Long.parseLong(b0183_1))){
				this.setMainMessage("领导职数应该大于等于正职与副职之和!");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}else if(radio.equals("GroupOrg")){//机构分组
		}else{
			this.setMainMessage("数据异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}
	
		this.setNextEventName("sysorg.save");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("sysorg.save")
	@OpLog
	@Transaction
	public int sysorgsave() throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		
		HBSession sess = HBUtil.getHBSession();
		String b0194Type="";
		String radio = this.getPageElement("b0194").getValue();
		if(radio==null){
			this.setMainMessage("数据异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(radio.equals("LegalEntity")){//法人单位
			b0194Type="1";
		}else if(radio.equals("InnerOrg")){//内设机构
			b0194Type="2";
		}else if(radio.equals("GroupOrg")){//机构分组
			b0194Type="3";
		}else{
			this.setMainMessage("数据异常!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			sess.beginTransaction();
		
			String b0101 = this.getPageElement("b0101").getValue().trim();
			String b0104 = this.getPageElement("b0104").getValue().trim();
			String b0111 = this.getPageElement("b0111").getValue().trim();
			//如果不包含自己重名提示不允许重名
			B01 b01 = CreateSysOrgBS.LoadB01(b0111);
			B01DTO b01dto = new B01DTO();
			CreateSysOrgBS.selectListByNameForUpdate(b0101,b0111);
			PropertyUtils.copyProperties(b01dto, b01);
			
			PMPropertyCopyUtil.copyElementsValueToObj(b01dto, this);
			
			/*String orifileid = this.getPageElement("orifileid").getValue();
			String orifileid2 = this.getPageElement("orifileid2").getValue();
			String orifileid3 = this.getPageElement("orifileid3").getValue();*/
						
			String orifileid = b01dto.getB0242();
			String orifileid2 = b01dto.getB0243();
			String orifileid3 = b01dto.getB0244();
			
			if(orifileid==null||"".equals(orifileid)){
				String uuid = UUID.randomUUID().toString();
				b01dto.setB0242(uuid);
				orifileid=uuid;
			}
			if(orifileid2==null||"".equals(orifileid2)){
				String uuid = UUID.randomUUID().toString();
				b01dto.setB0243(uuid);
				orifileid2=uuid;
			}if(orifileid3==null||"".equals(orifileid3)){
				String uuid = UUID.randomUUID().toString();
				b01dto.setB0244(uuid);
				orifileid3=uuid;
			}
			
			this.getPageElement("orifileid").setValue(orifileid);
			this.getPageElement("orifileid2").setValue(orifileid2);
			this.getPageElement("orifileid3").setValue(orifileid3);
			
			
			String b0101old = this.getPageElement("b0101old").getValue();
			String b0104old = this.getPageElement("b0104old").getValue();
			String isstauts = this.getPageElement("isstauts").getValue();
			if(b0101old.equals(b0101)){
				this.b0101stauts="0";
			}else{
				this.b0101stauts="1";
			}
			if(b0104old.equals(b0104)){
				this.b0104stauts="0";
			}else{
				this.b0104stauts="1";
			}
			if(this.b0101stauts.equals("0")&&this.b0104stauts.equals("0")||isstauts.equals("1")||b0194Type.equals("3")){
				if(radio.equals("InnerOrg")){//内设机构
					String b0183_1=this.getPageElement("b0183_1").getValue();
					String b0185_1=this.getPageElement("b0185_1").getValue();
					if(b0183_1!=null&&b0183_1.length()!=0){
						b01dto.setB0183(Long.parseLong(b0183_1));//正职领导职数
					}
					if(b0185_1!=null&&b0185_1.length()!=0){
						b01dto.setB0185(Long.parseLong(b0185_1));//副职领导职数
					}
				}
			//	CreateSysOrgBS.UpdateB01(b01dto,b0194Type);
				
				
				
				
				CreateSysOrgBS.saveOrUpdateB01(b01dto,b0194Type);
				this.getExecuteSG().addExecuteCode("scfj1()");    //上传附件   历史沿革
				
				saveInfo_Extend(sess,b01.getB0111());
				sess.flush();
				this.getPageElement("isstauts").setValue("0");
				//this.getExecuteSG().addExecuteCode("window.reloadTree()");
				this.getPageElement("b0101old").setValue(b01.getB0101());
				this.getPageElement("b0104old").setValue(b01.getB0104());
				//B01 b01_tem = (B01)sess.get(B01.class, b0111);
				//ReturnDO<B01> returnDO = CreateSysOrgBS.alertB01(b01_tem);
				//sess.saveOrUpdate(returnDO.getObj());
				//sess.flush();
				sess.connection().commit();
				this.getExecuteSG().addExecuteCode("window.realParent.queryGroupByUpdate('"+b0111+"');");
				//this.getExecuteSG().addExecuteCode("parent.queryGroupByUpdate('"+b0111+"');");
				this.setMainMessage("修改成功");
			}else{
				if(radio.equals("InnerOrg")){//内设机构
					String b0183_1=this.getPageElement("b0183_1").getValue();
					String b0185_1=this.getPageElement("b0185_1").getValue();
					if(b0183_1!=null&&b0183_1.length()!=0){
						b01dto.setB0183(Long.parseLong(b0183_1));//正职领导职数
					}
					if(b0185_1!=null&&b0185_1.length()!=0){
						b01dto.setB0185(Long.parseLong(b0185_1));//副职领导职数
					}
				}
			//	CreateSysOrgBS.UpdateB01(b01dto,b0194Type);
				CreateSysOrgBS.saveOrUpdateB01(b01dto,b0194Type);
				this.getExecuteSG().addExecuteCode("scfj1()");    //上传附件   历史沿革
				saveInfo_Extend(sess,b01.getB0111());
				sess.flush();
				this.getPageElement("isstauts").setValue("0");
				//this.getExecuteSG().addExecuteCode("window.reloadTree()");
				this.getPageElement("b0101old").setValue(b01.getB0101());
				this.getPageElement("b0104old").setValue(b01.getB0104());
				//B01 b01_tem = (B01)sess.get(B01.class, b0111);
				//ReturnDO<B01> returnDO = CreateSysOrgBS.alertB01(b01_tem);
				//sess.saveOrUpdate(returnDO.getObj());
				//sess.flush();
				sess.connection().commit();
				//sess.connection().rollback();
				this.openWindow("updateNameWin", "pages.sysorg.org.SysOrgUpdateName");
				String parent_data= b01.getB0111()+","+b0101stauts+","+b0104stauts+","+b01dto.getB0101()+","+b01dto.getB0104();
//				CommonQueryBS.systemOut("============"+parent_data);
				this.setRadow_parent_data(parent_data);
			}

		} catch (Exception e) {
			sess.connection().rollback();
			e.printStackTrace();
			throw new RadowException(e.toString());
		}		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	private void saveInfo_Extend(HBSession sess, String b0111) throws Exception {

		List<Object[]> list = CreateSysOrgBS.getB01ExtSQL();
		Map<String,String> field = new LinkedHashMap<String, String>();
		if(list!=null&&list.size()>0){
			for(Object[] os : list){
				field.put(os[1].toString(), os[2].toString());
			}
		}
		sess.flush();
		
		List<Object> Info_Extends = sess.createSQLQuery("select b0111 from b01_ext where b0111='"+b0111+"'").list();
		
		if(field.size()>0){//有扩展字段
			if(Info_Extends==null||Info_Extends.size()==0){//新增
				StringBuffer insert_sql = new StringBuffer("insert into b01_ext(b0111,");
				StringBuffer values = new StringBuffer(" values('"+b0111+"',");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						insert_sql.append(key+",");
						values.append("?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add "+key+" varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add "+key+" varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext."+key+" is '"+comment+"'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							} catch (Exception e1) {
							}
						}
					}catch (Exception e) {
					}
				}
				insert_sql.deleteCharAt(insert_sql.length()-1).append(")");
				values.deleteCharAt(values.length()-1).append(")");
				insert_sql.append(values);
				HBUtil.executeUpdate(insert_sql.toString(), args);
			}else{//修改
				StringBuffer update_sql = new StringBuffer("update b01_ext set ");
				Object[] args = new Object[field.size()];
				Integer index = 0;
				for(String key : field.keySet()){
					String comment = field.get(key);
					try{
						update_sql.append(key+"=?,");
						args[index++] = this.getPageElement(key.toLowerCase()).getValue();
						HBUtil.executeUpdate("alter table b01_ext add "+key+" varchar(200)");
						HBUtil.executeUpdate("alter table b01_ext_temp add "+key+" varchar(200)");
						try {
							HBUtil.executeUpdate("comment on column b01_ext."+key+" is '"+comment+"'");
						} catch (Exception e) {
							try {
								HBUtil.executeUpdate("ALTER TABLE b01_ext MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
								HBUtil.executeUpdate("ALTER TABLE b01_ext_temp MODIFY COLUMN "+key+" VARCHAR(200) COMMENT '"+comment+"'");//mysql
							} catch (Exception e1) {
							}
						}
						
					}catch (Exception e) {
					}
				}
				update_sql.deleteCharAt(update_sql.length()-1).append(" where b0111='"+b0111+"'");
				HBUtil.executeUpdate(update_sql.toString(), args);
			}
			
		}
	}
	@PageEvent("closeBtn.onclick")
	public int close() throws AppException, RadowException {
		//this.closeCueWindowByYes("addOrgWin");
//		this.closeCueWindow("updateOrgWin");
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('updateOrgWin').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
