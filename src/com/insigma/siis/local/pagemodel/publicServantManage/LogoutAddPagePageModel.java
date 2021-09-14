package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * �˳�����
 * @author fujun
 *
 */
public class LogoutAddPagePageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	
	@PageEvent("save")
	@Transaction
	@Synchronous(true)
	public int saveDegrees()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("���ȱ�����Ա������Ϣ��");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
		List<Object> list=sess.createSQLQuery(sql).list();
		try {
			//�˳�������Ϣ��
			/*A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			a30.setA0000(a0000);
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
			}
			PropertyUtils.copyProperties(a30_old, a30);
			sess.saveOrUpdate(a30_old);	*/
			
			A30 a30 = new A30();
			this.copyElementsValueToObj(a30, this);
			//�ж��˳�����λ���ڣ�Ӧ���ڲμӹ���ʱ�䡣
			String a0134 = a01.getA0134();//�μӹ���ʱ��
			String a3004 = a30.getA3004();//�˳�����λ����
			if(a0134!=null&&!"".equals(a0134)&&a3004!=null&&!"".equals(a3004)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a3004.length()==6){
					a3004 += "00";
				}
				if(a3004.compareTo(a0134)<0){
					this.setMainMessage("�˳�����λ���ڲ������ڲμӹ���ʱ��");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			String orgid=this.getPageElement("orgid").getValue();
			String a3007a=a30.getA3007a();
			String a3007=this.getPageElement("a3007a_combo").getValue();
			String a3001 = a30.getA3001();
			if(a3001!=null&&!"".equals(a3001)){
				//������Ա     ��ʷ��
				if(!a3001.startsWith("1")&&!a3001.startsWith("2")&&StringUtil.isEmpty(orgid)){
					 this.setMainMessage("�˳���λ����Ϊ��");
					 return EventRtnType.FAILD; 
				}
				if(a3001.startsWith("1")||a3001.startsWith("2")){
					//������λ��������λ�ĵ�ֱ������Ϊ����ְ��Ա,����д�����µĵ�λ���һ���յ�ְ����Ϣ
					if(null == a30.getA3007a() || "".equals(a30.getA3007a())){
						this.setMainMessage("������λ����Ϊ��");
						return EventRtnType.FAILD;
					}
					if("-1".equals(a30.getA3007a())){
						a01.setA0163("2");
						a01.setStatus("2");
						a01.setOrgid(list.get(0).toString());
					}else{
						a01.setA0163("1");
						a01.setStatus("1");
						a01.setOrgid(list.get(0).toString());
					}
				}else if("35".equals(a3001)){//����  ��ʾ����ȥ����       ��ѯ����ʷ��Ա
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31".equals(a3001)){//������ ��ʾ��������Ա��     ��ѯ��������Ա
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else{//����ѡ���˳��Ǽǡ��������������ˡ�����ȥ��ְ���������� ��ʾ��������Ա��     ��ѯ����ʷ��Ա
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}
			}else{
				a01.setA0163("1");
				a01.setStatus("1");
			}
			a30.setA0000(a0000);
			
			A30 a30_old = (A30)sess.get(A30.class, a0000);
			if(a30_old==null){
				a30_old = new A30();
				applog.createLog("3301", "A30", a01.getA0000(), a01.getA0101(), "������¼", new Map2Temp().getLogInfo(a30_old,a30));
				
			}else{
				applog.createLog("3302", "A30", a01.getA0000(), a01.getA0101(), "�޸ļ�¼", new Map2Temp().getLogInfo(a30_old,a30));
			}
			PropertyUtils.copyProperties(a30_old, a30);
			
			sess.saveOrUpdate(a30_old);
			
		    this.getExecuteSG().addExecuteCode("setParentA0163('"+a01.getA0163()+"')");
		} catch (Exception e) {
			this.setMainMessage("����ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("����ɹ���");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException, AppException, JSONException, IOException {
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		//��ҳ�渳ֵ
		if(a0000!=null && !"".equals(a0000)){
			A01 a01=(A01) sess.get(A01.class, a0000);
			//�˳��������
			Map<String,String> map=new LinkedHashMap<String,String>();
			String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
			List<Object[]> list=sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(Object[] a02:list){
					map.put(a02[1].toString(), a02[0].toString());
				}
				((Combo)this.getPageElement("orgid")).setValueListForSelect(map);
			}else{
				this.setMainMessage("ְ����ϢΪ���޷��˳�����");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('save').disable()");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				return EventRtnType.FAILD;
			}
			
			A30 a30 = (A30) sess.get(A30.class, a0000);
			if (a30 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
				//��ֵ �ж��Ƿ��޸�
				String json = objectToJson(a30);
				this.getExecuteSG().addExecuteCode("parent.A30value="+json+";");
				
				//�ж��˳�����ʽ�Ƿ�Ϊ�����ת��
				String a3001 = a30.getA3001();
				if(null != a3001 && !"".equals(a3001) && a3001.startsWith("3")){
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
					if(null!=a01.getOrgid()){
						String orgName= HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a01.getOrgid()+"'");
						if(orgName!=null){
							this.getPageElement("orgid").setValue(orgName);
						}
					}
				}else if(null == a3001 || "".equals(a3001)){
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				}else if(null != a3001 && !"".equals(a3001) && (a3001.startsWith("1")||a3001.startsWith("2"))){
					this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
					if(null != a30.getA3007a()){
						String a3007a = HBUtil.getValueFromTab("b0101", "b01", " b0111='"+a30.getA3007a()+"'");
						if(a3007a!=null){
							this.getPageElement("a3007a_combo").setValue(a3007a);//�������� ���ġ�
						}
					}
				}
				
			}else{
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
			}

		}

		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
     * ��ʵ��POJOת��ΪJSON
     * @param obj
     * @return
     * @throws JSONException
     * @throws IOException
     */
    public static<T> String objectToJson(T obj) throws JSONException, IOException {
        ObjectMapper mapper = new ObjectMapper();  
        // Convert object to JSON string  
        String jsonStr = "{}";
        try {
             jsonStr =  mapper.writeValueAsString(obj);
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
}
