package com.insigma.siis.local.pagemodel.xbrm.ldbj;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.commform.hibernate.HUtil;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.JsYjtj;
import com.insigma.siis.local.business.helperUtil.CommonSQLUtil;
import com.insigma.siis.local.pagemodel.cadremgn.util.SqlToMapUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class TPFNCDPageModel extends PageModel{

	/**
	 * ����ɱ༭�İ�ť
	 */
	//ѡ������
	public  String btnn1init[]={"btnn1","btnn2","btnn4"};//1.ѡ������ 2.���� 3.��
	//ѡ������
	public  String btnn1[]={"btnn5","btnn6"};//1.���� 2.����
	//��
	public  String btnn2[]={"btnn4","btnn1","btnn2"};//1.�� 2.ѡ������ 3.��
	//��
	public  String btnn3[]={"btnn5","btnn6"};//1.���� 2.����
	//��
	public  String btnn4[]={"btnn1","btnn2"};//1.ѡ������ 2.��
	//����
	public  String btnn5[]={"btnn1","btnn2"};//1.ѡ������  2.��
	//����
	public  String btnn6[]={"btnn1","btnn2"};//1.ѡ������ 2.��
	
	public HashMap<String , String[]> mapBtn=new HashMap<String, String[]>();
	@Override
	public int doInit() throws RadowException {
		//�������������У����水ť���ɱ༭
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(true);
		//�������������У�Ԥ����ť���ɱ༭
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(true);
		//ֵ��
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
//		this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		//�������
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
		
		this.setNextEventName("loadtable");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ֵ2 date
	 * @return
	 */
	@PageEvent("setValue2Disable")
	public int setValue2Disable(){
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);//
		this.createPageElement("conditionName61", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("setValue111Disable")
	public int setValue111Disable(){
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ֵ2 number
	 * @return
	 */
	@PageEvent("setValue211Disable")
	public int setValue211Disable(){
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);//
		this.createPageElement("conditionName6111", ElementType.BUTTON, false).setDisabled(false);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * ����ֵ2 select
	 * @return
	 * @throws AppException 
	 * @throws RadowException 
	 */
	@PageEvent("setValue21Disable")
	public int setValue21Disable(String code_type) throws RadowException, AppException{
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
		this.getExecuteSG().addExecuteCode("setTree2('"+code_type+"');");
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(false);//����
		//((Combo)this.getPageElement("conditionName71")).setValueListForSelect(getMap(code_type)); 
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initPage")
	public int initPageInfo() throws RadowException, AppException{
		String qvid=this.getPageElement("qvid").getValue();
		if(qvid!=null&&!qvid.trim().equals("")&&!qvid.trim().equals("null")){
			CommQuery cq=new CommQuery();
			//String txtareaarr="";//�洢����������� ������ʾ����
			String sql=""
					+ "select "
					+ " 1 tyle,"//
					+ " quid, "//	����
					+ " qvid, "//	��ͼid or ����ֶ� ctci
					+ " sort, "//	����˳��
					+ " fldname, "//	�ֶ���
					+ " fldcode, "//	����
					+ " tblname, "//	����
					+ " valuename1, "//	ֵһ������
					+ " valuecode1, "//	ֵһ����
					+ " lable_type code_type, "//	�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
					+ " valuecode2, "//	ֵ������
					+ " valuename2, "//	ֵ��������
					+ " q.sign, "//	����
					+ " (select code_name from code_value where code_type='OPERATOR' and code_value=q.sign) signname "
					+ " from JS_YJTJ_use q where qvid='"+qvid+"' " + " order by sort asc " ;
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);//3������������Ϣ
			
			String zhtj=HBUtil.getValueFromTab("JS_YJTJ.conditions", "JS_YJTJ", "qvid='"+qvid+"' ");// 1.����. 2.����. 3
			this.getExecuteSG().addExecuteCode("cleanInfo();");
			this.getPageElement("conditionName9").setValue(zhtj);
			this.getPageElement("zhtj").setValue(zhtj);
			
			if(list!=null&&list.size()>0){
				String jsonstr=JSONArray.fromObject(list).toString();
				//jsonstr=jsonstr.replace("\'", "$");
				this.getExecuteSG().addExecuteCode("initPageInfo('"+jsonstr+"');");
			}else{
				this.setNextEventName("initListAndButton");
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initListAndButton")
	public int initListAndButton() throws RadowException, AppException{
		String qvid=this.getPageElement("qvid").getValue();
		if(qvid!=null&&!qvid.trim().equals("")&&!qvid.trim().equals("null")){
			//��ʼ���б� -- ��Ϣ��
			String sql=" select tblname from ("
					+ " select tblname from js_yjtj_fld where qvid='"+qvid+"'"
					+ " union all"
					+ " select tblname from JS_YJTJ_use where qvid='"+qvid+"' "
					+ " ) tt group by tblname ";
			CommQuery cq=new CommQuery();
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			String tableInfos="";
			for(int i=0;list!=null&&i<list.size();i++){
				tableInfos=tableInfos+list.get(i).get("tblname")+",";
			}
			//��ʼ���б� -- ��Ϣ�� ����ʽA01,A0101@A01,A0104@A01,A0107@A01,A0111@A01,A0114@��
			List<HashMap<String, Object>> listfld=cq.getListBySQL("select tblname,fldname from js_yjtj_fld where qvid='"+qvid+"'");
			String flds="";
			for(int i=0;listfld!=null&&i<listfld.size();i++){
				flds=flds+listfld.get(i).get("tblname")+","+listfld.get(i).get("fldname")+"@";
			}
			if(!"".equals(tableInfos)){
				tableInfos=tableInfos.substring(0, tableInfos.length()-1);
				flds=flds.substring(0, flds.length()-1);
				this.getExecuteSG().addExecuteCode("initGridChecked('"+tableInfos+"','"+flds+"');");
			}
			//��ʼ����ť�Ƿ�ɱ༭
		}
		String conditionName9=this.getPageElement("conditionName9").getValue();
		if(conditionName9==null||conditionName9.trim().equals("")||conditionName9.trim().equals("null")){
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(true);
			
			this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
			this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(false);
			this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(false);
		}
		String querysql=HBUtil.getValueFromTab("qrysql", "JS_YJTJ", "qvid='"+qvid+"'");
		if(querysql!=null&&!querysql.trim().equals("")&&!querysql.trim().equals("null")){
			this.getPageElement("querysql").setValue(querysql);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	//ѡ��is null ���� is not null
	@PageEvent("setValue1And2Disable")
	public int setValue1And2Disable() throws RadowException{
		clearValue1And2();//���ֵ һ  ֵ��
		//this.createPageElement("conditionName6", ElementType.SELECT, false).setDisabled(true);
		//this.createPageElement("conditionName61", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("conditionName611", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName6111", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);
		return EventRtnType.NORMAL_SUCCESS;
	}
	//���ֵ һ  ֵ��
	public void clearValue1And2() throws RadowException{
		this.getPageElement("conditionName6").setValue("");
		this.getPageElement("conditionName6_combotree").setValue("");
		this.getPageElement("conditionName61").setValue("");
		this.getPageElement("conditionName611").setValue("");
		this.getPageElement("conditionName6111").setValue("");
		this.getPageElement("conditionName7").setValue("");
		this.getPageElement("conditionName71").setValue("");
		this.getPageElement("conditionName71_combotree").setValue("");
		this.getPageElement("conditionName711").setValue("");
	}
	/**
	 * ���ѡ��������
	 * ���ض�Ӧ��ť
	 * 1.ѡ������
	 * 2.��
	 * 3.��
	 * 4.��
	 * ��ʾ��Ӧ��ť
	 * 5.����
	 * 6.����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("setDisSelect")
	public int setDisSelect(String arrid) throws RadowException{
		try{
			String id="";
			String arrstr[]=arrid.split(",");
			id=arrstr[0];
			int leftBrakets=Integer.parseInt(arrstr[1]);
			int rightBrakets=Integer.parseInt(arrstr[2]);
			id=arrstr[0];
			//���ÿɱ༭��ť
			if(mapBtn==null||mapBtn.size()==0){
				mapBtn.put("btnn1", btnn1);
				mapBtn.put("btnn2", btnn2);
				mapBtn.put("btnn3", btnn3);
				mapBtn.put("btnn4", btnn4);
				mapBtn.put("btnn5", btnn5);
				mapBtn.put("btnn6", btnn6);
			}
			String arr[]=mapBtn.get(id);
			for(int i=0;i<arr.length;i++){
				this.createPageElement(arr[i], ElementType.BUTTON, false).setDisabled(false);
			}
//			this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//ѡ������
//			this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//��
//			this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
//			this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
//			this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(false);//����
//			this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(false);//����
			//���ò��ɱ༭��ť
			if("btnn1".equals(id)){
				//"btnn5","btnn6"};//1.���� 2.����
				if(leftBrakets==rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
				}
				if(leftBrakets>rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false);//��
				}
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//ѡ������
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//��
			}else if("btnn2".equals(id)){
				//"btnn4","btnn1","btnn2"};//1.�� 2.ѡ������ 3.��
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
			}else if("btnn3".equals(id)){
				//"btnn5","btnn6"};//1.���� 2.����
				if(leftBrakets>rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(false);//��
				}
				if(leftBrakets==rightBrakets){
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
				}
				this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(true);//ѡ������
				this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
			}else if("btnn4".equals(id)){
					//"btnn1","btnn2"};//1.ѡ������ 2.��
					this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
					this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
					this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
					this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
			}else if("btnn5".equals(id)){
				//"btnn1","btnn2"};//1.ѡ������  2.��
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
			}else if("btnn6".equals(id)){
				//"btnn1","btnn2"};//1.ѡ������ 2.��
				this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(true);//��
				this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
				this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
			}
			setAllDis();//����ȫ��ɾ����ť�����ɱ༭
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��������
	 * ���ض�Ӧ��ť
	 * 1.��
	 * 2.����
	 * 3.����
	 * ��ʾ��Ӧ��ť
	 * 4.ѡ������
	 * 5.��
	 * 6.��
	 * @return
	 */
	@PageEvent("refreshDis")
	public int refreshDis(){
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);//��
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);//����
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);//����
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false);//��
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false);//��
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false);//ѡ������
		//����ȫ��ɾ����ť���ɱ༭
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���ñ���Ԥ���ɱ༭
	 * @return
	 */
	@PageEvent("setDisalbe")
	public int setDisalbe(){
		//�������������У����水ť�ɱ༭
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		//�������������У�Ԥ����ť�ɱ༭
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������Ϣ���б�
	 * @return
	 */
	@PageEvent("loadtable")
	public int loadtable() throws RadowException{
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr= " and table_code in (select t.table_code from competence_usertable t where t.userid = '"+userid+"')";
		}
		try{
			CommQuery cq=new CommQuery();
			String sql=""
					+ " select "
					+ " table_code tblcod,"//
					+ " concat(concat(table_code,' '),table_name) tblcpt "//
					+ " from code_table"
					+ " where 1=1 "
					+ instr
					+ " order by table_code asc ";
			List<HashMap<String, Object>> list=cq.getListBySQL(sql);
			this.getPageElement("tableList2Grid").setValueList(list);
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ֵ1 ����ѡ����ֵ
	 * @returnreplaceSpecial
	 * @throws RadowException 
	 */
	@PageEvent("selectValue1List")
	public int selectValue1List(String code_type) throws RadowException{
		try{
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			//((Combo)this.getPageElement("conditionName6")).setValueListForSelect(getMap(code_type)); 
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public Map<String, Object> getMap(String code_type) throws AppException{
		String sql="";
		CommQuery commQuery = new CommQuery();
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		if(code_type!=null&&"b01".equals(code_type.toLowerCase())){//��������ȡ��b01
			sql="select t.b0114,t.b0101,t.b0111 from b01 t "
					//+ " where  t.b0111='"+code_type.trim()+"' "
					+ " order by t.b0111 asc ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			for(int i=0;i<list2.size(); i++){
				Object b0111=list2.get(i).get("b0111");
				if(b0111==null)b0111="";
				Object b0114=list2.get(i).get("b0114");
				if(b0114==null)b0114="";
				Object b0101=list2.get(i).get("b0101");
				if(b0101==null)b0101="";
				map.put(b0111.toString(),b0114.toString()+" "+b0101);
			}
		}else{
			sql="select t.code_value,t.code_name from CODE_VALUE t "
					+ " where  t.code_type='"+code_type.trim()+"' "
					+ " order by t.code_value asc ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			for(int i=0;i<list2.size(); i++){
				map.put(replaceSpecial(list2.get(i).get("code_value").toString()),replaceSpecial(list2.get(i).get("code_value").toString())+" "+replaceSpecial(list2.get(i).get("code_name").toString()));
			}
		}
		return map;
	}
	
	public static String replaceSpecial(String str){
		String temp=str
		.replace("\"", "")  
		.replace("\'", "")  
		.replace("\\", "")
		.replace("/", "")
		.replace("\n", "")  
		.replace("\r", "")
//		.replace(",", "")
//		.replace("��", "")
		;
		return temp;
	}
	
	@PageEvent("modifyTable")
	public int modifyTable(){
		//�����Ϣ
		this.setNextEventName("clearValue");
		//������Ϣ
		this.setNextEventName("tabletofld");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 5��->num=4;
	 */
	//ʵ��ѡ��Ԥѡ��
	@PageEvent("checkClickCode")
	public int checkClickCode(String num) throws RadowException{
		checkCode(num,false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("checkClickCodeAll")
	public int checkClickCodeAll() throws RadowException{
		checkCode("",true);
		return 1;
	}
	public int checkCode(String num,boolean flag ) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//δѡ��
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(flag==false){
				String n=num;
				if(null==n||"0".equals(n)){n="0";}//�����grid ��һ�з���null
				int rownum = Integer.parseInt(n);
				HashMap<String,Object> hashMap1 = listCode.get(rownum);
				hashMap1.put("weizhi", n);
				listCode.remove(Integer.parseInt(n));//δѡ�� -- �Ƴ�
				listCode1.add(hashMap1);//Ԥѡ�� -- ����
		}else{
			int size=listCode.size();
			for(int i=0;i<size;i++){
				HashMap<String,Object> hashMap1 = listCode.get(i);
				hashMap1.put("weizhi", i);
				listCode1.add(hashMap1);//Ԥѡ�� -- ����
			}
			for(int i=(size-1);i>=0;i--){
				listCode.remove(i);//δѡ�� -- �Ƴ�
			}
		}
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return 1;
	}
	
	//ʵ���Ƴ�Ԥѡ��
	@PageEvent("delThisOne")
	public int delThisOne(String num) throws RadowException{
		Grid gridCode = (Grid) this.getPageElement("codeList2Grid");//δѡ��
		List<HashMap<String, Object>> listCode = gridCode.getValueList();
		Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
		List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
		if(null==num||"0".equals(num)){num="0";}//�����grid ��һ�з���null
		int rownum = Integer.parseInt(num);
		HashMap<String,Object> hashMap1 = listCode1.get(rownum);
		String weizhi = (String)hashMap1.get("weizhi");
		listCode1.remove(Integer.parseInt(num));//Ԥѡ��  -- �Ƴ�
		listCode.add(hashMap1);//δѡ��-- ���������
		this.getPageElement("codeList2Grid").setValueList(listCode);
		this.getPageElement("codeList2Grid1").setValueList(listCode1);
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	
	/**
	 * ����ѡ����Ϣ������ѯ��Ϣ��
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("tabletofld")
	public int tabletofld() throws RadowException{
		String qvid=this.getPageElement("qvid").getValue();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and col_code in ( select t.col_code from COMPETENCE_USERTABLECOL t where t.userid = '"+userid+"' ) ";
		}
		try{
			String cueUserid = PrivilegeManager.getInstance().getCueLoginUser().getId();//��ǰ�û�ID
			String parenttablename=this.getPageElement("parenttablename").getValue();
			String flds = this.getPageElement("flds").getValue();//��ȡ��ѡ��Ϣ������Ϣ�� ����ʽA01,A0101@A01,A0104@A01,A0107@A01,A0111@A01,A0114@��
			String[] split = flds.split("@");
			String[] splitnull = null;
			String col_code = "'1'";//�Ա������Ϣ��
			if(split.length==0||(split.length==1&&flds.length()==0)){
				
			}else{
				for (String s : split) {
					splitnull = s.split(",");
					col_code = col_code + "'" + splitnull[1] + "',";
				}
				col_code=col_code.substring(0, col_code.length()-1).replace("'1'", "");
			}
			CommQuery cq=new CommQuery();
			if(parenttablename==null||parenttablename.trim().equals("")||parenttablename.equals("null")){
				Grid grid = (Grid)this.getPageElement("tableList2Grid");
				List<HashMap<String, Object>> list=grid.getValueList();
				if(list==null||list.size()==0){
					this.setMainMessage("�����쳣����ⲻ����Ϣ��!");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String str="";
				for(int i=0;i<list.size();i++){
					if("true".equals(list.get(i).get("checked")+"")){
						str=str+"'"+list.get(i).get("tblcod").toString().trim()+"'"+",";
					}
				}
				if("".equals(str)){//��ѡ�б�ʱ
					String sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"//
							+ " col_name,"
							+ " code_type,"//ָ����
							+ " col_data_type_should,"//
							+ " col_data_type,"//�ൺ��Ӧ����������
							+ " col_code,"//ָ�����Ӧ�ı�Ķ�Ӧ��
							+ " table_code "//��Ϣ�����,��������CODE_TABLE
							+ " from code_table_col "
							+ " where "
							/*+ " ISUSE=1 and "*/
							/*�ൺ�ض����ݿ�
							 * + " ISLOOK='1' and "*/
							+ " 1=2 order by table_code , col_code asc ";
					List<HashMap<String, Object>> listfld=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid").setValueList(listfld);
					sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"//
							+ " col_name,"
							+ " code_type,"//ָ����
							+ " col_data_type_should,"//
							+ " col_data_type,"//�ൺ��Ӧ����������
							+ " col_code,"//ָ�����Ӧ�ı�Ķ�Ӧ��
							+ " table_code "//��Ϣ�����,��������CODE_TABLE
							+ " from code_table_col "
							+ " where 1<>1";
					List<HashMap<String, Object>> listfld2=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid1").setValueList(listfld2);
					return EventRtnType.NORMAL_SUCCESS;
				}else{
					//���ﴦ���������֣�Ԥѡ��Ϣ����δѡ��Ϣ��
					str=str.substring(0, str.length()-1);
					
				
					if(!flds.isEmpty()){
						String sqlnew=" select concat(concat(concat(concat(c.table_code,'.'),c.col_code),' '),c.col_name) col_name1,"
								+ " c.col_name, c.code_type, c.col_data_type_should, c.col_code, c.col_data_type,"
								+ " c.table_code, q.FLDNUM from js_yjtj_fld q left join code_table_col c  on c.COL_CODE=q.FLDNAME"
								+ " where 1 = 1 and "
								/*+ " ISUSE = 1 and "*/
								/*�ൺ�ض����ݿ�
								 * + " ISLOOK='1' and "*/
								+ " c.table_code in ("+str+") and q.QVID='"+qvid+"' " + instr
								+ " order by q.fldnum asc";
						List<HashMap<String, Object>> listfldnew=cq.getListBySQL(sqlnew);
						this.getPageElement("codeList2Grid1").setValueList(listfldnew);
					}else{//�������/ȥ����Ϣ��ʱ��ȥ����ѡ��Ϣ��
						Grid gridCode1 = (Grid) this.getPageElement("codeList2Grid1");//Ԥѡ��
						List<HashMap<String, Object>> listCode1 = gridCode1.getValueList();
						if(str.length()>2){
							String splTables = str.replace("'", " ");//ע������ʹ�ÿո�ָ�
							for (int i = 0; i < listCode1.size(); i++) {
								if(splTables.indexOf(" "+(String)listCode1.get(i).get("table_code").toString()+" ")>-1){
									col_code = col_code + "'" + listCode1.get(i).get("col_code") + "',";
								}else{
									listCode1.remove(i);
									continue;
								}
							}
							this.getPageElement("codeList2Grid1").setValueList(listCode1);
						}
						if(col_code.length()>3){
							col_code=col_code.substring(0, col_code.length()-1).replace("'1'", "");
						}
					}
					
					
					/* δѡ�� */
					String sqlold="select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"
							+ " col_name,code_type, "
							+ " col_data_type_should,col_code,"
							+ " col_data_type,"//�ൺ��Ӧ����������
							+ " table_code from code_table_col "
							+ " where 1=1 and "
							/*+ " ISUSE=1 and "*/
							/* �ൺ�ض����ݿ��ֶ�
							 * + " ISLOOK='1' and "*/
							+ " table_code in ("+str+") and "
							+ " col_code NOT IN  ("+col_code+") "
							+ instr + " order by table_code , col_code asc";
					List<HashMap<String, Object>> listfldold=cq.getListBySQL(sqlold);
					this.getPageElement("codeList2Grid").setValueList(listfldold);

				}
			}else{////��Ա��Ϣ�����޸� ���ܵ���
				//BatchChange
				String arr[]=parenttablename.split("@");
				if(arr.length>1){
					String changeOrDel=arr[0];//����ɾ�����޸�Ȩ��
					parenttablename=arr[1];//��ȡ��Ϣ��
					String sql="";
					if(parenttablename==null||parenttablename.trim().equals("")||parenttablename.equals("null")){
						sql="select * from dual where 1=2 ";
					}else{
						//if(("40288103556cc97701556d629135000f").equals(cueUserid)){
							//ϵͳ����Ա��ȫ��Ȩ��
							sql=" select concat(concat(concat(concat(table_code,'.'),col_code),' '),col_name) col_name1,"
									+ " col_name,code_type, "
									+ " col_data_type_should,"
									+ " col_data_type,"//�ൺ��Ӧ����������
									+ " col_code,table_code "
									+ " from code_table_col "
									+ " where "
									/*+ " ISUSE=1 and "*/
									/*�ൺ�ض����ݿ�
									 * + " ISLOOK='1' and "*/
									+ " table_code in ("+parenttablename+") order by table_code , col_code asc";
						/*}else{
							sql=" select concat(concat(concat(concat(ctc.table_code,'.'),ctc.col_code),' '),ctc.col_name) col_name1,"
									+ " ctc.col_name,ctc.code_type, "
									+ " ctc.col_data_type_should,"
									+ " ctc.col_data_type,"//�ൺ��Ӧ����������
									+ " ctc.col_code,ctc.table_code "
									+" from code_table_col ctc"
									+ " left join competence_usertablecol cul on  ctc.table_code=cul.table_code and ctc.col_code=cul.col_code  "
									+ " where "
									+ " ctc.ISLOOK='1' and "
									+ " cul."+changeOrDel+"='1' and cul.userid = '"+cueUserid+"'"
									+ "and ctc.table_code  in ("+parenttablename+") order by ctc.table_code , ctc.col_code asc";
						}*/
					}	
					List<HashMap<String, Object>> listfld=cq.getListBySQL(sql);
					this.getPageElement("codeList2Grid").setValueList(listfld);
				}else{
					//tab1û�й�ѡʱ�ÿ�tab2��ָ����
					this.getPageElement("codeList2Grid").setValueList(null);		
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 *�ı�����ֵ 
	 * ��like ��between and
	 * @return
	 * @throws AppException 
	 * @throws RadowException 
	 */
	@PageEvent("selectValueListNobt")
	public int selectValueListNobt() throws AppException, RadowException{
		conditionclear();//�������
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.code_value not like '%between%' "
				+ " and t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		this.getExecuteSG().addExecuteCode("setconditionName4();");//������ָ���ֵ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���ڣ���ֵ
	 * ��between and  ��like 
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("selectValueList")
	public int selectValueList() throws AppException, RadowException{
		try{
			conditionclear();//�������
			CommQuery commQuery = new CommQuery();
			String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where t.code_value not like '%like%' "
					+ " and t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<list2.size(); i++){
				map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
			}
			((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
			
			this.getExecuteSG().addExecuteCode("setconditionName4();");//������ָ���ֵ
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ������������ѡ����ֵ
	 * ��between and  ��like 
	 * @return
	 * @throws AppException
	 * @throws RadowException
	 */
	@PageEvent("selectValueListLike")
	public int selectValueListLike() throws AppException, RadowException{
		try{
			conditionclear();//�������
			CommQuery commQuery = new CommQuery();
			String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR'";
			List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			for(int i=0;i<list2.size(); i++){
				map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
			}
			((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
			
			this.getExecuteSG().addExecuteCode("setconditionName4();");//������ָ���ֵ
		}catch(Exception e){
			e.printStackTrace();
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ѡ�����������ֶΣ�������ѡ�ؼ���ֵ
	 * ��between and  ��like 
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("code_type_value1")
	public int code_type_value1(String code_type) throws AppException, RadowException{
		conditionclear();//�������
		//�������
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		selectValue1List(code_type);//ֵһ
		this.getExecuteSG().addExecuteCode("setconditionName4();");//������ָ���ֵ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**���� 
	 * 
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("selectValueListLikeBt")
	public int selectValueListLikeBt() throws RadowException, AppException{
		conditionclear();//�������
		//�������
		CommQuery commQuery = new CommQuery();
		String sql="select t.code_value,concat(concat(t.code_value,'             '),t.code_name) code_name from CODE_VALUE t "
				+ " where t.sub_code_value!='11' "
				+ " and t.code_type='OPERATOR' ";
		List<HashMap<String, Object>> list2=commQuery.getListBySQL(sql);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for(int i=0;i<list2.size(); i++){
			map.put(list2.get(i).get("code_value").toString(),list2.get(i).get("code_name"));
		}
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		
		this.getExecuteSG().addExecuteCode("setconditionName4();");//������ָ���ֵ
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/**
	 * �������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("conditionclear")
	public int conditionclear() throws RadowException{
		this.getPageElement("conditionName4").setValue("");//����ָ����
		this.getPageElement("conditionName5").setValue("");//��������
		this.getPageElement("conditionName6").setValue("");//ֵһ
		this.getPageElement("conditionName6_combotree").setValue("");//ֵһ
		this.getPageElement("conditionName61").setValue("");//ֵһ
		this.getPageElement("conditionName611").setValue("");//ֵһ
		this.getPageElement("conditionName6111").setValue("");//ֵһ
		this.getPageElement("conditionName7").setValue("");//ֵ��
		this.getPageElement("conditionName71").setValue("");//ֵ��
		this.getPageElement("conditionName71_combotree").setValue("");//ֵ��
		this.getPageElement("conditionName711").setValue("");//ֵ��
		this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(true);//����
		this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=true; ");
		//this.createPageElement("conditionName71", ElementType.SELECT, false).setDisabled(true);//����
		this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(true);//����
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 1.��ӵ��б� ��ʾ����
	 * 2.�������� ����
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("addtolistfunc")
	public int addtolistfunc() throws RadowException, AppException{

		//ָ�������
		String col_code=this.getPageElement("col_code").getValue();
		//����
		String table_code=this.getPageElement("table_code").getValue();
		
		//ֵһ conditionName6 select
		//String conditionName6=this.getPageElement("conditionName6").getValue();
		//ֵһ conditionName61 dateEdit
		//String conditionName61=this.getPageElement("conditionName61").getValue();
		//ֵһconditionName611 textEdit 
		//String conditionName611=this.getPageElement("conditionName611").getValue();
		//ֵһconditionName6111 numberEdit
		//String conditionName6111=this.getPageElement("conditionName6111").getValue();
		//ֵ��conditionName7 dateEdit
		//String conditionName7=this.getPageElement("conditionName7").getValue();
		//ֵ��conditionName71 textEdit
		//String conditionName71=this.getPageElement("conditionName71").getValue();
		//ֵ��conditionName711 numberEdit
		//String conditionName711=this.getPageElement("conditionName711").getValue();
		String col_data_type_should=this.getPageElement("col_data_type_should").getValue();//�洢����������
		String code_type=this.getPageElement("code_type").getValue();//��������
		String col_data_type=this.getPageElement("col_data_type").getValue();//��ʾ�Ŀؼ�����
		if(col_data_type_should==null||col_data_type_should.trim().length()==0){
			this.setMainMessage("��˫��ָ���");
			return EventRtnType.NORMAL_SUCCESS;
		}
		col_data_type_should=col_data_type_should.toLowerCase();
		String conditionName4=this.getPageElement("col_name").getValue();//���ص�ָ���� �������� 
		//���ص�ָ���� ����
		String conditionName5=this.getPageElement("conditionName5").getValue();//��������
		if(conditionName5==null||conditionName5.trim().equals("")||"null".equals(conditionName5.trim())){
			this.setMainMessage("��ѡ����������!");
			return EventRtnType.NORMAL_SUCCESS;
		}
		//�������� ����
		String conditionName51=HUtil.getValueFromTab("code_name", "code_value", "code_type='OPERATOR' and code_value='"+conditionName5+"' ");
		String value1="";
		String value11="";
		String value2="";
		String value21="";
		String qryusestr="";//�����ѯ������¼
		if(code_type!=null&&!code_type.trim().equals("")&&!code_type.equals("null")){//ֵһ ����
			value1=this.getPageElement("conditionName6").getValue();
			value2=this.getPageElement("conditionName71").getValue();
			if("b01".equals(code_type.toLowerCase())){
				value11=HUtil.getValueFromTab("b0101", "b01", "b0111='"+value1+"'");
				value21=HUtil.getValueFromTab("b0101", "b01", "b0111='"+value2+"'");
			}else{
				value11=HUtil.getValueFromTab("code_name", "code_value", "code_type='"+code_type.toUpperCase()+"' and code_value='"+value1+"'");
				value21=HUtil.getValueFromTab("code_name", "code_value", "code_type='"+code_type.toUpperCase()+"' and code_value='"+value2+"'");
			}
			if(conditionName5.indexOf("between")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+value11//ֵһ������
						+","+value1//ֵһ����
						+","+"S"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+value2//ֵ������
						+","+value21//ֵ��������
						+","+conditionName5//����
						;
			}else if(conditionName5.indexOf("null")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+""//ֵһ����
						+","+"S"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else{
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+value11//ֵһ������
						+","+value1//ֵһ����
						+","+"S"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}
			
		}else if("date".equals(col_data_type_should.toLowerCase())||"t".equals(col_data_type)){//ֵһ date
			value2=this.getPageElement("conditionName7").getValue();
			value1=this.getPageElement("conditionName61").getValue();
			if(conditionName5.indexOf("between")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+value1//ֵһ����
						+","+"T"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+value2//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else if(conditionName5.indexOf("null")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+""//ֵһ����
						+","+"T"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else{
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+" "//ֵһ������
						+","+value1//ֵһ����
						+","+"T"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+" "//ֵ������
						+","+" "//ֵ��������
						+","+conditionName5//����
						;
			}
			
		}else if(col_data_type_should==null
				||col_data_type_should.equals("clob")
				||col_data_type_should.equals("varchar2")
				||col_data_type_should.equals("null")
				||col_data_type_should.trim().equals("")){//ֵһ  �ı�
			value1=this.getPageElement("conditionName611").getValue();
			if(conditionName5.indexOf("null")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+""//ֵһ����
						+","+"C"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else{
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+value1.replace(",", "").replace("@", "")//ֵһ����
						+","+"C"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}
		}else if("number".equals(col_data_type_should)){//number
			value2=this.getPageElement("conditionName711").getValue();
			value1=this.getPageElement("conditionName6111").getValue();
			if(conditionName5.indexOf("between")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code.replace(",", "")//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+value1//ֵһ����
						+","+"N"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+value2//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else if(conditionName5.indexOf("null")!=-1){
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code.replace(",", "")//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+""//ֵһ����
						+","+"N"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}else{
				qryusestr=conditionName4.replace(",", "")//�ֶ���
						+","+col_code.replace(",", "")//����
						+","+table_code//����
						+","+""//ֵһ������
						+","+value1//ֵһ����
						+","+"N"//�ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
						+","+""//ֵ������
						+","+""//ֵ��������
						+","+conditionName5//����
						;
			}
		}
		this.getPageElement("qryusestr").setValue(qryusestr);
		if(code_type!=null&&!code_type.trim().equals("")&&!code_type.equals("null")){
			if(conditionName5.indexOf("between")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value11+" , "+value21);
			}else if(conditionName5.indexOf("null")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"");
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value11);
			}
		}else{
			if(conditionName5.indexOf("between")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value1+" , "+value2);
			}else if(conditionName5.indexOf("null")!=-1){
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"");
			}else{
				this.getPageElement("conditoionlist").setValue(conditionName4+"  "+conditionName51+"  "+value1);
			}
		}
		this.getExecuteSG().addExecuteCode("textareadd();");//��ӵ��б�
		//conditionStr
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����ȫ��ɾ����ť�����ɱ༭
	 * @return
	 */
	@PageEvent("setAllDis")
	public int setAllDis(){
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(true);//
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(true);//
		this.createPageElement("editbtnm", ElementType.BUTTON, false).setDisabled(true);//
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �������ñ���
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("saveFunc")
	public int saveFunc(String v) throws RadowException{
		Statement  stmt =null;
		Connection connection =null;
		String qvid="";
		String qvsql="";
		String paramurl="";//�Զ����ѯ����
		try{
			paramurl=this.getPageElement("paramurl").getValue();
			qvid=this.getPageElement("qvid").getValue();
			
			qvsql=HBUtil.getValueFromTab("qrysql", "JS_YJTJ", "qvid='"+qvid+"'");
			
			Grid gridCode = (Grid)this.getPageElement("codeList2Grid");//δѡ����
			List<HashMap<String, Object>> listCode=gridCode.getValueList();
			Grid gridCode1 = (Grid)this.getPageElement("codeList2Grid1");//Ԥѡ����
			List<HashMap<String, Object>> listCode1=gridCode1.getValueList();
			if((listCode==null&&listCode1==null)||(listCode.size()==0&&listCode1.size()==0)){
				this.setMainMessage("��ѡ����Ϣ��!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			if(listCode1==null||listCode1.size()==0){
				this.setMainMessage("��ѡ����Ϣ��!");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//�������
			String zhtj=this.getPageElement("zhtj").getValue();
		
			String qrysql=getSql();//����sql
			//����sql��Ӧ����ͼ
			HBSession hbsess = HBUtil.getHBSession();	
			connection = hbsess.connection();
			stmt =connection.createStatement();
			try{
				String tmepsql=qrysql+ " and 1=2 ";
				stmt.executeQuery(tmepsql);
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
				this.setMainMessage("���ɵ�����﷨����������ӵ�����! "+e.getMessage());
				return EventRtnType.NORMAL_SUCCESS;
			}finally{
				try{
					if(stmt!=null){
						stmt.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			//����ȫ�̼�ʵѡ��ʱ����Ա�⣬��ҵ���ʹ�õ�sql��
			String qrysql_2=getSql2();//����sql
			//����sql��Ӧ����ͼ
			connection = hbsess.connection();
			stmt =connection.createStatement();
			try{
				String tmepsql=qrysql_2+ " and 1=2 ";
				System.out.println(qrysql_2);
				stmt.executeQuery(tmepsql);
				stmt.close();
			}catch(SQLException e){
				e.printStackTrace();
				this.setMainMessage("���ɵ�����﷨����������ӵ�����! "+e.getMessage());
				return EventRtnType.NORMAL_SUCCESS;
			}finally{
				try{
					if(stmt!=null){
						stmt.close();
					}
					if(connection!=null){
						connection.close();
					}
				}catch(Exception e2){
					e2.printStackTrace();
				}
			}
			
			HBSession hbSession = HBUtil.getHBSession();
			JsYjtj jsYjtj=(JsYjtj)hbSession.get(JsYjtj.class, qvid);
			jsYjtj.setQrysql(qrysql);
			jsYjtj.setQrysql2(qrysql_2);
			jsYjtj.setConditions(zhtj);
			hbSession.update(jsYjtj);//����sql����ͼ����
			hbSession.flush();
			stmt =connection.createStatement();
			
			//������Ϣ��Զ�����ͼָ������� a0000,a0101 �����ֶα��뱣�棬��ѡ�У���Ĭ�ϱ���
			boolean a0000flag=false;
			boolean a0101flag=false;
			stmt.execute("delete from js_yjtj_fld where qvid='"+qvid+"' ");
			String col_code,table_code,col_name,sql,fldnum;
			for(int i=0;i<listCode1.size();i++){
				col_code=(String)listCode1.get(i).get("col_code");
				
				table_code=(String)listCode1.get(i).get("table_code");
				fldnum = (i+1) +"";
				if("a0000".equals(col_code.trim().toLowerCase())&&"a01".equals(table_code.trim().toLowerCase())){
					a0000flag=true;
					fldnum = "0";
				}
				if("a0101".equals(col_code.trim().toLowerCase())&&"a01".equals(table_code.trim().toLowerCase())){
					a0101flag=true;
					fldnum = "1";
				}
				col_name=(String)listCode1.get(i).get("col_name");
				//INSERT INTO employees (employee_id, name) VALUES (1, 'Zhangsan');  
				sql="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+table_code+"','"+col_code+"','"+col_name+"','"+fldnum+"')";
				stmt.execute(sql);
			}
			if(a0000flag==false){
				String sqlInsertA0000="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+"A01"+"','"+"A0000"+"','"+"��Աͳһ��ʶ��"+"','0')";
				stmt.execute(sqlInsertA0000);
			}
			if(a0101flag==false){
				String sqlInsertA0101="insert into js_yjtj_fld (qvfid,qvid,tblname,fldname,fldnamenote,fldnum ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+"A01"+"','"+"A0101"+"','"+"����"+"','0')";
				stmt.execute(sqlInsertA0101);
			}
			/*����������¼*/
			String allqryusestr=this.getPageElement("allqryusestr").getValue();
			String arrAt[]=null;//���м�¼
			if(allqryusestr!=null&&!allqryusestr.trim().equals("")){
				arrAt=allqryusestr.split("@");
			}
			stmt.execute("delete from JS_YJTJ_use where qvid='"+qvid+"' ");
			String arrone[];
			String sqlInsertQryuse;
			for(int i=0;arrAt!=null&&i<arrAt.length;i++){
				arrone=arrAt[i].split(",");
//				String uuid=UUID.randomUUID().toString();
				sqlInsertQryuse="insert into JS_YJTJ_use (quid,qvid,sort,fldname,fldcode,"
						+ "tblname,valuename1,valuecode1,lable_type,valuecode2,valuename2,sign ) "
						+ " values ("+CommonSQLUtil.UUID()+",'"+qvid+"','"+(i+1)+"','"+arrone[0]+"','"+arrone[1]+"'"
								+ ",'"+arrone[2]+"','"+arrone[3]+"','"+arrone[4]+"',"
										+ "'"+arrone[5]+"','"+arrone[6]+"','"+arrone[7]+"','"+arrone[8]+"')";
				stmt.execute(sqlInsertQryuse);
			}
			stmt.close();
			//connection.commit();
			connection.close();
			if(paramurl!=null&&paramurl.trim().length()>0&&!paramurl.equals("null")){//�Զ����ѯ����Ա��Ϣ��ѯtab��
				//����ɹ�������qvid����ҳ�棬��ҳ�����qvid�ж��Ƿ񱣴�
				this.getExecuteSG().addExecuteCode("parent.document.getElementById('qvid').value='"+qvid+"';");
			}
			if(qvsql==null||qvid.trim().equals("qvsql")||qvid.trim().equals("")){//������ͼ
					this.toastmessage("����ɹ�!");
					this.getExecuteSG().addExecuteCode("parent.refreshViewListGrid();");
				
				
			}else{
					this.toastmessage("�޸ĳɹ�!");
					this.getExecuteSG().addExecuteCode("parent.refreshViewListGrid();");
				
			}
		}catch(Exception e){
			e.printStackTrace();
			try{
	          if(stmt!=null){
	        	  stmt.close();
	          }
	          if(connection!=null){
	        	  connection.close();
	          }
			}catch(Exception e1){
				
			}
			
			throw new RadowException(e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	/**
	 * ��ҳ���ȡsql
	 * @parenthiddenid ��ҳ�����ص�id 
	 * @functionname�� ҳ��ķ�������
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("getSqlParent")
	public int getSqlParent(String strarr) throws RadowException{
		String arr[]=strarr.split(",");
		String parentid=arr[0];
		String functionname=arr[1];
		String sql=getSql();
		sql=sql.replace("\'", "@@");
		this.getExecuteSG().addExecuteCode("parent.document.getElementById('"+parentid+"').value='"+sql+"';"
				+"parent."+functionname+ "();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����������� ����sql 
	 * @return
	 * @throws RadowException
	 */
	public String getSql() throws RadowException{
		Grid gridCode = (Grid)this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode=gridCode.getValueList();
		Grid gridTable = (Grid)this.getPageElement("tableList2Grid");
		List<HashMap<String, Object>> listTable=gridTable.getValueList();
		
		
		/*���ɲ�ѯsql���浽�Զ����ѯ��ͼ����*/
		String sqlSelect="select ",tempTable,tempCode;
		for(int i=0;i<listCode.size();i++){
//			String checked=listCode.get(i).get("checked")+"";
//			if("true".equals(checked)){
			tempTable=(String)listCode.get(i).get("table_code");
			tempCode=(String)listCode.get(i).get("col_code");
			sqlSelect=sqlSelect+" "+tempTable+"."+tempCode+" "+tempTable+tempCode+",";
//			}
		}
		sqlSelect=sqlSelect.toLowerCase();
		if(sqlSelect.indexOf("a01a0000")<0){//û��ѡ��a0000��Ϣ��
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0000"+" a01a0000,";
		}
		if(sqlSelect.indexOf("a01a0101")<0){//û��ѡ��a0000��Ϣ��
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0101"+" a01a0101,";
		}
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		if(sqlSelect.toLowerCase().contains("a01.a0195")){
			sqlSelect = sqlSelect.toLowerCase().replace("a01.a0195", " (select b0101 from b01 where b0111=a01.A0195) ");
		}
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		sqlSelect=sqlSelect.substring(0, sqlSelect.length()-1)+" from ";
		
		String tabletemp="";//����
		for(int i=0;i<listTable.size();i++){
			String checked=listTable.get(i).get("checked")+"";
			if("true".equals(checked)){
				String temp=(String)listTable.get(i).get("tblcod");
				tabletemp=tabletemp+" "+temp.toLowerCase()+",";
			}
		}
		String arr[]=null;
		if(listTable==null||listTable.size()==0){//��Ա��Ϣ����ã���û�в�ѯ��Ϣ��
			String parenttablename=this.getPageElement("parenttablename").getValue();//��Ա��Ϣ�޸ģ����ص���ҳ���tab_code
	    	//BatchChange
			arr=parenttablename.split("@");
			parenttablename=arr[1];//��ȡ��Ϣ��
			parenttablename=parenttablename.replace("\'", "");
			tabletemp=tabletemp+" "+parenttablename.toLowerCase()+"  ";
		}
		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
		if(tabletemp.indexOf("a01")==-1){//a01 ����ѡ��
			tabletemp=tabletemp+",a01";
		}
		if(tabletemp!=null&&tabletemp.indexOf("b01")!=-1){//ѡ��b01�����ѡ��a02��ǰ̨��ѡ����sql�Զ����
//			if(tabletemp.indexOf("a01")==-1){
//				tabletemp=tabletemp+",a01";
//			}
			if(tabletemp.indexOf("a02")==-1){
				tabletemp=tabletemp+",a02";
			}
		}
		sqlSelect=sqlSelect+tabletemp;//ƴ�ӱ���
		
		sqlSelect=sqlSelect+" where 1=1 ";
		String tableid="";//�����
		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
			//for(int i=0;i<listTable.size();i++){
				//String checked=listTable.get(i).get("checked")+"";
				//if("true".equals(checked)){
			String arrt[]=tabletemp.split(",");
			for(int i=0;i<arrt.length;i++){
				String temp=arrt[i];
				temp=temp.trim();
				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
				}
			}
					
					
				//}
			//}
			String arr1[]=null;
			if(arr!=null){
				String temp=arr[1];
				temp=temp.replace("\'", "");
				arr1=temp.split(",");
				
			}
			for(int i=0;arr1!=null&&i<arr1.length;i++){
				String temp=arr1[i];
				if(!"a01".equals(temp.toLowerCase())&&!"b01".equals(temp.toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
				}else if("b01".equals(temp.toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
				}
			}
		}
//		if(tableid.indexOf("a01.a0000")==-1){//ǰ̨����ѡ����b01��Ϣ��
//			tableid=tableid+" and a01.a0000=a02.a0000 ";
//		}
		sqlSelect=sqlSelect+tableid;//ƴ�ӱ�����
		sqlSelect=sqlSelect+" and a01.status!='4' "//��������
				+ " ";
		String whereConditionStr=this.getPageElement("conditionStr").getValue();//�������
		String qrysql="";
		if(whereConditionStr!=null
				&&!whereConditionStr.trim().equals("")
				&&!whereConditionStr.trim().equals("null")){
			qrysql=sqlSelect+" and (" + whereConditionStr+") ";//����sql
		}else{
			qrysql=sqlSelect;
		}
		//�滻�����е������
		qrysql=qrysql.replace("{v}", "")
				.replace("{%v}", "")
				.replace("{v%}", "")
				.replace("{%v%}", "");
		//qrysql=qrysql.toLowerCase();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
		}
		qrysql=qrysql+instr;
		this.getPageElement("querysql").setValue(qrysql);//����sql��ǰ̨
		return qrysql;
	}
	
	/**
	 * ����������� ����sql 
	 * @return
	 * @throws RadowException
	 */
	public String getSql2() throws RadowException{
		Grid gridCode = (Grid)this.getPageElement("codeList2Grid1");
		List<HashMap<String, Object>> listCode=gridCode.getValueList();
		Grid gridTable = (Grid)this.getPageElement("tableList2Grid");
		List<HashMap<String, Object>> listTable=gridTable.getValueList();
		
		
		/*���ɲ�ѯsql���浽�Զ����ѯ��ͼ����*/
		String sqlSelect="select ",tempTable,tempCode;
		for(int i=0;i<listCode.size();i++){
//			String checked=listCode.get(i).get("checked")+"";
//			if("true".equals(checked)){
			tempTable=(String)listCode.get(i).get("table_code");
			tempCode=(String)listCode.get(i).get("col_code");
			sqlSelect=sqlSelect+" "+tempTable+"."+tempCode+" "+tempTable+tempCode+",";
//			}
		}
		sqlSelect=sqlSelect.toLowerCase();
		if(sqlSelect.indexOf("a01a0000")<0){//û��ѡ��a0000��Ϣ��
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0000"+" a01a0000,";
		}
		if(sqlSelect.indexOf("a01a0101")<0){//û��ѡ��a0000��Ϣ��
		sqlSelect=sqlSelect+" "+"a01"+"."+"a0101"+" a01a0101,";
		}
		sqlSelect=sqlSelect+" "+"a01"+"."+"v_xt"+" v_xt,";
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		if(sqlSelect.toLowerCase().contains("a01.a0195")){
			sqlSelect = sqlSelect.toLowerCase().replace("a01.a0195", " (select b0101 from V_js_b01 b01 where b0111=a01.A0195 and b01.v_xt=a01.v_xt) ");
		}
		/* �޸�ͳ�ƹ�ϵ���ڵ�λ - ��ֹ��ʾ�������� */
		sqlSelect=sqlSelect.substring(0, sqlSelect.length()-1)+" from ";
		
		String tabletemp="";//����
		for(int i=0;i<listTable.size();i++){
			String checked=listTable.get(i).get("checked")+"";
			if("true".equals(checked)){
				String temp=(String)listTable.get(i).get("tblcod");
				tabletemp = tabletemp+"V_js_"+temp.toLowerCase()+" "+temp.toLowerCase()+",";
			}
		}
		String arr[]=null;
		if(listTable==null||listTable.size()==0){//��Ա��Ϣ����ã���û�в�ѯ��Ϣ��
			String parenttablename=this.getPageElement("parenttablename").getValue();//��Ա��Ϣ�޸ģ����ص���ҳ���tab_code
	    	//BatchChange
			arr=parenttablename.split("@");
			parenttablename=arr[1];//��ȡ��Ϣ��
			parenttablename=parenttablename.replace("\'", "");
			tabletemp = tabletemp+"V_js_"+parenttablename.toLowerCase()+" "+parenttablename.toLowerCase()+",";
		}
		tabletemp=tabletemp.substring(0, tabletemp.length()-1);
		if(tabletemp.indexOf("a01")==-1){//a01 ����ѡ��
			tabletemp=tabletemp+","+"V_js_"+"a01 a01";
		}
		if(tabletemp!=null&&tabletemp.indexOf("b01")!=-1){//ѡ��b01�����ѡ��a02��ǰ̨��ѡ����sql�Զ����
//			if(tabletemp.indexOf("a01")==-1){
//				tabletemp=tabletemp+",a01";
//			}
			if(tabletemp.indexOf("a02")==-1){
				tabletemp=tabletemp+","+"V_js_"+"a02 a02";
			}
		}
		sqlSelect=sqlSelect+tabletemp;//ƴ�ӱ���
		
		sqlSelect=sqlSelect+" where 1=1 ";
		String tableid="";//�����
		if(tabletemp!=null&&!tabletemp.equals("null")&&tabletemp.trim().length()>0){
			//for(int i=0;i<listTable.size();i++){
				//String checked=listTable.get(i).get("checked")+"";
				//if("true".equals(checked)){
			String arrt[]=tabletemp.split(",");
			for(int i=0;i<arrt.length;i++){
				String temp=arrt[i];
				temp=temp.trim().split(" ")[1].trim();
				if(!"a01".equals(temp.toLowerCase())&&!"b".equals(temp.substring(0, 1).toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
					tableid=tableid+" and a01.v_xt="+temp.toLowerCase()+".v_xt ";
				}else if("b".equals(temp.substring(0, 1).toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
					tableid=tableid+" and a02.v_xt="+temp.toLowerCase()+".v_xt ";
				}
				
			}
					
					
				//}
			//}
			String arr1[]=null;
			if(arr!=null){
				String temp=arr[1];
				temp=temp.replace("\'", "");
				arr1=temp.split(",");
				
			}
			for(int i=0;arr1!=null&&i<arr1.length;i++){
				String temp=arr1[i];
				if(!"a01".equals(temp.toLowerCase())&&!"b01".equals(temp.toLowerCase())){
					tableid=tableid+" and a01.a0000="+temp.toLowerCase()+".a0000 ";
					tableid=tableid+" and a01.v_xt="+temp.toLowerCase()+".v_xt ";
				}else if("b01".equals(temp.toLowerCase())){//b01.b0111=a02.b0111 and a02.a0000=a01.a0000
					tableid=tableid+" and a02.a0201b="+temp.toLowerCase()+".b0111 ";
					tableid=tableid+" and a02.v_xt="+temp.toLowerCase()+".v_xt ";
				}
			}
		}
//		if(tableid.indexOf("a01.a0000")==-1){//ǰ̨����ѡ����b01��Ϣ��
//			tableid=tableid+" and a01.a0000=a02.a0000 ";
//		}
		sqlSelect=sqlSelect+tableid;//ƴ�ӱ�����
		sqlSelect=sqlSelect+" and a01.status!='4' "//��������
				+ " ";
		String whereConditionStr=this.getPageElement("conditionStr").getValue();//�������
		String qrysql="";
		if(whereConditionStr!=null
				&&!whereConditionStr.trim().equals("")
				&&!whereConditionStr.trim().equals("null")){
			qrysql=sqlSelect+" and (" + whereConditionStr+") ";//����sql
		}else{
			qrysql=sqlSelect;
		}
		//�滻�����е������
		qrysql=qrysql.replace("{v}", "")
				.replace("{%v}", "")
				.replace("{v%}", "")
				.replace("{%v%}", "");
		//qrysql=qrysql.toLowerCase();
		String userid=SysUtil.getCacheCurrentUser().getId();
		String username=SysUtil.getCacheCurrentUser().getLoginname();
		String instr="";
		if("system".equals(username)){
			
		}else{
			//instr=" and a01.a0000 in (select t.a0000 from Competence_Subperson t where t.userid = '"+userid+"' and t.type = '1' ) ";
		}
		qrysql=qrysql+instr;
		//this.getPageElement("querysql").setValue(qrysql);//����sql��ǰ̨
		return qrysql;
	}
	
	/**
	 * �������������������ɾ����������ݣ�������ͼ ���ܻع����ݣ�
	 * @param qvid
	 * @param viewnametb
	 * paramurl �ж�ҳ��
	 * @return
	 */
	public int deleteErr(String qvid,String viewnametb,String paramurl){
		Connection connection=null;
		Statement stmt=null;
		try{
			//ɾ�������sql
			HBSession session =HBUtil.getHBSession();
			connection = session.connection();
			if(paramurl!=null&&paramurl.trim().length()>0&&!paramurl.equals("null")){//�Զ����ѯ����Ա��Ϣ��ѯtab��������Ϣ��ѯ�������ɾ�� 
				stmt =connection.createStatement();
				stmt.execute("delete from JS_YJTJ where qvid='"+qvid+"' ");
				stmt.close();
			}
			if(qvid!=null&&qvid.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("update JS_YJTJ set qrysql = '' where qvid='"+qvid+"' ");
				stmt.close();
			}
			
			//ɾ����ͼ
			if(viewnametb!=null&&viewnametb.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("drop "+viewnametb+" ");
				stmt.close();
			}
			//ɾ���������Ϣ��
			if(qvid!=null&&qvid.trim().length()>0){
				stmt =connection.createStatement();
				stmt.execute("delete from js_yjtj_fld where qvid='"+qvid+"' ");
				stmt.close();
			}
			if(qvid!=null&&qvid.trim().length()>0){//ɾ�������������
				stmt =connection.createStatement();
				stmt.execute("delete from JS_YJTJ_use where qvid='"+qvid+"' ");
				stmt.close();
			}
			connection.close();
		}catch(Exception e1){
			try{
				System.out.println("�Զ�����ͼ��ѯ��������������������ݻع�ʧ��!");
				if(stmt!=null){
					stmt.close();
				}
				if(connection!=null){
					connection.close();
				}
			}catch(Exception e){
				
			}
		}
		if(paramurl!=null&&paramurl.trim().length()>0){
			this.getExecuteSG().addExecuteCode("parent.document.getElementById('qvid').value='';");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ����sql��ҳ��
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("previewFunc")
	public int previewFunc() throws RadowException{
		String sql=getSql();
		//ˢ��Ԥ��iframe��ҳ��
		this.getExecuteSG().addExecuteCode("refreshPreview();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ���ҳ���ֵ
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("clearValue")
	public int clearValue() throws RadowException, AppException{
		this.getPageElement("conditionName9").setValue("");//�������
		//ֵ��
		this.getPageElement("conditionName71").setValue("");
		this.getPageElement("conditionName71_combotree").setValue("");
		//ֵ��
		this.getPageElement("conditionName7").setValue("");
		//ֵһ
		this.getPageElement("conditionName61").setValue("");
		//ֵһ
		this.getPageElement("conditionName611").setValue("");
		//ֵһ
		this.getPageElement("conditionName6").setValue("");
		this.getPageElement("conditionName6_combotree").setValue("");
		//��������
		this.getPageElement("conditionName5").setValue("");
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		((Combo)this.getPageElement("conditionName5")).setValueListForSelect(map);
		//����ָ����
		this.getPageElement("conditionName4").setValue("");
		//�����Ϣ���б�
		CommQuery commQuery=new CommQuery();
		List<HashMap<String, Object>> list = commQuery.getListBySQL(" select 1 from dual where 1=2 ");
		this.getPageElement("codeList2Grid").setValueList(list);
		//"btnn1","btnn2","btnn4"};//1.ѡ������ 2.���� 3.��
		this.createPageElement("btnn1", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn2", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn4", ElementType.BUTTON, false).setDisabled(false);
		this.createPageElement("btnn3", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn5", ElementType.BUTTON, false).setDisabled(true);
		this.createPageElement("btnn6", ElementType.BUTTON, false).setDisabled(true);
		//�Ƴ�
		this.createPageElement("btnm3", ElementType.BUTTON, false).setDisabled(false);
		//ȫ��ɾ��
		this.createPageElement("btnm4", ElementType.BUTTON, false).setDisabled(false);
		//����
		this.createPageElement("btn4", ElementType.BUTTON, false).setDisabled(false);
		//Ԥ��
		this.createPageElement("btn5", ElementType.BUTTON, false).setDisabled(false);
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * �༭����
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("arrToContab")
	public int arrToContab(String str) throws RadowException, AppException{
		String arr[]=str.split(",");
		int num=0;
		String type=arr[num+5];
		String conditionName4=arr[num+2]+'.'+arr[num+1]+' '+arr[num];
		this.getPageElement("conditionName4").setValue(conditionName4);
		if("T".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from code_value t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName61").setValue(arr[num+4]);
				if(arr.length>=10&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.createPageElement("conditionName7", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName7").setValue(arr[num+6]);
				}
			}
		}else if("C".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from CODE_VALUE t "
					+ " where t.code_value not like '%between%' "
					+ " and t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName611").setValue(arr[num+4]);
			}
		}else if("N".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from CODE_VALUE t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName6111").setValue(arr[num+4]);
				if(arr.length>=8&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.createPageElement("conditionName711", ElementType.BUTTON, false).setDisabled(false);
					this.getPageElement("conditionName711").setValue(arr[num+6]);
				}
			}
		}else if("S".equals(type)){
			String sql="select t.code_value,concat(concat(t.code_value,' '),t.code_name) showname "
					+ " from code_value t "
					+ " where t.sub_code_value!='11' "
					+ " and t.code_type='OPERATOR' ";
			Map<String, String> map = SqlToMapUtil.HListTomap(sql, "code_value","showname");
			SqlToMapUtil.setValueListForSelect(this, "conditionName5", map);
			this.getPageElement("conditionName5").setValue(arr[num+8]);
			String name=HBUtil.getValueFromTab("concat(concat(code_value,' '),code_name)", "code_value", "code_type = 'OPERATOR' and code_value ='"+arr[num+8]+"' ");
			this.getPageElement("conditionName5_combo").setValue(name);
			String code_type=this.getPageElement("code_type").getValue();
			this.getExecuteSG().addExecuteCode("setTree('"+code_type+"');");
			if(arr[num+8].indexOf("null")==-1){
				this.getPageElement("conditionName6").setValue(arr[num+4]);
				this.getPageElement("conditionName6_combotree").setValue(arr[num+4]+"  "+arr[num+3]);
				if(arr.length>=10&&arr[num+8]!=null&&arr[num+8].indexOf("between")!=-1){
					this.getExecuteSG().addExecuteCode("document.getElementById('conditionName71_combotree').disabled=false; ");
					this.getPageElement("conditionName71").setValue(arr[num+6]);
					this.getPageElement("conditionName71_combotree").setValue(arr[num+6]+"  "+arr[num+7]);
				}
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
