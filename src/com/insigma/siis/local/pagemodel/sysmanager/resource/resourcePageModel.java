package com.insigma.siis.local.pagemodel.sysmanager.resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.IResourceControl;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.entity.SmtFunction;
import com.insigma.odin.framework.privilege.entity.SmtResource;
import com.insigma.odin.framework.privilege.util.BeanUtil;
import com.insigma.odin.framework.privilege.vo.FunctionVO;
import com.insigma.odin.framework.privilege.vo.ResourceVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.radow.event.NextEventValue;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class resourcePageModel extends PageModel {
	
	//MiMiBiaoShi
	public static HashMap<String, String> mmbs = new HashMap<String, String>();
	public static void initMMBS() {
		CommQuery cqbs=new CommQuery();
		//1非密 2内部  0秘密
		String sql = "select functionid,param3 from Smt_Function t where param3 in('01','0') ";
		try {
			List<HashMap<String, String>> list = cqbs.getListBySQL2(sql);
			for (HashMap<String, String> map1 : list) {
				mmbs.put(map1.get("functionid"), map1.get("param3"));
			}
		} catch (AppException e) {
			e.printStackTrace();
		}
	}
	static{
		initMMBS();
	}
	

	@Override
	public int doInit() throws RadowException {
		return 0;
	}

	@PageEvent("log.onchange")
	@NoRequiredValidate
	public void logchange() throws RadowException {
		if(getPageElement("log").getValue().equals("1")) {
			getPageElement("prsource").setDisabled(false);
			getPageElement("prsource").setValue("[{property:\"\",label:\"\"},{property:\"\",label:\"\"}]");
		}else {
			getPageElement("prsource").setDisabled(true);
		}
	}
	
	@PageEvent("auflag.onchange")
	@NoRequiredValidate
	public void auflagchange() throws RadowException {
		if(getPageElement("auflag").getValue().length()>1){
			throw new RadowException("长度不正确！是否多级审核长度应该为一位数字长度");
		}
	}
	
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
				getPageElement("parentname").setValue(pf.getTitle());
			}
			function.setUptype(function.getUptype()==null?"":function.getUptype().trim());
			PMPropertyCopyUtil.copyObjValueToElement(function, this);
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnNew.onclick")
	@NoRequiredValidate
	public int newResource() throws RadowException {
		String parentid = getPageElement("functionid").getValue();
		if(parentid ==null || parentid.trim().length()==0) {
			throw new RadowException("请选择要添加在哪个节点下！");
		}
		getPageElement("parentname").setValue(getPageElement("title").getValue());
		getPageElement("parent").setValue(parentid);
		getPageElement("functionid").setValue("");
		getPageElement("title").setValue("");
		String location = getPageElement("location").getValue();
		if(location.indexOf(".")>=0) {
			getPageElement("location").setValue(location.substring(0, location.lastIndexOf(".")));
		}
		getPageElement("description").setValue("");
		getPageElement("owner").setValue("");
		getPageElement("active").setValue("1");
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnDelete.onclick")
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
	@Transaction
	public int delete(String functionid) throws RadowException {
		try {
			PrivilegeManager.getInstance().getIResourceControl()
					.deleteFunction(functionid);
		} catch (PrivilegeException e) {
			throw new RadowException(e.getMessage(),e);
		}
		this.isShowMsg = true;
		this.setMainMessage("成功删除该资源！");
		this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('orgTree'); var otreenode=otree.getNodeById('"+functionid+"').parentNode;otree.getLoader().load(otreenode);otreenode.expand();");
		
		return EventRtnType.NORMAL_SUCCESS;
	}

	@PageEvent("btnSave.onclick")
	@Transaction
	@GridDataRange
	public int saveResource() throws RadowException {
			HBSession sess = HBUtil.getHBSession();
			Date createtime = new Date();
			String functionid = getPageElement("functionid").getValue();
			if(functionid != null && functionid.trim().length()>0) {
				SmtFunction	smtfunction  = (SmtFunction) sess.get(SmtFunction.class, functionid);
				SmtResource	smtres =(SmtResource)sess.get(SmtResource.class, functionid);
				if(smtfunction==null || smtres==null) {
					throw new RadowException("该数据已失效，可能是人为改动造成，请联系管理员！");
				}
	
				if(getPageElement("location").getValue()!=null && !getPageElement("location").getValue().equals(smtfunction.getLocation())) {
						List list = sess.createQuery("from SmtFunction where location=:location").setString("location", getPageElement("location").getValue()).list();
						if(list.size()>0) {
							throw new RadowException("存在重复的URL,请检查后重新输入！");
						}
				}
				PMPropertyCopyUtil.copyElementsValueToObj(smtfunction, this);
				smtfunction.setCreatedate(createtime);   //创建时间，add by ganlh 2018-01-09
				smtres.setName(smtfunction.getTitle());
				smtres.setDesc(smtfunction.getDescription());
				if(smtfunction.getOwner()!=null && smtfunction.getOwner().trim().length()>0) {
					smtres.setOwner(smtfunction.getOwner());
				}
				smtres.setParent(smtfunction.getParent());
				if(smtfunction.getActive()!=null && smtfunction.getActive().trim().length()>0) {
					smtres.setStatus(smtfunction.getActive());
				}else {
					smtres.setStatus("1");
				}
				
				smtres.setType(smtfunction.getType());
				smtres.setUrl(smtfunction.getLocation());
				smtres.setPublicflag(smtfunction.getPublicflag());
				sess.flush();
				initMMBS();
				this.setMainMessage("修改成功");
				this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('orgTree'); var otreenode=otree.getNodeById('"+smtfunction.getFunctionid()+"').parentNode;otree.getLoader().load(otreenode);otreenode.expand();");
				
				return EventRtnType.NORMAL_SUCCESS;
			}
			SmtFunction function = new SmtFunction();
			SmtResource res = new SmtResource();
			PMPropertyCopyUtil.copyElementsValueToObj(function, this);
			if(function.getLocation()!=null && function.getLocation().trim().length()>0) {
				List list = sess.createQuery("from SmtFunction where location=:location").setString("location", function.getLocation()).list();
				if(list.size()>0) {
					throw new RadowException("存在重复的URL,请检查后重新输入！");
				}
			}
			function.setCreatedate(createtime);   //创建时间，add by ganlh 2018-01-09
			res.setName(function.getTitle());
			res.setDesc(res.getName());
			res.setOwner(function.getOwner());
			res.setParent(function.getParent());
			res.setStatus(function.getActive());
			res.setType(function.getType());
			if(function.getPublicflag()!=null && function.getPublicflag().trim().length()>0) {
				res.setPublicflag(function.getPublicflag());
			}
			res.setUrl(function.getLocation());
			if (function.getLog()==null) {
				function.setLog("0");
			}
			if(function.getIsproxy()==null){
				function.setIsproxy("0");
			}
		try {
			if (function.getOrderno() == null) {
				
				function.setOrderno((long) (PrivilegeManager.getInstance().getIResourceControl().findByParentId(
							function.getParent()).size() + 1));
				
			}
			function.setDescription(function.getTitle());
			if(function.getActive()==null) {
				function.setActive("1");
			}
			if(function.getRpflag()==null) {
				function.setRpflag("0");
			}
			if(function.getUptype()==null) {
				function.setUptype("9");
			}
			if(function.getPublicflag()==null) {
				function.setPublicflag("0");
			}
			if(function.getRbflag()==null) {
				function.setRbflag("1");
			}
			if(function.getReauflag()==null) {
				function.setReauflag("0");
			}
		
				sess.save(res);
				sess.flush();
				function.setFunctionid(res.getId());
				function.setResourceid(res.getId());
				sess.save(function);
				sess.flush();
			} catch (PrivilegeException e) {
				e.printStackTrace();
				throw new RadowException("系统发生未知异常，请联系管理员！",e);
			}
			
			
			
			/*if(functionid != null && functionid.trim().length()>0) {
				SmtFunction	smtfunction  = (SmtFunction) sess.get(SmtFunction.class, functionid);
				SmtResource	smtres =null;
				try {
					if(smtfunction!=null) {
						BeanUtil.propertyCopy(smtfunction, function);
						smtres = (SmtResource) sess.get(SmtResource.class, smtfunction.getResourceid());
					}
					if(smtres!=null) {
						BeanUtil.propertyCopy(smtres, res);
					}
				} catch (PrivilegeException e) {
					e.printStackTrace();
					throw new RadowException("资源信息异常！请检查输入",e);
				}
			}
			
			PMPropertyCopyUtil.copyElementsValueToObj(function, this);
			if(res.getId()==null) {
				res.setId(function.getFunctionid());
			}
			res.setName(function.getTitle());
			res.setDesc(res.getName());
			res.setOwner(function.getOwner());
			res.setParent(function.getParent());
			res.setStatus(function.getActive());
			res.setType(function.getType());
			res.setUrl(function.getLocation());
			
			IResourceControl irc = PrivilegeManager.getInstance()
					.getIResourceControl();
			
			try {
				irc.saveOrUpdateResource(res);
			} catch (PrivilegeException e) {
				e.printStackTrace();
				throw new RadowException("资源保存或更新时发生异常",e);
			}
			
			List<ResourceVO> list = null;
			try {
				list = irc.queryByName(res.getName(), true);
			} catch (PrivilegeException e) {
				e.printStackTrace();
			}
			if (list.size() > 0) {
				function.setResourceid(list.get(0).getId());
			}

			try {
				irc.saveOrUpdateFunction(function);
			} catch (PrivilegeException e) {
				e.printStackTrace();
				throw new RadowException("资源保存或更新时发生异常",e);
			}*/
			this.setMainMessage("操作成功");
			initMMBS();
			this.getExecuteSG().addExecuteCode("var otree=Ext.getCmp('orgTree'); var otreenode=otree.getNodeById('"+function.getParent()+"');otree.getLoader().load(otreenode);otreenode.expand();");
			
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
				HashMap<String,Object> treeNode = new HashMap<String,Object>();
				treeNode.put("text", res.getTitle());
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
			/*jsonStr.append("]");
			int i = 0;
			int last = list.size();
			for (FunctionVO res : list) {
				if(i==0 && last==1) {
					jsonStr.append("[{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ res.getFunctionid() + "')\"");
					jsonStr.append("}]");
				}else if (i == 0) {
					jsonStr.append("[{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ res.getFunctionid() + "')\"");
					jsonStr.append("}");
				} else if (i == (last - 1)) {
					jsonStr.append(",{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ res.getFunctionid() + "')\"");
					jsonStr.append("}]");
				} else {
					jsonStr.append(",{\"text\" :\"" + res.getTitle()
							+ "\" ,\"id\" :\"" + res.getFunctionid()
							+ "\" ,\"cls\" :\"folder\",");
					jsonStr.append("\"href\":");
					jsonStr.append("\"javascript:radow.doEvent('querybyid','"
							+ res.getFunctionid() + "')\"");
					jsonStr.append("}");
				}
				i++;
			}
			*/
		} else {
			jsonStr.append("{}");
		}
		//System.out.println(jsonStr.toString());
		this.setSelfDefResData(jsonStr.toString());
		return EventRtnType.XML_SUCCESS;
	}
}
