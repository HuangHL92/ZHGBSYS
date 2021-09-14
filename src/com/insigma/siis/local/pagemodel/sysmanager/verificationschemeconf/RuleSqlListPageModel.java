package com.insigma.siis.local.pagemodel.sysmanager.verificationschemeconf;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.CodeManager;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.PageEvents;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.CodeTableCol;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.VVerifyColVsl006;
import com.insigma.siis.local.business.entity.VerifyRule;
import com.insigma.siis.local.business.entity.VerifyScheme;
import com.insigma.siis.local.business.entity.VerifySqlList;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.RuleSqlListBS;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifyRuleDTO;
import com.insigma.siis.local.business.sysmanager.verificationschemeconf.VerifySchemeDTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * У��������ô���
 * @author mengl
 *
 */
public class RuleSqlListPageModel extends PageModel{
	
	@PageEvent("btnClose.onclick")
	@NoRequiredValidate
	public int btn1Onclick() throws RadowException {
		String vsc001Str = this.getPageElement("vsc001").getValue();
		this.closeCueWindow("addwin");
		//this.getExecuteSG().addExecuteCode("parent.radow.doEvent('verifyRuleGrideload','"+vsc001Str+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**������津����
	 * 1.���� У������verify_rule��,����У�鷽����verify_scheme������������
	 * 2.���� У��SQL����verify_sql_list��,����У������verify_rule������������
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvents({"btnSave.onclick","btnSaveContinue.onclick"})
	@NoRequiredValidate
	@Transaction
	public int btnSaveOnclick() throws RadowException, AppException {
		String eleName = this.getCueEventElementName();
		String alertMsg = "����ɹ���";
		
		String operateType = null; //�������ͣ���¼��־ʹ�ã�
		VerifyRuleDTO vrDTO = new VerifyRuleDTO();//У��������ݴ�����󣨼�¼��־ʹ�ã�
		List<VerifySqlList> vslOldList = new LinkedList<VerifySqlList>();//У�������ϸ���ã���¼��־ʹ�ã�
		List<VerifySqlList> vslNewList = new LinkedList<VerifySqlList>();//У�������ϸ���ã���¼��־ʹ�ã�
		
		//0. У��
		
		String vru002 = this.getPageElement("vru002").getValue();
		String vru003 = this.getPageElement("vru003").getValue();
		List<HashMap<String, Object>>  gridList = this.getPageElement("grid").getValueList();
		if(StringUtil.isEmpty(vru002) || StringUtil.isEmpty(vru003) || gridList==null || gridList.size()<1 || gridList.get(0)==null){
			throw new AppException("��������쳣��Ϣ��У��������ơ�У�����͡�У������б������Ϊ�գ�");
		}
		
		HBSession sess = HBUtil.getHBSession();
		String vsc001Str = this.getPageElement("vsc001").getValue()==null?"":this.getPageElement("vsc001").getValue();//У�鷽����verify_scheme��������
		String vru001Str = this.getPageElement("vru001").getValue()==null?"":this.getPageElement("vru001").getValue();//У������verify_rule��������
		if(StringUtil.isEmpty(vsc001Str)){
			throw new AppException("δ��ȡ����ӦУ�鷽����Ϣ��");
		}
		try {
			PageElement pe = this.getPageElement("grid");
			VerifyRule verifyRule = null;
			
			//1.���� У������verify_rule��
			
				//1.1 �ж��������޸�
			
			if(StringUtil.isEmpty(vru001Str)){
			//����
				verifyRule =  new VerifyRule();
				verifyRule.setVru007("0");//��Ч(����)��ǣ�  1-���ã�0-δ����
				
				operateType = "638";
			}else{
			//�޸�
				verifyRule = (VerifyRule) sess.get(VerifyRule.class, vru001Str);
				//verifyRule.setVru007(this.getPageElement("vru007").getValue());//��Ч(����)��ǣ�  1-���ã�0-δ����
				verifyRule.setVru007("0");
				operateType = "639";
				BeanUtil.copy(verifyRule, vrDTO);
			}
			
				//1.2 ��ֵ����
			
//			this.copyElementsValueToObj(verifyRule, this);
			String vru008 = this.getPageElement("vru008").getValue();
			String vru004 = this.getPageElement("vru004").getValue();
			String vru005 = this.getPageElement("vru005").getValue();
			if(StringUtil.isEmpty(vru004)||StringUtil.isEmpty(vru005)){
				throw new AppException("����У�����ʱ������ѡ��У����Ϣ������У����Ϣ���Ϊ��У�������ҪУ�����Ϣ��棡");
			}
			
			verifyRule.setVru002(this.getPageElement("vru002").getValue());
			verifyRule.setVru003(this.getPageElement("vru003").getValue());
			verifyRule.setVru004(vru004);
			verifyRule.setVru005(vru005);
			verifyRule.setVru006(this.getPageElement("vru006").getValue());
			verifyRule.setVsc001(vsc001Str);
			verifyRule.setVru008((vru008!=null?vru008:"")+verifyRule.getVru002());//������Ϣ
			verifyRule.setVru009(this.getPageElement("vru009").getValue());//���ƴ�Ӻõ�sql���
			sess.saveOrUpdate(verifyRule);
			vru001Str = verifyRule.getVru001();
			this.getPageElement("vru001").setValue(vru001Str);
			
			//2. ���� У��SQL����verify_sql_list��
			
				//2.1 ɾ���ù��򷽰���Ӧ��ԭУ��SQL����
			
			String sql = " from VerifySqlList where vru001 = '"+vru001Str+"' ";
			@SuppressWarnings("unchecked")
			List<VerifySqlList> verifySqlLists = sess.createQuery(sql).list();
			for(VerifySqlList verifySqlList:verifySqlLists){
				sess.delete(verifySqlList);
				
				//��¼��־ʹ��
				vslOldList.add(verifySqlList);
			}
			
				//2.2 ����Grid��У��SQL����
			
			List<HashMap<String, Object>> list = pe.getValueList();
			Field[] fields = VerifySqlList.class.getDeclaredFields();
			long vsl002Count = 0; //��������
			long vsl011Count = 0; //��������
			for(int i=0;i<list.size();i++){
				VerifySqlList vsl = new VerifySqlList();
				vsl.setVsl014(Long.valueOf(i+1));
				HashMap<String, Object> map = list.get(i);
				Object vsl002 = map.get("vsl002");
				Object vsl011 = map.get("vsl011");
				vsl002Count = vsl002Count + ((vsl002==null||StringUtil.isEmpty(vsl002.toString()))?0L:Long.valueOf(vsl002.toString()));
				vsl011Count = vsl011Count + ((vsl011==null||StringUtil.isEmpty(vsl011.toString()))?0L:Long.valueOf(vsl011.toString()));
				for(Field field : fields){
					String fieldName = field.getName();
					Object fieldValue = map.get(fieldName);
					
					if(map.containsKey(fieldName) && fieldValue!=null && !StringUtil.isEmpty(fieldValue.toString())){
						Method setMethod = VerifySqlList.class.getDeclaredMethod("set"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1), field.getType());
						//���������쳣��ͨ��ת��Ϊ���ö�Ӧ�����String�������췽��
						try {
							setMethod.invoke(vsl, fieldValue);
						} catch (IllegalArgumentException e) {
							fieldValue = field.getType().getConstructor(String.class).newInstance(fieldValue.toString());
							setMethod.invoke(vsl, fieldValue);
						}
					}
				}
				vsl.setVru001(vru001Str);
				sess.save(vsl);
				
				vslNewList.add(vsl);
			}
			if(vsl002Count!=vsl011Count){
				throw new AppException("ƴ�ӵ�У�����������������������ȣ�");
			}
			
			//3.��¼��־
				//��ȡ�ı���Ϣ
			List<String[]> changesList = Map2Temp.getLogInfo(vrDTO, verifyRule);
			
			VerifySqlList vslNew = new VerifySqlList();//�ն����¶���ȶ�Ϊ��ʱʹ��
				//ѭ��������ȡ���ֵ
			int size = vslOldList.size()>vslNewList.size()?vslOldList.size():vslNewList.size();
			for(int i = 0;i<size;i++){
				//����List��Χ����ֵΪ�ն������Ƚ�
				VerifySqlList odlVsl = vslOldList.size()>i && vslOldList.get(i)!=null?vslOldList.get(i):vslNew;
				VerifySqlList newVsl = vslNewList.size()>i && vslNewList.get(i)!=null?vslNewList.get(i):vslNew ;
				changesList.addAll( Map2Temp.getLogInfo(odlVsl,newVsl));
			}
			
				//�ų�Verify_sql_list�����ı�����
			Iterator<String[]> it = changesList.iterator();
			while(it.hasNext()){
				String[] changeItem = it.next();
				if(changeItem!=null && changeItem.length>0 && "VSL001".equalsIgnoreCase(changeItem[0])){
					it.remove();
				}
			}
			
				//У������б仯�������÷���δ���ò���¼��־
			if(changesList!=null && changesList.size()>0){
				VerifyScheme vs = (VerifyScheme) sess.get(VerifyScheme.class, vsc001Str);
				VerifySchemeDTO vsDTO = new VerifySchemeDTO();
				BeanUtil.copy(vs, vsDTO);
				/*vs.setVsc003("0");//0-��Ч��δ���ã�
				sess.update(vs);*/
				changesList.addAll( Map2Temp.getLogInfo(vsDTO, vs));
				try {
					new LogUtil().createLog(operateType, "VERIFY_RULE", vru001Str, verifyRule.getVru002(), null, changesList);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				//�˴������׳��쳣��verify_sql_listɾ�������������ˣ���Ҫ�����ύ
				alertMsg = "����ɹ���";
			}
			
			sess.flush();
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new AppException("����ʧ�ܣ��쳣��Ϣ��"+e1.getMessage());
		}
		
		//4. ��������桿��ť���ر�У�������Ӵ��ڣ���ҳ����ʾ����ɹ��� ����������������ť����ʾ����ɹ���ˢ��  У�������Ӵ��ڣ�����Ϊ��У�鷽�����У�����
		
		if("btnSave".equals(eleName)){
			this.closeCueWindow("addwin");
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('success','"+vsc001Str+","+alertMsg+"')");
		}else if("btnSaveContinue".equals(eleName)){
			//this.setMainMessage(alertMsg);
			this.getExecuteSG().addExecuteCode("parent.radow.doEvent('success','"+vsc001Str+","+alertMsg+"')");
			this.setNextEventName("btnReload.onclick");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��Ϣ�����޸ĵİ�ť�¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("loadadd.onclick")
	@GridDataRange
	public int loadonClick() throws RadowException{  
		this.setRadow_parent_data(this.getPageElement("grid").getValue("modelobjid",this.getPageElement("grid").getCueRowIndex()).toString());
		this.openWindow("addwin", "pages.modeldb.mscript.LoadScipt");
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	
	
	/**
	 * �����������б�
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvents({"savecond.onclick","modefyCond.onclick"})
	@GridDataRange
	@AutoNoMask
	public int savecondonClick() throws RadowException, AppException{  
		PageElement pe = this.getPageElement("grid");
		String eleName = this.getCueEventElementName();
		if(StringUtil.isEmpty(eleName)){
			throw new AppException("�����쳣������ϵ�����̣�����savecondonClick()������ͨ���¼����ã�");
		}
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		//�޸İ�ťʱ�����ж��Ƿ�ѡ��ĳ��
		if("modefyCond".equals(eleName)){
			if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
				throw new AppException("����ѡ��һ�У�");
			}else{
				cueRowIndex = Integer.parseInt(cueRowIndexStr);
			}
		}
		
		String vsl007= this.getPageElement("vsl007").getValue();
		String vsl013= this.getPageElement("vsl013").getValue();
		String vsl009= this.getPageElement("vsl009").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		String vsl006= this.getPageElement("vsl006").getValue();//���������
		String vru004= this.getPageElement("vru004").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		List<HashMap<String, Object>> list = pe.getValueList();
		
		//1. У��
			//1.1 ǰ̨��֤�������̨Ҳ�����֤��
		if(StringUtil.isEmpty(vsl006) || StringUtil.isEmpty(vru004) || StringUtil.isEmpty(vru005)){
			throw new AppException("У����Ϣ����У����Ϣ������������Ϊ�գ�");
		}
			//1.2 �ȶ���Ϣ�����ȶ���Ϣ�� ����ͬʱ����
		if((StringUtil.isEmpty(vsl008) && !StringUtil.isEmpty(vsl009)) || (StringUtil.isEmpty(vsl009) && !StringUtil.isEmpty(vsl008))){
			throw new AppException("�ȶ���Ϣ�����ȶ���Ϣ�� ����ͬʱΪ�ջ�ͬʱ��Ϊ�գ�");
		}
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		if(codeValue==null){
			throw new AppException("��ȡ��������������쳣���쳣��Ϣ��������ID-"+vsl006);
		}
		String subCodeValue = codeValue.getSubCodeValue();
		
			//1.3	�� ������Ϊ�����������subCodeValue=3�������ǿա���Ϊ�ա��������̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�
			//		����������Ϊ���ȶ���Ϣ����̶�ֵ��Ӽ������������subCodeValue=5�������̶�ȡֵ���ȶ���Ϣ�������Ϊ��,ѡ��ȡֵӦ��Ϊ��
			//		����������Ϊ��ϵͳ������̶�ֵ��Ӽ������������subCodeValue=6�������̶�ȡֵ����Ϊ�գ���ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�
			//		�ܲ��������ǡ�����������͡���Ӽ���������������̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ�����ҽ�������һ�Ϊ��
		 if("3".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("������Ϊ���ǿա���Ϊ�ա����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
			}
		}else if("5".equals(subCodeValue)){
			if(StringUtil.isEmpty(vsl009) || StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("������Ϊ���ȶ���Ϣ����̶�ֵ��Ӽ���������������̶�ȡֵ���ȶ���Ϣ�������Ϊ��,ѡ��ȡֵӦ��Ϊ�գ�");
			}
		}else if("6".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("������Ϊ��ϵͳ������̶�ֵ��Ӽ���������������̶�ȡֵ����Ϊ�գ���ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
			}
		}else if("7".equals(subCodeValue)){
			if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
				throw new AppException("������Ϊ��������ʽ����������̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
			}
		}else if("8".equals(subCodeValue)){
			if("80".equals(codeValue.getCodeValue())||"81".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if("B01".equalsIgnoreCase(vru004)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�������ܶԵ�λ������Ϣʹ�ã�");
				}
			}
		}else if("9".equals(subCodeValue)){
			if("99".equals(codeValue.getCodeValue()) || "98".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ�����֤�š���");
				}
			}
			if("90".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0195")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ͳ�ƹ�ϵ���ڵ�λ����");
				}
			}
			/*if("91".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a2911")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ�����뱾��λ�䶯��𡿣�");
				}
			}*/
			if("96".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0281")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ�����ְ���ʶ����");
				}
			}
			if("93".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ���������֤���롿��");
				}
			}
			if("902".equals(codeValue.getCodeValue())||"932".equals(codeValue.getCodeValue())||"933".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0184")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ���������֤���롿��");
				}
			}
			if("91".equals(codeValue.getCodeValue())||"934".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("b0114")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ�����˵�λ����(�����������)����");
				}
			}
			if("92".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("a0195")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ͳ�ƹ�ϵ���ڵ�λ����");
				}
			}
			if("901".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0160")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ����Ա��𡿣�");
				}
			}
			if("903".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("B0150")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ����������쵼ְ������");
				}
			}
			if("908".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A1701")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ����������");
				}
			}
			if("909".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0195")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ͳ�����ڹ�ϵ����");
				}
			}
			if("925".equals(codeValue.getCodeValue()) ){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0243")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ����ְʱ�䡿��");
				}
			}
			if("921".equals(codeValue.getCodeValue())||"926".equals(codeValue.getCodeValue())||"927".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0501B")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ְ���Σ�ְ����Σ�����");
				}
			}
			if("929".equals(codeValue.getCodeValue())||"930".equals(codeValue.getCodeValue())||"931".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A15Z101")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ����ȿ��˽����������");
				}
			}
			if("938".equals(codeValue.getCodeValue())){
				if(!StringUtil.isEmpty(vsl009) || !StringUtil.isEmpty(vsl007) || !StringUtil.isEmpty(vsl013)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��Ӧ�þ�Ϊ�գ�");
				}
				if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0215A")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ְ�����ơ���");
				}
			}	
			if("941".equals(codeValue.getCodeValue())){
				if(StringUtil.isEmpty(vsl007)){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"�����̶�ȡֵ��ӦΪ�գ�");
				}
				/*if(StringUtil.isEmpty(vru005) || !vru005.equalsIgnoreCase("A0215A")){
					throw new AppException("������Ϊ��"+codeValue.getCodeName()+"������ӦУ����Ϣ��ֻ��Ϊ��ְ�����ơ���");
				}*/
			}	
		}else{
			int countNotNull = 0;
			if(!StringUtil.isEmpty(vsl007)){//�̶�ֵ
				countNotNull++;
			}
			if(!StringUtil.isEmpty(vsl013)){//ѡ��ֵ
				countNotNull++;
			}
			if(!StringUtil.isEmpty(vsl009)){//�ȶ���Ϣ��
				countNotNull++;
			}
			//���� ���� �������ж��Ƿ��пո� ��������ո�
			if(countNotNull!=1){
				if(vsl006.equals("11") && (vru005.equalsIgnoreCase("a0111a") || vru005.equalsIgnoreCase("a0114a") || vru005.equalsIgnoreCase("a0101"))){
					
				}else{
					throw new AppException("���������ǡ�����������͡���Ӽ���������������̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ�����ҽ�������һ�Ϊ�գ�");
				}
				
			}
		}
		
		//2. gridΪ��ʱ����������ݣ��趨У����Ϣ����vru004��У����Ϣ�vru005�������޸�
		//��ȡ���ù��ܣ� mengl 20160426
		/*if(list==null || list.size()<1 || list.isEmpty()){
			this.getPageElement("vru004").setDisabled(true);
			this.getPageElement("vru005").setDisabled(true);
		}*/
		
		//3. �¼ӽű����б���
		HashMap<String, Object> map = new HashMap<String, Object>();
		VerifySqlList vsl = new VerifySqlList();
		this.copyElementsValueToObj(vsl, this);
		
		//3.1ͨ�ö��󿽱���Map��
		for(Field field :VerifySqlList.class.getDeclaredFields()){
			Method method;
			try {
				method = VerifySqlList.class.getDeclaredMethod("get"+field.getName().substring(0, 1).toUpperCase()+field.getName().substring(1));
				map.put(field.getName(),method.invoke(vsl)==null?"":method.invoke(vsl).toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new AppException("ȡֵ����");
			}
		}
		
		//3.2 ������ʾֵ��ֵ������ʹ��codeType���£�ֻ��������ʾ
		RuleSqlListBS.getCodeNameVerifySqlList(map, vsl);
		if("A3627".equals(vsl.getVsl004().toUpperCase())){
			map.put("vsl013", map.get("vsl013_name"));
		}
		list.add(map);
		
		//3.3 �жϲ�ͬ����:savecond-������modefyCond-�޸�ѡ����
		if("savecond".equals(eleName)){
			pe.setValueList(list);
			this.getPageElement("cueRowIndex").setValue("");//�������ݺ�ѡ�������
		}else if("modefyCond".equals(eleName)){
			((Grid)pe).updateRowData(cueRowIndex, map);//���¸�������
		}
		
		return EventRtnType.NORMAL_SUCCESS;	
	}
	
	/**
	 * ɾ��һ�����ݺ�ͳ��Grid����������������Ϊ0���趨У����Ϣ����vru004��У����Ϣ�vru005�����޸�
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("delCount")
	@NoRequiredValidate
	public int delCount() throws RadowException{
		List<HashMap<String, Object>> list = this.getPageElement("grid").getValueList();
		if(list==null || list.size()<1 || list.isEmpty()){
			this.getPageElement("vru004").setDisabled(false);
			this.getPageElement("vru005").setDisabled(false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ȫ��ɾ��Grid���趨У����Ϣ����vru004��У����Ϣ�vru005�����޸�
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("delAll.onclick")
	@NoRequiredValidate
	public int delAll() throws RadowException{
		/*this.createPageElement("vru004", ElementType.SELECT, false).setDisabled(false);
		this.createPageElement("vru005", ElementType.SELECT, false).setDisabled(false);*/
		/*this.getPageElement("vru004").setDisabled(false);
		this.getPageElement("vru005").setDisabled(false);*/
		this.getPageElement("grid").setValue("");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * �趨ѡ���кŵ�ҳ����,����ѡ���л��Ե��༭ҳ��
	 * @author mengl
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("grid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException, AppException{
		PageElement grid = this.getPageElement("grid"); 
		int cueRowIndex = grid.getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		
		String vru004 = (String)grid.getValue("vsl003");
		String vru005 = (String)grid.getValue("vsl004");
		String vsl006 = (String)grid.getValue("vsl006");
		String vsl007 = (String)grid.getValue("vsl007");
		String vsl013 = (String)grid.getValue("vsl013");
		String vsl008 = (String)grid.getValue("vsl008");
		String vsl009 = (String)grid.getValue("vsl009");
		String vsl012 = (String)grid.getValue("vsl012");
		
		//У����Ϣ��
		this.getPageElement("vru004").setValue(vru004);
		this.getPageElement("vsl003").setValue(vru004);
		
		//��Ϣ��onchange�¼�
		this.vru004Change();
		
		//У����Ϣ��
		this.getPageElement("vru005").setValue(vru005);
		this.getPageElement("vsl004").setValue(vru005);
		
		//��Ϣ��onchange�¼�
		this.vru005Change();
		
		//�����
		this.getPageElement("vsl006").setValue(vsl006);
		
		//�����onchange�¼�
		vsl006Onchange();
		
		this.getPageElement("vsl007").setValue(vsl007);
		this.getPageElement("vsl013").setValue(vsl013);
		this.getPageElement("vsl008").setValue(vsl008);
		
		//�ȶ���Ϣ��onchange�¼�
		vsl008Change();
		
		this.getPageElement("vsl009").setValue(vsl009);
		this.getPageElement("vsl012").setValue(vsl012);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**��ѡ������������
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvents({"addLeft.onclick","addRight.onclick","delLeft.onclick","delRight.onclick"})
	@AutoNoMask
	@NoRequiredValidate
	public int bracketsModify() throws RadowException, AppException{
		String eleName = this.getCueEventElementName();
		if(StringUtil.isEmpty(eleName)){
			throw new AppException("�����쳣������ϵ�����̣�����bracketsModify()������ͨ���¼����ã�");
		}
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("����ѡ��һ�У�");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==cueRowIndex){
				HashMap<String,Object> map = list.get(i);
				Object vsl002 = map.get("vsl002");//��������
				Object vsl011 = map.get("vsl011");//��������
				int left = (vsl002==null||vsl002.toString().trim().equals(""))?0:Integer.parseInt(vsl002.toString());
				int right = (vsl011==null||vsl011.toString().trim().equals(""))?0:Integer.parseInt(vsl011.toString());
				//1.���������
				if(eleName.equals("addLeft")){
					if(left<5){
						map.put("vsl002", left+1);
					}else{
						throw new AppException("һ�����������5�������ţ�");
					}

				//2.���������
				}else if(eleName.equals("addRight")){
					if(right<5){
						map.put("vsl011", right+1);
					}else{
						throw new AppException("һ�����������5�������ţ�");
					}
					
				//3.ɾ��������
				}else if(eleName.equals("delLeft")){
					if(left>0){
						map.put("vsl002", left-1);
					}
				//4.ɾ��������
				}else if(eleName.equals("delRight")){
					if(right>0){
						map.put("vsl011", right-1);
					}
				}
				newList.add(map);
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex);//ѡ�е�ǰ������
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**��ѡ��������һ��
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("upRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int upRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("����ѡ��һ�У�");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==0){
				throw new AppException("�Ѿ��ǵ�һ�У��������ƣ�");
			}
		}
		List<HashMap<String, Object>> list = pe.getValueList();
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex-1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex-1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex-1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex-1+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**��ѡ���н���һ��
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("downRow.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int downRow() throws RadowException, AppException{
		Grid pe = (Grid) this.getPageElement("grid");
		List<HashMap<String, Object>> list = pe.getValueList();
		String cueRowIndexStr = this.getPageElement("cueRowIndex").getValue();
		Integer cueRowIndex = null;
		if(cueRowIndexStr==null || cueRowIndexStr.trim().equals("")){
			throw new AppException("����ѡ��һ�У�");
		}else{
			cueRowIndex = Integer.parseInt(cueRowIndexStr);
			if(cueRowIndex==(list.size()-1)){
				throw new AppException("�Ѿ�������У��������ƣ�");
			}
		}
		
		List<HashMap<String, Object>> newList = new ArrayList<HashMap<String, Object>>();
		for(int i =0;i<list.size();i++){
			if(i==(cueRowIndex+1)){
				newList.add(list.get(cueRowIndex));
			}else if(i==cueRowIndex){
				newList.add(list.get(cueRowIndex+1));
			}else{
				newList.add(list.get(i));
			}
		}
		pe.setValueList(newList);
		pe.selectRow(cueRowIndex+1);
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+1+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	


	/**
	 * ��ѯ
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("grid.dogridquery")
	@NoRequiredValidate         
	public int dogridQuery(int start,int limit) throws RadowException{
		String vru001 = this.getPageElement("vru001").getValue();
		String sql = "";
		StringBuffer sqlBf = null;
		sqlBf = new StringBuffer("Select Vsl001,                                                                   ")
						.append("       Vru001,                                                                    ")
						.append("       Vsl002,                                                                    ")
						.append("       (Select Table_Name From Code_Table Where Table_Code = Vsl003) Vsl003_Name, ")
						.append("       (Select Col_Name                                                           ")
						.append("          From Code_Table_Col                                                     ")
						.append("         Where Table_Code = Vsl003                                                ")
						.append("           And Col_Code = Vsl004) Vsl004_Name,                                    ")
						.append("       Vsl003,                                                                    ")
						.append("       Vsl004,                                                                    ")
						.append("       Vsl005,                                                                    ")
						.append("       Vsl006,                                                                    ")
						.append("       Vsl007,                                                                    ")
						.append("       (Select Table_Name From Code_Table Where Table_Code = Vsl008) Vsl008_Name, ")
						.append("       (Select Col_Name                                                           ")
						.append("          From Code_Table_Col                                                     ")
						.append("         Where Table_Code = Vsl008                                                ")
						.append("           And Col_Code = Vsl009) Vsl009_Name,                                    ")
						.append("       Vsl008,                                                                    ")
						.append("       Vsl009,                                                                    ")
						.append("       Vsl010,                                                                    ")
						.append("       Vsl011,                                                                    ")
						.append("       Vsl012,                                                                    ")
						.append("       (Select Aaa103                                                             ")
						.append("          From v_Aa10                                                             ")
						.append("         Where Aaa102 = Vsl013                                                    ")
						.append("           And Aaa100 = (Select Upper(Nvl(Code_Type, Vsl004))                     ")
						.append("                           From Code_Table_Col                                    ")
						.append("                          Where Table_Code = Vsl003                               ")
						.append("                            And Col_Code = Vsl004)) Vsl013_Name,                  ")
						.append("       Vsl013                                                                     ")
						.append("  From Verify_Sql_List                                                            ")
						.append(" Where 1 = 1                                                                      ")
						.append(" and vru001='" + vru001+"'" )
						.append(" order by vsl014 " );
		sql = sqlBf.toString();
		if(DBUtil.getDBType()==DBType.MYSQL){
			sql = sql.replace("nvl(", "ifnull(").replace("Nvl(", "ifnull(");
		}
		this.pageQuery(sql,"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	/**
	 * ��ʾ���ͣ�vru003���ı���޸�У������
	 * @author mengl
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("vru003.onchange")
	@AutoNoMask
	@NoRequiredValidate
	public int vru003Onchange() throws RadowException{
		String vru003 = this.getPageElement("vru003").getValue();
		String vru004 = this.getPageElement("vru004").getValue();
		String vru005 = this.getPageElement("vru005").getValue();
		//�趨У���������
		if(!StringUtil.isEmpty(vru003) && !StringUtil.isEmpty(vru004) && !StringUtil.isEmpty(vru005)){
			String vru003Name = CodeManager.getValueByCode("VRU003", vru003);
			String vru004Name = RuleSqlListBS.getTableName(vru004);
			String vru005Name = RuleSqlListBS.getColName(vru004, vru005 );
			//this.getPageElement("vru002").setValue((vru003Name!=null?vru003Name:"")+"У��_"+vru004Name+"_"+vru005Name+"_");//+(vru002!=null ?vru002.trim():""));
			this.getPageElement("vru008").setValue("");
			//this.getPageElement("vru002").setValue(vru005Name);
			//this.getPageElement("vru008").setValue(vru003Name+":");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**vru004У����Ϣ���ı�󴥷�
	 * 
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws AppException 
	 * @throws SQLException
	 */
	@PageEvent("vru004.onchange")
	@NoRequiredValidate       
	@AutoNoMask
	public int vru004Change() throws RadowException, AppException{
		
		String vru004= this.getPageElement("vru004").getValue();
		this.getPageElement("vsl003").setValue(vru004);
		this.getPageElement("vsl004").setValue("");
		this.getPageElement("vru005").setValue("");
		
		TreeMap<String, String> treeMap;
		try {
			treeMap = RuleSqlListBS.getVru005byVru004(vru004,true);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("��ȡ��Ϣ���ѡֵ�쳣���쳣��Ϣ:"+e.getMessage());
		}
		( (Combo) this.getPageElement("vru005")).setValueListForSelect(treeMap);
		this.getPageElement("vsl003").setValue(vru004);
		this.getPageElement("vsl004").setValue("");
		this.getPageElement("vsl005").setValue("");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * vru005У����Ϣ��ı�󴥷�
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 * @throws AppException 
	 */
	@PageEvent("vru005.onchange")
	@NoRequiredValidate   
	@AutoNoMask
	public int vru005Change() throws RadowException, AppException{
		String vru005= this.getPageElement("vru005").getValue();
		String vru004= this.getPageElement("vru004").getValue();
		String vru003= this.getPageElement("vru003").getValue();
//		String vru002= this.getPageElement("vru002").getValue();
		//1.У��
		if(StringUtil.isEmpty(vru003)){
			this.getPageElement("vru005").setValue("");
			this.getPageElement("vsl004").setValue("");
			this.setMainMessage("��ѡ��У�����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(vru004)){
			this.getPageElement("vru005").setValue("");
			this.getPageElement("vsl004").setValue("");
			this.setMainMessage("����ѡ��У����Ϣ����");
			return EventRtnType.NORMAL_SUCCESS;
		}
		this.getPageElement("vsl004").setValue(vru005);
		
		//2.�趨У���������
		if(!StringUtil.isEmpty(vru005) && !StringUtil.isEmpty(vru003)){
			CodeValue vru003CodeValue = RuleSqlListBS.getCodeValue("VRU003", vru003);
			String vru003Name = vru003CodeValue!=null?vru003CodeValue.getCodeName():"";
			String vru004Name = RuleSqlListBS.getTableName(vru004);
			String vru005Name = RuleSqlListBS.getColName(vru004, vru005 );
			//this.getPageElement("vru002").setValue((vru003Name!=null?vru003Name:"")+"У��_"+vru004Name+"_"+vru005Name+"_");//+(vru002!=null ?vru002.trim():""));
			this.getPageElement("vru008").setValue("");
			//this.getPageElement("vru002").setValue(vru005Name);
			//this.getPageElement("vru008").setValue(vru003Name+":");
		}
		
		//3.�������õĲ����趨��ѡ ���������
		String vsl006s;
		try {
			vsl006s = RuleSqlListBS.getVsl006(vru004, vru005);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AppException("��ȡ��������������쳣��"+e.getMessage());
		}
		
		Combo vsl006Combo = (Combo)this.getPageElement("vsl006");
		vsl006Combo.setValue("");
		if(!StringUtil.isEmpty(vsl006s)){
//			vsl006Combo.loadDataForSelectStore("VSL006","","aaa102 in ("+vsl006s+")",true);
			vsl006Combo.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType("VSL006", "code_value IN  ("+vsl006s+")"));
		}else{
			this.setMainMessage("��Ӧ��Ϣ����Ϣ��û�����ÿ�ʹ�õ������������");
		}
		
		
		//4. �趨ѡ��У����Ϣ�����������
		VVerifyColVsl006 vcv = null;
		if(!StringUtil.isEmpty(vru005)){
			vcv = RuleSqlListBS.getVverifyColVsl006(vru004, vru005);
			if(vcv==null){
				throw new AppException("�޷���ȡ������Ϣ������Ϣ���������Ϣ������ϵ����Ա��");
			}
		}
		this.getPageElement("vsl005").setValue(vcv==null?"":vcv.getColDataType());
		
		//5.����ѡ���У����趨vsl013��ѡ��ȡֵ���Ŀ�ѡ��
		CodeTableCol cr = RuleSqlListBS.getCodeTableCol(vru004, vru005);
		Combo vsl013 = (Combo)this.getPageElement("vsl013");
		if(cr!=null && !StringUtil.isEmpty(cr.getCodeType())){
			vsl013.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType(cr.getCodeType().toUpperCase()));
		}else if(!StringUtil.isEmpty(vru005)){
			vsl013.setValueListForSelect(RuleSqlListBS.getAllMapByCodeType(vru005.toUpperCase()));
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������ı�����Ϊ��Ϊ�ա��򡾲�Ϊ�ա�����չ̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��
	 * @return
	 * @throws RadowException 
	 * @throws AppException 
	 */
	@PageEvent("vsl006.onchange")
	@AutoNoMask
	public int vsl006Onchange() throws RadowException, AppException{
		String vsl006 = this.getPageElement("vsl006").getValue();
		PageElement vsl013Ele = this.createPageElement("vsl013",ElementType.SELECT,false);	//�ȶ�ѡ��ֵ
		PageElement vsl007Ele = this.createPageElement("vsl007",ElementType.TEXT,false);	//�ȶԹ̶�ֵ
		PageElement vsl009Ele = this.createPageElement("vsl009",ElementType.SELECT,false);	//�ȶ���Ϣ��
		PageElement vsl008Ele = this.createPageElement("vsl008",ElementType.SELECT,false);	//�ȶ���Ϣ��
		if(StringUtil.isEmpty(vsl006)){
			vsl013Ele.setDisabled(false);
			vsl007Ele.setDisabled(false);
			vsl009Ele.setDisabled(false);
			vsl008Ele.setDisabled(false);
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//��ʼ���������޸ģ�
		vsl013Ele.setDisabled(false);
		vsl007Ele.setDisabled(false);
		vsl009Ele.setDisabled(false);
		vsl008Ele.setDisabled(false);
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		if(codeValue==null || StringUtil.isEmpty(codeValue.getSubCodeValue())){
			throw new AppException("��ȡ������������ò����쳣���쳣��Ϣ�������ֵ-"+vsl006);
		}
		String subCodeValue = codeValue.getSubCodeValue();
		
		//Ϊ������:�����ȶ�ֵ��Ϊ��
		if("3".equalsIgnoreCase(subCodeValue)){
			vsl013Ele.setValue("");
			vsl007Ele.setValue("");
			vsl009Ele.setValue("");
			vsl008Ele.setValue("");
			vsl013Ele.setDisabled(true);
			vsl007Ele.setDisabled(true);
			vsl009Ele.setDisabled(true);
			vsl008Ele.setDisabled(true);
		}else if("2".equalsIgnoreCase(subCodeValue) || "4".equalsIgnoreCase(subCodeValue) || "6".equalsIgnoreCase(subCodeValue) ){
		//�ַ�ģ����ѯ����ͳ��ȱȽ�������������������ֻʹ�ù̶��ȶ�ֵ
			vsl013Ele.setValue("");
			vsl009Ele.setValue("");
			vsl008Ele.setValue("");
			vsl013Ele.setDisabled(true);
			vsl009Ele.setDisabled(true);
			vsl008Ele.setDisabled(true);
		}else if("5".equalsIgnoreCase(subCodeValue)){
		//�ȶ���Ϣ��+�̶�ֵ��ֻʹ�ù̶��ȶ�ֵ�ͱȶ���Ϣ�����ȶ���Ϣ��
			vsl013Ele.setValue("");
			vsl013Ele.setDisabled(true);
		}else if("10".equalsIgnoreCase(subCodeValue) || "9".equalsIgnoreCase(subCodeValue) ||  "7".equalsIgnoreCase(subCodeValue) || "8".equalsIgnoreCase(subCodeValue)){
				vsl013Ele.setValue("");
				vsl007Ele.setValue("");
				vsl009Ele.setValue("");
				vsl008Ele.setValue("");
				vsl013Ele.setDisabled(true);
				vsl007Ele.setDisabled(true);
				vsl009Ele.setDisabled(true);
				vsl008Ele.setDisabled(true);
				if("941".equals(codeValue.getCodeValue())){
					vsl007Ele.setValue("");
					vsl007Ele.setDisabled(false);
				}
		}else{
			vsl013Ele.setDisabled(false);
			vsl007Ele.setDisabled(false);
			vsl009Ele.setDisabled(false);
			vsl008Ele.setDisabled(false);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**vsl008�ȶ���Ϣ���ı�󴥷�
	 * �ȶ��������������Ҫ��У������ͬ,��ѯ����VERIFY_COL_VSL006���趨�� ����Ӧ�õ��������͡�����Ϊ�п��ܱ�����Date����ȴ����Ϊvarchar2���͵����
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 * @throws AppException 
	 */
	@PageEvent("vsl008.onchange")
	@NoRequiredValidate    
	@AutoNoMask
	public int vsl008Change() throws RadowException, AppException{
		String vru004= this.getPageElement("vru004").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		this.getPageElement("vsl009").setValue("");
		if(StringUtil.isEmpty(vru005) || StringUtil.isEmpty(vru004)){
			this.setMainMessage("����ѡ��У����Ϣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		//�����������ȶ��������������Ҫ��У������ͬ,��ѯ����VERIFY_COL_VSL006���趨�� ����Ӧ�õ��������͡�����Ϊ�п��ܱ�����Date����ȴ����Ϊvarchar2���͵����
		VVerifyColVsl006 vcv = RuleSqlListBS.getVverifyColVsl006(vru004,vru005);
		String fliter = null;
		try {
			fliter = " colDataTypeShould = '"+vcv.getColDataTypeShould()+"'";
		} catch (Exception e) {
			e.printStackTrace();
			throw new AppException("��ȡУ����Ϣ����У����Ϣ�������ļ��쳣��");
		}
		TreeMap<String, String> treeMap = RuleSqlListBS.getVru005byVru004(vsl008,false,fliter);
		( (Combo) this.getPageElement("vsl009")).setValueListForSelect(treeMap);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ȶ�ֵ�ı�󴥷������ܣ�ȷ���̶�ȡֵ��ѡ��ȡֵ���ȶ���Ϣ��ֻ����ѡ��һ
	 * 
	 * @return
	 * @author mengl
	 * @throws RadowException
	 * @throws SQLException
	 */
	@PageEvents({"vsl007.onchange","vsl013.onchange","vsl009.onchange"})
	@NoRequiredValidate       
	@AutoNoMask
	public int compareValueChange() throws RadowException, SQLException{
		String eleName = this.getCueEventElementName();
		String vsl007= this.getPageElement("vsl007").getValue();
		String vsl013= this.getPageElement("vsl013").getValue();
		String vsl009= this.getPageElement("vsl009").getValue();
		String vsl008= this.getPageElement("vsl008").getValue();
		String vru005= this.getPageElement("vru005").getValue();
		String vru004= this.getPageElement("vru004").getValue();
		String vsl006= this.getPageElement("vsl006").getValue();//�����
		
		if(StringUtil.isEmpty(vru005) || StringUtil.isEmpty(vru004)){
			this.getPageElement("vsl009").setValue("");
			this.setMainMessage("����ѡ��У����Ϣ����У����Ϣ�");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtil.isEmpty(vsl006)){
			this.getPageElement("vsl013").setValue("");
			this.getPageElement("vsl009").setValue("");
			this.getPageElement("vsl008").setValue("");
			this.getPageElement("vsl007").setValue("");
			this.setMainMessage("����ѡ�������������");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		CodeValue codeValue = RuleSqlListBS.getCodeValue("VSL006", vsl006);
		String vsl006SubValue = codeValue!=null?codeValue.getSubCodeValue():"";//���������
		
		if("vsl007".equals(eleName)){
			if(!StringUtil.isEmpty(vsl007)){
				
				this.getPageElement("vsl013").setValue("");
				this.getPageElement("vsl009").setValue("");
				this.getPageElement("vsl008").setValue("");
				this.getPageElement("vsl007").setValue(vsl007);
			}
		}else if("vsl013".equals(eleName)){
			if(!StringUtil.isEmpty(vsl013)){
				this.getPageElement("vsl007").setValue("");
				this.getPageElement("vsl009").setValue("");
				this.getPageElement("vsl008").setValue("");
				this.getPageElement("vsl013").setValue(vsl013);
			}
			
		}else if("vsl009".equals(eleName)){
			if(!StringUtil.isEmpty(vsl009)){
				if(StringUtil.isEmpty(vsl008)){
					this.getPageElement("vsl009").setValue("");
					this.setMainMessage("����ѡ��ȶ���Ϣ����");
					return EventRtnType.NORMAL_SUCCESS;
				}else if(!"5".equals(vsl006SubValue)){//�Ǳȶ���Ϣ��+�̶�ֵ(��)
					this.getPageElement("vsl007").setValue("");
					this.getPageElement("vsl013").setValue("");
				}else{
					this.getPageElement("vsl013").setValue("");
				}
				
			}
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ҳ�����¼���
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnReload.onclick")
	@NoRequiredValidate
	public int reload() throws RadowException{
		this.reloadPage();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@Override
	public int doInit() throws RadowException {
		String parData = this.getRadow_parent_data();
		String[] parDatas = parData.split(",");
		this.getPageElement("vsc001").setValue(parDatas[0]);//�趨����ID
		this.getPageElement("vsl012").setValue("2");//�趨Ĭ�����ӷ�������
		
		//��ʼ�����趨��Ϣ����ѡ��
		Combo vru004 =  (Combo)this.createPageElement("vru004", ElementType.SELECT, false);
		Combo vsl008 =  (Combo)this.createPageElement("vsl008", ElementType.SELECT, false);
		Map<String,String> map = RuleSqlListBS.getAllCodeTablesMap();
		vru004.setValueListForSelect(map);
		vsl008.setValueListForSelect(map);
		
		if(parDatas.length>1 && !StringUtil.isEmpty(parDatas[1])){
			String vru001 = parDatas[1];
			VerifyRule vr = (VerifyRule) HBUtil.getHBSession().get(VerifyRule.class, vru001);
			//1.��ֵУ�������Ϣ���趨У������޸�
			PageModel.copyObjValueToElement(vr, this);
			this.getPageElement("vsl003").setValue(vr.getVru004());
			this.getPageElement("vsl004").setValue(vr.getVru005());
			/*this.createPageElement("vru004", ElementType.SELECT, false).setDisabled(true);
			this.createPageElement("vru005", ElementType.SELECT, false).setDisabled(true);*/
			//2.�޸Ľ������ء����沢��������ť
			this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSaveContinue').setVisible(false);");
			//3. ��ѯУ������Ӧ��SQL�б�
			this.setNextEventName("grid.dogridquery");
			//4.����У����onchange�¼�
			this.setNextEventName("vru004.onchange");
		}
		if(parDatas.length>1 && !StringUtil.isEmpty(parDatas[0])){
			String vsc001 = parDatas[0];
			String username=SysUtil.getCacheCurrentUser().getLoginname();
			VerifyScheme vs = (VerifyScheme)HBUtil.getHBSession().get(VerifyScheme.class, vsc001);
			//�趨�÷��������޸�
			if(vs!=null && !StringUtil.isEmpty(vs.getVsc007()) && vs.getVsc007().equals("1") && !"admin".equals(username)){
				//���ر��水ť
				this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSaveContinue').setVisible(false);");
				this.getExecuteSG().addExecuteCode("Ext.getCmp('btnSave').setVisible(false);");
			}
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}

}
