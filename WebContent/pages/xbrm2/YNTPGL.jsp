<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

//共享信息
function shareInfo(value, params, record, rowIndex, colIndex, ds) {
	if(value!=''&&value!=null){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('unsaveShareInfo','"+record.data.yn_id+"');\">取消共享</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">查看存档</a>"
		+ "</font></div>"
	}else{
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+record.data.yn_id+"');\">共享</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">查看存档</a>"
		+ "</font></div>"
	}
	
}
var hjJSON = {'TPHJ1':'动议干部情况一览表','TPHJ3':'省委书记专题会议干部情况一览表','TPHJ2':'省委常委会干部情况一览表'}; //,'TPHJ4':'市委书记专题会议','TPHJ5':'市委常委会'
//描述
function infoDesc(value, params, record, rowIndex, colIndex, ds) {
	if(value!=''&&value!=null){
		return "<div align='center' width='100%' >"
		+ "<a  onclick=\"return false;\">"+hjJSON[value]+"("+(record.data.gname||'')+record.data.info02+")"+"</a>"
		+ "</div>"
	}else{
		return "";
	}
	
}

function openProcessWin(v){
	var selecthtml = '<div  style="padding:10px;"><label for="tphjselect">方案类型:&nbsp;&nbsp;</label><select id="tphjselect"  name="tphjselect" > '+
		'<option value="TPHJ1">动议干部情况一览表</option>'+
		'<option value="TPHJ3">省委书记专题会议干部情况一览表</option>'+
		'<option value="TPHJ2">省委常委会干部情况一览表</option>'+
	 	//'<option value="TPHJ4">省委书记专题会议</option>'+
	 	//'<option value="TPHJ5">市委常委会</option>'+ 
		'</select></div><div  style="padding:10px;"><label for="male">用户名:&nbsp;&nbsp;&nbsp;&nbsp;</label> <input type="text" name="uname" id="uname" style="width:183px;"/></div>';
		
	var win = new Ext.Window({
		html : selecthtml+'<div  style="padding:10px;margin-left:200px;"><button style="cursor:pointer;" onclick="saveShareInfo(\''+v+'\')">保存</button></div>',
		title : '共享信息',
		layout : 'fit',
		width : 300,
		height : 161,
		closeAction : 'close',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{}
		             
	});
	win.show();
}

//保存共享信息
function saveShareInfo(yn_id){
	if($('#tphjselect').val()==''){
		$h.alert('系统提示','请选择方案类型！');
		return;
	}
	if($('#uname').val()==''){
		$h.alert('系统提示','请输入用户名！');
		return;
	}
	$('#tphjselectH').val($('#tphjselect').val());
	$('#unameH').val($('#uname').val());
	radow.doEvent('saveShareInfo',yn_id);
}
</script>

<!-- 批次面板 -->
<div id="groupTreePanel"></div>
	
	<odin:hidden property="tphjselectH" title="共享环节id"/>
	<odin:hidden property="unameH"  title="共享用户id"/>
	<odin:hidden property="yn_id"/>
	<odin:hidden property="yn_name"/>
	<odin:groupBox title="查询条件">

	<table style="width: 100%;">
		<tr>
			<odin:textEdit property="yn_name1" label="批次名称" ></odin:textEdit>
			<odin:select2 property="yn_type1" label="类型" data="['TPHJ1','动议干部情况一览表'],['TPHJ3','省委书记专题会议干部情况一览表'],['TPHJ2','省委常委会干部情况一览表']"></odin:select2> <!-- ['TPHJ1','酝酿'],['TPHJ2','市委书记专题会议成员酝酿'],['TPHJ3','部务会议'],['TPHJ4','市委书记专题会议'],['TPHJ5','市委常委会'] -->
		</tr>
	</table>
</odin:groupBox>

<!-- 批次显示列表 -->
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="批次信息" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="yn_id" />
		<odin:gridDataCol name="yn_name"/>
		<odin:gridDataCol name="yn_type"/>
		<odin:gridDataCol name="info01"/>
		<odin:gridDataCol name="info02"/>
		<odin:gridDataCol name="gname"/>
		<odin:gridDataCol name="yn_date" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="yn_id" width="110" hidden="true" editor="text" header="主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="yn_name" width="190" header="名称" editor="text" edited="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="yn_type" width="140" header="当前调配类型" editor="select" selectData="['TPHJ1','动议干部情况一览表'],['TPHJ3','省委书记专题会议干部情况一览表'],['TPHJ2','省委常委会干部情况一览表']" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="yn_date" width="140" header="登记日期" editor="text"   edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="info01" width="190" header="共享信息" editor="text" edited="false" align="center" renderer="infoDesc" />
		<odin:gridEditColumn2 dataIndex="info02" width="190" header="操作" editor="text" edited="false" align="center" renderer="shareInfo" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>

		


<!-- 操作工具栏 -->
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="修改" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="infoSearch" handler="infoSearch" isLast="true"/>
</odin:toolBar>


<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	//表格单击，给酝酿编号yn_id,酝酿名称yn_name的隐藏域赋值
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('yn_id').value = rc.data.yn_id;
		document.getElementById('yn_name').value = rc.data.yn_name;
	});
	
	//表格双击，弹出干部信息一览表窗体
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('yn_id').value = rc.data.yn_id;
		var id=rc.data.yn_id+","+rc.data.yn_type;
		/* $h.openPageModeWin('qcjs','pages.xbrm2.TPB','干部调配建议方案',1150,800,
				{yn_id:rc.data.yn_id,yn_type:rc.data.yn_type,scroll:"scroll:yes;"},g_contextpath);  
		 
		*/
		var winHeight = $(top).outerHeight()+160;
		var winWidth = $(top).width() * 0.95;
		top.$h.openWin('qcjs','pages.xbrm2.TPB','干部信息一览表',winWidth,winHeight,
				id,g_contextpath,null,{maximizable:false,resizable:true,draggable:false}, true); 
		 
	
	});
	
});

/**
 * 增加批次
 *
 */
function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm2.AddYNTP','增加批次',510,220,{yn_id:''},g_contextpath);
}

/**
 * 查看归档
 *
 */
function openGDview(yn_id){
	$h.openPageModeWin('gdck','pages.xbrm2.TPBView','干部调配建议方案存档信息',1150,800,
			{yn_id:yn_id,scroll:"scroll:no;"},g_contextpath);
}

/**
 * 增加名称修改
 *
 */
function infoUpdate(){
	var yn_id = document.getElementById('yn_id').value;

	if(yn_id==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm2.AddYNTP','修改批次',510,220,{yn_id:yn_id},g_contextpath);
}

/**
 * 删除批次
 *
 */
function infoDelete(){ 
	var yn_id = document.getElementById('yn_id').value;
	var yn_name = document.getElementById('yn_name').value;
	if(yn_id==''){
		$h.alert('系统提示','请选择一行批次记录！');
		return;
	}
	$h.confirm("系统提示：",'删除批次记录将会删除该批次下所有的调配记录，确定删除批次：'+yn_name+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',yn_id);
		}else{
			return false;
		}		
	});
}


function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//默认选择第一条数据。
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.yn_id==$('#yn_id').val()){
				
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

/**
 * 查询
 *
 */
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


