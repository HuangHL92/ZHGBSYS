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
    <odin:buttonForToolBar text="����" id="save" cls="x-btn-text-icon2"
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
            <odin:hidden property="gzdbid" title="��������id"></odin:hidden>
        </tr>
        <tr>
        	<td style="width: 20px"></td>
			<odin:textarea property="sxmc"  label="��������"  cols="80" rows="4" maxlength="800" required="true" ></odin:textarea>
			<odin:textarea property="jtwt" label="��������"  cols="80" rows="4" maxlength="800" required="true" ></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zgmb" label="����Ŀ��"  cols="80" rows="2" maxlength="800" required="true" ></odin:textarea>
		  	<odin:textarea property="qtld" label="ǣͷ�쵼"  cols="80" rows="2" maxlength="800" required="true" ></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zrcs" label="���δ���"  cols="80" rows="1" maxlength="800" required="true"></odin:textarea>
		  	<odin:textarea property="cs001" label="��ϴ���"  cols="80" rows="1" maxlength="800" required="true"></odin:textarea>
		  </tr>
		  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:textarea property="zgrw" label="���Ĵ�ʩ"  cols="80" rows="4" maxlength="800" required="true"></odin:textarea>
		  	<odin:textarea property="wcsx" label="���ʱ��"  cols="80" rows="4" maxlength="800" required="true"></odin:textarea>
		  	
		  </tr>
		   <tr>
		  	<td style="width: 20px"></td>
		  	<odin:numberEdit property="wcl" label="���&nbsp;&nbsp;��"  maxValue="100" required="true" minValue="0" colspan="1">%</odin:numberEdit>
		  	<odin:select2 property="wcbz2"  value="" label="���ڼ��"  data="['1','��'],['0','��']"  ></odin:select2> 
		  </tr>
		 <%--  <tr>
		  	<td style="width: 20px"></td>
		  	<odin:numberEdit property="wcl" label="����"  maxValue="100" minValue="0" colspan="4"></odin:numberEdit>
		  </tr> --%>
		  <%-- <tr>
		  	<td style="width: 20px"></td>
		  	<odin:select2 property="wcbz1" value=""  label="��ɱ�־1"  data="['1','���'],['2','������'],['3','δ����']" ></odin:select2>
		  	<odin:select2 property="wcbz2"  value="" label="��ɱ�־2"  data="['1','���'],['2','������'],['3','δ����']"  ></odin:select2> 
		  </tr> --%>
		  
       <!--  <tr>
        <tr>
            <td nowrap align=right><span id=p3102spanid style="font-size: 12px">��ע��</span>&nbsp;</td>
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
    <odin:textForToolBar text="<h3>������������Ϣ</h3>"/>
    <odin:fill/>
    <odin:separator></odin:separator>
    <odin:buttonForToolBar text="����" icon="images/add.gif" handler="adda" id="adda"/>
    <odin:separator></odin:separator>
    <%--<odin:buttonForToolBar text="&nbsp;������ݼټƻ�����"   id="print" icon="jwjc/images/print.jpg" handler="printStatement"/>--%>
    <%--<odin:separator></odin:separator>--%>
    <odin:buttonForToolBar text="ɾ��" icon="image/delete.png" id="deleteBtn"  isLast="true"/>

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
                        <odin:gridEditColumn2 dataIndex="rwwcqkid" width="10" editor="text" header="����id" hidden="true"/>
                        <%-- <odin:gridEditColumn2 dataIndex="cs001" width="40" editor="text" header="����" hidden="false"/> --%>
                        <odin:gridEditColumn2 dataIndex="wcqk" width="982" header="������" editor="text" edited="false"
                                                align="left"/>
                        <odin:gridEditColumn2 dataIndex="wcqkbj" width="80" header="��ɱ�־" editor="select"  selectData="['1','���'],['2','���ڼ��'],['3','��������']"  edited="false"/>
                        <odin:gridEditColumn2 dataIndex="bz" width="80" header="��ע" editor="text" edited="false"
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
  //�޸�
    function editbtn(){
    	var list = odin.ext.getCmp('grid2').getSelectionModel().getSelections();	
        if(list.length==1){  
        	var rwwcqkid=list[0].get('rwwcqkid');
            //document.getElementById('rwwcqkid').value=rwwcqkid;
            edit(rwwcqkid);
        	
    	}else if(list!=null && list.length>1 ){
    		var msg = 'ֻ��ѡ��һ����Ҫ�޸ĵ�������!';
            odin.alert(msg);
    	}else{
    		var msg = '����ѡ����Ҫ�޸ĵ�������!';
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
            $h.alert('ϵͳ��ʾ', '��ѡ��һ������');
            return;
        }
        param = rwwcqkid + "@" + gzdbid;
        <%--$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics','�Զ���ͨ��ͳ��ͼ',1010,650,param,'<%=request.getContextPath()%>');--%>
        $h.openWin('WorkSuperviseAdd', 'pages.gzdb.WorkSuperviseAdd', '�鿴���޸ģ�������������ϸ', 580, 421, param, contextpath);
        $("#rwwcqkid").val("");
        
    }
    function adda(){
    	var id = document.getElementById('gzdbid').value;
    	param = '' + "@" + id;
    	$h.openWin('WorkSuperviseAdd', 'pages.gzdb.WorkSuperviseAdd', '����������������ϸ', 580, 421, param, contextpath);
    	        	
    }
    function openMxWin(){
    	var win = Ext.getCmp("adda");	
    	if(win){
    		win.show();	
    		return;
    	}
    	win = new Ext.Window({
    		title : '������������ϸά��',
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
    //�б�ʱ���ʽ��
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


