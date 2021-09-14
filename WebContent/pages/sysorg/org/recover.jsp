<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="全部清空" id="excelOut" handler="alldelete" icon="images/icon/exp.png" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" style="width:955px;"></div>
<div style="width:100%;">
	<odin:editgrid  property="recover" height="425" width="700" title="" autoFill="true" bbarId="pageToolBar" pageSize="9999"  url="/" load="">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="datarecordid" />
		<odin:gridDataCol name="removedate" />
		<odin:gridDataCol name="filename" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn2 dataIndex="filename" width="160" header="机构名称"
				align="center" editor="text" edited="false"/>
				<odin:gridEditColumn2 dataIndex="removedate" width="60" header="删除时间"
				align="center" editor="text" edited="false"/>
			<odin:gridEditColumn2 dataIndex="datarecordid" width="100" header="id"
				hideable="false" editor="text" align="center"
				hidden="true" />
			<odin:gridEditColumn header="操作" width="50" dataIndex="delete" editor="text" align="center" edited="false" renderer="handleRenderer" isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>
<script type="text/javascript">


function handleRenderer(value, params, record,rowIndex,colIndex,ds){
	var datarecordid = record.data.datarecordid;
	return "<a href=\"javascript:verify(&quot;"+datarecordid+"&quot;)\">恢复</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:deletedata(&quot;"+datarecordid+"&quot;)\">删除</a>";
}
//首先先校验回收的数据
function verify(datarecordid){
	Ext.Msg.wait('校验数据中...','系统提示');
	radow.doEvent("verify",datarecordid);
}
//通过校验的恢复请求
function recover(datarecordid){
	Ext.Msg.wait('数据恢复中...','系统提示');
	radow.doEvent("datarecord",datarecordid);
}
function recoverReal(result){
	 var num = result.split(",")[0];
	 var datarecordid = result.split(",")[1];
	 $h.confirm("系统提示：",'系统中有存在身份证相同的人员，共'+num+'人,是否覆盖？',200,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('请稍等...','系统提示');
				radow.doEvent("delRecover",datarecordid);
			}else{
				return false;
			}		
		});
}
//删除人员再恢复
function delRecover(datarecordid){
	Ext.Msg.wait('数据恢复中...','系统提示');
	radow.doEvent("delRecover",datarecordid);
}
//删除的提醒
function deletedata(datarecordid){
	 $h.confirm("系统提示：",'请再次确认？',200,function(id) { 
			if("ok"==id){
				Ext.Msg.wait('请稍等...','系统提示');
				radow.doEvent("singledelete",datarecordid);
			}else{
				return false;
			}		
		});
}

function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("recover").setHeight(height-height_top);
	Ext.getCmp("recover").setWidth(width);
}
Ext.onReady(function() {
	setWidthHeight();
	window.onresize=setWidthHeight;
	
});
function alldelete(){
	$h.confirm("系统提示：",'数据清除后，原有数据将无法恢复，是否继续？',330,function(id) { 
		if("ok"==id){
			 $h.confirm("系统提示：",'请再次确认？',200,function(id) { 
				if("ok"==id){
					Ext.Msg.wait('清空数据中...','系统提示');
					radow.doEvent('allDelete');
				}else{
					return false;
				}		
			});
		}else{
			return false;
		}		
	});
}
</script>