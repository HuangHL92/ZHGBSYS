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
       <odin:property name="1������" type="date" value="2007-12-10"></odin:property>
       <odin:property name="2���Ƿ����" type="bool" value="true"></odin:property>
       <odin:property name="3����Դ" value=""></odin:property>
       <odin:property name="4�����" value="1"></odin:property>
       <odin:property name="5������" value="Test" isLast="true"></odin:property>
    </odin:propertyData>
    <odin:propertySelectEditor>
       <odin:propertySelectOption html="����" value="1"></odin:propertySelectOption>
       <odin:propertySelectOption html="һ��" value="2" isLast="true"></odin:propertySelectOption>
    </odin:propertySelectEditor>
     <odin:propertySelectEditor id="type">
       <odin:propertySelectOption html="����" value="1"></odin:propertySelectOption>
       <odin:propertySelectOption html="һ��" value="2" isLast="true"></odin:propertySelectOption>
    </odin:propertySelectEditor>
    <odin:propertyEditors>
       <odin:propertyEditor propertyName="4�����" type="select" id="type"></odin:propertyEditor>
       <odin:propertyEditor propertyName="3����Դ" type="select" isLast="true"></odin:propertyEditor>
    </odin:propertyEditors>
</odin:propertyGrid>
<input type='button' value="close" onclick="parent.odin.ext.getCmp('window2').close()" />
<table width='100%' align='center'><tr><td><div id='gridDiv_myGrid' style='width:100%'></div></td><tr/></table>
<script>
Ext.onReady(function(){
    var bselect = Ext.DomHelper.append(document.body, {
        tag: 'select', cls: 'x-grid-editor x-hide-display', children: [
            {tag: 'option', value: '1', html: '����'},
            {tag: 'option', value: '2', html: 'һ��'}
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
        '1������': new Ext.grid.GridEditor(new Ext.form.DateField({format:'Y-m-d',selectOnFocus:true})),
        //'��Դ': new Ext.grid.GridEditor(bfield)
        '3����Դ':new Ext.grid.GridEditor( new Ext.form.ComboBox({
                  store: new Ext.data.SimpleStore({fields: ['key', 'value'],data:[['1', '��'],['2', 'Ů']]}),
                  displayField:'value',typeAhead: true,mode: 'local',triggerAction: 'all',editable:true,
                  selectOnFocus:true,allowBlank:true }))
     },   
     source: {
    "1������":new Date(Date.parseDate('2007-12-10','Y-m-d')),
    "2���Ƿ����":true,
    "3����Դ":'',
    "4������":"Test"
     }
     });
     var colModel = Ext.getCmp('myGrid').getColumnModel();
     //colModel.setRenderer(1,doGridSelectAcc);
     colModel.setConfig([
        {header: '����', width:50, sortable: true, dataIndex:'name', id: 'name'},
        {header: 'ֵ', width:50, resizable:false, renderer:doGridSelectAcc,dataIndex: 'value', id: 'value'}
    ]);
});
var property2_select = [['1', '��'],['2', 'Ů']];
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