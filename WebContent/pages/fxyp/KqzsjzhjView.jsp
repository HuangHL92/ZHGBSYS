<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/field-to-grid-dd-view.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/cookie.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style type="text/css">
label,td{
	font-size: 12px;
}
.x-grid3-col{
	vertical-align: middle!important;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.x-grid-record-nm{
	background: #ccccff;
}
.x-grid-record-zr{
	background: #ff5555;
}
.x-grid-record-ytp{
	background: #84bf96;
}
.x-grid-record-zr span,.x-grid-record-zr div{
	color: white;
}
.x-grid-record-ytp span,.x-grid-record-ytp div{
	color: white;
}
.top_btn_style{
	display:inline;
	border-radius:5px;
	cursor:pointer;
	margin-left:7px;
	height:25px;
	line-height:25px;
	font-size:14px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
}

.x-drop-target-active {
	background-color: #D88!important;
}
.mdf {
	background-color: #D18;
}
.OrgInfo{
	border: 1px solid #c0d1e3 ;
	font-size: 12px;
	overflow: auto;
}
.OrgInfo .p1{
	text-indent: -7em;
	padding-left: 6em;
	margin: 10px 0px;
}
.OrgInfo .p2{
	text-indent: -6em;
	padding-left: 6em;
	margin: 10px 0px;
}
#gwInfo input{
	border: 1px solid #c0d1e3 !important;
}
/*������ʽ  */
.reverse-search{
	cursor: pointer;
}
.rcbgcolor{
	background-color: #98FB98;
}
</style>
<script type="text/javascript">
function searchn(){
	radow.doEvent("noticeSetgrid.dogridquery");
}
function selectORG(){
	var url = g_contextpath + "/pages/fxyp/OrgConfig.jsp?";
	$h.showWindowWithSrc("setOrgConfig",url,"������ʾ��λ", 400, 730);
}
function showECharts(b01id,b0111){
	$('#b01id').val(b01id);
	$('#b0111').val(b0111);
	var newWin_ = $h.getTopParent().Ext.getCmp('structuralAnalysis');
	if(newWin_){
		newWin_.show();
		$h.getTopParent().document.getElementById('iframe_structuralAnalysis').contentWindow.submitForm();
		return;
	}
	var url = g_contextpath + "/pages/fxyp/StructuralAnalysis.jsp?";
	$h.showWindowWithSrc("structuralAnalysis",url,"ͼ�����", 1400, 700,null,{closeAction:'hide'},true);
}
function showET(value, params, record, rowIndex, colIndex, ds) {
	
	if($('#mntp05').val()=='2'){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"showECharts('"+record.data.b01id+"','"+record.data.b0111+"');\">"+value+"</a>"
		+ "</font></div>";
	}else{
		return value;
	}
	
	
}
</script>
<odin:hidden property="mntp01"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="b0111"/>
<odin:hidden property="a0000s"/>
<odin:hidden property="a0000sBD"/>
<odin:hidden property="a0200s"/>
<odin:hidden property="b01id"/>
<div id="grid-example"></div>
<table style="width: 100%;">
  <tr>
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<odin:textEdit property="dwmc" width="80" label="��λ����"/>
				<td><odin:button text="��ѯ" property="searchn" handler="searchn" /></td>
				<td><odin:button text="ѡ��λ" property="selectORG" handler="selectORG" /></td>
				<td class="quxianshi" style="display: none;">
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="2" checked>������</label><br/>
					<!-- <label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="3">������ƽ̨</label> -->
					<label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="1">��ֱ�������У��ƽ̨</label>
					<!-- <label><input type="radio" name="mntp05_r" onclick="changeZSB()" value="4">�����У</label> -->
				</td>
				
			</tr>
		</table>

		<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid2 property="noticeSetgrid" hasRightMenu="false"  load="refreshNoticeSetgrid"
			   pageSize="300" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="jgmc" />
							<odin:gridDataCol name="zzhd" />
							<odin:gridDataCol name="zzsp" />
							<odin:gridDataCol name="fzhd" />
							<odin:gridDataCol name="fzsp" />
							<odin:gridDataCol name="zshd" />
							<odin:gridDataCol name="zssp" />
							<odin:gridDataCol name="bzzs" />
							<odin:gridDataCol name="b0111" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="bz" />
							<odin:gridDataCol name="b0234" />
							<odin:gridDataCol name="b0234_ygrx" />
							<odin:gridDataCol name="b0234_yggw" />
							<odin:gridDataCol name="b0235" />
							<odin:gridDataCol name="b0235_ygrx" />
							<odin:gridDataCol name="b0235_yggw" />
							<odin:gridDataCol name="yxgw" />
							
							
							
							<%-- ���� --%>
							<odin:gridDataCol name="fyqp" />
							<odin:gridDataCol name="jcyqp" />
							<odin:gridDataCol name="bzqpdw" />
							<odin:gridDataCol name="bzqpzf" />
							<odin:gridDataCol name="bzqprd" />
							<odin:gridDataCol name="bzqpzx" />
							<odin:gridDataCol name="fyqp_ygrx" />
							<odin:gridDataCol name="jcyqp_ygrx" />
							<odin:gridDataCol name="bzqpdw_ygrx" />
							<odin:gridDataCol name="bzqpzf_ygrx" />
							<odin:gridDataCol name="bzqprd_ygrx" />
							<odin:gridDataCol name="bzqpzx_ygrx" />
							
							<odin:gridDataCol name="fyqp_yggw" />
							<odin:gridDataCol name="jcyqp_yggw" />
							<odin:gridDataCol name="bzqpdw_yggw" />
							<odin:gridDataCol name="bzqpzf_yggw" />
							<odin:gridDataCol name="bzqprd_yggw" />
							<odin:gridDataCol name="bzqpzx_yggw" />
							
							<odin:gridDataCol name="bzdw" />
							<odin:gridDataCol name="bzzf" />
							<odin:gridDataCol name="bzrd" />
							<odin:gridDataCol name="bzzx" />
							<odin:gridDataCol name="bzspdw" />
							<odin:gridDataCol name="bzspzf" />
							<odin:gridDataCol name="bzsprd" />
							<odin:gridDataCol name="bzspzx" />
							<odin:gridDataCol name="fj" />
							<odin:gridDataCol name="fjsp" />
							
							<odin:gridDataCol name="fy" />
							<odin:gridDataCol name="jcy" />
							<odin:gridDataCol name="fysp" />
							<odin:gridDataCol name="jcysp" />
							
							<odin:gridDataCol name="b0236" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 dataIndex="jgmc" width="210" header="��λ����" align="left" editor="text" edited="false" renderer="showET" menuDisabled="true" hidden="true"/>
							<odin:gridEditColumn2 editor="text" width="40" edited="false" dataIndex="b0234" header="��ְ" renderer="selectGW" menuDisabled="true" align="center" hidden="true"/>
							<odin:gridEditColumn2 dataIndex="b0235" width="40" header="��ְ"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bzqpdw" width="40" header="��ί"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzf" width="40" header="����"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqprd" width="40" header="�˴�"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="bzqpzx" width="40" header="��Э"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="fyqp" width="40" header="Ժ��"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="jcyqp" width="40" header="��鳤"  renderer="selectGW" align="center" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							
							<odin:gridEditColumn2 dataIndex="b0236" width="90" header="��ȱ��λ" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							
							<odin:gridEditColumn2 dataIndex="bz" width="90" header="�������" renderer="displayKQGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="yxgw" width="120" header="�����λ" renderer="displayYXGW" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
							<odin:gridEditColumn2 dataIndex="nmgw" width="120" header="�����λ" renderer="displayYXGW" isLast="true" align="left" menuDisabled="true" editor="text" edited="false"  hidden="true"/>
						</odin:gridColumnModel>
					</odin:editgrid2>
		</div>
	</td>
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<td style="height:25px;">
					����ǰ��
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
			<odin:editgrid2 height="50" ddGroup="pgridBufferDD" property="pgrid" hasRightMenu="false" grouping="true" groupCol="zrrx"
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"��\" : \"��\"]})"
			pageSize="200" isFirstLoadData="false" url="/" 
			autoFill="true" forceNoScroll="true">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="personstatus" />
							<odin:gridDataCol name="a01bzdesc" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0200" />
							<odin:gridDataCol name="a0201b" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="zrrx" />
							<odin:gridDataCol name="fxyp07" />
							<odin:gridDataCol name="fxyp00" />
							<odin:gridDataCol name="tp0100" />
							<odin:gridDataCol name="a0215a" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="60" header="����" align="center"/>
							<odin:gridColumn dataIndex="zrrx" width="120" header="������Ա" hidden="true" renderer="decodeZRRY" align="center"/>
							<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="����ְ��" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="���ֹ�����λ��ְ��" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="ȥ��" editor="text" edited="false" isLast="true" align="left"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid2>
		</div>
    </td>
    
    
    <td width="25%">
    	<table>
		  	<tr>
				<td style="height:25px;">
					�����
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
    	<odin:editgrid2 height="50" property="pgrid2" hasRightMenu="false" grouping="true" groupCol="zrrx" load="pgridLoad"
			groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"��\" : \"��\"]})"
			pageSize="200" isFirstLoadData="false" url="/" 
			autoFill="true" forceNoScroll="false">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				<odin:gridDataCol name="personstatus" />
				<odin:gridDataCol name="a01bzdesc" />
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="a0200" />
				<odin:gridDataCol name="a0201b" />
				<odin:gridDataCol name="b01id" />
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="zrrx" />
				<odin:gridDataCol name="fxyp07" />
				<odin:gridDataCol name="fxyp00" />
				<odin:gridDataCol name="tp0100" />
				<odin:gridDataCol name="a0215a" />
				<odin:gridDataCol name="a0192a" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn dataIndex="a0101" width="60" header="����" align="center"/>
				<odin:gridColumn dataIndex="zrrx" width="120" header="������Ա" hidden="true" renderer="decodeZRRY" align="center"/>
				<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="����ְ��" editor="text" edited="false"  align="left"/>
				<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="���ֹ�����λ��ְ��" editor="text" edited="false"  align="left"/>
				<odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="��Դ" editor="text" edited="false" isLast="true"  align="left"/>
			</odin:gridColumnModel>
				 
		</odin:editgrid2>
		</div>
    </td>
    
    
    <td style="width: 25%;">
    	<table>
		  	<tr>
				<td style="height:25px;">
					����������
				</td>
				<td>
					<!-- <div class="top_btn_style" 
					 style="background-color:#F08000; font-size:14px; margin-left: 130px;
					 width: 80px;" onclick="openRenXuanTiaoJian($('#mntp00').val())">��ѯ����
					</div> -->
					<div class="top_btn_style" 
					 style="background-color:#F08000; font-size:14px; margin-left: 130px;
					 width: 80px;" onclick="rybd()">��Ա�ȶ�
					</div>
				</td>
			</tr>
		</table>
    	<div  style="align:left top;height:100%;overflow:auto;">
			<odin:editgrid2 height="50" ddGroup="noticeSetgridDD" property="pgridBuffer" hasRightMenu="false" 
			pageSize="200" isFirstLoadData="false" url="/"
			autoFill="true" forceNoScroll="true">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="personstatus" />
							<odin:gridDataCol name="a01bzdesc" />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0200" />
							<odin:gridDataCol name="a0201b" />
							<odin:gridDataCol name="b01id" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="zrrx" />
							<odin:gridDataCol name="fxyp07" />
							<odin:gridDataCol name="fxyp00" />
							<odin:gridDataCol name="tp0100" />
							<odin:gridDataCol name="a0215a" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="a0101" width="60" header="����" align="center"/>
							<odin:gridColumn dataIndex="zrrx" width="120" header="������Ա" hidden="true" align="center"/>
							<odin:gridEditColumn2 dataIndex="a0215a" width="80" header="����ְ��" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="���ֹ�����λ��ְ��" editor="text" edited="false"  align="left"/>
							<odin:gridEditColumn2 dataIndex="a01bzdesc" width="40" header="��Դ/ȥ��" editor="text" edited="false" isLast="true"  align="left"/>
						</odin:gridColumnModel>
							 
					</odin:editgrid2>
		</div>
		<div class="OrgInfo">
			<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
		</div>
		<table>
		  	<tr>
				<td style="height:25px;">
					ģ�������
				</td>
				<td>
					<div class="top_btn_style" 
					 style="background-color:#F08000; font-size:14px;
					 width: 80px;" onclick="openViewGW($('#mntp00').val())">��λ��ѡ��
					</div>
					<!-- <div class="top_btn_style" 
					 style="background-color:#F08000; font-size:14px;
					 width: 80px;" onclick="openViewRYBD($('#mntp00').val())">��λ�ȶ�
					</div> -->
				</td>
			</tr>
		</table>
    </td>
  </tr>
</table>



<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	noticeSetgrid.setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgrid2').setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgrid').setHeight(document.body.clientHeight-35);
	Ext.getCmp('pgridBuffer').setHeight(document.body.clientHeight-300);
	$('.OrgInfo').css('height',document.body.clientHeight-Ext.getCmp('pgridBuffer').getHeight()-75);
	
	
	Ext.getCmp('pgrid2').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//����
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//����
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	};
	Ext.getCmp('pgrid').getView().getRowClass=function(record,rowIndex,rowParams,store){
		var fxyp07 = record.data.fxyp07;
		if(fxyp07=='-1'){//����
			return 'x-grid-record-nm';
        }else if(fxyp07=='1'){//����
			return 'x-grid-record-zr';
        }else{
        	return '';
        }
		
	};
	
	
	
	
	

	
	$("#mntp00").val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$("#mntp01").val(parent.Ext.getCmp(subWinId).initialConfig.mntp01);
	var mntp01 = $("#mntp01").val();
	var mntp05 = parent.Ext.getCmp(subWinId).initialConfig.mntp05;
	if(mntp01=='1'||mntp01=='3'){
		mntp05='1';
	}else if(mntp01=='2'){
		mntp05='2';
	}
	$("#mntp05").val(mntp05);
	$("input[name='mntp05_r'][value='"+$("#mntp05").val()+"']").attr('checked',true);
	closeWin();
	
	//������ʾ��
	changeZSB(true);
	
	
	
	
	noticeSetgrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#b01id').val(rc.data.b01id);
		$('#b0111').val(rc.data.b0111);
		radow.doEvent('pgrid.dogridquery');
		radow.doEvent('pgrid2.dogridquery');
		radow.doEvent('showOrgInfo');
	});
	
	/* initGridSort('noticeSetgrid',function(g){
		//radow.doEvent('publishsort');
	});
	initGridSortG2G('pgridBuffer',function(g){
		//radow.doEvent('publishsort');
	}); */
});
function initGridSort(gridId,callback){
    var pgrid = Ext.getCmp(gridId);
    var dstore = pgrid.getStore();
    var firstGridDropTargetEl =  pgrid.getView().el.dom.childNodes[0].childNodes[1];
    GridDrop.init(pgrid);
    var ddrow = new Ext.dd.DropTarget(firstGridDropTargetEl,GridDrop);
}
function initGridSortG2G(gridId,callback){
    var pgrid = Ext.getCmp(gridId);
    var dstore = pgrid.getStore();
    GridDrop2G.init(pgrid,Ext.getCmp('noticeSetgrid'));
    var ddrow = new Ext.dd.DropTarget(pgrid.container,GridDrop2G);
}

function displayYXGW(value, params, record, rowIndex, colIndex, ds) {
	var vArrayGw,vArray;
	var retStr = "";
	if(value){
		vArrayGw = value.split("{RN}");
		for(i=0;i<vArrayGw.length;i++){
			vArray = vArrayGw[i].split("@@");
			retStr += "<div align='left' width='100%' ><font color=rgb(55,100,160)>"
			+ "<a style='cursor:pointer;font-weight:bold' onclick=\"openYouGuanRenXuann('"+vArray[2]+"');\">"+vArray[0]+"</a>"
			+ "</font></div></br>"
		}
		return retStr;
		
	}
	
	return "";
}
function displayKQGW(value, params, record, rowIndex, colIndex, ds) {
	value = (value||"").replace(/\r/gi,"").replace(/\n/gi,"</br>");
	return value;
}
function movePB(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	//+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('DeleteP','"+value+"');\">�Ƴ�</a>"
	+ (record.data.personstatus.indexOf("3")>=0?"<a style='cursor:pointer;' onclick=\"movePB_by_nm('"+record.id+"');\">����ְ</a></br></br>":"")
	+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_movePB('"+record.id+"');\">�Ƴ�</a>"
	+ "</font></div>";
	
}
function moveP(value, params, record, rowIndex, colIndex, ds) {
	var fxyp07 = record.data.fxyp07;
	if(fxyp07=='-1'){//����
		//record.set('personStatus', '2');
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_moveP('"+record.id+"',-1,'"+ds.baseParams.cueGridId+"');\">ȡ��</a>"
		+ "</font></div>";
    }else if(fxyp07=='1'){//����
    	return "<div align='center' width='100%' ><font color=blue>"
    	+ "<a style='cursor:pointer;' onclick=\"refreshNoticeSetgrid_moveP('"+record.id+"',1,'"+ds.baseParams.cueGridId+"');\">ɾ��</a>"
    	+ "</font></div>";
    }else{
    	return '';
    }
	
	
}
function selectGW(value, params, record, rowIndex, colIndex, ds) {
	var DataIndex = Ext.getCmp("noticeSetgrid").getColumnModel().getDataIndex(colIndex);
	var ygrx='',FXYP06='';
	if(value==""||value==null){
		value=0;
	}
	
	if(record.data[DataIndex+"_yggw"]){
		value = parseInt(value) - parseInt(record.data[DataIndex+"_yggw"]);
	}
	
	if(value==0){
		value="";
	}
	
	ygrx = record.data[DataIndex+"_ygrx"];
	FXYP06 = FIELD_RXLB[DataIndex];
	
	var b0236 = (record.data.b0236||"").replace(/[0-9]��/g,"");
	b0236 = b0236.replace(/\r/gi,"").replace(/(\n)|(<\/br>)/gi,function(t){
		return t+(record.data.jgmc||"");
	});
	
	
	
	//�˶� ʵ�� ȱ��˶��ϲ�
	var HD = record.data[QP_HD_SP[DataIndex][0]];
	var SP = record.data[QP_HD_SP[DataIndex][1]];
	
	if(HD==null){
		HD='';
	}
	if(SP==null){
		SP='';
	}
	
	var dwmckqgw = (record.data.jgmc||"")+b0236;
	dwmckqgw = dwmckqgw.replace(/\r/gi,"").replace(/\n/gi,"\\n").replace(/<\/br>/gi,"\\n");
	return "<div align='center' width='100%' >"+(HD+"/"+SP+"<br/>(") +(value||"0")/*+(ygrx=="0"?"/":("/"+ygrx)) */+")<font color=blue>"
	//+ "<a style='cursor:pointer;' onclick=\"openGW('"+FXYP06+"','"+record.data.b01id+"','"+dwmckqgw+"');\">ѡ���λ</a>"
	+ "</font></div>";
	
}




function openRenXuanTiaoJian(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(!newWin_){
		newWin_ = $h.openWin('rxtj','pages.fxyp.RenXuanTiaoJian','�ɲ�������ѡ���� ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_rxtj").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.clearConbtn(true);
		subwindow.reloadtree();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide(); 
	}
}
function openYouGuanRenXuann(fxyp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(!newWin_){
		newWin_ = $h.openWin('ygrx','pages.fxyp.YouGuanRenXuan','�й���ѡ���� ',1250,920,null,g_contextpath,null,
				{maximizable:false,resizable:false,closeAction:'hide',fxyp00:fxyp00||""},true);
	}else{
		newWin_.show(); 
		var subwindow = $h.getTopParent().document.getElementById("iframe_ygrx").contentWindow;
		subwindow.parent.Ext.getCmp(subwindow.subWinId).initialConfig.fxyp00=(fxyp00||"");
		subwindow.reload();
	}
	newWin_.setPosition(newWin_.getPosition()[0],$('body').scrollTop());
	
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide(); 
	}
}
function hideWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.hide();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.hide();
	}
	//ˢ�±��
	//radow.doEvent('noticeSetgrid.dogridquery');
	//radow.doEvent('pgrid.dogridquery');
	//openViewGW($("#mntp00").val());
}
function openViewGW(mntp00){
	$h.openPageModeWin('RXFXYP','pages.fxyp.RXFXYP','�ɲ�ģ�������ѡ�������б�',1250,900,
			{mntp00:mntp00,scroll:"scroll:yes;"},g_contextpath);
}
function openViewRYBD(mntp00){//��Ա�ȶ�
	var b01id = $('#b01id').val();
	var b0111 = $('#b0111').val();
	var mntp05 = $('#mntp05').val();
	if(b0111==''){
		$h.alert('','��ѡ��λ��');
		return;
	}
	$h.openWin('ViewRYBDWin','pages.fxyp.BZRYBD','��Ա�ȶ�',1200,600,'','<%=request.getContextPath()%>',null,{mntp00:mntp00,b01id:b01id,b0111:b0111,mntp05:mntp05},true);
}
function closeWin(){
	var newWin_ = $h.getTopParent().Ext.getCmp('ygrx');
	if(newWin_){
		newWin_.destroy();
	}
	newWin_ = $h.getTopParent().Ext.getCmp('rxtj');
	if(newWin_){
		newWin_.destroy();
	}
	//ˢ�±��
}



var doAddPerson = (function(){//������Ա
	return {
		queryByNameAndIDS_JZHJ:function(fxyp00){//����
			radow.doEvent('queryByNameAndIDS',fxyp00);
		},
		queryByNameAndIDS:function(a0000s){//�����ݴ���
			radow.doEvent('queryByNameAndIDS_ZCQ',a0000s);
		},
		addPgridBuffer:function(data){//�����ݴ���
			var pgridBuffer = Ext.getCmp('pgridBuffer');
			var store = pgridBuffer.getStore();
			
			hideWin();
			for(var i=0;i<data.length;i++){
				store.add(new Ext.data.Record(data[i]));
			}
		}
	}
})();


function openZSGW(){//��ְ����λ����  �Ѿ���ְ����λҳ����
	
}


document.onkeydown=function() {
	if (event.keyCode == 13) {
		var id = document.activeElement.id;
		if(id=='dwmc'){
			searchn();
			return false;
		}
	}
	
};

function changeZSB(fload){
	if($(".quxianshi").is(':visible')){
		var mntp05 = $("input[name='mntp05_r']:checked").val();
		$("#mntp05").val(mntp05);
	}
	
	
	
	var noticeSetgrid = Ext.getCmp('noticeSetgrid');
	//����������ʾҪ��������ݣ���������Ⱦ��Ҫʱ��
	noticeSetgrid.getStore().removeAll();
	//������ʾ��
	var ColumnModel = noticeSetgrid.getColumnModel();
	$(".quxianshi").show();
	if($('#mntp05').val()=='2'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 50 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), false );
		
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), true );
		
	}else if($('#mntp05').val()=='3'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 150 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), true );
		
		
		
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );
	}else if($('#mntp05').val()=='1'||$('#mntp05').val()=='4'){
		ColumnModel.setColumnWidth( ColumnModel.findColumnIndex('jgmc'), 200 );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0234'), false );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('b0235'), false );
		
		
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpdw'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzf'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqprd'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('bzqpzx'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('fyqp'), true );
		ColumnModel.setHidden( ColumnModel.findColumnIndex('jcyqp'), true );

	}
	
	ColumnModel.setHidden( ColumnModel.findColumnIndex('jgmc'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('b0236'), false );
	ColumnModel.setHidden( ColumnModel.findColumnIndex('bz'), false );
	
	if(!fload){
		radow.doEvent('noticeSetgrid.dogridquery');
	}
	
	//radow.doEvent('pgrid.dogridquery');
	
}


Ext.onReady(function () {
    /* Ext.getCmp('pgrid').on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var a01bzdesc = rc.data.a01bzdesc;
		var fxyp07 = rc.data.fxyp07;
		var tp0100 = rc.data.tp0100;
		var a0200 = rc.data.a0200;
		$('#a01bzdesc').val(a01bzdesc);
		if(fxyp07=="1"){
			$('#a01bztype').val("2");
			$('#a01bzid').val(tp0100);
		}else{
			$('#a01bztype').val("1");
			$('#a01bzid').val(a0200);
		}
		openETCWin();
	}); */

})
</script>

<div id="gwInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:select2 property="yxgwSel" width="396" onblur="setGWdis(this)" label="�������θ�λ"/>
		  </tr>
		  
		  <tr>
			<odin:textarea property="dwmckqgw" cols="70" rows="3" label="�������θ�λ"></odin:textarea>
		  </tr>
		  <tr>
			<odin:select2 property="a0201e" label="��Ա���" codeType="ZB129" />
		  </tr>
		  <tr >
		  	<td style="padding-top: 20px;"><label >��ǰ������λ��ְ��</label></td>
		  	<td class="dqgzdwjzw" style="padding-top: 20px;"></td>
		  </tr>
		  <tr>
		  	<td ><label >��������ְ��</label></td>
			<td>
				<odin:hidden property="fxyp06"/>
				<odin:hidden property="b01idkq"/>
				<input type="checkbox" name="mxz" id="mxz" style="border: none!important;display: none;" checked="checked" />
				<!-- <label for="mxz">�Ƿ�����ְ</label> 	 -->
			</td>
		  </tr>
		  <tr >
		  	<td class="NMinfo" colspan="2"></td>
		  </tr>
		</table>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveGWInfo" handler="saveGWInfo" />
		</div>
	</div>
</div>
<div id="mntpbzInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textarea property="a01bzdesc" cols="40" rows="6" label="��Դ/ȥ��"></odin:textarea>
		  </tr>
		</table>
		<odin:hidden property="a01bzid"/>
		<odin:hidden property="a01bztype"/>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="ȷ��" property="savemntpbzInfo" handler="saveMntpbzInfo" />
		</div>
	</div>
</div>
