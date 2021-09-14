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
           //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
           collapsible: true,    
           /* title: '标题',//标题文本  */   
           width: 295,  
           height:190,
           border : false,//表框    
           autoScroll: true,//自动滚动条    
           animate : true,//动画效果    
           rootVisible: true,//根节点是否可见    
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
                   tree.expandAll();//展开树     
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
    			<odin:groupBox property="group1" title="代码大类" >
    				<table>
    					<tr>
    						<td>
    							<table>
    								<tr>
    									<td>
    									<odin:textEdit property="selt1" label="查询条件" maxlength="10" width="240"/>
    									</td>
    								</tr>
    							</table>
    						</td>
    						<td>
    							<table>
    								<tr style="display: none;">
    									<td style="padding-left: 19px">
    									<odin:textEdit property="selt2" label="代码集名称" maxlength="10" width="200" />
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
														<odin:gridEditColumn header="代码集描述" align="left"
															edited="false" width="268" dataIndex="rowsdsc"
															editor="text" hidden="true" />
														<odin:gridEditColumn header="代码集名称" align="left"
															edited="false" width="268" dataIndex="rowsname"
															editor="text" isLast="true" />
													</odin:gridColumnModel>
											</odin:grid>
    									</td>
    								</tr>
    							</table>
    							
    						</td>
							<td>
								<odin:groupBox title="代码集属性">
								<table style="height: 120px;width: 300px;">
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<odin:textEdit property="t1" label="代码集编码" maxlength="10" required="true" onchange="changeTypeValue();" width="235" value=" "></odin:textEdit>	
													</td>
												</tr>
												<tr>
													<td style="height: 3px;"></td>
												</tr>
												<tr>
													<td>
														<odin:textEdit property="t2" label="代码集名称" required="true" width="235" onchange="specialValue();" maxlength="100"></odin:textEdit>
													</td>
												</tr>
												<tr>
													<td>
														<odin:textarea property="area2" label="代码集描述" required="true" onchange="specialValue();" maxlength="100"></odin:textarea>
													</td>
												</tr>
												<!-- <tr>
													<td>
														<font>注:代码集编码必须以KZ开头，并且只能输入字母或阿拉伯数字</font>
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
						<td><input  type="button" class="yellowbutton" value="查&nbsp;询" id="selbtn" onclick="selBtn()"/></td>
					</tr>
					
					<tr id="showbtn1">
						<td><input  type="button" class="yellowbutton" value="新&nbsp;增" id="btn1" onclick="typeCreat()"/></td>
					</tr>
					<tr id="hiddenbtn1" style="display: none;">
						<td><input  type="button" class="graybutton" value="新&nbsp;增" disabled="disabled"/></td>
					</tr>
					
					<tr id="showbtn2" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="删&nbsp;除" id="btn2" onclick="typeDelete()"/></td>
					</tr>
					<tr id="hiddenbtn2" >
						<td><input  type="button" class="graybutton" value="删&nbsp;除" disabled="disabled"/></td>
					</tr>
					
					<tr id="showbtn3" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="保&nbsp;存" id="btn3" onclick="typeSave()"/></td>
					</tr>
					<tr id="hiddenbtn3" >
						<td><input  type="button" class="graybutton" value="保&nbsp;存" disabled="disabled"/></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td>
				<div style="width: 640px;height: 250px;">
    			<odin:groupBox property="group2" title="代码项" >
    				<table>
    					<tr>
    						<td>
    							<odin:groupBox title="选择代码"  >
    								<div id="tree-div1" style="width: 295px;height: 205px;"></div>
    							</odin:groupBox>
    						</td>
							<td>
							<odin:groupBox title="代码属性">
								<table style="height: 180px; margin-left: 15px;width: 300px;" id="table1">
									<tr>
										<td>
											<odin:textEdit property="td1" label="代码编号" required="true" width="200" maxlength="50" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="s2" label="代码名称" required="true" width="200" maxlength="100" onkeyup="queryJP(this,'td5')" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td2" label="代码简称1" required="true" width="200" maxlength="30" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td3" label="代码简称2" width="200" maxlength="30" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td4" label="代码录入简称" width="200" maxlength="100" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:textEdit property="td5" label="代码首拼录入" width="200" maxlength="50" onchange="checkValue();"></odin:textEdit>	
										</td>
									</tr>
									<tr>
										<td>
											<odin:select property="td6" label="优先级" width="200"></odin:select>	
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
						<td><input  type="button" class="yellowbutton" value="新&nbsp;增" id="btn4" onclick="creatCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn4"  >
						<td><input  type="button" class="graybutton" value="新&nbsp;增" disabled="disabled"/></td>
					</tr>
					
					
					<tr id="showbtn5" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="删&nbsp;除" id="btn5" onclick="deleteCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn5" >
						<td><input  type="button" class="graybutton" value="删&nbsp;除" disabled="disabled"/></td>
					</tr>
					
					
					<tr id="showbtn6" style="display: none;">
						<td><input  type="button" class="yellowbutton" value="保&nbsp;存" id="btn6" onclick="saveCodeValue()"/></td>
					</tr>
					<tr id="hiddenbtn6" >
						<td><input  type="button" class="graybutton" value="保&nbsp;存" disabled="disabled"/></td>
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
		if (confirm("    确定删除吗？")) {
			radow.doEvent("typeDelete");
		} else {
			return;
		} 
		/* $h.confirm('系统提示', '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确定删除吗？', '300', function(id) {
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
		if (confirm("    确定删除吗？")) {
			radow.doEvent("deleteCodeValue");
		} else {
			return;
		}
	}
	function saveCodeValue() {
		radow.doEvent("saveCodeValue");
	}
	function updateCodeValue() {
		if (confirm("修改会导致引用该代码的数据全部修改,继续修改吗？")) {
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