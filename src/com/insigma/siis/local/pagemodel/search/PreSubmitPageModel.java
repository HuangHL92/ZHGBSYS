package com.insigma.siis.local.pagemodel.search;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

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
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilder;
import com.insigma.siis.local.pagemodel.exportexcel.ExportDataBuilderBcZdy;

public class PreSubmitPageModel extends PageModel {
	public static int i = 0;
	public static int k = 0;
	private static final HttpServletRequest HttpServletRequest = null;
	public static Map<Integer, String> map = new HashMap<Integer, String>();
	private String sScript;
	
	@Override
	public int doInit() throws RadowException {
		i = 0;
		k = 0;
		this.getExecuteSG().addExecuteCode("showdata()");
		return EventRtnType.NORMAL_SUCCESS;
	}

	public String aa(HttpServletRequest request) throws RadowException {
		String viewType = (String) request.getSession().getAttribute("yviewType");
		String tempType = (String) request.getSession().getAttribute("ytempType");
		if("6".equals(tempType) || "8".equals(tempType) || "21".equals(viewType) || "22".equals(viewType) || "23".equals(viewType)){
		//-------------------------------------
			
			sScript=functyppe(request);
			return sScript;
		} else {
		//-------------------------------------
		// 根据判断
		Map<String, String> map = new HashMap<String, String>();
		String num = request.getSession().getAttribute("viewType").toString();
		List<Map<String,String>> bzmclist = (List)request.getSession().getAttribute("bzbclist");
		String id = (String)request.getSession().getAttribute("personids");	
		String ido ="";	
		String idoo ="";	
		/*if((String)request.getSession().getAttribute("checkList") != null && !"".equals((String)request.getSession().getAttribute("checkList"))){
			ido = (String)request.getSession().getAttribute("checkList");
		}
		if((String)request.getSession().getAttribute("checkListAll") != null && !"".equals((String)request.getSession().getAttribute("checkListAll"))){
			idoo = (String)request.getSession().getAttribute("checkListAll");
		}
		if(ido != null && !"".equals(ido) ){ 
			id = ido;
		}else{
			id = idoo;
		}*/
		String[] id1 = id.replace("|", "'").split("@");
		//String tpid = request.getSession().getAttribute("tempType").toString();
		String tpid = request.getSession().getAttribute("tpid").toString();
		//1.不分组无机构名列
			if ("1".equals(num)) {
			int count = 2;
			String sScript = "";
			sScript += "<script type='text/javascript' >";
			sScript += "function a(){";
			
			sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript +="cell.PrintSetAlign(1,0);";
			sScript +="cell.PrintSetOrient(1);";
			sScript += "cell.MergeCells(1, 1, 20, 1);";// 合并单元格
			sScript += "cell.SetRowHeight(1, 40, 1, 0);";
			sScript = yangshi1(sScript);
			sScript += "cell.DrawGridLine(1,3,20,3,5,4,9);";
			
			sScript += "cell.SetCellString(2,2,0,'职务');";
			sScript += "cell.SetCellFontStyle(2,2,0,2);";// 设置字体样式
			sScript += "cell.SetCellFontSize(2,2,0,12);";// 设置字体大小
			sScript += "cell.SetCellAlign(2, 2, 0, 64 + 32);";// 设置单元格对齐方式
			sScript += "cell.SetRowHeight(1, 18, 2, 0);";// 设置单元格列宽
			sScript += "cell.SetRowHeight(1, 21, 3, 0);";
			sScript += "cell.MergeCells(2, 2, 2, 3);";

			sScript += "cell.SetCellString(4,2,0,'姓名');";
			sScript += "cell.SetCellFontStyle(4,2,0,2);";
			sScript += "cell.SetCellFontSize(4,2,0,12);";
			sScript += "cell.SetCellAlign(4, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(4, 2, 4, 3);";

			sScript += "cell.SetCellString(6,2,0,'籍贯');";
			sScript += "cell.SetCellFontStyle(6,2,0,2);";
			sScript += "cell.SetCellFontSize(6,2,0,12);";
			sScript += "cell.SetCellAlign(6, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(6, 2, 6, 3);";

			sScript += "cell.SetCellString(8,2,0,'出生');";
			sScript += "cell.SetCellString(8,3,0,'年月');";
			sScript += "cell.SetCellFontStyle(8,2,0,2);";
			sScript += "cell.SetCellFontStyle(8,3,0,2);";
			sScript += "cell.SetCellFontSize(8,2,0,12);";
			sScript += "cell.SetCellFontSize(8,3,0,12);";
			sScript += "cell.SetCellAlign(8, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(8, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(10,2,0,'全日制');";
			sScript += "cell.SetCellString(10,3,0,'学历');";
			sScript += "cell.SetCellFontStyle(10,2,0,2);";
			sScript += "cell.SetCellFontStyle(10,3,0,2);";
			sScript += "cell.SetCellFontSize(10,2,0,12);";
			sScript += "cell.SetCellFontSize(10,3,0,12);";
			sScript += "cell.SetCellAlign(10, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(10, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(12,2,0,'在职');";
			sScript += "cell.SetCellString(12,3,0,'学历');";
			sScript += "cell.SetCellFontStyle(12,2,0,2);";
			sScript += "cell.SetCellFontStyle(12,3,0,2);";
			sScript += "cell.SetCellFontSize(12,2,0,12);";
			sScript += "cell.SetCellFontSize(12,3,0,12);";
			sScript += "cell.SetCellAlign(12, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(12, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(14,2,0,'入党');";
			sScript += "cell.SetCellString(14,3,0,'时间');";
			sScript += "cell.SetCellFontStyle(14,2,0,2);";
			sScript += "cell.SetCellFontStyle(14,3,0,2);";
			sScript += "cell.SetCellFontSize(14,2,0,12);";
			sScript += "cell.SetCellFontSize(14,3,0,12);";
			sScript += "cell.SetCellAlign(14, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(14, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(16,2,0,'参加工');";
			sScript += "cell.SetCellString(16,3,0,'作时间');";
			sScript += "cell.SetCellFontStyle(16,2,0,2);";
			sScript += "cell.SetCellFontStyle(16,3,0,2);";
			sScript += "cell.SetCellFontSize(16,2,0,12);";
			sScript += "cell.SetCellFontSize(16,3,0,12);";
			sScript += "cell.SetCellAlign(16, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(16, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(18,2,0,'任职');";
			sScript += "cell.SetCellString(18,3,0,'时间');";
			sScript += "cell.SetCellFontStyle(18,2,0,2);";
			sScript += "cell.SetCellFontStyle(18,3,0,2);";
			sScript += "cell.SetCellFontSize(18,2,0,12);";
			sScript += "cell.SetCellFontSize(18,3,0,12);";
			sScript += "cell.SetCellAlign(18, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(18, 3, 0, 64 + 8);";

			sScript += "cell.SetCellString(20,2,0,'备注');";
			sScript += "cell.SetCellFontStyle(20,2,0,2);";
			sScript += "cell.SetCellFontSize(20,2,0,12);";
			sScript += "cell.SetCellAlign(20, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(20, 2, 20, 3);";

			sScript += "cell.SetRowHeight(1, 9, 4, 0);";
			
			
			//===================获取选中人的id加排序==============================={1
			List<String> cunid  = new ArrayList();
			ExportDataBuilderBcZdy expo = new ExportDataBuilderBcZdy();
			List<Map<String, String>> list = null;
			cunid = getPersonID(id);
			/*if(cunid.size()>46) {
				sScript += "cell.InsertRow(50,"+(cunid.size()-46)+",0);";
				}*/
			sScript += "cell.InsertRow(50,65000,0);";
			int a = 0;
			for (a = 0 ; a<cunid.size();a++){
				String sql = "";
				/*if(DBUtil.getDBType() == DBType.MYSQL){
					sql ="select a01.a0000,                                                  "  
					       +"a01.a0101,                                                      "
					       +"a01.a0117A,                                                     "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.a0144,                                                      "
					       +"a02.a0221 a0148,                                                "
					       +"a01.a0192,                                                      " 
					       +"a01.a0141,                                                      "
					       +"(select code_name                                               "
		                   +"from code_value                                                 "
		                   +"where code_type = 'ZB09'                                        "
		                   +"and code_value = a02.a0221) a0149,                              "
					       +"a01.a0134,                                                      "
					       +"a02.a0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223,                                                      "
					       +"a01.a0117,                                                      " 
					       +"(select code_name                                               "
						   +"from code_value                                                 "
						   +" where code_type = 'GB4762'                                     "
						   +" and code_value = a01.a0141) a0141a                             "	                                                      
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104                                   "
					       +"and a01.a0000 in ('"+ cunid.get(a) + "') ";
					sql = sql + " order by a0223 asc";
				} else{
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                "
					       +"a01.a0192,                                                      "
					       +"(select code_name                                               "
					       +"from code_value                                                 "
					       +"where code_type = 'ZB09'                                        "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223,                                                      "
					       +"(select code_name                                               "
						   +"from code_value                                                 "
						   +" where code_type = 'GB4762'                                     "
						   +" and code_value = a01.a0141) a0141a                             "		
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104                                   "
					       +"and a01.a0000 in ('"+ cunid.get(a) + "') ";
					sql = sql + " order by a0223 asc";
				}*/
				sql = "select count(1) from a57 a57 where  a57.a0000= ('"+ cunid.get(a) + "')";
				String sql2 = "select a01.a0117 from a01 a01 where  a01.a0000= ('"+ cunid.get(a) + "')";
				String sql3 = "select a01.a0104 from a01 a01 where  a01.a0000= ('"+ cunid.get(a) + "')";
				List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
				List sqllist2 = HBUtil.getHBSession().createSQLQuery(sql2).list();
				List sqllist3 = HBUtil.getHBSession().createSQLQuery(sql3).list();
				if("0".equals(sqllist1.get(0).toString())){
					if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0)) && !"null".equals(sqllist3.get(0)) && sqllist3.get(0)!=null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"a02.a0000 a02a0000,                                                        "
						       +"a01.a0111a,                                                     "
						       +"c2.code_name a0104,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223                                                     "
						       +"from A01 a01, A02 a02,Code_Value c2 "
						       +"where a02.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c2.code_type = 'GB2261'                                     "
						       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "') ";
					}else if((!"".equals(sqllist2.get(0)) && !"null".equals(sqllist2.get(0))||sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"c1.code_name a0117,                                             "
						       +"a01.a0111a,                                                     "
						       +"a02.a0000 a02a0000,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223                                                     "
						       +"from A01 a01, A02 a02,Code_Value c1 "
						       +"where a02.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c1.code_type = 'GB3304'                                     "
						       +"and c1.code_value = a01.a0117                                   "
						       +" and a02.a0221 is not null         "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"a01.a0000 a01a0000,                                             "
						       +"a01.a0111a,                                                     "
						       +"a02.a0000 a02a0000,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223                                                     "
						       +"from A01 a01, A02 a02 "
						       +"where a02.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +" and a02.a0221 is not null         "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}else{
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"c1.code_name a0117,                                             "
						       +"a01.a0111a,                                                     "
						       +"c2.code_name a0104,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223                                                     "
						       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
						       +"where a02.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c1.code_type = 'GB3304'                                     "
						       +"and c1.code_value = a01.a0117                                   "
						       +"AND c2.code_type = 'GB2261'                                     "
						       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}
				}else{
					if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0)) && !"null".equals(sqllist3.get(0)) && sqllist3.get(0)!=null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"a01.a0000 a01a0000,                                             "
						       +"a01.a0111a,                                                     "
						       +"c2.code_name a0104,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
						       +"from A01 a01, A02 a02,Code_Value c2,A57 a57 "
						       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c2.code_type = 'GB2261'                                     "
						       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}else if((!"".equals(sqllist2.get(0))&&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"c1.code_name a0117,                                             "
						       +"a01.a0111a,                                                     "
						       +"a02.a0000 a02a0000,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
						       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
						       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c1.code_type = 'GB3304'                                     "
						       +"and c1.code_value = a01.a0117                                   "
						       +"AND c2.code_type = 'GB2261'                                     "
						       +" and a02.a0221 is not null        "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"a01.a0000 a01a0000,                                             "
						       +"a01.a0111a,                                                     "
						       +"a02.a0000 a02a0000,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
						       +"from A01 a01, A02 a02,A57 a57 "
						       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +" and a02.a0221 is not null        "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}else{
						//------------------
						sql ="select a01.a0000,                                               "  
						       +"a01.a0101,                                                      "
						       +"c1.code_name a0117,                                             "
						       +"a01.a0111a,                                                     "
						       +"c2.code_name a0104,                                             "
						       +"a01.a0107,                                                      "
						       +"a02.a0216a,                                                     "
						       +"a01.QRZXL,                                                      "
						       +"a01.ZZXL,                                                       "
						       +"a01.A0144,                                                      "
						       +"a02.a0221 a0148,                                                      "
						       +"a01.a0192,                                                      "
						       +"(select code_name "
						       +"from code_value "
						       +"where code_type = 'ZB09' "
						       +"and code_value = a02.a0221) a0149,                              "
						       +"a01.A0134,                                                      "
						       +"a02.A0243,                                                      "
						       +"a02.A0288,                                                      "
						       +"a02.A0201A,                                                     "
						       +"a02.A0201b,                                                     "
						       +"a01.a0180,                                                      "
						       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
						       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
						       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
						       +"and a02.A0255 = '1'                                             "
						       +"and a01.status = '1'                                            "
						       +"AND c1.code_type = 'GB3304'                                     "
						       +"and c1.code_value = a01.a0117                                   "
						       +"AND c2.code_type = 'GB2261'                                     "
						       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
						       +"and a01.a0000 = ('"+ cunid.get(a) + "')  ";
					}
				}
				sql = sql + " order by a0223 asc";
				bzmclist = expo.getListBySqlforMc(sql, true, tempType);
				String zhiwu = "";
				String renztime = "";
				Map<String, String> map1 = new HashMap<String, String>();
				List<String> e = new ArrayList<String>();
				int  height = 0;
				for (int i = 0; i < bzmclist.size(); i++) {
//					Map<String, String> b = nsf(bzmclist, i);
					map1 = bzmclist.get(i);
					String zhizhu=map1.get("a0216a");
					if(zhizhu != null && !zhizhu.equals("null") &&!"".equals(zhizhu) ){
						zhizhu = zhizhu.replace("\n","");
						zhiwu = (zhizhu +"\\r\\n");
					}else{
						zhiwu += (""+"\\r\\n");
					}
					map1.put("a0216a",zhiwu);
					String d1 = "";
					String d2 = "";
					String d3 = "";
					d1 = map1.get("a0104");// 性别
					d2 = map1.get("a0117");// 民族
					if(map1.get("a0141a") != null && !"".equals(map1.get("a0141a"))){
						d3 = map1.get("a0141a").replace("\n", "\\r\\n");// 第一党派
					}else{
						d3 = map1.get("a0141a");// 第一党派
					}
					//------进行女少非的判断_wx
					if((d1 == null ||"".equals(d1)) && (d2 != null && !"".equals(d2))){
						String d11 = "";
						if(d2.equals("汉族")){
							d11 = map1.get("a0101");
						}else{
							d11 = map1.get("a0101")+"\\r\\n"+"(" + d2+ ")";
						}
						map1.put("a0101", d11);
					}else if((d1 != null && !"".equals(d1)) && (d2 == null ||"".equals(d2))){
						String d11 = "";
						if(d1.equals("女")){
							d11 = map1.get("a0101")+"\\r\\n"+"(女)";
						}else{
							d11 = map1.get("a0101");
						}
						map1.put("a0101", d11);
					}else if((d1 == null || "".equals(d1)) && (d2 == null || "".equals(d2))){
						String d11 = map1.get("a0101");
						map1.put("a0101", d11);
					}else{
						
						if (d1 != null && d1.equals("女") && d2.equals("汉族")) {
							String d11 = map1.get("a0101")+"\\r\\n"+"(女)";
							map1.put("a0101", d11);
						}
						if (d1 != null && d1.equals("女") && !d2.equals("汉族")) {
							String d12 = map1.get("a0101")+"\\r\\n"+"(女," + d2+ ")";
							map1.put("a0101", d12);
						}
						if (d1 != null && !d1.equals("女") && !d2.equals("汉族")) {
							String d13 = map1.get("a0101")+"\\r\\n"+"("+ d2+ ")";
							map1.put("a0101", d13);
						}
					}
					if(!"".equals(d3)&&d3!=null){
						if("中共党员".equals(d3)){
							try {
								String	mes60 = map1.get("a0144").replace("\n", "\\r\\n");//入党时间
								if(mes60 != null ){
								String mes61 = mes60.replace(".", "");
								String mes62 = mes61.substring(0,4)+".";
								String mes63 = mes61.substring(4, 6);
								String	mes6 = mes62 + mes63;
								map1.put("a0144", mes6);
								}
							} catch (Exception e1) {
								String	mes60 = "";
								if(map1.get("a0144") != null && !"".equals(map1.get("a0144"))){
									mes60 = map1.get("a0144").replace("\n", "\\r\\n");//入党时间
								}else{
									mes60 = map1.get("a0144");//入党时间
								}
								map1.put("a0144", mes60);
							}
						}else{
							map1.put("a0144", map1.get("a0141a"));
						}
					}else{
						map1.put("a0144", "");
					}
					try {
						String	mes30 = map1.get("a0243").replace("\n", "\\r\\n");//任职时间
						if( mes30!=null){
						String mes31 = mes30.replace(".", "");
						String mes32 = mes31.substring(0,4)+".";
						String mes33 = mes31.substring(4, 6);
						String	mes3 = mes32 + mes33;
						map1.put("a0243", mes3);
						}
					} catch (Exception e1) {
						String	mes30 = "";
						if(map1.get("a0243") != null && !"".equals(map1.get("a0243"))){
							mes30 = map1.get("a0243").replace("\n", "\\r\\n");//任职时间
						}else{
							mes30 = map1.get("a0243");//任职时间
						}
						map1.put("a0243", mes30);
					}
					try {
						String	mes40 = map1.get("a0107").replace("\n", "\\r\\n");//生日
						String mes41 = mes40.replace(".", "");
						String mes42 = mes41.substring(0,4)+".";
						String mes43 = mes41.substring(4, 6);
						String	mes4 = mes42 + mes43;
						map1.put("a0107", mes4);
						
					} catch (Exception e1) {
						// TODO: handle exception
						String	mes40 = "";
						if(map1.get("a0107") != null && !"".equals(map1.get("a0107"))){
							mes40 = map1.get("a0107").replace("\n", "\\r\\n");//生日
						}else{
							mes40 = map1.get("a0107");//生日
						}
						map1.put("a0107", mes40);
					}
					try {
						String	mes70 = map1.get("a0134").replace("\n", "\\r\\n");//参加工作时间
						String mes71 = mes70.replace(".", "");
						String mes72 = mes71.substring(0,4)+".";
						String mes73 = mes71.substring(4, 6);
						String	mes7 = mes72 + mes73;
						map1.put("a0134", mes7);
					} catch (Exception e1) {
						String	mes70 = "";
						if(map1.get("a0134") != null && !"".equals(map1.get("a0134"))){
							mes70 = map1.get("a0134").replace("\n", "\\r\\n");//参加工作时间
						}else{
							mes70 = map1.get("a0134");//参加工作时间
						}
						map1.put("a0134", mes70);
					}
					renztime += map1.get("a0243")+"\\r\\n";
					map1.put("a0243",renztime);
					if(i == 0){
						height = 63;
					}else{
						height = 63+i*20;
					}
				}
				e.add(map1.get("a0216a"));
				e.add(map1.get("a0101"));
				if(map1.get("a0111a") != null && !"".equals(map1.get("a0111a"))){
					e.add(map1.get("a0111a").replace("\n", "\\r\\n"));
				}else{
					e.add(map1.get("a0111a"));
				}
				e.add(map1.get("a0107"));
				if(map1.get("qrzxl") != null && !"".equals(map1.get("qrzxl"))){
					e.add(map1.get("qrzxl").replace("\n", "\\r\\n"));
				}else{
					e.add(map1.get("qrzxl"));
				}
				if(map1.get("zzxl") != null && !"".equals(map1.get("zzxl"))){
					e.add(map1.get("zzxl").replace("\n", "\\r\\n"));
				}else{
					e.add(map1.get("zzxl"));
				}
				e.add(map1.get("a0144"));
				e.add(map1.get("a0134"));
				e.add(map1.get("a0243"));
				if(map1.get("a0192") != null && !"".equals(map1.get("a0192"))){
					e.add(map1.get("a0192").replace("\n", "\\r\\n"));
				}else{
					e.add(map1.get("a0192"));
				}
				for (int j = 0; j < e.size(); j++) {
					String mesaa = "";
					try {
						e.get(j).length();
						mesaa = e.get(j);
					} catch (Exception e1) {
						mesaa = "";
					}
					sScript += "cell.SetCellString("+ count + "," + (a + 5) + ",0,'" + mesaa +"');";
					sScript += "cell.SetCellFontSize("+ count + "," + (a + 5) + ",0,12);";//设置单元格字体大小。
					sScript += "cell.SetCellAlign("+ count + "," + (a + 5) + ",0, 64 + 8);";//设置指定单元格的对齐方式。
					sScript += "cell.SetCellHideZero ("+ count + "," + (a + 5) + ",0, 0);";//设置指定单元格是否隐藏0值
					sScript += "cell.SetRowHeight(1, "+height+", "+ (a + 5) + ", 0);";//设置指定行的行高。
					sScript += "cell.SetCellTextStyle("+ count + "," + (a + 5) + ",0,2);";// 设置自动换行
					count += 2;
				}
				count = 2;
			}
			sScript += "cell.DeleteRow("+ ((a + 5)) + "," +  (65050 - ((a + 5))) + ",0);";//删除多余的行	
			
			
			//===================================================================
			sScript += "}\n";
			sScript += "</script>";
			System.out.println(sScript);
			return sScript;
		
		}
		//2.不分组有机构名列
		if ("2".equals(num)) {
			int countRow = 0;
			int count = 2;
			String sScript = "";
			sScript += "<script type='text/javascript' >";
			sScript += "function a(){";
			sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript +="cell.PrintSetAlign(1,0);";
			sScript +="cell.PrintSetOrient(1);";
			sScript += "cell.InsertCol(20,1,0);"; 
			sScript += "cell.MergeCells(1, 1, 21, 1);";// 合并单元格
			sScript += "cell.SetRowHeight(1, 40, 1, 0);";
			sScript += "cell.SetColWidth (1, 80,1 , 0);";
			sScript += "cell.SetColWidth (1, 11,2 , 0);";
			sScript += "cell.SetColWidth (1, 80,3 , 0);";
			sScript += "cell.SetColWidth (1, 16,4 , 0);";
			sScript += "cell.SetColWidth (1, 82,5 , 0);";
			sScript += "cell.SetColWidth (1, 16,6 , 0);";
			sScript += "cell.SetColWidth (1, 73,7 , 0);";
			sScript += "cell.SetColWidth (1, 16,8 , 0);";
			sScript += "cell.SetColWidth (1, 64,9 , 0);";
			sScript += "cell.SetColWidth (1, 16,10 , 0);";
			sScript += "cell.SetColWidth (1, 70,11 , 0);";
			sScript += "cell.SetColWidth (1, 16,12 , 0);";
			sScript += "cell.SetColWidth (1, 70,13 , 0);";
			sScript += "cell.SetColWidth (1, 16,14 , 0);";
			sScript += "cell.SetColWidth (1, 64,15 , 0);";
			sScript += "cell.SetColWidth (1, 16,16 , 0);";
			sScript += "cell.SetColWidth (1, 64,17 , 0);";
			sScript += "cell.SetColWidth (1, 16,18 , 0);";
			sScript += "cell.SetColWidth (1, 64,19 , 0);";
			sScript += "cell.SetColWidth (1, 16,20 , 0);";
			sScript += "cell.SetColWidth (1, 121,21 , 0);";
			sScript += "cell.DrawGridLine(1,3,21,3,5,4,9);";
			sScript += "cell.SetCellString(3,2,0,'职务');";
			sScript += "cell.SetCellFontStyle(3,2,0,2);";// 设置字体样式
			sScript += "cell.SetCellFontSize(3,2,0,12);";// 设置字体大小
			sScript += "cell.SetCellAlign(3, 2, 0, 64 + 32);";// 设置单元格对齐方式
			sScript += "cell.SetRowHeight(1, 18, 2, 0);";// 设置单元格行高
			sScript += "cell.SetRowHeight(1, 21, 3, 0);";
			sScript += "cell.MergeCells(3, 2, 3, 3);";
			sScript += "cell.SetCellString(5,2,0,'姓名');";
			sScript += "cell.SetCellFontStyle(5,2,0,2);";
			sScript += "cell.SetCellFontSize(5,2,0,12);";
			sScript += "cell.SetCellAlign(5, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(5, 2, 5, 3);";
			sScript += "cell.SetCellString(7,2,0,'籍贯');";
			sScript += "cell.SetCellFontStyle(7,2,0,2);";
			sScript += "cell.SetCellFontSize(7,2,0,12);";
			sScript += "cell.SetCellAlign(7, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(7, 2, 7, 3);";
			sScript += "cell.SetCellString(9,2,0,'出生');";
			sScript += "cell.SetCellString(9,3,0,'年月');";
			sScript += "cell.SetCellFontStyle(9,2,0,2);";
			sScript += "cell.SetCellFontStyle(9,3,0,2);";
			sScript += "cell.SetCellFontSize(9,2,0,12);";
			sScript += "cell.SetCellFontSize(9,3,0,12);";
			sScript += "cell.SetCellAlign(9, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(9, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(11,2,0,'全日制');";
			sScript += "cell.SetCellString(11,3,0,'学历');";
			sScript += "cell.SetCellFontStyle(11,2,0,2);";
			sScript += "cell.SetCellFontStyle(11,3,0,2);";
			sScript += "cell.SetCellFontSize(11,2,0,12);";
			sScript += "cell.SetCellFontSize(11,3,0,12);";
			sScript += "cell.SetCellAlign(11, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(11, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(13,2,0,'在职');";
			sScript += "cell.SetCellString(13,3,0,'学历');";
			sScript += "cell.SetCellFontStyle(13,2,0,2);";
			sScript += "cell.SetCellFontStyle(13,3,0,2);";
			sScript += "cell.SetCellFontSize(13,2,0,12);";
			sScript += "cell.SetCellFontSize(13,3,0,12);";
			sScript += "cell.SetCellAlign(13, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(13, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(15,2,0,'入党');";
			sScript += "cell.SetCellString(15,3,0,'时间');";
			sScript += "cell.SetCellFontStyle(15,2,0,2);";
			sScript += "cell.SetCellFontStyle(15,3,0,2);";
			sScript += "cell.SetCellFontSize(15,2,0,12);";
			sScript += "cell.SetCellFontSize(15,3,0,12);";
			sScript += "cell.SetCellAlign(15, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(15, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(17,2,0,'参加工');";
			sScript += "cell.SetCellString(17,3,0,'作时间');";
			sScript += "cell.SetCellFontStyle(17,2,0,2);";
			sScript += "cell.SetCellFontStyle(17,3,0,2);";
			sScript += "cell.SetCellFontSize(17,2,0,12);";
			sScript += "cell.SetCellFontSize(17,3,0,12);";
			sScript += "cell.SetCellAlign(17, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(17, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(19,2,0,'任职');";
			sScript += "cell.SetCellString(19,3,0,'时间');";
			sScript += "cell.SetCellFontStyle(19,2,0,2);";
			sScript += "cell.SetCellFontStyle(19,3,0,2);";
			sScript += "cell.SetCellFontSize(19,2,0,12);";
			sScript += "cell.SetCellFontSize(19,3,0,12);";
			sScript += "cell.SetCellAlign(19, 2, 0, 64 + 16);";
			sScript += "cell.SetCellAlign(19, 3, 0, 64 + 8);";
			sScript += "cell.SetCellString(21,2,0,'备注');";
			sScript += "cell.SetCellFontStyle(21,2,0,2);";
			sScript += "cell.SetCellFontSize(21,2,0,12);";
			sScript += "cell.SetCellAlign(21, 2, 0, 64 + 32);";
			sScript += "cell.MergeCells(21, 2, 21, 3);";
			sScript += "cell.SetRowHeight(1, 9, 4, 0);";
			sScript += "cell.InsertRow(50,65000,0);";
			//=================================================
//			String sql1 = "select t.a0201b from (select a.a0201b,a.b0111,a.b0114 from (select a02.a0201b,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+id.replace("|", "'").replace("@", ",")+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201b,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t";
			String sql1 = "select t.a0201b from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+id.replace("|", "'").replace("@", ",")+") and a02.a0255 = '1' and a02.a0281 = 'true' order by length(a0201b),a0201b asc) t";
			List<List<Map<String, String>>> personlist = new ArrayList<List<Map<String, String>>>();
			List<String> cunid   = new ArrayList<String>();
			List disA0201b = HBUtil.getHBSession().createSQLQuery(sql1).list();
			int f = 0;
			for (int q = 0; q < disA0201b.size(); q++) { 
				String jgbm = disA0201b.get(q).toString();
				if(jgbm != null && !jgbm.equals("-1")){
					cunid  = new ArrayList<String>();
					cunid = getPersonID(id);
//					personlist = new ArrayList<List<Map<String, String>>>();
					bzmclist = getPersonMess(cunid,jgbm,"1","4",4);
					personlist.add(bzmclist);
				}

			}
			//===================================================
			int m = 0;
			for(m =0;m<personlist.size();m++){
				bzmclist = personlist.get(m);
				for (int i = 0; i < bzmclist.size(); i++) {
//					Map<String, String> b = nsf1(bzmclist, i);
					Map<String, String> map1 = bzmclist.get(i);
					map1 = special(map1); //特殊项处理
					List<String> e = new ArrayList<String>();
					e.add(map1.get("a0201a"));
					e.add(map1.get("a0216a"));
					e.add(map1.get("a0101"));
					e.add(map1.get("a0111a"));
					e.add(map1.get("a0107"));
					e.add(map1.get("qrzxl"));
					e.add(map1.get("zzxl"));
					e.add(map1.get("a0144"));
					e.add(map1.get("a0134"));
					e.add(map1.get("a0243"));
					e.add(map1.get("a0192"));
					for (int j = 0; j < e.size(); j++) {
						String mesaa = "";
						try {
							e.get(j).length();
							mesaa = e.get(j);
							if( e.get(j) != null && !mesaa.equals("null") ){
								mesaa = e.get(j);
							}else{
								mesaa = "";
							}
						} catch (Exception e1) {
							mesaa = "";
						}
						sScript += "cell.SetCellString("+ (count-1) + "," + (m + (5+f) ) + ",0,'" + mesaa + "');";
						sScript += "cell.SetCellFontSize("+ (count-1) + "," + (m + (5+f) ) + ",0,12);";
						sScript += "cell.SetCellAlign("+ (count-1) + "," + (m + (5+f) ) + ",0, 64 + 8);";
						sScript += "cell.SetCellHideZero ("+ (count-1) + "," + (m + (5+f) ) + ",0, 0);";
						sScript += "cell.SetRowHeight(1, 63, "+ (m + (5+f)) + ", 0);";
						sScript += "cell.SetCellTextStyle("+ (count-1) + "," + (m + (5+f) ) + ",0,2);";// 设置自动换行
						count += 2;
					}
					f++;
					countRow = (m + (5+f) );
					count = 2;
				}
				
			}
			/*if(cunid.size()>46) {
				sScript += "cell.InsertRow(50,"+(cunid.size()-46)+",0);";
				}*/
			sScript += "cell.DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
			sScript += "}\n";
			sScript += "</script>";
			return sScript;
		}
		//3.按机构排序
		if ("3".equals(num)) {
			int countRow = 0;
			int count = 2;
			int geshu = 0;
			String sScript = "";
//			String sql1 = "SELECT t.a0201a from(select distinct a0201a,a0201b from a02 order by length(a0201b),a0201b asc) t";
			String sql1 = "select t.a0201a from (select a.a0201a,a.b0111,a.b0114 from (select a02.a0201a,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+id.replace("|", "'").replace("@", ",")+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201a,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sql1).list();
			int rowNumber = 1;
			int falsecount = 0;
			sScript += "<script type='text/javascript' >";
			sScript += "function a(){";
			sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript +="cell.PrintSetAlign(1,0);";
			sScript +="cell.PrintSetOrient(1);";
//			sScript += "cell.PrintSetMargin(30,30,30,30);";
			sScript += "cell.SetRowHeight(1, 41, 1, 0);";
			sScript = yangshi1(sScript);
			sScript += "cell.InsertRow(50,65000,0);";
			//=================================================
			String sql12 = "select t.a0201b from (select a.a0201b,a.b0111,a.b0114 from (select a02.a0201b,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+id.replace("|", "'").replace("@", ",")+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201b,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t";
			List<List<Map<String, String>>> personlist = new ArrayList<List<Map<String, String>>>();
			List<String> cunid   = new ArrayList<String>();
			List disA0201b = HBUtil.getHBSession().createSQLQuery(sql12).list();
			int f = 0;
			for (int q = 0; q < disA0201b.size(); q++) { 
				String jgbm = disA0201b.get(q).toString();
				cunid  = new ArrayList<String>();
				cunid = getPersonID(id);
//				personlist = new ArrayList<List<Map<String, String>>>();
				bzmclist = getPersonMess(cunid,jgbm,"1","4",4);
				personlist.add(bzmclist);
			}
			//===================================================
			/*if((disA0201a.size()*4+cunid.size())>50) {
				//sScript += "cell.InsertRow(50,"+((disA0201a.size()*4+cunid.size())-50)+",0);";
				sScript += "cell.InsertRow(50,100,0);";
				}*/
			for(int m =0;m<personlist.size();m++){
				bzmclist = personlist.get(m);
				for (int j = 0; j < disA0201a.size(); j++) { 
					geshu = 0;
					Object obj1 = disA0201a.get(j);
					if (obj1 != null) {
						Map<String, String> map1 = new HashMap<String, String>();
						for (int i = 0; i < bzmclist.size(); i++) {
							map1 = bzmclist.get(i);
							if (map1.get("a0201a") != null && map1.get("a0201a").equals(obj1.toString())) {
								sScript += "cell.SetCellString(1,"+ rowNumber + ",0,'" + obj1.toString() + "');";//1
							sScript = yangshi2(sScript, rowNumber);
								geshu++;
								map1 = special(map1); //特殊项处理
								List<String> e = new ArrayList<String>();
								e.add(map1.get("a0216a"));
								e.add(map1.get("a0101"));
								e.add(map1.get("a0111a"));
								e.add(map1.get("a0107"));
								e.add(map1.get("qrzxl"));
								e.add(map1.get("zzxl"));
								e.add(map1.get("a0144"));
								e.add(map1.get("a0134"));
								e.add(map1.get("a0243"));
								e.add(map1.get("a0192"));
								for (int k = 0; k < e.size(); k++) {
									String mesaa = "";
									try {
										e.get(k).length();
										mesaa = e.get(k);
									} catch (Exception e1) {
										mesaa = "";
									}
									sScript += "cell.SetCellString("+ count+ ","+ (rowNumber + geshu+3)+ ",0,'" + mesaa + "');";
									sScript += "cell.SetCellFontSize("+ count + "," + (rowNumber + 3 + geshu)+ ",0,12);";
									sScript += "cell.SetCellAlign("+ count + "," + (rowNumber + 3 + geshu)+",0, 64 + 8);";
									sScript += "cell.SetCellHideZero ("+ count + "," + (rowNumber + 3 + geshu)+ ",0, 0);";
									sScript += "cell.SetRowHeight(1, 60, "+ (rowNumber + 3 + geshu)+", 0);";
									sScript += "cell.SetCellTextStyle("+ count + "," + (rowNumber + 3 + geshu)+ ",0,2);";// 设置自动换行
									count+=2;
									countRow = (rowNumber + geshu+3);
								}
								f++;
								count = 2;
							}else{
								falsecount++;//如果不等于则+1
							}
						}
						if(falsecount == bzmclist.size()){
							rowNumber = rowNumber + geshu;
						}else{
							rowNumber = rowNumber + geshu + 4;
						}
					}
					falsecount = 0;
				}
			}
			sScript += "cell.DeleteRow("+ (countRow+1)  + "," +  (65050 - (countRow+1 )) + ",0);";//删除多余的行	
			sScript += "}\n";
			sScript += "</script>";
			return sScript;
		}
		//4.按职务层次分组
		if ("4".equals(num)) {
			int countRow = 0;
			int count = 2;
			int geshu = 0;
			String sScript = "";
			String sql1 = "select b.code_name from code_value b,(select a02.a0221 from a02 where a02.a0000 in("+id.replace("|", "'").replace("@", ",")+") group by a02.a0221  ) a where b.code_value = a.a0221 and b.code_type='ZB09' order by a.a0221 asc ";
			System.out.println(sql1);
			List disA0149s = HBUtil.getHBSession().createSQLQuery(sql1).list();
			int rowNumber = 1;
			int falsecount = 0;
			sScript += "<script type='text/javascript' >";
			sScript += "function a(){";
			sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript +="cell.PrintSetAlign(1,0);";
			sScript +="cell.PrintSetOrient(1);";
			sScript += "cell.SetRowHeight(1, 41, 1, 0);";
			sScript += "cell.InsertRow(50,65000,0);";
			sScript = yangshi1(sScript);
			//=================================================
			List<String> cunid  = new ArrayList<String>();
			List<String> cunid1  = new ArrayList<String>();
			cunid = getPersonID(id);
			cunid1 = getPersonIDWucc(id);//wu cengci id
			List list  =  new ArrayList();
			list.add(cunid);
			list.add(cunid1);
			System.out.println(list.get(1).toString());
			int a = 0;
			/*if((disA0149s.size()*4+cunid.size())>50) {
				sScript += "cell.InsertRow(50,"+((disA0149s.size()*4+cunid.size())-50)+",0);";
				}*/
			boolean cengcc = false;
			String cengccq = "";
			int countperson = 0;
			for(int o = 0;o<list.size();o++ ){
			bzmclist = getPersonMess((List) list.get(o),"jgbm","0","4",o);
			//===================================================
			if(o==0){
				countperson = disA0149s.size();
			}else{
				countperson = ((List)list.get(1)).size();
			}
			for (int j = 0; j < countperson; j++) { 
				geshu = 0;
				Object obj1 = "";
				if(o == 0){
					obj1 = disA0149s.get(j);
				}
				if (obj1 != null) {
					Map<String, String> map1 = new HashMap<String, String>();
					System.out.println( bzmclist.size());
					for (int i = 0; i < bzmclist.size(); i++) {
						map1 = bzmclist.get(i);
						if(o == 0){
							cengcc = map1.get("a0149").equals(obj1);
							cengccq = obj1.toString();
						}else{
							cengcc = map1.get("a0149").equals("") ||map1.get("a0149").equals("null") ;
							cengccq = "wu ziwucengci";
						}
						if (map1.get("a0149") != null  && cengcc ) {
							sScript += "cell.SetCellString(1,"+ rowNumber + ",0,'" + cengccq + "');";//1
							sScript = yangshi2(sScript, rowNumber);
							geshu++;
							map1 = special(map1); //特殊项处理
							List<String> mess = new ArrayList<String>();
							mess.add(map1.get("a0216a"));
							mess.add(map1.get("a0101"));
							mess.add(map1.get("a0111a"));
							mess.add(map1.get("a0107"));
							mess.add(map1.get("qrzxl"));
							mess.add(map1.get("zzxl"));
							mess.add(map1.get("a0144"));
							mess.add(map1.get("a0134"));
							mess.add(map1.get("a0243"));
							mess.add(map1.get("a0192"));
							for (int k = 0; k < mess.size(); k++) {
								String mesaa = "";
								try {
									mess.get(k).length();
									mesaa = mess.get(k);
								} catch (Exception e1) {
									mesaa = "";
								}
								sScript += "cell.SetCellString("+ count+ ","+ (rowNumber + geshu+3)+ ",0,'" + mesaa + "');";
								sScript += "cell.SetCellFontSize("+ count + "," + (rowNumber + 3 + geshu)+ ",0,12);";
								sScript += "cell.SetCellAlign("+ count + "," + (rowNumber + 3 + geshu)+",0, 64 + 8);";
								sScript += "cell.SetCellHideZero ("+ count + "," + (rowNumber + 3 + geshu)+ ",0, 0);";
								sScript += "cell.SetRowHeight(1, 60, "+ (rowNumber + 3 + geshu)+", 0);";
								sScript += "cell.SetCellTextStyle("+ count + "," + (rowNumber + 3 + geshu)+ ",0,2);";// 设置自动换行
								count+=2;
								countRow = (rowNumber + geshu+3);
							}
							count = 2;
						}else{
							falsecount++;
						}
					}
					if(falsecount == bzmclist.size()){
						rowNumber = rowNumber + geshu;
					}else{
						rowNumber = rowNumber + geshu + 4;
					}
				}
				falsecount = 0;
			}
			}
			sScript += "cell.DeleteRow("+ (countRow+1) + "," +  (65050 - (countRow+1)) + ",0);";//删除多余的行	
			sScript += "}\n";
			sScript += "</script>";
			return sScript;
		}
		//5.按职务层次分组，按机构排序
		if ("5".equals(num)) {
			int countRow = 0;
			int count = 2;
			int geshu = 0;
			String sScript = "";
			String sqlcc = "select cv.code_name a0104 from code_value cv,(select a02.a0221 from a02 where a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") group by a02.a0221 ) a where cv.code_value=a.a0221 and cv.code_type='ZB09' order by a.a0221 asc";
			System.out.println(sqlcc);
			List disA0149s = HBUtil.getHBSession().createSQLQuery(sqlcc).list();
			int rowNumber = 1;
			int falsecount = 0;
			sScript += "<script type='text/javascript' >";
			sScript += "function a(){";
			sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript +="cell.PrintSetAlign(1,0);";
			sScript +="cell.PrintSetOrient(1);";
			sScript += "cell.InsertCol(20,1,0);"; 
			sScript += "cell.MergeCells(1, 1, 21, 1);";// 合并单元格
			sScript += "cell.SetColWidth (1, 80,1 , 0);";
			sScript += "cell.SetColWidth (1, 11,2 , 0);";
			sScript += "cell.SetColWidth (1, 80,3 , 0);";
			sScript += "cell.SetColWidth (1, 16,4 , 0);";
			sScript += "cell.SetColWidth (1, 82,5 , 0);";
			sScript += "cell.SetColWidth (1, 16,6 , 0);";
			sScript += "cell.SetColWidth (1, 73,7 , 0);";
			sScript += "cell.SetColWidth (1, 16,8 , 0);";
			sScript += "cell.SetColWidth (1, 64,9 , 0);";
			sScript += "cell.SetColWidth (1, 16,10 , 0);";
			sScript += "cell.SetColWidth (1, 70,11 , 0);";
			sScript += "cell.SetColWidth (1, 16,12 , 0);";
			sScript += "cell.SetColWidth (1, 70,13 , 0);";
			sScript += "cell.SetColWidth (1, 16,14 , 0);";
			sScript += "cell.SetColWidth (1, 64,15 , 0);";
			sScript += "cell.SetColWidth (1, 16,16 , 0);";
			sScript += "cell.SetColWidth (1, 64,17 , 0);";
			sScript += "cell.SetColWidth (1, 16,18 , 0);";
			sScript += "cell.SetColWidth (1, 64,19 , 0);";
			sScript += "cell.SetColWidth (1, 16,20 , 0);";
			sScript += "cell.SetColWidth (1, 121,21 , 0);";
			sScript += "cell.InsertRow(50,65000,0);";
			//=================================================
			String sql12 = "select t.a0201b from (select a.a0201b,a.b0111,a.b0114 from (select a02.a0201b,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+id.replace("|", "'").replace("@", ",")+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201b,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t";
			List<List<Map<String, String>>> personlist = new ArrayList<List<Map<String, String>>>();
			List<String> cunid   = new ArrayList<String>();
			List disA0201b = HBUtil.getHBSession().createSQLQuery(sql12).list();
			int f = 0;
			for (int q = 0; q < disA0201b.size(); q++) { 
				String jgbm = disA0201b.get(q).toString();
				cunid  = new ArrayList<String>();
				cunid = getPersonID(id);
				bzmclist = getPersonMess(cunid,jgbm,"1","5",4);
				personlist.add(bzmclist);
			}
			//===================================================
			for (int j = 0; j < disA0149s.size(); j++) { 			
				geshu = 0;
				Object obj1 = disA0149s.get(j);
				if (obj1 != null) {
					Map<String, String> map1 = new HashMap<String, String>();
					//==============================
					for(int m =0;m<personlist.size();m++){
						bzmclist = personlist.get(m);
					//==============================
					for (int i = 0; i < bzmclist.size(); i++) {
						map1 = bzmclist.get(i);
						if (map1.get("a0149") != null && map1.get("a0149").equals(obj1.toString())) {
						
						sScript += "cell.MergeCells(1, "+ rowNumber + ", 22, "+ rowNumber + ");";// 合并单元格
						sScript += "cell.SetCellFontSize(1,"+ rowNumber + ",0,17);";// 设置字体大小
						sScript += "cell.SetCellFontStyle(1,"+ rowNumber + ",0,2);";// 设置字体样式
						sScript += "cell.SetCellAlign(1, "+ rowNumber  + ", 0, 32 + 4);";// 设置单元格对齐方式
						sScript += "cell.SetRowHeight(1, 40, "+ rowNumber  + ", 0);";// 设置单元格行高
						sScript += "cell.SetCellString(1,"+ rowNumber + ",0,'" + obj1.toString() + "');";//1
						
						sScript += "cell.SetCellString(3,"+ (rowNumber + 1) + ",0,'职务');";//2
						sScript += "cell.SetCellFontStyle(3,"+ (rowNumber + 1) + ",0,2);";// 设置字体样式
						sScript += "cell.SetCellFontSize(3,"+ (rowNumber + 1) + ",0,12);";// 设置字体大小
						sScript += "cell.SetCellAlign(3, "+ (rowNumber + 1) + ", 0, 64 + 32);";// 设置单元格对齐方式
						sScript += "cell.SetRowHeight(1, 18, "+ (rowNumber + 1) + ", 0);";// 设置单元格列宽
						sScript += "cell.SetRowHeight(1, 21, "+ (rowNumber + 2) + ", 0);";
						sScript += "cell.MergeCells(3, "+ (rowNumber + 1) + ", 3, "+ (rowNumber + 2) + ");";
						
						sScript += "cell.SetCellString(5,"+ (rowNumber + 1) + ",0,'姓名');";
						sScript += "cell.SetCellFontStyle(5,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontSize(5,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellAlign(5, "+ (rowNumber + 1) + ", 0, 64 + 32);";
						sScript += "cell.MergeCells(5, "+ (rowNumber + 1) + ", 5, "+ (rowNumber + 2) + ");";
						
						sScript += "cell.SetCellString(7,"+ (rowNumber + 1) + ",0,'籍贯');";
						sScript += "cell.SetCellFontStyle(7,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontSize(7,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellAlign(7, "+ (rowNumber + 1) + ", 0, 64 + 32);";
						sScript += "cell.MergeCells(7, "+ (rowNumber + 1) + ", 7, "+ (rowNumber + 2) + ");";
						
						sScript += "cell.SetCellString(9,"+ (rowNumber + 1) + ",0,'出生');";
						sScript += "cell.SetCellString(9,"+ (rowNumber + 2) + ",0,'年月');";
						sScript += "cell.SetCellFontStyle(9,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(9,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(9,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(9,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(9, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(9, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(11,"+ (rowNumber + 1) + ",0,'全日制');";
						sScript += "cell.SetCellString(11,"+ (rowNumber + 2) + ",0,'学历');";
						sScript += "cell.SetCellFontStyle(11,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(11,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(11,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(11,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(11, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(11, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(13,"+ (rowNumber + 1) + ",0,'在职');";
						sScript += "cell.SetCellString(13,"+ (rowNumber + 2) + ",0,'学历');";
						sScript += "cell.SetCellFontStyle(13,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(13,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(13,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(13,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(13, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(13, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(15,"+ (rowNumber + 1) + ",0,'入党');";
						sScript += "cell.SetCellString(15,"+ (rowNumber + 2) + ",0,'时间');";
						sScript += "cell.SetCellFontStyle(15,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(15,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(15,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(15,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(15, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(15, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(17,"+ (rowNumber + 1) + ",0,'参加工');";
						sScript += "cell.SetCellString(17,"+ (rowNumber + 2) + ",0,'作时间');";
						sScript += "cell.SetCellFontStyle(17,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(17,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(17,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(17,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(17, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(17, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(19,"+ (rowNumber + 1) + ",0,'任职');";
						sScript += "cell.SetCellString(19,"+ (rowNumber + 2) + ",0,'时间');";
						sScript += "cell.SetCellFontStyle(19,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontStyle(19,"+ (rowNumber + 2) + ",0,2);";
						sScript += "cell.SetCellFontSize(19,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellFontSize(19,"+ (rowNumber + 2) + ",0,12);";
						sScript += "cell.SetCellAlign(19, "+ (rowNumber + 1) + ", 0, 64 + 16);";
						sScript += "cell.SetCellAlign(19, "+ (rowNumber + 2) + ", 0, 64 + 8);";
						
						sScript += "cell.SetCellString(21,"+ (rowNumber + 1) + ",0,'备注');";
						sScript += "cell.SetCellFontStyle(21,"+ (rowNumber + 1) + ",0,2);";
						sScript += "cell.SetCellFontSize(21,"+ (rowNumber + 1) + ",0,12);";
						sScript += "cell.SetCellAlign(21, "+ (rowNumber + 1) + ", 0, 64 + 32);";
						sScript += "cell.MergeCells(21, "+ (rowNumber + 1) + ", 21, "+ (rowNumber + 2) + ");";
						sScript += "cell.DrawGridLine(1,"+ (rowNumber + 2) + ",21,"+ (rowNumber + 2) + ",5,4,9);";
							geshu++;
							map1 = special(map1); //特殊项处理
							List<String> e = new ArrayList<String>();
							e.add(map1.get("a0201a"));
							e.add(map1.get("a0216a"));
							e.add(map1.get("a0101"));
							e.add(map1.get("a0111a"));
							e.add(map1.get("a0107"));
							e.add(map1.get("qrzxl"));
							e.add(map1.get("zzxl"));
							e.add(map1.get("a0144"));
							e.add(map1.get("a0134"));
							e.add(map1.get("a0243"));
							e.add(map1.get("a0192"));
							for (int k = 0; k < e.size(); k++) {
								String mesaa = "";
								try {
									e.get(k).length();
									mesaa = e.get(k);
								} catch (Exception e1) {
									mesaa = "";
								}
								sScript += "cell.SetCellString("+ (count-1)+ ","+ (rowNumber + geshu+3)+ ",0,'" + mesaa + "');";
								sScript += "cell.SetCellFontSize("+ (count-1) + "," + (rowNumber + 3 + geshu)+ ",0,12);";
								sScript += "cell.SetCellAlign("+ (count-1) + "," + (rowNumber + 3 + geshu)+",0, 64 + 8);";
								sScript += "cell.SetCellHideZero ("+ (count-1) + "," + (rowNumber + 3 + geshu)+ ",0, 0);";
								sScript += "cell.SetRowHeight(1, 63, "+ (rowNumber + 3 + geshu)+", 0);";
								sScript += "cell.SetCellTextStyle("+ (count-1) + "," + (rowNumber + 3 + geshu)+ ",0,2);";// 设置自动换行
								countRow = (rowNumber + geshu+3);
								count+=2;
							}
							count = 2;
						}else{
//							falsecount++;//如果不等于则+1
						}
					}
					//==================
				}
					falsecount++;//如果不等于则+1s
					//==================
					if(falsecount == personlist.size()){
						rowNumber = rowNumber + geshu;
					}else{
						rowNumber = rowNumber + geshu + 4;
					}
				}
				falsecount = 0;
			}			
			sScript += "cell.DeleteRow("+ (countRow+1 ) + "," +  (65050 - (countRow+1 )) + ",0);";//删除多余的行	
			sScript += "}\n";
			sScript += "</script>";
			return sScript;
		}
		
		
		
		//------------------------------------标准名册自定义展示
		
		//1.不分组无机构名列
		if("11".equals(num)){
			String tpidw = (String) request.getSession().getAttribute("tpid");//模板类型
			String sScript="";
			int pnum = 0 ;//行
			String ZBRow = "0";
			String ZBLine = "0";
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		    sScript += "var cell = document.getElementById('DCellWeb1');";
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
		    sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    int m = 0;
		    //1，获取表册中的信息项
		    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
		    //假设行的最大值
		    int max = maxZbrow(listtj);
		    //将行数等于最大值的查询出来
			List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow='"+max+"'").list();
			for(int i=0;i<listtj1.size();i++){
				Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
				int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
				String messageE = obj[1].toString();
				String endtime = "";
				if(obj[5]==null||"".equals((String)obj[5])){
					endtime = DateUtil.getcurdate();
				}else{
					endtime = obj[5].toString();
				}
				ZBRow = obj[2].toString();
				ZBLine = obj[3].toString();
				//================================================
				//行的差值
				sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
				sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";

			}
		    for(m = 0; m< id1.length;m++){//id1选中的人员id
		    	//2，遍历表册中的每一个信息项与特殊项进行匹配
			    for(int i=0;i<listtj1.size();i++){
					Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
					int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
					String messageE = obj[1].toString();
					String endtime = "";
					if(obj[5]==null||"".equals((String)obj[5])){
						endtime = DateUtil.getcurdate();
					}else{
						endtime = obj[5].toString();
					}
					ZBRow = obj[2].toString();
					ZBLine = obj[3].toString();
					//================================================
					//列的差值
					sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
					//行的差值
					sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
					//结束行值
					sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
					sScript += "if(endline == 0){endline = "+ZBRow+"};";
					//合并单元格
					sScript += "cell.MergeCells("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+ZBLine+"+mathline,"+ZBRow+"+(mathrow1+1)*"+m+"+mathrow);";
					//================================================
					String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime);
					if(mes !=null && !mes.equals("null") && !mes.equals("")){
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",0,document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",0,'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";
						}

					}else{
						mes = "";
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",0,document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+",0,'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

						}
					}
			    }
//			    pnum++;
		    }
	    	sScript += "cell.DeleteRow("+Integer.parseInt(ZBRow)+1+"+((mathrow1)*"+id1.length+"),(65050 - ("+Integer.parseInt(ZBRow)+1+"+((mathrow1)*"+id1.length+"))),0);";//删除多余的行	
//		    sScript+="cell.PrintSetMargin('150','100','200','100');";//设置页边距
			sScript+="}";
			sScript+="</script>";
			return sScript;	
			}
		
		//2.不分组机有构名列
		if("12".equals(num)){
			String tpidw = (String) request.getSession().getAttribute("tpid");//模板类型
			String sScript="";
			String ZBRow = "0";
			String ZBLine = "0";
			int pnum = 0 ;
			int rownumber  = 0 ;
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		  //1，获取表册中的信息项
		    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
		    int max = maxZbrow(listtj);
		    List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow='"+max+"'").list();
		    for(int i=0;i<listtj1.size();i++){
				Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
				int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
				String messageE = obj[1].toString();
				String endtime = "";
				if(obj[5]==null||"".equals((String)obj[5])){
					endtime = DateUtil.getcurdate();
				}else{
					endtime = obj[5].toString();
				}
				ZBRow = obj[2].toString();
				ZBLine = obj[3].toString();
				//================================================
				//行的差值
				sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
				sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";

			}
		    for(int m = 0; m< id1.length;m++){
		    	//2，遍历表册中的每一个信息项与特殊项进行匹配
			    for(int i=0;i<listtj1.size();i++){
			    	Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
			    	
					int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
					String messageE = obj[1].toString();
					String endtime = "";
					if(obj[5]==null||"".equals((String)obj[5])){
						endtime = DateUtil.getcurdate();
					}else{
						endtime = obj[5].toString();
					}
					ZBRow = obj[2].toString();
					ZBLine = obj[3].toString();
					//================================================
					//列的差值
					sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
					//行的差值
					sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
					//结束行值
					sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
					sScript += "if(endline == 0){endline = "+ZBRow+"};";
					//合并单元格
					sScript += "cell.MergeCells("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+ZBLine+"+mathline,"+ZBRow+"+(mathrow1+1)*"+m+"+mathrow);";
					//================================================
					List listjg = HBUtil.getHBSession().createSQLQuery("select t.a0201a from (select a02.a0201a from a02 where a0000 =("+id1[m].toString()+")) t").list();
					sScript += "cell.SetCellString(1,"+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'"+listjg.get(0)+"');";
					String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime);
					
					if(mes !=null && !mes.equals("null") && !mes.equals("")){
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

						}

					}else{
						mes = "";
						if(getcharnu1(mes, "@")==2){
							sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
							sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+ZBRow+"+(mathrow1+1)*"+m+","+sheet+",'');";
						}else{
//							sScript += insertmess(mes, obj, pnum, ZBRow,sheet);
							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',"+ZBRow+"+(mathrow1+1)*"+m+",'"+sheet+"','"+mes+"');";

						}
					}
				}
			    pnum++;
		    }
		    sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "," +  (65050 - (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//删除多余的行	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//列宽
//		    sScript+="cell.PrintSetMargin('150','100','200','100');";//设置页边距
		    sScript+="cell.SetColUnhidden('1','1');";//取消列的隐藏
			sScript+="}";
			sScript+="</script>";
			return sScript;	
			}
		

		//3.按机构分组
		if ("13".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//模板类型
			String sScript="";
			int pnum = 0 ;
			int add = 0;
			int pnum2 = 0 ;
			String ZBRow = "0";
			String ZBLine = "0";
			String id3 = request.getSession().getAttribute("checkList").toString();
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";

		    //1，获取表册中的信息项
			//根据人员id获取去重后的机构种类（按顺序来排序）
			String sql = "select t.a0201a from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+id_3+") order by length(a0201b),a0201b asc) t";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sql).list();
			//根据人员id来获取对应的机构与姓名
			int rownumber = 0;
			int pcount = 0;
			int a = 0;
			for(;a<disA0201a.size();a++){
				for(int m = 0; m< id1.length;m++){
				//根据人员id查询出对应的机构与姓名
				String sql1 = "select a02.a0201a ,a01.a0101 from A01 a01,A02 a02 where a01.a0000 = a02.a0000 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000,a02.a0201,a01.a0101,a02.a0201a";
				List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
				//按顺序与机构一一对比
					for(int w = 0;w <list.size();w++){
						if(list.get(w)[0].toString().equals(disA0201a.get(a).toString())){
							   List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
							   int max = maxZbrow(listtj);
							   List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
							   
							   for(int i=0;i<listtj1.size();i++){
									Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
									int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
									String messageE = obj[1].toString();
									String endtime = "";
									if(obj[5]==null||"".equals((String)obj[5])){
										endtime = DateUtil.getcurdate();
									}else{
										endtime = obj[5].toString();
									}
									ZBRow = obj[2].toString();
									ZBLine = obj[3].toString();
									//================================================
									//行的差值
									sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
									sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
								}
							   
							   //2，遍历表册中的每一个信息项与特殊项进行匹配
							    for(int i=0;i<listtj1.size();i++){
							    	Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
									int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
									String messageE = obj[1].toString();
									String endtime = "";
									if(obj[5]==null||"".equals((String)obj[5])){
										endtime = DateUtil.getcurdate();
									}else{
										endtime = obj[5].toString();
									}
									ZBRow = obj[2].toString();
									ZBLine = obj[3].toString();
									
									//================================================
									//列的差值
									sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
									//行的差值
									sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
									//结束行值
									sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
									sScript += "if(endline == 0){endline = "+ZBRow+"};";
									//合并单元格
									sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
									//================================================
									
									
									String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime);
									if(mes !=null && !mes.equals("null") && !mes.equals("")){
										if(getcharnu1(mes, "@")==2){
											sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
											sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

										}else{
											sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
										}

									}else{
										mes = "";
										if(getcharnu1(mes, "@")==2){
											sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
											sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

										}else{
											sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
										}
									}
							    }
							    pnum++;
							    pnum2++;
						    }
					}
				 
			}
//				sScript += "cell.SetCellString(1,"+(pnum+(Integer.parseInt(ZBRow))+rownumber-pcount-1)+",0,'"+disA0201a.get(a).toString()+"');";
//				sScript += "cell.SetCellString(1,"+ Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+ (pnum) +"+"+ (rownumber +"-(mathrow1+1)*"+ pnum +"-"+1)+",0,'"+disA0201a.get(a).toString()+"');";
				if(pcount==0){
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +",0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}else{
					add++;
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}
				rownumber = a+1;
				pcount = 1;
			
			}
			sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//删除多余的行	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//列宽
//		    sScript+="cell.PrintSetMargin('150','100','200','100');";//设置页边距
		    sScript+="cell.SetColUnhidden('1','1');";//取消隐藏列
		    sScript+="cell.SetRowUnhidden('1','1');";//取消隐藏行
			sScript+="}";
			sScript+="</script>";
			return sScript;	
		}
		
		//4.按职务层次分组
		if ("14".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//模板类型
			String sScript="";
			int pnum = 0 ;
			int pnum2 = 0;//统计每个层次下有几个人
			int add = 0 ;//控制层次名称的位置
			String ZBRow = "0";
			String ZBLine = "0";
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    //1，获取表册中的信息项
			//根据人员id获取去重后的机构种类（按顺序来排序）
			String sql = "select  cv.code_name from code_value cv,(select a01.a0148 from a01 a01 where a01.a0000 in("+id_3+") group by a01.a0148 order by a01.a0148) a where cv.code_type='ZB09' and cv.code_value=a.a0148";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sql).list();
			disA0201a.removeAll(Collections.singleton(null));
			//根据人员id来获取对应的机构与姓名
			int rownumber = 0;
			int pcount = 0;
			for(int a=0;a<disA0201a.size();a++){
				for(int m = 0; m< id1.length;m++){
					//根据人员id查询出对应的机构与姓名
					String sql1 = "select cv.code_name ,a01.a0101 from code_value cv,A01 a01 where cv.code_type='ZB09' and cv.code_value=a01.a0148 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000 ,cv.code_name,a01.a0101";
					List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
					//按顺序与机构一一对比
					if(list.get(0)[0]!=null&&!"".equals(list.get(0)[0])){
						 if(list.get(0)[0].toString().equals(disA0201a.get(a).toString())){
						   List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
						   int max = maxZbrow(listtj);
						   List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
						   
						   for(int i=0;i<listtj1.size();i++){
								Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
								int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//行的差值
								sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
							}
						   
						   //2，遍历表册中的每一个信息项与特殊项进行匹配
						    for(int i=0;i<listtj1.size();i++){
						    	Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
								int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
								String messageE = obj[1].toString();
								String endtime = "";
								if(obj[5]==null||"".equals((String)obj[5])){
									endtime = DateUtil.getcurdate();
								}else{
									endtime = obj[5].toString();
								}
								ZBRow = obj[2].toString();
								ZBLine = obj[3].toString();
								//================================================
								//列的差值
								sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
								//行的差值
								sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
								//结束行值
								sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
								sScript += "if(endline == 0){endline = "+ZBRow+"};";
								//合并单元格
								sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
								//================================================
								String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime);
								if(mes !=null && !mes.equals("null") && !mes.equals("")){
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

									}else{
										sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
									}

								}else{
									mes = "";
									if(getcharnu1(mes, "@")==2){
										sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
										sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

									}else{
										sScript+="cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";
									}
								}
						    }
						    pnum++;
						    pnum2++;
					    }
					
					}
			
				
				}
				if(pcount==0){//插入机构名称，是用上一个人的个数乘以差值
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +",0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}else{
					add++;
					sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,0,'"+disA0201a.get(a).toString()+"');";
					pnum2 = 0;
				}
				rownumber = a+1;
				pcount = 1;
			
			}
			sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//删除多余的行	
			sScript += "cell.SetColWidth (1, 200,1 , 0);";//列宽
//		    sScript+="cell.PrintSetMargin('150','100','200','100');";//设置页边距
		    sScript+="cell.SetColUnhidden('1','1');";//取消隐藏列
		    sScript+="cell.SetRowUnhidden('1','1');";//取消隐藏行
			sScript+="}";
			sScript+="</script>";
			return sScript;	
		}
		
		//5.按职务层次分组，按机构排序
		if ("15".equals(num)) {
			String tpidw = (String) request.getSession().getAttribute("tpid");//模板类型
			String sScript="";
			int pnum = 0 ;
			int pnum2 = 0 ;
			int add = 0;
			String ZBRow = "0";
			String ZBLine = "0";
			String id3 = request.getSession().getAttribute("checkList").toString();
			String id_3 = id.replace("|", "'").replace("@", ",");
			sScript+="<script type='text/javascript'>";
		    sScript+="function a(){";
		    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpidw +"'+'.cll','' );\n";
		    sScript += "var cell = document.getElementById('DCellWeb1');";
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		    sScript +="cell.PrintSetAlign(1,1);";
			sScript +="cell.PrintSetOrient(1);";
			sScript +="var endline=0;";
		    sScript +="var mathrow1 = 0;";
		    //1，获取表册中的信息项
			//根据人员id获取去重后的职务种类（按顺序来排序）
			String sql = "select  cv.code_name from code_value cv,(select a01.a0148 from a01 a01 where a01.a0000 in("+id_3+") group by a01.a0148 order by a01.a0148) a where cv.code_type='ZB09' and cv.code_value=a.a0148";
			List disA0149 = HBUtil.getHBSession().createSQLQuery(sql).list();
			disA0149.removeAll(Collections.singleton(null));
			//根据人员id获取去重后的机构种类（按顺序来排序）
			String sqljg = "select t.a0201a from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+id_3+") order by length(a0201b),a0201b asc) t";
			List disA0201a = HBUtil.getHBSession().createSQLQuery(sqljg).list();
			disA0201a.removeAll(Collections.singleton(null));
					int rownumber = 0;
					int rownumberw = 0; //控制机构的位置
					int pcount = 0;
						for(int a=0;a<disA0149.size();a++){
							for(int b=0;b<disA0201a.size();b++){
							for(int m = 0; m< id1.length;m++){
									String sql1 = "select cv.code_name,a01.a0101,a02.a0201a from code_value cv,A01 a01,A02 a02 where cv.code_type='ZB09' and cv.code_value=a01.a0148 and a01.a0000=a02.a0000 and a01.a0000 ="+id1[m].toString()+" GROUP BY a01.a0000,cv.code_name ,a01.a0101,a02.a0201a";
									List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql1).list();
									//按顺序与职务一一对比
								if(list.get(0)[0]!=null&&!"".equals(list.get(0)[0])){	
									if(list.get(0)[0].toString().equals(disA0149.get(a).toString())){
										//按顺序与机构一一对比
										if(list.get(0)[2].toString().equals(disA0201a.get(b).toString())){
											List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu from listoutput where TPID = ('"+tpid+"')").list();
											int max = maxZbrow(listtj);
											List<Object[]> listtj1 = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tpid+"') and ZBRow = '"+max+"'").list();
											for(int i=0;i<listtj1.size();i++){
												Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
												int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
												String messageE = obj[1].toString();
												String endtime = "";
												if(obj[5]==null||"".equals((String)obj[5])){
													endtime = DateUtil.getcurdate();
												}else{
													endtime = obj[5].toString();
												}
												ZBRow = obj[2].toString();
												ZBLine = obj[3].toString();
												//================================================
												//行的差值
												sScript += "var mathrowx = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
												sScript += "if(mathrowx >= mathrow1){mathrow1 = mathrowx;}";
											}
											//2，遍历表册中的每一个信息项与特殊项进行匹配
											for(int i=0;i<listtj1.size();i++){
												Object[] obj = listtj1.get(i);//获取每个数组里面包含要的数据
												int sheet = Integer.parseInt((String)listtj1.get(i)[4])-1;
												String messageE = obj[1].toString();
												String endtime = "";
												if(obj[5]==null||"".equals((String)obj[5])){
													endtime = DateUtil.getcurdate();
												}else{
													endtime = obj[5].toString();
												}
												ZBRow = obj[2].toString();
												ZBLine = obj[3].toString();
												//================================================
												//列的差值
												sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
												//行的差值
												sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
												//结束行值
												sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
												sScript += "if(endline == 0){endline = "+ZBRow+"};";
												//合并单元格
												sScript += "cell.MergeCells("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+ZBLine+"+mathline,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+"+mathrow);";
												//================================================
//												sScript += "cell.SetCellString(1,"+(pnum+rownumberw+max)+","+sheet+",'"+list.get(0)[2].toString()+"');";
												sScript += "cell.SetCellString(1,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+list.get(0)[2].toString()+"');";
												sScript+="document.getElementById('DCellWeb1').SetCellAlign(1,"+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'36');";//对其方式
												String mes = returnMes(request,messageE, id1[m].toString().replace("'", "").replace("'", ""), endtime);
												if(mes !=null && !mes.equals("null") && !mes.equals("")){
													if(getcharnu1(mes, "@")==2){
														sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
														sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

													}else{
														sScript += "cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";

													}

												}else{
													mes = "";
													if(getcharnu1(mes, "@")==2){
														sScript += "document.getElementById('DCellWeb1').SetCellImage("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",document.getElementById('DCellWeb1').AddImage('"+mes.substring(1,mes.length()-1)+"'),1,1,1);";
														sScript += "document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'');";

													}else{
														sScript += "cell.SetCellString("+ZBLine+","+Integer.parseInt(ZBRow)+"+(mathrow1+1)*"+pnum+"+"+rownumber+","+sheet+",'"+mes+"');";

													}
												}
											}
											pnum++;
										    pnum2++;
										}
									}
								}
							}
						}
							if(pcount==0){//插入机构名称，是用上一个人的个数乘以差值
								sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +",0,'"+disA0149.get(a).toString()+"');";
								pnum2 = 0;
							}else{
								add++;
								sScript += "cell.SetCellString(1,"+ (Integer.parseInt(ZBRow)-1) +"+(mathrow1+1)*"+ (pnum - pnum2 )+"+"+add+" ,0,'"+disA0149.get(a).toString()+"');";
								pnum2 = 0;
							}
							rownumber = a+1;
							pcount = 1;
							rownumberw++;
							
//							sScript += "document.getElementById('DCellWeb1').SetCellString(1,"+(pnum+(Integer.parseInt(ZBRow))+rownumber-pcount-1)+",0,'"+disA0149.get(a).toString()+"');";
//							rownumber++;	
//							pcount = 0;	
						}
						sScript+="cell.DeleteRow("+ (Integer.parseInt(ZBRow)) +"+(mathrow1+1)*"+ pnum + "+"+rownumber+"," +  (65050 - rownumber- (Integer.parseInt(ZBRow)) +"-(mathrow1+1)*"+ pnum )+ ",0);";//删除多余的行	
						sScript += "cell.SetColWidth (1, 200,1 , 0);";//列宽
//						sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
						sScript+="cell.SetColUnhidden('1','1');";//取消隐藏列
						sScript+="cell.SetRowUnhidden('1','1');";//取消隐藏行
						sScript+="}";
						sScript+="</script>";
						return sScript;	
			}
			}
		
	
			return null;

		}
		


	private int maxZbrow(List<Object[]> listtj) {
		int max = Integer.parseInt(listtj.get(0)[2].toString());
		for(Object[] o:listtj){
			int zbrow = Integer.parseInt(o[2].toString());
			int c = zbrow - max;
			if(c>0){
				max=zbrow;
			}
		}
		return max;
	}

	private String yangshi2(String sScript, int rowNumber) {
		sScript += "cell.MergeCells(1, "+ rowNumber + ", 20, "+ rowNumber + ");";// 合并单元格
		sScript += "cell.SetCellFontSize(1,"+ rowNumber + ",0,17);";// 设置字体大小
		sScript += "cell.SetCellFontStyle(1,"+ rowNumber + ",0,2);";// 设置字体样式
		sScript += "cell.SetCellAlign(1, "+ rowNumber  + ", 0, 32 + 4);";// 设置单元格对齐方式
		sScript += "cell.SetRowHeight(1, 41, "+ rowNumber  + ", 0);";// 设置单元格行高
		
		
		sScript += "cell.SetCellString(2,"+ (rowNumber + 1) + ",0,'职务');";//2
		sScript += "cell.SetCellFontStyle(2,"+ (rowNumber + 1) + ",0,2);";// 设置字体样式
		sScript += "cell.SetCellFontSize(2,"+ (rowNumber + 1) + ",0,12);";// 设置字体大小
		sScript += "cell.SetCellAlign(2, "+ (rowNumber + 1) + ", 0, 64 + 32);";// 设置单元格对齐方式
		sScript += "cell.SetRowHeight(1, 18, "+ (rowNumber + 1) + ", 0);";// 设置单元格行高
		sScript += "cell.SetRowHeight(1, 22, "+ (rowNumber + 2) + ", 0);";
		sScript += "cell.MergeCells(2, "+ (rowNumber + 1) + ", 2, "+ (rowNumber + 2) + ");";
		
		
		sScript += "cell.SetCellString(4,"+ (rowNumber + 1) + ",0,'姓名');";
		sScript += "cell.SetCellFontStyle(4,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(4,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(4, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(4, "+ (rowNumber + 1) + ", 4, "+ (rowNumber + 2) + ");";
		
		sScript += "cell.SetCellString(6,"+ (rowNumber + 1) + ",0,'籍贯');";
		sScript += "cell.SetCellFontStyle(6,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(6,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(6, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(6, "+ (rowNumber + 1) + ", 6, "+ (rowNumber + 2) + ");";
		
		sScript += "cell.SetCellString(8,"+ (rowNumber + 1) + ",0,'出生');";
		sScript += "cell.SetCellString(8,"+ (rowNumber + 2) + ",0,'年月');";
		sScript += "cell.SetCellFontStyle(8,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(8,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(8,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(8,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(8, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(8, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		
		sScript += "cell.SetCellString(10,"+ (rowNumber + 1) + ",0,'全日制');";
		sScript += "cell.SetCellString(10,"+ (rowNumber + 2) + ",0,'学历');";
		sScript += "cell.SetCellFontStyle(10,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(10,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(10,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(10,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(10, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(10, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(12,"+ (rowNumber + 1) + ",0,'在职');";
		sScript += "cell.SetCellString(12,"+ (rowNumber + 2) + ",0,'学历');";
		sScript += "cell.SetCellFontStyle(12,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(12,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(12,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(12,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(12, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(12, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(14,"+ (rowNumber + 1) + ",0,'入党');";
		sScript += "cell.SetCellString(14,"+ (rowNumber + 2) + ",0,'时间');";
		sScript += "cell.SetCellFontStyle(14,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(14,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(14,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(14,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(14, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(14, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(16,"+ (rowNumber + 1) + ",0,'参加工');";
		sScript += "cell.SetCellString(16,"+ (rowNumber + 2) + ",0,'作时间');";
		sScript += "cell.SetCellFontStyle(16,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(16,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(16,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(16,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(16, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(16, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(18,"+ (rowNumber + 1) + ",0,'任职');";
		sScript += "cell.SetCellString(18,"+ (rowNumber + 2) + ",0,'时间');";
		sScript += "cell.SetCellFontStyle(18,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontStyle(18,"+ (rowNumber + 2) + ",0,2);";
		sScript += "cell.SetCellFontSize(18,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellFontSize(18,"+ (rowNumber + 2) + ",0,12);";
		sScript += "cell.SetCellAlign(18, "+ (rowNumber + 1) + ", 0, 64 + 16);";
		sScript += "cell.SetCellAlign(18, "+ (rowNumber + 2) + ", 0, 64 + 8);";
		
		sScript += "cell.SetCellString(20,"+ (rowNumber + 1) + ",0,'备注');";
		sScript += "cell.SetCellFontStyle(20,"+ (rowNumber + 1) + ",0,2);";
		sScript += "cell.SetCellFontSize(20,"+ (rowNumber + 1) + ",0,12);";
		sScript += "cell.SetCellAlign(20, "+ (rowNumber + 1) + ", 0, 64 + 32);";
		sScript += "cell.MergeCells(20, "+ (rowNumber + 1) + ", 20, "+ (rowNumber + 2) + ");";
		sScript += "cell.DrawGridLine(1,"+ (rowNumber + 2) + ",20,"+ (rowNumber + 2) + ",5,4,9);";
		sScript += "cell.SetRowHeight (1, 9,"+ (rowNumber + 3) + " , 0);";
		return sScript;
	}

	private String yangshi1(String sScript) {
		sScript += "cell.SetColWidth (1, 11,1 , 0);";
		sScript += "cell.SetColWidth (1, 80,2 , 0);";
		sScript += "cell.SetColWidth (1, 16,3 , 0);";
		sScript += "cell.SetColWidth (1, 82,4 , 0);";
		sScript += "cell.SetColWidth (1, 16,5 , 0);";
		sScript += "cell.SetColWidth (1, 73,6 , 0);";
		sScript += "cell.SetColWidth (1, 16,7 , 0);";
		sScript += "cell.SetColWidth (1, 64,8 , 0);";
		sScript += "cell.SetColWidth (1, 16,9 , 0);";
		sScript += "cell.SetColWidth (1, 70,10 , 0);";
		sScript += "cell.SetColWidth (1, 16,11 , 0);";
		sScript  += "cell.SetColWidth (1, 70,12 , 0);";
		sScript += "cell.SetColWidth (1, 16,13 , 0);";
		sScript += "cell.SetColWidth (1, 64,14 , 0);";
		sScript += "cell.SetColWidth (1, 16,15 , 0);";
		sScript += "cell.SetColWidth (1, 64,16 , 0);";
		sScript += "cell.SetColWidth (1, 16,17 , 0);";
		sScript += "cell.SetColWidth (1, 64,18 , 0);";
		sScript += "cell.SetColWidth (1, 16,19 , 0);";
		sScript += "cell.SetColWidth (1, 121,20 , 0);";
		return sScript;
	}

	/*private Map<String, String> nsf(List<Map<String, String>> bzmclist, int i) {
		
		Map<String, String> b = bzmclist.get(i);
		String zhiwu = "";
		zhiwu += b.get("a0216a")+"\\r\\n";
		
		String d1 = "";
		String d2 = "";
		if(DBUtil.getDBType() == DBType.MYSQL){ 
			d1 = b.get("code_name");// 性别
			d2 = b.get("a0117a");// 民族
		} else {
			 d1 = b.get("a0104");// 性别
			 d2 = b.get("a0117");// 民族
		}
		
		String d3 = b.get("a0141a");// 第一党派
		//------进行女少非的判断_wx
		if (d1 != null && d1.equals("女") && d2.equals("汉族")) {
			String d11 = b.get("a0101")+"\\r\\n"+"(女)";
			b.put("a0101", d11);
		}
		if (d1 != null && d1.equals("女") && !d2.equals("汉族")) {
			String d12 = b.get("a0101")+"\\r\\n"+"(女," + d2+ ")";
			b.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("女") && !d2.equals("汉族")) {
			String d13 = b.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			b.put("a0101", d13);
		}
		if(!"".equals(d3)&&d3!=null){
			if("中共党员".equals(d3)){
				try {
					String	mes60 = b.get("a0144");//入党时间
					if(mes60 != null ){
					String mes61 = mes60.replace(".", "");
					String mes62 = mes61.substring(0,4)+".";
					String mes63 = mes61.substring(4, 6);
					String	mes6 = mes62 + mes63;
					b.put("a0144", mes6);
					}
				} catch (Exception e1) {
					String	mes60 = b.get("a0144");//入党时间
					b.put("a0144", mes60);
					
				}
			}else{
				b.put("a0144", b.get("a0141a"));
			}
		}else{
			b.put("a0144", "");
		}
		try {
			String	mes40 = b.get("a0107");//生日
			String mes41 = mes40.replace(".", "");
			String mes42 = mes41.substring(0,4)+".";
			String mes43 = mes41.substring(4, 6);
			String	mes4 = mes42 + mes43;
			b.put("a0107", mes4);
			
		} catch (Exception e) {
			// TODO: handle exception
			String	mes40 = b.get("a0107");//生日
			b.put("a0107", mes40);

		}
		
		try {
			String	mes30 = b.get("a0243");//任职时间
			String mes31 = mes30.replace(".", "");
			String mes32 = mes31.substring(0,4)+".";
			String mes33 = mes31.substring(4, 6);
			String	mes3 = mes32 + mes33;
			b.put("a0243", mes3);
		} catch (Exception e) {
			String	mes30 = b.get("a0243");//任职时间
			b.put("a0243", mes30);
		}
		
		try {
			String	mes60 = b.get("a0144");//入党时间
			
			String mes61 = mes60.replace(".", "");
			String mes62 = mes61.substring(0,4)+".";
			String mes63 = mes61.substring(4, 6);
			String	mes6 = mes62 + mes63;
			b.put("a0144", mes6);
			
		} catch (Exception e) {
			// TODO: handle exception
			String	mes60 = b.get("a0144");//入党时间
			b.put("a0144", mes60);

		}

		
		String	mes7 = "";
		String	mes78 ="";
		try {
			String	mes70 = b.get("a0134");//参加工作时间
			String mes71 = mes70.replace(".", "");
			String mes72 = mes71.substring(0,4)+".";
			String mes73 = mes71.substring(4, 6);
			mes7 += mes72 + mes73+"\\r\\n";
			b.put("a0134", mes7);
		} catch (Exception e) {
			// TODO: handle exception
			mes78 += b.get("a0134")+"\\r\\n";//参加工作时间
			b.put("a0134", mes78);
		}
		
		return b;
	}*/
		
	/*private Map<String, String> nsf1(List<Map<String, String>> bzmclist, int i) {
		
		Map<String, String> b = bzmclist.get(i);
		
		
		String d1 = "";
		String d2 = "";
		if(DBUtil.getDBType() == DBType.MYSQL){ 
			d1 = b.get("code_name");// 性别
			d2 = b.get("a0117a");// 民族
		} else {
			d1 = b.get("a0104");// 性别
			d2 = b.get("a0117");// 民族
		}

		String d3 = b.get("a0141a");// 第一党派
		//------进行女少非的判断_wx
		if (d1 != null && d1.equals("女") && d2.equals("汉族")) {
			String d11 = b.get("a0101")+"\\r\\n"+"(女)";
			b.put("a0101", d11);
	
		}
		if (d1 != null && d1.equals("女") && !d2.equals("汉族")) {
			String d12 = b.get("a0101")+"\\r\\n"+"(女," + d2+ ")";
			b.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("女") && !d2.equals("汉族")) {
			String d13 = b.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			b.put("a0101", d13);
		}
		if(!"".equals(d3)&&d3!=null){
			if("中共党员".equals(d3)){
				try {
					String	mes60 = b.get("a0144");//入党时间
					if(mes60 != null ){
					String mes61 = mes60.replace(".", "");
					String mes62 = mes61.substring(0,4)+".";
					String mes63 = mes61.substring(4, 6);
					String	mes6 = mes62 + mes63;
					b.put("a0144", mes6);
					}
				} catch (Exception e1) {
					String	mes60 = b.get("a0144");//入党时间
					b.put("a0144", mes60);
					
				}
			}else{
				b.put("a0144", b.get("a0141a"));
			}
		}else{
			b.put("a0144", "");
		}
		String	mes30 = b.get("a0243");//任职时间
		if(mes30.length() == 8 && mes30!=null){
			String mes31 = mes30.replace(".", "");
			String mes32 = mes31.substring(0,4)+".";
			String mes33 = mes31.substring(4, 6);
			String	mes3 = mes32 + mes33;
			b.put("a0243", mes3);
		}
		try {
			String	mes40 = b.get("a0107");//生日
			String mes41 = mes40.replace(".", "");
			String mes42 = mes41.substring(0,4)+".";
			String mes43 = mes41.substring(4, 6);
			String	mes4 = mes42 + mes43;
			b.put("a0107", mes4);
		} catch (Exception e) {
			String	mes40 = b.get("a0107");//生日
			
			b.put("a0107", mes40);
		}
		try {
			String	mes60 = b.get("a0144");//入党时间
			String mes61 = mes60.replace(".", "");
			String mes62 = mes61.substring(0,4)+".";
			String mes63 = mes61.substring(4, 6);
			String	mes6 = mes62 + mes63;
			b.put("a0144", mes6);
		} catch (Exception e) {
			String	mes60 = b.get("a0144");//入党时间
			b.put("a0144", mes60);
		}
		try {
			String	mes70 = b.get("a0134");//参加工作时间
			String mes71 = mes70.replace(".", "");
			String mes72 = mes71.substring(0,4)+".";
			String mes73 = mes71.substring(4, 6);
			String	mes7 = mes72 + mes73;
			b.put("a0134", mes7);
		} catch (Exception e) {
			String	mes70 = b.get("a0134");//参加工作时间
			b.put("a0134", mes70);
		}
		return b;
	}*/
	
	//y自定义表册插入数据公用的方法
	public  String  insertmess(String mes,Object[] obj,int pnum, String ZBRow ,int sheet){
		String sScript = "";
		 //4, 获取该项的行列信息值进行插入
		String ZBLine = obj[3].toString();
		ZBRow = obj[2].toString();
		//插入数据
		sScript+="document.getElementById('DCellWeb1').SetCellString("+ZBLine+","+(pnum+(Integer.parseInt(ZBRow)))+","+sheet+",'"+mes+"');";
		
		return sScript;	
	}
	

	
	
	//自定义表册插入数据公用的方法
	public  String  insertmess1(List listj,Object[] obj,int row, String ZBRow,int rownumber,int count){
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "无数据";
		}else{
			mes = (String) listj.get(0);//查询数据库后的信息
		}
		System.out.println(mes);
		 //4, 获取该项的行列信息值进行插入
//		String ZBRow = obj[2].toString();
//		System.out.println(ZBRow);
		String ZBLine = obj[3].toString();
//		System.out.println(ZBLine);
		//插入数据
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow))+rownumber)+"','0','"+mes+"');";
		return sScript;	
	}
	
	
	
	//筛选方法
	public String  functyppe(HttpServletRequest request) throws RadowException{
		String viewType = (String) request.getSession().getAttribute("yviewType");
		String tempType = (String) request.getSession().getAttribute("ytempType");
		String sScript = "";
		if("6".equals(tempType)){
			if("1".equals(viewType)){
				sScript = sibufz(request);
			}else if("3".equals(viewType)){
				sScript = sianjifz(request);
			}else{
				sScript = sianchengc(request);
			}
			
		}else if("1".equals(viewType)){
			sScript = yihangbufz(request);
		}else if("3".equals(viewType)){
			sScript = yihangjgfz(request);
		}else if("4".equals(viewType)){
			sScript = yihangchengc(request);
		}else if(viewType.equals("21")){
			sScript = ybufenzi(request);
		}else if(viewType.equals("22")){
			sScript = yanjigou(request);
		}else{
			sScript = yanceci(request);
		}
		request.getSession().setAttribute("yviewType", "");
		request.getSession().setAttribute("ytempType", "");
		return sScript;
	}
	private String yanceci(HttpServletRequest request) {
		String tpid = (String) request.getSession().getAttribute("tpid");//模板类型
		String tempType = (String) request.getSession().getAttribute("ytempType");//模板id
		String mani = "";
		String manio = "";
		String manioo = "";
		if((String) request.getSession().getAttribute("yList") != null && !"".equals((String) request.getSession().getAttribute("yList"))){
			manio = (String) request.getSession().getAttribute("yList");
		}
		if((String) request.getSession().getAttribute("yListall") != null && !"".equals((String) request.getSession().getAttribute("yListall"))){
			manioo = (String) request.getSession().getAttribute("yListall");
		}
		if(manio != null && !"".equals(manio) ){
			mani = manio;
		}else{
			mani = manioo;
		}
		
		String manid = mani.replace("|", "'").replace("@", ",");//人员id
		String[] ryid = mani.replace("|", "'").split("@");//人员id
		int ZBLine2 = 0;
		int bian = 0;
		int minline = 0;
		int maxline = 0;
		String sScript="";
		int row = 0 ;//行
		int ZBRow = 0;
		String ZBLine = "";
		int j = 0;//用来判断时候是第一个机构的第一个人插入信息 1表示第一个人开始插入
		int u = 0;
		sScript+="<script type='text/javascript'>";
	    sScript+="function a(){";
	    sScript+="var cell = document.getElementById('DCellWeb1');";
	    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpid +"'+'.cll','' );\n";
	    sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
	    sScript+="var maxrow=0;";
	    sScript+="var end=0;";
	  //选定的人有多少机构，不重复
	    String sql = "select cv.code_name from code_value cv,(select a01.a0148 from a01 a01 group by a01.a0148 order by a01.a0148) a where cv.code_type='ZB09' and cv.code_value=a.a0148"; 
		List jigou = HBUtil.getHBSession().createSQLQuery(sql).list();
	    String yem ="";
	    int m  = 0;
	    for(int x = 0;x<jigou.size();x++){
	    	yem = (String) jigou.get(x);//只查了一个信息项，不用数组；
	    //查询每个人的机构名称，如果匹配成功就查出	 
	    int idry = ryid.length;//选中的人的id个数
	    
	    	 for(m = 0;m<idry;m++){
	    		String sqli = "select cv.code_name from code_value cv,a01 a01 where cv.code_type='ZB09' and cv.code_value=a01.a0148 and a01.a0000 = "+ryid[m]+"";
	    		System.out.println(sqli);
	    		List ryjg = HBUtil.getHBSession().createSQLQuery(sqli).list();//人的机构
	    		if(ryjg.get(0)!=null&&!"".equals(ryjg.get(0))){
	    		String jgm = (String) ryjg.get(0);//获取人的机构
	    		 if(jigou.get(x) != null && jigou.get(x).equals(jgm)){
	    			 //---------------------------------------
	    			 //1，获取表册中的信息项
	    			    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tempType+"')").list();
//	    			    for(int m = 0; m< ryid.length;m++){//遍历每个人的id
	    			    	System.out.println(ryid[m].toString());
	    			    	//2，遍历表册中的每一个信息项与特殊项进行匹配
	    				    int size = listtj.size();
	    					for(int i=0;i<size;i++){
	    						Object[] obj = listtj.get(i);//获取每个数组里面包含要的数据
	    						String messageE = obj[1].toString();
	    						System.out.println(messageE);
	    						int sheet = Integer.parseInt(obj[4].toString())-1;
	    						String endtime = "";
	    						ZBRow = Integer.parseInt(obj[2].toString());
	    						ZBLine = obj[3].toString();
	    						//================================={
	    						if(bian == 0 ){
	    							bian++;
	    							ZBLine2 = Integer.parseInt(ZBLine);
	    							minline = ZBLine2;
	    						}
	    						if(Integer.parseInt(ZBLine) <= minline ){
	    							minline = Integer.parseInt(ZBLine);
	    						}else{
	    							maxline =  Integer.parseInt(ZBLine);
	    						}
	    						//=======================================}
	    						if(obj[5]==null||"".equals((String)obj[5])){
	    							endtime = DateUtil.getcurdate();
	    						}else{
	    							endtime = obj[5].toString();
	    						}
	    						sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
	    						sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
	    						sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
	    						sScript += "if(endline>=end){end = endline;}";
	    						sScript += "if(end == 0){end = "+ZBRow+"};";
	    						sScript += "var rowheight = cell.GetRowHeight(0,"+ZBRow+","+sheet+");";
	    						sScript += "cell.MergeCells("+ZBLine+",end*"+m+"+"+ZBRow+","+ZBLine+"+mathline,end*"+m+"+"+ZBRow+"+mathrow);";
	    						sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+","+sheet+");";
	    						sScript += "for(var i=1;i<=mathrow;i++){\n";
	    						sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+"+i,"+sheet+");\n";
	    						sScript += "}\n";
	    						String mes = returnMes(request,messageE, ryid[m].toString().replace("'", "").replace("'", ""), endtime);
	    						if(j==0){
	    							sScript+="document.getElementById('DCellWeb1').MergeCells('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+(Integer.parseInt(ZBLine)+8)+"',end*"+m+"+2);";//合并机构名的单元格
	    							//名称信息
	    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"','"+yem+"');";
	    							sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',36);";
	    							sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',16);";
	    							sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',2);";
	    						}
	    						if(mes !=null && !mes.equals("null") && !mes.equals("")){
	    							if(mes.contains("@")){
		    							sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
		    						}else{
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
		    						}
		    						j=1;

	    						}else{
	    							mes = "";
	    							if(mes.contains("@")){
		    							sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
		    						}else{
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
		    						}
		    						j=1;
	    						}
	    					}//第二个for循环的
	    					//硬分页
	    					sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
	    					row = (row +ZBRow +1);
	    		 }
	    	 }
	    	 j=0;
	    	 u+=1; 
	    }
	    }
	    if(minline > 1){
	    	sScript += "cell.SetColHidden('1','"+ (minline - 1 )+ "');";	
	    }
	    sScript += "cell.DeleteRow((end*"+m+"+1),(65050 - ((end*"+m+"))),0);";//删除多余的行	
	    sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
//		sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
		sScript+="document.getElementById('DCellWeb1').SetRowUnhidden(1,2);";
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}

	private String yanjigou(HttpServletRequest request) {
		String tpid = (String) request.getSession().getAttribute("tpid");//模板类型
		String tempType = (String) request.getSession().getAttribute("ytempType");//模板id
		String mani = "";
		String manio = "";
		String manioo = "";
		if((String) request.getSession().getAttribute("yList") != null && !"".equals((String) request.getSession().getAttribute("yList"))){
			manio = (String) request.getSession().getAttribute("yList");
		}
		if((String) request.getSession().getAttribute("yListall") != null && !"".equals((String) request.getSession().getAttribute("yListall"))){
			manioo = (String) request.getSession().getAttribute("yListall");
		}
		if(manio != null && !"".equals(manio) ){
			mani = manio;
		}else{
			mani = manioo;
		}
		String manid = mani.replace("|", "'").replace("@", ",");//人员id
		String[] ryid = mani.replace("|", "'").split("@");//人员id
		int ZBLine2 = 0;
		int bian = 0;
		int minline = 0;
		int maxline = 0;
		String sScript="";
		int row = 0 ;//行
		int maxrow = 0;
		int status = 0;
		int ZBRow = 0;
		String ZBLine = "";
		int j = 0;//用来判断时候是第一个机构的第一个人插入信息 1表示第一个人开始插入
		int u = 0;
		sScript+="<script type='text/javascript'>";
	    sScript+="function a(){";
	    sScript+="var cell = document.getElementById('DCellWeb1');";
	    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpid +"'+'.cll','' );\n";
	    sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
	    sScript+="var maxrow=0;";
	    sScript+="var end=0;";
	  //选定的人有多少机构，不重复
		String sql = "select t.a0201a from(select distinct a0201a,a0201b from a02 where a02.a0000 in ("+manid+") order by length(a0201b),a0201b asc) t";
		List jigou = HBUtil.getHBSession().createSQLQuery(sql).list();
	    String yem ="";
	    int m = 0;
	    for(int x = 0;x<jigou.size();x++){
	    	yem = (String) jigou.get(x);//只查了一个信息项，不用数组；
	    //查询每个人的机构名称，如果匹配成功就查出	 
	    int idry = ryid.length;//选中的人的id个数
	    	 for(m = 0;m<idry;m++){
	    		String sqli = "select a02.a0201a from a02 a02 where a02.a0000 = "+ryid[m]+"";
	    		List ryjg = HBUtil.getHBSession().createSQLQuery(sqli).list();//人的机构
	    		String jgm = (String) ryjg.get(0);//获取人的机构
	    		 if(jigou.get(x) != null && jigou.get(x).equals(jgm)){
	    			 //---------------------------------------
	    			 //1，获取表册中的信息项
	    			    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tempType+"')").list();
	    			    	//2，遍历表册中的每一个信息项与特殊项进行匹配
	    				    int size = listtj.size();
	    					for(int i=0;i<size;i++){
	    						Object[] obj = listtj.get(i);//获取每个数组里面包含要的数据
	    						String messageE = obj[1].toString();
	    						int sheet = Integer.parseInt(obj[4].toString())-1;
	    						String endtime = "";
	    						ZBRow = Integer.parseInt(obj[2].toString());
	    						ZBLine = obj[3].toString();
	    						//================================={
	    						if(bian == 0 ){
	    							bian++;
	    							ZBLine2 = Integer.parseInt(ZBLine);
	    							minline = ZBLine2;
	    						}
	    						if(Integer.parseInt(ZBLine) <= minline ){
	    							minline = Integer.parseInt(ZBLine);
	    						}else{
	    							maxline =  Integer.parseInt(ZBLine);
	    						}
	    						//=======================================}
	    						if(obj[5]==null||"".equals((String)obj[5])){
	    							endtime = DateUtil.getcurdate();
	    						}else{
	    							endtime = obj[5].toString();
	    						}
	    						//列的差值
	    						sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
	    						//行的差值
	    						sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
	    						//结束行值
	    						sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
	    						//获取结束行的最大值
	    						sScript += "if(endline > end){end = endline;}";
	    						sScript += "if(end == 0){end = "+ZBRow+"};";
	    						//行差的最大值
	    						sScript += "if(mathrow>maxrow){maxrow=mathrow;}";
	    						//得到指定行的行高
	    						sScript += "var rowheight = cell.GetRowHeight(0,"+ZBRow+","+sheet+");";
	    						//合并单元格
	    						sScript += "cell.MergeCells("+ZBLine+",end*"+m+"+"+ZBRow+","+ZBLine+"+mathline,end*"+m+"+"+ZBRow+"+mathrow);";
	    						//设置行高
	    						sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+","+sheet+");";
	    						//循环 
	    						sScript += "for(var i=1;i<=mathrow;i++){\n";
	    						////设置行高
	    						sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+"+i,"+sheet+");\n";
	    						sScript += "}\n";
	    						String mes = returnMes(request,messageE, ryid[m].toString().replace("'", "").replace("'", ""), endtime);
	    						if(j==0){
	    							sScript+="document.getElementById('DCellWeb1').MergeCells('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+(Integer.parseInt(ZBLine)+8)+"',end*"+m+"+2);";//合并机构名的单元格
	    							//名称信息
	    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"','"+yem+"');";
	    							sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',36);";
	    							sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',16);";
	    							sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(Integer.parseInt(ZBLine)+2)+"',end*"+m+"+1,'"+sheet+"',2);";
	    						}
	    						if(mes !=null && !mes.equals("null") && !mes.equals("")){
	    							if(mes.contains("@")){
		    							sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
		    						}else{
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
		    						}
		    						j=1;

	    						}else{
	    							mes = "";
	    							if(mes.contains("@")){
		    							sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
		    						}else{
		    							sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
		    							
		    						}
		    						j=1;
	    						}
	    					}//第二个for循环的
	    					//硬分页
	    					sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
	    					row = (row +ZBRow +1);
	    					
	    		 }
	    	 }
	    	 j=0;
	    	 u+=1; 
	    }
	    if(maxline == 0 ){
	    	maxline = (minline+2);
	    }
	    if(minline > 1){
	    	sScript += "cell.SetColHidden('1','"+ (minline - 1 )+ "');";	
	    }
	    sScript += "cell.DeleteRow((end*"+m+"+1),(65050 - ((end*"+m+"))),0);";//删除多余的行	
	    sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
//		sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
		sScript+="document.getElementById('DCellWeb1').SetRowUnhidden(1,2);";
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}

	private String ybufenzi(HttpServletRequest request) throws RadowException {
//		String tpid =  this.getPageElement("tpid").getValue();
		String tpid = (String) request.getSession().getAttribute("tpid");//模板类型
		String tempType = (String) request.getSession().getAttribute("ytempType");//模板id
		String mani = "";
		String manio = "";
		String manioo = "";
		if((String) request.getSession().getAttribute("yList") != null && !"".equals((String) request.getSession().getAttribute("yList"))){
			manio = (String) request.getSession().getAttribute("yList");
		}
		if((String) request.getSession().getAttribute("yListall") != null && !"".equals((String) request.getSession().getAttribute("yListall"))){
			manioo = (String) request.getSession().getAttribute("yListall");
		}
		if(manio != null && !"".equals(manio) ){
			mani = manio;
		}else{
			mani = manioo;
		}
		String[] ryid = mani.replace("|", "'").split("@");//人员id
		int ZBLine2 = 0;
		int bian = 0;
		int minline = 0;
		int maxline = 0;
		String sScript="";
		int row = 0 ;//行
		String ZBRow = "0";
		String ZBLine = "0";
		sScript+="<script type='text/javascript'>";
	    sScript+="function a(){";
	    sScript+="var cell = document.getElementById('DCellWeb1');";
	    sScript+="document.getElementById('DCellWeb1').openfile(ctpath+'/template/'+'"+ tpid +"'+'.cll','' );\n";
	    sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
	    sScript+="var maxrow=0;";
	    sScript+="var end=0;";
	    //1，获取表册中的信息项
	    List<Object[]> listtj = HBUtil.getHBSession().createSQLQuery("select TPName,MessageE,ZBRow,ZBLine,PageNu,endtime from listoutput where TPID = ('"+tempType+"')").list();
	    int m = 0 ;
	    for(m = 0; m< ryid.length;m++){//遍历每个人的id
	    	System.out.println(ryid[m].toString());
	    	//2，遍历表册中的每一个信息项与特殊项进行匹配
		    int size = listtj.size();
			for(int i=0;i<size;i++){
				Object[] obj = listtj.get(i);//获取每个数组里面包含要的数据
				String messageE = obj[1].toString();
				int sheet = Integer.parseInt(obj[4].toString())-1;
				String endtime = "";
				ZBRow = obj[2].toString();
				ZBLine = obj[3].toString();
				//================================={
				if(bian == 0 ){
					bian++;
					ZBLine2 = Integer.parseInt(ZBLine);
					minline = ZBLine2;
				}
				if(Integer.parseInt(ZBLine) <= minline ){
					minline = Integer.parseInt(ZBLine);
				}else{
					maxline =  Integer.parseInt(ZBLine);
				}
				//=======================================}
				if(obj[5]==null||"".equals((String)obj[5])){
					endtime = DateUtil.getcurdate();
				}else{
					endtime = obj[5].toString();
				}
				sScript += "var mathline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",2)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",0);";
				sScript += "var mathrow = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3)-cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",1);";
				sScript += "var endline = cell.GetMergeRangeJ("+ZBLine+","+ZBRow+","+sheet+",3);";
				sScript += "if(endline>=end){end = endline;}";
				sScript += "if(end == 0){end = "+ZBRow+"};";
				sScript += "if(mathrow>maxrow){maxrow=mathrow;}";
				sScript += "var rowheight = cell.GetRowHeight(0,"+ZBRow+","+sheet+");";
				sScript += "cell.MergeCells("+ZBLine+",end*"+m+"+"+ZBRow+","+ZBLine+"+mathline,end*"+m+"+"+ZBRow+"+mathrow);";
				sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+","+sheet+");";
				sScript += "for(var i=1;i<=mathrow;i++){\n";
				sScript += "cell.SetRowHeight(0,rowheight,end*"+m+"+"+ZBRow+"+i,"+sheet+");\n";
				sScript += "}\n";
				String mes = returnMes(request,messageE, ryid[m].toString().replace("'", "").replace("'", ""), endtime);
				if(mes !=null && !mes.equals("null") && !mes.equals("")){
					if(mes.contains("@")){
						sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
						sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
					}else{
						sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
					}
				}else{
					mes = "";
					if(mes.contains("@")){
						sScript+="document.getElementById('DCellWeb1').SetCellImage('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"',document.getElementById('DCellWeb1').AddImage('"+mes.substring(1, mes.length()-1)+"'),1,1,1);";
						sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','');";
					}else{
						sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"',end*"+m+"+"+ZBRow+",'"+sheet+"','"+mes+"');";
					}
				}
			}//第二个for循环的
			//硬分页
			sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
			row = (row +(Integer.parseInt(ZBRow) ));
	    }//第一个for循环的
		    if(maxline == 0 ){
		    	maxline = (minline+2);
		    }
		    System.out.println(minline+"++++++++++++++++++++++++++++++++");
		    if(minline > 1){
		    	sScript += "cell.SetColHidden('1','"+ (minline - 1 )+ "');";	
		    }
	    	sScript += "cell.DeleteRow((end*"+m+"+1),(65050 - (end*"+m+")),0);";//删除多余的行	
	    	sScript+="document.getElementById('DCellWeb1').SetRowPageBreak(end*"+m+"+1,'1');";
//	    	sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
	    	sScript+="}";
	    	sScript+="</script>";
	    	return sScript;	
		}
	
	//y自定义表册插入数据公用的方法
	public  String  insertmess1(List listj,Object[] obj,int row, String ZBRow ){
		int j = 0;
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "无数据";
		}else if(!"".equals((String) listj.get(0))&&(String) listj.get(0)!=null){
			mes = (String) listj.get(0);//查询数据库后的信息
			mes = mes.replace("\n", "\\r\\n");
		}
		 //4, 获取该项的行列信息值进行插入
		String ZBLine = obj[3].toString();
		//插入数据
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow)))+"','0','"+mes+"');";
		return sScript;	
	}
	
	//y自定义表册插入数据公用的方法
	//j判断是否第一次插入 u 控制插入的机构行
	
	public  String  insert(List listj,Object[] obj,int row, String ZBRow ){
		String mes = "";
		String sScript = "";
		if(listj.size() ==0){
			mes = "无数据";
		}else{
			for(int a =0;a<listj.size();a++){
				mes += (String) listj.get(a)+"\\r\\n";//查询数据库后的信息
			}
			
		}
		//4, 获取该项的行列信息值进行插入
		String ZBLine = obj[3].toString();
		String sheet = obj[4].toString();
		//插入数据
		sScript+="document.getElementById('DCellWeb1').SetCellString('"+ZBLine+"','"+(row+(Integer.parseInt(ZBRow)))+"','"+sheet+"','"+mes+"');";
		return sScript;	
	}
	//一行不分组
	public String yihangbufz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"0");
		List<Map<String,String>> listy = returnlist(id,"0");
		Map<String, String> map = new HashMap<String, String>();
		String sScript="";
		int size = listy.size();//人数
		int count = size*11; // 需要的行数
		int y =0; //总行数
		y =count;
		sScript+="<script type='text/javascript' >";
	    sScript+="function a(){";
	    sScript+="var cell = document.getElementById('DCellWeb1');";
	    sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
	    int row1 = 1;//列
	    int row2 = 4;//列
	    int row3 = 9;//列
	    int row4 = 3;//列
	    int rowj1 = 1;//列
	    int rowj2 = 4;//列
	    for(int i = 0;i < size;i++){
	    	//合并单元格
		    sScript+="cell.MergeCells('1','"+row1+"','2','"+row3+"');";//照片格
		    sScript+="cell.MergeCells('3','"+row1+"','5','"+row4+"');";//职务格
		    sScript+="cell.SetCellFontSize('3','"+row1+"','0','12');";//字体大小
		    sScript+="cell.SetCellFontStyle('3','"+row1+"','0',2);";//字体
		    sScript+="cell.SetCellAlign('3','"+row1+"','0',33);";//对其方式
		    sScript+="cell.SetCellTextStyle('3','"+row1+"','0','2');";//自动换行
		    sScript+="cell.MergeCells('6','"+row1+"','8','"+row4+"');";//姓名格
		    sScript+="cell.SetCellFontSize('6','"+row1+"','0','12');";//字体大小
		    sScript+="cell.SetCellFontStyle('6','"+row1+"','0',2);";//字体
		    sScript+="cell.SetCellAlign('6','"+row1+"','0',36);";//对其方式
		    sScript+="cell.SetCellTextStyle('6','"+row1+"','0','2');";//自动换行
		    countRow = row3;
		    sScript+="cell.MergeCells('3','"+row2+"','8','"+row3+"');";//信息格
		    sScript+="cell.SetCellFontSize('3','"+row2+"','0','12');";//字体大小
		    sScript+="cell.SetCellAlign('3','"+row2+"','0','9');";//对其方式
		    sScript+="cell.SetCellTextStyle('3','"+row2+"','0','2');";//自动换行
		     row1 += 11;//平移
		     row2 += 11;//平移
		     row3 += 11;//平移
		     row4 += 11;//平移
	    	//循环插入值
	    	 map = listy.get(i);
	    	 String message ="";
	    		String photopath = "";//拼接照片绝对路径
	    		String photoname = "";//拼接照片绝对路径
	    		photopath = map.get("photopath").toUpperCase();
	    		photoname = map.get("photoname");
	    		String birthmes = "";
	    		String xlmes = "";
	    		String joinmes = "";
	    		String workmes = "";
	    		String rzmes = "";
	    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
	    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"生， ";
	    		}
	    		if(map.get("qrzxl")!=null){
	    			xlmes += map.get("qrzxl")+"， ";
	    		}
	    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
	    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"入党， ";
	    		}
	    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
	    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"参加工作， ";
	    		}
	    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
	    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
	    		}
	    		message += map.get("a0111a")+"人， ";// 哪里人
				message += birthmes;// 生日
				message += xlmes;// 学历
				message += joinmes;// 入党
				message += workmes;// 参加工作
				message += rzmes+"任现职。";// 任职时间
				//插入图片
	    		sScript+="cell.SetCellImage('1','"+rowj1+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
	    		//插入职务
	    		sScript+="cell.SetCellString('3','"+rowj1+"','0','"+map.get("a0192")+"');";
	    		//插入姓名
	    		sScript+="cell.SetCellString('6','"+rowj1+"','0','"+map.get("a0101")+"');";
	    		//插入信息
	    		sScript+="cell.SetCellString('3','"+rowj2+"','0','"+message+"');";
	    		//刷新
	    		sScript+="cell.Invalidate();";
	    		message ="";
	    		photopath ="";
	    		photoname ="";
	    	rowj1 += 11;//平移
	    	rowj2 += 11;//平移
	    }
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
	    sScript+="cell.PrintSetOrient('0');";//设置进纸方向
	    sScript+="cell.PrintRange('1','1','8','"+y+"');";//设置打印区域
//	    sScript+="cell.PrintSetMargin('150','250','350','250');";//设置页边距
	    sScript+="cell.PrintSetAlign('0','0');";//设置第对其方式
	    sScript+="cell.PrintPageBreak('0');";//拆分
	    sScript+="}";
		sScript+="</script>";
	    return sScript;	
	}
	//一行按机构分组
	public  String yihangjgfz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"0");
		List<Map<String,String>> listy = returnlist(id,"0");
		int size = listy.size();//总人数
		int count = size*13; // 需要的行数
		int z = count - 50; // 需要添加的行数
		int y =0; //总行数
		y =count;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		String sql = "select t.a0201a from (select a.a0201a,a.b0111,a.b0114 from (select a02.a0201a,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+ids+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201a,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t"; // 获取机构
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// 获取的机构存入list
		int row1 = 1;//设置默认值
		int row2 = 2;//设置默认值
		int geshu = 0;
		int jigoushu = 0;
		int falsecount = 0;
		int zhaopian = 0;
		int fenqu = 0;//设置硬分页
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// 获取选的每一个机构名称
			Map<String, String> map = new HashMap<String, String>();
			for (int m = 0; m < size; m++) {
				String message = "";
				map = listy.get(m);// 获取每个人的信息
				if (jigmc1.equals(map.get("a0201a"))) {// 判断当前人是否是当前机构
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ row1 + "','7','" + (row1+1) + "');";// 机构行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+row1+"','0','16');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+row1+"','0','12');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+row1+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+row1+"','0','2');";//粗体
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+ row1 + "','0','" + map.get("a0201a") + "');";// 插入机构名称
					String photopath = "";//拼接照片绝对路径
					String photoname = "";//拼接照片绝对路径
		    		photopath = map.get("photopath").toUpperCase();
		    		photoname = map.get("photoname");
		    		String birthmes = "";
		    		String xlmes = "";
		    		String joinmes = "";
		    		String workmes = "";
		    		String rzmes = "";
		    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
		    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"生， ";
		    		}
		    		if(map.get("qrzxl")!=null){
		    			xlmes += map.get("qrzxl")+"， ";
		    		}
		    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
		    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"入党， ";
		    		}
		    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
		    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"参加工作， ";
		    		}
		    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
		    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
		    		}
		    		message += map.get("a0111a")+"人， ";// 哪里人
					message += birthmes;// 生日
					message += xlmes;// 学历
					message += joinmes;// 入党
					message += workmes;// 参加工作
					message += rzmes+"任现职。";// 任职时间
					// 合并单元格
					// 照片行
					sScript += "document.getElementById('DCellWeb1').MergeCells('1','"+(row2+1+geshu*11+jigoushu*2)+"','2','"+(row2+3+6+geshu*11+jigoushu*2)+"');";
					zhaopian = (row2+3+6+geshu*11+jigoushu*2);
					fenqu++;
					if(fenqu == 4){//行硬分页符
						sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((row2+3+6+geshu*11+jigoushu*2)+1)+"','1');";
						fenqu=0;
					}
					// 职务行
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+(row2+1+geshu*11+jigoushu*2)+"','6','"+(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',2);";//字体
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',33);";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//自动换行
					// 姓名行
					sScript += "document.getElementById('DCellWeb1').MergeCells('7','"+(row2+1+geshu*11+jigoushu*2)+"','9','" +(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('7','"+(row2+1+geshu*11+jigoushu*2)+"','0',36);";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//粗体
					// 信息行
					countRow  = ( row2+3+6+geshu*11 +jigoushu*2);
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ (row2 + 4+geshu*11+jigoushu*2) + "','9','" +( row2+3+6+geshu*11 +jigoushu*2)+ "');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','12');";//字体大小
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','9');";//对其方式
				    sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//自动换行
					// 写入信息
				    //照片行
				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
				    // 职务行
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0192")+ "');";
					// 姓名行
					sScript += "document.getElementById('DCellWeb1').SetCellString('7','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0101")+ "');";
					// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+4+geshu*11+jigoushu*2) + "','0','" + message + "');";
					message = "";
					photopath = "";
					geshu++;	
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				jigoushu++;
				row1 = 1;
				row1 += geshu*11;
				row1 += jigoushu*2;
			}
			falsecount = 0;
		}
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('0');";//设置进纸方向
//	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//设置第对其方式
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//拆分
	    sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ zhaopian +"');";//设置打印区域
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}
	//一行层次分组
	public  String yihangchengc(HttpServletRequest request) {
		int countRow = 0 ;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
//		List<Map<String,String>> listy = returnlist1(id,"1");
		List<Map<String,String>> listy = returnlist(id,"1");
		int size = listy.size();//总人数
		int count = size*13; // 需要的行数
//		int z = count - 50; // 需要添加的行数
		int y =0; //总行数
		y =count;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行

		/*if(count - 50 > 0){
    		y=50+z;
    	 sScript+="document.getElementById('DCellWeb1').InsertRow(50,"+z+",'0');";//插入行
    	
    }*/
//		String message = "";
		String sql = "select a.code_name from (select cv.code_name,k.a0148 from code_value cv,(select t.a0148 from (select a01.a0149,a01.a0148 from a01 a01 where a01.a0000 in ("+ids+") group by a01.a0149,a01.a0148) t order by t.a0148) k where cv.code_type='ZB09' and cv.code_value=k.a0148 group by cv.code_name,k.a0148 order by k.a0148) a"; // 获取机构
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// 获取的机构存入list
		jigmc.removeAll(Collections.singleton(null));
		int row1 = 1;//设置默认值
		int row2 = 2;//设置默认值
		int geshu = 0;
		int jigoushu = 0;
		int falsecount = 0;
		int zhaopian = 0;
		int fenqu = 0;//设置硬分页
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// 获取选的每一个机构名称
			Map<String, String> map = new HashMap<String, String>();
			for (int m = 0; m < size; m++) {
				String message = "";
				map = listy.get(m);// 获取每个人的信息
				if (jigmc1.equals(map.get("a0149"))) {// 判断当前人是否是当前机构
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ row1 + "','7','" + (row1+1) + "');";// 机构行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+row1+"','0','16');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+row1+"','0','12');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+row1+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+row1+"','0','2');";//粗体
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+ row1 + "','0','" + map.get("a0149") + "');";// 插入机构名称
					String photopath = "";//拼接照片绝对路径
					String photoname = "";//拼接照片绝对路径
		    		photopath = map.get("photopath").toUpperCase();
		    		photoname = map.get("photoname");
		    		String birthmes = "";
		    		String xlmes = "";
		    		String joinmes = "";
		    		String workmes = "";
		    		String rzmes = "";
		    		if(map.get("a0107")!=null&&!"".equals(map.get("a0107"))&&map.get("a0107").length()>=6){
		    			birthmes += map.get("a0107").substring(0, 4)+"."+map.get("a0107").substring(4, 6)+"生， ";
		    		}
		    		if(map.get("qrzxl")!=null){
		    			xlmes += map.get("qrzxl")+"， ";
		    		}
		    		if(map.get("a0144")!=null&&!"".equals(map.get("a0144"))&&map.get("a0144").length()>=6){
		    			joinmes += map.get("a0144").substring(0, 4)+"."+map.get("a0144").substring(4, 6)+"入党， ";
		    		}
		    		if(map.get("a0134")!=null&&!"".equals(map.get("a0134"))&&map.get("a0134").length()>=6){
		    			workmes += map.get("a0134").substring(0, 4)+"."+map.get("a0134").substring(4, 6)+"参加工作， ";
		    		}
		    		if(map.get("a0243")!=null&&!"".equals(map.get("a0243"))&&map.get("a0243").length()>=6){
		    			rzmes += map.get("a0243").substring(0, 4)+"."+map.get("a0243").substring(4, 6);
		    		}
		    		message += map.get("a0111a")+"人， ";// 哪里人
					message += birthmes;// 生日
					message += xlmes;// 学历
					message += joinmes;// 入党
					message += workmes;// 参加工作
					message += rzmes+"任现职。";// 任职时间
					// 合并单元格
					// 照片行
					sScript += "document.getElementById('DCellWeb1').MergeCells('1','"+(row2+1+geshu*11+jigoushu*2)+"','2','"+(row2+3+6+geshu*11+jigoushu*2)+"');";
					zhaopian = (row2+3+6+geshu*11+jigoushu*2);
					fenqu++;
					if(fenqu == 4){//行硬分页符
						sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((row2+3+6+geshu*11+jigoushu*2)+1)+"','1');";
						fenqu=0;
					}
//					sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ (row2+3+6+geshu*11+jigoushu*2) +"');";//设置打印区域
					// 职务行
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+(row2+1+geshu*11+jigoushu*2)+"','6','"+(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2+1+geshu*11+jigoushu*2)+"','0',33);";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//粗体
					// 姓名行
					sScript += "document.getElementById('DCellWeb1').MergeCells('7','"+(row2+1+geshu*11+jigoushu*2)+"','9','" +(row2+3+geshu*11+jigoushu*2)+"');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','12');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('7','"+(row2+1+geshu*11+jigoushu*2)+"','0',36);";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('7','"+(row2+1+geshu*11+jigoushu*2)+"','0','2');";//粗体

					// 信息行
					sScript += "document.getElementById('DCellWeb1').MergeCells('3','"+ (row2 + 4+geshu*11+jigoushu*2) + "','9','" +( row2+3+6+geshu*11 +jigoushu*2)+ "');";
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','12');";//字体大小
				    sScript+="document.getElementById('DCellWeb1').SetCellAlign('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','9');";//对其方式
				    sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('3','"+(row2 + 4+geshu*11+jigoushu*2)+"','0','2');";//粗体
				    countRow = ( row2+3+6+geshu*11 +jigoushu*2);
					// 写入信息
				    //照片行
//				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+photopath+"'),'1','1','1');";
				    sScript+="document.getElementById('DCellWeb1').SetCellImage('1','"+(row2+1+geshu*11+jigoushu*2)+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
				    // 职务行
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0192")+ "');";
					// 姓名行
					sScript += "document.getElementById('DCellWeb1').SetCellString('7','"+(row2+1+geshu*11+jigoushu*2)+ "','0','"+ map.get("a0101")+ "');";
					// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('3','"+(row2+4+geshu*11+jigoushu*2) + "','0','" + message + "');";
					message = "";
					photopath = "";
					geshu++;
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				jigoushu++;
				row1 = 1;
				row1 += geshu*11;
				row1 += jigoushu*2;
			}
			falsecount = 0;
		}
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('0');";//设置进纸方向
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
//	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('150','100','200','100');";//设置页边距
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//设置第对其方式
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//拆分
//	    sScript+="document.getElementById('DCellWeb1').SetColPageBreak('10','1');";//lie
//	    int o = (row2+3+6+geshu*11+jigoushu*2+1);
//	    if(fenqu >0){
	    	sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','9','"+ zhaopian +"');";//设置打印区域
//	    }
		sScript+="}";
		sScript+="</script>";
		return sScript;	
	}
	
	public List<Map<String, String>> returnlist(String[] id,String type){
		String sql = "";
		String sql2 = "";
		String sql3 = "";
		List<Object[]> sqllist =  null;
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<id.length;i++){
			Map<String, String> map = new HashMap<String, String>();
			sql = "select count(1) from a57 a57 where  a57.a0000="+id[i]+"";
			sql2 = "select a01.a0117 from a01 a01 where  a01.a0000="+id[i]+"";
			sql3 = "select a01.a0104 from a01 a01 where  a01.a0000="+id[i]+"";
			List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
			List sqllist2 = HBUtil.getHBSession().createSQLQuery(sql2).list();
			List sqllist3 = HBUtil.getHBSession().createSQLQuery(sql3).list();
			if("0".equals(sqllist1.get(0).toString())){
				if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a02.a0000 a02a0000,                                                        "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c2 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", sqllist3.get(0).toString());
				}else if((!"".equals(sqllist2.get(0)) &&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c1 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +" and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0104", "");
					map.put("a0117", sqllist2.get(0).toString());
				}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +" and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", "");
				}else{
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223                                                     "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
					       +"where a02.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null         "
					       +"and a01.a0000="+id[i]+"  ";
				}
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				map.put("photopath", "");
				map.put("photoname", "");
//				System.out.println("---->"+sql);
				sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				/*map.put("photopath", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
				map.put("photoname", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");*/
			}else{
				if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))||!"null".equals(sqllist3.get(0))||sqllist3.get(0)!=null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104",sqllist3.get(0).toString());
				}else if((!"".equals(sqllist2.get(0))||!"null".equals(sqllist2.get(0))||sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +" and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0104", "");
					map.put("a0117", sqllist2.get(0).toString());
				}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"a01.a0000 a01a0000,                                             "
					       +"a01.a0111a,                                                     "
					       +"a02.a0000 a02a0000,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +" and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
					map.put("a0117", "");
					map.put("a0104", "");
				}else{
					//------------------
					sql ="select a01.a0000,                                               "  
					       +"a01.a0101,                                                      "
					       +"c1.code_name a0117,                                             "
					       +"a01.a0111a,                                                     "
					       +"c2.code_name a0104,                                             "
					       +"a01.a0107,                                                      "
					       +"a02.a0216a,                                                     "
					       +"a01.QRZXL,                                                      "
					       +"a01.ZZXL,                                                       "
					       +"a01.A0144,                                                      "
					       +"a02.a0221 a0148,                                                      "
					       +"a01.a0192,                                                      "
					       +"(select code_name "
					       +"from code_value "
					       +"where code_type = 'ZB09' "
					       +"and code_value = a02.a0221) a0149,                              "
					       +"a01.A0134,                                                      "
					       +"a02.A0243,                                                      "
					       +"a02.A0288,                                                      "
					       +"a02.A0201A,                                                     "
					       +"a02.A0201b,                                                     "
					       +"a01.a0180,                                                      "
					       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
					       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
					       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
					       +"and a02.A0255 = '1'                                             "
					       +"and a01.status = '1'                                            "
					       +"AND c1.code_type = 'GB3304'                                     "
					       +"and c1.code_value = a01.a0117                                   "
					       +"AND c2.code_type = 'GB2261'                                     "
					       +"and c2.code_value = a01.a0104 and a02.a0221 is not null        "
					       +"and a01.a0000="+id[i]+"  ";
				}
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				System.out.println(sqllist.toString());
				map.put("photopath", ((String) sqllist.get(0)[20] != null  && !((String) sqllist.get(0)[20].toString()).equals("null"))?(String) sqllist.get(0)[20]:"");
				map.put("photoname", ((String) sqllist.get(0)[21] != null  && !((String) sqllist.get(0)[21].toString()).equals("null"))?(String) sqllist.get(0)[21]:"");
			}
			map.put("a0000", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
			map.put("a0101", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");
//			map.put("a0117", ((String) sqllist.get(0)[2] != null  && !((String) sqllist.get(0)[2].toString()).equals("null"))?(String) sqllist.get(0)[2]:"");
			map.put("a0111a",((String) sqllist.get(0)[3] != null  && !((String) sqllist.get(0)[3].toString()).equals("null"))?(String) sqllist.get(0)[3]:"");
//			map.put("a0104", ((String) sqllist.get(0)[4] != null  && !((String) sqllist.get(0)[4].toString()).equals("null"))?(String) sqllist.get(0)[4]:"");
			map.put("a0107", ((String) sqllist.get(0)[5] != null  && !((String) sqllist.get(0)[5].toString()).equals("null"))?(String) sqllist.get(0)[5]:"");
			map.put("a0216a",((String) sqllist.get(0)[6] != null  && !((String) sqllist.get(0)[6].toString()).equals("null"))?(String) sqllist.get(0)[6]:"");
			map.put("qrzxl", ((String) sqllist.get(0)[7] != null  && !((String) sqllist.get(0)[7].toString()).equals("null"))?(String) sqllist.get(0)[7]:"");
			map.put("zzxl", ((String) sqllist.get(0)[8] != null  && !((String) sqllist.get(0)[8].toString()).equals("null"))?(String) sqllist.get(0)[8]:"");
			map.put("a0144", ((String) sqllist.get(0)[9] != null  && !((String) sqllist.get(0)[9].toString()).equals("null"))?(String) sqllist.get(0)[9]:"");
			map.put("a0148", ((String) sqllist.get(0)[10] != null  && !((String) sqllist.get(0)[10].toString()).equals("null"))?(String) sqllist.get(0)[10]:"");
			map.put("a0192", (((String) sqllist.get(0)[16] != null  && !((String) sqllist.get(0)[16].toString()).equals("null"))?(String) sqllist.get(0)[16]:"")+(((String) sqllist.get(0)[6] != null  && !((String) sqllist.get(0)[6].toString()).equals("null"))?(String) sqllist.get(0)[6]:""));
			map.put("a0149", ((String) sqllist.get(0)[12] != null  && !((String) sqllist.get(0)[12].toString()).equals("null"))?(String) sqllist.get(0)[12]:"");
			map.put("a0134", ((String) sqllist.get(0)[13] != null  && !((String) sqllist.get(0)[13].toString()).equals("null"))?(String) sqllist.get(0)[13]:"");
			map.put("a0243", ((String) sqllist.get(0)[14] != null  && !((String) sqllist.get(0)[14].toString()).equals("null"))?(String) sqllist.get(0)[14]:"");
			map.put("a0288", ((String) sqllist.get(0)[15] != null  && !((String) sqllist.get(0)[15].toString()).equals("null"))?(String) sqllist.get(0)[15]:"");
			map.put("a0201a", ((String) sqllist.get(0)[16] != null  && !((String) sqllist.get(0)[16].toString()).equals("null"))?(String) sqllist.get(0)[16]:"");
			map.put("a0201b", ((String) sqllist.get(0)[17] != null  && !((String) sqllist.get(0)[17].toString()).equals("null"))?(String) sqllist.get(0)[17]:"");
			map.put("a0180", ((String) sqllist.get(0)[18] != null  && !((String) sqllist.get(0)[18].toString()).equals("null"))?(String) sqllist.get(0)[18]:"");
			map.put("a0223", (( sqllist.get(0)[19] != null  && !( sqllist.get(0)[19]+"").equals("null"))? sqllist.get(0)[19]+"":"")+"");
			
			list.add(map);
		}
		System.out.println(list.toString());
		return list;
	}
	
	/*public List<Map<String, String>> returnlist1(String[] id,String type){
		String sql = "";
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		for(int i=0;i<id.length;i++){
			sql = "select count(1) from a57 a57 where  a57.a0000="+id[i]+"";
			List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
			Map<String, String> map = new HashMap<String, String>();
			List<Object[]> sqllist = null;
			if("0".equals(sqllist1.get(0).toString())){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192a,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223                                                     "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     "
				       +"and c2.code_value = a01.a0104  and a02.a0221 is not null                                 "
				       +"and a01.a0000="+id[i]+" ";
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				 sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				map.put("photopath", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
				map.put("photoname", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");
			}else{

				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192a,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223, a57.photopath,a57.PHOTONAME                                                      "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2,A57 a57 "
				       +"where a02.a0000 = a01.a0000 and a57.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     "
				       +"and c2.code_value = a01.a0104 and a02.a0221 is not null                                  "
				       +"and a01.a0000="+id[i]+"  ";
				if(type == "0"){
					sql = sql +"order by a0215a asc";
				}else{
					sql = sql +"order by a0221 asc ";
				}
				sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
				map.put("photopath", ((String) sqllist.get(0)[20] != null  && !((String) sqllist.get(0)[20].toString()).equals("null"))?(String) sqllist.get(0)[20]:"");
				map.put("photoname", ((String) sqllist.get(0)[21] != null  && !((String) sqllist.get(0)[21].toString()).equals("null"))?(String) sqllist.get(0)[21]:"");
				
			}
			map.put("a0000", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
			map.put("a0101", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");
			map.put("a0117", ((String) sqllist.get(0)[2] != null  && !((String) sqllist.get(0)[2].toString()).equals("null"))?(String) sqllist.get(0)[2]:"");
			map.put("a0111a",((String) sqllist.get(0)[3] != null  && !((String) sqllist.get(0)[3].toString()).equals("null"))?(String) sqllist.get(0)[3]:"");
			map.put("a0104", ((String) sqllist.get(0)[4] != null  && !((String) sqllist.get(0)[4].toString()).equals("null"))?(String) sqllist.get(0)[4]:"");
			map.put("a0107", ((String) sqllist.get(0)[5] != null  && !((String) sqllist.get(0)[5].toString()).equals("null"))?(String) sqllist.get(0)[5]:"");
			map.put("a0216a",((String) sqllist.get(0)[6] != null  && !((String) sqllist.get(0)[6].toString()).equals("null"))?(String) sqllist.get(0)[6]:"");
			map.put("qrzxl", ((String) sqllist.get(0)[7] != null  && !((String) sqllist.get(0)[7].toString()).equals("null"))?(String) sqllist.get(0)[7]:"");
			map.put("zzxl", ((String) sqllist.get(0)[8] != null  && !((String) sqllist.get(0)[8].toString()).equals("null"))?(String) sqllist.get(0)[8]:"");
			map.put("a0144", ((String) sqllist.get(0)[9] != null  && !((String) sqllist.get(0)[9].toString()).equals("null"))?(String) sqllist.get(0)[9]:"");
			map.put("a0148", ((String) sqllist.get(0)[10] != null  && !((String) sqllist.get(0)[10].toString()).equals("null"))?(String) sqllist.get(0)[10]:"");
			map.put("a0192", ((String) sqllist.get(0)[11] != null  && !((String) sqllist.get(0)[11].toString()).equals("null"))?(String) sqllist.get(0)[11]:"");
			map.put("a0149", ((String) sqllist.get(0)[12] != null  && !((String) sqllist.get(0)[12].toString()).equals("null"))?(String) sqllist.get(0)[12]:"");
			map.put("a0134", ((String) sqllist.get(0)[13] != null  && !((String) sqllist.get(0)[13].toString()).equals("null"))?(String) sqllist.get(0)[13]:"");
			map.put("a0243", ((String) sqllist.get(0)[14] != null  && !((String) sqllist.get(0)[14].toString()).equals("null"))?(String) sqllist.get(0)[14]:"");
			map.put("a0288", ((String) sqllist.get(0)[15] != null  && !((String) sqllist.get(0)[15].toString()).equals("null"))?(String) sqllist.get(0)[15]:"");
			map.put("a0201a", ((String) sqllist.get(0)[16] != null  && !((String) sqllist.get(0)[16].toString()).equals("null"))?(String) sqllist.get(0)[16]:"");
			map.put("a0201b", ((String) sqllist.get(0)[17] != null  && !((String) sqllist.get(0)[17].toString()).equals("null"))?(String) sqllist.get(0)[17]:"");
			map.put("a0180", ((String) sqllist.get(0)[18] != null  && !((String) sqllist.get(0)[18].toString()).equals("null"))?(String) sqllist.get(0)[18]:"");
			map.put("a0223", (( sqllist.get(0)[19] != null  && !( sqllist.get(0)[19]+"").equals("null"))? sqllist.get(0)[19]+"":"")+"");
			
			list.add(map);
		}
		return list;
	}*/
	
	//四人不分组
	public  String sibufz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
		List<Map<String,String>> listy  = returnlist(id,"0");
		int size = listy.size();//总人数
		
		/*int count = (size/4)*16; // 需要的行数
		int z = count - 50; // 需要添加的行数
		int y =0; //总行数
		y =count;
		*/
		int size2 = (listy.size()-3)*16;//需要插入的行数
		String sScript="";
		sScript+="<script type='text/javascript' >";
	    sScript+="function a(){";
	    sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
	    sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
	   /* if(count - 50 > 0){
    		y=50+z;
    	 sScript+="document.getElementById('DCellWeb1').InsertRow(50,"+z+",'0');";//插入行
    	
	    	}*/
//	    sScript+= "alert(123456789);";
//	    System.out.println("----------"+size);
//	    String message ="";
	    int row = 0;//行
	    int list = 0;//列
	    int m = 0;// 控制行
	    int n = 0;//控制列
	    int j = 1;//控制
	    for(int i = 0;i < size;i++){
	    	String message ="";
	    	String photopath = "";//拼接照片绝对路径
	    	String photoname ="";
	    	Map<String, String> map = listy.get(i);
	    	photopath = map.get("photopath").toUpperCase();
	    	photoname = map.get("photoname");
	    	String  mes1 = map.get("a0101");//姓名
	    	String  mes2 = map.get("a0192");//职务
//	    	String	mes3 = (map.get("a0243")+"  任职");//任职时间
	    	String mes32 = "";
	    	String mes33 = "";
	    	String	mes3 = "";
	    	try{
	    		String	mes30 = (map.get("a0243"));//任职时间
	    		String mes31 = mes30.replace(".", "");
		    	mes32 = mes31.substring(0,4)+".";
		    	mes33 = mes31.substring(4, 6);
		    	mes3 = mes32 + mes33+"任职";
	    	}catch(Exception e){
	    		mes3 = "";
	    	}
//	    	String	mes40 = (map.get("a0107")+"  生");//生日
	    	String	mes4 = "";
	    	try {
		    	String	mes40 = (map.get("a0107"));//生日
		    	String mes41 = mes40.replace(".", "");
		    	String mes42 = mes41.substring(0,4)+".";
		    	String mes43 = mes41.substring(4, 6);
		    	mes4 = mes42 + mes43+"生";
			} catch (Exception e) {
				mes4 = "";
			}
	    	String	mes5 = map.get("a0111a");//哪里人
	    	String	mes6 = map.get("qrzxl");//学历
	    	
//	    	String	mes7 = (map.get("a0134")+"  参加工作");//参加工作时间
	    	String	mes7 ="";
	    	try {
	    		String mes70 = (map.get("a0134")+"  参加工作");//参加工作时间
		    	String mes71 = mes70.replace(".", "");
		    	String mes72 = mes71.substring(0,4)+".";
		    	String mes73 = mes71.substring(4, 6);
		    	mes7 = mes72 + mes73+"参加工作";
			} catch (Exception e) {
				mes7 = "";
			}
	    	 //合并单元格
//	    	sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*n))+"','"+(row+2+(3*m))+"','"+(list+7+(7*n))+"');";//照片格
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*n)+1)+"','"+(row+2+(3*m))+"','"+(list+(8*n)+9)+"');";//照片格

//		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+"','"+(row+2+(3*m))+"','"+(list+7+(7*(n+1)))+"');";//信息行
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+1+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+2+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+3+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+4+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+5+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+6+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1)+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+7+1)+"');";//信息行
		    sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1)+"','0','10');";//字体大小
			sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1)+"','0','9');";//对其方式
			sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1)+"','0','2');";//自动换行
//			sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+"','0','2');";//粗体
		    
		    //插入信息
//		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+ "','0','" + message + "');";// 照片行
//			sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+1)+"','0',document.getElementById('DCellWeb1').AddImage('"+photopath+"'),'1','1','1');";
			sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+1)+"','0',document.getElementById('DCellWeb1').AddImage('"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+ photoname +"'),'1','1','1');";

		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1)+ "','0','" + mes1 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1)+ "','0','" + mes2 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1)+ "','0','" + mes3 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1)+ "','0','" + mes4 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1)+ "','0','" + mes5 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1)+ "','0','" + mes6 + "');";// 信息行
		    sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1)+ "','0','" + mes7 + "');";// 信息行
			sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','11','"+ (list+(8*j)+7+1) +"');";//设置打印区域
			countRow = (list+(8*j)+7+1);
		    m++;
		    photopath = "";
		    photoname ="";
		    if(m==4){
		    	m=0;
		    	n=(j+1);
		    	j=(n+1);
		    }
	    }
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
	    sScript+="document.getElementById('DCellWeb1').PrintSetOrient('1');";//设置进纸方向
//	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('100','250','550','200');";//设置页边距
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//设置第对其方式
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('0');";//拆分
	    sScript+="}";
		sScript+="</script>";
	    return sScript;	
	}

	//四行按机构分组
	public  String sianjifz(HttpServletRequest request) {
		int countRow = 0;
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
		List<Map<String,String>> listy = returnlist(id,"0");
		int size = listy.size();//总数
		/*int count = size*19; // 需要的行数
		int z = count - 50; // 需要添加的行数
		int y =0; //总行数
		y =count;*/
		int lastcount = 0;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行
		/*if(count - 50 > 0){
			y=50+z;
			sScript+="document.getElementById('DCellWeb1').InsertRow(50,"+z+",'0');";//插入行
			
		}*/
		String sql = "select t.a0201a from (select a.a0201a,a.b0111,a.b0114 from (select a02.a0201a,b01.b0111,b01.b0114 from a02 a02,b01 b01 where a02.a0000 in("+ids+") and a02.a0201b=b01.b0111  order by length(b01.b0111),b01.b0114) a group by a.a0201a,a.b0111,a.b0114 order by length(a.b0111),a.b0114) t"; // 获取机构
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// 获取的机构存入list
		int row1 = 1;
		int row2 = 2;
		int falsecount = 0;
		int row = 0;//行
		int list = 0;//列
		int m = 0;// 控制行
		int n = 0;//控制列
		int j = 1;//控制
		int a = 0;//控制信息总体的位置
		int X = 0;
		int y = 0;
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// 获取选的每一个机构名称
			Map<String, String> map = new HashMap<String, String>();
			for (int q = 0; q < size; q++) {
				map = listy.get(q);// 获取每个人的信息
				if (jigmc1 != null &&jigmc1.equals(map.get("a0201a"))) {// 判断当前人是否是当前机构
					String photopath = "";//拼接照片绝对路径
					String photoname = "";//拼接照片绝对路径
					photopath = map.get("photopath").toUpperCase();
					photoname = map.get("photoname");
					sScript += "document.getElementById('DCellWeb1').MergeCells('2','"+row1+"','10','"+row2+"');";// 机构行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('2','"+row1+"','0','16');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('2','"+row1+"','0','12');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('2','"+row1+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('2','"+row1+"','0','2');";//粗体
					sScript += "document.getElementById('DCellWeb1').SetCellString('2','"+row1+"','0','" + map.get("a0201a") + "');";// 插入机构名称
					String  mes1 = map.get("a0101");//姓名
					String  mes2 = map.get("a0192");//职务
//			    	String	mes3 = (map.get("a0243")+"  任职");//任职时间
			    	String mes32 = "";
			    	String mes33 = "";
			    	String	mes3 = "";
			    	try{
			    		String	mes30 = (map.get("a0243"));//任职时间
			    		String mes31 = mes30.replace(".", "");
				    	mes32 = mes31.substring(0,4)+".";
				    	mes33 = mes31.substring(4, 6);
				    	mes3 = mes32 + mes33+"任职";
			    	}catch(Exception e){
			    		mes3 = "";
			    	}
//			    	String	mes40 = (map.get("a0107")+"  生");//生日
			    	String	mes4 = "";
			    	try {
				    	String	mes40 = (map.get("a0107"));//生日
				    	String mes41 = mes40.replace(".", "");
				    	String mes42 = mes41.substring(0,4)+".";
				    	String mes43 = mes41.substring(4, 6);
				    	mes4 = mes42 + mes43+"生";
					} catch (Exception e) {
						mes4 = "";
					}
			    	String	mes5 = map.get("a0111a");//哪里人
			    	String	mes6 = map.get("qrzxl");//学历
			    	
//			    	String	mes7 = (map.get("a0134")+"  参加工作");//参加工作时间
			    	String	mes7 ="";
			    	try {
			    		String mes70 = (map.get("a0134")+"  参加工作");//参加工作时间
				    	String mes71 = mes70.replace(".", "");
				    	String mes72 = mes71.substring(0,4)+".";
				    	String mes73 = mes71.substring(4, 6);
				    	mes7 = mes72 + mes73+"参加工作";
					} catch (Exception e) {
						mes7 = "";
					}
					//合并单元格
//				    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*n))+"','"+(row+2+(3*m))+"','"+(list+7+(7*n))+"');";//照片格
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+((list+(8*n)+3+a)+(2*X))+"','"+(row+2+(3*m))+"','"+((list+(8*n)+7+2+2+a)+(2*X))+"');";//照片格
					
//					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+"','"+(row+2+(3*m))+"','"+(list+7+(7*(n+1)))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"');";//信息行
					lastcount = list+(8*j)+7+1+(2+a)+(2*X);
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a))+"','0','2');";//粗体
					
					//插入信息
//					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+ "','0','" + message + "');";// 照片行
//					sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+3+a+(2*X))+"','0',document.getElementById('DCellWeb1').AddImage('"+photopath+"'),'1','1','1');";
					sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+3+a+(2*X))+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
					
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+ "','0','" + mes1 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+ "','0','" + mes2 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+ "','0','" + mes3 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+ "','0','" + mes4 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+ "','0','" + mes5 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+ "','0','" + mes6 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+ "','0','" + mes7 + "');";// 信息行
					sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','11','"+ (list+(8*j)+7+1+(2+a)+(2*X)) +"');";//设置打印区域
					countRow = (list+(8*j)+7+1+(2+a)+(2*X));
					m++;
					y++;
					photopath = "";
					photoname = "";
					if(m==4){
						X++;
						m=0;
						n=(j+1);
						j=(n+1);
					}
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				if(y%4==0){
					row1+=y/4*18;
					row2+=y/4*18;
				}else{
					row1+=(y/4+1)*18;
					row2+=(y/4+1)*18;
				}
				if(y != 4){
					a+=2;
					n=(j+1);
					j=(n+1);
				}
				
				m=0;
			}
			falsecount = 0;
			y=0;
		}
		sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+list+(8*j)+7+1+(2+a)+(2*X)+"','1');";
		int L = 0 ;
		int xunxh = lastcount/37;
		for(int s = 1; s <= xunxh; s++){
			sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((37*s)-L)+"','1');";//硬分页符
			L++;
		}
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('1');";//设置进纸方向
//		sScript+="document.getElementById('DCellWeb1').PrintSetMargin('100','290','150','290');";//设置页边距
		sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//设置第对其方式
		sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//拆分
		sScript+="}";
		sScript+="</script>";
		return sScript;	
		
	}
	public  String sianchengc(HttpServletRequest request) {
		int countRow = 0;
		String tpid = (String) request.getSession().getAttribute("tpid");//模板类型
		String ids = "";
		String idso = "";
		String idsoo = "";
		if(request.getSession().getAttribute("personids")!=null && !"".equals(request.getSession().getAttribute("personids"))){
			idso = request.getSession().getAttribute("personids").toString().replace("|", "'").replace("@", ",");
		}
		if(request.getSession().getAttribute("personidsall")!=null && !"".equals(request.getSession().getAttribute("personidsall"))){
			idsoo = request.getSession().getAttribute("personidsall").toString().replace("|", "'").replace("@", ",");
		}
		if(idso != null && !"".equals(idso)){
			ids = idso;
		}else{
			ids = idsoo;
		}
		String[] id = ids.split(",");
		List<Map<String,String>> listy = returnlist(id,"1");
		int size = listy.size();//总数
		/*int count = size*19; // 需要的行数
		int z = count - 50; // 需要添加的行数
		int y =0; //总行数
		y =count;*/
		int lastcount = 0;
		String sScript = "";
		sScript += "<script type='text/javascript' >";
		sScript += "function a(){";
		sScript+="document.getElementById('DCellWeb1').AllowDragdrop = false;";//不可编辑
		sScript+="document.getElementById('DCellWeb1').InsertRow(50,65000,'0');";//插入行

		/*if(count - 50 > 0){
    		y=50+z;
    	 sScript+="document.getElementById('DCellWeb1').InsertRow(50,"+z+",'0');";//插入行
    	
    }*/
		String sql = "select a.code_name from (select cv.code_name,k.a0148 from code_value cv,(select t.a0148 from (select a01.a0149,a01.a0148 from a01 a01 where a01.a0000 in ("+ids+") group by a01.a0149,a01.a0148) t order by t.a0148) k where cv.code_type='ZB09' and cv.code_value=k.a0148 group by cv.code_name,k.a0148 order by k.a0148) a"; // 获取层次
		System.out.println(sql);
		List jigmc = HBUtil.getHBSession().createSQLQuery(sql).list();// 获取的机构存入list
		jigmc.removeAll(Collections.singleton(null));
		int row1 = 1;
		int row2 = 2;
		int falsecount = 0;
		int row = 0;//行
		int list = 0;//列
		int m = 0;// 控制行
		int n = 0;//控制列
		int j = 1;//控制
		int a = 0;//控制信息总体的位置
		int X = 0;
		int y = 0;
		for (int i = 0; i < jigmc.size(); i++) {
			String jigmc1 = (String) jigmc.get(i);// 获取选的每一个机构名称
			System.out.println(jigmc1);
			Map<String, String> map = new HashMap<String, String>();
			for (int q = 0; q < size; q++) {
				map = listy.get(q);// 获取每个人的信息
				System.out.println(map.get("a0149"));
				if (jigmc1 != null &&jigmc1.equals(map.get("a0149"))) {// 判断当前人是否是当前机构
					String photopath = "";//拼接照片绝对路径
					String photoname = "";//拼接照片绝对路径
					photopath = map.get("photopath").toUpperCase();
					photoname = map.get("photoname");
					sScript += "document.getElementById('DCellWeb1').MergeCells('2','"+row1+"','10','"+row2+"');";// 机构行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('2','"+row1+"','0','16');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('2','"+row1+"','0','12');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('2','"+row1+"','0','2');";//自动换行
					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('2','"+row1+"','0','2');";//粗体
					
					sScript += "document.getElementById('DCellWeb1').SetCellString('2','"+row1+"','0','" + map.get("a0149") + "');";// 插入机构名称
					String  mes1 = map.get("a0101");//姓名
					System.out.println(mes1);
					String  mes2 = map.get("a0192");//职务
//			    	String	mes3 = (map.get("a0243")+"  任职");//任职时间
			    	String mes32 = "";
			    	String mes33 = "";
			    	String	mes3 = "";
			    	try{
			    		String	mes30 = (map.get("a0243"));//任职时间
			    		String mes31 = mes30.replace(".", "");
				    	mes32 = mes31.substring(0,4)+".";
				    	mes33 = mes31.substring(4, 6);
				    	mes3 = mes32 + mes33+"任职";
			    	}catch(Exception e){
			    		mes3 = "";
			    	}
//			    	String	mes40 = (map.get("a0107")+"  生");//生日
			    	String	mes4 = "";
			    	try {
				    	String	mes40 = (map.get("a0107"));//生日
				    	String mes41 = mes40.replace(".", "");
				    	String mes42 = mes41.substring(0,4)+".";
				    	String mes43 = mes41.substring(4, 6);
				    	mes4 = mes42 + mes43+"生";
					} catch (Exception e) {
						mes4 = "";
					}
			    	String	mes5 = map.get("a0111a");//哪里人
			    	String	mes6 = map.get("qrzxl");//学历
			    	
//			    	String	mes7 = (map.get("a0134")+"  参加工作");//参加工作时间
			    	String	mes7 ="";
			    	try {
			    		String mes70 = (map.get("a0134")+"  参加工作");//参加工作时间
				    	String mes71 = mes70.replace(".", "");
				    	String mes72 = mes71.substring(0,4)+".";
				    	String mes73 = mes71.substring(4, 6);
				    	mes7 = mes72 + mes73+"参加工作";
					} catch (Exception e) {
						mes7 = "";
					}
					
					
					//合并单元格
//				    sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*n))+"','"+(row+2+(3*m))+"','"+(list+7+(7*n))+"');";//照片格
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+((list+(8*n)+3+a)+(2*X))+"','"+(row+2+(3*m))+"','"+((list+(8*n)+7+2+2+a)+(2*X))+"');";//照片格
					
//					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+"','"+(row+2+(3*m))+"','"+(list+7+(7*(n+1)))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"');";//信息行
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a))+"','0','2');";//粗体
					
					sScript+="document.getElementById('DCellWeb1').MergeCells('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','"+(row+2+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"');";//信息行
					lastcount = list+(8*j)+7+1+(2+a)+(2*X);
					sScript+="document.getElementById('DCellWeb1').SetCellFontSize('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','10');";//字体大小
					sScript+="document.getElementById('DCellWeb1').SetCellAlign('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','9');";//对其方式
					sScript+="document.getElementById('DCellWeb1').SetCellTextStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+"','0','2');";//自动换行
//					sScript+="document.getElementById('DCellWeb1').SetCellFontStyle('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a))+"','0','2');";//粗体
					
					//插入信息
//					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+1+(7*(n+1)))+ "','0','" + message + "');";// 照片行
//					sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+3+a+(2*X))+"','0',document.getElementById('DCellWeb1').AddImage('"+photopath+"'),'1','1','1');";
					sScript+="document.getElementById('DCellWeb1').SetCellImage('"+(row+1+(3*m))+"','"+(list+(8*n)+3+a+(2*X))+"','0',document.getElementById('DCellWeb1').AddImage('"+ request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname +"'),'1','1','1');";
					
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+1+1+(2+a)+(2*X))+ "','0','" + mes1 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+2+1+(2+a)+(2*X))+ "','0','" + mes2 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+3+1+(2+a)+(2*X))+ "','0','" + mes3 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+4+1+(2+a)+(2*X))+ "','0','" + mes4 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+5+1+(2+a)+(2*X))+ "','0','" + mes5 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+6+1+(2+a)+(2*X))+ "','0','" + mes6 + "');";// 信息行
					sScript += "document.getElementById('DCellWeb1').SetCellString('"+(row+1+(3*m))+"','"+(list+(8*j)+7+1+(2+a)+(2*X))+ "','0','" + mes7 + "');";// 信息行
					sScript+="document.getElementById('DCellWeb1').PrintRange('1','1','11','"+ (list+(8*j)+7+1+(2+a)+(2*X)) +"');";//设置打印区域
					countRow = (list+(8*j)+7+1+(2+a)+(2*X));
					m++;
					y++;
					photopath = "";
					photoname = "";
					if(m==4){
						X++;
						m=0;
						n=(j+1);
						j=(n+1);
					}
				} else {
					falsecount++;
				}
			}
			if (falsecount == size) {
			} else {
				if(y%4==0){
					row1+=y/4*18;
					row2+=y/4*18;
				}else{
					row1+=(y/4+1)*18;
					row2+=(y/4+1)*18;
				}
				if(y != 4){
					a+=2;
					n=(j+1);
					j=(n+1);
				}
				
				m=0;
			}
			falsecount = 0;
			y=0;
		}
		sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+list+(8*j)+7+1+(2+a)+(2*X)+"','1');";
		int L = 0 ;
		int xunxh = lastcount/37;
		for(int s = 1; s <= xunxh; s++){
			sScript+="document.getElementById('DCellWeb1').SetRowPageBreak('"+((37*s)-L)+"','1');";//硬分页符
			L++;
		}
		sScript+="document.getElementById('DCellWeb1').DeleteRow("+ countRow + "," +  (65050 - countRow) + ",0);";//删除多余的行	
		sScript+="document.getElementById('DCellWeb1').PrintSetOrient('1');";//设置进纸方向
//	    sScript+="document.getElementById('DCellWeb1').PrintSetMargin('100','290','150','290');";//设置页边距
	    sScript+="document.getElementById('DCellWeb1').PrintSetAlign('0','0');";//设置第对其方式
	    sScript+="document.getElementById('DCellWeb1').PrintPageBreak('1');";//拆分
		sScript+="}";
		sScript+="</script>";
		return sScript;	
		
	}
	//获得某一字符串包含某一字符的个数
	public int getcharnu1(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			k++;
			getcharnu1(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return k;
		}
		return 0;
	}
	
	//获得某一字符串包含某一字符的个数
	public int getcharnu(String parentString,String childString){
		if(parentString.indexOf(childString) == -1){
			return 0;
		}
		else if(parentString.indexOf(childString) != -1){
			i++;
			getcharnu(parentString.substring(parentString.indexOf(childString)+childString.length()),childString);
			return i;
		}
		return 0;
	}
	public String returnMes(HttpServletRequest request,String messageE,String id,String endtime){
		String mesvalue = "";
		int count = getcharnu1(messageE,",");
		if(count > 0){
			String ms = messageE+",";
			for(int i1=0;i1<=count;i1++){
				messageE = ms.substring(0,ms.indexOf(","));
				if(messageE.contains("a01")){
					//System.out.println("1-->"+messageE);
					if("a01.a0101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}else if("a01.a0192a".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}else if("a01.a0134".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes = (String) listj.get(0);
						}
						mes = mes.replace(".", "");
						 if(mes.length() == 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else if("a01.a14z101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						mesvalue+=mes.replace("\n", "\\\\r\\\\n");
					}else if("a01.a15z101".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes +=listj.get(0);
						}
						mesvalue+=mes.replace("\n", "\\\\r\\\\n");
					}else if("a01.a0141".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
						String mes = "";
						if(listj1==null ||listj1.size() ==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.orgid".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
						String mes = "";
						if(listj1==null ||listj1.size() ==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0160".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
						String mes = "";
						if(listj1==null ||listj1.size() ==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0104a".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
						String mes = "";
						if(listj1==null ||listj1.size() ==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.a0117a".equals(messageE)){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
						String mes = "";
						if(listj1==null ||listj1.size() ==0||"null".equals(listj1.get(0))||listj1.get(0)==null||"".equals(listj1.get(0))){
							mes += "";
						}else{
							mes +=listj1.get(0);
						}
						mesvalue += mes;
					}else if("a01.xgsj".equals(messageE)){
						String mes = "";
						List listj1 = null;
						if(DBUtil.getDBType()==DBType.MYSQL){
							listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
						}else{
							listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
						}
						if(listj1.size()>0){
							mes += listj1.get(0); 
						}
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj1.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
						if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mesvalue += "";
						}else{
							mesvalue +=listj.get(0);
						}
					}
				}else if(messageE.contains("a02")){
					if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a0255= '1' ").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else if("a02.a0247".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else if("a02.a0219".equals(messageE)){
						String mess = "";
						List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 a02 where a02.a0000 = '"+id+"'").list();
						if(listj.size()==0){
							listj.add("");
						}
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mess = (String)listj.get(0);
							if("1".equals(mess)){
								mesvalue += "是";
							}
							if("2".equals(mess)){
								mesvalue += "否";
							}
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if(messageE.contains("a08")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a29")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
					if(messageE.contains("a29.a2907")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2947")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2949")){
						mesvalue += returnmesvalue2(listj);
					}else if(messageE.contains("a29.a2911")){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += (String)listj1.get(0);
						}
					}else{
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if(messageE.contains("a30")){
					if(messageE.equals("a30.a3001")){
						List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
						if(listj1.size()==0){
							listj1.add("");
						}
						if(listj1==null || listj1.size()==0 ||"".equals(((String)listj1.get(0)))||listj1.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj1.get(0);
						}
					}else if("a30.a3004".equals(messageE)){
						List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
						String mes = "";
						if(listj.size()>0||listj != null||"".equals(listj.get(0))||"null".equals(listj.get(0))){
							mes += listj.get(0); 
						}
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else {
						List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
						if(listj1.size()==0){
							listj1.add("");
						}
						if(listj1==null || listj1.size()==0 ||"".equals(((String)listj1.get(0)))||listj1.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj1.get(0);
						}
					}
				}else if(messageE.contains("a31")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
					if("a31.a3104".equals(messageE)){
						String mes = "";
						if(listj.size()>0){
							mes += listj.get(0); 
						}
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}else if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a37")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if(messageE.contains("a53")){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
					if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("csny".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes = "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mesvalue +=(String) listj.get(0);
						}
					}
//					mesvalue += mes;
				}else if("nl".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zp".equals(messageE) ){
					String mes = "";
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
					if(listj.size() ==0){
						mes = "";
					}else{
						Object[] sz = listj.get(0);
						Object photopath = sz[0].toString().toUpperCase();
						Object photoname = sz[1];
						//String path = PhotosUtil.PHOTO_PATH+photopath+photoname;
						//插入数据
						//2017.04.19 yinl 修改图片地址
						String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname;
						mesvalue += "@"+imagepath+"@";
					}
				}else if("ryzt".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null ||"".equals(listj.get(0))){
						mes = "";
						}else{
							mes = (String) listj.get(0);
							if("1".equals(mes)){
								mes="现职人员";
							}else if("2".equals(mes)){
								mes="离退人员";
							}else if("3".equals(mes)){
								mes="调出人员";
							}else if("4".equals(mes)){
								mes="已去世";
							}else{
								mes="其他人员";
							}
						}
					mesvalue += mes;
				}else if("rdsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						int length = mes.length();
						if(mes.contains(".")){
							mes = ((String)listj.get(0)).replace(".", "");
							int indexOf = mes.indexOf("(");
							int indexOf2 = mes.indexOf(")");
							String sub = mes.substring(indexOf, length);
							String year = sub.substring(1,5)+".";
							String yue = sub.substring(5,7);
							mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
						}else if(length>=6){
							mes = ((String)listj.get(0)).replace(".", "");
							mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
						}else{
							mes = ((String)listj.get(0));
						}
					}
					mesvalue += mes;
				}else if("zgxl".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxlbyxx".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxlsxzy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxlrxsj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxlbisj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxwbyxx".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
					
				}else if("zgxwsxzy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += (String)listj.get(0);
					}
				}else if("zgxwrxsj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("zgxwbisj".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
					if(listj.size()==0){
						listj.add("");
					}
					String mes= "";
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mes = "";
					}else{
						mes = (String)listj.get(0);
						mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
					}
					mesvalue += mes;
				}else if("xlqrz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rxsjqrz".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
						
				}else if("bysjqrz".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("xwqrz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("xxjyxql".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xxjyxqw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true'  and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyql".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyqw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xlzz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rxsjzz".equals(messageE) ){
					String mes ="";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("bysjzz".equals(messageE) ){
					String mes ="";
					List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
					if(listj==null ||listj.size() ==0||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("xwzz".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("xxjyxzl".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '2' and a08.A0801A != '' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '2' and a08.A0801A is not null and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("xxjyxzw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08  where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyzl".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("sxzyzw".equals(messageE) ){
					if(DBUtil.getDBType()==DBType.MYSQL){
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}else{
						List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
						if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
							mesvalue += "";
						}else{
							mesvalue += listj.get(0);
						}
					}
				}else if("qrzxlrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("qrzxlxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("qrzxwrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("qrzxwxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zzxlrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zzxixxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("zzxwrb".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zzxwxxrb".equals(messageE) ){
					List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
					String mes = "";
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
					mesvalue += mes;
				}else if("dedp".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
					if(listj.size()>0){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("dsdp".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
					if(listj.size()>0){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("zwcc".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0149 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
						mesvalue += "";
					}else{
						mesvalue += listj.get(0);
					}
				}else if("rzsj".equals(messageE) ){
					String mes =  "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("rgzwccsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0288 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("mzsj".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}
				}else if("jl".equals(messageE) ){
					ResultSet rs;
					try {
						rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
						while(rs.next()){
							mesvalue +=  formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}else if("cw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("xm".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("csnyjy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					String mes1 = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes = listj.get(i1)+"\\r\\n";
							mes = (String) listj.get(i1);
							mes = mes.replace(".", "");
							if(mes.length() >= 6){
								mes1 = mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mes1 =(String) listj.get(i1)+"\\r\\n";
							}
						}
						mesvalue += mes1;
						j++;
						if(j==9)
							break;
					}
				}else if("nljy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("zzmmjy".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("gzdwjzw".equals(messageE) ){
					List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
					String mes = "";
					int j = 0;
					for(int i11=0;i11<listj.size();i11++){
						if("null".equals(listj.get(i11))||listj.get(i11)==null||"".equals(listj.get(i11))){
							mes += "";
						}else{
							mes += listj.get(i11)+"\\r\\n";
						}
						mesvalue += mes;
						j++;
						if(j==9)
							break;
					}
				}else if("tbsjn".equals(messageE) ){
					String mes =  "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						for(int a = 0;a<listj.size();a++){
							mes = (String) listj.get(a);
							mes = mes.replace(".", "");
							 if(mes.length() >= 6){
								mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
							}else{
								mesvalue +=(String) listj.get(a)+"\\r\\n";
							}
						}
					}	
					/*String mes = "";
					List listj = null;
					if(DBUtil.getDBType()==DBType.MYSQL){
						listj = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.TBSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
					}else{
						listj = HBUtil.getHBSession().createSQLQuery("select to_char(a01.TBSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
					}
					if(listj.size() ==0){
						mes = "";
					}else if(!"".equals((String) listj.get(0))&&(String) listj.get(0)!=null){
						if("null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
							mes += "";
						}else{
							mes = (String) listj.get(0);//查询数据库后的信息
						}
						mes =mes.replace(".", "");
						mes = mes.substring(0, 4);
					}
					mesvalue += mes;*/
				}else if("dqsj".equals(messageE) ){
					String sysDate = DateUtil.getTimeString();
					sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
					mesvalue += sysDate;
				}else if("dqyhm".equals(messageE) ){
					UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
					String loginnname=user.getLoginname();
					//插入数据
					mesvalue += loginnname;
				}else if("jcnx".equals(messageE) ){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
					if("0".equals(listj.get(0))){
						mes = "否";
					}else if("1".equals(listj.get(0))){
						mes = "是";
					}else{
						mes = "";
					}
					mesvalue += mes;
				}else if("mzm".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
					if(listj.size()==0){
						mes = "";
					}else{
						if(!"汉族".equals(listj.get(0))){
							mes = listj.get(0).toString();
						}else{
							mes = "";
						}
					}
					mesvalue += mes;
				}else if("xbm".equals(messageE)){
					String mes = "";
					List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
					if(listj.size()==0){
						mes = "";
					}else{
						if("男".equals(listj.get(0))){
							mes = "";
						}else{
							mes = listj.get(0).toString();
						}
					}
					mesvalue += mes;
				}
				ms = ms.substring(ms.indexOf(",")+1,ms.length());
			} 
//			mesvalue = "";
			k = 0;
		}else{
			System.out.println("no");
		if(messageE.contains("a01")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a01 where a01.a0000 ='"+id+"'").list();
			if(listj==null || listj.size()==0){
				listj.add("");
			}
			
			if("a01.a0192a".equals(messageE)){
				if(listj.size()>0){
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mesvalue += "";
					}else{
						mesvalue +=listj.get(0);
					}
				}
			}else if("a01.a0134".equals(messageE)){
				String mes ="";
				if(listj.size()>0){
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 8){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
						}else if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mesvalue +=(String) listj.get(0)+"\\r\\n";
						}
					}//查询数据库后的信息
					
				}
			}else if("a01.a0107".equals(messageE)){
				String mes ="";
				if(listj.size()>0){
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes = (String) listj.get(0);
						mes = mes.replace(".", "");
						if(mes.length() >= 8){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
						}else if(mes.length() >= 6){
							mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mesvalue +=(String) listj.get(0)+"\\r\\n";
						}
					}//查询数据库后的信息
					
				}
			}else if("a01.a14z101".equals(messageE)){
				mesvalue = (String) listj.get(0);
			}else if("a01.a15z101".equals(messageE)){
				mesvalue = (String) listj.get(0);
			}else if("a01.a0141".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where a01.a0000 ='"+id+"' and cv.code_type='GB4762' and cv.code_value=a01.a0141").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0160".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from a01 a01,code_value cv where cv.code_type='ZB125' and cv.code_value=a01.a0160 and a01.a0000='"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0104a".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.orgid".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a01.orgid,a01.a0000 from a01 a01 ) a01 on a01.orgid = a02.a0201b where a01.a0000 = '"+id+"' group by a02.a0201a").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.a0117a".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
				mesvalue += returnmesvalue(listj1);
			}else if("a01.xgsj".equals(messageE)){
				List listj1 = null;
				if(DBUtil.getDBType()==DBType.MYSQL){
					listj1 = HBUtil.getHBSession().createSQLQuery("select DATE_FORMAT(a01.XGSJ,'%Y%m%d') from a01 where a01.a0000=('"+id+"')").list();
				}else{
					listj1 = HBUtil.getHBSession().createSQLQuery("select to_char(a01.XGSJ,'YYYYMMDD') from a01 where a01.a0000=('"+id+"')").list();
				}
				mesvalue += returnmesvalue2(listj1);
			}else if("a01.a0195".equals(messageE)){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02  join (select a01.a0195 from a01 a01 where a0000= '"+id+"') a on  a.a0195 = a02.a0201b and a02.a0255='1' where a02.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else{
				mesvalue += returnmesvalue(listj);
			}
		}else if(messageE.contains("a02")){
			if("a02.a0201a".equals(messageE)||"a02.a0216a".equals(messageE)){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"' and a02.a0255='1'").list();
				mesvalue += returnmesvalue(listj);
			}else if("a02.a0247".equals(messageE)){
				List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a02 a02 on  a02.a0247 = vce.code_value and vce.code_type = 'ZB122' where a02.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else if("a02.a0219".equals(messageE)){
				List list = HBUtil.getHBSession().createSQLQuery("select a02.a0219 from a02 where a0000 = '"+id+"'  ").list();
				if(list.size()>0){
					for(int a = 0;a<list.size();a++){
						mesvalue = (String)list.get(a);
					}
				}
				if("1".equals(messageE)){
					mesvalue = "是";
				}
				if("2".equals(messageE)){
					mesvalue = "否";
				}
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a02 where a02.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if(messageE.contains("a08")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a08 where a08.a0000 ='"+id+"'").list();
			mesvalue += returnmesvalue(listj);
		}else if(messageE.contains("a29")){
			if(messageE.contains("a29.a2907")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2947")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2949")){
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue2(listj);
			}else if(messageE.contains("a29.a2911")){
				List listj1 = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a29 a29 on  a29.a2911 = vce.code_value and vce.code_type = 'ZB77' where a29.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj1);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a29 a29 where a29.a0000 ='"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
			
		}else   if(messageE.contains("a30")){
			if(messageE.equals("a30.a3001")){
				List listj = HBUtil.getHBSession().createSQLQuery("select code_name from code_value vce join a30 a30 on  a30.a3001 = vce.code_value and vce.code_type = 'ZB78' where a30.a0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else if(messageE.equals("a30.a3007a")){
				List listj = HBUtil.getHBSession().createSQLQuery("select a0201a from a02 a02 join (select a30.a3007a,a30.a0000  from a30 a30 ) a30 on a30.a3007a = a02.a0201b where a30.a0000 = '"+id+"' group by a02.a0201a").list();
				mesvalue += returnmesvalue(listj);
			}else{
				if("a30.a3004".equals(messageE)){
					List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a30 a30 where a30.a0000 ='"+id+"'").list();
					mesvalue += returnmesvalue2(listj);
				}
			}
			
		}else if(messageE.contains("a31")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a31 a31 where a31.a0000 ='"+id+"'").list();
			if("a31.a3104".equals(messageE)){
				mesvalue += returnmesvalue2(listj);
			}else{
				mesvalue += returnmesvalue(listj);
			}
			
		}else if(messageE.contains("a37")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a37 a37 where a37.a0000 ='"+id+"'").list();
			mesvalue += returnmesvalue(listj);
		}else if(messageE.contains("a53")){
			List listj = HBUtil.getHBSession().createSQLQuery("select "+messageE+" from a53 a53 where a53.a0000 ='"+id+"'").list();
			if("a53.a5304".equals(messageE)||"a53.a5315".equals(messageE)){
				String mes = "";
				if(listj.size()>0){
					if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
						mes += "";
					}else{
						mes +=listj.get(0);
					}
				}
				mesvalue += mes;
			}else{
			mesvalue += returnmesvalue(listj);
			}
		}else if("csny".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0107 from a01 a01 where a01.a0000='"+id+"'").list();
			if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes = (String) listj.get(0);
				mes = mes.replace(".", "");
				if(mes.length() >= 6){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6);
				}else{
					mesvalue +=(String) listj.get(0);
				}
			}
		}else if("nl".equals(messageE) ){
			String nowdata = DateUtil.getcurdate();
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a01.a0107,'"+endtime+"') age from a01 where a01.a0000='"+id+"'").list();
			if(listj.size() ==0){
				mes = "";
			}else{
				if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
					mes += "";
				}else{
					mes +=listj.get(0);
				}
			}
			mesvalue += mes;
		}else if("zp".equals(messageE) ){
			String mes = "";
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a57.photopath,a57.photoname from a01,a57 where a01.a0000=a57.a0000 and a01.a0000='"+id+"'").list();
			if(listj.size() ==0){
				mes = "";
			}else{
				System.out.println(listj.size());
				Object[] sz = listj.get(0);
				Object photopath = sz[0];
				Object photoname = sz[1];
				//String path = PhotosUtil.PHOTO_PATH+photopath+photoname;
				//System.out.println("photopath------>"+path);
				//插入数据
				//2017.04.19 yinl 修改图片地址
				String imagepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/pt/"+photopath+photoname;
				mesvalue += "@"+imagepath+"@";
			}
		}else if("ryzt".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0163 from a01 a01 where  a01.a0000='"+id+"'").list();
			if(listj.size()==0){
				listj.add("");
			}
			if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null||"".equals(listj.get(0))){
				mes = "";
				}else{
					mes = (String) listj.get(0);
					if("1".equals(mes)){
						mes="现职人员";
					}else if("2".equals(mes)){
						mes="离退人员";
					}else if("3".equals(mes)){
						mes="调出人员";
					}else if("4".equals(mes)){
						mes="已去世";
					}else{
						mes="其他人员";
					}
				}
			mesvalue += mes;
		}else if("rdsj".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0140 from a01 a01 where a01.A0000 = '"+id+"'").list();
			if(listj.size()==0){
				listj.add("");
			}
			if(listj==null || listj.size()==0 ||"".equals(((String)listj.get(0)))||listj.get(0) == null){
				mes = "";
				}else{
					mes = ((String)listj.get(0)).replace(".", "");
					int length = mes.length();
					if(mes.contains(".")){
						int indexOf = mes.indexOf("(");
						int indexOf2 = mes.indexOf(")");
						String sub = mes.substring(indexOf, length);
						String year = sub.substring(1,5)+".";
						String yue = sub.substring(5,7);
						mes = mes.substring(0, indexOf)+"\\\\r\\\\n"+"("+year+yue+")";
						}else if(length>=6){
							mes = mes.substring(0, 4)+"."+mes.substring(4, 6);
							}else{
							mes = ((String)listj.get(0));
								}
					}
			mesvalue += mes;
		}else if("zgxl".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0801a from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlbyxx".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlsxzy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxlrxsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxlbisj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a08.a0834 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0901a from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwbyxx".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwsxzy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0824 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue(listj);
		}else if("zgxwrxsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0804 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("zgxwbisj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where  a08.A0000 = ('"+id+"') and a0835 = '1' and a08.a0899 = 'true'").list();
			if(listj.size()==0){
				listj.add("");
			}
			mesvalue += returnmesvalue2(listj);
		}else if("xlqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rxsjqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0804").list();
			mesvalue += returnmesvalue2(listj);
		}else if("bysjqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '1' and a08.A0000 = '"+id+"' group by a0807").list();
			mesvalue += returnmesvalue2(listj);
		}else if("xwqrz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("xxjyxql".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xxjyxqw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyql".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0801A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyqw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A != '' and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '1' and a08.A0901A is not null and a08.a0899 = 'true' and a08.A0000 = '"+id+"'").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xlzz".equals(messageE) ){ 
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rxsjzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0804 from a08 a08 where a08.a0899 = 'true' and a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0804").list();
			mesvalue += returnmesvalue2(listj);
		}else if("bysjzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0807 from a08 a08 where a08.a0899 = 'true' and  a08.a0837 = '2' and a08.A0000 = '"+id+"' group by a0807").list();
			mesvalue += returnmesvalue2(listj);
		}else if("xwzz".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("xxjyxzl".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '2' and a08.A0801A != '' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 where a08.A0837 = '2' and a08.A0801A is not null and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("xxjyxzw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.a0814 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyzl".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0801A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 where a08.A0837 = '2' and a08.A0801A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("sxzyzw".equals(messageE) ){
			if(DBUtil.getDBType()==DBType.MYSQL){
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A != '' and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}else{
				List listj = HBUtil.getHBSession().createSQLQuery("select a08.A0824 from a08 a08 where a08.A0837 = '2' and a08.A0901A is not null and A0899 = 'true' and a08.A0000 = ('"+id+"')").list();
				mesvalue += returnmesvalue(listj);
			}
		}else if("qrzxlrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXL from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("qrzxlxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("qrzxwrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXW from a01 a01 where  a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("qrzxwxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.QRZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zzxlrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXL from a01 a01 where a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("zzxixxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXLXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zzxwrb".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXW from a01 a01 where a01.A0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("zzxwxxrb".equals(messageE) ){
			List<Object[]> listj = HBUtil.getHBSession().createSQLQuery("select a01.ZZXWXX from a01 a01 where a01.A0000 = ('"+id+"')").list();
			String mes = "";
			if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
				mes += "";
			}else{
				mes +=listj.get(0);
			}
			mesvalue += mes;
		}else if("zgzwcc".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a.code_name from code_value a where code_type = 'ZB09' and code_value in (select a01.A0148 from a01 a01 where a01.a0000 = '"+id+"') order by a.code_value ").list();
			String mes = "";
			if(listj.size()>0){
				if(listj==null || listj.size()==0 ||"null".equals(listj.get(0))||listj.get(0)==null||"".equals(listj.get(0))){
					mes += "";
				}else{
					mes +=listj.get(0);
				}
			}
			mesvalue += mes;
		}else if("dedp".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3921 and cv.code_type='GB4762'").list();
			mesvalue += returnmesvalue(listj);
		}else if("dsdp".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a01  where  a01.a0000 = ('"+id+"') and cv.code_value=a01.a3927 and cv.code_type='GB4762'").list();
			mesvalue += returnmesvalue(listj);
		}else if("zwcc".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a01.a0149 from a01 a01  where  a01.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue(listj);
		}else if("rzsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0243 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("rgzwccsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a02.a0288 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("mzsj".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a02.A0265 from a02 a02  where  a02.a0000 = ('"+id+"')").list();
			mesvalue += returnmesvalue2(listj);
		}else if("jl".equals(messageE) ){
			ResultSet rs;
			try {
				rs = HBUtil.getHBSession().connection().prepareStatement("select a01.a1701 from a01 where a01.a0000='"+id+"'").executeQuery();
				while(rs.next()){
					String mes = "";
					if("null".equals(rs.getString(1))||rs.getString(1)==null||"".equals(rs.getString(1))){
						mes = "";
					}else{
						mes = formatJL(rs.getString(1),new StringBuffer("")).replace("\n", "\\\\r\\\\n");
					}
					mesvalue += mes;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else if("cw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4761' AND cv.code_value=a36.A3604A ORDER BY a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("xm".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3601 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("csnyjy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a36.A3607 from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			String mes1 = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = ""+"\\r\\n";
					}else{
//						mes = listj.get(i1)+"\\r\\n";
						mes = (String) listj.get(i1);
						mes = mes.replace(".", "");
						if(mes.length() >= 6){
							mes1 = mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
						}else{
							mes1 =(String) listj.get(i1)+"\\r\\n";
						}
					}
					mesvalue += mes1;
					j++;
					if(j==9)
						break;
				}
			}else{
				mesvalue="";
				
			}
		}else if("nljy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select GET_BIRTHDAY(a36.a3607,'"+endtime+"') age from a36 a36 where a36.A0000='"+id+"' order by a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("zzmmjy".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("SELECT cv.code_name FROM a36 a36,code_value cv WHERE a36.A0000='"+id+"' AND cv.code_type='GB4762' AND cv.code_value=a36.A3627 ORDER BY a36.a3604A").list();
			String mes = "";
			int j = 0;
			if(listj.size()!=0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("gzdwjzw".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a3611 from a36 where a0000 = '"+id+"' ").list();
			String mes = "";
			int j = 0;
			if(listj.size()>0){
				for(int i1=0;i1<listj.size();i1++){
					if("null".equals(listj.get(i1))||listj.get(i1)==null){
						mes = "";
					}else{
						mes = listj.get(i1)+"\\r\\n";
					}
					mesvalue += mes;
					j++;
					if(j==9)
						break;
				}
			}
		}else if("tbsjn".equals(messageE) ){
			List listj = HBUtil.getHBSession().createSQLQuery("select a5323 from a53 where a0000 = '"+id+"' ").list();
			mesvalue += returnmesvalue2(listj);
		}else if("dqsj".equals(messageE) ){
			String sysDate = DateUtil.getTimeString();
			sysDate = sysDate.substring(0, 4)+"年"+ sysDate.substring(6, 8)+"月";
			mesvalue = sysDate;
		}else if("dqyhm".equals(messageE) ){
			UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
			String loginnname=user.getLoginname();
			mesvalue += loginnname;
		}else if("jcnx".equals(messageE) ){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select a0197 from a01 where a01.a0000 ='"+id+"'").list();
			if("0".equals(listj.get(0))){
				mes = "否";
			}else if("1".equals(listj.get(0))){
				mes = "是";
			}else{
				mes = "";
			}
			mesvalue += mes;
		}else if("mzm".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB3304' and a.a0000='"+id+"' and cv.code_value=a.a0117").list();
			if(listj.size()==0){
				mes = "";
			}else{
				if(!"汉族".equals(listj.get(0))){
					mes = listj.get(0).toString();
				}else{
					mes = "";
				}
			}
			mesvalue += mes;
		}else if("xbm".equals(messageE)){
			String mes = "";
			List listj = HBUtil.getHBSession().createSQLQuery("select cv.code_name from code_value cv,a01 a where cv.code_type='GB2261' and a.a0000='"+id+"' and cv.code_value=a.a0104").list();
			if(listj.size()==0){
				mes = "";
			}else{
				if("男".equals(listj.get(0))){
					mes = "";
				}else{
					mes = listj.get(0).toString();
				}
			}
			mesvalue += mes;
		}
		}
	
		return mesvalue;
	
	}
	
	public String formatJL(String a1701,StringBuffer originaljl) {
		if(a1701!=null){
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
	
	private void parseJL(String line2, StringBuffer jlsb, boolean isStart){
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
	
	public String returnmesvalue(List list){
		String mesvalue="";
		if(list.size()>0){
			for(int a = 0;a<list.size();a++){
				mesvalue += list.get(a)+"\\r\\n";
			}
			
		}
		return mesvalue;
	}
	public String returnmesvalue2(List list){
		String mesvalue="";
		String mes="";
		if(list.size() ==0){
			mes = "";
		}else{
			for(int a = 0;a<list.size();a++){
				System.out.println(list.get(a));
			if("null".equals(list.get(a))||list.get(a)==null||"".equals(list.get(a))){
				mes = "";
			}else{
				mes = (String) list.get(a);
				mes = mes.replace(".", "");
				if(mes.length() >= 8){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"."+ mes.substring(6, 8)+"\\r\\n";
				}else if(mes.length() >= 6){
					mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
				}else{
					mesvalue +=(String) list.get(0)+"\\r\\n";
				}
					
				}
			}
		}
		return mesvalue;
	}
	public String returnmesvalue3(List list){
		String mesvalue="";
		String mes="";
		if(list.size() ==0){
			mes = "";
		}else{
			for(int a = 0;a<list.size();a++){
				System.out.println(list.get(a));
				if("null".equals(list.get(a))||list.get(a)==null||"".equals(list.get(a))){
					mes = "";
				}else{
					mes = (String) list.get(a);
					mes = mes.replace(".", "");
					 if(mes.length() >= 6){
						mesvalue += mes.substring(0, 4)+"."+mes.substring(4, 6)+"\\r\\n";
					}else{
						mesvalue +=(String) list.get(0)+"\\r\\n";
					}
					
				}
			}
		}
		return mesvalue;
	}
	
	//标准表册用的公用方法
	public List<String> getPersonID(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			sqly = "select  t.a0000 from ( select a01.a0000,a02.a0223,row_number()over(partition by a01.a0000 order by a02.a0223 desc) rn from A01 a01, A02 a02, Code_Value c1, Code_Value c2 where a02.a0000 = a01.a0000 and a02.A0255 = '1' and a01.status = '1' and c1.code_value = a01.a0117 AND c1.code_type = 'GB3304' and c2.code_value = a01.a0104 AND c2.code_type = 'GB2261' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ) t where t.rn = 1";
		}else{
			sqly = "select distinct a02.a0000                     "		
				       +"from A01 a01, A02 a02              "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
			sqly = sqly + " order by a02.a0223 desc";
		}
//		String sqly = "select distinct a02.a0000                     "		
//			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
//			       +"where a02.a0000 = a01.a0000                                     "
//			       +"and a02.A0255 = '1'                                             "
//			       +"and a01.status = '1'                                            "
//			       +"AND c1.code_type = 'GB3304'                                     "
//			       +"and c1.code_value = a01.a0117                                   "
//			       +"AND c2.code_type = 'GB2261'                                     "
//			       +"and c2.code_value = a01.a0104                                   "
//			       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//		sqly = sqly + " order by a0223 desc";
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
	public List<String> getPersonIDWucc(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		if(DBUtil.getDBType()==DBType.ORACLE){
			sqly = "select  t.a0000 from ( select a01.a0000,a02.a0223,row_number()over(partition by a01.a0000 order by a02.a0223 desc) rn from A01 a01, A02 a02 where a02.a0000 = a01.a0000 and a02.a0221 is null and a02.A0255 = '1' and a01.status = '1' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ) t where t.rn = 1";

		}else{
			sqly = "select distinct a02.a0000                     "		
				+"from A01 a01, A02 a02              "
				+"where a02.a0000 = a01.a0000                                     "
				+"and a02.A0255 = '1'                                             "
				+"and a01.status = '1'                                            "
				+"and a02.a0221 = '' and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
			sqly = sqly + " order by a02.a0223 desc";
		}
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
	//标准自定义表册用的公用方法
	public List<String> getPersonIDZ(String id){
		List<String> cunid  = new ArrayList();
		String sqly = "";
		
			sqly = "select  a01.a0000                     "		
				+"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
				+"where a02.a0000 = a01.a0000                                     "
				+"and a02.A0255 = '1'                                             "
				+"and a01.status = '1'                                            "
				+"AND c1.code_type = 'GB3304'                                     "
				+"and c1.code_value = a01.a0117                                   "
				+"AND c2.code_type = 'GB2261'                                     "
				+"and c2.code_value = a01.a0104                                   "
				+"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//			sqly = sqly + " order by a02.a0223 desc";
		System.out.println(sqly);
//		String sqly = "select distinct a02.a0000                     "		
//			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
//			       +"where a02.a0000 = a01.a0000                                     "
//			       +"and a02.A0255 = '1'                                             "
//			       +"and a01.status = '1'                                            "
//			       +"AND c1.code_type = 'GB3304'                                     "
//			       +"and c1.code_value = a01.a0117                                   "
//			       +"AND c2.code_type = 'GB2261'                                     "
//			       +"and c2.code_value = a01.a0104                                   "
//			       +"and a01.a0000 in ("+ id.replace("|", "'").replace("@", ",") + ") ";
//		sqly = sqly + " order by a0223 desc";
		try {
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sqly).executeQuery();
			while(rs.next()){
				cunid.add(rs.getString(1));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cunid;
	}
//标准表册公用方法2
public List<Map<String, String>>  getPersonMess(List cunid, String jgbm,String type,String type2,int o){
	System.out.println(cunid.size());
	List<Map<String,String>> bzmclist = new ArrayList<Map<String,String>>();
	List<Map<String, String>> personlist = new ArrayList<Map<String,String>>();
	List<Object[]> sqllist =  null;
	for (int a = 0 ; a<cunid.size();a++){
		Map<String, String> sqlmap = new HashMap<String, String>();
		String sql ="";
		/*if(DBUtil.getDBType() == DBType.MYSQL){
			sql ="select a01.a0000,                                                  "  
			       +"a01.a0101,                                                      "
			       +"a01.a0117A,                                                     "
			       +"a01.a0111a,                                                     "
			       +"c2.code_name as a0104,                                             "
			       +"a01.a0107,                                                      "
			       +"a02.a0216a,                                                     "
			       +"a01.QRZXL,                                                      "
			       +"a01.ZZXL,                                                       "
			       +"a01.a0144,                                                      "
			       +"a02.a0221 as a0148,                                                "
			       +"a01.a0192,                                                      " 
			       +"a01.a0141,                                                      "
			       +"(select code_name                                               "
                   +"from code_value                                                 "
                   +"where code_type = 'ZB09'                                        "
                   +"and code_value = a02.a0221) as a0149,                              "
			       +"a01.a0134,                                                      "
			       +"a02.a0243,                                                      "
			       +"a02.A0288,                                                      "
			       +"a02.A0201a,                                                     "
			       +"a02.A0201b,                                                     "
			       +"a01.a0180,                                                      "
			       +"a02.a0223,                                                      "
			       +"a01.a0117,                                                      " 
			       +"(select code_name                                               "
				   +"from code_value                                                 "
				   +" where code_type = 'GB4762'                                     "
				   +" and code_value = a01.a0141) as a0141a                             "	                                                      
			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
			       +"where a02.a0000 = a01.a0000                                     "
			       +"and a02.A0255 = '1'                                             "
			       +"and a01.status = '1'                                            "
			       +"AND c1.code_type = 'GB3304'                                     "
			       +"and c1.code_value = a01.a0117                                   "
			       +"AND c2.code_type = 'GB2261'                                     "
			       +"and c2.code_value = a01.a0104                                   "
			       +"and a01.a0000 in ('"+ cunid.get(a) + "') ";
			if("1".equals(type)){
				sql =  sql+"and a02.a0201b = '"+jgbm+"'" ;
			}
			if("5".equals(type2)){
				sql = sql + " order by a0148,length(a0201b),a0201b";
			}else{
				sql = sql + " order by a0148,a0223 asc";
			}
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			if(list.size()>0){
				Map<String, String> sqlmap = new HashMap<String, String>();
				String	mes3 = "";
				String zhiwu = "";
				for(int i=0;i<list.size();i++){
					String zhizhu = (String)list.get(i)[6];
					if(zhizhu != null && !zhizhu.equals("null")){
						zhiwu += (zhizhu.toString().replace("\n", "\\r\\n")+"\\r\\n");
					}else{
						zhiwu += (""+"\\r\\n");
					}
					
//					sqlmap.put("a0243", list.get(i)[14].toString());
					try {
						String	mes30 = (String) list.get(i)[15];//任职时间
						String mes31 = mes30.replace(".", "");
						String mes32 = mes31.substring(0,4)+".";
						String mes33 = mes31.substring(4, 6);
						String mes34 = (mes32 + mes33+"\\r\\n");
						mes3 += mes34;
					} catch (Exception e) {
						mes3 += ""+"\\r\\n";
					}
				}
				sqlmap.put("a0216a", zhiwu);//
				sqlmap.put("a0243", mes3);
				sqlmap.put("a0101", ((String) list.get(0)[1] != null  && !((String) list.get(0)[1]).equals("null"))?(String) list.get(0)[1]:"");
				sqlmap.put("a0117a", ((String) list.get(0)[2] != null  && !((String) list.get(0)[2]).equals("null"))?(String) list.get(0)[2]:"");
				sqlmap.put("a0111a",((String) list.get(0)[3] != null  && !((String) list.get(0)[3]).equals("null"))? list.get(0)[3].toString().replace("\n", "\\r\\n"):"");
				sqlmap.put("a0104", ((String) list.get(0)[4] != null  && !((String) list.get(0)[4]).equals("null"))?(String) list.get(0)[4]:"");
				sqlmap.put("a0107", ((String) list.get(0)[5] != null  && !((String) list.get(0)[5]).equals("null"))?(String) list.get(0)[5]:"");
				sqlmap.put("qrzxl", ((String) list.get(0)[7] != null  && !((String) list.get(0)[7]).equals("null"))?list.get(0)[7].toString().replace("\n", "\\r\\n"):"");
				sqlmap.put("zzxl", ((String) list.get(0)[8] != null  && !((String) list.get(0)[8]).equals("null"))? list.get(0)[8].toString().replace("\n", "\\r\\n"):"");
				sqlmap.put("a0144", ((String) list.get(0)[9] != null  && !((String) list.get(0)[9]).equals("null"))?(String) list.get(0)[9]:"");
				sqlmap.put("a0148", ((String) list.get(0)[10] != null  && !((String) list.get(0)[10]).equals("null"))?(String) list.get(0)[10]:"");
				sqlmap.put("a0192", ((String) list.get(0)[11] != null  && !((String) list.get(0)[11]).equals("null"))? list.get(0)[11].toString().replace("\n", "\\r\\n"):"");
				sqlmap.put("a0141", ((String) list.get(0)[12] != null  && !((String) list.get(0)[12]).equals("null"))?(String) list.get(0)[12]:"");
				sqlmap.put("a0149", ((String) list.get(0)[13] != null  && !((String) list.get(0)[13]).equals("null"))?(String) list.get(0)[13]:"");
				sqlmap.put("a0134", ((String) list.get(0)[14] != null  && !((String) list.get(0)[14]).equals("null"))?(String) list.get(0)[14]:"");
//				sqlmap.put("A0243", (String)list.get(0)[15]);//
				sqlmap.put("A0288", ((String) list.get(0)[16] != null  && !((String) list.get(0)[16]).equals("null"))?(String) list.get(0)[16]:"");
				sqlmap.put("a0201a",((String) list.get(0)[17] != null  && !((String) list.get(0)[17]).equals("null"))?(String) list.get(0)[17]:"");
				sqlmap.put("A0201b",((String) list.get(0)[18] != null  && !((String) list.get(0)[18]).equals("null"))?(String) list.get(0)[18]:"");
				sqlmap.put("a0180",((String) list.get(0)[19] != null  && !((String) list.get(0)[19]).equals("null"))?(String) list.get(0)[19]:"");
//				sqlmap.put("a0223",(String) list.get(0)[19]);
				sqlmap.put("a0141a",((String) list.get(0)[22] != null  && !((String) list.get(0)[22]).equals("null"))?(String) list.get(0)[22]:"");
				personlist.add(sqlmap);
			}
			
		} else{
			sql ="select a01.a0000,                                               "  
			       +"a01.a0101,                                                      "
			       +"c1.code_name a0117,                                             "
			       +"a01.a0111a,                                                     "
			       +"c2.code_name a0104,                                             "
			       +"a01.a0107,                                                      "
			       +"a02.a0216a,                                                     "
			       +"a01.QRZXL,                                                      "
			       +"a01.ZZXL,                                                       "
			       +"a01.A0144,                                                      "
			       +"a02.a0221 a0148,                                                "
			       +"a01.a0192,                                                      "
			       +"(select code_name                                               "
			       +"from code_value                                                 "
			       +"where code_type = 'ZB09'                                        "
			       +"and code_value = a02.a0221) a0149,                              "
			       +"a01.A0134,                                                      "
			       +"a02.A0243,                                                      "
			       +"a02.A0288,                                                      "
			       +"a02.A0201A,                                                     "
			       +"a02.A0201b,                                                     "
			       +"a01.a0180,                                                      "
			       +"a02.a0223,                                                      "
			       +"(select code_name                                               "
				   +"from code_value                                                 "
				   +" where code_type = 'GB4762'                                     "
				   +" and code_value = a01.a0141) a0141a                             "		
			       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2               "
			       +"where a02.a0000 = a01.a0000                                     "
			       +"and a02.A0255 = '1'                                             "
			       +"and a01.status = '1'                                            "
			       +"AND c1.code_type = 'GB3304'                                     "
			       +"and c1.code_value = a01.a0117                                   "
			       +"AND c2.code_type = 'GB2261'                                     "
			       +"and c2.code_value = a01.a0104                                   "
			       +"and a01.a0000 in ('"+ cunid.get(a) + "') ";
			if("1".equals(type)){
				sql =  sql+"and a02.a0201b = '"+jgbm+"'" ;
			}
			
			if("5".equals(type2)){
				sql = sql + " order by a0148,length(a0201b),a0201b";
			}else{
				sql = sql + " order by a0148,a0223 asc";
			}
			List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
			if(list.size()>0){
				Map<String, String> sqlmap = new HashMap<String, String>();
				String	mes3 = "";
				String zhiwu = "";
				String a0201b = "";
				for(int i=0;i<list.size();i++){
					String zhizhu = (String)list.get(i)[6];
					if(zhizhu != null && !zhizhu.equals("null")){
						zhiwu += ((String)list.get(i)[6]+"\\r\\n");
					}else{
						zhiwu += (""+"\\r\\n");
					}
					try {
						String mes30 = (String) list.get(i)[14];//任职时间
						String mes31 = mes30.replace(".", "");
						String mes32 = mes31.substring(0,4)+".";
						String mes33 = mes31.substring(4, 6);
						String mes34 = (mes32 + mes33+"\\r\\n");
						mes3 += mes34;
					} catch (Exception e) {
						mes3 += ""+"\\r\\n";
					}
				}
				sqlmap.put("a0216a", zhiwu);
				sqlmap.put("a0243", mes3);
				sqlmap.put("a0101", ((String) list.get(0)[1] != null  && !((String) list.get(0)[1]).equals("null"))?(String) list.get(0)[1]:"");
				sqlmap.put("a0117", ((String) list.get(0)[2] != null  && !((String) list.get(0)[2]).equals("null"))?(String) list.get(0)[2]:"");
				sqlmap.put("a0111a",((String) list.get(0)[3] != null  && !((String) list.get(0)[3]).equals("null"))?(String) list.get(0)[3]:"");
				sqlmap.put("a0104", ((String) list.get(0)[4] != null  && !((String) list.get(0)[4]).equals("null"))?(String) list.get(0)[4]:"");
				sqlmap.put("a0107", ((String) list.get(0)[5] != null  && !((String) list.get(0)[5]).equals("null"))?(String) list.get(0)[5]:"");
				sqlmap.put("qrzxl", ((String) list.get(0)[7] != null  && !((String) list.get(0)[7]).equals("null"))?(String) list.get(0)[7]:"");
				sqlmap.put("zzxl", ((String) list.get(0)[8] != null  && !((String) list.get(0)[8]).equals("null"))?(String) list.get(0)[8]:"");
				sqlmap.put("a0144", ((String) list.get(0)[9] != null  && !((String) list.get(0)[9]).equals("null"))?(String) list.get(0)[9]:"");
				sqlmap.put("a0148", ((String) list.get(0)[10] != null  && !((String) list.get(0)[10]).equals("null"))?(String) list.get(0)[10]:"");
				sqlmap.put("a0192", ((String) list.get(0)[11] != null  && !((String) list.get(0)[11]).equals("null"))?(String) list.get(0)[11]:"");
				sqlmap.put("a0149", ((String) list.get(0)[12] != null  && !((String) list.get(0)[12]).equals("null"))?(String) list.get(0)[12]:"");
				sqlmap.put("a0134", ((String) list.get(0)[13] != null  && !((String) list.get(0)[13]).equals("null"))?(String) list.get(0)[13]:"");
				sqlmap.put("A0243", ((String) list.get(0)[14] != null  && !((String) list.get(0)[14]).equals("null"))?(String) list.get(0)[14]:"");
				sqlmap.put("A0288", ((String) list.get(0)[15] != null  && !((String) list.get(0)[15]).equals("null"))?(String) list.get(0)[15]:"");
				sqlmap.put("a0201a",((String) list.get(0)[16] != null  && !((String) list.get(0)[16]).equals("null"))?(String) list.get(0)[16]:"");
				sqlmap.put("A0201b",((String) list.get(0)[17] != null  && !((String) list.get(0)[17]).equals("null"))?(String) list.get(0)[17]:"");
				sqlmap.put("a0180",((String) list.get(0)[18] != null  && !((String) list.get(0)[18]).equals("null"))?(String) list.get(0)[18]:"");
//				sqlmap.put("a0223",(String) list.get(0)[19]);
				sqlmap.put("a0141a", ((String) list.get(0)[20] != null  && !((String) list.get(0)[20]).equals("null"))?(String) list.get(0)[20]:"");
				personlist.add(sqlmap);
			}
		}*/
		String sql2 = "";
		String sql3 = "";
		sql = "select count(1) from a57 a57 where  a57.a0000 = '"+ cunid.get(a) + "'";
		sql2 = "select a01.a0117a from a01 a01 where  a01.a0000 = '"+ cunid.get(a) + "'";
		sql3 = "select a01.a0104 from a01 a01 where  a01.a0000 = '"+ cunid.get(a) + "'";
		List sqllist1 = HBUtil.getHBSession().createSQLQuery(sql).list();
		List sqllist2 = HBUtil.getHBSession().createSQLQuery(sql2).list();
		List sqllist3 = HBUtil.getHBSession().createSQLQuery(sql3).list();
		if("0".equals(sqllist1.get(0).toString())){
			if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a02.a0000 a02a0000,                                                        "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c2.code_type = 'GB2261'                                     "
				       +"and c2.code_value = a01.a0104          ";
				if(0==0){
					sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
				}else{
					sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
				}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", sqllist3.get(0).toString());
			}else if((!"".equals(sqllist2.get(0)) &&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0104", "");
				sqlmap.put("a0117", sqllist2.get(0).toString());
			}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", "");
			}else{
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
			}
//			sqlmap.put("photopath", "");
//			sqlmap.put("photoname", "");
//			System.out.println("---->"+sql);
			sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
			/*map.put("photopath", ((String) sqllist.get(0)[0] != null  && !((String) sqllist.get(0)[0].toString()).equals("null"))?(String) sqllist.get(0)[0]:"");
			map.put("photoname", ((String) sqllist.get(0)[1] != null  && !((String) sqllist.get(0)[1].toString()).equals("null"))?(String) sqllist.get(0)[1]:"");*/
		}else{
			if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) && (!"".equals(sqllist3.get(0))&&!"null".equals(sqllist3.get(0))&&sqllist3.get(0)!=null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                    "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104",sqllist3.get(0).toString());
			}else if((!"".equals(sqllist2.get(0))&&!"null".equals(sqllist2.get(0))&&sqllist2.get(0)!=null) && ("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                     "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0104", "");
				sqlmap.put("a0117", sqllist2.get(0).toString());
			}else if(("".equals(sqllist2.get(0))||"null".equals(sqllist2.get(0))||sqllist2.get(0)==null) &&("".equals(sqllist3.get(0))||"null".equals(sqllist3.get(0))||sqllist3.get(0)==null)){
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"a01.a0000 a01a0000,                                             "
				       +"a01.a0111a,                                                     "
				       +"a02.a0000 a02a0000,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                      "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02 "
				       +"where a02.a0000 = a01.a0000                                     "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            ";
				       if(0==0){
							sql +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
				sqlmap.put("a0117", "");
				sqlmap.put("a0104", "");
			}else{
				//------------------
				sql ="select a01.a0000,                                               "  
				       +"a01.a0101,                                                      "
				       +"c1.code_name a0117,                                             "
				       +"a01.a0111a,                                                     "
				       +"c2.code_name a0104,                                             "
				       +"a01.a0107,                                                      "
				       +"a02.a0216a,                                                     "
				       +"a01.QRZXL,                                                      "
				       +"a01.ZZXL,                                                       "
				       +"a01.A0144,                                                      "
				       +"a02.a0221 a0148,                                                      "
				       +"a01.a0192,                                                      "
				       +"(select code_name "
				       +"from code_value "
				       +"where code_type = 'ZB09' "
				       +"and code_value = a02.a0221) a0149,                              "
				       +"a01.A0134,                                                      "
				       +"a02.A0243,                                                      "
				       +"a02.A0288,                                                      "
				       +"a02.A0201A,                                                     "
				       +"a02.A0201b,                                                     "
				       +"a01.a0180,                                                      "
				       +"a02.a0223,                                                       "
				       +"(select code_name                                               "
					   +"from code_value                                                 "
					   +" where code_type = 'GB4762'                                     "
					   +" and code_value = a01.a0141) as a0141a                             "
				       +"from A01 a01, A02 a02,Code_Value c1,Code_Value c2 "
				       +"where a02.a0000 = a01.a0000                                      "
				       +"and a02.A0255 = '1'                                             "
				       +"and a01.status = '1'                                            "
				       +"AND c1.code_type = 'GB3304'                                     "
				       +"and c1.code_value = a01.a0117                                   "
				       +"AND c2.code_type = 'GB2261'                                     ";
				       if(o==0){
							sql  +=  "and a02.a0221 is not null  and a01.a0000 = '"+ cunid.get(a) + "'";
						}else{
							sql +=  "and a01.a0000 = '"+ cunid.get(a) + "'";
						}
			}
			sqllist = HBUtil.getHBSession().createSQLQuery(sql).list();
//			sqlmap.put("photopath", ((String) sqllist.get(0)[20] != null  && !((String) sqllist.get(0)[20].toString()).equals("null"))?(String) sqllist.get(0)[20]:"");
//			sqlmap.put("photoname", ((String) sqllist.get(0)[21] != null  && !((String) sqllist.get(0)[21].toString()).equals("null"))?(String) sqllist.get(0)[21]:"");
		}
		if("1".equals(type)){
			sql =  sql+"and a02.a0201b = '"+jgbm+"'" ;
		}
		
		if("5".equals(type2)){
			sql = sql + " order by a0148,length(a0201b),a0201b";
		}else{
			sql = sql + " order by a0148,a0223 asc";
		}
		List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
		if(list.size()>0){
			String	mes3 = "";
			String zhiwu = "";
			String a0201b = "";
			for(int i=0;i<list.size();i++){
				String zhizhu = (String)list.get(i)[6];
				if(zhizhu != null && !zhizhu.equals("null")){
					zhiwu += ((String)list.get(i)[6]+"\\r\\n");
				}else{
					zhiwu += (""+"\\r\\n");
				}
				try {
					String mes30 = (String) list.get(i)[14];//任职时间
					String mes31 = mes30.replace(".", "");
					String mes32 = mes31.substring(0,4)+".";
					String mes33 = mes31.substring(4, 6);
					String mes34 = (mes32 + mes33+"\\r\\n");
					mes3 += mes34;
				} catch (Exception e) {
					mes3 += ""+"\\r\\n";
				}
			}
			sqlmap.put("a0216a", zhiwu);
			sqlmap.put("a0243", mes3);
			sqlmap.put("a0101", ((String) list.get(0)[1] != null  && !((String) list.get(0)[1]).equals("null"))?(String) list.get(0)[1]:"");
//			sqlmap.put("a0117", ((String) list.get(0)[2] != null  && !((String) list.get(0)[2]).equals("null"))?(String) list.get(0)[2]:"");
			sqlmap.put("a0111a",((String) list.get(0)[3] != null  && !((String) list.get(0)[3]).equals("null"))?(String) list.get(0)[3]:"");
//			sqlmap.put("a0104", ((String) list.get(0)[4] != null  && !((String) list.get(0)[4]).equals("null"))?(String) list.get(0)[4]:"");
			sqlmap.put("a0107", ((String) list.get(0)[5] != null  && !((String) list.get(0)[5]).equals("null"))?(String) list.get(0)[5]:"");
			sqlmap.put("qrzxl", ((String) list.get(0)[7] != null  && !((String) list.get(0)[7]).equals("null"))?(String) list.get(0)[7]:"");
			sqlmap.put("zzxl", ((String) list.get(0)[8] != null  && !((String) list.get(0)[8]).equals("null"))?(String) list.get(0)[8]:"");
			sqlmap.put("a0144", ((String) list.get(0)[9] != null  && !((String) list.get(0)[9]).equals("null"))?(String) list.get(0)[9]:"");
			sqlmap.put("a0148", ((String) list.get(0)[10] != null  && !((String) list.get(0)[10]).equals("null"))?(String) list.get(0)[10]:"");
			sqlmap.put("a0192", ((String) list.get(0)[11] != null  && !((String) list.get(0)[11]).equals("null"))?(String) list.get(0)[11]:"");
			sqlmap.put("a0149", ((String) list.get(0)[12] != null  && !((String) list.get(0)[12]).equals("null"))?(String) list.get(0)[12]:"");
			sqlmap.put("a0134", ((String) list.get(0)[13] != null  && !((String) list.get(0)[13]).equals("null"))?(String) list.get(0)[13]:"");
			sqlmap.put("A0243", ((String) list.get(0)[14] != null  && !((String) list.get(0)[14]).equals("null"))?(String) list.get(0)[14]:"");
			sqlmap.put("A0288", ((String) list.get(0)[15] != null  && !((String) list.get(0)[15]).equals("null"))?(String) list.get(0)[15]:"");
			sqlmap.put("a0201a",((String) list.get(0)[16] != null  && !((String) list.get(0)[16]).equals("null"))?(String) list.get(0)[16]:"");
			sqlmap.put("A0201b",((String) list.get(0)[17] != null  && !((String) list.get(0)[17]).equals("null"))?(String) list.get(0)[17]:"");
			sqlmap.put("a0180",((String) list.get(0)[18] != null  && !((String) list.get(0)[18]).equals("null"))?(String) list.get(0)[18]:"");
//			sqlmap.put("a0223",(String) list.get(0)[19]);
			sqlmap.put("a0141a", ((String) list.get(0)[20] != null  && !((String) list.get(0)[20]).equals("null"))?(String) list.get(0)[20]:"");
			personlist.add(sqlmap);
		}
		bzmclist = personlist;
	}
	return bzmclist;
}
//标准表册公用方法3 特殊项项处理
public Map<String, String> special(Map map ){
	Map<String, String> map1 = map;
	String d1 = "";
	String d2 = "";
	/*if(DBUtil.getDBType() == DBType.MYSQL){ 
		d1 = map1.get("code_name");// 性别
		d2 = map1.get("a0117a");// 民族
	} else {
		d1 = map1.get("a0104");// 性别
		d2 = map1.get("a0117");// 民族
	}*/
	d1 = map1.get("a0104");// 性别
	d2 = map1.get("a0117");// 民族

	String d3 = map1.get("a0141a");// 第一党派
	//------进行女少非的判断_wx
	if((d1 == null ||"".equals(d1)) && (d2 != null && !"".equals(d2))){
		String d11 = "";
		if(d2.equals("汉族")){
			d11 = map1.get("a0101");
		}else{
			d11 = map1.get("a0101")+"\\r\\n"+"(" + d2+ ")";
		}
		map1.put("a0101", d11);
	}else if((d1 != null && !"".equals(d1)) && (d2 == null ||"".equals(d2))){
		String d11 = "";
		if(d1.equals("女")){
			d11 = map1.get("a0101")+"\\r\\n"+"(女)";
		}else{
			d11 = map1.get("a0101");
		}
		map1.put("a0101", d11);
	}else if((d1 == null || "".equals(d1)) && (d2 == null || "".equals(d2))){
		String d11 = map1.get("a0101");
		map1.put("a0101", d11);
	}else{
		
		if (d1 != null && d1.equals("女") && d2.equals("汉族")) {
			String d11 = map1.get("a0101")+"\\r\\n"+"(女)";
			map1.put("a0101", d11);
		}
		if (d1 != null && d1.equals("女") && !d2.equals("汉族")) {
			String d12 = map1.get("a0101")+"\\r\\n"+"(女," + d2+ ")";
			map1.put("a0101", d12);
		}
		if (d1 != null && !d1.equals("女") && !d2.equals("汉族")) {
			String d13 = map1.get("a0101")+"\\r\\n"+"("+ d2+ ")";
			map1.put("a0101", d13);
		}
	}
	if(!"".equals(d3)&&d3!=null){
		if("中共党员".equals(d3)){
			try {
				String	mes60 = map1.get("a0144");//入党时间
				if(mes60 != null ){
				String mes61 = mes60.replace(".", "");
				String mes62 = mes61.substring(0,4)+".";
				String mes63 = mes61.substring(4, 6);
				String	mes6 = mes62 + mes63;
				map1.put("a0144", mes6);
				}
			} catch (Exception e1) {
				String	mes60 = map1.get("a0144");//入党时间
				map1.put("a0144", mes60);
				
			}
		}else{
			map1.put("a0144", map1.get("a0141a"));
		}
	}else{
		map1.put("a0144", "");
	}
	
	try {
		String	mes40 = map1.get("a0107");//生日
		String mes41 = mes40.replace(".", "");
		String mes42 = mes41.substring(0,4)+".";
		String mes43 = mes41.substring(4, 6);
		String	mes4 = mes42 + mes43;
		map1.put("a0107", mes4);
	} catch (Exception e) {
		String	mes40 = map1.get("a0107");//生日
		
		map1.put("a0107", mes40);
	}
	try {
		String	mes60 = map1.get("a0144");//入党时间
		String mes61 = mes60.replace(".", "");
		String mes62 = mes61.substring(0,4)+".";
		String mes63 = mes61.substring(4, 6);
		String	mes6 = mes62 + mes63;
		map1.put("a0144", mes6);
	} catch (Exception e) {
		String	mes60 = map1.get("a0144");//入党时间
		map1.put("a0144", mes60);
	}
	try {
		String	mes70 = map1.get("a0134");//参加工作时间
		String mes71 = mes70.replace(".", "");
		String mes72 = mes71.substring(0,4)+".";
		String mes73 = mes71.substring(4, 6);
		String	mes7 = mes72 + mes73;
		map1.put("a0134", mes7);
	} catch (Exception e) {
		String	mes70 = map1.get("a0134");//参加工作时间
		map1.put("a0134", mes70);
	}
	return map1;
}

}
	
