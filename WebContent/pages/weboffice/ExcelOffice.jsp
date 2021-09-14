<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.ExcelOfficePageModel"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.siis.local.epsoft.config.AppConfig"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>
<% 
UserVO user=PrivilegeManager.getInstance().getCueLoginUser();
String cueUserid = user.getId();
%>
<%
String urll = AppConfig.HZB_PATH+"/WebOffice"; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Excel��ᶨ��</title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>

<SCRIPT LANGUAGE=javascript>
Ext.onReady(function() {
	   document.all.WebOffice1.ShowToolBar=0;//���ؿؼ��Դ��İ�ť1:��ʾ0:����
		var totalHeight = document.body.clientHeight;
		var totalWidth = document.body.clientWidth * 0.14;
		//newDoc();//��ʼ����word
	    var tree = new Ext.tree.TreePanel({    
            region: 'center',
            el:'div1',
            collapsible: true,//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������      
            title: '��Ԫ������',//�����ı�    
            width: totalWidth, 
            height: totalHeight,
            border : true,//���    
            autoScroll: true,//�Զ�������    
            containerScroll : true,
            animate : true,//����Ч��    
            rootVisible: false,//���ڵ��Ƿ�ɼ�    
            split: true,    
            loader : new Ext.tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.WebOffice&eventNames=orgTreeJsonData'
          	})     
        }); 
	    tree.on('click',treeClick); //tree����¼�
		var root = new Ext.tree.AsyncTreeNode({
			checked : true,
			text : "������λ",
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
		 Ext.Msg.confirm("ϵͳ��ʾ","δ��⵽WebOffice1����Ƿ�װ",function(ida) { 
				if("yes"==ida){
					radow.doEvent("expWeboffice",1);
				}else{
					return;;
				}		
			});	 
		}	
}



function treeClick(node,e){
	var type = $("input[name='type']:checked").val();
	var aa = node.parentNode.id;//��ȡ���ڵ�id
	var id = node.id;
	if(aa==-1){
		return;
	}
	if(type==null||type==""){
		alert("��ѡ��������");
		return;
	};
	if(type==4){
		//if(aa=="A01"&&(id=="A0288"||id=="A2949"||id=="JSNLSJ"||id=="TBSJ"||id=="XGSJ"||id=="A0107"||id=="A0134"||id=="A0154Q"||id=="S0145Q"||id=="S0146Q"||id=="A0140"||id=="A0144B"||id=="A0144C"||id=="A0148C"||id=="A0155"||id=="A0162"||id=="A0192F"||id=="A0144")){
		//ȥ���뵳ʱ������A0140
		if(aa=="A01"&&(id=="A0288"||id=="A2949"||id=="JSNLSJ"||id=="TBSJ"||id=="XGSJ"||id=="A0107"||id=="A0134"||id=="A0154Q"||id=="S0145Q"||id=="S0146Q"||id=="A0144B"||id=="A0144C"||id=="A0148C"||id=="A0155"||id=="A0162"||id=="A0192F"||id=="A0144")){	
			Ext.MessageBox.show({  
				   title:' ��ѡ����������',  
				   msg: '<select id="select"><option value ="">��ѡ��</option><option value ="yyyy.mm">yyyy.mm</option> <option value ="yy-mm-dd">yyyy-mm-dd</option><option value ="yymmdd">yymmdd</option><option value ="yy.mm">yy.mm</option></select> ',  
				   buttons: {ok:'ȷ��',cancel:'ȡ��'},  
				   fn: function(btn){
					var myselect=document.getElementById("select");
					var index=myselect.selectedIndex ;
					 option= myselect.options[index].value; 
					if(option==null||option==""){
						Ext.Msg.minWidth = 260; 
						Ext.Msg.alert('ϵͳ��Ϣ','��ѡ�����ڸ�ʽ����!',function(){}); 
					}else{
						Ext.Msg.minWidth = 260; 
						Ext.Msg.alert('ϵͳ��Ϣ','��ѡ����'+option+'�������ʽ����!',function(){}); 
							 var id = node.id;
							 id="\\${"+aa+"_"+id+"#"+option+"}";
							addBookmark1(id);//�����¼�
					}
				   }  
				}); 
		}else if(aa=="A01"){
			 id= node.id;
			id="\\${"+aa+"_"+id+"}";
			addBookmark1(id);//�����¼� 
		}else{
		
			Ext.MessageBox.prompt('�����������,��1-9����', '<a>��ѡ�����ڸ�ʽ</><select id="select"><option value ="">��ѡ��</option><option value ="yyyy.mm">yyyy.mm</option> <option value ="yy-mm-dd">yyyy-mm-dd</option><option value ="yymmdd">yymmdd</option><option value ="yy.mm">yy.mm</option></select>', function(btn, text) {
				 var myselect=document.getElementById("select");
				 var index=myselect.selectedIndex ;
				 var option= myselect.options[index].value; 
				if(btn=="ok"){
					//var aa=node.parentNode.id; 
					var id= node.id;
					if(option==null||option==""){
						if(text!=null||text!=""){
							id= "\\${"+aa+"_"+id+"#"+text+"}";
							addBookmark1(id);//�����¼�
						}
						
					 }else{
						 if(text!=null||text!=""){
							 id= "\\${"+aa+"_"+id+"@"+option+"#"+text+"}";
							 Ext.Msg.minWidth = 260; 
							 Ext.Msg.alert('ϵͳ��Ϣ','��ѡ����'+option+'�������ʽ����!',function(){}); 
						     addBookmark1(id);//�����¼�
						 }
					} 
				}
				});
		}
		
	}else{
		id= node.id;
		id="\\${"+aa+"_"+id+"}";
		//alert(id);
		addBookmark1(id);//�����¼�
	}
}  

//��ȡ·��
var url=window.location.protocol+"//"+window.location.host;
//-----------------------------== �����ĵ� ==------------------------------------ //
function newSave() {
	var type=$('input:radio:checked').val();
	if(type==undefined){
		alert("��ѡ��Ҫ�������������!");
		return;
	}
	//var  name=$('#FileName').val();
	if(docfilename!=""){
		newSaveAction(docfilename)
	}else{
		Ext.MessageBox.prompt('�뱣���ļ���', '', function(btn, text) {
			if(btn=="ok"){
				newSaveAction(text)
			}
		});
	}
	
}

function newSaveAction(text){
    if (text == null || text == '') {//������ʾ
    	alert("�������ļ��� ");
    }else{//�����ļ�
    	
    	var myselect=document.getElementById("sel");
    	 if('<%=cueUserid%>'=="40288103556cc97701556d629135000f"){
     	    index=myselect.selectedIndex ;
        }
    	 var sel;
    	 if('<%=cueUserid%>'!="40288103556cc97701556d629135000f"&&(sel==""||sel==null)){
				sel=0;
			}else if('<%=cueUserid%>'=="40288103556cc97701556d629135000f"&&(sel==""||sel==null)){
				sel=1;
			}else{
				sel=myselect.options[index].value;
			}
    	var returnValue;     //  ����ҳ��ķ���ֵ
    	//var type=document.getElementById("type").value;//��λ����ͻ��������ʾ1��λ 2����
    	var type = $("input[name='type']:checked").val();
    	document.all.WebOffice1.HttpInit(); //  ��ʼ�� Http ����
    	document.all.WebOffice1.SetTrackRevisions(0);
    	document.all.WebOffice1.ShowRevisions(0);
    	//  �����Ӧ�� Post Ԫ��  
    	document.all.WebOffice1.HttpAddPostString("FileName", text);
    	//  ����ϴ��ļ�
    	if(type==1){//��λ
    		var name=document.getElementById("nametype1").value;
       	 var nametype = encodeURI(encodeURI(name,'utf-8'));
    	}else if(type==2){//����
    		var name=document.getElementById("nametype2").value;
          	 var nametype = encodeURI(encodeURI(name,'utf-8'));
    	}else{
    		var name=document.getElementById("nametype3").value;
         	 var nametype = encodeURI(encodeURI(name,'utf-8'));
    	}
    	
    	 document.all.WebOffice1.HttpAddPostCurrFile("DocContent", ""); 	// �ϴ��ļ�
    	//  �ύ�ϴ��ļ�
    	if(docfilename.indexOf("null")>=0)docfilename="";
    	returnValue = document.all.WebOffice1.HttpPost(url+"/hzb/servlet/SendtoServer?type="+type+"&id="+docfilename+"&nametype="+nametype+"&sel="+sel);
    	var fileext=returnValue.substring(returnValue.lastIndexOf("."),returnValue.length)
    	fileext=fileext.toLowerCase()
    	//alert(fileext);
    	if (returnValue!=""&&fileext==".xls") {
    		docfilename = returnValue;
    		Ext.Msg.alert("��Ϣ", "�ļ�����ɹ���");
    		//document.getElementById("div1").innerHTML=request.responseText;
    	} else{
    		Ext.Msg.alert("��Ϣ", "��ʽ���Ͳ���ȷ,��ѡ��xls��ʽ��");
    	}
    }
}

function updatename(){//�޸ķ�������
	var id=document.getElementById("filename").value;
	if(id==""||id==null){
		alert("��ѡ���޸ĵ�ģ��!")
	}else{
		
		Ext.MessageBox.prompt('�����뷽������', '', function(btn, text) {
			if (text == null || text == '') {//������ʾ
		    	alert("�������ļ��� ");
		    }else{
		    	text=id+","+text;
		    	//alert(text);
		    	radow.doEvent("updatename",text);
		    }
			});
	}
}

/* function newSave() {
	var type=$('input:radio:checked').val();
	if(type==undefined){
		alert("��ѡ��Ҫ�������������!");
		return;
	}
	Ext.MessageBox.prompt('�뱣���ļ���', '', function(btn, text) {
		if(btn=="ok"){
		    if (text == null || text == '') {//������ʾ
		    	alert("�������ļ��� ");
		    }else{//�����ļ�
		    	var returnValue;     //  ����ҳ��ķ���ֵ
		    	document.all.WebOffice1.HttpInit(); //  ��ʼ�� Http ����
		    	document.all.WebOffice1.SetTrackRevisions(0);
		    	document.all.WebOffice1.ShowRevisions(0);
		    	//  �����Ӧ�� Post Ԫ��  
		    	 document.all.WebOffice1.HttpAddPostString("FileName", text);
		    	//  ����ϴ��ļ�
		    	 document.all.WebOffice1.HttpAddPostCurrFile("DocContent", ""); 	// �ϴ��ļ�
		    	 //var type=document.getElementById("type").value;
		    	//  �ύ�ϴ��ļ�
		    	returnValue = document.all.WebOffice1.HttpPost(url+"/hzb/servlet/SendtoServer?type="+type);
		    	if ("true" == returnValue) {
		    		Ext.Msg.alert("��Ϣ", "�ļ�����ɹ���");
		    	} else {
		    		Ext.Msg.alert("��Ϣ", "δ������ʧ�ܣ�");
		    	} 
		    }
		}
	});
} */




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
function reload(){
	odin.reset();
}


//---------------------=== �ؼ���ʼ��WebOffice���� ===---------------------- //
function WebOffice1_NotifyCtrlReady() {
		document.all.WebOffice1.LoadOriginalFile("", "xls");
}
// ---------------------=== �½��ĵ� ===---------------------- //
function newDoc() {
		 document.all.WebOffice1.LoadOriginalFile("", "xls");
		 docfilename="";
		 $('input:radio[name="type"]').removeAttr('checked');//����ģ��remove��������ѡ�еı������	
		// $(".sel").val("0");//�½��ĵ���ʼ��select��ǩ
		 
}
// ---------------------=== ��ʾ��ӡ�Ի��� ===---------------------- //
function showPrintDialog(){
		document.all.WebOffice1.PrintDoc(1);
}
// ---------------------=== ֱ�Ӵ�ӡ ===---------------------- //
function zhiPrint(){
		document.all.WebOffice1.PrintDoc(0);
}
// ---------------------== �ر�ҳ��ʱ���ô˺������ر��ļ� ==---------------------- //
function window_onunload() {
	document.all.WebOffice1.Close();
}
 
// ---------------------------== ����ĵ����� ==---------------------------------- //
function UnProtect() {
	document.all.WebOffice1.ProtectDoc(0,1, document.all.docPwd.value);
}

// ---------------------------== �����ĵ����� ==---------------------------------- //
function ProtectFull() {
	document.all.WebOffice1.ProtectDoc(1,1, document.all.docPwd.value);
}
// ---------------------------== ��ֹ��ӡ ==---------------------------------- //
function notPrint() {
		document.all.WebOffice1.SetSecurity(0x01); 
}
// ---------------------------== �ָ������ӡ ==---------------------------------- //
function okPrint() {
		document.all.WebOffice1.SetSecurity(0x01 + 0x8000);

}
// ---------------------------== ��ֹ���� ==---------------------------------- //
function notSave() {
		document.all.WebOffice1.SetSecurity(0x02); 

}
// ---------------------------== �ָ������� ==---------------------------------- //
function okSave() {
		document.all.WebOffice1.SetSecurity(0x02 + 0x8000);

}
// ---------------------------== ��ֹ���� ==---------------------------------- //
function notCopy() {
		document.all.WebOffice1.SetSecurity(0x04); 
}
// ---------------------------== �ָ������� ==---------------------------------- //
function okCopy() {
		document.all.WebOffice1.SetSecurity(0x04 + 0x8000); 

}
// ---------------------------== ��ֹ�϶� ==---------------------------------- //
function notDrag() {
		document.all.WebOffice1.SetSecurity(0x08); 
}
// ---------------------------== �ָ��϶� ==---------------------------------- //
function okDrag() {
		document.all.WebOffice1.SetSecurity(0x08 + 0x8000); 

}
// -----------------------------== �޶��ĵ� ==------------------------------------ //
function ProtectRevision() {
	document.all.WebOffice1.SetTrackRevisions(1) 
}

// -----------------------------== �����޶� ==------------------------------------ //
function UnShowRevisions() {
	document.all.WebOffice1.ShowRevisions(0);
}

// --------------------------== ��ʾ��ǰ�޶� ==---------------------------------- //
function ShowRevisions() {
	document.all.WebOffice1.ShowRevisions(1);

}

// -------------------------== ���ܵ�ǰ�����޶� ==------------------------------- //
function AcceptAllRevisions() {
 	document.all.WebOffice1.SetTrackRevisions(4);
}

// ---------------------------== ���õ�ǰ�����û� ==------------------------------- //
function SetUserName() {
	if(document.all.UserName.value ==""){
		alert("�û�������Ϊ��")
		document.all.UserName.focus();
		return false;
	}
 	document.all.WebOffice1.SetCurrUserName(document.all.UserName.value);
}

// -----------------------------== ������ҳ ==------------------------------------ //
function return_onclick() {
	document.all.WebOffice1.Close();
	window.location.href  = "index.jsp"
}

// �򿪱����ļ�
function docOpen() {
	document.all.WebOffice1.LoadOriginalFile("open", "xls");
}

// -----------------------------== ���Ϊ�ĵ� ==------------------------------------ //

// -----------------------------== ���ز˵� ==------------------------------------ //
function notMenu() {
	document.all.WebOffice1.SetToolBarButton2("Menu Bar",1,8);
}
// -----------------------------== ��ʾ�˵� ==------------------------------------ //
function okMenu() {
	document.all.WebOffice1.SetToolBarButton2("Menu Bar",1,11);
}
// -----------------------------== ���س��ù����� ==------------------------------------ //
function notOfter() {
	document.all.WebOffice1.SetToolBarButton2("Standard",1,8);
}
// -----------------------------== ��ʾ���ù����� ==------------------------------------ //
function okOfter() {
	document.all.WebOffice1.SetToolBarButton2("Standard",1,11);
}
// -----------------------------== ���ظ�ʽ������ ==------------------------------------ //
function notFormat() {
	document.all.WebOffice1.SetToolBarButton2("Formatting",1,8);
}
// -----------------------------== ��ʾ��ʽ������ ==------------------------------------ //
function okFormat() {
	document.all.WebOffice1.SetToolBarButton2("Formatting",1,11);
} 

function ShowToolBar_onclick()
{
    //ͨ��Document->application->CommandBars ��ȡ���˵�����
	var vObj = document.all.WebOffice1.GetDocumentObject().Application.CommandBars("����ӡ��");
	vObj.Visible = !vObj.Visible
}

function AddSeal_onclick()
{
    var vObj = document.all.WebOffice1.GetDocumentObject().Application.CommandBars("����ӡ��");
	if(vObj) vObj.Controls("����").Execute();
}   
function view(){
    <%-- $h.openPageModeWin('WebOffice','pages.weboffice.ViewOffice','��ʷ��¼',870,435,'','<%=request.getContextPath()%>');   --%>
    var iWidth = 550;     
    var iHeight = 400;        
    var iTop = (window.screen.availHeight-30-iHeight)/2;       //��ô��ڵĴ�ֱλ��;
    var iLeft = (window.screen.availWidth-10-iWidth)/2;   //��ô��ڵ�ˮƽλ��;
    window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelViewOffice', '��ʷ��¼', 'height=' + iHeight + ',width=' + iWidth + ',top='+iTop+',left='+iLeft+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes');
}
var docfilename = "";      
// -------------------------=== ������ǩ ===------------------------------ //
function  addBookmark1(id){
	//��ȡ���x���y��
	var column=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.column;
	var Row=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.Row;
	if(id=="\\${A01_S0192AQ}"){
		document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).cells(Row,column).value="image";//��ֵ	
	}else{
		document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).cells(Row,column).value=id;//��ֵ	
	}
	

}

function contentstart(){
	var column=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.column;
	var Row=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.Row;
	document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).cells(Row,column).value="contentstart";//��ͷ	
}
function contentend(){
	var column=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.column;
	var Row=document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).Application.ActiveCell.Row;
	document.all.WebOffice1.GetDocumentObject().Application.Worksheets(1).cells(Row,column).value="contentend";//��β
} 
// �ı������¼�
function OnPropChanged (event) {
	 if (event.propertyName.toLowerCase () == "value") {
	    	//docfilename = event.srcElement.value;
	    	
	    	
	    	docfile = event.srcElement.value;
	    	docfile1 = docfile.split(',');
	    	docfilename = docfile1[1];
	    	//alert(docfilename);
	    	
	    	/* 
	    	docfile = event.srcElement.value;
	    	var doc = docfile.split(",");
	    	var docname = doc[1];
	    	var type = doc[0]; */
	    	//ChangeWord(docname,type);/* �ص��´�word���� */
	    	ChangeWord(event.srcElement.value);/* �ص��´�word���� */
	    }
}
// ����ʷ��¼�ļ�
var urll = '<%=urll%>';
function ChangeWord(RealPath){
	
	var selfile=RealPath.split(',');
	var type = selfile[0];
	var name = selfile[1]; 
	var slety = selfile[2];
	docfilename =name;
	//document.all.WebOffice1.LoadOriginalFile(url+"/hzb/webOffice/word/"+docname, "xls");
	 document.all.WebOffice1.LoadOriginalFile(url+"/hzb/servlet/ViewOffice?name="+name,"xls");
	 $(".sel").val(slety);
	//document.all.WebOffice1.LoadOriginalFile(urll+"/"+docname, "xls");
	$("input[type=radio][name='type'][value='"+type+"']").attr("checked",'checked');//����radio�������
}
</SCRIPT>
</head>
<body>
 <odin:hidden property="downfile" /> 
<form action="" id=""  enctype="multipart/form-data" method="post">
<input  type="hidden" id="filename" oninput="OnInput (event)" onpropertychange="OnPropChanged (event)"><!-- �ļ����� -->
 <div>
    <div  style="float:right;margin:0 0 0 -200px; width:86%;height:100%" >
        <div  style="margin:0 0 0 50px; background:#e4e4e4">
        	 <table width="100%"  border="0" cellpadding="0" cellspacing="0">
			  <tr style="margin-left:100px">
			  <td>
				<!-- ---------------=== �ô��ļ���ʽ��value�������Զ��� ===------------------------- -->
				<!-- <select name="doctype" size="1" id="doctype">
		          <option value="doc">Word</option>
		          <option value="xls">Excel</option>
		        </select> -->
				<input name="CreateFile" type="button" id="CreateFile" value="�½��ĵ�" onclick="newDoc()">
				  <input name="button" type="button" onclick="return docOpen()" value="�򿪱����ļ�" />
				<input name="CreateFile2" type="button" id="CreateFile2" value="����" onclick="newSave()" />
				<input name="OpenHistory" type="button" id="OpenHistory" value="�������" onclick="view()" />
				<input name="button" type="button" onclick="updatename()" value="�޸ķ�������" />
				<label> <input type="radio" name="type" id="type1" value='1'/>��λ����</label>  
	            <label> <input name="type" id="type2" type="radio" value="2"/>��������</label>  
	            <label> <input name="type" id="type3" type="radio" value="4"/>Excel���</label>  
	            <input name="biaot" type="button" id="biaot" value="��ͷ" onclick="contentstart()" />
	            <input name="biaow" type="button" id="biaow" value="��β" onclick="contentend()" />
	            <input type="hidden" name="nametype1" id="nametype1" value="��λ����">
	            <input type="hidden" name="nametype2" id="nametype2" value="��������">
	            <input type="hidden" name="nametype3" id="nametype3" value="Excel���">
	            <%if(cueUserid.equals("40288103556cc97701556d629135000f")){ %> 
	            <select class="sel"name="sel" id="sel">
				       <option  value="1" selected="selected">ͨ��</option>
				       <option  value="2">��ͨ��</option>
				</select>
	           <% }%>
      			<!-- -----------------------------== װ��weboffice�ؼ� ==--------------------------------- -->
					<script src="<%=request.getContextPath() %>/pages/weboffice/LoadWebOffice.js"></script>    
      			<!-- --------------------------------== ����װ�ؿؼ� ==----------------------------------- -->  
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