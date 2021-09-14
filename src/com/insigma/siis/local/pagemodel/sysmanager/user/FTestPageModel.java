package com.insigma.siis.local.pagemodel.sysmanager.user;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.EventDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.EditorGrid;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class FTestPageModel extends PageModel {

	
	private static final int CHOOSE_OVER_TOW = 2;
	private static final int ON_ONE_CHOOSE = -1;
	
	/**
	 * ������ʾ���õĶԻ���
	 * @return EventRtnType.FAILD  ����ʧ�� EventRtnType.NOMAL_SUCCESS �����ɹ�
	 */
	/*
	@PageEvent("resetPasswordBtn.onclick")
	public int resetPasswordBtnOnClick() {
		this.addNextEvent(NextEventValue.YES, "resetPasswordEvent");
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ���������������"); //������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}
	*/
	/**
	 * ȡ����ִ�е��¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("cannelEvent")
	public int cannelEvent() throws RadowException{ //���������Զ����¼�
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������ִ����������
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("resetPasswordEvent")
	public int resetPassword() throws RadowException {
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		String userId = cueUser.getId();
		String oldPassword = cueUser.getPasswd();
		try {
			PrivilegeManager.getInstance().getIUserControl().updatePassword(
					userId, oldPassword, "admin");
		} catch (PrivilegeException e) {
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setMainMessage("��������ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ��ѯ�û�����Ϣ
	 * @return EventRtnType.FAILD  ����ʧ�� EventRtnType.NORMAL_SUCCESS �����ɹ�
	 * @throws RadowException 
	 */
	@PageEvent("toolBarBtn1.onclick")
	public int btnOnClick() throws RadowException {
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * �޸��û����¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("updateBnt.onclick")
	public int updateOnClick() throws RadowException {
		int result = choosePerson("usergrid");
		if (result == ON_ONE_CHOOSE) {
			this.isShowMsg = true;
			this.setMainMessage("����ѡ��Ҫ�޸ĵ��û�");
			return EventRtnType.FAILD;
		}
		if (result == CHOOSE_OVER_TOW) {
			this.isShowMsg = true;
			this.setMainMessage("����ͬʱ�޸Ķ���û�����ѡ��һ���û�");
			return EventRtnType.FAILD;
		}
		this.openWindow("win1", "pages.sysmanager.user.FWindow");
		this.setRadow_parent_data(this.getPageElement("usergrid").getValue(
					"loginname", result).toString());
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/**
	 * �޸��û���Ϣ��˫���¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("usergrid.rowdbclick")
	public int usergridOnRowDbClick() throws RadowException{  //�򿪴��ڵ�ʵ��
		this.openWindow("win1",	"pages.sysmanager.user.FWindow");//�¼��������Ĵ򿪴����¼�
		this.setRadow_parent_data(this.getPageElement("usergrid").getStringValue("loginname"));
		return EventRtnType.NORMAL_SUCCESS;		
	}

	/**
	 * ɾ��ѡ�е��û�
	 * 
	 * @throws RadowException
	 * @throws RadowException
	 */
	@PageEvent("deleteBnt.onclick")
	public int deleteBntOnClick() throws RadowException {
		int i = choosePerson("usergrid");
		if (i == ON_ONE_CHOOSE) {
			this.isShowMsg = true;
			this.setMainMessage("����ѡ��Ҫɾ�����û�");
			return EventRtnType.FAILD;
		}
		this.addNextEvent(NextEventValue.YES, "samedeleteEvent");
		this.addNextEvent(NextEventValue.CANNEL, "cannelEvent");// ���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); // ��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪɾ��ѡ�е��û���"); // ������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ������ɾ��ѡ�е��û�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("samedeleteEvent")
	public int deleteBntOnClicl() throws RadowException {
		this.isShowMsg = false;
		PageElement pe = this.getPageElement("usergrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object logchecked = map.get("check1");
			if (logchecked.equals(true)) {
				UserVO user = new UserVO();
				String userid = (String) this.getPageElement("usergrid")
						.getValue("id", i);
				user.setId(userid);
				try {
					PrivilegeManager.getInstance().getIUserControl()
							.deleteUser(user);
				} catch (PrivilegeException e) {
					this.isShowMsg = true;
					this.setMainMessage("����ʧ��,ԭ����ѡ�е��û���" + e.getMessage());
					this.setNextEventName("usergrid.dogridquery");
					return EventRtnType.FAILD;
				}
			}
		}
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * ˽�з������Ƿ�ѡ���û�
	 * 
	 * @throws RadowException
	 */
	private int choosePerson(String gridid) throws RadowException {
		int result = 1;
		int number = 0;
		PageElement pe = this.getPageElement(gridid);
		List<HashMap<String, Object>> list = pe.getValueList();
		for (int i = 0; i < list.size(); i++) {
			HashMap<String, Object> map = list.get(i);
			Object check1 = map.get("check1");
			if (check1.equals(true)) {
				number = i;
				result++;
			}
		}
		if (result == 1) {
			return ON_ONE_CHOOSE;// û��ѡ���κ��û�
		}
		if (result > 2) {
			return CHOOSE_OVER_TOW;// ѡ�ж���һ���û�
		}
		return number;// ѡ�еڼ����û�
	}

	/**
	 * �����û���ť�¼�
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("toolBarBtn2.onclick")
	public int btn2OnClick() throws RadowException {
		this.openWindow("createUserWin", "pages.sysmanager.user.AddWindow");
		return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * ִ��ɾ���Ĳ�����ʾ��
	 * 
	 * @param params
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("dogriddelete")
	public int dogridDelete(String params){
		this.addNextEvent(NextEventValue.YES, "deleteEvent",params);
		this.addNextEvent(NextEventValue.CANNEL,"cannelEvent");//���´��¼���Ҫ����ֵ���ڴ������´��¼��Ĳ���ֵ
		this.setMessageType(EventMessageType.CONFIRM); //��Ϣ�����ͣ���confirm���ʹ���
		this.setMainMessage("��ȷʵҪִ��ɾ��������"); //������ʾ��Ϣ
		return EventRtnType.NORMAL_SUCCESS;
	}


	/**
	 * ��ѯ�¼�
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("deleteEvent")
	public int dogriddeleteOnClick(String params) throws RadowException{
		String userid = null;
		UserVO user = new UserVO();
		user.setId(params);
		try {
			PrivilegeManager.getInstance().getIUserControl().deleteUser(user);
		} catch (PrivilegeException e) {
			this.setMainMessage("����ʧ�ܣ�ԭ����"+e.getMessage());
			return EventRtnType.FAILD;
		}
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("usergrid.afteredit")
	public int usergridAfterEdit() throws RadowException{
		this.isShowMsg=false;
		String userid = null;
		String loginname = null;
		String username = null;
		String useful = null;
		String description = null;
		String owner = null;
		EditorGrid pe  = (EditorGrid)getPageElement("usergrid");
		userid = pe.getStringValue("id");
		CommonQueryBS.systemOut(userid);
		useful = pe.getStringValue("status");
		CommonQueryBS.systemOut(useful);
		if(useful.equals("��Ч")){
			useful="1";
		}
		if(useful.equals("��Ч")){
			useful="0";
		}
		loginname = pe.getStringValue("loginname");
		username = pe.getStringValue("name");
		description = pe.getStringValue("desc");
		owner = pe.getStringValue("ownerId");
		UserVO user = new UserVO();
		user.setId(userid);
		user.setLoginname(loginname);
		user.setName(username);
		user.setStatus(useful);
		user.setDesc(description);
		user.setOwnerId(owner);
		try {
			PrivilegeManager.getInstance().getIUserControl().updateUser(user);
			pe.reload();
		} catch (PrivilegeException e) {
			this.isShowMsg=true;
			pe.reload();
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.NORMAL_SUCCESS;
		
	}
	
	@PageEvent("usergrid.dogridquery")
	@EventDataRange("panel_content")
	public int usergridQuery(int start, int limit) throws RadowException,
			AppException {
		HashMap params = new HashMap();
		List list = null;
		List clist = null;
		UserVO cueUser = PrivilegeManager.getInstance().getCueLoginUser();
		PageElement pe = this.getPageElement("panel_content.loginname");
		PageElement pe2 = this.getPageElement("panel_content.name");
		boolean usernameBoolean = (pe2 != null && !pe2.getValue().equals(""));
		boolean loginnameBoolean = (pe != null && !pe.getValue().equals(""));
		if (loginnameBoolean) {
			params.put("loginname", pe.getValue());
			if (usernameBoolean) {
				params.put("name", pe2.getValue());
			}
		} else {
			if (usernameBoolean) {
				params.put("name", pe2.getValue());
			}
		}
		try {
			if (!usernameBoolean && !loginnameBoolean) {
				list = PrivilegeManager.getInstance().getIUserControl()
						.queryByUser(cueUser, start, -1, null);
				this.setSelfDefResData(this
						.getPageQueryData(list, start, limit));
			} else {
				clist = PrivilegeManager.getInstance().getIUserControl()
						.queryByUser(cueUser, start, -1, params);
				this.setSelfDefResData(this.getPageQueryData(clist, start,
						limit));
			}
		} catch (PrivilegeException e) {
			this.isShowMsg = true;
			this.setMainMessage(e.getMessage());
			return EventRtnType.FAILD;
		}
		return EventRtnType.SPE_SUCCESS;
	}

	
	
	/**
	 * ��ԭ�û�
	 * @throws RadowException 
	*/
	/*
	@PageEvent("reuseUserBnt.onclick")
	public int ReuseSceneBtnOnClick() throws RadowException{
		int choose = choosePerson("usergrid");
		if(choose == ON_ONE_CHOOSE){
			this.isShowMsg=true;
			this.setMainMessage("����ѡ��Ҫ��ԭ���û�");
			return EventRtnType.FAILD;
		}
		PageElement pe = this.getPageElement("usergrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("check1");
			if(logchecked.equals(true)){
				String id = (String) this.getPageElement("usergrid").getValue("id", i);
				try {
					PrivilegeManager.getInstance().getIUserControl().reuseUser(id);
				} catch (PrivilegeException e) {
					this.isShowMsg=true;
					this.setMainMessage("����ʧ��,ԭ����ѡ�е��û�"+e.getMessage());
					this.setNextEventName("usergrid.dogridquery");
					return EventRtnType.FAILD;
				}
			}
		}
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	*/
	
	/**
	 * ���
	 * @throws RadowException 
	 */
	@PageEvent("clearBtn.onclick")
	public int clearOnClick() throws RadowException{
		this.getPageElement("panel_content.loginname").setValue("");
		this.getPageElement("panel_content.name").setValue("");
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * ע���û�
	 * @throws RadowException 
	*/
	@PageEvent("revokeUserBnt.onclick")
	public int RevokeUserBtnOnClick() throws RadowException{
		int choose = choosePerson("usergrid");
		if(choose == ON_ONE_CHOOSE){
			this.isShowMsg=true;
			this.setMainMessage("����ѡ��Ҫע�����û�");
			return EventRtnType.FAILD;
		}
		PageElement pe = this.getPageElement("usergrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		for(int i=0;i<list.size();i++){
			HashMap<String, Object> map = list.get(i);
			Object logchecked =  map.get("check1");
			if(logchecked.equals(true)){
				String id = (String) this.getPageElement("usergrid").getValue("id", i);
				try {
					PrivilegeManager.getInstance().getIUserControl().revokeUser(id);
				} catch (PrivilegeException e) {
					this.isShowMsg=true;
					this.setMainMessage("����ʧ��,ԭ����ѡ�е��û�"+e.getMessage());
					this.setNextEventName("usergrid.dogridquery");
					return EventRtnType.FAILD;
				}
			}
		}
		this.setNextEventName("usergrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ��ʼ������
	 * @throws RadowException 
	 */
	@Override
	public int doInit() {  
		return EventRtnType.NORMAL_SUCCESS;
	}     
}
