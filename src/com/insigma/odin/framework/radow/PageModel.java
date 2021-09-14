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
 * <h1>抽象的页面模型类</h1>
 * <p>
 * 开发人员在开发过程中需要实现一个具体的页面模型类，该页面模型类需要继承此抽象页面模型，而每个具体的页面模型都必须实现“doInit”初始化事件。那么，什么是页面模型类了？我们可以将其理解为一个和我们的功能JSP页面对应且对等的Java类，
 * 其涵盖了页面的请求过来的Input元素类型和值（支持两级模式，即某DIV下的Input）、工程的上下文路径（contextPath）、本次请求事件的名称等等。
 * </P>
 * <p>
 * 当页面上触发了某个事件时，我们的框架会自动提取请求所需要的参数（此类定义在我们的具体PageModel类里某方法的标注上），将其传回后台，并依据具体是那个元素的什么事件
 * 调用具体PageModel的该方法，调用完成后框架自动提取出响应信息进行返回，最后再由我们框架统一处理响应，当然也可由开发人员自行处理，如果事件需自行处理事件时需要告诉框架
 * 处理的函数名是什么，框架就会将处理的权力移交给该函数进行处理。
 * </P>
 * <p>Company: 浙江网新恩普软件有限公司</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>时间： 2009-10</p>
 * @author Jinwei
 */
public abstract class PageModel {

	/**
	 * 页面元素
	 */
	private PageElement webPage;
	
	/**
	 * 上下文路径
	 */
	public String contextPath = null;
	
	/**
	 * 页面请求
	 */
	public HttpServletRequest request;

	/**
	 * 页面模型的事件集合（为了动态注册而设置的）
	 */
	private List<String> pageEvents = new ArrayList<String>();
	
	/**
	 * 页面的请求JSON内容
	 */
	private String reqJSONContent;
	
	/**
	 * 用来存储每次事件的成功或失败后的消息
	 */
	private String mainMessage  = "";
	
	/**
	 * 下次事件集合
	 */
	private List<NextEvent> nextEvents = new ArrayList<NextEvent>();
	
	/**
	 * 当前事件名
	 */
	private String cueEventName = null;
	
	/**
	 * 消息类型
	 */
	private String messageType = EventMessageType.ALERT;
	
	/**
	 * 自定义事件响应数据  返回时直接将其作为ajax响应数据
	 */
	private Object selfDefResData = null;
	
	/**
	 * 用来存父页面的一些关键数据，比较适合通过Odin标签打开Window的情况（将该数据作为初始参数）
	 */
	@SuppressWarnings("unused")
	private String radow_parent_data = "";
	
	/**
	 * 是否显示提示框
	 */
	public boolean isShowMsg = false;
	
	/**
	 * 执行序列生产器
	 */
	private IExecuteSeqGenerator executeSG;
	/**
	 * 页面模型解析器
	 */
	private IPageModelParser pageModelParser;
	/**
	 * 页面数据存储和执行器
	 */
	private IDataStore dataStore;
	
	
	/**
	 * 自定义响应处理函数
	 */
	private String selfResponseFunc = null;
	
	/**
	 * 存放请求里的所有参数信息
	 */
	private HashMap<String,String> requestParamer = new HashMap<String,String>();
	
	/**
	 * 往PageModel里存放请求参数
	 * @param key 键
	 * @param value 值
	 */
	public void putParameter(String key,String value){
		this.requestParamer.put(key, value);
	}
	/**
	 * 取请求里的非框架自动处理的参数
	 * @param key 参数名称
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
	 * 页面的初始化
	 * @throws RadowException 
	 */
	@PageEvent("doInit")
	public abstract int doInit() throws RadowException;
	
	/**
	 * 生成返回对象
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
	 * 获取页面元素
	 * @param elementName 页面元素的ID，写法支持"div1.aab004" 即取页面里div1下面的aab004
	 * @return
	 * @throws RadowException
	 */
	public PageElement getPageElement(String elementName) throws RadowException{
		return pageModelParser.parseElement(elementName);
	}
	
	/**
	 * 创建一个元素，如果我们这次事件要设置值的元素没传过来，而还要改变其值等属性时需要通过此方法来创建没传过来
	 * 但页面存在的元素，其也可在父页面内
	 * @param elementName 元素名
	 * @param elementType 元素类型
	 * @param isParent 是否在父页面
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
	 * 设置一个自定义的数据对象，将其作为本次事件响应的Ajax结果，如PageQueryData等等 
	 * 还可以是字符行的xml文件，但必须将事件返回类型设为EventRtnType.XML_SUCCESS
	 * 否则默认为EventResBean
	 * @param selfDefResData 任何类型
	 */
	public void setSelfDefResData(Object selfDefResData) {
		this.selfDefResData = selfDefResData;
	}
	/**
	 * 获取弹出框的父页面参数值
	 * 其父页面必须设置过，否则为空
	 * @return
	 * @throws RadowException
	 */
	public String getRadow_parent_data() throws RadowException {		
		return this.getPageElement("radow_parent_data").getValue();
	}
	/**
	 * 设置当前的radow_parent_data元素的值，以供下次弹出窗口去get
	 * 即该值只是在父页面下次要传给子页面的参数，对本页面并无任何影响
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
	 * 打开一个窗口 
	 * @param windowId 窗口的id
	 * @param pageModel 如pages.sample.SimpleWindow
	 */
	public void openWindow(String windowId,String pageModel){
		this.getExecuteSG().addExecuteCode("radow.openWindow('"+windowId+"','"+pageModel+"');");
		this.isShowMsg = false;
	}
	/**
	 * 获取关闭一个窗口对象的脚本
	 * @param windowId
	 */
	public void closeCueWindow(String windowId){
		this.getExecuteSG().addExecuteCode("parent.odin.ext.getCmp('"+windowId+"').hide();");
	}
	/**
	 * 关闭通过window.open打开的窗口
	 */
	public void closeCueWindow(){
		this.getExecuteSG().addExecuteCode("window.close();");
	}
	
	/**
	 * 关闭通过window.open打开的窗口,当点击确定按钮时
	 */
	public void closeCueWindowByYes(){
		this.addNextBackFunc(NextEventValue.YES, "window.close();");
	}
	
	/**
	 * 弹出一个alert框，并当点确定时关闭当前窗口
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
	 * 添加一个下次响应事件
	 * @param ne
	 */
	public void addNextEvent(NextEvent ne){
		this.nextEvents.add(ne);
	}
	/**
	 * 增加一组根据点击条件而下次触发的事件
	 * @param nextEvents
	 */
	public void setNextEvents(List<NextEvent> nextEvents) {
		this.nextEvents = nextEvents;
	}
	/**
	 * 获取当前的消息框类型
	 * @return
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * 设置消息框的类型
	 * @param messageType 必须为EventMessageType里的类型
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	/**
	 * 当前是否要显示弹出消息框
	 * @return
	 */
	public boolean isShowMsg() {
		return isShowMsg;
	}
	
	/**
	 * 设置是否弹出消息框
	 * @param isShowMsg
	 */
	public void setShowMsg(boolean isShowMsg) {
		this.isShowMsg = isShowMsg;
	}
	/**
	 * 增加一个简单下次事件，即不是在某个按钮按下才触发，而是直接触发
	 * @param eventName
	 */
	public void setNextEventName(String eventName){
		NextEvent ne = new NextEvent();
		ne.setNextEventName(eventName);
		this.addNextEvent(ne);
	}
	/**
	 * 设置弹出消息框的回调函数
	 * @param nextBackFunc
	 */
	public void setNextBackFunc(String nextBackFunc){		
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES);
		ne.setNextBackFunc(nextBackFunc);
		this.addNextEvent(ne);
	}
	
	/**
	 * 增加一个下次的触发事件
	 * @param nextEventValue 下次触发事件的按下按钮的值，即是否是按下消息框的确定或取消
	 * @param nextEventName 下次的事件名
	 */
	public void addNextEvent(String nextEventValue,String nextEventName){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue); 
		ne.setNextEventName(nextEventName);
		this.addNextEvent(ne);
	}
	
	/**
	 * 增加一个下次的触发事件
	 * @param nextEventValue 下次触发事件的按下按钮的值，即是否是按下消息框的确定或取消
	 * @param nextEventName 下次的事件名
	 * @param nextEventParameter 下次事件的参数值，即可带回到下次事件响应方法里去
	 * @param nextBackFunc 触发下次事件的同时不管按下什么按钮都会执行该函数
	 */
	public void addNextEvent(String nextEventValue,String nextEventName,String nextEventParameter,String nextBackFunc){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue); 
		ne.setNextEventName(nextEventName);
		ne.setNextBackFunc(nextBackFunc);
		this.addNextEvent(ne);
	}
	
	/**
	 * 增加一个下次的触发事件
	 * @param nextEventValue 下次触发事件的按下按钮的值，即是否是按下消息框的确定或取消
	 * @param nextEventName 下次的事件名
	 * @param nextEventParameter 下次事件的参数值，即可带回到下次事件响应方法里去
	 */
	public void addNextEvent(String nextEventValue,String nextEventName,String nextEventParameter){
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(nextEventValue);
		ne.setNextEventName(nextEventName);
		ne.setNextEventParameter(nextEventParameter); 
		this.addNextEvent(ne);
	}
	
	/**
	 * 增加下次的一个回调JS函数，即当按下nextEventValue所代表的按钮时 去触发nextBackFunc函数
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
	 * 做完相应的操作后的刷新页面
	 * @throws RadowException 
	 */
	public void reloadPage() throws RadowException{
		if(this.getWebPage()==null){
			this.pageModelParser.parse();
		}
		this.executeSG.addExecuteCode(this.getWebPage().reload());
	}
	
	/**
	 * 必须为有弹出框的情况下，且按了YES确定后reloadPage
	 * @throws RadowException
	 */
	public void reloadPageByYes()throws RadowException{
		if(this.getWebPage()==null){
			this.pageModelParser.parse();
		}
		this.setNextBackFunc(this.webPage.reload());
	}
	
	/**
	 * 将PMHList的第一条查询结果 自动填到PageModel的元素里去
	 * 数据格式如[{\"aae135\":\"false\",\"aac003\":\"\",\"rewage\":\"\",\"household\":\"\",\"household_combo\":\"请您选择...\",\"wlwgry\":\"\",\"wlwgry_combo\":\"请您选择...\",\"ylcblb\":\"\",\"ylcblb_combo\":\"请您选择...\",\"ybcblb\":\"\",\"ybcblb_combo\":\"请您选择...\"}]
	 * @param hlist
	 * @throws RadowException 
	 */
	public void autoFillPm(PMHList hlist) throws RadowException{
		PmHListUtil.autoFillPm(hlist, this);
	}
	/**
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * @param pm
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
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
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * @param pm
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
	 * @param map 自定义页面数据，SQL查询不出来时 Map<String,List<String>>
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
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作
	 * @param pm
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
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
	 * 不需要再在PageModel里执行 “pm.setSelfDefResData(data); ” 此操作（使用异步方法同时进行本页数据和总数据的查询，使查询提速）
	 * @param pm
	 * @param sql 查询的sql
	 * @param sqlType 查询的类别，HQL或SQL
	 * @param start 从那条记录开始，-1为取所有记录
	 * @param limit 取多少条，主要用在分页时
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
	 * 把一个List里元素为Java Bean对象的集合进行内存中分页并生成PageQueryData对象，
	 * 即不是sql查的时候就已经分好，而是查全部，在使用后台代码分页
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
	 * 获取一个HList对象
	 * @param name 如“div_1”或"div_1.grid1" 为null时创建一个没有初始数据的HList对象
	 * @param pm 当前PageModel
	 * @return
	 * @throws RadowException 
	 */
	public PMHList getPMHList(String name) throws RadowException{
		return PmHListUtil.getPMHList(name, this);
	}

	/**
	 * 获取当前触发的事件名 如aab001.onchange
	 * @return
	 */
	public String getCueEventName() {
		return cueEventName;
	}
	/**
	 * 获取当前触发事件的元素名
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
	 * 自动填充表格第一行数据到父页面的同名元素中
	 * @param pe 必须为grid或editorgrid元素
	 */
	public void autoFillPageByGridElement(PageElement pe){
		autoFillPageByGridElement(pe,0,true);
	}
	/**
	 * 自动填充表格某行数据到父页面或当前页面的同名元素中
	 * @param pe 必须为grid或editorgrid元素
	 * @param rowIndex 要填到页面去的表格行号
	 * @param isParent TRUE代表填到父页面，否则为本页面
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
	 * 自动填充一个对象的属性值到界面里
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillElementValue('"+jsonDS+"',"+isParent+");");
	}
	
	/**
	 * 自动填充一个对象的属性值到界面里
	 * @param dataObj 待填充的
	 * @param isParent 是否填充父页面
	 * @param isFillEmptyValue 是否将空字符串也进行填充
	 */
	public void autoFillPage(Object dataObj,boolean isParent,boolean isFillEmptyValue){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillElementValue('"+jsonDS+"',"+isParent+","+isFillEmptyValue+");");
	}
	
	/**
	 * 清除div下所有表单表格元素的数据信息
	 * @param divIds div的id信息，多个用逗号隔开
	 */
	public void clearDivData(String divIds){
		this.executeSG.addExecuteCode("radow.clearDivData('"+divIds+"');");
	}
	
	/**
	 * 自动填充一个对象的属性值到界面里的非表单元素中（采用文本方式），如“span\div…………”
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillTextToPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillTextValue('"+jsonDS+"',"+isParent+");");
	}
	
	/**
	 * 自动填充一个对象的属性值到界面里的非表单元素中（采用文本方式），如“span\div…………”
	 * @param dataObj
	 * @param isParent
	 */
	public void autoFillHtmlToPage(Object dataObj,boolean isParent){
		String jsonDS = JSONObject.fromObject(dataObj).toString();
		this.executeSG.addExecuteCode("radow.autoFillTextValue('"+jsonDS+"',"+isParent+",1);");
	}
	
	
	/**
	 * 执行一个查询，并把查询出的记录填到Element里去，依据字段名和元素的id相同来填
	 * 只填第一级的元素
	 * @param pm
	 * @param sql 查询的语句
	 * @param sqlType sql的类型，为“SQL”、“HQL”
	 * @return
	 */
	public boolean autoFill(PageModel pm,String sql,String sqlType)throws RadowException{
		return this.dataStore.autoFill(pm, sql,sqlType);
	}
	
	/**
	 * 拷贝页面元素值到Object对象中同名属性里,如果parentElementName为空，则拷贝PM的第一级元素，否则
	 * 拷贝parentElementName下的所有子元素到Object同名属性里
	 * @param obj 要拷贝元素值到的Java Bean对象
	 * @param pm PageModel页面
	 * @param parentElementName 拷贝该元素下面的所有子元素值到obj的同名属性里
	 * @return
	 * @throws RadowException 
	 */
	public boolean copyElementsValueToObj(Object obj, PageModel pm,String parentElementName) throws RadowException{
		return PMPropertyCopyUtil.copyElementsValueToObj(obj, pm,parentElementName);
	}
	
	/**
	 * 拷贝页面元素值到Object对象中同名属性里
	 * @param obj 要拷贝元素值到的Java Bean对象
	 * @param pm PageModel页面
	 * @return
	 * @throws RadowException 
	 */
	public boolean copyElementsValueToObj(Object obj, PageModel pm) throws RadowException{
		return PMPropertyCopyUtil.copyElementsValueToObj(obj, pm);
	}
	
	/**
	 * 把obj属性的值设到对应的PageElement中,如果parentElementName不为空，则设到其对应元素的子元素中
	 * 否则，则设到PageModel的第一级元素中
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
	 * 把obj属性的值设到对应的PageElement中
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
	 * 设置自定义响应函数，即如果定义了该函数则所有的处理都必须自己去处理在这个函数里
	 * @param selfResponseFunc 只需要函数名，参数会将response传进去
	 */
	public void setSelfResponseFunc(String selfResponseFunc) {
		this.selfResponseFunc = selfResponseFunc;
	}
	
	/**
	 * 报表打印
	 * @param reportNo
	 * @param queryName
	 * @param param
	 * @param preview
	 */
	public void billPrint(String reportNo,String queryName,String param,boolean preview){
		this.executeSG.addExecuteCode("odin.billPrint('"+reportNo+"','"+queryName+"','"+param+"',"+preview+");");
	}
	
	/**
	 * 报表打印  按下确定后打印
	 * @param reportNo
	 * @param queryName
	 * @param param
	 * @param preview
	 */
	public void billPrintByYes(String reportNo,String queryName,String param,boolean preview)throws RadowException{
		this.setNextBackFunc("odin.billPrint('"+reportNo+"','"+queryName+"','"+param+"',"+preview+");");
	}
	
	/**
	 * 禁止操作当前页面
	 * @param msg 禁止时显示的提示信息
	 */
	public void forbidOpPage(String msg){
		this.getExecuteSG().addExecuteCode("odin.ext.get(document.body).mask('<h3><font color=red>"+msg+"</font></h3>',odin.msgCls);");
	}
	/**
	 * 页面跳转
	 * @param location 地址，如果其不是以“/”开头，则认为是相对路径，如果以“/”开头，但没有含上下文路径时，系统将自动加上上下文环境
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
	 * 刷新grid的视图，主要是用于gird的滚动条无法正常显示情况下（此情况一般发生在tab标签里）
	 * @param gridId 表格ID
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
	   * 批量生成子页面元素
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
