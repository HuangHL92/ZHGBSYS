package com.insigma.siis.local.pagemodel.publicServantManage;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Synchronous;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A71;
import com.insigma.siis.local.business.entity.CodeType;
import com.insigma.siis.local.business.entity.FolderTree;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_BS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;

public class AddFolderTreePageModel extends PageModel{

	ZWHZYQ_001_006_BS bs6 = new ZWHZYQ_001_006_BS();
	private static String nodeId;
	
	@Override
	public int doInit() throws RadowException {
		
		
		String data = this.getRadow_parent_data();//当为新建时Radow_parent_data为"NEW",当为修改时为代码集编码
		
		String[] ids =  data.split(",");
		nodeId = ids[0];			//操作标示
		String treeId = ids[1];		//当前选择的文件夹树id
		String a0000 = ids[2];		//当前人员id
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "from FolderTree where id = '"+treeId+"' and a0000 = '"+a0000+"'";
		
		if(!"NEW".equals(nodeId)) {				//修改
			
			List listFolderTree = sess.createQuery(sql).list();
			
			if (listFolderTree != null && listFolderTree.size() > 0) {
				FolderTree folderTree = (FolderTree) listFolderTree.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(folderTree, this);
			}
			
			
		} else {								//新增
			this.getPageElement("parentId").setValue(treeId);
			this.getPageElement("a0000").setValue(a0000);
			//查询出父文件夹名称，赋值到页面
			List listFolderTree = sess.createQuery(sql).list();
			
			if (listFolderTree != null && listFolderTree.size() > 0) {
				FolderTree folderTree = (FolderTree) listFolderTree.get(0);
				this.getPageElement("parentIdName").setValue(folderTree.getName());
			}
			
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@NoRequiredValidate
	@PageEvent("cancel.onclick")
	public int cancel(){
		if("NEW".equals(nodeId)) {
			this.closeCueWindow("AddFolderTree");
		} else {
			this.closeCueWindow("UpdateFolderTree");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	//新增，修改文件夹
	@PageEvent("save.onclick")
	@Transaction
	public int save() throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		String parentId = this.getPageElement("parentId").getValue();
		
		//新增文件夹
		if("NEW".equals(nodeId)) {
			
			FolderTree folderTree = new FolderTree();
			this.copyElementsValueToObj(folderTree, this);
			
			//判断文件夹名称是否重复
			String sql = "select count(1) from FolderTree where  a0000 ='"+a0000+"' and name='"+folderTree.getName()+"'";
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
				this.setMainMessage("["+folderTree.getName()+"]该文件夹已存在，请重新输入文件夹名称！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//查询出最大(数值上)的同级树菜单id
			String addTreeId = null;
			String sqlTreeId = "select max(id) from foldertree where parentid = '"+parentId+"'";
			Object maxTreeIdO = sess.createSQLQuery(sqlTreeId).uniqueResult();
			
			if(maxTreeIdO != null && !maxTreeIdO.equals("")){	
				String maxTreeId = maxTreeIdO.toString();
				
				int newTreeId = Integer.parseInt(maxTreeId.substring(parentId.length() +1)) + 1;
				
				addTreeId = parentId + "." + newTreeId;
			}else{		//maxTreeId为空，当前还没有下级文件夹，id = parentId + .001
				addTreeId = parentId + ".001";
			}
			
			folderTree.setId(addTreeId);  			//将主键id，set到对象中
			sess.save(folderTree);	
			
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("AddFolderTree");
		} else{							//修改文件夹
			
			FolderTree folderTree = new FolderTree();
			this.copyElementsValueToObj(folderTree, this);
			sess.saveOrUpdate(folderTree);	
			
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("UpdateFolderTree");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
}
