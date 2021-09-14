package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.Dataexchangeconf;

public class KingbsFilePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("confbtn.onclick")
	public int confbtn() throws RadowException{
		List<HashMap<String,Object>> list = this.getPageElement("Fgrid").getValueList();
		int countNum = 0;
		String fname = "";
		String fabsolutepath = "";
		for (int j = 0; j < list.size();j++) {
			HashMap<String, Object> map = list.get(j);
			Object check1 = map.get("checked");
			if (check1!= null && check1.equals(true)) {
				fname=map.get("fname")==null?"":map.get("fname").toString();
				fabsolutepath=map.get("fabsolutepath")==null?"":map.get("fabsolutepath").toString();
				countNum++;
			}
		}
		if(countNum==0){
			throw new RadowException("请勾选信息！");
		}else if(countNum>1){
			throw new RadowException("只能勾选一条信息！");
		}
//		this.createPageElement("fabsolutepath", ElementType.TEXTWITHICON, true).setValue(fabsolutepath);
//		this.createPageElement("fname", ElementType.HIDDEN, true).setValue(fname);
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('fabsolutepath').value='"+fabsolutepath+"';");
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('fname').value='"+fname+"';");
		this.closeCueWindow("winFile");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	/**
	 * 查询文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("zzbsearch")
	public int lfsearch() throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String ZZB3_FILE = "D:/KingbsData";
		try {
			List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
			if (list !=null && list.size()>0) {
				ZZB3_FILE = list.get(0).getZzbthreepath();
			} else {
				this.setMainMessage("导入前请先配置相应信息。");
				return EventRtnType.NORMAL_SUCCESS;
			}
			File file = new File(ZZB3_FILE);
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			Grid grid = (Grid)this.createPageElement("Fgrid", ElementType.GRID, false);
			if(file.exists()){
				File[] subFiles = file.listFiles();
				if(subFiles!=null && subFiles.length>0){
					for (int i = 0; i < subFiles.length; i++) {
						File file0= subFiles[i];
						String name = file0.getName();
						if(name.substring(name.lastIndexOf(".") + 1).equalsIgnoreCase("zzb3")){
							HashMap<String, Object> map = new HashMap<String, Object>();
							map.put("fname", name);
							map.put("fabsolutepath", file0.getAbsolutePath().replace("\\", "/"));
							gridList.add(map);
						}
					}
				}
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
		} catch (Exception e) {
			throw new RadowException("检索目录失败:"+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 刷新
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("zzbsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public void closeCueWindow(String arg0) {
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('"+arg0+"').close();");
	}

}
