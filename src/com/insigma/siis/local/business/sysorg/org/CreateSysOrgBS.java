package com.insigma.siis.local.business.sysorg.org;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.ReturnDO;
import com.insigma.siis.local.business.sysorg.org.dto.B01DTO;
import com.insigma.siis.local.business.utils.ChineseSpelling;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class CreateSysOrgBS extends BSSupport {

	// 根据主键查询
	public static B01 LoadB01(String id) {
		try {
			return (B01) HBUtil.getHBSession().get(B01.class, id.trim());
		} catch (Exception e) {
			return null;
		}
	}
	
	// 行政编制
	public static String selectCivilServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where  t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 ='1' and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}
	// 参照公务员法管理事业编制
	public static String selectLikeCivilServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 ='2' and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 其他事业编制
	public static String selectCareerServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 in ('3','4') and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}
	
	//根据机构类型，删除部分值
	public static ReturnDO<B01> alertB01(B01 b01){
		String type = b01.getB0194();
		ReturnDO<B01> returnDO = new ReturnDO<B01>();
		if(type != null){
			if("1".equals(type)){//法人单位
				b01.setB0150(b01.getB0150()==null?0:b01.getB0150());
				b01.setB0183(b01.getB0183()==null?0:b01.getB0183());
				b01.setB0185(b01.getB0185()==null?0:b01.getB0185());
				b01.setB0188(b01.getB0188()==null?0:b01.getB0188());
				b01.setB0189(b01.getB0189()==null?0:b01.getB0189());
				b01.setB0190(b01.getB0190()==null?0:b01.getB0190());
				b01.setB0191a(b01.getB0191a()==null?0:b01.getB0191a());
				b01.setB0192(b01.getB0192()==null?0:b01.getB0192());
				b01.setB0193(b01.getB0193()==null?0:b01.getB0193());
				b01.setB0227(b01.getB0227()==null?0:b01.getB0227());
				b01.setB0232(b01.getB0232()==null?0:b01.getB0232());
				b01.setB0233(b01.getB0233()==null?0:b01.getB0233());
				/*
				 * b01.setB0234(b01.getB0234()==null?"":b01.getB0234());
				 * b01.setB0235(b01.getB0235()==null?"":b01.getB0235());
				 * b01.setB0236(b01.getB0236()==null?"":b01.getB0236());
				 */
			}else if("2".equals(type)){//内设机构
				//内设机构信息
				b01.setB0150(b01.getB0150()==null?0:b01.getB0150());
				b01.setB0190(b01.getB0190()==null?0:b01.getB0190());
				b01.setB0191a(b01.getB0191a()==null?0:b01.getB0191a());
				//法人单位信息
				b01.setB0183(0L);
				b01.setB0185(0L);
				b01.setB0188(0L);
				b01.setB0189(0L);
				b01.setB0192(0L);
				b01.setB0193(0L);
				b01.setB0227(0L);
				b01.setB0232(0L);
				b01.setB0233(0L);
				/*
				 * b01.setB0234(""); b01.setB0235(0L); b01.setB0236(0L);
				 */
				
				b01.setB0117(null);
				b01.setB0124(null);
				b01.setB0131(null);
			}else if("3".equals(type)){
				b01.setB0117(null);
				b01.setB0124(null);
				b01.setB0127(null);
				b01.setB0131(null);
				b01.setB0124(null);
				b01.setB0238(null);
				b01.setB0239(null);
				b01.setB0150(0L);
				b01.setB0183(0L);
				b01.setB0185(0L);
				b01.setB0188(0L);
				b01.setB0189(0L);
				b01.setB0190(0L);
				b01.setB0191a(0L);
				b01.setB0192(0L);
				b01.setB0193(0L);
				b01.setB0227(0L);
				b01.setB0232(0L);
				b01.setB0233(0L);
				/*
				 * b01.setB0234(0L); b01.setB0235(0L); b01.setB0236(0L);
				 */
			}else{
				returnDO.setErrorMsg("22", "保存失败！");
				return returnDO;
			}
		}
		return returnDO.setObj(b01);
	}
	//校验机构
	public static ReturnDO<String> groupValidate(B01 b01){
		ReturnDO<String> returnDO = new ReturnDO<String>();
		String type = b01.getB0194();
		if(type.equals("LegalEntity")){
			type="1";
		}else if(type.equals("InnerOrg")){
			type="2";
		}else if(type.equals("GroupOrg")){
			type="3";
		}
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			if(type == null || type.equals("")){
				returnDO.setErrorMsg("88", "机构信息错误");
				return returnDO;
			}else{
				//获取父机构
				B01 b01Father = (B01)sess.get(B01.class, b01.getB0121());
				String fatherType = b01Father.getB0194();
				if(fatherType == null || fatherType.equals("")){
					returnDO.setErrorMsg("88", "机构信息错误");
					return returnDO;
				}
				if("1".equals(type)){
					if("2".equals(fatherType)){
						returnDO.setErrorMsg("88", "内设机构下不能创建法人单位！");
						return returnDO;
					}
				}else if("2".equals(type)){
					if("3".equals(fatherType)){
						returnDO.setErrorMsg("000000", "机构分组下不能创建内设机构！");
						return returnDO;
					}
					//判断下面
					String sql = "select distinct b.b0194 from b01 b where b.b0121 = '"+b01.getB0111()+"'";
					List<String> listTypes = HBUtil.getHBSession().createSQLQuery(sql).list(); 
					if(listTypes!=null || listTypes.size()>0){
						for(String types:listTypes){
							if("1".equals(types)){
								returnDO.setErrorMsg("88", "该机构包含法人单位，内设机构不能包含法人单位！");
								return returnDO;
							}else if("3".equals(types)){
								returnDO.setErrorMsg("88", "该机构包含机构分组，内设机构不能包含机构分组！");
								return returnDO;
							}
						}
					}
				}else if("3".equals(type)){
					if("2".equals(fatherType)){
						returnDO.setErrorMsg("88", "上级机构是内设机构，内设机构下不能包含机构分组！");
						return returnDO;
					}
					//判断下级
					String sql = "select count(1) from a02 b join a01 a on b.a0000=a.a0000 where a.status='1' and b.a0201b = '"+b01.getB0111()+"' and b. a0255='1'";
					String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
					sql = "select count(1) from a01 t where t.orgid = '"+b01.getB0111()+"' and t.status='3'";
					String str2 = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
					int count_temp = Integer.parseInt(str)+Integer.parseInt(str2);
					if(count_temp>0){
						returnDO.setErrorMsg("88", "机构分组中不能包含人员！");
						return returnDO;
					}
					//sql = "select distinct b.b0194 from b01 b where b.b0121 = '"+b01.getB0111()+"'";
					//List<String> listTypes = HBUtil.getHBSession().createSQLQuery(sql).list(); 
					//已经改成允许
//					if(listTypes!=null || listTypes.size()>0){
//						for(String types:listTypes){
//							if("2".equals(types)){
//								returnDO.setErrorMsg("88", "机构分组下不能包含内设机构！");
//								return returnDO;
//							}
//						}
//					}
				}else{
					returnDO.setErrorMsg("88", "机构校验失败！");
					return returnDO;	
				}
			}
			return returnDO;
		} catch (Exception e) {
			returnDO.setErrorMsg("88", "机构信息错误！");
			return returnDO;
		}
	}
	
	/**
	 * 新增or修改 机构修改专用
	 * @param b01dto
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AppException
	 * @throws RadowException
	 */
//	public static int UpdateB01(B01DTO b01dto, String type)
//			throws SQLException, IntrospectionException,
//			IllegalAccessException, InvocationTargetException, AppException,
//			RadowException {
//		CurrentUser user = SysUtil.getCacheCurrentUser();
//		if (b01dto.getB0180()!=null&&b01dto.getB0180().length() > 200) {
//			b01dto.setB0180(b01dto.getB0180().substring(0, 150));
//		}
//		                                                   
//		
//		b01dto.setStatus("1");
//		b01dto.setB0114(b01dto.getB0114().trim());//单位编码
//		b01dto.setB0121(b01dto.getB0121().trim());//上级单位id
//		b01dto.setB0101(b01dto.getB0101().trim());//机构名称
//		b01dto.setB0104(b01dto.getB0104().trim());//机构简称 
//		ChineseSpelling chineseSpelling = new ChineseSpelling();
//		b01dto.setB0107(chineseSpelling.getPYString(b01dto.getB0101().trim()));//机构简拼
//		if (b01dto.getB0111() != null && !"".equals(b01dto.getB0111())) {//机构主键// id存在，修改
//			b01dto.setUpdateuser(user.getId());
//			b01dto.setUpdatedate(DateUtil.getTimestamp().getTime());
//			
//			//B01 b02 = LoadB01(b01dto.getB0111());
//			
//			// B01 b01 = new B01();
//			
//			try {
//				//检验机构编码
//				String str = selectB01WithB0114CountByUpdate(b01dto.getB0114(),
//						b01dto.getB0111());
//				if (!str.equals("0")&&!"".equals(b01dto.getB0114())) {
//					throw new RadowException("存在相同的机构编码,请修改机构信息!");
//				}
//				//保存 
//				HBSession sess= HBUtil.getHBSession();
//				B01 b01=(B01)sess.get(B01.class, b01dto.getB0111().trim());
//				if (type.equals("1")) {////法人单位=-
//					b01.setB0194("1");//单位类型
//					
//				} else if (type.equals("2")) {//内设机构
//					b01.setB0194("2");
//				} else {////机构分组
//					b01.setB0194("3");
//				} 
//				//PropertyUtils.copyProperties(b01, b01dto);
//				sess.update(b01);
//				sess.flush();
//				//日志
//				new LogUtil().createLog("22", "B01", b01dto.getB0111(),
//						b01dto.getB0101(), "机构修改",
//						new Map2Temp().getLogInfo(b01, b01dto));
//				
//			} catch (RadowException e) {
//				e.printStackTrace();
//				throw new RadowException("存在相同的机构编码,请修改机构信息!");
//			}
////			HBUtil.getHBSession().update(b02);
////			HBUtil.getHBSession().flush();
//			
//			CreateSysOrgBS.updateB01UpdatedWithZero(b01dto.getB0111());
//
//			// saveInfo_Extend(HBUtil.getHBSession(),b02.getB0111());
//		}
//		return EventRtnType.NORMAL_SUCCESS;
//	}

	/**
	 * 新增or修改
	 * @param b01dto
	 * @param type
	 * @return
	 * @throws SQLException
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws AppException
	 * @throws RadowException
	 */
	public static int saveOrUpdateB01(B01DTO b01dto, String type)
			throws SQLException, IntrospectionException,
			IllegalAccessException, InvocationTargetException, AppException,
			RadowException {
		CurrentUser user = SysUtil.getCacheCurrentUser();
		if (b01dto.getB0180()!=null&&b01dto.getB0180().length() > 200) {
			b01dto.setB0180(b01dto.getB0180().substring(0, 150));
		}
		if (type.equals("1")) {////法人单位=-
				b01dto.setB0194("1");
		} else if (type.equals("2")) {//内设机构
			Long zero = (long) 0;
			b01dto.setB0194("2");
			b01dto.setB0117("");//机构所在政区 ZB01
			b01dto.setB0131("");//机构性质类别 ZB04 
			b01dto.setB0124("");//单位隶属关系 ZB87

			//b01dto.setB0192(zero);//内设机构正职非领导职数
			//b01dto.setB0193(zero);//内设机构副职非领导职数
			

			//b01dto.setB0188(zero);//同级正职非领导职数
			//b01dto.setB0189(zero);//同级副职非领导职数
			b01dto.setB0227(zero);//行政编制数 
			b01dto.setB0232(zero);//事业编制数(参公)
			b01dto.setB0233(zero);//事业编制数(其他)
			//b01dto.setB0235(zero);//政法专项编制数
			b01dto.setB0236("");//工勤编制数
			b01dto.setB0234("");//其 他 编 制 数
			
		} else {////机构分组
			b01dto.setB0194("3");
			b01dto.setB0117("");
			b01dto.setB0131("");
			b01dto.setB0124("");
			b01dto.setB0127("");
			Long zero = (long) 0;
			b01dto.setB0183(zero);//正职领导职数      
			b01dto.setB0185(zero);//副职领导职数      
			//b01dto.setB0188(zero);//同级正职非领导职数   
			//b01dto.setB0189(zero);//同级副职非领导职数   
			b01dto.setB0227(zero);//行政编制数       
			b01dto.setB0232(zero);//事业编制数(参公)   
			b01dto.setB0233(zero);//事业编制数(其他)   
		//	b01dto.setB0235(zero);//政法专项编制数     A
			/*
			 * b01dto.setB0236(zero);//工勤编制数 b01dto.setB0234(zero);//其 他 编 制 数
			 */			
			b01dto.setB0246(zero);
			b01dto.setB0247(zero);
			b01dto.setB0248(zero);
			b01dto.setB0249(zero);
			b01dto.setB0250(zero);
			b01dto.setB0256(zero);
			b01dto.setB0257(zero);
			b01dto.setB0258(zero);
			b01dto.setB0259(zero);
			b01dto.setB0260(zero);
			
			b01dto.setB0150(zero);//内设领导职数                   
			//b01dto.setB0190(zero);//内设机构正职领导职数               
			//b01dto.setB0191a(zero);//内设副职领导职数                
			//b01dto.setB0192(zero);//内设机构正职非领导职数              
			//b01dto.setB0193(zero);//内设机构副职非领导职数       
			b01dto.setB0238("");//参照公务员法管理审批时间
			b01dto.setB0239("");//参照公务员法管理审批文号
		}                                                    
		
		b01dto.setStatus("1");
		b01dto.setB0114(b01dto.getB0114().trim());//单位编码
		b01dto.setB0121(b01dto.getB0121().trim());//上级单位id
		b01dto.setB0101(b01dto.getB0101().trim());//机构名称
		b01dto.setB0104(b01dto.getB0104().trim());//机构简称 
		ChineseSpelling chineseSpelling = new ChineseSpelling();
		b01dto.setB0107(chineseSpelling.getPYString(b01dto.getB0101().trim()));//机构简拼
		if (b01dto.getB0111() != null && !"".equals(b01dto.getB0111())) {//机构主键// id存在，修改
			b01dto.setUpdateuser(user.getId());
			b01dto.setUpdatedate(DateUtil.getTimestamp().getTime());
			B01 b02 = LoadB01(b01dto.getB0111());
			// B01 b01 = new B01();
			new LogUtil().createLog("22", "B01", b01dto.getB0111(),
					b01dto.getB0101(), "机构修改",
					new Map2Temp().getLogInfo(b02, b01dto));
			try {
				PropertyUtils.copyProperties(b02, b01dto);
				String str = selectB01WithB0114CountByUpdate(b02.getB0114(),
						b02.getB0111());
				if (!str.equals("0")&&!"".equals(b02.getB0114())) {
					throw new RadowException("存在相同的机构编码,请修改机构信息!");
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RadowException e) {
				e.printStackTrace();
				throw new RadowException("存在相同的机构编码,请修改机构信息!");
			}
			HBUtil.getHBSession().update(b02);
			HBUtil.getHBSession().flush();
			CreateSysOrgBS.updateB01UpdatedWithZero(b02.getB0111());

			// saveInfo_Extend(HBUtil.getHBSession(),b02.getB0111());
			return 1;
		} else {// 新增
			b01dto.setCreateuser(user.getId());
			b01dto.setCreatedate(DateUtil.getTimestamp().getTime());
			b01dto.setSortid(insertSortId(b01dto.getB0121().trim()));
			
			/***********机构排序，新字段 2020年6月4日10:18:28 zoul********3位点3位，后面调整最后加1位字母校验位*******************************************************************/
			String sql = "select max(B0269) from B01 where b0121='"+b01dto.getB0121().trim()+"'";
			Object maxsort = HBUtil.getHBSession().createSQLQuery(sql).uniqueResult();
			String maxsortNUM = "";
			if(maxsort!=null&&!"".equals(maxsort.toString())){
				String[] numArray = maxsort.toString().split("\\.");
				String num = numArray[numArray.length-1];
				if(num.length()==4){
					maxsortNUM = (Integer.valueOf(num.substring(0, num.length()-1))+1)+num.substring(num.length()-1, num.length()-0);
					b01dto.setB0269(maxsort.toString().substring(0,maxsort.toString().length()-4)+StringUtils.leftPad((maxsortNUM), 4, "0"));
				}else{
					maxsortNUM = (Integer.valueOf(num)+1)+"";
					b01dto.setB0269(maxsort.toString().substring(0,maxsort.toString().length()-3)+StringUtils.leftPad((maxsortNUM), 3, "0"));
				}
				
			}else{
				b01dto.setB0269(HBUtil.getValueFromTab("B0269", "b01", "b0111='"+b01dto.getB0121()+"'")+".001A");
			}
			/***********机构排序，新字段 2020年6月4日10:18:28 zoul********3位点3位，后面调整最后加1位字母校验位*******************************************************************/ 
			
			b01dto.setB0111(selectB0111BySubId(b01dto.getB0121()));//如果上级为根机构，则生成 001.001
			if(b01dto.getB0111().equals("001.001")){//根机构下新建第一个机构
				String count = selectExistsById(b01dto.getB0111());
				if(count.equals("1")){
					throw new RadowException("仅能新建一个根机构！");
				}
			}
			SysOrgBS.getTime();
			B01 b01 = new B01();
			try {
				PropertyUtils.copyProperties(b01, b01dto);
				ReturnDO<String> returnDO = CreateSysOrgBS.groupValidate(b01);
				if(!returnDO.isSuccess()){
					throw new RadowException(returnDO.getErrorMsg());
				}
				String str = selectB01WithB0114Count(b01.getB0114());
				if (!str.equals("0")&&!"".equals(b01.getB0114())) {
					throw new RadowException("已存在相同的机构编码!");
				}
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RadowException("新建失败!");
			} catch (RadowException e) {
				e.printStackTrace();
				throw new RadowException("已存在相同的机构编码!");
			}
			HBUtil.getHBSession().save(b01);
			HBUtil.getHBSession().flush();
			//校验标识 UPDATED：0-未校验或校验未通过，1-校验通过
			updateB01UpdatedWithZero(b01.getB0111());//设置 UPDATED = 0
			// saveInfo_Extend(HBUtil.getHBSession(),b01.getB0111());
			SysOrgBS.getTime();
			new LogUtil().createLog("21", "B01", b01dto.getB0111(),
					b01dto.getB0101(), "机构新增",
					new Map2Temp().getLogInfo(new B01(), b01));
			SysOrgBS.getTime();
			return 1;
		}
	}

	// 查询机构编码是否存在数据库
	public static String selectExistsById(String id) throws RadowException {
		String sql = "select count(*) from b01 t where B0111='" + id.trim()
				+ "'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}
	
	// 查询同级机构是否重名
	public static void selectSubListByName(String name, String subid)
			throws RadowException {
		String sql = "from B01 where b0101 = '" + name.trim()
				+ "' and b0121= '" + subid.trim() + "'";
		List<B01> list = HBUtil.getHBSession().createQuery(sql).list();
		if (list.size() > 0) {
			throw new RadowException("当前机构名称库中已存在，请重新输入!");
		}
	}

	// 特殊处理 （根据BUG需求单 其他人删除机构，此机构点击保存时的错误提示）
	public static void selectCountById(String id) throws RadowException {
		String sql = "select count(*) from b01 t where B0111='" + id.trim()
				+ "'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		if (str.equals("0")) {
			throw new RadowException("当前机构不存在数据库中!");
		}
	}

	// 修改时效验是否同名
	public static void selectListByNameForUpdate(String name, String id)
			throws RadowException {
		selectCountById(id);
		B01 b01 = LoadB01(id);
		String sql = "select * from B01 where b0101 = '" + name.trim()
				+ "' and b0121= '" + b01.getB0121()
				+ "' and not exists (select * from B01 t where t.b0111 ='"
				+ id.trim() + "')";
		List<B01> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if (list.size() > 0) {
			throw new RadowException("当前机构名称库中已存在，请重新输入!");
		}
	}
	
	public static String getB0111Three(String subId,String b0111) throws AppException, RadowException{
		String tmp=HBUtil.getValueFromTab("count(*)", "b01", "b0111='"+subId + "." + b0111+"' ");
		if(tmp.equals("1")){//主键重复、重新生成
			//继续+1
			b0111 = getB0111(b0111);
			b0111=getB0111Three(subId,b0111);
			return b0111;
		}else{
			return b0111;
		}
		
	}

	// 通过上级编码 生成主键
	public static String selectB0111BySubId(String subId) throws AppException, RadowException {
		if (subId.equals("-1")) {
			return "001.001";
		} else {
			String sql = "select max(substr(t.b0111,-3,3)) from  b01 t where t.B0121='"
					+ subId.trim() + "'";
			Object substrb0111 = (Object) HBUtil.getHBSession()
					.createSQLQuery(sql).uniqueResult();
			if (substrb0111 != null) {
				String b0111 = getB0111(substrb0111.toString());
				b0111=getB0111Three(subId,b0111);//去重 
				return subId + "." + b0111;
			} else {
				return subId + ".001";
			}
		}
	}
	

	// 主键生成方式
	public static String getB0111(String str) throws RadowException {
		str = str.toUpperCase();
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		// System.out.println(str.substring(2));//个位数
		// System.out.println(str.substring(1,2));//十位数
		// System.out.println(str.substring(0,1));//百位数
		// 如果个位数不为Z 那么+1
		if (!str.substring(2).equals("Z")) {
			for (int i = 0; i < key.length; i++) {
				if (key[i].equals(str.substring(2))) {
					return str.substring(0, 2) + key[i + 1];
				}
			}
		} else {
			// 个位是Z 十位数不是Z,那么十位数+1,个位置变为0
			if (!str.substring(1, 2).equals("Z")) {
				for (int i = 0; i < key.length; i++) {
					if (key[i].equals(str.substring(1, 2))) {
						return str.substring(0, 1) + key[i + 1] + "0";
					}
				}
			} else {
				// 个位是Z 十位数是Z,那么十位数+1,个位置变为0
				for (int i = 0; i < key.length; i++) {
					if (key[i].equals(str.substring(0, 1))) {
						return key[i + 1] + "00";
					}
				}
			}
		}
		throw new RadowException("该机构主键不规范："+str);
	}

	// 机构排序字段生成
	public static Long insertSortId(String id) {
		String sql = "select max(t.sortid)+1 sortid from b01 t where t.b0121='"
				+ id.trim() + "'";
		List list = HBUtil.getHBSession().createSQLQuery(sql).list();
		String sortid = "1";
		if (list.get(0) == null) {
			sortid = "1";
		} else {
			sortid = list.get(0).toString();
		}
		return Long.parseLong(sortid);
	}

	// 正职领导
	public static String selectRightLeaderCount(String id)
			throws RadowException {
		//在任 ，班子成员，正职，领导
		String sql = "select Count(distinct a0000) from a01"
				+ "  where a0000 in "
						+ " (Select a0000 From A02 t "
						+ " where t.a0255='1' "//任职状态   等
						+ " and t.a0201b='"+id.trim()+"' "//任职机构代码   等
						+ " and t.a0201d='1' "//是否班子成员
						+ " and t.a0201e='1' "//班子成员类别   等 //1 正职 3 副职 Z其他
						+ " and t.a0219='1')"//是否领导职务   等
						;
//		 String
//		 sql="Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b='"+id.trim()+"'  and t.a0201e='1' and t.a0219='1'";
//		String sql = "Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
//				+"  and t.a0201e='1' and t.a0219='1' and t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 副职领导
	public static String selectViceLeaderCount(String id) throws RadowException {
		//在任 ，班子成员，正职，领导
		String sql = "select Count(distinct a0000) "
				+ " from a01 "
				+ " where a0000 in "
						+ " (Select a0000 "
						+ " From A02 t "
						+ " where t.a0255='1' "//任职状态   等
						+ " and t.a0201b='"+id.trim()+"' "//任职机构代码   等
						+ " and t.a0201d='1' "//是否班子成员   等
						+ " and t.a0201e='3' "//1 正职 3 副职 Z其他//班子成员类别 
						+ " and t.a0219='1'"//是否领导职务   等
						+ " )";
//		 String
//		 sql="Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b='"+id.trim()+"'  and t.a0201e='3' and t.a0219='1'";
//		String sql = "Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
//				 + "  and t.a0201e='3' and t.a0219='1' and t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 同级正职非领导
	public static String selectRightNoLeaderCount(String id)
			throws RadowException {
		//在任 ，班子成员，副职，领导
		String sql = "select Count(distinct a0000) from a01 where a0000 in(Select a0000 From A02 t where t.a0255='1' and t.a0201d='1' and t.a0201b='"+id.trim()+"'  and t.a0201e='1' and t.a0219='2')";
//		 String
//		 sql="Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b='"+id.trim()+"' and t.a0201e='1' and t.a0219='2'";
//		String sql = "Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
//				+ " and t.a0201e='1' and t.a0219='2' and t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 同级副职非领导
	public static String selectViceNoLeaderCount(String id)
			throws RadowException {
		//在任 ，班子成员，副职，非领导领导
		String sql = "select Count(distinct a0000) from a01 where a0000 in(Select a0000 From A02 t where t.a0255='1' and t.a0201d='1' and t.a0201b='"+id.trim()+"'  and t.a0201e='3' and t.a0219='2')";
//		String
//		 sql="Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b='"+id.trim()+"' and t.a0201e='3' and t.a0219='2'";
//		String sql = "Select Count(distinct t.a0000) From A02 t where 1=1 and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
//				 + " and t.a0201e='3' and t.a0219='2' and a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	/*// 行政编制
	public static String selectCivilServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where  t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 ='1' and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 参照公务员法管理事业编制
	public static String selectLikeCivilServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 ='2' and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 其他事业编制
	public static String selectCareerServantCount(String id)
			throws RadowException {
		String sql = "Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and t.a0201b in(select b0111 from b01 where (B0121='"+id+"' and b0194 = '2') or B0111='"+id+"'  ) "
				
				+ " and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 in ('3','4') and b.a0163='1')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 其他(没用)
	public static String selectOtherCount(String id) throws RadowException {
		String sql = "Select Count(distinct t.a0000) From a02 t where t.a0255='1' and t.a0201b='"
				+ id.trim()
				+ "' and exists (select * from a01 b where t.a0000=b.a0000 and b.A0121 ='4')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}*/
	
	/**
	 * @author 朱文忠
	 * @param id 单位ID
	 * @param type  编制类型
	 * @return 编制数
	 * @throws RadowException
	 */
	public static String getServantCounts(String id,String type) throws RadowException{
		String sql = " select count(distinct a.a0000) "
				+ " from a01 a,a02 b "
				+ " where a.a0000=b.a0000 "
					+ " and a.a0195 = '"+id+"' "//统计关系所在单位   等
					+ " and a.a0121 = '"+type+"' "//编制类型   等
					+ " and b.a0255 = '1' ";//任职状态   等
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}

	public static String selectLowerLevelInsideSysOrgCount(String id)
			throws RadowException {
		String sql = "Select Count(1) From b01 t where t.b0121 ='" + id.trim()
				+ "' and t.b0194='2'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	//内设领导职数
	public static String selectLowerLevelLeaderCount(String id) throws RadowException{
		String sql="Select Count(distinct t.a0000) From a02 t where  t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1') and t.a0219='1' and t.a0201b in (select b.b0111 from b01 b where b.b0194='2' and b.b0121 = '"+id.trim()+"')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//正职
	public static String selectLowerLevelRightCount(String id) throws RadowException{
		String sql="Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1') and t.a0219='1' and t.a0201e='1' and t.a0201b in (select b.b0111 from b01 b where b.b0194='2' and b.b0121 = '"+id.trim()+"')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//副职
	public static String selectLowerLevelViceCount(String id) throws RadowException{
		String sql="Select Count(distinct t.a0000) From  a02 t where t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1') and t.a0219='1' and t.a0201e='3' and t.a0201b in (select b.b0111 from b01 b where b.b0194='2' and b.b0121 = '"+id.trim()+"')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//正职非领导
	public static String selectLowerLevelRightNoLeaderCount(String id) throws RadowException{
		String sql="Select Count(distinct t.a0000) From a02 t where t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1') and t.a0201e='1' and t.a0219='2' and t.a0201b in (select b.b0111 from b01 b where b.b0194='2' and b.b0121 = '"+id.trim()+"')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	//副职非领导
	public static String selectLowerLevelViceNoLeaderCount(String id) throws RadowException{
		String sql="Select Count(distinct t.a0000) From a02 t where t.a0255='1' and exists (select * from a01 b where b.a0000=t.a0000 and b.a0163='1') and t.a0201e='3' and t.a0219='2' and t.a0201b in (select b.b0111 from b01 b where b.b0194='2' and b.b0121 = '"+id.trim()+"')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
	
	public static Map<String, List<Object[]>> getB01Ext() {
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			List<Object[]> list = getB01ExtSQL();
			Map<String, List<Object[]>> info_map = new LinkedHashMap<String, List<Object[]>>();
			if (list != null && list.size() > 0) {
				for (Object[] os : list) {
					List<Object[]> os_list = info_map
							.get(os[0] + "___" + os[4]);
					if (os_list == null) {
						os_list = new ArrayList<Object[]>();
						os_list.add(os);
						info_map.put(
								os[0].toString() + "___" + os[4].toString(),
								os_list);
					} else {
						os_list.add(os);
					}

				}
			}
			return info_map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Object[]> getB01ExtSQL() {
		String sql = "select t.table_code, t.col_code,t.col_name,t.code_type,a.add_type_name,t.col_data_type_should "
				+ " from (select ctc.table_code, ctc.col_code,ctc.col_name,ctc.code_type,"
				+ " ctc.col_data_type_should,ctc.is_new_code_col,av.isused from code_table_col ctc "
				+ " left join add_value av on ctc.col_code=av.col_code) t "
				+ " left join add_type a on t.table_code=a.table_code where t.is_new_code_col='1' and t.isused='1' and t.table_code = 'B01'";
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		return list;
	}

	// 新增时查询法人单位编码是否重复
	public static String selectB01WithB0114Count(String b0114)
			throws RadowException {
		String sql = "Select Count(1) From b01 t where t.b0114='"
				+ b0114.trim() + "'";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// 修改时时查询法人单位编码是否重复
	public static String selectB01WithB0114CountByUpdate(String b0114,
			String b0111) throws RadowException {
		String sql = "Select Count(1) From b01 t where t.b0114='"
				+ b0114.trim()
				+ "' and not exists (select * from b01 b where b.b0111=t.b0111 and b.b0111='"
				+ b0111 + "')";
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0)
				.toString();
		return str;
	}

	// CreateSysOrgBS.updateB01UpdatedWithZero(b0111);
	// 修改校验标记
	public static void updateB01UpdatedWithZero(String b0111)
			throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		// 修改本级的效验标记为0
		if(!StringUtil.isEmpty(b0111)){
			String sql = "update B01 t set t.updated = '0' where t.b0111 = '"
					+ b0111 + "'";
			sess.createSQLQuery(sql).executeUpdate();
		}
	}
	
	// 查询该机构下是否有人
	public static String validationGroup(String b0111) throws RadowException {
		String sql = "Select Count(1) From a02 t where t.a0201b='"+b0111+"' and a0255='1'";//判断机构下是否有人员，增加该机构在在职状态
		String str = HBUtil.getHBSession().createSQLQuery(sql).list().get(0).toString();
		return str;
	}
}