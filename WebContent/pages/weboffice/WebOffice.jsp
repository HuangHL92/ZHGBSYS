<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.WebOfficePageModel"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.siis.local.epsoft.config.AppConfig"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page isELIgnored="false" %>
<% 
UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
String cueUserid = user.getId();
%>
<%
   String urll = AppConfig.HZB_PATH+"/WebOffice";
   String webof= GlobalNames.sysConfig.get("PHOTO_PATH");
   webof=webof.replace("HZBPHOTOS", "WebOffice");
   CommonQueryBS.systemOut(webof);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Word表册定义</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>

<SCRIPT LANGUAGE=javascript>
var cueUserid = '<%=cueUserid%>';
Ext.onReady(function() {
	   document.all.WebOffice1.ShowToolBar=0;//隐藏控件自带的按钮1:显示0:隐藏
		var totalHeight = document.body.clientHeight;
		var totalWidth = document.body.clientWidth * 0.14;
		//newDoc();//初始化打开word
	    var tree = new Ext.tree.TreePanel({    
            region: 'center',
            el:'div1',
            collapsible: true,//True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条      
            title: '单元格属性',//标题文本    
            width: totalWidth, 
            height:totalHeight,
            border : true,//表框    
            autoScroll: true,//自动滚动条    
            containerScroll : true,
            animate : true,//动画效果    
            rootVisible: false,//根节点是否可见    
            split: true,    
            loader : new Ext.tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.WebOffice&eventNames=orgTreeJsonData'
          	})     
        }); 
	    tree.on('click',treeClick); //tree点击事件
		var root = new Ext.tree.AsyncTreeNode({
			checked : true,
			text : "其他单位",
			iconCls : "picOrg",
			draggable : true,
			id : "-1"
		});
		tree.setRootNode(root);
		tree.render();
		root.expand(false,true,callback);
		var callback = function (node){
			if(node.hasChildNodes()) {
				node.eachChild(function(child){
					child.expand();
				})
			}
		}
		
});

function haveCJ(){
	if(document.all.WebOffice1.object == null) {
		 Ext.Msg.confirm("系统提示","未检测到WebOffice1插件是否安装",function(ida) { 
				if("yes"==ida){
					radow.doEvent("expWeboffice",1);
				}else{
					return;;
				}		
			});	 
		}	
}





function treeClick(node,e){
	//var id = node.id.split(',')[0];
	var aa=node.parentNode.id; 
	var id = node.id;
	if(aa==-1){
		return;
	}
	//if(aa=="A01"&&(id=="A0288"||id=="A2949"||id=="JSNLSJ"||id=="TBSJ"||id=="XGSJ"||id=="A0107"||id=="A0134"||id=="A0154Q"||id=="S0145Q"||id=="S0146Q"||id=="A0140"||id=="A0144B"||id=="A0144C"||id=="A0148C"||id=="A0155"||id=="A0162"||id=="A0192F"||id=="A0144")){
	//去除了入党时间描述A0140
	if(aa=="A01"&&(id=="A0288"||id=="A2949"||id=="JSNLSJ"||id=="TBSJ"||id=="XGSJ"||id=="A0107"||id=="A0134"||id=="A0154Q"||id=="S0145Q"||id=="S0146Q"||id=="A0144B"||id=="A0144C"||id=="A0148C"||id=="A0155"||id=="A0162"||id=="A0192F"||id=="A0144")){	
		//Ext.MessageBox.alert('请选择日期格式','<select id="select"><option value ="">请选择</option><option value ="yyyy.mm">yyyy.mm</option> <option value ="yy-mm-dd">yyyy-mm-dd</option><option value ="yymmdd">yymmdd</option><option value ="yy.mm">yy.mm</option></select>'); 
		/* Ext.MessageBox.buttonText.yes="继续上传";  
		Ext.MessageBox.buttonText.no='hidden';  
		Ext.MessageBox.buttonText.cancel="取消"; */
		
		Ext.MessageBox.show({
		   title:' 请选择日期类型',  
		   msg: '<select id="select"><option value ="">请选择</option><option value ="yyyy.mm">yyyy.mm</option> <option value ="yy-mm-dd">yyyy-mm-dd</option><option value ="yymmdd">yymmdd</option><option value ="yy.mm">yy.mm</option></select> ',  
		   buttons: {ok:'确定',cancel:'取消'},  
		   fn: function(btn){
			var myselect=document.getElementById("select");
			var index=myselect.selectedIndex ;
			 option= myselect.options[index].value; 
			if(option==null||option==""){
				Ext.Msg.minWidth = 260; 
				Ext.Msg.alert('系统消息','请选择日期格式类型!',function(){}); 
			}else{
				Ext.Msg.minWidth = 260; 
				//Ext.MessageBox.alert("您选择了"+option+"日期类型格式");
				Ext.Msg.alert('系统消息','您选择了'+option+'日期类格式类型!',function(){}); 
					var id = node.id;
					id=aa+"_"+id+"#"+option;
					//alert(id);
					var name = node.text;
					addBookmark1(id);//加载事件
			}
		   }  
		}); 
			
		
	}else if(aa=="A01"){
		var id = node.id;
		id=aa+"_"+id;
		//alert(id);
		var name = node.text;
		addBookmark1(id);//加载事件
	}else{
		Ext.MessageBox.prompt('请输入区别符,以1-9区分', '<a>请选择日期格式</><select id="select"><option value ="">请选择</option><option value ="yyyy.mm">yyyy.mm</option> <option value ="yy-mm-dd">yyyy-mm-dd</option><option value ="yymmdd">yymmdd</option><option value ="yy.mm">yy.mm</option></select>', function(btn, text) {
		//Ext.MessageBox.prompt('请输入区别符,以1-9区分', '', function(btn, text) {  
			
			var myselect=document.getElementById("select");
			var index=myselect.selectedIndex ;
			var option= myselect.options[index].value; 
			//alert(option); 
			if(btn=="ok"){
				//var aa=node.parentNode.id;
				var id = node.id;
				if(option==null||option==""){
					if(text!=null||text!=""){
						id=aa+"_"+id+"#"+text;
					}
					
				 }else{
					 if(text!=null||text!=""){
					 id=aa+"_"+id+"@"+option+"#"+text;
					 Ext.Msg.minWidth = 260; 
					 Ext.Msg.alert('系统消息','您选择了'+option+'日期类格式类型!',function(){}); 

					 }
				} 
				var name = node.text;
				addBookmark1(id);//加载事件
			}
			  
			});
	}
	
	
}  

//获取路径
var url=window.location.protocol+"//"+window.location.host;
//-----------------------------== 保存文档 ==------------------------------------ //
function newSave() {
	//var  name=$('#FileName').val();
	if(docfilename!=""){
		newSaveAction(docfilename)
	}else{
		Ext.MessageBox.prompt('请保存文件名', '', function(btn, text) {
			if(btn=="ok"){
				newSaveAction(text)
			}
		});
	}
	
}
function newSaveAction(text){
    if (text == null || text == '') {//弹窗提示
    	alert("请输入文件名 ");
    }else{//保存文件
    	var myselect=document.getElementById("sel");
       if('<%=cueUserid%>'=="40288103556cc97701556d629135000f"){
    	    index=myselect.selectedIndex ;
       }
        var sel;
<%--     	 if('<%=cueUserid%>'=="40288103556cc97701556d629135000f"&&sel==0){ --%>
// 				//alert("请选择是否通用!");
// 				//return;
//     		 sel=1;
// 			}else{
// 				sel=0;
// 			}
			if('<%=cueUserid%>'!="40288103556cc97701556d629135000f"&&(sel==""||sel==null)){
				sel=0;
			}else if('<%=cueUserid%>'=="40288103556cc97701556d629135000f"&&(sel==""||sel==null)){
				sel=1;
			}else{
				sel=myselect.options[index].value;
			}
    	var returnValue;     //  保存页面的返回值
    	document.all.WebOffice1.HttpInit(); //  初始化 Http 引擎
    	document.all.WebOffice1.SetTrackRevisions(0);
    	document.all.WebOffice1.ShowRevisions(0);
    	//  添加相应的 Post 元素  
    	document.all.WebOffice1.HttpAddPostString("FileName", text);
    	//  添加上传文件
    	
    	 var name=document.getElementById("nametype").value;
    	 var nametype = encodeURI(encodeURI(name,'utf-8'));
    	 //alert(nametype);
    	 document.all.WebOffice1.HttpAddPostCurrFile("DocContent", ""); 	// 上传文件
    	 var filename=document.getElementById("filename").value;
    	//  提交上传文件
    	if(docfilename.indexOf("null")>=0)docfilename="";
    	returnValue = document.all.WebOffice1.HttpPost(url+"/hzb/servlet/SendtoServer?type="+document.getElementById("type").value+"&id="+docfilename+"&nametype="+nametype+"&sel="+sel+"&filename="+filename);
    	var fileext=returnValue.substring(returnValue.lastIndexOf("."),returnValue.length)
    	fileext=fileext.toLowerCase()  
    	if (returnValue!=""&&fileext==".doc") {
    		docfilename = returnValue;
    		Ext.Msg.alert("信息", "文件保存成功！");
    	} else if(returnValue!=""&&fileext!=".doc"){
    		Ext.Msg.alert("信息", "格式类型不正确,请选择doc格式！");
    	}else{
    		Ext.Msg.alert("信息", "保存失败!");
    	}
    }
}


function updatename(){//修改方案名称
	var id=document.getElementById("filename").value;
	if(id==""||id==null){
		alert("请选择修改的模板!")
	}else{
		
		Ext.MessageBox.prompt('请输入方案名称', '', function(btn, text) {
			if (text == null || text == '') {//弹窗提示
		    	alert("请输入文件名 ");
		    }else{
		    	text=id+","+text;
		    	radow.doEvent("updatename",text);
		    }
			});
	}
}


function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function cc(){
	
}
function reload(){
	odin.reset();
}
//---------------------=== 控件初始化WebOffice方法 ===---------------------- //
function WebOffice1_NotifyCtrlReady() {
		document.all.WebOffice1.LoadOriginalFile("", "doc");
}
// ---------------------=== 新建文档 ===---------------------- //
function newDoc() {
		 document.all.WebOffice1.LoadOriginalFile("", "doc");
		 docfilename="";
		 //$(".sel").val("0");//新建文档初始化select标签
}
// ---------------------=== 显示打印对话框 ===---------------------- //
function showPrintDialog(){
		document.all.WebOffice1.PrintDoc(1);
}
// ---------------------=== 直接打印 ===---------------------- //
function zhiPrint(){
		document.all.WebOffice1.PrintDoc(0);
}
// ---------------------== 关闭页面时调用此函数，关闭文件 ==---------------------- //
function window_onunload() {
	document.all.WebOffice1.Close();
}
 
// ---------------------------== 解除文档保护 ==---------------------------------- //
function UnProtect() {
	document.all.WebOffice1.ProtectDoc(0,1, document.all.docPwd.value);
}

// ---------------------------== 设置文档保护 ==---------------------------------- //
function ProtectFull() {
	document.all.WebOffice1.ProtectDoc(1,1, document.all.docPwd.value);
}
// ---------------------------== 禁止打印 ==---------------------------------- //
function notPrint() {
		document.all.WebOffice1.SetSecurity(0x01); 
}
// ---------------------------== 恢复允许打印 ==---------------------------------- //
function okPrint() {
		document.all.WebOffice1.SetSecurity(0x01 + 0x8000);

}
// ---------------------------== 禁止保存 ==---------------------------------- //
function notSave() {
		document.all.WebOffice1.SetSecurity(0x02); 

}
// ---------------------------== 恢复允许保存 ==---------------------------------- //
function okSave() {
		document.all.WebOffice1.SetSecurity(0x02 + 0x8000);

}
// ---------------------------== 禁止复制 ==---------------------------------- //
function notCopy() {
		document.all.WebOffice1.SetSecurity(0x04); 
}
// ---------------------------== 恢复允许复制 ==---------------------------------- //
function okCopy() {
		document.all.WebOffice1.SetSecurity(0x04 + 0x8000); 

}
// ---------------------------== 禁止拖动 ==---------------------------------- //
function notDrag() {
		document.all.WebOffice1.SetSecurity(0x08); 
}
// ---------------------------== 恢复拖动 ==---------------------------------- //
function okDrag() {
		document.all.WebOffice1.SetSecurity(0x08 + 0x8000); 

}
// -----------------------------== 修订文档 ==------------------------------------ //
function ProtectRevision() {
	document.all.WebOffice1.SetTrackRevisions(1) 
}

// -----------------------------== 隐藏修订 ==------------------------------------ //
function UnShowRevisions() {
	document.all.WebOffice1.ShowRevisions(0);
}

// --------------------------== 显示当前修订 ==---------------------------------- //
function ShowRevisions() {
	document.all.WebOffice1.ShowRevisions(1);

}

// -------------------------== 接受当前所有修订 ==------------------------------- //
function AcceptAllRevisions() {
 	document.all.WebOffice1.SetTrackRevisions(4);
}

// ---------------------------== 设置当前操作用户 ==------------------------------- //
function SetUserName() {
	if(document.all.UserName.value ==""){
		alert("用户名不可为空")
		document.all.UserName.focus();
		return false;
	}
 	document.all.WebOffice1.SetCurrUserName(document.all.UserName.value);
}

// -----------------------------== 返回首页 ==------------------------------------ //
function return_onclick() {
	document.all.WebOffice1.Close();
	window.location.href  = "index.jsp"
}

// 打开本地文件
function docOpen() {
	document.all.WebOffice1.LoadOriginalFile("open", "doc");
}

// -----------------------------== 另存为文档 ==------------------------------------ //

// -----------------------------== 隐藏菜单 ==------------------------------------ //
function notMenu() {
	document.all.WebOffice1.SetToolBarButton2("Menu Bar",1,8);
}
// -----------------------------== 显示菜单 ==------------------------------------ //
function okMenu() {
	document.all.WebOffice1.SetToolBarButton2("Menu Bar",1,11);
}
// -----------------------------== 隐藏常用工具栏 ==------------------------------------ //
function notOfter() {
	document.all.WebOffice1.SetToolBarButton2("Standard",1,8);
}
// -----------------------------== 显示常用工具栏 ==------------------------------------ //
function okOfter() {
	document.all.WebOffice1.SetToolBarButton2("Standard",1,11);
}
// -----------------------------== 隐藏格式工具栏 ==------------------------------------ //
function notFormat() {
	document.all.WebOffice1.SetToolBarButton2("Formatting",1,8);
}
// -----------------------------== 显示格式工具栏 ==------------------------------------ //
function okFormat() {
	document.all.WebOffice1.SetToolBarButton2("Formatting",1,11);
} 

function ShowToolBar_onclick()
{
    //通过Document->application->CommandBars 获取到菜单对象
	var vObj = document.all.WebOffice1.GetDocumentObject().Application.CommandBars("电子印章");
	vObj.Visible = !vObj.Visible
}

function AddSeal_onclick()
{
    var vObj = document.all.WebOffice1.GetDocumentObject().Application.CommandBars("电子印章");
	if(vObj) vObj.Controls("盖章").Execute();
}   
function view(){
    <%-- $h.openPageModeWin('WebOffice','pages.weboffice.ViewOffice','历史纪录',870,435,'','<%=request.getContextPath()%>');   --%>
    var iWidth = 550;     
    var iHeight = 400;        
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //获得窗口的垂直位置;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;   //获得窗口的水平位置;
    window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ViewOffice', '历史记录', 'height=' + iHeight + ',width=' + iWidth + ', top='+iTop+',left='+iLeft+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}
var docfilename = "";        
// -------------------------=== 设置书签 ===------------------------------ //
function  addBookmark1(id){
	var Applocation
	var obj = document.all.WebOffice1.GetDocumentObject();
	if(id=="A01_S0192AQ"){
		obj.Application.Selection.Fields.Add(obj.Application.Selection.Range,-1,"MERGEFIELD  image",true);//只针对照片
	}else{
		obj.Application.Selection.Fields.Add(obj.Application.Selection.Range,-1,"MERGEFIELD  "+id,true);
	}
	

}
// 文本监听事件
function OnPropChanged (event) {
	/* alert(event);
	var selfile=event.split(',');
	alert(selfile);
	var slety = event[0];
	var name = event[1]; */
	
    if (event.propertyName.toLowerCase () == "value") {
    	//docfilename = event.srcElement.value;
    	docfile = event.srcElement.value;
    	docfile1 = docfile.split(',');
    	docfilename = docfile1[1];
    	ChangeWord(event.srcElement.value);/* 回调新打开word函数 */
    }
}
// 打开历史纪录文件
var urll = '<%=webof%>';
function ChangeWord(RealPath){
	var selfile=RealPath.split(',');
	var slety = selfile[0];
	var name = selfile[1]; 
	 document.all.WebOffice1.LoadOriginalFile(url+"/hzb/servlet/ViewOffice?name="+name,"doc");
	//document.all.WebOffice1.LoadOriginalFile(url+"/hzb/webOffice/word/"+name, "doc");
	$(".sel").val(slety);
	//alert(url+"/hzb/servlet/ViewOffice");
	//document.all.WebOffice1.LoadOriginalFile(urll+name, "doc");
	
	 
}
</SCRIPT>
 
</head>
<body>
 <odin:hidden property="downfile" /> 
<form action="" id=""  enctype="multipart/form-data" method="post">
<input  type="hidden" id="filename" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"><!-- 文件名称 -->
 <div>
    <div  style="float:right;margin:0 0 0 -200px; width:86%;height:100%" >
        <div  style="margin:0 0 0 50px; background:#e4e4e4">
        	 <table width="100%"  border="0" cellpadding="0" cellspacing="0">
			  <tr style="margin-left:100px">
			  <td>
			  
				<input name="CreateFile" type="button" id="CreateFile" value="新建文档" onclick="newDoc()">
		        <input name="button" type="button" onclick="return docOpen()" value="打开本地文件" />
				<input name="CreateFile2" type="button" id="CreateFile2" value="保存" onclick="newSave()" />
				<input name="OpenHistory" type="button" id="OpenHistory" value="浏览方案" onclick="view()" />
				 <input name="button" type="button" onclick="updatename()" value="修改方案名称" />
				<input type="hidden" name="type" id="type" value="3">
				<input type="hidden" name="nametype" id="nametype" value="Word模板">
				<%if(cueUserid.equals("40288103556cc97701556d629135000f")){ %> 
				<select class="sel"name="sel" id="sel">
				       <option  value="1" selected="selected">通用</option>
				       <option  value="2">不通用</option>
				</select>
				<% }%>
				
				<!-- <input type="text" name="text" id="text" value=""> -->
      			<!-- -----------------------------== 装载weboffice控件 ==--------------------------------- -->
					<script src="<%=request.getContextPath() %>/pages/weboffice/LoadWebOffice.js"></script>    
      			<!-- --------------------------------== 结束装载控件 ==----------------------------------- -->  
 				</td>
			  </tr>
			 </table>
    	</div>
    </div> 
    <div style="float:left;width:200px;"  id="div1">
    </div>
</div> 
</form>
</body>
</html>