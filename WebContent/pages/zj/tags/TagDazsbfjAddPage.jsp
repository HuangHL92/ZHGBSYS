<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<%@ page import="java.net.URLDecoder"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#wzms{width: 820px; height: 120px !important; position: relative;top: 10px;left: 15px;}
#grid{width: 818px; position: relative; top: 5px; left: 15px;}
</style>
<div id="tag_container">
	<odin:hidden property="tagid" title="����id" ></odin:hidden>
	<odin:hidden property="a0000" title="��Ա����" ></odin:hidden>
	<iframe src ="<%=request.getContextPath()%>/pages/zj/tags/addTagDazsbfj.jsp" id="wzms" frameborder="0"></iframe>
	<div id="grid">
		<table>
			<tr>
				<td width="100%">
					<odin:grid property="tagDazsbfjGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" height="300">
						<odin:gridJsonDataModel id="tagid" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="tagid" />
							<odin:gridDataCol name="fileurl" />
							<odin:gridDataCol name="filename" />
							<odin:gridDataCol name="filesize" />
							<odin:gridDataCol name="updatedate" />
							<odin:gridDataCol name="note" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn dataIndex="tagid" width="90" hidden="true" header="tagid" align="center" editor="text" edited="false"/>
							<odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="�ļ����·��" align="center" editor="text" edited="false"/>
							<odin:gridEditColumn dataIndex="filename" header="�ļ�����" align="center" edited="false" editor="text" width="100" />
							<odin:gridEditColumn dataIndex="filesize" header="�ļ���С" align="center" edited="false" editor="text" width="10" />
							<odin:gridEditColumn dataIndex="updatedate" header="����ʱ��" align="center" edited="false" editor="text" width="50" />
							<odin:gridEditColumn dataIndex="note" header="��ע" align="center" edited="false" editor="text" width="50" />
							<odin:gridEditColumn dataIndex="downtimes" header="����" align="center" renderer="GrantRender" edited="false" editor="text" width="45"  isLast="true"/>
						</odin:gridColumnModel>
					</odin:grid>	
				</td>
			</tr>
		</table>
	</div>
</div>	
<script type="text/javascript">
Ext.onReady(function(){
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.tagCjlx);
		
		var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var ext_gridobj = Ext.getCmp('tagDazsbfjGrid');
		var gridobj = document.getElementById('forView_tagDazsbfjGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className= "divcover_wrap";
		cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className= "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
		
	}
	
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(realParent.fieldsDisabled);
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
	var side_resize=function(){
		 Ext.getCmp('tagDazsbfjGrid').setWidth(810); 
	}
	side_resize();  
	window.onresize=side_resize; 
});

function GrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:downloadDazsbfjFile('"+rs.get('tagid')+"','"+rs.get('fileurl')+"')\">����</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:deleteDazsbfjFile('"+rs.get('tagid')+"','"+rs.get('fileurl')+"')\">ɾ��</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
}

// ����ר�󸽼����� 
// encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
function downloadDazsbfjFile(tagid, fileurl){
	window.location="<%=request.getContextPath()%>/TagFileServlet?method=downloadDazsbfjFile&fileurl="+encodeURI(encodeURI(fileurl));
}

//����ר�󸽼�ɾ��
//encodeURI��������urlת�룬������Ĵ����������� 
function deleteDazsbfjFile(tagid, fileurl){
	//ͨ��ajax����ɾ������ר�󸽼� 
	$.ajax({
		url:"<%=request.getContextPath()%>/TagFileServlet?method=deleteDazsbfjFile",
		type:"GET",
		data:{
			"tagid": tagid,
			"fileurl": encodeURI(fileurl)
		},
		success:function(){
			radow.doEvent('tagDazsbfjGrid.dogridquery');
			updateRmbFresh();
			odin.alert("����ר�󸽼�ɾ���ɹ�!");
		},
		error:function(){
			odin.alert("����ר�󸽼�ɾ��ʧ��!");		
		}
	});
	
}

function updateRmbFresh(){
	radow.doEvent('updateRmbFresh');
}
</script>


<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>
