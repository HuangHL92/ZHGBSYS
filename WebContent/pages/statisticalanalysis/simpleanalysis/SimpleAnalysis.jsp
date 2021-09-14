<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<table width="98%" border = "1" bordercolor = "#6BB3F3">     
	<tr>
		<td valign="top" align="left" width="25%">
			<table align="left" cellpadding="0" cellspacing="0" width="98%" border="0">

			    <tr>
			        <td valign="top" align="left">
						<odin:select property="collection" defaultValue="��ѡ��ָ�꼯..." codeType="COLLECTION" value="A01"/>
						
			        </td>			        
			    </tr>     
				<tr>
					<td valign="middle" align="left" colspan="3" height="100%">	

						<odin:grid property="grid1" url="/" forceNoScroll="false" >
							<odin:gridJsonDataModel >
								<odin:gridDataCol name="col_code" />
								<odin:gridDataCol name="code_type"/>
								<odin:gridDataCol name="col_name"/>
								<odin:gridDataCol name="table_code" />
								<odin:gridDataCol name="col_data_type_should" isLast="true"/>
					    	</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridColumn hidden="true" header="Ŀ¼ID" dataIndex="col_code"/>
								<odin:gridColumn  header="" dataIndex="code_type" width="10"/>
								<odin:gridColumn  header="ָ����" dataIndex="col_name" />
								<odin:gridColumn  header="ָ����" dataIndex="table_code" hidden="true" />
								<odin:gridColumn  header="ָ������"  dataIndex="col_data_type_should" hidden="true" isLast="true"/>
							</odin:gridColumnModel>
							<odin:gridJsonData>
								{
									data:[]
								}				
							</odin:gridJsonData>		
						</odin:grid>                    			
					</td>	
				</tr>				
			</table>
	    </td>
	    <td valign="middle" align="center" width="5%" border = "0">

     	<table border="0" align="center" width="100%" height="100%" style="top:100px;" >
     		<tr>
				<td height="100">&nbsp;</td>
			</tr>
			<tr>
				<td height="50"><odin:button text="��ť1"></odin:button>	</td>
			</tr>
			<tr>
				<td height="50"><odin:button text="��ť2"></odin:button>	</td>
			</tr>						
			<tr>
				<td height="50"><odin:button text="��ť3"></odin:button>	</td>
			</tr>
     		<tr>
				<td height="50">&nbsp;</td>
			</tr>
		</table>
		
	    </td>
		<td valign="top" align="right" width="70%">
			<table align="left" cellpadding="0" cellspacing="0" width="98%">
				
			    <tr>
			        <td>
			        	<odin:toolBar property="toolBar2">									
							<odin:fill></odin:fill>
							<odin:buttonForToolBar text="ɾ��" id="delete" ></odin:buttonForToolBar>		
							<odin:buttonForToolBar text="ȫ��ɾ��"  id="deleteAll"  isLast="true"></odin:buttonForToolBar>							
						</odin:toolBar>
						<odin:gridSelectColJs2 name="logicSymbols" codeType="LOGICSYMBOLS" ></odin:gridSelectColJs2>
						<odin:editgrid property="grid" topBarId="toolBar2" sm="row" remoteSort="false" forceNoScroll="false">
						    <odin:gridJsonDataModel >
								<odin:gridDataCol name="logchecked"/>
								<odin:gridDataCol name="tableName" />
							    <odin:gridDataCol name="colNamesValue" />
								<odin:gridDataCol name="colNames" />
								<odin:gridDataCol name="opeartors"/>
								<odin:gridDataCol name="colValues"/>
							    <odin:gridDataCol name="colValuesView"/>
						        <odin:gridDataCol name="logicSymbols" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridColumn header="" hidden="true" editor="checkbox" dataIndex="logchecked" edited="true" sortable="false"/>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
	                            <odin:gridColumn dataIndex="tableName"  header="����" align="center" width="10" hidden="true" sortable="false"/>	
								<odin:gridColumn dataIndex="colNamesValue"  header="ָ��ֵ" align="center" hidden="true" sortable="false"/>						
								<odin:gridColumn dataIndex="colNames"  header="ָ����" align="center" sortable="false"/>
								<odin:gridEditColumn2 dataIndex="opeartors" header="�����" align="center" editor="select" edited="false" codeType="OPERATOR" sortable="false"/>
								<odin:gridColumn dataIndex="colValues" header="ֵ(����)" align="center" hidden="true" sortable="false"/>
								<odin:gridColumn dataIndex="colValuesView" header="ֵ" align="center" sortable="false"/>	
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
</table> 
<script>
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
</script>