<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin" %>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>

<style>
   
#etcInfo input{
	border: 1px solid #c0d1e3 !important;
	
}
/* #content   input[type=radio]{
width:100px;
}  */
#text1 textarea{
	width:400px;
	height:40px
} 
.title{
	font-weight:700;
	color:#FF0000
}
</style>

<odin:toolBar property="toolBar">
    <odin:fill/>
    <odin:buttonForToolBar text="����" id="save" isLast="true"/>
</odin:toolBar>

<div id="content">
    <odin:groupBox title="��ʵ��ϸ��Ϣ" property="groupBox">
        <table 	>
	        <tr align="center">
		    	<td colspan='8'  align="center">
		    	<label><input type="radio" id='r1' name="radio_1" value="1" onclick="updateMC()" >ѡ����Ա</label>
		    	<label><input type="radio"  name="radio_1" value="2" onclick="updateMC()" >�ֶ������Ա</label>
		    	<td>
			</tr>
            <tr id='tr1'>
                <odin:textEdit property="a0101_1" label="����" readonly="true" width="72"  onclick="openSelectPersonWin()"/>
                <odin:NewDateEditTag property="a0107_1" label="��������"  readonly="true" width="72"  ></odin:NewDateEditTag>
                <odin:textEdit property="a0192a_1" label="����ְ��" readonly="true" width="210" />
                <odin:NewDateEditTag property="a0192f_1" label="����ְʱ��"  readonly="true" width="72" />
            </tr>
            <tr id='tr2'>
                <odin:textEdit property="a0101_2" label="����" width="72"/>
                <odin:NewDateEditTag property="a0107_2" label="��������" width="72"  />
                <odin:textEdit property="a0192a_2" label="����ְ��" width="210"/>
                <odin:NewDateEditTag property="a0192f_2" label="����ְʱ��" width="72"/>
            </tr>
<%--             <tr id="text1">
            	<odin:textarea property="srzw" label="ʱ��ְ��" colspan='4'/>
                <odin:textarea property="js01" label="�Ƹ��������<br/>�������Ƹ���Σ�" colspan='4'/>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">�����Ƽ��������Ƽ�����</td>
            </tr>
            <tr>
                <odin:textEdit property="js02a" label="��Ʊ��" width="72"/>
                <odin:textEdit property="js02b" label="������" width="72"/>
                <odin:textEdit property="js02c" label="����" width="72"/>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">�����Ƽ���̸���Ƽ�����</td>
            </tr>
            <tr>
                <odin:textEdit property="js03a" label="��Ʊ��" width="72"/>
                <odin:textEdit property="js03b" label="������" width="72"/>
                <odin:textEdit property="js03c" label="����" width="72"/> 
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">���������</td>
            </tr>
            <tr>
            	<td  colspan="2"  align="center"><label style="FONT-SIZE: 12px">�Ƿ���п���</label>
				<input name="js04a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="2"  align="center"><label style="FONT-SIZE: 12px;">���޲�����Ϊ������������
				<input name="js04b" id="js04b" type="checkbox" value="1" /></label></td>
            </tr>
            <tr>
                <td  class='title' colspan='8' align="center">���飨��ί������</td>
            </tr>
            <tr>
                <odin:textEdit property="js05a" label="Ӧ�λ�����" width="72"/>
                <odin:textEdit property="js05b" label="ʵ�ʲλ�����" width="72"/>
                <odin:textEdit property="js05c" label="�λ�ͬ������" width="72"/> 
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">��ְ��ʾû�з�ӳ</label>
				<input name="js06a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">��ְ��ʾ�з�ӳ����Ӱ��ʹ��</label>
				<input name="js06b" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">�Ƿ�����ͼ���������</label>
				<input name="js07" id="js04a" type="checkbox" value="1" /> </td>
				<td width="80"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">�����й���������Ƿ�һ��</label>
				<input name="js08" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr>
            	<td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">�ɲ������Ƿ�������</label>
				<input name="js09a" id="js04a" type="checkbox" value="1" /> </td>
				<td width="120"></td>
                <td  colspan="4"  align="center"><label style="FONT-SIZE: 12px">�ɲ���������������һ��ݡ��Ƿ��������</label>
				<input name="js09b" id="js04b" type="checkbox" value="1" /> </td>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js10" label="�����Ƽ�ʱ��" width="72"  />
                <odin:NewDateEditTag property="js11" label="̸���Ƽ�ʱ��" width="72"/>
                <odin:NewDateEditTag property="js12" label="����̸��ʱ��" width="72"  />
                <odin:NewDateEditTag property="js13" label="����ͼ������ʱ��" width="72"/>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js14" label="�ͼ��첿�Żظ�ʱ��" width="72"  />
                <odin:NewDateEditTag property="js15" label="���������ѯί��ʱ��" width="72"/>
                <odin:NewDateEditTag property="js16" label="���������˴���ʱ��" width="72"  />
                <odin:NewDateEditTag property="js17" label="�������һ��ݺ˶�ʱ��" width="72"/>
            </tr>
            <tr >
                <odin:NewDateEditTag property="js18" label="���������ʱ��" width="72"  />
                <odin:NewDateEditTag property="js19a" label="��ǰ��ʾʱ�䷢����ʾ֪ͨʱ��" width="72"/>
                <odin:textEdit property="js19b" label="��ʾ����" width="144"  />
            </tr>
            <tr >
                <odin:NewDateEditTag property="js20" label="����ʱ�䣨���ʱ�䣩" width="72"  />
                <odin:textEdit property="js21" label="���δ���" width="72"/>
                <odin:select2  property="js22" label="�������"  data="['1','���'],['2','ת��'],['3','��ί���ֳ���ת��'],['4','����Ѳ��Ա����Ѳ��Ա'],['5','���ּ���֯Ա'],['6','����ְ������Ա']" ></odin:select2>
            </tr> --%>
<%--             <tr>
                <odin:select2  property="rank" label="����"  data="['1', '����'],['2', 'ʡ'],['3', '��'],['4', '������']" ></odin:select2>
               	<odin:select2  property="dbwy" label="����ίԱ"  data="['1', '��ίίԱ'],['2', '�˴�ί'],['3', '�˴����'],['4', '��ЭίԱ'],['5', '������'],['6', '��ίίԱ']" ></odin:select2>
				
            </tr>
            <tr>
                <odin:NewDateEditTag property="rzsj" isCheck="true" label="��ְʱ��"/>
                <odin:NewDateEditTag property="mzsj" isCheck="true" label="��ְʱ��"/>
            </tr> --%>
        </table>
    </odin:groupBox>
		
</div>
<odin:panel property="panel" contentEl="content" topBarId="toolBar"/>

<odin:hidden property="type"/>
<odin:hidden property="js00"/>
<odin:hidden property="personId"/>
<odin:hidden property="ssid"/>
<script>
Ext.onReady(function () {
    //var data = parent.Ext.getCmp(subWinId).initialConfig.data
    
    if(parentParams.jgId){
        document.getElementById("js00").value=parentParams.js00;
    }
    
    document.getElementById("r1").checked='true';
	document.getElementById("tr2").style.display="none";
	document.getElementById("type").value='1';
    

})
function openSelectPersonWin() {
    $h.openWin('selectPerson', 'pages.gzgl.CjqkAddSelect', 'ѡ����Ա ', 1200, 610, null, contextPath, null, {
        maximizable: false,
        resizable: false,
        closeAction: 'close'
    })
}

function callback(param) {
    document.getElementById("personId").value = param
    // �ر�ѡ����Ա����
    $h.getTopParent().Ext.getCmp('selectPerson').close()
    // ������Ա��Ϣ
     radow.doEvent('freshInfo') 
}

function updateMC(){
	document.getElementById("personId").value="";
	document.getElementById("a0101_1").value="";
	document.getElementById("a0107_1").value="";
	document.getElementById("a0107_1_1").value="";
	document.getElementById("a0192a_1").value="";
	document.getElementById("a0192f_1").value="";
	document.getElementById("a0192f_1_1").value="";
	document.getElementById("a0101_2").value="";
	document.getElementById("a0107_2").value="";
	document.getElementById("a0107_2_1").value="";
	document.getElementById("a0192a_2").value="";
	document.getElementById("a0192f_2").value="";
	document.getElementById("a0192f_2_1").value="";
	var r1=$("input[name='radio_1']:checked").val();
	if(r1=='1'){
		document.getElementById("type").value="1";
		document.getElementById("tr1").style.display="";
		document.getElementById("tr2").style.display="none";
	}else if(r1=='2'){
		document.getElementById("type").value="2";
		document.getElementById("tr1").style.display="none";
		document.getElementById("tr2").style.display="";
	}
}


    


</script>