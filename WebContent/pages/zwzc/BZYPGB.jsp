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
               			<odin:textEdit property="tbfzsj" label="��θ�ְʱ��" maxlength="8"  ></odin:textEdit>
						<td align="right">&nbsp;</td>
						<odin:textEdit property="tbzzsj" label="�����ְʱ��" maxlength="8"></odin:textEdit>
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
                            <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="clean" handler="clean"></odin:button>
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
                            <odin:editgrid2 property="editgrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
                                <odin:gridJsonDataModel >
                                    <odin:gridDataCol name="checked"/>
                                    <odin:gridDataCol name="a0000"/>
                                    <odin:gridDataCol name="a0101"/>
                                    <odin:gridDataCol name="b0111"/>
                                    <odin:gridDataCol name="a0104"/>
                                    <odin:gridDataCol name="a0117"/>
                                    <odin:gridDataCol name="a0107"/>
                                    <odin:gridDataCol name="a0192a"/>
                                    <odin:gridDataCol name="id"/>
                                    <odin:gridDataCol name="tbrs"/>
                                    <odin:gridDataCol name="tbfzsj"/>
                                    <odin:gridDataCol name="tbzzsj" isLast="true"/>
                                </odin:gridJsonDataModel>
                                <odin:gridColumnModel>
                                    <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                                    <odin:gridEditColumn2 locked="true" header="selectall" width="40" editor="checkbox" dataIndex="checked" edited="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0000" width="50" header="��Աid" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="id" width="50" header="����ίԱ����" editor="text" edited="false" align="center" hidden="true"/>
                                    <odin:gridEditColumn2 dataIndex="a0101" width="150" header="����" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="a0104" width="80" header="�Ա�" editor="select" edited="false" align="center" codeType="GB2261"/>
                                    <odin:gridEditColumn2 dataIndex="a0117" width="80" header="����" editor="select" edited="false" align="center" codeType="GB3304"/>
									<odin:gridEditColumn2 dataIndex="a0107" width="140" header="��������" editor="text" edited="false" align="center" renderer="formatCsny"/>
									<odin:gridEditColumn2 dataIndex="a0192a" width="300" header="����ְ��" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="tbrs" width="100" header="�������������" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="tbfzsj" width="100" header="��θ�ְʱ��" editor="text" edited="false" align="center"/>
                                    <odin:gridEditColumn2 dataIndex="tbzzsj" width="100" header="�����ְʱ��" editor="text" edited="false" isLast="true" align="center"/>
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
        document.getElementById("checkedgroupid").value = node.id;
        var checkedgroupid = document.getElementById('checkedgroupid').value;
        
        var a=checkedgroupid.indexOf("001.001.002");
        var b=checkedgroupid.indexOf("001.001.003");
        var c=checkedgroupid.indexOf("001.001.004");
        var d=checkedgroupid.indexOf("001.001.001");
        var a1=checkedgroupid.indexOf("001.001.002.01O");
    	var a2=checkedgroupid.indexOf("001.001.002.01Q");
    	var a3=checkedgroupid.indexOf("001.001.002.02O");
    	if(d==0){
    		$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
    		return;
    	}
        if(a==0){
        	if(checkedgroupid.length!=15){
        		if(a1<0 && a2<0 && a3<0){
        			$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
        			return;
        		}else if(checkedgroupid=='001.001.002.01O.005' || checkedgroupid=='001.001.002.01Q.003' || checkedgroupid=='001.001.002.02O.001'){
        			$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
        			return;
        		}else{
        			//document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.BZYP';
        			/* $h.openWin('BZYP','pages.fxyp.BZYP','��λ��������',1000,650,null,ctxPath,null,
        					{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
        					PersonQuery();
        		}	
        	}else{
        		if(a1>=0 || a2>=0 || a3>=0){
        			$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
        			return;
        		}else{
        			//document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.BZYP';
        	/* 		$h.openWin('BZYP','pages.fxyp.BZYP','��λ��������',1000,650,null,ctxPath,null,
        					{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
        					PersonQuery();
        		}
        	}	
        }
        if(b==0){
        	if(checkedgroupid.length!=15){
        		$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
    			return;
        	}else{
        		//document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.GQBZYP';
    /*     		$h.openWin('GQBZYP','pages.fxyp.GQBZYP','�����У��������',1000,650,null,ctxPath,null,
        				{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
        				PersonQuery();
        	}
        }
        if(c==0){
        	if(checkedgroupid.length!=15){
        		$h.alert('ϵͳ��ʾ','��ѡ����ȷ���ӣ�');
    			return;
        	}else{
        		//document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.QXSBZYP';
    /*     		$h.openWin('QXSBZYP','pages.fxyp.QXSBZYP','�����а�������',1000,650,null,ctxPath,null,
        				{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
        				PersonQuery();
        	}
        }
        
        
    }

    function reload() {
        radow.doEvent('editgrid.dogridquery');
    }

    function PersonQuery() {
    	document.getElementById("searching").value = '1';
        radow.doEvent("editgrid.dogridquery");
    }

    function personAdd() {
    	var checkedgroupid = document.getElementById('checkedgroupid').value;
        $h.openWin('BZYPGBAdd', 'pages.zwzc.BZYPGBAdd', '�����ɲ� ', 750, 250, null, ctxPath, null, {
            maximizable: false,
            resizable: false,
            closeAction: 'close',
            checkedgroupid:checkedgroupid
        })
    }
    
    function clean() {
    	document.getElementById("a0101").value ='';
    	document.getElementById("tbfzsj").value ='';
    	document.getElementById("tbzzsj").value ='';
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
</script>
