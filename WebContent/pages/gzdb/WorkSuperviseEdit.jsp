 <%@ page language="java" contentType="text/html; charset=GBK"
         pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@ page import="com.insigma.siis.local.pagemodel.gzdb.MaptoBeanUtil" %>
<%
    String ctxPath = request.getContextPath();
%>

<script src="<%=ctxPath%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="<%=ctxPath%>/jwjc/js/underscore.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/jwjc/js/examine/common.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
 <script type="text/javascript">
        var contextpath = '<%= request.getContextPath() %>';
    </script>
<style>
    .td_width{
        width: 20px;
    }
    #tablelist{
        width: 100%;
        padding-left: 2px;
    }
    #perInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
</style>
<odin:toolBar property="btnToolBar2" applyTo="id">
    <odin:fill/>
    <odin:textForToolBar text=""/>
    <%
    MaptoBeanUtil userBs  = new MaptoBeanUtil();
      if ( userBs.isLeader() ) {//
%>
    <odin:buttonForToolBar text="保存" id="save" cls="x-btn-text-icon2"
                           icon="images/save.gif"/>
<%} %>
    <odin:separator/>
</odin:toolBar>

<odin:hidden property="operation"></odin:hidden>

<div id="id" style="padding-bottom: 0px; width: 100%"></div>

<div id="addContent" style="width: 100%">
    <table border="0" align="center" style="width:100%;"
           cellpadding="0" cellspacing="0">
        <tr>
            <odin:hidden property="gzdbid"/>
        </tr>
        <tr>
            <br/>
        </tr>
        <tr>
            <odin:hidden property="gzdbid" title="工作督办id"></odin:hidden>
        </tr>
        <tr>
        	<td style="width: 20px"></td>
			<odin:textarea property="sxmc"  label="事项名称"  cols="80" rows="4" maxlength="800" required="true" ></odin:textarea>
			<odin:textarea property="jtwt" label="具体问题"  cols="80" rows="4" maxlength="800" required="true" ></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zgmb" label="整改目标"  cols="80" rows="2" maxlength="800" required="true" ></odin:textarea>
		  	<odin:textarea property="qtld" label="牵头领导"  cols="80" rows="2" maxlength="800" required="true" ></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zrcs" label="责任处室"  cols="80" rows="1" maxlength="800" required="true"></odin:textarea>
		  	<odin:textarea property="cs001" label="配合处室"  cols="80" rows="1" maxlength="800" required="true"></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zgrw" label="整改措施"  cols="80" rows="4" maxlength="800" required="true"></odin:textarea>
		  	<odin:textarea property="wcsx" label="完成时限"  cols="80" rows="4" maxlength="800" required="true"></odin:textarea>
		  	
		  </tr>
		   <tr>
		  	<td style="width: 20px"></td>
		  	<odin:numberEdit property="wcl" label="完成&nbsp;&nbsp;率"  maxValue="100" required="true" minValue="0" colspan="1">%</odin:numberEdit>
		  	<odin:select2 property="wcbz2"  value="" label="长期坚持"  data="['1','是'],['0','否']"  ></odin:select2> 
		  </tr>
		 <%--  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:numberEdit property="wcl" label="排序"  maxValue="100" minValue="0" colspan="4"></odin:numberEdit>
		  </tr> --%>
		  <%-- <tr>
		  	<td style="width: 20px"></td>
		  	<odin:select2 property="wcbz1" value=""  label="完成标志1"  data="['1','完成'],['2','进行中'],['3','未启动']" ></odin:select2>
		  	<odin:select2 property="wcbz2"  value="" label="完成标志2"  data="['1','完成'],['2','进行中'],['3','未启动']"  ></odin:select2> 
		  </tr> --%>
		  
       <!--  <tr>
        <tr>
            <td nowrap align=right><span id=p3102spanid style="font-size: 12px">备注：</span>&nbsp;</td>
            <td colspan=6 nowrap>
                <div class=x-form-item>
                    <div id=ext-gen19 class=x-form-element><textarea id=remark title="" class=" x-form-textarea x-form-field " style="width: 97%" rows=13 name=remark></textarea>
                    </div>
                </div>

            </td>
        </tr>
        </tr> -->

    </table>
</div>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
    <odin:textForToolBar text="<h3>任务完成情况信息</h3>"/>
    <odin:fill/>
    <odin:separator></odin:separator>
    <odin:buttonForToolBar text="新增" icon="images/add.gif" handler="adda" id="adda"/>
    <odin:separator></odin:separator>
    <%--<odin:buttonForToolBar text="&nbsp;年度年休假计划名单"   id="print" icon="jwjc/images/print.jpg" handler="printStatement"/>--%>
    <%--<odin:separator></odin:separator>--%>
    <odin:buttonForToolBar text="删除" icon="image/delete.png" id="deleteBtn"  isLast="true"/>

</odin:toolBar>
<div id="tablelist" style=" display: none" class="showTable">
    <div id="groupTreePanel" style=" display: none" class="showTable"></div>
    <table style="width: 100%; display: none"  class="showTable">
        <tr>
            <td>
                <odin:editgrid2 property="grid2" bbarId="pageToolBar" autoFill="false"  cellDbClick="editbtn"  height="223" >
                    <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
                        <odin:gridDataCol name="checked"/>
                        <odin:gridDataCol name="rwwcqkid"/>
                       <%--  <odin:gridDataCol name="cs001"/> --%>
                        <odin:gridDataCol name="wcqk"/>
                        <odin:gridDataCol name="wcqkbj"/>
                        <odin:gridDataCol name="bz" isLast="true"/>
                    </odin:gridJsonDataModel>
                    <odin:gridColumnModel>
                        <odin:gridRowNumColumn2/>
                        <odin:gridEditColumn2 header="selectall" width="30" gridName="gbnrmGrid" dataIndex="checked"
                                              editor="checkbox" edited="true" hidden="false"/>
                        <odin:gridEditColumn2 dataIndex="rwwcqkid" width="10" editor="text" header="主键id" hidden="true"/>
                        <%-- <odin:gridEditColumn2 dataIndex="cs001" width="40" editor="text" header="处室" hidden="false"/> --%>
                        <odin:gridEditColumn2 dataIndex="wcqk" width="982" header="完成情况" editor="text" edited="false"
                                                align="left"/>
                        <odin:gridEditColumn2 dataIndex="wcqkbj" width="80" header="完成标志" editor="select"  selectData="['1','完成'],['2','长期坚持'],['3','正在整改']"  edited="false"/>
                        <odin:gridEditColumn2 dataIndex="bz" width="80" header="备注" editor="text" edited="false"
                                              maxLength="2000" menuDisabled="true" sortable="false" align="left"
                                              isLast="true"/>
                    </odin:gridColumnModel>
                    <odin:gridJsonData>
                        {
                        data:[]
                        }
                    </odin:gridJsonData>
                </odin:editgrid2>
            </td>
        </tr>
    </table>
</div>
<odin:hidden property="gzdbid"/>
<script type="text/javascript">

    var Func = {
        init: function () {
        	/* var id = document.getElementById("id");
           	var viewSize = Ext.getBody().getViewSize();
           	id.style.height = viewSize.height-492;  */
            $("#operation").val(GetQueryString("operation"));
            var operation=$("#operation").val();
            $("#gzdbid").val(GetQueryString("gzdbid"));
            //$(".showTableS").remove();
            if(operation=="edit"){
                $(".showTable").css("display","block");
            }else{
                $(".showTable").remove();
            }
        	
            // Func.clickEvent("initPage");
            radow.doEvent("initPage");
           /*  Ext.getCmp('grid2').on('rowdblclick',function(gridobj,index,e){
            	$(".showTableS").css("display","block");
         		var rc = gridobj.getStore().getAt(index);
         		
         		odin.setSelectValue('rwwcqkid',rc.data.rwwcqkid);
             	odin.setSelectValue('cs001',rc.data.cs001);
             	odin.setSelectValue('wcqk',rc.data.wcqk);
             	odin.setSelectValue('wcqkbj',rc.data.wcqkbj);
             	odin.setSelectValue('bz',rc.data.bz);
             	
             	openMxWin();
         	}); */

        },
        clickEvent: function () {
            radow.doEvent(event);
        }
		

    }

    Ext.onReady(function(){
    	
    });
  //修改
    function editbtn(){
    	var list = odin.ext.getCmp('grid2').getSelectionModel().getSelections();	
        if(list.length==1){  
        	var rwwcqkid=list[0].get('rwwcqkid');
            //document.getElementById('rwwcqkid').value=rwwcqkid;
            edit(rwwcqkid);
        	
    	}else if(list!=null && list.length>1 ){
    		var msg = '只能选择一条需要修改的完成情况!';
            odin.alert(msg);
    	}else{
    		var msg = '请先选择需要修改的完成情况!';
            odin.alert(msg);
    	} 
    		
    }
    function dd(aa){
    if(aa=="1"){
    	sxmc.readOnly=false;
    	jtwt.readOnly=false;
    	zgmb.readOnly=false;
    	qtld.readOnly=false;
    	zrcs.readOnly=false;
    	cs001.readOnly=false;
    	zgrw.readOnly=false;
    	wcsx.readOnly=false;
    }else if(aa=="2"){
    	sxmc.readOnly=true;
    	jtwt.readOnly=true;
    	zgmb.readOnly=true;
    	qtld.readOnly=true;
    	zrcs.readOnly=true;
    	cs001.readOnly=true;
    	zgrw.readOnly=true;
    	wcsx.readOnly=true;
    	
    }
    	
    }
    function success(){
    	/* if (typeof(parent.GridReload)!="undefined"){
    		parent.GridReload();
    	} */
    	realParent.radow.doEvent('grid1.dogridquery');
    	// window.close();
    }
    function saveCallBack() {

        //window.close();
        //window.dialogArguments.window.doTrainQuery();
        realParent.radow.doEvent("grid1.dogridquery");
        //window.parent.alert("1111111111111111")
    }
    function closeSelfWin(){
    	
    	 $("#operation").val(GetQueryString("operation"));
         var operation=$("#operation").val();
         $("#rwwcqkid").val(GetQueryString("rwwcqkid"));
         if(operation=="add"){
        	 parent.Ext.getCmp("add1").close();
         }else if(operation=="edit"){
        	 parent.Ext.getCmp("update1").close();
         }
    	 
    	
    }
    function edit (rwwcqkid) {
        var gzdbid = document.getElementById('gzdbid').value;
        if (feildIsNull(rwwcqkid)) {
            $h.alert('系统提示', '请选择一行任务！');
            return;
        }
        param = rwwcqkid + "@" + gzdbid;
        <%--$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics','自定义通用统计图',1010,650,param,'<%=request.getContextPath()%>');--%>
        $h.openWin('WorkSuperviseAdd', 'pages.gzdb.WorkSuperviseAdd', '查看（修改）任务完成情况明细', 580, 421, param, contextpath);
        $("#rwwcqkid").val("");
        
    }
    function adda(){
    	var id = document.getElementById('gzdbid').value;
    	param = '' + "@" + id;
    	$h.openWin('WorkSuperviseAdd', 'pages.gzdb.WorkSuperviseAdd', '新增任务完成情况明细', 580, 421, param, contextpath);
    	        	
    }
    function openMxWin(){
    	var win = Ext.getCmp("adda");	
    	if(win){
    		win.show();	
    		return;
    	}
    	win = new Ext.Window({
    		title : '任务完成情况明细维护',
    		layout : 'fit',
    		width : 380,
    		height : 321,
    		closeAction : 'hide',
    		closable : true,
    		modal : true,
    		id : 'adda',
    		collapsed:false,
    		collapsible:false,
    		bodyStyle : 'background-color:#FFFFFF',
    		plain : true,
    		titleCollapse:false,
    		contentEl:"perInfo",
    		listeners:{}
    		           
    	});
    	win.show();
    }
   /*  function hideWin(){
    	var win = Ext.getCmp("adda");	
    	if(win){
    		win.hide();	
    	}
    } */
    //列表时间格式化
    function formatTime(value){

    	var string="";
    	if(value instanceof Date==true){
    		var y = value.getFullYear();  
    	    var m = value.getMonth() + 1;
    	    if(m<10){
    	    	m="0"+m; 
    	    }
    	    string=y+"-"+m;
    	}else{
    		if(value!=null&&value!=""){
    			string=value.substring(0,7);
    		}
    	  
    	}
    	
    	return string;
    }
</script>


