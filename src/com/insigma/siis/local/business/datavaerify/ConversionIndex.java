package com.insigma.siis.local.business.datavaerify;

/*
 * 主要做二级代码的的转换
 * 
 */
public class ConversionIndex {
	// 人员类别代码ZB125
	public static String A0160(String index) {
		String A0160 = index;
		if ("2".equals(index) || "3".equals(index)) {
			A0160 = "1";
		} else if ("A2".equals(index)) {
			A0160 = "9";
		}
		return A0160;
	}

	// 人员管理状态代码ZB126
	public static String A0163(String index) {
		String A0163 = index;
		if("2".equals(index)){
			A0163 = "21";
		}
		if("3".equals(index)){
			A0163 = "22";
		}
		if("4".equals(index)){
			A0163 = "23";
		}
		if("5".equals(index)){
			A0163 = "29";
		}
		/*if ("3".equals(index) || "4".equals(index) || "5".equals(index)) {
			A0163 = "2";
		}*/
		return A0163;
	}

	// 职务名称代码ZB08 A0215A
	public static String ZB08(String index) {
		String ZB08 = "";
		if ("001A".equals(index) || "001B".equals(index)
				|| "002A".equals(index) || "004A".equals(index)
				|| "004B".equals(index) || "105A".equals(index)
				|| "105B".equals(index) || "201A".equals(index)
				|| "201B".equals(index) || "251A".equals(index)
				|| "251B".equals(index) || "252A".equals(index)
				|| "252B".equals(index) || "254A".equals(index)
				|| "254B".equals(index) || "255A".equals(index)
				|| "255B".equals(index) || "256A".equals(index)
				|| "256B".equals(index) || "257A".equals(index)
				|| "257B".equals(index) || "258A".equals(index)
				|| "258B".equals(index) || "259A".equals(index)
				|| "259B".equals(index) || "260A".equals(index)
				|| "260B".equals(index) || "261A".equals(index)
				|| "261B".equals(index) || "417A".equals(index)
				|| "417B".equals(index) || "T01K".equals(index)) {
			ZB08 = index;
		} else {
			ZB08 = "ZZZZ";
		}
		return ZB08;
	}

	// 免职类别ZB16 A0271
	public static String ZB16(String index) {
		if ("1".equals(index)) {
			return "1";
		} else if ("3".equals(index)) {
			return "2";
		} else if ("4".equals(index)) {
			return "3";
		} else if ("7".equals(index)) {
			return "6";
		} else if ("8".equals(index)) {
			return "7";
		}
		return "99";
	}

	// 教育 ZB64 A0801B
	public static String ZB64(String index) {
		/*if ("13".equals(index) || "37".equals(index) || "1B".equals(index)
				|| "2B".equals(index)) {
			return "91";
		}*/
		return index;
	}
	//獎懲 ZB65 A1404B
	public static String ZB65(String index) {
		if ("011".equals(index) || "0111".equals(index)) {
			return "01119";
		} else if ("02235".equals(index) || "02236".equals(index)) {
			return "02234";
		}
		return index;
	}
	//ZB78  調出   A3001
	public static String ZB78(String index) {
		if ("11".equals(index)) {//调到实施公务员法机关（公开遴选）
			return "21";
		} else if ("12".equals(index)) {//调到实施公务员法机关（非公开遴选）
			return "22";
		} else if ("13".equals(index)) {//调到参照管理机关（单位）（公开遴选）
			return "23";
		} else if ("14".equals(index)) {//调到参照管理机关（单位）（非公开遴选)
			return "24";
		} else if ("15".equals(index)) {//录用 被实施公务员法机关录用
			return "95";
		}else if ("16".equals(index)) {//被参照管理机关（单位）录用
			return "96";
		}else if ("17".equals(index)) {//调到其他事业单位
			return "25";
		}else if ("18".equals(index)) {//调到国有企业
			return "26";
		}else if ("21".equals(index)) {//整建制转到实施公务员法机关
			return "81";
		}else if ("22".equals(index)) {//整建制转到参照公务员法管理的群团机关
			return "82";
		}else if ("23".equals(index)) {//整建制转到参照公务员法管理的事业单位
			return "82";
		}else if ("24".equals(index)) {//整建制转到其他事业单位
			return "83";
		}else if ("25".equals(index)) {//整建制转到国有企业
			return "84";
		}else if ("31".equals(index)) {//离（退）休
			return "11";
		}else if ("32".equals(index)) {//辞去公职
			return "44";
		}else if ("33".equals(index)) {//辞退
			return "91";
		}else if ("34".equals(index)) {//开除
			return "92";
		}else if ("35".equals(index)) {//死亡
			return "31";
		}else if ("36".equals(index)) {//非正常死亡
			return "32";
		}else if ("37".equals(index)) {//因选举退出登记
			return "93";
		}else if ("39".equals(index)) {//其他
			return "99";
		}
		return index;
	}
	//ZB87 B0124	单位隶属关系代码
	public static String ZB87(String index) {
		/*if ("1A".equals(index)) {
			return "10";
		}*/
		return index;
	}
	//ZB29 A1101	培训类别代码
	public static String ZB29(String index) {
		/*if ("4".equals(index)) {
			return "9";
		}*/
		return index;
	}
	//ZB27	A1127 从学单位类别代码
	public static String ZB27(String index) {
		if ("4".equals(index)) {
			return "9";
		}
		return index;
	}
	//zb09     级别    A1415,A3107
	public static String zb09(String index) {
		if("01".equals(index)){
			return "1";
		}else if("0101".equals(index)){
			return "1A01";
		}else if("0102".equals(index)){
			return "1A02";
		}else if("0111".equals(index)){
			return "1A11";
		}else if("0112".equals(index)){
			return "1A12";
		}else if("0121".equals(index)){
			return "1A21";
		}else if("0122".equals(index)){
			return "1A22";
		}else if("0131".equals(index)){
			return "1A31";
		}else if("0132".equals(index)){
			return "1A32";
		}else if("0141".equals(index)){
			return "1A41";
		}else if("0142".equals(index)){
			return "1A42";
		}else if("0150".equals(index)){
			return "1A50";
		}else if("0160".equals(index)){
			return "1A60";
		}else if("0198".equals(index)){
			return "1A98";
		}else if("0199".equals(index)){
			return "1A99";
		}
		
		else if("02".equals(index)){
			return "2";
		}else if("020".equals(index)){
			return "20";
		}else if("021".equals(index)){
			return "21";
		}else if("022".equals(index)){
			return "22";
		}else if("023".equals(index)){
			return "23";
		}else if("024".equals(index)){
			return "24";
		}else if("025".equals(index)){
			return "25";
		}else if("026".equals(index)){
			return "26";
		}else if("027".equals(index)){
			return "27";
		}else if("028".equals(index)){
			return "28";
		}
		
		else if("09".equals(index)){
			return "7";
		}
		
		else if("08".equals(index)){
			return "9";
		}else if("0811".equals(index)){
			return "901";
		}else if("0812".equals(index)){
			return "902";
		}else if("0821".equals(index)){
			return "903";
		}else if("0822".equals(index)){
			return "904";
		}else if("0831".equals(index)){
			return "905";
		}else if("0832".equals(index)){
			return "906";
		}else if("0841".equals(index)){
			return "907";
		}else if("0842".equals(index)){
			return "908";
		}else if("0850".equals(index)){
			return "909";
		}else if("0860".equals(index)){
			return "910";
		}else if("0899".equals(index)){
			return "912";
		}
		
		else if("03".equals(index)){
			return "C";
		}else if("0311".equals(index)){
			return "C01";
		}else if("0312".equals(index)){
			return "C02";
		}else if("0313".equals(index)){
			return "C03";
		}else if("0314".equals(index)){
			return "C04";
		}else if("0321".equals(index)){
			return "C05";
		}else if("0322".equals(index)){
			return "C06";
		}else if("0323".equals(index)){
			return "C07";
		}else if("0331".equals(index)){
			return "C08";
		}else if("0332".equals(index)){
			return "C09";
		}else if("0333".equals(index)){
			return "C10";
		}else if("0341".equals(index)){
			return "C11";
		}else if("0342".equals(index)){
			return "C12";
		}else if("0351".equals(index)){
			return "C13";
		}else if("0399".equals(index)){
			return "C99";
		}
		
		else if("04".equals(index)){
			return "D";
		}else if("0410".equals(index)){
			return "D01";
		}else if("0420".equals(index)){
			return "D02";
		}else if("0430".equals(index)){
			return "D03";
		}else if("0440".equals(index)){
			return "D04";
		}else if("0450".equals(index)){
			return "D05";
		}else if("0499".equals(index)){
			return "D09";
		}
		
		else if("05".equals(index)){
			return "E";
		}else if("0510".equals(index)){
			return "E01";
		}else if("0599".equals(index)){
			return "E09";
		}
		
		else if("06".equals(index)){
			return "F";
		}else if("0610".equals(index)){
			return "F01";
		}else if("0620".equals(index)){
			return "F02";
		}else if("0630".equals(index)){
			return "F03";
		}else if("0640".equals(index)){
			return "F04";
		}else if("0650".equals(index)){
			return "F05";
		}else if("0699".equals(index)){
			return "F09";
		}
		
		else if("07".equals(index)){
			return "G";
		}else if("0710".equals(index)){
			return "G01";
		}else if("0799".equals(index)){
			return "G09";
		}
		
		/*if ("01".equals(index)||"0101".equals(index)||"0102".equals(index)||"0111".equals(index)||"0112".equals(index)
				||"0121".equals(index)||"0122".equals(index)||"0131".equals(index)||"0132".equals(index)||"0141".equals(index)
				||"0142".equals(index)||"0150".equals(index)||"0160".equals(index)||"0198".equals(index)||"0199".equals(index)
				||"02".equals(index)||"021".equals(index)||"020".equals(index)||"022".equals(index)||"023".equals(index)
				||"024".equals(index)||"025".equals(index)||"026".equals(index)||"027".equals(index)||"028".equals(index)) {
			return index;
		} else if ("01011".equals(index)||"01021".equals(index)||"01111".equals(index)||"01121".equals(index)
				||"01211".equals(index)||"01221".equals(index)||"01311".equals(index)||"01321".equals(index)||"01411".equals(index)
				||"01421".equals(index)) {
			return "0199";
		} else if("03".equals(index)){
			return "C";
		} else if("0311".equals(index)){
			return "C01";
		} else if("0312".equals(index)){
			return "C02";
		} else if("0313".equals(index)){
			return "C03";
		} else if("0314".equals(index)){
			return "C04";
		} else if("0321".equals(index)){
			return "C05";
		} else if("0322".equals(index)){
			return "C06";
		} else if("0323".equals(index)){
			return "C07";
		} else if("0331".equals(index)){
			return "C08";
		} else if("0332".equals(index)){
			return "C09";
		} else if("0333".equals(index)){
			return "C10";
		} else if("0341".equals(index)){
			return "C11";
		} else if("0342".equals(index)){
			return "C12";
		} else if("0351".equals(index)){
			return "C13";
		} else if("0399".equals(index)){
			return "C99";
			
		} else if("04".equals(index)){
			return "D";
		} else if("0410".equals(index)){
			return "D01";
		} else if("0420".equals(index)){
			return "D02";
		} else if("0430".equals(index)){
			return "D03";
		} else if("0440".equals(index)){
			return "D04";
		} else if("0450".equals(index)){
			return "D05";
		} else if("0499".equals(index)){
			return "D09";
			
		} else if("05".equals(index)){
			return "E";
		} else if("0510".equals(index)){
			return "E01";
		} else if("0599".equals(index)){
			return "E09";
			
		} else if("06".equals(index)){
			return "F";
		} else if("0610".equals(index)){
			return "F01";
		} else if("0620".equals(index)){
			return "F02";
		} else if("0630".equals(index)){
			return "F03";
		} else if("0640".equals(index)){
			return "F04";
		} else if("0650".equals(index)){
			return "F05";
		} else if("0699".equals(index)){
			return "F09";
			
		} else if("07".equals(index)){
			return "G";
		} else if("0710".equals(index)){
			return "G01";
		} else if("0799".equals(index)){
			return "G09";
			
		} else if("09".equals(index)){
			return "5";
		}*/
		return "";
	}
	//ZB77 A2911
	public static String ZB77(String index) {
		/*if ("4".equals(index)) {
			return "6";
		}*///由于目前不接受A29表，故导入临时表时全按6导入，避免不必要报错
		return "6";
	}
	//ZB04 B0131
	public static String ZB04(String index) {
		if(index.equals("3425")|| index.equals("3426")){
			return "3450";
		} else if(index.equals("3434")|| index.equals("3435")){
			return "3451";
		} else if(index.equals("36")){
			return "1006";
		} else if(index.equals("37")){
			return "1007";
		} else if(index.equals("5001")){
			return "511";
		} else if(index.equals("5003")){
			return "512";
		} else if(index.equals("5012")){
			return "516";
		} else if(index.equals("5013")){
			return "515";
		} else if(index.equals("5016")){
			return "533";
		} else if(index.equals("5019")){
			return "523";
		} else if(index.equals("F002")){
			return "F001";
		} else if(index.equals("F003")){
			return "F002";
		} else if(index.equals("F004")){
			return "F003";
		} else if(index.equals("F001")||index.equals("61")||index.equals("6101")||index.equals("6102")||index.equals("6103")
				||index.equals("6104")||index.equals("6105")||index.equals("5002")||index.equals("5004")||index.equals("5005")
				||index.equals("5006")||index.equals("5007")||index.equals("5008")||index.equals("5009")||index.equals("5010")
				||index.equals("5011")||index.equals("5014")||index.equals("5015")||index.equals("5017")||index.equals("5018")
				||index.equals("5020")||index.equals("50FF")){
			return "F";
		}
		return index;
	}
	
	//称谓 GB4761 A3604a
	public static String GB4761(String index) {
		if(index.equals("01")){
			return "51";
		} else if(index.equals("02")){
			return "52";
		} else if(index.equals("03")){
			return "11";
		} else if(index.equals("04")){
			return "12";
		} else if(index.equals("05")){
			return "20";
		} else if(index.equals("06")){
			return "30";
			
		} else if(index.equals("07")){
			return "71";
		} else if(index.equals("08")){
			return "73";
		} else if(index.equals("09")){
			return "75";
		} else if(index.equals("10")){
			return "77";
			
		} else if(index.equals("13")){
			return "21";
		} else if(index.equals("14")){
			return "22";
		} else if(index.equals("15")){
			return "23";
		} else if(index.equals("16")){
			return "24";
		} else if(index.equals("17")){
			return "25";
		} else if(index.equals("18")){
			return "26";
		} else if(index.equals("19")){
			return "27";
			
		} else if(index.equals("22")){
			return "28";
		} else if(index.equals("23")){
			return "29";
			
		} else if(index.equals("24")){
			return "31";
		} else if(index.equals("25")){
			return "32";
		} else if(index.equals("26")){
			return "33";
		} else if(index.equals("27")){
			return "34";
		} else if(index.equals("28")){
			return "35";
		} else if(index.equals("29")){
			return "36";
		} else if(index.equals("30")){
			return "37";
		} else if(index.equals("31")){
			return "38";
		} else if(index.equals("32")){
			return "39";
			
		} else if(index.equals("34")){
			return "41";
		} else if(index.equals("35")){
			return "42";
		} else if(index.equals("36")){
			return "43";
		} else if(index.equals("37")){
			return "44";
		} else if(index.equals("38")){
			return "45";
		} else if(index.equals("39")){
			return "46";
		} else if(index.equals("40")){
			return "47";
		} else if(index.equals("41")){
			return "48";
		} else if(index.equals("42")){
			return "49";
			
		} else if(index.equals("43")){
			return "53";
		} else if(index.equals("44")){
			return "54";
		} else if(index.equals("45")){
			return "55";
		} else if(index.equals("46")){
			return "56";
		} else if(index.equals("47")){
			return "57";
			
		} else if(index.equals("50")){
			return "58";
		} else if(index.equals("53")){
			return "59";
			
		} else if(index.equals("55")){
			return "61";
		} else if(index.equals("56")){
			return "62";
		} else if(index.equals("57")){
			return "63";
		} else if(index.equals("58")){
			return "64";
		} else if(index.equals("59")){
			return "65";
		} else if(index.equals("60")){
			return "66";
		} else if(index.equals("61")){
			return "67";
		} else if(index.equals("62")){
			return "68";
		} else if(index.equals("63")){
			return "69";
			
			
		} else if(index.equals("65")){
			return "72";
		} else if(index.equals("66")){
			return "74";
		} else if(index.equals("67")){
			return "76";
		} else if(index.equals("68")){
			return "78";
		} else if(index.equals("69")){
			return "80";
		} else if(index.equals("70")){
			return "81";
		} else if(index.equals("71")){
			return "82";
		} else if(index.equals("72")){
			return "83";
		} else if(index.equals("73")){
			return "84";
		} else if(index.equals("74")){
			return "85";
		} else if(index.equals("75")){
			return "86";
		} else if(index.equals("76")){
			return "87";
		} else if(index.equals("77")){
			return "88";
		} else if(index.equals("78")){
			return "89";
		} else if(index.equals("79")){
			return "90";
			
			
		} else if(index.equals("80")){
			return "91";
		} else if(index.equals("85")){
			return "92";
			
			
		} else if(index.equals("90")){
			return "93";
		} else if(index.equals("91")){
			return "94";
		} else if(index.equals("92")){
			return "95";
		} else if(index.equals("93")){
			return "96";
		} else if(index.equals("94")){
			return "97";
		} else if(index.equals("95")){
			return "98";
		} else if(index.equals("96")){
			return "99";
		} else {
			return "97";
		}
	}
	
	// 机构级别  B0127
	public static String ZB03(String index) {
		/*if ("103A".equals(index)) {
			return "1031";
		} else if ("103B".equals(index)) {
			return "1032";
		} else if ("104A".equals(index)) {
			return "1041";
		} else if ("103B".equals(index)) {
			return "1042";
		}*/
		return index;	
	}
	
	
	// 班子成员类别代码ZB129（无改变）
	public static String ZB129(String index) {
		if ("1".equals(index) || "3".equals(index) || "Z".equals(index)) {
			return index;
		} else{
			return "Z";
		}
	}
	
	
	
	
	

	

	
	// 单位减员类别代码？？
	// 单位性质类别代码
	// 培训类别代码
	// 工资级别代码首信未找到ZB134(无)
	// 编制类别代码未找到ZB135（无）
	// 专业技术类公务员任职资格代码未找到ZB139（无）

	// 班子成员类别代码ZB129（无改变）
	// 学历代码？？？

}
