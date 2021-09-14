<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page isELIgnored="false" %>


<script src="<%=request.getContextPath()%>/jqueryUpload/jquery-1.11.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>


<style type="text/css">
.pointer{
	cursor: pointer;
}

td{
	text-align:center;
	vertical-align:middle;
	font-family:宋体;
	font-weight:normal;
	background: #CAE8EA
}
</style>
<table style="width:100%">
<tr >
<td colspan="2"><div id="table1"  style="height:560px;overflow-y:scroll;"></div></td>
<td colspan="2"><div id="table2"  style="height:560px;overflow-y:scroll;"></div></td>
</tr>
<tr>
<td id="td1" align="center" colspan="2" style="width:50%"> 
	<table>
	<tr style="height: 50px">
  	<tags:JUpload2 property="file1" label="选择文件" fileTypeDesc="文件类型"  colspan="2"
  	uploadLimit="99" width="80" fileSizeLimit="20MB" fileTypeExts="*.xls;*.xlsx" labelTdcls="titleTd"/>
  	<td colspan="2" align="right" style="padding-right: 20px;">
		<odin:button text="上传" property="impBtn1" />
	</td>
	</tr>
	</table>
</td> 
<td id="td2" align="center" colspan="2" style="width:50%"> 
	<table>
	<tr style="height: 50px">
  	<tags:JUpload2 property="file2" label="选择文件" fileTypeDesc="文件类型"  colspan="2"
  	uploadLimit="99" width="80" fileSizeLimit="20MB" fileTypeExts="*.xls;*.xlsx" labelTdcls="titleTd"/>
  	<td colspan="2" align="right" style="padding-right: 20px;">
		<odin:button text="上传" property="impBtn2" />
	</td>
 	</tr>
	</table>
</td> 
</tr>
<tr >
<odin:textEdit property="excelselected1" label="Excel1选中列" size="100"  colspan="2"></odin:textEdit>
<odin:textEdit property="excelselected2" label="Excel2选中列" size="100"  colspan="2"></odin:textEdit>
</tr>
</table>
<div >
<div style="width:150px;float:left;" align="center"><odin:button text="选&nbsp;&nbsp;择" property="selectshow" handler="selectshow" /></div>
<div style="width:1350px;float:center;" align="center"><odin:button text="清空列" property="clear" handler="clear" /></div>
<div style="width:150px;float:right;" align="center"><odin:button text="比&nbsp;&nbsp;对" property="contrast" handler="contrast" /></div>
</div>

</body>

<odin:hidden property="tabledata1" title="Excel表1数据"/>
<odin:hidden property="tabledata2" title="Excel表2数据"/>
<odin:hidden property="selectindex1" title="Excel表1被选中列索引"/>
<odin:hidden property="selectindex2" title="Excel表2被选中列索引"/>
<odin:hidden property="path1" title="Excel表1文件路径"/>
<odin:hidden property="path2" title="Excel表2文件路径"/>
<odin:hidden property="flag" title="选中列终止标识符"/>

<odin:hidden property="selectcol1" title="Excel表1被选中列"/>
<odin:hidden property="selectcol2" title="Excel表2被选中列"/>
<odin:hidden property="upflag1" title="上传1标志点击标识"/>
<odin:hidden property="upflag2" title="上传2标志点击标识"/>
<script type="text/javascript">

Ext.onReady(function() {	
	document.getElementById("flag").value=0;
	document.getElementById('selectindex1').value=10000;
	document.getElementById('selectindex2').value=10000;
	document.getElementById('tabledata2').value="";
	document.getElementById('tabledata1').value="";
	document.getElementById('upflag1').value="";
	document.getElementById('upflag2').value="";
});
/* function formSubmit1(){
	//alert(document.getElementById('upload1').value);
	var path1=document.getElementById('upload1').value;
	path1=path1.replace(/\\/g,'/');
	var part = path1.split('/');
	path1="";
	$.each(part,function(i,partva){
		if(i<part.length-1)
			path1=path1+partva+"//";
		else 
			path1=path1+partva;
	});
	path1=path1.replace(/\//g,'\\');
	//alert(path1);
	document.getElementById('path1').value=path1;
	radow.doEvent('Excelshow01');
	//radow.doEvent('getdata01');
	
} */


function fillExcel1(){
	document.getElementById("table1").innerHTML=document.getElementById('tabledata1').value;
	//alert($('td','#table1 tr').length)
	if(document.getElementById('tabledata1').value!=""){
		$('td','#table1 tr').each(function (i, item) {
	        $(this).addClass('pointer');
	        $(this).on('click',function(){
	        	if(document.getElementById('flag').value==1){
	        		if(document.getElementById('selectindex1').value==10000)
		        		document.getElementById('selectindex1').value="";
		        	var selectindex1=document.getElementById('selectindex1').value;
		        	if(selectindex1.length!=0){
		        		var arr1=selectindex1.split(" ");
		        		if(arr1.indexOf(""+$(this).index())<0){
		        			document.getElementById('selectindex1').value=selectindex1+" "+$(this).index();
			        		if(document.getElementById('flag').value==1)
				        		clicktoselect();
		        		}
		        	}else{
		        		document.getElementById('selectindex1').value=$(this).index();
		        		if(document.getElementById('flag').value==1)
			        		clicktoselect();
		        	}
	        	}else
	        		Ext.Msg.alert("系统提示","请点击选择按钮开始选择！");
	        })
	    });
	}
}
function fillExcel2(){
	document.getElementById("table2").innerHTML=document.getElementById('tabledata2').value;
	if(document.getElementById('tabledata2').value!=""){
		$('td','#table2 tr').each(function (i, item) {
	        $(this).addClass('pointer');
	        $(this).on('click',function(){
	        	if(document.getElementById('flag').value==1){
	        		if(document.getElementById('selectindex2').value==10000)
		        		document.getElementById('selectindex2').value="";
		        	var selectindex2=document.getElementById('selectindex2').value;
		        	if(selectindex2.length!=0){
		        		var arr2=selectindex2.split(" ");
		        		if(arr2.indexOf(""+$(this).index())<0){
		        			document.getElementById('selectindex2').value=selectindex2+" "+$(this).index();
			        		if(document.getElementById('flag').value==1)
				        		clicktoselect();
		        		}
		        	}else{
		        		document.getElementById('selectindex2').value=$(this).index();
		        		if(document.getElementById('flag').value==1)
			        		clicktoselect();
		        	}
	        	}else
	        		Ext.Msg.alert("系统提示","请点击选择按钮开始选择！");
	        })
	    });
	}
}
function selectshow(){
	if(document.getElementById('tabledata2').value=="" || document.getElementById('tabledata1').value==""){
		Ext.Msg.alert("系统提示","请上传两个Excel!");
		//alert();
	}else{
		Ext.Msg.alert("系统提示","开始选择列!");
		//alert();
		document.getElementById('flag').value=1; 
	}
}
function clicktoselect(){
	var index1=10000;
	var index2=10000;
	if(document.getElementById('selectindex1').value!=10000){
			if(index1!=document.getElementById('selectindex1').value){
				index1=document.getElementById('selectindex1').value;
				var arr1=index1.split(" ");
				var value="";
				$.each(arr1,function(i,item){
					var colname=$('td',$('tr','#table1')[0])[parseInt(item)].innerHTML;
					value=value+colname+" ";
				});
				document.getElementById("excelselected1").value=value;
				document.getElementById("selectcol1").value=value;
			}
	}
	if(document.getElementById('selectindex2').value!=10000){
			if(index2!=document.getElementById('selectindex2').value){
				index2=document.getElementById('selectindex2').value;
				var arr2=index2.split(" ");
				var value="";
				$.each(arr2,function(i,item){
					var colname=$('td',$('tr','#table2')[0])[parseInt(item)].innerHTML;
					value=value+colname+" ";
				});
				document.getElementById("excelselected2").value=value;
				document.getElementById("selectcol2").value=value;
			}
	}
}
function contrast(){
	if(document.getElementById('tabledata2').value==""||document.getElementById('tabledata1').value==""){
		Ext.Msg.alert("系统提示","请上传两个Excel!");
		//alert();
		return;
	}
	if(document.getElementById('selectindex1').value==10000 || document.getElementById('selectindex2').value==10000 ){
		Ext.Msg.alert("系统提示","请点击选择按钮再点击数据以选择!");
		//alert();
		return;
	}
	var arr1=document.getElementById('selectindex1').value.split(" ");
	var arr2=document.getElementById('selectindex2').value.split(" ");
	if(arr1.length!=arr2.length){
		Ext.Msg.alert("系统提示","两个Excel选中列数需要一致");
		//alert();
		return;
	}
	
	if(document.getElementById('selectcol1').value!=document.getElementById('excelselected1').value ||
		document.getElementById('selectcol2').value!=document.getElementById('excelselected2').value){
		Ext.Msg.alert("系统提示","请清空列来修改比对条件");
		//alert();
		clear();
		return;
	}
		
	document.getElementById('flag').value=0;
	var data1y=$('#table1 tr').length;
	var data1x=$('td',$('tr','#table1')[0]).length;
	var myarr1 = getTabArray('#table1 table');
	var myarr2 = getTabArray('#table2 table');
	/* var myarr1=new Array();  
	for(var i=0;i<data1y;i++){  
	  	myarr1[i]=new Array();  
	  	for(var j=0;j<data1x;j++){  
	    myarr1[i][j]=null;  
	 	}
	}
	$('#table1 tr').each(function(i,item){
		$('td',item).each(function(j,itemj){
			myarr1[i][j]=itemj.innerHTML;
		});
	});
	for(var i=0;i<data1y;i++){  
	  	for(var j=0;j<data1x;j++){  
	  		if(myarr1[i][j]==null)
	  			myarr1[i][j]=myarr1[i-1][j];  
	 	}
	} */
	var myarrcop1=new Array();
	for(var i=0;i<data1y;i++){  
	  	myarrcop1[i]=new Array();  
	  	for(var j=0;j<data1x;j++){  
	    myarrcop1[i][j]=myarr1[i][j];  
	 	}
	}
	//console.log(myarr1);
	var data2y=$('#table2 tr').length;
	var data2x=$('td',$('tr','#table2')[0]).length;
	/* var myarr2=new Array();  
	for(var i=0;i<data2y;i++){  
	  	myarr2[i]=new Array();  
	  	for(var j=0;j<data2x;j++){  
	    myarr2[i][j]=null;  
	 	}
	}
	$('#table2 tr').each(function(i,item){
		$('td',item).each(function(j,itemj){
			myarr2[i][j]=itemj.innerHTML;
		});
	});
	for(var i=0;i<data2y;i++){  
	  	for(var j=0;j<data2x;j++){  
	  		if(myarr2[i][j]==null)
	  			myarr2[i][j]=myarr2[i-1][j];  
	 	}
	} */
	var myarrcop2=new Array();
	for(var i=0;i<data2y;i++){  
	  	myarrcop2[i]=new Array();  
	  	for(var j=0;j<data2x;j++){  
	    myarrcop2[i][j]=myarr2[i][j];  
	 	}
	}
	//console.log(myarr2);
	
	$.each(arr1,function(j,itemj){
		var colnamej=$('td',$('tr','#table1')[0])[parseInt(itemj)].innerHTML;
		$.each(arr2,function(i,item){
			var colnamei=$('td',$('tr','#table2')[0])[parseInt(item)].innerHTML;
			if(colnamej==colnamei){
				for(var i=0;i<data1y;i++){  
				  	for(var j=0;j<data2y;j++){  
				  		if(myarr1[i][parseInt(itemj)]==myarr2[j][parseInt(item)]){
				  			myarr1[i][parseInt(itemj)]=null;
				  			myarr2[j][parseInt(item)]=null;
				  		}
				  	}
				}
			}
		}); 
	});
	
	var htval1="<table width=\"100%\" style=\"border:1px solid #000;border-width:1px 0 0 1px;margin:2px 0 2px 0;border-collapse:collapse;\">";
	var myflag1=0;
	for(var i=0;i<data1y;i++){ 
		for(var j=0;j<data1x;j++){  
	  		if(myarr1[i][j]==null){
	  			myflag1++;
	  		}
	  	}
		if(myflag1!=arr1.length){
			htval1=htval1+"<tr height=\"22\" style=\"border:1px solid #000;border-width:0 1px 1px 0;margin:2px 0 2px 0;\">";
		  	for(var j=0;j<data1x;j++){  
		  		//if(myarrcop1[i][j]!=null)
		  			htval1=htval1+"<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0;font-family:楷体;font-weight:normal;text-align:left;vertical-align=text-top;\" align=\"center\" valign=\"center\" width=\"200\"  colspan=\"0\" rowspan=\"0\">"+myarrcop1[i][j]+"</td>";
		  	}
		  	htval1=htval1+"</tr>";
		}
		myflag1=0;
	}
	htval1=htval1+"</table>";
	console.log(htval1);
	document.getElementById('table1').innerHTML=htval1;
	
	var htval2="<table  width=\"100%\" style=\"border:1px solid #000;border-width:1px 0 0 1px;margin:2px 0 2px 0;border-collapse:collapse;\" >";
	var myflag2=0;
	for(var i=0;i<data2y;i++){  
		for(var j=0;j<data2x;j++){  
	  		if(myarr2[i][j]==null){
	  			myflag2++;
	  		}
	  	}
		if(myflag2!=arr2.length){
			htval2=htval2+"<tr height=\"22\" style=\"border:1px solid #000;border-width:0 1px 1px 0;margin:2px 0 2px 0;\" >";
		  	for(var j=0;j<data2x;j++){  
		  		//if(myarr2[i][j]!=null)
		  			htval2=htval2+"<td style=\"border:1px solid #000; border-width:0 1px 1px 0;margin:2px 0 2px 0;font-family:楷体;font-weight:normal;text-align:left;vertical-align=text-top;\" align=\"center\" valign=\"center\" width=\"200\"  colspan=\"0\" rowspan=\"0\" >"+myarrcop2[i][j]+"</td>";
		  	}
		  	htval2=htval2+"</tr>";
		}
		myflag2=0;
	}
	htval2=htval2+"</table>";
	document.getElementById('table2').innerHTML=htval2;
	
	clear();
}

function clear(){
	document.getElementById('selectindex1').value=10000;
	document.getElementById('selectindex2').value=10000;
	document.getElementById("excelselected1").value="";
	document.getElementById("excelselected2").value="";
}

 function onUploadSuccess(){//上传后
	if(document.getElementById('upflag1').value=='1'){
		radow.doEvent('Excelshow01');
		document.getElementById('upflag1').value="";
		//先清除原来的文件显示。
		$('#file1fileQueue').html('');
		
		var swfuploadify = window['uploadify_file1'];
		swfuploadify.queueData = {
				files              : {}, // The files in the queue
				filesSelected      : 0, // The number of files selected in the last select operation
				filesQueued        : 0, // The number of files added to the queue in the last select operation
				filesReplaced      : 0, // The number of files replaced in the last select operation
				filesCancelled     : 0, // The number of files that were cancelled instead of replaced
				filesErrored       : 0, // The number of files that caused error in the last select operation
				uploadsSuccessful  : 0, // The number of files that were successfully uploaded
				uploadsErrored     : 0, // The number of files that returned errors during upload
				averageSpeed       : 0, // The average speed of the uploads in KB
				queueLength        : 0, // The number of files in the queue
				queueSize          : 0, // The size in bytes of the entire queue
				uploadSize         : 0, // The size in bytes of the upload queue
				queueBytesUploaded : 0, // The size in bytes that have been uploaded for the current upload queue
				uploadQueue        : [], // The files currently to be uploaded
				errorMsg           : 'Some files were not added to the queue:'
			};
	}
		
	if(document.getElementById('upflag2').value=='1'){
		radow.doEvent('Excelshow02');
		document.getElementById('upflag2').value="";
		$('#file2fileQueue').html('');
		var swfuploadify = window['uploadify_file2'];
		swfuploadify.queueData = {
				files              : {}, // The files in the queue
				filesSelected      : 0, // The number of files selected in the last select operation
				filesQueued        : 0, // The number of files added to the queue in the last select operation
				filesReplaced      : 0, // The number of files replaced in the last select operation
				filesCancelled     : 0, // The number of files that were cancelled instead of replaced
				filesErrored       : 0, // The number of files that caused error in the last select operation
				uploadsSuccessful  : 0, // The number of files that were successfully uploaded
				uploadsErrored     : 0, // The number of files that returned errors during upload
				averageSpeed       : 0, // The average speed of the uploads in KB
				queueLength        : 0, // The number of files in the queue
				queueSize          : 0, // The size in bytes of the entire queue
				uploadSize         : 0, // The size in bytes of the upload queue
				queueBytesUploaded : 0, // The size in bytes that have been uploaded for the current upload queue
				uploadQueue        : [], // The files currently to be uploaded
				errorMsg           : 'Some files were not added to the queue:'
			};
	}
}
function afterDelete(fileID){//删除后
	alert(1);
	if(fileID=="1"){
		alert(2);
		document.getElementById("table1").innerHTML="";
	}
	if(fileID=="2"){
		document.getElementById("table2").innerHTML="";
	}	
}  


//获取table内容并转成二维数组（支持colspan和rowspan）
function getTabArray(tabId) {
    var data = [];
    if ($(tabId + ' tr').length == 0) {
        return data;
    }
    //填充数组
    $(tabId + ' tr').each(function () {
        var arr = [];
        $(this).children('th,td').each(function () {
            arr.push($(this).text().trim());
            if ($(this).attr('colspan')) {
                for (var i = 0, len = parseInt($(this).attr('colspan')) - 1; i < len; i++) {
                    arr.push('');
                }
            }
        });
        data.push(arr);
    });
    //二次填充
    var yIndex = 0;
    $(tabId + ' tr').each(function () {
        var xIndex = 0;
        $(this).children('th,td').each(function () {
            var t_yIndex = 0;
            if ($(this).attr('rowspan')) {
                t_yIndex = parseInt($(this).attr('rowspan'));
            }

            for (var i = 1, len = t_yIndex; i < len; i++) {
                var arr = data[yIndex + i];
                arr.splice(xIndex, 0, '');
            }

            if ($(this).attr('colspan')) {
                xIndex += parseInt($(this).attr('colspan'));
            } else {
                xIndex++;
            }
        });
        yIndex++;
    });
    return data;
}
//测试调用显示到console
function getCvsData() {
    var data = getTabArray('#table1 table');
    var cvsLines = '';
    for (var i = 0, len = data.length; i < len; i++) {
        cvsLines += data[i].join(',') + '\n';
    }

    //debug
    for (var i = 0, len = data.length; i < len; i++) {
        console.info(data[i].join(','));
    }
    return cvsLines;
}
</script>
