<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%String ctxPath = request.getContextPath(); 
%>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script> --%>
<script
	src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: #F6F6F6;
}
.vueBtn {
    display: inline-block;
    padding: .3em .5em;
    background-color: #6495ED;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: .3em;
    box-shadow: 0 1px white inset;
    text-align: center;
    text-shadow: 0 1px 1px black;
    color:white;
    font-weight: bold;
	cursor:pointer;
}
</style>

<div id="div_data" style="height: 350;">
	<table  style="width:100%;height:100%;margin-top: 20px;">
		<tr>
			<td id="p" align="left" width="50%" style="display: none">
				<iframe width="95%" height="100%" src="<%=request.getContextPath() %>/pages/train/photo/index.html" id=iframe1></iframe>
			</td>
			<td align="center" valign="" width="50%">
				<odin:groupBox title="�����Ϣ">
				<table id="Ttext" style="width:100%;">
				<tr>
						<td style="width: 20px"></td>
						<odin:select2 property="type" label="ѡ������" width="160" data="['1','�ϼ�ѡ��'],['2','����ѡ��'],['3','������ѵ']" onchange="typechange" required="true"></odin:select2>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit colspan="4" property="xrdx01" label="�༶����" width="467" required="true"></odin:textEdit>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:select2  property="year" label="��ѵ���" width="160"  required="true"></odin:select2>
						<tags:ComBoxWithTree property="xrdx02" codetype="PXLX"  width="150"  label="�༶����"  nodeDblclick="lxchange" required="true"/>
<%-- 						<odin:select2 property="xrdx02" label="�༶����" width="160" codeType="ZB29"></odin:select2> --%>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:select2  property="xrdx03" label="���쵥λ" width="160" codeType="TRANORG"></odin:select2>
<%-- 						<odin:select2  property="xrdx04" label="�Ƿ������ѵ" width="160" codeType="TRANCRJ"></odin:select2> --%>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:dateEdit property="xrdx05" required="true" label="��������"
								onchange="funcCal()" maxlength="8" selectOnFocus="true"
								format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')"  />
							<odin:dateEdit property="xrdx06" required="true" label="��ҵ����"
								onchange="funcCal()" maxlength="8" selectOnFocus="true"
								format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit property="xrdx07" label="���ݲ�ѵ����" width="160" ></odin:textEdit>
						<odin:textEdit property="xrdx08" required="true" label="ѧ�ƣ��죩"
								onchange="xzchange()" width="160"
								readonly="false"></odin:textEdit>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit property="xrdx09" label="ѧʱ" width="160" readonly="true"></odin:textEdit>
						<odin:textEdit property="xrdx10" label="��ѵ�ص�" width="160" ></odin:textEdit>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit property="xrdx11" label="��ѵ��֯" width="160"></odin:textEdit>
						<odin:select2 property="xrdx12" label="��༶��" width="160" codeType="ZB27"></odin:select2>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textarea colspan="4" property="remark"   label="��ע"></odin:textarea>
					</tr>

				</table>
				</odin:groupBox>

			</td>
		</tr>
	</table>

</div>
<div id="bottomDiv" style="width: 100%;">
					<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<td><div onclick="save()" class="vueBtn">�� ��</div>
							</td>
						<tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>
<odin:hidden property="xrdx00"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function(){
	var div_data = document.getElementById("div_data");
	/* var bottomDiv = document.getElementById("bottomDiv"); */
	var viewSize = Ext.getBody().getViewSize();
	div_data.style.height = viewSize.height-82;
	div_data.style.width =  viewSize.width;
	/* bottomDiv.style.width =  viewSize.width; */
});
function loadPhoto(data){
	$("#p").show();
	//����iframe�е�ͼƬ
	var iframe1=window.frames["iframe1"];
	iframe1.loadData(data);
}
function save(){
	radow.doEvent('save');
}
function saveCallBack(){
	parent.Ext.getCmp('editgrid').getStore().reload();
	window.close();
}

function funcCal() {
    var xrdx05=$("#xrdx05").val();
    var xrdx06 =$("#xrdx06").val();
    if(!feildIsNull(xrdx05)&&!feildIsNull(xrdx06)){
        if(timeCheck("xrdx05","xrdx06")){
        	//$("#xrdx08").val(getDaysBetween(xrdx05,xrdx06));
        }
    }
}
function lxchange() {
	 var xrdx02=$("#xrdx02").val();
	 if(xrdx02=='0203'){
		 $('#xrdx09').removeAttr("readonly");
	 }else{
		 $('#xrdx09').attr("readonly","readonly")
	 }
	 

}
function xzchange() {
	 var xrdx02=$("#xrdx02").val();
	 if(xrdx02=='0203'){
		 return;
	 }
	 var xrdx08=$("#xrdx08").val();
	 var xrdx09=(parseFloat(xrdx08).toFixed(2))*8;
	 $("#xrdx09").val(xrdx09);
}
function  getDaysBetween(dateString1,dateString2){
    var  startDate = Date.parse(dateString1);
    var  endDate = Date.parse(dateString2);
    if (startDate>endDate){
        return 0;
    }
    if (startDate==endDate){
        return 1;
    }
    var days=(endDate - startDate)/(1*24*60*60*1000)+1;
    return  days;
}

//���ݱ�����֤�ǿ�  {id:"",type:'text',""}
// [
//     {id:"",type:'text',""}
// ]
function timeCheck(startTime,endTime){
    var start = document.getElementById(startTime).value;
    if(feildIsNull(start))
    {
        $h.alert("ϵͳ��Ϣ","��ʼʱ�䲻��Ϊ��");
        return false;
    }
    // start = timeTurnInt (start);
    var end = document.getElementById(endTime).value;
/*     if(feildIsNull(end))
    {
        $h.alert("ϵͳ��Ϣ","����ʱ�䲻��Ϊ��");
        return false;
    } */
    // end = timeTurnInt (end);
    if(end<start)
    {
        $h.alert("ϵͳ��Ϣ","����ʱ�������ڿ�ʼʱ��");
        return false;
    }
    return true;
}
function typechange(){
	radow.doEvent('typechange');
}
</script>