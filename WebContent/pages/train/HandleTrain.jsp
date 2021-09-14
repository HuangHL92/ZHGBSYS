<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
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

<div id="div_data" style="height: 300;">
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
						<odin:textEdit colspan="4" property="a1131" label="��ѵ�������" width="467"></odin:textEdit>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:numberEdit property="g11020" label="��ѵ���" width="160" ></odin:numberEdit>
						<odin:select2 property="a1101" label="��ѵ�����" width="160" codeType="ZB29"></odin:select2>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:select2  property="a1114" label="��ѵ���쵥λ" width="160" codeType="TRANORG"></odin:select2>
						<odin:select2  property="a1151" label="�Ƿ������ѵ" width="160" codeType="TRANCRJ"></odin:select2>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:dateEdit property="a1107" required="true" label="��ѵ��ʼ����"
								onchange="funcCal()" maxlength="8" selectOnFocus="true"
								format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
							<odin:dateEdit property="a1111" required="true" label="��ѵ��������"
								onchange="funcCal()" maxlength="8" selectOnFocus="true"
								format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
						<%-- <odin:NewDateEditTag property="a1107" isCheck="true" label="��ѵ��ʼ����" maxlength="8"></odin:NewDateEditTag>
						<odin:NewDateEditTag property="a1111" isCheck="true" label="��ѵ��������" maxlength="8"></odin:NewDateEditTag> --%>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit property="g11023" label="��ѵ����" width="160" ></odin:textEdit>
						<odin:textEdit property="g11024" required="true" label="ѧ�ƣ��죩"
								onchange="value=value.replace(/[^\d]/g,'')" width="160"
								readonly="false"></odin:textEdit>
						<%-- <odin:textEdit property="g11024" label="ѧ�ƣ��죩" width="160" ></odin:textEdit> --%>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:textEdit property="g11021" label="ѧʱ" width="160" ></odin:textEdit>
						<odin:textEdit property="g11022" label="��ѵ�ص�" width="160" ></odin:textEdit>
					</tr>
					<tr>
						<td style="width: 20px"></td>
						<odin:select2 property="a1121a" label="��ѵ��������" width="160" codeType="TRANINS"></odin:select2>
						<odin:select2 property="a1127" label="��ѵ��������" width="160" codeType="ZB27"></odin:select2>
					</tr>
					<%-- <tr>
						<td style="width: 20px"></td>
						<odin:textEdit colspan="4" property="g11022" label="��ѵ�ص�" width="467" ></odin:textEdit>
					</tr>--%>
				</table>
				</odin:groupBox>
				<%--<odin:groupBox title="��������">
					<table style="width:100%;">
						<tr><td height="15px"></td></tr>
						<tr align="center">
							<td align="center">
								<div><span onclick="downFile(1)" title="����" style="color: blue;cursor: pointer;">��ѧ�ƻ���Ϣ</span></div>
							</td>
							<td align="center">
								<div><span onclick="downFile(2)" title="����" style="color: blue;cursor: pointer;">�γ̱���Ϣ</span></div>
							</td>
							<td align="center">
								<div><span onclick="downFile(3)" title="����" style="color: blue;cursor: pointer;">��ѧ������Ϣ</span></div>
							</td>
						</tr>
						<tr><td height="15px"></td></tr>
					</table>
				</odin:groupBox> --%>
			</td>
		</tr>
	</table>
<%-- <table id="Ttext" style="width:100%;margin-top: -120px;">
	<tr>
		<td style="width: 20px"></td>
		<odin:numberEdit property="g11020" label="��ѵ���" width="160" ></odin:numberEdit>
		<odin:textEdit property="a1131" label="��ѵ�������" width="160"></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:select2 property="a1101" label="��ѵ�����" width="160" codeType="ZB29"></odin:select2>
		<odin:textEdit property="a1114" label="��ѵ���쵥λ" width="160"></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:NewDateEditTag property="a1107" isCheck="true" label="��ѵ��ʼ����" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" isCheck="true" label="��ѵ��������" maxlength="8"></odin:NewDateEditTag>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="g11021" label="ѡ������" width="160" ></odin:textEdit>
		<odin:textEdit property="g11022" label="��ѵ�ص�" width="160" ></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="g11023" label="��ѵ����" width="160" ></odin:textEdit>
		<odin:textEdit property="g11024" label="ѧ��" width="160" ></odin:textEdit>
	</tr>
	<tr>
		<td style="width: 20px"></td>
		<odin:textEdit property="a1121a" label="��ѵ��������" width="160" ></odin:textEdit>
		<odin:select2 property="a1127" label="��ѵ�������" width="160" codeType="ZB27"></odin:select2>
	</tr>
</table> --%>
<%-- <table id="p" style="width:100%;height:100%;margin-top: 20px;display: none">
	<tr>
		<td align="center">
			<iframe width="70%" height="70%" src="<%=request.getContextPath() %>/pages/train/photo/index.html" id=iframe1></iframe>
		</td>
	</tr>
</table> --%>
<%-- <table style="width:85%;margin-top: 20px;">
	<tr>
		<odin:textarea property="g11025" label="��ѧ�ƻ���Ϣ" rows="8" colspan="150"></odin:textarea>
	</tr>
</table>
<table style="width:85%;margin-top: 20px;">
	<tr>
		<odin:textarea property="g11026" label="��ѧ�γ���Ϣ" rows="8" colspan="150"></odin:textarea>
	</tr>
</table>
<table style="width:85%;margin-top: 20px;">
	<tr>
		<odin:textarea property="g11030" label="��ѧ������Ϣ" rows="8" colspan="150"></odin:textarea>
	</tr>
</table> --%>
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
<odin:hidden property="trainid"/>
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
	parent.Ext.getCmp('grid1').getStore().reload();
	window.close();
}
/* function removeCss(){
	$("#Ttext").css("margin-top","30px");
	//$("#Ttext").removeClass("margin-top: -120px"); //�Ƴ�
} */
function downFile(type){
	radow.doEvent('downFile',type);
}
function outFileZip(){
	var url = g_contextpath+'/PublishFileServlet?method=Train';
	window.location.href=url;
}
function funcCal() {
    var a1107=$("#a1107").val();
    var a1111 =$("#a1111").val();
    if(!feildIsNull(a1107)&&!feildIsNull(a1111)){
        if(timeCheck("a1107","a1111")){
        	//$("#g11024").val(getDaysBetween(a1107,a1111));
        }
    }
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
    if(feildIsNull(end))
    {
        $h.alert("ϵͳ��Ϣ","����ʱ�䲻��Ϊ��");
        return false;
    }
    // end = timeTurnInt (end);
    if(end<start)
    {
        $h.alert("ϵͳ��Ϣ","����ʱ�������ڿ�ʼʱ��");
        return false;
    }
    return true;
}
</script>