<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar2">
	<odin:textForToolBar text="������������ӡ�(���в����������Ч)"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:resetBtn></ss:resetBtn>
	<ss:doClickBtn id="addrow" text="��������"  icon="images/add.gif"  handlerName="addrow" ></ss:doClickBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
</ss:toolBar>

<ss:editgrid property="div_2" pageSize="150" topBarId="bar2"   afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title="������Ϣ"  height="-50,1" autoFill="false">
		<ss:gridColModel>
			<ss:gridCol header="����id" name="conditionid" editor="text" p="H"></ss:gridCol>
			<ss:gridCol header="��������" editor="text" name="conditionname" width="180" p="H" maxLength="100"></ss:gridCol>
			<ss:gridCol header="�ֶ�" name="target" width="130" editor="select" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="�������" name="operater" width="130" editor="select" p="E" maxLength="100"></ss:gridCol>
			<ss:gridCol header="����" name="condition" width="130" editor="text" p="E" ></ss:gridCol>
			<ss:gridCol header="ɾ��" name="delrow" width="130" editor="text" renderer="renderClick"></ss:gridCol>
		</ss:gridColModel>
</ss:editgrid>
<script type="text/javascript">
	function init(){ 
			var inputList = parent.curparent.document.getElementsByTagName("input");
			var selectData = new Array();;
			var j=0;
			//alert(odin.encode(parent.curparent.page_element_tree.div_2.label));
			if (inputList != null && inputList.length > 0) {
				for (i = 0; i < inputList.length; i++) {
					var label = inputList.item(i).getAttribute("label");
					var div=getInputParentDiv(inputList.item(i).name);
				 	if(label!=null&&label!=""){
						selectData[j]={};
						selectData[j].key = div+"."+inputList.item(i).name;
						selectData[j].value= div+"."+label;
						j++;
					}
				}
			}
			var gridDivs = parent.curparent.radow.getGridDivs();
			if(gridDivs!=null){
				gridDivs = gridDivs.uniqueEx();
					for (i = 0; i < gridDivs.length; i++) {
						var gid = gridDivs[i];
						gid = gid.substr(gid.indexOf("_")+1);
						var gridLabel =eval("parent.curparent.page_element_tree."+gid+".label");
						for(lab in gridLabel){
							selectData[j]={};
							selectData[j].key =gid+"."+lab;
							if(lab=='check'){
								selectData[j].value='��ѡ���';
							}else{
								selectData[j].value=gid+"."+gridLabel[lab];
							}
							j++;
						}
					}
			}
			reSetSelectData("target",selectData);
	}
	
	/**
 	* ȡ�ñ�input��Ŀ�ĸ�div  
 	*/
	function getInputParentDiv(inputName) {
		var divList = parent.curparent.document.getElementsByTagName("div");
		for (var i = 0; i < divList.length; i++) {
			var div = divList.item(i);
			if (getDivItem(div, inputName) != null&&div.id.indexOf("div") != -1) {
				return div.id;
			} else if (Ext.getCmp(div.id)) {
				try {
					var gridColumnModel = Ext.getCmp(div.id).getColumnModel();
					for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
						if (gridColumnModel.getDataIndex(j) == inputName) {
							return div.id;
						}
					}
				} catch (e) {
					continue;
				}
			}
		}
	}
</script>