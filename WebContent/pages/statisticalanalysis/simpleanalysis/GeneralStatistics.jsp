<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript">
	function save(param){
		$h.openWin('SaveStatistics','pages.statisticalanalysis.simpleanalysis.SaveStatistics','�����Զ���ͨ��ͳ��',400,570,param,'<%=request.getContextPath()%>');
	}
</script>
<odin:hidden property="sublibrariesmodelid"/>
<style>
	#aa{
		width:415px !important;
	}
	#conditionName{
		width:360px !important;
	}
	li{height:14px;width:160px;cursor:hand}
	.zdytjxlb{height:14px;width:180px;}
	.tx{border:solid #add9c0; border-width:1px 1px 1px 1px; padding:0px 0px;border-color: #A4D3EE}
	.xt{border:solid #add9c0; border-width:1px 1px 1px 1px;height:380px}
	/* ���ͼ���С�� */
	/* cursor:hand */
</style>
<table>
  <tr>
   <td>
    <table>
      <tr>
       <td>
		 <table >
			<tr>
				<td>
					<table style="width: 500px">
						<tr>
						 <td>
						  <odin:select2  property="collection" label="��Ϣ��" />
						 </td>
						 <%-- <odin:select property="searchInfoBtn" label="��Ϣ��" /> --%>
						 <td width="80px" />
						 <td>
						  <odin:select2  property="colName" label="��Ϣ��" />
						 </td>
						</tr>
	                    <tr>
						 <td>
                          <%-- <odin:select2 property="opeartor" label="�����" data="['={v}','����'],['!={v}','������'],['>{v}','����'],['>={v}','���ڵ���'],['<{v}','С��'],['<={v}','С�ڵ���'],['like {v%}','ʼ��'],['not like {v%}','��ʼ��'],['like {%v}','ֹ��'],['not like {%v}','��ֹ��'],['like {%v%}','����'],['not like {%v%}','������']" value="={v}" required="true" /> --%>
                          <odin:select2  property="opeartor" label="�����" codeType="OPERATOR" value="={v}" />
						 </td>
						 <td width="80px" />
						 <td>
						  <odin:select2  property="colValue" label="ȡ&nbsp&nbspֵ" canOutSelectList="true" />
						 </td>
						</tr>															
					</table>
				</td>
		    </tr>
			<tr>
			    <td>
			        <table style="width: 100%">
			           <tr>
			           <td style="font-size: 12px">
			         	  �������ӹ�ϵ
			           </td>
						<td align="left">
                         <odin:select2  width="112"  property="logicSymbol" data="[' and 1=1 ','��'],[' and ','����'],[' or ','����']" value=" and 1=1 " />
						</td>
						<td style="width:13%" align="right">
						 <odin:button text="����" property="savecond" handler="savecond"></odin:button>
						</td>
						<td style="width:13%" align="right">
						 <odin:button text="�޸�" property="editcond"></odin:button>
						</td>
						<td style="width:13%" align="right">
                         <odin:button text="ɾ��" property="delEmpRow" handler="delEmpRow"></odin:button>
                         <odin:hidden property="cueRowIndex"/>
						</td>
						<td style="width:13%" align="right">
                         <odin:button text="���" property="delAll" ></odin:button>
						</td>		            			            
			           </tr>
			         </table>
			   </td>
			</tr>
		</table>
	</td>
	</tr>	
	<tr>
	 <td>
       <table style="width:500">
          <tr>
            <td>	
	<odin:toolBar property="toolBar2">
		
		<odin:buttonForToolBar text="(+" id="addLeftBracket" handler="addleftBracket"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="(-" id="delLeftBracket" handler="delleftBracket"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="����" id="upRow" icon="images/icon/arrowup.gif"></odin:buttonForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="����" id="downRow" icon="images/icon/arrowdown.gif"  ></odin:buttonForToolBar>		
		<odin:buttonForToolBar text="-)" id="delRightBracket" handler="delRightBracket"></odin:buttonForToolBar>		
		<odin:buttonForToolBar text="+)"  id="addRightBracket" handler="addRightBracket" isLast="true"></odin:buttonForToolBar>
		
	</odin:toolBar>
    <odin:gridSelectColJs2 name="logicSymbols" codeType="LOGICSYMBOLS" ></odin:gridSelectColJs2>
    <odin:gridSelectColJs2 name="existsFlag" selectData="['exists','����'],['not exists','������'],['','']"  ></odin:gridSelectColJs2>

<odin:editgrid property="grid" topBarId="toolBar2" height="200" sm="row" remoteSort="false" >
					    <odin:gridJsonDataModel >
							<odin:gridDataCol name="logchecked"/>
							<odin:gridDataCol name="tableName" />
							<odin:gridDataCol name="leftBracket" />
							<odin:gridDataCol name="existsFlag" />
						    <odin:gridDataCol name="colNamesValue" />
							<odin:gridDataCol name="colNames" />
							<odin:gridDataCol name="opeartors"/>
							<odin:gridDataCol name="colValues"/>
						    <odin:gridDataCol name="colValuesView"/>
					        <odin:gridDataCol name="rightBracket" />
					        <odin:gridDataCol name="logicSymbols" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridColumn header="" hidden="true" editor="checkbox" dataIndex="logchecked" edited="true" sortable="false"/>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
                            <odin:gridColumn dataIndex="tableName"  header="����" align="center" width="10" hidden="true" sortable="false"/>	
							<odin:gridColumn dataIndex="leftBracket"  header=" " align="center" width="10" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="existsFlag"  header=" " align="center" width="20" hidden="true" sortable="false" editor="select"  edited="true" selectData="['exists','����'],['not exists','������'],['','']"/>	
							<odin:gridColumn dataIndex="colNamesValue"  header="ָ��ֵ" align="center" hidden="true" sortable="false"/>						
							<odin:gridColumn dataIndex="colNames"  header="ָ����" align="center" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="opeartors" header="�����" align="center" editor="select" edited="false" codeType="OPERATOR" sortable="false"/>
							<odin:gridColumn dataIndex="colValues" header="ֵ(����)" align="center" hidden="true" sortable="false"/>
							<odin:gridColumn dataIndex="colValuesView" header="ֵ" align="center" sortable="false"/>							
							<odin:gridColumn dataIndex="rightBracket" header=" " align="center" width="10" sortable="false"/>
			                <odin:gridEditColumn2 header="��ϵ" editor="select"
			                width="15" dataIndex="logicSymbols"  codeType="LOGICSYMBOLS" edited="true" sortable="false"  isLast="true"/> 
 						</odin:gridColumnModel> 
				<odin:gridJsonData> 
					{data:[]}
				</odin:gridJsonData> 
				</odin:editgrid>
	</td>
	</tr>			
     </table>       				 
	</td>
	</tr>
	<tr>
	<td height="10">
	</td>
	</tr>
	<tr>
	<td>
<table>
	<tr>
	<td>
	  <odin:textarea property="aa" label="ͳ������һ��" colspan="4" ></odin:textarea>
	</td>
	</tr>
	<tr>
	<td >
	<odin:textEdit property="conditionName" label="ͳ����������" width="390" ></odin:textEdit>
	</td>
	<td >
	<odin:button text="���" property="btnSave"></odin:button>
	</td>
	</tr>
	<tr>
	<td>
	</td>
	<td>
	</td>	
	</tr>
</table>		
	</td>
	</tr>
</table>
  </td>
  <td>
		<table class="xt">
			<tr>
				<td class="tx" style="height:20px;width:180px;text-align:center;font-size: 12px">
				�ѽ�ͳ������<br>�����ɾ����༭			   
				</td>		
			</tr>	
			<tr valign="top">
				<td>
				<ol id="myList">
					
				</ol>
				</td>
			</tr>
			<tr >
			   <td align="right" valign="bottom">
			   <odin:button text="ɾ��" property="del" handler="delright"></odin:button>
			   <odin:hidden property="uuid" value=""/>
			   <odin:hidden property="rightname" value = ""/>    
			   </td>
			</tr>	
		</table>
	</td>
  </tr>		
</table>

<table style="width: 650px" id="bp" >
  <tr >
  	<td align="right" width="500px">
   	<odin:button text="����" property="save"></odin:button>    
   	</td>
    <td align="right" >
   <odin:button text="ͳ��" property="showPic"></odin:button>
   </td>
   <td align="right">
   <odin:button text="�ر�" property="close"></odin:button>    
   </td>
  </tr>
</table>

<script>
function tjfx(param){
	$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics','�Զ���ͨ��ͳ��ͼ',1010,650,param,'<%=request.getContextPath()%>');
}
function tjfx2(param){
	var title = param.split('&')[1];
	var data = param.split('&')[0];
	$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics',title+'ͨ��ͳ��ͼ',1010,650,data,'<%=request.getContextPath()%>');
}

function delright(){
	var uuid = document.getElementById("uuid").value;
	if(uuid==""){
		odin.error("��ѡ��Ҫɾ����������");
		return;
	}
	var li = document.getElementById(uuid);
	var name = document.getElementById("rightname").value;
	$h.confirm3btn('ϵͳ��ʾ','�Ƿ�ɾ��ͳ��������'+name+'',200,function(id){
		if(id=='yes'){
			//var li = document.getElementById(uuid);
			document.getElementById("myList").removeChild(li);
			radow.doEvent("delright",uuid);
		}else if(id=='no'){
			
		}else if(id=='cancel'){
			
		}
	});
	
}

function CreateLi(value,uuid){
	var name = value;
	var id = uuid;
	//��� li
	var li = document.createElement("LI");
	li.setAttribute('id',uuid);
	li.style.fontSize = "12px"; 
	li.attachEvent('onclick', function(){
		var lis = document.getElementById("myList").childNodes;
		for(var i=0;i<lis.length;i++){ 
			lis.item(i).style.background='';
		} 
		document.getElementById(id).style.background='#CCCCCC';
		document.getElementById("rightname").value = name;
		radow.doEvent("Print",id);});
/* 	li.attachEvent('onmouseover',function(){
		document.getElementById(id).style.background='#CCCCCC';});
	li.attachEvent('onmouseout',function(){
		document.getElementById(id).style.background='';;}); */
	//�������
	var txt = document.createTextNode(name);
	//��� img
����   var img = document.createElement("img");
    //���� img ͼƬ��ַ
����  img.src = "<%=request.getContextPath()%>/main/image/leaf.gif";
    li.appendChild(img);
	li.appendChild(txt);
	
	document.getElementById("myList").appendChild(li);
}

function addEmpRow(){
	radow.addGridEmptyRow("grid",0);
	printSql();
}
function dataRender4del(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=href='javascript:void(0)' onclick = del('"+rowIndex+"')>ɾ��</a>";
}
function del(rowIndex){
	var grid = odin.ext.getCmp('grid');
	var store = grid.store;
	store.removeAt(rowIndex);
	printSql();
}

function delEmpRow1(){
	var grid = odin.ext.getCmp('grid');
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount()-1;
	if(store.getCount() > 0){
		for( var i = store.getCount()-1 ;i>=0; i-- ){
			var ck = grid.getStore().getAt(i).get("logchecked");
	        if(ck == true){
	        	store.remove(grid.getStore().getAt(i));
			}
		}
	}
	grid.view.refresh();
/**	for (i = 0; i < arrayObj.length; i++)
    {
        store.remove(grid.getStore().getAt(arrayObj[i]));
    }
	*/
	printSql();
				
}
function delEmpRow(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);
				
	}
	grid.view.refresh();
	printSql();			
}

function savecond(){
	document.getElementById("conditionName").focus(); 
	radow.doEvent('savecond');
}

function addleftBracket(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		selected.data['leftBracket']=selected.data['leftBracket']+'(';

	}
	grid.view.refresh();
	printSql();
  } 

function addRightBracket(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		selected.data['rightBracket']=selected.data['rightBracket']+')';

	}
	grid.view.refresh();
	printSql();
  }  
  
function delleftBracket(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		selected.data['leftBracket']=selected.data['leftBracket'].substr(selected.data['leftBracket'].indexOf('(')+1,selected.data['leftBracket'].length);

	}
	grid.view.refresh();
	printSql();
  } 

function delRightBracket(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		selected.data['rightBracket']=selected.data['rightBracket'].substr(0,selected.data['rightBracket'].lastIndexOf(')'))+selected.data['rightBracket'].substr(selected.data['rightBracket'].lastIndexOf(')')+1,selected.data['leftBracket'].length);

	}
	grid.view.refresh();
	printSql();
  } 
//��װ��������sqlƴ������ 
function printSql(){
	var grid = odin.ext.getCmp('grid');
	var store = grid.store;
	var sqltoChar = "";
	var eiDTO = {};
	if(store.getCount() > 0){
		for( var i = 0 ;i < store.getCount(); i++ ){
			var ck = grid.getStore().getAt(i).get("logchecked");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("leftBracket");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("colNames");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("opeartors");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("colValuesView");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("rightBracket");
			sqltoChar = sqltoChar+grid.getStore().getAt(i).get("logicSymbols");
		}
	}
	document.all.aa.value = sqltoChar;
	radow.doEvent("PrintSQL",odin.encode(eiDTO));
}
function choose(num){
	
}

function replace(oldUuid){
	Ext.MessageBox.show({
	    title:"ϵͳ��ʾ",
	    msg:"ͳ�����������Ѵ��ڣ��Ƿ񸲸ǣ�",
	    buttons:{"ok":"����","cancel":"ȡ��"},
		modal:true,
		closable:false,
	    fn:function(e){
			if(e == "ok"){
				radow.doEvent("replaceEvent",oldUuid);
			}
			if(e == "cancel"){
				return ;
			}
		
		}
	  
	});

} 
</script>