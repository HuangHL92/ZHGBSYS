<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/basejs/big-grid.js"></script>
<div id="panel_content">
<table id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
     <odin:textEdit property="worker0" label="条件1" required="true"/> 
	 <odin:textEdit property="name" label="条件2"/>
	 <odin:textEdit property="description" label="条件3"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>
	<tr>
		<td colspan="6" align="center" id="bgird1"></td>
	</tr>		 
 </table>
</div>
 
<script type="text/javascript">
<!--
odin.ext.onReady(function(){
	var config = {
		title:"人员信息表格",
		width:"96%",
		maxlength:8000,
		head:[
			{name:"name",title:"姓名",width:150,height:40,align:"center"},
			{name:"tt1",title:"信息1",width:150},
			{name:"tt2",title:"信息2",width:150},
			{name:"tt3",title:"信息3",width:150},
			{name:"tt4",title:"信息4",width:150},
			{name:"tt5",title:"信息5",width:150},
			{name:"tt6",title:"信息6",width:150}
		]
	};
	var data = [];
	for(i=0;i<10000;i++){
		data[i] = {
			name:"金"+i,
			tt1:"测试A"+i,
			tt2:"测试B"+i,
			tt3:"测试C"+i,
			tt4:"测试D"+i,
			tt5:"测试E"+i,
			tt6:"测试F"+i
		};
	}
	var bg = new bigGrid("bgird1",config,data);
	bg.show();
	
});
//-->
</script>



