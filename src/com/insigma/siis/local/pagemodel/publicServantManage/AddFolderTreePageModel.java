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
		
		
		String data = this.getRadow_parent_data();//��Ϊ�½�ʱRadow_parent_dataΪ"NEW",��Ϊ�޸�ʱΪ���뼯����
		
		String[] ids =  data.split(",");
		nodeId = ids[0];			//������ʾ
		String treeId = ids[1];		//��ǰѡ����ļ�����id
		String a0000 = ids[2];		//��ǰ��Աid
		
		HBSession sess = HBUtil.getHBSession();
		String sql = "from FolderTree where id = '"+treeId+"' and a0000 = '"+a0000+"'";
		
		if(!"NEW".equals(nodeId)) {				//�޸�
			
			List listFolderTree = sess.createQuery(sql).list();
			
			if (listFolderTree != null && listFolderTree.size() > 0) {
				FolderTree folderTree = (FolderTree) listFolderTree.get(0);
				PMPropertyCopyUtil.copyObjValueToElement(folderTree, this);
			}
			
			
		} else {								//����
			this.getPageElement("parentId").setValue(treeId);
			this.getPageElement("a0000").setValue(a0000);
			//��ѯ�����ļ������ƣ���ֵ��ҳ��
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
	
	
	//�������޸��ļ���
	@PageEvent("save.onclick")
	@Transaction
	public int save() throws RadowException{
		
		HBSession sess = HBUtil.getHBSession();
		String a0000 = this.getPageElement("a0000").getValue();
		String parentId = this.getPageElement("parentId").getValue();
		
		//�����ļ���
		if("NEW".equals(nodeId)) {
			
			FolderTree folderTree = new FolderTree();
			this.copyElementsValueToObj(folderTree, this);
			
			//�ж��ļ��������Ƿ��ظ�
			String sql = "select count(1) from FolderTree where  a0000 ='"+a0000+"' and name='"+folderTree.getName()+"'";
			Object c = sess.createSQLQuery(sql).uniqueResult();
			if(!String.valueOf(c).equals("0")){
				this.setMainMessage("["+folderTree.getName()+"]���ļ����Ѵ��ڣ������������ļ������ƣ�");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			//��ѯ�����(��ֵ��)��ͬ�����˵�id
			String addTreeId = null;
			String sqlTreeId = "select max(id) from foldertree where parentid = '"+parentId+"'";
			Object maxTreeIdO = sess.createSQLQuery(sqlTreeId).uniqueResult();
			
			if(maxTreeIdO != null && !maxTreeIdO.equals("")){	
				String maxTreeId = maxTreeIdO.toString();
				
				int newTreeId = Integer.parseInt(maxTreeId.substring(parentId.length() +1)) + 1;
				
				addTreeId = parentId + "." + newTreeId;
			}else{		//maxTreeIdΪ�գ���ǰ��û���¼��ļ��У�id = parentId + .001
				addTreeId = parentId + ".001";
			}
			
			folderTree.setId(addTreeId);  			//������id��set��������
			sess.save(folderTree);	
			
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("AddFolderTree");
		} else{							//�޸��ļ���
			
			FolderTree folderTree = new FolderTree();
			this.copyElementsValueToObj(folderTree, this);
			sess.saveOrUpdate(folderTree);	
			
			this.getExecuteSG().addExecuteCode("window.parent.reloadTree();");
			this.closeCueWindow("UpdateFolderTree");
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
	
}
