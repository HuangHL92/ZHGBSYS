<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@include file="/comOpenWinInit.jsp"%>
<%
	String ctxPath = request.getContextPath();
%>
<script type="text/javascript">var cxt_path = "<%=ctxPath%>";</script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath() %>/picCut/js/jquery-1.4.2.js"></script>

<odin:hidden property="downfile" />
<odin:hidden property="batchid"/>
<odin:toolBar property="toolbar">
<odin:fill/>
<odin:buttonForToolBar text="����ʧ��ģ������"  id="expModelExcel" icon="images/icon/table.gif"></odin:buttonForToolBar>
<odin:buttonForToolBar text="����ʧ��ԭ��Excel" isLast="true" id="expExcel" icon="images/icon/table.gif"></odin:buttonForToolBar>
<%-- <odin:buttonForToolBar text="�ٴε���" isLast="true" id="impAgain" ></odin:buttonForToolBar> --%>
</odin:toolBar>
<div>
	<table style="width: 100%">
		<tr>
			<td>	
			<iframe id="ImpA15File" name="ImpA15File" height="100px" width="620px" src="<%=ctxPath%>/pages/publicServantManage/ImpA15File.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
		</tr>
		<tr>
			<td>
			<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid property="memberGrid" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" height="370" topBarId="toolbar">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="id" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a1517" />
							<odin:gridDataCol name="a1521" />
							<odin:gridDataCol name="status" />
							<odin:gridDataCol name="num" />
							<odin:gridDataCol name="failure" />
							<odin:gridDataCol name="a0192" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
<%-- 							<odin:gridRowNumColumn></odin:gridRowNumColumn> --%>
							
							
							<odin:gridColumn dataIndex="a0000" width="120" header="��Ա����" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="a1517" width="120" header="��ȿ���" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="a1521" width="120" header="���" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="status" width="120" header="�Ƿ�����ȷ�ϵ���" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="num" width="60" header="ʧ�����" align="center" />
							<odin:gridColumn dataIndex="failure" width="120" header="ʧ��ԭ��" align="center"/>
							<odin:gridColumn dataIndex="a0192" width="230" header="������λ��ְ��" align="center"/>
							
							<odin:gridColumn dataIndex="id" width="120" header="����" align="center" isLast="true" renderer="caozuo"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</div>
			</td>
		</tr>
		<tr >
		<td><div id='isFlag'></div></td>
		</tr>
	</table>
</div>


<%
	String ctxPaths = request.getContextPath();
%>
<script>
function impCallback(){
	$h.alert("ϵͳ��ʾ","����ɹ���",function(){
		parent.odin.ext.getCmp('impA15').close();
	});
	
}

function caozuo(param,obj,record){
	var value=record.data.id;
	var status=record.data.status;
	var failure=record.data.failure;
	var a0000=record.data.a0000;
	var a1517=record.data.a1517;
	var a1521=record.data.a1521;
	var a0192=record.data.a0192;//������λ��ְ����
	var name=failure.substring(failure.indexOf('(')+1,failure.indexOf(')'));//��ȡ����
	if(status=='0'){
		return "<a href=\"javascript:#\">ȷ�ϵ����ɲ���</a>"; 
	}
	if(failure.indexOf('ϵͳ������') != -1 ){
		return "<a href=\"javascript:marryInfo('"+name+"','"+a0192+"','"+a1521+"','"+a1517+"','"+value+"')\">ƥ��</a>      <a href=\"javascript:deleteReport('"+value+"')\">ɾ��</a>";
	}
	else if(failure.indexOf('��ȿ����ظ�') != -1 ){
		return "<a href=\"javascript:cover('"+value+"','"+a0000+"','"+a1517+"','"+a1521+"','"+name+"')\">����</a>      <a href=\"javascript:deleteReport('"+value+"')\">ɾ��</a>"; 
	}
	else{
		return "<a href=\"javascript:deleteReport('"+value+"')\">ɾ��</a>"; 
	}
}

var ctxPath = '<%=ctxPaths%>';
function marryInfo(name,a0192,a1521,a1517,value){
 	//ת��,��ʱͨ��url���ݲ���
	 name=escape(name);
	 a0192=escape(a0192);
	 a1517=escape(a1517);
// 	 $h.openWin('marryInfo','pages.publicServantManage.MarryInfo&name='+name+'&a0192='+a0192,'�ظ���Աƥ�� ',570,400,"",ctxPath);
// 	$h.openPageModeWin
	$h.openWin('marryInfo','pages.publicServantManage.MarryInfo&name='+name+'&a0192='+a0192+'&a1521='+a1521+'&a1517='+a1517+'&value='+value,'�ظ���Աƥ�� ',570,400,"",ctxPath,null,
			{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}
function deleteReport(value){
	
	radow.doEvent('deleteReport', value);
	
}

function cover(value,a0000,a1517,a1521,name){
	var str = value+':'+a0000+':'+a1517+':'+a1521+':'+name;
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ񸲸ǣ�",function(id) { 
		if("yes"==id){
			radow.doEvent('cover', str);
		}else{

		}		
	});	
	
}
function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function cc(){
	
}
function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("ϵͳ��ʾ", msgs, function(but) {  
				
			}); 
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}

//ȷ�ϵ���
function confImp(){
	radow.doEvent('confImp');
}
//ȡ������
function callImp(){
	radow.doEvent('callImp');
}

//
function result(){
	document.getElementById('isFlag').innerHTML='';
}
</script>