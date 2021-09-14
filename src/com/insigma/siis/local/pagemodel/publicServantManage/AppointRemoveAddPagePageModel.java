package com.insigma.siis.local.pagemodel.publicServantManage;

import java.io.IOException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 拟任免
 * @author fujun
 *
 */
public class AppointRemoveAddPagePageModel extends PageModel{
	private LogUtil applog = new LogUtil();
	
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	public int saveDegrees()throws RadowException, AppException{
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("subWinIdBussessId").getValue();
		A01 a01 = (A01)sess.get(A01.class, a0000);
		if(a0000==null||"".equals(a0000)){//
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		try {
			/*//拟任免信息集
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			a53.setA0000(a0000);
			A53 a53_old = (A53)sess.get(A53.class, a0000);
			if(a53_old==null){
				a53_old = new A53();
			}
			PropertyUtils.copyProperties(a53_old, a53);
			sess.saveOrUpdate(a53_old);	*/
			
			//拟任免保存	id生成方式为uuid 。 如果是新增 将id设置为null
			A53 a53 = new A53();
			this.copyElementsValueToObj(a53, this);
			
			if(a53.getA5399()==null||"".equals(a53.getA5399())){
				a53.setA5399(SysManagerUtils.getUserId());
			}
			a53.setA0000(a0000);
			A53 a53_old = null;
			if("".equals(a53.getA5300())){
				a53.setA5300(null);
				a53_old = new A53();
				applog.createLog("3531", "A53", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a53_old,a53));
			}else{
				a53_old = (A53)sess.get(A53.class, a53.getA5300());
				applog.createLog("3532", "A53", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a53_old,a53));
			}
			PropertyUtils.copyProperties(a53_old, a53);
			
			sess.saveOrUpdate(a53_old);
			this.getPageElement("a5300").setValue(a53_old.getA5300());
			this.getPageElement("a5399").setValue(a53_old.getA5399());
			
		} catch (Exception e) {
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		this.setMainMessage("保存成功！");
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
		//this.getPageElement("a5399").setValue(SysManagerUtils.getUserId());
		//对页面赋值
		if(a0000!=null && !"".equals(a0000)){
			// 拟任免加载
			String sqlA53 = "from A53 where a0000='" + a0000 + "' and a5399='"+SysManagerUtils.getUserId()+"'";
			List listA53 = sess.createQuery(sqlA53).list();
			if (listA53 != null && listA53.size() > 0) {
				A53 a53 = (A53) listA53.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(a53, this);
				//旧值 判断是否修改
				String json = objectToJson(a53);
				this.getExecuteSG().addExecuteCode("parent.A53value="+json+";");
			}else{
				String date = DateUtil.getcurdate();
				A53 a53 = new A53();
				a53.setA5321(date);
				a53.setA5323(date);
				a53.setA5327(SysManagerUtils.getUserName());
				//PMPropertyCopyUtil.copyObjValueToElement(a53, this);
			}
		}
		//this.getExecuteSG().addExecuteCode("document.getElementById('a5399').value='"+SysManagerUtils.getUserId()+"';");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
     * 将实体POJO转化为JSON
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
