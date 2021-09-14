package com.insigma.siis.local.util;

import java.sql.Statement;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
/**
 *sql×Ö·û´®Æ´½Ó 
 */

public class TYGSsqlUtilTwo { 

	public static String A01zip= "A0000 AS A00,A0101,A0102,A0104,A0107,A0117,A0111A AS A0111C,A0114A AS A0114C,A0141,A0144,A0140 AS A0144B,A0134,"
			+ "A0128,A0196 AS A0601B,A0187A,QRZXL AS A0801A,QRZXW AS A0830A,QRZXWXX AS A0806A,ZZXL AS A0801B,ZZXW AS A0830B,ZZXLXX AS A0806B,A0192A AS A0201B,"
			+ "A1701,A14Z101 AS A1401,A15Z101 AS A1501,A0184,A0160 AS A0151,A0192E AS A0501B,"
			+ "A0192C AS A0504,A0198 AS A0106£¬A0163 as BJXA0109";
	
	public static String A02zip = "A0200 AS RECORDID,A0000 AS A00,A0201B,A0201A as A0201C,A0215A AS A0217,A0219 AS A0277,A0223,A0225,A0243,A0245,A0255";
		
	public static String A39zip="A0000 AS A00,A3921,A3927 AS A3905";
	
	public static String A36zip="A.A3600 AS RECORDID,A.A0000 AS A00,A.A3601,A.A3604A AS A3604C, A.A3607,A.A3611,B.CODE_VALUE AS A3627,SORTID AS A3647";

	public static String A15zip="A1500 AS RECORDID,A0000 AS A00,A1521 AS A1502,A1517";
	
	public static String B01zip="B0111 as B00,B0101,B0104,B0131,B0121 as B0144B,B0142 as B01_ORDER";
	
	public static String FILEINFOzip="A0000 AS A00,IDS AS ID,FILEID AS RECORDID,FILENAME,FILETYPE,BEIZHU,CREATEON AS CREATEDATE";
}
