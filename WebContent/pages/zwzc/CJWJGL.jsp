<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<style>
.uploadify{
position: absolute;
left: 0px;
top: 0px;
}


</style>
<script  type="text/javascript">

</script>


<odin:toolBar property="btnToolBarET" >
	
	<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;个人材料</h1>" ></odin:textForToolBar>
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:fill />
	<odin:buttonForToolBar text="上传材料" icon="images/add.gif" id="addBtnET"  handler="upload"  isLast="true"/>
</odin:toolBar>
<odin:toolBar property="btnToolBarET1" >
	
	<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;单位材料</h1>" ></odin:textForToolBar>
	<%-- <odin:buttonForToolBar text="删除" icon="images/delete.gif" id="delBtnET" handler="delet" /> --%>
	<odin:fill />
	<odin:buttonForToolBar text="修改材料" icon="image/icon021a6.gif" id="updateBtnET1"  handler="update1"/>
	<odin:buttonForToolBar text="上传材料" icon="images/add.gif" id="addBtnET1"  handler="upload1"  isLast="true"/>
</odin:toolBar>
<odin:hidden property="a0000"/>
<table width='100%' style="overflow:hidden">
	<tr>
		<td>
			<table width='100%' style="overflow-y: auto; overflow-x:hidden;"><tr>
			 	<td  valign="top" rowspan='2'>
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
			<td width="55%" height="670" rowspan='2' >
				<odin:editgrid2 property="ryGrid" hasRightMenu="false"  bbarId="pageToolBar" width="590" height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="cl01" />
						<odin:gridDataCol name="cl02" />
						<odin:gridDataCol name="cl03" />
						<odin:gridDataCol name="cl04" />
						<odin:gridDataCol name="cl05" />
						<odin:gridDataCol name="cl06" />
						<odin:gridDataCol name="cl07" />
						<odin:gridDataCol name="cl08" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="a0000"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="a0101" width="20" header="姓名" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="a0107" width="25" header="出生年月" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="a0192a" width="90" header="职务全称" align="left" editor="text"  edited="false"   />
						<odin:gridEditColumn2 dataIndex="cl01" width="20" header="政治素<br/>质考核" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl02" width="20" header="干部提<br/>任考察" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl03" width="20" header="平时考<br/>核表现" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl04" width="20" header="年度<br/>考核" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl05" width="20" header="民主<br/>生活会" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl06" width="20" header="整改<br/>清单" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl07" width="20" header="心里话" align="center" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="cl08" width="20" header="其他" align="center" editor="text"  edited="false" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
			</td>
			<td width="35%" >
				<odin:editgrid2 property="clGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET"
				width="590" height="300" pageSize="10" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="wj00" />
						<odin:gridDataCol name="year" />
						<odin:gridDataCol name="wj03" />
						<odin:gridDataCol name="wj02" />
						<odin:gridDataCol name="wj05" />
						<odin:gridDataCol name="delete" isLast="true"/>		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="wj00"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="year" width="40" header="年份" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj02" width="80" header="文件类型" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj03" width="180" header="文件名" align="center" edited="false"  editor="text" renderer="file" />
						<odin:gridEditColumn2 width="30" header="操作" dataIndex="delete" editor="text" align="center"  edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
				
			</td>
			</tr>
			<tr>
				<td>
					<odin:editgrid2 property="dwGrid" hasRightMenu="false" autoFill="true" bbarId="pageToolBar" pageSize="20" topBarId="btnToolBarET1"
				width="590" height="300" pageSize="10" isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="wj00" />
						<odin:gridDataCol name="year" />
						<odin:gridDataCol name="wj03" />
						<odin:gridDataCol name="wj02" />
						<odin:gridDataCol name="wj05" />
						<odin:gridDataCol name="delete" isLast="true"/>		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="wj00"  header="id" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="year" width="40" header="年份" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj02" width="80" header="文件类型" align="center" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="wj03" width="180" header="文件名" align="center" edited="false"  editor="text" renderer="file" />
						<odin:gridEditColumn2 width="30" header="操作" dataIndex="delete" editor="text" align="center"  edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
				</td>
			</tr>
			</table>
		
		</td>
	</tr>
</table>

<odin:hidden property="rmbs"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="train_id"/>


<script type="text/javascript">
<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();

%>
//删除行
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var wj00 = record.data.wj00;
	return "<a href=\"javascript:deleteRow2(&quot;"+wj00+"&quot;)\">删除</a>";
}
function deleteRow2(wj00){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',wj00);
		}else{
			return;
		}		
	});	
}

Ext.onReady(function() {
	var pgrid = Ext.getCmp('ryGrid')
	var bbar = pgrid.getTopToolbar();
 	 bbar.insertButton(1,[
							createSel2()
						]);
	
	
	
	hideWin();
	
	//页面调整

	 Ext.getCmp('ryGrid').setHeight((Ext.getBody().getViewSize().height-$h.pos(document.getElementById('forView_ryGrid')).top-4)*0.99);
	 Ext.getCmp('clGrid').setHeight(Ext.getCmp('ryGrid').getHeight()/2);
	 Ext.getCmp('dwGrid').setHeight(Ext.getCmp('clGrid').getHeight());

	 
});




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
    var editgrid = Ext.getCmp('rygrid');

    var resizeobj = Ext.getCmp('tab');
    resizeobj.setHeight(viewSize.height - 19);//34 - 29
    var tableTab1 = document.getElementById("tableTab1");
    tableTab1.style.height = viewSize.height - 49 + "px";//87 82
    editgrid.setHeight(viewSize.height - 50);
    editgrid.setWidth(viewSize.width - 270);
    

});

function treeClick(node, e) {
    document.getElementById("checkedgroupid").value = node.id;
    radow.doEvent('ryGrid.dogridquery');
    radow.doEvent('dwGrid.dogridquery');
    document.getElementById("a0000").value ='';
    document.getElementById("train_id").value ='';
    radow.doEvent('clGrid.dogridquery');
}


//来文原件
function file(value, params, rs, rowIndex, colIndex, ds){
	var wj05 = rs.get('wj05');
	var name = rs.get('wj03');
	var url=wj05.replace(/\\/g,"/");
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloads('" +url +"')\">"+name+"</a>";
	} 
	

} 
function downloads(url){
	window.location="YearCheckServlet?method=YearCheckFile&filePath="+encodeURI(encodeURI(url));
}

//上传文件
function upload() {
	radow.doEvent("insert");
}

//上传文件
function upload1() {
	radow.doEvent("insert1");
}


//修改班次
function update1(){
	var a= document.getElementById("train_id").value ;
	radow.doEvent("update1");
}
</script>


