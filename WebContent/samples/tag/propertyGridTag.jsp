<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>
<%
     CommonQueryBS.systemOut("test:"+request.getParameter("test"));
%>
<odin:propertyGrid property="grid1" width="500">
    <odin:propertyData>
       <odin:property name="1、日期" type="date" value="2007-12-10"></odin:property>
       <odin:property name="2、是否成立" type="bool" value="true"></odin:property>
       <odin:property name="3、来源" value=""></odin:property>
       <odin:property name="4、类别" value="1"></odin:property>
       <odin:property name="5、姓名" value="Test" isLast="true"></odin:property>
    </odin:propertyData>
    <odin:propertySelectEditor>
       <odin:propertySelectOption html="好人" value="1"></odin:propertySelectOption>
       <odin:propertySelectOption html="一生" value="2" isLast="true"></odin:propertySelectOption>
    </odin:propertySelectEditor>
     <odin:propertySelectEditor id="type">
       <odin:propertySelectOption html="好人" value="1"></odin:propertySelectOption>
       <odin:propertySelectOption html="一生" value="2" isLast="true"></odin:propertySelectOption>
    </odin:propertySelectEditor>
    <odin:propertyEditors>
       <odin:propertyEditor propertyName="4、类别" type="select" id="type"></odin:propertyEditor>
       <odin:propertyEditor propertyName="3、来源" type="select" isLast="true"></odin:propertyEditor>
    </odin:propertyEditors>
</odin:propertyGrid>
<input type='button' value="close" onclick="parent.odin.ext.getCmp('window2').close()" />
<table width='100%' align='center'><tr><td><div id='gridDiv_myGrid' style='width:100%'></div></td><tr/></table>
<script>
Ext.onReady(function(){
    var bselect = Ext.DomHelper.append(document.body, {
        tag: 'select', cls: 'x-grid-editor x-hide-display', children: [
            {tag: 'option', value: '1', html: '好人'},
            {tag: 'option', value: '2', html: '一生'}
        ]
    });
    var bfield = new Ext.form.Field({
        el:bselect,
        bselect :bselect,
        autoShow: true,
        getValue : function(){
        return this.bselect.value;
        }
    });
     var grid_myGrid = new Ext.grid.PropertyGrid({
     id:'myGrid',width: 500,
     height: 400,
     title:'',
     collapsible:true,
     renderTo:gridDiv_myGrid,
     customEditors: {
        '1、日期': new Ext.grid.GridEditor(new Ext.form.DateField({format:'Y-m-d',selectOnFocus:true})),
        //'来源': new Ext.grid.GridEditor(bfield)
        '3、来源':new Ext.grid.GridEditor( new Ext.form.ComboBox({
                  store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:[['1', '男'],['2', '女']]}),
                  displayField:'value',typeAhead: true,mode: 'local',triggerAction: 'all',editable:true,
                  selectOnFocus:true,allowBlank:true }))
     },   
     source: {
    "1、日期":new Date(Date.parseDate('2007-12-10','Y-m-d')),
    "2、是否成立":true,
    "3、来源":'',
    "4、姓名":"Test"
     }
     });
     var colModel = Ext.getCmp('myGrid').getColumnModel();
     //colModel.setRenderer(1,doGridSelectAcc);
     colModel.setConfig([
        {header: '名称', width:50, sortable: true, dataIndex:'name', id: 'name'},
        {header: '值', width:50, resizable:false, renderer:doGridSelectAcc,dataIndex: 'value', id: 'value'}
    ]);
});
var property2_select = [['1', '男'],['2', '女']];
function doGridSelectAcc(value, params, record,rowIndex,colIndex,ds){
    alert(rowIndex);
    if(value instanceof Date){
         value = renderDate(value);
     }else if(typeof value == 'boolean'){
         value = renderBool(value);
     }else if(eval("window.property"+rowIndex+"_select")){
         alert(1);
     }
     return Ext.util.Format.htmlEncode(value);
}
    renderDate = function(dateVal){
        return dateVal.dateFormat(dateFormat);
    }

    renderBool = function(bVal){
        return bVal ? 'true' : 'false';
    }
</script>
</body>
</html>