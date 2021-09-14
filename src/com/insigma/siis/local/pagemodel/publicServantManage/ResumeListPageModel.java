package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A1701entry;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ResumeListPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException,AppException {
		
		this.setNextEventName("a1701grid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a1701grid.dogridquery")
	@NoRequiredValidate
	public int a1701gridGridQuery(int start,int limit) throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String sql = " from A1701entry a where a0000='"+a0000+"' order by startDate";
		this.pageQuery(sql,"HQL", start, limit); //�����ҳ��ѯ
	    return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * ѧλѧ��������ť
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("addBtn.onclick")
	@NoRequiredValidate
	public int addBtn()throws RadowException{
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		
		String a1701EntryId = this.getPageElement("a1701EntryId").getValue();
		String startDate = this.getPageElement("startDate").getValue();
		String entryContent = this.getPageElement("entryContent").getValue();
		if(a1701EntryId==null||"".equals(a1701EntryId)){//������ʾ�Ƿ񱣴浱ǰ��Ϣ��
			if(startDate!=null&&!"".equals(startDate) && (entryContent!=null&&!"".equals(entryContent))){
				this.getExecuteSG().addExecuteCode("$h.confirm('ϵͳ��ʾ','�Ƿ񱣴浱ǰ������Ϣ?',200,function(id){" +
						"if(id=='ok'){" +
							"saveData();	" +
							"}else if(id=='cancel'){" +
							"	radow.doEvent('clearCondition');" +
							"}" +
						"});");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
		}
		
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		clearCondition();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��� �����б�
	 * @throws RadowException 
	 */
	@PageEvent("clearCondition")
	@NoRequiredValidate
	public void clearCondition() throws RadowException{
		A1701entry a1701entry = new A1701entry();
		PMPropertyCopyUtil.copyObjValueToElement(a1701entry, this);
	}
	
	@PageEvent("saveData")
	@Transaction
	@Synchronous(true)
	public int saveData()throws RadowException, AppException{
		A1701entry a1701entry = new A1701entry();
		this.copyElementsValueToObj(a1701entry, this);
		String a1701EntryId = this.getPageElement("a1701EntryId").getValue();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		String refBs = this.getPageElement("refBs").getValue();
		if(a0000==null||"".equals(a0000)){
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String start = a1701entry.getStartDate();//��ʼ����
		String end = a1701entry.getEndDate();//��������
		if(start!=null&&!"".equals(start)&&end!=null&&!"".equals(end)){
			if(end.length()==6){
				end += "00";
			}
			if(start.length()==6){
				start += "00";
			}
			if(end.compareTo(start)<0){
				this.setMainMessage("�������ڲ������ڿ�ʼ����");
				return EventRtnType.NORMAL_SUCCESS;
			}
		}
		a1701entry.setA0000(a0000);
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			String a0107 = a01.getA0107();//��������
			if(a0107!=null&&!"".equals(a0107)){
				if(a0107.length()==6){
					a0107 += "00";
				}
				if(start!=null&&!"".equals(start)){
					if(start.length()==6){
						start += "00";
					}
					if(start.compareTo(a0107)<0){
						this.setMainMessage("��ʼ���ڲ���С�ڳ�������");
						return EventRtnType.NORMAL_SUCCESS;
					}
				}
			}
			if(a1701EntryId==null||"".equals(a1701EntryId)){
				sess.save(a1701entry);	
			}else{
				sess.update(a1701entry);
			}
			if(refBs!=null && !"".equals(refBs)) {
				String ism =  a1701entry.getIsMatch();
				if(ism==null || "".equals(ism) || "0".equals(ism)) {
					this.getExecuteSG().addExecuteCode("openRefWin('"+a1701EntryId+"')");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			sess.flush();
			this.setMainMessage("����ɹ���");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getPageElement("a1701EntryId").setValue(a1701entry.getA1701EntryId());//����ɹ���id���ص�ҳ���ϡ�
		this.getExecuteSG().addExecuteCode("radow.doEvent('a1701grid.dogridquery')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �޸�ѧλѧ���޸��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("a1701grid.rowclick")
	@GridDataRange
	@NoRequiredValidate
	public int a1701gridOnRowClick() throws RadowException{ 
		int index = this.getPageElement("a1701grid").getCueRowIndex();
		String a1701EntryId = this.getPageElement("a1701grid").getValue("a1701EntryId",index).toString();
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A1701entry a1701entry = (A1701entry)sess.get(A1701entry.class, a1701EntryId);
			PMPropertyCopyUtil.copyObjValueToElement(a1701entry, this);
		} catch (Exception e) {
			this.setMainMessage("��ѯʧ�ܣ�");
			return EventRtnType.FAILD;
		}							
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("deleteRow")
	@Transaction
	@Synchronous(true)
	public int deleteRow(String a1701EntryId)throws RadowException, AppException{
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A1701entry a1701entry = (A1701entry)sess.get(A1701entry.class, a1701EntryId);
			sess.delete(a1701entry);
			sess.flush();
			this.getExecuteSG().addExecuteCode("odin.alert('ɾ���ɹ�');radow.doEvent('a1701grid.dogridquery')");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("removeRef")
	@Transaction
	@Synchronous(true)
	public int removeRef(String a1701EntryId)throws RadowException, AppException{
		HBSession sess = null;
		try {
			sess = HBUtil.getHBSession();
			A1701entry a1701entry = (A1701entry)sess.get(A1701entry.class, a1701EntryId);
			a1701entry.setRefBs("");
			a1701entry.setRefBsId("");
			a1701entry.setIsMatch("0");
			sess.update(a1701entry);
			sess.flush();
			this.getExecuteSG().addExecuteCode("odin.alert('�Ӵ��ɹ�');radow.doEvent('a1701grid.dogridquery');");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("���������쳣��");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initdata")
	@Transaction
	@Synchronous(true)
	public int initdata()throws RadowException, AppException{
		HBSession sess = null;
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		try {
			sess = HBUtil.getHBSession();
			A01 a01 = (A01)sess.get(A01.class, a0000);
			String a1701 = a01.getA1701();
			if(a1701!=null&&!"".equals(a1701)){
				String[] jianli = a1701.split("\r\n|\r|\n");
				String jlnr = "";
				String time = "";
				String start = "";
				String end = "";
				for(int i=0;i<jianli.length;i++){
					String jl = jianli[i].trim();
					//boolean b = jl.matches("[0-9]{4}\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]{2}.*");//\\.[0-9]{2}[\\-]{1,2}[0-9]{4}\\.[0-9]
					Pattern pattern = Pattern.compile("[0-9| ]{4}[\\.| |��][0-9| ]{2}[\\-|��|-]{1,2}[0-9| ]{4}[\\.| |��][0-9| ]{2}[ ]{2}");     
			        Matcher matcher = pattern.matcher(jl);     
			        if (matcher.find()) {
			        	String line1 = matcher.group(0);  
			        	int index = jl.indexOf(line1);
			        	if(index==0){//�����ڿ�ͷ  (һ��)
			        		//----------- ����������ڿ�ͷ,�����ϸ����ڿ�ͷ����----------
			        		if(time!=null && "".equals(time)) {
			        			A1701entry a1701entry = new A1701entry();
			        			
			        			
			        			
			        		}
			        		time = line1;
			        		String[] date = time.split("[\\-|��|-]{1,2}");
			        		start = date[0];
			        		end = date[1];		
			        		jlnr = jlnr + jl.substring(line1.length()).trim();
			        	}else{
			        		//jlnr = jlnr + jl.substring(line1.length()).trim();
			        	}
			        }else{
			        	//jlnr = jlnr + jl;
			        }
				}
				//----------- �������һ������----------
				if(time!=null && "".equals(time)) {
        			A1701entry a1701entry = new A1701entry();
        			
        			
        			
        		}
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("���������쳣��");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
