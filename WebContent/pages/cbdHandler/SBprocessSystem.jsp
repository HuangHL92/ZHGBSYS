<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script>
function new1_1(){
	document.getElementById('new').style.display = 'none';
	document.getElementById('new_1').style.display = 'block';	
	
	document.getElementById('firstStepDiv').style.display = 'block';
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
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
}

function sccbd1_1_1(){
	document.getElementById('sccbd').style.display = 'block';
	document.getElementById('sccbd_1').style.display = 'none';
}

function sccbd2_1(){
	document.getElementById('sccbd2').style.display = 'none';
	document.getElementById('sccbd2_1').style.display = 'block';
	
	document.getElementById('secondStepDivDiv').style.display = 'block';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
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
	
}

function bjsh1_1_1(){
	
	document.getElementById('bjsh').style.display = 'block';
	document.getElementById('bjsh_1').style.display = 'none';
}

function bjsh2_1(){
	document.getElementById('bjsh2').style.display = 'none';
	document.getElementById('bjsh2_1').style.display = 'block';
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'block';
}

function bjsh2_1_1(){
	document.getElementById('bjsh2').style.display = 'block';
	document.getElementById('bjsh2_1').style.display = 'none';
}
</script>

<div>
    <table>
		<tr>
			<td width="220"></td>
			<td><label id="name_cbd" style="font-weight:bold;font-size: 18"></label>
			</td>
		</tr>
	</table>
	<odin:groupBox property="picture" title="����ͼ">
		<table id="image1" width="100%">
			<tr>
			    <!-- ��һ������   ----------------------------------------------------------- -->
				<td>
					<div id="new" style="display: block">
					<img src="<%=request.getContextPath()%>/images/xjsp.png" onmousedown="new1_1()" onmouseup="new1_1_1()" />
					</div>
					<div id="new_1" style="display: none">
				    <img src="<%=request.getContextPath()%>/images/xjsp_1.png" />
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
					<div id="sccbd" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsp.png"  onmousedown="sccbd1_1()" onmouseup="sccbd1_1_1()"/></div>
					<div id="sccbd_1" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsp_1.png" /></div>
		            <div id="sccbd2" style="display: block"><img src="<%=request.getContextPath()%>/images/bjsp2.png" onmousedown="sccbd2_1()" onmouseup="sccbd2_1_1()"/></div>
		            <div id="sccbd2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/bjsp2_1.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<!-- ����������   ----------------------------------------------------------- -->
				<td>
					<div id="bjsh" style="display: none"><img src="<%=request.getContextPath()%>/images/pf.png"  onmousedown="bjsh1_1()" onmouseup="bjsh1_1_1()"/></div>
					<div id="bjsh_1" style="display: none"><img src="<%=request.getContextPath()%>/images/pf_1.png" /></div>
		            <div id="bjsh2" style="display: block"><img src="<%=request.getContextPath()%>/images/pf2.png"  onmousedown="bjsh2_1()" onmouseup="bjsh2_1_1()"/></div>
		            <div id="bjsh2_1" style="display: none"><img src="<%=request.getContextPath()%>/images/pf2_1.png" /></div>
				</td>
			    
				<%-- <td>
					<div id="new"><img src="<%=request.getContextPath()%>/images/xjsp.png"  onclick="new1()" onmousedown="new1_1()" onmouseup="new1_1_1()"/></div>
					<div id="new"><img src="<%=request.getContextPath()%>/images/xjsp.png" /></div>
		            <div id="new2"><img src="<%=request.getContextPath()%>/images/xjsp2.png" onclick="new2()"/></div>
		            <div id="new2"><img src="<%=request.getContextPath()%>/images/xjsp2.png" /></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<td>
					<div id="sccbd"><img src="<%=request.getContextPath()%>/images/tjspcl.png" onclick="sccbd1()"/></div>
		            <div id="sccbd2"><img src="<%=request.getContextPath()%>/images/tjspcl2.png" onclick="sccbd2()"/></div>
				</td>
				<td>
					<img src="<%=request.getContextPath()%>/images/jt-righht.png" />
				</td>
				<td>
					<div id="bjsh"><img src="<%=request.getContextPath()%>/images/bjsp.png" onclick="bjsh1()"/></div>
		            <div id="bjsh2"><img src="<%=request.getContextPath()%>/images/bjsp2.png" onclick="bjsh2()"/></div>
				</td> --%>
			</tr>
		
		</table>
	</odin:groupBox>


		<div id="firstStepDiv">
			<table height="70">
				<tr>
					
					<td width="20"></td>
					<td><input type="button" value="���ɱ����ʱ���" id="createCBD" onclick="event('createCBD.onclick')" /></td>
					<td width="60"></td>
					<td><input type="button" value="�޸ı����ʱ���" id="modifyCBD" onclick="event('modifyCBD.onclick')" /></td>
					<td width="50"></td>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('1')" /></td>
					
				</tr>
			</table>
		</div>
	
		<div id="secondStepDivDiv">
			<table height="70">
				<tr>
					
					<td width="20"></td>
					<td><input type="button" value="���ر����ʱ���" id="getCBD" onclick="event('getCBD.onclick')" /></td>
					<td width="40"></td>
					<td><input type="button" value="�༭�����ʱ�������" id="editBCBDFileBtn" onclick="editBCBDFile()" /></td>
					<td width="47"></td>
					<td><input type="button" value="��  ��" id="nextStepBtn" onclick="nextStep('2')" /></td>
					
				</tr>
			</table>
		</div>

		<div id="thirdStepDivDiv">
			<table height="70">
				<tr>
					<td width="20"></td>
					<td><odin:select property="answer"  data="['1','ͬ��'],['2','�˻�']" size="12" label="����"></odin:select></td>
					<td width="60"></td>
					<td><input type="button" value="ͨ����������" id="sureBtn" onclick="sure1()" /></td>
					<td width="40"></td>
					<td><input type="button" value="ͨ����������" id="sureBtn2" onclick="sure()" /></td>
				</tr>
			</table>
		</div>

</div>
<odin:hidden property="cbd_id"/>
<odin:hidden property="cbd_name"/>
<odin:hidden property="filePath"/>
<odin:hidden property="status"/>
<odin:hidden property="step"/>
<odin:hidden property="cbd_personname"/>
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="¼��ʱ���"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="650" height="480" maximizable="false" title="�鿴/ɾ����������"></odin:window>
<odin:window src="/blank.htm" id="backWin" width="560" height="350" maximizable="false" modal="true" title="����������"></odin:window>
<script>
function setButton(buttonname){
	document.getElementById(buttonname).disabled=true;
}

function event(eventName){
	radow.doEvent(eventName);
}
   
   /** �״������б����ݿ�ʼ */
Ext.onReady(function(){
	document.getElementById('secondStepDivDiv').style.display = 'none';
	document.getElementById('thirdStepDivDiv').style.display = 'none';
	document.getElementById('firstStepDiv').style.display = 'none';
	
	//����ͼƬĬ�ϲ���ʾ
	document.getElementById('new').style.display = 'none';
	document.getElementById('sccbd').style.display = 'none';
	document.getElementById('bjsh').style.display = 'none';
});


//�½��Ǽ�ͼƬ����¼����ң�--1�׶�
	function new2(){
		document.getElementById('firstStepDiv').style.display = 'block';
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		/*
		document.getElementById('new').style.display = 'block';
		document.getElementById('new2').style.display = 'none';
		//��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("2") == -1){
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
		}
		if(step.indexOf("3") == -1){
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
		}*/
	}
	
	//�½��Ǽ�ͼƬ����¼�������--1�׶�
	function new1(){
		document.getElementById('firstStepDiv').style.display = 'block';
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		/* //��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("2") == -1){
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
		}
		if(step.indexOf("3") == -1){
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
		} */
	}
	
	//���ɱ����ʱ���ͼƬ����¼�������--2�׶�
	function sccbd1(){
		document.getElementById('secondStepDivDiv').style.display = 'block';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		
		/* //��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("1") == -1){
			document.getElementById('new').style.display = 'none';
			document.getElementById('new2').style.display = 'block';
		}
		if(step.indexOf("3") == -1){
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
		} */
	}
	//���ɱ����ʱ���ͼƬ����¼����ң�--2�׶�
	function sccbd2(){
		document.getElementById('secondStepDivDiv').style.display = 'block';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'none';
		/*
		document.getElementById('sccbd').style.display = 'block';
		document.getElementById('sccbd2').style.display = 'none';
	 //��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("1") == -1){
			document.getElementById('new').style.display = 'none';
			document.getElementById('new2').style.display = 'block';
		}
		if(step.indexOf("3") == -1){
			document.getElementById('bjsh').style.display = 'none';
			document.getElementById('bjsh2').style.display = 'block';
		} */
	}
	
	
	//�������ͼƬ����¼�������--3�׶�
	function bjsh1(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'block';
		
		/* //��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("2") == -1){
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
		}
		if(step.indexOf("1") == -1){
			document.getElementById('new').style.display = 'none';
			document.getElementById('new2').style.display = 'block';
		} */
	}
	
	//�������ͼƬ����¼����ң�--3�׶�
	function bjsh2(){
		document.getElementById('secondStepDivDiv').style.display = 'none';
		document.getElementById('firstStepDiv').style.display = 'none';
		document.getElementById('thirdStepDivDiv').style.display = 'block';
		/*
		document.getElementById('bjsh').style.display = 'block';
		document.getElementById('bjsh2').style.display = 'none';
		 //��ȡ�׶α��
		var step = document.getElementById("step").value;
		if(step.indexOf("2") == -1){
			document.getElementById('sccbd').style.display = 'none';
			document.getElementById('sccbd2').style.display = 'block';
		}
		if(step.indexOf("1") == -1){
			document.getElementById('new').style.display = 'none';
			document.getElementById('new2').style.display = 'block';
		} */
	}
	
	function sure1(){
	   var num = document.getElementById("answer").value;
	   if(num=='1'){
		   reply1();
	   }
	   if(num=='2'){
		   back1();
	   }
	   if(num==''){
		   alert('��ѡ�����������');
		   return;
	   }
   }
   
	function reply1(){
		var cbd_id = document.getElementById("cbd_id").value;
		var filePath = document.getElementById("filePath").value;
		radow.doEvent('reply1',cbd_id+"@"+filePath);
	}
	function back1(){
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		var filePath = document.getElementById("filePath").value;
		var status = document.getElementById("status").value;
		if(status == '2'){
			alert("�����ʱ����Ѿ�����أ������ٴδ�أ�");
			return;
		}
		if(status == '3'){
			alert("�����ʱ����Ѿ���������������ز�����");
			return;
		}
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('getBackWin',cbd_id+"@"+cbd_name+"@"+filePath+"@gp");
	}
   
   function sure(){
	   var num = document.getElementById("answer").value;
	   if(num=='1'){
		   reply();
	   }
	   if(num=='2'){
		   back();
	   }
	   if(num==''){
		   alert('��ѡ�����������');
		   return;
	   }
   }
   
 	//����
	function reply(){
	
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		var filePath = document.getElementById("filePath").value;
		radow.doEvent('reply',cbd_id+"@"+cbd_name+"@"+filePath);
	}
 
	//¼�������
	function back(){
		
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
		var filePath = document.getElementById("filePath").value;
		var status = document.getElementById("status").value;
		if(status == '2'){
			alert("�����ʱ����Ѿ�����أ������ٴδ�أ�");
			return;
		}
		if(status == '3'){
			alert("�����ʱ����Ѿ���������������ز�����");
			return;
		}
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('getBackWin',cbd_id+"@"+cbd_name+"@"+filePath+"@1");
	}
    

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
					
					document.getElementById('firstStepDiv').style.display = 'block';
					document.getElementById('secondStepDivDiv').style.display = 'none';
					document.getElementById('thirdStepDivDiv').style.display = 'none';
				}
				if(value== '1'){
					
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
					
					document.getElementById('firstStepDiv').style.display = 'none';
					document.getElementById('secondStepDivDiv').style.display = 'block';
					document.getElementById('thirdStepDivDiv').style.display = 'none';
					
					
				}
				if(value== '2'){
					
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
					
					document.getElementById('firstStepDiv').style.display = 'none';
					document.getElementById('secondStepDivDiv').style.display = 'none';
					document.getElementById('thirdStepDivDiv').style.display = 'block';
					
					
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
					
					
				}
				
			}

	
	/**
	 * �����ʱ���
	 */
	/**
	 * �����ڲ��ʱ���
	 */
	function expExcelTemp(value) {
	    var cbd_id = value;
		var cbd_name = document.getElementById("cbd_name").value;
		
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=gg&cbd_type=1")), "�����ļ�", 600, 200);
	}

	//��ʾ����
	function setcbdname(value){
		document.getElementById("name_cbd").innerText=value;
	}
	
	//�༭����
	function editBCBDFile(){
		var cbd_id = document.getElementById("cbd_id").value;
		var cbd_name = document.getElementById("cbd_name").value;
   		radow.doEvent("modifyAttach",cbd_id+"@3@"+cbd_name);
	}
		
		//��һ��
	function nextStep(step){
		
		radow.doEvent('nextStep',step);	
	
	}
	
	//�����ʱ���ѹ���ļ�
	function createCBDZip(cbd_id,cbd_name,bj_cbdid,personname){
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/CBDZipDownLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&bj_cbdid="+bj_cbdid+"&personname="+personname)), "���سʱ������ݰ�", 600, 250);
	}
	
	function createCBDZip1(cbd_id,cbd_name,cbd_id_old,personname){
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/CBDZipDownLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=gppf&bj_cbdid="+cbd_id_old+"&personname="+personname)), "���سʱ������ݰ�", 600, 250);
	}
</script>