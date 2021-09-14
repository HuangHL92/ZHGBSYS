package com.insigma.siis.local.pagemodel.lfsearch;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.BeanCopyUtil;
import com.insigma.siis.local.jtrans.IteratorUD;
import com.insigma.siis.local.jtrans.UDPackFile;
import com.insigma.siis.local.pagemodel.repandrec.plat.RecieiveHzbThread;

/**
 * �������ļ��б�
 * @author gezh
 *
 */
public class LFSearchPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ��ѯ�ļ�
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("lfsearch")
	public int lfsearch() throws RadowException{
		try {
			Grid grid = (Grid)this.createPageElement("grid", ElementType.GRID, false);
			List<String> param = new ArrayList<String>();
			param.add("OTHER");
			param.add("HZB");
			List<UDPackFile> udpfs =  IteratorUD.iteratorDir(param);
			List<HashMap<String, Object>> gridList = new ArrayList<HashMap<String,Object>>();
			for (UDPackFile udpf : udpfs) {
				HashMap<String, Object> hm = new HashMap<String, Object>();
				BeanCopyUtil.CopytoHashMap(udpf, hm);
				hm.put("transTypeCn", "up".equals(udpf.getTransType())?"�ϱ�":"�·�");
				gridList.add(hm);
			}
			if(gridList.size()>0){
				grid.setValueList(gridList);
			}else{
				grid.setValueList(new ArrayList<HashMap<String,Object>>());
			}
			return EventRtnType.NORMAL_SUCCESS;
		} catch (Exception e) {
			throw new RadowException("����������Ŀ¼ʧ��:"+e.getMessage());
		}
	}
	
	@PageEvent("btnsx.onclick")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("lfsearch");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static Thread trd = null;
	
	@PageEvent("receiveData")
	public int receiveData(String filename)throws RadowException{
		try {
			if(trd!=null && trd.isAlive()){
				this.setMainMessage("���ճ������ڽ������ݣ����Ժ����ԡ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			String id = UUID.randomUUID().toString().replace("-", "");	//ˢ�¼�¼���� id 
			CurrentUser user = SysUtil.getCacheCurrentUser();   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
			KingbsconfigBS.saveImpDetailInit5(id);//ˢ�����ݵĴ�����
			UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
			RecieiveHzbThread thr = new RecieiveHzbThread(filename, id, user,userVo);
			trd = new Thread(thr,"���յ����߳�1");
			trd.start();
			
//			this.setRadow_parent_data(id);
//			this.openWindow("refreshWin", "pages.dataverify.RefreshOrgExp");
			this.getExecuteSG().addExecuteCode("$h.openWin('refreshWin','pages.dataverify.RefreshOrgExp','У������',580,150,'"+id+"','"+request.getContextPath()+"');");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("orgdataverify")
	public int orgdataverify(String uuid)throws RadowException{
		try {
//			this.setRadow_parent_data("2@" + uuid);
//			this.openWindow("dataVerifyWin", "pages.sysorg.org.orgdataverify.OrgDataVerify");
			this.getExecuteSG().addExecuteCode("$h.openWin('dataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify','У������',1130,592,'2@"+uuid+"','"+request.getContextPath()+"');");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
}
