<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">

Ext.onReady(function() {
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {
	id:'group',
     el : 'tree-div',//目标div容器
     autoScroll : true,
     rootVisible: false,
     split:false,
     width: 324,
     height:490,
     minSize: 224,
     maxSize: 224,
     border:false,
     animate : true,
     enableDD : false,
     containerScroll : true,
     loader : new Tree.TreeLoader( {
           dataUrl : '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
     }),
     listeners: {
         'click': function(node, e) {
			document.getElementById("sysorgtext").value=node.text;
			document.getElementById("hiddenSysOrg").value=node.id;
         }
     }
 });
	var root = new Tree.AsyncTreeNode({
		checked : false,
		draggable : false,
		id :'-1'//默认的node值：?node=-100
	});
	tree.setRootNode(root);
	tree.render();
}); 

function impMysqlZip(){
	var sysorgid=document.getElementById("hiddenSysOrg").value;
	var filepathv=document.getElementById("filepath").value;
	var user=document.getElementById("user").value;
	var password=document.getElementById("password").value;
	var port=document.getElementById("port").value;
	var sid=document.getElementById("sid").value;

	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.MysqlImp&eventNames=impMysqlStart';
	ShowCellCover('start','系统提示','开始抽取mysql库数据','请您稍等...');
   	Ext.Ajax.request({
   		timeout: 50000000,
   		url: path,
   		async: true,
   		method :"post",
   		params : {
			'sysorgid':sysorgid,
			'filepathv':filepathv,
			'user':user,
			'password':password,
			'port':port,
			'sid':sid
   		},
        callback: function (options, success, response) {
        	if (success) {
        		var result = response.responseText;
 					if(result){
 						Ext.Msg.hide();
 						var json = eval('(' + result + ')');
 						var data_str=json.data;
 						var arr=data_str.split('@@@');
 						if(arr[0]==2){//成功
 							alert(arr[1]);
 						}else if(arr[0]==1){
 							alert(arr[1]);
 						}
 						Ext.Msg.hide();
 					}
      	   }
        }
   });
}
function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}
</script>
<table border="0">
	<tr height="9px">
		<td colspan=4"></td>
	</tr>
	<tr>
		<td height="425px" rowspan="2">
			<div id="tree-div" style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;"></div>
		</td>
		<td valign="top">
			<table border="0">
				<tr height="12px">
					<td colspan="4"></td>
				</tr>
				<tr >
					<odin:textEdit property="sysorgtext" label="所选机构" width="415" colspan="4" disabled="true"></odin:textEdit>
					<odin:hidden property="hiddenSysOrg"/>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<odin:textEdit width="416" colspan="4"  property="filepath" label="照片路径" value="D:/DATAS/Photos" ></odin:textEdit>
					<%-- <odin:textEdit property="filepath" label="文件路径"></odin:textEdit> --%>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<odin:textEdit property="user" colspan="4" width="415" label="用户名" value="gwydb"></odin:textEdit>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<odin:textEdit property="password" colspan="4" width="415" label="密码" value="gwydbpwd"></odin:textEdit>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<odin:textEdit property="port" colspan="4" width="415" label="端口号" value="7953"></odin:textEdit>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<odin:textEdit property="sid" colspan="4" width="415" label="实体名" value="gwybase"></odin:textEdit>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<td colspan=4"></td>
				</tr>
				<tr >
					<td colspan="4" style="font-size:12px;">
						<span style="color: red;">注：1.路径以反斜杠“/”作为分隔符（如：c：/）。
						 <br>&nbsp;&nbsp;&nbsp;&nbsp;2.所选择的机构及其以下的机构（包含机构信息与人员信息）会被全覆盖!</span>
					</td>
				</tr>
				<tr height="12px">
					<td colspan=4"></td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<odin:button text="开始接收" handler="impMysqlZip"></odin:button>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
</table>