<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>
<style>
.x-grid3-cell-inner, .x-grid3-hd-inner{
  white-space:normal !important;
}

</style>
<div id="girdDiv" style="width: 100%;margin:0;">
	<table>
       <tr>
       	<odin:textEdit property="a0101"  width="100" label="����"  />
		<td align="right">&nbsp;</td>
   		<odin:select2  property="a0104" width="100"  label="�Ա�"  data="['1', '��'],['2', 'Ů']" ></odin:select2>
   		<td align="right">&nbsp;</td>

		<odin:select2 property="a0165B" width="100"  label="�йܸɲ�" data="['10', '�й���ְ'],['11', '�йܸ�ְ'],['18','��ֱ���飨��ί����Ա����Ѳ'],['19', '�й�ְ������Ա�����˳��쵼��λ��'],['04', '�й�����']" multiSelect="true"></odin:select2>
        <td align="right">&nbsp;</td>
        <odin:select2 property="a0165C" width="100"  label="�������в㣩�ɲ�" data="['12', '��ֱ����'],['50', '��ֱ����'],['07', '������ְ'],['08', '���ܸ�ְ'],['09', '���й���ְ'],['13', '���йܸ�ְ'],['51','�������ּ���ҵ��ְ'],['60','�������ּ���ҵ�в㸱ְ'],['52','����']" multiSelect="true"></odin:select2>
        <odin:dateEdit property="a0288" width="100"  label="����ְ����ʱ������"
		 maxlength="8" selectOnFocus="true"
		format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
		<td align="right">&nbsp;</td>
      	<td noWrap="nowrap" align=right ><span id="ageASpanId" style="FONT-SIZE: 12px">��������</span>&nbsp;</td>
				<td >
					<table  ><tr>
						<odin:numberEdit property="a0107A"  maxlength="6" width="62" />
						<td><span style="font: 12px">��</span></td>
						<odin:numberEdit property="a0107B" maxlength="6" width="62" />
					</tr></table>
				</td>
		<odin:textEdit property="xrdx01"  width="100"  label="�������"  />
		<tags:ComBoxWithTree property="xrdx02" codetype="PXLX"  width="100"  label="�༶����"  ischecked="true" />


	  </tr>
	  <tr>
  		<odin:textEdit property="xrdx08" width="100"  label="ѧ�ƴ���"  />
		<td align="right">&nbsp;</td>
	    <odin:dateEdit property="xrdx05"width="100"  label="����ʱ������"
		 maxlength="8" selectOnFocus="true"
		format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
		<td align="right">&nbsp;</td>
		<odin:textEdit property="xrdx09" width="100"  label="�ϼ�ѧʱС��"  />
		<td align="right">&nbsp;</td>	
		<odin:dateEdit property="scpxsj" width="100"  label="�ϴ��г�����ѵ����"
		 maxlength="8" selectOnFocus="true"
		format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
		<odin:textEdit property="scpxsc" width="100"  label="�ϴ��г�����ѵѧ�� " maxlength="6" />
		<td align="right">&nbsp;</td>
		<td><label style="FONT-SIZE: 12px">��ʾʡ�ܸɲ�
		</label></td>
		<td>&nbsp;<input id="sggb" name="sggb" type="checkbox" value="1" checked="true"/></td> 
		<tags:ComBoxWithTree property="a0141" label="��&nbsp;&nbsp;&nbsp;��" readonly="true" ischecked="true" width="100" codetype="GB4762" />
		<odin:select2  property="type" label="ѡ������" width="100" data="['1', '�ϼ�ѡ��'],['2', '����ѡ��'],['3', '������ѵ']"  multiSelect="true"></odin:select2>

		<td align="right">&nbsp;</td>
      </tr>
   </table>
   <table class="x-form-item2" style="background-color: rgb(209,223,245); border-left: 1px solid rgb(153,187,232);
border-top: 1px solid rgb(153,187,232); border-right: 1px solid rgb(153,187,232);top: 0px;width: 100%;">
        <tr>
         	<td align="right" style="width:5%;">
                <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="clean"  handler="clean"></odin:button>
            </td>
            <td align="right" style="width:5%;">
                <odin:button text="&nbsp;��&nbsp;&nbsp;ѯ&nbsp;" property="BZQuery" handler="gridquery"></odin:button>
            </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="export" handler="expExcelFromGrid"></odin:button>
             </td>
              <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;ϸ&nbsp;" property="exporttotal" handler="opentotal"></odin:button>
             </td>
<%--             <td align="right" style="width:5%;">
                <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="BZAdd" ></odin:button>
            </td> --%>
<%--              <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="BZUpdate" handler="BZUpdate"></odin:button>
             </td> --%>
<%--              <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;ɾ&nbsp;&nbsp;��&nbsp;" property="BZDel"  handler="BZDel"></odin:button>
             </td> --%>

            <td align="right">&nbsp;</td>
        </tr>
    </table>
    <table style="width: 100%;">
         <tr>
             <td>
                 <odin:editgrid2 property="editgrid" hasRightMenu="false" topBarId="btnToolBar" width="100%" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
                     <odin:gridJsonDataModel >
                         <odin:gridDataCol name="a0000"/>
                         <odin:gridDataCol name="a0101"/>
                         <odin:gridDataCol name="a0184"/>
                         <odin:gridDataCol name="a0165"/>
                         <odin:gridDataCol name="a0288"/>
                         <odin:gridDataCol name="a3707c"/>
                         <odin:gridDataCol name="b0101"/>
                         <odin:gridDataCol name="a0104"/>
                         <odin:gridDataCol name="a0107"/>
                         <odin:gridDataCol name="a0192a"/>
                         <odin:gridDataCol name="a0140"/>
                         <odin:gridDataCol name="xrdx00"/>
                         <odin:gridDataCol name="xrdx01"/>
                         <odin:gridDataCol name="xrdx02"/>
                         <odin:gridDataCol name="xrdx03"/>
                         <odin:gridDataCol name="xrdx04"/>
                         <odin:gridDataCol name="xrdx05"/>
                         <odin:gridDataCol name="xrdx06"/>
                         <odin:gridDataCol name="xrdx07"/>
                         <odin:gridDataCol name="xrdx08"/>
                         <odin:gridDataCol name="xrdx09"/>
                         <odin:gridDataCol name="xrdx10"/>
                         <odin:gridDataCol name="xrdx11"/>
                         <odin:gridDataCol name="xrdx12"/>
                         <odin:gridDataCol name="zts"/>
                         <odin:gridDataCol name="zxs" isLast="true"/>
                     </odin:gridJsonDataModel>
                     <odin:gridColumnModel>
                         <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                         <odin:gridEditColumn2 dataIndex="a0000" width="50" header="id" editor="text" edited="false" align="center" hidden="true"/>
                         <odin:gridEditColumn2 dataIndex="a0101" width="50" header="����" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="a0104" width="30" header="�Ա�" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="a0107" width="50" header="��������" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="a0165" width="50" header="�������"  editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="a3707c" width="50" header="�ֻ�����" editor="text" edited="false" align="center"/> 
						  <odin:gridEditColumn2 dataIndex="a0184" width="70" header="���֤��" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="b0101" width="50" header="��ְ����" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="a0192a" width="120" header="����ְ��" editor="text" edited="false" align="center"/>
						 <odin:gridEditColumn2 dataIndex="a0288" width="50" header="����ְ��<br/>���ʱ��" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0140" width="50" header="�뵳ʱ��" editor="text" edited="false" align="center"/> 
						  <odin:gridEditColumn2 dataIndex="bcxx"   width="600"   
						  header="<table width='100%'><tr><td align='center' width='100' valign='middle' style='border:none' >�������</td><td align='center' width='50' valign='middle' style='border:none'>�������</td><td  align='center' width='50' valign='middle' style='border:none'>��ʼʱ��</td><td align='center'  width='50' valign='middle' style='border:none'>��ҵʱ��</td><td align='center'  width='30' valign='middle' style='border:none'>ѧ��</td><td align='center'  width='30' valign='middle' style='border:none'>ѧʱ</td><td align='center'  width='100' valign='middle' style='border:none'>��ѵ�ص�</td></tr></table>" editor="text"  edited="false" renderer="bcxxRenderer" align="left"  />
	                     <odin:gridEditColumn2 dataIndex="zts" width="50" header="�ϼ�����" editor="text" edited="false" align="center" />
	                     <odin:gridEditColumn2 dataIndex="zxs" width="50" header="�ϼ�ѧʱ" editor="text" edited="false" align="center" isLast="true"/>     
	                 </odin:gridColumnModel>
                 </odin:editgrid2>
             </td>
         </tr>
     </table>

</div>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('editgrid');
	grid2.setHeight(viewSize.height/1.2);
})







function bcxxRenderer(value, params, record, rowIndex, colIndex, ds) {
	if(record.get("xrdx00")==null){
		return result;
	}
	if(record.get("xrdx00").indexOf(",")==-1){
		result = "<table width='100%'><tr><td align='center' width='100' valign='middle' style='border-bottom:1px solid #DDDDDD' >"+record.get("xrdx01")+
		"</td><td align='center' width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx02")+
		"</td><td  align='center' width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx05")+
		"</td><td align='center'  width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx06")+
		"</td><td align='center'  width='30' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx08")+
		"</td><td align='center'  width='30' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx09")+
		"</td><td align='center'  width='100' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+record.get("xrdx10")+
		"</td></tr></table>";
		return result;
	}
	var xrdx00s = record.get("xrdx00").split(",");
	var xrdx01s = record.get("xrdx01").split("@");
	var xrdx02s = record.get("xrdx02").split(",");
	var xrdx05s = record.get("xrdx05").split(",");
	var xrdx06s = record.get("xrdx06").split(",");
	var xrdx08s = record.get("xrdx08").split(",");
	var xrdx09s = record.get("xrdx09").split(",");
	var xrdx10s = record.get("xrdx10").split(",");
	var result = "<table width='100%'>";
	for(var i=0;i<xrdx00s.length;i++){
		result =result + "<tr><td align='center' width='100' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx01s[i]+
		"</td><td align='center' width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx02s[i]+
		"</td><td align='center' width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx05s[i]+
		"</td><td align='center'  width='50' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx06s[i]+
		"</td><td align='center'  width='30' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx08s[i]+
		"</td><td align='center'  width='30' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx09s[i]+
		"</td><td align='center'  width='100' valign='middle' style='border-bottom:1px solid #DDDDDD'>"+xrdx10s[i]+
		"</td></tr>";	
	}
	/* result = result.substring(0,result.length-4); */
	result = result+"</table>"
	return result;
}
function expExcelFromGrid(){
	


    var excelName = null;
    //excel�������Ƶ�ƴ��
    var pgrid = Ext.getCmp('editgrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('ϵͳ��ʾ��', 'û��Ҫ���������ݣ�', null, 180);
        return;
    }
/*     odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false); */

/* 
        excelName = "��Ա��Ϣ" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd");  */
    excelName ="��ѵ��Ϣ��Ա����";

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}
function clean(){
	document.getElementById("a0104").value ='';
	document.getElementById("a0104_combo").value ='';
	document.getElementById("a0101").value ='';
	document.getElementById("xrdx05").value ='';
	document.getElementById("a0165B").value ='';
	Ext.getCmp('a0165B_combo').setValue('')
//	document.getElementById("a0165B_combo").value ='';
	document.getElementById("a0165C").value ='';
//	document.getElementById("a0165C_combo").value ='';
	Ext.getCmp('a0165C_combo').setValue('')	
	document.getElementById("a0107A").value ='';
	document.getElementById("a0107B").value ='';
	document.getElementById("a0288").value ='';
	document.getElementById("xrdx08").value ='';
	document.getElementById("xrdx09").value ='';
	document.getElementById("scpxsc").value ='';
	document.getElementById("scpxsj").value ='';
	document.getElementById("xrdx01").value ='';
	document.getElementById("xrdx02").value ='';
	document.getElementById("xrdx02_combotree").value ='';
	Ext.getCmp("xrdx02_combotree").clearCheck();
	document.getElementById("type").value ='';
	Ext.getCmp('type_combo').setValue('');	
	document.getElementById("a0141").value ='';
	document.getElementById("a0141_combotree").value ='';
	Ext.getCmp("a0141_combotree").clearCheck();
}
function gridquery(){
	
	radow.doEvent("editgrid.dogridquery");
}
var g_contextpath = '<%= request.getContextPath() %>';
function opentotal(){
	$h.openWin('pxxxList', 'pages.edu.pxxxList', '', 1410,900, null, g_contextpath, null, 
			{ maximizable: false,resizable: false,closeAction: 'close',listtype:'0'})
}
 </script>
 