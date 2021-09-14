<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

 <div id="girdDiv" style="width: 100%;margin:0;">
 	<table>
        <tr>
        	<odin:select2 property="year"  label="���"  />
 			<td align="right">&nbsp;</td>
 			<odin:textEdit property="xrdx01"  label="�������"  />
 			<td align="right">&nbsp;</td>
    		<odin:select2  property="type" label="ѡ������"  data="['1', '�ϼ�ѡ��'],['2', '����ѡ��'],['3', '������ѵ']" ></odin:select2>
    		<td align="right">&nbsp;</td>
    		<tags:ComBoxWithTree property="bjtype" codetype="PXLX"  width="150"  label="�༶����"  />
    		<td align="right">&nbsp;</td>
        </tr>
    </table>
    <table class="x-form-item2" style="background-color: rgb(209,223,245); border-left: 1px solid rgb(153,187,232);
	border-top: 1px solid rgb(153,187,232); border-right: 1px solid rgb(153,187,232);top: 0px;width: 100%;">
         <tr>
          	<td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="clean" handler="clean"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;ѯ&nbsp;" property="BZQuery" handler="BZQuery"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="BZAdd" handler="BZAdd"></odin:button>
             </td>
             <td align="right" style="width:5%;">
                 <odin:button text="&nbsp;��&nbsp;&nbsp;��&nbsp;" property="export" handler="expExcelFromGrid"></odin:button>
             </td>
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
                          <odin:gridDataCol name="xrdx00"/>
                          <odin:gridDataCol name="year"/>
                          <odin:gridDataCol name="type"/>
                          <odin:gridDataCol name="xrdx01"/>
                          <odin:gridDataCol name="xrdx02"/>
                          <odin:gridDataCol name="xrdx03"/>
<%--                           <odin:gridDataCol name="xrdx04"/> --%>
                          <odin:gridDataCol name="xrdx05"/>
                          <odin:gridDataCol name="xrdx06"/>
                          <odin:gridDataCol name="xrdx07"/>
                          <odin:gridDataCol name="xrdx08"/>
                          <odin:gridDataCol name="xrdx09"/>
                          <odin:gridDataCol name="xrdx10"/>
                          <odin:gridDataCol name="xrdx11"/>
                          <odin:gridDataCol name="xrdx12"/>
                          <odin:gridDataCol name="status"/>
                          <odin:gridDataCol name="caozuo" isLast="true"/>
                      </odin:gridJsonDataModel>
                      <odin:gridColumnModel>
                          <odin:gridRowNumColumn2></odin:gridRowNumColumn2>
                          <odin:gridEditColumn2 dataIndex="xrdx00" width="50" header="id" editor="text" edited="false" align="center" hidden="true"/>
                          <odin:gridEditColumn2 dataIndex="year" width="50" header="���" editor="text" edited="false" align="center"/>
                          <odin:gridEditColumn2 dataIndex="type" width="50" header="ѡ������" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="xrdx01" width="180" header="�༶����" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="xrdx02" width="80" header="�༶����" editor="text" edited="false" align="center" />
						  <odin:gridEditColumn2 dataIndex="xrdx03" width="100" header="���쵥λ" editor="text" edited="false" align="center" />
<%-- 						  <odin:gridEditColumn2 dataIndex="xrdx04" width="50" header="�Ƿ�<br/>������ѵ" editor="select" edited="false" align="center" codeType="TRANCRJ"/> --%>
						  <odin:gridEditColumn2 dataIndex="xrdx05" width="80" header="����ʱ��" editor="text" edited="false" align="center" renderer="dateRenderer"/>
						  <odin:gridEditColumn2 dataIndex="xrdx06" width="80" header="��ҵʱ��" editor="text" edited="false" align="center" renderer="dateRenderer"/>
						  <odin:gridEditColumn2 dataIndex="xrdx07" width="80" header="���ݲ�ѵ<br/>����" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx08" width="40" header="ѧ��" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx09" width="40" header="ѧʱ" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx10" width="100" header="��ѵ�ص�" editor="text" edited="false" align="center"/>
						  <odin:gridEditColumn2 dataIndex="xrdx11" width="80" header="��ѵ��֯" editor="text" edited="false" align="center" />
						  <odin:gridEditColumn2 dataIndex="xrdx12" width="60" header="��༶��" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="status" width="80" header="�༶״̬" editor="text" edited="false" align="center" />
                          <odin:gridEditColumn2 dataIndex="caozuo" width="100" header="����" editor="text" edited="false" isLast="true" align="center" renderer="updateDel"/>
                      </odin:gridColumnModel>
                  </odin:editgrid2>
              </td>
          </tr>
      </table>
 
 </div>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid2 = Ext.getCmp('editgrid');
	grid2.setHeight(viewSize.height/1.167);
})
function dateRenderer(val) {
	if(val!=null && val!=''){
		var length=val.length;
	    if ( val.length === 8) {
	        return val.substr(0, 4) + "." + val.substr(4, 2)+ "." +val.substr(6, 2);
	    } else {
	        return val;
	    }
	}else{
		return val;
	}
	
	
}
function updateDel(value, params, record, rowIndex, colIndex, ds){
	//return "<font color=blue><a style='cursor:pointer;' onclick=\"updatepel('"+record.get("sh000")+"','"+record.get("yy_flag")+"');\">ά��</a>&nbsp&nbsp<a style='cursor:pointer;' onclick=\"deletepel('"+record.get("sh000")+"','"+record.get("a0000")+"');\">ɾ��</a></font>";
	return "<font color=blue><a style='cursor:pointer;' onclick=\"updateBZ('"+record.get("xrdx00")+"');\">����</a>&nbsp;&nbsp;&nbsp;&nbsp;<a style='cursor:pointer;' onclick=\"delBZ('"+record.get("xrdx00")+"');\">ɾ��</a></font>";
}
function updateBZ(xrdx00){
	if(xrdx00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����ݣ�');
		return;
	}
	// $h.openPageModeWin('updateTrain', 'pages.train.HandleTrain', '�鿴(�޸�)�༶��Ϣ', 651, 350, trainid, g_contextpath);
	 $h.openPageModeWin('addXRDX','pages.edu.xrdxAdd','�������',651,400,xrdx00,g_contextpath);
	//$h.openPageModeWin('impFuJian','pages.train.ImpFuJian','�ϴ�����',500,300,{trainid:trainid},g_contextpath);
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
    excelName ="�������";

	odin.grid.menu.expExcelFromGrid('editgrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}
function delBZ(xrdx00){
	if(xrdx00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����ݣ�');
		return;
	}
	$h.confirm("ϵͳ��ʾ��","�Ƿ�ȷ��ɾ����",200,function(id) { 
		if("ok"==id){
			radow.doEvent("delBZ",xrdx00);
		}else{
			return false;
		}		
	});
}
var g_contextpath = '<%= request.getContextPath() %>';
function clean(){
	document.getElementById("year").value ='';
	document.getElementById("year_combo").value ='';
	document.getElementById("xrdx01").value ='';
	document.getElementById("type").value ='';
	document.getElementById("type_combo").value ='';
	document.getElementById("bjtype").value ='';
	document.getElementById("bjtype_combotree").value ='';

}
function BZQuery(){
    radow.doEvent("editgrid.dogridquery");
}

function BZAdd(){
	$h.openPageModeWin('addXRDX','pages.edu.xrdxAdd','�������',651,500,'',g_contextpath);

}
function BZDel(){

}

 </script>
 