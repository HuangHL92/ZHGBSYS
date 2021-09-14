package com.insigma.siis.local.pagemodel.customquery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Customquery;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.lbs.leaf.util.BeanHelper;

import net.sf.json.JSONArray;

/**
 * @author zoul
 *
 */
public class QueryConditionExPageModel extends PageModel {
	
	private CustomQueryBS ctcBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException{
		//this.getExecuteSG().addExecuteCode("resizeframe();");
		this.setNextEventName("gridcq");
		this.setNextEventName("gridcqSH");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 双击固定条件查询   
	 * @author 
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("gridcq.rowdbclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int rowdbclick() throws RadowException, AppException{
		this.getPageElement("checkedgroupid").setValue(null);
		this.request.getSession().setAttribute("queryType", "1");
		this.request.getSession().setAttribute("queryTypeEX", "新改查询方式");
		
		String querysql = this.getPageElement("gridcq").getValue("querysql").toString();
		
		//String hasQueried = this.getPageElement("sql").getValue();
		
		/*String radioC = this.getPageElement("radioC").getValue();
		if(!"1".equals(radioC)){
			if("".equals(hasQueried) || hasQueried==null)
				throw new AppException("未进行过查询请先查询!");
		}
		this.getPageElement("sql").setValue(querysql);
		this.setNextEventName("peopleInfoGrid.dogridquery");*/
		this.getExecuteSG().addExecuteCode("realParent.conditionRowdbclick('"+querysql.replace("'", "\\'")+"',doQuery());");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	CustomQueryBS cbBs=new CustomQueryBS();
	/**
	 * 查询固定条件
	 * @return
	 * @throws RadowException
	 */	
	@PageEvent("gridcq")
	public int gridcq() throws RadowException{
		   String data= JSONArray.fromObject(cbBs.getCustomSqlList(SysUtil.getCacheCurrentUser().getLoginname())).toString();
    		this.getPageElement("gridcq").setValue(data
    				);
			return EventRtnType.NORMAL_SUCCESS;
		
	}
	/**
	 * 查询固定条件
	 * @return
	 * @throws RadowException
	 */	
	@PageEvent("gridcqSH")
	public int gridcqSH() throws RadowException{
		   String data= JSONArray.fromObject(cbBs.getCustomSqlList("",true)).toString();
    		this.getPageElement("gridcqSH").setValue(data);
			return EventRtnType.NORMAL_SUCCESS;
		
	}
	/**
	 * 添加固定条件到共享
	 * @return
	 * @throws RadowException
	 */	
	@PageEvent("shareQC")
	public int shareQC(String id) throws RadowException {
		String loginname = SysUtil.getCacheCurrentUser().getLoginname();
		HBSession sess = HBUtil.getHBSession();
		try {
			Customquery cq = (Customquery) sess.get(Customquery.class, id);
			Customquery cqnew = new Customquery();
			BeanHelper.copyProperties(cq, cqnew);
			cqnew.setSharename(loginname);
			cqnew.setQueryid(UUID.randomUUID().toString());
			sess.save(cqnew);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setNextEventName("gridcqSH");
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	/**
	 * 从共享添加固定条件
	 * @return
	 * @throws RadowException
	 */	
	@PageEvent("getShareQC")
	public int getShareQC(String id) throws RadowException {
		String loginname = SysUtil.getCacheCurrentUser().getLoginname();
		HBSession sess = HBUtil.getHBSession();
		try {
			Customquery cq = (Customquery) sess.get(Customquery.class, id);
			Customquery cqnew = new Customquery();
			BeanHelper.copyProperties(cq, cqnew);
			cqnew.setLoginname(loginname);
			cqnew.setSharename(null);
			cqnew.setQueryid(UUID.randomUUID().toString());
			sess.save(cqnew);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setNextEventName("gridcq");
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	/**
	 * 删除共享固定条件
	 * @return
	 * @throws RadowException
	 */	
	@PageEvent("deleteRow")
	public int deleteRow(String id) throws RadowException {
		String loginname = SysUtil.getCacheCurrentUser().getLoginname();
		HBSession sess = HBUtil.getHBSession();
		try {
			Customquery cq = (Customquery) sess.get(Customquery.class, id);
			
			sess.delete(cq);
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setNextEventName("gridcqSH");
		return EventRtnType.NORMAL_SUCCESS;

	}
	
	/**
	 * 打开模型脚本查询页面
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("mscript.onclick")
	@NoRequiredValidate
	public int mscriptOnclick()throws RadowException{
		//this.setRadow_parent_data("");
		//this.openWindow("win1", "pages.customquery.QueryCondition");
		//this.getExecuteSG().addExecuteCode("$h.openWin('win1','pages.customquery.QueryCondition','查询条件',1250,520,'','"+request.getContextPath()+"')");
		this.getExecuteSG().addExecuteCode("realParent.userDefined();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 删除条件按钮
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("mscriptDel.onclick")
	@NoRequiredValidate
	@Transaction
	public int mscriptDel(String idname)throws RadowException, AppException{
		if(idname==null||"".equals(idname)){
			this.setMainMessage("请选择要删除的查询方案");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] in = idname.split("\\{,\\}");
		String queryname= in[1];
		String queryId= in[0];
        
		if(queryId==null){
			this.setMainMessage("请先选择要删除的查询方案！");
			return EventRtnType.NORMAL_SUCCESS;
		}else{
			NextEvent nextEvent = new NextEvent();
			nextEvent.setNextEventName("delCodition");
			nextEvent.setNextEventParameter(queryId);
			nextEvent.setNextEventValue(NextEventValue.YES);
			this.addNextEvent(nextEvent);
			this.setMainMessage("确定要删除查询方案:"+queryname);
			this.setMessageType(EventMessageType.CONFIRM);
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	/**
	 * 删除条件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("delCodition")
	@NoRequiredValidate
	@Transaction
	public int delCodition(String id)throws RadowException, AppException{
		String queryId=id;	
		Customquery cq=(Customquery) HBUtil.getHBSession().get(Customquery.class, queryId);
		HBUtil.getHBSession().delete(cq);
		//this.getPageElement("cueRowIndex").setValue("");
		this.setNextEventName("gridcq");
	    return EventRtnType.NORMAL_SUCCESS;	    
	}
	
	/**
	 * 更新条件
	 * @return
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("mscriptUp.onclick")
	@NoRequiredValidate
	public int mscriptUp(String idname)throws RadowException, AppException{
		if(idname==null||"".equals(idname)){
			this.setMainMessage("请选择要修改的查询方案");
			return EventRtnType.NORMAL_SUCCESS;
		}
		String[] in = idname.split("\\{,\\}");
		String queryname= in[1];
		String queryId= in[0];
		if(queryId==null){
			this.setMainMessage("请先选择要修改的条件！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if("常用条件".equals(queryname) ){//|| "上次查询".equals(queryname)
			this.setMainMessage("【常用条件】不可编辑！【常用条件】请到【组合查询】页面重新保存！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		
		this.getExecuteSG().addExecuteCode("realParent.userDefined(null,null,null,'"+queryId+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	/** 
	 * 设定选中行号到页面上
	 * @author mengl
	 * @return
	 * @throws RadowException
	 */
	//@PageEvent("gridcq.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException{
		this.request.getSession().setAttribute("groupBy", "1");
		int cueRowIndex = this.getPageElement("gridcq").getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	//数据导出
	@PageEvent("dataOut")
	@NoRequiredValidate
	@Transaction
	public int mscriptOut(String queryId)throws RadowException, AppException, FileNotFoundException, UnsupportedEncodingException{
		
		/*private String queryid;*/
		/*private String querysql;
		private String querydescription;
		private String loginname;
		private Date createtime;
		private String queryname;
		private String gridstring;
		private String sharename;*/
		HBSession sess = HBUtil.getHBSession();
		Customquery customquery = (Customquery)sess.get(Customquery.class, queryId);
		Document document = DocumentHelper.createDocument();
        //添加元素 xml
        Element root = document.addElement("root");
        //添加元素 table
        Element Customquery = root.addElement("Customquery");
        Element queryid = Customquery.addElement("QUERYID");
        queryid.addText(customquery.getQueryid());
		Element querysql = Customquery.addElement("QUERYSQL");
		querysql.addText(customquery.getQuerysql());
		Element querydescription = Customquery.addElement("QUERYDESCRIPTION");
		querydescription.addText(customquery.getQuerydescription());
		Element loginname = Customquery.addElement("LOGINNAME");
		loginname.addText(customquery.getLoginname());
		Element createtime = Customquery.addElement("CREATETIME");
		createtime.addText(customquery.getCreatetime().toString());
		Element queryName = Customquery.addElement("QUERYNAME");
		queryName.addText(customquery.getQueryname());
		Element gridstring = Customquery.addElement("GRIDSTRING");
		gridstring.addText(customquery.getGridstring());
		Element sharename = Customquery.addElement("SHARENAME");
		if(customquery.getSharename()==null) {
			sharename.addText("-1");
		}else {
			sharename.addText(customquery.getSharename());
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		String namefile = "查询方案("+customquery.getQueryname()+sdf.format(new Date())+").xml";
		try {
			File file = new File(AppConfig.HZB_PATH + "\\Customtemp\\outdata");
			if (!file.exists() && !file.isDirectory()) {// 如果文件夹不存在则创建
				file.mkdirs();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String temppath = AppConfig.HZB_PATH + "/Customtemp"+"/"+ "/outdata"+"/"+namefile;
		FileOutputStream fos = new FileOutputStream(temppath);
		XMLWriter writer = new XMLWriter(fos,OutputFormat.createPrettyPrint());
		
		//5
		try {
			writer.write(document);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("写出完毕!");
		try {
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       /* SchemaElement.addAttribute("name", "Customquery");
        //1 添加元素 column
        Element column1 = SchemaElement.addElement("column");
        column1.addAttribute("name", "QUERYID");
        //2 添加元素 column
        Element column2 = SchemaElement.addElement("column");
        column2.addAttribute("name", "QUERYSQL");
        //3 添加元素 column
        Element column3 = SchemaElement.addElement("column");
        column3.addAttribute("name", "QUERYDESCRIPTION");
        //4 添加元素 column
        Element column4 = SchemaElement.addElement("column");
        column4.addAttribute("name", "LOGINNAME");
        //5 添加元素 column
        Element column5 = SchemaElement.addElement("column");
        column5.addAttribute("name", "CREATETIME");
        //6 添加元素 column
        Element column6 = SchemaElement.addElement("column");
        column6.addAttribute("name", "QUERYNAME");
        //7 添加元素 column
        Element column7 = SchemaElement.addElement("column");
        column7.addAttribute("name", "GRIDSTRING");
        //8 添加元素 column
        Element column8 = SchemaElement.addElement("column");
        column8.addAttribute("name", "SHARENAME");*/
		
		this.getExecuteSG().addExecuteCode("downFile('"+temppath+"')");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("importdata.onclick")
	@NoRequiredValidate
	@Transaction
	public int importdata()throws RadowException, AppException, FileNotFoundException, UnsupportedEncodingException{
		this.getExecuteSG().addExecuteCode("$h.openWin('Importdata','pages.customquery.Importdata','数据导入',349,176,'s','"+request.getContextPath()+"');");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static void main(String[] args) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(sdf.format(new Date()));
	}
	
}