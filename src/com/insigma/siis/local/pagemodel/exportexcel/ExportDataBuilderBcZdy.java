package com.insigma.siis.local.pagemodel.exportexcel;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;

import sun.misc.BASE64Encoder;

import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Rydjb;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.search.PreSubmitPageModel;
import com.picCut.teetaa.util.ImageHepler;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author lixy ��ᵼ�����ݲ�ѯ�����
 */
public class ExportDataBuilderBcZdy {

	private Configuration configuration = null;
	
	

	public ExportDataBuilderBcZdy() {
		configuration = new Configuration();
		configuration.setDefaultEncoding("UTF-8");
	}

	
	
	/**
	 * @$comment ����Ҫ����ģ�������
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getTempData(String tempType,
			HttpServletRequest request,HttpServletResponse response) {
		Map<String, Object> tmpData = null;
		if("6".equals(tempType) || "8".equals(tempType)) {
			tmpData = getMcList1(request,tempType, response);
		}else {
			tmpData = getMcList(request,response, tempType);
		}
		return tmpData;
	}
	

	/**
	 * @$comment ��ȡ����Ա��������Ϣ
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getMcList1(HttpServletRequest request,
			String tempType, HttpServletResponse response) {
		Map<String, Object> tmpData = null;
		String dw = request.getParameter("dw");
		String dwbm = request.getParameter("dwbm");
		String checkList = request.getParameter("List");
		request.getSession().setAttribute("personids", checkList);
		String viewType=request.getParameter("viewType")==null?request.getParameter("viewType2"):request.getParameter("viewType");//����չ�ַ�ʽ
		request.getSession().setAttribute("yviewType", viewType);
		try {
			ServletOutputStream out =response.getOutputStream();
	        out.println("<script language='javascript'>parent.parent.showtemplate();</script>");
		} catch (Exception e) {
			
		}


		return tmpData;
	}
	
	
	
	public Map<String, Object> getMcList(HttpServletRequest request,HttpServletResponse response,
			String tempType) {
		Map<String, Object> tmpData = null;
		String dw = request.getParameter("dw");
		String dwbm = request.getParameter("dwbm");
		String checkList = request.getParameter("List");
		request.getSession().setAttribute("checkList", checkList);
		String viewType=request.getParameter("viewType")==null?request.getParameter("viewType3"):request.getParameter("viewType");//����չ�ַ�ʽ
		//=====================================================
		
		/*if ("1".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			try {
				ServletOutputStream out =response.getOutputStream();
		        
		        out.println("<script language='javascript'>parent.parent.showtemplate();</script>");	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}*/
		
		
		//======================================================
		
		
		
		
		List<Map<String, String>> list = null;
		String sql = "";
		if(DBUtil.getDBType() == DBType.MYSQL){
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
			       +"and a01.a0000 in ("+ checkList.replace("|", "'").replace("@", ",") + ") ";
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
			       +"and a01.a0000 in ("+ checkList.replace("|", "'").replace("@", ",") + ") ";
		}
		System.out.println(sql);
		// �������޻���������
		if ("1".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			sql = sql + " order by a0223 desc";
//			list = getListBySqlforMc(sql, true, tempType);
		}// �������л���������
		else if ("2".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			sql = sql + " order by a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			// ����������
		} else if ("3".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			sql = sql + "  order by length(a0201b),a0201b ,a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			// ��ְ���η���
		} else if ("4".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			sql = sql + " order by a0148,a0223 desc";
			list = getListBySqlforMc(sql, true, tempType);
			// ��ְ���η��飬����������
		} else if ("5".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
			sql = sql + " order by a0148,length(a0201b),a0201b";
			list = getListBySqlforMc(sql, true, tempType);
		} else if ("11".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
		} else if ("12".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
		} else if ("13".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
		} else if ("14".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
		} else if ("15".equals(viewType)) {
			request.getSession().setAttribute("viewType", viewType);
		}
		
		request.getSession().setAttribute("bzbclist", list);
		
		try {
			ServletOutputStream out =response.getOutputStream();
	        
	        out.println("<script language='javascript'>parent.parent.showtemplate();</script>");	
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tmpData;
	}
	
	
	
	

	/**
	 * @$comment �Լ��������ݽ��з���
	 * @param list
	 *            ��Ҫ������б�,groupCol ���ݷ������
	 * @return List<Map<String, Object>> Map�е�tag Ϊ�������ƣ�datas��������
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, Object>> groupListByCol(List<Map<String, String>> list, String groupCol) {
		List<Map<String, Object>> groupList = new ArrayList<Map<String, Object>>();
		String tag = null;// �����ֶ�ֵ(ͬ���־�ֶ�ֵ)

		List<Map<String, String>> groupsList = null;// ����list
		List<Map<String, String>> nullList = new ArrayList<Map<String, String>>();// �����ֶ�Ϊ�յ����ݷ���
		for (Map<String, String> gm : list) {
			if (gm.get(groupCol) != null) {
				if (!gm.get(groupCol).toString().equals(tag)) {
					// ���ͬ���־�ֶ�ֵ��Ϊ�գ���������շ����б�
					if (tag != null) {
						Map<String, Object> fm = new HashMap<String, Object>();
						fm.put("tag", tag);
						fm.put("datas", groupsList);
						groupList.add(fm);
					}
					groupsList = new ArrayList<Map<String, String>>();
				}

				groupsList.add(gm);
				// ���÷����־ֵ
				tag = gm.get(groupCol).toString();

			} else {
				// ��ӷ����ֶ�Ϊ�յ��б�
				nullList.add(gm);
			}

		}

		// �����һ����������б�
		Map<String, Object> fm = new HashMap<String, Object>();
		fm.put("tag", tag);
		fm.put("datas", groupsList);
		groupList.add(fm);

		if (nullList.size() > 0) {
			// ��ӷ����ֶ�Ϊ�յ��б�������б�
			Map<String, Object> um = new HashMap<String, Object>();
			um.put("tag", "�޷�����");
			um.put("datas", nullList);
			groupList.add(um);
		}

		return groupList;
	}

    public List<Map<String, Object>> groupListByCol2(List<Map<String, String>> list,String groupCol,List list2){
    	String tag=null;//�����ֶ�ֵ(ͬ���־�ֶ�ֵ)
    	List<Map<String, Object>> groupsList=new ArrayList<Map<String,Object>>();//����list
    	if(list2.size()>0){
    		for(int i = 0;i<list2.size();i++){//ѭ������
    			List<Map<String, String>> groupList=new ArrayList<Map<String,String>>();
    			tag = list2.get(i).toString();
    			for(int j=list.size()-1;j>=0;j--){//ѭ����Ա��Ϣ
    				Map<String, String> gm = list.get(j);
    				if(gm.get(groupCol).toString().equals(tag)){
    					groupList.add(gm);
    					list.remove(j);//ͳ�ƹ�����Ա�Ƴ���list���Ƴ�
    				}
    			}
    			Map<String, Object> fm=new HashMap<String, Object>();
    			fm.put("tag", tag);
    			fm.put("datas",groupList);
    			groupsList.add(fm);
    		}
    		if(list.size()>0){
    			Map<String, Object> fm1=new HashMap<String, Object>();
    			fm1.put("tag", "��ְ����");
    			fm1.put("datas", list);
    			groupsList.add(fm1);
    		}
    	}else{
    		Map<String, Object> fm2=new HashMap<String, Object>();
    		fm2.put("tag", "��ְ����");
    		fm2.put("datas", list);
    		groupsList.add(fm2);
    	}
    	
    	return groupsList;
    }

	/**
	 * @$comment ��ȡ����Ա��������Ϣ
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getBabCollectionList(HttpServletRequest request) {
		Map<String, Object> tmpData = new HashMap<String, Object>();
		String dw = (String) request.getParameter("dw");
		String gbs = (String) request.getParameter("gbs");
		String sys = (String) request.getParameter("sys");
		String djs = (String) request.getParameter("djs");
		String zhs = (String) request.getParameter("zhs");
		String djgridString = (String) request.getParameter("djgridString");
		String zhgridString = (String) request.getParameter("zhgridString");

		tmpData.put("dw", dw);
		tmpData.put("gbs", gbs);
		tmpData.put("sys", sys);
		tmpData.put("djs", djs);
		tmpData.put("zhs", zhs);
		String sql = null;
		if (DBUtil.getDBType() == DBType.ORACLE)
			sql = "select rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180 from A01 a01";
		else if (DBUtil.getDBType() == DBType.MYSQL)
			sql = "select (@rowNum:=@rowNum+1) as rownum,a0101,(select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) a0104,a0107,a0192,(select code_name from code_value where code_type='ZB134' and code_value.code_value=a01.a0120) a0120,a01.a0180 from A01 a01,(Select (@rowNum :=0) ) b ";
		if (djgridString != null && !"".equals(djgridString)) {
			djgridString = djgridString.replace("|", "'").replace("@", ",");
			String dsql = sql + " where a0000 in (" + djgridString + ")";
			List<Map<String, String>> list = getListBySql(dsql, false);
			tmpData.put("djgrid", list);
		} else {
			tmpData.put("zhgrid", new ArrayList<Map<String, String>>());
		}

		if (zhgridString != null && !"".equals(zhgridString)) {
			zhgridString = zhgridString.replace("|", "'").replace("@", ",");
			String zsql = sql + " where a0000 in (" + zhgridString + ")";
			List<Map<String, String>> list = getListBySql(zsql, false);
			tmpData.put("zhgrid", list);
		} else {
			tmpData.put("zhgrid", new ArrayList<Map<String, String>>());
		}
		if (djgridString != null && !"".equals(djgridString)) {
			// ��¼��־
			String[] checkLists = djgridString.replace("'", "").split(",");
			HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < checkLists.length; i++) {
				A01 a01 = (A01) sess.get(A01.class, checkLists[i]);
				A01 a01log = new A01();
				try {
					new LogUtil().createLog("54", "A01", a01.getA0000(),
							a01.getA0101(), "����������",
							new Map2Temp().getLogInfo(new A01(), a01log));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (zhgridString != null && !"".equals(zhgridString)) {
			// ��¼��־
			String[] checkLists = zhgridString.replace("'", "").split(",");
			HBSession sess = HBUtil.getHBSession();
			for (int i = 0; i < checkLists.length; i++) {
				A01 a01 = (A01) sess.get(A01.class, checkLists[i]);
				A01 a01log = new A01();
				try {
					new LogUtil().createLog("54", "A01", a01.getA0000(),
							a01.getA0101(), "����������",
							new Map2Temp().getLogInfo(new A01(), a01log));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return tmpData;
	}

	/**
	 * @$comment ��ȡ�����Ϣ
	 * @param a0000
	 *            ��ԱiD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getDjbMap(String a0000) {
		Map<String, Object> tmpData = null;
		List list = getListBySql("select * from Rydjb where a0000='" + a0000
				+ "'", true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData;
	}
	
	/**
	 * @$comment ��ȡ�����Ϣ
	 * @param a0000
	 *            ��ԱiD,
	 * @return Map<String, Object>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getDjbMap3(String a0000) {
		Map<String, Object> tmpData = null;
		List list = getListBySql("select * from Rydjb where a0000='" + a0000
				+ "'", true);
		if (list != null && list.size() > 0)
			tmpData = (Map<String, Object>) list.get(0);
		return tmpData;
	}

	/**
	 * @$comment ��ȡ�����Ϣ
	 * @param a0000
	 *            ��ԱiD,tempType ģ������
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getDjbCollectionList(HttpServletRequest request) {
		Map<String, Object> tmpData = null;

		// String a0000=(String)request.getParameter("a0000");
		String a0000 = (String) request.getAttribute("a0000");
		String djhsrzw = (String) request.getParameter("djhsrzw");
		String djhsdjb = (String) request.getParameter("djhsdjb");
		String szjgyj = (String) request.getParameter("szjgyj");
		String shjgyj = (String) request.getParameter("shjgyj");
		String spjgyj = (String) request.getParameter("spjgyj");
		String bz = (String) request.getParameter("bz");
		DBType cueDBType = DBUtil.getDBType();
		String sql = "";

		if (cueDBType == DBType.MYSQL) {
			sql = "select a01.A0101,a01.A0184,"
					+ // --����
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --�Ա�
					"  a01.A0107,"
					+ // --��������
					// "  a01.Age,"+//����
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --����
					// "  (select code_name3"+
					// "  from code_value"+
					// "  where code_type='ZB01'"+
					// "  and code_value = a01.a0111) A0111,"+
					"  a01.A0111a,"
					+ // --����
					"  (select code_name from code_value where code_type = 'ZB01' and code_value.code_value = a01.A0114) A0114,"
					+ // --������
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --����ְ����
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + 
					// --������ò
					"  a01.A0144," + // --�뵳ʱ��
					"  a01.A0134," + // --�μӹ���ʱ��
					" a01.A0128,  " + // --����״��
					" a01.A0196,  " + // --רҵ����ְ��
					" a01.QRZXL,  " + // --���ȫ����ѧ��
					" a01.QRZXW,  " + // --���ȫ����ѧλ
					" a01.QRZXLXX," + // --ԺУϵרҵ�����ȫ����ѧ����
					" a01.ZZXL,  " + // --�����ְѧ��
					" a01.ZZXW,  " + // --�����ְѧλ
					" a01.ZZXLXX," + // --ԺУϵרҵ�������ְѧ����
					" a01.A0192," + // --������λ��ְ��
					" a01.NRZW," + // -- ����ְ��
					" a01.NMZW," + // -- ����ְ��
					" a01.A1701," + // --����
					" a01.A14z101," + // --�������
					" a01.A15Z101," + // -- �Ը�����ȿ��˽���ĺ�������
					" a01.RMLY" + // --��������
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		} else {
			sql = "select a01.A0101,a01.A0184,"
					+ // --����
					"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
					+ // --�Ա�
					"  a01.A0107,"
					+ // --��������
					// "  a01.Age,"+//����
					"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
					+ // --����
					// "  (select code_name3"+
					// "  from code_value"+
					// "  where code_type='ZB01'"+
					// "  and code_value = a01.a0111) A0111,"+
					"  a01.A0111a,"
					+ // --����
					"  (select code_name from code_value where code_type = 'ZB01' and code_value.code_value = a01.A0114) A0114,"
					+ // --������
					"  (select code_name from code_value where code_type='ZB09' and code_value.code_value=a01.a0148) A0148,"
					+ // --����ְ����
					"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141," + // --������ò
					"  a01.A0144," + // --�뵳ʱ��
					"  a01.A0134," + // --�μӹ���ʱ��
					" a01.A0128,  " + // --����״��
					" a01.A0196,  " + // --רҵ����ְ��
					" a01.QRZXL,  " + // --���ȫ����ѧ��
					" a01.QRZXW,  " + // --���ȫ����ѧλ
					" a01.QRZXLXX," + // --ԺУϵרҵ�����ȫ����ѧ����
					" a01.ZZXL,  " + // --�����ְѧ��
					" a01.ZZXW,  " + // --�����ְѧλ
					" a01.ZZXLXX," + // --ԺУϵרҵ�������ְѧ����
					" a01.A0192," + // --������λ��ְ��
					" a01.NRZW," + // -- ����ְ��
					" a01.NMZW," + // -- ����ְ��
					" to_char(a01.A1701) as a1701," + // --����
					" a01.A14z101," + // --�������
					" a01.A15Z101," + // -- �Ը�����ȿ��˽���ĺ�������
					" a01.RMLY" + // --��������
					" from A01 a01 where a01.a0000='" + a0000 + "'";
		}

		Map<String, Object> tmp = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (tmp != null) {
			tmpData = tmp;

			// �����д��Ϣ
			tmpData.put("djhsrzw", djhsrzw);
			tmpData.put("djhsdjb", djhsdjb);
			tmpData.put("szjgyj", szjgyj);
			tmpData.put("shjgyj", shjgyj);
			tmpData.put("spjgyj", spjgyj);
			tmpData.put("bz", bz);

		}

		Rydjb rydjb = new Rydjb();
		try {
			com.insigma.odin.framework.util.BeanUtil.copyHashMap(
					(HashMap) tmpData, rydjb);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		rydjb.setA0000(a0000);
		HBUtil.getHBSession().saveOrUpdate(rydjb);
		HBUtil.getHBSession().flush();

		return tmpData;
	}

	/**
	 * @$comment ��ȡ���ģ��
	 * @param tempType
	 *            ģ������
	 * @return Template
	 * @throws IOException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Template getTemplate(String tempType, ServletContext event,
			String viewType) throws IOException {
		Template template = null;
		String tempName = null;

		if ("1".equals(tempType))
			tempName = "gwydjb_2.xml";
		if ("2".equals(tempType))
			tempName = "czgwyfgljgzzrydjb_1.xml";
		if ("3".equals(tempType))
			tempName = "gwybadjb_1.xml";
		if ("4".equals(tempType))
			tempName = "gwybadjb2_1.xml";
		if ("5".equals(tempType)) {
			if ("1".equals(viewType) || "3".equals(viewType)
					|| "4".equals(viewType)) {
				tempName = "bzmc_3.xml";
			} else {
				tempName = "bzmc_2.xml";
			}
		}
		if ("6".equals(tempType))
			tempName = "zpmc.xml";
		if ("7".equals(tempType))
			tempName = "gwydjbahzb.xml";
		if ("8".equals(tempType))
			tempName = "zpmc_2.xml";
		if ("11".equals(tempType)) {
			tempName = "cbdmb_1.xml";
		}
		if ("12".equals(tempType)) {
			tempName = "cbdmb_up.xml";
		}
		if ("100".equals(tempType)) {
			tempName = "cbdexp.xml";
		}
		if ("13".equals(tempType)) {
			tempName = "gwybadjb_2.xml";
		}
		if ("14".equals(tempType)) {
			tempName = "gwybadjb2_2.xml";
		}
		if ("101".equals(tempType)) {
			tempName = "cmxx.xml";
		}
		// ��ʲô�ط�����freemarkerģ���ļ�
		configuration.setDirectoryForTemplateLoading(new File(event
				.getRealPath("pages/exportexcel/")));

		// ���ö����װ��
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		// �����쳣������
		configuration
				.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		// ����Template����
		template = configuration.getTemplate(tempName);

		return template;
	}
	
	/**
	 * @$comment ��ȡ���ģ��
	 * @param tempType
	 *            ģ������
	 * @return Template
	 * @throws IOException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Template getTemplate3(String tempType, ServletContext event,
			String viewType) throws IOException {
		Template template = null;
		String tempName = null;

		if ("1".equals(tempType) || "11".equals(tempType))
			tempName = "gwydjb_2.xml";
		if ("2".equals(tempType) || "12".equals(tempType))
			tempName = "czgwyfgljgzzrydjb_1.xml";
		if ("3".equals(tempType) || "13".equals(tempType))
			tempName = "gwybadjb_1.xml";
		if ("4".equals(tempType) || "14".equals(tempType))
			tempName = "gwybadjb2_1.xml";
		if ("5".equals(tempType) || "15".equals(tempType)) {
			if ("1".equals(viewType) || "3".equals(viewType)
					|| "4".equals(viewType) || "11".equals(tempType) || "13".equals(tempType) || "14".equals(tempType)) {
				tempName = "bzmc_3.xml";
			} else {
				tempName = "bzmc_2.xml";
			}
		}
		if ("6".equals(tempType))
			tempName = "zpmc.xml";
		if ("7".equals(tempType))
			tempName = "gwydjbahzb.xml";
		if ("8".equals(tempType))
			tempName = "zpmc_2.xml";
		if ("11".equals(tempType)) {
			tempName = "cbdmb_1.xml";
		}
		if ("12".equals(tempType)) {
			tempName = "cbdmb_up.xml";
		}
		if ("100".equals(tempType)) {
			tempName = "cbdexp.xml";
		}
		if ("13".equals(tempType)) {
			tempName = "gwybadjb_2.xml";
		}
		if ("14".equals(tempType)) {
			tempName = "gwybadjb2_2.xml";
		}
		if ("101".equals(tempType)) {
			tempName = "cmxx.xml";
		}
		// ��ʲô�ط�����freemarkerģ���ļ�
		configuration.setDirectoryForTemplateLoading(new File(event
				.getRealPath("pages/exportexcel/")));

		// ���ö����װ��
		configuration.setObjectWrapper(new DefaultObjectWrapper());
		// �����쳣������
		configuration
				.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		// ����Template����
		template = configuration.getTemplate(tempName);

		return template;
	}

	/**
	 * @$comment ������ԱId��ѯ��Ա��Ƭ��Ϣ
	 * @param a0000
	 * @return String
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */

	public String getImageStr(String a0000) throws RadowException {
		// �����ݿ��ȡ�ļ�������

		BASE64Encoder encoder = new BASE64Encoder();
		// ѹ����Ƭ
		try {

			A57 a57 = (A57) HBUtil.getHBSession().get(A57.class, a0000);
			if (a57 != null) {
				String imageurl = PhotosUtil.PHOTO_PATH + a57.getPhotopath()
						+ a57.getPhotoname();
				File pfile = new File(imageurl);
				byte[] data = null;
				if (pfile.isFile()) {
					if (pfile.length() > 80000) {
						Rectangle rec = new Rectangle(0, 0, 272, 340);
						data = ImageHepler.cut(imageurl, "", 0, 0, rec, a0000,
								null);
					} else {
						data = PhotosUtil.getPhotoData(a57);
					}

					if (data != null) {

						return encoder.encode(data);
					}
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encoder.encode(new byte[1]);
	}

	/**
	 * @$comment ���ز�ѯ��Ϣ��
	 * @param
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, String>> getListByB0111(String a0201b,
			String isContain) {
		List<Map<String, String>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			String sql = "";
			if ("1".equals(isContain)) {
				sql = "select a01.a0000,"+
			               "a0101,"+
			               "(select code_name "+
			               " from code_value "+
			               "where code_type = 'GB2261' "+
			               "and code_value.code_value = a01.a0104) a0104, "+
			               "(select code_name "+
			               "from code_value "+
			               "where code_type = 'ZB134' "+
			               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						"a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b like '"+a0201b+"%'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			} else {
				sql = "select a01.a0000,"+
               "a0101,"+
               "(select code_name "+
               " from code_value "+
               "where code_type = 'GB2261' "+
               "and code_value.code_value = a01.a0104) a0104, "+
               "(select code_name "+
               "from code_value "+
               "where code_type = 'ZB134' "+
               "and code_value.code_value = a01.a0120) a0120,a01.a0180,a0192,a0107,a0148,status "+
						"  from (select a01.a0000,                                                            "+
						"               a0101,                                                                "+
						" a0104, "+
						"a0120, "+
						"               a01.a0180,                                                            "+
						"               a0192,                                                                "+
						"               a0107,                                                                "+
						"               a0148,                                                                "+
						"               status                                                                "+
						"          from A01 a01,a02 a02,COMPETENCE_USERDEPT cu  "+
						"         where 1 = 1                                                                 "+
						"           and a01.a0000 = a02.a0000                                                 "+
						"           and a02.a0201b = '"+a0201b+"'                                            "+
						"           and a02.a0255 = '1'                                                       "+
						"           and a01.status = '1'                                                      "+
						"           and a02.A0201B=cu.b0111                                                   "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"'                        "+
						"           group by a01.a0000,a0101,a0104,a0120,a01.a0180,a0192,a0107,a0148,status) a01 "+
						" where 1 = 1                                                                         "+
						"   and not exists                                                                    "+
						" (select 1                                                                           "+
						"          from COMPETENCE_USERPERSON cu                                              "+
						"         where cu.a0000 = a01.a0000                                                  "+
						"           and cu.userid = '"+SysManagerUtils.getUserId()+"')                       "+
						" order by a01.a0148,                                                                 "+
						"          (select max(a0225)                                                         "+
						"             from b01, a02                                                           "+
						"            where a01.a0000 = a02.a0000                                              "+
						"              and a02.a0201b = b01.b0111                                             "+
						"              and a02.a0255 = '1')                                                   ";
			}

			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, String>>();
				while (iterator.hasNext()) {

					Map<String, String> tmp = (Map<String, String>) iterator
							.next();
					list.add(tmp);

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * @$comment ���ݴ���SQL���ز�ѯ��Ϣ��
	 * @param SQL
	 *            ,isHasPic�Ƿ��ȡ��Ƭ
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public List<Map<String, String>> getListBySql(String sql, Boolean isHasPic) {
		List<Map<String, String>> list = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql);
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {
				list = new ArrayList<Map<String, String>>();
				while (iterator.hasNext()) {

					Map<String, String> tmp = (Map<String, String>) iterator.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * @$comment ���ݴ���SQL���ز�ѯ��Ϣ����ƴ��ָ���ֶ�
	 * @param SQL
	 *            ,isHasPic�Ƿ��ȡ��Ƭ
	 * @return List<Map<String, String>>
	 * @throws AppException
	 * @throws
	 * @author wuh
	 */
	public List<Map<String, String>> getListBySqlforMc(String sql,
			Boolean isHasPic, String tempType) {
		List<Map<String, String>> list = new ArrayList();
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();
			query.setConnection(session.connection());
			query.setQuerySQL(sql);
			ResultSet rs = HBUtil.getHBSession().connection().prepareStatement(sql).executeQuery();
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if ("8".equals(tempType)) {
				if (iterator.hasNext()) {
					list = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {

						Map<String, String> tmp = (Map<String, String>) iterator.next();
						// �����Ҫ��Ƭ��������Աid��Ϊ�����ȡ��Ƭ����
						if (isHasPic && !"".equals(tmp.get("a0000"))
								&& tmp.get("a0000") != null) {
							//tmp.put("image", getImageStr(tmp.get("a0000")));
						}
						tmp.put("a0111a",
								getA0111aStr(tmp.get("a0111a"),
										tmp.get("a0107"), tmp.get("qrzxl"),
										tmp.get("a0144"), tmp.get("a0134"),
										tmp.get("a0243")));
						tmp.put("a0107",
								getA0107aStr(tmp.get("a0107"),
										tmp.get("qrzxl"), tmp.get("a0144"),
										tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("qrzxl",
								getQrzxlStr(tmp.get("qrzxl"), tmp.get("a0144"),
										tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("a0144",
								getA0144Str(tmp.get("a0144"), tmp.get("a0134"),
										tmp.get("a0243")));
						tmp.put("a0134",
								getA0134Str(tmp.get("a0134"), tmp.get("a0243")));
						tmp.put("a0243", getA0243Str(tmp.get("a0243")));
						list.add(tmp);
					}
				}
			} else {
				if (iterator.hasNext()) {
					list = new ArrayList<Map<String, String>>();
					while (iterator.hasNext()) {

						Map<String, String> tmp = (Map<String, String>) iterator
								.next();
						// �����Ҫ��Ƭ��������Աid��Ϊ�����ȡ��Ƭ����
						if (isHasPic && !"".equals(tmp.get("a0000"))
								&& tmp.get("a0000") != null) {
							//tmp.put("image", getImageStr(tmp.get("a0000")));
						}
						list.add(tmp);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return list;
	}

	/**
	 * ƴ�Ӽ�����Ϣ
	 * 
	 */
	public String getA0111aStr(String a0111a, String a0107, String qrzxl,
			String a0144, String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0111a)) {
			if (StringUtil.isEmpty(a0107) && StringUtil.isEmpty(qrzxl)
					&& StringUtil.isEmpty(a0144) && StringUtil.isEmpty(a0134)
					&& StringUtil.isEmpty(a0243)) {
				a0111a = a0111a + "�ˡ�";
			} else {
				a0111a = a0111a + "��,";
			}
		} else {
			a0111a = "";
		}
		return a0111a;
	}

	/**
	 * ƴ�ӳ���������Ϣ
	 * 
	 */
	public String getA0107aStr(String a0107, String qrzxl, String a0144,
			String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0107)) {
			if (StringUtil.isEmpty(qrzxl) && StringUtil.isEmpty(a0144)
					&& StringUtil.isEmpty(a0134) && StringUtil.isEmpty(a0243)) {
				a0107 = a0107.substring(0, 4) + "." + a0107.substring(4, 6)
						+ "����";
			} else {
				a0107 = a0107.substring(0, 4) + "." + a0107.substring(4, 6)
						+ "����";
			}
		} else {
			a0107 = "";
		}
		return a0107;
	}

	/**
	 * ƴ��ѧ����Ϣ
	 * 
	 */
	public String getQrzxlStr(String qrzxl, String a0144, String a0134,
			String a0243) {
		if (!StringUtil.isEmpty(qrzxl)) {
			if (StringUtil.isEmpty(a0144) && StringUtil.isEmpty(a0134)
					&& StringUtil.isEmpty(a0243)) {
				qrzxl = qrzxl + "��";
			} else {
				qrzxl = qrzxl + "��";
			}
		} else {
			qrzxl = "";
		}
		return qrzxl;
	}

	/**
	 * ƴ���뵳������Ϣ
	 * 
	 */
	public String getA0144Str(String a0144, String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0144)) {
			if (StringUtil.isEmpty(a0134) && StringUtil.isEmpty(a0243)) {
				a0144 = a0144.substring(0, 4) + "." + a0144.substring(4, 6)
						+ "�뵳��";
			} else {
				a0144 = a0144.substring(0, 4) + "." + a0144.substring(4, 6)
						+ "�뵳��";
			}
		} else {
			a0144 = "";
		}
		return a0144;
	}

	/**
	 * ƴ�Ӳμӹ���������Ϣ
	 * 
	 */
	public String getA0134Str(String a0134, String a0243) {
		if (!StringUtil.isEmpty(a0134)) {
			if (StringUtil.isEmpty(a0243)) {
				a0134 = a0134.substring(0, 4) + "." + a0134.substring(4, 6)
						+ "�μӹ�����";
			} else {
				a0134 = a0134.substring(0, 4) + "." + a0134.substring(4, 6)
						+ "�μӹ�����";
			}
		} else {
			a0134 = "";
		}
		return a0134;
	}

	/**
	 * ƴ������ְ������Ϣ
	 * 
	 */
	public String getA0243Str(String a0243) {
		if (!StringUtil.isEmpty(a0243)) {
			a0243 = a0243.substring(0, 4) + "." + a0243.substring(4, 6)
					+ "����ְ��";

		} else {
			a0243 = "";
		}
		return a0243;
	}

	/**
	 * @$comment ��ȡ�ʱ�����Ϣ
	 * @param request
	 * @return List<Map<String, Object>>
	 * @throws RadowException
	 * @throws AppException
	 * @throws
	 * @author lixy
	 */
	public Map<String, Object> getCBDList(HttpServletRequest request) {

		String cbd_id = (String) request.getParameter("cbd_id");
		Map<String, Object> tmpData = null;
		String[] cbd_ids = cbd_id.split("@");

		StringBuffer sql = new StringBuffer();
		if (DBType.ORACLE == DBUtil.getDBType()) {

			sql.append("select ci.cbd_id,"
					+ "ci.cbd_name,"
					+ "ci.cbd_path,"
					+ "ci.cbd_date,"
					+ "ci.cdb_word_year_no,"
					+
					// "ci.cbd_word," +
					// "ci.cbd_year," +
					// "ci.cbd_no," +
					"ci.cbd_leader,"
					+ "substr(ci.cbd_date1,0,4) || '��' || substr(ci.cbd_date1,6,2) || '��' || substr(ci.cbd_date1,9,2) || '��' as cbd_date1,"
					+ "ci.cbd_organ," + "ci.cbd_text," + "ci.cbd_personid,"
					+ "ci.cbd_personname," + "ci.cbd_userid,"
					+ "ci.cbd_username," + "ci.objectno, " + "ci.cbd_cbr "
					+ "from cbd_info ci " + "where 1=1 ");
		} else if (DBType.MYSQL == DBUtil.getDBType()) {
			sql.append("select ci.cbd_id,"
					+ "ci.cbd_name,"
					+ "ci.cbd_path,"
					+ "ci.cbd_date,"
					+ "ci.cdb_word_year_no,"
					+
					// "ci.cbd_word," +
					// "ci.cbd_year," +
					// "ci.cbd_no," +
					"ci.cbd_leader,"
					+ "CONCAT (SUBSTR(ci.cbd_date1, 1, 4),'��',cast(substr(ci.CBD_DATE1,6,2) as unsigned int) ,'��' ,cast(substr(ci.CBD_DATE1,9,2) as unsigned int),'��' ) as cbd_date1 ,"
					+ "ci.cbd_organ," + "ci.cbd_text," + "ci.cbd_personid,"
					+ "ci.cbd_personname," + "ci.cbd_userid,"
					+ "ci.cbd_username," + "ci.objectno, " + "ci.cbd_cbr "
					+ "from cbd_info ci " + "where 1=1 ");
		}
		if ("u".equals(cbd_ids[1])) {
			sql.append(" and ci.cbd_id = '" + cbd_ids[0] + "'");
		} else if ("gu".equals(cbd_ids[1])) {
			sql.append(" and ci.objectno = '" + cbd_ids[0]
					+ "' and ci.cbd_path = '2'");
		} else if ("gg".equals(cbd_ids[1])) {
			sql.append(" and ci.objectno = '" + cbd_ids[0]
					+ "' and ci.cbd_path = '3'");
		}

		Map<String, Object> tmp = null;
		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql.toString());
			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		if (tmp != null) {
			tmpData = tmp;
		}

		return tmpData;
	}

	/**
	 * @$comment ��ȡ�������ܱ���Ϣ
	 * @param request
	 * @return
	 */
	Map<String, Object> getbaList(HttpServletRequest request, String a) {

		Map<String, Object> tmp = null;
		String a0000 = (String) request.getAttribute("a0000");

		String sql = "select a01.A0101, "
				+ // --����
				"  (select code_name from code_value where code_type='GB2261' and code_value.code_value=a01.a0104) A0104,"
				+ // --�Ա�
				"  a01.A0107,"
				+ // --��������
				"  (select code_name from code_value where code_type='GB3304' and code_value.code_value=a01.a0117) A0117,"
				+ // --����
				"  (select code_name from code_value where code_type='GB4762' and code_value.code_value=a01.a0141) A0141,"
				+ // --������ò
				" a01.QRZXL,  "
				+ // --���ȫ����ѧ��
				" a01.QRZXW,  "
				+ // --���ȫ����ѧ
				" a01.A0192,"
				+ // --������λ��ְ��
				" (select code_name from code_value where code_type='ZB09' and code_value.code_value = a01.a0148) a0148,"
				+ // ְ����
				" (select code_name from code_value where code_type='ZB134' and code_value.code_value = a01.a0120) a0120,"
				+ // ����
				" a29.A2947,"
				+ // ���빫��Ա����ʱ��
				" (select code_name from code_value where code_type='ZB77' and code_value.code_value = a29.a2911) a2911"
				+ // ���빫��Ա���鷽ʽ
				" from A01 a01 left join A29 a29 on a01.a0000=a29.a0000 where a01.A0000='"
				+ a0000 + "'";

		try {
			HBSession session = HBUtil.getHBSession();
			CommonQueryBS query = new CommonQueryBS();

			query.setConnection(session.connection());

			query.setQuerySQL(sql);

			Vector<?> vector = query.query();
			Iterator<?> iterator = vector.iterator();
			if (iterator.hasNext()) {

				while (iterator.hasNext()) {

					tmp = (Map<String, Object>) iterator.next();

				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		tmp.put("jgmc", getBysql(a0000));

		return tmp;
	}

	/**
	 * ��ѯ��λ
	 * 
	 * @param a0000
	 * @return
	 */
	private String getBysql(String a0000) {
		// ��ʼ��ȡ���鷳�Ĺ�����λ
		HBSession sess = HBUtil.getHBSession();
		String hql = " from A02  where a0000='" + a0000 + "' and a0281='true' ";
		List<A02> list = sess.createQuery(hql).list();
		String jgmc = "";
		if (list != null && list.size() > 0) {
			Collections.sort(list, new Comparator<Object>() {

				@Override
				public int compare(Object o1, Object o2) {
					A02 a1 = (A02) o1;
					A02 a2 = (A02) o2;
					if (a1.getA0221() == null || "".equals(a1.getA0221())) {
						return 1;
					}
					if (a2.getA0221() == null || "".equals(a2.getA0221())) {
						return -1;
					}
					return a1.getA0221().compareTo(a2.getA0221());
				}

			});

			A02 a02 = list.get(0);
			String jgbm = a02.getA0201b();// ��������
			String zwmc = a02.getA0216a() == null ? "" : " " + a02.getA0216a();// ְ������
			B01 b01 = null;
			if (jgbm != null && !"".equals(jgbm)) {
				b01 = (B01) sess.get(B01.class, jgbm);
			}
			if (b01 != null) {
				String b0194 = b01.getB0194();// 1�����˵�λ��2�����������3���������顣
				if ("2".equals(b0194)) {
					jgmc = b01.getB0101() + jgmc;
					while (true) {
						b01 = (B01) sess.get(B01.class, b01.getB0121()); // ��ȡ�ϼ����������и��ָ����ıȽ�
						if (b01 == null) {
							break;
						} else {
							b0194 = b01.getB0194();
							if ("1".equals(b0194)) {// ���˵�λ
								jgmc = b01.getB0101() + jgmc;
								return jgmc + zwmc;

							} else if ("3".equals(b0194)) {// 3����������
								continue;
							} else if ("2".equals(b0194)) { // �������

								if (b01 != null) {

									jgmc = b01.getB0101() + jgmc;

								}
							}
						}
					}
				} else {
					jgmc = b01.getB0101() + jgmc;
					return jgmc + zwmc;
				}
				// tmp = new HashMap<String, Object>();
				// tmp.put("jgmc", jgmcList.get(0));
			}
		}
		return jgmc;

	}

	/**
	 * ��ȡ���ʱ��ķ��� 2016/8/11
	 * 
	 * @return date
	 */
	public String getDate() {
		Calendar now = Calendar.getInstance();
		Date date = now.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");

		return sdf.format(date);
	}

}
