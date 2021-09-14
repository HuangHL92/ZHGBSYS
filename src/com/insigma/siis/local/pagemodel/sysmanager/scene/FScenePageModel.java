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
		 * 给grid加载数据
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
		 * 初始化
		 */
		@Override
		public int doInit() {  
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}     
	
		/**
		 * 增加场景
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
		 *修改选中的场景
		 * @throws RadowException 
		 */
		@PageEvent("editSceneBtn.onclick")
		public int UpdateBtn(String params) throws RadowException{
			int result = chooseScene("scenegrid");
			if(result== ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("请先选中要修改的场景");
				return EventRtnType.FAILD;
			}else if(result==CHOOSE_OVER_TOW){
				this.isShowMsg=true;
				this.setMainMessage("不能同时修改多个场景场景，请选择一个");
				return EventRtnType.FAILD;
			}
			this.openWindow("updateSceneWin",	"pages.sysmanager.scene.UpdateSceneWindow");
			this.setRadow_parent_data(this.getPageElement("scenegrid").getValue("name",result).toString());
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		/**
		 * 修改场景信息的双击事件
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("scenegrid.rowdbclick")
		public int usergridOnRowDbClick() throws RadowException{  //打开窗口的实例
			this.openWindow("updateSceneWin","pages.sysmanager.scene.UpdateSceneWindow");//事件处理完后的打开窗口事件
			this.setRadow_parent_data(this.getPageElement("scenegrid").getValue("name",this.getPageElement("scenegrid").getCueRowIndex()).toString());
			return EventRtnType.NORMAL_SUCCESS;		
		}		
		
		
		
		/**
		 * 删除选中的场景
		 * @throws RadowException 
		 * @throws RadowException 
		 */
		@PageEvent("deleteSceneBtn.onclick")
		public int deleteBntOnClick() throws RadowException{
			int i = chooseScene("scenegrid");
			if(i==ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("请先选中要删除的场景");
				return EventRtnType.FAILD;
			}
			this.addNextEvent(NextEventValue.YES, "samedeleteEvent");
			this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");//其下次事件需要参数值，在此设置下次事件的参数值
			this.setMessageType(EventMessageType.CONFIRM); //消息框类型，即confirm类型窗口
			this.setMainMessage("您确实要删除选中的场景吗？"); //窗口提示信息
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		/**
		 * 真正的删除选中的场景
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
						this.setMainMessage("操作失败,原因是选中的场景中"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		/**
		 * 查询场景的信息
		 * @return EventRtnType.FAILD  操作失败 EventRtnType.NOMAL_SUCCESS 操作成功
		 * @throws RadowException 
		 */
		@PageEvent("querySceneBtn.onclick")
		public int QuerySceneBtnOnClick() throws RadowException{
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		
		
		
		
		/**
		 * 还原场景
		 * @throws RadowException 
		 */
		@PageEvent("reuseSceneBtn.onclick")
		public int ReuseSceneBtnOnClick() throws RadowException{
			int choose = chooseScene("scenegrid");
			if(choose == ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("请先选中要还原的场景");
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
						this.setMainMessage("操作失败,原因是选中的场景"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		/**
		 * 注销场景
		 * @throws RadowException 
		 */
		@PageEvent("revokeSceneBtn.onclick")
		public int RevokeSceneBtnOnClick() throws RadowException{
			int choose = chooseScene("scenegrid");
			if(choose == ON_ONE_CHOOSE){
				this.isShowMsg=true;
				this.setMainMessage("请先选中要注销的场景");
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
						this.setMainMessage("操作失败,原因是选中的场景"+e.getMessage());
						this.setNextEventName("scenegrid.dogridquery");
						return EventRtnType.FAILD;
					}
				}
			}
			this.setNextEventName("scenegrid.dogridquery");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		/**
		 * 取消后执行的事件
		 * @return
		 * @throws RadowException
		 */
		@PageEvent("cannelEvent")
		public int cannelEvent() throws RadowException{ //带参数的自定义事件
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		
		/**
		 * 私有方法，是否选中用户
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
				return ON_ONE_CHOOSE;//没有选中任何用户
			}else if(result>2){
				return CHOOSE_OVER_TOW;//选中多于一个用户
			}
			return number;//选中第几个用户
		}
}
