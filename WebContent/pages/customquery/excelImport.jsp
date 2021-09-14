<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	
	String ctxPath = request.getContextPath();
%>

<html style="background-color: rgb(223,232,246);">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />

<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/RowExpander.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/ExtJS/local/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/odin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<odin:head/>

<style>
font {
	font-family: "微软雅黑";
}
#start {
	position: relative;
	left: 90px;
	font-family: 微软雅黑;
}
#closeWin {
	position: relative;
	left: 180px;
	font-family: 微软雅黑;
}
</style>
</head>
<script type="text/javascript">
var path = "<%=request.getContextPath()%>";

var unid;
var tree;

Ext.onReady(function(){	
	
	Ext.override(Ext.tree.TreeNodeUI, {
		onDblClick : function(e) {
			e.preventDefault();
	        if(this.disabled){
	            return;
	        }
	        if(!this.animating && this.node.hasChildNodes() && !this.node.attributes.dblclick){
	            this.node.toggle();
	        }
	        if(this.node.disabled){//节点disabled 不促发下面事件
	            return;
	        }
	        if(this.checkbox){
	            //this.toggleCheck();
	        }
	       
			this.fireEvent("dblclick", this.node, e);
		}
	});
	
	var Tree = Ext.tree;
	 tree = new Tree.TreePanel( {
	  	  id:'group',
	        el : 'tree-div',//目标div容器
	        split:false,
	        height: 200,
	        minSize: 164,
	        maxSize: 164,
	        rootVisible: false,//是否显示最上级节点
	        autoScroll : true,
	        animate : true,
	        border:false,
	        enableDD : false,
	        containerScroll : true,
	        loader : new Tree.TreeLoader( {
	              dataUrl : '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
	        })
	 });
		var root = new Tree.AsyncTreeNode({
			checked : false,
			text : '<%=ereaname %>',
			iconCls : '<%=picType %>',
			draggable : false,
			id : '<%=ereaid %>',
			/* href : "javascript:radow.doEvent('querybyid','"
					+ parent.document.getElementById('ereaid').value + "')", */
			href : "javascript:void(0)"
		});
		tree.setRootNode(root);
		tree.render();
		tree.expandPath(root.getPath(),null,function(){addnode();});

});

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '其它现职人员', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001',
          leaf: false ,
          dblclick:"javascript:radow.doEvent('querybyid','X001')"
      });
      newnode.appendChild(new Ext.tree.TreeNode({ 
		  text: '职务为空的其它现职人员', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X0010',
          leaf: true ,
          dblclick:"javascript:radow.doEvent('querybyid','X0010')"
      }));
      nodeadd.appendChild(newnode);
}


function reloadTree() {
	var tree = Ext.getCmp("group");
	//获取选中的节点  
	var node = tree.getSelectionModel().getSelectedNode();  
	if(node == null) { //没有选中 重载树  
		tree.root.reload();
	} else {        //重载树 并默认选中上次选择的节点    
	    var path = node.getPath('id');  
	    tree.getLoader().load(tree.getRootNode(),  
	                function(treeNode) {  
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
	                                tree.getSelectionModel().select(oLastNode);  
	                            });  
	                }, this);    
	}  
}


/* 关闭 */
function gbWin(){
	var win = parent.Ext.getCmp('excelImport');
    if (win) {win.close();}
}

/* 开始导入 */
function startImport(){
	var bbidS = document.getElementById("bbids").value;//获取最终的bbids
	var unid = document.getElementById("unid").value;//获取最终的unid值
	if(!unid){
		alert("请先选择要导入的单位！");
		return;
	}
	if(!PERIOD){
		alert("请先选择报告期！");
		return;
	}
	if(!bbidS){
		bbidS = bbids;
	}
	
	var path = '<%=request.getContextPath()%>/ImportAction.do?method=startImp&bbids='+bbids+'&unid='+unid+'&PERIOD='+PERIOD+'&bbid='+bbid;
	
	ShowCellCover('start','系统提示','正在处理EXCEL文件,需要一定时间,请您稍等...');
	Ext.Ajax.request({
		url: path,
        form:'data',
        callback: function (options, success, response) {
			if (success) {
				var result = response.responseText;
				if(result){
					result = result.substring(5,result.length-6);
					var json = eval('(' + result + ')');
					var data = json.data;
					for (var key in data) {
						if('success'==key){
							//alert(data.success);
							ShowCellCover('success','系统提示',data.success);
							//setTimeout(gbWin,2000);//2s后,关闭页面
							
							//展开单位树并选中单位
							parent.expandTree1();
							
							var cellweb1 = parent.document.getElementById("DCellWeb1");
							var DBtable;
							if(bbid == 'J1603'){
								DBtable = 'M_M01';
							}
							if(bbid == 'J1604'){
								DBtable = 'M_Q01';
							}
							if(bbid == 'J1606'){
								DBtable = 'M_P01';
							}
							if(bbid == 'J1607'){
								DBtable = 'M_N01';
							}
							
							cellweb1.setCurSheet(0);//重新定位到当前页面
							parent.reloadcll(unid,"",bbid,"",DBtable,PERIOD);
							cellweb1.SaveEdit();
							
							gbWin();//关闭页面
						}
						if('error'==key){
							ShowCellCover('failure','错误提示',data.error);
						}
						if('wrongNum'==key){
							Ext.Msg.hide();
							var excelpath = data.path;
							
							var path = '<%=request.getContextPath()%>';
							var url = path + "/pages/impAndExp/wrong.jsp?wrongNum=" + data.wrongNum + "&path=" + excelpath;
							$h.showWindowWithSrc('wrong', url ,'错误提示',300,130);
							//数据格式不符合要求,共发现错误 1 处!,  点击进行打开或下载
						}
					}
				}
            } 
        }
   });
}


function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "关闭";
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
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
		        height:300,
		        modal:true,
				closable:true,
				//icon:Ext.MessageBox.INFO,  
				buttons: Ext.MessageBox.OK
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			, 2000);
			*/
					
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
</script>
<odin:hidden property="unid" value=""/>

<div style="width: 100%;height: 60%">
	<table style="width: 100%;">
		<tr style="background-color: rgb(208,223,240)">
			<td>
				<font style="font-size: 13px;color: red;">&nbsp①选择要导入的单位</font>
			</td>
		</tr>
		<tr>
			<td style="background-color: white;width: 100%;height: 200px;">
				<div id="tree-div"></div>
			</td>
		</tr>
	</table>
</div>
<div style="width: 100%;height: 40%">
	<table style="width: 100%;">
		<tr>
			<td>
				<font style="font-size: 13px;color: red;">&nbsp②选择上传要导入的文件</font>
			</td>
		</tr>
		<tr></tr>
		<tr></tr>
		<tr>
			<td>
				<font style="font-size: 13px;">&nbsp1: 导入的Excel文件的格式必须与填写模板一致,否则不能导入!</font>
			</td>
		</tr>
	</table>	
	<br>
	<form id="data" name="data" method="post" enctype="multipart/form-data">
		<div>
			<font style="font-size: 13px;">&nbsp&nbsp选择要导入的文件：</font>
			<input id="import" type="file" style="width: 380px;" value="浏览.." name="ww"/>
			<br><br>
			<input type="button" value="开始导入" id="start" onclick="startImport()"/>
			<input type="button" value="&nbsp&nbsp关&nbsp&nbsp闭&nbsp&nbsp" id="closeWin" onclick="gbWin()"/>
		</div>
	</form>
</div>

</html>

