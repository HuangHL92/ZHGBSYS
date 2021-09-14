<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<script type="text/javascript">

</script>
<odin:hidden property="dc001" title="类别id"/>
<odin:hidden property="rbId" title="批次id"/>
<odin:hidden property="a0000" title="人员id"/>
<odin:hidden property="js0100" title="任免人员id"/>
<odin:hidden property="dc005" title="分类标识"/>

<table style="width:100%;margin-left:80px;">
<tr>
<td style="width: 280px;">
<odin:groupBox property="group1"  title="类别信息">
<table>
<tr>				
<td>

		<table>
			<tr>
				<odin:textEdit property="t1" label="任免批次名称" colspan="3" maxlength="1" value=" " width="230"/>
			</tr>
			<tr>
				<%-- <odin:select2 property="dc005" label="类型"  maxlength="200" colspan="3" onchange="typechange" 
				data="['1','调配类型'],['2','谈话安排建议'],['3','分管市（单位）领导']"
					value="1"	width="230"/> --%>
				<odin:select2 property="dc005" label="类型"  maxlength="200" colspan="3" onchange="typechange" 
				data="['1','调配类型']"
					value="1"	width="230"/> 
			</tr>
			<tr>
				<odin:textEdit property="dc003" label="名称"  maxlength="200"  colspan="3" width="230"/> 
				<%-- <odin:select label="名称" property="dc003" data="['市级机关','市级机关'],['市（县）区','市（县）区'],['国有企业','国有企业']" width="'100%'" title="名称" colspan="1" value="市级机关"></odin:select> --%>
			</tr>
			<tr>
				<odin:textarea property="dc006" label="备注"  maxlength="1000" rows="6" colspan="3" cols="38"/>
			</tr>
			<tr>
				<td align="right"><odin:button text="清空" property="btn1" handler="tableadd"/></td>
				
				<td align="right"> <odin:button text="删除" property="btn2" handler="tabledelete"/></td>
				
				<td align="right"> <odin:button text="保存" property="btn3" handler="tablechange"/></td>
			</tr>
		</table>

</td>
</tr>
<tr>
<td>
	<odin:editgrid2 property="grid1" autoFill="true"
					height="390" width="100" url="/">
		<odin:gridJsonDataModel id="id" root="data">
			<odin:gridDataCol name="dc001" />
			<odin:gridDataCol name="rb_id" />
			<odin:gridDataCol name="rb_name" />
			<odin:gridDataCol name="dc003" />
			<odin:gridDataCol name="dc006" />
			<odin:gridDataCol name="dc004" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="分类ID" align="left" edited="false" width="100" dataIndex="dc001"
				editor="text" hidden="true"/>
				<odin:gridEditColumn header="任免批次ID" align="left" edited="false" width="100" dataIndex="rb_id"
				editor="text" hidden="true"/>
				<odin:gridEditColumn header="任免批次名称" align="left" edited="false" width="100" dataIndex="rb_name"
				editor="text" hidden="true"/>
				<odin:gridEditColumn2 header="名称"  align="left" edited="false" width="250" dataIndex="dc003"
				editor="text" menuDisabled="true" sortable="false"/>
			<odin:gridEditColumn header="排序" align="left" edited="false" width="100" dataIndex="dc004"
				editor="text" hidden="true" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid2>																
</td>
</tr>
</table>
				
				
</odin:groupBox>
</td>
<td style="width: 450px;">
<%-- <odin:groupBox property="group2"  title="分管市领导单位管辖情况">
<table style="width: 100%;">
  <tr>
    <odin:select2 property="selectLD" defaultValue="输入姓名..." width="200" canOutSelectList="true"/>
    <td style="padding-left: 20px"><odin:button text="添加到列表" property="addLD" handler="addLDas"/></td>
  </tr>
  <tr>
    <td><input name="selectdw_combo" id="selectdw_combo"/></td>
    <td style="padding-left: 20px"><odin:button text="添加到列表" property="addDW" handler="addDWas"/></td>
  </tr>
<tr><td colspan="2">
	<odin:editgrid2 property="ldqkgrid111" autoFill="true" grouping="true" groupCol="gdc003" 
	groupTextTpl="{text} ({[values.rs.length]} {[\"家单位\"]})"
					height="547" width="100" url="/">
		<odin:gridJsonDataModel id="id" root="data">
			<odin:gridDataCol name="jslddw000" />
			<odin:gridDataCol name="jsdw002" />
			
			<odin:gridDataCol name="gdc003" isLast="true" />
			
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
				
				<odin:gridEditColumn2 header="分管市(单位)领导" align="left" menuDisabled="true" edited="false"  width="30" dataIndex="gdc003"
				editor="select" editorId="gdc003" sortable="false"/>
				<odin:gridEditColumn2 header="分管单位" align="left" menuDisabled="true" edited="false" width="100" dataIndex="jsdw002"
				editor="text" sortable="false"/>
				<odin:gridEditColumn2 header="操作" align="center" menuDisabled="true" isLast="true" edited="false" width="30" dataIndex="jslddw000"
				editor="text" renderer="deleteDWrenderer" sortable="false"/>
		</odin:gridColumnModel>
	</odin:editgrid2>																
</td>				

</tr>
</table>
</odin:groupBox> --%>
</td>
</tr>
</table>




<odin:hidden property="gdc001" title="分管领导id"/>
<odin:hidden property="gdc003" title="分管领导姓名"/>
<odin:hidden property="jsdw002" title="单位名称"/>
<odin:hidden property="b0111" title="单位id 可为空"/>
<script type="text/javascript">
function addDWas(){//增加分管领导信息
	/* alert(document.getElementById("selectdw_combo").value)
	alert(document.getElementById("selectdw").value)
	alert(document.getElementById("selectLD_combo").value)
	alert(document.getElementById("selectLD").value) */
	
	var selectdw_combo = document.getElementById("selectdw_combo").value;
	var selectdw = document.getElementById("selectdw").value;
	var selectLD_combo = document.getElementById("selectLD_combo").value;
	var selectLD = document.getElementById("selectLD").value;
	if("输入姓名..."==selectLD_combo||""==selectLD_combo){
		$h.alert('系统提示','请输入姓名！');
		return;
	}
	if("输入单位名称..."==selectdw_combo||""==selectdw_combo){
		$h.alert('系统提示','请输入单位名称！');
		return;
	}
	if(selectLD==selectLD_combo){//新增领导
		document.getElementById("gdc001").value="";
		document.getElementById("gdc003").value=selectLD_combo;
	}else{
		document.getElementById("gdc001").value=selectLD;
		document.getElementById("gdc003").value=selectLD_combo;
	}
	if(selectdw==selectdw_combo){//单位添加
		document.getElementById("b0111").value="";
		document.getElementById("jsdw002").value=selectdw_combo;
	}else{
		document.getElementById("b0111").value=selectdw;
		document.getElementById("jsdw002").value=selectdw_combo;
	}
	radow.doEvent('saveLD');
}
function createComboBox(id,mode,store,emptyText){
	
	
	new Ext.form.ComboBox({
        allowBlank: true,
        hideLabel: true,
        width: 200,
        store: store,
        queryParam: 'query',
        emptyText: emptyText,
        valueField: 'key',
        displayField: 'value',
        editable: true,
        autoLoadStore: false,
        mode: mode,
        hideTrigger :true,
        typeAhead: true,
        loadingText: '正在加载...',
        minChars: 1,
        id:id+"_combo",
        hiddenId:id,
        hiddenName :id,
        forceSelection: false,
        applyTo:id+'_combo'
   });
}
Ext.onReady(function() {
	
	//$("#group2").hide();
	
	document.getElementById('rbId').value = parentParam.rb_id;		
	
	<%-- var comboStore = new Ext.data.JsonStore({
        url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.DeployClass&eventNames=jsondata",
        autoDestroy: true, 
        fields: new Ext.data.Record.create(['key', 'value']),
        root: "data",
        autoLoad: false
    });
	createComboBox('selectdw','remote',comboStore,'输入单位名称...');
	
	    
	$h.initGridSort('grid1',function(){
		radow.doEvent('btnsave.onclick');
	});
	$h.initGridSort('ldqkgrid111',function(g){
		g.store.sort('gdc003','ASC');
		radow.doEvent('ldqkgridsort');
	}); --%>
	
	
});
Ext.onReady(function() {

	
	
});
function tableadd(){//清空
	document.getElementById('dc003').value="";
	document.getElementById('dc001').value="";
	document.getElementById('dc006').value="";
	//radow.doEvent("tableadd");
}

function tablechange(){  //修改
	
	
	radow.doEvent("tablechange");
}

function tabledelete(){  //删除
	var dc001 = document.getElementById('dc001').value;
	if(dc001==null||dc001==""){
		alert("请选择一个类别！！！");		
		return ;
	}else{
		if(confirm("    确定删除吗？")){    			
			radow.doEvent('tabledelete');
			document.getElementById('dc001').value="";
		}else{
		   	return ;
		}
	   	
	}
	
}


window.onbeforeunload=function(){
	realParent.radow.doEvent('initX');
	
}
function typechange(){
	document.getElementById('dc003').value="";
	document.getElementById('dc001').value="";
	document.getElementById('dc006').value="";
	radow.doEvent('grid1.dogridquery');
}

function deleteDWrenderer(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"deleteDW('"+value+"');\">删除</a>"
	+ "</font></div>"
}
function deleteDW(v){  //删除
	if(v==null||v==""){
		alert("请选择一条记录！");		
		return ;
	}else{
		if(confirm("    确定删除吗？")){    			
			radow.doEvent('deleteDW',v);
		}else{
		   	return ;
		}
	   	
	}
	
}
</script>

