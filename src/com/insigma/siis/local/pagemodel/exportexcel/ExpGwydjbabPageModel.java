package com.insigma.siis.local.pagemodel.exportexcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.insigma.odin.framework.AppException;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.business.sysrule.SysRuleBS;
import com.insigma.siis.local.pagemodel.sysorg.org.PublicWindowPageModel;
/**
 * @author lixy
 * 
 */
public class ExpGwydjbabPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		// TODO Auto-generated method stub\
		return 0;
	}
	
	//�������ѯ�¼�
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws RadowException, AppException {
		HBSession sess = HBUtil.getHBSession();
		this.getPageElement("checkedgroupid").setValue(id);
		String userID = SysManagerUtils.getUserId();
		Map<String, String> map = PublicWindowPageModel.isHasRule(id, userID);
		if (!map.isEmpty()||map==null) {
			if ("2".equals(map.get("type"))){
				this.setMainMessage("��û�иû�����Ȩ��");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		String isContain = this.getPageElement("isContain").getValue();
		if("1".equals(isContain)){
			B01 b01=(B01) HBUtil.getHBSession().get(B01.class, id);
			this.getPageElement("ereaname").setValue(b01.getB0101()+"���¼�����");
		}else{
			B01 b01=(B01) HBUtil.getHBSession().get(B01.class, id);
			this.getPageElement("ereaname").setValue(b01.getB0101());
		}
		this.getExecuteSG().addExecuteCode("getShowFour();");//��ʾ�����ѯtab
		this.setNextEventName("plgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}	
	
	/* ���ɱ��Ǽ���Ա���ַ���
	 * @author lxy
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getSheet.onclick")
	public int getSheet() throws RadowException, AppException{
		String djString=null;
		//ѭ���Ǽ��б��������ɵǼ��ַ���
		PageElement pe = this.getPageElement("djgrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			djString=djString+"@|"+hm.get("a0000")+"|";
		}
		
		if(djString!=null)
		    djString=djString.substring(djString.indexOf("@")+1,djString.length());
 		this.getPageElement("djgridString").setValue(djString);
 		
		String zhString=null;
		//ѭ���ݻ��б��������ɵǼ��ַ���
		pe = this.getPageElement("zhgrid");
		list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			zhString=zhString+"@|"+hm.get("a0000")+"|";
		}
		
		if(zhString!=null)
		    zhString=zhString.substring(zhString.indexOf("@")+1,zhString.length());
 		this.getPageElement("zhgridString").setValue(zhString);		
		
		this.getExecuteSG().addExecuteCode("downLoadTmp()");
 		
 		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* ���ɱ��Ǽ���Ա���ַ���
	 * @author lxy
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getDj")
	public int delDj() throws RadowException, AppException{
		String djString=null;
		//ѭ���Ǽ��б��������ɵǼ��ַ���
		PageElement pe = this.getPageElement("djgrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			djString=djString+"@|"+hm.get("a0000")+"|";
		}
		
		if(djString!=null)
		    djString=djString.substring(djString.indexOf("@")+1,djString.length());
 		this.getPageElement("djgridString").setValue(djString);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* ���ɱ��Ǽ���Ա���ַ���
	 * @author lxy
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("getZh")
	public int delZh() throws RadowException, AppException{
		String zhString=null;
		//ѭ���ݻ��б��������ɵǼ��ַ���
		PageElement pe = this.getPageElement("zhgrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (HashMap<String, Object> hm : list) {
			zhString=zhString+"@|"+hm.get("a0000")+"|";
		}
		
		if(zhString!=null)
		    zhString=zhString.substring(zhString.indexOf("@")+1,zhString.length());
 		this.getPageElement("zhgridString").setValue(zhString);		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("addDj.onclick")
	public int addDj() throws RadowException, AppException{
		String djString=null;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("plgrid");
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		int i = 0;
		for(HashMap<String, Object> map:list){
			if(map.get("personcheck")== null || map.get("personcheck").equals("")){
				i = i;
			}else{
				i = i+1;
			}
		}
		if(i==0){
			this.setMainMessage("�빴ѡ��Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String, Object>> listOther=null;//�ݻ��Ǽ���Ա�б�
		List<HashMap<String, Object>> listM=this.getPageElement("djgrid").getValueList();//�Ǽ���Ա�б�
		for (HashMap<String, Object> hm : list) {

			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listOther = this.getPageElement("zhgrid").getValueList();
				for(HashMap<String, Object> om : listOther) {
					if(hm.get("a0000").equals(om.get("a0000")))
					throw new AppException(hm.get("a0101")+":�Ѿ�����ӵ��ݻ��Ǽ��б�������Ǽ���Ա�б���ӣ�");
				}
				gdlist.add(hm);
				djString=djString+"@|"+hm.get("a0000")+"|";
			}
		}
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(gdlist);
		pe.setValue(JSONArray.fromObject(list).toString()
 				);
		//���ɵǼ���Ա�ַ���
//		if(djString!=null)
//		    djString=djString.substring(djString.indexOf("@")+1,djString.length());
// 		this.getPageElement("djgridString").setValue(djString);
 		//��������ӵ��Ǽ���Ա�б�
 		listM.addAll(gdlist);
		String data= JSONArray.fromObject(listM).toString();
 		this.getPageElement("djgrid").setValue(data
 				);
 		//���õǼ�����
 		this.getPageElement("djs").setValue(Integer.toString(listM.size()));
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("addZh.onclick")
	public int addZh() throws RadowException, AppException{
		String zhString=null;
		List<HashMap<String, Object>> gdlist=new ArrayList<HashMap<String,Object>>();
		PageElement pe = this.getPageElement("plgrid");
		List<HashMap<String, Object>> listOther=null;//�Ǽ���Ա�б�
		List<HashMap<String, Object>> list = pe.getValueList();//��ѯ��Ա�б�
		int i = 0;
		for(HashMap<String, Object> map:list){
			if(map.get("personcheck")== null || map.get("personcheck").equals("")){
				i = i;
			}else{
				i = i+1;
			}
		}
		if(i==0){
			this.setMainMessage("�빴ѡ��Ա��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		List<HashMap<String, Object>> listM=this.getPageElement("zhgrid").getValueList();//�ݻ��Ǽ���Ա�б�		
		for (HashMap<String, Object> hm : list) {
			
			if(hm.get("personcheck")!=null&&!"".equals(hm.get("personcheck"))&&(Boolean) hm.get("personcheck")){
				listOther = this.getPageElement("djgrid").getValueList();
				for(HashMap<String, Object> om : listOther) {
					if(hm.get("a0000").equals(om.get("a0000")))
						throw new AppException(hm.get("a0101")+":�Ѿ�����ӵ��Ǽ���Ա�б��������ݻ��Ǽ���Ա�б���ӣ�");
				}		
				gdlist.add(hm);
				zhString=zhString+"@|"+hm.get("a0000")+"|";
			}
		}
		//ȥ����ѯ�б��б���ӵ���Ա
		list.removeAll(gdlist);
		pe.setValue(JSONArray.fromObject(list).toString()
 				);
		//�����ݻ��Ǽ���Ա�ַ���
//		if(zhString!=null)
//		    zhString=zhString.substring(zhString.indexOf("@")+1,zhString.length());
// 		this.getPageElement("zhgridString").setValue(zhString);
 		//��������ӵ��ݻ��Ǽ���Ա�б�
 		listM.addAll(gdlist);
		 String data= JSONArray.fromObject(listM).toString();
 		this.getPageElement("zhgrid").setValue(data);
 		//�����ݻ��Ǽ���
 		this.getPageElement("zhs").setValue(Integer.toString(listM.size()));
			return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("mQuery.onclick")
	public int query() throws RadowException{
		    this.setNextEventName("plgrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("plgrid.dogridquery")
	public int gridcq(int start, int limit) throws RadowException, AppException{
		   String b0111=this.getPageElement("checkedgroupid").getValue();
		   String isContain = this.getPageElement("isContain").getValue();
		   if(b0111!=null&&!"".equals(b0111)){
			   if(b0111.contains("@"))
				  throw new AppException("ֻ��ѡ�񵥸�������ѯ��");
			   else
				   b0111=b0111.replace("|","");
				   
		   }else
			   throw new AppException("��ѡ�������");
			   
		   ExportDataBuilder eb=new ExportDataBuilder();
		   String sql = eb.getStringByB0111(b0111, isContain);
		   this.pageQuery(sql, "SQL", start, limit);
//		   String data= JSONArray.fromObject(eb.getListByB0111(b0111,isContain)).toString();
//    		this.getPageElement("plgrid").setValue(data
//    				);
    		//ˢ�µǼǺ��ݻ��Ǽ����ֵ
    		this.getPageElement("djgrid").reload();
    		this.getPageElement("zhgrid").reload();
     		this.getPageElement("zhgridString").setValue("");
     		this.getPageElement("djgridString").setValue("");
     		this.getPageElement("zhs").setValue("");	
     		this.getPageElement("djs").setValue("");	  
			return EventRtnType.SPE_SUCCESS;
		
	}
	
	/** 
	 * �趨ѡ���кŵ�ҳ����
	 * @author mengl
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("plgrid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setplCueRow() throws RadowException{
		int cueRowIndex = this.getPageElement("plgrid").getCueRowIndex();
		this.getPageElement("plcueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/** 
	 * �趨ѡ���кŵ�ҳ����
	 * @author mengl
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("djgrid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setdjCueRow() throws RadowException{
		int cueRowIndex = this.getPageElement("djgrid").getCueRowIndex();
		this.getPageElement("djcueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/** 
	 * �趨ѡ���кŵ�ҳ����
	 * @author mengl
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zhgrid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setzhCueRow() throws RadowException{
		int cueRowIndex = this.getPageElement("zhgrid").getCueRowIndex();
		this.getPageElement("zhcueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
