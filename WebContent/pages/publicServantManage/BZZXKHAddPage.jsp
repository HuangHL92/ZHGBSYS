<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>

<style>
#wzms{position: relative;top:20px;left:15px;}
#grid{position: relative;top:40px;left:15px;}
</style>
<script type="text/javascript">
function save(){	
	radow.doEvent("save.onclick");
}

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var bz00 = record.data.bz00;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+bz00+"&quot;)\">ɾ��</a>";
}

function deleteRow2(bz00){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',bz00);
		}else{
			return;
		}		
	});	
}
</script>


<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<%-- <odin:buttonForToolBar text="ɾ��" icon="images/back.gif" cls="x-btn-text-icon" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
				<%--
				<odin:buttonForToolBar text="��������" id="saveAll" cls="x-btn-text-icon" icon="images/save.gif" ></odin:buttonForToolBar>
				--%>
				<odin:buttonForToolBar text="����" id="BZZXKHAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
				
				<odin:buttonForToolBar text="����" id="save22" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" align="center"></div>
<odin:hidden property="bz00" title="����id" ></odin:hidden>
<div id="wzms">
	<table>
		<tr>
			<td><odin:textarea property="description"  cols="100" rows="4" label="��������"  ></odin:textarea></td>		
			
		</tr>
	</table>
</div>
<div id="grid">
	<table>
		<tr>
			<td style="width:625px">
				<odin:grid property="BZZXKHGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="250" >
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="bz00" />
					<odin:gridDataCol name="zxkhsj" />
			  		<odin:gridDataCol name="zxkhm" />
			   		<odin:gridDataCol name="zxkhjg"/>			   		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="bz00" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="ר���ʱ��" dataIndex="zxkhsj" edited="false" editor="text"/>
				  <odin:gridEditColumn header="ר�����" dataIndex="zxkhm" edited="false" editor="text"/>
				  <odin:gridEditColumn2  header="���˽��"  dataIndex="zxkhjg" edited="false" editor="select" selectData="['1','����'],['2','����'],['3','һ��'],['4','�ϲ�']"  />
				  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>	
			</td>
			<td>
				<table>
					<tr height="50">
						<odin:NewDateEditTag property="zxkhsj" label="����ʱ��" required="true" maxlength="8"  />
					</tr>
					<tr height="50">
						<odin:textEdit property="zxkhm" label="ר������" required="true"  />
					</tr>
					<tr height="50">
						<odin:select2 property="zxkhjg" label="���˽��" required="true" data="['1','����'],['2','����'],['3','һ��'],['4','�ϲ�']"></odin:select2>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("BZZXKH")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("BZZXKH")%>;
Ext.onReady(function(){
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a15);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var ext_gridobj = Ext.getCmp('BZZXKHGrid');
		var gridobj = document.getElementById('forView_BZZXKHGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className= "divcover_wrap";
		cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className= "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
	}
});

Ext.onReady(function(){
	var side_resize=function(){
		 document.getElementById('btnToolBarDiv').style.width = document.body.clientWidth;	
//		 Ext.getCmp('AssessmentInfoGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_AssessmentInfoGrid'))[0]-4);
		 Ext.getCmp('BZZXKHGrid').setWidth(625); 
		 //document.getElementById('main').style.width = document.body.clientWidth-2;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
//		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	}
	side_resize();  
	window.onresize=side_resize; 
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

function lockINFO(){
	Ext.getCmp("BZZXKHAddBtn").disable(); 
	Ext.getCmp("save22").disable(); 
	Ext.getCmp("BZZXKHGrid").getColumnModel().setHidden(4,true);
}

</script>


<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>