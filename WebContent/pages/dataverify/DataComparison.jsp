<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.orgdataverify.OrgDataVerifyPageModel" %>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>







	<script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>
    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />
    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<%
String subWinId = request.getParameter("subWinId");
String subWinIdBussessId = request.getParameter("subWinIdBussessId");
if(subWinIdBussessId!=null){
	subWinIdBussessId = new String(request.getParameter("subWinIdBussessId").getBytes("iso8859-1"),"utf8");
}
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var params = parent.Ext.getCmp(subWinId).initialConfig.param;
</script>
<odin:hidden property="subWinIdBussessId" value="<%=subWinIdBussessId %>"/>
<odin:hidden property="subWinIdBussessId2" value=""/>

<odin:hidden property="hcb0" value="1"/>
<odin:hidden property="hcb1" value="1"/>
<script type="text/javascript">
var subWinIdBussessId = '<%=subWinIdBussessId.split(",")[0] %>';
Ext.onReady(function(){
	document.getElementById("subWinIdBussessId2").value=params;
	
})

function errorGiddbclikc(grid,rowIndex,event){
	
	//var record = grid.getSelectionModel().getSelected();
	//console.log(record);
	//var record=grid.getSelectionModel().getSelected();
    // alert(grid.getView().getHeaderCell(0).name);
	// var row = grid.getView().getRow(rowIndex);
    /*  console.log(row.innerText);
	 var record=grid.getSelectionModel().getSelected();
	 console.log(record);
	 var record1 = grid.getStore().getAt(rowIndex);
	 console.log(record1);; */
    //  grid.getSelectionModel().each(function(rec){  
    // alert(rec);
	var errorinfo=grid.getView().getCell(rowIndex,10).firstChild.innerText;

	
	Ext.MessageBox.show({
	    title:"��ϸ����",
	    //msg:"��ϸ����",
	    width:300,
	    value: errorinfo,
	    buttons:Ext.MessageBox.OK,
	    //icon:Ext.MessageBox.ERROR,
	    multiline:true,
	    fn:function(btn,val) {
	        
	    }
	});
}

</script>

  <style>
	.ui-progressbar {
		position: relative;
	}
	.progress-label {
		position: absolute;
		left: 50%;
		top: 4px;
		font-weight: bold;
		text-shadow: 1px 1px 0 #fff;
	}
	</style>
	

<%-- <odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""></odin:textForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="���ݱȶ�" icon="images/icon/right.gif" />
	<odin:buttonForToolBar id="impconfirmBtn"  text="����"  icon="images/icon_photodesk.gif"/>
	<odin:buttonForToolBar id="rejectBtn"  text="�ܾ�"  icon="images/qinkong.gif" isLast="true" />
</odin:toolBar>	 --%>
<div id="addSceneContent">
<odin:hidden property="imprecordid"/>
<odin:hidden property="impclose"/>


<odin:hidden property="bsType"/>		<!-- У��ҵ������ -->
<odin:hidden property="b0111OrimpID"/>	<!-- ��ҳ�洫�����ҪУ��ĵ�λ���� -->
<odin:hidden property="errorSign"/>
<!-- <div id="toolDiv" style="width: 881px"></div> -->

<odin:tab id="tab" width="881">
	<odin:tabModel>
		<odin:tabItem title="�������б�" id="tab1"></odin:tabItem>
		<odin:tabItem title="��Ա�б�" id="tab2"></odin:tabItem>
		<odin:tabItem title="���֤�ظ���Ա�б�" id="tab3" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<%-----------------------------�������б�-------------------------------------------------------%>
	<odin:tabCont itemIndex="tab1">
		<table style="background-color: rgb(201,221,240);width: 100%">
			<tr>
				<td width="600px"></td>
				<td>
					<odin:select property="dataResultType" label="����ƥ������" onchange="typeChange" canOutSelectList="false"
						data="['4', 'ȫ��'],['1', 'һ��'],['5', '��һ��'],['0', '��ɾ��'],['2', '����'],['3', '���޸�']" value="5"></odin:select>
				</td>
			</tr>
		</table>
			
			<odin:editgrid property="list1" topBarId="btnToolBar1"
				bbarId="pageToolBar" rowDbClick="openView" width="600" height="397"
				isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="storeid" />
					<odin:gridDataCol name="imp_record_id" />
					<odin:gridDataCol name="b0111_n" />
					<odin:gridDataCol name="b0111_w" />
					<odin:gridDataCol name="b0111_parent_n" />
					<odin:gridDataCol name="b0111_parent_w" />
					<odin:gridDataCol name="b0101_n" />
					<odin:gridDataCol name="b0101_w" />
					<odin:gridDataCol name="b0114_n" />
					<odin:gridDataCol name="b0114_w" />
					<odin:gridDataCol name="opptimetype" />
					<odin:gridDataCol name="comments" />
					<odin:gridDataCol name="opptime" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn width="30"/>
					<odin:gridEditColumn dataIndex="storeid" header="���ݱȶԽ����id"
						width="40" edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn dataIndex="imp_record_id"
						header="imp_record����" width="170" edited="false" editor="text"
						hidden="true" />
					<odin:gridEditColumn dataIndex="b0101_n" header="�������ƣ��ڲ���"
						width="140" edited="false" editor="text" hidden="false" />
					<odin:gridEditColumn dataIndex="b0114_n" header="�������루�ڲ���"
						width="120" edited="false" editor="text" hidden="false" />
					<odin:gridEditColumn dataIndex="b0101_w" header="�������ƣ��ⲿ��"
						width="140" edited="false" editor="text" hidden="false" />
					<odin:gridEditColumn dataIndex="b0114_w" header="�������루�ⲿ��"
						width="120" edited="false" editor="text" hidden="false" />
					<odin:gridEditColumn dataIndex="comments" header="�ȶԽ������"
						width="160" edited="false" editor="text" hidden="false" />
					<odin:gridEditColumn dataIndex="opptimetype" header="�ȶԽ��"
						width="120" edited="false" editor="select"
						selectData="['0','��ɾ��'],['1','һ��'],['2','����'],['3','���޸�']" />
					<odin:gridEditColumn dataIndex="opptime" header="����ʱ��" width="145" hidden="true"
						edited="false" editor="text" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
		</odin:tabCont>

	<odin:tabCont itemIndex="tab2">
			<odin:editgrid property="errorGrid9"
				rowDbClick="errorGiddbclikc" autoFill="true" title="" 
				topBarId="errorGrid9ToolBar" bbarId="pageToolBar">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0184" />
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="a0101b" />
					<odin:gridDataCol name="a0107" />
					<odin:gridDataCol name="a0144" />
					<odin:gridDataCol name="a0134" />
					<odin:gridDataCol name="a0107b" />
					<odin:gridDataCol name="a0144b" />
					<odin:gridDataCol name="a0134b" />
					<odin:gridDataCol name="errorinfo" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn />
					<odin:gridEditColumn dataIndex="a0101" header="����" align="center"
						width="90" editor="text" edited="false" />
					<odin:gridEditColumn dataIndex="a0184" header="���֤��" align="center"
						width="170" editor="text" edited="false" />
					<odin:gridEditColumn dataIndex="a0107" header="����ʱ��" align="center"
						width="70" editor="text" hidden="true" edited="false" />
					<odin:gridEditColumn dataIndex="a0144" header="�뵳ʱ��" align="center"
						width="70" editor="text" hidden="true" edited="false" />
					<odin:gridEditColumn dataIndex="a0134" header="�μӹ���ʱ��"
						align="center" width="70" editor="text" hidden="true"
						edited="false" />
					<odin:gridEditColumn dataIndex="a0107b" header="�ϱ�����ʱ��"
						align="center" width="70" editor="text" hidden="true"
						edited="false" />
					<odin:gridEditColumn dataIndex="a0144b" header="�ϱ��뵳ʱ��"
						align="center" width="70" editor="text" hidden="true"
						edited="false" />
					<odin:gridEditColumn dataIndex="a0134b" header="�ϱ��μӹ���ʱ��"
						align="center" width="70" editor="text" hidden="true"
						edited="false" />
					<odin:gridEditColumn dataIndex="a0101b" header="�ϱ�����"
						align="center" width="70" editor="text" hidden="true"
						edited="false" />
					<odin:gridEditColumn dataIndex="errorinfo" header="���ս��"
						align="center" width="270" editor="text" renderer="rendererComp"
						edited="false" isLast="true" />
				</odin:gridColumnModel>
				<odin:gridJsonData>
							 	{
							        data:[]
							    }				
				</odin:gridJsonData>
			</odin:editgrid>
	</odin:tabCont>
	
	<odin:tabCont itemIndex="tab3">
			<odin:editgrid property="doublea0184" autoFill="true" title="" topBarId="" bbarId="pageToolBar">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="a0184" />
					<odin:gridDataCol name="a0101" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn />
					<odin:gridEditColumn dataIndex="a0101" header="����" align="center"
						width="90" editor="text" edited="false" />
					<odin:gridEditColumn dataIndex="a0184" header="���֤��" align="center"
						width="170" editor="text" edited="false" isLast="true" />
					
				</odin:gridColumnModel>
				<odin:gridJsonData>
							 	{
							        data:[]
							    }				
				</odin:gridJsonData>
			</odin:editgrid>
	</odin:tabCont>

	</odin:tab>

</div>


<odin:window src="/blank.htm" id="refreshWin" width="560" height="450"
	maximizable="false" title="����" />
<script type="text/javascript">

/* Ext.onReady(function(){ */
function no(){
	Ext.QuickTips.init();  
	
    var tree = new Ext.tree.ColumnTree({
        el:'tree',
        id:'coltree',
        width:1000,
        height:455,
        autoHeight:false,
        rootVisible:false,
        autoScroll:true,
        title: '',
        border : false,
        tbar:new Ext.Toolbar({items: [{
  		   xtype: "checkbox",
           boxLabel : "���˵�λ",
           checked : true,
           handler: function(e){
        	   //console.log(e);
        	   if(e.checked){
        		   	Ext.getCmp("cb1").setDisabled(false);
        		   	document.getElementById('hcb0').value="1";
        		} else{
        			document.getElementById('hcb0').value="0";
        		   	document.getElementById('hcb1').value="0";
        			Ext.getCmp("cb1").setValue(false);
        		   	Ext.getCmp("cb1").setDisabled(true);
        	   }
        	   reloadTree1();
        	},
           id:'cb0'
         },'-',
         {  xtype: "checkbox",
                boxLabel : "�������",
                checked : true,
                handler: function(e){
                	if(e.checked){
                		document.getElementById('hcb1').value="1";
            		} else{
            			document.getElementById('hcb1').value="0";
            	   }
                	reloadTree1();
                },
                id:'cb1'
         },'-',
         {   xtype: "button",
             text : "�鿴��ϸ��Ϣ",
             handler: function(e){
            	 dataContrastResult();
             },
             id:'cb3'
         }
         ],
		  height:25,
		  layout :'column'}
        ),
		  
        columns:[{
            header:'��������',
            width:220,
            dataIndex:'b0101'
        },{
            header:'�����������',
            width:150,
            dataIndex:'b0101m',
            renderer:function (value, metaData, record, rowIdx, colIdx, store){  
            	return '<a title="' + value + '">' + value + '</a>';
            }  
        },{
            header:'�����������',
            width:110,
            dataIndex:'b0114',
            renderer:function (value, metaData, record, rowIdx, colIdx, store){  
            	return '<a title="' + value + '">' + value + '</a>';
            }
        },{
            header:'����',
            width:80,
            dataIndex:'comcount',
            renderer:function (value, metaData, record, rowIdx, colIdx, store){
            	if(value == '1' || record.comcount2 ==value){
            		//pywu  20170611 
            		return '<font color="green">'+value+'</font>';
            		/*
            		if(!value == false) {
						 var id = rowIdx;
						 //�������ֵ�ǰ������Ǵ�ѡ�б��Ʊ�ݻ�����ѡ�б��Ʊ��
						 var isModifyImage = "${YES}";
            			 return '<a href="#" onClick="fnDraftInfo(\''+ id +'\'+ \',\'+ \''+ isModifyImage +'\')"><font color="green"><b>'+ value +'</b></font></a>';
					}
					*/
            	} else {
            		return '<font color="red"><b>'+value+'</b></font>'; 
            		/*
            		if(!value == false) {
						 var id = rowIdx;
						 //�������ֵ�ǰ������Ǵ�ѡ�б��Ʊ�ݻ�����ѡ�б��Ʊ��
						 var isModifyImage = "${YES}";
            			 return '<a href="#" onClick="fnDraftInfo(\''+ id +'\'+ \',\'+ \''+ isModifyImage +'\')"><font color="red"><b>'+ value +'</b></font></a>';
					}
            		*/
            	}
            }  
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.dataverify.DataComparison&eventNames=orgTreeJsonData&initParams='+subWinIdBussessId,
//          dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&eventNames=orgTreeJsonData&userid='+userid,
            baseParams:{ 'hcb0': '1', 'hcb1' : '1'},
			uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),

         	root: new Ext.tree.AsyncTreeNode({
	            text:'Tasks',
	            id: "-1"

        }),
        listeners:{
        	'expandnode':function(node){
        		
        	}
        }
    	/* rootVisible:false; */
    });
    tree.render();
    //tree.expandAll();
}
/* }); */
function reloadTree1(){
	var hcb0 = document.getElementById('hcb0').value;
	var hcb1 = document.getElementById('hcb1').value;
	var tree1 = odin.ext.getCmp("coltree");
	var loader1=tree1.getLoader();
	Ext.apply(loader1.baseParams,{'hcb0': hcb0, 'hcb1' : hcb1});
	tree1.root.reload();
}
function rendererComp(value, params, rs, rowIndex, colIndex, ds) {
	var a0101 = rs.get("a0101");
	var a0107 = rs.get("a0107");
	var a0134 = rs.get("a0134");
	var a0144 = rs.get("a0144");
	var a0101b = rs.get("a0101b");
	var a0107b = rs.get("a0107b");
	var a0134b = rs.get("a0134b");
	var a0144b = rs.get("a0144b");
	var val = '';
	if(a0101 != a0101b){
		val = val +'������'+a0101+'-->' + a0101b + ";";
	}
	if(a0107 != a0107b){
		val = val +'����ʱ�䣺'+a0107+'-->' + a0107b + ";";
	}
	if(a0134 != a0134b){
		val = val +'�μӹ���ʱ�䣺'+a0134+'-->' + a0134b + ";";
	}
	if(a0144 != a0144b){
		val = val +'�뵳ʱ�䣺'+a0144+'-->' + a0144b + ";";
	}
	return val;
}
	Ext.onReady(function() {
		var side_resize=function(){
			//ҳ�����
			//document.getElementById("addSceneContent").style.width = document.body.clientWidth + "px";
			//document.getElementById("addSceneContent").style.height = document.body.clientHeight + "px";
			Ext.getCmp('errorGrid9').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_errorGrid9'))[0]-60);
			//Ext.getCmp('MGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_MGrid'))[1]-2);
			//document.getElementById("toolDiv").style.width = document.body.clientWidth-1 + "px";
			Ext.getCmp('doublea0184').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_doublea0184'))[0]-60);
		}
		side_resize();  
	    window.onresize=side_resize;
	    
	});
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
	function dataContrastResult(){
		var imprecordid = document.getElementById('imprecordid').value;
		radow.doEvent('dataContrastResult',imprecordid);
	}
	
	function openDataContrastResult(){
			//radow.doEvent('dataContrastResult',"11111111");
			var imprecordid = document.getElementById('imprecordid').value;
			$h.openWin('dataContrastResult','pages.dataverify.DataContrastResult&initParams='+ imprecordid,'���ݱȶԽ������',1040,558,imprecordid,'<%=request.getContextPath()%>');
	}
	 //�򿪣��¿�tabҳ�棩       
	function openView(){
		var obj=odin.ext.getCmp('list1').getSelectionModel().getSelections()[0];
		var storeid=obj.get('storeid');
		var iWidth=1150; //�������ڵĿ��;
		var iHeight=550; //�������ڵĸ߶�;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //��ô��ڵĴ�ֱλ��;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //��ô��ڵ�ˮƽλ��;
		$h.openWin('DataContrastResultWin','pages.dataverify.DataContrastResultWin&initParams='+ storeid,'���ݱȶԽ������',680,380,storeid,'<%=request.getContextPath()%>');

	 }
	 function typeChange(){
		 radow.doEvent('list1.dogridquery');
	 }
	 
	 function ShowCellCover(elementId, titles, msgs)
		{	
			Ext.MessageBox.buttonText.ok = "�ر�";
			if(elementId.indexOf("start") != -1){
			
				Ext.MessageBox.show({
					title:titles,
					msg:msgs,
					width:300,
			        height:300,
					closable:false,
				//	buttons: Ext.MessageBox.OK,		
					modal:true,
					progress:true,
					wait:true,
					animEl: 'elId',
					increment:5, 
					waitConfig: {interval:150}
					//,icon:Ext.MessageBox.INFO        
				});
			}else if(elementId.indexOf("success") != -1){
					Ext.MessageBox.confirm("ϵͳ��ʾ", msgs, function(but) {  
						
					}); 
			}else if(elementId.indexOf("failure") != -1){
					Ext.MessageBox.show({
						title:titles,
						msg:msgs,
						width:300,
						modal:true,
				        height:300,
						closable:true,
						//icon:Ext.MessageBox.INFO,
						buttons: Ext.MessageBox.OK		
					});
					/*
					setTimeout(function(){
							Ext.MessageBox.hide();
					}, 2000);
					*/
			}else {
					Ext.MessageBox.show({
						title:titles,
						msg:msgs,
						width:300,
						modal:true,
				        height:300,
						closable:true,
						//icon:Ext.MessageBox.INFO,
						buttons: Ext.MessageBox.OK		
					});
				}
		}
	</script>
