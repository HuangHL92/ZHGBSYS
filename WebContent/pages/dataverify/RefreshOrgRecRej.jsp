<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<odin:toolBar property="btnToolBar">
</odin:toolBar>
<odin:panel contentEl="Content" property="ManagePanel" topBarId="btnToolBar" />
<div id="Content" >

<odin:hidden property="id"/>
<odin:hidden property="type"/>
<odin:editgrid property="Fgrid" autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="detail" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="starttime" />
		<odin:gridDataCol name="dtable" />
		
		<odin:gridDataCol name="endtime" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="detail" width="80" header="����׶�"  align="left" />
		<odin:gridColumn dataIndex="status" width="40" header="����״̬" align="left" renderer="rendererthis" />
		<odin:gridColumn dataIndex="starttime" width="100" header="��ʼʱ��"  align="center" />
		<odin:gridColumn dataIndex="dtable" hidden="true" header="��Ӧ��" align="center" />
		<odin:gridColumn dataIndex="endtime" width="100" header="����ʱ��" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">
function myrefresh() 
{
     radow.doEvent('btnsx');
} 
var timer1= window.setInterval("myrefresh()",10000); 


function rendererthis(value, params, rs, rowIndex, colIndex, ds){
	/**if(rowIndex==31){
		if(value=='2'){
			clearInterval(timer1);
			///downFile(rs.data['adf1']);
		}
	}*/
	var tab= rs.data['dtable'];
	if(tab=='DONE'){
		clearInterval(timer1);
		alert(rs.data['detail']);
		//�رյ�������
		if(document.getElementById("type").value !== '1'){
///			parent.odin.ext.getCmp('refreshWin1').hide();
			parent.Ext.getCmp('refreshWin1').close();
		}
		
		realParent.odin.ext.getCmp('MGrid').store.reload();
		
		//����̨��ͼ�����Ϣͬ������
		realParent.parent.gzt.window.location.reload();
	}
	var v = '';
	if(value=='2'){
		v ='���';
	} else{
		v ='������';
	}
	return v;
}

function downFile(downfile){
	var w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}
function cc()
{
	w.close();
}
</script>