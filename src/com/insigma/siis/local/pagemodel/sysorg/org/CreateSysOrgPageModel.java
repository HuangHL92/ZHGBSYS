package com.insigma.siis.local.pagemodel.sysorg.org;


import java.beans.IntrospectionException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.OpLog;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.pagemodel.sysorg.org.util.YearCheckServlet;

public class CreateSysOrgPageModel extends PageModel {
	
	/**
	 * 系统区域信息
	 */

	public Hashtable<String,Object> areaInfo = new Hashtable<String ,Object>();
	public static String LegalEntityTypeBtn ="0";//按钮1继续增加 2保存 3确定 4取消
	public static String b0194Type="0";//单位类型1单位 2内设机构 3分组
	public static String opType="0";//1新增2修改
	public static String tag= "0";//修改动作0未执行1已执行
	public static String b0101stauts="0";//0未改变1改变
	public static String b0104stauts="0";//0未改变1改变
	public CreateSysOrgPageModel() throws RadowException{
	}
	
	//页面初始化
	@Override
	public int doInit() throws RadowException {
		//this.getExecuteSG().addExecuteCode("getParentUnitId();");
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		
		String ids = this.getRadow_parent_data();
		String parentid = "1,";
		if(ids ==null || ids.trim().equals("")) {
			parentid=parentid+"-1";
		}else {
			parentid=parentid+ids.trim();
		}
	
		String parentids[] = parentid.split(",");
		
		B01 b01 =CreateSysOrgBS.LoadB01(parentids[1]);
		if(parentids[0].equals("1")){
			this.opType="1";//1新增2修改
			this.b0194Type =b01.getB0194();//机构类型 1法人单位 2内设机构 3机构分组
			this.getPageElement("optionGroup").setValue(b01.getB0101());//上级机构名称
			this.getPageElement("parentb0114").setValue(b01.getB0114());//上级机构编码
			this.getPageElement("b0121").setValue(parentids[1]);//上级单位编码
			this.getExecuteSG().addExecuteCode("myfunction("+b0194Type+");");

			if("1".equals(b0194Type)){
				this.getExecuteSG().addExecuteCode("Check1();");
				this.getExecuteSG().addExecuteCode("$('#b0194a').attr('checked',true);");
			}else if("2".equals(b0194Type)){
				this.getExecuteSG().addExecuteCode("Check2();");
				this.getExecuteSG().addExecuteCode("$('#b0194b').attr('checked',true);");
			}else{
				this.getExecuteSG().addExecuteCode("Check1();");
				this.getExecuteSG().addExecuteCode("$('#b0194a').attr('checked',true);");
			}
		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 继续增加
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntityContinueAddBtn.onclick")
	public int LegalEntityContinueAdd() throws AppException, RadowException {
		String radio = this.getPageElement("b0194").getValue();
		if(radio.equals("LegalEntity")){//法人
			this.b0194Type="1";
		}else if(radio.equals("InnerOrg")){//内设
			this.b0194Type="2";
		}else{//分组
			this.b0194Type="3";
		}
		this.LegalEntityTypeBtn="1";//按钮1继续增加 2保存 3确定 4取消
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 保存
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntitySaveBtn.onclick")
	public int LegalEntitySave() throws AppException, RadowException {
		String radio = this.getPageElement("b0194").getValue();
		if(radio.equals("LegalEntity")){//法人单位
			this.b0194Type="1";
		}else if(radio.equals("InnerOrg")){//内设机构
			this.b0194Type="2";
		}else{//机构分组
			this.b0194Type="3";
		}
		this.LegalEntityTypeBtn="2";//按钮1继续增加 2保存 3确定 4取消
		this.setNextEventName("validation.result");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 关闭
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("LegalEntityCancelBtn.onclick")
	public int LegalEntityCancel() throws AppException, RadowException {
		this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
		this.closeCueWindow("addOrgWin");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * 开始保存
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@PageEvent("sysorg.save")
	public int sysorgsave() throws AppException, RadowException, SQLException, IntrospectionException, IllegalAccessException, InvocationTargetException {
		HBSession sess = HBUtil.getHBSession();
		sess.beginTransaction();
		//页面可以多次保存,
		String b0111 = this.getPageElement("b0111").getValue().trim();//机构id
		//CurrentUser user = SysUtil.getCacheCurrentUser();
		String b0101 = this.getPageElement("b0101").getValue().trim();//机构名称
		String b0104 = this.getPageElement("b0104").getValue().trim();//简称
		String parentid = this.getRadow_parent_data();
		String parentids[] = parentid.split(",");
	
		String b0111new = "";
		try {
			/* 1继续增加 2保存 3确定 4取消*/
			if(!b0111.equals("")){//第二次保存存在机构id
				this.opType="2";//2修改
			}
			if(!b0111.equals("")&&LegalEntityTypeBtn.equals("1")){//点击保存之后，在再点击继续增加
				this.opType="1";//1新增
				this.setNextEventName("reset.onclick");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(opType.equals("1")){//1新增
				B01DTO b01 = new B01DTO();
				PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
				//this.getExecuteSG().addExecuteCode("scfj1()");    //上传附件
				
				String historyfile = UUID.randomUUID().toString();
				this.getPageElement("orifileid").setValue(historyfile);
				
				String schemefile = UUID.randomUUID().toString();
				this.getPageElement("orifileid2").setValue(schemefile);
				
				String leaderfile = UUID.randomUUID().toString();
				this.getPageElement("orifileid3").setValue(leaderfile);
				
				
				
				//String historyfile = this.getPageElement("orifileid").getValue();
				b01.setB0242(historyfile);
				b01.setB0243(schemefile);
				b01.setB0244(leaderfile);
				String radio = this.getPageElement("b0194").getValue();
				if("InnerOrg".equals(radio)){//内设机构
					/*法人单位与机构分组的正职与副职为相同字段*/
					String b0183_1=this.getPageElement("b0183_1").getValue();//正职
					String b0185_1=this.getPageElement("b0185_1").getValue();//副职
					
					if(b0183_1!=null&&b0183_1.length()!=0){
						b01.setB0183(Long.parseLong(b0183_1));//正职领导职数
					}
					if(b0185_1!=null&&b0185_1.length()!=0){
						b01.setB0185(Long.parseLong(b0185_1));//副职领导职数
					}
				}
				CreateSysOrgBS.selectSubListByName(b01.getB0101(),b01.getB0121());//校验同一机构下，不能存在同名机构
				CreateSysOrgBS.saveOrUpdateB01(b01,b0194Type);
				
				this.getExecuteSG().addExecuteCode("scfj1()");    //上传附件   历史沿革
				//this.getExecuteSG().addExecuteCode("scfj2()");    //三定方案
				//this.getExecuteSG().addExecuteCode("scfj3()");    //历任领导
			
				
				saveInfo_Extend(sess,b01.getB0111());
				SysRuleBS.saveUserDept(b01.getB0111());//获取机构的上级机构的所有机构权限
				if(LegalEntityTypeBtn.equals("2")){//保存
					this.getPageElement("b0101old").setValue(b01.getB0101());
					this.getPageElement("b0104old").setValue(b01.getB0104());
					this.getPageElement("b0111").setValue(b01.getB0111());
				}
				this.request.getSession().setAttribute("tag", "1");
				b0111new = b01.getB0111();
				
			}else if(opType.equals("2")){//2修改
				String b0194Type="";
				String radio = this.getPageElement("b0194").getValue();
				B01DTO b01dto = new B01DTO();
				
				try {
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
					//如果不包含自己重名提示不允许重名
					B01 b01 = CreateSysOrgBS.LoadB01(b0111);
					
					CreateSysOrgBS.selectListByNameForUpdate(b0101,b0111);////校验同一机构下，不能存在同名机构
					PropertyUtils.copyProperties(b01dto, b01);
					PMPropertyCopyUtil.copyElementsValueToObj(b01dto, this);
					if(radio.equals("InnerOrg")){//内设机构
						/*法人单位与机构分组的正职与副职为相同字段*/
						String b0183_1=this.getPageElement("b0183_1").getValue();//正职
						String b0185_1=this.getPageElement("b0185_1").getValue();//副职
						
						if(b0183_1!=null&&b0183_1.length()!=0){
							b01dto.setB0183(Long.parseLong(b0183_1));//正职领导职数
						}
						if(b0185_1!=null&&b0185_1.length()!=0){
							b01dto.setB0185(Long.parseLong(b0185_1));//副职领导职数
						}
					}
					String b0101old = this.getPageElement("b0101old").getValue();
					String b0104old = this.getPageElement("b0104old").getValue();
					String isstauts = this.getPageElement("isstauts").getValue();
					if(LegalEntityTypeBtn.equals("2")){//保存
						this.getPageElement("b0101old").setValue(b01.getB0101());
						this.getPageElement("b0104old").setValue(b01.getB0104());
						this.getPageElement("b0111").setValue(b01.getB0111());
					}
					if(b0101old.equals(b0101)){//机构名称，为改变
						this.b0101stauts="0";
					}else{
						this.b0101stauts="1";
					}
					if(b0104old.equals(b0104)){//机构编码为改变
						this.b0104stauts="0";
					}else{
						this.b0104stauts="1";
					}
					if(this.b0101stauts.equals("0")&&this.b0104stauts.equals("0")||isstauts.equals("1")||b0194Type.equals("3")){
						CreateSysOrgBS.saveOrUpdateB01(b01dto,b0194Type);
						saveInfo_Extend(sess,b01.getB0111());
						this.getPageElement("isstauts").setValue("0");
						this.getPageElement("b0101old").setValue(b01.getB0101());
						this.getPageElement("b0104old").setValue(b01.getB0104());
					}else{
						sess.connection().rollback();
						this.openWindow("updateNameWin", "pages.sysorg.org.SysOrgUpdateName");
						String parent_data= b01.getB0111()+","+b0101stauts+","+b0104stauts+","+b01dto.getB0101()+","+b01dto.getB0104();
						this.setRadow_parent_data(parent_data);
						return EventRtnType.NORMAL_SUCCESS;
					}

				} catch (Exception e) {
					try{
						if(sess!=null){
							sess.connection().rollback();
						}
					}catch(Exception e1){
						e1.printStackTrace();
					}
					throw new RadowException(e.toString());
				}		

			}else{
				this.setMainMessage("数据异常!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(LegalEntityTypeBtn.equals("1")){
				this.setNextEventName("reset.onclick");
				this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			}else if(LegalEntityTypeBtn.equals("2")){
				this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
			}else{
				//导入新建机构返回数据
				if(parentids.length==3 && parentids[2].equals("imp_create")){
					this.closeCueWindowByYes("addOrgImpWin");
					this.createPageElement("b0111new", ElementType.HIDDEN, true).setValue(b0111new);
					this.createPageElement("info", ElementType.TEXTAREA, true).setValue("新建匹配机构信息"
							+"\n匹配机构：" + b0101 +"\n机构编码：" + this.getPageElement("b0114").getValue());
					this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
				} else {
					this.getExecuteSG().addExecuteCode("window.parent.reloadTree()");
					this.closeCueWindowByYes("addOrgWin");
					this.closeCueWindowByYes("updateOrgWin");
				}
			}
			sess.connection().commit();
			//刷新父页面列表
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('orgInfoGrid.dogridquery')");
			this.setMainMessage("保存成功");
		} catch (Exception e) {
			try{
				if(sess!=null){
					sess.connection().rollback();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
			throw new RadowException(e.toString());
		}		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//重置按钮
	@PageEvent("reset.onclick")
	@NoRequiredValidate  
	@Transaction
	@OpLog
	public int resetonclick()throws RadowException, AppException {
		//this.getPageElement("b0111").setValue("");//机构主键
		this.getPageElement("b0114").setValue("");//法人单位编码(机构分组编码)
		this.getPageElement("b0101").setValue("");//机构名称
		this.getPageElement("b0104").setValue("");//机构简称
		this.getPageElement("b0117").setValue("");//机构所在政区 ZB01
		this.getPageElement("b0124").setValue("");//单位隶属关系 ZB87
		this.getPageElement("b0131").setValue("");//机构性质类别 ZB04 
		this.getPageElement("b0127").setValue("");//机构级别 ZB03   等
		
		this.getPageElement("b0183").setValue("0");//正职领导职数
		this.getPageElement("b0185").setValue("0");//副职领导职数
		//this.getPageElement("b0227").setValue("0");//行政编制数   等
		//this.getPageElement("b0232").setValue("0");//事业编制数(参公)
		//this.getPageElement("b0233").setValue("0");//事业编制数(其他)
		//this.getPageElement("b0236").setValue("0");//工勤编制数
		//this.getPageElement("b0234").setValue("0");//其他编制数
		
		this.getPageElement("b0240").setValue("");//编制配置
		this.getPageElement("b0241").setValue("");//实际配置
		//this.getPageElement("b0242").setValue("");//历史沿革
		//this.getPageElement("b0243").setValue("");//三定方案 
		//this.getPageElement("b0244").setValue("");//历任领导
		this.getPageElement("b0246").setValue("0");
		this.getPageElement("b0256").setValue("0");
		this.getPageElement("b0247").setValue("0");
		this.getPageElement("b0257").setValue("0");
		this.getPageElement("b0248").setValue("0");
		this.getPageElement("b0258").setValue("0");
		this.getPageElement("b0249").setValue("0");
		this.getPageElement("b0259").setValue("0");
		//this.getPageElement("b0250").setValue("0");
		//this.getPageElement("b0260").setValue("0");
		
		this.getPageElement("b0150").setValue("0");//内设领导职数
		this.getPageElement("b0183_1").setValue("0");//正职领导职数
		this.getPageElement("b0185_1").setValue("0");//副职领导职数
		//bo238自定义标签，存在2个值
		this.getPageElement("b0238").setValue("");//参照公务员法管理审批时间
		this.getPageElement("b0238_1").setValue("");//参照公务员法管理审批时间
		this.getPageElement("b0239").setValue("");//参照公务员法管理审批文号
		this.getPageElement("b0180").setValue("");//备注
		
		/*隐藏字段*/
		this.getPageElement("b0101old").setValue("");
		this.getPageElement("b0104old").setValue("");
		this.getPageElement("b0111").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//效验
	@PageEvent("validation.result")
	public int validation() throws RadowException{
		String b0111 =this.getPageElement("b0111").getValue();
		String b0121 =this.getPageElement("b0121").getValue();//上级单位编码
		String b0114 =this.getPageElement("b0114").getValue();//法人单位（机构分组）编码
		if(b0194Type.equals("3")){
			//通过B0111查是否有人员，如果有人员提示转移
			String str = CreateSysOrgBS.validationGroup(b0111);
			if(!"0".equals(str)){
				this.setMainMessage("机构分组下不允许有人员，请先转移人员！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		if(b0114.trim().equals("")){
			this.setMainMessage("请输入机构编码！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//122.a23.aaa.11a ......
		if(!java.util.regex.Pattern.matches("^[0-9a-zA-Z]{3}(\\.{1}[0-9a-zA-Z]{3}){0,}$",b0114)){
			this.setMainMessage("机构编码不合法,请重新输入！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(!b0194Type.equals("2")){
		}else{
			List list = HBUtil.getHBSession().createSQLQuery("select t.b0194 from B01 t where t.b0121 = '"+b0111+"'").list();
			for(int i=0;i<list.size();i++){
				String b0194 = (String) list.get(i);
				if(b0194.equals("1")||b0194.equals("3")){
					this.setMainMessage("内设机构下不允许新建法人单位或机构分组！");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
		}
		if(!b0121.equals("-1")){
			B01 b01 = CreateSysOrgBS.LoadB01(this.getPageElement("b0121").getValue());
			if(b01!=null){
				String b0194 = b01.getB0194();//单位标识
				if(b0194.equals("2")){
					if(b0194Type.equals("1")||b0194Type.equals("3")){
						this.setMainMessage("内设机构下不允许新建法人单位或机构分组！");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
		}/*else if(b0121.equals("-1")){//上级机构为隐藏的跟机构，则新建的第一个机构，不能是内设机构
			if(b0194Type.equals("2")){//成立，则是新建内设机构
				this.setMainMessage("内设机构不能做为根机构!");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}*/
		B01 b01 =new B01();
		PMPropertyCopyUtil.copyElementsValueToObj(b01, this);
		//参照公务员法管理审批文号 长度限制
		if(b01.getB0239()!=null&&b01.getB0239().length()>24){
			this.setMainMessage("参照公务员法管理审批文号长度过长(长度需要小于25)！");
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
		if(b0194Type.equals("1")){//法人单位
			if(b01.getB0114().trim().equals("")){
				this.setMainMessage("请输入机构编码！");
				return EventRtnType.NORMAL_SUCCESS;
			}
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
		}else if(b0194Type.equals("2")){//内设机构
			if(b01.getB0114().trim().equals("")){
				this.setMainMessage("请输入内设机构编码！");
				return EventRtnType.NORMAL_SUCCESS;
			}
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
		}else{
			if(this.getPageElement("b0114").getValue().equals("")&&b0121.length()<7){
				this.setMainMessage("请输入机构分组编码！");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		
		this.setNextEventName("sysorg.save");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
//	private void readInfo_Extend(String b0111) throws AppException {
//		List<Map<String,String>>  Info_Extends = HBUtil.queryforlist("select * from B01_EXT where b0111='"+b0111+"'",null);
//			if(Info_Extends!=null&&Info_Extends.size()>0){//
//				Map<String,String> entity = Info_Extends.get(0);
//				for(String key : entity.keySet()){
//					try {
//						this.getPageElement(key.toLowerCase()).setValue(entity.get(key));
//					} catch (Exception e) {
//					}
//				}
//			}else{
//				DBType cueDBType = DBUtil.getDBType();
//				List<String> list =null;
//				if(cueDBType==DBType.MYSQL){
//					list = HBUtil.getHBSession().createSQLQuery("select COLUMN_NAME from information_schema.COLUMNS where table_name = 'b01_ext' and  column_name!='B0111'").list();
//				}else{
//					list = HBUtil.getHBSession().createSQLQuery("SELECT column_name FROM all_tab_cols WHERE  table_name = UPPER('b01_ext')  and  column_name!='B0111'").list();
//				}
//				for(int i=0;i<list.size();i++){
//					try {
//						this.getPageElement(list.get(i).toLowerCase()).setValue("");
//					} catch (Exception e) {
//					}
//				}
//			}
//	}
	
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
	

}

