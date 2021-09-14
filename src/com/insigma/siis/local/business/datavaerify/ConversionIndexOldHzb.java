package com.insigma.siis.local.business.datavaerify;

import java.util.Map;

/*
 * 主要做二级代码的的转换
 * 
 */
public class ConversionIndexOldHzb {
	
	private final static Map<String, String> A3604Map = getA3604Map();
	private final static Map<String, String> ZB09Map = getZB09Map();
	private final static Map<String, String> ZB136Map = getZB136Map();
	private final static Map<String, String> GB4762Map = getGB4762Map();
	
	// 人员类别代码ZB125 
	public static String ZB125(String index) {
		String A0160 = index;
		if ("2".equals(index) || "3".equals(index)) {
			A0160 = "1";
		} else if ("61".equals(index) || "62".equals(index)|| "63".equals(index)) {
			A0160 = "6";
		} else if ("A2".equals(index) || "B1".equals(index)|| "B2".equals(index)|| "B3".equals(index)|| "B4".equals(index)) {
			A0160 = "9";
		}
		return A0160;
	}


	// 人员管理状态代码ZB126
	public static String ZB126(String index) {
		String A0163 = index;
		if("2".equals(index)){
			A0163 = "21";
		} else if("3".equals(index)){
			A0163 = "22";
		} else if("4".equals(index)){
			A0163 = "23";
		} else if("5".equals(index)){
			A0163 = "29";
		}
		return A0163;
	}
	
	//ZB134级别代码
	public static String ZB134(String index) {
		String A0120 = index;
		if("01".equals(index)){
			A0120 = "1";
		} else if("02".equals(index)){
			A0120 = "2";
		} else if("03".equals(index)){
			A0120 = "3";
		} else if("04".equals(index)){
			A0120 = "4";
		} else if("05".equals(index)){
			A0120 = "5";
		} else if("06".equals(index)){
			A0120 = "6";
		} else if("08".equals(index)){
			A0120 = "8";
		} else if("07".equals(index)){
			A0120 = "7";
		} else if("09".equals(index)){
			A0120 = "9";
		}
		return A0120;
	}
	//ZB135编制类型代码
	public static String ZB135(String index) {
		String A0121 = index;
		if("4".equals(index)){
			A0121 = "9";
		} 
		return A0121;
	}	
	
	//ZB122 选拔任用方式代码
	public static String ZB122(String index) {
		String A0247 = index;
		if("19".equals(index)){
			A0247 = "";
		} 
		return A0247;
	}
	
	// 免职类别ZB16 A0271
		public static String ZB16(String index) {
			if ("1".equals(index)) {
				return "1";
			} else if ("3".equals(index)) {
				return "2";
			} else if ("4".equals(index)) {
				return "3";
			} else if ("5".equals(index)) {
				return "4";
			} else if ("6".equals(index)) {
				return "5";
			} else if ("7".equals(index)) {
				return "6";
			} else if ("8".equals(index)) {
				return "7";
			}
			return "99";
		}
		//獎懲 ZB65 A1404B
		public static String ZB65(String index) {
			if ("01111".equals(index) || "01111A".equals(index)|| "01111B".equals(index)|| "01111C".equals(index)) {
				return "01111";
			} else if ("02234".equals(index) || "02235".equals(index) || "02236".equals(index)) {
				return "02234";
			}
			return index;
		}
		//考核结论类别代码ZB18 ZB18
		public static String ZB18(String index) {
			/*if ("01111".equals(index) || "01111A".equals(index)|| "01111B".equals(index)|| "01111C".equals(index)) {
				return "01111";
			} else if ("02234".equals(index) || "02235".equals(index) || "02236".equals(index)) {
				return "02234";
			}*/
			return index;
		}
	
		//ZB04 B0131
		public static String ZB04(String index) {
			if(index.equals("5001")|| index.equals("5002")){
				return "511";
			} else if(index.equals("5003")|| index.equals("5004")|| index.equals("5005")|| index.equals("5006")
					|| index.equals("5007")|| index.equals("5008")|| index.equals("5009")|| index.equals("5010")
					|| index.equals("5011")){
				return "512";
			} else if(index.equals("5013")){
				return "515";
			} else if(index.equals("5012")){
				return "516";
			} else if(index.equals("5020")){
				return "517";
			} else if(index.equals("50FF")){
				return "519";
			} else if(index.equals("5019")){
				return "52";
			} else if(index.equals("5017")){
				return "521";
			} else if(index.equals("5018")){
				return "522";
			} else if(index.equals("5014")){
				return "531";
			} else if(index.equals("5015")){
				return "532";
			} else if(index.equals("5016")){
				return "533";
			} else if(index.equals("61")||index.equals("6101")||index.equals("6102")
					||index.equals("6103")||index.equals("6104")||index.equals("6105")){
				return "6101";
			} else if(index.equals("F001")||index.equals("F002")){
				return "F001";
			} else if(index.equals("F003")){
				return "F002";
			} else if(index.equals("F004")){
				return "F003";
			} 
			return index;
		}
	
		
		//ZB137
		public static String ZB137(String index) {
			if(index.equals("01")){
				return "1";
			} else if(index.equals("02")){
				return "2";
			} else if(index.equals("03")){
				return "3";
			}
			return index;
		}		
		//ZB138
		public static String ZB138(String index) {
			if(index.equals("01")){
				return "1";
			} else if(index.equals("02")){
				return "2";
			} else if(index.equals("03")){
				return "9";
			}
			return index;
		}	
		//ZB141
		public static String ZB141(String index) {
			String k = index;
			if(k.equals("6")){
				return "9";
			}
			return index;
		}		
		//ZB141
		public static String ZB145(String index) {
			String k = index;
			if(k.equals("5")){
				return "13";
			} else if(k.equals("6")){
				return "12";
			}  else if(k.equals("7")){
				return "11";
			}  else if(k.equals("8")){
				return "10";
			}  else if(k.equals("9")){
				return "9";
			}  else if(k.equals("10")){
				return "8";
			}  else if(k.equals("11")){
				return "7";
			}  else if(k.equals("12")){
				return "6";
			}  else if(k.equals("13")){
				return "5";
			} 
			return index;
		}	
		
		//ZB27	A1127 从学单位类别代码
		public static String ZB27(String index) {
			if ("4".equals(index)) {
				return "9";
			}
			return index;
		}
		
		//ZB133
		public static String ZB133(String index) {
			String k = index;
			if(k.equals("1")){
				return "21";
			} else if(k.equals("2")){
				return "22";
			}  else if(k.equals("3")){
				return "23";
			}  else if(k.equals("4")){
				return "24";
			}  else if(k.equals("5")){
				return "25";
			}
			return index;
		}			
		
		
		//ZB133
		public static String GB4761_ch(String index) {
			return A3604Map.get(index+"");
		}			
		
		//zb09     级别    A1415,A3107
		public static String ZB09(String index) {
			return ZB09Map.get(index+"");
		}
		
		//zb09     级别    A1415,A3107
		public static String ZB136(String index) {
			return ZB136Map.get(index+"");
		}
		
		public static String GB4762(String index) {
			return GB4762Map.get(index+"");
		}
		
		
		private static Map getGB4762Map() {
			Map<String, String> data = new java.util.HashMap<String, String>();
			data.put("01","中共党员");
			data.put("02","预备党员");
			data.put("03","共青团员");
			data.put("04","民革");
			data.put("05","民盟");
			data.put("06","民建");
			data.put("07","民进");
			data.put("08","农工党");
			data.put("09","致公党");
			data.put("10","九三学社");
			data.put("11","台盟");
			data.put("12","无党派");
			data.put("13","群众");
			return data;
		}
		
		private static Map getZB136Map() {
			Map<String, String> data = new java.util.HashMap<String, String>();
			data.put("1","3");
			data.put("101","301");
			data.put("102","302");
			data.put("103","303");
			data.put("104","304");
			data.put("105","305");
			data.put("106","306");
			data.put("107","307");
			data.put("108","308");
			data.put("109","309");
			data.put("110","310");
			data.put("111","311");
			data.put("112","312");
			data.put("2","4");
			data.put("201","401");
			data.put("202","402");
			data.put("203","403");
			data.put("204","404");
			data.put("205","405");
			data.put("206","406");
			data.put("207","407");
			data.put("208","408");
			data.put("209","409");
			data.put("210","410");
			data.put("211","411");
			data.put("212","412");
			data.put("3","5");
			data.put("301","501");
			data.put("302","502");
			data.put("303","503");
			data.put("304","504");
			data.put("305","505");
			data.put("306","506");
			data.put("307","507");
			data.put("308","508");
			data.put("309","509");
			data.put("310","510");
			data.put("311","511");
			data.put("4","6");
			data.put("401","601");
			data.put("402","602");
			data.put("403","603");
			data.put("404","604");
			data.put("405","605");
			data.put("406","606");
			data.put("407","607");
			data.put("408","608");
			data.put("409","609");
			data.put("410","610");
			data.put("411","611");
			data.put("412","612");
			data.put("5","71");
			data.put("501","7101");
			data.put("502","7102");
			data.put("503","7103");
			data.put("504","7104");
			data.put("505","7105");
			data.put("506","7106");
			data.put("507","7107");
			data.put("508","7108");
			data.put("509","7109");
			data.put("510","7110");
			data.put("6","72");
			data.put("601","7201");
			data.put("602","7202");
			data.put("603","7203");
			data.put("604","7204");
			data.put("605","7205");
			data.put("606","7206");
			data.put("607","7207");
			data.put("608","7208");
			data.put("609","7209");
			data.put("610","7210");
			data.put("611","7211");
			data.put("612","7212");
			data.put("7","7");
			data.put("701","7");
			data.put("702","7");
			data.put("703","7");
			data.put("704","7");
			data.put("705","7");
			data.put("706","7");
			data.put("707","7");
			return data;
		}
		
		private static Map getA3604Map() {
			Map<String, String> data = new java.util.HashMap<String, String>();
			data.put("01","父亲");
			data.put("02","母亲");
			data.put("03","丈夫");
			data.put("04","妻子");
			data.put("05","儿子");
			data.put("06","女儿");
			data.put("07","哥哥");
			data.put("08","弟弟");
			data.put("09","姐姐");
			data.put("10","妹妹");
			data.put("11","父母");
			data.put("12","配偶");
			data.put("13","独生子");
			data.put("14","长子");
			data.put("15","次子");
			data.put("16","三子");
			data.put("17","四子");
			data.put("18","五子");
			data.put("19","养子或继子");
			data.put("20","养子");
			data.put("21","继子");
			data.put("22","女婿");
			data.put("23","其他儿子");
			data.put("24","独生女");
			data.put("25","长女");
			data.put("26","次女");
			data.put("27","三女");
			data.put("28","四女");
			data.put("29","五女");
			data.put("30","养女");
			data.put("31","儿媳");
			data.put("32","其他女儿");
			data.put("33","孙子、孙女或外孙子、外孙女");
			data.put("34","孙子");
			data.put("35","孙女");
			data.put("36","外孙子");
			data.put("37","外孙女");
			data.put("38","孙媳妇或外孙媳妇");
			data.put("39","孙女婿或外孙女婿");
			data.put("40","曾孙子或外曾孙子");
			data.put("41","曾孙女或外曾孙女");
			data.put("42","其他孙子、孙女或外孙子、外孙女");
			data.put("43","公公");
			data.put("44","婆婆");
			data.put("45","岳父");
			data.put("46","岳母");
			data.put("47","继父或养父");
			data.put("48","继父");
			data.put("49","养父");
			data.put("50","继母或养母");
			data.put("51","继母");
			data.put("52","养母");
			data.put("53","其他父母关系");
			data.put("54","祖父或外祖父");
			data.put("55","祖父");
			data.put("56","祖母");
			data.put("57","外祖父");
			data.put("58","外祖母");
			data.put("59","配偶的祖父母或外祖父母");
			data.put("60","曾祖父");
			data.put("61","曾祖母");
			data.put("62","配偶的曾祖父母");
			data.put("63","其他祖父母或外祖父母关系");
			data.put("64","兄弟姐妹");
			data.put("65","嫂子");
			data.put("66","弟媳");
			data.put("67","姐夫");
			data.put("68","妹夫");
			data.put("69","其他");
			data.put("70","伯父");
			data.put("71","伯母");
			data.put("72","叔父");
			data.put("73","婶母");
			data.put("74","舅父");
			data.put("75","舅母");
			data.put("76","姨父");
			data.put("77","姨母");
			data.put("78","姑父");
			data.put("79","姑母");
			data.put("7A","夫兄");
			data.put("7B","夫弟");
			data.put("7C","夫姐");
			data.put("7D","夫妹");
			data.put("7E","妻兄");
			data.put("7F","妻弟");
			data.put("7G","妻姐");
			data.put("7H","妻妹");
			data.put("80","堂兄弟、堂姐妹");
			data.put("81","堂兄");
			data.put("82","堂弟");
			data.put("83","堂姐");
			data.put("84","堂妹");
			data.put("85","表兄弟、表姐妹");
			data.put("86","表兄");
			data.put("87","表弟");
			data.put("88","表姐");
			data.put("89","表妹");
			data.put("90","侄子");
			data.put("91","侄女");
			data.put("92","外甥");
			data.put("93","外甥女");
			data.put("94","其他亲属");
			data.put("95","保姆");
			data.put("96","非亲属");
			return data;
		}
		
		
		private static Map<String, String> getZB09Map() {
			Map<String, String> data = new java.util.HashMap<String, String>();
			/*data.put("01","1");
			data.put("0101","1A");
			data.put("0102","1A01");
			data.put("0111","1A02");
			data.put("0112","1A11");
			data.put("0121","1A12");
			data.put("0122","1A21");
			data.put("0131","1A22");
			data.put("0132","1A31");
			data.put("0141","1A32");
			data.put("0142","1A41");
			data.put("0150","1A42");
			data.put("0160","1A50");
			data.put("0198","1A60");
			data.put("0199","1A98");
			data.put("01A","1A99");*/
			
			data.put("01A","1A");
			data.put("0101","1A01");
			data.put("0102","1A02");
			data.put("0111","1A11");
			data.put("0112","1A12");
			data.put("0121","1A21");
			data.put("0122","1A22");
			data.put("0131","1A31");
			data.put("0132","1A32");
			data.put("0141","1A41");
			data.put("0142","1A42");
			data.put("0150","1A50");
			data.put("0160","1A60");
			data.put("0198","1A98");
			data.put("0199","1A99");
			
			
			data.put("01B","1B");
			data.put("01B01","1B01");
			data.put("01B02","1B02");
			data.put("01B03","1B03");
			data.put("01B04","1B04");
			data.put("01B05","1B05");
			data.put("01B06","1B06");
			data.put("01B07","1B07");
			data.put("01B08","1B08");
			data.put("01B09","1B09");
			data.put("01B10","1B10");
			data.put("01B11","1B11");
			data.put("01B98","1B98");
			data.put("01B99","1B99");
			data.put("01C","1C");
			data.put("01C01","1C01");
			data.put("01C02","1C02");
			data.put("01C03","1C03");
			data.put("01C04","1C04");
			data.put("01C05","1C05");
			data.put("01C06","1C06");
			data.put("01C07","1C07");
			data.put("01C08","1C08");
			data.put("01C09","1C09");
			data.put("01C10","1C10");
			data.put("01C11","1C11");
			data.put("01C98","1C98");
			data.put("01C99","1C99");
			data.put("02","2");
			data.put("020","20");
			data.put("021","21");
			data.put("022","22");
			data.put("023","23");
			data.put("024","24");
			data.put("025","25");
			data.put("026","26");
			data.put("027","27");
			data.put("028","28");
			data.put("09","7");
			data.put("08","9");
			data.put("0811","901");
			data.put("0812","902");
			data.put("0821","903");
			data.put("0822","904");
			data.put("0831","905");
			data.put("0832","906");
			data.put("0841","907");
			data.put("0842","908");
			data.put("0850","909");
			data.put("0860","910");
			data.put("0899","912");
			data.put("03","C");
			data.put("0311","C01");
			data.put("0312","C02");
			data.put("0313","C03");
			data.put("0314","C04");
			data.put("0321","C05");
			data.put("0322","C06");
			data.put("0323","C07");
			data.put("0331","C08");
			data.put("0332","C09");
			data.put("0333","C10");
			data.put("0341","C11");
			data.put("0342","C12");
			data.put("0351","C13");
			data.put("0399","C99");
			data.put("04","D");
			data.put("0410","D01");
			data.put("0420","D02");
			data.put("0430","D03");
			data.put("0440","D04");
			data.put("0450","D05");
			data.put("0499","D09");
			data.put("05","E");
			data.put("0510","E01");
			data.put("0599","E09");
			data.put("06","F");
			data.put("0610","F01");
			data.put("0620","F02");
			data.put("0630","F03");
			data.put("0640","F04");
			data.put("0650","F05");
			data.put("0699","F09");
			data.put("07","G");
			data.put("0710","G01");
			data.put("0799","G09");

			return data;
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

	

	// 教育 ZB64 A0801B
	public static String ZB64(String index) {
		/*if ("13".equals(index) || "37".equals(index) || "1B".equals(index)
				|| "2B".equals(index)) {
			return "91";
		}*/
		return index;
	}
	
	//ZB78  調出   A3001
	public static String ZB78(String index) {
		if ("11".equals(index) || "12".equals(index)|| "13".equals(index)) {
			return "39";
		} else if ("14".equals(index)) {
			return "17";
		} else if ("15".equals(index)) {
			return "18";
		} else if ("36".equals(index)) {
			return "37";
		} else if ("37".equals(index)) {
			return "39";
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
	
	
	//ZB77 A2911
	public static String ZB77(String index) {
		if ("4".equals(index)) {
			return "6";
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
