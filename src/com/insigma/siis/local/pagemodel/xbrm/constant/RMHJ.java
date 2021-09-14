package com.insigma.siis.local.pagemodel.xbrm.constant;

import java.util.HashMap;
import java.util.Map;

import com.insigma.odin.framework.radow.event.EventRtnType;

/**
 * ���⻷��
 * @author zhdj0
 *
 */
public class RMHJ {
	public final static String DONG_YI = "1";
	public final static String JI_BEN_QING_KUANG = "0";
	public final static String MIN_ZHU_TUI_JIAN = "2";
	public final static String KAO_CHA = "3";
	public final static String TAO_LUN_JUE_DING = "4";
	public final static String REN_MIAN_ZHI = "5";
	public Map<String, String> rmhj = new HashMap<String, String>();
	
	public Map<String, String> ryfl = new HashMap<String, String>();
	public final static String TIAO_PEI_LEI_BIE = "1";
	public final static String TAN_HUA_AN_PAI = "2";
	public final static String FEN_GUAN_LING_DAO = "3";
	public RMHJ() {
		rmhj.put("DONG_YI", DONG_YI);
		rmhj.put("JI_BEN_QING_KUANG", JI_BEN_QING_KUANG);
		rmhj.put("MIN_ZHU_TUI_JIAN", MIN_ZHU_TUI_JIAN);
		rmhj.put("KAO_CHA", KAO_CHA);
		rmhj.put("TAO_LUN_JUE_DING", TAO_LUN_JUE_DING);
		rmhj.put("REN_MIAN_ZHI", REN_MIAN_ZHI);
		ryfl.put("TIAO_PEI_LEI_BIE", TIAO_PEI_LEI_BIE);
		ryfl.put("TAN_HUA_AN_PAI", TAN_HUA_AN_PAI);
		ryfl.put("FEN_GUAN_LING_DAO", FEN_GUAN_LING_DAO);
	}
	
	
	/**
	 * 1��grid�б�������һ�����
	 * 2��initX��������������������ֵ
	 */
	/**
	 * ��ȡ��Ч����
	 */
	public static String getRealCurHJ(String cur_hj,String cur_hj_4){
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			cur_hj = cur_hj_4;
			
		}else if(RMHJ.JI_BEN_QING_KUANG.equals(cur_hj)){
			cur_hj = RMHJ.DONG_YI;
		}
		return cur_hj;
	}
	
	
	
	static QuerySqlMap qsm = new QuerySqlMap();
	/**
	 * grid ��ѯgridcq.dogridquery
	 */
	public static QuerySqlMap getQuerySqlMap(String cur_hj,String cur_hj_4,String dc005){
		
		qsm.ref_dc001 = "js01.dc001";
		qsm.orderbyfield = " JS_SORT";
		qsm.dc001_alias = "dc001";
		qsm.hj4sql = " and js_type like '"+cur_hj+"%' ";
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			qsm.orderbyfield = " JS_SORT4";
		}
		
		if(RMHJ.DONG_YI.equals(cur_hj)){
			qsm.hj4sql = "";
			qsm.orderbyfield = " js0113";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			qsm.orderbyfield = " JS_SORT_dc005_2";
			qsm.ref_dc001 = "js_hj.JS_CLASS_DC001_2";
			qsm.dc001_alias = "dc001_2";
		}
		return qsm;
	}
	
	public static  class QuerySqlMap{
		//grid ��ѯgridcq.dogridquery
		/**
		 * deploy_classify���������dc001 ��Ӧ������������ֶ����
		 */
		public String ref_dc001 = "";
		/**
		 * grid��ѯ�����ֶΡ���������Ӧ
		 */
		public String orderbyfield = "";
		/**
		 * ���ı���������grid�б���ʾ�Ĳ�ͬ��𣬸�������
		 */
		public String dc001_alias = "";
		/**
		 * ���ڹ���
		 */
		public String hj4sql = "";
		
		
		
	}
	
	
	
	
	
	
	
	
	
	static InsertSqlMap ism = new InsertSqlMap();
	/**
	 * queryByNameAndIDS ��������
	 */
	public static InsertSqlMap getInsertSqlMap(String cur_hj,String cur_hj_4,String dc005,String tplb){
		
		ism.hj_sort = "js_sort";
		ism.javascript = "";
		ism.thapfield = "";
		ism.thapvalue = "";
		ism.tplbvalue = tplb;
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj)){
			ism.hj_sort = "js_sort4";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			ism.hj_sort = " JS_SORT_dc005_2";
		}
		
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)){
			ism.thapfield = " ,js_class_dc001_2 ";
			ism.thapvalue = " ,'"+tplb+"'";
			ism.javascript += "clearGridJsonStore('5');clearGridJsonStore('5_2');";
			ism.tplbvalue = "";
		}
		
		if(!RMHJ.DONG_YI.equals(cur_hj)){
			ism.javascript += "clearGridJsonStore('"+RMHJ.DONG_YI+"');";
		}
		return ism;
	}
	public static  class InsertSqlMap{
		//queryByNameAndIDS ��������
		/**
		 * �����ֶ�
		 */
		public String hj_sort = "";
		/**
		 * ���id
		 */
		public String tplb = "";
		/**
		 * ̸�����Ŷ�Ӧ������ֶ�
		 */
		public String thapfield = "";
		/**
		 * ̸�����Ŷ�Ӧ�����ֵ
		 */
		public String thapvalue = "";
		/**
		 * ��������Ӧ�����ֵ
		 */
		public String tplbvalue = "";
		public String javascript = "";
	}
	
	
	
	
	
	
	
	
	
	
	static SortSqlMap ssm = new SortSqlMap();
	/**
	 * personsort �б�����
	 */
	public static SortSqlMap getSortSqlMap(String cur_hj,String cur_hj_4,String dc005){
		ssm.table = "js_hj";
		ssm.sortfield = "JS_SORT";
		
		if(RMHJ.TAO_LUN_JUE_DING.equals(cur_hj_4)){
			ssm.sortfield = "JS_SORT4";
		}
		ssm.where = " and JS_TYPE like '"+cur_hj+"%'";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			ssm.table = "js01";
			ssm.where = "";
			ssm.sortfield = "js0113";
		}
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			ssm.sortfield = " JS_SORT_dc005_2";
		}
		return ssm;
	}
	public static  class SortSqlMap{
		//personsort �б�����
		/**
		 * �����
		 */
		public String table = "";
		/**
		 * �����ֶ�
		 */
		public String sortfield = "";
		/**
		 * ����
		 */
		public String where = "";
	}
	
	
	
	
	
	
	
	
	static HjDelSqlMap hdsm = new HjDelSqlMap();
	/**
	 * hjDelete ɾ��������Ա���ڼ�¼	
	 */
	public static HjDelSqlMap getHjDelSqlMap(String cur_hj,String cur_hj_4){
		hdsm.mainMessage = "";
		hdsm.javascript = "";
		if(RMHJ.DONG_YI.equals(cur_hj)){
			hdsm.mainMessage = "��ʼ�����޷��Ƴ���Ա����ѡ��ɾ����Ա��";
		}
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)){
			hdsm.javascript = "clearGridJsonStore('5');clearGridJsonStore('5_2');";
		}
		
		return hdsm;
	}
	public static  class HjDelSqlMap{
		/**
		 * �����
		 */
		public String mainMessage = "";
		/**
		 * �����ֶ�
		 */
		public String javascript = "";
		
	}
	
	
	
	
	static SavedSqlMap sdsm = new SavedSqlMap();
	/**
	 * �б������𱣴� savedata
	 */
	public static SavedSqlMap getSavedSqlMap(String cur_hj,String dc005){
		sdsm.table = "js01";
		sdsm.field = "dc001";
		sdsm.where = "";
		if(RMHJ.REN_MIAN_ZHI.equals(cur_hj)&&RMHJ.TAN_HUA_AN_PAI.equals(dc005)){
			sdsm.table = "js_hj";
			sdsm.field = "js_class_dc001_2";
			sdsm.where = " and js_type='5'";
		}
		return sdsm;
	}
	public static  class SavedSqlMap{
		/**
		 * ��
		 */
		public String table = "";
		/**
		 * �ֶ�
		 */
		public String field = "";
		/**
		 * ����
		 */
		public String where = "";
	}
	
}
