<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script type="text/javascript">
function new1_1(){
	document.getElementById('new').style.display = 'none';
	document.getElementById('new_1').style.display = 'block';	
	
	document.getElementById('firstStepDiv').style.display = 'block';
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'block';
	document.getElementById('Attach').style.display = 'none';
}

function new1_1_1(){
	document.getElementById('new').style.display = 'block';
	document.getElementById('new_1').style.display = 'none';
}

//�ڶ�������
function sccbd1_1(){
	document.getElementById('sccbd').style.display = 'none';
	document.getElementById('sccbd_1').style.display = 'block';
	
	
	document.getElementById('secondStepDivDiv').style.display = 'block';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'none';
	document.getElementById('Attach').style.display = 'block';
	
	radow.doEvent('peopleInfoGrid.dogridquery');
}

function sccbd1_1_1(){
	document.getElementById('sccbd').style.display = 'block';
	document.getElementById('sccbd_1').style.display = 'none';
}

function sccbd2_1(){
	document.getElementById('sccbd2').style.display = 'none';
	document.getElementById('sccbd2_1').style.display = 'block';
	
	var nowStep = document.getElementById('nowStep').value;
	
	if(nowStep>0){
		document.getElementById('secondStepDivDiv').style.display = 'block';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'none';
		document.getElementById('Attach').style.display = 'block';
		radow.doEvent('peopleInfoGrid.dogridquery');
	}
}

function sccbd2_1_1(){
	document.getElementById('sccbd2').style.display = 'block';
	document.getElementById('sccbd2_1').style.display = 'none';
}

//����������
function bjsh1_1(){
	
	document.getElementById('bjsh').style.display = 'none';
	document.getElementById('bjsh_1').style.display = 'block';
	
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'block';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'block';
	document.getElementById('Attach').style.display = 'none';
	
}

function bjsh1_1_1(){
	
	document.getElementById('bjsh').style.display = 'block';
	document.getElementById('bjsh_1').style.display = 'none';
}

function bjsh2_1(){
	document.getElementById('bjsh2').style.display = 'none';
	document.getElementById('bjsh2_1').style.display = 'block';
	
	var nowStep = document.getElementById('nowStep').value;
	if(nowStep>1){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'block';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
	}
}

function bjsh2_1_1(){
	document.getElementById('bjsh2').style.display = 'block';
	document.getElementById('bjsh2_1').style.display = 'none';
}

//���ĸ�����
function scsbcbd1_1(){
	document.getElementById('scsbcbd').style.display = 'none';
	document.getElementById('scsbcbd_1').style.display = 'block';
	
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'block';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'block';
	document.getElementById('Attach').style.display = 'none';
}

function scsbcbd1_1_1(){
	document.getElementById('scsbcbd').style.display = 'block';
	document.getElementById('scsbcbd_1').style.display = 'none';
}

function scsbcbd2_1(){
	document.getElementById('scsbcbd2').style.display = 'none';
	document.getElementById('scsbcbd2_1').style.display = 'block';
	
	var nowStep = document.getElementById('nowStep').value;
	if(nowStep>2){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'block';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
	}
}

function scsbcbd2_1_1(){
	document.getElementById('scsbcbd2').style.display = 'block';
	document.getElementById('scsbcbd2_1').style.display = 'none';
}

//���������
function sp1_1(){
	document.getElementById('sp').style.display = 'none';
	document.getElementById('sp_1').style.display = 'block';
	
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'block';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'block';
	document.getElementById('Attach').style.display = 'none';
}

function sp1_1_1(){
	document.getElementById('sp').style.display = 'block';
	document.getElementById('sp_1').style.display = 'none';
}

function sp2_1(){
	document.getElementById('sp2').style.display = 'none';
	document.getElementById('sp2_1').style.display = 'block';
	
	var nowStep = document.getElementById('nowStep').value;
	if(nowStep>3){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'block';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
	}
}

function sp2_1_1(){
	document.getElementById('sp2').style.display = 'block';
	document.getElementById('sp2_1').style.display = 'none';
}

//����������
function ckjg1_1(){
	document.getElementById('ckjg').style.display = 'none';
	document.getElementById('ckjg_1').style.display = 'block';
	
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'block';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	document.getElementById('noAttach').style.display = 'block';
	document.getElementById('Attach').style.display = 'none';
}

function ckjg1_1_1(){
	document.getElementById('ckjg').style.display = 'block';
	document.getElementById('ckjg_1').style.display = 'none';
}

function ckjg2_1(){
	document.getElementById('ckjg2').style.display = 'none';
	document.getElementById('ckjg2_1').style.display = 'block';
	
	var nowStep = document.getElementById('nowStep').value;
	if(nowStep>4){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'block';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
	}
}

function ckjg2_1_1(){
	document.getElementById('ckjg2').style.display = 'block';
	document.getElementById('ckjg2_1').style.display = 'none';
}

</script>

<div id="main">
    <table>
		<tr>
			<td width="270"></td>
			<td><label id="name_cbd" style="font-weight:bold;font-size: 18"></label>
			</td>
		</tr>
	</table>
	<odin:groupBox property="picture" title="����ͼ">
		<table id="image1" >
			<tr>
			<!-- ��һ������   ----------------------------------------------------------- -->
				<td>
					<div id="new" style="display: block">
					<img src="<%=request.getContextPath()%>/images/new.png" onmousedown="new1_1()" onmouseup="new1_1_1()" />
					</div>
					<div id="new_1" style="display: none">
				    <img src="<%=request.getContextPath()%>/images/new_1.png" />
				    </div>
		           <%--  <div id="new2">
		            <img src="<%=request.getContextPath()%>/images/new2.png" onclick="new2()"/>
		            </div>
		            <div id="new2_1">
		            <img src="<%=request.getContextPath()%>/images/new2_1.png"/>
		            </div> --%>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<!-- �ڶ�������   ----------------------------------------------------------- -->
				<td>
					<div id="sccbd" style="display: none"><img src="<%=request.getContextPath()%>/images/tjryshcl.png"  onmousedown="sccbd1_1()" onmouseup="sccbd1_1_1()"/></div>
					<div id="sccbd_1" style="display: none"><img src="<%=request.getContextPath()%>/images/tjryshcl_1.png" /></div>
		            <div id="sccbd2" style="display: block"><img src="<%=request.getContextPath()%>/images/tjryshcl2.png" onmousedown="sccbd2_1()" onmouseup="sccbd2_1_1()"/></div>
		            <div id="sccbd2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/tjryshcl2_1.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<!-- ����������   ----------------------------------------------------------- -->
				<td>
					<div id="bjsh" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsh.png"  onmousedown="bjsh1_1()" onmouseup="bjsh1_1_1()"/></div>
					<div id="bjsh_1" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsh_1.png" /></div>
		            <div id="bjsh2" style="display: block"><img src="<%=request.getContextPath()%>/images/bjsh2.png"  onmousedown="bjsh2_1()" onmouseup="bjsh2_1_1()"/></div>
		            <div id="bjsh2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsh2_1.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<!-- ���ĸ�����   ----------------------------------------------------------- -->
				<td>
					<div id="scsbcbd" style="display: none"><img src="<%=request.getContextPath()%>/images/scsbcbd.png" onclick="scsbcbd1()" onmousedown="scsbcbd1_1()" onmouseup="scsbcbd1_1_1()"/></div>
					<div id="scsbcbd_1" style="display: none"><img src="<%=request.getContextPath()%>/images/scsbcbd_1.png" /></div>
		            <div id="scsbcbd2" style="display: block"><img src="<%=request.getContextPath()%>/images/scsbcbd2.png" onclick="scsbcbd2()" onmousedown="scsbcbd2_1()" onmouseup="scsbcbd2_1_1()"/></div>
		            <div id="scsbcbd2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/scsbcbd2_1.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				
				<!-- ���������   ----------------------------------------------------------- -->
				<td>
					<div id="sp" style="display: none"><img src="<%=request.getContextPath()%>/images/sp.png" onclick="sp1()" onmousedown="sp1_1()" onmouseup="sp1_1_1()"/></div>
					<div id="sp_1" style="display: none"><img src="<%=request.getContextPath()%>/images/sp_1.png" /></div>
		            <div id="sp2" style="display: block"><img src="<%=request.getContextPath()%>/images/sp2.png" onclick="sp2()" onmousedown="sp2_1()" onmouseup="sp2_1_1()"/></div>
		            <div id="sp2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/sp2_1.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				
				<!-- ����������   ----------------------------------------------------------- -->
				<td>
					<div id="ckjg" style="display: none"><img src="<%=request.getContextPath()%>/images/ckjg.png" onclick="ckjg1()" onmousedown="ckjg1_1()" onmouseup="ckjg1_1_1()"/></div>
					<div id="ckjg_1" style="display: none"><img src="<%=request.getContextPath()%>/images/ckjg_1.png" /></div>
		            <div id="ckjg2" style="display: block"><img src="<%=request.getContextPath()%>/images/ckjg2.png" onclick="ckjg2()" onmousedown="ckjg2_1()" onmouseup="ckjg2_1_1()"/></div>
		            <div id="ckjg2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/ckjg2_1.png"/></div>
				</td>
			</tr>
		
		</table>
		
	</odin:groupBox>
	
	<div style="display: block" id="noAttach">
	<odin:editgrid property="peopleInfoGrid_noAttach" title="�Ǽ���Ա����"
		topBarId="toolBar1" width="400" height="250" autoFill="false"
		bbarId="pageToolBar" pageSize="7">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="personcheck" />
			<odin:gridDataCol name="a0000" />
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0104" />
			<odin:gridDataCol name="a0107" />
			<odin:gridDataCol name="a0117" />
			<odin:gridDataCol name="a0141" />
			<odin:gridDataCol name="a0192" />
			<odin:gridDataCol name="a0184" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
<%-- 			<odin:gridColumn header="selectall" width="40" gridName="personGrid"
				editor="checkbox" dataIndex="personcheck" edited="true"
				checkBoxClick="getCheckListForPerson()"
				checkBoxSelectAllClick="getCheckListForPerson()" /> --%>
			<odin:gridColumn dataIndex="a0000" width="110" header="id"
				align="center" hidden="true" />
			<odin:gridColumn dataIndex="a0101" width="110" header="����"
				align="center" />
			<odin:gridEditColumn2 dataIndex="a0104" width="100" header="�Ա�"
				align="center" editor="select" edited="false" codeType="GB2261" />
			<odin:gridColumn dataIndex="a0107" width="130" header="��������"
				align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0117" width="100" header="����"
				align="center" editor="select" edited="false" codeType="GB3304" />
			<odin:gridEditColumn2 dataIndex="a0141" width="130" header="������ò"
				align="center" editor="select" edited="false" codeType="GB4762" />
			<odin:gridColumn dataIndex="a0192" width="130" header="������λ��ְ��"
				align="center" />
			<odin:gridColumn dataIndex="a0000" width="100" header="�鿴/ɾ������" align="center"
			    renderer="deleteAttach" editor="text" edited="false" isLast="true"/>

			
		</odin:gridColumnModel>
		<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	</odin:editgrid>
    </div>
    
    <div style="display: none" id="Attach">
        <odin:editgrid property="peopleInfoGrid" title="�Ǽ���Ա����"
		topBarId="toolBar1" width="400" height="200" autoFill="false"
		bbarId="pageToolBar" pageSize="7">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="personcheck" />
			<odin:gridDataCol name="a0000" />
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0104" />
			<odin:gridDataCol name="a0107" />
			<odin:gridDataCol name="a0117" />
			<odin:gridDataCol name="a0141" />
			<odin:gridDataCol name="a0192" />
			<odin:gridDataCol name="a0184" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
<%-- 			<odin:gridColumn header="selectall" width="40" gridName="personGrid"
				editor="checkbox" dataIndex="personcheck" edited="true"
				checkBoxClick="getCheckListForPerson()"
				checkBoxSelectAllClick="getCheckListForPerson()" /> --%>
			<odin:gridColumn dataIndex="a0000" width="110" header="id"
				align="center" hidden="true" />
			<odin:gridColumn dataIndex="a0101" width="110" header="����"
				align="center" />
			<odin:gridEditColumn2 dataIndex="a0104" width="100" header="�Ա�"
				align="center" editor="select" edited="false" codeType="GB2261" />
			<odin:gridColumn dataIndex="a0107" width="130" header="��������"
				align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0117" width="100" header="����"
				align="center" editor="select" edited="false" codeType="GB3304" />
			<odin:gridEditColumn2 dataIndex="a0141" width="130" header="������ò"
				align="center" editor="select" edited="false" codeType="GB4762" />
			<odin:gridColumn dataIndex="a0192" width="130" header="������λ��ְ��"
				align="center" />
			<odin:gridColumn dataIndex="a0000" width="100" header="���ɵǼǱ�" align="center"
			    renderer="createDjb" editor="text" edited="false" />
			<odin:gridColumn dataIndex="a0000" width="100" header="�ϴ�����" align="center"
			    renderer="createAttach" editor="text" edited="false" hidden="true" />
			<odin:gridColumn dataIndex="a0000" width="100" header="�鿴/ɾ������" align="center"
			    renderer="deleteAttach" editor="text" edited="false" isLast="true"/>
			
		</odin:gridColumnModel>
		<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	</odin:editgrid>
    </div>

<%-- 	<odin:groupBox property="step" title="�ʱ�����������"> --%>
	
		<div id="firstStepDiv">
			<table height="70">
				<tr>
                    <td width="20"></td>
                    <td><input type="button" value="¼�뱾���ʱ���" id="addCBD" onclick="event('addCBD.onclick')" /></td>
					<td width="40"></td>
					<td><input type="button" value="�޸ı����ʱ���" id="modifyCBDBtn" onclick="modifyCBD()" /></td>
					<td width="60"></td>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('1')" /></td>
				</tr>
			</table>
		</div>
	
		<div id="secondStepDivDiv">
			<table height="70" id="secondStepDiv">
				<tr>
                    <td width="20"></td>
					<%-- <td><odin:button text="�ǼǱ����" property="djbOperate" handler="createExcelTemp"></odin:button></td>
					<td width="40"></td> --%>
					<td><input type="button" value="����������" id="expbab" onclick="getSheetBab()" /></td>
					<td width="40"></td>
					<%-- <td><odin:button text="�༭��Ա����" property="openAttach"></odin:button></td>
					<td width="60"></td> --%>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('2')" /></td>
				</tr>
			</table>
		</div>

		<div id="thirdStepDivDiv">
			<table height="70">
				<tr>
                    <td width="20"></td>
                    <td><input type="button" value="���ر����ʱ���" id="downLoadNb" onclick="expExcelTemp1()" /></td>
					<td width="40"></td>
					<td><input type="button" value="�༭�����ʱ�������" id="openAttachNbCbd" onclick="editBCBDFile()" /></td>
					<td width="60"></td>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('3')" /></td>
				</tr>
			</table>
		</div>

		<div id="fourthStepDivDiv">
			<table height="70">
				<tr>
                    <td width="20"></td>
                    <td><input type="button" value="�����ϱ��ʱ���" id="addUpCBDBtn" onclick="event('addUpCBDBtn.onclick')" /></td>
					<td width="40"></td>
					<td><input type="button" value="�޸��ϱ��ʱ���" id="modifyUpCBDBtn" onclick="event('modifyUpCBDBtn.onclick')" /></td>
					<td width="60"></td>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('4')" /></td>
				</tr>
			</table>
		</div>

		<div id="fifthStepDivDiv">
			<table height="70">
				<tr>
				    <td width="20"></td>
					<td><input type="button" value="�����ϱ��ʱ���" id="downLoadSb" onclick="expExcelTemp2()" /></td>
					<td width="40"></td>
					<td><input type="button" value="�༭�ϱ��ʱ�������" id="openAttachSbCbd" onclick="editUCBDFile()" /></td>
					<td width="60"></td>
					<td><input type="button" value="ͨ�������ϱ�" id="makeSure" onclick="commitCBD()" /></td>
					<td width="40"></td>
					<td><input type="button" value="ͨ�������ϱ�" id="makeSure2" onclick="commitCBD2()" /></td>
				</tr>
			</table>
		</div>
		
		<div id="sixthStepDivDiv">
			<table height="70">
				<tr>
                    <td width="20"></td>
					<td><odin:select property="end" defaultValue="��ѡ���������"
							data="['1','ͬ��'],['2','���']" label="�������" size="15"></odin:select></td>
					<td width="33"></td>
					<td><input type="button" value="�浵�����ļ�" id="archiveBtn" onclick="archive()" /></td>
					<td width="133"></td>
					<td><input type="button" value="ȷ  ��" id="makeSure" onclick="event('makeSure.onclick')" /></td>
				</tr>
			</table>
		</div>
		<div id="seventhStepDivDiv">
			<table height="70">
				<tr>
					<td><label id="fifth" style="font-size: 12">&nbsp;&nbsp;&nbsp;&nbsp;������&nbsp;&nbsp;&nbsp;&nbsp;</label></td>
					<td><label id="thirdName" style="font-size: 12">����Ա�Ǽ��������
					</label></td>
				</tr>
			</table>
		</div>
<%-- 	</odin:groupBox> --%>
</div>
<odin:hidden property="cbd_id"/>
<odin:hidden property="sb_cbd_id"/>
<odin:hidden property="cbd_path"/>
<odin:hidden property="cbd_name"/>
<odin:hidden property="cbd_personname"/>
<odin:hidden property="cbd_personid"/>
<odin:hidden property="step"/>
<odin:hidden property="resource"/>
<odin:hidden property="nowStep"/>
<odin:hidden property="person_id"/>
<odin:hidden property="person_name"/>

<odin:hidden property="djgridString"/>
<odin:hidden property="zhgridString"/>
<odin:hidden property="djs" />
<odin:hidden property="gbs"/>
<odin:hidden property="sys"/>
<odin:hidden property="zhs"/>
<odin:hidden property="dw"/>

<odin:hidden property="a0000"/>
<odin:hidden property="a0101"/>
<odin:hidden property="filename"/>

<odin:window src="/blank.htm" id="personWindow" width="700" height="400"
	title="��Ա��������" modal="true" />
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="��������"></odin:window>
<odin:window src="/blank.htm" modal="true" id="ReportCBD" title="�ϱ��ʱ���ҳ��" width="600" height="350"></odin:window>
<odin:window src="/blank.htm" modal="true" id="newUpCBD" title="���ɳʱ���ҳ��" width="600" height="450"></odin:window>
<%-- <odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="350" maximizable="false" title="�鿴/ɾ����������"></odin:window> --%>
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="¼��ʱ���" modal="true"/>
<odin:window src="/blank.htm" id="modifyFileWindow" width="700" height="450" maximizable="false" title="�鿴/ɾ����������" modal="true"></odin:window>

<script type="text/javascript">

	function event(eventName){
		radow.doEvent(eventName);
	}
   
    /**
     * ���ɵǼǱ�
     */
    function createExcelTemp(a0000) {
	    var a0000 = a0000;
	    radow.doEvent('checkPer', a0000 + "@1");
    }
    
    function getPersonIdForDj(){
    	var personId = document.getElementById('a0000').value;
    	return personId;
	}
	
	function getPersonNameForDj(){
    	var personName = document.getElementById('a0101').value;
    	return personName;
	}
    
    /**
	 * �����ǼǱ�
	 */
	function expExcelTemp() {
		var filename = document.getElementById('filename').value;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?download=true&filename="+filename)), "�����ļ�", 600, 200);
	}
    
	/**
	 * ����������
	 */
	function getSheetBab() {
		var a0000 = document.getElementById('cbd_personid').value;
		var a0101 = document.getElementById('cbd_personname').value;
		
		radow.doEvent('getSheet', a0000 + "@" + a0101);

	}
	
	function getDjgridString(){
		var djgridString=document.getElementById("djgridString").value;
		return djgridString;
	}

	function getZhgridString(){
		var zhgridString=document.getElementById("zhgridString").value;
		return zhgridString;
	}
	
	function downLoadTmp() {
		var dw = document.getElementById("dw").value;
		var gbs = document.getElementById("gbs").value;
		var sys = document.getElementById("sys").value;
		var djs = document.getElementById("djs").value;
		var zhs = document.getElementById("zhs").value;

		var djgridString = document.getElementById("djgridString").value;
		var zhgridString = document.getElementById("zhgridString").value;
		var checkList = 3;
		if(typeof(djgridString)!='undefined'&&djgridString!=''){
			if(zhs!=''&&zhs!=0){
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&babdj=1"+"&checkList="+checkList+"&tmpType=['3', '����Ա�ǼǱ�����'],['4', '���չ���Ա��������أ���λ������Ա�ǼǱ�����']")),
			 			"�����ļ�", 500, 160);
			}else{
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&babdj=1"+"&checkList="+checkList+"&tmpType=['13', '����Ա�ǼǱ�����'],['14', '���չ���Ա��������أ���λ������Ա�ǼǱ�����']")),
			 			"�����ļ�", 500, 160);
			}
		}else{
			alert("û�еǼ��κ���Ա���ܵ�����");
		}

	}
 
	function ml(a0000,allName){
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = allName;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp")), "�����ļ�", 600, 400);
	}
	
	function createDjb(value, params, rs, rowIndex, colIndex, ds, record){
		return "<a href=\"javascript:createExcelTemp('" + value + "')\">���ɵǼǱ�</a>";
	}

    function createAttach(value, params, rs, rowIndex, colIndex, ds){
    	return "<a href=\"javascript:editFilePer('" + value + "')\">�ϴ�����</a>";
    }
    
  //�ǼǱ�༭����
	function editFilePer(a0000) {
		var value = a0000;
		var name = "����";
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid=" + value
				+ "&uname=" + name);
	}
    
    function deleteAttach(value, params, rs, rowIndex, colIndex, ds){
    	return "<a href=\"javascript:modifyFilePer('" + rs.get('a0000') + "','"+rs.get('a0101')+"','"+rs.get('a0184')+"')\">�鿴/ɾ������</a>";
    }
    
  //�鿴/ɾ������
	function modifyFilePer(a0000,a0101,a0184) {

		var value = a0000;

		radow.doEvent("modifyAttach", value + "@0@"+a0101);

	}

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){

	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('fourthStepDivDiv').style.display = 'none';
	document.getElementById('fifthStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('sixthStepDivDiv').style.display = 'none';
	document.getElementById('seventhStepDivDiv').style.display = 'none';
	
	//����ͼƬĬ�ϲ���ʾ
	document.getElementById('new').style.display = 'none';
	document.getElementById('sccbd').style.display = 'none';
	document.getElementById('bjsh').style.display = 'none';
	document.getElementById('ckjg').style.display = 'none';
	document.getElementById('sp').style.display = 'none';
	document.getElementById('scsbcbd').style.display = 'none';
});

//���ݳʱ������̿��ƽ�����ʾЧ��
function controlStep(step){

		var value = step
		 if(value== '0'){
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'none';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'block';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'none';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'block';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		} 
		if(value== '1'){
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'none';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'block';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'none';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'block';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
		if(value== '2'){
			
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'none';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'block';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'none';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'block';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
		if(value== '3'){
			
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'none';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'block';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'none';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'block';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
		if(value== '4'){
			
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'none';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'block';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
		if(value== '5'){
			
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'block';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'none';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'none';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'block';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
		if(value== '6'){
			
			document.getElementById('new').style.display = 'block';
			document.getElementById('new_1').style.display = 'none';
			
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('sccbd_1').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('sccbd2_1').style.display = 'none';
			
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('bjsh_1').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('bjsh2_1').style.display = 'none';
			
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('scsbcbd_1').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('scsbcbd2_1').style.display = 'none';
			
			document.getElementById('sp').style.display = 'block';
			document.getElementById('sp_1').style.display = 'none';
			document.getElementById('sp2').style.display = 'none';
			document.getElementById('sp2_1').style.display = 'none';
			
			document.getElementById('ckjg').style.display = 'block';
			document.getElementById('ckjg_1').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'none';
			document.getElementById('ckjg2_1').style.display = 'none';
		}
	}

	
	//�½��Ǽ�ͼƬ����¼����ң�--1�׶�
	function new2(){
		document.getElementById('firstStepDiv').style.display = 'block';
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
		
	}
	
	//�½��Ǽ�ͼƬ����¼�������--1�׶�
	function new1(){
	
		document.getElementById('firstStepDiv').style.display = 'block';
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
		
	}
	
	//���ɱ����ʱ���ͼƬ����¼�������--2�׶�
	function sccbd1(){
		
		document.getElementById('secondStepDivDiv').style.display = 'block';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'none';
		document.getElementById('Attach').style.display = 'block';
		
		radow.doEvent('peopleInfoGrid.dogridquery');
		
	}
	//���ɱ����ʱ���ͼƬ����¼����ң�--2�׶�
	function sccbd2(){
		var nowStep = document.getElementById('nowStep').value;
		
		if(nowStep>0){
			document.getElementById('secondStepDivDiv').style.display = 'block';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'none';
			document.getElementById('fourthStepDivDiv').style.display = 'none';
			document.getElementById('fifthStepDivDiv').style.display = 'none';
			document.getElementById('sixthStepDivDiv').style.display = 'none';
			document.getElementById('seventhStepDivDiv').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'none';
			document.getElementById('Attach').style.display = 'block';
			radow.doEvent('peopleInfoGrid.dogridquery');
		}
		
		
	}
	
	
	//�������ͼƬ����¼�������--3�׶�
	function bjsh1(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'block';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
		
	}
	
	//�������ͼƬ����¼����ң�--3�׶�
	function bjsh2(){
		var nowStep = document.getElementById('nowStep').value;
		if(nowStep>1){
			document.getElementById('secondStepDivDiv').style.display = 'none';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'block';
			document.getElementById('fourthStepDivDiv').style.display = 'none';
			document.getElementById('fifthStepDivDiv').style.display = 'none';
			document.getElementById('sixthStepDivDiv').style.display = 'none';
			document.getElementById('seventhStepDivDiv').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'block';
			document.getElementById('Attach').style.display = 'none';
		}

	}
	
	//�����ϱ��ʱ���ͼƬ����¼�������--4�׶�
	function scsbcbd1(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'block';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
		
	}
	
	//�����ϱ��ʱ���ͼƬ����¼����ң�--4�׶�
	function scsbcbd2(){
		var nowStep = document.getElementById('nowStep').value;
		if(nowStep>2){
			document.getElementById('secondStepDivDiv').style.display = 'none';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'none';
			document.getElementById('fourthStepDivDiv').style.display = 'block';
			document.getElementById('fifthStepDivDiv').style.display = 'none';
			document.getElementById('sixthStepDivDiv').style.display = 'none';
			document.getElementById('seventhStepDivDiv').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'block';
			document.getElementById('Attach').style.display = 'none';
		}

	}
	
	//������Ա���ܲ�������ͼƬ����¼�������--5�׶�
	function sp1(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'block';
		document.getElementById('sixthStepDivDiv').style.display = 'none';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';
	}
	
	//������Ա���ܲ�������ͼƬ����¼����ң�--5�׶�
	function sp2(){
		var nowStep = document.getElementById('nowStep').value;
		if(nowStep>3){
			document.getElementById('secondStepDivDiv').style.display = 'none';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'none';
			document.getElementById('fourthStepDivDiv').style.display = 'none';
			document.getElementById('fifthStepDivDiv').style.display = 'block';
			document.getElementById('sixthStepDivDiv').style.display = 'none';
			document.getElementById('seventhStepDivDiv').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'block';
			document.getElementById('Attach').style.display = 'none';
		}
	}
	
	//�鿴�������ͼƬ����¼�������--6�׶�
	function ckjg1(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		document.getElementById('fourthStepDivDiv').style.display = 'none';
		document.getElementById('fifthStepDivDiv').style.display = 'none';
		document.getElementById('sixthStepDivDiv').style.display = 'block';
		document.getElementById('seventhStepDivDiv').style.display = 'none';
		
		document.getElementById('noAttach').style.display = 'block';
		document.getElementById('Attach').style.display = 'none';

	}
	
	//�鿴�������ͼƬ����¼����ң�--6�׶�
	function ckjg2(){
		var nowStep = document.getElementById('nowStep').value;
		if(nowStep>4){
			document.getElementById('secondStepDivDiv').style.display = 'none';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'none';
			document.getElementById('fourthStepDivDiv').style.display = 'none';
			document.getElementById('fifthStepDivDiv').style.display = 'none';
			document.getElementById('sixthStepDivDiv').style.display = 'block';
			document.getElementById('seventhStepDivDiv').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'block';
			document.getElementById('Attach').style.display = 'none';
		}

	}
	
	
	
    //�ϱ��ʱ�����ʽͨ���ļ�
    function commitCBD(){
    	var cbd_id = document.getElementById("cbd_id").value;
    	var cbd_name = document.getElementById("cbd_name").value;
    	radow.doEvent('getCBDZip',cbd_id+"@"+cbd_name);
    }
    
    //�ϱ��ʱ�����ʽͨ��ftp
    function commitCBD2(){
    	var cbd_id = document.getElementById("cbd_id").value;
    	var cbd_name = document.getElementById("cbd_name").value;
    	radow.doEvent('repBtn',cbd_id+"@"+cbd_name);
    }
    
    //�ϱ��ʱ�����ʽ
    function commitCBD1(){
    	var num = document.getElementById("commit").value;
    	var num1 = document	.getElementById("commit_combo").value;
    	var cbd_id = document.getElementById("cbd_id").value;
    	var cbd_name = document.getElementById("cbd_name").value;
    	if(num1==''){
    		num='';
    		document.getElementById("commit").value='';
    	}
    	
    	if(num=='0'){
    		radow.doEvent('repBtn',cbd_id+"@"+cbd_name);
    	}
    	if(num=='1'){
    		radow.doEvent('getCBDZip',cbd_id+"@"+cbd_name);
    	}
    }
    
  	//�����ʱ���ѹ���ļ�
	function createCBDZip(cbd_id,cbd_name,bj_cbdid,personname){
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/CBDZipDownLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&bj_cbdid="+bj_cbdid+"&personname="+personname)), "���سʱ������ݰ�", 600, 250);
	}
	
	//��ʾ����
	function setcbdname(value){
		document.getElementById("name_cbd").innerText=value;
	}
	

	/**
	 * �����ڲ��ʱ���
	 */
	function expExcelTemp1() {
	
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		if(cbd_id ==''){
			alert('δ���ɱ����ʱ�����');
			return;
		}
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&cbd_type=1")), "�����ļ�", 600, 200);
	}
	
	/**
	 * �����ϱ��ʱ���
	 */
	function expExcelTemp2() {
	
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		if(cbd_id ==''){
			alert('δ�����ϱ��ʱ�����');
			return;
		}
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=gu&cbd_type=2")), "�����ļ�", 600, 200);
	}
	
	//�������ʱ�������
	function editBCBDFile(){
		
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		if(cbd_id == ''){
			alert('δ���ɱ����ʱ�����');
			return;
		}
    	radow.doEvent("modifyAttach",cbd_id+"@1@"+cbd_name);
		
	}
	//�����ϱ��ʱ�������
	function editUCBDFile(){
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
    	radow.doEvent("modifyAttach",cbd_id+"@2@"+cbd_name);
		
	}
	
	//�浵�����ļ�
	function archive(){
		var cbd_name = document.getElementById("cbd_name").value;
		var cbd_id = document.getElementById("cbd_id").value;
    	radow.doEvent("modifyAttach",cbd_id+"@1@"+cbd_name);
	}
	
	//�޸ĳʱ���
	function modifyCBD(){
	
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_personname = document.getElementById("cbd_personname").value;
		if(cbd_id == ''){
			alert('�������ɱ����ʱ�����');
			return;
		}
		radow.doEvent('modifyCBD',cbd_id+"@0@meiyong");
	}
	
	//��һ��
	function nextStep(step){
		
		radow.doEvent('nextStep',step);	
	
	}
	
	function setNextStep(curStep){
		if(curStep == '0'){
			document.getElementById('firstStepDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			//document.getElementById('new2').style.display = 'none';
		}
		if(curStep == '1'){
			document.getElementById('secondStepDivDiv').style.display = 'block';
			document.getElementById('firstStepDiv').style.display = 'none';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'none';
			document.getElementById('Attach').style.display = 'block';
			
			radow.doEvent('peopleInfoGrid.dogridquery');


		}
		if(curStep == '2'){
			document.getElementById('secondStepDivDiv').style.display = 'none';
			document.getElementById('thirdStepDivDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('bjsh').style.display = 'block';

			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			
			document.getElementById('noAttach').style.display = 'block';
			document.getElementById('Attach').style.display = 'none';


		}
		if(curStep == '3'){
			document.getElementById('thirdStepDivDiv').style.display = 'none';
			document.getElementById('fourthStepDivDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('scsbcbd').style.display = 'block';

			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';


		}
		if(curStep == '4'){
			document.getElementById('fourthStepDivDiv').style.display = 'none';
			document.getElementById('fifthStepDivDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('sp').style.display = 'block';

			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('sp2').style.display = 'none';


		}
		if(curStep == '5'){
			document.getElementById('fifthStepDivDiv').style.display = 'none';
			document.getElementById('sixthStepDivDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('sp').style.display = 'block';
			document.getElementById('ckjg').style.display = 'block';

			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('sp2').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'none';

		}
		if(curStep == '6'){
			document.getElementById('sixthStepDivDiv').style.display = 'none';
			document.getElementById('seventhStepDivDiv').style.display = 'block';
			document.getElementById('new').style.display = 'block';
			document.getElementById('sccbd').style.display = 'block';
			document.getElementById('bjsh').style.display = 'block';
			document.getElementById('scsbcbd').style.display = 'block';
			document.getElementById('sp').style.display = 'block';
			document.getElementById('ckjg').style.display = 'block';

			//document.getElementById('new2').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'none';
			document.getElementById('scsbcbd2').style.display = 'none';
			document.getElementById('sp2').style.display = 'none';
			document.getElementById('ckjg2').style.display = 'none';
			
		}
	}
	
	function setButton(buttonname){
		document.getElementById(buttonname).disabled=true;
	}
	Ext.onReady(function() {
		//ҳ�����
		 document.getElementById("main").style.height = document.body.clientHeight;
		 Ext.getCmp('peopleInfoGrid_noAttach').setHeight(Ext.getBody().getViewSize().height*0.66);
		 Ext.getCmp('peopleInfoGrid_noAttach').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_peopleInfoGrid_noAttach'))[1]-2); 
//		 document.getElementById('ftpUpManagePanel').style.width = document.body.clientWidth;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
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
</script>
