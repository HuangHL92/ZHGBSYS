package com.insigma.siis.local.pagemodel.sysmanager.scene;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.SceneVO;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class FScenePageModel extends PageModel {

		private static final int CHOOSE_OVER_TOW = 2;
		private static final int ON_ONE_CHOOSE = -1;
		/**
		 * ��grid��������
		 * @param start
		 * @param limit
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("scenegrid.dogridquery")
		@EventDataRange("sceneQueryContent")
		public int doscenegridQuery(int start,int limit) throws RadowException{
			HashMap params = new HashMap();
			PageElement pe = this.getPageElement("sceneQueryContent.name");
			PageElement pe2 = this.getPageElement("sceneQueryContent.description");
			boolean usernameBoolean = (pe2!=null&&!pe2.getValue().equals(""));
			boolean loginnameBoolean  = (pe!=null&&!pe.getValue().equals(""));
			if(loginnameBoolean){
				params.put("name", pe.getValue());
				if(usernameBoolean){
					params.put("description",pe2.getValue());
				}
			}else{
				if(usernameBoolean){
					params.put("description",pe2.getValue());
				}
			}
			UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
			try {
				if(!usernameBoolean&&!loginnameBoolean){
					List list = PrivilegeManager.getInstance().getISceneControl().queryByUser(cueUser, start, limit, null);
					this.setSelfDefResData(this.getPageQueryData(list, start, limit));
				}else{
					List clist = PrivilegeManager.getInstance().getISceneControl().queryByUser(cueUser, start, -1, params);
					this.setSelfDefResData(this.getPageQueryData(clist, start, limit));
				}
			} catch (PrivilegeException e) {
				e.printStackTrace();
			}
			return EventRtnType.SPE_SUCCESS;				
		}

		/**
		 * ��ʼ��
		 */
		@Override
		public int doInit() {  
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}     
	
		/**
		 * ���ӳ���
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("addSceneBtn.onclick")
		public int btn2OnClick() throws RadowException{
			this.isShowMsg = false;
			this.openWindow("addSceneWin",	"pages.sysmanager.scene.AddSceneWindow");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		

		/**
		 *�޸�ѡ�еĳ���
		 * @throws RadowException 
		 */
		@PageEvent("editSceneBtn.onclick")
		public int UpdateBtn(String params) throws RadowException{
			int result = chooseScene("scenegrid");
			if(result== ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("����ѡ��Ҫ�޸ĵĳ���");
				return EventRtnType.FAILD;
			}else if(result==CHOOSE_OVER_TOW){
				this.isShowMsg=true;
				this.setMainMessage("����ͬʱ�޸Ķ��������������ѡ��һ��");
				return EventRtnType.FAILD;
			}
			this.openWindow("updateSceneWin",	"pages.sysmanager.scene.UpdateSceneWindow");
			this.setRadow_parent_data(this.getPageElement("scenegrid").getValue("name",result).toString());
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		/**
		 * �޸ĳ�����Ϣ��˫���¼�
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("scenegrid.rowdbclick")
		public int usergridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
			this.openWindow("updateSceneWin","pages.sysmanager.scene.UpdateSceneWindow");//�¼��������Ĵ򿪴����¼�
			this.setRadow_parent_data(this.getPageElement("scenegrid").getValue("name",this.getPageElement("scenegrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;		
		}		
		
		
		
		/**
		 * ɾ��ѡ�еĳ���
		 * @throws RadowException 
		 * @throws RadowException 
		 */
		@PageEvent("deleteSceneBtn.onclick")
		public int deleteBntOnClick() throws RadowException{
			int i = chooseScene("scenegrid");
			if(i==ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("����ѡ��Ҫɾ���ĳ���");
				return EventRtnType.FAILD;
			}
			this.addNextEvent(NextEventValue.YES, "samedeleteEvent");
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
			this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
			this.setMainMessage("��ȷʵҪɾ��ѡ�еĳ�����"); //������ʾ��Ϣ
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		/**
		 * ������ɾ��ѡ�еĳ���
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("samedeleteEvent")
		public int deleteBntOnClicl() throws RadowException{
			this.isShowMsg=false;
			PageElement pe = this.getPageElement("scenegrid");
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(map.get("checked")==""){
					map.put("checked", false);
				}
				Object logchecked =  map.get("checked");
				if(logchecked.equals(true)){
					SceneVO scene = new SceneVO();
					String sceneid = (String) this.getPageElement("scenegrid").getValue("sceneid", i);
					scene.setSceneid(sceneid);
					try {
						CommonQueryBS.systemOut("status--->"+scene.getStatus());
						PrivilegeManager.getInstance().getISceneControl().deleteScene(scene);
					} catch (PrivilegeException e) {
						this.isShowMsg=true;
						this.setMainMessage("����ʧ��,ԭ����ѡ�еĳ�����"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		/**
		 * ��ѯ��������Ϣ
		 * @return EventRtnType.FAILD  ����ʧ�� EventRtnType.NOMAL_SUCCESS �����ɹ�
		 * @throws RadowException 
		 */
		@PageEvent("querySceneBtn.onclick")
		public int QuerySceneBtnOnClick() throws RadowException{
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
		
		
		/**
		 * ��ԭ����
		 * @throws RadowException 
		 */
		@PageEvent("reuseSceneBtn.onclick")
		public int ReuseSceneBtnOnClick() throws RadowException{
			int choose = chooseScene("scenegrid");
			if(choose == ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("����ѡ��Ҫ��ԭ�ĳ���");
				return EventRtnType.FAILD;
			}
			PageElement pe = this.getPageElement("scenegrid");
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(map.get("checked")==""){
					map.put("checked", false);
				}
				Object logchecked =  map.get("checked");
				if(logchecked.equals(true)){
					String sceneid = (String) this.getPageElement("scenegrid").getValue("sceneid", i);
					try {
						PrivilegeManager.getInstance().getISceneControl().reuseScene(sceneid);
					} catch (PrivilegeException e) {
						this.isShowMsg=true;
						this.setMainMessage("����ʧ��,ԭ����ѡ�еĳ���"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		/**
		 * ע������
		 * @throws RadowException 
		 */
		@PageEvent("revokeSceneBtn.onclick")
		public int RevokeSceneBtnOnClick() throws RadowException{
			int choose = chooseScene("scenegrid");
			if(choose == ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("����ѡ��Ҫע���ĳ���");
				return EventRtnType.FAILD;
			}
			PageElement pe = this.getPageElement("scenegrid");
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				if(map.get("checked")==""){
					map.put("checked", false);
				}
				Object logchecked =  map.get("checked");
				if(logchecked.equals(true)){
					String sceneid = (String) this.getPageElement("scenegrid").getValue("sceneid", i);
					try {
						PrivilegeManager.getInstance().getISceneControl().revokeScene(sceneid);
					} catch (PrivilegeException e) {
						this.isShowMsg=true;
						this.setMainMessage("����ʧ��,ԭ����ѡ�еĳ���"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		/**
		 * ȡ����ִ�е��¼�
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("cannelEvent")
		public int cannelEvent() throws RadowException{ //���������Զ����¼�
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		/**
		 * ˽�з������Ƿ�ѡ���û�
		 * @throws RadowException 
		 */
		private int chooseScene(String gridid) throws RadowException{
			int result = 1;
			int number = 0;
			PageElement pe = this.getPageElement(gridid);
			List<HashMap<String, Object>> list = pe.getValueList();
			for(int i=0;i<list.size();i++){
				HashMap<String, Object> map = list.get(i);
				Object check1 =  map.get("checked");
				if(check1.equals(true)){
					number=i;
					result++;
				}
			}
			if(result==1){
				return ON_ONE_CHOOSE;//û��ѡ���κ��û�
			}else if(result>2){
				return CHOOSE_OVER_TOW;//ѡ�ж���һ���û�
			}
			return number;//ѡ�еڼ����û�
		}
}
