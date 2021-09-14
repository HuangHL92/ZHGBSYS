<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>



<table width='100%' style="overflow-y: auto; overflow-x:hidden;">
	<tr>
		<td  valign="top" width="270" >
               <odin:tab id="tab" width="270" height="670">
                      <odin:tabModel>
                             <odin:tabItem title="机构树" id="tab1" isLast="true"></odin:tabItem>
                      </odin:tabModel>
                           <odin:tabCont itemIndex="tab1" className="tab">
                                <table id="tableTab1" style="height: 465px;border-collapse:collapse;">
                                    <tr>
                                        <td colspan="2">
                                            <div id="tree-div" style="overflow-y: auto; overflow-x:hidden;height: 660; width: 100%; border: 2px solid #c3daf9;"></div>
                                            <odin:hidden property="checkedgroupid"/>
                                        </td>
                                    </tr>
                                    <!-- <tr>
                                        <td style="width: 190px; background-color: #cedff5;height: 30px;">
                                            <input type="checkbox" checked id="isContain"/>
                                            <span style="font-size: 13px">包含下级</span>
                                        </td>
                                    </tr> -->
                               </table>
                      </odin:tabCont>
                </odin:tab>
        </td>
        <td >
            <iframe name="myiframe" id="YPframe" src="" frameborder="0" align="left" width="1000" height="670" scrolling="no">
       		</iframe>
        </td>
    </tr>
</table>





<script type="text/javascript">
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
    var checkedgroupid = document.getElementById('checkedgroupid').value;
    
    var a=checkedgroupid.indexOf("001.001.002");
    var b=checkedgroupid.indexOf("001.001.003");
    var c=checkedgroupid.indexOf("001.001.004");
    var d=checkedgroupid.indexOf("001.001.001");
    var a1=checkedgroupid.indexOf("001.001.002.01O");
	var a2=checkedgroupid.indexOf("001.001.002.01Q");
	var a3=checkedgroupid.indexOf("001.001.002.02O");
	if(d==0){
		$h.alert('系统提示','请选择正确班子！');
		return;
	}
    if(a==0){
    	if(checkedgroupid.length!=15){
    		if(a1<0 && a2<0 && a3<0){
    			$h.alert('系统提示','请选择正确班子！');
    			return;
    		}else if(checkedgroupid=='001.001.002.01O.005' || checkedgroupid=='001.001.002.01Q.003' || checkedgroupid=='001.001.002.02O.001'){
    			$h.alert('系统提示','请选择正确班子！');
    			return;
    		}else{
    			document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.BZYP';
    			/* $h.openWin('BZYP','pages.fxyp.BZYP','单位班子研判',1000,650,null,ctxPath,null,
    					{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
    		}	
    	}else{
    		if(a1>=0 || a2>=0 || a3>=0){
    			$h.alert('系统提示','请选择正确班子！');
    			return;
    		}else{
    			document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.BZYP';
    	/* 		$h.openWin('BZYP','pages.fxyp.BZYP','单位班子研判',1000,650,null,ctxPath,null,
    					{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
    		}
    	}	
    }
    if(b==0){
    	if(checkedgroupid.length!=15){
    		$h.alert('系统提示','请选择正确班子！');
			return;
    	}else{
    		document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.GQBZYP';
/*     		$h.openWin('GQBZYP','pages.fxyp.GQBZYP','国企高校班子研判',1000,650,null,ctxPath,null,
    				{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
    	}
    }
    if(c==0){
    	if(checkedgroupid.length!=15){
    		$h.alert('系统提示','请选择正确班子！');
			return;
    	}else{
    		document.getElementById("YPframe").src='/hzb/radowAction.do?method=doEvent&pageModel=pages.fxyp.QXSBZYP';
/*     		$h.openWin('QXSBZYP','pages.fxyp.QXSBZYP','区县市班子研判',1000,650,null,ctxPath,null,
    				{checkedgroupid:checkedgroupid,scroll:"scroll:yes;"},true); */
    	}
    }
    
    
}

</script>