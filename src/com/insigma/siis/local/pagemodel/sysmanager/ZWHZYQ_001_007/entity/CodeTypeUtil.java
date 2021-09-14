package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.siis.local.business.entity.Param;

public class CodeTypeUtil {
	private static Map<String, String> codetypeMaping = new HashMap<String, String>();
	
	static{
		put("A0104", "GB2261");  //性别 
		put("A0117", "GB3304");  //民族
		put("A0160", "ZB125");   //人员类别
		put("A0163", "ZB126");	//人员管理状态
		put("A0165", "ZB130");	//管理类别
		put("A0192D", "ZB133");	//职级
		put("A0120", "ZB134");	//级别
		put("A0121", "ZB135");	//编制类型
		put("A0122", "ZB139");	//专业技术类公务员任职资格
		put("A0201E", "ZB129");	//班子成员类别
		put("A0215A", "ZB08");	//职务名称
		put("A0219", "ZB42");	//是否领导职务
		put("A0221", "ZB09");	//职务层次
		put("A0222", "ZB127");	//岗位类别
		put("A0247", "ZB122");	//任职方式
		put("A0251", "ZB13");	//任职变动类别
		put("A0255", "ZB14");	//任职状态
		put("A0271", "ZB16");	//免职原因类别
		put("A4901", "ZB72");	//交流方式
		put("A4904", "ZB73");	//交流原因
		put("A4907", "ZB74");	//交流去向
		put("A0221A", "ZB136");	//职务等级
		put("A0601", "GB8561");	//专业技术任职资格
		put("A0607", "ZB24");	//取得资格途径
		put("A0801B", "ZB64");	//学历代码
		put("A0901B", "GB6864");	//学位代码
		put("A0827", "GB16835");	//所学专业类别
		put("A0837", "ZB123");	//教育类别
		put("A1101", "ZB29");	//培训类别
		put("A1104", "ZB30");	//培训离岗状态
		put("A1127", "ZB27");	//培训机构类别
		put("A1404B", "ZB65");	//奖惩名称代码
		put("A1414", "ZB03");	//批准奖惩机关级别
		put("A1415", "ZB09");	//奖惩时职务层次
		put("A1428", "ZB128");	//批准机关性质
		put("A1517", "ZB18");	//考核结论类别
		put("A2911", "ZB77");	//进入本单位变动类别
//		put("A2921B", "ZB74");	//进入本单位前工作单位所在地
//		put("A2921C", "ZB140");	//进入工作单位前工作单位性质
//		put("A2921D", "ZB141");	//进入工作单位前工作单位层次
//		put("A2970", "ZB137");	//选调生
//		put("A2970A", "ZB138");	//选调生来源
		put("A3001", "ZB78");	//退出本单位变动类别
		put("A3101", "ZB132");	//离退类别
		put("A3107", "ZB09");	//离退前职务层次
		put("A3108", "ZB133");	//离退前职级
		put("A3109", "ZB136");	//离退前职务等级
		put("A3110", "ZB134");	//离退前级别
		put("A0141", "GB4762");	//政治面貌
		put("A3921", "GB4762");	//第二党派
		put("A3927", "GB4762");	//第三党派
		//put("B0111", "ZB02");	//单位编码
		put("B0194", "B0194");	//单位性质类别
		put("B0117", "ZB01");	//单位所在政区
		put("B0124", "ZB87");	//单位隶属关系
		put("B0127", "ZB03");	//单位级别
		put("B0131", "ZB04");	//单位性质类别
		put("A0281", "BOOLEAN");//输出标识
/*		put("A3140","ZB09");	//离退前职务层次
		put("A3142","ZB133");	//离退前职级
		put("A3141","ZB136");	//离退前职务等级
*/		put("A0111A","ZB01");
		put("A0114A", "ZB01");
		
		/*业务信息群信息项补充*/
		put("A6005", "ZB64");
		put("A6007", "GB6864");
		put("A6009", "ZB146");
		put("A6010", "ZB147");
		put("A6005", "ZB64");
		put("A2970", "ZB137");	//选调生
		put("A2970A", "ZB138");	//选调生来源
		put("A6109", "ZB64");
		put("A6111", "GB6864");
		put("A6202", "ZB142");
		put("A6203", "ZB143");
		put("A6204", "ZB141");
		put("A6302", "ZB142");
		put("A6303", "ZB144");
		put("A6304", "ZB141");
		put("A6305", "ZB145");
	}
	
	public static String get(String key){
		return codetypeMaping.get(key);
	}
	
	private static void put(String key,String value){
		codetypeMaping.put(key , value);
	}
	
	
	/**
	 * 根据参数获取CodeValue值  机构的代码单独判断
	 * @param param
	 * @return 返回null 等于返回错误信息
	 */
	public static Param getCodeValue(Param param){
		/*if(1==1){//做测试暂时先通过
			return param;
		}*/
		if("".equals(param.getValue())){
			return param;
		}
		String codetype = CodeTypeUtil.get(param.getName().toUpperCase());
		if(codetype==null){
			return param;
		}
		List<String> list = new ArrayList<String>();
		if(param.getName().equals("A0111A") || param.getName().equals("A0114A")){
			 list = HBUtil.getHBSession().createSQLQuery(
					"select code_value from Code_Value where code_type='"+codetype+"'" +
							" and code_status='1' and code_name3='"+param.getValue()+"'").list();
		}else{
			 list = HBUtil.getHBSession().createSQLQuery(
					"select code_value from Code_Value where code_type='"+codetype+"'" +
							" and code_status='1' and code_name='"+param.getValue()+"'").list();
		}
		
		if (list==null||list.size()==0) {
			return null;
		}
		param.setValue(list.get(0).toString());
		return param;
	}
	
	
	/**
	 * 
	 * @param b
	 * @return
	 * @throws Exception 
	 */
	public static byte[]  getPhotoBase64(String b) throws Exception{
		if(b==null||b.length()==0){
			throw new Exception("图片数据不能为空！");
		}
		//对字节数组Base64编码  
		BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(b);//返回Base64编码过的字节数组字符串
	}
	
	
}
