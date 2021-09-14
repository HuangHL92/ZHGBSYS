<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script type="text/javascript">
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
            el : 'tree-div',//Ŀ��div����
            autoScroll : true,
            rootVisible: false,
            split:false,
            id:"orgTree",
            width: 224,
            minSize: 224,
            maxSize: 224,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.resource.resource&eventNames=orgTreeJsonData'
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
  	//ҳ�����
  		document.getElementById("addResourceContent").style.width = document.body.clientWidth + "px";
  		//document.getElementById("addResourceContent").style.height = document.body.clientHeight*0.8 + "px";
  		document.getElementById("addResourcePanel").style.width = document.body.clientWidth-1 + "px";
}); 
</script>



<div id="addResourceContent">
<div id="addResourcePanel"></div>
<table height="100%">
	<tr>
		<td valign="top">
		<table height="100%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto;height: 600px; width: 250px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		<td valign="top">
			<table width="550px" style="height: 300px;">
				<tr>
					<odin:hidden property="parent"></odin:hidden>
					<odin:hidden property="functionid"></odin:hidden>
				</tr>
				<tr>
					<odin:textEdit property="parentname" label="����Դ����" disabled="true"></odin:textEdit>
					<odin:textEdit property="title" label="��Դ����" required="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="orderno" label="ͬ���˵����" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="location" label="��ԴURL" size="82"
						colspan="4"></odin:textEdit>
				</tr>
				<tr>
					<odin:select property="type" label="����" codeType="NTYPE" value="1"></odin:select>
					<odin:textEdit property="description" label="��Դ����"></odin:textEdit>
				</tr>
				<tr style="display: none;">
					<odin:select property="isproxy" label="�Ƿ�ʹ�ô���" codeType="YESNO"
						value="0"></odin:select>
					<odin:select property="appid" label="��ƷӦ��" codeType="PROXY"
						></odin:select>
				</tr>
				<tr>
					<odin:textEdit property="owner" label="��Դ������"></odin:textEdit>
					<odin:select property="active" label="״̬" codeType="USEFUL"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:select property="rpflag" label="�������" codeType="RPTYPE"
						value="1"></odin:select>
					<odin:select property="uptype" label="ģ�鹦�ܷ���" codeType="UPTYPE"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="param1" label="�Զ���ģ�����1"></odin:textEdit>
					<odin:textEdit property="param2" label="�Զ���ģ�����2"></odin:textEdit>
				</tr>
				<tr>
					<odin:select2 property="param3" data="['011','����'],['01','�ڲ�'],['0','����']" label="�ܼ���ʶ" />
					
				</tr>
				<tr style="display: none;">
					<odin:select property="log" label="�Ƿ��¼��־" codeType="YESNO"
						value="0"></odin:select>
						<odin:select property="rbflag" label="�Ƿ��������" codeType="YESNO"
						value="1"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:select property="publicflag" label="�Ƿ񹫹�����" codeType="YESNO"
							value="0"></odin:select>
					<odin:textEdit property="ywlx" maxlength="6" label="ҵ������"></odin:textEdit>		
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="cprate" maxlength="1" label="���Ƶȼ�"></odin:textEdit>
					<odin:select property="opctrl" label="����������" codeType="opctrl"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:numberEdit property="auflag" label="��˼���"></odin:numberEdit>
					<odin:select property="reauflag" label="�Ƿ����" codeType="YESNO"
						value="0"></odin:select>
				</tr>
				<tr style="display: none;">
					<odin:textEdit property="functioncode" maxlength="8" label="���ܱ��"></odin:textEdit>
					<odin:textEdit property="proc" maxlength="4" label="������"></odin:textEdit>
				</tr>
				<tr style="display: none;">
					<odin:textarea property="prsource" label="��־��Ϣ��Ҫ�ֶ�" rows="4"
						colspan="4" ></odin:textarea>
					<!--<odin:textEdit property="prsource" label="��־��Ϣ��Ҫ�ֶ�" size="82"
						colspan="4" ></odin:textEdit>-->
				</tr>
				<tr style="display: none;">
					<odin:textarea property="zyxx" label="ժҪ��Ϣ" rows="4"  ></odin:textarea>
					<odin:textEdit property="param4" label="�Զ���ģ�����4" ></odin:textEdit>
				</tr>	
				<tr>
					<td height="20"></td>
				</tr>					
			</table>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar" applyTo="addResourcePanel">
	<odin:textForToolBar text="<h3>��Դ����</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnNew" text="�½�" icon="images/i_2.gif"
		cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnDelete" text="ɾ��"
		icon="images/qinkong.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSave" isLast="true" text="����"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<!-- 
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>
 -->

