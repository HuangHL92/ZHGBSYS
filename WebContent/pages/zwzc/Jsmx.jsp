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
                                <odin:tabItem title="������" id="tab1" isLast="true"></odin:tabItem>
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
                                            <span style="font-size: 13px">�����¼�</span>
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
             			<odin:textEdit property="a0101"  label="����"  />
             			<td align="right">&nbsp;</td>
             			<odin:NewDateEditTag property="a0107" label="��������"  ></odin:NewDateEditTag>
						<td align="right">&nbsp;</td>
               			<odin:textEdit property="a0192a" label="����ְ��"  ></odin:textEdit>
						<td align="right">&nbsp;</td>	
						<odin:NewDateEditTag property="a0192f" label="��ְʱ��" ></odin:NewDateEditTag>
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
                            <odin:button text="&nbsp;ͬ���Ƚ�&nbsp;" property="tpbj"></odin:button>
                        </td> --%>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="outExec" handler="outExec"></odin:button>
                        </td> --%>
                       	 <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;ˢ&nbsp;&nbsp;��&nbsp;" property="clean" handler="clean"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;ѯ&nbsp;" property="personQuery" handler="PersonQuery"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="personAdd" handler="personAdd"></odin:button>
                        </td>
                        <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;ɾ&nbsp;&nbsp;��&nbsp;" property="gbjyDelete"></odin:button>
                        </td>
                       <%--  <td align="right" style="width:5%;">
                            <odin:button text="&nbsp;��&nbsp;&nbsp;ע&nbsp;" property="gbjyAddBZ" handler="gbjyAddBZ"></odin:button>
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
                                    <odin:gridEditColumn2 dataIndex="js00" width="50" header="����" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0000" width="50" header="��Աid" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0101" width="80" header="����" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="a0107" width="80" header="��������" editor="text" edited="true"  editorId="asd1" align="center" renderer="dateRenderer" />
                                    <odin:gridEditColumn2 dataIndex="srzw" width="250" header="ʱ��ְ��" editor="text" edited="true"  editorId="asd2"  align="center" />
                                    <odin:gridEditColumn2 dataIndex="a0192a" width="250" header="����ְ��" editor="text" edited="true"  editorId="asd3"  align="center" />
                                   <%--  <odin:gridEditColumn2 dataIndex="xrzw" width="250" header="ʱ��ְ������ְ��" editor="text" edited="false" align="center" /> --%>
                                    <odin:gridEditColumn2 dataIndex="a0192f" width="80" header="����ְʱ��" editor="text" edited="true"  editorId="asd4"  align="center" renderer="dateRenderer" />
                                    <odin:gridEditColumn2 dataIndex="js01" width="150" header="�Ƹ��������" editor="text" edited="true"  editorId="asd5"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02a" width="50" header="��Ʊ��<br/>(����)" editor="text" edited="true"  editorId="asd6"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02b" width="50" header="������<br/>(����)" editor="text" edited="true"  editorId="asd7"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js02c" width="50" header="����<br/>(����)" editor="text" edited="true"  editorId="asd8"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03a" width="50" header="��Ʊ��<br/>(̸��)" editor="text" edited="true"  editorId="asd9"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03b" width="50" header="������<br/>(̸��)" editor="text" edited="true"  editorId="asd10"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js03c" width="50" header="����<br/>(̸��)" editor="text" edited="true"  editorId="asd11"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js04a" width="50" header="�Ƿ�<br/>����<br/>����" editor="select" selectData="['1', '��'],['0', '��']" edited="true"  editorId="asd12"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js04b" width="100" header="���޲���<br/>��Ϊ����<br/>���������" editor="select" selectData="['1', '��'],['0', '��']" edited="true"  editorId="asd13"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05a" width="80" header="����<br/>����ί��<br/>����Ӧ�λ�<br/>����" editor="text" edited="true"  editorId="asd14"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05b" width="50" header="ʵ�ʲ�<br/>������" editor="text" edited="true"  editorId="asd15"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js05c" width="50" header="�λ�ͬ<br/>������" editor="text" edited="true"  editorId="asd16"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js06a" width="80" header="��ְ��ʾ<br/>û�з�ӳ" selectData="['1', '��'],['0', '��']" editor="select" edited="true"  editorId="asd17"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js06b" width="80" header="�з�ӳ����<br/>Ӱ��ʹ��" selectData="['1', '��'],['0', '��']" editor="select" edited="true"  editorId="asd18"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js07" width="80" header="�Ƿ�����<br/>�ͼ���<br/>�������" selectData="['1', '��'],['0', '��']" editor="select" edited="true"  editorId="asd19"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js08" width="80" header="�����й�<br/>�����<br/>����Ƿ�<br/>һ��" selectData="['1', '��'],['0', '��']" editor="select" edited="true"  editorId="asd20"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js09a" width="80" header="�ɲ�����<br/>�Ƿ����<br/>���" selectData="['1', '��'],['0', '��']" editor="select" edited="true"  editorId="asd21"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js09b" width="80" header="����������<br/>һ��ݡ�<br/>�Ƿ����<br/>����" selectData="['1', '��'],['0', '��']" edited="true"  editorId="asd22" editor="select" align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js10" width="80" header="�����Ƽ�<br/>ʱ��" editor="text" edited="true"  editorId="asd23"  align="center"  renderer="dateRenderer1"/>
                                    <odin:gridEditColumn2 dataIndex="js11" width="80" header="̸���Ƽ�<br/>ʱ��" editor="text" edited="true"  editorId="asd24"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js12" width="80" header="����̸��<br/>ʱ��" editor="text" edited="true"  editorId="asd25"  align="center"   renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js13" width="80" header="����ͼ�<br/>������<br/>ʱ��" editor="text" edited="true"  editorId="asd26"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js14" width="80" header="�ͼ���<br/>���Żظ�<br/>ʱ��" editor="text" edited="true"  editorId="asd27"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js15" width="80" header="��������<br/>��ѯί��<br/>ʱ��" editor="text" edited="true"  editorId="asd28"  align="center"  renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js16" width="80" header="��������<br/>��˴���<br/>ʱ��" editor="text" edited="true"  editorId="asd29"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js17" width="80" header="�������<br/>һ���<br/>�˶�ʱ��" editor="text" edited="true"  editorId="asd30"  align="center"   renderer="dateRenderer1" />
                                    <odin:gridEditColumn2 dataIndex="js18" width="80" header="�����<br/>����ʱ��" editor="text" edited="true"  editorId="asd31"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js19a" width="80" header="��ǰ��ʾ<br/>ʱ�䷢��<br/>��ʾ֪ͨ<br/>ʱ��" editor="text" edited="true"  editorId="asd32"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js19b" width="150" header="��ʾ����" editor="text" edited="true"  editorId="asd33"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js20" width="100" header="����ʱ��<br/>�����ʱ�䣩" editor="text" edited="true"  editorId="asd34"  align="center"  renderer="dateRenderer1"  />
                                    <odin:gridEditColumn2 dataIndex="js21" width="80" header="���δ���" editor="text" edited="true"  editorId="asd35"  align="center"  />
                                    <odin:gridEditColumn2 dataIndex="js22" width="100" header="�������" selectData="['1','���'],['2','ת��'],['3','��ί���ֳ���ת��'],['4','����Ѳ��Ա����Ѳ��Ա'],['5','���ּ���֯Ա'],['6','����ְ������Ա']" editor="select" edited="true"  editorId="asd36"  align="center"  isLast="true"  />
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
            el: 'tree-div',//Ŀ��div����
            split: false,
            width: 270,
            height: 600,
            minSize: 164,
            maxSize: 164,
            rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
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
        $h.openWin('JsmxAdd', 'pages.zwzc.JsmxAdd', '������ʵ��ϸ ', 720, 250, null, ctxPath, null, {
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
    	//�б�
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
		$h.alert('ϵͳ��ʾ��','��ѡ��һ����¼��',null,150);
		return;
	}
	var a0000 = a0000s.substring(0,a0000s.length-1);
	var g_contextpath = '<%=request.getContextPath()%>';
	alert(a0000)
	//$h.openWin('khpjWin','pages.gbwh.KHPJ','��������',1100,600,a0000,g_contextpath,null,{maximizable:false,resizable:false});
	
}
</script>
