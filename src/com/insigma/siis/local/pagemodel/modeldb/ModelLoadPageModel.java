package com.insigma.siis.local.pagemodel.modeldb;

import java.util.Calendar;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.BeanUtil;
import com.insigma.siis.local.business.entity.Sublibrariesmodel;
import com.insigma.siis.local.business.modeldb.ModeldbBS;
import com.insigma.siis.local.business.modeldb.SublibrariesmodelDTO;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class ModelLoadPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		String id = this.getRadow_parent_data();
		if(!StringUtil.isEmpty(id)){
			Sublibrariesmodel model = ModeldbBS.LoadSublibrariesmodel(id.replace("[", "").replace("]", ""));
			if(model!=null){
				PageModel.copyObjValueToElement(model, this);
				//�趨ԭ�ֿ�����
				this.getPageElement("sub_libraries_model_type_old").setValue(model.getSub_libraries_model_type());
			}
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * �ֿ����͸ı䴥������ʾ�޸ķֿ����ͣ���ɾ�����ý�������ø�����ֿ�δ����
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("sub_libraries_model_type.onchange")
	public int modelTypeChange() throws RadowException{
		String oldType = this.getPageElement("sub_libraries_model_type_old").getValue();
		String newType = this.getPageElement("sub_libraries_model_type").getValue();
		String modelId = this.getPageElement("sub_libraries_model_id").getValue();
		String runState = this.getPageElement("run_state").getValue();
		if(!StringUtil.isEmpty(oldType) && !StringUtil.isEmpty(newType) && !StringUtil.isEmpty(modelId) && !StringUtil.isEmpty(runState) && "1".equals(runState) && !oldType.equals(newType)){
			this.setMainMessage("��ʾ���޸ķֿ����ͣ���ɾ�����ý�������ø�����ֿ�δ���ã�������ֿ����������ã�");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	@PageEvent("save.onclick")
	@Transaction
	public int saveOrUpdate() throws RadowException, AppException {
		int  commentLenth = this.getPageElement("sub_libraries_model_info").getValue().length();
		if(commentLenth>200){
			throw new AppException("����˵���ַ����ܳ���200��");
		}
		String  name = this.getPageElement("sub_libraries_model_name").getValue();
		if(StringUtil.isEmpty(name)){
			throw new AppException("�������Ʋ���Ϊ�գ�");
		}
		//�ж��Ƿ����ú��޸��˷ֿ����ͣ�����޸��ˣ���ɾ��ԭ������Ϣ�����趨Ϊ��δ���á�  mengl 20160526
		String modelId = this.getPageElement("sub_libraries_model_id").getValue();
		String modelType = this.getPageElement("sub_libraries_model_type").getValue();
		Sublibrariesmodel mod = null;
		HBSession sess = HBUtil.getHBSession();
		if(!StringUtil.isEmpty(modelId)){
			mod = (Sublibrariesmodel) sess.get(Sublibrariesmodel.class, modelId);
			if("1".equals(mod.getRun_state()) && !modelType.equals(mod.getSub_libraries_model_type())){
				this.getPageElement("run_state").setValue("0");//��Ϊδ����
				ModeldbBS.delSublibrariesmodel(modelId, 1, false);//ɾ�����ý��
			}
			
		}else{
			mod = new Sublibrariesmodel();
		}
		//��¼��־ʹ��
		SublibrariesmodelDTO dto = new SublibrariesmodelDTO();
		BeanUtil.copy(mod, dto);
		
		this.copyElementsValueToObj(mod, this);
		int res =ModeldbBS.saveOrUpdateModel(mod);
		
		//��¼��־
		try {
			new LogUtil().createLog(res==0?"650":"651", "SUB_LIBRARIES_MODEL",
					mod.getSub_libraries_model_id(),
					mod.getSub_libraries_model_name(), null,
					Map2Temp.getLogInfo(dto, mod));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		this.createPageElement("MGrid", "grid", true).reload();
		this.closeCueWindow("createWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void main(String[] args) { 
		  Calendar c = Calendar.getInstance(); 
		  long a = c.getTimeInMillis(); 
		  CommonQueryBS.systemOut(a+""); 
		  CommonQueryBS.systemOut(System.currentTimeMillis()+""); 
		 } 
}
