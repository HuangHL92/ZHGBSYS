<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>

<ss:hlistDiv id="div_1" cols="6" >
	<ss:textEdit property="aae135_a" label="身份证"/>
	<ss:textEdit property="aac003_a" label="姓名"/>
	<ss:textEdit property="eac001_a" label="个人编码"/>
	<ss:textEdit property="aaz501_a" label="卡号"/>
	<ss:textEdit property="eac213_a" label="姓名拼音"/>
	<ss:textEdit property="eab014_a" label="单位类型"/>
	<ss:numberEdit property="aaz001_a" label="单位内码"  p="H"/>
	<ss:numberEdit property="aac001_a" label="人员内码" p="H" />
	<ss:textEdit property="flag" label="刷新标志" p="H"/>
	<ss:textEdit property="iscpflag" label="是否带单位" p="H"/>
	
</ss:hlistDiv>
<odin:editgrid property="div_2"  pageSize="10"  bbarId="pageToolBar"  isFirstLoadData="false" autoFill="false" url="/" title=""  height="190" load="loadStore" sm="row" rowDbClick="rDbClick">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="aac001" />
		<odin:gridDataCol name="aaz001" />
		<odin:gridDataCol name="cpquery" />
		<odin:gridDataCol name="aae135" />
		<odin:gridDataCol name="aac003" />
		<odin:gridDataCol name="aac004" />
		<odin:gridDataCol name="eac001" />
		<odin:gridDataCol name="eac070" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aaf015" />
		<odin:gridDataCol name="aab023" />
		<odin:gridDataCol name="aac009" />
		<odin:gridDataCol name="eab014" />
		<odin:gridDataCol name="aac001" />
		<odin:gridDataCol name="eac157" />
		<odin:gridDataCol name="eac158" />
		<odin:gridDataCol name="aic020" />
		<odin:gridDataCol name="aac006" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="cpcombo" />
		<odin:gridDataCol name="rowstocol" />
		<ss:gridDataBatchCol keyCol="aae140" batchFilter="aaa102 in('11','31','21','41','51','61')" valueCol="aac008"></ss:gridDataBatchCol>
		<odin:gridDataCol name="eab219" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<ss:gridEditColumn header="人员内码" width="50" dataIndex="aac001" editor="number" p="H"/>
		<ss:gridEditColumn header="单位内码" width="50" dataIndex="aaz001" editor="number" p="H"/>
		<ss:gridEditColumn header="单位查询" width="50" dataIndex="cpquery" editor="number" p="H"/>
		<ss:gridEditColumn header="身份证号码"  dataIndex="aae135" editor="text" width="137"/>
		<ss:gridEditColumn header="姓名"  dataIndex="aac003" editor="text" width="90" p="D"/>
		<ss:gridEditColumn header="性别" dataIndex="aac004" editor="select" codeType="AAC004" width="60" p="D"/>
		<ss:gridEditColumn header="个人编码" dataIndex="eac001" editor="text" width="80" p="D"/>
		<ss:gridBatchCol editor="select" keyCol="aae140" batchFilter="aaa102 in('11','31','21','41','51','61')" valueCol="aac008" width="100" p="D"/>
		<ss:gridEditColumn header="性质" dataIndex="eac070" editor="select" width="70" codeType="EAC070" p="D"/>
		<%if(ObjectUtil.equals(request.getParameter("isCpflag"),"true")){ %>
			<ss:gridEditColumn header="辖区" dataIndex="aab301" editor="select" codeType="AAB301" width="80" p="D"/>
			<ss:gridEditColumn header="单位名称" dataIndex="aab004" editor="text" width="135" p="D"/>
			<ss:gridEditColumn header="单位编码" dataIndex="aab001" editor="text" width="80" p="D"/>
			<ss:gridEditColumn header="街道" width="50" dataIndex="aaf015" editor="select" width="100" codeType="AAF015" p="D"/>
			<ss:gridEditColumn header="主管部门" width="70" dataIndex="aab023"
				editor="select" codeType="AAB023" width="90" p="D"/>
			<ss:gridEditColumn header="户籍性质" width="60" dataIndex="aac009"
				editor="text" p="H"/>
			<ss:gridEditColumn header="单位类型" width="75" dataIndex="eab014"
				editor="text" p="H"/>
			<ss:gridEditColumn header="人员内码" width="75" dataIndex="aac001"
				editor="number" p="H"/>
			<ss:gridEditColumn header="人员类别明细" width="75" dataIndex="eac157"
				editor="text" p="H"/>
			<ss:gridEditColumn header="人员类别汇总" width="75" dataIndex="eac158"
				editor="text" p="H"/>
			<ss:gridEditColumn header="上报工资" width="75" dataIndex="aic020"
				editor="number" p="H"/>
		<%}else{%>
			<ss:gridEditColumn header="单位编码" width="160" dataIndex="aab001" editor="text" p="H"/>
			<ss:gridEditColumn header="单位名称" width="160" dataIndex="aab004" editor="text" p="H"/>
			<ss:gridEditColumn header="参保单位" width="160" dataIndex="rowstocol" editor="text" p="D"/>
		<%}%>
		
		<ss:gridEditColumn header="出生日期" width="75" dataIndex="aac006" editor="text" p="H"/>	
		<ss:gridEditColumn header="组织托收类型" width="75" dataIndex="eab219" editor="text" isLast="true" p="H"/>
	</odin:gridColumnModel>		
</odin:editgrid>


<script>
isQueryDiv='div_2';
function loadStore(){
	var g=odin.ext.getCmp('div_2');
	var s=g.store;
	var c=s.getCount();
	var errmg="社会保险系统内没有要查找的人员！";
	switch(c){
	case 0: 
			parent.odin.error("社会保险系统内没有要查找的人员！");
			parent.radow.cm.setValid(parent.commParams.currentColumn,false,errmg);
			parent.odin.unmask();
			break;
	case 1:rDbClick(g,0);
			break;
	default:
		var windowId = "win_pup";
		var pupWindow = parent.Ext.getCmp(windowId);
		pupWindow.show();
		parent.odin.unmask();
		break;
	}
}
function rDbClick(g,i){
	var s=g.store;
	var r=s.getAt(i);
	if(document.getElementById("iscpflag").value== false || document.getElementById("iscpflag").value =='false' || document.getElementById("iscpflag").value==''){
		var params = {};
		params.querySQL = "select aab001 aaa102,aab004 aaa103,aaz001 from sbdv_acg8 a where aac001="+ r.data.aac001+"" ;
		params.sqlType = "SQL";
		var req = odin.commonQuery(params, successFunc, null, false, false);
		var data = odin.ext.decode(req.responseText).data.data;
		r.set('aab001',data[0].aaa102);
		r.set('cpquery',data[0].aaa102);
		r.set('aab004',data[0].aaa103);
		r.set('aaz001',data[0].aaz001);
		r.set('cpcombo',odin.ext.encode(data));
		
	}
	fillParentData(r.data,parent.commParams.currentColumn);
}
  function successFunc(responseTxt) { // 空的ajax调用成功的处理函数
		//
	}


</script>