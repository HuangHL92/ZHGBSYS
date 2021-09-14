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
 * 退出管理
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
			this.setMainMessage("请先保存人员基本信息！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
		List<Object> list=sess.createSQLQuery(sql).list();
		try {
			//退出管理信息集
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
			//判断退出本单位日期：应晚于参加工作时间。
			String a0134 = a01.getA0134();//参加工作时间
			String a3004 = a30.getA3004();//退出本单位日期
			if(a0134!=null&&!"".equals(a0134)&&a3004!=null&&!"".equals(a3004)){
				if(a0134.length()==6){
					a0134 += "00";
				}
				if(a3004.length()==6){
					a3004 += "00";
				}
				if(a3004.compareTo(a0134)<0){
					this.setMainMessage("退出本单位日期不能早于参加工作时间");
					return EventRtnType.NORMAL_SUCCESS;
				}
			}
			String orgid=this.getPageElement("orgid").getValue();
			String a3007a=a30.getA3007a();
			String a3007=this.getPageElement("a3007a_combo").getValue();
			String a3001 = a30.getA3001();
			if(a3001!=null&&!"".equals(a3001)){
				//调出人员     历史库
				if(!a3001.startsWith("1")&&!a3001.startsWith("2")&&StringUtil.isEmpty(orgid)){
					 this.setMainMessage("退出单位不能为空");
					 return EventRtnType.FAILD; 
				}
				if(a3001.startsWith("1")||a3001.startsWith("2")){
					//调往单位是其他单位的的直接设置为非现职人员,有填写的在新的单位添加一条空的职务信息
					if(null == a30.getA3007a() || "".equals(a30.getA3007a())){
						this.setMainMessage("调往单位不能为空");
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
				}else if("35".equals(a3001)){//死亡  显示：已去世。       查询：历史人员
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("2");
					//}
					
				}else if("31".equals(a3001)){//离退休 显示：离退人员。     查询：离退人员
					a01.setA0163("2");
					a01.setOrgid(orgid);
					//if(!"4".equals(a01.getStatus())){
					a01.setStatus("3");
					//}
					
				}else{//【因选举退出登记】【开除】【辞退】【辞去公职】【其它】 显示：其它人员。     查询：历史人员
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
				applog.createLog("3301", "A30", a01.getA0000(), a01.getA0101(), "新增记录", new Map2Temp().getLogInfo(a30_old,a30));
				
			}else{
				applog.createLog("3302", "A30", a01.getA0000(), a01.getA0101(), "修改记录", new Map2Temp().getLogInfo(a30_old,a30));
			}
			PropertyUtils.copyProperties(a30_old, a30);
			
			sess.saveOrUpdate(a30_old);
			
		    this.getExecuteSG().addExecuteCode("setParentA0163('"+a01.getA0163()+"')");
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
		//对页面赋值
		if(a0000!=null && !"".equals(a0000)){
			A01 a01=(A01) sess.get(A01.class, a0000);
			//退出管理加载
			Map<String,String> map=new LinkedHashMap<String,String>();
			String sql="select a0201a,a0201b from a02 where a0000='"+a0000+"' order by a0255 desc, a0223";
			List<Object[]> list=sess.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(Object[] a02:list){
					map.put(a02[1].toString(), a02[0].toString());
				}
				((Combo)this.getPageElement("orgid")).setValueListForSelect(map);
			}else{
				this.setMainMessage("职务信息为空无法退出管理");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('save').disable()");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('a3007a_combo').disable();Ext.query('#a3007a_combo+img')[0].onclick=null;document.getElementById('a3007a_combo').value='';");
				this.getExecuteSG().addExecuteCode("odin.ext.getCmp('orgid_combo').disable();document.getElementById('orgid').value='';");
				return EventRtnType.FAILD;
			}
			
			A30 a30 = (A30) sess.get(A30.class, a0000);
			if (a30 != null) {
				PMPropertyCopyUtil.copyObjValueToElement(a30, this);
				//旧值 判断是否修改
				String json = objectToJson(a30);
				this.getExecuteSG().addExecuteCode("parent.A30value="+json+";");
				
				//判断退出管理方式是否为调入或转出
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
							this.getPageElement("a3007a_combo").setValue(a3007a);//机构名称 中文。
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
