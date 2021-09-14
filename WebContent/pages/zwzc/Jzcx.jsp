<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<style>
    .x-form-item2 tr td .x-form-item {
        margin-bottom: 0px !important;
    }
    .yellow{
    	background: #fff68f;
    }

</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>




<table cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;" >
<tr>
<td  valign="top" width="270">
	<table cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;">
                <tr>
                    <td valign="top">
                        <odin:tab id="tab" width="270" height="498">
                            <odin:tabModel>
                                <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                            </odin:tabModel>
                            <odin:tabCont itemIndex="tab1" className="tab">
                                <table id="tableTab1" style="height: 465px;border-collapse:collapse;">
                                    <tr>
                                        <td colspan="2">
                                            <div id="tree-div" style="overflow: auto; height: 100%; width: 100%; border: 2px solid #c3daf9;"></div>
                                            <odin:hidden property="checkedgroupid"/>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                            <input type="checkbox" checked id="isContain"/>
                                            <span style="font-size: 13px">包含下级</span>
                                        </td>
                                    </tr>
                                </table>
                            </odin:tabCont>
                        </odin:tab>
                    </td>
                </tr>
            </table>
</td>
<td valign="top" >
 <div id="girdDiv" style="width: 100%;margin:0;">
  <table>
		<tr>
			<odin:textEdit property="a0101"  width="160" label="姓名"  />
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:textEdit property="stname"  width="160" label="社团名称" />
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:textEdit property="stjob"  width="160" label="社团职务" />
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:select2 property="stnature" label="社团性质" data="['社团','社团'],['基金会','基金会'],['行业协会','行业协会'],['其他','其他']" ></odin:select2>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:select2 property="status" label="状&nbsp;&nbsp;态&nbsp;" data="['1','在任'],['0','已免']"  ></odin:select2>
			<td>&nbsp;&nbsp;&nbsp;</td>
		</tr>
		<tr>
			<odin:textEdit property="approvaldate" label="批准日期" maxlength="8"  ></odin:textEdit>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:textEdit property="startdate"  label="起始日期" maxlength="8"  ></odin:textEdit>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:textEdit property="closingdate"  label="终止日期" maxlength="8"></odin:textEdit>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:textEdit property="sessionsnum"  width="160" label="担任届数"  />
			<td>&nbsp;&nbsp;&nbsp;</td>
			<odin:select2 property="salary" label="是否取薪" data="['1','是'],['0','否']" ></odin:select2>
			<td>&nbsp;&nbsp;&nbsp;</td>
		</tr>
		</table>
		<table  >
		<tr>
			<td>&nbsp;&nbsp;&nbsp;<td>
			<td ><odin:button text="清&nbsp;&nbsp;空" property="doClear" handler="doClear"></odin:button></td>
			<td>&nbsp;&nbsp;&nbsp;<td>
			<td ><odin:button text="查&nbsp;&nbsp;询" property="doQuery" handler="doQuery"></odin:button></td>
		</tr>	
<!-- <div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;"> -->	
		</table>
		<table>
		<tr>
			<td >	
				<odin:editgrid2 property="editgrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="st00" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0117" />
						<odin:gridDataCol name="a0107" />
	<%-- 				<odin:gridDataCol name="a0111a" /> --%>
	<%-- 				<odin:gridDataCol name="a0288" />
						<odin:gridDataCol name="a0140" /> --%>
	<%-- 				<odin:gridDataCol name="age" /> --%>
						<odin:gridDataCol name="stname" />
						<odin:gridDataCol name="stjob" />
						<odin:gridDataCol name="stnature" />
						<odin:gridDataCol name="status" />
						<odin:gridDataCol name="approvaldate" />
						<odin:gridDataCol name="startdate" />
						<odin:gridDataCol name="closingdate" />
						<odin:gridDataCol name="sessionsnum" />
						<odin:gridDataCol name="salary" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
						<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
						<odin:gridEditColumn2 header="人员主键" align="center" edited="false" width="20" dataIndex="a0000" editor="text" hidden="true" edited="false" />
						<odin:gridEditColumn2 dataIndex="st00" width="50" header="干部兼职主键" editor="text"  align="center" hidden="true" edited="false" />
						<odin:gridEditColumn2 header="姓名" align="center" edited="false" width="80" dataIndex="a0101" editor="text" edited="false"  />	
						<odin:gridEditColumn2 header="现任职务" align="center" edited="false" width="200" dataIndex="a0192a" editor="text"  edited="false" />
						<odin:gridEditColumn2 header="性别" align="center" edited="false" width="40" dataIndex="a0104" editor="select"  codeType="GB2261" edited="false" />
						<odin:gridEditColumn2 header="民族" align="center" edited="false" width="40" dataIndex="a0117" editor="select" codeType="GB3304" edited="false" />
	<%-- 				<odin:gridEditColumn header="籍贯" align="center" edited="false" width="80" dataIndex="a0111a" editor="text" /> --%>
						<odin:gridEditColumn2 header="出生年月" align="center" edited="false" width="100" dataIndex="a0107" editor="text" renderer="formatCsny" edited="false" />
	<%-- 				<odin:gridEditColumn header="年龄" align="center" edited="false" width="40" dataIndex="age" editor="text" /> --%>
						<odin:gridEditColumn2 header="社团名称" align="center" edited="false" width="180" dataIndex="stname" editor="text"  edited="false" />
						<odin:gridEditColumn2 header="担任社团职务" align="center" edited="false" width="120" dataIndex="stjob" editor="text"  edited="false" />
						<odin:gridEditColumn2 header="社团性质" align="center" edited="false" width="80" dataIndex="stnature" editor="select" selectData="['社团','社团'],['基金会','基金会'],['行业协会','行业协会'],['其他','其他']" edited="false"  />
						<odin:gridEditColumn2 header="状态" align="center" edited="false" width="60" dataIndex="status" editor="select"  selectData="['1','在任'],['0','已免']" edited="false" />
						<odin:gridEditColumn2 header="批准日期" align="center" edited="false" width="100" dataIndex="approvaldate" editor="text"  edited="false" />
						<odin:gridEditColumn2 header="起始日期" align="center" edited="false" width="100" dataIndex="startdate" editor="text" edited="false"  />
						<odin:gridEditColumn2 header="终止日期" align="center" edited="false" width="100" dataIndex="closingdate" editor="text" edited="false"  />
						<odin:gridEditColumn2 header="届数" align="center" edited="false" width="80" dataIndex="sessionsnum" editor="text" edited="false"  />
						<odin:gridEditColumn2 header="是否取薪" align="center" edited="false" width="80" dataIndex="salary" editor="select" selectData="['1','是'],['0','否']" isLast="true" edited="false" />
					  </odin:gridColumnModel>
				   </odin:editgrid2>
				</td>
		   </tr>
		 </table>
		</div>
      </td>
   </tr>
</table>




<odin:hidden property="rmbs"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<script type="text/javascript">

function doQuery(){
	
	radow.doEvent('editgrid.dogridquery');
}

function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

Ext.onReady(function(){
	
	setColor();
	
});
function doClear(){
	document.getElementById('stname').value="";
	document.getElementById('stjob').value="";
	odin.setSelectValue('stnature',"");
	odin.setSelectValue('status',"");
	/* odin.setSelectValue('approvaldate',"");
	odin.setSelectValue('closingdate',""); */
	document.getElementById('approvaldate').value="";
	document.getElementById('closingdate').value="";
	document.getElementById('sessionsnum').value=""; 
	document.getElementById('a0101').value=""; 
	odin.setSelectValue('salary',"");
}


function setColor(){
	var grid = Ext.getCmp("editgrid");
	grid.getView().getRowClass = function(record, rowIndex, rowParams, store){
		if(record.data.jsgz == '0'){
			return 'yellow';
		}else{
			return '';
		}
    };
}


    var ctxPath = '<%= request.getContextPath() %>';
    var tree;
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
        editgrid.setHeight(viewSize.height - 100);
        editgrid.setWidth(viewSize.width - 300);
    });

    var callback = function (node) {
        if (node.hasChildNodes()) {
            node.eachChild(function (child) {
                child.expand();
            })
        }
    }

function formatCsny(val) {
    if (val.length === 6 || val.length === 8) {
        return val.substr(0, 4) + "." + val.substr(4, 2)
    } else {
        return val
    }
}
function treeClick(node, e) {
    document.getElementById("checkedgroupid").value = node.id;
    radow.doEvent('editgrid.dogridquery');
}
</script>