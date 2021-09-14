<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
#grid1,#grid2 {
	width: 316px !important;
}
#area2 {
	margin-top:5px;
	width: 233px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/cadremgn/sysbuilder/cdjs/pinyinJP.js"></script>
<script type="text/javascript">
Ext.onReady(function() {
	 var tree = new Ext.tree.TreePanel({    
           region: 'center',
           id: 'codeTypeTree',
           el: 'tree-div1',
           //True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
           collapsible: true,    
           /* title: '����',//�����ı�  */   
           width: 295,  
           height:190,
           border : false,//���    
           autoScroll: true,//�Զ�������    
           animate : true,//����Ч��    
           rootVisible: true,//���ڵ��Ƿ�ɼ�    
           split: true,    
           loader : new Ext.tree.TreeLoader({
        	   dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysbuilder.CodeMaintain&eventNames=updateTree&codetype='
           }),  
           root : new Ext.tree.AsyncTreeNode({    
               text: "",
               id:'-1'
           }),  
           listeners: { 
        	   click: function(node){
        		   var codevalue=node.attributes.id;
        		   radow.doEvent("clickCodeValue",codevalue);
        	   },
               afterrender: function(node) {        
                   tree.expandAll();//չ����     
               }        
           }     
       });    
	 tree.render();    

});

</script>
<odin:hidden property="newFlag"/>
<odin:hidden property="codeid"/>
<odin:hidden property="codeType"/>
<div>
	<table>
		<tr>
			<td>
				<div style="width: 640px;height: 200px;">
    			<odin:groupBox property="group1" title="�������" >
    				<table>
    					<tr>
    						<td>
    							<table>
    								<tr>
    									<td>
    									<odin:textEdit property="selt1" label="��ѯ����" maxlength="10" width="240"/>
    									</td>
    								</tr>
    							</table>
    						</td>
    						<td>
    							<table>
    								<tr style="display: none;">
    									<td style="padding-left: 19px">
    									<odin:textEdit property="selt2" label="���뼯����" maxlength="10" width="200" />
    									</td>
    								</tr>
    							</table>
    						</td>
    					</tr>
    					<tr>
    						<td>
    							<table>
    								<tr>
    									<td>
    										<odin:grid property="grid1" autoFill="false" height="150"
													url="/">
													<odin:gridJsonDataModel id="id" root="data">
														<odin:gridDataCol name="rowsdsc" />
														<odin:gridDataCol name="rowsname" isLast="true" />
													</odin:gridJsonDataModel>
													<odin:gridColumnModel>
														<odin:gridRowNumColumn></odin:gridRowNumColumn>
														<odin:gridEditColumn header="���뼯����" align="left"
															edited="false" width="268" dataIndex="rowsdsc"
															editor="text" hidden="true" />
														<odin:gridEditColumn header="���뼯����" align="left"
															edited="false" width="268" dataIndex="rowsname"
															editor="text" isLast="true" />
													</odin:gridColumnModel>
											</odin:grid>
    									</td>
    								</tr>
    							</table>
    							
    						</td>
							<td>
								<odin:groupBox title="���뼯����">
								<table style="height: 120px;width: 300px;">
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<odin:textEdit property="t1" label="���뼯����" maxlength="10" required="true" onchange="changeTypeValue();" width="235" value=" "></odin:textEdit>	
													</td>
												</tr>
												<tr>
													<td style="height: 3px;"></td>
												</tr>
												<tr>
													<td>
														<odin:textEdit property="t2" label="���뼯����" required="true" width="235" onchange="specialValue();" maxlength="100"></odin:textEdit>
													</td>
												</tr>
												<tr>
													<td>
														<odin:textarea property="area2" label="���뼯����" required="true" onchange="specialValue();" maxlength="100"></odin:textarea>
													</td>
												</tr>
												<!-- <tr>
													<td>
														<font>ע:���뼯���������KZ��ͷ������ֻ��������ĸ����������</font>
													</td>
												</tr> -->
											</table>
										</td>
									</tr>
								</table>
								</odin:groupBox>
							</td>
						</tr>
    				</table>
    			</odin:groupBox>
    			</div>
			</td>
			<td>
				<table style="height: 200px; margin-left: 10px; width: 100px;">
					<tr>
						<td><input  type="button" class="yellowbutton" value="��&nbsp;ѯ" id="selbtn" onclick="selBtn()"/></td>
					</tr>
					
					<tr id="showbtn1">
						<td><input  type="button" class="yellowbutton" value="��&nbsp;��" id="btn1" onclick="typeCreat()"/></td>
					</tr>
					<tr id="hiddenbtn1" style="display: none;">
						<td><input  type="button" class="graybutton" value="��&nbsp;��" disabled="disabled"/></td>
					</tr>
					
					<tr id="showbtn2" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="ɾ&nbsp;��" id="btn2" onclick="typeDelete()"/></td>
					</tr>
					<tr id="hiddenbtn2" >
						<td><input  type="button" class="graybutton" value="ɾ&nbsp;��" disabled="disabled"/></td>
					</tr>
					
					<tr id="showbtn3" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="��&nbsp;��" id="btn3" onclick="typeSave()"/></td>
					</tr>
					<tr id="hiddenbtn3" >
						<td><input  type="button" class="graybutton" value="��&nbsp;��" disabled="disabled"/></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<div style="width: 640px;height: 250px;">
    			<odin:groupBox property="group2" title="������" >
    				<table>
    					<tr>
    						<td>
    							<odin:groupBox title="ѡ�����"  >
    								<div id="tree-div1" style="width: 295px;height: 205px;"></div>
    							</odin:groupBox>
    						</td>
							<td>
							<odin:groupBox title="��������">
								<table style="height: 180px; margin-left: 15px;width: 300px;" id="table1">
									<tr>
										<td>
											<odin:textEdit property="td1" label="������" required="true" width="200" maxlength="50" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="s2" label="��������" required="true" width="200" maxlength="100" onkeyup="queryJP(this,'td5')" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td2" label="������1" required="true" width="200" maxlength="30" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td3" label="������2" width="200" maxlength="30" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td4" label="����¼����" width="200" maxlength="100" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td5" label="������ƴ¼��" width="200" maxlength="50" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:select property="td6" label="���ȼ�" width="200"></odin:select>	
										</td>
									</tr>
								</table>
							</odin:groupBox>
							</td>
						</tr>
						</table>
    			</odin:groupBox>
    			</div>
			</td>
			<td>
				<table
					style="height: 200px; margin-left: 10px; width: 100px;">
					<tr id="showbtn4" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="��&nbsp;��" id="btn4" onclick="creatCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn4"  >
						<td><input  type="button" class="graybutton" value="��&nbsp;��" disabled="disabled"/></td>
					</tr>
					
					
					<tr id="showbtn5" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="ɾ&nbsp;��" id="btn5" onclick="deleteCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn5" >
						<td><input  type="button" class="graybutton" value="ɾ&nbsp;��" disabled="disabled"/></td>
					</tr>
					
					
					<tr id="showbtn6" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="��&nbsp;��" id="btn6" onclick="saveCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn6" >
						<td><input  type="button" class="graybutton" value="��&nbsp;��" disabled="disabled"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	function selBtn(){
		radow.doEvent("selBtn");
	}
	function typeCreat(){
		radow.doEvent("typeCreat");
	}
	
	function typeDelete() {
		if (confirm("    ȷ��ɾ����")) {
			radow.doEvent("typeDelete");
		} else {
			return;
		} 
		/* $h.confirm('ϵͳ��ʾ', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ȷ��ɾ����', '300', function(id) {
			if (id == 'ok') {
				radow.doEvent("typeDelete");
			} else if (id == 'cancel') {
				
			}
		}); */
	}
	function typeSave() {
		radow.doEvent("typeSave");
	}
	function changeTypeValue() {
		radow.doEvent("changeTypeValue");
	}
	function specialValue() {
		radow.doEvent("specialValue");
	}
	function checkValue() {
		radow.doEvent("checkValue");
	}
	function updateTree() {
		var tree = Ext.getCmp("codeTypeTree");
		var url = 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysbuilder.CodeMaintain&eventNames=updateTree&codetype=';
		tree.loader.dataUrl = url + document.getElementById('t1').value;
		tree.root.setText(document.getElementById('t2').value);
		tree.root.reload();
	}

	function creatCodeValue() {
		radow.doEvent("creatCodeValue");
	}
	function deleteCodeValue() {
		if (confirm("    ȷ��ɾ����")) {
			radow.doEvent("deleteCodeValue");
		} else {
			return;
		}
	}
	function saveCodeValue() {
		radow.doEvent("saveCodeValue");
	}
	function updateCodeValue() {
		if (confirm("�޸Ļᵼ�����øô��������ȫ���޸�,�����޸���")) {
			radow.doEvent("updateCodeValue");
		} else {
			return;
		}
	}
	
	function showbtn1(){
		document.getElementById('showbtn1').style.display='block'; 
		document.getElementById('hiddenbtn1').style.display='none';
	}
	function hiddenbtn1(){
		document.getElementById('showbtn1').style.display='none'; 
		document.getElementById('hiddenbtn1').style.display='block';
	}
	function showbtn2(){
		document.getElementById('showbtn2').style.display='block'; 
		document.getElementById('hiddenbtn2').style.display='none';
	}
	function hiddenbtn2(){
		document.getElementById('showbtn2').style.display='none'; 
		document.getElementById('hiddenbtn2').style.display='block';
	}
	function showbtn3(){
		document.getElementById('showbtn3').style.display='block'; 
		document.getElementById('hiddenbtn3').style.display='none';
	}
	function hiddenbtn3(){
		document.getElementById('showbtn3').style.display='none'; 
		document.getElementById('hiddenbtn3').style.display='block';
	}
	function showbtn4(){
		document.getElementById('showbtn4').style.display='block'; 
		document.getElementById('hiddenbtn4').style.display='none';
	}
	function hiddenbtn4(){
		document.getElementById('showbtn4').style.display='none'; 
		document.getElementById('hiddenbtn4').style.display='block';
	}
	function showbtn5(){
		document.getElementById('showbtn5').style.display='block'; 
		document.getElementById('hiddenbtn5').style.display='none';
	}
	function hiddenbtn5(){
		document.getElementById('showbtn5').style.display='none'; 
		document.getElementById('hiddenbtn5').style.display='block';
	}
	function showbtn6(){
		document.getElementById('showbtn6').style.display='block'; 
		document.getElementById('hiddenbtn6').style.display='none';
	}
	function hiddenbtn6(){
		document.getElementById('showbtn6').style.display='none'; 
		document.getElementById('hiddenbtn6').style.display='block';
	}
</script>