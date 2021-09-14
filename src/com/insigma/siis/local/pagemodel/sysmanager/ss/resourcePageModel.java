package com.insigma.siis.local.pagemodel.sysmanager.ss;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtResource;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Button;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.commform.BuildUtil;
import com.insigma.odin.framework.util.commform.BuildUtil.Item;

public class resourcePageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		//  this.getPageElement("functionid").setDisabled(true);
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@PageEvent("querybyid")
	@NoRequiredValidate
	public int query(String id) throws Exception {
		String hql = "from SmtFunction where functionid=:functionid";
		HBSession sess = HBUtil.getHBSession();
		List<Object> list = sess.createQuery(hql).setString("functionid", id).list();
		if (list.size() > 0) {
			SmtFunction function = (SmtFunction) list.get(0);
			String hqlparent = "from SmtFunction where functionid=:parent";
			List<Object> listparent = sess.createQuery(hqlparent).setString("parent", function.getParent()).list();
			if(listparent.size()>0) {
				SmtFunction pf = (SmtFunction)listparent.get(0);
				this.getPageElement("parentid").setValue(pf.getTitle());
				this.createPageElement("parentid", ElementType.TEXT, false).setValue(pf.getFunctionid());
			}else{
				this.getPageElement("parentid").setValue("");
				this.createPageElement("parentid", ElementType.TEXT, false).setValue("");
			}
			PMPropertyCopyUtil.copyObjValueToElement(function, this);
			Button btn = (Button)this.createPageElement("btnSave", ElementType.BUTTON, false);
			btn.setText("更新");
			this.getPageElement("optype").setValue("1");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnNew.onclick")
	@NoRequiredValidate
	public int newResource() throws RadowException {
		HBSession sess = HBUtil.getHBSession();
		String functionid = getPageElement("functionid").getValue();
		String parentid = this.getPageElement("parentid").getValue();
		if(!parentid.equals("") && functionid.equals("")){
			functionid = parentid;
		}
		this.getExecuteSG().addExecuteCode("document.commForm.reset();");
		this.getPageElement("type").setValue("1");
		this.getPageElement("auflag").setValue("1");
		this.getPageElement("opctrl").setValue("000");
		this.getPageElement("draftflag").setValue("1");
		this.getPageElement("cprate").setValue("0");
		this.getPageElement("active").setValue("1");
		this.getPageElement("isproxy").setValue("0");
		this.getPageElement("rpflag").setValue("0");
		this.getPageElement("publicflag").setValue("0");
		this.getPageElement("log").setValue("0");
		this.getPageElement("rbflag").setValue("0");
		this.getPageElement("reauflag").setValue("0");
		if(functionid!=null && !"".equals(functionid)){
			SmtFunction parentFunc = (SmtFunction) sess.get(SmtFunction.class, functionid);
			this.getPageElement("parentid").setValue(parentFunc.getTitle());
			this.createPageElement("parentid", ElementType.TEXT, false).setValue(parentFunc.getFunctionid());
			//this.createPageElement("parentid_combo", ElementType.TEXT, false).setValue(parentFunc.getTitle());
		}
		Button btn = (Button)this.createPageElement("btnSave", ElementType.BUTTON, false);
		btn.setText("保存");
		this.getPageElement("optype").setValue("0");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnDelete.onclick")
	@NoRequiredValidate
	public int deleteResource() throws RadowException {
		String id = getPageElement("functionid").getValue();
		NextEvent ne = new NextEvent();
		ne.setNextEventValue(NextEventValue.YES); // 当点击消息框的是确定时触发的下次事件
		ne.setNextEventName("sureTodelete");
		ne.setNextEventParameter(id);
		this.addNextEvent(ne);
		NextEvent nec = new NextEvent();
		nec.setNextEventValue(NextEventValue.CANNEL);// 当点击消息框的是取消时触发的下次事件
		this.addNextEvent(nec);
		this.setMessageType(EventMessageType.CONFIRM); // 消息框类型，即confirm类型窗口
		this.setMainMessage("您即将删除该节点以及该节点下的所有子节点!确认删除吗？"); // 窗口提示信息
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("sureTodelete")
	@NoRequiredValidate
	@Transaction
	public int delete(String functionid) throws RadowException {
		try {
			PrivilegeManager.getInstance().getIResourceControl().deleteFunction(functionid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage(),e);
		}
		this.isShowMsg = true;
		this.setMainMessage("成功删除该资源！");
		this.reloadPageByYes();
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 获取父的岗位菜单信息
	 * @param parentid
	 * @return
	 */
	private SmtFunction getParentGW(String parentid){
		HBSession sess = HBUtil.getHBSession();
		SmtFunction parentFunc = (SmtFunction) sess.get(SmtFunction.class, parentid);
		if("1".equals(parentFunc.getType())){
			return parentFunc;
		}else{
			return getParentGW(parentFunc.getParent());
		}
	}

	@SuppressWarnings("unchecked")
	@PageEvent("saveMenu")
	@Transaction
	@GridDataRange
	public int saveResource() throws RadowException, AppException {
			HBSession sess = HBUtil.getHBSession();
			String functioncode = this.getPageElement("functioncode").getValue();
			String type = this.getPageElement("type").getValue();
			String parentid = this.getPageElement("parentid").getValue();
			if("0".equals(type) && functioncode.trim().length()!=9){
				throw new RadowException("功能的菜单编号必须为9位！");
			}
			if("0".equals(type) && !parentid.trim().equals("")){
				SmtFunction parentFunc = getParentGW(parentid);
				if(!functioncode.startsWith(parentFunc.getFunctioncode())){
					throw new RadowException("模块前5位必须跟父菜单（岗位）的菜单编号一致！");
				}
			}
			if("1".equals(type)){
				if(!parentid.trim().equals("") && functioncode.trim().length()!=5){
					throw new RadowException("岗位的菜单编号必须为5位！");
				}else if(parentid.trim().equals("") && functioncode.trim().length()!=3 ){
					throw new RadowException("顶级根菜单的菜单编号必须为3位！");
				}
			}
			String ywlx = this.getPageElement("ywlx").getValue();
			if(ywlx!=null && ywlx.trim().length() != 6){
				throw new RadowException("业务类型的长度必须为6位！");
			}
			String bdyy = this.getPageElement("bdyy").getValue();
			if(bdyy!=null && bdyy.trim().length() != 8){
				throw new RadowException("变动原因的长度必须为8位！");
			}
			if(!bdyy.startsWith(ywlx)){
				throw new RadowException("变动原因前6位必须跟业务类型一样！");
			}
			SmtFunction func = new SmtFunction();
			String functionid = getPageElement("functionid").getValue();
			String location = this.getPageElement("location").getValue();
			if(functionid != null && functionid.trim().length()>0) {
				func  = (SmtFunction) sess.get(SmtFunction.class, functionid);
				if(func==null) {
					throw new RadowException("该数据已失效，可能是人为改动造成，请联系管理员！");
				}
				List list = sess.createQuery("from SmtFunction where location is not null and location=:location and functionid!='"+functionid+"' ").setString("location", location).list();
				if(list.size()>0) {
					throw new RadowException("存在重复的URL,请检查后重新输入！");
				}
			}else{
				List list = sess.createQuery("from SmtFunction where location is not null and location=:location").setString("location", location).list();
				if(list.size()>0) {
					throw new RadowException("存在重复的URL,请检查后重新输入！");
				}
			}
			//update or save
			SmtResource res = new SmtResource();
			if(func.getFunctionid()!=null){
				res = (SmtResource) sess.get(SmtResource.class, func.getResourceid());
			}
			func.setParent(parentid);
			func.setFunctioncode(functioncode);
			func.setOrderno(Long.parseLong(this.getPageElement("orderno").getValue()));
			func.setTitle(this.getPageElement("title").getValue());
			func.setType(type);
			func.setDescription(this.getPageElement("description").getValue());
			func.setLocation(location);
			func.setYwlx(ywlx);
			func.setBdyy(bdyy);
			func.setZyxx(this.getPageElement("zyxx").getValue());
			func.setParam1(this.getPageElement("param1").getValue());
			func.setParam2(this.getPageElement("param2").getValue());
			func.setParam3(this.getPageElement("param3").getValue());
			func.setParam4(this.getPageElement("param4").getValue());
			func.setProc(this.getPageElement("proc").getValue());
			func.setAuflag(this.getPageElement("auflag").getValue());
			func.setOpctrl(this.getPageElement("opctrl").getValue());
			func.setOwner(this.getPageElement("owner").getValue());
			func.setDraftflag(this.getPageElement("draftflag").getValue());
			func.setSysid(this.getPageElement("sysid").getValue());
			func.setCprate(this.getPageElement("cprate").getValue());
			func.setActive(this.getPageElement("active").getValue());
			func.setIsproxy(this.getPageElement("isproxy").getValue());
			func.setAppid(this.getPageElement("appid").getValue());
			func.setRpflag(this.getPageElement("rpflag").getValue());
			func.setPublicflag(this.getPageElement("publicflag").getValue());
			func.setLog(this.getPageElement("log").getValue());
			func.setRbflag(this.getPageElement("rbflag").getValue());
			func.setReauflag(this.getPageElement("reauflag").getValue());
			
			res.setName(this.getPageElement("title").getValue());
			res.setDesc(this.getPageElement("description").getValue());
			res.setUrl(location);
			res.setOwner(this.getPageElement("owner").getValue());
			res.setStatus(this.getPageElement("active").getValue());
			res.setType(type);
			res.setPublicflag(this.getPageElement("publicflag").getValue());
			
			
			
			
			
			String isnum = HBUtil.getValueFromTab("aaa005","aa01","aaa001='ISIDNUM'");
			if("1".equals(isnum)){
				if(func.getFunctionid()==null){
					SmtFunction maxsf=(SmtFunction)sess.createQuery("from SmtFunction where functionid=(select max(functionid) from SmtFunction)").uniqueResult();
					String maxid=maxsf.getFunctionid();
					String newid=String.valueOf(Integer.parseInt(maxid)+1);
					res.setId(newid);
					HBUtil.executeUpdate("insert into Smt_Resource(resourceid,name,description,url,owner,status,type,publicflag) values('"+res.getId()+"','"+res.getName()+"','"+res.getDesc()+"','"+res.getUrl()+"','"+res.getOwner()+"','"+res.getStatus()+"','"+res.getType()+"','"+res.getPublicflag()+"')");
				}else{
					HBUtil.executeUpdate("update Smt_Resource set name='"+res.getName()+"',description='"+res.getDesc()+"',url='"+res.getUrl()+"',owner='"+res.getOwner()+"',status='"+res.getStatus()+"',type='"+res.getType()+"',publicflag='"+res.getPublicflag()+"'");
				}
			}else{
				sess.saveOrUpdate(res);
			}
			
			
			
			if(func.getReauflag()==null){
				func.setReauflag("0");
			}
			if(func.getFunctionid()==null || func.getFunctionid().trim().equals("")){
				func.setFunctionid(res.getId());
				func.setResourceid(res.getId());
				//func.setFunctionid("12345678");
				sess.save(func);
			}else{
				sess.update(func);
			}
			this.setMainMessage("操作成功");
			this.reloadPageByYes();
		return EventRtnType.NORMAL_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@PageEvent("orgTreeJsonData")
	public int getOrgTreeJsonData() throws PrivilegeException {
		String node = this.getParameter("node");
		List<FunctionVO> list = PrivilegeManager.getInstance().getIResourceControl().findByParentId(node);
		StringBuffer jsonStr = new StringBuffer();
		Collections.sort(list,new Comparator(){
			public int compare(Object arg0, Object arg1) {
				if(arg0 instanceof FunctionVO && arg1 instanceof FunctionVO) {
					FunctionVO func1 =  (FunctionVO) arg0;
					FunctionVO func2 =  (FunctionVO) arg1;
					return func1.getOrderno()>func2.getOrderno()?1:-1;
				}
				return 0;
		}});
		if (list != null && !list.isEmpty()) {
			List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>(list.size());
			for(Iterator<FunctionVO> it = list.iterator();it.hasNext();){
				FunctionVO res = it.next();
				String title = res.getTitle();
				if(res.getFunctioncode()!=null){
					title = res.getFunctioncode()+title;
				}
				HashMap<String,Object> treeNode = new HashMap<String,Object>();
				treeNode.put("text", title);
				treeNode.put("id", res.getFunctionid());
				treeNode.put("href", "javascript:radow.doEvent('querybyid','"+ res.getFunctionid() + "')");
				List<FunctionVO> temp = PrivilegeManager.getInstance().getIResourceControl().findByParentId(res.getFunctionid());
				boolean leaf = true;
				if(temp.size()>0){
					leaf = false;
				}
				treeNode.put("leaf", leaf);
				data.add(treeNode);
			}
			jsonStr.append(JSONArray.fromObject(data));
		} else {
			jsonStr.append("{}");
		}
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
}
