<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>标签测试</title>
<style type="text/css">
<!--
.bluebackgroundcolor {
	background-color: #CCCC33;
}
-->
</style>
<odin:head />
</head>
<script>
function  doSetRowBgColor(){
    var view  = odin.ext.getCmp('grid2').getView();
    var rows = odin.ext.getCmp('grid2').store.getCount();
    for(i=0;i<rows;i++){
    	if(i%2!=0){
    		view.addRowClass(i,'bluebackgroundcolor');
    	}
    }
}
function renderCompany(value, params, record, rowIndex, colIndex, ds){
	return "<div style='width:100%;height:100%;background-color: #CCCC33;'>"+value+"</div>";
}
</script>
<body>
<odin:base>
<odin:toolBar property="myToolBar1">
  <odin:buttonForToolBar text="增加" iconCls="add"  icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="增加行数据"></odin:buttonForToolBar>
  <odin:buttonForToolBar text="修改行背景色" handler="doSetRowBgColor"></odin:buttonForToolBar>
  <odin:separator />
  <odin:textForToolBar text="测试"></odin:textForToolBar>
  <odin:fill></odin:fill>
  <odin:textForToolBar text="数据"></odin:textForToolBar>
  <odin:buttonForToolBar text="过滤数据" handler="doFilterData"></odin:buttonForToolBar>
  <odin:buttonForToolBar text="取消过滤" handler="doClearFilter"></odin:buttonForToolBar>
  <odin:separator/>
  <odin:buttonForToolBar isLast="true" text="增加" iconCls="add" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="增加行数据"></odin:buttonForToolBar>
</odin:toolBar>
<odin:grid property="grid2" filterFunc="doFilterPrice" bbarId="myToolBar1"  autoFill="false" title="我的表格" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridColumn  header="公司" renderer="renderCompany" width="160" dataIndex="company" />
  <odin:gridColumn  align="center" header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,0.02,0.03,'9/1 12:00am'],
        ['Alcoa Inc',29.01,0.42,1.47,'9/1 12:00am'],
        ['Altria Group Inc',83.81,0.28,0.34,'9/1 12:00am'],
        ['American Express Company',52.55,0.01,0.02,'9/1 12:00am'],
        ['American International Group, Inc.',64.13,0.31,0.49,'9/1 12:00am'],
        ['AT&T Inc.',31.61,-0.48,-1.54,'9/1 12:00am'],
        ['Boeing Co.',75.43,0.53,0.71,'9/1 12:00am'],
        ['Caterpillar Inc.',67.27,0.92,1.39,'9/1 12:00am'],
        ['Citigroup, Inc.',49.37,0.02,0.04,'9/1 12:00am'],
        ['E.I. du Pont de Nemours and Company',40.48,0.51,1.28,'9/1 12:00am'],
        ['Exxon Mobil Corp',68.1,-0.43,-0.64,'9/1 12:00am'],
        ['General Electric Company',34.14,-0.08,-0.23,'9/1 12:00am'],
        ['General Motors Corporation',30.27,1.09,3.74,'9/1 12:00am'],
        ['Hewlett-Packard Co.',36.53,-0.03,-0.08,'9/1 12:00am'],
        ['Honeywell Intl Inc',38.77,0.05,0.13,'9/1 12:00am'],
        ['Intel Corporation',19.88,0.31,1.58,'9/1 12:00am'],
        ['International Business Machines',81.41,0.44,0.54,'9/1 12:00am'],
        ['Johnson & Johnson',64.72,0.06,0.09,'9/1 12:00am'],
        ['JP Morgan & Chase & Co',45.73,0.07,0.15,'9/1 12:00am'],
        ['McDonald\'s Corporation',36.76,0.86,2.40,'9/1 12:00am'],
        ['Merck & Co., Inc.',40.96,0.41,1.01,'9/1 12:00am'],
        ['Microsoft Corporation',25.84,0.14,0.54,'9/1 12:00am'],
        ['Pfizer Inc',27.96,0.4,1.45,'9/1 12:00am'],
        ['The Coca-Cola Company',45.07,0.26,0.58,'9/1 12:00am'],
        ['The Home Depot, Inc.',34.64,0.35,1.02,'9/1 12:00am'],
        ['The Procter & Gamble Company',61.91,0.01,0.02,'9/1 12:00am'],
        ['United Technologies Corporation',63.26,0.55,0.88,'9/1 12:00am'],
        ['Verizon Communications',35.57,0.39,1.11,'9/1 12:00am'],
        ['Wal-Mart Stores, Inc.',45.45,0.73,1.63,'9/1 12:00am']
</odin:griddata>		
</odin:grid>

<odin:grid property="grid3" isFirstLoadData="true" filterFunc="doFilterPrice" url="/samples/tag/BasicGridData.jsp" autoFill="false" title="我的表格" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridColumn  header="公司" width="160" dataIndex="company" />
  <odin:gridColumn  align="center" header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" width="160"/>
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" width="160"/>
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" width="160"/>
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>		
</odin:grid>

<script>
function doFilterData(){
    odin.doFilterGridCueData(Ext.getCmp('grid2'),doFilterPrice);
}
function doClearFilter(){
    odin.doClearFilter(Ext.getCmp('grid2'));
}
function doFilterPrice(record,id){
    if(record.get('price')>=50){
        return true;
    }else{
        return false;
    }
}
</script>

</odin:base>  
</body>
</html>
