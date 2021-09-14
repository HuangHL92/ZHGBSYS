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
<odin:head />
</head>

<body>
<odin:base>
<script>
function doRDbClick(grid,rowIndex,e)
{
     //Ext.getCmp('window').show(Ext.getCmp('window'));
     Ext.getCmp('grid5').store.remove(Ext.getCmp('grid5').store.getAt(rowIndex));
     Ext.getCmp('grid5').getSelectionModel().getSelections(); 
}
</script>
<odin:window id="window" src="/samples/tag/propertyGridTag.jsp"></odin:window>
<odin:gridWithPagingTool topToolBar="true" autoFill="false" rowDbClick="doRDbClick" forceNoScroll="false" url="/samples/tag/pageJsonData.jsp" property="grid5" pageSize="15" title="分页表格" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="公司" width="160" dataIndex="company" />
  <odin:gridColumn renderer="odin.changeToUrl" header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>		
</odin:gridWithPagingTool>
</odin:base>  
<input type="button" value="setUrl" onclick="odin.loadPageGridWithQueryParams('grid5',{test:encodeURIComponent('您好')})"/>
<script>
function doSetUrlAndLoadData()
{
    var store = Ext.getCmp('grid5').getStore();
    alert(store);
    alert(store.proxy);
    alert(store.url);
    
    store.on('beforeload', function(ds) {    
		ds.baseParams = {    
			test: 'test111'  
		};    
    });     
    store.reload();
    //store.url = '/insiis/samples/tag/pageJsonData1.jsp?test=test2';
    
   /*
    var ds = new Ext.data.Store({
		url: '/insiis/samples/tag/pageJsonData.jsp?test=test2',
		reader: store.reader,
		remoteSort: false
		});

    Ext.getCmp('grid5').reconfigure(ds,Ext.getCmp('grid5').getColumnModel());
    */
    //alert(Ext.getCmp('grid5').store.url);
    //Ext.getCmp('grid5').getBottomToolbar().bind(ds);
    //ds.load();
    //store.proxy.getConnection().request({url:'/insiis/samples/tag/pageJsonData.jsp?test=test'});
    //store.load({url:'/insiis/samples/tag/pageJsonData.jsp',params:{test:'test2',start:'0',limit: '15'}});
}

</script>
<odin:gridWithPagingTool sm="checkbox"  autoFill="false" rowDbClick="doRDbClick" forceNoScroll="false" url="/samples/tag/pageJsonData.jsp" property="grid6" pageSize="15" title="分页表格" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridColumn id="yes" header="公司" width="160" dataIndex="company" />
  <odin:gridColumn renderer="odin.changeToUrl" header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  header="价格" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>		
</odin:gridWithPagingTool>
</body>
</html>
