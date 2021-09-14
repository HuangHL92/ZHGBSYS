<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%-- <odin:toolBar property="btnToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="���ɳʱ���" id="makeCBD" isLast="true"></odin:buttonForToolBar>
</odin:toolBar> --%>

<div id="cbdInfo">
	<table>
		<tr>
			<odin:textEdit property="cdb_word_year_no" label="������_��_��" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="cbd_year" label="������_��" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_no" label="������_��" required="true"></odin:textEdit>
		</tr> --%>
		<tr>
			<odin:textEdit property="cbd_leader" label="�쵼��ν" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_organ" label="�а쵥λ" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<tr>
			<odin:textarea property="cbd_text" label="�ʱ�������" rows="5" cols="50"  required="true" onchange="checkLength()"></odin:textarea>
		</tr>
		<tr>
			<odin:dateEdit property="cbd_date1" label="�ʱ�������"  required="true"></odin:dateEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_cbr" label="�а���" maxlength="50"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_personname" label="�Ǽ���Ա" readonly="true" required="true" disabled="true" ></odin:textEdit>
			 <td id="choose">
				<input type="button" value="ѡ����Ա" onclick="selectPerson()" name="selectperson" id="selectperson"/>
			</td> 
			
		</tr>
		<tr >
		    <td width="40"></td>
		    <td>
			    <input type="button" value="���ɳʱ���" onclick="makeCBD()" name="cbd" id="cbd"/>
			</td>
		</tr>
	</table>
</div>
<odin:hidden property="cbd_userid"/>
<odin:hidden property="cbd_username"/>
<odin:hidden property="cbd_personid"/>
<odin:hidden property="cbd_id" />
<odin:hidden property="objectno" />
<odin:hidden property="flag" />
<odin:panel contentEl="cbdInfo" property="cbdPanel" topBarId="btnToolBar"/>

<odin:window src="/blank.htm" id="selectPerson" width="320" height="150" title="ѡ����Ա����" modal="true"></odin:window>
<script type="text/javascript">
   function makeCBD(){
	   radow.doEvent('makeCBD.onclick');
   }

   function control(){
	   document.getElementById('choose').style.display = 'none';
   }


	function selectPerson(){
	
		doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.cbdHandler.SelectPerson",
		"ѡ����Ա����",500,400,null);
		
	}
	//�жϳʱ�����������ĳ��ȣ��������������ʾ������ȡ����Ҫ��ĳ��ȵ��ؼ��С�
	function checkLength(){
		var cbd_text = document.getElementById("cbd_text").value;
		var size = cbd_text.length;
		if(size > 1000){
			alert("�ʱ��������������ݳ��ȳ�������,��󳤶�Ϊ1000��");
			return;
			document.setElementById("cbd_text").value=cbd_text.substring(0,1000);
		}
	}

</script>