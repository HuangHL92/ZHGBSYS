<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/basejs/big-grid.js"></script>
<div id="panel_content">
<table id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
     <odin:textEdit property="worker0" label="����1" required="true"/> 
	 <odin:textEdit property="name" label="����2"/>
	 <odin:textEdit property="description" label="����3"/>
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
		title:"��Ա��Ϣ���",
		width:"96%",
		maxlength:8000,
		head:[
			{name:"name",title:"����",width:150,height:40,align:"center"},
			{name:"tt1",title:"��Ϣ1",width:150},
			{name:"tt2",title:"��Ϣ2",width:150},
			{name:"tt3",title:"��Ϣ3",width:150},
			{name:"tt4",title:"��Ϣ4",width:150},
			{name:"tt5",title:"��Ϣ5",width:150},
			{name:"tt6",title:"��Ϣ6",width:150}
		]
	};
	var data = [];
	for(i=0;i<10000;i++){
		data[i] = {
			name:"��"+i,
			tt1:"����A"+i,
			tt2:"����B"+i,
			tt3:"����C"+i,
			tt4:"����D"+i,
			tt5:"����E"+i,
			tt6:"����F"+i
		};
	}
	var bg = new bigGrid("bgird1",config,data);
	bg.show();
	
});
//-->
</script>



