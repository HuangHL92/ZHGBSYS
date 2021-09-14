<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>


<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="rb_id"/>
<odin:hidden property="rb_name"/>
<odin:hidden property="sign"/>
<odin:groupBox title="查询条件">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="rb_name1" label="批次名称" ></odin:textEdit>
		<odin:textEdit property="rb_date1" label="申请日期" ></odin:textEdit>
		<%-- <odin:select2 property="rb_type" label="类型" data="['1','班子换届'],['2','个人提拔']"></odin:select2> --%>
	</tr>
</table>
</odin:groupBox>

<div id="group" style="text-align: right;">
	<table>
	 <tr align="right">
		 <td align="left">
		 <input name="weish" class=" x-form-radio x-form-field" checked id="weish" onclick="wei()" type="radio"/>
		 </td>
		 <td class="x-form-item" aligh="left">未审核</td>
		 <td align="left">
		 <input name="weish" class=" x-form-radio x-form-field" id="weish" onclick="yish()" type="radio"/>
		 </td>  
		 <td class="x-form-item" aligh="left">已审核</td>
		   <%-- <odin:radio property="weish" value="1" onclick="weish()" label="是"></odin:radio>
		   <odin:radio property="weish" value="1" onclick="yish()" label="否"></odin:radio> --%>
	 </tr>
	
	
	</table>
</div>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="批次信息" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="rb_id" />
		<odin:gridDataCol name="rb_name"/>
		<odin:gridDataCol name="rb_date"/>
		<odin:gridDataCol name="rb_no"/>
		<odin:gridDataCol name="rb_org"/>
		<odin:gridDataCol name="rb_applicant"/>
		<odin:gridDataCol name="rb_userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="rb_id" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_name" width="190" header="批次名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_date" width="140" header="申请日期" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_no" width="140" header="批次编号" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_org" width="140" header="申请机构" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_applicant" width="140" header="申请人" editor="text" edited="false" isLast="true"  align="center"/>
		
		<%-- <odin:gridEditColumn2 dataIndex="rb_id" width="140" header="操作" editor="text" renderer="expFile" edited="false"  align="center" isLast="true"  />
		 --%>
	</odin:gridColumnModel>
</odin:editgrid2>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>纪实批次</h3>" />
	<odin:fill />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" /> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" handler="infoSearch" isLast="true"/>
</odin:toolBar>



<%-- <odin:toolBar property="btnToolBar2" applyTo="group">
	<odin:buttonForToolBar text="未审核" handler="weish" id="weish" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="已审核" handler="yish" id="yish" isLast="true"/> 
</odin:toolBar> --%>


<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-140);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		document.getElementById('rb_name').value = rc.data.rb_name;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		
		$h.openPageModeWin('zsys','pages.xbrm.ZSYS','职数审核',900,600,{rb_id:rc.data.rb_id},g_contextpath);
		radow.doEvent('memberGrid.dogridquery');
	});
	
	//Ext.getDom('weish').style.background = '#99FF00';
	//Ext.getDom('yish').style.background = '';
});

function wei(){
	//alert("0");
	document.getElementById('sign').value = '0';
	radow.doEvent("memberGrid.dogridquery");
	//Ext.getDom('weish').style.background = '#99FF00';
	//Ext.getDom('yish').style.background = '';
}

function yish(){
	//alert("1");
	document.getElementById('sign').value = '1';
	radow.doEvent("memberGrid.dogridquery");
	//Ext.getDom('weish').style.background = '';
	//Ext.getDom('yish').style.background = '#99FF00';
}

var g_contextpath = '<%= request.getContextPath() %>';

function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.rb_id==$('#rb_id').val()){
				
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	}
}
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


