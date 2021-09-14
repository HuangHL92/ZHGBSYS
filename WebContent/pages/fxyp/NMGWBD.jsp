<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/field-to-grid-dd.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style type="text/css">
.x-grid-record-nm{
	background: #ccccff;
}
.x-grid-record-zr{
	background: #ff5555;
}
#bdbutton button{
	margin-right:1px
}
</style>
<script type="text/javascript">

</script>
<odin:hidden property="mntp00"/>
<odin:hidden property="fxyp07"/>
<odin:hidden property="a0200"/>
<odin:hidden property="zrrx"/>
<odin:hidden property="b0111"/>
<odin:hidden property="a0215a"/>
<table style="width: 100%;">
	<tr>
		<td>
				<odin:editgrid2 property="nrGrid" hasRightMenu="false" bbarId="pageToolBar" pageSize="20"  width="500" height="670" isFirstLoadData="true" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="zrrx" />
						<odin:gridDataCol name="fxyp07"/>
						<odin:gridDataCol name="fxyp02"/>
						<odin:gridDataCol name="fxyp00"/>
						<odin:gridDataCol name="fxyp00ref"/>
						<odin:gridDataCol name="bind" isLast="true"/>		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="zrrx"  header="机构性质" hidden="true" edited="false"  editor="text"/>
						<odin:gridEditColumn2 dataIndex="fxyp07" hidden="true" header="职务全称" editor="text"  edited="false" />
						<odin:gridEditColumn2 dataIndex="fxyp02"  header="职务名称" width="350" editor="text" align="left"  edited="false"/>
						<odin:gridEditColumn2 dataIndex="fxyp00" hidden="true"  header="分析研判主键" editor="text"  edited="false"    />
						<odin:gridEditColumn2 dataIndex="fxyp00ref" hidden="true"  header="绑定对象主键" editor="text"  edited="false"    />
						<odin:gridEditColumn2 width="150" header="操作" dataIndex="bind" editor="text" edited="false" renderer="bindRenderer" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
	</tr>
</table>



<script type="text/javascript">



Ext.onReady(function(){
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$('#fxyp07').val(parent.Ext.getCmp(subWinId).initialConfig.fxyp07);
	$('#a0200').val(parent.Ext.getCmp(subWinId).initialConfig.a0200);
	$('#zrrx').val(parent.Ext.getCmp(subWinId).initialConfig.zrrx);
	$('#b0111').val(parent.Ext.getCmp(subWinId).initialConfig.b0111);
	$('#a0215a').val(parent.Ext.getCmp(subWinId).initialConfig.a0215a);
	
	
	Ext.getCmp('nrGrid').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp00ref = record.data.fxyp00ref;
		if(fxyp00ref!=null && fxyp00ref!=''){//已绑定
			return 'x-grid-record-nm';
        }else{
        	return '';
        }
		
	};
	
	
});


//删除行
function bindRenderer(value, params, record,rowIndex,colIndex,ds){
	var fxyp00 = record.data.fxyp00;
	var fxyp00ref=record.data.fxyp00ref;
	if(realParent.buttonDisabled){
		return "绑定";
	}
	return "<a href=\"javascript:bindRow2(&quot;"+fxyp00+"&quot;,&quot;"+fxyp00ref+"&quot;)\">绑定</a>";
}
function bindRow2(fxyp00,fxyp00ref){ 
	if(fxyp00ref!=null && fxyp00ref!='' && fxyp00ref!='null'){//已绑定
		$h.alert('系统提示：','该拟任职务已绑定！',null,150);
		return;
    }
	Ext.Msg.confirm("系统提示","是否确认绑定？",function(id) { 
		if("yes"==id){
			radow.doEvent('bindGW',fxyp00);
		}else{
			return;
		}		
	});	
}
</script>
