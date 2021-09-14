package com.insigma.odin.framework.radow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.SQLManager;
import com.insigma.odin.framework.comm.query.PageQueryData;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.bean.EventResBean;
import com.insigma.odin.framework.radow.bean.SQLInfo;
import com.insigma.odin.framework.radow.data.IDataStore;
import com.insigma.odin.framework.radow.data.PMHList;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.radow.util.PmHListUtil;
import com.insigma.odin.framework.sys.SysfunctionManager;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.zj.Utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <h1>�����ҳ��ģ����</h1>
 * <p>
 * ������Ա�ڿ�����������Ҫʵ��һ�������ҳ��ģ���࣬��ҳ��ģ������Ҫ�̳д˳���ҳ��ģ�ͣ���ÿ�������ҳ��ģ�Ͷ�����ʵ�֡�doInit����ʼ���¼�����ô��ʲô��ҳ��ģ�����ˣ����ǿ��Խ������Ϊһ�������ǵĹ���JSPҳ���Ӧ�ҶԵȵ�Java�࣬
 * �亭����ҳ������������InputԪ�����ͺ�ֵ��֧������ģʽ����ĳDIV�µ�Input�������̵�������·����contextPath�������������¼������Ƶȵȡ�
 * </P>
 * <p>
 * ��ҳ���ϴ�����ĳ���¼�ʱ�����ǵĿ�ܻ��Զ���ȡ��������Ҫ�Ĳ��������ඨ�������ǵľ���PageModel����ĳ�����ı�ע�ϣ������䴫�غ�̨�������ݾ������Ǹ�Ԫ�ص�ʲô�¼�
 * ���þ���PageModel�ĸ÷�����������ɺ����Զ���ȡ����Ӧ��Ϣ���з��أ�����������ǿ��ͳһ������Ӧ����ȻҲ���ɿ�����Ա���д�������¼������д����¼�ʱ��Ҫ���߿��
 * ����ĺ�������ʲô����ܾͻὫ�����Ȩ���ƽ����ú������д���
 * </P>
 * <p>Company: �㽭���¶���������޹�˾</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>ʱ�䣺 2009-10</p>
 * @author Jinwei
 */
public abstract class PageModel {

	/**
	 * ҳ��Ԫ��
	 */
	private PageElement webPage;
	
	/**
	 * ������·��
	 */
	public String contextPath = null;
	
	/**
	 * ҳ������
	 */
	public HttpServletRequest request;

	/**
	 * ҳ��ģ�͵��¼����ϣ�Ϊ�˶�̬ע������õģ�
	 */
	private List<String> pageEvents = new ArrayList<String>();
	
	/**
	 * ҳ�������JSON����
	 */
	private String reqJSONContent;
	
	/**
	 * �����洢ÿ���¼��ĳɹ���ʧ�ܺ����Ϣ
	 */
	private String mainMessage  = "";
	
	/**
	 * �´��¼�����
	 */
	private List<NextEvent> nextEvents = new ArrayList<NextEvent>();
	
	/**
	 * ��ǰ�¼���
	 */
	private String cueEventName = null;
	
	/**
	 * ��Ϣ����
	 */
	private String messageType = EventMessageType.ALERT;
	
	/**
	 * �Զ����¼���Ӧ����  ����ʱֱ�ӽ�����Ϊajax��Ӧ����
	 */
	private Object selfDefResData = null;
	
	/**
	 * �����游ҳ���һЩ�ؼ����ݣ��Ƚ��ʺ�ͨ��Odin��ǩ��Window�����������������Ϊ��ʼ������
	 */
	@SuppressWarnings("unused")
	private String radow_parent_data = "";
	
	/**
	 * �Ƿ���ʾ��ʾ��
	 */
	public boolean isShowMsg = false;
	
	/**
	 * ִ������������
	 */
	private IExecuteSeqGenerator executeSG;
	/**
	 * ҳ��ģ�ͽ�����
	 */
	private IPageModelParser pageModelParser;
	/**
	 * ҳ�����ݴ洢��ִ����
	 */
	private IDataStore dataStore;
	
	
	/**
	 * �Զ�����Ӧ������
	 */
	private String selfResponseFunc = null;
	
	/**
	 * �������������в�����Ϣ
	 */
	private HashMap<String,String> requestParamer = new HashMap<String,String>();
	
	/**
	 * ��PageModel�����������
	 * @param key ��
	 * @param value ֵ
	 */
	public void putParameter(String key,String value){
		this.requestParamer.put(key, value);
	}
	/**
	 * ȡ������ķǿ���Զ�����Ĳ���
	 * @param key ��������
	 * @return
	 */
	public String  getParameter(String key){
		return this.requestParamer.get(key);
	}
	
	public HashMap<String, String> getRequestParamer() {
		return requestParamer;
	}
	public void addPageEvent(String event){
		pageEvents.add(event);
	}
	
	public void setReqJSONContent(String reqJSONContent) {
		this.reqJSONContent = reqJSONContent;
	}

	public void setExecuteSG(IExecuteSeqGenerator executeSG) {
		this.executeSG = executeSG;
	}
	
	public IExecuteSeqGenerator getExecuteSG() {
		return executeSG;
	}



	public IPageModelParser getPageModelParser() {
		return pageModelParser;
	}



	public void setPageModelParser(IPageModelParser pageModelParser) {
		this.pageModelParser = pageModelParser;
	}



	/**
	 * ҳ��ĳ�ʼ��
	 * @throws RadowException 
	 */
	@PageEvent("doInit")
	public abstract int doInit() throws RadowException;
	
	/**
	 * ���ɷ��ض���
	 * @param eventResCode
	 * @return
	 */
	public EventResBean generateEventRes(int eventResCode){
		EventResBean erb = new EventResBean();
		erb.setElementsScript(executeSG.getExecuteSeq());
		if(!this.mainMessage.equals("")){
			erb.setMainMessage(this.mainMessage);
		}
		if(eventResCode==EventRtnType.FAILD) {
			erb.setMessageCode("1");
		}else if(eventResCode==EventRtnType.NORMAL_SUCCESS) {
			erb.setMessageCode("0");
			if(this.selfDefResData!=null){
				erb.setData(this.selfDefResData);
			}
		}else if(eventResCode==EventRtnType.SPE_SUCCESS || eventResCode==EventRtnType.XML_SUCCESS) {
			erb.setMessageCode("0");
			erb.setData(this.selfDefResData);
			erb.setResType(eventResCode);
		}
		erb.setNextEvents(this.nextEvents);
		erb.isShowMsg = this.isShowMsg;
		erb.messageType = this.messageType;
		erb.selfResponseFunc = this.selfResponseFunc;
		return erb;
	}
	
	/**
	 * ��ȡҳ��Ԫ��
	 * @param elementName ҳ��Ԫ�ص�ID��д��֧��"div1.aab004" ��ȡҳ����div1�����aab004
	 * @return
	 * @throws RadowException
	 */
	public PageElement getPageElement(String elementName) throws RadowException{
		return pageModelParser.parseElement(elementName);
	}
	
	/**
	 * ����һ��Ԫ�أ������������¼�Ҫ����ֵ��Ԫ��û������������Ҫ�ı���ֵ������ʱ��Ҫͨ���˷���������û������
	 * ��ҳ����ڵ�Ԫ�أ���Ҳ���ڸ�ҳ����
	 * @param elementName Ԫ����
	 * @param elementType Ԫ������
	 * @param isParent �Ƿ��ڸ�ҳ��
	 * @return
	 */
	public PageElement createPageElement(String elementName,String elementType,boolean isParent){
		PageElement pe =  PageElementFactory.getInstance(elementType, this);
		pe.setParentPageElement(isParent);
		pe.setId(elementName);
		pe.setName(elementName);
		return pe;
	}
	
	public String getReqJSONContent() {
		return reqJSONContent;
	}

	public PageElement getWebPage() {
		return webPage;
	}

	public void setWebPage(PageElement webPage) {
		this.webPage = webPage;
	}

	public String getMainMessage() {
		return mainMessage;
	}

	public void setMainMessage(String mainMessage) {
		this.isShowMsg = true;
		this.mainMessage = mainMessage;
	}

	public Object getSelfDefResData() {
		return selfDefResData;
	}
	
	/**
	 * ����һ���Զ�������ݶ��󣬽�����Ϊ�����¼���Ӧ��Ajax�������PageQueryData�ȵ� 
	 * ���������ַ��е�xml�ļ��������뽫�¼�����������ΪEventRtnType.XML_SUCCESS
	 * ����Ĭ��ΪEventResBean
	 * @param selfDefResData �κ�����
	 */
	public void setSelfDefResData(Object selfDefResData) {
		this.selfDefResData = selfDefResData;
	}
	/**
	 * ��ȡ������ĸ�ҳ�����ֵ
	 * �丸ҳ��������ù�������Ϊ��
	 * @return
	 * @throws RadowException
	 */
	public String getRadow_parent_data() throws RadowException {		
		return this.getPageElement("radow_parent_data").getValue();
	}
	/**
	 * ���õ�ǰ��radow_parent_dataԪ�ص�ֵ���Թ��´ε�������ȥget
	 * ����ֵֻ���ڸ�ҳ���´�Ҫ������ҳ��Ĳ������Ա�ҳ�沢���κ�Ӱ��
	 * @param radow_parent_data
	 * @throws RadowException
	 */
	public void setRadow_parent_data(String radow_parent_data) throws RadowException {
		this.radow_parent_data = radow_parent_data;
		this.getPageElement("radow_parent_data").setValue(radow_parent_data);
	}

	public IDataStore getDataStore() {
		return dataStore;
	}

	public void setDataStore(IDataStore dataStore) {
		this.dataStore = dataStore;
	}

	/**
	 * ��һ������ 
	 * @param windowId ���ڵ�id
	 * @param pageModel ��pages.sample.SimpleWindow
	 */
	public void openWindow(String windowId,String pageModel){
		this.getExecuteSG().addExecuteCode("radow.openWindow('"+windowId+"','"+pageModel+"');");
		this.isShowMsg = false;
	}
	/**
	 * ��ȡ�ر�һ�����ڶ���Ľű�
	 * @param windowId
	 */
	public void closeCueWindow(String windowId){
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('"+windowId+"').hide();");
	}
	/**
	 * �ر�ͨ��window.open�򿪵Ĵ���
	 */
	public void closeCueWindow(){
		this.getExecuteSG().addExecuteCode("window.close();");
	}
	
	/**
	 * �ر�ͨ��window.open�򿪵Ĵ���,�����ȷ����ťʱ
	 */
	public void closeCueWindowByYes(){
		this.addNextBackFunc(NextEventValue.YES, "window.close();");
	}
	
	/**
	 * ����һ��alert�򣬲�����ȷ��ʱ�رյ�ǰ����
	 * @param windowId
	 */
	public void closeCueWindowByYes(String windowId){
		this.setShowMsg(true);
		this.addNextBackFunc(NextEventValue.YES, "parent.odin.ext.getCmp('"+windowId+"').hide();");
	}  
	
	public List<NextEvent> getNextEvents() {
		return nextEvents;
	}
	/**
	 * ���һ���´���Ӧ�¼�
	 * @param ne
	 */
	public void addNextEvent(NextEvent ne){
		this.nextEvents.add(ne);
	}
	/**
	 * ����һ����ݵ���������´δ������¼�
	 * @param nextEvents
	 */
	public void setNextEvents(List<NextEvent> nextEvents) {
		this.nextEvents = nextEvents;
	}
	/**
	 * ��ȡ��ǰ����Ϣ������
	 * @return
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * ������Ϣ�������
	 * @param messageType ����ΪEventMessageType�������
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * ��ǰ�Ƿ�Ҫ��ʾ������Ϣ��
	 * @return
	 */
	public boolean isShowMsg() {
		return isShowMsg;
	}
	
	/**
	 * �����Ƿ񵯳���Ϣ��
	 * @param isShowMsg
	 */
	public void setShowMsg(boolean isShowMsg) {
		this.isShowMsg = isShowMsg;
	}
	/**
	 * ����һ�����´��¼�����������ĳ����ť���²Ŵ���������ֱ�Ӵ���
	 * @param eventName
	 */
	public void setNextEventName(String eventName){
		NextEvent ne = new NextEvent();
		ne.setNextEventName(eventName);
		this.addNextEvent(ne);
	}
	/**
	 * ���õ�����Ϣ��Ļص�����
	 * @param nextBackFunc
	 */
	public void setNextBackFunc(String nextBackFunc){		
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES);
		ne.setNextBackFunc(nextBackFunc);
		this.addNextEvent(ne);
	}
	
	/**
	 * ����һ���´εĴ����¼�
	 * @param nextEventValue �´δ����¼��İ��°�ť��ֵ�����Ƿ��ǰ�����Ϣ���ȷ����ȡ��
	 * @param nextEventName �´ε��¼���
	 */
	public void addNextEvent(String nextEventValue,String nextEventName){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue); 
		ne.setNextEventName(nextEventName);
		this.addNextEvent(ne);
	}
	
	/**
	 * ����һ���´εĴ����¼�
	 * @param nextEventValue �´δ����¼��İ��°�ť��ֵ�����Ƿ��ǰ�����Ϣ���ȷ����ȡ��
	 * @param nextEventName �´ε��¼���
	 * @param nextEventParameter �´��¼��Ĳ���ֵ�����ɴ��ص��´��¼���Ӧ������ȥ
	 * @param nextBackFunc �����´��¼���ͬʱ���ܰ���ʲô��ť����ִ�иú���
	 */
	public void addNextEvent(String nextEventValue,String nextEventName,String nextEventParameter,String nextBackFunc){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue); 
		ne.setNextEventName(nextEventName);
		ne.setNextBackFunc(nextBackFunc);
		this.addNextEvent(ne);
	}
	
	/**
	 * ����һ���´εĴ����¼�
	 * @param nextEventValue �´δ����¼��İ��°�ť��ֵ�����Ƿ��ǰ�����Ϣ���ȷ����ȡ��
	 * @param nextEventName �´ε��¼���
	 * @param nextEventParameter �´��¼��Ĳ���ֵ�����ɴ��ص��´��¼���Ӧ������ȥ
	 */
	public void addNextEvent(String nextEventValue,String nextEventName,String nextEventParameter){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue);
		ne.setNextEventName(nextEventName);
		ne.setNextEventParameter(nextEventParameter); 
		this.addNextEvent(ne);
	}
	
	/**
	 * �����´ε�һ���ص�JS��������������nextEventValue������İ�ťʱ ȥ����nextBackFunc����
	 * @param nextEventValue
	 * @param nextBackFunc
	 */
	public void addNextBackFunc(String nextEventValue,String nextBackFunc){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue);
		ne.setNextBackFunc(nextBackFunc);
		this.addNextEvent(ne);
	}
	
	/**
	 * ������Ӧ�Ĳ������ˢ��ҳ��
	 * @throws RadowException 
	 */
	public void reloadPage() throws RadowException{
		if(this.getWebPage()==null){
			this.pageModelParser.parse();
		}
		this.executeSG.addExecuteCode(this.getWebPage().reload());
	}
	
	/**
	 * ����Ϊ�е����������£��Ұ���YESȷ����reloadPage
	 * @throws RadowException
	 */
	public void reloadPageByYes()throws RadowException{
		if(this.getWebPage()==null){
			this.pageModelParser.parse();
		}
		this.setNextBackFunc(this.webPage.reload());
	}
	
	/**
	 * ��PMHList�ĵ�һ����ѯ��� �Զ��PageModel��Ԫ����ȥ
	 * ���ݸ�ʽ��[{\"aae135\":\"false\",\"aac003\":\"\",\"rewage\":\"\",\"household\":\"\",\"household_combo\":\"����ѡ��...\",\"wlwgry\":\"\",\"wlwgry_combo\":\"����ѡ��...\",\"ylcblb\":\"\",\"ylcblb_combo\":\"����ѡ��...\",\"ybcblb\":\"\",\"ybcblb_combo\":\"����ѡ��...\"}]
	 * @param hlist
	 * @throws RadowException 
	 */
	public void autoFillPm(PMHList hlist) throws RadowException{
		PmHListUtil.autoFillPm(hlist, this);
	}
	/**
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * @param pm
	 * @param sql ��ѯ��sql
	 * @param sqlType ��ѯ�����HQL��SQL
	 * @param start ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit ȡ����������Ҫ���ڷ�ҳʱ
	 * @return
	 * @throws RadowException 
	 */
	public PageQueryData pageQuery(String sql,String sqlType,int start,int limit) throws RadowException{
		sql = SQLManager.getNewQuerySqlByOrdOrFilterStr(sql, sqlType, requestParamer);
		//SQLManager.saveQueryInfo(sql, sqlType, start, limit, this);
		if(start==0){
			try {
				SQLInfo info = new SQLInfo();
				info.setSql(sql);
				info.setSqlType(sqlType);
				this.request.getSession().setAttribute(SysfunctionManager.getCurrentSysfunction().getFunctionid()+"@"+this.getCueEventElementName(),info);
			} catch (AppException e) {
				throw new RadowException(e.getMessage(),(Exception)e.getMyException());
			}
		}
		return PmHListUtil.pageQuery(this, sql, sqlType,start, limit);
	}
	
	
	/**
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * @param pm
	 * @param sql ��ѯ��sql
	 * @param sqlType ��ѯ�����HQL��SQL
	 * @param start ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit ȡ����������Ҫ���ڷ�ҳʱ
	 * @param map �Զ���ҳ�����ݣ�SQL��ѯ������ʱ Map<String,List<String>>
	 * @return
	 * @throws RadowException 
	 */
	public PageQueryData pageQuery(String sql,String sqlType,int start,int limit,HashMap<String,List<String>> map) throws RadowException{
		sql = SQLManager.getNewQuerySqlByOrdOrFilterStr(sql, sqlType, requestParamer);
		if(start==0){
			try {
				SQLInfo info = new SQLInfo();
				info.setSql(sql);
				info.setSqlType(sqlType);
				this.request.getSession().setAttribute(SysfunctionManager.getCurrentSysfunction().getFunctionid()+"@"+this.getCueEventElementName(),info);
			} catch (AppException e) {
				throw new RadowException(e.getMessage(),(Exception)e.getMyException());
			}
		}
		return PmHListUtil.pageQuery(this, sql, sqlType,start, limit,map);
	}
	
	/**
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲���
	 * @param pm
	 * @param sql ��ѯ��sql
	 * @param sqlType ��ѯ�����HQL��SQL
	 * @param start ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit ȡ����������Ҫ���ڷ�ҳʱ
	 * @return
	 * @throws RadowException 
	 */
	public PageQueryData pageQueryNoCount(String sql,String sqlType,int start,int limit,int totalCount) throws RadowException{
		sql = SQLManager.getNewQuerySqlByOrdOrFilterStr(sql, sqlType, requestParamer);
		//SQLManager.saveQueryInfo(sql, sqlType, start, limit, this);
		if(start==0){
			try {
				SQLInfo info = new SQLInfo();
				info.setSql(sql);
				info.setSqlType(sqlType);
				this.request.getSession().setAttribute(SysfunctionManager.getCurrentSysfunction().getFunctionid()+"@"+this.getCueEventElementName(),info);
			} catch (AppException e) {
				throw new RadowException(e.getMessage(),(Exception)e.getMyException());
			}
		}
		return PmHListUtil.pageQueryNoCount(this, sql, sqlType,start, limit, totalCount);
	}
	
	
	/**
	 * ����Ҫ����PageModel��ִ�� ��pm.setSelfDefResData(data); �� �˲�����ʹ���첽����ͬʱ���б�ҳ���ݺ������ݵĲ�ѯ��ʹ��ѯ���٣�
	 * @param pm
	 * @param sql ��ѯ��sql
	 * @param sqlType ��ѯ�����HQL��SQL
	 * @param start ��������¼��ʼ��-1Ϊȡ���м�¼
	 * @param limit ȡ����������Ҫ���ڷ�ҳʱ
	 * @return
	 * @throws RadowException 
	 */
	public PageQueryData pageQueryByAsynchronous(String sql,String sqlType,int start,int limit) throws RadowException{
		sql = SQLManager.getNewQuerySqlByOrdOrFilterStr(sql, sqlType, requestParamer);
		//SQLManager.saveQueryInfo(sql, sqlType, start, limit, this);
		if(start==0){
			try {
				SQLInfo info = new SQLInfo();
				info.setSql(sql);
				info.setSqlType(sqlType);
				this.request.getSession().setAttribute(SysfunctionManager.getCurrentSysfunction().getFunctionid()+"@"+this.getCueEventElementName(),info);
			} catch (AppException e) {
				throw new RadowException(e.getMessage(),(Exception)e.getMyException());
			}
		}
		return PmHListUtil.pageQueryByAsynchronous(this, sql, sqlType,start, limit);
	}
	
	
	
	
	/**
	 * ��һ��List��Ԫ��ΪJava Bean����ļ��Ͻ����ڴ��з�ҳ������PageQueryData����
	 * ������sql���ʱ����Ѿ��ֺã����ǲ�ȫ������ʹ�ú�̨�����ҳ
	 * @param entityList
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	public PageQueryData getPageQueryData(List<Object> entityList,int start,int limit)throws RadowException{
		return PmHListUtil.getPageQueryData(entityList, start, limit);
	}
	/**
	 * ��ȡһ��HList����
	 * @param name �硰div_1����"div_1.grid1" Ϊnullʱ����һ��û�г�ʼ���ݵ�HList����
	 * @param pm ��ǰPageModel
	 * @return
	 * @throws RadowException 
	 */
	public PMHList getPMHList(String name) throws RadowException{
		return PmHListUtil.getPMHList(name, this);
	}

	/**
	 * ��ȡ��ǰ�������¼��� ��aab001.onchange
	 * @return
	 */
	public String getCueEventName() {
		return cueEventName;
	}
	/**
	 * ��ȡ��ǰ�����¼���Ԫ����
	 * @return
	 */
	public String getCueEventElementName(){
		if(this.cueEventName.equals("dogridquery") && this.getParameter("cueGridId") != null){
			return this.getParameter("cueGridId");
		}else if(this.cueEventName==null||this.cueEventName.indexOf(".")<=0){
			return null;
		}else{
			return cueEventName.substring(0,this.cueEventName.indexOf("."));
		}
	}

	public void setCueEventName(String cueEventName) {
		this.cueEventName = cueEventName;
	}
	
	/**
	 * �Զ�������һ�����ݵ���ҳ���ͬ��Ԫ����
	 * @param pe ����Ϊgrid��editorgridԪ��
	 */
	public void autoFillPageByGridElement(PageElement pe){
		autoFillPageByGridElement(pe,0,true);
	}
	/**
	 * �Զ������ĳ�����ݵ���ҳ���ǰҳ���ͬ��Ԫ����
	 * @param pe ����Ϊgrid��editorgridԪ��
	 * @param rowIndex Ҫ�ҳ��ȥ�ı���к�
	 * @param isParent TRUE�������ҳ�棬����Ϊ��ҳ��
	 */
	public void autoFillPageByGridElement(PageElement pe,int rowIndex,boolean isParent){
		if(pe.getType().equals("grid") || pe.getType().equals("editorgrid")){
			List<?> list = pe.getValueList();
			if(list.size()!=0){
				String jsonDS = JSONObject.fromObject(list.get(rowIndex)).toString();
				this.executeSG.addExecuteCode("radow.autoFillElementValue('"+jsonDS+"',"+isParent+");");
			}
		}
	}
	/**
	 * �Զ����һ�����������ֵ��������
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillElementValue('"+jsonDS+"',"+isParent+");");
	}
	
	/**
	 * �Զ����һ�����������ֵ��������
	 * @param dataObj ������
	 * @param isParent �Ƿ���丸ҳ��
	 * @param isFillEmptyValue �Ƿ񽫿��ַ���Ҳ�������
	 */
	public void autoFillPage(Object dataObj,boolean isParent,boolean isFillEmptyValue){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillElementValue('"+jsonDS+"',"+isParent+","+isFillEmptyValue+");");
	}
	
	/**
	 * ���div�����б����Ԫ�ص�������Ϣ
	 * @param divIds div��id��Ϣ������ö��Ÿ���
	 */
	public void clearDivData(String divIds){
		this.executeSG.addExecuteCode("radow.clearDivData('"+divIds+"');");
	}
	
	/**
	 * �Զ����һ�����������ֵ��������ķǱ�Ԫ���У������ı���ʽ�����硰span\div����������
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillTextToPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillTextValue('"+jsonDS+"',"+isParent+");");
	}
	
	/**
	 * �Զ����һ�����������ֵ��������ķǱ�Ԫ���У������ı���ʽ�����硰span\div����������
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillHtmlToPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillTextValue('"+jsonDS+"',"+isParent+",1);");
	}
	
	
	/**
	 * ִ��һ����ѯ�����Ѳ�ѯ���ļ�¼�Element��ȥ�������ֶ�����Ԫ�ص�id��ͬ����
	 * ֻ���һ����Ԫ��
	 * @param pm
	 * @param sql ��ѯ�����
	 * @param sqlType sql�����ͣ�Ϊ��SQL������HQL��
	 * @return
	 */
	public boolean autoFill(PageModel pm,String sql,String sqlType)throws RadowException{
		return this.dataStore.autoFill(pm, sql,sqlType);
	}
	
	/**
	 * ����ҳ��Ԫ��ֵ��Object������ͬ��������,���parentElementNameΪ�գ��򿽱�PM�ĵ�һ��Ԫ�أ�����
	 * ����parentElementName�µ�������Ԫ�ص�Objectͬ��������
	 * @param obj Ҫ����Ԫ��ֵ����Java Bean����
	 * @param pm PageModelҳ��
	 * @param parentElementName ������Ԫ�������������Ԫ��ֵ��obj��ͬ��������
	 * @return
	 * @throws RadowException 
	 */
	public boolean copyElementsValueToObj(Object obj, PageModel pm,String parentElementName) throws RadowException{
		return PMPropertyCopyUtil.copyElementsValueToObj(obj, pm,parentElementName);
	}
	
	/**
	 * ����ҳ��Ԫ��ֵ��Object������ͬ��������
	 * @param obj Ҫ����Ԫ��ֵ����Java Bean����
	 * @param pm PageModelҳ��
	 * @return
	 * @throws RadowException 
	 */
	public boolean copyElementsValueToObj(Object obj, PageModel pm) throws RadowException{
		return PMPropertyCopyUtil.copyElementsValueToObj(obj, pm);
	}
	
	/**
	 * ��obj���Ե�ֵ�赽��Ӧ��PageElement��,���parentElementName��Ϊ�գ����赽���ӦԪ�ص���Ԫ����
	 * �������赽PageModel�ĵ�һ��Ԫ����
	 * @param obj
	 * @param pm
	 * @param parentElementName
	 * @return
	 * @throws RadowException 
	 * @throws Exception
	 */
	public boolean copyObjValueToElement(Object obj, PageModel pm,String parentElementName) throws RadowException{		
		return PMPropertyCopyUtil.copyObjValueToElement(obj, pm,parentElementName);
	}
	
	/**
	 * ��obj���Ե�ֵ�赽��Ӧ��PageElement��
	 * @param obj
	 * @param pm
	 * @return
	 * @throws RadowException 
	 */
	public static boolean copyObjValueToElement(Object obj, PageModel pm) throws RadowException{
		return PMPropertyCopyUtil.copyObjValueToElement(obj, pm);
	}
	public String getSelfResponseFunc() {
		return selfResponseFunc;
	}
	/**
	 * �����Զ�����Ӧ����������������˸ú��������еĴ��������Լ�ȥ���������������
	 * @param selfResponseFunc ֻ��Ҫ�������������Ὣresponse����ȥ
	 */
	public void setSelfResponseFunc(String selfResponseFunc) {
		this.selfResponseFunc = selfResponseFunc;
	}
	
	/**
	 * �����ӡ
	 * @param reportNo
	 * @param queryName
	 * @param param
	 * @param preview
	 */
	public void billPrint(String reportNo,String queryName,String param,boolean preview){
		this.executeSG.addExecuteCode("odin.billPrint('"+reportNo+"','"+queryName+"','"+param+"',"+preview+");");
	}
	
	/**
	 * �����ӡ  ����ȷ�����ӡ
	 * @param reportNo
	 * @param queryName
	 * @param param
	 * @param preview
	 */
	public void billPrintByYes(String reportNo,String queryName,String param,boolean preview)throws RadowException{
		this.setNextBackFunc("odin.billPrint('"+reportNo+"','"+queryName+"','"+param+"',"+preview+");");
	}
	
	/**
	 * ��ֹ������ǰҳ��
	 * @param msg ��ֹʱ��ʾ����ʾ��Ϣ
	 */
	public void forbidOpPage(String msg){
		this.getExecuteSG().addExecuteCode("odin.ext.get(document.body).mask('<h3><font color=red>"+msg+"</font></h3>',odin.msgCls);");
	}
	/**
	 * ҳ����ת
	 * @param location ��ַ������䲻���ԡ�/����ͷ������Ϊ�����·��������ԡ�/����ͷ����û�к�������·��ʱ��ϵͳ���Զ����������Ļ���
	 */
	public void jumpToOtherPage(String location){
		if(location.indexOf("/")==0){
			if(location.indexOf(this.contextPath)<0){
				location = this.contextPath + location;
			}
		}
		this.getExecuteSG().addExecuteCode("window.location.href='"+location+"';");
	}
	/**
	 * ˢ��grid����ͼ����Ҫ������gird�Ĺ������޷�������ʾ����£������һ�㷢����tab��ǩ�
	 * @param gridId ���ID
	 */
	public void refreshGridViewFromTab(String gridId){
		this.getExecuteSG().addExecuteCode("odin.ext.getCmp('"+gridId+"').view.refresh(true);");
	}
	
	public void setFilesInfo(String id, List<Map<String, String>> listmap){
		setFilesInfo(id, listmap, false);
	}
	  
	public void setFilesInfo(String id, List<Map<String, String>> listmap,boolean lazy){
		JSONArray jsonObject = JSONArray.fromObject(listmap);
		if(lazy){
			this.getExecuteSG().addExecuteCode("if(!!fnSet['"+id+"']){$h.ready(function() {$('#"+id+"').uploadify('fileEcho', "+jsonObject.toString()+");},'"+id+"_info');}else{$('#"+id+"').uploadify('fileEcho', "+jsonObject.toString()+");}");
		}else{
			this.getExecuteSG().addExecuteCode("$('#"+id+"').uploadify('fileEcho', "+jsonObject.toString()+");");
		}
	}  
	  /**
	   * ����������ҳ��Ԫ��
	   * @param ids
	   */
	  public void initParentData(String... ids ) {
			if(ids != null && ids.length>0){
				for(int i=0;i<ids.length;i++){
					String id = ids[i];
					this.getExecuteSG().addExecuteCode("$(\"body\").append(($(\"<input type='hidden' id='"+id+"' name='"+id+"' />\")).val(parent.$('#"+id+"').val())); ");
				}
			}
	  }
	  
	  
	  
	  public void setFileButtonDisabled(String id) {
		  this.getExecuteSG().addExecuteCode("$('#"+id+"').css('visibility','hidden')");
			
	  }
	  
	  public void setFileButtonEnabled(String id) {
		  this.getExecuteSG().addExecuteCode("$('#"+id+"').css('visibility','visible')");
			
	  }
	  
	  public void upLoadFile(String id)
	  {
		  upLoadFile(id,false);
	  }
	  public void upLoadFile(String id,boolean lazy)
	  {
		  if(lazy){
			  getExecuteSG().addExecuteCode("if(!fnSet['"+id+"'])$('#"+id.replace("\r", "").replace("\n", "").replace("'", "\\'")+"').uploadify('upload','*');");
			  
		  }else{
			  getExecuteSG().addExecuteCode("$('#"+id.replace("\r", "").replace("\n", "").replace("'", "\\'")+"').uploadify('upload','*');");
			   
		  }
	    }
	 
	  public void toastmessage(String msg, int i) {
		  getExecuteSG().addExecuteCode("Ext.example.msg('','"+msg.replace("\r", "").replace("\n", "").replace("'", "\\'")+"',"+i+");");
			
		}  
	  public void toastmessage(String msg)
	  {
		  getExecuteSG().addExecuteCode("Ext.example.msg('','"+msg.replace("\r", "").replace("\n", "").replace("'", "\\'")+"',1);");
	
	  }
	  
	  public void controlButton(){
		  List<String> buttonList = (List<String>) this.request.getSession().getAttribute("function_button_list");
		  if(buttonList==null||buttonList.size()==0)buttonList=Utils.getButtonList();
		  if(buttonList!=null&&buttonList.size()>0){
			  for(String buttonid : buttonList){
				  this.getExecuteSG().addExecuteCode("odin.ext.getCmp('"+buttonid+"').hide();");
			  }
		  }
	  }
	  
	  
	  public String getCurUserid(){
		  return getCurUserVO().getId();
	  }
	  public String getCurUsername(){
		  return getCurUserVO().getLoginname();
	  }
	  public UserVO getCurUserVO(){
		  UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
		  return user;
	  }
}
