<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/rmb/lockData.jsp"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
#table{position:relative;top: -12px;left:5px;}
#table2{position:relative;top: -20px; padding: 0px;margin: 0px;height:300}
.inline{
display: inline;
}
.pl{
margin-left: 8px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript">
function setA0602Value(record,index){
	Ext.getCmp('a0602').setValue(record.data.value);
	Ext.getCmp('a0196').setValue(record.data.value);
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a0600 = record.data.a0600;
	if(realParent.buttonDisabled){
		return "删除";
	}
	var fieldsDisabled = <%=TableColInterface.getAllUpdateData()%>;
	if(fieldsDisabled==''||fieldsDisabled==undefined){
		return "<a href=\"javascript:deleteRow2(&quot;"+a0600+"&quot;)\">删除</a>";
	}
	var datas = fieldsDisabled.toString().split(',');
	for(var i=0;i<datas.length;i++){
		if(datas[i]==("a0602")||datas[i]==("a0601")||datas[i]==("a0604")||datas[i]==("a0607")||datas[i]==("a0611")){
			Ext.getCmp("professSkillAddBtn").setDisabled(true);
			return "<u style=\"color:#D3D3D3\">删除</u>"; 
		}
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a0600+"&quot;)\">删除</a>";
}
function deleteRow2(a0600){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0600);
		}else{
			return;
		}		
	});	
}



</script>

<body>
<odin:toolBar property="toolBar1" applyTo="tol1">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="增加" id="professSkillAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="保存" id="save1" handler="save" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
<div id="tol1"></div>
<odin:hidden property="sortid" title="排序号"/>
<table id="table">
	<tr>
		<td>
			<div id="div1" style="width:330;">
			 <odin:editgrid property="professSkillgrid"    isFirstLoadData="false" forceNoScroll="true" url="/" applyTo="div1">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
				    <odin:gridDataCol name="a0699" />
					<odin:gridDataCol name="a0600" />
					<odin:gridDataCol name="a0601" />
					<odin:gridDataCol name="a0602" />
					<odin:gridDataCol name="a0604" />
					<odin:gridDataCol name="a0607" />
					<odin:gridDataCol name="a0611" />
					<odin:gridDataCol name="delete" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="输出" width="15" editor="checkbox" dataIndex="a0699" edited="true"/><%-- checkBoxClick="a06checkBoxColClick" --%>
					<odin:gridColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
					<odin:gridEditColumn2 header="专业技术资格代码" dataIndex="a0601" codeType="GB8561" edited="false" editor="select" hidden="true"/>
					<odin:gridColumn header="专业技术资格代码" dataIndex="a0602" editor="text" />
					<odin:gridColumn header="获得资格日期" dataIndex="a0604" editor="text" />
					<odin:gridEditColumn2 header="取得资格途径" dataIndex="a0607" codeType="ZB24" edited="false" editor="select" hidden="true"/>
					<odin:gridColumn header="评委会或考试名称" dataIndex="a0611" editor="text"  hidden="true"/>		
					 <odin:gridEditColumn header="操作" width="15" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>		
				</odin:gridColumnModel>
			 </odin:editgrid>
			</div>
			<div id="btngroup"> </div>
		</td>
		<td>
		  <div>
			<table id="table2">
				<tr>
					<odin:textEdit property="a0196" label="专业技术职务" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<td height="50px" style="font-size: 12px"></td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit property="a0601" label="专业技术资格代码" onchange="setA0602Value" required="true" readonly="true" codetype="GB8561"></tags:PublicTextIconEdit>	
				</tr>
				<tr>
					<odin:textEdit property="a0602" label="专业技术资格名称" validator="a0602Length"></odin:textEdit>	
				</tr>
				<tr>
					<odin:NewDateEditTag property="a0604" label="获得资格日期" maxlength="8" ></odin:NewDateEditTag>	
				</tr>
				<tr>
					<odin:select2 property="a0607" label="取得资格途径" codeType="ZB24"></odin:select2>		
				</tr>
				<tr>
					<odin:textEdit property="a0611" label="评委会或考试名称" validator="a0611Length"></odin:textEdit>
					<odin:hidden property="a0600" title="主键id" ></odin:hidden>		
				</tr>
			</table>
		  </div>
		</td>
	</tr>
	
	</table>
	
<odin:hidden property="a0699" title="输出"/>
 </div>
</body>
<script type="text/javascript">
/** function deleteRow(){ 
	var sm = Ext.getCmp("professSkillgrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
} **/
Ext.onReady(function(){
	var firstload = true;
	var pgrid = Ext.getCmp("professSkillgrid");
	var dstore = pgrid.getStore();
	dstore.on({  
       load:{  
           fn:function(){  
       		 if(firstload){
       		    $h.selectGridRow('professSkillgrid',0);
       		    firstload = false;
             }
           }      
       },  
       scope:this      
   });  

	
	//取得父页面显示的专业技术职务
	//var a0196 = window.realParent.document.getElementById("a0196").value;
	
});

</script>
<script type="text/javascript">
Ext.onReady(function(){
	 //$h.applyFontConfig($h.spFeildAll.a06);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a06);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var ext_gridobj = Ext.getCmp('professSkillgrid');
		var gridobj = document.getElementById('forView_professSkillgrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		cover_wrap1.className=  "divcover_wrap";
		cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
	}
	
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(realParent.fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(realParent.selectDisabled,imgdata); 
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}
Ext.onReady(function(){
	new Ext.Button({
		icon : 'images/icon/arrowup.gif',
		id:'UpBtn',
	    text:'上移',
	    cls :'inline',
	    renderTo:"btngroup",
	    handler:UpBtn
	});
	new Ext.Button({
		icon : 'images/icon/arrowdown.gif',
		id:'DownBtn',
	    text:'下移',
	    cls :'inline pl',
	    renderTo:"btngroup",
	    handler:DownBtn
	});
	new Ext.Button({
		icon : 'images/icon/save.gif',
		id:'saveSortBtn',
	    text:'保存排序',
	    cls :'inline pl',
	    renderTo:"btngroup",
	    handler:function(){
			radow.doEvent('worksort');
	    }
	});
	/** var side_resize=function(){
		 document.getElementById('tol1').style.width = Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth;
		 document.getElementById('main').style.width = Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth;
		 Ext.getCmp('professSkillgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_professSkillgrid'))[0]-4);
		 Ext.getCmp('professSkillgrid').setWidth(document.body.clientWidth); 

	}
	side_resize();  
	window.onresize=side_resize;  **/
	//document.getElementById('tol1').style.width = Ext.getBody().getViewSize().width;	
	
	Ext.getCmp('professSkillgrid').setHeight(365);
});

function UpBtn(){	
	var grid = odin.ext.getCmp('professSkillgrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要排序的职务!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该职务已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}


function DownBtn(){	
	var grid = odin.ext.getCmp('professSkillgrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的职务!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该职务已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}

function a06checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	if(realParent.buttonDisabled){
		return;
	}
	var sr = getGridSelected(gridName);
	if(!sr){
		return;
	}
	//alert(sr.data.a0600);
	radow.doEvent('updateA06',sr.data.a0600);
}


function save(){
	
	//获得资格日期
	var a0604 = document.getElementById('a0604').value;	
	var a0604_1 = document.getElementById('a0604_1').value;	
	
	
	var text1 = dateValidateBeforeTady(a0604_1);
	if(a0604_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0604);
	}
	if(text1!==true){
		$h.alert('系统提示','获得资格日期：' + text1, null,400);
		return false;
	}
	
	radow.doEvent('save.onclick');
}
odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
	<%
	  String data = TableColInterface.getAllUpdateData();
	  String info = "a06checkBoxColClick";
	  if(data==null||data.equals("")){
		  info = "a06checkBoxColClick";
	  }else{
		  data = data.replaceAll("'","");
		  String[] datas = data.split(",");
		  boolean flag = false;
		  for(String str:datas){
			  if(str.equals("a0602")||str.equals("a0601")||str.equals("a0604")||str.equals("a0607")||str.equals("a0611")){
				  info="-1";
				  break;
			  }
		  }
	  }
	  %>
	if(<%=info %>=='-1'){
		return;
	}
	<%=info %>(rowIndex,colIndex,null,gridId);
	if(obj.className=='x-grid3-check-col'){
		if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
			odin.checkboxds.getAt(rowIndex).set(colName, true);
		}else{
			odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
		}
		obj.className = 'x-grid3-check-col-on';
    }else{
		if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
			odin.checkboxds.getAt(rowIndex).set(colName, false);
		}else{
			odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
			if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
				document.getElementById("selectall_"+gridId+"_"+colName).value='false';
				document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
			}	
		}
		obj.className = 'x-grid3-check-col';
    }
	
	
}

function lockINFO(){
	Ext.getCmp("professSkillAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("UpBtn").disable(); 
	Ext.getCmp("DownBtn").disable(); 
	Ext.getCmp("saveSortBtn").disable(); 
	Ext.getCmp("professSkillgrid").getColumnModel().setHidden(8,true);
}

</script>
<div id="cover_wrap1"></div>