package com.insigma.siis.local.pagemodel.publicServantManage;


import java.io.File;
import com.insigma.siis.local.pagemodel.customquery.CustomQueryPageModel;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.PropertyUtils;
import org.json.JSONObject;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.odin.framework.util.tree.TreeNode;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A80;
import com.insigma.siis.local.business.entity.A81;
import com.insigma.siis.local.business.entity.A82;
import com.insigma.siis.local.business.entity.A83;
import com.insigma.siis.local.business.entity.A84;
import com.insigma.siis.local.business.entity.A99Z1;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.IdCardManageUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.publicServantManage.QueryPersonListBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.lrmx.ExpRar;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class BaseAddPageGBPageModel extends PageModel {
	public static void main(String[] args) {
		
	}
	private LogUtil applog = new LogUtil();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX()throws RadowException, AppException{
		//String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		String a0000 = this.getPageElement("a0000").getValue();//this.getRadow_parent_data();
		if(a0000==null||"".equals(a0000)){//
			//this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			HBSession sess = HBUtil.getHBSession();
			List<A01> list = sess.createQuery("from A01 where a0000='"+a0000+"'").list();
			if(list.size()==0){
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		HBSession sess = HBUtil.getHBSession();
		A01 a01 = (A01)sess.get(A01.class, a0000);
/*			Long a0194 = a01.getA0194();
		if(a0194!=null){
			this.getPageElement("a0194_y").setValue(a0194/12+"");
			this.getPageElement("a0194_m").setValue(a0194%12+"");
		}*/
		
		PMPropertyCopyUtil.copyObjValueToElement(a01, this);
		if(a01.getA0195()!=null){
			String a0195 = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getA0195()+"'");
			if(a0195!=null){
				this.getPageElement("a0195_combo").setValue(a0195);//机构名称 中文。
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save.onclick")
	@Transaction
	public int savePerson()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String a0000 = this.getPageElement("a0000").getValue();
		if(a0000==null||"".equals(a0000)){
			//新增
			A01 a01 = new A01();
			//参加工作时间不能小于出生年月
			String a0134 = this.getPageElement("a0134").getValue();//参加工作时间
			String a0107 = this.getPageElement("a0107").getValue();	//出生年月
			if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0134.compareTo(a0107)<=0){
					this.setMainMessage("参加工作时间不能小于等于出生年月");
					return EventRtnType.FAILD;
				}
			}
			
			//专长a0187a
			String a0187a =  this.getPageElement("a0187a").getValue();
			if(a0187a != null || "".equals(a0187a)){
				if(a0187a.length() > 60){
					this.setMainMessage("专长不能超过60字！");
					return EventRtnType.FAILD;
				}
			}
			String a0195 = this.getPageElement("a0195").getValue();
			String b0194 = HBUtil.getHBSession().createSQLQuery("select b0194 from b01 where b0111='"+a0195+"'").uniqueResult().toString();
			if(!b0194.equals("1")){
				this.setMainMessage("统计关系所在单位只能选择是法人单位！");
				return EventRtnType.FAILD;
			}
			a0134 = this.getPageElement("a0134").getValue();//参加工作时间
			a0107 = this.getPageElement("a0107").getValue();//参加工作时间
			a01.setA0134(a0134);
			a01.setA0107(a0107);
			a01.setA0187a(a0187a);
			a01.setA0195(a0195);
			String a0101 = this.getPageElement("a0101").getValue();
			a01.setA0101(a0101);
			String a0104 = this.getPageElement("a0104").getValue();
			a01.setA0104(a0104);
			String a0117 = this.getPageElement("a0117").getValue();
			a01.setA0117(a0117);
			String a0111 = this.getPageElement("comboxArea_a0111").getValue();
			a01.setA0111(a0111);
			String comboxArea_a0111 = this.getPageElement("comboxArea_a0111_combo").getValue();
			a01.setComboxArea_a0111(comboxArea_a0111);
			String a0114 = this.getPageElement("comboxArea_a0114").getValue();
			a01.setA0114(a0114);
			String comboxArea_a0114 = this.getPageElement("comboxArea_a0114_combo").getValue();
			a01.setComboxArea_a0114(comboxArea_a0114);
			String a0128 = this.getPageElement("a0128").getValue();
			a01.setA0128(a0128);
			String a0141 = this.getPageElement("a0141").getValue();
			a01.setA0141(a0141);
			String a0144 = this.getPageElement("a0144").getValue();
			a01.setA0144(a0144);
			String a3921 = this.getPageElement("a3921").getValue();
			a01.setA3921(a3921);
			String a3927 = this.getPageElement("a3927").getValue();
			a01.setA3927(a3927);
			//是否担任乡镇（街道）党政正职
			String n0150 = this.getPageElement("n0150check").getValue();
			a01.setN0150(n0150);
			//是否有基层工作经验
			String a01k02 = this.getPageElement("a01k02check").getValue();
			a01.setA01k02(a01k02);
			if(a01k02.equals("1")){//是
				String a0194_Y = this.getPageElement("a0194_Y").getValue();
				String a0194_M = this.getPageElement("a0194_M").getValue();
				int year =0;
				int month =0;
				try{
					year= Integer.valueOf(a0194_Y);
				}catch (Exception e) {
				}
				try{
					month = Integer.valueOf(a0194_M);
				}catch (Exception e) {
				}
				long a0194 = 0;
				if(year!=0||month!=0){
					//计算成月份
					a0194 = year*12+month;
					a01.setA0194(a0194);
					//计算a0197
					if(a0194*12>=2){
						a01.setA0197("1");
					}else{
						a01.setA0197("0");
					}
				}
			}else{
				a01.setA01k02("0");
				a01.setA0197("0");			
			}
			String a0131 = this.getPageElement("a0131").getValue();
			a01.setA0131(a0131);
			String n0152 = this.getPageElement("n0152").getValue();
			a01.setN0152(n0152);
			String a0184 = this.getPageElement("a0184").getValue();
			a01.setA0184(a0184);
			String a0165 = this.getPageElement("a0165").getValue();
			a01.setA0165(a0165);
			String a0160 = this.getPageElement("a0160").getValue();
			a01.setA0160(a0160);
			String a0121 = this.getPageElement("a0121").getValue();
			a01.setA0121(a0121);
			String a0120 = this.getPageElement("a0120").getValue();
			a01.setA0120(a0120);
			String a0122 = this.getPageElement("a0122").getValue();
			a01.setA0122(a0122);
			
			//----------------------------------------------------------------入党时间模块
			String a0144All = a0144;
			String a0141_combo = this.getPageElement("a0141_combo").getValue();
			String a3921_combo = this.getPageElement("a3921_combo").getValue();
			String a3927_combo = this.getPageElement("a3927_combo").getValue();
			String A0140 = "";
//			String a0000 = this.getRadow_parent_data();
//			if(a0000==null||"".equals(a0000)){
//				this.setMainMessage("请先保存人员基本信息！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(a0141_combo==null || "".equals(a0141_combo) || "请您选择...".equals(a0141_combo)){
				a0141="";
			}
			if(a3921_combo==null || "".equals(a3921_combo)|| "请您选择...".equals(a3921_combo)){
				a3921="";
			}
			if(a3927_combo==null || "".equals(a3927_combo) || "请您选择...".equals(a3927_combo)){
				a3927="";
			}
			if((a0141==null || "".equals(a0141))&& ((a0144!=null && !"".equals(a0144))|| (a3921!=null && !"".equals(a3921))|| (a3927!=null && !"".equals(a3927)))){
				this.setMainMessage("请先选择为政治面貌！");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				if("02".equals(a0141) || "01".equals(a0141)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("请添加入党时间！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					a0107 = this.getPageElement("a0107").getValue();//出生年月
					if(!StringUtil.isEmpty(a0144)){
						if(a0144.length()==6){
							a0144+="01";
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String now=sdf.format(new Date());
						if(Integer.valueOf(a0144)>Integer.valueOf(now)){
							this.setMainMessage("入党时间不能晚于当前时间");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}	
					if(a0107!=null&&!"".equals(a0107)){
						if (a0107.length() == 6) {
							a0107 += "01";
						}
						if (a0144.length() == 6) {
							a0144 += "01";
						}
						if(a0107!=null&&!"".equals(a0107)&&a0144!=null&&!"".equals(a0144)){
							int start = Integer.valueOf(a0107);      	//出生日期
							int end = Integer.valueOf(a0144);			//入党时间
							
							//计算18岁的年月日
							/*String a010718 = a0107.substring(0,4);
							int year18 = Integer.valueOf(a010718) + 18;
							a010718 = String.valueOf(year18) + a0107.substring(4,8);
							start = Integer.valueOf(a010718);*/
							
							
							if (start >= end) {
								this.setMainMessage("入党时间不能早于等于出生日期");
								//this.setMainMessage("入党时间不能小于18岁");
								return EventRtnType.NORMAL_SUCCESS;
							}
							
							a0107 = a0107.replace(".", "").substring(0, 6);
							a0144 = a0144.replace(".", "").substring(0, 6);
							
						}
					}
					String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							this.setMainMessage("请添加第二党派！");
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							A0140 = a0144_sj ;
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  a3921_combo+ "、" + a3927_combo + "(" + a0144_sj +")";
						} else {
							A0140 =  a3921_combo+ "(" + a0144_sj +")";
						}
					}
				} else {
					if("02".equals(a3921) || "01".equals(a3921)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("请添加入党时间！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
					if("02".equals(a3927) || "01".equals(a3927)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("请添加入党时间！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							this.setMainMessage("请添加第二党派！");
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							A0140 = "(" + a0141_combo +")";
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ "、" + a3927_combo +")";
						} else {
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ ")";
						}
					}
				}
				
			}
			
			if("()".equals(A0140)){
				A0140="";
			}
			
			String a0144_time = "";
			if(a0144 != null && !a0144.equals("")){
				a0144_time = a0144.substring(0,4)+"."+a0144.substring(4,6);
			}
			
			
			//重新拼接入党时间
			String A0140New = "";
			String date = "";
			
			if(a0141.equals("01") || a0141.equals("02")){
				date = "("+a0144_time+")";
				a0141_combo = "";
			}
			if(a3921.equals("01") || a3921.equals("02")){
				date = "("+a0144_time+")";
				a3921_combo = "";
			}
			if(a3927.equals("01") || a3927.equals("02")){
				date = "("+a0144_time+")";
				a3927_combo = "";
			}
			
			
			
			if(a0141_combo != null && !a0141_combo.equals("")){
				A0140New = A0140New + a0141_combo+ "、";
			}
			if(a3921_combo != null && !a3921_combo.equals("")){
				A0140New = A0140New + a3921_combo+ "、";
			}
			if(a3927_combo != null && !a3927_combo.equals("")){
				A0140New = A0140New + a3927_combo+ "、";
			}
			
			if(A0140New != null && !A0140New.equals("")){
				A0140New = A0140New.substring(0, A0140New.length()-1);
				if(date != null && !date.equals("")){
					A0140New = A0140New + date;
				}
			}else{
				A0140New = A0140New + a0144_time;
			}
			
			
			if("()".equals(A0140New)){
				A0140New="";
			}
			
			//判断是否为共产党，没有则拼接括号
			if(a0144 == null || a0144.equals("")){		//加括号
				
				if(A0140New != null && !A0140New.equals("")){
					A0140New = "(" +A0140New+ ")";
				}
				
			}
			a01.setA0140(A0140New);
			a01.setA0141(a0141);
			a01.setA3921(a3921);
			a01.setA3927(a3927);
			a01.setA0144(a0144All);
			//----------------------------------------------------------------入党时间模块
			
			String uuid=UUID.randomUUID().toString();
			//默认现职人员
			a01.setA0163("1");
			a01.setA0000(uuid);
			a01.setStatus("1");
			HBSession session = HBUtil.getHBSession();
			session.save(a01);
			session.flush();
			a0000=uuid;
			this.getPageElement("a0000").setValue(uuid);
			
		}else{
			//修改
			A01 a01 = new A01();
			//参加工作时间不能小于出生年月
			String a0134 = this.getPageElement("a0134").getValue();//参加工作时间
			String a0107 = this.getPageElement("a0107").getValue();	//出生年月
			if(a0134!=null&&!"".equals(a0134)&&a0107!=null&&!"".equals(a0107)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(a0134.compareTo(a0107)<=0){
					this.setMainMessage("参加工作时间不能小于等于出生年月");
					return EventRtnType.FAILD;
				}
			}
			a0134 = this.getPageElement("a0134").getValue();//参加工作时间
			a0107 = this.getPageElement("a0107").getValue();	//出生年月
			//专长a0187a
			String a0187a =  this.getPageElement("a0187a").getValue();
			if(a0187a != null || "".equals(a0187a)){
				if(a0187a.length() > 60){
					this.setMainMessage("专长不能超过60字！");
					return EventRtnType.FAILD;
				}
			}
			String a0195 = this.getPageElement("a0195").getValue();
			String b0194 = HBUtil.getHBSession().createSQLQuery("select b0194 from b01 where b0111='"+a0195+"'").uniqueResult().toString();
			if(!b0194.equals("1")){
				this.setMainMessage("统计关系所在单位只能选择是法人单位！");
				return EventRtnType.FAILD;
			}
			a01.setA0134(a0134);
			a01.setA0107(a0107);
			a01.setA0187a(a0187a);
			a01.setA0195(a0195);
			String a0101 = this.getPageElement("a0101").getValue();
			a01.setA0101(a0101);
			String a0104 = this.getPageElement("a0104").getValue();
			a01.setA0104(a0104);
			String a0117 = this.getPageElement("a0117").getValue();
			a01.setA0117(a0117);
			String a0111 = this.getPageElement("comboxArea_a0111").getValue();
			a01.setA0111(a0111);
			String comboxArea_a0111 = this.getPageElement("comboxArea_a0111_combo").getValue();
			a01.setComboxArea_a0111(comboxArea_a0111);
			String a0114 = this.getPageElement("comboxArea_a0114").getValue();
			a01.setA0114(a0114);
			String comboxArea_a0114 = this.getPageElement("comboxArea_a0114_combo").getValue();
			a01.setComboxArea_a0114(comboxArea_a0114);
			String a0128 = this.getPageElement("a0128").getValue();
			a01.setA0128(a0128);
			String a0141 = this.getPageElement("a0141").getValue();
			a01.setA0141(a0141);
			String a0144 = this.getPageElement("a0144").getValue();
			a01.setA0144(a0144);
			String a3921 = this.getPageElement("a3921").getValue();
			a01.setA3921(a3921);
			String a3927 = this.getPageElement("a3927").getValue();
			a01.setA3927(a3927);
			//是否担任乡镇（街道）党政正职
			String n0150 = this.getPageElement("n0150check").getValue();
			a01.setN0150(n0150);
			//是否有基层工作经历
			String a01k02 = this.getPageElement("a01k02check").getValue();
			a01.setA01k02(a01k02);
			if(a01k02.equals("1")){//是
				String a0194_Y = this.getPageElement("a0194_Y").getValue();
				String a0194_M = this.getPageElement("a0194_M").getValue();
				int year =0;
				int month =0;
				try{
					year= Integer.valueOf(a0194_Y);
				}catch (Exception e) {
				}
				try{
					month = Integer.valueOf(a0194_M);
				}catch (Exception e) {
				}
				long a0194 = 0;
				if(year!=0||month!=0){
					//计算成月份
					a0194 = year*12+month;
					a01.setA0194(a0194);
					//计算a0197
					if(a0194*12>=2){
						a01.setA0197("1");
					}else{
						a01.setA0197("0");
					}
				}
			}
			String a0131 = this.getPageElement("a0131").getValue();
			a01.setA0131(a0131);
			String n0152 = this.getPageElement("n0152").getValue();
			a01.setN0152(n0152);
			String a0184 = this.getPageElement("a0184").getValue();
			a01.setA0184(a0184);
			String a0165 = this.getPageElement("a0165").getValue();
			a01.setA0165(a0165);
			String a0160 = this.getPageElement("a0160").getValue();
			a01.setA0160(a0160);
			String a0121 = this.getPageElement("a0121").getValue();
			a01.setA0121(a0121);
			String a0120 = this.getPageElement("a0120").getValue();
			a01.setA0120(a0120);
			String a0122 = this.getPageElement("a0122").getValue();
			a01.setA0122(a0122);
			
			//----------------------------------------------------------------入党时间模块
			String a0144All = a0144;
			String a0141_combo = this.getPageElement("a0141_combo").getValue();
			String a3921_combo = this.getPageElement("a3921_combo").getValue();
			String a3927_combo = this.getPageElement("a3927_combo").getValue();
			String A0140 = "";
//			String a0000 = this.getRadow_parent_data();
//			if(a0000==null||"".equals(a0000)){
//				this.setMainMessage("请先保存人员基本信息！");
//				return EventRtnType.NORMAL_SUCCESS;
//			}
			if(a0141_combo==null || "".equals(a0141_combo) || "请您选择...".equals(a0141_combo)){
				a0141="";
			}
			if(a3921_combo==null || "".equals(a3921_combo)|| "请您选择...".equals(a3921_combo)){
				a3921="";
			}
			if(a3927_combo==null || "".equals(a3927_combo) || "请您选择...".equals(a3927_combo)){
				a3927="";
			}
			if((a0141==null || "".equals(a0141))&& ((a0144!=null && !"".equals(a0144))|| (a3921!=null && !"".equals(a3921))|| (a3927!=null && !"".equals(a3927)))){
				this.setMainMessage("请先选择为政治面貌！");
				return EventRtnType.NORMAL_SUCCESS;
			} else {
				if("02".equals(a0141) || "01".equals(a0141)){
					if((a0144==null || "".equals(a0144))){
						this.setMainMessage("请添加入党时间！");
						return EventRtnType.NORMAL_SUCCESS;
					}
					a0107 = this.getPageElement("a0107").getValue();//出生年月
					if(!StringUtil.isEmpty(a0144)){
						if(a0144.length()==6){
							a0144+="01";
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						String now=sdf.format(new Date());
						if(Integer.valueOf(a0144)>Integer.valueOf(now)){
							this.setMainMessage("入党时间不能晚于当前时间");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}	
					if(a0107!=null&&!"".equals(a0107)){
						if (a0107.length() == 6) {
							a0107 += "01";
						}
						if (a0144.length() == 6) {
							a0144 += "01";
						}
						if(a0107!=null&&!"".equals(a0107)&&a0144!=null&&!"".equals(a0144)){
							int start = Integer.valueOf(a0107);      	//出生日期
							int end = Integer.valueOf(a0144);			//入党时间
							
							//计算18岁的年月日
							/*String a010718 = a0107.substring(0,4);
							int year18 = Integer.valueOf(a010718) + 18;
							a010718 = String.valueOf(year18) + a0107.substring(4,8);
							start = Integer.valueOf(a010718);*/
							
							
							if (start >= end) {
								this.setMainMessage("入党时间不能早于等于出生日期");
								//this.setMainMessage("入党时间不能小于18岁");
								return EventRtnType.NORMAL_SUCCESS;
							}
							
							a0107 = a0107.replace(".", "").substring(0, 6);
							a0144 = a0144.replace(".", "").substring(0, 6);
							
						}
					}
					String a0144_sj = a0144.substring(0,4)+"."+a0144.substring(4,6);
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							this.setMainMessage("请添加第二党派！");
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							A0140 = a0144_sj ;
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  a3921_combo+ "、" + a3927_combo + "(" + a0144_sj +")";
						} else {
							A0140 =  a3921_combo+ "(" + a0144_sj +")";
						}
					}
				} else {
					if("02".equals(a3921) || "01".equals(a3921)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("请添加入党时间！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
					if("02".equals(a3927) || "01".equals(a3927)){
						if((a0144==null || "".equals(a0144))){
							this.setMainMessage("请添加入党时间！");
							return EventRtnType.NORMAL_SUCCESS;
						}
					}
					if(a3921==null || "".equals(a3921)){
						if((a3927!=null && !"".equals(a3927))){
							this.setMainMessage("请添加第二党派！");
							return EventRtnType.NORMAL_SUCCESS;
						} else {
							A0140 = "(" + a0141_combo +")";
						}
					} else {
						if(a3927!=null && !"".equals(a3927)){
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ "、" + a3927_combo +")";
						} else {
							A0140 =  "(" + a0141_combo+ "、" +a3921_combo+ ")";
						}
					}
				}
				
			}
			
			if("()".equals(A0140)){
				A0140="";
			}
			
			String a0144_time = "";
			if(a0144 != null && !a0144.equals("")){
				a0144_time = a0144.substring(0,4)+"."+a0144.substring(4,6);
			}
			
			
			//重新拼接入党时间
			String A0140New = "";
			String date = "";
			
			if(a0141.equals("01") || a0141.equals("02")){
				date = "("+a0144_time+")";
				a0141_combo = "";
			}
			if(a3921.equals("01") || a3921.equals("02")){
				date = "("+a0144_time+")";
				a3921_combo = "";
			}
			if(a3927.equals("01") || a3927.equals("02")){
				date = "("+a0144_time+")";
				a3927_combo = "";
			}
			
			
			
			if(a0141_combo != null && !a0141_combo.equals("")){
				A0140New = A0140New + a0141_combo+ "、";
			}
			if(a3921_combo != null && !a3921_combo.equals("")){
				A0140New = A0140New + a3921_combo+ "、";
			}
			if(a3927_combo != null && !a3927_combo.equals("")){
				A0140New = A0140New + a3927_combo+ "、";
			}
			
			if(A0140New != null && !A0140New.equals("")){
				A0140New = A0140New.substring(0, A0140New.length()-1);
				if(date != null && !date.equals("")){
					A0140New = A0140New + date;
				}
			}else{
				A0140New = A0140New + a0144_time;
			}
			
			
			if("()".equals(A0140New)){
				A0140New="";
			}
			
			//判断是否为共产党，没有则拼接括号
			if(a0144 == null || a0144.equals("")){		//加括号
				
				if(A0140New != null && !A0140New.equals("")){
					A0140New = "(" +A0140New+ ")";
				}
				
			}
			a01.setA0140(A0140New);
			a01.setA0141(a0141);
			a01.setA3921(a3921);
			a01.setA3927(a3927);
			a01.setA0144(a0144All);
			//-------------------------------------------------------------------入党时间模块
			
			a01.setStatus("1");
			a01.setA0000(a0000);
			//默认现职人员
			a01.setA0163("1");
			HBSession session = HBUtil.getHBSession();
			session.update(a01);
			this.getPageElement("a0000").setValue(a0000);
		}
		A29Save(a0000);
		A30Save(a0000);
		A37Save(a0000);
		A80Save(a0000);
		A81Save(a0000);
		A82Save(a0000);
		A83Save(a0000);
		//A84Save(a0000);
		A11Save(a0000);
		this.setMainMessage("保存成功！");
		return EventRtnType.NORMAL_SUCCESS;
	}
	public void A11Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a1101 = this.getPageElement("a1101").getValue();
		String a1104 = this.getPageElement("a1104").getValue();
		String a1107 = this.getPageElement("a1107").getValue();
		String a1114 = this.getPageElement("a1114").getValue();
		String a1111 = this.getPageElement("a1111").getValue();
		String a1151 = this.getPageElement("a1151").getValue();
		String a1121a = this.getPageElement("a1121a").getValue();
		String a1127 = this.getPageElement("a1127").getValue();
		String a1131 = this.getPageElement("a1131").getValue();
		String a1108 = this.getPageElement("a1108").getValue();
		String a1107c = this.getPageElement("a1107c").getValue();
		String g02003 = this.getPageElement("g02003").getValue();
		String a1108a = this.getPageElement("a1108a").getValue();
		String a1108b = this.getPageElement("a1108b").getValue();
		String g11003 = this.getPageElement("g11003").getValue();
		String g11004 = this.getPageElement("g11004").getValue();
		String g11005 = this.getPageElement("g11005").getValue();
		String g11006 = this.getPageElement("g11006").getValue();
		String g11007 = this.getPageElement("g11007").getValue();
		String g11008 = this.getPageElement("g11008").getValue();
		String g11009 = this.getPageElement("g11009").getValue();
		String g11010 = this.getPageElement("g11010").getValue();
		String g11011 = this.getPageElement("g11011").getValue();
		String g11012 = this.getPageElement("g11012").getValue();
		String g11013 = this.getPageElement("g11013").getValue();
		String g11014 = this.getPageElement("g11014").getValue();
		String g11015 = this.getPageElement("g11015").getValue();
		A11 a11_db = (A11) session.createQuery("from A11 where a0000='"+a0000+"'").uniqueResult();
		A11 a11 = new A11();
		if(a11_db==null){//增加
			a11.setA0000(a0000);
			a11.setA1101(a1101);
			a11.setA1104(a1104);
			a11.setA1107(a1107);
			a11.setA1114(a1114);
			a11.setA1111(a1111);
			a11.setA1151(a1151);
			a11.setA1121a(a1121a);
			a11.setA1127(a1127);
			a11.setA1131(a1131);
			if(a1108!=null&&!"".equals(a1108)){
				a11.setA1108(new BigDecimal(a1108));
			}
			if(a1107c!=null&&!"".equals(a1107c)){
				a11.setA1107c(new BigDecimal(a1107c));
			}
			a11.setG02003(g02003);
			a11.setA1108a(a1108a);
			a11.setA1108b(a1108b);
			a11.setG11003(g11003);
			a11.setG11004(g11004);
			a11.setG11005(g11005);
			a11.setG11006(g11006);
			a11.setG11007(g11007);
			a11.setG11008(g11008);
			a11.setG11009(g11009);
			a11.setG11010(g11010);
			a11.setG11011(g11011);
			a11.setG11012(g11012);
			a11.setG11013(g11013);
			a11.setG11014(g11014);
			a11.setG11015(g11015);
			session.save(a11);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A11 SET a0000 = '"+a0000+"',a1101 = '"+a1101+"',a1104 = '"+a1104+"',a1107 = '"+a1107+"',a1114 = '"+a1114+"',a1111 = '"+a1111+"',a1151 = '"+a1151+"',a1121a = '"+a1121a+"',a1127 = '"+a1127+"',a1131 = '"+a1131+"',a1108 = '"+a1108+"',a1107c = '"+a1107c+"',g02003 = '"+g02003+"',a1108a = '"+a1108a+"',a1108b = '"+a1108b+"',g11003 = '"+g11003+"',g11004 = '"+g11004+"',g11005 = '"+g11005+"',g11006 = '"+g11006+"',g11007 = '"+g11007+"',g11008 = '"+g11008+"',g11009 = '"+g11009+"',g11010 = '"+g11010+"',g11011 = '"+g11011+"',g11012 = '"+g11012+"',g11013 = '"+g11013+"',g11014 = '"+g11014+"',g11015 = '"+g11015+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A84Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a03011 = this.getPageElement("a03011").getValue();
		String a03021 = this.getPageElement("a03021").getValue();
		String a03095 = this.getPageElement("a03095").getValue();
		String a03027 = this.getPageElement("a03027").getValue();
		String a03014 = this.getPageElement("a03014").getValue();
		String a03017 = this.getPageElement("a03017").getValue();
		String a03018 = this.getPageElement("a03018").getValue();
		String a03024 = this.getPageElement("a03024").getValue();
		A84 a84_db = (A84) session.createQuery("from A84 where a0000='"+a0000+"'").uniqueResult();
		A84 a84 = new A84();
		if(a84_db==null){//增加
			a84.setA0000(a0000);
			a84.setA03011(a03011);
			a84.setA03021(a03021);
			a84.setA03095(a03095);
			a84.setA03027(a03027);
			a84.setA03014(a03014);
			a84.setA03017(a03017);
			a84.setA03018(a03018);
			a84.setA03024(a03024);
			session.save(a84);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A84 SET a0000 = '"+a0000+"',a03011 = '"+a03011+"',a03021 = '"+a03021+"',a03095 = '"+a03095+"',a03027 = '"+a03027+"',a03014 = '"+a03014+"',a03017 = '"+a03017+"',a03018 = '"+a03018+"',a03024 = '"+a03024+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A82Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a02191 = this.getPageElement("a02191").getValue();
		String a29301 = this.getPageElement("a29301").getValue();
		String a29304 = this.getPageElement("a29304").getValue();
		String a29044 = this.getPageElement("a29044").getValue();
		String a29307 = this.getPageElement("a29307").getValue();
		A82 a82_db = (A82) session.createQuery("from A82 where a0000='"+a0000+"'").uniqueResult();
		A82 a82 = new A82();
		if(a82_db==null){//增加
			a82.setA0000(a0000);
			a82.setA02191(a02191);
			a82.setA29301(a29301);
			a82.setA29304(a29304);
			a82.setA29044(a29044);
			a82.setA29307(a29307);
			session.save(a82);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A82 SET a0000 = '"+a0000+"',a02191 = '"+a02191+"',a29301 = '"+a29301+"',a29304 = '"+a29304+"',a29044 = '"+a29044+"',a29307 = '"+a29307+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A83Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a02192 = this.getPageElement("a02192").getValue();
		String a29311 = this.getPageElement("a29311").getValue();
		String g02002 = this.getPageElement("g02002").getValue();
		String a29044 = this.getPageElement("a29044_a83").getValue();
		String a29041 = this.getPageElement("a29041").getValue();
		String a29354 = this.getPageElement("a29354").getValue();
		String a44027 = this.getPageElement("a44027_a83").getValue();
		String a39077 = this.getPageElement("a39077_a83").getValue();
		String a44031 = this.getPageElement("a44031_a83").getValue();
		String a39084 = this.getPageElement("a39084_a83").getValue();
		A83 a83_db = (A83) session.createQuery("from A83 where a0000='"+a0000+"'").uniqueResult();
		A83 a83 = new A83();
		if(a83_db==null){//增加
			a83.setA0000(a0000);
			a83.setA02192(a02192);
			a83.setA29311(a29311);
			a83.setG02002(g02002);
			a83.setA29044(a29044);
			a83.setA29041(a29041);
			a83.setA29354(a29354);
		/*	a83.setA44027(a44027);
			a83.setA39077(a39077);
			a83.setA44031(a44031);
			a83.setA39084(a39084);*/
			a83.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a83.setA39077(Byte.valueOf(a39077));
			}
			a83.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a83.setA39077(Byte.valueOf(a39084));
			}
			session.save(a83);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A83 SET a0000 = '"+a0000+"',a02192 = '"+a02192+"',a29311 = '"+a29311+"',g02002 = '"+g02002+"',a29044 = '"+a29044+"',a29041 = '"+a29041+"',a29354 = '"+a29354+"',a44027 = '"+a44027+"',a39077 = '"+a39077+"',a44031 = '"+a44031+"',a39084 = '"+a39084+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	
	
	
	
	public void A81Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String g02001 = this.getPageElement("g02001").getValue();
		String a29071 = this.getPageElement("a29071").getValue();
		String a29072 = this.getPageElement("a29072").getValue();
		String a29341 = this.getPageElement("a29341").getValue();
		String a29073_Y = this.getPageElement("a29073_Y").getValue();
		String a29073_M = this.getPageElement("a29073_M").getValue();
		String a29344 = this.getPageElement("a29344").getValue();
		String a29347a = this.getPageElement("a29347a").getValue();
		String a29347b = this.getPageElement("a29347b").getValue();
		String a29347c = this.getPageElement("a29347c").getValue();
		String a29351b = this.getPageElement("a29351b").getValue();
		String a39067 = this.getPageElement("a39067_a81").getValue();
		String a44027 = this.getPageElement("a44027_a81").getValue();
		String a39077 = this.getPageElement("a39077_a81").getValue();
		String a44031 = this.getPageElement("a44031_a81").getValue();
		String a39084 = this.getPageElement("a39084_a81").getValue();
		String a03011 = this.getPageElement("a03011_a81").getValue();
		String a03021 = this.getPageElement("a03021_a81").getValue();
		String a03095 = this.getPageElement("a03095_a81").getValue();
		String a03027 = this.getPageElement("a03027_a81").getValue();
		String a03014 = this.getPageElement("a03014_a81").getValue();
		String a03017 = this.getPageElement("a03017_a81").getValue();
		String a03018 = this.getPageElement("a03018_a81").getValue();
		String a03024 = this.getPageElement("a03024_a81").getValue();
		Byte a29073 = DataChange(a29073_Y, a29073_M);
		A81 a81_db = (A81) session.createQuery("from A81 where a0000='"+a0000+"'").uniqueResult();
		A81 a81 = new A81();
		A84 a84 = new A84();
		if(a81_db==null){//增加
			a81.setA0000(a0000);
			a81.setG02001(g02001);
			a81.setA29071(a29071);
			a81.setA29072(a29072);
			a81.setA29341(a29341);
			a81.setA29073(a29073);
			a81.setA29344(a29344);
			a81.setA29347a(a29347a);
			a81.setA29347b(a29347b);
			a81.setA29347c(a29347c);
			a81.setA29351b(a29351b);
			a81.setA39067(a39067);
			a81.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a81.setA39077(Byte.valueOf(a39077));
			}
			a81.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a81.setA39084(Byte.valueOf(a39084));
			}
			a84.setA0000(a0000);
			a84.setA03011(a03011);
			a84.setA03021(a03021);
			a84.setA03095(a03095);
			a84.setA03027(a03027);
			a84.setA03014(a03014);
			a84.setA03017(a03017);
			a84.setA03018(a03018);
			a84.setA03024(a03024);
			a84.setA84type("2");//考试录入信息集(A80)----2
			session.save(a81);
			session.save(a84);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A81 SET a0000 = '"+a0000+"',g02001 = '"+g02001+"',a29071 = '"+a29071+"',a29072 = '"+a29072+"',a29341 = '"+a29341+"',a29073 = '"+a29073+"',a29344 = '"+a29344+"',a29347a = '"+a29347a+"',a29347b = '"+a29347b+"',a29347c = '"+a29347c+"',a29351b = '"+a29351b+"',a39067 = '"+a39067+"',a44027 = '"+a44027+"',a39077 = '"+a39077+"',a44031 = '"+a44031+"',a39084 = '"+a39084+"' where a0000 = '"+a0000+"'").executeUpdate();
			session.createSQLQuery("UPDATE A84 SET a0000 = '"+a0000+"',a03011 = '"+a03011+"',a03021 = '"+a03021+"',a03095 = '"+a03095+"',a03027 = '"+a03027+"',a03014 = '"+a03014+"',a03017 = '"+a03017+"',a03018 = '"+a03018+"',a03024 = '"+a03024+"' where a0000 = '"+a0000+"' and a84type='2'").executeUpdate();
		}
	}
	
	public void A80Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a29314 = this.getPageElement("a29314").getValue();
		String a03033 = this.getPageElement("a03033").getValue();
		String a29321 = this.getPageElement("a29321").getValue();
		String a29324a = this.getPageElement("a29324a").getValue();
		String a29324b = this.getPageElement("a29324b").getValue();
		String a29327a = this.getPageElement("a29327a").getValue();
		String a29327b = this.getPageElement("a29327b").getValue();
		String a29334_GY = this.getPageElement("a29334_GY").getValue();
		String a29334_GM = this.getPageElement("a29334_GM").getValue();
		String a29337 = this.getPageElement("a29337").getValue();
		String a39061 = this.getPageElement("a39061").getValue();
		String a39064 = this.getPageElement("a39064").getValue();
		String a39067 = this.getPageElement("a39067").getValue();
		String a39071 = this.getPageElement("a39071").getValue();
		String a44027 = this.getPageElement("a44027").getValue();
		String a39077 = this.getPageElement("a39077").getValue();
		String a44031 = this.getPageElement("a44031").getValue();
		String a39084 = this.getPageElement("a39084").getValue();
		String a03011 = this.getPageElement("a03011_a80").getValue();
		String a03021 = this.getPageElement("a03021_a80").getValue();
		String a03095 = this.getPageElement("a03095_a80").getValue();
		String a03027 = this.getPageElement("a03027_a80").getValue();
		String a03014 = this.getPageElement("a03014_a80").getValue();
		String a03017 = this.getPageElement("a03017_a80").getValue();
		String a03018 = this.getPageElement("a03018_a80").getValue();
		String a03024 = this.getPageElement("a03024_a80").getValue();
		A80 a80_db = (A80) session.createQuery("from A80 where a0000='"+a0000+"'").uniqueResult();
		A80 a80 = new A80();
		A84 a84 = new A84();
		Byte a29334 = DataChange(a29334_GY, a29334_GM);
		if(a80_db==null){//增加
			a80.setA0000(a0000);
			a80.setA29314(a29314);
			a80.setA03033(a03033);
			a80.setA29321(a29321);
			a80.setA29324a(a29324a);
			a80.setA29324b(a29324b);
			a80.setA29327a(a29327a);
			a80.setA29327b(a29327b);
			a80.setA29334(a29334);
			a80.setA29337(a29337);
			a80.setA39061(a39061);
			a80.setA39064(a39064);
			a80.setA39067(a39067);
			a80.setA39071(a39071);
			a80.setA44027(a44027);
			if(a39077!=null&&!"".equals(a39077)){
				a80.setA39077(Byte.valueOf(a39077));
			}
			a80.setA44031(a44031);
			if(a39084!=null&&!"".equals(a39084)){
				a80.setA39084(Byte.valueOf(a39084));
			}
			a84.setA0000(a0000);
			a84.setA03011(a03011);
			a84.setA03021(a03021);
			a84.setA03095(a03095);
			a84.setA03027(a03027);
			a84.setA03014(a03014);
			a84.setA03017(a03017);
			a84.setA03018(a03018);
			a84.setA03024(a03024);
			a84.setA84type("1");//考试录入信息集(A80)----1
			session.save(a80);
			session.save(a84);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE A84 SET a0000 = '"+a0000+"',a03011 = '"+a03011+"',a03021 = '"+a03021+"',a03095 = '"+a03095+"',a03027 = '"+a03027+"',a03014 = '"+a03014+"',a03017 = '"+a03017+"',a03018 = '"+a03018+"',a03024 = '"+a03024+"' where a0000 = '"+a0000+"' and a84type='1'").executeUpdate();
			session.createSQLQuery("UPDATE A80 SET a0000 = '"+a0000+"',a29314 = '"+a29314+"',a03033 = '"+a03033+"',a29321 = '"+a29321+"',a29324a = '"+a29324a+"',a29324b = '"+a29324b+"',a29327a = '"+a29327a+"',a29327b = '"+a29327b+"',a29334 = '"+a29334+"',a29337 = '"+a29337+"',a39061 = '"+a39061+"',a39064 = '"+a39064+"',a39067 = '"+a39067+"',a39071 = '"+a39071+"',a44027 = '"+a44027+"',a39077 = '"+a39077+"',a44031 = '"+a44031+"',a39084 = '"+a39084+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A37Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a3701 = this.getPageElement("a3701").getValue();
		String a3707a = this.getPageElement("a3707a").getValue();
		String a3707b = this.getPageElement("a3707b").getValue();
		String a3707c = this.getPageElement("a3707c").getValue();
		String a3711 = this.getPageElement("a3711").getValue();
		String a3721 = this.getPageElement("a3721").getValue();
		A37 a37_db = (A37) session.createQuery("from A37 where a0000='"+a0000+"'").uniqueResult();
		A37 a37 = new A37();
		if(a37_db==null){//增加
			a37.setA3701(a3701);
			a37.setA3707a(a3707a);
			a37.setA3707b(a3707b);
			a37.setA3707c(a3707c);
			a37.setA3711(a3711);
			a37.setA3721(a3721);
			a37.setA0000(a0000);
			session.save(a37);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE a37 SET a3701 = '"+a3701+"',a3707a = '"+a3707a+"',a3707b = '"+a3707b+"',a3707c = '"+a3707c+"',a3711 = '"+a3711+"',a3721 = '"+a3721+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A30Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		String a3001 = this.getPageElement("a3001").getValue();
		String a3004 = this.getPageElement("a3004").getValue();
		String a3034 = this.getPageElement("a3034").getValue();
		String a3954a = this.getPageElement("a3954a").getValue();
		String a3954b = this.getPageElement("a3954b").getValue();
		A30 a30_db = (A30) session.createQuery("from A30 where a0000='"+a0000+"'").uniqueResult();
		A30 a30 = new A30();
		if(a30_db==null){//增加
			a30.setA0000(a0000);
			a30.setA3001(a3001);
			a30.setA3004(a3004);
			a30.setA3034(a3034);
			a30.setA3954a(a3954a);
			a30.setA3954b(a3954b);
			session.save(a30);
			session.flush();
		}else{
			session.createSQLQuery("UPDATE a30 SET a3001 = '"+a3001+"',a3004 = '"+a3004+"',a3034 = '"+a3034+"',a3954a = '"+a3954a+"',a3954b = '"+a3954b+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
	}
	public void A29Save(String a0000) throws RadowException{
		HBSession session = HBUtil.getHBSession();
		//A29子集的保存
		String a2907 = this.getPageElement("a2907").getValue();
		String a2911 = this.getPageElement("a2911").getValue();
		String a2921a = this.getPageElement("a2921a").getValue();
		String a2941 = this.getPageElement("a2941").getValue();
		String a2947a_Y = this.getPageElement("a2947a_Y").getValue();
		String a2947a_M = this.getPageElement("a2947a_M").getValue();
		long a2947a = DataChange(a2947a_Y, a2947a_M);
		String a2944 = this.getPageElement("a2944").getValue();
		String a2947 = this.getPageElement("a2947").getValue();
		String a2949 = this.getPageElement("a2949").getValue();
		A29 a29_db = (A29) session.createQuery("from A29 where a0000='"+a0000+"'").uniqueResult();
		A29 a29 = new A29();
		if(a29_db==null){//增加
			a29.setA2907(a2907);
			a29.setA2911(a2911);
			a29.setA2921a(a2921a);
			a29.setA2941(a2941);
			a29.setA2947a(a2947a);
			a29.setA2944(a2944);
			a29.setA2947(a2947);
			a29.setA2949(a2949);
			a29.setA0000(a0000);
			session.save(a29);
			session.flush();
		}else{//修改
			session.createSQLQuery("UPDATE a29 SET a2907 = '"+a2907+"',a2911 = '"+a2911+"',a2921a = '"+a2921a+"',a2941 = '"+a2941+"',a2944 = '"+a2944+"',a2947a = null,a2947 = '"+a2947+"',a2949 = '"+a2949+"' where a0000 = '"+a0000+"'").executeUpdate();
		}
		
	}
	
	//时间转换
	public Byte DataChange(String year,String months){
		Byte year_flag =0;
		Byte month_flag =0;
		try{
			year_flag= Byte.valueOf(year);
		}catch (Exception e) {
		}
		try{
			month_flag = Byte.valueOf(months);
		}catch (Exception e) {
		}
		Byte result = 0;
		if(year_flag!=0||month_flag!=0){
			//计算成月份
			result = (byte) (year_flag*12+month_flag);
		}
		return result;
	}
}



