<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%
String count = GlobalNames.sysConfig.get("YYHEADCOUNT");
%>

<script type="text/javascript">
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
            el : 'tree-div',//Ŀ��div����
            autoScroll : true,
            rootVisible: false,
            split:false,
            width: 224,
            minSize: 224,
            maxSize: 224,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.resource&eventNames=orgTreeJsonData'
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : 'ҵ��˵���',
            draggable : false,
            id : 'null',//Ĭ�ϵ�nodeֵ��?node=-100
            href:"javascript:void(0)"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();

});
function doSave(btn){
	var msg = "";
	if(radow.$("parentid").value == "" && (radow.$("parentid_combo").value == "" || radow.$("parentid_combo").value == "����ѡ��...") && radow.$("functionid").value == ""){
		msg = "<font color=red>����������һ�������˵��ڵ㣬</font>";
	}
	odin.confirm(msg+"��ȷ��Ҫִ��"+btn.getText()+"������",function(rtn){
		if(rtn=='ok'){
			radow.doEvent("saveMenu");
		}
	});
}
function createRootNode(){
	radow.$("parentid").value = "";
	radow.$("parentid_combo").value = "";
	radow.$("functionid").value = "";
	radow.doEvent("btnNew.onclick");
}
var count = 24;
function showYyHeadDiv(){
	var html = "";
	for(var i=0;i<count;i++){
		var iid = i+1;
		html += "<lable><img src='images/func/"+iid+".png' id='"+iid+"' onclick='checkHead(this);' style='cursor:hand'/></lable>";
	}
	document.getElementById("showYyHeadDiv").innerHTML=html;
	document.getElementById('showYyHeadDiv').style.display="block";
	//$('#showYyHeadDiv').html(html);
	//��ȡѡ����λ��
	//var p = $("#yyhead");
	//var yyhead = p.offset();
	//$('#showYyHeadDiv').css({top:yyhead.top+15,left:yyhead.left}).fadeIn('slow');
}


//���ͼ��� ��ֵ��ѡ���
function checkHead(is){
	var id = is.id;
	var funcpic = id;
	document.getElementById("funcpic").value=funcpic;
	document.getElementById('showYyHeadDiv').style.display="none";
}
</script>



<div id="addResourceContent">
<table height="480">
	<tr>
		<td valign="top">
		<table height="100%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		
		<td valign="top">
			<table width="550px">
				<tr>
					<odin:select property="parentid" isPageSelect="true" label="���˵�" pageSize="20" tpl="keyvalue" minChars="2"  canOutSelectList="true" codeType="FUNCTIONID"></odin:select>
					<odin:textEdit property="functionid" label="�˵�ID" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="functioncode" label="�˵����" required="true" maxlength="9" title="ǰ��λ�����븸�˵��Ĳ˵����һ�£�������ҳ����ӹ��ܣ�����Ϊ��00000000����"></odin:textEdit>
					<odin:numberEdit property="orderno" label="�˵����" required="true" maxlength="4"></odin:numberEdit>
				</tr>
				<tr>
					<odin:textEdit property="title" label="�˵�����" required="true"></odin:textEdit>
					<odin:select property="type" label="�˵�����" codeType="CDTYPE" tpl="keyvalue" value="1" required="true"></odin:select>
				</tr>
				<tr>
					<odin:textEdit property="description" label="��������" required="true" colspan="4" size="81"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="location" label="�˵�URL" size="81" colspan="4"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="ywlx" label="ҵ������" required="true" maxlength="6" title="��Ϊ��ѯ����ҵ�����͵Ĺ��ܣ�������Ϊ��000000��"></odin:textEdit>
					<odin:textEdit property="bdyy" label="�䶯ԭ��" required="true" maxlength="8" title="��û�л��ж���䶯ԭ��������Ϊ��00000000��"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="zyxx" label="ժҪ��Ϣ" size="81" colspan="4" title="����Ҫ����Ĺ��ܣ�������Ϊ��{}�������������д��"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="param1" label="�Զ���ģ�����1"></odin:textEdit>
					<odin:textEdit property="param2" label="�Զ���ģ�����2"></odin:textEdit>
				</tr>
				<tr>
					<td>
						<div style="display: none;z-index: 3;position: absolute; background: #F8F8FF;width: 320px;height: 250px;overflow: auto;"  id="showYyHeadDiv"></div>
					</td>
				</tr>
				<tr>
					<odin:textEdit property="param3" label="�Զ���ģ�����3"></odin:textEdit>
					<odin:textEdit property="param4" label="�Զ���ģ�����4"></odin:textEdit>
				</tr>
				<tr>					
					<odin:textEdit property="proc" maxlength="4" label="������" value="P0" required="true"></odin:textEdit>					
					<odin:select property="auflag" label="�Զ����" required="true" tpl="keyvalue" codeType="CDAUFLAG" value="1"></odin:select>				
				</tr>				
				<tr>
					<odin:select property="opctrl" label="����������" tpl="keyvalue" required="true"></odin:select>
					<odin:textEdit property="owner" label="������Ա"></odin:textEdit>
				</tr>
				<tr>
					<odin:select property="draftflag" label="�ݸ����޸�" tpl="keyvalue" required="true" value="1"></odin:select>
					<odin:select property="sysid" label="����ϵͳ" tpl="keyvalue" required="true" ></odin:select>
				</tr>								
				<tr>
					<odin:select property="cprate" label="���Ƶȼ�" codeType="cprate" tpl="keyvalue"></odin:select>
					<odin:select property="active" label="״̬" codeType="USEFUL" value="1" required="true" tpl="keyvalue"></odin:select>
					
				</tr>
				<tr>
					<odin:select property="isproxy" label="�Ƿ�ʹ�ô���" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
					<odin:select property="appid" label="��ƷӦ��" codeType="PROXY" tpl="keyvalue"></odin:select>
				</tr>
				<tr>
					<odin:select property="rpflag" label="�������" codeType="RPTYPE" value="1" tpl="keyvalue"></odin:select>
					<odin:select property="publicflag" label="�Ƿ񹫹�����" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
				</tr>
				<tr>
					<odin:select property="log" label="�Ƿ��¼��־" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>
					<odin:select property="rbflag" label="������־���˱�־" codeType="YESNO" value="1" tpl="keyvalue"></odin:select>
					
				</tr>
				<tr>
					<odin:select property="reauflag" label="�Ƿ����" codeType="YESNO" value="0" tpl="keyvalue"></odin:select>					
					<odin:textEdit property="funcpic" label="ϵͳͼ��" onclick="showYyHeadDiv();" readonly="true"></odin:textEdit>							
					
				</tr>		
		
			</table>
			
		</td>
		
	</tr>
</table>

</div>

<odin:hidden property="optype" value="1"/>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>��Դ����</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="�½����ڵ�" icon="images/i_2.gif" handler="createRootNode"
		cls="x-btn-text-icon" />
	<odin:buttonForToolBar id="btnNew" text="�½�" icon="images/i_2.gif"
		cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnDelete" text="ɾ��"
		icon="images/qinkong.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSave" isLast="true" text="����" handler="doSave"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent"  property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


