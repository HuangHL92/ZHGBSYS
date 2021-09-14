<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp"%>

<head>
	<style>
		#tree-div{
			width: 240px;
			float: left;
			position: relative;
			overflow: auto;  
			border: 2px solid #c3daf9; 
			height: 485px;
		}
		
		#grid-div{
			float: left;
		}
	</style>

	<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
</head>
	
<body>
	<odin:hidden property="a0000s" /> <!-- 拟任免人选基本情况ID -->
	 
	<div id="tree-div"></div>
	
	<div id="grid-div">
		<div id="panel_content" align="center">
			<div></div>
		</div>
		<odin:panel contentEl="panel_content" property="mypanel" ></odin:panel>

		<odin:editgrid2 property="gridMnrm" isFirstLoadData="false" clicksToEdit="false" autoFill="false" pageSize="20">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="detail" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn />
				<odin:gridEditColumn2 dataIndex="detail" header="拟任信息" width="557" menuDisabled="true"
					edited="false" editor="text" sortable="false" align="center" isLast="true" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
	</div>
	
</body>	

<script>
	var basePath = "<%=request.getContextPath()%>";
	var tree;
	
	$(function(){
		$("#a0000s").val(parent.Ext.getCmp("mnrm").initialConfig.a0000s);
	});
	
	Ext.onReady(function(){  
        var tree = new Ext.tree.TreePanel({  
        	id :'group',
        	el : 'tree-div',
            region: 'center',  
            collapsible: true,  
            width: 200,  
            border : false,  
            autoScroll: true,  
            animate : true,  
            rootVisible: false,  
            split: true,  
            loader : new Ext.tree.TreeLoader({  
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&eventNames=orgTreeJsonData&tag=1' 
            }),
            listeners: {      
                click: function(node) {  
                	var icon = node.attributes.icon;
                	var id = node.attributes.id;
                	if("./main/images/icons/companyOrgImg2.png"!=icon){
                    	Ext.Msg.alert('提示', '请选择法人单位!' );  
                	} else {
                		radow.doEvent('showDetail',id);
                	}
                }       
            }  
        });  
         
        var root = new Ext.tree.AsyncTreeNode( {
    	    text :  '',
            iconCls : '',
            draggable : false,
            expanded:true,
            id : '-1'
      	});
        tree.setRootNode(root);
        
        new Ext.Viewport({  
            items: [tree]  
        });  
        
		Ext.getCmp('gridMnrm').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_gridMnrm'))[0]);
		Ext.getCmp('gridMnrm').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_gridMnrm'))[1]-2); 
		document.getElementById("panel_content").style.width = Ext.getCmp('gridMnrm').getWidth() + "px";
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
</script>

