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

<title>��ǩ����</title>
<style type="text/css">
	    .listname {
		font-family: "΢���ź�", "����", "����";
		font-size: 14px;
		font-weight: bold;
	}
	    .listsex {
		font-family: "����";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "����", "����";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "����";
		font-size: 12px;
		text-decoration: none;
	}
	
.upper{
	text-transform: uppercase; 
}
.lower{
	text-transform:lowercase;
}	
</style>	
<odin:head />

</head>

<body>
<odin:base>
<script>
function doSave(record,index)
{
	//alert(index);
	//alert('��ť�����¼�������');  
	/*
	if(cueCell.rowIndex>=0){
		var store = odin.ext.getCmp('grid7').store;
		if(cueCell.colIndex==5){
			store.getAt(cueCell.rowIndex).set('pctChange',record.get('key'));
		}
	} 
	*/  
}

function doClick(scope,button,e){
   //odin.addGridRowData('grid7');
   odin.ext.getCmp('grid7').stopEditing();
   for(i=0;i<Ext.getCmp('grid7').store.getCount();i++){
	   alert(Ext.getCmp('grid7').store.getAt(i).get('pctChange'));
   }
   odin.addGridRowData('grid7',{company:'',price:0,change:0,pctChange:0,lastChange:''});   
}


<odin:gridJsonDataModel  id="psid" root="rows" totalProperty="result">
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="sex"/>
  <odin:gridDataCol name="iscode"/>
  <odin:gridDataCol name="pscode"/>
  <odin:gridDataCol name="birthdate"/>
  <odin:gridDataCol name="workdate"/>
  <odin:gridDataCol name="laborrel"/>
  <odin:gridDataCol name="psstatus"/>
  <odin:gridDataCol name="cpname"/>
  <odin:gridDataCol name="mdtype"/>
  <odin:gridDataCol name="account" isLast="true"/>
</odin:gridJsonDataModel>
<odin:dataStore name="myDataStore" url="/samples/tag/toolBarTag_data.jsp" data="{'result' : 2,'rows': [
	 { 'psid': 1, 'name': '�ΰ��', 'sex': '��', 'iscode': '3301831972120700763', 'pscode': '12345678', 'birthdate':'1972-12-07','workdate':'1994-07-01','laborrel':'�ڱ�','psstatus':'��ְ','cpname':'IVAO China Division','mdtype':'��ҵ��ְ','account':'251.45'},
	 { 'psid': 2, 'name': '����', 'sex': 'Ů', 'iscode': '3122311978042876123', 'pscode': '12345679', 'birthdate':'1978-04-28','workdate':'1999-07-15','laborrel':'�ڱ�',  'psstatus':'��ְ','cpname':'���Ե�λ11111111111111111111111111111111111111111111111111111111','mdtype':'����Ա��ְ','account':'65.15'}
	]}">
</odin:dataStore>
<odin:template name="resultTpl">
	'<tpl for="."><div class="search-item" style="width=400px">',
	'<p>{#}&nbsp;<span class="listname">{name}</span>,<span class="listsex">{sex}</span>,&nbsp;{pscode},',
	'<span class="listnormal">{iscode}</span><br>',
	'<span class="listnormal">{cpname}</span></p>',
	'</div></tpl>'
</odin:template> 

	var getSelectDataToArray = function(selectId){ /***��ȡselect��������ݣ�����ת������Ҫ������������******/
		var store = odin.ext.getCmp(selectId).store;
		var length = store.getCount();
		var arrayData = new Array(length);
		for(i=0;i<length;i++){
			var temp = new Array(2);
			temp[0] = store.getAt(i).get('pscode');
			temp[1] = store.getAt(i).get('name');
			arrayData[i] = temp;
		}
		odin.cueSelectArrayData = arrayData;
	}
	var accEditGridSelectColSelEve = function(record,index){ /***�Ա༭���������༭ʱѡ���¼���Ĭ�ϴ���***/
		getSelectDataToArray('grid7_patChange');
	}
	function doAutoSelectByNum(e){
		//alert('���ⰴ��');
		var key = e.getKey();
		//alert(key);
		if(key>=48&&key<=57){
			var store = null;
			try{
				var obj = odin.ext.getCmp('grid7_patChange');
				store = obj.store;
				var count = store.getCount();
				//alert(count);
				if(count>0){
					if(key==48){ //����0
						obj.select(count-1,true);
					}else{//����1-9
						obj.select(key-48-1,true);
					}
					window.event.returnValue=false;
				}
			}catch(e){
				//
			}
		}
		if(e.getKey()==e.ENTER){
				e.keyCode = 9;
		}
		if(e.isSpecialKey()){
            this.fireEvent("specialkey", this, e);
        }
	}
</script>
<odin:toolBar property="myToolBar">
  <odin:buttonForToolBar text="����" iconCls="add" handler="doClick" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="����������"></odin:buttonForToolBar>
  <odin:separator />
  <odin:fill></odin:fill>
  <odin:buttonForToolBar text="������������" handler="doFilterSelectData"></odin:buttonForToolBar>
  <odin:separator />
  <odin:buttonForToolBar text="ȡ����������" handler="doClearSelectFilter"></odin:buttonForToolBar>
  <odin:separator />
  <odin:buttonForToolBar text="��������" handler="doFilterData"></odin:buttonForToolBar>
  <odin:buttonForToolBar text="ȡ������" handler="doClearFilter"></odin:buttonForToolBar>
  <odin:buttonForToolBar isLast="true" text="����" iconCls="add" handler="doClick" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="����������"></odin:buttonForToolBar>
</odin:toolBar>
<odin:gridSelectColJs name="pctChange" codeType="AAE140"></odin:gridSelectColJs>
<odin:editgrid property="grid7" keydown="editGridKeyDown" filterFunc="doFilterPrice" beforeedit="doSetIsEdit" cellmousedown="doCellMouseDown" rowDbClick="doRowDbClick" cellDbClick="doCellDbClick" topBarId="myToolBar" inputName="gridSubData"  afteredit="odin.afterEditForEditGrid" title="�ɱ༭���" width="600" height="400">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="boolean"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn width="50"/>
  <odin:gridEditColumn id="yes" header="��˾" width="160" selectOnFocus="true" dataIndex="company" editor="text"/>
  <odin:gridEditColumn  header="�۸�" dataIndex="price" editor="number" selectOnFocus="true" decimalPrecision="4" maxValue="10000"/>
  <odin:gridColumn  dataIndex="change" editor="checkbox" edited="true"/>
  <odin:gridColumn editorId="grid7_patChange" queryDelay="1000" onKeyDown="doAutoSelectByNum" upperOrLower="1" width="450" dataIndex="pctChange" tpl="resultTpl" hideTrigger="true" onSelect="accEditGridSelectColSelEve" store="myDataStore" displayField="name" selectData="['1', '��'],['2', 'Ů']" editor="select" edited="true"/>
  <odin:gridColumn editorId="grid7_patChange1" width="250" dataIndex="pctChange" onSelect="doSave" selectData="['1', '��'],['2', 'Ů']" editor="select" edited="true"/>
  <odin:gridEditColumn  dataIndex="lastChange" renderer="renderDate" width="250" isLast="true" editor="date" format="Y-m"/>
</odin:gridColumnModel>
<odin:griddata>
        ['3m Co',71.72,false,1.20,'2007-08'],
        ['Alcoa Inc',29.01,false,1.47,''],
        ['Altria Group Inc',83.81,false,0.34,''],
        ['American Express Company',52.55,false,0.02,''],
        ['American International Group, Inc.',64.13,false,0.49,''],
        ['3m 2Co',71.72,false,1.20,''],
        ['Alc4oa Inc',29.01,false,1.47,''],
        ['Alt4ria Group Inc',83.81,false,0.34,''],
        ['Ame4rican Express Company',52.55,false,0.02,''],
        ['Amer4ican International Group, Inc.',64.13,false,0.49,''],
        ['3m Co',71.72,false,1.20,''],
        ['Alcoa Inc',29.01,false,1.47,''],
        ['Altria Group Inc',83.81,false,0.34,''],
        ['American Express Company',52.55,false,0.02,''],
        ['American International Group, Inc.',64.13,false,0.49,''],
        ['3m 2Co',71.72,false,1.20,''],
        ['Alc4oa Inc',29.01,false,1.47,''],
        ['Alt4ria Group Inc',83.81,false,0.34,''],
        ['Ame4rican Express Company',52.55,false,0.02,''],
        ['Amer4ican International Group, Inc.',64.13,false,0.49,''],
        ['AT&T Inc.',31.61,false,-1.54,'']
</odin:griddata>		
</odin:editgrid>


<script>
var cueCell = {};
function doCellMouseDown(grid,rowIndex,colIndex,event){
	cueCell.rowIndex = rowIndex;
	cueCell.colIndex = colIndex;
}
function doCellDbClick(grid,rowIndex,colIndex,event){
    alert('doCellDbClick');
}
function doRowDbClick(grid,rowIndex,event){
    alert('doRowDbClick');
}
Ext.onReady(function(){
    //odin.ext.getCmp('grid7').addListener('beforeedit',doKeyp);
});
function renderDate(dateVal){
    if(!dateVal||dateVal==""){
       dateVal = new Date();
    }else{
     	if(typeof dateVal == 'string'){
    		dateVal = Date.parseDate(dateVal,'Y-m');
    	}
    }
    return Ext.util.Format.date(dateVal,'Y-m');
}
function doKeyp(e){
     e.cancel = true;
}
var paramsType = 0;
var codeType = ['AKA083','AAE140','EAZ028','EAC059','AIC161']
function doSetIsEdit(e){
	var grid = e.grid;
    var record = e.record;
    var field = e.field;
    var originalValue = e.originalValue;
    var value = e.value;
    var row = e.row;
    var column = e.column;
	var cancel = e.cancel;
	if(grid.store.getAt(row).get('price')<=55&&column==2){
	    //alert(2);
	    e.cancel = true;
	}
	if(field == 'pctChange'){
		paramsType++;
		if(paramsType>5){
			paramsType = 1;
		}
		odin.loadDataForSelectStore('grid7_patChange1',codeType[paramsType-1]);
	}
}

function editGridKeyDown(e){
	alert(e.getKey());
}
</script>

<table>
	 <tr>
	   <odin:textarea property="aab011" cols="75" rows="5" value="
	   odin.getGridJsonData('grid7','gridSubData');
       odin.setGridJsonData('grid7','gridSubData');
       //alert(Ext.util.JSON.encode(Ext.getCmp('grid7').store.getAt(0).data))
       ">
	   </odin:textarea>
	   <td>
		   <script>
		    function doFun()
			{
			     //alert(document.all('aab011').value);
			     eval(document.all('aab011').value);
			}
		   </script>
	       <odin:button text="ִ��" handler ="doFun" />
	    </td>
	    <td>
	       <input type="button" value="��ʾ�������е�ֵ" onclick="
	        for(i=0;i<Ext.getCmp('grid7').store.getCount();i++){
	           alert(Ext.getCmp('grid7').store.getAt(i).get('pctChange'));
			}
			" />
			 <input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;">
	    </td>
	 </tr>
 </table>
<script>
function doFilterData(){
    odin.doFilterGridCueData(Ext.getCmp('grid7'),doFilterPrice);//���˱��������
}
function doClearFilter(){
    odin.doClearFilter(Ext.getCmp('grid7')); //�ָ�������ݵ�����ǰ
    
}
function doFilterPrice(record,id){
    if(record.get('price')>=50){
        return true;
    }else{
        return false;
    }
}
function doFilterSex(record,id){
     if(record.get('key')=='1'){
          return true;
    }else{ return false; }
}
function doFilterSelectData(){
	odin.doFilterGridCueData(odin.ext.getCmp('grid7_patChange'),doFilterSex);//���������༭�����������
} 
function doClearSelectFilter(){
	odin.doClearFilter(odin.ext.getCmp('grid7_patChange')); //�ָ����������ݵ�����ǰ
}
function doReloadData(){
	odin.loadGridData('grid6',{test:'test'});
	odin.loadGridData('grid8',{test:'����'},beforeloadFun);
}
function beforeloadFun(ds){
    if(ds){
       if(ds.getAt(0)){
           alert(ds.getAt(0).get('company'));
       }
    } 
}
</script>
<odin:toolBar property="myToolBar2">
  <odin:buttonForToolBar isLast="true" text="���¼�������" iconCls="add" handler="doReloadData" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon" tooltip="����������"></odin:buttonForToolBar>
</odin:toolBar>
<odin:editgrid property="grid6" isFirstLoadData="true" topBarId="myToolBar2" url="/samples/tag/BasicGridData.jsp" title="�ɱ༭���" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridEditColumn header="��˾" width="160" dataIndex="company" editor="text"/>
  <odin:gridEditColumn  header="�۸�" dataIndex="price" editor="number" />
  <odin:gridColumn  dataIndex="change" editor="checkbox"/>
  <odin:gridColumn  dataIndex="pctChange"  editor="text"/>
  <odin:gridEditColumn  dataIndex="lastChange"  isLast="true" editor="date"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:editgrid property="grid8" beforeload="beforeloadFun" collapsible="false" bbarId="pagetoolbar"  isFirstLoadData="true"  url="/samples/tag/pageJsonData.jsp" title="�ɱ༭���" width="600" height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridEditColumn header="��˾" width="160" dataIndex="company" editor="text"/>
  <odin:gridEditColumn  header="�۸�" dataIndex="price" editor="number" />
  <odin:gridColumn  dataIndex="change" editor="checkbox"/>
  <odin:gridColumn  dataIndex="pctChange"  editor="text"/>
  <odin:gridEditColumn  dataIndex="lastChange"  isLast="true" editor="date"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:hidden property="test"/>

</odin:base>  
</body>
</html>
