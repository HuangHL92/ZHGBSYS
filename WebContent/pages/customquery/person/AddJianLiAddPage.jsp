<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<!-- ��ʽ ��ʼ -->
<style>
/* .x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
} */
.edui-default{height:170px;}
.editorclass{height:170px; width: 835px; margin: 5, 0, 0, 5; overflow-y: scroll; overflow-x: hidden; border: 1px solid #B2B2B2;}
.editorclass1{height:60px; width: 1115px; margin: 5, 0, 0, 5; overflow-y: scroll; overflow-x: hidden; border: 1px solid #B2B2B2;}
/* #contenttext2{margin: 5, 0, 0, 5; height:523px;width: 740px;} */
</style>
<!-- ��ʽ ���� -->


<!-- JS ��ʼ -->
<script type="text/javascript" charset="gbk" src="rmb/jquery-1.7.2.min.js"> </script>
<script type="text/javascript" charset="gbk" src="ueditor/ueditor.config.js"></script>

<script type="text/javascript" charset="gbk" src="ueditor/ueditor.all.js"> </script>
<!--�����ֶ��������ԣ�������ie����ʱ��Ϊ��������ʧ�ܵ��±༭������ʧ��--> 
<!--������ص������ļ��Ḳ������������Ŀ����ӵ��������ͣ���������������Ŀ�����õ���Ӣ�ģ�������ص����ģ�������������--> 
<script type="text/javascript" charset="gbk" src="ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<!-- JS ���� -->



<!-- ������Ϣ ��ʼ -->
<body style="overflow-y:hidden;">
<odin:hidden property="a0000"/>
<odin:hidden property="a0101"/>
<odin:hidden property="a0184"/>
<!-- <div id="jlcontent" style="width:835px"> -->
<odin:toolBar property="a1701Show" applyTo="a1701Bar">
	<odin:textForToolBar text="<font style=&quot;font-size:12px;font-weight:bolder;&quot;>����Ԥ��</font><font style=&quot;font-size:12px;color:red;font-weight:bolder;&quot;>�����������ʽ��Ԥ��Ч��Ϊ׼��˫�������ı��������л�������ά��ҳ�棩</font>"></odin:textForToolBar>
	<odin:fill />
<%-- 	<odin:buttonForToolBar id="bdjgBtn" handler="bdjgFunc" text="�����ȶ�" icon="image/u53.png"/> --%>
	<odin:buttonForToolBar id="splitBtn" handler="initJLForOne" text="�������" icon="image/icon040a15.gif"/>
	<odin:buttonForToolBar id="toUp" handler="changeToUp" text="����ά��" icon="image/icon040a2.gif"/>
</odin:toolBar>
<div id= "tabJLInit" style="display:none">
	<odin:tab id="tabInit">
	<odin:tabModel>
		<odin:tabItem title='������ʼ��' id='JLInit'></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="JLInit" className="JLInit">
	<table width="100%">
   		<tr>
   			<td>
	   			<odin:toolBar property="a1701Hand" applyTo="a1701HandBar">
					<odin:textForToolBar text="<font style=&quot;font-size:12px;color:red;&quot;>����ά��������Ϣ�����·���������ֶ���д��ʹ�ø���ճ����ֻ�ɱ���һ�Σ�</font>"></odin:textForToolBar>
					<odin:fill />
					<odin:buttonForToolBar id="saveBtn" handler="saveA1701" text="�������沢���" icon="image/icon040a15.gif"/>
				</odin:toolBar>
   				<div id="a1701HandBar"></div>
   				<div id="editorHandId" class="editorHandClass">
					<textarea id="a1701ByHand" style="height:170px; width: 857px; font-size:14px;"></textarea>
				</div>
   			</td>
   		</tr>
   	</table>
   	</odin:tabCont>
   	</odin:tab>
</div>
<div id="tabJLup">
<odin:tab id="tab">
<odin:tabModel>
<%-- 	<odin:tabItem title='����Ԥ��' id='editshow' ></odin:tabItem> --%>
	<odin:tabItem title='����ά��' id='editup'  isLast="true"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex='editshow' className="editshow">
   	<table width="100%" id="editLook">
   		<tr>
   			<td>
   				<div id="a1701Bar"></div>
   				<div id="editorid" class="editorclass">
    				<odin:hidden property="a17" name="����"/>
					<script id="editor" type="text/plain" style="width:100%;height:100%; "></script>
				</div>
   			</td>
   		</tr>
   	</table>
</odin:tabCont>
<odin:tabCont itemIndex='editup' className="editup">
   	<table width="100%" style="overflow:auto">
   		<tr>
   			<td style="height:170px; width: 857px;">
    			<odin:toolBar property="pageTopBar">
					<odin:textForToolBar text="<font style=&quot;font-size:12px;font-weight:bolder;&quot;>����ά��</font><font style=&quot;font-size:12px;color:red;font-weight:bolder;&quot;>��Ĭ����ʾ������ʱ���б����������ʽ��Ԥ��Ч��Ϊ׼����</font> "></odin:textForToolBar>
					<odin:fill />
					<%-- <odin:buttonForToolBar id="gzgl" text="������Ϣ" icon="image/u53.png"/> --%>
					<%-- <odin:buttonForToolBar id="bdjgBtn" handler="bdjgFunc" text="�����ȶ�" icon="image/u53.png"/> --%>
<%-- 					<odin:buttonForToolBar id="splitBtn" handler="initJLForOne" text="�������" icon="image/icon040a15.gif"/> --%>
					<odin:buttonForToolBar id="addBtn" handler="addFunc" text="������" icon="image/icon040a2.gif"/>
					<%-- <odin:buttonForToolBar id="saveBtn" handler="saveFunc" text="Ԥ������" icon="image/u53.png"/> --%>
					<odin:buttonForToolBar id="composeBtn" handler="composeFunc" text="���沢���ɼ���"   isLast="true"/>
				</odin:toolBar>
   				<odin:editgrid2 property="grid1" topBarId="pageTopBar" afteredit="cellEdit" width="670" height="420" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- ��ʱչʾ�Ƿ�Ϊ�ڼ��������� 1 �� 0 ���� -->
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a1701" />
						<odin:gridDataCol name="a1702" />
						<odin:gridDataCol name="a1703" />
						<odin:gridDataCol name="a0221" />
						<odin:gridDataCol name="complete" />
						<odin:gridDataCol name="a1704" />
						<odin:gridDataCol name="a1705" />
						<odin:gridDataCol name="a1706" />
						<odin:gridDataCol name="a1707" />
						<odin:gridDataCol name="a1708" />
						<odin:gridDataCol name="a0192e" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 	dataIndex="a1799"  	header=""  	   width="40" editor="text" edited="false" align="center" renderer="indexFormatter"></odin:gridEditColumn2>
						<odin:gridColumn 		dataIndex="a1700"  	header="����"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="6"	header="��ʼ"  width="70"  editor="text" align="center" renderer="changeTrim" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="6"	header="����"  width="70"  editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="complete" 	header="����<font style=font-size:12px;color:red;>������ĳһ�м����·����Ӿ����ģ�����ѡ����Ӧ�кţ�<br/>�ٵ�������С�˫��������������Ԫ������޸ģ���</font>"  width="350"  editor="text" align="left" menuDisabled="true" sortable="false" edited="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a0221" codeType="ZB09" header="ְ����"  width="30"  edited="false" editor="select" align="center" filter="code_status = '1' and code_leaf = '1'" menuDisabled="true" sortable="false" hidden="true"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a0192e" codeType="ZB148" header="ְ��"  width="30"  edited="false" editor="select" align="center" filter="code_status = '1' and code_leaf = '1'" menuDisabled="true" sortable="false"  hidden="true"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="�ص��λ" editorId="asdasd2" width="120"  edited="true" editor="selectTree"  align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="��λ����" editorId="asd1" width="70"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="�ֹܹ�������"  width="120"  edited="true" editor="selectTree" ischecked="true" editorId="asd" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="�ֹܹ�����Ч"  width="120"  edited="true" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="��ע"  width="150"  edited="true" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="caozuo" 	header=""  width="40" edited="false" editor="text" align="center" renderer="caozuo"  menuDisabled="true" sortable="false" isLast="true"></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
							data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid2>
   			</td>
   		</tr>
   	</table>
   	<odin:hidden property="a1700"/>
	<odin:hidden property="a0000"/>
	<odin:hidden property="a1799"/>
<%--  	<odin:groupBox title="��������ǰ����������Ԥ��" width="100%" >
		<div id="editorid1" class="editorclass1">   --%>
			<odin:hidden property="a1701z" name="����" />
<%--  			<script id="editor1" type="text/plain" style="width: 100%;   height:30;"></script>
		</div>
	</odin:groupBox>   --%>
	<odin:groupBox title="��������ά��" width="100%">
	<odin:toolBar property="saveBtn" applyTo="save">
	<odin:fill/>
	<odin:buttonForToolBar id="composeBtn" handler="composeFunc1" text="���沢��������������" icon="image/btn_adopt.png" isLast="true"/>
	</odin:toolBar>
	<table style="width:100%;">
		<tr>
			<td>
				<div id="save"></div>
			</td>
		</tr>
		<tr>
			<td>
			<table>
			<tr style="height:30;">
				<odin:NewDateEditTag property="a1701" label="��ʼʱ��" isCheck="false" labelSpanId="startSpanId" maxlength="6"  width="170"></odin:NewDateEditTag>
				<odin:NewDateEditTag property="a1702" label="����ʱ��" isCheck="false" labelSpanId="endSpanId" maxlength="6" width="170"></odin:NewDateEditTag>
				<odin:textEdit property="a1703" label="��������"  width="220" colspan="6"></odin:textEdit>
				<odin:select2 property="a1706" data="['������','������'],['�ۺϹ�����','�ۺϹ�����'],['����ҵ�͹�ҵ������','����ҵ�͹�ҵ������']
				,['�����ݺ���Ϣ������','�����ݺ���Ϣ������'],['�ǽ��ǹ���','�ǽ��ǹ���'],['����������','����������'],['������ó��','������ó��']
				,['ũҵũ����','ũҵũ����'],['�Ļ���չ��������','�Ļ���չ��������'],['���취������','���취������'],['��ҵ��Ӫ������','��ҵ��Ӫ������']
				,['���ڲ�����','���ڲ�����']" 
				width="130"  label="�ֹܹ�������" multiSelect="true" />
				
			</tr>
			<tr>	
				<tags:ComBoxWithTree property="a1705" codetype="JL02"  width="150"  label="�ص��λ" nodeDblclick="a1705change" />
				<odin:select2 property="a1704" codeType="JL02"  width="100"  label="��λ����" readonly="true"/>	
				<odin:textEdit property="a1707" label="�ֹܹ�����Ч"  width="220" colspan="6"></odin:textEdit>
				<odin:textEdit property="a1708" label="��ע" width="220" colspan="6"></odin:textEdit>
			</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
			<div id="zzjlgrid">
				<odin:toolBar property="zzjl" applyTo="gridTopBar">
					<odin:fill />
					<odin:buttonForToolBar id="addBtn" handler="addFunc1" isLast="true" text="������" icon="image/icon040a2.gif"/>
				</odin:toolBar>
				<div id ="gridTopBar"></div>
   				<odin:editgrid2 property="grid" title="��ܰ��ʾ�����ڴ��б�����ӵ�ǰ����������䡯�򡮿�ʱ��Ρ����ּ�����Ϣ,˫������ά��" afteredit="cellEdit" width="670"  height="130" autoFill="false" forceNoScroll="false" pageSize="50" sm="row" remoteSort="false">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a1799" />
						<odin:gridDataCol name="a1798" /><!-- ��ʱչʾ�Ƿ�Ϊ�ڼ��������� 1 �� 0 ���� -->
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a1701" />
						<odin:gridDataCol name="a1702" />
						<odin:gridDataCol name="a1703" />
						<odin:gridDataCol name="a0221" />
						<odin:gridDataCol name="complete"/>
						<odin:gridDataCol name="a1704" />
						<odin:gridDataCol name="a1705" />
						<odin:gridDataCol name="a1706" />
						<odin:gridDataCol name="a1707" />
						<odin:gridDataCol name="a1708" />
						<odin:gridDataCol name="a0192e" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridEditColumn2 	dataIndex="a1799"  	header=""  	   width="40" editor="text" edited="false" align="center" renderer="indexFormatter"></odin:gridEditColumn2>
						<odin:gridColumn 		dataIndex="a1700"  	header="����"  width="0"  edited="false" align="center" hidden="true" ></odin:gridColumn>
						<odin:gridEditColumn2 	dataIndex="a1701" maxLength="8"	header="��ʼ"  width="70" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1702" maxLength="8"	header="����"  width="70" edited="true" editor="text" align="center" menuDisabled="true" sortable="false" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1703" 	header="����"  width="350" edited="true" editorId="dsa11" editor="text" align="left" menuDisabled="true" sortable="false"></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1705" codeType="JL02" header="�ص��λ" editorId="dsa2" width="120"  edited="true" editor="selectTree"  align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1704" codeType="JL02" header="��λ����" editorId="dsa1" width="70"  edited="false" editor="select" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1706" codeType="EXTRA_TAGS"   header="�ֹܹ�������"  width="120"  edited="true" editor="selectTree" ischecked="true" editorId="dsa" align="center" ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1707"  header="�ֹܹ�����Ч"  width="120"  edited="true" editorId="dsa4" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="a1708"  header="��ע"  width="150"  edited="true" editorId="dsa5" editor="text" align="center"  ></odin:gridEditColumn2>
						<odin:gridEditColumn2 	dataIndex="caozuo" 	header=""  width="35" edited="false" editor="text" align="center" renderer="caozuo1"  menuDisabled="true" sortable="false" isLast="true"></odin:gridEditColumn2>
					</odin:gridColumnModel>
					<odin:gridJsonData>
						{
							data:[]
						}
					</odin:gridJsonData>
				</odin:editgrid2>
			</div>
			</td>
		</tr>
	</table>
	</odin:groupBox>
<%--    	<table>
   		<tr style="height:40px;">  			
   			<odin:textarea property="lead" label="��Ҫְ����Ҫ����"></odin:textarea>
   		</tr>
   	</table> --%>
   	<odin:hidden property="lead"/>
   	
</odin:tabCont>
</odin:tab>
</div>
</body>
<!-- </div>	 -->

<!-- ������Ϣ ���� -->


<!-- ���� ��ʼ -->
<script type="text/javascript">
 <%
/* String RrmbCodeType = (String)session.getAttribute("RrmbCodeType"); */
String RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	/* var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.EXTRA_TAGS[v[i]]){
				returnV += CodeTypeJson.EXTRA_TAGS[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	} */
	//Ext.getCmp('grid1').fireEvent("afteredit") 
	return value;
	
} 


Ext.onReady(function(){
	if(parent.document.getElementById("a0000").value!=''&&parent.document.getElementById("a0000").value!=null){
		document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	}else{
		var parentParam = window.dialogArguments.param;
		document.getElementById('a0000').value = parentParam.a0000;
	}
	
	
	
	
	var pgrid = Ext.getCmp('grid1');
	var dstore = pgrid.getStore();
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
					ddGroup : 'GridDD',
					copy : false,
					notifyDrop : function(dd,e,data){
						//ѡ���˶�����
						var rows = data.selections;
						//�϶����ڼ���
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						//�޸�store
						for ( i=0; i<rows.length; i++){
							var rowData = rows[i];
							if (!this.copy) dstore.remove(rowData);
							dstore.insert(index, rowData);
						}
						pgrid.view.refresh();
					}
				});
	/*dstore.on('load',function(){
		insertEmptyRow(this);
	});*/
	windowOnresize();
});
function changeTab(){
	document.getElementById('tabJLInit').style.display='none';
	document.getElementById('tabJLup').style.display='block';
	windowOnresize();
}
//����gridΪ��ק��������Ӧ 
window.onresize = function() {
	windowOnresize();
}; 


function isQiJian(a1701str) {
    if(a1701str.indexOf("(��") == 0||a1701str.indexOf("����") == 0||
     a1701str.indexOf("(��") == 0||a1701str.indexOf("����") == 0||
     a1701str.indexOf("(1") == 0 ||a1701str.indexOf("��1") == 0||
     a1701str.indexOf("(2") == 0 ||a1701str.indexOf("��2") == 0){
      return true;
    }else{
      return false;
    }
}
//��ȡ�ַ����������۵�����Ϊ��ʱ�����ؿ��ַ���
function getObjStrOutNull(value) {
	return validatorNull(value) ? "" : value;
}
//�ж�Ԫ���Ƿ�Ϊ ��,"��",""
function validatorNull(value) {
	if(typeof value == "undefined" || value == null || value == ""){
		return true;
	}else{
		return false;
	}
}

function zzjlShow(a1700,a1799){
	var a0000=document.getElementById("a0000").value;
	var param="{'a1700':'"+a1700+"','a0000':'"+a0000+"','a1799':'"+a1799+"'}"
	$h.openWin('addZZJianLi', 'pages.customquery.person.AddZZJianLi', "��������ά��", 800, 600, param, '<%=request.getContextPath() %>', window, {maximizable:false, resizable:true, draggable:true}, true);
	//$h.openWin('addScheme',   'pages.bzpj.PJZBScheme','ָ�귽��ά��',                 430,635,null,'<%=request.getContextPath()%>',      null, p,true);
}
 function windowOnresize() {
	var clientWidth = document.body.clientWidth;
	var clientHeight = document.body.clientHeight;
	$('#tabJLInit').height(clientHeight);
	$('#tabJLInit').width(clientWidth);
	$('#tabJLup').height(clientHeight);
	$('#tabJLup').width(clientWidth);
	$('#tab').height(clientHeight);
	$('#tab').width(clientWidth);
	$('.odinTabCont').height(clientHeight-1);
	$('.odinTabCont').width(clientWidth-1);
	$('#editorid').height(clientHeight-50);
	$('#editorid').width(clientWidth-14);
	$('#editorHandId').height(clientHeight-60);
	$('#editorHandId').width(clientWidth-10);
	$('#editor').height(clientHeight);
	$('#editor').width(clientWidth-20);
	$('#a1701ByHand').height(clientHeight-60);
	$('#a1701ByHand').width(clientWidth-10);
	$('.editorid1').height(120);
	$('.editor1').height(110);
	$('#lead').width(clientWidth-110);
	var grid1 = Ext.getCmp('grid1');
	grid1.setHeight(clientHeight/2);
	var grid = Ext.getCmp('grid');
	grid.setHeight(clientHeight/4);
}
function saveA1701(){
	var a1701=document.getElementById("a1701ByHand").value;
	if(a1701){
		radow.doEvent("saveA1701",a1701);
	}else {
		odin.alert("�����������Ϣ��", "", "ϵͳ��ʾ");
	}
}
function bdjgFunc(){

	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
	var a0184 = document.getElementById("a0184").value;
	/*  $h.openWin('bdJianLi', "pages.publicServantManage.BdJianLi&bdJianLi="+Id+"&a0184="+a0184,name+"�ļ����ȶԽ��", 500, 470, Id, contextPath, null, {
         maximizable: false,
         resizable: false,
         closeAction: 'close',
         data: {khcjId:khcjId,a0000:a0000}
     }); */
 	$h.openPageModeWin('bdJianLi',"pages.publicServantManage.BdJianLi&bdJianLi="+Id+"&a0184="+a0184,name+"�ļ����ȶԽ��",950,560,Id,'<%=request.getContextPath() %>');

}
//����հ���
function insertEmptyRow(ds){
	var dstorecount = ds.getCount();
	for(var gi=0;gi<8-dstorecount;gi++){
		savecond();
	}
}

//����հ���
function savecond() {
	var grid = odin.ext.getCmp('grid1');
	var store = grid.getStore();
	var p = new Ext.data.Record({
		a1701: '',  
		a1702: '',  
		a1703: '',
		a1798: '1'
    });
	store.insert(store.getCount(), p);
}

//�����ʾ
function indexFormatter(value, params, record, rowIndex, colIndex, ds) {
  return rowIndex+1;
}

//����ʹ�ù�������getEditor���������ñ༭��ʵ���������ĳ���հ������øñ༭����ֱ�ӵ���UE.getEditor('editor')�����õ���ص�ʵ��
var ue = UE.getEditor('editor');

ue.addListener('blur',function(a,b,c){
	document.getElementById("a17").value = ue.getPlainTxt().trim();
});

var rowIndex = 0;
//���ڼ�¼��ǰ��������
function isHaveRow(obj){
	rowIndex = rowIndex + parseInt(obj);
}

//ȥ����С����ӵĿո�
function changeTrim(value,params,record,rowIndex,colIndex,ds){
	if(value==null||value==''||value=='null'){
		return "";
	}else{
		return value.trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
	}
}

//����
function caozuo(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid1').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='ɾ������' onclick=\"delrowFunc('"+a1700+"');\">";
}

//��������
function toA1701(obj){
	ue = UE.getEditor('editor');
	
	ue.ready(function () {
		var a1701 = obj;
		a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
		ue.setContent("<p>"+a1701+"</p>", false);
		ue.fireEvent("selectionchange");
	});
	document.getElementById("a17").value = a1701Format(obj);
	window.dialogArguments.window.setJIANLI(document.getElementById("a17").value)
	if(!obj){
		document.getElementById('tabJLInit').style.display='block';
		document.getElementById('tabJLup').style.display='none';
	}
}

//������ʽ��
function a1701Format(obj){
	var a1701 = obj.replace(/�ڼ�/g,'���');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	return newA1701;
}

//ƴ�Ӽ���
function composeFunc(){
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid1').store;
	
	//��ȡ������Ϣ������
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		odin.alert("����Ӽ�����Ϣ��", "", "ϵͳ��ʾ");
		return false;
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//��ȡ�жϼ��������ַ���
		if(a1703){
			if(isQiJian(a1703)){//��������ڼ�����û��������еģ��򲻽��д���
				continue;
			}
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		if(a1701 == '' && a1702 == '' && a1703 == ''){
			continue;
		}
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		//�����Ƴ��հ���
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	//������δ��ֹʱ��,У���Ƿ�����
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "ϵͳ��ʾ");
		return false;
	}

	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}

	changeToShow();
	radow.doEvent('compose');
}
function changeToShow(){
	odin.ext.getCmp('tab').activate('editup'); 
	odin.ext.getCmp('tab').activate('editshow');
}
function changeToUp(){
	odin.ext.getCmp('tab').activate('editshow');
	odin.ext.getCmp('tab').activate('editup');
}
//��������ָ����ݣ�����δͬ����A01
function saveFunc(){
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid1').store;
	
	//��ȡ������Ϣ������
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		odin.alert("����Ӽ�����Ϣ��", "", "ϵͳ��ʾ");
		return false;
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//��ȡ�жϼ��������ַ���
		if(isQiJian(a1703)){//��������ڼ�����û��������еģ��򲻽��д���
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//�����Ƴ��հ���
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//������δ��ֹʱ��,У���Ƿ�����
	var checkVar = checkVarFunc(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "ϵͳ��ʾ");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	
	radow.doEvent('save');
}

//������������� + �������������
function checkVarFunc(a1701Arr, a1702Arr, a1703Arr){
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//�������������
	for(var j = 0, n = a1702Arr.length; j < n; j++){
		msg = checkDate(a1701Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(a1702Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkJL(a1703Arr[j]);
		if(msg!=""){
			return msg;
		}
		if(a1701Arr[j] == ''){
			return "�������ڿ�ʼʱ��Ϊ�գ�";
		}
		else{
			if(a1702Arr[j] == '' && a1703Arr[j] == ''){
				return "�������ݴ���Ϊ�գ�";
			}else if(a1702Arr[j] != '' && a1703Arr[j] == ''){
				return a1701Arr[j] + "��" + a1702Arr[j] + "��������Ϊ�գ�";
			}else if(a1702Arr[j] == '' && a1703Arr[j] != ''){
				num = num + 1;
			}
		}
		if(j>0){
			if(a1701Arr[j]!=a1702Arr[j-1]){
				return "���ڼ���ʱ��"+a1701Arr[j]+"��������";
			}
		}
	}
	if(num > 1){
		return "������Ϣ���ڳ���1����¼�޽�ֹʱ�䣡";
	}
	
	//�������������
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '��' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "������Ϣ"+a1701Arr[i]+"��"+a1702Arr[i]+"��ʱ�䲻������";
							}
						}
					}
				}
			}
		}
	} */
	
	return msg;
}

function checkDate(evalue){
	var reg = new RegExp("^[0-9]{6}$");
	
	if(evalue){
		var value = evalue.trim();
		//if(value.length!=6) odin.alert("��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���","","ϵͳ��ʾ");
		if(!reg.test(value)) {
			return "��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���";
		}
		if(!/^[0-9]{6}$/.test(value)){
			return "��⵽���ڷ�6λʱ�����ݣ���ȷ������202001������������ٱ���";
		}
	}
	
	return "";
}
function checkJL(evalue){
	if(!evalue){
		return "��⵽���ڿհ׼����У�����������ٱ��棡";
	}
	return "";
}
//�б�༭�¼�
function cellEdit(e){
	//�޸Ĺ����д�0��ʼ  e.row;
	//�޸��� e.column;
	//ԭʼֵ e.originalValue;
	//�޸ĺ��ֵ e.value;
	//��ǰ�޸ĵ�grid e.grid;
	//���ڱ��༭���ֶ��� e.field;
	//���ڱ��༭���� e.record
	var grid = odin.ext.getCmp('grid1');
	var ecolumn = grid.getColumnModel().getDataIndex(e.column);
	var evalue = e.value;
	var reg = new RegExp("^[0-9]*$");
	if("a1701" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
			if(!reg.test(value)) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
			if(!/^[0-9]*$/.test(value)) odin.alert("������6λ��ʼʱ�䣡������202001","","ϵͳ��ʾ");
		}
	}
	if("a1702" == ecolumn){
		if(evalue != ''){
			var value = evalue.trim();
			if(value.length!=6) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
			if(!reg.test(value)) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
			if(!/^[0-9]*$/.test(value)) odin.alert("������6λ��ֹʱ�䣡","","ϵͳ��ʾ");
		}
	}
	if("a1703" == ecolumn){
		if(evalue == '') odin.alert("������������ݣ�","","ϵͳ��ʾ");
	}
}

//������ֳ���
function splitFunc(){
	var obj = document.getElementById("a17").value;
	if(obj == null || obj.trim().length < 1){
		odin.alert("���������ݣ�","","ϵͳ��ʾ");
		return false;
	}
	var a1701 = obj.replace(/�ڼ�/g,'���');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	
	/* ʵ�ֽ�ԭӦ��ʱһ��������޸�Ϊһ�� - ��ʼ , ʵ�ַ�ʽ�������У���������һ��������ƴ��Ϊһ�� */
	var a1701ArrayNew = new Array();//�м䴦������
	var a1701ArrayOld = new Array();//���������Ч����
	var a1701s="",a1701str="",indexNew=0;
	for(var index=a1701Array.length-1;index>=0;index--){
		a1701str = a1701Array[index].replace(" ","").replace("��","");
		a1701s = a1701str + a1701s;
		if(a1701str == '') continue;
		if(a1701str.indexOf("2")== 0||a1701str.indexOf("1")== 0||isQiJian(a1701str)){
			a1701ArrayNew[indexNew++]=a1701s;
			a1701s="";
			continue;
		}
	}
	/* ʵ�ֽ�ԭӦ��ʱһ��������޸�Ϊһ�� - ����  �ٴε��������޸�������*/
	for(var index=a1701ArrayNew.length-1,indexNew=0;index>=0;index--){
		var text = a1701ArrayNew[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701ArrayOld[indexNew++] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');//ƴ���µķ�ʽ
	}
	radow.doEvent('splitFunc', a1701ArrayOld.join("$$"));
}

//������
function addFunc(){
	
 	var hanghao = Ext.getCmp('grid1').getSelectionModel().lastActive;
	//alert(hanghao);
	if(!hanghao){
		hanghao=-1;
		}
	radow.addGridEmptyRow('grid1',(hanghao+1));
	try {
		Ext.getCmp('grid1').getSelectionModel().selectRow(hanghao+1);
		Ext.getCmp('grid1').getView().focusRow(hanghao+1);
	} catch (e) {
		console.log("�����쳣��");
	}
	rowIndex = rowIndex + 1; 
}

//ɾ����
function delrowFunc(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid1');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("��ʾ","�Ƿ�ȷ��ɾ��������������Ӧ��ְ������",200,function(e){
		if("ok" == e){
			rowIndex = rowIndex - 1;
			var grid = odin.ext.getCmp('grid1');
			var store = grid.store;
			var hanghao = grid.getSelectionModel().lastActive;
			store.remove(store.getAt(hanghao));
			radow.doEvent("deleteRow",a1700);
		}else{
			return;
		}
		});
	}
}


function initJLForOne(){
	var a0000 = document.getElementById('a0000').value;
	if(!a0000){
		odin.alert("����ѡ����Ա��");
		return;
	}
	ShowCellCover("start","��ܰ��ʾ��","���ڳ�ʼ��...");
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.NotePickUp&eventNames=initJLForOne&a0000="+a0000,
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				eval(cfg.elementsScript);
				Ext.Msg.alert("��ܰ��ʾ","������ֳɹ���");
				radow.doEvent("grid1.dogridquery");
				radow.doEvent("writeLead");
			}else{
				Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
					return;
				}
				Ext.Msg.alert("��ܰ��ʾ","��ʼ��ʧ�ܣ�");
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			odin.alert("�����쳣��");
		}  
	});
	changeToUp();
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

Ext.onReady(function(){
	showEditup();
});

function showEditup(){
	Ext.getCmp();
}

function a1705change(){
	var a1705=document.getElementById('a1705').value;
	var a1704=a1705.substring(0,2);
	document.getElementById('a1704').value=a1704;
	radow.doEvent('changeA1704name')
}

//ƴ�Ӽ���
function composeFunc1(){
	var a1700=document.getElementById('a1700').value;

	var za1701=document.getElementById('a1701').value;
	var za1703=document.getElementById('a1703').value;
	if(za1701==''||za1703==''){
		odin.info("��������ʼʱ�������Ϊ�գ�����ȷά������")
		return false;
		}
	var a1700Arr=new Array();
	var a1701Arr=new Array();
	var a1702Arr=new Array();
	var a1703Arr=new Array();
	var store = odin.ext.getCmp('grid').store;
	
	//��ȡ������Ϣ������
	var n = store.getCount();var a1700;var a1701;var a1702;var a1703;
	if(n == 0){
		radow.doEvent('compose');
	}
	var a1701="",a1702="",a1703="";
	for(var i = 0; i < n; i++){
		a1700 = store.getAt(i);
		a1703 = a1700.get("a1703");//��ȡ�жϼ��������ַ���
		if(isQiJian(a1703)){//��������ڼ�����û��������еģ��򲻽��д���
			continue;
		}
		a1701 = getObjStrOutNull(a1700.get("a1701"));
		a1702 = getObjStrOutNull(a1700.get("a1702"));
		a1703 = getObjStrOutNull(a1703);
		a1701Arr.push(a1701.trim());
		a1702Arr.push(a1702.trim());
		a1703Arr.push(a1703.trim());
		
		//�����Ƴ��հ���
		if(a1701Arr[i] == '' && a1702Arr[i] == '' && a1703Arr[i] == ''){
			a1700Arr.push(i);
		}
	}
	
	//������δ��ֹʱ��,У���Ƿ�����
	var checkVar = checkVarFunc1(a1701Arr, a1702Arr, a1703Arr);
	if(checkVar != ''){
		odin.alert(checkVar, "", "ϵͳ��ʾ");
		return false;
	}
	
	for(var j = a1700Arr.length - 1; j > -1; j--){
		store.remove(store.getAt(a1700Arr[j]));
	}
	radow.doEvent('compose1');
}
var ue1 = UE.getEditor('editor1');
function toA1701_1(obj){
	ue1.ready(function () {
		var a1701 = obj;
		a1701 = a1701.replace(/\r/g,'').replace(/\n/g,'</p><p>');
		ue1.setContent("<p>"+a1701+"</p>", false);
		//ue1.fireEvent("selectionchange");
	});
	document.getElementById("a1701z").value = a1701Format(obj);
}
//������ʽ��

function a1701Format(obj){
	var a1701 = obj.replace(/�ڼ�/g,'���');
	var a1701Array = a1701.replace(/\r/g,'').split('\n');
	for(var index=0;index<a1701Array.length;index++){
		var text = a1701Array[index].trim().replace(/^[\u3000\x20\xA0]{1,}/,'').replace(/[\u3000\x20\xA0]{1,}$/,'');
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}\s*[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--\s*/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}\s*[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text=text.replace(/\s*--/g,'--');
		}
		if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[0-9]{4}[\.\uff0e][0-9]{2}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020');
		}else if(text.match(/^[0-9]{4}[\.\uff0e][0-9]{2}[\-\u2500\u2014\uff0d]{1,}[\u3000\x20\xA0]{1,}/)){
			text = text.replace(/[\.\uff0e]/g,'.').replace(/[\-\u2500\u2014\uff0d]{1,}/,'--').replace(/[\u3000\x20\xA0]{1,}/,'\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020\u0020');
		}
		a1701Array[index] = text.replace(/[\u3000\x20\xA0]{18,}/g,'\n');
	}
	var newA1701='';
	for(var index=0;index<a1701Array.length;index++){
		newA1701 = newA1701 + a1701Array[index] + '\n';
	}
	return newA1701;
}
//������
function addFunc1(){
	var a1700=document.getElementById("a1700").value;
	if(a1700==null || a1700=='' ){
		$h.alert('','����ѡ�������Ŀ��')
		return;
		
	}
	var a1701=document.getElementById("a1701").value;
	var a1703=document.getElementById("a1703").value;
	if(a1701==null || a1701=='' || a1703==null || a1703==''){
		$h.alert('','������������Ŀ����Ϣ��')
		return;
		
	}
	
	var hanghao = Ext.getCmp('grid').getSelectionModel().lastActive;
	//alert(hanghao);
	if(!hanghao){
		hanghao=-1;
		}
	radow.addGridEmptyRow('grid',(hanghao+1));
	try {
		Ext.getCmp('grid').getSelectionModel().selectRow(hanghao+1);
		Ext.getCmp('grid').getView().focusRow(hanghao+1);
	} catch (e) {
		console.log("�����쳣��");
	}
	rowIndex = rowIndex + 1;
}
function caozuo1(value,params,record,rowIndex,colIndex,ds){
	var a1700 = Ext.getCmp('grid').getStore().getAt(rowIndex).get('a1700');
	return "<img style='cursor: pointer;' src='<%=request.getContextPath() %>/image/u109.png' title='ɾ������' onclick=\"delrowFunc1('"+a1700+"');\">";
}
//ɾ����ְ������
function delrowFunc1(a1700){
	if(""==a1700){
		rowIndex = rowIndex - 1;
		var grid = odin.ext.getCmp('grid');
		var store = grid.store;
		var hanghao = grid.getSelectionModel().lastActive;
		store.remove(store.getAt(hanghao));
		}
	else{
	$h.confirm("��ʾ","�Ƿ�ȷ��ɾ������������",200,function(e){
		if("ok" == e){
			radow.doEvent("deleteRow1",a1700);
		}else{
			return;
		}
		});
	}
}
//������������� + �������������
function checkVarFunc1(a1701Arr, a1702Arr, a1703Arr){
	var za1702=document.getElementById('a1702').value;
	var za1701=document.getElementById('a1701').value;
	var msg = "";var num = 0;var num2 = 0;var val;var val2;var a1703;
	//�������������
	for(var j = 0, n = a1702Arr.length; j < n; j++){
		msg = checkDate(a1701Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(a1702Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkJL(a1703Arr[j]);
		if(msg!=""){
			return msg;
		}
		msg = checkDate(za1702);
		if(msg!=""){
				return msg;
		}	
		if(a1701Arr[j]==""){
			//return "���ڼ�����ʼʱ��Ϊ�գ�";
		}else{
			if(a1703Arr[j]==""){
					return "���ڼ�������Ϊ�գ�";
			}
		}
		if(za1702){
			if((a1701Arr[j]!=''&&a1701Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]>za1702) || (a1702Arr[j]!=''&&a1702Arr[j]<za1701)){
				return "����䡯������ʱ��Ρ���������ʱ��Ӧ����ְ������ʼ����ʱ���ڣ���ѡ��ά������ȷ�������У���";
			}
		}
	}
	//�������������
	/* bbb: for(var i = 0, nn = a1702Arr.length; i < nn; i++){
		val = a1702Arr[i];
		a1703 = a1703Arr[i].trim();
		if(a1703 != ''){
			a1703 = a1703.substr(0, 1);
			if(a1703 != '(' && a1703 != '��' && val != ''){
				aaa: for(var j = 0, n = a1701Arr.length; j < n; j++){
					val2 = a1701Arr[j];
					if(val2 == ''){
						break aaa;
					}else{
						if(val2 == val){
							break aaa;
						}else{
							if(j + 1 == n && val2 != val){
								return "������Ϣ"+a1701Arr[i]+"��"+a1702Arr[i]+"��ʱ�䲻������";
							}
						}
					}
				}
			}
		}
	} */
	
	return msg;
}

</script>
