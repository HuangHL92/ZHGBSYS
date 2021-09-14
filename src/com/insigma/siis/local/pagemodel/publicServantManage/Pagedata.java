package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.HashMap;

public class Pagedata {
	public static HashMap<String,HashMap<String,String>> map;
	/*---------------------------------------学历学位---------------------------------------------*/
	public static HashMap<String, String> mapa0837 = new HashMap<String, String>();
	static {
		mapa0837.put("dataIndex", "a0837");
		mapa0837.put("header", "教育类别");
		mapa0837.put("width", "120");
		mapa0837.put("edited", "false");
		mapa0837.put("editor", "select");
		mapa0837.put("required", "true");
		mapa0837.put("codeType", "ZB123");
		mapa0837.put("editorId", "a0837");
		mapa0837.put("onSelect", "");
		mapa0837.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapa0801b = new HashMap<String, String>();
	static {
		mapa0801b.put("dataIndex", "a0801b");
		mapa0801b.put("header", "学历代码");
		mapa0801b.put("width", "120");
		mapa0801b.put("edited", "false");
		mapa0801b.put("editor", "select");
		mapa0801b.put("required", "true");
		mapa0801b.put("codeType", "ZB64");
		mapa0801b.put("editorId", "a0801b");
		mapa0801b.put("onSelect", "setA0801bValue");
		mapa0801b.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapa0801a = new HashMap<String, String>();
	static {
		mapa0801a.put("dataIndex", "a0801a");
		mapa0801a.put("header", "学历名称");
		mapa0801a.put("width", "120");
		mapa0801a.put("edited", "true");
		mapa0801a.put("editor", "text");
		mapa0801a.put("required", "true");
		mapa0801a.put("codeType", "");
		mapa0801a.put("editorId", "a0801a");
		mapa0801a.put("onSelect", "");
		mapa0801a.put("onKeyDown", "");
	}
	
	
	/* <odin:gridEditColumn2 dataIndex="a0837" header="教育类别" editorId="a0837" align="center" width="30" codeType="ZB123" edited="true" editor="select" required="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0801b" header="学历代码" onSelect="setA0801bValue" editorId="a0801b" align="center" width="30" codeType="ZB64" edited="true" editor="select" required="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0801a" header="学历名称" align="center" width="30" edited="true" editor="text" required="true" sortable="false" codeType="ZB01" />
	<odin:gridEditColumn2 dataIndex="a0811" header="学制年限（年）" align="center" width="40" edited="true" editor="number" sortable="false" maxLength="3" />
	<odin:gridEditColumn2 dataIndex="a0901b" header="学位代码" onSelect="setA0901bValue" editorId="a0901b"  align="center" width="30" codeType="GB6864" edited="true" editor="select" required="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0901a" header="学位名称" align="center" width="30" required="true" edited="true" editor="text" sortable="false" codeType="ZB130" />
	<odin:gridEditColumn2 dataIndex="a0814" header="学校（单位）名称" align="center" width="30" editor="text" edited="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0827" header="所学专业类别" align="center" onSelect="setA0824bValue" editorId="a0827" width="30" codeType="GB16835" editor="select" required="false" edited="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0824" header="所学专业名称" align="center" editorId="a0824" width="30" editor="text" edited="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0804" header="入学时间" align="center" width="30" editor="text" edited="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0807" header="毕（肄）业时间" align="center" width="30" editor="text" edited="true" sortable="false" />
	<odin:gridEditColumn2 dataIndex="a0904" header="学位授予时间" align="center" width="30" editor="text" edited="true" sortable="false" isLast="true" /> */
	
	
	
	
	
	
	
	
	
	
	/*--------------------------------------人员基本信息-----------------------------------------*/
	public static HashMap<String, String> mapA0101 = new HashMap<String, String>();
	static {
		mapA0101.put("dataIndex", "A0101");
		mapA0101.put("header", "姓名");
		mapA0101.put("width", "80");
		mapA0101.put("edited", "true");
		mapA0101.put("editor", "text");
		mapA0101.put("required", "true");
		mapA0101.put("codeType", "");
		mapA0101.put("editorId", "A0101");
		mapA0101.put("onSelect", "");
		mapA0101.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0184 = new HashMap<String, String>();
	static {
		mapA0184.put("dataIndex", "A0184");
		mapA0184.put("header", "身份证号");
		mapA0184.put("width", "200");
		mapA0184.put("edited", "true");
		mapA0184.put("editor", "text");
		mapA0184.put("required", "true");
		mapA0184.put("codeType", "");
		mapA0184.put("editorId", "A0184");
		mapA0184.put("onSelect", "");
		mapA0184.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0104 = new HashMap<String, String>();
	static {
		mapA0104.put("dataIndex", "A0104");
		mapA0104.put("header", "性别");
		mapA0104.put("width", "50");
		mapA0104.put("edited", "false");
		mapA0104.put("editor", "select");
		mapA0104.put("required", "true");
		mapA0104.put("codeType", "GB2261");
		mapA0104.put("editorId", "A0104");
		mapA0104.put("onSelect", "");
		mapA0104.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0107 = new HashMap<String, String>();
	static {
		mapA0107.put("dataIndex", "A0107");
		mapA0107.put("header", "出生年月");
		mapA0107.put("width", "150");
		mapA0107.put("edited", "true");
		mapA0107.put("editor", "text");
		mapA0107.put("required", "true");
		mapA0107.put("codeType", "");
		mapA0107.put("editorId", "A0107");
		mapA0107.put("onSelect", "");
		mapA0107.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0117 = new HashMap<String, String>();
	static {
		mapA0117.put("dataIndex", "A0117");
		mapA0117.put("header", "民族");
		mapA0117.put("width", "80");
		mapA0117.put("edited", "false");
		mapA0117.put("editor", "select");
		mapA0117.put("required", "true");
		mapA0117.put("codeType", "GB3304");
		mapA0117.put("editorId", "A0117");
		mapA0117.put("onSelect", "");
		mapA0117.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0111A = new HashMap<String, String>();
	static {
		mapA0111A.put("dataIndex", "A0111A");
		mapA0111A.put("header", "籍贯");
		mapA0111A.put("width", "80");
		mapA0111A.put("edited", "false");
		mapA0111A.put("editor", "selectTree");
		mapA0111A.put("required", "true");
		mapA0111A.put("codeType", "ZB01");
		mapA0111A.put("editorId", "A0111A");
		mapA0111A.put("onSelect", "");
		mapA0111A.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0114A = new HashMap<String, String>();
	static {
		mapA0114A.put("dataIndex", "A0114A");
		mapA0114A.put("header", "出生地");
		mapA0114A.put("width", "120");
		mapA0114A.put("edited", "false");
		mapA0114A.put("editor", "selectTree");
		mapA0114A.put("required", "true");
		mapA0114A.put("codeType", "ZB01");
		mapA0114A.put("editorId", "A0114A");
		mapA0114A.put("onSelect", "");
		mapA0114A.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0141 = new HashMap<String, String>();
	static {
		mapA0141.put("dataIndex", "A0141");
		mapA0141.put("header", "政治面貌");
		mapA0141.put("width", "150");
		mapA0141.put("edited", "true");
		mapA0141.put("editor", "select");
		mapA0141.put("required", "true");
		mapA0141.put("codeType", "GB4762");
		mapA0141.put("editorId", "A0141");
		mapA0141.put("onSelect", "verifyA0141");
		mapA0141.put("onKeyDown", "verifyA0141");
	}
	public static HashMap<String, String> mapA0140 = new HashMap<String, String>();
	static {
		mapA0140.put("dataIndex", "A0140");
		mapA0140.put("header", "入党时间");
		mapA0140.put("width", "150");
		mapA0140.put("edited", "true");
		mapA0140.put("editor", "text");
		mapA0140.put("required", "true");
		mapA0140.put("codeType", "");
		mapA0140.put("editorId", "A0140");
		mapA0140.put("onSelect", "");
		mapA0140.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA3921 = new HashMap<String, String>();
	static {
		mapA3921.put("dataIndex", "A3921");
		mapA3921.put("header", "第二党派");
		mapA3921.put("width", "150");
		mapA3921.put("edited", "false");
		mapA3921.put("editor", "select");
		mapA3921.put("required", "false");
		mapA3921.put("codeType", "GB4762");
		mapA3921.put("editorId", "A3921");
		mapA3921.put("onSelect", "verifyA3921");
		mapA3921.put("onKeyDown", "verifyA3921");
	}
	public static HashMap<String, String> mapA3927 = new HashMap<String, String>();
	static {
		mapA3927.put("dataIndex", "A3927");
		mapA3927.put("header", "第三党派");
		mapA3927.put("width", "150");
		mapA3927.put("edited", "false");
		mapA3927.put("editor", "select");
		mapA3927.put("required", "false");
		mapA3927.put("codeType", "GB4762");
		mapA3927.put("editorId", "A3927");
		mapA3927.put("onSelect", "verifyA3927");
		mapA3927.put("onKeyDown", "verifyA3927");
	}
	public static HashMap<String, String> mapA0195 = new HashMap<String, String>();
	static {
		mapA0195.put("dataIndex", "A0195");
		mapA0195.put("header", "统计关系所在单位");
		mapA0195.put("width", "150");
		mapA0195.put("edited", "false");
		mapA0195.put("editor", "select");
		mapA0195.put("required", "true");
		mapA0195.put("codeType", "");
		mapA0195.put("editorId", "A0195");
		mapA0195.put("onSelect", "");
		mapA0195.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA2949 = new HashMap<String, String>();
	static {
		mapA2949.put("dataIndex", "A2949");
		mapA2949.put("header", "公务员登记时间");
		mapA2949.put("width", "150");
		mapA2949.put("edited", "true");
		mapA2949.put("editor", "text");
		mapA2949.put("required", "");
		mapA2949.put("codeType", "");
		mapA2949.put("editorId", "A2949");
		mapA2949.put("onSelect", "");
		mapA2949.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0120 = new HashMap<String, String>();
	static {
		mapA0120.put("dataIndex", "A0120");
		mapA0120.put("header", "级别");
		mapA0120.put("width", "80");
		mapA0120.put("edited", "false");
		mapA0120.put("editor", "select");
		mapA0120.put("required", "false");
		mapA0120.put("codeType", "ZB134");
		mapA0120.put("editorId", "A0120");
		mapA0120.put("onSelect", "");
		mapA0120.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0115A = new HashMap<String, String>();
	static {
		mapA0115A.put("dataIndex", "A0115A");
		mapA0115A.put("header", "成长地");
		mapA0115A.put("width", "120");
		mapA0115A.put("edited", "false");
		mapA0115A.put("editor", "selectTree");
		mapA0115A.put("required", "false");
		mapA0115A.put("codeType", "ZB01");
		mapA0115A.put("editorId", "A0115A");
		mapA0115A.put("onSelect", "");
		mapA0115A.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0122 = new HashMap<String, String>();
	static {
		mapA0122.put("dataIndex", "A0122");
		mapA0122.put("header", "专业技术类公务员任职资格");
		mapA0122.put("width", "200");
		mapA0122.put("edited", "false");
		mapA0122.put("editor", "select");
		mapA0122.put("required", "false");
		mapA0122.put("codeType", "ZB139");
		mapA0122.put("editorId", "A0122");
		mapA0122.put("onSelect", "");
		mapA0122.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0134 = new HashMap<String, String>();
	static {
		mapA0134.put("dataIndex", "A0134");
		mapA0134.put("header", "参加工作时间");
		mapA0134.put("width", "200");
		mapA0134.put("edited", "true");
		mapA0134.put("editor", "text");
		mapA0134.put("required", "true");
		mapA0134.put("codeType", "");
		mapA0134.put("editorId", "A0134");
		mapA0134.put("onSelect", "");
		mapA0134.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0128 = new HashMap<String, String>();
	static {
		mapA0128.put("dataIndex", "A0128");
		mapA0128.put("header", "健康状况");
		mapA0128.put("width", "80");
		mapA0128.put("edited", "false");
		mapA0128.put("editor", "select");
		mapA0128.put("required", "true");
		mapA0128.put("codeType", "GB2261D");
		mapA0128.put("editorId", "A0128");
		mapA0128.put("onSelect", "");
		mapA0128.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0187A = new HashMap<String, String>();
	static {
		mapA0187A.put("dataIndex", "A0187A");
		mapA0187A.put("header", "专长");
		mapA0187A.put("width", "200");
		mapA0187A.put("edited", "true");
		mapA0187A.put("editor", "text");
		mapA0187A.put("required", "false");
		mapA0187A.put("codeType", "");
		mapA0187A.put("editorId", "A0187A");
		mapA0187A.put("onSelect", "");
		mapA0187A.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0160 = new HashMap<String, String>();
	static {
		mapA0160.put("dataIndex", "A0160");
		mapA0160.put("header", "人员类别");
		mapA0160.put("width", "120");
		mapA0160.put("edited", "false");
		mapA0160.put("editor", "select");
		mapA0160.put("required", "true");
		mapA0160.put("codeType", "ZB125");
		mapA0160.put("editorId", "A0160");
		mapA0160.put("onSelect", "");
		mapA0160.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0165 = new HashMap<String, String>();
	static {
	    mapA0165.put("dataIndex", "A0165");
	    mapA0165.put("header", "管理类别");
	    mapA0165.put("width", "120");
	    mapA0165.put("edited", "false");
	    mapA0165.put("editor", "select");
	    mapA0165.put("required", "true");
	    mapA0165.put("codeType", "ZB130");
	    mapA0165.put("editorId", "A0165");
	    mapA0165.put("onSelect", "");
	    mapA0165.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0121 = new HashMap<String, String>();
	static {
		mapA0121.put("dataIndex", "A0121");
		mapA0121.put("header", "编制类型");
		mapA0121.put("width", "120");
		mapA0121.put("edited", "false");
		mapA0121.put("editor", "select");
		mapA0121.put("required", "true");
		mapA0121.put("codeType", "ZB135");
		mapA0121.put("editorId", "A0121");
		mapA0121.put("onSelect", "");
		mapA0121.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0180 = new HashMap<String, String>();
	static {
		mapA0180.put("dataIndex", "A0180");
		mapA0180.put("header", "备注");
		mapA0180.put("width", "200");
		mapA0180.put("edited", "true");
		mapA0180.put("editor", "text");
		mapA0180.put("required", "false");
		mapA0180.put("codeType", "");
		mapA0180.put("editorId", "A0180");
		mapA0180.put("onSelect", "");
		mapA0180.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0201b = new HashMap<String, String>();
	static {
		mapA0201b.put("dataIndex", "a0201b");
		mapA0201b.put("header", "任职机构");
		mapA0201b.put("width", "30");
		mapA0201b.put("edited", "true");
		mapA0201b.put("editor", "select");
		mapA0201b.put("required", "true");
		mapA0201b.put("codeType", "");
		mapA0201b.put("editorId", "a0201b");
		mapA0201b.put("onSelect", "isA0201b");
		mapA0201b.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0201a = new HashMap<String, String>();
	static {
		mapA0201a.put("dataIndex", "a0201a");
		mapA0201a.put("header", "任职机构名称");
		mapA0201a.put("width", "40");
		mapA0201a.put("edited", "true");
		mapA0201a.put("editor", "text");
		mapA0201a.put("required", "true");
		mapA0201a.put("codeType", "");
		mapA0201a.put("editorId", "a0201a");
		mapA0201a.put("onSelect", "");
		mapA0201a.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0215a = new HashMap<String, String>();
	static {
		mapA0215a.put("dataIndex", "a0215a");
		mapA0215a.put("header", "职务名称");
		mapA0215a.put("width", "30");
		mapA0215a.put("edited", "true");
		mapA0215a.put("editor", "text");
		mapA0215a.put("required", "true");
		mapA0215a.put("codeType", "");
		mapA0215a.put("editorId", "a0215a");
		mapA0215a.put("onSelect", "");
		mapA0215a.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0255 = new HashMap<String, String>();
	static {
		mapA0255.put("dataIndex", "a0255");
		mapA0255.put("header", "任免状态");
		mapA0255.put("width", "30");
		mapA0255.put("edited", "true");
		mapA0255.put("editor", "select");
		mapA0255.put("required", "true");
		mapA0255.put("codeType", "ZB14");
		mapA0255.put("editorId", "a0255");
		mapA0255.put("onSelect", "officeState");
		mapA0255.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0279 = new HashMap<String, String>();
	static {
		mapA0279.put("dataIndex", "a0279");
		mapA0279.put("header", "主职务");
		mapA0279.put("width", "20");
		mapA0279.put("edited", "true");
		mapA0279.put("editor", "select");
		mapA0279.put("required", "true");
		mapA0279.put("codeType", "XZ09");
		mapA0279.put("editorId", "a0279");
		mapA0279.put("onSelect", "");
		mapA0279.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0219 = new HashMap<String, String>();
	static {
		mapA0219.put("dataIndex", "a0219");
		mapA0219.put("header", "领导职务");
		mapA0219.put("width", "30");
		mapA0219.put("edited", "true");
		mapA0219.put("editor", "select");
		mapA0219.put("required", "false");
		mapA0219.put("codeType", "XZ09");
		mapA0219.put("editorId", "a0219");
		mapA0219.put("onSelect", "");
		mapA0219.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0201d = new HashMap<String, String>();
	static {
		mapA0201d.put("dataIndex", "a0201d");
		mapA0201d.put("header", "领导成员");
		mapA0201d.put("width", "30");
		mapA0201d.put("edited", "true");
		mapA0201d.put("editor", "select");
		mapA0201d.put("required", "false");
		mapA0201d.put("codeType", "VSC007");
		mapA0201d.put("editorId", "a0201d");
		mapA0201d.put("onSelect", "setA0201e");
		mapA0201d.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0201e = new HashMap<String, String>();
	static {
		mapA0201e.put("dataIndex", "a0201e");
		mapA0201e.put("header", "成员类别");
		mapA0201e.put("width", "30");
		mapA0201e.put("edited", "true");
		mapA0201e.put("editor", "select");
		mapA0201e.put("required", "false");
		mapA0201e.put("codeType", "ZB129");
		mapA0201e.put("editorId", "a0201e");
		mapA0201e.put("onSelect", "");
		mapA0201e.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0251b = new HashMap<String, String>();
	static {
		mapA0251b.put("dataIndex", "a0251b");
		mapA0251b.put("header", "破格提拔");
		mapA0251b.put("width", "30");
		mapA0251b.put("edited", "true");
		mapA0251b.put("editor", "select");
		mapA0251b.put("required", "false");
		mapA0251b.put("codeType", "VSC007");
		mapA0251b.put("editorId", "a0251b");
		mapA0251b.put("onSelect", "");
		mapA0251b.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0247 = new HashMap<String, String>();
	static {
		mapA0247.put("dataIndex", "a0247");
		mapA0247.put("header", "选拔任用方式");
		mapA0247.put("width", "40");
		mapA0247.put("edited", "true");
		mapA0247.put("editor", "select");
		mapA0247.put("required", "false");
		mapA0247.put("codeType", "ZB122");
		mapA0247.put("editorId", "a0247");
		mapA0247.put("onSelect", "");
		mapA0247.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0243 = new HashMap<String, String>();
	static {
		mapA0243.put("dataIndex", "a0243");
		mapA0243.put("header", "任职时间");
		mapA0243.put("width", "30");
		mapA0243.put("edited", "true");
		mapA0243.put("editor", "text");
		mapA0243.put("required", "false");
		mapA0243.put("codeType", "");
		mapA0243.put("editorId", "a0243");
		mapA0243.put("onSelect", "");
		mapA0243.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0245 = new HashMap<String, String>();
	static {
		mapA0245.put("dataIndex", "a0245");
		mapA0245.put("header", "任职文号");
		mapA0245.put("width", "30");
		mapA0245.put("edited", "true");
		mapA0245.put("editor", "text");
		mapA0245.put("required", "false");
		mapA0245.put("codeType", "");
		mapA0245.put("editorId", "a0245");
		mapA0245.put("onSelect", "");
		mapA0245.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0265 = new HashMap<String, String>();
	static {
		mapA0265.put("dataIndex", "a0265");
		mapA0265.put("header", "免职时间");
		mapA0265.put("width", "30");
		mapA0265.put("edited", "true");
		mapA0265.put("editor", "text");
		mapA0265.put("required", "false");
		mapA0265.put("codeType", "");
		mapA0265.put("editorId", "a0265");
		mapA0265.put("onSelect", "");
		mapA0265.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0267 = new HashMap<String, String>();
	static {
		mapA0267.put("dataIndex", "a0267");
		mapA0267.put("header", "免职文号");
		mapA0267.put("width", "30");
		mapA0267.put("edited", "true");
		mapA0267.put("editor", "text");
		mapA0267.put("required", "false");
		mapA0267.put("codeType", "");
		mapA0267.put("editorId", "a0267");
		mapA0267.put("onSelect", "");
		mapA0267.put("onKeyDown", "");
	}
	public static HashMap<String, String> mapA0272 = new HashMap<String, String>();
	static {
		mapA0272.put("dataIndex", "a0272");
		mapA0272.put("header", "职务变动原因综述");
		mapA0272.put("width", "50");
		mapA0272.put("edited", "true");
		mapA0272.put("editor", "text");
		mapA0272.put("required", "false");
		mapA0272.put("codeType", "");
		mapA0272.put("editorId", "a0272");
		mapA0272.put("onSelect", "");
		mapA0272.put("onKeyDown", "");
	}
	static {
	    map = new HashMap<String,HashMap<String,String>>();
	    //
	    
	    map.put("姓名", mapA0101);
	    map.put("身份证号", mapA0184);
	    map.put("性别", mapA0104);
	    map.put("出生年月", mapA0107);
	    map.put("民族", mapA0117);
	    map.put("籍贯", mapA0111A);
	    map.put("出生地", mapA0114A);
	    map.put("政治面貌", mapA0141);
	    map.put("入党时间", mapA0140);
	    map.put("第二党派", mapA3921);
	    map.put("第三党派", mapA3927);
	    map.put("统计关系所在单位", mapA0195);
	    map.put("公务员登记时间", mapA2949);
	    map.put("级别", mapA0120);
	    map.put("成长地", mapA0115A);
	    map.put("专业技术类公务员任职资格", mapA0122);
	    map.put("参加工作时间", mapA0134);
	    map.put("健康状况", mapA0128);
	    map.put("专长", mapA0187A);
	    map.put("人员类别", mapA0160);
	    map.put("管理类别", mapA0165);
	    map.put("编制类型", mapA0121);
	    map.put("备注", mapA0180);
	    //工作单位及职务
	    map.put("任职机构", mapA0201b);
	    map.put("任职机构名称", mapA0201a);
	    map.put("职务名称", mapA0215a);
	    map.put("任免状态", mapA0255);
	    map.put("主职务", mapA0279);
	    map.put("领导职务", mapA0219);
	    map.put("领导成员", mapA0201d);
	    map.put("成员类别", mapA0201e);
	    map.put("破格提拔", mapA0251b);
	    map.put("选拔任用方式", mapA0247);
	    map.put("任职时间", mapA0243);
	    map.put("任职文号", mapA0245);
	    map.put("免职时间", mapA0265);
	    map.put("免职文号", mapA0267);
	    map.put("职务变动原因综述", mapA0272);
	}
}
