package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.slabel.A0196Tag;
import com.insigma.siis.local.business.slabel.ExtraTags;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

/**
 * 熟悉领域
 * 
 * @author zhubo
 *
 */
public class PZZDGWSXLYPageModel extends PageModel {

	private LogUtil applog = new LogUtil();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String id = this.getPageElement("subWinIdBussessId").getValue();
		CommQuery cqbs=new CommQuery();
		try {
			String[] arr=id.split("##");
			String sql="select * from "+arr[0]+" where "+arr[1];
			List<HashMap<String, Object>> list=cqbs.getListBySQL(sql);
			if(list!=null&list.size()>0) {
				HashMap<String, Object> map=list.get(0);
				this.getPageElement("a0196z").setValue(map.get("csdesc2")==null?"":map.get("csdesc2").toString());
				String a0194s=map.get("mxcs2")==null?"":map.get("mxcs2").toString();
				if (a0194s != null && !"".equals(a0194s)) {
					String[] num = a0194s.split(",");
					for (int i = 0; i < num.length; i++) {
						if(num[i].length()==1) {
							
						}else {
							this.getPageElement("tag" + num[i]).setValue("1");
						}
					}
					this.getPageElement("a0196s").setValue(a0194s);
				}
			}
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("save.onclick")
	@Transaction
	@Synchronous(true)
	@NoRequiredValidate
	public int saveA0194zInfo() throws RadowException, AppException {
		String id = this.getPageElement("subWinIdBussessId").getValue();
		String a0196z = this.getPageElement("a0196z").getValue();
		String a0196s = this.getPageElement("a0196s").getValue();
		try {
			String[] arr=id.split("##");
			HBSession sess = HBUtil.getHBSession();
			String sql="update "+arr[0]+" set mxcs2='"+a0196s+"',csdesc2='"+a0196z+"' where "+arr[1];
			Statement stmt = sess.connection().createStatement();
			stmt.executeUpdate(sql);
			stmt.close();
			this.getExecuteSG().addExecuteCode("parent.document.getElementById('code2').value='"+a0196z+"';parent.document.getElementById('sxly').value='"+a0196s+"';window.close();");
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("保存失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("fullAll")
	public int fullAll(String str) throws RadowException, AppException {
		String a0196z = this.getPageElement("a0196z").getValue();
		String a0196s = this.getPageElement("a0196s").getValue();
		try {
			String[] arr=str.split("##");
			String type=arr[0];
			String value=arr[1];
			String valuename=arr[2];
			String[] arr1=value.split(",");
			String[] arr2=valuename.split(",");
			if("1".equals(type)) {//全选
				for(int i=0;i<arr1.length;i++) {
					a0196z = a0196z.replace(arr2[i]+",", "").replace(arr2[i], "");
					a0196s = a0196s.replace(arr1[i]+",", "").replace(arr1[i], "");
					if( a0196z == null || "".equals(a0196z) ){
						a0196z = arr2[i];
					}else{
						a0196z = a0196z + "," + arr2[i];
					}	
					if( a0196s == null || "".equals(a0196s)){
						a0196s = arr1[i];
					}else{
						a0196s = a0196s  + "," + arr1[i];
					}
					this.getPageElement("tag" + arr1[i]).setValue("1");
				}
			}else {//全不选
				for(int i=0;i<arr1.length;i++) {
					a0196z = a0196z.replace(arr2[i]+",", "").replace(arr2[i], "");
					a0196s = a0196s.replace(arr1[i]+",", "").replace(arr1[i], "");
					this.getPageElement("tag" + arr1[i]).setValue("0");
				}
			}
			if(a0196z!=null && !"".equals(a0196z) && ",".equals(a0196z.substring(a0196z.length()-1))){
				a0196z=a0196z.substring(0,a0196z.length()-1);
				a0196s=a0196s.substring(0,a0196s.length()-1);
			}
			this.getPageElement("a0196s").setValue(a0196s);
			this.getPageElement("a0196z").setValue(a0196z);
		} catch (Exception e) {
			e.printStackTrace();
			this.setMainMessage("全选失败！");
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
