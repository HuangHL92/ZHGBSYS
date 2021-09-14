<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>


<style>


.x-grid3-cell-inner, .x-grid3-hd-inner{
  white-space:normal !important;
}

</style>
<script  type="text/javascript">

</script>


<odin:hidden property="a0000"/>
<table width='100%' style="overflow:hidden">
	<tr>
		<td>
			<table width='100%' style="overflow-y: auto; overflow-x:hidden;"><tr>
			 	<td  valign="top">
                        <odin:tab id="tab" width="270" height="670">
                            <odin:tabModel>
                                <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                            </odin:tabModel>
                            <odin:tabCont itemIndex="tab1" className="tab">
                                <table id="tableTab1" style="height: 465px;border-collapse:collapse;">
                                    <tr>
                                        <td colspan="2">
                                            <div id="tree-div" style="overflow-y: auto; overflow-x:hidden;height: 600; width: 100%; border: 2px solid #c3daf9;"></div>
                                            <odin:hidden property="checkedgroupid"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                            <input type="checkbox" id="isContain"  />
                                            <span style="font-size: 13px">包含下级</span>
                                        </td>
                                    </tr>
                                </table>
                            </odin:tabCont>
                        </odin:tab>
                    </td>
			<td width="40%" height="670" >
				<odin:editgrid2 property="a01Grid" hasRightMenu="false" topBarId="btnToolBar" bbarId="pageToolBar" pageSize="20"  width="500" height="670" isFirstLoadData="false" pageSize="200" url="/">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0101_1" />
						<odin:gridDataCol name="a0104_1" />
						<odin:gridDataCol name="a0107_1" />
						<odin:gridDataCol name="a0192a_1"/>
						<odin:gridDataCol name="a0000_1" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
						<odin:gridEditColumn2 dataIndex="a0101_1" width="100" header="姓名" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0104_1" width="50" header="性别" editor="select" edited="false" align="center" codeType="GB2261" />
						<odin:gridEditColumn2 dataIndex="a0107_1" width="80" header="出生年月" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0192a_1" width="300" header="工作单位及职务" editor="text" edited="false" align="center"isLast="true" />
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			<td width="40%" >
				<odin:editgrid2 property="a02Grid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET"
				width="500" height="670" pageSize="200" isFirstLoadData="false" url="/">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0201b_2"/>
						<odin:gridDataCol name="a0215a_2"/>
						<odin:gridDataCol name="blackcheck" />
						<odin:gridDataCol name="graycheck" />
						<odin:gridDataCol name="a0000_2"/>
						<odin:gridDataCol name="a0200_2" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
						<odin:gridEditColumn2 dataIndex="a0201b_2" width="150" header="工作单位" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0215a_2" width="150" header="职务名称" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2  width="40" header="不显示"  editor="checkbox" dataIndex="blackcheck" edited="true" align="center" hideable="false" checkBoxClick="blackcheck" />
						<odin:gridEditColumn2  width="60" header="选择性显示"  editor="checkbox" dataIndex="graycheck" edited="true" align="center" hideable="false" checkBoxClick="graycheck"  isLast="true"/>
						
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			</tr></table>
		
		</td>
	</tr>
</table>
<script type="text/javascript">
<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
}

Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('a02Grid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_a02Grid')).top-4)*0.99);
	 Ext.getCmp('a01Grid').setHeight(Ext.getCmp('a02Grid').getHeight());
	 
});




function flash() {
	document.getElementById("checkedgroupid").value = '';
	radow.doEvent('a01Grid.dogridquery');
	
}


var tree;
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function () {
    var Tree = Ext.tree;
    tree = new Tree.TreePanel({
        id: 'group',
        el: 'tree-div',//目标div容器
        split: false,
        width: 270,
        height: 600,
        minSize: 164,
        maxSize: 164,
        rootVisible: false,//是否显示最上级节点
        autoScroll: true,
        animate: true,
        border: false,
        enableDD: false,
        containerScroll: true,
        loader: new Tree.TreeLoader({
            dataUrl: 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1'
        })
    });
    tree.on('click', treeClick);

    var root = new Tree.AsyncTreeNode({
        id: "-1"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(false, true, callback);

    var viewSize = Ext.getBody().getViewSize();
    var editgrid = Ext.getCmp('editgrid');

    var resizeobj = Ext.getCmp('tab');
    resizeobj.setHeight(viewSize.height - 19);//34 - 29
    var tableTab1 = document.getElementById("tableTab1");
    tableTab1.style.height = viewSize.height - 49 + "px";//87 82
    editgrid.setHeight(viewSize.height - 50);
    editgrid.setWidth(viewSize.width - 270);
});

function treeClick(node, e) {
    document.getElementById("checkedgroupid").value = node.id;
    document.getElementById("a0000").value="";
    radow.doEvent('a01Grid.dogridquery');
    radow.doEvent('a02Grid.dogridquery');
}

function blackcheck(row,col,col_id,grid_id){
	var grid=Ext.getCmp(grid_id);
	record=grid.store.getAt(row);
	if(record.get("blackcheck")==true){
		radow.doEvent('updateHHMD',record.get("a0200_2")+"##"+"insert"+"##"+"1");
	}else{
		radow.doEvent('updateHHMD',record.get("a0200_2")+"##"+"delete"+"##"+"1");
	}
}

function graycheck(row,col,col_id,grid_id){
	var grid=Ext.getCmp(grid_id);
	record=grid.store.getAt(row);
	if(record.get("graycheck")==true){
		radow.doEvent('updateHHMD',record.get("a0200_2")+"##"+"insert"+"##"+"2");
	}else{
		radow.doEvent('updateHHMD',record.get("a0200_2")+"##"+"delete"+"##"+"2");
	}
}

</script>


