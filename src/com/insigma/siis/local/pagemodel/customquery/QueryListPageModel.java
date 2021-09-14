package com.insigma.siis.local.pagemodel.customquery;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.comm.query.PageQueryData;
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
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Customquerylist;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
/**
 * @author lixy
 *
 */
public class QueryListPageModel extends PageModel {

	private CustomQueryBS ctcBs=new CustomQueryBS();
	
	@Override
	public int doInit() throws RadowException {
		//this.setNextEventName("queryList");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	/* �����б�
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("loadList.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int load() throws RadowException, AppException, IOException, SQLException{
		String ri=this.getPageElement("cueRowIndex").getValue();
		if(ri==null||"".equals(ri))
			throw new AppException("��ѡ��Ҫ������б�");
		int rowIdx= Integer.parseInt(ri);
		PageElement pe = this.getPageElement("listGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		HashMap<String,Object> map=list.get(rowIdx);
		Customquerylist cql=(Customquerylist)HBUtil.getHBSession().get(Customquerylist.class, map.get("cqli").toString());
		//BASE64Decoder decoder = new BASE64Decoder(); 
		//CommonQueryBS.systemOut(new String(cql.getListdata().getBytes((long)1, (int)cql.getListdata().length())));
		//this.createPageElement("peopleInfoGrid",ElementType.GRID, true).setValue(new String(cql.getListdata().getBytes((long)1, (int)cql.getListdata().length())));//�����ص��б�������ӵ�����������
		String sql = CommSQL.getComSQL(this.request.getSession().getId())+" where a01.a0000 in (select a0000 from listinfo li where  cqli='"+cql.getCqli()+"') ";
		this.getPageElement("sql").setValue(sql);
		
		//this.getExecuteSG().addExecuteCode("parent.document.getElementById('orgjson').value='';");
		
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('orgjson').value='';");
		
		this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('sql').value=document.getElementById('sql').value;"
				+ "window.realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
		//this.closeCueWindow("win2");
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('win2').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* ɾ���б�
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("deleteEvent")
	@NoRequiredValidate
	public int deleteEvent(String rowIndex) throws RadowException, AppException{
		//String ri=this.getPageElement("cueRowIndex").getValue();
		if(rowIndex==null||"".equals(rowIndex))
			throw new AppException("��ѡ��Ҫɾ�����б�");
		int rowIdx= Integer.parseInt(rowIndex);
		PageElement pe = this.getPageElement("listGrid");
		List<HashMap<String, Object>> list = pe.getValueList();
		HashMap<String,Object> map=list.get(rowIdx);
		ctcBs.delCl(map.get("cqli").toString());
		this.getPageElement("cueRowIndex").setValue("");//���ѡ������
		this.setNextEventName("queryList");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/* �趨ѡ���кŵ�ҳ����
	 * @author 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("listGrid.rowclick")
	@GridDataRange(GridData.cuerow)
	@AutoNoMask
	@NoRequiredValidate
	public int setCueRow() throws RadowException{
		int cueRowIndex = this.getPageElement("listGrid").getCueRowIndex();
		this.getPageElement("cueRowIndex").setValue(cueRowIndex+"");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("queryList")
	public int queryList() throws RadowException{
		   String data= JSONArray.fromObject(ctcBs.getQueryList(SysUtil.getCacheCurrentUser().getLoginname())).toString();
		   if(data!=null&&!"".equals(data))
   		this.getPageElement("listGrid").setValue(data
   				);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("closeWin.onclick")

	public int closeWin() throws RadowException{
		//this.closeCueWindow("win2");
		this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('win2').close();");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("querybyid")
	public int getCurName(String id) throws RadowException{
		String sql="SELECT CQLI,LISTNAME  FROM CUSTOMQUERYLIST WHERE CQLI='"+id+"' ORDER BY LISTTIME DESC";
		try {
			PageQueryData pageQuery = this.pageQuery(sql,"SQL", -1, 999); 	
			List list= (List) pageQuery.getData();
			if(list.size()>0){	//���ݲ�Ϊ��
				Map map = (Map)list.get(0);
				this.getPageElement("perID").setValue(map.get("cqli")+"");
			}else{	//����Ϊ��
				this.getPageElement("perID").setValue("-1");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @author wangs2
	 * 			��������
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 * @throws IOException
	 * @throws SQLException
	 */
	@PageEvent("loadListNew.onclick")
	@AutoNoMask
	@NoRequiredValidate
	public int loadNew() throws RadowException, AppException, IOException, SQLException{
		
		String curID="";
		try {
			curID=this.getPageElement("perID").getValue();
		} catch (RadowException e) {
			this.setMainMessage("��ȡҳ��<�ڵ�ID>ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		if(curID == null || "".equals(curID)){
			this.setMainMessage("��ȡҳ��<�ڵ�ID>ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		
		Customquerylist cql=(Customquerylist)HBUtil.getHBSession().get(Customquerylist.class, curID);
		if(cql != null && !"".equals(cql)){
			CustomQueryPageModel.LISTADDCCQLI=cql.getCqli();
			CustomQueryPageModel.LISTADDNAME=cql.getListname();
			CustomQueryPageModel.QUERYLISTFLAG=true;
			String sql = CommSQL.getComSQL(this.request.getSession().getId())+" where a01.a0000 in (select a0000 from listinfo li where  cqli='"+cql.getCqli()+"') ";
			this.getPageElement("sql").setValue(sql);
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('orgjson').value='';");
			this.getExecuteSG().addExecuteCode("window.realParent.document.getElementById('sql').value=document.getElementById('sql').value;"
					+ "window.realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			this.getExecuteSG().addExecuteCode("parent.Ext.getCmp('win2').close();");
		}else {
			CustomQueryPageModel.LISTADDCCQLI="-1";
			CustomQueryPageModel.LISTADDNAME="��";
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("deleteList")
	@NoRequiredValidate
	public int deleteList(){
		String curID="";
		try {
			curID=this.getPageElement("perID").getValue();
		} catch (RadowException e) {
			this.setMainMessage("��ѡ����Ҫɾ�����б�");
			return EventRtnType.FAILD;
		}
		if(curID == null || "".equals(curID)){
			this.setMainMessage("��ѡ����Ҫɾ�����б�");
			return EventRtnType.FAILD;
		}
		try {
				ctcBs.delClNew(curID);
				CustomQueryPageModel.LISTADDCCQLI="-1";
				CustomQueryPageModel.LISTADDNAME="��";
		} catch (AppException e1) {
			this.setMainMessage("ɾ��ʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		try {
			this.getPageElement("perID").setValue("-1");
		} catch (RadowException e) {
			this.setMainMessage("���ýڵ�IDʧ�ܣ�");
			return EventRtnType.FAILD;
		}
		this.getExecuteSG().addExecuteCode("initTree();");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
