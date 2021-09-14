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
<table cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;">
    <tr>
        <td width="270" id="td1">
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
        <td valign="top">
            <div id="girdDiv" style="width: 100%;margin:0;">
            	<table>
            		<tr>
             			<odin:textEdit property="a0101"  label="姓名"  />
             			<td align="right">&nbsp;</td>
             			<odin:NewDateEditTag property="a0107" label="出生年月"  ></odin:NewDateEditTag>
						<td align="right">&nbsp;</td>
               			<odin:textEdit property="a0192a" label="现任职务"  ></odin:textEdit>
						<td align="right">&nbsp;</td>	
						<odin:NewDateEditTag property="a0192f" label="任职时间" ></odin:NewDateEditTag>
						<td align="right">&nbsp;</td>
						<odin:hidden property="searching"/>
              		 </tr>
                </table>
                <table class="x-form-item2" style="background-color: rgb(209,223,245); border-left: 1px solid rgb(153,187,232);
	border-top: 1px solid rgb(153,187,232); border-right: 1px solid rgb(153,187,232);top: 0px;width: 100%;">
                    <tr>
                        <!-- <td height="20px">
                            <div style="width: 170px"></div>
                        </td> -->
                        <%-- <td align="right" style="width:40.7%;">
                            <odin:button text="&nbsp;同屏比较&nbsp;" property="tpbj"></odin:button>
                        </td> --%>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;导&nbsp;&nbsp;出&nbsp;" property="outExec" handler="outExec"></odin:button>
                        </td> --%>
                       	 <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;刷&nbsp;&nbsp;新&nbsp;" property="clean" handler="clean"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;查&nbsp;&nbsp;询&nbsp;" property="personQuery" handler="PersonQuery"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;新&nbsp;&nbsp;增&nbsp;" property="personAdd" handler="personAdd"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;删&nbsp;&nbsp;除&nbsp;" property="gbjyDelete"></odin:button>
                        </td>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;备&nbsp;&nbsp;注&nbsp;" property="gbjyAddBZ" handler="gbjyAddBZ"></odin:button>
                        </td> --%>
                        <td align="right">&nbsp;</td>
                    </tr>
                </table>
                <table>
                    <tr>
                        <td>
                            <odin:editgrid2 property="editgrid" hasRightMenu="false" autoFill="false"  title=""  pageSize="20" bbarId="pageToolBar" url="/">
                                <odin:gridJsonDataModel >
                                    <odin:gridDataCol name="checked"/>
                                    <odin:gridDataCol name="js00"/>
                                    <odin:gridDataCol name="a0000"/>
                                    <odin:gridDataCol name="a0101"/>
                                    <odin:gridDataCol name="a0107"/>
                                    <odin:gridDataCol name="srzw"/>
                                    <odin:gridDataCol name="a0192a"/>
                                    <odin:gridDataCol name="xrzw"/>
                                    <odin:gridDataCol name="a0192f"/>
                                    <odin:gridDataCol name="js01"/>
                                    <odin:gridDataCol name="js02a"/>
                                    <odin:gridDataCol name="js02b"/>
                                    <odin:gridDataCol name="js02c"/>
                                    <odin:gridDataCol name="js03a"/>
                                    <odin:gridDataCol name="js03b"/>
                                    <odin:gridDataCol name="js03c"/>
                                    <odin:gridDataCol name="js04a"/>
                                    <odin:gridDataCol name="js04b"/>
                                    <odin:gridDataCol name="js05a"/>
                                    <odin:gridDataCol name="js05b"/>
                                    <odin:gridDataCol name="js05c"/>
                                    <odin:gridDataCol name="js06a"/>
                                    <odin:gridDataCol name="js06b"/>
                                    <odin:gridDataCol name="js07"/>
                                    <odin:gridDataCol name="js08"/>
                                    <odin:gridDataCol name="js09a"/>
                                    <odin:gridDataCol name="js09b"/>
                                    <odin:gridDataCol name="js10"/>
                                    <odin:gridDataCol name="js11"/>
                                    <odin:gridDataCol name="js12"/>
                                    <odin:gridDataCol name="js13"/>
                                    <odin:gridDataCol name="js14"/>
                                    <odin:gridDataCol name="js15"/>
                                    <odin:gridDataCol name="js16"/>
                                    <odin:gridDataCol name="js17"/>
                                    <odin:gridDataCol name="js18"/>
                                    <odin:gridDataCol name="js19a"/>
                                    <odin:gridDataCol name="js19b"/>
                                    <odin:gridDataCol name="js20"/>
                                    <odin:gridDataCol name="js21"/>
                                    <odin:gridDataCol name="js22" isLast="true"/>
                                </odin:gridJsonDataModel>
                                <odin:gridColumnModel>
                                    <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                                    <odin:gridEditColumn2 locked="true" header="selectall" width="40" editor="checkbox" dataIndex="checked" edited="true"/>
                                    <odin:gridEditColumn2 dataIndex="js00" width="50" header="主键" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0000" width="50" header="人员id" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0101" width="80" header="姓名" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="a0107" width="80" header="出生年月" editor="text" edited="true"  editorId="asd1" align="center" renderer="dateRenderer" />
                                    <odin:gridEditColumn2 dataIndex="srzw" width="250" header="时任职务" editor="text" edited="true"  editorId="asd2"  align="center" />
                                    <odin:gridEditColumn2 dataIndex="a0192a" width="250" header="现任职务" editor="text" edited="true"  editorId="asd3"  align="center" />
                                   <%--  <odin:gridEditColumn2 dataIndex="xrzw" width="250" header="时任职务【现任职务】" editor="text" edited="false" align="center" /> --%>
                                    <odin:gridEditColumn2 dataIndex="a0192f" width="80" header="任现职时间" editor="text" edited="true"  editorId="asd4"  align="center" renderer="dateRenderer" />
                                    <odin:gridEditColumn2 dataIndex="js01" width="150" header="破格提拔理由" editor="text" edited="true"  editorId="asd5"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02a" width="50" header="得票数<br/>(会议)" editor="text" edited="true"  editorId="asd6"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02b" width="50" header="总人数<br/>(会议)" editor="text" edited="true"  editorId="asd7"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02c" width="50" header="名次<br/>(会议)" editor="text" edited="true"  editorId="asd8"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03a" width="50" header="得票数<br/>(谈话)" editor="text" edited="true"  editorId="asd9"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03b" width="50" header="总人数<br/>(谈话)" editor="text" edited="true"  editorId="asd10"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03c" width="50" header="名次<br/>(谈话)" editor="text" edited="true"  editorId="asd11"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js04a" width="50" header="是否<br/>进行<br/>考察" editor="select" selectData="['1', '是'],['0', '否']" edited="true"  editorId="asd12"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js04b" width="100" header="有无不得<br/>列为考察<br/>对象的情形" editor="select" selectData="['1', '有'],['0', '无']" edited="true"  editorId="asd13"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05a" width="80" header="党组<br/>（党委）<br/>讨论应参会<br/>人数" editor="text" edited="true"  editorId="asd14"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05b" width="50" header="实际参<br/>会人数" editor="text" edited="true"  editorId="asd15"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05c" width="50" header="参会同<br/>意人数" editor="text" edited="true"  editorId="asd16"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js06a" width="80" header="任职公示<br/>没有反映" selectData="['1', '√'],['0', '○']" editor="select" edited="true"  editorId="asd17"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js06b" width="80" header="有反映但不<br/>影响使用" selectData="['1', '√'],['0', '○']" editor="select" edited="true"  editorId="asd18"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js07" width="80" header="是否征求<br/>纪检监察<br/>机关意见" selectData="['1', '是'],['0', '否']" editor="select" edited="true"  editorId="asd19"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js08" width="80" header="个人有关<br/>事项报告<br/>查核是否<br/>一致" selectData="['1', '是'],['0', '否']" editor="select" edited="true"  editorId="asd20"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js09a" width="80" header="干部档案<br/>是否进行<br/>审核" selectData="['1', '是'],['0', '否']" editor="select" edited="true"  editorId="asd21"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js09b" width="80" header="“三龄两历<br/>一身份”<br/>是否存在<br/>问题" selectData="['1', '是'],['0', '否']" edited="true"  editorId="asd22" editor="select" align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js10" width="80" header="会议推荐<br/>时间" editor="text" edited="true"  editorId="asd23"  align="center"  renderer="dateRenderer1"/>
                                    <odin:gridEditColumn2 dataIndex="js11" width="80" header="谈话推荐<br/>时间" editor="text" edited="true"  editorId="asd24"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js12" width="80" header="考察谈话<br/>时间" editor="text" edited="true"  editorId="asd25"  align="center"   renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js13" width="80" header="征求纪检<br/>监察意见<br/>时间" editor="text" edited="true"  editorId="asd26"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js14" width="80" header="纪检监察<br/>部门回复<br/>时间" editor="text" edited="true"  editorId="asd27"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js15" width="80" header="个人事项<br/>查询委托<br/>时间" editor="text" edited="true"  editorId="asd28"  align="center"  renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js16" width="80" header="个人事项<br/>查核处理<br/>时间" editor="text" edited="true"  editorId="asd29"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js17" width="80" header="三龄二历<br/>一身份<br/>核定时间" editor="text" edited="true"  editorId="asd30"  align="center"   renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js18" width="80" header="党组会<br/>讨论时间" editor="text" edited="true"  editorId="asd31"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js19a" width="80" header="任前公示<br/>时间发布<br/>公示通知<br/>时间" editor="text" edited="true"  editorId="asd32"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js19b" width="150" header="公示期限" editor="text" edited="true"  editorId="asd33"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js20" width="100" header="发文时间<br/>（落款时间）" editor="text" edited="true"  editorId="asd34"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js21" width="80" header="责任处室" editor="text" edited="true"  editorId="asd35"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js22" width="100" header="提拔类型" selectData="['1','提拔'],['2','转重'],['3','纪委副局长级转出'],['4','晋升巡视员、副巡视员'],['5','副局级组织员'],['6','晋升职级公务员']" editor="select" edited="true"  editorId="asd36"  align="center"  isLast="true"  />
                                </odin:gridColumnModel>
                            </odin:editgrid2>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
Ext.onReady(function(){
	
	setColor();
	
});

function dateRenderer(val) {
	if(val!=null && val!=''){
		var length=val.length;
	    if (val.length === 6 || val.length === 8) {
	        return val.substr(0, 4) + "." + val.substr(4, 2);
	    } else {
	        return val;
	    }
	}else{
		return val;
	}
}
function dateRenderer1(val) {
	if(val!=null && val!=''){
		var length=val.length;
	    if (val.length === 8) {
	        return val.substr(0, 4) + "." + val.substr(4, 2)+ "." + val.substr(6, 2);
	    } else {
	        return val;
	    }
	}else{
		return val;
	}
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
        editgrid.setHeight(viewSize.height - 90);
        editgrid.setWidth(viewSize.width - 300);
    });

    var callback = function (node) {
        if (node.hasChildNodes()) {
            node.eachChild(function (child) {
                child.expand();
            })
        }
    }

    function treeClick(node, e) {
    	document.getElementById("searching").value = '0';
        document.getElementById("checkedgroupid").value = node.id;
        radow.doEvent("editgrid.dogridquery");
    }



    function PersonQuery() {
    	document.getElementById("searching").value = '1';
        radow.doEvent("editgrid.dogridquery");
    }

    function personAdd() {
        $h.openWin('JsmxAdd', 'pages.zwzc.JsmxAdd', '新增纪实明细 ', 720, 250, null, ctxPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close'
        })
    }
    
    function clean() {
    	document.getElementById("a0101").value ='';
    	document.getElementById("a0107").value ='';
    	document.getElementById("a0107_1").value ='';
    	document.getElementById("a0192a").value ='';
    	document.getElementById("a0192f").value ='';
    	document.getElementById("a0192f_1").value ='';
    	document.getElementById("searching").value = '1';
    	radow.doEvent("editgrid.dogridquery");
    }

    function formatCsny(val) {
        if (val.length === 6 || val.length === 8) {
            return val.substr(0, 4) + "." + val.substr(4, 2)
        } else {
            return val
        }
    }
/*     function outExec() {
        radow.doEvent("OutExec");
    }
    function outExcel(path) {
        var url = ctxPath + '/PublishFileServlet?method=Jbgz&param=' + path;
        window.location.href = url;
    } */
    
    function clearSelected() {
    	//列表
		var gridId = "editgrid";
		var fieldName = "checked";
		var store = odin.ext.getCmp(gridId).store;
		var length = store.getCount();
		for (var i = 0; i < length; i++) {
			store.getAt(i).set(fieldName, false);
		}
		//store.reload()
    }
    
    
    
function gbjyAddBZ(){
	var grid = odin.ext.getCmp('editgrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var a0000s = "";

	
	var flag=0;
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.checked == true){
			a0000s=a0000s+rowData.data.a0000+",";
			flag++;
		}
	} 
	if(a0000s == null || a0000s == ""||flag>1){
		$h.alert('系统提示：','请选择一条记录！',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	alert(a0000)
	//$h.openWin('khpjWin','pages.gbwh.KHPJ','考核评价',1100,600,a0000,g_contextpath,null,{maximizable:false,resizable:false});
	
}
</script>
