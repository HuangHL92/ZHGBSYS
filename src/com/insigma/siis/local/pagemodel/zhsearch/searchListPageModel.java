package com.insigma.siis.local.pagemodel.zhsearch;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class searchListPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("searchListGrid.dogridquery");
		return 0;
	}
	//�б��ѯ
	@PageEvent("searchListGrid.dogridquery")
	public int listquery(int start, int limit) throws RadowException {
		StringBuffer sql=new StringBuffer();
		sql.append(" select sjly,queryid,querysql,queryname  from ");
		sql.append(" ( select '����������ѯ'  sjly   ,queryid , querysql , queryname ,to_char(createtime) createtime from Customquery ");
		sql.append(" union ");
		sql.append(" select '�Զ����ѯ'   sjly ,   qvid queryid,viewnametb querysql ,chinesename queryname,createtime  from qryview " );
		sql.append(" where 1=1 and type='4' )");
		sql.append(" order by sjly,createtime desc ");
		System.out.println("sql:"+sql.toString());
		this.pageQuery(sql.toString(), "sql", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	//�б�˫���¼�
	@PageEvent("searchListGrid.rowdbclick")
	public int gridDbclick() throws RadowException {
		Grid grid1=(Grid) this.getPageElement("searchListGrid");
		String sjly=(String) grid1.getValue("sjly");
		String queryid=(String) grid1.getValue("queryid");
		String querysql=(String) grid1.getValue("querysql");
		if("�Զ����ѯ".equals(sjly)) {
			 this.getPageElement("qvid").setValue(queryid);
		     //����ڸ�ҳ����ajax �������ø�����ֵ
			 this.getExecuteSG().addExecuteCode("realParent.document.getElementById('qvid').value='"+queryid+"'");
		     //����ǰ̨�رմ���
			 this.getExecuteSG().addExecuteCode("closeWin();");
		     //���ø�ҳ���js����
		     this.getExecuteSG().addExecuteCode("realParent.sList('"+queryid+"');");
			
			
			
			
		}else {
			this.getExecuteSG().addExecuteCode("realParent.document.getElementById('checkedgroupid').value=''");
	        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('tabn').value='tab2'");
			
	        this.getPageElement("sql").setValue(querysql);
	        this.getExecuteSG().addExecuteCode("realParent.document.getElementById('sql').value=document.getElementById('sql').value");
	        //ҳ�潨һ����sql��
	        
	        this.getExecuteSG().addExecuteCode("closeWin();");
	        String queryType = (String)request.getSession().getAttribute("queryType");
	        this.request.getSession().setAttribute("queryType", "1");
	        //���ø�ҳ��pageModel�еķ���
        	if("define".equals(queryType)){
				this.getExecuteSG().addExecuteCode("realParent.changeField()");
				request.getSession().removeAttribute("queryType");
			}else{
				this.getExecuteSG().addExecuteCode("realParent.radow.doEvent('peopleInfoGrid.dogridquery');");
			}
		}
		
		
		return EventRtnType.NORMAL_SUCCESS;
	}		
	
	@PageEvent("doDel")
	public int doDel(String value) {
		HBUtil.getHBSession().createSQLQuery("delete from Customquery where queryid='"+value+"'").executeUpdate();
		HBUtil.getHBSession().createSQLQuery("delete from qryview where qvid='"+value+"'").executeUpdate();
		this.getExecuteSG().addExecuteCode("radow.doEvent('searchListGrid.dogridquery')");
		this.setMainMessage("ɾ���ɹ�");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
}
