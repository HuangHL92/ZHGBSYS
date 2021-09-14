package com.insigma.siis.local.pagemodel.xbrm;


import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.fileupload.FileItem;
import org.hibernate.SQLQuery;

import com.JUpload.JUpload;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.DeployClassify;
import com.insigma.siis.local.business.entity.Js01;
import com.insigma.siis.local.business.entity.JsAtt;
import com.insigma.siis.local.business.entity.RecordBatch;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.publicServantManage.ExportAsposeBS;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.pagemodel.cadremgn.comm.QueryCommon;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ;
import com.insigma.siis.local.pagemodel.xbrm.tpl.ExcelStyleUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JBXXTPageModel extends PageModel implements JUpload{
	
	JSGLBS bs = new JSGLBS();
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		//����������
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	private void setSelectCombo() throws RadowException{
		String sql="select b0111, jsdw002  from js_dw order by jsdw003";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode = new ArrayList<HashMap<String,Object>>();
		try {
			listCode = cqbs.getListBySQL(sql);
		} catch (AppException e) {
			e.printStackTrace();
		}
		HashMap<String, Object> mapCode_1=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
				mapCode_1.put(listCode.get(i).get("b0111").toString(), listCode.get(i).get("jsdw002").toString());
		}
	}
	
	
	private void setGridCombo() throws RadowException, AppException{
		//����������
		String rbId = this.getPageElement("rbId").getValue();
		//String dc005 = this.getPageElement("dc005").getValue();//����ʶ
		String sql="select  DC001,DC003,lpad(dc004,10,'0') dc004,dc005 from DEPLOY_CLASSIFY where RB_ID  ='"+rbId+"'  order by dc004";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode=cqbs.getListBySQL(sql);
		HashMap<String, Object> mapCode_1=new LinkedHashMap<String, Object>();
		HashMap<String, Object> mapCode_2=new LinkedHashMap<String, Object>();
		for(int i=0;i<listCode.size();i++){
			if(RMHJ.TIAO_PEI_LEI_BIE.equals(listCode.get(i).get("dc005"))){
				mapCode_1.put(listCode.get(i).get("dc004").toString()+"@@"+listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
			}else if(RMHJ.TAN_HUA_AN_PAI.equals(listCode.get(i).get("dc005"))){
				mapCode_2.put(listCode.get(i).get("dc004").toString()+"@@"+listCode.get(i).get("dc001").toString(), listCode.get(i).get("dc003").toString());
			}
		}
		mapCode_1.put("999@@999", "����");
		mapCode_2.put("999@@999", "����");
		((Combo)this.getPageElement("dc001_grid")).setValueListForSelect(mapCode_1);
		((Combo)this.getPageElement("dc001_2_grid")).setValueListForSelect(mapCode_2);
	}
	
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String rbId = this.getPageElement("rbId").getValue();
		RecordBatch rb = (RecordBatch) HBUtil.getHBSession().get(RecordBatch.class, rbId);
		if(rb!=null){
			if("1".equals(rb.getRbUpdated())){
				this.getExecuteSG().addExecuteCode("Ext.getCmp('updateNRM').addClass('bh');");
			}
		}
		setGridCombo();
		setSelectCombo();
		this.setNextEventName("gridcq.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("authorLetter")
	public int authorLetter(String data) throws RadowException, AppException {
		//���뱨����ʱ���
		return 0 ;
		/*
		 * QCJSFileExportBS fileBS = new QCJSFileExportBS();
		 * 
		 * String rbId = this.getPageElement("rbId").getValue();//���� String dc005 =
		 * this.getPageElement("dc005").getValue();//����ʶ String cur_hj =
		 * this.getPageElement("cur_hj").getValue();//���� String cur_hj_4 =
		 * this.getPageElement("cur_hj_4").getValue();//���۾����ֻ��� String
		 * personsStrOne=(fileBS.getSheetPersons(rbId,dc005,cur_hj,cur_hj_4,1)).replace(
		 * "����������", ""); //ʱ�� String
		 * nowDate=DateUtil.timeToString(DateUtil.getTimestamp(), "MM��dd��"); //��׼�䱸 int
		 * personum=fileBS.getPersonsNum(rbId,dc005,cur_hj,cur_hj_4);
		 * 
		 * String[] Flds={"personInfo","time","hzpb","qtsy"}; String[]
		 * Vals={personsStrOne,nowDate,personum+"",data}; String path=new
		 * ExportAsposeBS().signatureSheet(Flds,Vals,"wth.doc","ί�к�.doc",null);
		 * if(path.length()==0){ return EventRtnType.FAILD; } String infile
		 * =path.substring(0,path.length()-1)+".zip" ; SevenZipUtil.zip7z(path, infile,
		 * null); this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		 * this.getExecuteSG().addExecuteCode("window.reloadTree()"); return
		 * EventRtnType.NORMAL_SUCCESS;
		 */}
	
	
	@PageEvent("importSheet")
	public int importSheet(String data) throws RadowException, AppException {
		//���뱨����ʱ���
				return 0 ;
		/*
		 * QCJSFileExportBS fileBS = new QCJSFileExportBS();
		 * 
		 * String rbId = this.getPageElement("rbId").getValue();//���� String dc005 =
		 * this.getPageElement("dc005").getValue();//����ʶ String cur_hj =
		 * this.getPageElement("cur_hj").getValue();//���� String cur_hj_4 =
		 * this.getPageElement("cur_hj_4").getValue();//���۾����ֻ���
		 * 
		 * //��ǩ�� String personsStr=fileBS.getSheetPersons(rbId,dc005,cur_hj,cur_hj_4,2);
		 * data=data+","+personsStr; String[]
		 * Flds={"tqyj1","tqyj2","tqyj3","tqyj4","tqyj5","tqyj6","sxch1","sxch2","gzsy",
		 * "rydx"}; String[] Vals=data.split(","); String path=new
		 * ExportAsposeBS().signatureSheet(Flds,Vals,"sqd.doc","��ǩ��.doc",null);
		 * //��ְǰ��ȡ�����������word String[] gonganFlds={"names"}; String
		 * personsStrOne=fileBS.getSheetPersons(rbId,dc005,cur_hj,cur_hj_4,1); String[]
		 * gonganVals={personsStrOne}; new
		 * ExportAsposeBS().signatureSheet(gonganFlds,gonganVals,"gonganyj.doc",
		 * "��ְǰ��ȡ�������������.doc",path); //��ְǰ��ȡ����������� new
		 * ExportAsposeBS().signatureSheet(gonganFlds,gonganVals,"jianchajiguanyj.doc",
		 * "��ְǰ��ȡ�����������.doc",path); //��ȡ�ͼ����������� new
		 * ExportAsposeBS().signatureSheet(gonganFlds,gonganVals,"jijianyj.doc",
		 * "��ȡ�ͼ�����������.doc",path); //��ְǰ��ȡ���л�������� new
		 * ExportAsposeBS().signatureSheet(gonganFlds,gonganVals,"shenpanyj.doc",
		 * "��ְǰ��ȡ���л��������.doc",path); //��ְǰ��ȡ����������������� new
		 * ExportAsposeBS().signatureSheet(gonganFlds,gonganVals,"weishengyj.doc",
		 * "��ְǰ��ȡ�����������������.doc",path); //��ȡ�����Ա����,�������8���� StringBuffer personInfoS=new
		 * StringBuffer(); for(int i=1;i<9;i++){ if(i==1){ personInfoS.append("num1");
		 * }else{ personInfoS.append(","+"num"+i); } personInfoS.append(","+"name"+i);
		 * personInfoS.append(","+"sex"+i); personInfoS.append(","+"idcard"+i);
		 * personInfoS.append(","+"zw"+i); } String[]
		 * personInfoFlds=personInfoS.toString().split(","); String
		 * personsinfo=fileBS.getPersonsInfo(rbId,dc005,cur_hj,cur_hj_4); String[]
		 * personInfoVals=personsinfo.split(","); new
		 * ExportAsposeBS().signatureSheet(personInfoFlds,personInfoVals,"tingqumd.doc",
		 * "��ȡ�����Ա����.doc",path); //��ȡ�����Ա����ż���� StringBuffer matepersonInfoS=new
		 * StringBuffer(); for(int i=1;i<9;i++){ if(i==1){ matepersonInfoS.append("n1");
		 * }else{ matepersonInfoS.append(","+"n"+i); }
		 * matepersonInfoS.append(","+"na"+i); matepersonInfoS.append(","+"s"+i);
		 * matepersonInfoS.append(","+"i"+i); matepersonInfoS.append(","+"z"+i);
		 * matepersonInfoS.append(","+"p"+i); matepersonInfoS.append(","+"ps"+i);
		 * matepersonInfoS.append(","+"pi"+i); matepersonInfoS.append(","+"pz"+i); }
		 * 
		 * String[] matepersonInfoFlds=matepersonInfoS.toString().split(","); String
		 * matepersonsinfo=fileBS.matePersonInfo(rbId,dc005,cur_hj,cur_hj_4); String[]
		 * matepersonInfoVals=matepersonsinfo.split(","); new
		 * ExportAsposeBS().signatureSheet(matepersonInfoFlds,matepersonInfoVals,
		 * "peiomd.doc","��ȡ�����Ա����ż����.doc",path);
		 * 
		 * if(path.length()==0){ return EventRtnType.FAILD; } String infile
		 * =path.substring(0,path.length()-1)+".zip" ; SevenZipUtil.zip7z(path, infile,
		 * null); this.getPageElement("downfile").setValue(infile.replace("\\", "/"));
		 * this.getExecuteSG().addExecuteCode("window.reloadTree()"); return
		 * EventRtnType.NORMAL_SUCCESS;
		 */}
	

	@PageEvent("gridcq.dogridquery")
	@AutoNoMask
	public int doMemberQuery(int start,int limit) throws RadowException{
		try {
			String rbId = this.getPageElement("rbId").getValue();//����
			String dc005 = this.getPageElement("dc005").getValue();//����ʶ
			
			String cur_hj = this.getPageElement("cur_hj").getValue();//����
			String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//���۾����ֻ���
			cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
			RMHJ.QuerySqlMap sm = RMHJ.getQuerySqlMap(cur_hj, cur_hj_4, dc005);
			
			
			String sql = "select distinct HAVE_FINE(js01.a0000,js01.js0100) havefine,js01.js0119, js01.js0118, js_hj.js0100,nvl((select lpad(dc004,10,'0') dc004 from deploy_classify t where t.dc001="+sm.ref_dc001+"),999)||'@@'||nvl("+sm.ref_dc001+",'999') "+sm.dc001_alias+","
					+ " js01.a0000,a0101,"+sm.orderbyfield
					+ " from a01,js01,js_hj where "
					+ " a01.a0000=js01.a0000 and js_hj.js0100=js01.js0100 "
					+ " and rb_id='"+rbId+"'  "+sm.hj4sql +"order by"+sm.orderbyfield;
			this.pageQuery(sql, "SQL", start, limit);
        	return EventRtnType.SPE_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.SPE_SUCCESS;
		}
	}
	
	//��������ѯ��������ԱIDs
	@PageEvent("queryByNameAndIDS")
	public int queryByNameAndIDS(String listStr) throws Exception{
		//System.out.println(listStr);
		String tplb = this.getPageElement("tplb").getValue();//�������
		//������� �������ݿ������������
		HBSession sess = HBUtil.getHBSession();
		List dclist = sess.createSQLQuery("select dc001 from DEPLOY_CLASSIFY where dc001=?").setString(0, tplb).list();
		String rbId = this.getPageElement("rbId").getValue();
		String cur_hj = this.getPageElement("cur_hj").getValue();//����
		String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//���۾����ֻ���
		String dc005 = this.request.getParameter("dc005");
		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		
		//������� �������ݿ������������
		if(dclist.size()==0&&!"".equals(tplb)){
			String id = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into deploy_classify(dc001,rb_id,dc003,dc004,dc005)"
					+ " values(?,?,?,deploy_classify_dc004.nextval,?)",
					new Object[]{id,rbId,tplb,RMHJ.REN_MIAN_ZHI.equals(cur_hj)?"2":"1"});
			tplb = id;
			setGridCombo();
		}
		RMHJ.InsertSqlMap sm = RMHJ.getInsertSqlMap(cur_hj, cur_hj_4, dc005, tplb);
		StringBuffer sql = new StringBuffer("insert into js01(js0100, a0000, rb_id,js0102, js0103, js0104, js0105, js0106, js0114,js0108,js0110,js0109,dc001,js0113)  "
				+ "(select sys_guid(),a0000,'"+rbId+"' rb_id,a0101,a0107,a0134,a0140,zgxl||zgxw,a0104,a0192a, a0288 || ' ' || a0192c rtzjsj,"
						+ "(select replace(TO_CHAR(wm_concat(a0243)), ',', ' ') from a02 where a02.a0000=a01.a0000 and a02.a0281 = 'true') a0243,'"+sm.tplbvalue+"',deploy_classify_dc004.nextval from a01");
		StringBuffer hjSql = new StringBuffer("insert into js_hj(js0100,JS_TYPE,"+sm.hj_sort+sm.thapfield+") (select js0100,'"+cur_hj+"',js0113 "+sm.thapvalue+" from js01 where rb_id='"+rbId+"' "
				+ " and not exists (select 1 from js_hj where js_hj.js0100=js01.js0100 and js_type='"+cur_hj+"')  ");
		hjSql.append(" and a0000 in ('-1'");
		sql.append(" where a0000 in ('-1'");
		if(listStr!=null && listStr.length()>2){
			listStr = listStr.substring(1, listStr.length()-1);
			List<String> list = Arrays.asList(listStr.split(","));
			for(String id:list){
				sql.append(",'"+id.trim()+"'");
				hjSql.append(",'"+id.trim()+"'");
				
			}
			sql.append(") and not exists (select 1 from js01 where js01.a0000=a01.a0000 and js01.rb_id='"+rbId+"' ))");
			try {
				HBUtil.executeUpdate(sql.toString());
				
				hjSql.append("))");
				HBUtil.executeUpdate(hjSql.toString());
			} catch (AppException e) {
				this.setMainMessage("����ʧ�ܣ�");
				e.printStackTrace();
			}
			//this.setMainMessage("����ɹ���");
			if(!"".equals(sm.javascript)){
				this.getExecuteSG().addExecuteCode(sm.javascript);
			}
			this.getExecuteSG().addExecuteCode("$('#tplb').val('');");
			this.setNextEventName("gridcq.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.setMainMessage("�޷���ѯ������Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	@PageEvent("personsort")
	@Transaction
	@Synchronous(true)
	public int savePersonsort()throws RadowException, AppException{
		
		String cur_hj = this.getPageElement("cur_hj").getValue();//����
		String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();//���۾����ֻ���
		String dc005 = this.getPageElement("dc005").getValue();//����ʶ

		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		RMHJ.SortSqlMap sm = RMHJ.getSortSqlMap(cur_hj, cur_hj_4, dc005);
		
		List<HashMap<String,String>> list = this.getPageElement("gridcq").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update "+sm.table+" set "+sm.sortfield+"=? where js0100=?"+sm.where;
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for(HashMap<String,String> m : list){
				String js0100 = m.get("js0100");
				ps.setInt(1, i);
				ps.setString(2, js0100);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if(con!=null){
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		//this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
		this.toastmessage("�����ѱ��棡");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ʵʱ����������Ϣ
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("saveNRMValue")
	@Transaction
	@Synchronous(true)
	public int saveNRMValue()throws RadowException, AppException{
		String nrmid = this.getPageElement("nrmid").getValue();//�ֶ�
		String nrmvalue = this.getPageElement("nrmvalue").getValue();//ֵ
		String js0100 = this.getPageElement("js0100").getValue();//ֵ
		String nrmdesc = this.getPageElement("nrmdesc").getValue();//����
		String js0102 = this.getPageElement("js0102").getValue();
		String rbId = this.getPageElement("rbId").getValue();//����
		
		if(!"".equals(js0100)){
			HBSession sess = HBUtil.getHBSession();
			
			Connection con = null;
			try {
				con = sess.connection();
				con.setAutoCommit(false);
				String sql = "update js01 set "+nrmid+"=? where js0100=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nrmvalue);
				ps.setString(2, js0100);
				ps.addBatch();
				ps.executeBatch();
				
				
				
				con.commit();
				if("js0111".equals(nrmid)||"js0117".equals(nrmid)){//������������Ϣ��
					Js01 js01 = (Js01)sess.get(Js01.class, js0100);
					bs.setPm(this);
					bs.saveA053(js01, sess);
				}
				
				
				ps.close();
				con.close();
			} catch (Exception e) {
				try {
					con.rollback();
					if(con!=null){
						con.close();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
				this.toastmessage(js0102+"����"+nrmdesc+"����Ϣ����ʧ�ܣ�",2);
				return EventRtnType.FAILD;
			}
			//this.getExecuteSG().addExecuteCode("radow.doEvent('gridA01.dogridquery');");
		}
		this.toastmessage(js0102+"����"+nrmdesc+"����Ϣ�Ѹ��£�",2);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������쵼�ɲ������й���������ʵ���������Ϣ��Excel
	 */
	@PageEvent("exportJBXXTExcel")
	@Transaction
	@Synchronous(true)
	public void exportJBXXTExcel(){
		QCJSFileExportBS fileBS = new QCJSFileExportBS();
		HBSession sess = HBUtil.getHBSession();
		CommQuery cq = new CommQuery();
		try {

			List<List<Map<String,String>>> datalistAll=new ArrayList<List<Map<String,String>>>();
			String a0000s="";
			List<HashMap<String,String>> list = this.getPageElement("gridcq").getStringValueList();
			for(HashMap<String,String> m : list){
				String a00000sql="select * from JS17 where a0000 ='"+m.get("a0000").toString()+"'";
				List<HashMap<String, Object>> listBySQL = cq.getListBySQL(a00000sql);
				List<Map<String,String>> datalist=new ArrayList<Map<String,String>>();
				for(HashMap<String, Object> submap:listBySQL){
					Map<String,String> map1=new HashMap<String,String>();
			        map1.put("target", submap.get("js1702")==null?"":submap.get("js1702").toString());
			        map1.put("name", submap.get("js1703")==null?"":submap.get("js1703").toString());
			        map1.put("sex", submap.get("js1704")==null?"":submap.get("js1704").toString());
			        map1.put("zw", submap.get("js1705")==null?"":submap.get("js1705").toString());
			        map1.put("idcard", submap.get("js1706")==null?"":submap.get("js1706").toString());
			        map1.put("housse", submap.get("js1707")==null?"":submap.get("js1707").toString());
			        map1.put("date", submap.get("js1708")==null?"":submap.get("js1708").toString());
			        datalist.add(map1);
				}
				datalistAll.add(datalist);
			}
			fileBS.exportJBXXB(datalistAll);
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if(fns.size()==1){//һ���ļ�ֱ������
				downloadPath = fileBS.getOutputpath()+fns.get(0);
				downloadName = fns.get(0);
			}else if(fns.size()>1){//ѹ��
				downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
				downloadName = fileBS.getSheetName()+".zip";
				Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
			}else{
				
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
			
		} catch (RadowException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * ���ݻ���
	 * @param id
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("peopleInfo")
	@Synchronous(true)
	public int peopleInfo(String id) throws RadowException{
		try {
			String a0000id = id;
			
			String fysql = "select a0000,(case a0104 when '1' then '��' when '2' then 'Ů' end) a0104,a0101,a0184,a0107,a0117,a0111a,a0140,a0134,a0196,a0192a from a01 where a0000='"+a0000id+"'";
			String a36sql=" select * from A36 right join ("+fysql+") m on a36.a0000=m.a0000";
			
			HBSession sess = HBUtil.getHBSession();
			String data;
			List<Object[]>  list=sess.createSQLQuery(a36sql).list();
			
			StringBuffer strB=new StringBuffer();
			
			StringBuffer trstrB=new StringBuffer();
			
			for(int i=0;i<list.size();i++){
				Object[] objA=list.get(i);
				
				//ƴ�ӱ�����tr
				if(i==0){
					strB.append("<tr style='height:30px;'>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input  type='text' value='������' />");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text' value='"+objA[14]+"' />");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text' value='"+(objA[13].toString())+"' />");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text' value='"+objA[22]+"' />");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text' value='"+objA[15]+"' />");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text'/>");
					strB.append("</td>");
					strB.append("<td style='margin:0px;'>");
					strB.append("<input type='text' value='"+DateUtil.dateToString(DateUtil.getTimestamp(), "yyyyMMdd")+"' />");
					strB.append("</td>");
					strB.append("</tr>");
				}
				//ƴ�Ӽ�ͥ��Աtr
				trstrB.append("<tr style='height:30px;'>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text' value='"+objA[3]+"' />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text' value='"+objA[2]+"' />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text'  />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text' value='"+objA[5]+"' />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text'  />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text'  />");
				trstrB.append("</td>");
				trstrB.append("<td style='margin:0px;'>");
				trstrB.append("<input type='text' value='"+DateUtil.dateToString(DateUtil.getTimestamp(), "yyyyMMdd")+"' />");
				trstrB.append("</td>");
				trstrB.append("</tr>");
				
			}
			strB.append(trstrB);
			this.getExecuteSG().addExecuteCode("$('#tableID').empty();$('#tableID').append(\""+strB.toString()+"\");");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
	}
	
	
	private void setBusinessInfo(HBSession sess) {
		try {
			String js0100 = this.getPageElement("js0100").getValue();
			//String a0000 = this.getPageElement("a0000").getValue();
			//List<Js01> list = sess.createQuery("from Js01 where a0000='"+a0000+"'").list();
			
			Js01 js01 = (Js01)sess.get(Js01.class, js0100);
			if(js01==null){//���ҳ����Ϣ
				js01 = new Js01();
				js01.setA0000("");
				js01.setJs0100("");
			}
			bs.setPm(this);
			//bs.setPageInfo(js01,sess);
			//�����ļ���Ϣ
			List<JsAtt> jsattlist = sess.createQuery("from JsAtt where js0100='"+js0100+"'").list();
			
			if(jsattlist!=null){
				Map<String, List<Map<String, String>>> m = new HashMap<String, List<Map<String,String>>>();
				List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
				for(JsAtt jsatt : jsattlist){
					String tablename = jsatt.getJsa02();
					listmap = m.get(tablename);
					if(listmap==null){
						listmap = new ArrayList<Map<String, String>>();
						m.put(tablename, listmap);
					}
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", jsatt.getJsa00());
					map.put("name", jsatt.getJsa04());
					map.put("fileSize", jsatt.getJsa08());
					//map2.put("readOnly", "true");
					
					listmap.add(map);
				}
				String fx;
				for(int i=2; i<12; i++){
					if(i<10){
						fx = "0"+i;
					}else{
						fx = i+"";
					}
					String key = "JS"+fx;
					listmap = m.get(key);
					if(listmap==null){
						listmap = new ArrayList<Map<String, String>>();
					}
					this.setFilesInfo(key.replace("JS", "file"),listmap,true);
				}
				//this.getExecuteSG().addExecuteCode("$('.ui-tabs-active').click()");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("��ѯ����");
		}
		
	}
	
	/**
	 * ����
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("save")
	@Transaction
	public int save(String id) throws RadowException{
		String rbId = this.getPageElement("rbId").getValue();//����id
		String js0100 = this.getPageElement("js0100").getValue();//������Աid
		String a0000 = this.getPageElement("a0000").getValue();//��Աid
		if(js0100==null||"".equals(js0100)){
			this.setMainMessage("��ѡ����Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		try {
			HBSession sess = HBUtil.getHBSession();
			Js01 js01 = (Js01)sess.get(Js01.class, js0100);
			if(js01==null){
				this.setMainMessage("��Ա��Ϣ�����ڣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			String jsonstr=this.getPageElement("objson").getValue();
			JSONArray array=JSONArray.fromObject(jsonstr);
			
			//ɾ�����������ݣ�
	        String sql = "delete from JS17 where a0000 = :a0000";
			SQLQuery query = sess.createSQLQuery(sql);
			query.setString("a0000", a0000);
			query.executeUpdate();
			sess.flush();
			for(int i=0;i<array.size();i++){
				JSONObject jsonObj= array.getJSONObject(i);
				
		        String js1301=UUID.randomUUID().toString().replaceAll("-", "");
		        //��������
		        HBUtil.executeUpdate("insert into JS17(js0100,rb_id,a0000,js1701,js1702,js1703,js1704,js1705,js1706,js1707,js1708)"
						+ " values(?,?,?,?,?,?,?,?,?,?,?)",
						new Object[]{js0100,rbId,a0000,js1301,jsonObj.getString("target"),jsonObj.getString("name"),jsonObj.getString("sex"),jsonObj.getString("zw"),jsonObj.getString("idcard"),jsonObj.getString("house"),jsonObj.getString("date")});
			}
			this.setMainMessage("����ɹ�");
		}  catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�"+e.getCause().getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@Override
	public Map<String, String> getFiles(List<FileItem> fileItem, Map<String, String> formDataMap) {
		Map<String, String> map = new HashMap<String, String>();
		// ����ļ�����
		String isfile = formDataMap.get("Filename");
		// �ж��Ƿ��ϴ��˸�����û���ϴ��򲻽����ļ�����
		if (isfile != null && !isfile.equals("")) {
			try {
				// ��ȡ����Ϣ
				FileItem fi = fileItem.get(0);
				DecimalFormat df = new DecimalFormat("#.00");
				String fileSize = df.format((double) fi.getSize() / 1048576) + "MB";
				// ����ļ�����1M����ʾM��С������ʾkb
				if (fi.getSize() < 1048576) {
					fileSize = (int) fi.getSize() / 1024 + "KB";
				}
				if (fi.getSize() < 1024) {
					fileSize = (int) fi.getSize() / 1024 + "B";
				}
				String id = saveFile(formDataMap, fi,fileSize);
				map.put("file_pk", id);
				map.put("file_name", isfile);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return map;
	}
	public static String  disk = JSGLBS.HZBPATH ;
	private String saveFile(Map<String, String> formDataMap, FileItem fi, String fileSize) throws Exception {
		// �����Ա��Ϣid
		String js0100 = formDataMap.get("js0100");
		String fileid = formDataMap.get("fileid");
		String filename = formDataMap.get("Filename");
		String rbId = formDataMap.get("rbId");//����id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		JsAtt st = new JsAtt();
		st.setJs0100(js0100);//��Ա��Ϣ
		st.setJsa00(UUID.randomUUID().toString());//����
		st.setJsa05(SysManagerUtils.getUserId());//�û�id
		st.setJsa06(sdf.format(new Date()));//�ϴ�ʱ��
		st.setRbId(rbId);//����id
		st.setJsa04(filename);
		
		String directory = "zhgbuploadfiles" + File.separator +rbId+ File.separator;
		String filePath = directory  + st.getJsa00();
		File f = new File(disk + directory);
		
		if(!f.isDirectory()){
			f.mkdirs();
		}
		fi.write(new File(disk + filePath));
		st.setJsa07(directory);
		st.setJsa08(fileSize);
		st.setJsa02(fileid.replace("file", "JS"));//����id
		HBUtil.getHBSession().save(st);
		HBUtil.getHBSession().flush();
		
		return st.getJsa00();
	}

	@Override
	public String deleteFile(String id) {
		try {
			HBSession sess = HBUtil.getHBSession();
			JsAtt ja = (JsAtt)sess.get(JsAtt.class, id);
			if(ja==null){
				return null;//ɾ��ʧ��
			}
			String directory = disk+ja.getJsa07();
			File f = new File(directory+id);
			if(f.isFile()){
				f.delete();
			}
			sess.delete(ja);
			sess.flush();
			
			return id;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * ɾ��������Ա��¼
	 * @param js0100
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("allDelete")
	public int allDelete(String js0100) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			bs.setPm(this);
			bs.deletePersonInfo(js0100,sess);
			
			List<JsAtt> jsattList = sess.createQuery("from JsAtt where js0100='"+js0100+"'").list();
			for(JsAtt ja : jsattList){
				String directory = disk+ja.getJsa07();
				File f = new File(directory+ja.getJsa00());
				if(f.isFile()){
					f.delete();
				}
				sess.delete(ja);
				
			}
			
			//ɾ����ϸ
			String sql = "delete from YJMX where JS0100 = :js0100";
			SQLQuery query = sess.createSQLQuery(sql);
			query.setString("js0100", js0100);
			query.executeUpdate();
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("deleteGridJsonStoreRecord('"+js0100+"');");
			this.setNextEventName("gridcq.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.toastmessage("��Ա��ɾ����");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ������������Ϣ��
	 * @param a
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("updateNRM")
	public int updateNRM(String a) throws RadowException, AppException{
		String rbId = this.getPageElement("rbId").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			bs.setPm(this);
			bs.updateNRM(rbId,sess);
			
			sess.flush();
			this.getExecuteSG().addExecuteCode("Ext.getCmp('updateNRM').addClass('bh');");
			this.setNextEventName("gridcq.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.toastmessage("������Ϣ�Ѹ��¡�");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ɾ��������Ա���ڼ�¼	
	 * @param js0100
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("hjDelete")
	public int hjDelete(String js0100) throws RadowException, AppException{
		String cur_hj = this.getPageElement("cur_hj").getValue();
		String cur_hj_4 = this.getPageElement("cur_hj_4").getValue();
		cur_hj = RMHJ.getRealCurHJ(cur_hj, cur_hj_4);
		RMHJ.HjDelSqlMap sm = RMHJ.getHjDelSqlMap(cur_hj, cur_hj_4);
		try {
			if(!"".equals(sm.mainMessage)){
				this.setMainMessage(sm.mainMessage);
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBUtil.executeUpdate("delete from js_hj where js0100 =? and js_type like ?||'%'",new Object[]{js0100,cur_hj});
			//�������������в����ڸ���Ա�ˣ���ɾ��js01
			List list = HBUtil.getHBSession().createSQLQuery("select 1 from js_hj where js0100 =?").setString(0, js0100).list();
			if(list.size()==0){
				allDelete(js0100);
			}
			if(!"".equals(sm.javascript)){
				this.getExecuteSG().addExecuteCode(sm.javascript);
			}
			this.toastmessage("��Ա���Ƴ���");
			this.setNextEventName("gridcq.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �б������𱣴�
	 * @param str
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("savedata")
	public int savedata(String str) throws RadowException, AppException{
		String rbId = this.getPageElement("rbId").getValue();
		String[] s = str.split("@@@");
		String js0100;
		String dc003;
		if(s.length==1){
			 js0100 = s[0];
			 dc003 = "";
		}else{
			 js0100 = s[0];
			 dc003 = s[1];
		}
		
		String cur_hj = this.getPageElement("cur_hj").getValue();
		String dc005 = this.getPageElement("dc005").getValue();

		RMHJ.SavedSqlMap sm = RMHJ.getSavedSqlMap(cur_hj, dc005);
		
		if(dc003==null||"".equals(dc003)){
			String sql1 ="update "+sm.table+" set "+sm.field+" = null where  js0100 = '"+js0100+"'"+sm.where;
			HBUtil.executeUpdate(sql1);
			this.toastmessage("�ѱ��档");
			return 0;
		}else{
			String sql = "select dc001 from deploy_classify where dc001 = '"+dc003+"' and rb_id = '"+rbId+"'";

			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>>  list=cq.getListBySQL(sql);
			if(list.size()==0){
				
				this.setMainMessage("��ѡ����ȷ����𣡣���");
				return 0 ;	
			}else{
				HashMap<String, Object> map=list.get(0);
				String dc001 = (String) map.get("dc001");	
				String sql1 ="update "+sm.table+" set "+sm.field+" = '"+dc001+"' where  js0100 = '"+js0100+"'"+sm.where;
				HBUtil.executeUpdate(sql1);
			}				
			this.toastmessage("�ѱ��档");
			return 0 ;		
		}
			
	}
	
		
		
		
	
		
		
		
		/**
		 * ��Ϣ����
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("ExpGird")
		public int ExpGird(String param) throws RadowException{
			QCJSFileExportBS fileBS = new QCJSFileExportBS();
			//String rbId = this.getPageElement("rbId").getValue();			
			String rbId = this.request.getParameter("rbId");//����
			String cur_hj = this.request.getParameter("cur_hj");//����
			String cur_hj_4 = this.request.getParameter("cur_hj_4");//���۾����ֻ���
			String dc005 = this.request.getParameter("dc005");
			String buttontext = this.request.getParameter("buttontext");
			
			this.request.setAttribute("rbId", rbId);
			this.request.setAttribute("cur_hj", cur_hj);
			this.request.setAttribute("cur_hj_4", cur_hj_4);
			this.request.setAttribute("dc005", dc005);
			
			
			String js0100s = this.request.getParameter("js0100s");
			String js0100WhereSql="";
			if(!"".equals(js0100s)){
				js0100WhereSql = js0100s.substring(0,js0100s.length()-1).replace(",", "','");
				ExcelStyleUtil.js0100WhereSql = " and js01.js0100 in('"+js0100WhereSql+"')";
			}else{
				ExcelStyleUtil.js0100WhereSql = "";
			}
			
			fileBS.setPm(this);
			fileBS.setSheetName(buttontext);
			fileBS.setOutputpath(JSGLBS.HZBPATH+"zhgboutputfiles/"+rbId+"/");
			File f = new File(fileBS.getOutputpath());
			
			if(f.isDirectory()){//��������Ŀ¼
				JSGLBS.deleteDirectory(fileBS.getOutputpath());
			}
			String buttonid = this.request.getParameter("buttonid");
			if("btn0".equals(buttonid)){//�ɲ����佨�鷽��
				try {
					String sql = "select m.dc003 from ("+ExcelStyleUtil.getSql(cur_hj, cur_hj_4, rbId,dc005)+")m group by dc003";
					HBSession sess = HBUtil.getHBSession();
					List list  = sess.createSQLQuery(sql).list();
					String dc003 = list.get(0).toString();
					if(list.size()>1){
						this.setMainMessage("����һ�����ϵ�������޷�������");
						return 0;
					}else if(dc003.equals("����")){
						this.setMainMessage("�������Ϊ������");
						return 0;
					}else{
						fileBS.exportGBDYJY(dc003);
					}
					//fileBS.exportGBTP("1");
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn1".equals(buttonid)){//����ɲ�����
				try {
					fileBS.exportDYMD();
					//fileBS.exportJYRX();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn2".equals(buttonid)){//�ֹ����쵼��ͨ������
				try {
					fileBS.exportTQCL();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn17".equals(buttonid)){//�ֹ����쵼��ͨ�����ϣ�����ְ��
				try {
					fileBS.exportTQCLRMZ();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn3".equals(buttonid)){//���Ƽ����������ѡ����ʡ��ί�������
				try {
					fileBS.exportTJRX("���Ƽ����������ѡ����ʡ��ί�������.xls");
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn7".equals(buttonid)){//�����������������ܱ�
				fileBS.exportKCDX();
			}else if("btn8".equals(buttonid)){//����ɲ����佨�鷽��
				try {
					fileBS.exportBHGBTP();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn9".equals(buttonid)){//���ר���ɲ����佨�鷽��
				fileBS.exportSJHGBTP();
			}else if("btn10".equals(buttonid)){//��ί��ɲ����佨�鷽��
				fileBS.exportCWHGBTP();
			}else if("btn11".equals(buttonid)){//��ί����Ʊ
				try {
					fileBS.exportPJP();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn12".equals(buttonid)){//��ί����Ʊ���
				try {
					fileBS.exportPJQK();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn14".equals(buttonid)){//�ɲ�̸�����ŷ���
				try {
					this.request.setAttribute("dc005", RMHJ.TAN_HUA_AN_PAI);
					fileBS.exportTHAP();
				} catch (Exception e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}else if("btn16".equals(buttonid)){//��ʾ
				try {
					fileBS.exportGS();
				} catch (IOException e) {
					this.setMainMessage(e.getMessage());
					e.printStackTrace();
				}
			}
			ExcelStyleUtil.js0100WhereSql = "";
			
			List<String> fns = fileBS.getOutputFileNameList();
			String downloadPath = "";
			String downloadName = "";
			if(fns.size()==1){//һ���ļ�ֱ������
				downloadPath = fileBS.getOutputpath()+fns.get(0);
				downloadName = fns.get(0);
			}else if(fns.size()>1){//ѹ��
				downloadPath = fileBS.getOutputpath()+fileBS.getSheetName()+".zip";
				downloadName = fileBS.getSheetName()+".zip";
				Zip7z.zip7Z(fileBS.getOutputpath(), downloadPath, null);
				
			}else{
				return EventRtnType.NORMAL_SUCCESS;
			}
			String downloadUUID = UUID.randomUUID().toString();
			request.getSession().setAttribute(downloadUUID, new String[]{downloadPath,downloadName});
			this.getExecuteSG().addExecuteCode("document.getElementById('docpath').value='"+downloadUUID+"';");
			this.getExecuteSG().addExecuteCode("downloadByUUID();");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
}