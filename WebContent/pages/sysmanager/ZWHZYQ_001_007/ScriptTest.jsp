<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/json2.js"></script>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1;charset=GBK">
<title>脚本测试</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/uuid.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/base64.js"></script>

<odin:hidden property="nodeId"/>
<odin:hidden property="interface_script_id"/>
<odin:hidden property="interface_config_id"/>
<odin:hidden property="QUERY_PARAM"/>

<input type="button" id="execute" style="display:none"/>
<odin:toolBar property="floatToolBar">
	<odin:textForToolBar text=""/> 
	<odin:fill/>
	<odin:buttonForToolBar text="应用" id="save" icon="images/icon/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="关闭" id="close" icon="images/icon/close.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="editDIV" property="mypanel" topBarId="floatToolBar" height="90"></odin:panel>
<div id="editDIV">
<table  style="margin:5px" align="left" border="0" cellpadding="5px" cellspacing="0" >
	<tr>
		<td>
			<odin:textEdit property="interfaceconfigidname" label="数据访问接口方案" width="350" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td>
			<odin:textEdit property="interfacescriptidname" label="数据访问接口脚本" width="350" disabled="true"/>
		</td>
		<td>
			<span width="500"></span>
		</td>
	</tr>
</table>
</div>
<table  style="margin:5px" border="0" cellpadding="5px" cellspacing="0" >
	<tr>
	    <td>
			<odin:textEdit property="tabName" label="返回数据集" width="100" disabled="true"/>
		</td>
		<td width="15px">
			<odin:textEdit property="tableMessage" label="返回表信息" width="200" disabled="true"/>
		</td>
	</tr>
</table>
<!-- tab页面开始 -->
	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top: 8px;">
		<tr>
			<td align="left" valign="top">
				<odin:tab id="tab" width="725">
					<odin:tabModel>
						<odin:tabItem title="接口参数" id="tab1"/>
						<odin:tabItem title="返回结果" id="tab2"/>
						<odin:tabItem title="执行日志" id="tab3" isLast="true"/>
					</odin:tabModel>
					<odin:tabCont itemIndex="tab1" className="">
						<div id="gridDiv_list"></div>
						<odin:editgrid property="list"  sm="row" url="/"  title="" height="200"  applyTo="gridDiv_list">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="interface_parameter_sequence"/>
								<odin:gridDataCol name="interface_parameter_name"/>
								<odin:gridDataCol name="interface_parameter_desc" />
								<odin:gridDataCol name="interface_parameter_type" />
								<odin:gridDataCol name="paramValue" isLast="true" />
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridEditColumn header="序号" dataIndex="interface_parameter_sequence" edited="false" editor="text" width="35" align="center"/>
								<odin:gridEditColumn header="参数编码" dataIndex="interface_parameter_name" edited="false" editor="text" width="200" align="center"/>
								<odin:gridEditColumn header="参数名称" dataIndex="interface_parameter_desc" edited="false" editor="text" width="280" align="center" />
								<odin:gridEditColumn header="参数类型" dataIndex="interface_parameter_type" hidden="true" editor="text"/>
								<odin:gridEditColumn header="参数值" dataIndex="paramValue"  edited="true"  editor="text"  width="200" align="center" isLast="true" />
							</odin:gridColumnModel>
						</odin:editgrid> 	
					</odin:tabCont>
					<odin:tabCont itemIndex="tab2" className="">
						<div id="dyngrid_list1" style="height:200px;"></div>
					</odin:tabCont>
					<odin:tabCont itemIndex="tab3" className="">
						<textarea name="remessage" cols="110" rows="13"></textarea>
					</odin:tabCont>
				</odin:tab>
			</td>
		</tr>
	</table>
<!-- tab页面结束 -->
<script type="text/javascript">
Ext.onReady(function(){

Ext.get('execute').on('click',function(){
	Ext.getCmp('tab').activate(Ext.getCmp('tab').getItem('tab2'));
	document.getElementById("dyngrid_list1").innerHTML="";
	var interface_script_id = document.all.interface_script_id.value;
	var interface_config_id = document.all.interface_config_id.value;
	Ext.Ajax.request({
 	    url:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_007.ScriptTest&eventNames=doAction&interface_script_id='+interface_script_id+'&interface_config_id='+interface_config_id,
		success:function(response){
          json = Ext.util.JSON.decode(response.responseText);
          document.all.remessage.value = json.message;
          document.all.tabName.value = json.tabName;
          document.all.tableMessage.value = json.tableMessage;
          var cm = new Ext.grid.ColumnModel(json.columnModel);
         
		  var store = new Ext.data.JsonStore({
		      data:json.data,
		     
		 	  fields:json.fieldsNames
		  });
		  var sm = new Ext.grid.CheckboxSelectionModel({handleMouseDown:Ext.emptyFn}); 
 		  var grid = new Ext.grid.EditorGridPanel({
                title:'',                     	//表格标题
                region: 'center',				//布局
                id:'list1',						//grid的id
                border:'true',
                selModel:sm,
                autoScroll : true,
                stripeRows:true,
	            split: true,
                renderTo: 'dyngrid_list1',		//指定存放区域
                height:200,
                //width:595,
                bodyStyle:"width:100%",
                cm:cm,							//列模型
                store:store
            });
          },
		 failure:function(){
     		 alert("error");
         }
	});
})
});
</script>





