<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:resetBtn></ss:resetBtn>
	<ss:doSaveBtn />
	<ss:doQueryBtn />
</ss:toolBar>



<ss:module param="P0">
<ss:hlistDiv id="1" cols="6" title="¼����Ϣ">

	<ss:textEdit  property="name" label="����" required="true"  p="E" />
	<ss:select property="functionid" p="E" label="�Ա�" onchange="true"></ss:select>
	<ss:numberEdit property="money"  label="����" p="E"  />
	<ss:dateEdit property="createdate"  label="¼������" p="E" />
</ss:hlistDiv>
</ss:module>



<ss:editgrid property="div_2" autoFill="false" hasRightMenu="true"  pageSize="50" counting="true" grouping="true" groupCol="hj"  afteredit="false" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="��ͨ���" width="780" height="400">
<ss:gridColModel>
  <ss:gridCol header="����" width="160" name="name"  editor="text" p="E" checkBoxClick="" enterAutoAddRow="true" />
  <ss:gridCol header="�Ա�" width="260" name="description" url="" pageSize="" minChars="" editor="text" p="E" enterAutoAddRow="true"/>
   <ss:gridCol header="�ϼ�" width="100" name="hj" editor="number"  p="H" enterAutoAddRow="true"/>	 
  <ss:gridCol header="����" name="money" width="260"   editor="number" enterAutoAddRow="true" countType="sum" renderer="radow.renderMoney"  p="E"/>
   <ss:gridCol header="¼������" name="createdate" width="260"   editor="date"  p="D" />
</ss:gridColModel>		
</ss:editgrid>

<ss:editgrid property="div_3" autoFill="false"  hasRightMenu="false"  pageSize="50" counting="true" grouping="true" groupCol="hj"  afteredit="false" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="��ͨ���" width="780" height="400">
<ss:gridColModel>
  <ss:gridCol header="����" width="160" name="name"  editor="text" p="E" checkBoxClick="" enterAutoAddRow="true" />
  <ss:gridCol header="�Ա�" width="260" name="description" url="" pageSize="" minChars="" editor="text" p="E" enterAutoAddRow="true"/>
   <ss:gridCol header="�ϼ�" width="100" name="hj" editor="number"  p="H" enterAutoAddRow="true"/>	 
  <ss:gridCol header="����" name="money" width="260"   editor="number" enterAutoAddRow="true" countType="sum" renderer="radow.renderMoney"  p="E"/>
   <ss:gridCol header="¼������" name="createdate" width="260"   editor="date"  p="D" />
</ss:gridColModel>		
</ss:editgrid>

<ss:editgrid property="div_4" isFirstLoadData="false" url="/" title="���µ�λԱ��������Ϣ" autoFill="false" width="780" height="150">
<ss:gridColModel>

  <ss:gridCol header="����11" name="money" width="260"   editor="number" enterAutoAddRow="true" countType="sum" renderer="radow.renderMoney"  />
   <ss:gridCol header="¼������" name="createdate" width="260"   editor="date" />
</ss:gridColModel>		
</ss:editgrid>


<script>
function query(dwbm){//��ѯ
	var bu=Ext.getCmp('cpquery_combo');
	document.getElementById('cpquery_combo').value=dwbm;
	if(bu){
		bu.addListener('myquery',function(btn,e){doOnChange(this);});	
		bu.fireEvent('myquery');
	}	
}

function test(){//��ѯ
	radow.cm.doCheck('name');
}
</script>