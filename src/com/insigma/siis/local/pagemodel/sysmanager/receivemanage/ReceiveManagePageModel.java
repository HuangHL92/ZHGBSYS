package com.insigma.siis.local.pagemodel.sysmanager.receivemanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.epsoft.task.TimerJob;
import com.insigma.siis.local.epsoft.util.BeanCopyUtil;
import com.insigma.siis.local.jtrans.HlPolingTask;
import com.insigma.siis.local.jtrans.IteratorUD;
import com.insigma.siis.local.jtrans.UDPackFile;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;

/**
 * 待接收文件列表
 * @author gezh
 *
 */
public class ReceiveManagePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询文件
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lfsearch")
	public int lfsearch() throws RadowException{
		try {
			Grid grid = (Grid)this.createPageElement("grid", ElementType.GRID, false);
			List<String> param = new ArrayList<String>();
			param.add("JC");
			param.add("ZB");
			param.add("DM");
			List<UDPackFile> udpfs =  IteratorUD.iteratorDir(param);
//			List<UDPackFile> udpfs =  IteratorUD.iteratorDir();
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			for (UDPackFile udpf : udpfs) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				BeanCopyUtil.CopytoHashMap(udpf, hm);
				hm.put("transTypeCn", "up".equals(udpf.getTransType())?"上报":"下发");
				gridList.add(hm);
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			throw new RadowException("检索待接收目录失败:"+e.getMessage());
		}
	}
	
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		List<TransConfig> jfccs = sess.createQuery("from TransConfig t where t.type='0' and t.status='1'").list();
		ZwhzFtpClient.hlPoling(jfccs);
		try {
			Grid grid = (Grid)this.createPageElement("grid", ElementType.GRID, false);
//			List<UDPackFile> udpfs =  IteratorUD.iteratorDir();
			List<String> param = new ArrayList<String>();
			param.add("JC");
			param.add("ZB");
			param.add("DM");
			List<UDPackFile> udpfs =  IteratorUD.iteratorDir(param);
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			for (UDPackFile udpf : udpfs) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				BeanCopyUtil.CopytoHashMap(udpf, hm);
				hm.put("transTypeCn", "up".equals(udpf.getTransType())?"上报":"下发");
				gridList.add(hm);
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
			this.request.getSession().setAttribute("HTTP_SESSION_ATTRIBUTE_IS_EXP_FILE_END", "1");
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			throw new RadowException("检索待接收目录失败:"+e.getMessage());
		}
	}
	
	@PageEvent("recieve")
	public int reload(String filename)throws RadowException{
		if(filename.contains("信息校验方案")||filename.contains("扩展代码集发布")||filename.contains("补充信息项发布")){
			this.setRadow_parent_data(filename);
			this.openWindow("QueryRecieveWin", "pages.repandrec.plat.QueryRecieve");
		}else{
			this.getExecuteSG().addExecuteCode("addTab()");
		}
		//System.out.println(filename);
		return EventRtnType.NORMAL_SUCCESS;

	}
}
